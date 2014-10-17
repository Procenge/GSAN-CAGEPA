
package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroOrdemServicoFotoOcorrencia
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public FiltroOrdemServicoFotoOcorrencia() {

	}

	public FiltroOrdemServicoFotoOcorrencia(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID_OS = "idOrdemServico";

	public final static String ID_OS_PROGRAMACAO = "idOrdemServicoProgramacao";

	public final static String ID_SEQUENCIA = "numeroSequenciaFoto";

	public final static String FOTO = "foto";
}
