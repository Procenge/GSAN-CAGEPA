/**
 * 
 */

package gcom.atendimentopublico.ordemservico;

/**
 * @author eduardo henrique
 * @date 16/05/2009
 *       Valores possíveis de Eventos para geração de Ordens de Serviço Asociadas
 */
public enum EventoGeracaoServico {
	GERACAO_ORDEM_SERVICO(1), ENCERRAMENTO_ORDEM_SERVICO(2);

	private final int idEvento;

	EventoGeracaoServico(int idEventoGeracao) {

		this.idEvento = idEventoGeracao;
	}

	public int valorId(){

		return this.idEvento;
	}
}
