
package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

public class FiltrarRelatorioFechamentoCobrancaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String comando;

	private String periodoInicio;

	private String periodoFim;

	private String acao;

	private String empresa;

	private String tituloCobrancaAcaoAtividadeComando;

	private String idCobrancaAcaoAtividadeComando;

	private String idCobrancaAcaoAtividadeCronograma;

	private String descricaoGrupo;

	public String getDescricaoGrupo(){

		return descricaoGrupo;
	}

	public void setDescricaoGrupo(String descricaoGrupo){

		this.descricaoGrupo = descricaoGrupo;
	}

	public String getIdCobrancaAcaoAtividadeCronograma(){

		return idCobrancaAcaoAtividadeCronograma;
	}

	public void setIdCobrancaAcaoAtividadeCronograma(String idCobrancaAcaoAtividadeCronograma){

		this.idCobrancaAcaoAtividadeCronograma = idCobrancaAcaoAtividadeCronograma;
	}

	public String getTituloCobrancaAcaoAtividadeComando(){

		return tituloCobrancaAcaoAtividadeComando;
	}

	public void setTituloCobrancaAcaoAtividadeComando(String tituloCobrancaAcaoAtividadeComando){

		this.tituloCobrancaAcaoAtividadeComando = tituloCobrancaAcaoAtividadeComando;
	}

	public String getIdCobrancaAcaoAtividadeComando(){

		return idCobrancaAcaoAtividadeComando;
	}

	public void setIdCobrancaAcaoAtividadeComando(String idCobrancaAcaoAtividadeComando){

		this.idCobrancaAcaoAtividadeComando = idCobrancaAcaoAtividadeComando;
	}

	public String getComando(){

		return comando;
	}

	public void setComando(String comando){

		this.comando = comando;
	}

	public String getPeriodoInicio(){

		return periodoInicio;
	}

	public void setPeriodoInicio(String periodoInicio){

		this.periodoInicio = periodoInicio;
	}

	public String getPeriodoFim(){

		return periodoFim;
	}

	public void setPeriodoFim(String periodoFim){

		this.periodoFim = periodoFim;
	}

	public String getAcao(){

		return acao;
	}

	public void setAcao(String acao){

		this.acao = acao;
	}

	public String getEmpresa(){

		return empresa;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}
}
