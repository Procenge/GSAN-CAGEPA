
package gcom.seguranca.transacao;

public class AuditoriaAlteracoesClientesHelper {

	private String codigo;

	private String codigoOperacaoUsuario;

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

	public String getCodigoOperacaoUsuario(){

		return codigoOperacaoUsuario;
	}

	public void setCodigoOperacaoUsuario(String codigoOperacaoUsuario){

		this.codigoOperacaoUsuario = codigoOperacaoUsuario;
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

	public AuditoriaAlteracoesClientesHelper() {

	}

}
