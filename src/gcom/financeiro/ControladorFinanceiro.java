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

package gcom.financeiro;

import gcom.arrecadacao.*;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoCategoria;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.batch.ControladorBatchLocal;
import gcom.batch.ControladorBatchLocalHome;
import gcom.batch.ProcessoIniciado;
import gcom.batch.UnidadeProcessamento;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaSituacao;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.faturamento.ImpostoTipo;
import gcom.faturamento.bean.GuiaPagamentoCategoriaHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaCategoria;
import gcom.faturamento.conta.ContaImpostosDeduzidos;
import gcom.faturamento.credito.*;
import gcom.faturamento.debito.*;
import gcom.financeiro.bean.AcumularValoresHelper;
import gcom.financeiro.bean.GerarIntegracaoContabilidadeHelper;
import gcom.financeiro.bean.GerarResumoDevedoresDuvidososHelper;
import gcom.financeiro.bean.ResumoDevedoresDuvidososRelatorioHelper;
import gcom.financeiro.lancamento.*;
import gcom.interceptor.ObjetoTransacao;
import gcom.relatorio.financeiro.FiltroRelatorioPosicaoContasAReceberContabil;
import gcom.relatorio.financeiro.RelatorioEvolucaoContasAReceberContabilBean;
import gcom.util.*;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.*;
import java.util.zip.ZipOutputStream;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * Controlador Financeiro PADRÃO
 * 
 * @author Raphael Rossiter
 * @date 26/06/2007
 */
public class ControladorFinanceiro
				implements SessionBean {

	protected static final long serialVersionUID = 1L;

	SessionContext sessionContext;

	protected IRepositorioFinanceiro repositorioFinanceiro = null;

	protected IRepositorioLancamentoItemContabil repositorioLancamentoItemContabil = null;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

		repositorioFinanceiro = RepositorioFinanceiroHBM.getInstancia();
		repositorioLancamentoItemContabil = RepositorioLancamentoItemContabilHBM.getInstancia();

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
	 * Retorna o valor de controladorArrecadacao
	 * 
	 * @return O valor de controladorCliente
	 */
	protected ControladorArrecadacaoLocal getControladorArrecadacao(){

		ControladorArrecadacaoLocalHome localHome = null;
		ControladorArrecadacaoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorArrecadacaoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_ARRECADACAO_SEJB);
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
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

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

	protected ControladorFaturamentoLocal getControladorFaturamento(){

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

	protected ControladorBatchLocal getControladorBatch(){

		ControladorBatchLocalHome localHome = null;
		ControladorBatchLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorBatchLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_BATCH_SEJB);
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
	 * [UC0175] - Gerar Lançamentos Contábeis do Faturamento
	 * Author: Raphael Rossiter, Pedro Alexandre
	 * Data: 16/01/2006, 23/05/2007
	 * Gera os lançamentos contábeis a partir dos dados selecionados na tabela RESUMO_FATURAMENTO
	 * 
	 * @param anoMesReferenciaFaturamento
	 * @param idLocalidade
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarLancamentosContabeisFaturamento(Integer anoMesReferenciaFaturamento, Integer idLocalidade, int idFuncionalidadeIniciada)
					throws ControladorException{

		System.out.println("Localidade " + idLocalidade);

		if(idLocalidade.equals(260) || idLocalidade.equals(544)){
			System.out.print("Parouuuuuu");
		}

		// [FS0001 - Validar ano/mês do Faturamento]
		Integer anoMesFaturamentoAtual = getControladorUtil().pesquisarParametrosDoSistema().getAnoMesFaturamento();
		if(anoMesReferenciaFaturamento.intValue() > anoMesFaturamentoAtual.intValue()){
			// levanta a exceção para a próxima camada
			throw new ControladorException("atencao.mes_ano.faturamento.inferior", null, Util
							.formatarAnoMesParaMesAno(anoMesFaturamentoAtual.toString()));
		}

		int idUnidadeIniciada = 0;

		/*
		 * Registrar o início do processamento da Unidade de
		 * Processamento do Batch
		 */
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, (idLocalidade));

		try{

			/*
			 * Pesquisa os dados do resumo do faturamento para o ano/mês de referência atual e
			 * para a localidade informada.
			 * 0 - id da localidade
			 * 1 - id do tipo de lançamento
			 * 2 - id do item de lançamento
			 * 3 - id do item de lançamento contábil
			 * 4 - id da categoria
			 * 5 - soma do valor do resumo do faturamento
			 */
			Collection<Object[]> colecaoDadosResumoFaturamento = repositorioFinanceiro.obterDadosResumoFaturamento(
							anoMesReferenciaFaturamento, idLocalidade);

			/*
			 * Caso exista resumo de faturamento para a localidade e o ano/mês
			 * cria o lancamento contábil junto com seus items
			 * para cada conjunto de mesmo tipo de lançamento
			 */
			if(colecaoDadosResumoFaturamento != null && !colecaoDadosResumoFaturamento.isEmpty()){

				// flag utilizada somente quando for a primeira entrada
				boolean flagPrimeiraVez = true;
				int idTipoLancamentoTemp = -1;
				Collection<Object[]> colecaoDadosResumoPorTipoLancamento = new ArrayList();

				// definição da origem do lançamento
				LancamentoOrigem lancamentoOrigem = new LancamentoOrigem();
				lancamentoOrigem.setId(LancamentoOrigem.FATURAMENTO);

				// Cria a variável que vai armazenar o lançamento contábil
				LancamentoContabil lancamentoContabilInsert = null;

				// laço para gerar os lançamentos por grupo de tipo de lançamento
				for(Object[] dadosResumoFaturamento : colecaoDadosResumoFaturamento){

					// recupera o tipo de lançamento atual
					Integer idTipoLancamento = (Integer) dadosResumoFaturamento[1];

					/*
					 * Caso seja a primeira entrada do "for"
					 * adiciona os dados a coleção e atualiza o item temporario
					 * criando também o lançamento contabil que ira ser inserindo
					 * junto com seus items.
					 * Caso contrário (não seja a primeira entrada do laço "for")
					 * verifica se o item de lançamento mudou, caso não tenha mudado
					 * adiciona os dados ao conjunto do mesmo item
					 * caso contrário, se mudou o item de lançamento o conjunto está fechado
					 * para o lançamento contábil e chama o me´todo para inserir o
					 * lançamento contábil junto com seus itens.
					 */
					if(flagPrimeiraVez){
						colecaoDadosResumoPorTipoLancamento.add(dadosResumoFaturamento);
						flagPrimeiraVez = false;
						idTipoLancamentoTemp = idTipoLancamento;

						LancamentoTipo tipoLancamento = new LancamentoTipo();
						tipoLancamento.setId(idTipoLancamento);

						Localidade localidade = new Localidade();
						localidade.setId(idLocalidade);

						// cria o lançamento contábil para ser inserido
						lancamentoContabilInsert = new LancamentoContabil();
						lancamentoContabilInsert.setAnoMes(anoMesReferenciaFaturamento);
						lancamentoContabilInsert.setLancamentoOrigem(lancamentoOrigem);
						lancamentoContabilInsert.setLancamentoTipo(tipoLancamento);
						lancamentoContabilInsert.setLocalidade(localidade);
						lancamentoContabilInsert.setRecebimentoTipo(null);
						lancamentoContabilInsert.setUltimaAlteracao(new Date());
					}else{
						/*
						 * Caso ainda seja o mesmo item adicona os dados para
						 * ser gerado os itens do lançamento para o mesmo lançamento.
						 * Caso contrário chama o metódo para inseri os itens e o lançamento
						 * contábil.
						 */
						if(idTipoLancamento == idTipoLancamentoTemp){
							colecaoDadosResumoPorTipoLancamento.add(dadosResumoFaturamento);
						}else{
							/* metódo para inserir o lançamento contábil assim como seus itens */
							this.inserirLancamentoContabilItemFaturamento(lancamentoContabilInsert, colecaoDadosResumoPorTipoLancamento);

							// limpaa coleção e adiciona os dados do resumo atual
							colecaoDadosResumoPorTipoLancamento.clear();
							colecaoDadosResumoPorTipoLancamento.add(dadosResumoFaturamento);

							LancamentoTipo tipoLancamento = new LancamentoTipo();
							tipoLancamento.setId(idTipoLancamento);

							Localidade localidade = new Localidade();
							localidade.setId(idLocalidade);

							// cria o lançamento contábil que vai ser inserido
							lancamentoContabilInsert = new LancamentoContabil();
							lancamentoContabilInsert.setAnoMes(anoMesReferenciaFaturamento);
							lancamentoContabilInsert.setLancamentoOrigem(lancamentoOrigem);
							lancamentoContabilInsert.setLancamentoTipo(tipoLancamento);
							lancamentoContabilInsert.setLocalidade(localidade);
							lancamentoContabilInsert.setRecebimentoTipo(null);
							lancamentoContabilInsert.setUltimaAlteracao(new Date());

							// atualiza o tipo de lançamento temporário com o novo valor
							idTipoLancamentoTemp = idTipoLancamento;
						}
					}
				}

				/*
				 * Último registro
				 * Esse "if" é para verificar se ainda existe um último registro na coleção
				 * caso exista algum item, adiciona o lançamento contábil junto com o item.
				 */
				if(colecaoDadosResumoPorTipoLancamento != null && colecaoDadosResumoPorTipoLancamento.size() > 0){
					this.inserirLancamentoContabilItemFaturamento(lancamentoContabilInsert, colecaoDadosResumoPorTipoLancamento);
					colecaoDadosResumoPorTipoLancamento = null;
				}
			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			ex.printStackTrace();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(ex);
		}
	}

	/**
	 * Gera o lançamento contábil junto com seus itens.
	 * [UC0175] - Gerar Lançamentos Contábeis do Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 24/05/2007
	 * @param lancamentoContabil
	 * @param colecaoDadosResumoPorTipoLancamento
	 * @throws ControladorException
	 */
	protected void inserirLancamentoContabilItemFaturamento(LancamentoContabil lancamentoContabil,
					Collection<Object[]> colecaoDadosResumoPorTipoLancamento) throws ControladorException{

		try{
			/*
			 * Caso exista dados para os itens do resumo do faturamento.
			 */
			if(colecaoDadosResumoPorTipoLancamento != null && !colecaoDadosResumoPorTipoLancamento.isEmpty()){

				Collection colecaoLancamentoContabilItem = new ArrayList();

				// flag que indica se o lançamento contábil já foi inserido ou não.
				boolean flagInseridoLancamentoContabil = false;

				/*
				 * Dados do resumo do faturamento
				 * 0 - id da localidade
				 * 1 - id do tipo de lançamento
				 * 2 - id do item de lançamento
				 * 3 - id do item de lançamento contábil
				 * 4 - id da categoria
				 * 5 - soma do valor do resumo do faturamento
				 */
				for(Object[] dadosResumoFaturamento : colecaoDadosResumoPorTipoLancamento){

					// recupera os dados do resumo do faturamento
					Integer idLancamentoTipo = (Integer) dadosResumoFaturamento[1];
					Integer idLancamentoItem = (Integer) dadosResumoFaturamento[2];
					Integer idLancamentoItemContabil = (Integer) dadosResumoFaturamento[3];
					Integer idCategoria = (Integer) dadosResumoFaturamento[4];
					BigDecimal valorLancamento = (BigDecimal) dadosResumoFaturamento[5];

					/*
					 * Verifica se existe conta contábil para o item que vai ser inserido
					 * 0 - id conta contábil do débito
					 * 1 - id conta contábil crédito
					 * 2 - descrição do histórico do débito
					 * 3 - descrição do histórico do crédito
					 */
					Object[] dadosContaContabil = repositorioFinanceiro.obterParametrosContabilFaturamento(idCategoria,
									idLancamentoItemContabil, idLancamentoItem, idLancamentoTipo);

					if(dadosContaContabil != null){
						Integer idLancamentoContabil = null;
						/*
						 * Caso exista dados para a conta contábil e o item contábil
						 * ainda não foi inserido
						 * inseri o item contábil na base.
						 */
						if(!flagInseridoLancamentoContabil){
							idLancamentoContabil = (Integer) getControladorUtil().inserir(lancamentoContabil);
							lancamentoContabil.setId(idLancamentoContabil);
							flagInseridoLancamentoContabil = true;
						}

						// recupera os dados da conta contábil para o item do resumo do faturamento.
						Integer idContaContabilDebito = (Integer) dadosContaContabil[0];
						Integer idContaContabilCredito = (Integer) dadosContaContabil[1];
						String descricaoHistoricoDebito = (String) dadosContaContabil[2];
						String descricaoHistoricoCredito = (String) dadosContaContabil[3];

						// cria os indicadores de débito e crédito.
						Short indicadorDebito = new Short("2");
						Short indicadorCredito = new Short("1");

						Date ultimaAlteracao = new Date();

						// cria as contas contábeis de crédito e débito.
						ContaContabil contaContabilCredito = new ContaContabil();
						contaContabilCredito.setId(idContaContabilCredito);

						ContaContabil contaContabilDebito = new ContaContabil();
						contaContabilDebito.setId(idContaContabilDebito);

						/** Item de crédito */
						LancamentoContabilItem lancamentoContabilItemCredito = new LancamentoContabilItem(indicadorCredito,
										valorLancamento, descricaoHistoricoCredito, ultimaAlteracao, lancamentoContabil,
										contaContabilCredito);

						colecaoLancamentoContabilItem.add(lancamentoContabilItemCredito);

						/** Item de débito */
						LancamentoContabilItem lancamentoContabilItemDebito = new LancamentoContabilItem(indicadorDebito, valorLancamento,
										descricaoHistoricoDebito, ultimaAlteracao, lancamentoContabil, contaContabilDebito);

						colecaoLancamentoContabilItem.add(lancamentoContabilItemDebito);

					}
				}
				// inserios itens de lançamento contábeis.
				getControladorBatch().inserirColecaoObjetoParaBatch(colecaoLancamentoContabilItem);
			}

		}catch(Exception ex){
			throw new EJBException(ex);
		}
	}

	public void inserirLancamentoContabilItem(LancamentoContabil lancamentoContabil, int etapa, BigDecimal valorTotalResidencial,
					BigDecimal valorTotalComercial, BigDecimal valorTotalIndustrial, BigDecimal valorTotalPublico,
					Collection<AcumularValoresHelper> colecaoAcumularValoresPorLancamentoItemContabil,
					Collection<AcumularValoresHelper> colecaoAcumularValoresPorLancamentoItemCategoria) throws ControladorException{

		LancamentoContabilItem lancamentoContabilItem = new LancamentoContabilItem();
		lancamentoContabilItem.setLancamentoContabil(lancamentoContabil);

		// Utilizado dentro do switch
		LancamentoItem lancamentoItem = new LancamentoItem();
		Categoria categoria = new Categoria();

		switch(lancamentoContabil.getLancamentoTipo().getId().intValue()){

			case LancamentoTipo.AGUA_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 31 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("111"),
									valorTotalResidencial, null, null, null, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 31 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = COMERCIAL
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("112"), null,
									valorTotalComercial, null, null, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 31 e Conta Contábil = 113
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("113"), null,
									null, valorTotalIndustrial, null, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 31 e Conta Contábil = 114
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("114"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);
				}

				break;

			case LancamentoTipo.ESGOTO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 31 e Conta Contábil = 211
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("211"),
									valorTotalResidencial, null, null, null, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 31 e Conta Contábil = 212
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = COMERCIAL
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("212"), null,
									valorTotalComercial, null, null, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 31 e Conta Contábil = 213
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("213"), null,
									null, valorTotalIndustrial, null, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 31 e Conta Contábil = 214
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("31"), new Integer("214"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.FINANCIAMENTOS_INCLUIDOS_CURTO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("151"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("152"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else{

					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.FINANCIAMENTOS_INCLUIDOS_LONGO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 13 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("151"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 13 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("152"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else{

					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.FATURAMENTO_ADICIONAL_GUIA_PAGAMENTO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else{

					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.FINANCIAMENTOS_CANCELADOS_CURTO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("151"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 12 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("152"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.FINANCIAMENTOS_CANCELADOS_LONGO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 13 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("151"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 13 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("152"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.CANCELAMENTOS_POR_REFATURAMENTO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 31 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 31 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + COMERCIAL
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 31 e Conta Contábil = 113
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("113"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 31 e Conta Contábil = 114
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("114"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL + COMERCIAL +
				 * INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 6){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 7, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 07
				 * Razão Contábil = 31 e Conta Contábil = 211
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL
				 */
				else if(etapa == 7){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("211"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 8, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 08
				 * Razão Contábil = 31 e Conta Contábil = 212
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + COMERCIAL
				 */
				else if(etapa == 8){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("212"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 9, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 09
				 * Razão Contábil = 31 e Conta Contábil = 213
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + INDUSTRIAL
				 */
				else if(etapa == 9){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("213"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 10, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 10
				 * Razão Contábil = 31 e Conta Contábil = 214
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 10){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("214"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 11, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 11
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL + COMERCIAL +
				 * INDUSTRIAL
				 */
				else if(etapa == 11){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 12, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 12
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 12){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 13, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 13
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else if(etapa == 13){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 14, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 14
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 14){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 15, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 15
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);
				}

				break;

			case LancamentoTipo.PARCELAMENTOS_COBRADOS_SUP_CANCELAMENTOS_POR_REFATURAMENTO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 31 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 31 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + COMERCIAL
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 31 e Conta Contábil = 113
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("113"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 31 e Conta Contábil = 114
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("114"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL + COMERCIAL +
				 * INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 6){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 7, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 07
				 * Razão Contábil = 31 e Conta Contábil = 211
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL
				 */
				else if(etapa == 7){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("211"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 8, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 08
				 * Razão Contábil = 31 e Conta Contábil = 212
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + COMERCIAL
				 */
				else if(etapa == 8){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("212"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 9, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 10
				 * Razão Contábil = 31 e Conta Contábil = 214
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 10){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("214"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 11, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 11
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL + COMERCIAL +
				 * INDUSTRIAL
				 */
				else if(etapa == 11){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 12, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 12
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 12){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 13, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 13
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else if(etapa == 13){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 14, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 14
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 14){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 15, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 15
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 15){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 16, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 16
				 * Razão Contábil = 31 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS)
				 */
				else if(etapa == 16){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);

					this.inserirLancamentoContabilItemAcumulandoItemEspecifico(lancamentoContabil, new Short("31"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 17, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 17
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS) + RESIDENCIAL + COMERCIAL +
				 * INDUSTRIAL
				 */
				else if(etapa == 17){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 18, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 18
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS) + PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.INCLUSOES_POR_REFATURAMENTO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL + COMERCIAL +
				 * INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 31 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 31 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + COMERCIAL
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 31 e Conta Contábil = 113
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("113"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 31 e Conta Contábil = 114
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 6){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("114"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 7, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 07
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL + COMERCIAL +
				 * INDUSTRIAL
				 */
				else if(etapa == 7){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 8, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 08
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 8){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 9, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 09
				 * Razão Contábil = 31 e Conta Contábil = 211
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL
				 */
				else if(etapa == 9){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("211"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 10, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 10
				 * Razão Contábil = 31 e Conta Contábil = 212
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + COMERCIAL
				 */
				else if(etapa == 10){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("212"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 11, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 11
				 * Razão Contábil = 31 e Conta Contábil = 213
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + INDUSTRIAL
				 */
				else if(etapa == 11){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("213"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 12, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 12
				 * Razão Contábil = 31 e Conta Contábil = 214
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 12){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("214"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 13, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 13
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 13){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 14, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 14
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 14){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 15, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 15
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.FINANCIAMENTOS_COBRADOS_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 12 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("151"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 12 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("152"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.FINANCIAMENTOS_TRANSFERIDOS_CURTO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("151"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("152"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 13 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("151"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 13 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("152"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.PARCELAMENTOS_REALIZADOS_CURTO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS) + RESIDENCIAL +
				 * COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS) + PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS) + RESIDENCIAL +
				 * COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS) + PUBLICO
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 6){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 7, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 07
				 * Razão Contábil = 12 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 7){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("151"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 8, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 08
				 * Razão Contábil = 12 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 8){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("152"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 9, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 09
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 9){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 10, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 10
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 10){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 11, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 11
				 * Razão Contábil = 13 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 11){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("151"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 12, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 12
				 * Razão Contábil = 13 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 12){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("152"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 13, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 13
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_LONGO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 13){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 14, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 14
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 14){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 15, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 15
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_LONGO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 15){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 16, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 16
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 16){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 17, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 17
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_COBRADOS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 17){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_COBRADOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 18, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 18
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_COBRADOS) + PUBLICO
				 */
				else if(etapa == 18){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_COBRADOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 19, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 19
				 * Razão Contábil = 31 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_COBRADOS)
				 */
				else if(etapa == 19){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_COBRADOS);

					this.inserirLancamentoContabilItemAcumulandoItemEspecifico(lancamentoContabil, new Short("31"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 20, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 20
				 * Razão Contábil = 31 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_CANCELADOS)
				 */
				else if(etapa == 20){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_CANCELADOS);

					this.inserirLancamentoContabilItemAcumulandoItemEspecifico(lancamentoContabil, new Short("31"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 21, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 21
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_CANCELADOS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 21){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_CANCELADOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 22, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 23
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_CANCELADOS) + PUBLICO
				 */
				else if(etapa == 22){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_CANCELADOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 23, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 23
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DEBITOS_ANTERIORES_PARA_RECOBRANCA)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 23){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DEBITOS_ANTERIORES_PARA_RECOBRANCA);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 24, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 24
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DEBITOS_ANTERIORES_PARA_RECOBRANCA) +
				 * PUBLICO
				 */
				else if(etapa == 24){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DEBITOS_ANTERIORES_PARA_RECOBRANCA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 25, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 25
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DEBITOS_ANTERIORES_PARA_RECOBRANCA)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 25){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DEBITOS_ANTERIORES_PARA_RECOBRANCA);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 26, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 26
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DEBITOS_ANTERIORES_PARA_RECOBRANCA) +
				 * PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DEBITOS_ANTERIORES_PARA_RECOBRANCA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.PARCELAMENTOS_REALIZADOS_LONGO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS) + PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(DOCUMENTOS_EMITIDOS) + PUBLICO
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.DOCUMENTOS_EMITIDOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 6){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 7, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 07
				 * Razão Contábil = 12 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 7){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("151"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 8, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 08
				 * Razão Contábil = 12 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 8){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("152"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 9, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 09
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 9){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 10, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 10
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 10){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 11, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 11
				 * Razão Contábil = 13 e Conta Contábil = 151
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 11){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("151"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 12, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 12
				 * Razão Contábil = 13 e Conta Contábil = 152
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 12){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("152"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 13, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 13
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_CURTO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 13){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 14, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 14
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 14){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 15, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 15
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_CURTO_PRAZO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 15){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 16, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 16
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(PARCELAMENTO_A_COBRAR_LONGO_PRAZO) +
				 * PUBLICO
				 */
				else if(etapa == 16){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 17, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 17
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_COBRADOS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 17){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_COBRADOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 18, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 18
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_COBRADOS) + PUBLICO
				 */
				else if(etapa == 18){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_COBRADOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 19, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 19
				 * Razão Contábil = 31 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_COBRADOS)
				 */
				else if(etapa == 19){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_COBRADOS);

					this.inserirLancamentoContabilItemAcumulandoItemEspecifico(lancamentoContabil, new Short("31"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 20, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 20
				 * Razão Contábil = 31 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_CANCELADOS)
				 */
				else if(etapa == 20){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_CANCELADOS);

					this.inserirLancamentoContabilItemAcumulandoItemEspecifico(lancamentoContabil, new Short("31"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 21, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 21
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_CANCELADOS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 21){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_CANCELADOS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 22, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 22
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS_CANCELADOS) + PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS_COBRADOS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.PARCELAMENTOS_CANCELADOS_CURTO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 31 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 31 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + COMERCIAL
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 31 e Conta Contábil = 113
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("113"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 31 e Conta Contábil = 114
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("114"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 6){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 7, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 07
				 * Razão Contábil = 31 e Conta Contábil = 211
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL
				 */
				else if(etapa == 7){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("211"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 8, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 08
				 * Razão Contábil = 31 e Conta Contábil = 212
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + COMERCIAL
				 */
				else if(etapa == 8){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("212"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 9, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 09
				 * Razão Contábil = 31 e Conta Contábil = 213
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + INDUSTRIAL
				 */
				else if(etapa == 9){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("213"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 10, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 10
				 * Razão Contábil = 31 e Conta Contábil = 214
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 10){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("214"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 11, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 11
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 11){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 12, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 12
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 12){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 13, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 13
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else if(etapa == 13){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 14, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);

				}

				/*
				 * ETAPA Nº 14
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 14){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("121"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 15, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 15
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 15){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("122"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 16, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 16
				 * Razão Contábil = 31 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS)
				 */
				else if(etapa == 16){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);

					this.inserirLancamentoContabilItemAcumulandoItemEspecifico(lancamentoContabil, new Short("31"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 17, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 17
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 17){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 18, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 18
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS) + PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("12"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.PARCELAMENTOS_CANCELADOS_LONGO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 31 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + RESIDENCIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("111"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 31 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + COMERCIAL
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("112"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 31 e Conta Contábil = 113
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + INSDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("113"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 31 e Conta Contábil = 114
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 4){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("114"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 5, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 05
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 5){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 6, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 06
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(AGUA) + PUBLICO
				 */
				else if(etapa == 6){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.AGUA);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 7, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 07
				 * Razão Contábil = 31 e Conta Contábil = 211
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + RESIDENCIAL
				 */
				else if(etapa == 7){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("211"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 8, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 08
				 * Razão Contábil = 31 e Conta Contábil = 212
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + COMERCIAL
				 */
				else if(etapa == 8){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.COMERCIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("212"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 9, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 09
				 * Razão Contábil = 31 e Conta Contábil = 213
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + INDUSTRIAL
				 */
				else if(etapa == 9){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("213"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 10, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 10
				 * Razão Contábil = 31 e Conta Contábil = 214
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 10){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("31"), new Integer("214"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 11, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 11
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 11){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 12, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 12
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(ESGOTO) + PUBLICO
				 */
				else if(etapa == 12){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.ESGOTO);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 13, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 13
				 * contaContabil = CNCT_ID da tabela LANCAMENTO_ITEM_CONTABIL com LICT_ID = id do
				 * ítem de lançamento contábil
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = Valor acumulado para cada ítem de lançamento contábil
				 */
				else if(etapa == 13){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoItems(lancamentoContabil, colecaoAcumularValoresPorLancamentoItemContabil,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 14, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);

				}

				/*
				 * ETAPA Nº 14
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 14){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("121"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 15, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 15
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 15){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("122"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 16, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 16
				 * Razão Contábil = 31 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS)
				 */
				else if(etapa == 16){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);

					this.inserirLancamentoContabilItemAcumulandoItemEspecifico(lancamentoContabil, new Short("31"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 17, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 17
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS)
				 * + RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 17){

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);
					categoria.setId(Categoria.RESIDENCIAL);

					// Para os casos que trabalham com mais de uma categoria
					Categoria categoriaAuxilar1 = new Categoria();
					categoriaAuxilar1.setId(Categoria.COMERCIAL);

					Categoria categoriaAuxilar2 = new Categoria();
					categoriaAuxilar2.setId(Categoria.INDUSTRIAL);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("121"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, categoriaAuxilar1,
									categoriaAuxilar2, ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 18, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 18
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = LANCAMENTO_ITEM(JUROS) + PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					lancamentoItem.setId(LancamentoItem.JUROS);
					categoria.setId(Categoria.PUBLICO);

					this.inserirLancamentoContabilItemAcumulandoItemsCategorias(lancamentoContabil, new Short("13"), new Integer("122"),
									colecaoAcumularValoresPorLancamentoItemCategoria, lancamentoItem, categoria, null, null,
									ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.PARCELAMENTOS_COBRADOS_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("121"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("122"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.PARCELAMENTOS_TRASFERIDOS_PARA_CURTO_PRAZO_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("121"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("122"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 13 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("121"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 13 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("13"), new Integer("122"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			case LancamentoTipo.DEBITOS_ANTERIORES_COBRADOS_INT:

				/*
				 * ETAPA Nº 01
				 * Razão Contábil = 12 e Conta Contábil = 111
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				if(etapa == 1){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("111"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 2, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 02
				 * Razão Contábil = 12 e Conta Contábil = 112
				 * Indicador Débito/Crédito = Débito (2)
				 * ValorItemFaturamento = PUBLICO
				 */
				else if(etapa == 2){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("112"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_DEBITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 3, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 03
				 * Razão Contábil = 12 e Conta Contábil = 121
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = RESIDENCIAL + COMERCIAL + INDUSTRIAL
				 */
				else if(etapa == 3){

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("121"),
									valorTotalResidencial, valorTotalComercial, valorTotalIndustrial, null,
									ConstantesSistema.INDICADOR_CREDITO);

					// Direciona para próxima ETAPA
					this.inserirLancamentoContabilItem(lancamentoContabil, 4, valorTotalResidencial, valorTotalComercial,
									valorTotalIndustrial, valorTotalPublico, colecaoAcumularValoresPorLancamentoItemContabil,
									colecaoAcumularValoresPorLancamentoItemCategoria);
				}

				/*
				 * ETAPA Nº 04
				 * Razão Contábil = 12 e Conta Contábil = 122
				 * Indicador Débito/Crédito = Crédito (1)
				 * ValorItemFaturamento = PUBLICO
				 */
				else{

					// Inseri o registro de acordo com os dados passados
					this.inserirLancamentoContabilItemAcumulandoCategoria(lancamentoContabil, new Short("12"), new Integer("122"), null,
									null, null, valorTotalPublico, ConstantesSistema.INDICADOR_CREDITO);

				}

				break;

			default:

				break;
		}
	}

	/**
	 * [UC00175] - Gerar Lançamentos Contábeis
	 * Author: Raphael Rossiter
	 * Data: 17/01/2006
	 * Auxilia no cadastramento de um objeto do tipo LANCAMENTO_CONTABIL_ITEM quando a situação
	 * exige que o valor do faturamento seja acumulado por CATEGORIA.
	 * 
	 * @param lancamentoContabil
	 * @param numeroRazao
	 * @param numeroConta
	 * @param valorTotalResidencial
	 * @param valorTotalComercial
	 * @param valorTotalIndustrial
	 * @param valorTotalPublico
	 * @param indicadorDebitoCredito
	 * @throws ControladorException
	 */
	protected void inserirLancamentoContabilItemAcumulandoCategoria(LancamentoContabil lancamentoContabil, Short numeroRazao,
					Integer numeroConta, BigDecimal valorTotalResidencial, BigDecimal valorTotalComercial, BigDecimal valorTotalIndustrial,
					BigDecimal valorTotalPublico, Short indicadorDebitoCredito) throws ControladorException{

		LancamentoContabilItem lancamentoContabilItem = new LancamentoContabilItem();
		lancamentoContabilItem.setLancamentoContabil(lancamentoContabil);

		ContaContabil contaContabil = null;
		BigDecimal valorItemFaturamento = new BigDecimal("0.00");

		try{

			contaContabil = repositorioFinanceiro.obterContaContabil(numeroRazao, numeroConta);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		if(valorTotalResidencial != null){
			valorItemFaturamento = valorItemFaturamento.add(valorTotalResidencial);
		}

		if(valorTotalComercial != null){
			valorItemFaturamento = valorItemFaturamento.add(valorTotalComercial);
		}

		if(valorTotalIndustrial != null){
			valorItemFaturamento = valorItemFaturamento.add(valorTotalIndustrial);
		}

		if(valorTotalPublico != null){
			valorItemFaturamento = valorItemFaturamento.add(valorTotalPublico);
		}

		lancamentoContabilItem.setContaContabil(contaContabil);
		lancamentoContabilItem.setValorLancamento(valorItemFaturamento);
		lancamentoContabilItem.setIndicadorDebitoCredito(indicadorDebitoCredito);

		this.getControladorUtil().inserir(lancamentoContabilItem);

	}

	/**
	 * [UC00175] - Gerar Lançamentos Contábeis
	 * Author: Raphael Rossiter
	 * Data: 17/01/2006
	 * Auxilia no cadastramento de um ou vários objetos do tipo LANCAMENTO_CONTABIL_ITEM quando a
	 * situação
	 * exige que o valor do faturamento seja acumulado por LANCAMENTO_ITEM_CONTABIL.
	 * 
	 * @param lancamentoContabil
	 * @param colecaoAcumularValoresPorLancamentoItemContabil
	 * @param indicadorCreditoDebito
	 * @throws ControladorException
	 */
	protected void inserirLancamentoContabilItemAcumulandoItems(LancamentoContabil lancamentoContabil,
					Collection<AcumularValoresHelper> colecaoAcumularValoresPorLancamentoItemContabil, Short indicadorDebitoCredito)
					throws ControladorException{

		LancamentoContabilItem lancamentoContabilItem = new LancamentoContabilItem();
		lancamentoContabilItem.setLancamentoContabil(lancamentoContabil);

		ContaContabil contaContabil = null;

		Iterator colecaoAcumularValoresPorLancamentoItemContabilIt = colecaoAcumularValoresPorLancamentoItemContabil.iterator();

		AcumularValoresHelper acumularValoresPorLancamentoItemContabil = null;

		LancamentoItemContabil lancamentoItemContabil = new LancamentoItemContabil();

		while(colecaoAcumularValoresPorLancamentoItemContabilIt.hasNext()){

			acumularValoresPorLancamentoItemContabil = (AcumularValoresHelper) colecaoAcumularValoresPorLancamentoItemContabilIt.next();

			lancamentoItemContabil.setId(acumularValoresPorLancamentoItemContabil.getIdLancamentoItemContabil());

			try{

				contaContabil = repositorioFinanceiro.obterContaContabil(lancamentoItemContabil);

			}catch(ErroRepositorioException ex){
				sessionContext.setRollbackOnly();
				throw new ControladorException("erro.sistema", ex);
			}

			lancamentoContabilItem.setContaContabil(contaContabil);
			lancamentoContabilItem.setValorLancamento(acumularValoresPorLancamentoItemContabil.getValorItemFaturamento());
			lancamentoContabilItem.setIndicadorDebitoCredito(indicadorDebitoCredito);

			this.getControladorUtil().inserir(lancamentoContabilItem);
		}
	}

	/**
	 * @param lancamentoContabil
	 * @param numeroRazao
	 * @param numeroConta
	 * @param colecaoAcumularValoresPorLancamentoItemContabilCategoria
	 * @param lancamentoItem
	 * @param categoria1
	 * @param categoria2
	 * @param categoria3
	 * @param indicadorDebitoCredito
	 * @throws ControladorException
	 */
	protected void inserirLancamentoContabilItemAcumulandoItemsCategorias(LancamentoContabil lancamentoContabil, Short numeroRazao,
					Integer numeroConta, Collection<AcumularValoresHelper> colecaoAcumularValoresPorLancamentoItemContabilCategoria,
					LancamentoItem lancamentoItem, Categoria categoria1, Categoria categoria2, Categoria categoria3,
					Short indicadorDebitoCredito) throws ControladorException{

		LancamentoContabilItem lancamentoContabilItem = new LancamentoContabilItem();
		lancamentoContabilItem.setLancamentoContabil(lancamentoContabil);

		ContaContabil contaContabil = null;

		try{

			contaContabil = repositorioFinanceiro.obterContaContabil(numeroRazao, numeroConta);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoAcumularValoresPorLancamentoItemContabilCategoriaIt = colecaoAcumularValoresPorLancamentoItemContabilCategoria
						.iterator();

		AcumularValoresHelper acumularValoresPorLancamentoItemContabilCategoria = null;

		// Para os casos que trabalham com mais de uma categoria
		BigDecimal valorAcumuladoLancamentoItemCategoria = new BigDecimal("0.00");

		while(colecaoAcumularValoresPorLancamentoItemContabilCategoriaIt.hasNext()){

			acumularValoresPorLancamentoItemContabilCategoria = (AcumularValoresHelper) colecaoAcumularValoresPorLancamentoItemContabilCategoriaIt
							.next();

			// Para acumular com três categorias
			if(categoria2 != null && categoria3 != null){

				if(acumularValoresPorLancamentoItemContabilCategoria.getIdLancamentoItem().equals(lancamentoItem.getId())
								&& (acumularValoresPorLancamentoItemContabilCategoria.getIdCategoria().equals(categoria1.getId())
												|| acumularValoresPorLancamentoItemContabilCategoria.getIdCategoria().equals(
																categoria2.getId()) || acumularValoresPorLancamentoItemContabilCategoria
												.getIdCategoria().equals(categoria3.getId()))){

					valorAcumuladoLancamentoItemCategoria.add(acumularValoresPorLancamentoItemContabilCategoria.getValorItemFaturamento());

				}
			}
			// Para acumular com apenas uma categoria
			else{

				if(acumularValoresPorLancamentoItemContabilCategoria.getIdLancamentoItem().equals(lancamentoItem.getId())
								&& acumularValoresPorLancamentoItemContabilCategoria.getIdCategoria().equals(categoria1.getId())){

					lancamentoContabilItem.setContaContabil(contaContabil);
					lancamentoContabilItem.setValorLancamento(acumularValoresPorLancamentoItemContabilCategoria.getValorItemFaturamento());
					lancamentoContabilItem.setIndicadorDebitoCredito(indicadorDebitoCredito);

					this.getControladorUtil().inserir(lancamentoContabilItem);

					break;

				}
			}
		}

		// Para os casos que trabalham com mais de uma categoria
		if(categoria2 != null && categoria3 != null){

			lancamentoContabilItem.setContaContabil(contaContabil);
			lancamentoContabilItem.setValorLancamento(valorAcumuladoLancamentoItemCategoria);
			lancamentoContabilItem.setIndicadorDebitoCredito(indicadorDebitoCredito);

			this.getControladorUtil().inserir(lancamentoContabilItem);
		}
	}

	protected void inserirLancamentoContabilItemAcumulandoItemEspecifico(LancamentoContabil lancamentoContabil, Short numeroRazao,
					Integer numeroConta, Collection<AcumularValoresHelper> colecaoAcumularValoresPorLancamentoItemContabil,
					LancamentoItem lancamentoItem, Short indicadorDebitoCredito) throws ControladorException{

		LancamentoContabilItem lancamentoContabilItem = new LancamentoContabilItem();
		lancamentoContabilItem.setLancamentoContabil(lancamentoContabil);

		ContaContabil contaContabil = null;

		try{

			contaContabil = repositorioFinanceiro.obterContaContabil(numeroRazao, numeroConta);

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		Iterator colecaoAcumularValoresPorLancamentoItemContabilIt = colecaoAcumularValoresPorLancamentoItemContabil.iterator();

		AcumularValoresHelper acumularValoresPorLancamentoItemContabil = null;

		BigDecimal valorItemFaturamento = new BigDecimal("0.00");

		while(colecaoAcumularValoresPorLancamentoItemContabilIt.hasNext()){

			acumularValoresPorLancamentoItemContabil = (AcumularValoresHelper) colecaoAcumularValoresPorLancamentoItemContabilIt.next();

			if(acumularValoresPorLancamentoItemContabil.getIdLancamentoItem().equals(lancamentoItem.getId())){

				valorItemFaturamento.add(acumularValoresPorLancamentoItemContabil.getValorItemFaturamento());

			}
		}

		if(!valorItemFaturamento.equals(new BigDecimal("0.00"))){

			lancamentoContabilItem.setContaContabil(contaContabil);
			lancamentoContabilItem.setValorLancamento(valorItemFaturamento);
			lancamentoContabilItem.setIndicadorDebitoCredito(indicadorDebitoCredito);

			this.getControladorUtil().inserir(lancamentoContabilItem);

		}

	}

	/**
	 * Pesquisa uma coleção de lançamento de item contábil
	 * 
	 * @return Coleção de Lançamentos de Item Contábil
	 * @exception ErroRepositorioException
	 *                Erro no hibernate
	 */
	public Collection<LancamentoItemContabil> pesquisarLancamentoItemContabil() throws ControladorException{

		try{
			// pesquisa os lançamentos de item contábil existentes no sisitema
			return repositorioLancamentoItemContabil.pesquisarLancamentoItemContabil();

			// erro no hibernate
		}catch(ErroRepositorioException ex){

			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Gera Lançamentos Contábeis do Faturamento
	 * [UC000348] - Gerar Lançamento Contábeis da Arrecadação
	 * 
	 * @author Rafael Santos, Pedro Alexandre
	 * @date 22/05/2006, 25/05/2007
	 * @param anoMesArrecadacao
	 * @throws ControladorException
	 */
	public void gerarLancamentoContabeisArrecadacao(Integer anoMesReferenciaArrecadacao, Integer idLocalidade, int idFuncionalidadeIniciada)
					throws ControladorException{

		int idUnidadeIniciada = 0;

		/*
		 * Registrar o início do processamento da Unidade de
		 * Processamento do Batch
		 */
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, (idLocalidade));

		try{
			Integer anoMesArrecadacaoAtual = getControladorUtil().pesquisarParametrosDoSistema().getAnoMesArrecadacao();
			if(anoMesReferenciaArrecadacao.intValue() > anoMesArrecadacaoAtual.intValue()){
				// levanta a exceção para a próxima camada
				throw new ControladorException("atencao.mes_ano.arrecadacao.inferior", null, Util
								.formatarAnoMesParaMesAno(anoMesArrecadacaoAtual.toString()));
			}

			/*
			 * Pesquisa os dados do resumo da arrecadação para o ano/mês de referência atual e
			 * para a localidade informada.
			 * 0 - id da localidade
			 * 1 - id do tipo de recebimento
			 * 2 - id do tipo de lançamento
			 * 3 - id do item de lançamento
			 * 4 - id do item de lançamento contábil
			 * 5 - id da categoria
			 * 6 - soma do valor do resumo da arrecadação
			 */
			Collection<Object[]> colecaoDadosResumoArrecadacao = repositorioFinanceiro.obterDadosResumoArrecadacao(
							anoMesReferenciaArrecadacao, idLocalidade);

			/*
			 * Caso exista resumo da arrecadação para a localidade e o ano/mês
			 * cria o lançamento contábil junto com seus items
			 * para cada conjunto de mesmo tipo de lançamento
			 */
			if(colecaoDadosResumoArrecadacao != null && !colecaoDadosResumoArrecadacao.isEmpty()){

				// flag utilizada somente quando for a primeira entrada
				boolean flagPrimeiraVez = true;
				int idTipoLancamentoTemp = -1;
				Collection<Object[]> colecaoDadosResumoPorTipoLancamento = new ArrayList();

				// definição da origem do lançamento
				LancamentoOrigem lancamentoOrigem = new LancamentoOrigem();
				lancamentoOrigem.setId(LancamentoOrigem.ARRECADACAO);

				// Cria a variável que vai armazenar o lançamento contábil
				LancamentoContabil lancamentoContabilInsert = null;

				// laço para gerar os lançamentos por grupo de tipo de lançamento
				for(Object[] dadosResumoArrecadacao : colecaoDadosResumoArrecadacao){

					// recupera o id do tipo de recebimento
					Integer idRecebimentoTipo = (Integer) dadosResumoArrecadacao[1];

					// recupera o tipo de lançamento atual
					Integer idTipoLancamento = (Integer) dadosResumoArrecadacao[2];

					/*
					 * Caso seja a primeira entrada do "for"
					 * adiciona os dados a coleção e atualiza o item temporario
					 * criando também o lançamento contabil que ira ser inserindo
					 * junto com seus items.
					 * Caso contrário (não seja a primeira entrada do laço "for")
					 * verifica se o item de lançamento mudou, caso não tenha mudado
					 * adiciona os dados ao conjunto do mesmo item
					 * caso contrário, se mudou o item de lançamento o conjunto está fechado
					 * para o lançamento contábil e chama o método para inserir o
					 * lançamento contábil junto com seus itens.
					 */
					if(flagPrimeiraVez){
						colecaoDadosResumoPorTipoLancamento.add(dadosResumoArrecadacao);
						flagPrimeiraVez = false;
						idTipoLancamentoTemp = idTipoLancamento;

						RecebimentoTipo recebimentoTipo = new RecebimentoTipo();
						recebimentoTipo.setId(idRecebimentoTipo);

						LancamentoTipo tipoLancamento = new LancamentoTipo();
						tipoLancamento.setId(idTipoLancamento);

						Localidade localidade = new Localidade();
						localidade.setId(idLocalidade);

						// cri o lançamento contábil que vai ser inserido
						lancamentoContabilInsert = new LancamentoContabil();
						lancamentoContabilInsert.setAnoMes(anoMesReferenciaArrecadacao);
						lancamentoContabilInsert.setLancamentoOrigem(lancamentoOrigem);
						lancamentoContabilInsert.setLancamentoTipo(tipoLancamento);
						lancamentoContabilInsert.setLocalidade(localidade);
						lancamentoContabilInsert.setRecebimentoTipo(recebimentoTipo);
						lancamentoContabilInsert.setUltimaAlteracao(new Date());
					}else{
						/*
						 * Caso ainda seja o mesmo item adicona os dados para
						 * ser gerado os itens do lançamento para o mesmo lançamento.
						 * Caso contrário chama o metódo para inseri os itens e o lançamento
						 * contábil.
						 */
						if(idTipoLancamento == idTipoLancamentoTemp){
							colecaoDadosResumoPorTipoLancamento.add(dadosResumoArrecadacao);
						}else{
							/* metódo para inserir o lançamento contábil assim como seus itens */
							this.inserirLancamentoContabilItemArrecadacao(lancamentoContabilInsert, colecaoDadosResumoPorTipoLancamento);

							// limpaa coleção e adiciona os dados do resumo atual
							colecaoDadosResumoPorTipoLancamento.clear();
							colecaoDadosResumoPorTipoLancamento.add(dadosResumoArrecadacao);

							RecebimentoTipo recebimentoTipo = new RecebimentoTipo();
							recebimentoTipo.setId(idRecebimentoTipo);

							LancamentoTipo tipoLancamento = new LancamentoTipo();
							tipoLancamento.setId(idTipoLancamento);

							Localidade localidade = new Localidade();
							localidade.setId(idLocalidade);

							// cria o lançamento contábil que vai ser inserido
							lancamentoContabilInsert = new LancamentoContabil();
							lancamentoContabilInsert.setAnoMes(anoMesReferenciaArrecadacao);
							lancamentoContabilInsert.setLancamentoOrigem(lancamentoOrigem);
							lancamentoContabilInsert.setLancamentoTipo(tipoLancamento);
							lancamentoContabilInsert.setLocalidade(localidade);
							lancamentoContabilInsert.setRecebimentoTipo(recebimentoTipo);
							lancamentoContabilInsert.setUltimaAlteracao(new Date());

							// atualiza o tipo de lançamento temporário com o novo valor
							idTipoLancamentoTemp = idTipoLancamento;
						}
					}
				}

				/*
				 * Último registro
				 * Esse "if" é para verificar se ainda existe um último registro na coleção
				 * caso exista algum item, adiciona o lançamento contábil junto com o item.
				 */
				if(colecaoDadosResumoPorTipoLancamento != null && colecaoDadosResumoPorTipoLancamento.size() > 0){
					this.inserirLancamentoContabilItemArrecadacao(lancamentoContabilInsert, colecaoDadosResumoPorTipoLancamento);
					colecaoDadosResumoPorTipoLancamento = null;
				}
			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			ex.printStackTrace();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(ex);
		}
	}

	/**
	 * Este metodo é utilizado para pesquisar os registros q serão
	 * usados para contrução do txt do caso de uso
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @author Pedro Alexandre
	 * @date 28/05/2007
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarGerarIntegracaoContabilidade(String idLancamentoOrigem, String anoMes) throws ControladorException{

		Collection colecaoObjetoGerar = null;
		Collection colecaoGerarIntegracaoContabilidade = null;

		try{

			colecaoObjetoGerar = repositorioFinanceiro.pesquisarGerarIntegracaoContabilidade(idLancamentoOrigem, anoMes);

			if(!colecaoObjetoGerar.isEmpty()){
				Iterator iteratorPesquisa = colecaoObjetoGerar.iterator();

				colecaoGerarIntegracaoContabilidade = new ArrayList();
				GerarIntegracaoContabilidadeHelper gerarIntegracaoContabilidadeHelper = null;
				Object[] objetoGerar = null;

				while(iteratorPesquisa.hasNext()){
					gerarIntegracaoContabilidadeHelper = new GerarIntegracaoContabilidadeHelper();

					objetoGerar = (Object[]) iteratorPesquisa.next();

					// numero cartao
					if(objetoGerar[0] != null){
						gerarIntegracaoContabilidadeHelper.setNumeroCartao((Short) objetoGerar[0]);
					}

					// lancamento tipo
					if(objetoGerar[1] != null){
						gerarIntegracaoContabilidadeHelper.setIdTipoLancamento((Integer) objetoGerar[1]);
					}

					// folha
					if(objetoGerar[2] != null){
						gerarIntegracaoContabilidadeHelper.setFolha((Integer) objetoGerar[2]);
					}

					// linha
					if(objetoGerar[3] != null){
						gerarIntegracaoContabilidadeHelper.setIndicadorLinha((Integer) objetoGerar[3]);
					}

					// prefixo contabil
					if(objetoGerar[4] != null){
						gerarIntegracaoContabilidadeHelper.setNumeroPrefixoContabil((String) objetoGerar[4]);
					}

					// conta
					if(objetoGerar[5] != null){
						gerarIntegracaoContabilidadeHelper.setCont((Integer) objetoGerar[5]);
					}

					// analise
					if(objetoGerar[6] != null){
						gerarIntegracaoContabilidadeHelper.setAnalise((Integer) objetoGerar[6]);
					}

					// digito
					if(objetoGerar[7] != null){
						gerarIntegracaoContabilidadeHelper.setDigito((Integer) objetoGerar[7]);
					}

					// terceiros
					if(objetoGerar[8] != null){
						gerarIntegracaoContabilidadeHelper.setTerceiros((Integer) objetoGerar[8]);
					}

					// referencia
					if(objetoGerar[9] != null){
						gerarIntegracaoContabilidadeHelper.setReferencial((Integer) objetoGerar[9]);
					}

					// valor lancamento
					if(objetoGerar[10] != null){
						gerarIntegracaoContabilidadeHelper.setValorLancamento((BigDecimal) objetoGerar[10]);
					}

					// indicador debito credito
					if(objetoGerar[11] != null){
						gerarIntegracaoContabilidadeHelper.setIndicadorDebitoConta((Integer) objetoGerar[11]);
					}

					// indicador debito credito
					if(objetoGerar[12] != null){
						gerarIntegracaoContabilidadeHelper.setCartao2((Integer) objetoGerar[12]);
					}

					// id localidade
					if(objetoGerar[13] != null){
						gerarIntegracaoContabilidadeHelper.setIdLocalidade((Integer) objetoGerar[13]);
					}

					colecaoGerarIntegracaoContabilidade.add(gerarIntegracaoContabilidadeHelper);
				}
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoGerarIntegracaoContabilidade;
	}

	/**
	 * este caso de uso gera a integração para a contabilidade
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @author Pedro Alexandre
	 * @date 28/05/2007
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @param data
	 * @throws ControladorException
	 */
	public void gerarIntegracaoContabilidade(String idLancamentoOrigem, String anoMes, String data) throws ControladorException{

		/*
		 * Pesquisa os dados para gerar a integração para a contabilidade.
		 * 0 - número do cartão
		 * 1 - código tipo
		 * 2 - número folha
		 * 3 - indicador linha
		 * 4 - prefixo contábil
		 * 5 - número conta
		 * 6 - número dígito
		 * 7 - número terceiros
		 * 8 - código referência
		 * 9 - valor lançamento
		 * 10 - indicador débito crédito
		 * 11 - número cartão 2
		 * 12 - número versão
		 * 13 - id da localidade
		 */
		Collection<Object[]> colecaoDadosGerarIntegracao = null;
		try{
			colecaoDadosGerarIntegracao = repositorioFinanceiro.pesquisarGerarIntegracaoContabilidade(idLancamentoOrigem, anoMes);
		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		/** definição das variáveis */
		StringBuilder gerarIntegracaoTxt = new StringBuilder();
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		String mesDiaInformado = mes + dia;
		String dataFormatada = data.replace("/", "");

		/*
		 * Caso a coleção dos dados não esteja vazia
		 */
		if(colecaoDadosGerarIntegracao != null && !colecaoDadosGerarIntegracao.isEmpty()){

			/** definição das variáveis */
			int sequencial = 1;
			int contadorLinha = 1;
			BigDecimal acumuladorValor = new BigDecimal("0");

			Integer idLocalidadeAnterior = null;
			Short numeroCartao = null;
			Short codigoTipo = null;
			Short numeroFolha = null;
			Short indicadorLinha = null;
			String numeroPrefixoContabil = null;
			String numeroConta = null;
			String numeroDigito = null;
			String numeroTerceiros = null;
			String codigoReferencia = null;
			BigDecimal valorLancamento = null;
			Short indicadorDebitoCredito = null;
			Short numeroCartao2 = null;
			Short numeroVersao = null;
			Integer idLocalidade = null;

			/*
			 * Laço para gerar o txt
			 */
			for(Object[] dadosGerarIntegracao : colecaoDadosGerarIntegracao){

				// número do cartão
				numeroCartao = (Short) dadosGerarIntegracao[0];

				// tipo de código
				codigoTipo = (Short) dadosGerarIntegracao[1];

				// número da folha
				numeroFolha = (Short) dadosGerarIntegracao[2];

				// indicador de linha
				indicadorLinha = (Short) dadosGerarIntegracao[3];

				/*
				 * caso o número do prefixo contábil não esteja vazio recupera
				 * o nº do prefixo.
				 * caso contrário atribui o nº da localidade ao prefixo
				 */
				if(dadosGerarIntegracao[4] != null){
					numeroPrefixoContabil = (String) dadosGerarIntegracao[4];
				}else{
					// localidade
					numeroPrefixoContabil = (Integer) dadosGerarIntegracao[13] + "";
				}

				// número conta
				numeroConta = (String) dadosGerarIntegracao[5];

				// número dígito
				numeroDigito = (String) dadosGerarIntegracao[6];

				// número terceiros
				numeroTerceiros = (String) dadosGerarIntegracao[7];

				// código de referência
				codigoReferencia = (String) dadosGerarIntegracao[8];

				// valor do lançamento
				valorLancamento = (BigDecimal) dadosGerarIntegracao[9];

				// indicador de débito ou crédito
				indicadorDebitoCredito = (Short) dadosGerarIntegracao[10];

				// número do cartão 2
				numeroCartao2 = (Short) dadosGerarIntegracao[11];

				// número da versão
				numeroVersao = (Short) dadosGerarIntegracao[12];

				// código da localidade
				idLocalidade = (Integer) dadosGerarIntegracao[13];

				/*
				 * Inicio da geração do txt
				 */
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, numeroCartao + ""));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(4, mesDiaInformado));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(2, codigoTipo + ""));
				/*
				 * Sequencial - iniciar com 1 e somar 1 a cada vez que LOCA_ID
				 * seja diferente do LOCA_ID anterior
				 */
				if(idLocalidadeAnterior != null && !idLocalidade.equals(idLocalidadeAnterior)){
					sequencial = sequencial + 1;
				}
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, sequencial + ""));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, numeroFolha + ""));
				/*
				 * Linha - Iniciar com 1 se o campo CNCT_ICLINHA da tabela CONTA_CONTABIL
				 * for 1 e somar 1 ao conteudo anterior caso este campo seja igual a 2
				 */
				if(indicadorLinha.equals(ConstantesSistema.SIM)){
					gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(2, "1"));
					contadorLinha = 1;
				}else if(indicadorLinha.equals(ConstantesSistema.NAO)){
					contadorLinha = contadorLinha + 1;
					gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(2, contadorLinha + ""));
				}
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, numeroPrefixoContabil + ""));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(12, numeroConta.trim()));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(1, numeroDigito));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(7, numeroTerceiros));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, codigoReferencia));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(2, mes));
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(13, (valorLancamento + "").replace(".", "")));
				/*
				 * Debito Credito - Igual a C se LCTI_ICDEBITOCREDITO for = 1
				 * e igual a D se for igual a 2
				 */
				if(indicadorDebitoCredito.equals(ConstantesSistema.SIM)){
					gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(1, "C"));
				}else{
					gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(1, "D"));
				}
				gerarIntegracaoTxt.append(Util.completaString("", 21));
				gerarIntegracaoTxt.append(System.getProperty("line.separator"));

				/*
				 * Caso o valor do item seja diferente de nulo
				 * acumula ao valor total.
				 */
				if(valorLancamento != null){
					acumuladorValor = acumuladorValor.add(valorLancamento);
				}

				// atualiza o id da localidade anterior
				idLocalidadeAnterior = idLocalidade;

			}

			/*
			 * Gerar ultima linha do txt
			 */
			// cartão 2
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, numeroCartao2 + ""));
			// filler
			gerarIntegracaoTxt.append(Util.completaString("", 1));
			// data completa
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(8, dataFormatada));
			// número da versão
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(2, numeroVersao + ""));
			// filler
			gerarIntegracaoTxt.append(Util.completaString("", 1));
			// dia lançamento
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(2, dia));
			// filler
			gerarIntegracaoTxt.append(Util.completaString("", 1));
			// número da folha
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, numeroFolha + ""));
			// filler
			gerarIntegracaoTxt.append(Util.completaString("", 1));
			// tipo de lançamento
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(2, codigoTipo + ""));
			// filler
			gerarIntegracaoTxt.append(Util.completaString("", 1));
			// sequêncial
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, sequencial + ""));
			// filler
			gerarIntegracaoTxt.append(Util.completaString("", 1));
			// valor total
			gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(14, (acumuladorValor.toString()).replace(".", "")));
			// filler
			gerarIntegracaoTxt.append(Util.completaString("", 37));

			/*
			 * Determina se o arquivo é de faturamento ou arrecadação
			 * para concatenar no nome do arquivo .zip
			 */
			String descricaoLancamento = "";
			if(idLancamentoOrigem.equals(LancamentoOrigem.FATURAMENTO + "")){
				descricaoLancamento = "FATURAMENTO";
			}else if(idLancamentoOrigem.equals(LancamentoOrigem.ARRECADACAO + "")){
				descricaoLancamento = "ARRECADACAO";
			}else if(idLancamentoOrigem.equals(LancamentoOrigem.DEVEDORES_DUVIDOSOS + "")){
				descricaoLancamento = "DEVEDORES_DUVIDOSOS";
			}

			/*
			 * Gerando o arquivo zip
			 */
			String nomeZip = "CONTABILIDADE_" + descricaoLancamento + "_" + (data.replace("/", "_"));
			BufferedWriter out = null;
			ZipOutputStream zos = null;
			File compactadoTipo = new File(nomeZip + ".zip");
			File leituraTipo = new File(nomeZip + ".txt");

			/*
			 * Caso oarquivo txt não esteja vazio
			 * adiciona o txt ao arquivo zip.
			 */
			if(gerarIntegracaoTxt != null && gerarIntegracaoTxt.length() != 0){
				try{
					System.out.println("CRIANDO ZIP");
					zos = new ZipOutputStream(new FileOutputStream(compactadoTipo));

					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leituraTipo.getAbsolutePath())));
					out.write(gerarIntegracaoTxt.toString());
					out.flush();
					ZipUtil.adicionarArquivo(zos, leituraTipo);
					zos.close();
					leituraTipo.delete();
					out.close();
				}catch(IOException ex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", ex);
				}catch(Exception e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);

				}

			}
			// caso não exista informação para os dados informados
		}else{
			if(idLancamentoOrigem.equals(LancamentoOrigem.FATURAMENTO + "")){
				throw new ControladorException("atencao.pesquisa.nenhum_registro_tabela", null, "Resumo Faturamento");
			}else if(idLancamentoOrigem.equals(LancamentoOrigem.ARRECADACAO + "")){
				throw new ControladorException("atencao.pesquisa.nenhum_registro_tabela", null, "Resumo Arrecadação");
			}else if(idLancamentoOrigem.equals(LancamentoOrigem.DEVEDORES_DUVIDOSOS + "")){
				throw new ControladorException("atencao.pesquisa.nenhum_registro_tabela", null, "Resumo Devedores Duvidosos");
			}
		}
	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Gera o resumo dos devedores duvidosos e marca as contas baixadas contabilmente.
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 06/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarResumoDevedoresDuvidosos(int anoMesReferenciaContabil, Integer idLocalidade, int idFuncionalidadeIniciada)
					throws ControladorException{

		System.out.println("Localidade " + idLocalidade);

		int idUnidadeIniciada = 0;

		/*
		 * Registrar o início do processamento da Unidade de
		 * Processamento do Batch
		 */
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, (idLocalidade));

		try{
			Collection<GerarResumoDevedoresDuvidososHelper> colecaoGerarResumoDevedoresHelperTemp = null;
			List<GerarResumoDevedoresDuvidososHelper> colecaoGerarResumoDevedoresHelperPrincipal = new ArrayList();
			Integer anoMesArrecadacao = null;

			// Recupera os parâmetros dos devedores duvidosos.
			ParametrosDevedoresDuvidosos parametrosDevedoresDuvidosos = repositorioFinanceiro
							.pesquisarParametrosDevedoresDuvidosos(anoMesReferenciaContabil);

			BigDecimal valorLimiteBaixado = parametrosDevedoresDuvidosos.getValorABaixar();
			SistemaParametro sistemaParametro = null;
			Collection<Integer> colecaoIdsContasParaAtualizarAnoMesContabil = new ArrayList();

			BigDecimal valorTotalJaBaixado = parametrosDevedoresDuvidosos.getValorBaixado();

			// 5.4-Caso o valor total dos valores baixados seja maior
			// 5.5-Caso contrário,processar o grupo de contas do próximo imóvel
			if(valorTotalJaBaixado.compareTo(valorLimiteBaixado) != 1){

				// [FS0001] - Verificar existência dos parâmetros
				if(parametrosDevedoresDuvidosos == null){
					throw new ControladorException("atencao.naocadastrado.referencia_contabil");
				}

				// 2-Caso seja um reprocessamento
				if(parametrosDevedoresDuvidosos.getDataProcessamento() != null){

					// o sistema atualiza com o valor nulo o ano/mês referência contábil das contas
					// baixadas
					// contabilmente no ano/mês referência contábil
					repositorioFinanceiro.atualizaContaAnoMesReferenciaContabil(anoMesReferenciaContabil, idLocalidade);

					// exclui o resumo dos devedores duvidosos,referente ao ano/mês referência
					// contábil
					repositorioFinanceiro.removeResumoDevedoresDuvidososPorAnoMesReferenciaContabil(anoMesReferenciaContabil, idLocalidade);
				}

				// 3-Seleciona todas as ocorrências dos itens dos parâmetros para baixa contábil
				sistemaParametro = this.getControladorUtil().pesquisarParametrosDoSistema();
				Collection<ParametrosDevedoresDuvidososItem> colecaoParametrosItem = repositorioFinanceiro
								.pesquisaParametrosDevedoresDuvidososItem(parametrosDevedoresDuvidosos.getId());

				// 4-Seleciona as contas agrupando por imóvel com as seguintes condições:
				// 4.1-Contas que não estejam na categoria publica
				// 4.2-Contas que estejam na situação atual com o valor correspondente a
				// normal,retificada ou incluída
				// 4.3-Contas com ano/mês referência menor que o ano/mês de referência contábil
				// 4.4-Contas ainda não baixadas contabilmente

				BigDecimal valorTotalValoresBaixados = BigDecimal.ZERO;

				Collection<Integer> colecaoIdsQuadras = repositorioFinanceiro.obterQuadrasPorLocalidadeParaGerarResumoDevedoresDuvidosos(
								anoMesReferenciaContabil, idLocalidade);

				if(colecaoIdsQuadras != null && !colecaoIdsQuadras.isEmpty()){

					for(Integer idQuadra : colecaoIdsQuadras){

						HashMap mapContasPorImovel = this.getControladorFaturamento().obterContaAgrupadasPorImovel(
										anoMesReferenciaContabil, idLocalidade, idQuadra);

						if(mapContasPorImovel != null){

							Set chaves = mapContasPorImovel.keySet();
							if(chaves != null && !chaves.isEmpty()){

								anoMesArrecadacao = sistemaParametro.getAnoMesArrecadacao();

								Iterator iteraChaves = chaves.iterator();
								while(iteraChaves.hasNext()){

									Integer chave = (Integer) iteraChaves.next();

									Collection contas = (ArrayList) mapContasPorImovel.get(chave);
									int qtdMesesCalculada = 0;

									// 5-Para cada grupo de contas selecionadas de um imóvel
									// 5.1-Determina quantidade de meses entre o vencimento mais
									// antigo das contas
									Date dataVencimento = this.getControladorFaturamento().determinarMenorDataVencimentoConta(contas);

									String anoMesString = "" + parametrosDevedoresDuvidosos.getAnoMesReferenciaContabil();

									int mes = new Integer(anoMesString.substring(4, 6)).intValue();
									int ano = new Integer(anoMesString.substring(0, 4)).intValue();
									Date dataAnoMes = Util.criarData(1, mes, ano);
									qtdMesesCalculada = Util.dataDiff(dataVencimento, dataAnoMes);

									Iterator iteraContas = contas.iterator();

									BigDecimal valorTotalGrupoContas = BigDecimal.ZERO;
									BigDecimal valorTotalGrupoContasAindaNaoBaixado = BigDecimal.ZERO;
									Imovel imovel = null;

									while(iteraContas.hasNext()){
										Conta conta = (Conta) iteraContas.next();

										if(imovel == null){
											imovel = conta.getImovel();
										}

										// 5.2-Determina o valor total de grupo de contas
										// selecionadas para o imóvel
										valorTotalGrupoContas = valorTotalGrupoContas.add(conta.getValorTotal());

										/*
										 * 5.3 - Determina o valor total do grupo de contas
										 * selecionadas
										 * para o imóvel que ainda não foi baixado contabilmente
										 * para as contas onde o ano/mês de referência contábil for
										 * nulo.
										 */
										if(conta.getReferenciaBaixaContabil() == null){
											valorTotalGrupoContasAindaNaoBaixado = valorTotalGrupoContasAindaNaoBaixado.add(conta
															.getValorTotal());
										}

									}// fim do iteraConta

									CobrancaSituacao cobrancaSituacaoImovel = imovel.getCobrancaSituacao();

									// 5.3-Para cada ocorrência selecionada dos itens dos parâmetros
									// para a baixa contábil:
									if(colecaoParametrosItem != null && !colecaoParametrosItem.isEmpty()){
										Iterator iteraItem = colecaoParametrosItem.iterator();
										loopItemParametros: while(iteraItem.hasNext()){

											ParametrosDevedoresDuvidososItem parametroItem = (ParametrosDevedoresDuvidososItem) iteraItem
															.next();

											int qtdMesItem = parametroItem.getNumeroMeses();
											BigDecimal valorLimiteItem = parametroItem.getValorLimite();
											CobrancaSituacao cobrancaSituacao = parametroItem.getCobrancaSituacao();

											// 5.3.1-Caso a quantidade de meses calculada no passo
											// 5.1 seja maior ou igual a
											// quantidade de meses do item dos parametros e o valor
											// total do grupo de contas
											// calculado no passo 5.2 seja menor ou igual ao valor
											// limite do item de parâmetros
											// e a situação de cobrança do imóvel.
											// 5.3.2-Caso contrário,passar para a próxima ocorrência
											// dos itens do parâmetros
											// para a baixa contábil
											Collection colecaoImovelCobrancaSituacao = null;
											if(cobrancaSituacao != null){
												FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
												filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(
																FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel.getId()));
												filtroImovelCobrancaSituacao
																.adicionarParametro(new ParametroSimples(
																				FiltroImovelCobrancaSituacao.ID_COBRANCA_SITUACAO,
																				cobrancaSituacao.getId()));
												filtroImovelCobrancaSituacao.adicionarParametro(new ParametroNulo(
																FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));

												colecaoImovelCobrancaSituacao = getControladorUtil().pesquisar(
																filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class.getName());
											}
											if(qtdMesesCalculada >= qtdMesItem
															&& valorTotalGrupoContas.compareTo(valorLimiteItem) != 1
															&& (cobrancaSituacao == null || (colecaoImovelCobrancaSituacao != null && !colecaoImovelCobrancaSituacao
																			.isEmpty()))){

												// 5.3.1.1-Acumula o valor total do grupo de contas
												// calculado no passo 5.2
												// no valor total dos valores baixados
												// alterado por pedro alexandre dia 18/07/2007
												// valorTotalValoresBaixados =
												// valorTotalValoresBaixados.add(valorTotalGrupoContas);
												valorTotalValoresBaixados = valorTotalValoresBaixados
																.add(valorTotalGrupoContasAindaNaoBaixado);

												// 5.3.1.2-Atualiza na conta o ano/mês de referencia
												// da baixa contábil para
												// todas as contas do grupo do imóvel
												iteraContas = contas.iterator();
												colecaoIdsContasParaAtualizarAnoMesContabil = new ArrayList();
												while(iteraContas.hasNext()){
													Conta conta = (Conta) iteraContas.next();
													if(conta.getReferenciaBaixaContabil() == null){
														colecaoIdsContasParaAtualizarAnoMesContabil.add(conta.getId());
													}
												}

												if(!colecaoIdsContasParaAtualizarAnoMesContabil.isEmpty()){
													repositorioFinanceiro.atualizaContaAnoMesReferenciaContabil(anoMesReferenciaContabil,
																	colecaoIdsContasParaAtualizarAnoMesContabil);

													colecaoGerarResumoDevedoresHelperTemp = this.acumularResumoDevedoresDuvidosos(
																	anoMesReferenciaContabil, idLocalidade,
																	colecaoIdsContasParaAtualizarAnoMesContabil);
												}
												colecaoIdsContasParaAtualizarAnoMesContabil = null;

												// Caso a coleção temporaria não esteja vazia
												// acumula os registros que estão na mesma quebra
												// e adiciona os novos registros.
												if(colecaoGerarResumoDevedoresHelperTemp != null
																&& !colecaoGerarResumoDevedoresHelperTemp.isEmpty()){

													if(colecaoGerarResumoDevedoresHelperPrincipal.isEmpty()){
														colecaoGerarResumoDevedoresHelperPrincipal
																		.addAll(colecaoGerarResumoDevedoresHelperTemp);
														colecaoGerarResumoDevedoresHelperTemp = null;
													}else{
														for(GerarResumoDevedoresDuvidososHelper gerarResumoDevedoresDuvidososHelperTemp : colecaoGerarResumoDevedoresHelperTemp){
															if(colecaoGerarResumoDevedoresHelperPrincipal
																			.contains(gerarResumoDevedoresDuvidososHelperTemp)){
																int posicao = colecaoGerarResumoDevedoresHelperPrincipal
																				.indexOf(gerarResumoDevedoresDuvidososHelperTemp);

																GerarResumoDevedoresDuvidososHelper jaCadastrado = colecaoGerarResumoDevedoresHelperPrincipal
																				.get(posicao);

																jaCadastrado.setValorBaixado(jaCadastrado.getValorBaixado().add(
																				gerarResumoDevedoresDuvidososHelperTemp.getValorBaixado()));

															}else{
																colecaoGerarResumoDevedoresHelperPrincipal
																				.add(gerarResumoDevedoresDuvidososHelperTemp);
															}
														}
														colecaoGerarResumoDevedoresHelperTemp = null;
													}
												}
												break loopItemParametros;
											}
										}
									}// fim do if colecaoParametrosItem

								}// fim iteraChaves
							}// fim do if chaves != null
						}// fim do map
					}
				}

				Collection colecaoDevedoresDuvidosos = new ArrayList();

				GerenciaRegional gerenciaRegional = new GerenciaRegional();
				Integer idGerenciaRegional = this.getControladorLocalidade().pesquisarIdGerenciaParaLocalidade(idLocalidade);
				gerenciaRegional.setId(idGerenciaRegional);
				Localidade localidade = new Localidade();
				localidade.setId(idLocalidade);

				// 6.Inserir as linhas acumuladas do resumo dos devedores duvidosos
				if(colecaoGerarResumoDevedoresHelperPrincipal != null && !colecaoGerarResumoDevedoresHelperPrincipal.isEmpty()){
					for(GerarResumoDevedoresDuvidososHelper temp : colecaoGerarResumoDevedoresHelperPrincipal){
						// Caso o valor seja maior que zero o resumo vai ser inserido
						// caso contrário passar para o próximo registro.
						if(temp.getValorBaixado().compareTo(BigDecimal.ZERO) == 1){
							LancamentoItem lancamentoItem = null;
							LancamentoTipo lancamentoTipo = null;
							LancamentoItemContabil lancamentoItemContabil = null;
							Categoria categoria = null;

							if(temp.getIdCategoria() != null){
								categoria = new Categoria();
								categoria.setId(temp.getIdCategoria());
							}

							if(temp.getIdLancamentoItem() != null){
								lancamentoItem = new LancamentoItem();
								lancamentoItem.setId(temp.getIdLancamentoItem());
							}

							if(temp.getIdLancamentoTipo() != null){
								lancamentoTipo = new LancamentoTipo();
								lancamentoTipo.setId(temp.getIdLancamentoTipo());
							}

							if(temp.getIdLancamentoItemContabil() != null){
								lancamentoItemContabil = new LancamentoItemContabil();
								lancamentoItemContabil.setId(temp.getIdLancamentoItemContabil());
							}

							ResumoDevedoresDuvidosos resumoDevedoresDuvidosos = new ResumoDevedoresDuvidosos(anoMesReferenciaContabil,
											anoMesArrecadacao, temp.getNumeroSequenciaTipoLancamento(), temp
															.getNumeroSequencialItemTipoLancamento(), temp.getValorBaixado(), new Date(),
											gerenciaRegional, localidade, categoria, lancamentoItemContabil, lancamentoTipo, lancamentoItem);

							colecaoDevedoresDuvidosos.add(resumoDevedoresDuvidosos);
						}
					}
				}

				// recupera os parâmetros mais atual para verificar se o valor já ultrapasoou o
				// limite já permitido
				// mais o percentual aceitavel de estouro.
				parametrosDevedoresDuvidosos = repositorioFinanceiro.pesquisarParametrosDevedoresDuvidosos(anoMesReferenciaContabil);

				// Recupera o percentual permitido e calcula o valor limite permitido
				// de estouro
				BigDecimal percentualPermitido = new BigDecimal(0.10);
				BigDecimal valorLimiteBaixadoComPercentual = valorLimiteBaixado.add(valorLimiteBaixado.multiply(percentualPermitido));
				valorTotalJaBaixado = parametrosDevedoresDuvidosos.getValorBaixado().add(valorTotalValoresBaixados);
				if(valorTotalJaBaixado.compareTo(valorLimiteBaixadoComPercentual) != 1){
					// Inserindo o resumo
					getControladorBatch().inserirColecaoObjetoParaBatch(colecaoDevedoresDuvidosos);
					colecaoDevedoresDuvidosos = null;

					// 7.Atualiza nos parâmetros para baixa das contas dos devedores duvidosos a
					// data e hora
					// do processamento e o valor total baixado
					parametrosDevedoresDuvidosos.setValorBaixado(parametrosDevedoresDuvidosos.getValorBaixado().add(
									valorTotalValoresBaixados));
					this.getControladorUtil().atualizar(parametrosDevedoresDuvidosos);
					// ********************************************************

				}else{
					// o sistema atualiza com o valor nulo o ano/mês referência contábil das contas
					// baixadas
					// contabilmente no ano/mês referência contábil
					repositorioFinanceiro.atualizaContaAnoMesReferenciaContabil(anoMesReferenciaContabil, idLocalidade);

					// exclui o resumo dos devedores duvidosos,referente ao ano/mês referência
					// contábil
					repositorioFinanceiro.removeResumoDevedoresDuvidososPorAnoMesReferenciaContabil(anoMesReferenciaContabil, idLocalidade);
				}

				getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);
			}else{
				repositorioFinanceiro.atualizaContaAnoMesReferenciaContabil(anoMesReferenciaContabil, idLocalidade);

				repositorioFinanceiro.removeResumoDevedoresDuvidososPorAnoMesReferenciaContabil(anoMesReferenciaContabil, idLocalidade);

				getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);
			}
		}catch(Exception ex){
			ex.printStackTrace();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(ex);
		}
	}

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * [SB0001] - Acumular o resumo dos devedores duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 12/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param colecaoIdsContas
	 * @throws ControladorException
	 */
	protected Collection<GerarResumoDevedoresDuvidososHelper> acumularResumoDevedoresDuvidosos(int anoMesReferenciaContabil,
					Integer idLocalidade, Collection<Integer> colecaoIdsContas) throws ControladorException{

		Collection<GerarResumoDevedoresDuvidososHelper> colecaoRetorno = new ArrayList();
		GerarResumoDevedoresDuvidososHelper gerarResumoDevedoresDuvidososHelper = null;
		Collection<Object[]> colecaoDadosTemporaria = null;
		final Short ZERO = 0;
		Short maxSequencialImpressaoMais10 = this.getControladorFaturamento().recuperarValorMaximoSequencialImpressaoMais10();

		Collection<Integer> colecaoIdsCategorias = getControladorArrecadacao().pesquisarIdsCategoria();

		try{
			Integer idGerenciaRegional = this.getControladorLocalidade().pesquisarIdGerenciaParaLocalidade(idLocalidade);

			for(Integer idCategoria : colecaoIdsCategorias){
				System.out.println("Categoria " + idCategoria);
				// Linha 01
				BigDecimal valorAgua = repositorioFinanceiro.acumularValorAgua(anoMesReferenciaContabil, idLocalidade, idCategoria,
								colecaoIdsContas);

				if(valorAgua != null && valorAgua.compareTo(BigDecimal.ZERO) == 1){

					gerarResumoDevedoresDuvidososHelper = new GerarResumoDevedoresDuvidososHelper(idLocalidade, idGerenciaRegional,
									idCategoria, LancamentoTipo.AGUA, LancamentoItem.AGUA, null, new Short("100"), ZERO, valorAgua);

					colecaoRetorno.add(gerarResumoDevedoresDuvidososHelper);

				}
				// fim linha 01

				// Linha 02
				BigDecimal valorEsgoto = repositorioFinanceiro.acumularValorEsgoto(anoMesReferenciaContabil, idLocalidade, idCategoria,
								colecaoIdsContas);

				if(valorEsgoto != null && valorEsgoto.compareTo(BigDecimal.ZERO) == 1){

					gerarResumoDevedoresDuvidososHelper = new GerarResumoDevedoresDuvidososHelper(idLocalidade, idGerenciaRegional,
									idCategoria, LancamentoTipo.ESGOTO, LancamentoItem.ESGOTO, null, new Short("200"), ZERO, valorEsgoto);

					colecaoRetorno.add(gerarResumoDevedoresDuvidososHelper);
				}
				// fim linha 02

				// Linha 03
				BigDecimal valorParcelamentoAgua = repositorioFinanceiro
								.acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoAgua(anoMesReferenciaContabil,
												idLocalidade, idCategoria, colecaoIdsContas);

				if(valorParcelamentoAgua != null && valorParcelamentoAgua.compareTo(BigDecimal.ZERO) == 1){

					gerarResumoDevedoresDuvidososHelper = new GerarResumoDevedoresDuvidososHelper(idLocalidade, idGerenciaRegional,
									idCategoria, LancamentoTipo.PARCELAMENTOS_COBRADOS, LancamentoItem.AGUA, null, new Short("1000"),
									new Short("10"), valorParcelamentoAgua);

					colecaoRetorno.add(gerarResumoDevedoresDuvidososHelper);
				}
				// fim linha 03

				// Linha 04
				BigDecimal valorParcelamentoEsgoto = repositorioFinanceiro
								.acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoEsgoto(anoMesReferenciaContabil,
												idLocalidade, idCategoria, colecaoIdsContas);

				if(valorParcelamentoEsgoto != null && valorParcelamentoEsgoto.compareTo(BigDecimal.ZERO) == 1){

					gerarResumoDevedoresDuvidososHelper = new GerarResumoDevedoresDuvidososHelper(idLocalidade, idGerenciaRegional,
									idCategoria, LancamentoTipo.PARCELAMENTOS_COBRADOS, LancamentoItem.ESGOTO, null, new Short("1000"),
									new Short("20"), valorParcelamentoEsgoto);

					colecaoRetorno.add(gerarResumoDevedoresDuvidososHelper);
				}
				// fim linha 04

				// Linha 05
				colecaoDadosTemporaria = repositorioFinanceiro
								.acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoServico(anoMesReferenciaContabil,
												idLocalidade, idCategoria, colecaoIdsContas);

				if(colecaoDadosTemporaria != null && !colecaoDadosTemporaria.isEmpty()){

					for(Object[] dadosDebitoCobrado : colecaoDadosTemporaria){

						BigDecimal valorDebito = (BigDecimal) dadosDebitoCobrado[0];
						Short numeroSequencialImpressao = (Short) dadosDebitoCobrado[1];
						Integer idLancamentoItemContabil = (Integer) dadosDebitoCobrado[2];

						if(valorDebito != null && valorDebito.compareTo(BigDecimal.ZERO) == 1){

							gerarResumoDevedoresDuvidososHelper = new GerarResumoDevedoresDuvidososHelper(idLocalidade, idGerenciaRegional,
											idCategoria, LancamentoTipo.PARCELAMENTOS_COBRADOS, LancamentoItem.GRUPO_CONTABIL,
											idLancamentoItemContabil, new Short("1000"), numeroSequencialImpressao, valorDebito);

							colecaoRetorno.add(gerarResumoDevedoresDuvidososHelper);
						}
					}
				}
				// fim linha 05

				// Linha 06
				BigDecimal valorJurosParcelamento = repositorioFinanceiro
								.acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoJurosParcelamento(anoMesReferenciaContabil,
												idLocalidade, idCategoria, colecaoIdsContas);

				if(valorJurosParcelamento != null && valorJurosParcelamento.compareTo(BigDecimal.ZERO) == 1){

					gerarResumoDevedoresDuvidososHelper = new GerarResumoDevedoresDuvidososHelper(idLocalidade, idGerenciaRegional,
									idCategoria, LancamentoTipo.PARCELAMENTOS_COBRADOS, LancamentoItem.JUROS, null, new Short("1000"),
									maxSequencialImpressaoMais10, valorJurosParcelamento);

					colecaoRetorno.add(gerarResumoDevedoresDuvidososHelper);
				}
				// fim linha 06

				// Linha 07
				colecaoDadosTemporaria = repositorioFinanceiro.acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamento(
								anoMesReferenciaContabil, idLocalidade, idCategoria, FinanciamentoTipo.SERVICO_NORMAL, colecaoIdsContas);

				if(colecaoDadosTemporaria != null && !colecaoDadosTemporaria.isEmpty()){

					for(Object[] dadosDebitoCobrado : colecaoDadosTemporaria){

						BigDecimal valorDebito = (BigDecimal) dadosDebitoCobrado[0];
						Short numeroSequencialImpressao = (Short) dadosDebitoCobrado[1];
						Integer idLancamentoItemContabil = (Integer) dadosDebitoCobrado[2];

						if(valorDebito != null && valorDebito.compareTo(BigDecimal.ZERO) == 1){
							gerarResumoDevedoresDuvidososHelper = new GerarResumoDevedoresDuvidososHelper(idLocalidade, idGerenciaRegional,
											idCategoria, LancamentoTipo.FINANCIAMENTOS_COBRADOS, LancamentoItem.GRUPO_CONTABIL,
											idLancamentoItemContabil, new Short("1300"), numeroSequencialImpressao, valorDebito);

							colecaoRetorno.add(gerarResumoDevedoresDuvidososHelper);
						}
					}
				}
				// fim linha 07

			}// fim do if categoria

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC0345] - Gerar Relatorio de Resumo da Arrecadação
	 * 
	 * @author Vivianne Sousa
	 * @date 10/04/2007
	 * @param idLancamentoTipo
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoLancamentoTipo(Integer idLancamentoTipo) throws ControladorException{

		try{
			return repositorioFinanceiro.obterDescricaoLancamentoTipo(idLancamentoTipo);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa as localidades que tem resumo de faturamento
	 * para o ano/mês de faturamento informado.
	 * [UC00175] Gerar Lançamentos Contábeis do Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 25/05/2007
	 * @param anoMesFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisFaturamento(Integer anoMesFaturamento)
					throws ControladorException{

		try{
			// pesquisa os lançamentos de item contábil existentes no sisitema
			return repositorioFinanceiro.pesquisarIdsLocalidadesParaGerarLancamentosContabeisFaturamento(anoMesFaturamento);

			// erro no hibernate
		}catch(ErroRepositorioException ex){
			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Gera o lançamento contábil junto com seus itens.
	 * [UC0348] - Gerar Lançamentos Contábeis da Arrecadação
	 * 
	 * @author Pedro Alexandre
	 * @date 31/05/2007
	 * @param lancamentoContabil
	 * @param colecaoDadosResumoPorTipoLancamento
	 * @throws ControladorException
	 */
	protected void inserirLancamentoContabilItemArrecadacao(LancamentoContabil lancamentoContabil,
					Collection<Object[]> colecaoDadosResumoPorTipoLancamento) throws ControladorException{

		try{
			/*
			 * Caso exista dados para os itens do resumo da arrecadação
			 * inseri os itens do lançamento contábil.
			 */
			if(colecaoDadosResumoPorTipoLancamento != null && !colecaoDadosResumoPorTipoLancamento.isEmpty()){

				Collection colecaoLancamentoContabilItem = new ArrayList();

				// flaq que indica se o lançamento contábil já foi inserido
				boolean flagInseridoLancamentoContabil = false;

				/*
				 * Dados do resumo da arrecadação
				 * 0 - id da localidade
				 * 1 - id do tipo de recebimento
				 * 2 - id do tipo de lançamento
				 * 3 - id do item de lançamento
				 * 4 - id do item de lançamento contábil
				 * 5 - id da categoria
				 * 6 - soma do valor do resumo da arrecadação
				 */
				for(Object[] dadosResumoFaturamento : colecaoDadosResumoPorTipoLancamento){

					// recupera os dados do resumo do faturamento
					Integer idTipoRecebimento = (Integer) dadosResumoFaturamento[1];
					Integer idLancamentoTipo = (Integer) dadosResumoFaturamento[2];
					Integer idLancamentoItem = (Integer) dadosResumoFaturamento[3];
					Integer idLancamentoItemContabil = (Integer) dadosResumoFaturamento[4];
					Integer idCategoria = (Integer) dadosResumoFaturamento[5];
					BigDecimal valorLancamento = (BigDecimal) dadosResumoFaturamento[6];

					/*
					 * Verifica se existe conta contábil para o item que vai ser inserido
					 * 0 - id conta contábil do débito
					 * 1 - id conta contábil crédito
					 * 2 - descrição do histórico do débito
					 * 3 - descrição do histórico do crédito
					 */
					Object[] dadosContaContabil = repositorioFinanceiro.obterParametrosContabilArrecadacao(idTipoRecebimento, idCategoria,
									idLancamentoItemContabil, idLancamentoItem, idLancamentoTipo);

					if(dadosContaContabil != null){
						Integer idLancamentoContabil = null;
						/*
						 * Caso exista dados para a conta contábil do item do resumo da arrecadação
						 * e o lançamento contábil não foi inserido ainda
						 * inseri o lançamento contábil na base.
						 */
						if(!flagInseridoLancamentoContabil){
							idLancamentoContabil = (Integer) getControladorUtil().inserir(lancamentoContabil);
							lancamentoContabil.setId(idLancamentoContabil);
							flagInseridoLancamentoContabil = true;
						}

						// recupera os dados da conta contábil.
						Integer idContaContabilDebito = (Integer) dadosContaContabil[0];
						Integer idContaContabilCredito = (Integer) dadosContaContabil[1];
						String descricaoHistoricoDebito = (String) dadosContaContabil[2];
						String descricaoHistoricoCredito = (String) dadosContaContabil[3];

						// cria os indicadores de débito e crédito.
						Short indicadorDebito = new Short("2");
						Short indicadorCredito = new Short("1");

						Date ultimaAlteracao = new Date();

						// cria as contas de débito e crédito.
						ContaContabil contaContabilCredito = new ContaContabil();
						contaContabilCredito.setId(idContaContabilCredito);

						ContaContabil contaContabilDebito = new ContaContabil();
						contaContabilDebito.setId(idContaContabilDebito);

						/** Item de crédito */
						LancamentoContabilItem lancamentoContabilItemCredito = new LancamentoContabilItem(indicadorCredito,
										valorLancamento, descricaoHistoricoCredito, ultimaAlteracao, lancamentoContabil,
										contaContabilCredito);

						colecaoLancamentoContabilItem.add(lancamentoContabilItemCredito);

						/** Item de débito */
						LancamentoContabilItem lancamentoContabilItemDebito = new LancamentoContabilItem(indicadorDebito, valorLancamento,
										descricaoHistoricoDebito, ultimaAlteracao, lancamentoContabil, contaContabilDebito);

						colecaoLancamentoContabilItem.add(lancamentoContabilItemDebito);

					}
				}

				getControladorBatch().inserirColecaoObjetoParaBatch(colecaoLancamentoContabilItem);
			}

		}catch(Exception ex){
			throw new EJBException(ex);
		}
	}

	/**
	 * Pesquisa as localidades que tem resumo de arrecadação
	 * para o ano/mês de arrecadação informado.
	 * [UC00348] Gerar Lançamentos Contábeis da arrecadação
	 * 
	 * @author Pedro Alexandre
	 * @date 31/05/2007
	 * @param anoMesArrecadacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisArrecadacao(Integer anoMesArrecadacao)
					throws ControladorException{

		try{
			return repositorioFinanceiro.pesquisarIdsLocalidadesParaGerarLancamentosContabeisArrecadacao(anoMesArrecadacao);

			// erro no hibernate
		}catch(ErroRepositorioException ex){
			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Pesquisa a coleção de ids das localidades para processar o resumo
	 * dos devedores duvidosos.
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 25/06/2007
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarResumoDevedoresDuvidosos(Integer anoMesReferenciaContabil)
					throws ControladorException{

		try{
			return repositorioFinanceiro.pesquisarIdsLocalidadesGerarResumoDevedoresDuvidosos(anoMesReferenciaContabil);

			// erro no hibernate
		}catch(ErroRepositorioException ex){
			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	// ***********************************************************************************************************************
	// ******************************** PARTE PARA SEER COLOCADA NO CONTROLADOR CAERN

	/**
	 * Este metodo é utilizado para pesquisar os registros q serão
	 * usados para contrução do txt do caso de uso
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @author Flávio Leonardo
	 * @date 28/05/2007
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarGerarIntegracaoContabilidadeCaern(String idLancamentoOrigem, String anoMes) throws ControladorException{

		Collection colecaoObjetoGerar = null;
		Collection colecaoGerarIntegracaoContabilidade = null;

		try{

			colecaoObjetoGerar = repositorioFinanceiro.pesquisarGerarIntegracaoContabilidadeCaern(idLancamentoOrigem, anoMes);

			if(!colecaoObjetoGerar.isEmpty()){
				Iterator iteratorPesquisa = colecaoObjetoGerar.iterator();

				colecaoGerarIntegracaoContabilidade = new ArrayList();
				GerarIntegracaoContabilidadeHelper gerarIntegracaoContabilidadeHelper = null;
				Object[] objetoGerar = null;

				while(iteratorPesquisa.hasNext()){
					gerarIntegracaoContabilidadeHelper = new GerarIntegracaoContabilidadeHelper();

					objetoGerar = (Object[]) iteratorPesquisa.next();

					// indicador debito credito
					if(objetoGerar[10] != null){
						gerarIntegracaoContabilidadeHelper.setIndicadorDebitoConta(new Integer((Short) objetoGerar[10]));
					}

					// LCO_DEB_CRE
					if(gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta() != null
									&& gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(1)){
						gerarIntegracaoContabilidadeHelper.setCreditoDebito("C");
					}else if(gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta() != null
									&& gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(2)){
						gerarIntegracaoContabilidadeHelper.setCreditoDebito("D");
					}

					// numero cartao
					if(gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta() != null
									&& gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(1)){
						gerarIntegracaoContabilidadeHelper.setNumeroCartao(new Short("402"));
					}else if(gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta() != null
									&& gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(2)){
						gerarIntegracaoContabilidadeHelper.setNumeroCartao(new Short("401"));
					}

					// lancamento tipo
					if(objetoGerar[1] != null){
						gerarIntegracaoContabilidadeHelper.setIdTipoLancamento(new Integer((Short) objetoGerar[1]));
					}

					// folha
					if(objetoGerar[2] != null){
						gerarIntegracaoContabilidadeHelper.setFolha(new Integer((Short) objetoGerar[2]));
					}

					// linha
					if(objetoGerar[3] != null){
						gerarIntegracaoContabilidadeHelper.setIndicadorLinha(new Integer((Short) objetoGerar[3]));
					}

					// prefixo contabil
					if(objetoGerar[4] != null){
						gerarIntegracaoContabilidadeHelper.setNumeroPrefixoContabil((String) objetoGerar[4]);
					}

					// conta
					if(objetoGerar[5] != null && gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(1)){
						String numero = ((String) objetoGerar[5]).trim();
						gerarIntegracaoContabilidadeHelper.setNumeroContaCredito(numero);
						gerarIntegracaoContabilidadeHelper.setNumeroContaDebito("");
					}else if(objetoGerar[5] != null && gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta() != null
									&& gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(2)){
						String numero = ((String) objetoGerar[5]).trim();
						gerarIntegracaoContabilidadeHelper.setNumeroContaDebito(numero);
						gerarIntegracaoContabilidadeHelper.setNumeroContaCredito("");
					}

					// digito
					if(objetoGerar[6] != null){
						gerarIntegracaoContabilidadeHelper.setDigito(new Integer(((String) objetoGerar[6]).trim()));
					}

					// terceiros
					if(objetoGerar[7] != null){
						gerarIntegracaoContabilidadeHelper.setTerceiros(new Integer(((String) objetoGerar[7]).trim()));
					}

					// referencia
					if(objetoGerar[8] != null){
						gerarIntegracaoContabilidadeHelper.setReferencial(new Integer(((String) objetoGerar[8]).trim()));
					}

					// valor lancamento
					if(objetoGerar[9] != null){
						gerarIntegracaoContabilidadeHelper.setValorLancamento((BigDecimal) objetoGerar[9]);
					}

					// Cartao2
					if(objetoGerar[11] != null){
						gerarIntegracaoContabilidadeHelper.setCartao2(new Integer((Short) objetoGerar[11]));
					}

					// Versao
					if(objetoGerar[12] != null){
						gerarIntegracaoContabilidadeHelper.setVersao(new Integer((Short) objetoGerar[12]));
					}

					// id localidade
					if(objetoGerar[13] != null){
						gerarIntegracaoContabilidadeHelper.setIdLocalidade((Integer) objetoGerar[13]);
					}

					// codigo centro custo
					if(objetoGerar[14] != null && gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta() != null
									&& gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(1)){
						gerarIntegracaoContabilidadeHelper.setCodigoCentroCustoCredito(new Integer(((String) objetoGerar[14]).trim()));
						gerarIntegracaoContabilidadeHelper.setCodigoCentroCustoDebito(new Integer(0));
					}else if(objetoGerar[14] != null && gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta() != null
									&& gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta().equals(2)){
						gerarIntegracaoContabilidadeHelper.setCodigoCentroCustoDebito(new Integer(((String) objetoGerar[14]).trim()));
						gerarIntegracaoContabilidadeHelper.setCodigoCentroCustoCredito(new Integer(0));
					}

					colecaoGerarIntegracaoContabilidade.add(gerarIntegracaoContabilidadeHelper);
				}
			}

		}catch(ErroRepositorioException ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoGerarIntegracaoContabilidade;
	}

	/**
	 * este caso de uso gera a integração para a contabilidade
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @author Pedro Alexandre
	 * @date 28/05/2007
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @param data
	 * @throws ControladorException
	 */
	public void gerarIntegracaoContabilidadeCaern(String idLancamentoOrigem, String anoMes, String data) throws ControladorException{

		/*
		 * Pesquisa os dados para gerar a integração para a contabilidade.
		 * 0 - número do cartão
		 * 1 - código tipo
		 * 2 - número folha
		 * 3 - indicador linha
		 * 4 - prefixo contábil
		 * 5 - número conta
		 * 6 - número dígito
		 * 7 - número terceiros
		 * 8 - código referência
		 * 9 - valor lançamento
		 * 10 - indicador débito crédito
		 * 11 - número cartão 2
		 * 12 - número versão
		 * 13 - id da localidade
		 * 14 - código centro custo
		 */
		Collection<Object[]> colecaoDadosGerarIntegracao = null;

		colecaoDadosGerarIntegracao = this.pesquisarGerarIntegracaoContabilidadeCaern(idLancamentoOrigem, anoMes);

		/** definição das variáveis */
		StringBuilder gerarIntegracaoTxt = new StringBuilder();
		String mes = data.substring(3, 5);
		String dia = data.substring(0, 2);
		String mesDiaInformado = mes + dia;
		String dataFormatada = data.replace("/", "");

		/*
		 * Caso a coleção dos dados não esteja vazia
		 */
		if(colecaoDadosGerarIntegracao != null && !colecaoDadosGerarIntegracao.isEmpty()){

			/** definição das variáveis */
			int sequencial = 1;
			int contadorLinha = 1;
			BigDecimal acumuladorValor = new BigDecimal("0");

			Short numeroCartao = null;
			String creditoDebito = "";
			Short numeroFolha = null;
			Short indicadorLinha = null;
			String numeroPrefixoContabil = null;
			String numeroConta = null;
			String numeroDigito = null;
			String numeroTerceiros = null;
			String codigoReferencia = null;
			BigDecimal valorLancamento = null;
			Integer indicadorDebitoCredito = null;
			Short numeroCartao2 = null;
			Short numeroVersao = null;
			Integer idLocalidade = null;

			/*
			 * Laço para gerar o txt
			 */
			Iterator iterator = colecaoDadosGerarIntegracao.iterator();
			while(iterator.hasNext()){
				GerarIntegracaoContabilidadeHelper gerarIntegracaoContabilidadeHelper = (GerarIntegracaoContabilidadeHelper) iterator
								.next();
				// número do cartão
				numeroCartao = gerarIntegracaoContabilidadeHelper.getNumeroCartao();

				// CreditoDebito
				creditoDebito = gerarIntegracaoContabilidadeHelper.getCreditoDebito();

				// número conta
				numeroConta = gerarIntegracaoContabilidadeHelper.getCont() + "";

				// valor do lançamento
				valorLancamento = (BigDecimal) gerarIntegracaoContabilidadeHelper.getValorLancamento();

				// indicador de débito ou crédito
				indicadorDebitoCredito = gerarIntegracaoContabilidadeHelper.getIndicadorDebitoConta();

				// código da localidade
				idLocalidade = gerarIntegracaoContabilidadeHelper.getIdLocalidade();

				/*
				 * Inicio da geração do txt
				 */
				// Cartao
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(3, numeroCartao + ""));
				// Sequencial
				gerarIntegracaoTxt.append("01");
				// Lote
				gerarIntegracaoTxt.append("8888");
				// Documento
				gerarIntegracaoTxt.append("200001");
				// Linha
				gerarIntegracaoTxt.append("01");
				// data completa
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(8, dataFormatada));
				// CreditoDebito
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda(creditoDebito.trim(), 1));
				// COnta Debito
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda(gerarIntegracaoContabilidadeHelper.getNumeroContaDebito()
								+ "", 20));
				// COnta Debito
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda(gerarIntegracaoContabilidadeHelper.getNumeroContaCredito()
								+ "", 20));
				// Moeda
				gerarIntegracaoTxt.append("SSSSS");
				// Valor Lancamento
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda((valorLancamento + "").replace(".", ""), 17));
				// LCO_HISTORICO
				if(idLancamentoOrigem.equals(LancamentoOrigem.FATURAMENTO + "")){
					gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda("VL FATURAMENTO", 15));
				}else if(idLancamentoOrigem.equals(LancamentoOrigem.ARRECADACAO + "")){
					gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda("VL ARRECADACAO", 15));
				}
				// MesAno
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda(Util.formatarAnoMesParaMesAnoSemBarra(new Integer(anoMes))
								+ "", 6));
				// COdigo Custo Debito
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda(gerarIntegracaoContabilidadeHelper
								.getCodigoCentroCustoDebito()
								+ "", 9));
				// COdigo Custo Debito
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda(gerarIntegracaoContabilidadeHelper
								.getCodigoCentroCustoCredito()
								+ "", 9));
				// dia mes ano fechamento
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(8, dataFormatada));
				// FILLER
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda("DDDD", 4));
				// ANOMES
				gerarIntegracaoTxt.append(Util.adicionarZerosEsquedaNumero(6, anoMes));
				// FILLER
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda("LANCAMENTO GCOM", 15));
				// FILLER
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda("", 318));
				// FILLER
				gerarIntegracaoTxt.append(Util.completaStringComEspacoAEsquerda("", 33));
				// Quebra de Linha
				gerarIntegracaoTxt.append(System.getProperty("line.separator"));

			}
			/*
			 * Determina se o arquivo é de faturamento ou arrecadação
			 * para concatenar no nome do arquivo .zip
			 */
			String descricaoLancamento = "";
			if(idLancamentoOrigem.equals(LancamentoOrigem.FATURAMENTO + "")){
				descricaoLancamento = "FATURAMENTO";
			}else if(idLancamentoOrigem.equals(LancamentoOrigem.ARRECADACAO + "")){
				descricaoLancamento = "ARRECADACAO";
			}

			/*
			 * Gerando o arquivo zip
			 */
			String nomeZip = "CONTABILIDADE_" + descricaoLancamento + "_" + (data.replace("/", "_"));
			BufferedWriter out = null;
			ZipOutputStream zos = null;
			File compactadoTipo = new File(nomeZip + ".zip");
			File leituraTipo = new File(nomeZip + ".txt");

			/*
			 * Caso oarquivo txt não esteja vazio
			 * adiciona o txt ao arquivo zip.
			 */
			if(gerarIntegracaoTxt != null && gerarIntegracaoTxt.length() != 0){
				try{
					System.out.println("CRIANDO ZIP");
					zos = new ZipOutputStream(new FileOutputStream(compactadoTipo));

					out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leituraTipo.getAbsolutePath())));
					out.write(gerarIntegracaoTxt.toString());
					out.flush();
					ZipUtil.adicionarArquivo(zos, leituraTipo);
					zos.close();
					leituraTipo.delete();
					out.close();
				}catch(IOException ex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", ex);
				}catch(Exception e){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema", e);

				}

			}
			// caso não exista informação para os dados informados
		}else{
			if(idLancamentoOrigem.equals(LancamentoOrigem.FATURAMENTO + "")){
				throw new ControladorException("atencao.pesquisa.nenhum_registro_tabela", null, "Resumo Faturamento");
			}else if(idLancamentoOrigem.equals(LancamentoOrigem.ARRECADACAO + "")){
				throw new ControladorException("atencao.pesquisa.nenhum_registro_tabela", null, "Resumo Faturamento");
			}
		}
	}

	// ***********************************************************************************************************************

	/**
	 * Inserir o processo para gerar o resumo dos devedores duvidosos.
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 18/06/2007
	 * @param processoIniciado
	 * @param dadosProcessamento
	 * @return
	 * @throws ControladorException
	 */
	public Integer gerarResumoDevedoresDuvidosos(ProcessoIniciado processoIniciado, Map<String, Object> dadosProcessamento)
					throws ControladorException{

		// Recupera o ano/mês de referência contábil.
		Integer anoMesReferenciaContabil = new Integer((String) dadosProcessamento.get("anoMesReferenciaContabil"));

		ParametrosDevedoresDuvidosos parametrosDevedoresDuvidosos;
		try{

			// [FS0001] - Verificar existência dos parâmetros
			parametrosDevedoresDuvidosos = repositorioFinanceiro.pesquisarParametrosDevedoresDuvidosos(anoMesReferenciaContabil);
			if(parametrosDevedoresDuvidosos == null){
				throw new ControladorException("atencao.naocadastrado.referencia_contabil");
			}else{
				if(parametrosDevedoresDuvidosos.getDataProcessamento() != null){
					// 2-Caso seja um reprocessamento zerar o valor baixado
					parametrosDevedoresDuvidosos.setValorBaixado(BigDecimal.ZERO);
					this.getControladorUtil().atualizar(parametrosDevedoresDuvidosos);
				}

			}
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
		// chama o metódo para inserir o processo de gerar resumo devedores duvidosos
		return this.getControladorBatch().inserirProcessoIniciado(processoIniciado, dadosProcessamento);
	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Método responsável pela geração de contas a receber contábil
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void gerarContasAReceberContabil(Integer idLocalidade, int idFuncionalidadeIniciada) throws ControladorException{

		System.out.println("LOCALIDADE " + idLocalidade);
		int idUnidadeIniciada = 0;

		// -------------------------
		//
		// Registrar o início do processamento da Unidade de
		// Processamento
		// do Batch
		//
		// -------------------------
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, idLocalidade);

		SistemaParametro sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();

		try{

			if(!sistemaParametro.getAnoMesArrecadacao().equals(sistemaParametro.getAnoMesFaturamento())){
				getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
				throw new ControladorException("atencao.arrecadacao_ou_faturamento_nao_encerrados");
			}

			int anoMesAnteriorFaturamento = Util.subtrairMesDoAnoMes(sistemaParametro.getAnoMesFaturamento(), 1);

			Collection colecaoContasAReceberContabil = new ArrayList();

			// item 2
			// exclui os dados do saldo de contas a receber contábil do mês de
			// referência do faturamento já encerrado
			repositorioFinanceiro.removerContasAReceberContabil(anoMesAnteriorFaturamento, idLocalidade);

			// Valores de Água e Esgoto
			adicionarContaAReceberContabilAguaEsgoto(idLocalidade, anoMesAnteriorFaturamento, colecaoContasAReceberContabil);

			// Débitos Cobrados
			adicionarContaAReceberContabilDebitosCobrados(idLocalidade, anoMesAnteriorFaturamento, colecaoContasAReceberContabil);

			// Guias de Pagamento
			adicionarContaAReceberContabilGuiasPagamento(idLocalidade, anoMesAnteriorFaturamento, colecaoContasAReceberContabil);

			// Créditos Realizados
			adicionarContaAReceberContabilCreditosRealizados(idLocalidade, anoMesAnteriorFaturamento, colecaoContasAReceberContabil);

			// Débitos a Cobrar
			adicionarContaAReceberContabilDebitosACobrar(idLocalidade, anoMesAnteriorFaturamento, colecaoContasAReceberContabil);

			// Créditos a Realizar
			adicionarContaAReceberContabilCreditosARealizar(idLocalidade, anoMesAnteriorFaturamento, colecaoContasAReceberContabil);

			if(colecaoContasAReceberContabil != null && !colecaoContasAReceberContabil.isEmpty()){

				getControladorBatch().inserirColecaoObjetoParaBatch(colecaoContasAReceberContabil);
			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

			System.out.println("fim da geração " + "Localidade " + idLocalidade);

		}catch(Exception e){

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			sessionContext.setRollbackOnly();
			throw new EJBException(e);
		}
	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Cria o objeto de ContaAReceberContabil de acordo com os parâmetros
	 * passados
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 */
	private ContaAReceberContabil criarContaAReceberContabil(int anoMesReferencia, Integer idUnidadeNegocio, Integer idGerenciaRegional,
					Integer idLocalidade, Integer idCategoria, BigDecimal valorItem, Integer idLancamentoTipo, int sequenciaLancamentoTipo,
					Integer idLancamentoItem, int sequenciaLancamentoItem){

		ContaAReceberContabil retorno = new ContaAReceberContabil();

		retorno.setAnoMesReferencia(anoMesReferencia);

		// Gerência Regional
		if(idGerenciaRegional != null){
			GerenciaRegional gerenciaRegional = new GerenciaRegional();
			gerenciaRegional.setId(idGerenciaRegional);
			retorno.setGerenciaRegional(gerenciaRegional);
		}

		// Unidade Negócio
		if(idUnidadeNegocio != null){
			UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
			unidadeNegocio.setId(idUnidadeNegocio);
			retorno.setUnidadeNegocio(unidadeNegocio);
		}

		// Localidade
		if(idLocalidade != null){
			Localidade localidade = new Localidade();
			localidade.setId(idLocalidade);
			retorno.setLocalidade(localidade);
		}

		// Categoria
		if(idCategoria != null){
			Categoria categoria = new Categoria();
			categoria.setId(idCategoria);
			retorno.setCategoria(categoria);
		}
		// Valor Acumulado
		retorno.setValorItemLancamento(valorItem);

		// Lançamento Tipo
		if(idLancamentoTipo != null){
			LancamentoTipo lancamentoTipo = new LancamentoTipo();
			lancamentoTipo.setId(idLancamentoTipo);
			retorno.setLancamentoTipo(lancamentoTipo);
		}
		// Seqüência do Lançamento Tipo
		retorno.setNumeroSequenciaTipoLancamento(sequenciaLancamentoTipo);

		// Lançamento Item
		if(idLancamentoItem != null){
			LancamentoItem lancamentoItem = new LancamentoItem();
			lancamentoItem.setId(idLancamentoItem);
			retorno.setLancamentoItem(lancamentoItem);
		}

		// Seqüência do Lançamento Item
		retorno.setNumeroSequenciaItemTipoLancamento(sequenciaLancamentoItem);

		return retorno;

	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Adiciona os dados de água e esgoto
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 */
	private void adicionarContaAReceberContabilAguaEsgoto(Integer idLocalidade, int anoMesAnteriorFaturamento,
					Collection colecaoContasAReceberContabil) throws ErroRepositorioException{

		Collection colecaoDadosValorAguaEsgoto = repositorioFinanceiro.pesquisarDadosContasCategoriaValorAguaEsgoto(
						anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosValorAguaEsgoto != null && !colecaoDadosValorAguaEsgoto.isEmpty()){

			Iterator colecaoDadosValorAguaEsgotoIterator = colecaoDadosValorAguaEsgoto.iterator();

			while(colecaoDadosValorAguaEsgotoIterator.hasNext()){

				Object[] dadosValorAguaEsgoto = (Object[]) colecaoDadosValorAguaEsgotoIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosValorAguaEsgoto[0];
				Integer idUnidadeNegocioConta = (Integer) dadosValorAguaEsgoto[1];
				Integer idLocalidadeConta = (Integer) dadosValorAguaEsgoto[2];
				Integer idCategoriaConta = (Integer) dadosValorAguaEsgoto[3];
				BigDecimal valorAgua = (BigDecimal) dadosValorAguaEsgoto[4];
				BigDecimal valorEsgoto = (BigDecimal) dadosValorAguaEsgoto[5];



				// Água
				if(valorAgua != null && valorAgua.compareTo(new BigDecimal("0.00")) > 0){


					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorAgua,
									LancamentoTipo.TOTAL_COBRADO_NAS_CONTAS_INT, 100, LancamentoItem.AGUA, 10);

					colecaoContasAReceberContabil.add(contaAReceberContabil);

				}

				// Esgoto
				if(valorEsgoto != null && valorEsgoto.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorEsgoto,
									LancamentoTipo.TOTAL_COBRADO_NAS_CONTAS_INT, 100, LancamentoItem.ESGOTO, 20);

					colecaoContasAReceberContabil.add(contaAReceberContabil);

				}

			}

			colecaoDadosValorAguaEsgoto = null;

		}

	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Adiciona os dados de débitos cobrados
	 * 
	 * @author Rafael Corrêa
	 * @throws ControladorException
	 * @date 08/11/2007
	 */
	private void adicionarContaAReceberContabilDebitosCobrados(Integer idLocalidade, int anoMesAnteriorFaturamento,
					Collection colecaoContasAReceberContabil) throws ErroRepositorioException, ControladorException{

		// Serviço
		Collection colecaoDadosDebitosCobradosServico = repositorioFinanceiro.pesquisarDadosDebitosCobradosCategoriaServico(
						anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosDebitosCobradosServico != null && !colecaoDadosDebitosCobradosServico.isEmpty()){

			Iterator colecaoDadosDebitosCobradosServicoIterator = colecaoDadosDebitosCobradosServico.iterator();

			while(colecaoDadosDebitosCobradosServicoIterator.hasNext()){

				Object[] dadosDebitosCobradosServico = (Object[]) colecaoDadosDebitosCobradosServicoIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosDebitosCobradosServico[0];
				Integer idUnidadeNegocioConta = (Integer) dadosDebitosCobradosServico[1];
				Integer idLocalidadeConta = (Integer) dadosDebitosCobradosServico[2];
				Integer idCategoriaConta = (Integer) dadosDebitosCobradosServico[3];



				BigDecimal valorCategoria = (BigDecimal) dadosDebitosCobradosServico[4];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.TOTAL_COBRADO_NAS_CONTAS_INT, 100, LancamentoItem.FINANCIAMENTOS_COBRADOS, 30);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosDebitosCobradosServico = null;

		}

		Collection<Integer> tiposParcelamento = Util
						.converterStringParaColecaoInteger(ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO
						.executar());

		// Parcelamento
		Collection colecaoDadosDebitosCobradosParcelamento = repositorioFinanceiro.pesquisarDadosDebitosCobradosCategoriaParcelamento(
						anoMesAnteriorFaturamento, idLocalidade, tiposParcelamento);

		if(colecaoDadosDebitosCobradosParcelamento != null && !colecaoDadosDebitosCobradosParcelamento.isEmpty()){

			Iterator colecaoDadosDebitosCobradosParcelamentoIterator = colecaoDadosDebitosCobradosParcelamento.iterator();

			while(colecaoDadosDebitosCobradosParcelamentoIterator.hasNext()){

				Object[] dadosDebitosCobradosParcelamento = (Object[]) colecaoDadosDebitosCobradosParcelamentoIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosDebitosCobradosParcelamento[0];
				Integer idUnidadeNegocioConta = (Integer) dadosDebitosCobradosParcelamento[1];
				Integer idLocalidadeConta = (Integer) dadosDebitosCobradosParcelamento[2];
				Integer idCategoriaConta = (Integer) dadosDebitosCobradosParcelamento[3];

				BigDecimal valorCategoria = (BigDecimal) dadosDebitosCobradosParcelamento[4];


				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.TOTAL_COBRADO_NAS_CONTAS_INT, 100, LancamentoItem.PARCELAMENTOS_COBRADOS, 40);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosDebitosCobradosParcelamento = null;

		}

	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Adiciona os dados das guias de pagamento
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 */
	private void adicionarContaAReceberContabilGuiasPagamento(Integer idLocalidade, int anoMesAnteriorFaturamento,
					Collection colecaoContasAReceberContabil) throws ErroRepositorioException{

		// Parcelamento a cobrar
		Collection colecaoDadosGuiasPagamentoEntradaParcelamento = repositorioFinanceiro
						.pesquisarDadosGuiasPagamentoCategoriaEntradaParcelamento(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosGuiasPagamentoEntradaParcelamento != null && !colecaoDadosGuiasPagamentoEntradaParcelamento.isEmpty()){

			Iterator colecaoDadosGuiasPagamentoEntradaParcelamentoIterator = colecaoDadosGuiasPagamentoEntradaParcelamento.iterator();

			while(colecaoDadosGuiasPagamentoEntradaParcelamentoIterator.hasNext()){

				Object[] dadosGuiasPagamentoEntradaParcelamento = (Object[]) colecaoDadosGuiasPagamentoEntradaParcelamentoIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosGuiasPagamentoEntradaParcelamento[0];
				Integer idUnidadeNegocioConta = (Integer) dadosGuiasPagamentoEntradaParcelamento[1];
				Integer idLocalidadeConta = (Integer) dadosGuiasPagamentoEntradaParcelamento[2];
				Integer idCategoriaConta = (Integer) dadosGuiasPagamentoEntradaParcelamento[3];

				BigDecimal valorCategoria = (BigDecimal) dadosGuiasPagamentoEntradaParcelamento[4];


				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.GUIAS_PAGAMENTO, 200, LancamentoItem.PARCELAMENTOS_COBRADOS, 10);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosGuiasPagamentoEntradaParcelamento = null;

		}

		// Serviço
		Collection colecaoDadosGuiaPagamentoServico = repositorioFinanceiro.pesquisarDadosGuiasPagamentoCategoriaServico(
						anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosGuiaPagamentoServico != null && !colecaoDadosGuiaPagamentoServico.isEmpty()){

			Iterator colecaoDadosGuiaPagamentoServicoIterator = colecaoDadosGuiaPagamentoServico.iterator();

			while(colecaoDadosGuiaPagamentoServicoIterator.hasNext()){

				Object[] dadosGuiaPagamentoServico = (Object[]) colecaoDadosGuiaPagamentoServicoIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosGuiaPagamentoServico[0];
				Integer idUnidadeNegocioConta = (Integer) dadosGuiaPagamentoServico[1];
				Integer idLocalidadeConta = (Integer) dadosGuiaPagamentoServico[2];
				Integer idCategoriaConta = (Integer) dadosGuiaPagamentoServico[3];

				BigDecimal valorCategoria = (BigDecimal) dadosGuiaPagamentoServico[4];


				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.GUIAS_PAGAMENTO, 200, LancamentoItem.GUIAS_PAGAMENTO, 20);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosGuiaPagamentoServico = null;

		}

	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Adiciona os dados de créditos realizados
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 */
	private void adicionarContaAReceberContabilCreditosRealizados(Integer idLocalidade, int anoMesAnteriorFaturamento,
					Collection colecaoContasAReceberContabil) throws ErroRepositorioException{

		// Pagamento em duplicidade ou excesso
		Collection colecaoDadosCreditosRealizadosPagamentoExcesso = repositorioFinanceiro
						.pesquisarDadosCreditosRealizadosCategoriaPagamentoExcesso(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosRealizadosPagamentoExcesso != null && !colecaoDadosCreditosRealizadosPagamentoExcesso.isEmpty()){

			Iterator colecaoDadosCreditosRealizadosPagamentoExcessoIterator = colecaoDadosCreditosRealizadosPagamentoExcesso.iterator();

			while(colecaoDadosCreditosRealizadosPagamentoExcessoIterator.hasNext()){

				Object[] dadosCreditosRealizadosPagamentoExcesso = (Object[]) colecaoDadosCreditosRealizadosPagamentoExcessoIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosCreditosRealizadosPagamentoExcesso[0];
				Integer idUnidadeNegocioConta = (Integer) dadosCreditosRealizadosPagamentoExcesso[1];
				Integer idLocalidadeConta = (Integer) dadosCreditosRealizadosPagamentoExcesso[2];
				Integer idCategoriaConta = (Integer) dadosCreditosRealizadosPagamentoExcesso[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosRealizadosPagamentoExcesso[4];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, 300, LancamentoItem.CONTAS_PAGA_EM_DUPLICIDADE_EXCESSO, 10);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosRealizadosPagamentoExcesso = null;

		}

		// Descontos concedidos no parcelamento
		Collection colecaoDadosCreditosRealizadosDescontoParcelamento = repositorioFinanceiro
						.pesquisarDadosCreditosRealizadosCategoriaDescontoParcelamento(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosRealizadosDescontoParcelamento != null && !colecaoDadosCreditosRealizadosDescontoParcelamento.isEmpty()){

			Iterator colecaoDadosCreditosRealizadosDescontoParcelamentoIterator = colecaoDadosCreditosRealizadosDescontoParcelamento
							.iterator();

			while(colecaoDadosCreditosRealizadosDescontoParcelamentoIterator.hasNext()){

				Object[] dadosCreditosRealizadosDescontoParcelamento = (Object[]) colecaoDadosCreditosRealizadosDescontoParcelamentoIterator
								.next();

				Integer idGerenciaRegionalConta = (Integer) dadosCreditosRealizadosDescontoParcelamento[0];
				Integer idUnidadeNegocioConta = (Integer) dadosCreditosRealizadosDescontoParcelamento[1];
				Integer idLocalidadeConta = (Integer) dadosCreditosRealizadosDescontoParcelamento[2];
				Integer idCategoriaConta = (Integer) dadosCreditosRealizadosDescontoParcelamento[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosRealizadosDescontoParcelamento[4];



				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, 300, LancamentoItem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO,
									20);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosRealizadosDescontoParcelamento = null;

		}

		// Descontos condicionais
		Collection colecaoDadosCreditosRealizadosDescontoCondicional = repositorioFinanceiro
						.pesquisarDadosCreditosRealizadosCategoriaDescontoCondicional(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosRealizadosDescontoCondicional != null && !colecaoDadosCreditosRealizadosDescontoCondicional.isEmpty()){

			Iterator colecaoDadosCreditosRealizadosDescontoCondicionalIterator = colecaoDadosCreditosRealizadosDescontoCondicional
							.iterator();

			while(colecaoDadosCreditosRealizadosDescontoCondicionalIterator.hasNext()){

				Object[] dadosCreditosRealizadosDescontoCondicional = (Object[]) colecaoDadosCreditosRealizadosDescontoCondicionalIterator
								.next();

				Integer idGerenciaRegionalConta = (Integer) dadosCreditosRealizadosDescontoCondicional[0];
				Integer idUnidadeNegocioConta = (Integer) dadosCreditosRealizadosDescontoCondicional[1];
				Integer idLocalidadeConta = (Integer) dadosCreditosRealizadosDescontoCondicional[2];
				Integer idCategoriaConta = (Integer) dadosCreditosRealizadosDescontoCondicional[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosRealizadosDescontoCondicional[4];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, 300, LancamentoItem.DESCONTOS_CONDICIONAIS, 30);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosRealizadosDescontoCondicional = null;

		}

		// Descontos incondicionais
		Collection colecaoDadosCreditosRealizadosDescontoIncondicional = repositorioFinanceiro
						.pesquisarDadosCreditosRealizadosCategoriaDescontoIncondicional(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosRealizadosDescontoIncondicional != null && !colecaoDadosCreditosRealizadosDescontoIncondicional.isEmpty()){

			Iterator colecaoDadosCreditosRealizadosDescontoIncondicionalIterator = colecaoDadosCreditosRealizadosDescontoIncondicional
							.iterator();

			while(colecaoDadosCreditosRealizadosDescontoIncondicionalIterator.hasNext()){

				Object[] dadosCreditosRealizadosDescontoIncondicional = (Object[]) colecaoDadosCreditosRealizadosDescontoIncondicionalIterator
								.next();

				Integer idGerenciaRegionalConta = (Integer) dadosCreditosRealizadosDescontoIncondicional[0];
				Integer idUnidadeNegocioConta = (Integer) dadosCreditosRealizadosDescontoIncondicional[1];
				Integer idLocalidadeConta = (Integer) dadosCreditosRealizadosDescontoIncondicional[2];
				Integer idCategoriaConta = (Integer) dadosCreditosRealizadosDescontoIncondicional[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosRealizadosDescontoIncondicional[4];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, 300, LancamentoItem.DESCONTOS_INCONDICIONAIS, 40);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosRealizadosDescontoIncondicional = null;

		}

		// Ajustes para Zerar a Conta
		Collection colecaoDadosCreditosRealizadosAjusteZerarConta = repositorioFinanceiro
						.pesquisarDadosCreditosRealizadosCategoriaAjusteZerarConta(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosRealizadosAjusteZerarConta != null && !colecaoDadosCreditosRealizadosAjusteZerarConta.isEmpty()){

			Iterator colecaoDadosCreditosRealizadosAjusteZerarContaIterator = colecaoDadosCreditosRealizadosAjusteZerarConta.iterator();

			while(colecaoDadosCreditosRealizadosAjusteZerarContaIterator.hasNext()){

				Object[] dadosCreditosRealizadosAjusteZerarConta = (Object[]) colecaoDadosCreditosRealizadosAjusteZerarContaIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosCreditosRealizadosAjusteZerarConta[0];
				Integer idUnidadeNegocioConta = (Integer) dadosCreditosRealizadosAjusteZerarConta[1];
				Integer idLocalidadeConta = (Integer) dadosCreditosRealizadosAjusteZerarConta[2];
				Integer idCategoriaConta = (Integer) dadosCreditosRealizadosAjusteZerarConta[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosRealizadosAjusteZerarConta[4];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, 300, LancamentoItem.AJUSTES_PARA_ZERAR_CONTA, 50);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosRealizadosAjusteZerarConta = null;

		}

		// Devoluções
		Collection colecaoDadosCreditosRealizadosDevolucao = repositorioFinanceiro.pesquisarDadosCreditosRealizadosCategoriaDevolucao(
						anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosRealizadosDevolucao != null && !colecaoDadosCreditosRealizadosDevolucao.isEmpty()){

			Iterator colecaoDadosCreditosRealizadosDevolucaoIterator = colecaoDadosCreditosRealizadosDevolucao.iterator();

			while(colecaoDadosCreditosRealizadosDevolucaoIterator.hasNext()){

				Object[] dadosCreditosRealizadosDevolucao = (Object[]) colecaoDadosCreditosRealizadosDevolucaoIterator.next();

				Integer idGerenciaRegionalConta = (Integer) dadosCreditosRealizadosDevolucao[0];
				Integer idUnidadeNegocioConta = (Integer) dadosCreditosRealizadosDevolucao[1];
				Integer idLocalidadeConta = (Integer) dadosCreditosRealizadosDevolucao[2];
				Integer idCategoriaConta = (Integer) dadosCreditosRealizadosDevolucao[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosRealizadosDevolucao[4];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioConta, idGerenciaRegionalConta, idLocalidadeConta, idCategoriaConta, valorCategoria,
									LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, 300, LancamentoItem.VALORES_COBRADOS_INDEVIDAMENTE, 60);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosRealizadosDevolucao = null;

		}

	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Adiciona os dados de débitos a cobrar
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 */
	private void adicionarContaAReceberContabilDebitosACobrar(Integer idLocalidade, int anoMesAnteriorFaturamento,
					Collection colecaoContasAReceberContabil) throws ErroRepositorioException{

		// Serviço
		Collection colecaoDadosDebitosACobrarServico = repositorioFinanceiro.pesquisarDadosDebitoACobrarCategoriaServico(
						anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosDebitosACobrarServico != null && !colecaoDadosDebitosACobrarServico.isEmpty()){

			Iterator colecaoDadosDebitosACobrarServicoIterator = colecaoDadosDebitosACobrarServico.iterator();

			while(colecaoDadosDebitosACobrarServicoIterator.hasNext()){

				Object[] dadosDebitosACobrarServico = (Object[]) colecaoDadosDebitosACobrarServicoIterator.next();

				Integer idGerenciaRegionalDebito = (Integer) dadosDebitosACobrarServico[0];
				Integer idUnidadeNegocioDebito = (Integer) dadosDebitosACobrarServico[1];
				Integer idLocalidadeDebito = (Integer) dadosDebitosACobrarServico[2];
				Integer idCategoriaDebito = (Integer) dadosDebitosACobrarServico[3];

				BigDecimal valorCurtoPrazo = (BigDecimal) dadosDebitosACobrarServico[4];
				BigDecimal valorLongoPrazo = (BigDecimal) dadosDebitosACobrarServico[5];


				// Curto Prazo
				if(valorCurtoPrazo != null && valorCurtoPrazo.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioDebito, idGerenciaRegionalDebito, idLocalidadeDebito, idCategoriaDebito,
									valorCurtoPrazo, LancamentoTipo.FINANCIAMENTOS_A_COBRAR, 400,
									LancamentoItem.FINANCIAMENTOS_A_COBRAR_CURTO_PRAZO, 10);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

				// Longo Prazo
				if(valorLongoPrazo != null && valorLongoPrazo.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioDebito, idGerenciaRegionalDebito, idLocalidadeDebito, idCategoriaDebito,
									valorLongoPrazo, LancamentoTipo.FINANCIAMENTOS_A_COBRAR, 400,
									LancamentoItem.FINANCIAMENTOS_A_COBRAR_LONGO_PRAZO, 20);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosDebitosACobrarServico = null;

		}

		// Parcelamentos A Cobrar de Longo Prazo
		Collection colecaoDadosDebitosACobrarParcelamentosLongoPrazo = repositorioFinanceiro
						.pesquisarDadosDebitoACobrarCategoriaParcelamentosLongoPrazo(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosDebitosACobrarParcelamentosLongoPrazo != null && !colecaoDadosDebitosACobrarParcelamentosLongoPrazo.isEmpty()){

			Iterator colecaoDadosDebitosACobrarParcelamentosLongoPrazoIterator = colecaoDadosDebitosACobrarParcelamentosLongoPrazo
							.iterator();

			while(colecaoDadosDebitosACobrarParcelamentosLongoPrazoIterator.hasNext()){

				Object[] dadosDebitosACobrarParcelamentosLongoPrazo = (Object[]) colecaoDadosDebitosACobrarParcelamentosLongoPrazoIterator
								.next();

				Integer idGerenciaRegionalDebito = (Integer) dadosDebitosACobrarParcelamentosLongoPrazo[0];
				Integer idUnidadeNegocioDebito = (Integer) dadosDebitosACobrarParcelamentosLongoPrazo[1];
				Integer idLocalidadeDebito = (Integer) dadosDebitosACobrarParcelamentosLongoPrazo[2];
				Integer idCategoriaDebito = (Integer) dadosDebitosACobrarParcelamentosLongoPrazo[3];

				BigDecimal valorCurtoPrazo = (BigDecimal) dadosDebitosACobrarParcelamentosLongoPrazo[4];
				BigDecimal valorLongoPrazo = (BigDecimal) dadosDebitosACobrarParcelamentosLongoPrazo[5];


				// Curto Prazo
				if(valorCurtoPrazo != null && valorCurtoPrazo.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioDebito, idGerenciaRegionalDebito, idLocalidadeDebito, idCategoriaDebito,
									valorCurtoPrazo, LancamentoTipo.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO, 500,
									LancamentoItem.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO, 50);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

				// Longo Prazo
				if(valorLongoPrazo != null && valorLongoPrazo.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioDebito, idGerenciaRegionalDebito, idLocalidadeDebito, idCategoriaDebito,
									valorLongoPrazo, LancamentoTipo.PARCELAMENTOS_A_COBRAR_LONGO_PRAZO, 600,
									LancamentoItem.PARCELAMENTOS_A_COBRAR_LONGO_PRAZO, 50);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosDebitosACobrarParcelamentosLongoPrazo = null;

		}

		// Arrasto
		Collection colecaoDadosDebitosACobrarArrasto = repositorioFinanceiro.pesquisarDadosDebitoACobrarCategoriaArrasto(
						anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosDebitosACobrarArrasto != null && !colecaoDadosDebitosACobrarArrasto.isEmpty()){

			Iterator colecaoDadosDebitosACobrarArrastoIterator = colecaoDadosDebitosACobrarArrasto.iterator();

			while(colecaoDadosDebitosACobrarArrastoIterator.hasNext()){

				Object[] dadosDebitosACobrarArrasto = (Object[]) colecaoDadosDebitosACobrarArrastoIterator.next();

				Integer idGerenciaRegionalDebito = (Integer) dadosDebitosACobrarArrasto[0];
				Integer idUnidadeNegocioDebito = (Integer) dadosDebitosACobrarArrasto[1];
				Integer idLocalidadeDebito = (Integer) dadosDebitosACobrarArrasto[2];
				Integer idCategoriaDebito = (Integer) dadosDebitosACobrarArrasto[3];

				BigDecimal valorCategoria = (BigDecimal) dadosDebitosACobrarArrasto[4];


				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioDebito, idGerenciaRegionalDebito, idLocalidadeDebito, idCategoriaDebito,
									valorCategoria, LancamentoTipo.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO, 500,
									LancamentoItem.DEBITOS_ANTERIORES_PARA_RECOBRANCA, 60);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosDebitosACobrarArrasto = null;

		}

	}

	/**
	 * [UC0714] Gerar Contas a Receber Contábil
	 * Adiciona os dados de créditos a realizar
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 */
	private void adicionarContaAReceberContabilCreditosARealizar(Integer idLocalidade, int anoMesAnteriorFaturamento,
					Collection colecaoContasAReceberContabil) throws ErroRepositorioException{

		// Descontos Concedidos no Parcelamento
		Collection colecaoDadosCreditosARealizarDescontoParcelamento = repositorioFinanceiro
						.pesquisarDadosCreditoARealizarCategoriaDescontosParcelamento(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosARealizarDescontoParcelamento != null && !colecaoDadosCreditosARealizarDescontoParcelamento.isEmpty()){

			Iterator colecaoDadosCreditosARealizarDescontoParcelamentoIterator = colecaoDadosCreditosARealizarDescontoParcelamento
							.iterator();

			while(colecaoDadosCreditosARealizarDescontoParcelamentoIterator.hasNext()){

				Object[] dadosCreditosARealizarDescontoParcelamento = (Object[]) colecaoDadosCreditosARealizarDescontoParcelamentoIterator
								.next();

				Integer idGerenciaRegionalCredito = (Integer) dadosCreditosARealizarDescontoParcelamento[0];
				Integer idUnidadeNegocioCredito = (Integer) dadosCreditosARealizarDescontoParcelamento[1];
				Integer idLocalidadeCredito = (Integer) dadosCreditosARealizarDescontoParcelamento[2];
				Integer idCategoriaCredito = (Integer) dadosCreditosARealizarDescontoParcelamento[3];

				BigDecimal valorCurtoPrazo = (BigDecimal) dadosCreditosARealizarDescontoParcelamento[4];
				BigDecimal valorLongoPrazo = (BigDecimal) dadosCreditosARealizarDescontoParcelamento[5];


				// Curto Prazo
				if(valorCurtoPrazo != null && valorCurtoPrazo.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioCredito, idGerenciaRegionalCredito, idLocalidadeCredito, idCategoriaCredito,
									valorCurtoPrazo, LancamentoTipo.PARCELAMENTOS_A_COBRAR_CURTO_PRAZO, 500,
									LancamentoItem.DESCONTOS_CONCEDIDOS, 70);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

				// Longo Prazo
				if(valorLongoPrazo != null && valorLongoPrazo.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioCredito, idGerenciaRegionalCredito, idLocalidadeCredito, idCategoriaCredito,
									valorLongoPrazo, LancamentoTipo.PARCELAMENTOS_A_COBRAR_LONGO_PRAZO, 600,
									LancamentoItem.DESCONTOS_CONCEDIDOS, 60);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosARealizarDescontoParcelamento = null;

		}

		// Devoluções
		Collection colecaoDadosCreditosARealizarDevolucao = repositorioFinanceiro.pesquisarDadosCreditosARealizarCategoriaDevolucao(
						anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosARealizarDevolucao != null && !colecaoDadosCreditosARealizarDevolucao.isEmpty()){

			Iterator colecaoDadosCreditosARealizarDevolucaoIterator = colecaoDadosCreditosARealizarDevolucao.iterator();

			while(colecaoDadosCreditosARealizarDevolucaoIterator.hasNext()){

				Object[] dadosCreditosARealizarDevolucao = (Object[]) colecaoDadosCreditosARealizarDevolucaoIterator.next();

				Integer idGerenciaRegionalCredito = (Integer) dadosCreditosARealizarDevolucao[0];
				Integer idUnidadeNegocioCredito = (Integer) dadosCreditosARealizarDevolucao[1];
				Integer idLocalidadeCredito = (Integer) dadosCreditosARealizarDevolucao[2];
				Integer idCategoriaCredito = (Integer) dadosCreditosARealizarDevolucao[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosARealizarDevolucao[4];


				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioCredito, idGerenciaRegionalCredito, idLocalidadeCredito, idCategoriaCredito,
									valorCategoria, LancamentoTipo.CREDITOS_A_REALIZAR, 700, LancamentoItem.VALORES_COBRADOS_INDEVIDAMENTE,
									10);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosARealizarDevolucao = null;

		}

		// Descontos incondicionais
		Collection colecaoDadosCreditosARealizarDescontoIncondicional = repositorioFinanceiro
						.pesquisarDadosCreditosARealizarCategoriaDescontoIncondicional(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosARealizarDescontoIncondicional != null && !colecaoDadosCreditosARealizarDescontoIncondicional.isEmpty()){

			Iterator colecaoDadosCreditosARealizarDescontoIncondicionalIterator = colecaoDadosCreditosARealizarDescontoIncondicional
							.iterator();

			while(colecaoDadosCreditosARealizarDescontoIncondicionalIterator.hasNext()){

				Object[] dadosCreditosARealizarDescontoIncondicional = (Object[]) colecaoDadosCreditosARealizarDescontoIncondicionalIterator
								.next();

				Integer idGerenciaRegionalCredito = (Integer) dadosCreditosARealizarDescontoIncondicional[0];
				Integer idUnidadeNegocioCredito = (Integer) dadosCreditosARealizarDescontoIncondicional[1];
				Integer idLocalidadeCredito = (Integer) dadosCreditosARealizarDescontoIncondicional[2];
				Integer idCategoriaCredito = (Integer) dadosCreditosARealizarDescontoIncondicional[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosARealizarDescontoIncondicional[4];


				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioCredito, idGerenciaRegionalCredito, idLocalidadeCredito, idCategoriaCredito,
									valorCategoria, LancamentoTipo.CREDITOS_A_REALIZAR, 700, LancamentoItem.DESCONTOS_INCONDICIONAIS, 20);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosARealizarDescontoIncondicional = null;
		}

		// Pagamento em duplicidade ou excesso
		Collection colecaoDadosCreditosARealizarPagamentoExcesso = repositorioFinanceiro
						.pesquisarDadosCreditosARealizarCategoriaPagamentoExcesso(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosARealizarPagamentoExcesso != null && !colecaoDadosCreditosARealizarPagamentoExcesso.isEmpty()){

			Iterator colecaoDadosCreditosARealizarPagamentoExcessoIterator = colecaoDadosCreditosARealizarPagamentoExcesso.iterator();

			while(colecaoDadosCreditosARealizarPagamentoExcessoIterator.hasNext()){

				Object[] dadosCreditosARealizarPagamentoExcesso = (Object[]) colecaoDadosCreditosARealizarPagamentoExcessoIterator.next();

				Integer idGerenciaRegionalCredito = (Integer) dadosCreditosARealizarPagamentoExcesso[0];
				Integer idUnidadeNegocioCredito = (Integer) dadosCreditosARealizarPagamentoExcesso[1];
				Integer idLocalidadeCredito = (Integer) dadosCreditosARealizarPagamentoExcesso[2];
				Integer idCategoriaCredito = (Integer) dadosCreditosARealizarPagamentoExcesso[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosARealizarPagamentoExcesso[4];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioCredito, idGerenciaRegionalCredito, idLocalidadeCredito, idCategoriaCredito,
									valorCategoria, LancamentoTipo.CREDITOS_A_REALIZAR, 700,
									LancamentoItem.CONTAS_PAGA_EM_DUPLICIDADE_EXCESSO, 30);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosARealizarPagamentoExcesso = null;

		}

		// Descontos condicionais
		Collection colecaoDadosCreditosARealizarDescontoCondicional = repositorioFinanceiro
						.pesquisarDadosCreditosARealizarCategoriaDescontoCondicional(anoMesAnteriorFaturamento, idLocalidade);

		if(colecaoDadosCreditosARealizarDescontoCondicional != null && !colecaoDadosCreditosARealizarDescontoCondicional.isEmpty()){

			Iterator colecaoDadosCreditosARealizarDescontoCondicionalIterator = colecaoDadosCreditosARealizarDescontoCondicional.iterator();

			while(colecaoDadosCreditosARealizarDescontoCondicionalIterator.hasNext()){

				Object[] dadosCreditosARealizarDescontoCondicional = (Object[]) colecaoDadosCreditosARealizarDescontoCondicionalIterator
								.next();

				Integer idGerenciaRegionalCredito = (Integer) dadosCreditosARealizarDescontoCondicional[0];
				Integer idUnidadeNegocioCredito = (Integer) dadosCreditosARealizarDescontoCondicional[1];
				Integer idLocalidadeCredito = (Integer) dadosCreditosARealizarDescontoCondicional[2];
				Integer idCategoriaCredito = (Integer) dadosCreditosARealizarDescontoCondicional[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosARealizarDescontoCondicional[4];


				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioCredito, idGerenciaRegionalCredito, idLocalidadeCredito, idCategoriaCredito,
									valorCategoria, LancamentoTipo.CREDITOS_A_REALIZAR, 700, LancamentoItem.DESCONTOS_CONDICIONAIS, 40);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosARealizarDescontoCondicional = null;

		}

		// Ajustes para Zerar a Conta
		Collection colecaoDadosCreditosARealizarAjusteZerarConta = repositorioFinanceiro
						.pesquisarDadosCreditosARealizarCategoriaAjusteZerarConta(anoMesAnteriorFaturamento, idLocalidade);


		if(colecaoDadosCreditosARealizarAjusteZerarConta != null && !colecaoDadosCreditosARealizarAjusteZerarConta.isEmpty()){

			Iterator colecaoDadosCreditosARealizarAjusteZerarContaIterator = colecaoDadosCreditosARealizarAjusteZerarConta.iterator();

			while(colecaoDadosCreditosARealizarAjusteZerarContaIterator.hasNext()){

				Object[] dadosCreditosARealizarAjusteZerarConta = (Object[]) colecaoDadosCreditosARealizarAjusteZerarContaIterator.next();

				Integer idGerenciaRegionalCredito = (Integer) dadosCreditosARealizarAjusteZerarConta[0];
				Integer idUnidadeNegocioCredito = (Integer) dadosCreditosARealizarAjusteZerarConta[1];
				Integer idLocalidadeCredito = (Integer) dadosCreditosARealizarAjusteZerarConta[2];
				Integer idCategoriaCredito = (Integer) dadosCreditosARealizarAjusteZerarConta[3];

				BigDecimal valorCategoria = (BigDecimal) dadosCreditosARealizarAjusteZerarConta[43];

				if(valorCategoria != null && valorCategoria.compareTo(new BigDecimal("0.00")) > 0){

					// Cria o objeto com os valores passados
					ContaAReceberContabil contaAReceberContabil = criarContaAReceberContabil(anoMesAnteriorFaturamento,
									idUnidadeNegocioCredito, idGerenciaRegionalCredito, idLocalidadeCredito, idCategoriaCredito,
									valorCategoria, LancamentoTipo.CREDITOS_A_REALIZAR, 700, LancamentoItem.AJUSTES_PARA_ZERAR_CONTA, 50);

					colecaoContasAReceberContabil.add(contaAReceberContabil);
				}

			}

			colecaoDadosCreditosARealizarAjusteZerarConta = null;

		}

	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/06/2007
	 * @param processoIniciado
	 * @param dadosProcessamento
	 * @return
	 * @throws ControladorException
	 */
	public ParametrosDevedoresDuvidosos pesquisarParametrosDevedoresDuvidosos(Integer anoMesReferenciaContabil) throws ControladorException{

		try{
			return repositorioFinanceiro.pesquisarParametrosDevedoresDuvidosos(anoMesReferenciaContabil);
		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Gera os lançamentos dos devedores duvidosos.
	 * [UC0486] Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 21/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarLancamentosContabeisDevedoresDuvidosos(Integer anoMesReferenciaContabil, Integer idLocalidade,
					int idFuncionalidadeIniciada) throws ControladorException{

		System.out.println("Localidade " + idLocalidade);

		int idUnidadeIniciada = 0;

		/*
		 * Registrar o início do processamento da Unidade de
		 * Processamento do Batch
		 */
		idUnidadeIniciada = getControladorBatch().iniciarUnidadeProcessamentoBatch(idFuncionalidadeIniciada,
						UnidadeProcessamento.LOCALIDADE, (idLocalidade));

		// [FS0001 - Validar Referência Contábil]
		Integer anoMesArrecadacaoAtual = getControladorUtil().pesquisarParametrosDoSistema().getAnoMesArrecadacao();
		if(anoMesReferenciaContabil.intValue() > anoMesArrecadacaoAtual.intValue()){
			// levanta a exceção para a próxima camada
			throw new ControladorException("atencao.mes_ano.contabil.superior", null, Util.formatarAnoMesParaMesAno(anoMesArrecadacaoAtual
							.toString()));
		}

		// remove os lançamentos contábeis já gerados
		this.removerLancamentoContabil(anoMesReferenciaContabil, idLocalidade, LancamentoOrigem.DEVEDORES_DUVIDOSOS);

		try{

			/*
			 * Pesquisa os dados do resumo dos devedores duvidosos
			 * para o ano/mês de referência atual e para a localidade informada.
			 * 0 - id da localidade
			 * 1 - id do tipo de lançamento
			 * 2 - id do item de lançamento
			 * 3 - id do item de lançamento contábil
			 * 4 - id da categoria
			 * 5 - soma do valor do resumo dos devedores duvidosos
			 */
			Collection<Object[]> colecaoDadosResumoDevedoresDuvidosos = repositorioFinanceiro.obterDadosResumoDevedoresDuvidosos(
							anoMesReferenciaContabil, idLocalidade);

			/*
			 * Caso exista resumo de devedores duvidosos para a localidade e o ano/mês
			 * cria o lancamento contábil junto com seus items
			 * para cada conjunto de mesmo tipo de lançamento
			 */
			if(colecaoDadosResumoDevedoresDuvidosos != null && !colecaoDadosResumoDevedoresDuvidosos.isEmpty()){

				// flag utilizada somente quando for a primeira entrada
				boolean flagPrimeiraVez = true;
				int idTipoLancamentoTemp = -1;
				Collection<Object[]> colecaoDadosResumoPorTipoLancamento = new ArrayList();

				// definição da origem do lançamento
				LancamentoOrigem lancamentoOrigem = new LancamentoOrigem();
				lancamentoOrigem.setId(LancamentoOrigem.DEVEDORES_DUVIDOSOS);

				// Cria a variável que vai armazenar o lançamento contábil
				LancamentoContabil lancamentoContabilInsert = null;

				// laço para gerar os lançamentos por grupo de tipo de lançamento
				for(Object[] dadosResumoDevedoresDuvidosos : colecaoDadosResumoDevedoresDuvidosos){

					// recupera o tipo de lançamento atual
					Integer idTipoLancamento = (Integer) dadosResumoDevedoresDuvidosos[1];

					/*
					 * Caso seja a primeira entrada do "for"
					 * adiciona os dados a coleção e atualiza o item temporario
					 * criando também o lançamento contabil que ira ser inserido
					 * junto com seus items.
					 * Caso contrário (não seja a primeira entrada do laço "for")
					 * verifica se o item de lançamento mudou, caso não tenha mudado
					 * adiciona os dados ao conjunto do mesmo item
					 * caso contrário, se mudou o item de lançamento o conjunto está fechado
					 * para o lançamento contábil e chama o método para inserir o
					 * lançamento contábil junto com seus itens.
					 */
					if(flagPrimeiraVez){
						colecaoDadosResumoPorTipoLancamento.add(dadosResumoDevedoresDuvidosos);
						flagPrimeiraVez = false;
						idTipoLancamentoTemp = idTipoLancamento;

						LancamentoTipo tipoLancamento = new LancamentoTipo();
						tipoLancamento.setId(idTipoLancamento);

						Localidade localidade = new Localidade();
						localidade.setId(idLocalidade);

						// cria o lançamento contábil para ser inserido
						lancamentoContabilInsert = new LancamentoContabil();
						lancamentoContabilInsert.setAnoMes(anoMesReferenciaContabil);
						lancamentoContabilInsert.setLancamentoOrigem(lancamentoOrigem);
						lancamentoContabilInsert.setLancamentoTipo(tipoLancamento);
						lancamentoContabilInsert.setLocalidade(localidade);
						lancamentoContabilInsert.setRecebimentoTipo(null);
						lancamentoContabilInsert.setUltimaAlteracao(new Date());
					}else{
						/*
						 * Caso ainda seja o mesmo item adicona os dados para
						 * ser gerado os itens do lançamento para o mesmo lançamento.
						 * Caso contrário chama o metódo para inseri os itens e o lançamento
						 * contábil.
						 */
						if(idTipoLancamento == idTipoLancamentoTemp){
							colecaoDadosResumoPorTipoLancamento.add(dadosResumoDevedoresDuvidosos);
						}else{
							/* metódo para inserir o lançamento contábil assim como seus itens */
							this.inserirLancamentoContabilItemDevedoresDuvidosos(lancamentoContabilInsert,
											colecaoDadosResumoPorTipoLancamento);

							// limpa coleção e adiciona os dados do resumo atual
							colecaoDadosResumoPorTipoLancamento.clear();
							colecaoDadosResumoPorTipoLancamento.add(dadosResumoDevedoresDuvidosos);

							LancamentoTipo tipoLancamento = new LancamentoTipo();
							tipoLancamento.setId(idTipoLancamento);

							Localidade localidade = new Localidade();
							localidade.setId(idLocalidade);

							// cria o lançamento contábil que vai ser inserido
							lancamentoContabilInsert = new LancamentoContabil();
							lancamentoContabilInsert.setAnoMes(anoMesReferenciaContabil);
							lancamentoContabilInsert.setLancamentoOrigem(lancamentoOrigem);
							lancamentoContabilInsert.setLancamentoTipo(tipoLancamento);
							lancamentoContabilInsert.setLocalidade(localidade);
							lancamentoContabilInsert.setRecebimentoTipo(null);
							lancamentoContabilInsert.setUltimaAlteracao(new Date());

							// atualiza o tipo de lançamento temporário com o novo valor
							idTipoLancamentoTemp = idTipoLancamento;
						}
					}
				}

				/*
				 * Último registro
				 * Esse "if" é para verificar se ainda existe um último registro na coleção
				 * caso exista algum item, adiciona o lançamento contábil junto com o item.
				 */
				if(colecaoDadosResumoPorTipoLancamento != null && colecaoDadosResumoPorTipoLancamento.size() > 0){
					this.inserirLancamentoContabilItemDevedoresDuvidosos(lancamentoContabilInsert, colecaoDadosResumoPorTipoLancamento);
					colecaoDadosResumoPorTipoLancamento = null;
				}
			}

			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, false);

		}catch(Exception ex){
			ex.printStackTrace();
			getControladorBatch().encerrarUnidadeProcessamentoBatch(idUnidadeIniciada, true);
			throw new EJBException(ex);
		}
	}

	/**
	 * Gera o lançamento contábil junto com seus itens.
	 * [UC0486] - Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 21/06/2007
	 * @param lancamentoContabil
	 * @param colecaoDadosResumoPorTipoLancamento
	 * @throws ControladorException
	 */
	protected void inserirLancamentoContabilItemDevedoresDuvidosos(LancamentoContabil lancamentoContabil,
					Collection<Object[]> colecaoDadosResumoPorTipoLancamento) throws ControladorException{

		try{
			/*
			 * Caso exista dados para os itens do resumo do faturamento.
			 */
			if(colecaoDadosResumoPorTipoLancamento != null && !colecaoDadosResumoPorTipoLancamento.isEmpty()){

				Collection colecaoLancamentoContabilItem = new ArrayList();

				// flag que indica se o lançamento contábil já foi inserido ou não.
				boolean flagInseridoLancamentoContabil = false;

				/*
				 * Dados do resumo dos devedores duvidosos
				 * 0 - id da localidade
				 * 1 - id do tipo de lançamento
				 * 2 - id do item de lançamento
				 * 3 - id do item de lançamento contábil
				 * 4 - id da categoria
				 * 5 - soma do valor do resumo dos devedores duvidosos
				 */
				for(Object[] dadosResumoDevedoresDuvidosos : colecaoDadosResumoPorTipoLancamento){

					// recupera os dados do resumo dos devedores duvidosos
					Integer idLancamentoTipo = (Integer) dadosResumoDevedoresDuvidosos[1];
					Integer idLancamentoItem = (Integer) dadosResumoDevedoresDuvidosos[2];
					Integer idLancamentoItemContabil = (Integer) dadosResumoDevedoresDuvidosos[3];
					Integer idCategoria = (Integer) dadosResumoDevedoresDuvidosos[4];
					BigDecimal valorLancamento = (BigDecimal) dadosResumoDevedoresDuvidosos[5];

					/*
					 * Verifica se existe conta contábil para o item que vai ser inserido
					 * 0 - id conta contábil do débito
					 * 1 - id conta contábil crédito
					 * 2 - descrição do histórico do débito
					 * 3 - descrição do histórico do crédito
					 */
					Object[] dadosContaContabil = repositorioFinanceiro.obterParametrosContabilDevedoresDuvidosos(idCategoria,
									idLancamentoItemContabil, idLancamentoItem, idLancamentoTipo);

					if(dadosContaContabil != null){
						Integer idLancamentoContabil = null;
						/*
						 * Caso exista dados para a conta contábil e o item contábil
						 * ainda não foi inserido
						 * inseri o item contábil na base.
						 */
						if(!flagInseridoLancamentoContabil){
							idLancamentoContabil = (Integer) getControladorUtil().inserir(lancamentoContabil);
							lancamentoContabil.setId(idLancamentoContabil);
							flagInseridoLancamentoContabil = true;
						}

						// recupera os dados da conta contábil para o item
						// do resumo dos devedores duvidosos.
						Integer idContaContabilDebito = (Integer) dadosContaContabil[0];
						Integer idContaContabilCredito = (Integer) dadosContaContabil[1];
						String descricaoHistoricoDebito = (String) dadosContaContabil[2];
						String descricaoHistoricoCredito = (String) dadosContaContabil[3];

						// cria os indicadores de débito e crédito.
						Short indicadorDebito = new Short("2");
						Short indicadorCredito = new Short("1");

						Date ultimaAlteracao = new Date();

						// cria as contas contábeis de crédito e débito.
						ContaContabil contaContabilCredito = new ContaContabil();
						contaContabilCredito.setId(idContaContabilCredito);

						ContaContabil contaContabilDebito = new ContaContabil();
						contaContabilDebito.setId(idContaContabilDebito);

						/** Item de crédito */
						LancamentoContabilItem lancamentoContabilItemCredito = new LancamentoContabilItem(indicadorCredito,
										valorLancamento, descricaoHistoricoCredito, ultimaAlteracao, lancamentoContabil,
										contaContabilCredito);

						colecaoLancamentoContabilItem.add(lancamentoContabilItemCredito);

						/** Item de débito */
						LancamentoContabilItem lancamentoContabilItemDebito = new LancamentoContabilItem(indicadorDebito, valorLancamento,
										descricaoHistoricoDebito, ultimaAlteracao, lancamentoContabil, contaContabilDebito);

						colecaoLancamentoContabilItem.add(lancamentoContabilItemDebito);

					}
				}
				// inseri os itens de lançamento contábeis.
				getControladorBatch().inserirColecaoObjetoParaBatch(colecaoLancamentoContabilItem);
			}

		}catch(Exception ex){
			throw new EJBException(ex);
		}
	}

	/**
	 * Pesquisa a coleção de ids das localidades para processar o lançamentos
	 * contábeis dos devedores duvidosos.
	 * [UC0485] Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 25/06/2007
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisDevedoresDuvidosos(Integer anoMesReferenciaContabil)
					throws ControladorException{

		try{
			return repositorioFinanceiro.pesquisarIdsLocalidadesGerarLancamentosContabeisDevedoresDuvidosos(anoMesReferenciaContabil);

			// erro no hibernate
		}catch(ErroRepositorioException ex){
			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * Remove os lançamentos contábeis e seus respectivos itens
	 * de acordo com os parâmetros informados.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idLancamentoOrigem
	 * @throws ControladorException
	 */
	public void removerLancamentoContabil(Integer anoMesReferenciaContabil, Integer idLocalidade, Integer idLancamentoOrigem)
					throws ControladorException{

		try{
			Collection<Integer> colecaoIdsLancamentosContabeis = repositorioFinanceiro.pesquisarIdsLancamentosContabeis(
							anoMesReferenciaContabil, idLocalidade, idLancamentoOrigem);

			if(colecaoIdsLancamentosContabeis != null && !colecaoIdsLancamentosContabeis.isEmpty()){
				for(Integer idLancamentoContabil : colecaoIdsLancamentosContabeis){
					repositorioFinanceiro.removerItensLancamentoContabil(idLancamentoContabil);
				}

				repositorioFinanceiro.removerLancamentosContabeis(colecaoIdsLancamentosContabeis);
				colecaoIdsLancamentosContabeis = null;
			}
			// erro no hibernate
		}catch(ErroRepositorioException ex){
			// levanta a exceção para a próxima camada
			throw new ControladorException("erro.sistema", ex);
		}
	}

	// ////////////////////////////////////////////////////////////////////////

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 20/07/2007
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorio(String opcaoTotalizacao, int mesAnoReferencia, Integer gerenciaRegional,
					Integer localidade, Integer unidadeNegocio) throws ControladorException{

		Collection retorno = new ArrayList();
		Collection colecaoResumoDevedoresDuvidososRelatorio = null;

		// Converter de mesAno para anoMes para que funcione nas consultas
		int anoMesReferencia = Util.formatarMesAnoParaAnoMes(mesAnoReferencia);
		boolean consultarResumoDevedoresDuvidososRelatorio = true;

		try{

			if(opcaoTotalizacao.equals("estado")){
				colecaoResumoDevedoresDuvidososRelatorio = repositorioFinanceiro
								.consultarResumoDevedoresDuvidososRelatorioPorEstado(anoMesReferencia);

			}else if(opcaoTotalizacao.equals("estadoGerencia")){
				colecaoResumoDevedoresDuvidososRelatorio = repositorioFinanceiro
								.consultarResumoDevedoresDuvidososRelatorioPorEstadoPorGerenciaRegional(anoMesReferencia);

			}else if(opcaoTotalizacao.equals("estadoUnidadeNegocio")){

				consultarResumoDevedoresDuvidososRelatorio = false;

				retorno = consultarResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio(anoMesReferencia);

			}else if(opcaoTotalizacao.equals("estadoLocalidade")){

				consultarResumoDevedoresDuvidososRelatorio = false;

				retorno = consultarResumoDevedoresDuvidososRelatorioPorEstadoPorLocalidade(anoMesReferencia);

			}else if(opcaoTotalizacao.equals("gerenciaRegional")){
				colecaoResumoDevedoresDuvidososRelatorio = repositorioFinanceiro
								.consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(anoMesReferencia, gerenciaRegional);

			}else if(opcaoTotalizacao.equals("gerenciaRegionalLocalidade")){
				colecaoResumoDevedoresDuvidososRelatorio = repositorioFinanceiro
								.consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegionalPorLocalidade(anoMesReferencia,
												gerenciaRegional);

			}else if(opcaoTotalizacao.equals("unidadeNegocio")){
				colecaoResumoDevedoresDuvidososRelatorio = repositorioFinanceiro
								.consultarResumoDevedoresDuvidososRelatorioPorUnidadeNegocio(anoMesReferencia, unidadeNegocio);

			}else if(opcaoTotalizacao.equals("localidade")){
				colecaoResumoDevedoresDuvidososRelatorio = repositorioFinanceiro.consultarResumoDevedoresDuvidososRelatorioPorLocalidade(
								anoMesReferencia, localidade);
			}

			if(consultarResumoDevedoresDuvidososRelatorio){

				Iterator iterator = colecaoResumoDevedoresDuvidososRelatorio.iterator();

				// Prepara cada linha do relatório

				String tipoLancamento = null;
				String itemLancamento = null;
				String itemContabel = null;

				String descGerenciaRegionalAnterior = null;
				String idGerenciaRegionalAnterior = null;
				String descLocalidadeAnterior = null;
				String idLocalidadeAnterior = null;
				String descLancamentoTipoSuperior = "";

				String descUnidadeNegocioAnterior = null;
				String idUnidadeNegocioAnterior = null;

				Object[] elementAnterior = new Object[13];
				BigDecimal[] arrayValores = new BigDecimal[5];

				Boolean agrupaPorGerencia = false;
				if(opcaoTotalizacao.equalsIgnoreCase("estadoGerencia") || opcaoTotalizacao.equalsIgnoreCase("gerenciaRegional")){
					agrupaPorGerencia = true;
				}

				Boolean agrupaPorLocalidade = false;
				if(opcaoTotalizacao.equalsIgnoreCase("estadoLocalidade") || opcaoTotalizacao.equalsIgnoreCase("gerenciaRegionalLocalidade")
								|| opcaoTotalizacao.equalsIgnoreCase("localidade")){
					agrupaPorLocalidade = true;
				}

				Boolean agrupaPorUnidadeNegocio = false;
				if(opcaoTotalizacao.equalsIgnoreCase("unidadeNegocio") || opcaoTotalizacao.equalsIgnoreCase("estadoUnidadeNegocio")){
					agrupaPorUnidadeNegocio = true;
				}

				while(iterator.hasNext()){
					Object[] element = null;
					String tempTipoLancamento = null;
					String tempItemLancamento = null;
					String tempItemContabel = null;

					element = (Object[]) iterator.next();

					if(tipoLancamento == null){
						tipoLancamento = (String) element[1];
						itemLancamento = (String) element[2];
						itemContabel = (String) element[3];
					}

					tempTipoLancamento = (String) element[1];
					tempItemLancamento = (String) element[2];
					tempItemContabel = (String) element[3];

					boolean condicaoIgual = true;
					// compara se o registro atual eh do
					// mesmo tipo de Recebimento, mesmo tipo de lançamento
					// e mesmo item de lançamento do registro anterior
					if(tipoLancamento.equals(tempTipoLancamento) && itemLancamento.equals(tempItemLancamento)){

						// se o registro possuir item contabel
						// compara se eh do mesmo item contabel do registro anterior
						if(itemContabel == null && tempItemContabel == null
										|| (itemContabel != null && tempItemContabel != null && itemContabel.equals(tempItemContabel))){

							// se for agrupado por gerencia
							// compara se o registro atual eh da
							// mesma gerencia regional do registro anterior
							if(!agrupaPorGerencia || descGerenciaRegionalAnterior == null
											|| (agrupaPorGerencia && descGerenciaRegionalAnterior.equalsIgnoreCase((String) element[9]))){

								switch(((Integer) element[8]).intValue()){
									case 1:
										arrayValores[0] = (BigDecimal) element[0];
										break;
									case 2:
										arrayValores[1] = (BigDecimal) element[0];
										break;
									case 3:
										arrayValores[2] = (BigDecimal) element[0];
										break;
									case 4:
										arrayValores[4] = (BigDecimal) element[0];
										break;
								}
							}else{
								condicaoIgual = false;
							}

						}else{

							condicaoIgual = false;
						}

					}else{

						condicaoIgual = false;

					}

					if(!condicaoIgual){

						ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
										(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
										(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
										(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
										idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior,
										descLancamentoTipoSuperior, descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

						retorno.add(ResumoDevedoresDuvidososRelatorioHelper);

						arrayValores = new BigDecimal[5];
						switch(((Integer) element[8]).intValue()){
							case 1:
								arrayValores[0] = (BigDecimal) element[0];
								break;
							case 2:
								arrayValores[1] = (BigDecimal) element[0];
								break;
							case 3:
								arrayValores[2] = (BigDecimal) element[0];
								break;
							case 4:
								arrayValores[4] = (BigDecimal) element[0];
								break;
						}

					}

					elementAnterior[1] = element[1]; // descricaoTipoLancamento
					if(((String) element[1]).equalsIgnoreCase((String) element[2])){
						elementAnterior[2] = null; // descricaoItemLancamento
					}else{
						elementAnterior[2] = element[2]; // descricaoItemLancamento
					}

					elementAnterior[3] = element[3]; // descricaoItemContabil
					elementAnterior[4] = element[4]; // indicadorImpressao
					elementAnterior[5] = element[5]; // indicadorTotal
					elementAnterior[6] = element[6]; // lancamentoTipo
					elementAnterior[7] = element[7]; // lancamentoTipoSuperior

					// identifica pelo que vai ser "quebrado" o relátorio
					if(agrupaPorGerencia){
						// quebra página por Gerência Regional e não mostra a Localidade
						descGerenciaRegionalAnterior = "" + element[9];
						idGerenciaRegionalAnterior = "" + element[10];
					}
					if(agrupaPorLocalidade){
						if(opcaoTotalizacao.equalsIgnoreCase("estadoLocalidade")
										|| opcaoTotalizacao.equalsIgnoreCase("gerenciaRegionalLocalidade")){
							// quebra a página por Localidade e mostra a Gerência
							// Regional
							descGerenciaRegionalAnterior = "" + element[9];
							idGerenciaRegionalAnterior = "" + element[10];
							descLocalidadeAnterior = "" + element[11];
							idLocalidadeAnterior = "" + element[12];
						}else{
							// quebra a página por Localidade e não mostra a Gerência
							// Regional
							descLocalidadeAnterior = "" + element[9];
							idLocalidadeAnterior = "" + element[10];
						}
					}

					if(agrupaPorUnidadeNegocio){
						descUnidadeNegocioAnterior = "" + element[9];
						idUnidadeNegocioAnterior = "" + element[10];

					}

					tipoLancamento = tempTipoLancamento;
					itemLancamento = tempItemLancamento;
					itemContabel = tempItemContabel;

				}

				if(colecaoResumoDevedoresDuvidososRelatorio != null && !colecaoResumoDevedoresDuvidososRelatorio.isEmpty()){
					// adiciona a ultima linha

					ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
									(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
									(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
									(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
									idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
									descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

					retorno.add(ResumoDevedoresDuvidososRelatorioHelper);
				}

			}

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 20/07/2007
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio(int anoMesReferencia) throws ControladorException{

		Collection colecaoResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio = new ArrayList();
		Collection retorno = new ArrayList();

		try{
			colecaoResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio = repositorioFinanceiro
							.consultarResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio(anoMesReferencia);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Iterator iterator = colecaoResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio.iterator();

		// Prepara cada linha do relatório
		String tipoLancamento = null;
		String itemLancamento = null;
		String itemContabel = null;

		String descGerenciaRegionalAnterior = null;
		String idGerenciaRegionalAnterior = null;
		String descLocalidadeAnterior = null;
		String idLocalidadeAnterior = null;
		String descLancamentoTipoSuperior = "";

		String descUnidadeNegocioAnterior = null;
		String idUnidadeNegocioAnterior = null;

		Object[] elementAnterior = new Object[13];
		BigDecimal[] arrayValores = new BigDecimal[5];

		while(iterator.hasNext()){
			Object[] element = null;
			String tempTipoLancamento = null;
			String tempItemLancamento = null;
			String tempItemContabel = null;

			element = (Object[]) iterator.next();

			if(tipoLancamento == null){
				tipoLancamento = (String) element[1];
				itemLancamento = (String) element[2];
				itemContabel = (String) element[3];
			}

			tempTipoLancamento = (String) element[1];
			tempItemLancamento = (String) element[2];
			tempItemContabel = (String) element[3];

			boolean condicaoIgual = true;
			// compara se o registro atual eh do
			// mesmo tipo de Recebimento, mesmo tipo de lançamento
			// e mesmo item de lançamento do registro anterior
			if(tipoLancamento.equals(tempTipoLancamento) && itemLancamento.equals(tempItemLancamento)){

				// se o registro possuir item contabel
				// compara se eh do mesmo item contabel do registro anterior
				if(itemContabel == null && tempItemContabel == null
								|| (itemContabel != null && tempItemContabel != null && itemContabel.equals(tempItemContabel))){

					// se for agrupado por gerencia
					// compara se o registro atual eh da
					// mesma gerencia regional do registro anterior

					switch(((Integer) element[8]).intValue()){
						case 1:
							arrayValores[0] = (BigDecimal) element[0];
							break;
						case 2:
							arrayValores[1] = (BigDecimal) element[0];
							break;
						case 3:
							arrayValores[2] = (BigDecimal) element[0];
							break;
						case 4:
							arrayValores[4] = (BigDecimal) element[0];
							break;
					}

				}else{

					condicaoIgual = false;
				}

			}else{

				condicaoIgual = false;

			}

			if(!condicaoIgual){

				ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
								(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
								(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
								(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
								idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
								descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

				retorno.add(ResumoDevedoresDuvidososRelatorioHelper);

				arrayValores = new BigDecimal[5];
				switch(((Integer) element[8]).intValue()){
					case 1:
						arrayValores[0] = (BigDecimal) element[0];
						break;
					case 2:
						arrayValores[1] = (BigDecimal) element[0];
						break;
					case 3:
						arrayValores[2] = (BigDecimal) element[0];
						break;
					case 4:
						arrayValores[4] = (BigDecimal) element[0];
						break;
				}

			}

			if(idGerenciaRegionalAnterior != null && !idGerenciaRegionalAnterior.equals("" + element[14])){
				// quebra por gerencia
				retorno = consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(anoMesReferencia, new Integer(
								idGerenciaRegionalAnterior), retorno);
			}

			elementAnterior[1] = element[1]; // descricaoTipoLancamento
			if(((String) element[1]).equalsIgnoreCase((String) element[2])){
				elementAnterior[2] = null; // descricaoItemLancamento
			}else{
				elementAnterior[2] = element[2]; // descricaoItemLancamento
			}

			elementAnterior[3] = element[3]; // descricaoItemContabil
			elementAnterior[4] = element[4]; // indicadorImpressao
			elementAnterior[5] = element[5]; // indicadorTotal
			elementAnterior[6] = element[6]; // lancamentoTipo
			elementAnterior[7] = element[7]; // lancamentoTipoSuperior

			descUnidadeNegocioAnterior = "" + element[9];
			idUnidadeNegocioAnterior = "" + element[10];

			descGerenciaRegionalAnterior = "" + element[13];
			idGerenciaRegionalAnterior = "" + element[14];

			tipoLancamento = tempTipoLancamento;
			itemLancamento = tempItemLancamento;
			itemContabel = tempItemContabel;

		}

		if(colecaoResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio != null
						&& !colecaoResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio.isEmpty()){
			// adiciona a ultima linha

			ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
							(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
							(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
							(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
							idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
							descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

			retorno.add(ResumoDevedoresDuvidososRelatorioHelper);

			retorno = consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(anoMesReferencia, new Integer(
							idGerenciaRegionalAnterior), retorno);

		}

		return retorno;

	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 20/07/2007
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(int anoMesReferencia, Integer idGerenciaRegional,
					Collection colecaoResumoDevedoresDuvidososRelatorio) throws ControladorException{

		Collection colecaoResumoDevedoresDuvidososRelatorioPorGerenciaRegional = new ArrayList();

		try{
			colecaoResumoDevedoresDuvidososRelatorioPorGerenciaRegional = repositorioFinanceiro
							.consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(anoMesReferencia, idGerenciaRegional);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Iterator iteratorResumoDevedoresDuvidososRelatorioPorGerenciaRegional = colecaoResumoDevedoresDuvidososRelatorioPorGerenciaRegional
						.iterator();

		// Prepara cada linha do relatório
		String tipoLancamento = null;
		String itemLancamento = null;
		String itemContabel = null;

		String descGerenciaRegionalAnterior = null;
		String idGerenciaRegionalAnterior = null;
		String descLocalidadeAnterior = null;
		String idLocalidadeAnterior = null;
		String descLancamentoTipoSuperior = "";

		String descUnidadeNegocioAnterior = null;
		String idUnidadeNegocioAnterior = null;

		Object[] elementAnterior = new Object[13];
		BigDecimal[] arrayValores = new BigDecimal[5];

		Boolean agrupaPorGerencia = true;

		while(iteratorResumoDevedoresDuvidososRelatorioPorGerenciaRegional.hasNext()){
			Object[] element = null;
			String tempTipoLancamento = null;
			String tempItemLancamento = null;
			String tempItemContabel = null;

			element = (Object[]) iteratorResumoDevedoresDuvidososRelatorioPorGerenciaRegional.next();

			if(tipoLancamento == null){
				tipoLancamento = (String) element[1];
				itemLancamento = (String) element[2];
				itemContabel = (String) element[3];
			}

			tempTipoLancamento = (String) element[1];
			tempItemLancamento = (String) element[2];
			tempItemContabel = (String) element[3];

			boolean condicaoIgual = true;
			// compara se o registro atual eh do
			// mesmo tipo de Recebimento, mesmo tipo de lançamento
			// e mesmo item de lançamento do registro anterior
			if(tipoLancamento.equals(tempTipoLancamento) && itemLancamento.equals(tempItemLancamento)){

				// se o registro possuir item contabel
				// compara se eh do mesmo item contabel do registro anterior
				if(itemContabel == null && tempItemContabel == null
								|| (itemContabel != null && tempItemContabel != null && itemContabel.equals(tempItemContabel))){

					// se for agrupado por gerencia
					// compara se o registro atual eh da
					// mesma gerencia regional do registro anterior
					if(!agrupaPorGerencia || descGerenciaRegionalAnterior == null
									|| (agrupaPorGerencia && descGerenciaRegionalAnterior.equalsIgnoreCase((String) element[9]))){

						switch(((Integer) element[8]).intValue()){
							case 1:
								arrayValores[0] = (BigDecimal) element[0];
								break;
							case 2:
								arrayValores[1] = (BigDecimal) element[0];
								break;
							case 3:
								arrayValores[2] = (BigDecimal) element[0];
								break;
							case 4:
								arrayValores[4] = (BigDecimal) element[0];
								break;
						}
					}else{
						condicaoIgual = false;
					}

				}else{

					condicaoIgual = false;
				}

			}else{

				condicaoIgual = false;

			}

			if(!condicaoIgual){

				ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
								(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
								(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
								(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
								idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
								descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

				colecaoResumoDevedoresDuvidososRelatorio.add(ResumoDevedoresDuvidososRelatorioHelper);

				arrayValores = new BigDecimal[5];
				switch(((Integer) element[8]).intValue()){
					case 1:
						arrayValores[0] = (BigDecimal) element[0];
						break;
					case 2:
						arrayValores[1] = (BigDecimal) element[0];
						break;
					case 3:
						arrayValores[2] = (BigDecimal) element[0];
						break;
					case 4:
						arrayValores[4] = (BigDecimal) element[0];
						break;
				}

			}

			elementAnterior[1] = element[1]; // descricaoTipoLancamento
			if(((String) element[1]).equalsIgnoreCase((String) element[2])){
				elementAnterior[2] = null; // descricaoItemLancamento
			}else{
				elementAnterior[2] = element[2]; // descricaoItemLancamento
			}

			elementAnterior[3] = element[3]; // descricaoItemContabil
			elementAnterior[4] = element[4]; // indicadorImpressao
			elementAnterior[5] = element[5]; // indicadorTotal
			elementAnterior[6] = element[6]; // lancamentoTipo
			elementAnterior[7] = element[7]; // lancamentoTipoSuperior

			descGerenciaRegionalAnterior = "" + element[9];
			idGerenciaRegionalAnterior = "" + element[10];

			tipoLancamento = tempTipoLancamento;
			itemLancamento = tempItemLancamento;
			itemContabel = tempItemContabel;

		}

		if(colecaoResumoDevedoresDuvidososRelatorioPorGerenciaRegional != null
						&& !colecaoResumoDevedoresDuvidososRelatorioPorGerenciaRegional.isEmpty()){
			// adiciona a ultima linha

			ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
							(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
							(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
							(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
							idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
							descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

			colecaoResumoDevedoresDuvidososRelatorio.add(ResumoDevedoresDuvidososRelatorioHelper);
		}

		return colecaoResumoDevedoresDuvidososRelatorio;

	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 20/07/2007
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstadoPorLocalidade(int anoMesReferencia) throws ControladorException{

		Collection colecaoResumoDevedoresDuvidososRelatorioEstadoPorLocalidade = new ArrayList();
		Collection retorno = new ArrayList();

		try{
			colecaoResumoDevedoresDuvidososRelatorioEstadoPorLocalidade = repositorioFinanceiro
							.consultarResumoDevedoresDuvidososRelatorioPorEstadoPorLocalidade(anoMesReferencia);

			if(colecaoResumoDevedoresDuvidososRelatorioEstadoPorLocalidade != null
							&& !colecaoResumoDevedoresDuvidososRelatorioEstadoPorLocalidade.isEmpty()){

				Iterator iterator = colecaoResumoDevedoresDuvidososRelatorioEstadoPorLocalidade.iterator();

				// Prepara cada linha do relatório

				String tipoLancamento = null;
				String itemLancamento = null;
				String itemContabel = null;

				String descGerenciaRegionalAnterior = null;
				String idGerenciaRegionalAnterior = null;
				String descLocalidadeAnterior = null;
				String idLocalidadeAnterior = null;
				String descLancamentoTipoSuperior = "";

				String descUnidadeNegocioAnterior = null;
				String idUnidadeNegocioAnterior = null;

				Object[] elementAnterior = new Object[13];
				BigDecimal[] arrayValores = new BigDecimal[5];

				while(iterator.hasNext()){
					Object[] element = null;
					String tempTipoLancamento = null;
					String tempItemLancamento = null;
					String tempItemContabel = null;

					element = (Object[]) iterator.next();

					if(tipoLancamento == null){
						tipoLancamento = (String) element[1];
						itemLancamento = (String) element[2];
						itemContabel = (String) element[3];
					}

					tempTipoLancamento = (String) element[1];
					tempItemLancamento = (String) element[2];
					tempItemContabel = (String) element[3];

					boolean condicaoIgual = true;
					// compara se o registro atual eh do
					// mesmo tipo de Recebimento, mesmo tipo de lançamento
					// e mesmo item de lançamento do registro anterior
					if(tipoLancamento.equals(tempTipoLancamento) && itemLancamento.equals(tempItemLancamento)){

						// se o registro possuir item contabel
						// compara se eh do mesmo item contabel do registro anterior
						if(itemContabel == null && tempItemContabel == null
										|| (itemContabel != null && tempItemContabel != null && itemContabel.equals(tempItemContabel))){

							switch(((Integer) element[8]).intValue()){
								case 1:
									arrayValores[0] = (BigDecimal) element[0];
									break;
								case 2:
									arrayValores[1] = (BigDecimal) element[0];
									break;
								case 3:
									arrayValores[2] = (BigDecimal) element[0];
									break;
								case 4:
									arrayValores[4] = (BigDecimal) element[0];
									break;
							}

						}else{

							condicaoIgual = false;
						}

					}else{

						condicaoIgual = false;

					}

					if(!condicaoIgual){

						ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
										(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
										(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
										(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
										idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior,
										descLancamentoTipoSuperior, descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

						retorno.add(ResumoDevedoresDuvidososRelatorioHelper);

						arrayValores = new BigDecimal[5];
						switch(((Integer) element[8]).intValue()){
							case 1:
								arrayValores[0] = (BigDecimal) element[0];
								break;
							case 2:
								arrayValores[1] = (BigDecimal) element[0];
								break;
							case 3:
								arrayValores[2] = (BigDecimal) element[0];
								break;
							case 4:
								arrayValores[4] = (BigDecimal) element[0];
								break;
						}

					}

					if(idUnidadeNegocioAnterior != null && !idUnidadeNegocioAnterior.equals("" + element[16])){
						// quebra por Unidade Negocio
						retorno = consultarResumoDevedoresDuvidososRelatorioPorUnidadeNegocio(anoMesReferencia, new Integer(
										idUnidadeNegocioAnterior), retorno);

					}

					if(idGerenciaRegionalAnterior != null && !idGerenciaRegionalAnterior.equals("" + element[10])){
						// quebra por gerencia
						retorno = consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(anoMesReferencia, new Integer(
										idGerenciaRegionalAnterior), retorno);

					}

					elementAnterior[1] = element[1]; // descricaoTipoLancamento
					if(((String) element[1]).equalsIgnoreCase((String) element[2])){
						elementAnterior[2] = null; // descricaoItemLancamento
					}else{
						elementAnterior[2] = element[2]; // descricaoItemLancamento
					}

					elementAnterior[3] = element[3]; // descricaoItemContabil
					elementAnterior[4] = element[4]; // indicadorImpressao
					elementAnterior[5] = element[5]; // indicadorTotal
					elementAnterior[6] = element[6]; // lancamentoTipo
					elementAnterior[7] = element[7]; // lancamentoTipoSuperior

					descGerenciaRegionalAnterior = "" + element[9];
					idGerenciaRegionalAnterior = "" + element[10];
					descLocalidadeAnterior = "" + element[11];
					idLocalidadeAnterior = "" + element[12];
					descUnidadeNegocioAnterior = "" + element[15];
					idUnidadeNegocioAnterior = "" + element[16];

					tipoLancamento = tempTipoLancamento;
					itemLancamento = tempItemLancamento;
					itemContabel = tempItemContabel;

				}

				if(colecaoResumoDevedoresDuvidososRelatorioEstadoPorLocalidade != null
								&& !colecaoResumoDevedoresDuvidososRelatorioEstadoPorLocalidade.isEmpty()){
					// adiciona a ultima linha

					ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
									(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
									(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
									(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
									idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
									descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

					retorno.add(ResumoDevedoresDuvidososRelatorioHelper);

					retorno = consultarResumoDevedoresDuvidososRelatorioPorUnidadeNegocio(anoMesReferencia, new Integer(
									idUnidadeNegocioAnterior), retorno);

					retorno = consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(anoMesReferencia, new Integer(
									idGerenciaRegionalAnterior), retorno);

					retorno = consultarResumoDevedoresDuvidososRelatorioPorEstado(anoMesReferencia, retorno);
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		return retorno;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 20/07/2007
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer idUnidadeNegocio,
					Collection colecaoResumoDevedoresDuvidososRelatorio) throws ControladorException{

		Collection colecaoResumoDevedoresDuvidososRelatorioPorUnidadeNegocio = new ArrayList();

		try{
			colecaoResumoDevedoresDuvidososRelatorioPorUnidadeNegocio = repositorioFinanceiro
							.consultarResumoDevedoresDuvidososRelatorioPorUnidadeNegocio(anoMesReferencia, idUnidadeNegocio);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Iterator iterator = colecaoResumoDevedoresDuvidososRelatorioPorUnidadeNegocio.iterator();

		String tipoLancamento = null;
		String itemLancamento = null;
		String itemContabel = null;

		String descGerenciaRegionalAnterior = null;
		String idGerenciaRegionalAnterior = null;
		String descLocalidadeAnterior = null;
		String idLocalidadeAnterior = null;
		String descLancamentoTipoSuperior = "";

		String descUnidadeNegocioAnterior = null;
		String idUnidadeNegocioAnterior = null;

		Object[] elementAnterior = new Object[13];
		BigDecimal[] arrayValores = new BigDecimal[5];

		while(iterator.hasNext()){
			Object[] element = null;
			String tempTipoLancamento = null;
			String tempItemLancamento = null;
			String tempItemContabel = null;

			element = (Object[]) iterator.next();

			if(tipoLancamento == null){
				tipoLancamento = (String) element[1];
				itemLancamento = (String) element[2];
				itemContabel = (String) element[3];
			}

			tempTipoLancamento = (String) element[1];
			tempItemLancamento = (String) element[2];
			tempItemContabel = (String) element[3];

			boolean condicaoIgual = true;
			// compara se o registro atual eh do
			// mesmo tipo de Recebimento, mesmo tipo de lançamento
			// e mesmo item de lançamento do registro anterior
			if(tipoLancamento.equals(tempTipoLancamento) && itemLancamento.equals(tempItemLancamento)){

				// se o registro possuir item contabel
				// compara se eh do mesmo item contabel do registro anterior
				if(itemContabel == null && tempItemContabel == null
								|| (itemContabel != null && tempItemContabel != null && itemContabel.equals(tempItemContabel))){

					switch(((Integer) element[8]).intValue()){
						case 1:
							arrayValores[0] = (BigDecimal) element[0];
							break;
						case 2:
							arrayValores[1] = (BigDecimal) element[0];
							break;
						case 3:
							arrayValores[2] = (BigDecimal) element[0];
							break;
						case 4:
							arrayValores[4] = (BigDecimal) element[0];
							break;
					}

				}else{

					condicaoIgual = false;
				}

			}else{

				condicaoIgual = false;

			}

			if(!condicaoIgual){

				ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
								(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
								(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
								(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
								idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
								descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

				colecaoResumoDevedoresDuvidososRelatorio.add(ResumoDevedoresDuvidososRelatorioHelper);

				arrayValores = new BigDecimal[5];
				switch(((Integer) element[8]).intValue()){
					case 1:
						arrayValores[0] = (BigDecimal) element[0];
						break;
					case 2:
						arrayValores[1] = (BigDecimal) element[0];
						break;
					case 3:
						arrayValores[2] = (BigDecimal) element[0];
						break;
					case 4:
						arrayValores[4] = (BigDecimal) element[0];
						break;
				}

			}

			elementAnterior[1] = element[1]; // descricaoTipoLancamento
			if(((String) element[1]).equalsIgnoreCase((String) element[2])){
				elementAnterior[2] = null; // descricaoItemLancamento
			}else{
				elementAnterior[2] = element[2]; // descricaoItemLancamento
			}

			elementAnterior[3] = element[3]; // descricaoItemContabil
			elementAnterior[4] = element[4]; // indicadorImpressao
			elementAnterior[5] = element[5]; // indicadorTotal
			elementAnterior[6] = element[6]; // lancamentoTipo
			elementAnterior[7] = element[7]; // lancamentoTipoSuperior

			descGerenciaRegionalAnterior = "" + element[13];
			idGerenciaRegionalAnterior = "" + element[14];
			descUnidadeNegocioAnterior = "" + element[9];
			idUnidadeNegocioAnterior = "" + element[10];

			tipoLancamento = tempTipoLancamento;
			itemLancamento = tempItemLancamento;
			itemContabel = tempItemContabel;

		}

		if(colecaoResumoDevedoresDuvidososRelatorio != null && !colecaoResumoDevedoresDuvidososRelatorio.isEmpty()){
			// adiciona a ultima linha

			ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
							(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
							(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
							(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
							idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
							descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

			colecaoResumoDevedoresDuvidososRelatorio.add(ResumoDevedoresDuvidososRelatorioHelper);
		}

		return colecaoResumoDevedoresDuvidososRelatorio;
	}

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 20/07/2007
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstado(int anoMesReferencia,
					Collection colecaoResumoDevedoresDuvidososRelatorio) throws ControladorException{

		Collection colecaoResumoDevedoresDuvidososRelatorioPorEstado = new ArrayList();

		try{
			colecaoResumoDevedoresDuvidososRelatorioPorEstado = repositorioFinanceiro
							.consultarResumoDevedoresDuvidososRelatorioPorEstado(anoMesReferencia);

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Iterator iterator = colecaoResumoDevedoresDuvidososRelatorioPorEstado.iterator();

		String tipoLancamento = null;
		String itemLancamento = null;
		String itemContabel = null;

		String descGerenciaRegionalAnterior = null;
		String idGerenciaRegionalAnterior = null;
		String descLocalidadeAnterior = null;
		String idLocalidadeAnterior = null;
		String descLancamentoTipoSuperior = "";

		String descUnidadeNegocioAnterior = null;
		String idUnidadeNegocioAnterior = null;

		Object[] elementAnterior = new Object[13];
		BigDecimal[] arrayValores = new BigDecimal[5];

		while(iterator.hasNext()){
			Object[] element = null;
			String tempTipoLancamento = null;
			String tempItemLancamento = null;
			String tempItemContabel = null;

			element = (Object[]) iterator.next();

			if(tipoLancamento == null){
				tipoLancamento = (String) element[1];
				itemLancamento = (String) element[2];
				itemContabel = (String) element[3];
			}

			tempTipoLancamento = (String) element[1];
			tempItemLancamento = (String) element[2];
			tempItemContabel = (String) element[3];

			boolean condicaoIgual = true;
			// compara se o registro atual eh do
			// mesmo tipo de Recebimento, mesmo tipo de lançamento
			// e mesmo item de lançamento do registro anterior
			if(tipoLancamento.equals(tempTipoLancamento) && itemLancamento.equals(tempItemLancamento)){

				// se o registro possuir item contabel
				// compara se eh do mesmo item contabel do registro anterior
				if(itemContabel == null && tempItemContabel == null
								|| (itemContabel != null && tempItemContabel != null && itemContabel.equals(tempItemContabel))){

					switch(((Integer) element[8]).intValue()){
						case 1:
							arrayValores[0] = (BigDecimal) element[0];
							break;
						case 2:
							arrayValores[1] = (BigDecimal) element[0];
							break;
						case 3:
							arrayValores[2] = (BigDecimal) element[0];
							break;
						case 4:
							arrayValores[4] = (BigDecimal) element[0];
							break;
					}

				}else{

					condicaoIgual = false;
				}

			}else{

				condicaoIgual = false;

			}

			if(!condicaoIgual){

				ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
								(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
								(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
								(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
								idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
								descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

				colecaoResumoDevedoresDuvidososRelatorio.add(ResumoDevedoresDuvidososRelatorioHelper);

				arrayValores = new BigDecimal[5];
				switch(((Integer) element[8]).intValue()){
					case 1:
						arrayValores[0] = (BigDecimal) element[0];
						break;
					case 2:
						arrayValores[1] = (BigDecimal) element[0];
						break;
					case 3:
						arrayValores[2] = (BigDecimal) element[0];
						break;
					case 4:
						arrayValores[4] = (BigDecimal) element[0];
						break;
				}

			}

			elementAnterior[1] = element[1]; // descricaoTipoLancamento
			if(((String) element[1]).equalsIgnoreCase((String) element[2])){
				elementAnterior[2] = null; // descricaoItemLancamento
			}else{
				elementAnterior[2] = element[2]; // descricaoItemLancamento
			}

			elementAnterior[3] = element[3]; // descricaoItemContabil
			elementAnterior[4] = element[4]; // indicadorImpressao
			elementAnterior[5] = element[5]; // indicadorTotal
			elementAnterior[6] = element[6]; // lancamentoTipo
			elementAnterior[7] = element[7]; // lancamentoTipoSuperior

			tipoLancamento = tempTipoLancamento;
			itemLancamento = tempItemLancamento;
			itemContabel = tempItemContabel;

		}

		if(colecaoResumoDevedoresDuvidososRelatorioPorEstado != null && !colecaoResumoDevedoresDuvidososRelatorioPorEstado.isEmpty()){
			// adiciona a ultima linha

			ResumoDevedoresDuvidososRelatorioHelper ResumoDevedoresDuvidososRelatorioHelper = new ResumoDevedoresDuvidososRelatorioHelper(
							(BigDecimal[]) arrayValores, (String) elementAnterior[1], (String) elementAnterior[2],
							(String) elementAnterior[3], (Short) elementAnterior[4], (Short) elementAnterior[5],
							(Integer) elementAnterior[6], (Integer) elementAnterior[7], false, descGerenciaRegionalAnterior,
							idGerenciaRegionalAnterior, descLocalidadeAnterior, idLocalidadeAnterior, descLancamentoTipoSuperior,
							descUnidadeNegocioAnterior, idUnidadeNegocioAnterior);

			colecaoResumoDevedoresDuvidososRelatorio.add(ResumoDevedoresDuvidososRelatorioHelper);
		}

		return colecaoResumoDevedoresDuvidososRelatorio;
	}

	/**
	 * [UC0718] Gerar Relatório de Evolucao do Contas a Receber Contabil
	 * 
	 * @author Francisco Junior
	 * @date 02/01/08
	 * @param opcaoTotalizacao
	 * @param mesAno
	 * @param codigoGerencia
	 * @param codigoLocalidade
	 * @param unidadeNegocio
	 * @return Colecao
	 * @throws ControladorException
	 */
	public Collection<RelatorioEvolucaoContasAReceberContabilBean> consultarDadosEvolucaoContasAReceberContabilRelatorio(
					String opcaoTotalizacao, int mesAno, Integer codigoGerencia, Integer codigoLocalidade, Integer unidadeNegocio)
					throws ControladorException{

		// este mapeamento é composto de uma descrição do grupo como chave (localidade, gerencia ou
		// unidade de negocio)
		// e a coleção de valores associado a esta chave
		HashMap<String, BigDecimal[][]> dadosAgrupados = new HashMap<String, BigDecimal[][]>();
		int anoMes = Util.formatarMesAnoParaAnoMes(mesAno);
		// As linhas de detalhes são fixas e nesta ordem
		String descricoes[] = {"S A L D O    A N T E R I O R", "(+) FATURAMENTO AGUA", "(+) FATURAMENTO ESGOTO", "(+) FINANCIAMENTOS INCLUIDOS", "(+) JUROS DE PARCELAMENTO", "(+) GUIAS DE PAGAMENTO", "(+) INCLUSOES POR REFATURAMENTO", "(-) FINANCIAMENTOS CANCELADOS", "(-) PARCELAMENTOS CANCELADOS", "(-) CANCELAMENTOS POR REFATURAMENTO", "(=) FATURAMENTO DO MES", "(+) DOACOES COBRADAS EM CONTA", "(-) VALORES DEVOLVIDOS NAS CONTAS", "(-) IMPOSTOS DEDUZIDOS", "(=) ARRECADAVEL", "(-) RECEBIMENTOS CLASSIFICADOS", "(-) RECEBIMENTOS DE MESES ANTERIORES CLASSIFICADOS NO MES", "S A L D O    A T U A L"};

		Collection pesquisaSaldo = new ArrayList();
		Collection pesquisaDadosFaturamento = new ArrayList();
		Collection pesquisaDadosRecebimentos = new ArrayList();

		try{

			if(opcaoTotalizacao.equals("estado")){
				// quando os parametros gerencia, unidade de negocio e localidade são passados
				// nulos,
				// a pesquisa é feita sem restrição, trazendo os dados referentes a todo o estado.
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes, null, null,
								null);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes,
								null, null, null);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorEstado(anoMes,
								null, null, null);

			}else if(opcaoTotalizacao.equals("estadoGerencia")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorGerenciaRegional(anoMes);
				// pesquisaSaldo.addAll(
				// repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorEstado(
				// anoMes, null, null, null)
				// );

				pesquisaDadosFaturamento = repositorioFinanceiro
								.consultarDadosEvolucaoContasAReceberContabilRelatorioPorGerenciaRegional(anoMes);
				// pesquisaDadosFaturamento.addAll(
				// repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorEstado(
				// anoMes, null, null, null)
				// );

				pesquisaDadosRecebimentos = repositorioFinanceiro
								.consultarRecebimentosContasAReceberContabilRelatorioPorGerenciaRegional(anoMes);
				// pesquisaDadosRecebimentos.addAll(
				// repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorEstado(
				// anoMes, null, null, null)
				// );

			}else if(opcaoTotalizacao.equals("estadoUnidadeNegocio")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(anoMes, null);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(
								anoMes, null);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorUnidadeNegocio(
								anoMes, null);

			}else if(opcaoTotalizacao.equals("estadoLocalidade")){
				pesquisaSaldo = repositorioFinanceiro
								.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorLocalidade(anoMes, null, null);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorLocalidade(anoMes,
								null, null);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorLocalidade(anoMes,
								null, null);

			}else if(opcaoTotalizacao.equals("gerenciaRegional")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes,
								codigoGerencia, null, null);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes,
								codigoGerencia, null, null);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorEstado(anoMes,
								codigoGerencia, null, null);
			}else if(opcaoTotalizacao.equals("gerenciaRegionalUnidadeNegocio")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(anoMes,
								codigoGerencia);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(
								anoMes, codigoGerencia);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorUnidadeNegocio(
								anoMes, codigoGerencia);
			}else if(opcaoTotalizacao.equals("gerenciaRegionalLocalidade")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorLocalidade(anoMes,
								codigoGerencia, null);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorLocalidade(anoMes,
								codigoGerencia, null);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorLocalidade(anoMes,
								codigoGerencia, null);

			}else if(opcaoTotalizacao.equals("unidadeNegocioLocalidade")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorLocalidade(anoMes, null,
								unidadeNegocio);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorLocalidade(anoMes,
								null, unidadeNegocio);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorLocalidade(anoMes,
								null, unidadeNegocio);
			}else if(opcaoTotalizacao.equals("unidadeNegocio")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes, null,
								unidadeNegocio, null);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes,
								null, unidadeNegocio, null);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorEstado(anoMes,
								null, unidadeNegocio, null);
			}else if(opcaoTotalizacao.equals("localidade")){
				pesquisaSaldo = repositorioFinanceiro.consultarSaldoEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes, null, null,
								codigoLocalidade);
				pesquisaDadosFaturamento = repositorioFinanceiro.consultarDadosEvolucaoContasAReceberContabilRelatorioPorEstado(anoMes,
								null, null, codigoLocalidade);
				pesquisaDadosRecebimentos = repositorioFinanceiro.consultarRecebimentosContasAReceberContabilRelatorioPorEstado(anoMes,
								null, null, codigoLocalidade);
			}

			// Tratando a consulta dos saldos em conta a receber contabil
			Iterator iter = pesquisaSaldo.iterator();
			Integer idDescAnterior = -1;
			while(iter.hasNext()){
				Object[] element = (Object[]) iter.next();
				String desc = (String) element[0];
				Integer idDesc = (Integer) element[1];
				Integer seqTipoLancamento = (Integer) element[2];
				Integer codigo = (Integer) element[3];
				// recupera o conjunto de valores atual para a chave 'desc'
				BigDecimal[][] valoresItem = dadosAgrupados.get(desc);
				// quando a descrição atual for diferente da anterior é pq é hora de criar um novo
				// grupo de valoresItem
				if(idDesc.intValue() != idDescAnterior.intValue()){
					valoresItem = new BigDecimal[descricoes.length][2];
					for(int i = 0; i < valoresItem.length; i++){
						valoresItem[i][0] = new BigDecimal(0);
						valoresItem[i][1] = new BigDecimal(0);
					}
					// SALDO ANTERIOR
					valoresItem[0] = new BigDecimal[2];
					valoresItem[0][0] = new BigDecimal(0);
					valoresItem[0][1] = new BigDecimal(0);
					dadosAgrupados.put(desc, valoresItem);
				}
				// no select retornou o codigo do tipo de categoria (1 ou 2)
				// entao em cada linha de resultado devemos verificar se foi do particular ou
				// privado
				if(codigo.intValue() == CategoriaTipo.PARTICULAR){
					valoresItem[0][0] = (BigDecimal) element[4];
				}else{
					valoresItem[0][1] = (BigDecimal) element[4];
				}
				idDescAnterior = idDesc;
			}

			// conjunto de valores: cada linha é um item diretamente associado ao array descricoes
			// e na primeira coluna o valor de particular, na segunda, o valor de privado
			// na definição do bean do relatório, o valor total é definido como a soma destes dois
			// em cada linha
			BigDecimal[][] valoresItem = null;
			idDescAnterior = -1;

			// Tratando dados de faturamento
			iter = pesquisaDadosFaturamento.iterator();
			while(iter.hasNext()){
				Object[] element = (Object[]) iter.next();
				String desc = (String) element[0];
				Integer idDesc = (Integer) element[1];
				Short sequenciaTipoLancamento = (Short) element[2];
				Integer tipoCategoria = (Integer) element[3];
				BigDecimal valorItem = (BigDecimal) element[4];

				if(idDescAnterior.intValue() != idDesc.intValue()){
					// continuando o array de valoresItem iniciado com o item SALDO ANTERIOR
					valoresItem = dadosAgrupados.get(desc);
					if(valoresItem == null){
						valoresItem = new BigDecimal[descricoes.length][2];
						for(int i = 0; i < valoresItem.length; i++){
							valoresItem[i][0] = new BigDecimal(0);
							valoresItem[i][1] = new BigDecimal(0);
						}
						dadosAgrupados.put(desc, valoresItem);
					}
				}
				idDescAnterior = idDesc;

				// o indice diz respeito a posicao deste item na exibição, que é também
				// a posição no array valoresItem
				short indiceTipoLancamento = -1;
				switch(sequenciaTipoLancamento.shortValue()){
					case ResumoFaturamento.CANCELAMENTOS_POR_REFATURAMENTO_SEQUENCIA_TIPO_LANCAMENTO_1:
					case ResumoFaturamento.CANCELAMENTOS_POR_REFATURAMENTO_SEQUENCIA_TIPO_LANCAMENTO_2:
						indiceTipoLancamento = 9;
						break;
					case ResumoFaturamento.DOACOES_COBRADAS_EM_CONTA_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 11;
						break;
					case ResumoFaturamento.FATURAMENTO_AGUA_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 1;
						break;
					case ResumoFaturamento.FATURAMENTO_ESGOTO_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 2;
						break;
					case ResumoFaturamento.FINANCIAMENTOS_CANCELADOS_SEQUENCIA_TIPO_LANCAMENTO_1:
					case ResumoFaturamento.FINANCIAMENTOS_CANCELADOS_SEQUENCIA_TIPO_LANCAMENTO_2:
						indiceTipoLancamento = 7;
						break;
					case ResumoFaturamento.FINANCIAMENTOS_INCLUIDOS_SEQUENCIA_TIPO_LANCAMENTO_1:
					case ResumoFaturamento.FINANCIAMENTOS_INCLUIDOS_SEQUENCIA_TIPO_LANCAMENTO_2:
						indiceTipoLancamento = 3;
						break;
					case ResumoFaturamento.GUIAS_PAGAMENTO_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 5;
						break;
					case ResumoFaturamento.IMPOSTOS_DEDUZIDOS_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 13;
						break;
					case ResumoFaturamento.INCLUSOES_POR_REFATURAMENTO_SEQUENCIA_TIPO_LANCAMENTO_1:
					case ResumoFaturamento.INCLUSOES_POR_REFATURAMENTO_SEQUENCIA_TIPO_LANCAMENTO_2:
						indiceTipoLancamento = 6;
						break;
					case ResumoFaturamento.JUROS_PARCELAMENTO_SEQUENCIA_TIPO_LANCAMENTO_1:
					case ResumoFaturamento.JUROS_PARCELAMENTO_SEQUENCIA_TIPO_LANCAMENTO_2:
						indiceTipoLancamento = 4;
						break;
					case ResumoFaturamento.PARCELAMENTOS_CANCELADOS_SEQUENCIA_TIPO_LANCAMENTO_1:
					case ResumoFaturamento.PARCELAMENTOS_CANCELADOS_SEQUENCIA_TIPO_LANCAMENTO_2:
						indiceTipoLancamento = 8;
						break;
					case ResumoFaturamento.VALORES_DEVOLVIDOS_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 12;
						break;
					default:
						// existem valores de sequenciaTipoLancamento que nao serao usados nesse
						// relatorio
						continue;
				}
				if(tipoCategoria.intValue() == CategoriaTipo.PARTICULAR){
					if(valoresItem[indiceTipoLancamento][0] == null){
						valoresItem[indiceTipoLancamento][0] = valorItem;
					}else{
						valoresItem[indiceTipoLancamento][0] = valoresItem[indiceTipoLancamento][0].add(valorItem);
					}
				}else{
					if(valoresItem[indiceTipoLancamento][1] == null){
						valoresItem[indiceTipoLancamento][1] = valorItem;
					}else{
						valoresItem[indiceTipoLancamento][1] = valoresItem[indiceTipoLancamento][0].add(valorItem);
					}
				}

			}

			idDescAnterior = -1;

			// Pesquisando dados de recebimentos referentes ao preenchimento das linhas 15 e 16
			iter = pesquisaDadosRecebimentos.iterator();
			while(iter.hasNext()){
				Object[] element = (Object[]) iter.next();
				String desc = (String) element[0];
				Integer idDesc = (Integer) element[1];
				Short sequenciaTipoLancamento = (Short) element[2];
				Integer tipoCategoria = (Integer) element[3];
				BigDecimal valorItem = (BigDecimal) element[4];

				if(idDescAnterior.intValue() != idDesc.intValue()){
					// continuando o array de valoresItem iniciado com o item SALDO ANTERIOR
					valoresItem = dadosAgrupados.get(desc);
					if(valoresItem == null){
						valoresItem = new BigDecimal[descricoes.length][2];
						for(int i = 0; i < valoresItem.length; i++){
							valoresItem[i][0] = new BigDecimal(0);
							valoresItem[i][1] = new BigDecimal(0);
						}
						dadosAgrupados.put(desc, valoresItem);
					}
				}
				idDescAnterior = idDesc;

				short indiceTipoLancamento = -1;
				switch(sequenciaTipoLancamento.shortValue()){
					case ResumoArrecadacao.RECEBIMENTOS_CLASSIFICADOS_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 15;
						break;
					case ResumoArrecadacao.RECEBIMENTOS_MESES_SEQUENCIA_TIPO_LANCAMENTO:
						indiceTipoLancamento = 16;
						break;
					default:
						continue;
				}
				if(tipoCategoria.intValue() == CategoriaTipo.PARTICULAR){
					if(valoresItem[indiceTipoLancamento][0] == null){
						valoresItem[indiceTipoLancamento][0] = valorItem;
					}else{
						valoresItem[indiceTipoLancamento][0] = valoresItem[indiceTipoLancamento][0].add(valorItem);
					}
				}else{
					if(valoresItem[indiceTipoLancamento][1] == null){
						valoresItem[indiceTipoLancamento][1] = valorItem;
					}else{
						valoresItem[indiceTipoLancamento][1] = valoresItem[indiceTipoLancamento][0].add(valorItem);
					}
				}
			}

		}catch(ErroRepositorioException e){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", e);
		}

		Collection<RelatorioEvolucaoContasAReceberContabilBean> dados = new ArrayList<RelatorioEvolucaoContasAReceberContabilBean>();

		// Colocando em ordem alfabetica as descricoes dos grupos
		ArrayList chavesOrdenadas = new ArrayList(dadosAgrupados.keySet());
		Collections.sort(chavesOrdenadas);
		for(Iterator iter = chavesOrdenadas.iterator(); iter.hasNext();){
			String chave = (String) iter.next();
			BigDecimal[][] valoresItem = dadosAgrupados.get(chave);

			// Calculando a linha 10 Faturamento do mes : SOMA(valor2:valor7) - SOMA(valor8:valor10)
			for(int ind = 1; ind < 7; ind++){
				valoresItem[10][0] = valoresItem[10][0].add(valoresItem[ind][0]);
				valoresItem[10][1] = valoresItem[10][1].add(valoresItem[ind][1]);
			}
			for(int ind = 7; ind < 10; ind++){
				valoresItem[10][0] = valoresItem[10][0].subtract(valoresItem[ind][0]);
				valoresItem[10][1] = valoresItem[10][1].subtract(valoresItem[ind][1]);
			}
			// calculando a linha 14 - Arrecadavel : SOMA(Valor11:Valor12) - SOMA(Valor13:Valor14)
			for(int ind = 10; ind < 12; ind++){
				valoresItem[14][0] = valoresItem[14][0].add(valoresItem[ind][0]);
				valoresItem[14][1] = valoresItem[14][1].add(valoresItem[ind][1]);
			}
			for(int ind = 12; ind < 14; ind++){
				valoresItem[14][0] = valoresItem[14][0].subtract(valoresItem[ind][0]);
				valoresItem[14][1] = valoresItem[14][1].subtract(valoresItem[ind][1]);
			}

			// calculando a linha 17 - Saldo Atual: V1 + V15 - (V16 + V17)
			valoresItem[17][0] = valoresItem[0][0].add(valoresItem[14][0]).subtract(valoresItem[15][0]).subtract(valoresItem[16][0]);
			valoresItem[17][1] = valoresItem[0][1].add(valoresItem[14][1]).subtract(valoresItem[15][1]).subtract(valoresItem[16][1]);

			for(int i = 0; i < descricoes.length; i++){
				RelatorioEvolucaoContasAReceberContabilBean bean = new RelatorioEvolucaoContasAReceberContabilBean(
								(BigDecimal[]) valoresItem[i], descricoes[i]);

				if(opcaoTotalizacao.equals("estado")){
					bean.setTipoGrupo("TOTAL DO ESTADO");
				}else if(opcaoTotalizacao.equals("estadoGerencia")){
					bean.setTipoGrupo("Gerência Regional");

				}else if(opcaoTotalizacao.equals("estadoLocalidade") || opcaoTotalizacao.equals("gerenciaRegionalLocalidade")
								|| opcaoTotalizacao.equals("unidadeNegocioLocalidade")){
					bean.setTipoGrupo("Localidade");
				}else if(opcaoTotalizacao.equals("estadoUnidadeNegocio") || opcaoTotalizacao.equals("gerenciaRegionalUnidadeNegocio")){
					bean.setTipoGrupo("Unidade de Negócio");
				}

				bean.setDescricaoGrupo(chave);
				dados.add(bean);
			}

		}

		return dados;
	}

	/**
	 * Geração / Atualização do Resumo de Faturamento
	 * [UC0207] - Gerar/Atualizar Resumo do Faturamento - chamado de
	 * Contabilização por Evento de Faturamento.
	 * Método recebe uma coleção de objetos, que pode ser Guia de Pagamentos,
	 * Contas, Créditos a Receber, etc. e realiza um tratamento para obter os
	 * valores por itemLancamentoContabil e Categoria, para posterior registro.
	 * Detalhe que o mesmo difere do encerrarFaturamentoMes, pois as entidades
	 * envolvidas ainda não estão persistidas.
	 * 
	 * @param Collection
	 *            <ObjetoTransacao> c -> Coleção de Objetos de Faturamento que serão
	 *            'contabilizados'
	 * @param tipoEventoContabil
	 *            t -> Tipo de Evento que está sendo realizado, baseado na Interface EventoContabil
	 * @author eduardo henrique
	 * @date 06/08/2008, 23/10/2008
	 * @since v0.04
	 * @deprecated Esse método não dever ser utilizado, pois a tabela de Resumo de Faturamento não é
	 *             utilizada
	 */
	public synchronized void contabilizarEventoFaturamento(Collection<ObjetoTransacao> colecaoEntidadesResumoFaturamento,
					Integer tipoEventoContabil) throws ControladorException{

		try{
			SistemaParametro sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();
			if(sistemaParametros == null || sistemaParametros.getAnoMesFaturamento() == null){
				throw new ControladorException("atencao.naocadastrado", null, "Ano/Mês Faturamento Parametros Sistema");
			}

			Collection<Integer> tiposParcelamento = Util
							.converterStringParaColecaoInteger(ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO.executar());

			for(Iterator iterator = colecaoEntidadesResumoFaturamento.iterator(); iterator.hasNext();){
				ObjetoTransacao objetoGeracaoResumo = (ObjetoTransacao) iterator.next();

				if(objetoGeracaoResumo instanceof GuiaPagamento){
					// Realiza o tratamento para a Guia de Pagamento
					GuiaPagamento guiaPagamentoResumo = (GuiaPagamento) objetoGeracaoResumo;

					// Carrega a Unidade de Negócio e a Gerência Regional da
					// Localidade da Guia
					Integer idGerenciaRegional = getControladorLocalidade().pesquisarIdGerenciaParaLocalidade(
									guiaPagamentoResumo.getLocalidade().getId());
					GerenciaRegional gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(idGerenciaRegional);
					guiaPagamentoResumo.getLocalidade().setGerenciaRegional(gerenciaRegional);

					Integer idUnidadeNegocio = getControladorLocalidade().pesquisarIdUnidadeNegocioParaLocalidade(
									guiaPagamentoResumo.getLocalidade().getId());
					UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(idUnidadeNegocio);
					guiaPagamentoResumo.getLocalidade().setUnidadeNegocio(unidadeNegocio);

					if(guiaPagamentoResumo.getImovel().getImovelPerfil() == null){
						throw new ControladorException("atencao.perfil_imovel_guia_pagamento_a_cobrar_nao_definido");
					}

					// Verifica se as Categorias da Guia Pagamento existem
					// Como as Categorias estão divididas por Prestações é necessário acumular antes
					// por Categoria e ItemLancamentoContabil
					// Não se verifica se o tipo de Fin. é Serviço, pois a inclusão só permitem este
					// tipo
					Map mapCategoriasGuiaPagamento = new HashMap();
					for(Iterator iteratorGuiaCategoria = guiaPagamentoResumo.getGuiasPagamentoCategoria().iterator(); iteratorGuiaCategoria
									.hasNext();){
						GuiaPagamentoCategoria guiaPagamentoCategoria = (GuiaPagamentoCategoria) iteratorGuiaCategoria.next();

						if(guiaPagamentoCategoria.getValorCategoria() != null
										&& guiaPagamentoCategoria.getValorCategoria().compareTo(BigDecimal.ZERO) == 1){

							GuiaPagamentoCategoriaHelper beanCategoriaHelper = new GuiaPagamentoCategoriaHelper();
							beanCategoriaHelper.setIdCategoria(guiaPagamentoCategoria.getComp_id().getCategoriaId());
							beanCategoriaHelper.setIdLancamentoItemContabil(guiaPagamentoCategoria.getComp_id()
											.getLancamentoItemContabilId());

							// Regra para Inclusão e Cancelamento são diferentes
							if(tipoEventoContabil.equals(EventoContabil.INCLUSAO_GUIA_PAGAMENTO)){

								beanCategoriaHelper.setValorAcumulado(guiaPagamentoCategoria.getValorCategoria());

							}else if(tipoEventoContabil.equals(EventoContabil.CANCELAMENTO_GUIA_PAGAMENTO)){
								// Itera nas Prestações 'Correspondentes' da Categoria e verifica se
								// seus Valores e que débitos foram Cancelados
								BigDecimal valorCancelamentoCategoria = BigDecimal.ZERO;

								for(Iterator iteratorPrestacoesGuia = guiaPagamentoResumo.getGuiasPagamentoPrestacao().iterator(); iteratorPrestacoesGuia
												.hasNext();){
									GuiaPagamentoPrestacao prestacaoGuia = (GuiaPagamentoPrestacao) iteratorPrestacoesGuia.next();

									if((guiaPagamentoCategoria.getComp_id().getNumeroPrestacao().equals(prestacaoGuia.getComp_id()
													.getNumeroPrestacao()))
													&& (guiaPagamentoCategoria.getComp_id().getLancamentoItemContabilId()
																	.equals(prestacaoGuia.getComp_id().getItemLancamentoContabilId()))){

										if(prestacaoGuia.getDebitoCreditoSituacao() != null
														&& prestacaoGuia.getDebitoCreditoSituacao().getId().equals(
																		DebitoCreditoSituacao.CANCELADA)){
											valorCancelamentoCategoria = valorCancelamentoCategoria.add(prestacaoGuia.getValorPrestacao());
										}else if(prestacaoGuia.getDebitoCreditoSituacao() == null){ // Obrigatório
											// vir
											// populado
											throw new ControladorException("atencao.situacao_debito_prestacao_guia_nao_definido");
										}
									}
								}
								beanCategoriaHelper.setValorAcumulado(valorCancelamentoCategoria);
							}
							if(!mapCategoriasGuiaPagamento.containsKey(new Integer(beanCategoriaHelper.hashCode()))){

								mapCategoriasGuiaPagamento.put(new Integer(beanCategoriaHelper.hashCode()), beanCategoriaHelper);
							}else{
								GuiaPagamentoCategoriaHelper beanMap = (GuiaPagamentoCategoriaHelper) mapCategoriasGuiaPagamento
												.get(new Integer(beanCategoriaHelper.hashCode()));
								beanMap.setValorAcumulado(beanMap.getValorAcumulado().add(beanCategoriaHelper.getValorAcumulado()));
							}
						}
					}

					for(Iterator iteratorMapCategorias = mapCategoriasGuiaPagamento.values().iterator(); iteratorMapCategorias.hasNext();){
						GuiaPagamentoCategoriaHelper categoriaHelper = (GuiaPagamentoCategoriaHelper) iteratorMapCategorias.next();

						if(categoriaHelper.getValorAcumulado() != null
										&& categoriaHelper.getValorAcumulado().compareTo(BigDecimal.ZERO) > 0){

							ResumoFaturamento resumoFaturamentoInserir = new ResumoFaturamento();

							Categoria categoria = null;
							FiltroCategoria filtroCategoria = new FiltroCategoria();
							filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, categoriaHelper
											.getIdCategoria()));

							Collection colecaoCategoria = getControladorUtil().pesquisar(filtroCategoria, Categoria.class.getName());
							if(colecaoCategoria != null && !colecaoCategoria.isEmpty()){
								categoria = (Categoria) colecaoCategoria.iterator().next();
							}
							resumoFaturamentoInserir.setCategoria(categoria);
							resumoFaturamentoInserir.setDataEvento(Util.obterDataEventoContabilizacao());
							resumoFaturamentoInserir.setValorItemFaturamento(categoriaHelper.getValorAcumulado());
							resumoFaturamentoInserir.setAnoMesReferencia(sistemaParametros.getAnoMesFaturamento());
							resumoFaturamentoInserir.setLocalidade(guiaPagamentoResumo.getLocalidade());
							resumoFaturamentoInserir.setUnidadeNegocio(guiaPagamentoResumo.getLocalidade().getUnidadeNegocio());
							resumoFaturamentoInserir.setGerenciaRegional(guiaPagamentoResumo.getLocalidade().getGerenciaRegional());

							// Imóvel não é obrigatório na Guia de Pagamento
							if(guiaPagamentoResumo.getImovel() != null){
								resumoFaturamentoInserir.setSetorComercial(guiaPagamentoResumo.getImovel().getSetorComercial());
								resumoFaturamentoInserir.setImovelPerfil(guiaPagamentoResumo.getImovel().getImovelPerfil());
							}

							resumoFaturamentoInserir.setUltimaAlteracao(new Date());

							if(tipoEventoContabil.equals(EventoContabil.INCLUSAO_GUIA_PAGAMENTO)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoInserir, LancamentoTipo.GUIAS_PAGAMENTO,
												categoriaHelper.getIdLancamentoItemContabil(), null, null);

							}else if(tipoEventoContabil.equals(EventoContabil.CANCELAMENTO_GUIA_PAGAMENTO)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoInserir,
												LancamentoTipo.CANCELAMENTOS_POR_REFATURAMENTO, categoriaHelper
																.getIdLancamentoItemContabil(), null, null);
							}

							this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoInserir);
						}

					}

				}else if(objetoGeracaoResumo instanceof Conta){
					// Realiza o tratamento para a Conta (fará para Água, esgoto
					Conta conta = (Conta) objetoGeracaoResumo;

					// Carrega a Unidade de Negócio e a Gerência Regional da
					// Localidade da Guia
					Integer idGerenciaRegional = getControladorLocalidade()
									.pesquisarIdGerenciaParaLocalidade(conta.getLocalidade().getId());
					GerenciaRegional gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(idGerenciaRegional);
					Localidade localidade = new Localidade();
					localidade.setId(conta.getLocalidade().getId());
					localidade.setGerenciaRegional(gerenciaRegional);
					conta.setLocalidade(localidade);
					// conta.getLocalidade().setGerenciaRegional(gerenciaRegional);

					Integer idUnidadeNegocio = getControladorLocalidade().pesquisarIdUnidadeNegocioParaLocalidade(
									conta.getLocalidade().getId());
					UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(idUnidadeNegocio);
					conta.getLocalidade().setUnidadeNegocio(unidadeNegocio);

					if(conta.getImovel().getImovelPerfil() == null){
						throw new ControladorException("atencao.perfil_imovel_conta_nao_definido");
					}

					// Monta o resumoFaturamento que será utilizado como 'modelo' para todos da
					// conta
					ResumoFaturamento resumoFaturamentoModelo = new ResumoFaturamento();
					resumoFaturamentoModelo.setDataEvento(Util.obterDataEventoContabilizacao());
					resumoFaturamentoModelo.setAnoMesReferencia(sistemaParametros.getAnoMesFaturamento());
					resumoFaturamentoModelo.setLocalidade(conta.getLocalidade());
					resumoFaturamentoModelo.setUnidadeNegocio(conta.getLocalidade().getUnidadeNegocio());
					resumoFaturamentoModelo.setSetorComercial(conta.getImovel().getSetorComercial());
					resumoFaturamentoModelo.setImovelPerfil(conta.getImovel().getImovelPerfil());
					resumoFaturamentoModelo.setGerenciaRegional(conta.getLocalidade().getGerenciaRegional());
					resumoFaturamentoModelo.setValorItemFaturamento(BigDecimal.ZERO);

					if(tipoEventoContabil.equals(EventoContabil.FATURAMENTO_CONTA)){ // Todo
						// faturamento
						// normal
						// 'deixa' a
						// conta com
						// a
						// situação
						// de Débito
						// como
						// NORMAL
						// acumula os valores de Água e Esgoto pelas Categorias, para geração do
						// Resumo correspondete
						for(Iterator iteratorContaCategoria = conta.getContaCategorias().iterator(); iteratorContaCategoria.hasNext();){
							ContaCategoria categoriaConta = (ContaCategoria) iteratorContaCategoria.next();

							resumoFaturamentoModelo.setCategoria(categoriaConta.getComp_id().getCategoria());
							resumoFaturamentoModelo.setUltimaAlteracao(new Date());

							LancamentoTipo lancamentoTipo = null;

							if(categoriaConta.getValorAgua() != null && categoriaConta.getValorAgua().compareTo(BigDecimal.ZERO) == 1){

								ResumoFaturamento resumoFaturamentoAgua = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoAgua, resumoFaturamentoModelo);
								}catch(IllegalAccessException e){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema", e);
								}catch(InvocationTargetException e){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema", e);
								}
								resumoFaturamentoAgua.setId(null);
								resumoFaturamentoAgua.setValorItemFaturamento(categoriaConta.getValorAgua());
								resumoFaturamentoAgua.setUltimaAlteracao(new Date());

								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoAgua, LancamentoTipo.AGUA, null, null,
												LancamentoItem.AGUA);
								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoAgua);
							}

							if(categoriaConta.getValorEsgoto() != null && categoriaConta.getValorEsgoto().compareTo(BigDecimal.ZERO) == 1){
								ResumoFaturamento resumoFaturamentoEsgoto = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoEsgoto, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoEsgoto.setId(null);
								resumoFaturamentoEsgoto.setValorItemFaturamento(categoriaConta.getValorEsgoto());
								resumoFaturamentoEsgoto.setUltimaAlteracao(new Date());

								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoEsgoto, LancamentoTipo.ESGOTO, null, null,
												LancamentoItem.ESGOTO);
								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoEsgoto);
							}
						} // fim dos valores de água e esgoto

						// verifica os Débitos Cobrados
						for(Iterator iteratorContaDebitoCobrado = conta.getDebitoCobrados().iterator(); iteratorContaDebitoCobrado
										.hasNext();){
							DebitoCobrado debitoCobradoConta = (DebitoCobrado) iteratorContaDebitoCobrado.next();
							// itera nos valores Categoria do Debito Serviço
							for(Iterator iteratorDebitoCobradoCategoria = debitoCobradoConta.getDebitoCobradoCategorias().iterator(); iteratorDebitoCobradoCategoria
											.hasNext();){
								DebitoCobradoCategoria debitoCobradoCategoria = (DebitoCobradoCategoria) iteratorDebitoCobradoCategoria
												.next();

								ResumoFaturamento resumoFaturamentoDebitoCategoria = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoDebitoCategoria, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoDebitoCategoria.setId(null);
								resumoFaturamentoDebitoCategoria.setValorItemFaturamento(debitoCobradoCategoria.getValorCategoria());
								resumoFaturamentoDebitoCategoria.setCategoria(debitoCobradoCategoria.getCategoria());
								resumoFaturamentoDebitoCategoria.setUltimaAlteracao(new Date());

								if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.SERVICO_NORMAL)){

									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.FINANCIAMENTOS_COBRADOS, debitoCobradoConta.getLancamentoItemContabil()
																	.getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

									// verifica se vai gerar Resumo de Fin. Transf. para o curto
									// prazo. (tipos são short)
									this.verificarGeracaoResumoFaturamentoFinanciamentoCurtoPrazo(resumoFaturamentoDebitoCategoria,
													FinanciamentoTipo.SERVICO_NORMAL, debitoCobradoConta.getNumeroPrestacao(),
													debitoCobradoConta.getNumeroPrestacaoDebito(), debitoCobradoCategoria
																	.getValorCategoria());

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.DOACOES)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.DOACOES_COBRADAS_EM_CONTA, debitoCobradoConta
																	.getLancamentoItemContabil().getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_AGUA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS, null, new Short("10"), LancamentoItem.AGUA);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

									// verifica se vai gerar Resumo de Fin. Transf. para o curto
									// prazo. (tipos são short)
									this.verificarGeracaoResumoFaturamentoFinanciamentoCurtoPrazo(resumoFaturamentoDebitoCategoria,
													FinanciamentoTipo.PARCELAMENTO_AGUA, debitoCobradoConta.getNumeroPrestacao(),
													debitoCobradoConta.getNumeroPrestacaoDebito(), debitoCobradoCategoria
																	.getValorCategoria());

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_ESGOTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS, null, new Short("20"), LancamentoItem.ESGOTO);

									// verifica se vai gerar Resumo de Fin. Transf. para o curto
									// prazo. (tipos são short)
									this.verificarGeracaoResumoFaturamentoFinanciamentoCurtoPrazo(resumoFaturamentoDebitoCategoria,
													FinanciamentoTipo.PARCELAMENTO_ESGOTO, debitoCobradoConta.getNumeroPrestacao(),
													debitoCobradoConta.getNumeroPrestacaoDebito(), debitoCobradoCategoria
																	.getValorCategoria());

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_SERVICO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS, debitoCobradoConta.getLancamentoItemContabil()
																	.getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

									// verifica se vai gerar Resumo de Fin. Transf. para o curto
									// prazo. (tipos são short)
									this.verificarGeracaoResumoFaturamentoFinanciamentoCurtoPrazo(resumoFaturamentoDebitoCategoria,
													FinanciamentoTipo.PARCELAMENTO_SERVICO, debitoCobradoConta.getNumeroPrestacao(),
													debitoCobradoConta.getNumeroPrestacaoDebito(), debitoCobradoCategoria
																	.getValorCategoria());

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.JUROS_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS, null, new Short("1010"), LancamentoItem.JUROS);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

									// verifica se vai gerar Resumo de Fin. Transf. para o curto
									// prazo. (tipos são short)
									this.verificarGeracaoResumoFaturamentoFinanciamentoCurtoPrazo(resumoFaturamentoDebitoCategoria,
													FinanciamentoTipo.JUROS_PARCELAMENTO, debitoCobradoConta.getNumeroPrestacao(),
													debitoCobradoConta.getNumeroPrestacaoDebito(), debitoCobradoCategoria
																	.getValorCategoria());

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.ARRASTO_AGUA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.DEBITOS_ANTERIORES_COBRADOS, null, new Short("10"), LancamentoItem.AGUA);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.ARRASTO_ESGOTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.DEBITOS_ANTERIORES_COBRADOS, null, new Short("20"),
													LancamentoItem.ESGOTO);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.ARRASTO_SERVICO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.DEBITOS_ANTERIORES_COBRADOS, debitoCobradoConta
																	.getLancamentoItemContabil().getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);

								}
							}
						} // Fim de análise dos Débitos Cobrados

						for(Iterator iteratorImpostosDeduzidos = conta.getContaImpostosDeduzidos().iterator(); iteratorImpostosDeduzidos
										.hasNext();){
							ContaImpostosDeduzidos impostosDeduzidosConta = (ContaImpostosDeduzidos) iteratorImpostosDeduzidos.next();

							ResumoFaturamento resumoFaturamentoImpostoCategoria = new ResumoFaturamento();
							try{
								PropertyUtils.copyProperties(resumoFaturamentoImpostoCategoria, resumoFaturamentoModelo);
							}catch(IllegalAccessException iaex){
								sessionContext.setRollbackOnly();
								throw new ControladorException("erro.sistema");
							}catch(InvocationTargetException itex){
								sessionContext.setRollbackOnly();
								throw new ControladorException("erro.sistema");
							}
							resumoFaturamentoImpostoCategoria.setId(null);
							resumoFaturamentoImpostoCategoria.setValorItemFaturamento(impostosDeduzidosConta.getValorImposto());
							resumoFaturamentoImpostoCategoria.setUltimaAlteracao(new Date());

							Categoria principalCategoriaImovel = this.getControladorImovel().obterPrincipalCategoriaImovel(
											conta.getImovel().getId());
							resumoFaturamentoImpostoCategoria.setCategoria(principalCategoriaImovel);

							if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.IR)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
												LancamentoTipo.IMPOSTOS_DEDUZIDOS_EM_CONTA, null, new Short("10"),
												LancamentoItem.IMPOSTO_RENDA);

							}else if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.COFINS)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
												LancamentoTipo.IMPOSTOS_DEDUZIDOS_EM_CONTA, null, new Short("20"), LancamentoItem.COFINS);

							}else if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.CSLL)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
												LancamentoTipo.IMPOSTOS_DEDUZIDOS_EM_CONTA, null, new Short("30"), LancamentoItem.CSLL);

							}else if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.PIS_PASEP)){
								this
												.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
																LancamentoTipo.IMPOSTOS_DEDUZIDOS_EM_CONTA, null, new Short("40"),
																LancamentoItem.PIS_PASEP);

							}

							this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoImpostoCategoria);
						}
						// Créditos Realizados na Conta
						for(Iterator iteratorContaCreditoRealizado = conta.getCreditoRealizados().iterator(); iteratorContaCreditoRealizado
										.hasNext();){
							CreditoRealizado creditoRealizadoConta = (CreditoRealizado) iteratorContaCreditoRealizado.next();
							// itera nos valores Categoria do CreditoRealizado
							for(Iterator iteratorCreditoRealizadoCategoria = creditoRealizadoConta.getCreditoRealizadoCategorias()
											.iterator(); iteratorCreditoRealizadoCategoria.hasNext();){
								CreditoRealizadoCategoria creditoRealizadoCategoria = (CreditoRealizadoCategoria) iteratorCreditoRealizadoCategoria
												.next();

								ResumoFaturamento resumoFaturamentoCreditoCategoria = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoCreditoCategoria, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoCreditoCategoria.setId(null);
								resumoFaturamentoCreditoCategoria.setValorItemFaturamento(creditoRealizadoCategoria.getValorCategoria());
								resumoFaturamentoCreditoCategoria.setCategoria(creditoRealizadoCategoria.getCategoria());
								resumoFaturamentoCreditoCategoria.setUltimaAlteracao(new Date());

								if(creditoRealizadoConta.getCreditoOrigem().getId().equals(
												CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, null, new Short("10"),
													LancamentoItem.CONTAS_PAGA_EM_DUPLICIDADE_EXCESSO);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(
												CreditoOrigem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, null, new Short("20"),
													LancamentoItem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_CONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, null, new Short("30"),
													LancamentoItem.DESCONTOS_CONDICIONAIS);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_INCONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, null, new Short("40"),
													LancamentoItem.DESCONTOS_INCONDICIONAIS);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.AJUSTES_PARA_ZERAR_CONTA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.DEVOLUCAO__VALORES_EM_CONTA, null, new Short("50"),
													LancamentoItem.AJUSTES_PARA_ZERAR_CONTA);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DEVOLUCAO_TARIFA_AGUA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.VALORES_COBRADOS_INDEVIDAMENTE, null, new Short("10"),
													LancamentoItem.AGUA);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DEVOLUCAO_TARIFA_ESGOTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.VALORES_COBRADOS_INDEVIDAMENTE, null, new Short("20"),
													LancamentoItem.ESGOTO);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(
												CreditoOrigem.SERVICOS_INDIRETOS_PAGOS_INDEVIDAMENTE)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.VALORES_COBRADOS_INDEVIDAMENTE, creditoRealizadoConta
																	.getLancamentoItemContabil().getId(), null, null);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId()
												.equals(CreditoOrigem.DEVOLUCAO_JUROS_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.VALORES_COBRADOS_INDEVIDAMENTE, null, new Short("1010"),
													LancamentoItem.JUROS);
								}

								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoCreditoCategoria);
							}
						} // Fim de análise dos Créditos Realizados e Do Evento - FATURAMENTO CONTA
					}else if(tipoEventoContabil.equals(EventoContabil.INCLUSAO_CONTA)){ // Toda
						// Inclusão
						// (!=
						// faturamento)
						// 'deixa' a
						// conta com
						// a
						// situação
						// de Débito
						// como
						// INCLUIDA
						// acumula os valores de Água e Esgoto pelas Categorias, para geração do
						// Resumo correspondete
						for(Iterator iteratorContaCategoria = conta.getContaCategorias().iterator(); iteratorContaCategoria.hasNext();){
							ContaCategoria categoriaConta = (ContaCategoria) iteratorContaCategoria.next();

							resumoFaturamentoModelo.setCategoria(categoriaConta.getComp_id().getCategoria());
							resumoFaturamentoModelo.setUltimaAlteracao(new Date());

							LancamentoTipo lancamentoTipo = null;

							if(categoriaConta.getValorAgua() != null && categoriaConta.getValorAgua().compareTo(BigDecimal.ZERO) == 1){

								ResumoFaturamento resumoFaturamentoAgua = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoAgua, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoAgua.setId(null);
								resumoFaturamentoAgua.setValorItemFaturamento(categoriaConta.getValorAgua());
								resumoFaturamentoAgua.setUltimaAlteracao(new Date());

								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoAgua,
												LancamentoTipo.INCLUSOES_POR_REFATURAMENTO, null, null, LancamentoItem.AGUA);
								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoAgua);
							}

							if(categoriaConta.getValorEsgoto() != null && categoriaConta.getValorEsgoto().compareTo(BigDecimal.ZERO) == 1){
								ResumoFaturamento resumoFaturamentoEsgoto = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoEsgoto, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoEsgoto.setId(null);
								resumoFaturamentoEsgoto.setValorItemFaturamento(categoriaConta.getValorEsgoto());
								resumoFaturamentoEsgoto.setUltimaAlteracao(new Date());

								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoEsgoto,
												LancamentoTipo.INCLUSOES_POR_REFATURAMENTO, null, null, LancamentoItem.ESGOTO);
								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoEsgoto);
							}
						} // fim dos valores de água e esgoto
						// Verifica se Débitos foram incluídos na Conta
						for(Iterator iteratorContaDebitoCobrado = conta.getDebitoCobrados().iterator(); iteratorContaDebitoCobrado
										.hasNext();){
							DebitoCobrado debitoCobradoConta = (DebitoCobrado) iteratorContaDebitoCobrado.next();
							// itera nos valores Categoria do Debito Serviço
							for(Iterator iteratorDebitoCobradoCategoria = debitoCobradoConta.getDebitoCobradoCategorias().iterator(); iteratorDebitoCobradoCategoria
											.hasNext();){
								DebitoCobradoCategoria debitoCobradoCategoria = (DebitoCobradoCategoria) iteratorDebitoCobradoCategoria
												.next();

								ResumoFaturamento resumoFaturamentoDebitoCategoria = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoDebitoCategoria, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoDebitoCategoria.setId(null);
								resumoFaturamentoDebitoCategoria.setValorItemFaturamento(debitoCobradoCategoria.getValorCategoria());
								resumoFaturamentoDebitoCategoria.setCategoria(debitoCobradoCategoria.getCategoria());
								resumoFaturamentoDebitoCategoria.setUltimaAlteracao(new Date());

								if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.SERVICO_NORMAL)){

									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.INCLUSOES_POR_REFATURAMENTO, debitoCobradoConta
																	.getLancamentoItemContabil().getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);
								}
							}
						}
						// Creditos Realizados
						for(Iterator iteratorContaCreditoRealizado = conta.getCreditoRealizados().iterator(); iteratorContaCreditoRealizado
										.hasNext();){
							CreditoRealizado creditoRealizadoConta = (CreditoRealizado) iteratorContaCreditoRealizado.next();
							// itera nos valores Categoria do CreditoRealizado
							for(Iterator iteratorCreditoRealizadoCategoria = creditoRealizadoConta.getCreditoRealizadoCategorias()
											.iterator(); iteratorCreditoRealizadoCategoria.hasNext();){
								CreditoRealizadoCategoria creditoRealizadoCategoria = (CreditoRealizadoCategoria) iteratorCreditoRealizadoCategoria
												.next();

								ResumoFaturamento resumoFaturamentoCreditoCategoria = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoCreditoCategoria, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoCreditoCategoria.setId(null);
								resumoFaturamentoCreditoCategoria.setValorItemFaturamento(creditoRealizadoCategoria.getValorCategoria());
								resumoFaturamentoCreditoCategoria.setCategoria(creditoRealizadoCategoria.getCategoria());
								resumoFaturamentoCreditoCategoria.setUltimaAlteracao(new Date());

								if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_INCONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.INCLUSOES_POR_REFATURAMENTO, null, new Short("0"),
													LancamentoItem.DESCONTOS_INCONDICIONAIS_INCLUIDOS);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DEVOLUCAO_TARIFA_AGUA)
												|| creditoRealizadoConta.getCreditoOrigem().getId().equals(
																CreditoOrigem.DEVOLUCAO_TARIFA_ESGOTO)
												|| creditoRealizadoConta.getCreditoOrigem().getId().equals(
																CreditoOrigem.SERVICOS_INDIRETOS_PAGOS_INDEVIDAMENTE)
												|| creditoRealizadoConta.getCreditoOrigem().getId().equals(
																CreditoOrigem.DEVOLUCAO_JUROS_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.INCLUSOES_POR_REFATURAMENTO, creditoRealizadoCategoria
																	.getCreditoRealizado().getLancamentoItemContabil().getId(), null, null);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(
												CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CONCEDIDOS_POR_REFATURAMENTO, null, new Short("10"),
													LancamentoItem.CONTAS_PAGA_EM_DUPLICIDADE_EXCESSO);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(
												CreditoOrigem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CONCEDIDOS_POR_REFATURAMENTO, null, new Short("20"),
													LancamentoItem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_CONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CONCEDIDOS_POR_REFATURAMENTO, null, new Short("30"),
													LancamentoItem.DESCONTOS_CONDICIONAIS);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.AJUSTES_PARA_ZERAR_CONTA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CONCEDIDOS_POR_REFATURAMENTO, null, new Short("40"),
													LancamentoItem.AJUSTES_PARA_ZERAR_CONTA);

								}

								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoCreditoCategoria);
							}
						}// FIM do Evento INCLUSAO_CONTA
					}else if(tipoEventoContabil.equals(EventoContabil.CANCELAMENTO_CONTA)){
						// Todo cancelamento de Conta, seta a mesma para Situação CANCELADA
						// acumula os valores de Água e Esgoto pelas Categorias, para geração do
						// Resumo correspondete
						for(Iterator iteratorContaCategoria = conta.getContaCategorias().iterator(); iteratorContaCategoria.hasNext();){
							ContaCategoria categoriaConta = (ContaCategoria) iteratorContaCategoria.next();

							resumoFaturamentoModelo.setCategoria(categoriaConta.getComp_id().getCategoria());
							resumoFaturamentoModelo.setUltimaAlteracao(new Date());

							LancamentoTipo lancamentoTipo = null;

							if(categoriaConta.getValorAgua() != null && categoriaConta.getValorAgua().compareTo(BigDecimal.ZERO) == 1){

								ResumoFaturamento resumoFaturamentoAgua = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoAgua, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoAgua.setId(null);
								resumoFaturamentoAgua.setValorItemFaturamento(categoriaConta.getValorAgua());
								resumoFaturamentoAgua.setUltimaAlteracao(new Date());

								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoAgua,
												LancamentoTipo.CANCELAMENTOS_POR_REFATURAMENTO, null, null, LancamentoItem.AGUA);
								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoAgua);
							}

							if(categoriaConta.getValorEsgoto() != null && categoriaConta.getValorEsgoto().compareTo(BigDecimal.ZERO) == 1){
								ResumoFaturamento resumoFaturamentoEsgoto = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoEsgoto, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoEsgoto.setId(null);
								resumoFaturamentoEsgoto.setValorItemFaturamento(categoriaConta.getValorEsgoto());
								resumoFaturamentoEsgoto.setUltimaAlteracao(new Date());

								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoEsgoto,
												LancamentoTipo.CANCELAMENTOS_POR_REFATURAMENTO, null, null, LancamentoItem.ESGOTO);
								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoEsgoto);
							}
						} // fim dos valores de água e esgoto
						// Verifica se Débitos foram incluídos na Conta
						for(Iterator iteratorContaDebitoCobrado = conta.getDebitoCobrados().iterator(); iteratorContaDebitoCobrado
										.hasNext();){
							DebitoCobrado debitoCobradoConta = (DebitoCobrado) iteratorContaDebitoCobrado.next();
							// itera nos valores Categoria do Debito Serviço
							for(Iterator iteratorDebitoCobradoCategoria = debitoCobradoConta.getDebitoCobradoCategorias().iterator(); iteratorDebitoCobradoCategoria
											.hasNext();){
								DebitoCobradoCategoria debitoCobradoCategoria = (DebitoCobradoCategoria) iteratorDebitoCobradoCategoria
												.next();

								ResumoFaturamento resumoFaturamentoDebitoCategoria = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoDebitoCategoria, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoDebitoCategoria.setId(null);
								resumoFaturamentoDebitoCategoria.setValorItemFaturamento(debitoCobradoCategoria.getValorCategoria());
								resumoFaturamentoDebitoCategoria.setCategoria(debitoCobradoCategoria.getCategoria());
								resumoFaturamentoDebitoCategoria.setUltimaAlteracao(new Date());

								if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.SERVICO_NORMAL)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.INCLUSOES_POR_REFATURAMENTO, debitoCobradoConta
																	.getLancamentoItemContabil().getId(), null, null);

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_AGUA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS_SUP_CANCELAMENTOS_POR_REFATURAMENTO, null,
													new Short("10"), LancamentoItem.AGUA);

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_ESGOTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS_SUP_CANCELAMENTOS_POR_REFATURAMENTO, null,
													new Short("20"), LancamentoItem.ESGOTO);

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_SERVICO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS_SUP_CANCELAMENTOS_POR_REFATURAMENTO,
													debitoCobradoConta.getLancamentoItemContabil().getId(), null, null);

								}else if(debitoCobradoConta.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.JUROS_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoCategoria,
													LancamentoTipo.PARCELAMENTOS_COBRADOS_SUP_CANCELAMENTOS_POR_REFATURAMENTO, null,
													new Short("9010"), LancamentoItem.JUROS);

								}
								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoCategoria);
							}
						}
						// Creditos Realizados
						for(Iterator iteratorContaCreditoRealizado = conta.getCreditoRealizados().iterator(); iteratorContaCreditoRealizado
										.hasNext();){
							CreditoRealizado creditoRealizadoConta = (CreditoRealizado) iteratorContaCreditoRealizado.next();
							// itera nos valores Categoria do CreditoRealizado
							for(Iterator iteratorCreditoRealizadoCategoria = creditoRealizadoConta.getCreditoRealizadoCategorias()
											.iterator(); iteratorCreditoRealizadoCategoria.hasNext();){
								CreditoRealizadoCategoria creditoRealizadoCategoria = (CreditoRealizadoCategoria) iteratorCreditoRealizadoCategoria
												.next();

								ResumoFaturamento resumoFaturamentoCreditoCategoria = new ResumoFaturamento();
								try{
									PropertyUtils.copyProperties(resumoFaturamentoCreditoCategoria, resumoFaturamentoModelo);
								}catch(IllegalAccessException iaex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}catch(InvocationTargetException itex){
									sessionContext.setRollbackOnly();
									throw new ControladorException("erro.sistema");
								}
								resumoFaturamentoCreditoCategoria.setId(null);
								resumoFaturamentoCreditoCategoria.setValorItemFaturamento(creditoRealizadoCategoria.getValorCategoria());
								resumoFaturamentoCreditoCategoria.setCategoria(creditoRealizadoCategoria.getCategoria());
								resumoFaturamentoCreditoCategoria.setUltimaAlteracao(new Date());

								if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_INCONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.CREDITOS_REALIZADOS_SUP_CANCELAMENTO_POR_REFATURAMENTO, null, new Short(
																	"0"), LancamentoItem.DESCONTOS_INCONDICIONAIS_CANCELADOS);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DEVOLUCAO_TARIFA_AGUA)
												|| creditoRealizadoConta.getCreditoOrigem().getId().equals(
																CreditoOrigem.DEVOLUCAO_TARIFA_ESGOTO)
												|| creditoRealizadoConta.getCreditoOrigem().getId().equals(
																CreditoOrigem.SERVICOS_INDIRETOS_PAGOS_INDEVIDAMENTE)
												|| creditoRealizadoConta.getCreditoOrigem().getId().equals(
																CreditoOrigem.DEVOLUCAO_JUROS_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.CREDITOS_REALIZADOS_SUP_CANCELAMENTO_POR_REFATURAMENTO,
													creditoRealizadoCategoria.getCreditoRealizado().getLancamentoItemContabil().getId(),
													null, null);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(
												CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CANCELADOS_POR_REFATURAMENTO, null, new Short("10"),
													LancamentoItem.CONTAS_PAGA_EM_DUPLICIDADE_EXCESSO);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(
												CreditoOrigem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CANCELADOS_POR_REFATURAMENTO, null, new Short("20"),
													LancamentoItem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_CONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CANCELADOS_POR_REFATURAMENTO, null, new Short("30"),
													LancamentoItem.DESCONTOS_CONDICIONAIS);

								}else if(creditoRealizadoConta.getCreditoOrigem().getId().equals(CreditoOrigem.AJUSTES_PARA_ZERAR_CONTA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_CANCELADOS_POR_REFATURAMENTO, null, new Short("40"),
													LancamentoItem.AJUSTES_PARA_ZERAR_CONTA);

								}

								this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoCreditoCategoria);
							}
						} // Fim do Tratamento de CREDITOS_REALIZADOS
						// Tratamento de Impostos
						for(Iterator iteratorImpostosDeduzidos = conta.getContaImpostosDeduzidos().iterator(); iteratorImpostosDeduzidos
										.hasNext();){
							ContaImpostosDeduzidos impostosDeduzidosConta = (ContaImpostosDeduzidos) iteratorImpostosDeduzidos.next();

							ResumoFaturamento resumoFaturamentoImpostoCategoria = new ResumoFaturamento();
							try{
								PropertyUtils.copyProperties(resumoFaturamentoImpostoCategoria, resumoFaturamentoModelo);
							}catch(IllegalAccessException iaex){
								sessionContext.setRollbackOnly();
								throw new ControladorException("erro.sistema");
							}catch(InvocationTargetException itex){
								sessionContext.setRollbackOnly();
								throw new ControladorException("erro.sistema");
							}
							resumoFaturamentoImpostoCategoria.setId(null);
							resumoFaturamentoImpostoCategoria.setValorItemFaturamento(impostosDeduzidosConta.getValorImposto());
							resumoFaturamentoImpostoCategoria.setUltimaAlteracao(new Date());

							Categoria principalCategoriaImovel = this.getControladorImovel().obterPrincipalCategoriaImovel(
											conta.getImovel().getId());
							resumoFaturamentoImpostoCategoria.setCategoria(principalCategoriaImovel);

							if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.IR)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
												LancamentoTipo.IMPOSTOS_CANCELADOS_REFATURAMENTO, null, new Short("10"),
												LancamentoItem.IMPOSTO_RENDA);

							}else if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.COFINS)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
												LancamentoTipo.IMPOSTOS_CANCELADOS_REFATURAMENTO, null, new Short("20"),
												LancamentoItem.COFINS);

							}else if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.CSLL)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
												LancamentoTipo.IMPOSTOS_CANCELADOS_REFATURAMENTO, null, new Short("30"),
												LancamentoItem.CSLL);

							}else if(impostosDeduzidosConta.getImpostoTipo().getId().equals(ImpostoTipo.PIS_PASEP)){
								this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoImpostoCategoria,
												LancamentoTipo.IMPOSTOS_CANCELADOS_REFATURAMENTO, null, new Short("40"),
												LancamentoItem.PIS_PASEP);

							}

							this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoImpostoCategoria);
						}
					}// FIM do Evento CANCELAMENTO_CONTA

				}else if(objetoGeracaoResumo instanceof DebitoACobrar){
					// Realiza o tratamento para o DebitoACobrar
					DebitoACobrar debitoACobrar = (DebitoACobrar) objetoGeracaoResumo;

					// Carrega a Unidade de Negócio e a Gerência Regional da
					// Localidade da Guia
					Integer idGerenciaRegional = getControladorLocalidade().pesquisarIdGerenciaParaLocalidade(
									debitoACobrar.getLocalidade().getId());
					GerenciaRegional gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(idGerenciaRegional);
					debitoACobrar.getLocalidade().setGerenciaRegional(gerenciaRegional);

					Integer idUnidadeNegocio = getControladorLocalidade().pesquisarIdUnidadeNegocioParaLocalidade(
									debitoACobrar.getLocalidade().getId());
					UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(idUnidadeNegocio);
					debitoACobrar.getLocalidade().setUnidadeNegocio(unidadeNegocio);

					if(debitoACobrar.getImovel().getImovelPerfil() == null){
						throw new ControladorException("atencao.perfil_imovel_debito_a_cobrar_nao_definido");
					}

					// Monta o resumoFaturamento que será utilizado como 'modelo' para todos da
					// conta
					ResumoFaturamento resumoFaturamentoModelo = new ResumoFaturamento();
					resumoFaturamentoModelo.setDataEvento(Util.obterDataEventoContabilizacao());
					resumoFaturamentoModelo.setAnoMesReferencia(sistemaParametros.getAnoMesFaturamento());
					resumoFaturamentoModelo.setLocalidade(debitoACobrar.getLocalidade());
					resumoFaturamentoModelo.setUnidadeNegocio(debitoACobrar.getLocalidade().getUnidadeNegocio());
					resumoFaturamentoModelo.setSetorComercial(debitoACobrar.getImovel().getSetorComercial());
					resumoFaturamentoModelo.setImovelPerfil(debitoACobrar.getImovel().getImovelPerfil());
					resumoFaturamentoModelo.setGerenciaRegional(debitoACobrar.getLocalidade().getGerenciaRegional());
					resumoFaturamentoModelo.setValorItemFaturamento(BigDecimal.ZERO);

					// acumula os valores pelas Categorias, para geração do Resumo correspondete
					for(Iterator iteratorDebitoACobrarCategoria = debitoACobrar.getDebitoACobrarCategorias().iterator(); iteratorDebitoACobrarCategoria
									.hasNext();){
						DebitoACobrarCategoria categoriaDebitoACobrar = (DebitoACobrarCategoria) iteratorDebitoACobrarCategoria.next();

						resumoFaturamentoModelo.setCategoria(categoriaDebitoACobrar.getComp_id().getCategoria());
						resumoFaturamentoModelo.setUltimaAlteracao(new Date());

						LancamentoTipo lancamentoTipo = null;

						if(categoriaDebitoACobrar.getValorCategoria() != null
										&& categoriaDebitoACobrar.getValorCategoria().compareTo(BigDecimal.ZERO) == 1
										&& debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.SERVICO_NORMAL)){

							if(tipoEventoContabil.equals(EventoContabil.INCLUSAO_DEBITO_A_COBRAR)){
								BigDecimal[] valoresFinanciamentoCurtoPrazo = getControladorFaturamento()
												.obterValorACobrarDeCurtoELongoPrazo(debitoACobrar.getNumeroPrestacaoDebito(), (short) 0,
																categoriaDebitoACobrar.getValorCategoria());
								if(valoresFinanciamentoCurtoPrazo != null
												&& valoresFinanciamentoCurtoPrazo[0].compareTo(BigDecimal.ZERO) == 1){

									ResumoFaturamento resumoFaturamentoDebitoACobrar = new ResumoFaturamento();
									try{
										PropertyUtils.copyProperties(resumoFaturamentoDebitoACobrar, resumoFaturamentoModelo);
									}catch(IllegalAccessException iaex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}catch(InvocationTargetException itex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}
									resumoFaturamentoDebitoACobrar.setId(null);
									resumoFaturamentoDebitoACobrar.setValorItemFaturamento(valoresFinanciamentoCurtoPrazo[0]);
									resumoFaturamentoDebitoACobrar.setUltimaAlteracao(new Date());

									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
													LancamentoTipo.FINANCIAMENTOS_INCLUIDOS_CURTO_PRAZO, debitoACobrar
																	.getLancamentoItemContabil().getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoACobrar);
								}
								if(valoresFinanciamentoCurtoPrazo != null
												&& valoresFinanciamentoCurtoPrazo[1].compareTo(BigDecimal.ZERO) == 1){

									ResumoFaturamento resumoFaturamentoDebitoACobrar = new ResumoFaturamento();
									try{
										PropertyUtils.copyProperties(resumoFaturamentoDebitoACobrar, resumoFaturamentoModelo);
									}catch(IllegalAccessException iaex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}catch(InvocationTargetException itex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}
									resumoFaturamentoDebitoACobrar.setId(null);
									resumoFaturamentoDebitoACobrar.setValorItemFaturamento(valoresFinanciamentoCurtoPrazo[1]);
									resumoFaturamentoDebitoACobrar.setUltimaAlteracao(new Date());

									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
													LancamentoTipo.FINANCIAMENTOS_INCLUIDOS_LONGO_PRAZO, debitoACobrar
																	.getLancamentoItemContabil().getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoACobrar);
								}
							}else if(tipoEventoContabil.equals(EventoContabil.CANCELAMENTO_DEBITO_A_COBRAR)){
								BigDecimal[] valoresFinanciamentoCurtoPrazo = getControladorFaturamento()
												.obterValorACobrarDeCurtoELongoPrazo(debitoACobrar.getNumeroPrestacaoDebito(),
																debitoACobrar.getNumeroPrestacaoCobradas(),
																categoriaDebitoACobrar.getValorCategoria());
								if(valoresFinanciamentoCurtoPrazo != null
												&& valoresFinanciamentoCurtoPrazo[0].compareTo(BigDecimal.ZERO) == 1){

									ResumoFaturamento resumoFaturamentoDebitoACobrar = new ResumoFaturamento();
									try{
										PropertyUtils.copyProperties(resumoFaturamentoDebitoACobrar, resumoFaturamentoModelo);
									}catch(IllegalAccessException iaex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}catch(InvocationTargetException itex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}
									resumoFaturamentoDebitoACobrar.setId(null);
									resumoFaturamentoDebitoACobrar.setValorItemFaturamento(valoresFinanciamentoCurtoPrazo[0]);
									resumoFaturamentoDebitoACobrar.setUltimaAlteracao(new Date());

									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
													LancamentoTipo.FINANCIAMENTOS_CANCELADOS_CURTO_PRAZO, debitoACobrar
																	.getLancamentoItemContabil().getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoACobrar);
								}
								if(valoresFinanciamentoCurtoPrazo != null
												&& valoresFinanciamentoCurtoPrazo[1].compareTo(BigDecimal.ZERO) == 1){

									ResumoFaturamento resumoFaturamentoDebitoACobrar = new ResumoFaturamento();
									try{
										PropertyUtils.copyProperties(resumoFaturamentoDebitoACobrar, resumoFaturamentoModelo);
									}catch(IllegalAccessException iaex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}catch(InvocationTargetException itex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}
									resumoFaturamentoDebitoACobrar.setId(null);
									resumoFaturamentoDebitoACobrar.setValorItemFaturamento(valoresFinanciamentoCurtoPrazo[1]);
									resumoFaturamentoDebitoACobrar.setUltimaAlteracao(new Date());

									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
													LancamentoTipo.FINANCIAMENTOS_CANCELADOS_LONGO_PRAZO, debitoACobrar
																	.getLancamentoItemContabil().getId(), null, null);
									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoACobrar);
								}

							}
						}else if((categoriaDebitoACobrar.getValorCategoria() != null && categoriaDebitoACobrar.getValorCategoria()
										.compareTo(BigDecimal.ZERO) == 1)
										&& tiposParcelamento.contains(debitoACobrar.getFinanciamentoTipo().getId())){

							if(tipoEventoContabil.equals(EventoContabil.CANCELAMENTO_DEBITO_A_COBRAR)){
								BigDecimal[] valoresFinanciamentoCurtoPrazo = getControladorFaturamento()
												.obterValorACobrarDeCurtoELongoPrazo(debitoACobrar.getNumeroPrestacaoDebito(),
																debitoACobrar.getNumeroPrestacaoCobradas(),
																categoriaDebitoACobrar.getValorCategoria());
								if(valoresFinanciamentoCurtoPrazo != null
												&& valoresFinanciamentoCurtoPrazo[0].compareTo(BigDecimal.ZERO) == 1){

									ResumoFaturamento resumoFaturamentoDebitoACobrar = new ResumoFaturamento();
									try{
										PropertyUtils.copyProperties(resumoFaturamentoDebitoACobrar, resumoFaturamentoModelo);
									}catch(IllegalAccessException iaex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}catch(InvocationTargetException itex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}
									resumoFaturamentoDebitoACobrar.setId(null);
									resumoFaturamentoDebitoACobrar.setValorItemFaturamento(valoresFinanciamentoCurtoPrazo[0]);
									resumoFaturamentoDebitoACobrar.setUltimaAlteracao(new Date());

									if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_AGUA)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_CURTO_PRAZO, null, null,
														LancamentoItem.AGUA);

									}else if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_ESGOTO)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_CURTO_PRAZO, null, null,
														LancamentoItem.ESGOTO);

									}else if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_SERVICO)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_CURTO_PRAZO, debitoACobrar
																		.getLancamentoItemContabil().getId(), null, null);

									}else if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.JUROS_PARCELAMENTO)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_CURTO_PRAZO, null, new Short("1010"),
														LancamentoItem.JUROS);

									}

									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoACobrar);
								}
								if(valoresFinanciamentoCurtoPrazo != null
												&& valoresFinanciamentoCurtoPrazo[1].compareTo(BigDecimal.ZERO) == 1){

									ResumoFaturamento resumoFaturamentoDebitoACobrar = new ResumoFaturamento();
									try{
										PropertyUtils.copyProperties(resumoFaturamentoDebitoACobrar, resumoFaturamentoModelo);
									}catch(IllegalAccessException iaex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}catch(InvocationTargetException itex){
										sessionContext.setRollbackOnly();
										throw new ControladorException("erro.sistema");
									}
									resumoFaturamentoDebitoACobrar.setId(null);
									resumoFaturamentoDebitoACobrar.setValorItemFaturamento(valoresFinanciamentoCurtoPrazo[1]);

									if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_AGUA)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_LONGO_PRAZO, null, null,
														LancamentoItem.AGUA);

									}else if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_ESGOTO)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_LONGO_PRAZO, null, null,
														LancamentoItem.ESGOTO);

									}else if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.PARCELAMENTO_SERVICO)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_LONGO_PRAZO, debitoACobrar
																		.getLancamentoItemContabil().getId(), null, null);

									}else if(debitoACobrar.getFinanciamentoTipo().getId().equals(FinanciamentoTipo.JUROS_PARCELAMENTO)){
										this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDebitoACobrar,
														LancamentoTipo.PARCELAMENTOS_CANCELADOS_LONGO_PRAZO, null, null,
														LancamentoItem.JUROS);
									}

									this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDebitoACobrar);
								}
							}

						}
					}

				}else if(objetoGeracaoResumo instanceof CreditoARealizar){
					// Realiza o tratamento para o DebitoACobrar
					CreditoARealizar creditoARealizar = (CreditoARealizar) objetoGeracaoResumo;

					// Carrega a Unidade de Negócio e a Gerência Regional da
					// Localidade da Guia
					Integer idGerenciaRegional = getControladorLocalidade().pesquisarIdGerenciaParaLocalidade(
									creditoARealizar.getLocalidade().getId());
					GerenciaRegional gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(idGerenciaRegional);
					creditoARealizar.getLocalidade().setGerenciaRegional(gerenciaRegional);

					Integer idUnidadeNegocio = getControladorLocalidade().pesquisarIdUnidadeNegocioParaLocalidade(
									creditoARealizar.getLocalidade().getId());
					UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(idUnidadeNegocio);
					creditoARealizar.getLocalidade().setUnidadeNegocio(unidadeNegocio);

					if(creditoARealizar.getImovel().getImovelPerfil() == null){
						throw new ControladorException("atencao.perfil_imovel_credito_a_realizar_nao_definido");
					}

					// Monta o resumoFaturamento que será utilizado como 'modelo' para todos da
					// conta
					ResumoFaturamento resumoFaturamentoModelo = new ResumoFaturamento();
					resumoFaturamentoModelo.setDataEvento(Util.obterDataEventoContabilizacao());
					resumoFaturamentoModelo.setAnoMesReferencia(sistemaParametros.getAnoMesFaturamento());
					resumoFaturamentoModelo.setLocalidade(creditoARealizar.getLocalidade());
					resumoFaturamentoModelo.setUnidadeNegocio(creditoARealizar.getLocalidade().getUnidadeNegocio());
					resumoFaturamentoModelo.setSetorComercial(creditoARealizar.getImovel().getSetorComercial());
					resumoFaturamentoModelo.setImovelPerfil(creditoARealizar.getImovel().getImovelPerfil());
					resumoFaturamentoModelo.setGerenciaRegional(creditoARealizar.getLocalidade().getGerenciaRegional());
					resumoFaturamentoModelo.setValorItemFaturamento(BigDecimal.ZERO);

					// acumula os valores pelas Categorias, para geração do Resumo correspondete
					for(Iterator iteratorCreditoACategoria = creditoARealizar.getCreditoARealizarCategoria().iterator(); iteratorCreditoACategoria
									.hasNext();){
						CreditoARealizarCategoria categoriaCreditoARealizar = (CreditoARealizarCategoria) iteratorCreditoACategoria.next();

						if(categoriaCreditoARealizar.getValorCategoria() != null
										&& categoriaCreditoARealizar.getValorCategoria().compareTo(BigDecimal.ZERO) == 1){

							ResumoFaturamento resumoFaturamentoCreditoCategoria = new ResumoFaturamento();
							try{
								PropertyUtils.copyProperties(resumoFaturamentoCreditoCategoria, resumoFaturamentoModelo);
							}catch(IllegalAccessException iaex){
								sessionContext.setRollbackOnly();
								throw new ControladorException("erro.sistema");
							}catch(InvocationTargetException itex){
								sessionContext.setRollbackOnly();
								throw new ControladorException("erro.sistema");
							}
							resumoFaturamentoCreditoCategoria.setId(null);
							resumoFaturamentoCreditoCategoria.setValorItemFaturamento(categoriaCreditoARealizar.getValorCategoria());
							resumoFaturamentoCreditoCategoria.setCategoria(categoriaCreditoARealizar.getCategoria());
							resumoFaturamentoCreditoCategoria.setUltimaAlteracao(new Date());

							if(tipoEventoContabil.equals(EventoContabil.INCLUSAO_CREDITO_A_REALIZAR)){ // Toda
								// inclusão
								// tem
								// a
								// situação
								// como
								// NORMAL
								if(creditoARealizar.getCreditoOrigem().getId().equals(CreditoOrigem.DEVOLUCAO_TARIFA_AGUA)
												|| creditoARealizar.getCreditoOrigem().getId()
																.equals(CreditoOrigem.DEVOLUCAO_TARIFA_ESGOTO)
												|| creditoARealizar.getCreditoOrigem().getId().equals(
																CreditoOrigem.SERVICOS_INDIRETOS_PAGOS_INDEVIDAMENTE)
												|| creditoARealizar.getCreditoOrigem().getId().equals(
																CreditoOrigem.DEVOLUCAO_JUROS_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.CREDITOS_A_REALIZAR_POR_COBRANCA_INDEVIDA_INCLUIDOS, creditoARealizar
																	.getLancamentoItemContabil().getId(), null, null);

								}else if(creditoARealizar.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_INCONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.DESCONTOS_INCONDICIONAIS_INCLUIDOS, null, null,
													LancamentoItem.DESCONTOS_INCONDICIONAIS_INCLUIDOS);

								}else if(creditoARealizar.getCreditoOrigem().getId().equals(
												CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_A_REALIZAR_INCLUIDOS, null, new Short("10"),
													LancamentoItem.CONTAS_PAGA_EM_DUPLICIDADE_EXCESSO);

								}else if(creditoARealizar.getCreditoOrigem().getId().equals(CreditoOrigem.AJUSTES_PARA_ZERAR_CONTA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_A_REALIZAR_INCLUIDOS, null, new Short("30"),
													LancamentoItem.AJUSTES_PARA_ZERAR_CONTA);

								}

							}else if(tipoEventoContabil.equals(EventoContabil.CANCELAMENTO_CREDITO_A_REALIZAR)){ // Todo
								// cancelamento
								// tem
								// a
								// situação
								// como
								// CANCELAMENTO
								if(creditoARealizar.getCreditoOrigem().getId().equals(CreditoOrigem.DEVOLUCAO_TARIFA_AGUA)
												|| creditoARealizar.getCreditoOrigem().getId()
																.equals(CreditoOrigem.DEVOLUCAO_TARIFA_ESGOTO)
												|| creditoARealizar.getCreditoOrigem().getId().equals(
																CreditoOrigem.SERVICOS_INDIRETOS_PAGOS_INDEVIDAMENTE)
												|| creditoARealizar.getCreditoOrigem().getId().equals(
																CreditoOrigem.DEVOLUCAO_JUROS_PARCELAMENTO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.CREDITOS_A_REALIZAR_POR_COBRANCA_INDEVIDA_CANCELADO, creditoARealizar
																	.getLancamentoItemContabil().getId(), null, null);

								}else if(creditoARealizar.getCreditoOrigem().getId().equals(CreditoOrigem.DESCONTOS_INCONDICIONAIS)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.DESCONTOS_INCONDICIONAIS_A_REALIZAR_CANCELADOS, null, null,
													LancamentoItem.DESCONTOS_INCONDICIONAIS_CANCELADOS);

								}else if(creditoARealizar.getCreditoOrigem().getId().equals(
												CreditoOrigem.CONTAS_PAGAS_EM_DUPLICIDADE_EXCESSO)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_A_REALIZAR_CANCELADOS, null, new Short("10"),
													LancamentoItem.CONTAS_PAGA_EM_DUPLICIDADE_EXCESSO);

								}else if(creditoARealizar.getCreditoOrigem().getId().equals(CreditoOrigem.AJUSTES_PARA_ZERAR_CONTA)){
									this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoCreditoCategoria,
													LancamentoTipo.OUTROS_CREDITOS_A_REALIZAR_CANCELADOS, null, new Short("30"),
													LancamentoItem.AJUSTES_PARA_ZERAR_CONTA);
								}
							}
							this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoCreditoCategoria);
						}
					}
				}else if(objetoGeracaoResumo instanceof GuiaDevolucao){
					// Realiza o tratamento para o DebitoACobrar
					GuiaDevolucao guiaDevolucao = (GuiaDevolucao) objetoGeracaoResumo;

					// Carrega a Unidade de Negócio e a Gerência Regional da
					// Localidade da Guia
					Integer idGerenciaRegional = getControladorLocalidade().pesquisarIdGerenciaParaLocalidade(
									guiaDevolucao.getLocalidade().getId());
					GerenciaRegional gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(idGerenciaRegional);
					guiaDevolucao.getLocalidade().setGerenciaRegional(gerenciaRegional);

					Integer idUnidadeNegocio = getControladorLocalidade().pesquisarIdUnidadeNegocioParaLocalidade(
									guiaDevolucao.getLocalidade().getId());
					UnidadeNegocio unidadeNegocio = new UnidadeNegocio();
					unidadeNegocio.setId(idUnidadeNegocio);
					guiaDevolucao.getLocalidade().setUnidadeNegocio(unidadeNegocio);

					if(guiaDevolucao.getImovel().getImovelPerfil() == null){
						throw new ControladorException("atencao.perfil_imovel_credito_a_realizar_nao_definido");
					}

					// Monta o resumoFaturamento que será utilizado como 'modelo' para todos da
					// conta
					ResumoFaturamento resumoFaturamentoModelo = new ResumoFaturamento();
					resumoFaturamentoModelo.setDataEvento(Util.obterDataEventoContabilizacao());
					resumoFaturamentoModelo.setAnoMesReferencia(sistemaParametros.getAnoMesFaturamento());
					resumoFaturamentoModelo.setLocalidade(guiaDevolucao.getLocalidade());
					resumoFaturamentoModelo.setUnidadeNegocio(guiaDevolucao.getLocalidade().getUnidadeNegocio());
					resumoFaturamentoModelo.setSetorComercial(guiaDevolucao.getImovel().getSetorComercial());
					resumoFaturamentoModelo.setImovelPerfil(guiaDevolucao.getImovel().getImovelPerfil());
					resumoFaturamentoModelo.setGerenciaRegional(guiaDevolucao.getLocalidade().getGerenciaRegional());
					resumoFaturamentoModelo.setValorItemFaturamento(BigDecimal.ZERO);

					// Obtém a principal Categoria do Imóvel
					Categoria categoriaImovel = getControladorImovel().obterPrincipalCategoriaImovel(guiaDevolucao.getImovel().getId());
					if(categoriaImovel == null){ // TODO cadastrar Msg
						throw new ControladorException("atencao.categoria_imovel_guia_devolucao_nao_definido");
					}

					ResumoFaturamento resumoFaturamentoDevolucaoCategoria = new ResumoFaturamento();
					try{
						PropertyUtils.copyProperties(resumoFaturamentoDevolucaoCategoria, resumoFaturamentoModelo);
					}catch(IllegalAccessException iaex){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema");
					}catch(InvocationTargetException itex){
						sessionContext.setRollbackOnly();
						throw new ControladorException("erro.sistema");
					}
					resumoFaturamentoDevolucaoCategoria.setId(null);
					// resumoFaturamentoCreditoCategoria.setValorItemFaturamento(categoriaCreditoARealizar.getValorCategoria());
					resumoFaturamentoDevolucaoCategoria.setCategoria(categoriaImovel);
					resumoFaturamentoDevolucaoCategoria.setUltimaAlteracao(new Date());

					if(tipoEventoContabil.equals(EventoContabil.INCLUSAO_CREDITO_A_REALIZAR)){ // Toda
						// inclusão
						// tem
						// a
						// situação
						// como
						// NORMAL
						this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoDevolucaoCategoria,
										LancamentoTipo.CREDITOS_A_REALIZAR_POR_COBRANCA_INDEVIDA_INCLUIDOS, guiaDevolucao
														.getLancamentoItemContabil().getId(), null, null);
						this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoDevolucaoCategoria);
					}
				}

			}
		}catch(ControladorException cx){
			sessionContext.setRollbackOnly();
			throw cx;
		}catch(Exception ex){
			sessionContext.setRollbackOnly();
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0207] - Gerar/Atualizar Resumo do Faturamento - chamado de
	 * Contabilização por Evento de Faturamento.
	 * Método recebe um Resumo de Faturamento e verifica que LancamentoTipo, LancamentoItemContabil
	 * e LancamentoItem serão utilizados no Resumo
	 * 
	 * @param resumoFaturamento
	 *            -> ResumoFaturamento que terão os atributos relativos a LancamentoItemContabil e
	 *            LancamentoItem alterados.
	 * @param idLancamentoTipo
	 *            -> Id do LancamentoTipo do Resumo que está sendo gerado.
	 * @param idLancamentoItemContabil
	 *            -> Id do LancamentoItemContabil que será buscado, caso houver.
	 * @param sequenciaImpressaoItemContabil
	 *            -> Utilizado para facilitar a ordem de Impressão. Em Alguns casos, são passados
	 *            quando não há LancamentoItemContabil
	 * @param idLancamentoItemEvento
	 *            -> Utilizado quando Um Lancamento Tipo possui mais de uma associação com
	 *            LancamentoItem, e deseja-se utilizar um LancamentoItem específico para a Situação.
	 * @author eduardo henrique
	 * @date 29/10/2008
	 * @since v0.06
	 */
	private void atribuirTipoLancamentoResumoFaturamento(ResumoFaturamento resumoFaturamento, Integer idLancamentoTipo,
					Integer idLancamentoItemContabil, Short sequenciaImpressaoItemContabil, Integer idLancamentoItemEvento)
					throws ControladorException{

		try{
			// Filtro Comum utilizado
			FiltroLancamentoTipo filtroLancamentoTipo = new FiltroLancamentoTipo();
			filtroLancamentoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroLancamentoTipo.LANCAMENTO_TIPO_DECREMENTO_VALOR);
			filtroLancamentoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroLancamentoTipo.LANCAMENTO_TIPO_INCREMENTO_VALOR);
			filtroLancamentoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroLancamentoTipo.COLECAO_LANCAMENTO_TIPO_ITEMS);

			LancamentoTipo lancamentoTipo = null;
			filtroLancamentoTipo.limparListaParametros();
			filtroLancamentoTipo.adicionarParametro(new ParametroSimples(FiltroLancamentoTipo.ID, idLancamentoTipo));

			Collection colecaoLancamentoTipo = getControladorUtil().pesquisar(filtroLancamentoTipo, LancamentoTipo.class.getName());
			if(colecaoLancamentoTipo != null && !colecaoLancamentoTipo.isEmpty()){
				lancamentoTipo = (LancamentoTipo) colecaoLancamentoTipo.iterator().next();
			}else{
				throw new ControladorException("atencao.lancamento_tipo_nao_encontrado");
			}

			resumoFaturamento.setLancamentoTipo(lancamentoTipo);
			if(lancamentoTipo != null){
				resumoFaturamento.setSequenciaTipoLancamento(lancamentoTipo.getSequenciaImpressao());
			}

			if(idLancamentoItemContabil != null){
				LancamentoItemContabil lancamentoItemContabil = null;
				FiltroLancamentoItemContabil filtroLancamentoItem = new FiltroLancamentoItemContabil();
				filtroLancamentoItem.adicionarCaminhoParaCarregamentoEntidade("lancamentoItem");

				filtroLancamentoItem.adicionarParametro(new ParametroSimples(FiltroLancamentoItemContabil.ID, idLancamentoItemContabil));
				Collection colecaoLancamentoItemContabil = getControladorUtil().pesquisar(filtroLancamentoItem,
								LancamentoItemContabil.class.getName());
				if(colecaoLancamentoItemContabil != null && !colecaoLancamentoItemContabil.isEmpty()){
					lancamentoItemContabil = (LancamentoItemContabil) colecaoLancamentoItemContabil.iterator().next();
				}else{
					throw new ControladorException("atencao.lancamento_item_nao_encontrado");
				}
				resumoFaturamento.setLancamentoItemContabil(lancamentoItemContabil);
				resumoFaturamento.setLancamentoItem(lancamentoItemContabil.getLancamentoItem());
				resumoFaturamento.setSequenciaItemTipoLancamento(lancamentoItemContabil.getSequenciaImpressao());
			}else{ // Para os resumos que não possuem LancamentoItemContabil

				// Obtém o LancamentoItem, Associado ao LancamentoTipo encontrado
				if(lancamentoTipo.getLancamentoTipoItems() != null && !lancamentoTipo.getLancamentoTipoItems().isEmpty()){
					for(Iterator iterator = lancamentoTipo.getLancamentoTipoItems().iterator(); iterator.hasNext();){
						LancamentoTipoItem lancamentoTipoItem = (LancamentoTipoItem) iterator.next();

						// Caso o Evento requeira um LancamentoItem Específico, o Tipo só é
						// definido, se encontrar um 'match' (casos de Lancamento_Tipo x multiplos
						// Lancamentos_item)
						if(idLancamentoItemEvento == null
										|| (idLancamentoItemEvento != null && lancamentoTipoItem.getLancamentoItem().getId().equals(
														idLancamentoItemEvento))){
							resumoFaturamento.setLancamentoItem(lancamentoTipoItem.getLancamentoItem());
						}
					}
				}else{
					throw new ControladorException("atencao.associacao_lancamento_tipo_item_nao_definido");
				}

				resumoFaturamento.setLancamentoItemContabil(null);
				if(sequenciaImpressaoItemContabil == null){
					resumoFaturamento.setSequenciaItemTipoLancamento(new Short("0"));
				}else{
					resumoFaturamento.setSequenciaItemTipoLancamento(sequenciaImpressaoItemContabil);
				}
			}

		}catch(Exception ex){
			throw new ControladorException("erro.sistema", ex);
		}
	}

	/**
	 * [UC0207] - Gerar/Atualizar Resumo do Faturamento - chamado de
	 * Contabilização por Evento de Faturamento.
	 * Método recebe um Resumo de Faturamento e verifica que LancamentoTipo, LancamentoItemContabil
	 * e LancamentoItem serão utilizados no Resumo
	 * 
	 * @param resumoFaturamento
	 *            -> ResumoFaturamento que terão os atributos relativos a LancamentoItemContabil e
	 *            LancamentoItem alterados.
	 * @param idLancamentoTipo
	 *            -> Id do LancamentoTipo do Resumo que está sendo gerado.
	 * @param idLancamentoItemContabil
	 *            -> Id do LancamentoItemContabil que será buscado, caso houver.
	 * @author eduardo henrique
	 * @date 29/10/2008
	 * @since v0.06
	 */
	private void verificarGeracaoResumoFaturamentoFinanciamentoCurtoPrazo(ResumoFaturamento resumoFaturamentoOrigem,
					Integer idFinanciamentoTipo, short numeroPrestacoes, short numeroPrestacoesCobradas, BigDecimal valorDebitoCategoria)
					throws ControladorException{

		try{
			BigDecimal[] valoresFinanciamentoCurtoPrazo = getControladorFaturamento().obterValorACobrarDeCurtoELongoPrazo(numeroPrestacoes,
							numeroPrestacoesCobradas, valorDebitoCategoria);
			if(valoresFinanciamentoCurtoPrazo != null && valoresFinanciamentoCurtoPrazo[0].compareTo(BigDecimal.ZERO) == 1){
				ResumoFaturamento resumoFaturamentoFinanciamentoTransferidosCurtoPrazo = new ResumoFaturamento();

				try{
					PropertyUtils.copyProperties(resumoFaturamentoFinanciamentoTransferidosCurtoPrazo, resumoFaturamentoOrigem);
				}catch(IllegalAccessException iaex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema");
				}catch(InvocationTargetException itex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema");
				}
				resumoFaturamentoFinanciamentoTransferidosCurtoPrazo.setId(null);
				resumoFaturamentoFinanciamentoTransferidosCurtoPrazo.setValorItemFaturamento(valoresFinanciamentoCurtoPrazo[0]);

				Integer idLancamentoTipoFinanciamentoCurtoPrazo = null;
				if(idFinanciamentoTipo.equals(FinanciamentoTipo.SERVICO_NORMAL)){
					idLancamentoTipoFinanciamentoCurtoPrazo = LancamentoTipo.FINANCIAMENTOS_TRANSFERIDOS_CURTO_PRAZO;
					this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoFinanciamentoTransferidosCurtoPrazo,
									idLancamentoTipoFinanciamentoCurtoPrazo, null, null, LancamentoItem.GRUPO_CONTABIL);

				}else if(idFinanciamentoTipo.equals(FinanciamentoTipo.PARCELAMENTO_AGUA)){
					idLancamentoTipoFinanciamentoCurtoPrazo = LancamentoTipo.PARCELAMENTOS_TRASFERIDOS_PARA_CURTO_PRAZO;
					this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoFinanciamentoTransferidosCurtoPrazo,
									idLancamentoTipoFinanciamentoCurtoPrazo, null, null, LancamentoItem.AGUA);

				}else if(idFinanciamentoTipo.equals(FinanciamentoTipo.PARCELAMENTO_ESGOTO)){
					idLancamentoTipoFinanciamentoCurtoPrazo = LancamentoTipo.PARCELAMENTOS_TRASFERIDOS_PARA_CURTO_PRAZO;
					this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoFinanciamentoTransferidosCurtoPrazo,
									idLancamentoTipoFinanciamentoCurtoPrazo, null, null, LancamentoItem.ESGOTO);

				}else if(idFinanciamentoTipo.equals(FinanciamentoTipo.JUROS_PARCELAMENTO)){
					idLancamentoTipoFinanciamentoCurtoPrazo = LancamentoTipo.PARCELAMENTOS_TRASFERIDOS_PARA_CURTO_PRAZO;
					this.atribuirTipoLancamentoResumoFaturamento(resumoFaturamentoFinanciamentoTransferidosCurtoPrazo,
									idLancamentoTipoFinanciamentoCurtoPrazo, null, null, LancamentoItem.JUROS);

				}
				this.inserirOuAtualizarAgrupamentoResumoFaturamento(resumoFaturamentoFinanciamentoTransferidosCurtoPrazo);
			}
		}catch(Exception e){
			throw new ControladorException("erro.sistema", e);
		}
	}

	/**
	 * Geração / Atualização do Resumo de Faturamento
	 * [UC0207] - Gerar/Atualizar Resumo do Faturamento - chamado de
	 * Contabilização por Evento de Faturamento.
	 * Método recebe um Resumo de Faturamento e verifica se o
	 * 'agrupamento' de:
	 * - Data de Evento
	 * - Ano/Mês de Referência
	 * - Gerência Regional
	 * - Localidade
	 * - Setor Comercial
	 * - Unidade de Negócio
	 * - Perfil do Imóvel
	 * - Categoria
	 * já existe. Se sim, acumula o valor ao registro do Agrupamento.
	 * Verifica ainda , se este registro, afetará outros 'agrupamentos' (acumuladores)
	 * 
	 * @param resumoFaturamento
	 *            r -> ResumoFaturamento que deve ser inserido/atualizado
	 * @author eduardo henrique
	 * @date 23/10/2008
	 * @since v0.06
	 */
	private synchronized void inserirOuAtualizarAgrupamentoResumoFaturamento(ResumoFaturamento resumoFaturamento)
					throws ControladorException{

		// Collection<ResumoFaturamento> colecaoResumosFaturamento = new
		// ArrayList<ResumoFaturamento>();

		try{
			// pesquisar se ja existe um resumo faturamento simulação
			ResumoFaturamento resumoFaturamentoBase = null;

			try{

				resumoFaturamentoBase = repositorioFinanceiro.pesquisarResumoFaturamento(resumoFaturamento);
			}catch(ErroRepositorioException ex){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.erro_consulta_resumo_faturamento", ex);
			}
			// Será usado nos Lancamento_tipo associados
			BigDecimal valorEventoContabilizado = resumoFaturamento.getValorItemFaturamento();

			if(resumoFaturamentoBase != null){ // acumula o valor já existente
				resumoFaturamento.setId(resumoFaturamentoBase.getId());
				resumoFaturamento.setValorItemFaturamento(resumoFaturamento.getValorItemFaturamento().add(
								resumoFaturamentoBase.getValorItemFaturamento()));
			}
			// colecaoResumosFaturamento.add(resumoFaturamento);
			// Inclui ou atualiza o Resumo
			try{
				repositorioFinanceiro.inserirOuAtualizarResumoFaturamento(Collections.singletonList(resumoFaturamento));
			}catch(ErroRepositorioException erx){
				sessionContext.setRollbackOnly();
				throw new ControladorException("atencao.erro_atualizacao_resumo_faturamento", erx);
			}

			Collection<LancamentoTipo> colecaoTiposLancamentoIncremento = new ArrayList<LancamentoTipo>();
			Collection<LancamentoTipo> colecaoTiposLancamentoDecremento = new ArrayList<LancamentoTipo>();

			// verifica se o 'Agrupamento' afetará outros agrupamentos acumuladores
			this.obterLancamentosTipoAssociados(resumoFaturamento.getLancamentoTipo(), colecaoTiposLancamentoIncremento,
							colecaoTiposLancamentoDecremento);

			// adiciona à coleção de resumos , aqueles que terão seus "tipos" afetados (caso não
			// existam, serão criados)
			for(Iterator iteratorLancamentosIncremento = colecaoTiposLancamentoIncremento.iterator(); iteratorLancamentosIncremento
							.hasNext();){
				LancamentoTipo lancamentoTipoIncremento = (LancamentoTipo) iteratorLancamentosIncremento.next();

				ResumoFaturamento resumoFaturamentoModelo = new ResumoFaturamento();
				try{
					PropertyUtils.copyProperties(resumoFaturamentoModelo, resumoFaturamento);
				}catch(IllegalAccessException iaex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema");
				}catch(InvocationTargetException itex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema");
				}

				// Obtém o LançamentoItem Associado ao LancamentoTipo encontrado (Somente
				// Acumuladores)
				if(lancamentoTipoIncremento.getLancamentoTipoItems() != null
								&& !lancamentoTipoIncremento.getLancamentoTipoItems().isEmpty()){
					LancamentoTipoItem lancamentoTipoItem = (LancamentoTipoItem) lancamentoTipoIncremento.getLancamentoTipoItems()
									.iterator().next();
					resumoFaturamentoModelo.setLancamentoItem(lancamentoTipoItem.getLancamentoItem());
				}else{
					throw new ControladorException("atencao.lancamento_tipo_incremento_sem_lancamento_item_definido");
				}

				resumoFaturamentoModelo.setId(null);
				resumoFaturamentoModelo.setLancamentoTipo(lancamentoTipoIncremento);
				resumoFaturamentoModelo.setSequenciaTipoLancamento(lancamentoTipoIncremento.getSequenciaImpressao());
				resumoFaturamentoModelo.setValorItemFaturamento(valorEventoContabilizado);
				try{
					resumoFaturamentoBase = repositorioFinanceiro.pesquisarResumoFaturamento(resumoFaturamentoModelo);
				}catch(ErroRepositorioException ex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.erro_atualizacao_resumo_faturamento", ex);
				}
				if(resumoFaturamentoBase != null){
					resumoFaturamentoModelo.setId(resumoFaturamentoBase.getId());
					resumoFaturamentoModelo.setValorItemFaturamento(resumoFaturamentoBase.getValorItemFaturamento().add(
									resumoFaturamentoModelo.getValorItemFaturamento()));
				}

				// colecaoResumosFaturamento.add(resumoFaturamentoModelo);
				// Inclui ou atualiza o Resumo
				try{
					repositorioFinanceiro.inserirOuAtualizarResumoFaturamento(Collections.singletonList(resumoFaturamentoModelo));
				}catch(ErroRepositorioException erx){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.erro_atualizacao_resumo_faturamento", erx);
				}
			}

			for(Iterator iteratorLancamentosDecremento = colecaoTiposLancamentoDecremento.iterator(); iteratorLancamentosDecremento
							.hasNext();){
				LancamentoTipo lancamentoTipoDecremento = (LancamentoTipo) iteratorLancamentosDecremento.next();

				ResumoFaturamento resumoFaturamentoModelo = new ResumoFaturamento();
				try{
					PropertyUtils.copyProperties(resumoFaturamentoModelo, resumoFaturamento);
				}catch(IllegalAccessException iaex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema");
				}catch(InvocationTargetException itex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("erro.sistema");
				}

				// Obtém o LançamentoItem Associado ao LancamentoTipo encontrado
				if(lancamentoTipoDecremento.getLancamentoTipoItems() != null
								&& !lancamentoTipoDecremento.getLancamentoTipoItems().isEmpty()){
					LancamentoTipoItem lancamentoTipoItem = (LancamentoTipoItem) lancamentoTipoDecremento.getLancamentoTipoItems()
									.iterator().next();
					resumoFaturamentoModelo.setLancamentoItem(lancamentoTipoItem.getLancamentoItem());
				}else{
					throw new ControladorException("atencao.lancamento_tipo_decremento_sem_lancamento_item_definido");
				}

				resumoFaturamentoModelo.setId(null);
				resumoFaturamentoModelo.setLancamentoTipo(lancamentoTipoDecremento);
				resumoFaturamentoModelo.setSequenciaTipoLancamento(lancamentoTipoDecremento.getSequenciaImpressao());
				resumoFaturamentoModelo.setValorItemFaturamento(valorEventoContabilizado);
				try{
					resumoFaturamentoBase = repositorioFinanceiro.pesquisarResumoFaturamento(resumoFaturamentoModelo);
				}catch(ErroRepositorioException ex){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.erro_atualizacao_resumo_faturamento", ex);
				}

				if(resumoFaturamentoBase != null){
					resumoFaturamentoModelo.setId(resumoFaturamentoBase.getId());
					resumoFaturamentoModelo.setValorItemFaturamento(resumoFaturamentoBase.getValorItemFaturamento().subtract(
									resumoFaturamentoModelo.getValorItemFaturamento()));
				}else{ // altera o sinal valor para diminuir corretamente no acumulado
					resumoFaturamentoModelo.setValorItemFaturamento(resumoFaturamentoModelo.getValorItemFaturamento().negate());
				}

				// colecaoResumosFaturamento.add(resumoFaturamentoModelo);
				// Inclui ou atualiza o Resumo
				try{
					repositorioFinanceiro.inserirOuAtualizarResumoFaturamento(Collections.singletonList(resumoFaturamentoModelo));
				}catch(ErroRepositorioException erx){
					sessionContext.setRollbackOnly();
					throw new ControladorException("atencao.erro_atualizacao_resumo_faturamento", erx);
				}
			}

			/*
			 * Ao fim, manda a coleção para atualização
			 * try {
			 * repositorioFinanceiro.inserirOuAtualizarResumoFaturamento(colecaoResumosFaturamento);
			 * } catch (ErroRepositorioException erx) {
			 * sessionContext.setRollbackOnly();
			 * throw new ControladorException("erro.sistema", erx);
			 * }
			 */
		}catch(Exception ex){
			throw new ControladorException("erro.sistema", ex);
		}

	}

	/**
	 * [UC0207] - Gerar/Atualizar Resumo do Faturamento - chamado de
	 * Contabilização por Evento de Faturamento.
	 * Método recebe um LancamentoTipo e Retorna todos os Lancamentos Tipos associados (de forma
	 * recursiva)
	 * 
	 * @param colecaoLancamentoTipoIncremento
	 *            -> Coleção que terá elementos adicionados os Tipos que forem 'Incremento'
	 * @param colecaoLancamentoTipoDecremento
	 *            -> Coleção que terá elementos adicionados os Tipos que forem 'Decremento'
	 * @param LancamentoTipo
	 *            -> LancamentoTipo 'origem'. Deve vir com atributos de
	 *            LancamentoTipoIncrementoValor ou LancamentoTipoDecrementoValor inicializados.
	 * @author eduardo henrique
	 * @date 23/10/2008
	 * @since v0.06
	 */
	private void obterLancamentosTipoAssociados(LancamentoTipo lancamentoTipo, Collection<LancamentoTipo> colecaoLancamentoTipoIncremento,
					Collection<LancamentoTipo> colecaoLancamentoTipoDecremento) throws ControladorException{

		FiltroLancamentoTipo filtroLancamentoTipo = new FiltroLancamentoTipo();
		filtroLancamentoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroLancamentoTipo.COLECAO_LANCAMENTO_TIPO_ITEMS);
		if(lancamentoTipo != null
						&& (lancamentoTipo.getLancamentoTipoIncrementoValor() != null || lancamentoTipo.getLancamentoTipoDecrementoValor() != null)){

			// verifica todos os Tipos que serão 'afetados' e adiciona o Lancamento 'Original' na
			// colecção correta
			if(lancamentoTipo.getLancamentoTipoIncrementoValor() != null){
				filtroLancamentoTipo.adicionarParametro(new ParametroSimples(FiltroLancamentoTipo.ID, lancamentoTipo
								.getLancamentoTipoIncrementoValor().getId()));
			}else{
				filtroLancamentoTipo.adicionarParametro(new ParametroSimples(FiltroLancamentoTipo.ID, lancamentoTipo
								.getLancamentoTipoDecrementoValor().getId()));
			}

			Collection colecaoLancamentoTipo = getControladorUtil().pesquisar(filtroLancamentoTipo, LancamentoTipo.class.getName());
			Iterator iteratorColecaoLancamentoTipo = colecaoLancamentoTipo.iterator();

			while(iteratorColecaoLancamentoTipo.hasNext()){
				LancamentoTipo lancamentoTipoBase = (LancamentoTipo) iteratorColecaoLancamentoTipo.next();

				if(lancamentoTipo.getLancamentoTipoIncrementoValor() != null){
					colecaoLancamentoTipoIncremento.add(lancamentoTipoBase);
				}else{
					colecaoLancamentoTipoDecremento.add(lancamentoTipoBase);
				}

				filtroLancamentoTipo.limparListaParametros();
				if(lancamentoTipoBase.getLancamentoTipoIncrementoValor() != null){ // Busca o
					// próximo
					// LancamentoTipo
					// Associado, se
					// houver
					filtroLancamentoTipo.adicionarParametro(new ParametroSimples(FiltroLancamentoTipo.ID, lancamentoTipoBase
									.getLancamentoTipoIncrementoValor().getId()));

				}else if(lancamentoTipoBase.getLancamentoTipoDecrementoValor() != null){
					filtroLancamentoTipo.adicionarParametro(new ParametroSimples(FiltroLancamentoTipo.ID, lancamentoTipoBase
									.getLancamentoTipoDecrementoValor().getId()));

				}
				// Verifica se será necessário mais uma iteração
				if(filtroLancamentoTipo.getParametros() != null && !filtroLancamentoTipo.getParametros().isEmpty()){
					colecaoLancamentoTipo = getControladorUtil().pesquisar(filtroLancamentoTipo, LancamentoTipo.class.getName());
					if(colecaoLancamentoTipo != null){
						lancamentoTipo = lancamentoTipoBase; // define último tipo de Lançamento
						// para verificação da próxima
						// iteração
						iteratorColecaoLancamentoTipo = colecaoLancamentoTipo.iterator();
					}
				}
			}
		}
	}

	// private Collection<RelatorioEvolucaoContasAReceberContabilHelper>
	// gerarEvolucaoContasAReceberContabilHelper(
	// Collection objetosBase, String tipoGrupo, String descricaoGrupo, int ordem){
	// Iterator iter = objetosBase.iterator();
	// while (iter.hasNext()){
	// Object[] element = (Object[]) iter.next();
	// String descElementoGrupo = (String) element[0];
	// Integer idElementoGrupo = (Integer) element[1];
	// Short sequenciaTipoLancamento = (Short) element[2];
	// Integer tipoCategoria = (Integer) element[3];
	// BigDecimal valorItem = (BigDecimal) element[4];
	//
	// RelatorioEvolucaoContasAReceberContabilHelper helper = new
	// RelatorioEvolucaoContasAReceberContabilHelper(tipoGrupo, descricaoGrupo, ordem,
	// descElementoGrupo, sequenciaTipoLancamento, )
	//
	// }

	/**
	 * [UC3077] Gerar Relatório Posição de Contas a Receber Contábil
	 * 
	 * @author Anderson Italo
	 * @date 20/11/2012
	 */
	public List<Object[]> pesquisarContasRelatorioPosicaoContasAReceberContabil(FiltroRelatorioPosicaoContasAReceberContabil filtro)
					throws ControladorException{

		List<Object[]> colecaoRetorno = new ArrayList<Object[]>();
		try{

			colecaoRetorno = (List<Object[]>) repositorioFinanceiro.pesquisarContasRelatorioPosicaoContasAReceberContabil(filtro);

		}catch(ErroRepositorioException ex){
			ex.printStackTrace();
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * [UC3077] Gerar Relatório Posição de Contas a Receber Contábil
	 * 
	 * @author Anderson Italo
	 * @date 20/11/2012
	 */
	public Collection<Object[]> obterDebitosACobrarRelatorioPosicaoContasAReceberContabil(Integer idGerenciaRegional, Integer idLocalidade,
					Integer anoMesReferencia) throws ControladorException{

		Collection<Object[]> colecaoRetorno = null;
		try{

			colecaoRetorno = repositorioFinanceiro.obterDebitosACobrarRelatorioPosicaoContasAReceberContabil(idGerenciaRegional,
							idLocalidade, anoMesReferencia);

		}catch(ErroRepositorioException ex){
			throw new ControladorException("erro.sistema", ex);
		}

		return colecaoRetorno;
	}

	/**
	 * Método responsável por calcular a quantidade de registros do relatório saldo de contas a
	 * receber contabil
	 * 
	 * @param filtro
	 * @return
	 * @throws ControladorException
	 */
	public Integer calcularQtdaRegistrosRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro) throws ControladorException{

		try{

			return this.repositorioFinanceiro.calcularQtdaRegistrosRelatorioSaldoContasAReceberContabil(filtro);

		}catch(Exception e){

			throw new ControladorException("erro.sistema", e);

		}

	}

	/**
	 * Método responsável por obter os dados do relatório saldo de contas a receber contabil
	 * 
	 * @param filtro
	 * @return
	 * @throws ControladorException
	 */
	public List<ContaAReceberContabil> obterDadosRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro)
					throws ControladorException{

		List<ContaAReceberContabil> retorno = null;

		try{

			retorno = this.repositorioFinanceiro.obterRegistrosRelatorioSaldoContasAReceberContabil(filtro);

		}catch(Exception e){

			throw new ControladorException("erro.sistema", e);

		}

		return retorno;

	}

}
