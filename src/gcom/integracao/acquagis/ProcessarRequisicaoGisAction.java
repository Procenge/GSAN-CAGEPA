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

package gcom.integracao.acquagis;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável por recuperar os dados do AcquaGis
 * 
 * @author Virgínia Melo
 * @created 06 de Julho de 2009
 */
public class ProcessarRequisicaoGisAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
					HttpServletResponse response){

		HttpSession sessao = request.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		// Recuperar dados do AcquaGis
		DadosAcquaGis dadosAcquaGis = this.recuperarDadosAcquaGis(request);

		// Validar os dados recebidos
		Integer codigoRetorno = fachada.validarDadosAcquaGis(dadosAcquaGis);

		// Criar um map com nome do usuário e dados
		ServletContext context = sessao.getServletContext();

		// Dados do AcquaGis que serão usados ao cadastrar uma RA.
		context.setAttribute(dadosAcquaGis.getLoginUsuario(), dadosAcquaGis);

		try{
			response.getWriter().print(codigoRetorno);

		}catch(IOException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * Método responsável por recuperar os dados recebidos pelo AcquaGis.
	 * 
	 * @author Saulo Lima
	 * @date 21/09/2009
	 *       Novo atributo idImovel
	 * @param request
	 * @return DadosAcquaGis
	 */
	private DadosAcquaGis recuperarDadosAcquaGis(HttpServletRequest request){

		// Recuperando dados do AcquaGis que virão por request.
		String loginUsuario = (String) request.getParameter("usur_nmlogin");
		String idLogradouroBairro = (String) request.getParameter("lgbr_id");
		String idLogradouroCep = (String) request.getParameter("lgcp_id");
		String descricaoPontoReferencia = (String) request.getParameter("rgat_dspontoreferencia");
		String descricaoComplemento = (String) request.getParameter("rgat_dscomplementondereco");
		String numeroImovel = (String) request.getParameter("rgat_nnimovel");
		String numeroCoordenadaNorte = (String) request.getParameter("rgat_nncoordenadanorte");
		String numeroCoordenadaLeste = (String) request.getParameter("rgat_nncoordenadaleste");
		String idEdificacaoReferencia = (String) request.getParameter("edrf_id");
		String idImovel = (String) request.getParameter("imov_id");

		// Montar objeto DadosAcquaGis
		DadosAcquaGis dadosAcquaGis = new DadosAcquaGis(loginUsuario, idLogradouroBairro, idLogradouroCep, descricaoPontoReferencia,
						descricaoComplemento, numeroImovel, numeroCoordenadaNorte, numeroCoordenadaLeste, idEdificacaoReferencia, idImovel);

		return dadosAcquaGis;
	}
}
