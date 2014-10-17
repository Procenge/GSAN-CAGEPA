
package gcom.gui.cobranca;

import gcom.cobranca.ResolucaoDiretoriaLayout;

import java.io.Serializable;
import java.util.Date;

public class ResolucaoDiretoriaHelper
				implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private String numero;

	private String assunto;

	private Date dataInicio;

	private Date dataFim;

	private ResolucaoDiretoriaLayout resolucaoDiretoriaLayout;

	private String grupo;

	public ResolucaoDiretoriaHelper() {

	}

	public ResolucaoDiretoriaHelper clone(){

		try{
			return (ResolucaoDiretoriaHelper) super.clone();
		}catch(CloneNotSupportedException e){
			return null;
		}
	}

	public String getNumero(){

		return numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	public String getAssunto(){

		return assunto;
	}

	public void setAssunto(String assunto){

		this.assunto = assunto;
	}

	public Date getDataInicio(){

		return dataInicio;
	}

	public void setDataInicio(Date dataInicio){

		this.dataInicio = dataInicio;
	}

	public Date getDataFim(){

		return dataFim;
	}

	public void setDataFim(Date dataFim){

		this.dataFim = dataFim;
	}

	public void setGrupo(String grupo){

		this.grupo = grupo;
	}

	public ResolucaoDiretoriaLayout getResolucaoDiretoriaLayout(){

		return resolucaoDiretoriaLayout;
	}

	public void setResolucaoDiretoriaLayout(ResolucaoDiretoriaLayout resolucaoDiretoriaLayout){

		this.resolucaoDiretoriaLayout = resolucaoDiretoriaLayout;
	}

	public String getGrupo(){

		return grupo;
	}

}
