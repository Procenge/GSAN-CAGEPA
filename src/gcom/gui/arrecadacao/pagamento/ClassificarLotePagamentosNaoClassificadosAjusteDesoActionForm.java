
package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.cadastro.localidade.Localidade;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author Magno Silveira (magno.silveira@procenge.com.br)
 * @since 10/12/2014
 */
public class ClassificarLotePagamentosNaoClassificadosAjusteDesoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private char opcaoGeracao;

	private Localidade localidade;

	private String referenciaPagamentoInicial;

	private String referenciaPagamentoFinal;

	private String dataPagamentoInicial;

	private String dataPagamentoFinal;

	private PagamentoSituacao pagamentoSituacao;

	/**
	 * @return the opcaoGeracao
	 */
	public char getOpcaoGeracao(){

		return opcaoGeracao;
	}

	/**
	 * @param opcaoGeracao
	 *            the opcaoGeracao to set
	 */
	public void setOpcaoGeracao(char opcaoGeracao){

		this.opcaoGeracao = opcaoGeracao;
	}

	/**
	 * @return the localidade
	 */
	public Localidade getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade
	 *            the localidade to set
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * @return the referenciaPagamentoInicial
	 */
	public String getReferenciaPagamentoInicial(){

		return referenciaPagamentoInicial;
	}

	/**
	 * @param referenciaPagamentoInicial
	 *            the referenciaPagamentoInicial to set
	 */
	public void setReferenciaPagamentoInicial(String referenciaPagamentoInicial){

		this.referenciaPagamentoInicial = referenciaPagamentoInicial;
	}

	/**
	 * @return the referenciaPagamentoFinal
	 */
	public String getReferenciaPagamentoFinal(){

		return referenciaPagamentoFinal;
	}

	/**
	 * @param referenciaPagamentoFinal
	 *            the referenciaPagamentoFinal to set
	 */
	public void setReferenciaPagamentoFinal(String referenciaPagamentoFinal){

		this.referenciaPagamentoFinal = referenciaPagamentoFinal;
	}

	/**
	 * @return the dataPagamentoInicial
	 */
	public String getDataPagamentoInicial(){

		return dataPagamentoInicial;
	}

	/**
	 * @param dataPagamentoInicial
	 *            the dataPagamentoInicial to set
	 */
	public void setDataPagamentoInicial(String dataPagamentoInicial){

		this.dataPagamentoInicial = dataPagamentoInicial;
	}

	/**
	 * @return the dataPagamentoFinal
	 */
	public String getDataPagamentoFinal(){

		return dataPagamentoFinal;
	}

	/**
	 * @param dataPagamentoFinal
	 *            the dataPagamentoFinal to set
	 */
	public void setDataPagamentoFinal(String dataPagamentoFinal){

		this.dataPagamentoFinal = dataPagamentoFinal;
	}

	/**
	 * @return the pagamentoSituacao
	 */
	public PagamentoSituacao getPagamentoSituacao(){

		return pagamentoSituacao;
	}

	/**
	 * @param pagamentoSituacao
	 *            the pagamentoSituacao to set
	 */
	public void setPagamentoSituacao(PagamentoSituacao pagamentoSituacao){

		this.pagamentoSituacao = pagamentoSituacao;
	}

}
