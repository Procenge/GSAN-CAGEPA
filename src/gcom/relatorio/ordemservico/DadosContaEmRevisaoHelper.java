/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author isilva
 */
public class DadosContaEmRevisaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private int fatura;

	private Date vencimento;

	private BigDecimal valorAgua;

	private BigDecimal valorEsgoto;

	private BigDecimal valorDebitos;

	private BigDecimal valorCreditos;

	private Integer idMotivo;

	private String descricaoMotivo;

	public DadosContaEmRevisaoHelper(int fatura, Date vencimento, BigDecimal valorAgua, BigDecimal valorEsgoto, BigDecimal valorDebitos,
										BigDecimal valorCreditos, Integer idMotivo, String descricaoMotivo) {

		super();
		this.fatura = fatura;
		this.vencimento = vencimento;
		this.valorAgua = valorAgua;
		this.valorEsgoto = valorEsgoto;
		this.valorDebitos = valorDebitos;
		this.valorCreditos = valorCreditos;
		this.idMotivo = idMotivo;
		this.descricaoMotivo = descricaoMotivo;
	}

	public int getFatura(){

		return fatura;
	}

	public void setFatura(int fatura){

		this.fatura = fatura;
	}

	public Date getVencimento(){

		return vencimento;
	}

	public void setVencimento(Date vencimento){

		this.vencimento = vencimento;
	}

	public BigDecimal getValorAgua(){

		return valorAgua;
	}

	public void setValorAgua(BigDecimal valorAgua){

		this.valorAgua = valorAgua;
	}

	public BigDecimal getValorEsgoto(){

		return valorEsgoto;
	}

	public void setValorEsgoto(BigDecimal valorEsgoto){

		this.valorEsgoto = valorEsgoto;
	}

	public BigDecimal getValorDebitos(){

		return valorDebitos;
	}

	public void setValorDebitos(BigDecimal valorDebitos){

		this.valorDebitos = valorDebitos;
	}

	public BigDecimal getValorCreditos(){

		return valorCreditos;
	}

	public void setValorCreditos(BigDecimal valorCreditos){

		this.valorCreditos = valorCreditos;
	}

	public Integer getIdMotivo(){

		return idMotivo;
	}

	public void setIdMotivo(Integer idMotivo){

		this.idMotivo = idMotivo;
	}

	public String getDescricaoMotivo(){

		return descricaoMotivo;
	}

	public void setDescricaoMotivo(String descricaoMotivo){

		this.descricaoMotivo = descricaoMotivo;
	}

	public BigDecimal getValorTotal(){

		BigDecimal retorno = BigDecimal.ZERO;

		if(getValorAgua() != null){
			retorno = getValorAgua();
		}

		if(getValorEsgoto() != null){
			retorno = retorno.add(getValorEsgoto());
		}

		if(getValorDebitos() != null){
			retorno = retorno.subtract(getValorDebitos());
		}

		if(getValorCreditos() != null){
			retorno = retorno.add(getValorCreditos());
		}

		return retorno;
	}

	public String getIdDescricao(){

		if(!Util.isVazioOuBranco(getIdMotivo()) && !Util.isVazioOuBranco(getDescricaoMotivo())){
			return getIdMotivo().toString() + " - " + getDescricaoMotivo();
		}else if(!Util.isVazioOuBranco(getIdMotivo())){
			return getIdMotivo().toString();
		}else if(!Util.isVazioOuBranco(getDescricaoMotivo())){
			return getDescricaoMotivo();
		}

		return "";
	}
}
