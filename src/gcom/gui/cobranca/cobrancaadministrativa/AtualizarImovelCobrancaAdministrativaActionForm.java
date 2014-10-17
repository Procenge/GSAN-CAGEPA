
package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.cobranca.bean.ItensRemuradosHelper;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;

/**
 * [UC3060] Consultar Im�vel Cobran�a Administrativa
 * [SB0001] Consultar Dados da Cobran�a Administrativa do Im�vel
 * 
 * @author Hebert Falc�o
 * @date 15/09/2012
 */
public class AtualizarImovelCobrancaAdministrativaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	// 1.1. Im�vel
	private String idImovel;

	//
	// 1.2. Dados do Im�vel
	//

	// 1.2.1. Inscri��o
	private String inscricaoFormatadaImovel;

	// 1.2.2. Situa��o de �gua
	private String descricaoLigacaoAguaSituacao;

	// 1.2.3. Situa��o de Esgoto
	private String descricaoLigacaoEsgotoSituacao;

	// 1.2.4. Perfil do Im�vel
	private String descricaoImovelPerfil;

	// 1.2.5. Endere�o do Im�vel
	private String enderecoImovel;

	// 1.2.6. Lista das subcategorias e quantidades de economias do im�vel

	// 1.2.7. Lista dos clientes associados ao im�vel

	//
	// 1.3. Dados da Cobran�a Administrativa
	//

	// 1.3.1. Empresa
	private String empresa;

	// 1.3.2. Data da Implanta��o
	private String dataImplantacao;

	// 1.3.3. Data da Retirada
	private String dataRetirada;

	// 1.3.4. Motivo da Retirada
	private String motivoRetirada;

	// 1.3.5. Data da Adimpl�ncia
	private String dataAdimplencia;

	// 1.3.6. Motivo da Adimpl�ncia
	private String motivoAdimplencia;

	// 1.3.7. Quantidade de D�bitos
	private String quantidadeDebitos;

	// 1.3.8. Valor dos D�bitos
	private String valorDebitos;

	//
	// 1.4. Dados dos Itens do D�bito do Im�vel
	//

	private String descricaoDocumentoTipoConta;

	private String descricaoDocumentoTipoGuia;

	private String descricaoDocumentoTipoDebito;

	//
	// 1.5. Dados da Remunera��o da Cobran�a Administrativa do Im�vel
	//

	// Percentual de Remunera��o Padr�o do Im�vel
	private String percentualRemuneracao;

	private List<ItensRemuradosHelper> itensRemuradosHelper = new ArrayList<ItensRemuradosHelper>();

	private String quantidadeItensRemunerados;

	private String valorItensRemunerados;

	public String getIdImovel(){

		return idImovel;
	}

	public String getEmpresa(){

		return empresa;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoFormatadaImovel(){

		return inscricaoFormatadaImovel;
	}

	public void setInscricaoFormatadaImovel(String inscricaoFormatadaImovel){

		this.inscricaoFormatadaImovel = inscricaoFormatadaImovel;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public String getDescricaoImovelPerfil(){

		return descricaoImovelPerfil;
	}

	public void setDescricaoImovelPerfil(String descricaoImovelPerfil){

		this.descricaoImovelPerfil = descricaoImovelPerfil;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getDataImplantacao(){

		return dataImplantacao;
	}

	public void setDataImplantacao(String dataImplantacao){

		this.dataImplantacao = dataImplantacao;
	}

	public String getDataRetirada(){

		return dataRetirada;
	}

	public void setDataRetirada(String dataRetirada){

		this.dataRetirada = dataRetirada;
	}

	public String getMotivoRetirada(){

		return motivoRetirada;
	}

	public void setMotivoRetirada(String motivoRetirada){

		this.motivoRetirada = motivoRetirada;
	}

	public String getDataAdimplencia(){

		return dataAdimplencia;
	}

	public void setDataAdimplencia(String dataAdimplencia){

		this.dataAdimplencia = dataAdimplencia;
	}

	public String getMotivoAdimplencia(){

		return motivoAdimplencia;
	}

	public void setMotivoAdimplencia(String motivoAdimplencia){

		this.motivoAdimplencia = motivoAdimplencia;
	}

	public String getQuantidadeDebitos(){

		return quantidadeDebitos;
	}

	public void setQuantidadeDebitos(String quantidadeDebitos){

		this.quantidadeDebitos = quantidadeDebitos;
	}

	public String getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(String valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public String getDescricaoDocumentoTipoConta(){

		return descricaoDocumentoTipoConta;
	}

	public void setDescricaoDocumentoTipoConta(String descricaoDocumentoTipoConta){

		this.descricaoDocumentoTipoConta = descricaoDocumentoTipoConta;
	}

	public String getDescricaoDocumentoTipoGuia(){

		return descricaoDocumentoTipoGuia;
	}

	public void setDescricaoDocumentoTipoGuia(String descricaoDocumentoTipoGuia){

		this.descricaoDocumentoTipoGuia = descricaoDocumentoTipoGuia;
	}

	public void limpar(){

		this.setEmpresa("");
		this.setIdImovel("");
		this.setInscricaoFormatadaImovel("");
		this.setDescricaoLigacaoAguaSituacao("");
		this.setDescricaoLigacaoEsgotoSituacao("");
		this.setDescricaoImovelPerfil("");
		this.setEnderecoImovel("");
		this.setDataImplantacao("");
		this.setDataRetirada("");
		this.setMotivoRetirada("");
		this.setDataAdimplencia("");
		this.setMotivoAdimplencia("");
		this.setQuantidadeDebitos("");
		this.setValorDebitos("");
		this.setDescricaoDocumentoTipoConta("");
		this.setDescricaoDocumentoTipoGuia("");
		this.setPercentualRemuneracao("");

	}

	public String getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(String percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public List<ItensRemuradosHelper> getItensRemuradosHelper(){

		return itensRemuradosHelper;
	}

	public void setItensRemuradosHelper(List<ItensRemuradosHelper> itensRemuradosHelper){

		this.itensRemuradosHelper = itensRemuradosHelper;
	}

	public void addItensRemuradosHelper(ItensRemuradosHelper itensRemuradosHelper){

		this.itensRemuradosHelper.add(itensRemuradosHelper);
	}

	/**
	 * @return the quantidadeItensRemunerados
	 */
	public String getQuantidadeItensRemunerados(){

		return quantidadeItensRemunerados;
	}

	/**
	 * @param quantidadeItensRemunerados
	 *            the quantidadeItensRemunerados to set
	 */
	public void setQuantidadeItensRemunerados(String quantidadeItensRemunerados){

		this.quantidadeItensRemunerados = quantidadeItensRemunerados;
	}

	/**
	 * @return the valorItensRemunerados
	 */
	public String getValorItensRemunerados(){

		return valorItensRemunerados;
	}

	/**
	 * @param valorItensRemunerados
	 *            the valorItensRemunerados to set
	 */
	public void setValorItensRemunerados(String valorItensRemunerados){

		this.valorItensRemunerados = valorItensRemunerados;
	}

	/**
	 * @return the descricaoDocumentoTipoDebito
	 */
	public String getDescricaoDocumentoTipoDebito(){

		return descricaoDocumentoTipoDebito;
	}

	/**
	 * @param descricaoDocumentoTipoDebito
	 *            the descricaoDocumentoTipoDebito to set
	 */
	public void setDescricaoDocumentoTipoDebito(String descricaoDocumentoTipoDebito){

		this.descricaoDocumentoTipoDebito = descricaoDocumentoTipoDebito;
	}

}
