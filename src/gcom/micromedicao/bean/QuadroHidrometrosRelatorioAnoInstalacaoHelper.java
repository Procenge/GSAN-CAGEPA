package gcom.micromedicao.bean;


/**
 * @author Ado Rocha
 * @date 12/2013
 */
public class QuadroHidrometrosRelatorioAnoInstalacaoHelper {


	private Integer idlocalidade;

	private String localidade;

	private Integer idMarca;

	private String marca;

	private Integer ano;

	private Integer quantidade;

	public QuadroHidrometrosRelatorioAnoInstalacaoHelper() {

	}


	public Integer getIdLocalidade(){

		return idlocalidade;
	}


	public void setIdLocalidade(Integer idlocalidade){

		this.idlocalidade = idlocalidade;
	}


	public String getLocalidade(){

		return localidade;
	}


	public void setLocalidade(String descricaoLocalidade){

		this.localidade = descricaoLocalidade;
	}

	public Integer getIdMarca(){

		return idMarca;
	}

	public void setIdMarca(Integer idMarca){

		this.idMarca = idMarca;
	}

	public String getMarca(){

		return marca;
	}

	public void setMarca(String marca){

		this.marca = marca;
	}

	public Integer getAno(){

		return ano;
	}

	public void setAno(Integer ano){

		this.ano = ano;
	}


	public Integer getQuantidade(){

		return quantidade;
	}


	public void setQuantidade(Integer quantidade){

		this.quantidade = quantidade;
	}

}
