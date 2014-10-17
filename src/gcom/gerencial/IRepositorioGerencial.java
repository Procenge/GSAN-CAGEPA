/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 */

package gcom.gerencial;

import gcom.gerencial.bean.FiltrarRelatorioOrcamentoSINPHelper;
import gcom.gerencial.bean.FiltrarRelatorioQuadroMetasAcumuladoHelper;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
 * @author Raphael Rossiter
 * @created 20/05/2006
 */
public interface IRepositorioGerencial {

	public List consultarComparativoResumosFaturamentoArrecadacaoPendencia(
					InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da faturamento
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento, Arrecada��o e da Pend�ncia.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoFaturamento(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da arrecada��o
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento, Arrecada��o e da Pend�ncia.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarResumoArrecadacao(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o valor e a quantidade de contas do resumo da pend�ncia.
	 * [UC0350] - Consultar Comparativo entre os Resumos do Faturamento, Arrecada��o e da Pend�ncia.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/06/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List consultarComparativoResumoPendencia(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os valores necessarios na tabela un_resumo_ligacao_economia.
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[34]
	 *         Object[0] - aguaTotalLigacoesCadastradas
	 *         Object[1] - esgotoTotalLigacoesCadastradas
	 *         Object[2] - esgotoTotalLigacoesCadastradasConvencional
	 *         Object[3] - aguaTotalLigacoesAtivas
	 *         Object[4] - esgotoTotalLigacoesCadastradasCondominial
	 *         Object[5] - aguaTotalLigacoesMedidas
	 *         Object[6] - esgotoTotalLigacoesAtivasConvencional
	 *         Object[7] - aguaTotalLigacoesComHidrometro
	 *         Object[8] - esgotoTotalLigacoesAtivasCondominial
	 *         Object[9] - aguaTotalLigacoesResidencialCadastradas
	 *         Object[10] - esgotoTotalLigacoesResidencialCadastradas
	 *         Object[11] - aguaTotalLigacoesDesligadas
	 *         Object[12] - aguaTotalEconomiasCadastradas
	 *         Object[13] - esgotoTotalEconomiasCadastradasConverncional
	 *         Object[14] - aguaTotalEconomiasAtivas
	 *         Object[15] - aguaTotalEconomiasCadastradasCondominial
	 *         Object[16] - aguaTotalEconomiasAtivasMedidas
	 *         Object[17] - esgotoTotalEconomiasAtivasConvencional
	 *         Object[18] - aguaTotalEconomiasResidencialCadastradas
	 *         Object[19] - esgotoTotalEconomiasAtivasCondominial
	 *         Object[20] - aguaTotalEconomiasResidencialAtivasMicromedidas
	 *         Object[21] - esgotoTotalEconomiasResidencialCadastradas
	 *         Object[22] - aguaTotalEconomiasResidencialAtivas
	 *         Object[23] - esgotoTotalEconomiasResidencialAtivas
	 *         Object[24] - aguaTotalEconomiasComercialAtivas
	 *         Object[25] - esgotoTotalEconomiasComercialAtivas
	 *         Object[26] - aguaTotalEconomiasIndustrialAtivas
	 *         Object[27] - esgotoTotalEconomiasIndustrialAtivas
	 *         Object[28] - aguaTotalEconomiasPublicoAtivas
	 *         Object[29] - esgotoTotalEconomiasPublicoAtivas
	 *         Object[30] - aguaTotalEconomiasRuralAtivas
	 *         Object[31] - aguaTotalLigacoesSuprimidas
	 *         Object[32] - campoSelecionado pode ser (loca_id,uneg_id,greg_id)
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPResumoLigacaoEconomia(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException;

	/**
	 * Pesquisa os valores necessarios na tabela un_resumo_faturamento
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[29]
	 *         Object[0] - aguaTotalVolumeFaturadoMedido
	 *         Object[1] - esgotoTotalVolumeFaturadoResidencial
	 *         Object[2] - esgotoTotalVolumeFaturadoComercial
	 *         Object[3] - aguaTotalVolumeFaturadoEstimado
	 *         Object[4] - esgotoTotalVolumeFaturadoIndustrial
	 *         Object[5] - esgotoTotalVolumeFaturadoPublico
	 *         Object[6] - aguaTotalVolumeFaturadoResidencial
	 *         Object[7] - esgotoTotalVolumeFaturadoGeral
	 *         Object[8] - aguaTotalVolumeFaturadoComercial
	 *         Object[9] - aguaTotalVolumeFaturadoIndustrial
	 *         Object[10] - aguaTotalVolumeFaturadoPublico
	 *         Object[11] - aguaTotalVolumeFaturadoRural
	 *         Object[12] - aguaTotalVolumeFaturadoGeral
	 *         Object[13] - aguaTotalVolumeFaturadoResidencial
	 *         Object[14] - esgotoTotalVolumeFaturadoResidencial
	 *         Object[15] - aguaTotalVolumeFaturadoComercial
	 *         Object[16] - esgotoTotalVolumeFaturadoComercial
	 *         Object[17] - aguaTotalVolumeFaturadoIndustrial
	 *         Object[18] - esgotoTotalVolumeFaturadoIndustrial
	 *         Object[19] - aguaTotalVolumeFaturadoPublico
	 *         Object[20] - esgotoTotalVolumeFaturadoPublico
	 *         Object[21] - aguaTotalFaturadoDireto
	 *         Object[22] - esgotoTotalFaturadoDireto
	 *         Object[23] - aguaTotalFaturadoIndireto
	 *         Object[24] - esgotoTotalFaturadoIndireto
	 *         Object[25] - devolucao
	 *         Object[26] - campoSelecionado pode ser (loca_id,uneg_id,greg_id)
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPResumoFaturamento(FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o total das liga�oes tabela un_resumo_ligacao_economia
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[2]
	 *         Object[0] - quantidadeLigacoesAgua
	 *         Object[1] - quantidadeLigacoesEsgoto
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPTotalLigacoesResumoLigacaoEconomia(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException;

	/**
	 * Pesquisa o total dos consumos tabela un_resumo_consumo_agua
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[2]
	 *         Object[0] - volumeFaturadoMicroMedido
	 *         Object[1] - volumeFaturadoEconomiasResidenciasAtivasMicroMedido
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarRelatorioOrcamentoSINPTotalComumoResumoConsumoAgua(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException;

	/**
	 * Pesquisa o total dos consumos tabela un_resumo_consumo_agua
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 20/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return Object[2]
	 *         Object[0] - volumeFaturadoMicroMedido
	 *         Object[1] - volumeFaturadoEconomiasResidenciasAtivasMicroMedido
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarRelatorioOrcamentoSINPTotalArrecadacaoResumoArrecadacao(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper, boolean ehAnterior)
					throws ErroRepositorioException;

	/**
	 * Pesquisa todas as localidades da tabela g_localidade
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 21/11/2006
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarLocalidades() throws ErroRepositorioException;

	/**
	 * Pesquisa todas as unidades da tabela g_unidade_negocio
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 21/11/2006
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarUnidadesNegocios() throws ErroRepositorioException;

	/**
	 * Pesquisa todas as unidades da tabela g_gerencia_regional
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 21/11/2006
	 * @return Collection<Integer>
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarGerenciasRegionais() throws ErroRepositorioException;

	/**
	 * Pesquisa o total de contas a receber consumos tabela un_resumo_pendencia
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 30/11/2006
	 * @param FiltrarRelatorioOrcamentoSINPHelper
	 * @return BigDecimal
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarRelatorioOrcamentoSINPTotalContasResumoPendencia(
					FiltrarRelatorioOrcamentoSINPHelper filtrarRelatorioOrcamentoSINPHelper) throws ErroRepositorioException;

	/**
	 * [UC0733] Gerar Quadro de metas Acumulado
	 * 
	 * @author Bruno Barros
	 * @param filtrarRelatorioQuadroMetasAcumuladoHelper
	 * @return
	 */
	public List pesquisarRelatorioQuadroMetasAcumulado(FiltrarRelatorioQuadroMetasAcumuladoHelper filtrarRelatorioQuadroMetasAcumuladoHelper)
					throws ErroRepositorioException;

	/**
	 * Pesquisa todas as tabelas de resumo para o Orcamento
	 * [UC0722] - Gerar Relatorio para Or�amento e SINP
	 * 
	 * @author Rafael Pinto
	 * @date 11/01/2008
	 * @return anoMesReferencia
	 * @throws ErroRepositorioException
	 */
	public boolean existeDadosUnResumoParaOrcamentoSINP(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC3095] Apresentar Quadro Comparativo Faturamento e Arrecada��o
	 * Consulta o quadro comparativo de faturamento e arrecada��o por estado
	 * 
	 * @author Luciano Galvao
	 * @date 07/05/2013
	 */
	public List consultarQuadroComparativoFaturamentoArrecadacaoPorEstado(Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC3095] Apresentar Quadro Comparativo Faturamento e Arrecada��o
	 * Consulta o quadro comparativo de faturamento e arrecada��o por ger�ncia regional
	 * 
	 * @author Luciano Galvao
	 * @date 07/05/2013
	 */
	public List consultarQuadroComparativoFaturamentoArrecadacaoPorGerenciaRegional(Integer anoMesReferencia, Integer idGerenciaRegional)
					throws ErroRepositorioException;

	/**
	 * [UC3095] Apresentar Quadro Comparativo Faturamento e Arrecada��o
	 * Consulta o quadro comparativo de faturamento e arrecada��o por localidade
	 * 
	 * @author Luciano Galvao
	 * @date 07/05/2013
	 */
	public List consultarQuadroComparativoFaturamentoArrecadacaoPorLocalidade(Integer anoMesReferencia, Integer idLocalidade)
					throws ErroRepositorioException;

}
