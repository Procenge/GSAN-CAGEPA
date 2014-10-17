
package gcom.gui.cobranca;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoSubgrupo;
import gcom.batch.cobranca.RelatorioRelacaoImovelReligacaoEspecialDia;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class RelImovReligacaoEspecialDiaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward exibirFiltrarRelacaoImovelEspecialDia(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;

		if(request.getParameter("limpar") == null){
			this.limparFormulario(dynaForm, mapping, request);
		}

		montarCombos(request);

		// Valida Enter
		this.pesquisarUnidadeOrganizacional(dynaForm, request);

		return mapping.findForward("mesmaPagina");
	}

	public ActionForward gerarRelatorioImoveisReligacaoEspecial(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		ActionForward retorno = null;

		DynaActionForm dynaForm = (DynaActionForm) form;

		Integer idUnidade = null;
		Date periodoInicio = null;
		Date periodoFim = null;
		Integer idGrupo = null;
		String[] setoresSelecionado = null;
		String[] bairrosSelecionado = null;
		String[] tiposServicoSelecionado = null;

		if(dynaForm.get("idUnidade") != null && !"".equals(dynaForm.get("idUnidade"))){
			idUnidade = Integer.valueOf(dynaForm.get("idUnidade").toString());
		}

		if(dynaForm.get("periodoInicio") != null && !"".equals(dynaForm.get("periodoInicio"))){
			periodoInicio = Util.converteStringParaDate(dynaForm.get("periodoInicio").toString());
		}

		if(dynaForm.get("periodoFim") != null && !"".equals(dynaForm.get("periodoFim"))){
			periodoFim = Util.converteStringParaDate(dynaForm.get("periodoFim").toString());
		}

		if(dynaForm.get("grupo") != null && !"".equals(dynaForm.get("grupo")) && !"-1".equals(dynaForm.get("grupo"))){
			idGrupo = Integer.valueOf(dynaForm.get("grupo").toString());
		}

		if(dynaForm.get("setorSelecionado") != null && !"".equals(dynaForm.get("setorSelecionado"))
						&& !"-1".equals(dynaForm.get("setorSelecionado"))){
			if(Util.isVazioOuBranco(((String[]) dynaForm.get("setorSelecionado"))[0])
							|| (((String[]) dynaForm.get("setorSelecionado"))[0]).equalsIgnoreCase("")
							|| (((String[]) dynaForm.get("setorSelecionado"))[0]).equalsIgnoreCase("null")){
				setoresSelecionado = null;
			}else{
				setoresSelecionado = (String[]) dynaForm.get("setorSelecionado");
				setoresSelecionado = setoresSelecionado[0].split(",");
			}
		}

		if(dynaForm.get("bairrosSelecionado") != null && !"".equals(dynaForm.get("bairrosSelecionado"))
						&& !"-1".equals(dynaForm.get("bairrosSelecionado"))){
			if(Util.isVazioOuBranco(((String[]) dynaForm.get("bairrosSelecionado"))[0])
							|| (((String[]) dynaForm.get("bairrosSelecionado"))[0]).equalsIgnoreCase("")
							|| (((String[]) dynaForm.get("bairrosSelecionado"))[0]).equalsIgnoreCase("null")){
				bairrosSelecionado = null;
			}else{
				bairrosSelecionado = (String[]) dynaForm.get("bairrosSelecionado");
				bairrosSelecionado = bairrosSelecionado[0].split(",");
			}
		}

		if(dynaForm.get("servicoTipoSelecionado") != null && !"".equals(dynaForm.get("servicoTipoSelecionado"))
						&& !"-1".equals(dynaForm.get("servicoTipoSelecionado"))){
			if(Util.isVazioOuBranco(((String[]) dynaForm.get("servicoTipoSelecionado"))[0])
							|| (((String[]) dynaForm.get("servicoTipoSelecionado"))[0]).equalsIgnoreCase("")
							|| (((String[]) dynaForm.get("servicoTipoSelecionado"))[0]).equalsIgnoreCase("null")){
				tiposServicoSelecionado = null;
			}else{
				tiposServicoSelecionado = (String[]) dynaForm.get("servicoTipoSelecionado");
				tiposServicoSelecionado = tiposServicoSelecionado[0].split(",");
			}
		}

		// Valida Enter
		this.pesquisarUnidadeOrganizacional(dynaForm, request);

		montarCombos(request);

		// Valida os dados da tela
		this.validarDatas(dynaForm);

		try{
			File template = new File(request.getRealPath("") + "\\xls\\relatorioRelacaoImoveisReligacaoEspecialDiaTemplate.xls");

			RelatorioRelacaoImovelReligacaoEspecialDia relatorio = new RelatorioRelacaoImovelReligacaoEspecialDia((Usuario) (request
							.getSession(false)).getAttribute("usuarioLogado"));

			relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_XLS));
			relatorio.addParametro("idUnidade", idUnidade);
			relatorio.addParametro("periodoInicio", periodoInicio);
			relatorio.addParametro("periodoFim", periodoFim);
			relatorio.addParametro("grupo", idGrupo);
			relatorio.addParametro("setor", setoresSelecionado);
			relatorio.addParametro("bairro", bairrosSelecionado);
			relatorio.addParametro("servicoTipo", tiposServicoSelecionado);
			relatorio.addParametro("template", template);

			retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, request, response, mapping);

		}catch(SistemaException ex){
			reportarErros(request, "erro.sistema");
			retorno = mapping.findForward("telaErroPopup");
		}catch(RelatorioVazioException ex1){
			reportarErros(request, "atencao.relatorio.vazio");
			retorno = mapping.findForward("telaAtencaoPopup");
		}
		return retorno;
	}

	/**
	 * Monta combos utilizados na JSP
	 * 
	 * @param request
	 */
	private void montarCombos(HttpServletRequest request){

		Collection colecaoServicoTipo = null;
		Collection colecaoCobrancaGrupo = null;
		Collection colecaoSetorComercial = null;
		Collection colecaoBairro = null;

		Fachada fachada = Fachada.getInstancia();

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoSubgrupo");
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.SERVICOTIPOSUBGRUPO, ServicoTipoSubgrupo.RELIGACAO));
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);
		colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		request.setAttribute("colecaoServicoTipo", colecaoServicoTipo);

		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);
		colecaoCobrancaGrupo = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());

		request.setAttribute("colecaoCobrancaGrupo", colecaoCobrancaGrupo);

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSetorComercial.setCampoOrderBy(FiltroSetorComercial.DESCRICAO);
		colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		request.setAttribute("colecaoSetorComercial", colecaoSetorComercial);

		FiltroBairro filtroBairro = new FiltroBairro();
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroBairro.setCampoOrderBy(FiltroBairro.NOME);
		colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

		request.setAttribute("colecaoBairro", colecaoBairro);

	}

	/**
	 * @param dynaForm
	 * @param request
	 */
	private void limparFormulario(DynaActionForm dynaForm, ActionMapping mapping, HttpServletRequest request){

		dynaForm.reset(mapping, request);
		dynaForm.set("idUnidade", null);
		dynaForm.set("nomeUnidade", null);
		dynaForm.set("periodoInicio", null);
		dynaForm.set("periodoFim", null);
		dynaForm.set("grupo", null);
		dynaForm.set("setor", null);
		dynaForm.set("bairro", null);
		dynaForm.set("servicoTipo", null);
		dynaForm.set("bairrosSelecionado", null);
		dynaForm.set("setorSelecionado", null);
		dynaForm.set("servicoTipoSelecionado", null);
	}

	/**
	 * @author isilva
	 * @param form
	 */
	private void validarDatas(ActionForm form){

		DynaActionForm dynaForm = (DynaActionForm) form;

		String periodoInicioString = null;
		String periodoFimString = null;

		if(dynaForm.get("periodoInicio") != null && !"".equals(dynaForm.get("periodoInicio"))){
			periodoInicioString = dynaForm.get("periodoInicio").toString();
		}

		if(dynaForm.get("periodoFim") != null && !"".equals(dynaForm.get("periodoFim"))){
			periodoFimString = dynaForm.get("periodoFim").toString();
		}

		if(Util.isVazioOuBranco(periodoInicioString) && Util.isVazioOuBranco(periodoInicioString)){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		Date periodoInicio = null;
		Date periodoFim = null;

		// Data Inicial
		if(Util.isVazioOuBranco(periodoInicioString)){
			throw new ActionServletException("atencao.required", null, "Período Inicial");
		}else{

			try{
				// Data Inicial
				if(!Util.validaDataLinear(periodoInicioString)){
					throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
				}
				periodoInicio = Util.converteStringParaDate(periodoInicioString);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
			}

		}

		// Data Final
		if(Util.isVazioOuBranco(periodoFimString)){
			throw new ActionServletException("atencao.required", null, "Período Final");
		}else{

			try{
				// Data Final
				if(!Util.validaDataLinear(periodoFimString)){
					throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
				}
				periodoFim = Util.converteStringParaDate(periodoFimString);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
			}

		}

		// Se data inicio maior que data fim
		if(Util.compararData(periodoInicio, periodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", periodoInicioString, periodoFimString);
		}

		if(dynaForm.get("servicoTipo") == null || "".equals(dynaForm.get("servicoTipo")) || "-1".equals(dynaForm.get("servicoTipo"))){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Serviço");
		}
	}

	/**
	 * @author isilva
	 * @param form
	 * @param request
	 */
	private void pesquisarUnidadeOrganizacional(DynaActionForm form, HttpServletRequest request){

		String idDigitadoEnterUnidade = null;

		if(form.get("idUnidade") != null && !"".equals(form.get("idUnidade"))){
			idDigitadoEnterUnidade = form.get("idUnidade").toString();
		}

		// Verifica se o código da Unidade foi digitado
		if(idDigitadoEnterUnidade != null && !idDigitadoEnterUnidade.trim().equalsIgnoreCase("")
						&& Integer.parseInt(idDigitadoEnterUnidade) > 0){
			FiltroUnidadeOrganizacional filtroUnidade = new FiltroUnidadeOrganizacional();

			filtroUnidade.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idDigitadoEnterUnidade));

			UnidadeOrganizacional unidadeEncontrada = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroUnidade, UnidadeOrganizacional.class.getName()));

			if(unidadeEncontrada != null){
				// a unidade de Unidade Empresa foi encontrado
				form.set("idUnidade", unidadeEncontrada.getId().toString());
				form.set("nomeUnidade", unidadeEncontrada.getDescricao());
				request.setAttribute("idUnidadeEncontrada", "true");
			}else{
				form.set("idUnidade", "");
				form.set("nomeUnidade", "UNIDADE ORGANIZACIONAL INEXISTENTE");
				request.setAttribute("idUnidadeEncontrada", "exception");
			}
		}
		// -------Fim de parte que trata do código quando o usuário tecla enter
	}

}
