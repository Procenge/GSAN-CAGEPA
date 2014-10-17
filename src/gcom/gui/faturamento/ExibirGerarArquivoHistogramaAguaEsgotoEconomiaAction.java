package gcom.gui.faturamento;

import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroEsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.CategoriaTipo;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroCategoriaTipo;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.ConsumoFaixaCategoria;
import gcom.faturamento.FiltroConsumoFaixaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirGerarArquivoHistogramaAguaEsgotoEconomiaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarArquivoHistogramaAguaEsgotoEconomia");

		// Form
		EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form = (EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm) actionForm;

		String acao = httpServletRequest.getParameter("acao");

		if(acao != null && !acao.equals("")){

			String tipoConsumoCategoria = form.getTipoConsumoCategoria();

			// Inserir Faixa - Mostrar Popup
			if(acao.equals("I")){

				if(Categoria.RESIDENCIAL.toString().equals(tipoConsumoCategoria)){
					form.setSubTituloFaixaConsumo("Residencial");
				}else if(Categoria.COMERCIAL.toString().equals(tipoConsumoCategoria)){
					form.setSubTituloFaixaConsumo("Comercial");
				}else if(Categoria.INDUSTRIAL.toString().equals(tipoConsumoCategoria)){
					form.setSubTituloFaixaConsumo("Industrial");
				}else if(Categoria.PUBLICO.toString().equals(tipoConsumoCategoria)){
					form.setSubTituloFaixaConsumo("Publico");
				}else{
					form.setSubTituloFaixaConsumo("Outros");
				}

				form.setLimiteInferiorFaixa(null);
				form.setLimiteSuperiorFaixa(null);

				retorno = actionMapping.findForward("histogramaAguaEsgotoEconomiaInformarFaixa");

				// Remover Faixa
			}else if(acao.equals("R")){

				String idRemover = httpServletRequest.getParameter("idRemover");
				this.removeConsumoFaixaCategoria(form, Integer.parseInt(idRemover));

				// Inserir Faixa - Insere na colecao
			}else if(acao.equals("A")){
				this.validarInclusaoConsumoFaixaCategoria(form);
			}

		}else{

			// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
			String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

			// [UC0018] - Pesquisar Elo
			if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("1")){

				// Faz a consulta do Elo P�lo
				this.pesquisarEloPolo(form);
			}

			// [UC0023] - Pesquisar Localidade
			if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("2")){

				// Faz a consulta de Localidade
				this.pesquisarLocalidade(form);
			}

			// [UC0142] - Pesquisar Setor Comercial
			if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("3")){

				// Faz a consulta de Setor Comercial
				this.pesquisarSetorComercial(form);
			}

			// [UC0143] - Pesquisar Quadra
			if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("4")){

				// Faz a consulta de Quadra
				this.pesquisarQuadra(httpServletRequest, form);
			}
		}

		// Monta os select�s do jsp
		this.pesquisarGerenciaRegional(httpServletRequest);
		this.pesquisarUnidadeNegocio(httpServletRequest, form);
		this.pesquisarCategoriaTipo(httpServletRequest);
		this.pesquisarCategoria(httpServletRequest, form);
		this.pesquisarConsumoTarifa(httpServletRequest);
		this.pesquisarPerfilImovel(httpServletRequest);
		this.pesquisarEsferaPoder(httpServletRequest);
		this.pesquisarLigacaoEsgotoSituacao(httpServletRequest);
		this.pesquisarLigacaoEsgotoPerfil(httpServletRequest);

		if(acao == null || acao.equals("")){
			this.pesquisarConsumoFaixaCategoria(form, httpServletRequest);
		}else if(acao.equals("M")){
			Collection<ConsumoFaixaCategoria> colecaoFaixa = (Collection<ConsumoFaixaCategoria>) form
							.getLinkedHashMapConsumoFaixaCategoria().get(form.getTipoConsumoCategoria());

			form.setColecaoConsumoFaixaCategoria(colecaoFaixa);

			if(colecaoFaixa != null && !colecaoFaixa.isEmpty()){
				form.setTamanhoColecao(colecaoFaixa.size() + "");
			}else{
				throw new ActionServletException("atencao.faixa_consumo_inexistente_para_categoria");
			}
		}

		// Seta os request�s encontrados
		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	private void pesquisarEloPolo(EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, form.getEloPolo()));

		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

		// Recupera Elo P�lo
		Collection colecaoEloPolo = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoEloPolo != null && !colecaoEloPolo.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoEloPolo);

			localidade = localidade.getLocalidade();

			form.setEloPolo(localidade.getId().toString());
			form.setNomeEloPolo(localidade.getDescricao());

		}else{
			form.setEloPolo(null);
			form.setNomeEloPolo("Localidade inexistente");
		}
	}

	private void pesquisarLocalidade(EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getLocalidade()));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());

		}else{
			form.setLocalidade(null);
			form.setNomeLocalidade("Localidade inexistente");
		}
	}

	private void pesquisarSetorComercial(EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial
						.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, form.getSetorComercial()));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, form.getLocalidade()));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			form.setSetorComercial("" + setorComercial.getCodigo());
			form.setNomeSetorComercial(setorComercial.getDescricao());

		}else{
			form.setSetorComercial(null);
			form.setNomeSetorComercial("Setor Comercial inexistente");
		}
	}

	private void pesquisarQuadra(HttpServletRequest httpServletRequest, EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, form.getQuadra()));

		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, form.getSetorComercial()));

		// Recupera Quadra
		Collection colecaoQuadra = this.getFachada().pesquisar(filtroQuadra, Quadra.class.getName());

		if(colecaoQuadra != null && !colecaoQuadra.isEmpty()){

			Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

			form.setQuadra("" + quadra.getNumeroQuadra());
			// form.setNomeQuadra(quadra.getDescricao());

		}else{
			form.setQuadra(null);
			httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");

		}
	}

	private void setaRequest(HttpServletRequest httpServletRequest, EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		// Elo Polo
		if(form.getEloPolo() != null && !form.getEloPolo().equals("") && form.getNomeEloPolo() != null && !form.getNomeEloPolo().equals("")){

			httpServletRequest.setAttribute("eloPoloEncontrado", "true");
		}

		// Localidade
		if(form.getLocalidade() != null && !form.getLocalidade().equals("") && form.getNomeLocalidade() != null
						&& !form.getNomeLocalidade().equals("")){

			httpServletRequest.setAttribute("localidadeEncontrada", "true");
		}

		// Setor Comercial
		if(form.getSetorComercial() != null && !form.getSetorComercial().equals("") && form.getNomeSetorComercial() != null
						&& !form.getNomeSetorComercial().equals("")){

			httpServletRequest.setAttribute("setorComercialEncontrado", "true");
		}

		// Quadra
		if(form.getNomeQuadra() != null && !form.getNomeQuadra().equals("")){
			httpServletRequest.setAttribute("quadraNaoEncontrada", "true");
		}
	}

	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Ger�ncia Regional");
		}else{
			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}
	}

	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest, EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.setConsultaSemLimites(true);
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);

		if(form.getGerenciaRegional() != null && !form.getGerenciaRegional().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID_GERENCIA, form.getGerenciaRegional()));
		}

		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Unidade de Neg�cio");
		}else{
			httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
		}
	}

	private void pesquisarCategoriaTipo(HttpServletRequest httpServletRequest){

		FiltroCategoriaTipo filtroCategoriaTipo = new FiltroCategoriaTipo();

		filtroCategoriaTipo.setConsultaSemLimites(true);
		filtroCategoriaTipo.setCampoOrderBy(FiltroCategoriaTipo.DESCRICAO);

		Collection colecaoTipoCategoria = this.getFachada().pesquisar(filtroCategoriaTipo, CategoriaTipo.class.getName());

		if(colecaoTipoCategoria == null || colecaoTipoCategoria.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Categoria Tipo");
		}else{
			httpServletRequest.setAttribute("colecaoTipoCategoria", colecaoTipoCategoria);
		}
	}

	private void pesquisarCategoria(HttpServletRequest httpServletRequest, EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.setConsultaSemLimites(true);
		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);
		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(form.getTipoCategoria() != null && !form.getTipoCategoria().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.TIPO_CATEGORIA, form.getTipoCategoria()));
		}

		Collection colecaoCategoria = this.getFachada().pesquisar(filtroCategoria, Categoria.class.getName());

		if(colecaoCategoria == null || colecaoCategoria.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Categoria");
		}else{
			httpServletRequest.setAttribute("colecaoCategoria", colecaoCategoria);
		}
	}

	private void pesquisarConsumoTarifa(HttpServletRequest httpServletRequest){

		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

		filtroConsumoTarifa.setConsultaSemLimites(true);
		filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);
		filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoTarifa = this.getFachada().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

		if(colecaoTarifa == null || colecaoTarifa.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Tarifa");
		}else{
			httpServletRequest.setAttribute("colecaoTarifa", colecaoTarifa);
		}
	}

	private void pesquisarPerfilImovel(HttpServletRequest httpServletRequest){

		FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();

		filtroImovelPerfil.setConsultaSemLimites(true);
		filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);
		filtroImovelPerfil
						.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoPerfilImovel = this.getFachada().pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

		if(colecaoPerfilImovel == null || colecaoPerfilImovel.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Perfil do Im�vel");
		}else{
			httpServletRequest.setAttribute("colecaoPerfilImovel", colecaoPerfilImovel);
		}
	}

	private void pesquisarEsferaPoder(HttpServletRequest httpServletRequest){

		FiltroEsferaPoder filtroEsferaPoder = new FiltroEsferaPoder();

		filtroEsferaPoder.setConsultaSemLimites(true);
		filtroEsferaPoder.setCampoOrderBy(FiltroEsferaPoder.DESCRICAO);
		filtroEsferaPoder.adicionarParametro(new ParametroSimples(FiltroEsferaPoder.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoEsferaPoder = this.getFachada().pesquisar(filtroEsferaPoder, EsferaPoder.class.getName());

		if(colecaoEsferaPoder == null || colecaoEsferaPoder.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Esfera Poder");
		}else{
			httpServletRequest.setAttribute("colecaoEsferaPoder", colecaoEsferaPoder);
		}
	}

	private void pesquisarLigacaoEsgotoSituacao(HttpServletRequest httpServletRequest){

		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		filtroLigacaoEsgotoSituacao.setConsultaSemLimites(true);
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoSituacaoLigacaoEsgoto = this.getFachada().pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());

		if(colecaoSituacaoLigacaoEsgoto == null || colecaoSituacaoLigacaoEsgoto.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Situa��o da Liga��o de Esgoto");
		}else{
			httpServletRequest.setAttribute("colecaoSituacaoLigacaoEsgoto", colecaoSituacaoLigacaoEsgoto);
		}
	}

	private void pesquisarLigacaoEsgotoPerfil(HttpServletRequest httpServletRequest){

		FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();

		filtroLigacaoEsgotoPerfil.setConsultaSemLimites(true);
		filtroLigacaoEsgotoPerfil.setCampoOrderBy(FiltroLigacaoEsgotoPerfil.PERCENTUAL);
		filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoPerfilLigacaoEsgoto = this.getFachada().pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());

		if(colecaoPerfilLigacaoEsgoto == null || colecaoPerfilLigacaoEsgoto.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Perfil da Liga��o de Esgoto");
		}else{
			httpServletRequest.setAttribute("colecaoPerfilLigacaoEsgoto",
							this.montarColecaoUnicaPerfilLigacaoEsgoto(colecaoPerfilLigacaoEsgoto));
		}
	}

	private Collection montarColecaoUnicaPerfilLigacaoEsgoto(Collection colecaoPerfilLigacaoEsgoto){

		LinkedHashMap map = new LinkedHashMap();

		if(colecaoPerfilLigacaoEsgoto != null && !colecaoPerfilLigacaoEsgoto.isEmpty()){

			Iterator itera = colecaoPerfilLigacaoEsgoto.iterator();

			while(itera.hasNext()){
				LigacaoEsgotoPerfil element = (LigacaoEsgotoPerfil) itera.next();
				map.put(element.getPercentualEsgotoConsumidaColetada(), element);
			}
		}

		return map.values();
	}

	private void pesquisarConsumoFaixaCategoria(EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form,
					HttpServletRequest httpServletRequest){

		Collection colecaoCategoria = (Collection) httpServletRequest.getAttribute("colecaoCategoria");

		LinkedHashMap<String, Collection<ConsumoFaixaCategoria>> linkedHashMapConsumoFaixaCategoria = new LinkedHashMap<String, Collection<ConsumoFaixaCategoria>>();

		FiltroConsumoFaixaCategoria filtro = new FiltroConsumoFaixaCategoria();

		filtro.setConsultaSemLimites(true);
		filtro.setCampoOrderBy(FiltroConsumoFaixaCategoria.NUMERO_FAIXA_INICIO);

		if(colecaoCategoria != null && !colecaoCategoria.isEmpty()){

			if(colecaoCategoria.size() == 1){

				Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoCategoria);

				filtro.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaCategoria.ID_CATEGORIA, categoria.getId()));

			}else{

				Iterator itera = colecaoCategoria.iterator();

				while(itera.hasNext()){
					Categoria cat = (Categoria) itera.next();

					filtro.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaCategoria.ID_CATEGORIA, cat.getId(),
									ConectorOr.CONECTOR_OR, colecaoCategoria.size()));

				}
			}
		}

		if(form.getTipoCategoria() != null && !form.getTipoCategoria().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			filtro.adicionarParametro(new ParametroSimples(FiltroConsumoFaixaCategoria.ID_CATEGORIA_TIPO, form.getTipoCategoria()));
		}

		filtro.adicionarCaminhoParaCarregamentoEntidade("categoria");

		Collection colecaoConsumoFaixaCategoria = this.getFachada().pesquisar(filtro, ConsumoFaixaCategoria.class.getName());

		if(colecaoConsumoFaixaCategoria != null && !colecaoConsumoFaixaCategoria.isEmpty()){

			Collection<ConsumoFaixaCategoria> colecaoFaixa = null;
			Iterator itera = colecaoConsumoFaixaCategoria.iterator();

			while(itera.hasNext()){

				ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) itera.next();

				Categoria categoria = consumoFaixaCategoria.getCategoria();

				if(linkedHashMapConsumoFaixaCategoria.get("" + categoria.getId()) != null){
					colecaoFaixa = linkedHashMapConsumoFaixaCategoria.get("" + categoria.getId());
					colecaoFaixa.add(consumoFaixaCategoria);

					linkedHashMapConsumoFaixaCategoria.put("" + categoria.getId(), colecaoFaixa);
				}else{
					colecaoFaixa = new ArrayList<ConsumoFaixaCategoria>();
					colecaoFaixa.add(consumoFaixaCategoria);

					linkedHashMapConsumoFaixaCategoria.put("" + categoria.getId(), colecaoFaixa);
				}

			}
			if(form.getTipoConsumoCategoria() != null && !form.getTipoConsumoCategoria().equals("")){
				colecaoFaixa = linkedHashMapConsumoFaixaCategoria.get(form.getTipoConsumoCategoria());
			}
			if(colecaoFaixa == null || colecaoFaixa.isEmpty()){

				Categoria categoria = (Categoria) colecaoCategoria.iterator().next();
				colecaoFaixa = linkedHashMapConsumoFaixaCategoria.get("" + categoria.getId());

				String tipoConsumoCategoria = "" + categoria.getId();
				form.setTipoConsumoCategoria(tipoConsumoCategoria);

			}else if(form.getTipoConsumoCategoria() == null){

				String idCategoria = (String) linkedHashMapConsumoFaixaCategoria.keySet().iterator().next();
				String tipoConsumoCategoria = idCategoria;
				form.setTipoConsumoCategoria(tipoConsumoCategoria);
			}

			form.setColecaoConsumoFaixaCategoria(colecaoFaixa);
			if(colecaoFaixa != null && !colecaoFaixa.isEmpty()){
				form.setTamanhoColecao(colecaoFaixa.size() + "");
			}
			form.setLinkedHashMapConsumoFaixaCategoria(linkedHashMapConsumoFaixaCategoria);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Consumo Faixa Categoria");
		}

	}

	private void removeConsumoFaixaCategoria(EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form, int idRemover){

		Collection<ConsumoFaixaCategoria> novaColecaoConsumoFaixaCategoria = new ArrayList<ConsumoFaixaCategoria>();

		Collection<ConsumoFaixaCategoria> colecaoParaIterar = (Collection<ConsumoFaixaCategoria>) form
						.getLinkedHashMapConsumoFaixaCategoria().get(form.getTipoConsumoCategoria());

		int index = 0;
		for(Iterator iter = colecaoParaIterar.iterator(); iter.hasNext();){

			index++;

			ConsumoFaixaCategoria element = (ConsumoFaixaCategoria) iter.next();

			if(index != idRemover){
				novaColecaoConsumoFaixaCategoria.add(element);
			}
		}

		if(novaColecaoConsumoFaixaCategoria.size() == 0){
			throw new ActionServletException("atencao.consumo_faixa_nao_informada");
		}

		this.ordenaColecaoFaixaConsumoCategoria(novaColecaoConsumoFaixaCategoria);

		form.getLinkedHashMapConsumoFaixaCategoria().put(form.getTipoConsumoCategoria(), novaColecaoConsumoFaixaCategoria);
		form.setColecaoConsumoFaixaCategoria(novaColecaoConsumoFaixaCategoria);
		form.setTamanhoColecao(novaColecaoConsumoFaixaCategoria.size() + "");
	}

	private void validarInclusaoConsumoFaixaCategoria(EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		boolean ehValido = true;

		Collection<ConsumoFaixaCategoria> colecaoFaixas = (Collection<ConsumoFaixaCategoria>) form.getLinkedHashMapConsumoFaixaCategoria()
						.get(form.getTipoConsumoCategoria());

		int numeroFaixaInicio = Integer.parseInt(form.getLimiteInferiorFaixa());
		int numeroFaixaFim = Integer.parseInt(form.getLimiteSuperiorFaixa());

		Iterator itera = colecaoFaixas.iterator();
		while(itera.hasNext()){
			ConsumoFaixaCategoria consumoExistente = (ConsumoFaixaCategoria) itera.next();

			int numeroFaixaInicioExistente = consumoExistente.getNumeroFaixaInicio();
			int numeroFaixaFimExistente = consumoExistente.getNumeroFaixaFim();

			if((numeroFaixaInicio >= numeroFaixaInicioExistente && numeroFaixaInicio <= numeroFaixaFimExistente)
							|| (numeroFaixaFim >= numeroFaixaInicioExistente && numeroFaixaInicio <= numeroFaixaFimExistente)){

				ehValido = false;
			}
		}

		if(ehValido){
			ConsumoFaixaCategoria consumoFaixaCategoria = new ConsumoFaixaCategoria();

			consumoFaixaCategoria.setNumeroFaixaInicio(numeroFaixaInicio);
			consumoFaixaCategoria.setNumeroFaixaFim(numeroFaixaFim);

			colecaoFaixas.add(consumoFaixaCategoria);

			this.ordenaColecaoFaixaConsumoCategoria(colecaoFaixas);

			form.setColecaoConsumoFaixaCategoria(colecaoFaixas);
			form.setTamanhoColecao(colecaoFaixas.size() + "");
		}else{
			throw new ActionServletException("atencao.consumo_faixa_ja_informada");
		}
	}

	private void ordenaColecaoFaixaConsumoCategoria(Collection colecaoConsumoFaixaCategoria){

		Collections.sort((List) colecaoConsumoFaixaCategoria, new Comparator() {

			public int compare(Object a, Object b){

				ConsumoFaixaCategoria consumo1 = (ConsumoFaixaCategoria) a;
				ConsumoFaixaCategoria consumo2 = (ConsumoFaixaCategoria) b;

				Integer faixaInicio1 = consumo1.getNumeroFaixaInicio();
				Integer faixaInicio2 = consumo2.getNumeroFaixaInicio();

				return faixaInicio1.compareTo(faixaInicio2);
			}
		});

	}

}
