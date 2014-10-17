/**
 * 
 */

package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FiltroImovelCobrancaMotivoRetirada
				extends Filtro {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public FiltroImovelCobrancaMotivoRetirada() {

	}

	/**
	 * Constructor for the FiltroImovelAguaParaTodos object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroImovelCobrancaMotivoRetirada(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}


	public final static String ID = "id";

	public final static String DESCRICAO = "descricao";

	public final static String DESCRICAO_ABREVIADA = "descricaoAbreviada";

	public final static String INDICADOR_SISTEMA = "indicadorSistema";

	public final static String CODIGO_CONSTANTE = "codigoConstante";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String ULTIMA_ALTERACAO = "ultimaAlteracao";

	public final static String COBRANCA_DEBITO_SITUACAO_ID = "cobrancaDebitoSituacao.id";

}
