
package gcom.gui.faturamento.conta.contamotivo;

import java.util.Date;

public class ExibirAtualizarMotivoRevisaoActionForm {

	private Integer id;

	private String descricaoMotivoRevisaoConta;

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
	 * @return the descricaoMotivoRevisaoConta
	 */
	public String getDescricaoMotivoRevisaoConta(){

		return descricaoMotivoRevisaoConta;
	}

	/**
	 * @param descricaoMotivoRevisaoConta
	 *            the descricaoMotivoRevisaoConta to set
	 */
	public void setDescricaoMotivoRevisaoConta(String descricaoMotivoRevisaoConta){

		this.descricaoMotivoRevisaoConta = descricaoMotivoRevisaoConta;
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
