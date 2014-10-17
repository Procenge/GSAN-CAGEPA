/**
 * 
 */

package gcom.cobranca;

import gcom.util.filtro.Filtro;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FiltroCobrancaAcaoAtividadeImovel
				extends Filtro {

	/**
	 * 
	 */
	public FiltroCobrancaAcaoAtividadeImovel() {

		// TODO Auto-generated constructor stub
	}

	public FiltroCobrancaAcaoAtividadeImovel(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String COBRANCA_ACAO_ATIVIDADE_COMANDO_ID = "cobrancaAcaoAtividadeComando.id";

	public final static String COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID = "cobrancaAcaoAtividadeCronograma.id";

	public final static String IMOVEL_ID = "imovel.id";

}
