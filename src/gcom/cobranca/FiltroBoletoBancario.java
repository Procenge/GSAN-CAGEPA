
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroBoletoBancario
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroBoletoBancario object
	 */
	public FiltroBoletoBancario() {

	}

	/**
	 * Constructor for the FiltroBoletoBancario object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroBoletoBancario(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Description of the Field
	 */
	public final static String ARRECADADOR = "arrecadador";

	public final static String ARRECADADOR_ID = "arrecadador.id";

	public final static String IMOVEL = "imovel";

	public final static String CLIENTE = "cliente";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String DOCUMENTOCOBRANCA = "documentoCobranca";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String NUMERO_SEQUENCIAL = "numeroSequencial";

	public final static String SITUACAO = "boletoBancarioSituacao";

	public final static String NUMERO_BOLETO = "numeroBoletoCartaCobranca";

	public final static String PARCELAMENTO_ID = "parcelamento.id";

	public final static String PARCELAMENTO = "parcelamento";

	public final static String SITUACAO_ATUAL_ID = "boletoBancarioSituacao.id";

	public final static String MOTIVO_CANCELAMENTO = "boletoBancarioMotivoCancelamento";

	public final static String DOCUMENTO_TIPO = "documentoTipo";

	public final static String DOCUMENTOCOBRANCA_ID = "documentoCobranca.id";

	public final static String BOLETOBANCARIO_ID_ORIGINAL = "idOriginal";

	public final static String BOLETO_BANCARIO_LANCAMENTO_ENVIO = "boleto_bancario_lancmt_envio";

	public final static String COBRANCA_ACAO_ATIVIDADE_COMANDO_ID = "documentoCobranca.cobrancaAcaoAtividadeComando.id";
}
