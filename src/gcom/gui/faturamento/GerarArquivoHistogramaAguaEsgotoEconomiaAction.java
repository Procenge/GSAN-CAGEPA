package gcom.gui.faturamento;

import gcom.cadastro.imovel.CategoriaTipo;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.ConsumoFaixaCategoria;
import gcom.faturamento.bean.FiltrarEmitirHistogramaAguaEconomiaHelper;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioHistogramaAguaEsgotoEconomiaTxt;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarArquivoHistogramaAguaEsgotoEconomiaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("gerarArquivoHistograma");

		// Form
		EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form = (EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm) actionForm;

		FiltrarEmitirHistogramaEsgotoEconomiaHelper filtroEsgoto = new FiltrarEmitirHistogramaEsgotoEconomiaHelper();

		// Opção de Totalização
		filtroEsgoto.setOpcaoTotalizacao(Integer.parseInt(form.getOpcaoTotalizacao()));

		// Mês/Ano do Faturamento
		Integer anoMes = Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoFaturamento());

		SistemaParametro sistemaParametro = this.getFachada().pesquisarParametrosDoSistema();

		if(sistemaParametro.getAnoMesFaturamento().intValue() < anoMes.intValue()){
			throw new ActionServletException("atencao.mes_ano.faturamento.inferior", null, "" + sistemaParametro.getAnoMesFaturamento());
		}

		filtroEsgoto.setMesAnoFaturamento(anoMes);

		// Gerência Regional
		if(form.getGerenciaRegional() != null && !form.getGerenciaRegional().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			GerenciaRegional gerencia = new GerenciaRegional();
			gerencia.setId(Integer.valueOf(form.getGerenciaRegional()));

			filtroEsgoto.setGerenciaRegional(gerencia);

		}

		// Unidade de Negocio
		if(form.getUnidadeNegocio() != null && !form.getUnidadeNegocio().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			UnidadeNegocio unidade = new UnidadeNegocio();
			unidade.setId(Integer.valueOf(form.getUnidadeNegocio()));

			filtroEsgoto.setUnidadeNegocio(unidade);

		}

		// Elo Pólo
		if(form.getEloPolo() != null && !form.getEloPolo().equals("")){

			Localidade eloPolo = new Localidade();
			eloPolo.setId(Integer.valueOf(form.getEloPolo()));

			filtroEsgoto.setEloPolo(eloPolo);

		}

		// Localidade
		if(form.getLocalidade() != null && !form.getLocalidade().equals("")){

			Localidade localidade = new Localidade();
			localidade.setId(Integer.valueOf(form.getLocalidade()));

			filtroEsgoto.setLocalidade(localidade);

		}

		// Setor Comercial
		if(form.getSetorComercial() != null && !form.getSetorComercial().equals("")){

			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, form
							.getSetorComercial()));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, form.getLocalidade()));

			// Recupera Setor Comercial
			Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			filtroEsgoto.setSetorComercial(setorComercial);
		}

		// Quadra
		if(form.getQuadra() != null && !form.getQuadra().equals("")){

			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, form.getQuadra()));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, filtroEsgoto.getSetorComercial().getId()));

			// Recupera Quadra
			Collection colecaoQuadra = this.getFachada().pesquisar(filtroQuadra, Quadra.class.getName());

			Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

			filtroEsgoto.setQuadra(quadra);

		}

		// Tipo Categoria
		if(form.getTipoCategoria() != null && !form.getTipoCategoria().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			CategoriaTipo tipoCategoria = new CategoriaTipo();
			tipoCategoria.setId(Integer.valueOf(form.getTipoCategoria()));

			filtroEsgoto.setTipoCategoria(tipoCategoria);

		}

		// validação das Faixas de Consumo
		this.validarColecaoFaixaConsumoCategoria(form);

		// coleção de faixas que será passada para o filtro
		LinkedHashMap<String, Collection<ConsumoFaixaCategoria>> linkedHashMapConsumoFaixaCategoria = new LinkedHashMap<String, Collection<ConsumoFaixaCategoria>>(
						form.getLinkedHashMapConsumoFaixaCategoria());

		// Categoria
		if(form.getCategoria() != null && form.getCategoria().length > 0){

			Collection<Integer> colecao = new ArrayList<Integer>();
			linkedHashMapConsumoFaixaCategoria.clear();

			String[] array = form.getCategoria();
			for(int i = 0; i < array.length; i++){

				Integer key = Integer.valueOf(array[i]);

				if(key.intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){

					colecao.add(key);

					Collection<ConsumoFaixaCategoria> colecaoFaixa = (Collection<ConsumoFaixaCategoria>) form
									.getLinkedHashMapConsumoFaixaCategoria().get(key.toString());

					linkedHashMapConsumoFaixaCategoria.put(key.toString(), colecaoFaixa);
				}
			}

			// form.setLinkedHashMapConsumoFaixaCategoria(linkedHashMapConsumoFaixaCategoria);
			filtroEsgoto.setColecaoCategoria(colecao);

		}

		// Faixa de Consumo por Categoria
		filtroEsgoto.setLinkedHashMapConsumoFaixaCategoria(linkedHashMapConsumoFaixaCategoria);

		// Tarifa
		if(form.getTarifa() != null && form.getTarifa().length > 0){

			Collection<Integer> colecao = new ArrayList();

			String[] array = form.getTarifa();
			for(int i = 0; i < array.length; i++){
				if(Integer.valueOf(array[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecao.add(Integer.valueOf(array[i]));
				}
			}

			filtroEsgoto.setColecaoTarifa(colecao);

		}

		// Perfil do Imovel
		if(form.getPerfilImovel() != null && form.getPerfilImovel().length > 0){

			Collection<Integer> colecao = new ArrayList();

			String[] array = form.getPerfilImovel();
			for(int i = 0; i < array.length; i++){
				if(Integer.valueOf(array[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecao.add(Integer.valueOf(array[i]));
				}
			}

			filtroEsgoto.setColecaoPerfilImovel(colecao);

		}

		// Esfera de Poder
		if(form.getEsferaPoder() != null && form.getEsferaPoder().length > 0){

			Collection<Integer> colecao = new ArrayList();

			String[] array = form.getEsferaPoder();
			for(int i = 0; i < array.length; i++){
				if(Integer.valueOf(array[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecao.add(Integer.valueOf(array[i]));
				}
			}

			filtroEsgoto.setColecaoEsferaPoder(colecao);

		}

		// Situacao da Ligacao de Esgoto
		if(form.getSituacaoLigacaoEsgoto() != null && form.getSituacaoLigacaoEsgoto().length > 0){

			Collection<Integer> colecao = new ArrayList();

			String[] array = form.getSituacaoLigacaoEsgoto();
			for(int i = 0; i < array.length; i++){
				if(Integer.valueOf(array[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecao.add(Integer.valueOf(array[i]));
				}
			}

			filtroEsgoto.setColecaoSituacaoLigacaoEsgoto(colecao);

		}

		// Perfil da Ligacao de Esgoto
		if(form.getPerfilLigacaoEsgoto() != null && form.getPerfilLigacaoEsgoto().length > 0){

			Collection<BigDecimal> colecao = new ArrayList();

			String[] array = form.getPerfilLigacaoEsgoto();
			for(int i = 0; i < array.length; i++){
				if(array[i] != null && new BigDecimal(array[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
					colecao.add(new BigDecimal(array[i]));
				}
			}

			filtroEsgoto.setColecaoPercentualLigacaoEsgoto(colecao);

		}

		// Indicador Consumo
		if(form.getConsumo() != null && !form.getConsumo().equals("")){

			filtroEsgoto.setConsumo(Short.valueOf(form.getConsumo()));
		}

		// Indicador Poco
		if(form.getPoco() != null && !form.getPoco().equals("")){

			filtroEsgoto.setPoco(Short.valueOf(form.getPoco()));
		}

		// Indicador Volume
		if(form.getVolumoFixoEsgoto() != null && !form.getVolumoFixoEsgoto().equals("")){

			filtroEsgoto.setVolumoFixoEsgoto(Short.valueOf(form.getVolumoFixoEsgoto()));
		}

		if(form.getTipoArquivo() != null && StringUtils.isNotBlank(form.getTipoArquivo())){

			filtroEsgoto.setTipoArquivo(Integer.valueOf(form.getTipoArquivo().trim()));

		}

		RelatorioHistogramaAguaEsgotoEconomiaTxt relatorio = new RelatorioHistogramaAguaEsgotoEconomiaTxt(
						this.getUsuarioLogado(httpServletRequest));
		relatorio.addParametro("filtroEsgoto", filtroEsgoto);
		relatorio.addParametro("filtroAgua", this.obterFiltroAgua(filtroEsgoto));
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);

		retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_PDF, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;

	}

	private FiltrarEmitirHistogramaAguaEconomiaHelper obterFiltroAgua(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtroEsgoto){

		FiltrarEmitirHistogramaAguaEconomiaHelper filtroAgua = new FiltrarEmitirHistogramaAguaEconomiaHelper();
		filtroAgua.setOpcaoTotalizacao(filtroEsgoto.getOpcaoTotalizacao());
		filtroAgua.setMesAnoFaturamento(filtroEsgoto.getMesAnoFaturamento());
		filtroAgua.setGerenciaRegional(filtroEsgoto.getGerenciaRegional());
		filtroAgua.setUnidadeNegocio(filtroEsgoto.getUnidadeNegocio());
		filtroAgua.setEloPolo(filtroEsgoto.getEloPolo());
		filtroAgua.setLocalidade(filtroEsgoto.getLocalidade());
		filtroAgua.setSetorComercial(filtroEsgoto.getSetorComercial());
		filtroAgua.setQuadra(filtroEsgoto.getQuadra());
		filtroAgua.setTarifa(filtroEsgoto.getTarifa());
		filtroAgua.setTipoCategoria(filtroEsgoto.getTipoCategoria());
		filtroAgua.setColecaoCategoria(filtroEsgoto.getColecaoCategoria());
		filtroAgua.setColecaoTarifa(filtroEsgoto.getColecaoTarifa());
		filtroAgua.setColecaoPerfilImovel(filtroEsgoto.getColecaoPerfilImovel());
		filtroAgua.setColecaoEsferaPoder(filtroEsgoto.getColecaoEsferaPoder());
		filtroAgua.setColecaoSituacaoLigacaoAgua(filtroEsgoto.getColecaoSituacaoLigacaoEsgoto());
		filtroAgua.setConsumo(filtroEsgoto.getConsumo());
		filtroAgua.setPoco(filtroEsgoto.getPoco());
		filtroAgua.setVolumoFixoAgua(filtroEsgoto.getVolumoFixoEsgoto());
		filtroAgua.setLinkedHashMapConsumoFaixaCategoria(filtroEsgoto.getLinkedHashMapConsumoFaixaCategoria());
		filtroAgua.setMedicao(filtroEsgoto.getMedicao());
		filtroAgua.setTipoGroupBy(filtroEsgoto.getTipoGroupBy());
		filtroAgua.setTipoOrderBy(filtroEsgoto.getTipoOrderBy());
		filtroAgua.setCategoria(filtroEsgoto.getCategoria());
		filtroAgua.setConsumoFaixaCategoria(filtroEsgoto.getConsumoFaixaCategoria());
		filtroAgua.setTipoArquivo(filtroEsgoto.getTipoArquivo());

		return filtroAgua;

	}

	private void validarColecaoFaixaConsumoCategoria(EmissaoHistogramaEsgotoEconomiaDadosInformarActionForm form){

		LinkedHashMap<String, Collection<ConsumoFaixaCategoria>> linkedHashMapConsumoFaixaCategoria = form
						.getLinkedHashMapConsumoFaixaCategoria();

		Iterator iteraChaves = linkedHashMapConsumoFaixaCategoria.keySet().iterator();
		while(iteraChaves.hasNext()){

			String chave = (String) iteraChaves.next();

			Collection<ConsumoFaixaCategoria> colecaoFaixaConsumoCategoria = (Collection<ConsumoFaixaCategoria>) linkedHashMapConsumoFaixaCategoria
							.get(chave);

			if(colecaoFaixaConsumoCategoria != null && !colecaoFaixaConsumoCategoria.isEmpty()){

				int faixaFimAnterior = 0;

				Iterator itera = colecaoFaixaConsumoCategoria.iterator();
				while(itera.hasNext()){
					ConsumoFaixaCategoria consumoFaixaCategoria = (ConsumoFaixaCategoria) itera.next();
					int faixaInicio = consumoFaixaCategoria.getNumeroFaixaInicio();

					/*
					 * if(faixaFimAnterior == 0 && faixaInicio != 0){
					 * throw new ActionServletException("atencao.consumo_faixa_primeira_zero");
					 * }
					 */

					if(faixaInicio != faixaFimAnterior){
						String[] msg = new String[3];

						msg[0] = "" + faixaInicio;
						msg[1] = "" + consumoFaixaCategoria.getNumeroFaixaFim();
						msg[2] = "" + faixaFimAnterior;

						throw new ActionServletException("atencao.consumo_faixa_intervalo_invalido", null, msg);
					}
					faixaFimAnterior = consumoFaixaCategoria.getNumeroFaixaFim() + 1;
				}
			}

		}

	}

}
