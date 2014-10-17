
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoPerfilDebitoTipo;
import gcom.cobranca.InfracaoPerfilDebitoTipo;
import gcom.cobranca.bean.InfracaoPerfilDebitoTipoHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdicionarInfracaoPerfilDebitoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirInfracaoDebitPerfilDebitoTipo");
		InfracaoPerfilDebitoTipoActionForm form = (InfracaoPerfilDebitoTipoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession();
		sessao.setAttribute("fechar", null);
		if(form.getIdPerfil() == null){

			form.setIdPerfil(httpServletRequest.getParameter("idPerfil"));
		}

		if(form.getIndicadorLancamentoAtivo() == null){
			form.setIndicadorLancamentoAtivo("1");
		}

		if(httpServletRequest.getParameter("incluir") != null && httpServletRequest.getParameter("incluir").equals("ok")){
			adicionarInfracaoPerfilDebitoTipo(httpServletRequest, form);
		}else if(httpServletRequest.getParameter("concluir") != null && httpServletRequest.getParameter("concluir").equals("ok")){
			concluir(httpServletRequest, form);
			if(sessao.getAttribute("manter") != null){
				retorno = actionMapping.findForward("telaSucessoPopup");
				montarPaginaSucesso(httpServletRequest, "Perfil de infracao Debito Tipo atualizado com sucesso.", "", "", null, null);
			}
		}else if(httpServletRequest.getParameter("tipoConsulta") != null){
			form.setDescricaoDebitoTipo(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
			form.setIdTipoDebito(httpServletRequest.getParameter("idCampoEnviarDados"));
			httpServletRequest.setAttribute("debitoTipoEncontrado", "1");
			if(sessao.getAttribute("manter") != null){
				Collection colecaoInfracaoPerfilDebitoTipo = (Collection) sessao.getAttribute("colecaoDebitoTipo");
				verificaAlteracaoValores(httpServletRequest, colecaoInfracaoPerfilDebitoTipo);
			}
		}else if(httpServletRequest.getParameter("idTipoDebito") != null && !httpServletRequest.getParameter("idTipoDebito").equals("")){
			DebitoTipo debitoTipo = (DebitoTipo) Fachada.getInstancia().pesquisar(
							Integer.valueOf(httpServletRequest.getParameter("idTipoDebito")), DebitoTipo.class);
			if(debitoTipo == null){
				form.setDescricaoDebitoTipo("TIPO DE DEBITO NÃO CADASTRADO");
			}else{
				httpServletRequest.setAttribute("debitoTipoEncontrado", "1");
				form.setDescricaoDebitoTipo(debitoTipo.getDescricao());
				form.setIdTipoDebito(String.valueOf(debitoTipo.getId()));
			}
			if(sessao.getAttribute("manter") != null){
				Collection colecaoInfracaoPerfilDebitoTipo = (Collection) sessao.getAttribute("colecaoDebitoTipo");
				verificaAlteracaoValores(httpServletRequest, colecaoInfracaoPerfilDebitoTipo);
			}
		}else if(httpServletRequest.getParameter("remover") != null){
			removerItem(httpServletRequest, sessao);
		}else if(httpServletRequest.getParameter("manter") != null){
			carregarDados(httpServletRequest, sessao, form);
		}else{
			carregarDadosInserirInfracaoPerfilDebitoTipo(httpServletRequest, form);
		}

		return retorno;
	}

	private void removerItem(HttpServletRequest httpServletRequest, HttpSession sessao){

		Collection colecaoInfracaoPerfilDebitoTipo = (Collection) sessao.getAttribute("colecaoDebitoTipo");
		if(sessao.getAttribute("manter") != null){
			verificaAlteracaoValores(httpServletRequest, colecaoInfracaoPerfilDebitoTipo);
		}
		for(InfracaoPerfilDebitoTipoHelper i : (Collection<InfracaoPerfilDebitoTipoHelper>) colecaoInfracaoPerfilDebitoTipo){
			if((i.getIdInfracaoPerfil() + "" + i.getIdDebitoTipo()).equals(httpServletRequest.getParameter("remover"))){
				colecaoInfracaoPerfilDebitoTipo.remove(i);
				if(sessao.getAttribute("manter") != null){
					Collection remocao;
					if(sessao.getAttribute("colRemocaoDebitoTipo") != null){
						remocao = (Collection) sessao.getAttribute("colRemocaoDebitoTipo");
						remocao.add(i);
					}else{
						remocao = new ArrayList();
						remocao.add(i);
					}
					sessao.setAttribute("colRemocaoDebitoTipo", remocao);
				}
				break;
			}
		}
	}

	private void adicionarInfracaoPerfilDebitoTipo(HttpServletRequest httpServletRequest, InfracaoPerfilDebitoTipoActionForm form){

		HttpSession sessao = httpServletRequest.getSession();
		if(!StringUtils.isNotEmpty(form.getIdTipoDebito())){
			throw new ActionServletException("atencao.campo.informado", "Tipo de Debito");
		}
		DebitoTipo debitoTipo = (DebitoTipo) Fachada.getInstancia().pesquisar(Integer.valueOf(form.getIdTipoDebito()), DebitoTipo.class);
		if(debitoTipo == null){
			throw new ActionServletException("atencao.debito_tipo_inexistente");
		}
		Collection colecaoDebitoTipo;
		if(sessao.getAttribute("colecaoDebitoTipo") == null){
			colecaoDebitoTipo = new ArrayList();
		}else{
			colecaoDebitoTipo = (Collection) sessao.getAttribute("colecaoDebitoTipo");
		}
		if(sessao.getAttribute("manter") != null){
			verificaAlteracaoValores(httpServletRequest, colecaoDebitoTipo);
		}
		InfracaoPerfilDebitoTipoHelper helper = new InfracaoPerfilDebitoTipoHelper();
		helper.setTipoDebitoDescricao(debitoTipo.getDescricao());
		helper.setIdDebitoTipo(form.getIdTipoDebito());
		helper.setLancamentoAtivo("SIM");
		helper.setIdInfracaoPerfil(form.getIdPerfil());
		helper.setPorcentagemDesconto(form.getPcDesconto());
		helper.setFatorMultiplicador(form.getFatorMultiplicador());
		for(InfracaoPerfilDebitoTipoHelper i : (Collection<InfracaoPerfilDebitoTipoHelper>) colecaoDebitoTipo){
			if(i.getIdDebitoTipo().equals(helper.getIdDebitoTipo())){
				throw new ActionServletException("atencao.debito_tipo_infracao_perfil_ja_informado", form.getDescricaoDebitoTipo());
			}
		}

		colecaoDebitoTipo.add(helper);
		sessao.setAttribute("colecaoDebitoTipo", colecaoDebitoTipo);

		limparform(form);
	}

	private void limparform(InfracaoPerfilDebitoTipoActionForm form){

		form.setIdTipoDebito("");
		form.setDescricaoDebitoTipo("");
		form.setFatorMultiplicador("");
		form.setPcDesconto("");
	}

	private void carregarDadosInserirInfracaoPerfilDebitoTipo(HttpServletRequest httpServletRequest, InfracaoPerfilDebitoTipoActionForm form){

		HttpSession sessao = httpServletRequest.getSession();
		sessao.setAttribute("colecaoDebitoTipo", null);
		Map mapInfracaoPerfilDebitoTipo = (Map) sessao.getAttribute("mapInfracaoPerfilDebitoTipo");
		if(mapInfracaoPerfilDebitoTipo != null && mapInfracaoPerfilDebitoTipo.get(form.getIdPerfil()) != null){
			sessao.setAttribute("colecaoDebitoTipo", mapInfracaoPerfilDebitoTipo.get(form.getIdPerfil()));
		}
		sessao.setAttribute("fechar", null);
	}

	private void concluir(HttpServletRequest httpServletRequest, InfracaoPerfilDebitoTipoActionForm form){

		HttpSession sessao = httpServletRequest.getSession();
		Map mapInfracaoPerfilDebitoTipo = (Map) sessao.getAttribute("mapInfracaoPerfilDebitoTipo");
		Collection colecaoDebitoTipo = (Collection) sessao.getAttribute("colecaoDebitoTipo");
		Collection remocao = (Collection) sessao.getAttribute("colRemocaoDebitoTipo");
		Collection inserir = (Collection) sessao.getAttribute("colecaoDebitoTipoInserirManter");
		if(sessao.getAttribute("manter") != null){
			// caso seja do manter, atualiza direto os valores na base
			verificaAlteracaoValores(httpServletRequest, colecaoDebitoTipo);
			Fachada.getInstancia().atualizarInfracaoDebitoTipo(colecaoDebitoTipo, remocao, inserir);
		}else{
			if(mapInfracaoPerfilDebitoTipo == null){
				mapInfracaoPerfilDebitoTipo = new HashMap();
				mapInfracaoPerfilDebitoTipo.put(form.getIdPerfil(), colecaoDebitoTipo);
			}else{
				mapInfracaoPerfilDebitoTipo.put(form.getIdPerfil(), colecaoDebitoTipo);
			}
		}
		sessao.setAttribute("mapInfracaoPerfilDebitoTipo", mapInfracaoPerfilDebitoTipo);
		sessao.setAttribute("colecaoDebitoTipo", null);
		sessao.setAttribute("fechar", "S");
		limparform(form);
	}

	private void carregarDados(HttpServletRequest httpServletRequest, HttpSession sessao, InfracaoPerfilDebitoTipoActionForm form){

		String idPerfil = httpServletRequest.getParameter("idPerfil");
		FiltroInfracaoPerfilDebitoTipo filtro = new FiltroInfracaoPerfilDebitoTipo();
		filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoPerfilDebitoTipo.INFRACAO_PERFIL_ID, Integer.valueOf(idPerfil)));
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroInfracaoPerfilDebitoTipo.INFRACAO_PERFIL);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroInfracaoPerfilDebitoTipo.DEBITO_TIPO);
		Collection colecao = Fachada.getInstancia().pesquisar(filtro, InfracaoPerfilDebitoTipo.class.getName());

		Collection retorno = new ArrayList();
		for(InfracaoPerfilDebitoTipo i : (Collection<InfracaoPerfilDebitoTipo>) colecao){
			InfracaoPerfilDebitoTipoHelper helper = new InfracaoPerfilDebitoTipoHelper();
			helper.setId(String.valueOf(i.getId()));
			helper.setTipoDebitoDescricao(i.getDebitoTipo().getDescricao());
			helper.setIdDebitoTipo(String.valueOf(i.getDebitoTipo().getId()));
			helper.setLancamentoAtivo(i.getIndicadorLancamentoAtivo().intValue() == 1 ? "SIM" : "NAO");
			// httpServletRequest.setAttribute("lancamentoAtivo"+helper.getIdInfracaoPerfil()+helper.getIdDebitoTipo(),
			// helper.getLancamentoAtivo());
			helper.setIdInfracaoPerfil(String.valueOf(i.getInfracaoPerfil().getId()));
			helper.setPorcentagemDesconto(i.getPorcentagemDesconto() == null ? "" : String.valueOf(i.getPorcentagemDesconto()));
			helper.setFatorMultiplicador(i.getNumeroFatorMultiplicador() == null ? "" : String.valueOf(i.getNumeroFatorMultiplicador()));
			retorno.add(helper);
		}
		sessao.setAttribute("manter", httpServletRequest.getParameter("manter"));
		sessao.setAttribute("colecaoDebitoTipo", retorno);
		sessao.setAttribute("colRemocaoDebitoTipo", null);
		limparform(form);
	}

	private void verificaAlteracaoValores(HttpServletRequest httpServletRequest, Collection colecaoDebitoTipo){

		for(InfracaoPerfilDebitoTipoHelper i : (Collection<InfracaoPerfilDebitoTipoHelper>) colecaoDebitoTipo){
			if(httpServletRequest.getParameter("fatorMultiplicador" + i.getIdInfracaoPerfil() + i.getIdDebitoTipo()) != null){
				i.setFatorMultiplicador(httpServletRequest.getParameter("fatorMultiplicador" + i.getIdInfracaoPerfil()
								+ i.getIdDebitoTipo()));
			}
			if(httpServletRequest.getParameter("pcDesconto" + i.getIdInfracaoPerfil() + i.getIdDebitoTipo()) != null){
				i.setPorcentagemDesconto(httpServletRequest.getParameter("pcDesconto" + i.getIdInfracaoPerfil() + i.getIdDebitoTipo()));
			}
			if(httpServletRequest.getParameter("lancamentoAtivo" + i.getIdInfracaoPerfil() + i.getIdDebitoTipo()) != null){
				i.setLancamentoAtivo(httpServletRequest.getParameter("lancamentoAtivo" + i.getIdInfracaoPerfil() + i.getIdDebitoTipo()));
			}
		}
	}
}
