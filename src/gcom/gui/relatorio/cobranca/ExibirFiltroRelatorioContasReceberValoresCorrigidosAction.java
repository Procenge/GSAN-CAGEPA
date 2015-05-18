
package gcom.gui.relatorio.cobranca;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltroRelatorioContasReceberValoresCorrigidosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltrorRelContasReceberValorCorrigido");

		preencherImovelPesquisa(actionForm);

		return retorno;
	}

	private void preencherImovelPesquisa(ActionForm actionForm){

		RelatorioContasReceberValoresCorrigidosForm form = (RelatorioContasReceberValoresCorrigidosForm) actionForm;

		if(form.getMatriculaImovel() == null || form.getMatriculaImovel().trim().equals("")){
			return;

		}

		FiltroImovel filtroImovel = new FiltroImovel();

		// coloca parametro no filtro
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, new Integer(form.getMatriculaImovel())));

		// pesquisa
		Collection coll = Fachada.getInstancia().pesquisar(filtroImovel, Imovel.class.getName());
		form.setInscricaoImovel("IMÓVEL INEXISTENTE");

		if(coll != null && !coll.isEmpty()){
			form.setInscricaoImovel((((Imovel) ((List) coll).get(0)).getInscricaoFormatada()));

		}

	}
}
