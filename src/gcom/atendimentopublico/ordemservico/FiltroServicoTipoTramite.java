
package gcom.atendimentopublico.ordemservico;

import gcom.util.filtro.Filtro;

/**
 * Filtrar Serviço Tipo Trâmite
 * 
 * @author Hebert Falcão
 * @since 11/02/2012
 */
public class FiltroServicoTipoTramite
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public FiltroServicoTipoTramite() {

	}

	public FiltroServicoTipoTramite(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String SERVICO_TIPO = "servicoTipo";

	public final static String SERVICO_TIPO_ID = "servicoTipo.id";

	public final static String LOCALIDADE = "localidade";

	public final static String LOCALIDADE_ID = "localidade.id";

	public final static String SETOR_COMERCIAL = "setorComercial";

	public final static String SETOR_COMERCIAL_ID = "setorComercial.id";

	public final static String UNIDADE_ORGANIZACIONAL_ORIGEM = "unidadeOrganizacionalOrigem";

	public final static String UNIDADE_ORGANIZACIONAL_ORIGEM_ID = "unidadeOrganizacionalOrigem.id";

	public final static String UNIDADE_ORGANIZACIONAL_DESTINO = "unidadeOrganizacionalDestino";

	public final static String UNIDADE_ORGANIZACIONAL_DESTINO_ID = "unidadeOrganizacionalDestino.id";

}
