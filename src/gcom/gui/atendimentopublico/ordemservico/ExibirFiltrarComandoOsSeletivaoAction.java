
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFiltrarComandoOsSeletivaoAction
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

		ActionForward retorno = actionMapping.findForward("filtrarComandoOsSeletiva");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarComandoOsSeletivaActionForm consultarComandoOsSeletivaActionForm = (ConsultarComandoOsSeletivaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Collection<ImovelPerfil> collectionImovelPerfil = null;
		Collection<Categoria> collectionImovelCategoria = null;
		Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = null;
		Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = null;
		Integer categoria = 0;

		if(sessao.getAttribute("colecaoFirma") == null){
			final FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);

			Collection<Empresa> colecaoFirma = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			// [FS0001 - Verificar Existencia de dados]
			if((colecaoFirma == null) || (colecaoFirma.isEmpty())){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, Empresa.class.getName());
			}

			sessao.setAttribute("colecaoFirma", colecaoFirma);
		}

		carregarServicoTipo(httpServletRequest);
		carregarElo(httpServletRequest);
		carregarGrupoFaturamento(httpServletRequest);
		carregarGerenciaRegional(httpServletRequest);

		// Lista o Perfil do Imovel
		if(consultarComandoOsSeletivaActionForm.getPerfilImovel() == null){
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
			filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);
			collectionImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

			if(collectionImovelPerfil == null || collectionImovelPerfil.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Perfil do Imóvel");
			}

			sessao.setAttribute("collectionImovelPerfil", collectionImovelPerfil);
		}


		// Lista de Categoria
		if(consultarComandoOsSeletivaActionForm.getCategoria() == null){
			FiltroCategoria filtroCategoria = new FiltroCategoria();
			filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.SIM));
			filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

			collectionImovelCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
			if(collectionImovelCategoria == null || collectionImovelCategoria.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Categoria");
			}

			sessao.setAttribute("collectionImovelCategoria", collectionImovelCategoria);
		}

		Collection<Subcategoria> collectionImovelSubcategoria = null;

		if(consultarComandoOsSeletivaActionForm.getCategoria() != null && consultarComandoOsSeletivaActionForm.getCategoria().length == 1
						&& !consultarComandoOsSeletivaActionForm.getCategoria()[0].equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){

			// Lista de SubCategorias
			FiltroSubCategoria filtroSubcategoria = new FiltroSubCategoria();

			filtroSubcategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, categoria));

			filtroSubcategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);

			collectionImovelSubcategoria = fachada.pesquisar(filtroSubcategoria, Subcategoria.class.getName());
			if(collectionImovelSubcategoria == null || collectionImovelSubcategoria.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "Subcategoria");
			}

			sessao.setAttribute("collectionImovelSubcategoria", collectionImovelSubcategoria);
		}else{
			sessao.setAttribute("collectionImovelSubcategoria", null);
		}

		// Lista de LigacaoAguaSituacao
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

		colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

		sessao.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);

		if(Util.isVazioOrNulo(colecaoLigacaoAguaSituacao)){
			throw new ActionServletException("atencao.naocadastrado", null, "Situacao da Ligação de Água");
		}

		// Lista de LigacaoEsgotoSituacao
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

		colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

		if(Util.isVazioOrNulo(colecaoLigacaoEsgotoSituacao)){
			throw new ActionServletException("atencao.naocadastrado", null, "Situacao da Ligação de Esgoto");
		}

		sessao.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);

		return retorno;

	}

	public void carregarServicoTipo(HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroServicoTipo.adicionarParametro(new ParametroNaoNulo(FiltroServicoTipo.INDICADOR_ORDEM_SELETIVA));
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_ORDEM_SELETIVA,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);

		Collection<ServicoTipo> colecaoServicoTipo = this.getFachada().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){

			sessao.setAttribute("colecaoServicoTipo", colecaoServicoTipo);

		}else{

			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SERVICO_TIPO");

		}
	}

	public void carregarElo(HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroLocalidade filtroLocalidadeElo = new FiltroLocalidade();
		filtroLocalidadeElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.SIM));

		ArrayList<Localidade> colecaoLocalidadeElo = (ArrayList<Localidade>) this.getFachada().pesquisar(filtroLocalidadeElo,
						Localidade.class.getName());

		ArrayList<Localidade> colecaoElo = new ArrayList();

		for(Localidade localidade : colecaoLocalidadeElo){

			if(localidade.getLocalidade() != null){

				colecaoElo.add(localidade.getLocalidade());

			}
		}

		if(colecaoElo != null && !colecaoElo.isEmpty()){

			Collections.sort(colecaoElo, new Comparator() {

				public int compare(Object o1, Object o2){

					Localidade l1 = (Localidade) o1;
					Localidade l2 = (Localidade) o2;
					return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
				}
			});

			sessao.setAttribute("colecaoElo", colecaoElo);

		}else{

			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LOCALIDADE");

		}

	}

	public void carregarGrupoFaturamento(HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroFaturamentoGrupo filtroFaturamentogrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentogrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO, ConstantesSistema.SIM));
		filtroFaturamentogrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);

		Collection colecaoGrupoFaturamento = this.getFachada().pesquisar(filtroFaturamentogrupo, FaturamentoGrupo.class.getName());

		if(colecaoGrupoFaturamento != null && !colecaoGrupoFaturamento.isEmpty()){

			sessao.setAttribute("colecaoGrupoFaturamento", colecaoGrupoFaturamento);

		}
		else{

			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "FATURAMENTO_GRUPO");

		}
		
	}

	public void carregarGerenciaRegional(HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO, ConstantesSistema.SIM));
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);

	}
}
