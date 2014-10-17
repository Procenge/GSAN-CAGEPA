/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cobranca;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action utilizado
 * 
 * @author Yara Taciane
 * @since 21/02/2011
 */
public class ProcessarRequisicaoExtratoDebitoAction
				extends GcomAction {

	/**
	 * Action que captura as requisições vindas da Telametria.
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
					HttpServletResponse response){

		ActionForward actionForward = null;

		Integer codigoRetorno = ConstantesSistema.VALIDO;

		String dadosEnvio = request.getParameter("dadosEnvio");

		if(dadosEnvio == null){
			actionForward = actionMapping.findForward("testeRetorno");
		}else{

			String[] dados = dadosEnvio.split("\\*");

			// ---------------------------------------------------------------------------------
			// VALIDAR IMOVEL
			// ---------------------------------------------------------------------------------
			Integer idImovel = Integer.parseInt(dados[0]);
			Imovel imovel = Fachada.getInstancia().pesquisarImovel(idImovel);
			if(imovel == null){
				codigoRetorno = ConstantesSistema.INVALIDO;
			}
			// ---------------------------------------------------------------------------------

			// ---------------------------------------------------------------------------------
			// VALIDAR DATA DE ENVIO
			// ---------------------------------------------------------------------------------
			Date dataEnvio = new Date();
			if(dados[1] != null){
				dataEnvio = Util.converteStringParaDate(dados[1]);
			}

			// ---------------------------------------------------------------------------------

			// ---------------------------------------------------------------------------------
			// VALIDAR CPF OU CNPJ DO CLIENTE USUÁRIO.
			// ---------------------------------------------------------------------------------
			String cpfCnpj = dados[2];

			Cliente cliente = Fachada.getInstancia().consultarClienteUsuarioImovel(imovel);
			if(cliente != null){
				if(cliente.getCpf() != null){
					if(cpfCnpj.compareTo(cliente.getCpf()) != 0){
						codigoRetorno = ConstantesSistema.INVALIDO;
					}
				}else if(cliente.getCnpj() != null){
					if(cpfCnpj.compareTo(cliente.getCnpj()) != 0){
						codigoRetorno = ConstantesSistema.INVALIDO;
					}
				}
			}else{
				codigoRetorno = ConstantesSistema.INVALIDO;
			}

			String[] retorno = null;
			String representacaoNumericaCodBarraSemDigito = "";
			String valorDocumento = "";
			String valorAcrescimoImpontualidade = "";
			String valorTotalDocumento = "";
			String idDocumentoCobranca = "";

			String resposta = "";

			try{

				if(codigoRetorno.equals(ConstantesSistema.VALIDO)){
					retorno = Fachada.getInstancia().retornarCodBarrasExtratoDebito(imovel);

					if(retorno != null){

						System.out.println("Req. Extrato Déb. imóvel: " + idImovel + " em " + dataEnvio + "CPF: " + cpfCnpj);

						representacaoNumericaCodBarraSemDigito = retorno[0];
						valorDocumento = retorno[1];
						valorAcrescimoImpontualidade = retorno[2];
						valorTotalDocumento = retorno[3];
						idDocumentoCobranca = retorno[4];
					}else{
						codigoRetorno = ConstantesSistema.INVALIDO;
					}

				}

				// ..............................................................................................................................
				resposta = codigoRetorno + "#" + representacaoNumericaCodBarraSemDigito + "#" + valorDocumento + "#"
								+ valorAcrescimoImpontualidade + "#" + valorTotalDocumento + "#" + idDocumentoCobranca;
				// ..............................................................................................................................

				response.getWriter().append(resposta);

			}catch(Exception e){
				e.printStackTrace();
			}

		}

		return actionForward;

	}

}