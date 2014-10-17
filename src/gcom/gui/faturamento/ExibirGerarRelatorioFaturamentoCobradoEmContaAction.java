package gcom.gui.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.Localidade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;


public class ExibirGerarRelatorioFaturamentoCobradoEmContaAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioFaturamentoCobradoEmContaAction");

		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("1"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidadeInicial(actionForm, objetoConsulta);
		}

		// Pesquisar Localidade Final
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("3"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidadeFinal(actionForm, objetoConsulta);
		}

		this.pesquisarGrupoFaturamento(httpServletRequest);
		this.setaRequest(httpServletRequest, actionForm);

		return retorno;

	}

	private void pesquisarLocalidadeInicial(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer loca_i = (Integer) form.get("LOCA_I");

		if(!objetoConsulta.trim().equals("1")){
			loca_i = (Integer) form.get("LOCA_F");
		}

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_i));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("1")){
				form.set("LOCA_I", localidade.getId());
				form.set("nomeLocalidadeInicial", localidade.getDescricao());
			}

			form.set("LOCA_F", localidade.getId());
			form.set("nomeLocalidadeFinal", localidade.getDescricao());

		}else{
			if(objetoConsulta.trim().equals("1")){
				form.set("LOCA_I", null);
				form.set("nomeLocalidadeInicial", "Localidade Inicial inexistente");

				form.set("LOCA_F", null);
				form.set("nomeLocalidadeFinal", null);
			}else{
				form.set("LOCA_F", null);
				form.set("nomeLocalidadeFinal", "Localidade Final inexistente");
			}
		}
	}

	private void pesquisarLocalidadeFinal(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer loca_f = (Integer) form.get("LOCA_F");

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_f));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("3")){
				form.set("LOCA_F", localidade.getId());
				form.set("nomeLocalidadeFinal", localidade.getDescricao());
			}

		}else{
			form.set("LOCA_F", null);
			form.set("nomeLocalidadeFinal", "Localidade Final inexistente");
		}
	}

	private void pesquisarGrupoFaturamento(HttpServletRequest httpServletRequest){

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

		filtroFaturamentoGrupo.setConsultaSemLimites(true);
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoFaturamentoGrupo = this.getFachada().pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		if(!Util.isVazioOrNulo(colecaoFaturamentoGrupo)){

			for(Object object : colecaoFaturamentoGrupo){

				FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) object;
				faturamentoGrupo.setDescricao(faturamentoGrupo.getId() + " - " + faturamentoGrupo.getDescricao());

			}

			httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Grupo Faturamento");
		}
	}

	private void setaRequest(HttpServletRequest httpServletRequest, ActionForm actionForm){

		DynaActionForm form = (DynaActionForm) actionForm;

		// Localidade Inicial
		if(form.get("LOCA_I") != null && !form.get("LOCA_I").equals("") && form.get("nomeLocalidadeInicial") != null
						&& !form.get("nomeLocalidadeInicial").equals("")){

			httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

		}else{

			if(form.get("LOCA_F") != null && !form.get("LOCA_F").equals("") && form.get("nomeLocalidadeFinal") != null
							&& !form.get("nomeLocalidadeFinal").equals("")){

				httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

			}
		}
	}

}
