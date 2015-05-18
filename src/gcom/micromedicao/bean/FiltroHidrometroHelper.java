package gcom.micromedicao.bean;

import gcom.util.filtro.Filtro;

import java.util.Date;


public class FiltroHidrometroHelper
				extends Filtro {

	private String numero;

	private Date dataAquisicao;

	private Short anoFabricacao;

	private Short indicadorMacromedidor;

	private Integer idHidrometroClasseMetrologica;

	private Integer idHidrometroMarca;

	private Integer idHidrometroDiametro;

	private Integer idHidrometroCapacidade;

	private Integer idHidrometroTipo;

	private Integer idHidrometroSituacao;

	private Integer idHidrometroLocalArmazenagem;

	private Integer idHidrometroTipoTurbina;

	private Date dataInstalacao;

	private boolean consultarHistoricoInstalacao;

	private boolean consultaSemLimites;

	private Integer numeroNotaFiscal;

	private String loteEntrega;

	public String getLoteEntrega(){

		return loteEntrega;
	}

	public void setLoteEntrega(String loteEntrega){

		this.loteEntrega = loteEntrega;
	}

	public FiltroHidrometroHelper() {

	}

	public String getNumero(){

		return numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	public Date getDataAquisicao(){

		return dataAquisicao;
	}

	public void setDataAquisicao(Date dataAquisicao){

		this.dataAquisicao = dataAquisicao;
	}

	public Short getAnoFabricacao(){

		return anoFabricacao;
	}

	public void setAnoFabricacao(Short anoFabricacao){

		this.anoFabricacao = anoFabricacao;
	}

	public Short getIndicadorMacromedidor(){

		return indicadorMacromedidor;
	}

	public void setIndicadorMacromedidor(Short indicadorMacromedidor){

		this.indicadorMacromedidor = indicadorMacromedidor;
	}

	public Integer getIdHidrometroClasseMetrologica(){

		return idHidrometroClasseMetrologica;
	}

	public void setIdHidrometroClasseMetrologica(Integer idHidrometroClasseMetrologica){

		this.idHidrometroClasseMetrologica = idHidrometroClasseMetrologica;
	}

	public Integer getIdHidrometroMarca(){

		return idHidrometroMarca;
	}

	public void setIdHidrometroMarca(Integer idHidrometroMarca){

		this.idHidrometroMarca = idHidrometroMarca;
	}

	public Integer getIdHidrometroDiametro(){

		return idHidrometroDiametro;
	}

	public void setIdHidrometroDiametro(Integer idHidrometroDiametro){

		this.idHidrometroDiametro = idHidrometroDiametro;
	}

	public Integer getIdHidrometroCapacidade(){

		return idHidrometroCapacidade;
	}

	public void setIdHidrometroCapacidade(Integer idHidrometroCapacidade){

		this.idHidrometroCapacidade = idHidrometroCapacidade;
	}

	public Integer getIdHidrometroTipo(){

		return idHidrometroTipo;
	}

	public void setIdHidrometroTipo(Integer idHidrometroTipo){

		this.idHidrometroTipo = idHidrometroTipo;
	}

	public Integer getIdHidrometroSituacao(){

		return idHidrometroSituacao;
	}

	public void setIdHidrometroSituacao(Integer idHidrometroSituacao){

		this.idHidrometroSituacao = idHidrometroSituacao;
	}

	public Integer getIdHidrometroLocalArmazenagem(){

		return idHidrometroLocalArmazenagem;
	}

	public void setIdHidrometroLocalArmazenagem(Integer idHidrometroLocalArmazenagem){

		this.idHidrometroLocalArmazenagem = idHidrometroLocalArmazenagem;
	}

	public Integer getIdHidrometroTipoTurbina(){

		return idHidrometroTipoTurbina;
	}

	public void setIdHidrometroTipoTurbina(Integer idHidrometroTipoTurbina){

		this.idHidrometroTipoTurbina = idHidrometroTipoTurbina;
	}

	public Date getDataInstalacao(){

		return dataInstalacao;
	}

	public void setDataInstalacao(Date dataInstalacao){

		this.dataInstalacao = dataInstalacao;
	}

	public boolean isConsultarHistoricoInstalacao(){

		return consultarHistoricoInstalacao;
	}

	public void setConsultarHistoricoInstalacao(boolean consultarHistoricoInstalacao){

		this.consultarHistoricoInstalacao = consultarHistoricoInstalacao;
	}

	public boolean isConsultaSemLimites(){

		return consultaSemLimites;
	}

	public void setConsultaSemLimites(boolean consultaSemLimites){

		this.consultaSemLimites = consultaSemLimites;
	}

	public Integer getNumeroNotaFiscal(){

		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(Integer numeroNotaFiscal){

		this.numeroNotaFiscal = numeroNotaFiscal;
	}

}
