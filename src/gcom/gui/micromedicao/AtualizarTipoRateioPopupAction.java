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

package gcom.gui.micromedicao;

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRateioTipo;
import gcom.micromedicao.RateioTipo;
import gcom.micromedicao.hidrometro.FiltroHidrometroInstalacaoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Atualizar Tipo de Rateio
 * 
 * @author Rafael Santos
 * @since 12/01/2006
 */
public class AtualizarTipoRateioPopupAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucessoPopup");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarTipoRateioPopupActionForm atualizarTipoRateioPopupAction = (AtualizarTipoRateioPopupActionForm) actionForm;

		String tipoRateioLigacaoAgua = atualizarTipoRateioPopupAction.getRateioTipoAgua();

		String tipoRateioLigacaoPoco = atualizarTipoRateioPopupAction.getRateioTipoPoco();
		Imovel imovel = null;
		if(sessao.getAttribute("imovelVinculado") != null){
			imovel = (Imovel) sessao.getAttribute("imovelVinculado");
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoAgua = null;
			HidrometroInstalacaoHistorico hidrometroInstalacaoHistoricoPoco = null;
			if(tipoRateioLigacaoAgua != null && !tipoRateioLigacaoAgua.equals("")){

				FiltroRateioTipo filtroRateioTipo = new FiltroRateioTipo();

				filtroRateioTipo.adicionarParametro(new ParametroSimples(FiltroRateioTipo.ID, tipoRateioLigacaoAgua));
				filtroRateioTipo.adicionarParametro(new ParametroSimples(FiltroRateioTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				// Rateio Tipo Agua
				RateioTipo rateioTipo = (RateioTipo) ((List) (fachada.pesquisar(filtroRateioTipo, RateioTipo.class.getName()))).get(0);

				FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();
				filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroHidrometroInstalacaoHistorico.LIGACAO_AGUA_ID, imovel.getId()));

				Collection colecaoHidrometroInstalacaoHistorico = fachada.pesquisar(filtroHidrometroInstalacaoHistorico,
								HidrometroInstalacaoHistorico.class.getName());

				if(colecaoHidrometroInstalacaoHistorico != null && !colecaoHidrometroInstalacaoHistorico.isEmpty()){
					hidrometroInstalacaoHistoricoAgua = (HidrometroInstalacaoHistorico) colecaoHidrometroInstalacaoHistorico.iterator()
									.next();
					hidrometroInstalacaoHistoricoAgua.setRateioTipo(rateioTipo);
					// hidrometroInstalacaoHistoricoAgua.setImovel(imovel);
				}

			}

			if(tipoRateioLigacaoPoco != null && !tipoRateioLigacaoPoco.equals("")){

				FiltroRateioTipo filtroRateioTipo = new FiltroRateioTipo();

				filtroRateioTipo.adicionarParametro(new ParametroSimples(FiltroRateioTipo.ID, tipoRateioLigacaoPoco));
				filtroRateioTipo.adicionarParametro(new ParametroSimples(FiltroRateioTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Rateio Tipo Poco
				RateioTipo rateioTipo = (RateioTipo) ((List) (fachada.pesquisar(filtroRateioTipo, RateioTipo.class.getName()))).get(0);

				FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();
				filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(FiltroHidrometroInstalacaoHistorico.IMOVEL_ID,
								imovel.getId()));

				Collection colecaoHidrometroInstalacaoHistorico = fachada.pesquisar(filtroHidrometroInstalacaoHistorico,
								HidrometroInstalacaoHistorico.class.getName());

				if(colecaoHidrometroInstalacaoHistorico != null && !colecaoHidrometroInstalacaoHistorico.isEmpty()){
					hidrometroInstalacaoHistoricoPoco = (HidrometroInstalacaoHistorico) colecaoHidrometroInstalacaoHistorico.iterator()
									.next();
					hidrometroInstalacaoHistoricoPoco.setRateioTipo(rateioTipo);
				}
			}

			/**
			 * alterado por pedro alexandre dia 19/11/2006
			 * Recupera o usu�rio logado para passar no met�do de atualizar tipo de rateio
			 * para verificar se o usu�rio tem abrang�ncia para atualizar o tipo de rateio
			 * informado.
			 */
			Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
			// Atualizar Tipo Rateio
			fachada.atualizarTipoRateio(imovel, hidrometroInstalacaoHistoricoAgua, hidrometroInstalacaoHistoricoPoco, usuarioLogado);
			/*
			 * fachada.atualizarTipoRateio(imovel,
			 * hidrometroInstalacaoHistoricoAgua,
			 * hidrometroInstalacaoHistoricoPoco);
			 */

		}

		httpServletRequest.setAttribute("fechar", "true");

		// liberar da sessao
		if(sessao.getAttribute("imovelVinculado") != null){
			sessao.removeAttribute("imovelVinculado");
		}

		// Monta a p�gina de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucessoPopup")){
			montarPaginaSucesso(httpServletRequest, "Tipo de Rateio do im�vel condom�nio de matr�cula " + imovel.getId()
							+ " atualizado com sucesso.", "", "");
		}

		return retorno;
	}
}
