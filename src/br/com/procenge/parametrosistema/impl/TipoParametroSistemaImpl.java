/**
 * 
 */

package br.com.procenge.parametrosistema.impl;

import br.com.procenge.parametrosistema.api.TipoParametroSistema;

/**
 * @author gmatos
 */
public class TipoParametroSistemaImpl
				implements TipoParametroSistema {

	private int codigo;

	private String descricao;

	public TipoParametroSistemaImpl() {

		super();
	}

	public TipoParametroSistemaImpl(int codigo) {

		super();
		this.setCodigo(codigo);
	}

	public int getCodigo(){

		return codigo;
	}

	public void setCodigo(int codigo){

		this.codigo = codigo;

		if(codigo == TIPO_DINAMICO){
			this.descricao = DESCRICAO_TIPO_DINAMICO;
		}else if(codigo == TIPO_ESTATICO){
			this.descricao = DESCRICAO_TIPO_ESTATICO;
		}

	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

}
