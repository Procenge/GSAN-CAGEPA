package gcom.seguranca.transacao;

public class AuditoriaRetificacaoImplantacaoContasHelper {
	
	private String descricaoCampo;

	private String conteudoAtual;

	private String conteudoAnterior;

	private String referencia;

	private String motivo;

	private String codigo;

	private String data;

	private String hora;


	public AuditoriaRetificacaoImplantacaoContasHelper() {

	}


	public String getDescricaoCampo(){

		return descricaoCampo;
	}

	public void setDescricaoCampo(String descricaoCampo){

		this.descricaoCampo = descricaoCampo;
	}

	public String getConteudoAtual(){

		return conteudoAtual;
	}

	public void setConteudoAtual(String conteudoAtual){

		this.conteudoAtual = conteudoAtual;
	}

	public String getConteudoAnterior(){

		return conteudoAnterior;
	}

	public void setConteudoAnterior(String conteudoAnterior){

		this.conteudoAnterior = conteudoAnterior;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getMotivo(){

		return motivo;
	}

	public void setMotivo(String motivo){

		this.motivo = motivo;
	}

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getData(){

		return data;
	}

	public void setData(String data){

		this.data = data;
	}

	public String getHora(){

		return hora;
	}

	public void setHora(String hora){

		this.hora = hora;
	}

}
