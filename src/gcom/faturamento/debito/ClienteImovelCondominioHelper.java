package gcom.faturamento.debito;

import gcom.util.Util;

import java.util.Date;

/**
 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor
 * 6. O sistema apresenta os dados do imóvel Condomínio:
 * 
 * @author Josenildo Neves
 *         Date 20/08/2013
 */
public class ClienteImovelCondominioHelper {

	// id do cliente
	private Integer idCliente;

	// 6.4.1. Nome do cliente
	private String nomeCliente;

	// 6.4.2. Tipo de relação
	private String descricaoTipoRelacaoCliente;

	// 6.4.3. Data de início da relação
	private Date dataInicioRelacao;

	// 6.4.4. Telefone principal
	private String dddTelefonePrincipal;
	private String telefonePrincipal;

	// 6.4.5. CPF/CNPJ
	private String cpfCnpj;

	// 6.4.6. Endereço do Imóvel <<Inclui>> [UC0085 - Obter Endereço];
	private String enderecoImovel;

	// 6.4.7. Histórico de Medição Individualizada
	private HistoricoMedicaoIndividualizadaHelper historicoMedicaoIndividualizadaHelper;

	/**
	 * @param idCliente
	 * @param nomeCliente
	 * @param descricaoCliente
	 * @param dataInicioRelacao
	 * @param dddTelefonePrincipal
	 * @param telefonePrincipal
	 * @param cpfCnpj
	 */
	public ClienteImovelCondominioHelper(Integer idCliente, String nomeCliente, String descricaoTipoRelacaoCliente, Date dataInicioRelacao,
											String dddTelefonePrincipal, String telefonePrincipal, String cpfCnpj) {

		super();
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
		this.descricaoTipoRelacaoCliente = descricaoTipoRelacaoCliente;
		this.dataInicioRelacao = dataInicioRelacao;
		this.dddTelefonePrincipal = dddTelefonePrincipal;
		this.telefonePrincipal = telefonePrincipal;
		this.cpfCnpj = cpfCnpj;
	}

	/**
	 * @return the idCliente
	 */
	public Integer getIdCliente(){

		return idCliente;
	}

	/**
	 * @param idCliente
	 *            the idCliente to set
	 */
	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
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
	 * @return the descricaoCliente
	 */
	public String getDescricaoTipoRelacaoCliente(){

		return descricaoTipoRelacaoCliente;
	}

	/**
	 * @param descricaoCliente
	 *            the descricaoCliente to set
	 */
	public void setDescricaoTipoRelacaoCliente(String descricaoTipoRelacaoCliente){

		this.descricaoTipoRelacaoCliente = descricaoTipoRelacaoCliente;
	}

	/**
	 * @return the dataInicioRelacao
	 */
	public Date getDataInicioRelacao(){

		return dataInicioRelacao;
	}

	/**
	 * @param dataInicioRelacao
	 *            the dataInicioRelacao to set
	 */
	public void setDataInicioRelacao(Date dataInicioRelacao){

		this.dataInicioRelacao = dataInicioRelacao;
	}

	/**
	 * @return the dddTelefonePrincipal
	 */
	public String getDddTelefonePrincipal(){

		return dddTelefonePrincipal;
	}

	/**
	 * @param dddTelefonePrincipal
	 *            the dddTelefonePrincipal to set
	 */
	public void setDddTelefonePrincipal(String dddTelefonePrincipal){

		this.dddTelefonePrincipal = dddTelefonePrincipal;
	}

	/**
	 * @return the telefonePrincipal
	 */
	public String getTelefonePrincipal(){

		return telefonePrincipal;
	}

	/**
	 * @param telefonePrincipal
	 *            the telefonePrincipal to set
	 */
	public void setTelefonePrincipal(String telefonePrincipal){

		this.telefonePrincipal = telefonePrincipal;
	}

	/**
	 * @return the cpfCnpj
	 */
	public String getCpfCnpj(){

		String cpfCnpj = "";
		if(Util.isNaoNuloBrancoZero(this.cpfCnpj)){
			if(this.cpfCnpj.length() <= 11){
				cpfCnpj = Util.formatarCpf(this.cpfCnpj);
			}else{
				cpfCnpj = Util.formatarCnpj(this.cpfCnpj);
			}
		}

		return cpfCnpj;
	}

	/**
	 * @param cpfCnpj
	 *            the cpfCnpj to set
	 */
	public void setCpfCnpj(String cpfCnpj){

		this.cpfCnpj = cpfCnpj;
	}

	/**
	 * @return the enderecoImovel
	 */
	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	/**
	 * @param enderecoImovel
	 *            the enderecoImovel to set
	 */
	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getTelefoneFormatado(){

		String telefoneFormatado = "";
		if(Util.isNaoNuloBrancoZero(this.dddTelefonePrincipal) && Util.isNaoNuloBrancoZero(this.dddTelefonePrincipal)){
			telefoneFormatado = "(" + this.dddTelefonePrincipal + ") " + Util.formatarFone(this.telefonePrincipal);
		}
		return telefoneFormatado;
	}

	/**
	 * @return the historicoMedicaoIndividualizadaHelper
	 */
	public HistoricoMedicaoIndividualizadaHelper getHistoricoMedicaoIndividualizadaHelper(){

		return historicoMedicaoIndividualizadaHelper;
	}

	/**
	 * @param historicoMedicaoIndividualizadaHelper
	 *            the historicoMedicaoIndividualizadaHelper to set
	 */
	public void setHistoricoMedicaoIndividualizadaHelper(HistoricoMedicaoIndividualizadaHelper historicoMedicaoIndividualizadaHelper){

		this.historicoMedicaoIndividualizadaHelper = historicoMedicaoIndividualizadaHelper;
	}

}
