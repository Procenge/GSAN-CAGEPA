
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * Filtro da entidade BoletoBancarioOcorrencias
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class FiltroBoletoBancarioOcorrencias
				extends Filtro
				implements Serializable {

	public FiltroBoletoBancarioOcorrencias() {

	}

	public FiltroBoletoBancarioOcorrencias(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String BOLETO_BANCARIO_MOVIMENTACAO_ID = "boletoBancarioMovimentacao.id";

	public final static String BOLETO_BANCARIO_MOTIVO_OCORRENCIA = "boletoBancarioMotivoOcorrencia";

	public final static String BOLETO_BANCARIO_MOTIVO_OCORRENCIA_ID = "boletoBancarioMotivoOcorrencia.id";

	public final static String BOLETO_BANCARIO_MOTIVO_OCORRENCIA_DESCRICAO = "boletoBancarioMotivoOcorrencia.descricaoMotivoOcorrencia";

}
