package gcom.seguranca.transacao;



public class AuditoriaServicosACobrarHelper {

	private String codigo;

	private String codigoOperacaoTransacao;

	private String data;

	private String hora;

	private String conteudoAtual;

	private String conteudoAnterior;

	private String descricaoCampo;


	public String getCodigo(){

		return codigo;
	}


	public void setCodigo(String codigo){

		this.codigo = codigo;
	}


	public String getCodigoOperacaoTransacao(){

		return codigoOperacaoTransacao;
	}


	public void setCodigoOperacaoTransacao(String codigoOperacaoTransacao){

		this.codigoOperacaoTransacao = codigoOperacaoTransacao;
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


	public String getDescricaoCampo(){

		return descricaoCampo;
	}


	public void setDescricaoCampo(String descricaoCampo){

		this.descricaoCampo = descricaoCampo;
	}


	public AuditoriaServicosACobrarHelper() {

	}


}
