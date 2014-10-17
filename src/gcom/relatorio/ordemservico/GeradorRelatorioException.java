/**
 * 
 */

package gcom.relatorio.ordemservico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author gmatos
 */
public class GeradorRelatorioException
				extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ArrayList<String> parametrosMensagem = new ArrayList<String>();

	private String parametroMensagem;

	/**
	 * Construtor da classe GeradorRelatorioOrdemServicoException
	 * 
	 * @param mensagem
	 *            Chave do erro que aconteceu no controlador(mensagem obtida num
	 *            arquivo de properties)
	 * @param excecaoCausa
	 *            Exceção que originou o problema
	 */
	public GeradorRelatorioException(String mensagem, Exception excecaoCausa) {

		super(mensagem, excecaoCausa);

	}

	/**
	 * Construtor da classe GeradorRelatorioOrdemServicoException
	 * 
	 * @param mensagem
	 *            Descrição do parâmetro
	 */
	public GeradorRelatorioException(String mensagem) {

		this(mensagem, null);
	}

	/**
	 * Construtor da classe GeradorRelatorioOrdemServicoException
	 * 
	 * @param mensagem
	 *            Chave do erro que aconteceu no controlador(mensagem obtida num
	 *            arquivo de properties)
	 * @param excecaoCausa
	 *            Exceção que originou o problema
	 * @param parametroMensagem
	 *            Descrição do parâmetro
	 */
	public GeradorRelatorioException(String mensagem, Exception excecaoCausa, String... parametroMensagem) {

		super(mensagem, excecaoCausa);
		parametrosMensagem.addAll(Arrays.asList(parametroMensagem));

	}

	public GeradorRelatorioException(String mensagem, Exception excecaoCausa, String parametroMensagem) {

		super(mensagem, excecaoCausa);
		this.parametroMensagem = parametroMensagem;

	}

	public List<String> getParametroMensagem(){

		ArrayList<String> list = new ArrayList<String>();
		list.addAll(parametrosMensagem);
		if(parametroMensagem != null && parametroMensagem.length() > 0){
			list.add(parametroMensagem);
		}
		return list;
	}

	public void setParametroMensagem(String... parametroMensagem){

		parametrosMensagem.addAll(Arrays.asList(parametroMensagem));
	}

}
