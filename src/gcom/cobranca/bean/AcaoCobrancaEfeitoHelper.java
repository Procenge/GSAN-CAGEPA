
package gcom.cobranca.bean;

import gcom.cobranca.AcaoCobrancaEfeito;
import gcom.cobranca.CobrancaDocumento;

import java.util.Date;

public class AcaoCobrancaEfeitoHelper {

	private AcaoCobrancaEfeito acaoCobrancaEfeito;

	private Date dataEmissaoDocumentoCobranca;

	private CobrancaDocumento cobrancaDocumento;

	public CobrancaDocumento getCobrancaDocumento(){

		return cobrancaDocumento;
	}

	public void setCobrancaDocumento(CobrancaDocumento cobrancaDocumento){

		this.cobrancaDocumento = cobrancaDocumento;
	}

	public AcaoCobrancaEfeito getAcaoCobrancaEfeito(){

		return acaoCobrancaEfeito;
	}

	public void setAcaoCobrancaEfeito(AcaoCobrancaEfeito acaoCobrancaEfeito){

		this.acaoCobrancaEfeito = acaoCobrancaEfeito;
	}

	public Date getDataEmissaoDocumentoCobranca(){

		return dataEmissaoDocumentoCobranca;
	}

	public void setDataEmissaoDocumentoCobranca(Date dataEmissaoDocumentoCobranca){

		this.dataEmissaoDocumentoCobranca = dataEmissaoDocumentoCobranca;
	}

}
