/**
 * 
 */

package gcom.agenciavirtual.cadastro.geral;

/**
 * @author mgrb
 */
public class ObjetoChaveDescricaoJSON {

	public ObjetoChaveDescricaoJSON(Integer id, String descricao) {

		super();
		this.id = id;
		this.descricao = descricao;
	}

	private Integer id;

	private String descricao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

}
