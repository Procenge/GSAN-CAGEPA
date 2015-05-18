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
 * Ivan Sérgio da Silva Júnior
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
 * Thiago Silva Toscano de Brito
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

package gcom.spcserasa;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.batch.*;
import gcom.cadastro.ControladorCadastroLocal;
import gcom.cadastro.ControladorCadastroLocalHome;
import gcom.cadastro.CpfTipo;
import gcom.cadastro.EnvioEmail;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.endereco.*;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.*;
import gcom.cobranca.bean.*;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.conta.ContaHistorico;
import gcom.gui.cobranca.spcserasa.DeterminarConfirmacaoNegativacaoHelper;
import gcom.gui.cobranca.spcserasa.RelatorioAcompanhamentoClientesNegativadosSomatorioDadosParcelamentoHelper;
import gcom.gui.cobranca.spcserasa.RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper;
import gcom.relatorio.spcserasa.RelatorioExclusaoNegativacaoSpcSerasa;
import gcom.relatorio.spcserasa.RelatorioNegativacaoSpcSerasa;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.spcserasa.bean.DadosNegativacaoPorImovelHelper;
import gcom.spcserasa.bean.InserirComandoNegativacaoPorCriterioHelper;
import gcom.spcserasa.bean.NegativadorMovimentoHelper;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.email.ErroEmailException;
import gcom.util.email.ServicosEmail;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;
import gcom.util.parametrizacao.spcserasa.ExecutorParametrosSpcSerasa;
import gcom.util.parametrizacao.spcserasa.ParametroSpcSerasa;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.zip.ZipOutputStream;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Marcio Roberto
 * @created 25 de Outubro de 2007
 * @version 1.0
 */

public class ControladorSpcSerasaSEJB
				implements SessionBean {

	private static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	private IRepositorioSpcSerasa repositorioSpcSerasa = null;

	private IRepositorioUtil repositorioUtil = null;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioSpcSerasa = RepositorioSpcSerasaHBM.getInstancia();
		repositorioUtil = RepositorioUtilHBM.getInstancia();
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

	/**
	 * Retorna a interface remota de ControladorImovel
	 * 
	 * @return A interface remota do controlador de parï¿½metro
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
	 * Retorna o valor de ControladorBatch
	 * 
	 * @return O valor de ControladorBatch
	 */

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
	 * Retorna o valor de ControladorEndereco
	 * 
	 * @return O valor de ControladorEndereco
	 */

	private ControladorEnderecoLocal getControladorEndereco(){

		ControladorEnderecoLocalHome localHome = null;
		ControladorEnderecoLocal local = null;

		// pega a instância do ServiceLocator.

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
	 * Retorna a interface remota de ControladorImovel
	 * 
	 * @return A interface remota do controlador de parï¿½metro
	 */
	private ControladorCadastroLocal getControladorCadastro(){

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
	 * Author: Rafael Santos Data: 04/01/2006
	 * Retorna o valor do Controlador de Cobranca
	 * 
	 * @return O valor de controladorCobrancaLocal
	 */
	private ControladorCobrancaLocal getControladorCobranca(){

		ControladorCobrancaLocalHome localHome = null;
		ControladorCobrancaLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorCobrancaLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
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
	 * Método que gera Movimento de Inclusão de Negativação
	 * [UC0671] - Gerar Movimento da Negativação
	 * 
	 * @author Marcio Roberto
	 * @date 30/10/2007
	 */
	public void gerarMovimentoInclusaoNegativacao(int idNegativador, String identificacaoCI, int idUsuarioResponsavel,
					Collection<DadosNegativacaoPorImovelHelper> dadosImoveis) throws ControladorException{

		Integer primeiraVez = 0;
		int quantidadeRegistros = 0;
		DadosNegativacaoPorImovelHelper listaDadosImovel;

		try{
			// Inseri comando de Negativação
			Integer idNegativadorComando = 31;

			// obtem dados do negativador Criterio [SB0002] 1.
			NegativacaoCriterio negCriterio = (NegativacaoCriterio) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa
							.getNegativacaoCriterio(idNegativadorComando));

			// para cada imovel da lista de imoveis
			Iterator itdadosImoveis = dadosImoveis.iterator();
			while(itdadosImoveis.hasNext()){
				listaDadosImovel = (DadosNegativacaoPorImovelHelper) itdadosImoveis.next();

				// [SB0001] 4.1 - Obtem dados do Imovel.
				Imovel imovelNegativado = (Imovel) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDadosImoveis(listaDadosImovel
								.getIdImovel()));

				// para cada imovel da lista de imoveis [SB0003] 2.
				// [SB0005] - Gerar Negativacao Imovel
				// imoveisNegCliente = Coleção de Imoveis.
				quantidadeRegistros += 1;

				// [SB0007] - Gerar Registro da Negativação marcio
				this.geraRegistroNegativacao(imovelNegativado, negCriterio.getNegativacaoComando().getNegativador().getId(),
								idUsuarioResponsavel, negCriterio.getNegativacaoComando().getId(), listaDadosImovel, quantidadeRegistros,
								primeiraVez);
				// seta indicador de que não é a primeira vez que esta entrando.
				primeiraVez = 1;

			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new EJBException(ex);
		}
	}

	private Date obterMaiorMenorVencimento(List contas, List guiasPagamento, int tipo) throws ControladorException{

		Date dataRetorno = null;
		try{
			if(tipo == 1){ // Tipo = 1 Maior Data
				// varre lista de contas
				for(int i = 0; i < contas.size(); i++){
					ContaValoresHelper contaValoresHelper = (ContaValoresHelper) contas.get(i);
					Conta conta = contaValoresHelper.getConta();

					if(dataRetorno == null){
						dataRetorno = conta.getDataVencimentoConta();
					}

					if((conta.getDataVencimentoConta()).after(dataRetorno)){
						dataRetorno = conta.getDataVencimentoConta();
					}

				}
			}else if(tipo == 2){ // Tipo = 2 Menor Data
				// varre lista de contas
				for(int i = 0; i < contas.size(); i++){
					ContaValoresHelper contaValoresHelper = (ContaValoresHelper) contas.get(i);
					Conta conta = contaValoresHelper.getConta();

					if(dataRetorno == null){
						dataRetorno = conta.getDataVencimentoConta();
					}

					if((conta.getDataVencimentoConta()).before(dataRetorno)){
						dataRetorno = conta.getDataVencimentoConta();
					}

				}
			}
		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
		return dataRetorno;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 29/10/2007
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ControladorException
	 */

	public Integer pesquisarComandoNegativacao(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ControladorException{

		Integer retorno = 0;

		try{

			retorno = repositorioSpcSerasa.pesquisarComandoNegativacao(comandoNegativacaoHelper);

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 29/10/2007
	 * @param comandoNegativacaoHelper
	 * @param numeroPagina
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarComandoNegativacaoParaPaginacao(ComandoNegativacaoHelper comandoNegativacaoHelper, Integer numeroPagina)
					throws ControladorException{

		Collection pesquisaComandoNegativacao = new ArrayList();
		Collection<ComandoNegativacaoHelper> retorno = new ArrayList<ComandoNegativacaoHelper>();

		try{

			pesquisaComandoNegativacao = repositorioSpcSerasa.pesquisarComandoNegativacaoParaPaginacao(comandoNegativacaoHelper,
							numeroPagina);

			if(pesquisaComandoNegativacao != null && !pesquisaComandoNegativacao.isEmpty()){

				Iterator comandoNegativacaoIterator = pesquisaComandoNegativacao.iterator();

				while(comandoNegativacaoIterator.hasNext()){
					ComandoNegativacaoHelper comandoNegativacao = new ComandoNegativacaoHelper();
					Object[] dadosComandoNegativacao = (Object[]) comandoNegativacaoIterator.next();

					if(dadosComandoNegativacao[0] != null){
						comandoNegativacao.setIdComandoNegativacao((Integer) dadosComandoNegativacao[0]);
					}
					if(dadosComandoNegativacao[1] != null){
						comandoNegativacao.setTituloComando((String) dadosComandoNegativacao[1]);
					}
					if(dadosComandoNegativacao[2] != null){
						comandoNegativacao.setIndicadorComandoSimulado((Short) dadosComandoNegativacao[2]);
					}
					if(dadosComandoNegativacao[3] != null){
						comandoNegativacao.setGeracaoComandoInicio((Date) dadosComandoNegativacao[3]);
					}
					if(dadosComandoNegativacao[4] != null){
						comandoNegativacao.setExecucaoComandoInicio((Date) dadosComandoNegativacao[4]);
					}
					if(dadosComandoNegativacao[5] != null){
						comandoNegativacao.setQuantidadeInclusoes((Integer) dadosComandoNegativacao[5]);
					}
					if(dadosComandoNegativacao[6] != null){
						comandoNegativacao.setNomeUsuarioResponsavel((String) dadosComandoNegativacao[6]);
					}
					retorno.add(comandoNegativacao);
				}
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 29/10/2007
	 * @param idComandoNegativacao
	 * @return Collection
	 * @throws ControladorException
	 */
	public Integer pesquisarDadosInclusoesComandoNegativacao(Integer idComandoNegativacao) throws ControladorException{

		Integer retorno = 0;
		try{

			retorno = repositorioSpcSerasa.pesquisarDadosInclusoesComandoNegativacao(idComandoNegativacao);

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 29/10/2007
	 * @param idComandoNegativacao
	 * @param numeroPagina
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarDadosInclusoesComandoNegativacaoParaPaginacao(Integer idComandoNegativacao, Integer numeroPagina)
					throws ControladorException{

		Collection pesquisaDadosInclusoesComandoNegativacao = new ArrayList();
		Collection<DadosInclusoesComandoNegativacaoHelper> retorno = new ArrayList<DadosInclusoesComandoNegativacaoHelper>();

		try{

			pesquisaDadosInclusoesComandoNegativacao = repositorioSpcSerasa.pesquisarDadosInclusoesComandoNegativacaoParaPaginacao(
							idComandoNegativacao, numeroPagina);

			if(pesquisaDadosInclusoesComandoNegativacao != null && !pesquisaDadosInclusoesComandoNegativacao.isEmpty()){

				Iterator dadosInclusoesComandoNegativacaoIterator = pesquisaDadosInclusoesComandoNegativacao.iterator();

				while(dadosInclusoesComandoNegativacaoIterator.hasNext()){

					DadosInclusoesComandoNegativacaoHelper dadosInclusoesComandoNegativacaoHelper = new DadosInclusoesComandoNegativacaoHelper();
					Object[] dadosInclusoesComandoNegativacao = (Object[]) dadosInclusoesComandoNegativacaoIterator.next();

					if(dadosInclusoesComandoNegativacao[0] != null){
						dadosInclusoesComandoNegativacaoHelper.setNomeCliente((String) dadosInclusoesComandoNegativacao[0]);
					}
					if(dadosInclusoesComandoNegativacao[1] != null){
						dadosInclusoesComandoNegativacaoHelper.setQuantidadeInclusoes((Integer) dadosInclusoesComandoNegativacao[1]);
					}
					if(dadosInclusoesComandoNegativacao[2] != null){
						dadosInclusoesComandoNegativacaoHelper.setValorTotalDebito((BigDecimal) dadosInclusoesComandoNegativacao[2]);
					}
					if(dadosInclusoesComandoNegativacao[3] != null){
						dadosInclusoesComandoNegativacaoHelper.setQuantidadeItensIncluidos((Integer) dadosInclusoesComandoNegativacao[3]);
					}
					if(dadosInclusoesComandoNegativacao[4] != null){
						dadosInclusoesComandoNegativacaoHelper.setIdImovel((Integer) dadosInclusoesComandoNegativacao[4]);
					}
					if(dadosInclusoesComandoNegativacao[5] != null){
						dadosInclusoesComandoNegativacaoHelper.setNumeroCpf((String) dadosInclusoesComandoNegativacao[5]);
					}
					if(dadosInclusoesComandoNegativacao[6] != null){
						dadosInclusoesComandoNegativacaoHelper.setNumeroCnpj((String) dadosInclusoesComandoNegativacao[6]);
					}
					if(dadosInclusoesComandoNegativacao[7] != null){
						dadosInclusoesComandoNegativacaoHelper.setValorDebito((BigDecimal) dadosInclusoesComandoNegativacao[7]);
					}
					if(dadosInclusoesComandoNegativacao[8] != null){
						dadosInclusoesComandoNegativacaoHelper.setDescricaoCobrancaSituacao((String) dadosInclusoesComandoNegativacao[8]);
					}
					if(dadosInclusoesComandoNegativacao[9] != null){
						dadosInclusoesComandoNegativacaoHelper.setDataSituacaoDebito((Date) dadosInclusoesComandoNegativacao[9]);
					}
					if(dadosInclusoesComandoNegativacao[10] != null){
						dadosInclusoesComandoNegativacaoHelper.setIndicadorAceito((Short) dadosInclusoesComandoNegativacao[10]);
					}
					if(dadosInclusoesComandoNegativacao[11] != null){
						dadosInclusoesComandoNegativacaoHelper.setIndicadorCorrecao((Short) dadosInclusoesComandoNegativacao[11]);
					}
					if(dadosInclusoesComandoNegativacao[12] != null){
						dadosInclusoesComandoNegativacaoHelper.setCodigoExclusaoTipo((Integer) dadosInclusoesComandoNegativacao[12]);
					}
					if(dadosInclusoesComandoNegativacao[13] != null){
						dadosInclusoesComandoNegativacaoHelper.setNomeUsuario((String) dadosInclusoesComandoNegativacao[13]);
					}

					retorno.add(dadosInclusoesComandoNegativacaoHelper);
				}
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * [UC 0653] Pesquisar Comando Negativação
	 * 
	 * @author Kássia Albuquerque
	 * @date 09/11/2007
	 * @param idComandoNegativacao
	 * @return ParametrosComandoNegativacaoHelper
	 * @throws ControladorException
	 */
	public ParametrosComandoNegativacaoHelper pesquisarParametrosComandoNegativacao(Integer idComandoNegativacao)
					throws ControladorException{

		ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper = null;
		Object[] pesquisaParametrosComandoNegativacao = null;
		Collection pesquisaTitularidadeNegativacao = null;
		Collection pesquisaGrupoCobranca = null;
		Collection pesquisaGerenciaRegional = null;
		Collection pesquisaUnidadeNegocio = null;
		Collection pesquisaEloPolo = null;
		Collection pesquisaSubcategoria = null;
		Collection pesquisaPerfilImovel = null;
		Collection pesquisaTipoCliente = null;

		try{

			pesquisaParametrosComandoNegativacao = repositorioSpcSerasa.pesquisarParametrosComandoNegativacao(idComandoNegativacao);
			pesquisaTitularidadeNegativacao = repositorioSpcSerasa.pesquisarTitularidadeCpfCnpjNegativacao(idComandoNegativacao);
			pesquisaGrupoCobranca = repositorioSpcSerasa.pesquisarGrupoCobranca(idComandoNegativacao);
			pesquisaGerenciaRegional = repositorioSpcSerasa.pesquisarGerenciaRegional(idComandoNegativacao);
			pesquisaUnidadeNegocio = repositorioSpcSerasa.pesquisarUnidadeNegocio(idComandoNegativacao);
			pesquisaEloPolo = repositorioSpcSerasa.pesquisarEloPolo(idComandoNegativacao);
			pesquisaSubcategoria = repositorioSpcSerasa.pesquisarSubcategoria(idComandoNegativacao);
			pesquisaPerfilImovel = repositorioSpcSerasa.pesquisarPerfilImovel(idComandoNegativacao);
			pesquisaTipoCliente = repositorioSpcSerasa.pesquisarTipoCliente(idComandoNegativacao);

		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

		if(pesquisaParametrosComandoNegativacao != null && !(pesquisaParametrosComandoNegativacao.equals(""))){
			parametrosComandoNegativacaoHelper = new ParametrosComandoNegativacaoHelper();
			parametrosComandoNegativacaoHelper = setDadosGerais(parametrosComandoNegativacaoHelper, pesquisaParametrosComandoNegativacao,
							pesquisaTitularidadeNegativacao);
			parametrosComandoNegativacaoHelper = setDadosDebito(parametrosComandoNegativacaoHelper, pesquisaParametrosComandoNegativacao);
			parametrosComandoNegativacaoHelper = setDadosImovel(parametrosComandoNegativacaoHelper, pesquisaParametrosComandoNegativacao,
							pesquisaSubcategoria, pesquisaPerfilImovel, pesquisaTipoCliente);
			parametrosComandoNegativacaoHelper = setDadosLocalizacao(parametrosComandoNegativacaoHelper,
							pesquisaParametrosComandoNegativacao, pesquisaGrupoCobranca, pesquisaGerenciaRegional, pesquisaUnidadeNegocio,
							pesquisaEloPolo);
		}
		return parametrosComandoNegativacaoHelper;

	}

	private ParametrosComandoNegativacaoHelper setDadosLocalizacao(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Object[] pesquisaParametrosComandoNegativacao, Collection pesquisaGrupoCobranca, Collection pesquisaGerenciaRegional,
					Collection pesquisaUnidadeNegocio, Collection pesquisaEloPolo){

		// Seta a ColecaoGrupoCobranca
		ParametrosComandoNegativacaoHelper objetoCobrancaGrupo = setCobrancaGrupo(parametrosComandoNegativacaoHelper, pesquisaGrupoCobranca);
		if(objetoCobrancaGrupo.getColecaoGrupoCobranca() != null){
			parametrosComandoNegativacaoHelper.setColecaoGrupoCobranca(objetoCobrancaGrupo.getColecaoGrupoCobranca());
		}

		// Seta a ColecaoGerenciaRegional
		ParametrosComandoNegativacaoHelper objetoGerenciaRegional = setGerenciaRegional(parametrosComandoNegativacaoHelper,
						pesquisaGerenciaRegional);
		if(objetoGerenciaRegional.getColecaoGerenciaRegional() != null){
			parametrosComandoNegativacaoHelper.setColecaoGerenciaRegional(objetoGerenciaRegional.getColecaoGerenciaRegional());
		}
		// Seta a ColecaoUnidadeNegocio
		ParametrosComandoNegativacaoHelper objetoUnidadeNegocio = setUnidadeNegocio(parametrosComandoNegativacaoHelper,
						pesquisaUnidadeNegocio);
		if(objetoUnidadeNegocio.getColecaoUnidadeNegocio() != null){
			parametrosComandoNegativacaoHelper.setColecaoUnidadeNegocio(objetoUnidadeNegocio.getColecaoUnidadeNegocio());
		}

		// Seta a ColecaoEloPolo

		ParametrosComandoNegativacaoHelper objetoEloPolo = setEloPolo(parametrosComandoNegativacaoHelper, pesquisaEloPolo);
		if(objetoEloPolo.getColecaoEloPolo() != null){
			parametrosComandoNegativacaoHelper.setColecaoEloPolo(objetoEloPolo.getColecaoEloPolo());
		}
		// Seta a Localidade Inicial
		if(pesquisaParametrosComandoNegativacao[29] != null){
			parametrosComandoNegativacaoHelper.setLocInicial((String) pesquisaParametrosComandoNegativacao[29]);
		}
		// Seta a Localidade Final
		if(pesquisaParametrosComandoNegativacao[30] != null){
			parametrosComandoNegativacaoHelper.setLocFinal((String) pesquisaParametrosComandoNegativacao[30]);
		}
		// Seta o Setor Comencial Inicial
		if(pesquisaParametrosComandoNegativacao[31] != null){
			parametrosComandoNegativacaoHelper.setSetComInicial((String) pesquisaParametrosComandoNegativacao[31]);
		}
		// Seta o Setor Comencial Inicial
		if(pesquisaParametrosComandoNegativacao[32] != null){
			parametrosComandoNegativacaoHelper.setSetComInicial((String) pesquisaParametrosComandoNegativacao[32]);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setDadosImovel(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Object[] pesquisaParametrosComandoNegativacao, Collection pesquisaSubcategoria, Collection pesquisaPerfilImovel,
					Collection pesquisaTipoCliente){

		// Seta o ID do Cliente
		if(pesquisaParametrosComandoNegativacao[24] != null){
			parametrosComandoNegativacaoHelper.setIdCliente((Integer) pesquisaParametrosComandoNegativacao[24]);
		}
		// Seta o nome do Cliente
		if(pesquisaParametrosComandoNegativacao[25] != null){
			parametrosComandoNegativacaoHelper.setNomeCliente((String) pesquisaParametrosComandoNegativacao[25]);
		}
		// Seta o Tipo de Relacao com o Cliente
		if(pesquisaParametrosComandoNegativacao[26] != null){
			parametrosComandoNegativacaoHelper.setTipoRelClie((String) pesquisaParametrosComandoNegativacao[26]);
		}
		// Seta o valor do Indicador Especial de Cobranca
		if(pesquisaParametrosComandoNegativacao[27] != null){
			parametrosComandoNegativacaoHelper.setIndicadorEspCobranca((Short) pesquisaParametrosComandoNegativacao[27]);
		}
		// Seta o valor do Indicador da Situacao de Cobranca
		if(pesquisaParametrosComandoNegativacao[28] != null){
			parametrosComandoNegativacaoHelper.setIndicadorSitCobranca((Short) pesquisaParametrosComandoNegativacao[28]);
		}

		// Seta a ColecaoSubcategoria
		ParametrosComandoNegativacaoHelper objetoSubcategoria = setSubcategoria(parametrosComandoNegativacaoHelper, pesquisaSubcategoria);
		if(objetoSubcategoria.getColecaoSubcategoria() != null){
			parametrosComandoNegativacaoHelper.setColecaoSubcategoria(objetoSubcategoria.getColecaoSubcategoria());
		}

		// Seta a ColecaoPerfilImovel
		ParametrosComandoNegativacaoHelper objetoPerfilImovel = setPerfilImovel(parametrosComandoNegativacaoHelper, pesquisaPerfilImovel);
		if(objetoPerfilImovel.getColecaoPerfilImovel() != null){
			parametrosComandoNegativacaoHelper.setColecaoPerfilImovel(objetoPerfilImovel.getColecaoPerfilImovel());
		}
		// Seta a ColecaoTipoCliente
		ParametrosComandoNegativacaoHelper objetoTipoCliente = setTipoCliente(parametrosComandoNegativacaoHelper, pesquisaTipoCliente);
		if(objetoTipoCliente.getColecaoTipoCliente() != null){
			parametrosComandoNegativacaoHelper.setColecaoTipoCliente(objetoTipoCliente.getColecaoTipoCliente());
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setDadosDebito(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Object[] pesquisaParametrosComandoNegativacao){

		// Seta a Referencia Inicial
		if(pesquisaParametrosComandoNegativacao[10] != null){
			parametrosComandoNegativacaoHelper.setReferenciaInicial((Integer) pesquisaParametrosComandoNegativacao[10]);
		}
		// Seta a Referencia Final
		if(pesquisaParametrosComandoNegativacao[11] != null){
			parametrosComandoNegativacaoHelper.setReferenciaFinal((Integer) pesquisaParametrosComandoNegativacao[11]);
		}
		// Seta o Vencimento Inicial
		if(pesquisaParametrosComandoNegativacao[12] != null){
			parametrosComandoNegativacaoHelper.setVencimentoInicial((Date) pesquisaParametrosComandoNegativacao[12]);
		}
		// Seta o Vencimento Final
		if(pesquisaParametrosComandoNegativacao[13] != null){
			parametrosComandoNegativacaoHelper.setVencimentoFinal((Date) pesquisaParametrosComandoNegativacao[13]);
		}
		// Seta o Valor Minimo Debito
		if(pesquisaParametrosComandoNegativacao[14] != null){
			parametrosComandoNegativacaoHelper.setValoMinimoDebito((BigDecimal) pesquisaParametrosComandoNegativacao[14]);
		}
		// Seta o Valor Maximo Debito
		if(pesquisaParametrosComandoNegativacao[15] != null){
			parametrosComandoNegativacaoHelper.setValoMaximoDebito((BigDecimal) pesquisaParametrosComandoNegativacao[15]);
		}
		// Seta a Quntidade Minima de Contas
		if(pesquisaParametrosComandoNegativacao[16] != null){
			parametrosComandoNegativacaoHelper.setQtdMinimaContas((Integer) pesquisaParametrosComandoNegativacao[16]);
		}
		// Seta a Quntidade Maxima de Contas
		if(pesquisaParametrosComandoNegativacao[17] != null){
			parametrosComandoNegativacaoHelper.setQtdMaximaContas((Integer) pesquisaParametrosComandoNegativacao[17]);
		}
		// Seta o Indicador Conta Revisao
		if(pesquisaParametrosComandoNegativacao[18] != null){
			parametrosComandoNegativacaoHelper.setIndicadorContaRevisao((Short) pesquisaParametrosComandoNegativacao[18]);
		}
		// Seta o Indicador Guia Pagamento
		if(pesquisaParametrosComandoNegativacao[19] != null){
			parametrosComandoNegativacaoHelper.setIndicadorGuiaPagamento((Short) pesquisaParametrosComandoNegativacao[19]);
		}
		// Seta o Indicador Parcelamento Atraso
		if(pesquisaParametrosComandoNegativacao[20] != null){
			parametrosComandoNegativacaoHelper.setIndicadorParcelamentoAtraso((Short) pesquisaParametrosComandoNegativacao[20]);
		}
		// Seta Numero de dias de Atraso do Parcelamento
		if(pesquisaParametrosComandoNegativacao[21] != null){
			parametrosComandoNegativacaoHelper.setNumDiasAtrasoParcelamento((Integer) pesquisaParametrosComandoNegativacao[21]);
		}
		// Seta o Indicador de Recebimento da Carta de Parcelamento em Atraso
		if(pesquisaParametrosComandoNegativacao[22] != null){
			parametrosComandoNegativacaoHelper.setIndicadorCartaParcelamentoAtraso((Short) pesquisaParametrosComandoNegativacao[22]);
		}
		// Seta Numero de dias de Atraso apos Recebimento da Carta
		if(pesquisaParametrosComandoNegativacao[23] != null){
			parametrosComandoNegativacaoHelper.setNumDiasAtrasoAposRecCarta((Integer) pesquisaParametrosComandoNegativacao[23]);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setDadosGerais(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Object[] pesquisaParametrosComandoNegativacao, Collection pesquisaTitularidadeNegativacao){

		// Seta o nome do Negativador
		if(pesquisaParametrosComandoNegativacao[0] != null){
			parametrosComandoNegativacaoHelper.setNegativador((String) pesquisaParametrosComandoNegativacao[0]);
		}
		// Seta a Quantidade de Inclusões
		if(pesquisaParametrosComandoNegativacao[1] != null){
			parametrosComandoNegativacaoHelper.setQtdInclusoes((Integer) pesquisaParametrosComandoNegativacao[1]);
		}
		// Seta o valor Total do Débito
		if(pesquisaParametrosComandoNegativacao[2] != null){
			parametrosComandoNegativacaoHelper.setValorTotalDebito((BigDecimal) pesquisaParametrosComandoNegativacao[2]);
		}
		// Seta a Quantidade de Intens Incluídos
		if(pesquisaParametrosComandoNegativacao[3] != null){
			parametrosComandoNegativacaoHelper.setQtdItensIncluidos((Integer) pesquisaParametrosComandoNegativacao[3]);
		}
		// Seta o Titulo do Comando
		if(pesquisaParametrosComandoNegativacao[4] != null){
			parametrosComandoNegativacaoHelper.setTituloComando((String) pesquisaParametrosComandoNegativacao[4]);
		}
		// Seta a Descricao da Solicitacao
		if(pesquisaParametrosComandoNegativacao[5] != null){
			parametrosComandoNegativacaoHelper.setDescricaoSolicitacao((String) pesquisaParametrosComandoNegativacao[5]);
		}
		// Seta o Indicador Simulacao
		if(pesquisaParametrosComandoNegativacao[6] != null){
			parametrosComandoNegativacaoHelper.setSimularNegativacao((Short) pesquisaParametrosComandoNegativacao[6]);
		}
		// Seta a Data de Execucao
		if(pesquisaParametrosComandoNegativacao[7] != null){
			parametrosComandoNegativacaoHelper.setDataExecucao((Date) pesquisaParametrosComandoNegativacao[7]);
		}
		// Seta o Usuario Responsavel
		if(pesquisaParametrosComandoNegativacao[8] != null){
			parametrosComandoNegativacaoHelper.setUsuarioResponsavel((String) pesquisaParametrosComandoNegativacao[8]);
		}
		// Seta o id do cliente
		if(pesquisaParametrosComandoNegativacao[9] != null){
			parametrosComandoNegativacaoHelper.setQtdMaxInclusoes((Integer) pesquisaParametrosComandoNegativacao[9]);
		}
		// Titularidade do CPF/CNPJ da Negativacao
		ParametrosComandoNegativacaoHelper objetoTitularidadeNegativacao = setTitularidadeNegativacao(parametrosComandoNegativacaoHelper,
						pesquisaTitularidadeNegativacao);

		if(objetoTitularidadeNegativacao.getColecaoTitularNegativacao() != null
						&& !objetoTitularidadeNegativacao.getColecaoTitularNegativacao().isEmpty()){
			parametrosComandoNegativacaoHelper.setColecaoTitularNegativacao(objetoTitularidadeNegativacao.getColecaoTitularNegativacao());
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setTitularidadeNegativacao(
					ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper, Collection pesquisaTitularidadeNegativacao){

		if(pesquisaTitularidadeNegativacao != null && !pesquisaTitularidadeNegativacao.isEmpty()){

			Collection<NegativacaoCriterioCpfTipo> colecaoNegativacaoCriterioCpfTipo = new ArrayList();
			Iterator titularidadeNegativacaoIterator = pesquisaTitularidadeNegativacao.iterator();
			while(titularidadeNegativacaoIterator.hasNext()){
				NegativacaoCriterioCpfTipo negativacaoCriterioCpfTipo = new NegativacaoCriterioCpfTipo();
				Object[] titularidadeNegativacao = (Object[]) titularidadeNegativacaoIterator.next();
				if(titularidadeNegativacao[3] != null){
					CpfTipo cpfTipo = new CpfTipo();
					cpfTipo.setId((Integer) titularidadeNegativacao[3]);
					cpfTipo.setDescricaoTipoCpf((String) titularidadeNegativacao[0]);
					negativacaoCriterioCpfTipo.setCpfTipo(cpfTipo);
				}
				if(titularidadeNegativacao[1] != null){
					negativacaoCriterioCpfTipo.setNumeroOrdemSelecao((Short) titularidadeNegativacao[1]);
				}
				if(titularidadeNegativacao[2] != null){
					negativacaoCriterioCpfTipo.setIndicadorCoincidente((Short) titularidadeNegativacao[2]);
				}
				colecaoNegativacaoCriterioCpfTipo.add(negativacaoCriterioCpfTipo);
			}
			parametrosComandoNegativacaoHelper.setColecaoTitularNegativacao(colecaoNegativacaoCriterioCpfTipo);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setCobrancaGrupo(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Collection pesquisaGrupoCobranca){

		if(pesquisaGrupoCobranca != null && !pesquisaGrupoCobranca.isEmpty()){
			Collection<CobrancaGrupo> colecaoGrupoCobranca = new ArrayList();
			Iterator grupoCobrancaIterator = pesquisaGrupoCobranca.iterator();
			while(grupoCobrancaIterator.hasNext()){
				CobrancaGrupo cobrancaGrupoAuxiliar = new CobrancaGrupo();
				Object[] grupoCobranca = (Object[]) grupoCobrancaIterator.next();
				if(grupoCobranca[1] != null){
					cobrancaGrupoAuxiliar.setId((Integer) grupoCobranca[1]);
					cobrancaGrupoAuxiliar.setDescricao((String) grupoCobranca[0]);
				}
				colecaoGrupoCobranca.add(cobrancaGrupoAuxiliar);
			}
			parametrosComandoNegativacaoHelper.setColecaoGrupoCobranca(colecaoGrupoCobranca);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setGerenciaRegional(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Collection pesquisaGerenciaRegional){

		if(pesquisaGerenciaRegional != null && !pesquisaGerenciaRegional.isEmpty()){
			Collection<GerenciaRegional> colecaoGerenciaRegional = new ArrayList();
			Iterator gerenciaRegionalIterator = pesquisaGerenciaRegional.iterator();
			while(gerenciaRegionalIterator.hasNext()){
				GerenciaRegional gerenciaRegionalAuxiliar = new GerenciaRegional();
				Object[] gerenciaRegional = (Object[]) gerenciaRegionalIterator.next();
				if(gerenciaRegional[1] != null){
					gerenciaRegionalAuxiliar.setId((Integer) gerenciaRegional[1]);
					gerenciaRegionalAuxiliar.setNome((String) gerenciaRegional[0]);
				}
				colecaoGerenciaRegional.add(gerenciaRegionalAuxiliar);
			}
			parametrosComandoNegativacaoHelper.setColecaoGerenciaRegional(colecaoGerenciaRegional);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setUnidadeNegocio(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Collection pesquisaUnidadeNegocio){

		if(pesquisaUnidadeNegocio != null && !pesquisaUnidadeNegocio.isEmpty()){
			Collection<UnidadeNegocio> colecaoUnidadeNegocio = new ArrayList();
			Iterator unidadeNegocioIterator = pesquisaUnidadeNegocio.iterator();
			while(unidadeNegocioIterator.hasNext()){
				UnidadeNegocio unidadeNegocioAuxiliar = new UnidadeNegocio();
				Object[] unidadeNegocio = (Object[]) unidadeNegocioIterator.next();
				if(unidadeNegocio[1] != null){
					unidadeNegocioAuxiliar.setId((Integer) unidadeNegocio[1]);
					unidadeNegocioAuxiliar.setNome((String) unidadeNegocio[0]);
				}
				colecaoUnidadeNegocio.add(unidadeNegocioAuxiliar);
			}
			parametrosComandoNegativacaoHelper.setColecaoUnidadeNegocio(colecaoUnidadeNegocio);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setEloPolo(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Collection pesquisaEloPolo){

		if(pesquisaEloPolo != null && !pesquisaEloPolo.isEmpty()){
			Collection<Localidade> colecaoEloPolo = new ArrayList();
			Iterator eloPoloIterator = pesquisaEloPolo.iterator();
			while(eloPoloIterator.hasNext()){
				Localidade eloPoloAuxiliar = new Localidade();
				Object[] eloPolo = (Object[]) eloPoloIterator.next();
				if(eloPolo[1] != null){
					eloPoloAuxiliar.setId((Integer) eloPolo[1]);
					eloPoloAuxiliar.setDescricao((String) eloPolo[0]);
				}
				colecaoEloPolo.add(eloPoloAuxiliar);
			}
			parametrosComandoNegativacaoHelper.setColecaoEloPolo(colecaoEloPolo);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setSubcategoria(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Collection pesquisaSubcategoria){

		if(pesquisaSubcategoria != null && !pesquisaSubcategoria.isEmpty()){
			Collection<Subcategoria> colecaoSubcategoria = new ArrayList<Subcategoria>();
			Iterator subcategoriaIterator = pesquisaSubcategoria.iterator();
			while(subcategoriaIterator.hasNext()){
				Subcategoria subcategoriaAuxiliar = new Subcategoria();
				Object[] subcategoria = (Object[]) subcategoriaIterator.next();
				if(subcategoria[1] != null){
					subcategoriaAuxiliar.setId((Integer) subcategoria[1]);
					subcategoriaAuxiliar.setDescricao((String) subcategoria[0]);
				}
				colecaoSubcategoria.add(subcategoriaAuxiliar);
			}
			parametrosComandoNegativacaoHelper.setColecaoSubcategoria(colecaoSubcategoria);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setPerfilImovel(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Collection pesquisaPerfilImovel){

		if(pesquisaPerfilImovel != null && !pesquisaPerfilImovel.isEmpty()){
			Collection<ImovelPerfil> colecaoPerfilImovel = new ArrayList();
			Iterator perfilImovelIterator = pesquisaPerfilImovel.iterator();
			while(perfilImovelIterator.hasNext()){
				ImovelPerfil imovelPerfilAuxiliar = new ImovelPerfil();
				Object[] imovelPerfil = (Object[]) perfilImovelIterator.next();
				if(imovelPerfil[1] != null){
					imovelPerfilAuxiliar.setId((Integer) imovelPerfil[1]);
					imovelPerfilAuxiliar.setDescricao((String) imovelPerfil[0]);
				}
				colecaoPerfilImovel.add(imovelPerfilAuxiliar);
			}
			parametrosComandoNegativacaoHelper.setColecaoPerfilImovel(colecaoPerfilImovel);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private ParametrosComandoNegativacaoHelper setTipoCliente(ParametrosComandoNegativacaoHelper parametrosComandoNegativacaoHelper,
					Collection pesquisaTipoCliente){

		if(pesquisaTipoCliente != null && !pesquisaTipoCliente.isEmpty()){

			Collection<ClienteTipo> colecaoTipoCliente = new ArrayList();
			Iterator tipoClienteIterator = pesquisaTipoCliente.iterator();
			while(tipoClienteIterator.hasNext()){
				ClienteTipo clienteTipoAuxiliar = new ClienteTipo();
				Object[] clienteTipo = (Object[]) tipoClienteIterator.next();
				if(clienteTipo[1] != null){
					clienteTipoAuxiliar.setId((Integer) clienteTipo[1]);
					clienteTipoAuxiliar.setDescricao((String) clienteTipo[0]);
				}
				colecaoTipoCliente.add(clienteTipoAuxiliar);
			}
			parametrosComandoNegativacaoHelper.setColecaoTipoCliente(colecaoTipoCliente);
		}
		return parametrosComandoNegativacaoHelper;
	}

	private Cliente verificaCriterioNegativacaoImovel(int idImovel, int idNegativacaoCriterio) throws ErroRepositorioException,
					ControladorException{

		Cliente retorno = null;
		try{

			// [SB0010] Obter Documento da Negativacao
			List titularidadeDocumentos = this.repositorioSpcSerasa.obtemTitularidadesDocumentos(idNegativacaoCriterio);

			// [SB0011] Obter dados do cliente da Negativacao
			if(titularidadeDocumentos.size() == 1){
				NegativacaoCriterioCpfTipo titularidade = (NegativacaoCriterioCpfTipo) Util
								.retonarObjetoDeColecao(this.repositorioSpcSerasa.obtemTitularidadesDocumentos(idNegativacaoCriterio));

				Cliente clienteUsuario = (Cliente) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.obtemDadosClienteNegativacao(
								idImovel, titularidade.getNegativacaoCriterio().getClienteRelacaoTipo().getId()));
				retorno = clienteUsuario;
			}else{
				Cliente clienteProprietario = null;
				Cliente clienteUsu = null;
				// para cada imovel da lista de imoveis
				Iterator itTitularidadeDocumentos = titularidadeDocumentos.iterator();
				while(itTitularidadeDocumentos.hasNext()){
					NegativacaoCriterioCpfTipo titularidades = (NegativacaoCriterioCpfTipo) itTitularidadeDocumentos.next();

					if(titularidades.getNumeroOrdemSelecao() != 0){
						Cliente clienteUsuario = (Cliente) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa
										.obtemDadosClienteNegativacao(idImovel, titularidades.getNegativacaoCriterio()
														.getClienteRelacaoTipo().getId()));
						if((clienteUsuario.getCpf().equals("") || clienteUsuario.getCpf() == "" || clienteUsuario.getCpf() == null)
										|| (clienteUsuario.getCnpj().equals("") || clienteUsuario.getCnpj() == "" || clienteUsuario
														.getCnpj() == null)){
							continue;
						}else{
							retorno = clienteUsuario;
							break;
						}
					}else{
						Cliente clienteUsuario = (Cliente) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa
										.obtemDadosClienteNegativacao(idImovel, titularidades.getNegativacaoCriterio()
														.getClienteRelacaoTipo().getId()));
						if(titularidades.getNegativacaoCriterio().getClienteRelacaoTipo().getId() == 1){
							clienteProprietario = clienteUsuario;
						}else if(titularidades.getNegativacaoCriterio().getClienteRelacaoTipo().getId() == 2){
							clienteUsu = clienteUsuario;
						}
						if(clienteProprietario != null && clienteUsu != null){
							if(clienteProprietario.getCpf().equals(clienteUsu.getCpf())){
								retorno = clienteProprietario;
							}
						}
					}
				}
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	public void geraNegativacaoImovel(Collection imoveisNegCliente, NegativacaoCriterio negCriterio, int idUsuarioResponsavel,
					DadosNegativacaoPorImovelHelper listaDadosImovel, int quantidadeRegistros, int primeiraVez)
					throws ControladorException, ErroRepositorioException{

		Imovel imovelNegativado = null;

		Iterator itimoveisNegCliente = imoveisNegCliente.iterator();
		while(itimoveisNegCliente.hasNext()){
			imovelNegativado = (Imovel) itimoveisNegCliente.next();

			// [SB0005] 1.
			Integer ocorrenciaImovel = this.repositorioSpcSerasa.verificaExistenciaNegativacao(imovelNegativado.getId(), negCriterio
							.getNegativacaoComando().getNegativador().getId());
			if(ocorrenciaImovel == 0 || ocorrenciaImovel == null){
				// Passa para próximo imovel.
				continue;
				// Caso Contrario [SB0005] 2.
			}else{

				// [SB0006] 1. chama o [SB0010] - Obter documento da Negativacao.
				Cliente clienteDocumentoNegativacao = this.verificaCriterioNegativacaoImovel(imovelNegativado.getId(), negCriterio.getId());

				// [SB0006] 2. Verifica CPF e CNPJ
				if((clienteDocumentoNegativacao.getCpf().trim().equals("")) && (clienteDocumentoNegativacao.getCnpj().trim().equals(""))){
					continue;
				}
				// [SB0006] 3.
				if(negCriterio.getIndicadorNegativacaoImovelParalisacao() == 2 && imovelNegativado.getCobrancaSituacaoTipo() != null){
					continue;
				}
				// [SB0006] 4.
				Integer ocorrenciaCobrancaImovel = (Integer) this.repositorioSpcSerasa
								.verificaExistenciaImovelCobrancaSituacao(imovelNegativado.getId());
				if(ocorrenciaCobrancaImovel == null){
					ocorrenciaCobrancaImovel = 0;
				}
				if(negCriterio.getIndicadorNegativacaoImovelParalisacao() == 2 && ocorrenciaCobrancaImovel > 0){
					continue;
				}
				// [SB0006] 5.
				Integer ocorrenciaSubCatImovelCriterio = this.repositorioSpcSerasa.verificaSubCategoriaImovelNegativacaoCriterio(
								imovelNegativado.getId(), negCriterio.getId());
				if(ocorrenciaSubCatImovelCriterio == null){
					ocorrenciaSubCatImovelCriterio = 0;
				}
				if(ocorrenciaSubCatImovelCriterio == 0){
					continue;
				}
				// [SB0006] 6.
				Integer ocorrenciaPerfilImovelCriterio = this.repositorioSpcSerasa.verificaPerfilImovelNegativacaoCriterio(negCriterio
								.getId(), imovelNegativado.getImovelPerfil().getId());
				if(ocorrenciaPerfilImovelCriterio == null){
					ocorrenciaPerfilImovelCriterio = 0;
				}
				if(ocorrenciaPerfilImovelCriterio == 0){
					continue;
				}
				// [SB0006] 7.
				Integer ocorrenciaClienteUsuarioNegativacaoCriterio = this.repositorioSpcSerasa.verificaTipoClienteNegativacaoCriterio(
								imovelNegativado.getId(), negCriterio.getId());
				if(ocorrenciaClienteUsuarioNegativacaoCriterio == null){
					ocorrenciaClienteUsuarioNegativacaoCriterio = 0;
				}
				if(ocorrenciaClienteUsuarioNegativacaoCriterio == 0){
					continue;
				}

				// [SB0006] 8.
				// seta valores constantes para chamar o metodo que consulta debitos do imovel
				Integer tipoImovel = Integer.valueOf(1);
				Integer indicadorPagamento = Integer.valueOf(1);
				Integer indicadorDebito = Integer.valueOf(2);
				Integer indicadorCredito = Integer.valueOf(2);
				Integer indicadorNotas = Integer.valueOf(2);
				Integer indicadorAtualizar = Integer.valueOf(1);
				int indicadorCalcularAcrescimosSucumbenciaAnterior = 2;

				// Obtendo os débitos do imovel
				ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = this.getControladorCobranca().obterDebitoImovelOuCliente(
								tipoImovel.intValue(), imovelNegativado.getId().toString(), null, null,
								negCriterio.getAnoMesReferenciaContaInicial().toString(),
								negCriterio.getAnoMesReferenciaContaFinal().toString(), negCriterio.getDataVencimentoDebitoInicial(),
								negCriterio.getDataVencimentoDebitoFinal(), indicadorPagamento.intValue(),
								(int) negCriterio.getIndicadorNegativacaoContaRevisao(), indicadorDebito.intValue(),
								indicadorCredito.intValue(), indicadorNotas.intValue(),
								(int) negCriterio.getIndicadorNegativacaoGuiaPagamento(), indicadorAtualizar.intValue(), null, null, null,
								null, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
								indicadorCalcularAcrescimosSucumbenciaAnterior, null);
				// [SB0006] 9.
				// Coleção de Contas
				Collection<ContaValoresHelper> colecaoContasValores = colecaoDebitoImovel.getColecaoContasValores();
				Iterator itColecaoContasValores = colecaoContasValores.iterator();
				while(itColecaoContasValores.hasNext()){
					ContaValoresHelper contaValores = (ContaValoresHelper) itColecaoContasValores.next();
					if(contaValores.getValorPago() != null){
						itColecaoContasValores.remove();
					}
				}
				// [SB0006] 9.
				// Coleção de Guias de Pagamento
				Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = colecaoDebitoImovel.getColecaoGuiasPagamentoValores();
				Iterator itColecaoGuiasPagamentoValores = colecaoGuiasPagamentoValores.iterator();
				while(itColecaoGuiasPagamentoValores.hasNext()){
					GuiaPagamentoValoresHelper guiaPagamentoValores = (GuiaPagamentoValoresHelper) itColecaoGuiasPagamentoValores.next();
					if(guiaPagamentoValores.getValorPago() != null){
						itColecaoGuiasPagamentoValores.remove();
					}

				}
				// [SB0006] 10.
				if(colecaoContasValores.isEmpty() && colecaoGuiasPagamentoValores.isEmpty()){
					continue;
				}
				// [SB0006] 11.
				BigDecimal valorTotalConta = BigDecimal.ZERO;
				BigDecimal valorTotalGuiaPagamento = BigDecimal.ZERO;
				BigDecimal valorTotal = BigDecimal.ZERO;
				Integer quantidadeTotalItensDebito = 0;
				// Varre lista de contas para totalizar
				itColecaoContasValores = colecaoContasValores.iterator();
				while(itColecaoContasValores.hasNext()){
					ContaValoresHelper contaValores = (ContaValoresHelper) itColecaoContasValores.next();
					// [SB0006] 11.1 Acumula valores total da conta.
					valorTotalConta.add(contaValores.getValorTotalConta());
					quantidadeTotalItensDebito += 1;
				}
				// varre lista de guias de pagamento para totalizar
				itColecaoGuiasPagamentoValores = colecaoGuiasPagamentoValores.iterator();
				while(itColecaoGuiasPagamentoValores.hasNext()){
					GuiaPagamentoValoresHelper guiaPagamentoValores = (GuiaPagamentoValoresHelper) itColecaoGuiasPagamentoValores.next();
					// [SB0006] 11.2 Acumula valor debito da guia de paramento
					valorTotalGuiaPagamento.add(guiaPagamentoValores.getValorTotalPrestacao());
					quantidadeTotalItensDebito += 1;
				}
				// Valor total de debitos do imovel.
				valorTotal.add(valorTotalConta.add(valorTotalGuiaPagamento));
				// [SB0006] 12.
				if(valorTotal.floatValue() < negCriterio.getValorMinimoDebito().floatValue()
								|| valorTotal.floatValue() > negCriterio.getValorMaximoDebito().floatValue()){
					continue;
				}
				// [SB0006] 14.
				if(quantidadeTotalItensDebito < negCriterio.getQuantidadeMinimaContas()
								|| quantidadeTotalItensDebito > negCriterio.getQuantidadeMaximaContas()){
					continue;
				}
				Parcelamento clienteParcelamento = new Parcelamento();
				Integer indicadorImovelParcelamento = 0;
				Integer indicadorRecebimentoCarta = 0;
				Integer numeroDiasAtaso = 0;
				itColecaoContasValores = colecaoContasValores.iterator();
				while(itColecaoContasValores.hasNext()){
					ContaValoresHelper contaValores = (ContaValoresHelper) itColecaoContasValores.next();

					// obtem imovel para pesquisar se o mesmo tem parcelamento.
					Integer imovelParcelamento = this.repositorioSpcSerasa.verificaDebitoCobradoConta(contaValores.getConta().getId());
					if(imovelParcelamento == null){
						imovelParcelamento = 0;
					}
					if(imovelParcelamento != 0){
						clienteParcelamento = (Parcelamento) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa
										.verificaImovelParcelamento(imovelParcelamento));
						indicadorImovelParcelamento = 1;
					}
				}
				// [SB0006] 16.
				if(negCriterio.getIndicadorParcelamentoAtraso() == 1){
					// [SB0006] 16.1
					if(indicadorImovelParcelamento == 0){
						continue;
					}
					if(negCriterio.getNumeroDiasParcelamentoAtraso() != 0 && negCriterio.getNumeroDiasParcelamentoAtraso() != null){
						if(clienteParcelamento != null){
							numeroDiasAtaso = Util.obterQuantidadeDiasEntreDuasDatas(new Date(), clienteParcelamento.getParcelamento());
						}
					}
					// [SB0006] 16.2.1
					if(numeroDiasAtaso < negCriterio.getNumeroDiasParcelamentoAtraso()){
						continue;
					}
				}
				// [SB0006] 17.
				if(negCriterio.getIndicadorNegativacaoRecebimentoCartaParcelamento() == 1){
					// [SB0006] 17.1.
					indicadorRecebimentoCarta = this.repositorioSpcSerasa.verificaCartaAvisoParcelamento(imovelNegativado.getId(),
									negCriterio.getNumeroDiasAtrasoRecebimentoCartaParcelamento());
					if(indicadorRecebimentoCarta == null){
						indicadorRecebimentoCarta = 0;
					}
					if(indicadorRecebimentoCarta == 0){
						continue;
					}
				}
				// [SB0007] - Gerar Registro da Negativação
				this.geraRegistroNegativacao(imovelNegativado, negCriterio.getNegativacaoComando().getNegativador().getId(),
								idUsuarioResponsavel, negCriterio.getNegativacaoComando().getId(), listaDadosImovel, quantidadeRegistros,
								primeiraVez);
			}
		}
	}

	public void geraRegistroNegativacao(Imovel imovelNegativado, int idNegativador, int idUsuarioResponsaval, int idNegativadorComando,
					DadosNegativacaoPorImovelHelper listaDadosImovel, int quantidadeRegistros, int primeiraVez)
					throws ControladorException, ErroRepositorioException{

		int idNegativadorMovimento = 0;
		StringBuilder registroTipoHeader = new StringBuilder();

		// Numero do Sequencial de envio.
		int saEnvio = this.repositorioSpcSerasa.getSaEnvioContratoNegativador(idNegativador);

		// Caso esteja gerando o primeiro registro de negativação
		Negativador negativador = new Negativador();
		NegativacaoComando negativacaoComando = new NegativacaoComando();
		NegativadorExclusaoMotivo negativadorExclusaoMotivo = new NegativadorExclusaoMotivo();
		NegativadorRegistroTipo negativadorRegistroTipo = new NegativadorRegistroTipo();
		NegativadorMovimentoReg negativadorMovimentoReg = new NegativadorMovimentoReg();
		NegativadorMovimento negativadorMovimento = new NegativadorMovimento();
		if(primeiraVez == 0){
			// Modifica flag para não entrar mais de uma vez na
			// geração do Header.
			// chama metodo que gera os dados do movimento da negativacao na tabela
			// NEGATIVADOR_MOVIMENTO
			negativador.setId(idNegativador);
			negativacaoComando.setId(idNegativadorComando);
			negativadorMovimento.setCodigoMovimento(Short.valueOf("1"));
			negativadorMovimento.setDataEnvio(new Date());
			negativadorMovimento.setDataProcessamentoEnvio(new Date());
			negativadorMovimento.setNumeroSequencialEnvio(saEnvio + 1);
			negativadorMovimento.setUltimaAlteracao(new Date());
			negativadorMovimento.setNegativador(negativador);
			Integer id = (Integer) this.getControladorUtil().inserir(negativacaoComando);
			negativadorMovimento.setId(id);

			// [SB0008] - Gerar Registro tipo Header
			registroTipoHeader = this.geraRegistroTipoHeader(saEnvio, 0);

			// [SB0008] - 2.

			negativadorExclusaoMotivo.setId(quantidadeRegistros);

			negativadorRegistroTipo.setId(this.repositorioSpcSerasa.getTpoRegistro(idNegativador));

			negativadorMovimentoReg.setNegativadorMovimento(negativadorMovimento);
			negativadorMovimentoReg.setNegativadorRegistroTipo(negativadorRegistroTipo);
			negativadorMovimentoReg.setConteudoRegistro(registroTipoHeader.toString());
			negativadorMovimentoReg.setUltimaAlteracao(new Date());
			negativadorMovimentoReg.setNumeroCnpj("2");
			negativadorMovimentoReg.setNegativadorExclusaoMotivo(negativadorExclusaoMotivo);

			this.getControladorUtil().inserir(negativadorMovimentoReg);
		}

		// [SB0009]
		StringBuilder registroDetalheConsumidor = this.geraRegistroTipoDetalhe(saEnvio, quantidadeRegistros, listaDadosImovel);

		// Gera registro do movimento da negativação correspondente ao registro tipo Detalhe
		// Consumidor.
		negativadorExclusaoMotivo.setId(quantidadeRegistros);
		negativadorRegistroTipo.setId(this.repositorioSpcSerasa.getTpoRegistro(idNegativador));
		negativadorMovimento.setId(idNegativadorMovimento);
		negativacaoComando.setId(idNegativadorComando);

		negativadorMovimentoReg = new NegativadorMovimentoReg();
		negativadorMovimentoReg.setNegativadorMovimento(negativadorMovimento);
		negativadorMovimentoReg.setNegativadorRegistroTipo(negativadorRegistroTipo);
		negativadorMovimentoReg.setConteudoRegistro(registroDetalheConsumidor.toString());
		negativadorMovimentoReg.setUltimaAlteracao(new Date());
		negativadorMovimentoReg.setNumeroCnpj("2");
		negativadorMovimentoReg.setNegativadorExclusaoMotivo(negativadorExclusaoMotivo);
		this.getControladorUtil().inserir(negativadorMovimentoReg);

		// [SB0009] 1.3
		StringBuilder registroDetalheSPC = this.geraRegistroTipoDetalheSPC(saEnvio, 0, listaDadosImovel);

		int idCategoria = 0;
		int idImovel = listaDadosImovel.getIdImovel();
		Categoria categoria = null;
		categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(idImovel);
		if(categoria != null){
			idCategoria = categoria.getId();
		}
		Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDadosImoveis(listaDadosImovel.getIdImovel()));
		int idLocalidade = imovel.getLocalidade().getId();
		int idQuadra = imovel.getQuadra().getId();
		int stComercialCD = imovel.getQuadra().getSetorComercial().getId();
		int numeroQuadra = imovel.getQuadra().getNumeroQuadra();
		int iper_id = imovel.getImovelPerfil().getId();
		int idCliente = listaDadosImovel.getIdCliente();
		String cpfCliente = listaDadosImovel.getCpfCliente();
		String cnpjCliente = listaDadosImovel.getCnpjCliente();

		// Gera registro movimento da negativação correspondente
		// ao registro tipo detail-SPC.
		this.repositorioSpcSerasa.geraRegistroNegativacaoRegDetalheSPC(idNegativador, idUsuarioResponsaval, saEnvio, idNegativadorComando,
						idNegativadorMovimento, registroDetalheSPC, quantidadeRegistros, BigDecimal.ZERO, 0, idImovel, idLocalidade,
						idQuadra, stComercialCD, numeroQuadra, iper_id, idCliente, idCategoria, cpfCliente, cnpjCliente);
	}

	// GERA REGISTRO HEADER ***********************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0008] **
	// ********************************************
	public StringBuilder geraRegistroTipoHeader(int saEnvio, int quantidadeRegistros) throws ControladorException, ErroRepositorioException{

		StringBuilder registroHeader = new StringBuilder();
		// H.01
		registroHeader.append("00");
		// H.02
		registroHeader.append("REMESSA");
		// seta a data corrente
		// H.03
		String dataAtualString = Util.recuperaDataInvertida(new Date());
		registroHeader.append(Util.completaString(dataAtualString, 8));
		// H.04
		saEnvio += 1;
		registroHeader.append(Util.adicionarZerosEsquedaNumero(8, "" + saEnvio + 1));
		// H.05
		registroHeader.append("01101");
		// H.06
		registroHeader.append("        ");
		// H.07
		String h07 = Util.recuperaDataInvertida(new Date());
		registroHeader.append(Util.completaString(h07, 8));
		// H.08
		registroHeader.append(Util.completaString(" ", 270));
		// H.09
		registroHeader.append("  SPC");
		// H.10
		registroHeader.append("07");
		// H.11
		registroHeader.append("          ");
		// H.12
		quantidadeRegistros += 1;
		registroHeader.append(Util.adicionarZerosEsquedaNumero(6, "" + quantidadeRegistros));

		return registroHeader;
	}

	// GERA REGISTRO DETALHE **********************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ********************************************
	public StringBuilder geraRegistroTipoDetalhe(int saEnvio, int quantidadeRegistros, DadosNegativacaoPorImovelHelper listaDadosImovel)
					throws ControladorException, ErroRepositorioException{

		StringBuilder registroDetalheConsumidor = new StringBuilder();

		// Obtem dados do cliente.
		Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDadosCliente(listaDadosImovel.getIdCliente()));

		// //////////////////////////////////DETALHE CONSUMIDOR
		// D1.01
		registroDetalheConsumidor.append("01");
		// D1.02
		String pracaConcessao = (String) ParametroSpcSerasa.P_PRACA_CONCESSAO_NEGATIVACAO.executar(ExecutorParametrosSpcSerasa
						.getInstancia());
		pracaConcessao = Util.adicionarZerosEsquedaNumero(8, pracaConcessao);
		registroDetalheConsumidor.append(pracaConcessao);
		// D1.03 - Espaços em Branco
		registroDetalheConsumidor.append(Util.completaString(" ", 45));
		// D1.04 - Indicador se tem ou não CNPJ ou CPF Preenchido
		if(listaDadosImovel.getCnpjCliente().length() > 0){
			registroDetalheConsumidor.append("1");
		}else{
			registroDetalheConsumidor.append("2");
		}
		// D1.05 - CPF ou CNPJ
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(15, listaDadosImovel.getCnpjCliente()));
		// D1.06 - RG Cliente
		registroDetalheConsumidor.append(Util.completaString(cliente.getRg(), 20));
		// D1.07 - Data de Nascimento
		String D107 = Util.recuperaDataInvertida((Date) cliente.getDataNascimento());
		registroDetalheConsumidor.append(D107);
		// D1.08 - Nome da Mae
		registroDetalheConsumidor.append(Util.completaString(cliente.getNomeMae(), 45));
		// D1.09 - Endereco
		String ender = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(cliente.getId());
		registroDetalheConsumidor.append(Util.completaString(ender, 50));

		// Obtem dados do endereço do cliente.
		ClienteEndereco cliEnder = (ClienteEndereco) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDadosEnderecoCliente(cliente
						.getId()));

		// D1.10 - numero
		registroDetalheConsumidor.append(Util.completaString(cliEnder.getNumero(), 5));
		// D1.11 - complemento
		registroDetalheConsumidor.append(Util.completaString(cliEnder.getComplemento(), 30));

		Integer idLogBairro = cliEnder.getLogradouroBairro().getId();

		Bairro bairroCep = (Bairro) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getBairroCep(cliente.getId()));
		LogradouroCep cep = (LogradouroCep) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getCep(cliEnder.getId()));
		LogradouroBairro muni = (LogradouroBairro) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getMunicipio(idLogBairro));

		if(idLogBairro != null){
			registroDetalheConsumidor.append(Util.completaString(bairroCep.getNome(), 25));
			registroDetalheConsumidor.append(Util.completaString("" + cep.getCep().getCodigo(), 8));
			registroDetalheConsumidor.append(Util.completaString(muni.getLogradouro().getMunicipio().getNome(), 30));
			registroDetalheConsumidor.append(Util.completaString(cep.getCep().getSigla(), 2));
		}else{
			registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroBairro().getBairro().getNome(), 25));
			registroDetalheConsumidor.append(Util.completaString("" + cliEnder.getLogradouroCep().getCep().getCodigo(), 8));
			registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getMunicipio(), 30));
			registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getSigla(), 2));
		}

		ClienteFone cliFone = (ClienteFone) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDddFone(cliente.getId()));
		registroDetalheConsumidor.append(cliFone.getDdd());
		registroDetalheConsumidor.append(Util.completaString(" ", 19));
		registroDetalheConsumidor.append(cliFone.getTelefone());
		registroDetalheConsumidor.append(Util.completaString(" ", 9));
		quantidadeRegistros += 1;
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(6, "" + quantidadeRegistros));

		return registroDetalheConsumidor;
	}

	// GERA REGISTRO DETALHE **********************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ********************************************
	public StringBuilder geraRegistroTipoDetalheSPC(int saEnvio, int quantidadeRegistros, DadosNegativacaoPorImovelHelper listaDadosImovel)
					throws ControladorException, ErroRepositorioException{

		StringBuilder registroDetalheSPC = new StringBuilder();

		// //////////////////////////////////DETALHE SPC
		// D2.01
		registroDetalheSPC.append("02");
		// D2.02 - Espaços em Branco
		registroDetalheSPC.append(Util.completaString(" ", 45));
		// D2.03 - Indicador se tem ou não CNPJ ou CPF
		// Preenchido
		if(listaDadosImovel.getCnpjCliente().length() > 0){
			registroDetalheSPC.append("1");
		}else{
			registroDetalheSPC.append("2");
		}
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(15, listaDadosImovel.getCnpjCliente()));

		registroDetalheSPC.append("I");

		registroDetalheSPC.append("C");
		// Maior data
		Date maiorData = this.obterMaiorMenorVencimento(listaDadosImovel.getColecaoGuias(), listaDadosImovel.getColecaoGuias(), 1);
		String D206 = Util.recuperaDataInvertida(maiorData); // VERIFICAR DEPOIS
		registroDetalheSPC.append(D206);
		// Maior data
		Date menorData = this.obterMaiorMenorVencimento(listaDadosImovel.getColecaoGuias(), listaDadosImovel.getColecaoGuias(), 2);
		String D207 = Util.recuperaDataInvertida(menorData);
		registroDetalheSPC.append(D206);
		// Valor total debito imovel
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(13, (listaDadosImovel.getTotalDebitosImovel() + "").replace(".", "")));
		// imov_id
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(30, "" + listaDadosImovel.getIdImovel()));
		// D210 - Preencher com espaços em branco
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(7, " "));
		// D211 - 00
		registroDetalheSPC.append("00");
		// D212 - Preencher com espaços em branco - 03 posicoes
		registroDetalheSPC.append("   ");
		// D213 - Preencher com espaços em branco - 232 posicoes
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(231, " "));
		// D214 - Preencher com espaços em branco - 10 posicoes
		registroDetalheSPC.append("          ");
		// D215 - numero do registro
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(6, "" + quantidadeRegistros));

		return registroDetalheSPC;
	}

	/**
	 * Método consuta os Negativadores que tenham movimento de Exclusão do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0003] - Selecionar Negativadores
	 * 
	 * @author Thiago Toscano
	 * @date 21/12/2007
	 */
	public Collection consultarNegativadoresParaExclusaoMovimento() throws ControladorException{

		Collection coll = new ArrayList();

		try{

			coll = repositorioSpcSerasa.consultarNegativadoresParaExclusaoMovimento();

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return coll;
	}

	/**
	 * Método consuta os Negativadores que tenham movimento de Exclusão do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0001] - Gerar Movimento da Exclusão de Negativação
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 */
	public Collection gerarMovimentoExclusaoNegativacao(Integer[] id) throws ControladorException{

		// todas as movimentacoes
		Collection coll = new ArrayList();

		try{
			// consultar as negativacoes para excluir
			Collection collNegativadoresParaExclusao = repositorioSpcSerasa.consultarNegativacoesParaExclusaoMovimento(id);

			// gerando um map dos navegadores com os NegativadorMovimentoReg
			Map mapNegativadorCOMNegativadorMovimentoReg = new HashMap();
			Iterator it = collNegativadoresParaExclusao.iterator();
			while(it.hasNext()){
				NegativadorMovimentoReg negativadorMovimentoReg = (NegativadorMovimentoReg) it.next();

				if(mapNegativadorCOMNegativadorMovimentoReg.containsKey(negativadorMovimentoReg.getNegativadorMovimento().getNegativador())){
					ArrayList al = (ArrayList) mapNegativadorCOMNegativadorMovimentoReg.get(negativadorMovimentoReg
									.getNegativadorMovimento().getNegativador());
					al.add(negativadorMovimentoReg);
				}else{
					ArrayList al = new ArrayList();
					al.add(negativadorMovimentoReg);
					mapNegativadorCOMNegativadorMovimentoReg.put(negativadorMovimentoReg.getNegativadorMovimento().getNegativador(), al);
				}
			}

			// 7.0
			it = mapNegativadorCOMNegativadorMovimentoReg.keySet().iterator();
			while(it.hasNext()){
				Negativador n = (Negativador) it.next();

				Collection collv = (ArrayList) mapNegativadorCOMNegativadorMovimentoReg.get(n);
				NegativadorContrato nc = repositorioSpcSerasa.consultarNegativadorContratoVigente(n.getId());
				NegativadorMovimento nm = new NegativadorMovimento();
				nm.setCodigoMovimento(Short.valueOf("2"));
				nm.setDataEnvio(new Date());
				nm.setDataProcessamentoEnvio(new Date());
				nm.setNumeroSequencialEnvio(nc.getNumeroSequencialEnvio() + 1);
				nm.setUltimaAlteracao(new Date());
				nm.setNegativador(n);

				repositorioUtil.inserir(nm);

				Object[] header = this.gerarRegistroTipoHeader(Integer.valueOf(0), BigDecimal.ZERO, Integer.valueOf(0), n, nm, nc, collv);
				header = this.gerarRegistroTipoDetalhe((Integer) header[0], (BigDecimal) header[1], (Integer) header[2], n, nm, nc, collv);
				header = this.gerarRegistroTipoTrailler((Integer) header[0], (BigDecimal) header[1], (Integer) header[2], n, nm, nc, collv);

				NegativadorMovimentoGeradosExclusaoHelper helper = new NegativadorMovimentoGeradosExclusaoHelper();
				helper.setDescricaoNegativador(n.getCliente().getNome());
				helper.setDataProcessamento(nm.getUltimaAlteracao());
				helper.setHoraProcessamento(nm.getUltimaAlteracao());
				helper.setNsa(nm.getNumeroSequencialEnvio() + "");
				helper.setQuantidadeRegistros((Integer) header[0] + "");
				helper.setValorDebito(gcom.util.Util.formataBigDecimal((BigDecimal) header[1], 2, true) + "");
				coll.add(helper);

				StringBuffer sb = this.gerarArquivo(nm.getId());
				// -----------------------------------------
				Date data = new Date();
				String AAAAMMDD = Util.formatarDataAAAAMMDD(data);
				String HHMM = Util.formatarDataHHMM(data);
				String formatodatahora = AAAAMMDD + "_" + HHMM;
				String nomeArquivo = "";

				if(n.equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
					nomeArquivo = "REG_SPC_SP_" + formatodatahora + ".env";
				}else if(n.equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
					nomeArquivo = "REG_SPC_BRASIL_" + formatodatahora + ".txt";
				}else if(n.equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
					formatodatahora = Util.recuperaDiaMesAnoCom2DigitosDaData(data) + Util.formatarDataHHMM(data);
					nomeArquivo = "BVCAG" + formatodatahora + ".txt";
				}else{
					nomeArquivo = "REG_SERASA_" + formatodatahora + ".txt";
				}
				this.enviarArquivoSpcSerasaParaBatch(sb, nomeArquivo, Usuario.USUARIO_BATCH);
				// ----------------------------------------------
				Integer numeroExclusoesJaEnviadas = nc.getNumeroExclusoesEnviadas();
				if(numeroExclusoesJaEnviadas == null){
					numeroExclusoesJaEnviadas = 0;
				}

				nc.setNumeroSequencialEnvio(nm.getNumeroSequencialEnvio());
				nc.setNumeroExclusoesEnviadas(numeroExclusoesJaEnviadas + ((Integer) header[2]));
				nc.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(nc);

				nm.setNumeroRegistrosEnvio((Integer) header[0]);
				nm.setValorTotalEnvio((BigDecimal) header[1]);
				nm.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(nm);

				coll.add(nm);
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return coll;
	}

	/**
	 * Método que gera o Header a ser enviado
	 * [UC0673] - Gerar Movimento de Exclusao de Negativacao
	 * [SB0004] - Gerar Registro Tipo Header
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @param n
	 *            - negativador
	 * @param nm
	 *            -negativaromovimento
	 * @param nc
	 *            - negativadorContrato
	 * @param collNegativadorMovimentoReg
	 * @return Object[]
	 *         Object[0] Integer numeroRegistro = new Integer(0);
	 *         Object[1] BigDecimal valor = BigDecimal.ZERO;
	 *         Object[2] Integer quantidadeExclusao = new Integer(0);
	 * @throws ErroRepositorioException
	 */
	private Object[] gerarRegistroTipoHeader(Integer numeroRegistro, BigDecimal valor, Integer quantidadeExclusao, Negativador n,
					NegativadorMovimento nm, NegativadorContrato nc, Collection collNegativadorMovimentoReg) throws ControladorException{

		Object[] retorno = new Object[3];
		retorno[0] = numeroRegistro + 1;
		retorno[1] = valor;
		retorno[2] = quantidadeExclusao;
		char[] registro = new char[340];
		String conteudoRegistro = "";
		Integer nnRegistroMovimentoReg = 1;

		// -----------------
		int saEnvio = nc.getNumeroSequencialEnvio();
		Integer quantidadeRegistros = (Integer) retorno[0];
		// -----------------

		String ddmmaaaa = Util.formatarDataSemBarra(new Date());
		String aaaammdd = ddmmaaaa.substring(0, 2) + ddmmaaaa.substring(2, 4) + ddmmaaaa.substring(4, 8);

		if(n.getId().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
			StringBuilder registroHeader = this.geraRegistroTipoHeaderSPCSP(saEnvio, quantidadeRegistros);
			conteudoRegistro = registroHeader.toString();
		}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
			StringBuilder registroHeader = this.geraRegistroTipoHeaderSPCBrasil(saEnvio, quantidadeRegistros);
			conteudoRegistro = registroHeader.toString();
		}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
			StringBuilder registroHeader = this.geraRegistroTipoHeaderSPCBoaVista(saEnvio);
			conteudoRegistro = registroHeader.toString();
			nnRegistroMovimentoReg = 0;
		}else if(n.getId().equals(Negativador.NEGATIVADOR_SERASA)){

			registro = new char[600];
			for(int i = 0; i < registro.length; i++){
				registro[i] = ' ';
			}

			String numeroContrato = (nc.getNumeroSequencialEnvio() + 1) + "";
			if(numeroContrato.length() < 6){
				while(numeroContrato.length() != 6){
					numeroContrato = "0" + numeroContrato;
				}
			}

			// h.01
			colocarConteudo("0", 1, registro);

			SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
			String cnpjEmpresa = sistemaParametro.getCnpjEmpresa();
			cnpjEmpresa = cnpjEmpresa.substring(0, 8);

			// h.02
			colocarConteudo(Util.adicionarZerosEsquedaNumero(9, cnpjEmpresa), 2, registro);

			// h.03
			colocarConteudo(aaaammdd, 11, registro);
			// h.04
			String ddd = (String) ParametroSpcSerasa.P_DDD_FONE_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO
							.executar(ExecutorParametrosSpcSerasa.getInstancia());
			ddd = Util.adicionarZerosEsquedaNumero(4, ddd);
			colocarConteudo(ddd, 19, registro);
			// h.05
			String numero = (String) ParametroSpcSerasa.P_NUMERO_FONE_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO
							.executar(ExecutorParametrosSpcSerasa.getInstancia());
			numero = Util.adicionarZerosEsquedaNumero(8, numero);
			colocarConteudo(numero, 23, registro);
			// h.06
			String ramal = (String) ParametroSpcSerasa.P_NUMERO_RAMAL_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO
							.executar(ExecutorParametrosSpcSerasa.getInstancia());
			ramal = Util.adicionarZerosEsquedaNumero(4, ramal);
			colocarConteudo(ramal, 31, registro);
			// h.07
			String nome = (String) ParametroSpcSerasa.P_NOME_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO
							.executar(ExecutorParametrosSpcSerasa.getInstancia());
			nome = Util.completaString(nome, 70);
			colocarConteudo(nome, 35, registro);
			// h.08
			colocarConteudo("SERASA-CONVEM04", 105, registro);
			// h.09
			colocarConteudo(numeroContrato, 120, registro);
			// h.10
			colocarConteudo("E", 126, registro);
			// h.11
			colocarConteudo("    ", 127, registro);
			// h.14
			colocarConteudo(Util.adicionarZerosEsquedaNumero(7, "" + quantidadeRegistros), 594, registro);

			conteudoRegistro = new String(registro);
		}

		FiltroNegativadorRegistroTipo fnrt = new FiltroNegativadorRegistroTipo();
		fnrt.adicionarParametro(new ParametroSimples(FiltroNegativadorRegistroTipo.NEGATIVADOR_ID, n.getId()));
		fnrt.adicionarParametro(new ParametroSimples(FiltroNegativadorRegistroTipo.CODIGO_REGISTRO, NegativadorRegistroTipo.TIPO_HEADER));

		try{
			NegativadorRegistroTipo nrt = (NegativadorRegistroTipo) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(fnrt,
							NegativadorRegistroTipo.class.getName()));

			NegativadorMovimentoReg nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(conteudoRegistro);
			nmr.setIndicadorSituacaoDefinitiva(Short.valueOf((short) 1));
			nmr.setNumeroRegistro(nnRegistroMovimentoReg);
			nmr.setUltimaAlteracao(new Date());

			repositorioUtil.inserir(nmr);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	private String getConteudo(int posicaoInicial, int tamanho, char[] registro) throws ControladorException{

		String retorno = "";
		while(retorno.length() != tamanho){
			retorno = retorno + registro[(retorno.length() + posicaoInicial) - 1];
		}
		return retorno;
	}

	private void colocarConteudo(String conteudo, int posicaoInicial, char[] registro){

		if(registro.length >= ((posicaoInicial + conteudo.length()) - 1)){
			for(int i = 0; i < conteudo.length(); i++){
				registro[posicaoInicial + i - 1] = conteudo.charAt(i);
			}
		}
	}

	/**
	 * Método que gera o Detalhe a ser enviado
	 * [UC0673] - Gerar Movimento de Exclusao de Negativacao
	 * [SB0005] - Gerar Registro Tipo Detalhe
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @param n
	 *            - negativador
	 * @param nm
	 *            -negativaromovimento
	 * @param nc
	 *            - negativadorContrato
	 * @param collNegativadorMovimentoReg
	 *            Object[0] Integer numeroRegistro = new Integer(0);
	 *            Object[1] BigDecimal valor = BigDecimal.ZERO;
	 *            Object[2] Integer quantidadeExclusao = new Integer(0);
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	private Object[] gerarRegistroTipoDetalhe(Integer numeroRegistro, BigDecimal valor, Integer quantidadeExclusao, Negativador n,
					NegativadorMovimento nm, NegativadorContrato nc, Collection collNegativadorMovimentoReg)
					throws ErroRepositorioException, ControladorException{

		Object[] retorno = new Object[3];
		retorno[0] = numeroRegistro;
		retorno[1] = valor;
		retorno[2] = quantidadeExclusao;

		// Coleção utilizada para guardar um conjunto de 3 registros consecutivos
		// para geração do detalhe para SPC Boa Vista
		// Collection<NegativadorMovimentoReg> movimentosSPCBoaVista = null;

		if(collNegativadorMovimentoReg != null && !collNegativadorMovimentoReg.isEmpty()){
			Iterator it = collNegativadorMovimentoReg.iterator();
			while(it.hasNext()){
				NegativadorMovimentoReg nmr = (NegativadorMovimentoReg) it.next();

				if(n.getId().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){

					retorno[0] = Integer.valueOf((Integer) retorno[0]) + 1;

					if(nmr.getNegativadorRegistroTipo().getId().equals(NegativadorRegistroTipo.ID_SPC_DETALHE_OCORRENCIA_SPC_SP)
									|| nmr.getNegativadorRegistroTipo().getId()
													.equals(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_SP)){

						NegativadorExclusaoMotivo nem = repositorioSpcSerasa.pesquisarNegativadorMotivoExclusao(nmr
										.getCobrancaDebitoSituacao().getId(), n.getId());

						DadosNegativacaoPorImovelHelper listaDadosImovel = new DadosNegativacaoPorImovelHelper();
						listaDadosImovel.setIdCliente(nmr.getCliente().getId());

						Cliente cliente = nmr.getCliente();

						StringBuilder conteudoRegistro = this.gerarRegistroTipoDetalheOcorrenciaSPC(quantidadeExclusao, listaDadosImovel,
										cliente, nmr.getValorDebito(), nc, ConstantesAplicacao.get("operacao_exclusao"));

						NegativadorMovimentoReg nmrExclusao = new NegativadorMovimentoReg();
						nmrExclusao.setNegativadorMovimento(nm);
						nmrExclusao.setNegativadorMovimentoRegInclusao(nmr);
						nmrExclusao.setNegativadorRegistroTipo(nmr.getNegativadorRegistroTipo());
						nmrExclusao.setConteudoRegistro(conteudoRegistro.toString());
						nmrExclusao.setUltimaAlteracao(new Date());
						nmrExclusao.setUsuario(nmr.getUsuario());
						nmrExclusao.setCodigoExclusaoTipo(nmr.getCodigoExclusaoTipo());
						nmrExclusao.setValorDebito(nmr.getValorDebito());
						nmrExclusao.setCobrancaDebitoSituacao(nmr.getCobrancaDebitoSituacao());
						nmrExclusao.setDataSituacaoDebito(nmr.getDataSituacaoDebito());
						nmrExclusao.setImovel(nmr.getImovel());
						nmrExclusao.setLocalidade(nmr.getLocalidade());
						nmrExclusao.setQuadra(nmr.getQuadra());
						nmrExclusao.setCodigoSetorComercial(nmr.getCodigoSetorComercial());
						nmrExclusao.setNumeroQuadra(nmr.getNumeroQuadra());
						nmrExclusao.setImovelPerfil(nmr.getImovelPerfil());
						nmrExclusao.setCliente(nmr.getCliente());
						nmrExclusao.setCategoria(nmr.getCategoria());
						nmrExclusao.setNumeroCpf(nmr.getNumeroCpf());
						nmrExclusao.setNumeroCnpj(nmr.getNumeroCnpj());
						nmrExclusao.setCpfTipo(nmr.getCpfTipo());
						nmrExclusao.setIndicadorSituacaoDefinitiva(Short.valueOf((short) 1));
						nmrExclusao.setNumeroRegistro(((Integer) retorno[0]));
						nmrExclusao.setNumeroContrato(nmr.getNumeroContrato());

						repositorioUtil.inserir(nmrExclusao);

						retorno[1] = new BigDecimal(((BigDecimal) retorno[1]).doubleValue() + nmr.getValorDebito().doubleValue());
						retorno[2] = new Integer((Integer) retorno[2]) + 1;

						atualizarNegativacao(n.getId(), nmr, nem);
					}
				}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BRASIL)){

					retorno[0] = new Integer((Integer) retorno[0]) + 1;

					// geradno 6 digitos do numero de registro
					String sNumeroRegistro = ((Integer) retorno[0]).toString();
					if(sNumeroRegistro.length() < 6){
						while(sNumeroRegistro.length() != 6){
							sNumeroRegistro = "0" + sNumeroRegistro;
						}
					}

					if(nmr.getNegativadorRegistroTipo().getId().equals(NegativadorRegistroTipo.ID_SPC_DETALHE_SPC_BR)){

						// Gerar Registro de Exclusão Tipo Detalhe-Consumidor
						this.gerarNegMovRegDetalheConsumidor(nmr, nm, sNumeroRegistro);

						retorno[0] = new Integer((Integer) retorno[0]) + 1;

						// geradno 6 digitos do numero de registro
						sNumeroRegistro = ((Integer) retorno[0]).toString();
						if(sNumeroRegistro.length() < 6){
							while(sNumeroRegistro.length() != 6){
								sNumeroRegistro = "0" + sNumeroRegistro;
							}
						}

						NegativadorExclusaoMotivo nem = repositorioSpcSerasa.pesquisarNegativadorMotivoExclusao(nmr
										.getCobrancaDebitoSituacao().getId(), n.getId());

						char[] registro = new char[340];
						for(int i = 0; i < registro.length; i++){
							registro[i] = ' ';
						}

						char[] registroInclusao = new char[340];
						for(int i = 0; i < registroInclusao.length; i++){
							registroInclusao[i] = ' ';
						}
						if(nmr.getConteudoRegistro() != null){
							for(int i = 0; i < nmr.getConteudoRegistro().length(); i++){
								if(i <= registroInclusao.length){
									registroInclusao[i] = nmr.getConteudoRegistro().toCharArray()[i];
								}
							}
						}

						// h.01
						if(registroInclusao != null && registroInclusao.length > 3) colocarConteudo(getConteudo(1, 2, registroInclusao), 1,
										registro);
						// h.02
						if(registroInclusao != null && registroInclusao.length > 4) colocarConteudo(getConteudo(3, 1, registroInclusao), 3,
										registro);
						// h.03
						if(registroInclusao != null && registroInclusao.length > 19) colocarConteudo(getConteudo(4, 15, registroInclusao),
										4, registro);
						// h.04
						colocarConteudo("E", 19, registro);
						// h.05
						if(registroInclusao != null && registroInclusao.length > 21) colocarConteudo(getConteudo(20, 1, registroInclusao),
										20, registro);
						// h.06
						if(registroInclusao != null && registroInclusao.length > 29) colocarConteudo(getConteudo(21, 8, registroInclusao),
										21, registro);
						// h.07
						if(registroInclusao != null && registroInclusao.length > 27) colocarConteudo(getConteudo(29, 8, registroInclusao),
										29, registro);
						// h.08
						if(registroInclusao != null && registroInclusao.length > 49) colocarConteudo(getConteudo(37, 13, registroInclusao),
										37, registro);
						// h.09
						if(registroInclusao != null && registroInclusao.length > 80) colocarConteudo(getConteudo(50, 30, registroInclusao),
										50, registro);
						// h.10
						if(registroInclusao != null && registroInclusao.length > 88) colocarConteudo(getConteudo(80, 8, registroInclusao),
										80, registro);
						// h.11
						if(registroInclusao != null && registroInclusao.length > 90) colocarConteudo(getConteudo(88, 2, registroInclusao),
										88, registro);

						// h.12
						String codigoExclusaoMotivo = nem.getCodigoExclusaoMotivo() + "";
						if(codigoExclusaoMotivo.length() < 3){
							while(codigoExclusaoMotivo.length() != 3){
								codigoExclusaoMotivo = "0" + codigoExclusaoMotivo;
							}
						}

						colocarConteudo(codigoExclusaoMotivo, 90, registro);
						// h.13
						if(registroInclusao != null && registroInclusao.length > 2) colocarConteudo(getConteudo(93, 232, registroInclusao),
										325, registro);
						// h.14
						if(registroInclusao != null && registroInclusao.length > 335) colocarConteudo(
										getConteudo(325, 10, registroInclusao), 325, registro);
						// h.15
						colocarConteudo(sNumeroRegistro, 335, registro);

						NegativadorMovimentoReg nmrExclusao = new NegativadorMovimentoReg();
						nmrExclusao.setNegativadorMovimento(nm);
						nmrExclusao.setNegativadorMovimentoRegInclusao(nmr);
						nmrExclusao.setNegativadorRegistroTipo(nmr.getNegativadorRegistroTipo());
						nmrExclusao.setConteudoRegistro(new String(registro));
						nmrExclusao.setUltimaAlteracao(new Date());
						nmrExclusao.setUsuario(nmr.getUsuario());
						nmrExclusao.setCodigoExclusaoTipo(nmr.getCodigoExclusaoTipo());
						nmrExclusao.setValorDebito(nmr.getValorDebito());
						nmrExclusao.setCobrancaDebitoSituacao(nmr.getCobrancaDebitoSituacao());
						nmrExclusao.setDataSituacaoDebito(nmr.getDataSituacaoDebito());
						nmrExclusao.setImovel(nmr.getImovel());
						nmrExclusao.setLocalidade(nmr.getLocalidade());
						nmrExclusao.setQuadra(nmr.getQuadra());
						nmrExclusao.setCodigoSetorComercial(nmr.getCodigoSetorComercial());
						nmrExclusao.setNumeroQuadra(nmr.getNumeroQuadra());
						nmrExclusao.setImovelPerfil(nmr.getImovelPerfil());
						nmrExclusao.setCliente(nmr.getCliente());
						nmrExclusao.setCategoria(nmr.getCategoria());
						nmrExclusao.setNumeroCpf(nmr.getNumeroCpf());
						nmrExclusao.setNumeroCnpj(nmr.getNumeroCnpj());
						nmrExclusao.setCpfTipo(nmr.getCpfTipo());
						nmrExclusao.setIndicadorSituacaoDefinitiva(new Short((short) 1));
						nmrExclusao.setNumeroRegistro(((Integer) retorno[0]));

						// Inserir nmrExclusao
						RepositorioUtilHBM.getInstancia().inserir(nmrExclusao);
						// inicio do 7

						retorno[1] = new BigDecimal(((BigDecimal) retorno[1]).doubleValue() + nmr.getValorDebito().doubleValue());
						retorno[2] = new Integer((Integer) retorno[2]) + 1;

						atualizarNegativacao(n.getId(), nmr, nem);

					}
				}else if(n.getId().equals(Negativador.NEGATIVADOR_SERASA)){

					NegativadorExclusaoMotivo nem = repositorioSpcSerasa.pesquisarNegativadorMotivoExclusao(nmr.getCobrancaDebitoSituacao()
									.getId(), n.getId());

					char[] registro = new char[600];
					for(int i = 0; i < registro.length; i++){
						registro[i] = ' ';
					}

					char[] registroInclusao = new char[600];
					for(int i = 0; i < registroInclusao.length; i++){
						registroInclusao[i] = ' ';
					}

					if(nmr.getConteudoRegistro() != null){
						for(int i = 0; i < nmr.getConteudoRegistro().length(); i++){
							if(i <= registroInclusao.length){
								registroInclusao[i] = nmr.getConteudoRegistro().toCharArray()[i];
							}
						}
					}

					String codigoIdExclusaoMotivo = "";
					if(nem != null && nem.getCodigoExclusaoMotivo() != null){

						codigoIdExclusaoMotivo = nem.getCodigoExclusaoMotivo().toString();
						if(codigoIdExclusaoMotivo.length() < 2){
							while(codigoIdExclusaoMotivo.length() != 2){
								codigoIdExclusaoMotivo = "0" + codigoIdExclusaoMotivo;
							}
						}
					}


					retorno[0] = Integer.valueOf((Integer) retorno[0]) + 1;
					String sNumeroRegistro = ((Integer) retorno[0]).toString();
					if(sNumeroRegistro.length() < 7){
						while(sNumeroRegistro.length() != 7){
							sNumeroRegistro = "0" + sNumeroRegistro;
						}
					}


					
					// D.01
					colocarConteudo("1", 1, registro);
					// D.02
					colocarConteudo("E", 2, registro);
					

					if(nmr.getNumeroCpf() != null){

						// D.08 32 32 CHAR(1)* Caso o CNPJ do Cliente da Negativação esteja com o
						// valor diferente de nulo, J; caso contrário, F.
						colocarConteudo("F", 32, registro);

						// D.09 33 33 CHAR(1)* Caso o CNPJ do Cliente da Negativação esteja com o
						// valor diferente de nulo, 1; caso contrário, 2.
						colocarConteudo("2", 33, registro);

						// D.10
						String numeroCpf = Util.completarStringZeroEsquerda(nmr.getNumeroCpf(), 15);
						colocarConteudo(numeroCpf, 34, registro);
					}else if(nmr.getNumeroCnpj() != null){

						// D.08 32 32 CHAR(1)* Caso o CNPJ do Cliente da Negativação esteja com o
						// valor diferente de nulo, J; caso contrário, F.
						colocarConteudo("J", 32, registro);

						// D.09 33 33 CHAR(1)* Caso o CNPJ do Cliente da Negativação esteja com o
						// valor diferente de nulo, 1; caso contrário, 2.
						colocarConteudo("1", 33, registro);

						// D.10
						String numeroCnpj = Util.completarStringZeroEsquerda(nmr.getNumeroCnpj(), 15);
						colocarConteudo(numeroCnpj, 34, registro);
					}
					
					
					// D.11
					colocarConteudo(codigoIdExclusaoMotivo, 49, registro);
					// D.12
					colocarConteudo(getConteudo(51, 1, registroInclusao), 51, registro);
					// D.13
					colocarConteudo(getConteudo(52, 15, registroInclusao), 52, registro);
					// D.14
					colocarConteudo(getConteudo(67, 2, registroInclusao), 67, registro);
					// D.22
					colocarConteudo(getConteudo(106, 70, registroInclusao), 106, registro);
					// D.23
					colocarConteudo(getConteudo(176, 8, registroInclusao), 176, registro);
					// D.25
					colocarConteudo(getConteudo(254, 70, registroInclusao), 254, registro);
					// D.32
					colocarConteudo(Util.adicionarZerosEsquedaNumero(16, nmr.getNumeroContrato().toString()), 439, registro);
					// D.41
					colocarConteudo(sNumeroRegistro, 594, registro);

					NegativadorMovimentoReg nmrExclusao = new NegativadorMovimentoReg();
					nmrExclusao.setNegativadorMovimento(nm);
					nmrExclusao.setNegativadorMovimentoRegInclusao(nmr);
					nmrExclusao.setNegativadorRegistroTipo(nmr.getNegativadorRegistroTipo());
					nmrExclusao.setConteudoRegistro(new String(registro));
					nmrExclusao.setUltimaAlteracao(new Date());
					nmrExclusao.setUsuario(nmr.getUsuario());
					nmrExclusao.setCodigoExclusaoTipo(nmr.getCodigoExclusaoTipo());
					nmrExclusao.setValorDebito(nmr.getValorDebito());
					nmrExclusao.setCobrancaDebitoSituacao(nmr.getCobrancaDebitoSituacao());
					nmrExclusao.setDataSituacaoDebito(nmr.getDataSituacaoDebito());
					nmrExclusao.setImovel(nmr.getImovel());
					nmrExclusao.setLocalidade(nmr.getLocalidade());
					nmrExclusao.setQuadra(nmr.getQuadra());
					nmrExclusao.setCodigoSetorComercial(nmr.getCodigoSetorComercial());
					nmrExclusao.setNumeroQuadra(nmr.getNumeroQuadra());
					nmrExclusao.setImovelPerfil(nmr.getImovelPerfil());
					nmrExclusao.setCliente(nmr.getCliente());
					nmrExclusao.setCategoria(nmr.getCategoria());
					nmrExclusao.setNumeroCpf(nmr.getNumeroCpf());
					nmrExclusao.setNumeroCnpj(nmr.getNumeroCnpj());
					nmrExclusao.setCpfTipo(nmr.getCpfTipo());
					nmrExclusao.setIndicadorSituacaoDefinitiva(Short.valueOf((short) 1));
					nmrExclusao.setNumeroRegistro(((Integer) retorno[0]));
					nmrExclusao.setNumeroContrato(nmr.getNumeroContrato());

					repositorioUtil.inserir(nmrExclusao);

					retorno[1] = new BigDecimal(((BigDecimal) retorno[1]).doubleValue() + nmr.getValorDebito().doubleValue());
					retorno[2] = Integer.valueOf((Integer) retorno[2]) + 1;

					atualizarNegativacao(n.getId(), nmr, nem);

				}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){

					// Incrementa o número de registros
					retorno[0] = Integer.valueOf((Integer) retorno[0]) + 1;

					NegativadorMovimentoReg detalheConsumidorOcorrencia = null;
					NegativadorMovimentoReg detalheConsumidorNome = null;
					NegativadorMovimentoReg detalheConsumidorEndereco = null;
					String codigoRegistro = null;

					// Identifica cada tipo de movimento negativador do SPC Boa Vista,
					// de acordo com o trecho de 15 a 16 do campo nmrg_cnregistro
					// for(NegativadorMovimentoReg movimentoReg : movimentosSPCBoaVista){
					if(!Util.isVazioOuBranco(nmr.getConteudoRegistro()) && nmr.getConteudoRegistro().length() >= 16){
						codigoRegistro = nmr.getConteudoRegistro().substring(14, 16);

						if(!Util.isVazioOuBranco(codigoRegistro)){
							detalheConsumidorOcorrencia = nmr;
						}
					}

					Collection<Integer> colecaoIds = new ArrayList<Integer>();
					colecaoIds.add(nmr.getId() - 1);
					colecaoIds.add(nmr.getId() - 2);

					FiltroNegativadorMovimentoReg filtroNegativadorMovimentoReg = new FiltroNegativadorMovimentoReg();
					filtroNegativadorMovimentoReg.adicionarParametro(new ParametroSimplesColecao(FiltroNegativadorMovimentoReg.ID,
									colecaoIds));

					Collection<NegativadorMovimentoReg> colecaoNegativadorMovimentoReg = this.getControladorUtil().pesquisar(
									filtroNegativadorMovimentoReg, NegativadorMovimentoReg.class.getName());

					for(NegativadorMovimentoReg movimentoReg : colecaoNegativadorMovimentoReg){
						if(!Util.isVazioOuBranco(movimentoReg.getConteudoRegistro()) && movimentoReg.getConteudoRegistro().length() >= 16){
							codigoRegistro = movimentoReg.getConteudoRegistro().substring(14, 16);

							if(!Util.isVazioOuBranco(codigoRegistro)){
								if(codigoRegistro.equals(ConstantesAplicacao.get("spc_boa_vista_registro_nome"))){
									detalheConsumidorNome = movimentoReg;
								}else if(codigoRegistro.equals(ConstantesAplicacao.get("spc_boa_vista_registro_endereco"))){
									detalheConsumidorEndereco = movimentoReg;
								}
							}
						}
					}

					if(detalheConsumidorOcorrencia == null || detalheConsumidorNome == null || detalheConsumidorEndereco == null){

						if(detalheConsumidorOcorrencia == null){
							throw new ControladorException("atencao.movimento_spc_ocorrencia");
						}

						if(detalheConsumidorNome == null){
							throw new ControladorException("atencao.movimento_spc_nome");
						}

						if(detalheConsumidorEndereco == null){
							throw new ControladorException("atencao.movimento_spc_endereco");
						}
					}
					
					NegativadorExclusaoMotivo nem = repositorioSpcSerasa.pesquisarNegativadorMotivoExclusao(detalheConsumidorOcorrencia
									.getCobrancaDebitoSituacao().getId(), n.getId());

					// Exclui o registro tipo "Detalhe-Consumidor-Nome"
					detalheConsumidorNome.setCodigoExclusaoTipo(Integer.valueOf(1));
					detalheConsumidorNome.setIndicadorSituacaoDefinitiva(Short.valueOf("1"));
					detalheConsumidorNome.setCobrancaSituacao(null);
					detalheConsumidorNome.setNegativadorExclusaoMotivo(nem);
					detalheConsumidorNome.setUltimaAlteracao(new Date());
					repositorioUtil.atualizar(detalheConsumidorNome);

					// Exclui o registro tipo "Detalhe-Consumidor-Endereço"
					detalheConsumidorEndereco.setCodigoExclusaoTipo(Integer.valueOf(1));
					detalheConsumidorEndereco.setIndicadorSituacaoDefinitiva(Short.valueOf("1"));
					detalheConsumidorEndereco.setCobrancaSituacao(null);
					detalheConsumidorEndereco.setNegativadorExclusaoMotivo(nem);
					detalheConsumidorEndereco.setUltimaAlteracao(new Date());
					repositorioUtil.atualizar(detalheConsumidorEndereco);

					// Formata o conteúdo do registro tipo "Detalhe_Consumidor_Ocorrência" para
					// gravação no conteúdo do registro do movimento de exclusão da negativação
					StringBuilder conteudoRegistro = this.gerarRegistroTipoDetalheOcorrenciaSPCBoaVista(
									detalheConsumidorOcorrencia.getConteudoRegistro(), quantidadeExclusao);

					NegativadorMovimentoReg nmrExclusao = new NegativadorMovimentoReg();
					nmrExclusao.setNegativadorMovimento(nm);
					nmrExclusao.setNegativadorMovimentoRegInclusao(detalheConsumidorOcorrencia);
					nmrExclusao.setNegativadorRegistroTipo(detalheConsumidorOcorrencia.getNegativadorRegistroTipo());
					nmrExclusao.setConteudoRegistro(Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(conteudoRegistro.toString(),
									250));
					nmrExclusao.setUltimaAlteracao(new Date());
					nmrExclusao.setUsuario(detalheConsumidorOcorrencia.getUsuario());
					nmrExclusao.setCodigoExclusaoTipo(detalheConsumidorOcorrencia.getCodigoExclusaoTipo());
					nmrExclusao.setValorDebito(detalheConsumidorOcorrencia.getValorDebito());
					nmrExclusao.setCobrancaDebitoSituacao(detalheConsumidorOcorrencia.getCobrancaDebitoSituacao());
					nmrExclusao.setDataSituacaoDebito(detalheConsumidorOcorrencia.getDataSituacaoDebito());
					nmrExclusao.setImovel(detalheConsumidorOcorrencia.getImovel());
					nmrExclusao.setLocalidade(detalheConsumidorOcorrencia.getLocalidade());
					nmrExclusao.setQuadra(detalheConsumidorOcorrencia.getQuadra());
					nmrExclusao.setCodigoSetorComercial(detalheConsumidorOcorrencia.getCodigoSetorComercial());
					nmrExclusao.setNumeroQuadra(detalheConsumidorOcorrencia.getNumeroQuadra());
					nmrExclusao.setImovelPerfil(detalheConsumidorOcorrencia.getImovelPerfil());
					nmrExclusao.setCliente(detalheConsumidorOcorrencia.getCliente());
					nmrExclusao.setCategoria(detalheConsumidorOcorrencia.getCategoria());
					nmrExclusao.setNumeroCpf(detalheConsumidorOcorrencia.getNumeroCpf());
					nmrExclusao.setNumeroCnpj(detalheConsumidorOcorrencia.getNumeroCnpj());
					nmrExclusao.setCpfTipo(detalheConsumidorOcorrencia.getCpfTipo());
					nmrExclusao.setIndicadorSituacaoDefinitiva(Short.valueOf((short) 1));
					nmrExclusao.setNumeroRegistro(((Integer) retorno[0]));
					nmrExclusao.setNumeroContrato(detalheConsumidorOcorrencia.getNumeroContrato());

					// Gera o registro do movimento de exclusão da negativação correspondente ao
					// registro tipo "Detalhe_Consumidor_Ocorrência"
					repositorioUtil.inserir(nmrExclusao);

					// Incrementa a quantidade de exclusões e acumula o valor total de débitos
					retorno[1] = new BigDecimal(((BigDecimal) retorno[1]).doubleValue()
									+ detalheConsumidorOcorrencia.getValorDebito().doubleValue());
					retorno[2] = Integer.valueOf((Integer) retorno[2]) + 1;

					// Atualiza os dados da negativação excluída
					atualizarNegativacao(n.getId(), detalheConsumidorOcorrencia, nem);

				}
			}
		}

		return retorno;
	}

	/**
	 * @author Thiago Toscano
	 * @date 04/01/2008
	 * @param tipoNegativador
	 *            (1 para spc; 2 para serasa)
	 * @param nmr
	 * @param nem
	 * @throws ErroRepositorioException
	 */
	private void atualizarNegativacao(Integer codigoNegativador, NegativadorMovimentoReg nmr, NegativadorExclusaoMotivo nem)
					throws ErroRepositorioException{

		nmr.setCodigoExclusaoTipo(Integer.valueOf(1));
		nmr.setIndicadorSituacaoDefinitiva(Short.valueOf((short) 1));
		nmr.setNegativadorExclusaoMotivo(nem);
		nmr.setUltimaAlteracao(new Date());

		repositorioUtil.atualizar(nmr);

		Collection collItens = repositorioSpcSerasa.pesquisarNegativadorMovimentoRegItens(nmr.getId());
		if(collItens != null && !collItens.isEmpty()){
			Collection colecaoNegMovRegItem = new ArrayList<NegativadorMovimentoRegItem>();
			Iterator itt = collItens.iterator();
			while(itt.hasNext()){
				NegativadorMovimentoRegItem nmri = (NegativadorMovimentoRegItem) itt.next();
				nmri.setCobrancaDebitoSituacaoAposExclusao(nmri.getCobrancaDebitoSituacao());
				nmri.setDataSituacaoDebitoAposExclusao(nmri.getDataSituacaoDebito());
				// repositorioUtil.atualizar(nmri);
				colecaoNegMovRegItem.add(nmri);
			}
			RepositorioBatchHBM.getInstancia().atualizarColecaoObjetoParaBatch(colecaoNegMovRegItem);
		}

		Collection coll = null;
		Integer idComando = null;
		Integer idCronogarama = null;
		if(nmr.getNegativadorMovimento().getCobrancaAcaoAtividadeComando() != null){
			idComando = nmr.getNegativadorMovimento().getCobrancaAcaoAtividadeComando().getId();
		}else if(nmr.getNegativadorMovimento().getCobrancaAcaoAtividadeCronograma() != null){
			idCronogarama = nmr.getNegativadorMovimento().getCobrancaAcaoAtividadeCronograma().getId();
		}
		coll = repositorioSpcSerasa.pesquisarNegativacaoImovel(nmr.getImovel().getId(), idCronogarama, idComando);

		if(coll != null && !coll.isEmpty()){
			Iterator itt = coll.iterator();
			while(itt.hasNext()){
				NegativacaoImovei ni = (NegativacaoImovei) itt.next();
				ni.setIndicadorExcluido(Short.valueOf((short) 1));
				ni.setDataExclusao(new Date());
				ni.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(ni);

				// sb7.7
				if(nmr.getImovel() != null){
					FiltroImovelCobrancaSituacao fics = new FiltroImovelCobrancaSituacao();
					fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, nmr.getImovel().getId()));
					fics.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));

					if(codigoNegativador.equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.CARTA_ENVIADA_AO_SPC_SP, ConectorOr.CONECTOR_OR, 3));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP, ConectorOr.CONECTOR_OR));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP));
					}else if(codigoNegativador.equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BRASIL, ConectorOr.CONECTOR_OR, 3));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL, ConectorOr.CONECTOR_OR));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL));
					}else if(codigoNegativador.equals(Negativador.NEGATIVADOR_SERASA)){
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.CARTA_ENVIADA_A_SERASA, ConectorOr.CONECTOR_OR, 3));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NA_SERASA, ConectorOr.CONECTOR_OR));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA));
					}else if(codigoNegativador.equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BOA_VISTA, ConectorOr.CONECTOR_OR, 3));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BOA_VISTA, ConectorOr.CONECTOR_OR));
						fics.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
										CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA));
					}

					Collection collICS = repositorioUtil.pesquisar(fics, ImovelCobrancaSituacao.class.getName());

					if(collICS != null && !collICS.isEmpty()){
						Iterator ittt = collICS.iterator();
						while(ittt.hasNext()){
							ImovelCobrancaSituacao ics = (ImovelCobrancaSituacao) ittt.next();
							ics.setDataRetiradaCobranca(new Date());
							ics.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(ics);
						}
					}
				}
				if(nmr.getImovel() != null && nmr.getImovel().getCobrancaSituacao() != null){
					// sb7 - 7
					if(nmr.getImovel().getCobrancaSituacao().getId().equals(CobrancaSituacao.CARTA_ENVIADA_AO_SPC_SP)
									|| nmr.getImovel().getCobrancaSituacao().getId()
													.equals(CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP)
									|| nmr.getImovel().getCobrancaSituacao().getId()
													.equals(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP)
									|| nmr.getImovel().getCobrancaSituacao().getId().equals(CobrancaSituacao.CARTA_ENVIADA_A_SERASA)
									|| nmr.getImovel().getCobrancaSituacao().getId()
													.equals(
													CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NA_SERASA)
									|| nmr.getImovel().getCobrancaSituacao().getId().equals(
													CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA)
									|| nmr.getImovel().getCobrancaSituacao().getId().equals(CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BRASIL)
									|| nmr.getImovel().getCobrancaSituacao().getId().equals(
													CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL)
									|| nmr.getImovel().getCobrancaSituacao().getId()
													.equals(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL)
									|| nmr.getImovel().getCobrancaSituacao().getId()
													.equals(CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BOA_VISTA)
									|| nmr.getImovel().getCobrancaSituacao().getId()
													.equals(CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BOA_VISTA)
									|| nmr.getImovel().getCobrancaSituacao().getId()
													.equals(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA)

					){
						// sb7 - 7.1
						if(coll.size() == 1){
							nmr.getImovel().setCobrancaSituacao(null);
							nmr.getImovel().setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(nmr.getImovel());
						}

					}
				}
			}

		}
	}

	/**
	 * Método que gera o trailler a ser enviado
	 * [UC0673] - Gerar Movimento de Exclusao de Negativacao
	 * [SB0008] - Gerar Registro Tipo trailler
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @param n
	 *            - negativador
	 * @param nm
	 *            -negativaromovimento
	 * @param nc
	 *            - negativadorContrato
	 * @param collNegativadorMovimentoReg
	 *            Object[0] Integer numeroRegistro = new Integer(0);
	 *            Object[1] BigDecimal valor = BigDecimal.ZERO;
	 *            Object[2] Integer quantidadeExclusao = new Integer(0);
	 * @throws ErroRepositorioException
	 */
	private Object[] gerarRegistroTipoTrailler(Integer numeroRegistro, BigDecimal valor, Integer quantidadeExclusao, Negativador n,
					NegativadorMovimento nm, NegativadorContrato nc, Collection collNegativadorMovimentoReg)
					throws ErroRepositorioException{

		Object[] retorno = new Object[3];
		retorno[0] = numeroRegistro + 1;
		retorno[1] = valor;
		retorno[2] = quantidadeExclusao;

		Integer quantidade = (Integer) retorno[0];
		String conteudoRegistro = "";

		if(n.getId().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){

			try{
				StringBuilder conteudoRegistroTrailler = this.geraRegistroTipoTraillerSPCSP(quantidadeExclusao);
				conteudoRegistro = conteudoRegistroTrailler.toString();
			}catch(ControladorException e){
				e.printStackTrace();
			}

		}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BRASIL)){

			try{
				StringBuilder conteudoRegistroTrailler = this.geraRegistroTipoTraillerSPCBrasil(quantidade);
				conteudoRegistro = conteudoRegistroTrailler.toString();
			}catch(ControladorException e){
				e.printStackTrace();
			}

		}else if(n.getId().equals(Negativador.NEGATIVADOR_SERASA)){

			char[] registro = new char[600];
			for(int i = 0; i < registro.length; i++){
				registro[i] = ' ';
			}

			// h.01
			colocarConteudo("9", 1, registro);
			colocarConteudo(Util.adicionarZerosEsquedaNumero(7, "" + quantidade), 594, registro);

			conteudoRegistro = new String(registro);

		}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){

			try{
				StringBuilder conteudoRegistroTrailler = this.geraRegistroTipoTraillerSPCBoaVista(quantidadeExclusao);
				conteudoRegistro = conteudoRegistroTrailler.toString();
			}catch(ControladorException e){
				e.printStackTrace();
			}

		}

		FiltroNegativadorRegistroTipo fnrt = new FiltroNegativadorRegistroTipo();
		fnrt.adicionarParametro(new ParametroSimples(FiltroNegativadorRegistroTipo.NEGATIVADOR_ID, n.getId()));
		fnrt.adicionarParametro(new ParametroSimples(FiltroNegativadorRegistroTipo.CODIGO_REGISTRO, NegativadorRegistroTipo.TIPO_TRAILLER));

		NegativadorRegistroTipo nrt = (NegativadorRegistroTipo) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(fnrt,
						NegativadorRegistroTipo.class.getName()));

		NegativadorMovimentoReg nmr = new NegativadorMovimentoReg();
		nmr.setNegativadorMovimento(nm);
		nmr.setNegativadorRegistroTipo(nrt);

		nmr.setConteudoRegistro(conteudoRegistro);

		nmr.setIndicadorSituacaoDefinitiva(Short.valueOf((short) 1));
		nmr.setNumeroRegistro((Integer) retorno[0]);
		nmr.setUltimaAlteracao(new Date());
		repositorioUtil.inserir(nmr);

		return retorno;
	}

	/**
	 * Método consuta os Negativadores que tenham movimento de Exclusão do spc ou serasa
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0002] - Gerar TxT de Movimento de Exclusão de Negativacao
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 */
	public Collection gerarArquivoTXTMovimentoExclusaoNegativacao(Integer idMovimento) throws ControladorException{

		try{
			Collection retorno = new ArrayList();

			FiltroNegativadorMovimento fnm = new FiltroNegativadorMovimento();
			fnm.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimento.ID, idMovimento));
			fnm.adicionarCaminhoParaCarregamentoEntidade("negativador.cliente");
			Collection coll = repositorioUtil.pesquisar(fnm, NegativadorMovimento.class.getName());
			if(coll != null && !coll.isEmpty()){
				NegativadorMovimento nm = (NegativadorMovimento) coll.iterator().next();

				NegativadorMovimentoGeradosExclusaoHelper helper = new NegativadorMovimentoGeradosExclusaoHelper();
				helper.setDescricaoNegativador(nm.getNegativador().getCliente().getNome());
				helper.setDataProcessamento(nm.getUltimaAlteracao());
				helper.setHoraProcessamento(nm.getUltimaAlteracao());
				helper.setNsa(nm.getNumeroSequencialEnvio() + "");
				helper.setQuantidadeRegistros(nm.getNumeroRegistrosEnvio() + "");
				helper.setValorDebito(gcom.util.Util.formataBigDecimal(nm.getValorTotalEnvio(), 2, true) + "");
				retorno.add(helper);
			}

			return retorno;
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Método privado que gera uma string buffer que representa o arquivo dos movimentos de exclusao
	 * de negativacao
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0009] - Gerar Arquivo TxT para Envio ao Negativador
	 * 
	 * @author Thiago Toscano
	 * @date 27/12/2007
	 * @param idMovimento
	 * @return o arquivo
	 * @throws ErroRepositorioException
	 */
	private StringBuffer gerarArquivo(Integer idMovimento) throws ErroRepositorioException{

		StringBuffer sb = new StringBuffer();
		FiltroNegativadorMovimento fnm = new FiltroNegativadorMovimento();
		fnm.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimento.ID, idMovimento));
		Collection coll = repositorioUtil.pesquisar(fnm, NegativadorMovimento.class.getName());
		if(coll != null && !coll.isEmpty()){
			coll = repositorioSpcSerasa.consultarNegativadorMovimentoRegistroParaGerarArquivo(idMovimento, "H");
			if(coll != null && !coll.isEmpty()){
				NegativadorMovimentoReg nmr = (NegativadorMovimentoReg) coll.iterator().next();
				sb.append(nmr.getConteudoRegistro());
				sb.append("\n");
			}
			coll = repositorioSpcSerasa.consultarNegativadorMovimentoRegistroParaGerarArquivo(idMovimento, "D");
			if(coll != null && !coll.isEmpty()){
				Iterator it = coll.iterator();
				while(it.hasNext()){
					NegativadorMovimentoReg nmr = (NegativadorMovimentoReg) it.next();
					sb.append(nmr.getConteudoRegistro());
					sb.append("\n");
				}
			}
			coll = repositorioSpcSerasa.consultarNegativadorMovimentoRegistroParaGerarArquivo(idMovimento, "T");
			if(coll != null && !coll.isEmpty()){
				NegativadorMovimentoReg nmr = (NegativadorMovimentoReg) coll.iterator().next();
				sb.append(nmr.getConteudoRegistro());
				sb.append("\n");
			}
		}
		return sb;
	}

	/**
	 * Método usado para pesquisa de Comando Negativação (Helper)
	 * [UC0655] Filtrar Comando Negativação
	 * 
	 * @author Thiago Vieira
	 * @date 02/01/2008
	 * @param comandoNegativacaoHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoHelper(ComandoNegativacaoHelper comandoNegativacaoHelper) throws ControladorException{

		Collection pesquisaComandoNegativacao = new ArrayList();
		Collection<ComandoNegativacaoHelper> retorno = new ArrayList<ComandoNegativacaoHelper>();

		try{

			pesquisaComandoNegativacao = repositorioSpcSerasa.pesquisarComandoNegativacaoHelper(comandoNegativacaoHelper);

			if(pesquisaComandoNegativacao != null && !pesquisaComandoNegativacao.isEmpty()){

				Iterator comandoNegativacaoIterator = pesquisaComandoNegativacao.iterator();

				while(comandoNegativacaoIterator.hasNext()){
					ComandoNegativacaoHelper comandoNegativacao = new ComandoNegativacaoHelper();
					Object[] dadosComandoNegativacao = (Object[]) comandoNegativacaoIterator.next();

					if(dadosComandoNegativacao[0] != null){
						comandoNegativacao.setIdComandoNegativacao((Integer) dadosComandoNegativacao[0]);
					}
					if(dadosComandoNegativacao[1] != null){
						comandoNegativacao.setTituloComando((String) dadosComandoNegativacao[1]);
					}
					if(dadosComandoNegativacao[2] != null){
						comandoNegativacao.setIndicadorComandoSimulado((Short) dadosComandoNegativacao[2]);
					}
					if(dadosComandoNegativacao[3] != null){
						comandoNegativacao.setGeracaoComandoInicio((Date) dadosComandoNegativacao[3]);
					}
					if(dadosComandoNegativacao[4] != null){
						comandoNegativacao.setExecucaoComandoInicio((Date) dadosComandoNegativacao[4]);
					}
					if(dadosComandoNegativacao[5] != null){
						comandoNegativacao.setQuantidadeInclusoes((Integer) dadosComandoNegativacao[5]);
					}
					if(dadosComandoNegativacao[6] != null){
						comandoNegativacao.setNomeUsuarioResponsavel((String) dadosComandoNegativacao[6]);
					}
					if(dadosComandoNegativacao[7] != null){
						comandoNegativacao.setNomeClienteNegativador((String) dadosComandoNegativacao[7]);
					}
					retorno.add(comandoNegativacao);
				}
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * [UC0651] Inserir Comando Negativação [FS0015] Verificar existência de negativação para o
	 * imóvel no negativador
	 * 
	 * @author Ana Maria
	 * @date 04/12/2007
	 * @param idNegativador
	 * @param idImovel
	 * @return Boolean
	 * @throws ErroRepositorioException
	 */
	public Boolean verificarExistenciaNegativacaoImovel(Integer idNegativador, Integer idImovel) throws ControladorException{

		try{
			return repositorioSpcSerasa.verificarExistenciaNegativacaoImovel(idNegativador, idImovel);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0651] Inserir Comando Negativação
	 * [SB0003] Determinar Data Prevista para Execução do Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarUltimaDataRealizacaoComando(idNegativador);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0365] Inserir Comando Negativação
	 * [SB0004] Inclui Comando de Negativação por critério
	 * 
	 * @author Ana Maria
	 * @date 13/12/2007
	 * @throws ControladorException
	 */
	public Integer inserirComandoNegativacao(InserirComandoNegativacaoPorCriterioHelper helper) throws ControladorException{

		Integer retorno = null;

		try{
			// [FS0014]- Verificar existência de comando para os mesmos parâmetros
			String nomeNegativacaoComando = repositorioSpcSerasa.verificarExistenciaComandoMesmoParametro(helper);

			if(nomeNegativacaoComando != null && !nomeNegativacaoComando.equals("")){
				throw new ControladorException("atencao.comando_nao_realizado_mesmo_parametro", null, nomeNegativacaoComando);
			}

			// Incluir Comando Negativacao
			NegativacaoComando negativacaoComando = helper.getNegativacaoComando();
			negativacaoComando.setUltimaAlteracao(new Date());

			Integer idNegativacaoComandoGerado = (Integer) this.getControladorUtil().inserir(negativacaoComando);
			negativacaoComando.setId(idNegativacaoComandoGerado);

			// Incluir Negativacao Criterio
			NegativacaoCriterio negativacaoCriterio = helper.getNegativacaoCriterio();
			negativacaoCriterio.setNegativacaoComando(negativacaoComando);
			negativacaoCriterio.setUltimaAlteracao(new Date());

			Integer idNegativacaoCriterioGerado = (Integer) this.getControladorUtil().inserir(negativacaoCriterio);
			negativacaoCriterio.setId(idNegativacaoCriterioGerado);

			// Incluir Negativacai Criterio CPF Tipo
			Collection<NegativacaoCriterioCpfTipo> colecaoNegativacaoCriterioCpfTipo = helper.getColecaoNegativacaoCriterioCpfTipo();
			Iterator negativacaoCriterioCpfTipoIterator = colecaoNegativacaoCriterioCpfTipo.iterator();
			while(negativacaoCriterioCpfTipoIterator.hasNext()){
				NegativacaoCriterioCpfTipo negativacaoCriterioCpfTipo = (NegativacaoCriterioCpfTipo) negativacaoCriterioCpfTipoIterator
								.next();
				negativacaoCriterioCpfTipo.setNegativacaoCriterio(negativacaoCriterio);
				negativacaoCriterioCpfTipo.setUltimaAlteracao(new Date());
				this.getControladorUtil().inserir(negativacaoCriterioCpfTipo);
			}

			// Incluir Negativacao Criterio SubCategoria
			if(helper.getIdsSubcategoria() != null && helper.getIdsSubcategoria().length > 0){
				String[] idsSubCategoria = (String[]) helper.getIdsSubcategoria();
				int indexSubCategoria = 0;
				if(!idsSubCategoria[indexSubCategoria].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsSubCategoria.length > indexSubCategoria){
						Integer idSubCategoria = Integer.valueOf(idsSubCategoria[indexSubCategoria]);
						NegativacaoCriterioSubcategoria ncSubacategoria = new NegativacaoCriterioSubcategoria();
						Subcategoria subcategoria = new Subcategoria();
						subcategoria.setId(idSubCategoria);

						NegativacaoCriterioSubcategoriaPK negativacaoCriterioSubcategoriaPK = new NegativacaoCriterioSubcategoriaPK();
						negativacaoCriterioSubcategoriaPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioSubcategoriaPK.setSubcategoria(subcategoria);
						ncSubacategoria.setComp_id(negativacaoCriterioSubcategoriaPK);
						ncSubacategoria.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncSubacategoria);

						indexSubCategoria++;
					}
				}
			}

			// Incluir Negativacao Imovel Perfil
			if(helper.getIdsPerfilImovel() != null && helper.getIdsPerfilImovel().length > 0){
				String[] idsPerfilImovel = (String[]) helper.getIdsPerfilImovel();
				int indexPerfilaImovel = 0;

				if(!idsPerfilImovel[indexPerfilaImovel].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsPerfilImovel.length > indexPerfilaImovel){
						Integer idPerfilImovel = Integer.valueOf(idsPerfilImovel[indexPerfilaImovel]);
						NegativacaoCriterioImovelPerfil ncImovelPerfil = new NegativacaoCriterioImovelPerfil();
						ImovelPerfil imovelPerfil = new ImovelPerfil();
						imovelPerfil.setId(idPerfilImovel);

						NegativacaoCriterioImovelPerfilPK negativacaoCriterioImovelPerfilPK = new NegativacaoCriterioImovelPerfilPK();
						negativacaoCriterioImovelPerfilPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioImovelPerfilPK.setImovelPerfil(imovelPerfil);
						ncImovelPerfil.setComp_id(negativacaoCriterioImovelPerfilPK);
						ncImovelPerfil.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncImovelPerfil);

						indexPerfilaImovel++;
					}
				}
			}

			// Incluir Negativacao Cliente Tipo
			if(helper.getIdsTipoCliente() != null && helper.getIdsTipoCliente().length > 0){
				String[] idsTipoCliente = (String[]) helper.getIdsTipoCliente();
				int indexClienteTipo = 0;

				if(!idsTipoCliente[indexClienteTipo].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsTipoCliente.length > indexClienteTipo){
						Integer idTipoCliente = Integer.valueOf(idsTipoCliente[indexClienteTipo]);
						NegativacaoCriterioClienteTipo ncClienteTipo = new NegativacaoCriterioClienteTipo();
						ClienteTipo clienteTipo = new ClienteTipo();
						clienteTipo.setId(idTipoCliente);

						NegativacaoCriterioClienteTipoPK negativacaoCriterioClienteTipoPK = new NegativacaoCriterioClienteTipoPK();
						negativacaoCriterioClienteTipoPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioClienteTipoPK.setClienteTipo(clienteTipo);
						ncClienteTipo.setComp_id(negativacaoCriterioClienteTipoPK);
						ncClienteTipo.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncClienteTipo);

						indexClienteTipo++;
					}
				}
			}

			// Incluir Negativacao Grupo Cobrança
			if(helper.getIdsCobrancaGrupo() != null && helper.getIdsCobrancaGrupo().length > 0){
				String[] idsCobrancaGrupo = (String[]) helper.getIdsCobrancaGrupo();
				int indexCobrancaGrupo = 0;

				if(!idsCobrancaGrupo[indexCobrancaGrupo].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsCobrancaGrupo.length > indexCobrancaGrupo){
						Integer idCobrancaGrupo = Integer.valueOf(idsCobrancaGrupo[indexCobrancaGrupo]);
						NegativCritCobrGrupo ncCobrGrupo = new NegativCritCobrGrupo();
						CobrancaGrupo cobrancaGrupo = new CobrancaGrupo();
						cobrancaGrupo.setId(idCobrancaGrupo);

						NegativCritCobrGrupoPK negativCritCobrGrupoPK = new NegativCritCobrGrupoPK();
						negativCritCobrGrupoPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritCobrGrupoPK.setCobrancaGrupo(cobrancaGrupo);
						ncCobrGrupo.setComp_id(negativCritCobrGrupoPK);
						ncCobrGrupo.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncCobrGrupo);

						indexCobrancaGrupo++;
					}
				}
			}

			// Incluir Negativacao Gerencia Regional
			if(helper.getIdsGerenciaRegional() != null && helper.getIdsGerenciaRegional().length > 0){
				String[] idsGerenciaRegional = (String[]) helper.getIdsGerenciaRegional();
				int indexGerenciaRegional = 0;

				if(!idsGerenciaRegional[indexGerenciaRegional].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsGerenciaRegional.length > indexGerenciaRegional){
						Integer idGerenciaRegional = Integer.valueOf(idsGerenciaRegional[indexGerenciaRegional]);
						NegativCritGerReg negativCritGerReg = new NegativCritGerReg();
						GerenciaRegional gerenciaRegional = new GerenciaRegional();
						gerenciaRegional.setId(idGerenciaRegional);

						NegativCritGerRegPK negativCritGerRegPK = new NegativCritGerRegPK();
						negativCritGerRegPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritGerRegPK.setGerenciaRegional(gerenciaRegional);
						negativCritGerReg.setComp_id(negativCritGerRegPK);
						negativCritGerReg.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(negativCritGerReg);

						indexGerenciaRegional++;
					}
				}
			}

			// Incluir Negativacao Unidade Negocio
			if(helper.getIdsUnidadeNegocio() != null && helper.getIdsUnidadeNegocio().length > 0){
				String[] idsUnidadeNegocio = (String[]) helper.getIdsUnidadeNegocio();
				int indexUnidadeNegocio = 0;

				if(!idsUnidadeNegocio[indexUnidadeNegocio].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsUnidadeNegocio.length > indexUnidadeNegocio){
						Integer idUnidadeNegocio = Integer.valueOf(idsUnidadeNegocio[indexUnidadeNegocio]);
						NegativCritUndNeg negativCritUndNeg = new NegativCritUndNeg();
						UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
						unidadeNegocio.setId(idUnidadeNegocio);

						NegativCritUndNegPK negativCritUndNegPK = new NegativCritUndNegPK();
						negativCritUndNegPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritUndNegPK.setUnidadeNegocio(unidadeNegocio);
						negativCritUndNeg.setComp_id(negativCritUndNegPK);
						negativCritUndNeg.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(negativCritUndNeg);

						indexUnidadeNegocio++;
					}
				}
			}

			// Incluir Negativacao Elo Polo
			if(helper.getIdsEloPolo() != null && helper.getIdsEloPolo().length > 0){
				String[] idsEloPolo = (String[]) helper.getIdsEloPolo();
				int indexEloPolo = 0;

				if(!idsEloPolo[indexEloPolo].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsEloPolo.length > indexEloPolo){
						Integer idEloPolo = Integer.valueOf(idsEloPolo[indexEloPolo]);
						NegativCritElo negativCritElo = new NegativCritElo();
						Localidade elo = new Localidade();
						elo.setId(idEloPolo);

						NegativCritEloPK negativCritEloPK = new NegativCritEloPK();
						negativCritEloPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritEloPK.setLocalidade(elo);
						negativCritElo.setComp_id(negativCritEloPK);
						negativCritElo.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(negativCritElo);

						indexEloPolo++;
					}
				}
			}

			retorno = idNegativacaoComandoGerado;

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Processa os itens de uma NegativadorMovimentoReg
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * [SB0002] Determinar Situação do Débito da Negativação
	 * 
	 * @author Genival Barbosa
	 * @date 27/10/2011
	 * @param negativadorMovimentoReg
	 * @param collNegativadorMovimentoRegItem
	 * @param mapaQuantidadeValor
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private void determinarSituacaoPredominanteDebitoNegativacao(NegativadorMovimentoReg negativadorMovimentoReg,
					Collection collNegativadorMovimentoRegItem, Map<String, Object> mapa) throws ControladorException,
					ErroRepositorioException{

		Integer quantidadeItensNegativacao = (Integer) mapa.get("quantidadeItensNegativacao");
		Integer quantidadeItensNegativacaoPagos = (Integer) mapa.get("quantidadeItensNegativacaoPagos");
		Integer quantidadeItensNegativacaoParcelados = (Integer) mapa.get("quantidadeItensNegativacaoParcelados");
		Integer quantidadeItensNegativacaoCancelados = (Integer) mapa.get("quantidadeItensNegativacaoCancelados");
		Integer quantidadeItensNegativacaoExcluidos = (Integer) mapa.get("quantidadeItensNegativacaoExcluidos");
		BigDecimal valorItensNegativacaoPagos = (BigDecimal) mapa.get("valorItensNegativacaoPagos");
		BigDecimal valorItensNegativacaoParcelados = (BigDecimal) mapa.get("valorItensNegativacaoParcelados");
		BigDecimal valorItensNegativacaoCancelados = (BigDecimal) mapa.get("valorItensNegativacaoCancelados");
		BigDecimal valorItensNegativacaoExcluidos = (BigDecimal) mapa.get("valorItensNegativacaoExcluidos");

		boolean temItemPendente = false;

		// 1.0
		for(Object objeto : collNegativadorMovimentoRegItem){
			NegativadorMovimentoRegItem negativadorMovimentoRegItem = (NegativadorMovimentoRegItem) objeto;
			if(negativadorMovimentoRegItem.getCobrancaDebitoSituacao().getId().equals(CobrancaDebitoSituacao.PENDENTE)){

				// 1.1 e 1.2
				popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.PENDENTE, new Date());

				temItemPendente = true;
				break;
			}
		}
		if(!temItemPendente){
			// 2.0
			Collection collCobrancaCriterio = getControladorCobranca().obterCobrancaCriterioAssociadoDocumentoCobrancaCorrespNegativacao(
							negativadorMovimentoReg.getNegativadorMovimento().getId());
			CobrancaCriterio cobrancaCriterio;
			// [FS0003] - Verificar existência do critério de cobrança
			BigDecimal percentualValorMinimoPagoParceladoCancelado = new BigDecimal(100);
			BigDecimal percentualQuantidadeMinimoPagoParceladoCancelado = new BigDecimal(100);
			if(collCobrancaCriterio != null && collCobrancaCriterio.size() > 0){
				FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();
				filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, (Integer) Util
								.retonarObjetoDeColecao(collCobrancaCriterio)));
				cobrancaCriterio = (CobrancaCriterio) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(filtroCobrancaCriterio,
								CobrancaCriterio.class.getName()));

				if(cobrancaCriterio.getPercentualValorMinimoPagoParceladoCancelado() != null
								&& !cobrancaCriterio.getPercentualValorMinimoPagoParceladoCancelado().equals(BigDecimal.ZERO)){
					percentualValorMinimoPagoParceladoCancelado = cobrancaCriterio.getPercentualValorMinimoPagoParceladoCancelado();
				}

				if(cobrancaCriterio.getPercentualQuantidadeMinimoPagoParceladoCancelado() != null
								&& !cobrancaCriterio.getPercentualQuantidadeMinimoPagoParceladoCancelado().equals(BigDecimal.ZERO)){
					percentualQuantidadeMinimoPagoParceladoCancelado = cobrancaCriterio
									.getPercentualQuantidadeMinimoPagoParceladoCancelado();
				}
			}

			// 3.0
			BigDecimal valorMinimo = negativadorMovimentoReg.getValorDebito().multiply(
							percentualValorMinimoPagoParceladoCancelado.divide(new BigDecimal(100), RoundingMode.HALF_UP)).setScale(2,
							RoundingMode.HALF_UP);
			// 4.0
			BigDecimal quantidadeMinima = (new BigDecimal(quantidadeItensNegativacao)).multiply(
							percentualQuantidadeMinimoPagoParceladoCancelado.divide(new BigDecimal(100), RoundingMode.HALF_UP)).setScale(2,
							RoundingMode.HALF_UP);
			// 5.0
			if(valorItensNegativacaoPagos.compareTo(valorMinimo) >= 0
							|| (new BigDecimal(quantidadeItensNegativacaoPagos)).compareTo(quantidadeMinima) >= 0){

				popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.PAGO, (Date) mapa.get("dataMaiorItensNegativacaoPagos"));

				// 6.0
			}else if(valorItensNegativacaoParcelados.compareTo(valorMinimo) >= 0
							|| (new BigDecimal(quantidadeItensNegativacaoParcelados)).compareTo(quantidadeMinima) >= 0){

				popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.PARCELADO, (Date) mapa
								.get("dataMaiorItensNegativacaoParcelados"));

				// 7.0
			}else if(valorItensNegativacaoCancelados.compareTo(valorMinimo) >= 0
							|| (new BigDecimal(quantidadeItensNegativacaoCancelados)).compareTo(quantidadeMinima) >= 0){

				popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.CANCELADO, (Date) mapa
								.get("dataMaiorItensNegativacaoCancelados"));

				// 8.0
			}else if(valorItensNegativacaoExcluidos.compareTo(valorMinimo) >= 0
							|| (new BigDecimal(quantidadeItensNegativacaoExcluidos)).compareTo(quantidadeMinima) >= 0){

				popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.EXCLUIDO, (Date) mapa
								.get("dataMaiorItensNegativacaoExcluidos"));

			}else{

				// [FS0004] - Verificar Valores Equivalentes
				List<BigDecimal> collValor = new ArrayList<BigDecimal>();
				collValor.add(valorItensNegativacaoPagos);
				collValor.add(valorItensNegativacaoParcelados);
				collValor.add(valorItensNegativacaoCancelados);
				collValor.add(valorItensNegativacaoExcluidos);
				Collections.sort(collValor, new Comparator<BigDecimal>() {

					public int compare(BigDecimal valor1, BigDecimal valor2){

						return valor1.compareTo(valor2);
					};
				});
				BigDecimal maiorValor = collValor.get(0);

				if(valorItensNegativacaoPagos.compareTo(maiorValor) == 0){

					// 9.0 e 10.0
					popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.PAGO, (Date) mapa.get("dataMaiorItensNegativacaoPagos"));

				}else if(valorItensNegativacaoParcelados.compareTo(maiorValor) == 0){

					// 9.0 e 10.0
					popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.PARCELADO, (Date) mapa
									.get("dataMaiorItensNegativacaoParcelados"));

				}else if(valorItensNegativacaoCancelados.compareTo(maiorValor) == 0){

					// 9.0 e 10.0
					popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.CANCELADO, (Date) mapa
									.get("dataMaiorItensNegativacaoCancelados"));

				}else if(valorItensNegativacaoExcluidos.compareTo(maiorValor) == 0){

					// 9.0 e 10.0
					popularSituacaoDataPredominante(mapa, CobrancaDebitoSituacao.EXCLUIDO, (Date) mapa
									.get("dataMaiorItensNegativacaoExcluidos"));
				}
			}
		}
	}

	private void popularSituacaoDataPredominante(Map<String, Object> mapa, Integer idCobrancaDebitoSituacao, Date data)
					throws ControladorException{

		CobrancaDebitoSituacao cobrancaDebitoSituacaoPredominante = null;
		Date dataSituacaoPredominante = null;

		FiltroCobrancaDebitoSituacao filtroCobrancaDebitoSituacao = new FiltroCobrancaDebitoSituacao();
		filtroCobrancaDebitoSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaDebitoSituacao.ID, idCobrancaDebitoSituacao));
		// 9.0
		cobrancaDebitoSituacaoPredominante = (CobrancaDebitoSituacao) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
						filtroCobrancaDebitoSituacao, CobrancaDebitoSituacao.class.getName()));
		// 10.0
		dataSituacaoPredominante = data;

		mapa.put("cobrancaDebitoSituacaoPredominante", cobrancaDebitoSituacaoPredominante);
		mapa.put("dataSituacaoPredominante", dataSituacaoPredominante);
	}

	/**
	 * [UC0652] Manter Comando de Negativação por Critério
	 * [SB0002] Atualizar Comando de Negativação por critério
	 * 
	 * @author Ana Maria
	 * @date 24/01/2008
	 * @throws ControladorException
	 */
	public void atualizarComandoNegativacao(InserirComandoNegativacaoPorCriterioHelper helper) throws ControladorException{

		try{

			// [FS0012]- Verificar existência de comando para os mesmos parâmetros
			String nomeNegativacaoComando = repositorioSpcSerasa.verificarExistenciaComandoMesmoParametroAtualizacao(helper);

			if(nomeNegativacaoComando != null && !nomeNegativacaoComando.equals("")){
				throw new ControladorException("atencao.comando_nao_realizado_mesmo_parametro", null, nomeNegativacaoComando);
			}

			FiltroNegativacaoComando filtroNegativacaoComando = new FiltroNegativacaoComando();

			// Seta o filtro para buscar o cliente na base
			filtroNegativacaoComando.adicionarParametro(new ParametroSimples(FiltroNegativacaoComando.ID, helper.getNegativacaoComando()
							.getId()));

			Collection colecaoNegativacaoComando = getControladorUtil().pesquisar(filtroNegativacaoComando,
							NegativacaoComando.class.getName());

			// verifica se a negativação comando ainda existe na base, porque ela pode ter sido
			// excluida com isso
			// não é possível analizar a data de ultima alteração
			if(colecaoNegativacaoComando == null || colecaoNegativacaoComando.isEmpty()){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Procura a negativação comando na base
			NegativacaoComando negativacaoComandoNaBase = (NegativacaoComando) ((List) colecaoNegativacaoComando).get(0);

			// Verificar se a negativação comando já foi atualizado por outro usuário
			// durante esta atualização
			if(negativacaoComandoNaBase.getUltimaAlteracao().after(helper.getNegativacaoComando().getUltimaAlteracao())){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.atualizacao.timestamp");
			}

			// Atualizar Comando Negativacao
			NegativacaoComando negativacaoComando = helper.getNegativacaoComando();
			negativacaoComando.setUltimaAlteracao(new Date());

			getControladorUtil().atualizar(negativacaoComando);

			// Atualizar Negativacao Criterio
			NegativacaoCriterio negativacaoCriterio = helper.getNegativacaoCriterio();
			negativacaoCriterio.setUltimaAlteracao(new Date());

			getControladorUtil().atualizar(negativacaoCriterio);

			// Remover
			repositorioSpcSerasa.removerParametrosCriterio(negativacaoCriterio.getId());

			// Incluir Negativacao Criterio CPF Tipo
			Collection<NegativacaoCriterioCpfTipo> colecaoNegativacaoCriterioCpfTipo = helper.getColecaoNegativacaoCriterioCpfTipo();
			Iterator negativacaoCriterioCpfTipoIterator = colecaoNegativacaoCriterioCpfTipo.iterator();
			while(negativacaoCriterioCpfTipoIterator.hasNext()){
				NegativacaoCriterioCpfTipo negativacaoCriterioCpfTipo = (NegativacaoCriterioCpfTipo) negativacaoCriterioCpfTipoIterator
								.next();
				negativacaoCriterioCpfTipo.setNegativacaoCriterio(negativacaoCriterio);
				negativacaoCriterioCpfTipo.setUltimaAlteracao(new Date());
				this.getControladorUtil().inserir(negativacaoCriterioCpfTipo);
			}

			// Incluir Negativacao Ligação Água
			if(helper.getIdsLigacaoAguaSituacao() != null && helper.getIdsLigacaoAguaSituacao().length > 0){
				String[] idsLigacaoAguaSituacao = (String[]) helper.getIdsLigacaoAguaSituacao();
				int indexLigacaoAguaSituacao = 0;

				if(!idsLigacaoAguaSituacao[indexLigacaoAguaSituacao].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsLigacaoAguaSituacao.length > indexLigacaoAguaSituacao){
						Integer idLigacaoAguaSituacao = Integer.valueOf(idsLigacaoAguaSituacao[indexLigacaoAguaSituacao]);
						NegativacaoCriterioLigacaoAgua ncLigacaoAgua = new NegativacaoCriterioLigacaoAgua();
						LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
						ligacaoAguaSituacao.setId(idLigacaoAguaSituacao);

						NegativacaoCriterioLigacaoAguaPK negativacaoCriterioLigacaoAguaPK = new NegativacaoCriterioLigacaoAguaPK();
						negativacaoCriterioLigacaoAguaPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioLigacaoAguaPK.setLigacaoAguaSituacao(ligacaoAguaSituacao);
						ncLigacaoAgua.setComp_id(negativacaoCriterioLigacaoAguaPK);
						ncLigacaoAgua.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncLigacaoAgua);

						indexLigacaoAguaSituacao++;
					}
				}
			}

			// Incluir Negativacao Ligação Esgoto
			if(helper.getIdsLigacaoEsgotoSituacao() != null && helper.getIdsLigacaoEsgotoSituacao().length > 0){
				String[] idsLigacaoEsgotoSituacao = (String[]) helper.getIdsLigacaoEsgotoSituacao();
				int indexLigacaoEsgotoSituacao = 0;

				if(!idsLigacaoEsgotoSituacao[indexLigacaoEsgotoSituacao].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsLigacaoEsgotoSituacao.length > indexLigacaoEsgotoSituacao){
						Integer idLigacaoEsgotoSituacao = Integer.valueOf(idsLigacaoEsgotoSituacao[indexLigacaoEsgotoSituacao]);
						NegativacaoCriterioLigacaoEsgoto ncLigacaoEsgoto = new NegativacaoCriterioLigacaoEsgoto();
						LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
						ligacaoEsgotoSituacao.setId(idLigacaoEsgotoSituacao);

						NegativacaoCriterioLigacaoEsgotoPK negativacaoCriterioLigacaoEsgotoPK = new NegativacaoCriterioLigacaoEsgotoPK();
						negativacaoCriterioLigacaoEsgotoPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioLigacaoEsgotoPK.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
						ncLigacaoEsgoto.setComp_id(negativacaoCriterioLigacaoEsgotoPK);
						ncLigacaoEsgoto.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncLigacaoEsgoto);

						indexLigacaoEsgotoSituacao++;
					}
				}
			}

			// Incluir Negativacao Criterio SubCategoria
			if(helper.getIdsSubcategoria() != null && helper.getIdsSubcategoria().length > 0){
				String[] idsSubCategoria = (String[]) helper.getIdsSubcategoria();
				int indexSubCategoria = 0;
				if(!idsSubCategoria[indexSubCategoria].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsSubCategoria.length > indexSubCategoria){
						Integer idSubCategoria = Integer.valueOf(idsSubCategoria[indexSubCategoria]);
						NegativacaoCriterioSubcategoria ncSubacategoria = new NegativacaoCriterioSubcategoria();
						Subcategoria subcategoria = new Subcategoria();
						subcategoria.setId(idSubCategoria);

						NegativacaoCriterioSubcategoriaPK negativacaoCriterioSubcategoriaPK = new NegativacaoCriterioSubcategoriaPK();
						negativacaoCriterioSubcategoriaPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioSubcategoriaPK.setSubcategoria(subcategoria);
						ncSubacategoria.setComp_id(negativacaoCriterioSubcategoriaPK);
						ncSubacategoria.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncSubacategoria);

						indexSubCategoria++;
					}
				}
			}

			// Incluir Negativacao Imovel Perfil
			if(helper.getIdsPerfilImovel() != null && helper.getIdsPerfilImovel().length > 0){
				String[] idsPerfilImovel = (String[]) helper.getIdsPerfilImovel();
				int indexPerfilaImovel = 0;

				if(!idsPerfilImovel[indexPerfilaImovel].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsPerfilImovel.length > indexPerfilaImovel){
						Integer idPerfilImovel = Integer.valueOf(idsPerfilImovel[indexPerfilaImovel]);
						NegativacaoCriterioImovelPerfil ncImovelPerfil = new NegativacaoCriterioImovelPerfil();
						ImovelPerfil imovelPerfil = new ImovelPerfil();
						imovelPerfil.setId(idPerfilImovel);

						NegativacaoCriterioImovelPerfilPK negativacaoCriterioImovelPerfilPK = new NegativacaoCriterioImovelPerfilPK();
						negativacaoCriterioImovelPerfilPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioImovelPerfilPK.setImovelPerfil(imovelPerfil);
						ncImovelPerfil.setComp_id(negativacaoCriterioImovelPerfilPK);
						ncImovelPerfil.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncImovelPerfil);

						indexPerfilaImovel++;
					}
				}
			}

			// Incluir Negativacao Cliente Tipo
			if(helper.getIdsTipoCliente() != null && helper.getIdsTipoCliente().length > 0){
				String[] idsTipoCliente = (String[]) helper.getIdsTipoCliente();
				int indexClienteTipo = 0;

				if(!idsTipoCliente[indexClienteTipo].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsTipoCliente.length > indexClienteTipo){
						Integer idTipoCliente = Integer.valueOf(idsTipoCliente[indexClienteTipo]);
						NegativacaoCriterioClienteTipo ncClienteTipo = new NegativacaoCriterioClienteTipo();
						ClienteTipo clienteTipo = new ClienteTipo();
						clienteTipo.setId(idTipoCliente);

						NegativacaoCriterioClienteTipoPK negativacaoCriterioClienteTipoPK = new NegativacaoCriterioClienteTipoPK();
						negativacaoCriterioClienteTipoPK.setNegativacaoCriterio(negativacaoCriterio);
						negativacaoCriterioClienteTipoPK.setClienteTipo(clienteTipo);
						ncClienteTipo.setComp_id(negativacaoCriterioClienteTipoPK);
						ncClienteTipo.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncClienteTipo);

						indexClienteTipo++;
					}
				}
			}

			// Incluir Negativacao Grupo Cobrança
			if(helper.getIdsCobrancaGrupo() != null && helper.getIdsCobrancaGrupo().length > 0){
				String[] idsCobrancaGrupo = (String[]) helper.getIdsCobrancaGrupo();
				int indexCobrancaGrupo = 0;

				if(!idsCobrancaGrupo[indexCobrancaGrupo].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsCobrancaGrupo.length > indexCobrancaGrupo){
						Integer idCobrancaGrupo = Integer.valueOf(idsCobrancaGrupo[indexCobrancaGrupo]);
						NegativCritCobrGrupo ncCobrGrupo = new NegativCritCobrGrupo();
						CobrancaGrupo cobrancaGrupo = new CobrancaGrupo();
						cobrancaGrupo.setId(idCobrancaGrupo);

						NegativCritCobrGrupoPK negativCritCobrGrupoPK = new NegativCritCobrGrupoPK();
						negativCritCobrGrupoPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritCobrGrupoPK.setCobrancaGrupo(cobrancaGrupo);
						ncCobrGrupo.setComp_id(negativCritCobrGrupoPK);
						ncCobrGrupo.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(ncCobrGrupo);

						indexCobrancaGrupo++;
					}
				}
			}

			// Incluir Negativacao Gerencia Regional
			if(helper.getIdsGerenciaRegional() != null && helper.getIdsGerenciaRegional().length > 0){
				String[] idsGerenciaRegional = (String[]) helper.getIdsGerenciaRegional();
				int indexGerenciaRegional = 0;

				if(!idsGerenciaRegional[indexGerenciaRegional].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsGerenciaRegional.length > indexGerenciaRegional){
						Integer idGerenciaRegional = Integer.valueOf(idsGerenciaRegional[indexGerenciaRegional]);
						NegativCritGerReg negativCritGerReg = new NegativCritGerReg();
						GerenciaRegional gerenciaRegional = new GerenciaRegional();
						gerenciaRegional.setId(idGerenciaRegional);

						NegativCritGerRegPK negativCritGerRegPK = new NegativCritGerRegPK();
						negativCritGerRegPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritGerRegPK.setGerenciaRegional(gerenciaRegional);
						negativCritGerReg.setComp_id(negativCritGerRegPK);
						negativCritGerReg.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(negativCritGerReg);

						indexGerenciaRegional++;
					}
				}
			}

			// Incluir Negativacao Unidade Negocio
			if(helper.getIdsUnidadeNegocio() != null && helper.getIdsUnidadeNegocio().length > 0){
				String[] idsUnidadeNegocio = (String[]) helper.getIdsUnidadeNegocio();
				int indexUnidadeNegocio = 0;

				if(!idsUnidadeNegocio[indexUnidadeNegocio].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsUnidadeNegocio.length > indexUnidadeNegocio){
						Integer idUnidadeNegocio = Integer.valueOf(idsUnidadeNegocio[indexUnidadeNegocio]);
						NegativCritUndNeg negativCritUndNeg = new NegativCritUndNeg();
						UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
						unidadeNegocio.setId(idUnidadeNegocio);

						NegativCritUndNegPK negativCritUndNegPK = new NegativCritUndNegPK();
						negativCritUndNegPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritUndNegPK.setUnidadeNegocio(unidadeNegocio);
						negativCritUndNeg.setComp_id(negativCritUndNegPK);
						negativCritUndNeg.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(negativCritUndNeg);

						indexUnidadeNegocio++;
					}
				}
			}

			// Incluir Negativacao Elo Polo
			if(helper.getIdsEloPolo() != null && helper.getIdsEloPolo().length > 0){
				String[] idsEloPolo = (String[]) helper.getIdsEloPolo();
				int indexEloPolo = 0;

				if(!idsEloPolo[indexEloPolo].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					while(idsEloPolo.length > indexEloPolo){
						Integer idEloPolo = Integer.valueOf(idsEloPolo[indexEloPolo]);
						NegativCritElo negativCritElo = new NegativCritElo();
						Localidade elo = new Localidade();
						elo.setId(idEloPolo);

						NegativCritEloPK negativCritEloPK = new NegativCritEloPK();
						negativCritEloPK.setNegativacaoCriterio(negativacaoCriterio);
						negativCritEloPK.setLocalidade(elo);
						negativCritElo.setComp_id(negativCritEloPK);
						negativCritElo.setUltimaAlteracao(new Date());

						this.getControladorUtil().inserir(negativCritElo);

						indexEloPolo++;
					}
				}
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Método que atualiza o número da execução do resumo da negativação na tabela
	 * SISTEMA_PARAMETROS mais um [UC0688] Gerar Resumo Diário da
	 * Negativação. *
	 * 
	 * @author Yara Taciane
	 * @date 11/11/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarNumeroExecucaoResumoNegativacao(Integer idFuncionalidadeIniciada) throws ControladorException{

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		int idUnidadeIniciada = 0;
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, 0);

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

		Integer numeroExecucao = sistemaParametro.getNumeroExecucaoResumoNegativacao() + 1;

		// [UC0688] Gerar Resumo Diário da Negativação.
		// -------------------------------------------------------------------------------------------
		// Alterado por : Yara Taciane - data : 08/07/2008
		// Analista : Fátima Sampaio
		// -------------------------------------------------------------------------------------------

		// O sistema atualiza o número da execução do resumo da negativação na tabela
		// SISTEMA_PARAMETROS mais um).
		sistemaParametro.setNumeroExecucaoResumoNegativacao(numeroExecucao);

		sistemaParametro.setUltimaAlteracao(new Date());

		this.getControladorUtil().atualizarSistemaParametro(sistemaParametro);

		System.out.println(" Fim --- > altualizado Sistema Parametro = " + sistemaParametro.getNumeroExecucaoResumoNegativacao());

		getControladorBatch().encerrarUnidadeProcessamentoBatch(null, idUnidadeIniciada, false);

	}

	public NegativadorMovimentoReg pesquisarNegativadorMovimentoRegInclusao(Imovel imovel, Negativador negativador)
					throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarNegativadorMovimentoRegInclusao(imovel, negativador);
		}catch(ErroRepositorioException e){

			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Método consuta os Negativadores que tenham movimento de Exclusão BATCH
	 * [UC0673] - Gerar Movimento da Exclusão de Negativação
	 * [SB0001] - Gerar Movimento da Exclusão de Negativação
	 * 
	 * @author Yara T. Souza
	 * @date 26/12/2007
	 */
	public Collection gerarMovimentoExclusaoNegativacao(Integer idFuncionalidadeIniciada, Integer[] idNegativador, Usuario usuario)
					throws ControladorException{

		System.out.println("....................Inicio Movimento Exclusao Negativacao");

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		int idUnidadeIniciada = 0;
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, 0);

		// todas as movimentacoes
		Collection coll = new ArrayList();

		try{
			// consultar as negativacoes para excluir
			Collection collNegativadoresParaExclusao = repositorioSpcSerasa.consultarNegativacoesParaExclusaoMovimento(idNegativador);

			if(!Util.isVazioOrNulo(collNegativadoresParaExclusao)){
				System.out.println("Total Negativadores para exclusão = " + collNegativadoresParaExclusao.size());

				// gerando um map dos navegadores com os NegativadorMovimentoReg
				Map mapNegativadorCOMNegativadorMovimentoReg = new HashMap();
				Iterator it = collNegativadoresParaExclusao.iterator();
				while(it.hasNext()){
					NegativadorMovimentoReg negativadorMovimentoReg = (NegativadorMovimentoReg) it.next();

					if(mapNegativadorCOMNegativadorMovimentoReg.containsKey(negativadorMovimentoReg.getNegativadorMovimento()
									.getNegativador())){
						ArrayList al = (ArrayList) mapNegativadorCOMNegativadorMovimentoReg.get(negativadorMovimentoReg
										.getNegativadorMovimento().getNegativador());
						al.add(negativadorMovimentoReg);
					}else{
						ArrayList al = new ArrayList();
						al.add(negativadorMovimentoReg);
						mapNegativadorCOMNegativadorMovimentoReg
										.put(negativadorMovimentoReg.getNegativadorMovimento().getNegativador(), al);
					}
				}

				// 7.0
				it = mapNegativadorCOMNegativadorMovimentoReg.keySet().iterator();

				while(it.hasNext()){
					Negativador n = (Negativador) it.next();

					Collection collv = (ArrayList) mapNegativadorCOMNegativadorMovimentoReg.get(n);
					NegativadorContrato nc = repositorioSpcSerasa.consultarNegativadorContratoVigente(n.getId());

					NegativadorMovimento nm = new NegativadorMovimento();
					nm.setCodigoMovimento(Short.valueOf("2"));
					nm.setDataEnvio(new Date());
					nm.setDataProcessamentoEnvio(new Date());
					nm.setNumeroSequencialEnvio(nc.getNumeroSequencialEnvio() + 1);
					nm.setUltimaAlteracao(new Date());
					nm.setNegativador(n);

					repositorioUtil.inserir(nm);
					System.out.println("Negativador Movimento = " + nm.getId());

					Object[] header = this.gerarRegistroTipoHeader(Integer.valueOf(0), BigDecimal.ZERO, Integer.valueOf(0), n, nm, nc,
									collv);
					header = this.gerarRegistroTipoDetalhe((Integer) header[0], (BigDecimal) header[1], (Integer) header[2], n, nm, nc,
									collv);
					header = this.gerarRegistroTipoTrailler((Integer) header[0], (BigDecimal) header[1], (Integer) header[2], n, nm, nc,
									collv);

					System.out.println("Entrou no gerar arquivo .................");
					this.gerarArquivo(nm.getId(), false, nm.getNegativador().getId(), usuario);
					System.out.println("Saiu do gerar arquivo .................");

					Integer numeroExclusoesJaEnviadas = nc.getNumeroExclusoesEnviadas();
					if(numeroExclusoesJaEnviadas == null){
						numeroExclusoesJaEnviadas = 0;
					}

					nc.setNumeroSequencialEnvio(nm.getNumeroSequencialEnvio());
					nc.setNumeroExclusoesEnviadas(numeroExclusoesJaEnviadas + ((Integer) header[2]));

					nc.setUltimaAlteracao(new Date());

					repositorioUtil.atualizar(nc);
					System.out.println("............Sequencial =" + nm.getNumeroSequencialEnvio());

					nm.setNumeroRegistrosEnvio((Integer) header[0]);
					nm.setValorTotalEnvio((BigDecimal) header[1]);
					nm.setUltimaAlteracao(new Date());

					repositorioUtil.atualizar(nm);

				}

			}else{
				System.out.println(" Não existem Negativações ");
			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(null, idUnidadeIniciada, false);

		}catch(Exception ex){
			getControladorBatch().encerrarUnidadeProcessamentoBatch(ex, idUnidadeIniciada, true);
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}finally{
			System.out.println("....................Fim Movimento Exclusao Negativacao");
		}

		return coll;
	}

	/**
	 * Método que inicia o caso de uso de Gerar Movimento de Inclusao de Negativacao [UC0671] Gerar
	 * Movimento de Inclusao de Nwegativação [Fluxo
	 * Principal]
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param idComandoNegativacao
	 * @param tipoComando
	 * @param comunicacaoInterna
	 * @param idNegativador
	 * @param idUsuarioResponsaval
	 * @param ObjectImovel
	 *            -
	 *            Collecao de [0] Integer - Matricula do Imovel
	 *            [1] Integer - id do cliente da
	 *            negativacao [2] String - cpf do cliente da
	 *            negativacao [3] String - cnpj do cliente da negativaca [4] Collection - lista da
	 *            contas e guias de pagamento do imovel [5]
	 *            Intetger - quantidade de itens em débito do imovel [6] BigDecimal - valor total
	 *            dos débitos do imovel
	 * @param dataPrevista
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public Integer gerarMovimentoInclusaoNegativacao(String comunicacaoInterna, Integer idNegativador, int idUsuarioResponsaval,
					Collection objectImovel, Date dataPrevista, Integer idCobrancaAcaoAtividadeCronograma,
					Integer idCobrancaAcaoAtividadeComando) throws ControladorException{

		Integer quantidadeInclusao = 0;
		Integer quantidadeItensIncluidos = 0;
		BigDecimal valorTotal = BigDecimal.ZERO;
		Integer idNegativacaoMovimento = 0;
		Integer quantidadeRegistro = 0;

		Object[] objQuantidades = new Object[5];
		objQuantidades[0] = quantidadeInclusao;
		objQuantidades[1] = quantidadeItensIncluidos;
		objQuantidades[2] = valorTotal;
		objQuantidades[3] = idNegativacaoMovimento;
		objQuantidades[4] = quantidadeRegistro;

		try{

			// 3.0

			// 1
			Negativador negativador = new Negativador();
			negativador.setId(idNegativador);

			// 2
			NegativadorContrato negativadorContrato = repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador.getId());
			if(negativadorContrato == null){
				throw new ControladorException("atencao.nao_ha_contrato_negativador");
			}
			// 3

			CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma = null;

			if(idCobrancaAcaoAtividadeCronograma != null){
				cobrancaAcaoAtividadeCronograma = new CobrancaAcaoAtividadeCronograma();
				cobrancaAcaoAtividadeCronograma.setId(idCobrancaAcaoAtividadeCronograma);
			}

			CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = null;

			if(idCobrancaAcaoAtividadeComando != null){
				cobrancaAcaoAtividadeComando = new CobrancaAcaoAtividadeComando();
				cobrancaAcaoAtividadeComando.setId(idCobrancaAcaoAtividadeComando);
			}

			// 4
			objQuantidades = gerarMovimentodeInclusaoNegativacaoPorMatriculadeImovel(objQuantidades, negativadorContrato, negativador,
			// tipoComando, nComando,
							objectImovel, cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando);
			// }

			// 4.0
			if((Integer) objQuantidades[0] > 0){

				// 4.2.1 [SB0012] - Gerar Arquivo TXT para envio ao negativador

				FiltroNegativadorMovimento fnm = new FiltroNegativadorMovimento();
				fnm.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimento.ID, (Integer) objQuantidades[3]));
				Collection colecaoNegativadorMovimento = repositorioUtil.pesquisar(fnm, NegativadorMovimento.class.getName());
				NegativadorMovimento nm = null;
				if(colecaoNegativadorMovimento != null && !colecaoNegativadorMovimento.isEmpty()){
					nm = (NegativadorMovimento) colecaoNegativadorMovimento.iterator().next();
				}

				// Soma o registro do trailler
				// ---------------------------------------------------------yara 15-01-2011
				objQuantidades[4] = (Integer) objQuantidades[4] + 1;
				// ---------------------------------------------------------yara 15-01-2011

				Usuario usuario = new Usuario();
				usuario.setId(idUsuarioResponsaval);

				this.gerarArquivo(nm.getId(), true, idNegativador, usuario);

				// Negativador n = nComando.getNegativador();

				// 4.2.2
				NegativadorContrato negativadorContratoAtualizar = repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador
								.getId());
				negativadorContratoAtualizar.setNumeroSequencialEnvio(nm.getNumeroSequencialEnvio());
				if(negativadorContratoAtualizar.getNumeroInclusoesEnviadas() != null){
					negativadorContratoAtualizar.setNumeroInclusoesEnviadas(negativadorContratoAtualizar.getNumeroInclusoesEnviadas()
									+ (Integer) objQuantidades[0]);
				}else{
					negativadorContratoAtualizar.setNumeroInclusoesEnviadas((Integer) objQuantidades[0]);
				}
				repositorioUtil.atualizar(negativadorContratoAtualizar);

				// 4.2.3
				if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
					nm.setNumeroRegistrosEnvio((((Integer) objQuantidades[0]) * 3) + 2);
				}else{
					nm.setNumeroRegistrosEnvio((Integer) objQuantidades[4]);
				}

				nm.setValorTotalEnvio((BigDecimal) objQuantidades[2]);

				repositorioUtil.atualizar(nm);
			}
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return (Integer) objQuantidades[0];
	}

	/**
	 * Gerar Registro de negativacao tipo header
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativação [SB0008] Gerar Registro de tipo header
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param nc
	 * @param Object
	 *            []
	 *            obj obj[0] Integer - quantidadeInclusao obj[1] Integer - quantidadeItens obj[2]
	 *            BigDecimal - valorTotalDebito
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public Integer gerarRegistroDeInclusaoTipoHeader(Integer quantidadeRegistro, Negativador n, NegativadorContrato nContrato,
					NegativacaoCriterio nCriterio, NegativadorMovimento NegativacaoMovimento,
					DadosNegativacaoPorImovelHelper dadosNegativacaoPorImovelHelper) throws ControladorException{

		StringBuilder registroTipoHeader = null;

		if(n.getId().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
			System.out.println("************************** gerarRegistroDeInclusaoTipoHeader NEGATIVADOR_SPC_SAO_PAULO ");
			// 1. Gerar Registro tipo Header SPC
			registroTipoHeader = this.geraRegistroTipoHeaderSPCSP(nContrato.getNumeroSequencialEnvio(), quantidadeRegistro);
		}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
			System.out.println("************************** gerarRegistroDeInclusaoTipoHeader NEGATIVADOR_SPC_BRASIL ");
			// 1. Gerar Registro tipo Header Brasil
			registroTipoHeader = this.geraRegistroTipoHeaderSPCBrasil(nContrato.getNumeroSequencialEnvio(), quantidadeRegistro);
		}else if(n.getId().equals(Negativador.NEGATIVADOR_SERASA)){
			System.out.println("************************** gerarRegistroDeInclusaoTipoHeader NEGATIVADOR_SERASA ");
			// 2. Gerar Registro tipo Header SERASA
			registroTipoHeader = this.geraRegistroTipoHeaderSERASA(nContrato.getNumeroSequencialEnvio(), quantidadeRegistro);
		}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
			System.out.println("************************** gerarRegistroDeInclusaoTipoHeader NEGATIVADOR_SPC_BOA_VISTA ");
			// 2. Gerar Registro tipo Header BOA VISTA
			registroTipoHeader = this.geraRegistroTipoHeaderSPCBoaVista(nContrato.getNumeroSequencialEnvio(), quantidadeRegistro);
		}

		// 3. Gerar o registro do movimento da negativação correspondente ao registro tipo
		// Hearder

		NegativadorMovimentoReg negativadorMovimentoRegInserir = new NegativadorMovimentoReg();
		negativadorMovimentoRegInserir.setNegativadorMovimento(NegativacaoMovimento);
		NegativadorRegistroTipo negativadorRegistroTipo = new NegativadorRegistroTipo();

		if(n.getId() == Negativador.NEGATIVADOR_SPC_SAO_PAULO){
			negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_SPC_HEADER_SP);
		}else if(n.getId() == Negativador.NEGATIVADOR_SPC_BRASIL){
			negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_SPC_HEADER_BR);
		}else if(n.getId() == Negativador.NEGATIVADOR_SERASA){
			negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_SERASA_HEADER);
		}else if(n.getId() == Negativador.NEGATIVADOR_SPC_BOA_VISTA){
			negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_BOA_VISTA_HEADER);
		}

		negativadorMovimentoRegInserir.setNegativadorRegistroTipo(negativadorRegistroTipo);
		negativadorMovimentoRegInserir.setConteudoRegistro(registroTipoHeader.toString());
		negativadorMovimentoRegInserir.setUltimaAlteracao(new Date());
		negativadorMovimentoRegInserir.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
		negativadorMovimentoRegInserir.setNumeroRegistro(0);

		if(dadosNegativacaoPorImovelHelper != null && n.getId() != Negativador.NEGATIVADOR_SPC_SAO_PAULO){

			negativadorMovimentoRegInserir.setUsuario(dadosNegativacaoPorImovelHelper.getUsuario());

		}

		this.getControladorUtil().inserir(negativadorMovimentoRegInserir);

		System.out.println("************************** FIM gerarRegistroDeInclusaoTipoHeader");
		return quantidadeRegistro;
	}

	/**
	 * Informações Atualizadas em (maior data e hora da última execução
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public Date getDataUltimaAtualizacaoResumoNegativacao(Integer numeroExecucaoResumoNegativacao) throws ControladorException{

		try{
			return repositorioSpcSerasa.getDataUltimaAtualizacaoResumoNegativacao(numeroExecucaoResumoNegativacao);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	// GERA REGISTRO HEADER SPC *******************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0008] **
	// ********************************************
	public StringBuilder geraRegistroTipoHeaderSPCBrasil(int saEnvio, int quantidadeRegistros) throws ControladorException{

		// 1.1
		StringBuilder registroHeader = new StringBuilder();
		// H.01
		registroHeader.append("00");
		// H.02
		registroHeader.append("REMESSA");
		// seta a data corrente
		// H.03
		String dataAtualString = Util.formatarDataSemBarraDDMMAAAA(new Date());
		// .recuperaDataInvertida(new Date());
		registroHeader.append(Util.completaString(dataAtualString, 8));
		// H.04
		registroHeader.append(Util.adicionarZerosEsquedaNumero(8, "" + (saEnvio + 1)));
		// H.05
		String entidade = (String) ParametroSpcSerasa.P_CODIGO_ENTIDADE_INFORMANTE_NEGATIVACAO.executar(ExecutorParametrosSpcSerasa
						.getInstancia());
		entidade = Util.adicionarZerosEsquedaNumero(5, entidade);
		registroHeader.append(entidade);
		// H.06
		String associado = (String) ParametroSpcSerasa.P_CODIGO_ASSOCIADO_INFORMANTE_NEGATIVACAO.executar(ExecutorParametrosSpcSerasa
						.getInstancia());
		associado = Util.adicionarZerosEsquedaNumero(8, associado);
		registroHeader.append(associado);
		// H.07
		String h07 = Util.recuperaDataInvertida(new Date());
		registroHeader.append(Util.completaString(h07, 8));
		// H.08
		registroHeader.append(Util.completaString(" ", 271));
		// H.09
		registroHeader.append("SPC  ");
		// H.10
		String versao = ConstantesAplicacao.get("versao_spc_brasil");
		registroHeader.append(versao);
		// H.11
		registroHeader.append("          ");
		// H.12
		registroHeader.append(Util.adicionarZerosEsquedaNumero(6, "" + quantidadeRegistros));

		return registroHeader;
	}

	/**
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores Insere Processo Batch para
	 * Registrar Movimento de Retorno do Negativadaor.
	 * 
	 * @author Yara T. Souza
	 * @date 09/12/2008
	 * @return void
	 * @throws ControladorException
	 */
	public void inserirProcessoRegistrarNegativadorMovimentoRetorno(Usuario usuario) throws ControladorException{

		int idProcesso = Processo.GERAR_MOVIMENTO_RETORNO_NEGATIVACAO;

		ProcessoIniciado processoIniciado = new ProcessoIniciado();

		Processo processo = new Processo();
		processo.setId(idProcesso);
		processoIniciado.setUsuario(usuario);
		processoIniciado.setDataHoraAgendamento(new Date());
		ProcessoSituacao processoSituacao = new ProcessoSituacao();
		processoIniciado.setProcesso(processo);
		processoIniciado.setProcessoSituacao(processoSituacao);

		// 3 - Insere Processo Batch.
		Integer codigoProcessoIniciadoGerado = (Integer) this.getControladorBatch().inserirProcessoIniciado(processoIniciado);
		System.out.println("codigoProcessoIniciadoGerado = " + codigoProcessoIniciadoGerado);
	}

	public NegativacaoCriterio pesquisarComandoNegativacaoSimulado(Integer idComandoNegativacao) throws ControladorException{

		NegativacaoCriterio negativacaoCriterio = null;
		Object[] pesquisaComandoNegativacaoSimulacao = null;

		try{

			pesquisaComandoNegativacaoSimulacao = repositorioSpcSerasa.pesquisarComandoNegativacaoSimulado(idComandoNegativacao);

		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

		if(pesquisaComandoNegativacaoSimulacao != null && !(pesquisaComandoNegativacaoSimulacao.equals(""))){
			negativacaoCriterio = new NegativacaoCriterio();
			// seta o titulo Negativação Critério
			if(pesquisaComandoNegativacaoSimulacao[0] != null){
				negativacaoCriterio.setDescricaoTitulo((String) pesquisaComandoNegativacaoSimulacao[0]);
			}
			// seta o indicador de simulação e a data da última alteração de negativação comando
			if(pesquisaComandoNegativacaoSimulacao[1] != null){
				NegativacaoComando negComando = new NegativacaoComando();
				negComando.setIndicadorSimulacao((Short) pesquisaComandoNegativacaoSimulacao[1]);
				negComando.setDataHoraRealizacao((Date) pesquisaComandoNegativacaoSimulacao[2]);
				negativacaoCriterio.setNegativacaoComando(negComando);
			}

		}
		return negativacaoCriterio;

	}

	/**
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso critério)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoCriterio(ComandoNegativacaoTipoCriterioHelper comandoNegativacaoTipoCriterioHelper,
					Integer numeroPagina) throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			retorno = repositorioSpcSerasa.pesquisarComandoNegativacaoTipoCriterio(comandoNegativacaoTipoCriterioHelper, numeroPagina);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso critério)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoCriterio(ComandoNegativacaoTipoCriterioHelper comandoNegativacaoTipoCriterioHelper)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			retorno = repositorioSpcSerasa.pesquisarComandoNegativacaoTipoCriterio(comandoNegativacaoTipoCriterioHelper);
		}catch(ErroRepositorioException e){

			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso matrícula)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoMatricula(ComandoNegativacaoHelper comandoNegativacaoHelper)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			retorno = repositorioSpcSerasa.pesquisarComandoNegativacaoTipoMatricula(comandoNegativacaoHelper);
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Método usado para consulta de comandos de negativação por tipo de comando
	 * (nesse caso matrícula)usado no caso de uso [UC0691]
	 * 
	 * @author Thiago Vieira
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarComandoNegativacaoTipoMatricula(ComandoNegativacaoHelper comandoNegativacaoHelper, Integer numeroPagina)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			retorno = repositorioSpcSerasa.pesquisarComandoNegativacaoTipoMatricula(comandoNegativacaoHelper, numeroPagina);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * [UC0693] - Gerar Relatório Acompanhamento de Clientes Negativados
	 * pesquisa ocorrência na tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO para NMRG_ID=NMRG_ID
	 * da tabela NEGATIVADOR_MOVIMENTO_REG)
	 * 
	 * @author Vivianne Sousa
	 * @data 30/04/2009
	 */
	public Object[] pesquisarDadosParcelamentoRelatorioAcompanhamentoClientesNegativados(Integer idNegativadorMovimentoReg)
					throws ControladorException{

		try{

			Object[] retorno = null;
			RelatorioAcompanhamentoClientesNegativadosSomatorioDadosParcelamentoHelper helperSomatorio = null;
			Collection colecaoNegativadorMovimentoRegParcelamento = repositorioSpcSerasa
							.pesquisarNegativadorMovimentoRegParcelamento(idNegativadorMovimentoReg);

			if(colecaoNegativadorMovimentoRegParcelamento != null && !colecaoNegativadorMovimentoRegParcelamento.isEmpty()){

				BigDecimal valorParceladoTotalParc = BigDecimal.ZERO;
				BigDecimal valorParceladoEntradaTotalParc = BigDecimal.ZERO;
				BigDecimal valorParceladoEntradaPagoTotalParc = BigDecimal.ZERO;
				Integer numeroPrestacoesTotalParc = 0;
				Integer numeroPrestacoesCobradasPagasTotalParc = 0;
				BigDecimal valorParceladoCobradoPagoTotalParc = BigDecimal.ZERO;
				Integer numeroPrestacoesCobradasNaoPagasTotalParc = 0;
				BigDecimal valorParceladoCobradoNaoPagoTotalParc = BigDecimal.ZERO;
				Integer numeroPrestacoesNaoCobradasTotalParc = 0;
				BigDecimal valorParceladoNaoCobradoTotalParc = BigDecimal.ZERO;
				BigDecimal valorParceladoCanceladoTotalParc = BigDecimal.ZERO;
				Iterator iterator = colecaoNegativadorMovimentoRegParcelamento.iterator();
				retorno = new Object[2];

				if(colecaoNegativadorMovimentoRegParcelamento.size() > 1){
					while(iterator.hasNext()){
						NegativadorMovimentoRegParcelamento nmrp = (NegativadorMovimentoRegParcelamento) iterator.next();

						// NMRP_VLPARCELADO da tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO com
						// NMRP_ICPARCELAMENTOATIVO=1
						// mais (somatório(NMRP_VLPARCELADOCOBRADOPAGO e
						// NMRP_VLPARCELADOCOBRADONAOPAGO)) com NMRP_ICPARCELAMENTOATIVO=2
						if(nmrp.getIndicadorParcelamentoAtivo().equals(ConstantesSistema.SIM)){
							valorParceladoTotalParc = valorParceladoTotalParc.add(nmrp.getValorParcelado());
						}else{
							BigDecimal valorCobradoPagoMaisValorCobradoNaoPago = BigDecimal.ZERO;

							if(nmrp.getValorParceladoCobradoPago() != null){
								valorCobradoPagoMaisValorCobradoNaoPago = nmrp.getValorParceladoCobradoPago().add(
												nmrp.getValorParceladoCobradoNaoPago());
							}

							valorParceladoTotalParc = valorParceladoTotalParc.add(valorCobradoPagoMaisValorCobradoNaoPago);
						}

						// somatório de NMRP_VLPARCELADOENTRADA
						valorParceladoEntradaTotalParc = valorParceladoEntradaTotalParc.add(nmrp.getValorParceladoEntrada());

						// somatório de NMRP_VLPARCELADOENTRADAPAGO
						if(nmrp.getValorParceladoEntradaPago() != null){
							valorParceladoEntradaPagoTotalParc = valorParceladoEntradaPagoTotalParc
											.add(nmrp.getValorParceladoEntradaPago());
						}

						// NMRP_NNPRESTACOES da tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO com
						// NMRP_ICPARCELAMENTOATIVO=1
						// mais (somatório(NMRP_NNPRESTACOESCOBRADASPAGAS e
						// NMRP_NNPRESTACOESCOBRADASNAOPAGAS)) com NMRP_ICPARCELAMENTOATIVO=2
						if(nmrp.getIndicadorParcelamentoAtivo().equals(ConstantesSistema.SIM)){
							numeroPrestacoesTotalParc = numeroPrestacoesTotalParc.intValue() + nmrp.getNumeroPrestacoes().intValue();
						}else{

							if(nmrp.getNumeroPrestacoesCobradasPagas() != null){
								numeroPrestacoesTotalParc = numeroPrestacoesTotalParc.intValue()
												+ nmrp.getNumeroPrestacoesCobradasPagas().intValue()
												+ nmrp.getNumeroPrestacoesCobradasNaoPagas().intValue();
							}
						}

						// somatório de NMRP_NNPRESTACOESCOBRADASPAGAS
						if(nmrp.getNumeroPrestacoesCobradasPagas() != null){
							numeroPrestacoesCobradasPagasTotalParc = numeroPrestacoesCobradasPagasTotalParc.intValue()
											+ nmrp.getNumeroPrestacoesCobradasPagas().intValue();
						}

						// somatório de NMRP_VLPARCELADOCOBRADOPAGO
						if(nmrp.getValorParceladoCobradoPago() != null){
							valorParceladoCobradoPagoTotalParc = valorParceladoCobradoPagoTotalParc
											.add(nmrp.getValorParceladoCobradoPago());
						}

						// somatório de NMRP_NNPRESTACOESCOBRADASNAOPAGAS
						if(nmrp.getNumeroPrestacoesCobradasNaoPagas() != null){
							numeroPrestacoesCobradasNaoPagasTotalParc = numeroPrestacoesCobradasNaoPagasTotalParc.intValue()
											+ nmrp.getNumeroPrestacoesCobradasNaoPagas().intValue();
						}

						// somatório de NMRP_VLPARCELADOCOBRADONAOPAGO
						if(nmrp.getValorParceladoCobradoNaoPago() != null){
							valorParceladoCobradoNaoPagoTotalParc = valorParceladoCobradoNaoPagoTotalParc.add(nmrp
											.getValorParceladoCobradoNaoPago());
						}

						// NMRP_NNPRESTACOESNAOCOBRADAS da tabela
						// NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO com NMRP_ICPARCELAMENTOATIVO=1
						if(nmrp.getIndicadorParcelamentoAtivo().equals(ConstantesSistema.SIM)
										&& nmrp.getNumeroPrestacoesNaoCobradas() != null){
							numeroPrestacoesNaoCobradasTotalParc = numeroPrestacoesNaoCobradasTotalParc.intValue()
											+ nmrp.getNumeroPrestacoesNaoCobradas();
						}

						// NMRP_VLPARCELADONAOCOBRADO da tabela
						// NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO com NMRP_ICPARCELAMENTOATIVO=1
						if(nmrp.getIndicadorParcelamentoAtivo().equals(ConstantesSistema.SIM) && nmrp.getValorParceladoNaoCobrado() != null){
							valorParceladoNaoCobradoTotalParc = valorParceladoNaoCobradoTotalParc.add(nmrp.getValorParceladoNaoCobrado());
						}

						// somatório de NMRP_VLPARCELADOCANCELADO
						if(nmrp.getValorParceladoCancelado() != null){
							valorParceladoCanceladoTotalParc = valorParceladoCanceladoTotalParc.add(nmrp.getValorParceladoCancelado());
						}
					}
					helperSomatorio = new RelatorioAcompanhamentoClientesNegativadosSomatorioDadosParcelamentoHelper();
					helperSomatorio.setValorParceladoTotal(valorParceladoTotalParc);
					helperSomatorio.setValorParceladoEntradaTotal(valorParceladoEntradaTotalParc);
					helperSomatorio.setValorParceladoEntradaPagoTotal(valorParceladoEntradaPagoTotalParc);
					helperSomatorio.setNumeroPrestacoesTotal(numeroPrestacoesTotalParc.shortValue());
					helperSomatorio.setNumeroPrestacoesCobradasPagasTotal(numeroPrestacoesCobradasPagasTotalParc.shortValue());
					helperSomatorio.setValorParceladoCobradoPagoTotal(valorParceladoCobradoPagoTotalParc);
					helperSomatorio.setNumeroPrestacoesCobradasNaoPagasTotal(numeroPrestacoesCobradasNaoPagasTotalParc.shortValue());
					helperSomatorio.setValorParceladoCobradoNaoPagoTotal(valorParceladoCobradoNaoPagoTotalParc);
					helperSomatorio.setNumeroPrestacoesNaoCobradasTotal(numeroPrestacoesNaoCobradasTotalParc.shortValue());
					helperSomatorio.setValorParceladoNaoCobradoTotal(valorParceladoNaoCobradoTotalParc);
					helperSomatorio.setValorParceladoCanceladoTotal(valorParceladoCanceladoTotalParc);
				}

				retorno[0] = colecaoNegativadorMovimentoRegParcelamento;
				retorno[1] = helperSomatorio;
			}

			return retorno;

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisa a Data da Exclusão da Negativação
	 * 
	 * @author Yara Taciane
	 * @date 9/05/2008
	 * @param
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarDataExclusaoNegativacao(int idImovel, int idNegativacaoComando) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarDataExclusaoNegativacao(idImovel, idNegativacaoComando);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisar se a negativação do imóvel .
	 * [UC0675] Excluir Negativação Online.
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Collection pesquisarImovelNegativado(Imovel imovel, Negativador negativador) throws ControladorException{

		Collection retorno;

		try{
			retorno = repositorioSpcSerasa.pesquisarImovelNegativado(imovel, negativador);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Método usado para apresentar os registros de negativadorMovimento Registro aceitos
	 * usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegatiacaoParaImovel(Imovel imovel, Negativador negativador) throws ControladorException{

		Collection retorno;
		try{
			retorno = repositorioSpcSerasa.pesquisarNegatiacaoParaImovel(imovel, negativador);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * [UC0678] Relatório Negativador Resultado Simulacao
	 * pesquisar Negativador Resultado Simulacao
	 * 
	 * @author Yara Taciane
	 * @date 09/05/2008
	 * @param idNegativacaoComando
	 * @return NegativadorResultadoSimulacao
	 */
	public NegativacaoCriterio pesquisarNegativacaoCriterio(Integer idNegativacaoComando) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarNegativacaoCriterio(idNegativacaoComando);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Método usado para pesquisar Negativador Movimento usado no caso de uso [UC0682]
	 * 
	 * @author Yara Taciane
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper, Integer numeroPagina)
					throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			retorno = repositorioSpcSerasa.pesquisarNegativadorMovimento(negativadorMovimentoHelper, numeroPagina);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Método usado para pesquisar Negativador Movimento
	 * usado no caso de uso [UC0682]
	 * 
	 * @author Yara Taciane
	 * @date 16/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimento(NegativadorMovimentoHelper negativadorMovimentoHelper) throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			retorno = repositorioSpcSerasa.pesquisarNegativadorMovimento(negativadorMovimentoHelper);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Método usado para apresentar os registros de negativadorMovimento Registro aceitos
	 * usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper)
					throws ControladorException{

		Collection retorno;

		try{
			retorno = repositorioSpcSerasa.pesquisarNegativadorMovimentoRegistroAceito(negativadorMovimentoHelper);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Método usado para apresentar os registros de negativadorMovimento Registro aceitos
	 * usado no caso de uso [UC0681]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarNegativadorMovimentoRegistroAceito(NegativadorMovimentoHelper negativadorMovimentoHelper,
					Integer numeroPagina) throws ControladorException{

		Collection retorno;

		try{
			retorno = repositorioSpcSerasa.pesquisarNegativadorMovimentoRegistroAceito(negativadorMovimentoHelper, numeroPagina);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Informações Atualizadas em (maior data e hora da última execução
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public List pesquisarNegativadorMovimentoRegItens(Integer idNegativadorMovimentoReg) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarNegativadorMovimentoRegItens(idNegativadorMovimentoReg);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0688] - Gerar Resumo Diario da Negativação
	 * [UC0694] - Gerar Relatório Negativações Excluídas
	 * pesquisa ocorrência na tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO para NMRG_ID=NMRG_ID
	 * da tabela NEGATIVADOR_MOVIMENTO_REG)
	 * 
	 * @author Vivianne Sousa
	 * @data 28/04/2009
	 * @param idNegativadorMovimentoReg
	 * @return RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper
	 */
	public RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper pesquisarNegativadorMovimentoRegParcelamento(
					Integer idNegativadorMovimentoReg) throws ControladorException{

		try{

			RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper retorno = new RelatorioNegativacoesExcluidasSomatorioDadosParcelamentoHelper();

			Collection colecaoNegativadorMovimentoRegParcelamento = repositorioSpcSerasa
							.pesquisarNegativadorMovimentoRegParcelamento(idNegativadorMovimentoReg);
			Iterator iterator = colecaoNegativadorMovimentoRegParcelamento.iterator();

			BigDecimal valorParceladoEntrada = BigDecimal.ZERO;
			BigDecimal valorEntradaPago = BigDecimal.ZERO;
			BigDecimal valorParcelado = BigDecimal.ZERO;
			BigDecimal valorParceladoPago = BigDecimal.ZERO;

			if(colecaoNegativadorMovimentoRegParcelamento != null && !colecaoNegativadorMovimentoRegParcelamento.isEmpty()){

				while(iterator.hasNext()){
					NegativadorMovimentoRegParcelamento nmrp = (NegativadorMovimentoRegParcelamento) iterator.next();

					// somatório de NMRP_VLPARCELADOENTRADA
					valorParceladoEntrada = valorParceladoEntrada.add(nmrp.getValorParceladoEntrada());

					// somatório de NMRP_VLPARCELADOENTRADAPAGO
					if(nmrp.getValorParceladoEntradaPago() != null){
						valorEntradaPago = valorEntradaPago.add(nmrp.getValorParceladoEntradaPago());
					}

					// NMRP_VLPARCELADO da tabela NEGATIVADOR_MOVIMENTO_REG_PARCELAMENTO com
					// NMRP_ICPARCELAMENTOATIVO=1
					// mais (somatório(NMRP_VLPARCELADOCOBRADOPAGO e
					// NMRP_VLPARCELADOCOBRADONAOPAGO)) com NMRP_ICPARCELAMENTOATIVO=2
					if(nmrp.getIndicadorParcelamentoAtivo().equals(ConstantesSistema.SIM)){
						valorParcelado = valorParcelado.add(nmrp.getValorParcelado());
					}else{
						BigDecimal valorCobradoPagoMaisValorCobradoNaoPago = BigDecimal.ZERO;

						if(nmrp.getValorParceladoCobradoPago() != null){
							valorCobradoPagoMaisValorCobradoNaoPago = nmrp.getValorParceladoCobradoPago().add(
											nmrp.getValorParceladoCobradoNaoPago());
						}

						valorParcelado = valorParcelado.add(valorCobradoPagoMaisValorCobradoNaoPago);
					}

					// somatório de NMRP_VLPARCELADOCOBRADOPAGO
					if(nmrp.getValorParceladoCobradoPago() != null){
						valorParceladoPago = valorParceladoPago.add(nmrp.getValorParceladoCobradoPago());
					}

				}

			}

			retorno.setValorParceladoEntrada(valorParceladoEntrada);
			retorno.setValorParceladoEntradaPago(valorEntradaPago);
			retorno.setValorParcelado(valorParcelado);
			retorno.setValorParceladoPago(valorParceladoPago);

			return retorno;

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0678] Relatório Negativador Resultado Simulacao
	 * pesquisar Negativador Resultado Simulacao
	 * 
	 * @author Yara Taciane
	 * @date 09/05/2008
	 * @param idNegativacaoComando
	 * @return NegativadorResultadoSimulacao
	 */
	public Collection pesquisarNegativadorResultadoSimulacao(Integer idNegativacaoComando) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarNegativadorResultadoSimulacao(idNegativacaoComando);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Collection pesquisarRelatorioAcompanhamentoClientesNegativador(DadosConsultaNegativacaoHelper helper)
					throws ControladorException{

		Collection retorno;

		try{
			retorno = repositorioSpcSerasa.pesquisarRelatorioAcompanhamentoClientesNegativador(helper);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Conta a quantidade de Clientes Negativados
	 * [UC0693] Gerar Relatório Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Integer pesquisarRelatorioAcompanhamentoClientesNegativadorCount(DadosConsultaNegativacaoHelper helper)
					throws ControladorException{

		Integer retorno;

		try{
			retorno = repositorioSpcSerasa.pesquisarRelatorioAcompanhamentoClientesNegativadorCount(helper);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Conta a quantidade de Neg
	 * [UC0693] Gerar Relatório Negativacoes Excluidas
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Collection pesquisarRelatorioNegativacoesExcluidas(DadosConsultaNegativacaoHelper helper) throws ControladorException{

		Collection retorno = new ArrayList();

		try{
			Collection coll = repositorioSpcSerasa.pesquisarRelatorioNegativacoesExcluidas(helper);

			if(helper.getPeriodoExclusaoNegativacaoInicio() != null && helper.getPeriodoExclusaoNegativacaoFim() != null){
				Iterator it = coll.iterator();
				while(it.hasNext()){

					NegativadorMovimentoReg negr = (NegativadorMovimentoReg) it.next();

					Date dataExclusao = repositorioSpcSerasa.pesquisarDataExclusaoNegativacao(negr.getImovel().getId(), negr
									.getNegativadorMovimento().getCobrancaAcaoAtividadeComando().getId());

					if((Util.compararData(dataExclusao, helper.getPeriodoExclusaoNegativacaoInicio()) == 1 || Util.compararData(
									dataExclusao, helper.getPeriodoExclusaoNegativacaoInicio()) == 0)
									&& (Util.compararData(dataExclusao, helper.getPeriodoExclusaoNegativacaoFim()) == -1 || Util
													.compararData(dataExclusao, helper.getPeriodoExclusaoNegativacaoFim()) == 0)){

						retorno.add(negr);

					}

				}
			}else{
				retorno = coll;
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Conta a quantidade de Neg
	 * [UC0693] Gerar Relatório Negativacoes Excluidas
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */

	public Integer pesquisarRelatorioNegativacoesExcluidasCount(DadosConsultaNegativacaoHelper helper) throws ControladorException{

		Integer retorno;

		try{
			retorno = repositorioSpcSerasa.pesquisarRelatorioNegativacoesExcluidasCount(helper);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Gerar Relatório de Negativações Excluídas
	 * Pesquisar o somatório do valor paga ou parcelado pelo registro negativador
	 * 
	 * @author Yara T. Souza
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarSomatorioNegativadorMovimentoRegItens(Integer idNegativadorMovimentoReg, Integer idCobrancaDebitoSituacao)
					throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarSomatorioNegativadorMovimentoRegItens(idNegativadorMovimentoReg, idCobrancaDebitoSituacao);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Retorna o somatório do valor do Débito do NegativadoMovimentoReg pela CobrancaDebitoSituacao
	 * [UC0693] Gerar Relatório Acompanhamaneto de
	 * Clientes Negativados
	 * 
	 * @author Yara Taciane
	 * @date 17/03/2008
	 */
	public BigDecimal pesquisarSomatorioValorDebito(NegativadorMovimentoReg negativadorMovimentoReg,
					CobrancaDebitoSituacao cobrancaDebitoSituacao) throws ControladorException{

		BigDecimal retorno;
		try{
			retorno = repositorioSpcSerasa.pesquisarSomatorioValorDebito(negativadorMovimentoReg, cobrancaDebitoSituacao);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	/**
	 * Retorna o somatório do VALOR PAGO e do VALOR CANCELADO [UC0693] Gerar Relatório
	 * Acompanhamaneto de Clientes Negativados
	 * 
	 * @author Vivianne Sousa
	 * @date 29/04/2009
	 */
	public Object[] pesquisarSomatorioValorPagoEValorCancelado(Integer idNegativadorMovimentoReg) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarSomatorioValorPagoEValorCancelado(idNegativadorMovimentoReg);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0651] Inserir Comando Negativação
	 * [SB0003] Determinar Data Prevista para Execução do Comando
	 * 
	 * @author Ana Maria
	 * @date 11/12/2007
	 * @param idNegativador
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarUltimaDataRealizacaoComando(Integer idNegativador, Integer icSimulacao) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarUltimaDataRealizacaoComando(idNegativador, icSimulacao);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Ponto inicial do caso de uso de Registrar Movimento de Retorno dos Negativadores
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores
	 * 
	 * @author Yara Taciane
	 * @throws ErroRepositorioException
	 * @date 10/01/2008
	 */
	public Collection registrarNegativadorMovimentoRetorno(Integer idFuncionalidadeIniciada) throws ControladorException{

		// --------------------------------------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------------------------------------
		int idUnidadeIniciada = 0;
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, 0);

		String nomeArquivo = "";
		Collection collRegistrosLidos = null;

		System.out.println(" [UC0672] Registrar Movimento de Retorno dos Negativadores ");

		try{

			Integer countRegistro = 0;
			String numeroSequencialRetorno = "";
			NegativadorMovimento negativadorMovimento = null;

			Object[] retornoArquivo = getArquivoNegativadorMovimentoRetorno();
			Integer idNegativador = (Integer) retornoArquivo[0];
			StringBuilder stringBuilderTxt = (StringBuilder) retornoArquivo[1];
			Integer quantidadeRegistros = (Integer) retornoArquivo[2];
			nomeArquivo = (String) retornoArquivo[3];

			Negativador negativador = new Negativador();
			negativador.setId(idNegativador);

			if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){

				String constanteHeader = ConstantesAplicacao.get("constante_header");
				StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

				if(stk.hasMoreTokens()){
					// ---------------------------------------------------
					String registro = stk.nextToken();
					// ---------------------------------------------------
					// H.01
					String tipoRegistro = getConteudo(9, 10, registro.toCharArray());

					// --------------------------------------------------------------------------------------
					// Verifica Header (Primeira Linha)
					// ---------------------------------------------------------------------------------------
					if(tipoRegistro.equals(constanteHeader)){

						Object[] retornoHeader = new Object[2];
						try{
							retornoHeader = this.verificarHeaderSPCRetorno(registro, negativador);
							negativadorMovimento = (NegativadorMovimento) retornoHeader[1];
							numeroSequencialRetorno = retornoHeader[2].toString();
							Object[] retorno = processarArquivoMovimentoRetornoSPC(stringBuilderTxt, negativador, negativadorMovimento);
							collRegistrosLidos = (Collection) retorno[0];
						}catch(ErroRepositorioException e){
							e.printStackTrace();
						}
					}
				}

				System.out.println("################################# " + Negativador.NEGATIVADOR_SPC_SAO_PAULO);

			}else if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_BRASIL)){

				String[] reg = new String[2];

				// [SB0001 - Validar Arquivo de Movimento de Retorno do SPC-BRASIL]
				Object[] retorno = this.validarArquivoMovimentoRetornoSPCBrasil(stringBuilderTxt, negativador);

				collRegistrosLidos = (Collection) retorno[0];
				negativadorMovimento = (NegativadorMovimento) retorno[1];

				Object[] registrosLidos = collRegistrosLidos.toArray();

				for(int i = 0; i < registrosLidos.length; i = i + 1){
					if(i % 500 == 0){
						System.out.println("Retorno " + negativadorMovimento.getNumeroSequencialEnvio() + ": Processados " + i + " / "
										+ registrosLidos.length);
					}

					if(i > 0){
						try{
							reg[0] = (String) registrosLidos[i];

							if(i + 1 < registrosLidos.length){
								reg[1] = (String) registrosLidos[i + 1];
							}else{
								reg[1] = null;
							}

							i = i + 1;

							// [SB002 - Atualizar Movimento de Envio do SPC-BRASIL]
							this.atualizarMovimentoEnvioSPCBrasil(reg, negativador, negativadorMovimento);

						}catch(ControladorException e){
							getControladorBatch().encerrarUnidadeProcessamentoBatch(e, idUnidadeIniciada, true);
							envioEmailErroMovimentoRetorno(e, nomeArquivo);
							sessionContext.setRollbackOnly();
							throw new ControladorException("erro.sistema", e);
						}catch(ErroRepositorioException e){
							getControladorBatch().encerrarUnidadeProcessamentoBatch(e, idUnidadeIniciada, true);
							envioEmailErroMovimentoRetorno(e, nomeArquivo);
							sessionContext.setRollbackOnly();
							throw new ControladorException("erro.sistema", e);
						}
					}else{
						// H.04
						numeroSequencialRetorno = getConteudo(18, 8, registrosLidos[i].toString().toCharArray());
					}
				}

				System.out.println("################################# " + Negativador.NEGATIVADOR_SPC_BRASIL);

			}else if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){

				String constanteHeader = ConstantesAplicacao.get("constante_header");
				StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

				if(stk.hasMoreTokens()){
					// ---------------------------------------------------
					String registro = stk.nextToken();
					// ---------------------------------------------------

					registro = Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(registro, 250);


					// H.01
					String tipoRegistro = getConteudo(9, 10, registro.toCharArray());

					// --------------------------------------------------------------------------------------
					// Verifica Header (Primeira Linha)
					// ---------------------------------------------------------------------------------------
					if(tipoRegistro.equals(constanteHeader)){

						Object[] retornoHeader = new Object[2];
						try{
							retornoHeader = this.verificarHeaderSPCBoaVistaRetorno(registro, negativador);
							negativadorMovimento = (NegativadorMovimento) retornoHeader[1];
							numeroSequencialRetorno = retornoHeader[2].toString();
							processarArquivoMovimentoRetornoSPCBoaVista(stringBuilderTxt, negativador,
											negativadorMovimento);

						}catch(ErroRepositorioException e){
							e.printStackTrace();
						}
					}
				}

				System.out.println("################################# " + Negativador.NEGATIVADOR_SPC_BOA_VISTA);

			}else if(idNegativador.equals(Negativador.NEGATIVADOR_SERASA)){
				System.out.println("################################# " + Negativador.NEGATIVADOR_SERASA);

				// [FS0006 – Verificar a existência do registro de envio].
				Object[] retorno = validarArquivoMovimentoRetornoSERASA(stringBuilderTxt, negativador);
				collRegistrosLidos = (Collection) retorno[0];
				Iterator it = collRegistrosLidos.iterator();
				negativadorMovimento = (NegativadorMovimento) retorno[1];
				while(it.hasNext()){
					String registro = (String) it.next();
					countRegistro = countRegistro + 1;
					if(countRegistro > 1){
						// [SB0005 – Atualizar Registro de Envio].
						this.atualizarMovimentoEnvioSERASA(registro, negativador, negativadorMovimento);
					}else{
						// H.09
						numeroSequencialRetorno = getConteudo(120, 6, registro.toCharArray());
					}

				}
			}

			// -----------------------------------------------------------------------------------------------------
			/*
			 * 6. O sistema atualiza o número seqüencial do arquivo (NSA) de retorno
			 * (NGCN_NNNSARETORNO da tabela NEGATIVADOR_CONTRATO)
			 * com o número seqüencial do arquivo (NSA) de envio (NGCN_NNNSAENVIO).
			 */

			NegativadorContrato negativadorContrato = new NegativadorContrato();

			negativadorContrato = this.repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador.getId());

			negativadorContrato.setNumeroSequencialRetorno(Util.converterStringParaInteger(numeroSequencialRetorno));

			System.out.println("################################# " + numeroSequencialRetorno);

			// -------------------------------------------------------------
			repositorioUtil.atualizar(negativadorContrato);
			// -------------------------------------------------------------

			/*
			 * 7.O sistema atualiza os dados de retorno do movimento –
			 * atualiza a tabela NEGATIVADOR_MOVIMENTO com os seguintes valores:
			 */
			negativadorMovimento.setDataRetorno(new Date());
			negativadorMovimento.setDataProcessamentoRetorno(new Date());
			negativadorMovimento.setNumeroSequencialRetorno(Util.converterStringParaInteger(numeroSequencialRetorno));
			negativadorMovimento.setNumeroRegistrosRetorno(quantidadeRegistros);
			negativadorMovimento.setUltimaAlteracao(new Date());

			// --------------------------------------------------------------
			repositorioUtil.atualizar(negativadorMovimento);
			// --------------------------------------------------------------

			getControladorBatch().encerrarUnidadeProcessamentoBatch(null, idUnidadeIniciada, false);

		}catch(Exception ex){
			getControladorBatch().encerrarUnidadeProcessamentoBatch(ex, idUnidadeIniciada, true);
			envioEmailErroMovimentoRetorno(ex, nomeArquivo);
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return collRegistrosLidos;
	}

	/**
	 * [UC0317] Manter Comando de Negativação por Critério
	 * [SB0001] Excluir Comando de Negativação por Critério
	 * 
	 * @author Ana Maria
	 * @param ids
	 * @param usuarioLogado
	 * @created 21/01/2008
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerComandoNegativacaoPorCriterio(String[] ids) throws ControladorException{

		try{
			for(int i = 0; i < ids.length; i++){

				NegativacaoCriterio negativacaoCriterio = repositorioSpcSerasa.pesquisarNegativacaoCriterio(Integer.parseInt(ids[i]));

				/*
				 * Remove Titularidades do CPF/CNPJ da Negativação, Subcategorias, Perfis de imóvel,
				 * Tipos de cliente, Grupos de Cobrança, Gerências Regionais, Unidades Negócio,
				 * Elos Pólo do critério
				 */

				repositorioSpcSerasa.removerParametrosCriterio(negativacaoCriterio.getId());

				// Remove Negativação Critério
				getControladorUtil().remover(negativacaoCriterio);

				// Remove Negativação Comando
				repositorioSpcSerasa.removerNegativacaoComando(Integer.parseInt(ids[i]));

			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores
	 * Validar Arquivo Movimento Retorno Online.
	 * 
	 * @author Yara T. Souza
	 * @date 09/12/2008
	 * @return Object[]
	 * @throws ControladorException
	 */
	public Object[] validarArquivoMovimentoRetorno(StringBuilder stringBuilderTxt, Negativador negativador) throws ControladorException{

		Object[] retorno = null;
		if(negativador.getId().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
			retorno = validarArquivoMovimentoRetornoSPCSaoPaulo(stringBuilderTxt, negativador);
		}else if(negativador.getId().equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
			retorno = validarArquivoMovimentoRetornoSPCBrasil(stringBuilderTxt, negativador);
		}else if(negativador.getId().equals(Negativador.NEGATIVADOR_SERASA)){
			retorno = validarArquivoMovimentoRetornoSERASA(stringBuilderTxt, negativador);
		}else if(negativador.getId().equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
			retorno = validarArquivoMovimentoRetornoSPCBoaVista(stringBuilderTxt, negativador);
		}

		return retorno;
	}

	/**
	 * [UC0651] Inserir Comando Negativação
	 * [FS0026] Verificar existência de comando para o negativador na data
	 * 
	 * @author Ana Maria
	 * @date 07/05/2008
	 * @param idNegativador
	 * @param Data
	 * @return boolean
	 */
	public boolean verificarExistenciaComandoNegativador(String idNegativador, Date dataPrevista) throws ControladorException{

		try{
			return repositorioSpcSerasa.verificarExistenciaComandoNegativador(idNegativador, dataPrevista);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Método usado para contar a quantidade de ocorrências de negativadorMovimento Registro aceitos
	 * usado no caso de uso [UC061]
	 * 
	 * @author Yara Taciane
	 * @date 22/01/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer verificarTotalRegistrosAceitos(Integer idNegativadorMovimento) throws ControladorException{

		Integer retorno;

		try{
			retorno = repositorioSpcSerasa.verificarTotalRegistrosAceitos(idNegativadorMovimento);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores
	 * Busca o Arquivo salvo na pasta bin , para processar Movimento de Retorno da Negativação.
	 * 
	 * @author Yara T. Souza
	 * @date 09/12/2008
	 * @return Object[]
	 * @throws ControladorException
	 */
	private Object[] getArquivoNegativadorMovimentoRetorno() throws ControladorException{

		// cria uma string builder que recupera o txt e joga para o
		// controlador para o processamento
		StringBuilder stringBuilderTxt = new StringBuilder();
		Integer idNegativador = null;
		Object[] retorno = new Object[4];

		// cria um contador para saber quantas linhas terá o txt
		int quantidadeRegistros = 0;

		Date data = new Date();
		String AAAAMMDD = Util.formatarDataAAAAMMDD(data);
		// String HHMM = Util.formatarDataHHMM(data);
		String formatodatahora = AAAAMMDD;
		String nomeArquivo = "REG_SPC_SERASA" + formatodatahora;

		File file = new File(ConstantesConfig.getPathRepositorioAnexos() + File.separator + nomeArquivo);
		FileInputStream fin = null;
		InputStreamReader reader = null;
		BufferedReader buffer = null;
		try{
			fin = new FileInputStream(file);
			reader = new InputStreamReader(fin);
			buffer = new BufferedReader(reader);
			// cria uma variavel do tipo boolean
			boolean eof = false;
			boolean primeiraLinha = true;
			// enquanto a variavel for false
			while(!eof){
				// pega a linha do arquivo
				String linhaLida = buffer.readLine();

				if(primeiraLinha){
					String identificacaoArquivo = "";
					identificacaoArquivo = getConteudo(103, 2, linhaLida.toCharArray());
					// .......................................
					// Se não for SPC é SERASA
					// .......................................
					if(!identificacaoArquivo.equals(ConstantesAplicacao.get("versao_spc_sao_paulo"))){
						identificacaoArquivo = getConteudo(105, 15, linhaLida.toCharArray());
						if(identificacaoArquivo.toUpperCase().equals("SERASA-CONVEM04")){
							idNegativador = Negativador.NEGATIVADOR_SERASA;
						}else{
							identificacaoArquivo = getConteudo(323, 2, linhaLida.toCharArray());
							if(identificacaoArquivo.equals(ConstantesAplicacao.get("versao_spc_brasil"))){
								idNegativador = Negativador.NEGATIVADOR_SPC_BRASIL;
							}
						}
					}else{

						identificacaoArquivo = getConteudo(1, 8, linhaLida.toCharArray());

						if(identificacaoArquivo.toUpperCase().equals(ConstantesAplicacao.get("identificacao_arquivo"))){
							idNegativador = Negativador.NEGATIVADOR_SPC_BOA_VISTA;
						}else{
							idNegativador = Negativador.NEGATIVADOR_SPC_SAO_PAULO;
						}


					}

					primeiraLinha = false;
				}

				// se for a ultima linha do arquivo
				if(linhaLida != null && linhaLida.length() > 0 && !linhaLida.equals("")){
					stringBuilderTxt.append(linhaLida);
					stringBuilderTxt.append("\n");
					quantidadeRegistros = quantidadeRegistros + 1;
				}else{
					break;
				}
			}

		}catch(FileNotFoundException e1){

			e1.printStackTrace();
		}catch(IOException e){

			e.printStackTrace();
		}finally{
			IoUtil.fecharStream(buffer);
			IoUtil.fecharStream(reader);
			IoUtil.fecharStream(fin);
		}

		retorno[0] = idNegativador;
		retorno[1] = stringBuilderTxt;
		retorno[2] = quantidadeRegistros;
		retorno[3] = nomeArquivo;

		return retorno;

	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672] Registrar
	 * Movimento de Retorno dos Negativadores
	 * [SB0003] - Validar Arquivo de Movimento de Retorno do SERASA
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 */
	private Object[] validarArquivoMovimentoRetornoSERASA(StringBuilder stringBuilderTxt, Negativador negativador)
					throws ControladorException{

		// ----------------------------------------------------------------
		// [SB0003] - Validar Arquivo de Movimento de Retorno do SERASA
		// ----------------------------------------------------------------

		Object[] retorno = new Object[2];
		String numeroSequencialArquivo = "";
		String numeroRegistro = "";
		int countRegistro = 0;
		NegativadorMovimento negativadorMovimento = null;

		Collection collRegistrosLidos = new ArrayList();

		try{
			StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

			boolean ehHeader = true;
			while(stk.hasMoreTokens()){

				countRegistro = countRegistro + 1;

				String registro = stk.nextToken();

				// H.01
				String tipoRegistro = getConteudo(1, 1, registro.toCharArray());

				// --------------------------------------------------------------------------------------
				// Verifica Header (Primeira Linha)
				// ---------------------------------------------------------------------------------------
				if(countRegistro == 1){

					// 2.
					if(!tipoRegistro.toUpperCase().equals("0")){
						throw new ControladorException("atencao.arquivo.movimento.negativador.header");
					}

					// H.02
					String cnpj = getConteudo(2, 9, registro.toCharArray());
					// 3.
					// Pega as informações de Sistema Parâmetros
					SistemaParametro sistemaParametros = null;
					sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();

					String cnpjEmpresa = null;
					if(sistemaParametros != null && sistemaParametros.getCnpjEmpresa() != null){
						cnpjEmpresa = "0" + sistemaParametros.getCnpjEmpresa().substring(0, 8);
					}

					if(!cnpj.toUpperCase().equals(cnpjEmpresa)){
						throw new ControladorException("atencao.cnpj_nao_corresponde_ao_contratante");
					}

					// H.03
					String dataMovimento = getConteudo(11, 8, registro.toCharArray());
					// 4.
					if(!Util.validarDiaMesAno(dataMovimento)){
						throw new ControladorException("atencao.data_movimento_invalida", null, dataMovimento);
					}

					// H.08
					// 9.
					String identificacaoArquivoFixo = getConteudo(105, 15, registro.toCharArray());
					if(!identificacaoArquivoFixo.toUpperCase().equals("SERASA-CONVEM04")){
						throw new ControladorException("atencao.identificacao_arquivo_invalida", null, identificacaoArquivoFixo);
					}

					// H.10
					// 10.
					String codigoEnvioArquivo = getConteudo(126, 1, registro.toCharArray());
					if(!codigoEnvioArquivo.toUpperCase().equals("R")){
						throw new ControladorException("atencao.codigo_envio_arquivo_invalido", null, codigoEnvioArquivo);
					}

					// 12.
					NegativadorContrato negativadorContrato = new NegativadorContrato();

					negativadorContrato = this.repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador.getId());

					String numeroSequencialEnvioBD = negativadorContrato.getNumeroSequencialEnvio() + "";

					String qtdZeros = "";

					int tamanho = 6 - numeroSequencialEnvioBD.length();

					for(int i = 0; i < tamanho; i++){
						qtdZeros = qtdZeros + "0";
					}

					numeroSequencialEnvioBD = qtdZeros + numeroSequencialEnvioBD;

					// H.09
					numeroSequencialArquivo = getConteudo(120, 6, registro.toCharArray());

					// 8.
					if(Util.converterStringParaInteger(numeroSequencialArquivo) > Util.converterStringParaInteger(numeroSequencialEnvioBD)){
						throw new ControladorException("atencao.movimento_fora_sequencia");
					}

					// 13.
					negativadorMovimento = this.repositorioSpcSerasa.getNegativadorMovimento(negativador, Util
									.converterStringParaInteger(numeroSequencialArquivo), true);
					if(negativadorMovimento == null){
						throw new ControladorException("atencao.arquivo_negativador_movimento_retorno_inexistente");
					}
					if(negativadorMovimento != null && negativadorMovimento.getDataRetorno() != null){
						throw new ControladorException("atencao.movimento_retorno_ja_processado");
					}
					// ----------------------------------------------------------------------------------

					// 8.
					// H.14
					/*
					 * [FS0006 – Verificar a existência do registro de envio],
					 */
					numeroRegistro = getConteudo(594, 7, registro.toCharArray());
					Integer numRegistro = Util.converterStringParaInteger(numeroRegistro);

					NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(
									negativadorMovimento, numRegistro);

					if(negativadorMovimentoReg.getConteudoRegistro() == null){
						throw new ControladorException("atencao.arquivo_movimento_sem_registros");
					}

					String[] reg = new String[2];
					reg[0] = registro;

					Object[] negativadorMovimentoRegistros = new Object[2];
					negativadorMovimentoRegistros[0] = negativadorMovimentoReg;

					/*
					 * [FS0012 - Verificar erro de Header/Trailler no arquivo retorno], passando o
					 * conteúdo do primeiro registro de movimento.
					 */
					verificarErroHeaderTraillerNoArquivoRetorno(negativador, reg);

					// 15.
					// [SB0005 – Atualizar Registro de Envio].
					this.atualizarRegistroEnvio(negativador, registro, negativadorMovimentoReg);

				}

				// ----------------------------------------------------------------------------------------

				// [FS0003 – Verificar a existência do registro tipo trailler],
				if(tipoRegistro.equals("9")){

				}else if(stk.hasMoreTokens() == false && !tipoRegistro.equals("9")){
					throw new ControladorException("atencao.arquivo_movimento_nao_possui_trailler");
				}

				// [FS0004 – Verificar a existência de registros com tipo inválido],
				if(!tipoRegistro.equals("9") && !tipoRegistro.equals("1") && !tipoRegistro.equals("0") && countRegistro > 1){
					throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_tipos_invalidos");
				}

				// [FS0005 – Verificar a existência de registros com número de sequência inválida]
				String numeroSequencia = getConteudo(594, 7, registro.toCharArray()).trim();

				int numSequencia = Util.converterStringParaInteger(numeroSequencia.trim());

				if(numSequencia != countRegistro){
					throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_sequencia_invalida");
				}

				NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(
								negativadorMovimento, numSequencia);

				if(negativadorMovimentoReg != null && negativadorMovimentoReg.getConteudoRegistro() == null){
					throw new ControladorException("atencao.arquivo_movimento_sem_registros");
				}

				String[] regLinha = new String[2];
				regLinha[0] = registro;

				Object[] negativadorMovimentoRegistrosLinha = new Object[2];
				negativadorMovimentoRegistrosLinha[0] = negativadorMovimentoReg;

				/*
				 * [FS0012 - Verificar erro de Header/Trailler no arquivo retorno], passando o
				 * conteúdo do último registro de movimento. //VERIFICAÇÃO DO TRAILLER
				 */
				if(tipoRegistro.equals("9") || ehHeader){
					verificarErroHeaderTraillerNoArquivoRetorno(negativador, regLinha);
					ehHeader = false;
				}

				collRegistrosLidos.add(registro);
			}

			if(negativadorMovimento.getNumeroRegistrosEnvio() != countRegistro){
				throw new ControladorException("atencao.total_registro_do_arquivo_invalido");
			}

			retorno[0] = collRegistrosLidos;
			retorno[1] = negativadorMovimento;
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;

		// ------------------------------------------------------------------

	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672] Registrar
	 * Movimento de Retorno dos Negativadores
	 * [SB0005] - Atualizar Registro de Envio
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private void atualizarRegistroEnvio(Negativador negativador, String registro, NegativadorMovimentoReg negativadorMovimentoReg)
					throws ControladorException, ErroRepositorioException{

		short indicadorRegistroAceito = -1;
		String tipoRegistro = "";
		String codigoRetorno = "-1";
		String sequencialArquivo = "";
		String sequencialRegistro = "";
		String campoCodigoRetorno = "";

		// escolher cobrança situação
		Integer idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
		if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SERASA)){
			idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
		}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
			idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP;
		}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
			idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL;
		}

		if(Negativador.NEGATIVADOR_SERASA.equals(negativador.getId())){

			// --------------------------------------------------------------------------------------
			// [SB005] - Atualizar Registro de Envio para SERASA
			// --------------------------------------------------------------------------------------

			// -------------------------------------------------------------------------------------
			// Comparar apenas o sequencial do Arquivo, pois a SERASA altera o conteúdo do registro.
			// --------------------------------------------------------------------------------------
			String qtdZeros = "";
			sequencialArquivo = getConteudo(594, 7, registro.toCharArray());

			sequencialRegistro = negativadorMovimentoReg.getNumeroRegistro() + "";

			int tamanho = 7 - sequencialRegistro.length();

			for(int i = 0; i < tamanho; i++){
				qtdZeros = qtdZeros + "0";
			}

			sequencialRegistro = qtdZeros + sequencialRegistro;
			if(!tipoRegistro.equals("0") && !tipoRegistro.equals("9")){

				if(!sequencialArquivo.equals(sequencialRegistro)){
					throw new ControladorException("atencao.conteudo_registro_nao_corresponde_ao_enviado", null, sequencialArquivo);
				}
			}

			// 2.0
			// --------------------------------------------------------------------------------
			// atribuir o valor um ao campo indicador registro aceito
			indicadorRegistroAceito = 1;
			// -------------------------------------------------------------------------------

			// Para cada ocorrência do código de retorno
			// Caso o registro seja do SERASA
			// -------------------------------------------------------------------------------
			// 3.0
			campoCodigoRetorno = getConteudo(534, 60, registro.toCharArray()).trim();
			if("".equals(campoCodigoRetorno)){

				codigoRetorno = "0";
				Integer codRetorno = Util.converterStringParaInteger(codigoRetorno);

				NegativadorMovimentoRegRetMot negativadorMovimentoRegRetMot = new NegativadorMovimentoRegRetMot();
				negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);

				FiltroNegativadorRetornoMotivo fnrm = new FiltroNegativadorRetornoMotivo();
				fnrm
								.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.CODIGO_RETORNO_MOTIVO, codRetorno
												.shortValue()));
				fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.NEGATIVADOR_RETORNO_MOTIVO_NEGATIVADOR,
								Negativador.NEGATIVADOR_SERASA));

				NegativadorRetornoMotivo negativadorRetornoMot = (NegativadorRetornoMotivo) Util.retonarObjetoDeColecao(RepositorioUtilHBM
								.getInstancia().pesquisar(fnrm, NegativadorRetornoMotivo.class.getName()));

				// [FS007] - Verifica a exixtência do motivo de retorno
				if(negativadorRetornoMot != null){

					negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);
					negativadorMovimentoRegRetMot.setNegativadorRetornoMotivo(negativadorRetornoMot);
					negativadorMovimentoRegRetMot.setUltimaAlteracao(new Date());

					// [FS008] -Verifica a indicação de registro aceito
					// Caso não corresponda a aceito
					if(negativadorRetornoMot.getIndicadorRegistroAceito() != 1){
						indicadorRegistroAceito = 2;
						negativadorRetornoMot.setIndicadorRegistroAceito(indicadorRegistroAceito);
					}

					// ****************************************************************************
					this.inserirNegativadorMovimentoRegRetMot(negativadorMovimentoRegRetMot);
					// ****************************************************************************

				}else{
					throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
				}

			}else if(!"".equals(campoCodigoRetorno)){

				// int cont = 0;
				for(int j = 0; j <= campoCodigoRetorno.length() - 3; j = j + 3){

					codigoRetorno = campoCodigoRetorno.substring(j, j + 3);

					if(!codigoRetorno.equals("")){
						Integer codRetorno = Util.converterStringParaInteger(codigoRetorno);

						NegativadorMovimentoRegRetMot negativadorMovimentoRegRetMot = new NegativadorMovimentoRegRetMot();
						negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);

						FiltroNegativadorRetornoMotivo fnrm = new FiltroNegativadorRetornoMotivo();
						fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.CODIGO_RETORNO_MOTIVO, codRetorno
										.shortValue()));
						fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.NEGATIVADOR_RETORNO_MOTIVO_NEGATIVADOR,
										Negativador.NEGATIVADOR_SERASA));

						NegativadorRetornoMotivo negativadorRetornoMot = (NegativadorRetornoMotivo) Util
										.retonarObjetoDeColecao(RepositorioUtilHBM.getInstancia().pesquisar(fnrm,
														NegativadorRetornoMotivo.class.getName()));

						// [FS007] - Verifica a exixtência do motivo de retorno
						if(negativadorRetornoMot != null){

							negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);
							negativadorMovimentoRegRetMot.setNegativadorRetornoMotivo(negativadorRetornoMot);
							negativadorMovimentoRegRetMot.setUltimaAlteracao(new Date());

							// [FS008] -Verifica a indicação de registro aceito
							// Caso não corresponda a aceito
							if(negativadorRetornoMot.getIndicadorRegistroAceito() != 1){
								indicadorRegistroAceito = 2;
								negativadorRetornoMot.setIndicadorRegistroAceito(indicadorRegistroAceito);
							}

							// ****************************************************************************
							this.inserirNegativadorMovimentoRegRetMot(negativadorMovimentoRegRetMot);
							// ****************************************************************************

						}else{
							throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
						}
					}

				}

				// *****
			}
			// *****

		}
		// 4.0
		// ------------------------------------------------------------------------------------------------
		negativadorMovimentoReg.setIndicadorAceito(indicadorRegistroAceito);
		// -------------------------------------------------------------------------------------------------
		// [início] - Alteração 05/05/2008 - Indicar Exclusão do imóvel caso a inclusão da
		// negativação não seja aceita.
		// -------------------------------------------------------------------------------------------------
		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		// 5.0
		if(indicadorRegistroAceito == ConstantesSistema.NAO_ACEITO && negativadorMovimento.getCodigoMovimento() == 1){

			Imovel imovel = negativadorMovimentoReg.getImovel();

			if(imovel != null){
				FiltroNegativacaoImoveis filtroNegativacaoImoveis = new FiltroNegativacaoImoveis();
				filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.IMOVEL_ID, imovel.getId()));
				filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.NEGATIVADOR_ID, negativador
								.getId()));
				// adicionado por Vivianne Sousa 09/06/2009
				if(negativadorMovimentoReg.getNegativadorMovimento().getNegativacaoComando() != null){
					filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.NEGATIVACAO_COMANDO_ID,
									negativadorMovimentoReg.getNegativadorMovimento().getNegativacaoComando().getId()));
				}else if(negativadorMovimentoReg.getNegativadorMovimento().getCobrancaAcaoAtividadeComando() != null){
					filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(
									FiltroNegativacaoImoveis.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, negativadorMovimentoReg
													.getNegativadorMovimento().getCobrancaAcaoAtividadeComando().getId()));
				}else if(negativadorMovimentoReg.getNegativadorMovimento().getCobrancaAcaoAtividadeCronograma() != null){
					filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(
									FiltroNegativacaoImoveis.COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID, negativadorMovimentoReg
													.getNegativadorMovimento().getCobrancaAcaoAtividadeCronograma().getId()));
				}

				try{
					NegativacaoImovei negativacaoImoveis = (NegativacaoImovei) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(
									filtroNegativacaoImoveis, NegativacaoImovei.class.getName()));

					if(negativacaoImoveis != null){
						// 5.1
						short indicadorExcluido = 1;
						negativacaoImoveis.setIndicadorExcluido(indicadorExcluido);

						Date dataExclusao = new Date();
						negativacaoImoveis.setDataExclusao(dataExclusao);

						Date ultimaAlteracao = new Date();
						negativacaoImoveis.setUltimaAlteracao(ultimaAlteracao);

						// indicar a exclusão do imóvel.
						repositorioUtil.atualizar(negativacaoImoveis);

						// 5.2
						FiltroImovel filtroImovel = new FiltroImovel();
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.COBRANCA_SITUACAO_ID, idCobrancaSituacao));

						Imovel imovelRetorno = (Imovel) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(filtroImovel, Imovel.class
										.getName()));

						if(imovelRetorno != null){
							imovelRetorno.setCobrancaSituacao(null);
							imovelRetorno.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(imovelRetorno);
						}

						// 5.3
						CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
						cobrancaSituacao.setId(idCobrancaSituacao);

						ImovelCobrancaSituacao imovelCobrancaSituacao = repositorioSpcSerasa.getImovelCobrancaSituacao(imovel,
										cobrancaSituacao);

						if(imovelCobrancaSituacao != null){
							imovelCobrancaSituacao.setDataRetiradaCobranca(new Date());
							imovelCobrancaSituacao.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(imovelCobrancaSituacao);
						}

						// adicionado por Vivianne Sousa - 21/08/2009 - analista: Fatima Sampaio
						// 6.4.Atualizar a situação de cobrança do imóvel na tabela
						// NEGATIVADOR_MOVIMENTO_REG
						negativadorMovimentoReg.setCobrancaSituacao(cobrancaSituacao);
						negativadorMovimentoReg.setUltimaAlteracao(new Date());
						repositorioUtil.atualizar(negativadorMovimentoReg);
					}

				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}

			}

		}

		// 9.
		verificarRegistroInclusao(negativadorMovimentoReg, indicadorRegistroAceito, negativador);

		// -------------------------------------------------------------------------------------------------
		// [fim] - Alteração 05/05/2008 - Indicar Exclusão do imóvel caso a inclusão da negativação
		// não seja aceita.
		// -------------------------------------------------------------------------------------------------

		try{
			negativadorMovimentoReg.setIndicadorAceito(indicadorRegistroAceito);
			negativadorMovimentoReg.setUltimaAlteracao(new Date());
			System.out.println("Atualiza nmrg = " + negativadorMovimentoReg.getId());

			repositorioUtil.atualizar(negativadorMovimentoReg);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672]
	 * Verifica o negativadorMovimentoRegRetMot do Registro 02 do SPC
	 * e atualiza o negativadorMovimentoReg com o indicador de Aceito com valor 1 ou 2
	 * [inserir Negativador Movimento Reg Ret Mot]
	 * 
	 * @author Yara Taciane
	 * @date 04/12/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	private void inserirNegativadorMovimentoRegRetMot(NegativadorMovimentoReg negativadorMovimentoReg,
					NegativadorRetornoMotivo negativadorRetornoMot) throws ControladorException{

		NegativadorMovimentoRegRetMot negativadorMovimentoRegRetMot = new NegativadorMovimentoRegRetMot();
		negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);
		negativadorMovimentoRegRetMot.setNegativadorRetornoMotivo(negativadorRetornoMot);
		negativadorMovimentoRegRetMot.setUltimaAlteracao(new Date());

		try{
			repositorioUtil.inserir(negativadorMovimentoRegRetMot);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672]
	 * Verifica o negativadorMovimentoRegRetMot do Registro 02 do SPC
	 * e atualiza o negativadorMovimentoReg com o indicador de Aceito com valor 1 ou 2
	 * [inserir Negativador Movimento Reg Ret Mot]
	 * 
	 * @author Yara Taciane
	 * @date 04/12/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	private void inserirNegativadorMovimentoRegRetMot(NegativadorMovimentoRegRetMot negativadorMovimentoRegRetMot)
					throws ControladorException{

		/*
		 * Método que verifica se já foi inserido Motivo de Retorno para o registro do negativador.
		 */
		if(negativadorMovimentoRegRetMot != null){
			try{
				Integer idNegativadorMovimentoRegRetMot = this.repositorioSpcSerasa.pesquisarNegativadorMovimentoRegRetMot(
								negativadorMovimentoRegRetMot.getNegativadorMovimentoReg().getId(), negativadorMovimentoRegRetMot
												.getNegativadorRetornoMotivo().getId());

				if(idNegativadorMovimentoRegRetMot == null){
					repositorioUtil.inserir(negativadorMovimentoRegRetMot);
				}

			}catch(ErroRepositorioException e){
				throw new ControladorException("erro.sistema", e);
			}
		}
	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672]
	 * Verifica o negativadorMovimentoRegRetMot do Registro 01 do SPC
	 * e atualiza o negativadorMovimentoReg com o indicador de Aceito com valor 1 ou 2
	 * [Verificar Registro 01 do SPC]
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private void verificarRegistroSPCBrasil01(Object[] registro, Negativador negativador, NegativadorMovimentoReg negativadorMovimentoReg)
					throws ControladorException, ErroRepositorioException{

		try{
			short indicadorRegistroAceito = -1;
			String codigoRetorno = "-1";
			String campoCodigoRetorno1 = "";
			String campoCodigoRetorno2 = "";

			// --------------------------------------------------------------------------------------
			// [SB005] - Atualizar Registro de Envio para SPC
			// --------------------------------------------------------------------------------------
			// 1.0
			// ---------------------------------------------------------------------------------------

			// 2.0
			// --------------------------------------------------------------------------------
			// atribuir o valor um ao campo indicador registro aceito
			indicadorRegistroAceito = 1;
			// -------------------------------------------------------------------------------

			// Para cada ocorrência do código de retorno
			// Caso o registro seja do SPC
			// 3.0
			// -------------------------------------------------------------------------------
			String registro1 = (String) registro[0];
			String registro2 = (String) registro[1];
			// ------------------------------------------------------------------------------
			// Para o REGISTRO 01
			// ------------------------------------------------------------------------------

			if(!Util.isVazioOuBranco(registro1) && !Util.isVazioOuBranco(registro2)){
				campoCodigoRetorno1 = getConteudo(325, 10, registro1.toCharArray()).trim();
				campoCodigoRetorno2 = getConteudo(325, 10, registro2.toCharArray()).trim();

				if(campoCodigoRetorno1.equals("0000000000") && !campoCodigoRetorno1.equals("")){
					if(!campoCodigoRetorno2.equals("0000000000") && !campoCodigoRetorno2.equals("")){
						campoCodigoRetorno1 = "0000000098";
					}
				}

			}else if(!Util.isVazioOuBranco(registro1)){
				campoCodigoRetorno1 = getConteudo(325, 10, registro1.toCharArray()).trim();
			}

			int cont = 0;
			for(int j = 0; j < 9; j = j + 2){
				codigoRetorno = campoCodigoRetorno1.substring(j, j + 2);

				if(codigoRetorno.equals("00")){
					cont = cont + 1;
				}

				if(!codigoRetorno.equals("00") || (cont == 5 && codigoRetorno.equals("00"))){
					Integer codRetorno = Util.converterStringParaInteger(codigoRetorno);

					NegativadorMovimentoRegRetMot negativadorMovimentoRegRetMot = new NegativadorMovimentoRegRetMot();
					negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);

					FiltroNegativadorRetornoMotivo fnrm = new FiltroNegativadorRetornoMotivo();
					fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.CODIGO_RETORNO_MOTIVO, codRetorno));
					fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.NEGATIVADOR_RETORNO_MOTIVO_NEGATIVADOR,
									Negativador.NEGATIVADOR_SPC_BRASIL));

					NegativadorRetornoMotivo negativadorRetornoMot = (NegativadorRetornoMotivo) Util
									.retonarObjetoDeColecao(RepositorioUtilHBM.getInstancia().pesquisar(fnrm,
													NegativadorRetornoMotivo.class.getName()));

					// [FS007] - Verifica a exixtência do motivo de
					if(negativadorRetornoMot != null){
						negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);
						negativadorMovimentoRegRetMot.setNegativadorRetornoMotivo(negativadorRetornoMot);
						negativadorMovimentoRegRetMot.setUltimaAlteracao(new Date());

						// [FS008] -Verifica a indicação de registro aceito
						// Caso não corresponda a aceito
						if(negativadorRetornoMot.getIndicadorRegistroAceito() != 1){
							indicadorRegistroAceito = 2;
							negativadorRetornoMot.setIndicadorRegistroAceito(indicadorRegistroAceito);
						}
						// ************************************************************************
						// 03/12/2008
						// Consultar antes, e só inserir se não existir.
						// repositorioUtil.inserir(negativadorMovimentoRegRetMot);
						this.inserirNegativadorMovimentoRegRetMot(negativadorMovimentoRegRetMot);
						// ************************************************************************
					}else{
						throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
					}
				}
			}

			// ----------------------------------------------------------------------------------------------
			// 4.0
			// ------------------------------------------------------------------------------------------------
			/*
			 * 7. O sistema atualiza o registro de envio – atualiza a tabela
			 * NEGATIVADOR_MOVIMENTO_REG com os seguintes valores:
			 */
			negativadorMovimentoReg.setIndicadorAceito(indicadorRegistroAceito);
			negativadorMovimentoReg.setUltimaAlteracao(new Date());
			repositorioUtil.atualizar(negativadorMovimentoReg);

			// -------------------------------------------------------------------------------------------------
			// [início] - Alteração 05/05/2008 - Indicar Exclusão do imóvel caso a inclusão da
			// negativação não seja aceita.
			// -------------------------------------------------------------------------------------------------

			// 9.
			verificarRegistroInclusao(negativadorMovimentoReg, indicadorRegistroAceito, negativador);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	private void verificarRegistroInclusao(NegativadorMovimentoReg negativadorMovimentoReg, short indicadorRegistroAceito,
					Negativador negativador) throws ControladorException, ErroRepositorioException{

		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		// escolher cobrança situação
		Integer idCobrancaSituacaoEmAnaliseNegativacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
		Integer idCobrancaSituacaoCartaEnviada = CobrancaSituacao.CARTA_ENVIADA_A_SERASA;
		if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SERASA)){
			idCobrancaSituacaoEmAnaliseNegativacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
			idCobrancaSituacaoCartaEnviada = CobrancaSituacao.CARTA_ENVIADA_A_SERASA;
		}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
			idCobrancaSituacaoEmAnaliseNegativacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP;
			idCobrancaSituacaoCartaEnviada = CobrancaSituacao.CARTA_ENVIADA_AO_SPC_SP;
		}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
			idCobrancaSituacaoEmAnaliseNegativacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL;
			idCobrancaSituacaoCartaEnviada = CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BRASIL;
		}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
			idCobrancaSituacaoEmAnaliseNegativacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA;
			idCobrancaSituacaoCartaEnviada = CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BOA_VISTA;
		}

		if(negativadorMovimento.getCodigoMovimento() == ConstantesSistema.SIM.shortValue()){
			/*
			 * 9. Caso o registro corresponda a uma inclusão (NGMV_CDMOVIMENTO da tabela
			 * NEGATIVADOR_MOVIMENTO com o valor 1 (inclusão) para NGMV_ID=NGMV_ID da tabela
			 * NEGATIVADOR_MOVIMENTO_REG) e o registro corresponda a um imóvel (IMOV_ID da
			 * tabela
			 * NEGATIVADOR_MOVIMENTO_REG com o valor diferente de nulo):
			 */
			Imovel imovel = negativadorMovimentoReg.getImovel();
		
			if(imovel != null){

				/**
				 * 9.2.1. e 9.1.2.
				 */
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.COBRANCA_SITUACAO_ID,
								idCobrancaSituacaoEmAnaliseNegativacao));

				Imovel imovelRetorno = (Imovel) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroImovel,
								Imovel.class.getName()));

				if(indicadorRegistroAceito == ConstantesSistema.NAO_ACEITO.shortValue()){
					/*
					 * 9.1. Caso o registro não tenha sido aceito (indicador de registro
					 * aceito=2
					 * (dois)), o sistema indica a exclusão do imóvel:
					 */

					/*
					 * 9.1.1. Atualizar a tabela NEGATIVACAO_IMOVEIS para IMOV_ID=IMOV_ID da
					 * tabela
					 * NEGATIVADOR_MOVIMENTO_REG e CACM_ID ou CAAC_ID = CACM_ID ou CAAC_ID da
					 * tabela
					 * NEGATIVADOR_MOVIMENTO com NGMV_ID=NGMV_ID da tabela
					 * NEGATIVADOR_MOVIMENTO_REG, com os seguintes valores:
					 */

					FiltroNegativacaoImoveis filtroNegativacaoImoveis = new FiltroNegativacaoImoveis();
					filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.IMOVEL_ID, imovel.getId()));

					if(negativadorMovimento.getCobrancaAcaoAtividadeComando() != null){
						filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(
										FiltroNegativacaoImoveis.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, negativadorMovimento
														.getCobrancaAcaoAtividadeComando().getId()));
					}else if(negativadorMovimento.getCobrancaAcaoAtividadeCronograma() != null){
						filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(
										FiltroNegativacaoImoveis.COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID, negativadorMovimento
														.getCobrancaAcaoAtividadeCronograma().getId()));
					}

					NegativacaoImovei negativacaoImoveis = (NegativacaoImovei) Util.retonarObjetoDeColecao(getControladorUtil().pesquisar(
									filtroNegativacaoImoveis, NegativacaoImovei.class.getName()));

					if(negativacaoImoveis != null){
						// 5.1
						Date now = new Date();
						negativacaoImoveis.setIndicadorExcluido(ConstantesSistema.SIM.shortValue());
						negativacaoImoveis.setDataExclusao(now);
						negativacaoImoveis.setUltimaAlteracao(now);

						// indicar a exclusão do imóvel.
						getControladorUtil().atualizar(negativacaoImoveis);
					}

					/*
					 * 9.1.2. Atualizar a situação de cobrança do imóvel na tabela IMOVEL
					 * para IMOV_ID=IMOV_ID da tabela NEGATIVADOR_MOVIMENTO_REG e
					 * CBST_ID=CBST_ID da tabela COBRANCA_SITUACAO com
					 * CBST_DSCOBRANCASITUACAO com o valor correspondente a “EM ANALISE PARA
					 * NEGATIVACAO - <<Negativador do arquivo de retorno>>”, com os
					 * seguintes valores:
					 */

					if(imovelRetorno != null){
						imovelRetorno.setCobrancaSituacao(null);
						imovelRetorno.setUltimaAlteracao(new Date());
						getControladorUtil().atualizar(imovelRetorno);
					}

					/*
					 * 9.1.3. Atualizar a situação de cobrança do imóvel na tabela
					 * IMOVEL_COBRANCA_SITUACAO para IMOV_ID=IMOV_ID da tabela
					 * NEGATIVADOR_MOVIMENTO_REG e ISCB_DTRETIRADACOBRANCA=nulo e CBST_ID com o
					 * valor correspondente a “EM ANALISE PARA NEGATIVACAO - <<Negativador do
					 * arquivo de retorno>>”, caso exista ocorrência na tabela, com os seguintes
					 * valores:
					 */

					// 5.3
					ImovelCobrancaSituacao imovelCobrancaSituacao = repositorioSpcSerasa.getImovelCobrancaSituacao(imovel,
									new CobrancaSituacao(idCobrancaSituacaoEmAnaliseNegativacao));

					if(imovelCobrancaSituacao != null){

						Date now = new Date();
						imovelCobrancaSituacao.setDataRetiradaCobranca(now);
						imovelCobrancaSituacao.setUltimaAlteracao(now);
						getControladorUtil().atualizar(imovelCobrancaSituacao);
					}

					/*
					 * 9.1.4. Atualizar a situação de cobrança do imóvel na tabela
					 * NEGATIVADOR_MOVIMENTO_REG, com os seguintes valores:
					 */
					negativadorMovimentoReg.setCobrancaSituacao(null);
					negativadorMovimentoReg.setUltimaAlteracao(new Date());
					getControladorUtil().atualizar(negativadorMovimentoReg);

				}else{
					/*
					 * 9.2. Caso contrário, ou seja, o registro tenha sido aceito (indicador de
					 * registro aceito=1 (um)), o sistema confirma o envio da carta ao cliente:
					 */

					/*
					 * 9.2.1. Atualizar a situação de cobrança do imóvel na tabela IMOVEL para
					 * IMOV_ID=IMOV_ID da tabela NEGATIVADOR_MOVIMENTO_REG e CBST_ID=CBST_ID da
					 * tabela COBRANCA_SITUACAO com CBST_DSCOBRANCASITUACAO com o valor
					 * correspondente a “EM ANALISE PARA NEGATIVACAO - <<Negativador do arquivo
					 * de retorno>>”, com os seguintes valores:
					 */

					if(imovelRetorno != null){
						imovelRetorno.setCobrancaSituacao(new CobrancaSituacao(idCobrancaSituacaoEmAnaliseNegativacao));
						imovelRetorno.setUltimaAlteracao(new Date());
						getControladorUtil().atualizar(imovelRetorno);
					}

					/*
					 * 9.2.2. Atualizar a situação de cobrança do imóvel na tabela
					 * IMOVEL_COBRANCA_SITUACAO para IMOV_ID=IMOV_ID da tabela
					 * NEGATIVADOR_MOVIMENTO_REG e ISCB_DTRETIRADACOBRANCA=nulo e
					 * CBST_ID=CBST_ID da tabela COBRANCA_SITUACAO com CBST_DSCOBRANCASITUACAO
					 * com o valor correspondente a “EM ANALISE PARA NEGATIVACAO - <<Negativador
					 * do arquivo de retorno>>”, caso exista ocorrência na tabela, com os
					 * seguintes valores:
					 */
					ImovelCobrancaSituacao imovelCobrancaSituacaoAtualizar = repositorioSpcSerasa.getImovelCobrancaSituacao(imovel,
									new CobrancaSituacao(idCobrancaSituacaoEmAnaliseNegativacao));

					if(imovelCobrancaSituacaoAtualizar != null){
						Date now = new Date();
						imovelCobrancaSituacaoAtualizar.setDataRetiradaCobranca(now);
						imovelCobrancaSituacaoAtualizar.setUltimaAlteracao(now);
						getControladorUtil().atualizar(imovelCobrancaSituacaoAtualizar);
					}

					/*
					 * 9.2.3. Incluir a nova situação de cobrança do imóvel na tabela
					 * IMOVEL_COBRANCA_SITUACAO com os seguintes valores, caso não exista a
					 * ocorrência na tabela (não existe ocorrência na tabela
					 * IMOVEL_COBRANCA_SITUACAO para IMOV_ID=IMOV_ID da tabela
					 * NEGATIVADOR_MOVIMENTO_REG e ISCB_DTRETIRADACOBRANCA=nulo e
					 * CBST_ID=CBST_ID da tabela COBRANCA_SITUACAO com CBST_DSCOBRANCASITUACAO
					 * com o valor correspondente a “CARTA ENVIADA AO <<Negativador do arquivo
					 * de retorno>>”):
					 */

					ImovelCobrancaSituacao imovelCobrancaSituacaoBD = repositorioSpcSerasa.getImovelCobrancaSituacao(imovel,
									new CobrancaSituacao(idCobrancaSituacaoCartaEnviada));

					if(imovelCobrancaSituacaoBD == null){
						Date now = new Date();
						ImovelCobrancaSituacao imovelCobrancaSituacaoInserir = new ImovelCobrancaSituacao();
						imovelCobrancaSituacaoInserir.setImovel(imovel);
						imovelCobrancaSituacaoInserir.setDataImplantacaoCobranca(now);
						imovelCobrancaSituacaoInserir.setDataRetiradaCobranca(null);
						imovelCobrancaSituacaoInserir.setCobrancaSituacao(new CobrancaSituacao(idCobrancaSituacaoCartaEnviada));
						imovelCobrancaSituacaoInserir.setCliente(negativadorMovimentoReg.getCliente());
						imovelCobrancaSituacaoInserir.setAnoMesReferenciaInicio(null);
						imovelCobrancaSituacaoInserir.setAnoMesReferenciaFinal(null);
						imovelCobrancaSituacaoInserir.setContaMotivoRevisao(null);
						imovelCobrancaSituacaoInserir.setEscritorio(null);
						imovelCobrancaSituacaoInserir.setAdvogado(null);
						imovelCobrancaSituacaoInserir.setUltimaAlteracao(now);
						getControladorUtil().inserir(imovelCobrancaSituacaoInserir);
					}

					/*
					 * 9.2.4. Atualizar a situação de cobrança do imóvel na tabela
					 * NEGATIVADOR_MOVIMENTO_REG:
					 */
					negativadorMovimentoReg.setCobrancaSituacao(new CobrancaSituacao(idCobrancaSituacaoCartaEnviada));
					negativadorMovimentoReg.setUltimaAlteracao(new Date());
					getControladorUtil().atualizar(negativadorMovimentoReg);
				}
			}
		}
	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672]
	 * Verifica o negativadorMovimentoRegRetMot do Registro 02 do SPC
	 * e atualiza o negativadorMovimentoReg com o indicador de Aceito com valor 1 ou 2
	 * [Verificar Registro 02 do SPC]
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private void verificarRegistroSPCBrasil02(Object[] registro, Negativador negativador, NegativadorMovimentoReg negativadorMovimentoReg)
					throws ControladorException, ErroRepositorioException{

		short indicadorRegistroAceito = -1;
		String codigoRetorno = "-1";
		String campoCodigoRetorno1 = "";
		String campoCodigoRetorno2 = "";

		// --------------------------------------------------------------------------------------
		// [SB005] - Atualizar Registro de Envio para SPC
		// --------------------------------------------------------------------------------------
		// 1.0
		// ---------------------------------------------------------------------------------------

		// 2.0
		// --------------------------------------------------------------------------------
		// atribuir o valor um ao campo indicador registro aceito
		indicadorRegistroAceito = 1;
		// -------------------------------------------------------------------------------

		// Para cada ocorrência do código de retorno
		// Caso o registro seja do SPC
		// 3.0
		// -------------------------------------------------------------------------------
		String registro1 = (String) registro[0];
		String registro2 = (String) registro[1];

		campoCodigoRetorno1 = getConteudo(325, 10, registro1.toCharArray()).trim();
		campoCodigoRetorno2 = getConteudo(325, 10, registro2.toCharArray()).trim();

		// ------------------------------------------------------------------------------
		// Para o REGISTRO 01
		// ------------------------------------------------------------------------------

		if(!Util.isVazioOuBranco(registro2)){

			if(campoCodigoRetorno2.equals("0000000000") && !campoCodigoRetorno2.equals("")){
				if(!campoCodigoRetorno1.equals("0000000000") && !campoCodigoRetorno1.equals("")){
					campoCodigoRetorno2 = "0000000098";
				}
			}

			int cont = 0;
			for(int j = 0; j < 9; j = j + 2){

				codigoRetorno = campoCodigoRetorno2.substring(j, j + 2);

				if(codigoRetorno.equals("00")){
					cont = cont + 1;
				}

				if(!codigoRetorno.equals("00") || (cont == 5 && codigoRetorno.equals("00"))){
					Integer codRetorno = Util.converterStringParaInteger(codigoRetorno);

					NegativadorMovimentoRegRetMot negativadorMovimentoRegRetMot = new NegativadorMovimentoRegRetMot();
					negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);

					FiltroNegativadorRetornoMotivo fnrm = new FiltroNegativadorRetornoMotivo();
					fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.CODIGO_RETORNO_MOTIVO, codRetorno));
					fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.NEGATIVADOR_RETORNO_MOTIVO_NEGATIVADOR,
									Negativador.NEGATIVADOR_SPC_BRASIL));

					NegativadorRetornoMotivo negativadorRetornoMot = (NegativadorRetornoMotivo) Util
									.retonarObjetoDeColecao(RepositorioUtilHBM.getInstancia().pesquisar(fnrm,
													NegativadorRetornoMotivo.class.getName()));

					// [FS007] - Verifica a exixtência do motivo de
					if(negativadorRetornoMot != null){
						negativadorMovimentoRegRetMot.setNegativadorMovimentoReg(negativadorMovimentoReg);
						negativadorMovimentoRegRetMot.setNegativadorRetornoMotivo(negativadorRetornoMot);
						negativadorMovimentoRegRetMot.setUltimaAlteracao(new Date());

						// [FS008] -Verifica a indicação de registro aceito
						// Caso não corresponda a aceito
						if(negativadorRetornoMot.getIndicadorRegistroAceito() != 1){
							indicadorRegistroAceito = 2;
							negativadorRetornoMot.setIndicadorRegistroAceito(indicadorRegistroAceito);
						}

						// ************************************************************************
						// 03/12/2008
						// Consultar antes, e só inserir se não existir.
						// repositorioUtil.inserir(negativadorMovimentoRegRetMot);
						this.inserirNegativadorMovimentoRegRetMot(negativadorMovimentoRegRetMot);

						// ************************************************************************

					}else{
						throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
					}
				}
			}
		}

		// ----------------------------------------------------------------------------------------------
		// 4.0
		// ------------------------------------------------------------------------------------------------
		negativadorMovimentoReg.setIndicadorAceito(indicadorRegistroAceito);
		// -------------------------------------------------------------------------------------------------
		// [início] - Alteração 05/05/2008 - Indicar Exclusão do imóvel caso a inclusão da
		// negativação não seja aceita.
		// -------------------------------------------------------------------------------------------------
		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		// 5.0
		if(indicadorRegistroAceito == ConstantesSistema.NAO_ACEITO && negativadorMovimento.getCodigoMovimento() == 1){

			Imovel imovel = negativadorMovimentoReg.getImovel();
		
			if(imovel != null){

				FiltroNegativacaoImoveis filtroNegativacaoImoveis = new FiltroNegativacaoImoveis();
				filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.IMOVEL_ID, imovel.getId()));
				filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.NEGATIVADOR_ID, negativador
								.getId()));

				if(negativadorMovimento.getCobrancaAcaoAtividadeComando() != null){
					filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(
									FiltroNegativacaoImoveis.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID, negativadorMovimento
													.getCobrancaAcaoAtividadeComando().getId()));
				}else if(negativadorMovimento.getCobrancaAcaoAtividadeCronograma() != null){
					filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(
									FiltroNegativacaoImoveis.COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID, negativadorMovimento
													.getCobrancaAcaoAtividadeCronograma().getId()));
				}else if(negativadorMovimento.getNegativacaoComando() != null){
					filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.NEGATIVACAO_COMANDO_ID,
									negativadorMovimento.getNegativacaoComando().getId()));
				}

				try{
					NegativacaoImovei negativacaoImoveis = (NegativacaoImovei) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(
									filtroNegativacaoImoveis, NegativacaoImovei.class.getName()));

					if(negativacaoImoveis != null){
						// 5.1
						short indicadorExcluido = 1;
						negativacaoImoveis.setIndicadorExcluido(indicadorExcluido);

						Date dataExclusao = new Date();
						negativacaoImoveis.setDataExclusao(dataExclusao);

						Date ultimaAlteracao = new Date();
						negativacaoImoveis.setUltimaAlteracao(ultimaAlteracao);

						// indicar a exclusão do imóvel.
						repositorioUtil.atualizar(negativacaoImoveis);

						int qtdNegativacoesAtivas = repositorioSpcSerasa.pesquisarQuantidadeNegativacoesAtivas(imovel.getId());

						if(qtdNegativacoesAtivas == 0){
							FiltroImovel filtroImovel = new FiltroImovel();
							filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
							filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.COBRANCA_SITUACAO_ID,
											CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL));

							Imovel imovelRetorno = (Imovel) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(filtroImovel,
											Imovel.class.getName()));

							if(imovelRetorno != null){
								imovelRetorno.setCobrancaSituacao(null);
								imovelRetorno.setUltimaAlteracao(new Date());
								repositorioUtil.atualizar(imovelRetorno);
							}
						}

						// 5.3
						CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
						cobrancaSituacao.setId(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL);

						ImovelCobrancaSituacao imovelCobrancaSituacao = repositorioSpcSerasa.getImovelCobrancaSituacao(imovel,
										cobrancaSituacao);

						if(imovelCobrancaSituacao != null){
							imovelCobrancaSituacao.setDataRetiradaCobranca(new Date());
							imovelCobrancaSituacao.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(imovelCobrancaSituacao);
						}
					}
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
			}
		}

		// 9.
		verificarRegistroInclusao(negativadorMovimentoReg, indicadorRegistroAceito, negativador);

		// -------------------------------------------------------------------------------------------------
		// [fim] - Alteração 05/05/2008 - Indicar Exclusão do imóvel caso a inclusão da negativação
		// não seja aceita.
		// -------------------------------------------------------------------------------------------------

		try{
			negativadorMovimentoReg.setIndicadorAceito(indicadorRegistroAceito);
			repositorioUtil.atualizar(negativadorMovimentoReg);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	private void envioEmailErroMovimentoRetorno(Exception excecao, String nomeArquivo){

		EnvioEmail envioEmail;
		ByteArrayOutputStream baos = null;
		try{
			envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.SPC_SERASA_MOV_RETORNO);

			String emailRemetente = envioEmail.getEmailRemetente();

			String tituloMensagem = envioEmail.getTituloMensagem();

			baos = new ByteArrayOutputStream();
			excecao.printStackTrace(new PrintStream(baos));

			String corpoMensagem = "O arquivo : " + nomeArquivo + " \n " + envioEmail.getCorpoMensagem() + " \n " + " Exceção: " + excecao;
			String emailReceptor = envioEmail.getEmailReceptor();

			System.out.println("email destinatário:" + emailReceptor);

			ServicosEmail.enviarMensagem(emailRemetente, emailReceptor, tituloMensagem, corpoMensagem);

		}catch(ControladorException e1){
			e1.printStackTrace();
		}catch(ErroEmailException e){
			e.printStackTrace();
		}finally{
			IoUtil.fecharStream(baos);
		}

	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672] Registrar
	 * Movimento de Retorno dos Negativadores
	 * [SB0004] - Atualizar Movimento de Envio SERASA
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 */
	private void atualizarMovimentoEnvioSERASA(String registro, Negativador negativador, NegativadorMovimento negativadorMovimento)
					throws ControladorException{

		// ----------------------------------------------------------------------------
		// [SB0004] - Atualizar Movimento de Envio SERASA
		// ----------------------------------------------------------------------------

		String numeroRegistro = "";
		// 7.

		try{
			// ---------------------------------------------------------------
			numeroRegistro = getConteudo(594, 7, registro.toCharArray()).trim();
			Integer numRegistro = Util.converterStringParaInteger(numeroRegistro);
			// ------------------------------------------------------------

			if(!numeroRegistro.equalsIgnoreCase("")){

				NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(
								negativadorMovimento, numRegistro);

				if(negativadorMovimentoReg == null
								|| (negativadorMovimentoReg != null && negativadorMovimentoReg.getConteudoRegistro() == null)){

					if(negativadorMovimento != null){
						System.out.println("NGMV_ID = " + negativadorMovimento.getId() + "numRegistro = " + numRegistro);
					}
					throw new ControladorException("atencao.arquivo_movimento_sem_registros");
				}

				// ---------------------------------------------------------------------------------
				// [SB0005] - Atualizar Registro Envio
				// -------------------------------------------------------------------------------
				this.atualizarRegistroEnvio(negativador, registro, negativadorMovimentoReg);

			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	/**
	 * Método privado que gera uma string buffer que representa o arquivo
	 * dos movimentos de exclusao de negativacao [UC0673] - Gerar Movimento
	 * da Exclusão de Negativação [SB0009] - Gerar Arquivo TxT para Envio ao
	 * Negativador
	 * 
	 * @author Thiago Toscano
	 * @date 27/12/2007
	 * @param idMovimento
	 * @return o arquivo
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	public void gerarArquivo(Integer idMovimento, boolean trailler, Integer idNegativador, Usuario usuario) throws ControladorException{

		StringBuffer sb = new StringBuffer();
		int numeroRegistro = 0;

		try{

			FiltroNegativadorMovimento fnm = new FiltroNegativadorMovimento();
			fnm.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimento.ID, idMovimento));
			fnm.setCampoOrderBy(FiltroNegativadorMovimento.ID);

			Collection coll = repositorioUtil.pesquisar(fnm, NegativadorMovimento.class.getName());
			if(coll != null && !coll.isEmpty()){
				NegativadorMovimento nm = (NegativadorMovimento) coll.iterator().next();
				coll = repositorioSpcSerasa.consultarNegativadorMovimentoRegistroParaGerarArquivo(idMovimento, "H");
				if(coll != null && !coll.isEmpty()){
					NegativadorMovimentoReg nmr = (NegativadorMovimentoReg) coll.iterator().next();

					if(Integer.valueOf(nm.getNegativador().getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
						sb.append(Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(nmr.getConteudoRegistro(), 250));
					}else{
						sb.append(nmr.getConteudoRegistro());
					}

					sb.append("\n");
				}
				coll = repositorioSpcSerasa.consultarNegativadorMovimentoRegistroParaGerarArquivo(idMovimento, "D");
				if(coll != null && !coll.isEmpty()){
					Iterator it = coll.iterator();
					while(it.hasNext()){
						NegativadorMovimentoReg nmr = (NegativadorMovimentoReg) it.next();

						if(Integer.valueOf(nm.getNegativador().getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
							sb.append(Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(nmr.getConteudoRegistro(), 250));
						}else{
							sb.append(nmr.getConteudoRegistro());
						}

						sb.append("\n");
						numeroRegistro = nmr.getNumeroRegistro();
					}
					// numeroRegistro = numeroRegistro + 1;

				}
				if(trailler){
					// numeroRegistro = 0;

					StringBuilder registroTrailler = new StringBuilder();

					if(Integer.valueOf(nm.getNegativador().getId()).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
						registroTrailler = this.geraRegistroTipoTraillerSPCSP(numeroRegistro);

					}else if(Integer.valueOf(nm.getNegativador().getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
						registroTrailler = this.geraRegistroTipoTraillerSPCBoaVista(numeroRegistro);

					}else if(Integer.valueOf(nm.getNegativador().getId()).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
						registroTrailler = this.geraRegistroTipoTraillerSPCBrasil(numeroRegistro);

					}else{
						registroTrailler = this.geraRegistroTipoTraillerSERASA(numeroRegistro);
					}
					NegativadorMovimentoReg negativadorMovimentoRegInserir = new NegativadorMovimentoReg();
					NegativadorRegistroTipo negativadorRegistroTipo = new NegativadorRegistroTipo();
					negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_SERASA_TRAILLER);
					if(Integer.valueOf(idNegativador).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
						negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_SPC_TRAILLER_SP);
					}else if(Integer.valueOf(idNegativador).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
						negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_BOA_VISTA_TRAILLER);
						numeroRegistro = numeroRegistro + 1;
					}else if(Integer.valueOf(idNegativador).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
						negativadorRegistroTipo.setId(NegativadorRegistroTipo.ID_SPC_TRAILLER_BR);
					}
					negativadorMovimentoRegInserir.setNegativadorRegistroTipo(negativadorRegistroTipo);
					negativadorMovimentoRegInserir.setNegativadorMovimento(nm);
					negativadorMovimentoRegInserir.setConteudoRegistro(registroTrailler.toString());
					negativadorMovimentoRegInserir.setNumeroRegistro(numeroRegistro);
					negativadorMovimentoRegInserir.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegInserir.setUltimaAlteracao(new Date());

					Usuario usu = negativadorMovimentoRegInserir.getUsuario();
					negativadorMovimentoRegInserir.setUsuario(null);

					getControladorUtil().inserir(negativadorMovimentoRegInserir);

					negativadorMovimentoRegInserir.setUsuario(usu);

					if(Integer.valueOf(nm.getNegativador().getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
						sb.append(Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(registroTrailler.toString(), 250));
					}else{
						sb.append(registroTrailler.toString());
					}

					sb.append("\n");

				}else{
					coll = repositorioSpcSerasa.consultarNegativadorMovimentoRegistroParaGerarArquivo(idMovimento, "T");
					if(coll != null && !coll.isEmpty()){
						NegativadorMovimentoReg nmr = (NegativadorMovimentoReg) coll.iterator().next();

						if(Integer.valueOf(nm.getNegativador().getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
							sb.append(Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(nmr.getConteudoRegistro(), 250));
						}else{
							sb.append(nmr.getConteudoRegistro());
						}

						sb.append("\n");
					}
				}
			}

			Date data = new Date();
			String AAAAMMDD = Util.formatarDataAAAAMMDD(data);
			String HHMM = Util.formatarDataHHMM(data);
			String formatodatahora = AAAAMMDD + "_" + HHMM;
			String nomeArquivo = "";

			if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
				nomeArquivo = "REG_SPC_SP_" + formatodatahora + ".env";
			}else if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
				String DDMMAAHHMM = AAAAMMDD.substring(6, 8) + AAAAMMDD.substring(4, 6) + AAAAMMDD.substring(2, 4) + HHMM;
				nomeArquivo = "BVCAG" + DDMMAAHHMM + ".txt";
			}else if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
				nomeArquivo = "REG_SPC_BRASIL_" + formatodatahora + ".txt";
			}else if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
				formatodatahora = Util.recuperaDiaMesAnoCom2DigitosDaData(data) + Util.formatarDataHHMM(data);
				nomeArquivo = "BVCAG" + formatodatahora + ".txt";
			}else{
				nomeArquivo = "REG_SERASA_" + formatodatahora + ".txt";
			}

			this.enviarArquivoSpcSerasaParaBatch(sb, nomeArquivo, usuario);

		}catch(Exception e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

	}

	public void enviarArquivoSpcSerasaParaBatch(StringBuffer sb, String nomeArquivo, Usuario usuario) throws ControladorException{

		RelatorioNegativacaoSpcSerasa relatorio = new RelatorioNegativacaoSpcSerasa(usuario);
		relatorio.addParametro("arquivoTexto", sb);
		relatorio.addParametro("nomeArquivo", nomeArquivo);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

	}

	public void enviarArquivoExclusaoSpcSerasaParaBatch(StringBuffer sb, String nomeArquivo, Usuario usuario) throws ControladorException{

		RelatorioExclusaoNegativacaoSpcSerasa relatorio = new RelatorioExclusaoNegativacaoSpcSerasa(usuario);
		relatorio.addParametro("arquivoTexto", sb);
		relatorio.addParametro("nomeArquivo", nomeArquivo);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);
		this.getControladorBatch().iniciarProcessoRelatorio(relatorio);

	}

	/*
	 * Metodo responsavel por preservar o trecho do codigo que salva, compacta e envia por email o
	 * arquivo SPC/SERASA
	 */
	public void salvarZipArquivoSpcSerasaDiscoEnviarEmail(StringBuffer sb, String nomeZip, Integer idNegativador)
					throws ControladorException{

		ZipOutputStream zos = null;
		BufferedWriter out = null;

		try{

			File leituraTipo = null;

			if(idNegativador.equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
				leituraTipo = new File(nomeZip);
			}else{
				leituraTipo = new File(nomeZip);
			}

			File compactado = new File(nomeZip + ".zip"); // nomeZip
			zos = new ZipOutputStream(new FileOutputStream(compactado));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leituraTipo.getAbsolutePath())));
			out.write(sb.toString());
			out.flush();

			out.close();

			ZipUtil.adicionarArquivo(zos, leituraTipo);

			zos.close();

			EnvioEmail envioEmail = getControladorCadastro().pesquisarEnvioEmail(EnvioEmail.SPC_SERASA);

			if(envioEmail != null){
				String emailRemetente = envioEmail.getEmailRemetente();

				String tituloMensagem = envioEmail.getTituloMensagem();

				String corpoMensagem = envioEmail.getCorpoMensagem();
				String emailReceptor = envioEmail.getEmailReceptor();
				System.out.println("email destinatário:" + emailReceptor);

				ServicosEmail.enviarMensagemArquivoAnexado(emailReceptor, emailRemetente, tituloMensagem, corpoMensagem, compactado);
			}

			leituraTipo.delete();

		}catch(IOException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}catch(Exception e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	// GERA REGISTRO TRAILLER SPC *****************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0012] **
	// ********************************************
	public StringBuilder geraRegistroTipoTraillerSERASA(int quantidadeRegistros) throws ControladorException, ErroRepositorioException{

		StringBuilder registroTrailler = new StringBuilder();
		// //////////////////////////////////DETALHE TRAILLER
		// T.01
		registroTrailler.append("9");
		// T.02
		registroTrailler.append(Util.completaString(" ", 532));
		// T.04
		registroTrailler.append(Util.completaString(" ", 60));
		// T.05
		registroTrailler.append(Util.adicionarZerosEsquedaNumero(7, "" + quantidadeRegistros));

		return registroTrailler;
	}
	
	// GERA REGISTRO TRAILLER SPC *****************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0012] **
	// ********************************************
	public StringBuilder geraRegistroTipoTraillerSPCBrasil(int quantidadeRegistros) throws ControladorException, ErroRepositorioException{

		StringBuilder registroTrailler = new StringBuilder();
		// //////////////////////////////////DETALHE TRAILLER
		// T.01
		registroTrailler.append("99");
		// T.02
		registroTrailler.append(Util.adicionarZerosEsquedaNumero(6, "" + quantidadeRegistros));
		// T.03
		registroTrailler.append(Util.completaString(" ", 316));
		// T.04
		registroTrailler.append(Util.completaString(" ", 10));
		// T.05
		registroTrailler.append(Util.adicionarZerosEsquedaNumero(6, "" + quantidadeRegistros));

		return registroTrailler;
	}

	/**
	 * Método que gera os movimento de inclusao de negativacao por Matricula de Imovel
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativação
	 * [SB0001] Gerar Movimento de Inclusao de Negativacao por Matricula de Imovel
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @return objQuantidades
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private Object[] gerarMovimentodeInclusaoNegativacaoPorMatriculadeImovel(Object[] objQuantidades, NegativadorContrato nContrato,
					Negativador n, Collection objectImovel, CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando) throws ControladorException, ErroRepositorioException{

		Integer quantidadeRegistro = 0;

		NegativadorMovimentoReg.resetNumeroProximoRegistro();

		if(objectImovel != null){
			Iterator it = objectImovel.iterator();
			while(it.hasNext()){
				DadosNegativacaoPorImovelHelper dadosNegativacaoPorImovelHelper = (DadosNegativacaoPorImovelHelper) it.next();

				objQuantidades[0] = (Integer) objQuantidades[0] + 1;
				objQuantidades[1] = (Integer) objQuantidades[1] + dadosNegativacaoPorImovelHelper.getQtdItensDebitoImovel();
				objQuantidades[2] = ((BigDecimal) objQuantidades[2]).add(dadosNegativacaoPorImovelHelper.getTotalDebitosImovel());

				// 4.2
				Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa
								.getDadosCliente(dadosNegativacaoPorImovelHelper.getIdCliente()));

				if(cliente == null){
					System.out.println("cliente = " + dadosNegativacaoPorImovelHelper.getIdCliente());
				}

				// 4.3
				quantidadeRegistro = gerarRegistroDeInclusaoNegativacao(n, null, quantidadeRegistro, dadosNegativacaoPorImovelHelper
								.getIdImovel(), dadosNegativacaoPorImovelHelper.getTotalDebitosImovel(), null,
								dadosNegativacaoPorImovelHelper, cliente, nContrato, objQuantidades, null, cobrancaAcaoAtividadeCronograma,
								cobrancaAcaoAtividadeComando);

			}
		}

		objQuantidades[4] = quantidadeRegistro;

		return objQuantidades;
	}

	/**
	 * Gerar Registro de negativacao
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativação
	 * [SB0007] Gerar Registro de negativacao
	 * [UC1102 Emitir Arquivo de movimento de inclusão de negativação
	 * [SB0002] – Gerar Registro de Negativação
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param nc
	 * @param Object
	 *            [] obj
	 *            obj[0] Integer - quantidadeInclusao
	 *            obj[1] Integer - quantidadeItens
	 *            obj[2] BigDecimal - valorTotalDebito
	 * @param Object
	 *            [] object
	 *            obj[0] Integer - quantidadeInclusao
	 *            obj[1] Integer - quantidadeItens
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private Integer gerarRegistroDeInclusaoNegativacao(Negativador n, NegativacaoCriterio nCriterio, Integer quantidadeRegistro,
					Integer imovel, BigDecimal valorTotalImovel, ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper,
					DadosNegativacaoPorImovelHelper dadosNegativacaoPorImovelHelper, Cliente cliente, NegativadorContrato nContrato,
					Object[] objQuantidades, NegativadorMovimento negMovimento,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando) throws ControladorException, ErroRepositorioException{

		Integer idNegativacaoMovimento = null;
		/*
		 * Trecho de incluir o HEADER foi colocado em TarefaBatchExecutarComandoNegativacao, pois
		 * garante que só seja chamado uma única vez, já que
		 * este processo esta executando em paralelo
		 */
		if(negMovimento == null){

			boolean entrou = false;
			if(quantidadeRegistro != 0){
				// consulta se ja foi incluido o negativor_movimento
				// @todo colocar n restricao do where onde cacm_id = cobrancaAcaoAtividadeCronograma
				// ou
				// caac_id = cobrancaAcaoAtividadeComando
				FiltroNegativadorMovimento fnm = new FiltroNegativadorMovimento();
				if(cobrancaAcaoAtividadeCronograma != null && cobrancaAcaoAtividadeCronograma.getId() != null){
					fnm.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimento.COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA_ID,
									cobrancaAcaoAtividadeCronograma.getId()));
				}else{
					fnm.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimento.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID,
									cobrancaAcaoAtividadeComando.getId()));
				}

				// fnm.adicionarParametro(new
				// ParametroSimples(FiltroNegativadorMovimento.NEGATIVACAO_COMANDOCOMANDO,
				// nComando.getId()));

				Collection colecaoNegativadorMovimento = repositorioUtil.pesquisar(fnm, NegativadorMovimento.class.getName());
				if(!Util.isVazioOrNulo(colecaoNegativadorMovimento)){
					negMovimento = (NegativadorMovimento) colecaoNegativadorMovimento.iterator().next();
					idNegativacaoMovimento = negMovimento.getId();
					entrou = true;
				}
			}

			if(!entrou){
				int saEnvio = nContrato.getNumeroSequencialEnvio() + 1;
				// 1.2
				// gerando o movimento
				// idNegativacaoMovimento =
				// this.repositorioSpcSerasa.geraRegistroNegativacao(n.getId(), saEnvio,
				// nComando.getId());

				quantidadeRegistro = 1;

				NegativadorMovimento negativadorMovimentoInserir = new NegativadorMovimento();
				negativadorMovimentoInserir.setNegativador(n);
				negativadorMovimentoInserir.setNumeroSequencialEnvio(saEnvio);
				// negativadorMovimentoInserir.setNegativacaoComando(nComando);
				negativadorMovimentoInserir.setDataEnvio(new Date());
				negativadorMovimentoInserir.setDataProcessamentoEnvio(new Date());
				negativadorMovimentoInserir.setUltimaAlteracao(new Date());
				negativadorMovimentoInserir.setCobrancaAcaoAtividadeCronograma(cobrancaAcaoAtividadeCronograma);
				negativadorMovimentoInserir.setCobrancaAcaoAtividadeComando(cobrancaAcaoAtividadeComando);
				negativadorMovimentoInserir.setCodigoMovimento(Short.valueOf("1"));

				idNegativacaoMovimento = (Integer) getControladorUtil().inserir(negativadorMovimentoInserir);
				objQuantidades[3] = idNegativacaoMovimento;
				negativadorMovimentoInserir.setId(idNegativacaoMovimento);
				// 1.3
				// [SB0008] - Gerar Registro do tipo Hearder
				quantidadeRegistro = gerarRegistroDeInclusaoTipoHeader(quantidadeRegistro, n, nContrato, nCriterio,
								negativadorMovimentoInserir, dadosNegativacaoPorImovelHelper);
			}
		}else{
			idNegativacaoMovimento = negMovimento.getId();
		}

		// 2
		// [SB0009] - Gerar Registro do tipo Detalhe

		quantidadeRegistro = gerarRegistroDeInclusaoTipoDetalhe(quantidadeRegistro, n, idNegativacaoMovimento, nCriterio, imovel,
						valorTotalImovel, obterDebitoImovelOuClienteHelper, dadosNegativacaoPorImovelHelper, cliente,
						cobrancaAcaoAtividadeCronograma, cobrancaAcaoAtividadeComando, nContrato);

		return quantidadeRegistro;
	}

	// GERA REGISTRO DETALHE CONSUMIDOR SERASA ****
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ********************************************
	public StringBuilder geraRegistroTipoDetalheSERASA(int quantidadeRegistros, BigDecimal valorTotalDebitosImovel,
					ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper, Imovel imovelNegativado,
					DadosNegativacaoPorImovelHelper dadosNegativacaoPorImovelHelper, Cliente cliente) throws ControladorException,
					ErroRepositorioException{

		// 2.1
		StringBuilder registroDetalheConsumidor = new StringBuilder();
		// ////DETALHE SERASA
		// D.01
		registroDetalheConsumidor.append("1");
		// D.02
		registroDetalheConsumidor.append("I");
		// D.03 CNPJ 06 ultimas posições
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
		String cnpjEmpresa = sistemaParametro.getCnpjEmpresa();
		registroDetalheConsumidor.append(cnpjEmpresa.substring(8));

		// D.04 - Maior data dos debitos no formato AAAAMMDD
		// D.05 - D.04
		Date maiorData = null;
		Date menorData = null;

		maiorData = this.obterMaiorMenorVencimento(dadosNegativacaoPorImovelHelper.getColecaoConta(), dadosNegativacaoPorImovelHelper
						.getColecaoGuias(), 1);

		menorData = this.obterMaiorMenorVencimento(dadosNegativacaoPorImovelHelper.getColecaoConta(), dadosNegativacaoPorImovelHelper
						.getColecaoGuias(), 2);

		if(maiorData != null){
			String D204 = Util.recuperaDataInvertida(maiorData);
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(8, D204));
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(8, D204));
		}else{
			registroDetalheConsumidor.append("00000000");
			registroDetalheConsumidor.append("00000000");
		}

		// D.06
		registroDetalheConsumidor.append("SB ");

		// D.07
		registroDetalheConsumidor.append("    ");

		if(cliente != null && cliente.getCnpj() != null && cliente.getCnpj().length() > 0){
			// D.08 - Indicador se tem ou não CNPJ ou CPF Preenchido
			registroDetalheConsumidor.append("J");
			// D.09 - Indicador se tem ou não CNPJ ou CPF Preenchido
			registroDetalheConsumidor.append("1");
			// D.10
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(15, "" + cliente.getCnpj()));
		}else{

			if(cliente == null || cliente.getCpf() == null){
				System.out.println("idImovel = " + imovelNegativado.getId());
				throw new ControladorException("atencao.campo.invalido", null, "CPF");
			}

			// D.08 - Indicador se tem ou não CNPJ ou CPF Preenchido
			registroDetalheConsumidor.append("F");
			// D.09 - Indicador se tem ou não CNPJ ou CPF Preenchido
			registroDetalheConsumidor.append("2");
			// D.10
			if(cliente.getCpf() != null){
				registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(15, "" + cliente.getCpf()));
			}else{
				registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(15, ""));
			}

		}

		// D.11
		registroDetalheConsumidor.append("  ");

		if(cliente.getCpf() != null && cliente.getCpf().length() > 0 && cliente.getRg() != null && cliente.getRg().length() > 0){
			// D.12
			registroDetalheConsumidor.append("3");
			// D.13
			registroDetalheConsumidor.append(Util.completaStringComEspacoAEsquerda(cliente.getRg(), 15));
			// D.14
			if(cliente.getUnidadeFederacao() != null && !cliente.getUnidadeFederacao().equals("")){
				// UNIDADE FEDERAÇÃO
				FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
				filtroUnidadeFederacao.adicionarParametro(new ParametroSimples(FiltroUnidadeFederacao.ID, cliente.getUnidadeFederacao()
								.getId().toString()));
				filtroUnidadeFederacao.setCampoOrderBy(FiltroUnidadeFederacao.DESCRICAO);
				UnidadeFederacao unidadeFederacao = (UnidadeFederacao) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(
								filtroUnidadeFederacao, UnidadeFederacao.class.getName()));
				registroDetalheConsumidor.append(Util.completaString(unidadeFederacao.getSigla(), 2));
			}else{
				registroDetalheConsumidor.append("  ");
			}
		}else{
			// D.12
			registroDetalheConsumidor.append(" ");
			// D.13
			registroDetalheConsumidor.append("               ");
			// D.14
			registroDetalheConsumidor.append("  ");
		}

		// os campo abaixo referentes a: D.15.16.17.18.19.20.21
		registroDetalheConsumidor.append(" ");
		registroDetalheConsumidor.append(" ");
		registroDetalheConsumidor.append("               ");
		registroDetalheConsumidor.append("  ");
		registroDetalheConsumidor.append(" ");
		registroDetalheConsumidor.append("               ");
		registroDetalheConsumidor.append("  ");

		// D.22 - Nome Cliente
		registroDetalheConsumidor.append(Util.completaString(cliente.getNome(), 70));

		// D.23 -Idade do cliente1
		if(cliente.getCpf() != null && cliente.getCpf().length() > 0){
			if(cliente.getDataNascimento() != null && !cliente.getDataNascimento().equals("")){
				registroDetalheConsumidor.append(Util.completaString(Util.recuperaDataInvertida(cliente.getDataNascimento()), 8));
			}else{
				registroDetalheConsumidor.append("00000000");
			}
		}else{
			registroDetalheConsumidor.append("00000000");
		}

		// D.24 - Espaços em Branco
		registroDetalheConsumidor.append(Util.completaString(" ", 70));
		// D.25 - Nome da Mãe
		if(cliente.getNomeMae() != null){
			registroDetalheConsumidor.append(Util.completaString(cliente.getNomeMae(), 70));
		}else{
			registroDetalheConsumidor.append(Util.completaString(" ", 70));
		}
		// D.26 - Endereco
		// String ender =
		// this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(cliente.getId());

		String endereco = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(cliente.getId(), true);

		if(endereco != null){

			if(endereco.length() > 45){
			registroDetalheConsumidor.append(Util.completaString(endereco.substring(0, 45), 45));
		}else{
				registroDetalheConsumidor.append(Util.completaString(endereco, 45));
			}

		}else{
			registroDetalheConsumidor.append(Util.completaString("", 45));
		}

		// Obtem dados do endereço do cliente.
		ClienteEndereco cliEnder = (ClienteEndereco) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDadosEnderecoCliente(cliente
						.getId()));

		if(cliEnder != null){
			if(cliEnder.getLogradouroBairro() != null){
				// D.27 - Bairro
				registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroBairro().getBairro().getNome(), 20));
				// D.28 - Município
				registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getMunicipio(), 25));
				// D.29 - Unidade da Federação
				registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getSigla(), 2));
				// D.30 - Cep
				registroDetalheConsumidor.append(Util.completaString("" + cliEnder.getLogradouroCep().getCep().getCodigo(), 8));
			}else{
				Cep cep = (Cep) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getCep(cliEnder.getId()));
				// D.27 - Bairro
				registroDetalheConsumidor.append(Util.completaString(cep.getBairro(), 20));
				// D.28 - Município
				registroDetalheConsumidor.append(Util.completaString(cep.getMunicipio(), 25));
				// D.29 - Unidade da Federação
				registroDetalheConsumidor.append(Util.completaString(cep.getSigla(), 2));
				// D.30 - Cep
				registroDetalheConsumidor.append(Util.completaString("" + cep.getCodigo(), 8));
			}
		}else{
			// throw new ControladorException("atencao.campo.invalido", null, "ENDERECO");
			// D.27 - Bairro
			registroDetalheConsumidor.append(Util.completaString("", 20));
			// D.28 - Município
			registroDetalheConsumidor.append(Util.completaString("", 25));
			// D.29 - Unidade da Federação
			registroDetalheConsumidor.append(Util.completaString("", 2));
			// D.30 - Cep
			registroDetalheConsumidor.append(Util.completaString("", 8));
		}

		String valorString = Util.formatarMoedaReal(valorTotalDebitosImovel);
		String valorNovo = "";
		for(int i = 0; i < valorString.length(); i++){
			if(valorString.charAt(i) != '.' && valorString.charAt(i) != ','){
				valorNovo = valorNovo + valorString.charAt(i);
			}
		}
		valorString = valorNovo;
		// D.31 - Valor total dos debitos
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(15, valorString));

		// D.32 - Imovel Id
		// Alterado para ADA .........................................
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(16, imovelNegativado.getId().toString()));
		// registroDetalheConsumidor.append(Util.completaString(imovelNegativado.getId().toString(),
		// 16));
		// ............................................................

		// D.33
		registroDetalheConsumidor.append("         ");// 000000000

		// D.34
		// if (ender != null && ender.length() > 45) {
		// registroDetalheConsumidor.append(Util.completaString(ender.substring(45), 25));
		// } else {
		registroDetalheConsumidor.append(Util.completaString(" ", 25));
		// }

		// D.35 - DDD
		ClienteFone cliFone = (ClienteFone) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDddFone(cliente.getId()));
		if(cliFone != null){
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(4, cliFone.getDdd()));
		}else{
			registroDetalheConsumidor.append("    ");// 0000
		}

		// D.36 - Fone
		if(cliFone != null){
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(9, cliFone.getTelefone()));
		}else{
			registroDetalheConsumidor.append("         ");// 000000000
		}

		// D.37 - Data do Menor vencimento do débito
		if(menorData != null){
			String D207 = Util.recuperaDataInvertida(menorData);
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(8, D207));
		}else{
			registroDetalheConsumidor.append("        ");// 00000000
		}

		// D.38
		registroDetalheConsumidor.append(Util.completaString(" ", 15));
		// D.39
		registroDetalheConsumidor.append(Util.completaString(" ", 9));
		// D.40
		registroDetalheConsumidor.append(Util.completaString(" ", 60));
		// D.41
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(7, "" + quantidadeRegistros));
		return registroDetalheConsumidor;
	}

	private char[] geraRegistroTipoDetalheSPCBrasilInclusao(
					// Short tipoComando,
					Integer idImovel, BigDecimal valorTotalImovel, ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper,
					DadosNegativacaoPorImovelHelper dadosNegativacaoPorImovelHelper, Cliente cliente, String numeroRegistroString)
					throws ControladorException{

		char[] registroInclusao = new char[340];
		for(int i = 0; i < registroInclusao.length; i++){
			registroInclusao[i] = ' ';
		}
		// D2.01
		colocarConteudo("02", 1, registroInclusao);

		if(cliente.getCnpj() != null && cliente.getCnpj().length() > 0){
			// D2.02
			colocarConteudo("1", 3, registroInclusao);
			String cnpj = Util.adicionarZerosEsquedaNumero(15, "" + cliente.getCnpj());
			// D2.03
			colocarConteudo(cnpj, 4, registroInclusao);
		}else{
			// D2.02
			colocarConteudo("2", 3, registroInclusao);
			String cpf = Util.adicionarZerosEsquedaNumero(15, "" + cliente.getCpf());
			// D2.03
			colocarConteudo(cpf, 4, registroInclusao);
		}

		// D2.04
		colocarConteudo("I", 19, registroInclusao);
		// D2.05
		colocarConteudo("C", 20, registroInclusao);

		Date maiorData = null;
		Date menorData = null;

		maiorData = this.obterMaiorMenorVencimento(dadosNegativacaoPorImovelHelper.getColecaoConta(), dadosNegativacaoPorImovelHelper
						.getColecaoGuias(), 1);

		menorData = this.obterMaiorMenorVencimento(dadosNegativacaoPorImovelHelper.getColecaoConta(), dadosNegativacaoPorImovelHelper
						.getColecaoGuias(), 2);

		// String D207 = Util.recuperaDataInvertida(menorData);
		if(maiorData != null){
			String D206 = Util.formatarDataSemBarraDDMMAAAA(maiorData);
			colocarConteudo(D206, 21, registroInclusao);
		}
		if(menorData != null){
			String D207 = Util.formatarDataSemBarraDDMMAAAA(menorData);
			colocarConteudo(D207, 29, registroInclusao);
		}
		String valorString = Util.formatarMoedaReal(valorTotalImovel);
		String valorNovo = "";
		for(int i = 0; i < valorString.length(); i++){
			if(valorString.charAt(i) != '.' && valorString.charAt(i) != ','){
				valorNovo = valorNovo + valorString.charAt(i);
			}
		}
		valorString = valorNovo;
		valorString = Util.adicionarZerosEsquedaNumero(13, valorString);

		colocarConteudo(valorString, 37, registroInclusao);
		colocarConteudo(idImovel + "", 50, registroInclusao);

		String codigoAssociado = (String) ParametroSpcSerasa.P_CODIGO_ASSOCIADO_NEGATIVACAO.executar(ExecutorParametrosSpcSerasa
						.getInstancia());
		codigoAssociado = Util.adicionarZerosEsquedaNumero(8, codigoAssociado);
		colocarConteudo(codigoAssociado, 80, registroInclusao);

		colocarConteudo("00", 88, registroInclusao);

		numeroRegistroString = Util.adicionarZerosEsquedaNumero(6, numeroRegistroString);

		colocarConteudo(numeroRegistroString, 335, registroInclusao);
		return registroInclusao;
	}

	// GERA REGISTRO DETALHE CONSUMIDOR SPC *******
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ********************************************
	public StringBuilder geraRegistroTipoDetalheConsumidorSPCBrasil(int quantidadeRegistros, Cliente cliente) throws ControladorException,
					ErroRepositorioException{

		StringBuilder registroDetalheConsumidor = new StringBuilder();

		// ////1.1 DETALHE CONSUMIDOR
		// D1.01
		registroDetalheConsumidor.append("01");
		// D1.02
		String pracaConcessao = (String) ParametroSpcSerasa.P_PRACA_CONCESSAO_NEGATIVACAO.executar(ExecutorParametrosSpcSerasa
						.getInstancia());
		pracaConcessao = Util.adicionarZerosEsquedaNumero(8, pracaConcessao);
		registroDetalheConsumidor.append(pracaConcessao);
		// D1.03 - Nome do cliente
		String nomeCliente = cliente.getNome();
		registroDetalheConsumidor.append(Util.completaString(nomeCliente, 45));

		if(cliente.getCnpj() != null && cliente.getCnpj().length() > 0){
			// D1.04 - Indicador se tem ou não CNPJ ou CPF Preenchido
			registroDetalheConsumidor.append("1");
			// D1.05 - CPF ou CNPJ
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(15, cliente.getCnpj()));
		}else{
			// D1.04 - Indicador se tem ou não CNPJ ou CPF Preenchido
			registroDetalheConsumidor.append("2");
			// D1.05 - CPF ou CNPJ
			registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(15, cliente.getCpf()));
		}
		// D1.06 - RG Cliente
		registroDetalheConsumidor.append(Util.completaString(cliente.getRg(), 20));

		// D1.07 - Data de Nascimento
		String D107;
		if(cliente.getDataNascimento() != null){
			D107 = Util.formatarDataSemBarraDDMMAAAA((Date) cliente.getDataNascimento());
		}else{
			D107 = "";
		}
		registroDetalheConsumidor.append(Util.completaString(D107, 8));
		// D1.08 - Nome da Mae
		registroDetalheConsumidor.append(Util.completaString(cliente.getNomeMae(), 45));
		// D1.09 - Endereco
		String ender = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(cliente.getId());
		registroDetalheConsumidor.append(Util.completaString(ender, 50));

		// Obtem dados do endereço do cliente.
		ClienteEndereco cliEnder = (ClienteEndereco) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDadosEnderecoCliente(cliente
						.getId()));

		if(cliEnder != null){
			// D1.10 - numero
			registroDetalheConsumidor.append(Util.completaString(cliEnder.getNumero(), 5));
			// D1.11 - complemento
			registroDetalheConsumidor.append(Util.completaString(cliEnder.getComplemento(), 30));

			if(cliEnder.getLogradouroBairro() != null){
				// D1.12 - Bairro
				registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroBairro().getBairro().getNome(), 25));
				// D1.13 - CEP
				registroDetalheConsumidor.append(Util.completaString("" + cliEnder.getLogradouroCep().getCep().getCodigo(), 8));
				// D1.14 - Município
				registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getMunicipio(), 30));
				// D1.15 - Unidade da Federação
				registroDetalheConsumidor.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getSigla(), 2));

			}else{
				Cep cep = (Cep) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getCep(cliEnder.getId()));
				// D1.12 - Bairro
				registroDetalheConsumidor.append(Util.completaString(cep.getBairro(), 25));
				// D1.13 - CEP
				registroDetalheConsumidor.append(Util.completaString("" + cep.getCodigo(), 8));
				// D1.14 - Município
				registroDetalheConsumidor.append(Util.completaString(cep.getMunicipio(), 30));
				// D1.15 - Unidade da Federação
				registroDetalheConsumidor.append(Util.completaString(cep.getSigla(), 2));
			}
		}else{
			// D1.10 - numero
			registroDetalheConsumidor.append(Util.completaString("", 5));
			// D1.11 - complemento
			registroDetalheConsumidor.append(Util.completaString("", 30));
			// D1.12 - Bairro
			registroDetalheConsumidor.append(Util.completaString("", 25));
			// D1.13 - CEP
			registroDetalheConsumidor.append(Util.completaString("", 8));
			// D1.14 - Município
			registroDetalheConsumidor.append(Util.completaString("", 30));
			// D1.15 - Unidade da Federação
			registroDetalheConsumidor.append(Util.completaString("", 2));
		}

		ClienteFone cliFone = (ClienteFone) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDddFone(cliente.getId()));

		// D1.16 - DDD
		if(cliFone != null){
			registroDetalheConsumidor.append(Util.completaString(cliFone.getDdd(), 2));
		}else{
			registroDetalheConsumidor.append("  ");
		}

		// D1.17 - Preencher com espaços em branco
		registroDetalheConsumidor.append(Util.completaString(" ", 20));

		// D1.18 - Fone
		if(cliFone != null && !Util.isVazioOuBranco(cliFone.getTelefone()) && cliFone.getTelefone().length() >= 8){
			registroDetalheConsumidor.append(Util.completaString(cliFone.getTelefone().substring(0, 8), 8));
		}else{
			registroDetalheConsumidor.append("        ");
		}

		// D1.19 - Preencher com espaços em branco
		registroDetalheConsumidor.append(Util.completaString(" ", 10));

		// D1.20 - Número do registro
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(6, "" + quantidadeRegistros));

		return registroDetalheConsumidor;
	}

	// GERA REGISTRO HEADER SERASA **************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0008] **
	// ********************************************
	public StringBuilder geraRegistroTipoHeaderSERASA(int saEnvio, int quantidadeRegistros) throws ControladorException{

		// 2.1
		StringBuilder registroHeader = new StringBuilder();
		// H.01 Zero
		registroHeader.append("0");
		// H.02 PARAM_NNCNPJEMPRESA(Sistema Parametro)
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
		String cnpjEmpresa = sistemaParametro.getCnpjEmpresa();
		cnpjEmpresa = cnpjEmpresa.substring(0, 8);
		registroHeader.append(Util.adicionarZerosEsquedaNumero(9, cnpjEmpresa));
		// H.03 Data Corrente no Formato AAAAMMDD
		String dataAtualString = Util.recuperaDataInvertida(new Date());
		registroHeader.append(Util.adicionarZerosEsquedaNumero(8, dataAtualString));
		// H.04
		String ddd = (String) ParametroSpcSerasa.P_DDD_FONE_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO.executar(ExecutorParametrosSpcSerasa
						.getInstancia());
		ddd = Util.adicionarZerosEsquedaNumero(4, ddd);
		registroHeader.append(ddd);
		// H.05
		String numero = (String) ParametroSpcSerasa.P_NUMERO_FONE_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO
						.executar(ExecutorParametrosSpcSerasa.getInstancia());
		numero = Util.adicionarZerosEsquedaNumero(8, numero);
		registroHeader.append(numero);
		// H.06
		String ramal = (String) ParametroSpcSerasa.P_NUMERO_RAMAL_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO
						.executar(ExecutorParametrosSpcSerasa.getInstancia());
		ramal = Util.adicionarZerosEsquedaNumero(4, ramal);
		registroHeader.append(ramal);
		// H.07
		String nome = (String) ParametroSpcSerasa.P_NOME_CONTATO_INSTITUICAO_INFORMANTE_NEGATIVACAO.executar(ExecutorParametrosSpcSerasa
						.getInstancia());
		nome = Util.completaString(nome, 70);
		registroHeader.append(nome);
		// H.08
		registroHeader.append("SERASA-CONVEM04");
		// H.09
		registroHeader.append(Util.adicionarZerosEsquedaNumero(6, "" + (saEnvio + 1)));
		// H.010
		registroHeader.append("E");
		// H.11
		registroHeader.append("    ");
		// H.12
		registroHeader.append(Util.completaString(" ", 403));
		// H.13
		registroHeader.append(Util.completaString(" ", 60));
		// H.14
		registroHeader.append(Util.adicionarZerosEsquedaNumero(7, "" + quantidadeRegistros));

		return registroHeader;
	}

	/**
	 * Consulta as rotas para a geracao do resumo diario da negativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 1.0
	 * 
	 * @author Francisco do Nascimento
	 * @date 03/02/2009
	 */
	public List consultarRotasParaGerarResumoDiarioNegativacao() throws ControladorException{

		List retorno = null;

		try{
			retorno = repositorioSpcSerasa.consultarRotasParaGerarResumoDiarioNegativacao();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;

	}

	/**
	 * Método que retorna todas NegativacaoComando que ainda nao tenha sido executada
	 * (dataHoraRealizacao == null)
	 * [UC0687] Executar Comando de Negativação
	 * [Fluxo Principal] - Item 2.0
	 * 
	 * @author Thiago Toscano
	 * @date 21/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativacaoComando consultarNegativacaoComandadoParaExecutar() throws ControladorException{

		NegativacaoComando retorno = null;

		try{
			retorno = repositorioSpcSerasa.consultarNegativacaoComandadoParaExecutar();
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Consulta todos os contratos em vigencia de um negativador
	 * 
	 * @author Thiago Toscano
	 * @date 26/12/2007
	 * @param negativador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public NegativadorContrato consultarNegativadorContratoVigente(Integer negativador) throws ControladorException{

		NegativadorContrato retorno = null;

		try{
			retorno = repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;
	}

	public Object[] pesquisarParametroNegativacaoCriterio(Integer idNegativacaoCriterio) throws ControladorException{

		try{
			Object[] retorno = repositorioSpcSerasa.pesquisarParametroNegativacaoCriterio(idNegativacaoCriterio);
			return retorno;
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Pesquisar as localidades dos Imóveis que estão no resultado da simulação
	 * 
	 * @author Ana Maria
	 * @date 05/06/2008
	 */
	public Collection pesquisarRotasImoveisComandoSimulacao(Integer idNegativacaoComando) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarRotasImoveisComandoSimulacao(idNegativacaoComando);
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisar as rotas dos Imóveis
	 * 
	 * @author Ana Maria, Francisco do Nascimento
	 * @date 05/06/2008, 14/01/09
	 */
	public Collection pesquisarRotasImoveis() throws ControladorException{

		try{

			return repositorioSpcSerasa.pesquisarRotasImoveis();
		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por grupo de cobrança para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorCobrancaGrupoParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarRotasPorCobrancaGrupoParaNegativacao(nCriterio);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por gerencial regional para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorGerenciaRegionalParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarRotasPorGerenciaRegionalParaNegativacao(nCriterio);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por Unidade de Negocio para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorUnidadeNegocioParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarRotasPorUnidadeNegocioParaNegativacao(nCriterio);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por Localidade para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorLocalidadeParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarRotasPorLocalidadeParaNegativacao(nCriterio);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * UC0671 - Gerar movimento de inclusão da negativação
	 * Pesquisar rotas por Localidade para um critério de negativação
	 * 
	 * @author Francisco do Nascimento
	 * @date 14/01/2009
	 * @param nCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarRotasPorLocalidadesParaNegativacao(NegativacaoCriterio nCriterio) throws ControladorException{

		try{
			return repositorioSpcSerasa.pesquisarRotasPorLocalidadesParaNegativacao(nCriterio);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Gerar Registro de negativacao tipo header
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativação
	 * [SB0008] Gerar Registro de tipo header
	 * 
	 * @author Thiago Toscano
	 * @date 21/02/2008
	 * @param nc
	 * @param Object
	 *            [] obj
	 *            obj[0] Integer - quantidadeInclusao
	 *            obj[1] Integer - quantidadeItens
	 *            obj[2] BigDecimal - valorTotalDebito
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public Integer gerarRegistroDeInclusaoTipoHeader(Integer quantidadeRegistro, Negativador n, NegativadorContrato nContrato,
					NegativacaoCriterio nCriterio, Integer idNegativacaoMovimento) throws ControladorException{

		StringBuilder registroTipoHeader = null;
		try{
			if(n.getId().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
				// 1. Gerar Registro tipo Header SPC
				registroTipoHeader = this.geraRegistroTipoHeaderSPCSP(nContrato.getNumeroSequencialEnvio(), quantidadeRegistro);
			}else if(n.getId().equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
				// 1. Gerar Registro tipo Header Brasil
				registroTipoHeader = this.geraRegistroTipoHeaderSPCBrasil(nContrato.getNumeroSequencialEnvio(), quantidadeRegistro);
			}else{
				// 2. Gerar Registro tipo Header SERASA
				registroTipoHeader = this.geraRegistroTipoHeaderSERASA(nContrato.getNumeroSequencialEnvio(), quantidadeRegistro);
			}

			this.repositorioSpcSerasa.geraRegistroNegativacaoReg(n.getId(), nContrato.getNumeroSequencialEnvio(), idNegativacaoMovimento,
							registroTipoHeader, quantidadeRegistro, null);

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
		return quantidadeRegistro;

	}

	/**
	 * Apaga todos os ResumoNegativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 2.0
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public void apagarResumoNegativacao(Integer numeroPenultimaExecResumoNegat) throws ControladorException{

		try{
			repositorioSpcSerasa.apagarResumoNegativacao(numeroPenultimaExecResumoNegat);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Apaga todos os ResumoNegativacao
	 * [UC0688] Gerar Resumo Diário da Negativação
	 * Fluxo principal Item 2.0
	 * 
	 * @author Yara Taciane
	 * @date 28/07/2008
	 */
	public void apagarResumoNegativacao() throws ControladorException{

		try{
			repositorioSpcSerasa.apagarResumoNegativacao();
		}catch(ErroRepositorioException e){

			throw new ControladorException("erro.sistema", e);
		}

	}

	// GERA REGISTRO HEADER SPC para nova versão do SPC/SCPC V03
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0008] **
	// ********************************************
	public StringBuilder geraRegistroTipoHeaderSPCSP(int saEnvio, int quantidadeRegistros) throws ControladorException{

		StringBuilder registroHeader = new StringBuilder();
		// H.01 [001-008] - codigo do assocido
		String associado = ConstantesAplicacao.get("codigo_associado");
		registroHeader.append(associado);
		// H.02 [009-018] - 0000000000
		String constante = ConstantesAplicacao.get("constante_zeros");
		registroHeader.append(constante);
		// H.03 [019-024] - data corrente
		String dataAtualString = Util.recuperaDiaMesAnoCom2DigitosDaData(new Date());
		registroHeader.append(dataAtualString);
		// H.04 [025-031] - remessa
		String constanteRemessa = ConstantesAplicacao.get("constante_envio");
		registroHeader.append(constanteRemessa);
		// H.05 [025-031] - nome do infromante
		String nomeInformante = ConstantesAplicacao.get("nome_informante");
		registroHeader.append(Util.completaString(nomeInformante, 55));
		// H.06 [087-087] - controle de remessa
		registroHeader.append("0");
		// H.07 [088-094] - número de sequencia
		registroHeader.append(Util.adicionarZerosEsquedaNumero(7, "" + (saEnvio + 1)));
		// H.08 [095-102] - constante brancos
		registroHeader.append(Util.completaString(" ", 8));
		// H.09 [103-104] - versao
		String versao = ConstantesAplicacao.get("versao_spc_sao_paulo");
		registroHeader.append(versao);
		// H.10 [105-250] - constante brancos
		registroHeader.append(Util.completaString(" ", 146));

		return registroHeader;
	}

	// GERA REGISTRO HEADER SPC para nova versão do SPC/SCPC V03
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0008] **
	// ********************************************
	public StringBuilder geraRegistroTipoHeaderSPCBoaVista(int saEnvio, int quantidadeRegistros) throws ControladorException{

		StringBuilder registroHeader = new StringBuilder();
		// H.01 [001-008] - codigo do assocido
		String associado = ConstantesAplicacao.get("codigo_associado_spc_boa_vista");
		registroHeader.append(associado);
		// H.02 [009-018] - 0000000000
		String constante = ConstantesAplicacao.get("constante_zeros");
		registroHeader.append(constante);
		// H.03 [019-024] - data corrente
		String dataAtualString = Util.recuperaDiaMesAnoCom2DigitosDaData(new Date());
		registroHeader.append(dataAtualString);
		// H.04 [025-031] - remessa
		String constanteRemessa = ConstantesAplicacao.get("constante_envio");
		registroHeader.append(constanteRemessa);
		// H.05 [025-031] - nome da empresa
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
		String nomeEmpresa = sistemaParametro.getNomeEmpresa();
		registroHeader.append(Util.completaString(nomeEmpresa, 55));
		// H.06 [087-087] - controle de remessa
		registroHeader.append("0");
		// H.07 [088-094] - número de sequencia
		registroHeader.append(Util.adicionarZerosEsquedaNumero(7, "" + (saEnvio + 1)));
		// H.08 [095-102] - constante brancos
		registroHeader.append(Util.completaString(" ", 8));
		// H.09 [103-104] - versao
		// registroHeader.append(Negativador.NEGATIVADOR_SPC_BOA_VISTA.toString());
		registroHeader.append("04");
		// H.10 [105-250] - constante brancos
		String valor = Util.completaString(" ", 145);
		registroHeader.append(valor);
		return registroHeader;
	}

	// GERA REGISTRO TRAILLER SPC *****************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0012] **
	// ********************************************
	public StringBuilder geraRegistroTipoTraillerSPCSP(int quantidadeRegistros) throws ControladorException, ErroRepositorioException{

		StringBuilder registroTrailler = new StringBuilder();

		// T.01 [001-008] - codigo do assocido
		String associado = ConstantesAplicacao.get("codigo_associado");
		registroTrailler.append(associado);
		// T.02 [009-018] - Constante
		String constante = ConstantesAplicacao.get("constante_trailler");
		registroTrailler.append(constante);
		// T.03 [019-024] - data corrente
		String dataAtualString = Util.recuperaDiaMesAnoCom2DigitosDaData(new Date());
		registroTrailler.append(dataAtualString);
		// T.048 [025-250] - constante brancos
		registroTrailler.append(Util.completaString(" ", 226));

		return registroTrailler;
	}

	// GERA REGISTRO TRAILLER SPC BOA VISTA***********
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0012] **
	// ********************************************
	public StringBuilder geraRegistroTipoTraillerSPCBOAVISTA(int quantidadeRegistros) throws ControladorException, ErroRepositorioException{

		StringBuilder registroTrailler = new StringBuilder();

		// T.01 [001-008] - codigo do assocido
		String associado = ConstantesAplicacao.get("codigo_associado_spc_boa_vista");
		registroTrailler.append(associado);
		// T.02 [009-018] - Constante
		String constante = ConstantesAplicacao.get("constante_trailler");
		registroTrailler.append(constante);
		// T.03 [019-024] - data corrente
		String dataAtualString = Util.recuperaDiaMesAnoCom2DigitosDaData(new Date());
		registroTrailler.append(dataAtualString);
		// T.048 [025-250] - constante brancos
		registroTrailler.append(Util.completaString(" ", 225));
		registroTrailler.append(Util.completaString(".", 1));

		return registroTrailler;
	}

	// GERA REGISTRO DETALHE CONSUMIDOR SPC *******
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ********************************************
	public StringBuilder gerarRegistroTipoDetalheConsumidorNomeSPC(int quantidadeRegistros, Cliente cliente) throws ControladorException,
					ErroRepositorioException{

		StringBuilder registroDetalheConsumidor = new StringBuilder();
		// D1.01 - [001-008] - associado
		String associado = ConstantesAplicacao.get("codigo_associado");
		registroDetalheConsumidor.append(Util.completaString(associado, 8));
		// D1.02 - [009-009] - constante
		String constante = ConstantesAplicacao.get("constante_detalhe_01");
		registroDetalheConsumidor.append(Util.completaString(constante, 1));
		// D1.03 - [010-014] - sequencial
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(5, "" + quantidadeRegistros));
		// D1.04 - [015-015] - sistema #1-SCPC
		String sistema = ConstantesAplicacao.get("sistema");
		registroDetalheConsumidor.append(Util.completaString(sistema, 1));
		// D1.05 - [016-016] - constante
		String constanteDetalheSPC02 = ConstantesAplicacao.get("constante_detalhe_02");
		registroDetalheConsumidor.append(Util.completaString(constanteDetalheSPC02, 1));
		// D1.06 - [017-018] - constante
		String constanteDetalheSPC03 = ConstantesAplicacao.get("constante_detalhe_03");
		registroDetalheConsumidor.append(Util.completaString(constanteDetalheSPC03, 2));

		boolean indicadorPessoaFisica = true;
		// Informar o número do documento, sem brancos intermediários.
		// exemplo: CPF12345678901
		// D1.07 - [019-038] - documento principal
		String documento = "";
		if(cliente.getCpf() != null){
			documento = "CPF" + cliente.getCpf();
		}else if(cliente.getCnpj() != null){
			indicadorPessoaFisica = false;
			documento = "CNPJ" + cliente.getCnpj();
		}
		registroDetalheConsumidor.append(Util.completaString(documento, 20));
		// D1.08 - [039-058] - documento secundario
		registroDetalheConsumidor.append(Util.completaString("", 20));
		// D1.09 - [059-078] - documento terciario
		registroDetalheConsumidor.append(Util.completaString("", 20));
		// D1.10 - [079-128] - nome devedor
		if(indicadorPessoaFisica){
			String nome = cliente.getNome();
			if(nome.length() > 40){
				nome = nome.substring(0, 40);
			}
			registroDetalheConsumidor.append(Util.completaString(nome, 50));
		}else{
			registroDetalheConsumidor.append(Util.completaString(cliente.getNome(), 50));
		}
		// D1.11 - [129-134] - brancos
		registroDetalheConsumidor.append(Util.completaString("", 6));

		// D1.12 - [135-142]- Data de Nascimento
		String dataNascimento = "00000000";
		if(cliente.getDataNascimento() != null){
			dataNascimento = Util.formatarDataSemBarraDDMMAAAA((Date) cliente.getDataNascimento());
		}
		registroDetalheConsumidor.append(Util.completaString(dataNascimento, 8));

		// D1.13 - [143-182]- Nome do conjuge
		registroDetalheConsumidor.append(Util.completaString("", 40));

		// D1.14 - [183-198]- Constante brancos
		registroDetalheConsumidor.append(Util.completaString("", 16));

		// D1.15 - [199-204]- Constante zeros
		registroDetalheConsumidor.append("000000");

		// D1.16 - [205-224]- Naturalidade
		registroDetalheConsumidor.append(Util.completaString("", 20));

		// D1.17 - [225-226]- Unidade de Federacao
		registroDetalheConsumidor.append(Util.completaString("", 2));

		// D1.18 - [227-230]- Constante Brancos
		registroDetalheConsumidor.append(Util.completaString("", 4));

		// D1.19 - [231-250]- código
		// Este campo será fornecido no arquivo RETORNO, contendo os diagnósticos de
		// processamento, com até 10 ocorrências
		registroDetalheConsumidor.append(Util.completaString("", 20));

		return registroDetalheConsumidor;
	}

	// GERA REGISTRO DETALHE CONSUMIDOR SPC BOA VISTA**
	// ************************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ************************************************
	public StringBuilder gerarRegistroTipoDetalheConsumidorNomeSPC_BOA_VISTA(int quantidadeRegistros, Cliente cliente)
					throws ControladorException, ErroRepositorioException{

		StringBuilder registroDetalheConsumidor = new StringBuilder();
		// D1.01 - [001-008] - associado
		String associado = ConstantesAplicacao.get("codigo_associado_spc_boa_vista");
		registroDetalheConsumidor.append(Util.completaString(associado, 8));
		// D1.02 - [009-009] - constante
		String constante = ConstantesAplicacao.get("constante_detalhe_01");
		registroDetalheConsumidor.append(Util.completaString(constante, 1));
		// D1.03 - [010-014] - sequencial
		int valor = quantidadeRegistros;
		registroDetalheConsumidor.append(Util.adicionarZerosEsquedaNumero(5, "" + valor));
		// D1.04 - [015-015] - sistema #1-SCPC
		String sistema = ConstantesAplicacao.get("sistema");
		registroDetalheConsumidor.append(Util.completaString(sistema, 1));
		// D1.05 - [016-016] - constante
		String constanteDetalheSPC02 = ConstantesAplicacao.get("constante_detalhe_02");
		registroDetalheConsumidor.append(Util.completaString(constanteDetalheSPC02, 1));
		// D1.06 - [017-018] - constante
		String constanteDetalheSPC03 = ConstantesAplicacao.get("constante_detalhe_03");
		registroDetalheConsumidor.append(Util.completaString(constanteDetalheSPC03, 2));

		boolean indicadorPessoaFisica = true;
		// Informar o número do documento, sem brancos intermediários.
		// exemplo: CPF12345678901
		// D1.07 - [019-038] - documento principal
		String documento = "";
		if(cliente.getCpf() != null){
			documento = "CPF" + cliente.getCpf();
		}else if(cliente.getCnpj() != null){
			indicadorPessoaFisica = false;
			documento = "CNPJ" + cliente.getCnpj();
		}
		registroDetalheConsumidor.append(Util.completaString(documento, 20));
		// D1.08 - [039-058] - documento secundario
		registroDetalheConsumidor.append(Util.completaString("", 20));
		// D1.09 - [059-078] - documento terciario
		registroDetalheConsumidor.append(Util.completaString("", 20));
		// D1.10 - [079-128] - nome devedor
		if(indicadorPessoaFisica){
			String nome = cliente.getNome();
			if(nome.length() > 40){
				nome = nome.substring(0, 40);
			}
			registroDetalheConsumidor.append(Util.completaString(nome, 50));
		}else{
			registroDetalheConsumidor.append(Util.completaString(cliente.getNome(), 50));
		}
		// D1.11 - [129-134] - brancos
		registroDetalheConsumidor.append(Util.completaString("", 6));

		// D1.12 - [135-142]- Data de Nascimento
		String dataNascimento = "00000000";
		if(cliente.getDataNascimento() != null){
			dataNascimento = Util.formatarDataSemBarraDDMMAAAA((Date) cliente.getDataNascimento());
		}
		registroDetalheConsumidor.append(Util.completaString(dataNascimento, 8));

		// D1.13 - [143-182]- Nome do conjuge
		registroDetalheConsumidor.append(Util.completaString("", 40));

		// D1.14 - [183-198]- Constante brancos
		registroDetalheConsumidor.append(Util.completaString("", 16));

		// D1.15 - [199-204]- Constante zeros
		registroDetalheConsumidor.append("000000");

		// D1.16 - [205-224]- Naturalidade
		registroDetalheConsumidor.append(Util.completaString("", 20));

		// D1.17 - [225-226]- Unidade de Federacao
		registroDetalheConsumidor.append(Util.completaString("", 2));

		// D1.18 - [227-230]- Constante Brancos
		registroDetalheConsumidor.append(Util.completaString("", 4));

		// D1.19 - [231-250]- código
		// Este campo será fornecido no arquivo RETORNO, contendo os diagnósticos de
		// processamento, com até 10 ocorrências
		registroDetalheConsumidor.append(Util.completaString("", 19));
		registroDetalheConsumidor.append(".");

		return registroDetalheConsumidor;
	}

	// GERA REGISTRO DETALHE **********************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ********************************************
	public StringBuilder gerarRegistroTipoDetalheConsumidorEnderecoSPC(int quantidadeRegistros,
					DadosNegativacaoPorImovelHelper listaDadosImovel, Cliente cliente) throws ControladorException,
					ErroRepositorioException{

		StringBuilder registroDetalheSPC = new StringBuilder();

		// D2.01 - [001-008] - associado
		String associado = ConstantesAplicacao.get("codigo_associado");
		registroDetalheSPC.append(Util.completaString(associado, 8));
		// D2.02 - [009-009] - constante
		String constante = ConstantesAplicacao.get("constante_detalhe_01");
		registroDetalheSPC.append(Util.completaString(constante, 1));
		// D2.03 - [010-014] - sequencial
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(5, "" + quantidadeRegistros));
		// D2.04 - [015-015] - sistema #1-SCPC
		String sistema = ConstantesAplicacao.get("sistema");
		registroDetalheSPC.append(Util.completaString(sistema, 1));
		// D2.05 - [016-016] - constante
		String constanteDetalheSPC02 = ConstantesAplicacao.get("constante_detalhe_06");
		registroDetalheSPC.append(Util.completaString(constanteDetalheSPC02, 1));
		// D2.06 - [017-018] - constante
		String constanteDetalheSPC03 = ConstantesAplicacao.get("constante_detalhe_03");
		registroDetalheSPC.append(Util.completaString(constanteDetalheSPC03, 2));
		// Informar o número do documento, sem brancos intermediários.
		// exemplo: CPF12345678901
		// D2.07 - [019-038] - documento principal
		String documento = "";
		if(cliente.getCpf() != null){
			documento = "CPF" + cliente.getCpf();
		}else if(cliente.getCnpj() != null){
			documento = "CNPJ" + cliente.getCnpj();
		}
		registroDetalheSPC.append(Util.completaString(documento, 20));

		// D2.08 - [039-039] - tipo registro - 1 => Endereço do Devedor
		String tipoRegistro = ConstantesAplicacao.get("tipo_registro");
		registroDetalheSPC.append(tipoRegistro);
		// D1.09 - Endereco
		String ender = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(listaDadosImovel.getIdCliente(), true);
		registroDetalheSPC.append(Util.completaString(ender, 50));
		// Obtem dados do endereço do cliente.
		ClienteFone cliFone = null;
		ClienteEndereco cliEnder = (ClienteEndereco) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa
						.getDadosEnderecoCliente(listaDadosImovel.getIdCliente()));
		if(cliEnder != null){

			cliFone = (ClienteFone) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDddFone(cliEnder.getCliente().getId()));

			if(cliEnder.getLogradouroBairro() != null){
				// D1.10 - Bairro
				registroDetalheSPC.append(Util.completaString(cliEnder.getLogradouroBairro().getBairro().getNome(), 20));
				// D1.11 - CEP
				registroDetalheSPC.append(Util.completaString("" + cliEnder.getLogradouroCep().getCep().getCodigo(), 8));
				// D1.12 - MunicípioregistroDetalheSPC
				registroDetalheSPC.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getMunicipio(), 20));
				// D1.13 - Unidade da Federação
				registroDetalheSPC.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getSigla(), 2));

			}else{
				Cep cep = (Cep) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getCep(cliEnder.getId()));
				// D1.10 - Bairro
				registroDetalheSPC.append(Util.completaString(cep.getBairro(), 20));
				// D1.11 - CEP
				registroDetalheSPC.append(Util.completaString("" + cep.getCodigo(), 8));
				// D1.12 - Município
				registroDetalheSPC.append(Util.completaString(cep.getMunicipio(), 20));
				// D1.13 - Unidade da Federação
				registroDetalheSPC.append(Util.completaString(cep.getSigla(), 2));
			}
		}else{
			// D1.10 - Bairro
			registroDetalheSPC.append(Util.completaString("", 20));
			// D1.11 - CEP
			registroDetalheSPC.append(Util.completaString("", 8));
			// D1.12 - Município
			registroDetalheSPC.append(Util.completaString("", 20));
			// D1.13 - Unidade da Federação
			registroDetalheSPC.append(Util.completaString("", 2));
		}

		// D1.14 - telefone
		if(cliFone != null){
			registroDetalheSPC.append(Util.completaString(cliFone.getTelefone(), 20));
		}else{
			registroDetalheSPC.append(Util.completaString("", 20));
		}
		// D1.15 - constante brancos
		registroDetalheSPC.append(Util.completaString("", 71));
		// D1.16 - codigo retorno
		registroDetalheSPC.append(Util.completaString("", 19));
		registroDetalheSPC.append(".");

		return registroDetalheSPC;
	}

	public StringBuilder gerarRegistroTipoDetalheConsumidorEnderecoSPC_BOA_VISTA(int quantidadeRegistros,
					DadosNegativacaoPorImovelHelper listaDadosImovel, Cliente cliente) throws ControladorException,
					ErroRepositorioException{

		StringBuilder registroDetalheSPC = new StringBuilder();

		// D2.01 - [001-008] - associado
		String associado = ConstantesAplicacao.get("codigo_associado_spc_boa_vista");
		registroDetalheSPC.append(Util.completaString(associado, 8));
		// D2.02 - [009-009] - constante
		String constante = ConstantesAplicacao.get("constante_detalhe_01");
		registroDetalheSPC.append(Util.completaString(constante, 1));
		// D2.03 - [010-014] - sequencial
		int valor = quantidadeRegistros;
		registroDetalheSPC.append(Util.adicionarZerosEsquedaNumero(5, "" + valor));
		// D2.04 - [015-015] - sistema #1-SCPC
		String sistema = ConstantesAplicacao.get("sistema");
		registroDetalheSPC.append(Util.completaString(sistema, 1));
		// D2.05 - [016-016] - constante
		String constanteDetalheSPC02 = ConstantesAplicacao.get("constante_detalhe_06");
		registroDetalheSPC.append(Util.completaString(constanteDetalheSPC02, 1));
		// D2.06 - [017-018] - constante
		String constanteDetalheSPC03 = ConstantesAplicacao.get("constante_detalhe_03");
		registroDetalheSPC.append(Util.completaString(constanteDetalheSPC03, 2));
		// Informar o número do documento, sem brancos intermediários.
		// exemplo: CPF12345678901
		// D2.07 - [019-038] - documento principal
		String documento = "";
		if(cliente.getCpf() != null){
			documento = "CPF" + cliente.getCpf();
		}else if(cliente.getCnpj() != null){
			documento = "CNPJ" + cliente.getCnpj();
		}
		registroDetalheSPC.append(Util.completaString(documento, 20));

		// D2.08 - [039-039] - tipo registro - 1 => Endereço do Devedor
		String tipoRegistro = ConstantesAplicacao.get("tipo_registro");
		registroDetalheSPC.append(tipoRegistro);
		// D1.09 - Endereco
		String ender = this.getControladorEndereco().pesquisarEnderecoClienteAbreviado(listaDadosImovel.getIdCliente(), true);
		registroDetalheSPC.append(Util.completaString(ender, 50));
		// Obtem dados do endereço do cliente.
		ClienteFone cliFone = null;
		ClienteEndereco cliEnder = (ClienteEndereco) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa
						.getDadosEnderecoCliente(listaDadosImovel.getIdCliente()));
		if(cliEnder != null){

			cliFone = (ClienteFone) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDddFone(cliEnder.getCliente().getId()));

			if(cliEnder.getLogradouroBairro() != null){
				// D1.10 - Bairro
				registroDetalheSPC.append(Util.completaString(cliEnder.getLogradouroBairro().getBairro().getNome(), 20));
				// D1.11 - CEP
				registroDetalheSPC.append(Util.completaString("" + cliEnder.getLogradouroCep().getCep().getCodigo(), 8));
				// D1.12 - MunicípioregistroDetalheSPC
				registroDetalheSPC.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getMunicipio(), 20));
				// D1.13 - Unidade da Federação
				registroDetalheSPC.append(Util.completaString(cliEnder.getLogradouroCep().getCep().getSigla(), 2));

			}else{
				Cep cep = (Cep) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getCep(cliEnder.getId()));
				// D1.10 - Bairro
				registroDetalheSPC.append(Util.completaString(cep.getBairro(), 20));
				// D1.11 - CEP
				registroDetalheSPC.append(Util.completaString("" + cep.getCodigo(), 8));
				// D1.12 - Município
				registroDetalheSPC.append(Util.completaString(cep.getMunicipio(), 20));
				// D1.13 - Unidade da Federação
				registroDetalheSPC.append(Util.completaString(cep.getSigla(), 2));
			}
		}else{
			// D1.10 - Bairro
			registroDetalheSPC.append(Util.completaString("", 20));
			// D1.11 - CEP
			registroDetalheSPC.append(Util.completaString("", 8));
			// D1.12 - Município
			registroDetalheSPC.append(Util.completaString("", 20));
			// D1.13 - Unidade da Federação
			registroDetalheSPC.append(Util.completaString("", 2));
		}

		// D1.14 - telefone
		if(cliFone != null){
			registroDetalheSPC.append(Util.completaString(cliFone.getTelefone(), 20));
		}else{
			registroDetalheSPC.append(Util.completaString("", 20));
		}
		// D1.15 - constante brancos
		registroDetalheSPC.append(Util.completaString("", 71));
		// D1.16 - codigo retorno
		registroDetalheSPC.append(Util.completaString("", 19));
		registroDetalheSPC.append(".");

		return registroDetalheSPC;
	}

	// GERA REGISTRO DETALHE **********************
	// ********************************************
	// CAMPOS CONFORME LAYOUT DO UC0671 [SB0009] **
	// ********************************************
	public StringBuilder gerarRegistroTipoDetalheOcorrenciaSPC(int quantidadeRegistros, DadosNegativacaoPorImovelHelper listaDadosImovel,
					Cliente cliente, BigDecimal valorTotalImovel, NegativadorContrato nContrato, String operacao)
					throws ControladorException, ErroRepositorioException{

		StringBuilder registroDetalheOcorrenciaSPC = new StringBuilder();

		// D3.01 - [001-008] - associado
		String associado = ConstantesAplicacao.get("codigo_associado");
		registroDetalheOcorrenciaSPC.append(Util.completaString(associado, 8));

		// D3.02 - [009-009] - constante
		String constante = ConstantesAplicacao.get("constante_detalhe_01");
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.03 - [010-014] - sequencial
		registroDetalheOcorrenciaSPC.append(Util.adicionarZerosEsquedaNumero(5, "" + quantidadeRegistros));

		// D3.04 - [015-015] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.05 - [016-016] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.06 - [017-018] - operação
		registroDetalheOcorrenciaSPC.append(operacao);

		// D3.07 - [019-038] - documento
		String documento = "";
		if(cliente.getCpf() != null){
			documento = "CPF" + cliente.getCpf();
		}else if(cliente.getCnpj() != null){
			documento = "CNPJ" + cliente.getCnpj();
		}
		registroDetalheOcorrenciaSPC.append(Util.completaString(documento, 20));

		// D3.08 - [039-046] - data ocorrência
		String dataAtualString = Util.formatarDataSemBarraDDMMAAAA(new Date());
		registroDetalheOcorrenciaSPC.append(dataAtualString);

		// D3.09 - [047-048] - Ocorrencia
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 2));

		// D3.10 - [049-070] - contrato
		registroDetalheOcorrenciaSPC.append(Util.completaString(nContrato.getNumeroContrato(), 22));

		// D3.10 - [071-090] - nome avalista
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 20));

		// D3.10 - [091-101] - valor débito
		String valorDebito = Util.formatarMoedaReal(valorTotalImovel);
		String valorNovo = "";
		for(int i = 0; i < valorDebito.length(); i++){
			if(valorDebito.charAt(i) != '.' && valorDebito.charAt(i) != ','){
				valorNovo = valorNovo + valorDebito.charAt(i);
			}
		}
		valorDebito = valorNovo;
		valorDebito = Util.adicionarZerosEsquedaNumero(11, valorDebito);
		registroDetalheOcorrenciaSPC.append(valorDebito);

		// D3.13 - [102-103] - Documentos de Debitos
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 2));

		// D3.14 - [104-120] - Reservado
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 17));

		// D3.15 - [121-121] - constante
		String constante15 = ConstantesAplicacao.get("constante_detalhe_04");
		registroDetalheOcorrenciaSPC.append(constante15);

		// D3.16 - [122-123] - constante
		String constante16 = ConstantesAplicacao.get("constante_detalhe_05");
		registroDetalheOcorrenciaSPC.append(constante16);

		// D3.17 - [124-124] - opcao boleto devedor
		String constante17 = ConstantesAplicacao.get("constante_detalhe_04");
		registroDetalheOcorrenciaSPC.append(constante17);

		// D3.18 - [125-125] - opcao boleto avalista
		String constante18 = ConstantesAplicacao.get("constante_detalhe_04");
		registroDetalheOcorrenciaSPC.append(constante18);

		// D3.19 - [126-133] - data vencimento
		String diasVencimento = ConstantesAplicacao.get("dias_vencimento");
		Date dataVencimento = Util.adicionarNumeroDiasDeUmaData(new Date(), Util.converterStringParaInteger(diasVencimento));
		String dataVencimentoString = Util.formatarDataSemBarraDDMMAAAA(dataVencimento);
		registroDetalheOcorrenciaSPC.append(dataVencimentoString);

		// D3.20 - [134-144] - valor cobranca
		registroDetalheOcorrenciaSPC.append(valorDebito);

		// D3.21 - [145-215] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 71));

		// D3.22 - [145-215] - reservador
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 15));

		// D3.23 - [231-250] - codigo retorno
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 20));

		return registroDetalheOcorrenciaSPC;
	}

	public StringBuilder gerarRegistroTipoDetalheOcorrenciaSPC_BOA_VISTA(int quantidadeRegistros,
					DadosNegativacaoPorImovelHelper listaDadosImovel, Cliente cliente, BigDecimal valorTotalImovel,
					NegativadorContrato nContrato, String operacao) throws ControladorException, ErroRepositorioException{

		StringBuilder registroDetalheOcorrenciaSPC = new StringBuilder();

		// D3.01 - [001-008] - associado
		String associado = ConstantesAplicacao.get("codigo_associado_spc_boa_vista");
		registroDetalheOcorrenciaSPC.append(Util.completaString(associado, 8));

		// D3.02 - [009-009] - constante
		String constante = ConstantesAplicacao.get("constante_detalhe_01");
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.03 - [010-014] - sequencial
		int valor = quantidadeRegistros;
		registroDetalheOcorrenciaSPC.append(Util.adicionarZerosEsquedaNumero(5, "" + valor));

		// D3.04 - [015-015] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.05 - [016-016] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.06 - [017-018] - operação
		registroDetalheOcorrenciaSPC.append(operacao);

		// D3.07 - [019-038] - documento
		String documento = "";
		if(cliente.getCpf() != null){
			documento = "CPF" + cliente.getCpf();
		}else if(cliente.getCnpj() != null){
			documento = "CNPJ" + cliente.getCnpj();
		}
		registroDetalheOcorrenciaSPC.append(Util.completaString(documento, 20));

		// D3.08 - [039-046] - data ocorrência

		// Date data =
		// repositorioSpcSerasa.pesquisaMaiorDataVencimentoDosDebitos(listaDadosImovel.getIdImovel());
		Date maiorData = null;
		maiorData = this.obterMaiorMenorVencimento(listaDadosImovel.getColecaoConta(), listaDadosImovel.getColecaoGuias(), 1);
		String dataAtualString = Util.formatarDataSemBarraDDMMAAAA(maiorData);
		// String dataAtualString = Util.formatarDataSemBarraDDMMAAAA(new Date()); // Maior data de
																				// vencimento dos
																				// itens de débito
																				// do imóvel
		registroDetalheOcorrenciaSPC.append(dataAtualString);

		// D3.09 - [047-048] - Ocorrencia
		// registroDetalheOcorrenciaSPC.append(Util.completaString("", 2)); //Caso campo D3.07 seja
		// CPF, 'RG'; caso contrário, 'XX'
		if(cliente.getCpf() != null){
			registroDetalheOcorrenciaSPC.append("RG");
		}else{
			registroDetalheOcorrenciaSPC.append("XX");
		}

		// D3.10 - [049-070] - contrato
		registroDetalheOcorrenciaSPC.append(Util.completaString(listaDadosImovel.getIdImovel().toString(), 22));

		// D3.10 - [071-090] - nome avalista
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 20));

		// D3.10 - [091-101] - valor débito
		String valorDebito = Util.formatarMoedaReal(valorTotalImovel);
		String valorNovo = "";
		for(int i = 0; i < valorDebito.length(); i++){
			if(valorDebito.charAt(i) != '.' && valorDebito.charAt(i) != ','){
				valorNovo = valorNovo + valorDebito.charAt(i);
			}
		}
		valorDebito = valorNovo;
		valorDebito = Util.adicionarZerosEsquedaNumero(11, valorDebito);
		registroDetalheOcorrenciaSPC.append(valorDebito);

		// D3.13 - [102-103] - Documentos de Debitos
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 2));

		// D3.14 - [104-120] - Reservado
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 17));

		// D3.15 - [121-121] - constante
		String constante15 = ConstantesAplicacao.get("constante_detalhe_04");
		registroDetalheOcorrenciaSPC.append(constante15);

		// D3.16 - [122-123] - constante
		String constante16 = ConstantesAplicacao.get("constante_detalhe_05");
		registroDetalheOcorrenciaSPC.append(constante16);

		// D3.17 - [124-124] - opcao boleto devedor
		String constante17 = ConstantesAplicacao.get("constante_detalhe_04");
		registroDetalheOcorrenciaSPC.append(constante17);

		// D3.18 - [125-125] - opcao boleto avalista
		String constante18 = ConstantesAplicacao.get("constante_detalhe_04");
		registroDetalheOcorrenciaSPC.append(constante18);

		// D3.19 - [126-133] - data vencimento
		// String diasVencimento = ConstantesAplicacao.get("dias_vencimento");
		// Date dataVencimento = Util.adicionarNumeroDiasDeUmaData(new Date(),
		// Util.converterStringParaInteger(diasVencimento));
		// String dataVencimentoString = Util.formatarDataSemBarraDDMMAAAA(dataVencimento);
		// registroDetalheOcorrenciaSPC.append(dataVencimentoString);
		registroDetalheOcorrenciaSPC.append(Util.completarStringZeroDireita("", 8));

		// D3.20 - [134-144] - valor cobranca
		// registroDetalheOcorrenciaSPC.append(valorDebito);
		registroDetalheOcorrenciaSPC.append(Util.completarStringZeroDireita("", 11));

		// D3.21 - [145-215] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 71));

		// D3.22 - [145-215] - reservador
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 15));

		// D3.23 - [231-250] - codigo retorno
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 19));
		registroDetalheOcorrenciaSPC.append(".");

		return registroDetalheOcorrenciaSPC;
	}

	/**
	 * [UC0672] Registrar
	 * Movimento de Retorno dos Negativadores
	 * [SB0001] - Validar Arquivo de Movimento de Retorno do SPC
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 *             Tenho que verificar se é spc ou serasa aqui nesse método.
	 */

	private Object[] validarArquivoMovimentoRetornoSPCSaoPaulo(StringBuilder stringBuilderTxt, Negativador negativador)
					throws ControladorException{

		Object[] retorno = new Object[2];
		Object[] retornoHeader = new Object[2];
		NegativadorMovimento negativadorMovimento = null;
		String registro = "";
		String tipoRegistro = "";

		// ----------------------------------------------------------------
		// [SB0001] - Validar Arquivo de Movimento de Retorno do SPC
		// ----------------------------------------------------------------

		String constanteHeader = ConstantesAplicacao.get("constante_header");
		String constanteTrailler = ConstantesAplicacao.get("constante_trailler");

		int countRegistro = 0;
		Collection collRegistrosLidos = new ArrayList();

		StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

		while(stk.hasMoreTokens()){

			// ---------------------------------------------------
			registro = stk.nextToken();
			collRegistrosLidos.add(registro);

			countRegistro = countRegistro + 1;
			// ---------------------------------------------------

			// H.01
			tipoRegistro = getConteudo(9, 10, registro.toCharArray());

			// --------------------------------------------------------------------------------------
			// Verifica Header (Primeira Linha)
			// ---------------------------------------------------------------------------------------
			if(countRegistro == 1 && tipoRegistro.equals(constanteHeader)){
				// só p/ header
				try{
					retornoHeader = this.verificarHeaderSPCRetorno(registro, negativador);

					negativadorMovimento = (NegativadorMovimento) retornoHeader[1];

				}catch(ErroRepositorioException e1){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e1);
				}

			}else{

				if(countRegistro > 1 && tipoRegistro.equals(constanteTrailler)){
					break;
				}
			}

		}

		// ----------------------------------------------------------------------------------------
		if(!tipoRegistro.equals(constanteTrailler)){
			throw new ControladorException("atencao.arquivo_movimento_nao_possui_trailler");
		}

		retorno[0] = collRegistrosLidos;
		retorno[1] = negativadorMovimento;
		return retorno;
		// --------------------------------------------------------------------------------------------
	}

	public Object[] verificarHeaderSPCRetorno(String registro, Negativador negativador) throws ControladorException,
					ErroRepositorioException{

		Object[] retorno = new Object[3];

		// H.01 [001-008] - codigo do assocido
		String associadoRetorno = getConteudo(1, 8, registro.toCharArray());
		String associado = ConstantesAplicacao.get("codigo_associado");
		if(!associadoRetorno.equals(associado)){
			SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();

			throw new ControladorException("atencao.arquivo_movimento_sem_associado", null, sistemaParametro.getNomeEmpresa());
		}
		// H.02 [009-018] - 0000000000
		String constanteZerosRetorno = getConteudo(9, 10, registro.toCharArray());
		String constanteZeros = ConstantesAplicacao.get("constante_zeros");
		if(!constanteZerosRetorno.equals(constanteZeros)){
			throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_tipos_invalidos");
		}
		// H.03 [019-024] - data corrente
		String dataCorrenteRetorno = getConteudo(19, 6, registro.toCharArray());
		if(dataCorrenteRetorno.equals("")){
			throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_tipos_invalidos");
		}
		// H.04 [025-031] - retorno
		String constante = ConstantesAplicacao.get("constante_retorno");
		String constanteRetorno = getConteudo(25, 7, registro.toCharArray());
		if(!constanteRetorno.equals(constante)){
			throw new ControladorException("atencao.operacao_nao_corresponde_retorno");
		}
		// H.05 [032-086] - nome do informante
		String nome = ConstantesAplicacao.get("nome_informante");
		String nomeRetorno = getConteudo(32, 55, registro.toCharArray());
		if(!nomeRetorno.trim().equals(nome)){
			throw new ControladorException("atencao.arquivo_movimento_associado_nao_corresponde_cadastrado_sistema", null, nome);
		}
		// H.06 [087-094] - controle do usuário
		Integer numeroSequencialArquivo = Util.converterStringParaInteger(getConteudo(88, 7, registro.toCharArray()));
		NegativadorContrato negativadorContrato = new NegativadorContrato();
		try{
			negativadorContrato = this.repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador.getId());
			if(negativadorContrato == null){
				throw new ControladorException("atencao.nao_ha_contrato_negativador");
			}else{
				String numeroSequencialEnvioBD = negativadorContrato.getNumeroSequencialEnvio() + "";
				String qtdZeros = "";
				int tamanho = 8 - numeroSequencialEnvioBD.length();
				for(int i = 0; i < tamanho; i++){
					qtdZeros = qtdZeros + "0";
				}
				numeroSequencialEnvioBD = qtdZeros + numeroSequencialEnvioBD;
				// H.06
				if(numeroSequencialArquivo > Util.converterStringParaInteger(numeroSequencialEnvioBD)){
					throw new ControladorException("atencao.movimento_fora_sequencia");
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// H.09 - quantidade de resgitros.
		NegativadorMovimento negativadorMovimento = this.repositorioSpcSerasa.getNegativadorMovimento(negativador, numeroSequencialArquivo,
						true);
		verificarExistenciaRetornoParaMovimentoEnvio(negativadorMovimento);

		String numeroRegistro = constanteZerosRetorno;
		Integer numRegistro = Util.converterStringParaInteger(numeroRegistro);

		NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(negativadorMovimento,
						numRegistro);

		if(negativadorMovimentoReg != null && negativadorMovimentoReg.getConteudoRegistro() == null){
			throw new ControladorException("atencao.arquivo_movimento_sem_registros");
		}

		// [FS0012 - Verificar erro de Header/Trailler no arquivo retorno], passando o conteúdo do
		// primeiro registro de movimento.
		String[] regLinha = new String[2];
		regLinha[0] = registro;

		Object[] negativadorMovimentoRegistrosLinha = new Object[2];
		negativadorMovimentoRegistrosLinha[0] = negativadorMovimentoReg;

		verificarErroHeaderTraillerNoArquivoRetorno(negativador, regLinha);

		retorno[0] = numRegistro;
		retorno[1] = negativadorMovimento;
		retorno[2] = numeroSequencialArquivo;
		return retorno;

	}

	private NegativadorMovimentoReg atualizarCodigoRetornoRegistro(String campoCodigoRetorno, Negativador negativador,
					NegativadorMovimentoReg negativadorMovimentoReg) throws ControladorException, ErroRepositorioException{

		// --------------------------------------------------------------------------------
		// atribuir o valor um ao campo indicador registro aceito
		Short indicadorRegistroAceito = ConstantesSistema.ACEITO;
		// -------------------------------------------------------------------------------
		String codigoRetorno = "-1";

		if(campoCodigoRetorno.equals(ConstantesAplicacao.get("indicador_retorno_aceito"))){

			Integer codRetorno = Util.converterStringParaInteger(NegativadorRetornoMotivo.OPERACAO_BEM_SUCEDIDA);


			NegativadorRetornoMotivo negativadorRetornoMot = repositorioSpcSerasa.pesquisarNegativadorRetornoMotivo(codRetorno, negativador
							.getId());

			if(negativadorRetornoMot != null){
				this.inserirNegativadorMovimentoRegRetMot(negativadorMovimentoReg, negativadorRetornoMot);
			}else{
				throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
			}

		}else{

			for(int j = 0; j < campoCodigoRetorno.length(); j = j + 2){

				codigoRetorno = campoCodigoRetorno.substring(j, j + 2);

				if(!codigoRetorno.equals(NegativadorRetornoMotivo.OPERACAO_BEM_SUCEDIDA)){

					indicadorRegistroAceito = ConstantesSistema.NAO_ACEITO;

					Integer codRetorno = Util.converterStringParaInteger(codigoRetorno);
					NegativadorRetornoMotivo negativadorRetornoMot = repositorioSpcSerasa.pesquisarNegativadorRetornoMotivo(codRetorno,
									negativador.getId());

					if(negativadorRetornoMot != null){
						this.inserirNegativadorMovimentoRegRetMot(negativadorMovimentoReg, negativadorRetornoMot);
					}else{
						throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
					}
				}

			}
		}

		// 5.0
		if(indicadorRegistroAceito.equals(ConstantesSistema.NAO_ACEITO)){

			// [início] - Alteração 05/05/2008 - Indicar Exclusão do imóvel caso a inclusão da
			// negativação não seja aceita.
			// -------------------------------------------------------------------------------------------------
			NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

			Imovel imovel = negativadorMovimentoReg.getImovel();
				
			if(imovel != null){
				Integer idComando = null;
				Integer idCronograma = null;
				if(negativadorMovimento.getCobrancaAcaoAtividadeComando() != null){
					idComando = negativadorMovimento.getCobrancaAcaoAtividadeComando().getId();
				}else{
					idCronograma = negativadorMovimento.getCobrancaAcaoAtividadeCronograma().getId();
				}

				Collection collNegativacaoImovel = repositorioSpcSerasa.pesquisarNegativacaoImovel(
imovel.getId(), idCronograma, idComando);

				NegativacaoImovei negativacaoImoveis = (NegativacaoImovei) Util.retonarObjetoDeColecao(collNegativacaoImovel);

				try{
					if(negativacaoImoveis != null){
						// 5.1
						short indicadorExcluido = 1;
						negativacaoImoveis.setIndicadorExcluido(indicadorExcluido);

						Date dataExclusao = new Date();
						negativacaoImoveis.setDataExclusao(dataExclusao);

						Date ultimaAlteracao = new Date();
						negativacaoImoveis.setUltimaAlteracao(ultimaAlteracao);

						// indicar a exclusão do imóvel.
						repositorioUtil.atualizar(negativacaoImoveis);

						// escolher cobrança situação
						Integer idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
						if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SERASA)){
							idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
						}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
							idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP;
						}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
							idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL;
						}

						// 5.2
						FiltroImovel filtroImovel = new FiltroImovel();
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.COBRANCA_SITUACAO_ID, idCobrancaSituacao));

						Imovel imovelRetorno = (Imovel) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(filtroImovel, Imovel.class
										.getName()));

						if(imovelRetorno != null){
							imovelRetorno.setCobrancaSituacao(null);
							imovelRetorno.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(imovelRetorno);
						}

						// 5.3
						CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
						cobrancaSituacao.setId(idCobrancaSituacao);

						ImovelCobrancaSituacao imovelCobrancaSituacao = repositorioSpcSerasa.getImovelCobrancaSituacao(imovel,
										cobrancaSituacao);

						if(imovelCobrancaSituacao != null){
							imovelCobrancaSituacao.setDataRetiradaCobranca(new Date());
							imovelCobrancaSituacao.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(imovelCobrancaSituacao);
						}
					}
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
			}

		}

		// 9.
		verificarRegistroInclusao(negativadorMovimentoReg, negativadorMovimentoReg.getIndicadorAceito(), negativador);

		// -------------------------------------------------------------------------------------------------
		// [fim] - Alteração 05/05/2008 - Indicar Exclusão do imóvel caso a inclusão da negativação
		// não seja aceita.
		// -------------------------------------------------------------------------------------------------
		negativadorMovimentoReg.setIndicadorAceito(indicadorRegistroAceito);

		return negativadorMovimentoReg;

	}

	/**
	 * Gerar Registro de negativacao tipo detalhe - nova versão
	 * [UC0671] Gerar Movimento de Inclusao de Nwegativação
	 * [SB0009] Gerar Registro de tipo Detalhe
	 * 
	 * @author Yara Souza
	 * @date 22/12/2010
	 * @param nc
	 * @param Object
	 *            [] obj
	 *            obj[0] Integer - quantidadeInclusao
	 *            obj[1] Integer - quantidadeItens
	 *            obj[2] BigDecimal - valorTotalDebito
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private Integer gerarRegistroDeInclusaoTipoDetalhe(Integer quantidadeRegistro, Negativador n, Integer idNegativacaoMovimento,
					NegativacaoCriterio nCriterio, Integer idImovel, BigDecimal valorTotalImovel,
					ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper,
					DadosNegativacaoPorImovelHelper dadosNegativacaoPorImovelHelper, Cliente cliente,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, NegativadorContrato nContrato) throws ControladorException,
					ErroRepositorioException{

		NegativadorMovimento nm = new NegativadorMovimento();
		nm.setId(idNegativacaoMovimento);
		Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(this.repositorioSpcSerasa.getDadosImoveis(idImovel));

		/*
		 * CASO SPC - Novo modelo.
		 * Linha 01 - Gerar Registro de tipo Detalhe Consumidor Nome
		 * Linha 02 - Gerar Registro de tipo Detalhe Consumidor Endereço
		 * Linha 03 - Gerar Registro de tipo Detalhe Ocorrencia SPC
		 */

		StringBuilder sb = null;
		NegativadorRegistroTipo nrt = null;
		NegativadorMovimentoReg nmr = null;
		if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){

			/*
			 * Linha 01 - Gerar Registro de tipo Detalhe Consumidor Nome
			 * Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe Consumidor Nome
			 */

			quantidadeRegistro = quantidadeRegistro + 1;
			sb = this.gerarRegistroTipoDetalheConsumidorNomeSPC(quantidadeRegistro, cliente);

			nrt = new NegativadorRegistroTipo();
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_NOME_SP);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(sb.toString());
			nmr.setUltimaAlteracao(new Date());
			// if(tipoComando.equals(ConstantesSistema.TIPO_COMANDO_POR_CRITERIO)){
			// nmr.setNegativacaoCriterio(nCriterio);
			// }
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			repositorioUtil.inserir(nmr);

			/*
			 * Linha 02 - Gerar Registro de tipo Detalhe Consumidor Endereço
			 * Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe Consumidor Endereço
			 */

			// quantidadeRegistro = quantidadeRegistro + 1;
			sb = this.gerarRegistroTipoDetalheConsumidorEnderecoSPC(quantidadeRegistro, dadosNegativacaoPorImovelHelper, cliente);

			nrt = new NegativadorRegistroTipo();
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_ENDERECO_SP);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(sb.toString());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			repositorioUtil.inserir(nmr);

			/*
			 * Linha 03 - Gerar Registro de tipo Detalhe Ocorrencia SPC
			 * Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe Ocorrência SPC
			 */

			sb = this.gerarRegistroTipoDetalheOcorrenciaSPC(quantidadeRegistro, dadosNegativacaoPorImovelHelper, cliente, valorTotalImovel,
							nContrato, ConstantesAplicacao.get("operacao_inclusao"));

			int idLocalidade = imovel.getLocalidade().getId();
			int idQuadra = imovel.getQuadra().getId();
			int stComercialCD = imovel.getQuadra().getSetorComercial().getCodigo();
			int numeroQuadra = imovel.getQuadra().getNumeroQuadra();
			int iper_id = imovel.getImovelPerfil().getId();

			Categoria categoria = null;
			categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(imovel.getId());

			Integer idDebitoSituacao = CobrancaDebitoSituacao.PENDENTE;
			CobrancaDebitoSituacao cobDebSit = new CobrancaDebitoSituacao();
			cobDebSit.setId(idDebitoSituacao);
			Localidade localidade = new Localidade(idLocalidade);
			Quadra quadra = new Quadra(idQuadra);
			ImovelPerfil imovelPerfil = new ImovelPerfil();
			imovelPerfil.setId(iper_id);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_OCORRENCIA_SPC_SP);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(sb.toString());
			nmr.setValorDebito(valorTotalImovel);
			nmr.setCobrancaDebitoSituacao(cobDebSit);
			nmr.setImovel(imovel);
			nmr.setLocalidade(localidade);
			nmr.setQuadra(quadra);
			nmr.setCodigoSetorComercial(stComercialCD);
			nmr.setNumeroQuadra(numeroQuadra);
			nmr.setImovelPerfil(imovelPerfil);
			nmr.setCliente(cliente);
			nmr.setCategoria(categoria);
			nmr.setNumeroCpf(cliente.getCpf());
			nmr.setNumeroCnpj(cliente.getCnpj());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			nmr.setNumeroContrato(imovel.getId().toString());

			//quantidadeRegistro = quantidadeRegistro + 1;

			// adicionado por Vivianne Sousa - 21/08/2009 - analista:Fátima Sampaio
			// CBST_ID da tabela COBRANCA_SITUACAO com o valor correspondente a “EM ANALISE PARA
			// NEGATIVACAO”
			CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
			cobrancaSituacao.setId(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP);
			nmr.setCobrancaSituacao(cobrancaSituacao);

			Integer idDetalheReg = (Integer) repositorioUtil.inserir(nmr);

			nmr.setId(idDetalheReg);

			/*
			 * 1.5 Para cada item da lista de contas e guias de pagamento do imovel, gera os itens
			 * do
			 * registro do movimento da negativação correspondente ao registro tipo Detalhe SPC
			 */
			Collection<ContaValoresHelper> colecaoContasValores = null;
			colecaoContasValores = dadosNegativacaoPorImovelHelper.getColecaoConta();

			// varre lista de contas de pagamento
			if(colecaoContasValores != null){
				Iterator itColecaoContasValores = colecaoContasValores.iterator();
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(1);
				while(itColecaoContasValores.hasNext()){
					ContaValoresHelper contaValores = (ContaValoresHelper) itColecaoContasValores.next();
					ContaGeral contaGeral = new ContaGeral();
					contaGeral.setConta(contaValores.getConta());
					contaGeral.setId(contaValores.getConta().getId());
					NegativadorMovimentoRegItem negativadorMovimentoRegItem = new NegativadorMovimentoRegItem();
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setNegativadorMovimentoReg(nmr);
					negativadorMovimentoRegItem.setDocumentoTipo(documentoTipo);
					negativadorMovimentoRegItem.setContaGeral(contaGeral);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setValorDebito(contaValores.getValorTotalConta());
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

					getControladorUtil().inserir(negativadorMovimentoRegItem);

				}
			}

			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = null;
			colecaoGuiasPagamentoValores = dadosNegativacaoPorImovelHelper.getColecaoGuias();

			// varre lista de guias de pagamento
			if(colecaoGuiasPagamentoValores != null){
				Iterator itColecaoGuiasPagamentoValores = colecaoGuiasPagamentoValores.iterator();
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(7);
				while(itColecaoGuiasPagamentoValores.hasNext()){
					GuiaPagamentoValoresHelper guiaPagamentoValores = (GuiaPagamentoValoresHelper) itColecaoGuiasPagamentoValores.next();
					GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();
					guiaPagamentoGeral.setId(guiaPagamentoValores.getIdGuiaPagamento());
					NegativadorMovimentoRegItem negativadorMovimentoRegItem = new NegativadorMovimentoRegItem();
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setNegativadorMovimentoReg(nmr);
					negativadorMovimentoRegItem.setDocumentoTipo(documentoTipo);
					negativadorMovimentoRegItem.setGuiaPagamentoGeral(guiaPagamentoGeral);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setValorDebito(guiaPagamentoValores.getValorTotalPrestacao());
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

					getControladorUtil().inserir(negativadorMovimentoRegItem);

				}
			}

		}else if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){

			/*
			 * Linha 01 - Gerar Registro de tipo Detalhe Consumidor Nome
			 * Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe Consumidor Nome
			 */

			sb = this.gerarRegistroTipoDetalheConsumidorNomeSPC_BOA_VISTA(quantidadeRegistro, cliente);

			nrt = new NegativadorRegistroTipo();
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_NOME_BOA_VISTA);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(sb.toString());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			repositorioUtil.inserir(nmr);

			/*
			 * Linha 02 - Gerar Registro de tipo Detalhe Consumidor Endereço
			 * Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe Consumidor Endereço
			 */

			sb = this.gerarRegistroTipoDetalheConsumidorEnderecoSPC_BOA_VISTA(quantidadeRegistro, dadosNegativacaoPorImovelHelper, cliente);

			nrt = new NegativadorRegistroTipo();
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_ENDERECO_BOA_VISTA);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(sb.toString());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			repositorioUtil.inserir(nmr);

			/*
			 * Linha 03 - Gerar Registro de tipo Detalhe Ocorrencia SPC
			 * Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe Ocorrência SPC
			 */

			sb = this.gerarRegistroTipoDetalheOcorrenciaSPC_BOA_VISTA(quantidadeRegistro, dadosNegativacaoPorImovelHelper, cliente,
							valorTotalImovel,
							nContrato, ConstantesAplicacao.get("operacao_inclusao"));

			int idLocalidade = imovel.getLocalidade().getId();

			int idQuadra = imovel.getQuadra().getId();

			int stComercialCD = imovel.getQuadra().getSetorComercial().getCodigo();

			int numeroQuadra = imovel.getQuadra().getNumeroQuadra();

			int iper_id = imovel.getImovelPerfil().getId();

			Categoria categoria = null;
			categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(imovel.getId());

			Integer idDebitoSituacao = CobrancaDebitoSituacao.PENDENTE;
			CobrancaDebitoSituacao cobDebSit = new CobrancaDebitoSituacao();
			cobDebSit.setId(idDebitoSituacao);

			Localidade localidade = new Localidade();
			localidade.setId(idLocalidade);

			Quadra quadra = new Quadra();
			quadra.setId(idQuadra);

			ImovelPerfil imovelPerfil = new ImovelPerfil();
			imovelPerfil.setId(iper_id);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_OCORRENCIA_SPC_BOA_VISTA);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(sb.toString());
			nmr.setValorDebito(valorTotalImovel);
			nmr.setCobrancaDebitoSituacao(cobDebSit);
			nmr.setImovel(imovel);
			nmr.setLocalidade(localidade);
			nmr.setQuadra(quadra);
			nmr.setCodigoSetorComercial(stComercialCD);
			nmr.setNumeroQuadra(numeroQuadra);
			nmr.setImovelPerfil(imovelPerfil);
			nmr.setCliente(cliente);
			nmr.setCategoria(categoria);
			nmr.setNumeroCpf(cliente.getCpf());
			nmr.setNumeroCnpj(cliente.getCnpj());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			nmr.setNumeroContrato(imovel.getId().toString());
			nmr.setDataSituacaoDebito(new Date());

			quantidadeRegistro = quantidadeRegistro + 1;

			// adicionado por Vivianne Sousa - 21/08/2009 - analista:Fátima Sampaio
			// CBST_ID da tabela COBRANCA_SITUACAO com o valor correspondente a “EM ANALISE PARA
			// NEGATIVACAO”
			CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
			cobrancaSituacao.setId(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA);
			nmr.setCobrancaSituacao(cobrancaSituacao);

			Integer idDetalheReg = (Integer) repositorioUtil.inserir(nmr);

			nmr.setId(idDetalheReg);

			/*
			 * 1.5 Para cada item da lista de contas e guias de pagamento do imovel, gera os itens
			 * do
			 * registro do movimento da negativação correspondente ao registro tipo Detalhe SPC
			 */
			Collection<ContaValoresHelper> colecaoContasValores = null;
			colecaoContasValores = dadosNegativacaoPorImovelHelper.getColecaoConta();

			// varre lista de contas de pagamento
			if(colecaoContasValores != null){
				Iterator itColecaoContasValores = colecaoContasValores.iterator();
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(DocumentoTipo.CONTA);
				while(itColecaoContasValores.hasNext()){
					ContaValoresHelper contaValores = (ContaValoresHelper) itColecaoContasValores.next();
					ContaGeral contaGeral = new ContaGeral();
					contaGeral.setConta(contaValores.getConta());
					contaGeral.setId(contaValores.getConta().getId());
					NegativadorMovimentoRegItem negativadorMovimentoRegItem = new NegativadorMovimentoRegItem();
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setNegativadorMovimentoReg(nmr);
					negativadorMovimentoRegItem.setDocumentoTipo(documentoTipo);
					negativadorMovimentoRegItem.setContaGeral(contaGeral);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);

					negativadorMovimentoRegItem.setValorDebito(contaValores.getConta().getValorTotal());

					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

					getControladorUtil().inserir(negativadorMovimentoRegItem);

				}
			}

			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = null;
			colecaoGuiasPagamentoValores = dadosNegativacaoPorImovelHelper.getColecaoGuias();

			// varre lista de guias de pagamento
			if(colecaoGuiasPagamentoValores != null){
				Iterator itColecaoGuiasPagamentoValores = colecaoGuiasPagamentoValores.iterator();
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(DocumentoTipo.GUIA_PAGAMENTO);
				while(itColecaoGuiasPagamentoValores.hasNext()){
					GuiaPagamentoValoresHelper guiaPagamentoValores = (GuiaPagamentoValoresHelper) itColecaoGuiasPagamentoValores.next();
					GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();
					guiaPagamentoGeral.setId(guiaPagamentoValores.getIdGuiaPagamento());
					NegativadorMovimentoRegItem negativadorMovimentoRegItem = new NegativadorMovimentoRegItem();
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setNegativadorMovimentoReg(nmr);
					negativadorMovimentoRegItem.setDocumentoTipo(documentoTipo);
					negativadorMovimentoRegItem.setGuiaPagamentoGeral(guiaPagamentoGeral);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setValorDebito(guiaPagamentoValores.getValorTotalPrestacao());
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

					getControladorUtil().inserir(negativadorMovimentoRegItem);

				}
			}

		}else if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
			// [SB0009]

			// 1.1 Detalhe do consumidor
			quantidadeRegistro = quantidadeRegistro + 1;
			sb = this.geraRegistroTipoDetalheConsumidorSPCBrasil(quantidadeRegistro, cliente);

			nrt = new NegativadorRegistroTipo();
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_BR);

			/*
			 * 1.2 Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe Consumidor
			 */
			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(sb.toString());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			RepositorioUtilHBM.getInstancia().inserir(nmr);

			// 1.3 Detalhe do spc
			quantidadeRegistro = quantidadeRegistro + 1;
			String numeroRegistroString = quantidadeRegistro + "";

			char[] registroInclusao = this.geraRegistroTipoDetalheSPCBrasilInclusao(idImovel, valorTotalImovel,
							obterDebitoImovelOuClienteHelper, dadosNegativacaoPorImovelHelper, cliente, numeroRegistroString);

			int idLocalidade = imovel.getLocalidade().getId();
			int idQuadra = imovel.getQuadra().getId();
			int stComercialCD = imovel.getQuadra().getSetorComercial().getCodigo();
			int numeroQuadra = imovel.getQuadra().getNumeroQuadra();
			int iper_id = imovel.getImovelPerfil().getId();

			Categoria categoria = null;
			categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(imovel.getId());

			/*
			 * 1.4 Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe SPC
			 */
			Integer idDebitoSituacao = CobrancaDebitoSituacao.PENDENTE;
			CobrancaDebitoSituacao cobDebSit = new CobrancaDebitoSituacao();
			cobDebSit.setId(idDebitoSituacao);
			Localidade localidade = new Localidade(idLocalidade);
			Quadra quadra = new Quadra(idQuadra);
			ImovelPerfil imovelPerfil = new ImovelPerfil();
			imovelPerfil.setId(iper_id);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nrt.setId(NegativadorRegistroTipo.ID_SPC_DETALHE_SPC_BR);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(new String(registroInclusao));
			nmr.setValorDebito(valorTotalImovel);
			nmr.setCobrancaDebitoSituacao(cobDebSit);
			nmr.setImovel(imovel);
			nmr.setLocalidade(localidade);
			nmr.setQuadra(quadra);
			nmr.setCodigoSetorComercial(stComercialCD);
			nmr.setNumeroQuadra(numeroQuadra);
			nmr.setImovelPerfil(imovelPerfil);
			nmr.setCliente(cliente);
			nmr.setCategoria(categoria);
			nmr.setNumeroCpf(cliente.getCpf());
			nmr.setNumeroCnpj(cliente.getCnpj());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);

			// adicionado por Vivianne Sousa - 21/08/2009 - analista:Fátima Sampaio
			// CBST_ID da tabela COBRANCA_SITUACAO com o valor correspondente a “EM ANALISE PARA
			// NEGATIVACAO”
			CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
			cobrancaSituacao.setId(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL);
			nmr.setCobrancaSituacao(cobrancaSituacao);

			Integer idDetalheReg = (Integer) RepositorioUtilHBM.getInstancia().inserir(nmr);

			nmr.setId(idDetalheReg);

			/*
			 * 1.5 Para cada item da lista de contas e guias de pagamento do imovel, gera os itens
			 * do
			 * registro do movimento da negativação correspondente ao registro tipo Detalhe SPC
			 */
			Collection<ContaValoresHelper> colecaoContasValores = null;
			colecaoContasValores = dadosNegativacaoPorImovelHelper.getColecaoConta();

			// varre lista de contas de pagamento
			if(colecaoContasValores != null){
				Iterator itColecaoContasValores = colecaoContasValores.iterator();
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(1);
				while(itColecaoContasValores.hasNext()){
					ContaValoresHelper contaValores = (ContaValoresHelper) itColecaoContasValores.next();
					ContaGeral contaGeral = new ContaGeral();
					contaGeral.setConta(contaValores.getConta());
					contaGeral.setId(contaValores.getConta().getId());
					NegativadorMovimentoRegItem negativadorMovimentoRegItem = new NegativadorMovimentoRegItem();
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setNegativadorMovimentoReg(nmr);
					negativadorMovimentoRegItem.setDocumentoTipo(documentoTipo);
					negativadorMovimentoRegItem.setContaGeral(contaGeral);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setValorDebito(contaValores.getValorTotalConta());
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

					getControladorUtil().inserir(negativadorMovimentoRegItem);

				}
			}

			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = null;
			colecaoGuiasPagamentoValores = dadosNegativacaoPorImovelHelper.getColecaoGuias();

			// varre lista de guias de pagamento
			if(colecaoGuiasPagamentoValores != null){
				Iterator itColecaoGuiasPagamentoValores = colecaoGuiasPagamentoValores.iterator();
				DocumentoTipo documentoTipo = new DocumentoTipo();
				documentoTipo.setId(7);
				while(itColecaoGuiasPagamentoValores.hasNext()){
					GuiaPagamentoValoresHelper guiaPagamentoValores = (GuiaPagamentoValoresHelper) itColecaoGuiasPagamentoValores.next();
					GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();
					guiaPagamentoGeral.setId(guiaPagamentoValores.getIdGuiaPagamento());
					NegativadorMovimentoRegItem negativadorMovimentoRegItem = new NegativadorMovimentoRegItem();
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setNegativadorMovimentoReg(nmr);
					negativadorMovimentoRegItem.setDocumentoTipo(documentoTipo);
					negativadorMovimentoRegItem.setGuiaPagamentoGeral(guiaPagamentoGeral);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobDebSit);
					negativadorMovimentoRegItem.setValorDebito(guiaPagamentoValores.getValorTotalPrestacao());
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

					getControladorUtil().inserir(negativadorMovimentoRegItem);

				}
			}

		}else if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SERASA)){
			// 2.0

			// 2.1 Detalhe SERASA
			quantidadeRegistro = quantidadeRegistro + 1;

			StringBuilder registroDetalheConsumidor = this.geraRegistroTipoDetalheSERASA(quantidadeRegistro, valorTotalImovel,
							obterDebitoImovelOuClienteHelper, imovel, dadosNegativacaoPorImovelHelper, cliente);

			int idLocalidade = imovel.getLocalidade().getId();
			int idQuadra = imovel.getQuadra().getId();
			int stComercialCD = imovel.getQuadra().getSetorComercial().getCodigo();
			int numeroQuadra = imovel.getQuadra().getNumeroQuadra();
			int iper_id = imovel.getImovelPerfil().getId();
			Categoria categoria = null;
			categoria = this.getControladorImovel().obterPrincipalCategoriaImovel(imovel.getId());

			int idDebitoSituacao = CobrancaDebitoSituacao.PENDENTE;
			/*
			 * 2.2 Gera o registro do movimento da negativaçao correspondente ao
			 * registro do tipo Detalhe SERASA
			 */
			nrt = new NegativadorRegistroTipo();
			nrt.setId(NegativadorRegistroTipo.ID_SERASA_DETALHE);
			CobrancaDebitoSituacao cobDebSit = new CobrancaDebitoSituacao();
			cobDebSit.setId(idDebitoSituacao);
			Localidade localidade = new Localidade(idLocalidade);
			Quadra quadra = new Quadra(idQuadra);
			ImovelPerfil imovelPerfil = new ImovelPerfil();
			imovelPerfil.setId(iper_id);

			nmr = new NegativadorMovimentoReg();
			nmr.setNegativadorMovimento(nm);
			nmr.setNegativadorRegistroTipo(nrt);
			nmr.setConteudoRegistro(new String(registroDetalheConsumidor));
			nmr.setValorDebito(valorTotalImovel);
			nmr.setCobrancaDebitoSituacao(cobDebSit);
			nmr.setImovel(imovel);
			nmr.setLocalidade(localidade);
			nmr.setQuadra(quadra);
			nmr.setCodigoSetorComercial(stComercialCD);
			nmr.setNumeroQuadra(numeroQuadra);
			nmr.setImovelPerfil(imovelPerfil);
			nmr.setCliente(cliente);
			nmr.setCategoria(categoria);
			nmr.setNumeroCpf(cliente.getCpf());
			nmr.setNumeroCnpj(cliente.getCnpj());
			nmr.setUltimaAlteracao(new Date());
			nmr.setIndicadorSituacaoDefinitiva((short) 2);
			nmr.setNumeroRegistro(quantidadeRegistro);
			nmr.setNumeroContrato(imovel.getId().toString());
			
			
			// adicionado por Vivianne Sousa - 21/08/2009 - analista:Fátima Sampaio
			// CBST_ID da tabela COBRANCA_SITUACAO com o valor correspondente a “EM ANALISE PARA
			// NEGATIVACAO”
			CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
			cobrancaSituacao.setId(CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA);
			nmr.setCobrancaSituacao(cobrancaSituacao);
			Integer idDetalheReg = (Integer) repositorioUtil.inserir(nmr);

			nmr.setId(idDetalheReg);

			// 2.3 - Para cada item da lista de contas e guias de pagamento do imovel...
			Collection<ContaValoresHelper> colecaoContasValores = null;
			// if(tipoComando.equals(ConstantesSistema.TIPO_COMANDO_POR_MATRICULA_IMOVEIS)){
			colecaoContasValores = dadosNegativacaoPorImovelHelper.getColecaoConta();
			// }else if(tipoComando.equals(ConstantesSistema.TIPO_COMANDO_POR_CRITERIO)){
			// colecaoContasValores = obterDebitoImovelOuClienteHelper.getColecaoContasValores();
			// }

			// varre lista de contas de pagamento
			if(colecaoContasValores != null){
				Iterator itColecaoContasValores = colecaoContasValores.iterator();
				while(itColecaoContasValores.hasNext()){
					ContaValoresHelper contaValores = (ContaValoresHelper) itColecaoContasValores.next();
					this.repositorioSpcSerasa.geraRegistroNegativacaoMovimentoRegItem(idDebitoSituacao, contaValores.getValorTotalConta(),
									idDetalheReg, 1, null, contaValores.getConta().getId());
				}
			}

			Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = null;
			colecaoGuiasPagamentoValores = dadosNegativacaoPorImovelHelper.getColecaoGuias();

			// varre lista de guias de pagamento
			if(colecaoGuiasPagamentoValores != null){
				Iterator itColecaoGuiasPagamentoValores = colecaoGuiasPagamentoValores.iterator();
				while(itColecaoGuiasPagamentoValores.hasNext()){
					GuiaPagamentoValoresHelper guiaPagamentoValores = (GuiaPagamentoValoresHelper) itColecaoGuiasPagamentoValores.next();
					this.repositorioSpcSerasa.geraRegistroNegativacaoMovimentoRegItem(idDebitoSituacao, guiaPagamentoValores
									.getValorTotalPrestacao(), idDetalheReg, 7, guiaPagamentoValores.getIdGuiaPagamento(), null);
				}
			}
		}

		// 3.0
		// 3.1 Inserir imóvel negativação
		NegativacaoImovei ni = new NegativacaoImovei();
		// [UC1102 Emitir Arquivo de movimento de inclusão de negativação
		// [SB0004] – Gerar Registro Tipo Detalhe
		// Alterado em 15/08/2011
		// ni.setNegativacaoComando(nComando);
		ni.setCobrancaAcaoAtividadeCronograma(cobrancaAcaoAtividadeCronograma);
		ni.setCobrancaAcaoAtividadeComando(cobrancaAcaoAtividadeComando);
		ni.setImovel(imovel);
		ni.setUltimaAlteracao(new Date());
		ni.setIndicadorExcluido((short) 2);
		repositorioUtil.inserir(ni);

		// escolher cobranca situacao
		Integer cobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
		if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SERASA)){
			cobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
		}else if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
			cobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP;
		}else if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
			cobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL;
		}else if(Integer.valueOf(n.getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
			cobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA;
		}

		// 3.2 Atualizar situação de cobrança do imóvel
		// getControladorImovel().atualizarSituacaoCobrancaImovel(cobrancaSituacao, idImovel);

		// 3.3 Inserir nova situação de cobrança do imóvel
		ImovelCobrancaSituacao imovSitCob = new ImovelCobrancaSituacao();
		imovSitCob.setImovel(imovel);
		imovSitCob.setDataImplantacaoCobranca(new Date());
		CobrancaSituacao cobSit = new CobrancaSituacao();
		cobSit.setId(cobrancaSituacao);
		imovSitCob.setCobrancaSituacao(cobSit);
		imovSitCob.setCliente(cliente);
		imovSitCob.setUltimaAlteracao(new Date());
		repositorioUtil.inserir(imovSitCob);

		return quantidadeRegistro;
	}

	private Object[] processarArquivoMovimentoRetornoSPC(StringBuilder stringBuilderTxt, Negativador negativador,
					NegativadorMovimento negativadorMovimento) throws ControladorException{

		Object[] retorno = new Object[1];
		NegativadorMovimentoReg negativadorMovimentoReg = null;
		NegativadorMovimentoReg negativadorMovimentoRegVerificado = null;
		Collection collNegativadorMovimentoReg = new ArrayList();

		// ----------------------------------------------------------------
		// [SB0001] - Validar Arquivo de Movimento de Retorno do SPC
		// ----------------------------------------------------------------
		Integer numeroRegistro = 0;
		int countRegistro = 0;

		String tipoRegistro = "";
		boolean isHeaderOuTrailler = false;

		StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

		while(stk.hasMoreTokens()){
			// ---------------------------------------------------
			String registro = stk.nextToken();
			countRegistro = countRegistro + 1;

			tipoRegistro = getConteudo(9, 10, registro.toCharArray());
			if(tipoRegistro.equals(ConstantesAplicacao.get("constante_header"))
							|| tipoRegistro.equals(ConstantesAplicacao.get("constante_trailler"))){
				isHeaderOuTrailler = true;
			}else{
				isHeaderOuTrailler = false;
			}

			try{
				negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(negativadorMovimento, numeroRegistro);
				if(negativadorMovimentoReg != null){
					negativadorMovimentoRegVerificado = this.atualizarRegistroEnvioSPCSaoPaulo(negativador, registro,
									negativadorMovimentoReg, isHeaderOuTrailler);
					collNegativadorMovimentoReg.add(negativadorMovimentoRegVerificado);
				}

			}catch(ErroRepositorioException e1){
				e1.printStackTrace();
			}

			numeroRegistro = numeroRegistro + 1;
			System.out.println(numeroRegistro);

		}

		if(collNegativadorMovimentoReg.size() > 0){
			getControladorBatch().atualizarColecaoObjetoParaBatch(collNegativadorMovimentoReg);
		}

		retorno[0] = collNegativadorMovimentoReg;
		return retorno;
		// --------------------------------------------------------------------------------------------
	}

	/**
	 * Registrar Movimento de Retorno dos Negativadores [UC0672] Registrar
	 * Movimento de Retorno dos Negativadores
	 * [Atualizar Registro Envio SPC]
	 * verificarRegistroSPC_01
	 * verificarRegistroSPC_02
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private NegativadorMovimentoReg atualizarRegistroEnvioSPCSaoPaulo(Negativador negativador, String registro,
					NegativadorMovimentoReg negativadorMovimentoReg, boolean isHeaderOuTrailler) throws ControladorException,
					ErroRepositorioException{

		if(!isHeaderOuTrailler){
			if(registro != null && !"".equals(registro)){
				String campoCodigoRetorno = getConteudo(231, 20, registro.toCharArray()).trim();
				negativadorMovimentoReg = this.atualizarCodigoRetornoRegistro(campoCodigoRetorno, negativador, negativadorMovimentoReg);

			}

			String sequencialArquivo = "";
			String sequencialRegistro = "";
			// Validação do sequencial do registro
			sequencialArquivo = getConteudo(594, 7, registro.toCharArray()).trim();

			sequencialRegistro = Integer.toString(negativadorMovimentoReg.getNumeroRegistro());
			sequencialRegistro = Util.adicionarZerosEsqueda(6, sequencialRegistro);

			if(!sequencialArquivo.equals(sequencialRegistro)){
				throw new ControladorException("atencao.conteudo_registro_nao_corresponde_ao_enviado", null, sequencialArquivo);
			}

			String conteudoRegistro = negativadorMovimentoReg.getConteudoRegistro();
			// CPF/CNPJ
			String cpfCnpjArquivo = getConteudo(19, 20, registro.toCharArray()).trim();
			String cpfCnpjRegistro = getConteudo(19, 20, conteudoRegistro.toCharArray());

			if(!cpfCnpjArquivo.equals(cpfCnpjRegistro)){
				throw new ControladorException("atencao.conteudo_registro_nao_corresponde_ao_enviado", null, sequencialArquivo);
			}
		}else{
			negativadorMovimentoReg.setIndicadorAceito(ConstantesSistema.ACEITO);
		}

		return negativadorMovimentoReg;
	}

	/**
	 * @param nmr
	 * @param nm
	 * @param sNumeroRegistro
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	private void gerarNegMovRegDetalheConsumidor(NegativadorMovimentoReg nmr, NegativadorMovimento nm, String sNumeroRegistro)
					throws ErroRepositorioException, ControladorException{

		NegativadorMovimentoReg nmrDetalheConsumidor = repositorioSpcSerasa.pesquisarRegistroTipoConsumidor(nmr.getNumeroRegistro() - 1,
						nmr.getNegativadorMovimento().getId());

		char[] registro = new char[340];
		for(int i = 0; i < registro.length; i++){
			registro[i] = ' ';
		}
		if(nmrDetalheConsumidor.getConteudoRegistro() != null){
			for(int i = 0; i < nmrDetalheConsumidor.getConteudoRegistro().length(); i++){
				if(i <= registro.length){
					registro[i] = nmrDetalheConsumidor.getConteudoRegistro().toCharArray()[i];
				}
			}
		}

		// h.15
		colocarConteudo(sNumeroRegistro, 335, registro);

		NegativadorMovimentoReg nmrExclusao = new NegativadorMovimentoReg();
		nmrExclusao.setNegativadorMovimento(nm);
		nmrExclusao.setNegativadorMovimentoRegInclusao(nmrDetalheConsumidor);
		nmrExclusao.setNegativadorRegistroTipo(nmrDetalheConsumidor.getNegativadorRegistroTipo());
		nmrExclusao.setConteudoRegistro(new String(registro));
		nmrExclusao.setUltimaAlteracao(new Date());
		nmrExclusao.setUsuario(nmrDetalheConsumidor.getUsuario());
		nmrExclusao.setCodigoExclusaoTipo(nmrDetalheConsumidor.getCodigoExclusaoTipo());
		nmrExclusao.setValorDebito(nmrDetalheConsumidor.getValorDebito());
		nmrExclusao.setCobrancaDebitoSituacao(nmrDetalheConsumidor.getCobrancaDebitoSituacao());

		nmrExclusao.setDataSituacaoDebito(nmrDetalheConsumidor.getDataSituacaoDebito());
		nmrExclusao.setImovel(nmrDetalheConsumidor.getImovel());
		nmrExclusao.setLocalidade(nmrDetalheConsumidor.getLocalidade());
		nmrExclusao.setQuadra(nmrDetalheConsumidor.getQuadra());
		nmrExclusao.setCodigoSetorComercial(nmrDetalheConsumidor.getCodigoSetorComercial());
		nmrExclusao.setNumeroQuadra(nmrDetalheConsumidor.getNumeroQuadra());
		nmrExclusao.setImovelPerfil(nmrDetalheConsumidor.getImovelPerfil());
		nmrExclusao.setCliente(nmrDetalheConsumidor.getCliente());
		nmrExclusao.setCategoria(nmrDetalheConsumidor.getCategoria());
		nmrExclusao.setNumeroCpf(nmrDetalheConsumidor.getNumeroCpf());
		nmrExclusao.setNumeroCnpj(nmrDetalheConsumidor.getNumeroCnpj());
		nmrExclusao.setCpfTipo(nmrDetalheConsumidor.getCpfTipo());
		nmrExclusao.setIndicadorSituacaoDefinitiva(new Short((short) 1));
		nmrExclusao.setNumeroRegistro(new Integer(sNumeroRegistro));
		nmrExclusao.setNumeroContrato(nmrDetalheConsumidor.getNumeroContrato());

		// Inserir registro de exclusão do tipo Detalhe-Consumidor
		RepositorioUtilHBM.getInstancia().inserir(nmrExclusao);

		// Atualiza a exclusão do registro Tipo Detalhe-Consumidor
		nmrDetalheConsumidor.setCodigoExclusaoTipo(1);
		nmrDetalheConsumidor.setUltimaAlteracao(new Date());
		RepositorioUtilHBM.getInstancia().atualizar(nmrDetalheConsumidor);

	}

	/**
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores
	 * [SB0001] - Validar Arquivo de Movimento de Retorno do SPC-BRASIL
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 */
	private Object[] validarArquivoMovimentoRetornoSPCBrasil(StringBuilder stringBuilderTxt, Negativador negativador)
					throws ControladorException{

		Object[] retorno = new Object[2];

		String numeroRegistro = "";

		int countRegistro = 0;
		Collection collRegistrosLidos = new ArrayList();
		NegativadorMovimento negativadorMovimento = null;

		try{
			StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

			while(stk.hasMoreTokens()){
				countRegistro = countRegistro + 1;

				String registro = stk.nextToken();

				// X.01 - Tipo de registro
				String tipoRegistro = getConteudo(1, 2, registro.toCharArray());

				// Verifica Header (Primeira Linha)
				if(countRegistro == 1){
					// 1.
					if(!tipoRegistro.toUpperCase().equals("00")){
						throw new ControladorException("atencao.arquivo.movimento.negativador.sem.header");
					}

					// H.02
					String operacao = getConteudo(3, 7, registro.toCharArray());

					// 3.
					if(!operacao.toUpperCase().equalsIgnoreCase("RETORNO")){
						throw new ControladorException("atencao.operacao_nao_corresponde_retorno");
					}

					// H.09
					String unidadeNegocio = getConteudo(318, 5, registro.toCharArray());
					unidadeNegocio = unidadeNegocio.trim();

					// 4.
					if(!unidadeNegocio.toUpperCase().equalsIgnoreCase("SPC")){
						throw new ControladorException("atencao.movimento_nao_spc_brasil");
					}

					// 5.
					NegativadorContrato negativadorContrato = this.repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador
									.getId());

					String numeroSequencialEnvioBD = negativadorContrato.getNumeroSequencialEnvio() + "";

					String qtdZeros = "";

					int tamanho = 8 - numeroSequencialEnvioBD.length();

					for(int i = 0; i < tamanho; i++){
						qtdZeros = qtdZeros + "0";
					}

					numeroSequencialEnvioBD = qtdZeros + numeroSequencialEnvioBD;

					// H.04
					Integer numeroSequencialArquivo = Util.converterStringParaInteger(getConteudo(18, 8, registro.toCharArray()));

					// 6.
					if(numeroSequencialArquivo > Util.converterStringParaInteger(numeroSequencialEnvioBD)){
						throw new ControladorException("atencao.movimento_fora_sequencia");
					}

					// 7.
					negativadorMovimento = this.repositorioSpcSerasa.getNegativadorMovimento(negativador, numeroSequencialArquivo, false);

					// [FS0011 – Verificar a existência de retorno para o movimento de envio].
					verificarExistenciaRetornoParaMovimentoEnvio(negativadorMovimento);

					// 8.
					// [FS0006 - Verificar a existência do registro de envio]

					// H.12
					numeroRegistro = getConteudo(335, 6, registro.toCharArray());
					Integer numRegistro = Util.converterStringParaInteger(numeroRegistro);

					NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(
									negativadorMovimento, numRegistro);

					if(negativadorMovimentoReg != null && negativadorMovimentoReg.getConteudoRegistro() == null){
						throw new ControladorException("atencao.arquivo_movimento_sem_registros");
					}

					String[] reg = new String[2];
					reg[0] = registro;

					Object[] negativadorMovimentoRegistros = new Object[2];
					negativadorMovimentoRegistros[0] = negativadorMovimentoReg;

					// [FS0012 - Verificar erro de Header/Trailler no arquivo retorno], passando o
					// conteúdo do primeiro registro de movimento.
					verificarErroHeaderTraillerNoArquivoRetorno(negativador, reg);

					// 9.
					// 9. O sistema atualiza os dados do registro header de envio
					// [SB005 - Atualizar Registro de Envio]
					this.atualizarRegistroEnvioSPCBrasil(negativador, reg, negativadorMovimentoRegistros);
				}

				// 10.
				// [FS0003 - Verificar a existência do registro tipo trailler]
				if((stk.hasMoreTokens() == true && tipoRegistro.equals("00")) && countRegistro > 1){
					throw new ControladorException("atencao.arquivo_movimento_nao_possui_trailler");
				}

				if(stk.hasMoreTokens() == false && tipoRegistro.equals("99")){

					String[] reg = new String[2];
					reg[0] = registro;
					/*
					 * [FS0012 - Verificar erro de Header/Trailler no arquivo retorno], passando o
					 * conteúdo do último registro de movimento.
					 */
					verificarErroHeaderTraillerNoArquivoRetorno(negativador, reg);

					// 12.
					// T.02
					String totalRegistros = getConteudo(3, 6, registro.toCharArray());
					int tRegistros = Util.converterStringParaInteger(totalRegistros.trim());

					if(tRegistros != countRegistro){
						throw new ControladorException("atencao.total_registro_do_arquivo_invalido");
					}
				}else if(stk.hasMoreTokens() == false && !tipoRegistro.equals("99")){
					throw new ControladorException("atencao.arquivo_movimento_nao_possui_trailler");
				}

				// 10.
				// [FS0004 - Verificar a existência de registros com tipo inválido]
				if(!tipoRegistro.equals("99") && !tipoRegistro.equals("01") && !tipoRegistro.equals("02") && countRegistro > 1){
					throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_tipos_invalidos");
				}

				// 10.
				// [FS0005 - Verificar a existência de registros com número de sequência inválida]
				String numeroSequencia = getConteudo(335, 6, registro.toCharArray());
				int numSequencia = Util.converterStringParaInteger(numeroSequencia.trim());

				if(numSequencia != countRegistro){
					throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_sequencia_invalida");
				}

				numeroRegistro = getConteudo(335, 6, registro.toCharArray());
				Integer numRegistro = Util.converterStringParaInteger(numeroRegistro);

				NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(
								negativadorMovimento, numRegistro);

				if(negativadorMovimentoReg != null && negativadorMovimentoReg.getConteudoRegistro() == null){
					throw new ControladorException("atencao.arquivo_movimento_sem_registros");
				}

				collRegistrosLidos.add(registro);
			}

			retorno[0] = collRegistrosLidos;
			retorno[1] = negativadorMovimento;

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores
	 * [SB0002] - Atualizar Movimento de Envio do SPC-BRASIL
	 * 
	 * @author Yara Taciane
	 * @throws ErroRepositorioException
	 * @date 10/01/2008
	 */
	private void atualizarMovimentoEnvioSPCBrasil(Object[] registro, Negativador negativador, NegativadorMovimento negativadorMovimento)
					throws ControladorException, ErroRepositorioException{

		Object[] negativadorMovimentoRegistros = new Object[2];

		for(int i = 0; i < registro.length; i++){
			if(registro[i] != null){
				Integer numeroRegistro = Util.converterStringParaInteger(getConteudo(335, 6, registro[i].toString().toCharArray()));

				if(numeroRegistro != null){
					NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(
									negativadorMovimento, numeroRegistro);

					// [FS0006 - Verificar a existência do registro de envio]
					if(negativadorMovimentoReg != null && negativadorMovimentoReg.getConteudoRegistro() == null){
						throw new ControladorException("atencao.arquivo_movimento_sem_registros");
					}

					negativadorMovimentoRegistros[i] = negativadorMovimentoReg;
				}
			}
		}

		// [SB0005 - Atualizar Registro Envio]
		this.atualizarRegistroEnvioSPCBrasil(negativador, registro, negativadorMovimentoRegistros);
	}

	/**
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores
	 * [SB0005] - Atualizar Registro Envio SPC-BRASIL
	 * verificarRegistroSPC_01
	 * verificarRegistroSPC_02
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private void atualizarRegistroEnvioSPCBrasil(Negativador negativador, Object[] registro, Object[] negativadorMovimentoRegistros)
					throws ControladorException, ErroRepositorioException{

		String tipoRegistro = "";
		String sequencialArquivo = "";
		String sequencialRegistro = "";
		boolean isHeader = false;

		NegativadorMovimentoReg negativadorMovimentoReg = null;

		Integer idNegativador = negativador.getId();

		if(Negativador.NEGATIVADOR_SPC_BRASIL.equals(idNegativador)){
			for(int i = 0; i < registro.length; i++){
				if(negativadorMovimentoRegistros[i] != null){
					negativadorMovimentoReg = (NegativadorMovimentoReg) negativadorMovimentoRegistros[i];

					if(registro[i] != null){
						// Quando é Header ele sempre vai levantar exceção
						tipoRegistro = getConteudo(1, 2, registro[i].toString().toCharArray());

						if(!tipoRegistro.equals("00") && !tipoRegistro.equals("99")){
							// Não é possível comparar todo o conteudo do arquivo, pois o SPC
							// altera altera o conteúdo do registro.

							// Validação do sequencial do registro
							sequencialArquivo = getConteudo(335, 6, registro[i].toString().toCharArray());

							sequencialRegistro = Integer.toString(negativadorMovimentoReg.getNumeroRegistro());
							sequencialRegistro = Util.adicionarZerosEsqueda(6, sequencialRegistro);

							if(!sequencialArquivo.equals(sequencialRegistro)){
								throw new ControladorException("atencao.conteudo_registro_nao_corresponde_ao_enviado", null,
												sequencialArquivo);
							}

							String conteudoRegistro = negativadorMovimentoReg.getConteudoRegistro();

							if(tipoRegistro.equals("01")){
								// CPF/CNPJ
								String cpfCnpjArquivo = getConteudo(57, 15, registro[i].toString().toCharArray());
								String cpfCnpjRegistro = getConteudo(57, 15, conteudoRegistro.toCharArray());

								if(!cpfCnpjArquivo.equals(cpfCnpjRegistro)){
									throw new ControladorException("atencao.conteudo_registro_nao_corresponde_ao_enviado", null,
													sequencialArquivo);
								}
							}else if(tipoRegistro.equals("02")){
								// CPF/CNPJ
								String cpfCnpjArquivo = getConteudo(4, 15, registro[i].toString().toCharArray());
								String cpfCnpjRegistro = getConteudo(4, 15, conteudoRegistro.toCharArray());

								// Contrato
								String contratoArquivo = getConteudo(50, 30, registro[i].toString().toCharArray());
								String contratoRegistro = getConteudo(50, 30, conteudoRegistro.toCharArray());

								if(!cpfCnpjArquivo.equals(cpfCnpjRegistro) || !contratoArquivo.equals(contratoRegistro)){
									throw new ControladorException("atencao.conteudo_registro_nao_corresponde_ao_enviado", null,
													sequencialArquivo);
								}
							}
						}else{
							isHeader = true;
						}
					}
				}
			}

			if(isHeader){
				NegativadorMovimentoReg negativadorMovimentoReg_01 = (NegativadorMovimentoReg) negativadorMovimentoRegistros[0];
				verificarRegistroSPCBrasil01(registro, negativador, negativadorMovimentoReg_01);
			}else{
				NegativadorMovimentoReg negativadorMovimentoReg_01 = (NegativadorMovimentoReg) negativadorMovimentoRegistros[0];
				NegativadorMovimentoReg negativadorMovimentoReg_02 = (NegativadorMovimentoReg) negativadorMovimentoRegistros[1];

				verificarRegistroSPCBrasil01(registro, negativador, negativadorMovimentoReg_01);
				verificarRegistroSPCBrasil02(registro, negativador, negativadorMovimentoReg_02);
			}
		}
	}

	/**
	 * Método que Exclui a negativacao de um imovel
	 * [UC0675] Excluir Negativação InLine
	 * [Fluxo principal] 8.0
	 * 
	 * @author Genival Barbosa
	 * @date 19/10/2011
	 * @throws ControladorException
	 */
	public Integer excluirNegativacaoOnLineEmLote(Collection collImovel, Integer idNegativador, Usuario usuarioLogado)
					throws ControladorException{

		// 9. O sistema atribui o valor zero à Quantidade de Imóveis Excluídos.
		Integer quantidadeImoveisExcluidos = 0;
		try{
			FiltroCobrancaDebitoSituacao filtroCobrancaDebitoSituacao = new FiltroCobrancaDebitoSituacao();
			filtroCobrancaDebitoSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaDebitoSituacao.ID,
							CobrancaDebitoSituacao.EXCLUIDO));
			CobrancaDebitoSituacao cobrancaDebitoSituacaoExcluido = (CobrancaDebitoSituacao) Util
							.retonarObjetoDeColecao(getControladorUtil().pesquisar(filtroCobrancaDebitoSituacao,
											CobrancaDebitoSituacao.class.getName()));
			// 10. Para cada imóvel da lista de imóveis, o sistema indica a exclusão da negativação
			// de acordo com as seguintes regras:
			for(Object objeto : collImovel){
				Integer matric = (Integer) objeto;

				System.out.println("------------------> Imovel = " + matric);

				// 10.1. Caso exista negativação a ser excluída para o imóvel
				Collection<NegativadorMovimentoReg> collNegMovReg = repositorioSpcSerasa.pesquisarNegativacaoASerExcluidaImovel(matric,
								idNegativador);
				for(NegativadorMovimentoReg negativadorMovimentoReg : collNegMovReg){
					// 10.1.1. O sistema obtém os itens do débito da negativação
					Collection<NegativadorMovimentoRegItem> collNegativadorMovimentoRegItem = repositorioSpcSerasa
									.pesquisarNegativadorMovimentoRegItemPorNegMovimentoRegCobrancaSituacao(
													negativadorMovimentoReg.getId(), CobrancaDebitoSituacao.PENDENTE);
					// [FS0013] – Verificar existência de itens do débito da negativação
					if(collNegativadorMovimentoRegItem != null && collNegativadorMovimentoRegItem.size() > 0){
						quantidadeImoveisExcluidos++;
						for(NegativadorMovimentoRegItem negativadorMovimentoRegItem : collNegativadorMovimentoRegItem){
							// 10.1.2. Para cada item da negativação selecionado, o sistema atualiza
							// a situação de débito do item
							// – atualiza a tabela NEGATIVADOR_MOVIMENTO_REG_ITEM com os seguintes
							// valores
							negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobrancaDebitoSituacaoExcluido);
							negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
							negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

							getControladorUtil().atualizar(negativadorMovimentoRegItem);
						}
						// 10.1.3. O sistema indica a atualização da situação de débito do item na
						// negativação –
						// atualiza a tabela NEGATIVADOR_MOVIMENTO_REG com os seguintes valores
						negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.SIM);
						negativadorMovimentoReg.setUltimaAlteracao(new Date());
						getControladorUtil().atualizar(negativadorMovimentoReg);
					}
				}
			}
			System.out.println("------------>FIM<-----------------");

		}catch(ErroRepositorioException e){

			e.printStackTrace();
			throw new ControladorException("erro.sistema", e);
		}
		return quantidadeImoveisExcluidos;
	}

	public Boolean verificaNegativacaoImovel(Integer idImovel) throws ControladorException{

		try{
			return repositorioSpcSerasa.verificaNegativacaoImovel(idImovel);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);

		}
	}

	/**
	 * [FS0019] - Verificar existência de negativação para o cliente-imóvel
	 * Caso o cliente do imóvel esteja em processo de negativação (existe ocorrência na tabela
	 * NEGATIVADOR_MOVIMENTO_REG com IMOV_ID=Id do imóvel informado e CLIE_ID=Id do cliente
	 * selecionado para remoção e NMRG_ICACEITO com o valor 1 ou nulo e NMRG_CDEXCLUSAOTIPO com o
	 * valor nulo e NMRG_IDREGINCLUSAO com o valor nulo), exibir a mensagem “ATENÇÃO: O cliente
	 * <<CLIE_NMCLIENTE com CLIE_ID=Id do cliente selecionado para remoção>>, vinculado ao imóvel
	 * <<IMOV_ID>>.
	 * 
	 * @author Isaac Silva
	 * @date 03/08/2011
	 * @param idImovel
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarExistenciaNegativacaClienteImovel(Integer idImovel, Integer idCliente) throws ControladorException{

		try{

			return repositorioSpcSerasa.verificarExistenciaNegativacaClienteImovel(idImovel, idCliente);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0937] Obter Itens de Negativação Associados à Conta
	 * 
	 * @author Vivianne Sousa
	 * @author isilva
	 * @date 10/09/2009
	 * @date 04/08/2011
	 * @param idImovel
	 * @param referencia
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> obterItensNegativacaoAssociadosAConta(Integer idImovel, Integer referencia,
					Boolean... ignorarSituacaoDefinitiva) throws ControladorException{

		try{

			Collection colecaoRetorno;
			Collection colecaoNmriAssociadosAContaHistorico;
			if(ignorarSituacaoDefinitiva != null && ignorarSituacaoDefinitiva.length > 0){
				colecaoRetorno = repositorioSpcSerasa.obterNegativadorMovimentoRegItemAssociadosAConta(idImovel, referencia,
								ignorarSituacaoDefinitiva[0]);
				colecaoNmriAssociadosAContaHistorico = repositorioSpcSerasa.obterNegativadorMovimentoRegItemAssociadosAContaHistorico(
								idImovel, referencia, ignorarSituacaoDefinitiva[0]);
			}else{
				colecaoRetorno = repositorioSpcSerasa.obterNegativadorMovimentoRegItemAssociadosAConta(idImovel, referencia, Boolean.FALSE);
				colecaoNmriAssociadosAContaHistorico = repositorioSpcSerasa.obterNegativadorMovimentoRegItemAssociadosAContaHistorico(
								idImovel, referencia, Boolean.FALSE);
			}

			if(colecaoNmriAssociadosAContaHistorico != null && !colecaoNmriAssociadosAContaHistorico.isEmpty()){
				if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){
					colecaoRetorno.addAll(colecaoNmriAssociadosAContaHistorico);
				}else{
					colecaoRetorno = colecaoNmriAssociadosAContaHistorico;
				}
			}

			return colecaoRetorno;

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UCXXXX] Atualizar o indicadorSituacaoDefinitiva dos Itens de Negativação Associados à Conta
	 * (Usado no desfazer Parcelamento e no Estornar pagamento)
	 * 
	 * @author Genival
	 * @date 03/11/2011
	 * @param idImovel
	 * @param referencia
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> atualizarItensNegativacaoAssociadosAContaIndicadorSitDefinit(Integer idImovel, Integer referencia)
					throws ControladorException{

		try{

			Collection colecaoRetorno = repositorioSpcSerasa.obterNegativadorMovimentoRegItemAssociadosAContaSitDefinit(idImovel,
							referencia);
			Collection colecaoNmriAssociadosAContaHistorico = repositorioSpcSerasa
							.obterNegativadorMovimentoRegItemAssociadosAContaHistoricoSitDefinit(idImovel, referencia);

			if(colecaoNmriAssociadosAContaHistorico != null && !colecaoNmriAssociadosAContaHistorico.isEmpty()){
				if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){
					colecaoRetorno.addAll(colecaoNmriAssociadosAContaHistorico);
				}else{
					colecaoRetorno = colecaoNmriAssociadosAContaHistorico;
				}
			}

			if(colecaoRetorno != null && colecaoRetorno.size() > 0){

				Collection<NegativadorMovimentoRegItem> collRegItens = repositorioSpcSerasa
								.pesquisarNegativadorMovimentoRegItensPorIds(colecaoRetorno);

				for(NegativadorMovimentoRegItem regItens : collRegItens){

					regItens.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					getControladorUtil().atualizar(regItens);

					regItens.getNegativadorMovimentoReg().setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					getControladorUtil().atualizar(regItens.getNegativadorMovimentoReg());

				}
			}
			return colecaoRetorno;

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * @author Isaac Silva
	 * @date 09/08/2011
	 * @param listaIdentificadoresNMRI
	 * @return
	 * @throws ControladorException
	 */
	public Collection<NegativadorMovimentoRegItem> pesquisarNegativadorMovimentoRegItensPorIds(List<Integer> listaIdentificadoresNMRI)
					throws ControladorException{

		Collection<NegativadorMovimentoRegItem> colecaoRetorno = new ArrayList<NegativadorMovimentoRegItem>();

		try{

			if(listaIdentificadoresNMRI != null && !listaIdentificadoresNMRI.isEmpty()){
				int qtdBlocosConsultas = Util.dividirArredondarResultado(listaIdentificadoresNMRI.size(),
								ConstantesSistema.QUANTIDADE_LIMITE_ITEM_CONSULTA, BigDecimal.ROUND_UP);
				for(int i = 0; i < qtdBlocosConsultas; i++){

					List<Integer> blocoIds = new ArrayList<Integer>();
					blocoIds.addAll(Util.obterSubListParaPaginacao(listaIdentificadoresNMRI, i,
									ConstantesSistema.QUANTIDADE_LIMITE_ITEM_CONSULTA));

					Collection<NegativadorMovimentoRegItem> colecaoNegativadorMovimentoRegItem = this.repositorioSpcSerasa
									.pesquisarNegativadorMovimentoRegItensPorIds(blocoIds);

					if(colecaoNegativadorMovimentoRegItem != null && !colecaoNegativadorMovimentoRegItem.isEmpty()){
						colecaoRetorno.addAll(colecaoNegativadorMovimentoRegItem);
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
	 * [UC1104] Efetuar Contra Ação
	 * [SB0004] – Atualiza Item do item do débito da negativação
	 * 
	 * @author Isaac Silva
	 * @date 08/08/2011
	 * @param conta
	 * @throws ControladorException
	 */
	public void atualizaItemDoItemDoDebitoDaNegativacao(ContaHistorico conta) throws ControladorException{

		try{

			/**
			 * 1. [UC0937 – Obter Itens de Negativação Associados à Conta] passando o identificador
			 * do imóvel (IMOV_ID da tabela CONTA) e a referência (CNTA_AMREFERENCIACONTA da tabela
			 * CONTA).
			 */
			Collection<Integer> colecaoIds = this.obterItensNegativacaoAssociadosAConta(conta.getImovel().getId(), conta
							.getAnoMesReferenciaConta());

			Collection<NegativadorMovimentoRegItem> colecaoRetorno = this
							.pesquisarNegativadorMovimentoRegItensPorIds((List<Integer>) colecaoIds);

			if(!Util.isVazioOrNulo(colecaoRetorno)){

				/**
				 * 2. Pra cada item da negativação obtido na lista:
				 */
				for(NegativadorMovimentoRegItem nmri : colecaoRetorno){

					if(nmri.getNegativadorMovimentoReg().getCodigoExclusaoTipo() == null){

						/**
						 * 2.1. Caso a negativação não esteja excluída (NMRG_CDEXCLUSAOTIPO da
						 * tabela NEGATIVADOR_MOVIMENTO_REG com o valor nulo para NMRG_ID=NMRG_ID da
						 * tabela NEGATIVADOR_MOVIMENTO_REG_ITEM com NMRI_ID=Id do item de
						 * negativação), atualiza o item da negativação (tabela
						 * NEGATIVADOR_MOVIMENTO_REG_ITEM) com os seguintes valores:
						 */
						nmri.setCobrancaDebitoSituacao(new CobrancaDebitoSituacao(CobrancaDebitoSituacao.PAGO));
						nmri.setDataSituacaoDebito(new Date());
						nmri.setUltimaAlteracao(new Date());
						nmri.setIndicadorSituacaoDefinitiva(ConstantesSistema.SIM);
						nmri.setValorPago(conta.getValorTotal());

					}else{

						/**
						 * 2.2. Caso contrário, atualiza o item da negativação (tabela
						 * NEGATIVADOR_MOVIMENTO_REG_ITEM) com os seguintes valores:
						 */
						nmri.setCobrancaDebitoSituacaoAposExclusao(new CobrancaDebitoSituacao(CobrancaDebitoSituacao.PAGO));
						nmri.setDataSituacaoDebitoAposExclusao(new Date());
						nmri.setValorPago(conta.getValorTotal());
						nmri.setUltimaAlteracao(new Date());
						nmri.setIndicadorSituacaoDefinitiva(ConstantesSistema.SIM);
					}

					repositorioUtil.atualizar(nmri);

					/**
					 * 2.3. Atualiza a tabela NEGATIVADOR_MOVIMENTO_REG com os seguintes valores
					 * (tabela NEGATIVADOR_MOVIMENTO_REG com NMRG_ID=NMRG_ID da tabela
					 * NEGATIVADOR_MOVIMENTO_REG_item):
					 */
					NegativadorMovimentoReg negativadorMovimentoReg = nmri.getNegativadorMovimentoReg();
					negativadorMovimentoReg.setUltimaAlteracao(new Date());
					negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.SIM);
					repositorioUtil.atualizar(negativadorMovimentoReg);
				}

			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0147] Cancelar Conta <br>
	 * [SB0001 – Atualizar Item da Negativação] <br>
	 * <br>
	 * 1. Caso a negativação não esteja excluída (NMRG_CDEXCLUSAOTIPO da tabela
	 * NEGATIVADOR_MOVIMENTO_REG com o valor nulo para NMRG_ID=NMRG_ID da tabela
	 * NEGATIVADOR_MOVIMENTO_REG_ITEM com NMRI_ID=Id do item de negativação), atualiza o item da
	 * negativação (tabela NEGATIVADOR_MOVIMENTO_REG_ITEM) com os seguintes valores:
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param negativadorMovimentoRegItem
	 * @throws ControladorException
	 */
	public void atualizarItemNegativacaoCancelarConta(NegativadorMovimentoRegItem negativadorMovimentoRegItem) throws ControladorException{

		try{

			if(negativadorMovimentoRegItem != null){

				if(negativadorMovimentoRegItem.getNegativadorMovimentoReg().getCodigoExclusaoTipo() == null){

					/**
					 * 1. Caso a negativação não esteja excluída (NMRG_CDEXCLUSAOTIPO da tabela
					 * NEGATIVADOR_MOVIMENTO_REG com o valor nulo para NMRG_ID=NMRG_ID da tabela
					 * NEGATIVADOR_MOVIMENTO_REG_ITEM com NMRI_ID=Id do item de negativação),
					 * atualiza o item da negativação (tabela NEGATIVADOR_MOVIMENTO_REG_ITEM) com os
					 * seguintes valores:
					 */

					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(new CobrancaDebitoSituacao(CobrancaDebitoSituacao.CANCELADO));
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setValorCancelado(negativadorMovimentoRegItem.getValorDebito());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.SIM);

				}else{

					/**
					 * 2. Caso contrário, atualiza o item da negativação (tabela
					 * NEGATIVADOR_MOVIMENTO_REG_ITEM) com os seguintes valores:
					 */

					negativadorMovimentoRegItem.setCobrancaDebitoSituacaoAposExclusao(new CobrancaDebitoSituacao(
									CobrancaDebitoSituacao.CANCELADO));
					negativadorMovimentoRegItem.setDataSituacaoDebitoAposExclusao(new Date());
					negativadorMovimentoRegItem.setValorCancelado(negativadorMovimentoRegItem.getValorDebito());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.SIM);
				}

				repositorioUtil.atualizar(negativadorMovimentoRegItem);

				/**
				 * 3. Atualiza o registro de negativação associado ao item
				 * (NEGATIVADOR_MOVIMENTO_REG com NMRG_ID=NMRG_ID da tabela
				 * NEGATIVADOR_MOVIMENTO_REG_item):
				 */

				NegativadorMovimentoReg negativadorMovimentoReg = negativadorMovimentoRegItem.getNegativadorMovimentoReg();
				negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.SIM);
				negativadorMovimentoReg.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(negativadorMovimentoReg);
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0147] Cancelar Conta
	 * Fluxo Principal:
	 * 1.1.7. Verificar se há relação do cancelamento com itens de negativação:
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param conta
	 * @throws ControladorException
	 */
	public void verificarHaRelacaoCancelamentoComItensNegativacaoCancelarConta(Conta conta) throws ControladorException{

		/*
		 * 1.1.7.1. Verificar se existem itens de negativação associados à conta - <<Inclui>>
		 * [UC0937 – Obter Itens de Negativação Associados à Conta] passando o identificador do
		 * imóvel (IMOV_ID da tabela CONTA) e a referência (CNTA_AMREFERENCIACONTA da tabela CONTA).
		 */

		Collection<Integer> colecaoIds = this.obterItensNegativacaoAssociadosAConta(conta.getImovel().getId(), conta.getReferencia());
		Collection<NegativadorMovimentoRegItem> colecaoRetorno = this
						.pesquisarNegativadorMovimentoRegItensPorIds((List<Integer>) colecaoIds);

		if(!Util.isVazioOrNulo(colecaoRetorno)){
			// 1.1.7.2. Caso existam itens de negativação associados à conta:

			for(NegativadorMovimentoRegItem nmri : colecaoRetorno){
				/*
				 * 1.1.7.2.1. Para cada item de negativação retornado, o sistema atualiza o item de
				 * negativação com os dados do cancelamento [SB0001 – Atualizar Item da
				 * Negativação].
				 */

				this.atualizarItemNegativacaoCancelarConta(nmri);
			}

		}
	}

	/**
	 * [UC0150] Retificar Conta <br>
	 * [SB0005] – Atualizar Item da Negativação <br>
	 * <br>
	 * 1. Caso o valor total da conta seja zero (Valor total de água + Valor total de esgoto + Valor
	 * total dos débitos - Valor total dos créditos – Valor total dos impostos):
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param negativadorMovimentoRegItem
	 * @throws ControladorException
	 */
	public void atualizarItemNegativacaoRetificarConta(NegativadorMovimentoRegItem negativadorMovimentoRegItem) throws ControladorException{

		try{

			if(negativadorMovimentoRegItem != null){

				// /**
				// * 1. Caso o valor total da conta seja zero (Valor total de água + Valor total de
				// * esgoto + Valor total dos débitos - Valor total dos créditos – Valor total dos
				// * impostos):
				// */

				if(negativadorMovimentoRegItem.getNegativadorMovimentoReg().getCodigoExclusaoTipo() == null){

					/**
					 * 1.1. Caso a negativação não esteja excluída (NMRG_CDEXCLUSAOTIPO da tabela
					 * NEGATIVADOR_MOVIMENTO_REG com o valor nulo para NMRG_ID=NMRG_ID da tabela
					 * NEGATIVADOR_MOVIMENTO_REG_ITEM com NMRI_ID=Id do item de negativação),
					 * atualiza o item da negativação (tabela NEGATIVADOR_MOVIMENTO_REG_ITEM) com os
					 * seguintes valores:
					 */

					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(new CobrancaDebitoSituacao(CobrancaDebitoSituacao.PAGO));
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setValorPago(BigDecimal.ZERO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());

				}else{

					/**
					 * 1.2. Caso contrário, atualiza o item da negativação (tabela
					 * NEGATIVADOR_MOVIMENTO_REG_ITEM) com os seguintes valores:
					 */

					negativadorMovimentoRegItem.setCobrancaDebitoSituacaoAposExclusao(new CobrancaDebitoSituacao(
									CobrancaDebitoSituacao.PAGO));
					negativadorMovimentoRegItem.setDataSituacaoDebitoAposExclusao(new Date());
					negativadorMovimentoRegItem.setValorPago(BigDecimal.ZERO);
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());
				}

				repositorioUtil.atualizar(negativadorMovimentoRegItem);

				/**
				 * 1.3. Atualiza o registro de negativação associado ao item
				 * (NEGATIVADOR_MOVIMENTO_REG com NMRG_ID=NMRG_ID da tabela
				 * NEGATIVADOR_MOVIMENTO_REG_item):
				 */

				NegativadorMovimentoReg negativadorMovimentoReg = negativadorMovimentoRegItem.getNegativadorMovimentoReg();
				negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.SIM);
				negativadorMovimentoReg.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(negativadorMovimentoReg);
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0147] Retificar Conta
	 * Fluxo Principal:
	 * 1.1.7. Verificar se há relação do cancelamento com itens de negativação:
	 * 
	 * @author Isaac Silva
	 * @date 05/08/2011
	 * @param conta
	 * @throws ControladorException
	 */
	public void verificarHaRelacaoCancelamentoComItensNegativacaoRetificarConta(Conta conta) throws ControladorException{

		/**
		 * 2. O sistema verifica se existem itens de negativação associados à conta - <<Inclui>>
		 * [UC0937 – Obter Itens de Negativação Associados à Conta] passando o identificador do
		 * imóvel (IMOV_ID da conta a ser retificada) e a referência (Ano e mês de referência da
		 * conta a ser retificada).
		 * 2.1. Caso existam itens de negativação associados à conta:
		 * 2.1.1. Para cada item de negativação retornado, o sistema atualiza o item de negativação
		 * com os dados da retificação
		 * [SB0005 – Atualizar Item da Negativação].
		 */

		if(conta.getValorTotal() != null && conta.getValorTotal().compareTo(BigDecimal.ZERO) == 0){
			Collection<Integer> colecaoIds = this.obterItensNegativacaoAssociadosAConta(conta.getImovel().getId(), conta.getReferencia());

			Collection<NegativadorMovimentoRegItem> colecaoRetorno = this
							.pesquisarNegativadorMovimentoRegItensPorIds((List<Integer>) colecaoIds);

			if(!Util.isVazioOrNulo(colecaoRetorno)){
				// 1.1.7.2. Caso existam itens de negativação associados à conta:

				for(NegativadorMovimentoRegItem nmri : colecaoRetorno){
					/*
					 * 1.1.7.2.1. Para cada item de negativação retornado, o sistema atualiza o item
					 * de
					 * negativação com os dados do cancelamento [SB0001 – Atualizar Item da
					 * Negativação].
					 */

					this.atualizarItemNegativacaoRetificarConta(nmri);
				}

			}
		}
	}

	/**
	 * [UC1016] Estornar Pagamentos <br>
	 * [SF0003] – Atualiza Pagamento
	 * 
	 * @author Isaac Silva
	 * @date 08/08/2011
	 * @param negativadorMovimentoRegItem
	 * @throws ControladorException
	 */
	public void atualizarItemNegativacaoEstornarPagamentos(NegativadorMovimentoRegItem negativadorMovimentoRegItem)
					throws ControladorException{

		try{

			if(negativadorMovimentoRegItem != null){

				if(negativadorMovimentoRegItem.getNegativadorMovimentoReg().getCodigoExclusaoTipo() == null){

					/**
					 * 2.1.3.1.1.1.1.1. Caso a negativação não esteja excluída (NMRG_CDEXCLUSAOTIPO
					 * da tabela NEGATIVADOR_MOVIMENTO_REG com o valor nulo para NMRG_ID=NMRG_ID da
					 * tabela NEGATIVADOR_MOVIMENTO_REG_ITEM com NMRI_ID=Id do item de negativação),
					 * atualiza o item da negativação (tabela NEGATIVADOR_MOVIMENTO_REG_ITEM) com os
					 * seguintes valores:
					 */
					/*
					 * NMRI_DTSITUACAODEBITO Data corrente
					 * NMRI_TMULTIMAALTERACAO Data e hora corrente
					 * NMRI_ICSITDEFINITIVA 2
					 * CDST_ID 1
					 * NMRI_VLPAGO 0
					 */
					negativadorMovimentoRegItem.setDataSituacaoDebito(new Date());
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacao(new CobrancaDebitoSituacao(CobrancaDebitoSituacao.PENDENTE));
					negativadorMovimentoRegItem.setValorPago(BigDecimal.ZERO);

				}else{

					/**
					 * 2.1.3.1.1.1.1.2. Caso contrário, atualiza o item da negativação (tabela
					 * NEGATIVADOR_MOVIMENTO_REG_ITEM) com os seguintes valores:
					 */
					/*
					 * NMRI_DTSITDEBAPOSEXCLUSAO Data corrente
					 * NMRI_TMULTIMAALTERACAO Data e hora corrente
					 * NMRI_ICSITDEFINITIVA 2
					 * CDST_IDAPOSEXCLUSAO 1
					 * NMRI_ VLPAGO 0
					 */

					negativadorMovimentoRegItem.setDataSituacaoDebitoAposExclusao(new Date());
					negativadorMovimentoRegItem.setUltimaAlteracao(new Date());
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(ConstantesSistema.NAO);
					negativadorMovimentoRegItem.setCobrancaDebitoSituacaoAposExclusao(new CobrancaDebitoSituacao(
									CobrancaDebitoSituacao.PENDENTE));
					negativadorMovimentoRegItem.setValorPago(BigDecimal.ZERO);
				}

				repositorioUtil.atualizar(negativadorMovimentoRegItem);

				/**
				 * 2.1.3.1.1.1.2. Atualiza o registro de negativação associado ao item
				 * (NEGATIVADOR_MOVIMENTO_REG com NMRG_ID=NMRG_ID da tabela
				 * NEGATIVADOR_MOVIMENTO_REG_item):
				 */
				/*
				 * NMRG_ICITEMATUALIZADO 1
				 * NMRG_TMULTIMAALTERACAO Data/Hora corrente
				 */

				NegativadorMovimentoReg negativadorMovimentoReg = negativadorMovimentoRegItem.getNegativadorMovimentoReg();
				negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.SIM);
				negativadorMovimentoReg.setUltimaAlteracao(new Date());
				repositorioUtil.atualizar(negativadorMovimentoReg);
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC1016] Estornar Pagamentos
	 * [SF0003] – Atualiza Pagamento
	 * 
	 * @author Isaac Silva
	 * @date 08/08/2011
	 * @param conta
	 * @throws ControladorException
	 */
	public void verificarHaRelacaoCancelamentoComItensNegativacaoEstornarPagementos(Conta conta) throws ControladorException{

		/**
		 * 2.1.3. Caso o item de débito seja uma conta (CNTA_ID com o valor diferente de
		 * nulo):
		 */

		if(conta != null){

			/**
			 * 2.1.3.1.1. Verificar se existem itens de negativação associados à conta - <<Inclui>>
			 * [UC0937 – Obter Itens de Negativação Associados à Conta] passando o identificador do
			 * imóvel (IMOV_ID da tabela CONTA) e a referência (CNTA_AMREFERENCIACONTA da tabela
			 * CONTA).
			 */

			// [UCXXXX - ]
			this.atualizarItensNegativacaoAssociadosAContaIndicadorSitDefinit(conta.getImovel().getId(), conta.getReferencia());

			Collection<Integer> colecaoIds = this.obterItensNegativacaoAssociadosAConta(conta.getImovel().getId(), conta.getReferencia());

			Collection<NegativadorMovimentoRegItem> colecaoRetorno = this
							.pesquisarNegativadorMovimentoRegItensPorIds((List<Integer>) colecaoIds);

			if(!Util.isVazioOrNulo(colecaoRetorno)){
				// 2.1.3.1.1.1. Caso existam itens de negativação associados à conta:

				for(NegativadorMovimentoRegItem nmri : colecaoRetorno){
					/*
					 * 2.1.3.1.1.1.1. Para cada item de negativação retornado,
					 */

					this.atualizarItemNegativacaoEstornarPagamentos(nmri);
				}

			}
		}
	}

	/**
	 * Valida se o imóvel está negativado
	 * 
	 * @author Isaac Silva
	 * @date 11/08/2011
	 * @param idImovel
	 * @param idAcaoCobranca
	 * @return
	 * @throws ControladorException
	 */
	public boolean isImovelNegativado(Integer idImovel, Integer idAcaoCobranca) throws ControladorException{

		try{
			return repositorioSpcSerasa.isImovelNegativado(idImovel, idAcaoCobranca);
		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * [UC0690] – Distribuir Dados Registro Movimento do SPC
	 * [FS0011] - Verificar a existência de retorno para o movimento de envio
	 * 
	 * @author Isaac Silva
	 * @date 17/08/2011
	 * @param negativadorMovimento
	 * @throws ControladorException
	 */
	private void verificarExistenciaRetornoParaMovimentoEnvio(NegativadorMovimento negativadorMovimento) throws ControladorException{

		if(negativadorMovimento != null && negativadorMovimento.getDataRetorno() != null){
			throw new ControladorException("atencao.movimento_retorno_ja_processado");
		}
	}

	/**
	 * [FS0012] - Verificar erro de Header/Trailler no arquivo retorno
	 * 
	 * @author Isaac Silva
	 * @date 18/08/2011
	 * @param negativador
	 * @param registro
	 * @param negativadorMovimentoRegistros
	 * @throws ControladorException
	 */
	private void verificarErroHeaderTraillerNoArquivoRetorno(Negativador negativador, String[] registro) throws ControladorException{

		String tipoRegistro = getConteudo(1, 2, registro[0].toCharArray());

		if(negativador.getId().equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){

			String codigoRetornoRegistroRecebido = getConteudo(331, 20, registro[0].toString().toCharArray());
			verificarCodigoRetornoErroHeaderTraillerNoArquivoRetorno(codigoRetornoRegistroRecebido, negativador.getId(), 2, tipoRegistro);

		}else if(negativador.getId().equals(Negativador.NEGATIVADOR_SPC_BRASIL)){

			String codigoRetornoRegistroRecebido = getConteudo(325, 10, registro[0].toString().toCharArray());
			verificarCodigoRetornoErroHeaderTraillerNoArquivoRetorno(codigoRetornoRegistroRecebido, negativador.getId(), 2, tipoRegistro);

		}else if(negativador.getId().equals(Negativador.NEGATIVADOR_SERASA)){

			String codigoRetornoRegistroRecebido = getConteudo(534, 60, registro[0].toString().toCharArray());
			verificarCodigoRetornoErroHeaderTraillerNoArquivoRetorno(codigoRetornoRegistroRecebido, negativador.getId(), 3, tipoRegistro);

		}else if(negativador.getId().equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){


			String codigoRetornoRegistroRecebido = getConteudo(231, 20, registro[0].toString().toCharArray());
			verificarCodigoRetornoErroHeaderTraillerNoArquivoRetorno(codigoRetornoRegistroRecebido, negativador.getId(), 2, tipoRegistro);

		}

	}

	private void verificarCodigoRetornoErroHeaderTraillerNoArquivoRetorno(String codigoRetornoRegistroRecebido, int codigoNegativador,
					int tamanhoCodigoRetornoRegistroRecebido, String tipoRegistro) throws ControladorException{

		if(!codigoRetornoRegistroRecebido.trim().equals("")){

			for(int i = 0; i < codigoRetornoRegistroRecebido.length(); i += tamanhoCodigoRetornoRegistroRecebido){
				
				String codRetornoString = codigoRetornoRegistroRecebido.substring(i, i	+ tamanhoCodigoRetornoRegistroRecebido);
				
				if(!Util.isVazioOuBranco(codRetornoString)){
					Integer codRetorno = Util.converterStringParaInteger(codRetornoString);
					FiltroNegativadorRetornoMotivo fnrm = new FiltroNegativadorRetornoMotivo();
					fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.CODIGO_RETORNO_MOTIVO, codRetorno));
					fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.NEGATIVADOR_RETORNO_MOTIVO_NEGATIVADOR,
									codigoNegativador));

					NegativadorRetornoMotivo negativadorRetornoMot;

					try{
						negativadorRetornoMot = (NegativadorRetornoMotivo) Util.retonarObjetoDeColecao(RepositorioUtilHBM.getInstancia()
										.pesquisar(fnrm, NegativadorRetornoMotivo.class.getName()));
					}catch(ErroRepositorioException e){
						throw new ControladorException(e.getMessage());
					}

					// FS0007
					if(negativadorRetornoMot == null){
						throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
					}

					if(negativadorRetornoMot.getIndicadorRegistroAceito().intValue() == NegativadorMovimentoReg.INDICADOR_NAO_ACEITO){
						if(tipoRegistro.trim().equals("99") || tipoRegistro.trim().equals("9")){
							throw new ControladorException("atencao.erro.trailler.retorno", null, negativadorRetornoMot
											.getDescricaoRetornocodigo());
						}else{
							throw new ControladorException("atencao.erro.header.retorno", null, negativadorRetornoMot
											.getDescricaoRetornocodigo());
						}
					}
				}
				
			}
		}
	}

	public Map<String, Collection<Integer>> validarImoveisExclusaoNegativacao(Collection<Integer> colecaoImoveis, Integer idNegativador)
					throws ControladorException{

		Map<String, Collection<Integer>> retorno = new HashMap<String, Collection<Integer>>();
		try{
			FiltroNegativador filtroNegativador = new FiltroNegativador();
			filtroNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.ID, idNegativador));
			filtroNegativador.adicionarCaminhoParaCarregamentoEntidade(FiltroNegativador.CLIENTE);
			Negativador negativador = (Negativador) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(filtroNegativador,
							Negativador.class.getName()));

			Collection<Integer> idsImoveisAceitos = new ArrayList<Integer>();
			Collection<Integer> idsImoveisNaoAceitos = new ArrayList<Integer>();
			Collection<Integer> idsImoveisNaoCadastrados = new ArrayList<Integer>();
			Collection<Integer> idsImoveisNaoNegativados = new ArrayList<Integer>();

			for(Integer idImovel : colecaoImoveis){
				// 1.5. Para cada imóvel do arquivo de imóveis para exclusão da negativação:
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

				Imovel imovelRetorno = (Imovel) Util
								.retonarObjetoDeColecao(repositorioUtil.pesquisar(filtroImovel, Imovel.class.getName()));
				if(imovelRetorno == null){
					// 1.5.1. Caso o imóvel não exista no cadastro de imóveis da empresa (não existe
					// ocorrência na tabela IMOVEL com IMOV_ID=Id do Imóvel do arquivo)
					idsImoveisNaoAceitos.add(idImovel);
					idsImoveisNaoCadastrados.add(idImovel);

				}else{
					// 1.5.2. Caso contrário, ou seja, o imóvel existe no cadastro de imóveis da
					// empresa
					Collection<NegativacaoImovei> colecaoNegativacaoImovei = repositorioSpcSerasa.pesquisarImovelNegativado(imovelRetorno,
									negativador);
					if(colecaoNegativacaoImovei != null && colecaoNegativacaoImovei.size() > 0){
						// 1.5.2.1. Caso o imóvel esteja negativado
						idsImoveisAceitos.add(imovelRetorno.getId());
					}else{
						idsImoveisNaoAceitos.add(imovelRetorno.getId());
						idsImoveisNaoNegativados.add(imovelRetorno.getId());
					}
				}
			}

			retorno.put("idsImoveisAceitos", idsImoveisAceitos);
			retorno.put("idsImoveisNaoAceitos", idsImoveisNaoAceitos);
			retorno.put("idsImoveisNaoCadastrados", idsImoveisNaoCadastrados);
			retorno.put("idsImoveisNaoNegativados", idsImoveisNaoNegativados);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		return retorno;

	}

	/**
	 * @autor Bruno Ferreira dos Santos
	 * @date 11/10/2011
	 * @return
	 * @throws ControladorException
	 */
	private Collection consultarNegativacoesDeterminarConfirmacao() throws ControladorException{

		Collection coll = new ArrayList();
		Collection colecaoNegativadorMovimentoReg = new ArrayList();

		try{

			coll = repositorioSpcSerasa.consultarNegativacoesDeterminarConfirmacao();

			if(!Util.isVazioOrNulo(coll)){
				Iterator it = coll.iterator();

				while(it.hasNext()){
					Object[] objs = (Object[]) it.next();

					DeterminarConfirmacaoNegativacaoHelper determinarConfirmacaoNegativacaoHelper = new DeterminarConfirmacaoNegativacaoHelper();

					NegativadorMovimentoReg negativadorMovimentoReg = new NegativadorMovimentoReg();
					negativadorMovimentoReg.setId((Integer) objs[0]);
					negativadorMovimentoReg.setImovel(new Imovel((Integer) objs[1]));
					negativadorMovimentoReg.setCodigoExclusaoTipo((Integer) objs[2]);
					negativadorMovimentoReg.setNegativadorMovimento(new NegativadorMovimento((Integer) objs[3]));
					negativadorMovimentoReg.getNegativadorMovimento().setCobrancaAcaoAtividadeComando(
									new CobrancaAcaoAtividadeComando((Integer) objs[4]));
					negativadorMovimentoReg.getNegativadorMovimento().setCobrancaAcaoAtividadeCronograma(
									new CobrancaAcaoAtividadeCronograma((Integer) objs[5]));
					negativadorMovimentoReg.getNegativadorMovimento().setDataProcessamentoEnvio((Date) objs[6]);
					negativadorMovimentoReg.setCodigoExclusaoTipo((Integer) objs[7]);

					NegativacaoImovei negativacaoImoveis = new NegativacaoImovei((Integer) objs[8]);
					negativacaoImoveis.setDataConfirmacao((Date) objs[9]);
					negativacaoImoveis.setDataExclusao((Date) objs[10]);

					NegativadorContrato negativadorContrato = new NegativadorContrato((Integer) objs[11]);
					negativadorContrato.setNumeroPrazoInclusao((Short) objs[12]);

					negativadorMovimentoReg.getNegativadorMovimento().setNegativador(new Negativador((Integer) objs[13]));
					negativadorMovimentoReg.setCliente(new Cliente((Integer) objs[14]));

					if(objs[15] != null){
						String numeroContrato = (String) objs[15];
						negativadorMovimentoReg.setNumeroContrato(numeroContrato.trim());

					}

					determinarConfirmacaoNegativacaoHelper.setNegativadorMovimentoReg(negativadorMovimentoReg);
					determinarConfirmacaoNegativacaoHelper.setNegativacaoImoveis(negativacaoImoveis);
					determinarConfirmacaoNegativacaoHelper.setNegativadorContrato(negativadorContrato);

					determinarConfirmacaoNegativacaoHelper.setNegativadorContrato(negativadorContrato);

					colecaoNegativadorMovimentoReg.add(determinarConfirmacaoNegativacaoHelper);

				}
			}else{
				System.out.println("atencao.nao_existem_negativacoes_confirmadas");
			}

		}catch(ErroRepositorioException e){
			throw new ControladorException("erro.sistema", e);
		}

		return colecaoNegativadorMovimentoReg;
	}

	/**
	 * [UC1105] Determinar Confirmacao Negativacao
	 * [SB0001] Confirmar Negativacao
	 * 
	 * @autor Bruno Ferreira dos Santos
	 * @date 11/10/2011
	 * @param colecao
	 * @throws ControladorException
	 */
	public void determinarConfirmacaoNegativacao(Integer idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;
		idUnidadeIniciada = this.getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{

			Collection colecao = this.consultarNegativacoesDeterminarConfirmacao();

			Iterator<DeterminarConfirmacaoNegativacaoHelper> it = colecao.iterator();
			while(it.hasNext()){
				DeterminarConfirmacaoNegativacaoHelper dcn = it.next();

				FiltroNegativacaoImoveis filtroNegativacaoImoveis = new FiltroNegativacaoImoveis();
				filtroNegativacaoImoveis.adicionarParametro(new ParametroSimples(FiltroNegativacaoImoveis.ID, dcn.getNegativacaoImoveis()
								.getId()));

				NegativacaoImovei negativacaoImovei = (NegativacaoImovei) this.getControladorUtil().pesquisar(filtroNegativacaoImoveis,
								NegativacaoImovei.class.getName()).iterator().next();

				negativacaoImovei.setDataConfirmacao(Util.adicionarNumeroDiasDeUmaData(dcn.getNegativadorMovimentoReg()
								.getNegativadorMovimento().getDataProcessamentoEnvio(), (dcn.getNegativadorContrato()
								.getNumeroPrazoInclusao() + 1)));
				negativacaoImovei.setUltimaAlteracao(new Date());

				getControladorUtil().atualizar(negativacaoImovei);

				CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();

				FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
				filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, dcn
								.getNegativadorMovimentoReg().getImovel().getId()));
				filtroImovelCobrancaSituacao.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));
				if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId().equals(
								Negativador.NEGATIVADOR_SERASA)){
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
									CobrancaSituacao.CARTA_ENVIADA_A_SERASA));
					cobrancaSituacao.setId(CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NA_SERASA);
				}else if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId().equals(
								Negativador.NEGATIVADOR_SPC_BRASIL)){
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
									CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BRASIL));
					cobrancaSituacao.setId(CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BRASIL);
				}else if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId().equals(
								Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
									CobrancaSituacao.CARTA_ENVIADA_AO_SPC_SP));
					cobrancaSituacao.setId(CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_SP);
				}else if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId()
								.equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
									CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BOA_VISTA));
					cobrancaSituacao.setId(CobrancaSituacao.NEGATIVADO_AUTOMATICAMENTE_NO_SPC_BOA_VISTA);
				}

				Collection colecaoImovelCobrancaSituacao = this.getControladorUtil().pesquisar(filtroImovelCobrancaSituacao,
								ImovelCobrancaSituacao.class.getName());

				if(!Util.isVazioOrNulo(colecaoImovelCobrancaSituacao)){
					ImovelCobrancaSituacao imovelCobrancaSituacao = (ImovelCobrancaSituacao) colecaoImovelCobrancaSituacao.iterator()
									.next();
					imovelCobrancaSituacao.setDataRetiradaCobranca(Util.adicionarNumeroDiasDeUmaData(dcn.getNegativadorMovimentoReg()
									.getNegativadorMovimento().getDataProcessamentoEnvio(), (dcn.getNegativadorContrato()
									.getNumeroPrazoInclusao() + 1)));
					imovelCobrancaSituacao.setUltimaAlteracao(new Date());
					this.getControladorUtil().atualizar(imovelCobrancaSituacao);
				}

				ImovelCobrancaSituacao imovelCobrancaSituacao = new ImovelCobrancaSituacao();

				imovelCobrancaSituacao.setImovel(dcn.getNegativadorMovimentoReg().getImovel());
				imovelCobrancaSituacao.setDataImplantacaoCobranca(Util.adicionarNumeroDiasDeUmaData(dcn.getNegativadorMovimentoReg()
								.getNegativadorMovimento().getDataProcessamentoEnvio(), (dcn.getNegativadorContrato()
								.getNumeroPrazoInclusao() + 1)));
				if(dcn.getNegativadorMovimentoReg().getCodigoExclusaoTipo() != null){
					imovelCobrancaSituacao.setDataRetiradaCobranca(dcn.getNegativacaoImoveis().getDataExclusao());
					this.atualizarDataRetiradaSituacaoCartaEnviada(dcn);
				}else{
					imovelCobrancaSituacao.setDataRetiradaCobranca(null);
				}

				imovelCobrancaSituacao.setCobrancaSituacao(cobrancaSituacao);

				imovelCobrancaSituacao.setCliente(dcn.getNegativadorMovimentoReg().getCliente());

				imovelCobrancaSituacao.setAnoMesReferenciaInicio(null);
				imovelCobrancaSituacao.setAnoMesReferenciaFinal(null);
				imovelCobrancaSituacao.setContaMotivoRevisao(null);
				imovelCobrancaSituacao.setEscritorio(null);
				imovelCobrancaSituacao.setAdvogado(null);
				imovelCobrancaSituacao.setUltimaAlteracao(new Date());

				this.getControladorUtil().inserir(imovelCobrancaSituacao);

				FiltroNegativadorMovimentoReg filtroNegativadorMovimentoReg = new FiltroNegativadorMovimentoReg();
				filtroNegativadorMovimentoReg.adicionarParametro(new ParametroSimples(FiltroNegativadorMovimentoReg.ID, dcn
								.getNegativadorMovimentoReg().getId()));

				NegativadorMovimentoReg negativadorMovimentoReg = (NegativadorMovimentoReg) this.getControladorUtil().pesquisar(
								filtroNegativadorMovimentoReg, NegativadorMovimentoReg.class.getName()).iterator().next();
				negativadorMovimentoReg.setCobrancaSituacao(cobrancaSituacao);
				negativadorMovimentoReg.setUltimaAlteracao(new Date());

				this.getControladorUtil().atualizar(negativadorMovimentoReg);

			}
			this.getControladorBatch().encerrarUnidadeProcessamentoBatch(null, idUnidadeIniciada, false);

		}catch(Exception ex){
			this.getControladorBatch().encerrarUnidadeProcessamentoBatch(ex, idUnidadeIniciada, true);
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC1105] Determinar Confirmacao Negativacao
	 * [SB0002] Atualizar Data da Retirada da Situacao Carta Enviada
	 * 
	 * @autor Bruno Ferreira dos Santos
	 * @date 11/10/2011
	 * @param dcn
	 * @throws ControladorException
	 */
	private void atualizarDataRetiradaSituacaoCartaEnviada(DeterminarConfirmacaoNegativacaoHelper dcn) throws ControladorException{

		FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
		filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, dcn
						.getNegativadorMovimentoReg().getImovel().getId()));
		filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA, dcn
						.getNegativacaoImoveis().getDataExclusao()));
		if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId().equals(Negativador.NEGATIVADOR_SERASA)){
			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
							CobrancaSituacao.CARTA_ENVIADA_A_SERASA));

		}else if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId().equals(
						Negativador.NEGATIVADOR_SPC_BRASIL)){
			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
							CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BRASIL));

		}else if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId().equals(
						Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
							CobrancaSituacao.CARTA_ENVIADA_AO_SPC_SP));

		}else if(dcn.getNegativadorMovimentoReg().getNegativadorMovimento().getNegativador().getId().equals(
						Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
							CobrancaSituacao.CARTA_ENVIADA_AO_SPC_BOA_VISTA));

		}

		Collection colecaoImovelCobrancaSituacao = getControladorUtil().pesquisar(filtroImovelCobrancaSituacao,
						ImovelCobrancaSituacao.class.getName());

		if(!Util.isVazioOrNulo(colecaoImovelCobrancaSituacao)){
			ImovelCobrancaSituacao imovelCobrancaSituacao = (ImovelCobrancaSituacao) colecaoImovelCobrancaSituacao.iterator().next();
			imovelCobrancaSituacao.setDataRetiradaCobranca(Util.adicionarNumeroDiasDeUmaData(dcn.getNegativadorMovimentoReg()
							.getNegativadorMovimento().getDataProcessamentoEnvio(),
							(dcn.getNegativadorContrato().getNumeroPrazoInclusao() + 1)));

			getControladorUtil().atualizar(imovelCobrancaSituacao);
		}

	}

	/**
	 * [UC0688] Gerar Resumo Diario de Negativaocao
	 * 
	 * @autor Genival Barbosa
	 * @date 26/10/2011
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarResumoDiarioNegativacao(Integer idFuncionalidadeIniciada) throws ControladorException{

		int idUnidadeIniciada = 0;
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.FUNCIONALIDADE, idFuncionalidadeIniciada);

		try{
			Collection<NegativadorMovimentoReg> collNegativadorMovimentoReg = repositorioSpcSerasa
							.pesquisarNegativadorMovimentoRegParaGerarResumoDiarioNegativacao();

			if(collNegativadorMovimentoReg != null && collNegativadorMovimentoReg.size() > 0){

				for(NegativadorMovimentoReg negativadorMovimentoReg : collNegativadorMovimentoReg){

					// 2.3 Processa os itens de negativacao
					// [SB0001 - Processar Itens da Negativação]
					Collection<NegativadorMovimentoRegItem> collNegativadorMovimentoRegItem = repositorioSpcSerasa
									.consultarNegativadorMovimentoRegItem(negativadorMovimentoReg.getId());

					if(collNegativadorMovimentoRegItem != null && collNegativadorMovimentoRegItem.size() > 0){

						Map<String, Object> mapa = montarMapQuantidadeValor(negativadorMovimentoReg, collNegativadorMovimentoRegItem);

						// [SB0002 - Determinar Situacao Predominante do Debito da Negativacao]
						determinarSituacaoPredominanteDebitoNegativacao(negativadorMovimentoReg, collNegativadorMovimentoRegItem, mapa);

						negativadorMovimentoReg.setCobrancaDebitoSituacao((CobrancaDebitoSituacao) mapa
										.get("cobrancaDebitoSituacaoPredominante"));
						negativadorMovimentoReg.setDataSituacaoDebito((Date) mapa.get("dataSituacaoPredominante"));
						negativadorMovimentoReg.setIndicadorSituacaoDefinitiva((Short) mapa.get("indicadorSituacaoDefinitivaItens"));
						negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.NAO);
						negativadorMovimentoReg.setUltimaAlteracao(new Date());
						getControladorUtil().atualizar(negativadorMovimentoReg);
					}

				}

			}else{
				// [FS0001] - verificar existencia de negativações
				// throw new ControladorException("atencao.nenhuma_negativacao_selecionada");
			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(null, idUnidadeIniciada, false);

		}catch(Exception ex){
			getControladorBatch().encerrarUnidadeProcessamentoBatch(ex, idUnidadeIniciada, true);
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	private Map<String, Object> montarMapQuantidadeValor(NegativadorMovimentoReg negativadorMovimentoReg,
					Collection<NegativadorMovimentoRegItem> collNegativadorMovimentoRegItem) throws ErroRepositorioException{

		Map<String, Object> mapa = new HashMap<String, Object>();

		Integer quantidadeItensNegativacao = 0;
		Integer quantidadeItensNegativacaoPendentes = 0;
		Integer quantidadeItensNegativacaoPagos = 0;
		Integer quantidadeItensNegativacaoParcelados = 0;
		Integer quantidadeItensNegativacaoCancelados = 0;
		Integer quantidadeItensNegativacaoExcluidos = 0;

		BigDecimal valorItensNegativacaoPendentes = BigDecimal.ZERO;
		BigDecimal valorItensNegativacaoPagos = BigDecimal.ZERO;
		BigDecimal valorItensNegativacaoParcelados = BigDecimal.ZERO;
		BigDecimal valorItensNegativacaoCancelados = BigDecimal.ZERO;
		BigDecimal valorItensNegativacaoExcluidos = BigDecimal.ZERO;

		Date dataMaiorItensNegativacaoPagos = null;
		Date dataMaiorItensNegativacaoParcelados = null;
		Date dataMaiorItensNegativacaoCancelados = null;
		Date dataMaiorItensNegativacaoExcluidos = null;

		short indicadorSituacaoDefinitivaItens = ConstantesSistema.SIM;

		// 2.3 Processa os itens de negativacao
		// [SB0001 - Processar Itens da Negativação]

		if(collNegativadorMovimentoRegItem != null && collNegativadorMovimentoRegItem.size() > 0){
			for(NegativadorMovimentoRegItem negativadorMovimentoRegItem : collNegativadorMovimentoRegItem){
				CobrancaDebitoSituacao cobrancaDebitoSituacao = negativadorMovimentoRegItem.getCobrancaDebitoSituacao();
				// Date
				// Date dataCobrancaDebitoSituacao =
				// negativadorMovimentoRegItem.getDataSituacaoDebito();

				quantidadeItensNegativacao++;

				// 2.4 Acumula o valor do item (NMRI_VLDEBITO) de acordo ...
				if(cobrancaDebitoSituacao.getId().equals(CobrancaDebitoSituacao.PENDENTE)){
					quantidadeItensNegativacaoPendentes++;
					valorItensNegativacaoPendentes = valorItensNegativacaoPendentes.add(negativadorMovimentoRegItem.getValorDebito());
					// a data atual sera colocada se predominar a situacao pendente

				}else if(cobrancaDebitoSituacao.getId().equals(CobrancaDebitoSituacao.PAGO)){
					quantidadeItensNegativacaoPagos++;
					valorItensNegativacaoPagos = valorItensNegativacaoPagos.add(negativadorMovimentoRegItem.getValorDebito());
					if(dataMaiorItensNegativacaoPagos == null){
						dataMaiorItensNegativacaoPagos = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}else if(Util.compararData(dataMaiorItensNegativacaoPagos, negativadorMovimentoRegItem.getDataSituacaoDebito()) < 0){
						dataMaiorItensNegativacaoPagos = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}
				}else if(cobrancaDebitoSituacao.getId().equals(CobrancaDebitoSituacao.PARCELADO)){
					quantidadeItensNegativacaoParcelados++;
					valorItensNegativacaoParcelados = valorItensNegativacaoParcelados.add(negativadorMovimentoRegItem.getValorDebito());
					if(dataMaiorItensNegativacaoParcelados == null){
						dataMaiorItensNegativacaoParcelados = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}else if(Util.compararData(dataMaiorItensNegativacaoParcelados, negativadorMovimentoRegItem.getDataSituacaoDebito()) < 0){
						dataMaiorItensNegativacaoParcelados = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}
				}else if(cobrancaDebitoSituacao.getId().equals(CobrancaDebitoSituacao.CANCELADO)){
					quantidadeItensNegativacaoCancelados++;
					valorItensNegativacaoCancelados = valorItensNegativacaoCancelados.add(negativadorMovimentoRegItem.getValorDebito());
					if(dataMaiorItensNegativacaoCancelados == null){
						dataMaiorItensNegativacaoCancelados = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}else if(Util.compararData(dataMaiorItensNegativacaoCancelados, negativadorMovimentoRegItem.getDataSituacaoDebito()) < 0){
						dataMaiorItensNegativacaoCancelados = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}
				}else if(cobrancaDebitoSituacao.getId().equals(CobrancaDebitoSituacao.EXCLUIDO)){
					quantidadeItensNegativacaoExcluidos++;
					valorItensNegativacaoExcluidos = valorItensNegativacaoExcluidos.add(negativadorMovimentoRegItem.getValorDebito());
					if(dataMaiorItensNegativacaoExcluidos == null){
						dataMaiorItensNegativacaoExcluidos = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}else if(Util.compararData(dataMaiorItensNegativacaoExcluidos, negativadorMovimentoRegItem.getDataSituacaoDebito()) < 0){
						dataMaiorItensNegativacaoExcluidos = negativadorMovimentoRegItem.getDataSituacaoDebito();
					}
				}

				if(negativadorMovimentoRegItem.getIndicadorSituacaoDefinitiva() == ConstantesSistema.NAO){
					indicadorSituacaoDefinitivaItens = ConstantesSistema.NAO;
				}
			}
		}

		mapa.put("quantidadeItensNegativacao", quantidadeItensNegativacao);
		mapa.put("quantidadeItensNegativacaoPendentes", quantidadeItensNegativacaoPendentes);
		mapa.put("quantidadeItensNegativacaoPagos", quantidadeItensNegativacaoPagos);
		mapa.put("quantidadeItensNegativacaoParcelados", quantidadeItensNegativacaoParcelados);
		mapa.put("quantidadeItensNegativacaoCancelados", quantidadeItensNegativacaoCancelados);
		mapa.put("quantidadeItensNegativacaoExcluidos", quantidadeItensNegativacaoExcluidos);
		mapa.put("valorItensNegativacaoPendentes", valorItensNegativacaoPendentes);
		mapa.put("valorItensNegativacaoPagos", valorItensNegativacaoPagos);
		mapa.put("valorItensNegativacaoParcelados", valorItensNegativacaoParcelados);
		mapa.put("valorItensNegativacaoCancelados", valorItensNegativacaoCancelados);
		mapa.put("valorItensNegativacaoExcluidos", valorItensNegativacaoExcluidos);
		mapa.put("dataMaiorItensNegativacaoPagos", dataMaiorItensNegativacaoPagos);
		mapa.put("dataMaiorItensNegativacaoParcelados", dataMaiorItensNegativacaoParcelados);
		mapa.put("dataMaiorItensNegativacaoCancelados", dataMaiorItensNegativacaoCancelados);
		mapa.put("dataMaiorItensNegativacaoExcluidos", dataMaiorItensNegativacaoExcluidos);
		mapa.put("indicadorSituacaoDefinitivaItens", indicadorSituacaoDefinitivaItens);

		return mapa;
	}

	public void atualizarItemDaNegativacao(int referencia, Imovel imovel, Integer idCobrancaDebitoSituacao, Date dataSituacao,
					BigDecimal valor, Boolean ignorarSituacaoDefinitiva, Short indicadorSituacaoDefinitiva) throws ControladorException{

		CobrancaDebitoSituacao cobrancaDebitoSituacao = new CobrancaDebitoSituacao();
		cobrancaDebitoSituacao.setId(idCobrancaDebitoSituacao);

		if(imovel != null){
			// 3.1. Verificar se há relação do parcelamento com itens de negativação:

			Integer idImovel = imovel.getId();
			int referenciaConta = referencia;

			// [UC0937 – Obter Itens de Negativação Associados à Conta]
			Collection<Integer> itensNegativacaoAssociadosAConta = this.obterItensNegativacaoAssociadosAConta(idImovel, referenciaConta,
							ignorarSituacaoDefinitiva);

			if(itensNegativacaoAssociadosAConta != null && itensNegativacaoAssociadosAConta.size() > 0){

				Collection<NegativadorMovimentoRegItem> negativadorMovimentoRegItens = this
								.pesquisarNegativadorMovimentoRegItensPorIds((List<Integer>) itensNegativacaoAssociadosAConta);

				if(!Util.isVazioOrNulo(negativadorMovimentoRegItens)){
					for(NegativadorMovimentoRegItem negativadorMovimentoRegItem : negativadorMovimentoRegItens){
						NegativadorMovimentoReg negativadorMovimentoReg = negativadorMovimentoRegItem.getNegativadorMovimentoReg();

						if(negativadorMovimentoReg.getCodigoExclusaoTipo() == null){
							// Negativação não foi excluida
							negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobrancaDebitoSituacao);
							negativadorMovimentoRegItem.setDataSituacaoDebito(dataSituacao);
						}else{
							// Negativação foi excluida

							negativadorMovimentoRegItem.setCobrancaDebitoSituacaoAposExclusao(cobrancaDebitoSituacao);
							negativadorMovimentoRegItem.setDataSituacaoDebitoAposExclusao(dataSituacao);
						}

						negativadorMovimentoRegItem.setValorPago(valor);
						negativadorMovimentoRegItem.setUltimaAlteracao(new Date());
						negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(indicadorSituacaoDefinitiva);

						// Atualiza o registro de negativação associado ao item
						negativadorMovimentoReg.setIndicadorSituacaoDefinitiva(indicadorSituacaoDefinitiva);
						negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.SIM);
						negativadorMovimentoReg.setUltimaAlteracao(new Date());

						getControladorUtil().atualizar(negativadorMovimentoReg);

						// Atualiza o item da negativação
						getControladorUtil().atualizar(negativadorMovimentoRegItem);
					}
				}
			}
		}
	}

	public void atualizarItemDaNegativacaoDesfazerParcelamento(int referencia, Imovel imovel, Date dataSituacao,
					Boolean ignorarSituacaoDefinitiva, Short indicadorSituacaoDefinitiva) throws ControladorException{

		Collection<Integer> itensNegativacaoAssociadosAConta = this.obterItensNegativacaoAssociadosAConta(imovel.getId(), referencia,
						ignorarSituacaoDefinitiva);

		if(itensNegativacaoAssociadosAConta != null && itensNegativacaoAssociadosAConta.size() > 0){

			Collection<NegativadorMovimentoRegItem> negativadorMovimentoRegItens = this
							.pesquisarNegativadorMovimentoRegItensPorIds((List<Integer>) itensNegativacaoAssociadosAConta);

			if(!Util.isVazioOrNulo(negativadorMovimentoRegItens)){
				Date dataCorrente = new Date();

				CobrancaDebitoSituacao cobrancaDebitoSituacao = new CobrancaDebitoSituacao();
				cobrancaDebitoSituacao.setId(CobrancaDebitoSituacao.PENDENTE);

				for(NegativadorMovimentoRegItem negativadorMovimentoRegItem : negativadorMovimentoRegItens){
					NegativadorMovimentoReg negativadorMovimentoReg = negativadorMovimentoRegItem.getNegativadorMovimentoReg();

					if(negativadorMovimentoRegItem.getCobrancaDebitoSituacao() != null
									&& CobrancaDebitoSituacao.PARCELADO.equals(negativadorMovimentoRegItem.getCobrancaDebitoSituacao()
													.getId())){

						if(negativadorMovimentoReg.getCodigoExclusaoTipo() == null){
							// Negativação não foi excluida

							negativadorMovimentoRegItem.setCobrancaDebitoSituacao(cobrancaDebitoSituacao);
							negativadorMovimentoRegItem.setDataSituacaoDebito(dataCorrente);
						}else{
							// Negativação foi excluida

							negativadorMovimentoRegItem.setCobrancaDebitoSituacaoAposExclusao(cobrancaDebitoSituacao);
							negativadorMovimentoRegItem.setDataSituacaoDebitoAposExclusao(dataCorrente);
						}
					}

					negativadorMovimentoRegItem.setUltimaAlteracao(dataCorrente);
					negativadorMovimentoRegItem.setIndicadorSituacaoDefinitiva(indicadorSituacaoDefinitiva);

					// Atualiza o registro de negativação associado ao item
					negativadorMovimentoReg.setIndicadorSituacaoDefinitiva(indicadorSituacaoDefinitiva);
					negativadorMovimentoReg.setUltimaAlteracao(dataCorrente);
					negativadorMovimentoReg.setIndicadorItemAtualizado(ConstantesSistema.SIM);

					getControladorUtil().atualizar(negativadorMovimentoReg);

					// Atualiza o item da negativação
					getControladorUtil().atualizar(negativadorMovimentoRegItem);
				}
			}
		}
	}



	/**
	 * [UC0672] - Registrar Movimento de Retorno dos Negativadores
	 * [SB0008] - Validar Arquivo de Movimento de Retorno do SPC-BOA_VISTA
	 * 
	 * @param registro
	 * @param negativador
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	public Object[] verificarHeaderSPCBoaVistaRetorno(String registro, Negativador negativador) throws ControladorException,
					ErroRepositorioException{

		Object[] retorno = new Object[3];

		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
		String nomeEmpresaSistemaParametros = sistemaParametro.getNomeEmpresa();


		// cadastrado no sistema (código do associado do arquivo de propriedades), exibir a mensagem
		// "Associado não corresponde ao da <<PARM_NMEMPRESA da tabela PARAMETROS_SISTEMA>>" e
		// encerrar o caso de uso.
		// H.01 [001-008] - codigo do assocido
		String associadoRetorno = getConteudo(1, 8, registro.toCharArray());

		String associado = ConstantesAplicacao.get("spc_boa_vista_codigo_associado");
		if(!associadoRetorno.equals(associado)){
			throw new ControladorException("atencao.arquivo_movimento_sem_associado", null, nomeEmpresaSistemaParametros);
		}

		// 3. Caso o tipo do registro (campo H.02) não corresponda a 0000000000, exibir a mensagem
		// "Arquivo de Movimento de Negativador sem Header" e encerrar o caso de uso.
		// H.02 [009-018] - 0000000000
		String constanteZerosRetorno = getConteudo(9, 10, registro.toCharArray());
		String constanteZeros = ConstantesAplicacao.get("constante_zeros");
		if(!constanteZerosRetorno.equals(constanteZeros)){
			throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_tipos_invalidos");
		}

		// 4. Caso a data de retorno (campo H.03) não corresponda a uma data válida no formato
		// DDMMAA, exibir a mensagem "Data de Retorno no Header é invalida" e encerrar o caso de
		// uso.
		// H.03 19 24 NUM(6) Data da Geração do Movimento no formato: 'DDMMAA'
		String dataCorrenteRetorno = getConteudo(19, 6, registro.toCharArray());
		if(dataCorrenteRetorno.equals("")){
			throw new ControladorException("atencao.arquivo_movimento_contem_registros_com_tipos_invalidos");
		}

		// 5. Caso a Operação (campo H.04) não seja igual a "RETORNO", exibir a mensagem
		// "Operação não corresponde a RETORNO" e encerrar o caso de uso.
		// H.04 25 31 CHAR(7) Identificação do movimento (REMESSA ou RETORNO)
		String constante = ConstantesAplicacao.get("constante_retorno");
		String constanteRetorno = getConteudo(25, 7, registro.toCharArray());
		if(!constanteRetorno.equals(constante)){
			throw new ControladorException("atencao.operacao_nao_corresponde_retorno");
		}


		// 6. Caso o Nome da Empresa (campo H.05) não seja igual ao nome da empresa (PARM_NMEMPRESA
		// da tabela SISTEMA_PARAMETROS), exibir a mensagem
		// "Associado não corresponde ao cadastrado no sistema <<PARM_NMEMPRESA da tabela SISTEMA_PARAMETROS>>"
		// e encerrar o caso de uso.
		// H.05 32 86 CHAR(55) Nome da Empresa COMPANHIA DE AGUA E ESGOTO DA PARAIBA S.A.
		String nomeEmpresa = getConteudo(32, 55, registro.toCharArray());
		if(!nomeEmpresa.trim().equals(nomeEmpresaSistemaParametros.trim())){
			throw new ControladorException("atencao.arquivo_movimento_sem_associado", null, sistemaParametro.getNomeEmpresa());
		}

		// 7. O sistema determina o número seqüencial do arquivo (NSA) esperado (NGCN_NNNSAENVIO da
		// tabela NEGATIVADOR_CONTRATO com NEGT_ID=NEGT_ID do SPC_BOA_VISTA e
		// (NGCN_DTCONTRATOENCERRAMENTO com o valor nulo ou NGCN_DTCONTRATOFIM>=Data corrente)).

		// 8. Caso o número seqüencial do arquivo (NSA) (campo H.07) seja maior que o número
		// seqüencial do arquivo (NSA) (NGCN_NNNSAENVIO) esperado, exibir a mensagem
		// "Movimento está fora de seqüência" e encerrar o caso de uso.
		// H.07 88 94 NUM(7) Número de Sequência do Arquivo
		Integer numeroSequencialArquivo = Util.converterStringParaInteger(getConteudo(88, 7, registro.toCharArray()));

		NegativadorContrato negativadorContrato = new NegativadorContrato();

		try{
			negativadorContrato = this.repositorioSpcSerasa.consultarNegativadorContratoVigente(negativador.getId());
			if(negativadorContrato == null){
				throw new ControladorException("atencao.nao_ha_contrato_negativador");
			}else{
				String numeroSequencialEnvioBD = negativadorContrato.getNumeroSequencialEnvio() + "";
				String qtdZeros = "";
				int tamanho = 8 - numeroSequencialEnvioBD.length();
				for(int i = 0; i < tamanho; i++){
					qtdZeros = qtdZeros + "0";
				}
				numeroSequencialEnvioBD = qtdZeros + numeroSequencialEnvioBD;
				// H.07
				if(numeroSequencialArquivo > Util.converterStringParaInteger(numeroSequencialEnvioBD)){
					throw new ControladorException("atencao.movimento_fora_sequencia");
				}
			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		// 9. O sistema seleciona o movimento de envio do SPC_BOA_VISTA (a partir da tabela
		// NEGATIVADOR_MOVIMENTO com NEGT_ID=NEGT_ID do SPC_BOA_VISTA e NGMV_NNNSAENVIO=Número
		// Seqüencial do Arquivo (NSA) (campo H.07))
		NegativadorMovimento negativadorMovimento = this.repositorioSpcSerasa.getNegativadorMovimento(negativador, numeroSequencialArquivo,
						true);
		if(negativadorMovimento != null){
			// [FS0011 - Verificar a existência de retorno para o movimento de envio].
			verificarExistenciaRetornoParaMovimentoEnvio(negativadorMovimento);

			// 10. O sistema seleciona o registro de envio correspondente (a partir da tabela
			// NEGATIVADOR_MOVIMENTO_REG com NGMV_ID=NGMV_ID da tabela NEGATIVADOR_MOVIMENTO e
			// posições
			// 9 a 18 de NMRG_CNREGISTRO=0000000000)
			// [FS0006 - Verificar a existência do registro de envio],

			String numeroRegistro = constanteZerosRetorno;
			Integer numRegistro = Util.converterStringParaInteger(numeroRegistro);

			NegativadorMovimentoReg negativadorMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoReg(negativadorMovimento,
							numRegistro);

			if(negativadorMovimentoReg != null && negativadorMovimentoReg.getConteudoRegistro() == null){
				throw new ControladorException("atencao.arquivo_movimento_sem_registros");
			}

			String[] regLinha = new String[2];
			regLinha[0] = registro;

			Object[] negativadorMovimentoRegistrosLinha = new Object[2];
			negativadorMovimentoRegistrosLinha[0] = negativadorMovimentoReg;

			// [FS0012 - Verificar erro de Header/Trailler no arquivo retorno], passando o conteúdo
			// do
			// primeiro registro de movimento.
			verificarErroHeaderTraillerNoArquivoRetorno(negativador, regLinha);


			retorno[0] = numRegistro;
			retorno[1] = negativadorMovimento;
			retorno[2] = numeroSequencialArquivo;

		}


		return retorno;

	}

	/**
	 * [UC0672] - Registrar Movimento de Retorno dos Negativadores
	 * [SB0009] - Atualizar Movimento de Envio do SPC-BOA_VISTA
	 * 
	 * @param registro
	 * @param negativador
	 * @return
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	private void processarArquivoMovimentoRetornoSPCBoaVista(StringBuilder stringBuilderTxt, Negativador negativador,
					NegativadorMovimento negativadorMovimento) throws ControladorException{


		Collection collNegativadorMovimentoReg = new ArrayList();

		Integer numeroRegistro = 0;


		String tipoRegistro = "";
		boolean isHeaderOuTrailler = false;

		StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

		while(stk.hasMoreTokens()){
			// ---------------------------------------------------
			String registro = stk.nextToken();

			registro = Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(registro, 250);


			tipoRegistro = getConteudo(9, 10, registro.toCharArray());
			if(tipoRegistro.equals(ConstantesAplicacao.get("constante_header"))
							|| tipoRegistro.equals(ConstantesAplicacao.get("spc_boa_vista_constante_trailler"))){
				isHeaderOuTrailler = true;
			}else{
				isHeaderOuTrailler = false;
			}

			try{

				Map parameters = new HashMap();

				// [FS0006 - Verificar a existência do registro de envio].
				// 2.3. O sistema atualiza os dados do registro de envio correspondente
				Collection collNegaMovimentoReg = this.repositorioSpcSerasa.getNegativadorMovimentoRegSpcBoaVista(negativadorMovimento, numeroRegistro);

				Collection collRegistros = new ArrayList();

				collNegativadorMovimentoReg.add(collNegaMovimentoReg);

				if(collNegaMovimentoReg.size() == 1){

					collRegistros.add(registro);

				}else if(collNegaMovimentoReg.size() == 3){

					collRegistros.add(registro);

					for(int i = 0; i < collNegaMovimentoReg.size() - 1; i++){

						registro = stk.nextToken();

						registro = Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(registro, 250);

						collRegistros.add(registro);
					}

					// ......................................................................
					Iterator itx = collRegistros.iterator();

					while(itx.hasNext()){

						String registroItx = (String) itx.next();

						System.out.println("Numero registro : " + numeroRegistro);
						System.out.println(" registro : " + registroItx);

					}

					// ......................................................................

				}

				numeroRegistro = numeroRegistro + 1;

				parameters.put("collNegaMovimentoReg", collNegaMovimentoReg);
				parameters.put("collRegistros", collRegistros);

				// [SB0005 - Atualizar Registro de Envio].
				this.atualizarRegistroEnvioSPCBoaVista(negativador, parameters, isHeaderOuTrailler);


			}catch(ErroRepositorioException e1){
				e1.printStackTrace();
			}

		}

		// --------------------------------------------------------------------------------------------
	}

	/**
	 * [UC0672] Registrar Movimento de Retorno dos Negativadores
	 * [SB0005 - Atualizar Registro de Envio].
	 * 
	 * @author Yara Taciane
	 * @date
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	private void atualizarRegistroEnvioSPCBoaVista(Negativador negativador, Map parameters, boolean isHeaderOuTrailler)
					throws ControladorException,
					ErroRepositorioException{

		this.atualizarCodigoRetornoRegistroSPCBoaVista(negativador, parameters, isHeaderOuTrailler);


	}

	/**
	 * GERA REGISTRO HEADER para SPC Boa Vista - V04
	 * CAMPOS CONFORME LAYOUT DO UC0673 [SB0004]
	 * 
	 * @author Luciano Galvão
	 * @date 21/02/2015
	 */
	public StringBuilder geraRegistroTipoHeaderSPCBoaVista(int saEnvio) throws ControladorException{

		StringBuilder registroHeader = new StringBuilder();
		// H.01 [001-008] - codigo do assocido
		String associado = ConstantesAplicacao.get("spc_boa_vista_codigo_associado");
		registroHeader.append(associado);
		// H.02 [009-018] - 0000000000
		String constante = ConstantesAplicacao.get("constante_zeros");
		registroHeader.append(constante);
		// H.03 [019-024] - data corrente
		String dataAtualString = Util.recuperaDiaMesAnoCom2DigitosDaData(new Date());
		registroHeader.append(dataAtualString);
		// H.04 [025-031] - remessa
		String constanteRemessa = ConstantesAplicacao.get("spc_boa_vista_constante_envio");
		registroHeader.append(constanteRemessa);
		// H.05 [025-031] - nome do infromante
		SistemaParametro sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
		registroHeader.append(Util.completaString(sistemaParametro.getNomeEmpresa(), 55));
		// H.06 [087-087] - controle de remessa
		registroHeader.append("0");
		// H.07 [088-094] - número de sequencia
		registroHeader.append(Util.adicionarZerosEsquedaNumero(7, "" + (saEnvio + 1)));
		// H.08 [095-102] - constante brancos
		registroHeader.append(Util.completaString(" ", 8));
		// H.09 [103-104] - versao
		String versao = ConstantesAplicacao.get("spc_boa_vista_versao");
		registroHeader.append(versao);
		// H.10 [105-250] - constante brancos
		registroHeader.append(Util.completaString(" ", 146));

		return registroHeader;
	}

	/**
	 * GERA REGISTRO DETALHE
	 * *****************************************
	 * CAMPOS CONFORME LAYOUT DO UC0673 [SB0005]
	 * ******************************************
	 * 
	 * @author Luciano Galvão
	 * @date 21/02/2015
	 */
	public StringBuilder gerarRegistroTipoDetalheOcorrenciaSPCBoaVista(String conteudoDetalheOcorrencia, int numeroSequencialRegistro)
					throws ControladorException, ErroRepositorioException{

		StringBuilder registroDetalheOcorrenciaSPC = new StringBuilder();

		// D3.01 - [001-008] - associado
		String associado = ConstantesAplicacao.get("spc_boa_vista_codigo_associado");
		registroDetalheOcorrenciaSPC.append(Util.completaString(associado, 8));

		// D3.02 - [009-009] - constante
		String constante = ConstantesAplicacao.get("spc_boa_vista_detalhe_01");
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.03 - [010-014] - sequencial
		registroDetalheOcorrenciaSPC.append(Util.adicionarZerosEsquedaNumero(5, "" + numeroSequencialRegistro));

		// D3.04 - [015-015] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.05 - [016-016] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString(constante, 1));

		// D3.06 - [017-018] - operação
		String operacao = ConstantesAplicacao.get("spc_boa_vista_operacao_exclusao");
		registroDetalheOcorrenciaSPC.append(operacao);

		// D3.07 - [019-038] - documento
		String documento = "";

		if(conteudoDetalheOcorrencia.length() >= 38){
			documento = conteudoDetalheOcorrencia.substring(18, 38);
		}
		registroDetalheOcorrenciaSPC.append(Util.completaString(documento, 20));

		// D3.08 - [039-046] - data ocorrência
		String dataOcorrencia = "";
		if(conteudoDetalheOcorrencia.length() >= 46){
			dataOcorrencia = conteudoDetalheOcorrencia.substring(38, 46);
		}
		registroDetalheOcorrenciaSPC.append(Util.completaString(dataOcorrencia, 8));

		// D3.09 - [047-048] - Ocorrencia
		String ocorrencia = "";
		if(conteudoDetalheOcorrencia.length() >= 48){
			ocorrencia = conteudoDetalheOcorrencia.substring(46, 48);
		}
		registroDetalheOcorrenciaSPC.append(Util.completaString(ocorrencia, 2));

		// D3.10 - [049-070] - Contrato
		String contrato = "";
		if(conteudoDetalheOcorrencia.length() >= 70){
			contrato = conteudoDetalheOcorrencia.substring(48, 70);
		}
		registroDetalheOcorrenciaSPC.append(Util.completaString(contrato, 22));

		// D3.10 - [071-090] - nome avalista
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 20));

		// D3.10 - [091-101] - valor débito
		String valorDebito = "";
		if(conteudoDetalheOcorrencia.length() >= 101){
			valorDebito = conteudoDetalheOcorrencia.substring(90, 101);
		}
		registroDetalheOcorrenciaSPC.append(Util.adicionarZerosEsquedaNumero(11, valorDebito));

		// D3.13 - [102-103] - Documentos de Debitos
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 2));

		// D3.14 - [104-120] - Reservado
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 17));

		// D3.15 - [121-121] - constante
		registroDetalheOcorrenciaSPC.append(ConstantesAplicacao.get("spc_boa_vista_detalhe_04"));

		// D3.16 - [122-123] - constante
		registroDetalheOcorrenciaSPC.append(ConstantesAplicacao.get("spc_boa_vista_detalhe_05"));

		// D3.17 - [124-124] - opcao boleto devedor
		registroDetalheOcorrenciaSPC.append(ConstantesAplicacao.get("spc_boa_vista_detalhe_04"));

		// D3.18 - [125-125] - opcao boleto avalista
		registroDetalheOcorrenciaSPC.append(ConstantesAplicacao.get("spc_boa_vista_detalhe_04"));

		// D3.19 - [126-133] - data vencimento
		String dataVencimento = "";
		if(conteudoDetalheOcorrencia.length() >= 133){
			dataVencimento = conteudoDetalheOcorrencia.substring(125, 133);
		}
		registroDetalheOcorrenciaSPC.append(Util.adicionarZerosEsquedaNumero(8, dataVencimento));

		// D3.20 - [134-144] - valor cobranca
		String valorCobranca = "";
		if(conteudoDetalheOcorrencia.length() >= 101){
			valorCobranca = conteudoDetalheOcorrencia.substring(133, 144);
		}
		registroDetalheOcorrenciaSPC.append(Util.adicionarZerosEsquedaNumero(11, valorCobranca));

		// D3.21 - [145-215] - constante
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 71));

		// D3.22 - [216-230] - reservador
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 15));

		// D3.23 - [231-250] - codigo retorno
		registroDetalheOcorrenciaSPC.append(Util.completaString("", 20));

		return registroDetalheOcorrenciaSPC;
	}

	/**
	 * GERA REGISTRO TRAILLER SPC BOA VISTA
	 * *****************************************
	 * CAMPOS CONFORME LAYOUT DO UC0673 [SB0008]
	 * *****************************************
	 * 
	 * @author Luciano Galvão
	 * @date 21/02/2015
	 */
	public StringBuilder geraRegistroTipoTraillerSPCBoaVista(int quantidadeRegistros) throws ControladorException, ErroRepositorioException{

		StringBuilder registroTrailler = new StringBuilder();

		// T.01 [001-008] - codigo do assocido
		String associado = ConstantesAplicacao.get("spc_boa_vista_codigo_associado");
		registroTrailler.append(associado);
		// T.02 [009-018] - Constante
		String constante = ConstantesAplicacao.get("spc_boa_vista_constante_trailler");
		registroTrailler.append(constante);
		// T.03 [019-024] - data corrente
		String dataAtualString = Util.recuperaDiaMesAnoCom2DigitosDaData(new Date());
		registroTrailler.append(dataAtualString);
		// T.048 [025-250] - constante brancos
		registroTrailler.append(Util.completaString(" ", 226));

		return registroTrailler;
	}

	/**
	 * [UC0672] Registrar
	 * Movimento de Retorno dos Negativadores
	 * [SB0001] - Validar Arquivo de Movimento de Retorno do SPC
	 * 
	 * @author Yara Taciane
	 * @date 10/01/2008
	 * @param negativadorMovimentoRegRetMot
	 * @throws ControladorException
	 *             Tenho que verificar se é spc ou serasa aqui nesse método.
	 */

	private Object[] validarArquivoMovimentoRetornoSPCBoaVista(StringBuilder stringBuilderTxt, Negativador negativador)
					throws ControladorException{

		Object[] retorno = new Object[2];
		Object[] retornoHeader = new Object[2];
		NegativadorMovimento negativadorMovimento = null;
		String registro = "";
		String tipoRegistro = "";

		// ----------------------------------------------------------------
		// [SB0001] - Validar Arquivo de Movimento de Retorno do SPC
		// ----------------------------------------------------------------

		String constanteHeader = ConstantesAplicacao.get("constante_header");
		String constanteTrailler = ConstantesAplicacao.get("spc_boa_vista_constante_trailler");

		int countRegistro = 0;
		Collection collRegistrosLidos = new ArrayList();

		StringTokenizer stk = new StringTokenizer(stringBuilderTxt.toString(), "\n");

		while(stk.hasMoreTokens()){

			// ---------------------------------------------------
			registro = stk.nextToken();

			registro = Util.completaStringComEspacoADireitaCondicaoTamanhoMaximo(registro, 250);

			collRegistrosLidos.add(registro);

			countRegistro = countRegistro + 1;
			// ---------------------------------------------------

			// H.01
			tipoRegistro = getConteudo(9, 10, registro.toCharArray());

			// --------------------------------------------------------------------------------------
			// Verifica Header (Primeira Linha)
			// ---------------------------------------------------------------------------------------
			if(countRegistro == 1 && tipoRegistro.equals(constanteHeader)){
				// só p/ header
				try{
					retornoHeader = this.verificarHeaderSPCBoaVistaRetorno(registro, negativador);

					negativadorMovimento = (NegativadorMovimento) retornoHeader[1];

				}catch(ErroRepositorioException e1){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e1);
				}

			}else{

				if(countRegistro > 1 && tipoRegistro.equals(constanteTrailler)){
					break;
				}
			}

		}

		// ----------------------------------------------------------------------------------------
		if(!tipoRegistro.equals(constanteTrailler)){
			throw new ControladorException("atencao.arquivo_movimento_nao_possui_trailler");
		}

		retorno[0] = collRegistrosLidos;
		retorno[1] = negativadorMovimento;
		return retorno;
		// --------------------------------------------------------------------------------------------
	}

	private void atualizarCodigoRetornoRegistroSPCBoaVista(Negativador negativador, Map parameters, boolean isHeaderOuTrailler)
					throws ControladorException, ErroRepositorioException{


		Collection collNegativadorMovimentoReg = (Collection) parameters.get("collNegaMovimentoReg");
		Collection colecaoRegistros = (Collection) parameters.get("collRegistros");

		Iterator it1 = collNegativadorMovimentoReg.iterator();
		Iterator it2 = colecaoRegistros.iterator();

		Integer codRetorno = null;
		String codigoRetorno = "-1";

	
		Collection collpRegMotivoRetorno = new ArrayList();
		NegativadorMotivoRetornoHelper helper = null;

		while(it1.hasNext() && it2.hasNext()){

			NegativadorMovimentoReg negativadorMovimentoReg = (NegativadorMovimentoReg) it1.next();

			String registro = (String) it2.next();

				if(registro != null && !"".equals(registro)){

					String campoCodigoRetorno = getConteudo(231, 20, registro.toCharArray()).trim();

					if(campoCodigoRetorno.equals(ConstantesAplicacao.get("spc_boa_vista_indicador_retorno_aceito_campo"))){

						codRetorno = Util.converterStringParaInteger(NegativadorRetornoMotivo.OPERACAO_BEM_SUCEDIDA);

						NegativadorRetornoMotivo negativadorRetornoMot = repositorioSpcSerasa.pesquisarNegativadorRetornoMotivo(codRetorno,
										negativador.getId());

						if(negativadorRetornoMot == null){

							throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
						}	
				

						helper = new NegativadorMotivoRetornoHelper();

						helper.setNegativadorMovimentoReg(negativadorMovimentoReg);
						helper.setNegativadorRetornoMot(negativadorRetornoMot);
						
						collpRegMotivoRetorno.add(helper);

					}else{

						for(int j = 0; j < campoCodigoRetorno.length(); j = j + 2){

							codigoRetorno = campoCodigoRetorno.substring(j, j + 2);

							if(!codigoRetorno.equals(NegativadorRetornoMotivo.OPERACAO_BEM_SUCEDIDA)){

								// não aceito

								codRetorno = Util.converterStringParaInteger(codigoRetorno);

								NegativadorRetornoMotivo negativadorRetornoMot = repositorioSpcSerasa.pesquisarNegativadorRetornoMotivo(
												codRetorno, negativador.getId());

								if(negativadorRetornoMot == null){

									throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
								}
								
								helper = new NegativadorMotivoRetornoHelper();

								helper.setNegativadorMovimentoReg(negativadorMovimentoReg);
								helper.setNegativadorRetornoMot(negativadorRetornoMot);

								collpRegMotivoRetorno.add(helper);
								
								this.tratamentoRegistroNaoAceitoSPCBoaVista(negativadorMovimentoReg, negativador);

							}

						}

					}

				}

		}

		this.inserirNegativadorMovimentoRegRetMotColecaoSPCBoaVista(collpRegMotivoRetorno, negativador, isHeaderOuTrailler);

	}


	/**
	 * @param negativadorMovimentoReg
	 * @param negativador
	 * @throws ControladorException
	 */
	private void tratamentoRegistroNaoAceitoSPCBoaVista(NegativadorMovimentoReg negativadorMovimentoReg, Negativador negativador)
					throws ControladorException{

		// [início] - Alteração 05/05/2008 - Indicar Exclusão do imóvel
			// caso a inclusão da
			// negativação não seja aceita.
			// -------------------------------------------------------------------------------------------------
			NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();


			Imovel imovel = negativadorMovimentoReg.getImovel();

			if(imovel != null){
				Integer idComando = null;
				Integer idCronograma = null;
				if(negativadorMovimento.getCobrancaAcaoAtividadeComando() != null){
					idComando = negativadorMovimento.getCobrancaAcaoAtividadeComando().getId();
				}else{
					idCronograma = negativadorMovimento.getCobrancaAcaoAtividadeCronograma().getId();
				}



				try{
				Collection collNegativacaoImovel = repositorioSpcSerasa.pesquisarNegativacaoImovel(imovel.getId(), idCronograma, idComando);

				NegativacaoImovei negativacaoImoveis = (NegativacaoImovei) Util.retonarObjetoDeColecao(collNegativacaoImovel);

					if(negativacaoImoveis != null){
						// 5.1
						short indicadorExcluido = 1;
						negativacaoImoveis.setIndicadorExcluido(indicadorExcluido);

						Date dataExclusao = new Date();
						negativacaoImoveis.setDataExclusao(dataExclusao);

						Date ultimaAlteracao = new Date();
						negativacaoImoveis.setUltimaAlteracao(ultimaAlteracao);

						// indicar a exclusão do imóvel.
						repositorioUtil.atualizar(negativacaoImoveis);

						// escolher cobrança situação
						Integer idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
						if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SERASA)){
							idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SERASA;
						}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_SAO_PAULO)){
							idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_SP;
						}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_BRASIL)){
							idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BRASIL;
					}else if(Integer.valueOf(negativador.getId()).equals(Negativador.NEGATIVADOR_SPC_BOA_VISTA)){
						idCobrancaSituacao = CobrancaSituacao.EM_ANALISE_PARA_NEGATIVACAO_SPC_BOA_VISTA;
						}

						// 5.2
						FiltroImovel filtroImovel = new FiltroImovel();
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovel.getId()));
						filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.COBRANCA_SITUACAO_ID, idCobrancaSituacao));

						Imovel imovelRetorno = (Imovel) Util.retonarObjetoDeColecao(repositorioUtil.pesquisar(filtroImovel,
										Imovel.class.getName()));

						if(imovelRetorno != null){
							imovelRetorno.setCobrancaSituacao(null);
							imovelRetorno.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(imovelRetorno);
						}

						// 5.3
						CobrancaSituacao cobrancaSituacao = new CobrancaSituacao();
						cobrancaSituacao.setId(idCobrancaSituacao);

						ImovelCobrancaSituacao imovelCobrancaSituacao = repositorioSpcSerasa.getImovelCobrancaSituacao(imovel,
										cobrancaSituacao);

						if(imovelCobrancaSituacao != null){
							imovelCobrancaSituacao.setDataRetiradaCobranca(new Date());
							imovelCobrancaSituacao.setUltimaAlteracao(new Date());
							repositorioUtil.atualizar(imovelCobrancaSituacao);
						}
					}
				}catch(ErroRepositorioException e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);
				}
			}


	}
	
	/***
	 * @param collpRegMotivoRetorno
	 * @param negativador
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	private void inserirNegativadorMovimentoRegRetMotColecaoSPCBoaVista(Collection collpRegMotivoRetorno, Negativador negativador,
					boolean isHeaderOuTrailler)
					throws ControladorException, ErroRepositorioException{

		Iterator it = collpRegMotivoRetorno.iterator();

		Collection colecaoHelperRegistroNaoAceito = new ArrayList();
		Collection colecaoHelperRegistroAceito = new ArrayList();

		Short indicadorDeAceito = null;
		
		// Separa as listas
		while(it.hasNext()){
			NegativadorMotivoRetornoHelper nmrHelper = (NegativadorMotivoRetornoHelper) it.next();

			if(nmrHelper.getNegativadorRetornoMot().getIndicadorRegistroAceito().equals(ConstantesSistema.NAO)){
				colecaoHelperRegistroNaoAceito.add(nmrHelper);
			}
			
			if(nmrHelper.getNegativadorRetornoMot().getIndicadorRegistroAceito().equals(ConstantesSistema.SIM)){
				colecaoHelperRegistroAceito.add(nmrHelper);
			}			

		}
		
		// Caso possua registros aceitos e nã aceitos no mesmo conjunto de 3 registros.
		if(!Util.isVazioOrNulo(colecaoHelperRegistroNaoAceito) && !Util.isVazioOrNulo(colecaoHelperRegistroAceito)){
			
			// Registro não aceito.
			// .......................................................................................................
			Iterator iti0 = colecaoHelperRegistroNaoAceito.iterator();

			while(iti0.hasNext()){

				NegativadorMotivoRetornoHelper nmrHelper = (NegativadorMotivoRetornoHelper) iti0.next();

				this.inserirNegativadorMovimentoRegRetMot(nmrHelper.getNegativadorMovimentoReg(), nmrHelper.getNegativadorRetornoMot());

				indicadorDeAceito = ConstantesSistema.NAO;

				verificarRegistroInclusao(nmrHelper.getNegativadorMovimentoReg(), indicadorDeAceito, negativador);

				NegativadorMovimentoReg negativadorMovimentoReg = nmrHelper.getNegativadorMovimentoReg();

				negativadorMovimentoReg.setIndicadorAceito(indicadorDeAceito);
				negativadorMovimentoReg.setUltimaAlteracao(new Date());

				this.getControladorUtil().atualizar(negativadorMovimentoReg);

			}
			// .......................................................................................................

			// Registro aceito com registro não aceito associado.
			// .......................................................................................................
			Iterator iti = colecaoHelperRegistroAceito.iterator();

			while(iti.hasNext()){
				NegativadorMotivoRetornoHelper nmrHelper = (NegativadorMotivoRetornoHelper) iti.next();

				FiltroNegativadorRetornoMotivo fnrm = new FiltroNegativadorRetornoMotivo();
				fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.CODIGO_RETORNO_MOTIVO,
								NegativadorRetornoMotivo.REGISTRO_ASSOCIADO_COM_ERRO_SPC_BOA_VISTA));
				fnrm.adicionarParametro(new ParametroSimples(FiltroNegativadorRetornoMotivo.NEGATIVADOR_RETORNO_MOTIVO_NEGATIVADOR,
								Negativador.NEGATIVADOR_SPC_BOA_VISTA));

				NegativadorRetornoMotivo negativadorRetornoMotx = (NegativadorRetornoMotivo) Util
								.retonarObjetoDeColecao(RepositorioUtilHBM.getInstancia().pesquisar(fnrm,
												NegativadorRetornoMotivo.class.getName()));

				if(negativadorRetornoMotx != null){
					indicadorDeAceito = ConstantesSistema.NAO;

					this.inserirNegativadorMovimentoRegRetMot(nmrHelper.getNegativadorMovimentoReg(), negativadorRetornoMotx);


					verificarRegistroInclusao(nmrHelper.getNegativadorMovimentoReg(), indicadorDeAceito, negativador);

					NegativadorMovimentoReg negativadorMovimentoReg = nmrHelper.getNegativadorMovimentoReg();

					negativadorMovimentoReg.setIndicadorAceito(indicadorDeAceito);
					negativadorMovimentoReg.setUltimaAlteracao(new Date());

					this.getControladorUtil().atualizar(negativadorMovimentoReg);
				}else{
					throw new ControladorException("atencao.arquivo_movimento_codigo_retorno_invalido");
				}


			}
			// .......................................................................................................
			
			
		}else if((!Util.isVazioOrNulo(colecaoHelperRegistroNaoAceito) && colecaoHelperRegistroNaoAceito.size() == 3)
						|| (!Util.isVazioOrNulo(colecaoHelperRegistroNaoAceito) && isHeaderOuTrailler)){
			
			// Todos os 3 registros não aceitos.
			Iterator itii = colecaoHelperRegistroNaoAceito.iterator();

			while(itii.hasNext()){
				NegativadorMotivoRetornoHelper nmrHelper = (NegativadorMotivoRetornoHelper) itii.next();

				this.inserirNegativadorMovimentoRegRetMot(nmrHelper.getNegativadorMovimentoReg(), nmrHelper.getNegativadorRetornoMot());
				
				indicadorDeAceito = ConstantesSistema.NAO;

				verificarRegistroInclusao(nmrHelper.getNegativadorMovimentoReg(), indicadorDeAceito, negativador);

				NegativadorMovimentoReg negativadorMovimentoReg = nmrHelper.getNegativadorMovimentoReg();

				negativadorMovimentoReg.setIndicadorAceito(indicadorDeAceito);
				negativadorMovimentoReg.setUltimaAlteracao(new Date());

				this.getControladorUtil().atualizar(negativadorMovimentoReg);

			}

		}else if((!Util.isVazioOrNulo(colecaoHelperRegistroAceito) && colecaoHelperRegistroAceito.size() == 3)
						|| (!Util.isVazioOrNulo(colecaoHelperRegistroAceito) && isHeaderOuTrailler)){
			
			// Todos os 3 registros aceitos.
			Iterator itiv = colecaoHelperRegistroAceito.iterator();

			while(itiv.hasNext()){
				NegativadorMotivoRetornoHelper nmrHelper = (NegativadorMotivoRetornoHelper) itiv.next();

				this.inserirNegativadorMovimentoRegRetMot(nmrHelper.getNegativadorMovimentoReg(), nmrHelper.getNegativadorRetornoMot());
				
				indicadorDeAceito = ConstantesSistema.SIM;
				verificarRegistroInclusao(nmrHelper.getNegativadorMovimentoReg(), indicadorDeAceito, negativador);

				NegativadorMovimentoReg negativadorMovimentoReg = nmrHelper.getNegativadorMovimentoReg();

				negativadorMovimentoReg.setIndicadorAceito(indicadorDeAceito);
				negativadorMovimentoReg.setUltimaAlteracao(new Date());

				this.getControladorUtil().atualizar(negativadorMovimentoReg);

			}
			
			
		}

	}
	




}

