
package gcom.cobranca;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroBoletoBancarioMotivoOcorrencia
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroBoletoBancarioMotivoOcorrencia() {

	}

	public FiltroBoletoBancarioMotivoOcorrencia(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String DESCRICAO = "descricaoMotivoOcorrencia";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String CODIGO_MOTIVO_OCORRENCIA = "codigoMotivoOcorrencia";

	public final static String BOLETO_BANCARIO_TIPO_OCORRENCIA = "boletoBancarioTipoOcorrencia";

	public final static String BOLETO_BANCARIO_TIPO_OCORRENCIA_ID = "boletoBancarioTipoOcorrencia.id";

}
