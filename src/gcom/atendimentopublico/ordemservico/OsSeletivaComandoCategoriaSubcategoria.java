package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

/** @author vsantos */
public class OsSeletivaComandoCategoriaSubcategoria
				extends ObjetoTransacao {

	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private Categoria categoria;

	private Subcategoria subCategoria;

	private Date ultimaAlteracao;

	/** Construtor padrão */
	public OsSeletivaComandoCategoriaSubcategoria() {

	}

	public OsSeletivaComandoCategoriaSubcategoria(Integer id, OsSeletivaComando osSeletivaComando, Categoria categoria, Subcategoria subCategoria,
									Date ultimaAlteracao) {

		this.id = id;
		this.osSeletivaComando = osSeletivaComando;
		this.categoria = categoria;
		this.subCategoria = subCategoria;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public OsSeletivaComandoCategoriaSubcategoria(Integer id, OsSeletivaComando osSeletivaComando, Categoria categoria, Date ultimaAlteracao) {

		this.id = id;
		this.osSeletivaComando = osSeletivaComando;
		this.categoria = categoria;
		this.ultimaAlteracao = ultimaAlteracao;
	}
	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public OsSeletivaComando getOsSeletivaComando(){

		return osSeletivaComando;
	}

	public void setOsSeletivaComando(OsSeletivaComando osSeletivaComando){

		this.osSeletivaComando = osSeletivaComando;
	}

	public Categoria getCategoria(){

		return categoria;
	}

	public void setCategoria(Categoria categoria){

		this.categoria = categoria;
	}

	public Subcategoria getSubCategoria(){

		return subCategoria;
	}

	public void setSubCategoria(Subcategoria subCategoria){

		this.subCategoria = subCategoria;
	}

	@Override
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}
	
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof OsSeletivaComandoCategoriaSubcategoria)) return false;
		final OsSeletivaComandoCategoriaSubcategoria other = (OsSeletivaComandoCategoriaSubcategoria) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

}
