/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cadastro.imovel;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Remover Cliente do Imovel em Manter Imovel
 * 
 * @author Rafael Santos
 * @created 09/02/2006
 */
public class RemoverAtualizarImovelClienteAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// atualizando o os clientes
		// AdicionarAtualizarImovelClienteAction.atualizarColecaoClienteImovelSessao(httpServletRequest);
		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		// DynaValidatorActionForm inserirImovelClienteActionForm = (DynaValidatorActionForm)
		// actionForm;

		// Cria variaveis
		Collection imovelClientesNovos = (Collection) sessao.getAttribute("imovelClientesNovos");

		// atribui os valores q vem pelo request as variaveis
		String[] clientesImoveis = httpServletRequest.getParameterValues("idRemocaoClienteImovel");

		Imovel imovel = null;

		ActionForward retorno = actionMapping.findForward("atualizarImovelCliente");
		imovel = (Imovel) sessao.getAttribute("imovelAtualizacao");

		Collection colecaoClientesImoveisFimRelacao = new ArrayList();

		// instancia cliente
		ClienteImovel clienteImovel = null;
		Collection colecaoClientesImoveisRemovidos = null;
		if(sessao.getAttribute("colecaoClientesImoveisRemovidos") == null){
			colecaoClientesImoveisRemovidos = new ArrayList();
		}else{
			colecaoClientesImoveisRemovidos = (Collection) sessao.getAttribute("colecaoClientesImoveisRemovidos");
		}

		if(imovelClientesNovos != null && !imovelClientesNovos.equals("")){

			Iterator clienteImovelIterator = imovelClientesNovos.iterator();

			while(clienteImovelIterator.hasNext()){
				clienteImovel = (ClienteImovel) clienteImovelIterator.next();
				for(int i = 0; i < clientesImoveis.length; i++){
					if(obterTimestampIdObjeto(clienteImovel) == Util.converterStringParaLong(clientesImoveis[i]).longValue()){
						if(!(colecaoClientesImoveisRemovidos.contains(clienteImovel))){
							if((imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL))
											&& (clienteImovel.getClienteRelacaoTipo().getId().intValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO
															.intValue())){
								throw new ActionServletException("atencao.remover.cliente.atualizar.imovel");
							}
							if((imovel.getImovelPerfil().getId().equals(ConstantesSistema.INDICADOR_TARIFA_SOCIAL))
											&& (clienteImovel.getClienteRelacaoTipo().getId().intValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_PROPIETARIO
															.intValue())){
								throw new ActionServletException("atencao.remover.cliente.atualizar.imovel");
							}

							if(clienteImovel.getId() != null && !clienteImovel.getId().equals("")){

								// caso seja um cliente im�vel da base ent�o vai para o
								// exibirManterImovelFimRelacaoClienteImovel para colocar o motivo
								// do fim da rela��o
								// retorno =
								// actionMapping.findForward("exibirManterImovelFimRelacaoClienteImovelAction");
								httpServletRequest.setAttribute("aberturaPopup", "1");
								colecaoClientesImoveisFimRelacao.add(clienteImovel);
								sessao.setAttribute("colecaoClientesImoveisFimRelacao", colecaoClientesImoveisFimRelacao);

							}else{
								// verifica se o tipo do cliente � usu�rio
								if(clienteImovel.getClienteRelacaoTipo().getId().shortValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO
												.shortValue()){
									if(sessao.getAttribute("idClienteImovelUsuario") != null){
										sessao.removeAttribute("idClienteImovelUsuario");
									}
								}

								// verifica se o tipo do cliente � responsavel
								if((clienteImovel.getClienteRelacaoTipo().getId().shortValue() == ConstantesSistema.CLIENTE_IMOVEL_TIPO_RESPONSAVEL
												.shortValue())){
									if(sessao.getAttribute("idClienteImovelResponsavel") != null){
										sessao.removeAttribute("idClienteImovelResponsavel");
									}
								}
								if(this.getParametroCompanhia(httpServletRequest).toString().equals(
												SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
									if(clienteImovel.getClienteRelacaoTipo().getId().shortValue() == Util.converterStringParaInteger(
													ConstantesSistema.CLIENTE_IMOVEL_TIPO_PROPIETARIO.toString()).shortValue()){
										sessao.removeAttribute("idClienteImovelProprietario");
									}
								}

								if(!(colecaoClientesImoveisRemovidos.contains(clienteImovel))){
									colecaoClientesImoveisRemovidos.add(clienteImovel);
								}
								clienteImovelIterator.remove();
							}
						}
					}
				}
			}
		}

		sessao.setAttribute("colecaoClientesImoveisRemovidos", colecaoClientesImoveisRemovidos);

		return retorno;
	}
}
