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

import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.relatorio.financeiro.FiltroRelatorioPosicaoContasAReceberContabil;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Interface para o repositório de financeiro
 * 
 * @author Raphael Rossiter
 * @since 09/01/2006
 */
public interface IRepositorioFinanceiro {

	/**
	 * Obtém os dados do resumoFaturamento a partir do ano e mês de referência
	 * [UC0175] - Gerar Lançamentos Contábeis do Faturamento
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 16/01/2006, 24/05/2007
	 * @param anoMesReferenciaFaturamento
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterDadosResumoFaturamento(Integer anoMesReferenciaFaturamento, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * Obtém a conta contábil a partir do número da razão contábil e do núemro da conta
	 * 
	 * @param razao
	 * @param conta
	 * @return ContaContabil
	 * @throws ErroRepositorioException
	 */
	public ContaContabil obterContaContabil(Short razao, Integer conta) throws ErroRepositorioException;

	/**
	 * Obtém a conta contábil a partir da tabela LANCAMENTO_ITEM_CONTABIL
	 * 
	 * @param razao
	 * @param conta
	 * @return ContaContabil
	 * @throws ErroRepositorioException
	 */
	public ContaContabil obterContaContabil(LancamentoItemContabil lancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * Gera Lançamentos Contabeis do Faturamento
	 * [UC000348] - Gerar Lançamento Constábeis da Arrecadação
	 * Obter O Parametros Contabile Arrecadacao
	 * 
	 * @author Rafael Santos
	 * @date 23/05/2006
	 * @param anoMesArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public Collection obterParametrosContabilArrecadacao(Integer idCategoria,Integer
	 * idItemLancamentoContabil,
	 * Integer idItemLancamento,Integer idTipoLancamento,Integer idTipoRecebimento)
	 * throws ErroRepositorioException;
	 */

	/**
	 * Gera Lançamentos Contabeis do Faturamento
	 * [UC000348] - Gerar Lançamento Constábeis da Arrecadação
	 * 
	 * @author Rafael Santos, Pedro Alexandre
	 * @date 23/05/2006, 25/05/2007
	 * @param anoMesArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterDadosResumoArrecadacao(Integer anoMesReferenciaArrecadacao, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * este metodo é utilizado para pesquisar os registros q serão
	 * usados para contrução do txt do caso de uso
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGerarIntegracaoContabilidade(String idLancamentoOrigem, String anoMes) throws ErroRepositorioException;

	/**
	 * Exclui os registros da tabela RESUMO_DEVEDORES_DUVIDOSOS
	 * por ano mês refência contábil
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @throws ErroRepositorioException
	 */
	public void removeResumoDevedoresDuvidososPorAnoMesReferenciaContabil(int anoMesReferenciaContabil) throws ErroRepositorioException;

	/**
	 * Atualiza com o valor nulo o mês/ano de referência de baixa contábil
	 * das contas baixadas contabilmente no ano/mês de referência
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @throws ErroRepositorioException
	 */
	public void atualizaContaAnoMesReferenciaContabil(int anoMesReferenciaContabil) throws ErroRepositorioException;

	/**
	 * Seleciona todas as ocorrencias dos itens dos parâmetros
	 * baixa contábil
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @throws ErroRepositorioException
	 */
	public Collection<ParametrosDevedoresDuvidososItem> pesquisaParametrosDevedoresDuvidososItem(Integer idParametrosDevedoresDuvidosos)
					throws ErroRepositorioException;

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 01 Retorna o valor de água acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorAgua(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 02 Retorna o valor do esgoto acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorEsgoto(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 03 Retorna o valor da categoria acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoAgua(
					int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 03 Retorna o valor da categoria acumulado por financiamento tipo esgoto,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoEsgoto(
					int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 05 Retorna o valor da categoria acumulado por financiamento por gupo contabil,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoServicos(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 06 Retorna o valor da categoria acumulado por financiamento juros parcelamentos,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoDevedoresDuvidosos acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoJurosParcelamento(
					int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * Linha 07 Retorna o valor da categoria acumulado por financiamento por serviço,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            Código da localidade
	 * @param idCategoria
	 *            Código da categoria
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public Collection acumularValorCategoriaDebitoTipoFinanciamentoServico(int anoMesReferenciaBaixaContabil, int idLocalidade,
					int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0345] - Gerar Relatorio de Resumo da Arrecadação
	 * 
	 * @author Vivianne Sousa
	 * @date 10/04/2007
	 * @param idLancamentoTipo
	 * @throws ErroRepositorioException
	 */
	public String obterDescricaoLancamentoTipo(Integer idLancamentoTipo) throws ErroRepositorioException;

	/**
	 * [UC00175] Gerar Lançamentos Contábeis do Faturamento
	 * Pesquisa os parâmetros contábil do faturamento.
	 * 
	 * @author Pedro Alexandre
	 * @date 24/05/2007
	 * @param idCategoria
	 * @param idItemLancamentoContabil
	 * @param idItemLancamento
	 * @param idTipoLancamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterParametrosContabilFaturamento(Integer idCategoria, Integer idLancamentoItemContabil, Integer idItemLancamento,
					Integer idTipoLancamento) throws ErroRepositorioException;

	/**
	 * Pesquisa as localidades que tem resumo de faturamento
	 * para o ano/mês de faturamento informado.
	 * [UC00175] Gerar Lançamentos Contábeis do Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 25/05/2007
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisFaturamento(Integer anoMesFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC00348] Gerar Lançamentos Contábeis da Arrecadação
	 * Pesquisa os parâmetros contábil da arrecadação.
	 * 
	 * @author Pedro Alexandre
	 * @date 31/05/2007
	 * @param idRecebimentoTipo
	 * @param idCategoria
	 * @param idItemLancamentoContabil
	 * @param idItemLancamento
	 * @param idTipoLancamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterParametrosContabilArrecadacao(Integer idRecebimentoTipo, Integer idCategoria, Integer idLancamentoItemContabil,
					Integer idItemLancamento, Integer idTipoLancamento) throws ErroRepositorioException;

	/**
	 * Pesquisa as localidades que tem resumo da arrecadação
	 * para o ano/mês de arrecadação informado.
	 * [UC00348] Gerar Lançamentos Contábeis da Arrecadação
	 * 
	 * @author Pedro Alexandre
	 * @date 31/05/2007
	 * @param anoMesArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesParaGerarLancamentosContabeisArrecadacao(Integer anoMesArrecadacao)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os parâmetros dos devedores duvidosos por
	 * ano/mês de referência contábil.
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 06/06/2007
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ParametrosDevedoresDuvidosos pesquisarParametrosDevedoresDuvidosos(Integer anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * Atualiza com o valor nulo o mês/ano de referência de baixa contábil
	 * das contas baixadas contabilmente no ano/mês de referência
	 * para a localidade informada.
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 06/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public void atualizaContaAnoMesReferenciaContabil(int anoMesReferenciaContabil, Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Exclui os registros da tabela RESUMO_DEVEDORES_DUVIDOSOS
	 * por ano mês refência contábil e localidade
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 06/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public void removeResumoDevedoresDuvidososPorAnoMesReferenciaContabil(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesGerarResumoDevedoresDuvidosos(int anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * Linha 01 Retorna o valor de água acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 12/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorAgua(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria,
					Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Linha 02 Retorna o valor do esgoto acumulado,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEsgoto(int anoMesReferenciaBaixaContabil, int idLocalidade, int idCategoria,
					Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Linha 03 Retorna o valor da categoria acumulado por tipo financiamento por parcelamento agua,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoAgua(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Linha 04 Retorna o valor da categoria acumulado por financiamento por parcelamento esgoto,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria.
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoEsgoto(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Linha 06 Retorna o valor da categoria acumulado por financiamento juros parcelamentos,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return retorna o valor acumulado de acordo com os parâmetros informados
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoJurosParcelamento(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Linha 05 Retorna o valor da categoria acumulado por financiamento por parcelamento serviço,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamento(int anoMesReferenciaBaixaContabil, int idLocalidade,
					int idCategoria, int idFinanciamentoTipo, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Este metódo é utilizado para pesquisar os registros q serão
	 * usados para contrução do txt do caso de uso
	 * [UC0469] Gerar Integração para a Contabilidade
	 * 
	 * @author Flávio Cordeiro
	 * @date 06/06/2007
	 * @param idLancamentoOrigem
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGerarIntegracaoContabilidadeCaern(String idLancamentoOrigem, String anoMes) throws ErroRepositorioException;

	/**
	 * Atualiza com o valor de referência de baixa contábil
	 * das contas baixadas contabilmente no ano/mês de referência
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 14/06/2007
	 * @param anoMesReferenciaContabil
	 * @param colecaoIdsContas
	 * @throws ErroRepositorioException
	 */
	public void atualizaContaAnoMesReferenciaContabil(int anoMesReferenciaContabil, Collection<Integer> colecaoIdsContas)
					throws ErroRepositorioException;

	/**
	 * Obtém os dados do resumo dos devedores duvidosos
	 * a partir do ano e mês de referência contábil e da localidade.
	 * [UC0486] - Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 21/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterDadosResumoDevedoresDuvidosos(Integer anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0486] Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * Pesquisa os parâmetros contábil dos devedores duvidosos.
	 * 
	 * @author Pedro Alexandre
	 * @date 21/06/2007
	 * @param idCategoria
	 * @param idItemLancamentoContabil
	 * @param idItemLancamento
	 * @param idTipoLancamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] obterParametrosContabilDevedoresDuvidosos(Integer idCategoria, Integer idLancamentoItemContabil,
					Integer idItemLancamento, Integer idTipoLancamento) throws ErroRepositorioException;

	/**
	 * Pesquisa a coleção de ids das localidades para processar os lançamentos
	 * contábeis dos devedores duvidosos.
	 * [UC0485] Gerar Lançamentos Contábeis dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 25/06/2007
	 * @param anoMesReferenciaContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadesGerarLancamentosContabeisDevedoresDuvidosos(int anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * Pesquisa uma coleção de ids de lançamentos contábeis por localidade.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idLancamentoOrigem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLancamentosContabeis(Integer anoMesReferenciaContabil, Integer idLocalidade,
					Integer idLancamentoOrigem) throws ErroRepositorioException;

	/**
	 * Remove os Itens do lançamento contábil.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param idLancamentoContabil
	 * @throws ErroRepositorioException
	 */
	public void removerItensLancamentoContabil(Integer idLancamentoContabil) throws ErroRepositorioException;

	/**
	 * Remove os Lançamentos Contábeis.
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param colecaoIdsLancamentosContabeis
	 * @throws ErroRepositorioException
	 */
	public void removerLancamentosContabeis(Collection<Integer> colecaoIdsLancamentosContabeis) throws ErroRepositorioException;

	/**
	 * Linha 05 Retorna o valor da categoria acumulado por financiamento por parcelamento serviço,
	 * de acordo com o ano/mês de referência baixa contabil, a localiade, a categoria
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @param anoMesReferenciaBaixaContabil
	 * @param idLocalidade
	 * @param idCategoria
	 * @param colecaoIdsContas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoServico(int anoMesReferenciaBaixaContabil,
					int idLocalidade, int idCategoria, Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstado(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstadoPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioEstadoPorUnidadeNegocio(int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorEstadoPorLocalidade(int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegional(int anoMesReferencia, Integer gerenciaRegional)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorGerenciaRegionalPorLocalidade(int anoMesReferencia,
					Integer gerenciaRegional) throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer unidadeNegocio)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoDevedoresDuvidosos para a geração do relatório
	 * [UC0487] Gerar Relatório de Resumo de Devedores Duvidosos
	 * de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 19/07/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoDevedoresDuvidososRelatorioPorLocalidade(int anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Remove as contas a receber contábil
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public void removerContasAReceberContabil(int anoMesReferenciaContabil, Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores de água e esgoto pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosContasCategoriaValorAguaEsgoto(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos cobrados para serviços pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitosCobradosCategoriaServico(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos cobrados para parcelamentos pela gerência, localidade e
	 * categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitosCobradosCategoriaParcelamento(int anoMesReferenciaContabil, Integer idLocalidade,
					Collection<Integer> tiposParcelamento) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores das guias de pagamento para entradas de parcelamento pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosGuiasPagamentoCategoriaEntradaParcelamento(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores das guias de pagamento para serviços pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosGuiasPagamentoCategoriaServico(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para pagamentos em duplicidade ou em excesso pela
	 * gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaPagamentoExcesso(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para descontos no parcelamento pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDescontoParcelamento(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para descontos condicionais pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDescontoCondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para descontos incondicionais pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDescontoIncondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para ajustes para zerar conta pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaAjusteZerarConta(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos realizados para devoluções pela gerência, localidade e
	 * categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosRealizadosCategoriaDevolucao(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para serviço pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaServico(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para documentos emitidos pela gerência, localidade e
	 * categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaDocumentosEmitidos(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para financiamentos a cobrar de
	 * curto prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaFinancimentosCurtoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para financiamentos a cobrar de
	 * longo prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaFinancimentosLongoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para parcelamentos a cobrar de
	 * curto prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaParcelamentosCurtoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para parcelamentos a cobrar de
	 * longo prazo pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaParcelamentosLongoPrazo(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos débitos a cobrar para arrasto de água, arrasto de
	 * esgoto e arrasto de serviço pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosDebitoACobrarCategoriaArrasto(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para descontos concedidos no
	 * parcelamento pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditoARealizarCategoriaDescontosParcelamento(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para devoluções pela gerência, localidade e
	 * categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaDevolucao(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para descontos incondicionais pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaDescontoIncondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para contas pagas em excesso
	 * ou em duplicidade pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaPagamentoExcesso(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para descontos condicionais pela gerência,
	 * localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaDescontoCondicional(int anoMesReferenciaContabil,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0714] - Gerar Contas a Receber Contábil
	 * Acumula os valores dos créditos a realizar para ajustes para zerar contas
	 * pela gerência, localidade e categoria
	 * 
	 * @author Rafael Corrêa
	 * @date 08/11/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaContabil
	 *            Ano e mês de referência contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Object[]> pesquisarDadosCreditosARealizarCategoriaAjusteZerarConta(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * Seleciona as quadras da localidade informada
	 * onde existe contas a serem baixadas contabiolmente
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterQuadrasPorLocalidadeParaGerarResumoDevedoresDuvidosos(int anoMesReferenciaContabil, int idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * verifica se a conta informada possui cliente responsável
	 * com esfera de poder de tipo de cliente igual a municipal,
	 * estadual ou federal.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/07/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaClienteResponsavelConta(int idConta) throws ErroRepositorioException;

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorEstado(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio, Integer localidade) throws ErroRepositorioException;

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorEstado(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio, Integer localidade) throws ErroRepositorioException;

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorEstado(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio, Integer localidade) throws ErroRepositorioException;

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorGerenciaRegional(int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer gerencia)
					throws ErroRepositorioException;

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer gerencia)
					throws ErroRepositorioException;

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por Unidade
	 * de Negocio
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer gerencia)
					throws ErroRepositorioException;

	/**
	 * Consultar Saldo da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por
	 * Localidade
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 21/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarSaldoEvolucaoContasAReceberContabilRelatorioPorLocalidade(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio) throws ErroRepositorioException;

	/**
	 * Consultar dados da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por
	 * Localidade
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 26/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosEvolucaoContasAReceberContabilRelatorioPorLocalidade(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio) throws ErroRepositorioException;

	/**
	 * Consultar dados de recebimentos do Relatorio da Evolucao de Contas a Receber Contabil
	 * [UC0718 - Gerar Relatório da Evolução do Contas a Receber Contábil] por Estado e por
	 * Localidade
	 * 
	 * @author: Francisco do Nascimento
	 * @date: 07/01/2008
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarRecebimentosContasAReceberContabilRelatorioPorLocalidade(int anoMesReferencia, Integer gerencia,
					Integer unidadeNegocio) throws ErroRepositorioException;

	/**
	 * UC0207 - Gerar/Atualizar Resumo do Faturamento
	 * Pesquisar se um 'agrupamento' de Resumo Faturamento já existe
	 * 
	 * @author eduardo henrique
	 * @date 24/10/2008
	 * @return ResumoFaturamento
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento pesquisarResumoFaturamento(ResumoFaturamento resumoFaturamentoPesquisa) throws ErroRepositorioException;

	/**
	 * UC0207 - Gerar/Atualizar Resumo do Faturamento
	 * Pesquisar se um 'agrupamento' de Resumo Faturamento já existe
	 * 
	 * @author eduardo henrique
	 * @date 24/10/2008
	 * @param Collection
	 *            <ResumoFaturamento>
	 */
	public void inserirOuAtualizarResumoFaturamento(Collection<ResumoFaturamento> colecaoResumoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC3077] Gerar Relatório Posição de Contas a Receber Contábil
	 * 
	 * @author Anderson Italo
	 * @date 20/11/2012
	 */
	public Collection<Object[]> pesquisarContasRelatorioPosicaoContasAReceberContabil(FiltroRelatorioPosicaoContasAReceberContabil filtro)
					throws ErroRepositorioException;

	/**
	 * [UC3077] Gerar Relatório Posição de Contas a Receber Contábil
	 * 
	 * @author Anderson Italo
	 * @date 20/11/2012
	 */
	public Collection<Object[]> obterDebitosACobrarRelatorioPosicaoContasAReceberContabil(Integer idGerenciaRegional, Integer idLocalidade,
					Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Método responsável por calcular a quantidade de registros do relatório saldo de contas a
	 * receber contabil
	 * 
	 * @param filtro
	 * @return
	 * @throws ErroRepositorioException
	 */
	Integer calcularQtdaRegistrosRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro) throws ErroRepositorioException;

	/**
	 * Método responsável por obter a quantidade de resgistros do relatório saldo de contas a
	 * receber contabil
	 * 
	 * @param filtro
	 * @return
	 * @throws ErroRepositorioException
	 */
	List<ContaAReceberContabil> obterRegistrosRelatorioSaldoContasAReceberContabil(Map<String, Object> filtro)
					throws ErroRepositorioException;

}