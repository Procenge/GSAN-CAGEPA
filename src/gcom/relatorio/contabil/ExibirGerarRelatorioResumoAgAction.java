package gcom.relatorio.contabil;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.*;
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

public class ExibirGerarRelatorioResumoAgAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioResumoAgAction");

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("1"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(actionForm, objetoConsulta);
		}

		this.pesquisarGerenciaRegional(httpServletRequest);
		// Pesquisar Municipio
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("8"))){
			this.pesquisarMunicipio(actionForm, objetoConsulta);
		}

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, actionForm);

		return retorno;
	}

	private void pesquisarMunicipio(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer municipio_id = (Integer) form.get("psCodMunicipio");

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
		filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, municipio_id));

		// Recuperar Municipio
		Collection colecaoMunicipio = this.getFachada().pesquisar(filtroMunicipio, Municipio.class.getName());
		if(!Util.isVazioOrNulo(colecaoMunicipio)){
			Municipio municipio = (Municipio) Util.retonarObjetoDeColecao(colecaoMunicipio);
			form.set("psCodMunicipio", municipio.getId());
			form.set("descricaoMunicipio", municipio.getNome());
		}

	}

	private void pesquisarLocalidade(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer psCodLocalidade = Integer.parseInt((String) form.get("psCodLocalidade"));

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, psCodLocalidade));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("1")){
				form.set("psCodLocalidade", localidade.getId().toString());
				form.set("nomeLocalidade", localidade.getDescricao());
			}

		}else{
			if(objetoConsulta.trim().equals("1")){
				form.set("psCodLocalidade", null);
				form.set("nomeLocalidade", "Localidade inexistente");
			}
		}
	}

	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.ID);
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(!Util.isVazioOrNulo(colecaoGerenciaRegional)){

			for(Object object : colecaoGerenciaRegional){

				GerenciaRegional gerenciaRegional = (GerenciaRegional) object;
				gerenciaRegional.setNome(gerenciaRegional.getId() + " - " + gerenciaRegional.getNome());

			}

			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Gerência Regional");
		}

	}

	private void setaRequest(HttpServletRequest httpServletRequest, ActionForm actionForm){

		DynaActionForm form = (DynaActionForm) actionForm;

		// Localidade
		if(form.get("psCodLocalidade") != null && !form.get("psCodLocalidade").equals("") && form.get("nomeLocalidade") != null
						&& !form.get("nomeLocalidade").equals("")){

			httpServletRequest.setAttribute("localidadeEncontrada", "true");

		}

	}


}