
package gcom.batch.cobranca;

import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaBatch;
import gcom.tarefa.TarefaException;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.Collection;

import javax.ejb.CreateException;

public class TarefaBatchEfetuarBaixaOrdensServicoCobranca
				extends TarefaBatch {

	private static final long serialVersionUID = 1L;

	public TarefaBatchEfetuarBaixaOrdensServicoCobranca(Usuario usuario, int idFuncionalidadeIniciada) {

		super(usuario, idFuncionalidadeIniciada);
	}

	@Deprecated
	public TarefaBatchEfetuarBaixaOrdensServicoCobranca() {

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

		AgendadorTarefas.agendarTarefa("TarefaBatchEfetuarBaixaOrdensServicoCobranca", this);
	}

	@Override
	public Object executar() throws TarefaException{

		Boolean concluiuComErro = false;
		String arquivo = (String) getParametro("arquivo");
		Usuario usuario = (Usuario) getParametro("usuario");

		try{

			this.getControladorCobranca().efetuarBaixaOrdensServicoCobranca(arquivo, usuario);

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

	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);

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
