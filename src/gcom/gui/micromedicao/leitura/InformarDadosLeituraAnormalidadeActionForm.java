/**
 * 
 */

package gcom.gui.micromedicao.leitura;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Bruno Ferreira dos Santos
 */
public class InformarDadosLeituraAnormalidadeActionForm
				extends ValidatorActionForm {

	public static final String DEFAULT_ACTION_MAPPING = "exibirInformarDadosLeituraAnormalidadeAction";

	public static final String PESQUISAR_ACTION_MAPPING = "pesquisarDadosImovelLeituraAnormalidade";

	public static final String PESQUISAR_LEITURISTA = "pesquisarLeiturista";

	public static final String IMOVEL_ROTA = "imoveisRota";

	public static final String PROXIMO = "proximo";

	public static final String ANTERIOR = "anterior";

	public static final String POSICAO_ROTA = "posicaoRota";

	public static final String LISTA_IMOVEIS_ROTA = "listaImoveisRota";

	public static final String QUANTIDADE_IMOVEIS_ROTA = "qtdImoveisRota";

	public static final String ATUALIZAR = "atualizar";

	public static final String ATUALIZAR_DIGITACAO = "atualizarDigitacao";

	public static final String PESQUISAR_IMOVEL_ROTA = "pesquisaImovelRota";

	public static final String PESQUISAR_ROTA = "pesquisaRota";

	public static final String ROTAS = "rotas";

	public static final String TOTAL_REGISTROS = "totalRegistros";

	public static final String COLECAO_EMPRESA = "colecaoEmpresa";

	public static final String DESFAZER = "desfazer";

	public static final String COLECAO_LEITURISTA = "colecaoLeiturista";

	public static final String COLECAO_OCORRENCIA = "colecaoOcorrencia";

	public static final String ANTERIOR_IMOVEL_ROTA = "anteriorImovelRota";

	public static final String PROXIMO_IMOVEL_ROTA = "proximoImovelRota";

	public static final String COLECAO_IMOVEIS_ROTA = "colecaoImovelRota";

	public static final String PESQUISAR_IMOVEL_ROTA_POPUP = "pesquisarImovelRotaPopup";

	public static final String RETORNAR_IMOVEL_ROTA = "retornarImovelRota";

	public static final String PROCESSAR_ARQUIVO = "processarArquivo";

	public static final String DATA_LEITURA = "dataLeitura";

	private String referenciaMovimento;

	private String idEmpresa;

	private String codigoLeiturista;

	private String dataLeitura;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String sequecialImovelRota;

	private String hidrometro;

	private String leitura;

	private String idOcorrencia;

	private String idRota;

	private String indicadorEntradaDados;

	private String idGrupoFaturamento;

	private FormFile arquivoLeitura;

	public String getReferenciaMovimento(){

		return referenciaMovimento;
	}

	public void setReferenciaMovimento(String referenciaMovimento){

		this.referenciaMovimento = referenciaMovimento;
	}

	public String getIdEmpresa(){

		return idEmpresa;
	}

	public void setIdEmpresa(String idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	public String getDataLeitura(){

		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura){

		this.dataLeitura = dataLeitura;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getSequecialImovelRota(){

		return sequecialImovelRota;
	}

	public void setSequecialImovelRota(String sequecialImovelRota){

		this.sequecialImovelRota = sequecialImovelRota;
	}

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public String getLeitura(){

		return leitura;
	}

	public void setLeitura(String leitura){

		this.leitura = leitura;
	}

	public String getIdOcorrencia(){

		return idOcorrencia;
	}

	public void setIdOcorrencia(String idOcorrencia){

		this.idOcorrencia = idOcorrencia;
	}

	public void setCodigoLeiturista(String codigoLeiturista){

		this.codigoLeiturista = codigoLeiturista;
	}

	public String getCodigoLeiturista(){

		return codigoLeiturista;
	}

	public String getIndicadorEntradaDados(){

		return indicadorEntradaDados;
	}

	public void setIndicadorEntradaDados(String indicadorEntradaDados){

		this.indicadorEntradaDados = indicadorEntradaDados;
	}

	public String getIdGrupoFaturamento(){

		return idGrupoFaturamento;
	}

	public void setIdGrupoFaturamento(String idGrupoFaturamento){

		this.idGrupoFaturamento = idGrupoFaturamento;
	}

	public FormFile getArquivoLeitura(){

		return arquivoLeitura;
	}

	public void setArquivoLeitura(FormFile arquivoLeitura){

		this.arquivoLeitura = arquivoLeitura;
	}

	public String getIdRota(){

		return idRota;
	}

	public void setIdRota(String idRota){

		this.idRota = idRota;
	}

}
