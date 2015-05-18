/*
Date * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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

package gcom.faturamento;

import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimento;
import gcom.arrecadacao.pagamento.*;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.QuitacaoDebitoAnual;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.cobranca.parcelamento.ParcelamentoItem;
import gcom.cobranca.parcelamento.ParcelamentoPerfil;
import gcom.faturamento.bean.*;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.conta.*;
import gcom.faturamento.credito.*;
import gcom.faturamento.debito.*;
import gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando;
import gcom.financeiro.ResumoFaturamento;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.bean.ManterContaHelper;
import gcom.micromedicao.MovimentoRoteiroEmpresa;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.faturamento.RelatorioSituacaoEspecialFaturamentoHelper;
import gcom.relatorio.ordemservico.DadosContaEmRevisaoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * < <Descri��o da Interface>>
 * 
 * @author Administrador
 */
public interface IRepositorioFaturamento {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param anoMesReferencia
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Integer pesquisarExistenciaConta(Imovel imovel, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Leonardo Maranh�o Data: 06/12/2008
	 * Atualiza o indicador de cobranca de multa na tabela de Guia Pagamento Prestacao
	 * 
	 * @param colecaoIdsGuiasPagamentoPrestacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorMultaDeGuiaPagamentoPrestacao(Collection<GuiaPagamentoPrestacaoPK> colecaoIdsGuiasPagamentoPrestacao)
					throws ErroRepositorioException;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param faturamentoGrupo
	 *            Descri��o do par�metro
	 * @param anoMesReferencia
	 *            Descri��o do par�metro
	 * @param faturamentoAtividade
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Object[] obterDataPrevistaRealizadaFaturamentoAtividadeCronograma(FaturamentoGrupo faturamentoGrupo, int anoMesReferencia,
					FaturamentoAtividade faturamentoAtividade) throws ErroRepositorioException;

	/**
	 * @param idFaturamentoAtividadeCronograma
	 * @throws ErroRepositorioException
	 */
	public void removerTodasRotasPorCronogramaFaturamento(Integer idFaturamentoAtividadeCronograma) throws ErroRepositorioException;

	/**
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * 
	 * @return uma lista de atividades de faturamento comandadas e ainda n�o
	 *         realizadas
	 * @throws ErroRepositorioException
	 */
	public Collection buscarAtividadeComandadaNaoRealizada(Integer numeroPagina, Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite alterar ou excluir um comando de atividade de
	 * faturamento
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * Retorna o count do resultado da pesquisa de Faturamento Atividade
	 * Cronograma n�o realizadas
	 * buscarAtividadeComandadaNaoRealizadaCount
	 * 
	 * @author Roberta Costa
	 * @date 18/07/2006
	 * @param filtroFaturamentoAtividadeCronograma
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer buscarAtividadeComandadaNaoRealizadaCount(Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * 
	 * @return uma cole��o de FATURAMENTO_ATIVIDADE_CRONOGRAMA
	 * @throws ErroRepositorioException
	 */
	public Collection buscarFaturamentoAtividadeCronograma(String ids) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 26/12/2005
	 * 
	 * @param conta
	 * @return uma cole��o com os d�bitos cobrados de uma conta
	 * @throws ControladorException
	 */
	public Collection buscarDebitosCobradosConta(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 28/12/2005
	 * 
	 * @param conta
	 * @return uma cole��o com os cr�ditos realizados de uma conta
	 * @throws ControladorException
	 */
	public Collection buscarCreditosRealizadosConta(Conta conta) throws ErroRepositorioException;

	public Collection pesquisarConsumoTarifaVigenciaEntreDataLeituraAnterioreDataLeituraAtual(ConsumoTarifa consumoTarifa,
					Date dataLeituraAnterior, Date dataLeituraAtual) throws ErroRepositorioException;

	public Collection pesquisarConsumoTarifaVigenciaMenorDataLeituraAnterior(ConsumoTarifa consumoTarifa, Date dataLeituraAnterior)
					throws ErroRepositorioException;

	public Collection pesquisarConsumoTarifaCategoria(ConsumoTarifaVigencia consumoTarifaVigencia, Categoria categoria)
					throws ErroRepositorioException;

	public Collection pesquisarConsumoTarifaFaixa(ConsumoTarifaCategoria consumoTarifaCategoria) throws ErroRepositorioException;

	/**
	 * [UC0168] - Inserir Tarifa de Consumo Retorna a date de vig�ncia em vigor
	 * de uma tarifa de consumo
	 * 
	 * @param consumoTarifa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConsumoTarifaVigenciaEmVigor(ConsumoTarifa consumoTarifa) throws ErroRepositorioException;

	/**
	 * [UC0168] - Inserir Tarifa de Consumo Retorna a date de vig�ncia em vigor
	 * de uma tarifa de consumo Pesquisa a Data de Vigencia da Consumo Tarifa e
	 * da Consumo Tarifa Vigencia
	 * 
	 * @author Rafael Santos
	 * @since 11/07/2006
	 * @param consumoTarifa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConsumoTarifaVigenciaEmVigor(ConsumoTarifa consumoTarifa, int idConsumoTarifaVigencia)
					throws ErroRepositorioException;

	/**
	 * [UC0145] - Inserir Conta Author: Raphael Rossiter Data: 13/01/2006
	 * Seleciona a partir da tabela CLIENTE_IMOVEL para IMOV_ID=Id do im�vel e
	 * CLIM_DTRELACAOFIM com o valor correspondente a nulo
	 * 
	 * @param IMOVEL
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteImovelDataRelacaoFimNull(Imovel imovel) throws ErroRepositorioException;

	public Integer pesquisarFaturamentoGrupoCronogramaMensal(Integer idFaturamentoGrupo, Integer anoMes) throws ErroRepositorioException;

	public Integer pesquisarFaturamentoAtividadeCronograma(Integer idFaturamentoGrupoCronogramaMensal, Integer idFaturamentoAtividade)
					throws ErroRepositorioException;

	public void atualizarFaturamentoAtividadeCronograma(Integer idFaturamentoAtividadeCronograma, Date dataRealizada)
					throws ErroRepositorioException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @created 18/01/2006
	 */
	public void inserirFaturamentoSituacaoHistorico(Collection collectionFaturamentoSituacaoHistorico) throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Rodrigo Silveira
	 * @created 18/01/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioPorEstado(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Rodrigo Silveira
	 * @created 18/01/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioPorEstadoPorGerenciaRegional(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Rodrigo Silveira
	 * @created 18/01/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioPorEstadoPorLocalidade(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Rodrigo Silveira
	 * @created 18/01/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioPorGerenciaRegional(int anoMesReferencia, Integer gerenciaRegional)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Rodrigo Silveira
	 * @created 18/01/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioPorGerenciaRegionalPorLocalidade(int anoMesReferencia, Integer gerenciaRegional)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Rodrigo Silveira
	 * @created 18/01/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioPorLocalidade(int anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	/**
	 * [UC0146] - Manter Conta Author: Raphael Rossiter Data: 21/01/2006
	 * Obt�m as contas de um im�vel que poder�o ser mantidas
	 * 
	 * @author Saulo Lima
	 * @date 10/09/2008
	 *       Altera��o na chamada do m�todo pra satifazer as especifica��es do UC0146 (ADA):
	 *       Remover os par�metros: situacaoNormal, situacaoIncluida e situacaoRetificada
	 * @param imovel
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasImovelManter(Imovel imovel) throws ErroRepositorioException;

	/**
	 * [UC0146] - Manter Conta
	 * Obt�m as contas de um im�vel que poder�o ser mantidas
	 * 
	 * @author Hugo Lima
	 * @date 10/09/2012
	 * @param manterContaHelper
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasImovelManterParametros(ManterContaHelper manterContaHelper) throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Fernanda Paiva Data: 24/04/2006
	 * Obt�m as contas de um im�vel com ano/mes da data de vencimento menor ou
	 * igual ao ano/mes de referencia da arrecadacao corrente e com situacao
	 * atual correspondente a normal, retificada ou incluida.
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasImovel(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida, Integer situacaoRetificada,
					Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Fernanda Paiva Data: 24/04/2006
	 * Obt�m as guias de pagamento de um im�vel com ano/mes da data de
	 * vencimento menor ou igual ao ano/mes de referencia da arrecadacao
	 * corrente e com situacao atual correspondente a normal, retificada ou
	 * incluida.
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterGuiasPagamentoImovel(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0147] - Cancelar Conta Author: Raphael Rossiter Data: 23/01/2006
	 * 
	 * @param conta
	 * @param debitoCreditoSituacaoAnterior
	 * @throws ErroRepositorioException
	 */
	public void cancelarContaReferenciaContabilMenorSistemaParametro(Conta conta, Integer debitoCreditoSituacaoAnterior)
					throws ErroRepositorioException;

	/**
	 * [UC0147] - Cancelar Conta Author: Raphael Rossiter Data: 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void cancelarContaReferenciaContabilMaiorIgualSistemaParametro(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0148] - Colocar Conta Revis�o Author: Raphael Rossiter Data:
	 * 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void colocarContaRevisao(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0149] - Retirar Conta Revis�o Author: Raphael Rossiter Data:
	 * 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void retirarContaRevisao(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0151] - Alterar Vencimento Conta Author: Raphael Rossiter Data:
	 * 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void alterarVencimentoConta(Conta conta) throws ErroRepositorioException;

	public Collection<Integer> pesquisarQuadras(Integer rotaId) throws ErroRepositorioException;

	public Object pesquisarFaturamentoAtividadeCronogramaDataRealizacao(Integer faturamentoGrupoId, Integer faturamentoAtividadeId)
					throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo Faturamento Author: Leonardo Vieria, Rafael
	 * (Utilizado no obterDebitoACobrarImovel)
	 * 
	 * @author eduardo henrique
	 * @date 22/09/2008
	 *       Altera��es no [SB0003] para a v0.05
	 * @param imovelId
	 *            Id do Imovel
	 * @param debitoCreditoSituacaoAtualId
	 *            Id do Debito Credito Situa��o
	 * @param anoMesReferenciaCobrancaDebito
	 *            Refer�ncia para obter d�bitos de determinada data e anteriores
	 * @return Cole��o de Debitos a Cobrar
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarDebitosACobrar(Integer imovelId, Integer debitoCreditoSituacaoAtualId, Integer anoMesReferenciaCobrancaDebito)
					throws ErroRepositorioException;

	/**
	 * Consultar os Cr�ditos a Realizar do Imovel
	 * [UC0113] - Faturar Grupo Faturamento
	 * 
	 * @author Leonardo Vieria, Rafael Santos
	 * @data 17/01/2006
	 * @author eduardo henrique
	 * @date 22/09/2008
	 *       Altera��o no m�todo para desativa��o dos par�metros debitoCreditoSituacao e
	 *       AnoMesFaturamento para a v0.05
	 * @author Saulo Lima
	 * @date 09/03/2009
	 *       Remover par�metros inutilizados
	 * @param imovelId
	 *            Id do Imovel
	 * @return Cole��o de atributos de Creditos a Realizar
	 * @exception ErroRepositorioException
	 */
	public Collection<Object> pesquisarCreditoARealizar(Integer imovelId) throws ErroRepositorioException;

	public Collection pesquisarCreditoARealizarHistorico(Integer imovelId, int anoMesFaturamento) throws ErroRepositorioException;

	public Collection pesquisarCreditoARealizarObjeto(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Met�do respons�vel por inserir um
	 * objeto de ResumoFaturamento no sistema
	 * 
	 * @param resumoFaturamento
	 *            Objeto de resumo de faturamento que vai ser inserido
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public void inserirResumoFaturamentoAnoMesReferencia(ResumoFaturamento resumoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 01 Retorna o valor de �gua
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a localiade, a
	 * categoria e a situa��o da conta igual a normal
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoFaturamento acumularValorAguaSituacaoContaNormal(int anoMesReferencia, int localidade, int categoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 02 Retorna o valor de esgoto
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a localiade, a
	 * categoria e a situa��o da conta igual a normal
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no Hibernate
	 */
	public ResumoFaturamento acumularValorEsgotoSituacaoContaNormal(int anoMesReferencia, int localidade, int categoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 03 e 04 Retorna uma cole��o
	 * de d�bitos a cobrar por ano e m�s de refer�ncia, por ger�ncia regional,
	 * localidade e categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @param idLancamentoItemContabil
	 *            C�digo do itemde lan�amento cont�bil
	 * @return retorna a cole��o pesquisada de acordo com os par�metros
	 *         informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoNormalFinanciamentoServico(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 05 Retorna o valor de guia
	 * de pagamento acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * da conta igual a normal e o tipo de financiamento igual a servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorGuiaPagamentoSituacaoNormalFinanciamentoServico(int anoMesReferencia, int localidade, int categoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 07 e 08 Retorna uma cole��o
	 * de d�bitos a cobrar por ano e m�s de refer�ncia, por ger�ncia regional,
	 * localidade e categoria
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @param idLancamentoItemContabil
	 *            C�digo de lan�amento de item cont�bil
	 * @return retorna a cole��o pesquisada de acordo com os par�metros
	 *         informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoCanceladoFinanciamentoServico(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 09 Retorna o valor de �gua
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a cancelada
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorAguaSituacaoCancelada(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 10 Retorna o valor de esgoto
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a cancelada
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorEsgotoSituacaoCancelada(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 11 Retorna o valor de d�bito
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a cancelada e o tipo de financiamento igual a servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoTipoFinanciamentoServicoSituacaoCancelada(int anoMesReferencia, int idLocalidade, int idCategoria,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 12 Retorna o valor de d�bito
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a cancelada e o tipo de financiamento igual a parcelamento de �gua
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoTipoFinanciamentoParcelamentoAguaSituacaoCancelada(int anoMesReferencia, int idLocalidade,
					int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 13 Retorna o valor de d�bito
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a cancelada e o tipo de financiamento igual a parcelamento de
	 * esgoto
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoTipoFinanciamentoParcelamentoEsgotoSituacaoCancelada(int anoMesReferencia, int idLocalidade,
					int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 14 Retorna o valor de d�bito
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a cancelada e o tipo de financiamento igual a parcelamento de
	 * servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoTipoFinanciamentoParcelamentoServicoSituacaoCancelada(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 15 Retorna o valor de d�bito
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a cancelada e o tipo de financiamento igual a juros de parcelamento
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoTipoFinanciamentoJurosParcelamentoSituacaoCancelada(int anoMesReferencia, int idLocalidade,
					int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 16 Retorna o valor de �gua
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a inclu�da
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorAguaSituacaoIncluida(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 17 Retorna o valor de esgoto
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a inclu�da
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorEsgotoSituacaoIncluida(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 18 Retorna o valor de d�bito
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o da conta
	 * igual a inclu�da e o tipo de financiamento igual a servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoTipoFinanciamentoServicoSituacaoIncluida(int anoMesReferencia, int idLocalidade, int idCategoria,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 20 Retorna o valor de
	 * categoria de d�bito acumulado, de acordo com o ano/m�s de refer�ncia, a
	 * situa��o da conta igual a normal e o tipo de financiamento igual a
	 * servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCategoriaDebitoTipoFinanciamentoServicoSituacaoNormal(int anoMesReferencia, int idLocalidade,
					int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 21 Retorna o valor de
	 * categoria de d�bito acumulado, de acordo com o ano/m�s de refer�ncia, a
	 * situa��o da conta igual a normal e o tipo de financiamento igual a
	 * servi�o, quando o n�mero de presta��es cobradas for maior que 11(onze)
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCategoriaDebitoTipoFinanciamentoServicoSituacaoNormalNumeroPrestacoesCobradasMaiorQue11(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 22 e 23 Retorna uma cole��o
	 * de d�bito a cobrar , de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a normal e o grupo de parcelamento igual a documentos emitidos
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoNormalGrupoParcelamentoDocumentosEmitidos(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 24 e 25 Retorna uma cole��o
	 * de d�bito a cobrar , de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a normal e o grupo de parcelamento igual a financiamentos a cobrar
	 * de curto prazo
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoNormalGrupoParcelamentoFinanciamentosACobrarCurtoPrazo(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 26 e 27 Retorna uma cole��o
	 * de d�bito a cobrar de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a normal e o grupo de parcelamento igual a financiamentos a cobrar
	 * de longo prazo
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoNormalGrupoParcelamentoFinanciamentosACobrarLongoPrazo(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 28 e 29 Retorna uma cole��o
	 * de d�bito a cobrar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a normal e o grupo de parcelamento igual a parcelamentos a cobrar
	 * de curto prazo
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoNormalGrupoParcelamentoParcelamentosACobrarCurtoPrazo(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 30 e 31 Retorna uma cole��o
	 * de d�bito a cobrar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a normal e o grupo de parcelamento igual a parcelamentos a cobrar a
	 * longo prazo
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoNormalGrupoParcelamentoParcelamentosACobrarLongoPrazo(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 32 e 33 Retorna uma cole��o
	 * de d�bitos a cobrar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a normal e o grupo de parcelamento igual a juros cobrados
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoNormalGrupoParcelamentoJurosCobrados(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 34 e 35 Retorna uma cole��o
	 * de d�bito a cobrar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a cancelado por parcelamento e o grupo de parcelamento igual a
	 * juros cobrados
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoCanceladoPorParcelamentoGrupoParcelamentoJurosCobrados(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 36 Retorna o valor de d�bito
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o igual a
	 * normal e o tipo de financiamento igual a arrasto de �gua ou arrasto de
	 * esgoto ou arrasto de servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorDebitoSituacaoNormalTipoFinanciamentoArrastoAguaArrastoEsgotoArrastoServico(int anoMesReferencia,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 37 e 38 Retorna uma cole��o
	 * de d�bito a cobrar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a cancelado e o tipo de financiamento igual a parcelamento de �gua
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoCanceladoTipoFinanciamentoParcelamentoAgua(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 39 e 40 Retorna uma cole��o
	 * de d�bito a cobrar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a cancelado e o tipo de financiamento igual a parcelamento de
	 * esgoto
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoCanceladoTipoFinanciamentoParcelamentoEsgoto(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 41 e 42 Retorna uma cole��o
	 * de d�bito a cobrar acumulado, de acordo com o ano/m�s de refer�ncia, a
	 * situa��o igual a cancelado e o tipo de financiamento igual a parcelamento
	 * de servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoCanceladoTipoFinanciamentoParcelamentoServico(int anoMesReferencia,
					int idLocalidade, int idCategoria, Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 43 e 44 Retorna uma cole��o
	 * de d�bito a cobrar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * igual a cancelado e o tipo de financiamento igual a juros de parcelamento
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarSituacaoCanceladoTipoFinanciamentoJurosParcelamento(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 45 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * parcelamento de �gua
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoAguaSituacaoNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 46 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * parcelamento de esgoto
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoEsgotoSituacaoNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 47 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * parcelamento de servi�os
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoParcelamentoServicosSituacaoNormal(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 48 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * juros de parcelamento
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoJurosParcelamentoSituacaoNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 49 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * juros de parcelamento e a diferen�a de presta��es maior que 11(onze)
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoJurosParcelamentoSituacaoNormalDiferencaPrestacoesMaiorQue11(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 50 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * arrasto de �gua
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoArrastoAguaSituacaoNormal(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 51 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * arrasto de esgoto
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoArrastoEsgotoSituacaoNormal(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 52 Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a
	 * arrasto de servi�o
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoArrastoServicoSituacaoNormal(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 53 Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e a origem do cr�dito igual a
	 * contas pagas em duplicidade ou em excesso
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaOrigemCreditoContasPagasEmDuplicidadeEmExcessoSituacaoContaNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 54 Retorna o valor de
	 * categoria de cr�dito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o de conta igual a normal e a origem do cr�dito
	 * igual a devolu��o de tarifa de �gua
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaOrigemCreditoDevolucaoTarifaAguaSituacaoContaNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 55 Retorna o valor de
	 * categoria de cr�dito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o de conta igual a normal e a origem do cr�dito
	 * igual a devolu��o de tarifa de esgoto
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaOrigemCreditoDevolucaoTarifaEsgotoSituacaoContaNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 56 Retorna o valor de
	 * categoria de cr�dito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o de conta igual a normal e a origem do cr�dito
	 * igual a servi�os indiretos pagos indevidamente
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCategoriaCreditoRealizadoCategoriaOrigemCreditoServicosIndiretosPagosIndevidamenteSituacaoContaNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 57 Retorna o valor de
	 * categoria de cr�dito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o de conta igual a normal e a origem do cr�dito
	 * igual a devolu��o de juros de parcelamento
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaOrigemCreditoDevolucaoJurosParcelamentoSituacaoContaNormal(
					int anoMesReferencia, int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 59 Retorna o valor de
	 * imposto de renda acumulado, de acordo com o ano/m�s de refer�ncia, a
	 * situa��o de conta igual a normal e a categoria igual a p�blica
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorIRSituacaoContaNormalCategoriaPublica(int anoMesReferencia, Integer idLocalidade, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 60 Retorna o valor de cofins
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o de conta
	 * igual a normal e a categoria igual a p�blica
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCOFINSSituacaoContaNormalCategoriaPublica(int anoMesReferencia, Integer idLocalidade, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 61 Retorna o valor de csll
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o de conta
	 * igual a normal e a categoria igual a p�blica
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCSLLSituacaoContaNormalCategoriaPublica(int anoMesReferencia, Integer idLocalidade, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Linha 62 Retorna o valor de pis e
	 * pasep acumulado, de acordo com o ano/m�s de refer�ncia, a situa��o de
	 * conta igual a normal e a categoria igual a p�blica
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorPISPASEPSituacaoContaNormalCategoriaPublica(int anoMesReferencia, Integer idLocalidade,
					Integer idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - acumula o valor de
	 * d�bito cobrado para situa��o de conta igual a cancelada por retifica��o
	 * de acordo com o ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal pesquisarSomaValorAguaSituacaoCanceladaPorRetificacao(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - acumula o valor de
	 * d�bito cobrado para situa��o de conta igual a retificada de acordo com o
	 * ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal pesquisarSomaValorAguaSituacaoRetificada(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - acumula o valor de
	 * d�bito cobrado para situa��o de conta igual a cancelada por retifica��o
	 * de acordo com o ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @param tipoFinanciamento
	 *            Tipo de Financiamento
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal pesquisarSomaValorDebitoCobradoParcelamentoAguaSituacaoCanceladaPorRetificacao(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - acumula o valor de
	 * d�bito cobrado para situa��o de conta igual a retificada de acordo com o
	 * ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @param tipoFinanciamento
	 *            Tipo de Financiamento
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal pesquisarSomaValorDebitoCobradoParcelamentoAguaSituacaoRetificada(int anoMesReferencia, int idLocalidade,
					int idCategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - acumula o valor de guia
	 * de pagamento para situa��o de conta igual a cancelada de acordo com o
	 * ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @param tipoFinanciamento
	 *            Tipo de Financiamento
	 * @param itemContabil
	 *            Item Cont�bil
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal pesquisarSomaValorEsgotoSituacaoCanceladaPorRetificacao(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - pesquisar a soma do
	 * valor de �gua para situa��o de conta igual a cancelada por retifica��o de
	 * acordo com o ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal pesquisarSomaValorEsgotoSituacaoRetificada(int anoMesReferencia, int idLocalidade, int idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - pesquisar a soma do
	 * valor de �gua para situa��o de conta igual a retificada de acordo com o
	 * ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoCobradoPorTipoFinanciamentoSituacaoContaCanceladaPorRetificacao(int anoMesReferencia,
					int idLocalidade, int idCategoria, Integer tipoFinanciamento) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - pesquisar a soma do
	 * valor d�bito cobrado para situa��o de conta igual a cancelada por
	 * retifica��o e parcelamento de �gua de acordo com o ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoCobradoPorTipoFinanciamentoSituacaoContaRetificada(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer tipoFinanciamento) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - pesquisar a soma do
	 * valor d�bito cobrado para situa��o de conta igual a retificada e
	 * parcelamento de �gua de acordo com o ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoCobradoPorTipoFinanciamentoSituacaoContaCanceladaPorRetificacao(int anoMesReferencia,
					int idLocalidade, int idCategoria, Integer tipoFinanciamento, Integer itemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - pesquisar a soma do
	 * valor de esgoto para situa��o de conta igual a cancelada por retifica��o
	 * de acordo com o ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorDebitoCobradoPorTipoFinanciamentoSituacaoContaRetificada(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer tipoFinanciamento, Integer itemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s [SB0001] - pesquisar a soma do
	 * valor de esgoto para situa��o de conta igual a retificada de acordo com o
	 * ano/m�s de refer�ncia
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public BigDecimal acumularValorGuiaPagamentoSituacaoCancelada(int anoMesReferencia, int idLocalidade, int idCategoria,
					Integer tipoFinanciamento, Integer itemContabil) throws ErroRepositorioException;

	// item 4
	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 04 - atualizar situa��o de
	 * im�vel com faturamento finalizado
	 * 
	 * @param anoMesFaturamento
	 *            Ano e m�s de refer�ncia do faturamento
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public void atualizarImoveisSituacaoEspecialFaturamentoFinalizada(int anoMesFaturamento, Integer idSetorComercial)
					throws ErroRepositorioException;

	// item 5
	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 05 - pesquisar contas
	 * canceladas
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Conta> pesquisarContasCanceladasPorMesAnoReferenciaContabil(int anoMesReferenciaContabil, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 05 - pesquisar debitos
	 * cobrados de contas canceladas
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoCobrado> pesquisarDebitosCobradosCanceladosPorMesAnoReferenciaContabil(int anoMesReferenciaContabil,
					Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 05 - pesquisar debitos
	 * cobrados de contas canceladas
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoCobradoHistorico> pesquisarDebitosCobradosHistoricoCanceladosPorMesAnoReferenciaContabil(
					int anoMesReferenciaContabil, Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 05 - pesquisar cr�ditos
	 * realizados de contas canceladas
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<CreditoRealizado> pesquisarCreditosRealizadosCanceladosPorMesAnoReferenciaContabil(int anoMesReferenciaContabil,
					Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 05 - pesquisar cr�ditos
	 * realizados de contas canceladas historico
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<CreditoRealizadoHistorico> pesquisarCreditosRealizadosCanceladosPorMesAnoReferenciaContabilHistorico(
					int anoMesReferenciaContabil, Integer idConta) throws ErroRepositorioException;

	// fim item 5

	// item 6
	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 06 - pesquisar d�bitos a
	 * cobrar cancelados
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrar> pesquisarDebitosACobrarCanceladosPorMesAnoReferenciaContabil(int anoMesReferenciaContabil,
					Integer idSetorComercial) throws ErroRepositorioException;

	// item 7
	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 07 - pesquisar cr�ditos a
	 * realizar cancelados
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<CreditoARealizar> pesquisarCreditosARealizarCanceladosPorMesAnoReferenciaContabil(int anoMesReferenciaContabil,
					Integer idSetorComercial) throws ErroRepositorioException;

	// item 8
	/**
	 * [UC0155] - Encerrar Faturamento do M�s Item 08 - atualizar ano m�s de
	 * refer�ncia do faturamento de acordo com o ano/m�s de refer�ncia
	 * 
	 * @param anoMesFaturamentoAtual
	 *            Ano e m�s de refer�ncia do faturamento atual
	 * @param anoMesFaturamentoNovo
	 *            Ano e m�s de refer�ncia do faturamento anterior
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public void atualizarAnoMesfaturamento(int anoMesFaturamentoAtual, int anoMesFaturamentoNovo) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s Pesquisa uma cole��o de resumos
	 * defaturamento por ano e m�s de refer�ncia
	 * 
	 * @param anoMesReferenciaContabil
	 *            Ano e m�s de refer�ncia contabil
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<ResumoFaturamento> pesquisarResumoFaturamentoPorAnoMes(int anoMesFaturameto, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void retificarContaAtualizarSituacao(Conta conta, Integer situacaoAnterior) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void retificarContaAtualizarValores(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void removerContaCategoria(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void removerDebitoCobrado(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 23/01/2006
	 * 
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void removerCreditoRealizado(Conta conta) throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo Faturamento Author: Rafael Santos Data:
	 * 13/01/2006 Consultar os Debitos a Cobrar CAtegoria do Debito a Cobrar
	 * 
	 * @param debitoACobrarCategoriaID
	 *            Id do Debito a Cobrar Categoria
	 * @return Cole��o de Debitos a Cobrar Categoria
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarDebitosACobrarCategoria(Integer debitoACobrarID) throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo Faturamento Author: Rafael Santos Data:
	 * 14/01/2006 Consultar os Creditos Realizados Categoria do Credito A
	 * Realizar
	 * 
	 * @param creditoARealizarID
	 *            Id do Creditoa A Realizar
	 * @return Cole��o de Creditoa a Realizar Categoria
	 * @exception ErroRepositorioException
	 */
	public Collection pesquisarCreditoRealizarCategoria(Integer creditoARealizarID) throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * Retorna o c�digo do d�bito autom�tico.
	 * 
	 * @author Rafael Santos, Pedro Alexandre
	 * @date 16/02/2006,18/09/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterDebitoAutomatico(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0113] - Gerar Faturamento Grupo Author: Rafael Santos Data: 16/02/2006
	 * Dados do Imovel para o caso de Uso
	 * 
	 * @author eduardo henrique
	 * @date 07/01/2009
	 *       Pagina��o do hibernate desativada, por forma inadequada de execu��o para o cache do
	 *       Oracle.
	 * @param imovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImovelGrupoFaturamento(Integer idImovel, Collection<Integer> colecaoMatriculasComandoSimulacaoFaturamento)
					throws ErroRepositorioException;

	/**
	 * @author Felipe Rosacruz
	 * @date 22/04/2014
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarImoveisGrupoFaturamento(Collection<Integer> idRota,
					Collection<Integer> colecaoMatriculasComandoSimulacaoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0113] - Gerar Faturamento Grupo Author: Rafael Santos Data: 16/02/2006
	 * Dados do Cliente Imovel
	 * 
	 * @param imovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarClienteImovelGrupoFaturamento(Integer idImovel, Integer relacaoTipo) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores Author: S�vio Luiz Data:
	 * 01/02/2006
	 * retorna o objeto debito automatico movimento
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DebitoAutomaticoMovimento obterDebitoAutomaticoMovimento(Integer idImovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores Author: S�vio Luiz Data:
	 * 01/02/2006
	 * 
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param anoMesReferencia
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Integer pesquisarExistenciaContaComSituacaoAtual(Imovel imovel, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras [SF0003] - Processar
	 * Pagamento de Documento de Cobran�a Author: S�vio Luiz Data: 01/02/2006
	 * retorna o objeto debito automatico movimento
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoAtualDebitoACobrar(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * [SF0003] - Processar Pagamento de Documento de Cobran�a
	 * 
	 * @author S�vio Luiz
	 * @created 16/02/2006
	 * @param matriculaImovel
	 *            Matr�cula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarFaturaItem(Integer idCliente, Integer anoMesReferencia, Integer numeroSequencial, BigDecimal valordebito)
					throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * [SF0003] - Processar Pagamento de Documento de Cobran�a
	 * 
	 * @author Saulo Lima
	 * @created 16/12/2008
	 * @param idCliente
	 * @param numeroSequencial
	 * @param valordebito
	 * @return Collection<Object>
	 * @exception ErroRepositorioException
	 */
	public Collection<Object> pesquisarFaturaItem(Integer idCliente, Integer numeroSequencial, BigDecimal valordebito)
					throws ErroRepositorioException;

	/**
	 * Consulta os ids dos im�veis para atualizar
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author S�vio Luiz
	 * @date 17/03/2006
	 * @param situacaoEspecialFaturamentoHelper
	 * @return
	 * @throws ErroRepositorioException
	 * @date 08/04/2014
	 * @author Felipe Rosacruz
	 *         Modificado para fazer o registro das transa��es
	 */
	public Collection consultaIdImovelParaAtualizarAnoMesFaturamentoSituacaoHistorico(
					SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper) throws ErroRepositorioException;

	/**
	 * O sistema seleciona as atividades que foram previamente comandadas e
	 * ainda n�o realizadas (a partir da tabela FATURAMENTO_ATIVIDADE_CRONOGRAMA
	 * com FTCM_ID = FTCM_ID da tabela FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL e
	 * FTAC_TMCOMANDO preenchido e FTAC_TMREALIZACAO n�o preenchido ou com um
	 * valor anterior � FTAC_TMCOMANDO)
	 * 
	 * @author Raphael Rossiter
	 * @date 29/03/2006
	 * @param FaturamentoGrupoCronogramaMensal
	 * @return Collection<FaturamentoAtividadeCronograma>
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoAtividadeCronograma> pesquisarFaturamentoAtividadeCronogramaComandadaNaoRealizada(Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * Retorna o count do resultado da pesquisa de Faturamento Atividade
	 * Cronograma
	 * pesquisarFaturamentoAtividadeCronogramaCount
	 * 
	 * @author Roberta Costa
	 * @date 05/05/2006
	 * @param FaturamentoGrupoCronogramaMensal
	 *            faturamentoGrupoCronogramaMensal
	 * @param Integer
	 *            numeroPagina
	 * @return Integer retorno
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarFaturamentoAtividadeCronogramaComandadaNaoRealizadaCount() throws ErroRepositorioException;

	/**
	 * M�todo que retorna todos os im�veis que tenham cliente respons�vel e
	 * indicacao de conta a ser entregue em outro endere�o e que estejam nas
	 * quadras pertencentes �s rotas passadas
	 * UC0209 Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author Thiago Toscano
	 * @date 04/04/2006
	 * @param rotas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterImoveisPorRotasComContaEntregaEmOutroEndereco(Integer idRota) throws ErroRepositorioException;

	/**
	 * Metodo que retorno o debito Tipo do id passado
	 * Utilizado pelo [UC029] Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author thiago
	 * @date 05/04/2006
	 * @param id
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DebitoTipo getDebitoTipo(Integer id) throws ErroRepositorioException;

	/**
	 * Metodo que retorno o valor da tarifa normal a ser cobrando no caso de uso
	 * [UC029]
	 * Utilizado pelo [UC029] Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author thiago
	 * @date 05/04/2006
	 * @param anoMes
	 * @return
	 */
	public BigDecimal obterValorTarifa(Integer consumoTarifaId) throws ErroRepositorioException;

	/**
	 * Metodo que retorno o valor da tarifa social a ser cobrando no caso de uso
	 * [UC029]
	 * Utilizado pelo [UC029] Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author thiago
	 * @date 05/04/2006
	 * @param anoMes
	 * @return
	 */
	// public BigDecimal obterValorTarifaSocial(Integer anoMes)
	// throws ErroRepositorioException;

	/**
	 * Metodo que retorno o debito Tipo do id passado
	 * Utilizado pelo [UC029] Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author thiago
	 * @date 05/04/2006
	 * @param id
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] getDebitoTipoHql(Integer id) throws ErroRepositorioException;

	/**
	 * Metodo que retorna o id debito a cobrar, o id do im�vel, o id do
	 * debito tipo e o ano/m�s de refer�ncia do d�bito
	 * Utilizado pelo [UC029] Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author Rafael Corr�a e Leonardo Vieira
	 * @date 24/08/2006
	 * @param idImovel
	 *            , idDebitoTipo, anoMesReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDebitoACobrar(Integer idImovel, Integer idDebitoTipo, Integer anoMesReferenciaDebito)
					throws ErroRepositorioException;

	/**
	 * Metodo que deleta os d�bitos a cobrar categoria de um respectivo d�bito a cobrar
	 * Utilizado pelo [UC029] Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author Rafael Corr�a e Leonardo Vieira
	 * @date 24/08/2006
	 * @param idDebitoACobrar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void deletarDebitoACobrarCategoria(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * Metodo que insere ou atualiza os d�bitos a cobrar
	 * Utilizado pelo [UC029] Gerar Taxa de Entrega de Conta em Outro Endere�o.
	 * 
	 * @author Rafael Corr�a, Leonardo Vieira, Pedro Alexandre
	 * @date 24/08/2006
	 * @param colecaoDebitosACobrar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> insereOuAtualizaDebitoACobrar(Collection colecaoDebitosACobrar) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * [UC0209] Gerar Taxa de Entrega de Conta em Outro Endere�o
	 * 
	 * @author Pedro Alexandre
	 * @date 29/08/2006
	 * @param colecaoDebitosACobrarCategoria
	 * @throws ErroRepositorioException
	 */
	public void inserirDebitoACobrarCategoria(Collection colecaoDebitosACobrarCategoria) throws ErroRepositorioException;

	/**
	 * Metodo que retorna os im�veis das quadras pertencentes �s rotas
	 * Utilizado pelo [UC0302] Gerar D�bitos a Cobrar de Acr�scimos por
	 * Impontualidade
	 * 
	 * @author fernanda paiva
	 * @date 20/04/2006
	 * @param anoMes
	 * @return
	 */
	public Collection pesquisarImoveisDasQuadrasPorRota(Collection colecaoRotas) throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Fernanda Paiva Data: 25/04/2006
	 * Obt�m os pagamentos da conta que contem a menor data de pagamento
	 * 
	 * @param conta
	 * @return
	 */
	public Date obterPagamentoContasMenorData(Integer conta, Integer idImovel, Integer anoMesReferenciaConta)
					throws ErroRepositorioException;

	/**
	 * [UC0302] Gerar D�bito a Cobrar de Acrescimos por Impontualidade [SB0001]
	 * Gerar D�bito a Cobrar
	 * 
	 * @author Fernanda Paiva
	 * @created 25/04/2006
	 * @param valor
	 *            da multa/juros/atualiza��o monetaria
	 * @param ano
	 *            /mes
	 *            referencia
	 * @param tipo
	 *            debito
	 * @return
	 */
	public Object gerarDebitoACobrar(DebitoACobrar debitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Fernanda Paiva Data: 26/04/2006
	 * Obt�m os pagamentos da conta que contem a menor data de pagamento
	 * 
	 * @param conta
	 * @return
	 */
	public Object[] obterDebitoTipo(Integer debitoTipo) throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Fernanda Paiva Data: 27/04/2006
	 * Atualiza o indicador de cobranca de multa na tabela de Conta
	 * 
	 * @param conta
	 * @return
	 */

	public void atualizarIndicadorMultaDeConta(Collection<Integer> colecaoIdsContas) throws ErroRepositorioException;

	/**
	 * Atualiza o indicador de cobranca de multa na tabela de Conta
	 * 
	 * @param conta
	 * @return
	 */

	public void atualizarIndicadorMultaDeContaHistorico(Collection<Integer> colecaoIdsContasHistorico) throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Fernanda Paiva Data: 27/04/2006
	 * Atualiza o indicador de cobranca de multa na tabela de Guia Pagamento
	 * 
	 * @param guia
	 * @return
	 */

	public void atualizarIndicadorMultaDeGuiaPagamento(Collection<Integer> colecaoIdsGuiasPagamento) throws ErroRepositorioException;

	/**
	 * Seleciona os relacionamentos entre o cliente e os im�veis de acordo com o
	 * c�digo do cliente respons�vel
	 * [UC0320] Gerar Fatura de Cliente Respons�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarClienteImovelPorClienteResponsavel(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa todos os clientes respons�veis na tabela de ClienteImovel para
	 * tipo de rela��o igual a respons�vel e data de fim de rela��o iguala a
	 * nula
	 * [UC0320] Gerar Fatura de Cliente Respons�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarClientesResponsaveis(Integer numeroIndice, Integer quantidadeRegistros)
					throws ErroRepositorioException;

	/**
	 * Pesquisa a cole��o de contas para os im�veis do cliente respons�vel para
	 * o ano/m�s de refer�ncia igual ao ano/m�s de refer�ncia corrente e a
	 * situa��o da conta igual a Normal ou Retificada ou Inclu�da
	 * [UC0320] Gerar Fatura de Cliente Respons�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2006
	 * @param idsConcatenadosImoveis
	 * @param anoMesReferenciaConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> pesquisarContaImovelResponsabilidadeCliente(String idsConcatenadosImoveis, Integer anoMesReferenciaConta)
					throws ErroRepositorioException;

	/**
	 * Retorna um Object contendo um array de object com tr�s posi��es contendo
	 * na primeira posi��o a soma do valor total das contas na segunda posi��o a
	 * maior data de vencimento das contas e na terceira posi��oa maior data de
	 * validade das contas esses dados s�o necess�rios para gerar a fatura
	 * [UC0320] Gerar Fatura de Cliente Respons�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 25/04/2006
	 * @param idsConcatenadosImoveis
	 * @param anoMesReferenciaConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarResumoContasClienteResponsavel(String idsConcatenadosImoveis, Integer anoMesReferenciaConta)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os items da fatura informada com o c�digo da fatura igua ao
	 * c�digo da fatura dos items para emitir a fatura do cliente respons�vel
	 * [UC0321] Emitir Fatura de Cliente Respons�vel
	 * 
	 * @author Pedro Alexandre
	 * @date 28/04/2006
	 * @param idFatura
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturaItem> pesquisarItemsFatura(Integer idFatura) throws ErroRepositorioException;

	/**
	 * [UC0329] - Restabelecer Situa��o Anterior da Conta Author: Fernanda Paiva
	 * Date: 05/05/2006
	 * Atualiza a situacao anterior da conta de situacao atual cancelada
	 * 
	 * @param idConta
	 * @return
	 */

	public void restabelecerSituacaoAnteriorContaCancelada(String idConta) throws ErroRepositorioException;

	/**
	 * [UC0329] - Restabelecer Situa��o Anterior da Conta Author: Fernanda Paiva
	 * Date: 05/05/2006
	 * Atualiza a situacao anterior da conta de situacao atual cancelada
	 * 
	 * @param idConta
	 * @return
	 */

	public void alterarSituacaoConta(String idConta, Integer situacao) throws ErroRepositorioException;

	/**
	 * [UC0329] - Alterar as Situa��o Anterior e atual da Conta Author: Fernanda
	 * Paiva Date: 05/05/2006
	 * Atualiza a situacao anterior da conta de situacao atual da conta
	 * 
	 * @param idConta
	 *            situacaoAnterior situacaoAtual
	 * @return
	 */

	public void alterarSituacaoAnteriorAtualConta(String idConta, Integer situacaoAnterior, Integer situacaoAtual)
					throws ErroRepositorioException;

	/**
	 * Permite FAturar um conjunto de rotas de um grupo de faturamento
	 * [UC0113] - Faturar Grupo de Faturaumetno
	 * Determinar VAlores para Faturamento de �gua e/ou Esgoto
	 * [SF0002] - Determinar VAlores para Faturamento de �gua e/ou Esgoto
	 * 
	 * @author Rafael Santos
	 * @date 26/04/2006
	 * @param faturamentoGrupoId
	 * @param faturamentoAtividadeId
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarFaturamentoAtividadeCronogramaDataRealizacao(Integer faturamentoGrupoId, Integer faturamentoAtividadeId,
					Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Atualizar Debito a Cobrar Campo numero de presta��es cobradas
	 * [UC00113] - Faturar Grupo de Faturamento
	 * 
	 * @author Rafael Santos
	 * @date 03/05/2006
	 * @param idDebitoAcobrar
	 * @param prestacaoCobrada
	 * @throws ErroRepositorioException
	 */
	public void atualizarDebitoAcobrar(Integer idDebitoAcobrar, Short prestacaoCobrada, Integer anoMesReferenciaUltimaCobranca)
					throws ErroRepositorioException;

	/**
	 * Atualizar Debito a Cobrar Campo numero de presta��es cobradas
	 * [UC00113] - Faturar Grupo de Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 15/09/2006
	 * @param colecaoDebitosACobrar
	 * @throws ErroRepositorioException
	 */
	public void atualizarDebitoACobrar(List colecaoDebitosACobrar) throws ErroRepositorioException;

	/**
	 * Atualizar Credito a Realizar Campo numero de presta��es cobradas
	 * [UC00113] - Faturar Grupo de Faturamento
	 * 
	 * @author Rafael Santos
	 * @date 03/05/2006
	 * @param idDebitoAcobrar
	 * @param prestacaoCobrada
	 * @throws ErroRepositorioException
	 */
	public void atualizarCreditoARealizar(Integer idCreditoARelizar, Short numeroprestacaoRealizadas, BigDecimal valorResidualMesAnterior)
					throws ErroRepositorioException;

	/**
	 * Obtem a Ligacao Esgoto do Imovel
	 * 
	 * @author Rafael Santos
	 * @date 05/05/2006
	 * @param idImovel
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoEsgoto obterLigacaoEsgotoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Recupera as contas com estouro de consumo ou com baixo consumo [UC0348] -
	 * Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasEmitir(Collection<Integer> idTipoConta, Integer idEmpresa, Integer numeroPaginas,
					Integer anoMesReferencia, Integer idFaturamentoGrupo, Integer anoMesReferenciaFaturamentoAntecipado)
					throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * 
	 * @author Saulo Lima
	 * @date 20/10/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasEmitirTipo2(Integer idEmpresa, Integer anoMesReferencia, Integer idFaturamentoGrupo,
					Integer anoMesReferenciaFaturamentoAntecipado) throws ErroRepositorioException;

	/**
	 * Recupera as contas com debito autom�tico [UC0348] - Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasDebitoAutomatico(Collection idsContas) throws ErroRepositorioException;

	/**
	 * Recupera as contas com entrega para o cliente respons�vel [UC0348] -
	 * Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasClienteResponsavel(Collection<Integer> idTipoConta, Integer numeroPaginas, Integer anoMesReferencia,
					Integer idFaturamentoGrupo, Short indicadorEmissaoExtratoFaturamento, Integer anoMesReferenciaFaturamentoAntecipado)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas normais [UC0348] - Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasNormais(Collection idsContas) throws ErroRepositorioException;

	/**
	 * Recupera o nome do cliente usu�rio pela conta [UC0348] - Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarNomeClienteUsuarioConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Recupera o id do cliente respons�vel pela conta [UC0348] - Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 15/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdClienteResponsavelConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de categorias
	 * [UC0348] Emitir Contas
	 * [SB0007] Obter Quantidade de Economias da Conta
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short obterQuantidadeEconomiasConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0348] Emitir Contas
	 * [SB0011] Obter Quantidade de Economias da Conta
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoria(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0348] Emitir Contas
	 * [SB0011] Obter Quantidade de Economias da Conta
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaFaixas(Integer idConta, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma arrey de object com a soma do valor dos debitos
	 * cobrados de parcelamento,o numero da prestacao e o numero total de
	 * presta��es
	 * [UC0348] Emitir Contas
	 * [SB0013] Gerar Linhas dos D�bitos Cobrados
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarParmsDebitoAutomatico(Integer idConta, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma arrey de object do debito ordenado pelo tipo de
	 * debito
	 * [UC0348] Emitir Contas
	 * [SB0013] Gerar Linhas dos D�bitos Cobrados
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarParmsDebitoCobradoPorTipo(Integer idConta, Collection<Integer> tiposParcelamento) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma arrey de object do cr�dito realizado ordenado pelo
	 * tipo de cr�dito
	 * [UC0348] Emitir Contas
	 * [SB0013] Gerar Linhas dos D�bitos Cobrados
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarParmsCreditoRealizadoPorTipo(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma array de object de qualidade de agua
	 * [UC0348] Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 25/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarParmsQualidadeAgua(EmitirContaHelper emitirContaHelper) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma array de object do conta impostos deduzidos
	 * [UC0348] Emitir Contas
	 * [SB0015] Gerar Linhas dos Impostos Deduzidos
	 * 
	 * @author S�vio Luiz
	 * @date 19/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarParmsContaImpostosDeduzidos(Integer idConta) throws ErroRepositorioException;

	/**
	 * Pesquisa todas as contas para testar o batch
	 * 
	 * @author S�vio Luiz
	 * @date 02/06/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdsTodasConta() throws ErroRepositorioException;

	public Collection gerarRelacaoAcompanhamentoFaturamento(String idImovelCondominio, String idImovelPrincipal, String idNomeConta,
					String idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,

					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal,

					String idImovelPerfil, String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo,
					String idSituacaoEspecialCobranca, String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal,
					String idCadastroOcorrencia, String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial,
					String idLocalidadeFinal, String setorComercialInicial, String setorComercialFinal, String quadraInicial,
					String quadraFinal, String loteOrigem, String loteDestno, String cep, String logradouro, String bairro,
					String municipio, String idTipoMedicao, String indicadorMedicao, String idSubCategoria, String idCategoria,
					String quantidadeEconomiasInicial, String quantidadeEconomiasFinal, String diaVencimento, String idCliente,
					String idClienteTipo, String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal,
					String numeroMoradoresInicial, String numeroMoradoresFinal, String idAreaConstruidaFaixa,
					String consumoFixadoEsgotoPocoInicial, String consumoFixadoEsgotoPocoFinal

	) throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<Conta> pesquisarContasDoImovelPorMesAnoReferencia(int anoMesReferencia, String idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<ConsumoHistorico> pesquisarConsumoMedioLigacaoAgua(String idImovel, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<ConsumoHistorico> pesquisarConsumoMedioLigacaoEsgoto(String idImovel, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<ConsumoHistorico> pesquisarConsumoMesLigacaoAgua(String idImovel, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<ConsumoHistorico> pesquisarConsumoMesLigacaoEsgoto(String idImovel, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<MedicaoHistorico> pesquisarLeituraFaturadaLigacaoAgua(String idImovel, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<MedicaoHistorico> pesquisarLeituraFaturadaLigacaoEsgoto(String idImovel, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public String pesquisarAnormalidadeLeitura(Integer idAnormalidadeLeitura) throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<HidrometroInstalacaoHistorico> pesquisarDataHidrometroLigacaoAgua(String idImovel) throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<HidrometroInstalacaoHistorico> pesquisarDataHidrometroLigacaoEsgoto(String idImovel) throws ErroRepositorioException;

	/**
	 * [UC0336] - Gerar Relatorio de Acompanhamento do Faturamento
	 * 
	 * @param anoMesReferenciaContabil
	 *            idImovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<ConsumoHistorico> pesquisarConsumoFaturadoMes(String idImovel, int anoMesReferencia) throws ErroRepositorioException;

	public Collection<FaturamentoAtividadeCronograma> pesquisarRelacaoAtividadesGrupo(Integer faturamentoGrupoId)
					throws ErroRepositorioException;

	/**
	 * [UC0168] - Inserir Tarifa de Consumo Retorna a date de vig�ncia em vigor
	 * de uma tarifa de consumo Pesquisa a Data de Vigencia da Consumo Tarifa e
	 * da Consumo Tarifa Vigencia
	 * 
	 * @author Rafael Santos
	 * @since 11/07/2006
	 * @param consumoTarifa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMenorDataConsumoTarifaVigenciaEmVigor(ConsumoTarifa consumoTarifa, int idConsumoTarifaVigencia)
					throws ErroRepositorioException;

	/**
	 * [UC0168] - Inserir Tarifa de Consumo Retorna a date de vig�ncia em vigor
	 * de uma tarifa de consumo Pesquisa a Data de Vigencia da Consumo Tarifa e
	 * da Consumo Tarifa Vigencia
	 * 
	 * @author Rafael Santos
	 * @since 11/07/2006
	 * @param consumoTarifa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMaiorDataConsumoTarifaVigenciaEmVigor(ConsumoTarifa consumoTarifa, int idConsumoTarifaVigencia)
					throws ErroRepositorioException;

	/**
	 * [UC0168] - Inserir Tarifa de Consumo Retorna a date de vig�ncia em vigor
	 * de uma tarifa de consumo Pesuisar a Maior Menor data de todas as
	 * vig�ncias do Consumo Tarifa
	 * 
	 * @author Rafael Santos
	 * @since 12/07/2006
	 * @param consumoTarifa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMaiorDataConsumoTarifaVigencia(ConsumoTarifa consumoTarifa) throws ErroRepositorioException;

	/**
	 * Este caso de uso calcula a tarifa min�ma de �gua para um im�vel
	 * [UC0451] Obter Tarifa Min�ma de �gua para um Im�vel
	 * Para cada categoria e maior data de vig�ncia retorna o valor da tarifa
	 * minima
	 * pesquisarTarifaMinimaCategoriaVigencia
	 * 
	 * @author Roberta Costa
	 * @date 09/08/2006
	 * @param dataCorrente
	 * @param imovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarTarifaMinimaCategoriaVigencia(Categoria categoria, ConsumoTarifaVigencia consumoTarifaVigencia,
					Integer idSubCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso inicia um processo para o mecanismo batch
	 * [UC0111] - Iniciar Processo
	 * Este subfluxo tem o papel de iniciar um processo de faturamento
	 * comandado, neste m�todo � feita uma busca para obter as atividades
	 * comandadas e n�o realizadas
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Rodrigo Silveira
	 * @date 14/08/2006
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<FaturamentoAtividadeCronograma> pesquisarFaturamentoAtividadeCronogramaComandadasNaoRealizadas(int numeroPagina)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso inicia um processo para o mecanismo batch
	 * [UC0111] - Iniciar Processo
	 * Este subfluxo tem o papel de iniciar um processo de faturamento
	 * comandado, neste m�todo � feita uma busca para obter as atividades
	 * comandadas e n�o realizadas
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Rodrigo Silveira
	 * @date 14/08/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public int pesquisarFaturamentoAtividadeCronogramaComandadasNaoRealizadasCount() throws ErroRepositorioException;

	/**
	 * Pesquisa a existencia de uma conta pelo id da conta e pela data da ultima alteracao
	 * 
	 * @author Saulo Lima
	 * @date 31/01/2009
	 *       Altera��o no tipo do par�metro 'ultimaAlteracao' para Date
	 * @param String
	 *            Id da Conta pesquisada
	 * @param Date
	 *            Data da �ltima altera��o
	 * @return Integer
	 *         Id da Conta localizada
	 * @throws ControladorException
	 */
	@Deprecated
	public Integer pesquisarExistenciaContaParaConcorrencia(String idConta, Date ultimaAlteracao) throws ErroRepositorioException;

	/**
	 * Pesquisa a existencia de um debito tipo pelo id
	 * 
	 * @param id
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Integer verificarExistenciaDebitoTipo(Integer idDebitoTipo) throws ErroRepositorioException;

	/**
	 * Este caso de uso consultar os dados da conta
	 * 
	 * @param idConta
	 *            Id da Conta
	 * @author Fernanda Paiva
	 * @date 04/09/2006
	 * @return uma colecao de conta
	 * @throws ErroRepositorioException
	 */
	public Collection consultarConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC00113] - Faturar Grupo de Faturamento
	 * Recupera o percentual de esgoto para o im�vel informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 18/09/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterPercentualLigacaoEsgotoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * Este caso de uso permite a emiss�o de uma ou mais contas.
	 * 
	 * @author Pedro Alexandre
	 * @date 19/09/2006
	 * @param anoMesReferencia
	 * @param faturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImpressao(Integer anoMesReferencia, FaturamentoGrupo faturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * Este caso de uso permite a emiss�o de uma ou mais contas.
	 * 
	 * @author Pedro Alexandre
	 * @date 24/10/2006
	 * @param anoMesReferencia
	 * @param faturamentoGrupo
	 * @param numeroPaginas
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImpressao(Integer anoMesReferencia, FaturamentoGrupo faturamentoGrupo, Integer numeroPaginas)
					throws ErroRepositorioException;

	/**
	 * Pesquisa a soma dos valores das multas cobradas para a conta.
	 * 
	 * @author Pedro Alexandre
	 * @date 19/09/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorMultasCobradas(int idConta) throws ErroRepositorioException;

	/**
	 * Recupera os dados da conta p emitir a 2� via
	 * [UC0482]Emitir 2� Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 15/09/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0209] Gerar Taxa de Entrega de Conta em Outro Endere�o
	 * Atualiza os dados do d�bito a cobrar e a data de �ltima atualiza��o
	 * do d�bito a cobrar geral.
	 * 
	 * @author Pedro Alexandre
	 * @date 20/09/2006
	 * @param colecaoDebitosACobrar
	 * @throws ErroRepositorioException
	 */
	public void atualizaDebitoACobrar(Collection colecaoDebitosACobrar) throws ErroRepositorioException;

	/**
	 * [UC0351 - Calcular Impostos Deduzidos da Conta] Author: Rafael Santos Data: 21/09/2006
	 * 
	 * @param idImovel
	 *            Id do Im�vel
	 * @return cliente responsavel pelo imovel
	 */
	public Integer pesquisarClienteResponsavelEsferaPoderFederal(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0351 - Calcular Impostos Deduzidos da Conta] Author: Fernanda Paiva Data: 22/09/2006
	 * 
	 * @param idImpostoTipo
	 *            Id do ImpostoTipo
	 * @param anoMesReferencia
	 *            Ano M�s de Refer�ncia
	 * @return aliquotas do imposto
	 */
	public ImpostoTipoAliquota pesquisarAliquotaImposto(Integer idImpostoTipo, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Fernanda Paiva Data: 25/09/2006
	 * 
	 * @param idConta
	 * @throws ErroRepositorioException
	 */
	public void removerClientesConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0150] - Retificar Conta Author: Fernanda Paiva Data: 25/09/2006
	 * 
	 * @param idConta
	 * @throws ErroRepositorioException
	 */
	public void removerImpostosDeduzidosConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0113] Faturar Grupo de Faturamento
	 * Atualiza a data e hora da realiza��o da atividade.
	 * 
	 * @author Pedro Alexandre
	 * @date 27/09/2006
	 * @param idAtividade
	 * @param anoMesReferencia
	 * @param idFaturamentoGrupo
	 * @throws ErroRepositorioException
	 */
	public void atualizarDataHoraRealizacaoAtividade(Integer idAtividade, Integer anoMesReferencia, Integer idFaturamentoGrupo)
					throws ErroRepositorioException;

	/**
	 * [UC0113] Faturar Grupo de Faturamento
	 * Atualiza o ano/m�s de refer�ncia do faturamento para o m�s seguinte.
	 * 
	 * @author Pedro Alexandre
	 * @date 27/09/2006
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @throws ErroRepositorioException
	 */
	public void atualizarAnoMesReferenciaFaturamentoGrupo(Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Permite inserir DebitoACobrarGeral contidos numa cole��o
	 * [UC0394] Gerar D�bitos a Cobrar de Doa��es
	 * 
	 * @author C�sar Ara�jo
	 * @date 05/08/2006
	 * @param Collection
	 *            <DebitoACobrarGeral> colecaoDebitosACobrarGeral - Cole��o de DebitoACobrarGeral
	 * @throws ErroRepositorioException
	 **/
	public Integer inserirDebitoACobrarGeral(DebitoACobrarGeral debitoACobrarGeral) throws ErroRepositorioException;

	/**
	 * Permite inserir DebitoACobrar contidos numa cole��o
	 * [UC0394] Gerar D�bitos a Cobrar de Doa��es
	 * 
	 * @author C�sar Ara�jo
	 * @date 05/08/2006
	 * @param Collection
	 *            <DebitoACobrarGeral> colecaoDebitosACobrarGeral - Cole��o de DebitoACobrarGeral
	 * @throws ErroRepositorioException
	 **/
	public void inserirDebitoACobrar(DebitoACobrar debitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s - Item 05
	 * Atualiza a situa��o de im�vel com cobran�a finalizada
	 * 
	 * @author Pedro Alexandre
	 * @date 07/10/2006
	 * @param anoMesFaturamento
	 * @throws ErroRepositorioException
	 */
	public void atualizarImoveisSituacaoEspecialCobrancaFinalizada(int anoMesFaturamento, Integer idSetorComercial)
					throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os d�bitos cobrados por categoria
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param idDebitoCobrado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitoCobradoCategoria(Integer idDebitoCobrado) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os d�bitos cobrados por categoria
	 * 
	 * @author Vitor Hora
	 * @date 03/09/2008
	 * @param idDebitoCobrado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitoACobrarCategoriaHistorico(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os d�bitos cobrados por categoria
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param idDebitoCobrado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitoCobradoCategoriaHistorico(Integer idDebitoCobrado) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os credios realizados por categoria
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param idCreditoRealizado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditoRealizadoCategoria(Integer idCreditoRealizado) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os credios realizados por categoria
	 * 
	 * @author Vitor Hora
	 * @date 21/08/2008
	 * @param idCreditoRealizado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditoRealizadoCategoriaHistorico(Integer idCreditoRealizado) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os d�bitos a cobrar por categoria.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param debitoACobrar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitosACobrarCategoria(DebitoACobrar debitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os cr�ditos a realizar por categoria.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param creditoARealizar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditoARealizarCategoria(CreditoARealizar creditoARealizar) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os cr�ditos a realizar por categoria historico.
	 * 
	 * @author Vitor Hora
	 * @date 02/09/2008
	 * @param creditoARealizar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditoARealizarCategoriaHistorico(CreditoARealizarHistorico creditoARealizar)
					throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os impostos deduzidos da conta.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/10/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaImpostosDeduzidos(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os impostos deduzidos da conta.
	 * 
	 * @author Vitor Hora
	 * @date 21/08/2008
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaImpostosDeduzidosHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa as conta categoria consumo de faixa.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/10/2005
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaConsumoFaixa(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa as conta categoria consumo de faixa.
	 * 
	 * @author Vitor Hora
	 * @date 21/08/2008
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaConsumoFaixaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Para cada conta transferida para o hist�rico, atualiza o indicador
	 * de que a conta est� no hist�rico na tabela ContaGeral.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/10/2006
	 * @param idsContas
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorContaNoHistorico(Collection idsContas) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Para cada d�bito a cobrar transferido para o hist�rico, atualiza o indicador
	 * de que o d�bito a cobrar est� no hist�rico na tabela DebitoACobrarGeral.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/10/2006
	 * @param idsDebitosACobrar
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorDebitoACobrarNoHistorico(Collection idsDebitosACobrar) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Para cada cr�dito a realizar transferido para o hist�rico, atualiza o indicador
	 * de que o cr�dito a realizar est� no hist�rico na tabela CreditoARealizarGeral.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/10/2006
	 * @param idsCreditoARealizar
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorCreditoARealizarNoHistorico(Collection idsCreditoARealizar) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Verifica se todos os grupos j� foram faturados
	 * 
	 * @author Pedro Alexandre
	 * @date 07/10/2006
	 * @param anoMesReferenciaFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarFaturamentoGrupoNaoFaturados(Integer anoMesReferenciaFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os relacionamentos entre cliente e conta.
	 * 
	 * @author Pedro Alexandre
	 * @date 13/10/2005
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisa os relacionamentos entre cliente e conta.
	 * 
	 * @author Vitor Hora
	 * @date 21/08/2008
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteContaHistorico(Integer idConta) throws ErroRepositorioException;

	public void inserirDebitoAutomaticoMovimento(DebitoAutomaticoMovimento debitoAutomaticoMovimento) throws ErroRepositorioException;

	/**
	 * @author Raphael Rossiter
	 * @date 30/10/2006
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarContaRetificacao(Integer idConta) throws ErroRepositorioException;

	/**
	 * Seleciona as contaas agrupando por im�vel
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto
	 * @date 22/11/2006
	 * @param anoMesReferenciaContabil
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> obterContaAgrupadasPorImovel(int anoMesReferenciaContabil, int idLocalidade, int idQuadra)
					throws ErroRepositorioException;

	/**
	 * Utilizado pelo [UC0] Manter Conta
	 * 
	 * @author Rafael Santos
	 * @date 23/11/2006
	 * @param idConta
	 * @param dataUltimaAlteracao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Object pesquisarDataUltimaAlteracaoConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * [SB0004] - Calcular Valor de �gua e/ou Esgoto
	 * 
	 * @author S�vio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */
	public Object[] pesquisarParmsFaturamentoGrupo(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0488] Informar Retorno Ordem de Fiscaliza��o
	 * [SB0004] - Calcular Valor de �gua e/ou Esgoto
	 * 
	 * @author S�vio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataRealizacaoFaturamentoAtividadeCronograma(Integer idFaturamentoGrupo, Integer idFaturamentoAtividade,
					Integer amReferencia) throws ErroRepositorioException;

	/**
	 * Recupera o id do cliente respons�vel pela conta [UC0348] - Emitir Contas
	 * 
	 * @author S�vio Luiz
	 * @date 05/12/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarParmsClienteResponsavelConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Inserir Pagamentos
	 * Pesquisa a conta digitada
	 * 
	 * @author Rafael Corr�a
	 * @date 07/12/2006
	 * @param idImovel
	 * @param referenciaConta
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarContaDigitada(String idImovel, String referenciaConta) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento Author: Rafael Santos
	 * Data: 12/12/2006
	 * 
	 * @param idImovel
	 *            Id do Im�vel
	 * @return cliente responsavel
	 */
	public Object pesquisarClienteResponsavel(Integer idImovel) throws ErroRepositorioException;

	public Collection obterContasImovelIntervalo(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer anoMesInicio, Integer anoMesFim) throws ErroRepositorioException;

	public Collection obterContasImovelIntervalo(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer anoMesInicio, Integer anoMesFim, Integer idContaMotivoRevisao)
					throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 27/12/2006
	 * Pesquisar o Resumo Faturamento Simula��o
	 */
	public ResumoFaturamentoSimulacao pesquisarResumoFaturamentoSimulacao(ResumoFaturamentoSimulacao resumoFaturamentoSimulacao)
					throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CONTA_CATEGORIA_CONSUMO_FAIXA
	 */
	public void apagarContaCategoriaConsumoFaixa(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CONTA_CATEGORIA
	 */
	public void apagarContaCategoria(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CONTA_IMPRESSAO
	 */
	public void apagarContaImpressao(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CLIENTE_CONTA
	 */
	public void apagarClienteConta(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CONTA_IMPOSTOS_DEDUZIDOS
	 */
	public void apagarContaImpostosDeduzidos(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta DEBITO_COBRADO_CATEGORIA
	 */
	public void apagarDebitoCobradoCategoria(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta DEBITO_COBRADO
	 */
	public void apagarDebitoCobrado(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CREDITO_REALIZADO_CATEGORIA
	 */
	public void apagarCreditoRealizadoCategoria(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CREDITO_REALIZADO
	 */
	public void apagarCreditoRealizado(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Update DEBITO_A_COBRAR
	 */
	public void atualizarDebitoACobrar(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Update CREDITO_A_REALIZAR
	 */
	public void atualizarCreditoARealizar(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Update CONTA_GERAL
	 */
	public void atualizarContaGeral(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Deleta CONTA
	 */
	public void apagarConta(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Delete CONTA_GERAL
	 */
	public void apagarContaGeral(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	/**
	 * UC0113 - Faturar Grupo Faturamento
	 * Author: Rafael Santos
	 * Data: 02/01/2007
	 * Saber a quantidade de contas da rota
	 */
	public Integer quantidadeContasRota(String anoMesFaturamento, Integer idRota) throws ErroRepositorioException;

	public Collection pesquisarConsumoTarifaCategoriaPorSubCategoria(ConsumoTarifaVigencia consumoTarifaVigencia, Categoria categoria,
					Subcategoria subCategoria) throws ErroRepositorioException;

	/**
	 * [UC0320] Gerar Fatura de Cliente Respons�vel
	 * Deleta as faturas e os items da fatura por cliente respons�vel e ano/m�s de refer�ncia.
	 * 
	 * @author Pedro Alexandre
	 * @date 04/01/2007
	 * @param idCliente
	 * @param anoMesReferenciaFatura
	 * @throws ErroRepositorioException
	 */
	public void deletarFaturaClienteResponsavel(Integer idCliente, Integer anoMesReferenciaFatura, Integer anoMesReferenciaFaturaAntecipada)
					throws ErroRepositorioException;

	public void removerFaturamentoGrupoAtividades(Integer idFaturamentoGrupoMensal, Usuario usuarioLogado) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Pesquisar os ids das localidades para encerrar o faturamento do ano/m�s de
	 * refer�ncia corrente.
	 * 
	 * @author Pedro Alexandre
	 * @date 05/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadeParaEncerrarFaturamento() throws ErroRepositorioException;

	/**
	 * Pesquisar os ids das localidades para gerar o resumo das liga��es/economias.
	 * 
	 * @author Rodrigo Silveira
	 * @date 17/01/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarIdsLocalidadeParaGerarResumoLigacoesEconomias() throws ErroRepositorioException;

	/**
	 * Recupera os dados da conta p emitir a 2� via [UC0482]Emitir 2� Via de
	 * Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 08/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaERota(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0532] Gerar Relat�rio de Faturamento das Liga��es com Medi��o Individualizada
	 * 
	 * @author Vivianne Sousa
	 * @date 09/01/2007
	 * @param filtroMedicaoHistoricoSql
	 * @param anoMesfaturamentoGrupo
	 * @throws ControladorException
	 */

	public Collection pesquisarIdImovelCondominioLigacoesMedicaoIndividualizada(FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql,
					Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0532] Gerar Relat�rio de Faturamento das Liga��es com Medi��o Individualizada
	 * 
	 * @author Vivianne Sousa
	 * @date 09/01/2007
	 * @param idImovel
	 * @param anoMesfaturamentoGrupo
	 * @throws ControladorException
	 */
	public Collection pesquisarLigacoesMedicaoIndividualizadaRelatorio(Integer idImovel, String anoMesfaturamentoGrupo)
					throws ErroRepositorioException;

	/**
	 * [UC0493] Emitir de Extrato de Consumo de Im�vel Condom�nio
	 * Fl�vio Cordeiro
	 * 08/01/2007
	 * 
	 * @throws ErroRepositorioException
	 *             idsRotas string formatada com valores separados por virgula. Ex: 1,2,5,6
	 *             anoMesFaturamento
	 */

	public Collection pesquisarEmitirExtratoConsumoImovelCondominio(Collection idsRotas, String anoMesFaturamento)
					throws ErroRepositorioException;

	/**
	 * soma dos consumos dos imoveis associados
	 * [UC0493] Emitir de Extrato de Consumo de Im�vel Condom�nio
	 * Fl�vio Cordeiro
	 * 12/01/2007
	 * 
	 * @throws ErroRepositorioException
	 */
	public Integer somaConsumosImoveisAssociados(Integer idImovel, String anoMes) throws ErroRepositorioException;

	/**
	 * quantidade de imoveis associados
	 * [UC0493] Emitir de Extrato de Consumo de Im�vel Condom�nio
	 * Fl�vio Cordeiro
	 * 12/01/2007
	 * 
	 * @throws ErroRepositorioException
	 */
	public Integer quantidadeImoveisAssociados(Integer idImovel, String anoMes) throws ErroRepositorioException;

	/**
	 * [UC0173] Gerar Relat�rio de Resumo do Faturamento
	 * 
	 * @author Vivianne Sousa
	 * @created 24/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer consultarQtdeRegistrosResumoFaturamentoRelatorio(int anoMesReferencia, Integer localidade, Integer gerenciaRegional,
					String opcaoTotalizacao) throws ErroRepositorioException;

	/**
	 * @author Ana Maria
	 * @date 26/01/2007
	 * @param idConta
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection obterConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0335] Gerar Resumo de Pend�ncia
	 * Pesquisar os ids das localidade
	 * 
	 * @author Ana Maria
	 * @date 29/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidade() throws ErroRepositorioException;

	/**
	 * Retorna o Id da Localidade de uma Conta
	 * 
	 * @author Saulo Lima
	 * @date 03/05/2012
	 * @return idLocalidade
	 * @throws ErroRepositorioException
	 */
	public Integer obterIdLocalidadePorConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Retorna o Id da Localidade de uma Conta no Hist�rico
	 * 
	 * @author Saulo Lima
	 * @date 03/05/2012
	 * @return idLocalidade
	 * @throws ErroRepositorioException
	 */
	public Integer obterIdLocalidadePorContaHistorico(Integer idContaHistorico) throws ErroRepositorioException;

	/**
	 * atualiza o sequencial de conta impress�o
	 * 
	 * @author S�vio Luiz
	 * @date 29/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarSequencialContaImpressao(Map<Integer, Integer> mapAtualizaSequencial) throws ErroRepositorioException;

	/**
	 * [UC] Gerar Relat�rio de Contas Emitidas
	 * 
	 * @author Vivianne Sousa
	 * @created 30/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarContasEmitidasRelatorio(int anoMesReferencia, Integer grupoFaturamento, Collection esferaPoder)
					throws ErroRepositorioException;

	/**
	 * [UC] Gerar Relat�rio de Contas Emitidas
	 * 
	 * @author Vivianne Sousa
	 * @created 02/02/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer consultarQtdeContasEmitidasRelatorio(int anoMesReferencia, Integer grupoFaturamento, Collection esferaPoder)
					throws ErroRepositorioException;

	// retorna o anoMes do faturamento grupo do im�vel passado
	public Integer retornaAnoMesFaturamentoGrupo(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Monta a colecao de resultdos apartir da tbela conta impressao para geracao
	 * do relatorio de MAPA DE CONTROLE DAS CONTAS EMITIDAS
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 13/02/2007
	 * @param idGrupoFaturamento
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection filtrarMapaControleContaRelatorio(Integer idGrupoFaturamento, String anoMes, String indicadorFichaCompensacao)
					throws ErroRepositorioException;

	/**
	 * Monta a colecao de resultdos apartir da tabela conta impressao para geracao
	 * do relatorio de RESUMO CONTAS EMITIDAS POR LOCALIDADE NO GRUPO
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 13/02/2007
	 * @param idGrupoFaturamento
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection filtrarResumoContasLocalidade(Integer idGrupoFaturamento, String anoMes, Integer idFirma, Integer idSetorFaturamento,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Author: Vivianne Sousa Data: 06/03/2007
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterGuiasPagamentoParcelamentoItem(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Author: Vivianne Sousa Data: 06/03/2007
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterGuiasPagamentoCobrancaDocumentoItem(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Author: Vivianne Sousa Data: 06/03/2007
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterGuiasPagamentoPagamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 17/03/2007
	 * @param conta
	 * @param idImovel
	 * @param anoMesReferenciaConta
	 * @param anoMesReferenciaAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean obterIndicadorPagamentosClassificadosContaReferenciaMenorIgualAtual(Integer conta, Integer idImovel,
					Integer anoMesReferenciaConta, Integer anoMesReferenciaAtual) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 19/03/2007
	 * @param idGuiaPagamento
	 * @param idImovel
	 * @param idDebitoTipo
	 * @param anoMesReferenciaAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean obterIndicadorPagamentosClassificadosGuiaPagamentoReferenciaMenorIgualAtual(Integer idGuiaPagamento, Integer idImovel,
					Integer idDebitoTipo, Integer anoMesReferenciaAtual) throws ErroRepositorioException;

	/**
	 * Obt�m as contas de um im�vel com ano/mes da data de vencimento menor ou
	 * igual ao ano/mes de referencia da arrecadacao corrente e com situacao
	 * atual correspondente a normal, retificada ou incluida.
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * 
	 * @author Fernanda Paiva, Pedro Alexandre
	 * @date 24/04/2006,15/03/2007
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasImovel(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida, Integer situacaoRetificada,
					Date dataAnoMesReferenciaUltimoDia) throws ErroRepositorioException;

	/**
	 * [UC0544] - Gerar Arquivo Texto do Faturamento
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 23/03/2007
	 * @param anoMes
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection gerarArquivoTextoFaturamento(int anoMes, Integer idCliente) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Linha 63 Retorna o valor de categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o igual a normal e o tipo de financiamento igual a doa��es.
	 * 
	 * @param anoMesReferencia
	 *            Ano e m�s de refer�ncia do faturamento
	 * @param idLocalidade
	 *            C�digo da localidade
	 * @param idCategoria
	 *            C�digo da categoria
	 * @return retorna o valor acumulado de acordo com os par�metros informados
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection acumularValorCategoriaDebitoCobradoCategoriaTipoFinanciamentoDoacoesSituacaoNormal(int anoMesReferencia,
					int idLocalidade, int idCategoria) throws ErroRepositorioException;

	/**
	 * Recupera as contas do conjunto de imov�is
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeContasImoveis(Integer anoMes, Collection idsImovel, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim, String inContasRevisao, Integer[] motivosRevisaoDisponiveis)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas do Conjunto de Im�veis
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImoveis(Integer anoMes, Collection idsImovel, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim, String inContasRevisao, Integer[] motivosRevisaoDisponiveis)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas do Conjunto de Im�veis
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasImoveis(Integer anoMes, Collection idsImovel, Date dataVencimentoContaInicio, Date dataVencimentoContaFim,
					Integer anoMesFim) throws ErroRepositorioException;

	/**
	 * Recupera o maior valor do sequ�ncial de impress�o e soma
	 * 10 ao valor maximo retornado
	 * [UC0155] Encerrar Faturamento do M�s
	 * 
	 * @author Pedro
	 * @date 27/03/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short recuperarValorMaximoSequencialImpressaoMais10() throws ErroRepositorioException;

	/**
	 * Remove o id da conta dos pagamentos referentes a conta
	 * para poder mandar a conta para o hist�rico.
	 * [UC0000] Gerar Hist�rco para encerrar Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 01/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void apagarIdContaPagamentos(Integer idConta) throws ErroRepositorioException;

	/**
	 * Remove o id da Guia dos pagamentos referentes a Guia de Pagamento para poder mandar a Guia
	 * para o hist�rico.
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Saulo Lima
	 * @date 28/11/2008
	 * @param idGuiaPagameno
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void apagarIdGuiaPagamentoPagamentos(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Remove o id do d�bito a cobrar dos pagamentos referentes a conta
	 * para poder mandar o d�bito a cobrar para o hist�rico.
	 * [UC0000] Gerar Hist�rco para encerrar Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 01/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void apagarIdDebitoACobrarPagamentos(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * Pesquisa as contas canceladas por localidade com pagina��o
	 * [UC0000] Gerar Historico para Encerrar Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 03/04/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param numeroIndice
	 * @param quantidadeRegistros
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> pesquisarContasCanceladasPorMesAnoReferenciaContabil(int anoMesReferenciaContabil, Integer idSetorComercial,
					Integer numeroIndice, Integer quantidadeRegistros) throws ErroRepositorioException;

	/**
	 * retorna o nome do cliente usuario da conta
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 09/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String pesquisarClienteUsuarioConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0XXX] Emitir Aviso de Cobran�a
	 * 
	 * @author S�vio Luiz
	 * @date 09/04/2007
	 */
	public Object[] pesquisarAnoMesEDiaVencimentoFaturamentoGrupo(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a soma dos valores das multas cobradas para a conta.
	 * 
	 * @author S�vio Luiz
	 * @date 13/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorMultasCobradasPorFinanciamnetoTipo(int idConta, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso calcula a tarifa min�ma de �gua para um im�vel (SUBCATEGORIA)
	 * [UC0451] Obter Tarifa Min�ma de �gua para um Im�vel
	 * 
	 * @author Raphael Rossiter
	 * @date 14/04/2007
	 * @param consumoTarifaVigencia
	 * @param subcategoria
	 * @return Object
	 * @throws ErroRepositorioException
	 */
	public Object pesquisarTarifaMinimaCategoriaVigenciaPorSubcategoria(ConsumoTarifaVigencia consumoTarifaVigencia,
					Subcategoria subcategoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * pesquisar debitos cobrados de contas
	 * 
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoCobrado> pesquisarDebitosCobrados(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * pesquisar debitos cobrados de contas
	 * 
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoCobradoHistorico> pesquisarDebitosCobradosHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * pesquisar debitos cobrados de contas
	 * 
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<DebitoACobrarHistorico> pesquisarDebitosACobrarHistorico(ParcelamentoItem parcelamentoItem)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Pesquisar cr�ditos realizados de contas canceladas
	 * 
	 * @param idConta
	 *            C�digo da conta
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Collection<CreditoRealizado> pesquisarCreditosRealizados(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de A��o de Cobran�a [SB0004] Verificar Crit�rio
	 * de Cobran�a para Im�vel Pesquisa a soma dos imoveis com parcelamento.
	 * 
	 * @author S�vio Luiz
	 * @date 13/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public int pesquisarQuantidadeDebitosCobradosComParcelamento(Collection<ContaValoresHelper> colecaoContasValores,
					Collection<Integer> tiposParcelamento) throws ErroRepositorioException;

	/**
	 * Recupera os ids das contas do Conjunto de Im�veis
	 * 
	 * @author Ana Maria
	 * @date 19/04/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdContasImoveis(Integer anoMes, Collection idsImovel, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ErroRepositorioException;

	/**
	 * Recupera o debitoCreditoSituacaoAtual da Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 25/04/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarDebitoCreditoSituacaoAtualConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Recupera o debitoCreditoSituacaoAtual da Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 10/08/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarDebitoCreditoSituacaoAtualConta(Integer idImovel, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0348] Emitir Contas
	 * [SB0011] Obter Quantidade de Economias da Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 28/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaFaixas(Integer idConta, Integer idCategoria, Integer idSubCategoria)
					throws ErroRepositorioException;

	/**
	 * Recupera o id da Conta Retificada
	 * 
	 * @author Vivianne Sousa
	 * @date 27/04/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarContaRetificada(Integer idImovel, int anoMesReferenciaConta) throws ErroRepositorioException;

	/**
	 * Gera credito a realizar para os im�veis de determinados grupos
	 * BATCH PARA CORRE��O DA BASE
	 * 
	 * @author S�vio Luiz
	 * @date 02/05/2007
	 */
	public Collection pesquisarDadosImoveisParaGerarCreditoARealizar(Collection idsGrupos, Integer anoMesReferenciaConta,
					Integer anoMesReferenciaDebito) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0482]Emitir 2� Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 02/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaSubCategoria(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna as contas para impressao
	 * Pesquisar Contas Emitir Caern
	 * 
	 * @author Tiago Moreno
	 * @date 05/05/2007
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasEmitirCAERN(Integer idTipoConta, Integer idEmpresa, Integer numeroPaginas, Integer anoMesReferencia,
					Integer idFaturamentoGrupo) throws ErroRepositorioException;

	public Collection pesquisarContasEmitirOrgaoPublicoCAERN(Integer idTipoConta, Integer idEmpresa, Integer numeroPaginas,
					Integer anoMesReferencia, Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * Recupera o id da Conta Retificada
	 * 
	 * @author Vivianne Sousa
	 * @date 08/05/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarAnoMesReferenciaFaturamentoGrupo(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0394] - Gerar D�bitos a Cobrar de Doa��es
	 * Pesquisas os debitos para serem removidos
	 * 
	 * @author S�vio Luiz
	 * @date 09/05/2007
	 * @param colecaoRotas
	 *            , Integer anoMesReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitoACobrarParaRemocao(Collection colecaoRotas, Integer anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0394] - Gerar D�bitos a Cobrar de Doa��es
	 * Deleta as categorias do d�bito a cobrar
	 * 
	 * @author S�vio Luiz
	 * @date 09/05/2007
	 * @param colecaoRotas
	 *            ,
	 *            Integer anoMesReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void deletarDebitosACobrarCategoria(Collection idsDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0394] - Gerar D�bitos a Cobrar de Doa��es
	 * Deleta os debitos a cobrar e os debitos a cobrar geral para o ano e mes
	 * de faturamento. Esse caso � quando um faturamento � rodado mais de 1 vez.
	 * 
	 * @author S�vio Luiz
	 * @date 09/05/2007
	 * @param colecaoRotas
	 *            ,
	 *            Integer anoMesReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void deletarDebitosACobrar(Collection idsDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0XXX] - Gerar Relat�rio Tarifa de Consumo
	 * Pesquisas as tarifas de consumo para o relat�rio
	 * 
	 * @author Rafael Corr�a
	 * @date 11/05/2007
	 * @param descricao
	 *            ,
	 *            dataVigenciaInicial, dataVigenciaFinal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConsumoTarifaRelatorio(String descricao, Date dataVigenciaInicial, Date dataVigenciaFinal,
					String descricaoAtoAdministrativo)
					throws ErroRepositorioException;

	/**
	 * [UC0XXX] - Gerar Relat�rio de Tarifa de Consumo
	 * Pesquisas a data final de validade de uma tarifa de consumo
	 * 
	 * @author Rafael Corr�a
	 * @date 11/05/2007
	 * @param Integer
	 *            idConsumoTarifa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarDataFinalValidadeConsumoTarifa(Integer idConsumoTarifa, Date dataInicioVigencia) throws ErroRepositorioException;

	/**
	 * Recupera id de contas que est�o em revis�o por ac�o do usuario
	 * 
	 * @author Vivianne Sousa
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasEmRevisaoPorAcaoUsuario(Collection idsConta) throws ErroRepositorioException;

	/**
	 * Recupera as contas do Cliente
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeContasCliente(Integer codigoCliente, Integer relacaoTipo, Integer anoMes,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer anoMesFim, String inContasRevisao,
					Integer[] motivosRevisaoDisponiveis) throws ErroRepositorioException;

	/**
	 * Recupera as contas do Cliente
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasCliente(Integer codigoCliente, Integer relacaoTipo, Integer anoMes, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim, String inContasRevisao, Integer[] motivosRevisaoDisponiveis)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas do Cliente
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasCliente(Integer codigoCliente, Integer relacaoTipo, Integer anoMes, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ErroRepositorioException;

	/**
	 * Recupera as contas do Cliente
	 * 
	 * @author Ana Maria
	 * @date 14/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdContasCliente(Integer codigoCliente, Integer relacaoTipo, Integer anoMes, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ErroRepositorioException;

	/**
	 * Recupera id de conta(s) sem revis�o ou em revis�o por a��o do usu�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 14/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasNaoEmRevisaoOuEmRevisaoPorAcaoUsuario(Collection idsConta) throws ErroRepositorioException;

	/**
	 * Recupera os dados da conta historico p emitir a 2� via
	 * [UC0482]Emitir 2� Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * Recupera o id do cliente respons�vel pela conta historico
	 * [UC0482]Emitir 2� Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdClienteResponsavelContaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna a soma de quantidade economia
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0007] Obter Quantidade de Economias da Conta Historico
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short obterQuantidadeEconomiasContaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * Pesquisa as contas do cliente respons�vel para todos os grupos de faturamento.
	 * [UC0348] Emitir Contas
	 * 
	 * @author Pedro Alexandre
	 * @date 17/05/2007
	 * @param idTipoConta
	 * @param numeroPaginas
	 * @param anoMesReferencia
	 * @param indicadorEmissaoExtratoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasClienteResponsavel(Collection<Integer> idTipoConta, Integer numeroPaginas, Integer anoMesReferencia,
					Short indicadorEmissaoExtratoFaturamento, Integer anoMesReferenciaFaturamentoAntecipado)
					throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0011] Obter Quantidade de Economias da Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0011] Obter Quantidade de Economias da Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @param idConta
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaHistoricoFaixas(Integer idConta, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma array de object do conta impostos deduzidos
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0015] Gerar Linhas dos Impostos Deduzidos
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarParmsContaImpostosDeduzidosHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma array de object com a soma do valor dos debitos
	 * cobrados de parcelamento,o numero da prestacao e o numero total de
	 * presta��es
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0013] Gerar Linhas dos D�bitos Cobrados
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarParmsDebitoCobradoHistorico(Integer idConta, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma array de object do debito cobrado ordenado pelo
	 * tipo de debito
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0013] Gerar Linhas dos D�bitos Cobrados
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarParmsDebitoCobradoHistoricoPorTipo(Integer idConta, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma array de object do cr�dito realizado ordenado pelo
	 * tipo de cr�dito
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0014] Gerar Linhas dos Creditos Realizados
	 * 
	 * @author Vivianne Sousa
	 * @date 16/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List pesquisarParmsCreditoRealizadoHistoricoPorTipo(Integer idConta) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar um relat�rio anal�tico do faturamento.
	 * [UC0593]Gerar Relat�rio Anal�tico do Faturamento
	 * 
	 * @author Fl�vio Cordeiro
	 * @date 18/05/2007
	 * @author Saulo Lima
	 * @date 07/02/2009
	 *       Colocados os Generics nas cole��es.
	 * @author Virg�nia Melo
	 * @date 26/03/2009
	 *       Adicionado o par�metro 'valorMinimo'.
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @param indicadorLocalidadeInformatizada
	 * @param colecaoLocalidades
	 * @param colecaoSetores
	 * @param colecaoQuadras
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDadosRelatorioAnaliticoFaturamento(int anoMesFaturamento, Integer idFaturamentoGrupo,
					int indicadorLocalidadeInformatizada, Collection<Localidade> colecaoLocalidades,
					Collection<SetorComercial> colecaoSetores, Collection<Quadra> colecaoQuadras, BigDecimal valorMinimo)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar um relat�rio anal�tico do faturamento em Conta Historico.
	 * [UC0593]Gerar Relat�rio Anal�tico do Faturamento
	 * Altera��o OC0931748 obter de historico.
	 * 
	 * @date 23/01/2013
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDadosRelatorioAnaliticoFaturamentoContaHistorico(int anoMesFaturamento,
					Integer idFaturamentoGrupo, int indicadorLocalidadeInformatizada, Collection<Localidade> colecaoLocalidades,
					Collection<SetorComercial> colecaoSetores, Collection<Quadra> colecaoQuadras, BigDecimal valorMinimo)
					throws ErroRepositorioException;

	/**
	 * retorno o id do imvel com
	 * FNTP_ID da tabela DEBITO_A_COBRAR com o valor correspondente a
	 * parcelamento de �gua (2), parcelamento de esgoto (3), ou parcelamento de servi�o(4)
	 * [UC0259] - Processar Pagamento com c�digo de Barras
	 * [SB0012] � Verifica Pagamento de Debito a Cobrar de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 30/05/2007
	 * @param idDebitoACobrar
	 * @return retorna o id do imovel do debito
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public Integer pesquisarImovelDebitoACobrar(Integer idDebitoACobrar, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * atualiza DSCT_IDATUAL com o valor correspondente a cancelado (3),
	 * na tabela DEBITO_A_COBRAR com IMOV_ID do debito a cobrar que foi pago,
	 * DCST_IDATUAL com o valor correspondente a normal (0)
	 * e FNTP_ID com o valor correspondente a juros de parcelamento (8)
	 * [UC0259] - Processar Pagamento com c�digo de Barras
	 * [SB0012] � Verifica Pagamento de Debito a Cobrar de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 30/05/2007
	 * @author Saulo Lima
	 * @date 23/09/2009
	 *       Novo par�metro idParcelamento
	 * @param idimovel
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public void atualizarDebitoCreditoSituacaoAtualDoDebitoACobrar(Integer idImovel, Integer idParcelamento)
					throws ErroRepositorioException;

	/**
	 * [UC0302] - Gerar Debitos a Cobrar de Acr�scimos por Impontualidade
	 * Author: Raphael Rossiter Data: 31/05/2007
	 * Obt�m os pagamentos da conta que contem a menor data de pagamento
	 * 
	 * @param Integer
	 *            conta, Integer idImovel, Integer anoMesReferenciaConta
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] obterArrecadacaoFormaPagamentoContasMenorData(Integer conta, Integer idImovel, Integer anoMesReferenciaConta)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 31/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioEstadoPorUnidadeNegocio(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoFaturamento para a gera��o do relat�rio '[UC0173] Gerar
	 * Relat�rio de Resumo Faturamento' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 31/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoFaturamentoRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer unidadeNegocio)
					throws ErroRepositorioException;

	/**
	 * [UC0600] Emitir Histograma de �gua
	 * [SB0014] Selecionar por Indicador de Consumo
	 * [SB0015] Selecionar por Indicador de Medido
	 * [SB0016] Selecionar por Indicador de Po�o
	 * [SB0017] Selecionar por Indicador de Volume Fixo de �gua
	 * 
	 * @author Rafael Pinto
	 * @date 01/06/2007
	 * @param FiltrarEmitirHistogramaAguaHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<Object[]> pesquisarEmitirHistogramaAgua(FiltrarEmitirHistogramaAguaHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC0600] Emitir Histograma de �gua - Volume Faturado Ligacao Estimado
	 * 
	 * @author Rafael Pinto
	 * @date 01/06/2007
	 * @param FiltrarEmitirHistogramaAguaHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Integer pesquisarEmitirHistogramaAguaVolumeConsumo(FiltrarEmitirHistogramaAguaHelper filtro, Short consumo, Categoria categoria,
					Short medicao) throws ErroRepositorioException;

	/**
	 * [UC0600] Emitir Histograma de �gua - Total Geral
	 * [SB0014] Selecionar por Indicador de Consumo
	 * [SB0015] Selecionar por Indicador de Medido
	 * [SB0016] Selecionar por Indicador de Po�o
	 * [SB0017] Selecionar por Indicador de Volume Fixo de �gua
	 * 
	 * @author Rafael Pinto
	 * @date 01/06/2007
	 * @param FiltrarEmitirHistogramaAguaHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Object[] pesquisarEmitirHistogramaAguaTotalGeral(FiltrarEmitirHistogramaAguaHelper filtro, Categoria categoria)
					throws ErroRepositorioException;

	/**
	 * [UC0605] Emitir Histograma de �gua por Economia
	 * 
	 * @author Rafael Pinto
	 * @date 14/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Object[]
	 * @throws ControladorException
	 */
	public Object[] pesquisarEmitirHistogramaAguaEconomia(FiltrarEmitirHistogramaAguaEconomiaHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC0605] Emitir Histograma de �gua por Economia
	 * Monta as quebras que ser�o necessarias para o relatorio
	 * 
	 * @author Rafael Pinto
	 * @date 18/06/2007
	 * @param FiltrarEmitirHistogramaAguaEconomiaHelper
	 * @return Collection<Object[]>
	 * @throws ControladorException
	 */
	public Collection<Object[]> pesquisarEmitirHistogramaAguaEconomiaChavesAgrupadas(FiltrarEmitirHistogramaAguaEconomiaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0120] - Calcular Valores de �gua e/ou Esgoto
	 * 
	 * @author Raphael Rossiter
	 * @date 29/06/2007
	 * @param consumoTarifa
	 * @param dataFaturamento
	 * @throws ErroRepositorioException
	 */
	public ConsumoTarifaVigencia pesquisarConsumoTarifaVigenciaMenorOUIgualDataFaturamento(Integer idConsumoTarifa, Date dataFaturamento)
					throws ErroRepositorioException;

	/**
	 * Recupera o debitoCreditoSituacaoAtual da Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 21/06/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarDebitoCreditoSituacaoAtualContaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * Recupera os dados da conta p emitir a 2� via
	 * [UC0482]Emitir 2� Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 21/06/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaHistoricoERota(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0482]Emitir 2� Via de Conta
	 * [SB0011] Obter Quantidade de Economias da Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 21/06/2007
	 * @param idConta
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaHistoricoFaixas(Integer idConta, Integer idCategoria, Integer idSubCategoria)
					throws ErroRepositorioException;

	/**
	 * M�todo que retorna uma colecao de conta categoria
	 * [UC0482]Emitir 2� Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 21/06/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaHistoricoCategoriaSubCategoria(Integer idConta) throws ErroRepositorioException;

	/**
	 * Metodo que retorna a data de revis�o da conta
	 * 
	 * @author Vivianne Sousa
	 * @date 06/07/2007
	 * @param idsConta
	 * @return
	 */
	public Collection pesquisarDataRevisaoConta(Collection idsConta, Integer parametroMotivoRevisaoCobrancaBancaria)
					throws ErroRepositorioException;

	/**
	 * atualiza DSCT_IDATUAL com o valor correspondente a cancelado (3),
	 * na tabela CREDITO_A_REALIZAR com IMOV_ID do debito a cobrar que foi pago,
	 * DCST_IDATUAL com o valor correspondente a normal (0)
	 * e CROG_ID com o valor correspondente a descontos concedidos no parcelamento (6)
	 * [UC0259] - Processar Pagamento com c�digo de Barras
	 * [SB0012] � Verifica Pagamento de Debito a Cobrar de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 18/07/2007
	 * @param idimovel
	 * @return
	 * @throws ErroRepositorioException
	 *             Erro no hibernate
	 */
	public void atualizarDebitoCreditoSituacaoAtualDoCreditoARealizar(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0146] - Manter Conta Author: Raphael Rossiter Data: 21/01/2006
	 * Obt�m as contas de um im�vel que poder�o ser mantidas
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterIdsContasImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0623] - Gerar Resumo de Metas CAERN Author: S�vio Luiz Data: 20/07/2007
	 * Obt�m as contas de um im�vel que poder�o ser mantidas
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public int obterPagamentosContas(Collection idsContas) throws ErroRepositorioException;

	/**
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * verifica se a conta informada possui cliente respons�vel
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
	 * [UC0623] - Gerar Resumo de Metas CAERN Author: S�vio Luiz Data:
	 * 20/07/2007
	 * Obt�m as contas de um im�vel que poder�o ser mantidas
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarResumoMetas(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0623] - Gerar Resumo de Metas CAERN Author: S�vio Luiz Data:
	 * 20/07/2007
	 * Obt�m as contas de um im�vel que poder�o ser mantidas
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarResumoMetasAcumulado(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Recupera as contas de um grupo de faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 20/08/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeContasGrupoFaturamento(Integer anoMes, Collection colecaoGrupoFaturamento,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer anoMesFim, String inContasRevisao,
					Integer[] motivosRevisaoDisponiveis) throws ErroRepositorioException;

	/**
	 * Recupera as contas de um grupo de faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 20/08/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasGrupoFaturamento(Integer anoMes, Collection colecaoGrupoFaturamento, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim, String inContasRevisao, Integer[] motivosRevisaoDisponiveis)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas de um grupo de faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasGrupoFaturamento(Integer anoMes, String idGrupoFaturamento, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ErroRepositorioException;

	/**
	 * Recupera os ids das contas de um grupo de faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdContasGrupoFaturamento(Integer anoMes, String idGrupoFaturamento, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ErroRepositorioException;

	/**
	 * [UC0151] Alterar Vencimento Conta Author: Raphael Rossiter Data:
	 * 
	 * @autor Raphael Rossiter
	 * @data 22/08/2007
	 * @throws ErroRepositorioException
	 */
	public void alterarVencimentoContaGrupoFaturamento(Date dataVencimento, Date dataValidade, Short indicadorAlteracaoVencimento,
					Integer idGrupoFaturamento, Integer anoMes, Integer anoMesFim, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim) throws ErroRepositorioException;

	/**
	 * [UC0482]Emitir 2� Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 20/08/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarTipoConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Metodo temporario para corre��o da base de dados
	 * Gerar Cr�dito a Realizar para os im�veis com contas com vencimento em
	 * 14/08/2007 com multa da conta 06/2007 cobrada na conta 07/2007 e que
	 * pagaram em 17/07/2007
	 * 
	 * @author Pedro Alexandre
	 * @date 20/08/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosImoveisParaGerarCreditoARealizarPorImoveisComContasComVencimento14082007()
					throws ErroRepositorioException;

	/**
	 * Busca os Debitos Cobrados Agrupados pelo Tipo de Debito Para a CAERN
	 * 
	 * @author Tiago Moreno
	 * @date 29/08/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection buscarDebitosCobradosEmitirContaCaern(Conta conta) throws ErroRepositorioException;

	public Date pesquisarFaturamentoAtividadeCronogramaDataPrevista(Integer faturamentoGrupoId, Integer faturamentoAtividadeId,
					Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0216] Calcular Acrescimo por Impontualidade
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2007
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarContaAtualizacaoTarifaria(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * 
	 * @author Raphael Rossiter
	 * @date 30/09/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeFaturaPorQualificador(Short codigoQualificador) throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * 
	 * @author Raphael Rossiter
	 * @date 30/09/2007
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarFaturaPorQualificador(Short codigoQualificador, Integer anoMesReferencia, BigDecimal valorDebito)
					throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * [SF0003] - Processar Pagamento de Documento de Cobran�a
	 * 
	 * @author S�vio Luiz
	 * @created 16/02/2006
	 * @param matriculaImovel
	 *            Matr�cula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarFaturaItem(Integer idFatura) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio
	 * [UC0637] - Gerar Relat�rios Volumes Faturados
	 * 
	 * @author Rafael Corr�a
	 * @created 11/09/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDadosRelatorioVolumesFaturados(Integer idLocalidade, Integer anoMes, Integer anoMes1, Integer anoMes2,
					Integer anoMes3, Integer anoMes4, Integer anoMes5, Integer anoMes6) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio resumido
	 * [UC0637] - Gerar Relat�rios Volumes Faturados
	 * 
	 * @author Rafael Corr�a
	 * @created 13/09/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDadosRelatorioVolumesFaturadosResumido(Integer idLocalidade, Integer anoMes, Integer anoMes1,
					Integer anoMes2, Integer anoMes3, Integer anoMes4, Integer anoMes5, Integer anoMes6) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio
	 * [UC0635] - Gerar Relat�rios de Contas em Revis�o
	 * 
	 * @author Rafael Corr�a
	 * @created 20/09/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDadosRelatorioContasRevisao(Integer idGerenciaRegional, Integer idUnidadeNegocio, Integer idElo,
					Integer idLocalidadeInicial, Integer idLocalidadeFinal, Integer idMotivoRevisao, Integer idImovelPerfil,
					Integer referenciaInicial, Integer referenciaFinal) throws ErroRepositorioException;

	// Relatorio Analitico de Contas
	public Collection pesquisarDadosRelatorioAnaliticoContas(Integer idGerenciaRegional, Integer idLocalidade, Integer idCategoria,
					Integer idCliente, Integer IdImovel, Integer idSituacao, Integer motivoRetificacao, Integer referencia,
					Integer faturamentoGrupo, Integer setorComercial, Integer quadra) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio resumido
	 * [UC0635] - Gerar Relat�rios de Contas em Revis�o
	 * 
	 * @author Rafael Corr�a
	 * @created 20/09/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDadosRelatorioContasRevisaoResumido(Integer idGerenciaRegional, Integer idElo, Integer idLocalidadeInicial,
					Integer idLocalidadeFinal, Integer idMotivoRevisao, Integer idImovelPerfil, Integer referenciaInicial,
					Integer referenciaFinal) throws ErroRepositorioException;

	/**
	 * Atualiza os Clientes Respons�veis para de Conta Impressao
	 * Alteracao feita para a ordenacao das contas de clientes orgaos publicos
	 * Por Tiago Moreno - 25/08/2007
	 */

	public void atualizaClienteResponsavelOrgaoPublicoCAERN(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * @author Vivianne Sousa
	 * @date 18/09/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasAtualizacaoTarifaria(Integer idImovel, Integer inicialReferencia, Integer finalReferencia,
					Date inicialVencimento, Date finalVencimento) throws ErroRepositorioException;

	/**
	 * Pesquisa os Valores das Faixas de D�bitos
	 * 
	 * @author Ivan S�rgio
	 * @created 14/09/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDebitoFaixaValores(Integer idFaixaValor, Double valorFaixa) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * ou anterior e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idOrigemCredito
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer idOrigemCredito, Integer idSituacaoAtual, Integer idSituacaoAnterior) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idOrigemCredito
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer idOrigemCredito, Integer idSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, situa��o anterior
	 * e a origem do cr�dito informados.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idCreditoOrigem
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCredito(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer idCreditoOrigem, Integer idSituacaoAtual, Integer idSituacaoAnterior)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual e o tipo de financiamento
	 * com o ano/m�s de refer�ncia da baixa cont�bil da conta preenchido.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idFinanciamentoTipo
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamentoComBaixaContabilPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer idFinanciamentoTipo, Integer idSituacaoAtual)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual e o tipo de financiamento
	 * com o ano/m�s de refer�ncia da baixa cont�bil da conta n�o preenchido.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idFinanciamentoTipo
	 * @param idSituacaoAtual
	 * @return boa tarde
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamentoComBaixaContabilNaoPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer idFinanciamentoTipo, Integer idSituacaoAtual)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual
	 * e a origem do cr�dito informados
	 * e com o ano/m�s da baixa cont�bil preenchida.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idCreditoOrigem
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCreditoComBaixaContabilPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer idCreditoOrigem, Integer idSituacaoAtual)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual
	 * e a origem do cr�dito informados
	 * e com o ano/m�s da baixa cont�bil n�o preenchida.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idCreditoOrigem
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCreditoComBaixaContabilNaoPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer idCreditoOrigem, Integer idSituacaoAtual)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor do imposto acumulado
	 * de acordo com o ano/m�s de refer�ncia da conta, a
	 * situa��o atual da conta e o tipo de imposto
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idImpostoTipo
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorImpostoPorTipoImpostoESituacaoContaComBaixaContabilPreenchida(int anoMesReferencia,
					Integer idLocalidade, Integer idCategoria, Integer idImpostoTipo, Integer idSituacaoAtual)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor do imposto acumulado
	 * de acordo com o ano/m�s de refer�ncia da conta, a
	 * situa��o atual da conta e o tipo de imposto
	 * e com o ano/m�s da baixa cont�bil da conta n�o preenchido.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idImpostoTipo
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorImpostoPorTipoImpostoESituacaoContaComBaixaContabilNaoPreenchida(int anoMesReferencia,
					Integer idLocalidade, Integer idCategoria, Integer idImpostoTipo, Integer idSituacaoAtual)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, tipo de financiamento e
	 * pelo lan�amento item cont�bil
	 * com o ano/m�s de refer�ncia da baixa cont�bil da conta preenchido
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idFinanciamentoTipo
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ResumoFaturamento> acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamentoComBaixaContabilPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer idFinanciamentoTipo, Integer idSituacaoAtual,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, tipo de financiamento e
	 * pelo lan�amento item cont�bil
	 * com o ano/m�s de refer�ncia da baixa cont�bil da conta n�o preenchido
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idFinanciamentoTipo
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamentoComBaixaContabilNaoPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer idFinanciamentoTipo, Integer idSituacaoAtual,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, o item de financiamento cont�bil
	 * e a origem do cr�dito informados
	 * e com o ano/m�s da baixa cont�bil preenchida.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsCreditoOrigem
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCreditoComBaixaContabilPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer[] idsCreditoOrigem, Integer idSituacaoAtual,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, o item de financiamento cont�bil
	 * e a origem do cr�dito informados
	 * e com o ano/m�s da baixa cont�bil n�o preenchida.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsCreditoOrigem
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCreditoComBaixaContabilNaoPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer[] idsCreditoOrigem, Integer idSituacaoAtual,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de �guae o de esgoto
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a localiade, a
	 * categoria e a situa��o da conta igual aos ids informados
	 * com ano/m�s da baixa cont�bil preenchida
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] acumularValorAguaEsgotoPorSituacaoContaComBaixaContabilPreenchida(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer[] idsSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de �guae o de esgoto
	 * acumulado, de acordo com o ano/m�s de refer�ncia, a localiade, a
	 * categoria e a situa��o da conta igual aos ids informados
	 * com ano/m�s da baixa cont�bil n�o preenchida
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] acumularValorAguaEsgotoPorSituacaoContaComBaixaContabilNaoPreenchida(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer[] idsSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a(s) situa��o(�es) atual(ais)
	 * com o ano/m�s de refer�ncia da baixa cont�bil da conta preenchido.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorCategoriaDebitoCobradoCategoriaComBaixaContabilPreenchida(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer[] idsSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor do imposto acumulado
	 * de acordo com o ano/m�s de refer�ncia cnt�bil da conta, as
	 * situa��es atuais da conta e o tipo de imposto.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idImpostoTipo
	 * @param idsSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResumoFaturamento acumularValorImpostoPorTipoImpostoESituacaoConta(int anoMesReferencia, Integer idLocalidade,
					Integer idCategoria, Integer idImpostoTipo, Integer[] idsSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor do imposto acumulado
	 * de acordo com o ano/m�s de refer�ncia cont�bil da conta, a
	 * situa��o atual da conta e o tipo de imposto.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idImpostoTipo
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorImpostoPorTipoImpostoESituacaoAtualConta(int anoMesReferencia, Integer idLocalidade,
					Integer idCategoria, Integer idImpostoTipo, Integer idSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor do imposto acumulado
	 * de acordo com o ano/m�s de refer�ncia cont�bil da conta, a
	 * situa��o atual e anterior da conta e o tipo de imposto.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idImpostoTipo
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorImpostoPorTipoImpostoESituacaoAtualConta(int anoMesReferencia, Integer idLocalidade,
					Integer idCategoria, Integer idImpostoTipo, Integer idSituacaoAtual, Integer idSituacaoAnterior)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual,
	 * e a origem do cr�dito informados.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsCreditoOrigem
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCredito(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer[] idsCreditoOrigem, Integer idSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, a situa��o anterior
	 * e a origem do cr�dito informados.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsCreditoOrigem
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCredito(int anoMesReferencia, int idLocalidade,
					int idCategoria, Integer[] idsCreditoOrigem, Integer idSituacaoAtual, Integer idSituacaoAnterior)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, o item de lan�amento cont�bil
	 * e a origem do cr�dito informados.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsCreditoOrigem
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCreditoLancamentoItemContabil(int anoMesReferencia,
					int idLocalidade, int idCategoria, Integer[] idsCreditoOrigem, Integer idSituacaoAtual, Integer idLancamentoItemContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de credito realizado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, a situa��o anterior, item lan�amento cont�bil
	 * e a origem do cr�dito informados.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsCreditoOrigem
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCategoriaCreditoRealizadoCategoriaPorOrigemCreditoLancamentoItemContabil(int anoMesReferencia,
					int idLocalidade, int idCategoria, Integer[] idsCreditoOrigem, Integer idSituacaoAtual, Integer idSituacaoAnterior,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * ou anterior e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idsOrigemCredito
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer[] idsOrigemCredito, Integer idSituacaoAtual, Integer idSituacaoAnterior) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * ou anterior e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idsOrigemCredito
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer[] idsOrigemCredito, Integer idSituacaoAtual) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna uma cole��o
	 * de cr�dito a realizar, de acordo com o ano/m�s de refer�ncia, a situa��o
	 * atual, a situa��o anterior e origem de cr�dito informados.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsOrigemCredito
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditoARealizarPorOrigemCredito(int anoMesReferencia, int idLocalidade, int idCategoria,
					Integer[] idsOrigemCredito, Integer idSituacaoAtual, Integer idSituacaoAnterior) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de guia de devolu��o
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * e lan�amento item cont�bil.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorGuiaDevolucaoPorLancamentoItemContabil(int anoMesReferencia, Integer idLocalidade,
					Integer idSituacaoAtual, Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de guia de devolu��o
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual,
	 * a situa��o anterior e lan�amento item cont�bil.
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorGuiaDevolucaoPorLancamentoItemContabil(int anoMesReferencia, Integer idLocalidade,
					Integer idSituacaoAtual, Integer idSituacaoAnterior, Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * ou anterior e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idsOrigemCredito
	 * @param idSituacaoAtual
	 * @param categoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer[] idsOrigemCredito, Integer idSituacaoAtual, Categoria categoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idOrigemCredito
	 * @param idSituacaoAtual
	 * @param categoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer idOrigemCredito, Integer idSituacaoAtual, Categoria categoria) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * ou anterior e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idsOrigemCredito
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @param categoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer[] idsOrigemCredito, Integer idSituacaoAtual, Integer idSituacaoAnterior, Categoria categoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de cr�dito a realizar
	 * acumulado, de acordo com o ano/m�s de refer�ncia cont�bil, a situa��o atual
	 * ou anterior e a origem de cr�dito informados.
	 * 
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @param idOrigemCredito
	 * @param idSituacaoAtual
	 * @param idSituacaoAnterior
	 * @param categoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection acumularValorCreditoARealizarPorOrigemCredito(int anoMesReferenciaContabil, Integer idLocalidade,
					Integer idOrigemCredito, Integer idSituacaoAtual, Integer idSituacaoAnterior, Categoria categoria)
					throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, tipo de financiamento e
	 * pelo lan�amento item cont�bil
	 * com o ano/m�s de refer�ncia da baixa cont�bil da conta preenchido
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsFinanciamentoTipo
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ResumoFaturamento> acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamentoComBaixaContabilPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer[] idsFinanciamentoTipo, Integer idSituacaoAtual,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * [UC0155] - Encerrar Faturamento do M�s
	 * Retorna o valor de
	 * categoria de d�bito cobrado acumulado, de acordo com o ano/m�s de
	 * refer�ncia, a situa��o atual, tipo de financiamento e
	 * pelo lan�amento item cont�bil
	 * com o ano/m�s de refer�ncia da baixa cont�bil da conta n�o preenchido
	 * 
	 * @param anoMesReferencia
	 * @param idLocalidade
	 * @param idCategoria
	 * @param idsFinanciamentoTipo
	 * @param idSituacaoAtual
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ResumoFaturamento> acumularValorCategoriaDebitoCobradoCategoriaPorTipoFinanciamentoComBaixaContabilNaoPreenchida(
					int anoMesReferencia, int idLocalidade, int idCategoria, Integer[] idsFinanciamentoTipo, Integer idSituacaoAtual,
					Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio
	 * [UC0638] - Gerar Relat�rios Anormalidade Consumo
	 * 
	 * @author Rafael Corr�a
	 * @created 15/10/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDadosRelatorioAnormalidadeConsumo(Integer idGrupoFaturamento, Short codigoRota, Integer idGerenciaRegional,
					Integer idUnidadeNegocio, Integer idElo, Integer idLocalidadeInicial, Integer idLocalidadeFinal, Integer referencia,
					Integer idImovelPerfil, Integer numOcorConsecutivas, String indicadorOcorrenciasIguais, Integer mediaConsumoInicial,
					Integer mediaConsumoFinal, Integer idAnormalidadeConsumo, Integer idAnormalidadeLeitura)
					throws ErroRepositorioException;

	/**
	 * [UC0600] Emitir Histograma de Esgoto
	 * [SB0014] Selecionar por Indicador de Consumo
	 * [SB0015] Selecionar por Indicador de Medido
	 * [SB0016] Selecionar por Indicador de Po�o
	 * [SB0017] Selecionar por Indicador de Volume Fixo de �gua
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<Object[]>
	 * @throws ControladorException
	 */
	public Collection<Object[]> pesquisarEmitirHistogramaEsgoto(FiltrarEmitirHistogramaEsgotoHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC0600] Emitir Histograma de Esgoto - Total Geral
	 * [SB0014] Selecionar por Indicador de Consumo
	 * [SB0015] Selecionar por Indicador de Medido
	 * [SB0016] Selecionar por Indicador de Po�o
	 * [SB0017] Selecionar por Indicador de Volume Fixo de �gua
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Object[]
	 * @throws ControladorException
	 */
	public Object[] pesquisarEmitirHistogramaEsgotoTotalGeral(FiltrarEmitirHistogramaEsgotoHelper filtro, Categoria categoria)
					throws ErroRepositorioException;

	/**
	 * [UC0600] Emitir Histograma de Esgoto -
	 * Volume Faturado Ligacao Estimado ou Real
	 * 
	 * @author Rafael Pinto
	 * @date 05/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Integer pesquisarEmitirHistogramaEsgotoVolumeConsumo(FiltrarEmitirHistogramaEsgotoHelper filtro, Short consumo,
					Categoria categoria, Short medicao) throws ErroRepositorioException;

	/**
	 * [UC0606] Emitir Histograma de Esgoto por Economia
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Object[]
	 * @throws ControladorException
	 */
	public Object[] pesquisarEmitirHistogramaEsgotoEconomia(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0606] Emitir Histograma de Esgoto por Economia
	 * Monta as quebras que ser�o necessarias para o relatorio
	 * 
	 * @author Rafael Pinto
	 * @date 07/11/2007
	 * @param FiltrarEmitirHistogramaEsgotoEconomiaHelper
	 * @return Collection<Object[]>
	 * @throws ControladorException
	 */
	public Collection<Object[]> pesquisarEmitirHistogramaEsgotoEconomiaChavesAgrupadas(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas com estouro de consumo ou com baixo consumo [UC0348] -
	 * Emitir Contas
	 * 
	 * @author S�vio Luiz, Vivianne Sousa
	 * @date 15/05/2006, 20/11/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasNaoFichaCompensacao(Integer idTipoConta, Integer idEmpresa, Integer numeroPaginas,
					Integer anoMesReferencia, Integer idFaturamentoGrupo, Integer anoMesReferenciaFaturamentoAntecipado)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas com estouro de consumo ou com baixo consumo [UC0348] -
	 * Emitir Contas
	 * 
	 * @author S�vio Luiz, Vivianne Sousa
	 * @date 15/05/2006, 20/11/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasFichaCompensacao(Integer idTipoConta, Integer idEmpresa, Integer numeroPaginas,
					Integer anoMesReferencia, Integer idFaturamentoGrupo, Integer anoMesReferenciaFaturamentoAntecipado)
					throws ErroRepositorioException;

	/**
	 * Recupera as contas com entrega para o cliente respons�vel [UC0348] -
	 * Emitir Contas
	 * 
	 * @author S�vio Luiz, Vivianne Sousa
	 * @date 15/05/2006, 20/11/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasClienteResponsavelNaoFichaCompensacao(Integer idTipoConta, Integer numeroPaginas,
					Integer anoMesReferencia, Integer idFaturamentoGrupo, Short indicadorEmissaoExtratoFaturamento,
					Integer anoMesReferenciaFaturamentoAntecipado) throws ErroRepositorioException;

	/**
	 * Recupera as contas com entrega para o cliente respons�vel [UC0348] -
	 * Emitir Contas
	 * 
	 * @author S�vio Luiz, Vivianne Sousa
	 * @date 15/05/2006, 20/11/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasClienteResponsavelFichaCompensacao(Integer idTipoConta, Integer numeroPaginas,
					Integer anoMesReferencia, Integer idFaturamentoGrupo, Short indicadorEmissaoExtratoFaturamento,
					Integer anoMesReferenciaFaturamentoAntecipado) throws ErroRepositorioException;

	/**
	 * Pesquisa as contas do cliente respons�vel para todos os grupos de
	 * faturamento.
	 * [UC0348] Emitir Contas
	 * 
	 * @author Pedro Alexandre,Vivianne Sousa
	 * @date 17/05/2007, 20/11/2007
	 * @param idTipoConta
	 * @param numeroPaginas
	 * @param anoMesReferencia
	 * @param indicadorEmissaoExtratoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasClienteResponsavelNaoFichaCompensacao(Integer idTipoConta, Integer numeroPaginas,
					Integer anoMesReferencia, Short indicadorEmissaoExtratoFaturamento, Integer anoMesReferenciaFaturamentoAntecipado)
					throws ErroRepositorioException;

	/**
	 * Pesquisa as contas do cliente respons�vel para todos os grupos de
	 * faturamento.
	 * [UC0348] Emitir Contas
	 * 
	 * @author Pedro Alexandre,Vivianne Sousa
	 * @date 17/05/2007, 20/11/2007
	 * @param idTipoConta
	 * @param numeroPaginas
	 * @param anoMesReferencia
	 * @param indicadorEmissaoExtratoFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasClienteResponsavelFichaCompensacao(Integer idTipoConta, Integer numeroPaginas,
					Integer anoMesReferencia, Short indicadorEmissaoExtratoFaturamento, Integer anoMesReferenciaFaturamentoAntecipado)
					throws ErroRepositorioException;

	/**
	 * [UC0626] Gerar Resumo de Metas Acumulado no M�s (CAERN)
	 * 
	 * @author S�vio Luiz
	 * @data 28/11/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public Collection pesquisarIdsContasDoImovelPorMesAnoReferencia(int anoMesReferencia, Integer idImovel) throws ErroRepositorioException;

	public Boolean pesquisarExisteciaParcelamentoConta(Integer idConta, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * [UC0724] - Processar Pagamento com Ficha de Compensa��o
	 * Author: Vivianne Sousa
	 * Data: 26/11/2007
	 * 
	 * @param idConta
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Conta pesquisarExistenciaContaComSituacaoAtual(Integer idConta) throws ErroRepositorioException;

	/**
	 * atualiza o sequencial de conta impress�o
	 * e o indicador de fichaCompensa��o
	 * 
	 * @author Vivianne Sousa
	 * @date 02/12/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarSequencialContaImpressaoFichaCompensacao(Map<Integer, Integer> mapAtualizaSequencial)
					throws ErroRepositorioException;

	/**
	 * [UC0120] - Calcular Valores de �gua e/ou Esgoto CAER
	 * 
	 * @author Raphael Rossiter
	 * @param idConsumoTarifa
	 * @return ConsumoTarifaVigencia
	 * @throws ErroRepositorioException
	 */
	public ConsumoTarifaVigencia pesquisarConsumoTarifaVigenciaEmVigor(Integer idConsumoTarifa) throws ErroRepositorioException;

	/**
	 * [UC00730] Gerar Relat�rio de Im�veis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 10/01/2008
	 * @param idImovel
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarQuantidadeFaturasValorFaturas(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC00730] Gerar Relat�rio de Im�veis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 10/01/2008
	 * @param idImovel
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarReferenciaAntigaContaSemPagamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC00730] Gerar Relat�rio de Im�veis com Faturas Recentes em Dia e Faturas Antigas em Atraso
	 * 
	 * @author Rafael Pinto
	 * @date 10/01/2008
	 * @param idImovel
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarReferenciaAtualContaSemPagamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * M�todo que verifica se h� refer�ncia de Qualidade de �gua em Faturamento j� realizado
	 * [UC0597] - FS0001
	 * 
	 * @author eduardo henrique
	 * @date 17/07/2008
	 */
	public boolean verificarExistenciaFaturamentoQualidadeAgua(QualidadeAgua qualidadeAgua) throws ErroRepositorioException;

	/**
	 * M�todo que verifica se h� Registro de Atendimento, que possui tipo de d�bito vinculado com
	 * Guia de Pagamento j� cadastrada.
	 * [UC0187] - FS0008
	 * 
	 * @author eduardo henrique
	 * @date 25/07/2008
	 */
	public boolean verificarExistenciaRegistroAtendimentoGuiaPagamento(Integer idRegistroAtendimento, Integer idDebitoTipo)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os dados de Guias de Pagamento (e suas Presta��es) de um determinado Im�vel
	 * 
	 * @author eduardo henrique
	 * @date 08/08/2008
	 * @param idImovel
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiasPagamentoPrestacaoImovelOuCliente(Integer idImovel, Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa a categoria com mais economias de uma Conta
	 * 
	 * @author eduardo henrique
	 * @date 23/09/2008
	 * @param conta
	 *            - Conta de Refer�ncia para Pesquisa
	 * @return ContaCategoria com id populado.
	 * @throws ErroRepositorioException
	 */
	public ContaCategoria obterPrincipalCategoriaConta(Conta conta) throws ErroRepositorioException;

	/**
	 * Pesquisa o volume do faturamento situa��o hist�rico
	 * 
	 * @author Virg�nia Melo
	 * @param idImovel
	 *            - Id do Im�vel
	 * @param anoMesFaturamento
	 *            - Ano/M�s de Faturamento
	 * @return FaturamentoSituacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarVolumeFaturamentoSituacaoHistorico(Integer idImovel, Integer anoMesFaturamento) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por pesquisar o FaturamentoSituacaoHistorico de um Im�vel
	 * 
	 * @author Eduardo Henrique
	 * @date 06/10/2008
	 * @param idImovel
	 *            - ID do im�vel
	 * @param anoMesFaturamento
	 *            - AnoM�s de Faturamento
	 */
	public Collection<FaturamentoSituacaoHistorico> pesquisarFaturamentoSituacaoHistoricoImovel(Integer idImovel, Integer anoMesFaturamento)
					throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por pesquisar o FaturamentoSituacaoHistorico ativo de um Im�vel
	 * 
	 * @author Saulo Lima
	 * @date 24/01/2013
	 * @param idImovel
	 */
	public Collection<FaturamentoSituacaoHistorico> pesquisarFaturamentoSituacaoHistoricoAtivoImovel(Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC0088] - Registrar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 08/10/2008
	 * @param imoveisRota
	 *            (List de Im�veis que ser�o usados na consulta)
	 * @param anoMesReferencia
	 *            (Ano/M�s referente ao Faturamento)
	 * @return Collection de Contas e Id's dos MovimentoRoteiro
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasPreFaturadasImoveisFaturamentoImediato(Collection imoveis, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0088] - Registrar Faturamento Imediato
	 * M�todo respons�vel por pesquisar a quantidade de MovimentoRoteiroEmpresa .
	 * 
	 * @author Eduardo Henrique
	 * @date 14/10/2008
	 * @param anoMesFaturamento
	 *            - AnoM�s de Faturamento
	 * @param indicadorFase
	 *            - indicadorFase - Indicador de Fase do Movimento (utiliza '<> de')
	 * @param idImovel
	 *            - ID do im�vel
	 */
	public Integer pesquisarQuantidadeMovimentosRoteiroEmpresaSemProcessamento(Integer idFaturamentoGrupo, Integer anoMesFaturamento,
					Short indicadorFase) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por Inserir ou Atualizar uma colec�o de ResumosFaturamentoSimula��o.
	 * 
	 * @author Eduardo Henrique
	 * @date 15/10/2008
	 * @param colecaoResumoFaturamentoSimulacao
	 *            - cole��o de Resumos
	 */
	public void inserirOuAtualizarResumoFaturamentoSimulacao(Collection<ResumoFaturamentoSimulacao> colecaoResumoFaturamentoSimulacao)
					throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por retornar o valor de cada presta��o de uma determinada guia de
	 * pagamento.
	 * 
	 * @param idGuia
	 *            - Id da guia de pagamento
	 * @return Collection de valores das parcelas
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarValorPrestacoesGuiaPagamento(Integer idGuia) throws ErroRepositorioException;

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 03/12/2008
	 * @param imovel
	 *            (Im�vel que ser� verificado)
	 * @param anoMesReferencia
	 *            (AnoMes de Refer�ncia que ser� verificado)
	 */
	public Object pesquisarIdMovimentoRoteiroEmpresaImovel(Imovel imovel, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Recupera o anoMesReferencia baseado em filtros do FaturamentoGrupo
	 * 
	 * @author eduardo henrique
	 * @date 10/12/2008
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarAnoMesReferenciaPorIdFaturamentoGrupo(Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por retornar o D�bito A Cobrar
	 * 
	 * @author Saulo Lima
	 * @date 01/12/2008
	 * @param idDebitoACobrar
	 * @return DebitoACobrar
	 */
	public DebitoACobrar pesquisarDebitoACobrar(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por pesquisar D�bitos A Cobrar
	 * 
	 * @author Saulo Lima
	 * @date 23/08/2012
	 * @param idsDebitosACobrar
	 * @return Collection<DebitoACobrar>
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrar(Collection<Integer> idsDebitosACobrar) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * 
	 * @author eduardo henrique
	 * @date 17/12/2008
	 *       Met�do para consulta de Conta Hist�rico a partir de um Im�vel e Refer�ncia.
	 * @param imovel
	 *            Descri��o do par�metro
	 * @param anoMesReferencia
	 *            Descri��o do par�metro
	 * @return id da ContaHistorico
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public Integer pesquisarExistenciaContaHistoricoComSituacaoAtual(Imovel imovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * pesquisa debito a cobrar historico pelo id da conta historico
	 * 
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CreditoRealizadoHistorico> pesquisarCreditosRealizadosHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por pesquisar o hist�rico de instala��o hidr�metro (�gua)
	 * 
	 * @author Virg�nia Melo
	 * @date 27/02/2009
	 * @param imovel
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarMedicaoAgua(Integer idImovel, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por pesquisar o hist�rico de instala��o hidr�metro (po�o)
	 * 
	 * @author Virg�nia Melo
	 * @date 27/02/2009
	 * @param imovel
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarMedicaoPoco(Integer idImovel, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por pesquisar o n�mero do hidr�metro a partir do id do hist�rico de
	 * instala��o.
	 * 
	 * @author Virg�nia Melo
	 * @date 27/02/2009
	 * @param idHistoricoInstalacaoHidr
	 * @return
	 */
	public String pesquisarNumeroHidrometro(Integer idHistoricoInstalacaoHidr) throws ErroRepositorioException;

	/**
	 * Atualiza o indicador de cobranca de multa na tabela de Guia Pagamento Prestacao Historico
	 * 
	 * @param colecaoIdsGuiasPagamentoPrestacaoHistorico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorMultaDeGuiaPagamentoPrestacaoHistorico(
					Collection<GuiaPagamentoPrestacaoHistoricoPK> colecaoIdsGuiasPagamentoPrestacaoHistorico)
					throws ErroRepositorioException;

	/**
	 * Insere uma Conta motivo revisao
	 * 
	 * @param contaMotivoRevisao
	 * @param usuarioLogado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer inserirContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao, Usuario usuarioLogado) throws ErroRepositorioException;

	/**
	 * Atualiza uma conta motivo revisao
	 * 
	 * @param contaMotivoRevisao
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void atualizarContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao) throws ErroRepositorioException;

	/**
	 * Pesquisa de conta motivo revisao
	 * 
	 * @param contaMotivoRevisao
	 * @param tipoPesquisa
	 * @return lista de ContaMotivoRevisao
	 * @throws ControladorException
	 */
	public Collection<ContaMotivoRevisao> pesquisarContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao, String tipoPesquisa)
					throws ErroRepositorioException;

	/**
	 * Insere uma Conta motivo retificacao
	 * 
	 * @param contaMotivoRetificacao
	 * @param usuarioLogado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer inserirContaMotivoRetificacao(ContaMotivoRetificacao contaMotivoRetificacao, Usuario usuarioLogado)
					throws ErroRepositorioException;

	/**
	 * Atualiza uma conta motivo retificacao
	 * 
	 * @param contaMotivoRetificacao
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void atualizarContaMotivoRetificacao(ContaMotivoRetificacao contaMotivoRetificacao) throws ErroRepositorioException;

	/**
	 * Pesquisa de conta motivo retificacao
	 * 
	 * @param contaMotivoRetificacao
	 * @param tipoPesquisa
	 * @return lista de ContaMotivoRetificacao
	 * @throws ControladorException
	 */
	public Collection<ContaMotivoRetificacao> pesquisarContaMotivoRetificacao(ContaMotivoRetificacao contaMotivoRetificacao,
					String tipoPesquisa) throws ErroRepositorioException;

	/**
	 * Pesquisa DebitoACobrarHistorico pelo Id
	 * 
	 * @param debitoACobrarHistorico
	 * @return DebitoACobrarHistorico
	 * @throws ErroRepositorioException
	 */
	public DebitoACobrarHistorico pesquisarDebitoACobrarHistorico(DebitoACobrarHistorico debitoACobrarHistorico)
					throws ErroRepositorioException;

	/**
	 * Pesquisa um ParcelamentoItem
	 * restringindo pelo ID do DebitoACobrarHistorico
	 * e pelo ID doParcelamento.
	 * 
	 * @param debitoACobrarHistorico
	 * @param parcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ParcelamentoItem pesquisarParcelamentoItem(DebitoACobrarHistorico debitoACobrarHistorico, Parcelamento parcelamento)
					throws ErroRepositorioException;

	/**
	 * Pesquisa um DebitoACobrarGeral pelo ID
	 * 
	 * @param debitoACobrarHistorico
	 * @return DebitoACobrarGeral
	 * @throws ErroRepositorioException
	 */
	public DebitoACobrarGeral pesquisarDebitoACobrarGeral(DebitoACobrarHistorico debitoACobrarHistorico) throws ErroRepositorioException;

	/**
	 * Obt�m uma conta categoria pela contaCategoriaPK
	 * 
	 * @param contaCategoriaPK
	 * @return contaCategoria
	 * @throws ErroRepositorioException
	 */
	public ContaCategoria obterContaCategoria(ContaCategoriaPK contaCategoriaPK) throws ErroRepositorioException;

	/**
	 * M�todo que consulta se existe uma conta de refer�ncia anterior a refer�ncia de faturamento
	 * com a data de vencimento igaul ou superiro ao mes da nova conta gerada
	 * UC113-Faturar Grupo de Faturamento
	 * 
	 * @param idImovel
	 * @param anoMesReferenciaFaturamento
	 * @param anoMesVencimentoConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaContaComMesmoMesVencimento(Integer idImovel, Integer anoMesReferenciaFaturamento,
					Integer anoMesVencimentoConta) throws ErroRepositorioException;

	/**
	 * M�todo que consulta se existe uma conta de refer�ncia anterior a refer�ncia de faturamento
	 * com a data de vencimento igual ou superior ao mes da nova conta gerada e com data de
	 * vencimento igual a data de vencimento original
	 * UC113-Faturar Grupo de Faturamento
	 * 
	 * @param idImovel
	 * @param anoMesReferenciaFaturamento
	 * @param anoMesVencimentoConta
	 * @return boolean
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaContaComMesmoMesVencimentoOriginal(Integer idImovel, Integer anoMesReferenciaFaturamento,
					Integer anoMesVencimentoConta) throws ErroRepositorioException;

	/**
	 * Lista a quantidade das faturas em d�bito e o somat�rio das mesmas. Subtraindo os Impostos e
	 * os Cr�ditos
	 * 
	 * @param imovel
	 * @return List de inteiros, posicao 0 = count, posicao 1 = somatorio
	 * @throws ErroRepositorioException
	 */
	public Object[] listarSomatorioEValorFaturasDebito(Integer imovel, Date dataVencimentoLimite) throws ErroRepositorioException;

	public Pagamento consultarPagamento(Integer idPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisa as conta categoria consumo de faixa hist�rico.
	 * 
	 * @author Yara Souza
	 * @date 30/06/2010
	 * @param idConta
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaConsumoFaixaHistorico(Integer idConta, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Pesquisa as conta categoria consumo de faixa.
	 * 
	 * @author Yara Souza
	 * @date 30/06/2010
	 * @param idConta
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCategoriaConsumoFaixa(Integer idConta, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 30/06/2010
	 * @param idConta
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */

	public int pesquisarQuantidadeDebitosCobradosComParcelamentoPorConta(Collection idsContas, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	public Pagamento consultarPagamento(Pagamento pagamento) throws ErroRepositorioException;

	public void transferirContaParaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * @author Saulo Lima
	 * @date 22/08/2012
	 *       M�todo criado para chamar a Function do Banco de Dados.
	 * @param pagamento
	 */
	public Object[] gerarDebitosACobrarDeAcrescimosPorImpontualidadeBancoDeDados(Pagamento pagamento, Date dataEmissaoDocumento)
					throws ErroRepositorioException;

	/**
	 * Metodo que retorno o som�torio dos debitos cobrados.
	 * [0] - Multas,
	 * [1] - Juros
	 * [2] - Outros Servi�os
	 * 
	 * @author isilva
	 * @date 17/11/2010
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDebitosCobradosPorConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por retornar a Mensagens de Quita��o
	 * 
	 * @author Hebert Falc�o
	 * @date 02/05/2011
	 */
	public Object[] pesquisarParmsQuitacaoMensagem(Integer idQuitacaoMensagem) throws ErroRepositorioException;

	/**
	 * Retorna os SetorComercialVencimento, Ativos ou Inativos;
	 * Ordenados pelo dia de Vencimento.
	 * N�o Carrega Localidade, n�m SetorComercial
	 * 
	 * @author isilva
	 * @date 21/01/2011
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param indicadorUso
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<SetorComercialVencimento> pesquisarSetorComercialVencimentoPorLocalidadeSetorComercial(Integer idLocalidade,
					Integer idSetorComercial, Short indicadorUso) throws ErroRepositorioException;

	/**
	 * Relat�rio Ordem de Servi�o de Substitui��o de Hidr�metro
	 * Obter dados de at� duas contas em revis�o
	 * 
	 * @author Anderson Italo
	 * @date 26/05/2011
	 */
	public Collection<DadosContaEmRevisaoHelper> pesquisarDadosContasEmRevisao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0083] Gerar Dados para Leitura
	 * - Atualizar situa��o do faturamento do im�vel
	 * - Atualizar o ano/m�s de faturamento retirada
	 * 
	 * @author Hebert Falc�o
	 * @date 28/06/2011
	 */
	public void atualizarImoveisSituacaoEspecialFaturamentoFinalizadaPorGrupo(Integer anoMesFaturamento, Integer idGrupoFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC0083] - [Gerar dados para leitura] [Atualizar faturamento atividade cronograma]
	 * 
	 * @author Bruno Ferreira dos Santos
	 * @date 02/08/2011
	 */

	public void atualizarFaturamentoAtividadeCronograma(FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal)
					throws ErroRepositorioException;

	/**
	 * @autor Bruno Ferreira dos Santos
	 * @date 15/08/2011
	 * @param idTipoEspecificacaoSolicitacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterQuantidadeRAEncerradaAnoCorrentePorTipoSolicitacaoEspecificacao(Integer idTipoEspecificacaoSolicitacao,
					Integer idImovel) throws ErroRepositorioException;

	public DebitoAutomatico obterObjetoDebitoAutomatico(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * Obter os Ultimos doze meses anteriores ao ano de referencia do faturamento, com conta
	 * vencida.
	 * 
	 * @author Ailton Sousa
	 * @date 23/08/2011
	 * @param idImovel
	 * @param anoFaturamento
	 * @throws ErroRepositorioException
	 */
	public Collection obterUltimosDozeMesesAnterioresReferenciaComContaVencida(Integer idImovel, Integer anoFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * [SB0002] � Obter Dados da Qualidade �gua
	 * Retorna a entidade QualidadeAgua de acordo com o AnoMesFaturamento e com a Localidade.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public QualidadeAgua pesquisarQualidadeAguaPorLocalidadeAnoMesFaturamento(Integer anoMesFaturamento, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna a entidade QualidadeAguaPadrao.
	 * Em conversa com F�tima, ficou esclarecido que essa tabela s� vai ter uma linha de dados.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public QualidadeAguaPadrao retornarQualidadeAguaPadrao() throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 17/08/2011
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @param idRotas
	 * @throws ErroRepositorioException
	 */
	public void deletarResumoFaturamentoSimulacaoPorColecaoRotas(Integer idFaturamentoGrupo, Integer anoMesReferencia,
					Collection<Integer> idRotas) throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 17/08/2011
	 * @param idImovel
	 * @param anoMesCobrancaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrar(Integer idImovel, Integer anoMesCobrancaDebito) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public List pesquisarTodosDebitoTipos() throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna a Descri��o da ContaMensagem mais recente de acordo com o AnoMesFaturamento e com o
	 * GrupoFaturamento.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public Object[] pesquisarDescricaoContaMensagemMaisRecente(Integer anoMesFaturamento, Integer idFaturamentoGrupo)
					throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna a o Dia de Vencimento do FaturamentoGrupo de acordo com o IdFaturamentoGrupo.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public Short pesquisarDiaVencimentoPorFaturamentoGrupo(Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna a Maior Data de Vencimento da Conta de FaturamentoAtividadeCronogramaRota.
	 * 
	 * @author Ailton Sousa
	 * @date 19/08/2011
	 */
	public Date retornarMaiorDtContaVencimentoPorRotaIdFaturamentoAnoMesFaturamento(String idsRotas, Integer idFaturamentoGrupo,
					Integer anoMesFaturamento) throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 17/08/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterMaiorAnoMesReferenciaConsumoHistorico(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param referencia
	 * @param idLigacaoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer obterConsumoMedioImovel(Integer idImovel, Integer referencia, Integer idLigacaoTipo) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna os Dados Dos Grandes Conumidores.
	 * 
	 * @author Ailton Sousa
	 * @date 20/08/2011
	 */
	public Collection obterDadosGrandesConsumidores() throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna os Ids de COnsumoFaixaAreaCategoria de acordo com a categoria recebida.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 */
	public Collection obterIdsConsumoFaixaAreaCategoriaPorCategoria(Integer idCategoria) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * Obter o maior e o menor ano anterior ao ano de referencia do faturamento, com conta vencida.
	 * 
	 * @author Ailton Sousa
	 * @date 22/08/2011
	 * @param idImovel
	 * @param anoFaturamento
	 * @throws ErroRepositorioException
	 */
	public Object[] obterMaiorMenorAnoAnteriorReferenciaComContaVencida(Integer idImovel, Integer anoFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * 
	 * @author P�ricles Tavares
	 * @date 05/09/2011
	 * @param idImovel
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return Integer
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarExistenciaGuiaPamentoComSituacaoAtual(int idImovel, int idGuiaPagamento, int numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * 
	 * @author P�ricles Tavares
	 * @date 05/09/2011
	 * @param idImovel
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return Integer
	 * @exception ErroRepositorioException
	 */
	public Integer pesquisarExistenciaGuiaPamentoHistoricoComSituacaoAtual(int idImovel, int idGuiaPagamento, int numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * 
	 * @date 06/09/2011
	 * @author P�ricles Tavares
	 * @param imovel
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return retorna o objeto debito automatico movimento
	 * @throws ErroRepositorioException
	 */
	public DebitoAutomaticoMovimento obterDebitoAutomaticoMovimentoGuiaPagamento(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * [SB0027] � Gerar Linhas dos D�bitos Cobrados
	 * Obt�m uma cole��o com os d�bitos cobrados de uma conta
	 * 
	 * @author Anderson Italo
	 * @date 26/08/2011
	 * @param idConta
	 *            , idsFinanciamentoTipo
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoCobrado> buscarDebitosCobradosContaEmissaoTxt(Integer idConta, String idsFinanciamentoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * [SB0027] � Gerar Linhas dos D�bitos Cobrados
	 * Obt�m a soma dos d�bitos cobrados de parcelamentos de uma conta
	 * 
	 * @author Anderson Italo
	 * @date 26/08/2011
	 * @param idConta
	 *            , idsFinanciamentoTipo
	 * @throws ErroRepositorioException
	 */
	public BigDecimal somarDebitosCobradosContaEmissaoTxt(Integer idConta, String idsFinanciamentoTipo) throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * [SB0028] � Gerar Linhas dos Cr�ditos Realizados
	 * Obt�m uma cole��o com os cr�ditos realizados de uma conta
	 * 
	 * @author Anderson Italo
	 * @date 26/08/2011
	 * @param idConta
	 * @throws ErroRepositorioException
	 */
	public Collection<CreditoRealizado> buscarCreditosRealizadosContaEmissaoTxt(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * Obt�m o fone da localidade ou elo informado como parametro
	 * 
	 * @author Anderson Italo
	 * @date 27/08/2011
	 */
	public String retornarFoneLocalidade(Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Contas
	 * Obter os Ultimos cinco anos anteriores ao ano de referencia do faturamento, com conta
	 * vencida.
	 * 
	 * @author Anderson Italo
	 * @date 29/08/2011
	 * @param idImovel
	 * @param anoFaturamento
	 * @throws ErroRepositorioException
	 */
	public Collection obterUltimosCincoAnosAnterioresReferenciaComContaVencida(Integer idImovel, Integer anoFaturamento)
					throws ErroRepositorioException;

	/**
	 * Retorna as categorias dos d�bitos cobrados hist�ricos da conta historico informada.
	 * 
	 * @param conta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection buscarDebitoCobradoCategoriaHistoricoPorIdConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Retorna as categorias dos d�bitos cobrados da conta informada.
	 * 
	 * @param conta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection buscarDebitoCobradoCategoriaPorIdConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * @author Isaac Silva
	 * @date 15/09/2011
	 * @param codigoSetorComercial
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoGrupo> pesquisarFaturamentoGrupoPorCodigoSetorComercialELocalidade(Integer codigoSetorComercial,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Verifica se a ROTA informada est� em
	 * (SETOR COMERCIAL, FATURAMENTO_GRUPO)
	 * 
	 * @author Isaac Silva
	 * @date 15/09/2011
	 * @param codigoSetorComercial
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean existeVinculoRotaSetorComercialFaturamentoGrupo(Quadra quadra) throws ErroRepositorioException;

	/**
	 * Retorna o Objeto FaturamentoGrupo pelo ID pesquisado.
	 * 
	 * @author Ailton Sousa
	 * @date 04/10/2011
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public FaturamentoGrupo pesquisarFaturamentoGrupoPorID(Integer idFaturamentoGrupo) throws ErroRepositorioException;

	/**
	 * [UC0254] - Efetuar An�lise do Movimento dos Arrecadadores
	 * obtem imovel, localidade e conta atraves do id da conta
	 * 
	 * @author Vivianne Sousa
	 * @date 29/01/2008
	 * @param idConta
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Conta obterImovelLocalidadeConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0254] - Efetuar An�lise do Movimento dos Arrecadadores
	 * obtem imovel, localidade e contaHistorico atraves do id da conta historico
	 * 
	 * @author Vivianne Sousa
	 * @date 29/01/2008
	 * @param idConta
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public ContaHistorico obterImovelLocalidadeContaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param idDebitoTipo
	 * @param anoMesReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String[] pesquisarDebitoACobrarPorDebitoTipo(Integer idImovel, Integer idDebitoTipo, Integer anoMesReferenciaDebito)
					throws ErroRepositorioException;

	/**
	 * [UCXXXX] - Consultar contas para provis�o de devedores duvidosos
	 * obtem imovel, localidade e conta atraves do id da conta
	 * 
	 * @author Genival Barbosa
	 * @date 06/12/2011
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> consultarContasProvisaoDevedoresDuvidosos() throws ErroRepositorioException;

	/**
	 * [UC3027] Filtrar Guia de Pagamento
	 * 
	 * @author Anderson Italo
	 * @date 27/10/2011
	 */
	public Collection pesquisarRegistrosManterGuiaPagamento(FiltroGuiaPagamentoHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC3027] Filtrar Guia de Pagamento
	 * Obter total de registros retornados na consulta
	 * 
	 * @author Anderson Italo
	 * @date 27/10/2011
	 */
	public Integer pesquisarTotalRegistrosManterGuiaPagamento(FiltroGuiaPagamentoHelper filtro) throws ErroRepositorioException;

	/**
	 * [UC0188] Manter Guia de Pagamento
	 * M�todo que obt�m o somat�rio de PGHI_VLPAGAMENTO da tabela PAGAMENTO_HISTORICO com
	 * GPAG_ID=GPAG_ID da tabela
	 * GUIA_PAGAMENTO_HISTORICO, caso exista pagamento para a guia
	 * 
	 * @author Anderson Italo
	 * @date 28/10/2011
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterTotalPagamentosPorGuiaPagamento(Integer idGuiaPagamentoGeral) throws ErroRepositorioException;

	/**
	 * [UC0188] Manter Guia de Pagamento
	 * M�todo que obt�m a maior presta��o paga da guia (PGHI_NNPRESTACAO) PAGAMENTO_HISTORICO com
	 * GPAG_ID=GPAG_ID da
	 * tabela GUIA_PAGAMENTO_HISTORICO, caso exista pagamento para a guia)
	 * 
	 * @author Anderson Italo
	 * @date 28/10/2011
	 * @throws ErroRepositorioException
	 */
	public List<PagamentoHistorico> obterTotalPrestacoesPagas(Integer idGuiaPagamentoGeral) throws ErroRepositorioException;

	/**
	 * Pesquisar Presta��es de Guia de Pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 20/12/2011
	 */
	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoFiltrar(Integer guiaPagamentoId, int nuConsulta)
					throws ErroRepositorioException;

	/**
	 * Pesquisar Presta��es de Guia de Pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 05/01/2012
	 */
	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoRelatorio(Integer guiaPagamentoId)
					throws ErroRepositorioException;

	/**
	 * Pesquisar tipos de financiamento de d�bitos que n�o permitam o cancelamento de uma guia de
	 * pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 02/01/2012
	 */
	public Collection<String> pesquisarTipoFinanciamentoDebitoNaoPermiteCancelarGuiaPagamento(Integer guiaPagamentoId)
					throws ErroRepositorioException;

	/**
	 * Pesquisar parcelamentos de cobranca bancarias correspondentes a uma guia de pagamento
	 * pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 02/01/2012
	 */
	public Collection<String> pesquisarBoletoEmissaoGuiaPagamento(Integer guiaPagamentoId) throws ErroRepositorioException;

	/**
	 * Pesquisar boletos bancarios j� pagos de guia de pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 05/01/2012
	 */
	public Collection<Object[]> pesquisarBoletoGeradoGuiaPagamento(Integer guiaPagamentoId, Short numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC3034] Gerar Multa por Descumprimento de Parcelamento
	 * Selecionar as RDs que possuem multa por descumprimento com valor maior que zero
	 * 
	 * @author Anderson Italo
	 * @date 04/02/2012
	 * @throws ErroRepositorioException
	 */
	public Collection<ParcelamentoPerfil> selecionarResolucaoDiretoriaComMultaDescumprimentoMaiorQueZero() throws ErroRepositorioException;

	/**
	 * [UC3034] Gerar Multa por Descumprimento de Parcelamento
	 * Consultar os parcelamentos ativos realizados com o perfil informado e com forma de cobran�a
	 * em
	 * conta e com presta��es a serem cobradas
	 * 
	 * @author Anderson Italo
	 * @date 04/02/2012
	 * @throws ErroRepositorioException
	 */
	public Collection<Parcelamento> consultarParcelamentoAtivosRealizadosPorPerfil(Integer idPerfilParcelamento, Integer idFaturamentoGrup)
					throws ErroRepositorioException;

	/**
	 * [UC3034] Gerar Multa por Descumprimento de Parcelamento
	 * [SB001] � Selecionar d�bitos cobrados vencidos
	 * Selecionar os d�bitos cobrados do tipo de financiamento de parcelamento j� vencidos
	 * associados ao parcelamento ordenados pelo n�mero da presta��o
	 * 
	 * @author Anderson Italo
	 * @date 04/02/2012
	 * @throws ErroRepositorioException
	 */
	public List<DebitoCobrado> selecionarDebitosCobradosVencidos(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * [UC3034] Gerar Multa por Descumprimento de Parcelamento
	 * Obter D�bitos a Cobrar relativo a parcelamento de conta para o ano/m�s de refer�ncia
	 * informados
	 * 
	 * @author Anderson Italo
	 * @date 04/02/2012
	 * @throws ErroRepositorioException
	 */
	public DebitoACobrar obterDebitoParcelamentoPorAnoMesSelecionado(Integer idParcelamento, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC3034] Gerar Multa por Descumprimento de Parcelamento
	 * Obter D�bitos a Cobrar relativos a parcelamento de contas para gerar a multa por
	 * descumprimento
	 * 
	 * @author Anderson Italo
	 * @date 04/02/2012
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> obterDebitoParcelamentoParaGerarMultaPorDescumprimento(Integer idParcelamento)
					throws ErroRepositorioException;

	/**
	 * [UC0111] Iniciar Processo
	 * [SB0006] � Obter Dados Complementares de Comando de Faturamento
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosComplementaresComandoFaturamento(Integer idComandoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * [SB0016] � Processar Pagamento Legado DESO
	 * 
	 * @author Anderson Italo
	 * @date 09/03/2012
	 */
	public Integer pesquisarExistenciaContaComSituacaoAtualLegadoDESO(Imovel imovel, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * [SB0016] � Processar Pagamento Legado DESO
	 * 
	 * @author Anderson Italo
	 * @date 09/03/2012
	 */
	public Integer pesquisarExistenciaContaHistoricoComSituacaoAtualLegadoDESO(Imovel imovel, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC3037] Filtrar Contas Pr�-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 10/02/2012
	 *          Exibir Contas Pr�-Faturadas.
	 */
	public Collection pesquisarContasPreFaturadas(FaturaContasPreFaturadasHelper faturaContasPreFaturadasHelper, Integer pageOffset,
					boolean indicadorRelatorio) throws ErroRepositorioException;

	/**
	 * [UC3035] Concluir Faturamento Contas Pr�-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 16/02/2012
	 */
	public Conta pesquisarContaPeloID(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC3037] Filtrar Contas Pr�-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 24/02/2012
	 *          Exibir Contas Pr�-Faturadas.
	 */
	public Collection<Integer> pesquisarQuantidadeContasPreFaturadas(FaturaContasPreFaturadasHelper faturaContasPreFaturadasHelper,
					Short permiteFaturarContaPreFaturadaZero) throws ErroRepositorioException;

	/**
	 * [UC3037] Filtrar Contas Pr�-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 12/03/2012
	 *          Exibir Contas Pr�-Faturadas.
	 */
	public Integer obterMaiorAnoMesReferenciaAnteriorMedicaoHistorico(Integer idImovel, Integer anoMesConta)
					throws ErroRepositorioException;

	/**
	 * [UC3037] Filtrar Contas Pr�-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 12/03/2012
	 *          Exibir Contas Pr�-Faturadas.
	 */
	public Integer obterMaiorAnoMesReferenciaAnteriorConsumoHistorico(Integer idImovel, Integer anoMesConta)
					throws ErroRepositorioException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [FS0005] - Verificar exist�ncia da conta
	 * 
	 * @author Anderson Italo
	 * @date 02/08/2011
	 * @return Object
	 * @throws ErroRepositorioException
	 * @exception ErroRepositorioException
	 */
	public Object verificarExistenciaConta(Integer imovelId, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC3055] Encerrar Faturamento
	 * [SB0001] � Selecionar Grupos de Faturamento por Situa��o
	 * 
	 * @author Hebert Falc�o
	 * @date 01/04/2012
	 */
	public Collection<Object[]> selecionarGruposFaturamentoPorSituacao(Integer referencia, Short situacao) throws ErroRepositorioException;

	/**
	 * [UC3055] Encerrar Faturamento
	 * Pesquisar Grupos N�o Faturados
	 * 
	 * @author Hebert Falc�o
	 * @date 01/04/2012
	 */
	public Collection<FaturamentoGrupo> pesquisarGruposNaoFaturados(Integer referencia) throws ErroRepositorioException;

	public BigDecimal pesquisarValorAtualizacaoCobradas(int idConta) throws ErroRepositorioException;

	public BigDecimal pesquisarValorJurosCobrados(int idConta) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Atualiza os im�veis com situa��o especial de cobran�a finalizada
	 * 
	 * @author Hebert Falc�o
	 * @date 02/05/2012
	 */
	public void atualizarImoveisSituacaoEspecialCobrancaFinalizada(int anoMesFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0155] Encerrar Faturamento do M�s
	 * Atualiza os im�veis com situa��o especial de faturamento finalizada
	 * 
	 * @author Hebert Falc�o
	 * @date 02/05/2012
	 */
	public void atualizarImoveisSituacaoEspecialFaturamentoFinalizada(int anoMesFaturamento) throws ErroRepositorioException;

	/**
	 * [UC3057] Retirar Conta de Revis�o Prazo Vencido
	 * Pesquisa Todas as contas em Revis�o e com C�digo de Revis�o que possua Prazo de Validade e
	 * que o prazo de Validade j� esteja vencido
	 * 
	 * @author Hugo Lima
	 * @date 15/05/2012
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> pesquisarContasRevisaoPrazoVencido() throws ErroRepositorioException;

	/**
	 * [UC0088] Registrar Faturamento Imediato
	 * Pesquisa os motivos de revis�o associados a um grupo de faturamento em determinado periodo
	 * 
	 * @author Hugo Lima
	 * @date 23/05/2012
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoGrupoRevisao> pesquisarContasMotivoRevisaoGrupoFaturamento(Integer idFaturamentoGrupo, Integer referencia)
					throws ErroRepositorioException;

	/**
	 * Aten��o m�todo provis�rio apenas para corre��o do faturamento imediato 08/05/2012
	 */
	public Collection<Object[]> selecionarContasCorrigirFaturamentoImediato() throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Obt�m IMTA_PCALIQUOTA da tabela IMPOSTO_TIPO_ALIQUOTA com IMTA_AMREFERENCIA com o maior
	 * valor que seja igual ou menor ao ano/m�s de refer�ncia e IMTP_ID igual � IMTP_ID da tabela
	 * IMPOSTO_TIPO com IMTP_ICUSO igual �1� - Ativo
	 * 
	 * @author Anderson Italo
	 * @date 02/06/2012
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterPercentualAliquotaImpostoFederal(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Obt�m as tarifas a partir da tabela CONSUMO_TARIFA_FAIXA com CSTC_ID = CSTC_ID da tabela
	 * CONSUMO_TARIFA_CATEGORIA com CSTV_ID = CSTV_ID da tabela CONSUMO_TARIFA_VIGENCIA com ano/m�s
	 * de vig�ncia (CSTV_DTVIGENCIA) menor ou igual ao ano/m�s do faturamento recebido, ordenando
	 * pela tarifa (CSTF_ID), pela vig�ncia (CSTV_DTVIGENCIA) em ordem decrescente, pela categoria
	 * (CATG_ID) e pela faixa (CTFX_NNCONSUMOFAIXAFIM) em ordem crescente
	 * 
	 * @author Anderson Italo
	 * @date 02/06/2012
	 * @throws ErroRepositorioException
	 */
	public List pesquisarTarifasArquivoTextoFaturamentoImediato(Integer referencia) throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Obt�m as anormalidades de leitura (a partir da tabela LEITURA_ANORMALIDADE com LTAN_ICUSO=1
	 * (um), ordenando pelo c�digo da anormalidade (LTAN_ID))
	 * 
	 * @author Anderson Italo
	 * @date 02/06/2012
	 * @throws ErroRepositorioException
	 */
	public List pesquisarLeituraAnormalidadeFaturamentoImediato() throws ErroRepositorioException;

	/**
	 * [UC3012] Gerar Arquivo Texto Faturamento Imediato
	 * Obt�m os setores (a partir da tabela SETOR_COMERCIAL ordenando
	 * pela localidade (LOCA_ID) e c�digo do setor comercial (STCM_CDSETORCOMERCIAL)) das rotas
	 * selecionadas
	 * 
	 * @author Anderson Italo
	 * @date 02/06/2012
	 * @throws ErroRepositorioException
	 */
	public List<Object[]> pesquisarSetorComercialFaturamentoImediato(String idsRotas) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0001] � Gerar Arquivo Texto para Faturamento Imediato.
	 * Retorna a Descri��o da ContaMensagem mais recente de acordo com o AnoMesFaturamento e com o
	 * GrupoFaturamento.
	 * 
	 * @author Anderson Italo
	 * @date 03/06/2012
	 */
	public Object[] pesquisarDescricaoContaMensagemFaturamentoImediato(Integer anoMesFaturamento, Integer idFaturamentoGrupo,
					Integer idGerenciaRegional, Integer idLocalidade, Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0002] � Obter Dados da Qualidade �gua
	 * Retorna dados da qualidade da �gua de acordo com o a refer�ncia do faturamento e com o setor
	 * comercial.
	 * 
	 * @author Anderson Italo
	 * @date 04/06/2012
	 */
	public QualidadeAgua pesquisarQualidadeAguaPorSetorComercialAnoMesFaturamento(Integer anoMesFaturamento, Integer idSetorComercial)
					throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * Obt�m o d�bito autom�tico do im�vel
	 * 
	 * @author Anderson Italo
	 * @date 04/06/2012
	 */
	public DebitoAutomatico pesquisarDebitoAutomaticoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0006] � Obter d�bitos anteriores
	 * 
	 * @author Anderson Italo
	 * @date 05/06/2012
	 */
	public List obterTotalDebitosContaAnteriores(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * [UC3012][SB0006]
	 * M�todo respons�vel por obter os debitos anteriores de um im�vel
	 * 
	 * @param idImovel
	 * @param qtdDiasVencContaAvisoCorte
	 * @param vlMinDebitosAnteriores
	 * @param idMotivoNegativacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	List<Conta> obterDebitosAnteriores(Integer idImovel, Integer qtdDiasVencContaAvisoCorte, BigDecimal vlMinDebitosAnteriores,
					Integer idMotivoNegativacao) throws ErroRepositorioException;

	/**
	 * Atualizar Indicador PDD Conta
	 * Author: Hugo Lima
	 * Data: 27/06/2012
	 * Atualiza o indicador de PDD na tabela de conta
	 * 
	 * @param idConta
	 * @param indicadorPDD
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorPDDDeConta(Integer idConta, Short indicadorPDD, boolean indicadorHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * Alterar a Conta dos Itens do Documento de Cobran�a
	 * 
	 * @author Hebert Falc�o
	 * @date 12/07/2012
	 */
	public void alterarContaCobrancaDocumentoItem(Integer idContaAntiga, Integer idContaNova) throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * Alterar a Conta da Administra��o da Cobran�a do Im�vel
	 * 
	 * @author Hebert Falc�o
	 * @date 12/07/2012
	 */
	public void alterarContaImovelCobrancaAdministrivaItem(Integer idContaAntiga, Integer idContaNova) throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * Alterar a Conta dos Documentos N�o Entregues
	 * 
	 * @author Hebert Falc�o
	 * @date 12/07/2012
	 */
	public void alterarContaDocumentoNaoEntregue(Integer idContaAntiga, Integer idContaNova) throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * Alterar a Conta dos Itens da Fatura
	 * 
	 * @author Hebert Falc�o
	 * @date 12/07/2012
	 */
	public void alterarContaFaturaItem(Integer idContaAntiga, Integer idContaNova) throws ErroRepositorioException;

	/**
	 * [UC3061] Gerar Relat�rio Posi��o do D�bito da Negativa��o � Legado CASAL
	 * Pesquisar as contas em processo de negativa��o e j� transferidas para o hist�rico
	 * 
	 * @date 28/07/2012
	 * @author Hebert Falc�o
	 */
	public Collection<ContaHistorico> pesquisarContaEmProcessoNegativacao(Integer anoMesFaturamento) throws ErroRepositorioException;

	/**
	 * [UC3165] Gerar Relat�rio Posi��o do D�bito da Negativa��o - Legado CAGEPA
	 * Pesquisar as contas em processo de negativa��o e j� transferidas para o hist�rico
	 * 
	 * @date 07/03/2015
	 * @author Luciano Galv�o
	 */
	public Collection<ContaHistorico> pesquisarContaEmProcessoNegativacaoCagepa(Integer anoMesFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0614] Gerar Resumo das A��es de Cobran�a Eventuais
	 * 
	 * @author Anderson Italo
	 * @date 13/07/2012
	 */
	public List<FaturamentoAtividadeCronograma> pesquisarFaturamentoAtividadeCronograma(Integer idFaturamentoGrupoCronogramaMensal,
					String idsFaturamentoAtividades) throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * Alterar a Conta dos Boletos Bancarios
	 * 
	 * @author Hebert Falc�o
	 * @date 15/08/2012
	 */
	public void alterarContaBoletoBancario(Integer idContaAntiga, Integer idContaNova) throws ErroRepositorioException;

	/**
	 * Pesquisar as contas que pertencem ao imovel passado
	 * 
	 * @date 08/08/2012
	 * @author Jose Claudio
	 */
	public boolean existePermissaoImovelConta(Integer idConta, Integer matriculaImovel) throws ErroRepositorioException;

	/**
	 * Retorna a lista de ids tipos de debitos dos itens de parcelamento (prestacoes)
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param idParcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterDebitosGuiasPagamentoPrestacoesParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * Count do Gerar Relacao Acompanhamento Faturamento
	 * [UC0336] GerarRelacaoAcompanhamentoFaturamento
	 * 
	 * @author Jose Claudio
	 * @date 29/08/2012
	 */
	public Integer gerarRelacaoAcompanhamentoFaturamentoCount(String idImovelCondominio, String idImovelPrincipal, String idNomeConta,
					String idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
					String intervaloValorPercentualEsgotoInicial, String intervaloValorPercentualEsgotoFinal,
					String intervaloMediaMinimaImovelInicial, String intervaloMediaMinimaImovelFinal,
					String intervaloMediaMinimaHidrometroInicial, String intervaloMediaMinimaHidrometroFinal, String idImovelPerfil,
					String idPocoTipo, String idFaturamentoSituacaoTipo, String idCobrancaSituacaoTipo, String idSituacaoEspecialCobranca,
					String idEloAnormalidade, String areaConstruidaInicial, String areaConstruidaFinal, String idCadastroOcorrencia,
					String idConsumoTarifa, String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal,
					String setorComercialInicial, String setorComercialFinal, String quadraInicial, String quadraFinal, String loteOrigem,
					String loteDestno, String cep, String logradouro, String bairro, String municipio, String idTipoMedicao,
					String indicadorMedicao, String idSubCategoria, String idCategoria, String quantidadeEconomiasInicial,
					String quantidadeEconomiasFinal, String diaVencimento, String idCliente, String idClienteTipo,
					String idClienteRelacaoTipo, String numeroPontosInicial, String numeroPontosFinal, String numeroMoradoresInicial,
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String consumoFixadoEsgotoPocoInicial,
					String consumoFixadoEsgotoPocoFinal) throws ErroRepositorioException;

	/**
	 * @author Diogo Monteiro
	 * @date 03/09/2012
	 * @param anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterValorTotalContasSelicionadas(String anoMesFaturamento) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por listar os d�bitos cobrados de uma conta
	 * 
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	Collection<Object[]> listarParametrosDebitosCobradoParcelamento(Integer idConta, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * Retorna os registros de movimento roteiro empresa para a refer�ncia e grupo informado
	 * 
	 * @author Anderson Italo
	 * @date 13/11/2012
	 */
	public Collection<MovimentoRoteiroEmpresa> pesquisarDadosMovimentoRoteiroEmpresaAjuste1(Integer anoMesFaturamento, Integer idRota)
					throws ErroRepositorioException;

	/**
	 * Obt�m a conta do im�vel pela refer�ncia informada
	 * 
	 * @author Anderson Italo
	 * @date 13/11/2012
	 */
	public Conta pesquisarContaDaReferenciaPorImovel(Integer idImovel, Integer anoMesReferencia, String idsDebitoCreditoSituacaoAtual)
					throws ErroRepositorioException;

	/**
	 * Obt�m os d�bitos cobrados da conta pra refer�ncia informada
	 * 
	 * @author Anderson Italo
	 * @date 13/11/2012
	 */
	public Collection<DebitoCobrado> pesquisarDebitosCobradoDaConta(Integer idConta, Integer idDebitoTipo, Short numeroPrestacao,
					Short numeroPrestacaoDebito) throws ErroRepositorioException;

	/**
	 * Obt�m os d�bitos a cobrar do im�vel pra refer�ncia informada
	 * 
	 * @author Anderson Italo
	 * @date 13/11/2012
	 */
	public Collection<DebitoACobrar> pesquisarDebitosACobrarDoImovel(Integer idImovel, Integer anoMesReferencia, Integer idDebitoTipo,
					Short numeroPrestacaoDebito) throws ErroRepositorioException;

	/**
	 * Obt�m o pagamento da conta
	 * 
	 * @author Anderson Italo
	 * @date 13/11/2012
	 */
	public Pagamento pesquisarPagamentoConta(Integer idConta, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Retorna os registros de movimento roteiro empresa para a refer�ncia e grupo informado
	 * 
	 * @author Anderson Italo
	 * @date 13/11/2012
	 */
	public Collection<MovimentoRoteiroEmpresa> pesquisarDadosMovimentoRoteiroEmpresaAjuste2(Integer anoMesFaturamento, Integer idRota)
					throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 15/11/2012
	 */

	public Collection pesquisarContaHistoricoDaReferenciaPorRota(Integer idRota, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 15/11/2012
	 */
	public Collection pesquisarContaDaReferenciaPorRota(Integer idRota, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 15/11/2012
	 */
	public EmitirContaHelper pesquisarContaHelper(Integer idConta) throws ErroRepositorioException;

	/**
	 * @author Yara Souza
	 * @date 15/11/2012
	 */
	public EmitirContaHelper pesquisarContaHistoricoHelper(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * Verificar exist�ncia de movimento para a conta
	 * 
	 * @author Anderson Italo
	 * @date 02/11/2012
	 * @return Object
	 * @exception ErroRepositorioException
	 */
	public Object verificarExistenciaDebitoAutomaticoMovimentoConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Retorna os d�bitos a cobrar a serem cancelados
	 * 
	 * @author Anderson Italo
	 * @date 26/11/2012
	 */
	public Collection<Object[]> pesquisarDebitoACobrarParaCancelar() throws ErroRepositorioException;

	/**
	 * Retorna a exist�ncia de d�bitos a cobrar de rateio
	 * 
	 * @author Carlos Chrystian
	 * @date 07/12/2012
	 */
	public Integer pesquisarExistenciaRateio(Integer idImovel, int anoMesReferencia, String parametroDebitoTipoRateio)
					throws ErroRepositorioException;

	/**
	 * Retorna a soma dos d�bitos a cobrar do im�vel
	 * 
	 * @author Carlos Chrystian
	 * @date 11/12/2012
	 */
	public BigDecimal somaValorDebitoACobrarImovel(Integer idImovel, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Obt�m somat�rio dos d�bitos cobrados da conta pra refer�ncia, tipo de d�bito e presta��o
	 * informada
	 * 
	 * @author Anderson Italo
	 * @date 09/12/2012
	 */
	public BigDecimal pesquisarSomatorioDebitosCobradoDaContaOriginal(Integer idConta, Integer idDebitoTipo, Short numeroPrestacao,
					Short numeroPrestacaoDebito) throws ErroRepositorioException;

	public DebitoACobrar pesquisarDebitosACobrarDoImovel(Integer idImovel, Integer anoMesCobranca, Integer idDebitoTipo)
					throws ErroRepositorioException;

	public Conta pesquisarContaAtualRetificada(Integer idImovel, Integer anoMesReferencia) throws ErroRepositorioException;

	public ContaHistorico pesquisarContaOriginalRetificada(Integer idImovel, Integer anoMesReferencia) throws ErroRepositorioException;

	public Collection<ContaHistorico> pesquisarContaRetificadaOutroUsuarioPeriodo(Integer idImovel, Integer anoMesReferencia,
					Date dataInicialPrimeiroAjuste) throws ErroRepositorioException;

	public Collection<DebitoCobradoHistorico> pesquisarDebitosCobradoDaContaOriginal(Integer idConta) throws ErroRepositorioException;

	public BigDecimal pesquisarSomatorioDebitosACobrarNaoFaturadosGeradoPrimeiroAjuste(Integer idImovel, Integer anoMesReferenciaDebito,
					Integer anoMesCobranca, Integer idDebitoTipo) throws ErroRepositorioException;

	public BigDecimal pesquisarSomatorioDebitosACobrarHistoricoFaturadosGeradoPrimeiroAjuste(Integer idImovel,
					Integer anoMesReferenciaDebito, Integer anoMesCobranca, Integer idDebitoTipo) throws ErroRepositorioException;

	public ContaHistorico pesquisarContaRetificadaPeriodoAnterior(Integer idImovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	public Collection<Object[]> pesquisarDebitoACobrarParaCancelarRotinaAjuste() throws ErroRepositorioException;

	public ContaHistorico pesquisarContaEmHistoricoIncluidaCanceladaParcelada(Integer idImovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param anoMesReferencia
	 * @param idDebitoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> pesquisarDebitosACobrarDoImovel(Integer idImovel, Collection anoMesReferencia, Integer idDebitoTipo)
					throws ErroRepositorioException;

	public ContaHistorico pesquisarContaHistoricoOriginalRetificadaRotinaQuantidadeRubricas(Integer idImovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	public Conta pesquisarContaOriginalRetificadaRotinaQuantidadeRubricas(Integer idImovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	public Collection<DebitoCobrado> pesquisarDebitosCobradoDaContaRetificada(Integer idConta) throws ErroRepositorioException;

	public BigDecimal pesquisarSomatorioDebitosCobradoDaContaRetificada(Integer idConta, Integer idDebitoTipo, Short numeroPrestacao,
					Short numeroPrestacaoDebito) throws ErroRepositorioException;

	public Collection<Object[]> pesquisarDebitoACobrarParaCancelarDuplicados() throws ErroRepositorioException;

	public Collection<Object[]> pesquisarDebitoACobrarParaCancelarDuplicados2() throws ErroRepositorioException;

	public Collection<Integer> pesquisarIdsPagamentosAMaiorParaClassificar() throws ErroRepositorioException;

	public BigDecimal pesquisarSomatorioDebitosACobrarFaturadosGeradosRotinaAjuste(Integer idImovel) throws ErroRepositorioException;

	public Collection pesquisarIdsContaPorGrupo(Integer situacao, Integer idGrupo, Integer referencia) throws ErroRepositorioException;

	public Object[] verificaExistenciaContaOuContaHistoricoGerarProvisaoReceita(Integer idImovel, Integer referenciaConta)
					throws ErroRepositorioException;

	public void removerProvisaoReceitaAnoMes(Integer anoMesProvisao) throws ErroRepositorioException;

	public Integer verificaExistenciaProvisaoReceitaAnoMes(Integer anoMesProvisao, Integer idImovel) throws ErroRepositorioException;

	public Collection<DebitoACobrar> pesquisarDebitoACobrarSuspensoParaCancelar(Integer limite, Integer numeroDiasSuspensao)
					throws ErroRepositorioException;

	public boolean verificarExistenciaDeGrupoFaturamentoParaImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0320] Gerar Fatura de Cliente Respons�vel
	 * [FS0006] � Verifica exist�ncia de im�veis de localidades associadas a concession�rias
	 * diversas
	 * 
	 * @author Anderson Italo
	 * @date 22/02/2013
	 */
	public Integer verificaExistenciaLocalidadesAssociadaConcessionariasDiversas(Integer periodoReferenciaContasInicial,
					Integer periodoReferenciaContasFinal, Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0203] Consultar D�bitos
	 * [FS0012] � Verifica exist�ncia de im�veis de localidades associadas a concession�rias
	 * diversas
	 * 
	 * @author Anderson Italo
	 * @date 23/02/2013
	 */
	public Integer verificaExistenciaLocalidadesAssociadaConcessionariasDiversas(Collection idsContas, String idsGuias, String idsDebitos,
					String idsCreditos) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] � Gerar Integra��o para Faturamento Imediato � Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Object verificarDebitosPendentesImovelTipoConta(Integer idImovel) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por listar CreditoOrigem a partir do tipo de cr�dito
	 * 
	 * @param idCreditoTipo
	 * @param indicadorUsoLivre
	 * @return
	 * @throws ErroRepositorioException
	 */
	Collection<CreditoOrigem> listarCreditoOrigem(Integer idCreditoTipo, Short indicadorUsoLivre) throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * [SB0101] � Gerar Integra��o para Faturamento Imediato � Modelo 3
	 * 
	 * @author Anderson Italo
	 * @date 01/04/2013
	 */
	public Collection<Object[]> pesquisarDadosContasVinculadasDocumentoReaviso(Integer idGrupo, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdsImovel() throws ErroRepositorioException;

	public Collection pesquisarIdsImoveisAjusteRetificarContasRetirarDebitorateioDuplicado() throws ErroRepositorioException;

	/**
	 * [UC3012 � Gerar Arquivo Texto Faturamento Imediado]
	 * Obt�m BANC_ID da agencia de d�bito autom�tico do im�vel
	 * 
	 * @author Anderson Italo
	 * @date 05/04/2013
	 */
	public Integer pesquisarBancoDebitoAutomaticoImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por estornar um credito a realizar
	 * 
	 * @param idCreditoARealizarHistorico
	 * @param numeroPrestacaoRealizada
	 * @throws ErroRepositorioException
	 */
	void estornarCreditoARealizar(Integer idCreditoARealizarHistorico, Short numeroPrestacaoRealizada) throws ErroRepositorioException;

	/**
	 * Inserir Pagamentos
	 * Pesquisa a conta digitada
	 */
	public Object[] pesquisarContaHistoricoDigitada(String idImovel, String referenciaConta) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por obter o total de economias e o total de liga��es do histograma de agua
	 * por economia
	 * 
	 * @param idGerenciaRegional
	 * @param idLocalidade
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	Object[] obterTotalEconomiasETotalLigacoesHistogramaAgua(Integer idGerenciaRegional, Integer idLocalidade, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0002] � Verificar N�o Gera��o da Declara��o para o Im�vel � Modelo 1
	 * Verifica se o im�vel tem contas vencida no ano de ref�ncia
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 19/04/2013
	 */
	public boolean verificarImovelContasVencidasAnoReferencia(Integer idImovel, Date datavencimentoconta) throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0002] � Verificar N�o Gera��o da Declara��o para o Im�vel � Modelo 1
	 * Verifica se tem pagamentos para im�vel no ano de ref�ncia
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 19/04/2013
	 */
	public boolean verificarPagamentosParaImovelAnoReferencia(Integer idImovel, Date datavencimentoconta) throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0002] � Verificar N�o Gera��o da Declara��o para o Im�vel � Modelo 1
	 * Verifica se tem pagamentos no hist�rico para im�vel no ano de ref�ncia
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 19/04/2013
	 */
	public boolean verificarPagamentosHistoricoParaImovelAnoReferencia(Integer idImovel, Date datavencimentoconta)
					throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * Pesquisa contas quitadas no ano de ref�ncia
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 24/04/2013
	 */
	public Collection<ContaHistorico> pesquisaContasQuitadasAnoReferencia(Integer idImovel, Integer referenciaFinal, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3014] Emitir Declara��o Anual Quita��o D�bitos
	 * [SB0001] Emitir Declara��o Anual de Quita��o de D�bitos
	 * 
	 * @author Hebert Falc�o
	 * @created 27/04/2013
	 */
	public Collection<QuitacaoDebitoAnual> pesquisarQuitacaoDebitoAnualParaEmicao(Integer idFaturamentoGrupo, Integer anoReferencia,
					Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0003] Verificar N�o Gera��o da Declara��o para o Im�vel � Modelo 2
	 * 
	 * @author Hebert Falc�o
	 * @created 28/04/2013
	 */
	public boolean verificarImovelContasVencidasAnoReferencia(Integer idImovel, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0003] Verificar N�o Gera��o da Declara��o para o Im�vel � Modelo 2
	 * 
	 * @author Hebert Falc�o
	 * @created 28/04/2013
	 */
	public boolean verificarImovelGuiasVencidasAnoReferencia(Integer idImovel, Date dataInicial, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0003] Verificar N�o Gera��o da Declara��o para o Im�vel � Modelo 2
	 * 
	 * @author Hebert Falc�o
	 * @created 28/04/2013
	 */
	public boolean verificarPagamentosHistoricoParaImovelAnoReferencia(Integer idImovel, Integer referenciaInicial, Integer referenciaFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0003] Verificar N�o Gera��o da Declara��o para o Im�vel � Modelo 2
	 * 
	 * @author Hebert Falc�o
	 * @created 28/04/2013
	 */
	public boolean verificarImovelParcelamentoAnoReferencia(Integer idImovel, Integer referenciaFinal, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3014] Emitir Declara��o Anual Quita��o D�bitos
	 * [SB0001] Emitir Declara��o Anual de Quita��o de D�bitos
	 * 
	 * @author Hebert Falc�o
	 * @created 27/04/2013
	 */
	public Integer pesquisarQuitacaoDebitoAnualParaEmicaoQtd(Integer idFaturamentoGrupo, Integer anoReferencia)
					throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por obter o percentual de esgoto
	 * 
	 * @param anoMesFaturamento
	 * @param idGerenciaRegional
	 * @param idLocalidade
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	Short obterPercentualEsgotoHistogramaEsgotoEconomia(Integer anoMesFaturamento, Integer idGerenciaRegional, Integer idLocalidade,
					Integer idCategoria) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por obter o valor da tarifa vigente por categoria
	 * 
	 * @param idConsumoTarifaDefault
	 * @param anoMesReferencia
	 * @param idCategoria
	 * @param numeroFaixaInicio
	 * @param numeroFaixaFim
	 * @param isPrimeiraFaixa
	 * @return
	 * @throws ErroRepositorioException
	 */
	BigDecimal obterValorTarifaVigentePorCategoria(Integer idConsumoTarifaDefault, Integer anoMesReferencia, Integer idCategoria,
					Integer numeroFaixaInicio, Integer numeroFaixaFim, boolean isPrimeiraFaixa) throws ErroRepositorioException;

	/**
	 * @param referencia
	 * @param faturamentoGrupo
	 * @param situacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> pesquisarConta(Integer referencia, Integer faturamentoGrupo, Integer situacao) throws ErroRepositorioException;

	/**
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<DebitoCobrado> pesquisarDebitoCobrado(Integer idConta) throws ErroRepositorioException;

	/**
	 * @param referencia
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarPorReferenciaUltimaCobranca(Integer referencia, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * @param referencia
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarHistoricoPorReferenciaUltimaCobranca(Integer referencia, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @param sinal
	 * @return
	 */

	public Collection<Integer> pesquisarImovelDoDebitoCobradoPorPrestacaoCobradas(Integer idFaturamentoGrupo, Integer anoMesReferencia,
					String sinal) throws ErroRepositorioException;

	/**
	 * @param collIdImoveis
	 * @param anoMesReferenciaUltimaCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarPorListaDeImoveis(Collection collIdImoveis,
					Integer anoMesReferenciaUltimaCobranca) throws ErroRepositorioException;

	/**
	 * @param collIdImoveis
	 * @param anoMesReferenciaUltimaCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrarHistorico> pesquisarDebitoACobrarHistoricoPorListaDeImoveis(Collection collIdImoveis,
					Integer anoMesReferenciaUltimaCobranca) throws ErroRepositorioException;

	/**
	 * @param referencia
	 * @param faturamentoGrupo
	 * @param situacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<ContaCategoria> pesquisarContaCategoriaPorConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * @param referencia
	 * @param faturamentoGrupo
	 * @param situacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ClienteConta> pesquisarClienteConta(Integer referencia, Integer faturamentoGrupo, Integer situacao)
					throws ErroRepositorioException;

	/**
	 * @param referencia
	 * @param faturamentoGrupo
	 * @param situacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<MovimentoRoteiroEmpresa> pesquisarMovimentoRoteiroEmpresa(Integer referencia, Integer faturamentoGrupo,
					Integer situacao) throws ErroRepositorioException;

	/**
	 * @param referencia
	 * @param faturamentoGrupo
	 * @param situacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ContaGeral> pesquisarContaGeral(Integer referencia, Integer faturamentoGrupo, Integer situacao)
					throws ErroRepositorioException;

	/**
	 * @param idFaturamentoGrupo
	 * @param anoMesReferencia
	 * @param sinal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoCobrado> pesquisarDebitoCobradoPorPrestacaoCobradas(Integer idFaturamentoGrupo, Integer anoMesReferencia,
					String sinal) throws ErroRepositorioException;

	public Collection<Integer> pesquisarIdImoveisDebitoCobradoPorPrestacaoCobradas(Integer idFaturamentoGrupo, Integer anoMesReferencia,
					String sinal) throws ErroRepositorioException;

	public void deletarDebitoACobrarCategoriaHistorico(Integer idDebitoACobrarHistorico) throws ErroRepositorioException;

	public void deletarDebitoACobrarHistorico(Integer idDebitoACobrarHistorico) throws ErroRepositorioException;

	public void deletarDebitoCobradoCategoria(Integer idDebitoCobrado) throws ErroRepositorioException;

	public void deletarDebitoCobrado(Integer idDebitoCobrado) throws ErroRepositorioException;

	public void deletarContaCategoria(Integer idConta) throws ErroRepositorioException;

	public void deletarClienteConta(Integer idConta) throws ErroRepositorioException;

	public void deletarConta(Integer idConta) throws ErroRepositorioException;

	public void deletarContaGeral(Integer idConta) throws ErroRepositorioException;

	/**
	 * @param idParcelamento
	 * @param idDebitoCreditoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarDebitoACobrarPorParcelamento(Integer idParcelamento, Integer idDebitoCreditoSituacao)
					throws ErroRepositorioException;

	/**
	 * @param idParcelamento
	 * @param idDebitoCreditoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarDebitoACobrarHistoricoPorParcelamento(Integer idParcelamento, Integer idDebitoCreditoSituacao)
					throws ErroRepositorioException;

	/**
	 * @param idParcelamento
	 * @param idDebitoCreditoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Date pesquisarGuiaPagamentoPorParcelamento(Integer idParcelamento, Integer idDebitoCreditoSituacao)
					throws ErroRepositorioException;

	/**
	 * @param idParcelamento
	 * @param idDebitoCreditoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarGuiaPagamentoHistoricoPorParcelamento(Integer idParcelamento, Integer idDebitoCreditoSituacao)
					throws ErroRepositorioException;

	/**
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarContasAjusteCasal() throws ErroRepositorioException;

	/**
	 * @param idDebitoCobrado
	 * @return
	 * @throws ErroRepositorioException
	 */

	public DebitoCobradoCategoria buscarDebitoCobradoCategoriaPorId(Integer idDebitoCobrado) throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DebitoCobrado pesquisarMaiorIdDebitoCobradoPorIdConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarContasHistoricoAjusteCasal(String condicao) throws ErroRepositorioException;

	/**
	 * @param idContaHistorico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DebitoCobradoHistorico pesquisarMaiorIdDebitoCobradoHistoricoPorIdContaHistorico(Integer idContaHistorico)
					throws ErroRepositorioException;

	/**
	 * @param idContaHistorico
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<DebitoCobradoHistorico> pesquisarDebitoCobradoHistoricoPorIdContaHistorico(Integer idContaHistorico)
					throws ErroRepositorioException;

	public void removerDebitoCobradoCategoriaHistorico(Integer idDebitoCobrado) throws ErroRepositorioException;

	/**
	 * @param idDebitoCobrado
	 * @throws ErroRepositorioException
	 */

	public void removerDebitoCobradoHistorico(Integer idDebitoCobrado) throws ErroRepositorioException;

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0004] � Obter os Cr�ditos a Realizar
	 * 
	 * @author Anderson Italo
	 * @date 08/05/2013
	 */
	public Collection<CreditoARealizar> pesquisarCreditosARealizarImovelFaturamentoImediato(Integer idImovel, Integer anoMesCobranca,
					boolean isRetornoFaturamento, Date dataGeracaoFaturamento) throws ErroRepositorioException;

	/**
	 * @param idDebitoACobrar
	 * @throws ErroRepositorioException
	 */
	public void removerDebitoACobrarCategoriaHistorico(Integer idDebitoACobrar) throws ErroRepositorioException;

	/***
	 * @param debitoACobrarCategoria
	 * @throws ErroRepositorioException
	 */

	public void inserirDebitoACobrarCategoria(DebitoACobrarCategoria debitoACobrarCategoria) throws ErroRepositorioException;

	public Collection pesquisarIdsContaAjusteContasEnviadasHistoricoPreFaturadasComValor(String idRota) throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo Faturamento
	 * [SF009] - Adiconar na cole��o para gerar o resumo faturamento simula��o
	 * 
	 * @date 19/06/2013
	 */
	public Collection<ResumoFaturamentoSimulacao> pesquisarResumoFaturamentoSimulacao(Integer idFaturamentoGrupo, Integer anoMesReferencia,
					Integer idRota) throws ErroRepositorioException;

	/**
	 * [UC3100] Consultar Hist�rico de D�bito Autom�tico Im�vel
	 * 24/07/2013
	 * 
	 * @param imovel
	 * @param ehContasOuGuia
	 *            true = ContaGeral, false GuiaPagamento
	 * @return retorna lista debito automatico movimento
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDebitoAutomaticoMovimentoPorImovel(Integer idImovel, boolean ehContaOuGuia)
					throws ErroRepositorioException;

	/**
	 * [UC3055] - Encerrar Faturamento
	 * 
	 * @date 26/07/2013
	 */
	public Collection<Rota> consultarRotasGrupo(Integer idFaturamentoGrupo, Integer anoMesReferencia, int tipoConsulta)
					throws ErroRepositorioException;

	/**
	 * [UC3103] Cancelar D�bito a Cobrar de Rateio por Macromedidor.
	 * 6. O sistema apresenta os dados do im�vel Condom�nio.
	 * 
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<ClienteImovelCondominioHelper> pesquisarClienteImovelCondominioHelper(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3103] Cancelar D�bito a Cobrar de Rateio por Macromedidor.
	 * 6.4.7. Hist�rico de Medi��o Individualizada.
	 * 
	 * @param idImovel
	 * @param idLigacaoTipo
	 * @param anoMesRefFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public HistoricoMedicaoIndividualizadaHelper pesquisarHistoricoMedicaoIndividualizadaHelper(Integer idImovel, Integer idLigacaoTipo,
					Integer anoMesRefFaturamento) throws ErroRepositorioException;

	/**
	 * [UC3103] Cancelar D�bito a Cobrar de Rateio por Macromedidor.
	 * 7. O sistema seleciona os d�bitos a cobrar de rateio dos im�veis vinculados para o m�s
	 * informado.
	 * 
	 * @param idImovel
	 * @param idLigacaoTipo
	 * @param anoMesRefFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<DebitosACobrarRateioImoveisVinculadosHelper> pesquisarDebitosACobrarRateioImoveisVinculadosHelper(Integer idImovel,
					Integer idLigacaoTipo, Integer anoMesRefFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0532] Gerar Relat�rio de Faturamento das Liga��es com Medi��o Individualizada
	 * 
	 * @author Carlos Chrystian
	 * @date 20/09/2013
	 * @param idImovel
	 * @param anoMesReferencia
	 * @throws ControladorException
	 */
	public ContaHistorico pesquisarContaOriginalAntesDaRetificacao(Integer idImovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio
	 * [UC3114] - Gerar Relat�rio Faturamento e Consumo Direto e Indireto Estadual
	 * 
	 * @author Victon Santos
	 * @created 27/09/2013
	 * @throws ControladorException
	 */
	public Collection<FaturamentoConsumoDiretoIndiretoEstadualRelatorioHelper> pesquisarDadosRelatorioFaturamentoConsumoDiretoIndiretoEstadual(
					Integer anoMes, Integer opcaoRelatorio) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param idDebitoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarPorDebitoTipo(Integer idImovel, Integer idDebitoTipo)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio
	 * [] - Gerar Relat�rio Maiores Consumidores
	 * 
	 * @author Victon Santos
	 * @created
	 * @throws ControladorException
	 */
	public Collection pesquisarDadosRelatorioMaioresConsumidores(Integer anoMes, Integer localidade, Integer registros)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os dados necess�rio para a gera��o do relat�rio
	 * [] - Gerar Relat�rio Maiores Devedores
	 * 
	 * @author Victon Santos
	 * @created
	 * @throws ControladorException
	 */
	public Collection pesquisarDadosRelatorioMaioresDevedores(Integer localidade, Integer registros, Integer[] idsTipoCliente)
					throws ErroRepositorioException;

	/**
	 * [UC3128] GerarRelatorioIm�veiscomLiga��oCortadacomConsumo
	 * 
	 * @author Hiroshi Gon�alves
	 * @created 09/12/2013
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarImoveisLigacaoCortadaComConsumo(int anoMesReferencia, Integer grupoFaturamento)
					throws ErroRepositorioException;

	/**
	 * @param anoMesRefInicial
	 * @param anoMesRefFinal
	 * @param idsCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdsContaAjusteContasEnviadasHistorico(Integer anoMesRefInicial, Integer anoMesRefFinal, String[] idsCliente)
					throws ErroRepositorioException;

	public List<Conta> obterDebitosAnteriores(Integer idImovel, BigDecimal vlMinDebitosAnteriores, Integer idMotivoNegativacao,
					Date dataVencimento) throws ErroRepositorioException;

	public Collection pesquisarIdsContaAjusteContasEnviadasHistoricoPreFaturadasZeradasIndicadorEmissaoCampo3(Integer idRota)
					throws ErroRepositorioException;

	/**
	 * [UC3118] Inserir Comando de Simula��o de Faturamento
	 * [FS0007] - Verificar exist�ncia do comando
	 * 
	 * @author Anderson Italo
	 * @date 24/12/2013
	 */
	public FaturamentoSimulacaoComando pesquisarFaturamentoSimulacaoComando(
					InserirComandoSimulacaoFaturamentoHelper inserirComandoSimulacaoFaturamentoHelper) throws ErroRepositorioException;

	/**
	 * [UC3118] Inserir Comando de Simula��o de Faturamento
	 * [FS0008] - Verificar exist�ncia de im�veis para os par�metros
	 * 
	 * @author Anderson Italo
	 * @date 27/12/2013
	 */
	public Collection pesquisarIdsImoveisComandoSimulacaoFaturamento(InserirComandoSimulacaoFaturamentoHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC0113 - Faturar Grupo Faturamento]
	 * 
	 * @author Anderson Italo
	 * @date 30/12/2013
	 */
	public void deletarResumoFaturamentoSimulacaoPorComando(Integer idFaturamentoSimulacaoComando) throws ErroRepositorioException;

	/**
	 * [UC0113] Faturar Grupo de Faturamento
	 * O sistema seleciona as rotas dos im�veis, que n�o sejam im�vel condom�nio
	 * (IMOV_ICIMOVELCONDOM�NIO<>1 da tabela IMOVEL), associados ao filtro do comando de simula��o
	 * recebido
	 * 
	 * @author Anderson Italo
	 * @date 31/12/2013
	 */
	public Collection pesquisarIdsRotasComandoSimulacaoFaturamento(FaturamentoSimulacaoComando faturamentoSimulacaoComando)
					throws ErroRepositorioException;

	/**
	 * [UC0113] Faturar Grupo de Faturamento
	 * O sistema seleciona as rotas dos im�veis, que n�o sejam im�vel condom�nio
	 * (IMOV_ICIMOVELCONDOM�NIO<>1 da tabela IMOVEL), associados ao filtro do comando de simula��o
	 * recebido
	 * 
	 * @author Anderson Italo
	 * @date 31/12/2013
	 */
	public Collection pesquisarMatriculasImoveisComandoSimulacaoFaturamento(FaturamentoSimulacaoComando faturamentoSimulacaoComando)
					throws ErroRepositorioException;

	/**
	 * [UC0146] Manter Conta
	 * [SB0003] - Retificar Conta
	 * 
	 * @author Anderson Italo
	 * @created 17/01/2014
	 * @throws ErroRepositorioException
	 */
	public ClienteConta pesquisarClienteContaPorTipoRelacao(Integer idConta, Integer idClienteRelacaoTipo) throws ErroRepositorioException;

	/**
	 * [UC3134] Manter Comando de Simula��o de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 19/01/2014
	 */
	public Collection<FaturamentoSimulacaoComando> pesquisarFaturamentoSimulacaoComando(Integer numeroPagina, Date dataInicialComando,
					Date dataFinalComando, Short indicadorExecutado) throws ErroRepositorioException;

	/**
	 * [UC3134] Manter Comando de Simula��o de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 19/01/2014
	 */
	public Integer pesquisarTotalRegistrosFaturamentoSimulacaoComando(Date dataInicialComando, Date dataFinalComando,
					Short indicadorExecutado) throws ErroRepositorioException;

	public Collection pesquisarIdsLigacaoEsgotoAjusteErroCalculoConsumoMedio() throws ErroRepositorioException;

	/**
	 * [UC0083] Gerar Dados para Leitura
	 * 
	 * @author Hiroshi Gon�alves
	 * @date 11/02/2014
	 */
	public List<Object[]> pesquisarImoveisComContratoDemandaAVencer(Integer idGrupoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC3132] Gerar Relat�rio de Contratos de Demanda de Consumo
	 * 
	 * @author Vicente Zarga
	 * @created 18/01/2013
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosRelatorioContratoDemandaConsumo(Integer faturamentoGrupo, Integer[] localidades, String tipoContrato,
					Integer tarifaConsumo, Integer mesAnoFaturamentoInicial, Integer mesAnoFaturamentoFinal, Integer encerrado)
					throws ErroRepositorioException;

	/**
	 * [UC3132] Gerar Relat�rio de Contratos de Demanda de Consumo
	 * 
	 * @author Vicente Zarga
	 * @created 18/01/2013
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarDadosRelatorioContratoDemandaConsumoCount(Integer faturamentoGrupo, Integer[] localidades, String tipoContrato,
					Integer tarifaConsumo, Integer mesAnoFaturamentoInicial, Integer mesAnoFaturamentoFinal, Integer encerrado)
					throws ErroRepositorioException;

	/**
	 * [UC3055] Encerrar Faturamento
	 * [SB0007] Encerrar contratos de demanda de consumo
	 * 
	 * @author Vicente Zarga
	 * @date 27/01/2014
	 */
	public void encerrarContratoDemandaConsumo(String referencia) throws ErroRepositorioException;

	/**
	 * [XYZ] Gerar Relat�rio Situa��o Especial de Faturamento
	 * 
	 * @author Hebert Falc�o
	 * @date 16/03/2014
	 */
	public Collection<FaturamentoSituacaoHistorico> consultarSituacaoEspecialDeFaturamento(RelatorioSituacaoEspecialFaturamentoHelper helper)
					throws ErroRepositorioException;

	/**
	 * [XYZ] Gerar Relat�rio Situa��o Especial de Faturamento
	 * 
	 * @author Hebert Falc�o
	 * @date 16/03/2014
	 */
	public Integer consultarSituacaoEspecialDeFaturamentoCount(RelatorioSituacaoEspecialFaturamentoHelper helper)
					throws ErroRepositorioException;

	/**
	 * @param anoMesInicio
	 * @param anoMesFim
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasIntervalo(Integer anoMesInicio, Integer anoMesFim, FaturamentoGrupo faturamentoGrupo)
					throws ErroRepositorioException;

	/**
	 * @param anoMesInicio
	 * @param anoMesFim
	 * @param idSituacaoConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasHistoricoIntervalo(Integer anoMesInicio, Integer anoMesFim, Integer idSituacaoConta,
					FaturamentoGrupo faturamentoGrupo) throws ErroRepositorioException;

	/**
	 * Obt�m os ids das contas que estiverem com o somat�rio do valor das faixas diferentes do valor
	 * da conta
	 * 
	 * @author Anderson Italo
	 * @date 21/03/2014
	 * @throws ErroRepositorioException
	 */
	public List pesquisarIdsContaRegerarContaCategoriaEFaixa(Integer referencia, String idsGrupos) throws ErroRepositorioException;

	/**
	 * Obt�m os ids das contas que estiverem com valor total igual a zero
	 * 
	 * @author Anderson Italo
	 * @date 26/03/2014
	 * @throws ErroRepositorioException
	 */
	public List pesquisarIdsContaZeradasParaEnviarHistorico(Integer referencia) throws ErroRepositorioException;

	/**
	 * Pesquisa count dos dados necess�rio para a gera��o do relat�rio
	 * [UC0638] - Gerar Relat�rios Anormalidade Consumo
	 * 
	 * @author Ado Rocha
	 * @created 27/03/2014
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Integer pesquisarDadosRelatorioAnormalidadeConsumoCount(Integer idGrupoFaturamento, Short codigoRota,
					Integer idGerenciaRegional, Integer idUnidadeNegocio, Integer idElo, Integer idLocalidadeInicial,
					Integer idLocalidadeFinal, Integer referencia, Integer idImovelPerfil, Integer numOcorConsecutivas,
					String indicadorOcorrenciasIguais, Integer mediaConsumoInicial, Integer mediaConsumoFinal,
					Integer idAnormalidadeConsumo, Integer idAnormalidadeLeitura) throws ErroRepositorioException;

	/**
	 * @param imovel
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarExistenciaContaHistoricoPorRefEImovel(Imovel imovel, Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @param tiposParcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> listarParmsDebitoCobradoHistorico(Integer idConta, Collection<Integer> tiposParcelamento)
					throws ErroRepositorioException;

	/**
	 * @param idparcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResolucaoDiretoria pesquisarRD(Integer idparcelamento) throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @param idCreditoTipo
	 * @param sinal
	 * @return
	 */
	public Collection<CreditoRealizadoHistorico> pesquisarCreditosRealizadosHistoricoPorCreditoTipo(Integer idConta, Integer idCreditoTipo,
					String sinal) throws ErroRepositorioException;

	/**
	 * M�todo que retorna a conta_categoria maior quantidade de economias
	 * [UC0083] Gerar Dados para Leitura
	 * [SB0001] - Gerar Arquivo Convencional
	 * [SB0010] - Gerar Arquivo - Modelo 2
	 * Dados para leitura (TIPO E)
	 * 
	 * @author Anderson Italo
	 * @date 11/06/2014
	 * @throws ErroRepositorioException
	 */
	public ContaCategoria pesquisarContaCategoriaComMaiorQuantidadeEconomias(Integer idConta) throws ErroRepositorioException;

	/**
	 * @param debitoACobrar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteDebitoACobrar(DebitoACobrar debitoACobrar) throws ErroRepositorioException;

	/**
	 * @param creditoARealizar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteCreditoARealizar(CreditoARealizar creditoARealizar) throws ErroRepositorioException;

	/**
	 * @param debitoACobrarHistorico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteDebitoACobrarHistorico(DebitoACobrarHistorico debitoACobrarHistorico) throws ErroRepositorioException;



	/**
	 * [UC0150] Retificar Conta
	 * Atualizar itens da execu��o fiscal.
	 * 
	 * @author Gicevalter Couto
	 * @date 10/08/2014
	 */
	public void alterarContaExecucaoFiscalItem(Integer idContaAntiga, Integer idContaNova) throws ErroRepositorioException;

	/**
	 * [UC1016] Estornar Pagamentos
	 * Atualizar itens da execu��o fiscal.
	 * 
	 * @author Gicevalter Couto
	 * @date 12/08/2014
	 */
	public void estornarContaExecucaoFiscalItem(Integer idConta, Date dataEstorno) throws ErroRepositorioException;

	/**
	 * [UC1016] Estornar Pagamentos
	 * Atualizar itens da execu��o fiscal.
	 * 
	 * @author Gicevalter Couto
	 * @date 12/08/2014
	 */
	public void estornarGuiaPagamentoExecucaoFiscalItem(Integer idGuiaPagamento, Short numeroPrestacao, Date dataEstorno)
					throws ErroRepositorioException;

	/**
	 * [UC3082] Atualizar Item Documento Cobran�a
	 * Atualizar itens da execu��o fiscal.
	 * 
	 * @author Gicevalter Couto
	 * @date 12/08/2014
	 */
	public void alterarGuiaPagamentoExecucaoFiscalItem(Integer idGuiaPagamento, Short numeroPrestacao, Integer idSituacaoDebitoItem,
					Date dataSituacaoDebitoItem) throws ErroRepositorioException;

	/**
	 * [UC3082] Atualizar Item Documento Cobran�a
	 * Atualizar itens da execu��o fiscal.
	 * 
	 * @author Gicevalter Couto
	 * @date 12/08/2014
	 */
	public void alterarContaExecucaoFiscalItem(Integer idConta, Integer idSituacaoDebitoItem, Date dataSituacaoDebitoItem)
					throws ErroRepositorioException;

	public Collection<FaturamentoGrupoCronogramaMensal> pesquisarFaturamentoGrupoCronogramaMensalReferenciaMaior(
					Integer idFaturamentoGrupo, Integer anoMesReferencia) throws ErroRepositorioException;

	public FaturamentoGrupoCronogramaMensal pesquisarUltimoFaturamentoGrupoCronogramaMensal(Integer idFaturamentoGrupo)
					throws ErroRepositorioException;

	/**
	 * <p>
	 * [OC1348276] [NF] Refaturar todas as contas da localidade 062 com refer�ncia de 10/2009 a
	 * 08/2014
	 * </p>
	 * <p>
	 * Refaturar todas as contas geradas para o municipio de Siriri, para atendermos ao trabalho que
	 * a Deso est� desenvolvendo naquele munic�pio e a RDE 12/2014. Par�metros:
	 * <ul>
	 * <li>Localidade: 062</li>
	 * <li>Per�odo de refer�ncia das faturas: 10/2009 a 08/2014</li>
	 * <li>Refaturar consumo de todas as matr�culas de categoria Residencial e Comercial para 10m� e
	 * categoria Industrial para 30m�</li>
	 * </ul>
	 * </p>
	 * 
	 * @author msilveira <magno.silveira@procenge.com.br>
	 * @since 2014-09-02
	 * @param idLocalidade
	 * @param anoMesReferenciaInicial
	 * @param anoMesReferenciaFinal
	 * @return Cole��o de Contas
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> obterContasPorLocalidade(Integer idLocalidade, Integer anoMesReferencia) throws ErroRepositorioException;

	public List<Object[]> gerarRelatorioTotalContasEmitidasLocalidade(Integer anoMesReferencia) throws ErroRepositorioException;

	public Long gerarQuantidadeRelatorioTotalContasEmitidasLocalidade(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0187] Inserir Guia de Pagamento
	 * Processos das execu��es especiais pendentes de cobran�a do valor de sucumb�ncia
	 * 
	 * @date 08/09/2014
	 * @author Gicevalter Couto
	 */
	public Collection<Integer> pesquisarProcessosExecucaoEspeciaisPendentesCobranca(Integer idImovel, Integer idCobrancaSituacaoExecFiscal,
					Integer idSucumbencia) throws ErroRepositorioException;

	/**
	 * [UC3156] Simular C�lculo da Conta Dados Reais
	 * 
	 * @author Anderson Italo
	 * @date 22/09/2014
	 */
	public Collection pesquisarContasSimularCalculoDadosReais(FiltroContaSimularCalculoHelper helper, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * [UC3156] Simular C�lculo da Conta Dados Reais
	 * 
	 * @author Anderson Italo
	 * @date 22/09/2014
	 */
	public Integer pesquisarTotalRegistrosContasSimularCalculoDadosReais(FiltroContaSimularCalculoHelper helper)
					throws ErroRepositorioException;
	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0001] - Gerar Declara��o Anual Quita��o D�bitos
	 * 
	 * @param idImovel
	 * @param referenciaFinal
	 * @param dataFinal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<GuiaPagamentoPrestacaoHistorico> pesquisaGuiasPagamentoQuitadasAnoReferencia(Integer idImovel,
					Integer referenciaFinal, Date dataFinal)
					throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0004 - Verificar N�o Gera��o da Declara��o para o Im�vel - Modelo Default]
	 * 
	 * @param idImovel
	 * @param ultimoDiaDoAnoDeReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarImovelContasVencidasAnoReferenciaEmRevisao(Integer idImovel, Date ultimoDiaDoAnoDeReferencia,
					Integer pVerificarPagamentoPendente)
					throws ErroRepositorioException;
	
	
	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0004 - Verificar N�o Gera��o da Declara��o para o Im�vel - Modelo Default]
	 * 
	 * @author Yara Souza
	 * @created 01/10/2014
	 */
	public boolean verificarImovelGuiasVencidasAnoReferencia(Integer idImovel, Date ultimoDiaDoAnoDeReferencia,
					Integer pVerificarPagamentoPendente) throws ErroRepositorioException;

	/**
	 * [UC3013] Gerar Declara��o Anual Quita��o D�bitos
	 * [SB0004 - Verificar N�o Gera��o da Declara��o para o Im�vel - Modelo Default]
	 * 
	 * @author Yara Souza
	 * @created 01/10/2014
	 */
	public boolean verificarPagamentosHistoricoParaImovelAnoReferencia(Integer idImovel, Integer referenciaInicial,
					Integer referenciaFinal,
					Integer pVerificarPagamentoPendente) throws ErroRepositorioException;

	/**
	 * @param idCliente
	 * @param idCobrancaSituacaoExecFiscal
	 * @param idSucumbencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarProcessosExecucaoEspeciaisPendentesCobrancaPorCliente(Integer idCliente,
					Integer idCobrancaSituacaoExecFiscal, Integer idSucumbencia) throws ErroRepositorioException;

	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoPorParcelamento(Integer parcelamentoId)
					throws ErroRepositorioException;

	public void inserirRegistrosTabelaTemporariaRelatorioTotalContasEmitidasLocalidade(Integer anoMesReferencia)
					throws ErroRepositorioException;
		
	public void criarTabelaTemporariaRelatorioTotalContasEmitidasLocalidade() throws ErroRepositorioException;
	/**
	 * @author Magno Silveira <magno.silveira@procenge.com.br>
	 * @since 16/04/2015
	 * @param idImovel
	 * @param paramMotivoRetificaoOcorrenciaConsumo
	 * @return
	 */
	public int obterQtdContasRetificadasPorMotivoRetificacao(Integer idImovel, Integer paramMotivoRetificaoOcorrenciaConsumo)
					throws ErroRepositorioException;

}