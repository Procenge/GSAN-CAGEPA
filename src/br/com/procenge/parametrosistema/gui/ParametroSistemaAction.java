/**
 * 
 */

package br.com.procenge.parametrosistema.gui;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.seguranca.sistema.ParametroSistemaActionForm;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.parametrosistema.api.ParametroSistema;
import br.com.procenge.parametrosistema.impl.ParametroSistemaImpl;
import br.com.procenge.parametrosistema.impl.ParametroSistemaValor;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;
import br.com.procenge.util.StrutsDispatchAction;

/**
 * @author gmatos
 */
public class ParametroSistemaAction
				extends StrutsDispatchAction {

	private static final String ATRIBUTO_USO = "uso";

	private static final String ATRIBUTO_TIPO = "tipo";

	private static final String ATRIBUTO_VALOR = "valor";

	private static final String ATRIBUTO_NOVO_VALOR = "novoValor";

	private static final String ATRIBUTO_DESCR_VALOR = "descricaoValor";

	private static final String ATRIBUTO_VERSAO = "versao";

	private static final String CAMPO_CHAVE_PRIMARIA = "chavePrimaria";

	private static final String CAMPO_STR_ULTIMA_ALTERACAO = "strUltimaAlteracao";

	private static final String ATRIBUTO_PARAMETRO = "parametro";

	private static final String ATRIBUTO_PARAMETROS = "parametros";

	// novo
	private static final String ATRIBUTO_VALOR_EXCLUIDO = "deletaValor";

	/**
	 * Método responsável por consultar os parametros do sistema.
	 * 
	 * @autor gilberto
	 * @param mapping
	 * @param form
	 *            O formulário
	 * @param request
	 *            O objeto request
	 * @param response
	 *            O objeto response
	 * @return ActionForward O retorno da ação
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	public ActionForward consultarParametroSistema(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		// List<ParametroSistema> parametros = fachada.consultarParametroSistema();
		// request.setAttribute(ATRIBUTO_PARAMETROS, parametros);
		//
		// return mapping.findForward(FORWARD_SUCESSO);

		ActionForward retorno = mapping.findForward("exibirConsultarParametroSistema");

		HttpSession sessao = request.getSession(false);

		return retorno;
	}

	public ActionForward filtrarParametroSistema(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		ActionForward retorno = mapping.findForward("exibirParametroSistemaFiltrado");

		ParametroSistemaActionForm actionForm = (ParametroSistemaActionForm) form;

		HttpSession sessao = request.getSession(false);
		String codigo;
		String tipoPesquisaCodigo;
		String valor;
		String tipoPesquisaValor;
		String indicadorUso;

		if(actionForm.getCodigo() == null){
			actionForm = (ParametroSistemaActionForm) sessao.getAttribute("formParametro");
		}

		sessao.removeAttribute("formParametro");
		codigo = actionForm.getCodigo().toUpperCase();
		tipoPesquisaCodigo = actionForm.getTipoPesquisaCodigo();
		valor = actionForm.getValor().toUpperCase();
		tipoPesquisaValor = actionForm.getTipoPesquisaValor();
		indicadorUso = actionForm.getUso();

		sessao.setAttribute("formParametro", actionForm);

		List<ParametroSistema> parametros = fachada.consultarParametroSistema();

		List<ParametroSistema> parametroSistemasDeletar = new ArrayList<ParametroSistema>();

		for(ParametroSistema parametroSistema : parametros){
			if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
				if(parametroSistema.getUso() != Integer.parseInt(indicadorUso)){
					parametroSistemasDeletar.add(parametroSistema);
					continue;
				}
			}
			if(codigo != null && !codigo.trim().equalsIgnoreCase("")){
				if(tipoPesquisaCodigo == null || tipoPesquisaCodigo.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					if(!parametroSistema.getCodigo().toUpperCase().contains(codigo)){
						parametroSistemasDeletar.add(parametroSistema);
						continue;
					}
				}else if(tipoPesquisaCodigo.equals(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString())){
					if(!parametroSistema.getCodigo().toUpperCase().startsWith(codigo)){
						parametroSistemasDeletar.add(parametroSistema);
						continue;
					}
				}
			}
			if(valor != null && !valor.trim().equalsIgnoreCase("")){
				if(tipoPesquisaValor == null || tipoPesquisaValor.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
					if(parametroSistema.getValor() == null || !parametroSistema.getValor().toUpperCase().contains(valor)){
						parametroSistemasDeletar.add(parametroSistema);
						continue;
					}
				}else if(tipoPesquisaValor.equals(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString())){
					if(parametroSistema.getValor() == null || !parametroSistema.getValor().toUpperCase().startsWith(valor)){
						parametroSistemasDeletar.add(parametroSistema);
						continue;
					}
				}
			}

		}

		for(ParametroSistema parametroSistema : parametroSistemasDeletar){
			parametros.remove(parametroSistema);
		}

		Collections.sort(parametros);
		request.setAttribute(ATRIBUTO_PARAMETROS, parametros);

		if(parametros == null || parametros.size() == 0){
			// Nenhum parametro encontrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		return retorno;
	}

	/**
	 * Método responsável por obter o parametro do sistema e exibir a tela de alteração.
	 * 
	 * @autor gilberto
	 * @param mapping
	 * @param form
	 *            O formulário
	 * @param request
	 *            O objeto request
	 * @param response
	 *            O objeto response
	 * @return ActionForward O retorno da ação
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	public ActionForward exibirFormAlteracaoParametroSistema(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;
		Long chavePrimaria = (Long) dynaForm.get(CAMPO_CHAVE_PRIMARIA);

		ParametroSistema parametro = fachada.obterParametroSistema(chavePrimaria);

		this.popularForm(form, request, parametro);

		dynaForm.set(ATRIBUTO_TIPO, parametro.getTipoParametroSistema().getCodigo());

		request.setAttribute(ATRIBUTO_PARAMETRO, parametro);
		request.setAttribute("paramValores", fachada.obterParametroSistemaValor(parametro));

		return mapping.findForward(FORWARD_SUCESSO);
	}

	/**
	 * Método responsável por alterar os dados do parâmetro.
	 * 
	 * @autor gilberto
	 * @param mapping
	 * @param form
	 *            O formulário
	 * @param request
	 *            O objeto request
	 * @param response
	 *            O objeto response
	 * @return ActionForward O retorno da ação
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	public ActionForward alterarParametroSistema(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;
		Long chavePrimaria = (Long) dynaForm.get(CAMPO_CHAVE_PRIMARIA);

		// Valida o token para garantir que não aconteça um duplo submit.
		// validarToken(request);

		HttpSession sessao = request.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		Fachada fachada = Fachada.getInstancia();

		ParametroSistema parametro = (ParametroSistema) fachada.obterParametroSistema(chavePrimaria);

		this.popularObjeto(parametro, form);

		// Validação para o campo Descrição
		if(parametro.getDescricao() == null || parametro.getDescricao().trim().equals("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", "Descrição");
		}

		fachada.alterarParametroSistema(parametro, usuarioLogado);

		super.exibirMensagem(request, Constantes.SUCESSO_ENTIDADE_ALTERADA,
						MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.ENTIDADE_ROTULO_PARAMETRO_SISTEMA));

		return mapping.findForward(FORWARD_SUCESSO);
	}

	/**
	 * Método responsável por popular os dados do objeto com os dados do formulário
	 * 
	 * @param parametroSistema
	 *            O parâmetro do sistema
	 * @param form
	 *            O formulário
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	private void popularObjeto(ParametroSistema parametroSistema, ActionForm form) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;
		Integer versao = (Integer) dynaForm.get(ATRIBUTO_VERSAO);

		if(versao != null){
			parametroSistema.setVersao(versao.intValue());
		}

		if(dynaForm.get(ATRIBUTO_USO) == null){
			dynaForm.set(ATRIBUTO_USO, 2);
		}

		parametroSistema.setUltimaAlteracao(new Date());
		PropertyUtils.setProperty(parametroSistema, "tipoParametroSistema.codigo", dynaForm.get("tipo"));

		PropertyUtils.copyProperties(parametroSistema, dynaForm);
	}

	/**
	 * Método responsável por popular o formulário com os dados do objeto
	 * 
	 * @param form
	 *            O formulário
	 * @param request
	 *            O request
	 * @param parametroSistema
	 *            O parâmetro do sistema
	 * @throws Exception
	 *             Caso ocorra algum erro
	 */
	private void popularForm(ActionForm form, HttpServletRequest request, ParametroSistema parametroSistema) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;
		Date ultimaAlteracao = null;

		// Se não for um postback então os dados do formulário tem que ser preenchido com os dados
		// do objeto.
		if(!super.isPostBack(request)){
			ultimaAlteracao = parametroSistema.getUltimaAlteracao();
			PropertyUtils.copyProperties(dynaForm, parametroSistema);
			dynaForm.set(CAMPO_STR_ULTIMA_ALTERACAO, String.valueOf(ultimaAlteracao));
		}
	}

	public ActionForward inserirParametroSistemaValor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;

		// Valida o token para garantir que não aconteça um duplo submit.
		// validarToken(request);

		HttpSession sessao = request.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
		// -------- RESGATA VALORES ------------
		Long idParamSistema = (Long) dynaForm.get(CAMPO_CHAVE_PRIMARIA);
		String descricao = (String) dynaForm.get(ATRIBUTO_DESCR_VALOR);
		String valor = (String) dynaForm.get(ATRIBUTO_NOVO_VALOR);
		if(!valor.equals("")){
			Fachada fachada = Fachada.getInstancia();

			ParametroSistemaValor paramValor = new ParametroSistemaValor();

			paramValor.setParametroSistema(new ParametroSistemaImpl());
			paramValor.getParametroSistema().setChavePrimaria(idParamSistema);
			paramValor.setDescricao(descricao);
			paramValor.setValor(valor);

			// validação
			Filtro filtroParametroSistemaValor = new Filtro() {};
			filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("parametroSistema.chavePrimaria", idParamSistema));
			filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("valor", valor));

			Collection paramsValores = fachada.pesquisar(filtroParametroSistemaValor, ParametroSistemaValor.class.getName());
			ParametroSistemaValor paramValorIgual = (ParametroSistemaValor) Util.retonarObjetoDeColecao(paramsValores);

			if(paramValor.getValor() == null || paramValor.getValor().trim().equals("")){
				throw new ActionServletException("atencao.informe_campo", "o valor do parâmetro");
			}

			if(paramValorIgual == null){
				try{
					fachada.inserirParametroSistemaValor(paramValor, usuarioLogado);
				}finally{
					ParametroSistema parametro = new ParametroSistemaImpl();
					parametro.setChavePrimaria(idParamSistema);
					request.setAttribute("paramValores", fachada.obterParametroSistemaValor(parametro));
				}
				return mapping.findForward("exibirFormAlteracaoParametroSistema");
			}else{
				throw new ActionServletException("atencao.duplicado.parametro.sistema.valor");
			}
		}else{
			throw new ActionServletException("atencao.nulo.parametro.sistema.valor");
		}

	}

	public ActionForward exibirParametroSistemaValor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		String idParamSistema = (String) request.getParameter(CAMPO_CHAVE_PRIMARIA);
		String valor = (String) request.getParameter(ATRIBUTO_VALOR);

		Filtro filtroParametroSistemaValor = new Filtro() {};
		filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("parametroSistema.chavePrimaria", idParamSistema));
		filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("valor", valor));

		Collection paramsValores = fachada.pesquisar(filtroParametroSistemaValor, ParametroSistemaValor.class.getName());
		ParametroSistemaValor paramValor = (ParametroSistemaValor) Util.retonarObjetoDeColecao(paramsValores);

		request.setAttribute(CAMPO_CHAVE_PRIMARIA, idParamSistema);
		request.setAttribute(ATRIBUTO_VALOR, paramValor.getValor());
		if(paramValor.getDescricao() == null) request.setAttribute(ATRIBUTO_DESCR_VALOR, "");
		else request.setAttribute(ATRIBUTO_DESCR_VALOR, paramValor.getDescricao());
		// this.popularFormValor(form, request, paramValor);

		return mapping.findForward("popup");
	}

	public ActionForward atualizarParametroSistemaValor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		String idParamSistema = (String) request.getParameter(CAMPO_CHAVE_PRIMARIA);
		String valor = (String) request.getParameter(ATRIBUTO_VALOR);

		Filtro filtroParametroSistemaValor = new Filtro() {};
		filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("parametroSistema.chavePrimaria", idParamSistema));
		filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("valor", valor));

		Collection paramsValores = fachada.pesquisar(filtroParametroSistemaValor, ParametroSistemaValor.class.getName());
		ParametroSistemaValor paramValor = (ParametroSistemaValor) Util.retonarObjetoDeColecao(paramsValores);

		if(paramValor == null){
			throw new PCGException(Constantes.RESOURCE_BUNDLE, Constantes.ERRO_REG_INEXISTENTE);
		}

		if(request.getParameter(ATRIBUTO_DESCR_VALOR).trim().length() >= 255){
			throw new ActionServletException("atencao.maxlength", "A Descrição do Valor", "255");
		}

		paramValor.setDescricao((String) request.getParameter(ATRIBUTO_DESCR_VALOR).trim());
		HttpSession sessao = request.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);
		fachada.atualizarParametroSistemaValor(paramValor, usuarioLogado);
		request.setAttribute("flagClose", "true");
		return mapping.findForward("popup");
	}

	public ActionForward excluirParametroSistemaValor(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;

		// Valida o token para garantir que não aconteça um duplo submit.
		// validarToken(request);

		Long idParamSistema = (Long) dynaForm.get(CAMPO_CHAVE_PRIMARIA);
		String valor = (String) request.getParameter(ATRIBUTO_VALOR_EXCLUIDO);

		Filtro filtroParametroSistemaValor = new Filtro() {};
		filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("parametroSistema.chavePrimaria", idParamSistema));
		if(StringUtils.isEmpty(valor)){
			filtroParametroSistemaValor.adicionarParametro(new ParametroNulo("valor"));
		}else{
			filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("valor", valor));
		}

		Collection paramsValores = fachada.pesquisar(filtroParametroSistemaValor, ParametroSistemaValor.class.getName());
		ParametroSistemaValor paramValor = (ParametroSistemaValor) Util.retonarObjetoDeColecao(paramsValores);

		try{
			if(paramValor == null){
				throw new PCGException(Constantes.RESOURCE_BUNDLE, Constantes.ERRO_REG_INEXISTENTE);
			}
			HttpSession sessao = request.getSession(false);
			Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

			fachada.excluirParametroSistemaValor(paramValor, usuarioLogado);
		}finally{
			ParametroSistema parametro = new ParametroSistemaImpl();
			parametro.setChavePrimaria(idParamSistema);
			request.setAttribute("paramValores", fachada.obterParametroSistemaValor(parametro));
		}

		return mapping.findForward("exibirFormAlteracaoParametroSistema");
	}

}
