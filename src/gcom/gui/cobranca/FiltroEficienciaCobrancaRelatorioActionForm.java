
package gcom.gui.cobranca;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 */
public class FiltroEficienciaCobrancaRelatorioActionForm
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

	// ********************************************
	private String[] idGrupo;

	private String[] idSetor;

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

	public String[] getIdGrupo(){

		return idGrupo;
	}

	public void setIdGrupo(String[] idGrupo){

		this.idGrupo = idGrupo;
	}

	public String[] getIdSetor(){

		return idSetor;
	}

	public void setIdSetor(String[] idSetor){

		this.idSetor = idSetor;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		this.comando = null;
		this.periodoInicio = null;
		this.periodoFim = null;
		this.acao = null;
		this.empresa = null;
		this.tituloCobrancaAcaoAtividadeComando = null;
		this.idCobrancaAcaoAtividadeComando = null;
		this.idCobrancaAcaoAtividadeCronograma = null;
		this.descricaoGrupo = null;
		this.idGrupo = null;
		this.idSetor = null;
	}
}
