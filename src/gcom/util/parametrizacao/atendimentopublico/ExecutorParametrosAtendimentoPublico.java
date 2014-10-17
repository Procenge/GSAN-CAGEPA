/**
 * 
 */

package gcom.util.parametrizacao.atendimentopublico;

import gcom.atendimentopublico.IRepositorioAtendimentoPublico;
import gcom.atendimentopublico.RepositorioAtendimentoPublicoHBM;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.cliente.ControladorClienteLocal;
import gcom.cadastro.cliente.ControladorClienteLocalHome;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.geografico.ControladorGeograficoLocal;
import gcom.cadastro.geografico.ControladorGeograficoLocalHome;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.relatorio.atendimentopublico.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.parametrizacao.ExecutorParametro;

import java.util.Date;
import java.util.Map;

import javax.ejb.CreateException;

import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * @author Ailton Sousa
 * @date 09/02/2012
 */
public class ExecutorParametrosAtendimentoPublico
				implements ExecutorParametro {

	private static final ExecutorParametrosAtendimentoPublico instancia = new ExecutorParametrosAtendimentoPublico();

	protected IRepositorioAtendimentoPublico repositorioAtendimentoPublico;

	public static ExecutorParametrosAtendimentoPublico getInstancia(){

		return instancia;
	}

	private ExecutorParametrosAtendimentoPublico() {

	}

	public void ejbCreate() throws CreateException{

		repositorioAtendimentoPublico = RepositorioAtendimentoPublicoHBM.getInstancia();
	}

	/**
	 * Retorna a instância de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	protected ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna a instância de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	protected ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorCliente
	 * 
	 * @return O valor de controladorImovel
	 */
	protected ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	protected ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorCadastro
	 * 
	 * @return O valor de controladorCadastro
	 */
	protected ControladorCadastroLocal getControladorCadastro(){

		ControladorCadastroLocalHome localHome = null;
		ControladorCadastroLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorCadastroLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CADASTRO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna a instância de controladorGeografico
	 * 
	 * @return O valor de controladorGeografico
	 */
	protected ControladorGeograficoLocal getControladorGeografico(){

		ControladorGeograficoLocalHome localHome = null;
		ControladorGeograficoLocal local = null;
		ServiceLocator locator = null;

		try{

			locator = ServiceLocator.getInstancia();
			localHome = (ControladorGeograficoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_GEOGRAFICO_SEJB);
			local = localHome.create();

			return local;
		}catch(CreateException e){

			throw new SistemaException(e);
		}catch(ServiceLocatorException e){

			throw new SistemaException(e);
		}
	}

	protected ControladorBatchLocal getControladorBatch(){

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

	/**
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB1000] - Emitir Relação Imóveis – Modelo 1
	 * 
	 * @author Hugo Lima
	 * @date 20/03/2012
	 * @param parametroSistema
	 * @param mapOdensServico
	 * @param helper
	 * @param usuario
	 * @param nomeRelatorio
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarRelatorioEmitirOrdemServicoSeletivaModelo1(ParametroSistema parametroSistema,
					Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper, Usuario usuario, String nomeRelatorio)
					throws ControladorException, CreateException{

		this.ejbCreate();

		RelatorioEmitirOrdemServicoSeletivaModelo1 relatorio = new RelatorioEmitirOrdemServicoSeletivaModelo1(usuario, nomeRelatorio);

		relatorio.addParametro("mapOdensServico", mapOdensServico);
		relatorio.addParametro("helper", helper);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB1001] - Emitir Relação Imóveis – Modelo 2
	 * 
	 * @author Hugo Lima
	 * @date 20/03/2012
	 * @param parametroSistema
	 * @param mapOdensServico
	 * @param helper
	 * @param usuario
	 * @param nomeRelatorio
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarRelatorioEmitirOrdemServicoSeletivaModelo2(ParametroSistema parametroSistema,
					Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper, Usuario usuario, String nomeRelatorio)
					throws ControladorException, CreateException{

		this.ejbCreate();

		RelatorioEmitirOrdemServicoSeletivaModelo2 relatorio = new RelatorioEmitirOrdemServicoSeletivaModelo2(usuario, nomeRelatorio);

		relatorio.addParametro("mapOdensServico", mapOdensServico);
		relatorio.addParametro("helper", helper);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [FS0008 – Validar da Data do Roteiro]
	 * 
	 * @author Hugo Lima
	 * @date 20/03/2012
	 * @param parametroSistema
	 * @param mapOdensServico
	 * @param helper
	 * @param usuario
	 * @param nomeRelatorio
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarRelatorioEmitirOrdemServicoSeletivaModelo3(ParametroSistema parametroSistema,
					Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper, Usuario usuario, String nomeRelatorio)
					throws ControladorException, CreateException{

		this.ejbCreate();

		RelatorioEmitirOrdemServicoSeletivaModelo3 relatorio = new RelatorioEmitirOrdemServicoSeletivaModelo3(usuario, nomeRelatorio);

		relatorio.addParametro("mapOdensServico", mapOdensServico);
		relatorio.addParametro("helper", helper);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB1003] - Emitir Relação Imóveis - Modelo 4 - P_REL_SEL_4]
	 * 
	 * @author André Lopes
	 * @date 08/04/2013
	 * @param parametroSistema
	 * @param mapOdensServico
	 * @param helper
	 * @param usuario
	 * @param nomeRelatorio
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarRelatorioEmitirOrdemServicoSeletivaModelo4(ParametroSistema parametroSistema,
					Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper, Usuario usuario, String nomeRelatorio)
					throws ControladorException, CreateException{

		this.ejbCreate();

		RelatorioEmitirOrdemServicoSeletivaModelo4 relatorio = new RelatorioEmitirOrdemServicoSeletivaModelo4(usuario, nomeRelatorio);

		relatorio.addParametro("mapOdensServico", mapOdensServico);
		relatorio.addParametro("helper", helper);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB1003] - Emitir Relação Imóveis - Modelo 5 - P_REL_SEL_5]
	 * 
	 * @author Eduardo Oliveira
	 * @date 07/11/2013
	 * @param parametroSistema
	 * @param mapOdensServico
	 * @param helper
	 * @param usuario
	 * @param nomeRelatorio
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarRelatorioEmitirOrdemServicoSeletivaModelo5(ParametroSistema parametroSistema,
					Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper, Usuario usuario, String nomeRelatorio)
					throws ControladorException, CreateException{

		this.ejbCreate();

		RelatorioEmitirOrdemServicoSeletivaModelo5 relatorio = new RelatorioEmitirOrdemServicoSeletivaModelo5(usuario, nomeRelatorio);

		relatorio.addParametro("mapOdensServico", mapOdensServico);
		relatorio.addParametro("helper", helper);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB1003] - Emitir Relação Imóveis - Modelo 6 - P_REL_SEL_6]
	 * 
	 * @author Eduardo Oliveira
	 * @date 13/11/2013
	 * @param parametroSistema
	 * @param mapOdensServico
	 * @param helper
	 * @param usuario
	 * @param nomeRelatorio
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarRelatorioEmitirOrdemServicoSeletivaModelo6(ParametroSistema parametroSistema,
					Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper, Usuario usuario, String nomeRelatorio)
					throws ControladorException, CreateException{

		this.ejbCreate();

		RelatorioEmitirOrdemServicoSeletivaModelo6 relatorio = new RelatorioEmitirOrdemServicoSeletivaModelo6(usuario, nomeRelatorio);

		relatorio.addParametro("mapOdensServico", mapOdensServico);
		relatorio.addParametro("helper", helper);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC0713] Emitir Ordem de Serviço Seletiva
	 * [SB1003] - Emitir Relação Imóveis - Modelo 7 - P_REL_SEL_7]
	 * 
	 * @author Eduardo Oliveira
	 * @date 07/11/2013
	 * @param parametroSistema
	 * @param mapOdensServico
	 * @param helper
	 * @param usuario
	 * @param nomeRelatorio
	 * @throws ControladorException
	 * @throws CreateException
	 */
	public void execParamGerarRelatorioEmitirOrdemServicoSeletivaModelo7(ParametroSistema parametroSistema,
					Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper, Usuario usuario, String nomeRelatorio)
					throws ControladorException, CreateException{

		this.ejbCreate();

		RelatorioEmitirOrdemServicoSeletivaModelo7 relatorio = new RelatorioEmitirOrdemServicoSeletivaModelo7(usuario, nomeRelatorio);

		relatorio.addParametro("mapOdensServico", mapOdensServico);
		relatorio.addParametro("helper", helper);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		getControladorBatch().iniciarProcessoRelatorio(relatorio);
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [FS0008 – Validar da Data do Roteiro]
	 * Este método é utilizado por empresas que utilizam esta
	 * validação: [FS0008 – Validar da Data do Roteiro]
	 * 
	 * @author Diogo Gonçalves, José Cláudio
	 * @date 14/05/2012
	 * @param parametroSistema
	 * @param dataEncerramentoDateSemHora
	 * @param dataRoteiroDate
	 * @throws ControladorException
	 * @throws CreateException
	 */

	public void execParamValidarDataRoteiroFormato1(ParametroSistema parametroSistema, Date dataEncerramentoDateSemHora,
					Date dataRoteiroDate) throws ControladorException, CreateException{

		if(dataEncerramentoDateSemHora.after(dataRoteiroDate)){
			throw new ControladorException("atencao.data_encerramento_maior_data_roteiro", null);
		}
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [FS0008 – Validar da Data do Roteiro]
	 * Este método não deve fazer nada pois é utilizado por empresas que não utilizam esta
	 * validação: [FS0008 – Validar da Data do Roteiro]
	 * 
	 * @author Diogo Gonçalves, José Cláudio
	 * @date 14/05/2012
	 * @param parametroSistema
	 * @param dataEncerramentoDateSemHora
	 * @param dataRoteiroDate
	 * @throws ControladorException
	 * @throws CreateException
	 */

	public void execParamValidarDataRoteiroFormato2(ParametroSistema parametroSistema, Date dataEncerramentoDateSemHora,
					Date dataRoteiroDate) throws ControladorException, CreateException{

		// Este método não deve fazer nada pois é utilizado por empresas que não utilizam esta
		// validação.

	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [FS0012] - Validar Data de Execução da Ordem de Serviço - Modelo 1.
	 * 
	 * @author Anderson italo
	 * @date 16/12/2013
	 */
	public void execParamValidarDataExecucaoOrdemServicoModelo1(ParametroSistema parametroSistema, Date dataExecucao, Date dataRoteiro)
					throws ControladorException, CreateException{

		// Não houver levantamento de nenhuma regra específica.
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [FS0012] - Validar Data de Execução da Ordem de Serviço - Modelo 2.
	 * 
	 * @author Anderson italo
	 * @date 16/12/2013
	 */
	public void execParamValidarDataExecucaoOrdemServicoModelo2(ParametroSistema parametroSistema, Date dataExecucao, Date dataRoteiro)
					throws ControladorException, CreateException{

		if(dataExecucao.before(Util.subtrairNumeroDiasDeUmaData(new Date(), 45))){

			throw new ControladorException("atencao.data_execucao_anterior_45_dias_data_corrente", null);
		}

		if(dataRoteiro.compareTo(Util.converteStringParaDate("01/01/1900", true)) != 0
						&& dataExecucao.before(Util.subtrairNumeroDiasDeUmaData(dataRoteiro, 2))){

			throw new ControladorException("atencao.data_execucao_anterior_2_dias_data_programacao", null);
		}

		if(dataRoteiro.compareTo(Util.converteStringParaDate("01/01/1900", true)) != 0
						&& dataExecucao.after(Util.adicionarNumeroDiasDeUmaData(dataRoteiro, 2))){

			throw new ControladorException("atencao.data_execucao_posterior_2_dias_data_programacao", null);
		}
	}
}
