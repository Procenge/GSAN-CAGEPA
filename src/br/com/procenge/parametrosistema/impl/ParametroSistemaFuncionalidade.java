/**
 * 
 */

package br.com.procenge.parametrosistema.impl;

import gcom.seguranca.acesso.Funcionalidade;
import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * @author fmarconi
 */
public class ParametroSistemaFuncionalidade {

	private Long id;

	private String valor;

	private Funcionalidade funcionalidade;

	private ParametroSistema parametroSistema;

	public void setParametroSistema(ParametroSistema parametroSistema){

		this.parametroSistema = parametroSistema;
	}

	public ParametroSistema getParametroSistema(){

		return parametroSistema;
	}

	public void setId(Long id){

		this.id = id;
	}

	public Long getId(){

		return id;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public String getValor(){

		return valor;
	}

	public void setFuncionalidade(Funcionalidade funcionalidade){

		this.funcionalidade = funcionalidade;
	}

	public Funcionalidade getFuncionalidade(){

		return funcionalidade;
	}

}
