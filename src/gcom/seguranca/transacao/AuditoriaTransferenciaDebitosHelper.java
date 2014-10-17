
package gcom.seguranca.transacao;


public class AuditoriaTransferenciaDebitosHelper {

	// Linha 1
	private Integer idFuncionario; // MPCCDFUN

	private String dataT; // MPCDTMPC

	private Integer dataInicial; // MPCDTMPC

	private Integer dataFinal; // MPCDTMPC

	private String hora; // MPCHRMPC

	private Integer referenciaInicial; // MPCAMINI

	private Integer referenciaFinal; // MPCAMFIN

	private Integer usuarioOrigem; // MPCNNMATUSU

	private Integer usuarioDestino; // MPCNNMATUSUD

	private String totalPrestacao; // MPCNNPREST

	// Linha 2
	private String valorEntrada; // MPCVLENTR

	private String valorPrestacao; // MPCVLPREST

	private String valorDebitoHistorico;// MPCVLDEBHIST

	private String valorDebitoAtual;// MPCVLDEBCORR

	private String valorSacadoIncluso;// MPCVLTOTSACINCL

	private String valorParcelado;// MPCVLPARCEL

	private String codigo;

	public AuditoriaTransferenciaDebitosHelper() {

	}


	public Integer getIdFuncionario(){

		return idFuncionario;
	}


	public void setIdFuncionario(Integer idFuncionario){

		this.idFuncionario = idFuncionario;
	}


	public String getDataT(){

		return dataT;
	}


	public void setDataT(String dataT){

		this.dataT = dataT;
	}


	public Integer getDataInicial(){

		return dataInicial;
	}


	public void setDataInicial(Integer dataInicial){

		this.dataInicial = dataInicial;
	}


	public Integer getDataFinal(){

		return dataFinal;
	}


	public void setDataFinal(Integer dataFinal){

		this.dataFinal = dataFinal;
	}


	public String getHora(){

		return hora;
	}


	public void setHora(String hora){

		this.hora = hora;
	}


	public Integer getReferenciaInicial(){

		return referenciaInicial;
	}


	public void setReferenciaInicial(Integer referenciaInicial){

		this.referenciaInicial = referenciaInicial;
	}


	public Integer getReferenciaFinal(){

		return referenciaFinal;
	}


	public void setReferenciaFinal(Integer referenciaFinal){

		this.referenciaFinal = referenciaFinal;
	}


	public Integer getUsuarioOrigem(){

		return usuarioOrigem;
	}


	public void setUsuarioOrigem(Integer usuarioOrigem){

		this.usuarioOrigem = usuarioOrigem;
	}


	public Integer getUsuarioDestino(){

		return usuarioDestino;
	}


	public void setUsuarioDestino(Integer usuarioDestino){

		this.usuarioDestino = usuarioDestino;
	}

	public String getTotalPrestacao(){

		return totalPrestacao;
	}

	public void setTotalPrestacao(String totalPrestacao){

		this.totalPrestacao = totalPrestacao;
	}

	public String getValorEntrada(){

		return valorEntrada;
	}

	public void setValorEntrada(String valorEntrada){

		this.valorEntrada = valorEntrada;
	}

	public String getValorPrestacao(){

		return valorPrestacao;
	}

	public void setValorPrestacao(String valorPrestacao){

		this.valorPrestacao = valorPrestacao;
	}

	public String getValorDebitoHistorico(){

		return valorDebitoHistorico;
	}

	public void setValorDebitoHistorico(String valorDebitoHistorico){

		this.valorDebitoHistorico = valorDebitoHistorico;
	}

	public String getValorDebitoAtual(){

		return valorDebitoAtual;
	}

	public void setValorDebitoAtual(String valorDebitoAtual){

		this.valorDebitoAtual = valorDebitoAtual;
	}

	public String getValorSacadoIncluso(){

		return valorSacadoIncluso;
	}

	public void setValorSacadoIncluso(String valorSacadoIncluso){

		this.valorSacadoIncluso = valorSacadoIncluso;
	}

	public String getValorParcelado(){

		return valorParcelado;
	}

	public void setValorParcelado(String valorParcelado){

		this.valorParcelado = valorParcelado;
	}


	public String getCodigo(){

		return codigo;
	}


	public void setCodigo(String codigo){

		this.codigo = codigo;
	}



}
