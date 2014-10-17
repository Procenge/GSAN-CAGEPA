
package gcom.cobranca.bean;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaCriterio;
import gcom.cobranca.CobrancaGrupo;
import gcom.seguranca.acesso.usuario.Usuario;

import java.io.Serializable;
import java.util.Date;

public class ParametrosHelper
				implements Serializable {

	private CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma;

	private CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando;

	private Date dataAtual;

	private CobrancaAcao acaoCobranca;

	private CobrancaGrupo grupoCobranca;

	private CobrancaCriterio criterioCobranca;

	private Integer faturamentoGrupoCronogramaMensalId;

	private Usuario usuario;

	public CobrancaAcaoAtividadeCronograma getCobrancaAcaoAtividadeCronograma(){

		return cobrancaAcaoAtividadeCronograma;
	}

	public void setCobrancaAcaoAtividadeCronograma(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma){

		this.cobrancaAcaoAtividadeCronograma = cobrancaAcaoAtividadeCronograma;
	}

	public CobrancaAcaoAtividadeComando getCobrancaAcaoAtividadeComando(){

		return cobrancaAcaoAtividadeComando;
	}

	public void setCobrancaAcaoAtividadeComando(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando){

		this.cobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando;
	}

	public Date getDataAtual(){

		return dataAtual;
	}

	public void setDataAtual(Date dataAtual){

		this.dataAtual = dataAtual;
	}

	public CobrancaAcao getAcaoCobranca(){

		return acaoCobranca;
	}

	public void setAcaoCobranca(CobrancaAcao acaoCobranca){

		this.acaoCobranca = acaoCobranca;
	}

	public CobrancaGrupo getGrupoCobranca(){

		return grupoCobranca;
	}

	public void setGrupoCobranca(CobrancaGrupo grupoCobranca){

		this.grupoCobranca = grupoCobranca;
	}

	public CobrancaCriterio getCriterioCobranca(){

		return criterioCobranca;
	}

	public void setCriterioCobranca(CobrancaCriterio criterioCobranca){

		this.criterioCobranca = criterioCobranca;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public Integer getFaturamentoGrupoCronogramaMensalId(){

		return faturamentoGrupoCronogramaMensalId;
	}

	public void setFaturamentoGrupoCronogramaMensalId(Integer faturamentoGrupoCronogramaMensalId){

		this.faturamentoGrupoCronogramaMensalId = faturamentoGrupoCronogramaMensalId;
	}

}
