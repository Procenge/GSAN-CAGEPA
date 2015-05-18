
package gcom.atendimentopublico.registroatendimento;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * [UCXXXX] TRAMITE POR ESPECIFICACAO
 * 
 * @author Ailton Sousa
 * @date 28/03/2011
 */
public class FiltroTramiteEspecificacao
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the Filtro object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroTramiteEspecificacao(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroTramiteEspecificacao
	 */
	public FiltroTramiteEspecificacao() {

	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	public final static String SOLICITACAO_TIPO = "solicitacaoTipoEspecificacao.solicitacaoTipo";

	public final static String SOLICITACAO_TIPO_ID = "solicitacaoTipoEspecificacao.solicitacaoTipo.id";

	public final static String SOLICITACAO_TIPO_ESPECIFICACAO = "solicitacaoTipoEspecificacao";

	public final static String SOLICITACAO_TIPO_ESPECIFICACAO_ID = "solicitacaoTipoEspecificacao.id";

	public final static String LOCALIDADE = "localidade";

	public final static String LOCALIDADE_ID = "localidade.id";

	public final static String SETOR_COMERCIAL = "setorComercial";

	public final static String SETOR_COMERCIAL_ID = "setorComercial.id";

	public final static String SETOR_COMERCIAL_CODIGO = "setorComercial.codigo";

	public final static String BAIRRO = "bairro";

	public final static String MUNICIPIO = "bairro.municipio";

	public final static String BAIRRO_ID = "bairro.id";

	public final static String BAIRRO_CODIGO = "bairro.codigo";


	public final static String UNID_ORGANIZACIONAL_ORIGEM_ID = "unidadeOrganizacionalOrigem.id";

	public final static String UNID_ORGANIZACIONAL_ORIGEM = "unidadeOrganizacionalOrigem";

	public final static String UNID_ORGANIZACIONAL_DESTINO_ID = "unidadeOrganizacionalDestino.id";

	public final static String UNID_ORGANIZACIONAL_DESTINO = "unidadeOrganizacionalDestino";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String INDICADOR_PRIMEIRO_TRAMITE = "indicadorPrimeiroTramite";
}
