
package gcom.util.parametrizacao.cadastro;

import gcom.util.parametrizacao.Parametro;

public class ParametroCadastro
				extends Parametro {

	/*
	 * Parâmetro indicador de alteração da inscrição restrita à localidade.
	 */
	public static final Parametro P_INDICADOR_ALTERACAO_INSCRICAO_RESTRITA_LOCALIDADE = new ParametroCadastro(
					"P_INDICADOR_ALTERACAO_INSCRICAO_RESTRITA_LOCALIDADE");

	public static final Parametro P_EMITE_AVISO_E_ORDEM_CORTE_INDIVIDUAL = new ParametroCadastro("P_EMITE_AVISO_E_ORDEM_CORTE_INDIVIDUAL");

	public static final Parametro P_SITE_EMPRESA = new ParametroCadastro("P_SITE_EMPRESA");

	// Parâmetro cadastro de imovel
	public static final Parametro P_CAMPOS_OBRIGATORIOS_IMOVEL = new ParametroCadastro("P_CAMPOS_OBRIGATORIOS_IMOVEL");

	// Indica a data que foi gerada a última relação de cadastro
	public static final Parametro P_ULT_EMISSAO_FICHA_CADASTRO = new ParametroCadastro("P_ULT_EMISSAO_FICHA_CADASTRO");

	// Parâmetros incluir/Manter Dados Tarifa Social
	public static final Parametro P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL = new ParametroCadastro("P_FATOR_SALARIO_MINIMO_TARIFA_SOCIAL");

	public static final Parametro P_TARIFA_CONSUMO_TARIFA_SOCIAL = new ParametroCadastro("P_TARIFA_CONSUMO_TARIFA_SOCIAL");

	public static final Parametro P_INDICADOR_LIMITE_CONSUMO_TARIFA_SOCIAL = new ParametroCadastro(
					"P_INDICADOR_LIMITE_CONSUMO_TARIFA_SOCIAL");

	public static final Parametro P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL = new ParametroCadastro("P_PERCENTUAL_ESGOTO_TARIFA_SOCIAL");

	public static final Parametro P_VALIDA_PERMANENCIA_TARIFA_SOCIAL = new ParametroCadastro("P_VALIDA_PERMANENCIA_TARIFA_SOCIAL");

	public static final Parametro P_NOME_MAE_CLIENTE_OBRIGATORIO = new ParametroCadastro("P_NOME_MAE_CLIENTE_OBRIGATORIO");

	public static final Parametro P_DATA_NASC_CLIENTE_OBRIGATORIO = new ParametroCadastro("P_DATA_NASC_CLIENTE_OBRIGATORIO");

	public static final Parametro P_INDICADOR_IMOVEL_CONSUMO_FAIXA_AREA_CATG = new ParametroCadastro(
					"P_INDICADOR_IMOVEL_CONSUMO_FAIXA_AREA_CATG");

	public static final Parametro P_INDICADOR_REFERENCIA_ENDERECO_OBRIGATORIO = new ParametroCadastro(
					"P_INDICADOR_REFERENCIA_ENDERECO_OBRIGATORIO");

	public static final Parametro P_INFORMAR_VENCIMENTO_PARA_CLIENTE = new ParametroCadastro("P_INFORMAR_VENCIMENTO_PARA_CLIENTE");

	public static final Parametro P_NUMERO_QUADRA_COM_4_DIGITOS = new ParametroCadastro("P_NUMERO_QUADRA_COM_4_DIGITOS");

	public static final Parametro P_INDICADOR_OBRIGATORIEDADE_FUNCIONARIO = new ParametroCadastro("P_INDICADOR_OBRIGATORIEDADE_FUNCIONARIO");

	/*
	 * [UC0269] Consultar Resumo das Ligações / Economias
	 * Retorna o metodo obterFiltroListaLigacaoAguaSituacao de acordo com a Empresa.
	 * @author Josenilldo Neves
	 * @created 30/01/2013
	 */
	public static final Parametro P_OBTER_FILTRO_LISTA_LIGACAO_AGUA_SITUACAO = new ParametroCadastro(
					"P_OBTER_FILTRO_LISTA_LIGACAO_AGUA_SITUACAO");

	/*
	 * [UC0269] Consultar Resumo das Ligações / Economias
	 * Retorna o metodo obterSubConsultaDetalheResumoLigacaoEconomia de acordo com a Empresa.
	 * @author Josenilldo Neves
	 * @created 30/01/2013
	 */
	public static final Parametro P_OBTER_SUB_CONSULTA_DETALHE_REL_RESUMO_LIGACOES_ECONOMIA = new ParametroCadastro(
					"P_OBTER_SUB_CONSULTA_DETALHE_REL_RESUMO_LIGACOES_ECONOMIA");

	public static final Parametro P_LOCA_ID_LOCALIDADE_PADRAO = new ParametroCadastro("P_LOCA_ID_LOCALIDADE_PADRAO");

	public static final Parametro P_STCM_ID_SETOR_COMERCIAL_PADRAO = new ParametroCadastro("P_STCM_ID_SETOR_COMERCIAL_PADRAO");

	public static final Parametro P_ANO_MES_REFERENCIA_PROVISAO_RECEITA = new ParametroCadastro("P_ANO_MES_REFERENCIA_PROVISAO_RECEITA");

	public static final Parametro P_VERIFICAR_ECONOMIA_E_PONTO_UTILIZACAO = new ParametroCadastro("P_VERIFICAR_ECONOMIA_E_PONTO_UTILIZACAO");

	public static final Parametro P_INDICADOR_EXTRATO_DEBITO_SELECAO = new ParametroCadastro("P_INDICADOR_EXTRATO_DEBITO_SELECAO");

	public static final Parametro P_INDICADOR_CONCESSIONARIA = new ParametroCadastro("P_INDICADOR_CONCESSIONARIA");

	public static final Parametro P_LAYOUT_BOLETIM_CADASTRAL = new ParametroCadastro("P_LAYOUT_BOLETIM_CADASTRAL");

	public static final Parametro P_EXIBIR_ROTA = new ParametroCadastro("P_EXIBIR_ROTA");

	public static final Parametro P_EXIBIR_ROTA_GRUPOS_PERMITIDOS = new ParametroCadastro("P_EXIBIR_ROTA_GRUPOS_PERMITIDOS");

	public static final Parametro P_SITUACAO_AGUA_IMOVEL_ATIVO = new ParametroCadastro("P_SITUACAO_AGUA_IMOVEL_ATIVO");

	public static final Parametro P_INDICADOR_RACA_CLIENTE_OBRIGATORIO = new ParametroCadastro("P_INDICADOR_RACA_CLIENTE_OBRIGATORIO");

	public static final Parametro P_FONTE_DE_ABASTECIMENTO_IMOVEL_OBRIGATORIO = new ParametroCadastro(
					"P_FONTE_DE_ABASTECIMENTO_IMOVEL_OBRIGATORIO");

	public static final Parametro P_SITUACAO_ESGOTO_NAO_PERMITE_ALTERAR_TIPO_DE_DESPEJO = new ParametroCadastro(
					"P_SITUACAO_ESGOTO_NAO_PERMITE_ALTERAR_TIPO_DE_DESPEJO");

	public static final Parametro P_INDICADOR_CADASTRO_PREVIO_TARIFA_SOCIAL = new ParametroCadastro(
					"P_INDICADOR_CADASTRO_PREVIO_TARIFA_SOCIAL");

	public static final Parametro P_VALIDA_QUANTIDADE_ECONOMIA_TARIFA_SOCIAL = new ParametroCadastro(
					"P_VALIDA_QUANTIDADE_ECONOMIA_TARIFA_SOCIAL");

	public static final Parametro P_VALIDADE_BENEFICIO_TARIFA_SOCIAL = new ParametroCadastro("P_VALIDADE_BENEFICIO_TARIFA_SOCIAL");

	public static final Parametro P_VALIDA_DATA_VALIDADE_TARIFA_SOCIAL = new ParametroCadastro("P_VALIDA_DATA_VALIDADE_TARIFA_SOCIAL");

	public static final Parametro P_QUANTIDADE_MINIMA_DE_DEBITOS = new ParametroCadastro("P_QUANTIDADE_MINIMA_DE_DEBITOS");

	public static final Parametro P_REATIVACAO_AUTOMATICA_TARIFA_SOCIAL = new ParametroCadastro("P_REATIVACAO_AUTOMATICA_TARIFA_SOCIAL");

	public static final Parametro P_PERMITE_DUPLICIDADE_CNPJ = new ParametroCadastro("P_PERMITE_DUPLICIDADE_CNPJ");

	public static final Parametro P_TIPO_SOLICITACAO_ESPECIFICACAO_CONTA_BRAILLE = new ParametroCadastro(
					"P_TIPO_SOLICITACAO_ESPECIFICACAO_CONTA_BRAILLE");

	public static final Parametro P_CNAE_OBRIGATORIO = new ParametroCadastro("P_CNAE_OBRIGATORIO");



	public static final Parametro P_MATRICULA_COM_DIGITO_VERIFICADOR = new ParametroCadastro("P_MATRICULA_COM_DIGITO_VERIFICADOR");

	public static final Parametro P_METODO_CALCULO_DIGITO_VERIFICADOR = new ParametroCadastro("P_METODO_CALCULO_DIGITO_VERIFICADOR");

	public static final Parametro P_INDICADOR_RESTRICAO_ALTERACAO_QTDE_ECONOMIAS_IMOVEL = new ParametroCadastro(
					"P_INDICADOR_RESTRICAO_ALTERACAO_QTDE_ECONOMIAS_IMOVEL");

	public static final Parametro P_NUMERO_BENEFICIO_OBRIGATORIO = new ParametroCadastro("P_NUMERO_BENEFICIO_OBRIGATORIO");

	public static final Parametro P_INDICADOR_INFORMAR_DATA_RELACAO_FIM_INSERIR_CLIENTE_IMOVEL = new ParametroCadastro(
					"P_INDICADOR_INFORMAR_DATA_RELACAO_FIM_INSERIR_CLIENTE_IMOVEL");

	public static final Parametro P_NUMERO_QUADRA_COM_5_DIGITOS = new ParametroCadastro("P_NUMERO_QUADRA_COM_5_DIGITOS");

	private ParametroCadastro(String parametro) {

		super(parametro);
	}
}
