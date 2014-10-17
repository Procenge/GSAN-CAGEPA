
package gcom.faturamento.conta;

import gcom.interceptor.ObjetoGcom;

import java.util.Date;

/**
 * Quitação Mensagem
 * 
 * @author Hebert Falcão
 * @date 02/05/2011
 */
public class QuitacaoMensagem
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	public final static Integer MENSAGEM_01 = Integer.valueOf(1);

	public final static Integer MENSAGEM_02 = Integer.valueOf(2);

	public final static Integer MENSAGEM_03 = Integer.valueOf(3);

	private Integer id;

	private String descricaoQuitacaoMensagem01;

	private String descricaoQuitacaoMensagem02;

	private String descricaoQuitacaoMensagem03;

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
	 * @return the descricaoQuitacaoMensagem01
	 */
	public String getDescricaoQuitacaoMensagem01(){

		return descricaoQuitacaoMensagem01;
	}

	/**
	 * @param descricaoQuitacaoMensagem01
	 *            the descricaoQuitacaoMensagem01 to set
	 */
	public void setDescricaoQuitacaoMensagem01(String descricaoQuitacaoMensagem01){

		this.descricaoQuitacaoMensagem01 = descricaoQuitacaoMensagem01;
	}

	/**
	 * @return the descricaoQuitacaoMensagem02
	 */
	public String getDescricaoQuitacaoMensagem02(){

		return descricaoQuitacaoMensagem02;
	}

	/**
	 * @param descricaoQuitacaoMensagem02
	 *            the descricaoQuitacaoMensagem02 to set
	 */
	public void setDescricaoQuitacaoMensagem02(String descricaoQuitacaoMensagem02){

		this.descricaoQuitacaoMensagem02 = descricaoQuitacaoMensagem02;
	}

	/**
	 * @return the descricaoQuitacaoMensagem03
	 */
	public String getDescricaoQuitacaoMensagem03(){

		return descricaoQuitacaoMensagem03;
	}

	/**
	 * @param descricaoQuitacaoMensagem03
	 *            the descricaoQuitacaoMensagem03 to set
	 */
	public void setDescricaoQuitacaoMensagem03(String descricaoQuitacaoMensagem03){

		this.descricaoQuitacaoMensagem03 = descricaoQuitacaoMensagem03;
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

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

}
