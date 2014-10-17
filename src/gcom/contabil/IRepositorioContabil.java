/*
 * Copyright (C) 2007-2007 the GSAN ? Sistema Integrado de Gestão de Serviços de Saneamento
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
 * GSAN ? Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Interface para o repositório contabil
 * 
 * @author Genival Barbosa
 * @created 07 de julho de 2011
 */
public interface IRepositorioContabil {

	/**
	 * Método responsável por consultar os LancamentoContabilSintetico a partir de um filtro antes
	 * de
	 * inserir
	 * 
	 * @param filtro
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilSintetico que se enquadram nos parâmetros passados no filtro
	 */
	public Collection consultarLancamentoContabilSinteticoInserir(Map<String, Object> filtro) throws ErroRepositorioException;

	/**
	 * Método responsável por consultar os LancamentoContabilSintetico apartir de um filtro para a
	 * tela de consulta
	 * 
	 * @param filtro
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilSintetico que se enquadram nos parâmetros passados no filtro
	 */
	public Collection<LancamentoContabilSinteticoConsultaHelper> consultarLancamentoContabilSintetico(Map<String, Object> filtro)
					throws ErroRepositorioException;

	/**
	 * altera um lancamentoContabilSintetico na base
	 * 
	 * @param lancamentoContabilSintetico
	 *            Descrição do parâmetro
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public void atualizarLancamentoContabilSintetico(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ErroRepositorioException;

	/**
	 * Método responsável por obter o eventoComercial através do id
	 * 
	 * @param id
	 * @return eventoComercial
	 */
	public EventoComercial obterEventoComercial(Integer id) throws ErroRepositorioException;

	/**
	 * Método responsável por consultar e montar os LancamentoContabilAnaliticoConsultaHelper
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
	 * Retorna o registro mais recente de provisão devedores duvidosos
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
	 * [UC3065] Gerar Integração Contábil
	 * [SB0002] ? Obter data final de lançamento para a Integração Contábil
	 * 
	 * @return dataFinal
	 */
	public Date obterDataFinalLancamentoIntegracaoContabil() throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar os dados de integração com o Sistema Contábil da empresa.
	 * [UC3065] Gerar Integração Contábil
	 * Fluxo Principal:
	 * 
	 * @param dataInicialContabil
	 * @param dataFinalContabil
	 * @return Lista de lancamentos Contabeis Sintético
	 * @throws ErroRepositorioException
	 */
	List<LancamentoContabilSintetico> pesquisaLancamentosContabeisSintetico(Date dataInicialContabil, Date dataFinalContabil)
					throws ErroRepositorioException;

	/**
	 * Metodo temporário para ajuste contábil. Analista de negócio Luís Eduardo
	 * Ocorrência: OC1128813
	 * 
	 * @author Saulo Lima
	 * @since 20/08/2013
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	List<LancamentoContabilSintetico> pesquisaLancamentosContabeisSinteticoAjusteTemp(Date dataContabil) throws ErroRepositorioException;

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0004] ? Gerar Dados para Integração Contábil
	 * Obter o 03- COD_FILIAL_ORIGEM
	 * 
	 * @return idGerenciaregional
	 */
	public int obterIdGerenciaRegionalPorUnidadeContabilAgrupamento(int idUnidadeContabilAgrupamento) throws ErroRepositorioException;

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0004] ? Gerar Dados para Integração Contábil
	 * Obter o 11- descricaoComplemento
	 * 
	 * @param idEventoComercial
	 * @param campoDescricao
	 *            campo com o nome do atributo descrição que vai ser retornado.
	 * @return idGerenciaregional
	 */
	public String obterCampoPorParametroEmEventoComercialLancamentoPorEventoComercial(Integer idEventoComercial, String campoDescricao)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso realiza a geração dos dados de Integração relativos à retenção com o Sistema
	 * Financeiro da empresa através de tabelas de integração.
	 * [UC3066] Gerar Integração Retenção
	 * Fluxo Principal:
	 * 
	 * @param dataInicialPagamento
	 * @param dataFinalPagamento
	 * @return Lista de Retanções.
	 * @throws ErroRepositorioException
	 */
	public List<RetencaoHelper> pesquisaRelacaoRetencao(Date dataInicialPagamento, Date dataFinalPagamento) throws ErroRepositorioException;

	/**
	 * [UC3065] Gerar Integração Contábil
	 * [SB0004] ? Gerar Dados para Integração Contábil
	 * Obter o 03- COD_CONTA_ORIGEM
	 * 
	 * @param codigoOperacao
	 *            dependendo da operação o consulta é pelo CNCT_ID_CREDITO ou CNCT_ID_DEBITO
	 * @param lancamentoContabilSintetico
	 * @return retorna o código da conta origem
	 * @throws ErroRepositorioException
	 */
	public String obterCodigoContaOrigem(String codigoOperacao, LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ErroRepositorioException;

	/**
	 * Método obterNumeroConta
	 * <p>
	 * Esse método implementa a regra para obtenção do Número da Conta corespondente ao critério de
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
	 * Método obterCodigoDocumento
	 * <p>
	 * Esse método implementa a regra de obtenção do código do documento contabil.
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
	 * Método consultarQuantidadeLancamentoContabilSinteticoInserir
	 * <p>
	 * Esse método implementa consulta de quantidade de {@link LancamentoContabilSintetico}
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
	 * Método usado para obter O Evento Comercial Lancamento
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ErroRepositorioException
	 */
	String obterNumeroContaAuiliar(ObjetoContabil objetoContabil,
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException;

	/**
	 * Método usado para obter a conta bancária
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ErroRepositorioException
	 */
	ContaBancaria obterContaBancaria(LancamentoContabilSintetico lancamentoContabilSintetico) throws ErroRepositorioException;

}
