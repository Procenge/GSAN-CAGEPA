
package gcom.batch.micromedicao;

import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.io.File;
import java.util.Collection;

import javax.ejb.CreateException;

public class TarefaBatchProcessarArquivoDadosLeituraAnormalidade
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchProcessarArquivoDadosLeituraAnormalidade(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchProcessarArquivoDadosLeituraAnormalidade() {

		super(null, 0);
	}

	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoBatch(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Collection<Object> pesquisarTodasUnidadeProcessamentoReinicioBatch(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("TarefaBatchProcessarArquivoDadosLeituraAnormalidade", this);
	}

	@Override
	public Object executar() throws TarefaException{

		Boolean concluiuComErro = false;
		Integer idFaturamentoGrupo = (Integer) getParametro("idFaturamentoGrupo");
		File arquivoLeitura = (File) getParametro("arquivoLeitura");
		Usuario usuario = (Usuario) getParametro("usuario");
		Integer idFuncionalidadeIniciada = (Integer) getParametro("idFuncionalidadeIniciada");

		try{

			this.getControladorMicromedicao().processarArquivoLeituraAnormalidades(idFaturamentoGrupo, arquivoLeitura, usuario,
							idFuncionalidadeIniciada);

		}catch(Exception e){
			concluiuComErro = true;
		}finally{
			try{
				this.getControladorBatch().encerrarProcesso(this.getIdFuncionalidadeIniciada(), concluiuComErro);
			}catch(ControladorException e){
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Retorna o valor de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	private ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	private ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

}
