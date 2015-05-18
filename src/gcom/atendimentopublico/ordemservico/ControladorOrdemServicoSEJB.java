/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio Virginio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.atendimentopublico.ordemservico;

import gcom.acquagis.atendimento.OrdemServicoDetalhesJSONHelper;
import gcom.acquagis.atendimento.OrdemServicoJSONHelper;
import gcom.arrecadacao.ControladorArrecadacaoLocal;
import gcom.arrecadacao.ControladorArrecadacaoLocalHome;
import gcom.arrecadacao.pagamento.*;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocal;
import gcom.atendimentopublico.ControladorAtendimentoPublicoLocalHome;
import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ligacaoagua.*;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao.LigacaoAguaSituacaoEnum;
import gcom.atendimentopublico.ligacaoesgoto.*;
import gcom.atendimentopublico.ordemservico.bean.*;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.FiltroFuncionalidadeIniciada;
import gcom.batch.FuncionalidadeIniciada;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ControladorClienteLocal;
import gcom.cadastro.cliente.ControladorClienteLocalHome;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.endereco.ControladorEnderecoLocal;
import gcom.cadastro.endereco.ControladorEnderecoLocalHome;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.imovel.*;
import gcom.cadastro.imovel.bean.ImovelDadosConsumoHistoricoHelper;
import gcom.cadastro.imovel.bean.ImovelDadosMedicaoHistoricoHelper;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.ControladorUnidadeLocal;
import gcom.cadastro.unidade.ControladorUnidadeLocalHome;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.*;
import gcom.cobranca.bean.ContaVencidaHelper;
import gcom.cobranca.bean.CriticaOSLoteHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.contabil.ControladorContabilLocal;
import gcom.contabil.ControladorContabilLocalHome;
import gcom.contabil.OperacaoContabil;
import gcom.fachada.Fachada;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.bean.EmitirContaHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.debito.*;
import gcom.faturamento.debito.DebitoTipo.DebitoTipoEnum;
import gcom.financeiro.ControladorFinanceiroLocal;
import gcom.financeiro.ControladorFinanceiroLocalHome;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.gui.atendimentopublico.ordemservico.RoteiroOSDadosProgramacaoHelper;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.bean.ListaDadosPrestacaoGuiaHelper;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.ControladorMicromedicaoLocal;
import gcom.micromedicao.ControladorMicromedicaoLocalHome;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroCondicao;
import gcom.micromedicao.hidrometro.HidrometroHistoricoAfericao;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.SetorAbastecimento;
import gcom.relatorio.atendimentopublico.*;
import gcom.relatorio.ordemservico.GeradorRelatorioOrdemServico;
import gcom.relatorio.ordemservico.OSRelatorioEstruturaHelper;
import gcom.relatorio.ordemservico.OSRelatorioPadraoComOcorrenciaHelper;
import gcom.seguranca.ControladorPermissaoEspecialLocal;
import gcom.seguranca.ControladorPermissaoEspecialLocalHome;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.*;
import gcom.seguranca.transacao.ControladorTransacaoLocal;
import gcom.seguranca.transacao.ControladorTransacaoLocalHome;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.filtro.*;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.ParametroGeral;
import gcom.util.parametrizacao.atendimentopublico.ExecutorParametrosAtendimentoPublico;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.ordemservico.ParametroOrdemServico;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.zip.ZipOutputStream;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.HashedMap;

public class ControladorOrdemServicoSEJB
				implements SessionBean, Parametrizacao {

	private static final long serialVersionUID = 1L;

	private IRepositorioOrdemServico repositorioOrdemServico = null;

	SessionContext sessionContext;

	public void ejbCreate() throws CreateException{

		repositorioOrdemServico = RepositorioOrdemServicoHBM.getInstancia();
	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosAtendimentoPublico.getInstancia();
	}

	/**
	 * Retorna o valor de controladorImovel
	 * 
	 * @return O valor de controladorImovel
	 */
	private ControladorImovelLocal getControladorImovel(){

		ControladorImovelLocalHome localHome = null;
		ControladorImovelLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorImovelLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_IMOVEL_SEJB);
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

	/**
	 * Retorna o valor de controladorFaturamento
	 * 
	 * @return O valor de controladorFaturamento
	 */
	private ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
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

	/**
	 * Retorna o valor de controladorFinanceiro
	 * 
	 * @return O valor de controladorFinanceiro
	 */
	private ControladorFinanceiroLocal getControladorFinanceiro(){

		ControladorFinanceiroLocalHome localHome = null;
		ControladorFinanceiroLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFinanceiroLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FINANCEIRO_SEJB);
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

	/**
	 * Retorna o controladorLigacaoEsgoto
	 * 
	 * @author Leonardo Regis
	 * @date 18/08/2006
	 */
	private ControladorLigacaoEsgotoLocal getControladorLigacaoEsgoto(){

		ControladorLigacaoEsgotoLocalHome localHome = null;
		ControladorLigacaoEsgotoLocal local = null;

		ServiceLocator locator = null;
		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorLigacaoEsgotoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LIGACAO_ESGOTO_SEJB);
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

	/**
	 * Retorna o controladorLigacaoAgua
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 */
	private ControladorLigacaoAguaLocal getControladorLigacaoAgua(){

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

	/**
	 * Retorna o valor de controladorUnidade
	 * 
	 * @return O valor de controladorUnidade
	 */
	private ControladorUnidadeLocal getControladorUnidade(){

		ControladorUnidadeLocalHome localHome = null;
		ControladorUnidadeLocal local = null;

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUnidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UNIDADE_SEJB);

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

	private ControladorEnderecoLocal getControladorEndereco(){

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

	private ControladorRegistroAtendimentoLocal getControladorRegistroAtendimento(){

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
	 * Retorna o valor de controladorAtendimentoPublico
	 * 
	 * @return O valor de controladorAtendimentoPublico
	 */

	private ControladorAtendimentoPublicoLocal getControladorAtendimentoPublico(){

		ControladorAtendimentoPublicoLocalHome localHome = null;
		ControladorAtendimentoPublicoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorAtendimentoPublicoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_ATENDIMENTO_PUBLICO_SEJB);
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

	/**
	 * Retorna o valor de controladorCliente
	 * 
	 * @return O valor de controladorCliente
	 */

	private ControladorClienteLocal getControladorCliente(){

		ControladorClienteLocalHome localHome = null;
		ControladorClienteLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorClienteLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CLIENTE_SEJB);
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

	/**
	 * Retorna o valor de controladorCobranca
	 * 
	 * @return O valor de controladorCobranca
	 */

	protected ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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

	/**
	 * Retorna o valor de controladorLocalidade
	 * 
	 * @return O valor de controladorLocalidade
	 */

	protected ControladorLocalidadeLocal getControladorLocalidade(){

		ControladorLocalidadeLocalHome localHome = null;
		ControladorLocalidadeLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorLocalidadeLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_LOCALIDADE_SEJB);
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

	/**
	 * Retorna o valor de controladorPermissaoEspecial
	 * 
	 * @return O valor de controladorPermissaoEspecial
	 */

	protected ControladorPermissaoEspecialLocal getControladorPermissaoEspecial(){

		ControladorPermissaoEspecialLocalHome localHome = null;
		ControladorPermissaoEspecialLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorPermissaoEspecialLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_PERMISSAO_ESPECIAL_SEJB);
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

	/**
	 * [UC0454] Obter Descrição da situação da OS
	 * Este caso de uso permite obter a descrição de uma ordem de serviço
	 * 
	 * @author Leonardo Regis
	 * @date 11/08/2006
	 * @param idOrdemServico
	 * @throws ControladorException
	 */
	public ObterDescricaoSituacaoOSHelper obterDescricaoSituacaoOS(Integer idOrdemServico) throws ControladorException{

		ObterDescricaoSituacaoOSHelper retorno = new ObterDescricaoSituacaoOSHelper();
		try{

			// verifica o código da situação(ORSE_CDSITUACAO) da ordem de
			// serviço
			Short situacao = repositorioOrdemServico.verificaSituacaoOS(idOrdemServico);

			if(situacao != null){
				// caso o valor igual a 1
				if(situacao.equals(OrdemServico.SITUACAO_PENDENTE)){
					retorno.setDescricaoSituacao(OrdemServico.SITUACAO_DESCRICAO_PENDENTE);
					retorno.setDescricaoAbreviadaSituacao(OrdemServico.SITUACAO_DESC_ABREV_PENDENTE);
					retorno.setLetraIdentificadoraSituacaoOS(OrdemServico.SITUACAO_LETRA_IDENTIFICADORA_PENDENTE);
					// caso o valor igual a 2
				}else if(situacao.equals(OrdemServico.SITUACAO_ENCERRADO)){
					retorno.setDescricaoSituacao(OrdemServico.SITUACAO_DESCRICAO_ENCERRADO);
					retorno.setDescricaoAbreviadaSituacao(OrdemServico.SITUACAO_DESC_ABREV_ENCERRADO);
					retorno.setLetraIdentificadoraSituacaoOS(OrdemServico.SITUACAO_LETRA_IDENTIFICADORA_ENCERRADO);
					// caso o valor igual a 3
				}else if(situacao.equals(OrdemServico.SITUACAO_EXECUCAO_EM_ANDAMENTO)){
					retorno.setDescricaoSituacao(OrdemServico.SITUACAO_DESCRICAO_EXECUCAO_EM_ANDAMENTO);
					retorno.setDescricaoAbreviadaSituacao(OrdemServico.SITUACAO_DESC_ABREV_EXECUCAO_EM_ANDAMENTO);
					retorno.setLetraIdentificadoraSituacaoOS(OrdemServico.SITUACAO_LETRA_IDENTIFICADORA_EXECUCAO_EM_ANDAMENTO);
					// caso o valor igual a 4
				}else if(situacao.equals(OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO)){
					retorno.setDescricaoSituacao(OrdemServico.SITUACAO_DESCRICAO_AGUARDANDO_LIBERACAO);
					retorno.setDescricaoAbreviadaSituacao(OrdemServico.SITUACAO_DESC_ABREV_AGUARDANDO_LIBERACAO);
					retorno.setLetraIdentificadoraSituacaoOS(OrdemServico.SITUACAO_LETRA_IDENTIFICADORA_AGUARDANDO_LIBERACAO);
				}
			}
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0441] Consultar Dados da Ordem de Serviço
	 * 
	 * @author Leonardo Regis
	 * @date 15/08/2006
	 * @param idOrdemServico
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico consultarDadosOrdemServico(Integer idOrdemServico) throws ControladorException{

		OrdemServico retorno = null;
		try{
			retorno = repositorioOrdemServico.consultarDadosOS(idOrdemServico);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0427] Tramitar Registro Atendimento
	 * Verifica Existência de Ordem de Serviço Programada
	 * 
	 * @author Leonardo Regis
	 * @date 19/08/2006
	 * @param idOS
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarExistenciaOSProgramada(Integer idOS) throws ControladorException{

		boolean retorno = false;
		try{
			retorno = repositorioOrdemServico.verificarExistenciaOSProgramada(idOS);
		}catch(ErroRepositorioException ex){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ilex){

			}
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB001] Selecionar Ordem de Servico por Situação [SB002] Selecionar Ordem
	 * de Servico por Situação da Programação [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Município [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 *       Alterado o método para fazer o merge de todos os ids de os filtradas, bem
	 *       como uma mensagem de erro caso não retorne registro.
	 * @author Leonardo Regis
	 * @date 07/09/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdemServico(PesquisarOrdemServicoHelper filtro) throws ControladorException{

		Set colecaoOS = new HashSet();

		try{

			// [SB0004] - Seleciona Ordem Servico por Código de Cliente
			if(filtro.getCodigoCliente() != null){

				Collection<Integer> colecaoIdOSPorCliente = this.consultarOrdemServicoPorCodigoCliente(filtro.getCodigoCliente(),
								filtro.getDocumentoCobranca());

				if(colecaoIdOSPorCliente != null && !colecaoIdOSPorCliente.isEmpty()){

					if(colecaoOS != null && !colecaoOS.isEmpty()){
						Collection<Integer> colecaoIdOSRetorno = mergeIDs(colecaoOS, colecaoIdOSPorCliente);

						if(colecaoIdOSRetorno != null && !colecaoIdOSRetorno.isEmpty()){
							colecaoOS.clear();
							colecaoOS.addAll(colecaoIdOSRetorno);
						}else{
							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.pesquisa.nenhumresultado");
						}
					}else{
						colecaoOS.addAll(colecaoIdOSPorCliente);
					}
				}else{
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.pesquisa.nenhumresultado");
				}
			}

			filtro.setColecaoIdsOS(colecaoOS);

			Short permiteTramiteIndependente = Short.parseShort((String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE
											.executar(ExecutorParametrosAtendimentoPublico.getInstancia()));

			return repositorioOrdemServico.pesquisarOrdemServico(filtro, permiteTramiteIndependente);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Filtrar Ordem de Serviço
	 * Faz o merge dos ids das os que entrarão na consulta do filtrar os
	 * 
	 * @author Leonardo Regis
	 * @date 07/09/2006
	 * @param oldCollection
	 * @param newCollection
	 * @return coleção de ids de os
	 */
	private Collection<Integer> mergeIDs(Collection<Integer> oldCollection, Collection<Integer> newCollection){

		Collection<Integer> returnCollection = new ArrayList<Integer>();
		for(Integer oldIDs : oldCollection){
			for(Integer newIDs : newCollection){
				if(oldIDs.intValue() == newIDs.intValue()){
					returnCollection.add(newIDs);
					break;
				}
			}
		}
		return returnCollection;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB004] Selecionar Ordem de Servico por Codigo do Cliente [SB004] Caso
	 * 1.1 [SB004] Caso 1.2
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param codigoCliente
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	private Collection<Integer> consultarOrdemServicoPorCodigoCliente(Integer codigoCliente, Integer documentoCobranca)
					throws ControladorException{

		Set colecaoOS = new HashSet();

		try{
			Collection colecaoOSRASolicitantes = repositorioOrdemServico.recuperaOSPorCodigoClienteRASolicitante(codigoCliente);
			colecaoOS.addAll(colecaoOSRASolicitantes);

			Collection colecaoOSClienteImovel = repositorioOrdemServico.recuperaOSPorCodigoCliente(codigoCliente);
			colecaoOS.addAll(colecaoOSClienteImovel);

			if(documentoCobranca != null){
				Collection colecaoOSCobrancaDocumento = repositorioOrdemServico.recuperaOSPorCodigoClienteCobrancaDocumento(codigoCliente);
				colecaoOS.addAll(colecaoOSCobrancaDocumento);
			}

		}catch(ErroRepositorioException e){
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);

		}

		return colecaoOS;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB005] Selecionar Ordem de Servico por Unidade Superior
	 * 
	 * @author Rafael Pinto
	 * @date 21/08/2006
	 * @param colecaoUnidadeSuperior
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	private Collection<Integer> recuperarListaOSPorUnidadeSubordinada(Collection<UnidadeOrganizacional> colecaoUnidadeSuperior,
					Collection<Integer> colecaoOSPorUnidadeSuperior) throws ControladorException{

		Collection<UnidadeOrganizacional> colecaoUnidadesSubordinadas = null;

		for(Iterator iter = colecaoUnidadeSuperior.iterator(); iter.hasNext();){

			UnidadeOrganizacional unidadeAtual = (UnidadeOrganizacional) iter.next();

			colecaoOSPorUnidadeSuperior.addAll(this.montarColecaoUnidades(unidadeAtual));

			colecaoUnidadesSubordinadas = getControladorUnidade().recuperarUnidadesSubordinadasPorUnidadeSuperior(unidadeAtual);

			if(colecaoUnidadesSubordinadas != null && !colecaoUnidadesSubordinadas.isEmpty()){

				colecaoOSPorUnidadeSuperior.addAll(recuperarListaOSPorUnidadeSubordinada(colecaoUnidadesSubordinadas,
								colecaoOSPorUnidadeSuperior));
			}
		}

		return colecaoOSPorUnidadeSuperior;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * Montar a colecao de unidades
	 * 
	 * @author Rafael Pinto
	 * @date 21/08/2006
	 * @param unidade
	 * @return Collection<Integer>
	 * @throws ControladorException
	 */
	private Collection<Integer> montarColecaoUnidades(UnidadeOrganizacional unidade) throws ControladorException{

		Collection<Integer> idsOS = new ArrayList();

		Collection<Integer> idsRA = this.getControladorRegistroAtendimento().recuperaRAPorUnidadeAtual(unidade);

		if(idsRA != null && !idsRA.isEmpty()){

			Iterator itera = idsRA.iterator();

			while(itera.hasNext()){

				Integer element = (Integer) itera.next();

				Collection<OrdemServico> colecaoOS = getControladorRegistroAtendimento().obterOSRA(element);

				if(colecaoOS != null && !colecaoOS.isEmpty()){
					Iterator iteraOS = colecaoOS.iterator();
					while(iteraOS.hasNext()){
						OrdemServico os = (OrdemServico) iteraOS.next();
						idsOS.add(os.getId());
					}
				}
			}
		}// fim do if idsRA

		return idsOS;
	}

	/**
	 * [UC413]- Pesquisar Tipo de Serviço
	 * Pesquisar o Objeto servicoTipo referente ao idTiposervico recebido na
	 * descrição da pesquisa, onde o mesmo sera detalhado.
	 * 
	 * @author Leandro Cavalcanti
	 * @date 23/08/2006
	 * @param idTipoServico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarServicoTipo(Integer idTipoServico) throws ControladorException{

		Object[] servicoTipo;
		try{
			servicoTipo = repositorioOrdemServico.pesquisarServicoTipo(idTipoServico);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return servicoTipo;

	}

	public ServicoTipo pesquisarServicoTipoObjeto(Integer idTipoServico) throws ControladorException{

		ServicoTipo servicoTipo;
		try{
			servicoTipo = repositorioOrdemServico.pesquisarServicoTipo2(idTipoServico);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return servicoTipo;

	}

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * select a.svtp_id from ATENDIMENTOPUBLICO.SERVICO_TIPO A,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_ATIVIDADE B,
	 * ATENDIMENTOPUBLICO.SERVICO_TIPO_MATERIAL C WHERE A.SVTP_DSSERVICOTIPO
	 * LIKE '%DESC%' AND A.SVTP_DSABREVIADO LIKE '%DESC%' AND (A.SVTP_VLSERVICO >=
	 * 000000 AND A.SVTP_VLSERVICO <= 99999) AND A.SVTP_ICPAVIMENTO = 1 OU 2 and
	 * A.SVTP_ICATUALIZACOMERCIAL = 1 OU 2 AND A.SVTP_ICTERCEIRIZADO = 1 OU 2
	 * AND A.SVTP_CDSERVICOTIPO = ("O" OR "C") AND (A.SVTP_NNTEMPOMEDIOEXECUCAO >=
	 * 0000 AND A.SVTP_NNTEMPOMEDIOEXECUCAO <= 9999) AND DBTP_ID = ID INFORMADO
	 * AND AND CRTP_ID = ID INFORMADO AND STSG_ID = ID INFORMADO AND STRF_ID =
	 * ID INFORMADO AND STPR_ID = ID INFORMADO AND A.SVTP_ID = B.SVTP_ID AND
	 * B.ATIV_ID IN (ID's INFORMADOS) AND A.SVTP_ID = C.SVTP_ID AND C.MATE_ID IN
	 * (ID's INFORMADOS)
	 * 
	 * @author Leandro Cavalcanti
	 * @date 17/08/2006
	 */
	public Collection<ServicoTipo> filtrarST(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer numeroPaginasPesquisa, Integer idUnidadeOrganizacionalDestino)
					throws ControladorException{

		ServicoTipo tipoServico = null;
		Collection colecaoServicoTipo = null;

		Collection colecaoServicoTipoNova = null;

		try{
			colecaoServicoTipo = repositorioOrdemServico.filtrarST(servicoTipo, colecaoAtividades, colecaoMaterial, valorServicoInicial,
							valorServicoFinal, tempoMedioExecucaoInicial, tempoMedioExecucaoFinal, tipoPesquisa, tipoPesquisaAbreviada,
							numeroPaginasPesquisa, idUnidadeOrganizacionalDestino);

			if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){

				colecaoServicoTipoNova = new ArrayList();

				tipoServico = null;
				Iterator servicoTipoIterator = colecaoServicoTipo.iterator();
				Object[] arrayServicoTipo = null;
				while(servicoTipoIterator.hasNext()){
					arrayServicoTipo = null;
					arrayServicoTipo = (Object[]) servicoTipoIterator.next();

					tipoServico = new ServicoTipo();

					if(arrayServicoTipo != null){

						if(arrayServicoTipo[0] != null){
							tipoServico.setId((Integer) arrayServicoTipo[0]);
						}

						if(arrayServicoTipo[1] != null){
							tipoServico.setDescricao((String) arrayServicoTipo[1]);
						}

						if(arrayServicoTipo[2] != null){
							ServicoTipoPrioridade servicoTipoPrioridade = new ServicoTipoPrioridade();
							servicoTipoPrioridade.setDescricao((String) arrayServicoTipo[2]);
							tipoServico.setServicoTipoPrioridade(servicoTipoPrioridade);
						}
						if(arrayServicoTipo[3] != null){
							tipoServico.setIndicadorAtualizaComercial((Short) arrayServicoTipo[3]);
						}
						if(arrayServicoTipo[4] != null){
							tipoServico.setIndicadorPavimento((Short) arrayServicoTipo[4]);
						}
						if(arrayServicoTipo[5] != null){
							tipoServico.setIndicadorTerceirizado((Short) arrayServicoTipo[5]);
						}

						colecaoServicoTipoNova.add(tipoServico);
					}
				}

			}

			return colecaoServicoTipoNova;
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0413] Pesquisar Tipo de Serviço
	 * 
	 * @author Flávio
	 * @date 17/08/2006
	 */
	public Integer filtrarSTCount(ServicoTipo servicoTipo, Collection colecaoAtividades, Collection colecaoMaterial,
					String valorServicoInicial, String valorServicoFinal, String tempoMedioExecucaoInicial, String tempoMedioExecucaoFinal,
					String tipoPesquisa, String tipoPesquisaAbreviada, Integer idUnidadeOrganizacionalDestino) throws ControladorException{

		try{

			Integer qtdRegistros = repositorioOrdemServico.filtrarSTCount(servicoTipo, colecaoAtividades, colecaoMaterial,
							valorServicoInicial, valorServicoFinal, tempoMedioExecucaoInicial, tempoMedioExecucaoFinal, tipoPesquisa,
							tipoPesquisaAbreviada, idUnidadeOrganizacionalDestino);

			return qtdRegistros;
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisar Atividades pelo idServicoTipo na tabela de ServicoTipoAtividade
	 * Recupera os Atividades do servico tipo Atividade
	 * 
	 * @author Leandro Cavalcanti
	 * @date 24/08/2006
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection recuperarAtividadeServicoTipoConsulta(Integer idServicoTipoAtividade) throws ControladorException{

		Collection consultaAtividades;
		try{
			consultaAtividades = repositorioOrdemServico.recuperarAtividadeServicoTipoConsulta(idServicoTipoAtividade);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return consultaAtividades;

	}

	/**
	 * Pesquisar Materiais pelo idServicoTipo na tabela de ServicoTipoMaterial
	 * Recupera os materiais do servico tipo material
	 * 
	 * @author Leandro Cavalcanti
	 * @date 24/08/2006
	 * @param Integer
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Collection recuperarMaterialServicoTipoConsulta(Integer idServicoTipoMaterial) throws ControladorException{

		Collection consultaAtividades;
		try{
			consultaAtividades = repositorioOrdemServico.recuperarMaterialServicoTipoConsulta(idServicoTipoMaterial);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return consultaAtividades;

	}

	/**
	 * [UC0367] Atualizar Dados da Ligação Agua
	 * Consulta a ordem de servico pelo id
	 * 
	 * @author Rafael Pinto
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public OrdemServico recuperaOSPorId(Integer idOS) throws ControladorException{

		try{
			return repositorioOrdemServico.recuperaOSPorId(idOS);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 14/08/2006
	 */
	public void verificarExistenciaOrdemServico(Integer idRegistroAtendimento) throws ControladorException{

		try{

			// [FS0008] - Alerta Existência Ordem de Serviço

			/*
			 * Caso existam ordens de serviço pendentes para o registro de
			 * atendimento informado, exibir a mensagem: "Existe(m) Ordem(ns) de
			 * Serviço pendentes associadas ao Registro de Atendimento"
			 */
			boolean existe = repositorioOrdemServico.existeOrdemServicoDiferenteEncerrado(idRegistroAtendimento);

			if(existe){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.registro_atendimento.existe_os_pendente");
			}

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 14/08/2006
	 */
	public void validarServicoTipo(Integer idRegistroAtendimento, Integer idServicoTipo) throws ControladorException{

		try{

			// [FS0002] - Validar Tipo de Serviço

			// Caso 2
			OrdemServico os = repositorioOrdemServico.pesquisarOrdemServicoDiferenteEncerrado(idRegistroAtendimento, idServicoTipo);

			if(os != null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.servico_tipo.existe_os", null, new String[] {os.getId().toString(), os
								.getServicoTipo().getDescricao(), os.getRegistroAtendimento().getId().toString()});
			}

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0430] Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 17/08/2006
	 */
	public ServicoTipo pesquisarSevicoTipo(Integer id) throws ControladorException{

		try{
			return repositorioOrdemServico.pesquisarSevicoTipo(id);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 18/08/2006
	 */
	public void validarOrdemServico(OrdemServico ordemServico) throws ControladorException{

		// [FS0003] - Validar Ordem de Serviço

		if(ordemServico.getServicoTipo() != null
						&& ordemServico.getServicoTipo().getServicoTipoReferencia() != null
						&& ordemServico.getServicoTipo().getServicoTipoReferencia().getIndicadorExistenciaOsReferencia() == ConstantesSistema.SIM){

			if(ordemServico.getOsReferencia() == null){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.campo.informado", null, "Ordem de Serviço Referência");
			}
			if(ordemServico.getRegistroAtendimento() != null){

				// Caso 1
				FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
				filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, ordemServico.getOsReferencia().getId()));

				filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.REGISTRO_ATENDIMENTO_ID, ordemServico
								.getRegistroAtendimento().getId()));

				OrdemServico os = this.pesquisar(filtroOrdemServico, OrdemServico.class);

				if(os == null){
					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.os_referencia_pertence_outra_ra");
				}
			}

			// Caso 2
			if(ordemServico.getServicoTipo().getServicoTipoReferencia().getServicoTipo() != null
							&& !ordemServico.getServicoTipo().getServicoTipoReferencia().getServicoTipo().getId()
											.equals(ordemServico.getServicoTipo().getId())){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.servico_tipo_os.incompativel", null, ordemServico.getServicoTipo()
								.getServicoTipoReferencia().getDescricao());
			}

			// Caso 3
			if(ordemServico.getOsReferencia().getSituacao() != ordemServico.getServicoTipo().getServicoTipoReferencia()
							.getSituacaoOsReferenciaAntes().shortValue()){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.situacao_os.incompativel.situacao_os_requerida");
			}
		}
	}

	/**
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * [FS0001] - Validar Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 29/01/2007
	 */
	public void validarOrdemServicoInformarRetornoOrdemFiscalizacao(OrdemServico ordemServico) throws ControladorException{

		// 1º Situação

		/*
		 * Caso o serviço associado à Ordem de Serviço não corresponda a um
		 * serviço de fiscalização de infração (SVTP_ICFISCALIZACAOINFRACAO da
		 * tabela SERVICO_TIPO com SVTP_ID = SVTP_ID da tabela ORDEM_SERVICO com
		 * valor correspondente a (Não) –2), exibir a mensagem “O serviço
		 * associado a esta ordem de serviço não corresponde a fiscalização de
		 * infração”.
		 */
		if(ordemServico.getServicoTipo().getIndicadorFiscalizacaoInfracao() == ConstantesSistema.NAO){

			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.os.servico_tipo_nao_fiscalizacao");
		}

		// 2º Situação

		/*
		 * Caso a Ordem de Serviço esteja na situação de encerrada
		 * (ORSE_CDSITUAÇÃO na tabela ORDEM_SERVICO com o valor diferente de
		 * “Pendente” (1)), exibir a mensagem “Esta Ordem de Serviço está <<descrição
		 * da situação >>” e retornar para o passo correspondente no fluxo
		 * principal
		 */
		if(ordemServico.getSituacao() != OrdemServico.SITUACAO_PENDENTE){

			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.os.encerrada");
		}

		// 3º Situação

		/*
		 * Caso a Ordem de Serviço não esteja associada a um documento de
		 * cobrança (CBDO_ID da tabela ORDEM_SERVICO com valor igual a NULO),
		 * exibir a mensagem “A ordem de serviço não está associada a um
		 * documento de cobrança” e retornar para o passo correspondente no
		 * fluxo principal.
		 */
		// if (ordemServico.getCobrancaDocumento() == null) {
		//
		// sessionContext.setRollbackOnly();
		// throw new ControladorException(
		// "atencao.os.nao_associada_cobranca_documento");
		// }
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * Mudar no método gerarOrdemServicoSemValidacao, gerarOrdemServicoSeletiva caso aconteça alguma
	 * mudança nesse método
	 * 
	 * @author lms
	 * @date 18/08/2006
	 * @author eduardo henrique
	 * @date 15/09/2009
	 *       Alteracao na assinatura do método de Geração de Ordens de Serviço para receber list de
	 *       Servicos Autorizados pelo usuário (somente aqueles que precisam de autorizacao)
	 * @author Saulo Lima
	 * @date 27/06/2012
	 *       Alterações para gerar tramite de OS, quando for o caso, independente da RA.
	 * @param OrdemServico
	 * @param Usuario
	 * @param Map
	 *            <Integer, ServicoAssociadoAutorizacaoHelper>
	 * @return Integer
	 *         idOrdemServicoGerada
	 * @throws ControladorException
	 */
	public Integer gerarOrdemServico(OrdemServico ordemServico, Usuario usuario,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos, Integer idLocalidade, Integer idSetorComercial,
					Integer idBairro, Integer idUnidadeOrigem, Integer idUnidadeDestino, String parecerUnidadeDestino,
					String idOSPrincipal, Boolean forcarGerarOS, Short qtdPrestacaoGuiaPagamento)
					throws ControladorException{

		try{

			// [FS0003] - Validar Ordem de Serviço
			this.validarOrdemServico(ordemServico);

			// [FS0010] - Verificar restrição de emissão da Ordem de Serviço
			this.verificarRestricaoEmissaoOrdemServico(usuario, ordemServico, ConstantesSistema.NAO);

			Integer idOrdemServicoGerada = null;
			Integer idServicoTipo = null;
			Integer idRegistroAtendimento = null;
			
			if (ordemServico != null && ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getId() != null) {
				idServicoTipo = ordemServico.getServicoTipo().getId();
			}

			if(ordemServico != null && ordemServico.getRegistroAtendimento() != null
							&& ordemServico.getRegistroAtendimento().getId() != null){
				idRegistroAtendimento = ordemServico.getRegistroAtendimento().getId();
			}

			// Verificar se o Tipo de Servico é de Pagamento Antecipado
			// sendo o mesmo irá criar uma Guia de Pagamento paa que após o pagamento desta Guia
			// seja criada a ordem de servico
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoTipo.DEBITOTIPO);
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));

			ServicoTipo servicoTipoAtual = (ServicoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroServicoTipo,
							ServicoTipo.class.getName()));

			Boolean flagGerarOrdemServico = null;
			if(forcarGerarOS == null || forcarGerarOS.equals(false)){
				flagGerarOrdemServico = !this.validarGerarGuiaPagamentoOS(idServicoTipo, idRegistroAtendimento);
			}else{
				flagGerarOrdemServico = true;
			}

			if(flagGerarOrdemServico){

				// [SB0004] - Gerar Ordem de serviço
				idOrdemServicoGerada = this.inserirOrdemServico(ordemServico, usuario);

				if(ordemServico.getServicoTipo().getServicoTipoReferencia() != null
								&& ordemServico.getServicoTipo().getServicoTipoReferencia().getIndicadorExistenciaOsReferencia() == ConstantesSistema.SIM){

					OrdemServico osReferencia = ordemServico.getOsReferencia();
					osReferencia.setSituacao(OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO);

					this.getControladorUtil().atualizar(osReferencia);
				}

				// verifica se fará gerações de Serviços Associados
				ServicoTipo servicoTipoOS = null;
				try{
					servicoTipoOS = repositorioOrdemServico.pesquisarServicoTipoPorId(ordemServico.getServicoTipo().getId());
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				if(servicoTipoOS.getServicosAssociados() != null && !servicoTipoOS.getServicosAssociados().isEmpty()){
					ordemServico.setServicoTipo(servicoTipoOS);
					this.gerarServicosAssociadosOrdemServico(ordemServico, usuario, EventoGeracaoServico.GERACAO_ORDEM_SERVICO, null,
									autorizacoesServicos);
				}

				this.gerarTramiteOrdemServico(idLocalidade, idSetorComercial, idBairro, idUnidadeOrigem, idUnidadeDestino,
								parecerUnidadeDestino, usuario, ordemServico);

				this.validarAtualizarOrdemServicoReparo(idOrdemServicoGerada, idOSPrincipal, usuario);
			}else{
				// Obter a quantidade de Dias para o Calculo do Vencimento
				Calendar dataVencimento = Calendar.getInstance();
				String parametroDiasCalculoVencimento = (String) ParametroFaturamento.P_NUMERO_DIAS_CALCULO_VENCIMENTO_GUIA_COBRANCA_SERVICO_ANTECIPADO
								.executar();

				Integer qtdeDiasVencimento = 0;
				if(parametroDiasCalculoVencimento != null){
					dataVencimento.add(Calendar.DAY_OF_YEAR, Integer.valueOf(parametroDiasCalculoVencimento));

					qtdeDiasVencimento = Integer.valueOf(parametroDiasCalculoVencimento);
				}

				// Criando a GuiaPagamento
				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamento.setImovel(ordemServico.getImovel());

				if(ordemServico.getRegistroAtendimento() != null && ordemServico.getRegistroAtendimento().getId() != null){
					FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
					filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(
									FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID, ordemServico.getRegistroAtendimento()
													.getId()));

					RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) Util
									.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroRegistroAtendimentoSolicitante,
													RegistroAtendimentoSolicitante.class.getName()));
					if(registroAtendimentoSolicitante != null){
						guiaPagamento.setCliente(registroAtendimentoSolicitante.getCliente());
					}
				}
				
				// Incluir as Prestações da Guia de Pagamento
				Collection<ServicoDebitoTipo> colecaoServicoDebitoTipo = new ArrayList<ServicoDebitoTipo>();
				
				if (servicoTipoAtual.getDebitoTipo() != null) {
					ServicoDebitoTipo servicoDebitoTipo = new ServicoDebitoTipo();
					
					servicoDebitoTipo.setDebitoTipo(servicoTipoAtual.getDebitoTipo());
					servicoDebitoTipo.setServicoTipo(servicoTipoAtual);
					
					servicoDebitoTipo.setNumeroPrestacoes(servicoTipoAtual.getNumeroMaximoGuiaPrestacaoAntecipadaPermitidas());
					servicoDebitoTipo.setValorServicoDebitoTipo(servicoTipoAtual.getValor());
					
					colecaoServicoDebitoTipo.add(servicoDebitoTipo);
					
				} else {
					FiltroServicoDebitoTipo filtroServicoDebitoTipo = new FiltroServicoDebitoTipo();
					filtroServicoDebitoTipo.adicionarParametro(new ParametroSimples(FiltroServicoDebitoTipo.ID_SERVICO_TIPO,
									servicoTipoAtual.getId()));
					filtroServicoDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoDebitoTipo.DEBITO_TIPO);
					
					colecaoServicoDebitoTipo = getControladorUtil().pesquisar(filtroServicoDebitoTipo, ServicoDebitoTipo.class.getName());
				}
				
				// Criando as Prestações
				Short numeroTotalPrestacoesASerGeradas = 0;
				Short numeroTotalPrestacoesPesquisada = 0;
				Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacao = new ArrayList<GuiaPagamentoPrestacaoHelper>();				
				Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacaoGuia = new ArrayList<ListaDadosPrestacaoGuiaHelper>();

				for(ServicoDebitoTipo servicoDebitoTipo : colecaoServicoDebitoTipo){
					Short numeroTotalGuiaPrestacao = 1;
					if(servicoDebitoTipo.getNumeroPrestacoes() != null){
						numeroTotalGuiaPrestacao = servicoDebitoTipo.getNumeroPrestacoes();

						if(numeroTotalPrestacoesPesquisada.compareTo(numeroTotalGuiaPrestacao) < 0){
							numeroTotalPrestacoesPesquisada = numeroTotalGuiaPrestacao;
						}
					}
					
					if(qtdPrestacaoGuiaPagamento != null){
						if(servicoDebitoTipo.getNumeroPrestacoes() != null
										&& qtdPrestacaoGuiaPagamento.compareTo(servicoDebitoTipo.getNumeroPrestacoes()) > 0){
							numeroTotalGuiaPrestacao = servicoDebitoTipo.getNumeroPrestacoes();
						}else{
							numeroTotalGuiaPrestacao = qtdPrestacaoGuiaPagamento;
						}
					}

					if(numeroTotalPrestacoesASerGeradas.compareTo(numeroTotalGuiaPrestacao) <= 0){
						numeroTotalPrestacoesASerGeradas = (short) (numeroTotalGuiaPrestacao);
					}
	
					guiaPagamento.setRegistroAtendimento(ordemServico.getRegistroAtendimento());
					guiaPagamento.setNumeroPrestacaoTotal(numeroTotalGuiaPrestacao);
					guiaPagamento.setIndicadorEmissaoObservacaoRA((short) 2);
					guiaPagamento.setServicoTipo(ordemServico.getServicoTipo());
	
					OrdemServico ordemServicoGuiaPagamento = new OrdemServico();
					guiaPagamento.setOrdemServico(ordemServicoGuiaPagamento);
	
					BigDecimal valorPrestacao = servicoDebitoTipo.getValorServicoDebitoTipo().divide(new BigDecimal(numeroTotalGuiaPrestacao.toString()), 2,
									RoundingMode.DOWN);
					BigDecimal valorPrestacaoResto = servicoDebitoTipo.getValorServicoDebitoTipo().subtract( valorPrestacao.multiply(new BigDecimal(numeroTotalGuiaPrestacao.toString())));
									
					for(int numeroPrestacaoAtual = 1; numeroTotalGuiaPrestacao >= numeroPrestacaoAtual; numeroPrestacaoAtual++){
	
						GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacao = new GuiaPagamentoPrestacaoHelper();
						guiaPagamentoPrestacao.setDescricaoTipoDebito(servicoDebitoTipo.getDebitoTipo().getDescricao());
						guiaPagamentoPrestacao
										.setIdItemLancamentoContabil(servicoDebitoTipo.getDebitoTipo().getLancamentoItemContabil().getId());
						guiaPagamentoPrestacao.setUltimaAlteracao(new Date());
	
						if (numeroPrestacaoAtual == numeroTotalGuiaPrestacao) {
							guiaPagamentoPrestacao.setValorPrestacaoTipoDebito(valorPrestacao.add(valorPrestacaoResto));
							guiaPagamentoPrestacao.setValorTipoDebito(valorPrestacao.add(valorPrestacaoResto));
						}else{
							guiaPagamentoPrestacao.setValorPrestacaoTipoDebito(valorPrestacao);
							guiaPagamentoPrestacao.setValorTipoDebito(valorPrestacao);
						}
						
						colecaoGuiaPagamentoPrestacao.add(guiaPagamentoPrestacao);
					}
	
					Calendar dataVencimentoPrestacoes = Calendar.getInstance();
					dataVencimentoPrestacoes.setTime(dataVencimento.getTime());
					for(int numeroPrestacaoAtual = 1; numeroTotalGuiaPrestacao >= numeroPrestacaoAtual; numeroPrestacaoAtual++){
						ListaDadosPrestacaoGuiaHelper listaDadosPrestacaoGuia = null;
	
						for(ListaDadosPrestacaoGuiaHelper dadosPrestacaoGuiaHelper : colecaoListaDadosPrestacaoGuia){
							if(dadosPrestacaoGuiaHelper.getPrestacao().intValue() == numeroPrestacaoAtual){
								listaDadosPrestacaoGuia = dadosPrestacaoGuiaHelper;
							}
						}

						Boolean bflagNovoItem = false;
						if(listaDadosPrestacaoGuia == null){
							listaDadosPrestacaoGuia = new ListaDadosPrestacaoGuiaHelper();
							listaDadosPrestacaoGuia.setPrestacao(numeroPrestacaoAtual);
							listaDadosPrestacaoGuia.setDataVencimentoPrestacao(dataVencimentoPrestacoes.getTime());

							bflagNovoItem = true;
						}

						Map<Integer, BigDecimal> mapValorDebitoNaPrestacaoPorTipoDebito = listaDadosPrestacaoGuia
										.getMapValorDebitoNaPrestacaoPorTipoDebito();
	
						if(numeroPrestacaoAtual == numeroTotalGuiaPrestacao){
							mapValorDebitoNaPrestacaoPorTipoDebito.put(servicoDebitoTipo.getDebitoTipo().getId(),
											valorPrestacao.add(valorPrestacaoResto));
						}else{
							mapValorDebitoNaPrestacaoPorTipoDebito.put(servicoDebitoTipo.getDebitoTipo().getId(), valorPrestacao);
						}
	
						Map<Integer, Integer> mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito = new HashedMap();
	
						listaDadosPrestacaoGuia.setMapValorDebitoNaPrestacaoPorTipoDebito(mapValorDebitoNaPrestacaoPorTipoDebito);
						listaDadosPrestacaoGuia
										.setMapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito(mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito);
	
						if(bflagNovoItem){
							colecaoListaDadosPrestacaoGuia.add(listaDadosPrestacaoGuia);
							dataVencimentoPrestacoes.add(Calendar.MONTH, 1);
						}
					}
				}

				if (colecaoGuiaPagamentoPrestacao.size() > 0 && colecaoListaDadosPrestacaoGuia.size()> 0 ) {
					if(qtdPrestacaoGuiaPagamento != null){
						if(qtdPrestacaoGuiaPagamento.compareTo(numeroTotalPrestacoesPesquisada) > 0){
							throw new ControladorException("atencao.ordem_servico.limite_prestacao_guia_pagamento", null,
											servicoTipoAtual.getDescricao(), numeroTotalPrestacoesPesquisada.toString());
						}
					}

					SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");
	
					FiltroUsuario filtroUsuario = new FiltroUsuario();
	
					// Busca o usuário por senha e login
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.GERENCIA_REGIONAL);
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.LOCALIDADE_ELO);
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.LOCALIDADE);
	
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.USUARIO_SITUACAO);
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.USUARIO_TIPO);
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.UNIDADE_ORGANIZACIONAL);
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.FUNCIONARIO);
					filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.EMPRESA);
	
					filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, usuario.getId()));
	
					Usuario usuarioGuiaPagamento = (Usuario) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroUsuario,
									Usuario.class.getName()));

					guiaPagamento.setNumeroPrestacaoTotal(numeroTotalPrestacoesASerGeradas);
	
					String numeroContratoParcelOrgaoPublico = null;
					idOrdemServicoGerada = (Integer) this.getControladorFaturamento().inserirGuiaPagamento(guiaPagamento, usuarioGuiaPagamento,
									formataData.format(dataVencimento.getTime()).toString(), qtdeDiasVencimento, colecaoGuiaPagamentoPrestacao,
									colecaoListaDadosPrestacaoGuia, numeroContratoParcelOrgaoPublico);
				}
			}

			return idOrdemServicoGerada;

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}
	}

	public Collection<Integer> gerarOrdemServicoAPartirGuiaPagamento(Collection<GuiaPagamentoHistorico> colecaoGuiaPagamentoHistorico)
					throws ControladorException{

		try{
			Collection<Integer> colecaoIdOS = new ArrayList<Integer>();

			String paramUsuarioBatch = ParametroGeral.P_USUARIO_BATCH.executar();

			FiltroUsuario filtroUsuario = new FiltroUsuario();
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, Util.obterInteger(paramUsuarioBatch)));

			Collection colUsuarios = getControladorUtil().pesquisar(filtroUsuario, Usuario.class.getName());
			Usuario usuario = (Usuario) colUsuarios.iterator().next();

			for(GuiaPagamentoHistorico guiaPagamentoHistoricoAtual : colecaoGuiaPagamentoHistorico){
				Integer idServicoTipo = null;
				Integer idtRegistroAtendimento = null;
				Boolean indicadorGuiaPagamentoHistorico = null;
				Boolean indicadorexisteOSGuiaPagamento = false;
				Integer idGuiaPagamento = null;
				Imovel imovelPesquisado = null;
				Cliente clientePesquisado = null;

				FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
				filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.REGISTRO_ATENDIMENTO);
				filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.SERVICO_TIPO);
				filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.DEBITO_TIPO);
				filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.CLIENTE);
				filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.IMOVEL);
				
				filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.ID, guiaPagamentoHistoricoAtual.getId()));

				GuiaPagamento guiaPagamentoPesquisado = null;
				GuiaPagamentoHistorico guiaPagamentoHistoricoPesquisado = (GuiaPagamentoHistorico) Util
								.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroGuiaPagamentoHistorico,
												GuiaPagamentoHistorico.class.getName()));

				if (guiaPagamentoHistoricoPesquisado != null && guiaPagamentoHistoricoPesquisado.getOrdemServico() != null ) {
					indicadorexisteOSGuiaPagamento	= true;				
				}
				
				if(guiaPagamentoHistoricoPesquisado != null && guiaPagamentoHistoricoPesquisado.getRegistroAtendimento() != null
								&& guiaPagamentoHistoricoPesquisado.getRegistroAtendimento().getId() != null
								&& guiaPagamentoHistoricoPesquisado.getServicoTipo() != null
								&& guiaPagamentoHistoricoPesquisado.getServicoTipo().getId() != null){
					idServicoTipo = guiaPagamentoHistoricoPesquisado.getServicoTipo().getId();
					idtRegistroAtendimento = guiaPagamentoHistoricoPesquisado.getRegistroAtendimento().getId();
					indicadorGuiaPagamentoHistorico = true;
					idGuiaPagamento = guiaPagamentoHistoricoPesquisado.getId();
					imovelPesquisado = guiaPagamentoHistoricoPesquisado.getImovel();
					clientePesquisado = guiaPagamentoHistoricoPesquisado.getCliente();
				}else{
					FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
					filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamento.REGISTRO_ATENDIMENTO);
					filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamento.SERVICO_TIPO);
					filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamento.DEBITO_TIPO);
					filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamento.CLIENTE);
					filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamento.IMOVEL);

					filtroGuiaPagamento
									.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID, guiaPagamentoHistoricoAtual.getId()));

					guiaPagamentoPesquisado = (GuiaPagamento) Util.retonarObjetoDeColecao(getControladorUtil()
									.pesquisar(filtroGuiaPagamento, GuiaPagamento.class.getName()));

					if (guiaPagamentoPesquisado != null && guiaPagamentoPesquisado.getOrdemServico() != null ) {
						indicadorexisteOSGuiaPagamento	= true;				
					}
					
					if(guiaPagamentoPesquisado != null && guiaPagamentoPesquisado.getRegistroAtendimento() != null
									&& guiaPagamentoPesquisado.getRegistroAtendimento().getId() != null
									&& guiaPagamentoPesquisado.getServicoTipo() != null
									&& guiaPagamentoPesquisado.getServicoTipo().getId() != null){
						idServicoTipo = guiaPagamentoPesquisado.getServicoTipo().getId();
						idtRegistroAtendimento = guiaPagamentoPesquisado.getRegistroAtendimento().getId();
						indicadorGuiaPagamentoHistorico = false;
						idGuiaPagamento = guiaPagamentoPesquisado.getId();
						imovelPesquisado = guiaPagamentoPesquisado.getImovel();
						clientePesquisado = guiaPagamentoPesquisado.getCliente();
					}
				}

				Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico = new ArrayList<GuiaPagamentoPrestacaoHistorico>();

				if(idtRegistroAtendimento != null && idServicoTipo != null && idGuiaPagamento != null){
								
					// Verifica se todas as prestacoes foram pagas dentro do prazo
					FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
					filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroGuiaPagamentoPrestacaoHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
					filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroGuiaPagamentoPrestacaoHistorico.INDICADOR_PAGAMENTO, ConstantesSistema.SIM));

					colecaoGuiaPagamentoPrestacaoHistorico = getControladorUtil().pesquisar(filtroGuiaPagamentoPrestacaoHistorico,
									GuiaPagamentoPrestacaoHistorico.class.getName());

					Boolean bFlagPagamentoDentroPrazo = true;
					Boolean bFlagExisteUmPagamentoDentroPrazo = false;
					for(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistoricoAtual : colecaoGuiaPagamentoPrestacaoHistorico){
						
						FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
						filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
										guiaPagamentoPrestacaoHistoricoAtual.getComp_id().getGuiaPagamentoId()));
						filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.NUMERO_PRESTACAO,
										guiaPagamentoPrestacaoHistoricoAtual.getComp_id().getNumeroPrestacao()));
						
						PagamentoHistorico pagamentoPesquisado = (PagamentoHistorico) Util.retonarObjetoDeColecao(getControladorUtil()
										.pesquisar(filtroPagamentoHistorico, PagamentoHistorico.class.getName()));

						if(pagamentoPesquisado != null){
							if(pagamentoPesquisado.getDataPagamento().after(guiaPagamentoPrestacaoHistoricoAtual.getDataVencimento())){
								bFlagPagamentoDentroPrazo = false;
							}else{
								bFlagExisteUmPagamentoDentroPrazo = true;
							}
						}
					}

					FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
					filtroRegistroAtendimento
									.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, idtRegistroAtendimento));

					filtroRegistroAtendimento
									.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
					filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO);
					filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.IMOVEL);
					filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
					filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
					filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");

					RegistroAtendimento registroAtendimentoPesquisado = (RegistroAtendimento) Util
									.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroRegistroAtendimento,
													RegistroAtendimento.class.getName()));

					// if(bFlagPagamentoDentroPrazo){
					Integer idLocalidade = null;
					Integer idSetorComercial = null;

					// Pesquisa para ver se já existe uma OS para Guia de pagamento
					if(!indicadorexisteOSGuiaPagamento){
						Collection<Integer> colecaoIdServicoTipo = this
										.consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(registroAtendimentoPesquisado
														.getSolicitacaoTipoEspecificacao().getId());

						Integer idSolicitacaoTipo = registroAtendimentoPesquisado.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo()
										.getId();
						Integer idSolicitacaoTipoEspecificacao = registroAtendimentoPesquisado.getSolicitacaoTipoEspecificacao().getId();

						Collection<OrdemServico> colecaoOsGeradaAutomatica = null;
						colecaoOsGeradaAutomatica = getControladorRegistroAtendimento().gerarColecaoOrdemServicoAutomatica(
										colecaoIdServicoTipo, idSolicitacaoTipo);

						if(getControladorRegistroAtendimento().gerarOrdemServicoAutomatica(idSolicitacaoTipoEspecificacao)
										&& !Util.isVazioOrNulo(colecaoOsGeradaAutomatica)){
							Imovel imovel = null;
							Integer idOrdemServico = null;

							for(OrdemServico osGeradaAutomatica : colecaoOsGeradaAutomatica){
								// O Imóvel da OS será o mesmo do RA
								imovel = registroAtendimentoPesquisado.getImovel();

								if(imovel != null){
									osGeradaAutomatica.setImovel(imovel);
								}

								osGeradaAutomatica.setRegistroAtendimento(registroAtendimentoPesquisado);

								Object[] dadosDaRA = getControladorRegistroAtendimento().pesquisarDadosLocalizacaoRegistroAtendimento(
												registroAtendimentoPesquisado.getId());
								Integer idBairro = null;
								if(dadosDaRA != null){
									if(idLocalidade == null){
										idLocalidade = (Integer) dadosDaRA[0];
									}
									if(idSetorComercial == null){
										idSetorComercial = (Integer) dadosDaRA[1];
									}
									idBairro = (Integer) dadosDaRA[2];
								}

								FiltroTramite filtroTramite = new FiltroTramite();
								filtroTramite.adicionarParametro(new ParametroSimples(FiltroTramite.REGISTRO_ATENDIMENTO_ID,
												registroAtendimentoPesquisado.getId()));
								filtroTramite.adicionarCaminhoParaCarregamentoEntidade(FiltroTramite.UNIDADE_ORGANIZACIONAL_ORIGEM);
								filtroTramite.adicionarCaminhoParaCarregamentoEntidade(FiltroTramite.UNIDADE_ORGANIZACIONAL_DESTINO);
								filtroTramite.setCampoOrderByDesc(FiltroTramite.ID);

								Tramite tramitePesquisado = (Tramite) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
												filtroTramite, Tramite.class.getName()));

								Integer idUnidadeAtendimento = tramitePesquisado.getUnidadeOrganizacionalOrigem().getId();
								Integer idUnidadeDestino = tramitePesquisado.getUnidadeOrganizacionalDestino().getId();
								String parecerUnidadeDestino = tramitePesquisado.getParecerTramite();

								// Gerar a Ordem de Servico
								idOrdemServico = this.gerarOrdemServico(osGeradaAutomatica, usuario, null, idLocalidade, idSetorComercial,
												idBairro, idUnidadeAtendimento, idUnidadeDestino, parecerUnidadeDestino, null, true, null);

								colecaoIdOS.add(idOrdemServico);

								// Atualizar a Guia de Pagamento
								OrdemServico ordemServicoGerada = new OrdemServico();
								ordemServicoGerada.setId(idOrdemServico);

								if(indicadorGuiaPagamentoHistorico){
									guiaPagamentoHistoricoPesquisado.setOrdemServico(ordemServicoGerada);
									getControladorUtil().atualizar(guiaPagamentoHistoricoPesquisado);
								}else{
									guiaPagamentoPesquisado.setOrdemServico(ordemServicoGerada);
									getControladorUtil().atualizar(guiaPagamentoPesquisado);
								}

							}
						}
					}
					/*
					 * else{
					 * if(!indicadorexisteOSGuiaPagamento){
					 * FiltroPagamentoHistorico filtroPagamentoHistorico = new
					 * FiltroPagamentoHistorico();
					 * filtroPagamentoHistorico.adicionarParametro(new
					 * ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID,
					 * idGuiaPagamento));
					 * Collection<PagamentoHistorico> colecaoPagamentoHistorico =
					 * getControladorUtil().pesquisar(
					 * filtroPagamentoHistorico, PagamentoHistorico.class.getName());
					 * for(PagamentoHistorico pagamentoHistorico : colecaoPagamentoHistorico){
					 * if(imovelPesquisado != null){
					 * CreditoARealizar creditoARealizar = new CreditoARealizar();
					 * // Criando o objeto Crédito Tipo
					 * CreditoTipo creditoTipo = new CreditoTipo();
					 * creditoTipo.setId(CreditoTipo.DEVSERVICOFRPRZ);
					 * creditoARealizar.setCreditoTipo(creditoTipo);
					 * // Criando o objeto Crédito Origem
					 * CreditoOrigem creditoOrigem = new CreditoOrigem();
					 * creditoOrigem.setId(CreditoOrigem.DEVOLUCAO_SERVICO);
					 * creditoARealizar.setCreditoOrigem(creditoOrigem);
					 * // Criando o objeto Registro Atendimento
					 * creditoARealizar.setRegistroAtendimento(registroAtendimentoPesquisado);
					 * creditoARealizar.setOrdemServico(null);
					 * creditoARealizar.setAnoMesReferenciaCredito(pagamentoHistorico.
					 * getAnoMesReferenciaPagamento());
					 * creditoARealizar.setNumeroPrestacaoCredito(Short.valueOf("1"));
					 * // Setando as informações do Imóvel
					 * creditoARealizar.setImovel(registroAtendimentoPesquisado.getImovel());
					 * creditoARealizar.setCodigoSetorComercial(registroAtendimentoPesquisado.getImovel
					 * ().getSetorComercial()
					 * .getCodigo());
					 * creditoARealizar.setNumeroQuadra(new
					 * Integer(registroAtendimentoPesquisado.getImovel().getQuadra()
					 * .getNumeroQuadra()));
					 * creditoARealizar.setNumeroLote(registroAtendimentoPesquisado.getImovel().getLote
					 * ());
					 * creditoARealizar.setNumeroSubLote(registroAtendimentoPesquisado.getImovel().
					 * getSubLote());
					 * creditoARealizar.setQuadra(registroAtendimentoPesquisado.getImovel().getQuadra
					 * ());
					 * creditoARealizar.setLocalidade(registroAtendimentoPesquisado.getImovel().
					 * getLocalidade());
					 * creditoARealizar.setValorCredito(pagamentoHistorico.getValorPagamento());
					 * creditoARealizar.setPagamentoHistorico(pagamentoHistorico);
					 * getControladorFaturamento().inserirCreditoARealizar(registroAtendimentoPesquisado
					 * .getImovel(),
					 * creditoARealizar, usuario, false);
					 * }else if(clientePesquisado != null){
					 * GuiaDevolucao guiaDevolucao = new GuiaDevolucao();
					 * guiaDevolucao.setCliente(clientePesquisado);
					 * guiaDevolucao.setRegistroAtendimento(registroAtendimentoPesquisado);
					 * DocumentoTipo documentoTipo = new DocumentoTipo();
					 * documentoTipo.setId(DocumentoTipo.GUIA_PAGAMENTO);
					 * guiaDevolucao.setDocumentoTipo(documentoTipo);
					 * GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();
					 * guiaPagamentoGeral.setId(idGuiaPagamento);
					 * guiaDevolucao.setGuiaPagamentoGeral(guiaPagamentoGeral);
					 * for(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistoricoAtual :
					 * colecaoGuiaPagamentoPrestacaoHistorico){
					 * if(guiaPagamentoPrestacaoHistoricoAtual.getComp_id().getNumeroPrestacao().
					 * toString()
					 * .equals(pagamentoHistorico.getNumeroPrestacao().toString())){
					 * DebitoTipo debitoTipo = new DebitoTipo();
					 * debitoTipo.setId(guiaPagamentoPrestacaoHistoricoAtual.getDebitoTipo().getId())
					 * ;
					 * guiaDevolucao.setDebitoTipo(debitoTipo);
					 * }
					 * }
					 * guiaDevolucao.setValorDevolucao(pagamentoHistorico.getValorPagamento());
					 * guiaDevolucao.setNumeroPrestacao(Short.valueOf(pagamentoHistorico.
					 * getNumeroPrestacao().toString()));
					 * guiaDevolucao.setPagamentoHistorico(pagamentoHistorico);
					 * String paramFuncionarioAdministrador =
					 * ParametroGeral.P_FUNCIONARIO_ADMINISTRADOR_SISTEMA.executar();
					 * Funcionario funcionario = new Funcionario();
					 * funcionario.setId(new Integer(paramFuncionarioAdministrador));
					 * guiaDevolucao.setFuncionarioAnalista(funcionario);
					 * funcionario = new Funcionario();
					 * funcionario.setId(new Integer(paramFuncionarioAdministrador));
					 * guiaDevolucao.setFuncionarioAutorizador(funcionario);
					 * getControladorArrecadacao().inserirGuiaDevolucao(guiaDevolucao, usuario,
					 * false, true,
					 * CreditoTipo.DEVSERVICOFRPRZ);
					 * }
					 * }
					 * // Cancelar as outras prestacoes da Guia
					 * Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoes =
					 * getControladorFaturamento()
					 * .pesquisarGuiasPagamentoPrestacaoFiltrar(idGuiaPagamento);
					 * if(colecaoGuiasPrestacoes != null && colecaoGuiasPrestacoes.size() > 0){
					 * Collection<String> colecaoGuiasPrestacoesRemovidas = new ArrayList<String>();
					 * for(GuiaPagamentoPrestacaoHelper guiaPagamentoHelper :
					 * colecaoGuiasPrestacoes){
					 * FiltroGuiaPagamentoPrestacao filtroGuiaPagamentoPrestacao = new
					 * FiltroGuiaPagamentoPrestacao();
					 * filtroGuiaPagamentoPrestacao.adicionarParametro(new ParametroSimples(
					 * FiltroGuiaPagamentoPrestacao.GUIA_PAGAMENTO_ID, guiaPagamentoHelper
					 * .getIdGuiaPagamento()));
					 * filtroGuiaPagamentoPrestacao
					 * .adicionarParametro(new
					 * ParametroSimples(FiltroGuiaPagamentoPrestacao.NUMERO_PRESTACAO,
					 * guiaPagamentoHelper.getNumeroPrestacao()));
					 * GuiaPagamentoPrestacao guiaPagamentoPrestacaoPesquisada =
					 * (GuiaPagamentoPrestacao) Util
					 * .retonarObjetoDeColecao(getControladorUtil().pesquisar(
					 * filtroGuiaPagamentoPrestacao,
					 * GuiaPagamentoPrestacao.class.getName()));
					 * if(guiaPagamentoPrestacaoPesquisada != null){
					 * colecaoGuiasPrestacoesRemovidas.add(guiaPagamentoHelper.getIdGuiaPagamento().
					 * toString()
					 * + guiaPagamentoHelper.getNumeroPrestacao().toString());
					 * }
					 * }
					 * String[] registrosRemocao = new
					 * String[colecaoGuiasPrestacoesRemovidas.size()];
					 * int contLinha = 0;
					 * for(String guiaPagamentoRemovida : colecaoGuiasPrestacoesRemovidas){
					 * registrosRemocao[contLinha] = guiaPagamentoRemovida;
					 * contLinha = contLinha + 1;
					 * }
					 * if(contLinha > 0){
					 * getControladorFaturamento().cancelarGuiaPagamento(colecaoGuiasPrestacoes,
					 * registrosRemocao, true,
					 * usuario);
					 * }
					 * }
					 * // Encerra Registro de Atendimento
					 * FiltroRegistroAtendimentoUnidade filtroRegistroAtendimentoUnidade =
					 * new FiltroRegistroAtendimentoUnidade();
					 * filtroRegistroAtendimentoUnidade.adicionarParametro(new
					 * ParametroSimples(
					 * FiltroRegistroAtendimentoUnidade.REGISTRO_ATENDIMENTO_ID,
					 * idtRegistroAtendimento));
					 * RegistroAtendimentoUnidade registroAtendimentoUnidadePesquisado =
					 * (RegistroAtendimentoUnidade) Util
					 * .retonarObjetoDeColecao(getControladorUtil().pesquisar(
					 * filtroRegistroAtendimentoUnidade,
					 * RegistroAtendimentoUnidade.class.getName()));
					 * AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new
					 * AtendimentoMotivoEncerramento();
					 * atendimentoMotivoEncerramento.setId(AtendimentoMotivoEncerramento.
					 * CANCELADO_POR_DERCURSO_DE_PRAZO);
					 * registroAtendimentoPesquisado.setAtendimentoMotivoEncerramento(
					 * atendimentoMotivoEncerramento);
					 * getControladorRegistroAtendimento().encerrarRegistroAtendimento(
					 * registroAtendimentoPesquisado,
					 * registroAtendimentoUnidadePesquisado, usuario);
					 * }
					 * }
					 */
				}
			}
			
			return colecaoIdOS;

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}
	}

	protected ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);

			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	public Boolean validarGerarGuiaPagamentoOS(Integer idServicoTipo, Integer idRegistroAtendimento) throws ControladorException{

		Boolean flagGerarGuiaPagamento = false;

		// Verificar se o Tipo de Servico é de Pagamento Antecipado
		// sendo o mesmo irá criar uma Guia de Pagamento paa que após o pagamento desta Guia
		// seja criada a ordem de servico
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoTipo.DEBITOTIPO);
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));

		ServicoTipo servicoTipoAtual = (ServicoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
						filtroServicoTipo, ServicoTipo.class.getName()));

		if(servicoTipoAtual.getIndicadorPagamentoAntecipado() == 1 && idRegistroAtendimento != null){
			FiltroGuiaPagamento filtroGuiaPagamento = new FiltroGuiaPagamento();
			filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamento.SERVICO_TIPO);
			filtroGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamento.DEBITO_TIPO);

			filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.REGISTRO_ATENDIMENTO_ID, idRegistroAtendimento));
			
			if (servicoTipoAtual.getDebitoTipo() != null ) {
				filtroGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroGuiaPagamento.ID_DEBITO_TIPO, servicoTipoAtual
							.getDebitoTipo().getId()));
			} else {
				FiltroServicoDebitoTipo filtroServicoDebitoTipo = new FiltroServicoDebitoTipo();
				filtroServicoDebitoTipo.adicionarParametro(new ParametroSimples(FiltroServicoDebitoTipo.ID_SERVICO_TIPO,
								servicoTipoAtual.getId()));
				filtroServicoDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoDebitoTipo.DEBITO_TIPO);
				
				Collection<ServicoDebitoTipo> colecaoServicoDebitoTipo = getControladorUtil().pesquisar(filtroServicoDebitoTipo, ServicoDebitoTipo.class.getName());
				Collection<Integer> idsDebitoTipo = new ArrayList<Integer>();
				for(ServicoDebitoTipo servicoDebitoTipo : colecaoServicoDebitoTipo){
					idsDebitoTipo.add(servicoDebitoTipo.getDebitoTipo().getId() );
				}
				
				if(idsDebitoTipo.size() == 0){
					throw new ControladorException("atencao.informe_campo", null, " 1 (um) Tipo de Débito para este Tipo de Servico "
									+ servicoTipoAtual.getDescricao());
				}

				filtroGuiaPagamento.adicionarParametro(new ParametroSimplesColecao(FiltroGuiaPagamento.ID_DEBITO_TIPO, idsDebitoTipo));
			}

			Collection<GuiaPagamento> colecaoGuiaPagamento = getControladorUtil().pesquisar(filtroGuiaPagamento,
							GuiaPagamento.class.getName());
			if(colecaoGuiaPagamento.size() == 0){
				FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
				filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.SERVICO_TIPO);
				filtroGuiaPagamentoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.DEBITO_TIPO);
				filtroGuiaPagamentoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoHistorico.DEBITO_CREDITO_SITUACAO_ATUAL);

				filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.REGISTRO_ATENDIMENTO_ID,
								idRegistroAtendimento));
				
				if(servicoTipoAtual.getDebitoTipo() != null){
					filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.ID_DEBITO_TIPO,
									servicoTipoAtual.getDebitoTipo().getId()));
				}else{
					FiltroServicoDebitoTipo filtroServicoDebitoTipo = new FiltroServicoDebitoTipo();
					filtroServicoDebitoTipo.adicionarParametro(new ParametroSimples(FiltroServicoDebitoTipo.ID_SERVICO_TIPO,
									servicoTipoAtual.getId()));
					filtroServicoDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoDebitoTipo.DEBITO_TIPO);

					Collection<ServicoDebitoTipo> colecaoServicoDebitoTipo = getControladorUtil().pesquisar(filtroServicoDebitoTipo,
									ServicoDebitoTipo.class.getName());
					Collection<Integer> idsDebitoTipo = new ArrayList<Integer>();
					for(ServicoDebitoTipo servicoDebitoTipo : colecaoServicoDebitoTipo){
						idsDebitoTipo.add(servicoDebitoTipo.getDebitoTipo().getId());
					}

					if(idsDebitoTipo.size() == 0){
						throw new ControladorException("atencao.informe_campo", null, " 1 (um) Tipo de Débito para este Tipo de Servico "
										+ servicoTipoAtual.getDescricao());
					}

					filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimplesColecao(
									FiltroGuiaPagamentoHistorico.ID_DEBITO_TIPO, idsDebitoTipo));
				}

				Collection<GuiaPagamentoHistorico> colecaoGuiaPagamentoHistorico = getControladorUtil().pesquisar(filtroGuiaPagamentoHistorico,
								GuiaPagamentoHistorico.class.getName());					
				
				if(colecaoGuiaPagamentoHistorico.size() == 0){
					flagGerarGuiaPagamento = true;
				}else{
					GuiaPagamentoHistorico guiaPagamentoHistoricoAtual = (GuiaPagamentoHistorico) Util
									.retonarObjetoDeColecao(colecaoGuiaPagamentoHistorico);
					// Verifica se todas as prestações foram canceladas
					FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
					filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroGuiaPagamentoPrestacaoHistorico.GUIA_PAGAMENTO_ID, guiaPagamentoHistoricoAtual.getId()));
					filtroGuiaPagamentoPrestacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("debitoCreditoSituacao");

					Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoHistoricoPrestacao = getControladorUtil().pesquisar(
									filtroGuiaPagamentoPrestacaoHistorico, GuiaPagamentoPrestacaoHistorico.class.getName());

					flagGerarGuiaPagamento = true;
					for(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistoricoAtual : colecaoGuiaPagamentoHistoricoPrestacao){
						if(!guiaPagamentoPrestacaoHistoricoAtual.getDebitoCreditoSituacao().getId().equals(DebitoCreditoSituacao.CANCELADA)){
							flagGerarGuiaPagamento = false;
						}
					}
				}

			}else{
				GuiaPagamento guiaPagamentoPedente = (GuiaPagamento) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);

				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.ordem_servico.guia.pagamento.pedente", null, servicoTipoAtual.getDescricao(),
								idRegistroAtendimento.toString(), Util.formatarMoedaReal(guiaPagamentoPedente
												.getValorDebito()));
			}
		}
		
		
		return flagGerarGuiaPagamento;
	}

	private void validarAtualizarOrdemServicoReparo(Integer idOrdemServicoGerada, String idOSPrincipal, Usuario usuario)
					throws ControladorException{

		OrdemServico ordemServicoReparo = this.pesquisarOrdemServico(idOrdemServicoGerada);
		String idServicosTipoReparoString = ParametroOrdemServico.P_SERVICO_TIPO_REPARO.executar();
		if(idServicosTipoReparoString != null){

			String[] idServicosTipoReparo = idServicosTipoReparoString.split(",");
			if(idServicosTipoReparo != null && idServicosTipoReparo.length > 0){

				Map<String, String> mapaTipoServicoReparo = new HashMap<String, String>();
				for(int i = 0; i < idServicosTipoReparo.length; i++){
					mapaTipoServicoReparo.put(idServicosTipoReparo[i], idServicosTipoReparo[i]);
				}

				if(idOSPrincipal == null || idOSPrincipal.equals("-1")){
					if(mapaTipoServicoReparo.containsKey(String.valueOf(ordemServicoReparo.getServicoTipo().getId()))){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.os_reparo_obrigatorio_os_principal");
					}

				}else if(idOSPrincipal != null){

					if(!mapaTipoServicoReparo.containsKey(String.valueOf(ordemServicoReparo.getServicoTipo().getId()))){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.servico_tipo.nao_eh_reparo");
					}

					OrdemServico ordemServicoPrincipal = this.pesquisarOrdemServico(Integer.parseInt(idOSPrincipal));

					if(ordemServicoPrincipal.getOrdemServicoReparo() != null){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.ja_existe_os_reparo_os_principal");
					}

					ordemServicoPrincipal.setOrdemServicoReparo(ordemServicoReparo);
					this.atualizarOrdemServico(ordemServicoPrincipal, usuario);
				}

			}
		}

	}

	/**
	 * @author Eduardo Henrique
	 * @date 15/05/2009
	 * @author Saulo Lima
	 * @date 03/06/2009
	 *       Correções + Alteração para encerrar, caso se aplique.
	 * @param ordemServico
	 *            - instância com Collection<ServicoAssociado> de ServicoTipo populado [obrigatório]
	 * @param usuario
	 *            - Usuário que efetuou a operação [obrigatório]
	 * @param eventoGeracaoServico
	 *            - enum dos eventos possíveis para geração dos Serviços Associados. [obrigatório]
	 * @param origemEncerramento
	 *            - enum com a origem da ação de encerramento da OS efetuada pelo usuário.
	 * @param mapServicosAutorizados
	 *            - Map com beans <ServicoAssociadoAutorizacaoHelper>, com Servicos que necessitaram
	 *            de autorizacao.
	 * @throws ControladorException
	 *             - Em caso de erro na geração da Ordem de Serviço associada
	 */
	private void gerarServicosAssociadosOrdemServico(OrdemServico ordemServico, Usuario usuario, EventoGeracaoServico evento,
					OrigemEncerramentoOrdemServico origemEncerramento,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados) throws ControladorException{

		if(ordemServico == null || ordemServico.getServicoTipo() == null || ordemServico.getServicoTipo().getServicosAssociados() == null){
			throw new IllegalArgumentException("erro.referencia_os_geracao_nula");
		}

		Collection<ServicoAssociado> servicosAssociadosGeracao = new ArrayList<ServicoAssociado>();
		for(ServicoAssociado servicoAssociado : ordemServico.getServicoTipo().getServicosAssociados()){
			if(evento.valorId() == servicoAssociado.getEventoGeracao().getId().intValue()){

				servicosAssociadosGeracao.add(servicoAssociado);
			}
		}

		// map será usado na tramitação
		Map<Integer, OrdemServico> ordensAssociadasGeradas = new HashMap<Integer, OrdemServico>();

		for(ServicoAssociado servicoAssociado : servicosAssociadosGeracao){

			ServicoAssociadoAutorizacaoHelper autorizacaoServico = this.obterDadosAutorizacaoServicoAssociado(
							servicoAssociado.getServicoTipoAssociado(), mapServicosAutorizados);

			// Se o evento foi a Geração de Ordem de Serviço
			if(evento.compareTo(EventoGeracaoServico.GERACAO_ORDEM_SERVICO) == 0){
				// TODO this.gerarOrdemServicoAssociada(ordemServico,
				// servicoAssociado.getServicoTipo(), usuario);

			}else if(evento.compareTo(EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO) == 0){

				if(servicoAssociado.getFormaGeracao().getId().intValue() == FormaGeracao.FormaGeracaoServico.SOLICITA_AUTORIZACAO.valorId()){

					// verifica se o servico foi autorizado, caso não se encontre o servico no Map
					// lança exceção
					if(autorizacaoServico == null || !autorizacaoServico.isExecucaoAutorizada()){
						continue;
					}
				}else if(servicoAssociado.getFormaGeracao().getId().intValue() == FormaGeracao.FormaGeracaoServico.POSTERIOR.valorId()){
					continue;
				}

				// [se geração automática ou autorizada, efetua a geração da OS]
				OrdemServico ordemAssociada = this.gerarOrdemServicoAssociada(ordemServico, servicoAssociado.getServicoTipoAssociado(),
								usuario);

				// ----- Início Trâmite -----
				/*
				 * Só faz Tramitação caso:
				 * 1. Trâmite automático ou autorizado
				 * 2. Tenha Unidade Organizacional
				 * 3. Não haja nenhuma outra OS pendente pra RA
				 */
				boolean tramiteEfetuado = false;
				if(servicoAssociado.getFormaTramite().getId().intValue() == FormaTramite.FormaTramiteServico.AUTOMATICA.valorId()
								|| (servicoAssociado.getFormaTramite().getId().intValue() == FormaTramite.FormaTramiteServico.SOLICITA_AUTORIZACAO
												.valorId() && autorizacaoServico != null && autorizacaoServico.isTramiteAutorizado())){

					if(servicoAssociado.getUnidadeOrganizacional() != null){

						UnidadeOrganizacional unidadeAtual = new UnidadeOrganizacional();

						UnidadeOrganizacional unidadeOrigem = null;
						unidadeOrigem = this.obterUnidadeAtualOrdemServico(ordemServico.getId());

						Integer idUnidadeTramite = this.pesquisarUnidadeTramiteOS(ordemServico.getServicoTipo().getId(), null, null, null,
										unidadeOrigem.getId());

						if(idUnidadeTramite == null){
							idUnidadeTramite = unidadeOrigem.getId();
						}

						unidadeAtual.setId(idUnidadeTramite);

						Short permiteTramiteIndependente = Util.obterShort((String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE
										.executar(ExecutorParametrosAtendimentoPublico.getInstancia()));

						// Caso o trâmite da OS seja independente do RA
						if(permiteTramiteIndependente.equals(ConstantesSistema.SIM)){

							// Gera o Trâmite
							Tramite tramiteServicoAssociado = new Tramite();
							tramiteServicoAssociado.setUsuarioRegistro(usuario);
							tramiteServicoAssociado.setUsuarioResponsavel(usuario);
							tramiteServicoAssociado.setParecerTramite(ConstantesAplicacao.get("mensagem.texto_padrao_tramite_automarico"));
							tramiteServicoAssociado.setDataTramite(new Date());
							tramiteServicoAssociado.setUltimaAlteracao(new Date());

							unidadeAtual = getControladorUnidade().pesquisarUnidadeOrganizacional(unidadeAtual.getId());
							UnidadeOrganizacional unidadeDestino = getControladorUnidade().pesquisarUnidadeOrganizacional(
											servicoAssociado.getUnidadeOrganizacional().getId());

							tramiteServicoAssociado.setUnidadeOrganizacionalOrigem(unidadeAtual);
							tramiteServicoAssociado.setUnidadeOrganizacionalDestino(unidadeDestino);
							tramiteServicoAssociado.setOrdemServico(ordemAssociada);

							this.tramitarOS(tramiteServicoAssociado, new Date());
							tramiteEfetuado = true;

						}else{

							// Unidades Diferentes
							if(!servicoAssociado.getUnidadeOrganizacional().getId().equals(unidadeAtual.getId())){

								// Verifica se Ordem de Serviço Original é a única do Registro
								// de Atendimento na qual está vinculada, quando houver
								if(ordemServico.getRegistroAtendimento() != null){
									Collection<OrdemServico> colecaoOrdemServico = getControladorRegistroAtendimento().obterOSRA(
													ordemServico.getRegistroAtendimento().getId());

									if(colecaoOrdemServico == null){
										throw new ControladorException("atencao.registro_atendimento_ordem_associada_nao_existe");
									}

									// Valida apenas as não encerradas
									for(Iterator<OrdemServico> iterator = colecaoOrdemServico.iterator(); iterator.hasNext();){

										OrdemServico ordemRegistroAtendimento = (OrdemServico) iterator.next();

										// Se a ordem consultada foi a gerada no processo, também
										// desconsidera.
										if(ordemRegistroAtendimento.getSituacao() == OrdemServico.SITUACAO_ENCERRADO.shortValue()
														|| (ordensAssociadasGeradas.containsKey(ordemRegistroAtendimento.getId()))){

											iterator.remove();
										}
									}

									if(colecaoOrdemServico.size() == 1){

										// Gera o Trâmite
										Tramite tramiteServicoAssociado = new Tramite();
										tramiteServicoAssociado.setUsuarioRegistro(usuario);
										tramiteServicoAssociado.setUsuarioResponsavel(usuario);
										tramiteServicoAssociado.setParecerTramite(ConstantesAplicacao
														.get("mensagem.texto_padrao_tramite_automarico"));
										tramiteServicoAssociado.setDataTramite(new Date());
										tramiteServicoAssociado.setUltimaAlteracao(new Date());

										unidadeAtual = getControladorUnidade().pesquisarUnidadeOrganizacional(unidadeAtual.getId());
										UnidadeOrganizacional unidadeDestino = getControladorUnidade().pesquisarUnidadeOrganizacional(
														servicoAssociado.getUnidadeOrganizacional().getId());
										tramiteServicoAssociado.setUnidadeOrganizacionalOrigem(unidadeAtual);
										tramiteServicoAssociado.setUnidadeOrganizacionalDestino(unidadeDestino);

										RegistroAtendimento registroAtendimentoOrdemServico = getControladorRegistroAtendimento()
														.pesquisarRegistroAtendimento(ordemAssociada.getRegistroAtendimento().getId());
										tramiteServicoAssociado.setRegistroAtendimento(registroAtendimentoOrdemServico);

										this.getControladorRegistroAtendimento().validarTramitacao(tramiteServicoAssociado, usuario);
										this.getControladorRegistroAtendimento().tramitar(tramiteServicoAssociado, null);
										tramiteEfetuado = true;
									}
								}
							}
						}
					}
				}
				ordensAssociadasGeradas.put(ordemAssociada.getId(), ordemAssociada);
				// ----- Fim Trâmite -----

				// ----- Início Programação -----
				/*
				 * Só faz a Programação caso:
				 * 1. Não tenha tramitado
				 * 2. Programação seja automática ou autorizada
				 * 3. Tenha equipe informada
				 * 4. Tenha data informada
				 * 5. Origem de Encerramento da OS seja ACOMPANHAMENTO_ROTEIRO
				 */
				if(!tramiteEfetuado){

					if(servicoAssociado.getFormaProgramacao().getId().intValue() == FormaProgramacao.FormaProgramacaoServico.MESMO_DIA
									.valorId()
									|| servicoAssociado.getFormaProgramacao().getId().intValue() == FormaProgramacao.FormaProgramacaoServico.DIA_POSTERIOR
													.valorId()
									|| servicoAssociado.getFormaProgramacao().getId().intValue() == FormaProgramacao.FormaProgramacaoServico.SOLICITA_AUTORIZACAO
													.valorId()){

						// Caso não tenha uma Unidade Organizacional definida, pegar a atual na
						// tabela de Tramite
						if(servicoAssociado.getUnidadeOrganizacional() == null){

							UnidadeOrganizacional unidade = obterUnidadeDestinoUltimoTramite(ordemServico.getId());
							servicoAssociado.setUnidadeOrganizacional(unidade);
						}

						if(origemEncerramento.compareTo(OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO) == 0){

							Date dataProgramacaoOrdemAssociada = null;

							// verifica como será a programação
							if(servicoAssociado.getFormaProgramacao().getId().intValue() == FormaProgramacao.FormaProgramacaoServico.MESMO_DIA
											.valorId()){

								Calendar calendar = Calendar.getInstance();
								dataProgramacaoOrdemAssociada = calendar.getTime();

							}else if(servicoAssociado.getFormaProgramacao().getId().intValue() == FormaProgramacao.FormaProgramacaoServico.SOLICITA_AUTORIZACAO
											.valorId()){

								// Data de Programação do Servico foi informada
								if(autorizacaoServico != null){
									dataProgramacaoOrdemAssociada = autorizacaoServico.getDataProgramacaoInformada();
								}

							}else if(servicoAssociado.getFormaProgramacao().getId().intValue() == FormaProgramacao.FormaProgramacaoServico.DIA_POSTERIOR
											.valorId()){

								Calendar calendar = Calendar.getInstance();
								calendar.add(Calendar.DAY_OF_MONTH, 1);

								dataProgramacaoOrdemAssociada = calendar.getTime();
							}

							// verifica se houve definição de equipe
							Equipe equipeOsAssociada = null;
							if(servicoAssociado.getFormaSelecaoEquipe().getId().intValue() == FormaSelecaoEquipe.FormaSelecaoEquipeServico.ATUAL
											.valorId()){

								// Busca a programação da OS Original, caso não encontre, lança
								// exceção
								try{
									OrdemServicoProgramacao programacaoOrdemOriginal = repositorioOrdemServico
													.pesquisarOSProgramacaoAtivaPorOS(ordemServico.getId());
									equipeOsAssociada = programacaoOrdemOriginal.getEquipe();
								}catch(ErroRepositorioException e){
									throw new ControladorException("erro.sistema", e);
								}

							}else if(servicoAssociado.getFormaSelecaoEquipe().getId().intValue() == FormaSelecaoEquipe.FormaSelecaoEquipeServico.SOLICITA_AUTORIZACAO
											.valorId()){

								// Equipe foi solicitada
								if(autorizacaoServico != null && autorizacaoServico.getIdEquipeInformada() != null){
									ObterDadosEquipe dadosEquipeInformada = this
													.obterDadosEquipe(autorizacaoServico.getIdEquipeInformada());
									if(dadosEquipeInformada != null && dadosEquipeInformada.getEquipe() != null){
										equipeOsAssociada = dadosEquipeInformada.getEquipe();
									}
								}
							}

							if(equipeOsAssociada != null && dataProgramacaoOrdemAssociada != null){

								dataProgramacaoOrdemAssociada = Util.formatarDataSemHora(dataProgramacaoOrdemAssociada);

								// Programa a Ordem de Serviço
								ProgramacaoRoteiro programacaoRoteiroOrdemAssociada = new ProgramacaoRoteiro();
								programacaoRoteiroOrdemAssociada.setDataRoteiro(dataProgramacaoOrdemAssociada);
								programacaoRoteiroOrdemAssociada.setUnidadeOrganizacional(servicoAssociado.getUnidadeOrganizacional());
								programacaoRoteiroOrdemAssociada.setUltimaAlteracao(new Date());

								OrdemServicoProgramacao programacaoOrdemAssociada = new OrdemServicoProgramacao();
								programacaoOrdemAssociada.setProgramacaoRoteiro(programacaoRoteiroOrdemAssociada);
								programacaoOrdemAssociada.setOrdemServico(ordemAssociada);
								programacaoOrdemAssociada.setEquipe(equipeOsAssociada);
								programacaoOrdemAssociada.setUsuarioProgramacao(usuario);

								// obtém sequencial
								programacaoOrdemAssociada.setNnSequencialProgramacao(this.gerarSequencialProgramacaoOrdemServico(
												equipeOsAssociada, dataProgramacaoOrdemAssociada));

								this.incluirOrdemServicoEmProgramacao(programacaoOrdemAssociada, programacaoRoteiroOrdemAssociada, usuario);
							}
						}
					}
				}
				// ----- Fim Programação -----

				// ----- Início Encerramento -----
				/*
				 * Só faz a Encerramento caso:
				 * 2. Encerramento seja automático ou autorizado
				 */

				// Verifica se o encerramento será automático ou se foi autorizado
				if(servicoAssociado.getFormaEncerramento().getId().intValue() == FormaEncerramento.FormaEncerramentoServico.SOLICITA_AUTORIZACAO
								.valorId() && autorizacaoServico != null && !autorizacaoServico.isEncerramentoAutorizado()){
					continue;

				}else if(servicoAssociado.getFormaEncerramento().getId().intValue() == FormaEncerramento.FormaEncerramentoServico.POSTERIOR
								.valorId()){
					continue;
				}

				if(servicoAssociado.getFormaEncerramento().getId().intValue() == FormaEncerramento.FormaEncerramentoServico.AUTOMATICA
								.valorId() && autorizacaoServico != null && autorizacaoServico.getOSEncerramentoHelper() == null){
					throw new ControladorException("atencao.os_associada.encerramento_automatico", null, ordemAssociada.getServicoTipo()
									.getDescricao());
				}

				// Chama o Encerramento Com Execução e Sem Referência
				OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();
				osEncerramentoHelper.setNumeroOS(ordemAssociada.getId());
				osEncerramentoHelper.setUsuarioLogado(usuario);
				osEncerramentoHelper.setUltimaAlteracao(new Date());
				osEncerramentoHelper.setTipoServicoOSId(ordemAssociada.getServicoTipo().getId().toString());
				osEncerramentoHelper.setOsFiscalizacao(null);

				OSEncerramentoHelper oSAssociadaEncerramentoHelper = null;
				if(autorizacaoServico != null){
					oSAssociadaEncerramentoHelper = autorizacaoServico.getOSEncerramentoHelper();
				}
				if(oSAssociadaEncerramentoHelper != null){

					osEncerramentoHelper.setIdServicoTipo(oSAssociadaEncerramentoHelper.getIdServicoTipo());
					osEncerramentoHelper.setIdMotivoEncerramento(oSAssociadaEncerramentoHelper.getIdMotivoEncerramento());
					osEncerramentoHelper.setDimensao1(oSAssociadaEncerramentoHelper.getDimensao1());
					osEncerramentoHelper.setDimensao2(oSAssociadaEncerramentoHelper.getDimensao2());
					osEncerramentoHelper.setDimensao3(oSAssociadaEncerramentoHelper.getDimensao3());
					osEncerramentoHelper.setDataExecucao(oSAssociadaEncerramentoHelper.getDataExecucao());
					osEncerramentoHelper.setParecerEncerramento(oSAssociadaEncerramentoHelper.getParecerEncerramento());
					osEncerramentoHelper.setIdTipoRetornoOSReferida(oSAssociadaEncerramentoHelper.getIdTipoRetornoOSReferida());
					osEncerramentoHelper.setCodigoRetornoVistoriaOs(oSAssociadaEncerramentoHelper.getCodigoRetornoVistoriaOs());
					osEncerramentoHelper.setIndicadorPavimento(oSAssociadaEncerramentoHelper.getIndicadorPavimento());

					this.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, null,
									OrigemEncerramentoOrdemServico.ENCERRAMENTO_OS_ASSOCIADA, null);
				}
			}
			// ----- Fim Encerramento -----

		}
	}

	/**
	 * @param equipe
	 *            [obrigatorio]
	 * @param dataProgramacaoRoteiro
	 *            [obrigatorio]
	 * @return short
	 * @throws ControladorException
	 *             NullPointerException se algum dos parâmetros for nulo.
	 */
	private short gerarSequencialProgramacaoOrdemServico(Equipe equipe, Date dataProgramacaoRoteiro) throws ControladorException{

		try{
			Collection<OrdemServicoProgramacao> programacaoOrdemServico = repositorioOrdemServico
							.pesquisarOSProgramacaoComDataRoteiroIdEquipe(dataProgramacaoRoteiro, equipe.getId(), false);

			Integer retorno = 1;
			if(programacaoOrdemServico != null && !programacaoOrdemServico.isEmpty()){
				retorno = Integer.valueOf(this.retornaUltimoSequencial(programacaoOrdemServico));
				retorno = retorno + 1;
			}

			return retorno.shortValue();

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	private ServicoAssociadoAutorizacaoHelper obterDadosAutorizacaoServicoAssociado(ServicoTipo servicoAutorizacao,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> servicosAutorizados){

		ServicoAssociadoAutorizacaoHelper dadosAutorizacao = null;
		if(servicosAutorizados != null && servicosAutorizados.containsKey(servicoAutorizacao.getId())){
			dadosAutorizacao = servicosAutorizados.get(servicoAutorizacao.getId());
		}

		return dadosAutorizacao;
	}

	/**
	 * @param ordemServicoOriginal
	 *            Ordem de Serviço que servirá de modelo, caso o serviço associado exiga Geração.
	 * @param servicoAssociado
	 *            Serviço Associado, que possui as configurações de como a Ordem de Serviço será
	 *            gerada/encerrada/tramitada.
	 * @param usuario
	 *            usuário que iniciou a operação.
	 * @throws ControladorException
	 * @throws nullPointerException
	 *             se OrdemServico é nulo ou servicoAssociado é nulo
	 */
	private OrdemServico gerarOrdemServicoAssociada(OrdemServico ordemServicoOriginal, ServicoTipo servicoAssociado, Usuario usuario)
					throws ControladorException{

		OrdemServico ordemServicoAssociada = null;

		try{
			ordemServicoAssociada = (OrdemServico) BeanUtils.cloneBean(ordemServicoOriginal);
		}catch(IllegalAccessException e){
			throw new ControladorException("erro.sistema", e);
		}catch(InstantiationException e){
			throw new ControladorException("erro.sistema", e);
		}catch(InvocationTargetException e){
			throw new ControladorException("erro.sistema", e);
		}catch(NoSuchMethodException e){
			throw new ControladorException("erro.sistema", e);
		}

		ordemServicoAssociada.setId(null);
		ordemServicoAssociada.setDataGeracao(new Date());
		ordemServicoAssociada.setServicoTipo(servicoAssociado);

		// Inclui a associada e atribui o id gerado
		ordemServicoAssociada.setId(this.inserirOrdemServico(ordemServicoAssociada, usuario));

		return ordemServicoAssociada;
	}

	/**
	 * Método responsável por retornar a Lista de Serviços associados a um determinado serviço, que
	 * necessitem de autorização.
	 * 
	 * @author Saulo Lima
	 * @date 04/06/2009
	 *       Novo parâmetro numeroOS
	 * @param servicoTipo
	 *            [obrigatório]
	 * @param evento
	 *            - enum dos Tipos de Eventos a serem verificados [obrigatório]
	 * @param origemEncerramento
	 *            - informa a Origem de Encerramento (qual funcionalidade) da ação do usuário.
	 * @param numeroOS
	 *            - utizado para pesquisar as equipes, caso necessário.
	 * @return List<ServicoAssociadoAutorizacaoHelper>
	 *         Deve ser utilizada apenas na verificação para encerramento.
	 * @throws ControladorException
	 *             se houver erro na consulta de servicos Associados
	 */
	public List<ServicoAssociadoAutorizacaoHelper> verificarServicosAssociadosParaAutorizacao(ServicoTipo servicoTipo,
					EventoGeracaoServico evento, OrigemEncerramentoOrdemServico origemEncerramento, Integer numeroOS)
					throws ControladorException{

		List<ServicoAssociadoAutorizacaoHelper> colecaoServicosParaAutorizar = new ArrayList<ServicoAssociadoAutorizacaoHelper>();

		ServicoTipo servicoTipoOriginal = new ServicoTipo();

		try{
			servicoTipoOriginal = repositorioOrdemServico.pesquisarServicoTipoPorId(servicoTipo.getId());
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", null);
		}

		if(servicoTipoOriginal.getServicosAssociados() != null){
			for(ServicoAssociado servicoAssociado : servicoTipoOriginal.getServicosAssociados()){
				if((evento.valorId() == servicoAssociado.getEventoGeracao().getId().intValue())
								&& (servicoAssociado.getIndicadorUso().equals(ConstantesSistema.SIM))){

					boolean autorizarServico = false;

					ServicoAssociadoAutorizacaoHelper servicoAutorizacao = new ServicoAssociadoAutorizacaoHelper();
					servicoAutorizacao.setIdServicoAssociado(servicoAssociado.getComp_id().getIdServicoTipoAssociado());
					servicoAutorizacao.setDescricaoServicoAssociado(servicoAssociado.getServicoTipoAssociado().getDescricao());

					// Forma de Geração
					if(servicoAssociado.getFormaGeracao() == null){
						throw new ControladorException("erro.servidor");
					}
					int idFormaGeracao = servicoAssociado.getFormaGeracao().getId().intValue();
					if(idFormaGeracao == FormaGeracao.FormaGeracaoServico.SOLICITA_AUTORIZACAO.valorId()){
						servicoAutorizacao.setFormaGeracaoHelper(new FormaGeracaoHelper(idFormaGeracao));
						autorizarServico = true;
					}else if(idFormaGeracao == FormaGeracao.FormaGeracaoServico.AUTOMATICA.valorId()){
						servicoAutorizacao.setFormaGeracaoHelper(new FormaGeracaoHelper(idFormaGeracao));
					}else if(idFormaGeracao == FormaGeracao.FormaGeracaoServico.POSTERIOR.valorId()){
						servicoAutorizacao.setFormaGeracaoHelper(new FormaGeracaoHelper(idFormaGeracao));
					}else{
						throw new ControladorException("erro.servidor");
					}

					// verifica que outros tipos de Autorizações serão necessárias
					if(origemEncerramento.compareTo(OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO) == 0){

						// Forma de Programação
						if(servicoAssociado.getFormaProgramacao() == null){
							throw new ControladorException("erro.servidor");
						}
						int idFormaProgramacao = servicoAssociado.getFormaProgramacao().getId().intValue();
						if(idFormaProgramacao == FormaProgramacao.FormaProgramacaoServico.SOLICITA_AUTORIZACAO.valorId()){
							servicoAutorizacao.setFormaProgramacaoHelper(new FormaProgramacaoHelper(idFormaProgramacao));
							autorizarServico = true;
						}else if(idFormaProgramacao == FormaProgramacao.FormaProgramacaoServico.POSTERIOR.valorId()){
							servicoAutorizacao.setFormaProgramacaoHelper(new FormaProgramacaoHelper(idFormaProgramacao));
						}else if(idFormaProgramacao == FormaProgramacao.FormaProgramacaoServico.MESMO_DIA.valorId()){
							servicoAutorizacao.setFormaProgramacaoHelper(new FormaProgramacaoHelper(idFormaProgramacao));
							Calendar calendario = Calendar.getInstance();
							servicoAutorizacao.setDataProgramacaoInformada(calendario.getTime());
						}else if(idFormaProgramacao == FormaProgramacao.FormaProgramacaoServico.DIA_POSTERIOR.valorId()){
							servicoAutorizacao.setFormaProgramacaoHelper(new FormaProgramacaoHelper(idFormaProgramacao));
							Calendar calendario = Calendar.getInstance();
							calendario.add(Calendar.DAY_OF_MONTH, 1);
							servicoAutorizacao.setDataProgramacaoInformada(calendario.getTime());
						}else{
							throw new ControladorException("erro.servidor");
						}

						// Forma de Seleção de Equipe
						if(servicoAssociado.getFormaSelecaoEquipe() == null){
							throw new ControladorException("erro.servidor");
						}
						int idFormaSelecaoEquipe = servicoAssociado.getFormaSelecaoEquipe().getId().intValue();
						if(idFormaSelecaoEquipe == FormaSelecaoEquipe.FormaSelecaoEquipeServico.SOLICITA_AUTORIZACAO.valorId()){
							servicoAutorizacao.setFormaSelecaoEquipeHelper(new FormaSelecaoEquipeHelper(idFormaSelecaoEquipe));
							autorizarServico = true;

							// Obtém as equipes disponíveis para Seleção.
							if(servicoAssociado.getUnidadeOrganizacional() == null){
								UnidadeOrganizacional unidade = this.obterUnidadeDestinoUltimoTramite(numeroOS);
								servicoAssociado.setUnidadeOrganizacional(unidade);
							}
							Collection<Equipe> equipesUnidade = this.obterEquipesPorUnidade(servicoAssociado.getUnidadeOrganizacional()
											.getId());
							servicoAutorizacao.setEquipes(equipesUnidade);

						}else if(idFormaSelecaoEquipe == FormaSelecaoEquipe.FormaSelecaoEquipeServico.ATUAL.valorId()){
							servicoAutorizacao.setFormaSelecaoEquipeHelper(new FormaSelecaoEquipeHelper(idFormaSelecaoEquipe));
						}else if(idFormaSelecaoEquipe == FormaSelecaoEquipe.FormaSelecaoEquipeServico.POSTERIOR.valorId()){
							servicoAutorizacao.setFormaSelecaoEquipeHelper(new FormaSelecaoEquipeHelper(idFormaSelecaoEquipe));
						}else{
							throw new ControladorException("erro.servidor");
						}

						// Caso não seja ACOMPANHAMENTO_ROTEIRO, colocar como posterir (programação
						// e equipe) para JSP preencher corretamente.
					}else{
						servicoAutorizacao.setFormaProgramacaoHelper(new FormaProgramacaoHelper(
										FormaProgramacao.FormaProgramacaoServico.POSTERIOR.valorId()));
						servicoAutorizacao.setFormaSelecaoEquipeHelper(new FormaSelecaoEquipeHelper(
										FormaSelecaoEquipe.FormaSelecaoEquipeServico.POSTERIOR.valorId()));
					}

					// Forma de Trâmite
					if(servicoAssociado.getFormaTramite() == null){
						throw new ControladorException("erro.servidor");
					}
					int idFormaTramite = servicoAssociado.getFormaTramite().getId().intValue();
					if(idFormaTramite == FormaTramite.FormaTramiteServico.SOLICITA_AUTORIZACAO.valorId()){
						servicoAutorizacao.setFormaTramiteHelper(new FormaTramiteHelper(idFormaTramite));
						autorizarServico = true;
					}else if(idFormaTramite == FormaTramite.FormaTramiteServico.AUTOMATICA.valorId()){
						servicoAutorizacao.setFormaTramiteHelper(new FormaTramiteHelper(idFormaTramite));
					}else if(idFormaTramite == FormaTramite.FormaTramiteServico.POSTERIOR.valorId()){
						servicoAutorizacao.setFormaTramiteHelper(new FormaTramiteHelper(idFormaTramite));
					}else{
						throw new ControladorException("erro.servidor");
					}

					// Forma de Encerramento
					if(servicoAssociado.getFormaEncerramento() == null){
						throw new ControladorException("erro.servidor");
					}
					int idFormaEncerramento = servicoAssociado.getFormaEncerramento().getId().intValue();
					if(idFormaEncerramento == FormaEncerramento.FormaEncerramentoServico.SOLICITA_AUTORIZACAO.valorId()){
						servicoAutorizacao.setFormaEncerramentoHelper(new FormaEncerramentoHelper(idFormaEncerramento));
						autorizarServico = true;
					}else if(idFormaEncerramento == FormaEncerramento.FormaEncerramentoServico.AUTOMATICA.valorId()){
						autorizarServico = true;
						servicoAutorizacao.setFormaEncerramentoHelper(new FormaEncerramentoHelper(idFormaEncerramento));
					}else if(idFormaEncerramento == FormaEncerramento.FormaEncerramentoServico.POSTERIOR.valorId()){
						servicoAutorizacao.setFormaEncerramentoHelper(new FormaEncerramentoHelper(idFormaEncerramento));
					}else{
						throw new ControladorException("erro.servidor");
					}

					if(autorizarServico && !servicoAutorizacao.getFormaGeracaoHelper().isPosterior()){
						colecaoServicosParaAutorizar.add(servicoAutorizacao);
					}else{
						servicoAutorizacao = null;
					}

				}
			}
		}

		BeanComparator beanComparator = new BeanComparator("descricaoServicoAssociado");
		Collections.sort(colecaoServicosParaAutorizar, beanComparator);

		return colecaoServicosParaAutorizar;
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * Método responsável por persistir uma OS, além atribuir valores "default" da mesma.
	 * 
	 * @author eduardo henrique
	 * @date 13/05/2009
	 */
	private Integer inserirOrdemServico(OrdemServico ordemServico, Usuario usuarioInclusao) throws ControladorException{

		if(ordemServico == null){
			throw new IllegalArgumentException("erro.referencia_os_geracao_nula");
		}

		Calendar calendar = Calendar.getInstance();

		ordemServico.setAtendimentoMotivoEncerramento(null);
		ordemServico.setOsReferidaRetornoTipo(null);
		ordemServico.setSituacao(OrdemServico.SITUACAO_PENDENTE);
		ordemServico.setDataGeracao(calendar.getTime());
		ordemServico.setDataEmissao(null);
		ordemServico.setDataEncerramento(null);
		ordemServico.setDataExecucao(null);
		ordemServico.setDescricaoParecerEncerramento(null);
		ordemServico.setAreaPavimento(null);
		ordemServico.setDimensao1(null);
		ordemServico.setDimensao2(null);
		ordemServico.setDimensao3(null);
		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.NAO);
		ordemServico.setServicoNaoCobrancaMotivo(null);
		ordemServico.setPercentualCobranca(null);
		ordemServico.setFiscalizacaoColetiva(null);
		ordemServico.setIndicadorServicoDiagnosticado(ConstantesSistema.NAO);
		ordemServico.setUltimaAlteracao(calendar.getTime());

		if(ordemServico.getServicoTipo() != null){
			try{
				ServicoTipo servicoTipo = repositorioOrdemServico.pesquisarServicoTipoPorId(ordemServico.getServicoTipo().getId());

				ordemServico.setValorOriginal(servicoTipo.getValor());
				ordemServico.setServicoTipoPrioridadeOriginal(servicoTipo.getServicoTipoPrioridade());
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}
		}

		if(ordemServico.getImovel() != null){ // O id da Lig. de água é a matrícula do próprio
			// Imóvel
			LigacaoAgua ligacaoAguaImovel = getControladorLigacaoAgua().pesquisarLigacaoAgua(ordemServico.getImovel().getId());
			if(ligacaoAguaImovel != null){
				if(ligacaoAguaImovel.getCorteTipo() != null){
					ordemServico.setCorteTipo(ligacaoAguaImovel.getCorteTipo());
				}
				if(ligacaoAguaImovel.getSupressaoTipo() != null){
					ordemServico.setSupressaoTipo(ligacaoAguaImovel.getSupressaoTipo());
				}
			}
		}

		if(ordemServico.getServicoTipoPrioridadeAtual() == null
						|| ordemServico.getServicoTipoPrioridadeAtual().getId().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			ordemServico.setServicoTipoPrioridadeAtual(ordemServico.getServicoTipoPrioridadeOriginal());
		}

		// Colocar como ordem de servico não programada - Raphael Rossiter em 08/02/2007
		ordemServico.setIndicadorProgramada(OrdemServico.NAO_PROGRAMADA);

		// .........................................................................
		// Consultar se já não existe OS para o tipo de servico com status PENDENTE.
		// .........................................................................
		if(ordemServico.getRegistroAtendimento() != null && ordemServico.getServicoTipo() != null){
			this.validarServicoTipo(ordemServico.getRegistroAtendimento().getId(), ordemServico.getServicoTipo().getId());
		}
		//
		// if(ordemServico.getRegistroAtendimento() != null){
		// unidadeOrganizacional =
		// this.getControladorRegistroAtendimento().obterUnidadeAtendimentoRA(ordemServico.getRegistroAtendimento().getId());
		// if(unidadeOrganizacional.getId().equals(UnidadeOrganizacional.ID_UNIDADE_ORGANIZACIONAL_REDES_DE_AGUA)){
		//
		// try {
		// Collection collOSServicoTipo =
		// repositorioOrdemServico.pesquisarServicoTipoPorRegistroAtendimento(ordemServico.getRegistroAtendimento().getId(),ordemServico.getServicoTipo().getId());
		// if(collOSServicoTipo != null && !collOSServicoTipo.isEmpty()){
		// throw new ControladorException(
		// "atencao.servico_tipo.existe_os",
		// null,
		// new String[] { "","","" });
		// }
		//
		// } catch (ErroRepositorioException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// }
		//
		//
		// }

		// .........................................................................

		// if(!Util.isVazioOuBranco(ordemServico.getOrdemServicoUnidades()) &&
		// !ordemServico.getOrdemServicoUnidades().isEmpty()){
		// unidadeOrganizacional = ordemServico.obterUnidadeOrganizacionalAbertura();
		// }

		if(ordemServico.getValorHorasTrabalhadas() == null){
			ordemServico.setValorHorasTrabalhadas(BigDecimal.ZERO);
		}

		if(ordemServico.getValorMateriais() == null){
			ordemServico.setValorMateriais(BigDecimal.ZERO);
		}

		UnidadeOrganizacional unidadeOrganizacional = null;
		ordemServico.setOrdemServicoUnidades(null);

		Integer idOrdemServico = (Integer) getControladorUtil().inserir(ordemServico);

		if(unidadeOrganizacional == null){
			String flagTramiteIndependente = ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar().toString();

			/*
			 * Caso não exista RA, atualizar a unidade como a unidade que represente a localidade do
			 * imóvel associado ao documento de cobrança
			 */
			if(ordemServico.getCobrancaDocumento() != null){
				CobrancaDocumento cobrancaDocumento = this.getControladorCobranca().pesquisarCobrancaDocumento(
								ordemServico.getCobrancaDocumento().getId());

				Integer idLocalidade = null;
				if(cobrancaDocumento.getImovel() != null){

					Imovel imovel = getControladorImovel().pesquisarImovel(cobrancaDocumento.getImovel().getId());
					idLocalidade = imovel.getLocalidade().getId();

				}else{

					idLocalidade = cobrancaDocumento.getLocalidade().getId();
				}
				
				// [UC3036] – Obter Unidade Tramite Ordem de Serviço
				Integer idUnidadeOrganizacional = null;
								
				idUnidadeOrganizacional = this.pesquisarUnidadeTramiteOS(ordemServico.getServicoTipo().getId(), idLocalidade, null, null,
								null);

				if(idUnidadeOrganizacional == null){
					unidadeOrganizacional = getControladorUnidade().pesquisarUnidadeOrganizacionalLocalidade(idLocalidade);
				} else {
					unidadeOrganizacional = getControladorUnidade().pesquisarUnidadeOrganizacional(idUnidadeOrganizacional);
				}

			}else if(ordemServico.getRegistroAtendimento() != null){
				if(flagTramiteIndependente.equals(ConstantesSistema.NAO.toString())){
					unidadeOrganizacional = this.getControladorRegistroAtendimento().obterUnidadeAtendimentoRA(
									ordemServico.getRegistroAtendimento().getId());
				} else {
					RegistroAtendimento registroAtendimentoPesq = this.getControladorRegistroAtendimento().pesquisarRegistroAtendimento(
									ordemServico.getRegistroAtendimento().getId());

					Integer idLocalidade = null;
					if(registroAtendimentoPesq.getImovel() != null){
						Imovel imovel = getControladorImovel().pesquisarImovel(registroAtendimentoPesq.getImovel().getId());
						idLocalidade = imovel.getLocalidade().getId();
					}else{
						idLocalidade = registroAtendimentoPesq.getLocalidade().getId();
					}

					Integer idUnidadeOrganizacional = this.pesquisarUnidadeTramiteOS(ordemServico.getServicoTipo().getId(), idLocalidade,
									null, null, null);
					
					if(idUnidadeOrganizacional != null){
						unidadeOrganizacional = getControladorUnidade().pesquisarUnidadeOrganizacional(idUnidadeOrganizacional);
					}else{
						unidadeOrganizacional = this.getControladorRegistroAtendimento().obterUnidadeAtendimentoRA(
										ordemServico.getRegistroAtendimento().getId());
					}
				}
			}else{ // Busca a Unidade pela Localidade da Empresa
				
				Integer idLocalidade = null;
				if(ordemServico.getImovel() != null){
					Imovel imovel = getControladorImovel().pesquisarImovel(ordemServico.getImovel().getId());

					idLocalidade = imovel.getLocalidade().getId();
				}else{
					idLocalidade = Integer.valueOf(ParametroCadastro.P_LOCA_ID_LOCALIDADE_PADRAO.executar());
				}

				Integer idUnidadeOrganizacional = this.pesquisarUnidadeTramiteOS(ordemServico.getServicoTipo().getId(), idLocalidade, null,
								null, null);

				if(idUnidadeOrganizacional == null){
					String idLocalidadePadrao = ParametroCadastro.P_LOCA_ID_LOCALIDADE_PADRAO.executar();
					if(idLocalidadePadrao == null){
						throw new IllegalArgumentException("erro.sistema");
					}
					unidadeOrganizacional = getControladorUnidade().pesquisarUnidadeOrganizacionalLocalidade(
									Integer.valueOf(idLocalidadePadrao));
				}else{
					unidadeOrganizacional = getControladorUnidade().pesquisarUnidadeOrganizacional(idUnidadeOrganizacional);
				}
			}
		}

		OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();

		ordemServicoUnidade.setOrdemServico(ordemServico);
		ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacional);
		ordemServicoUnidade.setUsuario(usuarioInclusao);
		AtendimentoRelacaoTipo atrt = new AtendimentoRelacaoTipo();
		atrt.setId(AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
		ordemServicoUnidade.setAtendimentoRelacaoTipo(atrt);
		ordemServicoUnidade.setUltimaAlteracao(calendar.getTime());

		getControladorUtil().inserir(ordemServicoUnidade);

		/*
		 * [OC790655][UC0430][SB0004.7.1]: Caso o tipo de serviço da ordem de serviço
		 * tenha a indicação de geração de dados no histórico de manutenção da ligação de água do
		 * imóvel (SVTP_ICGERARHISTORICOIMOVEL com o valor 1 (sim) na tabela SERVICO_TIPO com
		 * SVTP_ID=SVTP_ID da tabela ORDEM_SERVICO), o sistema gera os dados – inclui na tabela
		 * HISTORICO_MANUTENCAO_LIGACAO .
		 */
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServico.getServicoTipo().getId()));
		ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroServicoTipo,
						ServicoTipo.class.getName()));

		if(ConstantesSistema.SIM.equals(servicoTipo.getIndicadorGerarHistoricoImovel())){
			getControladorLigacaoAgua().criarHistoricoManutencaoLigacao(ordemServico);
		}

		return idOrdemServico;
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * Método que é chamado pelo [UC0251] Gerar Atividade de Ação de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 18/08/2006
	 */
	public Integer gerarOrdemServicoSemValidacao(OrdemServico ordemServico, Integer idLocalidade, Usuario usuario)
					throws ControladorException{

		Calendar calendar = Calendar.getInstance();
		UnidadeOrganizacional unidadeOrganizacional = null;
		Integer idSetorComercial = null;
		Integer unidadeOrganizacionalId = null;
		Integer idBairro = null;
		Integer unidadeId = null;

		// [SB0004] - Gerar Ordem de serviço

		ordemServico.setAtendimentoMotivoEncerramento(null);
		ordemServico.setOsReferidaRetornoTipo(null);
		ordemServico.setSituacao(OrdemServico.SITUACAO_PENDENTE);
		ordemServico.setDataGeracao(calendar.getTime());
		ordemServico.setDataEmissao(null);
		ordemServico.setDataEncerramento(null);
		ordemServico.setDataExecucao(null);
		ordemServico.setDescricaoParecerEncerramento(null);
		ordemServico.setAreaPavimento(null);
		ordemServico.setDimensao1(null);
		ordemServico.setDimensao2(null);
		ordemServico.setDimensao3(null);
		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.NAO);
		ordemServico.setServicoNaoCobrancaMotivo(null);
		ordemServico.setPercentualCobranca(BigDecimal.ZERO);
		ordemServico.setFiscalizacaoColetiva(null);
		ordemServico.setIndicadorServicoDiagnosticado(ConstantesSistema.NAO);
		ordemServico.setUltimaAlteracao(calendar.getTime());

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServico.getServicoTipo().getId()));
		Collection colecaoServicoTipo = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
		ServicoTipo servicoTipo = null;
		OrdemServico ordemServicoDados = null;

		if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
			servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

			try{
				ordemServicoDados = repositorioOrdemServico.pesquisarDadosServicoTipoPrioridade(servicoTipo.getId());
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}
			if(ordemServicoDados != null){
				ordemServico.setValorOriginal(servicoTipo.getValor());
				ordemServico.setServicoTipoPrioridadeOriginal(ordemServicoDados.getServicoTipo().getServicoTipoPrioridade());
				ordemServico.setServicoTipoPrioridadeAtual(ordemServicoDados.getServicoTipo().getServicoTipoPrioridade());
			}

		}

		// Colocar como ordem de servico não programada - Raphael Rossiter em 08/02/2007
		ordemServico.setIndicadorProgramada(OrdemServico.NAO_PROGRAMADA);

		// Prepara o retorno da geração da ordem de serviço
		Integer idOrdemServico = null;

		OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();

		// [SB0006] – Obter unidade de geração da Ordem de Serviço
		// Caso exista Documento de Cobrança:
		// 1.1. Caso haja indicação que o tipo de serviço da ordem de serviço seletiva tem regra
		// própria para definição da unidade de abertura

		FiltroServicoTipoTramite filtroServicoTipoTramite = new FiltroServicoTipoTramite();

		filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.SERVICO_TIPO_ID, ordemServico
						.getServicoTipo().getId()));

		Collection<ServicoTipoTramite> colecaoServicoTipoTramite = this.getControladorUtil().pesquisar(filtroServicoTipoTramite,
						ServicoTipoTramite.class.getName());

		if(colecaoServicoTipoTramite != null && !colecaoServicoTipoTramite.isEmpty()){

			Integer servicoTipoId = null;

			if(ordemServico.getServicoTipo() != null){

				servicoTipoId = ordemServico.getServicoTipo().getId();
			}

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacionalOrigem = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacionalOrigem.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID_LOCALIDADE,
							idLocalidade));

			Collection colecaoUnidadeOrganizacional = (Collection) getControladorUtil().pesquisar(filtroUnidadeOrganizacionalOrigem,
							UnidadeOrganizacional.class.getName());

			UnidadeOrganizacional unidadeIdAux = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

			unidadeId = unidadeIdAux.getId();

			// Recupera o Setor Comercial
			Integer idImovel = null;
			Imovel imovel = null;


			if(!Util.isVazioOuBranco(ordemServico.getImovel())){
				idImovel = ordemServico.getImovel().getId();
			}else{
				if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())
								&& !Util.isVazioOuBranco(ordemServico.getRegistroAtendimento().getImovel())){
					idImovel = ordemServico.getRegistroAtendimento().getImovel().getId();
				}
			}

			Collection colecaoServicoTipoTramiteLocUnidade;
			
			try{
				colecaoServicoTipoTramiteLocUnidade = repositorioOrdemServico.pesquisarServicoTipoTramite(servicoTipoId, idLocalidade,
								null, null, unidadeId);

				if(!Util.isVazioOrNulo(colecaoServicoTipoTramiteLocUnidade) && colecaoServicoTipoTramiteLocUnidade.size() == 1){
					unidadeOrganizacionalId = pesquisarUnidadeTramiteOS(servicoTipoId, idLocalidade, null, null, unidadeId);
				}else{
					if(!Util.isVazioOuBranco(idImovel)){
						FiltroImovel filtroImovel = new FiltroImovel();
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
						filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_BAIRRO);

						imovel = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));

						idSetorComercial = imovel.getSetorComercial().getId();
						idBairro = imovel.getLogradouroBairro().getBairro().getId();
						unidadeOrganizacionalId = pesquisarUnidadeTramiteOS(servicoTipoId, idLocalidade, idSetorComercial, null, unidadeId);
					}

				}

			}catch(ErroRepositorioException e){
				e.printStackTrace();
				throw new ControladorException("erro.sistema", e);
			}

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, unidadeOrganizacionalId));

			
			Collection collUnidadeOrganizacional = getControladorUtil().pesquisar(filtroUnidadeOrganizacional,
							UnidadeOrganizacional.class.getName());

			unidadeOrganizacional = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(collUnidadeOrganizacional);

		}else{
			unidadeOrganizacional = getControladorUnidade().pesquisarUnidadeOrganizacionalLocalidade(idLocalidade);
		}

		// 1.1. Caso exista unidade que represente a localidade do imóvel associado ao documento de
		// cobrança (a partir da tabela UNIDADE_ORGANIZACIONAL com LOCA_ID igual ao LOCA_ID da
		// tabela IMOVEL com IMOV_ID igual ao IMOV_ID da tabela COBRANCA_DOCUMENTO com CBDO_ID igual
		// ao Id do documento de cobrança informado)
		if(unidadeOrganizacional != null){
			// 1.1.1.Caso a unidade da localidade do imóvel tenha unidade centralizadora
			// (UNID_IDCENTRALIZADORA diferente de nulo), atualizar a unidade como a unidade
			// centralizadora
			if(unidadeOrganizacional.getUnidadeCentralizadora() != null){
				ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacional.getUnidadeCentralizadora());
			}else{
				// 1.1.2.Caso contrário, atualizar a unidade como a unidade da localidade do imóvel
				ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacional);
			}

			if(ordemServico.getValorHorasTrabalhadas() == null){
				ordemServico.setValorHorasTrabalhadas(BigDecimal.ZERO);
			}

			if(ordemServico.getValorMateriais() == null){
				ordemServico.setValorMateriais(BigDecimal.ZERO);
			}

			idOrdemServico = (Integer) this.getControladorUtil().inserir(ordemServico);

			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, idOrdemServico));

			Collection<OrdemServico> colecaoOS = this.getControladorUtil().pesquisar(filtroOrdemServico, OrdemServico.class.getName());

			ordemServico = (OrdemServico) Util.retonarObjetoDeColecao(colecaoOS);

			ordemServicoUnidade.setOrdemServico(ordemServico);
			ordemServicoUnidade.setUsuario(usuario);
			AtendimentoRelacaoTipo atrt = new AtendimentoRelacaoTipo();
			atrt.setId(AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
			ordemServicoUnidade.setAtendimentoRelacaoTipo(atrt);
			ordemServicoUnidade.setUltimaAlteracao(calendar.getTime());

			this.getControladorUtil().inserir(ordemServicoUnidade);
			
			this.gerarTramiteOrdemServico(idLocalidade, idSetorComercial, idBairro, unidadeOrganizacionalId, unidadeOrganizacionalId, null,
							usuario,
							ordemServico);

			/*
			 * [OC790655][UC0430][SB0004.7.1]: Caso o tipo de serviço da ordem de
			 * serviço tenha a indicação de geração de dados no histórico de manutenção da ligação
			 * de água do imóvel (SVTP_ICGERARHISTORICOIMOVEL com o valor 1 (sim) na tabela
			 * SERVICO_TIPO com SVTP_ID=SVTP_ID da tabela ORDEM_SERVICO), o sistema gera os dados –
			 * inclui na tabela HISTORICO_MANUTENCAO_LIGACAO .
			 */
			if(ConstantesSistema.SIM.equals(servicoTipo.getIndicadorGerarHistoricoImovel())){
				getControladorLigacaoAgua().criarHistoricoManutencaoLigacao(ordemServico);
			}
		}else{
			// 1.2. Caso contrário, o sistema não gera a ordem de serviço e retorna para o caso de
			// uso que chamou.
			throw new ControladorException("atencao.gerar_ordem_corte_unidade_nao_existente");
		}

		return idOrdemServico;
	}

	/**
	 * [UC0711] Filtro Gerar Ordem de Serviço Seletiva
	 * 
	 * @author Eduardo Oliveira
	 * @date 11/06/2013
	 */
	public boolean verificarLigacaoAguaSituacaoPermitida(LigacaoAguaSituacao LigacaoAguaSituacao) throws ControladorException{

		boolean situacaoPermitidaCorresponde = true;

		String[] parametroLigacaoAguaSituacaoPermitida = null;
		try{
			String parametroVerificarLigacaoAguaSituacaoPermitida = (String) ParametroOrdemServico.P_SIT_AGUA_PERMIT_OS_SELETIVA_MANUT_HIDROMETRO
							.executar();

			if(parametroVerificarLigacaoAguaSituacaoPermitida != null
							&& parametroVerificarLigacaoAguaSituacaoPermitida.equals(ConstantesSistema.SIM.toString())){

				situacaoPermitidaCorresponde = false;

				parametroLigacaoAguaSituacaoPermitida = ((String) ParametroOrdemServico.P_SIT_AGUA_PERMIT_OS_SELETIVA_MANUT_HIDROMETRO
								.executar()).split(",");

				for(int y = 0; y < parametroLigacaoAguaSituacaoPermitida.length; y++){
					if(parametroLigacaoAguaSituacaoPermitida[y].equals(LigacaoAguaSituacaoEnum.class.toString())){

						situacaoPermitidaCorresponde = true;

						return situacaoPermitidaCorresponde;
					}
				}

				return situacaoPermitidaCorresponde;
			}else{
				return situacaoPermitidaCorresponde;
			}

		}catch(ControladorException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	public boolean comparaServicoTipoSubgrupoSubstituicaoHidrometro(String idServicoTipo) throws ControladorException{

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));
		filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoTipo.SERVICO_TIPO_SUB_GRUPO);

		Collection<ServicoTipo> collectionServicoTipo;
		try{
			collectionServicoTipo = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
		}catch(ControladorException e){
			throw new ControladorException("erro.sistema", e);
		}

		ServicoTipo servicoTipo = collectionServicoTipo.iterator().next();

		if(servicoTipo.getServicoTipoSubgrupo().getId() != null
						&& (servicoTipo.getServicoTipoSubgrupo().getId().equals(ServicoTipoSubgrupo.SUBSTITUICAO_HIDROMETRO) || servicoTipo
										.getServicoTipoSubgrupo().getId().equals(ServicoTipoSubgrupo.MANUTENCAO_HIDROMETRO))
						|| servicoTipo.getServicoTipoSubgrupo().getId().equals(ServicoTipoSubgrupo.RETIRADA_HIDROMETRO)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * [UC0430] Gerar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 17/08/2006
	 */
	public OrdemServico pesquisarOrdemServico(Integer id) throws ControladorException{

		FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("osReferencia");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento.registroAtendimentoSolicitantes");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipo.servicoTipoReferencia.servicoTipo");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipo.servicoTipoPrioridade");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipo.ordemServicoLayout");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipoReferencia");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipoReferencia.servicoTipoReferencia");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipoPrioridadeAtual");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipoPrioridadeOriginal");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("imovel");

		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, id.toString()));

		return pesquisar(filtroOrdemServico, OrdemServico.class);
	}

	private <T> T pesquisar(Filtro filtro, Class clazz) throws ControladorException{

		T objeto = null;

		Collection colecao = getControladorUtil().pesquisar(filtro, clazz.getName());

		if(colecao != null && !colecao.isEmpty()){
			objeto = (T) colecao.iterator().next();
		}
		return objeto;
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
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

	/**
	 * [UC0471] Obter Dados da Equipe
	 * 
	 * @author Raphael Rossiter
	 * @date 01/09/2006
	 * @return idQuipe
	 * @throws ControladorException
	 */
	public ObterDadosEquipe obterDadosEquipe(Integer idEquipe) throws ControladorException{

		ObterDadosEquipe retorno = null;

		try{

			Collection colecaoEquipe = repositorioOrdemServico.pesquisarEquipe(idEquipe);

			if(colecaoEquipe != null && !colecaoEquipe.isEmpty()){

				retorno = new ObterDadosEquipe();

				// Dados da Equipe
				Object[] arrayEquipe = (Object[]) colecaoEquipe.iterator().next();

				Equipe equipe = new Equipe();
				UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
				ServicoPerfilTipo servicoPerfilTipo = new ServicoPerfilTipo();
				EquipeTipo equipeTipo = new EquipeTipo();

				equipe.setId((Integer) arrayEquipe[0]);
				equipe.setNome((String) arrayEquipe[1]);

				if(arrayEquipe[2] != null){
					equipe.setPlacaVeiculo((String) arrayEquipe[2]);
				}

				if(arrayEquipe[3] != null){
					equipe.setCargaTrabalho((Integer) arrayEquipe[3]);
				}

				unidadeOrganizacional.setId((Integer) arrayEquipe[4]);
				unidadeOrganizacional.setDescricao((String) arrayEquipe[5]);

				equipe.setUnidadeOrganizacional(unidadeOrganizacional);

				servicoPerfilTipo.setId((Integer) arrayEquipe[6]);
				servicoPerfilTipo.setDescricao((String) arrayEquipe[7]);

				equipe.setServicoPerfilTipo(servicoPerfilTipo);

				equipeTipo.setId((Integer) arrayEquipe[8]);
				equipeTipo.setDescricao((String) arrayEquipe[9]);

				equipe.setEquipeTipo(equipeTipo);

				retorno.setEquipe(equipe);

				Collection colecaoEquipeComponentes = repositorioOrdemServico.pesquisarEquipeComponentes(idEquipe);

				if(colecaoEquipeComponentes != null && !colecaoEquipeComponentes.isEmpty()){

					// Dados dos componenetes da equipe
					Collection colecaoEquipeComponentesFinal = new ArrayList();

					Iterator iteratorComponentes = colecaoEquipeComponentes.iterator();
					EquipeComponentes equipeComponentes = null;
					Funcionario funcionario = null;
					Object[] arrayEquipeComponentes = null;

					while(iteratorComponentes.hasNext()){

						arrayEquipeComponentes = (Object[]) iteratorComponentes.next();

						equipeComponentes = new EquipeComponentes();

						equipeComponentes.setEquipe(equipe);

						equipeComponentes.setId((Integer) arrayEquipeComponentes[0]);
						equipeComponentes.setIndicadorResponsavel((Short) arrayEquipeComponentes[1]);

						if(arrayEquipeComponentes[2] != null){
							funcionario = new Funcionario();

							funcionario.setId((Integer) arrayEquipeComponentes[2]);
							funcionario.setNome((String) arrayEquipeComponentes[3]);

							equipeComponentes.setFuncionario(funcionario);
						}

						if(arrayEquipeComponentes[4] != null){
							equipeComponentes.setComponentes((String) arrayEquipeComponentes[4]);
						}

						colecaoEquipeComponentesFinal.add(equipeComponentes);
					}

					retorno.setColecaoEquipeComponentes(colecaoEquipeComponentesFinal);
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 04/09/2006
	 * @author Saulo Lima
	 * @date 26/02/2009
	 *       Correção caso existam mais de 1000 RAs para uma unidadeLotacao
	 */
	public Collection pesquisarTipoServicoDisponivelPorCriterio(UnidadeOrganizacional unidadeLotacao, int tipoCriterio, int origemServico)
					throws ControladorException{

		Set retorno = new CopyOnWriteArraySet();

		Short permiteTramiteIndependente = Short.parseShort((String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE
						.executar(ExecutorParametrosAtendimentoPublico.getInstancia()));

		Collection colecao = new ArrayList();
		try{

			switch(tipoCriterio){

				case 1:

					// Consulta somente o servico solicitado
					if(origemServico == 1){

						colecao = this.repositorioOrdemServico
										.pesquisarServicoTipoPorRA(unidadeLotacao.getId(), permiteTramiteIndependente);
						retorno.addAll(colecao);
						break;

						// Consulta somente o servico seletivo
					}else if(origemServico == 2){

						colecao = this.repositorioOrdemServico.pesquisarServicoTipoPorUnidade(unidadeLotacao.getId());
						retorno.addAll(colecao);
						break;

						// Consulta ambas origens de serviço
					}else{

						retorno.addAll(this.repositorioOrdemServico.pesquisarServicoTipoPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente));
						colecao = this.repositorioOrdemServico.pesquisarServicoTipoPorUnidade(unidadeLotacao.getId());
						retorno.addAll(this.removeObjetoRepetido(retorno, colecao));
						break;
					}

				case 2:

					colecao = this.repositorioOrdemServico.pesquisarServicoPerfilTipoPorUnidade(unidadeLotacao.getId(), origemServico,
									permiteTramiteIndependente);
					retorno.addAll(colecao);
					break;

				case 3:

					// Consulta somente o servico solicitado
					if(origemServico == 1){

						colecao = this.repositorioOrdemServico.pesquisarUnidadeOrganizacionalPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente);
						retorno.addAll(colecao);
						break;

						// Consulta somente o servico seletivo
					}else if(origemServico == 2){

						colecao = this.repositorioOrdemServico.pesquisarUnidadeOrganizacionalPorUnidade(unidadeLotacao.getId());
						retorno.addAll(colecao);
						break;

						// Consulta ambas origens de serviço
					}else{

						retorno.addAll(this.repositorioOrdemServico.pesquisarUnidadeOrganizacionalPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente));
						colecao = this.repositorioOrdemServico.pesquisarUnidadeOrganizacionalPorUnidade(unidadeLotacao.getId());
						retorno.addAll(this.removeObjetoRepetido(retorno, colecao));
						break;
					}

				case 4:

					// Consulta somente o servico solicitado
					if(origemServico == ConstantesSistema.SIM.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarLocalidadePorRA(unidadeLotacao.getId(), permiteTramiteIndependente);
						retorno.addAll(colecao);
						break;

						// Consulta somente o servico seletivo
					}else if(origemServico == ConstantesSistema.NAO.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarLocalidadePorUnidade(unidadeLotacao.getId());
						retorno.addAll(colecao);
						break;

						// Consulta ambas origens de serviço
					}else{

						retorno.addAll(this.repositorioOrdemServico.pesquisarLocalidadePorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente));
						colecao = this.repositorioOrdemServico.pesquisarLocalidadePorUnidade(unidadeLotacao.getId());
						retorno.addAll(this.removeObjetoRepetido(retorno, colecao));
						break;
					}

				case 5:

					// Consulta somente o servico solicitado
					if(origemServico == ConstantesSistema.SIM.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarSetorComercialPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente);
						retorno.addAll(colecao);
						break;

						// Consulta somente o servico seletivo
					}else if(origemServico == ConstantesSistema.NAO.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarSetorComercialPorUnidade(unidadeLotacao.getId());
						retorno.addAll(colecao);
						break;

						// Consulta ambas origens de serviço
					}else{

						retorno.addAll(this.repositorioOrdemServico.pesquisarSetorComercialPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente));
						colecao = this.repositorioOrdemServico.pesquisarSetorComercialPorUnidade(unidadeLotacao.getId());
						retorno.addAll(this.removeObjetoRepetido(retorno, colecao));
						break;
					}

				case 6:

					// Consulta somente o servico solicitado
					if(origemServico == ConstantesSistema.SIM.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarDistritoOperacionalPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente);
						retorno.addAll(colecao);
						break;

						// Consulta somente o servico seletivo
					}else if(origemServico == ConstantesSistema.NAO.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarDistritoOperacionalPorUnidade(unidadeLotacao.getId());
						retorno.addAll(colecao);
						break;

						// Consulta ambas origens de serviço
					}else{

						retorno.addAll(this.repositorioOrdemServico.pesquisarDistritoOperacionalPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente));
						colecao = this.repositorioOrdemServico.pesquisarDistritoOperacionalPorUnidade(unidadeLotacao.getId());
						retorno.addAll(this.removeObjetoRepetido(retorno, colecao));
						break;

					}
				case 7:

					// Consulta somente o servico solicitado
					if(origemServico == ConstantesSistema.SIM.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarBairroPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente);
						retorno.addAll(colecao);
						break;

						// Consulta somente o servico seletivo
					}else if(origemServico == ConstantesSistema.NAO.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarBairroPorUnidade(unidadeLotacao.getId());
						retorno.addAll(colecao);
						break;

						// Consulta ambas origens de serviço
					}else{

						retorno.addAll(this.repositorioOrdemServico.pesquisarBairroPorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente));
						colecao = this.repositorioOrdemServico.pesquisarBairroPorUnidade(unidadeLotacao.getId());
						retorno.addAll(this.removeObjetoRepetido(retorno, colecao));
						break;

					}
				case 8:

					// Consulta somente o servico solicitado
					if(origemServico == ConstantesSistema.SIM.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarServicoTipoPrioridadePorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente);
						retorno.addAll(colecao);
						break;

						// Consulta somente o servico seletivo
					}else if(origemServico == ConstantesSistema.NAO.intValue()){

						colecao = this.repositorioOrdemServico.pesquisarServicoTipoPrioridadePorUnidade(unidadeLotacao.getId());
						retorno.addAll(colecao);
						break;

						// Consulta ambas origens de serviço
					}else{

						retorno.addAll(this.repositorioOrdemServico.pesquisarServicoTipoPrioridadePorRA(unidadeLotacao.getId(),
										permiteTramiteIndependente));
						colecao = this.repositorioOrdemServico.pesquisarServicoTipoPrioridadePorUnidade(unidadeLotacao.getId());
						retorno.addAll(this.removeObjetoRepetido(retorno, colecao));
						break;

					}
			}// fim do switch

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * Remove Objetos Repetidos na colecao passada Objetos: ServicoTipo ServicoPerfilTipo
	 * UnidadeOrganizacional Localidade SetorComercial
	 * DistritoOperacional
	 */
	private Collection removeObjetoRepetido(Collection oldCollection, Collection newCollection){

		Iterator oldItera = oldCollection.iterator();
		while(oldItera.hasNext()){
			Object old = (Object) oldItera.next();

			Iterator newItera = newCollection.iterator();

			while(newItera.hasNext()){

				Object novo = (Object) newItera.next();

				if(old instanceof ServicoTipo){

					if(((ServicoTipo) old).getId().intValue() == ((ServicoTipo) novo).getId().intValue()){
						((ServicoTipo) old).setQtidadeOs(((ServicoTipo) old).getQtidadeOs() + ((ServicoTipo) novo).getQtidadeOs());
						newItera.remove();
					}

				}else if(old instanceof ServicoPerfilTipo){

					if(((ServicoPerfilTipo) old).getId().intValue() == ((ServicoPerfilTipo) novo).getId().intValue()){
						((ServicoPerfilTipo) old).setQtidadeOs(((ServicoPerfilTipo) old).getQtidadeOs()
										+ ((ServicoPerfilTipo) novo).getQtidadeOs());
						newItera.remove();
					}

				}else if(old instanceof UnidadeOrganizacional){

					if(((UnidadeOrganizacional) old).getId().intValue() == ((UnidadeOrganizacional) novo).getId().intValue()){
						((UnidadeOrganizacional) old).setQtidadeOs(((UnidadeOrganizacional) old).getQtidadeOs()
										+ ((UnidadeOrganizacional) novo).getQtidadeOs());
						newItera.remove();
					}

				}else if(old instanceof Localidade){

					if(((Localidade) old).getId().intValue() == ((Localidade) novo).getId().intValue()){
						((Localidade) old).setQtidadeOs(((Localidade) old).getQtidadeOs() + ((Localidade) novo).getQtidadeOs());
						newItera.remove();
					}

				}else if(old instanceof SetorComercial){
					if(((SetorComercial) old).getId().intValue() == ((SetorComercial) novo).getId().intValue()){
						((SetorComercial) old).setQtidadeOs(((SetorComercial) old).getQtidadeOs() + ((SetorComercial) novo).getQtidadeOs());
						newItera.remove();
					}

				}else if(old instanceof DistritoOperacional){

					if(((DistritoOperacional) old).getId().intValue() == ((DistritoOperacional) novo).getId().intValue()){
						((DistritoOperacional) old).setQtidadeOs(((DistritoOperacional) old).getQtidadeOs()
										+ ((DistritoOperacional) novo).getQtidadeOs());
						newItera.remove();
					}

				}
			}
		}
		return newCollection;
	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico
	 * [SB0003] Seleciona Ordem de Servico por Criterio de Seleção [SB0004]
	 * Seleciona Ordem de Servico por Situacao de Diagnostico [SB0005] Seleciona
	 * Ordem de Servico por Situacao de Acompanhamento pela Agencia [SB0006]
	 * Seleciona Ordem de Servico por Critério Geral
	 * 
	 * @author Rafael Pinto
	 * @date 07/09/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdemServicoElaborarProgramacao(PesquisarOrdemServicoHelper filtro)
					throws ControladorException{

		try{

			Short permiteTramiteIndependente = Short.parseShort((String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE
											.executar(ExecutorParametrosAtendimentoPublico.getInstancia()));

			return this.repositorioOrdemServico.pesquisarOrdemServicoElaborarProgramacao(filtro, permiteTramiteIndependente);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0371]Inserir Equipe no sistema.
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipe
	 * @throws ControladorException
	 */
	public long inserirEquipe(Equipe equipe) throws ControladorException{

		// Insere a equipe na base
		getControladorUtil().inserir(equipe);

		return equipe.getId();
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * inserção da equipe.
	 * [FS0007] Verificar quantidade de indicador de responsável Validar Carga
	 * Horária Máxima Validar Placa do Veículo
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipe
	 */
	public void validarInsercaoEquipe(Equipe equipe) throws ControladorException{

		// Verificar objeto a ser inserido na base.
		if(equipe != null){
			// Verificar Carga Horária Máxima.
			int cargaHoraria = equipe.getCargaTrabalho() / 60;
			if(cargaHoraria > Equipe.CARGA_HORARIA_MAXIMA){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.inserir_equipe_carga_horaria_maxima", null, "");
			}

			// Validar Placa do Veículo
			if(equipe.getPlacaVeiculo() != null && !equipe.getPlacaVeiculo().equals("")){
				if(equipe.getPlacaVeiculo().length() != 7){
					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.inserir_equipe_placa_veiculo_invalida", null, "");
				}
				// Padrões da expressão regular
				String LETRAS_PLACA_MASK = "^[a-zA-Z]*$";
				String DIGITOS_PLACA_MASK = "^[0-9]*$";
				// Pega os 3 primeiros caractes
				String letras = equipe.getPlacaVeiculo().substring(0, 3);
				String digitos = equipe.getPlacaVeiculo().substring(3, 7);
				// Compara a placa com as expressões regulares
				boolean letrasValidas = Util.validateMask(letras, LETRAS_PLACA_MASK);
				boolean digitosValidos = Util.validateMask(digitos, DIGITOS_PLACA_MASK);
				if(!letrasValidas || !digitosValidos){
					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.inserir_equipe_placa_veiculo_invalida", null, "");
				}
			}
			// Caracteres no nome da equipe
			String nome = equipe.getNome();
			if(nome.contains("/") || nome.contains(" ") || nome.contains("-")){
				throw new ControladorException("atencao.campo_texto.caracter_invalido", null, "Nome da equipe");
			}
			// Equipe com o mesmo nome
			FiltroEquipe filtro = new FiltroEquipe();
			filtro.adicionarParametro(new ParametroSimples(FiltroEquipe.NOME, equipe.getNome()));
			filtro.adicionarParametro(new ParametroSimples(FiltroEquipe.ID_UNIDADE_ORGANIZACIONAL, equipe.getUnidadeOrganizacional()
							.getId()));
			Collection colecao = getControladorUtil().pesquisar(filtro, Equipe.class.getName());

			if(colecao != null && !colecao.isEmpty()){
				throw new ControladorException("atencao.inserir_equipe_mesmo_nome");
			}

		}else{
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.inserir_equipe_inválida", null, "");
		}
	}

	/**
	 * Inserir Componentes da Equipe no sistema.
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipeComponentes
	 * @throws ControladorException
	 */
	public long inserirEquipeComponentes(EquipeComponentes equipeComponentes) throws ControladorException{

		// Insere o componente da equipe na base
		getControladorUtil().inserir(equipeComponentes);

		return equipeComponentes.getId();
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * inserção de componentes da equipe.
	 * [FS0003] Validar equipe componente já existente [FS0004] Verificar
	 * existência do funcionário [FS0006] Verificar quantidade de componentes da
	 * equipe em Tipo Perfil Serviço
	 * 
	 * @author Leonardo Regis
	 * @date 25/07/2006
	 * @param equipeComponentes
	 */
	public void validarExibirInsercaoEquipeComponentes(Collection colecaoEquipeComponentes, EquipeComponentes equipeComponentes)
					throws ControladorException{

		// Verificar objeto a ser inserido na base.
		if(equipeComponentes != null){
			// Testar se novo componente pode ser inserido na coleção
			if(colecaoEquipeComponentes != null && !colecaoEquipeComponentes.isEmpty()){
				// Varre coleção de componentes da grid (ainda não inseridos na
				// base)
				for(Iterator iter = colecaoEquipeComponentes.iterator(); iter.hasNext();){
					EquipeComponentes element = (EquipeComponentes) iter.next();
					// [FS0003] Validar equipe componente já existente
					if(element.getFuncionario() != null && equipeComponentes.getFuncionario() != null){
						if(element.getFuncionario().getId().intValue() == equipeComponentes.getFuncionario().getId().intValue()){
							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException("atencao.inserir_equipe_componente_ja_informado", null, "");
						}
					}
					// [FS0007] Verificar quantidade de indicador de responsável
					if(equipeComponentes.getIndicadorResponsavel() == EquipeComponentes.INDICADOR_RESPONSAVEL_SIM){
						if(element.getIndicadorResponsavel() == equipeComponentes.getIndicadorResponsavel()){
							try{
								sessionContext.setRollbackOnly();
							}catch(IllegalStateException ex){

							}
							throw new ControladorException("atencao.inserir_equipe_um_responsavel", null, "");
						}
					}
				}
			}
		}else{
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.inserir_equipe_componente_invalido", null, "");
		}
	}

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * inserção de componentes da equipe.
	 * [FS0006] Verificar quantidade de componentes da equipe em Tipo Perfil
	 * Serviço Validar se possui algum responsável
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param equipeComponentes
	 * @throws ControladorException
	 */
	public void validarInsercaoEquipeComponentes(Collection colecaoEquipeComponentes, Equipe equipe) throws ControladorException{

		// Testar se novo componente pode ser inserido na coleção
		if(colecaoEquipeComponentes != null && !colecaoEquipeComponentes.isEmpty()){
			boolean achouResponsavel = false;
			// Varre coleção de componentes da grid (ainda não inseridos na
			// base)
			for(Iterator iter = colecaoEquipeComponentes.iterator(); iter.hasNext();){
				EquipeComponentes element = (EquipeComponentes) iter.next();
				// [FS0007] Verificar quantidade de indicador de responsável
				if(element.getIndicadorResponsavel() == EquipeComponentes.INDICADOR_RESPONSAVEL_SIM){
					achouResponsavel = true;
					break;
				}
			}
			if(!achouResponsavel){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.inserir_equipe_nenhum_responsavel", null, "");
			}

			validaEquipeComponenteEquipeTIpo(colecaoEquipeComponentes, equipe);

		}
	}

	private void validaEquipeComponenteEquipeTIpo(Collection colecaoEquipeComponentes, Equipe equipe) throws ControladorException{

		EquipeTipo equipeTipo = null;
		if(equipe.getEquipeTipo() != null && equipe.getEquipeTipo().getId() != null
						&& equipe.getEquipeTipo().getId() != Integer.valueOf(ConstantesSistema.VALOR_NAO_INFORMADO).intValue()){
			FiltroEquipeTipo filtroEquipeTipo = new FiltroEquipeTipo();
			filtroEquipeTipo.adicionarParametro(new ParametroSimples(FiltroEquipeTipo.ID, equipe.getEquipeTipo().getId()));
			equipeTipo = (EquipeTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroEquipeTipo,
							EquipeTipo.class.getName()));
			equipe.setEquipeTipo(equipeTipo);
		}

		String mensagem = "";
		for(Iterator iter = colecaoEquipeComponentes.iterator(); iter.hasNext();){
			EquipeComponentes element = (EquipeComponentes) iter.next();
			if(element.getFuncionario() != null && element.getFuncionario().getId() != null){
				FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
				filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, element.getFuncionario().getId()));
				filtroFuncionario.adicionarCaminhoParaCarregamentoEntidade("equipeTipo");
				Collection colecaoFuncionario = getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());
				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);
				if(funcionario != null && funcionario.getEquipeTipo() != null && equipe.getEquipeTipo() != null
								&& funcionario.getEquipeTipo().getId() != null && equipe.getEquipeTipo().getId() != null
								&& equipe.getEquipeTipo().getId() != Integer.valueOf(ConstantesSistema.VALOR_NAO_INFORMADO).intValue()
								&& funcionario.getEquipeTipo().getId().intValue() != equipe.getEquipeTipo().getId().intValue()){

					mensagem = mensagem + " Funcionario: " + funcionario.getNome() + " - Tipo Equipe: "
									+ funcionario.getEquipeTipo().getDescricao() + " ";
				}
			}

		}
		if(!mensagem.equals("")){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}

			throw new ControladorException("atencao.inserir_equipe_tipo_equipe_diferente_funcionario", null, equipeTipo
							.getDescricao(), mensagem);
		}
	}

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * 
	 * @author Leonardo Regis
	 * @date 09/09/2006
	 * @param idOS
	 * @return Collection<ObterDadosAtividadesOSHelper>
	 * @throws ControladorException
	 */
	public Collection<ObterDadosAtividadesOSHelper> obterDadosAtividadesOS(Integer idOS) throws ControladorException{

		Collection<ObterDadosAtividadesOSHelper> colecaoObterDadosAtividadesOSHelper = new ArrayList();
		ObterDadosAtividadesOSHelper obterDadosAtividadesOSHelper = null;
		try{
			Collection<OsAtividadeMaterialExecucao> colecaoOsAtividadeMaterialExecucao = this.repositorioOrdemServico
							.obterOsAtividadeMaterialExecucaoPorOS(idOS);
			if(colecaoOsAtividadeMaterialExecucao != null && !colecaoOsAtividadeMaterialExecucao.isEmpty()){
				for(OsAtividadeMaterialExecucao material : colecaoOsAtividadeMaterialExecucao){
					obterDadosAtividadesOSHelper = new ObterDadosAtividadesOSHelper();
					obterDadosAtividadesOSHelper.setMaterial(true);
					obterDadosAtividadesOSHelper.setAtividade(material.getOrdemServicoAtividade().getAtividade());
					obterDadosAtividadesOSHelper.setMaterial(material.getMaterial());
					obterDadosAtividadesOSHelper.setMaterialUnidade(material.getMaterial().getMaterialUnidade());
					obterDadosAtividadesOSHelper.setQtdeMaterial(material.getQuantidadeMaterial());
					obterDadosAtividadesOSHelper.setMaterialValor(material.getValorMaterial());
					colecaoObterDadosAtividadesOSHelper.add(obterDadosAtividadesOSHelper);
				}
			}
			Collection<OsExecucaoEquipe> colecaoOsExecucaoEquipe = this.repositorioOrdemServico.obterOsExecucaoEquipePorOS(idOS);
			if(colecaoOsExecucaoEquipe != null && !colecaoOsExecucaoEquipe.isEmpty()){
				for(OsExecucaoEquipe periodo : colecaoOsExecucaoEquipe){
					obterDadosAtividadesOSHelper = new ObterDadosAtividadesOSHelper();
					obterDadosAtividadesOSHelper.setPeriodo(true);
					obterDadosAtividadesOSHelper.setAtividade(periodo.getOsAtividadePeriodoExecucao().getOrdemServicoAtividade()
									.getAtividade());
					obterDadosAtividadesOSHelper.setDataInicio(periodo.getOsAtividadePeriodoExecucao().getDataInicio());
					obterDadosAtividadesOSHelper.setDataFim(periodo.getOsAtividadePeriodoExecucao().getDataFim());
					obterDadosAtividadesOSHelper.setEquipe(periodo.getEquipe());
					obterDadosAtividadesOSHelper.setValorAtividadePeriodo(periodo.getOsAtividadePeriodoExecucao()
									.getValorAtividadePeriodo());
					colecaoObterDadosAtividadesOSHelper.add(obterDadosAtividadesOSHelper);
				}
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return colecaoObterDadosAtividadesOSHelper;
	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @param dataRoteiro
	 * @return collection
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiro(Date dataRoteiro) throws ControladorException{

		try{
			return this.repositorioOrdemServico.recuperaOSProgramacaoPorDataRoteiro(dataRoteiro);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0479] Gerar Débito da Ordem de Serviço
	 * [FS0001] Verificar Existência da Ordem de Serviço [FS0002] Verificar
	 * Existência do Tipo de Débito [FS0003] Validar Valor do Débito [FS0004]
	 * Validar Quantidade de Parcelas
	 * 
	 * @author Leonardo Regis
	 * @date 11/09/2006
	 * @author eduardo henrique
	 * @date 05/11/2008
	 *       Alteração no método para chamada da inserção dos Debitos a Cobrar categoria pelo método
	 *       do ControladorFaturamento e
	 *       Contabilização do Débito a Cobrar Gerado
	 * @param ordemServicoId
	 * @param tipoDebitoId
	 * @param valorDebito
	 * @param qtdeParcelas
	 * @throws ControladorException
	 */
	public void gerarDebitoOrdemServico(Integer ordemServicoId, Integer tipoDebitoId, BigDecimal valorDebito, int qtdeParcelas)
					throws ControladorException{

		try{
			
			OrdemServico os = repositorioOrdemServico.recuperaOSPorId(ordemServicoId);
			// [FS0001]
			Imovel imovel = null;
			if(os == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.os_inexistente");
			}else{
				imovel = os.getImovel();
				if(imovel == null){
					if(os.getRegistroAtendimento().getImovel() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.os_sem_imovel");
					}else{
						imovel = os.getRegistroAtendimento().getImovel();
					}
				}
			}
			// [FS0002]
			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, tipoDebitoId));
			// Recupera Cliente
			Collection colecaoDebitoTipo = this.getControladorUtil().pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
			DebitoTipo debitoTipo = null;
			if(colecaoDebitoTipo != null && !colecaoDebitoTipo.isEmpty()){
				debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);
			}else{
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.tipo_debito_inexistente");
			}
			// [FS0003]
			if(valorDebito.intValue() > 0){

				// [FS0004]
				if(qtdeParcelas <= 0){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.qtde_parcelas_invalida");
				}

				/*
				 * Colocado por Raphael Rossiter em 27/04/2007
				 * Caso o serviço associado à ordem de serviço tenha a informação
				 * que é para colocar juros e a quantidade de parcelas for maior que
				 * 1(um)
				 */
				BigDecimal taxaJurosFinanciamento = null;

				if(os.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue() && qtdeParcelas > 1){

					SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
					taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();

					// [FS0005] - Validar Juros de Financiamento
					if(taxaJurosFinanciamento != null && taxaJurosFinanciamento.compareTo(new BigDecimal("0.00")) == 1){

						// [UC0186] - Calcular Prestação
						BigDecimal valorPrestacao = this.getControladorFaturamento().calcularPrestacao(taxaJurosFinanciamento,
										qtdeParcelas, valorDebito, new BigDecimal("0.00"));

						valorDebito = valorPrestacao.multiply(new BigDecimal(qtdeParcelas));
					}
				}

				// Collection<ObjetoTransacao> colecaoDebitoACobrarContabilizar = new
				// ArrayList<ObjetoTransacao>();

				// Insere débito a cobrar geral
				DebitoACobrarGeral debitoACobrarGeral = inserirDebitoACobrarGeral();

				//
				// valorDebito = valorDebito.setScale(2,BigDecimal.ROUND_HALF_UP);

				// Insere débito a cobrar
				DebitoACobrar debitoACobrar = inserirDebitoACobrar(valorDebito, qtdeParcelas, os, debitoTipo, debitoACobrarGeral,
								taxaJurosFinanciamento, null, null);

				this.getControladorFaturamento().inserirDebitoACobrarCategoria(debitoACobrar, debitoACobrar.getImovel());

				// INSERIR CLIENTE_DEBITO_A_COBRAR
				this.getControladorCobranca().inserirClienteDebitoACobrar(debitoACobrar);

				this.getControladorContabil().registrarLancamentoContabil(debitoACobrar, OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

				// colecaoDebitoACobrarContabilizar.add(debitoACobrar);
				// Contabiliza o Débito a Cobrar Incluído
				// getControladorFinanceiro().contabilizarEventoFaturamento(colecaoDebitoACobrarContabilizar,
				// EventoContabil.INCLUSAO_DEBITO_A_COBRAR);

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0479] Gerar Débito da Ordem de Serviço [FS0001] Verificar Existência
	 * da Ordem de Serviço [FS0002] Verificar Existência do Tipo de Débito
	 * [FS0003] Validar Valor do Débito [FS0004] Validar Quantidade de Parcelas
	 * 
	 * @author Leonardo Regis
	 * @date 11/09/2006
	 * @author eduardo henrique
	 * @date 05/11/2008 Alteração no método para chamada da inserção dos Debitos
	 *       a Cobrar categoria pelo método do ControladorFaturamento e
	 *       Contabilização do Débito a Cobrar Gerado
	 * @param ordemServicoId
	 * @param tipoDebitoId
	 * @param valorDebito
	 * @param qtdeParcelas
	 * @throws ControladorException
	 */
	public void gerarDebitoOrdemServicoSancao(Integer ordemServicoId, Integer tipoDebitoId, BigDecimal valorDebito, int qtdeParcelas,
					Integer numeroDiasSuspensao, OcorrenciaInfracaoItem ocorrenciaInfracaoItem) throws ControladorException{

		try{
			OrdemServico os = this.repositorioOrdemServico.recuperaOSPorId(ordemServicoId);
			// [FS0001]
			Imovel imovel = null;
			if(os == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.os_inexistente");
			}else{
				imovel = os.getImovel();
				if(imovel == null){
					if(os.getRegistroAtendimento().getImovel() == null){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.os_sem_imovel");
					}else{
						imovel = os.getRegistroAtendimento().getImovel();
					}
				}
			}
			// [FS0002]
			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, tipoDebitoId));
			// Recupera Cliente
			Collection colecaoDebitoTipo = this.getControladorUtil().pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());
			DebitoTipo debitoTipo = null;
			if(colecaoDebitoTipo != null && !colecaoDebitoTipo.isEmpty()){
				debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);
			}else{
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.tipo_debito_inexistente");
			}
			// [FS0003]
			if(valorDebito.intValue() <= 0){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.valor_debito_invalido");
			}
			// [FS0004]
			if(qtdeParcelas <= 0){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.qtde_parcelas_invalida");
			}

			/*
			 * Colocado por Raphael Rossiter em 27/04/2007
			 * Caso o serviço associado à ordem de serviço tenha a informação
			 * que é para colocar juros e a quantidade de parcelas for maior que
			 * 1(um)
			 */
			BigDecimal taxaJurosFinanciamento = null;

			if(os.getServicoTipo().getIndicadorCobrarJuros() == ConstantesSistema.SIM.shortValue() && qtdeParcelas > 1){

				SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
				taxaJurosFinanciamento = sistemaParametro.getPercentualTaxaJurosFinanciamento();

				// [FS0005] - Validar Juros de Financiamento
				if(taxaJurosFinanciamento != null && taxaJurosFinanciamento.compareTo(new BigDecimal("0.00")) == 1){

					// [UC0186] - Calcular Prestação
					BigDecimal valorPrestacao = this.getControladorFaturamento().calcularPrestacao(taxaJurosFinanciamento, qtdeParcelas,
									valorDebito, new BigDecimal("0.00"));

					valorDebito = valorPrestacao.multiply(new BigDecimal(qtdeParcelas));
				}
			}

			// Collection<ObjetoTransacao> colecaoDebitoACobrarContabilizar = new
			// ArrayList<ObjetoTransacao>();

			// Insere débito a cobrar geral
			DebitoACobrarGeral debitoACobrarGeral = inserirDebitoACobrarGeral();

			//
			// valorDebito = valorDebito.setScale(2,BigDecimal.ROUND_HALF_UP);

			// Insere débito a cobrar
			DebitoACobrar debitoACobrar = inserirDebitoACobrar(valorDebito, qtdeParcelas, os, debitoTipo, debitoACobrarGeral,
							taxaJurosFinanciamento, numeroDiasSuspensao, ocorrenciaInfracaoItem);

			getControladorFaturamento().inserirDebitoACobrarCategoria(debitoACobrar, debitoACobrar.getImovel());

			// INSERIR CLIENTE_DEBITO_A_COBRAR
			this.getControladorCobranca().inserirClienteDebitoACobrar(debitoACobrar);

			this.getControladorContabil().registrarLancamentoContabil(debitoACobrar, OperacaoContabil.INCLUIR_DEBITO_A_COBRAR);

			// colecaoDebitoACobrarContabilizar.add(debitoACobrar);
			// Contabiliza o Débito a Cobrar Incluído
			// getControladorFinanceiro().contabilizarEventoFaturamento(colecaoDebitoACobrarContabilizar,
			// EventoContabil.INCLUSAO_DEBITO_A_COBRAR);

			/*
			 * // Recupera Categorias por Imóvel
			 * Collection<Categoria> colecaoCategoria = this
			 * .getControladorImovel().obterQuantidadeEconomiasCategoria(
			 * imovel);
			 * // Recupera Valores por Categorias
			 * Collection<BigDecimal> colecaoValoresCategorias = this
			 * .getControladorImovel().obterValorPorCategoria(
			 * colecaoCategoria, valorDebito);
			 * // Insere débito a cobrar por categoria
			 * inserirDebitoACobrarCategoria(colecaoCategoria,
			 * colecaoValoresCategorias, debitoACobrar);
			 */
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0479] Gerar Débito da Ordem de Serviço
	 * Persiste Debito a Cobrar Categoria
	 * 
	 * @author Leonardo Regis
	 * @date 12/09/2006
	 * @param colecaoCategoria
	 * @param colecaoValores
	 * @throws ControladorException
	 */
	private void inserirDebitoACobrarCategoria(Collection<Categoria> colecaoCategoria, Collection<BigDecimal> colecaoValoresCategorias,
					DebitoACobrar debitoACobrar) throws ControladorException{

		DebitoACobrarCategoria debitoACobrarCategoria = null;
		BigDecimal valorCategoria = new BigDecimal(0);
		DebitoACobrarCategoriaPK debitoACobrarCategoriaPK = null;
		for(Categoria categoria : colecaoCategoria){
			debitoACobrarCategoriaPK = new DebitoACobrarCategoriaPK();
			debitoACobrarCategoriaPK.setCategoria(categoria);
			debitoACobrarCategoriaPK.setDebitoACobrar(debitoACobrar);

			debitoACobrarCategoria = new DebitoACobrarCategoria();
			debitoACobrarCategoria.setComp_id(debitoACobrarCategoriaPK);
			// debitoACobrarCategoria.setCategoria(categoria);
			debitoACobrarCategoria.setQuantidadeEconomia(categoria.getQuantidadeEconomiasCategoria());
			valorCategoria = colecaoValoresCategorias.iterator().next();
			debitoACobrarCategoria.setValorCategoria(valorCategoria);
			debitoACobrarCategoria.setUltimaAlteracao(new Date());
			// debitoACobrarCategoria.setDebitoACobrar(debitoACobrar);
			this.getControladorUtil().inserir(debitoACobrarCategoria);
		}
	}

	/**
	 * [UC0479] Gerar Débito da Ordem de Serviço
	 * Persiste Debito a Cobrar Geral
	 * 
	 * @author Leonardo Regos
	 * @date 12/09/2006
	 * @param debitoACobrar
	 * @throws ControladorException
	 */
	private DebitoACobrarGeral inserirDebitoACobrarGeral() throws ControladorException{

		DebitoACobrarGeral debitoACobrarGeral = new DebitoACobrarGeral();
		debitoACobrarGeral.setIndicadorHistorico(DebitoACobrarGeral.INDICADOR_NAO_POSSUI_HISTORICO);
		debitoACobrarGeral.setUltimaAlteracao(new Date());
		this.getControladorUtil().inserir(debitoACobrarGeral);

		return debitoACobrarGeral;
	}

	/**
	 * [UC0479] Gerar Débito da Ordem de Serviço
	 * Persiste Debito a Cobrar
	 * 
	 * @author Leonardo Regos
	 * @date 12/09/2006
	 * @param valorDebito
	 * @param qtdeParcelas
	 * @param os
	 * @param debitoTipo
	 * @return debitoACobrar
	 * @throws ControladorException
	 */
	private DebitoACobrar inserirDebitoACobrar(BigDecimal valorDebito, int qtdeParcelas, OrdemServico os, DebitoTipo debitoTipo,
					DebitoACobrarGeral debitoACobrarGeral, BigDecimal percentualTaxaJurosFinanciamento, Integer numeroDiasSuspensao,
					OcorrenciaInfracaoItem ocorrenciaInfracaoItem) throws ControladorException{

		DebitoACobrar debitoACobrar = new DebitoACobrar();
		debitoACobrar.setId(debitoACobrarGeral.getId());
		debitoACobrar.setDebitoACobrarGeral(debitoACobrarGeral);
		Imovel imovel = os.getImovel();
		if(imovel == null){
			imovel = os.getRegistroAtendimento().getImovel();
		}
		debitoACobrar.setNumeroDiasSuspensao(numeroDiasSuspensao);
		debitoACobrar.setOcorrenciaInfracaoItem(ocorrenciaInfracaoItem);
		debitoACobrar.setImovel(imovel);
		debitoACobrar.setDebitoTipo(debitoTipo);
		debitoACobrar.setGeracaoDebito(new Date());
		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		debitoACobrar.setAnoMesReferenciaDebito(sistemaParametro.getAnoMesFaturamento());
		debitoACobrar.setAnoMesCobrancaDebito(sistemaParametro.getAnoMesArrecadacao());
		debitoACobrar.setAnoMesReferenciaContabil(sistemaParametro.getAnoMesFaturamento());
		debitoACobrar.setValorDebito(valorDebito);
		debitoACobrar.setNumeroPrestacaoDebito(Integer.valueOf(qtdeParcelas).shortValue());
		debitoACobrar.setNumeroPrestacaoCobradas(Short.valueOf("0"));
		debitoACobrar.setLocalidade(imovel.getLocalidade());
		debitoACobrar.setQuadra(imovel.getQuadra());

		// Alterado por Raphael Rossiter em 16/01/2007
		// debitoACobrar.setCodigoSetorComercial(imovel.getSetorComercial().getId());
		debitoACobrar.setCodigoSetorComercial(imovel.getSetorComercial().getCodigo());

		// Colocado por Raphael Rossiter em 27/04/2007
		debitoACobrar.setPercentualTaxaJurosFinanciamento(percentualTaxaJurosFinanciamento);

		debitoACobrar.setNumeroQuadra(imovel.getQuadra().getNumeroQuadra());
		debitoACobrar.setNumeroLote(imovel.getLote());
		debitoACobrar.setNumeroSubLote(imovel.getSubLote());
		debitoACobrar.setPercentualTaxaJurosFinanciamento(new BigDecimal(0));
		debitoACobrar.setRegistroAtendimento(os.getRegistroAtendimento());
		debitoACobrar.setOrdemServico(os);
		debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());
		debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
		DebitoCreditoSituacao debitoCreditoSituacaoAtual = new DebitoCreditoSituacao();
		debitoCreditoSituacaoAtual.setId(DebitoCreditoSituacao.NORMAL);
		debitoACobrar.setDebitoCreditoSituacaoAtual(debitoCreditoSituacaoAtual);
		debitoACobrar.setDebitoCreditoSituacaoAnterior(null);
		debitoACobrar.setParcelamentoGrupo(null);
		CobrancaForma cobrancaForma = new CobrancaForma();
		cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);
		debitoACobrar.setCobrancaForma(cobrancaForma);
		debitoACobrar.setUltimaAlteracao(new Date());

		// [SB0001] - Verificar Marcação do Débito A Cobrar para Remuneração da Cobrança
		// Administrativa
		debitoACobrar.setIndicadorRemuneraCobrancaAdministrativa(this.getControladorFaturamento()
						.verificarMarcacaoRemuneracaoCobAdminstrativa(debitoACobrar.getImovel().getId(),
										debitoACobrar.getDebitoTipo().getId()));

		this.getControladorUtil().inserir(debitoACobrar);


		return debitoACobrar;
	}

	/**
	 * Faz o controle de concorrencia da programacao roteiro
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	private void verificarProgramacaoRoteiroControleConcorrencia(ProgramacaoRoteiro programacaoRoteiro) throws ControladorException{

		FiltroProgramacaoRoteiro filtroProgramacaoRoteiro = new FiltroProgramacaoRoteiro();
		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.ID, programacaoRoteiro.getId()));

		Collection colecaoProgramacaoRoteiro = getControladorUtil().pesquisar(filtroProgramacaoRoteiro, ProgramacaoRoteiro.class.getName());

		if(colecaoProgramacaoRoteiro == null || colecaoProgramacaoRoteiro.isEmpty()){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		ProgramacaoRoteiro programacaoRoteiroAtual = (ProgramacaoRoteiro) Util.retonarObjetoDeColecao(colecaoProgramacaoRoteiro);

		if(programacaoRoteiroAtual.getUltimaAlteracao().after(programacaoRoteiro.getUltimaAlteracao())){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * Faz o controle de concorrencia da Ordem de Servico Programação
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	private void verificarOrdemServicoProgramacaoControleConcorrencia(OrdemServicoProgramacao ordemServicoProgramacao)
					throws ControladorException{

		OrdemServicoProgramacao ordemServicoProgramacaoAtual;
		try{
			ordemServicoProgramacaoAtual = this.repositorioOrdemServico.pesquisarOSProgramacaoPorId(ordemServicoProgramacao.getId());

			if(ordemServicoProgramacaoAtual == null){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			if(ordemServicoProgramacaoAtual.getUltimaAlteracao().after(ordemServicoProgramacao.getUltimaAlteracao())){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);

		}

	}

	/**
	 * [UC0456] Elaborar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 13/09/2006
	 * @param colecaoOrdemServicoProgramacao
	 * @throws ControladorException
	 */
	public void elaboraRoteiro(Collection<OSProgramacaoHelper> colecaoOrdemServicoProgramacao, Usuario usuarioLogado)
					throws ControladorException{

		Iterator<OSProgramacaoHelper> iter = colecaoOrdemServicoProgramacao.iterator();

		while(iter.hasNext()){
			OSProgramacaoHelper helper = (OSProgramacaoHelper) iter.next();

			OrdemServicoProgramacao ordemServicoProgramacao = helper.getOrdemServicoProgramacao();
			ProgramacaoRoteiro programacao = ordemServicoProgramacao.getProgramacaoRoteiro();

			/*
			 * Identificar a maior sequência das Programações das Ordens de Seviço naquela data,
			 * para aquela Equipe
			 */
			Integer maiorSquencial;
			try{
				maiorSquencial = this.repositorioOrdemServico.maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(ordemServicoProgramacao
								.getEquipe().getId(), ordemServicoProgramacao.getProgramacaoRoteiro().getDataRoteiro());
			}catch(ErroRepositorioException e){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", e);
			}

			if(maiorSquencial == null){
				maiorSquencial = 1;
			}else{
				maiorSquencial++;
			}

			ordemServicoProgramacao.setNnSequencialProgramacao(maiorSquencial.shortValue());
			// *************************************

			this.incluirOrdemServicoEmProgramacao(ordemServicoProgramacao, programacao, usuarioLogado);
		}
	}

	/**
	 * Método responsável por Associar uma programação de OS com um roteiro.
	 * 
	 * @param ordemServicoProgramacao
	 *            - populada, e com atributo de ordemServico atribuído
	 * @param programacaoRoteiro
	 *            - programacao com data de Roteiro e Unidade Organizacional Atribuídos
	 * @param usuario
	 *            - usuario que está efetuando a operação.
	 * @throws ControladorException
	 * @author: André Nishimura
	 * @date: 27/05/2009
	 *        Correçao para gravar dados corretos da OS.
	 */
	private void incluirOrdemServicoEmProgramacao(OrdemServicoProgramacao ordemServicoProgramacao, ProgramacaoRoteiro progRoteiro,
					Usuario usuario) throws ControladorException{

		ProgramacaoRoteiro programacaoRoteiro = progRoteiro;
		if(ordemServicoProgramacao == null || ordemServicoProgramacao.getOrdemServico() == null || programacaoRoteiro == null){
			throw new IllegalArgumentException("erro.sistema");
		}

		try{
			// Filtro para programacao roteiro
			FiltroProgramacaoRoteiro filtroProgramacaoRoteiro = new FiltroProgramacaoRoteiro();
			filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.DATA_ROTEIRO, programacaoRoteiro
							.getDataRoteiro()));
			filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.UNIDADE_ORGANIZACIONAL_ID,
							programacaoRoteiro.getUnidadeOrganizacional().getId()));

			Collection colecaoProgramacao = this.getControladorUtil().pesquisar(filtroProgramacaoRoteiro,
							ProgramacaoRoteiro.class.getName());

			// [SB00015] - Inclui ou Altera Roteiro
			if(colecaoProgramacao == null || colecaoProgramacao.isEmpty()){
				programacaoRoteiro.setUltimaAlteracao(new Date());
				this.getControladorUtil().inserir(programacaoRoteiro);
			}else{
				programacaoRoteiro = (ProgramacaoRoteiro) Util.retonarObjetoDeColecao(colecaoProgramacao);
				programacaoRoteiro.setUltimaAlteracao(new Date());
				this.verificarProgramacaoRoteiroControleConcorrencia(programacaoRoteiro);
				this.getControladorUtil().atualizar(programacaoRoteiro);
			}

			// Colocado por Raphael Rossiter em 08/02/2007 (Mudança no UC)

			OrdemServico ordemServico = pesquisarOrdemServico(ordemServicoProgramacao.getOrdemServico().getId());
			ordemServico.setIndicadorProgramada(OrdemServico.PROGRAMADA);

			this.atualizarOrdemServico(ordemServico, usuario);

			// [SB00016] - Inclui Programacao
			ordemServicoProgramacao.setProgramacaoRoteiro(programacaoRoteiro);
			ordemServicoProgramacao.setUsuarioFechamento(null);
			ordemServicoProgramacao.setOsProgramNaoEncerMotivo(null);
			ordemServicoProgramacao.setIndicadorAtivo(ConstantesSistema.SIM);
			ordemServicoProgramacao.setUltimaAlteracao(new Date());
			ordemServicoProgramacao.setSituacaoFechamento(ConstantesSistema.NAO);

			this.getControladorUtil().inserir(ordemServicoProgramacao);
		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw e;
		}
	}

	/**
	 * [UC0462] Obter Dados das Atividades da OS
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param idOS
	 * @param idAtividade
	 * @param tipoAtividade
	 * @return Collection<ObterDadosAtividadeOSHelper>
	 * @throws ControladorException
	 */
	public Collection<ObterDadosAtividadeOSHelper> obterDadosAtividadeOS(Integer idOS, Integer idAtividade, int tipoAtividade)
					throws ControladorException{

		Collection<ObterDadosAtividadeOSHelper> colecaoObterDadosAtividadeOSHelper = new ArrayList();
		ObterDadosAtividadeOSHelper obterDadosAtividadeOSHelper = null;
		try{
			if(tipoAtividade == ObterDadosAtividadeOSHelper.INDICADOR_MATERIAL.intValue()){
				Collection<OsAtividadeMaterialExecucao> colecaoOsAtividadeMaterialExecucao = this.repositorioOrdemServico
								.obterOsAtividadeMaterialExecucaoPorOS(idOS, idAtividade);
				if(colecaoOsAtividadeMaterialExecucao != null && !colecaoOsAtividadeMaterialExecucao.isEmpty()){
					for(OsAtividadeMaterialExecucao material : colecaoOsAtividadeMaterialExecucao){
						obterDadosAtividadeOSHelper = new ObterDadosAtividadeOSHelper();
						obterDadosAtividadeOSHelper.setMaterial(true);
						obterDadosAtividadeOSHelper.setAtividade(material.getOrdemServicoAtividade().getAtividade());
						obterDadosAtividadeOSHelper.setMaterialUtilizado(material.getMaterial());
						obterDadosAtividadeOSHelper.setMaterialUnidade(material.getMaterial().getMaterialUnidade());
						obterDadosAtividadeOSHelper.setQtdeMaterial(material.getQuantidadeMaterial());
						obterDadosAtividadeOSHelper.setValorMaterial(material.getValorMaterial());
						colecaoObterDadosAtividadeOSHelper.add(obterDadosAtividadeOSHelper);

					}
				}

			}else if(tipoAtividade == ObterDadosAtividadeOSHelper.INDICADOR_PERIODO.intValue()){
				Collection<OsExecucaoEquipe> colecaoOsExecucaoEquipe = this.repositorioOrdemServico.obterOsExecucaoEquipePorOS(idOS,
								idAtividade);
				if(colecaoOsExecucaoEquipe != null && !colecaoOsExecucaoEquipe.isEmpty()){
					for(OsExecucaoEquipe periodo : colecaoOsExecucaoEquipe){
						obterDadosAtividadeOSHelper = new ObterDadosAtividadeOSHelper();
						obterDadosAtividadeOSHelper.setPeriodo(true);
						obterDadosAtividadeOSHelper.setAtividade(periodo.getOsAtividadePeriodoExecucao().getOrdemServicoAtividade()
										.getAtividade());
						obterDadosAtividadeOSHelper.setDataInicio(periodo.getOsAtividadePeriodoExecucao().getDataInicio());
						obterDadosAtividadeOSHelper.setDataFim(periodo.getOsAtividadePeriodoExecucao().getDataFim());
						obterDadosAtividadeOSHelper.setEquipe(periodo.getEquipe());
						obterDadosAtividadeOSHelper.setValorAtividadePeriodo(periodo.getOsAtividadePeriodoExecucao()
										.getValorAtividadePeriodo());
						colecaoObterDadosAtividadeOSHelper.add(obterDadosAtividadeOSHelper);
					}
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return colecaoObterDadosAtividadeOSHelper;
	}

	/**
	 * [UC0460] Obter Carga de Trabalho da Equipe
	 * 
	 * @author Leonardo Regis
	 * @date 14/09/2006
	 * @param equipeId
	 * @param colecaoIdOSProgramadas
	 * @param colecaoOSDistribuidasPorEquipe
	 * @param dataFinalProgramacao
	 * @param dataRoteiro
	 * @return valor da carga de trabalho da equipe
	 * @throws ControladorException
	 */
	public ObterCargaTrabalhoEquipeHelper obterCargaTrabalhoEquipe(Integer equipeId, Collection<Integer> colecaoIdOSProgramadas,
					Collection<ObterOSDistribuidasPorEquipeHelper> colecaoOSDistribuidasPorEquipe, Date dataRoteiro)
					throws ControladorException{

		ObterCargaTrabalhoEquipeHelper obterCargaTrabalhoEquipeHelper = null;

		try{

			if(dataRoteiro == null){
				dataRoteiro = new Date();
			}

			Equipe equipe = this.obterDadosEquipe(equipeId).getEquipe();

			OrdemServico ordemServicoProgramada = null;

			int tempoMedioExecucao = 0;
			int qtdeProgramacoesAtivas = 0;

			int cargaPrevistaEquipe = 0;
			int qtdeHorasExecutadas = 0;

			Collection<OrdemServicoProgramacao> colecaoOSP = null;
			Collection<OsAtividadePeriodoExecucao> colecaoOsExecucaoEquipe = null;

			if(colecaoIdOSProgramadas != null && !colecaoIdOSProgramadas.isEmpty()){

				Filtro filtroOrdemServico = new Filtro() {};
				filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

				for(Integer osId : colecaoIdOSProgramadas){

					filtroOrdemServico.adicionarParametro(new ParametroSimples("id", osId), true);

					Collection<OrdemServico> ordensServico = getControladorUtil().pesquisar(filtroOrdemServico,
									OrdemServico.class.getName());

					ordemServicoProgramada = (OrdemServico) Util.retonarObjetoDeColecao(ordensServico);

					// ordemServicoProgramada = this.recuperaOSPorId(osId);

					if(ordemServicoProgramada.getServicoTipo() == null){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.os_sem_tipo_servico", null, String.valueOf(osId));
					}

					tempoMedioExecucao += Short.valueOf(ordemServicoProgramada.getServicoTipo().getTempoMedioExecucao()).intValue();

					colecaoOSP = repositorioOrdemServico.obterProgramacoesAtivasPorOs(osId);

					if(colecaoOSP != null && !colecaoOSP.isEmpty()){
						qtdeProgramacoesAtivas += colecaoOSP.size();
					}else{
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.os_sem_programacao_ativa", null, String.valueOf(osId));
					}

					colecaoOsExecucaoEquipe = this.repositorioOrdemServico.obterOsAtividadePeriodoExecucaoPorOS(osId, dataRoteiro);

					qtdeHorasExecutadas = 0;
					if(colecaoOsExecucaoEquipe != null && !colecaoOsExecucaoEquipe.isEmpty()){
						for(OsAtividadePeriodoExecucao execucaoPeriodo : colecaoOsExecucaoEquipe){
							qtdeHorasExecutadas += Util.obterQtdeHorasEntreDatas(execucaoPeriodo.getDataInicio(),
											execucaoPeriodo.getDataFim());
						}
					}
					cargaPrevistaEquipe += (tempoMedioExecucao / qtdeProgramacoesAtivas) - qtdeHorasExecutadas * 60;
				}
			}

			tempoMedioExecucao = 0;

			int qtdeDiasTrabalhados = 0;
			int qtdeEquipes = 0;

			if(colecaoOSDistribuidasPorEquipe != null && !colecaoOSDistribuidasPorEquipe.isEmpty()){

				Filtro filtroOrdemServico = new Filtro() {};
				filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

				for(ObterOSDistribuidasPorEquipeHelper osDistribuidas : colecaoOSDistribuidasPorEquipe){

					filtroOrdemServico.adicionarParametro(new ParametroSimples("id", osDistribuidas.getIdOS()), true);

					Collection<OrdemServico> ordensServico = getControladorUtil().pesquisar(filtroOrdemServico,
									OrdemServico.class.getName());

					ordemServicoProgramada = (OrdemServico) Util.retonarObjetoDeColecao(ordensServico);

					// ordemServicoProgramada = this.recuperaOSPorId(osDistribuidas.getIdOS());

					if(ordemServicoProgramada.getServicoTipo() == null){
						try{
							sessionContext.setRollbackOnly();
						}catch(IllegalStateException ex){

						}
						throw new ControladorException("atencao.os_sem_tipo_servico", null, String.valueOf(osDistribuidas.getIdOS()));
					}

					tempoMedioExecucao += Short.valueOf(ordemServicoProgramada.getServicoTipo().getTempoMedioExecucao()).intValue();

					if(osDistribuidas.getColecaoEquipe() != null && !osDistribuidas.getColecaoEquipe().isEmpty()){
						qtdeEquipes = osDistribuidas.getColecaoEquipe().size();
					}else{
						qtdeEquipes = 0;
					}

					qtdeDiasTrabalhados = Util.obterQuantidadeDiasEntreDuasDatas(dataRoteiro, osDistribuidas.getDataFinalProgramacao());

					qtdeDiasTrabalhados = (qtdeDiasTrabalhados + 1) * qtdeEquipes;

					colecaoOsExecucaoEquipe = this.repositorioOrdemServico.obterOsAtividadePeriodoExecucaoPorOS(osDistribuidas.getIdOS(),
									dataRoteiro);

					qtdeHorasExecutadas = 0;

					if(colecaoOsExecucaoEquipe != null && !colecaoOsExecucaoEquipe.isEmpty()){
						for(OsAtividadePeriodoExecucao execucaoPeriodo : colecaoOsExecucaoEquipe){
							qtdeHorasExecutadas += Util.obterQtdeHorasEntreDatas(execucaoPeriodo.getDataInicio(),
											execucaoPeriodo.getDataFim());
						}
					}
					cargaPrevistaEquipe += (tempoMedioExecucao / qtdeDiasTrabalhados) - qtdeHorasExecutadas * 60;
				}
			}

			obterCargaTrabalhoEquipeHelper = new ObterCargaTrabalhoEquipeHelper();

			double percCTP = 0;
			obterCargaTrabalhoEquipeHelper.setCargaTrabalhoPrevistaHoras(cargaPrevistaEquipe);
			if(cargaPrevistaEquipe > 0){

				double valorDividido = cargaPrevistaEquipe / equipe.getCargaTrabalho().doubleValue();
				percCTP = valorDividido * 100;
			}else{
				percCTP = 100;
			}

			BigDecimal percentualCargaTrabalhoPrevista = new BigDecimal(percCTP);

			String bigFormatado = Util.formataBigDecimal(percentualCargaTrabalhoPrevista, 2, true);

			obterCargaTrabalhoEquipeHelper.setPercentualCargaTrabalhoPrevista(new BigDecimal((bigFormatado.replace(".", "")).replace(",",
							".")));

			int somatorioHorasEquipe = 0;

			colecaoOsExecucaoEquipe = this.repositorioOrdemServico.obterOsAtividadePeriodoExecucaoPorEquipe(equipeId, dataRoteiro);

			for(OsAtividadePeriodoExecucao execucao : colecaoOsExecucaoEquipe){
				somatorioHorasEquipe += Util.obterQtdeHorasEntreDatas(execucao.getDataInicio(), execucao.getDataFim());
			}

			BigDecimal percentualCargaRealizada = new BigDecimal(0);
			if(cargaPrevistaEquipe > 0){
				percentualCargaRealizada = new BigDecimal((somatorioHorasEquipe * 60) / cargaPrevistaEquipe * 100);
			}else{
				percentualCargaRealizada = new BigDecimal(100 + Math.abs(cargaPrevistaEquipe));
			}

			bigFormatado = Util.formataBigDecimal(percentualCargaRealizada, 2, true);
			obterCargaTrabalhoEquipeHelper.setPercentualCargaRealizada(new BigDecimal((bigFormatado.replace(".", "")).replace(",", ".")));

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}
		return obterCargaTrabalhoEquipeHelper;
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0001] - Verificar Unidade do Usuário
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void verificarUnidadeUsuario(Integer numeroOS, Usuario usuarioLogado) throws ControladorException{

		// <<Inclui>> [UC0620 - Obter Indicador de Autorização para Manutenção da OS]
		Short indicadorManutencaoOrdemServico = this.obterIndicadorAutorizacaoParaManutencaoOrdemServico(usuarioLogado, numeroOS);

		UnidadeOrganizacional unidadeAtendimentoOrdemServico = null;

		try{

			unidadeAtendimentoOrdemServico = repositorioOrdemServico.obterUnidadeGeracaoOrdemServico(numeroOS);
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

		/*
		 * Caso o indicador de autorização para manutenção da OS retornado pelo UC0620 esteja
		 * com o
		 * valor correspondente a 2-NÃO
		 */
		if(indicadorManutencaoOrdemServico.equals(ConstantesSistema.NAO)){

			if(Util.isVazioOuBranco(unidadeAtendimentoOrdemServico)){
				throw new ControladorException("atencao.nao_possivel_encerramento/atualizacao_os");
			}else{
				throw new ControladorException("atencao.nao_possivel_encerramento_os", null, unidadeAtendimentoOrdemServico.getDescricao());
			}
		}

	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0006] - Verificar Origem do Encerramento da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void verificarOrigemEncerramentoOS(Integer numeroOS, Date dataEncerramento) throws ControladorException{

		Integer numeroOSVerificada = null;
		if(dataEncerramento == null || dataEncerramento.equals("")){
			try{
				numeroOSVerificada = repositorioOrdemServico.pesquisarOSProgramacaoAtiva(numeroOS);
			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}
			if(numeroOSVerificada != null && !numeroOSVerificada.equals("")){
				throw new ControladorException("atencao.origem_encerramento_os");
			}
		}
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [FS0002] - Validar Tipo Serviço
	 * [FS0004] - Verificar preenchimento dos campos
	 * [FS0007] - Validar Data de Encerramento
	 * [FS0008] - Validar Data do roteiro
	 * 
	 * @author Sávio Luiz
	 * @date 29/09/2006
	 *       Correçao: Critica de encerrar OS com data de encerramento anterior a data de geração
	 * @author Andre Nishimura.
	 * @throws ControladorException
	 */
	public void validarCamposEncerrarOS(String indicadorExecucao, String numeroOS, String motivoEncerramento, String dataEncerramento,
					String tipoServicoReferenciaId, String indicadorPavimento, String pavimento, String idTipoRetornoOSReferida,
					String indicadorDeferimento, String indicadorTrocaServicoTipo, String idServicoTipo, String dataRoteiro, String idRA,
					String indicadorVistoriaServicoTipo, String codigoRetornoVistoriaOs, String horaEncerramento, Usuario usuarioLogado,
					String indicadorAfericaoHidrometro, String idHidrometroCondicao, String indicadorResultado, String idFuncionario,
					String indicadorClienteAcompanhou)
					throws ControladorException{

		// caso o motivo de encerramento esteja nulo
		if(motivoEncerramento == null || motivoEncerramento.equals("")){
			throw new ControladorException("atencao.required", null, "Motivo de Encerramento");
		}

		// validar data de encerramento - obrigatória e não deve ser maior que a atual nem menor que
		// a do atendimento
		if(dataEncerramento != null && !dataEncerramento.equals("")){
			// [FS0007] - Validar data de encerramento
			if(Util.validarDiaMesAno(dataEncerramento)){
				throw new ControladorException("atencao.data.invalida", null, "Data de Execução");
			}

			Date dataEncerramentoDate = Util.converteStringParaDate(dataEncerramento, true);
			if(dataEncerramentoDate.after(new Date())){
				throw new ControladorException("atencao.data.maior.data.corrente", null, "Data de Execução");
			}

			// Varidar se data de execução é menor que 01/01/1900
			Date dataLimiteMinimo = Util.converteStringParaDate("01/01/1900", true);
			if(Util.compararDiaMesAno(dataEncerramentoDate, dataLimiteMinimo) < 0){
				throw new ControladorException("atencao_data_execucao_nao_inferior_1900", null, "Data de Execução");
			}

			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, numeroOS));
			Collection<OrdemServico> colecaoOrdemServico = (Collection<OrdemServico>) this.getControladorUtil().pesquisar(
							filtroOrdemServico, OrdemServico.class.getName());
			OrdemServico ordemServico = null;
			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
				ordemServico = colecaoOrdemServico.iterator().next();
			}
			Date dataGeracao = ordemServico.getDataGeracao();

			// A pedido de ADA foi descomentado novamente 27/08/2010.
			// Por: Yara Souza
			// Data : 19/08/2010
			// 2.1 - Não aceitar baixas, com a data anterior a data da execução da mesma.
			if(Util.compararData(dataEncerramentoDate, dataGeracao) == -1){
				throw new ControladorException("atencao.data_encerramento_menor_data_geracao");
			}

		}else{
			throw new ControladorException("atencao.required", null, "Data de Execução");
		}

		// Tratar horaEncerramento - formato
		SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

		// Hora de encerramento não pode ser vazia.
		if(horaEncerramento == null || horaEncerramento.equals("")){
			throw new ControladorException("atencao.informe.hora.encerramento");
		}

		try{
			formatoHora.parse(horaEncerramento);
		}catch(ParseException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.data.hora.invalido", null);
		}

		Date dataEncerramentoDate = Util.converteStringParaDateHora(dataEncerramento + " " + horaEncerramento + ":00");
		Date dataEncerramentoDateSemHora = Util.converteStringParaDate(dataEncerramento, true);
		if(dataEncerramentoDate.after(new Date())){
			throw new ControladorException("atencao.data.maior.data.corrente", null, "Hora de Encerramento");
		}

		// caso não exista a data de encerramento então a data de encerramento será a data atual
		Date dataRoteiroDate = null;
		if(dataRoteiro != null && !dataRoteiro.equals("")){

			// [FS0008] - Validar data de roteiro
			if(Util.validarDiaMesAno(dataRoteiro)){
				throw new ControladorException("atencao.data.invalida", null, "Data de Encerramento");
			}

			Integer idUnidadeLotacao = usuarioLogado.getUnidadeOrganizacional().getId();
			dataRoteiroDate = Util.converteStringParaDate(dataRoteiro, true);
			// Date dataRoteiroDateFinal = Util.adicionarNumeroDiasDeUmaData(dataRoteiroDate, 1);

			// Recuperar dataRoteiro completa do programacao_roteiro.
			FiltroProgramacaoRoteiro filtroProgramacaoRoteiro = new FiltroProgramacaoRoteiro();
			filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.DATA_ROTEIRO, dataRoteiroDate));
			filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.UNIDADE_ORGANIZACIONAL_ID,
							idUnidadeLotacao));

			Collection colecaoProgramacao = this.getControladorUtil().pesquisar(filtroProgramacaoRoteiro,
							ProgramacaoRoteiro.class.getName());
			ProgramacaoRoteiro programacao = new ProgramacaoRoteiro();

			if(colecaoProgramacao != null && !colecaoProgramacao.isEmpty()){
				programacao = (ProgramacaoRoteiro) colecaoProgramacao.iterator().next();
				dataRoteiroDate = programacao.getDataRoteiro();
			}

			// Verificar se a data de encerramento é maior que a data do roteiro. (não considerar
			// hora)

			ParametroAtendimentoPublico.P_ENCERRAR_OS_APOS_DATA_ROTEIRO.executar(this, -1, dataEncerramentoDateSemHora, dataRoteiroDate);

			// if(dataEncerramentoDateSemHora.after(dataRoteiroDate)){
			// throw new ControladorException("atencao.data_encerramento_maior_data_roteiro", null);
			// }

			// if(dataEncerramentoDateSemHora.before(dataRoteiroDate)){
			// throw new ControladorException("atencao.data_encerramento_menor_data_roteiro",
			// null);
			// }
		}

		if(dataRoteiro != null){

			ParametroAtendimentoPublico.P_FORMA_VALIDAR_DATA_EXECUCAO_OS.executar(this, -1, dataEncerramentoDateSemHora, dataRoteiroDate);
		}else{

			ParametroAtendimentoPublico.P_FORMA_VALIDAR_DATA_EXECUCAO_OS.executar(this, -1, dataEncerramentoDateSemHora,
							Util.converteStringParaDate("01/01/1900", true));
		}

		/**
		 * TODO:Verificar necessidade desse bloco.
		 */
		FiltroOrdemServico filtroOS = new FiltroOrdemServico();
		filtroOS.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, numeroOS));

		Collection ordensServico = this.getControladorUtil().pesquisar(filtroOS, OrdemServico.class.getName());
		OrdemServico ordemServico = new OrdemServico();

		if(ordensServico != null && !ordensServico.isEmpty()){
			ordemServico = (OrdemServico) Util.retonarObjetoDeColecao(ordensServico);
			Date dataGeracao = ordemServico.getDataGeracao();

			// Verificar se a data/hora de encerramento é menor que a data/hora da geração.
			if(dataEncerramentoDate.before(dataGeracao)){
				// throw new ControladorException("atencao.data_encerramento_menor_data_geracao",
				// null);
			}
		}

		// indicador execução seja diferente de nulo
		if(indicadorExecucao != null && !indicadorExecucao.equals("")){
			short indicadorExecucaoShort = Short.parseShort(indicadorExecucao);

			// indicador execução seja igual a sim(1)
			if(indicadorExecucaoShort == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){

				/*
				 * if (colecaoManterDadosAtividadesOrdemServicoHelper == null ||
				 * colecaoManterDadosAtividadesOrdemServicoHelper .isEmpty()) {
				 * throw new ControladorException("atencao.required", null,
				 * "Atividades"); }
				 */

				// se o serviço tipo referencia seja igual a nulo
				if(tipoServicoReferenciaId == null || tipoServicoReferenciaId.equals("")){
					// [SB0002] - Encerrar com execução e sem referência

					// caso o indicador de pavimento esteja igual a sim e o pavimento não esteja
					// preenchido então
					if(indicadorPavimento != null && !indicadorPavimento.equals("")){
						short indicadorPavimentoShort = Short.valueOf(indicadorPavimento);
						if(indicadorPavimentoShort == ServicoTipo.INDICADOR_PAVIMENTO_SIM){
							if(pavimento == null || pavimento.equals("")){
								throw new ControladorException("atencao.required", null, "Pavimento");
							}
						}
					}

				}else{
					// [SB0003] - Encerrar com execução e com referência

					if(idTipoRetornoOSReferida == null || idTipoRetornoOSReferida.equals("")){
						throw new ControladorException("atencao.required", null, "Tipo de Retorno Referida");
					}
					if(indicadorDeferimento != null && !indicadorDeferimento.equals("")){
						short indDeferimento = Short.parseShort(indicadorDeferimento);
						// se indicador deferimento for igual a sim(1)
						if(indDeferimento == OsReferidaRetornoTipo.INDICADOR_DEFERIMENTO_SIM){
							// 9.1.1 caso o indicador de pavimento esteja igual a sim e o pavimento
							// não esteja preenchido então
							if(indicadorPavimento != null && !indicadorPavimento.equals("")){
								short indicadorPavimentoShort = Short.valueOf(indicadorPavimento);
								if(indicadorPavimentoShort == ServicoTipo.INDICADOR_PAVIMENTO_SIM){
									if(pavimento == null || pavimento.equals("")){
										throw new ControladorException("atencao.required", null, "Pavimento");
									}
								}
							}
							// 9.1.2 caso o indicador de troca se serviço da
							// tabela os
							// referida
							// retorno tipo seja diferente de nula e igual a sim
							if(indicadorTrocaServicoTipo != null && !indicadorTrocaServicoTipo.equals("")){
								short indicadorTrocaServicoTipoShort = Short.valueOf(indicadorTrocaServicoTipo);
								if(indicadorTrocaServicoTipoShort == OsReferidaRetornoTipo.INDICADOR_TROCA_SERVICO_SIM){
									if(idServicoTipo == null || idServicoTipo.equals("")){
										throw new ControladorException("atencao.required", null, "Tipo de Serviço");
									}else{
										// [FS0002] - Validar Tipo de Serviço Caso já exista OS para
										// RA informado com o mesmo tipo de serviço selecionado
										if(idRA != null && !idRA.equals("")){
											try{
												Object[] parmsRAServicoTipo = repositorioOrdemServico.verificarExistenciaOSEncerrado(
																Integer.valueOf(idRA), Integer.valueOf(idServicoTipo));
												if(parmsRAServicoTipo != null && !parmsRAServicoTipo.equals("")){
													Integer idOSNaBase = null;
													String descricaoServicoTipo = null;
													if(parmsRAServicoTipo[0] != null){
														idOSNaBase = (Integer) parmsRAServicoTipo[0];
													}
													if(parmsRAServicoTipo[1] != null){
														descricaoServicoTipo = (String) parmsRAServicoTipo[1];
													}
													if(idOSNaBase != null){
														throw new ControladorException("atencao.ordem_servico_com_ra", null, numeroOS,
																		idRA != null ? String.valueOf(idRA) : "", descricaoServicoTipo);
													}
												}

												Integer idServTipoRef = repositorioOrdemServico
																.verificarExistenciaServicoTipoReferencia(Integer.valueOf(idServicoTipo));
												if(idServTipoRef != null && !idServTipoRef.equals("")){
													throw new ControladorException("atencao.existe_tipo_servico_referencia_os");
												}

											}catch(ErroRepositorioException e){
												throw new ControladorException("erro.sistema", e);
											}
										}
										// tipo de serviço selecionado tenha um
										// tipo de serviço referência
									}
								}
							}
						}
					}
				}

				if(!Util.isVazioOuBranco(indicadorAfericaoHidrometro)
								&& indicadorAfericaoHidrometro.equals(ConstantesSistema.SIM.toString())){

					if(Util.isVazioOuBranco(idHidrometroCondicao)){

						throw new ControladorException("atencao.required", null, "Condição do Hidrômetro para Aferição");
					}

					if(Util.isVazioOuBranco(indicadorResultado)){

						throw new ControladorException("atencao.required", null, "Resultado");
					}

					if(Util.isVazioOuBranco(idFuncionario)){

						throw new ControladorException("atencao.required", null, "Funcionário");
					}else{

						FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
						filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Util.obterInteger(idFuncionario)));

						Collection colecaoFuncionario = getControladorUtil().pesquisar(filtroFuncionario, Funcionario.class.getName());

						if(Util.isVazioOrNulo(colecaoFuncionario)){

							throw new ControladorException("pesquisa.funcionario.inexistente");
						}
					}

					if(Util.isVazioOuBranco(indicadorClienteAcompanhou)){

						throw new ControladorException("atencao.required", null, "Cliente Acompanhou");
					}
				}
			}
		}
	}

	/**
	 * Salva as fotos da ordem de servico
	 * 
	 * @param colecaoFotos
	 * @throws ControladorException
	 */
	public void salvarFotoOrdemServico(OrdemServicoFotoOcorrencia foto) throws ControladorException{

		if(foto != null){
			foto.setUltimaAlteracao(new Date());
			getControladorUtil().inserir(foto);
		}
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0001] - Encerrar sem execução
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void encerrarOSSemExecucao(OSEncerramentoHelper helper, Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos,
					OrigemEncerramentoOrdemServico origemEncerramento,
					DadosAtividadesOrdemServicoHelper dadosAtividadeorAtividadesOrdemServicoHelper) throws ControladorException{

		OrdemServico osNaBase = null;

		helper.setUltimaAlteracao(new Date());

		if(helper.getUltimaAlteracao() != null && !helper.getUltimaAlteracao().equals("")){

			// controle de transação
			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();

			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, helper.getNumeroOS()));
			filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO_SERVICOS_ASSOCIADOS);

			Collection<OrdemServico> colecaoOS = getControladorUtil().pesquisar(filtroOrdemServico, OrdemServico.class.getName());

			if(colecaoOS == null || colecaoOS.isEmpty()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			osNaBase = (OrdemServico) colecaoOS.iterator().next();

			if(osNaBase.getUltimaAlteracao().after(helper.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
			// fim controle transação
		}

		Integer idMotivoEncerramento = null;

		// caso o motivo de encerramento esteja nulo
		if(helper.getIdMotivoEncerramento() == null || helper.getIdMotivoEncerramento().equals("")){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.required", null, "Motivo de Encerramento");
		}else{
			idMotivoEncerramento = Util.converterStringParaInteger(helper.getIdMotivoEncerramento());
		}

		// caso não exista a data de execução então a data de execução será a data atual
		if(helper.getDataExecucao() == null || helper.getDataExecucao().equals("")){
			helper.setDataExecucao(new Date());
		}

		if(helper.getIndicadorVistoriaServicoTipo() != null && !helper.getIndicadorVistoriaServicoTipo().equals("")){
			if(helper.getIndicadorVistoriaServicoTipo().equals(ServicoTipo.INDICADOR_VISTORIA_SIM)){
				if(helper.getCodigoRetornoVistoriaOs() == null || helper.getCodigoRetornoVistoriaOs().equals("")){
					throw new ControladorException("atencao.required", null, "Retorno Vistoria");
				}
			}
		}

		try{

			// [UC0461] Manter Dados das Atividades da Ordem de serviço
			this.inserirDadosAtividadesOrdemServico(dadosAtividadeorAtividadesOrdemServicoHelper);


			// Preparando objeto para o registrar transação
			AtendimentoMotivoEncerramento atendimentoMotivo = repositorioOrdemServico
							.pesquisarAtendimentoMotivoEncerramentoPorId(idMotivoEncerramento);

			osNaBase.setAtendimentoMotivoEncerramento(atendimentoMotivo);
			osNaBase.setDataEncerramento(new Date());
			osNaBase.setDataExecucao(helper.getDataExecucao());
			osNaBase.setDescricaoParecerEncerramento(helper.getParecerEncerramento());
			osNaBase.setSituacao(OrdemServico.SITUACAO_ENCERRADO);

			BigDecimal valorTotalHorasTrabalhadas = BigDecimal.ZERO;
			BigDecimal valorTotalMateriaias = BigDecimal.ZERO;
			if(dadosAtividadeorAtividadesOrdemServicoHelper != null){
				valorTotalHorasTrabalhadas = recuperarValorHorasTrabalhadasOS(dadosAtividadeorAtividadesOrdemServicoHelper
							.getColecaoManterDadosAtividadesOrdemServicoHelper());
				valorTotalMateriaias = recuperarValorMateriaisOS(dadosAtividadeorAtividadesOrdemServicoHelper
								.getColecaoManterDadosAtividadesOrdemServicoHelper());
			}

			osNaBase.setValorHorasTrabalhadas(valorTotalHorasTrabalhadas);
			osNaBase.setValorMateriais(valorTotalMateriaias);

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ORDEM_SERVICO_ENCERRAR, osNaBase.getId(),
							osNaBase.getId(), new UsuarioAcaoUsuarioHelper(helper.getUsuarioLogado(),
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			registradorOperacao.registrarOperacao(osNaBase);
			getControladorTransacao().registrarTransacao(osNaBase);
			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			repositorioOrdemServico.atualizarParmsOS(helper.getNumeroOS(), idMotivoEncerramento, new Date(),
							helper.getParecerEncerramento(), helper.getCodigoRetornoVistoriaOs(), helper.getDataExecucao());

			// inseri a tabela ordem serviço unidade
			OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();

			// id do usuário logado
			if(helper.getUsuarioLogado().getId() != null && !helper.getUsuarioLogado().getId().equals("")){

				// unidade do usuário que está logado
				if(helper.getUsuarioLogado().getUnidadeOrganizacional() != null
								&& !helper.getUsuarioLogado().getUnidadeOrganizacional().equals("")
								&& helper.getUsuarioLogado().getUnidadeOrganizacional().getId() != null
								&& !helper.getUsuarioLogado().getUnidadeOrganizacional().getId().equals("")){

					UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
					unidadeOrganizacional.setId(helper.getUsuarioLogado().getUnidadeOrganizacional().getId());

					// seta a unidade organizacional na ordem serviço unidade
					ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacional);
				}

				// seta o usuário na ordem serviço unidade
				ordemServicoUnidade.setUsuario(helper.getUsuarioLogado());
			}

			// inseri as ordem de serviço na ordem serviço unidade
			if(helper.getNumeroOS() != null && !helper.getNumeroOS().equals("")){
				OrdemServico ordemServico = new OrdemServico();
				ordemServico.setId(helper.getNumeroOS());
				ordemServicoUnidade.setOrdemServico(ordemServico);
			}

			// seta o id do atendimento relação tipo
			AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
			atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
			ordemServicoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);

			// seta a ultima alteração com a data atual
			ordemServicoUnidade.setUltimaAlteracao(new Date());

			// inseri a ordem de serviço unidade
			getControladorUtil().inserir(ordemServicoUnidade);

			/*
			 * [OC790655][UC0457][SB0007]: Verificar Atualização Dados Encerramento no
			 * Histórico de Manutenção da Ligação do Imóvel, passando a ordem de serviço encerrada.
			 */
			if(ConstantesSistema.SIM.equals(osNaBase.getServicoTipo().getIndicadorGerarHistoricoImovel())){
				getControladorLigacaoAgua()
								.atualizarHistoricoManutencaoLigacao(osNaBase, HistoricoManutencaoLigacao.ENCERRAR_ORDEM_SERVICO);
			}

			// [SB0009] - Verificar Atualização Situação Ação Documento de Cobrança
			atualizarSituacaoAcaoCobranca(helper.getNumeroOS(), CobrancaAcaoSituacao.CANCELADA);

			// caso a ordem de serviço que está sendo encerrada tenha uma ordem de serviço
			// referencia
			Integer idOSReferencia = repositorioOrdemServico.pesquisarOSReferencia(helper.getNumeroOS());
			if(idOSReferencia != null && !idOSReferencia.equals("")){
				repositorioOrdemServico.atualizarParmsOSReferencia(helper.getNumeroOS());
				/*
				 * [OC790655][UC0457][SB0008]: Verificar Atualização Dados Situação da
				 * OS no Histórico de Manutenção da Ligação do Imóvel, passando a ordem de serviço
				 * de referência.
				 */
				if(ConstantesSistema.SIM.equals(osNaBase.getServicoTipo().getIndicadorGerarHistoricoImovel())){
					getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(osNaBase,
									HistoricoManutencaoLigacao.ENCERRAR_ORDEM_SERVICO_ATUALIZAR_SITUACAO);
				}
			}

			// Verifica se a data de execuão vinda como parametro do método é diferente de nulo
			if(helper.getDataExecucao() != null && !helper.getDataExecucao().equals("")){

				// [SB0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
				verificarExcluirAtualizarProgramacaoOS(helper.getNumeroOS(), helper.getDataExecucao());
			}

			Integer idUnidadeAtual = null;

			// caso exista a ordem de serviço fiscalização então gera a os Fiscalização
			if(helper.getOsFiscalizacao() != null && !helper.getOsFiscalizacao().equals("")){

				Integer idLocalidade = null;
				Integer idSetorComercial = null;
				Integer idBairro = null;

				Object[] dadosDaRA = null;
				if(osNaBase.getRegistroAtendimento() != null){
					dadosDaRA = this.getControladorRegistroAtendimento().pesquisarDadosLocalizacaoRegistroAtendimento(
									osNaBase.getRegistroAtendimento().getId());
				}

				if(dadosDaRA != null){
					idLocalidade = (Integer) dadosDaRA[0];
					idSetorComercial = (Integer) dadosDaRA[1];
					idBairro = (Integer) dadosDaRA[2];

					UnidadeOrganizacional unidadeAtual = getControladorRegistroAtendimento().obterUnidadeAtendimentoRA(
									osNaBase.getRegistroAtendimento().getId());
					idUnidadeAtual = unidadeAtual.getId();
				}else{

					Object[] dadosDoImovel = null;
					if(helper.getOsFiscalizacao().getImovel() != null){
						dadosDoImovel = this.getControladorImovel().pesquisarDadosLocalizacaoImovel(
										helper.getOsFiscalizacao().getImovel().getId());
					}

					if(dadosDoImovel != null){
						idLocalidade = (Integer) dadosDoImovel[0];
						idSetorComercial = (Integer) dadosDoImovel[1];
						idBairro = (Integer) dadosDoImovel[2];
					}

					UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualOrdemServico(helper.getNumeroOS());
					idUnidadeAtual = unidadeAtual.getId();
				}

				this.gerarOrdemServico(helper.getOsFiscalizacao(), helper.getUsuarioLogado(), null, idLocalidade, idSetorComercial,
								idBairro, idUnidadeAtual, null, null, null, false, null);
			}

			// verifica se fará gerações de Serviços Associados
			if(atendimentoMotivo.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM
							&& !origemEncerramento.equals(OrigemEncerramentoOrdemServico.ENCERRAMENTO_OS_ASSOCIADA)){
				ServicoTipo servicoTipoOS = null;
				try{
					servicoTipoOS = repositorioOrdemServico.pesquisarServicoTipoPorId(osNaBase.getServicoTipo().getId());
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
				if(servicoTipoOS.getServicosAssociados() != null && !servicoTipoOS.getServicosAssociados().isEmpty()){
					osNaBase.setServicoTipo(servicoTipoOS);
					this.gerarServicosAssociadosOrdemServico(osNaBase, helper.getUsuarioLogado(),
									EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO, origemEncerramento, autorizacoesServicos);
				}
			}

			// Trata os Dados da tela no processo de Encerramento da OS
			if(helper.getOcorrenciaInfracao() != null && helper.getColecaoInfracaoTipo() != null
							&& helper.getColecaoInfracaoTipo().length > 0){
				Collection<OcorrenciaInfracaoItem> listaInfracoesItem = this.inserirOcorrenciaInfracao(helper.getOcorrenciaInfracao(),
								helper.getColecaoInfracaoTipo());

				this.gerarSancaoInfracao(helper.getNumeroOS(), listaInfracoesItem);
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}catch(ControladorException ex){
			sessionContext.setRollbackOnly();
			throw ex;
		}

	}

	/**
	 * Rotina Batch que encerra todas as OS de um serviço tipo especifico que não tenha RA
	 * 
	 * @author Sávio Luiz
	 * @date 23/02/2007
	 * @throws ControladorException
	 */
	public void encerrarOSDoServicoTipoSemRA(Usuario usuarioLogado, Integer idServicoTipo) throws ControladorException{

		try{
			Collection idsOS = repositorioOrdemServico.pesquisarOSComServicoTipo(idServicoTipo);

			Collection colecaoOrdemServicoUnidade = new ArrayList();

			if(idsOS != null && !idsOS.isEmpty()){
				repositorioOrdemServico.atualizarColecaoOS(idsOS);
				UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
				// id do usuário logado
				if(usuarioLogado.getId() != null && !usuarioLogado.getId().equals("")){
					// unidade do usuário que está logado
					if(usuarioLogado.getUnidadeOrganizacional() != null && !usuarioLogado.getUnidadeOrganizacional().equals("")
									&& usuarioLogado.getUnidadeOrganizacional().getId() != null
									&& !usuarioLogado.getUnidadeOrganizacional().getId().equals("")){

						unidadeOrganizacional.setId(usuarioLogado.getUnidadeOrganizacional().getId());

					}

					// seta o id do atendimento relação tipo
					AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
					atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);

					Iterator iteIdsOS = idsOS.iterator();

					Usuario usuarioMigracao = Usuario.USUARIO_BATCH;

					while(iteIdsOS.hasNext()){
						OrdemServico ordemServico = new OrdemServico();
						// inseri a tabela ordem serviço unidade
						OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();
						Integer idOS = (Integer) iteIdsOS.next();
						ordemServico.setId(idOS);

						// seta a unidade organizacional na ordem serviço
						// unidade
						ordemServicoUnidade.setUnidadeOrganizacional(usuarioMigracao.getUnidadeOrganizacional());

						// seta o usuário na ordem serviço unidade
						ordemServicoUnidade.setUsuario(usuarioMigracao);
						ordemServicoUnidade.setUltimaAlteracao(new Date());
						ordemServicoUnidade.setOrdemServico(ordemServico);
						ordemServicoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);
						colecaoOrdemServicoUnidade.add(ordemServicoUnidade);

					}
				}

				getControladorBatch().inserirColecaoObjetoParaBatch(colecaoOrdemServicoUnidade);

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);

		}
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0002] - Encerrar com execução e sem referência
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @author Saulo Lima
	 * @date 18/05/2009
	 *       Alteração para receber como parâmetro um OSEncerramentoHelper (OS's Associadas)
	 * @throws ControladorException
	 */
	public void encerrarOSComExecucaoSemReferencia(OSEncerramentoHelper helper,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos,
					OrigemEncerramentoOrdemServico origemEncerramento,
					DadosAtividadesOrdemServicoHelper dadosAtividadeorAtividadesOrdemServicoHelper) throws ControladorException{

		Integer idMotivoEncerramento = null;

		// [UC0461] Manter Dados das Atividades da Ordem de serviço
		this.inserirDadosAtividadesOrdemServico(dadosAtividadeorAtividadesOrdemServicoHelper);

		// caso o motivo de encerramento esteja nulo
		if(helper.getIdMotivoEncerramento() == null || helper.getIdMotivoEncerramento().equals("")){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.required", null, "Motivo de Encerramento");
		}else{
			idMotivoEncerramento = Util.converterStringParaInteger(helper.getIdMotivoEncerramento());
		}

		// caso a data de execução esteja nula
		if(helper.getDataExecucao() == null || helper.getDataExecucao().equals("")){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.required", null, "Data de Execução");
		}

		// caso o indicador de pavimento esteja igual a sim e o pavimento não esteja preenchido
		// então
		if(helper.getIndicadorPavimento() != null && !helper.getIndicadorPavimento().equals("")){
			short indicadorPavimentoShort = Short.valueOf(helper.getIndicadorPavimento());
			if(indicadorPavimentoShort == ServicoTipo.INDICADOR_PAVIMENTO_SIM){
				if(helper.getPavimento() == null || helper.getPavimento().equals("")){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.required", null, "Pavimento");
				}
			}
		}

		if(helper.getIndicadorVistoriaServicoTipo() != null && !helper.getIndicadorVistoriaServicoTipo().equals("")){

			if(helper.getIndicadorVistoriaServicoTipo().equals(ServicoTipo.INDICADOR_VISTORIA_SIM)){

				if(helper.getCodigoRetornoVistoriaOs() == null || helper.getCodigoRetornoVistoriaOs().equals("")){
					throw new ControladorException("atencao.required", null, "Retorno Vistoria");
				}
			}
		}

		// controle de transação
		FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();

		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, helper.getNumeroOS()));
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO_SERVICOS_ASSOCIADOS);
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("ordemServicoUnidades");

		Collection colecaoOS = getControladorUtil().pesquisar(filtroOrdemServico, OrdemServico.class.getName());

		if(colecaoOS == null || colecaoOS.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		helper.setUltimaAlteracao(new Date());

		OrdemServico osNaBase = (OrdemServico) colecaoOS.iterator().next();

		if(helper.getUltimaAlteracao() != null && !helper.getUltimaAlteracao().equals("")){

			if(osNaBase.getUltimaAlteracao().after(helper.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}
		// fim controle transação

		FiltroOrdemServicoUnidade filtroOrdemServicoUnidade = new FiltroOrdemServicoUnidade();
		filtroOrdemServicoUnidade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoUnidade.ORDEM_SERVICO_ID, osNaBase.getId()));
		filtroOrdemServicoUnidade.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

		Set<OrdemServicoUnidade> setOSUnidade = new TreeSet<OrdemServicoUnidade>();
		setOSUnidade.addAll(getControladorUtil().pesquisar(filtroOrdemServicoUnidade, OrdemServicoUnidade.class.getName()));

		osNaBase.setOrdemServicoUnidades(setOSUnidade);

		if(helper.getCodigoRetornoVistoriaOs() == null || helper.getCodigoRetornoVistoriaOs().equals("")){
			osNaBase.setCodigoRetornoVistoria(null);
		}else{
			osNaBase.setCodigoRetornoVistoria(Short.valueOf(helper.getCodigoRetornoVistoriaOs()));
		}

		// atualizar o objeto da base seta o código da situação como 2
		osNaBase.setSituacao(OrdemServico.SITUACAO_ENCERRADO);

		// seta a data de encerramento
		osNaBase.setDataEncerramento(new Date());

		// seta a data de execução informada
		osNaBase.setDataExecucao(helper.getDataExecucao());

		// seta o parecer de encerramento
		if(helper.getParecerEncerramento() != null && !helper.getParecerEncerramento().equals("")){
			osNaBase.setDescricaoParecerEncerramento(helper.getParecerEncerramento());
		}else{
			osNaBase.setDescricaoParecerEncerramento(null);
		}

		// seta o pavimento
		if(helper.getPavimento() != null && !helper.getPavimento().equals("")){
			osNaBase.setAreaPavimento(new BigDecimal(helper.getPavimento()).setScale(2, BigDecimal.ROUND_FLOOR));
		}else{
			osNaBase.setAreaPavimento(null);
		}

		if(helper.getDimensao1() != null && !helper.getDimensao1().equals("")){
			osNaBase.setDimensao1(Util.formatarMoedaRealparaBigDecimal(helper.getDimensao1()));
		}else{
			osNaBase.setDimensao1(null);
		}

		if(helper.getDimensao2() != null && !helper.getDimensao2().equals("")){
			osNaBase.setDimensao2(Util.formatarMoedaRealparaBigDecimal(helper.getDimensao2()));
		}else{
			osNaBase.setDimensao2(null);
		}

		if(helper.getDimensao3() != null && !helper.getDimensao3().equals("")){
			osNaBase.setDimensao3(Util.formatarMoedaRealparaBigDecimal(helper.getDimensao3()));
		}else{
			osNaBase.setDimensao3(null);
		}

		if(dadosAtividadeorAtividadesOrdemServicoHelper != null){
			BigDecimal valorTotalHorasTrabalhadas = recuperarValorHorasTrabalhadasOS(dadosAtividadeorAtividadesOrdemServicoHelper
							.getColecaoManterDadosAtividadesOrdemServicoHelper());
			BigDecimal valoorTotalMateriaias = recuperarValorMateriaisOS(dadosAtividadeorAtividadesOrdemServicoHelper
							.getColecaoManterDadosAtividadesOrdemServicoHelper());
			osNaBase.setValorHorasTrabalhadas(valorTotalHorasTrabalhadas);
			osNaBase.setValorMateriais(valoorTotalMateriaias);
		}

		// pesquisa os parametros tabela serviço tipo
		Short indAtualizarComercial = null;
		Short indIncluirDebito = null;
		Integer idDebitoTipo = null;
		Object[] parmsServTipo = null;
		Integer idServicoTipo = null;
		Integer idImovel = null;
		BigDecimal valorServico = null;

		try{
			parmsServTipo = repositorioOrdemServico.recuperarParametrosServicoTipo(helper.getNumeroOS());
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(parmsServTipo != null){
			if(parmsServTipo[0] != null){
				indAtualizarComercial = (Short) parmsServTipo[0];
			}
			if(parmsServTipo[1] != null){
				idDebitoTipo = (Integer) parmsServTipo[1];
			}
			if(parmsServTipo[2] != null){
				idImovel = (Integer) parmsServTipo[2];
			}
			if(parmsServTipo[3] != null){
				idServicoTipo = (Integer) parmsServTipo[3];
			}
			if(parmsServTipo[4] != null){
				indIncluirDebito = (Short) parmsServTipo[4];
			}
			if(parmsServTipo[5] != null){
				valorServico = (BigDecimal) parmsServTipo[5];
			}
		}
		if(indAtualizarComercial != null && !indAtualizarComercial.equals("")){
			if(indAtualizarComercial.equals(ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_SIM)){
				osNaBase.setIndicadorComercialAtualizado(ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_SIM);

				if(helper.getIntegracaoComercialHelper() != null && !helper.getIntegracaoComercialHelper().equals("")){
					if(helper.getIntegracaoComercialHelper().getOrdemServico() != null
									&& !helper.getIntegracaoComercialHelper().getOrdemServico().equals("")){
						if(helper.getIntegracaoComercialHelper().getOrdemServico().getValorAtual() != null
										&& !helper.getIntegracaoComercialHelper().getOrdemServico().getValorAtual().equals("")){
							osNaBase.setValorAtual(helper.getIntegracaoComercialHelper().getOrdemServico().getValorAtual());
						}
						if(helper.getIntegracaoComercialHelper().getOrdemServico().getServicoNaoCobrancaMotivo() != null
										&& !helper.getIntegracaoComercialHelper().getOrdemServico().getServicoNaoCobrancaMotivo()
														.equals("")){
							if(!helper.getIntegracaoComercialHelper().getOrdemServico().getServicoNaoCobrancaMotivo().getId()
											.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
								osNaBase.setServicoNaoCobrancaMotivo(helper.getIntegracaoComercialHelper().getOrdemServico()
												.getServicoNaoCobrancaMotivo());
							}
						}
						if(helper.getIntegracaoComercialHelper().getOrdemServico().getPercentualCobranca() != null
										&& !helper.getIntegracaoComercialHelper().getOrdemServico().getPercentualCobranca().equals("")){
							osNaBase.setPercentualCobranca(helper.getIntegracaoComercialHelper().getOrdemServico().getPercentualCobranca());
						}
					}
				}

			}
		}

		try{
			AtendimentoMotivoEncerramento atendimentoMotivo = repositorioOrdemServico
							.pesquisarAtendimentoMotivoEncerramentoPorId(idMotivoEncerramento);

			osNaBase.setAtendimentoMotivoEncerramento(atendimentoMotivo);
			osNaBase.setUltimaAlteracao(new Date());
			if(helper.getIntegracaoComercialHelper() != null && helper.getIntegracaoComercialHelper().getOrdemServico() != null){
				helper.getIntegracaoComercialHelper().getOrdemServico().setUltimaAlteracao(new Date());
			}

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ORDEM_SERVICO_ENCERRAR, osNaBase.getId(),
							osNaBase.getId(), new UsuarioAcaoUsuarioHelper(helper.getUsuarioLogado(),
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(osNaBase);
			// ------------ REGISTRAR TRANSAÇÃO---------------------------

			this.getControladorUtil().atualizar(osNaBase);
			// inseri a tabela ordem serviço unidade
			OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();

			// id do usuário logado
			if(helper.getUsuarioLogado().getId() != null && !helper.getUsuarioLogado().getId().equals("")){

				// unidade do usuário que está logado
				if(helper.getUsuarioLogado().getUnidadeOrganizacional() != null
								&& !helper.getUsuarioLogado().getUnidadeOrganizacional().equals("")
								&& helper.getUsuarioLogado().getUnidadeOrganizacional().getId() != null
								&& !helper.getUsuarioLogado().getUnidadeOrganizacional().getId().equals("")){
					UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
					unidadeOrganizacional.setId(helper.getUsuarioLogado().getUnidadeOrganizacional().getId());
					// seta a unidade organizacional na ordem serviço unidade
					ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacional);
				}

				// seta o usuário na ordem serviço unidade
				ordemServicoUnidade.setUsuario(helper.getUsuarioLogado());

			}

			// inseri as ordem de serviço na ordem serviço unidade
			if(helper.getNumeroOS() != null && !helper.getNumeroOS().equals("")){
				OrdemServico ordemServico = new OrdemServico();
				ordemServico.setId(helper.getNumeroOS());
				ordemServicoUnidade.setOrdemServico(ordemServico);
			}

			// seta o id do atendimento relação tipo
			AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
			atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
			ordemServicoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);

			// seta a ultima alteração com a data atual
			ordemServicoUnidade.setUltimaAlteracao(new Date());

			// inseri a ordem de serviço unidade
			this.getControladorUtil().inserir(ordemServicoUnidade);

			// [SB0009] Verificar Atualização Situação Ação Documento de Cobrança
			atualizarSituacaoAcaoCobranca(helper.getNumeroOS(), CobrancaAcaoSituacao.EXECUTADA);

			/*
			 * [OC790655][UC0457][SB0007]: Verificar Atualização Dados Encerramento no
			 * Histórico de Manutenção da Ligação do Imóvel, passando a ordem de serviço encerrada.
			 */
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, osNaBase.getServicoTipo().getId()));
			ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroServicoTipo,
							ServicoTipo.class.getName()));
			if(ConstantesSistema.SIM.equals(servicoTipo.getIndicadorGerarHistoricoImovel())){
				getControladorLigacaoAgua()
								.atualizarHistoricoManutencaoLigacao(osNaBase, HistoricoManutencaoLigacao.ENCERRAR_ORDEM_SERVICO);
			}

			// Verifica se a data de execução vinda como parametro do método é diferente de nulo
			if(helper.getDataExecucao() != null && !helper.getDataExecucao().equals("")){
				// [SB0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
				verificarExcluirAtualizarProgramacaoOS(helper.getNumeroOS(), helper.getDataExecucao());
			}

			// caso exista a ordem de serviço fiscalização então gera a os Fiscalização
			// Comentado por Raphael Rossiter em 28/02/2007
			/*
			 * if (osFiscalizacao != null && !osFiscalizacao.equals("")) { // seta a situação para 2
			 * pois já foi atualizado como encerrado na // base
			 * essa os osFiscalizacao.getOsReferencia().setSituacao(new Short("2"));
			 * gerarOrdemServico(osFiscalizacao, usuarioLogado); }
			 */

			Integer idUnidadeAtual = null;
			if(helper.getOsFiscalizacao() != null && helper.getOsFiscalizacao().getOsReferencia() != null){
				// seta a situação para 2 pois já foi atualizado como encerrado na base essa os
				helper.getOsFiscalizacao().getOsReferencia().setSituacao(Short.valueOf("2"));

				Integer idLocalidade = null;
				Integer idSetorComercial = null;
				Integer idBairro = null;

				Object[] dadosDaRA = null;
				if(osNaBase.getRegistroAtendimento() != null){
					dadosDaRA = this.getControladorRegistroAtendimento().pesquisarDadosLocalizacaoRegistroAtendimento(
									osNaBase.getRegistroAtendimento().getId());
				}

				if(dadosDaRA != null){
					idLocalidade = (Integer) dadosDaRA[0];
					idSetorComercial = (Integer) dadosDaRA[1];
					idBairro = (Integer) dadosDaRA[2];

					UnidadeOrganizacional unidadeAtual = getControladorRegistroAtendimento().obterUnidadeAtendimentoRA(
									osNaBase.getRegistroAtendimento().getId());
					idUnidadeAtual = unidadeAtual.getId();
				}else{

					Object[] dadosDoImovel = null;
					if(helper.getOsFiscalizacao().getImovel() != null){
						dadosDoImovel = this.getControladorImovel().pesquisarDadosLocalizacaoImovel(
										helper.getOsFiscalizacao().getImovel().getId());
					}

					if(dadosDoImovel != null){
						idLocalidade = (Integer) dadosDoImovel[0];
						idSetorComercial = (Integer) dadosDoImovel[1];
						idBairro = (Integer) dadosDoImovel[2];
					}

					UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualOrdemServico(helper.getNumeroOS());
					idUnidadeAtual = unidadeAtual.getId();
				}

				this.gerarOrdemServico(helper.getOsFiscalizacao(), helper.getUsuarioLogado(), null, idLocalidade, idSetorComercial,
								idBairro, idUnidadeAtual, null, null, null, false, null);
			}

			/*
			 * Colocado por Raphael Rossiter em 27/04/2007
			 * Caso o serviço associado à ordem de serviço tenha indicativo que é para gerar débito
			 * a cobrar automaticamente. Serviço associado não
			 * atualiza o sistema comercial Valor do serviço maior que zero Tipo do débito diferente
			 * de zero
			 */
			if(indIncluirDebito.shortValue() == ConstantesSistema.SIM.shortValue()){
				if(indAtualizarComercial.shortValue() == ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_NAO
							&& valorServico.compareTo(new BigDecimal("0.00")) == 1
							&& (idDebitoTipo != null && idDebitoTipo.intValue() != ConstantesSistema.ZERO.intValue())){

					// [UC0475] - Gerar Débito Ordem de Serviço
					this.gerarDebitoOrdemServico(helper.getNumeroOS(), idDebitoTipo, valorServico, 1);
				}
				if(helper.getIndicadorCobrarHorasMateriais().equals("1")
								&& osNaBase.getValorHorasTrabalhadas().compareTo(new BigDecimal("0.00")) == 1){
					if(DebitoTipoEnum.ORDEM_SERVICO_HORA.getId() != null){
						this.gerarDebitoOrdemServico(helper.getNumeroOS(), DebitoTipoEnum.ORDEM_SERVICO_HORA.getId(),
									osNaBase.getValorHorasTrabalhadas(), 1);
					}
				}

				if(helper.getIndicadorCobrarHorasMateriais().equals("1")
								&& osNaBase.getValorMateriais().compareTo(new BigDecimal("0.00")) == 1){
					if(DebitoTipoEnum.ORDEM_SERVICO_HORA.getId() != null){
						this.gerarDebitoOrdemServico(helper.getNumeroOS(), DebitoTipoEnum.ORDEM_SERVICO_HORA.getId(),
										osNaBase.getValorMateriais(), 1);
					}
				}
			}



			/*
			 * INTEGRAÇÃO COMERCIAL Autor: Raphael Rossiter Data: 25/04/2007
			 */
			if(helper.getTipoServicoOSId() != null){
				int idServicoTipoInt = Util.converterStringParaInteger(helper.getTipoServicoOSId());

				this.integracaoComercial(idServicoTipoInt, helper.getIntegracaoComercialHelper(), helper.getUsuarioLogado());
			}

			// verifica se fará gerações de Serviços Associados
			if(atendimentoMotivo.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM
							&& !origemEncerramento.equals(OrigemEncerramentoOrdemServico.ENCERRAMENTO_OS_ASSOCIADA)){
				ServicoTipo servicoTipoOS = null;
				try{
					servicoTipoOS = repositorioOrdemServico.pesquisarServicoTipoPorId(osNaBase.getServicoTipo().getId());
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
				if(servicoTipoOS.getServicosAssociados() != null && !servicoTipoOS.getServicosAssociados().isEmpty()){
					osNaBase.setServicoTipo(servicoTipoOS);
					this.gerarServicosAssociadosOrdemServico(osNaBase, helper.getUsuarioLogado(),
									EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO, origemEncerramento, autorizacoesServicos);
				}
			}
			// Trata os Dados da tela no processo de Encerramento da OS
			if(helper.getOcorrenciaInfracao() != null && helper.getColecaoInfracaoTipo() != null
							&& helper.getColecaoInfracaoTipo().length > 0){
				Collection<OcorrenciaInfracaoItem> listaInfracoesItem = this.inserirOcorrenciaInfracao(helper.getOcorrenciaInfracao(),
								helper.getColecaoInfracaoTipo());

				this.gerarSancaoInfracao(helper.getNumeroOS(), listaInfracoesItem);
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}catch(ControladorException ex){
			sessionContext.setRollbackOnly();
			throw ex;
		}

		// [SB0010] - Verificar Atualização Dados Encerramento no Histórico de Aferição do
		// Hidrômetro
		this.verificarAtualizacaoDadosEncerramentoHistoricoAfericaoHidrometro(helper, osNaBase);

		// ControladorOrdemServicoSEJB.showMem(this.getClass().getSimpleName(),
		// " void encerrarOSComExecucaoSemReferencia(OSEncerramentoHelper helper, Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos,",
		// "fim");

	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0010] - Verificar Atualização Dados Encerramento no Histórico de Aferição do Hidrômetro
	 * 
	 * @author Anderson Italo
	 * @date 09/09/2014
	 */
	private void verificarAtualizacaoDadosEncerramentoHistoricoAfericaoHidrometro(OSEncerramentoHelper helper, OrdemServico ordemServico)
					throws ControladorException{

		if(helper != null && ordemServico != null && ordemServico.getImovel() != null){

			// Caso o tipo de serviço da ordem de serviço encerrada tenha a indicação de geração de
			// dados no histórico de aferição de hidrômetro
			if(helper.getIndicadorAfericaoServicoTipo() != null && helper.getIndicadorAfericaoServicoTipo().equals(ConstantesSistema.SIM)){

				LigacaoAgua ligacaoAgua = (LigacaoAgua) getControladorUtil().pesquisar(ordemServico.getImovel().getId(), LigacaoAgua.class,
								true);

				if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null){

					HidrometroInstalacaoHistorico hidrometroInstalacaoHistorico = (HidrometroInstalacaoHistorico) getControladorUtil()
									.pesquisar(ligacaoAgua.getHidrometroInstalacaoHistorico().getId(), HidrometroInstalacaoHistorico.class,
													true);

					// Atualiza a tabela HIDROMETRO_HISTORICO_AFERICAO
					HidrometroHistoricoAfericao hidrometroHistoricoAfericao = new HidrometroHistoricoAfericao();
					hidrometroHistoricoAfericao.setHidrometro(hidrometroInstalacaoHistorico.getHidrometro());

					Funcionario funcionario = (Funcionario) getControladorUtil().pesquisar(helper.getIdFuncionario(), Funcionario.class,
									false);

					if(funcionario != null){

						hidrometroHistoricoAfericao.setFuncionario(funcionario);
					}else{

						throw new ControladorException("atencao.required", null, "Funcionário");
					}

					HidrometroCondicao hidrometroCondicao = (HidrometroCondicao) getControladorUtil().pesquisar(
									helper.getIdHidrometroCondicao(), HidrometroCondicao.class, false);

					if(hidrometroCondicao != null){

						hidrometroHistoricoAfericao.setHidrometroCondicao(hidrometroCondicao);
					}else{

						throw new ControladorException("atencao.required", null, "Condição do Hidrômetro para Aferição");
					}

					hidrometroHistoricoAfericao.setOrdemServico(ordemServico);

					if(helper.getIndicadorClienteAcompanhou() != null){

						hidrometroHistoricoAfericao.setIndicadorClienteAcompanhou(helper.getIndicadorClienteAcompanhou());
					}else{

						throw new ControladorException("atencao.required", null, "Cliente Acompanhou");
					}

					if(helper.getIndicadorResultado() != null){

						hidrometroHistoricoAfericao.setIndicadorResultado(helper.getIndicadorResultado());
					}else{

						throw new ControladorException("atencao.required", null, "Resultado");
					}

					hidrometroHistoricoAfericao.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
					hidrometroHistoricoAfericao.setUltimaAlteracao(new Date());

					if(ordemServico.getDataExecucao() != null){

						hidrometroHistoricoAfericao.setDataAfericao(ordemServico.getDataExecucao());
					}else{

						throw new ControladorException("atencao.required", null, "Data de Execução");
					}

					getControladorUtil().inserir(hidrometroHistoricoAfericao);

					// Atualiza a tabela HIDROMETRO
					Hidrometro hidrometro = (Hidrometro) getControladorUtil().pesquisar(
									hidrometroInstalacaoHistorico.getHidrometro().getId(), Hidrometro.class, true);
					hidrometro.setEmpresaUltimaAfericao(funcionario.getEmpresa());
					hidrometro.setDataUltimaRevisao(ordemServico.getDataExecucao());
					hidrometro.setUltimaAlteracao(new Date());

					getControladorUtil().atualizar(hidrometro);
				}
			}
		}
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0003] - Encerrar com execução e com referência
	 * 
	 * @author Sávio Luiz
	 * @date 27/09/2006
	 * @author eduardo henrique
	 * @date 21/05/2009 Alteração para Implementação de OS's Associadas.
	 * @throws ControladorException
	 */
	public Integer encerrarOSComExecucaoComReferencia(OSEncerramentoHelper helper,
					Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos,
					OrigemEncerramentoOrdemServico origemEncerramento, DadosAtividadesOrdemServicoHelper dadosAtividadesOrdemServicoHelper)
					throws ControladorException{

		Integer idMotivoEncerramento = null;

		this.inserirDadosAtividadesOrdemServico(dadosAtividadesOrdemServicoHelper);

		// caso o motivo de encerramento esteja nulo
		if(helper.getIdMotivoEncerramento() == null || helper.getIdMotivoEncerramento().equals("")){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.required", null, "Motivo de Encerramento");
		}else{
			idMotivoEncerramento = Util.converterStringParaInteger(helper.getIdMotivoEncerramento());
		}

		// se criar automaticamento outra ordem de Serviço retornar o id da OS nova senão retornar
		// null
		Integer idOrdemServicoDiagnostico = null;

		// caso a data de execução esteja nula
		if(helper.getDataExecucao() == null || helper.getDataExecucao().equals("")){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.required", null, "Data de Execução");
		}

		// caso a data de encerramento esteja nula
		if(helper.getIdTipoRetornoOSReferida() == null || helper.getIdTipoRetornoOSReferida().equals("")){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.required", null, "Tipo de Retorno Referida");
		}
		if(helper.getIndicadorDeferimento() != null && !helper.getIndicadorDeferimento().equals("")){

			short indDeferimento = Short.parseShort(helper.getIndicadorDeferimento());

			// se indicador deferimento for igual a sim(1)
			if(indDeferimento == OsReferidaRetornoTipo.INDICADOR_DEFERIMENTO_SIM){

				// 9.1.1 caso o indicador de pavimento esteja igual a sim e o pavimento não esteja
				// preenchido então
				if(helper.getIndicadorPavimento() != null && !helper.getIndicadorPavimento().equals("")){
					short indicadorPavimentoShort = Short.valueOf(helper.getIndicadorPavimento());
					if(indicadorPavimentoShort == ServicoTipo.INDICADOR_PAVIMENTO_SIM){
						if(helper.getPavimento() == null || helper.getPavimento().equals("")){
							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.required", null, "Pavimento");
						}
					}
				}

				// 9.1.2 caso o indicador de troca se serviço da tabela os referida retorno tipo
				// seja diferente de nula e igual a sim
				if(helper.getIndicadorTrocaServicoTipo() != null && !helper.getIndicadorTrocaServicoTipo().equals("")){
					short indicadorTrocaServicoTipoShort = Short.valueOf(helper.getIndicadorTrocaServicoTipo());
					if(indicadorTrocaServicoTipoShort == OsReferidaRetornoTipo.INDICADOR_TROCA_SERVICO_SIM){
						if(helper.getIdServicoTipo() == null || helper.getIdServicoTipo().equals("")){
							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.required", null, "Tipo de Serviço");
						}
					}
				}
			}
		}

		if(helper.getIndicadorVistoriaServicoTipo() != null && !helper.getIndicadorVistoriaServicoTipo().equals("")){
			if(helper.getIndicadorVistoriaServicoTipo().equals(ServicoTipo.INDICADOR_VISTORIA_SIM)){
				if(helper.getCodigoRetornoVistoriaOs() == null || helper.getCodigoRetornoVistoriaOs().equals("")){
					throw new ControladorException("atencao.required", null, "Retorno Vistoria");
				}
			}
		}

		// INICIO ATUALIZAÇÃO ORDEM SERVIÇO
		// controle de transação
		FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();

		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, helper.getNumeroOS()));

		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipo.servicoTipoPrioridade");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("servicoTipo.servicoTipoReferencia.servicoTipo");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO_SERVICOS_ASSOCIADOS);
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("osReferencia");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("ordemServicoUnidades");

		Collection colecaoOS = getControladorUtil().pesquisar(filtroOrdemServico, OrdemServico.class.getName());

		if(colecaoOS == null || colecaoOS.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		helper.setUltimaAlteracao(new Date());

		OrdemServico osNaBase = (OrdemServico) colecaoOS.iterator().next();
		if(helper.getUltimaAlteracao() != null && !helper.getUltimaAlteracao().equals("")){

			if(osNaBase.getUltimaAlteracao().after(helper.getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}
		}
		// fim controle transação

		FiltroOrdemServicoUnidade filtroOrdemServicoUnidade = new FiltroOrdemServicoUnidade();
		filtroOrdemServicoUnidade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoUnidade.ORDEM_SERVICO_ID, osNaBase.getId()));
		filtroOrdemServicoUnidade.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");

		Set<OrdemServicoUnidade> setOSUnidade = new TreeSet<OrdemServicoUnidade>();
		setOSUnidade.addAll(getControladorUtil().pesquisar(filtroOrdemServicoUnidade, OrdemServicoUnidade.class.getName()));

		osNaBase.setOrdemServicoUnidades(setOSUnidade);

		if(helper.getCodigoRetornoVistoriaOs() == null || helper.getCodigoRetornoVistoriaOs().equals("")){
			osNaBase.setCodigoRetornoVistoria(null);
		}else{
			osNaBase.setCodigoRetornoVistoria(Short.valueOf(helper.getCodigoRetornoVistoriaOs()));
		}

		// atualizar o objeto da base seta o código da situação como 2
		osNaBase.setSituacao(OrdemServico.SITUACAO_ENCERRADO);

		// seta a data de encerramento
		osNaBase.setDataEncerramento(new Date());

		// seta a data de execução informada
		osNaBase.setDataExecucao(helper.getDataExecucao());

		// seta o parecer de encerramento
		if(helper.getParecerEncerramento() != null && !helper.getParecerEncerramento().equals("")){
			osNaBase.setDescricaoParecerEncerramento(helper.getParecerEncerramento());
		}else{
			osNaBase.setDescricaoParecerEncerramento(null);
		}

		// seta o pavimento
		if(helper.getPavimento() != null && !helper.getPavimento().equals("")){
			osNaBase.setAreaPavimento(new BigDecimal(helper.getPavimento()).setScale(2, BigDecimal.ROUND_FLOOR));
		}else{
			osNaBase.setAreaPavimento(null);
		}

		if(helper.getDimensao1() != null && !helper.getDimensao1().equals("")){
			osNaBase.setDimensao1(Util.formatarMoedaRealparaBigDecimal(helper.getDimensao1()));
		}else{
			osNaBase.setDimensao1(null);
		}

		if(helper.getDimensao2() != null && !helper.getDimensao2().equals("")){
			osNaBase.setDimensao2(Util.formatarMoedaRealparaBigDecimal(helper.getDimensao2()));
		}else{
			osNaBase.setDimensao2(null);
		}

		if(helper.getDimensao3() != null && !helper.getDimensao3().equals("")){
			osNaBase.setDimensao3(Util.formatarMoedaRealparaBigDecimal(helper.getDimensao3()));
		}else{
			osNaBase.setDimensao3(null);
		}

		if(dadosAtividadesOrdemServicoHelper != null){
			BigDecimal valorTotalHorasTrabalhadas = recuperarValorHorasTrabalhadasOS(dadosAtividadesOrdemServicoHelper
							.getColecaoManterDadosAtividadesOrdemServicoHelper());
			BigDecimal valoorTotalMateriaias = recuperarValorMateriaisOS(dadosAtividadesOrdemServicoHelper
							.getColecaoManterDadosAtividadesOrdemServicoHelper());
			osNaBase.setValorHorasTrabalhadas(valorTotalHorasTrabalhadas);
			osNaBase.setValorMateriais(valoorTotalMateriaias);
		}

		// seta o id da os referida retorno tipo na os
		Integer idOSRetornoTipoReferida = Util.converterStringParaInteger(helper.getIdTipoRetornoOSReferida());
		OsReferidaRetornoTipo osReferidaRetornoTipo = new OsReferidaRetornoTipo();
		osReferidaRetornoTipo.setId(idOSRetornoTipoReferida);
		osNaBase.setOsReferidaRetornoTipo(osReferidaRetornoTipo);

		// caso o indicador de troca de serviço seja igual a sim(1)
		if(helper.getIndicadorTrocaServicoTipo() != null && !helper.getIndicadorTrocaServicoTipo().equals("")){
			short indicadorTrocaServicoTipoShort = Short.valueOf(helper.getIndicadorTrocaServicoTipo());
			if(indicadorTrocaServicoTipoShort == OsReferidaRetornoTipo.INDICADOR_TROCA_SERVICO_SIM){
				ServicoTipo servicoTipoReferencia = new ServicoTipo();
				servicoTipoReferencia.setId(Util.converterStringParaInteger(helper.getIdServicoTipo()));
				// seta o serviço tipo referencia com o serviço tipo escolhido
				osNaBase.setServicoTipoReferencia(servicoTipoReferencia);
			}else{
				osNaBase.setServicoTipoReferencia(null);
			}
		}else{
			osNaBase.setServicoTipoReferencia(null);
		}

		// pesquisa os parametros tabela serviço tipo
		Short indAtualizarComercial = null;
		Short indIncluirDebito = null;
		Integer idDebitoTipo = null;
		Object[] parmsServTipo = null;
		Integer idImovel = null;
		BigDecimal valorServico = null;
		try{
			parmsServTipo = repositorioOrdemServico.recuperarParametrosServicoTipo(helper.getNumeroOS());
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		if(parmsServTipo != null){
			if(parmsServTipo[0] != null){
				indAtualizarComercial = (Short) parmsServTipo[0];
			}
			if(parmsServTipo[1] != null){
				idDebitoTipo = (Integer) parmsServTipo[1];
			}
			if(parmsServTipo[2] != null){
				idImovel = (Integer) parmsServTipo[2];
			}
			if(parmsServTipo[4] != null){
				indIncluirDebito = (Short) parmsServTipo[4];
			}
			if(parmsServTipo[5] != null){
				valorServico = (BigDecimal) parmsServTipo[5];
			}

		}

		if(indAtualizarComercial != null && !indAtualizarComercial.equals("")){
			if(indAtualizarComercial.equals(ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_SIM)){
				osNaBase.setIndicadorComercialAtualizado(ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_SIM);

				if(helper.getIntegracaoComercialHelper() != null && !helper.getIntegracaoComercialHelper().equals("")){
					if(helper.getIntegracaoComercialHelper().getOrdemServico() != null
									&& !helper.getIntegracaoComercialHelper().getOrdemServico().equals("")){
						if(helper.getIntegracaoComercialHelper().getOrdemServico().getValorAtual() != null
										&& !helper.getIntegracaoComercialHelper().getOrdemServico().getValorAtual().equals("")){
							osNaBase.setValorAtual(helper.getIntegracaoComercialHelper().getOrdemServico().getValorAtual());
						}
						if(helper.getIntegracaoComercialHelper().getOrdemServico().getServicoNaoCobrancaMotivo() != null
										&& !helper.getIntegracaoComercialHelper().getOrdemServico().getServicoNaoCobrancaMotivo()
														.equals("")){
							if(!helper.getIntegracaoComercialHelper().getOrdemServico().getServicoNaoCobrancaMotivo()
											.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
								osNaBase.setServicoNaoCobrancaMotivo(helper.getIntegracaoComercialHelper().getOrdemServico()
												.getServicoNaoCobrancaMotivo());
							}
						}
						if(helper.getIntegracaoComercialHelper().getOrdemServico().getPercentualCobranca() != null
										&& !helper.getIntegracaoComercialHelper().getOrdemServico().getPercentualCobranca().equals("")){
							osNaBase.setPercentualCobranca(helper.getIntegracaoComercialHelper().getOrdemServico().getPercentualCobranca());
						}
					}
				}

			}
		}

		try{
			AtendimentoMotivoEncerramento atendimentoMotivo = repositorioOrdemServico
							.pesquisarAtendimentoMotivoEncerramentoPorId(idMotivoEncerramento);
			osNaBase.setAtendimentoMotivoEncerramento(atendimentoMotivo);
			osNaBase.setUltimaAlteracao(new Date());
			if(helper.getIntegracaoComercialHelper() != null && helper.getIntegracaoComercialHelper().getOrdemServico() != null){
				helper.getIntegracaoComercialHelper().getOrdemServico().setUltimaAlteracao(new Date());
			}

			// ------------ REGISTRAR TRANSAÇÃO----------------------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_ORDEM_SERVICO_ENCERRAR, osNaBase.getId(),
							osNaBase.getId(), new UsuarioAcaoUsuarioHelper(helper.getUsuarioLogado(),
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(osNaBase);
			// ------------ REGISTRAR TRANSAÇÃO----------------------------

			getControladorUtil().atualizar(osNaBase);

			// FIM ATUALIZAÇÃO ORDEM SERVIÇO

			// INICIO ATUALIZAÇÃO ORDEM SERVIÇO REFERÊNCIA
			// controle de transação
			if(helper.getIdOSReferencia() != null && !helper.getIdOSReferencia().equals("")){
				filtroOrdemServico = new FiltroOrdemServico();

				filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, helper.getIdOSReferencia()));

				Collection colecaoOSReferencia = getControladorUtil().pesquisar(filtroOrdemServico, OrdemServico.class.getName());

				if(colecaoOSReferencia == null || colecaoOSReferencia.isEmpty()){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.atualizacao.timestamp");
				}

				OrdemServico osReferenciaNaBase = new OrdemServico();
				osReferenciaNaBase = (OrdemServico) colecaoOSReferencia.iterator().next();
				// fim controle transação

				Integer idMotivoEncerramentoOSReferida = null;

				try{
					idMotivoEncerramentoOSReferida = repositorioOrdemServico
									.pesquisarIdMotivoEncerramentoOSReferida(idOSRetornoTipoReferida);
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				if(idMotivoEncerramentoOSReferida != null){

					// seta o id da os referida retorno tipo na os
					osReferidaRetornoTipo.setId(idOSRetornoTipoReferida);
					osReferenciaNaBase.setOsReferidaRetornoTipo(osReferidaRetornoTipo);

					// seta a data de encerramento
					osReferenciaNaBase.setDataEncerramento(new Date());

					// seta a data de execução informada
					osReferenciaNaBase.setDataExecucao(helper.getDataExecucao());

					// inseri a tabela ordem serviço unidade
					OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();

					// id do usuário logado
					if(helper.getUsuarioLogado().getId() != null && !helper.getUsuarioLogado().getId().equals("")){

						// unidade do usuário que está logado
						if(helper.getUsuarioLogado().getUnidadeOrganizacional() != null
										&& !helper.getUsuarioLogado().getUnidadeOrganizacional().equals("")
										&& helper.getUsuarioLogado().getUnidadeOrganizacional().getId() != null
										&& !helper.getUsuarioLogado().getUnidadeOrganizacional().getId().equals("")){

							UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
							unidadeOrganizacional.setId(helper.getUsuarioLogado().getUnidadeOrganizacional().getId());

							// seta a unidade organizacional na ordem serviço unidade
							ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacional);
						}

						// seta o usuário na ordem serviço unidade
						ordemServicoUnidade.setUsuario(helper.getUsuarioLogado());
					}

					// inseri as ordem de serviço na ordem serviço unidade
					OrdemServico ordemServico = new OrdemServico();
					ordemServico.setId(Integer.valueOf(helper.getIdOSReferencia()));
					ordemServicoUnidade.setOrdemServico(ordemServico);

					// seta o id do atendimento relação tipo
					AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
					atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
					ordemServicoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);

					// seta a ultima alteração com a data atual
					ordemServicoUnidade.setUltimaAlteracao(new Date());

					// inseri a ordem de serviço unidade
					getControladorUtil().inserir(ordemServicoUnidade);

				}else{
					osReferenciaNaBase.setOsReferidaRetornoTipo(null);
					osReferenciaNaBase.setDataEncerramento(null);
					osReferenciaNaBase.setDataExecucao(null);
				}

				// atualizar o objeto da base seta o código da situação como 2
				Short codigoSituacaoOSReferida = null;
				try{
					codigoSituacaoOSReferida = repositorioOrdemServico.recuperarSituacaoOSReferida(idOSRetornoTipoReferida);
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				if(codigoSituacaoOSReferida != null && !codigoSituacaoOSReferida.equals("")){
					osReferenciaNaBase.setSituacao(codigoSituacaoOSReferida);
				}

				// caso o indicador de troca de serviço seja igual a sim(1)
				if(helper.getIndicadorTrocaServicoTipo() != null && !helper.getIndicadorTrocaServicoTipo().equals("")){
					short indicadorTrocaServicoTipoShort = Short.valueOf(helper.getIndicadorTrocaServicoTipo());
					if(indicadorTrocaServicoTipoShort == OsReferidaRetornoTipo.INDICADOR_TROCA_SERVICO_SIM){
						ServicoTipo servicoTipo = new ServicoTipo();
						servicoTipo.setId(Util.converterStringParaInteger(helper.getIdServicoTipo()));
						// seta o serviço tipo referencia com o serviço tipo escolhido
						osReferenciaNaBase.setServicoTipo(servicoTipo);
					}else{
						osReferenciaNaBase.setServicoTipoReferencia(null);
					}
				}else{
					osReferenciaNaBase.setServicoTipoReferencia(null);
				}

				osReferenciaNaBase.setIndicadorComercialAtualizado(ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_NAO);

				Short indicadorDiagnostico = null;
				try{
					indicadorDiagnostico = repositorioOrdemServico.pesquisarIndDiagnosticoServicoTipoReferencia(Util
									.converterStringParaInteger(helper.getIdOSReferencia()));
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
				if(indicadorDiagnostico != null && !indicadorDiagnostico.equals("")){
					if(indicadorDiagnostico.equals(ServicoTipoReferencia.INDICADOR_DIAGNOSTICO_ATIVO)){
						osReferenciaNaBase.setIndicadorServicoDiagnosticado(ServicoTipoReferencia.INDICADOR_DIAGNOSTICO_ATIVO);
					}
				}
				osReferenciaNaBase.setUltimaAlteracao(new Date());

				getControladorUtil().atualizar(osReferenciaNaBase);
				/*
				 * [OC790655][UC0457][SB0007]: Verificar Atualização Dados Encerramento
				 * no Histórico de Manutenção da Ligação do Imóvel, passando a ordem de serviço de
				 * referência.
				 */
				if(idMotivoEncerramentoOSReferida != null
								&& ConstantesSistema.SIM.equals(osReferenciaNaBase.getServicoTipo().getIndicadorGerarHistoricoImovel())){
					getControladorLigacaoAgua().atualizarHistoricoManutencaoLigacao(osReferenciaNaBase,
									HistoricoManutencaoLigacao.ENCERRAR_ORDEM_SERVICO);
				}
			}

			// FIM ATUALIZAÇÃO ORDEM SERVIÇO REFERÊNCIA

			// inseri a tabela ordem serviço unidade
			OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();

			// id do usuário logado
			if(helper.getUsuarioLogado().getId() != null && !helper.getUsuarioLogado().getId().equals("")){

				// unidade do usuário que está logado
				if(helper.getUsuarioLogado().getUnidadeOrganizacional() != null
								&& !helper.getUsuarioLogado().getUnidadeOrganizacional().equals("")
								&& helper.getUsuarioLogado().getUnidadeOrganizacional().getId() != null
								&& !helper.getUsuarioLogado().getUnidadeOrganizacional().getId().equals("")){

					UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
					unidadeOrganizacional.setId(helper.getUsuarioLogado().getUnidadeOrganizacional().getId());

					// seta a unidade organizacional na ordem serviço unidade
					ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacional);
				}

				// seta o usuário na ordem serviço unidade
				ordemServicoUnidade.setUsuario(helper.getUsuarioLogado());
			}

			// inseri as ordem de serviço na ordem serviço unidade
			if(helper.getNumeroOS() != null && !helper.getNumeroOS().equals("")){
				OrdemServico ordemServico = new OrdemServico();
				ordemServico.setId(helper.getNumeroOS());
				ordemServicoUnidade.setOrdemServico(ordemServico);
			}

			// seta o id do atendimento relação tipo
			AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
			atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
			ordemServicoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);

			// seta a ultima alteração com a data atual
			ordemServicoUnidade.setUltimaAlteracao(new Date());

			// inseri a ordem de serviço unidade
			getControladorUtil().inserir(ordemServicoUnidade);

			/*
			 * [OC790655][UC0457][SB0007]: Verificar Atualização Dados Encerramento no
			 * Histórico de Manutenção da Ligação do Imóvel, passando a ordem de serviço encerrada.
			 */
			if(ConstantesSistema.SIM.equals(osNaBase.getServicoTipo().getIndicadorGerarHistoricoImovel())){
				getControladorLigacaoAgua()
								.atualizarHistoricoManutencaoLigacao(osNaBase, HistoricoManutencaoLigacao.ENCERRAR_ORDEM_SERVICO);
			}

			// [SB0009] Verificar Atualização Situação Ação Documento de Cobrança
			atualizarSituacaoAcaoCobranca(helper.getNumeroOS(), CobrancaAcaoSituacao.EXECUTADA);

			// Verifica se a data de execução vinda como parametro do método é diferente de nulo
			if(helper.getDataExecucao() != null && !helper.getDataExecucao().equals("")){

				// [SB0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
				this.verificarExcluirAtualizarProgramacaoOS(helper.getNumeroOS(), helper.getDataExecucao());
			}

			Integer idLocalidade = null;
			Integer idSetorComercial = null;
			Integer idBairro = null;
			Integer idUnidadeAtual = null;
			Object[] dadosDaRA = null;

			// se o id deferimento da tabela os_Referida_Retorno_tipo for igual a sim
			if(helper.getIndicadorDeferimento() != null && !helper.getIndicadorDeferimento().equals("")){

				short indDeferimento = Short.parseShort(helper.getIndicadorDeferimento());
				if(indDeferimento == OsReferidaRetornoTipo.INDICADOR_DEFERIMENTO_SIM){

					// se o id do serviço tipo referência for diferente de nulo
					if(helper.getIdServicoTipoReferenciaOS() != null && !helper.getIdServicoTipoReferenciaOS().equals("")){

						// [UC0430] - Gerar Ordem de Serviço
						OrdemServico ordemServicoGerar = new OrdemServico();
						ordemServicoGerar.setRegistroAtendimento(osNaBase.getRegistroAtendimento());
						FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
						filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, helper
										.getIdServicoTipoReferenciaOS()));
						filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoPrioridade");
						filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoReferencia");
						Collection colecaoServicoTipo = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

						ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

						idImovel = null;
						try{
							idImovel = repositorioOrdemServico.recuperarIdImovelDoRA(osNaBase.getRegistroAtendimento().getId());
						}catch(ErroRepositorioException e){
							sessionContext.setRollbackOnly();
							throw new ControladorException("erro.sistema", e);
						}

						// caso exista o imóvel seta na ordem de serviço
						if(idImovel != null && !idImovel.equals("")){
							Imovel imovel = null;

							FiltroImovel filtroImovel = new FiltroImovel();
							filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
							filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA);
							Collection<Imovel> colecaoImovel = getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName());

							if(colecaoImovel != null && !colecaoImovel.isEmpty()){
								imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
							}

							if(imovel != null){
								ordemServicoGerar.setImovel(imovel);
							}else{
								throw new ControladorException("atencao.pesquisa_inexistente", null, "Imovel " + idImovel);
							}
						}

						ordemServicoGerar.setServicoTipo(servicoTipo);

						if(osNaBase.getRegistroAtendimento() != null){
							dadosDaRA = this.getControladorRegistroAtendimento().pesquisarDadosLocalizacaoRegistroAtendimento(
											osNaBase.getRegistroAtendimento().getId());
						}

						if(dadosDaRA != null){
							idLocalidade = (Integer) dadosDaRA[0];
							idSetorComercial = (Integer) dadosDaRA[1];
							idBairro = (Integer) dadosDaRA[2];

							UnidadeOrganizacional unidadeAtual = getControladorRegistroAtendimento().obterUnidadeAtendimentoRA(
											osNaBase.getRegistroAtendimento().getId());
							idUnidadeAtual = unidadeAtual.getId();
						}else{

							Object[] dadosDoImovel = null;
							if(ordemServicoGerar.getImovel() != null){
								dadosDoImovel = this.getControladorImovel().pesquisarDadosLocalizacaoImovel(
												ordemServicoGerar.getImovel().getId());
							}

							if(dadosDoImovel != null){
								idLocalidade = (Integer) dadosDoImovel[0];
								idSetorComercial = (Integer) dadosDoImovel[1];
								idBairro = (Integer) dadosDoImovel[2];
							}

							UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualOrdemServico(helper.getNumeroOS());
							idUnidadeAtual = unidadeAtual.getId();
						}

						idOrdemServicoDiagnostico = this.gerarOrdemServico(ordemServicoGerar, helper.getUsuarioLogado(), null,
										idLocalidade, idSetorComercial, idBairro, idUnidadeAtual, null, null, null, false, null);

						// idOrdemServicoDiagnostico = gerarOrdemServico(ordemServicoGerar,
						// helper.getUsuarioLogado(), null);
					}
				}
			}

			// caso exista a ordem de serviço fiscalização então gera a os Fiscalização
			if(helper.getOsFiscalizacao() != null && !helper.getOsFiscalizacao().equals("")){

				if(helper.getOsFiscalizacao().getOsReferencia() != null){
					// seta a situação para 2 pois já foi atualizado como encerrado na base essa os
					helper.getOsFiscalizacao().getOsReferencia().setSituacao(Short.valueOf("2"));
				}

				if(osNaBase.getRegistroAtendimento() != null){
					dadosDaRA = this.getControladorRegistroAtendimento().pesquisarDadosLocalizacaoRegistroAtendimento(
									osNaBase.getRegistroAtendimento().getId());
				}

				if(dadosDaRA != null){
					idLocalidade = (Integer) dadosDaRA[0];
					idSetorComercial = (Integer) dadosDaRA[1];
					idBairro = (Integer) dadosDaRA[2];

					UnidadeOrganizacional unidadeAtual = getControladorRegistroAtendimento().obterUnidadeAtendimentoRA(
									osNaBase.getRegistroAtendimento().getId());
					idUnidadeAtual = unidadeAtual.getId();
				}else{

					Object[] dadosDoImovel = null;
					if(helper.getOsFiscalizacao().getImovel() != null){
						dadosDoImovel = this.getControladorImovel().pesquisarDadosLocalizacaoImovel(
										helper.getOsFiscalizacao().getImovel().getId());
					}

					if(dadosDoImovel != null){
						idLocalidade = (Integer) dadosDoImovel[0];
						idSetorComercial = (Integer) dadosDoImovel[1];
						idBairro = (Integer) dadosDoImovel[2];
					}

					UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualOrdemServico(helper.getNumeroOS());
					idUnidadeAtual = unidadeAtual.getId();
				}

				this.gerarOrdemServico(helper.getOsFiscalizacao(), helper.getUsuarioLogado(), null, idLocalidade, idSetorComercial,
								idBairro, idUnidadeAtual, null, null, null, false, null);

				// gerarOrdemServico(helper.getOsFiscalizacao(), helper.getUsuarioLogado(), null);

			}

			/*
			 * Colocado por Raphael Rossiter em 27/04/2007
			 * Caso o serviço associado à ordem de serviço tenha indicativo que é para gerar débito
			 * a cobrar automaticamente. Serviço associado não
			 * atualiza o sistema comercial Valor do serviço maior que zero Tipo do débito diferente
			 * de zero
			 */

			if(indIncluirDebito != null && indAtualizarComercial != null){
				if(indIncluirDebito.shortValue() == ConstantesSistema.SIM.shortValue()){
					if(indAtualizarComercial.shortValue() == ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_NAO
								&& valorServico.compareTo(new BigDecimal("0.00")) == 1
								&& (idDebitoTipo != null && idDebitoTipo.intValue() != ConstantesSistema.ZERO.intValue())){

					// [UC0475] - Gerar Débito Ordem de Serviço
					this.gerarDebitoOrdemServico(helper.getNumeroOS(), idDebitoTipo, valorServico, 1);
					}
					if(helper.getIndicadorCobrarHorasMateriais().equals("1")
									&& osNaBase.getValorHorasTrabalhadas().compareTo(new BigDecimal("0.00")) == 1){
						this.gerarDebitoOrdemServico(helper.getNumeroOS(), idDebitoTipo, osNaBase.getValorHorasTrabalhadas(), 1);
					}

					if(helper.getIndicadorCobrarHorasMateriais().equals("1")
									&& osNaBase.getValorMateriais().compareTo(new BigDecimal("0.00")) == 1){
						this.gerarDebitoOrdemServico(helper.getNumeroOS(), idDebitoTipo, osNaBase.getValorMateriais(), 1);
					}
				}
			}


			/*
			 * INTEGRAÇÃO COMERCIAL Autor: Raphael Rossiter Data: 25/04/2007
			 */

			if(helper.getTipoServicoOSId() != null){
				int idServicoTipoInt = Util.converterStringParaInteger(helper.getTipoServicoOSId());
				this.integracaoComercial(idServicoTipoInt, helper.getIntegracaoComercialHelper(), helper.getUsuarioLogado());
			}

			// verifica se fará gerações de Serviços Associados
			if(atendimentoMotivo.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM
							&& !origemEncerramento.equals(OrigemEncerramentoOrdemServico.ENCERRAMENTO_OS_ASSOCIADA)){
				ServicoTipo servicoTipoOS = null;
				try{
					servicoTipoOS = repositorioOrdemServico.pesquisarServicoTipoPorId(osNaBase.getServicoTipo().getId());
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
				if(servicoTipoOS.getServicosAssociados() != null && !servicoTipoOS.getServicosAssociados().isEmpty()){
					osNaBase.setServicoTipo(servicoTipoOS);
					this.gerarServicosAssociadosOrdemServico(osNaBase, helper.getUsuarioLogado(),
									EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO, origemEncerramento, autorizacoesServicos);
				}
			}

			// [SB0010] - Verificar Atualização Dados Encerramento no Histórico de Aferição do
			// Hidrômetro
			this.verificarAtualizacaoDadosEncerramentoHistoricoAfericaoHidrometro(helper, osNaBase);

			// ControladorOrdemServicoSEJB.showMem(this.getClass().getSimpleName(),
			// " void encerrarOSComExecucaoComReferencia(OSEncerramentoHelper helper, Map<Integer, ServicoAssociadoAutorizacaoHelper> autorizacoesServicos, OrigemEncerramentoOrdemServico origemEncerramento",
			// "fim");

			return idOrdemServicoDiagnostico;
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}catch(ControladorException ex){
			sessionContext.setRollbackOnly();
			throw ex;
		}
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * [SB0004] - Verificar/Excluir/Atualizar Programação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 18/09/2006
	 * @throws ControladorException
	 */
	public void verificarExcluirAtualizarProgramacaoOS(Integer numeroOS, Date dataExecucao) throws ControladorException{

		try{
			Collection colecaoParmsProgramacaoOS = repositorioOrdemServico.pesquisarRoteiro(numeroOS, dataExecucao);
			if(colecaoParmsProgramacaoOS != null && !colecaoParmsProgramacaoOS.isEmpty()){
				Iterator iteParmsProgramacaoOs = colecaoParmsProgramacaoOS.iterator();
				while(iteParmsProgramacaoOs.hasNext()){
					Object[] parmsProgramacaoOs = (Object[]) iteParmsProgramacaoOs.next();
					if(parmsProgramacaoOs != null && !parmsProgramacaoOs.equals("")){
						Integer idOsProgramacao = null;
						Integer idRoteiro = null;
						// id da OSProgramacao
						if(parmsProgramacaoOs[0] != null){
							idOsProgramacao = (Integer) parmsProgramacaoOs[0];
						}
						// id do roteiro
						if(parmsProgramacaoOs[1] != null){
							idRoteiro = (Integer) parmsProgramacaoOs[1];
						}

						if(idOsProgramacao != null && idRoteiro != null){

							OrdemServicoProgramacao ordemServicoProgramacao = new OrdemServicoProgramacao();
							ordemServicoProgramacao.setId(idOsProgramacao);
							ordemServicoProgramacao.setUltimaAlteracao(new Date());
							// remove a ordem de serviço programação
							getControladorUtil().remover(ordemServicoProgramacao);

							Integer idRoteiroBase = repositorioOrdemServico.verificarExistenciaProgramacaoRoteiroParaOSProgramacao(
											idOsProgramacao, idRoteiro);
							if(idRoteiroBase == null || idRoteiroBase.equals("")){
								ProgramacaoRoteiro programacaoRoteiro = new ProgramacaoRoteiro();
								programacaoRoteiro.setId(idRoteiro);
								programacaoRoteiro.setDataRoteiro(new Date());
								programacaoRoteiro.setUltimaAlteracao(new Date());
								// remove a programação roteiro
								getControladorUtil().remover(programacaoRoteiro);
							}
						}
					}
				}
				OrdemServicoProgramacao ordemServicoProgramacao = repositorioOrdemServico.pesquisarOSProgramacaoAtivaComDataEncerramento(
								numeroOS, dataExecucao);
				if(ordemServicoProgramacao != null && !ordemServicoProgramacao.equals("")){
					ordemServicoProgramacao.setIndicadorAtivo(OrdemServicoProgramacao.INDICADOR_ATIVO_NAO);
					ordemServicoProgramacao.setUltimaAlteracao(new Date());
					ordemServicoProgramacao.setSituacaoFechamento(OrdemServicoProgramacao.SITUACAO_FECHAMENTO);
					getControladorUtil().atualizar(ordemServicoProgramacao);
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 27/09/2006
	 * @throws ControladorException
	 */
	public boolean tramitarOuEncerrarRADaOSEncerrada(Integer numeroOS, Usuario usuarioLogado, String idMotivoEncerramento, String idRA,
					String dataRoteiro) throws ControladorException{

		boolean encerrarRA = false;
		// caso a OS tenha sido encerrada por um usuário associado a uma unidade
		// terceirizada
		Integer idOrdemServico = null;
		try{
			idOrdemServico = repositorioOrdemServico.verificarExistenciaUnidadeTerceirizada(usuarioLogado.getId());
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		if((idOrdemServico != null && !idOrdemServico.equals("")) && (idRA != null && !idRA.equals(""))){
			Tramite tramite = new Tramite();
			// id RA
			RegistroAtendimento registroAtendimento = new RegistroAtendimento();
			registroAtendimento.setId(Util.converterStringParaInteger(idRA));
			tramite.setRegistroAtendimento(registroAtendimento);

			if(usuarioLogado.getUnidadeOrganizacional() != null && !usuarioLogado.getUnidadeOrganizacional().equals("")){
				// Unidade Origem
				UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
				unidadeOrganizacional.setId(usuarioLogado.getUnidadeOrganizacional().getId());
				tramite.setUnidadeOrganizacionalOrigem(unidadeOrganizacional);
				// Unidade destino
				tramite.setUnidadeOrganizacionalDestino(unidadeOrganizacional);
			}
			// id Usuario registro
			tramite.setUsuarioRegistro(usuarioLogado);
			// usuário registro
			tramite.setUsuarioResponsavel(usuarioLogado);
			tramite.setParecerTramite("TRAMITE GERADO PELO SISTEMA NO ENCERRAMENTO DA ORDEM DE SERVIÇO EXECUTADA POR UMA UNIDADE TERCEIRIZADA");
			tramite.setDataTramite(new Date());
			tramite.setUltimaAlteracao(new Date());
			getControladorRegistroAtendimento().tramitar(tramite, new Date());
			// Caso contrário
			// caso a OS esteja associada a um registro de atendimento
		}else{
			// caso exista RA para a OS
			if(idRA != null && !idRA.equals("")){
				Collection colecaoRA = null;
				try{
					colecaoRA = repositorioOrdemServico.verificarOSparaRA(numeroOS, Util.converterStringParaInteger(idRA));
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
				// caso exista OS para a RA
				if(colecaoRA == null || colecaoRA.isEmpty()){
					encerrarRA = true;
				}
			}
		}
		return encerrarRA;

	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection<Atividade>
	 * @throws ErroRepositorioException
	 */
	public Collection<Atividade> obterAtividadesOrdemServico(Integer numeroOS) throws ControladorException{

		Collection<Atividade> retorno = null;

		try{
			retorno = repositorioOrdemServico.obterAtividadesOrdemServico(numeroOS);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// [FS0002 - Verificar Existência de Dados]
		if(retorno == null || retorno.isEmpty()){

			FiltroAtividade filtroAtividade = new FiltroAtividade();
			filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORATIVIDADEUNICA,
							Atividade.INDICADOR_ATIVIDADE_UNICA_SIM));
			retorno = this.getControladorUtil().pesquisar(filtroAtividade, Atividade.class.getName());

			if(retorno == null || retorno.isEmpty() || retorno.size() != 1){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){
				}
				throw new ControladorException("erro.sistema", null);
			}
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 18/09/2006
	 * @param numeroOS
	 * @return Collection<Equipe>
	 * @throws ErroRepositorioException
	 */
	public Collection<Equipe> obterEquipesProgramadas(Integer numeroOS) throws ControladorException{

		Collection colecaoEquipe = null;
		Collection<Equipe> retorno = new ArrayList();

		try{

			colecaoEquipe = repositorioOrdemServico.obterEquipesProgramadas(numeroOS);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// [FS0002 - Verificar Existência de Dados]
		/*
		 * if (colecaoEquipe == null || colecaoEquipe.isEmpty()) {
		 * sessionContext.setRollbackOnly(); throw new ControladorException(
		 * "atencao.entidade_sem_dados_para_selecao", null, "EQUIPE"); }
		 */

		if(colecaoEquipe != null && !colecaoEquipe.isEmpty()){

			Iterator equipeIterator = colecaoEquipe.iterator();
			Object[] arrayEquipe = null;
			Equipe equipe = null;

			while(equipeIterator.hasNext()){

				equipe = new Equipe();
				arrayEquipe = (Object[]) equipeIterator.next();

				equipe.setId((Integer) arrayEquipe[0]);
				equipe.setNome((String) arrayEquipe[1]);
				equipe.setUltimaAlteracao((Date) arrayEquipe[2]);

				retorno.add(equipe);
			}
		}

		return retorno;
	}

	/**
	 * [UC0467] - Atualizar Ordem de Serviço
	 * 
	 * @author lms
	 * @date 18/09/2006
	 */
	public void atualizarOrdemServico(OrdemServico ordemServico, Usuario usuario) throws ControladorException{

		Calendar calendar = Calendar.getInstance();

		boolean tramitar = false;

		validarOrdemServico(ordemServico);

		OrdemServico ordemServicoBanco = this.pesquisarOrdemServico(ordemServico.getId());

		if(ordemServicoBanco != null && ordemServico.getServicoTipo() != null && ordemServicoBanco.getServicoTipo() != null){

			if(!ordemServico.getServicoTipo().getId().equals(ordemServicoBanco.getServicoTipo().getId())){
				tramitar = true;
			}
		}

		ordemServico.setUltimaAlteracao(calendar.getTime());

		// ------------ REGISTRAR TRANSAÇÃO ------------

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_ORDEM_SERVICO_ATUALIZAR);

		RegistradorOperacao registradorOperacao = new RegistradorOperacao(operacao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		ordemServico.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(ordemServico);

		// ------------ REGISTRAR TRANSAÇÃO ------------

		getControladorUtil().atualizar(ordemServico);

		if(tramitar){
			// ID da Unidade Tramite
			Integer idUnidadeTramite = null;

			Short permiteTramiteIndependente = Short.parseShort((String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE
							.executar(ExecutorParametrosAtendimentoPublico.getInstancia()));

			if(permiteTramiteIndependente != null && permiteTramiteIndependente.equals(ConstantesSistema.SIM.intValue())){
				UnidadeOrganizacional unidadeOrigem = null;

				// [UC3040 - Obter Unidade Atual da OS].
				unidadeOrigem = this.obterUnidadeAtualOrdemServico(ordemServico.getId());

				// [UC3036 - Obter Unidade Tramite Ordem de Serviço].
				idUnidadeTramite = this.pesquisarUnidadeTramiteOS(ordemServico.getServicoTipo().getId(), null, null, null,
								unidadeOrigem.getId());

				if(idUnidadeTramite == null){
					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.tramitar_os_unidade_nao_existente");
				}

				// 2.3. Caso a unidade destino, retornada pelo [UC3036], seja diferente da unidade
				// atual da OS, retornada pelo [UC3040], gerar trâmite de O.S. [SB0002 - Gerar
				// Trâmite de O.S.].
				if(!idUnidadeTramite.equals(unidadeOrigem.getId())){

					// [SB0002 - Gerar Trâmite de O.S.]
					Tramite tramite = this.getTramite(ordemServico, usuario, idUnidadeTramite,
									"Tramite gerado pelo sistema na alteração do tipo de serviço da ordem de serviçoo");

					this.tramitarOS(tramite, ordemServico.getUltimaAlteracao());
				}

			}

		}

	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarOSAssociadaDocumentoCobranca(Integer numeroOS) throws ControladorException{

		Integer quantidadeOS = null;
		boolean retorno = false;

		try{

			quantidadeOS = repositorioOrdemServico.verificarOSAssociadaDocumentoCobranca(numeroOS);

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}

		if(quantidadeOS != null && quantidadeOS > 0){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return boolean
	 * @throws ControladorException
	 */
	public boolean verificarOSAssociadaRA(Integer numeroOS) throws ControladorException{

		Integer quantidadeOS = null;
		boolean retorno = false;

		try{

			quantidadeOS = repositorioOrdemServico.verificarOSAssociadaRA(numeroOS);

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}

		if(quantidadeOS != null && quantidadeOS > 0){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @author Saulo Lima
	 * @date 04/06/2009
	 *       Correção + alteração para retornar a UnidadeOrganizacional
	 * @param numeroOS
	 * @return UnidadeOganizacional
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeDestinoUltimoTramite(Integer numeroOS) throws ControladorException{

		UnidadeOrganizacional unidadeDestino = null;

		try{

			unidadeDestino = repositorioOrdemServico.obterUnidadeDestinoUltimoTramite(numeroOS);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return unidadeDestino;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 19/09/2006
	 * @param numeroOS
	 * @return Collection<Equipe>
	 * @throws ControladorException
	 */
	public Collection<Equipe> obterEquipesPorUnidade(Integer idUnidade) throws ControladorException{

		Collection colecaoEquipe = null;
		Collection<Equipe> retorno = new ArrayList();

		try{

			colecaoEquipe = repositorioOrdemServico.obterEquipesPorUnidade(idUnidade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoEquipe != null && !colecaoEquipe.isEmpty()){

			Iterator equipeIterator = colecaoEquipe.iterator();
			Object[] arrayEquipe = null;
			Equipe equipe = null;

			while(equipeIterator.hasNext()){

				equipe = new Equipe();
				arrayEquipe = (Object[]) equipeIterator.next();

				equipe.setId((Integer) arrayEquipe[0]);
				equipe.setNome((String) arrayEquipe[1]);

				retorno.add(equipe);
			}
		}

		return retorno;
	}

	/**
	 * [UC0362] Efetuar Instalação de Hidrômetro
	 * 
	 * @author Leonardo Regis
	 * @date 19/09/2006
	 * @param dadosOS
	 * @throws ControladorException
	 */
	public void atualizarOSParaEfetivacaoInstalacaoHidrometro(DadosAtualizacaoOSParaInstalacaoHidrometroHelper dadosOS)
					throws ControladorException{

		try{
			repositorioOrdemServico.atualizarOSParaEfetivacaoInstalacaoHidrometro(dadosOS);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 20/09/2006
	 * @param dataExecucao
	 *            ,
	 *            horaInicio, horaFim, idEquipe
	 * @return void
	 * @throws ControladorException
	 */
	public void verificaDadosAdicionarPeriodoEquipe(String dataExecucao, String horaInicio, String horaFim, Integer idEquipe,
					String dataEncerramentoOS, Integer numeroOS) throws ControladorException{

		// [FS0005] - Validar data de execução
		Collection colecaoOrdemnServico = null;

		if(dataEncerramentoOS != null && !dataEncerramentoOS.equalsIgnoreCase("")){

			try{
				colecaoOrdemnServico = repositorioOrdemServico.obterDatasGeracaoEncerramentoOS(numeroOS);

			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}

			if(colecaoOrdemnServico != null && !colecaoOrdemnServico.isEmpty()){

				Iterator osIterator = colecaoOrdemnServico.iterator();

				Object[] arrayOS = (Object[]) osIterator.next();

				Date datageracao = Util.converteStringParaDate(Util.formatarData((Date) arrayOS[0]));
				Date dataEncerramento = Util.converteStringParaDate(dataEncerramentoOS);
				Date dataExecucaoObjeto = Util.converteStringParaDate(dataExecucao);

				// if(Util.formatarDataFinal(dataExecucaoObjeto).compareTo(Util.formatarDataFinal(datageracao))
				// == -1
				// ||
				// Util.formatarDataFinal(dataExecucaoObjeto).compareTo(Util.formatarDataFinal(dataEncerramento))
				// == 1){
				//
				// throw new ControladorException("atencao.data_execucao_invalida");
				// }
			}
		}

		// [FS0006] - Validar hora início e fim de execução
		if(Util.compararHoraMinuto(horaInicio, horaFim, ">")){
			throw new ControladorException("atencao.hora_final_anterior_hora_inicio");
		}

		// [FS0007] - Verificar existência da equipe
		FiltroEquipe filtroEquipe = new FiltroEquipe();

		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, idEquipe));

		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoEquipe = this.getControladorUtil().pesquisar(filtroEquipe, Equipe.class.getName());

		if(colecaoEquipe == null || colecaoEquipe.isEmpty()){
			throw new ControladorException("atencao.pesquisa_inexistente", null, "Equipe");
		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 11/09/2006
	 * @param dataRoteiro
	 *            ,idUnidadeOrganizacional
	 * @return collection<OrdemServicoProgramacao>
	 * @throws ControladorException
	 */
	public Collection<OrdemServicoProgramacao> recuperaOSProgramacaoPorDataRoteiroUnidade(Date dataRoteiro, Integer idUnidadeOrganizacional)
					throws ControladorException{

		try{
			return repositorioOrdemServico.recuperaOSProgramacaoPorDataRoteiroUnidade(dataRoteiro, idUnidadeOrganizacional);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço [FS0008] -
	 * Verificar possibilidade da inclusão da ordem de serviço
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void validarInclusaoOsNaProgramacao(OrdemServico ordemServico, Integer unidadeLotacao) throws ControladorException{

		if(ordemServico != null){
			try{
				// Caso 1

				if(ordemServico.getRegistroAtendimento() != null){

					UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualOrdemServico(ordemServico.getId());

					if(unidadeAtual.getId().intValue() != unidadeLotacao.intValue()){

						String[] parametros = new String[2];

						parametros[0] = ordemServico.getId().toString();
						parametros[1] = unidadeLotacao.toString();

						throw new ControladorException("atencao.unidade_lotacao_nao_encontra_ordem_servico", null, parametros);
					}

				}else{
					// UnidadeOrganizacional unidadeAtual = this.repositorioOrdemServico
					// .pesquisarUnidadeOrganizacionalPorUnidade(ordemServico
					// .getRegistroAtendimento().getId(),
					// unidadeLotacao);

					UnidadeOrganizacional unidadeAtual = this.repositorioOrdemServico.pesquisarUnidadeOrganizacionalPorUnidade(
									ordemServico.getId(), unidadeLotacao);

					// if (unidadeAtual != null
					// && unidadeAtual.getId().intValue() == unidadeLotacao
					// .intValue()) {
					if(unidadeAtual == null || unidadeAtual.getId().intValue() != unidadeLotacao.intValue()){

						String[] parametros = new String[2];

						parametros[0] = ordemServico.getId().toString();
						parametros[1] = unidadeLotacao.toString();

						throw new ControladorException("atencao.unidade_lotacao_nao_encontra_ordem_servico", null, parametros);
					}

				}

				// Caso 2
				if(ordemServico.getSituacao() == OrdemServico.SITUACAO_ENCERRADO.shortValue()){
					throw new ControladorException("atencao.ordem_servico_encerrada_para_programacao");
				}

				// Caso 3
				OrdemServicoProgramacao osProgramacao = repositorioOrdemServico.pesquisarOSProgramacaoAtivaPorOS(ordemServico.getId());

				if(osProgramacao != null){

					String[] parametros = new String[2];

					parametros[0] = osProgramacao.getEquipe().getNome();
					parametros[1] = Util.formatarData(osProgramacao.getProgramacaoRoteiro().getDataRoteiro());

					throw new ControladorException("atencao.ordem_servico_ja_programada", null, parametros);
				}

			}catch(ErroRepositorioException e){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("erro.sistema", e);
			}
		}else{
			throw new ControladorException("atencao.ordem_servico.inexistente");
		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço [FS0012] -
	 * Reordena Sequencial de Programação - Inclusão de Ordem de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void reordenaSequencialProgramacaoInclusaoOrdemServico(Date dataRoteiro, short sequencialAlterado) throws ControladorException{

		try{

			Collection<OrdemServicoProgramacao> colecaoOSProgramacao = this.repositorioOrdemServico
							.recuperaOSProgramacaoPorDataRoteiro(dataRoteiro);

			Iterator itera = colecaoOSProgramacao.iterator();

			while(itera.hasNext()){
				OrdemServicoProgramacao osProgramacao = (OrdemServicoProgramacao) itera.next();

				short sequencial = osProgramacao.getNnSequencialProgramacao();

				if(sequencial == sequencialAlterado || sequencial > sequencialAlterado){

					osProgramacao.setNnSequencialProgramacao(sequencial++);
					osProgramacao.setUltimaAlteracao(new Date());

					this.verificarOrdemServicoProgramacaoControleConcorrencia(osProgramacao);
					this.getControladorUtil().atualizar(osProgramacao);
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço [FS0012] -
	 * Reordena Sequencial de Programação por Equipe e por dia - Inclusão de Ordem de Serviço
	 * 
	 * @author isilva
	 * @param dataRoteiro
	 * @param equipe
	 * @param sequencialAlterado
	 * @throws ControladorException
	 */
	public void reordenaSequencialProgramacaoInclusaoOrdemServico(Date dataRoteiro, Equipe equipe, short sequencialAlterado)
					throws ControladorException{

		try{

			// this.reordenaSequencialOrdemServicoProgramacao(dataRoteiro, equipe.getId());
			// this.maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(equipe.getId(), dataRoteiro);

			Collection<OrdemServicoProgramacao> colecaoOSProgramacao = this.repositorioOrdemServico.recuperaOSProgramacaoPorDataRoteiro(
							dataRoteiro, equipe);

			Iterator itera = colecaoOSProgramacao.iterator();

			while(itera.hasNext()){
				OrdemServicoProgramacao osProgramacao = (OrdemServicoProgramacao) itera.next();

				short sequencial = osProgramacao.getNnSequencialProgramacao();

				if(sequencial == sequencialAlterado || sequencial > sequencialAlterado){

					osProgramacao.setNnSequencialProgramacao(sequencial++);
					osProgramacao.setUltimaAlteracao(new Date());

					this.verificarOrdemServicoProgramacaoControleConcorrencia(osProgramacao);
					this.getControladorUtil().atualizar(osProgramacao);
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Acompanhar Roteiro de Programação de Ordens de Serviço
	 * Reordena Sequencial de Programação
	 * 
	 * @author isilva
	 * @date 12/11/2010
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void reordenaSequencialOrdemServicoProgramacao(Date dataRoteiro, Integer idEquipe) throws ControladorException{

		Collection<OrdemServicoProgramacao> colecaoOSProgramacao;

		try{

			// Ordenado crescentemente pelo sequencial
			colecaoOSProgramacao = this.repositorioOrdemServico.pesquisarOSProgramacaoComDataRoteiroIdEquipe(dataRoteiro, idEquipe, true);
			if(colecaoOSProgramacao != null && !colecaoOSProgramacao.isEmpty()){

				Collection<OrdemServicoProgramacao> colecaoOSProgramacaoAlteradas = new ArrayList<OrdemServicoProgramacao>();

				colecaoOSProgramacaoAlteradas = this.reordenaSequencialOrdemServicoProgramacao(colecaoOSProgramacao, false);

				// Atualiza as Ordens de Serviço Programação com as squencias ordenadas
				if(colecaoOSProgramacaoAlteradas != null && !colecaoOSProgramacaoAlteradas.isEmpty()){
					for(OrdemServicoProgramacao ordemServicoProgramacao : colecaoOSProgramacaoAlteradas){
						ordemServicoProgramacao.setUltimaAlteracao(new Date());
						this.verificarOrdemServicoProgramacaoControleConcorrencia(ordemServicoProgramacao);
						this.getControladorUtil().atualizar(ordemServicoProgramacao);
					}
				}
			}
		}catch(ErroRepositorioException e){
			e.printStackTrace();
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço [FS0012]
	 * Reordena Sequencial de Programação - Nova Ordem para Ordem de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @author isilva
	 * @date 16/11/2010
	 * @param dataRoteiro
	 * @param sequencialInformado
	 * @param sequencialAtual
	 * @param idEquipe
	 * @param verificaVazio
	 * @throws ControladorException
	 */
	public void reordenaSequencialProgramacaoNovaOrdem(Date dataRoteiro, short sequencialInformado, short sequencialAtual,
					Integer idEquipe, boolean verificaVazio) throws ControladorException{

		try{

			// **************** Alterações ISilva *****************
			Collection<OrdemServicoProgramacao> colecaoOSProgramacaoAlteradas = new ArrayList<OrdemServicoProgramacao>();

			// Ordenado crescentemente pelo sequencial
			Collection<OrdemServicoProgramacao> colecaoOSProgramacao = this.repositorioOrdemServico
							.pesquisarOSProgramacaoComDataRoteiroIdEquipe(dataRoteiro, idEquipe, true);

			if(colecaoOSProgramacao != null && !colecaoOSProgramacao.isEmpty()){

				Integer quantidadeOrdemServicoProgramacaoNaBase = colecaoOSProgramacao.size();

				if(quantidadeOrdemServicoProgramacaoNaBase >= Integer.valueOf(sequencialInformado)
								&& Integer.valueOf(sequencialInformado) > 0){

					Iterator itera = colecaoOSProgramacao.iterator();

					Collection<OrdemServicoProgramacao> colecaoAux = new ArrayList<OrdemServicoProgramacao>();
					OrdemServicoProgramacao ospAux = null;

					while(itera.hasNext()){
						OrdemServicoProgramacao osProgramacao = (OrdemServicoProgramacao) itera.next();

						short sequencial = osProgramacao.getNnSequencialProgramacao();

						if(sequencialAtual == sequencial){
							ospAux = osProgramacao;
						}else{
							colecaoAux.add(osProgramacao);
						}
					}

					Iterator iteraAux = colecaoAux.iterator();

					Collection<OrdemServicoProgramacao> colecaoAtualizar = new ArrayList<OrdemServicoProgramacao>();

					boolean addNovo = false;

					int posicao = 0;
					while(iteraAux.hasNext()){

						OrdemServicoProgramacao osP = (OrdemServicoProgramacao) iteraAux.next();

						boolean addNovamente = false;

						if(posicao == Integer.valueOf(sequencialInformado - 1)){
							ospAux.setNnSequencialProgramacao(sequencialInformado);
							colecaoAtualizar.add(ospAux);
							addNovo = true;
							addNovamente = true;
						}else{
							colecaoAtualizar.add(osP);
						}

						if(addNovamente){
							colecaoAtualizar.add(osP);
						}

						posicao++;
					}

					if(!addNovo){
						ospAux.setNnSequencialProgramacao(sequencialInformado);
						colecaoAtualizar.add(ospAux);
					}

					colecaoOSProgramacaoAlteradas = this.reordenaSequencialOrdemServicoProgramacao(colecaoAtualizar, false);

				}else{
					if(verificaVazio){
						throw new ControladorException("atencao.atualizacao.timestamp");
					}
				}
			}else{
				if(verificaVazio){
					throw new ControladorException("atencao.atualizacao.timestamp");
				}
			}

			// Atualiza as Ordens de Serviço Programação com as squencias ordenadas
			if(colecaoOSProgramacaoAlteradas != null && !colecaoOSProgramacaoAlteradas.isEmpty()){
				for(OrdemServicoProgramacao ordemServicoProgramacao : colecaoOSProgramacaoAlteradas){
					ordemServicoProgramacao.setUltimaAlteracao(new Date());
					this.verificarOrdemServicoProgramacaoControleConcorrencia(ordemServicoProgramacao);
					this.getControladorUtil().atualizar(ordemServicoProgramacao);
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	private Collection<OrdemServicoProgramacao> reordenaSequencialOrdemServicoProgramacao(
					Collection<OrdemServicoProgramacao> colecaoOrdemServicoProgramacaos, boolean ordernarComSort){

		Collection<OrdemServicoProgramacao> colecaoOrdemServicoProgramacaosRetorno = colecaoOrdemServicoProgramacaos;

		if(ordernarComSort){
			// Ordena a coleção pelo sequencial
			Collections.sort((List) colecaoOrdemServicoProgramacaosRetorno, new Comparator() {

				public int compare(Object a, Object b){

					Integer sequencial1 = new Integer(((OrdemServicoProgramacao) a).getNnSequencialProgramacao());
					Integer sequencial2 = new Integer(((OrdemServicoProgramacao) b).getNnSequencialProgramacao());
					return sequencial1.compareTo(sequencial2);
				}
			});
		}

		Iterator itera = colecaoOrdemServicoProgramacaosRetorno.iterator();

		Integer proximoSequencial = 1;
		while(itera.hasNext()){
			OrdemServicoProgramacao osProgramacao = (OrdemServicoProgramacao) itera.next();
			osProgramacao.setNnSequencialProgramacao(proximoSequencial.shortValue());
			proximoSequencial++;
		}

		return colecaoOrdemServicoProgramacaosRetorno;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço [FS0012]
	 * Reordena Sequencial de Programação
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @param ordemServico
	 * @throws ControladorException
	 */
	public void reordenarSequencialProgramacao(Date dataRoteiro, Integer idEquipe) throws ControladorException{

		try{

			Collection<OrdemServicoProgramacao> colecaoOSProgramacao = this.repositorioOrdemServico
							.pesquisarOSProgramacaoComDataRoteiroIdEquipeOrdenada(dataRoteiro, idEquipe);

			if(colecaoOSProgramacao != null && !colecaoOSProgramacao.isEmpty()){

				short sequencialCorreto = 1;

				Iterator colecaoOSProgramacaoIterator = colecaoOSProgramacao.iterator();

				while(colecaoOSProgramacaoIterator.hasNext()){

					OrdemServicoProgramacao ordemServicoProgramacao = (OrdemServicoProgramacao) colecaoOSProgramacaoIterator.next();

					if(ordemServicoProgramacao.getNnSequencialProgramacao() != sequencialCorreto){
						ordemServicoProgramacao.setNnSequencialProgramacao(sequencialCorreto);
						getControladorUtil().atualizar(ordemServicoProgramacao);
					}

					sequencialCorreto++;
				}

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0455] Exibir Calendário para Elaboração ou Acompanhamento de Roteiro
	 * 
	 * @author Rômulo Aurélio
	 * @date 21/09/2006
	 * @param mesAnoReferencia
	 * @return Collection<ProgramacaoRoteiro>
	 * @throws ControladorException
	 */

	public Collection consultarProgramacaoRoteiro(String mesAnoReferencia, Integer unidadeOrganizacional) throws ControladorException{

		String ano = null;

		String mes = null;

		String anoMesReferencia = null;

		if(mesAnoReferencia == null || mesAnoReferencia.equals("")){

			Date dataCorrente = new Date();
			String dataCorrenteTexto = Util.formatarData(dataCorrente);
			ano = dataCorrenteTexto.substring(6, 10);
			mes = dataCorrenteTexto.substring(3, 5);
			String anoMesCorrente = ano + mes;
			anoMesReferencia = anoMesCorrente;

		}else{

			boolean mesAnoValido = Util.validarMesAno(mesAnoReferencia);

			if(mesAnoValido == false){
				throw new ControladorException("atencao.ano_mes_referencia.invalida");
			}

			mes = mesAnoReferencia.substring(0, 2);
			ano = mesAnoReferencia.substring(3, 7);

			anoMesReferencia = ano + mes;
		}

		Integer anoMesReferenciaSomada = Util.somaUmMesAnoMesReferencia(Integer.valueOf(anoMesReferencia));

		String dia = "01";

		String dataInicial = String.valueOf(anoMesReferencia + dia);

		dataInicial = Util.formatarData(dataInicial);

		Date dataInicialFormatada = Util.converteStringParaDate(dataInicial);

		// Colocado por Raphael Rossiter em 14/02/2007
		dataInicialFormatada = Util.formatarDataInicial(dataInicialFormatada);

		String dataFinal = String.valueOf(anoMesReferenciaSomada + dia);

		dataFinal = Util.formatarData(dataFinal);

		Date dataFinalFormatada = Util.converteStringParaDate(dataFinal);

		// Colocado por Raphael Rossiter em 14/02/2007
		dataFinalFormatada = Util.formatarDataFinal(dataFinalFormatada);

		FiltroProgramacaoRoteiro filtroProgramacaoRoteiro = new FiltroProgramacaoRoteiro();

		filtroProgramacaoRoteiro.adicionarParametro(new Intervalo(FiltroProgramacaoRoteiro.DATA_ROTEIRO, dataInicialFormatada,
						dataFinalFormatada));
		filtroProgramacaoRoteiro.setCampoOrderBy(FiltroProgramacaoRoteiro.DATA_ROTEIRO);

		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.UNIDADE_ORGANIZACIONAL_ID,
						unidadeOrganizacional));

		Collection colecaoProgramacaoRoteiro = getControladorUtil().pesquisar(filtroProgramacaoRoteiro, ProgramacaoRoteiro.class.getName());

		return colecaoProgramacaoRoteiro;

	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */
	public void incluirOrdemServicoProgramacao(OrdemServicoProgramacao ordemServicoProgramacao, Usuario usuarioLogado)
					throws ControladorException{

		try{
			// short sequencial = ordemServicoProgramacao
			// .getNnSequencialProgramacao();

			// Verificar no Banco
			// if (sequencial == ConstantesSistema.NUMERO_NAO_INFORMADO) {

			// Collection<OrdemServicoProgramacao> colecao = this.repositorioOrdemServico
			// .pesquisarOSProgramacaoComDataRoteiroIdEquipe(
			// ordemServicoProgramacao.getProgramacaoRoteiro()
			// .getDataRoteiro(),
			// ordemServicoProgramacao.getEquipe().getId(), false);
			//
			// sequencial = this.retornaUltimoSequencial(colecao);
			// sequencial++;

			/*
			 * Identificar a maior sequência das Programações das Ordens de Seviço naquela data,
			 * para aquela Equipe
			 */
			this.reordenaSequencialOrdemServicoProgramacao(ordemServicoProgramacao.getProgramacaoRoteiro().getDataRoteiro(),
							ordemServicoProgramacao.getEquipe().getId());

			Integer maiorSquencial = this.repositorioOrdemServico.maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(
							ordemServicoProgramacao.getEquipe().getId(), ordemServicoProgramacao.getProgramacaoRoteiro().getDataRoteiro());

			if(maiorSquencial == null){
				maiorSquencial = 1;
			}else{
				maiorSquencial++;
			}

			ordemServicoProgramacao.setNnSequencialProgramacao(maiorSquencial.shortValue());
			// }

			// [UC0107] - Registrar Transação
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(
							Operacao.OPERACAO_INSERIR_ORDEM_SERVICO_PROGRAMACAO_ACOMPANHAMENTO_ROTEIRO, new UsuarioAcaoUsuarioHelper(
											usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_INSERIR_ORDEM_SERVICO_PROGRAMACAO_ACOMPANHAMENTO_ROTEIRO);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			// [UC0107] - Registrar Transação
			ordemServicoProgramacao.setOperacaoEfetuada(operacaoEfetuada);
			ordemServicoProgramacao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(ordemServicoProgramacao);

			// Colocado por Raphael Rossiter em 08/02/2007 (Mudança no UC)
			OrdemServico ordemServico = this.pesquisarOrdemServico(ordemServicoProgramacao.getOrdemServico().getId());
			ordemServico.setIndicadorProgramada(OrdemServico.PROGRAMADA);

			this.atualizarOrdemServico(ordemServico, usuarioLogado);

			this.getControladorUtil().inserir(ordemServicoProgramacao);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Acompanhar Roteiro de Programacao de Ordens de Servico
	 * com sequencial informado
	 * 
	 * @author isilva
	 * @date 16/11/2011
	 * @param ordemServicoProgramacaoParaInserir
	 * @param usuarioLogado
	 * @param verificaVazio
	 * @throws ControladorException
	 */
	public void incluirOrdemServicoProgramacaoComSequencialInformado(OrdemServicoProgramacao ordemServicoProgramacaoParaInserir,
					Usuario usuarioLogado, boolean verificaVazio) throws ControladorException{

		Short sequencialInformado = ordemServicoProgramacaoParaInserir.getNnSequencialProgramacao();

		try{

			// **************** Alterações ISilva *****************
			Collection<OrdemServicoProgramacao> colecaoOSProgramacaoAlteradas = new ArrayList<OrdemServicoProgramacao>();

			// Ordenado crescentemente pelo sequencial
			Collection<OrdemServicoProgramacao> colecaoOSProgramacao = this.repositorioOrdemServico
							.pesquisarOSProgramacaoComDataRoteiroIdEquipe(ordemServicoProgramacaoParaInserir.getProgramacaoRoteiro()
											.getDataRoteiro(), ordemServicoProgramacaoParaInserir.getEquipe().getId(), true);

			if(colecaoOSProgramacao != null && !colecaoOSProgramacao.isEmpty()){

				Integer quantidadeOrdemServicoProgramacaoNaBase = colecaoOSProgramacao.size();

				if(quantidadeOrdemServicoProgramacaoNaBase >= Integer.valueOf(sequencialInformado - 1)
								&& Integer.valueOf(sequencialInformado - 1) >= 0){

					Iterator itera = colecaoOSProgramacao.iterator();

					Collection<OrdemServicoProgramacao> colecaoAtualizar = new ArrayList<OrdemServicoProgramacao>();

					boolean addNovo = false;

					int posicao = 0;
					while(itera.hasNext()){

						OrdemServicoProgramacao osP = (OrdemServicoProgramacao) itera.next();

						boolean addNovamente = false;

						if(posicao == Integer.valueOf(sequencialInformado - 1)){
							ordemServicoProgramacaoParaInserir.setNnSequencialProgramacao(sequencialInformado);
							colecaoAtualizar.add(ordemServicoProgramacaoParaInserir);
							addNovo = true;
							addNovamente = true;
						}else{
							colecaoAtualizar.add(osP);
						}

						if(addNovamente){
							colecaoAtualizar.add(osP);
						}

						posicao++;
					}

					if(!addNovo){
						ordemServicoProgramacaoParaInserir.setNnSequencialProgramacao(sequencialInformado);
						colecaoAtualizar.add(ordemServicoProgramacaoParaInserir);
					}

					colecaoOSProgramacaoAlteradas = this.reordenaSequencialOrdemServicoProgramacao(colecaoAtualizar, false);

				}else{
					// if (verificaVazio) {
					throw new ControladorException("atencao.atualizacao.timestamp");
					// }
				}
			}else{
				if(verificaVazio){
					throw new ControladorException("atencao.atualizacao.timestamp");
				}else{
					// Caso não exista para aquela equipe e naquele dia,
					// Inserir uma OrdemServicoProgramacao com sequencil 1

					// [UC0107] - Registrar Transação
					RegistradorOperacao registradorOperacao = new RegistradorOperacao(
									Operacao.OPERACAO_INSERIR_ORDEM_SERVICO_PROGRAMACAO_ACOMPANHAMENTO_ROTEIRO,
									new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

					Operacao operacao = new Operacao();
					operacao.setId(Operacao.OPERACAO_INSERIR_ORDEM_SERVICO_PROGRAMACAO_ACOMPANHAMENTO_ROTEIRO);

					OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
					operacaoEfetuada.setOperacao(operacao);

					// [UC0107] - Registrar Transação
					ordemServicoProgramacaoParaInserir.setOperacaoEfetuada(operacaoEfetuada);
					ordemServicoProgramacaoParaInserir.setNnSequencialProgramacao(Short.parseShort("1"));
					ordemServicoProgramacaoParaInserir.setUltimaAlteracao(new Date());
					ordemServicoProgramacaoParaInserir.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

					registradorOperacao.registrarOperacao(ordemServicoProgramacaoParaInserir);

					// Colocado por Raphael Rossiter em 08/02/2007 (Mudança no UC)
					OrdemServico ordemServico = this.pesquisarOrdemServico(ordemServicoProgramacaoParaInserir.getOrdemServico().getId());
					ordemServico.setIndicadorProgramada(OrdemServico.PROGRAMADA);

					this.atualizarOrdemServico(ordemServico, usuarioLogado);

					this.getControladorUtil().inserir(ordemServicoProgramacaoParaInserir);
				}
			}

			// Atualiza as Ordens de Serviço Programação com as squencias ordenadas
			if(colecaoOSProgramacaoAlteradas != null && !colecaoOSProgramacaoAlteradas.isEmpty()){
				for(OrdemServicoProgramacao ordemServicoProgramacao : colecaoOSProgramacaoAlteradas){
					ordemServicoProgramacao.setUltimaAlteracao(new Date());

					// Verifica se é para inserir ou atualizar
					if(ordemServicoProgramacao.getId() != null){
						// Atualizar
						this.verificarOrdemServicoProgramacaoControleConcorrencia(ordemServicoProgramacao);
						this.getControladorUtil().atualizar(ordemServicoProgramacao);
					}else{
						// Inserir

						// [UC0107] - Registrar Transação
						RegistradorOperacao registradorOperacao = new RegistradorOperacao(
										Operacao.OPERACAO_INSERIR_ORDEM_SERVICO_PROGRAMACAO_ACOMPANHAMENTO_ROTEIRO,
										new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

						Operacao operacao = new Operacao();
						operacao.setId(Operacao.OPERACAO_INSERIR_ORDEM_SERVICO_PROGRAMACAO_ACOMPANHAMENTO_ROTEIRO);

						OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
						operacaoEfetuada.setOperacao(operacao);

						// [UC0107] - Registrar Transação
						ordemServicoProgramacao.setOperacaoEfetuada(operacaoEfetuada);
						ordemServicoProgramacao.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

						registradorOperacao.registrarOperacao(ordemServicoProgramacao);

						// Colocado por Raphael Rossiter em 08/02/2007 (Mudança no UC)
						OrdemServico ordemServico = this.pesquisarOrdemServico(ordemServicoProgramacao.getOrdemServico().getId());
						ordemServico.setIndicadorProgramada(OrdemServico.PROGRAMADA);

						this.atualizarOrdemServico(ordemServico, usuarioLogado);

						this.getControladorUtil().inserir(ordemServicoProgramacao);
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */

	private short retornaUltimoSequencial(Collection<OrdemServicoProgramacao> colecao){

		short valorSequencial = 1;
		if(colecao != null){
			Iterator iter = colecao.iterator();
			while(iter.hasNext()){
				OrdemServicoProgramacao ordemServicoProgramacao = (OrdemServicoProgramacao) iter.next();

				if(valorSequencial < ordemServicoProgramacao.getNnSequencialProgramacao()){
					valorSequencial = ordemServicoProgramacao.getNnSequencialProgramacao();
				}
			}
		}
		return valorSequencial;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */
	public void alocaEquipeParaOs(Integer numeroOS, Date dataRoteiro, Integer idEquipe) throws ControladorException{

		try{
			OrdemServicoProgramacao ordemServicoProgramacao = this.repositorioOrdemServico
							.pesquisarOSProgramacaoAtivaComDataRoteiroIdEquipe(numeroOS, dataRoteiro, idEquipe);

			this.verificarOrdemServicoProgramacaoControleConcorrencia(ordemServicoProgramacao);

			short sequencialReferencia = ordemServicoProgramacao.getNnSequencialProgramacao();

			this.getControladorUtil().remover(ordemServicoProgramacao);

			// Collection<OrdemServicoProgramacao> colecaoExistentes = this.repositorioOrdemServico
			// .pesquisarOSProgramacaoComDataRoteiroIdEquipeDiferenteOS(
			// numeroOS, dataRoteiro, idEquipe);

			this.reordenaSequencialOrdemServicoProgramacao(dataRoteiro, idEquipe);

			// if (colecaoExistentes != null && !colecaoExistentes.isEmpty()) {
			//
			// this.reordenaSequencialProgramacaoOSZerado(numeroOS, idEquipe,
			// dataRoteiro, sequencialReferencia);
			// }

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */
	public void atualizarOrdemServicoProgramacaoSituacaoOs(Integer numeroOS, Date dataRoteiro, short situacaoFechamento,
					Integer idOsProgramNaoEncerMotivo, Date dataHoraVisita, Usuario usuario) throws ControladorException{

		try{

			Collection<Equipe> colecaoEquipes = this.repositorioOrdemServico.recuperaEquipeDaOSProgramacaoPorDataRoteiro(numeroOS,
							dataRoteiro);

			short indicadorAtivo = ConstantesSistema.NUMERO_NAO_INFORMADO;

			if(situacaoFechamento == OrdemServico.SITUACAO_PENDENTE.shortValue()){
				indicadorAtivo = ConstantesSistema.NAO;
			}

			if(colecaoEquipes != null && !colecaoEquipes.isEmpty()){
				Iterator itera = colecaoEquipes.iterator();
				while(itera.hasNext()){

					Equipe equipe = (Equipe) itera.next();

					OrdemServicoProgramacao ordemServicoProgramacao = this.repositorioOrdemServico
									.pesquisarOSProgramacaoAtivaComDataRoteiroIdEquipe(numeroOS, dataRoteiro, equipe.getId());

					short sequencialReferencia = ordemServicoProgramacao.getNnSequencialProgramacao();

					if(indicadorAtivo != ConstantesSistema.NUMERO_NAO_INFORMADO){
						ordemServicoProgramacao.setIndicadorAtivo(indicadorAtivo);
					}

					ordemServicoProgramacao.setSituacaoFechamento(situacaoFechamento);

					if(idOsProgramNaoEncerMotivo != null){
						FiltroOsProgramNaoEncerMotivo filtroOsProgramNaoEncerMotivo = new FiltroOsProgramNaoEncerMotivo();
						filtroOsProgramNaoEncerMotivo.adicionarParametro(new ParametroSimples(FiltroOsProgramNaoEncerMotivo.ID, idOsProgramNaoEncerMotivo));

						OsProgramNaoEncerMotivo osProgramNaoEncerMotivo = (OsProgramNaoEncerMotivo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
										filtroOsProgramNaoEncerMotivo, OsProgramNaoEncerMotivo.class.getName()));
						
						ordemServicoProgramacao.setOsProgramNaoEncerMotivo(osProgramNaoEncerMotivo);

						if (situacaoFechamento == OrdemServico.SITUACAO_PENDENTE.shortValue() 
										&& osProgramNaoEncerMotivo.getIndicadorVisitaImprodutiva().equals(ConstantesSistema.SIM)){
							ordemServicoProgramacao.setDataHoraVisita(dataHoraVisita);
						}else{
							ordemServicoProgramacao.setDataHoraVisita(null);
						}
						
						
						if(osProgramNaoEncerMotivo.getIndicadorCobraVisitaImprodutiva().equals(ConstantesSistema.SIM)){
							// Preencher Debito a Cobrar
							FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
							filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, numeroOS));

							filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
							filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DEBITO_TIPO);
							filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.REGISTRO_ATENDIMENTO);

							OrdemServico ordemServicoPesquisado = (OrdemServico) Util.retonarObjetoDeColecao(getControladorUtil()
											.pesquisar(filtroOrdemServico, OrdemServico.class.getName()));

							if(ordemServicoPesquisado.getImovel() != null){
								DebitoACobrar debitoACobrar = new DebitoACobrar();

								debitoACobrar.setRegistroAtendimento(ordemServicoPesquisado.getRegistroAtendimento());
								debitoACobrar.setOrdemServico(ordemServicoPesquisado);

								debitoACobrar.setGeracaoDebito(new Date());
								debitoACobrar.setNumeroPrestacaoCobradas(new Short("0"));
								debitoACobrar.setPercentualTaxaJurosFinanciamento(null);
								debitoACobrar.setNumeroDiasSuspensao(null);

								debitoACobrar.setUltimaAlteracao(new Date());
	
								Imovel imovelPesquisado = this.getControladorImovel().pesquisarImovel(
												ordemServicoPesquisado.getImovel().getId());

								debitoACobrar.setImovel(imovelPesquisado);
								debitoACobrar.setLocalidade(imovelPesquisado.getLocalidade());
								debitoACobrar.setQuadra(imovelPesquisado.getQuadra());
		
								debitoACobrar.setCodigoSetorComercial(imovelPesquisado.getSetorComercial().getCodigo());
								debitoACobrar.setNumeroQuadra(new Integer(imovelPesquisado.getQuadra().getNumeroQuadra()));
								debitoACobrar.setNumeroLote(imovelPesquisado.getLote());
								debitoACobrar.setNumeroSubLote(imovelPesquisado.getSubLote());
		
								CobrancaForma cobrancaForma = new CobrancaForma();
								cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);
								debitoACobrar.setCobrancaForma(cobrancaForma);
								
								FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
								filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
								filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, DebitoTipo.VISITA_TECNICA));
								filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");								
								
								DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
												filtroDebitoTipo, DebitoTipo.class.getName()));
								
								debitoACobrar.setDebitoTipo(debitoTipo);
								debitoACobrar.setLancamentoItemContabil(debitoTipo.getLancamentoItemContabil());
								debitoACobrar.setFinanciamentoTipo(debitoTipo.getFinanciamentoTipo());

								debitoACobrar.setValorDebito(debitoTipo.getValorPadrao());

								FiltroRota filtroRota = new FiltroRota();
								filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.FATURAMENTO_GRUPO );
								filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, imovelPesquisado.getRota().getId()));
								
								Rota rotaPesquisada = (Rota) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
												filtroRota, Rota.class.getName()));								
								
								debitoACobrar.setAnoMesReferenciaDebito(this.getControladorUtil().pesquisarParametrosDoSistema()
												.getAnoMesFaturamento());
								debitoACobrar.setAnoMesCobrancaDebito(rotaPesquisada.getFaturamentoGrupo().getAnoMesReferencia());
								
								getControladorFaturamento().inserirDebitoACobrar(1, debitoACobrar, null, imovelPesquisado,
												null, null, usuario, false, null, null, null);								

							}else{
								// Gerar Guia de Pagamento para Cliente

								// Obter a quantidade de Dias para o Calculo do Vencimento
								Calendar dataVencimento = Calendar.getInstance();
								String parametroDiasCalculoVencimento = (String) ParametroFaturamento.P_NUMERO_DIAS_CALCULO_VENCIMENTO_GUIA_COBRANCA_SERVICO_ANTECIPADO
												.executar();

								Integer qtdeDiasVencimento = 0;
								if(parametroDiasCalculoVencimento != null){
									dataVencimento.add(Calendar.DAY_OF_YEAR, Integer.valueOf(parametroDiasCalculoVencimento));

									qtdeDiasVencimento = Integer.valueOf(parametroDiasCalculoVencimento);
								}

								// Criando a GuiaPagamento
								GuiaPagamento guiaPagamento = new GuiaPagamento();

								if(ordemServicoPesquisado.getRegistroAtendimento() != null
												&& ordemServicoPesquisado.getRegistroAtendimento().getId() != null){
									FiltroRegistroAtendimentoSolicitante filtroRegistroAtendimentoSolicitante = new FiltroRegistroAtendimentoSolicitante();
									filtroRegistroAtendimentoSolicitante.adicionarParametro(new ParametroSimples(
													FiltroRegistroAtendimentoSolicitante.REGISTRO_ATENDIMENTO_ID, ordemServicoPesquisado
																	.getRegistroAtendimento().getId()));

									RegistroAtendimentoSolicitante registroAtendimentoSolicitante = (RegistroAtendimentoSolicitante) Util
													.retonarObjetoDeColecao(getControladorUtil().pesquisar(
																	filtroRegistroAtendimentoSolicitante,
																	RegistroAtendimentoSolicitante.class.getName()));
									if(registroAtendimentoSolicitante != null){
										guiaPagamento.setCliente(registroAtendimentoSolicitante.getCliente());
									}
								}

								guiaPagamento.setRegistroAtendimento(ordemServicoPesquisado.getRegistroAtendimento());
								guiaPagamento.setNumeroPrestacaoTotal(Short.valueOf("1"));
								guiaPagamento.setIndicadorEmissaoObservacaoRA((short) 2);
								guiaPagamento.setServicoTipo(null);

								guiaPagamento.setOrdemServico(ordemServicoPesquisado);

								// Criando as Prestações
								Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacao = new ArrayList<GuiaPagamentoPrestacaoHelper>();

								FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
								filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
								filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, DebitoTipo.VISITA_TECNICA));
								filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");

								DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
												filtroDebitoTipo, DebitoTipo.class.getName()));

								GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacao = new GuiaPagamentoPrestacaoHelper();
								guiaPagamentoPrestacao.setDescricaoTipoDebito(ordemServicoPesquisado.getServicoTipo().getDescricao());
								guiaPagamentoPrestacao.setId(Long.valueOf(ordemServicoPesquisado.getServicoTipo().getId()));

								guiaPagamentoPrestacao.setIdItemLancamentoContabil(debitoTipo.getLancamentoItemContabil().getId());
								guiaPagamentoPrestacao.setUltimaAlteracao(new Date());
								guiaPagamentoPrestacao.setValorPrestacaoTipoDebito(debitoTipo.getValorPadrao());
								guiaPagamentoPrestacao.setValorTipoDebito(debitoTipo.getValorPadrao());

								colecaoGuiaPagamentoPrestacao.add(guiaPagamentoPrestacao);

								// Carrega segundo colecao
								Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacaoGuia = new ArrayList<ListaDadosPrestacaoGuiaHelper>();
								ListaDadosPrestacaoGuiaHelper listaDadosPrestacaoGuia = new ListaDadosPrestacaoGuiaHelper();

								listaDadosPrestacaoGuia.setPrestacao(1);
								listaDadosPrestacaoGuia.setDataVencimentoPrestacao(dataVencimento.getTime());

								Map<Integer, BigDecimal> mapValorDebitoNaPrestacaoPorTipoDebito = listaDadosPrestacaoGuia
												.getMapValorDebitoNaPrestacaoPorTipoDebito();
								mapValorDebitoNaPrestacaoPorTipoDebito.put(debitoTipo.getId(),
												debitoTipo.getValorPadrao());

								Map<Integer, Integer> mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito = new HashedMap();

								listaDadosPrestacaoGuia.setMapValorDebitoNaPrestacaoPorTipoDebito(mapValorDebitoNaPrestacaoPorTipoDebito);
								listaDadosPrestacaoGuia
												.setMapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito(mapNumeroProcessoAdministrativoExecucaoFiscalNaPrestacaoPorTipoDebito);

								colecaoListaDadosPrestacaoGuia.add(listaDadosPrestacaoGuia);
								//

								SimpleDateFormat formataData = new SimpleDateFormat("dd/MM/yyyy");

								FiltroUsuario filtroUsuario = new FiltroUsuario();

								// Busca o usuário por senha e login
								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.GERENCIA_REGIONAL);
								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.LOCALIDADE_ELO);
								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.LOCALIDADE);

								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.USUARIO_SITUACAO);
								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.USUARIO_TIPO);
								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.UNIDADE_ORGANIZACIONAL);
								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.FUNCIONARIO);
								filtroUsuario.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.EMPRESA);

								filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, usuario.getId()));

								Usuario usuarioGuiaPagamento = (Usuario) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
												filtroUsuario, Usuario.class.getName()));

								String numeroContratoParcelOrgaoPublico = null;
								this.getControladorFaturamento().inserirGuiaPagamento(guiaPagamento,
												usuarioGuiaPagamento, formataData.format(dataVencimento.getTime()).toString(),
												qtdeDiasVencimento, colecaoGuiaPagamentoPrestacao, colecaoListaDadosPrestacaoGuia,
												numeroContratoParcelOrgaoPublico);

							}
						}						

					}

					ordemServicoProgramacao.setUltimaAlteracao(new Date());

					this.verificarOrdemServicoProgramacaoControleConcorrencia(ordemServicoProgramacao);
					this.getControladorUtil().atualizar(ordemServicoProgramacao);

					// this.reordenaSequencialProgramacaoOSZerado(numeroOS, equipe
					// .getId(), dataRoteiro, sequencialReferencia);

					this.reordenaSequencialOrdemServicoProgramacao(dataRoteiro, equipe.getId());
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programacao de Ordens de Servico
	 * 
	 * @author Rafael Pinto
	 * @date 21/09/2006
	 * @throws ControladorException
	 */
	private void reordenaSequencialProgramacaoOSZerado(Integer numeroOs, Integer idEquipe, Date dataRoteiro, short sequencialReferencia)
					throws ControladorException{

		try{

			Collection<OrdemServicoProgramacao> colecaoOsProgramacao = this.repositorioOrdemServico
							.pesquisarOSProgramacaoComSequencialMaior(numeroOs, dataRoteiro, idEquipe, sequencialReferencia);

			if(colecaoOsProgramacao != null && !colecaoOsProgramacao.isEmpty()){

				Iterator itera = colecaoOsProgramacao.iterator();
				while(itera.hasNext()){
					OrdemServicoProgramacao osProgramacao = (OrdemServicoProgramacao) itera.next();

					short seq = osProgramacao.getNnSequencialProgramacao();

					osProgramacao.setNnSequencialProgramacao(seq--);
					osProgramacao.setUltimaAlteracao(new Date());

					this.verificarOrdemServicoProgramacaoControleConcorrencia(osProgramacao);
					this.getControladorUtil().atualizar(osProgramacao);

				}

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Método que valida a ordem de serviço utilizado por diversos outros
	 * métodos do atendimento ao público
	 * 
	 * @author Leonardo Regis
	 * @date 22/09/2006
	 * @throws ControladorException
	 */
	public void validaOrdemServico(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		if(!veioEncerrarOS){

			if(ordemServico.getSituacao() != OrdemServico.SITUACAO_ENCERRADO.shortValue()){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.ordem_servico_situacao", null, OrdemServico.SITUACAO_DESCRICAO_ENCERRADO);
			}else{
				if(ordemServico.getAtendimentoMotivoEncerramento() != null
								&& ordemServico.getAtendimentoMotivoEncerramento().getIndicadorExecucao() != ConstantesSistema.SIM){

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.ordem_servico_nao_executada", null,
									OrdemServico.SITUACAO_DESCRICAO_ENCERRADO_NAO_EXECUTADA);
				}
			}

			if(Integer.valueOf(ordemServico.getIndicadorComercialAtualizado()).intValue() == ConstantesSistema.SIM.intValue()){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.ordem_servico_sistema_comercial_atualizado");
			}
		}
	}

	/**
	 * Método que valida a ordem de serviço utilizado por diversos outros
	 * métodos do atendimento ao público sem a validação de indicador comercial
	 * 
	 * @author Rafael Francisco Pinto
	 * @date 12/10/2006
	 * @throws ControladorException
	 */
	public void validaOrdemServicoAtualizacao(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		if(!veioEncerrarOS){

			if(ordemServico.getSituacao() != OrdemServico.SITUACAO_ENCERRADO.shortValue()){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.ordem_servico_situacao", null, OrdemServico.SITUACAO_DESCRICAO_ENCERRADO);
			}else{
				if(ordemServico.getAtendimentoMotivoEncerramento() != null
								&& ordemServico.getAtendimentoMotivoEncerramento().getIndicadorExecucao() != ConstantesSistema.SIM){

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.ordem_servico_nao_executada", null,
									OrdemServico.SITUACAO_DESCRICAO_ENCERRADO_NAO_EXECUTADA);
				}
			}

		}
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */

	public Collection<Equipe> recuperaEquipeDaOSProgramacaoPorDataRoteiro(Integer idOs, Date dataRoteiro) throws ControladorException{

		try{
			return this.repositorioOrdemServico.recuperaEquipeDaOSProgramacaoPorDataRoteiro(idOs, dataRoteiro);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Atualização Geral de OS
	 * 
	 * @author Leonardo Regis
	 * @date 25/09/2006
	 * @param os
	 * @param updateCorte
	 * @param updateSupressao
	 * @throws ControladorException
	 */
	public void atualizaOSGeral(OrdemServico os, boolean updateCorte, boolean updateSupressao) throws ControladorException{

		try{
			this.repositorioOrdemServico.atualizaOSGeral(os, updateCorte, updateSupressao);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 25/09/2006
	 * @param numeroOS
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarColecaoServicoTipo(Integer numeroOS) throws ControladorException{

		Collection colecaoServicoTipo = null;
		try{
			Collection colecaoEspecificacaoServicoTipo = this.repositorioOrdemServico.pesquisarSolicitacoesServicoTipoOS(numeroOS);
			if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){
				Iterator iteEspecificacaoServicoTipo = colecaoEspecificacaoServicoTipo.iterator();
				Collection colecaoIdsServicoTipo = new ArrayList();
				while(iteEspecificacaoServicoTipo.hasNext()){
					EspecificacaoServicoTipo espcificacaoServicoTipo = (EspecificacaoServicoTipo) iteEspecificacaoServicoTipo.next();
					colecaoIdsServicoTipo.add(espcificacaoServicoTipo.getComp_id().getIdServicoTipo());
				}
				colecaoServicoTipo = repositorioOrdemServico.pesquisarServicoTipo(colecaoIdsServicoTipo);
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return colecaoServicoTipo;
	}

	/**
	 * [UC0470] Acompanhar Roteiro de Programação de Ordens de Serviço
	 * 
	 * @author Rafael Pinto
	 * @date 25/09/2006
	 * @param idOs
	 *            ,dataRoteiro
	 * @return Equipe
	 * @throws ErroRepositorioException
	 */
	public void verificaExitenciaProgramacaoAtivaParaDiasAnteriores(Integer idOs, Date dataRoteiro) throws ControladorException{

		try{

			boolean retorno = this.repositorioOrdemServico.verificaExitenciaProgramacaoAtivaParaDiasAnteriores(idOs, dataRoteiro);

			if(retorno){
				throw new ControladorException("atencao.programacao_ativa_dias_anteriores");
			}

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Faz o controle de concorrencia da ordem servico
	 * 
	 * @author Rafael Pinto
	 * @throws ControladorException
	 */
	public void verificarOrdemServicoControleConcorrencia(OrdemServico ordemServico) throws ControladorException{

		FiltroOrdemServico filtroOrdeServico = new FiltroOrdemServico();
		filtroOrdeServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, ordemServico.getId()));

		Collection colecaoOrdemServicoBase = getControladorUtil().pesquisar(filtroOrdeServico, OrdemServico.class.getName());

		if(colecaoOrdemServicoBase == null || colecaoOrdemServicoBase.isEmpty()){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		OrdemServico ordemServicoAtual = (OrdemServico) Util.retonarObjetoDeColecao(colecaoOrdemServicoBase);

		if(ordemServicoAtual.getUltimaAlteracao().after(ordemServico.getUltimaAlteracao())){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.atualizacao.timestamp");
		}
	}

	/**
	 * [UC0391] Inserir Valor de Cobrança de Serviço.
	 * Permite a inclusão de um novo valor de cobrança de serviço na tabela
	 * SERVICO_COBRANCA_VALOR.
	 * 
	 * @author Leonardo Regis
	 * @date 29/09/2006
	 * @param servicoCobrancaValor
	 * @throws ControladorException
	 */
	public void inserirValorCobrancaServico(ServicoCobrancaValor servicoCobrancaValor) throws ControladorException{

		/* [SB0001] Gerar Valor da cobrança do Serviço */
		getControladorUtil().inserir(servicoCobrancaValor);
	}

	/**
	 * [UC0391] Atualizar Valor de Cobrança de Serviço.
	 * Permite a atualização de um novo valor de cobrança de serviço na tabela
	 * SERVICO_COBRANCA_VALOR.
	 * 
	 * @author Rômulo Aurélio
	 * @date 01/11/2006
	 * @param servicoCobrancaValor
	 * @throws ControladorException
	 */
	public void atualizarValorCobrancaServico(ServicoCobrancaValor servicoCobrancaValor) throws ControladorException{

		// [FS0002] - Atualização realizada por outro usuário
		FiltroServicoCobrancaValor filtroServicoCobrancaValor = new FiltroServicoCobrancaValor();
		filtroServicoCobrancaValor.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.ID, servicoCobrancaValor.getId()));

		Collection colecaoServicoCobrancaValorBase = getControladorUtil().pesquisar(filtroServicoCobrancaValor,
						ServicoCobrancaValor.class.getName());

		if(colecaoServicoCobrancaValorBase == null || colecaoServicoCobrancaValorBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		ServicoCobrancaValor servicoCobrancaValorBase = (ServicoCobrancaValor) colecaoServicoCobrancaValorBase.iterator().next();

		if(servicoCobrancaValorBase.getUltimaAlteracao().after(servicoCobrancaValor.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		filtroServicoCobrancaValor.limparListaParametros();

		servicoCobrancaValor.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(servicoCobrancaValor);
	}

	/**
	 * [UC0298] Manter Tipo de Retorno da OS_Referida [] Atualizar Tipo de
	 * Retorno da OS_Referida Metodo que atualiza a Situação Usuario
	 * 
	 * @author Thiago Tenório
	 * @date 25/05/2006
	 * @param Tipo
	 *            de Retorno da OS_Referida Usuário
	 * @throws ControladorException
	 */

	public void atualizarTipoRetornoOrdemServicoReferida(OsReferidaRetornoTipo osReferidaRetornoTipo) throws ControladorException{

		// Verifica se todos os campos obrigatorios foram preenchidos

		if((osReferidaRetornoTipo.getDescricao() == null || osReferidaRetornoTipo.getDescricao().equals(
						String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& (osReferidaRetornoTipo.getDescricaoAbreviada() == null || osReferidaRetornoTipo.getDescricaoAbreviada().equals(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& (osReferidaRetornoTipo.getServicoTipoReferencia() == null || osReferidaRetornoTipo.getServicoTipoReferencia()
										.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& (osReferidaRetornoTipo.getIndicadorTrocaServico() == null || osReferidaRetornoTipo.getIndicadorTrocaServico()
										.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& (osReferidaRetornoTipo.getSituacaoOsReferencia() == null || osReferidaRetornoTipo.getSituacaoOsReferencia()
										.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& (osReferidaRetornoTipo.getAtendimentoMotivoEncerramento() == null || osReferidaRetornoTipo
										.getAtendimentoMotivoEncerramento().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))

						&& (osReferidaRetornoTipo.getIndicadorTrocaServico() == null || osReferidaRetornoTipo.getIndicadorTrocaServico()
										.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO)))){
			throw new ControladorException("atencao.filtro.nenhum_parametro_informado");

		}

		// Verifica se o campo Descrição foi preenchido

		if(osReferidaRetornoTipo.getDescricao() == null
						|| osReferidaRetornoTipo.getDescricao().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.Informe_entidade", null, " Descrição");
		}

		// Verifica se o campo Referência do Tipo de Serviço foi preenchido
		if(osReferidaRetornoTipo.getServicoTipoReferencia() == null
						|| osReferidaRetornoTipo.getServicoTipoReferencia().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.Informe_entidade", null, " Referência do Tipo de Serviço");
		}

		// Verifica se o campo Indicador de Troca de Serviço foi preenchido

		if(osReferidaRetornoTipo.getIndicadorTrocaServico() == null
						|| osReferidaRetornoTipo.getIndicadorTrocaServico().equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			throw new ControladorException("atencao.Informe_entidade", null, " Indicador de Troca de Serviço");
		}

		// Verifica se o campo Código da Situação foi preenchido

		// if (osReferidaRetornoTipo.getSituacaoOsReferencia() == null
		// || osReferidaRetornoTipo.getSituacaoOsReferencia().equals(
		// "" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
		// throw new ControladorException("atencao.Informe_entidade", null,
		// " Código da Situação");
		// }

		// [FS0003] - Atualização realizada por outro usuário
		FiltroOSReferidaRetornoTipo filtroOSReferidaRetornoTipo = new FiltroOSReferidaRetornoTipo();
		filtroOSReferidaRetornoTipo.adicionarParametro(new ParametroSimples(FiltroOSReferidaRetornoTipo.ID, osReferidaRetornoTipo.getId()));

		Collection colecaoTipoRetornoOsReferidaBase = getControladorUtil().pesquisar(filtroOSReferidaRetornoTipo,
						OsReferidaRetornoTipo.class.getName());

		if(colecaoTipoRetornoOsReferidaBase == null || colecaoTipoRetornoOsReferidaBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		OsReferidaRetornoTipo osReferidaRetornoTipoBase = (OsReferidaRetornoTipo) colecaoTipoRetornoOsReferidaBase.iterator().next();

		if(osReferidaRetornoTipo.getUltimaAlteracao().after(osReferidaRetornoTipoBase.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		osReferidaRetornoTipo.setUltimaAlteracao(new Date());

		getControladorUtil().atualizar(osReferidaRetornoTipo);

	}

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * Pesquisa os campos da OS que serão impressos no relatório de Ordem de
	 * Servico
	 * 
	 * @author Rafael Corrêa
	 * @date 17/10/2006
	 * @param idOrdemServico
	 * @return OSRelatorioHelper
	 * @throws ControladorException
	 */
	public Collection pesquisarOSRelatorio(Collection idsOrdemServico) throws ControladorException{

		Object[] dadosOS = null;

		Collection colecaoDadosOS = null;

		Collection colecaoOSRelatorioHelper = new ArrayList();

		try{
			if(idsOrdemServico.size() > 1000){

				Iterator iterator = idsOrdemServico.iterator();
				int qtdIteracoes = idsOrdemServico.size() / 1000;
				for(int i = 0; i <= qtdIteracoes; i++){
					Collection temp = new ArrayList();
					int count = 0;
					while(iterator.hasNext()){
						if(count < 1000){
							temp.add(iterator.next());
							count++;
						}else{
							break;
						}
					}
					if(colecaoDadosOS != null){
						colecaoDadosOS.addAll(this.repositorioOrdemServico.pesquisarOSRelatorio(temp));
					}else{
						colecaoDadosOS = this.repositorioOrdemServico.pesquisarOSRelatorio(temp);
					}
				}
			}else{
				colecaoDadosOS = this.repositorioOrdemServico.pesquisarOSRelatorio(idsOrdemServico);
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosOS != null && !colecaoDadosOS.isEmpty()){

			Iterator colecaoDadosOSIterator = colecaoDadosOS.iterator();

			while(colecaoDadosOSIterator.hasNext()){

				dadosOS = (Object[]) colecaoDadosOSIterator.next();

				if(dadosOS != null){

					OSRelatorioHelper oSRelatorioHelper = new OSRelatorioHelper();

					// oSRelatorioHelper.setIdOrdemServico(idOrdemServico);

					// Data da Geração
					if(dadosOS[0] != null){ // 0
						oSRelatorioHelper.setDataGeracao((Date) dadosOS[0]);
					}

					Imovel imovel = new Imovel();

					// Localidade
					if(dadosOS[1] != null){ // 1
						Localidade localidade = new Localidade();
						localidade.setId((Integer) dadosOS[1]);
						oSRelatorioHelper.setIdLocalidade((Integer) dadosOS[1]);
						imovel.setLocalidade(localidade);
					}

					// Setor Comercial
					if(dadosOS[2] != null){ // 2
						SetorComercial setorComercial = new SetorComercial();
						setorComercial.setCodigo((Integer) dadosOS[2]);
						imovel.setSetorComercial(setorComercial);
					}

					// Quadra
					if(dadosOS[3] != null){ // 3
						Quadra quadra = new Quadra();
						quadra.setNumeroQuadra((Integer) dadosOS[3]);
						imovel.setQuadra(quadra);
					}

					// Lote
					if(dadosOS[4] != null){ // 4
						imovel.setLote((Short) dadosOS[4]);
					}

					// SubLote
					if(dadosOS[5] != null){ // 5
						imovel.setSubLote((Short) dadosOS[5]);
					}

					// Rota
					if(dadosOS[32] != null){ // 32
						Integer idRota = (Integer) dadosOS[32];

						Rota rota = new Rota();
						rota.setId(idRota);

						imovel.setRota(rota);
					}

					// Segmento
					if(dadosOS[33] != null){ // 33
						imovel.setNumeroSegmento((Short) dadosOS[33]);
					}

					// Inscrição do Imóvel
					String inscricao = "";

					if(imovel.getLocalidade() != null){
						inscricao = imovel.getInscricaoFormatada();
					}
					oSRelatorioHelper.setInscricaoImovel(inscricao);

					// Matrícula do Imóvel
					if(dadosOS[6] != null){ // 6
						oSRelatorioHelper.setIdImovel((Integer) dadosOS[6]);
						// Seta o id no objeto imóvel para pesquisar,
						// posteriormente, a
						// descrição da categoria e a quantidade de economias
						imovel.setId((Integer) dadosOS[6]);
					}

					// Categoria e Quantidade de Economias
					if(imovel.getId() != null){
						Categoria categoria = getControladorImovel().obterDescricoesCategoriaImovel(imovel);
						oSRelatorioHelper.setCategoria(categoria.getDescricaoAbreviada());

						int quantidadeEconomias = getControladorImovel().obterQuantidadeEconomias(imovel);
						oSRelatorioHelper.setQuantidadeEconomias(quantidadeEconomias);
					}

					// Situação da Ligação de Água
					if(dadosOS[7] != null){ // 7
						oSRelatorioHelper.setSituacaoAgua((String) dadosOS[7]);
					}

					// Situação da Ligação de Esgoto
					if(dadosOS[8] != null){ // 8
						oSRelatorioHelper.setSituacaoEsgoto((String) dadosOS[8]);
					}

					// Esgoto Fixo
					if(dadosOS[9] != null){ // 9
						oSRelatorioHelper.setEsgotoFixo((Integer) dadosOS[9]);
					}

					// Pavimento Rua
					if(dadosOS[10] != null){ // 10
						oSRelatorioHelper.setPavimentoRua((String) dadosOS[10]);
					}else{
						oSRelatorioHelper.setPavimentoRua("");
					}

					// Pavimento Calçada
					if(dadosOS[11] != null){ // 11
						oSRelatorioHelper.setPavimentoCalcada((String) dadosOS[11]);
					}else{
						oSRelatorioHelper.setPavimentoCalcada("");
					}

					// Meio
					if(dadosOS[12] != null){ // 12
						oSRelatorioHelper.setMeio((String) dadosOS[12]);
					}else{
						oSRelatorioHelper.setMeio("");
					}

					// Nome do Atendente
					if(dadosOS[13] != null){ // 13
						oSRelatorioHelper.setNomeAtendente((String) dadosOS[13]);
					}

					// Matrícula do Atendente
					if(dadosOS[14] != null){ // 14
						oSRelatorioHelper.setIdAtendente((Integer) dadosOS[14]);
					}

					// Ponto de Referência
					if(dadosOS[15] != null){ // 15
						oSRelatorioHelper.setPontoReferencia((String) dadosOS[15]);
					}else{
						oSRelatorioHelper.setPontoReferencia("");
					}

					// Id Serviço Solicitado
					if(dadosOS[16] != null){ // 16
						oSRelatorioHelper.setIdServicoSolicitado((Integer) dadosOS[16]);
					}

					// Descrição Serviço Solicitado
					if(dadosOS[17] != null){ // 17
						oSRelatorioHelper.setDescricaoServicoSolicitado((String) dadosOS[17]);
					}

					// Valor Serviço Solicitado
					if(dadosOS[31] != null){ // 31
						oSRelatorioHelper.setValorServicoSolicitado(Util.formatarMoedaReal((BigDecimal) dadosOS[31]));
					}

					// Local Ocorrência
					if(dadosOS[18] != null){ // 18
						oSRelatorioHelper.setLocalOcorrencia((String) dadosOS[18]);
					}

					// Data Previsão Atual
					if(dadosOS[19] != null){ // 19
						oSRelatorioHelper.setPrevisao((Date) dadosOS[19]);
					}

					// Observação do RA
					if(dadosOS[20] != null){ // 20
						oSRelatorioHelper.setObservacaoRA((String) dadosOS[20]);
					}else{
						oSRelatorioHelper.setObservacaoRA("");
					}

					// Observação da OS
					if(dadosOS[21] != null){ // 21
						oSRelatorioHelper.setObservacaoOS((String) dadosOS[21]);
					}else{
						oSRelatorioHelper.setObservacaoOS("");
					}

					// Id do RA
					if(dadosOS[22] != null){ // 22
						oSRelatorioHelper.setIdRA((Integer) dadosOS[22]);
					}

					// Especificação
					if(dadosOS[23] != null){ // 23
						oSRelatorioHelper.setEspecificao((String) dadosOS[23]);
					}

					// Id da OS
					if(dadosOS[24] != null){ // 24
						oSRelatorioHelper.setIdOrdemServico((Integer) dadosOS[24]);
					}

					// Tempo Médio Execução
					if(dadosOS[25] != null){ // 25
						oSRelatorioHelper.setTempoMedioExecucao((Short) dadosOS[25]);
					}

					// Origem
					if(dadosOS[26] != null){ // 26
						oSRelatorioHelper.setOrigem((String) dadosOS[26]);
					}else{
						oSRelatorioHelper.setOrigem("");
					}

					// Sequencial Rota
					if(dadosOS[27] != null){ // 27
						oSRelatorioHelper.setSequencialRota((Integer) dadosOS[27]);
					}

					// Rota
					if(dadosOS[28] != null){ // 28
						oSRelatorioHelper.setCodigoRota((Short) dadosOS[28]);
					}

					// Id do Serviço Tipo Referência
					if(dadosOS[29] != null){ // 29
						oSRelatorioHelper.setIdServicoTipoReferencia((Integer) dadosOS[29]);
						FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
						filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, (Integer) dadosOS[29]));
						Collection servicoTipos = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
						ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(servicoTipos);
						oSRelatorioHelper.setDescricaoServicoTipoReferencia(servicoTipo.getDescricao());
					}

					// Descrição do Serviço Tipo Referência
					/*
					 * if (dadosOS[30] != null) { // 30 oSRelatorioHelper
					 * .setDescricaoServicoTipoReferencia((String) dadosOS[30]); }
					 */

					String endereco = this.obterEnderecoCompletoOS(oSRelatorioHelper.getIdOrdemServico());
					oSRelatorioHelper.setEndereco(endereco);

					if(oSRelatorioHelper.getIdRA() != null){

						Object[] dadosSolicitante = getControladorRegistroAtendimento()
										.pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(oSRelatorioHelper.getIdRA());

						if(dadosSolicitante != null){

							String telefone = "";

							// Id do Cliente
							if(dadosSolicitante[0] != null){ // 0
								oSRelatorioHelper.setIdSolicitante((Integer) dadosSolicitante[0]);

								// Seta o valor do telefone a partir de cliente
								// fone,
								// caso o id do cliente seja diferente de nulo
								telefone = getControladorCliente().pesquisarClienteFonePrincipal(oSRelatorioHelper.getIdSolicitante());
								oSRelatorioHelper.setTelefone(telefone);
							}else{
								// Seta o valor do telefone a partir de
								// solicitante
								// fone,
								// caso o id do cliente seja nulo
								telefone = getControladorRegistroAtendimento().pesquisarSolicitanteFonePrincipal(
												oSRelatorioHelper.getIdRA());
								oSRelatorioHelper.setTelefone(telefone);
							}

							// Nome do Cliente
							if(dadosSolicitante[1] != null){ // 1
								oSRelatorioHelper.setNomeSolicitante((String) dadosSolicitante[1]);
							}

							// Id da Unidade
							if(dadosSolicitante[2] != null){ // 2
								oSRelatorioHelper.setIdUnidade((Integer) dadosSolicitante[2]);
							}

							// Descrição da Unidade
							if(dadosSolicitante[3] != null){ // 3
								oSRelatorioHelper.setDescricaoUnidade((String) dadosSolicitante[3]);
							}

							// Id do Funcionário
							if(dadosSolicitante[4] != null){ // 4
								oSRelatorioHelper.setIdSolicitante((Integer) dadosSolicitante[4]);
							}

							// Nome do Funcionário
							if(dadosSolicitante[5] != null){ // 5
								oSRelatorioHelper.setNomeSolicitante((String) dadosSolicitante[5]);
							}

							// Nome do Solicitante
							if(dadosSolicitante[6] != null){ // 6
								oSRelatorioHelper.setNomeSolicitante((String) dadosSolicitante[6]);
							}

							// Endereço
							// String endereco = getControladorRegistroAtendimento()
							// .obterEnderecoOcorrenciaRA(
							// oSRelatorioHelper.getIdRA());
						}
					}

					colecaoOSRelatorioHelper.add(oSRelatorioHelper);

				}

			}

		}

		return colecaoOSRelatorioHelper;

	}

	/**
	 * [UC0482] - Obter Endereço Abreviado da Ocorrência do RA
	 * Pesquisa o Endereco Abreviado da OS
	 * 
	 * @author Rafael Corrêa
	 * @date 19/10/2006
	 * @author Saulo Lima
	 * @date 11/02/2009
	 *       Adicionado o endereço do imovel da Ordem de Serviço
	 * @author Saulo Lima
	 * @date 27/04/2012
	 *       Correção para só pesquisar o endereço do Doc Cob caso nao tenha localizado na RA
	 * @param idOrdemServico
	 * @throws ControladorException
	 */
	public String obterEnderecoAbreviadoOS(Integer idOrdemServico) throws ControladorException{

		Object[] dadosOS = null;
		try{
			dadosOS = repositorioOrdemServico.obterDadosPesquisaEnderecoAbreviadoOS(idOrdemServico);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		String endereco = null;

		if(dadosOS != null){

			// Id do RA
			if(dadosOS[0] != null){
				Integer idRa = (Integer) dadosOS[0];
				endereco = this.getControladorRegistroAtendimento().obterEnderecoAbreviadoOcorrenciaRA(idRa);
			}

			// Id do Imóvel do Documento de Cobrança
			if(dadosOS[1] != null && Util.isVazioOuBranco(endereco)){
				Integer idImovel = (Integer) dadosOS[1];
				endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovel);
			}

			// Id do Imóvel da Ordem de Serviço
			if(dadosOS[2] != null && Util.isVazioOuBranco(endereco)){
				Integer idImovelOS = (Integer) dadosOS[2];
				endereco = this.getControladorEndereco().obterEnderecoAbreviadoImovel(idImovelOS);
			}
		}

		return endereco;
	}

	/**
	 * Pesquisa o endereco completo da OS
	 * 
	 * @author Saulo Lima
	 * @date 07/04/2010
	 * @author Saulo Lima
	 * @date 27/04/2012
	 *       Correção para só pesquisar o endereço do Doc Cob caso nao tenha localizado na RA
	 * @param idOrdemServico
	 * @return String
	 * @throws ControladorException
	 */
	public String obterEnderecoCompletoOS(Integer idOrdemServico) throws ControladorException{

		Object[] dadosOS = null;
		try{
			dadosOS = repositorioOrdemServico.obterDadosPesquisaEnderecoAbreviadoOS(idOrdemServico);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		String endereco = null;

		if(dadosOS != null){

			// Id do RA
			if(dadosOS[0] != null){
				Integer idRa = (Integer) dadosOS[0];
				endereco = this.getControladorRegistroAtendimento().obterEnderecoOcorrenciaRA(idRa);
			}

			// Id do Imóvel do Documento de Cobrança
			if(dadosOS[1] != null && Util.isVazioOuBranco(endereco)){
				Integer idImovel = (Integer) dadosOS[1];
				endereco = this.getControladorEndereco().pesquisarEndereco(idImovel);
			}

			// Id do Imóvel da Ordem de Serviço
			if(dadosOS[2] != null && Util.isVazioOuBranco(endereco)){
				Integer idImovelOS = (Integer) dadosOS[2];
				endereco = this.getControladorEndereco().pesquisarEndereco(idImovelOS);
			}

			// Se o endereco ainda assim for null retorna String vazio
			if(endereco == null){
				endereco = "";
			}
		}

		return endereco;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection<Material> obterMateriaisProgramados(Integer numeroOS) throws ControladorException{

		Collection colecaoMaterial = null;
		Collection<Material> retorno = new ArrayList();

		try{

			colecaoMaterial = repositorioOrdemServico.obterMateriaisProgramados(numeroOS);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// [FS0002 - Verificar Existência de Dados]
		/*
		 * if (colecaoEquipe == null || colecaoEquipe.isEmpty()) {
		 * sessionContext.setRollbackOnly(); throw new ControladorException(
		 * "atencao.entidade_sem_dados_para_selecao", null, "EQUIPE"); }
		 */

		if(colecaoMaterial != null && !colecaoMaterial.isEmpty()){

			Iterator materialIterator = colecaoMaterial.iterator();
			Object[] arrayMaterial = null;
			Material material = null;

			while(materialIterator.hasNext()){

				material = new Material();
				arrayMaterial = (Object[]) materialIterator.next();

				material.setId((Integer) arrayMaterial[0]);
				material.setDescricao((String) arrayMaterial[1]);

				retorno.add(material);
			}
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 15/09/2006
	 * @param numeroOS
	 *            ,
	 *            idMaterial
	 * @return BigDecimal
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterQuantidadePadraoMaterial(Integer numeroOS, Integer idMaterial) throws ControladorException{

		BigDecimal retorno = null;

		try{

			retorno = repositorioOrdemServico.obterQuantidadePadraoMaterial(numeroOS, idMaterial);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Imprimir OS
	 * Atualiza a data de emissão e a de última alteração de OS quando gerar o
	 * relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 26/10/2006
	 * @param colecaoIdsOrdemServico
	 * @throws ControladorException
	 */
	public void atualizarOrdemServicoRelatorio(Collection colecaoIdsOrdemServico) throws ControladorException{

		try{
			Collection colecaoDadosOS = null;
			if(colecaoIdsOrdemServico.size() > 1000){

				Iterator iterator = colecaoIdsOrdemServico.iterator();
				int qtdIteracoes = colecaoIdsOrdemServico.size() / 1000;
				for(int i = 0; i <= qtdIteracoes; i++){
					Collection temp = new ArrayList();
					int count = 0;
					while(iterator.hasNext()){
						if(count < 1000){
							temp.add(iterator.next());
							count++;
						}else{
							break;
						}
					}
					this.repositorioOrdemServico.atualizarOrdemServicoRelatorio(temp);

				}
			}else{
				this.repositorioOrdemServico.atualizarOrdemServicoRelatorio(colecaoIdsOrdemServico);

			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Raphael Rossiter
	 * @date 26/10/2006
	 * @param Collection
	 *            <ManterDadosAtividadesOrdemServicoHelper>
	 * @return void
	 * @throws ControladorException
	 */
	private void manterDadosAtividadesOrdemServico(Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoDadosAtividades)
					throws ControladorException{

		if(colecaoDadosAtividades != null && !colecaoDadosAtividades.isEmpty()){

			Iterator iteratorColecaoDadosAtividades = colecaoDadosAtividades.iterator();
			ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = null;

			FiltroOrdemServicoAtividade filtroOrdemServicoAtividade = null;
			Collection colecaoOrdemServicoAtividade = null;

			OrdemServicoAtividade ordemServicoAtividade = null;
			Integer idOrdemServicoAtividade = null;

			FiltroOsAtividadeMaterialExecucao filtroOsAtividadeMaterialExecucao = null;
			Collection colecaoOsAtividadeMaterialExecucaoPesquisa = null;
			Collection colecaoOsAtividadeMaterialExecucao = null;
			OsAtividadeMaterialExecucao osAtividadeMaterialExecucao = null;

			FiltroOsAtividadePeriodoExecucao filtroOsAtividadePeriodoExecucao = null;
			Collection colecaoOSAtividadePeriodoExecucao = null;
			Collection colecaoOSAtividadePeriodoExecucaoHelper = null;
			OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper = null;
			OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = null;
			Integer idOsAtividadePeriodoExecucao = null;

			FiltroOsExecucaoEquipe filtroOsExecucaoEquipe = null;
			Collection colecaoOsExecucaoEquipe = null;
			OSExecucaoEquipeHelper osExecucaoEquipeHelper = null;
			OsExecucaoEquipe osExecucaoEquipe = null;
			OsExecucaoEquipePK osExecucaoEquipePK = null;

			Collection colecaoOsExecucaoEquipeComponentes = null;
			OsExecucaoEquipeComponentes osExecucaoEquipeComponentes = null;

			// Atividade
			while(iteratorColecaoDadosAtividades.hasNext()){

				manterDadosAtividadesOrdemServicoHelper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoDadosAtividades.next();

				filtroOrdemServicoAtividade = new FiltroOrdemServicoAtividade();

				filtroOrdemServicoAtividade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoAtividade.ID_ORDEM_SERVICO,
								manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getOrdemServico().getId()));

				filtroOrdemServicoAtividade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoAtividade.ID_ATIVIDADE,
								manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getAtividade().getId()));

				colecaoOrdemServicoAtividade = this.getControladorUtil().pesquisar(filtroOrdemServicoAtividade,
								OrdemServicoAtividade.class.getName());

				if(!Util.isVazioOrNulo(colecaoOrdemServicoAtividade)){

					ordemServicoAtividade = (OrdemServicoAtividade) Util.retonarObjetoDeColecao(colecaoOrdemServicoAtividade);
				}else{

					ordemServicoAtividade = manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade();
					ordemServicoAtividade.setUltimaAlteracao(new Date());

					// Inserindo OrdemServicoAtividade
					idOrdemServicoAtividade = (Integer) this.getControladorUtil().inserir(ordemServicoAtividade);
					ordemServicoAtividade.setId(idOrdemServicoAtividade);
				}

				// Apropriação de Material
				// ==============================================================================================
				colecaoOsAtividadeMaterialExecucao = manterDadosAtividadesOrdemServicoHelper.getColecaoOsAtividadeMaterialExecucao();

				if(colecaoOsAtividadeMaterialExecucao != null && !colecaoOsAtividadeMaterialExecucao.isEmpty()){

					Iterator iteratorcolecaoOsAtividadeMaterialExecucao = colecaoOsAtividadeMaterialExecucao.iterator();

					while(iteratorcolecaoOsAtividadeMaterialExecucao.hasNext()){

						osAtividadeMaterialExecucao = (OsAtividadeMaterialExecucao) iteratorcolecaoOsAtividadeMaterialExecucao.next();

						filtroOsAtividadeMaterialExecucao = new FiltroOsAtividadeMaterialExecucao();

						filtroOsAtividadeMaterialExecucao.adicionarParametro(new ParametroSimples(
										FiltroOsAtividadeMaterialExecucao.ID_ORDEM_SERVICO_ATIVIDADE, ordemServicoAtividade.getId()));

						filtroOsAtividadeMaterialExecucao.adicionarParametro(new ParametroSimples(
										FiltroOsAtividadeMaterialExecucao.ID_MATERIAL, osAtividadeMaterialExecucao.getMaterial().getId()));

						colecaoOsAtividadeMaterialExecucaoPesquisa = this.getControladorUtil().pesquisar(filtroOsAtividadeMaterialExecucao,
										OsAtividadeMaterialExecucao.class.getName());

						if(Util.isVazioOrNulo(colecaoOsAtividadeMaterialExecucaoPesquisa)){

							osAtividadeMaterialExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
							osAtividadeMaterialExecucao.setUltimaAlteracao(new Date());
							this.getControladorUtil().inserir(osAtividadeMaterialExecucao);
						}
					}
				}
				// ==============================================================================================

				// Apropriação de Horas
				colecaoOSAtividadePeriodoExecucaoHelper = manterDadosAtividadesOrdemServicoHelper
								.getColecaoOSAtividadePeriodoExecucaoHelper();

				if(!Util.isVazioOrNulo(colecaoOSAtividadePeriodoExecucaoHelper)){

					Iterator iteratorColecaoOSAtividadePeriodoExecucaoHelper = colecaoOSAtividadePeriodoExecucaoHelper.iterator();

					while(iteratorColecaoOSAtividadePeriodoExecucaoHelper.hasNext()){

						osAtividadePeriodoExecucaoHelper = (OSAtividadePeriodoExecucaoHelper) iteratorColecaoOSAtividadePeriodoExecucaoHelper
										.next();

						filtroOsAtividadePeriodoExecucao = new FiltroOsAtividadePeriodoExecucao();

						filtroOsAtividadePeriodoExecucao.adicionarParametro(new ParametroSimples(
										FiltroOsAtividadePeriodoExecucao.ID_ORDEM_SERVICO_ATIVIDADE, ordemServicoAtividade.getId()));

						filtroOsAtividadePeriodoExecucao.adicionarParametro(new ParametroSimples(
										FiltroOsAtividadePeriodoExecucao.DATA_INICIO, osAtividadePeriodoExecucaoHelper
														.getOsAtividadePeriodoExecucao().getDataInicio()));

						colecaoOSAtividadePeriodoExecucao = this.getControladorUtil().pesquisar(filtroOsAtividadePeriodoExecucao,
										OsAtividadePeriodoExecucao.class.getName());

						if(colecaoOSAtividadePeriodoExecucao != null && !colecaoOSAtividadePeriodoExecucao.isEmpty()){

							osAtividadePeriodoExecucao = (OsAtividadePeriodoExecucao) Util
											.retonarObjetoDeColecao(colecaoOSAtividadePeriodoExecucao);
						}else{

							osAtividadePeriodoExecucao = osAtividadePeriodoExecucaoHelper.getOsAtividadePeriodoExecucao();

							osAtividadePeriodoExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
							osAtividadePeriodoExecucao.setUltimaAlteracao(new Date());

							idOsAtividadePeriodoExecucao = (Integer) this.getControladorUtil().inserir(osAtividadePeriodoExecucao);
							osAtividadePeriodoExecucao.setId(idOsAtividadePeriodoExecucao);
						}

						// Equipe
						osExecucaoEquipeHelper = osAtividadePeriodoExecucaoHelper.getOrdemServicoExecucaoEquipeHelper();

						filtroOsExecucaoEquipe = new FiltroOsExecucaoEquipe();

						filtroOsExecucaoEquipe.adicionarParametro(new ParametroSimples(
										FiltroOsExecucaoEquipe.ID_OS_ATIVIDADE_PERIODO_EXECUCAO, osAtividadePeriodoExecucao.getId()));

						filtroOsExecucaoEquipe.adicionarParametro(new ParametroSimples(FiltroOsExecucaoEquipe.ID_EQUIPE,
										osExecucaoEquipeHelper.getOsExecucaoEquipe().getEquipe().getId()));

						colecaoOsExecucaoEquipe = this.getControladorUtil().pesquisar(filtroOsExecucaoEquipe,
										OsExecucaoEquipe.class.getName());

						if(!Util.isVazioOrNulo(colecaoOsExecucaoEquipe)){

							osExecucaoEquipe = (OsExecucaoEquipe) Util.retonarObjetoDeColecao(colecaoOsExecucaoEquipe);
						}else{

							osExecucaoEquipe = osExecucaoEquipeHelper.getOsExecucaoEquipe();

							osExecucaoEquipePK = new OsExecucaoEquipePK();
							osExecucaoEquipePK.setIdEquipe(osExecucaoEquipe.getEquipe().getId());
							osExecucaoEquipePK.setIdOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao.getId());

							osExecucaoEquipe.setComp_id(osExecucaoEquipePK);
							osExecucaoEquipe.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);

							this.getControladorUtil().inserir(osExecucaoEquipe);
						}

						// Componente
						colecaoOsExecucaoEquipeComponentes = osExecucaoEquipeHelper.getColecaoOsExecucaoEquipeComponentes();

						if(!Util.isVazioOrNulo(colecaoOsExecucaoEquipeComponentes)){

							Iterator iteratorColecaoOsExecucaoEquipeComponentes = colecaoOsExecucaoEquipeComponentes.iterator();

							while(iteratorColecaoOsExecucaoEquipeComponentes.hasNext()){

								osExecucaoEquipeComponentes = (OsExecucaoEquipeComponentes) iteratorColecaoOsExecucaoEquipeComponentes
												.next();

								osExecucaoEquipeComponentes.setOsExecucaoEquipe(osExecucaoEquipe);
								osExecucaoEquipeComponentes.setUltimaAlteracao(new Date());

								this.getControladorUtil().inserir(osExecucaoEquipeComponentes);
							}
						}

					}
				}
			}
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Saulo Lima
	 * @date 17/06/2009
	 * @param Collection
	 *            <ManterDadosAtividadesOrdemServicoHelper>
	 * @return void
	 * @throws ControladorException
	 */
	private void validarDadosAtividadesOrdemServico(Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoDadosAtividades,
					ServicoTipo tipoServico) throws ControladorException{

		if(!Util.isVazioOrNulo(colecaoDadosAtividades)){

			Iterator<ManterDadosAtividadesOrdemServicoHelper> iteratorColecaoDadosAtividades = colecaoDadosAtividades.iterator();

			// Atividades
			while(iteratorColecaoDadosAtividades.hasNext()){

				ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = iteratorColecaoDadosAtividades.next();

				// Verifica se o helper contém atividade e ordemServico
				if(manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade() == null
								|| manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getAtividade() == null
								|| manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getOrdemServico() == null){

					throw new ControladorException("erro.sistema", null);
				}else{

					FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
					filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID,
									manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getOrdemServico().getId()));
					filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
					Collection<OrdemServico> colecaoOrdemServico = this.getControladorUtil().pesquisar(filtroOrdemServico,
									OrdemServico.class.getName());

					if(colecaoOrdemServico == null || colecaoOrdemServico.isEmpty()){

						throw new ControladorException("erro.sistema", null);
					}
				}

				// Verifica se o servicoTipo exige especificação de material
				if(tipoServico.getIndicadorMaterial() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){

					if(manterDadosAtividadesOrdemServicoHelper.getColecaoOsAtividadeMaterialExecucao() == null
									|| manterDadosAtividadesOrdemServicoHelper.getColecaoOsAtividadeMaterialExecucao().isEmpty()){

						throw new ControladorException("atencao.informar.material", null);
					}
				}

				// Verifica se o servicoTipo exige especificação de horário
				if(tipoServico.getIndicadorHorariosExecucao() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){

					if(manterDadosAtividadesOrdemServicoHelper.getColecaoOSAtividadePeriodoExecucaoHelper() == null
									|| manterDadosAtividadesOrdemServicoHelper.getColecaoOSAtividadePeriodoExecucaoHelper().isEmpty()){

						throw new ControladorException("atencao.informar.horario", null);
					}
				}
			}
		}
	}

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Serviço
	 * Pesquisa os dados da OrdemServicoAtividade
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServico
	 *            ,
	 *            idAtividade
	 * @throws ErroRepositorioException
	 */
	public OrdemServicoAtividade pesquisarOrdemServicoAtividade(Integer numeroOS, Integer idAtividade) throws ControladorException{

		Object[] arrayOrdemServicoAtividade = null;
		OrdemServicoAtividade retorno = null;

		try{

			arrayOrdemServicoAtividade = repositorioOrdemServico.pesquisarOrdemServicoAtividade(numeroOS, idAtividade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(arrayOrdemServicoAtividade != null){

			OrdemServico ordemServico = new OrdemServico();
			ordemServico.setId(numeroOS);

			Atividade atividade = new Atividade();
			atividade.setId(idAtividade);

			retorno = new OrdemServicoAtividade();

			retorno.setId((Integer) arrayOrdemServicoAtividade[0]);
			retorno.setOrdemServico(ordemServico);
			retorno.setAtividade(atividade);
			retorno.setUltimaAlteracao((Date) arrayOrdemServicoAtividade[1]);

		}

		return retorno;
	}

	/**
	 * [UC0461] - Manter Dados das Atividades da Ordem de Serviço
	 * Pesquisa os dados da OsAtividadeMaterialExecucao associada à
	 * OrdemServicoAtividade para a data informada
	 * 
	 * @author Raphael Rossiter
	 * @date 01/11/2006
	 * @param idOrdemServicoAtividade
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOsAtividadeMaterialExecucao(Integer idOrdemServicoAtividade) throws ControladorException{

		Collection colecaoOsAtividadeMaterialExecucao = null;
		Collection<OsAtividadeMaterialExecucao> retorno = new ArrayList();

		try{

			colecaoOsAtividadeMaterialExecucao = repositorioOrdemServico.pesquisarOsAtividadeMaterialExecucao(idOrdemServicoAtividade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoOsAtividadeMaterialExecucao != null && !colecaoOsAtividadeMaterialExecucao.isEmpty()){

			Iterator osAtividadeMaterialExecucaoIterator = colecaoOsAtividadeMaterialExecucao.iterator();
			Object[] arrayOsAtividadeMaterialExecucao = null;
			OsAtividadeMaterialExecucao osAtividadeMaterialExecucao = null;

			OrdemServicoAtividade ordemServicoAtividade = new OrdemServicoAtividade();
			ordemServicoAtividade.setId(idOrdemServicoAtividade);

			while(osAtividadeMaterialExecucaoIterator.hasNext()){

				osAtividadeMaterialExecucao = new OsAtividadeMaterialExecucao();

				osAtividadeMaterialExecucao.setOrdemServicoAtividade(ordemServicoAtividade);

				arrayOsAtividadeMaterialExecucao = (Object[]) osAtividadeMaterialExecucaoIterator.next();

				osAtividadeMaterialExecucao.setId((Integer) arrayOsAtividadeMaterialExecucao[0]);

				if(arrayOsAtividadeMaterialExecucao[1] != null){
					osAtividadeMaterialExecucao.setQuantidadeMaterial((BigDecimal) arrayOsAtividadeMaterialExecucao[1]);
				}

				osAtividadeMaterialExecucao.setMaterial((Material) arrayOsAtividadeMaterialExecucao[2]);
				osAtividadeMaterialExecucao.setUltimaAlteracao((Date) arrayOsAtividadeMaterialExecucao[3]);

				retorno.add(osAtividadeMaterialExecucao);
			}
		}

		return retorno;
	}

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @param tipoOrdenacao
	 * @return Collection
	 */
	public Collection pesquisarOSGerarRelatorioAcompanhamentoExecucao(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ControladorException{

		Collection colecaoDadosOS = null;
		Collection<OSRelatorioAcompanhamentoExecucaoHelper> retorno = new ArrayList();

		try{

			colecaoDadosOS = repositorioOrdemServico.pesquisarOSGerarRelatorioAcompanhamentoExecucao(origemServico, situacaoOS,
							idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, tipoOrdenacao, idLocalidade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosOS != null && !colecaoDadosOS.isEmpty()){

			Iterator colecaoDadosOSIterator = colecaoDadosOS.iterator();

			Object[] dadosOS = null;

			OSRelatorioAcompanhamentoExecucaoHelper osRelatorioAcompanhamentoExecucaoHelper = null;

			while(colecaoDadosOSIterator.hasNext()){

				osRelatorioAcompanhamentoExecucaoHelper = new OSRelatorioAcompanhamentoExecucaoHelper();

				dadosOS = (Object[]) colecaoDadosOSIterator.next();

				// Id da OS
				if(dadosOS[0] != null){ // 0
					osRelatorioAcompanhamentoExecucaoHelper.setIdOrdemServico((Integer) dadosOS[0]);
				}

				// Situação da OS
				if(dadosOS[1] != null){ // 1
					if(dadosOS[1].equals(OrdemServico.SITUACAO_ENCERRADO)){
						osRelatorioAcompanhamentoExecucaoHelper.setSituacaoOS("ENCERRADA");
					}else{
						osRelatorioAcompanhamentoExecucaoHelper.setSituacaoOS("PENDENTE");
					}
				}

				// Id do Tipo de Serviço
				if(dadosOS[2] != null){ // 2
					osRelatorioAcompanhamentoExecucaoHelper.setIdServicoTipo((Integer) dadosOS[2]);
				}

				// Desrição do Tipo de Serviço
				if(dadosOS[3] != null){ // 3
					osRelatorioAcompanhamentoExecucaoHelper.setDescricaoServicoTipo((String) dadosOS[3]);
				}

				// Id do RA
				if(dadosOS[4] != null){ // 4
					osRelatorioAcompanhamentoExecucaoHelper.setIdRegistroAtendimento((Integer) dadosOS[4]);
				}

				// Data da Solicitação
				if(dadosOS[5] != null){ // 5
					osRelatorioAcompanhamentoExecucaoHelper.setDataSolicitacao((Date) dadosOS[5]);
				}

				// Data de Encerramento
				if(dadosOS[6] != null){ // 6
					osRelatorioAcompanhamentoExecucaoHelper.setDataEncerramento((Date) dadosOS[6]);
				}

				// Data da Programação
				if(dadosOS[7] != null){ // 7
					if(dadosOS[12] != null && dadosOS[13] != null){
						Short indicadorAtivo = (Short) dadosOS[12];
						Short indicadorEquipePrincipal = (Short) dadosOS[13];
						if(indicadorAtivo.equals(OrdemServicoProgramacao.INDICADOR_ATIVO)
										&& indicadorEquipePrincipal.equals(OrdemServicoProgramacao.EQUIPE_PRINCIPAL)){
							osRelatorioAcompanhamentoExecucaoHelper.setDataProgramacao((Date) dadosOS[7]);
						}
					}
				}

				// Id da Unidade de Atendimento(Origem)
				if(dadosOS[8] != null){ // 8
					osRelatorioAcompanhamentoExecucaoHelper.setIdUnidadeAtendimento((Integer) dadosOS[8]);
				}

				// Nome da Unidade de Atendimento(Origem)
				if(dadosOS[9] != null){ // 9
					osRelatorioAcompanhamentoExecucaoHelper.setNomeUnidadeAtendimento((String) dadosOS[9]);
				}

				// Data de Encerramento do RA
				if(dadosOS[10] != null){ // 10
					osRelatorioAcompanhamentoExecucaoHelper.setDataEncerramentoRA((Date) dadosOS[10]);
				}

				// Dias de Prazo
				if(dadosOS[11] != null){ // 11
					osRelatorioAcompanhamentoExecucaoHelper.setHorasPrazo((Integer) dadosOS[11]);
				}

				// Matrícula
				if(dadosOS[14] != null){ // 11
					osRelatorioAcompanhamentoExecucaoHelper.setIdImovel((Integer) dadosOS[14]);
				}

				// Endereço
				String endereco = this.obterEnderecoAbreviadoOS(osRelatorioAcompanhamentoExecucaoHelper.getIdOrdemServico());

				osRelatorioAcompanhamentoExecucaoHelper.setEndereco(endereco);

				// Equipe
				Equipe equipePrincipal = null;

				if(osRelatorioAcompanhamentoExecucaoHelper.getSituacaoOS().equalsIgnoreCase("ENCERRADA")){

					try{

						equipePrincipal = this.repositorioOrdemServico
										.pesquisarEquipePrincipalOSExecucaoEquipe(osRelatorioAcompanhamentoExecucaoHelper
														.getIdOrdemServico());

						if(equipePrincipal == null){
							equipePrincipal = this.repositorioOrdemServico
											.pesquisarEquipePrincipalOSProgramacao(osRelatorioAcompanhamentoExecucaoHelper
															.getIdOrdemServico());
						}

					}catch(ErroRepositorioException e){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", e);
					}

				}else{

					try{

						equipePrincipal = this.repositorioOrdemServico
										.pesquisarEquipePrincipalOSProgramacao(osRelatorioAcompanhamentoExecucaoHelper.getIdOrdemServico());

					}catch(ErroRepositorioException e){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", e);
					}

				}

				if(equipePrincipal != null){

					osRelatorioAcompanhamentoExecucaoHelper.setNomeEquipe(equipePrincipal.getNome());

				}

				// Unidade Atual
				UnidadeOrganizacional unidadeAtual = getControladorRegistroAtendimento().obterUnidadeAtualRA(
								osRelatorioAcompanhamentoExecucaoHelper.getIdRegistroAtendimento());

				osRelatorioAcompanhamentoExecucaoHelper.setNomeUnidadeAtual(unidadeAtual.getDescricao());

				if(!retorno.contains(osRelatorioAcompanhamentoExecucaoHelper)){
					retorno.add(osRelatorioAcompanhamentoExecucaoHelper);
				}
			}
		}

		if(tipoOrdenacao != null && tipoOrdenacao.equals("2")){

			// Organizar a coleção

			Collections.sort((List) retorno, new Comparator() {

				public int compare(Object a, Object b){

					Integer tipoServico1 = ((OSRelatorioAcompanhamentoExecucaoHelper) a).getIdServicoTipo();
					Integer tipoServico2 = ((OSRelatorioAcompanhamentoExecucaoHelper) b).getIdServicoTipo();

					if(!tipoServico1.equals(tipoServico2)){
						return tipoServico1.compareTo(tipoServico2);
					}else{

						String endereco1 = ((OSRelatorioAcompanhamentoExecucaoHelper) a).getEndereco();
						String endereco2 = ((OSRelatorioAcompanhamentoExecucaoHelper) b).getEndereco();

						return endereco1.compareTo(endereco2);

					}
				}
			});

		}

		return retorno;
	}

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relatório Acompanhamento de Execução de Ordem de Serviço
	 * 
	 * @author Rafael Corrêa
	 * @date 01/11/06
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return int
	 */
	public int pesquisarOSGerarRelatorioAcompanhamentoExecucaoCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException{

		int retorno = 0;

		try{

			retorno = repositorioOrdemServico.pesquisarOSGerarRelatorioAcompanhamentoExecucaoCount(origemServico, situacaoOS,
							idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, idLocalidade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;

	}

	/**
	 * Pesquisa as equipes de acordo com os parâmetros informado pelo usuário
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corrêa
	 * @date 09/11/06
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @param numeroPagina
	 * @return Collection
	 */
	public Collection pesquisarEquipes(String idEquipe, String nome, String placa, String cargaTrabalho, String idUnidade,
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idEquipeTipo, Integer numeroPagina)
					throws ControladorException{

		Collection retorno = null;

		try{

			retorno = repositorioOrdemServico.pesquisarEquipes(idEquipe, nome, placa, cargaTrabalho, idUnidade, idFuncionario,
							idPerfilServico, indicadorUso, idEquipeTipo, numeroPagina);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;

	}

	/**
	 * Verifica a quantidade de registros retornados da pesquisa de equipe
	 * [UC0370] - Filtrar Equipe
	 * 
	 * @author Rafael Corrêa
	 * @date 09/11/06
	 * @param idEquipe
	 * @param nome
	 * @param placa
	 * @param cargaTrabalho
	 * @param idUnidade
	 * @param idFuncionario
	 * @param idPerfilServico
	 * @param indicadorUso
	 * @return int
	 */
	public int pesquisarEquipesCount(String idEquipe, String nome, String placa, String cargaTrabalho, String idUnidade,
					String idFuncionario, String idPerfilServico, String indicadorUso, Integer idEquipeTipo) throws ControladorException{

		int retorno = 0;

		try{

			retorno = repositorioOrdemServico.pesquisarEquipesCount(idEquipe, nome, placa, cargaTrabalho, idUnidade, idFuncionario,
							idPerfilServico, indicadorUso, idEquipeTipo);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;

	}

	/**
	 * Remove as equipes selecionadas pelo usuário e as equipes componentes
	 * associadas a ela
	 * [UC0372] - Manter Equipe
	 * 
	 * @author Rafael Corrêa
	 * @date 09/11/06
	 * @param idsEquipes
	 * @throws ControladorException
	 */
	public void removerEquipes(String[] idsEquipes, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EQUIPE_REMOVER, new UsuarioAcaoUsuarioHelper(
						usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EQUIPE_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		if(idsEquipes != null && idsEquipes.length > 0){
			for(int i = 0; i < idsEquipes.length; i++){
				String idEquipe = idsEquipes[i];

				// Cria o filtro de equipe para verificar se a equipe ja foi
				// removida
				FiltroEquipe filtroEquipe = new FiltroEquipe();

				filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, idEquipe));

				Collection colecaoEquipe = getControladorUtil().pesquisar(filtroEquipe, Equipe.class.getName());

				if(colecaoEquipe != null && !colecaoEquipe.isEmpty()){

					Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);

					FiltroEquipeComponentes filtroEquipeComponentes = new FiltroEquipeComponentes();

					filtroEquipeComponentes.adicionarParametro(new ParametroSimples(FiltroEquipeComponentes.ID_EQUIPE, idEquipe));

					Collection colecaoEquipeComponentes = getControladorUtil().pesquisar(filtroEquipeComponentes,
									EquipeComponentes.class.getName());

					if(colecaoEquipeComponentes != null && !colecaoEquipeComponentes.isEmpty()){
						Iterator colecaoEquipeComponentesIterator = colecaoEquipeComponentes.iterator();

						while(colecaoEquipeComponentesIterator.hasNext()){

							EquipeComponentes equipeComponentes = (EquipeComponentes) colecaoEquipeComponentesIterator.next();

							// ------------ REGISTRAR TRANSAÇÃO ----------------
							equipeComponentes.setOperacaoEfetuada(operacaoEfetuada);
							equipeComponentes.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
							registradorOperacao.registrarOperacao(equipeComponentes);
							// ------------ REGISTRAR TRANSAÇÃO ----------------

							getControladorUtil().remover(equipeComponentes);

						}

					}

					// ------------ REGISTRAR TRANSAÇÃO ----------------
					equipe.setOperacaoEfetuada(operacaoEfetuada);
					equipe.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(equipe);
					// ------------ REGISTRAR TRANSAÇÃO ----------------

					getControladorUtil().remover(equipe);

				}else{
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.registro_remocao_nao_existente");
				}

			}
		}
	}

	/**
	 * Valida a ordem de serviço
	 * [UC0488] - Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 01/11/06
	 * @return Integer
	 */
	public void validarOrdemServico(Integer idOrdemServico) throws ControladorException{

		Object[] parmsOS = null;

		try{

			parmsOS = repositorioOrdemServico.pesquisarServicoTipoComFiscalizacaoInfracao(idOrdemServico);

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}
		if(parmsOS != null){
			String descricaoOS = null;
			Short situacaoOS = null;
			Integer idCobrancaDocumento = null;
			if(parmsOS[0] != null){
				descricaoOS = (String) parmsOS[0];
			}
			if(parmsOS[1] != null){
				situacaoOS = (Short) parmsOS[1];
			}
			if(parmsOS[2] != null){
				idCobrancaDocumento = (Integer) parmsOS[2];
			}

			if(situacaoOS != null && !situacaoOS.equals(OrdemServico.SITUACAO_PENDENTE)){
				throw new ControladorException("atencao.os_encerrada", null, descricaoOS);
			}

			if(idCobrancaDocumento == null || idCobrancaDocumento.equals("")){
				throw new ControladorException("atencao.os_nao_documento_cobranca");
			}

		}else{
			throw new ControladorException("atencao.os_sem_fiscalizacao_infracao");
		}

	}

	/**
	 * [UC0367] Informar Retorno Ordem de Fiscalização
	 * Recupera os parámetros necessários da OS
	 * 
	 * @author Sávio Luiz
	 * @date 24/08/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsOS(Integer idOrdemServico) throws ControladorException{

		Object[] parmsOS = null;

		try{

			parmsOS = repositorioOrdemServico.pesquisarParmsOS(idOrdemServico);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return parmsOS;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * Preparação dos dados para geração de RA
	 * 
	 * @author Raphael Rossiter
	 * @date 30/01/2007
	 * @param idImovel
	 *            ,
	 *            idSolicitacaoTipoEspecificacao, idSolicitacaoTipo,
	 *            idServicoTipo, idUsuarioLogado
	 * @return Integer[]
	 * @throws ControladorException
	 */
	public Integer[] informarRetornoOSFiscalizacaoGerarRA(Integer idImovel, Integer idSolicitacaoTipoEspecificacao,
					Integer idSolicitacaoTipo, Usuario usuarioLogado) throws ControladorException{

		Date dataAtual = new Date();
		String dataAtendimento = Util.formatarData(dataAtual);
		String horaAtendimento = Util.formatarHoraSemSegundos(dataAtual);

		DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper dataPrevistaUnidadeDestino = getControladorRegistroAtendimento()
						.definirDataPrevistaUnidadeDestinoEspecificacao(dataAtual, idSolicitacaoTipoEspecificacao);

		String dataPrevista = null;
		if(dataPrevistaUnidadeDestino.getDataPrevista() != null){

			dataPrevista = Util.formatarDataComHora(dataPrevistaUnidadeDestino.getDataPrevista());
		}

		String parecerUnidadeDestino = null;
		Integer idUnidadeDestino = null;
		if(dataPrevistaUnidadeDestino.getUnidadeOrganizacional() != null){

			idUnidadeDestino = dataPrevistaUnidadeDestino.getUnidadeOrganizacional().getId();
			parecerUnidadeDestino = "RA GERADA A PARTIR DA FISCALIZAÇÃO - (AUTO-INFRAÇÃO)";
		}

		Collection colecaoEndereco = new ArrayList();
		Imovel imovelEndereco = getControladorEndereco().pesquisarImovelParaEndereco(idImovel);
		colecaoEndereco.add(imovelEndereco);

		Imovel imovelCarregado = getControladorImovel().pesquisarImovelDigitado(idImovel);

		Cliente clienteUsuario = getControladorImovel().pesquisarClienteUsuarioImovel(idImovel);

		Collection<Integer> colecaoIdServicoTipo = null;

		if(idSolicitacaoTipoEspecificacao != null){
			colecaoIdServicoTipo = this.consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(idSolicitacaoTipoEspecificacao);
		}

		// Campos inseridos através da tela na funcionalidade Inserir RA
		String indicadorProcessoAdmJud = ConstantesSistema.NAO.toString();
		String numeroProcessoAgencia = null;

		// [UC0366] - Inserir Registro de Atendimento
		Integer[] idsGerados = getControladorRegistroAtendimento().inserirRegistroAtendimento(
						RegistroAtendimento.INDICADOR_ATENDIMENTO_ON_LINE, dataAtendimento, horaAtendimento, null, null,
						MeioSolicitacao.INTERNO, null, idSolicitacaoTipoEspecificacao, dataPrevista, null, idImovel, null,
						idSolicitacaoTipo, colecaoEndereco, null, null, imovelCarregado.getLocalidade().getId(),
						imovelCarregado.getSetorComercial().getId(), imovelCarregado.getQuadra().getId(), null, null, null, null,
						usuarioLogado.getUnidadeOrganizacional().getId(), usuarioLogado.getId(), clienteUsuario.getId(), null, null, false,
						null, null, null, null, idUnidadeDestino, parecerUnidadeDestino, colecaoIdServicoTipo, null, null, null, null,
						getControladorRegistroAtendimento().obterSequenceRA(), null, null, null, null, null, null, null, null, null, null,
						indicadorProcessoAdmJud, numeroProcessoAgencia, null);

		/*
		 * inserirRegistroAtendimento(short indicadorAtendimentoOnLine, String
		 * dataAtendimento, String horaAtendimento, String tempoEsperaInicial,
		 * String tempoEsperaFinal, Integer idMeioSolicitacao, Integer
		 * idSolicitacaoTipoEspecificacao, String dataPrevista, String
		 * observacao, Integer idImovel, String descricaoLocalOcorrencia,
		 * Integer idSolicitacaoTipo, Collection colecaoEndereco, String
		 * pontoReferenciaLocalOcorrencia, Integer idBairroArea, Integer
		 * idLocalidade, Integer idSetorComercial, Integer idQuadra, Integer
		 * idDivisaoEsgoto, Integer idLocalOcorrencia, Integer idPavimentoRua,
		 * Integer idPavimentoCalcada, Integer idUnidadeAtendimento, Integer
		 * idUsuarioLogado, Integer idCliente, String
		 * pontoReferenciaSolicitante, String nomeSolicitante, boolean
		 * novoSolicitante, Integer idUnidadeSolicitante, Integer idFuncionario,
		 * Collection colecaoFone, Collection colecaoEnderecoSolicitante,
		 * Integer idUnidadeDestino, String parecerUnidadeDestino, Integer
		 * idServicoTipo, String numeroRAManual)
		 */

		return idsGerados;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Integer[] informarRetornoOSFiscalizacao(Integer idOrdemServico, FiscalizacaoSituacao fiscalizacaoSituacao,
					String indicadorDocumentoEntregue, Integer idLigacaoAguaSituacaoImovel, Integer idLigacaoEsgotoSituacaoImovel,
					Integer idImovel, String indicadorMedicaoTipo, String indicadorGeracaoDebito, Usuario usuarioLogado)
					throws ControladorException{

		Integer[] retorno = new Integer[5];
		retorno[0] = idOrdemServico;

		// caso não tenha entrado em alguma condição que finalize o caso de uso
		// então continua o fluxo
		boolean finalizador = false;
		Integer consumoMedioMedido = 0;
		Integer maiorConsumoMedido = 0;
		Integer consumoMedioNaoMedido = 0;

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		try{

			// 5.1. verificar se é necessário validar a situação da ligação de
			// água do imóvel fiscalizado.
			if(fiscalizacaoSituacao.getIndicadorAguaSituacao() == FiscalizacaoSituacao.INDICADOR_SIM){
				// 5.1.1. caso não exista situação de ligação de agua do imóvel

				// [0] = idLigacaoAguaSituacao
				// [1] = ligacaoAguaSituacaoByLastIdnova.id
				// [2] = solicitacaoTipoEspecificacao.id
				// [3] = solicitacaoTipo.id

				Object[] fiscalizacaoSituacaoAgua = repositorioOrdemServico.pesquisarIdFiscalizacaoSituacaoAgua(
								idLigacaoAguaSituacaoImovel, fiscalizacaoSituacao.getId());

				if(fiscalizacaoSituacaoAgua == null){

					FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
					filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
									idLigacaoAguaSituacaoImovel));

					Collection colecaoLigacaoAguaSituacao = getControladorUtil().pesquisar(filtroLigacaoAguaSituacao,
									LigacaoAguaSituacao.class.getName());

					LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util.retonarObjetoDeColecao(colecaoLigacaoAguaSituacao);

					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.situacao.agua.incompativel", null, ligacaoAguaSituacao.getDescricao(),
									fiscalizacaoSituacao.getDescricaoFiscalizacaoSituacao());

					// [SB0001 - Atualizar Ordem de Serviço]
					// atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao
					// .getId(), indicadorDocumentoEntregue);
					// finalizador = true;
				}else{

					/*
					 * 5.1.2.2. O sistema deve verificar se é para gerar um RA
					 * para o imóvel associado ao documento de cobrança (STEP_ID
					 * da tabela FISCALIZACAO_SITUACAO_AGUA com valor diferente
					 * de nulo)
					 */
					if(fiscalizacaoSituacaoAgua[2] != null){

						/*
						 * [UC0366] - Inserir Registro de Atendimento, passando
						 * o imóvel e a especificação informada.
						 */
						Integer[] idsRAAgua = this.informarRetornoOSFiscalizacaoGerarRA(idImovel, (Integer) fiscalizacaoSituacaoAgua[2],
										(Integer) fiscalizacaoSituacaoAgua[3], usuarioLogado);

						if(idsRAAgua.length > 1){
							retorno[1] = idsRAAgua[0];
							retorno[3] = idsRAAgua[1];
						}else{
							retorno[1] = idsRAAgua[0];
						}
					}
					/*
					 * 5.1.2.1. O sistema deve verificar se é para atualizar a
					 * situação da ligação de água do imóvel [SB0002 - Atualizar
					 * Imovel/Ligação de Água]
					 */

					else if(fiscalizacaoSituacaoAgua[1] != null){

						atualizarImovelLigacaoAgua(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue, idImovel, 1,
										idLigacaoAguaSituacaoImovel, idLigacaoEsgotoSituacaoImovel);
					}

				}
			}

			if(!finalizador){
				// 5.2. verificar se é necessário validar a situação da ligação de esgoto do imóvel
				// fiscalizado.
				if(fiscalizacaoSituacao.getIndicadorEsgotoSituacao() == FiscalizacaoSituacao.INDICADOR_SIM){
					// 5.2.1. caso não exista situação de ligação de agua do
					// imóvel

					// [0] = idLigacaoEsgotoSituacao
					// [1] = ligacaoAguaSituacaoByLastIdnova.id
					// [2] = solicitacaoTipoEspecificacao.id
					// [3] = solicitacaoTipo.id

					Object[] fiscalizacaoSituacaoEsgoto = repositorioOrdemServico.pesquisarIdFiscalizacaoSituacaoEsgoto(
									idLigacaoEsgotoSituacaoImovel, fiscalizacaoSituacao.getId());

					if(fiscalizacaoSituacaoEsgoto == null){
						// [SB0001 - Atualizar Ordem de Serviço]
						atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue);
						finalizador = true;
					}else{

						/*
						 * 5.1.2.2. O sistema deve verificar se é para gerar um RA para o imóvel
						 * associado ao documento de cobrança (STEP_ID da tabela
						 * FISCALIZACAO_SITUACAO_AGUA com valor diferente de nulo)
						 */
						if(fiscalizacaoSituacaoEsgoto[2] != null){

							/*
							 * [UC0366] - Inserir Registro de Atendimento,
							 * passando o imóvel e a especificação informada.
							 */
							Integer[] idsRAEsgoto = this
											.informarRetornoOSFiscalizacaoGerarRA(idImovel, (Integer) fiscalizacaoSituacaoEsgoto[2],
															(Integer) fiscalizacaoSituacaoEsgoto[3], usuarioLogado);

							if(idsRAEsgoto.length > 1){
								retorno[2] = idsRAEsgoto[0];
								retorno[4] = idsRAEsgoto[1];
							}else{
								retorno[2] = idsRAEsgoto[0];
							}

						}
						/*
						 * 5.1.2.1. O sistema deve verificar se é para atualizar
						 * a situação da ligação de água do imóvel [SB0002 -
						 * Atualizar Imovel/Ligação de Água]
						 */
						else if(fiscalizacaoSituacaoEsgoto[1] != null){

							atualizarImovelLigacaoAgua(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue, idImovel,
											1, idLigacaoAguaSituacaoImovel, idLigacaoEsgotoSituacaoImovel);
						}

					}
				}
			}

			if(!finalizador){
				// 5.3. verificar se é necessário verificar se o imóvel é medido.
				if(fiscalizacaoSituacao.getIndicadorMedido() == FiscalizacaoSituacao.INDICADOR_SIM){
					boolean existeHidrometro = getControladorAtendimentoPublico().verificarExistenciaHidrometroEmLigacaoAgua(idImovel);
					// 5.3.1. caso o imóvel não seja medido
					if(!existeHidrometro){
						// [SB0001 - Atualizar Ordem de Serviço]
						atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue);
						finalizador = true;
					}
				}
			}

			if(!finalizador){
				// 5.4. verificar se é necessário validar a capacidade do hidrometro.
				if(fiscalizacaoSituacao.getIndicadorHidrometroCapacidade() == FiscalizacaoSituacao.INDICADOR_SIM){
					// 5.4.1. caso a opção de medição igual a "Ligação de Água"
					if(indicadorMedicaoTipo != null && indicadorMedicaoTipo.equals("1")){
						boolean existeHidrometro = getControladorAtendimentoPublico().verificarExistenciaHidrometroEmLigacaoAgua(idImovel);
						// 5.4.1.1. caso não exista hidrometro na ligação água
						if(!existeHidrometro){
							// [SB0001 - Atualizar Ordem de Serviço]
							atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue);
							finalizador = true;
						}else{
							// 5.4.1.2. caso exista a capacidade do hidrômetro instalado na ligação
							// de água
							Integer idHidrometroCapacidadeFiscalizacao = repositorioOrdemServico
											.pesquisarHidormetroCapacidadeFiscalizacaoAgua(idImovel);
							if(idHidrometroCapacidadeFiscalizacao == null || idHidrometroCapacidadeFiscalizacao.equals("")){
								// [SB0001 - Atualizar Ordem de Serviço]
								atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue);
								finalizador = true;
							}
						}
					}else{
						// 5.4.2. Caso a opção de medição igual a "Poço"
						if(indicadorMedicaoTipo != null && indicadorMedicaoTipo.equals("2")){
							boolean existeHidrometro = getControladorAtendimentoPublico().verificarExistenciaHidrometroEmImovel(idImovel);
							// 5.4.2.1. caso não exista hidrometro no imóvel
							if(!existeHidrometro){
								// [SB0001 - Atualizar Ordem de Serviço]
								atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue);
								finalizador = true;
							}else{
								// 5.4.2.2. caso exista a capacidade do hidrômetro instalado na
								// ligação de água
								Integer idHidrometroCapacidadeFiscalizacao = repositorioOrdemServico
												.pesquisarHidormetroCapacidadeFiscalizacaoPoco(idImovel);
								if(idHidrometroCapacidadeFiscalizacao == null || idHidrometroCapacidadeFiscalizacao.equals("")){
									// [SB0001 - Atualizar Ordem de Serviço]
									atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue);
									finalizador = true;
								}
							}
						}
					}
				}
			}

			if(!finalizador){
				// 6. O sistema deverá gerar os valores de consumo do imóvel
				boolean existeHidrometro = getControladorAtendimentoPublico().verificarExistenciaHidrometroEmLigacaoAgua(idImovel);
				if(existeHidrometro){
					// 6.1. Caso o imóvel tenha consumo medido

					// 6.1.1.1 Caso a opção de medição seja "Ligação Água"
					if(indicadorMedicaoTipo != null && indicadorMedicaoTipo.equals("1")){
						consumoMedioMedido = getControladorMicromedicao().pesquisarConsumoMedio(idImovel, LigacaoTipo.LIGACAO_AGUA);
					}else{
						// 6.1.1.2 Caso contrário a opção de medição seja "Poço"
						if(indicadorMedicaoTipo != null && indicadorMedicaoTipo.equals("2")){
							consumoMedioMedido = getControladorMicromedicao().pesquisarConsumoMedio(idImovel, LigacaoTipo.LIGACAO_ESGOTO);
						}
					}
					// 6.1.2 O sistema deverá obter o maior consumo medido do imóvel até a
					// quantidade de meses definida no sistema parametros

					short qtdMeses = sistemaParametro.getMesesMediaConsumo();
					// 6.1.2.1 Caso a opção de medição seja "Ligação Água"
					if(indicadorMedicaoTipo != null && indicadorMedicaoTipo.equals("1")){

						maiorConsumoMedido = getControladorMicromedicao().pesquisarMaiorConsumoFaturadoQuantidadeMeses(idImovel,
										LigacaoTipo.LIGACAO_AGUA, qtdMeses);
					}else{
						// 6.1.2.2 Caso contrário a opção de medição seja "Poço"
						if(indicadorMedicaoTipo != null && indicadorMedicaoTipo.equals("2")){
							maiorConsumoMedido = getControladorMicromedicao().pesquisarMaiorConsumoFaturadoQuantidadeMeses(idImovel,
											LigacaoTipo.LIGACAO_ESGOTO, qtdMeses);
						}
					}
				}

				if(consumoMedioMedido == null){
					consumoMedioMedido = 0;
				}

				if(maiorConsumoMedido == null){
					maiorConsumoMedido = 0;
				}

				/*
				 * 6.2 = 6.1.3. O Sistema deverá obter o consumo médio como o não medido para o
				 * imóvel
				 */
				Imovel imovel = new Imovel();

				if(idImovel != null){
					imovel.setId(idImovel);
				}

				Integer idConsumoTarifa = getControladorImovel().recuperarIdConsumoTarifa(idImovel);

				ConsumoTarifa consumoTarifa = new ConsumoTarifa();
				if(idConsumoTarifa != null){
					consumoTarifa.setId(idConsumoTarifa);
				}

				imovel.setConsumoTarifa(consumoTarifa);

				/*
				 * Comentado por Raphael Rossiter em 06/03/2007 (Analista: Rosana)
				 * consumoMedioNaoMedido = getControladorMicromedicao()
				 * .obterConsumoMinimoLigacao(imovel, null);
				 */

				// Colocado por Raphael Rossiter em 06/03/2007
				consumoMedioNaoMedido = getControladorImovel().obterConsumoMedioNaoMedidoImovel(imovel);

				// 7. caso o usuário tenha selecionado "Sim" para geração de débito
				if(indicadorGeracaoDebito != null && indicadorGeracaoDebito.equals("1")){

					Collection colecaoCodicoConsumoCalculo = repositorioOrdemServico
									.pesquisarFiscalizacaoSituacaoServicoACobrar(fiscalizacaoSituacao.getId());

					if(colecaoCodicoConsumoCalculo != null && !colecaoCodicoConsumoCalculo.isEmpty()){
						Iterator iteCodigoConsumoCalculado = colecaoCodicoConsumoCalculo.iterator();

						while(iteCodigoConsumoCalculado.hasNext()){

							Object[] fiscalizacaoSituacaoServicoACobrar = (Object[]) iteCodigoConsumoCalculado.next();

							Short codigoConsumoCalculado = (Short) fiscalizacaoSituacaoServicoACobrar[0];
							Integer idDebitoTipo = (Integer) fiscalizacaoSituacaoServicoACobrar[1];

							// 7.1.1. Caso o código do calculo de consumo=1
							if(codigoConsumoCalculado != null
											&& codigoConsumoCalculado.equals(FiscalizacaoSituacaoServicoACobrar.CONSUMO_CALCULO_UM)){

								BigDecimal valorDebito = new BigDecimal("0.00");

								// 7.1.1.1 Caso o imóvel tenha consumo medido maior que zero
								if(consumoMedioMedido > 0){
									// [SB0004 - Calcular Valor de Água e/ou Esgoto]
									valorDebito = calcularValorAguaEsgoto(consumoMedioMedido, sistemaParametro, idImovel,
													fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
													idLigacaoEsgotoSituacaoImovel);
								}else{
									// 7.1.1.2 o imóvel tenha consumo medido igual a zero passar o
									// médio não medido
									if(consumoMedioMedido == 0){
										// [SB0004 - Calcular Valor de Água e/ou Esgoto]
										valorDebito = calcularValorAguaEsgoto(consumoMedioNaoMedido, sistemaParametro, idImovel,
														fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
														idLigacaoEsgotoSituacaoImovel);
									}

								}
								// [SB003 - Calcular/Inserir Valor] o sistema deverá inserir o
								// débito a cobrar para o imóvel
								calcularInserirValor(valorDebito, idImovel, fiscalizacaoSituacao.getId(), Integer.valueOf(idOrdemServico),
												idDebitoTipo);

							}

							// 7.1.2. Caso o código do calculo de consumo=2
							if(codigoConsumoCalculado != null
											&& codigoConsumoCalculado.equals(FiscalizacaoSituacaoServicoACobrar.CONSUMO_CALCULO_DOIS)){

								BigDecimal valorDebito = new BigDecimal("0.00");

								// 7.1.2.1 Caso o imóvel tenha consumo
								// medido maior que consumo médio não medido
								// passar o valor do consumo médio médido
								if(consumoMedioMedido >= consumoMedioNaoMedido){
									// [SB0004 - Calcular Valor de Água e/ou
									// Esgoto]
									valorDebito = calcularValorAguaEsgoto(consumoMedioMedido, sistemaParametro, idImovel,
													fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
													idLigacaoEsgotoSituacaoImovel);
								}else{
									// 7.1.2.2 o imóvel tenha consumo medido
									// menor que o consumo medio não medido
									// passar o médio não
									// medido
									if(consumoMedioMedido < consumoMedioNaoMedido){
										// [SB0004 - Calcular Valor de Água
										// e/ou Esgoto]
										valorDebito = calcularValorAguaEsgoto(consumoMedioNaoMedido, sistemaParametro, idImovel,
														fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
														idLigacaoEsgotoSituacaoImovel);
									}
								}

								// [SB0003 - Calcular/Inserir Valor]
								// o sistema deverá inserir o débito a
								// cobrar para o imóvel
								calcularInserirValor(valorDebito, idImovel, fiscalizacaoSituacao.getId(), Integer.valueOf(idOrdemServico),
												idDebitoTipo);

							}
							// 7.1.3. Caso o código do calculo de consumo=3
							if(codigoConsumoCalculado != null
											&& codigoConsumoCalculado.equals(FiscalizacaoSituacaoServicoACobrar.CONSUMO_CALCULO_TRES)){

								BigDecimal maiorValor = new BigDecimal("0.00");

								// 7.1.3.1 Caso o imóvel tenha consumo
								// medido não medido maior que o consumo
								// médio medido
								// passar o valor do consumo merio medido
								if(consumoMedioNaoMedido >= maiorConsumoMedido){
									// [SB0004 - Calcular Valor de Água e/ou Esgoto]
									maiorValor = calcularValorAguaEsgoto(consumoMedioNaoMedido, sistemaParametro, idImovel,
													fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
													idLigacaoEsgotoSituacaoImovel);
								}else{
									// 7.1.3.2 o imóvel tenha o maior consumo medido maior que o
									// consumo medio não medido
									if(maiorConsumoMedido > consumoMedioNaoMedido){
										// [SB0004 - Calcular Valor de Água e/ou Esgoto]
										maiorValor = calcularValorAguaEsgoto(maiorConsumoMedido, sistemaParametro, idImovel,
														fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
														idLigacaoEsgotoSituacaoImovel);
									}
								}

								// 7.1.3.4 o sistema deverá obter o numero de meses de instalação do
								// hidrometro de acordo com a opção de medição
								Short numeroMesesCalculoConsumo = sistemaParametro.getMesesMediaConsumo();
								Date dataInstalacaoHidrometro = null;
								if(indicadorMedicaoTipo != null){
									if(indicadorMedicaoTipo != null && indicadorMedicaoTipo.equals("1")){
										// 7.1.3.4.1 Caso a opção de ligação seja de água
										dataInstalacaoHidrometro = getControladorMicromedicao().pesquisarDataInstalacaoHidrometroAgua(
														idImovel);
									}else{
										// 7.1.3.4.1 Caso a opção de ligação seja poço
										dataInstalacaoHidrometro = getControladorMicromedicao().pesquisarDataInstalacaoHidrometroPoco(
														idImovel);
									}
								}

								/*
								 * int anoMesDataInstalacao = Util
								 * .recuperaAnoMesDaData(dataInstalacaoHidrometro);
								 */

								// Colocado por Raphael Rossiter em 19/01/2007
								int anoMesUltimoConsumoFaturado = getControladorMicromedicao().pesquisarUltimoAnoMesConsumoFaturado(
												idImovel, LigacaoTipo.LIGACAO_AGUA);

								/*
								 * Date dataUltimoConsumoFaturado = Util .criarData( 1, Util
								 * .obterMes(anoMesUltimoConsumoFaturado), Util
								 * .obterAno(anoMesUltimoConsumoFaturado));
								 */

								// calcula a quantidade de meses entra a
								// data de
								// instalação do hidrometro e a data do ultimo
								// consumo faturado
								/*
								 * int quantidadeMeses = Util.dataDiff(
								 * dataInstalacaoHidrometro, new Date());
								 */

								/*
								 * int quantidadeMeses = Util.dataDiff( dataInstalacaoHidrometro,
								 * dataUltimoConsumoFaturado);
								 */

								int quantidadeMeses = Util.dataDiff(dataInstalacaoHidrometro, new Date());

								// se a quantidade de meses for maior que o
								// numero de meses calculado do sistema
								// parametro
								if(quantidadeMeses > numeroMesesCalculoConsumo.intValue()){
									quantidadeMeses = numeroMesesCalculoConsumo.intValue();
								}
								BigDecimal valorAcumulado = new BigDecimal("0.00");
								for(int i = quantidadeMeses; i > 0; i--){

									Integer consumoMes = null;
									int anoMesCalculo = Util.subtrairMesDoAnoMes(anoMesUltimoConsumoFaturado, i - 1);
									if(indicadorMedicaoTipo != null){

										// 7.1.3.5.2.1.1 Caso a opção de ligação seja de água ou
										// poço
										consumoMes = getControladorMicromedicao().pesquisarConsumoFaturaMes(anoMesCalculo,
														Integer.valueOf(indicadorMedicaoTipo), idImovel);

									}

									// 7.1.3.5.2.3 O sistema deve obter o valor de água e de esgoto
									// [SB0004 - Calcular Valor de Água e/ou Esgoto]
									BigDecimal valorAtual = calcularValorAguaEsgoto(consumoMes, sistemaParametro, idImovel,
													fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
													idLigacaoEsgotoSituacaoImovel);
									// 7.1.3.5.2.3 O sistema deverá obter o
									// valor final
									BigDecimal valorDiferenca = maiorValor.subtract(valorAtual);
									valorAcumulado = valorAcumulado.add(valorDiferenca);

								}

								// 7.1.3.6 O sistema deverá inserir o débito
								// a cobrar
								// [SB0003 - Calcular/Inserir Valor]
								// o sistema deverá inserir o débito a
								// cobrar para o imóvel
								calcularInserirValor(valorAcumulado, idImovel, fiscalizacaoSituacao.getId(),
												Integer.valueOf(idOrdemServico), idDebitoTipo);

							}

							// 7.1.4. Caso o código do calculo de consumo=4
							if(codigoConsumoCalculado != null
											&& codigoConsumoCalculado.equals(FiscalizacaoSituacaoServicoACobrar.CONSUMO_CALCULO_QUATRO)){

								Integer idServicoTipo = repositorioOrdemServico.pesquisarIdServicoTipoDaOS(idOrdemServico);
								Short tipoLigacao = null;
								if(indicadorMedicaoTipo != null){
									tipoLigacao = Short.valueOf(indicadorMedicaoTipo);
								}
								// 7.1.4.2 o sistema deverá inserir o
								// débitoa cobrar para o imóvel
								// [UC0475] Obter valor do débito

								BigDecimal valorDebito = getControladorAtendimentoPublico().obterValorDebito(idServicoTipo, idImovel,
												tipoLigacao);

								if(valorDebito != null && valorDebito.compareTo(new BigDecimal("0.00")) == 1){

									// [SB003 - Calcular/Inserir Valor]
									// o sistema deverá inserir o débito a
									// cobrar para o imóvel
									calcularInserirValor(valorDebito, idImovel, fiscalizacaoSituacao.getId(),
													Integer.valueOf(idOrdemServico), idDebitoTipo);
								}
							}

							// 7.1.5. Caso o código do calculo de consumo=5
							if(codigoConsumoCalculado != null
											&& codigoConsumoCalculado.equals(FiscalizacaoSituacaoServicoACobrar.CONSUMO_CALCULO_CINCO)){

								BigDecimal valorDebito = new BigDecimal("0.00");

								// 7.1.5.1 Caso o imóvel tenha consumo
								// médio medido maior ou igual que consumo médio
								// não medido
								// passar o valor do consumo médio médido
								if(consumoMedioMedido >= consumoMedioNaoMedido){
									// [SB0004 - Calcular Valor de Água e/ou
									// Esgoto]
									valorDebito = calcularValorAguaEsgoto(consumoMedioMedido, sistemaParametro, idImovel,
													fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
													idLigacaoEsgotoSituacaoImovel);
								}else{
									// 7.1.5.2 O imóvel tenha consumo médio
									// medido
									// menor que o consumo medio não medido
									// passar o consumo médio não medido
									if(consumoMedioMedido < consumoMedioNaoMedido){
										// [SB0004 - Calcular Valor de Água
										// e/ou Esgoto]
										valorDebito = calcularValorAguaEsgoto(consumoMedioNaoMedido, sistemaParametro, idImovel,
														fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
														idLigacaoEsgotoSituacaoImovel);
									}
								}

								/*
								 * 7.1.8 - O sistema deverá aplicar o fator
								 * obtido no valor calculado e inserir o débito
								 * a cobrar para o imóvel [SB0003 -
								 * Calcular/Inserir Valor].
								 */
								Short fatorMultiplicacaoAgua = this.obterFatorMultiplicacaoParaFiscalizacaoAgua(
												idLigacaoAguaSituacaoImovel, fiscalizacaoSituacao.getId(), idImovel);

								valorDebito = valorDebito.multiply(new BigDecimal(fatorMultiplicacaoAgua.toString()));

								// [SB003 - Calcular/Inserir Valor]
								// o sistema deverá inserir o débito a
								// cobrar para o imóvel
								calcularInserirValor(valorDebito, idImovel, fiscalizacaoSituacao.getId(), Integer.valueOf(idOrdemServico),
												idDebitoTipo);
							}

							// 7.1.6. Caso o código do calculo de consumo=6
							if(codigoConsumoCalculado != null
											&& codigoConsumoCalculado.equals(FiscalizacaoSituacaoServicoACobrar.CONSUMO_CALCULO_SEIS)){
								BigDecimal valorDebito = new BigDecimal("0.00");

								// 7.1.6.1 Caso o imóvel tenha consumo
								// médio medido maior ou igual que consumo médio
								// não medido
								// passar o valor do consumo médio médido
								if(consumoMedioMedido >= consumoMedioNaoMedido){
									// [SB0004 - Calcular Valor de Água e/ou
									// Esgoto]
									valorDebito = calcularValorAguaEsgoto(consumoMedioMedido, sistemaParametro, idImovel,
													fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
													idLigacaoEsgotoSituacaoImovel);
								}else{
									// 7.1.6.2 O imóvel tenha consumo médio
									// medido
									// menor que o consumo medio não medido
									// passar o consumo médio não medido
									if(consumoMedioMedido < consumoMedioNaoMedido){
										// [SB0004 - Calcular Valor de Água
										// e/ou Esgoto]
										valorDebito = calcularValorAguaEsgoto(consumoMedioNaoMedido, sistemaParametro, idImovel,
														fiscalizacaoSituacao.getId(), idLigacaoAguaSituacaoImovel,
														idLigacaoEsgotoSituacaoImovel);
									}
								}

								/*
								 * 7.1.8 - O sistema deverá aplicar o fator
								 * obtido no valor calculado e inserir o débito
								 * a cobrar para o imóvel [SB0003 -
								 * Calcular/Inserir Valor].
								 */
								Short fatorMultiplicacaoEsgoto = this.obterFatorMultiplicacaoParaFiscalizacaoEsgoto(
												idLigacaoEsgotoSituacaoImovel, fiscalizacaoSituacao.getId(), idImovel);

								valorDebito = valorDebito.multiply(new BigDecimal(fatorMultiplicacaoEsgoto.toString()));

								// [SB003 - Calcular/Inserir Valor]
								// o sistema deverá inserir o débito a
								// cobrar para o imóvel
								calcularInserirValor(valorDebito, idImovel, fiscalizacaoSituacao.getId(), Integer.valueOf(idOrdemServico),
												idDebitoTipo);
							}
						}
					}

				}

				// 8. o sistema deverá inserir a ordem de serviço de
				// fiscalização
				// [SB0001 - Atualizar Ordem de Serviço]
				atualizarOrdemServico(idOrdemServico, fiscalizacaoSituacao.getId(), indicadorDocumentoEntregue);

				// Comentado por Raphael Rossiter em 20/02/2007
				// [SB0002 - Atualizar Imovel/Ligação de Água]
				/*
				 * atualizarImovelLigacaoAgua(idOrdemServico,
				 * fiscalizacaoSituacao .getId(), indicadorDocumentoEntregue,
				 * idImovel, 1);
				 */
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;

	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * Utilizado para obter o fator de multiplicalçao do débito no cáculo 5
	 * 
	 * @author Raphael Rossiter
	 * @date 16/03/2007
	 * @param idLigacaoAguaSituacaoImovel
	 *            ,
	 *            idFiscalizacaoSituacao, idImovel
	 * @return fatorMultiplicacao
	 * @throws ControladorException
	 */
	public Short obterFatorMultiplicacaoParaFiscalizacaoAgua(Integer idLigacaoAguaSituacaoImovel, Integer idFiscalizacaoSituacao,
					Integer idImovel) throws ControladorException{

		Short fatorMultiplicacao = null;

		try{

			/*
			 * 7.1.5.4 - Caso existe fator de multiplicação para ser aplicado ao
			 * débito para a situação de fiscalização informada e para situação da ligação de água
			 */
			fatorMultiplicacao = repositorioOrdemServico
							.pesquisarNumeroMesesFaturaAgua(idLigacaoAguaSituacaoImovel, idFiscalizacaoSituacao);

			/*
			 * 7.1.5.5 - Caso contrário, o sistema deverá obter o valor de diferença de meses a ser
			 * utilizado como fator de multiplicação
			 */
			if(fatorMultiplicacao == null){

				LigacaoAgua ligacaoAgua = this.getControladorLigacaoAgua().recuperaParametrosLigacaoAgua(idImovel);

				/*
				 * 7.1.5.5.1 - Caso a situação da ligação de água do imóvel seja igual a "CORTADO"
				 */
				if(ligacaoAgua != null && idLigacaoAguaSituacaoImovel != null
								&& idLigacaoAguaSituacaoImovel.equals(LigacaoAguaSituacao.CORTADO)){

					/*
					 * 7.1.5.5.1.1 - O sistema deverá calcular a "diferença", em
					 * meses, da data de corte da ligação de água do imóvel e a data corrente
					 */
					fatorMultiplicacao = Short.valueOf(String.valueOf(Util.dataDiff(ligacaoAgua.getDataCorte(), new Date())));
				}

				/*
				 * 7.1.5.5.2 - Caso a situação da ligação de água do imóvel seja
				 * igual a "SUPRIMIDO" ou "SUPR. PARCIAL" ou "SUPR. A PEDIDO"
				 */
				else if(ligacaoAgua != null
								&& idLigacaoAguaSituacaoImovel != null
								&& (idLigacaoAguaSituacaoImovel.equals(LigacaoAguaSituacao.SUPRIMIDO)
												|| idLigacaoAguaSituacaoImovel.equals(LigacaoAguaSituacao.SUPR_PARC) || idLigacaoAguaSituacaoImovel
													.equals(LigacaoAguaSituacao.SUPR_PARC_PEDIDO))){

					/*
					 * 7.1.5.5.1.2 - O sistema deverá calcular a "diferença", em meses, da data da
					 * supressão da ligação de água do imóvel e a data
					 * corrente
					 */
					fatorMultiplicacao = Short.valueOf(String.valueOf(Util.dataDiff(ligacaoAgua.getDataSupressao(), new Date())));

				}
			}

			/*
			 * 7.1.6 - Caso a "diferença em meses" seja a igual a zeros, o
			 * sistema deverá atualizar o fator de multiplicação por 1 (um)
			 */
			if((fatorMultiplicacao == null) || (fatorMultiplicacao != null && fatorMultiplicacao.equals(ConstantesSistema.ZERO))){
				fatorMultiplicacao = Short.valueOf("1");
			}

			/*
			 * 7.1.7 - Caso contrário, o sistema deverá obter o valor máximo de
			 * meses de sanção
			 */
			else if(fatorMultiplicacao != null){

				SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

				/*
				 * 7.1.7.1 - Caso a "diferença em meses" seja maior que o número máximo de meses,
				 * usar como fator o número máximo de meses
				 */
				if(sistemaParametro.getNumeroMaximoMesesSancoes() != null
								&& fatorMultiplicacao > sistemaParametro.getNumeroMaximoMesesSancoes()){

					fatorMultiplicacao = sistemaParametro.getNumeroMaximoMesesSancoes();
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return fatorMultiplicacao;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * Utilizado para obter o fator de multiplicalçao do débito no cáculo 6
	 * 
	 * @author Raphael Rossiter
	 * @date 16/03/2007
	 * @param idLigacaoAguaSituacaoImovel
	 *            ,
	 *            idFiscalizacaoSituacao, idImovel
	 * @return fatorMultiplicacao
	 * @throws ControladorException
	 */
	public Short obterFatorMultiplicacaoParaFiscalizacaoEsgoto(Integer idLigacaoEsgotoSituacaoImovel, Integer idFiscalizacaoSituacao,
					Integer idImovel) throws ControladorException{

		Short fatorMultiplicacao = null;

		try{

			/*
			 * 7.1.5.4 - Caso existe fator de multiplicação para ser aplicado ao
			 * débito para a situação de fiscalização informada e para situação da ligação de esgoto
			 */
			fatorMultiplicacao = repositorioOrdemServico.pesquisarNumeroMesesFaturaEsgoto(idLigacaoEsgotoSituacaoImovel,
							idFiscalizacaoSituacao);

			/*
			 * 7.1.5.5 - Caso contrário, o sistema deverá obter o valor de diferença de meses a ser
			 * utilizado como fator de multiplicação
			 */
			if(fatorMultiplicacao == null){

				fatorMultiplicacao = Short.valueOf("1");
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return fatorMultiplicacao;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * [SB0001] - Atualizar Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarOrdemServico(Integer idOrdemServico, Integer idSituacaoEncontrada, String indicadorDocumentoEntregue)
					throws ControladorException{

		// caso o indiacdor documento entregue seja igual a três que equevale a
		// nenhum então seta essa variavel para null
		if(indicadorDocumentoEntregue != null && indicadorDocumentoEntregue.equals("3")){
			indicadorDocumentoEntregue = null;
		}

		try{
			repositorioOrdemServico.atualizarParmsOrdemFiscalizacao(idOrdemServico, idSituacaoEncontrada, indicadorDocumentoEntregue);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * [SB0002] - Atualizar Imóvel/Ligação Água
	 * 
	 * @author Sávio Luiz
	 * @date 14/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarImovelLigacaoAgua(Integer idOrdemServico, Integer idSituacaoEncontrada, String indicadorDocumentoEntregue,
					Integer idImovel, Integer consumoDefinido, Integer idLigacaoAguaSituacao, Integer idLigacaoEsgotoSituacao)
					throws ControladorException{

		Integer idLigacaoAguaSituacaoNovo = null;
		Integer idLigacaoEsgotoSituacaoNovo = null;
		short indicadorLigacaoAguaAtualiza = 0;
		short indicadorConsumoFixado = 0;
		short indicadorVolumeFixado = 0;
		try{
			Object[] parmsLigacaoAguaFiscalizacao = repositorioOrdemServico.pesquisarIdFiscalizacaoSituacaoAguaOS(idSituacaoEncontrada,
							idLigacaoAguaSituacao);
			if(parmsLigacaoAguaFiscalizacao != null){
				if(parmsLigacaoAguaFiscalizacao[3] != null){
					idLigacaoAguaSituacaoNovo = (Integer) parmsLigacaoAguaFiscalizacao[3];
				}
				if(parmsLigacaoAguaFiscalizacao[1] != null){
					indicadorConsumoFixado = (Short) parmsLigacaoAguaFiscalizacao[1];
				}
				if(parmsLigacaoAguaFiscalizacao[2] != null){
					indicadorLigacaoAguaAtualiza = (Short) parmsLigacaoAguaFiscalizacao[2];
				}
			}

			Object[] parmsLigacaoEsgotoFiscalizacao = repositorioOrdemServico.pesquisarIdFiscalizacaoSituacaoEsgotoOS(idSituacaoEncontrada,
							idLigacaoEsgotoSituacao);
			if(parmsLigacaoEsgotoFiscalizacao != null){
				if(parmsLigacaoEsgotoFiscalizacao[2] != null){
					idLigacaoEsgotoSituacaoNovo = (Integer) parmsLigacaoEsgotoFiscalizacao[2];
				}
				if(parmsLigacaoEsgotoFiscalizacao[1] != null){
					indicadorVolumeFixado = (Short) parmsLigacaoEsgotoFiscalizacao[1];
				}
			}

			// chama o método no controlador imóvel que vai atualizar a tabela
			// imóvel ,ligaçãoAguaSituação e ligaçãoEsgotoSituação
			getControladorImovel().atualizarImovelLigacaoAguaEsgoto(idImovel, idLigacaoAguaSituacaoNovo, idLigacaoEsgotoSituacaoNovo,
							indicadorLigacaoAguaAtualiza, indicadorConsumoFixado, consumoDefinido, indicadorVolumeFixado);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * [UC0372] - Manter Equipe
	 * Atualiza a equipe e seus componentes na base
	 * 
	 * @author Rafael Corrêa
	 * @date 14/11/2006
	 * @param equipe
	 * @throws ControladorException
	 */
	public void atualizarEquipe(Equipe equipe, Collection colecaoEquipeComponentes, Usuario usuarioLogado) throws ControladorException{

		// Caracteres no nome da equipe
		String nome = equipe.getNome();
		if(nome.contains("/") || nome.contains(" ") || nome.contains("-")){
			throw new ControladorException("atencao.campo_texto.caracter_invalido", null, "Nome da equipe");
		}
		// [FS0011] - Verificar equipe já existente
		FiltroEquipe filtroEquipe = new FiltroEquipe();
		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.NOME, equipe.getNome()));
		filtroEquipe.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroEquipe.ID, equipe.getId()));

		Collection colecaoEquipeJaExistente = getControladorUtil().pesquisar(filtroEquipe, Equipe.class.getName());
		if(colecaoEquipeJaExistente != null && !colecaoEquipeJaExistente.isEmpty()){
			throw new ControladorException("atencao.inserir_equipe_mesmo_nome");
		}

		// [FS0009] - Atualização realizada por outro usuário
		filtroEquipe.limparListaParametros();
		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, equipe.getId()));

		Collection colecaoEquipeBase = getControladorUtil().pesquisar(filtroEquipe, Equipe.class.getName());

		if(colecaoEquipeBase == null || colecaoEquipeBase.isEmpty()){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		Equipe equipeBase = (Equipe) colecaoEquipeBase.iterator().next();

		if(equipeBase.getUltimaAlteracao().after(equipe.getUltimaAlteracao())){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.atualizacao.timestamp");
		}

		equipe.setUltimaAlteracao(new Date());

		validaEquipeComponenteEquipeTIpo(colecaoEquipeComponentes, equipe);

		// ------------ REGISTRAR TRANSAÇÃO----------------------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EQUIPE_ATUALIZAR, new UsuarioAcaoUsuarioHelper(
						usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_EQUIPE_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		equipe.setOperacaoEfetuada(operacaoEfetuada);
		equipe.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(equipe);
		// ------------ REGISTRAR TRANSAÇÃO----------------------------

		getControladorUtil().atualizar(equipe);

		// Faz um filtro para recuperar todas as equipes componentes na base
		// associadas àquela equipe e remove todas para em seguida inserir
		// apenas as que o usuario selecionou no Atualizar Equipe
		FiltroEquipeComponentes filtroEquipeComponentes = new FiltroEquipeComponentes();
		filtroEquipeComponentes.adicionarParametro(new ParametroSimples(FiltroEquipeComponentes.ID_EQUIPE, equipe.getId()));

		Collection colecaoEquipeComponentesBase = getControladorUtil()
						.pesquisar(filtroEquipeComponentes, EquipeComponentes.class.getName());

		if(colecaoEquipeComponentesBase != null && !colecaoEquipeComponentesBase.isEmpty()){

			Iterator colecaoEquipeComponentesBaseIterator = colecaoEquipeComponentesBase.iterator();

			while(colecaoEquipeComponentesBaseIterator.hasNext()){

				EquipeComponentes equipeComponentesBase = (EquipeComponentes) colecaoEquipeComponentesBaseIterator.next();
				getControladorUtil().remover(equipeComponentesBase);

				equipeComponentesBase.setOperacaoEfetuada(operacaoEfetuada);
				equipeComponentesBase.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(equipeComponentesBase);
			}
		}

		// Insere as equipes selecionadas na base
		if(colecaoEquipeComponentes != null && !colecaoEquipeComponentes.isEmpty()){

			Iterator colecaoEquipeComponentesIterator = colecaoEquipeComponentes.iterator();

			boolean responsavel = false;

			while(colecaoEquipeComponentesIterator.hasNext()){

				EquipeComponentes equipeComponentes = (EquipeComponentes) colecaoEquipeComponentesIterator.next();

				if(equipeComponentes.getIndicadorResponsavel() == EquipeComponentes.INDICADOR_RESPONSAVEL_SIM){
					responsavel = true;
				}

				equipeComponentes.setEquipe(equipe);
				equipeComponentes.setUltimaAlteracao(new Date());

				getControladorUtil().inserir(equipeComponentes);

				equipeComponentes.setOperacaoEfetuada(operacaoEfetuada);
				equipeComponentes.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

				registradorOperacao.registrarOperacao(equipeComponentes);
			}

			if(!responsavel){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.inserir_equipe_nenhum_responsavel");
			}

		}
	}

	/**
	 * [UC0383] Manter Material [FS0001] Atualizar Material [FS0002] Atualizar Material
	 * 
	 * @author Kassia Albuquerque
	 * @date 20/11/2006
	 * @param material
	 * @throws ControladorException
	 */

	// [FS0001] VERIFICAR EXISTENCIA DA DESCRIÇÃO
	// [FS0002] VERIFICAR EXISTENCIA DA DESCRIÇÃO ABREVIADA
	public void verificarExistenciaDescricaoMaterial(Material material) throws ControladorException{

		FiltroMaterial filtroMaterial = new FiltroMaterial();
		filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.DESCRICAO, material.getDescricao()));
		filtroMaterial.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroMaterial.ID, material.getId()));

		Collection colecaoMaterial = getControladorUtil().pesquisar(filtroMaterial, Material.class.getName());
		if(colecaoMaterial != null && !colecaoMaterial.isEmpty()){
			throw new ControladorException("atencao.material.decricao.existente");
		}

		filtroMaterial = new FiltroMaterial();
		filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.DESCRICAO_ABREVIADA, material.getDescricaoAbreviada()));
		filtroMaterial.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroMaterial.ID, material.getId()));
		colecaoMaterial = new ArrayList();
		colecaoMaterial = getControladorUtil().pesquisar(filtroMaterial, Material.class.getName());
		if(!colecaoMaterial.isEmpty()){
			throw new ControladorException("atencao.material.decricaoabreviada.existente");
		}

	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * [SB0004] - Calcular Valor de Água e/ou Esgoto
	 * 
	 * @author Sávio Luiz, Raphael Rossiter
	 * @date 20/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public BigDecimal calcularValorAguaEsgoto(int consumoInformado, SistemaParametro sistemaParametro, Integer idImovel,
					Integer idSituacaoEncontrada, Integer idLigacaoAguaSituacaoImovel, Integer idLigacaoEsgotoSituacaoImovel)
					throws ControladorException{

		Integer idLigacaoAguaSituacaoNova = null;
		Integer idLigacaoEsgotoSituacaoNova = null;
		Integer idLigacaoAguaSituacao = null;
		Integer idLigacaoEsgotoSituacao = null;

		BigDecimal valorTotal = new BigDecimal("0.00");
		try{
			Object[] parmsLigacaoAguaFiscalizacao = repositorioOrdemServico.pesquisarIdFiscalizacaoSituacaoAguaOS(idSituacaoEncontrada,
							idLigacaoAguaSituacaoImovel);
			if(parmsLigacaoAguaFiscalizacao != null){
				if(parmsLigacaoAguaFiscalizacao[3] != null){
					idLigacaoAguaSituacaoNova = (Integer) parmsLigacaoAguaFiscalizacao[3];
				}

			}

			Object[] parmsLigacaoEsgotoFiscalizacao = repositorioOrdemServico.pesquisarIdFiscalizacaoSituacaoEsgotoOS(idSituacaoEncontrada,
							idLigacaoEsgotoSituacaoImovel);
			if(parmsLigacaoEsgotoFiscalizacao != null){
				if(parmsLigacaoEsgotoFiscalizacao[2] != null){
					idLigacaoEsgotoSituacaoNova = (Integer) parmsLigacaoEsgotoFiscalizacao[2];
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		// 1.1. ano mes de referencia
		int anoMesReferencia = sistemaParametro.getAnoMesFaturamento();
		// 1.2. situação de ligação de agua
		if(idLigacaoAguaSituacaoNova == null){
			idLigacaoAguaSituacao = getControladorImovel().pesquisarIdLigacaoAguaSituacao(idImovel);
		}
		// 1.3. situação de ligação de agua
		if(idLigacaoEsgotoSituacaoNova == null){
			idLigacaoEsgotoSituacao = getControladorImovel().pesquisarIdLigacaoEsgotoSituacao(idImovel);
		}
		// 1.4. indicador de faturamento água
		short indicadorFatAgua = 1;
		// 1.5. indicador de faturamento esgoto
		short indicadorFatEsgoto = 1;
		Imovel imovel = new Imovel();
		imovel.setId(idImovel);

		/*
		 * Colocado por Raphael Rossiter em 15/01/2007. Será utilizado para
		 * obter o consumo mínimo da ligação
		 * ==============================================
		 */
		Integer idConsumoTarifa = getControladorImovel().recuperarIdConsumoTarifa(idImovel);

		ConsumoTarifa consumoTarifa = new ConsumoTarifa();
		if(idConsumoTarifa != null){
			consumoTarifa.setId(idConsumoTarifa);
		}

		imovel.setConsumoTarifa(consumoTarifa);
		// ===============================================

		// 1.6. categorias e suas respectivas quantidade de economias
		Collection colecaoQuantidadeEconomias = getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);
		// 1.7. consumo fatura de água
		int consumoAgua = 0;
		// 1.7.1. caso a nova situaçao de agua seja ligado ou cortado
		if(idLigacaoAguaSituacaoNova != null
						&& (idLigacaoAguaSituacaoNova.equals(LigacaoAguaSituacao.LIGADO) || idLigacaoAguaSituacaoNova
										.equals(LigacaoAguaSituacao.CORTADO))){
			consumoAgua = consumoInformado;
		}else{
			// 1.7.3 caso a nova situação de agua não seja informado e a
			// situação atual da ligação de água seja ligado ou cortado
			if(idLigacaoAguaSituacaoNova == null
							&& idLigacaoAguaSituacao != null
							&& (idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.LIGADO) || idLigacaoAguaSituacao
											.equals(LigacaoAguaSituacao.CORTADO))){
				consumoAgua = consumoInformado;
			}
		}

		// 1.8. consumo fatura de esgoto
		int consumoEsgoto = 0;
		// 1.8.1. caso a nova situaçao de esgoto seja ligado ou cortado
		if(idLigacaoEsgotoSituacaoNova != null && idLigacaoEsgotoSituacaoNova.equals(LigacaoEsgotoSituacao.LIGADO)){
			consumoEsgoto = consumoInformado;
		}else{
			// 1.8.3 caso a nova situação de esgoto não seja informado e a situação atual da ligação
			// de esgoto seja ligado ou cortado
			if(idLigacaoEsgotoSituacaoNova == null && idLigacaoEsgotoSituacao != null
							&& idLigacaoEsgotoSituacao.equals(LigacaoEsgotoSituacao.LIGADO)){
				consumoEsgoto = consumoInformado;
			}
		}
		// 1.9. consumo mínimo da ligacao
		int volumeObtido = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, null);

		// 1.10.Data de leitura anterior
		Date dataRealizacao = getControladorFaturamento().pesquisarDataRealizacaoFaturamentoAtividadeCronagrama(idImovel, 1);

		/*
		 * Colocado por Raphael Rossiter em 16/01/2007
		 * Caso o valor retornado para a data de leitura atual seja nulo, o valor da mesma será o
		 * valor retornado para a data de leitura anterior. Já
		 * a data de leitura anterior será novamente pesquisa, porém será passado 2 e não 1 a
		 * quantidade de meses para retroceder.
		 */
		// 1.11.Data de leitura atual
		Date dataRealizacaoAtual = getControladorFaturamento().pesquisarDataRealizacaoFaturamentoAtividadeCronagrama(idImovel, 0);

		if(dataRealizacaoAtual == null){
			dataRealizacaoAtual = dataRealizacao;
			dataRealizacao = getControladorFaturamento().pesquisarDataRealizacaoFaturamentoAtividadeCronagrama(idImovel, 2);
		}

		// 1.13.Tarifa do imóvel
		/*
		 * Integer idConsumoTarifa =
		 * getControladorImovel().pesquisarTarifaImovel( idImovel);
		 */

		if(idLigacaoAguaSituacaoNova == null){
			idLigacaoAguaSituacaoNova = idLigacaoAguaSituacao;
		}
		if(idLigacaoEsgotoSituacaoNova == null){
			idLigacaoEsgotoSituacaoNova = idLigacaoEsgotoSituacao;
		}

		// 1.12 percentual de esgoto
		BigDecimal percentualEsgoto = getControladorLigacaoEsgoto().recuperarPercentualEsgoto(idImovel);

		if(percentualEsgoto == null){
			percentualEsgoto = new BigDecimal("100.00");
		}

		// [UC0120] - Calcular Valores de Água e/ou Esgoto
		Collection colecaoCalcularValoresAguaEsgotoHelper = getControladorFaturamento().calcularValoresAguaEsgoto(anoMesReferencia,
						idLigacaoAguaSituacaoNova, idLigacaoEsgotoSituacaoNova, indicadorFatAgua, indicadorFatEsgoto,
						colecaoQuantidadeEconomias, consumoAgua, consumoEsgoto, volumeObtido, dataRealizacao, dataRealizacaoAtual,
						percentualEsgoto, idConsumoTarifa, imovel.getId(), null);

		BigDecimal valorTotalAgua = new BigDecimal("0.00");
		BigDecimal valorTotalEsgoto = new BigDecimal("0.00");

		// 7.1.3.3 o sistema deve inserir o
		// débito a cobrar para o imóvel
		// Cria o iterator para os valores de água e
		// esgoto
		Iterator iteratorColecaoCalcularValoresAguaEsgotoHelper = colecaoCalcularValoresAguaEsgotoHelper.iterator();

		// Laço para acumular os valores de água e
		// esgoto.
		while(iteratorColecaoCalcularValoresAguaEsgotoHelper.hasNext()){

			// Recupera o objeto helper que contem
			// os valores de água e
			// esgoto.
			CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper = (CalcularValoresAguaEsgotoHelper) iteratorColecaoCalcularValoresAguaEsgotoHelper
							.next();

			/*
			 * Caso tenha valor de água faturado para categoria adiciona o valor
			 * de água ao valor total de água. Caso contrário soma zero.
			 */
			if(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria() != null){
				valorTotalAgua = valorTotalAgua.add(calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria());
			}else{
				valorTotalAgua = valorTotalAgua.add(new BigDecimal("0.00"));
			}

			/*
			 * Caso tenha valor de esgoto faturado para categoria adiciona o
			 * valor de esgoto ao valor total de esgoto. Caso contrário soma
			 * zero.
			 */
			if(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria() != null){
				valorTotalEsgoto = valorTotalEsgoto.add(calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria());
			}else{
				valorTotalEsgoto = valorTotalEsgoto.add(new BigDecimal("0.00"));
			}
		}

		// Colocado por Raphael Rossiter em 16/01/2007
		valorTotal = valorTotalAgua.add(valorTotalEsgoto);

		return valorTotal;
	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * [SB0003] - Calcular/Inserir Valor
	 * 
	 * @author Sávio Luiz
	 * @date 20/11/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void calcularInserirValor(BigDecimal valordebito, Integer idImovel, Integer idFiscalizacaoSituacao, Integer idOS,
					Integer idDebitoTipo) throws ControladorException{

		LigacaoAgua ligacaoAgua = getControladorLigacaoAgua().recuperaParametrosLigacaoAgua(idImovel);
		Integer idLigacaoAguaSituacao = getControladorImovel().pesquisarIdLigacaoAguaSituacao(idImovel);
		int quantidadeMeses = 0;

		if(idLigacaoAguaSituacao != null){
			// caso a situação de ligação de agua do imóvel seja igual a
			// "Cortado"
			if(idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.CORTADO)){
				quantidadeMeses = Util.dataDiff(ligacaoAgua.getDataCorte(), new Date());
			}
			if(idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.SUPRIMIDO) || idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.SUPR_PARC)
							|| idLigacaoAguaSituacao.equals(LigacaoAguaSituacao.SUPR_PARC_PEDIDO)){

				quantidadeMeses = Util.dataDiff(ligacaoAgua.getDataSupressao(), new Date());
			}
		}

		// [FS0003] - Verificar Valor do Débito
		if(valordebito != null && (valordebito.compareTo(new BigDecimal("0.00")) == 1)){

			Short numeroVezesServicoCalculadoValor = 0;

			if(idFiscalizacaoSituacao != null){

				Object[] parmsFiscalizacaoSituacaoServicoACobrar = null;

				try{
					parmsFiscalizacaoSituacaoServicoACobrar = repositorioOrdemServico.pesquisarParmsFiscalizacaoSituacaoServicoACobrar(
									idFiscalizacaoSituacao, idDebitoTipo);
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

				if(parmsFiscalizacaoSituacaoServicoACobrar != null){
					// recupera o numero de vezes que o valor foi calculado no
					// serviço
					if(parmsFiscalizacaoSituacaoServicoACobrar[1] != null){
						numeroVezesServicoCalculadoValor = (Short) parmsFiscalizacaoSituacaoServicoACobrar[1];
					}
				}
			}
			// 3.3.1. Caso a diferença de meses tenha valor diferente de zero e
			// seja menor que o numero de vezes de calculo do valor
			if(quantidadeMeses != 0 && quantidadeMeses < numeroVezesServicoCalculadoValor.intValue()){
				// valor do debito é multiplicado pela diferença de meses
				valordebito = valordebito.multiply(new BigDecimal(quantidadeMeses));

			}else{
				// 3.3.2. Caso Contrario,a diferença de meses tenha valor
				// diferente de zero e
				// seja maior que o numero de vezes de calculo do valor
				if(quantidadeMeses != 0 && quantidadeMeses > numeroVezesServicoCalculadoValor.intValue()){
					// valor do debito é multiplicado pelo numero de vezes de
					// calculo do valor
					valordebito = valordebito.multiply(new BigDecimal(numeroVezesServicoCalculadoValor.intValue()));

				}else{
					// 3.3.3. Caso Contrario,a diferença de meses tenha valor
					// igual a zero e o numero de vezes de calculo do valor
					// tenha valor diferente de zero
					if(quantidadeMeses == 0 && numeroVezesServicoCalculadoValor.intValue() != 0){
						// valor do debito é multiplicado pelo numero de vezes
						// de calculo do valor
						valordebito = valordebito.multiply(new BigDecimal(numeroVezesServicoCalculadoValor.intValue()));

					}
					// 3.3.4. Caso contrário então deixa o valor que está.
				}
			}

			// Alterado para sempre passar a quantidade de parcelas igual a 1
			/*
			 * gerarDebitoOrdemServico(idOS, idDebitoTipo, valordebito,
			 * quantidadeMeses);
			 */

			gerarDebitoOrdemServico(idOS, idDebitoTipo, valordebito, 1);
		}

	}

	/**
	 * [UC0450] Pesquisar Ordem de Servico verifica o tamanho da consulta
	 * [SB001] Selecionar Ordem de Servico por Situação [SB002] Selecionar Ordem
	 * de Servico por Situação da Programação [SB003] Selecionar Ordem de
	 * Servico por Matricula do Imovel [SB004] Selecionar Ordem de Servico por
	 * Codigo do Cliente [SB005] Selecionar Ordem de Servico por Unidade
	 * Superior [SB006] Selecionar Ordem de Servico por Município [SB007]
	 * Selecionar Ordem de Servico por Bairro [SB008] Selecionar Ordem de
	 * Servico por Bairro Logradouro
	 * 
	 * @author Rafael Pinto
	 * @date 18/08/2006
	 * @param PesquisarOrdemServicoHelper
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarOrdemServicoTamanho(PesquisarOrdemServicoHelper filtro) throws ControladorException{

		Set colecaoOS = new HashSet();

		try{

			// [SB0004] - Seleciona Ordem Servico por Código de Cliente
			if(filtro.getCodigoCliente() != null){

				Collection<Integer> colecaoIdOSPorCliente = this.consultarOrdemServicoPorCodigoCliente(filtro.getCodigoCliente(),
								filtro.getDocumentoCobranca());

				if(colecaoIdOSPorCliente != null && !colecaoIdOSPorCliente.isEmpty()){

					if(colecaoOS != null && !colecaoOS.isEmpty()){
						Collection<Integer> colecaoIdOSRetorno = mergeIDs(colecaoOS, colecaoIdOSPorCliente);

						if(colecaoIdOSRetorno != null && !colecaoIdOSRetorno.isEmpty()){
							colecaoOS.clear();
							colecaoOS.addAll(colecaoIdOSRetorno);
						}else{
							sessionContext.setRollbackOnly();
							throw new ControladorException("atencao.pesquisa.nenhumresultado");
						}
					}else{
						colecaoOS.addAll(colecaoIdOSPorCliente);
					}
				}else{
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.pesquisa.nenhumresultado");
				}
			}

			filtro.setColecaoIdsOS(colecaoOS);

			Short permiteTramiteIndependente = Short.parseShort((String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE
											.executar(ExecutorParametrosAtendimentoPublico.getInstancia()));

			return repositorioOrdemServico.pesquisarOrdemServicoTamanho(filtro, permiteTramiteIndependente);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Atualiza os imoveis da OS que refere a RA passada como parametro
	 * 
	 * @author Sávio Luiz
	 * @date 03/01/2007
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public void atualizarOsDaRA(Integer idRA, Integer idImovel) throws ControladorException{

		try{
			repositorioOrdemServico.atualizarOsDaRA(idRA, idImovel);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscalização
	 * 
	 * @author Raphael Rossiter
	 * @date 25/01/2007
	 * @param idOS
	 * @return fiscalizacaoSituacao
	 * @throws ControladorException
	 */
	public void verificarOSJaFiscalizada(Integer idOS) throws ControladorException{

		FiscalizacaoSituacao fiscalizacaoSituacaoOS = null;

		try{

			fiscalizacaoSituacaoOS = repositorioOrdemServico.pesquisarFiscalizacaoSituacaoPorOS(idOS);

		}catch(ErroRepositorioException ex){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ilex){

			}
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		if(fiscalizacaoSituacaoOS != null){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("atencao.os_ja_fiscalizada");
		}

	}

	/**
	 * [UC0539] Manter Prioridade do Tipo de Serviço
	 * Remove um ou mais objeto do tipo ServicoTipoPrioridade no BD
	 * 
	 * @author Vivianne Sousa
	 * @date 12/02/2007
	 * @param ids
	 * @return void
	 * @throws ControladorException
	 */
	public void removerPrioridadeTipoServico(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_SERVICO_TIPO_PRIORIDADE_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		// remover ServicoTipoPrioridade(s)
		this.getControladorUtil().remover(ids, ServicoTipoPrioridade.class.getName(), operacaoEfetuada, colecaoUsuarios);

	}

	/**
	 * [UC0455] Exibir Calendário para Elaboração ou Acompanhamento de Roteiro
	 * 
	 * @author Raphael Rossiter
	 * @date 14/02/2007
	 * @param idProgramacaoRoteiro
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer verificarExistenciaOSProgramacao(Integer idProgramacaoRoteiro) throws ControladorException{

		Integer retorno = null;

		try{
			retorno = repositorioOrdemServico.verificarExistenciaOSProgramacao(idProgramacaoRoteiro);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Pesquisar data e equipe da programação da ordem serviço
	 * 
	 * @author Ana Maria
	 * @date 09/03/2007
	 */
	public OrdemServicoProgramacao pesquisarDataEquipeOSProgramacao(Integer idOs) throws ControladorException{

		OrdemServicoProgramacao retorno = null;

		try{
			retorno = repositorioOrdemServico.pesquisarDataEquipeOSProgramacao(idOs);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0513] Manter Tipo de Crédito
	 * Remover Tipo de Crédito
	 * 
	 * @author Thiago Tenório
	 * @date 19/03/2007
	 * @param
	 * @throws ControladorException
	 */
	public void removerTipoRetornoOrdemServicoReferida(String[] ids, Usuario usuarioLogado) throws ControladorException{

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_TIPO_RETORNO_OS_REFERIDA_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuarioLogado,
						UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		this.getControladorUtil().remover(ids, OsReferidaRetornoTipo.class.getName(), null, null);
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * Método responsável pela integração com o comercial
	 * 
	 * @author Raphael Rossiter
	 * @date 25/04/2007
	 * @param idServicoTipo
	 *            ,
	 *            integracaoComercialHelper
	 * @throws ControladorException
	 */
	public void integracaoComercial(Integer idServicoTipo, IntegracaoComercialHelper integracaoComercialHelper, Usuario usuarioLogado)
					throws ControladorException{

		if(idServicoTipo != null && integracaoComercialHelper != null && integracaoComercialHelper.getOrdemServico() != null
						&& integracaoComercialHelper.getOrdemServico().getOperacaoEfetuada().getOperacao() != null){

			integracaoComercialHelper.setUsuarioLogado(usuarioLogado);

			Integer idOperacao = integracaoComercialHelper.getOrdemServico().getOperacaoEfetuada().getOperacao().getId();

			/*
			 * Integer idOperacao = this
			 * .pesquisarServicoTipoOperacao(idServicoTipo);
			 */
			if(idOperacao != null){

				switch(idOperacao){
					case (Operacao.OPERACAO_LIGACAO_AGUA_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarLigacaoAgua(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_LIGACAO_ESGOTO_EFETUAR_INT):
						getControladorAtendimentoPublico().inserirLigacaoEsgoto(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_CORTE_LIGACAO_AGUA_EFETUAR_INT):
						getControladorLigacaoAgua().efetuarCorteLigacaoAgua(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_SUPRESSAO_LIGACAO_AGUA_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarSupressaoLigacaoAgua(integracaoComercialHelper, true);
						break;
					case (Operacao.OPERACAO_RESTABELECIMENTO_LIGACAO_AGUA_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarRestabelecimentoLigacaoAgua(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_RELIGACAO_AGUA_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarReligacaoAgua(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_CORTE_ADMINISTRATIVO_LIGACAO_AGUA_EFETUAR_INT):
						getControladorLigacaoAgua().efetuarCorteAdministrativoLigacaoAgua(
										integracaoComercialHelper.getDadosEfetuacaoCorteLigacaoAguaHelper(), usuarioLogado);
						break;
					case (Operacao.OPERACAO_RETIRADA_HIDROMETRO_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarRetiradaHidrometro(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_REMANEJAMENTO_HIDROMETRO_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarRemanejamentoHidrometro(integracaoComercialHelper);
						break;

					case (Operacao.OPERACAO_INSTALACAO_HIDROMETRO_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarInstalacaoHidrometro(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_INSTALACAO_HIDROMETRO_ATUALIZAR_INT):
						getControladorAtendimentoPublico().atualizarInstalacaoHidrometro(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_SUBSTITUICAO_HIDROMETRO_EFETUAR_INT):
						getControladorAtendimentoPublico().efetuarSubstituicaoHidrometro(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_MUDANCA_SITUACAO_FATURAMENTO_LIGACAO_ESGOTO_INT):
						getControladorAtendimentoPublico().efetuarMudancaSituacaoFaturamentoLiagacaoEsgoto(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_CONSUMO_MINIMO_LIGACAO_AGUA_ATUALIZAR_INT):
						getControladorLigacaoAgua().atualizarConsumoMinimoLigacaoAgua(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_VOLUME_MINIMO_LIGACAO_ESGOTO_ATUALIZAR_INT):
						getControladorLigacaoEsgoto().atualizarVolumeMinimoLigacaoEsgoto(integracaoComercialHelper);
						break;
					case (Operacao.OPERACAO_EFETUAR_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO_INT):
						getControladorAtendimentoPublico().efetuarLigacaoAguaComInstalacaoHidrometro(integracaoComercialHelper,
										usuarioLogado);
						break;
					case (Operacao.RESTABELECIMENTO_LIGACAO_AGUA_COM_INSTALACAO_HIDROMETRO_INT):
						getControladorAtendimentoPublico().efetuarRestabelecimentoLigacaoAguaComInstalacaoHidrometro(
										integracaoComercialHelper, usuarioLogado);
						break;
					case (Operacao.OPERACAO_EFETUAR_RESTABELECIMENTO_LIGACAO_AGUA_COM_SUBSTITUICAO_DE_HIDROMETRO_INT):
						getControladorAtendimentoPublico().efetuarRestabelecimentoLigacaoAguaComSubstituicaoHidrometro(
										integracaoComercialHelper, usuarioLogado);
						break;
					case (Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_TUBETE_MAGNETICO_INT):
						this.getControladorAtendimentoPublico().efetuarInstalacaoSubstituicaoTubeteMagnetico(integracaoComercialHelper,
										usuarioLogado);
						break;
					case (Operacao.OPERACAO_EFETUAR_INSTALACAO_SUBSTITUICAO_REGISTRO_MAGNETICO_INT):
						this.getControladorAtendimentoPublico().efetuarInstalacaoSubstituicaoRegistroMagnetico(integracaoComercialHelper,
										usuarioLogado);
						break;
				}
			}

		}
	}

	/**
	 * [UC0457] Encerra Ordem de Serviço
	 * Pesquisa a operação que faz parte da associação com o SERVICO_TIPO na
	 * tabela SERVICO_TIPO_OPERACAO
	 * 
	 * @author Raphael Rossiter
	 * @date 26/04/2007
	 * @param idServicoTipo
	 *            ,
	 *            integracaoComercialHelper
	 * @throws ControladorException
	 */
	public Integer pesquisarServicoTipoOperacao(Integer idServicoTipo, Integer idOperacao) throws ControladorException{

		Integer retorno = null;

		try{
			retorno = repositorioOrdemServico.pesquisarServicoTipoOperacao(idServicoTipo, idOperacao);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Filtra os Imoveis
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Ivan Sérgio
	 * @date 08/11/2007
	 * @author Andre Nishimura
	 * @date 13/03/2010
	 *       Modificaçao na emissao de ordens de serviço seletivas para acrescentar novos parametros
	 *       de filtro
	 */
	public Collection<Integer> filtrarImovelEmissaoOrdensSeletivas(OrdemServicoSeletivaHelper ordemServicoSeletivaHelper)
					throws ControladorException{

		Collection<Integer> colecaoImoveis = null;
		List colecaoDados = null;
		String anormalidades = "";
		int quantidade = 0;
		int count = 0;
		Integer idImovel = 0;

		try{
			// Verifica as Anormalidades Selecionadas
			if(ordemServicoSeletivaHelper.getAnormalidadeHidrometro() != null){
				for(int i = 0; i < ordemServicoSeletivaHelper.getAnormalidadeHidrometro().length; i++){
					if(!ordemServicoSeletivaHelper.getAnormalidadeHidrometro()[i].trim().equalsIgnoreCase(
									Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
						anormalidades += ordemServicoSeletivaHelper.getAnormalidadeHidrometro()[i].toString() + ",";
					}
				}

				if(!anormalidades.equals("")){
					// Retira a ultima virgula
					anormalidades = anormalidades.substring(0, anormalidades.length() - 1);
				}else{
					anormalidades = null;
				}
			}

			String idServicoTipoStr = ordemServicoSeletivaHelper.getServicoTipo();
			Boolean substituicaoHidrometro = this.comparaServicoTipoSubgrupoSubstituicaoHidrometro(idServicoTipoStr);
			Boolean corte = this.comparaServicoTipoSubgrupoCorte(idServicoTipoStr);

			SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();
			Integer anoMesFaturamentoAtual = sistemaParametros.getAnoMesFaturamento();
			String situacaLigacaoAguaPermitidaManutencao = ParametroOrdemServico.P_SIT_AGUA_PERMIT_OS_SELETIVA_MANUT_HIDROMETRO.executar()
							.toString();
			String situacaLigacaoAguaPermitidaCorte = ParametroOrdemServico.P_SIT_AGUA_PERMIT_OS_SELETIVA_CORTE.executar().toString();

			// casos seja informado o arquivo com as matrículas
			boolean informouMatriculasEmArquivo = false;
			if(ordemServicoSeletivaHelper.getColecaoImoveis() == null || ordemServicoSeletivaHelper.getColecaoImoveis().isEmpty()){
				colecaoDados = repositorioOrdemServico.filtrarImovelEmissaoOrdensSeletivas(ordemServicoSeletivaHelper, anormalidades,
								substituicaoHidrometro, corte, anoMesFaturamentoAtual, situacaLigacaoAguaPermitidaManutencao,
								situacaLigacaoAguaPermitidaCorte);
			}else{
				colecaoDados = new ArrayList<Integer>(ordemServicoSeletivaHelper.getColecaoImoveis());
				informouMatriculasEmArquivo = true;
			}

			if(colecaoDados != null && !colecaoDados.isEmpty()){
				colecaoImoveis = new ArrayList();
				// Verifica a Quantidade Maxima informada pelo usuario
				if(ordemServicoSeletivaHelper.getQuantidadeMaxima() != null && !ordemServicoSeletivaHelper.getQuantidadeMaxima().equals("")){
					quantidade = Util.converterStringParaInteger(ordemServicoSeletivaHelper.getQuantidadeMaxima()).intValue();
				}

				if(quantidade == 0){
					quantidade = colecaoDados.size();
				}

				// Iterator iColecaoDados = colecaoDados.iterator();
				Integer totalContasEmDebito = null;
				Object obj = null;
				Object[] retorno = null;

				for(int i = 0; (i < colecaoDados.size()) && (count < quantidade); i++){
					obj = colecaoDados.get(i);

					if(obj instanceof Object[] || informouMatriculasEmArquivo){

						if(informouMatriculasEmArquivo){
							idImovel = (Integer) obj;
						}else{
							retorno = (Object[]) obj;
							idImovel = (Integer) retorno[0];
						}

						boolean incluiImovel = true;

						// Intervalo Quantidade de Documentos
						if(incluiImovel){
							if(ordemServicoSeletivaHelper.getIntervaloQuantidadeDocumentosInicial() != null
											&& !ordemServicoSeletivaHelper.getIntervaloQuantidadeDocumentosInicial().equals("")
											&& ordemServicoSeletivaHelper.getIntervaloQuantidadeDocumentosFinal() != null
											&& !ordemServicoSeletivaHelper.getIntervaloQuantidadeDocumentosFinal().equals("")){

								Calendar dataInicio = new GregorianCalendar();
								dataInicio.set(Calendar.YEAR, 1980);
								dataInicio.set(Calendar.MONTH, 0);
								dataInicio.set(Calendar.DAY_OF_MONTH, 1);

								Calendar dataFim = new GregorianCalendar();
								dataFim.set(Calendar.YEAR, 9999);
								dataFim.set(Calendar.MONTH, 11);
								dataFim.set(Calendar.DAY_OF_MONTH, 31);

								ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = this.getControladorCobranca()
												.obterDebitoImovelOuCliente(1, idImovel.toString(), null, null, "198001", "999912",
																dataInicio.getTime(), dataFim.getTime(), 1, 1, 2, 2, 2, 2, 2, null,
																sistemaParametros, null, null, null, ConstantesSistema.SIM,
																ConstantesSistema.SIM, ConstantesSistema.SIM, 2, null);

								if(obterDebitoImovelOuClienteHelper.getColecaoContasValores() != null
												&& !obterDebitoImovelOuClienteHelper.getColecaoContasValores().isEmpty()){

									totalContasEmDebito = obterDebitoImovelOuClienteHelper.getColecaoContasValores().size();

									if((totalContasEmDebito >= Util.converterStringParaInteger(ordemServicoSeletivaHelper
													.getIntervaloQuantidadeDocumentosInicial()))
													&& (totalContasEmDebito <= Util.converterStringParaInteger(ordemServicoSeletivaHelper
																	.getIntervaloQuantidadeDocumentosFinal()))){
										incluiImovel = true;
									}else{
										incluiImovel = false;
									}
								}else{
									incluiImovel = false;
								}
							}
						}

						// Numero de Ocorrencias Consecutivas
						/**
						 * Esperando Desenvolvimento do Metodo
						 */

						// // Verificar Ordem de Servico Pendente e Válida
						// if (incluiImovel) {
						// boolean existeOSInstalacaoSubstituicaoValida = false;
						// existeOSInstalacaoSubstituicaoValida =
						// this.verificarOrdemServicoInstalacaoSubstituicaoImovel(idImovel,Util.converterStringParaInteger(ordemServicoSeletivaHelper.getServicoTipo()));
						//
						// if (existeOSInstalacaoSubstituicaoValida) {
						// incluiImovel = false;
						// }
						// }

						// Adiciona o Id do Imovel na Colecao
						if(incluiImovel){
							colecaoImoveis.add(idImovel);
							count++;
						}
					}
				}

				Integer idServicoTipo = Util.converterStringParaInteger(idServicoTipoStr);

				// Filtrar imoveis sem ordem de serviço pendente
				if(colecaoImoveis.size() > 999){
					Collection temp = new ArrayList();
					Collection colecaoImoveisTemp = new ArrayList();
					for(Integer item : colecaoImoveis){
						temp.add(item);
						if(temp.size() == 999){
							colecaoImoveisTemp.addAll(this.verificarImoveisSemOrdemPendentePorTipoServico(temp, idServicoTipo));
							temp = new ArrayList();
						}
					}
					if(temp.size() > 0){
						colecaoImoveisTemp.addAll(this.verificarImoveisSemOrdemPendentePorTipoServico(temp, idServicoTipo));
					}
					colecaoImoveis = colecaoImoveisTemp;
				}else{
					colecaoImoveis = this.verificarImoveisSemOrdemPendentePorTipoServico(colecaoImoveis, idServicoTipo);
				}

				// Atenção! Implementado em caso de urgência para homologação DESO.
				// O sistema não deve emitir OS de Instalação de Hidrômetros para Imóveis Medidos
				// (que já tenham hidrômetro instalado).
				// Se não existe HIDI_ID em ligacao de água E LAST_ID = 3 (NORMAL)
				// ......................................................................................................................

				Collection colecaoImoveisRetorno = new ArrayList();
				if(ServicoTipo.INSTALACAO_HIDROMETRO.contains(idServicoTipo)){
					Iterator it = colecaoImoveis.iterator();
					while(it.hasNext()){
						Integer idImoveltemp = (Integer) it.next();

						LigacaoAguaSituacao ligacaoAguaSituacao = getControladorImovel().pesquisarLigacaoAguaSituacao(idImoveltemp);

						if(ligacaoAguaSituacao != null && ligacaoAguaSituacao.getId().equals(LigacaoAguaSituacao.LIGADO)){

							if(getControladorLigacaoAgua().pesquisarIdHidrometroInstalacaoHistorico(idImoveltemp) == null){
								colecaoImoveisRetorno.add(idImoveltemp);
							}

						}

					}
					colecaoImoveis = colecaoImoveisRetorno;

				}
				// ......................................................................................................................

			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return colecaoImoveis;
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * Método que é chamado pelo [UC0713] Emitir Ordem de Servico Seletiva
	 * 
	 * @author Willian Pereira
	 * @date 18/03/2010
	 */
	public Map<Integer, Integer> gerarOrdemServicoSeletiva(List<Integer> idsImoveis, ServicoTipo servicoTipo, Empresa empresa,
					Usuario usuario, Integer idcomandoOSSeletiva) throws ControladorException{

		Map<Integer, Integer> map = new TreeMap<Integer, Integer>();
		OrdemServico ordemServicoDados = null;

		try{
			ordemServicoDados = repositorioOrdemServico.pesquisarDadosServicoTipoPrioridade(servicoTipo.getId());
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		for(Integer idImovel : idsImoveis){

			// showMem(this.getClass().getSimpleName(),
			// "Map<Integer, Integer> gerarOrdemServicoSeletiva(List<Integer> idsImoveis",
			// "inicio");

			OrdemServico ordemServico = new OrdemServico();
			ordemServico.setServicoTipo(servicoTipo);
			ordemServico.setObservacao("Gerada pelo sistema");
			ordemServico.setValorOriginal(servicoTipo.getValor());

			if(idcomandoOSSeletiva != null){

				OsSeletivaComando osSeletivaComando = new OsSeletivaComando();
				osSeletivaComando.setId(idcomandoOSSeletiva);

				ordemServico.setOsSeletivaComando(osSeletivaComando);

			}
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.BAIRRO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.UNIDADE_NEGOCIO);

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()));

			ordemServico.setImovel(imovel);

			if(ordemServicoDados != null){
				ordemServico.setValorOriginal(ordemServicoDados.getServicoTipo().getValor());
				ordemServico.setServicoTipoPrioridadeOriginal(ordemServicoDados.getServicoTipo().getServicoTipoPrioridade());
				ordemServico.setServicoTipoPrioridadeAtual(ordemServicoDados.getServicoTipo().getServicoTipoPrioridade());
			}

			if(this.verificarOrdemServicoExistente(idImovel, servicoTipo.getId()) == false){
				// [UC0430] - Gerar Ordem de Serviço
				// [FS0010] - Verificar restrição de emissão da Ordem de Serviço
				if(this.verificarRestricaoEmissaoOrdemServico(usuario, ordemServico, ConstantesSistema.SIM)){
					map.put(idImovel, this.gerarOrdemServicoSeletiva(ordemServico, imovel, usuario));
				}

			}

			// showMem(this.getClass().getSimpleName(),
			// "Map<Integer, Integer> gerarOrdemServicoSeletiva(List<Integer> idsImoveis", "fim");
		}

		FiltroOsSeletivaComando filtroOsSeletivaComando = new FiltroOsSeletivaComando();
		filtroOsSeletivaComando.adicionarParametro(new ParametroSimples(FiltroOsSeletivaComando.ID, idcomandoOSSeletiva));
		Collection colecaoComando = this.getControladorUtil().pesquisar(filtroOsSeletivaComando, OsSeletivaComando.class.getName());

		OsSeletivaComando osSeletivaComando = (OsSeletivaComando) Util.retonarObjetoDeColecao(colecaoComando);

		if(osSeletivaComando != null){

			osSeletivaComando.setQuantidadeOrdensServicoGeradas(idsImoveis.size());
			osSeletivaComando.setTempoRealizacao(new Date());
			osSeletivaComando.setUltimaAlteracao(new Date());

			this.getControladorUtil().atualizar(osSeletivaComando);

		}

		return map;
	}

	/**
	 * [UC0430] - Gerar Ordem de Serviço
	 * Método que é chamado pelo [UC0713] Emitir Ordem de Servico Seletiva
	 * 
	 * @author Ivan Sérgio
	 * @date 27/11/2007
	 */
	public Integer gerarOrdemServicoSeletiva(OrdemServico ordemServico, Imovel imovel, Usuario usuario) throws ControladorException{

		Calendar calendar = Calendar.getInstance();

		UnidadeOrganizacional unidadeOrganizacionalGeracao = null;
		Integer unidadeOrganizacionalGeracaoId = null;

		// [SB0004] - Gerar Ordem de serviço

		ordemServico.setAtendimentoMotivoEncerramento(null);
		ordemServico.setOsReferidaRetornoTipo(null);
		ordemServico.setSituacao(OrdemServico.SITUACAO_PENDENTE);
		ordemServico.setDataGeracao(calendar.getTime());
		ordemServico.setDataEmissao(calendar.getTime());
		ordemServico.setDataEncerramento(null);
		ordemServico.setDataExecucao(null);
		ordemServico.setDescricaoParecerEncerramento(null);
		ordemServico.setAreaPavimento(null);
		ordemServico.setDimensao1(null);
		ordemServico.setDimensao2(null);
		ordemServico.setDimensao3(null);
		ordemServico.setIndicadorComercialAtualizado(ConstantesSistema.NAO);
		ordemServico.setServicoNaoCobrancaMotivo(null);
		ordemServico.setPercentualCobranca(null);
		ordemServico.setFiscalizacaoColetiva(null);
		ordemServico.setIndicadorServicoDiagnosticado(ConstantesSistema.NAO);
		ordemServico.setUltimaAlteracao(calendar.getTime());

		// Colocar como ordem de servico não programada - Raphael Rossiter em
		// 08/02/2007
		ordemServico.setIndicadorProgramada(OrdemServico.NAO_PROGRAMADA);

		// Sete o imovel
		if(imovel != null){
			ordemServico.setImovel(imovel);
		}

		if(ordemServico.getValorHorasTrabalhadas() == null){
			ordemServico.setValorHorasTrabalhadas(BigDecimal.ZERO);
		}

		if(ordemServico.getValorMateriais() == null){
			ordemServico.setValorMateriais(BigDecimal.ZERO);
		}

		Integer idOrdemServico = null;

		try{
			idOrdemServico = (Integer) getControladorUtil().inserir(ordemServico);

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		OrdemServicoUnidade ordemServicoUnidade = new OrdemServicoUnidade();

		ordemServicoUnidade.setOrdemServico(ordemServico);

		// Caso não Exista Documento de Cobranca nem RA, atualizar a unidade
		// com a unidade da tabela com o ID da Empresa

		if(imovel != null && imovel.getLocalidade() != null){

			// FiltroServicoTipoTramite filtroServicoTipoTramite = new FiltroServicoTipoTramite();
			//
			// filtroServicoTipoTramite.adicionarParametro(new
			// ParametroSimples(FiltroServicoTipoTramite.SERVICO_TIPO_ID, ordemServico
			// .getServicoTipo().getId()));
			//
			// Collection<ServicoTipoTramite> colecaoServicoTipoTramite =
			// this.getControladorUtil().pesquisar(filtroServicoTipoTramite,
			// ServicoTipoTramite.class.getName());

			// [SB0006] – Obter unidade de geração da Ordem de Serviço
			// if(colecaoServicoTipoTramite != null && !colecaoServicoTipoTramite.isEmpty()){
			//
			// Integer servicoTipoId = null;
			// Integer unidadeNegocioId = null;
			//
			// if(ordemServico.getServicoTipo() != null){
			//
			// servicoTipoId = ordemServico.getServicoTipo().getId();
			// }
			//
			// FiltroUnidadeOrganizacional filtroUnidadeOrganizacionalOrigem = new
			// FiltroUnidadeOrganizacional();
			// filtroUnidadeOrganizacionalOrigem.adicionarParametro(new
			// ParametroSimples(FiltroUnidadeOrganizacional.ID_LOCALIDADE, imovel
			// .getLocalidade().getId()));
			//
			// Collection colecaoUnidadeOrganizacional = (Collection)
			// getControladorUtil().pesquisar(filtroUnidadeOrganizacionalOrigem,
			// UnidadeOrganizacional.class.getName());
			//
			// UnidadeOrganizacional unidadeNegocioIdAux = (UnidadeOrganizacional) Util
			// .retonarObjetoDeColecao(colecaoUnidadeOrganizacional);
			//
			// unidadeNegocioId = unidadeNegocioIdAux.getId();
			//
			// Integer unidadeOrganizacionalId = pesquisarUnidadeTramiteOS(servicoTipoId, null,
			// null, null, unidadeNegocioId);
			//
			// FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new
			// FiltroUnidadeOrganizacional();
			// filtroUnidadeOrganizacional
			// .adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID,
			// unidadeOrganizacionalId));
			//
			// unidadeOrganizacionalGeracao = (UnidadeOrganizacional)
			// Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
			// filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName()));
			//
			// }else{
			//
			// unidadeOrganizacionalGeracao =
			// getControladorUnidade().pesquisarUnidadeOrganizacionalLocalidade(
			// imovel.getLocalidade().getId());
			//
			// }
			//

			// ***************************************************

			// [SB0006] – Obter unidade de geração da Ordem de Serviço
			// Caso exista Documento de Cobrança:
			// 1.1. Caso haja indicação que o tipo de serviço da ordem de serviço seletiva tem regra
			// própria para definição da unidade de abertura

			FiltroServicoTipoTramite filtroServicoTipoTramite = new FiltroServicoTipoTramite();

			filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.SERVICO_TIPO_ID, ordemServico
							.getServicoTipo().getId()));

			Collection<ServicoTipoTramite> colecaoServicoTipoTramite = this.getControladorUtil().pesquisar(filtroServicoTipoTramite,
							ServicoTipoTramite.class.getName());

			if(colecaoServicoTipoTramite != null && !colecaoServicoTipoTramite.isEmpty()){

				Integer servicoTipoId = null;
				Integer unidadeId = null;

				if(ordemServico.getServicoTipo() != null){

					servicoTipoId = ordemServico.getServicoTipo().getId();
				}

				FiltroUnidadeOrganizacional filtroUnidadeOrganizacionalOrigem = new FiltroUnidadeOrganizacional();
				filtroUnidadeOrganizacionalOrigem.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID_LOCALIDADE,
 imovel
								.getLocalidade().getId()));

				Collection colecaoUnidadeOrganizacional = (Collection) getControladorUtil().pesquisar(filtroUnidadeOrganizacionalOrigem,
								UnidadeOrganizacional.class.getName());

				UnidadeOrganizacional unidadeIdAux = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeOrganizacional);

				unidadeId = unidadeIdAux.getId();

				// Recupera o Setor Comercial
				Integer idImovel = null;

				Integer idSetorComercial = null;

				if(!Util.isVazioOuBranco(ordemServico.getImovel())){
					idImovel = ordemServico.getImovel().getId();
				}else{
					if(!Util.isVazioOuBranco(ordemServico.getRegistroAtendimento())
									&& !Util.isVazioOuBranco(ordemServico.getRegistroAtendimento().getImovel())){
						idImovel = ordemServico.getRegistroAtendimento().getImovel().getId();
					}
				}

				Integer unidadeOrganizacionalId = null;

				Collection colecaoServicoTipoTramiteLocUnidade;

				try{
					colecaoServicoTipoTramiteLocUnidade = repositorioOrdemServico.pesquisarServicoTipoTramite(servicoTipoId, imovel
									.getLocalidade().getId(),
									null, null, unidadeId);

					if(!Util.isVazioOrNulo(colecaoServicoTipoTramiteLocUnidade) && colecaoServicoTipoTramiteLocUnidade.size() == 1){
						unidadeOrganizacionalId = pesquisarUnidadeTramiteOS(servicoTipoId, imovel.getLocalidade().getId(), null, null,
										unidadeId);
					}else{
						if(!Util.isVazioOuBranco(idImovel)){
							FiltroImovel filtroImovel = new FiltroImovel();
							filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
							filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);

							imovel = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel,
											Imovel.class.getName()));

							idSetorComercial = imovel.getSetorComercial().getId();
							unidadeOrganizacionalId = pesquisarUnidadeTramiteOS(servicoTipoId, imovel.getLocalidade().getId(),
											idSetorComercial, null,
											unidadeId);
						}

					}

				}catch(ErroRepositorioException e){
					e.printStackTrace();
				}

				FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();
				filtroUnidadeOrganizacional
								.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, unidadeOrganizacionalId));

				Collection collUnidadeOrganizacional = getControladorUtil().pesquisar(filtroUnidadeOrganizacional,
								UnidadeOrganizacional.class.getName());

				unidadeOrganizacionalGeracao = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(collUnidadeOrganizacional);

			}else{
				unidadeOrganizacionalGeracao = getControladorUnidade().pesquisarUnidadeOrganizacionalLocalidade(
								imovel.getLocalidade().getId());
			}

			// ***************************************************

			// 1.1. Caso exista unidade que represente a localidade do imóvel associado ao documento
			// de
			// cobrança (a partir da tabela UNIDADE_ORGANIZACIONAL com LOCA_ID igual ao LOCA_ID da
			// tabela IMOVEL com IMOV_ID igual ao IMOV_ID da tabela COBRANCA_DOCUMENTO com CBDO_ID
			// igual
			// ao Id do documento de cobrança informado)
			if(unidadeOrganizacionalGeracao != null){

				// 1.1.1.Caso a unidade da localidade do imóvel tenha unidade centralizadora
				// (UNID_IDCENTRALIZADORA diferente de nulo), atualizar a unidade como a unidade
				// centralizadora
				if(unidadeOrganizacionalGeracao.getUnidadeCentralizadora() != null){
					ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacionalGeracao.getUnidadeCentralizadora());
				}else{
					// 1.1.2.Caso contrário, atualizar a unidade como a unidade da localidade do
					// imóvel
					ordemServicoUnidade.setUnidadeOrganizacional(unidadeOrganizacionalGeracao);
				}

				unidadeOrganizacionalGeracaoId = ordemServicoUnidade.getUnidadeOrganizacional().getId();
			}else{
				throw new ControladorException("atencao.gerar_ordem_corte_unidade_nao_existente");
			}
		}else{
			throw new ControladorException("atencao.gerar_ordem_corte_unidade_nao_existente");
		}

		ordemServicoUnidade.setUsuario(usuario);
		AtendimentoRelacaoTipo atrt = new AtendimentoRelacaoTipo();
		atrt.setId(AtendimentoRelacaoTipo.ABRIR_REGISTRAR);
		ordemServicoUnidade.setAtendimentoRelacaoTipo(atrt);
		ordemServicoUnidade.setUltimaAlteracao(calendar.getTime());

		try{
			getControladorUtil().inserir(ordemServicoUnidade);

			/*
			 * [OC790655][UC0430][SB0004.7.1]: Caso o tipo de serviço da ordem de serviço
			 * tenha a indicação de geração de dados no histórico de manutenção da ligação de água
			 * do
			 * imóvel (SVTP_ICGERARHISTORICOIMOVEL com o valor 1 (sim) na tabela SERVICO_TIPO com
			 * SVTP_ID=SVTP_ID da tabela ORDEM_SERVICO), o sistema gera os dados – inclui na tabela
			 * HISTORICO_MANUTENCAO_LIGACAO .
			 */
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServico.getServicoTipo().getId()));
			ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroServicoTipo,
							ServicoTipo.class.getName()));

			if(ConstantesSistema.SIM.equals(servicoTipo.getIndicadorGerarHistoricoImovel())){
				getControladorLigacaoAgua().criarHistoricoManutencaoLigacao(ordemServico);
			}

		}catch(ControladorException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Integer idLocalidade = null;
		Integer idSetorComercial = null;
		Integer idBairro = null;

		Object[] dadosDoImovel = null;
		if(ordemServico.getImovel() != null){
			dadosDoImovel = this.getControladorImovel().pesquisarDadosLocalizacaoImovel(ordemServico.getImovel().getId());
		}

		if(dadosDoImovel != null){
			idLocalidade = (Integer) dadosDoImovel[0];
			idSetorComercial = (Integer) dadosDoImovel[1];
			idBairro = (Integer) dadosDoImovel[2];
		}

		this.gerarTramiteOrdemServico(idLocalidade, idSetorComercial, idBairro, unidadeOrganizacionalGeracaoId, null, null, usuario,
						ordemServico);

		return idOrdemServico;
	}

	private ControladorTransacaoLocal getControladorTransacao(){

		ControladorTransacaoLocalHome localHome = null;
		ControladorTransacaoLocal local = null;
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorTransacaoLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_TRANSACAO_SEJB);
			local = localHome.create();
			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * [UC0732] - Gerar Relatorio Acompanhamento de Ordens de Servico Hidrometro
	 * 
	 * @author Ivan Sérgio
	 * @date 27/11/2007
	 * @param tipoOrdem
	 * @param situacaoOrdemServico
	 * @param idLocalidade
	 * @param dataEncerramentoInicial
	 * @param dataEncerramentoFinal
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOrdemServicoGerarRelatorioAcompanhamentoHidrometro(String idEmpresa, String tipoOrdem,
					String situacaoOrdemServico, String idLocalidade, String dataEncerramentoInicial, String dataEncerramentoFinal,
					String tipoRelatorio) throws ControladorException{

		Collection colecaoDados = null;
		Collection<RelacaoOrdensServicoEncerradasPendentesHelper> retorno = new ArrayList();
		Short situacaoOrdem = null;

		// Verifica o Tipo da Ordem(Instalacao ou Substituicao)
		if(tipoOrdem.substring(0).equalsIgnoreCase("INSTALACAO")){

			if(ServicoTipo.INSTALACAO_HIDROMETRO == null || ServicoTipo.INSTALACAO_HIDROMETRO.isEmpty()){
				tipoOrdem = "";
			}else{
				tipoOrdem = "";

				for(Integer idServicoTipo : ServicoTipo.INSTALACAO_HIDROMETRO){
					tipoOrdem += "," + idServicoTipo.toString();
				}
			}

		}else if(tipoOrdem.substring(0).equalsIgnoreCase("SUBSTITUICAO")){

			if(ServicoTipo.SUBSTITUICAO_HIDROMETRO == null || ServicoTipo.SUBSTITUICAO_HIDROMETRO.isEmpty()){
				tipoOrdem = "";
			}else{
				tipoOrdem = "";
				for(Integer idServicoTipo : ServicoTipo.SUBSTITUICAO_HIDROMETRO){
					tipoOrdem += "," + idServicoTipo.toString();
				}
			}

		}

		if(!Util.isVazioOuBranco(tipoOrdem)){

			tipoOrdem = tipoOrdem.substring(1);
		}else{
			// Não existe serviço tipo associado a constante
			tipoOrdem = "-1";
		}

		// Verifica a Situacao da Ordem(Encerradas ou Pendentes)
		if(situacaoOrdemServico.equalsIgnoreCase("ENCERRADAS")){
			situacaoOrdem = OrdemServico.SITUACAO_ENCERRADO;
		}else if(situacaoOrdemServico.equalsIgnoreCase("PENDENTES")){
			situacaoOrdem = OrdemServico.SITUACAO_PENDENTE;
		}

		try{
			colecaoDados = repositorioOrdemServico.pesquisarOrdemServicoGerarRelatorioAcompanhamentoHidrometro(idEmpresa, tipoOrdem,
							situacaoOrdem, idLocalidade, dataEncerramentoInicial, dataEncerramentoFinal, tipoRelatorio);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDados != null && !colecaoDados.isEmpty()){
			Iterator iColecaoDados = colecaoDados.iterator();
			Object[] dados = null;

			RelacaoOrdensServicoEncerradasPendentesHelper Helper = null;

			while(iColecaoDados.hasNext()){
				Helper = new RelacaoOrdensServicoEncerradasPendentesHelper();
				dados = (Object[]) iColecaoDados.next();

				if(Util.converterStringParaInteger(tipoRelatorio).equals(
								RelatorioResumoOrdensServicoEncerradasPendentes.TIPO_RELATORIO_ANALITICO)
								|| situacaoOrdem.equals(OrdemServico.SITUACAO_PENDENTE)){

					// Id Ordem Servico
					Helper.setIdOrdemServico((Integer) dados[0]);
					// Id Imovel
					Helper.setIdImovel((Integer) dados[1]);
					// Id Localidade
					Helper.setIdLocalidade((Integer) dados[2]);
					// Codigo Setor Comercial
					Helper.setCodigoSetorComercial((Integer) dados[3]);
					// Numero Quadra
					Helper.setNumeroQuadra((Integer) dados[4]);
					// Lote
					Helper.setLote((Short) dados[5]);
					// SubLote
					Helper.setSubLote((Short) dados[6]);
					// Data Geracao
					String dataGeracao = dados[7].toString();
					dataGeracao = dataGeracao.substring(8, 10) + "/" + dataGeracao.substring(5, 7) + "/" + dataGeracao.substring(0, 4);
					Helper.setDataGeracao(dataGeracao);
					// Data Encerramento
					String dataEncerramento = "";
					if(!Util.isVazioOuBranco(dados[8])){
						dataEncerramento = dados[8].toString();
						dataEncerramento = dataEncerramento.substring(8, 10) + "/" + dataEncerramento.substring(5, 7) + "/"
										+ dataEncerramento.substring(0, 4);
						Helper.setDataEncerramento(dataEncerramento);
					}
					// Motivo Encerramento
					String motivoEncerramento = "";
					if(!Util.isVazioOuBranco(dados[8])){
						motivoEncerramento = (String) dados[9];
						Helper.setMotivoEncerramento(motivoEncerramento);
					}

				}else if(Util.converterStringParaInteger(tipoRelatorio).equals(
								RelatorioResumoOrdensServicoEncerradasPendentes.TIPO_RELATORIO_SINTETICO)){
					// Id Ordem Servico
					Helper.setMotivoEncerramento((String) dados[0]);
					// Id Imovel
					Helper.setQuantidadeOS((Long) dados[1]);
				}

				// Adiciona na Colecao
				retorno.add(Helper);
			}
		}

		return retorno;
	}

	/**
	 * [UC0640] Gerar TXT Ação de Cobrança
	 * 
	 * @author Raphael Rossiter
	 * @date 04/01/2008
	 * @param idCobrancaDocumento
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarOrdemServicoPorCobrancaDocumento(Integer idCobrancaDocumento) throws ControladorException{

		Integer retorno = null;

		try{
			retorno = repositorioOrdemServico.pesquisarOrdemServicoPorCobrancaDocumento(idCobrancaDocumento);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Método retorna uma instância de AtendimentoMotivoEncerramento
	 * 
	 * @author Saulo Lima
	 * @date 21/05/2009
	 * @param idAtendimentoMotivoEncerramento
	 * @return AtendimentoMotivoEncerramento
	 * @throws ControladorException
	 */
	public AtendimentoMotivoEncerramento pesquisarAtendimentoMotivoEncerramentoPorId(Integer idAtendimentoMotivoEncerramento)
					throws ControladorException{

		AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = null;

		if(idAtendimentoMotivoEncerramento != null){
			try{
				atendimentoMotivoEncerramento = this.repositorioOrdemServico
								.pesquisarAtendimentoMotivoEncerramentoPorId(idAtendimentoMotivoEncerramento);
			}catch(ErroRepositorioException ex){
				ex.printStackTrace();
				throw new ControladorException("erro.sistema", ex);
			}
		}

		return atendimentoMotivoEncerramento;
	}

	/**
	 * Método responsável por pesquisar os layouts de ordem de serviço.
	 * 
	 * @author Virgínia Melo
	 * @date 28/05/2009
	 * @return Coleção de OrdemServicoLayout.
	 * @throws ControladorException
	 */
	public Collection<OrdemServicoLayout> pesquisarOrdemServicoLayouts() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarOrdemServicoLayouts();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera todos os possíveis motivos de interrupção(ativos).
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção com todos os motivos de interrupcao ativos.
	 */
	public Collection<MotivoInterrupcao> pesquisarMotivoInterrupcao() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarMotivoInterrupcao();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera todos os locais de ocorrência;
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Coleção com todos os locais de ocorrencia ativos.
	 */
	public Collection<LocalOcorrencia> pesquisarLocalOcorrencia() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarLocalOcorrencia();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera um local de ocorrência;
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Local de ocorrencia.
	 */
	public LocalOcorrencia pesquisarLocalOcorrencia(Integer idLocalOcorrencia) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarLocalOcorrencia(idLocalOcorrencia);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de PavimentoRua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Rua
	 */
	public Collection<PavimentoRua> pesquisarPavimentoRua() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarPavimentoRua();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de PavimentoCalcada ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Calcada
	 */
	public Collection<PavimentoCalcada> pesquisarPavimentoCalcada() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarPavimentoCalcada();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de CausaVazamento ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Causa Vazamento
	 */
	public Collection<CausaVazamento> pesquisarCausaVazamento() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarCausaVazamento();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de Agente Externo ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Agente Externo
	 */
	public Collection<AgenteExterno> pesquisarAgenteExterno() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarAgenteExterno();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de Diametro Rede Agua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Rede Água
	 */
	public Collection<DiametroRedeAgua> pesquisarDiametroRedeAgua() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarDiametroRedeAgua();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de Diametro Ramal Agua ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Ramal Água
	 */
	public Collection<DiametroRamalAgua> pesquisarDiametroRamalAgua() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarDiametroRamalAgua();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de Diametro Rede Esgoto ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Rede Esgoto
	 */
	public Collection<DiametroRedeEsgoto> pesquisarDiametroRedeEsgoto() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarDiametroRedeEsgoto();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de Diametro Ramal Esgoto ativos.
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de Diametro Ramal Esgoto
	 */
	public Collection<DiametroRamalEsgoto> pesquisarDiametroRamalEsgoto() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarDiametroRamalEsgoto();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de MaterialRedeAgua
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRedeAgua
	 */
	public Collection<MaterialRedeAgua> pesquisarMaterialRedeAgua() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarMaterialRedeAgua();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de MaterialRamalAgua
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRamalAgua
	 */
	public Collection<MaterialRamalAgua> pesquisarMaterialRamalAgua() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarMaterialRamalAgua();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de MaterialRedeEsgoto
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRedeEsgoto
	 */
	public Collection<MaterialRedeEsgoto> pesquisarMaterialRedeEsgoto() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarMaterialRedeEsgoto();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de MaterialRamalEsgoto
	 * 
	 * @author Virgínia Melo
	 * @date 17/06/2009
	 * @return Coleção de MaterialRamalEsgoto
	 */
	public Collection<MaterialRamalEsgoto> pesquisarMaterialRamalEsgoto() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarMaterialRamalEsgoto();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera um PavimentoRua.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Rua
	 */
	public PavimentoRua pesquisarPavimentoRua(Integer idPavimento) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarPavimentoRua(idPavimento);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera um PavimentoCalcada.
	 * 
	 * @author Virgínia Melo
	 * @date 10/06/2009
	 * @return Pavimento Calcada
	 */
	public PavimentoCalcada pesquisarPavimentoCalcada(Integer idPavimento) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarPavimentoCalcada(idPavimento);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * 
	 * @author Virgínia Melo
	 * @date 18/06/2009
	 * @param dadosHelper
	 */
	private void inserirDadosAtividadesOrdemServico(DadosAtividadesOrdemServicoHelper dadosHelper) throws ControladorException{

		if(dadosHelper != null){

			DiametroRedeAgua diametroRedeAgua = null;
			DiametroRamalAgua diametroRamalAgua = null;
			DiametroCavaleteAgua diametroCavaleteAgua = null;

			DiametroRedeEsgoto diametroRedeEsgoto = null;
			DiametroRamalEsgoto diametroRamalEsgoto = null;

			MaterialRedeAgua materialRedeAgua = null;
			MaterialRamalAgua materialRamalAgua = null;
			MaterialCavaleteAgua materialCavaleteAgua = null;

			MaterialRedeEsgoto materialRedeEsgoto = null;
			MaterialRamalEsgoto materialRamalEsgoto = null;

			BigDecimal numeroPressao = null;
			BigDecimal numeroProfundidade = null;

			AgenteExterno agenteExterno = null;
			CausaVazamento causaVazamento = null;

			this.validarDadosAtividadesOrdemServico(dadosHelper.getColecaoManterDadosAtividadesOrdemServicoHelper(), dadosHelper
							.getOrdemServico().getServicoTipo());

			// Persiste os dados de Atividades(horas e materiais).
			if(!Util.isVazioOrNulo(dadosHelper.getColecaoManterDadosAtividadesOrdemServicoHelper())){

				this.manterDadosAtividadesOrdemServico(dadosHelper.getColecaoManterDadosAtividadesOrdemServicoHelper());

			}

			// Persiste os demais dados de acordo com os indicadores.
			if(dadosHelper.getOrdemServico() != null && dadosHelper.getOrdemServico().getServicoTipo() != null){

				try{

					ServicoTipo servicoTipo = dadosHelper.getOrdemServico().getServicoTipo();

					// Indicador Deslocamento
					if(servicoTipo.getIndicadorDeslocamento() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

						OrdemServicoDeslocamento osDeslocamento = dadosHelper.getOrdemServicoDeslocamento();

						if(osDeslocamento != null && osDeslocamento.getOrdemServicoProgramacao() != null){

							// Verificar se já existe um deslocamento cadastrado
							OrdemServicoDeslocamento osDeslocamentoCadastrado = repositorioOrdemServico
											.pesquisarOrdemServicoDeslocamento(osDeslocamento.getOrdemServicoProgramacao().getId());

							if(osDeslocamentoCadastrado != null){

								this.getControladorUtil().remover(osDeslocamentoCadastrado);
							}

							this.getControladorUtil().inserir(osDeslocamento);

							// Verificando se já existem interrupções de deslocamento cadastradas.
							Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcaoCadastrada = repositorioOrdemServico
											.pesquisarOSInterrupcaoDeslocamento(osDeslocamento.getOrdemServicoProgramacao().getId());

							if(colecaoInterrupcaoCadastrada != null && !colecaoInterrupcaoCadastrada.isEmpty()){

								for(OrdemServicoInterrupcaoDeslocamento osInterrupcaoDeslocamento : colecaoInterrupcaoCadastrada){

									this.getControladorUtil().remover(osInterrupcaoDeslocamento);
								}
							}

						}

						Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcao = dadosHelper
										.getColecaoOrdemServicoInterrupcaoDeslocamento();
						if(colecaoInterrupcao != null && !colecaoInterrupcao.isEmpty()){

							for(OrdemServicoInterrupcaoDeslocamento interrupcao : colecaoInterrupcao){

								// validar km interrupcao
								if(interrupcao.getNumeroKm().compareTo(osDeslocamento.getNumeroKmInicio()) == -1
												|| interrupcao.getNumeroKm().compareTo(osDeslocamento.getNumeroKmFim()) == 1){

									throw new ControladorException("atencao.km_interrupcao_fora_do_intervalo");
								}

								interrupcao.setOrdemServicoProgramacao(osDeslocamento.getOrdemServicoProgramacao());
								this.getControladorUtil().inserir(interrupcao);
							}
						}
					}

					// Indicador Rede Ramal Água
					if(servicoTipo.getIndicadorRedeRamalAgua() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

						// Recuperar dados rede agua
						if(dadosHelper.getIdRedeRamalAgua().equals(ConstantesSistema.INDICADOR_REDE)){

							diametroRedeAgua = dadosHelper.getDiametroRedeAgua();
							materialRedeAgua = dadosHelper.getMaterialRedeAgua();
						}else if(dadosHelper.getIdRedeRamalAgua().equals(ConstantesSistema.INDICADOR_RAMAL)){

							diametroRamalAgua = dadosHelper.getDiametroRamalAgua();
							materialRamalAgua = dadosHelper.getMaterialRamalAgua();
						}else if(dadosHelper.getIdRedeRamalAgua().equals(ConstantesSistema.INDICADOR_CAVALETE)){

							diametroCavaleteAgua = dadosHelper.getDiametroCavaleteAgua();
							materialCavaleteAgua = dadosHelper.getMaterialCavaleteAgua();
						}

						// Indicador Causa Ocorrencia
						if(servicoTipo.getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

							causaVazamento = dadosHelper.getCausaVazamentoAgua();
						}

						agenteExterno = dadosHelper.getAgenteExternoAgua();
						numeroProfundidade = dadosHelper.getProfundidadeAgua();
						numeroPressao = dadosHelper.getPressaoAgua();

					}else if(servicoTipo.getIndicadorRedeRamalEsgoto() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

						// Indicador Rede Ramal Esgoto
						// Recuperar dados rede esgoto
						if(dadosHelper.getIdRedeRamalEsgoto().equals(ConstantesSistema.INDICADOR_REDE)){

							diametroRedeEsgoto = dadosHelper.getDiametroRedeEsgoto();
							materialRedeEsgoto = dadosHelper.getMaterialRedeEsgoto();
						}else{

							diametroRamalEsgoto = dadosHelper.getDiametroRamalEsgoto();
							materialRamalEsgoto = dadosHelper.getMaterialRamalEsgoto();
						}

						// Indicador Causa Ocorrencia
						if(servicoTipo.getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

							causaVazamento = dadosHelper.getCausaVazamentoEsgoto();
						}

						agenteExterno = dadosHelper.getAgenteExternoEsgoto();
						numeroProfundidade = dadosHelper.getProfundidadeEsgoto();
						numeroPressao = dadosHelper.getPressaoEsgoto();

					}

					// Indicador Vala
					if(servicoTipo.getIndicadorVala() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

						// Verificar se existem OrdemServicoValaPavimento para essa OS, se tiver,
						// remover.
						Collection<OrdemServicoValaPavimento> colecaoValasAnteriores = this.repositorioOrdemServico
										.pesquisarOrdemServicoValaPavimento(dadosHelper.getOrdemServico().getId());

						if(colecaoValasAnteriores != null && !colecaoValasAnteriores.isEmpty()){
							for(OrdemServicoValaPavimento valaAnterior : colecaoValasAnteriores){

								this.getControladorUtil().remover(valaAnterior);
							}
						}

						// Recuperando e inserindo as novas valas.
						Collection<OrdemServicoValaPavimento> colecaoValaPavimento = dadosHelper.getColecaoOrdemServicoValaPavimento();

						if(colecaoValaPavimento != null && !colecaoValaPavimento.isEmpty()){
							for(OrdemServicoValaPavimento osValaPavimento : colecaoValaPavimento){

								this.getControladorUtil().inserir(osValaPavimento);
							}
						}

					}

					// Para as interrupcoes de execucao, sempre sera verificado se foram informadas
					// independentemente do indicador de execucao
					// pois apesar de ser marcado como não o usuário continua tendo a possibilidade
					// de
					// informar as horas.
					// Verificando se já existem interrupções de deslocamento cadastradas.
					if(dadosHelper.getOrdemServicoProgramacao() != null){

						Collection<OrdemServicoInterrupcaoExecucao> colecaoInterrupcaoExecucaoCadastrada = repositorioOrdemServico
										.pesquisarOSInterrupcaoExecucao(dadosHelper.getOrdemServicoProgramacao().getId());

						if(colecaoInterrupcaoExecucaoCadastrada != null && !colecaoInterrupcaoExecucaoCadastrada.isEmpty()){
							for(OrdemServicoInterrupcaoExecucao osInterrupcaoExecucao : colecaoInterrupcaoExecucaoCadastrada){

								this.getControladorUtil().remover(osInterrupcaoExecucao);
							}
						}

						// Cadastrando as interrupções de execução.
						Collection<OrdemServicoInterrupcaoExecucao> colecaoInterrupcaoExecucao = dadosHelper
										.getColecaoOrdemServicoInterrupcaoExecucao();
						if(colecaoInterrupcaoExecucao != null && !colecaoInterrupcaoExecucao.isEmpty()){
							for(OrdemServicoInterrupcaoExecucao interrupcao : colecaoInterrupcaoExecucao){

								interrupcao.setOrdemServicoProgramacao(dadosHelper.getOrdemServicoProgramacao());
								this.getControladorUtil().inserir(interrupcao);
							}
						}
					}else{

						throw new ControladorException("atencao.erro_persistencia_interrupcao_execucao");
					}

					// Recuperando Ordem de Servico que será atualizada
					OrdemServico osAtualizacao = this.repositorioOrdemServico.pesquisarOrdemServico(dadosHelper.getOrdemServico().getId());

					// Setando novos atributos
					osAtualizacao.setDiametroRedeAgua(diametroRedeAgua);
					osAtualizacao.setDiametroRamalAgua(diametroRamalAgua);
					osAtualizacao.setDiametroCavaleteAgua(diametroCavaleteAgua);

					osAtualizacao.setDiametroRedeEsgoto(diametroRedeEsgoto);
					osAtualizacao.setDiametroRamalEsgoto(diametroRamalEsgoto);

					osAtualizacao.setMaterialRedeAgua(materialRedeAgua);
					osAtualizacao.setMaterialRamalAgua(materialRamalAgua);
					osAtualizacao.setMaterialCavaleteAgua(materialCavaleteAgua);

					osAtualizacao.setMaterialRedeEsgoto(materialRedeEsgoto);
					osAtualizacao.setMaterialRamalEsgoto(materialRamalEsgoto);

					osAtualizacao.setNumeroPressao(numeroPressao);
					osAtualizacao.setNumeroProfundidade(numeroProfundidade);

					osAtualizacao.setAgenteExterno(agenteExterno);
					osAtualizacao.setCausaVazamento(causaVazamento);

					osAtualizacao.setUltimaAlteracao(new Date());

					// Atualizar
					this.getControladorUtil().atualizar(osAtualizacao);

				}catch(ErroRepositorioException e){

					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}catch(ControladorException e){

					sessionContext.setRollbackOnly();
					throw e;
				}

			}
		}

	}

	/**
	 * Recupera uma coleção de OrdemServicoValaPavimento a partir da Ordem de Serviço.
	 * 
	 * @author Virgínia Melo
	 * @date 22/06/2009
	 * @return Coleção de OrdemServicoValaPavimento
	 */
	public Collection<OrdemServicoValaPavimento> pesquisarOrdemServicoValaPavimento(Integer idOrdemServico) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarOrdemServicoValaPavimento(idOrdemServico);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Virgínia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idProgramacaoRoteiro, Integer idOrdemServico, Integer idEquipe)
					throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarOrdemServicoProgramacao(idProgramacaoRoteiro, idOrdemServico, idEquipe);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma OrdemServicoDeslocamento através do id da OS Programação.
	 * 
	 * @author Virgínia Melo
	 * @date 25/06/2009
	 * @return OrdemServicoDeslocamento
	 */
	public OrdemServicoDeslocamento pesquisarOrdemServicoDeslocamento(Integer idOsProgramacao) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarOrdemServicoDeslocamento(idOsProgramacao);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de OSInterrupcaoDeslocamento a partir da OS Programação
	 * 
	 * @author Virgínia Melo
	 * @date 22/06/2009
	 * @return Coleção de OSInterrupcaoDeslocamento
	 */
	public Collection<OrdemServicoInterrupcaoDeslocamento> pesquisarOSInterrupcaoDeslocamento(Integer idOsProgramacao)
					throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarOSInterrupcaoDeslocamento(idOsProgramacao);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de OSInterrupcaoExecucao a partir da OS Programação
	 * 
	 * @author Virgínia Melo
	 * @date 30/06/2009
	 * @return Coleção de OSInterrupcaoExecucao
	 */
	public Collection<OrdemServicoInterrupcaoExecucao> pesquisarOSInterrupcaoExecucao(Integer idOsProgramacao) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarOSInterrupcaoExecucao(idOsProgramacao);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Lista uma colecao de OrdemServicoFotoOcorrencia pesquisando por uma
	 * OrdemServicoFotoOcorrencia
	 * 
	 * @param OrdemServicoFotoOcorrencia
	 * @return Collection<OrdemServicoFotoOcorrencia>
	 * @throws ControladorException
	 */
	public Collection<OrdemServicoFotoOcorrencia> listarOSFoto(OrdemServicoFotoOcorrencia osFoto) throws ControladorException{

		try{
			return this.repositorioOrdemServico.listarOSFoto(osFoto);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisa o último número sequencial das fotos, pelo id da ordem de servico
	 * 
	 * @param idOrdemServico
	 * @return numeroSequencia
	 * @throws ControladorException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServico(Integer idOrdemServico) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarQuantidadeFotosOrdemServico(idOrdemServico);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisa o último número sequencial das fotos, pelo id da ordem de servico e o id da ordem
	 * servico programacao
	 * 
	 * @param idOrdemServico
	 * @param idOrdemServicoProgramacao
	 * @return numeroSequencia
	 * @throws ControladorException
	 */
	public Integer pesquisarQuantidadeFotosOrdemServicoProgramacao(Integer idOrdemServico, Integer idOrdemServicoProgramacao)
					throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarQuantidadeFotosOrdemServicoProgramacao(idOrdemServico, idOrdemServicoProgramacao);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Gerar Ordens Servico Seletiva
	 * Método usado em Emitir Ordem Serviço Seletiva
	 * 
	 * @throws ControladorException
	 */
	public byte[] gerarOrdensServicoSeletiva(Collection colecaoDadosRelatorio, OrdemServicoSeletivaHelper helper,
					int idFuncionalidadeIniciada, String nomeRelatorio) throws ControladorException{

		byte[] retornoRelatorioOrdemServico = null;

		try{
			// dados para o relatorio
			if(colecaoDadosRelatorio != null && !colecaoDadosRelatorio.isEmpty()){

				Integer idEmpresa = null;
				Integer idOrdemServico = null;
				Integer idImovel = null;
				Integer idComandoOsSeletiva = helper.getIdComandoOsSeletiva();

				idEmpresa = Util.converterStringParaInteger(helper.getFirma());

				FiltroUsuario filtro = new FiltroUsuario();
				filtro.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, helper.getIdUsuario()));
				filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroUsuario.UNIDADE_ORGANIZACIONAL);
				Collection colecaoUsuario = getControladorUtil().pesquisar(filtro, Usuario.class.getName());

				if(colecaoUsuario == null || colecaoUsuario.isEmpty()){
					throw new IllegalStateException("pesquisa.usuario.inexistente");
				}

				Usuario usuario = null;
				usuario = (Usuario) colecaoUsuario.iterator().next();

				// Imprimir pagina com os parametros informados e a quantidade de imoveis
				// selecionados.
				List<Integer> idsImoveis = new ArrayList<Integer>();

				Iterator iColecaoDadosRelatorio = colecaoDadosRelatorio.iterator();

				while(iColecaoDadosRelatorio.hasNext()){
					idImovel = (Integer) iColecaoDadosRelatorio.next();
					idsImoveis.add(idImovel);
				}

				// ......................................................................................................................................
				// GERANDO ORDENS DE SERVIÇO
				// ......................................................................................................................................
				Map<Integer, Integer> mapOdensServico = this.gerarOrdensServico(idsImoveis, Integer.valueOf(helper.getServicoTipo()),
								idEmpresa, usuario, idComandoOsSeletiva);
				// ......................................................................................................................................

				Set<Integer> chaves = mapOdensServico.keySet();

				List<OrdemServico> listaOrdemServico = new ArrayList<OrdemServico>();

				for(Integer chave : chaves){
					idImovel = chave;
					idOrdemServico = mapOdensServico.get(chave);

					OrdemServico ordemServico = this.recuperaOSPorId(idOrdemServico);
					listaOrdemServico.add(ordemServico);
				}

				// Gera o relatorio de OS para exibição

				FuncionalidadeIniciada fuin = null;
				String tipoArquivo = ConstantesSistema.PDF;

				FiltroFuncionalidadeIniciada ffuin = new FiltroFuncionalidadeIniciada();
				ffuin.adicionarParametro(new ParametroSimples(FiltroFuncionalidadeIniciada.ID, idFuncionalidadeIniciada));
				ffuin.adicionarCaminhoParaCarregamentoEntidade(FiltroFuncionalidadeIniciada.FUNCIONALIDADE);
				Collection colecaoFuin = getControladorUtil().pesquisar(ffuin, FuncionalidadeIniciada.class.getName());
				if(colecaoFuin != null && !colecaoFuin.isEmpty()){
					fuin = (FuncionalidadeIniciada) Util.retonarObjetoDeColecao(colecaoFuin);
					if(fuin != null && fuin.getProcessoFuncionalidade() != null
									&& fuin.getProcessoFuncionalidade().getFuncionalidade() != null){
						tipoArquivo = ParametroOrdemServico.P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA.executar(fuin
										.getProcessoFuncionalidade().getFuncionalidade().getId());
					}

				}

				if(tipoArquivo.equals(ConstantesSistema.TXT)){
					retornoRelatorioOrdemServico = GeradorRelatorioOrdemServico.getInstancia().gerarTxtOrdemServico(listaOrdemServico);
				}else{
					helper.setTipoFormatoRelatorio(TarefaRelatorio.TIPO_PDF);

					GeradorRelatorioOrdemServico instanciaGeradorRelatorioOrdemServico = GeradorRelatorioOrdemServico.getInstancia();

					// Armazena que a chamada está vindo da tela de Emitir Ordem de Serviço Seletiva
					instanciaGeradorRelatorioOrdemServico.addParametro("telaOSSeletiva",
									ConstantesSistema.INDICADOR_CHAMADA_TELA_ORDEM_SERVICO_SELETIVA);

					retornoRelatorioOrdemServico = instanciaGeradorRelatorioOrdemServico.gerarRelatorioOrdemServico(listaOrdemServico);
				}

				this.enviarRelatorioListagemImoveisParaBatch(mapOdensServico, helper, usuario, nomeRelatorio);

			}

		}catch(ControladorException e){
			throw new ControladorException(e.getMessage(), e);
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retornoRelatorioOrdemServico;
	}

	/**
	 * Gerar Ordens Servico do tipo Ordem Seletiva Seletiva
	 * Método usado em Emitir Ordem Serviço Seletiva
	 * 
	 * @throws ControladorException
	 */
	private Map<Integer, Integer> gerarOrdensServico(List<Integer> idsImoveis, Integer idTipoServico, Integer idEmpresa, Usuario usuario,
					Integer idComandoOSSeletiva) throws ControladorException{

		Empresa empresa = new Empresa();
		empresa.setId(idEmpresa);

		ServicoTipo servicoTipo = null;

		FiltroServicoTipo filtroServico = new FiltroServicoTipo();
		filtroServico.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idTipoServico));

		Collection<ServicoTipo> colecaoServicoTipo = getControladorUtil().pesquisar(filtroServico, ServicoTipo.class.getName());

		if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
			servicoTipo = colecaoServicoTipo.iterator().next();
		}

		// [UC0430] - Gerar Ordem de Servico
		Map<Integer, Integer> f = gerarOrdemServicoSeletiva(idsImoveis, servicoTipo, empresa, usuario, idComandoOSSeletiva);

		return f;
	}

	/**
	 * Enviar Relatorio Listagem Imoveis Para Batch
	 * Método usado em Emitir Ordem Serviço Seletiva
	 */
	private void enviarRelatorioListagemImoveisParaBatch(Map<Integer, Integer> mapOdensServico, OrdemServicoSeletivaHelper helper,
					Usuario usuario, String nomeRelatorio){

		try{

			String codigoParametro = this.obterCodigoParametroMetodoRelatorioTipo(Integer.valueOf(helper.getServicoTipo()));

			SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

			if(!Util.isVazioOuBranco(codigoParametro)
							&& (sistemaParametro.getParmId().intValue() == SistemaParametro.INDICADOR_EMPRESA_DESO.intValue() || sistemaParametro
											.getParmId().intValue() == SistemaParametro.INDICADOR_EMPRESA_CASAL.intValue())){
				/*
				 * [UC0713] Emitir Ordem de Serviço Seletiva
				 * 4.1.3.1. Executar o método correspondente ao subfluxo de geração do relatório dos
				 * imóveis selecionados para a geração das ordens seletivas
				 */
				ParametroAtendimentoPublico parametro = new ParametroAtendimentoPublico(codigoParametro);
				parametro.executar(this, -1, mapOdensServico, helper, usuario, nomeRelatorio);

			}else{
				RelatorioOrdemServicoListarImoveis relatorioListar = new RelatorioOrdemServicoListarImoveis(usuario, nomeRelatorio);
				relatorioListar.addParametro("mapOdensServico", mapOdensServico);
				relatorioListar.addParametro("helper", helper);
				relatorioListar.addParametro("tipoFormatoRelatorio", helper.getTipoFormatoRelatorio());
				getControladorBatch().iniciarProcessoRelatorio(relatorioListar);

			}

		}catch(ControladorException e){
			throw new SistemaException(e, "Erro Batch Relatório");
		}

	}

	private String obterCodigoParametroMetodoRelatorioTipo(Integer idServicoTipo) throws ControladorException{

		String codigoParametro = "";
		String[] listaRelacionamentoServicoModelo = null;
		String parametroRelacionamentoServicoModelo = (String) ParametroAtendimentoPublico.P_LISTA_SERVICOS_COM_RELATORIO_OS_SELETIVA
						.executar();

		listaRelacionamentoServicoModelo = parametroRelacionamentoServicoModelo.split(",");

		// Percorre a lista com os tipos de servico e o identificador do parametro que indica o tipo
		// do layout do relatório
		for(int i = 0; i < listaRelacionamentoServicoModelo.length; i++){
			String relacionamento = listaRelacionamentoServicoModelo[i];
			Integer idServicoTipoRelacionamento = Integer.valueOf(relacionamento.substring(0, 4));

			if(idServicoTipo.equals(idServicoTipoRelacionamento)){
				codigoParametro = relacionamento.substring(4);
				return codigoParametro;
			}
		}

		return codigoParametro;
	}

	/**
	 * Gerar Arquivo Txt
	 * Método usado em Emitir Ordem Serviço Seletiva
	 */
	private void gerarArquivoTxt(StringBuilder txtArquivo, String tipoOrdem, String nomeElo, String nomeLocalidadeIncial,
					String nomeLocalidadeFinal) throws IOException, FileNotFoundException, Exception{

		if(txtArquivo != null && txtArquivo.length() != 0){
			String nomeZip = "OS_";

			if(nomeElo != null && !nomeElo.equals("")){
				nomeZip += nomeElo + "_" + Util.formatarDataComHora(new Date()).replace("/", "_").replace(":", "_");
			}else if(nomeLocalidadeIncial != null && !nomeLocalidadeIncial.equals("") && nomeLocalidadeFinal != null
							&& !nomeLocalidadeFinal.equals("")){

				nomeZip += nomeLocalidadeIncial + "_" + nomeLocalidadeFinal + "_"
								+ Util.formatarDataComHora(new Date()).replace("/", "_").replace(":", "_");
			}else{
				nomeZip += Util.formatarDataComHora(new Date()).replace("/", "_").replace(":", "_");
			}
			nomeZip = nomeZip.replace(" ", "_");

			txtArquivo.append("\u0004");

			try{
				// criar o arquivo zip
				File compactado = new File(nomeZip + ".zip"); // nomeZip
				ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(compactado));

				File leitura = new File(nomeZip + ".txt");
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leitura.getAbsolutePath())));
				out.write(txtArquivo.toString());
				out.close();
				ZipUtil.adicionarArquivo(zos, leitura);

				// close the stream
				zos.close();
				leitura.delete();
			}catch(IOException e){
				throw new IOException();
			}catch(Exception e){
				throw new Exception(e);
			}
		}
	}

	/**
	 * Recupera uma coleção de Diametro Rede Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 17/06/2009
	 * @return Coleção de Diametro Rede Água
	 */
	public Collection<DiametroCavaleteAgua> pesquisarDiametroCavaleteAgua() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarDiametroCavaleteAgua();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera uma coleção de Material Rede Agua ativos.
	 * 
	 * @author Yara Souza
	 * @date 17/06/2009
	 * @return Coleção de Material Rede Água
	 */
	public Collection<MaterialCavaleteAgua> pesquisarMaterialCavaleteAgua() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarMaterialCavaleteAgua();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * @author isilva
	 * @param idsOS
	 * @return
	 * @throws ControladorException
	 */
	public Collection<OrdemServico> pesquisarOrdensServicos(List<Integer> idsOS) throws ControladorException{

		Collection<OrdemServico> colecaoRetorno = new ArrayList<OrdemServico>();

		try{

			if(idsOS != null && !idsOS.isEmpty()){
				int qtdBlocosConsultas = Util.dividirArredondarResultado(idsOS.size(),
								ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO, BigDecimal.ROUND_UP);
				for(int i = 0; i < qtdBlocosConsultas; i++){

					List<Integer> blocoIds = new ArrayList<Integer>();
					blocoIds.addAll(Util
									.obterSubListParaPaginacao(idsOS, i, ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO));

					Collection<OrdemServico> colecaoOrdemServico = this.repositorioOrdemServico.pesquisarOrdensServicos(blocoIds);

					if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
						colecaoRetorno.addAll(colecaoOrdemServico);
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return colecaoRetorno;
	}

	/**
	 * @author isilva
	 * @param idOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<RoteiroOSDadosProgramacaoHelper> pesquisarProgramacaoOrdemServicoRoteiroEquipe(Integer idOrdemServico)
					throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarProgramacaoOrdemServicoRoteiroEquipe(idOrdemServico);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Retorna o maior Sequêncial da Ordem de Serviço Programação, de uma Data para uma Equipe.
	 * 
	 * @param idEquipe
	 * @param dtRoteiro
	 * @return
	 * @throws ControladorException
	 */
	public Integer maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(Integer idEquipe, Date dtRoteiro) throws ControladorException{

		try{
			return this.repositorioOrdemServico.maiorSquencialProgramacaoOrdemServicoRoteiroEquipe(idEquipe, dtRoteiro);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/*
	 * Verifica se já existe Ordem de Servico de
	 * Instalacao/Substituicao de Hidrometro para o Imovel
	 * @author Ivan Sérgio
	 * @date 19/11/2007
	 */
	public boolean verificarOrdemServicoInstalacaoSubstituicaoImovel(Integer idImovel, Integer idServicoTipo) throws ControladorException{

		boolean retorno = false;
		Collection retornoRepositorio = null;
		Date data = null;
		String dataValidade = "";

		try{
			// Data Validade - 90 dias
			data = Util.subtrairNumeroDiasDeUmaData(new Date(), 90);
			dataValidade = String.valueOf(Util.getAno(data));

			// Adiciona 0 na frente do mes
			if(Util.getMes(data) < 10){
				dataValidade += "0" + Util.getMes(data);
			}else{
				dataValidade += Util.getMes(data);
			}

			// Adiciona 0 no dia
			if(Util.getDiaMes(data) < 10){
				dataValidade += "0" + Util.getDiaMes(data);
			}else{
				dataValidade += Util.getDiaMes(data);
			}

			retornoRepositorio = repositorioOrdemServico.verificarOrdemServicoInstalacaoSubstituicaoImovel(idImovel, dataValidade,
							idServicoTipo);

			// Verifica se a consulta retornou resultado
			if(retornoRepositorio != null && retornoRepositorio.size() > 0){
				retorno = true;
			}

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){

			}
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	private static void showMem(String classe, String metodo, String flag){

		System.out.println(flag);

		System.out.println(classe);

		System.out.println(metodo);

		System.out.println("Free memory (bytes): "

		+ Runtime.getRuntime().freeMemory());

		long maxMemory = Runtime.getRuntime().maxMemory();

		System.out.println("Maximum memory (bytes): "

		+ (maxMemory == Long.MAX_VALUE ? "no limit" : maxMemory));

		System.out.println("Total memory (bytes): "

		+ Runtime.getRuntime().totalMemory());

	}

	public Collection verificarImoveisSemOrdemPendentePorTipoServico(Collection colecaoImoveis, Integer idServicoTipo)
					throws ControladorException{

		Collection retornoRepositorio = null;

		try{

			retornoRepositorio = repositorioOrdemServico.verificarImoveisSemOrdemPendentePorTipoServico(colecaoImoveis, idServicoTipo);

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){
				throw new ControladorException("erro.sistema", ex);
			}
			throw new ControladorException("erro.sistema", e);
		}

		return retornoRepositorio;
	}

	/**
	 * [UC0711] Filtro para Emissao de Ordens Seletivas
	 * 
	 * @author Anderson Lima
	 * @date 13/03/2010
	 *       Pesquisar o total de registros a serem processados
	 */
	public Integer filtrarImovelEmissaoOrdensSeletivasCount(OrdemServicoSeletivaHelper ordemServicoSeletivaHelper)
					throws ControladorException{

		Integer totalRegistros = 0;
		String anormalidades = "";

		try{
			// Verifica as Anormalidades Selecionadas
			if(ordemServicoSeletivaHelper.getAnormalidadeHidrometro() != null){

				for(int i = 0; i < ordemServicoSeletivaHelper.getAnormalidadeHidrometro().length; i++){

					if(!ordemServicoSeletivaHelper.getAnormalidadeHidrometro()[i].trim().equalsIgnoreCase(
									Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO).toString())){
						anormalidades += ordemServicoSeletivaHelper.getAnormalidadeHidrometro()[i].toString() + ",";
					}
				}

				if(!anormalidades.equals("")){

					// Retira a ultima virgula
					anormalidades = anormalidades.substring(0, anormalidades.length() - 1);
				}else{

					anormalidades = null;
				}
			}

			Boolean substituicaoHidrometro = this.comparaServicoTipoSubgrupoSubstituicaoHidrometro(ordemServicoSeletivaHelper
							.getServicoTipo());
			Boolean corte = this.comparaServicoTipoSubgrupoCorte(ordemServicoSeletivaHelper.getServicoTipo());


			SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();
			Integer anoMesFaturamentoAtual = sistemaParametros.getAnoMesFaturamento();
			String situacaLigacaoAguaPermitidaManutencao = ParametroOrdemServico.P_SIT_AGUA_PERMIT_OS_SELETIVA_MANUT_HIDROMETRO.executar()
							.toString();
			String situacaLigacaoAguaPermitidaCorte = ParametroOrdemServico.P_SIT_AGUA_PERMIT_OS_SELETIVA_CORTE.executar().toString();

			totalRegistros = repositorioOrdemServico.filtrarImovelEmissaoOrdensSeletivasCount(ordemServicoSeletivaHelper, anormalidades,
							substituicaoHidrometro, corte, anoMesFaturamentoAtual, situacaLigacaoAguaPermitidaManutencao,
							situacaLigacaoAguaPermitidaCorte);

			if(totalRegistros == null){
				totalRegistros = 0;
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return totalRegistros;
	}

	/**
	 * Faz a reprogramação das ordens de serviço passadas como parâmetro para a data e equipe
	 * passadas também.
	 * 
	 * @param ordensServico
	 *            - ordens de serviço a reprogramar
	 * @param novaEquipe
	 * @param dataRoteiro
	 *            - a data atual do roteiro
	 * @param novaDataRoteiro
	 *            - a nova data do roteiro
	 * @param usuario
	 *            - usuário do sistema que fez a reprogramação
	 * @throws ControladorException
	 * @author Rodrigo Oliveira
	 */
	public void reprogramarOrdensServico(List<OrdemServico> ordensServico, Equipe novaEquipe, Date dataRoteiro, Date novaDataRoteiro,
					Usuario usuario) throws ControladorException{

		try{
			for(Iterator iteratorOrdensServico = ordensServico.iterator(); iteratorOrdensServico.hasNext();){
				OrdemServico ordemServico = (OrdemServico) iteratorOrdensServico.next();

				OrdemServicoProgramacao osProgramacao = pesquisarOrdemServicoProgramacaoPorOrdemServicoEDataRoteiro(ordemServico,
								dataRoteiro);
				Equipe equipeAntiga = osProgramacao.getEquipe();

				// verifica se as duas datas são iguais
				if(dataRoteiro.compareTo(novaDataRoteiro) == 0){
					// se as duas datas são iguais não precisa verificar se existe programação para
					// excluir
					alterarDadosOrdemServicoProgramacaoERoteiro(osProgramacao, novaDataRoteiro, novaEquipe, usuario);
				}else{
					// verifica se já existe uma programacao para essa OS na novaDataRoteiro
					OrdemServicoProgramacao osProgramacaoNaDataDestino = pesquisarOrdemServicoProgramacaoPorOrdemServicoEDataRoteiro(
									ordemServico, novaDataRoteiro);
					if(osProgramacaoNaDataDestino != null){
						// se já existe, remove a programacao que ja esta salva na data de destino

						// antes de excluir esse roteiro, verificar se ele esta associado a outras
						// OSP
						if(pesquisarQuantidadeOrdemServicoProgramacaoPorRoteiroProgramacao(osProgramacaoNaDataDestino
										.getProgramacaoRoteiro()) == 1){
							// remover a OSProgramacao e a ProgramacaoRoteiro
							ProgramacaoRoteiro programacaoRoteiroExcluir = osProgramacaoNaDataDestino.getProgramacaoRoteiro();
							getControladorUtil().remover(osProgramacaoNaDataDestino);
							getControladorUtil().remover(programacaoRoteiroExcluir);
						}else{
							// remover só a OSProgramacao
							getControladorUtil().remover(osProgramacaoNaDataDestino);
						}
					}
					alterarDadosOrdemServicoProgramacaoERoteiro(osProgramacao, novaDataRoteiro, novaEquipe, usuario);
				}
				reordenarSequencialProgramacao(dataRoteiro, equipeAntiga.getId());
				reordenarSequencialProgramacao(novaDataRoteiro, novaEquipe.getId());
			}
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
	}

	public OrdemServico pesquisarOSPorDocumentoCobranca(Integer idCobrancaDocumento) throws ControladorException{

		OrdemServico retorno = null;

		try{

			retorno = repositorioOrdemServico.pesquisarOSPorDocumentoCobranca(idCobrancaDocumento);

		}catch(ErroRepositorioException e){
			try{
				sessionContext.setRollbackOnly();
			}catch(IllegalStateException ex){
				throw new ControladorException("erro.sistema", ex);
			}
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	private OrdemServicoProgramacao pesquisarOrdemServicoProgramacaoPorOrdemServicoEDataRoteiro(OrdemServico ordemServico, Date dataRoteiro)
					throws ControladorException{

		OrdemServicoProgramacao ordemServicoProgramacao = null;
		FiltroOrdemServicoProgramacao filtroOSProgramacao = new FiltroOrdemServicoProgramacao();
		filtroOSProgramacao.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.ORDEM_SERVICO, ordemServico));
		filtroOSProgramacao.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.PROGRAMACAO_ROTEIRO_DATA, dataRoteiro));
		filtroOSProgramacao.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.INDICADOR_ATIVO, ConstantesSistema.SIM));
		filtroOSProgramacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServicoProgramacao.PROGRAMACAO_ROTEIRO);
		filtroOSProgramacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServicoProgramacao.EQUIPE);
		Collection colecaoOrdemServicoProgramacao = getControladorUtil().pesquisar(filtroOSProgramacao,
						OrdemServicoProgramacao.class.getName());

		ordemServicoProgramacao = (OrdemServicoProgramacao) Util.retonarObjetoDeColecao(colecaoOrdemServicoProgramacao);
		return ordemServicoProgramacao;
	}

	private int pesquisarQuantidadeOrdemServicoProgramacaoPorRoteiroProgramacao(ProgramacaoRoteiro programacaoRoteiro)
					throws ControladorException{

		OrdemServicoProgramacao ordemServicoProgramacao = null;
		FiltroOrdemServicoProgramacao filtroOSProgramacao = new FiltroOrdemServicoProgramacao();
		filtroOSProgramacao.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.PROGRAMACAO_ROTEIRO, programacaoRoteiro));
		filtroOSProgramacao.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.INDICADOR_ATIVO, ConstantesSistema.SIM));
		int quantidadeOrdemServicoProgramacao = getControladorUtil().totalRegistrosPesquisa(filtroOSProgramacao,
						OrdemServicoProgramacao.class.getName());

		return quantidadeOrdemServicoProgramacao;
	}

	private void alterarDadosOrdemServicoProgramacaoERoteiro(OrdemServicoProgramacao osProgramacao, Date novaDataRoteiro, Equipe equipe,
					Usuario usuario) throws ControladorException{

		Date dataUltimaAlteracao = new Date();

		ProgramacaoRoteiro programacaoRoteiro = osProgramacao.getProgramacaoRoteiro();
		if(pesquisarQuantidadeOrdemServicoProgramacaoPorRoteiroProgramacao(programacaoRoteiro) > 1){
			// se esse programacaoRoteiro tem outras OrdemServicoProgramacao associadas a ele
			// criamos um
			// novo ProgramacaoRoteiro para associar à essa OrdemServicoProgramacao
			ProgramacaoRoteiro novoProgramacaoRoteiro = new ProgramacaoRoteiro();
			novoProgramacaoRoteiro.setDataRoteiro(novaDataRoteiro);
			novoProgramacaoRoteiro.setUltimaAlteracao(dataUltimaAlteracao);
			novoProgramacaoRoteiro.setIndicadorReprogramacao(ConstantesSistema.SIM);
			novoProgramacaoRoteiro.setUnidadeOrganizacional(usuario.getUnidadeOrganizacional());

			osProgramacao.setProgramacaoRoteiro(novoProgramacaoRoteiro);
			getControladorUtil().inserir(novoProgramacaoRoteiro);
		}else{
			programacaoRoteiro.setDataRoteiro(novaDataRoteiro);
			programacaoRoteiro.setUltimaAlteracao(dataUltimaAlteracao);
			programacaoRoteiro.setIndicadorReprogramacao(ConstantesSistema.SIM);
			getControladorUtil().atualizar(programacaoRoteiro);
		}

		osProgramacao.setEquipe(equipe);
		osProgramacao.setUsuarioProgramacao(usuario);
		osProgramacao.setUltimaAlteracao(dataUltimaAlteracao);

		getControladorUtil().atualizar(osProgramacao);
	}

	public List<OrdemServico> listarOrdensServicoOrdenadasPorInscricao(List<Integer> listaIdentificadoresOS) throws ControladorException{

		List<OrdemServico> colecaoRetorno = new ArrayList<OrdemServico>();

		try{

			if(listaIdentificadoresOS != null && !listaIdentificadoresOS.isEmpty()){
				int qtdBlocosConsultas = Util.dividirArredondarResultado(listaIdentificadoresOS.size(),
								ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO, BigDecimal.ROUND_UP);
				for(int i = 0; i < qtdBlocosConsultas; i++){

					List<Integer> blocoIds = new ArrayList<Integer>();
					blocoIds.addAll(Util.obterSubListParaPaginacao(listaIdentificadoresOS, i,
									ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO));

					List<OrdemServico> colecaoOrdemServico = this.repositorioOrdemServico
									.pequisarOrdensServicosOrdenadasPorInscricao(blocoIds);

					if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
						colecaoRetorno.addAll(colecaoOrdemServico);
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return colecaoRetorno;
	}

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relatório de Produtividade de Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 13/04/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @param tipoOrdenacao
	 * @return Collection
	 */
	public Collection pesquisarGerarRelatorioProdutividadeEquipe(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String tipoOrdenacao, String idLocalidade)
					throws ControladorException{

		Collection colecaoDadosOS = null;
		Collection<RelatorioProdutividadeEquipeHelper> retorno = new ArrayList();

		try{

			colecaoDadosOS = repositorioOrdemServico.pesquisarGerarRelatorioProdutividadeEquipe(origemServico, situacaoOS,
							idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, tipoOrdenacao, idLocalidade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosOS != null && !colecaoDadosOS.isEmpty()){

			Iterator colecaoDadosOSIterator = colecaoDadosOS.iterator();

			Object[] dadosOS = null;

			RelatorioProdutividadeEquipeHelper relatorioProdutividadeEquipeHelper = null;

			while(colecaoDadosOSIterator.hasNext()){

				relatorioProdutividadeEquipeHelper = new RelatorioProdutividadeEquipeHelper();

				dadosOS = (Object[]) colecaoDadosOSIterator.next();

				if(dadosOS[0] != null){ // 0
					relatorioProdutividadeEquipeHelper.setTempoMedio((Integer) dadosOS[0]);
				}

				if(dadosOS[1] != null){ // 1
					relatorioProdutividadeEquipeHelper.setQtd((Integer) dadosOS[1]);
				}

				if(dadosOS[2] != null){ // 2
					relatorioProdutividadeEquipeHelper.setIdUnidade((Integer) dadosOS[2]);
				}

				if(dadosOS[3] != null){ // 3
					relatorioProdutividadeEquipeHelper.setDescricaoUnidade((String) dadosOS[3]);
				}

				if(dadosOS[4] != null){ // 4
					relatorioProdutividadeEquipeHelper.setIdEquipe((Integer) dadosOS[4]);
				}

				if(dadosOS[5] != null){ // 5
					relatorioProdutividadeEquipeHelper.setDescricaoEquipe((String) dadosOS[5]);
				}

				if(dadosOS[6] != null){ // 6
					relatorioProdutividadeEquipeHelper.setIdServicoTipo((Integer) dadosOS[6]);
				}

				if(dadosOS[7] != null){ // 7
					relatorioProdutividadeEquipeHelper.setDescricaoServicoTipo((String) dadosOS[7]);
				}

				if(dadosOS[8] != null){ // 8
					relatorioProdutividadeEquipeHelper.setTempoPadrao((Integer) dadosOS[8]);
				}

				if(!retorno.contains(relatorioProdutividadeEquipeHelper)){
					retorno.add(relatorioProdutividadeEquipeHelper);
				}
			}
		}

		if(tipoOrdenacao != null && tipoOrdenacao.equals("2")){

			// Organizar a coleção

			Collections.sort((List) retorno, new Comparator() {

				public int compare(Object a, Object b){

					Integer tipoServico1 = ((RelatorioProdutividadeEquipeHelper) a).getIdServicoTipo();
					Integer tipoServico2 = ((RelatorioProdutividadeEquipeHelper) b).getIdServicoTipo();

					if(!tipoServico1.equals(tipoServico2)){
						return tipoServico1.compareTo(tipoServico2);
					}else{
						return 0;
					}
				}
			});

		}

		return retorno;
	}

	/**
	 * Retorna o resultado da pesquisa para a pesquisa
	 * [UC0492] - Gerar Relatório de Produtividade de Equipe
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 13/04/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return int
	 */
	public int pesquisarGerarRelatorioProdutividadeEquipeCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException{

		int retorno = 0;

		try{

			retorno = repositorioOrdemServico.pesquisarGerarRelatorioProdutividadeEquipeCount(origemServico, situacaoOS, idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, idLocalidade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço não Executados por Equipe"
	 * 
	 * @author Péricles TAvares
	 * @date 09/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return int
	 */
	public int pesquisarGerarRelatorioResumoOSNaoExecutadasEquipeCount(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException{

		int retorno = 0;

		try{

			retorno = repositorioOrdemServico.pesquisarGerarRelatorioResumoOSNaoExecutadasEquipeCount(origemServico, situacaoOS,
							idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, idLocalidade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Retorna o resultado da pesquisa para
	 * "Gerar relatório Resumo de Ordens de Serviço não Executados por Equipe"
	 * 
	 * @author Péricles TAvares
	 * @date 09/05/2011
	 * @param origemServico
	 * @param situacaoOS
	 * @param idsServicosTipos
	 * @param idUnidadeAtendimento
	 * @param idUnidadeAtual
	 * @param idUnidadeEncerramento
	 * @param periodoAtendimentoInicial
	 * @param periodoAtendimentoFinal
	 * @param periodoEncerramentoInicial
	 * @param periodoEncerramentoFinal
	 * @param idEquipeProgramacao
	 * @param idEquipeExecucao
	 * @return Collection
	 */
	public Collection pesquisarGerarRelatorioResumoOSNaoExecutadasEquipe(String origemServico, String situacaoOS,
					String[] idsServicosTipos, String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento,
					Date periodoAtendimentoInicial, Date periodoAtendimentoFinal, Date periodoEncerramentoInicial,
					Date periodoEncerramentoFinal, String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade)
					throws ControladorException{

		Collection colecaoDadosOS = null;
		Collection<RelatorioOSNaoExecutadaEquipeHelper> retorno = new ArrayList();

		try{

			colecaoDadosOS = repositorioOrdemServico.pesquisarGerarRelatorioResumoOSNaoExecutadasEquipe(origemServico, situacaoOS,
							idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, idLocalidade);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		if(colecaoDadosOS != null && !colecaoDadosOS.isEmpty()){

			Iterator colecaoDadosOSIterator = colecaoDadosOS.iterator();

			Object[] dadosOS = null;

			RelatorioOSNaoExecutadaEquipeHelper relatorioOSNaoExecutadaEquipeHelper = null;

			while(colecaoDadosOSIterator.hasNext()){

				relatorioOSNaoExecutadaEquipeHelper = new RelatorioOSNaoExecutadaEquipeHelper();

				dadosOS = (Object[]) colecaoDadosOSIterator.next();

				if(dadosOS[0] != null){ // 0
					relatorioOSNaoExecutadaEquipeHelper.setIdUnidade((Integer) dadosOS[0]);
				}

				if(dadosOS[1] != null){ // 1
					relatorioOSNaoExecutadaEquipeHelper.setDescricaoUnidade((String) dadosOS[1]);
				}

				if(dadosOS[2] != null){ // 2
					relatorioOSNaoExecutadaEquipeHelper.setIdEquipe((Integer) dadosOS[2]);
				}

				if(dadosOS[3] != null){ // 3
					relatorioOSNaoExecutadaEquipeHelper.setDescricaoEquipe((String) dadosOS[3]);
				}

				if(dadosOS[4] != null){ // 4
					relatorioOSNaoExecutadaEquipeHelper.setIdServicoTipo((Integer) dadosOS[4]);
				}

				if(dadosOS[5] != null){ // 5
					relatorioOSNaoExecutadaEquipeHelper.setDescricaoServicoTipo((String) dadosOS[5]);
				}

				if(dadosOS[6] != null){ // 6
					relatorioOSNaoExecutadaEquipeHelper.setQtd((Integer) dadosOS[6]);
				}

				if(dadosOS[7] != null){ // 7
					relatorioOSNaoExecutadaEquipeHelper.setMotivoEncerramento((String) dadosOS[7]);
				}

				if(!retorno.contains(relatorioOSNaoExecutadaEquipeHelper)){
					retorno.add(relatorioOSNaoExecutadaEquipeHelper);
				}
			}

			// Organizar a coleção

			Collections.sort((List) retorno, new Comparator() {

				public int compare(Object a, Object b){

					Integer tipoServico1 = ((RelatorioOSNaoExecutadaEquipeHelper) a).getIdServicoTipo();
					Integer tipoServico2 = ((RelatorioOSNaoExecutadaEquipeHelper) b).getIdServicoTipo();

					if(!tipoServico1.equals(tipoServico2)){
						return tipoServico1.compareTo(tipoServico2);
					}else{
						return 0;
					}
				}
			});

		}

		return retorno;
	}

	/**
	 * @author isilva
	 * @date 19/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOSPadraoComOcorrencia(Collection idsOrdemServico) throws ControladorException{

		OSRelatorioHelper dadosOS = null;

		Collection<OSRelatorioHelper> colecaoDadosOS = new ArrayList<OSRelatorioHelper>();

		try{

			if(idsOrdemServico != null && !idsOrdemServico.isEmpty()){
				int qtdBlocosConsultas = Util.dividirArredondarResultado(idsOrdemServico.size(),
								ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO, BigDecimal.ROUND_UP);
				for(int i = 0; i < qtdBlocosConsultas; i++){

					List<Integer> blocoIds = new ArrayList<Integer>();
					blocoIds.addAll(Util.obterSubListParaPaginacao((List) idsOrdemServico, i,
									ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO));

					Collection<OSRelatorioHelper> colecaoOrdemServico = this.repositorioOrdemServico
									.pesquisarOSRelatorioPadraoCabecalho(blocoIds);

					if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){

						for(OSRelatorioHelper osRelatorioHelper : colecaoOrdemServico){

							// Capturando a Unidade Atual da Ordem de Serviço
							UnidadeOrganizacional unidadeAtual = this.obterUnidadeAtualOrdemServico(osRelatorioHelper.getIdOrdemServico());

							if(unidadeAtual != null){
								osRelatorioHelper.setIdUnidade(unidadeAtual.getId());
								osRelatorioHelper.setDescricaoUnidade(unidadeAtual.getDescricaoComId());
							}

							colecaoDadosOS.add(osRelatorioHelper);
						}
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Collection colecaoOSRelatorioHelper = new ArrayList();

		if(colecaoDadosOS != null && !colecaoDadosOS.isEmpty()){

			Iterator colecaoDadosOSIterator = colecaoDadosOS.iterator();

			while(colecaoDadosOSIterator.hasNext()){

				dadosOS = (OSRelatorioHelper) colecaoDadosOSIterator.next();

				if(dadosOS != null){

					// Categoria e Quantidade de Economias
					if(dadosOS.getIdImovel() != null){ // 6
						Imovel imovel = new Imovel();
						imovel.setId(dadosOS.getIdImovel());

						Categoria categoria = getControladorImovel().obterDescricoesCategoriaImovel(imovel);
						dadosOS.setCategoria(categoria.getDescricaoAbreviada());

						int quantidadeEconomias = getControladorImovel().obterQuantidadeEconomias(imovel);
						dadosOS.setQuantidadeEconomias(quantidadeEconomias);
					}

					// Id do Serviço Tipo Referência
					if(dadosOS.getIdServicoTipoReferencia() != null){ // 29
						FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
						filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, dadosOS
										.getIdServicoTipoReferencia()));
						Collection servicoTipos = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
						ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(servicoTipos);
						dadosOS.setDescricaoServicoTipoReferencia(servicoTipo.getDescricao());
					}

					String endereco = this.obterEnderecoCompletoOS(dadosOS.getIdOrdemServico());
					dadosOS.setEndereco(endereco);

					if(dadosOS.getIdRA() != null){

						Object[] dadosSolicitante = getControladorRegistroAtendimento()
										.pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(dadosOS.getIdRA());

						if(dadosSolicitante != null){

							String telefone = "";

							// Id do Cliente
							if(dadosSolicitante[0] != null){ // 0
								dadosOS.setIdSolicitante((Integer) dadosSolicitante[0]);

								// Seta o valor do telefone a partir de cliente
								// fone,
								// caso o id do cliente seja diferente de nulo
								telefone = getControladorCliente().pesquisarClienteFonePrincipal(dadosOS.getIdSolicitante());
								dadosOS.setTelefone(telefone);
							}else{
								// Seta o valor do telefone a partir de
								// solicitante
								// fone,
								// caso o id do cliente seja nulo
								telefone = getControladorRegistroAtendimento().pesquisarSolicitanteFonePrincipal(dadosOS.getIdRA());
								dadosOS.setTelefone(telefone);
							}

							// Nome do Cliente
							if(dadosSolicitante[1] != null){ // 1
								dadosOS.setNomeSolicitante((String) dadosSolicitante[1]);
							}

							// Id da Unidade
							if(dadosSolicitante[2] != null){ // 2
								dadosOS.setIdUnidade((Integer) dadosSolicitante[2]);
							}

							// Descrição da Unidade
							if(dadosSolicitante[3] != null){ // 3
								dadosOS.setDescricaoUnidade((String) dadosSolicitante[3]);
							}

							// Id do Funcionário
							if(dadosSolicitante[4] != null){ // 4
								dadosOS.setIdSolicitante((Integer) dadosSolicitante[4]);
							}

							// Nome do Funcionário
							if(dadosSolicitante[5] != null){ // 5
								dadosOS.setNomeSolicitante((String) dadosSolicitante[5]);
							}

							// Nome do Solicitante
							if(dadosSolicitante[6] != null){ // 6
								dadosOS.setNomeSolicitante((String) dadosSolicitante[6]);
							}

						}
					}

					try{
						Object[] objetosRetorno = (Object[]) Util.retonarObjetoDeColecao(repositorioOrdemServico
										.pesquisarEquipeEDataProgramacaoOrdemServico(dadosOS.getIdOrdemServico()));

						if(objetosRetorno != null){
							// osp.programacaoRoteiro.dataRoteiro, osp.equipe.id, osp.equipe.nome

							/**
							 * [0] - Data da Programação
							 * [1] - Id da Equipe
							 * [2] - Nome Equipe
							 */
							dadosOS.setDataRoteiroProgramacao((Date) objetosRetorno[0]);
							dadosOS.setIdEquipe((Integer) objetosRetorno[1]);
							dadosOS.setNomeEquipe((String) objetosRetorno[2]);
						}

					}catch(ErroRepositorioException e){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", e);
					}

					Bairro bairro = this.getControladorEndereco().obterBairroPorImovelOuRA(dadosOS.getIdImovel(), dadosOS.getIdRA());

					String codigoDescricaoBairro = "";

					if(bairro != null){
						codigoDescricaoBairro = bairro.getNomeComCodigo();
					}

					dadosOS.setCodigoDescricaoBairro(codigoDescricaoBairro);

					colecaoOSRelatorioHelper.add(dadosOS);

				}

			}

		}

		return colecaoOSRelatorioHelper;

	}

	/**
	 * Relatório Padrão 2
	 * 
	 * @author isilva
	 * @date 19/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<OSRelatorioPadraoComOcorrenciaHelper> pesquisarOSRelatorioPadraoComOcorrencia(Collection idsOrdemServico)
					throws ControladorException{

		Collection<OSRelatorioPadraoComOcorrenciaHelper> helpers = pesquisarOSPadraoComOcorrencia(idsOrdemServico);

		return helpers;
	}

	/**
	 * @author isilva
	 * @date 24/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarOSCabecalho(Collection idsOrdemServico) throws ControladorException{

		OSRelatorioEstruturaHelper dadosOS = null;

		Collection<OSRelatorioEstruturaHelper> colecaoDadosOSEstrutura = new ArrayList<OSRelatorioEstruturaHelper>();

		try{

			if(idsOrdemServico != null && !idsOrdemServico.isEmpty()){
				int qtdBlocosConsultas = Util.dividirArredondarResultado(idsOrdemServico.size(),
								ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO, BigDecimal.ROUND_UP);
				for(int i = 0; i < qtdBlocosConsultas; i++){

					List<Integer> blocoIds = new ArrayList<Integer>();
					blocoIds.addAll(Util.obterSubListParaPaginacao((List) idsOrdemServico, i,
									ConstantesSistema.QUANTIDADE_LIMITE_CONSULTA_POR_ORDEM_SERVICO));

					Collection<OSRelatorioEstruturaHelper> colecaoOrdemServico = (ArrayList<OSRelatorioEstruturaHelper>) this.repositorioOrdemServico
									.pesquisarOSRelatorioCabecalho(blocoIds);

					if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
						colecaoDadosOSEstrutura.addAll(colecaoOrdemServico);
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Collection colecaoOSRelatorioEstruturaHelper = new ArrayList();

		if(colecaoDadosOSEstrutura != null && !colecaoDadosOSEstrutura.isEmpty()){

			Iterator colecaoDadosOSIterator = colecaoDadosOSEstrutura.iterator();

			while(colecaoDadosOSIterator.hasNext()){

				dadosOS = (OSRelatorioEstruturaHelper) colecaoDadosOSIterator.next();

				if(dadosOS != null){

					// Categoria e Quantidade de Economias
					if(dadosOS.getIdImovel() != null){ // 6
						Imovel imovel = new Imovel();
						imovel.setId(dadosOS.getIdImovel());

						Categoria categoria = getControladorImovel().obterDescricoesCategoriaImovel(imovel);
						dadosOS.setDescricaoAbreviadaCategoria(categoria.getDescricaoAbreviada());
						dadosOS.setDescricaoCategoria(categoria.getDescricao());

						int quantidadeEconomias = getControladorImovel().obterQuantidadeEconomias(imovel);
						dadosOS.setQuantidadeEconomias(quantidadeEconomias);
					}

					// Id do Serviço Tipo Referência
					if(dadosOS.getIdServicoTipoReferencia() != null){ // 29
						FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
						filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, dadosOS
										.getIdServicoTipoReferencia()));
						Collection servicoTipos = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
						ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(servicoTipos);
						dadosOS.setDescricaoServicoTipoReferencia(servicoTipo.getDescricao());
					}

					String endereco = this.obterEnderecoCompletoOS(dadosOS.getIdOrdemServico());
					dadosOS.setEndereco(endereco);

					if(dadosOS.getIdRA() != null){

						Object[] dadosSolicitante = getControladorRegistroAtendimento()
										.pesquisarDadosRegistroAtendimentoSolicitanteRelatorioOS(dadosOS.getIdRA());

						if(dadosSolicitante != null){

							String telefone = "";

							// Id do Cliente
							if(dadosSolicitante[0] != null){ // 0
								dadosOS.setIdSolicitante((Integer) dadosSolicitante[0]);

								// Seta o valor do telefone a partir de cliente
								// fone,
								// caso o id do cliente seja diferente de nulo
								telefone = getControladorCliente().pesquisarClienteFonePrincipal(dadosOS.getIdSolicitante());
								dadosOS.setTelefone(telefone);
							}else{
								// Seta o valor do telefone a partir de
								// solicitante
								// fone,
								// caso o id do cliente seja nulo
								telefone = getControladorRegistroAtendimento().pesquisarSolicitanteFonePrincipal(dadosOS.getIdRA());
								dadosOS.setTelefone(telefone);
							}

							// Nome do Cliente
							if(dadosSolicitante[1] != null){ // 1
								dadosOS.setNomeSolicitante((String) dadosSolicitante[1]);
							}

							// Id da Unidade
							if(dadosSolicitante[2] != null){ // 2
								dadosOS.setIdUnidade((Integer) dadosSolicitante[2]);
							}

							// Descrição da Unidade
							if(dadosSolicitante[3] != null){ // 3
								dadosOS.setDescricaoUnidade((String) dadosSolicitante[3]);
							}

							// Id do Funcionário
							if(dadosSolicitante[4] != null){ // 4
								dadosOS.setIdSolicitante((Integer) dadosSolicitante[4]);
							}

							// Nome do Funcionário
							if(dadosSolicitante[5] != null){ // 5
								dadosOS.setNomeSolicitante((String) dadosSolicitante[5]);
							}

							// Nome do Solicitante
							if(dadosSolicitante[6] != null){ // 6
								dadosOS.setNomeSolicitante((String) dadosSolicitante[6]);
							}

						}
					}

					try{
						Object[] objetosRetorno = (Object[]) Util.retonarObjetoDeColecao(repositorioOrdemServico
										.pesquisarEquipeEDataProgramacaoOrdemServico(dadosOS.getIdOrdemServico()));

						if(objetosRetorno != null){
							// osp.programacaoRoteiro.dataRoteiro, osp.equipe.id, osp.equipe.nome

							/**
							 * [0] - Data da Programação
							 * [1] - Id da Equipe
							 * [2] - Nome Equipe
							 */
							dadosOS.setDataRoteiroProgramacao((Date) objetosRetorno[0]);
							dadosOS.setIdEquipe((Integer) objetosRetorno[1]);
							dadosOS.setNomeEquipe((String) objetosRetorno[2]);
						}

					}catch(ErroRepositorioException e){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema", e);
					}

					Bairro bairro = this.getControladorEndereco().obterBairroPorImovelOuRA(dadosOS.getIdImovel(), dadosOS.getIdRA());

					String codigoDescricaoBairro = "";

					if(bairro != null){
						codigoDescricaoBairro = bairro.getNomeComCodigo();
					}

					dadosOS.setCodigoDescricaoBairro(codigoDescricaoBairro);

					colecaoOSRelatorioEstruturaHelper.add(dadosOS);

				}

			}

		}

		return colecaoOSRelatorioEstruturaHelper;

	}

	/**
	 * Cabeçalhos dos Relatórios
	 * 
	 * @author isilva
	 * @date 24/05/2011
	 * @param idsOrdemServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<OSRelatorioEstruturaHelper> pesquisarOSRelatorioEstruturaCabecalho(Collection idsOrdemServico)
					throws ControladorException{

		Collection<OSRelatorioEstruturaHelper> helpers = pesquisarOSCabecalho(idsOrdemServico);
		return helpers;
	}

	/**
	 * [UC0XXX] Gerar Relatório Resumo de Ordem de Serviço Encerradas
	 * Obter dados para gerar Relatório Resumo de Ordem de Serviço Encerradas
	 * 
	 * @author Anderson Italo
	 * @date 13/05/2011
	 */
	public Collection pesquisaRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException{

		Collection colecaoRetorno = null;
		try{

			colecaoRetorno = this.repositorioOrdemServico.pesquisaRelatorioResumoOsEncerradas(origemServico, situacaoOS, idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, idLocalidade);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório Resumo de Ordem de Serviço Encerradas
	 * Obter total de registros do Relatório Resumo de Ordem de Serviço Encerradas
	 * 
	 * @author Anderson Italo
	 * @date 13/05/2011
	 */
	public Integer pesquisaTotalRegistrosRelatorioResumoOsEncerradas(String origemServico, String situacaoOS, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal,
					String idEquipeProgramacao, String idEquipeExecucao, String idLocalidade) throws ControladorException{

		Integer retorno = null;
		try{
			retorno = this.repositorioOrdemServico.pesquisaTotalRegistrosRelatorioResumoOsEncerradas(origemServico, situacaoOS,
							idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao,
							idEquipeExecucao, idLocalidade);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC1015] Relatório Ordens de Serviço Pendentes
	 * Obter dados para geração relatório.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioResumoOrdensServicoPendentesHelper> pesquisaRelatorioResumoOSPendentes(
					FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro) throws ControladorException{

		Collection<RelatorioResumoOrdensServicoPendentesHelper> colecaoRetorno = null;
		try{

			colecaoRetorno = this.repositorioOrdemServico.pesquisaRelatorioResumoOSPendentes(filtro);

			if(colecaoRetorno == null || colecaoRetorno.isEmpty()){

				throw new ControladorException("atencao.relatorio.vazio");
			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC1015] Relatório Ordens de Serviço Pendentes
	 * Obtém o total de registros do relatório.
	 * 
	 * @author Anderson Italo
	 * @date 08/06/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTotalRegistrosRelatorioResumoOSPendentes(FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro)
					throws ControladorException{

		Integer retorno = null;
		try{
			retorno = this.repositorioOrdemServico.pesquisarTotalRegistrosRelatorioResumoOSPendentes(filtro);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	public Collection<OcorrenciaInfracaoItem> inserirOcorrenciaInfracao(OcorrenciaInfracao ocorrenciaInfracao, Integer[] colecaoInfracaoTipo)
					throws ControladorException{

		Collection<OcorrenciaInfracaoItem> listaInfracoesItem = new ArrayList<OcorrenciaInfracaoItem>();
		OcorrenciaInfracao ocorrenciaInfracaoBase = (OcorrenciaInfracao) this.recuperarOcorrenciaInfracao(
						ocorrenciaInfracao.getNnDoctoInfracao(), ocorrenciaInfracao.getNnAnoDoctoInfracao());
		if(ocorrenciaInfracaoBase != null){
			throw new ControladorException("atencao.ocorrencia_infracao.existente");
		}
		ocorrenciaInfracao.setUltimaAlteracao(new Date());

		Integer idOcorrenciaInfracao = (Integer) getControladorUtil().inserir(ocorrenciaInfracao);

		ocorrenciaInfracao.setId(idOcorrenciaInfracao);

		if(colecaoInfracaoTipo.length > 0){
			Integer idInfracaoTipo;
			OcorrenciaInfracaoItem ocorrenciaInfracaoItem = null;
			InfracaoTipo infracaoTipo = null;
			for(int i = 0; i < colecaoInfracaoTipo.length; i++){
				idInfracaoTipo = colecaoInfracaoTipo[i];
				infracaoTipo = new InfracaoTipo();
				infracaoTipo.setId(idInfracaoTipo);

				ocorrenciaInfracaoItem = new OcorrenciaInfracaoItem();

				ocorrenciaInfracaoItem.setInfracaoTipo(infracaoTipo);
				ocorrenciaInfracaoItem.setOcorrenciaInfracao(ocorrenciaInfracao);
				ocorrenciaInfracaoItem.setUltimaAlteracao(new Date());

				getControladorUtil().inserir(ocorrenciaInfracaoItem);

				listaInfracoesItem.add(ocorrenciaInfracaoItem);
			}

		}else{
			// Lança uma exceção exigindo o preenchimento
			throw new ControladorException("atencao.required", null, "O Tipo de Irregularidade");
		}

		return listaInfracoesItem;
	}

	/**
	 * [UC0947] - Gerar Sanção por Infração
	 * 
	 * @param numeroOS
	 * @param listaInfracoesItem
	 * @throws ControladorException
	 */
	public void gerarSancaoInfracao(int numeroOS, Collection<OcorrenciaInfracaoItem> listaInfracoesItem) throws ControladorException{

		try{
			SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
			// PASSO 2 Recupera o imovel
			OrdemServico ordemServico = this.repositorioOrdemServico.recuperaOSPorId(numeroOS);
			Imovel imovel = null;
			// Se ordem de serviço ñ possui imovel
			if(ordemServico.getImovel() == null){
				imovel = ordemServico.getRegistroAtendimento().getImovel();
			}else{
				// Se não, recupera da ordem servico
				imovel = ordemServico.getImovel();
			}
			// PASSO 3 - Obter a categoria,
			Categoria categoriaPrincipal = getControladorImovel().obterPrincipalCategoriaImovel(imovel.getId());
			// subcategoria
			ImovelSubcategoria subCategoriaPrincipal = getControladorImovel().obterPrincipalSubcategoria(categoriaPrincipal.getId(),
							imovel.getId());
			// Perfil
			ImovelPerfil perfil = getControladorImovel().obterImovelPerfil(imovel.getId());
			// PASSO 4 - Para cada ocorrência, encontrar a infração Perfil com o
			// máximo de
			// correspondências.
			Map<Integer, DebitoTipo> mapaPerfilDebitoTipo = new HashMap<Integer, DebitoTipo>();
			Map<Integer, OcorrenciaInfracaoItem> mapaPerfilOcorrenciaInfracaoItem = new HashMap<Integer, OcorrenciaInfracaoItem>();
			Collection<Categoria> categoriasImovel = getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);
			InfracaoPerfil infracaoPerfil;
			for(OcorrenciaInfracaoItem ocorrenciaInfracaoItem : listaInfracoesItem){
				infracaoPerfil = this.repositorioOrdemServico.pesquisarInfracaoPerfil(ocorrenciaInfracaoItem.getInfracaoTipo().getId(),
								categoriaPrincipal.getId(), subCategoriaPrincipal.getComp_id().getSubcategoria().getId(), perfil.getId());
				if(infracaoPerfil == null){
					throw new ControladorException("atencao.geracaosancao.nao_existe.infracao_perfil");
				}else{
					// PASSO 5 - Com o perfil Selecionado obter os tipos de
					// débito.
					Collection<DebitoTipo> colecaoDebitoTipoTemp = this.repositorioOrdemServico
									.pesquisarDebitosTipoInfracaoPerfil(infracaoPerfil.getId());
					if(colecaoDebitoTipoTemp.isEmpty()){
						throw new ControladorException("atencao.geracaosancao.nao_existe.infracao_perfil_debito");
					}else{
						for(DebitoTipo debitoTipo : colecaoDebitoTipoTemp){
							if(!mapaPerfilDebitoTipo.containsKey(debitoTipo.getId())){
								mapaPerfilDebitoTipo.put(debitoTipo.getId(), debitoTipo);
								mapaPerfilOcorrenciaInfracaoItem.put(debitoTipo.getId(), ocorrenciaInfracaoItem);
							}
						}
					}
				}
			}
			Collection<CalcularValoresAguaEsgotoHelper> colecaoCalculoValoresAguaEsgotoHelper = null;
			BigDecimal valorFaturado = BigDecimal.ZERO;

			Collection<Categoria> colecaoCategoriaAux = null;
			BigDecimal valorFaturadoAguaCategoria = BigDecimal.ZERO;
			BigDecimal valorFaturadoEsgotoCategoria = BigDecimal.ZERO;
			BigDecimal valorFaturadoCategoria = BigDecimal.ZERO;

			Integer consumoMinimoLigacao = null;

			BigDecimal numeroMesesCalculoInfracaoBD = new BigDecimal("1");
			Integer numeroMesesCalculoInfracao = sistemaParametro.getNumeroMesesCalculoInfracao();
			if(numeroMesesCalculoInfracao != null){
				numeroMesesCalculoInfracaoBD = new BigDecimal(numeroMesesCalculoInfracao);
			}

			BigDecimal jurosPercentualEsgoto = BigDecimal.ZERO;
			if(imovel.getLigacaoEsgoto() != null){
				jurosPercentualEsgoto = getControladorLigacaoEsgoto().recuperarPercentualEsgoto(imovel.getLigacaoEsgoto().getId());
			}

			// PASSO 6 - Para cada Debito Tipo
			for(DebitoTipo debitoTipo : mapaPerfilDebitoTipo.values()){
				if(debitoTipo.getValorPadrao() != null && debitoTipo.getIndicadorValorCalcular().equals(ConstantesSistema.NAO)){

					BigDecimal valorDebitoTipo = debitoTipo.getValorPadrao();

					Integer idImovelInformado = imovel.getId();
					if(Util.isNaoNuloBrancoZero(idImovelInformado)){
						BigDecimal valorServicoLocalidade = getControladorFaturamento().verificarDebitoTipoValorLocalidade(
										idImovelInformado, debitoTipo.getId());

						if(valorServicoLocalidade != null){
							valorDebitoTipo = valorServicoLocalidade;
						}
					}

					this.gerarDebitoOrdemServicoSancao(numeroOS, debitoTipo.getId(), valorDebitoTipo, 1,
									sistemaParametro.getNumeroDiasSuspensaoCobrancaInfracao(),
									mapaPerfilOcorrenciaInfracaoItem.get(debitoTipo.getId()));
				}else{
					// PASSO 7
					Integer volumeTotal = 0;

					// Consumo por categoria
					for(Categoria categoria : categoriasImovel){
						colecaoCategoriaAux = new ArrayList();
						colecaoCategoriaAux.add(categoria);

						consumoMinimoLigacao = getControladorMicromedicao().obterConsumoMinimoLigacao(imovel, colecaoCategoriaAux);

						volumeTotal = categoria.getQuantidadeEconomiasCategoria() * categoria.getConsumoMedioEconomiaMes();

						colecaoCalculoValoresAguaEsgotoHelper = getControladorFaturamento().calcularValoresAguaEsgoto(
										sistemaParametro.getAnoMesFaturamento(), LigacaoAguaSituacao.LIGADO,
										LigacaoEsgotoSituacao.FACTIVEL, Short.valueOf("1"), Short.valueOf("1"), colecaoCategoriaAux,
										volumeTotal, volumeTotal, consumoMinimoLigacao, Util.adicionarNumeroDiasDeUmaData(new Date(), -30),
										new Date(), jurosPercentualEsgoto, imovel.getConsumoTarifa().getId(), imovel.getId(), null);

						for(CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelper : colecaoCalculoValoresAguaEsgotoHelper){
							valorFaturadoAguaCategoria = calcularValoresAguaEsgotoHelper.getValorFaturadoAguaCategoria();
							valorFaturadoEsgotoCategoria = calcularValoresAguaEsgotoHelper.getValorFaturadoEsgotoCategoria();
							valorFaturadoCategoria = valorFaturadoAguaCategoria.add(valorFaturadoEsgotoCategoria);

							valorFaturado = valorFaturado.add(valorFaturadoCategoria.multiply(numeroMesesCalculoInfracaoBD));
						}
					}

					if(valorFaturado.compareTo(BigDecimal.ZERO) == 1){
						this.gerarDebitoOrdemServicoSancao(numeroOS, debitoTipo.getId(), valorFaturado, 1,
										sistemaParametro.getNumeroDiasSuspensaoCobrancaInfracao(),
										mapaPerfilOcorrenciaInfracaoItem.get(debitoTipo.getId()));
					}

				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Recupera a Ocorrencia Infracao pelo nnDoctoInfracao e nnAnoDoctoInfracao.
	 * 
	 * @param nnDoctoInfracao
	 * @param nnAnoDoctoInfracao
	 * @return OcorrenciaInfracao
	 * @throws ControladorException
	 */
	public OcorrenciaInfracao recuperarOcorrenciaInfracao(Integer nnDoctoInfracao, Integer nnAnoDoctoInfracao) throws ControladorException{

		try{
			return repositorioOrdemServico.recuperarOcorrenciaInfracao(nnDoctoInfracao, nnAnoDoctoInfracao);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Gera a Sansao para a Baixa em Lote da Ordem de Serviço
	 * 
	 * @param criticaOSLoteHelper
	 * @throws ControladorException
	 */
	public void gerarSansaoBaixaEmLoteOS(CriticaOSLoteHelper criticaOSLoteHelper) throws ControladorException{

		try{
			IntegracaoComercialHelper integracaoComercialHelper = criticaOSLoteHelper.getIntegracaoComercialHelper();
			OcorrenciaInfracao ocorrenciaInfracao = new OcorrenciaInfracao(integracaoComercialHelper.getNnDoctoInfracao(),
							integracaoComercialHelper.getNnAnoDoctoInfracao(), new Date(),
							this.repositorioOrdemServico.recuperaOSPorId(criticaOSLoteHelper.getNumeroOS()),
							integracaoComercialHelper.getInfracaoImovelSituacao(), integracaoComercialHelper.getInfracaoLigacaoSituacao(),
							integracaoComercialHelper.getFiscal());
			Integer idOcorrenciaInfracao = (Integer) getControladorUtil().inserir(ocorrenciaInfracao);
			ocorrenciaInfracao.setId(idOcorrenciaInfracao);
			OcorrenciaInfracaoItem ocorrenciaInfracaoItem = null;
			Collection<OcorrenciaInfracaoItem> listaInfracoesItem = new ArrayList<OcorrenciaInfracaoItem>();
			for(InfracaoTipo infracaoTipo : integracaoComercialHelper.getIrregularidadesTipo()){
				ocorrenciaInfracaoItem = new OcorrenciaInfracaoItem(ocorrenciaInfracao, infracaoTipo, new Date());
				Integer idOcorrenciaInfracaoItem = (Integer) getControladorUtil().inserir(ocorrenciaInfracaoItem);
				ocorrenciaInfracaoItem.setId(idOcorrenciaInfracaoItem);
				listaInfracoesItem.add(ocorrenciaInfracaoItem);
			}
			this.gerarSancaoInfracao(criticaOSLoteHelper.getNumeroOS(), listaInfracoesItem);
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Recupera uma OrdemServicoProgramacao
	 * 
	 * @author Hebert Falcão
	 * @date 19/07/2011
	 * @return OrdemServicoProgramacao
	 */
	public OrdemServicoProgramacao pesquisarOrdemServicoProgramacao(Integer idUnidadeOrganizacional, Integer idOrdemServico,
					Integer idEquipe, Date dataRoteiro) throws ControladorException{

		try{
			return repositorioOrdemServico.pesquisarOrdemServicoProgramacao(idUnidadeOrganizacional, idOrdemServico, idEquipe, dataRoteiro);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC3040] Obter Unidade Atual da OS
	 * Este método permite obter a unidade atual de uma ordem de serviço
	 * 
	 * @author Anderson Italo
	 * @date 08/02/2012
	 * @throws ControladorException
	 */
	public UnidadeOrganizacional obterUnidadeAtualOrdemServico(Integer idOrdemServico) throws ControladorException{

		UnidadeOrganizacional unidadeAtual = null;

		try{

			Short permiteTramiteIndependente = Short.parseShort((String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar(ExecutorParametrosAtendimentoPublico.getInstancia()));

			// Caso o trâmite da OS seja independente do RA
			if(permiteTramiteIndependente.equals(ConstantesSistema.SIM)){
				unidadeAtual = repositorioOrdemServico.obterUnidadeAtualOrdemServico(idOrdemServico);
			}

			if(unidadeAtual == null){

				OrdemServico ordemServico = repositorioOrdemServico.pesquisarOrdemServico(idOrdemServico);

				if(ordemServico.getRegistroAtendimento() == null){

					unidadeAtual = repositorioOrdemServico.obterUnidadeGeracaoOrdemServico(idOrdemServico);
				}else{

					unidadeAtual = getControladorRegistroAtendimento().obterUnidadeAtualRA(ordemServico.getRegistroAtendimento().getId());
				}
			}

		}catch(ErroRepositorioException e){

			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return unidadeAtual;
	}

	/**
	 * @author Ailton Sousa
	 * @date 10/02/2012
	 * @param numeroOS
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarOSProgramacaoAtiva(Integer numeroOS) throws ControladorException{

		Integer numeroOSConsultada = null;

		try{
			numeroOSConsultada = repositorioOrdemServico.pesquisarOSProgramacaoAtiva(numeroOS);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return numeroOSConsultada;
	}

	/**
	 * [UC0620] - Obter Indicador de Autorização para Manutenção da OS
	 * Este método permite obter o indicador de autorização para manutenção da OS
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2012
	 * @throws ControladorException
	 */
	public Short obterIndicadorAutorizacaoParaManutencaoOrdemServico(Usuario usuario, Integer idOrdemServico) throws ControladorException{

		Short indicadorAutorizacao = ConstantesSistema.NAO;

		try{

			OrdemServico ordemServico = repositorioOrdemServico.pesquisarOrdemServico(idOrdemServico);
			UnidadeOrganizacional unidadeAtualOrdemServico = this.obterUnidadeAtualOrdemServico(idOrdemServico);
			UnidadeOrganizacional unidadeGeracaoOrdemServico = repositorioOrdemServico.obterUnidadeGeracaoOrdemServico(idOrdemServico);

			/*
			 * Caso a Unidade de Lotação do Usuário corresponda à Unidade de Geração da OS ou a
			 * Unidade Atual da OS <<Inclui>> [UC3040 – Obter Unidade Atual da OS]
			 */
			// if(unidadeAtualOrdemServico != null && unidadeGeracaoOrdemServico != null){
			// if(usuario.getUnidadeOrganizacional() != null
			// &&
			// (usuario.getUnidadeOrganizacional().getId().equals(unidadeGeracaoOrdemServico.getId())
			// || usuario
			// .getUnidadeOrganizacional().getId().equals(unidadeAtualOrdemServico.getId()))){
			//
			// indicadorAutorizacao = ConstantesSistema.SIM;
			// }

			/*
			 * Caso a Unidade de Lotação do Usuário corresponda à Unidade de Geração da OS ou a
			 * Unidade Atual da OS <<Inclui>> [UC3040 – Obter Unidade Atual da OS]
			 */
			if(usuario.getUnidadeOrganizacional() != null
							&& (unidadeGeracaoOrdemServico != null
											&& (usuario.getUnidadeOrganizacional().getId().equals(unidadeGeracaoOrdemServico.getId())) || (unidadeAtualOrdemServico != null && usuario
											.getUnidadeOrganizacional().getId().equals(unidadeAtualOrdemServico.getId())))){

				indicadorAutorizacao = ConstantesSistema.SIM;

			}else{

				// Caso o usuário seja de empresa terceira
				if(usuario.getUsuarioTipo().getIndicadorFuncionario() == UsuarioTipo.INDICADOR_TERCEIRIZADO){

					Collection<Integer> colecaoIdEmpresaCobrancaLocalidade = null;

					if(ordemServico.getImovel() != null){

						FiltroImovel filtroImovel = new FiltroImovel();
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, ordemServico.getImovel().getId()));

						Imovel imovel = (Imovel) getControladorUtil().pesquisar(filtroImovel, Imovel.class.getName()).iterator().next();

						colecaoIdEmpresaCobrancaLocalidade = repositorioOrdemServico.obterIdEmpresasCobrancaRotasPorLocalidade(imovel
										.getLocalidade().getId());
					}

					/*
					 * Caso a empresa do usuário seja uma das empresas de cobrança da localidade da
					 * ordem de serviço
					 */
					if(!Util.isVazioOrNulo(colecaoIdEmpresaCobrancaLocalidade)){

						for(Integer idEmpresaCobrancaLocalidade : colecaoIdEmpresaCobrancaLocalidade){

							if(idEmpresaCobrancaLocalidade.equals(usuario.getEmpresa().getId())){

								indicadorAutorizacao = ConstantesSistema.SIM;
								break;
							}
						}
					}else{

						indicadorAutorizacao = ConstantesSistema.NAO;
					}
				}else{

					/*
					 * Caso contrário: Atribuir a unidade superior da Unidade de Geração da OS à
					 * Unidade Superior
					 */
					UnidadeOrganizacional unidadeSuperior = unidadeGeracaoOrdemServico.getUnidadeSuperior();

					// [FS0001] – Verificar existência de unidade superior
					if(unidadeSuperior == null){

						indicadorAutorizacao = ConstantesSistema.NAO;
					}else{

						// Caso a Unidade de Lotação do Usuário corresponda à Unidade Superior
						if(usuario.getUnidadeOrganizacional() != null
										&& usuario.getUnidadeOrganizacional().getId().equals(unidadeSuperior.getId())){

							indicadorAutorizacao = ConstantesSistema.SIM;
						}else{

							/*
							 * Variável para controlar loop da hierarquia das unidades
							 * organizacionais
							 */
							boolean verificouTodasUnidades = true;

							while(verificouTodasUnidades){

								/*
								 * Caso contrário: Atribuir a unidade superior da Unidade Superior à
								 * Unidade Superior
								 */
								unidadeSuperior = repositorioOrdemServico.obterUnidadeSuperiorPorUnidade(unidadeSuperior.getId());

								// [FS0001] – Verificar existência de unidade superior
								if(unidadeSuperior == null){

									indicadorAutorizacao = ConstantesSistema.NAO;
									verificouTodasUnidades = false;
								}else{

									/*
									 * Caso a Unidade de Lotação do Usuário corresponda à Unidade
									 * Superior
									 */
									if(usuario.getUnidadeOrganizacional() != null
													&& usuario.getUnidadeOrganizacional().getId().equals(unidadeSuperior.getId())){

										indicadorAutorizacao = ConstantesSistema.SIM;
										verificouTodasUnidades = false;
									}
								}

								if(unidadeSuperior.getId().equals(unidadeSuperior.getUnidadeSuperior().getId())){

									break;
								}

								unidadeSuperior = unidadeSuperior.getUnidadeSuperior();
							}
						}
					}
				}
			}

		}catch(ErroRepositorioException e){

			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return indicadorAutorizacao;
	}

	/**
	 * [UC3039] Tramitar Ordem de Serviço
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param idOS
	 * @return
	 * @throws ControladorException
	 */
	public Tramite pesquisarUltimaDataTramiteOS(Integer idOS) throws ControladorException{

		try{
			return repositorioOrdemServico.pesquisarUltimaDataTramiteOS(idOS);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC3039] Tramitar Ordem de Serviço
	 * O objeto tramite deve ter preenchidos os seguintes campos:
	 * -UnidadeOrganizacionalOrigem
	 * -UnidadeOrganizacionalDestino
	 * -OrdemServico
	 * -UsuarioRegistro
	 * -UsuarioResponsavel
	 * -DataTramite
	 * -UltimaAlteracao
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param tramite
	 * @param dataConcorrente
	 * @throws ControladorException
	 */
	public void tramitarOS(Tramite tramite, Date dataConcorrente) throws ControladorException{

		Tramite ultimoTramiteBase = this.pesquisarUltimaDataTramiteOS(tramite.getOrdemServico().getId());

		java.sql.Timestamp dataTramiteTramite = new java.sql.Timestamp(tramite.getDataTramite().getTime());

		if(ultimoTramiteBase != null && ultimoTramiteBase.getDataTramite().compareTo(dataTramiteTramite) == 0){
			sessionContext.setRollbackOnly();
			throw new ControladorException("atencao.tramite_existente_dia_hora_informada");
		}

		try{
			Date dataAtual = repositorioOrdemServico.verificarConcorrenciaOS(tramite.getOrdemServico().getId());

			if(dataConcorrente != null){
				if((dataAtual.after(dataConcorrente))){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.tramitar_os_usuario_concorrente");
				}
			}

			getControladorUtil().inserir(tramite);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC3039] Tramitar Ordem de Serviço
	 * Faz as validações para realizar o Trâmite da OS.
	 * 
	 * @author Ailton Sousa
	 * @date 14/02/2012
	 * @param tramite
	 * @param usuario
	 * @throws ControladorException
	 */
	public void validarTramitacaoOS(Tramite tramite, Usuario usuario) throws ControladorException{

		if(tramite != null){

			// [UC3040] - Obter Unidade Atual da OS
			UnidadeOrganizacional unidadeAtual = obterUnidadeAtualOrdemServico(tramite.getOrdemServico().getId());

			// [UC0620] - Obter Indicador de Autorização para Manutenção da OS
			Short indicadorAutorizacao = this.obterIndicadorAutorizacaoParaManutencaoOrdemServico(usuario, tramite.getOrdemServico()
							.getId());

			if(ConstantesSistema.NAO.equals(indicadorAutorizacao)){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_os_nao_autorizado");
			}

			// [FS0006] Validar Data
			// [FS0007] Validar Hora
			Date dataCorrente = new Date();
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(tramite.getDataTramite(), dataCorrente);

			if(qtdeDias < 0){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_os_data_maior_que_atual");

			}else if(Util.datasIguais(tramite.getDataTramite(), dataCorrente)){
				if(Util.compararHoraMinuto(Util.formatarHoraSemData(tramite.getDataTramite()), Util.formatarHoraSemData(dataCorrente), ">")){

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.tramitar_os_hora_maior_que_atual", null, Util.formatarHoraSemData(dataCorrente));
				}
			}

			// [FS0008]
			// Caso 1
			if(tramite.getUnidadeOrganizacionalOrigem() != null
							&& tramite.getUnidadeOrganizacionalOrigem().getId() != null
							&& tramite.getUnidadeOrganizacionalOrigem().getId().intValue() == tramite.getUnidadeOrganizacionalDestino()
											.getId().intValue()){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_os_unidade_origem_destino_iguais");
			}

			// Caso 2
			if((new Short(tramite.getUnidadeOrganizacionalDestino().getIndicadorTramite())).intValue() == 2){
				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.tramitar_os_unidade_destino_tramite");
			}

			// 3. Caso a tramitação esteja restrita às unidades responsáveis pela ordem de serviço
			// (PASI_VLPARAMETRO com o valor 1 (um) na tabela PARAMETRO_SISTEMA com
			// PASI_CDPARAMETRO="P_INDICADOR_TRAMITE_RESTRITO_UNIDADES_RESPONSAVEIS"):
			// 3.1. Caso a unidade destino informada/pesquisada não esteja entre as unidades
			// responsáveis pelo tipo de serviço (não existe ocorrência na tabela
			// SERVICO_TIPO_TRAMITE com SVTP_ID=SVTP_ID da tabela ORDEM_SERVICO e (LOCA_ID=LOCA_ID
			// da tabela REGISTRO_ATENDIMENTO ou LOCA_ID com o valor nulo) e (STCM_ID=SCTM_ID da
			// tabela REGISTRO_ATENDIMENTO ou STCM_ID com o valor nulo) e BAIR_ID=(BAIR_ID da tabela
			// LOGRADOURO_BAIRRO com LGBR_ID=LGBR_ID da Tabela REGISTRO_ATENDIMENTO ou BAIR_ID com o
			// valor nulo) e (UNID_IDORIGEM=unidade atual da OS, retornada pelo [UC3040], ou
			// UNID_IDORIGEM com o valor nulo) e UNID_IDDESTINO=Id da unidade destino
			// informada/pesquisada), exibir a mensagem
			// "A Unidade de Destino não é responsável pelo tipo de serviço <<SVTP_DSSERVICOTIPO da tabela SERVICO_TIPO com SVTP_ID=SVTP_ID da tabela ORDEM_SERVICO>>, 
			//logo não está autorizada a receber esta OS. Trâmite não autorizado".
			
			try{
				String pIndicadorTramiteRestritoUnidades = ParametroAtendimentoPublico.P_INDICADOR_TRAMITE_RESTRITO_UNIDADES_RESPONSAVEIS
								.executar();

				if(pIndicadorTramiteRestritoUnidades != null && pIndicadorTramiteRestritoUnidades.equals(ConstantesSistema.SIM.toString())){
					
					FiltroServicoTipoTramite filtroServicoTipoTramite = new FiltroServicoTipoTramite();
					filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.SERVICO_TIPO_ID, tramite.getOrdemServico()
									.getServicoTipo().getId()));
					filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.LOCALIDADE_ID, tramite.getRegistroAtendimento().getLocalidade()
									.getId(), ConectorOr.CONECTOR_OR));
					filtroServicoTipoTramite.adicionarParametro(new ParametroNulo(FiltroServicoTipoTramite.LOCALIDADE_ID));
					filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.SETOR_COMERCIAL_ID, tramite
									.getRegistroAtendimento().getSetorComercial().getId(), ConectorOr.CONECTOR_OR));
					filtroServicoTipoTramite.adicionarParametro(new ParametroNulo(FiltroServicoTipoTramite.SETOR_COMERCIAL_ID));
					filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.BAIRRO_ID, tramite
									.getRegistroAtendimento().getLogradouroBairro().getBairro().getId(), ConectorOr.CONECTOR_OR));
					filtroServicoTipoTramite.adicionarParametro(new ParametroNulo(FiltroServicoTipoTramite.BAIRRO_ID));
					filtroServicoTipoTramite
									.adicionarParametro(new ParametroSimples(FiltroServicoTipoTramite.UNIDADE_ORGANIZACIONAL_ORIGEM_ID,
													unidadeAtual.getId(), ConectorOr.CONECTOR_OR));
					filtroServicoTipoTramite
									.adicionarParametro(new ParametroNulo(FiltroServicoTipoTramite.UNIDADE_ORGANIZACIONAL_ORIGEM_ID));
					filtroServicoTipoTramite.adicionarParametro(new ParametroSimples(
									FiltroServicoTipoTramite.UNIDADE_ORGANIZACIONAL_DESTINO_ID, tramite.getUnidadeOrganizacionalDestino()
													.getId()));
					
					Collection<ServicoTipoTramite> colecaoServicoTipoTramite = this.getControladorUtil().pesquisar(filtroServicoTipoTramite,
									ServicoTipoTramite.class.getName());
					
					if(Util.isVazioOrNulo(colecaoServicoTipoTramite)){

						throw new ControladorException("atencao.sistemaparametro_inexistente", null, tramite.getOrdemServico()
										.getServicoTipo().getDescricao());

					}
					
					
					
				}else{
					
				}

			}catch(ControladorException e1){

				throw new ControladorException("atencao.sistemaparametro_inexistente", e1,
								"P_INDICADOR_TRAMITE_RESTRITO_UNIDADES_RESPONSAVEIS");
			}

			


		}
	}

	/**
	 * [UC3036] Obter Unidade Tramite Ordem de Serviço
	 * 
	 * @author Hugo Lima
	 * @date 06/12/2011
	 * @param idServicoTipoTramite
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param idBairro
	 * @param idUnidadeOrigem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarUnidadeTramiteOS(Integer idServicoTipoTramite, Integer idLocalidade, Integer idSetorComercial,
					Integer idBairro, Integer idUnidadeOrigem) throws ControladorException{

		try{

			if(idServicoTipoTramite == null){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.informe_campo", null, "Tipo de Serviço");
			}

			/*
			 * if(idUnidadeOrigem == null){
			 * sessionContext.setRollbackOnly();
			 * throw new ControladorException("atencao.informe_campo", null, "Unidade de Origem");
			 * }
			 */
			Integer idUnidade = null;
			idUnidade = repositorioOrdemServico.pesquisarUnidadeTramiteOS(idServicoTipoTramite, idLocalidade, idSetorComercial, idBairro,
							idUnidadeOrigem, null);
			if (idUnidade == null) {
				idUnidade = repositorioOrdemServico.pesquisarUnidadeTramiteOS(idServicoTipoTramite, idLocalidade, idSetorComercial, idBairro,
								null, new Short("1"));				
			}
			
			return idUnidade;

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * Carrega Trâmite com informações da O.S.
	 * 
	 * @author Ailton Sousa
	 * @date 16/02/2012
	 * @param ordemServico
	 * @param ordemServico
	 * @param idUnidadeDestino
	 * @param parecerTramite
	 */
	private Tramite getTramite(OrdemServico ordemServico, Usuario usuario, Integer idUnidadeDestino, String parecerTramite)
					throws ControladorException{

		Tramite tramite = new Tramite();
		// Unidade Origem
		UnidadeOrganizacional unidadeOrigem = null;
		unidadeOrigem = this.obterUnidadeAtualOrdemServico(ordemServico.getId());

		tramite.setUnidadeOrganizacionalOrigem(unidadeOrigem);
		// Unidade Destino
		UnidadeOrganizacional unidadeDestino = null;
		// unidadeDestino.setId(new Integer(form.getUnidadeDestinoId()));
		// Filtra Unidade de Destino
		FiltroUnidadeOrganizacional filtroUnidadeDestino = new FiltroUnidadeOrganizacional();
		filtroUnidadeDestino.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idUnidadeDestino));

		// Recupera Unidade de Destino
		Collection colecaoUnidadeDestino = this.getControladorUtil().pesquisar(filtroUnidadeDestino, UnidadeOrganizacional.class.getName());

		if(colecaoUnidadeDestino != null && !colecaoUnidadeDestino.isEmpty()){
			unidadeDestino = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidadeDestino);
		}else{
			// levanta a exceção para a próxima camada
			throw new ActionServletException("atencao.valor.invalida", null, "Unidade Destino");
		}

		tramite.setUnidadeOrganizacionalDestino(unidadeDestino);

		tramite.setOrdemServico(ordemServico);

		// Usuário Registro
		Usuario usuarioRegistro = new Usuario();
		usuarioRegistro.setId(usuario.getId());
		// UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
		// unidadeOrganizacional.setId(usuario.getId());
		// usuarioRegistro.setUnidadeOrganizacional(unidadeOrganizacional);
		tramite.setUsuarioRegistro(usuarioRegistro);

		// Usuário Responsável
		Usuario usuarioResponsavel = new Usuario();
		usuarioResponsavel.setId(usuario.getId());
		tramite.setUsuarioResponsavel(usuarioResponsavel);

		if(parecerTramite != null && !parecerTramite.equals("")){
			tramite.setParecerTramite(parecerTramite);
		}else{
			tramite.setParecerTramite(null);
		}

		tramite.setDataTramite(new Date());
		tramite.setUltimaAlteracao(new Date());

		return tramite;
	}

	/**
	 * [UC00503]Tramitar Conjunto de Ordem de Servico
	 * [SB0003]Incluir o Tramite
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2012
	 */
	public void tramitarConjuntoOSRA(Collection tramites, Usuario usuario) throws ControladorException{

		if(tramites != null && !tramites.isEmpty()){
			Iterator iteratorTramite = tramites.iterator();
			while(iteratorTramite.hasNext()){
				Tramite tramite = (Tramite) iteratorTramite.next();
				tramite.setUltimaAlteracao(new Date());
				this.tramitarOSRA(tramite, usuario);
			}
		}
	}

	/**
	 * [UC0427] Tramitar Ordem de Serviço a partir do Registro de Atendimento
	 * 
	 * @author Hugo Lima
	 * @date 16/02/2012
	 * @param tramite
	 * @param dataConcorrente
	 */
	public void tramitarOSRA(Tramite tramite, Usuario usuario) throws ControladorException{

		Collection<OrdemServico> colecaoOrdemServico = null;

		// Consulta as OS´s associadas ao registro de atendimento contido no tramite
		colecaoOrdemServico = this.getControladorRegistroAtendimento().obterOSRA(tramite.getRegistroAtendimento().getId());

		// [FS0011] - Verificar situação das OS
		if(!Util.isVazioOrNulo(colecaoOrdemServico)){

			// As mensagens de criticas a seguir só deverão ser apresentadas caso a O.S. em questão
			// seja a única gerada para a R.A., não devendo ser tramitada a O.S. que se encontre em
			// qualquer uma das situações abaixo.
			if(colecaoOrdemServico.size() == 1){
				this.verificarTramitacaoSituacaoColecaoOS(colecaoOrdemServico);
			}

			// Tramita as OS´s associadas ao RA caso existam
			// As O.S.’s pendentes e não criticadas deverão ser tramitadas normalmente.
			for(OrdemServico ordemServico : colecaoOrdemServico){

				if(ordemServico.getSituacao() != OrdemServico.SITUACAO_ENCERRADO.shortValue()
								&& ordemServico.getSituacao() != OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO.shortValue()){

					// Verifica se OS está pendente de retorno da OS de referência
					if(ordemServico.getSituacao() == OrdemServico.SITUACAO_PENDENTE.shortValue()){
						FiltroOrdemServicoProgramacao filtroOrdemServicoProgramacao = new FiltroOrdemServicoProgramacao();
						filtroOrdemServicoProgramacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServicoProgramacao.ORDEM_SERVICO);
						filtroOrdemServicoProgramacao.adicionarParametro(new ParametroSimples(
										FiltroOrdemServicoProgramacao.ORDEM_SERVICO_ID, ordemServico.getId()));

						Collection colecao = this.getControladorUtil().pesquisar(filtroOrdemServicoProgramacao,
										OrdemServicoProgramacao.class.getName());

						if(!Util.isVazioOrNulo(colecao)){
							continue;
						}
					}

					Integer idUnidadeTramiteOS = null;

					// 1.7 Caso tenha sido selecionada a opção de tramitar RA e OS’s, para cada
					// ordem de serviço associada ao RA (RGAT_ID em ORDEM_SERVICO = RGAT_ID
					// informado)
					// gerar o tramite de O.S: <<Inclui>> [UC3040 – Obter Unidade Atual da OS]
					UnidadeOrganizacional unidadeOrigem = this.obterUnidadeAtualOrdemServico(ordemServico.getId());

					tramite.setUnidadeOrganizacionalOrigem(unidadeOrigem);

					// [UC0620] - Obter Indicador de Autorização para Manutenção da OS
					Short indicadorAutorizacao = this.obterIndicadorAutorizacaoParaManutencaoOrdemServico(usuario, ordemServico.getId());

					if(ConstantesSistema.NAO.equals(indicadorAutorizacao)){
						sessionContext.setRollbackOnly();
						throw new ControladorException("atencao.tramitar_os_manutencao_nao_permitida");
					}

					// 1.7.1 Caso o parâmetro geral sinalize a sistemática de tramite de O.S.
					// independente do R.A. (P_OS_TRAMITE_INDEPENDENTE com valor 1), obter
					// definição de unidade de trâmite para cada O.S.
					// [<<Inclui>> [UC3036 – Obter Unidade Tramite Ordem de Serviço];
					// Caso contrário, assumir a mesma opção de trâmite determinada para a R.A
					if(ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar().toString().equals(ConstantesSistema.SIM.toString())){

						// Prevalece a unidade que usuário informou em tela
						if(tramite.getUnidadeOrganizacionalDestino() != null){
							idUnidadeTramiteOS = tramite.getUnidadeOrganizacionalDestino().getId();
						}

						// Caso não tenha, pesquisa unidade sugerida
						if(idUnidadeTramiteOS == null){
							idUnidadeTramiteOS = this.pesquisarUnidadeTramiteOS(ordemServico.getServicoTipo().getId(), null, null, null,
											tramite.getUnidadeOrganizacionalOrigem().getId());
						}

						// Caso não ache, pega unidade de origem
						if(idUnidadeTramiteOS == null){
							idUnidadeTramiteOS = tramite.getUnidadeOrganizacionalOrigem().getId();
						}

						UnidadeOrganizacional unidadeDestino = new UnidadeOrganizacional();
						if(!Util.isVazioOuBranco(idUnidadeTramiteOS)){
							unidadeDestino.setId(idUnidadeTramiteOS);
							tramite.setUnidadeOrganizacionalDestino(unidadeDestino);
						}
					}

					tramite.setRegistroAtendimento(null);
					tramite.setOrdemServico(ordemServico);

					this.tramitarOS(tramite, tramite.getOrdemServico().getUltimaAlteracao());
				}
			}
		}
	}

	/**
	 * [UC0427] Tramitar Registro de Atendimento
	 * Tramitar
	 * [FS0011] - Verificar situação das OS
	 * 
	 * @author Hugo Lima
	 * @date 04/02/2012
	 * @param tramite
	 * @param dataConcorrente
	 */
	private void verificarTramitacaoSituacaoColecaoOS(Collection<OrdemServico> colecaoOrdemServico) throws ControladorException{

		for(OrdemServico ordemServico : colecaoOrdemServico){

			// Verifica se a OS está encerrada
			if(ordemServico.getSituacao() == OrdemServico.SITUACAO_ENCERRADO.shortValue()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.tramitar_os_encerrada");
			}

			// Verifica se OS está pendente de retorno da OS de referência
			if(ordemServico.getSituacao() == OrdemServico.SITUACAO_AGUARDANDO_LIBERACAO.shortValue()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.tramitar_os_referencia", null, ordemServico.getOsReferencia().getId().toString());
			}

			// Verifica se OS está pendente de retorno da OS de referência
			if(ordemServico.getSituacao() == OrdemServico.SITUACAO_PENDENTE.shortValue()){
				FiltroOrdemServicoProgramacao filtroOrdemServicoProgramacao = new FiltroOrdemServicoProgramacao();
				filtroOrdemServicoProgramacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServicoProgramacao.ORDEM_SERVICO);
				filtroOrdemServicoProgramacao.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.ORDEM_SERVICO_ID,
								ordemServico.getId()));

				Collection colecao = this.getControladorUtil().pesquisar(filtroOrdemServicoProgramacao,
								OrdemServicoProgramacao.class.getName());

				if(!Util.isVazioOrNulo(colecao)){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.tramitar_os_programada");
				}
			}
		}

	}

	/**
	 * Consultar Id de Serviço Tipo com Geração Automática filtrando pelo Id da Solicitação Tipo
	 * Especificação
	 * 
	 * @author Hebet Falcão
	 * @date 17/02/2012
	 */
	public Collection<Integer> consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(Integer idSolicitacaoTipoEspecificacao)
					throws ControladorException{

		Collection<Integer> colecaoIdServicoTipo = null;

		try{
			colecaoIdServicoTipo = repositorioOrdemServico
							.consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(idSolicitacaoTipoEspecificacao);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoIdServicoTipo;
	}

	/**
	 * [UC0458] Imprimir Ordem de Serviço em Txt
	 * 
	 * @author Hebert Falcão
	 * @date 19/03/2012
	 */
	public StringBuilder imprimirOrdemServicoTxt(Collection<OrdemServico> colecaoOrdemServico, String nomeMetodoGeracaoArquivoTxt)
					throws ControladorException{

		StringBuilder arquivoTxt = new StringBuilder();

		StringBuilder arquivoTxtAux = null;

		boolean gerarCabecalho = true;
		String ordenacaoInscricaoAntiga = null;
		try{
			ordenacaoInscricaoAntiga = ParametroOrdemServico.P_ORDENACAO_INSCRICAO.executar();
		}catch(ControladorException e){
			e.printStackTrace();
		}
		List<OrdemServico> newListOrdemServico = new ArrayList<OrdemServico>();
		if(Util.isNaoNuloBrancoZero(ordenacaoInscricaoAntiga) && ordenacaoInscricaoAntiga.equals("1")){
			newListOrdemServico = ordenarOrdemServicoSeletivaInscricaoAntiga(colecaoOrdemServico);
		}else{
			newListOrdemServico.addAll(colecaoOrdemServico);
		}

		if(OrdemServicoLayout.EMITE_OS_FISCALIZACAO_TXT.equals(nomeMetodoGeracaoArquivoTxt)){
			for(OrdemServico ordemServico : newListOrdemServico){
				arquivoTxtAux = this.emiteOSFiscalizacaoTxt(ordemServico, gerarCabecalho);

				arquivoTxt = arquivoTxt.append(arquivoTxtAux);

				gerarCabecalho = false;
			}
		}else if(OrdemServicoLayout.EMITE_OS_HIDROMETRO_TXT.equals(nomeMetodoGeracaoArquivoTxt)){
			for(OrdemServico ordemServico : newListOrdemServico){
				arquivoTxtAux = this.emiteOSHidrometroTxt(ordemServico, gerarCabecalho);

				arquivoTxt = arquivoTxt.append(arquivoTxtAux);

				gerarCabecalho = false;
			}
		}else{
			throw new ControladorException("atencao.metodo_geracao_arquivo_txt_invalido");
		}

		return arquivoTxt;
	}

	private List<OrdemServico> ordenarOrdemServicoSeletivaInscricaoAntiga(Collection<OrdemServico> colecaoOrdemServico){

		List<OrdemServico> newListOrdemServico = new ArrayList<OrdemServico>();
		newListOrdemServico.addAll(colecaoOrdemServico);

		Collections.sort(newListOrdemServico, new Comparator<OrdemServico>() {

			public int compare(OrdemServico os1, OrdemServico os2){

				int compare = 0;
				if(os1.getImovel() != null && os2.getImovel() != null){
					Imovel imovelOS1 = os1.getImovel();

					Localidade localidade = imovelOS1.getLocalidade();
					Integer idLocalidadeOS1 = null;
					if(localidade != null){
						idLocalidadeOS1 = localidade.getId();
					}

					SetorComercial setorComercial = imovelOS1.getSetorComercial();
					Integer codigoSetorComercialOS1 = null;
					if(setorComercial != null){
						codigoSetorComercialOS1 = setorComercial.getCodigo();
					}

					Rota rota = imovelOS1.getRota();
					Short codigoRotaOS1 = null;
					if(rota != null){
						codigoRotaOS1 = rota.getCodigo();
					}

					Short numeroSegmentoOS1 = imovelOS1.getNumeroSegmento();
					Short loteOS1 = imovelOS1.getLote();
					Short subLoteOS1 = imovelOS1.getSubLote();

					// OS 2

					Imovel imovelOS2 = os2.getImovel();

					Localidade localidadeOs2 = imovelOS2.getLocalidade();
					Integer idLocalidadeOS2 = null;
					if(localidadeOs2 != null){
						idLocalidadeOS2 = localidadeOs2.getId();
					}

					SetorComercial setorComercialOs2 = imovelOS2.getSetorComercial();
					Integer codigoSetorComercialOS2 = null;
					if(setorComercial != null){
						codigoSetorComercialOS2 = setorComercialOs2.getCodigo();
					}

					Rota rotaOs2 = imovelOS2.getRota();
					Short codigoRotaOS2 = null;
					if(rotaOs2 != null){
						codigoRotaOS2 = rotaOs2.getCodigo();
					}

					Short numeroSegmentoOS2 = imovelOS2.getNumeroSegmento();
					Short loteOS2 = imovelOS2.getLote();
					Short subLoteOS2 = imovelOS2.getSubLote();

					// LOCAL.SETOR.ROTA.SEGMENTO.LOTE. SUBLOTE.

					// LOCALIDADE
					compare = idLocalidadeOS1.compareTo(idLocalidadeOS2);

					// SETOR
					if(compare == 0 && codigoSetorComercialOS1 != null && codigoSetorComercialOS2 != null){
						compare = codigoSetorComercialOS1.compareTo(codigoSetorComercialOS2);
					}
					// ROTA
					if(compare == 0 && codigoRotaOS1 != null && codigoRotaOS2 != null){
						compare = codigoRotaOS1.compareTo(codigoRotaOS2);
					}
					// SEGMENTO
					if(compare == 0 && numeroSegmentoOS1 != null && numeroSegmentoOS2 != null){
						compare = numeroSegmentoOS1.compareTo(numeroSegmentoOS2);
					}
					// LOTE
					if(compare == 0 && loteOS1 != null && loteOS2 != null){
						compare = loteOS1.compareTo(loteOS2);
					}
					// SUB LOTE
					if(compare == 0 && subLoteOS1 != null && subLoteOS2 != null){
						compare = subLoteOS1.compareTo(subLoteOS2);
					}
				}

				return compare;
			}

		});
		return newListOrdemServico;
	}

	/**
	 * [UC0458] Imprimir Ordem de Serviço
	 * [SB2001] Emite OS Hidrômetro TXT
	 * 
	 * @author Hebert Falcão
	 * @date 19/03/2012
	 */
	private StringBuilder emiteOSHidrometroTxt(OrdemServico ordemServico, boolean gerarCabecalho) throws ControladorException{

		// Corpo do Txt
		StringBuilder arquivoTxt = this.gerarCorpoTxtOrdemServicoSemDetalheDeConta("(desoorde.jdt) STARTLM", ordemServico, gerarCabecalho);

		// 46. Data Corte ou Supressão
		arquivoTxt.append(Util.completaString("", 10));

		// 47. Quantidade de contas vencidas
		arquivoTxt.append("000");

		// 48. Valor das contas vencidas
		arquivoTxt.append(Util.completaStringComEspacoAEsquerda("0,00", 17));

		// 49. Dados das contas vencidas
		arquivoTxt.append(Util.completaString("", 243));

		arquivoTxt.append(System.getProperty("line.separator"));

		return arquivoTxt;
	}

	/**
	 * [UC0458] Imprimir Ordem de Serviço
	 * [SB2002] Emite OS Fiscalização TXT
	 * 
	 * @author Hebert Falcão
	 * @date 19/03/2012
	 */
	private StringBuilder emiteOSFiscalizacaoTxt(OrdemServico ordemServico, boolean gerarCabecalho) throws ControladorException{

		// Corpo do Txt
		StringBuilder arquivoTxt = this.gerarCorpoTxtOrdemServicoSemDetalheDeConta("(desoord3.jdt) STARTLM", ordemServico, gerarCabecalho);

		// 46. Data Corte ou Supressão
		// [SB9000 - Obter Data de Corte ou Supressao]
		Date dataCorteOuSupressa = getControladorCobranca().obterDataCorteOuSupressao(ordemServico, "");
		if(dataCorteOuSupressa != null){
			arquivoTxt.append(Util.completaStringComEspacoAEsquerda(Util.formatarData(dataCorteOuSupressa), 10));
		}else{
			arquivoTxt.append(Util.completaStringComEspacoAEsquerda("", 10));
		}

		arquivoTxt = this.gerarCorpoTxtOrdemServicoComDetalheDeConta(arquivoTxt, ordemServico.getImovel().getId());

		arquivoTxt.append(System.getProperty("line.separator"));

		return arquivoTxt;
	}

	/**
	 * [UC0458] Imprimir Ordem de Serviço sem detalhe de conta
	 * Gerar corpo do txt sem detalhe de conta
	 * 
	 * @author Hebert Falcão
	 * @date 19/03/2012
	 */
	private StringBuilder gerarCorpoTxtOrdemServicoSemDetalheDeConta(String caracterControle, OrdemServico ordemServico,
					boolean gerarCabecalho) throws ControladorException{

		Imovel imovel = ordemServico.getImovel();
		Integer idImovel = imovel.getId();

		Integer idOrdemServico = ordemServico.getId();

		// [SB8000] – Obter Dados Emissão Ordem
		Date dataLeituraAnterior = null;
		Integer leituraAnterior = null;
		Integer consumoMes = null;
		Integer consumoMedio = null;
		Date dataInstalacaoHidrometro = null;
		String marcaHirometro = null;
		String numeroHidrometro = null;
		String capacidadeHidrometro = null;
		String diametroHidrometro = null;
		String tipoHidrometro = null;
		String localInstalacaoHidrometro = null;
		String protecaoHidrometro = null;
		String cavaleteHidrometro = null;
		Integer leituraAtual = null;

		// [SB8001] – Determinar tipo de ligação e tipo de medição
		EmitirContaHelper emitirContaHelper = new EmitirContaHelper();

		LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();
		Integer idLigacaoAguaSituacao = ligacaoAguaSituacao.getId();
		emitirContaHelper.setIdLigacaoAguaSituacao(idLigacaoAguaSituacao);

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();
		Integer idLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getId();
		emitirContaHelper.setIdLigacaoEsgotoSituacao(idLigacaoEsgotoSituacao);

		Integer[] tiposLigacaoMedicao = this.getControladorFaturamento().determinarTipoLigacaoMedicao(emitirContaHelper);
		Integer idTipoMedicao = tiposLigacaoMedicao[1];
		Integer idTipoLigacao = tiposLigacaoMedicao[0];

		// [SB8002] - Obter Dados do Hidrômetro
		if(idTipoMedicao != null){
			HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = getControladorMicromedicao().obterDadosHidrometroPorTipoMedicao(
							idImovel, idTipoMedicao);

			if(hidrometroRelatorioOSHelper != null){
				numeroHidrometro = hidrometroRelatorioOSHelper.getHidrometroNumero();

				String dataInstalacaoHidrometroStr = hidrometroRelatorioOSHelper.getDataInstalacaoHidrometo();
				if(!Util.isVazioOuBranco(dataInstalacaoHidrometroStr)){
					dataInstalacaoHidrometro = Util.converteStringParaDate(dataInstalacaoHidrometroStr);
				}

				localInstalacaoHidrometro = hidrometroRelatorioOSHelper.getHidrometroLocal();
				marcaHirometro = hidrometroRelatorioOSHelper.getHidrometroMarca();
				capacidadeHidrometro = hidrometroRelatorioOSHelper.getHidrometroCapacidade();
				diametroHidrometro = hidrometroRelatorioOSHelper.getHidrometroDiametro();
				tipoHidrometro = hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro();
				protecaoHidrometro = hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro();
				cavaleteHidrometro = hidrometroRelatorioOSHelper.getIndicadorCavalete();
			}
		}

		if(idTipoLigacao != null){
			// [SB8003] - Obter Dados de Medição
			ImovelDadosMedicaoHistoricoHelper imovelDadosMedicaoHistoricoHelper = getControladorMicromedicao()
							.obterDadosMedicaoMaiorReferenciaLeitura(imovel, idTipoLigacao);

			if(imovelDadosMedicaoHistoricoHelper != null){
				leituraAnterior = imovelDadosMedicaoHistoricoHelper.getLeituraAnterior();
				leituraAtual = imovelDadosMedicaoHistoricoHelper.getLeituraAtual();

				String dataLeituraAnteriorStr = imovelDadosMedicaoHistoricoHelper.getDataLeituraAnterior();
				if(!Util.isVazioOuBranco(dataLeituraAnteriorStr)){
					dataLeituraAnterior = Util.converteStringParaDate(dataLeituraAnteriorStr);
				}
			}

			// [SB8004] – Obter Dados de Consumo
			ImovelDadosConsumoHistoricoHelper imovelDadosConsumoHistoricoHelper = getControladorMicromedicao()
							.obterDadosConsumoMaiorReferenciaFaturamento(imovel, idTipoLigacao);

			if(imovelDadosConsumoHistoricoHelper != null){
				consumoMes = imovelDadosConsumoHistoricoHelper.getConsumoMes();
				consumoMedio = imovelDadosConsumoHistoricoHelper.getConsumoMedioImovel();
			}
		}

		Integer idLocalidade = null;
		Integer codigoSetorComercial = null;
		Integer idServicoTipo = null;
		Integer qtdEconomiaResidencial = null;
		Integer qtdEconomiaComercial = null;
		Integer qtdEconomiaIndustrial = null;
		Integer qtdEconomiaPublico = null;
		Integer idPiscinaVolumeFaixa = null;
		Integer idSetorAbastecimento = null;
		Integer anoMesFaturamento = null;

		Short codigoRota = null;
		Short numeroSegmento = null;
		Short lote = null;
		Short subLote = null;

		String descricaoServicoTipo = "";
		String descricaoLocalidade = "";
		String enderecoImovel = "";
		String descricaoLigacaoAguaMaterial = "";
		String descricaoLigacaoAguaDiametro = "";
		String descricaoLigacaoEsgotoMaterial = "";
		String nomeClienteImovel = "";

		// Localidade
		Localidade localidade = imovel.getLocalidade();

		if(localidade != null){
			idLocalidade = localidade.getId();
			descricaoLocalidade = localidade.getDescricao();
		}

		// Setor Comercial
		SetorComercial setorComercial = imovel.getSetorComercial();

		if(setorComercial != null){
			codigoSetorComercial = setorComercial.getCodigo();
		}

		// Rota
		Rota rota = imovel.getRota();

		if(rota != null){
			codigoRota = rota.getCodigo();
		}

		// Segmento
		numeroSegmento = imovel.getNumeroSegmento();

		// Lote
		lote = imovel.getLote();

		// SubLote
		subLote = imovel.getSubLote();

		// [UC0108] Obter Quantidade de Economias por Categoria
		Collection<Categoria> colecaoCategoriasImovel = this.getControladorImovel().obterQuantidadeEconomiasCategoria(imovel);

		if(!Util.isVazioOrNulo(colecaoCategoriasImovel)){
			Integer idCategoria = null;

			for(Categoria categoria : colecaoCategoriasImovel){
				idCategoria = categoria.getId();

				if(idCategoria.equals(Categoria.RESIDENCIAL)){
					qtdEconomiaResidencial = categoria.getQuantidadeEconomiasCategoria();
				}else if(idCategoria.equals(Categoria.COMERCIAL)){
					qtdEconomiaComercial = categoria.getQuantidadeEconomiasCategoria();
				}else if(idCategoria.equals(Categoria.INDUSTRIAL)){
					qtdEconomiaIndustrial = categoria.getQuantidadeEconomiasCategoria();
				}else if(idCategoria.equals(Categoria.PUBLICO)){
					qtdEconomiaPublico = categoria.getQuantidadeEconomiasCategoria();
				}
			}
		}

		// [UC0085] Obter Endereço
		enderecoImovel = this.getControladorEndereco().pesquisarEndereco(idImovel);

		// Ligação Água Situação
		if(ligacaoAguaSituacao != null){
			idLigacaoAguaSituacao = ligacaoAguaSituacao.getId();
		}

		// Ligação Esgoto Situação
		if(ligacaoEsgotoSituacao != null){
			idLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getId();
		}

		// Piscina
		PiscinaVolumeFaixa piscinaVolumeFaixa = imovel.getPiscinaVolumeFaixa();

		if(piscinaVolumeFaixa != null){
			idPiscinaVolumeFaixa = piscinaVolumeFaixa.getId();
		}

		// Setor Abastecimento
		SetorAbastecimento setorAbastecimento = imovel.getSetorAbastecimento();

		if(setorAbastecimento != null){
			idSetorAbastecimento = setorAbastecimento.getId();
		}

		// Ligação Água
		LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();

		if(ligacaoAgua != null){
			// Ligação Água Material
			LigacaoAguaMaterial ligacaoAguaMaterial = ligacaoAgua.getLigacaoAguaMaterial();

			if(ligacaoAguaMaterial != null){
				descricaoLigacaoAguaMaterial = ligacaoAguaMaterial.getDescricao();
			}

			// Ligação Água Diâmetro
			LigacaoAguaDiametro ligacaoAguaDiametro = ligacaoAgua.getLigacaoAguaDiametro();

			if(ligacaoAguaDiametro != null){
				descricaoLigacaoAguaDiametro = ligacaoAguaDiametro.getDescricao();
			}
		}

		// Ligação Esgoto
		LigacaoEsgoto ligacaoEsgoto = imovel.getLigacaoEsgoto();

		if(ligacaoEsgoto != null){
			// Ligação Esgoto Material
			LigacaoEsgotoMaterial ligacaoEsgotoMaterial = ligacaoEsgoto.getLigacaoEsgotoMaterial();

			if(ligacaoEsgotoMaterial != null){
				descricaoLigacaoEsgotoMaterial = ligacaoEsgotoMaterial.getDescricao();
			}
		}

		// Nome do Imóvel ou Cliente
		nomeClienteImovel = imovel.getNomeImovel();

		if(Util.isVazioOuBranco(nomeClienteImovel)){
			Cliente cliente = this.getControladorCliente().retornarDadosClienteUsuario(idImovel);

			nomeClienteImovel = cliente.getNome();
		}

		// Tipo de Serviço
		ServicoTipo servicoTipo = ordemServico.getServicoTipo();

		if(servicoTipo != null){
			idServicoTipo = servicoTipo.getId();
			descricaoServicoTipo = servicoTipo.getDescricao();
		}

		// Parâmetro do Sistema
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		if(sistemaParametro != null){
			anoMesFaturamento = sistemaParametro.getAnoMesFaturamento();
		}

		StringBuilder arquivoTxtLinha = new StringBuilder();

		if(gerarCabecalho){
			// #### Gerar a primeira linha de controle do arquivo TXT ####

			// 01. Caractere de controle
			arquivoTxtLinha.append(Util.completaString("%!", 665));

			arquivoTxtLinha.append(System.getProperty("line.separator"));

			// #### Gerar a segunda linha de controle do arquivo TXT ####

			// 01. Caractere de controle
			arquivoTxtLinha.append(Util.completaString(caracterControle, 665));

			arquivoTxtLinha.append(System.getProperty("line.separator"));
		}

		// #### Gerar a linha com os dados da ordem de serviço recebida ####

		// 01. Caractere de controle
		arquivoTxtLinha.append("1");

		// 02. Caractere de controle
		arquivoTxtLinha.append("81");

		// 03. Data Emissão
		arquivoTxtLinha.append(Util.formatarData(new Date()));

		// 04. Identificação da OS
		String idOrdemServicoStr = "";

		if(idOrdemServico != null){
			idOrdemServicoStr = Integer.toString(idOrdemServico);
		}

		idOrdemServicoStr = Util.adicionarZerosEsquedaNumero(9, idOrdemServicoStr);
		idOrdemServicoStr = idOrdemServicoStr.substring(0, 9);
		arquivoTxtLinha.append(idOrdemServicoStr);

		// 05. Localidade do Imóvel
		String idLocalidadeStr = "";

		if(idLocalidade != null){
			idLocalidadeStr = Integer.toString(idLocalidade);
		}

		idLocalidadeStr = Util.adicionarZerosEsquedaNumero(3, idLocalidadeStr);
		idLocalidadeStr = idLocalidadeStr.substring(0, 3);
		arquivoTxtLinha.append(idLocalidadeStr);

		// 06. Identificação do Imóvel
		String idImovelStr = "";

		if(idImovel != null){
			idImovelStr = Integer.toString(idImovel);
		}

		idImovelStr = Util.adicionarZerosEsquedaNumero(7, idImovelStr);
		idImovelStr = idImovelStr.substring(0, 7);
		arquivoTxtLinha.append(idImovelStr);

		// 07. Setor do Imóvel
		String codigoSetorComercialStr = "";

		if(codigoSetorComercial != null){
			codigoSetorComercialStr = Integer.toString(codigoSetorComercial);
		}

		codigoSetorComercialStr = Util.adicionarZerosEsquedaNumero(2, codigoSetorComercialStr);
		codigoSetorComercialStr = codigoSetorComercialStr.substring(0, 2);
		arquivoTxtLinha.append(codigoSetorComercialStr);

		// 08. Rota do Imóvel
		String codigoRotaStr = "";

		if(codigoRota != null){
			codigoRotaStr = Short.toString(codigoRota);
		}

		codigoRotaStr = Util.adicionarZerosEsquedaNumero(2, codigoRotaStr);
		codigoRotaStr = codigoRotaStr.substring(0, 2);
		arquivoTxtLinha.append(codigoRotaStr);

		// 09. Segmento do Imóvel
		String numeroSegmentoStr = "";

		if(numeroSegmento != null){
			numeroSegmentoStr = Short.toString(numeroSegmento);
		}

		numeroSegmentoStr = Util.adicionarZerosEsquedaNumero(2, numeroSegmentoStr);
		numeroSegmentoStr = numeroSegmentoStr.substring(0, 2);
		arquivoTxtLinha.append(numeroSegmentoStr);

		// 10. Lote do Imóvel
		String loteStr = "";

		if(lote != null){
			loteStr = Short.toString(lote);
		}

		loteStr = Util.adicionarZerosEsquedaNumero(4, loteStr);
		loteStr = loteStr.substring(0, 4);
		arquivoTxtLinha.append(loteStr);

		// 11. Sublote do Imóvel
		String subLoteStr = "";

		if(subLote != null){
			subLoteStr = Short.toString(subLote);
		}

		subLoteStr = Util.adicionarZerosEsquedaNumero(2, subLoteStr);
		subLoteStr = subLoteStr.substring(0, 2);
		arquivoTxtLinha.append(subLoteStr);

		// 12. Descrição do Serviço
		arquivoTxtLinha.append(Util.completaString(descricaoServicoTipo, 22));

		// 13.
		arquivoTxtLinha.append(Util.completaString("", 22));

		// 14. Nome da Localidade
		arquivoTxtLinha.append(Util.completaString(descricaoLocalidade, 22));

		// 15. Código do Serviço
		String idServicoTipoStr = "";

		if(idServicoTipo != null){
			idServicoTipoStr = Integer.toString(idServicoTipo);
		}

		idServicoTipoStr = Util.adicionarZerosEsquedaNumero(3, idServicoTipoStr);
		idServicoTipoStr = idServicoTipoStr.substring(0, 3);
		arquivoTxtLinha.append(idServicoTipoStr);

		// 16. Nome do Cliente
		arquivoTxtLinha.append(Util.completaString(nomeClienteImovel, 37));

		// 17. Endereço
		arquivoTxtLinha.append(Util.completaString(enderecoImovel, 50));

		// 18. Qtde. Economias RES
		String qtdEconomiaResidencialStr = "";

		if(qtdEconomiaResidencial != null){
			qtdEconomiaResidencialStr = Integer.toString(qtdEconomiaResidencial);
		}

		qtdEconomiaResidencialStr = Util.adicionarZerosEsquedaNumero(3, qtdEconomiaResidencialStr);
		qtdEconomiaResidencialStr = qtdEconomiaResidencialStr.substring(0, 3);
		arquivoTxtLinha.append(qtdEconomiaResidencialStr);

		// 19. Qtde. Economias COM
		String qtdEconomiaComercialStr = "";

		if(qtdEconomiaComercial != null){
			qtdEconomiaComercialStr = Integer.toString(qtdEconomiaComercial);
		}

		qtdEconomiaComercialStr = Util.adicionarZerosEsquedaNumero(3, qtdEconomiaComercialStr);
		qtdEconomiaComercialStr = qtdEconomiaComercialStr.substring(0, 3);
		arquivoTxtLinha.append(qtdEconomiaComercialStr);

		// 20. Qtde. Economias IND
		String qtdEconomiaIndustrialStr = "";

		if(qtdEconomiaIndustrial != null){
			qtdEconomiaIndustrialStr = Integer.toString(qtdEconomiaIndustrial);
		}

		qtdEconomiaIndustrialStr = Util.adicionarZerosEsquedaNumero(3, qtdEconomiaIndustrialStr);
		qtdEconomiaIndustrialStr = qtdEconomiaIndustrialStr.substring(0, 3);
		arquivoTxtLinha.append(qtdEconomiaIndustrialStr);

		// 21. Qtde. Economias PUB
		String qtdEconomiaPublicoStr = "";

		if(qtdEconomiaPublico != null){
			qtdEconomiaPublicoStr = Integer.toString(qtdEconomiaPublico);
		}

		qtdEconomiaPublicoStr = Util.adicionarZerosEsquedaNumero(3, qtdEconomiaPublicoStr);
		qtdEconomiaPublicoStr = qtdEconomiaPublicoStr.substring(0, 3);
		arquivoTxtLinha.append(qtdEconomiaPublicoStr);

		// 22. Situação da Ligação de Água
		String idLigacaoAguaSituacaoStr = "";

		if(idLigacaoAguaSituacao != null){
			idLigacaoAguaSituacaoStr = Integer.toString(idLigacaoAguaSituacao);
		}

		idLigacaoAguaSituacaoStr = Util.adicionarZerosEsquedaNumero(1, idLigacaoAguaSituacaoStr);
		idLigacaoAguaSituacaoStr = idLigacaoAguaSituacaoStr.substring(0, 1);
		arquivoTxtLinha.append(idLigacaoAguaSituacaoStr);

		// 23. Situação da Ligação de Esgoto
		String idLigacaoEsgotoSituacaoStr = "";

		if(idLigacaoEsgotoSituacao != null){
			idLigacaoEsgotoSituacaoStr = Integer.toString(idLigacaoEsgotoSituacao);
		}

		idLigacaoEsgotoSituacaoStr = Util.adicionarZerosEsquedaNumero(1, idLigacaoEsgotoSituacaoStr);
		idLigacaoEsgotoSituacaoStr = idLigacaoEsgotoSituacaoStr.substring(0, 1);
		arquivoTxtLinha.append(idLigacaoEsgotoSituacaoStr);

		// 24. Piscina
		String idPiscinaVolumeFaixaStr = "";

		if(idPiscinaVolumeFaixa != null){
			idPiscinaVolumeFaixaStr = Integer.toString(idPiscinaVolumeFaixa);
		}

		idPiscinaVolumeFaixaStr = Util.adicionarZerosEsquedaNumero(1, idPiscinaVolumeFaixaStr);
		idPiscinaVolumeFaixaStr = idPiscinaVolumeFaixaStr.substring(0, 1);
		arquivoTxtLinha.append(idPiscinaVolumeFaixaStr);

		// 25. Setor Abastecimento
		String idSetorAbastecimentoStr = "";

		if(idSetorAbastecimento != null){
			idSetorAbastecimentoStr = Integer.toString(idSetorAbastecimento);
		}

		idSetorAbastecimentoStr = Util.adicionarZerosEsquedaNumero(3, idSetorAbastecimentoStr);
		idSetorAbastecimentoStr = idSetorAbastecimentoStr.substring(0, 3);
		arquivoTxtLinha.append(idSetorAbastecimentoStr);

		// 26. Material da Ligação de Água
		arquivoTxtLinha.append(Util.completaString(descricaoLigacaoAguaMaterial, 10));

		// 27. Diâmetro da Ligação de Água
		arquivoTxtLinha.append(Util.completaString(descricaoLigacaoAguaDiametro, 12));

		// 28. Material da Ligação de Esgoto
		arquivoTxtLinha.append(Util.completaString(descricaoLigacaoEsgotoMaterial, 10));

		// 29. Data de Leitura Anterior
		String dataLeituraAnteriorStr = "";

		if(dataLeituraAnterior != null){
			dataLeituraAnteriorStr = Util.formatarData(dataLeituraAnterior);
		}

		arquivoTxtLinha.append(Util.completaString(dataLeituraAnteriorStr, 10));

		// 30. Leitura Anterior
		String leituraAnteriorStr = "";

		if(leituraAnterior != null){
			leituraAnteriorStr = Integer.toString(leituraAnterior);
		}

		leituraAnteriorStr = Util.adicionarZerosEsquedaNumero(6, leituraAnteriorStr);
		leituraAnteriorStr = leituraAnteriorStr.substring(0, 6);
		arquivoTxtLinha.append(leituraAnteriorStr);

		// 31. Consumo Mês
		String consumoMesStr = "";

		if(consumoMes != null){
			consumoMesStr = Integer.toString(consumoMes);
		}

		consumoMesStr = Util.adicionarZerosEsquedaNumero(6, consumoMesStr);
		consumoMesStr = consumoMesStr.substring(0, 6);
		arquivoTxtLinha.append(consumoMesStr);

		// 32. Consumo Médio
		String consumoMedioStr = "";

		if(consumoMedio != null){
			consumoMedioStr = Integer.toString(consumoMedio);
		}

		consumoMedioStr = Util.adicionarZerosEsquedaNumero(5, consumoMedioStr);
		consumoMedioStr = consumoMedioStr.substring(0, 5);
		arquivoTxtLinha.append(consumoMedioStr);

		// 33. Data Instalação Hidrômetro
		String dataInstalacaoHidrometroStr = "";

		if(dataInstalacaoHidrometro != null){
			dataInstalacaoHidrometroStr = Util.formatarData(dataInstalacaoHidrometro);
		}

		arquivoTxtLinha.append(Util.completaString(dataInstalacaoHidrometroStr, 10));

		// 34.
		if(dataInstalacaoHidrometro != null){
			arquivoTxtLinha.append("-");
		}else{
			arquivoTxtLinha.append(" ");
		}

		// 35. Número de Meses da Instalação Hidrômetro
		String numeroMesesInstalacaoHidStr = "";

		if(dataInstalacaoHidrometro != null){
			if(anoMesFaturamento != null){
				Date dataFaturamento = Util.gerarDataInicialApartirAnoMesRefencia(anoMesFaturamento);

				long numeroMeses = Util.calcularDiferencaDeMes(dataInstalacaoHidrometro, dataFaturamento);

				numeroMesesInstalacaoHidStr = Long.toString(numeroMeses);
			}

			numeroMesesInstalacaoHidStr = Util.adicionarZerosEsquedaNumero(3, numeroMesesInstalacaoHidStr);
			numeroMesesInstalacaoHidStr = numeroMesesInstalacaoHidStr.substring(0, 3);
			arquivoTxtLinha.append(numeroMesesInstalacaoHidStr);
		}else{
			arquivoTxtLinha.append(Util.completaString(numeroMesesInstalacaoHidStr, 3));
		}

		// 36. Marca do Hidrômetro
		if(marcaHirometro == null){
			marcaHirometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(marcaHirometro, 11));

		// 37. Número do Hidrômetro
		if(numeroHidrometro == null){
			numeroHidrometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(numeroHidrometro, 19));

		// 38. Capacidade do Hidrômetro
		if(capacidadeHidrometro == null){
			capacidadeHidrometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(capacidadeHidrometro, 12));

		// 39. Diâmetro do Hidrômetro
		if(diametroHidrometro == null){
			diametroHidrometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(diametroHidrometro, 9));

		// 40. Tipo do Hidrômetro
		if(tipoHidrometro == null){
			tipoHidrometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(tipoHidrometro, 9));

		// 41. Local Instalação do Hidrômetro
		if(localInstalacaoHidrometro == null){
			localInstalacaoHidrometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(localInstalacaoHidrometro, 9));

		// 42. Proteção do Hidrômetro
		if(protecaoHidrometro == null){
			protecaoHidrometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(protecaoHidrometro, 10));

		// 43. Cavalete do Hidrômetro
		if(cavaleteHidrometro == null){
			cavaleteHidrometro = "";
		}

		arquivoTxtLinha.append(Util.completaString(cavaleteHidrometro, 11));

		// 44. Leitura Atual do Hidrômetro
		String leituraAtualStr = "";

		if(leituraAtual != null){
			leituraAtualStr = Integer.toString(leituraAtual);
		}

		leituraAtualStr = Util.adicionarZerosEsquedaNumero(11, leituraAtualStr);
		leituraAtualStr = leituraAtualStr.substring(0, 11);
		arquivoTxtLinha.append(leituraAtualStr);

		return arquivoTxtLinha;
	}

	/**
	 * [UC0458] Imprimir Ordem de Serviço com detalhe de conta
	 * Gerar corpo do txt com detalhe de conta
	 * [SB9001 - Obter Contas Vencidas do Imóvel]
	 * 
	 * @author Yara Souza
	 * @date 21/03/2012
	 */
	private StringBuilder gerarCorpoTxtOrdemServicoComDetalheDeConta(StringBuilder arquivoTxtLinha, Integer idImovel)
					throws ControladorException{

		Object[] retorno = getControladorCobranca().obterContasVencidasDoImovel(idImovel, true);
		Integer quantidadeContasVencidas = (Integer) retorno[0];
		BigDecimal valorContasVencidas = (BigDecimal) retorno[1];
		Object[] contasVencidas = (Object[]) retorno[2];

		// 47. Quantidade de contas vencidas
		// zeros
		arquivoTxtLinha.append(Util.adicionarZerosEsquedaNumero(3, quantidadeContasVencidas.toString()));

		// 48. Valor de contas vencidas
		arquivoTxtLinha.append(Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(valorContasVencidas), 17));

		if(quantidadeContasVencidas > 0){
			// ******************************************************************
			// Dados das contas vencidas
			// ******************************************************************
			for(int i = 0; i < 9; i++){
				montarArquivoTxtLinhaConta((ContaVencidaHelper) contasVencidas[i], arquivoTxtLinha);
			}
		}else{
			for(int i = 0; i < 9; i++){
				montarArquivoTxtLinhaBranca((ContaVencidaHelper) contasVencidas[i], arquivoTxtLinha);
			}
		}

		return arquivoTxtLinha;
	}

	private StringBuilder montarArquivoTxtLinhaConta(ContaVencidaHelper contaVencidaHelper, StringBuilder arquivoTxtLinha){

		if(contaVencidaHelper != null){
			// referencia
			arquivoTxtLinha.append(Util.completaStringComEspacoAEsquerda(Util.formatarAnoMesParaMesAno(contaVencidaHelper.getReferencia()),
							7));
			// valor conta
			arquivoTxtLinha.append(Util.completaStringComEspacoAEsquerda(Util.formatarMoedaReal(contaVencidaHelper.getValor()), 10));
			// vencimento conta
			arquivoTxtLinha.append(Util.completaStringComEspacoAEsquerda(contaVencidaHelper.getVencimento(), 10));
		}

		return arquivoTxtLinha;
	}

	private StringBuilder montarArquivoTxtLinhaBranca(ContaVencidaHelper contaVencidaHelper, StringBuilder arquivoTxtLinha){

		if(contaVencidaHelper != null){
			// referencia
			arquivoTxtLinha.append(Util.completaStringComEspacoAEsquerda("", 7));
			// valor conta
			arquivoTxtLinha.append(Util.completaStringComEspacoAEsquerda("", 10));
			// vencimento conta
			arquivoTxtLinha.append(Util.completaStringComEspacoAEsquerda("", 10));
		}

		return arquivoTxtLinha;
	}

	/**
	 * Gerar Trâmite de Ordem de Serviço
	 * 
	 * @author Hebert Falcão
	 * @date 17/12/2011
	 */
	private void gerarTramiteOrdemServico(Integer idLocalidade, Integer idSetorComercial, Integer idBairro, Integer idUnidadeOrigem,
					Integer idUnidadeDestino, String parecerUnidadeDestino, Usuario usuarioLogado, OrdemServico ordemServico)
					throws ControladorException{

		Date dataAtual = new Date();

		Integer idUnidadeTramiteOS = null;

		if(ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar().toString().equals(ConstantesSistema.SIM.toString())){
			ServicoTipo servicoTipo = ordemServico.getServicoTipo();
			Integer idServicoTipo = servicoTipo.getId();

			// [UC3036] – Obter Unidade Tramite Ordem de Serviço
			idUnidadeTramiteOS = this.pesquisarUnidadeTramiteOS(idServicoTipo, idLocalidade, idSetorComercial, idBairro, idUnidadeOrigem);

			if(idUnidadeTramiteOS == null){
				if(idUnidadeDestino != null){
					idUnidadeTramiteOS = idUnidadeDestino;
				}else{
					idUnidadeTramiteOS = idUnidadeOrigem;
				}
			}

			Tramite tramite = new Tramite();
			tramite.setOrdemServico(ordemServico);

			UnidadeOrganizacional unidadeOrganizacionalOrigem = new UnidadeOrganizacional();
			unidadeOrganizacionalOrigem.setId(idUnidadeOrigem);
			tramite.setUnidadeOrganizacionalOrigem(unidadeOrganizacionalOrigem);

			UnidadeOrganizacional unidadeOrganizacionalDestino = new UnidadeOrganizacional();
			unidadeOrganizacionalDestino.setId(idUnidadeTramiteOS);
			tramite.setUnidadeOrganizacionalDestino(unidadeOrganizacionalDestino);

			tramite.setUsuarioRegistro(usuarioLogado);
			tramite.setUsuarioResponsavel(usuarioLogado);

			if(!Util.isVazioOuBranco(parecerUnidadeDestino)){
				tramite.setParecerTramite(parecerUnidadeDestino);
			}else{
				tramite.setParecerTramite(Tramite.TRAMITE_GERADO_PELO_SISTEMA_GERACAO_OS);
			}

			tramite.setDataTramite(dataAtual);
			tramite.setUltimaAlteracao(dataAtual);

			// [UC3039] - Tramitar Ordem de Serviço
			this.tramitarOS(tramite, dataAtual);
		}
	}

	/**
	 * Método que analisa se uma Ordem de serviço pode ser encerrada. O bloqueio é feito de forma
	 * parametrizada. Caso o parametro isFluxoPassaParaProximoImovel venha com valor verdadeiro o
	 * metodo pode retornar um valor falso que pode ser utilizado como condição para geração de uma
	 * Ordem de Serviço. Caso o parametro isFluxoPassaParaProximoImovel seja falso a validação é
	 * feita por meio de lançamento de exceções.
	 * [UC0430] Gerar Ordem de Serviço
	 * [FS0010] - Verificar restrição de emissão da Ordem de Serviço
	 * 
	 * @author Hugo Lima
	 * @date 06/08/2012
	 * @param usuario
	 * @param ordemServico
	 * @param indicadorFluxoPassaParaProximoImovel
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarRestricaoEmissaoOrdemServico(Usuario usuario, OrdemServico ordemServico,
					Short indicadorFluxoPassaParaProximoImovel) throws ControladorException{

		boolean retorno = true;
		boolean existeOSEncerradaRestricao = false;
		Date dataAtual = new Date();
		Date dataEncerramentoOS = null;
		Date dataLimite = null;

		try{
			// 1. Caso exista imóvel para a ordem de serviço:
			if(ordemServico.getImovel() == null || ordemServico.getImovel().getId() == null
							|| ordemServico.getServicoTipo().getPrazoRestricaoNovaOrdemServico() == 0){
				// Retorna para o fluxo que chamou o método caso não possua imóvel
				return true;
			}

			// Consulta as ordens de serviço encerradas com o mesmo tipo de servico da ordem passada
			// pelo parametro
			dataEncerramentoOS = this.repositorioOrdemServico.obterEncerramentoOrdemServicoTipoServicoRestricaoPrazoNaoExpirado(
							ordemServico.getImovel().getId(), ordemServico.getServicoTipo().getId());

			// 1.1. Caso ordem de serviço encerrada com o mesmo tipo de serviço informado e o
			// prazo de restrição para geração de uma nova OS não tenha expirado e seja do
			// imóvel relacionado
			if(dataEncerramentoOS != null){

				// com SVTP_ID = Id do serviço tipo informado e ORSE_TMENCERRAMENTO seja maior ou
				// igual à Data Atual menos SVTP_NNPRAZORESTRICAONOVAOS (da tabela SERVICO_TIPO com
				// SVTP_ID = Id do serviço tipo informado) e IMOV_ID = Id do Imóvel da Ordem de
				// Serviço)
				dataLimite = Util.subtrairNumeroDiasDeUmaData(dataAtual, ordemServico.getServicoTipo().getPrazoRestricaoNovaOrdemServico());

				if(!(Util.compararData(dataEncerramentoOS, dataLimite) == -1)){
					existeOSEncerradaRestricao = true;
				}

			}

			if(existeOSEncerradaRestricao){
				// 1.1.1. Caso o usuário não tem permissão especial de gerar ordem de serviço com
				// restrição
				if(!this.getControladorPermissaoEspecial().verificarPermissaoGerarOrdemServicoRestricao(usuario)){
					// 1.1.1.1 Caso o fluxo seja da geração de uma OS Seletiva ou Ação de Cobrança,
					// não gerar a OS e passar para o próximo imóvel.
					if(indicadorFluxoPassaParaProximoImovel.equals(ConstantesSistema.SIM)){
						retorno = false;
					}else{
						// 1.1.1.2 Caso contrário, exibir a mensagem
						throw new ControladorException("atencao.servico_tipo.existe_os_restricao_dias", null, ordemServico.getServicoTipo()
										.getDescricao(), ordemServico.getServicoTipo().getPrazoRestricaoNovaOrdemServico().toString());
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * [UC3063] Religacao Agua com Instalacao de Registro Magnetico
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */

	public void validarOrdemServicoRegistroMagnetico(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		if(!veioEncerrarOS){

			if(ordemServico.getSituacao() != OrdemServico.SITUACAO_ENCERRADO.shortValue()){

				try{
					sessionContext.setRollbackOnly();
				}catch(IllegalStateException ex){

				}
				throw new ControladorException("atencao.ordem_servico_situacao", null, OrdemServico.SITUACAO_DESCRICAO_ENCERRADO);
			}else{
				if(ordemServico.getAtendimentoMotivoEncerramento() != null
								&& ordemServico.getAtendimentoMotivoEncerramento().getIndicadorExecucao() != ConstantesSistema.SIM){

					try{
						sessionContext.setRollbackOnly();
					}catch(IllegalStateException ex){

					}
					throw new ControladorException("atencao.ordem_servico_nao_executada_instalacao_substituicao_registro_magnetico", null,
									OrdemServico.SITUACAO_DESCRICAO_ENCERRADO_NAO_EXECUTADA);
				}
			}

		}
	}

	/**
	 * [UC3064] Efetuar Instalação/Substituição de Tubete Magnético
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */

	public void validarOrdemServicoTubeteMagnetico(OrdemServico ordemServico, boolean veioEncerrarOS) throws ControladorException{

		if(!veioEncerrarOS){

			// . Caso a Ordem de Serviço tenha sido informada via Menu e a situação da Ordem de
			// Serviço
			// não esteja encerrada por execução
			// (ORSE_CDSITUAÇÃO na tabela ORDEM_SERVICO com o valor diferente de “Encerrada” (2)),
			// exibir a mensagem “Esta Ordem de Serviço está <<descrição da situação >>.
			// Não é possível efetuar a instalação/substituição de tubete magnético.” e retornar
			// para o
			// passo correspondente no fluxo principal.

			if(ordemServico.getSituacao() != OrdemServico.SITUACAO_ENCERRADO.shortValue()){

				throw new ControladorException("atencao.ordem_servico_situacao", null, OrdemServico.SITUACAO_DESCRICAO_ENCERRADO);

			}

			// . Caso a Ordem de Serviço tenha sido informada via Menu e a situação da Ordem de
			// Serviço
			// esteja encerrada sem ser por execução (ORSE_CDSITUAÇÃO na tabela ORDEM_SERVICO
			// com o
			// valor igual a “Encerrada” (2) e o tipo de encerramento da ordem de serviço esteja
			// com o
			// indicador diferente de execução (AMEN_ICEXECUCAO com o valor diferente de
			// “Sim”(1) na
			// tabela ATENDIMENTO_MOTIVO_ENCERRAMENTO para AMEN_ID=AMEN_ID da tabela
			// ORDEM_SERVIÇO),
			// exibir a mensagem “Esta Ordem de Serviço está encerrada, porém não foi executada.
			// Não é
			// possível efetuar a instalação/substituição de tubete magnético.” e retornar para
			// o passo
			// correspondente no fluxo principal.

			if(ordemServico.getAtendimentoMotivoEncerramento() != null
							&& ordemServico.getAtendimentoMotivoEncerramento().getIndicadorExecucao() != ConstantesSistema.SIM){

				throw new ControladorException("atencao.ordem_servico_nao_executada_tubete_magnetico", null,
								OrdemServico.SITUACAO_DESCRICAO_ENCERRADO_NAO_EXECUTADA);
			}

		}

	}

	/**
	 * [UC3063] Efetuar Instalação/Substituição de Registro Magnético
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */
	public OrdemServico recuperaOSPorIdRegistroMagnetico(Integer idOS) throws ControladorException{

		try{
			return repositorioOrdemServico.recuperaOrdemServicoPorId(idOS);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC3064] Religacao Agua com Instalacao de Tubete Magnetico
	 * 
	 * @date 28/08/2012
	 * @author Leonardo Angelim
	 */
	public OrdemServico recuperaOSPorIdTubeteMagnetico(Integer idOS) throws ControladorException{

		try{
			return repositorioOrdemServico.recuperaOrdemServicoPorId(idOS);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0457] Encerrar Ordem de Serviço
	 * [SB0009] Verificar Atualização Situação Ação Documento de Cobrança
	 * 
	 * @author Luciano Galvao
	 * @throws ControladorException
	 * @date 17/12/2012
	 */
	private void atualizarSituacaoAcaoCobranca(Integer ordemServicoId, Integer cobrancaAcaoSituacaoId) throws ControladorException{

		if(ordemServicoId != null && cobrancaAcaoSituacaoId != null){

			// Consulta a Ordem de Serviço, carregando todas as informações que serão necessárias a
			// seguir
			FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
			filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, ordemServicoId));
			filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.COBRANCA_DOCUMENTO);
			filtroOrdemServico
							.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.COBRANCA_DOCUMENTO_COBRANCA_ACAO_ATIVIDADE_COMANDO);
			filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.COBRANCA_DOCUMENTO_COBRANCA_ACAO_SITUACAO);
			filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.COBRANCA_DOCUMENTO_COBRANCA_ACAO);
			filtroOrdemServico
							.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.COBRANCA_DOCUMENTO_COBRANCA_ACAO_COBRANCA_ACAO_PREDECESSORA);
			filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.COBRANCA_DOCUMENTO_IMOVEL);

			OrdemServico ordemServico = (OrdemServico) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroOrdemServico,
							OrdemServico.class.getName()));

			// 1. Caso a ordem de serviço que está sendo encerrada esteja associada a um
			// documento de cobrança
			if(ordemServico != null && ordemServico.getCobrancaDocumento() != null){
				CobrancaDocumento cobrancaDocumento = ordemServico.getCobrancaDocumento();

				// 1.1. Caso o documento de cobrança da ordem de serviço não esteja associado a
				// um comando de cobrança
				if(cobrancaDocumento.getCobrancaAcaoAtividadeComando() == null){

					// 1.1.1. Caso a situação da ação do documento seja nulo ou “ação pendente”
					if(cobrancaDocumento.getCobrancaAcaoSituacao() == null
									|| cobrancaDocumento.getCobrancaAcaoSituacao().getId().equals(CobrancaAcaoSituacao.PENDENTE)){

						CobrancaAcaoSituacao novaSituacao = new CobrancaAcaoSituacao();
						novaSituacao.setId(cobrancaAcaoSituacaoId);

						// Atualizar a situação da ação do documento
						cobrancaDocumento.setCobrancaAcaoSituacao(novaSituacao);
						cobrancaDocumento.setDataSituacaoAcao(ordemServico.getDataExecucao());
						cobrancaDocumento.setUltimaAlteracao(new Date());

						getControladorUtil().atualizar(cobrancaDocumento);

						// 1.1.2. Caso a Situação_Ação_Cobrança recebida corresponda a
						// “CANCELADA”
						if(novaSituacao.getId().equals(CobrancaAcaoSituacao.CANCELADA)){

							// 1.1.2.1. Caso a ação do documento de cobrança tenha ação
							// precedente
							if(cobrancaDocumento.getCobrancaAcao() != null
											&& cobrancaDocumento.getCobrancaAcao().getCobrancaAcaoPredecessora() != null){

								// Consulta o documento de cobrança da ação precedente
								FiltroCobrancaDocumento filtroCobrancaDocumentoPrecedente = new FiltroCobrancaDocumento();
								filtroCobrancaDocumentoPrecedente.adicionarParametro(new ParametroSimples(
												FiltroCobrancaDocumento.ID_COBRANCA_ACAO, cobrancaDocumento.getCobrancaAcao()
																.getCobrancaAcaoPredecessora().getId()));
								filtroCobrancaDocumentoPrecedente.adicionarParametro(new ParametroNulo(
												FiltroCobrancaDocumento.ID_COBRANCA_ACAO_ATIVIDADE_COMANDO));
								filtroCobrancaDocumentoPrecedente.adicionarParametro(new ParametroSimples(
												FiltroCobrancaDocumento.IMOVEL_ID, cobrancaDocumento.getImovel().getId()));
								filtroCobrancaDocumentoPrecedente.adicionarParametro(new MenorQue(FiltroCobrancaDocumento.DATA_EMISSAO,
												cobrancaDocumento.getEmissao()));
								filtroCobrancaDocumentoPrecedente.setCampoOrderBy("emissao desc");

								CobrancaDocumento documentoCobrancaPrecedente = (CobrancaDocumento) Util
												.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroCobrancaDocumentoPrecedente,
																CobrancaDocumento.class.getName()));

								// 1.1.2.1.1. Caso exista o documento de cobrança precedente
								if(documentoCobrancaPrecedente != null){
									CobrancaAcaoSituacao cobrancaAcaoSituacao = documentoCobrancaPrecedente.getCobrancaAcaoSituacao();

									if(cobrancaAcaoSituacao != null
													&& !cobrancaAcaoSituacao.getId().equals(CobrancaAcaoSituacao.CANCELADA_PRAZO)){
										// Atualizar a situação da ação do documento de cobrança
										// precedente
										documentoCobrancaPrecedente.setCobrancaAcaoSituacao(novaSituacao);
										documentoCobrancaPrecedente.setDataSituacaoAcao(ordemServico.getDataExecucao());
										documentoCobrancaPrecedente.setUltimaAlteracao(new Date());

										getControladorUtil().atualizar(documentoCobrancaPrecedente);
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * [UC0468] Consultar Ordem de Serviço
	 * [SB0002] – Habilitar Emissão da OS
	 * Retorna <true> se a opção de Imprimir OS deve estar habilitada. Retorna <false>, caso
	 * contrário
	 * 
	 * @author Luciano Galvao
	 * @throws ControladorException
	 * @date 17/12/2012
	 */
	public boolean verificarHabilitacaoEmissaoOS(OrdemServico ordemServico) throws ControladorException{

		boolean retorno = true;

		if(ordemServico != null && ordemServico.getCobrancaDocumento() != null){

			FiltroCobrancaDocumento filtroCobrancaDocumento = new FiltroCobrancaDocumento();
			filtroCobrancaDocumento.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID, ordemServico.getCobrancaDocumento()
							.getId()));
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.ATIVIDADE_COMANDO);
			filtroCobrancaDocumento.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.COBRANCA_ACAO);
			filtroCobrancaDocumento
							.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaDocumento.COBRANCA_ACAO_COBRANCA_ACAO_PREDECESSORA);

			CobrancaDocumento cobrancaDocumento = (CobrancaDocumento) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
							filtroCobrancaDocumento, CobrancaDocumento.class.getName()));

			if(cobrancaDocumento != null && cobrancaDocumento.getCobrancaAcaoAtividadeComando() == null
							&& cobrancaDocumento.getCobrancaAcao() != null
							&& cobrancaDocumento.getCobrancaAcao().getCobrancaAcaoPredecessora() != null){

				// Consulta o documento de cobrança da ação precedente
				FiltroCobrancaDocumento filtroCobrancaDocumentoPrecedente = new FiltroCobrancaDocumento();
				filtroCobrancaDocumentoPrecedente.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.ID_COBRANCA_ACAO,
								cobrancaDocumento.getCobrancaAcao().getCobrancaAcaoPredecessora().getId()));
				filtroCobrancaDocumentoPrecedente.adicionarParametro(new ParametroNulo(
								FiltroCobrancaDocumento.ID_COBRANCA_ACAO_ATIVIDADE_COMANDO));
				filtroCobrancaDocumentoPrecedente.adicionarParametro(new ParametroSimples(FiltroCobrancaDocumento.IMOVEL_ID,
								cobrancaDocumento.getImovel().getId()));
				filtroCobrancaDocumentoPrecedente.adicionarParametro(new MenorQue(FiltroCobrancaDocumento.DATA_EMISSAO, cobrancaDocumento
								.getEmissao()));
				filtroCobrancaDocumentoPrecedente.setCampoOrderBy("emissao desc");

				CobrancaDocumento cobrancaDocumentoPrecedente = (CobrancaDocumento) Util.retonarObjetoDeColecao(getControladorUtil()
								.pesquisar(filtroCobrancaDocumentoPrecedente, CobrancaDocumento.class.getName()));

				if(cobrancaDocumentoPrecedente != null && cobrancaDocumentoPrecedente.getEmissao() != null){

					Date dataEmissao = cobrancaDocumentoPrecedente.getEmissao();

					if(cobrancaDocumento.getCobrancaAcao().getNumeroDiasMinimoAcaoPrecedente() != null){
						dataEmissao = Util.adicionarNumeroDiasDeUmaData(dataEmissao, cobrancaDocumento.getCobrancaAcao()
										.getNumeroDiasMinimoAcaoPrecedente());
					}

					if(Util.compararData(dataEmissao, new Date()) > 0){
						retorno = false;
					}

				}
			}
		}

		return retorno;

	}

	/**
	 * Recupera o tipo de relação do cliente e imóvel para exibição no relatório de ordem de
	 * serviço.
	 * 
	 * @param idOSLayout
	 * @date 26/12/2012
	 * @author Ítalo Almeida
	 */
	public String recuperaRelacaoOSClienteImovel(Integer idOSLayout) throws ControladorException{

		try{
			return repositorioOrdemServico.recuperaRelacaoOSClienteImovel(idOSLayout);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	private ControladorContabilLocal getControladorContabil(){

		ControladorContabilLocalHome localHome = null;
		ControladorContabilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();
			localHome = (ControladorContabilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_CONTABIL_SEJB);
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

	/**
	 * Checa ordens de servico existentes Booleano
	 * 
	 * @author Vicente Zarga
	 * @date 15/05/2013
	 */
	private boolean verificarOrdemServicoExistente(Integer idImovel, Integer idServicoTipo){

		boolean resposta = false;
		OrdemServico ordemServico = null;

		FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID_IMOVEL, idImovel));
		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.SERVICO_TIPO_ID, idServicoTipo));
		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.SITUACAO, ConstantesSistema.SIM.toString()));

		try{
			ordemServico = (OrdemServico) Util.retonarObjetoDeColecao(this.getControladorUtil().pesquisar(filtroOrdemServico,
							OrdemServico.class.getName()));

			if(ordemServico != null){
				resposta = true;
			}

		}catch(ControladorException e){

			throw new SistemaException(e);
		}

		return resposta;

	}

	public Collection<OrdemServicoJSONHelper> pesquisarOrdemServicoWebService(FiltrarOrdemServicoHelper filtro) throws ControladorException{

		Collection<OrdemServicoJSONHelper> toReturn = new ArrayList<OrdemServicoJSONHelper>();
		try{

			Collection<Object[]> objects = repositorioOrdemServico.filtrarOrdemServicoWebService(filtro);

			for(Object[] item : objects){
				OrdemServicoJSONHelper novo = new OrdemServicoJSONHelper();
				Imovel imovel = new Imovel();
				Integer numeroOS = (Integer) item[0];

				novo.setNumeroOs(numeroOS);
				novo.setCodigoPrioridadeExecucao((Integer) item[1]);
				novo.setCodigoServicoOS((Integer) item[2]);
				novo.setCodigoSituacaoOS((Integer) item[3]);

				novo.setCoordenadaLeste(String.valueOf((BigDecimal) item[4]));
				novo.setCoordenadaNorte(String.valueOf((BigDecimal) item[5]));

				novo.setDescricaoServicoTipo((String) item[6]);

				UnidadeOrganizacional unidadeAtualRA = obterUnidadeAtualOrdemServico(numeroOS);

				novo.setCodigoUnidadeNegocio(unidadeAtualRA.getId());
				novo.setCodigoUnidadeExecutora(unidadeAtualRA.getId());

				ObterDescricaoSituacaoOSHelper descricaoSituacaoOS = obterDescricaoSituacaoOS(numeroOS);
				novo.setDescricaoSituacaoOs(descricaoSituacaoOS.getDescricaoSituacao());

				Integer idLocalidade = (Integer) item[7];
				Integer codigoSetorComercial = (Integer) item[8];
				Integer numeroQuadra = (Integer) item[9];
				Short lote = (Short) item[10];
				Short subLote = (Short) item[11];

				if(idLocalidade != null && codigoSetorComercial != null && numeroQuadra != null && lote != null && subLote != null){
					imovel.setInscricaoFormatada(idLocalidade, codigoSetorComercial, numeroQuadra, lote, subLote);
					novo.setInscricao(imovel.getInscricaoFormatada());
				}

				toReturn.add(novo);
			}

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return toReturn;

	}

	public OrdemServicoDetalhesJSONHelper pesquisarOrdemServicoDetalhesWebService(FiltrarOrdemServicoHelper filtro)
					throws ControladorException{

		OrdemServicoDetalhesJSONHelper retorno = null;
		try{

			Object[] item = repositorioOrdemServico.filtrarOrdemServicoDetalhesWebService(filtro);

			if(item != null){
				retorno = new OrdemServicoDetalhesJSONHelper();
				Integer numeroOS = (Integer) item[0];
				Integer numeroRA = (Integer) item[1];

				retorno.setNumeroOs(numeroOS);
				retorno.setNumeroRa(numeroRA);
				retorno.setBairro((String) item[2]);
				retorno.setCoordenadaLeste(String.valueOf((BigDecimal) item[3]));
				retorno.setCoordenadaNorte(String.valueOf((BigDecimal) item[4]));
				retorno.setDataExecucao((Date) item[5]);
				retorno.setDataTramite((Date) item[6]);
				retorno.setDescricaoServicoTipo((String) item[7]);
				retorno.setCodigoServicoOS((Integer) item[8]);

				retorno.setCodigoSituacaoOS((Integer) item[9]);
				retorno.setDocumentoCobranca((Integer) item[10]);

				StringBuilder telefoneContato = new StringBuilder("");
				Short ddd = (Short) item[11];
				if(Util.isNaoNuloBrancoZero(ddd)){
					telefoneContato.append("(" + ddd + ") ");
				}
				String numeroFone = (String) item[12];
				if(Util.isNaoNuloBrancoZero(numeroFone)){
					telefoneContato.append(numeroFone);
				}
				retorno.setTelefoneContato(telefoneContato.toString());

				Integer idLocalidade = (Integer) item[13];
				Integer codigoSetorComercial = (Integer) item[14];
				Integer numeroQuadra = (Integer) item[15];
				Short lote = (Short) item[16];
				Short subLote = (Short) item[17];
				Imovel imovel = new Imovel();
				if(lote != null && subLote != null){
					imovel.setInscricaoFormatada(idLocalidade, codigoSetorComercial, numeroQuadra, lote, subLote);
					retorno.setInscricao(imovel.getInscricaoFormatada());
				}
				retorno.setNomeLocalidade((String) item[18]);
				Integer matricula = (Integer) item[19];
				if(Util.isNaoNuloBrancoZero(matricula)){
					retorno.setMatricula(String.valueOf(matricula));
				}
				retorno.setNomeMunicipio((String) item[20]);
				retorno.setNomeCliente((String) item[21]);
				retorno.setObservacao((String) item[22]);
				retorno.setPontoReferencia((String) item[23]);
				retorno.setMotivoEncerramento((String) item[24]);
				retorno.setParecer((String) item[25]);

				UnidadeOrganizacional unidadeAtualRA = obterUnidadeAtualOrdemServico(numeroOS);
				retorno.setUnidadeNegocio(unidadeAtualRA.getDescricao());
				retorno.setCodigoUnidadeExecutora(unidadeAtualRA.getId());

				ObterDescricaoSituacaoOSHelper descricaoSituacaoOS = obterDescricaoSituacaoOS(numeroOS);
				retorno.setDescricaoSituacaoOs(descricaoSituacaoOS.getDescricaoSituacao());

				if(numeroRA != null){
					String[] logradouro = getControladorRegistroAtendimento().obterEnderecoeNumeroOcorrenciaRA(numeroRA);
					retorno.setLogradouro(logradouro[0]);
					retorno.setNumeroLogradouro(logradouro[1]);

					// [UC0420] Obter Descrição da situação da RA
					ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRAHelper = getControladorRegistroAtendimento()
									.obterDescricaoSituacaoRA(numeroRA);
					retorno.setDescricaoSituacaoRa(obterDescricaoSituacaoRAHelper.getDescricaoSituacao());
				}
			}
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;

	}

	public Collection filtrarComandoOSSeletiva(OrdemServicoSeletivaComandoHelper filtro) throws ControladorException{

		Collection retorno;
		try{
			retorno = repositorioOrdemServico.filtrarComandoOSSeletiva(filtro);

		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;

	}

	public Collection filtrarDadosRelatorioComandoOSSeletiva(OrdemServicoSeletivaComandoHelper filtro) throws ControladorException{

		Collection retorno;
		try{
			retorno = repositorioOrdemServico.filtrarDadosRelatorioComandoOSSeletiva(filtro);

		}catch(ErroRepositorioException ex){

			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;

	}

	/**
	 * [UC0454] Obter Descrição da situação da OS
	 * Verificar a situação(ORSE_CDSITUACAO) da ordem de serviço
	 * 
	 * @author Vicente Zarga
	 * @date 25/11/2013
	 * @param idOS
	 * @throws ControladorException
	 */
	public Short verificaSituacaoOS(Integer idOrdemServico) throws ControladorException{

		Short retorno = null;

		try{
			retorno = repositorioOrdemServico.verificaSituacaoOS(idOrdemServico);
		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * Obter dados para gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 */
	public Collection pesquisaRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ControladorException{

		Collection colecaoRetorno = null;
		try{

			colecaoRetorno = this.repositorioOrdemServico.pesquisaRelatorioOrdemServicoEncerradaDentroForaPrazo(origemServico,
							idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idLocalidade);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0XXX] Gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * Obter dados para gerar Relatório de Ordem de Serviço Encerradas Dentro e Fora do Prazo
	 * 
	 * @author Victon Santos
	 * @date 27/12/2013
	 */
	public Integer pesquisaTotalRegistrosRelatorioOrdemServicoEncerradaDentroForaPrazo(String origemServico, String[] idsServicosTipos,
					String idUnidadeAtendimento, String idUnidadeAtual, String idUnidadeEncerramento, Date periodoAtendimentoInicial,
					Date periodoAtendimentoFinal, Date periodoEncerramentoInicial, Date periodoEncerramentoFinal, String idLocalidade)
					throws ControladorException{

		Integer retorno = null;
		try{
			retorno = this.repositorioOrdemServico.pesquisaTotalRegistrosRelatorioOrdemServicoEncerradaDentroForaPrazo(origemServico,
							idsServicosTipos,
							idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial,
							periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal, idLocalidade);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * @param imovelEmissaoOrdensSeletivaHelper
	 * @param usuarioLogado
	 */

	public Object[] gerarRelatorioOrdemSeletiva(ImovelEmissaoOrdensSeletivaHelper imovelEmissaoOrdensSeletivaHelper, Usuario usuarioLogado){

		Object[] retorno = new Object[2];

		if(imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaInicial() != null
						&& !imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaInicial().equals("")
						&& imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaFinal() != null
						&& !imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaFinal().equals("")){

			String areaInicial = imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaInicial();
			String areaFinal = imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaFinal();

			areaInicial = areaInicial.replace(".", "");
			areaInicial = areaInicial.replace(",", ".");
			areaFinal = areaFinal.replace(".", "");
			areaFinal = areaFinal.replace(",", ".");

			imovelEmissaoOrdensSeletivaHelper.setIntervaloAreaConstruidaInicial(areaInicial);
			imovelEmissaoOrdensSeletivaHelper.setIntervaloAreaConstruidaFinal(areaFinal);
		}

		// Valor do Débito
		String valorTotalDebitoVencido = imovelEmissaoOrdensSeletivaHelper.getValorTotalDebitoVencido();

		if(!Util.isVazioOuBranco(valorTotalDebitoVencido)){
			valorTotalDebitoVencido = valorTotalDebitoVencido.replace(".", "");
			valorTotalDebitoVencido = valorTotalDebitoVencido.replace(",", ".");
		}

		if(imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaPredefinida() != null){

			if(imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaPredefinida().trim()
							.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				imovelEmissaoOrdensSeletivaHelper.setIntervaloAreaConstruidaPredefinida(null);
}
			if(imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaPredefinida() != null){
				// Verifica se o usuario informou o Intervalo de Area Construida Pre definida
				FiltroAreaConstruidaFaixa filtroAreaConstruidaFaixa = new FiltroAreaConstruidaFaixa();

				filtroAreaConstruidaFaixa.adicionarParametro(new ParametroSimples(FiltroAreaConstruidaFaixa.CODIGO,
								imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaPredefinida()));

				Collection<AreaConstruidaFaixa> colecaoAreaConstruida;
				try{
					colecaoAreaConstruida = getControladorUtil().pesquisar(filtroAreaConstruidaFaixa, AreaConstruidaFaixa.class.getName());

					if(colecaoAreaConstruida != null && !colecaoAreaConstruida.isEmpty()){
						Iterator<AreaConstruidaFaixa> iColecaoAreaConstruida = colecaoAreaConstruida.iterator();
						AreaConstruidaFaixa faixa = iColecaoAreaConstruida.next();

						// Seta os Intervalos
						imovelEmissaoOrdensSeletivaHelper.setIntervaloAreaConstruidaInicial(faixa.getVolumeMenorFaixa().toString());
						imovelEmissaoOrdensSeletivaHelper.setIntervaloAreaConstruidaFinal(faixa.getVolumeMaiorFaixa().toString());
					}

				}catch(ControladorException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			}
		}

		if(imovelEmissaoOrdensSeletivaHelper.getImovelCondominio() == null){
			imovelEmissaoOrdensSeletivaHelper.setImovelCondominio("2");
		}

		if(imovelEmissaoOrdensSeletivaHelper.getTipoMedicao() != null
						&& imovelEmissaoOrdensSeletivaHelper.getTipoMedicao().trim()
										.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			imovelEmissaoOrdensSeletivaHelper.setTipoMedicao(null);
		}else{
			if(imovelEmissaoOrdensSeletivaHelper.getTipoMedicao() == null){
				imovelEmissaoOrdensSeletivaHelper.setTipoMedicao(String.valueOf(MedicaoTipo.LIGACAO_AGUA));
			}
		}

		// Grupo de Faturamento
		if(imovelEmissaoOrdensSeletivaHelper.getFaturamentoGrupo() != null
						&& imovelEmissaoOrdensSeletivaHelper.getFaturamentoGrupo().trim()
										.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			imovelEmissaoOrdensSeletivaHelper.setFaturamentoGrupo(null);
		}

		// Regional
		if(imovelEmissaoOrdensSeletivaHelper.getGerenciaRegional() != null
						&& imovelEmissaoOrdensSeletivaHelper.getGerenciaRegional().trim()
										.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			imovelEmissaoOrdensSeletivaHelper.setGerenciaRegional(null);
		}

		// Cortado no Período - Inicial
		String intervaloDataCorteInicial = imovelEmissaoOrdensSeletivaHelper.getIntervaloDataCorteInicial();

		if(!Util.isVazioOuBranco(intervaloDataCorteInicial)){
			intervaloDataCorteInicial = Util.formatarMesAnoParaAnoMes(intervaloDataCorteInicial);
		}

		// Cortado no Período - Final
		String intervaloDataCorteFinal = imovelEmissaoOrdensSeletivaHelper.getIntervaloDataCorteFinal();

		if(!Util.isVazioOuBranco(intervaloDataCorteFinal)){
			intervaloDataCorteFinal = Util.formatarMesAnoParaAnoMes(intervaloDataCorteFinal);
		}

		// Cortado no Período - Inicial
		String dataCorteInicial = imovelEmissaoOrdensSeletivaHelper.getDataCorteInicial();
		// Cortado no Período - Final
		String dataCorteFinal = imovelEmissaoOrdensSeletivaHelper.getDataCorteFinal();

		// Verifica se o usuario selecionou simulação
		if(!imovelEmissaoOrdensSeletivaHelper.getSimulacao().equals("1")){
			RelatorioEmitirOrdemServicoSeletiva relatorioEmitirOrdemServicoSeletiva = new RelatorioEmitirOrdemServicoSeletiva(usuarioLogado);

			relatorioEmitirOrdemServicoSeletiva.addParametro("servicoTipo", imovelEmissaoOrdensSeletivaHelper.getServicoTipo());

			relatorioEmitirOrdemServicoSeletiva.addParametro("servicoTipoDescricao",
							imovelEmissaoOrdensSeletivaHelper.getServicoTipoDescricao());

			relatorioEmitirOrdemServicoSeletiva.addParametro("simulacao", imovelEmissaoOrdensSeletivaHelper.getSimulacao());

			relatorioEmitirOrdemServicoSeletiva.addParametro("firma", imovelEmissaoOrdensSeletivaHelper.getFirma());

			relatorioEmitirOrdemServicoSeletiva.addParametro("nomeFirma", imovelEmissaoOrdensSeletivaHelper.getNomeFirma());

			relatorioEmitirOrdemServicoSeletiva.addParametro("quantidadeMaxima", imovelEmissaoOrdensSeletivaHelper.getQuantidadeMaxima());

			// Valida o Elo e recupera a Descricao
			if(imovelEmissaoOrdensSeletivaHelper.getElo() == null || imovelEmissaoOrdensSeletivaHelper.getElo().equals("")){
				relatorioEmitirOrdemServicoSeletiva.addParametro("elo", "");
				relatorioEmitirOrdemServicoSeletiva.addParametro("nomeElo", "");
			}else{
				Localidade localidadeElo = pesquisaElo(Integer.decode(imovelEmissaoOrdensSeletivaHelper.getElo()));

				if(localidadeElo == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Elo");
				}
				relatorioEmitirOrdemServicoSeletiva.addParametro("elo", localidadeElo.getLocalidade().getId().toString());
				relatorioEmitirOrdemServicoSeletiva.addParametro("nomeElo", localidadeElo.getLocalidade().getDescricao());

			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("faturamentoGrupo", imovelEmissaoOrdensSeletivaHelper.getFaturamentoGrupo());

			relatorioEmitirOrdemServicoSeletiva.addParametro("gerenciaRegional", imovelEmissaoOrdensSeletivaHelper.getGerenciaRegional());

			relatorioEmitirOrdemServicoSeletiva.addParametro("localidade", imovelEmissaoOrdensSeletivaHelper.getLocalidade());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getBairro())
							&& !imovelEmissaoOrdensSeletivaHelper.getBairro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("bairro", imovelEmissaoOrdensSeletivaHelper.getBairro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLogradouro())
							&& !imovelEmissaoOrdensSeletivaHelper.getLogradouro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("logradouro", imovelEmissaoOrdensSeletivaHelper.getLogradouro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getSetor())
							&& !imovelEmissaoOrdensSeletivaHelper.getSetor()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("setor", imovelEmissaoOrdensSeletivaHelper.getSetor());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getQuadra())
							&& !imovelEmissaoOrdensSeletivaHelper.getQuadra()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("quadra", imovelEmissaoOrdensSeletivaHelper.getQuadra());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getRota())
							&& !imovelEmissaoOrdensSeletivaHelper.getRota()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("rota", imovelEmissaoOrdensSeletivaHelper.getRota());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLote())
							&& !imovelEmissaoOrdensSeletivaHelper.getLote()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("lote", imovelEmissaoOrdensSeletivaHelper.getLote());
			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("inscricaoTipo", imovelEmissaoOrdensSeletivaHelper.getInscricaoTipo());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getPerfilImovel())
							&& !imovelEmissaoOrdensSeletivaHelper.getPerfilImovel()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("perfilImovel", imovelEmissaoOrdensSeletivaHelper.getPerfilImovel());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getCategoria())
							&& !imovelEmissaoOrdensSeletivaHelper.getCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("categoria", imovelEmissaoOrdensSeletivaHelper.getCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getSubCategoria())
							&& !imovelEmissaoOrdensSeletivaHelper.getSubCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("subCategoria", imovelEmissaoOrdensSeletivaHelper.getSubCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLigacaoAguaSituacao())
							&& !imovelEmissaoOrdensSeletivaHelper.getLigacaoAguaSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("ligacaoAguaSituacao",
								imovelEmissaoOrdensSeletivaHelper.getLigacaoAguaSituacao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLigacaoEsgotoSituacao())
							&& !imovelEmissaoOrdensSeletivaHelper.getLigacaoEsgotoSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("ligacaoEsgotoSituacao",
								imovelEmissaoOrdensSeletivaHelper.getLigacaoEsgotoSituacao());
			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloDataCorteInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloDataCorteInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloDataCorteFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloDataCorteFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("dataCorteInicial", imovelEmissaoOrdensSeletivaHelper.getDataCorteInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("dataCorteFinal", imovelEmissaoOrdensSeletivaHelper.getDataCorteFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeEconomiasInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeEconomiasInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeEconomiasFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeEconomiasFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroPontosUtilizacaoInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroPontosUtilizacaoInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroPontosUtilizacaoFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroPontosUtilizacaoFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeDocumentosInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeDocumentosInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeDocumentosFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeDocumentosFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroMoradoresInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroMoradoresInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroMoradoresFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroMoradoresFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloAreaConstruidaInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloAreaConstruidaFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloAreaConstruidaPredefinida",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaPredefinida());

			relatorioEmitirOrdemServicoSeletiva.addParametro("imovelCondominio", imovelEmissaoOrdensSeletivaHelper.getImovelCondominio());

			relatorioEmitirOrdemServicoSeletiva.addParametro("consumoPorEconomia",
							imovelEmissaoOrdensSeletivaHelper.getConsumoPorEconomia());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroConsumoMesInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroConsumoMesInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroConsumoMesFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroConsumoMesFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("colecaoConsumoMedioImovel",
							imovelEmissaoOrdensSeletivaHelper.getColecaoConsumoMedioImovel());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeContasVencidasInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeContasVencidasInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeContasVencidasFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeContasVencidasFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("valorTotalDebitoVencido",
							imovelEmissaoOrdensSeletivaHelper.getValorTotalDebitoVencido());

			relatorioEmitirOrdemServicoSeletiva.addParametro("tipoMedicao", imovelEmissaoOrdensSeletivaHelper.getTipoMedicao());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getMarca())
							&& !imovelEmissaoOrdensSeletivaHelper.getMarca()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("marca", imovelEmissaoOrdensSeletivaHelper.getMarca());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getHidrometroClasseMetrologica())
							&& !imovelEmissaoOrdensSeletivaHelper.getHidrometroClasseMetrologica()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("hidrometroClasseMetrologica",
								imovelEmissaoOrdensSeletivaHelper.getHidrometroClasseMetrologica());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getHidrometroProtecao())
							&& !imovelEmissaoOrdensSeletivaHelper.getHidrometroProtecao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("hidrometroProtecao",
								imovelEmissaoOrdensSeletivaHelper.getHidrometroProtecao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getHidrometroLocalInstalacao())
							&& !imovelEmissaoOrdensSeletivaHelper.getHidrometroLocalInstalacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("hidrometroLocalInstalacao",
								imovelEmissaoOrdensSeletivaHelper.getHidrometroLocalInstalacao());
			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("anormalidadeHidrometro",
							imovelEmissaoOrdensSeletivaHelper.getAnormalidadeHidrometro());

			relatorioEmitirOrdemServicoSeletiva.addParametro("numeroOcorrenciasConsecutivas",
							imovelEmissaoOrdensSeletivaHelper.getNumeroOcorrenciasConsecutivas());

			relatorioEmitirOrdemServicoSeletiva.addParametro("colecaoDadosDoHidrometro",
							imovelEmissaoOrdensSeletivaHelper.getColecaoDadosDoHidrometro());

			relatorioEmitirOrdemServicoSeletiva.addParametro("colecaoImoveis", imovelEmissaoOrdensSeletivaHelper.getIdsImoveis());


			relatorioEmitirOrdemServicoSeletiva.addParametro("idUsuario", String.valueOf(usuarioLogado.getId()));

			relatorioEmitirOrdemServicoSeletiva.addParametro("idComandoOsSeletiva",
							String.valueOf(imovelEmissaoOrdensSeletivaHelper.getIdComandoOsServicoSeletiva()));

			retorno[0] = relatorioEmitirOrdemServicoSeletiva;

		}else{
			RelatorioEmitirOrdemServicoSeletivaSugestao relatorioEmitirOrdemServicoSeletivaSugestao = new RelatorioEmitirOrdemServicoSeletivaSugestao(
							usuarioLogado);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("servicoTipo", imovelEmissaoOrdensSeletivaHelper.getServicoTipo());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("servicoTipoDescricao",
							imovelEmissaoOrdensSeletivaHelper.getServicoTipoDescricao());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("simulacao", imovelEmissaoOrdensSeletivaHelper.getSimulacao());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("firma", imovelEmissaoOrdensSeletivaHelper.getFirma());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("nomeFirma", imovelEmissaoOrdensSeletivaHelper.getNomeFirma());

			relatorioEmitirOrdemServicoSeletivaSugestao
.addParametro("quantidadeMaxima",
							imovelEmissaoOrdensSeletivaHelper.getQuantidadeMaxima());

			// Valida o Elo e recupera a Descricao
			if(imovelEmissaoOrdensSeletivaHelper.getElo() == null || imovelEmissaoOrdensSeletivaHelper.getElo().equals("")){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("elo", "");
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("nomeElo", "");
			}else{
				Localidade localidadeElo = pesquisaElo(Integer.decode(imovelEmissaoOrdensSeletivaHelper.getElo()));

				if(localidadeElo == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Elo");
				}
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("elo", localidadeElo.getLocalidade().getId().toString());
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("nomeElo", localidadeElo.getLocalidade().getDescricao());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao
.addParametro("faturamentoGrupo",
							imovelEmissaoOrdensSeletivaHelper.getFaturamentoGrupo());

			relatorioEmitirOrdemServicoSeletivaSugestao
.addParametro("gerenciaRegional",
							imovelEmissaoOrdensSeletivaHelper.getGerenciaRegional());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("localidade", imovelEmissaoOrdensSeletivaHelper.getLocalidade());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getBairro())
							&& !imovelEmissaoOrdensSeletivaHelper.getBairro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("bairro", imovelEmissaoOrdensSeletivaHelper.getBairro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLogradouro())
							&& !imovelEmissaoOrdensSeletivaHelper.getLogradouro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("logradouro", imovelEmissaoOrdensSeletivaHelper.getLogradouro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getSetor())
							&& !imovelEmissaoOrdensSeletivaHelper.getSetor()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("setor", imovelEmissaoOrdensSeletivaHelper.getSetor());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getQuadra())
							&& !imovelEmissaoOrdensSeletivaHelper.getQuadra()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("quadra", imovelEmissaoOrdensSeletivaHelper.getQuadra());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getRota())
							&& !imovelEmissaoOrdensSeletivaHelper.getRota()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("rota", imovelEmissaoOrdensSeletivaHelper.getRota());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLote())
							&& !imovelEmissaoOrdensSeletivaHelper.getLote()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("lote", imovelEmissaoOrdensSeletivaHelper.getLote());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("inscricaoTipo", imovelEmissaoOrdensSeletivaHelper.getInscricaoTipo());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getPerfilImovel())
							&& !imovelEmissaoOrdensSeletivaHelper.getPerfilImovel()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("perfilImovel",
								imovelEmissaoOrdensSeletivaHelper.getPerfilImovel());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getCategoria())
							&& !imovelEmissaoOrdensSeletivaHelper.getCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("categoria", imovelEmissaoOrdensSeletivaHelper.getCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getSubCategoria())
							&& !imovelEmissaoOrdensSeletivaHelper.getSubCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("subCategoria",
								imovelEmissaoOrdensSeletivaHelper.getSubCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLigacaoAguaSituacao())
							&& !imovelEmissaoOrdensSeletivaHelper.getLigacaoAguaSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("ligacaoAguaSituacao",
								imovelEmissaoOrdensSeletivaHelper.getLigacaoAguaSituacao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getLigacaoEsgotoSituacao())
							&& !imovelEmissaoOrdensSeletivaHelper.getLigacaoEsgotoSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("ligacaoEsgotoSituacao",
								imovelEmissaoOrdensSeletivaHelper.getLigacaoEsgotoSituacao());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloDataCorteInicial", intervaloDataCorteInicial);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloDataCorteFinal", intervaloDataCorteFinal);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("dataCorteInicial", dataCorteInicial);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("dataCorteFinal", dataCorteFinal);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeEconomiasInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeEconomiasInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeEconomiasFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeEconomiasFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroPontosUtilizacaoInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroPontosUtilizacaoInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroPontosUtilizacaoFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroPontosUtilizacaoFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeDocumentosInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeDocumentosInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeDocumentosFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeDocumentosFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroMoradoresInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroMoradoresInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroMoradoresFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroMoradoresFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloAreaConstruidaInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloAreaConstruidaFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloAreaConstruidaPredefinida",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloAreaConstruidaPredefinida());

			relatorioEmitirOrdemServicoSeletivaSugestao
.addParametro("imovelCondominio",
							imovelEmissaoOrdensSeletivaHelper.getImovelCondominio());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("consumoPorEconomia",
							imovelEmissaoOrdensSeletivaHelper.getConsumoPorEconomia());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroConsumoMesInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroConsumoMesInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroConsumoMesFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloNumeroConsumoMesFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("colecaoConsumoMedioImovel",
							imovelEmissaoOrdensSeletivaHelper.getColecaoConsumoMedioImovel());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeContasVencidasInicial",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeContasVencidasInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeContasVencidasFinal",
							imovelEmissaoOrdensSeletivaHelper.getIntervaloQuantidadeContasVencidasFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("valorTotalDebitoVencido", valorTotalDebitoVencido);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("tipoMedicao", imovelEmissaoOrdensSeletivaHelper.getTipoMedicao());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getMarca())
							&& !imovelEmissaoOrdensSeletivaHelper.getMarca()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("marca", imovelEmissaoOrdensSeletivaHelper.getMarca());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getHidrometroClasseMetrologica())
							&& !imovelEmissaoOrdensSeletivaHelper.getHidrometroClasseMetrologica()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("hidrometroClasseMetrologica",
								imovelEmissaoOrdensSeletivaHelper.getHidrometroClasseMetrologica());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getHidrometroProtecao())
							&& !imovelEmissaoOrdensSeletivaHelper.getHidrometroProtecao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("hidrometroProtecao",
								imovelEmissaoOrdensSeletivaHelper.getHidrometroProtecao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivaHelper.getHidrometroLocalInstalacao())
							&& !imovelEmissaoOrdensSeletivaHelper.getHidrometroLocalInstalacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("hidrometroLocalInstalacao",
								imovelEmissaoOrdensSeletivaHelper.getHidrometroLocalInstalacao());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("anormalidadeHidrometro",
							imovelEmissaoOrdensSeletivaHelper.getAnormalidadeHidrometro());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("numeroOcorrenciasConsecutivas",
							imovelEmissaoOrdensSeletivaHelper.getNumeroOcorrenciasConsecutivas());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("colecaoDadosDoHidrometro",
							imovelEmissaoOrdensSeletivaHelper.getColecaoDadosDoHidrometro());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("colecaoImoveis", imovelEmissaoOrdensSeletivaHelper.getIdsImoveis());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("idUsuario", String.valueOf(usuarioLogado.getId()));

			retorno[1] = relatorioEmitirOrdemServicoSeletivaSugestao;

		}

		return retorno;

	}

	/**
	 * Recupera a coleção Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Coleção de EntuhoMedida
	 */
	public Collection<EntulhoMedida> pesquisarEntulhoMedida() throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarEntulhoMedida();
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Recupera um Entulho Medida.
	 * 
	 * @author Genival Barbosa
	 * @date 20/09/2014
	 * @return Entulho Medida
	 */
	public EntulhoMedida pesquisarEntulhoMedida(Integer idEntulhoMedida) throws ControladorException{

		try{
			return this.repositorioOrdemServico.pesquisarEntulhoMedida(idEntulhoMedida);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisar ordem de serviço.
	 * 
	 * @author Genival Barbosa
	 * @date 30/09/2014
	 * @return Ordem Servico
	 */
	public OrdemServico pesquisarOrdemServicoPrincipal(Integer idOrdemServico) throws ControladorException{

		OrdemServico retorno = null;
		try{
			retorno = repositorioOrdemServico.pesquisarOrdemServicoPrincipal(idOrdemServico);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}
		return retorno;
	}


	/**
	 * @param elo
	 * @return
	 */
	private Localidade pesquisaElo(Integer elo){

		Fachada fachada = Fachada.getInstancia();
		Localidade localidade = null;

		FiltroLocalidade filtro = new FiltroLocalidade();
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, elo));
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());
		Iterator<Localidade> iColecaoLocalidade = colecaoLocalidade.iterator();
		localidade = iColecaoLocalidade.next();

		return localidade;
	}

	/**
	 * Pesquisa a Localidade
	 * 
	 * @param elo
	 * @return
	 */
	private Localidade pesquisaLocalidade(Integer idLocalidade){

		Fachada fachada = Fachada.getInstancia();
		Localidade localidade = null;

		FiltroLocalidade filtro = new FiltroLocalidade();
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());
		Iterator<Localidade> iColecaoLocalidade = colecaoLocalidade.iterator();
		localidade = iColecaoLocalidade.next();

		return localidade;
	}

	/**
	 * Pesquisa o Setor Comercial
	 * 
	 * @param elo
	 * @return
	 */
	private SetorComercial pesquisaSetorComercial(Integer codigoSetorComercial, Integer idLocalidadeInicial, Integer idLocalidadeFinal){

		Fachada fachada = Fachada.getInstancia();
		SetorComercial setorComercial = null;

		FiltroSetorComercial filtro = new FiltroSetorComercial();
		filtro.adicionarParametro(new Intervalo(FiltroSetorComercial.ID_LOCALIDADE, idLocalidadeInicial, idLocalidadeFinal));
		filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));
		filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<SetorComercial> colecaoSetor = fachada.pesquisar(filtro, SetorComercial.class.getName());
		Iterator<SetorComercial> iColecaoSetor = colecaoSetor.iterator();
		setorComercial = iColecaoSetor.next();

		return setorComercial;
	}

	/**
	 * @param numeroQuadra
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param codigoSetorComercialInicial
	 * @param codigoSetorComercialFinal
	 * @return
	 */
	private Quadra pesquisaQuadra(Integer numeroQuadra, Integer idLocalidadeInicial, Integer idLocalidadeFinal,
					Integer codigoSetorComercialInicial, Integer codigoSetorComercialFinal){

		Fachada fachada = Fachada.getInstancia();
		Quadra quadra = null;

		FiltroQuadra filtro = new FiltroQuadra();
		filtro.adicionarParametro(new Intervalo(FiltroQuadra.ID_LOCALIDADE, idLocalidadeInicial, idLocalidadeFinal));
		filtro.adicionarParametro(new Intervalo(FiltroQuadra.CODIGO_SETORCOMERCIAL, codigoSetorComercialInicial, codigoSetorComercialFinal));
		filtro.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, numeroQuadra));
		filtro.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Quadra> colecaoQuadra = fachada.pesquisar(filtro, Quadra.class.getName());
		if(colecaoQuadra == null || colecaoQuadra.isEmpty()){
			throw new ActionServletException("atencao.quadra.inexistente");
		}
		Iterator<Quadra> iColecaoQuadra = colecaoQuadra.iterator();
		quadra = iColecaoQuadra.next();

		return quadra;
	}

	/**
	 * @param idServicoTipo
	 * @return
	 * @throws ControladorException
	 */

	public boolean comparaServicoTipoSubgrupoCorte(String idServicoTipo) throws ControladorException{

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));
		filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroServicoTipo.SERVICO_TIPO_SUB_GRUPO);

		Collection<ServicoTipo> collectionServicoTipo;
		try{
			collectionServicoTipo = getControladorUtil().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
		}catch(ControladorException e){
			throw new ControladorException("erro.sistema", e);
		}

		ServicoTipo servicoTipo = collectionServicoTipo.iterator().next();

		if(servicoTipo.getServicoTipoSubgrupo().getId() != null
						&& (servicoTipo.getServicoTipoSubgrupo().getId().equals(ServicoTipoSubgrupo.CORTE))){
			return true;
		}else{
			return false;
		}
	}

	public BigDecimal recuperarValorHorasTrabalhadasOS(Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper) {
		BigDecimal valorTotal = new BigDecimal(0.0);
		if(colecaoManterDadosAtividadesOrdemServicoHelper!=null) {
			for(ManterDadosAtividadesOrdemServicoHelper helper : colecaoManterDadosAtividadesOrdemServicoHelper) {
				if(helper.getColecaoOSAtividadePeriodoExecucaoHelper()!=null) {
					for(OSAtividadePeriodoExecucaoHelper periodoHelper : helper.getColecaoOSAtividadePeriodoExecucaoHelper()){
						if(periodoHelper.getOsAtividadePeriodoExecucao() != null
										&& periodoHelper.getOsAtividadePeriodoExecucao().getValorAtividadePeriodo() != null){
							valorTotal = valorTotal.add(periodoHelper.getOsAtividadePeriodoExecucao().getValorAtividadePeriodo());
						}
					}
				}
				
			}
			
		}
		return valorTotal;
	}

	public BigDecimal recuperarValorMateriaisOS(
					Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper){

		BigDecimal valorTotal = new BigDecimal(0.0);
		if(colecaoManterDadosAtividadesOrdemServicoHelper != null){
			for(ManterDadosAtividadesOrdemServicoHelper helper : colecaoManterDadosAtividadesOrdemServicoHelper){
				if(helper.getColecaoOsAtividadeMaterialExecucao() != null){
					for(OsAtividadeMaterialExecucao materialHelper : helper.getColecaoOsAtividadeMaterialExecucao()){
						if(materialHelper.getValorMaterial() != null){
							valorTotal = valorTotal.add(materialHelper.getValorMaterial());
						}
					}
				}

			}

		}
		return valorTotal;
	}

	public OrdemServicoProgramacao pesquisarOSProgramacaoAtivaPorOS(Integer idOS) throws ControladorException{

		try{
		return repositorioOrdemServico.pesquisarOSProgramacaoAtivaPorOS(idOS);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	public Integer recuperaQuantidadeDiasUnidade(Integer idOS, Short permiteTramiteIndependente) throws ControladorException{

		try{
			return repositorioOrdemServico.recuperaQuantidadeDiasUnidade(idOS, permiteTramiteIndependente);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}
}

