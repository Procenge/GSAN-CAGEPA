
package gcom.gui.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cobranca.CobrancaAcaoAtividade;
import gcom.cobranca.bean.EmissaoOSCobrancaHelper;
import gcom.cobranca.bean.EmissaoOSCobrancaQuadraHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class EmitirOSCobrancaAction
				extends GcomAction {

	private final String MESMA_PAGINA = "mesmaPagina";

	private final String OS_SELECIONADAS = "OSSelecionadas";

	private final String AGENTE_SELECIONADO = "agenteSelecionado";

	private final String LISTA_OS_FILTRADA = "listaOSFiltrada";

	private final String EMPRESAS_ASSOCIADAS = "empresasAssociadas";

	public ActionForward exibirEmissaoOSCobranca(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		Collection<Empresa> colecaoEmpresa = getEmpresas();
		request.getSession().setAttribute("colecaoEmpresa", colecaoEmpresa);
		Integer idComando = Integer.valueOf(request.getParameter("idComando"));
		String comando = request.getParameter("tipoComando");

		request.getSession().setAttribute("idComando", idComando);
		request.getSession().setAttribute("tipoComando", comando);

		CobrancaAcaoAtividade cobrancaAtividade = null;

		if("cronograma".equals(comando)){
			cobrancaAtividade = CobrancaAcaoAtividade.CRONOGRAMA;
		}else{
			cobrancaAtividade = CobrancaAcaoAtividade.COMANDO;
		}

		List<EmissaoOSCobrancaHelper> listaOS = (List<EmissaoOSCobrancaHelper>) Fachada.getInstancia().pesquisarOS(idComando,
						cobrancaAtividade);
		request.getSession().setAttribute(LISTA_OS_FILTRADA, listaOS);

		return mapping.findForward(MESMA_PAGINA);
	}

	public ActionForward associarOSCobranca(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		DynaActionForm dynaForm = (DynaActionForm) form;
		String[] osSelecionadas = (String[]) dynaForm.get(OS_SELECIONADAS);
		Integer idEmpresa = (Integer) dynaForm.get(AGENTE_SELECIONADO);

		contabilizaNumeroOSSelecionadas(request, idEmpresa, osSelecionadas);

		if(idEmpresa == null){
			throw new ActionServletException("atencao.cobranca.nenhum_agente_selecionado", null, "Empresa");
		}

		EmissaoOSCobrancaHelper[] itensSelecionados = montaArrayOSCobranca(osSelecionadas);

		List<Empresa> empresas = (List<Empresa>) request.getSession().getAttribute("colecaoEmpresa");

		Empresa _empresa = new Empresa();
		_empresa.setId(idEmpresa);

		Empresa empresa = empresas.get(empresas.indexOf(_empresa));

		List<EmissaoOSCobrancaHelper> listaAtualizada = atualizaListaOS((List<EmissaoOSCobrancaHelper>) request.getSession().getAttribute(
						LISTA_OS_FILTRADA), itensSelecionados, empresa);
		request.getSession().setAttribute(LISTA_OS_FILTRADA, listaAtualizada);

		Object associacoes = request.getSession().getAttribute(EMPRESAS_ASSOCIADAS);
		Map<Integer, List<EmissaoOSCobrancaHelper>> empresasAssociadas = null;

		if(associacoes == null){
			empresasAssociadas = new HashMap<Integer, List<EmissaoOSCobrancaHelper>>();
		}else{
			empresasAssociadas = (Map<Integer, List<EmissaoOSCobrancaHelper>>) associacoes;
		}

		empresasAssociadas = associarOSEmpresa(idEmpresa, itensSelecionados, empresasAssociadas);
		request.getSession().setAttribute(EMPRESAS_ASSOCIADAS, empresasAssociadas);
		dynaForm.set(OS_SELECIONADAS, null);

		return mapping.findForward(MESMA_PAGINA);
	}

	public ActionForward desfazerAssociacoes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		DynaActionForm dynaForm = (DynaActionForm) form;
		dynaForm.set(OS_SELECIONADAS, null);

		Collection<Empresa> listaEmpresas = getEmpresas();

		List<EmissaoOSCobrancaHelper> listaOS = geraListaOSInicial(fachada, request);

		request.getSession().setAttribute("colecaoEmpresa", listaEmpresas);
		request.getSession().setAttribute(LISTA_OS_FILTRADA, listaOS);
		request.getSession().setAttribute(EMPRESAS_ASSOCIADAS, null);
		return mapping.findForward(MESMA_PAGINA);
	}

	public List<EmissaoOSCobrancaHelper> geraListaOSInicial(Fachada fachada, HttpServletRequest request){

		Integer idComando = Integer.valueOf(request.getSession().getAttribute("idComando").toString());
		String comando = request.getSession().getAttribute("tipoComando").toString();

		CobrancaAcaoAtividade cobrancaAtividade = null;

		if("cronograma".equals(comando)){
			cobrancaAtividade = CobrancaAcaoAtividade.CRONOGRAMA;
		}else{
			cobrancaAtividade = CobrancaAcaoAtividade.COMANDO;
		}
		return (List<EmissaoOSCobrancaHelper>) fachada.pesquisarOS(idComando, cobrancaAtividade);
	}

	public ActionForward emitirOS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		Fachada fachada = Fachada.getInstancia();
		Integer idComando = Integer.valueOf(request.getSession().getAttribute("idComando").toString());
		String comando = request.getSession().getAttribute("tipoComando").toString();
		CobrancaAcaoAtividade cobrancaAtividade = null;

		if("cronograma".equals(comando)){
			cobrancaAtividade = CobrancaAcaoAtividade.CRONOGRAMA;
		}else{
			cobrancaAtividade = CobrancaAcaoAtividade.COMANDO;
		}

		ActionForward retorno = mapping.findForward("telaSucesso");
		PrintWriter writer = response.getWriter();
		DynaActionForm dynaForm = (DynaActionForm) form;
		Object associacoes = request.getSession().getAttribute(EMPRESAS_ASSOCIADAS);

		if(associacoes == null){
			// writer.write("alert,Você deve associar pelo menos uma empresa para poder emitir as Ordens de Serviço");
			throw new ActionServletException("atencao.empresas_nao_associadas", null, "Emitir OS CObrança");
		}else{
			try{
				Map<Integer, List<EmissaoOSCobrancaHelper>> empresasAssociadas = (Map<Integer, List<EmissaoOSCobrancaHelper>>) request
								.getSession().getAttribute(EMPRESAS_ASSOCIADAS);
				List<Empresa> empresas = (List<Empresa>) request.getSession().getAttribute("colecaoEmpresa");
				Usuario usuarioLogado = this.getUsuarioLogado(request);

				fachada.emitirOSCobranca(empresasAssociadas, empresas, cobrancaAtividade, idComando, usuarioLogado);
				dynaForm.set(OS_SELECIONADAS, null);
				request.getSession().removeAttribute(EMPRESAS_ASSOCIADAS);
				writer.write("success,Ordens de serviço emitidas com sucesso!");
			}catch(Exception e){
				response.sendError(500, "Erro interno!");
				e.printStackTrace();
			}
		}
		montarPaginaSucesso(request, "Ordens de serviço emitidas com sucesso!", "", "");
		return retorno;
	}

	public EmissaoOSCobrancaHelper[] montaArrayOSCobranca(String[] osSelecionadas){

		if(osSelecionadas == null){
			throw new ActionServletException("atencao.nenhuma_empresa_selecionada", null, "Empresa");
		}
		EmissaoOSCobrancaHelper[] itensSelecionados = new EmissaoOSCobrancaHelper[osSelecionadas.length];
		int index = 0;
		for(String item : osSelecionadas){
			String[] locSetQuadra = item.split("-");
			EmissaoOSCobrancaHelper helper = new EmissaoOSCobrancaHelper();
			helper.setLocalidade(Integer.valueOf(locSetQuadra[0]));
			helper.setSetor(Integer.valueOf(locSetQuadra[1]));
			EmissaoOSCobrancaQuadraHelper quadra = new EmissaoOSCobrancaQuadraHelper();
			quadra.setNumeroQuadra(Integer.valueOf(locSetQuadra[2]));
			helper.getQuadras().add(quadra);
			itensSelecionados[index] = helper;
			index++;
		}
		return itensSelecionados;

	}

	public Collection<Empresa> getEmpresas(){

		Fachada fachada = Fachada.getInstancia();

		Collection<Empresa> colecaoEmpresa = null;
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());
		if(colecaoEmpresa == null || colecaoEmpresa.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Empresa");
		}

		return colecaoEmpresa;
	}

	public List<EmissaoOSCobrancaHelper> atualizaListaOS(List<EmissaoOSCobrancaHelper> listaOS,
					EmissaoOSCobrancaHelper[] itensSelecionados, Empresa empresa){

		for(EmissaoOSCobrancaHelper emissaoCobrancaHelper : itensSelecionados){
			EmissaoOSCobrancaHelper _emissaoOSHelper = listaOS.get(listaOS.indexOf(emissaoCobrancaHelper));
			List<EmissaoOSCobrancaQuadraHelper> quadras = (List<EmissaoOSCobrancaQuadraHelper>) _emissaoOSHelper.getQuadras();
			for(EmissaoOSCobrancaQuadraHelper quadra : emissaoCobrancaHelper.getQuadras()){
				EmissaoOSCobrancaQuadraHelper q = quadras.get(quadras.indexOf(quadra));
				q.setNomeAgente(empresa.getDescricao().split("<")[0].trim());
				quadras.set(quadras.indexOf(quadra), q);
				// quadras.remove(quadra);
			}
		}
		return listaOS;
	}

	public Map<Integer, List<EmissaoOSCobrancaHelper>> associarOSEmpresa(Integer idEmpresa, EmissaoOSCobrancaHelper[] itensSelecionados,
					Map<Integer, List<EmissaoOSCobrancaHelper>> associacoes){

		List<EmissaoOSCobrancaHelper> listaHelper = associacoes.get(idEmpresa);

		if(listaHelper == null){
			listaHelper = new ArrayList<EmissaoOSCobrancaHelper>();
		}

		for(EmissaoOSCobrancaHelper itemSelecionado : itensSelecionados){
			if(listaHelper.contains(itemSelecionado)){
				EmissaoOSCobrancaHelper _emissaoOS = listaHelper.get(listaHelper.indexOf(itemSelecionado));
				List<EmissaoOSCobrancaQuadraHelper> quadras = (List<EmissaoOSCobrancaQuadraHelper>) itemSelecionado.getQuadras();
				_emissaoOS.getQuadras().add(quadras.get(0));
				listaHelper.set(listaHelper.indexOf(itemSelecionado), _emissaoOS);
			}else{
				listaHelper.add(itemSelecionado);
			}
		}

		associacoes.put(idEmpresa, listaHelper);

		return associacoes;

	}

	public void contabilizaNumeroOSSelecionadas(HttpServletRequest request, Integer idEmpresa, String[] oSSelecionadas){

		List<Empresa> empresas = (List<Empresa>) request.getSession().getAttribute("colecaoEmpresa");
		Empresa _empresa = new Empresa();
		_empresa.setId(idEmpresa);

		Empresa empresa = empresas.get(empresas.indexOf(_empresa));
		String descricao = empresa.getDescricao();
		String[] split = descricao.split("<");

		int quantidadeOS = 0;
		for(String osSelecionada : oSSelecionadas){
			String[] itensOs = osSelecionada.split("-");
			quantidadeOS = quantidadeOS + Integer.valueOf(itensOs[3]);
		}

		if(split.length > 1){
			int totalOs = Integer.valueOf(split[1].split(">")[0].trim());
			totalOs = totalOs + quantidadeOS;
			empresa.setDescricao(split[0] + " < " + totalOs + " >");
		}else{
			empresa.setDescricao(empresa.getDescricao() + " < " + quantidadeOS + " >");
		}

		empresas.set(empresas.indexOf(empresa), empresa);
		request.getSession().setAttribute("colecaoEmpresa", empresas);
	}

	public void limparSessao(HttpSession sessao){

		sessao.removeAttribute(LISTA_OS_FILTRADA);
		sessao.removeAttribute(EMPRESAS_ASSOCIADAS);
		sessao.removeAttribute("colecaoEmpresa");
	}

}
