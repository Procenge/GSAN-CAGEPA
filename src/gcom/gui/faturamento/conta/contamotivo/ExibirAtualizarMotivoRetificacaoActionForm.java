
package gcom.gui.faturamento.conta.contamotivo;

import java.util.Date;

public class ExibirAtualizarMotivoRetificacaoActionForm {

	private Integer id;

	private String descricaoMotivoRetificacaoConta;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the descricaoMotivoRetificacaoConta
	 */
	public String getDescricaoMotivoRetificacaoConta(){

		return descricaoMotivoRetificacaoConta;
	}

	/**
	 * @param descricaoMotivoRetificacaoConta
	 *            the descricaoMotivoRetificacaoConta to set
	 */
	public void setDescricaoMotivoRetificacaoConta(String descricaoMotivoRetificacaoConta){

		this.descricaoMotivoRetificacaoConta = descricaoMotivoRetificacaoConta;
	}

	/**
	 * @return the indicadorUso
	 */
	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
