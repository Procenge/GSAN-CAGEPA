
package gcom.atendimentopublico.registroatendimento;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

public class FiltroSolicitacaoTipoEspecificacaoMensagem
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroSolicitacaoTipoEspecificacaoMensagem(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Construtor da classe FiltroQuadra
	 */
	public FiltroSolicitacaoTipoEspecificacaoMensagem() {

	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	/**
	 * Descrição do Sistema de Esgoto
	 */
	public final static String DESCRICAO = "descricao";

	/**
	 * Description of the Field
	 */
	public final static String INDICADOR_USO = "indicadorUso";

}
