
package gcom.cobranca.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author isilva
 */
public class AcompanhamentoExecucaoServicoCobrancaRelatorioHelper {

	private Integer idImovel;

	private Date dataEmissaoCorte;

	private Date dataExecucao;

	private String tipoCorte;

	private String tipoSupressao;

	private Date dataEmissaoReligacao;

	private Integer quantidadeDebitosCobrados;

	private BigDecimal valor;

	private String numeroInscricao;

	private String nomeCliente;

	private String grupo;

	private String endereco;

	private String bairro;

	private String telefoneResidencial;

	private String cpf;

	private String rg;

	private String grandeCliente;

	private String tipoLigacao;

	private String observacao;

	private String numeroHidrometro;

	private final BigDecimal ZERO = BigDecimal.ZERO;

	public AcompanhamentoExecucaoServicoCobrancaRelatorioHelper() {

	}

	/**
	 * @return the idImovel
	 */
	public Integer getIdImovel(){

		return idImovel;
	}

	/**
	 * @param idImovel
	 *            the idImovel to set
	 */
	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	/**
	 * @return the dataEmissaoCorte
	 */
	public Date getDataEmissaoCorte(){

		return dataEmissaoCorte;
	}

	/**
	 * @param dataEmissaoCorte
	 *            the dataEmissaoCorte to set
	 */
	public void setDataEmissaoCorte(Date dataEmissaoCorte){

		this.dataEmissaoCorte = dataEmissaoCorte;
	}

	/**
	 * @return the dataExecucao
	 */
	public Date getDataExecucao(){

		return dataExecucao;
	}

	/**
	 * @param dataExecucao
	 *            the dataExecucao to set
	 */
	public void setDataExecucao(Date dataExecucao){

		this.dataExecucao = dataExecucao;
	}

	/**
	 * @return the tipoCorte
	 */
	public String getTipoCorte(){

		return tipoCorte;
	}

	/**
	 * @param tipoCorte
	 *            the tipoCorte to set
	 */
	public void setTipoCorte(String tipoCorte){

		this.tipoCorte = tipoCorte;
	}

	/**
	 * @return the tipoSupressao
	 */
	public String getTipoSupressao(){

		return tipoSupressao;
	}

	/**
	 * @param tipoSupressao
	 *            the tipoSupressao to set
	 */
	public void setTipoSupressao(String tipoSupressao){

		this.tipoSupressao = tipoSupressao;
	}

	/**
	 * @return the dataEmissaoReligacao
	 */
	public Date getDataEmissaoReligacao(){

		return dataEmissaoReligacao;
	}

	/**
	 * @param dataEmissaoReligacao
	 *            the dataEmissaoReligacao to set
	 */
	public void setDataEmissaoReligacao(Date dataEmissaoReligacao){

		this.dataEmissaoReligacao = dataEmissaoReligacao;
	}

	/**
	 * @return the quantidadeDebitosCobrados
	 */
	public Integer getQuantidadeDebitosCobrados(){

		return quantidadeDebitosCobrados;
	}

	/**
	 * @param quantidadeDebitosCobrados
	 *            the quantidadeDebitosCobrados to set
	 */
	public void setQuantidadeDebitosCobrados(Integer quantidadeDebitosCobrados){

		this.quantidadeDebitosCobrados = quantidadeDebitosCobrados;
	}

	/**
	 * @return the valor
	 */
	public BigDecimal getValor(){

		return valor;
	}

	/**
	 * @param valor
	 *            the valor to set
	 */
	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	/**
	 * @return the numeroInscricao
	 */
	public String getNumeroInscricao(){

		return numeroInscricao;
	}

	/**
	 * @param numeroInscricao
	 *            the numeroInscricao to set
	 */
	public void setNumeroInscricao(String numeroInscricao){

		this.numeroInscricao = numeroInscricao;
	}

	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente(){

		return nomeCliente;
	}

	/**
	 * @param nomeCliente
	 *            the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	/**
	 * @return the grupo
	 */
	public String getGrupo(){

		return grupo;
	}

	/**
	 * @param grupo
	 *            the grupo to set
	 */
	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco(){

		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	/**
	 * @return the bairro
	 */
	public String getBairro(){

		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	/**
	 * @return the telefoneResidencial
	 */
	public String getTelefoneResidencial(){

		return telefoneResidencial;
	}

	/**
	 * @param telefoneResidencial
	 *            the telefoneResidencial to set
	 */
	public void setTelefoneResidencial(String telefoneResidencial){

		this.telefoneResidencial = telefoneResidencial;
	}

	/**
	 * @return the cpf
	 */
	public String getCpf(){

		return cpf;
	}

	/**
	 * @param cpf
	 *            the cpf to set
	 */
	public void setCpf(String cpf){

		this.cpf = cpf;
	}

	/**
	 * @return the rg
	 */
	public String getRg(){

		return rg;
	}

	/**
	 * @param rg
	 *            the rg to set
	 */
	public void setRg(String rg){

		this.rg = rg;
	}

	/**
	 * @return the grandeCliente
	 */
	public String getGrandeCliente(){

		return grandeCliente;
	}

	/**
	 * @param grandeCliente
	 *            the grandeCliente to set
	 */
	public void setGrandeCliente(String grandeCliente){

		this.grandeCliente = grandeCliente;
	}

	/**
	 * @return the tipoLigacao
	 */
	public String getTipoLigacao(){

		return tipoLigacao;
	}

	/**
	 * @param tipoLigacao
	 *            the tipoLigacao to set
	 */
	public void setTipoLigacao(String tipoLigacao){

		this.tipoLigacao = tipoLigacao;
	}

	/**
	 * @return the observacao
	 */
	public String getObservacao(){

		return observacao;
	}

	/**
	 * @param observacao
	 *            the observacao to set
	 */
	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	/**
	 * @return the numeroHidrometro
	 */
	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	/**
	 * @param numeroHidrometro
	 *            the numeroHidrometro to set
	 */
	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}
}
