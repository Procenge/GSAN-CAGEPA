
package gcom.cobranca;

public enum CobrancaAcaoAtividade {

	CRONOGRAMA("Cronograma"), COMANDO("Comando");

	private String msg;

	private CobrancaAcaoAtividade(String key) {

		this.msg = key;
	}

	@Override
	public String toString(){

		return msg;
	}

	public String getMsg(){

		return msg;
	}

	public void setMsg(String msg){

		this.msg = msg;
	}

}
