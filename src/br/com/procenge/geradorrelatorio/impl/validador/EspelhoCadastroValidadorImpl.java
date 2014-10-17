
package br.com.procenge.geradorrelatorio.impl.validador;

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
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

/**
 * Classe que valida os parâmetros da tela para gerar o relatório de espelho de cadastro.
 * 
 * @author Ricardo Rodrigues
 * @date 30/03/2012
 */
public class EspelhoCadastroValidadorImpl
				implements ValidadorParametros {

	public static final String P_REGIONAL_INICIAL = "p_regionalInico";

	public static final String P_REGIONAL_FINAL = "p_regionalFinal";

	public static final String P_GRUPO_FATURAMENTO_INICIAL = "p_grupoFaturamentoInicio";

	public static final String P_GRUPO_FATURAMENTO_FINAL = "p_grupoFaturamentoFinal";

	public static final String P_LOCALIDADE_INICIAL = "p_localidadeInicio";

	public static final String P_LOCALIDADE_FINAL = "p_localidadeFinal";

	public static final String P_PARCIAL = "p_parcial";

	public static final String P_DATA_ULTIMA_EMISSAO = "p_dataUltimaEmissao";

	public static final String P_QUADRA_INICIAL = "p_quadraInicio";

	public static final String P_QUADRA_FINAL = "p_quadraFinal";

	public static final String P_SETOR_FATURAMENTO_INICIAL = "p_setorFaturamentoInicio";

	public static final String P_SETOR_FATURAMENTO_FINAL = "p_setorFaturamentoFinal";

	private static final Integer VALOR_NAO_INFORMADO = Integer.valueOf(-1);

	private static final String MENOS_UM = "-1";

	private static final String OPCAO_PARCIAL = "p";

	private static final String P_EMISSAO_GERAL_EXIBICAO = "p_emissaoGeral";

	private static final String P_INDICADOR_ORDENACAO = "p_indicadorOrdenacao";

	public static final String P_UNIDADE_NEGOCIO_INICIAL = "P_UNE_INI";

	public static final String P_UNIDADE_NEGOCIO_FINAL = "P_UNE_FIN";

	private Fachada fachada = Fachada.getInstancia();

	/**
	 * Método que converte todos os parâmetros.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 30/03/2012
	 */
	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();

		// Converte os parâmetros
		this.converterOpcao(parametros, parametrosConvertidos);
		this.converterRegionais(parametros, parametrosConvertidos);
		this.converterGruposFaturamento(parametros, parametrosConvertidos);
		this.converterLocalidades(parametros, parametrosConvertidos);
		this.converterSetoresFaturamento(parametros, parametrosConvertidos);
		this.converterQuadras(parametros, parametrosConvertidos);
		this.converterIndicadorOrdenacao(parametros, parametrosConvertidos);
		this.converterUnidadeNegocio(parametros, parametrosConvertidos);

		return parametrosConvertidos;
	}

	/**
	 * Método que valida todos os parâmetros.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 30/03/2012
	 */
	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();

		Integer idGrupoFaturamentoInicial = (Integer) parametros.get(P_GRUPO_FATURAMENTO_INICIAL);
		Integer idGrupoFaturamentoFinal = (Integer) parametros.get(P_GRUPO_FATURAMENTO_FINAL);
		Integer idSetorFaturamentoInicial = (Integer) parametros.get(P_SETOR_FATURAMENTO_INICIAL);
		Integer idSetorFaturamentoFinal = (Integer) parametros.get(P_SETOR_FATURAMENTO_FINAL);
		Integer idLocalidadeInicial = (Integer) parametros.get(P_LOCALIDADE_INICIAL);
		Integer idLocalidadeFinal = (Integer) parametros.get(P_LOCALIDADE_FINAL);
		Integer idRegionalInicial = (Integer) parametros.get(P_REGIONAL_INICIAL);
		Integer idRegionalFinal = (Integer) parametros.get(P_REGIONAL_FINAL);
		Integer idQuadraInicial = (Integer) parametros.get(P_QUADRA_INICIAL);
		Integer idQuadraFinal = (Integer) parametros.get(P_QUADRA_FINAL);
		Integer idUnidadeNegocioInicial = (Integer) parametros.get(P_UNIDADE_NEGOCIO_INICIAL);
		Integer idUnidadeNegocioFinal = (Integer) parametros.get(P_UNIDADE_NEGOCIO_FINAL);

		// Valida os intervalos de valores
		validarIntervalo(idGrupoFaturamentoInicial, idGrupoFaturamentoFinal, erros, P_GRUPO_FATURAMENTO_INICIAL, P_GRUPO_FATURAMENTO_FINAL,
						"Grupo de Faturamento");
		validarIntervalo(idSetorFaturamentoInicial, idSetorFaturamentoFinal, erros, P_SETOR_FATURAMENTO_INICIAL, P_SETOR_FATURAMENTO_FINAL,
						"Setor Comercial");
		validarIntervalo(idLocalidadeInicial, idLocalidadeFinal, erros, P_LOCALIDADE_INICIAL, P_LOCALIDADE_FINAL, "Localidade");
		validarIntervalo(idRegionalInicial, idRegionalFinal, erros, P_REGIONAL_INICIAL, P_REGIONAL_FINAL, "Regional");
		validarIntervalo(idQuadraInicial, idQuadraFinal, erros, P_QUADRA_INICIAL, P_QUADRA_FINAL, "Quadra");
		validarIntervalo(idUnidadeNegocioInicial, idUnidadeNegocioFinal, erros, P_UNIDADE_NEGOCIO_INICIAL, P_UNIDADE_NEGOCIO_FINAL,
						"Unidade de Negocio");

		try{

			Fachada.getInstancia().atualizarValorParametroSistema(ParametroCadastro.P_ULT_EMISSAO_FICHA_CADASTRO.getCodigo(),
							Util.formatarData(new Date()));
		}catch(PCGException e){

			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return erros;
	}

	/**
	 * Valida um intervalo de valores
	 * 
	 * @author Luciano Galvão
	 * @date 02/04/2012
	 * @param erros
	 * @param valorInicial
	 * @param valorFinal
	 */
	private void validarIntervalo(Integer valorInicial, Integer valorFinal, Map<String, String> erros, String nomeParametroInicial,
					String nomeParametroFinal, String nomeEntidade){

		// Se o valor inicial está vazio
		if((valorInicial == null) || valorInicial.equals(VALOR_NAO_INFORMADO)){
			// retorna erro
			erros.put(nomeParametroInicial, nomeEntidade + " Inicial deve ser preenchido.");
		}

		// Se o valor inicial está vazio
		if((valorFinal == null) || valorFinal.equals(VALOR_NAO_INFORMADO)){
			// retorna erro
			erros.put(nomeParametroFinal, nomeEntidade + " Final deve ser preenchido.");
		}

		if((valorInicial != null) && (valorFinal != null) && !valorInicial.equals(VALOR_NAO_INFORMADO)
						&& !valorFinal.equals(VALOR_NAO_INFORMADO)){

			// Se o valor final é menor que o inicial
			if(valorFinal.compareTo(valorInicial) < 0){
				erros.put(nomeParametroFinal, nomeEntidade + " Final deve ser maior ou igual ao valor de " + nomeEntidade + " Inicial.");
			}
		}
	}

	private void converterRegionais(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idRegionalInicialString = Util.converterObjetoParaString(parametros.get(P_REGIONAL_INICIAL));
		String idRegionalFinalString = Util.converterObjetoParaString(parametros.get(P_REGIONAL_FINAL));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idRegionalInicial = VALOR_NAO_INFORMADO;
		Integer idRegionalFinal = VALOR_NAO_INFORMADO;

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.ID);
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		ArrayList<GerenciaRegional> colecaoGerenciaRegional = (ArrayList<GerenciaRegional>) fachada.pesquisar(filtroGerenciaRegional,
						GerenciaRegional.class.getName());

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(idRegionalInicialString) && !idRegionalInicialString.equals(MENOS_UM)){
			idRegionalInicial = Util.converterStringParaInteger(idRegionalInicialString);
		}else{

			idRegionalInicial = colecaoGerenciaRegional.get(0).getId();

		}
		if(!Util.isVazioOuBranco(idRegionalFinalString) && !idRegionalFinalString.equals(MENOS_UM)){
			idRegionalFinal = Util.converterStringParaInteger(idRegionalFinalString);
		}else{

			idRegionalFinal = colecaoGerenciaRegional.get(colecaoGerenciaRegional.size() - 1).getId();

		}

		parametrosConvertidos.put(P_REGIONAL_INICIAL, idRegionalInicial);
		parametrosConvertidos.put(P_REGIONAL_FINAL, idRegionalFinal);
	}

	private void converterGruposFaturamento(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idGrupoFaturamentoInicialString = Util.converterObjetoParaString(parametros.get(P_GRUPO_FATURAMENTO_INICIAL));
		String idGrupoFaturamentoFinalString = Util.converterObjetoParaString(parametros.get(P_GRUPO_FATURAMENTO_FINAL));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idGrupoFaturamentoInicial = VALOR_NAO_INFORMADO;
		Integer idGrupoFaturamentoFinal = VALOR_NAO_INFORMADO;

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

		filtroFaturamentoGrupo.setConsultaSemLimites(true);
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		ArrayList<FaturamentoGrupo> colecaoFaturamentoGrupo = (ArrayList<FaturamentoGrupo>) fachada.pesquisar(filtroFaturamentoGrupo,
						FaturamentoGrupo.class.getName());

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(idGrupoFaturamentoInicialString) && !idGrupoFaturamentoInicialString.equals(MENOS_UM)){
			// Converte os dados de String para Integer
			idGrupoFaturamentoInicial = Util.converterStringParaInteger(idGrupoFaturamentoInicialString);
		}else{
			idGrupoFaturamentoInicial = colecaoFaturamentoGrupo.get(0).getId();
		}
		if(!Util.isVazioOuBranco(idGrupoFaturamentoFinalString) && !idGrupoFaturamentoFinalString.equals(MENOS_UM)){
			idGrupoFaturamentoFinal = Util.converterStringParaInteger(idGrupoFaturamentoFinalString);
		}else{

			idGrupoFaturamentoFinal = colecaoFaturamentoGrupo.get(colecaoFaturamentoGrupo.size() - 1).getId();
		}

		parametrosConvertidos.put(P_GRUPO_FATURAMENTO_INICIAL, idGrupoFaturamentoInicial);
		parametrosConvertidos.put(P_GRUPO_FATURAMENTO_FINAL, idGrupoFaturamentoFinal);
	}

	private void converterLocalidades(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idLocalidadeInicialString = Util.converterObjetoParaString(parametros.get(P_LOCALIDADE_INICIAL));
		String idLocalidadeFinalString = Util.converterObjetoParaString(parametros.get(P_LOCALIDADE_FINAL));

		Integer idLocalidadeInicial = VALOR_NAO_INFORMADO;
		Integer idLocalidadeFinal = VALOR_NAO_INFORMADO;

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.setConsultaSemLimites(true);
		filtroLocalidade.setCampoOrderBy(FiltroLocalidade.ID);

		ArrayList<Localidade> colecaoLocalidade = (ArrayList<Localidade>) fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		if(!Util.isVazioOuBranco(idLocalidadeInicialString) && !idLocalidadeInicialString.equals(MENOS_UM)){
			idLocalidadeInicial = Util.converterStringParaInteger(idLocalidadeInicialString);
		}else{
			idLocalidadeInicial = colecaoLocalidade.get(0 + 1).getId();
		}
		if(!Util.isVazioOuBranco(idLocalidadeFinalString) && !idLocalidadeInicialString.equals(MENOS_UM)){
			idLocalidadeFinal = Util.converterStringParaInteger(idLocalidadeFinalString);
		}else{
			idLocalidadeFinal = colecaoLocalidade.get(colecaoLocalidade.size() - 1).getId();
		}

		parametrosConvertidos.put(P_LOCALIDADE_INICIAL, idLocalidadeInicial);
		parametrosConvertidos.put(P_LOCALIDADE_FINAL, idLocalidadeFinal);
	}

	private void converterQuadras(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idQuadraInicialString = Util.converterObjetoParaString(parametros.get(P_QUADRA_INICIAL));
		String idQuadraFinalString = Util.converterObjetoParaString(parametros.get(P_QUADRA_FINAL));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idQuadraInicial = VALOR_NAO_INFORMADO;
		Integer idQuadraFinal = VALOR_NAO_INFORMADO;

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.setConsultaSemLimites(true);
		filtroQuadra.setCampoOrderBy(FiltroQuadra.ID);
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		ArrayList<Quadra> colecaoQuadra = (ArrayList<Quadra>) fachada.pesquisar(filtroQuadra, Quadra.class.getName());

		if(!Util.isVazioOuBranco(idQuadraInicialString) && !idQuadraInicialString.equals(MENOS_UM)){
			idQuadraInicial = Util.converterStringParaInteger(idQuadraInicialString);
		}else{

			idQuadraInicial = colecaoQuadra.get(0).getId();

		}
		if(!Util.isVazioOuBranco(idQuadraFinalString) && !idQuadraFinalString.equals(MENOS_UM)){
			idQuadraFinal = Util.converterStringParaInteger(idQuadraFinalString);
		}else{

			idQuadraFinal = colecaoQuadra.get(colecaoQuadra.size() - 1).getId();

		}

		parametrosConvertidos.put(P_QUADRA_INICIAL, idQuadraInicial);
		parametrosConvertidos.put(P_QUADRA_FINAL, idQuadraFinal);
	}

	private void converterSetoresFaturamento(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		String idSetorFaturamentoInicialString = Util.converterObjetoParaString(parametros.get(P_SETOR_FATURAMENTO_INICIAL));
		String idSetorFaturamentoFinalString = Util.converterObjetoParaString(parametros.get(P_SETOR_FATURAMENTO_FINAL));

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		Integer idSetorFaturamentoInicial = VALOR_NAO_INFORMADO;
		Integer idSetorFaturamentoFinal = VALOR_NAO_INFORMADO;

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.setConsultaSemLimites(true);
		filtroSetorComercial.setCampoOrderBy(FiltroSetorComercial.ID);

		ArrayList<SetorComercial> colecaoSetorComercial = (ArrayList<SetorComercial>) fachada.pesquisar(filtroSetorComercial,
						SetorComercial.class.getName());

		// Verifica se foi preenchido
		if(!Util.isVazioOuBranco(idSetorFaturamentoInicialString) && !idSetorFaturamentoInicialString.equals(MENOS_UM)){
			idSetorFaturamentoInicial = Util.converterStringParaInteger(idSetorFaturamentoInicialString);
		}else{

			idSetorFaturamentoInicial = colecaoSetorComercial.get(0).getId();
		}
		if(!Util.isVazioOuBranco(idSetorFaturamentoFinalString) && !idSetorFaturamentoFinalString.equals(MENOS_UM)){
			idSetorFaturamentoFinal = Util.converterStringParaInteger(idSetorFaturamentoFinalString);
		}else{

			idSetorFaturamentoFinal = colecaoSetorComercial.get(colecaoSetorComercial.size() - 1).getId();

		}

		parametrosConvertidos.put(P_SETOR_FATURAMENTO_INICIAL, idSetorFaturamentoInicial);
		parametrosConvertidos.put(P_SETOR_FATURAMENTO_FINAL, idSetorFaturamentoFinal);
	}

	private void converterOpcao(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String parcialString = Util.converterObjetoParaString(parametros.get(P_PARCIAL));

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(parcialString)){

			String parametroDataUltimaEmissao = "";
			try{

				parametroDataUltimaEmissao = ParametroCadastro.P_ULT_EMISSAO_FICHA_CADASTRO.executar();
			}catch(ControladorException e){

				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			parametrosConvertidos.put(P_EMISSAO_GERAL_EXIBICAO, parametroDataUltimaEmissao);

			/*
			 * Se a opção do parcial a consulta trará apenas os registros de imóveis com a ultima
			 * alteração maior ou igual a data
			 * do parâmatro P_ULT_EMISSAO_FICHA_CADASTRO
			 */
			if(parcialString.equals(OPCAO_PARCIAL)){

				if(!Util.isVazioOuBranco(parametroDataUltimaEmissao)){

					parametrosConvertidos.put(P_DATA_ULTIMA_EMISSAO, Util.converteStringParaDate(parametroDataUltimaEmissao));
				}else{

					String dataUltimaEmissao = "01/01/1900";
					parametrosConvertidos.put(P_DATA_ULTIMA_EMISSAO, Util.converteStringParaDate(dataUltimaEmissao));
				}
			}else{

				String dataUltimaEmissao = "01/01/1900";
				parametrosConvertidos.put(P_DATA_ULTIMA_EMISSAO, Util.converteStringParaDate(dataUltimaEmissao));
			}
		}
	}

	private void converterIndicadorOrdenacao(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String indicadorOrdenacaoString = Util.converterObjetoParaString(parametros.get(P_INDICADOR_ORDENACAO));

		// Valida se não está nulo
		if(!Util.isVazioOuBranco(indicadorOrdenacaoString)){
			parametrosConvertidos.put(P_INDICADOR_ORDENACAO, indicadorOrdenacaoString);
		}
	}

	private void converterUnidadeNegocio(Map<String, Object> parametros, Map<String, Object> parametrosConvertidos){

		// Pega os valores em String
		String idUnidadeNegocioInicialString = Util.converterObjetoParaString(parametros.get(P_UNIDADE_NEGOCIO_INICIAL));
		String idUnidadeNegocioFinalString = Util.converterObjetoParaString(parametros.get(P_UNIDADE_NEGOCIO_FINAL));

		Integer idUnidadeNegocioInicial = VALOR_NAO_INFORMADO;
		Integer idUnidadeNegocioFinal = VALOR_NAO_INFORMADO;

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.setConsultaSemLimites(true);
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.ID);
		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		ArrayList<UnidadeNegocio> colecaoUnidadeNegocio = (ArrayList<UnidadeNegocio>) fachada.pesquisar(filtroUnidadeNegocio,
						UnidadeNegocio.class.getName());

		// Atribui o valor padrão. Se o parâmetro não for informado, será passado este valor
		if(!Util.isVazioOuBranco(idUnidadeNegocioInicialString) && !idUnidadeNegocioInicialString.equals(MENOS_UM)){
			idUnidadeNegocioInicial = Util.converterStringParaInteger(idUnidadeNegocioInicialString);
		}else{
			idUnidadeNegocioInicial = colecaoUnidadeNegocio.get(0).getId();
		}
		if(!Util.isVazioOuBranco(idUnidadeNegocioFinalString) && !idUnidadeNegocioFinalString.equals(MENOS_UM)){
			idUnidadeNegocioFinal = Util.converterStringParaInteger(idUnidadeNegocioFinalString);
		}else{
			idUnidadeNegocioFinal = colecaoUnidadeNegocio.get(colecaoUnidadeNegocio.size() - 1).getId();
		}

		parametrosConvertidos.put(P_UNIDADE_NEGOCIO_INICIAL, idUnidadeNegocioInicial);
		parametrosConvertidos.put(P_UNIDADE_NEGOCIO_FINAL, idUnidadeNegocioFinal);
	}
}
