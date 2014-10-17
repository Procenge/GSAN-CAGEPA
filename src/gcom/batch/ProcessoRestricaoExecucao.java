
package gcom.batch;

import java.io.Serializable;
import java.util.Date;

public class ProcessoRestricaoExecucao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Short indicadorUso;

	private Processo processoIniciar;

	private Processo processoExecucao;

	private Date ultimaAlteracao;

	public ProcessoRestricaoExecucao() {

	}

	public ProcessoRestricaoExecucao(Integer id) {

		this.id = id;
	}

	public ProcessoRestricaoExecucao(Integer id, Short indicadorUso, Processo processoIniciar, Processo processoExecucao,
										Date ultimaAlteracao) {

		this.id = id;
		this.indicadorUso = indicadorUso;
		this.processoIniciar = processoIniciar;
		this.processoExecucao = processoExecucao;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Processo getProcessoIniciar(){

		return processoIniciar;
	}

	public void setProcessoIniciar(Processo processoIniciar){

		this.processoIniciar = processoIniciar;
	}

	public Processo getProcessoExecucao(){

		return processoExecucao;
	}

	public void setProcessoExecucao(Processo processoExecucao){

		this.processoExecucao = processoExecucao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
