
package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.cadastro.imovel.Imovel;

import java.math.BigDecimal;

/**
 * [UC3023] Manter Boleto Bancário
 * Classe facilitadora para o retorno do filtro a ser usado no manter.
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class BoletoBancarioTotalizadorHelper {

	private Integer idArrecadador;

	private Arrecadador arrecadador;

	private Imovel imovel;

	private Short codigoAgenteArrecadador;

	private Integer idImovel;

	private BigDecimal valorTotalDebito;

	public BoletoBancarioTotalizadorHelper(Short codigoAgenteArrecadador, Integer idImovel, BigDecimal valorTotalDebito) {

		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
		this.idImovel = idImovel;
		this.valorTotalDebito = valorTotalDebito;
	}

	public BoletoBancarioTotalizadorHelper(Arrecadador arrecadador, Imovel imovel, BigDecimal valorTotalDebito) {

		this.arrecadador = arrecadador;
		this.imovel = imovel;
		this.valorTotalDebito = valorTotalDebito;
	}

	public BoletoBancarioTotalizadorHelper(Integer idArrecadador, Integer idImovel, BigDecimal valorTotalDebito) {

		this.idArrecadador = idArrecadador;
		this.idImovel = idImovel;
		this.valorTotalDebito = valorTotalDebito;
	}

	public BoletoBancarioTotalizadorHelper(Integer idArrecadador, Short codigoAgenteArrecadador, Integer idImovel,
											BigDecimal valorTotalDebito) {

		this.idArrecadador = idArrecadador;
		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
		this.idImovel = idImovel;
		this.valorTotalDebito = valorTotalDebito;
	}

	public Short getCodigoAgenteArrecadador(){

		return codigoAgenteArrecadador;
	}

	public void setCodigoAgenteArrecadador(Short codigoAgenteArrecadador){

		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public BigDecimal getValorTotalDebito(){

		return valorTotalDebito;
	}

	public void setValorDebito(BigDecimal valorTotalDebito){

		this.valorTotalDebito = valorTotalDebito;
	}

}
