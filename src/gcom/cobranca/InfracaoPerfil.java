
package gcom.cobranca;

import java.util.Date;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.imovel.Subcategoria;
import gcom.interceptor.ObjetoGcom;

public class InfracaoPerfil
				extends ObjetoGcom {

	private Integer id;

	private InfracaoTipo infracaoTipo;

	private Categoria categoria;

	private Subcategoria subcategoria;

	private ImovelPerfil imovelPerfil;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public InfracaoTipo getInfracaoTipo(){

		return infracaoTipo;
	}

	public void setInfracaoTipo(InfracaoTipo infracaoTipo){

		this.infracaoTipo = infracaoTipo;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public Subcategoria getSubcategoria(){

		return subcategoria;
	}

	public void setSubcategoria(Subcategoria subcategoria){

		this.subcategoria = subcategoria;
	}

	public ImovelPerfil getImovelPerfil(){

		return imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

}
