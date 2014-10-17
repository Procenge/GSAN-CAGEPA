/**
 * 
 */

package gcom.relatorio.ordemservico;

import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.atendimentopublico.ligacaoagua.ControladorLigacaoAguaLocal;
import gcom.atendimentopublico.ligacaoagua.ControladorLigacaoAguaLocalHome;
import gcom.atendimentopublico.ordemservico.ControladorOrdemServicoLocal;
import gcom.atendimentopublico.ordemservico.ControladorOrdemServicoLocalHome;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocal;
import gcom.atendimentopublico.registroatendimento.ControladorRegistroAtendimentoLocalHome;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.cliente.ControladorClienteLocal;
import gcom.cadastro.cliente.ControladorClienteLocalHome;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.imovel.ControladorImovelLocal;
import gcom.cadastro.imovel.ControladorImovelLocalHome;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.tarefa.ParametroTarefa;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.CreateException;

/**
 * Essa classe representa um gerador de dados para o relatório
 * 
 * @author gmatos
 */
public abstract class GeradorDadosRelatorioOrdemServico {

	@SuppressWarnings("unchecked")
	private Set parametroTarefa = new HashSet();

	/**
	 * @param os
	 *            A ordem de serviço
	 * @return Uma coleção com os dados para o relatório
	 * @throws GeradorRelatorioOrdemServicoException
	 */
	public abstract List<DadosRelatorioOrdemServico> gerarDados(OrdemServico os) throws GeradorRelatorioOrdemServicoException;

	/**
	 * 2 ou mais OS's por páginas
	 * 
	 * @author isilva
	 * @param os
	 *            A ordem de serviço
	 * @return Uma coleção com os dados para o relatório
	 * @throws GeradorRelatorioOrdemServicoException
	 */
	public abstract List<DadosRelatorioOrdemServico> gerarDados(Collection<OrdemServico> ordensServicos)
					throws GeradorRelatorioOrdemServicoException;

	@SuppressWarnings("unchecked")
	public Set getParametroTarefa(){

		return parametroTarefa;

	}

	@SuppressWarnings("unchecked")
	public void setParametroTarefa(Set parametroTarefa){

		this.parametroTarefa = parametroTarefa;
	}

	/**
	 * Método que adiciona um Short
	 * 
	 * @param nome
	 * @param valor
	 */
	@SuppressWarnings("unchecked")
	public void addParametro(String nome, Object valor){

		parametroTarefa.add(new ParametroTarefa(nome, valor));
	}

	/**
	 * @author isilva
	 * @date 15/09/2010
	 * @param nome
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getParametro(String nome){

		Object retorno = null;
		Iterator it = parametroTarefa.iterator();
		while(it.hasNext()){
			ParametroTarefa parametroTarefa = (ParametroTarefa) it.next();
			if(nome != null && nome.equals(parametroTarefa.getNome())){
				retorno = parametroTarefa.getValor();
			}
		}
		return retorno;
	}

	/**
	 * Retorna o valor de controladorRegistroAtendimento
	 */
	protected ControladorRegistroAtendimentoLocal getControladorRegistroAtendimento(){

		ControladorRegistroAtendimentoLocalHome localHome = null;
		ControladorRegistroAtendimentoLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorRegistroAtendimentoLocalHome) locator
							.getLocalHome(ConstantesJNDI.CONTROLADOR_REGISTRO_ATENDIMENTO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	protected ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorEndereco
	 * 
	 * @return O valor de controladorEndereco
	 */
	protected ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorEnderecoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ENDERECO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o controladorCadastro
	 * 
	 * @date 09/06/2009
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
	 * Retorna o controladorOrdemServico
	 * 
	 * @author Virgínia Melo
	 * @date 11/06/2009
	 */
	protected ControladorOrdemServicoLocal getControladorOrdemServico(){

		ControladorOrdemServicoLocalHome localHome = null;
		ControladorOrdemServicoLocal local = null;

		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorOrdemServicoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ORDEM_SERVICO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o ControladorCliente
	 * 
	 * @author Virgínia Melo
	 * @date 11/06/2009
	 */
	protected ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * @author André Nishimura
	 * @date 18 de Dezembro 2009
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * @author isilva
	 * @return
	 */
	protected ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorMicromedicao
	 * 
	 * @return O valor de controladorMicromedicao
	 */
	protected ControladorMicromedicaoLocal getControladorMicromedicao(){

		ControladorMicromedicaoLocalHome localHome = null;
		ControladorMicromedicaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorMicromedicaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_MICROMEDICAO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor do Controlador de Cobranca
	 * 
	 * @return O valor de controladorCobrancaLocal
	 */
	protected ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

	/**
	 * Retorna o controladorLigacaoAgua
	 */
	protected ControladorLigacaoAguaLocal getControladorLigacaoAgua(){

		ControladorLigacaoAguaLocalHome localHome = null;
		ControladorLigacaoAguaLocal local = null;

		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorLigacaoAguaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_AGUA_SEJB);
			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * @author isilva
	 * @param colecao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Collection dividirColecaoOrdenando(Collection colecao){

		List retorno = new ArrayList();
		List colecaoTmp = (List) colecao;
		int quantidadeItens = 0;
		int tamanhoColecao = colecaoTmp.size();
		int metadeColecao = 0;
		if(tamanhoColecao % 2 == 0){
			metadeColecao = tamanhoColecao / 2;
		}else{
			metadeColecao = (tamanhoColecao / 2) + 1;
		}
		while(quantidadeItens < metadeColecao){
			retorno.add(colecaoTmp.get(quantidadeItens));
			if(metadeColecao + quantidadeItens < tamanhoColecao){
				retorno.add(colecaoTmp.get(metadeColecao + quantidadeItens));
			}
			quantidadeItens++;
		}
		tamanhoColecao = 0;

		return retorno;
	}

	/**
	 * Retorna o controladorFaturamento
	 */
	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

}
