
package gcom.faturamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RelacaoTresDeso
				implements Serializable {

	private Integer id;

	private Integer idFaturamentoGrupo;

	private Integer idImovel;

	private Integer idDebitoTipo;

	private BigDecimal valorRestanteSerCobrado;

	private BigDecimal valorGeradoPrimeiraRotinaAjt;

	private BigDecimal valorGeradoRotinaAjusteAtual;

	private BigDecimal valorFaturado;

	private BigDecimal valorEnviado;

	private Date ultimaAlteracao;

	private Integer anoMesReferencia;

	private Integer mediaAnterior;

	private Integer novaMedia;

	private Integer idConta;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(Integer idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdDebitoTipo(){

		return idDebitoTipo;
	}

	public void setIdDebitoTipo(Integer idDebitoTipo){

		this.idDebitoTipo = idDebitoTipo;
	}

	public BigDecimal getValorRestanteSerCobrado(){

		return valorRestanteSerCobrado;
	}

	public void setValorRestanteSerCobrado(BigDecimal valorRestanteSerCobrado){

		this.valorRestanteSerCobrado = valorRestanteSerCobrado;
	}

	public BigDecimal getValorGeradoPrimeiraRotinaAjt(){

		return valorGeradoPrimeiraRotinaAjt;
	}

	public void setValorGeradoPrimeiraRotinaAjt(BigDecimal valorGeradoPrimeiraRotinaAjt){

		this.valorGeradoPrimeiraRotinaAjt = valorGeradoPrimeiraRotinaAjt;
	}

	public BigDecimal getValorGeradoRotinaAjusteAtual(){

		return valorGeradoRotinaAjusteAtual;
	}

	public void setValorGeradoRotinaAjusteAtual(BigDecimal valorGeradoRotinaAjusteAtual){

		this.valorGeradoRotinaAjusteAtual = valorGeradoRotinaAjusteAtual;
	}

	public BigDecimal getValorFaturado(){

		return valorFaturado;
	}

	public void setValorFaturado(BigDecimal valorFaturado){

		this.valorFaturado = valorFaturado;
	}

	public BigDecimal getValorEnviado(){

		return valorEnviado;
	}

	public void setValorEnviado(BigDecimal valorEnviado){

		this.valorEnviado = valorEnviado;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Integer getMediaAnterior(){

		return mediaAnterior;
	}

	public void setMediaAnterior(Integer mediaAnterior){

		this.mediaAnterior = mediaAnterior;
	}

	public Integer getNovaMedia(){

		return novaMedia;
	}

	public void setNovaMedia(Integer novaMedia){

		this.novaMedia = novaMedia;
	}

	public Integer getIdConta(){

		return idConta;
	}

	public void setIdConta(Integer idConta){

		this.idConta = idConta;
	}

}
