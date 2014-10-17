
package gcom.cobranca.bean;

import java.io.Serializable;

/**
 * @author ysouza
 */
public class DebitoACobrarParcelamentoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idParcelamento;

	private Integer idImovel;

	private Integer anoMesReferenciaDebito;

	private Integer anoMesCobrancaDebito;

	private Integer numeroPrestacaoDebito;

	private Integer numeroPrestacaoCobrada;

	public DebitoACobrarParcelamentoHelper() {

	}

	public Integer getIdParcelamento(){

		return idParcelamento;
	}

	public void setIdParcelamento(Integer idParcelamento){

		this.idParcelamento = idParcelamento;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getAnoMesReferenciaDebito(){

		return anoMesReferenciaDebito;
	}

	public void setAnoMesReferenciaDebito(Integer anoMesReferenciaDebito){

		this.anoMesReferenciaDebito = anoMesReferenciaDebito;
	}

	public Integer getAnoMesCobrancaDebito(){

		return anoMesCobrancaDebito;
	}

	public void setAnoMesCobrancaDebito(Integer anoMesCobrancaDebito){

		this.anoMesCobrancaDebito = anoMesCobrancaDebito;
	}

	public Integer getNumeroPrestacaoDebito(){

		return numeroPrestacaoDebito;
	}

	public void setNumeroPrestacaoDebito(Integer numeroPrestacaoDebito){

		this.numeroPrestacaoDebito = numeroPrestacaoDebito;
	}

	public Integer getNumeroPrestacaoCobrada(){

		return numeroPrestacaoCobrada;
	}

	public void setNumeroPrestacaoCobrada(Integer numeroPrestacaoCobrada){

		this.numeroPrestacaoCobrada = numeroPrestacaoCobrada;
	}

}
