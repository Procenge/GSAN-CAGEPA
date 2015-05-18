
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroOsProgramNaoEncerMotivo;
import gcom.atendimentopublico.ordemservico.OsProgramNaoEncerMotivo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemoverOsProgramNaoEncerMotivoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		if(ids == null || ids.length == 0){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		for(String idOsProgramNaoEncerMotivo : ids){
			FiltroOsProgramNaoEncerMotivo filtroOsProgramNaoEncerMotivo = new FiltroOsProgramNaoEncerMotivo();
			filtroOsProgramNaoEncerMotivo.adicionarParametro(new ParametroSimples(FiltroOsProgramNaoEncerMotivo.ID,
							idOsProgramNaoEncerMotivo));

			OsProgramNaoEncerMotivo osProgramNaoEncerMotivo = (OsProgramNaoEncerMotivo) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroOsProgramNaoEncerMotivo, OsProgramNaoEncerMotivo.class.getName()));

			fachada.remover(osProgramNaoEncerMotivo);
			osProgramNaoEncerMotivo = null;

		}

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " Motivo do não encerramento da OS removidas com sucesso",
							"Realizar outra Manutenção de Motivo do não encerramento da OS",
							"exibirFiltrarOsProgramNaoEncerMotivoAction.do?menuPrincipal=sim&menu=sim");
		}

		return retorno;
	}
}
