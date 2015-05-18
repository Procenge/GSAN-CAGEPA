/**
 * 
 */

package gcom.gui;

import gcom.arrecadacao.pagamento.bean.PagamentoHistoricoAdmiteDevolucaoHelper;
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.endereco.FiltroLogradouroBairro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroNegativadorExclusaoMotivo;
import gcom.cobranca.NegativadorExclusaoMotivo;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.bean.FiltroContaSimularCalculoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifaVigencia;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.seguranca.acesso.*;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.FiltroGenerico;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ExecutorParametrosFaturamento;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.directwebremoting.WebContextFactory;

/**
 * @author gmatos
 */
public class AjaxService {

	private static final String INDICADOR_REDE = "1";

	private Fachada fachada;

	/**
	 * Construtor padrão
	 */
	public AjaxService() {

		super();
		fachada = Fachada.getInstancia();
	}

	/**
	 * Método responsável por consultar os pavimentos.
	 * 
	 * @param idLocalOcorrencia
	 *            O ID do local da ocorrência.
	 * @return Um mapa de String
	 */
	public Map<String, String> consultarPavimentos(String idLocalOcorrencia){

		Map<String, String> dados = new HashMap<String, String>();
		LocalOcorrencia localOcorrencia = null;
		HttpSession sessao = WebContextFactory.get().getSession();

		localOcorrencia = fachada.pesquisarLocalOcorrencia(Integer.valueOf(idLocalOcorrencia));
		if(localOcorrencia != null && localOcorrencia.getIndicadorUso() == 1){

			if(localOcorrencia.getIndicadorRua() == 1){
				Collection<PavimentoRua> pavimentos = null;
				pavimentos = fachada.pesquisarPavimentoRua();
				sessao.setAttribute("colecaoDiametroRedeAgua", pavimentos);

				if(pavimentos != null){
					PavimentoRua pavimentoRua = null;
					for(Iterator<PavimentoRua> iterator = pavimentos.iterator(); iterator.hasNext();){
						pavimentoRua = (PavimentoRua) iterator.next();
						dados.put(String.valueOf(pavimentoRua.getId()), String.valueOf(pavimentoRua.getDescricao()));
					}
				}
			}else{
				Collection<PavimentoCalcada> pavimentos = null;
				pavimentos = fachada.pesquisarPavimentoCalcada();
				sessao.setAttribute("colecaoPavimentos", pavimentos);

				if(pavimentos != null){
					PavimentoCalcada pavimentoCalcada = null;
					for(Iterator<PavimentoCalcada> iterator = pavimentos.iterator(); iterator.hasNext();){
						pavimentoCalcada = (PavimentoCalcada) iterator.next();
						dados.put(String.valueOf(pavimentoCalcada.getId()), String.valueOf(pavimentoCalcada.getDescricao()));
					}
				}
			}
		}

		return dados;
	}

	/**
	 * Método responsável por consultar os diâmetros de água.
	 * 
	 * @param idRedeRamal
	 * @return Um mapa de String
	 */
	public Map<String, String> consultarDiametroAgua(String idRedeRamal){

		Map<String, String> dados = new HashMap<String, String>();
		HttpSession sessao = WebContextFactory.get().getSession();

		if(idRedeRamal.equals(ConstantesSistema.INDICADOR_REDE)){

			Collection<DiametroRedeAgua> diametros = null;
			diametros = fachada.pesquisarDiametroRedeAgua();
			sessao.setAttribute("colecaoDiametroRedeAgua", diametros);

			if(diametros != null){
				DiametroRedeAgua diametroRedeAgua = null;
				for(Iterator<DiametroRedeAgua> iterator = diametros.iterator(); iterator.hasNext();){
					diametroRedeAgua = (DiametroRedeAgua) iterator.next();
					dados.put(String.valueOf(diametroRedeAgua.getId()), String.valueOf(diametroRedeAgua.getDescricao()));
				}
			}

		}

		else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_RAMAL)){

			Collection<DiametroRamalAgua> diametros = null;
			diametros = fachada.pesquisarDiametroRamalAgua();
			sessao.setAttribute("colecaoDiametroRedeAgua", diametros);

			if(diametros != null){
				DiametroRamalAgua diametroRamalAgua = null;
				for(Iterator<DiametroRamalAgua> iterator = diametros.iterator(); iterator.hasNext();){
					diametroRamalAgua = (DiametroRamalAgua) iterator.next();
					dados.put(String.valueOf(diametroRamalAgua.getId()), String.valueOf(diametroRamalAgua.getDescricao()));
				}
			}
		}else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_CAVALETE)){

			Collection<DiametroCavaleteAgua> diametros = null;
			diametros = fachada.pesquisarDiametroCavaleteAgua();
			sessao.setAttribute("colecaoDiametroRedeAgua", diametros);

			if(diametros != null){
				DiametroCavaleteAgua diametroCavaleteAgua = null;
				for(Iterator<DiametroCavaleteAgua> iterator = diametros.iterator(); iterator.hasNext();){
					diametroCavaleteAgua = (DiametroCavaleteAgua) iterator.next();
					dados.put(String.valueOf(diametroCavaleteAgua.getId()), String.valueOf(diametroCavaleteAgua.getDescricao()));
				}
			}

		}

		return dados;

	}

	/**
	 * Método responsável por consultar os diâmetros de esgoto.
	 * 
	 * @param idRedeRamal
	 * @return Um mapa de String
	 */
	public Map<String, String> consultarDiametroEsgoto(String idRedeRamal){

		Map<String, String> dados = new HashMap<String, String>();
		HttpSession sessao = WebContextFactory.get().getSession();

		if(idRedeRamal.equals(INDICADOR_REDE)){

			Collection<DiametroRedeEsgoto> diametros = null;
			diametros = fachada.pesquisarDiametroRedeEsgoto();
			sessao.setAttribute("colecaoDiametroRedeEsgoto", diametros);

			if(diametros != null){
				DiametroRedeEsgoto diametroRedeEsgoto = null;
				for(Iterator<DiametroRedeEsgoto> iterator = diametros.iterator(); iterator.hasNext();){
					diametroRedeEsgoto = (DiametroRedeEsgoto) iterator.next();
					dados.put(String.valueOf(diametroRedeEsgoto.getId()), String.valueOf(diametroRedeEsgoto.getDescricao()));
				}
			}

		}else{

			Collection<DiametroRamalEsgoto> diametros = null;
			diametros = fachada.pesquisarDiametroRamalEsgoto();
			sessao.setAttribute("colecaoDiametroRedeEsgoto", diametros);

			if(diametros != null){
				DiametroRamalEsgoto diametroRamalEsgoto = null;
				for(Iterator<DiametroRamalEsgoto> iterator = diametros.iterator(); iterator.hasNext();){
					diametroRamalEsgoto = (DiametroRamalEsgoto) iterator.next();
					dados.put(String.valueOf(diametroRamalEsgoto.getId()), String.valueOf(diametroRamalEsgoto.getDescricao()));
				}
			}
		}

		return dados;

	}

	/**
	 * Método responsável por consultar os materiais de água.
	 * 
	 * @param idRedeRamal
	 * @return Um mapa de String
	 */
	public Map<String, String> consultarMaterialAgua(String idRedeRamal){

		Map<String, String> dados = new HashMap<String, String>();
		HttpSession sessao = WebContextFactory.get().getSession();

		if(idRedeRamal.equals(ConstantesSistema.INDICADOR_REDE)){

			Collection<MaterialRedeAgua> materiais = null;
			materiais = fachada.pesquisarMaterialRedeAgua();
			sessao.setAttribute("colecaoMaterialRedeAgua", materiais);

			if(materiais != null){
				MaterialRedeAgua materialRedeAgua = null;
				for(Iterator<MaterialRedeAgua> iterator = materiais.iterator(); iterator.hasNext();){
					materialRedeAgua = (MaterialRedeAgua) iterator.next();
					dados.put(String.valueOf(materialRedeAgua.getId()), String.valueOf(materialRedeAgua.getDescricao()));
				}
			}

		}else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_RAMAL)){

			Collection<MaterialRamalAgua> materiais = null;
			materiais = fachada.pesquisarMaterialRamalAgua();
			sessao.setAttribute("colecaoMaterialRedeAgua", materiais);

			if(materiais != null){
				MaterialRamalAgua materialRamalAgua = null;
				for(Iterator<MaterialRamalAgua> iterator = materiais.iterator(); iterator.hasNext();){
					materialRamalAgua = (MaterialRamalAgua) iterator.next();
					dados.put(String.valueOf(materialRamalAgua.getId()), String.valueOf(materialRamalAgua.getDescricao()));
				}
			}
		}else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_CAVALETE)){
			Collection<MaterialCavaleteAgua> materiais = null;
			materiais = fachada.pesquisarMaterialCavaleteAgua();
			sessao.setAttribute("colecaoMaterialRedeAgua", materiais);

			if(materiais != null){
				MaterialCavaleteAgua materialCavaleteAgua = null;
				for(Iterator<MaterialCavaleteAgua> iterator = materiais.iterator(); iterator.hasNext();){
					materialCavaleteAgua = (MaterialCavaleteAgua) iterator.next();
					dados.put(String.valueOf(materialCavaleteAgua.getId()), String.valueOf(materialCavaleteAgua.getDescricao()));
				}
			}
		}

		return dados;

	}

	/**
	 * Método responsável por consultar os materiais de esgoto..
	 * 
	 * @param idRedeRamal
	 * @return Um mapa de String
	 */
	public Map<String, String> consultarMaterialEsgoto(String idRedeRamal){

		Map<String, String> dados = new HashMap<String, String>();
		HttpSession sessao = WebContextFactory.get().getSession();

		if(idRedeRamal.equals(INDICADOR_REDE)){

			Collection<MaterialRedeEsgoto> materiais = null;
			materiais = fachada.pesquisarMaterialRedeEsgoto();
			sessao.setAttribute("colecaoMaterialRedeEsgoto", materiais);

			if(materiais != null){
				MaterialRedeEsgoto materialRedeEsgoto = null;
				for(Iterator<MaterialRedeEsgoto> iterator = materiais.iterator(); iterator.hasNext();){
					materialRedeEsgoto = (MaterialRedeEsgoto) iterator.next();
					dados.put(String.valueOf(materialRedeEsgoto.getId()), String.valueOf(materialRedeEsgoto.getDescricao()));
				}
			}

		}else{

			Collection<MaterialRamalEsgoto> materiais = null;
			materiais = fachada.pesquisarMaterialRamalEsgoto();
			sessao.setAttribute("colecaoMaterialRedeEsgoto", materiais);

			if(materiais != null){
				MaterialRamalEsgoto materialRamalEsgoto = null;
				for(Iterator<MaterialRamalEsgoto> iterator = materiais.iterator(); iterator.hasNext();){
					materialRamalEsgoto = (MaterialRamalEsgoto) iterator.next();
					dados.put(String.valueOf(materialRamalEsgoto.getId()), String.valueOf(materialRamalEsgoto.getDescricao()));
				}
			}
		}

		return dados;

	}

	public Map<String, String> carregarEspecificacao(String idSolicitacaoTipo){

		// Map<String, String> retorno = new HashMap<String, String>();
		SortedMap<String, String> retorno = new TreeMap<String, String>();

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoEspecificacao.setConsultaSemLimites(true);
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID,
						idSolicitacaoTipo));
		filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);

		Collection colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
						SolicitacaoTipoEspecificacao.class.getName());

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
		for(Iterator<SolicitacaoTipoEspecificacao> iterator = colecaoSolicitacaoTipoEspecificacao.iterator(); iterator.hasNext();){
			solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) iterator.next();
			retorno.put(String.valueOf(solicitacaoTipoEspecificacao.getDescricao()), String.valueOf(solicitacaoTipoEspecificacao.getId()));
		}

		return retorno;
	}

	/**
	 * [UC0197] Filtrar Operações Efetuadas
	 * 
	 * @author Saulo Lima
	 * @date 19/05/2012
	 * @param idFuncionalidade
	 */
	public Map<String, String> carregarOperacao(String idFuncionalidade){

		Map<String, String> retorno = new HashMap<String, String>();

		FiltroOperacao filtroOperacao = new FiltroOperacao(FiltroOperacao.DESCRICAO);
		filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.INDICADOR_AUDITORIA, ConstantesSistema.SIM));
		filtroOperacao.adicionarParametro(new ParametroSimples(FiltroOperacao.FUNCIONALIDADE_ID, idFuncionalidade));

		Collection colecaoOperacao = fachada.pesquisar(filtroOperacao, Operacao.class.getName());

		Operacao operacao = null;
		for(Iterator<Operacao> iterator = colecaoOperacao.iterator(); iterator.hasNext();){
			operacao = (Operacao) iterator.next();
			retorno.put(String.valueOf(operacao.getId()), String.valueOf(operacao.getDescricao()));
		}

		return retorno;
	}

	/**
	 * [UC0197] Filtrar Argumentos da funcionalidade.
	 * 
	 * @param idFuncionalidade
	 */
	public Collection carregarArgumentos(String idFuncionalidade){

		Collection retorno = new ArrayList();

		Funcionalidade funcionalidade = null;

		// Cria o filtro para pesquisa e seta o código da funcionalidade informada no filtro
		FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();
		filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, idFuncionalidade));
		filtroFuncionalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionalidade.ARGUMENTOS);

		Collection colecaoFuncionalidade = Fachada.getInstancia().pesquisar(filtroFuncionalidade, Funcionalidade.class.getName());

		// Caso exista a funcionalidade cadastrada na base de dados recupera a funcionalidade da
		// coleção
		if(colecaoFuncionalidade != null && !colecaoFuncionalidade.isEmpty()){
			funcionalidade = (Funcionalidade) Util.retonarObjetoDeColecao(colecaoFuncionalidade);
		}

		Set<Argumento> argumentos = funcionalidade.getArgumentos();
		for(Argumento argumento : argumentos){
			retorno.add(argumento.getDescricaoAbreviada());
		}

		return retorno;
	}

	public Map<String, String> carregar(String id, String nomeClasse, String attPesquisa, String textoCombo, String nomeColecao){

		HttpSession sessao = WebContextFactory.get().getSession();

		FiltroGenerico filtro = new FiltroGenerico();
		filtro.setConsultaSemLimites(true);
		filtro.setCampoOrderBy(FiltroGenerico.DESCRICAO);
		filtro.adicionarParametro(new ParametroSimples(FiltroGenerico.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(!Util.isVazioOuBranco(attPesquisa) && !Util.isVazioOuBranco(id)){
			filtro.adicionarParametro(new ParametroSimples(attPesquisa, id));
		}

		Collection<ObjetoTransacao> colecao = fachada.pesquisar(filtro, nomeClasse);

		if(!Util.isVazioOuBranco(nomeColecao) && sessao != null && colecao != null){
			sessao.removeAttribute(nomeColecao);
			sessao.setAttribute(nomeColecao, colecao);
		}

		Map<String, String> retorno = new HashMap<String, String>();

		if(!Util.isVazioOrNulo(colecao)){
			for(ObjetoTransacao objeto : colecao){
				// Id
				String idObjeto = String.valueOf(objeto.get("id"));

				// Texto
				String textoObjeto = "";
				if(!Util.isVazioOuBranco(textoCombo)){
					textoObjeto = String.valueOf(objeto.get(textoCombo));
				}else{
					textoObjeto = String.valueOf(objeto.get("descricao"));
				}

				retorno.put(idObjeto, textoObjeto);
			}
		}

		return retorno;
	}

	public boolean comparaServicoTipoSubGrupoSubstituicaoHidrometro(String idServicoTipo){

		boolean retorno = false;
		try{
			retorno = fachada.comparaServicoTipoSubgrupoSubstituicaoHidrometro(idServicoTipo);
		}catch(ControladorException e){
			throw new ActionServletException("");
		}

		return retorno;
	}

	public Collection carregaSetoresPorLocalidade(String idLocalidade){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, idLocalidade));
		filtroSetorComercial.setCampoOrderBy(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL);

		Collection<SetorComercial> colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		Collection retorno = new ArrayList();
		SetorComercial setorComercial = new SetorComercial();

		for(Iterator<SetorComercial> iterator = colecaoSetorComercial.iterator(); iterator.hasNext();){
			setorComercial = (SetorComercial) iterator.next();
			retorno.add(String.valueOf(setorComercial.getCodigo()));
			retorno.add(String.valueOf(setorComercial.getId()));
		}

		return retorno;
	}

	public Map<String, String> carregaBairrosPorLocalidade(String idLocalidade){

		TreeMap<String, String> retorno = new TreeMap<String, String>();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.MUNICIPIO);

		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		Collection<Bairro> colecaoBairro = null;

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
			if(colecaoLocalidade.iterator().next().getMunicipio() != null && !colecaoLocalidade.iterator().next().getMunicipio().equals("")){
				FiltroBairro filtroBairro = new FiltroBairro();
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, colecaoLocalidade.iterator().next()
								.getMunicipio().getId().toString()));
				filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroBairro.setCampoOrderBy(FiltroBairro.NOME);

				colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());
			}

			Bairro bairro = new Bairro();

			for(Iterator<Bairro> iterator = colecaoBairro.iterator(); iterator.hasNext();){
				bairro = (Bairro) iterator.next();
				retorno.put(String.valueOf(bairro.getNome()), String.valueOf(bairro.getId()));
			}
		}

		return retorno;
	}

	public Collection carregaQuadrasPorSetor(String idSetor){

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, idSetor));
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroQuadra.setCampoOrderBy(FiltroQuadra.NUMERO_QUADRA);

		Collection<Quadra> colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

		Collection retorno = new ArrayList();
		Quadra quadra = new Quadra();

		for(Iterator<Quadra> iterator = colecaoQuadra.iterator(); iterator.hasNext();){
			quadra = (Quadra) iterator.next();
			retorno.add(String.valueOf(quadra.getNumeroQuadra()));
			retorno.add(String.valueOf(quadra.getId()));
		}

		return retorno;
	}

	public Collection carregaRotasPorSetor(String idSetor){

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, idSetor));
		filtroRota.setCampoOrderBy(FiltroRota.CODIGO_ROTA);

		Collection<Rota> colecaoRota = fachada.pesquisar(filtroRota, Rota.class.getName());

		Collection retorno = new ArrayList();
		Rota rota = new Rota();

		for(Iterator<Rota> iterator = colecaoRota.iterator(); iterator.hasNext();){
			rota = (Rota) iterator.next();
			retorno.add(String.valueOf(rota.getCodigo()));
			retorno.add(String.valueOf(rota.getId()));
		}

		return retorno;
	}

	public Map<String, String> carregaLogradourosPorBairro(String idBairro){

		FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();
		filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.INDICADORUSO_BAIRRO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_BAIRRO, idBairro));
		filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouroBairro.LOGRADOURO);
		filtroLogradouroBairro.setCampoOrderBy(FiltroLogradouroBairro.NOME_LOGRADOURO);

		Collection<LogradouroBairro> colecaoLogradouroBairro = fachada.pesquisar(filtroLogradouroBairro, LogradouroBairro.class.getName());

		TreeMap<String, String> retorno = new TreeMap<String, String>();
		Logradouro logradouro = new Logradouro();

		for(Iterator<LogradouroBairro> iterator = colecaoLogradouroBairro.iterator(); iterator.hasNext();){
			logradouro = (Logradouro) iterator.next().getLogradouro();
			retorno.put(String.valueOf(logradouro.getNome()), String.valueOf(logradouro.getId()));
		}

		return retorno;
	}

	public Map<String, String> carregaSubGrupoTipoPorTipo(String idTipo){

		FiltroServicoTipoSubgrupo filtroServicoTipoSubGrupo = new FiltroServicoTipoSubgrupo();

		filtroServicoTipoSubGrupo.adicionarParametro(new ParametroSimples(FiltroServicoTipoSubgrupo.INDICADOR_USO_SERVICO_TIPO_GRUPO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroServicoTipoSubGrupo.adicionarParametro(new ParametroSimples(FiltroServicoTipoSubgrupo.ID_SERVICO_TIPO_GRUPO, Long
						.parseLong(idTipo)));
		// filtroServicoTipoSubGrupo.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouroBairro.LOGRADOURO);
		filtroServicoTipoSubGrupo.setCampoOrderBy(FiltroServicoTipoSubgrupo.DESCRICAO);

		Collection<ServicoTipoSubgrupo> colecaoLogradouroBairro = fachada.pesquisar(filtroServicoTipoSubGrupo,
						ServicoTipoSubgrupo.class.getName());

		Map<String, String> retorno = new HashMap<String, String>();

		for(ServicoTipoSubgrupo subgrupo : colecaoLogradouroBairro){
			retorno.put(subgrupo.getId().toString(), subgrupo.getDescricao());
		}

		return retorno;
	}

	public Map<String, String> carregaSubcategorias(String id){

		FiltroSubCategoria filtro = new FiltroSubCategoria();
		filtro.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, Integer.valueOf(id)));
		filtro.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);
		Collection<Subcategoria> retorno1 = fachada.pesquisar(filtro, Subcategoria.class.getName());
		LinkedHashMap<String, String> retorno = new LinkedHashMap<String, String>();
		for(Subcategoria a : retorno1){

			retorno.put(a.getId().toString(), a.getDescricao());
		}

		return retorno;
	}

	public Map<String, String> carregaLotesPorQuadra(String idQuadra){

		Map<String, String> retorno = new TreeMap<String, String>(new Comparator() {

			public int compare(Object a, Object b){

				return (Integer.valueOf((String) a)).compareTo(Integer.valueOf((String) b));

			}
		});

		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_ID, idQuadra));
		filtroImovel.setCampoOrderBy(FiltroImovel.LOTE);

		Collection<Imovel> colecaoImoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		if(!Util.isVazioOrNulo(colecaoImoveis)){
			for(Imovel imovel : colecaoImoveis){
				Short lote = imovel.getLote();

				if(!retorno.containsKey(String.valueOf(lote))){
					retorno.put(String.valueOf(lote), String.valueOf(lote));
				}
			}
		}

		return retorno;
	}

	public Map<String, String> carregaMotivoExclusao(String idNegativador){

		FiltroNegativadorExclusaoMotivo fnem = new FiltroNegativadorExclusaoMotivo();
		fnem.adicionarParametro(new ParametroSimples(FiltroNegativadorExclusaoMotivo.NEGATIVADOR_ID, idNegativador));
		fnem.setCampoOrderBy("codigoExclusaoMotivo");
		Collection colecaoNegativadorExclusaoMotivo = fachada.pesquisar(fnem, NegativadorExclusaoMotivo.class.getName());

		Map<String, String> dados = new HashMap<String, String>();
		NegativadorExclusaoMotivo negativadorExclusaoMotivo = new NegativadorExclusaoMotivo();

		for(Iterator<NegativadorExclusaoMotivo> iterator = colecaoNegativadorExclusaoMotivo.iterator(); iterator.hasNext();){
			negativadorExclusaoMotivo = (NegativadorExclusaoMotivo) iterator.next();
			dados.put(String.valueOf(negativadorExclusaoMotivo.getId()),
							String.valueOf(negativadorExclusaoMotivo.getDescricaoExclusaoMotivo()));

		}

		return dados;
	}

	public String pesquisaLocalidade(String id){

		FiltroLocalidade filtro = new FiltroLocalidade();
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(id)));
		Collection<Localidade> col = fachada.pesquisar(filtro, Localidade.class.getName());
		Localidade localidade = null;
		if(col != null && !col.isEmpty()){
			localidade = col.iterator().next();
		}
		if(localidade == null){
			return "-1";
		}else{
			return localidade.getDescricao();
		}
	}

	public String pesquisaUnidade(String id){

		FiltroUnidadeOrganizacional filtro = new FiltroUnidadeOrganizacional();
		filtro.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, Integer.valueOf(id)));
		Collection<UnidadeOrganizacional> col = fachada.pesquisar(filtro, UnidadeOrganizacional.class.getName());
		UnidadeOrganizacional UnidadeOrganizacional = null;
		if(col != null && !col.isEmpty()){
			UnidadeOrganizacional = col.iterator().next();
		}
		if(UnidadeOrganizacional == null){
			return "-1";
		}else{
			return UnidadeOrganizacional.getDescricao();
		}
	}

	public String pesquisaEquipe(String id){

		FiltroEquipe filtro = new FiltroEquipe();
		filtro.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, Integer.valueOf(id)));
		Collection<Equipe> col = fachada.pesquisar(filtro, Equipe.class.getName());
		Equipe equipe = null;
		if(col != null && !col.isEmpty()){
			equipe = col.iterator().next();
		}
		if(equipe == null){
			return "-1";
		}else{
			return equipe.getNome();
		}
	}

	public boolean verificaAcaoPrecedente(String idAcaoCobranca){

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.adicionarParametro(new ParametroNaoNulo(FiltroCobrancaAcao.COBRANCA_ACAO_PRECEDENTE_ID));
		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, Integer.valueOf(idAcaoCobranca)));

		Collection<CobrancaAcao> colecaoCobrancaAcao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
		boolean retorno = false;

		if(colecaoCobrancaAcao != null && !colecaoCobrancaAcao.isEmpty()){
			retorno = true;
		}

		return retorno;
	}

	public String validaRemocaoListaClienteImovelNegativado(String idImovel, String idCamposImovelCliente){

		String retorno = "";

		ArrayList<String> lista = new ArrayList<String>((LinkedList<String>) Util.separarCamposString(";", idCamposImovelCliente));

		if(lista != null && !lista.isEmpty()){
			Iterator clienteIterator = lista.iterator();

			while(clienteIterator.hasNext()){
				String idCliente = (String) clienteIterator.next();
				boolean existe = Fachada.getInstancia().verificarExistenciaNegativacaClienteImovel(Integer.valueOf(idImovel),
								Integer.valueOf(idCliente));
				if(existe){
					if(Util.isVazioOuBranco(retorno)){
						retorno = "ATENÇÃO: \n";
					}else{
						retorno = retorno + "\n\n";
					}
					String[] params = new String[2];
					params[0] = idCliente;
					params[1] = idImovel;
					retorno = retorno + ConstantesAplicacao.get("atencao.cliente.em.processo.negativacao", params);
				}
			}
		}

		return retorno;
	}

	/**
	 * [UC0157] Simular Cálculo da Conta
	 * Método que verifica se a situação de ligação de esgoto selecionada é faturável.
	 * 
	 * @author Anderson Italo
	 * @date 18/01/2012
	 */
	public int verificaIndicadorFaturavelLigacaoEsgotoSituacao(String idLigacaoEsgotoSituacao){

		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, Integer
						.valueOf(idLigacaoEsgotoSituacao)));

		Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());

		int retorno = ConstantesSistema.NAO.shortValue();

		if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoSituacao)){

			LigacaoEsgotoSituacao situacao = colecaoLigacaoEsgotoSituacao.iterator().next();

			if(situacao.getIndicadorFaturamentoSituacao().shortValue() == ConstantesSistema.SIM.shortValue()){

				retorno = ConstantesSistema.SIM.shortValue();
			}
		}

		return retorno;
	}

	public Map<String, String> carregaLocalidadePorGerenciaRegional(String idGerenciaRegional){

		TreeMap<String, String> retorno = new TreeMap<String, String>();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		// filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.GERENCIAREGIONAL);

		if(idGerenciaRegional.equals("") == false){

			if(!Integer.valueOf(idGerenciaRegional).equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_GERENCIA, Integer.valueOf(idGerenciaRegional)));
			}
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);

			Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			for(Localidade localidade : colecaoLocalidade){
				System.out.println(String.valueOf(localidade.getDescricao()));
			}

			for(Localidade localidade : colecaoLocalidade){
				retorno.put(String.valueOf(localidade.getDescricao()), String.valueOf(localidade.getId()));
			}

		}

		return retorno;
	}

	/**
	 * [UC0157] Simular Cálculo da Conta
	 * Método que verifica se a situação de ligação de água selecionada é faturável.
	 */
	public int verificaIndicadorFaturavelLigacaoAguaSituacao(String idLigacaoAguaSituacao){

		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, Integer
						.valueOf(idLigacaoAguaSituacao)));

		Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,
						LigacaoAguaSituacao.class.getName());

		int retorno = ConstantesSistema.NAO.shortValue();

		if(!Util.isVazioOrNulo(colecaoLigacaoAguaSituacao)){

			LigacaoAguaSituacao situacao = colecaoLigacaoAguaSituacao.iterator().next();

			if(situacao.getIndicadorFaturamentoSituacao().shortValue() == ConstantesSistema.SIM.shortValue()){

				retorno = ConstantesSistema.SIM.shortValue();
			}
		}

		return retorno;
	}

	public void configurarConta(Integer idConta, String tipo){

		HttpSession sessao = WebContextFactory.get().getSession();
		Collection<Integer> idsContaEP = (Collection<Integer>) sessao.getAttribute("idsContaEP");

		if(idsContaEP != null && tipo.equals("NB")){

			Collection<Integer> ids = new ArrayList<Integer>();

			for(Integer id : idsContaEP){

				if(!id.equals(idConta)){

					ids.add(id);

				}

			}

			sessao.setAttribute("idsContaEP", ids);

		}else if(idsContaEP != null && tipo.equals("EP")){

			if(!idsContaEP.contains(idConta)){

				idsContaEP.add(idConta);

			}

		}else if(idsContaEP == null && tipo.equals("EP")){

			idsContaEP = new ArrayList<Integer>();
			idsContaEP.add(idConta);

			sessao.setAttribute("idsContaEP", idsContaEP);

		}

	}

	/**
	 * Método responsável por obter o código da rota
	 * 
	 * @param idRota
	 * @return
	 */
	public Short obterCodigoRota(Integer idRota){

		return Fachada.getInstancia().obterCodigoRota(idRota);

	}

	/**
	 * [FS0027] Validade motivo da retificação informado.
	 * 
	 * @param idMotivoRetificacao
	 */
	public boolean validarMotivoRetificacaoInformado(String idMotivoRetificacaoStr, String idImovelStr){

		boolean error = false;

		/*
		 * 1. Caso existam, no ano corrente, contas retificadas com motivo de retificação igual a
		 * "OCORRENCIA DE CONSUMO" (existe ocorrência na Tabela CONTA com IMOV_ID = matrícula do
		 * imóvel e CNTA_DTRETIFICACAO entre o primeiro dia do ano corrente e a data corrente e
		 * CMRT_ID = (PASI_VLPARAMETRO na tabela PARAMETRO_SISTEMA para PASI_CDPARAMETRO =
		 * 'P_MOTIVO_RETIFICACAO_OCORRENCIA_CONSUMO'))
		 * ou
		 * (existe ocorrência na Tabela CONTA_HISTORICO com IMOV_ID = matrícula do imóvel e
		 * CNHI_DTRETIFICACAO entre o primeiro dia do ano corrente e a data corrente e CMRT_ID =
		 * (PASI_VLPARAMETRO na tabela PARAMETRO_SISTEMA para PASI_CDPARAMETRO =
		 * 'P_MOTIVO_RETIFICACAO_OCORRENCIA_CONSUMO'))
		 */
		if(idMotivoRetificacaoStr != null && !idMotivoRetificacaoStr.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			Integer idMotivoRetificacao = Util.converterStringParaInteger(idMotivoRetificacaoStr);
			int paramMotivoRetificaoOcorrenciaConsumo = 0;
			int qtdContasRetificadasPorMotivoRetificacao = 0;

			// id do parâmetro
			try{
				paramMotivoRetificaoOcorrenciaConsumo = Util
								.converterStringParaInteger((String) ParametroFaturamento.P_MOTIVO_RETIFICACAO_OCORRENCIA_CONSUMO
												.executar(ExecutorParametrosFaturamento.getInstancia()));
			}catch(ControladorException e){
				error = true;
			}

			// retificações permitidas por ano
			Integer qtdRAEncerradaAnoParametro = null;
			try{
				qtdRAEncerradaAnoParametro = Util
								.converterStringParaInteger((String) ParametroFaturamento.P_QUANTIDADE_RA_ENCERRADA_ANO_BLOQUEIO_RETIFICACAO
												.executar(ExecutorParametrosFaturamento.getInstancia()));
			}catch(ControladorException e){
				error = true;
			}

			// motivo selecionado = motivo do parâmetro
			if(idMotivoRetificacao.equals(paramMotivoRetificaoOcorrenciaConsumo)){

				qtdContasRetificadasPorMotivoRetificacao = Fachada.getInstancia().obterQtdContasRetificadasPorMotivoRetificacao(
								Integer.parseInt(idImovelStr), paramMotivoRetificaoOcorrenciaConsumo);
			}

			/*
			 * e a quantidade de contas encontradas seja
			 * maior que o valor contido em PASI_VLPARAMETRO na tabela PARAMETRO_SISTEMA para
			 * PASI_CDPARAMETRO = 'P_QUANTIDADE_RA_ENCERRADA_ANO_BLOQUEIO_RETIFICACAO'), exibir a
			 * mensagem "A quantidade de contas retificadas por Ocorrência de Consumo no ano de
			 * <<ano
			 * corrente>> supera a quantidade permitida. Utilize outro motivo de retificação" e
			 * retornar
			 * para o passo correspondente no fluxo principal.
			 */
			if(!error && qtdContasRetificadasPorMotivoRetificacao > qtdRAEncerradaAnoParametro){
				error = true;
			}
		}

		return error;
	}

	public Map<String, String> obterCreditoOrigem(Integer idCreditoTipo, Short indicadorUsoLivre){

		Map<String, String> dados = new LinkedHashMap<String, String>();

		HttpSession sessao = WebContextFactory.get().getSession();
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		boolean temPermissaoEspecial = fachada.verificarPermissaoEspecial(PermissaoEspecial.GERAR_DEVOLUCAO_DE_VALORES, usuarioLogado);

		Short indicadorUsoLivreLocal = indicadorUsoLivre;
		if(temPermissaoEspecial == ConstantesSistema.SENHA_ESPECIAL){
			indicadorUsoLivreLocal = null; // Se tiver permissão, poderá pesquisar com todos os
											// tipos
											// de indicadores de uso.
		}

		Collection<CreditoOrigem> coll = Fachada.getInstancia().listarCreditoOrigem(idCreditoTipo, indicadorUsoLivreLocal);

		if(coll != null){

			for(CreditoOrigem creditoOrigem : coll){

				dados.put(String.valueOf(creditoOrigem.getId()), creditoOrigem.getDescricao());

			}

		}

		return dados;

	}

	public Map<String, String> configurarCreditoARealizarImovel(Long idCreditoRealizado, String valorCredito){

		Map<String, String> retorno = new LinkedHashMap<String, String>();
		String msg = null;
		String vlFormatado = null;

		HttpSession sessao = WebContextFactory.get().getSession();
		Collection<CreditoRealizado> coll = (Collection<CreditoRealizado>) sessao.getAttribute("colecaoCreditoRealizado");

		if(coll != null && StringUtils.isNotBlank(valorCredito)){

			for(CreditoRealizado creditoRealizado : coll){

				if(GcomAction.obterTimestampIdObjeto(creditoRealizado) == idCreditoRealizado.longValue()){

					CreditoOrigem creditoOrigem = creditoRealizado.getCreditoOrigem();

					if(creditoOrigem.getId().equals(CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)
									&& creditoOrigem.getIndicadorUsoLivre() != null
									&& creditoOrigem.getIndicadorUsoLivre().equals(ConstantesSistema.NAO)){

						BigDecimal vlCreditoInformado = Util.formatarStringMoedaRealparaBigDecimal(valorCredito,
										Parcelamento.CASAS_DECIMAIS);
						BigDecimal vlCredito = creditoRealizado.getValorCredito();

						if(vlCreditoInformado.compareTo(vlCredito) != 0){

							BigDecimal vlTotalDisponivel = this.obterValorCreditoDisponivel(creditoRealizado.getCreditoTipo().getId(),
											sessao);

							if(vlTotalDisponivel != null){

								BigDecimal vl = BigDecimal.ZERO;

								if(vlCredito.compareTo(vlCreditoInformado) == 1){

									vl = vlCredito.subtract(vlCreditoInformado);

									this.atualizarValorCreditoDisponivel(creditoRealizado.getCreditoTipo().getId(),
													vlTotalDisponivel.add(vl), sessao);

									creditoRealizado.setValorCredito(vlCreditoInformado);

								}else{

									vl = vlCreditoInformado.subtract(vlCredito);

									if(vl.compareTo(vlTotalDisponivel) == 1){

										msg = "O valor do crédito informado não deve ser maior que o valor disponível de "
														+ Util.formatarMoedaReal(vlTotalDisponivel);
										vlFormatado = Util.formatarMoedaReal(vlCredito);

									}else{

										this.atualizarValorCreditoDisponivel(creditoRealizado.getCreditoTipo().getId(),
														vlTotalDisponivel.subtract(vl), sessao);

										creditoRealizado.setValorCredito(vlCreditoInformado);

									}

								}

							}else{

								if(vlCreditoInformado.compareTo(creditoRealizado.getValorCredito()) == -1){

									this.atualizarValorCreditoDisponivel(creditoRealizado.getCreditoTipo().getId(), creditoRealizado
													.getValorCredito().subtract(vlCreditoInformado), sessao);

									creditoRealizado.setValorCredito(vlCreditoInformado);

								}else{

									msg = "Não existe crédito de devolução de valores relacionado ao imóvel";
									vlFormatado = Util.formatarMoedaReal(vlCredito);

								}

							}

						}

					}

					break;

				}

			}

		}

		retorno.put("msg", msg);
		retorno.put("valor", vlFormatado);

		return retorno;

	}

	public String obterValorCreditoSugerido(Integer idCreditoTipo, Integer idCreditoOrigem){

		String valorRetorno = null;

		if(idCreditoTipo != null && idCreditoOrigem != null){

			if(idCreditoOrigem.equals(CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)){

				BigDecimal valorSugerido = this.obterValorCreditoDisponivel(idCreditoTipo, WebContextFactory.get().getSession());

				if(valorSugerido != null){

					valorRetorno = Util.formatarMoedaReal(valorSugerido);

				}

			}

		}

		return valorRetorno;

	}

	private BigDecimal obterValorCreditoDisponivel(Integer idCreditoTipo, HttpSession sessao){

		Map<Integer, BigDecimal> mapCreditos = (Map<Integer, BigDecimal>) sessao.getAttribute(ConstantesSistema.CREDITO_TOTAL_DISPONIVEL);

		return mapCreditos.get(idCreditoTipo);

	}

	private void atualizarValorCreditoDisponivel(Integer idCreditoTipo, BigDecimal valorAtualizado, HttpSession sessao){

		Map<Integer, BigDecimal> mapCreditos = (Map<Integer, BigDecimal>) sessao.getAttribute(ConstantesSistema.CREDITO_TOTAL_DISPONIVEL);

		mapCreditos.remove(idCreditoTipo);

		mapCreditos.put(idCreditoTipo, valorAtualizado);

		sessao.setAttribute(ConstantesSistema.CREDITO_TOTAL_DISPONIVEL, mapCreditos);

	}

	/**
	 * Método responsável por obter descrição da rota
	 * 
	 * @param idRota
	 * @return
	 */
	public String obterRota(Integer idRota){

		return Fachada.getInstancia().obterRota(idRota);

	}

	/**
	 * Consulta os pagamentos historico cujo admite devolução.
	 * 
	 * @param idOrigemCredito
	 * @param idImovel
	 * @return
	 */
	public Map<String, String> obterPagamentosHistoricoAdmiteDevolucao(String idOrigemCredito, String idImovel, String tipoCredito,
					boolean creditoARealizar){

		HttpSession sessao = WebContextFactory.get().getSession();
		Map<String, String> dados = new HashMap<String, String>();
		String mensagem = null;

		String tiposDeCreditoOrigem = null;
		try{
			tiposDeCreditoOrigem = ParametroFaturamento.P_TIPOS_CREDITO_ORIGEM_DOCS_PGTO_DUPL_E_EXCES.executar();
		}catch(ControladorException e){
			e.printStackTrace();
		}

		if(Util.isVazioOuBranco(idImovel) || "-1".equals(idImovel)){
			mensagem = "Informe a Matrícula do imóvel.";
		}

		if(Util.isVazioOuBranco(idOrigemCredito)){
			mensagem = "Informe a Origem do Crédito.";
		}

		if(Util.isVazioOuBranco(tipoCredito) || "-1".equals(idImovel)){
			mensagem = "Informe o Tipo de Crédito.";
		}

		dados.put("msg", mensagem);

		boolean condizComTiposDeCreditoOrigem = false;
		boolean pesquisar = mensagem == null && !"-1".equals(idOrigemCredito);

		if(pesquisar && tiposDeCreditoOrigem != null){
			StringTokenizer token = new StringTokenizer(tiposDeCreditoOrigem, ",");
			do{
				if(token.nextToken().equals(idOrigemCredito)){
					condizComTiposDeCreditoOrigem = true;
				}

			}while(token.hasMoreElements());
		}

		if(pesquisar && condizComTiposDeCreditoOrigem){

			Collection<PagamentoHistoricoAdmiteDevolucaoHelper> colecao = fachada.consultarPagamentosHistoricoAdmiteDevolucao(
							Integer.valueOf(idImovel), creditoARealizar);

			sessao.setAttribute("pagamentosHistoricoAdmiteDevolucao", colecao);
		}else{
			sessao.removeAttribute("pagamentosHistoricoAdmiteDevolucao");
		}

		return dados;
	}

	public String[] obgterInformacaoPagamentoHistorico(String idPagamentoHistorico, String matriculaImovel){

		HttpSession sessao = WebContextFactory.get().getSession();
		String[] retorno = new String[] {"false", null};

		Collection<PagamentoHistoricoAdmiteDevolucaoHelper> colecao = (Collection<PagamentoHistoricoAdmiteDevolucaoHelper>) sessao
						.getAttribute("pagamentosHistoricoAdmiteDevolucao");

		PagamentoHistoricoAdmiteDevolucaoHelper pagamento = null;

		for(PagamentoHistoricoAdmiteDevolucaoHelper item : colecao){
			if(item.getIdPagamentoHistorico().equals(Integer.valueOf(idPagamentoHistorico))){
				pagamento = item;
				break;
			}
		}

		if(pagamento != null){
			Collection<Integer> tiposRelacionadoNoUC = Arrays.asList(DebitoCreditoSituacao.NORMAL, DebitoCreditoSituacao.RETIFICADA,
							DebitoCreditoSituacao.INCLUIDA, DebitoCreditoSituacao.ENTRADA_DE_PARCELAMENTO); // 0,
																											// 1,
																											// 2,
																											// 4
			Integer debitoCreditoIndicadorAtual = pagamento.getDebitoCreditoIndicadorAtual();
			String condicaoJS = "false";

			BigDecimal valorDiferenca = pagamento.getValorPagamento().subtract(pagamento.getValorDocumento());
			String valorCredito = Util.formataBigDecimal(valorDiferenca, 2, false);
			String valorPagamentoStr = Util.formataBigDecimal(pagamento.getValorPagamento(), 2, false);

			if(tiposRelacionadoNoUC.contains(debitoCreditoIndicadorAtual) && !valorCredito.equals("0,00")){
				if(valorDiferenca.signum() == 1){
					valorCredito = Util.formataBigDecimal(valorDiferenca, 2, false);
					condicaoJS = "true";
				}
			}

			if(existeMaisDeUmPagamentoAssociadoAoDocumento(pagamento, matriculaImovel)){
				condicaoJS = "false";
			}

			if(condicaoJS.equals("false")){
				condicaoJS = "true";
				valorCredito = valorPagamentoStr;
			}

			retorno = new String[] {condicaoJS, valorCredito};
		}
		return retorno;
	}

	private boolean existeMaisDeUmPagamentoAssociadoAoDocumento(PagamentoHistoricoAdmiteDevolucaoHelper pagamento, String matriculaImovel){

		boolean retorno = false;

		if(fachada.pesquisarQuantidadePagamentosHistoricoCount(pagamento, matriculaImovel) > 1){
			retorno = true;
		}

		return retorno;
	}

	public Collection carregarElo(){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		ArrayList<String> retorno = new ArrayList();
		ArrayList<Localidade> retornoLocalidade = new ArrayList();
		Localidade localidade = new Localidade();

		for(Iterator<Localidade> iterator = colecaoLocalidade.iterator(); iterator.hasNext();){
			localidade = (Localidade) iterator.next();
			if(localidade.getLocalidade() != null){
				retornoLocalidade.add(localidade.getLocalidade());
			}
		}

		Collections.sort(retornoLocalidade, new Comparator() {

			public int compare(Object o1, Object o2){

				Localidade l1 = (Localidade) o1;
				Localidade l2 = (Localidade) o2;
				return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
			}
		});

		for(Iterator<Localidade> iterator = retornoLocalidade.iterator(); iterator.hasNext();){
			localidade = (Localidade) iterator.next();

			retorno.add(String.valueOf(localidade.getDescricao()));
			retorno.add(String.valueOf(localidade.getId()));
		}

		return retorno;
	}

	public Collection carregarFaturamentoGrupo(){

		ArrayList<String> retorno = new ArrayList();

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);
		Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		for(Iterator<FaturamentoGrupo> iterator = colecaoFaturamentoGrupo.iterator(); iterator.hasNext();){
			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) iterator.next();

			retorno.add(String.valueOf(faturamentoGrupo.getDescricao()));
			retorno.add(String.valueOf(faturamentoGrupo.getId()));
		}

		return retorno;
	}

	public Collection carregarGerenciaRegioanl(){

		ArrayList<String> retorno = new ArrayList();

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		Collection<GerenciaRegional> colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		for(Iterator<GerenciaRegional> iterator = colecaoGerenciaRegional.iterator(); iterator.hasNext();){
			GerenciaRegional gerenciaRegional = (GerenciaRegional) iterator.next();

			retorno.add(String.valueOf(gerenciaRegional.getNome()));
			retorno.add(String.valueOf(gerenciaRegional.getId()));
		}

		return retorno;
	}

	/**
	 * [UC3156] Simular Cálculo da Conta Dados Reais
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	public Map<String, String> carregaConsumoTarifaVigencias(String id){

		FiltroConsumoTarifaVigencia filtro = new FiltroConsumoTarifaVigencia();
		filtro.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaVigencia.CONSUMO_TARIFA_ID, Integer.valueOf(id)));
		filtro.setCampoOrderByDesc(FiltroConsumoTarifaVigencia.DATA_VIGENCIA);
		Collection<ConsumoTarifaVigencia> retorno1 = fachada.pesquisar(filtro, ConsumoTarifaVigencia.class.getName());
		LinkedHashMap<String, String> retorno = new LinkedHashMap<String, String>();
		for(ConsumoTarifaVigencia a : retorno1){

			retorno.put(a.getId().toString(), a.getDataVigenciaFormatada());
		}

		return retorno;
	}

	/**
	 * [UC3156] Simular Cálculo da Conta Dados Reais
	 * 
	 * @author Anderson Italo
	 * @date 27/09/2014
	 */
	public String obterQuantidadeContasComVigenciaValida(String idConsumoTarifaVigenciaRecalcular, String quantidadeContasFiltroSelecionado){

		String quantidadeContasComVigenciaValida = quantidadeContasFiltroSelecionado;
		HttpSession sessao = WebContextFactory.get().getSession();

		FiltroContaSimularCalculoHelper filtroContaSimularCalculoHelper = (FiltroContaSimularCalculoHelper) sessao
						.getAttribute("filtroContaSimularCalculoHelper");

		if(Util.verificarIdNaoVazio(idConsumoTarifaVigenciaRecalcular)){

			filtroContaSimularCalculoHelper.setIdConsumoTarifaVigenciaRecalcular(Util.obterInteger(idConsumoTarifaVigenciaRecalcular));
		}else{

			filtroContaSimularCalculoHelper.setIdConsumoTarifaVigenciaRecalcular(null);
		}

		sessao.setAttribute("filtroContaSimularCalculoHelper", filtroContaSimularCalculoHelper);

		Integer quantidadeRegistros = fachada.pesquisarTotalRegistrosContasSimularCalculoDadosReais(filtroContaSimularCalculoHelper);

		quantidadeContasComVigenciaValida = quantidadeRegistros.toString();

		return quantidadeContasComVigenciaValida;
	}

	public String carregaTotalizadorDiarioPagamento(String indicadorTotalizarPorDataPagamento){

		return indicadorTotalizarPorDataPagamento;
	}

}
