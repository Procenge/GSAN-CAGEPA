/*
 * Copyright (C) 2007-2007 the GSAN ? Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place ? Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN ? Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.contabil;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.imovel.Categoria;
import gcom.contabil.bean.LancamentoContabilAnaliticoConsultaHelper;
import gcom.contabil.bean.LancamentoContabilSinteticoConsultaHelper;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.ImpostoTipo;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.integracao.piramide.bean.RetencaoHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interface para o reposit�rio contabil
 * 
 * @author Genival Barbosa
 * @created 07 de julho de 2011
 */
public interface IRepositorioContabil {

	/**
	 * M�todo respons�vel por consultar os LancamentoContabilSintetico a partir de um filtro antes
	 * de
	 * inserir
	 * 
	 * @param filtro
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilSintetico que se enquadram nos par�metros passados no filtro
	 */
	public Collection consultarLancamentoContabilSinteticoInserir(Map<String, Object> filtro) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por consultar os LancamentoContabilSintetico apartir de um filtro para a
	 * tela de consulta
	 * 
	 * @param filtro
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilSintetico que se enquadram nos par�metros passados no filtro
	 */
	public Collection<LancamentoContabilSinteticoConsultaHelper> consultarLancamentoContabilSintetico(Map<String, Object> filtro)
					throws ErroRepositorioException;

	/**
	 * altera um lancamentoContabilSintetico na base
	 * 
	 * @param lancamentoContabilSintetico
	 *            Descri��o do par�metro
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public void atualizarLancamentoContabilSintetico(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por obter o eventoComercial atrav�s do id
	 * 
	 * @param id
	 * @return eventoComercial
	 */
	public EventoComercial obterEventoComercial(Integer id) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por consultar e montar os LancamentoContabilAnaliticoConsultaHelper
	 * 
	 * @param lancamentoContabilSintetico
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilAnaliticoConsultaHelper
	 * @throws ErroRepositorioException
	 */
	public Collection<LancamentoContabilAnaliticoConsultaHelper> consultarLancamentoContabilAnaliticoPorSintetico(
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException;

	/**
	 * Carregar atributos lazy da conta
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void carregarAtributosConta(Conta conta);

	/**
	 * Carregar atributos lazy da contaHistorico
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void carregarAtributosContaHistorico(ContaHistorico conta);

	/**
	 * Carregar atributos lazy da GuiaPagamentoGeral
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void carregarAtributosGuiaPagamentoGeral(GuiaPagamentoGeral guiaPagamentoGeral);

	/**
	 * Carregar atributos lazy da DebitoACobrarHistorico
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void carregarAtributosDebitoACobrarHistorico(DebitoACobrarHistorico debitoACobrar);

	/**
	 * Carregar atributos lazy do DebitoACobrar
	 * 
	 * @param debitoACobrar
	 */
	public void carregarAtributosDebitoACobrar(DebitoACobrar debitoACobrar);

	public void carregarAtributosPagamento(Pagamento pagamento);

	public void carregarAtributosPagamentoHistorico(PagamentoHistorico pagamentoHistorico);

	/**
	 * Retorna o registro mais recente de provis�o devedores duvidosos
	 * 
	 * @date 26/06/2012
	 * @author Hugo Lima
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ProvisaoDevedoresDuvidosos obterProvisaoDevedoresDuvidososMaisRecente(Integer idConta) throws ErroRepositorioException;

	/**
	 * Retorna o total de eventos comerciais
	 * 
	 * @date 26/06/2012
	 * @author Hugo Lima
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterTotalEventoComercial() throws ErroRepositorioException;

	/**
	 * [UC3065] Gerar Integra��o Cont�bil
	 * [SB0002] ? Obter data final de lan�amento para a Integra��o Cont�bil
	 * 
	 * @return dataFinal
	 */
	public Date obterDataFinalLancamentoIntegracaoContabil() throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar os dados de integra��o com o Sistema Cont�bil da empresa.
	 * [UC3065] Gerar Integra��o Cont�bil
	 * Fluxo Principal:
	 * 
	 * @param dataInicialContabil
	 * @param dataFinalContabil
	 * @return Lista de lancamentos Contabeis Sint�tico
	 * @throws ErroRepositorioException
	 */
	List<LancamentoContabilSintetico> pesquisaLancamentosContabeisSintetico(Date dataInicialContabil, Date dataFinalContabil)
					throws ErroRepositorioException;

	/**
	 * Metodo tempor�rio para ajuste cont�bil. Analista de neg�cio Lu�s Eduardo
	 * Ocorr�ncia: OC1128813
	 * 
	 * @author Saulo Lima
	 * @since 20/08/2013
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	List<LancamentoContabilSintetico> pesquisaLancamentosContabeisSinteticoAjusteTemp(Date dataContabil) throws ErroRepositorioException;

	/**
	 * [UC3065] Gerar Integra��o Cont�bil
	 * [SB0004] ? Gerar Dados para Integra��o Cont�bil
	 * Obter o 03- COD_FILIAL_ORIGEM
	 * 
	 * @return idGerenciaregional
	 */
	public int obterIdGerenciaRegionalPorUnidadeContabilAgrupamento(int idUnidadeContabilAgrupamento) throws ErroRepositorioException;

	/**
	 * [UC3065] Gerar Integra��o Cont�bil
	 * [SB0004] ? Gerar Dados para Integra��o Cont�bil
	 * Obter o 11- descricaoComplemento
	 * 
	 * @param idEventoComercial
	 * @param campoDescricao
	 *            campo com o nome do atributo descri��o que vai ser retornado.
	 * @return idGerenciaregional
	 */
	public String obterCampoPorParametroEmEventoComercialLancamentoPorEventoComercial(Integer idEventoComercial, String campoDescricao)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso realiza a gera��o dos dados de Integra��o relativos � reten��o com o Sistema
	 * Financeiro da empresa atrav�s de tabelas de integra��o.
	 * [UC3066] Gerar Integra��o Reten��o
	 * Fluxo Principal:
	 * 
	 * @param dataInicialPagamento
	 * @param dataFinalPagamento
	 * @return Lista de Retan��es.
	 * @throws ErroRepositorioException
	 */
	public List<RetencaoHelper> pesquisaRelacaoRetencao(Date dataInicialPagamento, Date dataFinalPagamento) throws ErroRepositorioException;

	/**
	 * [UC3065] Gerar Integra��o Cont�bil
	 * [SB0004] ? Gerar Dados para Integra��o Cont�bil
	 * Obter o 03- COD_CONTA_ORIGEM
	 * 
	 * @param codigoOperacao
	 *            dependendo da opera��o o consulta � pelo CNCT_ID_CREDITO ou CNCT_ID_DEBITO
	 * @param lancamentoContabilSintetico
	 * @return retorna o c�digo da conta origem
	 * @throws ErroRepositorioException
	 */
	public String obterCodigoContaOrigem(String codigoOperacao, LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ErroRepositorioException;

	/**
	 * M�todo obterNumeroConta
	 * <p>
	 * Esse m�todo implementa a regra para obten��o do N�mero da Conta corespondente ao crit�rio de
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004][ITEM 5 e 7]
	 * 
	 * @param tipo
	 * @param eventoComercial
	 * @param categoria
	 * @param lancamentoItemContabil
	 * @param impostoTipo
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public String obterNumeroConta(ObjetoContabil tipo, EventoComercial eventoComercial, Categoria categoria,
					LancamentoItemContabil lancamentoItemContabil, ImpostoTipo impostoTipo) throws ErroRepositorioException;

	/**
	 * M�todo obterCodigoDocumento
	 * <p>
	 * Esse m�todo implementa a regra de obten��o do c�digo do documento contabil.
	 * </p>
	 * RASTREIO: [OC750003][UC3065][SB0004][ITEM 13]
	 * 
	 * @param eventoComercial
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/09/2012
	 */
	public String obterDescricaoDocumento(EventoComercial eventoComercial) throws ErroRepositorioException;

	/**
	 * M�todo consultarQuantidadeLancamentoContabilSinteticoInserir
	 * <p>
	 * Esse m�todo implementa consulta de quantidade de {@link LancamentoContabilSintetico}
	 * </p>
	 * RASTREIO: [OC997181]
	 * 
	 * @param mapaParametros
	 * @return
	 * @author Marlos Ribeiro
	 * @since 05/04/2013
	 */
	public int consultarQuantidadeLancamentoContabilSinteticoInserir(Map<String, Object> mapaParametros) throws ErroRepositorioException;

	/**
	 * M�todo usado para obter O Evento Comercial Lancamento
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ErroRepositorioException
	 */
	String obterNumeroContaAuiliar(ObjetoContabil objetoContabil,
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException;

	/**
	 * M�todo usado para obter a conta banc�ria
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ErroRepositorioException
	 */
	ContaBancaria obterContaBancaria(LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException;

}
