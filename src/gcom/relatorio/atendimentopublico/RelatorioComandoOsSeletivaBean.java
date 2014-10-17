package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

public class RelatorioComandoOsSeletivaBean
				implements RelatorioBean {

	private String titulo;

	private String empresa;

	private String servicoTipoId;

	private String servicoTipo;

	private String dataHoraComando;

	private String dataHoraRealizacao;

	private String qtdeOsGerada;

	private String qtdeOsPendente;

	private String qtdeOsCancelada;

	private String qtdeOsExecutada;

	public RelatorioComandoOsSeletivaBean(String titulo, String empresa, String servicoTipoId, String servicoTipo, String dataHoraComando,
											String dataHoraRealizacao, String qtdeOsGerada, String qtdeOsPendente, String qtdeOsCancelada,
											String qtdeOsExecutada) {

		super();
		this.titulo = titulo;
		this.empresa = empresa;
		this.servicoTipoId = servicoTipoId;
		this.servicoTipo = servicoTipo;
		this.dataHoraComando = dataHoraComando;
		this.dataHoraRealizacao = dataHoraRealizacao;
		this.qtdeOsGerada = qtdeOsGerada;
		this.qtdeOsPendente = qtdeOsPendente;
		this.qtdeOsCancelada = qtdeOsCancelada;
		this.qtdeOsExecutada = qtdeOsExecutada;
	}

	public String getTitulo(){

		return titulo;
	}

	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	public String getEmpresa(){

		return empresa;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public String getServicoTipoId(){

		return servicoTipoId;
	}

	public void setServicoTipoId(String servicoTipoId){

		this.servicoTipoId = servicoTipoId;
	}

	public String getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(String servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String getDataHoraComando(){

		return dataHoraComando;
	}

	public void setDataHoraComando(String dataHoraComando){

		this.dataHoraComando = dataHoraComando;
	}

	public String getDataHoraRealizacao(){

		return dataHoraRealizacao;
	}

	public void setDataHoraRealizacao(String dataHoraRealizacao){

		this.dataHoraRealizacao = dataHoraRealizacao;
	}

	public String getQtdeOsGerada(){

		return qtdeOsGerada;
	}

	public void setQtdeOsGerada(String qtdeOsGerada){

		this.qtdeOsGerada = qtdeOsGerada;
	}

	public String getQtdeOsPendente(){

		return qtdeOsPendente;
	}

	public void setQtdeOsPendente(String qtdeOsPendente){

		this.qtdeOsPendente = qtdeOsPendente;
	}

	public String getQtdeOsCancelada(){

		return qtdeOsCancelada;
	}

	public void setQtdeOsCancelada(String qtdeOsCancelada){

		this.qtdeOsCancelada = qtdeOsCancelada;
	}

	public String getQtdeOsExecutada(){

		return qtdeOsExecutada;
	}

	public void setQtdeOsExecutada(String qtdeOsExecutada){

		this.qtdeOsExecutada = qtdeOsExecutada;
	}

}
