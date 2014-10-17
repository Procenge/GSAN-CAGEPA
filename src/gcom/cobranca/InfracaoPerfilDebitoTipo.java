
package gcom.cobranca;

import gcom.faturamento.debito.DebitoTipo;

import java.math.BigDecimal;
import java.util.Date;

public class InfracaoPerfilDebitoTipo {

	private Integer id;

	private Date ultimaAlteracao;

	private Integer indicadorLancamentoAtivo;

	private BigDecimal numeroFatorMultiplicador;

	private BigDecimal porcentagemDesconto;

	private InfracaoPerfil infracaoPerfil;

	private DebitoTipo debitoTipo;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getIndicadorLancamentoAtivo(){

		return indicadorLancamentoAtivo;
	}

	public void setIndicadorLancamentoAtivo(Integer indicadorLancamentoAtivo){

		this.indicadorLancamentoAtivo = indicadorLancamentoAtivo;
	}

	public BigDecimal getNumeroFatorMultiplicador(){

		return numeroFatorMultiplicador;
	}

	public void setNumeroFatorMultiplicador(BigDecimal numeroFatorMultiplicador){

		this.numeroFatorMultiplicador = numeroFatorMultiplicador;
	}

	public BigDecimal getPorcentagemDesconto(){

		return porcentagemDesconto;
	}

	public void setPorcentagemDesconto(BigDecimal porcentagemDesconto){

		this.porcentagemDesconto = porcentagemDesconto;
	}

	public InfracaoPerfil getInfracaoPerfil(){

		return infracaoPerfil;
	}

	public void setInfracaoPerfil(InfracaoPerfil infracaoPerfil){

		this.infracaoPerfil = infracaoPerfil;
	}

	public DebitoTipo getDebitoTipo(){

		return debitoTipo;
	}

	public void setDebitoTipo(DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

}
