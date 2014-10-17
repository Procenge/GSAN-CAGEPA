
package gcom.faturamento;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * @author Hugo Lima
 */
public class FiltroFaturamentoGrupoRevisao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroFaturamentoGrupoRevisao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public FiltroFaturamentoGrupoRevisao() {

	}

	public final static String ID = "id";

	public final static String ANO_MES_REFERENCIA_FATURAMENTO_INICIAL = "anoMesReferenciaFaturamentoInicial";

	public final static String ANO_MES_REFERENCIA_FATURAMENTO_FINAL = "anoMesReferenciaFaturamentoFinal";

	public final static String FATURAMENTO_GRUPO = "faturamentoGrupo";

	public final static String FATURAMENTO_GRUPO_ID = "faturamentoGrupo.id";

	public final static String CONTA_MOTIVO_REVISAO = "contaMotivoRevisao";

	public final static String CONTA_MOTIVO_REVISAO_ID = "contaMotivoRevisao.id";

}