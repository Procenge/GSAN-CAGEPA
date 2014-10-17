
package gcom.agenciavirtual.faturamento;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * URL de acesso: /agenciavirtual/calcularContaWebService.do
 * 
 * @author Ado Rocha
 */
public class CalcularContaWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		String mesAnoConta = recuperarParametroString("mesAnoConta", LABEL_CAMPO_MES_ANO, true, false, request);
		String grupoFaturamento = recuperarParametroString("grupoFaturamento", LABEL_CAMPO_GRUPO_FATURAMENTO, false, false, request);
		String situacaoAguaConta = recuperarParametroString("situacaoAguaConta", LABEL_CAMPO_AGUA_SITUACAO, true, false, request);
		String situacaoEsgotoConta = recuperarParametroString("situacaoEsgotoConta", LABEL_CAMPO_ESGOTO_SITUACAO, true, false, request);
		String consumoAgua = recuperarParametroString("consumoAgua", LABEL_CAMPO_CONSUMO_AGUA, false, false, request);
		String volumeEsgoto = recuperarParametroString("volumeEsgoto", LABEL_CAMPO_VOLUME_ESGOTO, false, false, request);
		String idConsumoTarifaConta = recuperarParametroString("idConsumoTarifaConta", LABEL_CAMPO_TARIFA_CONSUMO, true, false, request);
		String perfilLigacaoEsgoto = recuperarParametroString("perfilLigacaoEsgoto", "Perfil da Ligação de Esgoto", true, false, request);

		String colecaoCategoriaString = recuperarParametroString("colecaoCategoria", "Categorias", true, false, request);


		Collection colecaoCategoria = this.converterStringEmColecao(colecaoCategoriaString);


		// Declaração de objetos
		String anoMesReferencia = null;
		Integer ligacaoAguaSituacaoID = null;
		Integer ligacaoEsgotoSituacaoID = null;
		Integer consumoTarifaID = null;
		BigDecimal percentualEsgoto = BigDecimal.ZERO;
		Short indicadorFaturamentoAgua = new Short("1");
		Short indicadorFaturamentoEsgoto = new Short("1");
		Integer consumoFaturadoAgua = null;
		Integer consumoFaturadoEsgoto = null;
		Date dataLeituraAnterior = null;
		Date dataLeituraAtual = null;

		// Verifica qual atributo do action form foi informado no JSP
		if(mesAnoConta != null && !mesAnoConta.equalsIgnoreCase("")){

			String mes = mesAnoConta.substring(0, 2);
			String ano = mesAnoConta.substring(3, 7);

			anoMesReferencia = Util.formatarMesAnoParaAnoMes(mes + ano);

			// Obtém as datas de leitura anterior e atual da tabela
			// FATURAMENTO_ATIVIDADE_CRONOGRAMA.
			if(Util.verificarIdNaoVazio(grupoFaturamento)){

				dataLeituraAnterior = this.buscarDataLeituraCronograma(Util.converterStringParaInteger(grupoFaturamento), true,
								Util.formatarMesAnoComBarraParaAnoMes(mesAnoConta), fachada);
				dataLeituraAtual = this.buscarDataLeituraCronograma(
Util.converterStringParaInteger(grupoFaturamento), false,
								Util.formatarMesAnoComBarraParaAnoMes(mesAnoConta), fachada);
			}

			if(dataLeituraAnterior == null){
				dataLeituraAnterior = Util.converteStringParaDate("01/" + mesAnoConta);
			}

			if(dataLeituraAtual == null){

				String ultimoDiaMes = Util.obterUltimoDiaMes(Util.converterStringParaInteger(mes).intValue(), Util
								.converterStringParaInteger(ano).intValue());
				dataLeituraAtual = Util.converteStringParaDate(ultimoDiaMes + "/" + mesAnoConta);
			}
		}

		if(situacaoAguaConta != null && !situacaoAguaConta.equalsIgnoreCase("")){
			ligacaoAguaSituacaoID = new Integer(situacaoAguaConta);
		}

		if(situacaoEsgotoConta != null && !situacaoEsgotoConta.equalsIgnoreCase("")){
			ligacaoEsgotoSituacaoID = new Integer(situacaoEsgotoConta);
		}

		if(consumoAgua != null && !consumoAgua.equalsIgnoreCase("")){
			consumoFaturadoAgua = new Integer(consumoAgua);
		}

		if(volumeEsgoto != null && !volumeEsgoto.equalsIgnoreCase("")){
			consumoFaturadoEsgoto = new Integer(volumeEsgoto);
		}else{
			consumoFaturadoEsgoto = consumoFaturadoAgua;
		}

		if(idConsumoTarifaConta != null && !idConsumoTarifaConta.equalsIgnoreCase("")){
			consumoTarifaID = new Integer(idConsumoTarifaConta);
		}

		LigacaoEsgotoPerfil ligacaoEsgotoPerfil = null;

		if(perfilLigacaoEsgoto != null && !perfilLigacaoEsgoto.equals("")){
			FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
			filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.ID, perfilLigacaoEsgoto));

			Collection colecaoLigacaoEsgotoPerfil = (Collection) fachada.pesquisar(filtroLigacaoEsgotoPerfil,
							LigacaoEsgotoPerfil.class.getName());
			ligacaoEsgotoPerfil = (LigacaoEsgotoPerfil) Util.retonarObjetoDeColecao(colecaoLigacaoEsgotoPerfil);
		}

		if(ligacaoEsgotoPerfil != null){
			percentualEsgoto = ligacaoEsgotoPerfil.getPercentualEsgotoConsumidaColetada();
		}

		// Criação do consumo tarifa
		Imovel imovel = new Imovel();
		ConsumoTarifa consumoTarifa = new ConsumoTarifa();
		consumoTarifa.setId(consumoTarifaID);
		imovel.setConsumoTarifa(consumoTarifa);

		Collection colecaoCategoriaOUSubcategoria = null;
		Integer qtdEconnomia = null;
		int consumoMinimoLigacao = 0;

		if(colecaoCategoria != null){

			colecaoCategoriaOUSubcategoria = (Collection) colecaoCategoria;
			qtdEconnomia = this.atualizarQuantidadeEconomiasCategoria(colecaoCategoriaOUSubcategoria, request);

			// Obtém o consumo mínimo ligação por categoria
			consumoMinimoLigacao = fachada.obterConsumoMinimoLigacao(imovel, colecaoCategoriaOUSubcategoria);

		}

		/*
		 * [UC0157] - Simular Cálculo da Conta
		 * [FS0003] - Verificar Consumo Mínimo
		 */
		fachada.verificarConsumoFaturadoAgua(ligacaoAguaSituacaoID, consumoFaturadoAgua);

		/*
		 * [UC0157] - Simular Cálculo da Conta
		 * [FS0004] - Verificar Volume Mínimo
		 */
		fachada.verificarConsumoFaturadoEsgoto(ligacaoEsgotoSituacaoID, consumoFaturadoEsgoto);


		if(ligacaoEsgotoSituacaoID.intValue() == LigacaoEsgotoSituacao.LIGADO.intValue() && percentualEsgoto == null){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.informe.percentualEsgoto"));
		}



		// Criação do filtro para ligação água situação
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.DESCRICAO);
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, ligacaoAguaSituacaoID));
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Pesquisa ligação água situação
		Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
		LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(colecaoLigacaoAguaSituacao);

		// verifica os indicadores de Faturamento baseado no configuração da Ligacao_Situacao
		indicadorFaturamentoAgua = ligacaoAguaSituacao.getIndicadorFaturamentoSituacao();

		if(!indicadorFaturamentoAgua.equals(LigacaoAguaSituacao.FATURAMENTO_ATIVO)){
			consumoFaturadoAgua = new Integer(0);
		}

		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID, ligacaoEsgotoSituacaoID));
		Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());
		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = colecaoLigacaoEsgotoSituacao.iterator().next();
		indicadorFaturamentoEsgoto = ligacaoEsgotoSituacao.getIndicadorFaturamentoSituacao();

		// Caso não seja ligado o consumo deve ser zero
		if(!indicadorFaturamentoEsgoto.equals(LigacaoEsgotoSituacao.FATURAMENTO_ATIVO)){

			consumoFaturadoEsgoto = new Integer(0);
			percentualEsgoto = new BigDecimal("0.0");
		}

		Collection colecaoCalcularValoresAguaEsgotoHelper = null;

		// SB0001 – Ajuste do Consumo para Múltiplo da Quantidade de Economias
		Map<String, String> consumoFaturadoAguaEsgoto = fachada.ajusteConsumoMultiploQuantidadeEconomia(consumoFaturadoAgua,
						consumoMinimoLigacao, consumoFaturadoEsgoto, qtdEconnomia);

		if(consumoFaturadoAguaEsgoto != null && !consumoFaturadoAguaEsgoto.isEmpty()){

			if(consumoFaturadoAguaEsgoto.get("agua") != null){

				consumoFaturadoAgua = Integer.parseInt(consumoFaturadoAguaEsgoto.get("agua"));
			}

			if(consumoFaturadoAguaEsgoto.get("esgoto") != null){

				consumoFaturadoEsgoto = Integer.parseInt(consumoFaturadoAguaEsgoto.get("esgoto"));
			}
		}

		// REVER!!! ADO ROCHA ATENÇÃO!!!
		// [UC0120] - Calcular Valores de Água e/ou Esgoto
		colecaoCalcularValoresAguaEsgotoHelper = fachada.calcularValoresAguaEsgoto(new Integer(anoMesReferencia), ligacaoAguaSituacaoID,
						ligacaoEsgotoSituacaoID, indicadorFaturamentoAgua, indicadorFaturamentoEsgoto, colecaoCategoriaOUSubcategoria,
						consumoFaturadoAgua, consumoFaturadoEsgoto, consumoMinimoLigacao, dataLeituraAnterior, dataLeituraAtual,
						percentualEsgoto, consumoTarifa.getId(), 21720002);


		// Método reponsável pela totalização dos valores de água e esgoto por
		// categoria, a partir da coleção
		// retornada pelo [UC0120] - Calcular Valores de Água e/ou Esgoto
		Collection colecaoCalcularValoresAguaEsgotoHelperPorCategoria = fachada
						.calcularValoresAguaEsgotoTotalizandoPorCategoria(colecaoCalcularValoresAguaEsgotoHelper);

		adicionarListaAoBody("colecaoCalcularValoresAguaEsgotoHelper", colecaoCalcularValoresAguaEsgotoHelperPorCategoria);
		adicionarValorPrimitivoAoBody("tamanhoColecaoValores", colecaoCalcularValoresAguaEsgotoHelperPorCategoria.size());

		BigDecimal valorTotalCategorias = this.obterValorTotalCategorias(colecaoCalcularValoresAguaEsgotoHelperPorCategoria, null);

		BigDecimal valorTotalAgua = this.obterValorTotalCategorias(colecaoCalcularValoresAguaEsgotoHelperPorCategoria,
						ConstantesSistema.CALCULAR_AGUA);

		BigDecimal valorTotalEsgoto = this.obterValorTotalCategorias(colecaoCalcularValoresAguaEsgotoHelperPorCategoria,
						ConstantesSistema.CALCULAR_ESGOTO);

		adicionarObjetoAoBody("valorTotalAgua", valorTotalAgua);
		adicionarObjetoAoBody("valorTotalEsgoto", valorTotalEsgoto);
		adicionarObjetoAoBody("valorTotalCategorias", valorTotalCategorias);

	}

	private Date buscarDataLeituraCronograma(Integer grupoFaturamentoID, boolean situacao, Integer anoMesReferencia, Fachada fachada){

		return fachada.buscarDataLeituraCronograma(grupoFaturamentoID, situacao, anoMesReferencia);
	}

	private Integer atualizarQuantidadeEconomiasCategoria(Collection colecaoCategorias, HttpServletRequest httpServletRequest){

		// Atualizando a quantidade de economias por categoria de acordo com o informado pelo
		// usuário
		Integer qtdEconnomia = 0;

		if(colecaoCategorias != null && !colecaoCategorias.isEmpty()){

			Iterator colecaoCategoriaIt = colecaoCategorias.iterator();
			Categoria categoria;
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			String qtdPorEconomia;

			while(colecaoCategoriaIt.hasNext()){
				categoria = (Categoria) colecaoCategoriaIt.next();

				if(requestMap.get("categoria" + categoria.getId().intValue()) != null){
					qtdPorEconomia = (requestMap.get("categoria" + categoria.getId().intValue()))[0];

					if(qtdPorEconomia == null || qtdPorEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Quantidade de economias");
					}

					categoria.setQuantidadeEconomiasCategoria(new Integer(qtdPorEconomia));
					qtdEconnomia = Util.somaInteiros(qtdEconnomia, new Integer(qtdPorEconomia));
				}
			}
		}
		return qtdEconnomia;
	}

	private BigDecimal obterValorTotalCategorias(
					Collection<CalcularValoresAguaEsgotoHelper> colecaoCalcularValoresAguaEsgotoHelperPorCategoria, String totalizacao){

		BigDecimal retorno = new BigDecimal("0");

		Iterator iteratorCalcularValoresAguaEsgotoHelperPorCategoria = colecaoCalcularValoresAguaEsgotoHelperPorCategoria.iterator();

		while(iteratorCalcularValoresAguaEsgotoHelperPorCategoria.hasNext()){

			CalcularValoresAguaEsgotoHelper objeto = (CalcularValoresAguaEsgotoHelper) iteratorCalcularValoresAguaEsgotoHelperPorCategoria
							.next();

			if(totalizacao == null && objeto.getValorTotalCategoria() != null){
				retorno = retorno.add(objeto.getValorTotalCategoria());
			}else if((totalizacao != null && totalizacao.equals(ConstantesSistema.CALCULAR_AGUA))
							&& objeto.getValorFaturadoAguaCategoria() != null){
				retorno = retorno.add(objeto.getValorFaturadoAguaCategoria());
			}else if((totalizacao != null && totalizacao.equals(ConstantesSistema.CALCULAR_ESGOTO))
							&& objeto.getValorFaturadoEsgotoCategoria() != null){
				retorno = retorno.add(objeto.getValorFaturadoEsgotoCategoria());
			}
		}
		return retorno;
	}

	public Collection converterStringEmColecao(String string){

		String[] valores = null;
		String[] valoresCategoria = null;
		Collection colecaoCategoriasEconomiasString = null;
		Collection colecaoCategorias = new ArrayList();

		if(!Util.isVazioOuBranco(string)){
			// Recupera os valores da string
			valores = string.split(",");

			if(!Util.isVazioOrNulo(valores)){
				colecaoCategoriasEconomiasString = new ArrayList<String>();

				// carrega valores
				for(String valor : valores){
					if(valor != null){
						colecaoCategoriasEconomiasString.add(valor);
					}
				}

			}
		}

		Iterator iterator = colecaoCategoriasEconomiasString.iterator();
		while(iterator.hasNext()){
			String categoriaEconomia = (String) iterator.next();

			if(categoriaEconomia != null && !categoriaEconomia.equals("")){
				Categoria categoria = new Categoria();

				valoresCategoria = categoriaEconomia.split(";");

				categoria.setId(Integer.valueOf(valoresCategoria[0]));
				categoria.setQuantidadeEconomiasCategoria(Integer.valueOf(valoresCategoria[1]));

				colecaoCategorias.add(categoria);

			}
		}


		return colecaoCategorias;
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
