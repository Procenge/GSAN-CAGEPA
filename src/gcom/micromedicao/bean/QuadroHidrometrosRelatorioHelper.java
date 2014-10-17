package gcom.micromedicao.bean;


/**
 * @author Ado Rocha
 * @date 12/2013
 */
public class QuadroHidrometrosRelatorioHelper {


	private Integer idlocalidade;

	private String descricaoLocalidade;

	private Integer idGerencia;

	private String descricaoGerencia;

	private String diametro;

	private String capacidade;

	private String marca;

	private Integer quantidade;

	public QuadroHidrometrosRelatorioHelper() {

	}

	public Integer getIdlocalidade(){

		return idlocalidade;
	}

	public void setIdlocalidade(Integer idlocalidade){

		this.idlocalidade = idlocalidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public Integer getIdGerencia(){

		return idGerencia;
	}

	public void setIdGerencia(Integer idGerencia){

		this.idGerencia = idGerencia;
	}

	public String getDescricaoGerencia(){

		return descricaoGerencia;
	}

	public void setDescricaoGerencia(String descricaoGerencia){

		this.descricaoGerencia = descricaoGerencia;
	}


	public String getDiametro(){

		return diametro;
	}


	public void setDiametro(String diametro){

		this.diametro = diametro;
	}

	public String getCapacidade(){

		return capacidade;
	}

	public void setCapacidade(String capacidade){

		this.capacidade = capacidade;
	}


	public String getMarca(){

		return marca;
	}


	public void setMarca(String marca){

		this.marca = marca;
	}

	public Integer getQuantidade(){

		return quantidade;
	}

	public void setQuantidade(Integer quantidade){

		this.quantidade = quantidade;
	}

}
