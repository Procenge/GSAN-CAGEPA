/**
 * 
 */

package gcom.gui.arrecadacao;

import gcom.cadastro.imovel.Imovel;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 * @date 26/09/2011
 */
public class ExibirConsultarAcrescimoImpontualidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarAcrescimoImpontualidade");

		ConsultarAcrescimoImpontualidadeActionForm form = (ConsultarAcrescimoImpontualidadeActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("colecaAcrescimoImpontualidade");

		if(!Util.isVazioOuBranco(form.getIdImovel())){
			Imovel imovel = getFachada().pesquisarImovel(Util.obterInteger(form.getIdImovel()));
			if(imovel != null){
				httpServletRequest.removeAttribute("corInscricao");
				form.setInscricaoImovel(imovel.getInscricaoFormatada());
				if(imovel.getLigacaoAguaSituacao() != null){
					form.setSituacaoAguaImovel(imovel.getLigacaoAguaSituacao().getDescricao());
				}
				if(imovel.getLigacaoEsgotoSituacao() != null){
					form.setSituacaoEsgotoImovel(imovel.getLigacaoEsgotoSituacao().getDescricao());
				}

				Collection colecaAcrescimoImpontualidade = getFachada().pesquisarAcrescimoImpontualidade(imovel.getId());

				if(!Util.isVazioOrNulo(colecaAcrescimoImpontualidade)){
					sessao.setAttribute("colecaAcrescimoImpontualidade", colecaAcrescimoImpontualidade);
				}else{
					httpServletRequest.setAttribute("consultaImpontualidade", "exception");

					form.setIdImovel("");
					form.setInscricaoImovel("");
					httpServletRequest.setAttribute("nomeCampo", "idImovel");
					sessao.setAttribute("colecaAcrescimoImpontualidade", null);
					form.setSituacaoAguaImovel("");
					form.setSituacaoEsgotoImovel("");
					httpServletRequest.setAttribute("consultaImpontualidade", "exception");
					httpServletRequest.setAttribute("msgConsultaAcrescimo", "O imóvel não possui acréscimos por impontualidade.");
				}

			}else{
				httpServletRequest.setAttribute("corInscricao", "exception");
				form.setIdImovel("");
				form.setInscricaoImovel("Matrícula Inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");
				sessao.setAttribute("colecaAcrescimoImpontualidade", null);
				form.setSituacaoAguaImovel("");
				form.setSituacaoEsgotoImovel("");
			}
		}

		return retorno;
	}
}
