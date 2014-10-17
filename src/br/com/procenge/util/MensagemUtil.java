
package br.com.procenge.util;

import java.util.ResourceBundle;

/**
 * @autor gilberto
 */
public class MensagemUtil {

	/**
	 * Método responsável por obter a mensagem do arquivo de propriedades.
	 * 
	 * @autor gilberto
	 * @param rb
	 *            O Resource Bundle
	 * @param chave
	 *            A chave
	 * @param argumentos
	 *            Os argumentos da mensagem
	 * @return String A Mensagem
	 */
	public static String obterMensagem(ResourceBundle rb, String chave, Object argumentos[]){

		String mensagem = obterMensagem(rb, chave);
		for(int i = 0; i < argumentos.length; i++){
			mensagem = substituirArgumentosDaMensagem(mensagem, i, argumentos[i]);
		}
		return mensagem;
	}

	/**
	 * Método responsável por obter a mensagem do arquivo de propriedades.
	 * 
	 * @autor gilberto
	 * @param rb
	 *            O Resource Bundle
	 * @param chave
	 *            A chave
	 * @param argumento
	 *            Os argumentos da mensagem
	 * @return String A Mensagem
	 */
	public static String obterMensagem(ResourceBundle rb, String chave, Object argumento){

		return obterMensagem(rb, chave, new Object[] {argumento});
	}

	/**
	 * Método responsável por obter a mensagem do arquivo de propriedades.
	 * 
	 * @autor gilberto
	 * @param rb
	 *            O Resource Bundle
	 * @param chave
	 *            A chave
	 * @return String A mensagem
	 */
	public static String obterMensagem(ResourceBundle rb, String chave){

		return rb.getString(chave);
	}

	/**
	 * Método responsável por substituir os argumentos da mensagem.
	 * 
	 * @autor gilberto
	 * @param mensagem
	 *            O texto
	 * @param numeroDeArgumentos
	 *            O número de argumentos
	 * @param valorDoArgumento
	 *            O valor do argumento que será subtituído
	 * @return String A Mensagem
	 */
	private static String substituirArgumentosDaMensagem(String mensagem, int numeroDeArgumentos, Object valorDoArgumento){

		StringBuffer sb = new StringBuffer(mensagem.length() + 10);
		String chave = "{" + numeroDeArgumentos + "}";
		int i = mensagem.indexOf(chave);
		if(i >= 0){
			sb.append(mensagem.substring(0, i));
			sb.append(valorDoArgumento);
			sb.append(mensagem.substring(i + chave.length()));
		}else{
			sb.append(mensagem);
		}
		return sb.toString();
	}
}