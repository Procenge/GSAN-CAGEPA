
package gcom.agenciavirtual.cadastro.geografico;


public class MunicipioJSONHelper {

	private Integer id;

	private String descricao;

	public MunicipioJSONHelper(Integer id, String descricao) {

		this.id = id;
		this.descricao = descricao;
	}

	public MunicipioJSONHelper() {

	}

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
