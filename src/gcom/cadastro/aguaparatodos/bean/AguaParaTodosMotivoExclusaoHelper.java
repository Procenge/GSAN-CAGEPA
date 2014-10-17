/**
 * 
 */

package gcom.cadastro.aguaparatodos.bean;

import java.math.BigDecimal;

/**
 * @author Luciano Galvão
 */
public class AguaParaTodosMotivoExclusaoHelper {

	private BigDecimal id;

	private String descricao;

	public AguaParaTodosMotivoExclusaoHelper(BigDecimal id, String descricao) {

		this.id = id;
		this.descricao = descricao;
	}

	public BigDecimal getId(){

		return id;
	}

	public void setId(BigDecimal id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

}
