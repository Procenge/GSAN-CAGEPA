
package gcom.util.parametrizacao.micromedicao;

import gcom.util.parametrizacao.Parametro;

public class ParametroMicromedicao
				extends Parametro {

	/*
	 * Indica a forma de calcular o consumo mínimo para Faturamento
	 * de água dos imóveis não-medidos. Pode assumir os valores CONSUMO MINIMO FIXO ou CONSUMO
	 * ESTIMADO POR FAIXA DE ÁREA E CATEGORIA.
	 */
	public static final Parametro P_CALCULO_CONSUMO_MINIMO_NAO_MEDIDOS = new ParametroMicromedicao("P_CALCULO_CONSUMO_MINIMO_NAO_MEDIDOS");

	/*
	 * Identificação do agente comercial que realizou a leitura quando o arquivo de retorno de
	 * leitura da empresa não tiver a identificação do leiturista.
	 */
	public static final Parametro P_AGENTE_COMERCIAL_PADRAO = new ParametroMicromedicao("P_AGENTE_COMERCIAL_PADRAO");

	/*
	 * Identificação do agente comercial que realizou a leitura quando o arquivo de retorno de
	 * leitura da empresa não tiver a identificação do leiturista.
	 */
	public static final Parametro P_CONVERTER_ARQUIVO_LEITURA_PARA_FORMATO_PADRAO = new ParametroMicromedicao(
					"P_CONVERTER_ARQUIVO_LEITURA_PARA_FORMATO_PADRAO");

	/*
	 * Indica, caso a empresa adote o controle do limite aceitável de anormalidades de consumo, se o
	 * controle será por grupo de faturamento, por setor comercial ou por rota
	 */
	public static final Parametro P_COD_ENTIDADE_CONTROLE_ANORMALIDADES_CONSUMO = new ParametroMicromedicao(
					"P_COD_ENTIDADE_CONTROLE_ANORMALIDADES_CONSUMO");

	/*
	 * Indica os códigos das anormalidades de consumo e respectivos percentuais para controle do
	 * limite aceitável de anormalidades de consumo
	 */
	public static final Parametro P_LISTA_COD_PERCENTUAL_ANORMALIDADE_CONSUMO_CONTROLADA = new ParametroMicromedicao(
					"P_LISTA_COD_PERCENTUAL_ANORMALIDADE_CONSUMO_CONTROLADA");

	// Indica o critério de identificação de hidrômetro novo.
	public static final Parametro P_HIDROMETRO_NOVO = new ParametroMicromedicao("P_HIDROMETRO_NOVO");

	// Indica a quantidade de dias para identificação de hidrômetro novo.
	public static final Parametro P_QTD_DIAS_HIDROMETRO_NOVO = new ParametroMicromedicao("P_QTD_DIAS_HIDROMETRO_NOVO");

	// Indica se deve ser verificado a leitura está fora da faixa esperada.
	public static final Parametro P_VERIFICA_LEITURA_FORA_FAIXA = new ParametroMicromedicao("P_VERIFICA_LEITURA_FORA_FAIXA");

	/*
	 * Indica a quantidade de meses que a leitura deve se repetir para indicar leitura igual a
	 * anterior, considerando a leitura atual.
	 */
	public static final Parametro P_QTD_MESES_LEITURA_IGUAL = new ParametroMicromedicao("P_QTD_MESES_LEITURA_IGUAL");

	// Indica se a empresa verifica se o hidrômetro está parado quando a leitura é igual à anterior.
	public static final Parametro P_VERIFICA_HIDROMETRO_PARADO = new ParametroMicromedicao("P_VERIFICA_HIDROMETRO_PARADO");

	/*
	 * Indica o numero mínimo de dígitos no medidor para validação de virada de hidrômetro
	 * (inclusive).
	 */
	public static final Parametro P_NUM_DIGITOS_HIDROMETRO_VIRADA = new ParametroMicromedicao("P_NUM_DIGITOS_HIDROMETRO_VIRADA");

	// Indica se a empresa verifica uma substituição de hidrômetro ainda não informada.
	public static final Parametro P_VERIFICA_POSSIVEL_SUBSTITUICAO_HIDROMETRO = new ParametroMicromedicao(
					"P_VERIFICA_POSSIVEL_SUBSTITUICAO_HIDROMETRO");

	// Define o mês para tratamento de ajuste de consumo.
	public static final Parametro P_DEFINICAO_MES_AJUSTE_CONSUMO = new ParametroMicromedicao("P_DEFINICAO_MES_AJUSTE_CONSUMO");

	// Quantidade de dias aceita como margem para ajuste de consumo.
	public static final Parametro P_VARIACAO_DIAS_AJUSTE_CONSUMO = new ParametroMicromedicao("P_VARIACAO_DIAS_AJUSTE_CONSUMO");

	/*
	 * Indica valor atribuído ao consumo do mês quando for definida a cobrança de mínimo na
	 * consistência de leitura.
	 */
	public static final Parametro P_COBRANCA_CONSUMO_MINIMO = new ParametroMicromedicao("P_COBRANCA_CONSUMO_MINIMO");

	// Indica o critério utilizado para determinar um estouro de consumo.
	public static final Parametro P_CRITERIO_ESTOURO_CONSUMO = new ParametroMicromedicao("P_CRITERIO_ESTOURO_CONSUMO");

	// Indica o critério utilizado para determinar um alto consumo.
	public static final Parametro P_CRITERIO_ALTO_CONSUMO = new ParametroMicromedicao("P_CRITERIO_ALTO_CONSUMO");

	/*
	 * Indica a quantidades de meses com da instalação a partir da qual e verificado o alto consumo
	 * (exclusive).
	 */
	public static final Parametro P_QTD_MESES_VERIFICACAO_ALTO_CONSUMO = new ParametroMicromedicao("P_QTD_MESES_VERIFICACAO_ALTO_CONSUMO");

	// Indica o critério utilizado para determinar um baixo consumo.
	public static final Parametro P_CRITERIO_BAIXO_CONSUMO = new ParametroMicromedicao("P_CRITERIO_BAIXO_CONSUMO");

	// Indica a ação a ser realizada no ajuste mensal.
	public static final Parametro P_ACAO_AJUSTE_CONSUMO = new ParametroMicromedicao("P_ACAO_AJUSTE_CONSUMO");

	// Indica a regra a ser utilizada quando a média for igual a zero (0)
	public static final Parametro P_TRATAMENTO_MEDIA_ZERADA = new ParametroMicromedicao("P_TRATAMENTO_MEDIA_ZERADA");

	/*
	 * Parâmetro para indicar qual ação deve ser tomada quando o violume é ajustado pela quantidade
	 * de economias.Se o consumo restante obtido pelo ajuste do consumo pelo número de economias
	 * deve ser gerado como um crédito de consumo para a empresa, sendo cobrado do cliente no
	 * próximo ciclo de leitura/faturamento
	 */
	public static final Parametro P_ACAO_AJUSTE_CONSUMO_MULTIPLO_QTDE_ECONOMIAS = new ParametroMicromedicao(
					"P_ACAO_AJUSTE_CONSUMO_MULTIPLO_QTDE_ECONOMIAS");

	// Tipo de debito utilizado para cobrança do rateio de consumo
	public static final Parametro P_DEBITO_TIPO_RATEIO = new ParametroMicromedicao("P_DEBITO_TIPO_RATEIO");

	public static final Parametro P_GERACAO_ARQUIVO_FATURAMENTO_IMEDIATO_ENVIO = new ParametroMicromedicao(
					"P_GERACAO_ARQUIVO_FATURAMENTO_IMEDIATO_ENVIO");

	public static final Parametro P_ORDENACAO_DIGITACAO_LEITURA = new ParametroMicromedicao("P_ORDENACAO_DIGITACAO_LEITURA");

	public static final Parametro P_PERMITE_INFORMAR_DADOS_LEITURA_EM_LOTE = new ParametroMicromedicao(
					"P_PERMITE_INFORMAR_DADOS_LEITURA_EM_LOTE");

	/**
	 * Segue o seguinte formato: xx,yyyy[,xx,yyyy,]*
	 * Onde:
	 * <ul>
	 * <li>xx é codigo da situação da ligação de agua</li>
	 * <li>yyyy é o limite minimo consumo informado faturável</li>
	 * </ul>
	 */
	public static Parametro P_SIT_LIGAGUA_VERIFICA_CONSUMO_FATURAVEL = new ParametroMicromedicao("P_SIT_LIGAGUA_VERIFICA_CONSUMO_FATURAVEL");

	/*
	 * Informa se o sistema deve ou não confrontar a situação da ligação de água e esgoto do imóvel
	 * condomínio com a situação da ligação de água e esgoto do imóvel vinculado no momento de
	 * Estabelecer o vínculo.
	 */
	public static Parametro P_VERIF_SIT_LIG_AGUA_ESG_IMOVEL_VINCULADO = new ParametroMicromedicao(
					"P_VERIF_SIT_LIG_AGUA_ESG_IMOVEL_VINCULADO");

	/*
	 * [UC0356] Efetuar Mudança de Situação de Faturamento da Ligação de Esgoto
	 * Caso (PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA com PASI_DSPARAMETRO=
	 * "P_SITUACAO_LIGACAO_ESGOTO_SERVICO_TAMPONAMENTO") for igual '1', atualizar a situação de
	 * esgoto do imóvel para tamponado (LEST_ID da tabela IMOVEL igual ao LEST_ID da tabela
	 * LIGACAO_ESGOTO_SITUACAO com o conteúdo da LEST_DSLIGACAOESGOTOSITUACAO igual a TAMPONADO);
	 * Caso (PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA com PASI_DSPARAMETRO=
	 * "P_SITUACAO_LIGACAO_ESGOTO_SERVICO_TAMPONAMENTO") for igual '2', atualizar a situação de
	 * esgoto do imóvel para cortado (LEST_ID da tabela IMOVEL igual ao LEST_ID da tabela
	 * LIGACAO_ESGOTO_SITUACAO com o conteúdo da LEST_DSLIGACAOESGOTOSITUACAO igual a CORTADO);
	 */
	public static final Parametro P_SITUACAO_LIGACAO_ESGOTO_SERVICO_TAMPONAMENTO = new ParametroMicromedicao(
					"P_SITUACAO_LIGACAO_ESGOTO_SERVICO_TAMPONAMENTO");

	/*
	 * Lista as referencias que seram geradas o Resumo Ligações Economia.
	 */
	public static Parametro P_GERAR_RESUMO_LIGACOES_ECONOMIA_REFERENCIAS = new ParametroMicromedicao(
					"P_GERAR_RESUMO_LIGACOES_ECONOMIA_REFERENCIAS");

	/*
	 * Indica se o sistema deve ou não verificar débitos do Imóvel Condomínio.
	 */
	public static Parametro P_VERIFICAR_DEBITOS_IMOVEL_CONDOMINIO = new ParametroMicromedicao("P_VERIFICAR_DEBITOS_IMOVEL_CONDOMINIO");

	/*
	 * Criterio para Leitura da Anormalidade: LEITURA_IGUAL_ANTERIOR.
	 */
	public static Parametro P_CRITERIO_LEITURA_ANORMALIDADE_LEITURA_IGUAL_ANTERIOR = new ParametroMicromedicao(
					"P_CRITERIO_LEITURA_ANORMALIDADE_LEITURA_IGUAL_ANTERIOR");

	/*
	 * Criterio que definirá a regra adotada quanto a leitura atual. Valores possíveis: 1 - Leitura
	 * Atual menor que projetada; 2 - Leitura atual menor ou igual a media
	 */
	public static Parametro P_CRITERIO_LEITURA_ATUAL = new ParametroMicromedicao("P_CRITERIO_LEITURA_ATUAL");

	/*
	 * Fator de Multiplicacao da Media de consumo do imovel
	 */
	public static Parametro P_FATOR_MULTIPLICACAO_MEDIA_CONSUMO = new ParametroMicromedicao("P_FATOR_MULTIPLICACAO_MEDIA_CONSUMO");

	/*
	 * Limite de consumo a ser cobrado por categoria
	 */
	public static Parametro P_LIMITE_CONSUMO_COBRADO_POR_CATEGORIA = new ParametroMicromedicao("P_LIMITE_CONSUMO_COBRADO_POR_CATEGORIA");

	/*
	 * Consumo minimo a ser cobrado
	 */
	public static Parametro P_CONSUMO_COBRADO_MINIMO = new ParametroMicromedicao("P_CONSUMO_COBRADO_MINIMO");

	/*
	 * Criterio para determinacao de data da leitura anterior
	 */
	public static Parametro P_CRITERIO_DATA_LEITURA_ANTERIOR = new ParametroMicromedicao("P_CRITERIO_DATA_LEITURA_ANTERIOR");

	/*
	 * Indica se deve realizar a comparacao entre as datas de instalacao e leitura anterior para
	 * hidrometro novo
	 */
	public static Parametro P_COMPARAR_DATAS_INSTALACAO_E_LEITURA_ANTERIOR_PARA_HIDR_NOVO = new ParametroMicromedicao(
					"P_COMPARAR_DATAS_INSTALACAO_E_LEITURA_ANTERIOR_PARA_HIDR_NOVO");

	/*
	 * Indica se o sistema deve ignorar imoveis com anormalidade de leitura do tipo hidrometro
	 * invertido
	 */
	public static Parametro P_IGNORAR_HIDROMETRO_INVERTIDO_LEITURA_MENOR_QUE_ANTERIOR = new ParametroMicromedicao(
					"P_IGNORAR_HIDROMETRO_INVERTIDO_LEITURA_MENOR_QUE_ANTERIOR");

	/*
	 * Indica se deve tratar leituras menores que anterior quando a situacao da leitura anterior nao
	 * tenha sido real
	 */
	public static Parametro P_TRATAR_SIT_LEIT_ANT_NAO_REAL_LEITURA_MENOR_QUE_ANTERIOR = new ParametroMicromedicao(
					"P_TRATAR_SIT_LEIT_ANT_NAO_REAL_LEITURA_MENOR_QUE_ANTERIOR");

	/*
	 * Indica se deve verificar a quantidade de meses entre as datas de instalacao do hidrometro e a
	 * data corrente no procedimento de verificacao de alto consumo
	 */
	public static Parametro P_VERIFICAR_QTD_MESES_VERIFICACAO_ALTO_CONSUMO = new ParametroMicromedicao(
					"P_VERIFICAR_QTD_MESES_VERIFICACAO_ALTO_CONSUMO");

	/*
	 * Indica se deve verificar pelo consumo medio sem anormalidade BC no procedimento de verificar
	 * baixo consumo
	 */
	public static Parametro P_VERIFICAR_BAIXO_CONSUMO_SEM_ANORMALIDADE_BC = new ParametroMicromedicao(
					"P_VERIFICAR_BAIXO_CONSUMO_SEM_ANORMALIDADE_BC");

	/*
	 * Define a regra utilizada no ajuste do consumo para multiplo da quantidade de economias
	 */
	public static Parametro P_AJUSTAR_CONSUMO_MULTIPLO_QTD_ECONOMIAS = new ParametroMicromedicao("P_AJUSTAR_CONSUMO_MULTIPLO_QTD_ECONOMIAS");

	/*
	 * Indica se o Relatorio de Consumidores para Leitura deve exibir as totalizacoes
	 */
	public static Parametro P_EXIBIR_TOTALIZACOES_RELAT_CONSUMIDORES_LEITURA = new ParametroMicromedicao(
					"P_EXIBIR_TOTALIZACOES_RELAT_CONSUMIDORES_LEITURA");

	/*
	 * Indica a quantidade de dias a considerar o imóvel faturável quando o imóvel é cortado.
	 */
	public static Parametro P_NUMERO_DIAS_CORTADO_SEM_CONSUMO = new ParametroMicromedicao("P_NUMERO_DIAS_CORTADO_SEM_CONSUMO");

	/*
	 * Indica a referência de consumo medido para imóveis cortados a serem faturáveis.
	 */
	public static Parametro P_CONSUMO_MEDIDO_REFERENCIA_CORTADO = new ParametroMicromedicao("P_CONSUMO_MEDIDO_REFERENCIA_CORTADO");

	/*
	 * Indica a quantidade mínima de dias de consumo para considerar o imóvel faturável quando o
	 * imóvel é ligação nova.
	 */
	public static Parametro P_NUMERO_DIAS_PARA_FATURAR_LIGACAO_NOVA = new ParametroMicromedicao("P_NUMERO_DIAS_PARA_FATURAR_LIGACAO_NOVA");

	/*
	 * Indica a quantidade mínima de dias de consumo para considerar o imóvel faturável quando o
	 * imóvel é religado.
	 */
	public static Parametro P_NUMERO_DIAS_PARA_FATURAR_RELIGADO = new ParametroMicromedicao("P_NUMERO_DIAS_PARA_FATURAR_RELIGADO");

	/*
	 * Indica se deve considerar o consumo fixo apenas se este for maior que o mínimo da ligação
	 */
	public static Parametro P_FATURAR_CONSUMO_FIXO_MENOR_MINIMO = new ParametroMicromedicao("P_FATURAR_CONSUMO_FIXO_MENOR_MINIMO");

	public static Parametro P_PERMITE_SUBSTITUIR_TODAS_LEITURAS_ANTERIORES = new ParametroMicromedicao(
					"P_PERMITE_SUBSTITUIR_TODAS_LEITURAS_ANTERIORES");

	public static Parametro P_GESTAO_LEITURA_ENDERECO = new ParametroMicromedicao("P_GESTAO_LEITURA_ENDERECO");

	private ParametroMicromedicao(String parametro) {

		super(parametro);
	}
}
