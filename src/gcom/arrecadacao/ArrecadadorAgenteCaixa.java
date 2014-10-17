package gcom.arrecadacao;

import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.Filtro;

import java.util.Date;


public class ArrecadadorAgenteCaixa
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Arrecadador arrecadador;

	private Usuario usuario;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Arrecadador getArrecadador(){

		return arrecadador;
	}

	public void setArrecadador(Arrecadador arrecadador){

		this.arrecadador = arrecadador;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;

	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;

	}

	public Filtro retornaFiltro(){


		return null;

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;

	}

}
