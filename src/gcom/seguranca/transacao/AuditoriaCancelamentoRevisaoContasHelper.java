package gcom.seguranca.transacao;


public class AuditoriaCancelamentoRevisaoContasHelper {

	private String mctcdoper;

	private Integer mctiamref;

	private String mcticdmotivo;

	private Integer mctcodigo;

	private String mctdata;

	private String mcthora;

	public AuditoriaCancelamentoRevisaoContasHelper() {

	}


	public String getMctcdoper(){

		return mctcdoper;
	}


	public void setMctcdoper(String mctcdoper){

		this.mctcdoper = mctcdoper;
	}


	public Integer getMctiamref(){

		return mctiamref;
	}


	public void setMctiamref(Integer mctiamref){

		this.mctiamref = mctiamref;
	}


	public String getMcticdmotivo(){

		return mcticdmotivo;
	}


	public void setMcticdmotivo(String mcticdmotivo){

		this.mcticdmotivo = mcticdmotivo;
	}

	public Integer getMctcodigo(){

		return mctcodigo;
	}


	public void setMctcodigo(Integer mctcodigo){

		this.mctcodigo = mctcodigo;
	}


	public String getMctdata(){

		return mctdata;
	}


	public void setMctdata(String mctdata){

		this.mctdata = mctdata;
	}


	public String getMcthora(){

		return mcthora;
	}


	public void setMcthora(String mcthora){

		this.mcthora = mcthora;
	}

}
