
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.relatorio.ConstantesRelatorios;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

public class RelatorioOrdemServicoCobranca
				extends TarefaRelatorio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RelatorioOrdemServicoCobranca(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
	}

	public RelatorioOrdemServicoCobranca(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO);
	}

	public RelatorioOrdemServicoCobranca() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub
		AgendadorTarefas.agendarTarefa("RelatorioOrdemServicoCobranca", this);
	}

	@Override
	public Object executar() throws TarefaException{

		byte[] retorno = (byte[]) getParametro("relatorio");
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		try{
			this.persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ORDEM_SERVICO_COBRANCA, idFuncionalidadeIniciada, "");
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

}
