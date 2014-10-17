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
 * 
 * GSANPCG
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 * Vitor Cavalcante Hora
 * 
 */

package gcom.faturamento;

import gcom.arrecadacao.ContratoDemanda;
import gcom.arrecadacao.ContratoDemandaConsumo;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimentoHelper;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgoto;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.batch.FuncionalidadeIniciada;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.bean.CalcularAcrescimoPorImpontualidadeHelper;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.GupoFaturamentoHelper;
import gcom.faturamento.bean.*;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaCategoria;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.conta.*;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.*;
import gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.gui.faturamento.bean.ListaDadosPrestacaoGuiaHelper;
import gcom.gui.faturamento.bean.ManterContaHelper;
import gcom.gui.faturamento.consumotarifa.bean.CategoriaFaixaConsumoTarifaHelper;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.Rota;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.relatorio.faturamento.RelatorioAnaliticoFaturamentoHelper;
import gcom.relatorio.faturamento.RelatorioArquivoDeclaracaoAnualQuitacaoDebitos;
import gcom.relatorio.faturamento.RelatorioSituacaoEspecialFaturamentoHelper;
import gcom.relatorio.faturamento.conta.RelatorioContaModelo2Bean;
import gcom.relatorio.faturamento.conta.RelatorioContaModelo3Bean;
import gcom.relatorio.ordemservico.DadosContaEmRevisaoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.FachadaException;

import java.math.BigDecimal;
import java.util.*;

/**
 * Interface Controlador Faturamento PADRÃO
 * 
 * @author Raphael Rossiter
 * @date 20/12/2006
 */

public interface IControladorFaturamento {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoAtividadeCronogramas
	 *            Descrição do parâmetro
	 * @param faturamentoGrupoCronogramaMensal
	 *            Descrição do parâmetro
	 */
	public void inserirFaturamentoCronograma(Collection faturamentoAtividadeCronogramas,
					FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal, RegistradorOperacao registradorOperacao)
					throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupoCronogramaMensal
	 *            Descrição do parâmetro
	 * @param faturamentoAtividadeCronogramas
	 *            Descrição do parâmetro
	 */
	public void inserirFaturamentoGrupoCronogramaMensal(FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal,
					Collection faturamentoAtividadeCronogramas, Usuario usuarioLogado) throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param rotas
	 *            Descrição do parâmetro
	 */
	public void faturarGrupoFaturamento(Collection colecaoRotas, FaturamentoGrupo faturamentoGrupo, Integer anoMesReferencia,
					int atividade, int idFuncionalidadeIniciada, FaturamentoSimulacaoComando faturamentoSimulacaoComando)
					throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupoCronogramaMensal
	 *            Descrição do parâmetro
	 * @param faturamentoAtividadeCronogramas
	 *            Descrição do parâmetro
	 */
	public void atualizarFaturamentoGrupoCronogramaMensal(FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal,
					Collection faturamentoAtividadeCronogramas, Collection colecaoTodasAtividades, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 * @return um boleano que identifica a existência do cronograma para o grupo
	 */
	public void verificarExistenciaCronogramaGrupo(FaturamentoGrupo faturamentoGrupo) throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 * @param faturamentoAtividade
	 */
	public boolean verificarExistenciaCronogramaAtividadeGrupo(FaturamentoAtividade faturamentoAtividade, FaturamentoGrupo faturamentoGrupo)
					throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 * @return um boleano que identifica a existência do cronograma para o grupo
	 */
	public Collection selecionarAtividadeFaturamentoQuePodeSerComandada(FaturamentoGrupo faturamentoGrupo) throws ControladorException;

	/**
	 * < <Descrição do método>>
	 * 
	 * @param faturamentoGrupo
	 * @return uma coleção de rotas pertencentes ao grupo selecionado
	 */
	public Collection verificarExistenciaRotaGrupo(FaturamentoGrupo faturamentoGrupo) throws ControladorException;

	/**
	 * @param colecaoRotasGrupo
	 * @param faturamentoAtividade
	 * @param faturamentoGrupo
	 * @return
	 */
	public Collection verificarSituacaoAtividadeRota(Collection colecaoRotasGrupo, FaturamentoAtividade faturamentoAtividade,
					FaturamentoGrupo faturamentoGrupo, boolean habilitada) throws ControladorException;

	public Integer inserirComandoAtividadeFaturamento(FaturamentoGrupo faturamentoGrupo, FaturamentoAtividade faturamentoAtividade,
					Collection colecaoRotas, Date dataVencimentoGrupo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * Retorna uma lista de atividades de faturamento comandadas e ainda não
	 * realizadas
	 */
	public Collection buscarAtividadeComandadaNaoRealizada(Integer numeroPagina, Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * Este caso de uso permite alterar ou excluir um comando de atividade de
	 * faturamento
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * Retorna o count do resultado da pesquisa de Faturamento Atividade
	 * Cronograma não realizadas
	 * buscarAtividadeComandadaNaoRealizadaCount
	 * 
	 * @author Roberta Costa
	 * @date 18/07/2006
	 * @param filtroFaturamentoAtividadeCronograma
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer buscarAtividadeComandadaNaoRealizadaCount(Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * [SB0002] - Excluir Comando de Atividade de Faturamento
	 */
	public void removerComandoAtividadeFaturamento(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * Atualizar Comando de Atividade de Faturamento
	 * 
	 * @param usuarioLogado
	 */
	public void atualizarComandoAtividadeFaturamento(FaturamentoAtividadeCronograma faturamentoAtividadeCronograma,
					Collection colecaoFaturamentoAtividadeCronogramaRota, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0120 - Calcular Valores de Água e/ou Esgoto]
	 * 
	 * @param anoMesReferencia
	 * @param ligacaoSituacaoAguaId
	 * @param ligacaoSituacaoEsgotoId
	 * @param indicadorFaturamentoAgua
	 * @param indicadorFaturamentoEsgoto
	 * @param categoriasImovel
	 * @param consumoFaturadoAguaMes
	 * @param consumoFaturadoEsgotoMes
	 * @param consumoMinimoLigacao
	 * @param dataLeituraAnterior
	 * @param dataLeituraAtual
	 * @param percentualEsgoto
	 * @param tarifaImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CalcularValoresAguaEsgotoHelper> calcularValoresAguaEsgoto(Integer anoMesReferencia, Integer ligacaoSituacaoAguaId,
					Integer ligacaoSituacaoEsgotoId, Short indicadorFaturamentoAgua, Short indicadorFaturamentoEsgoto,
					Collection categoriasImovel, Integer consumoFaturadoAguaMes, Integer consumoFaturadoEsgotoMes,
					int consumoMinimoLigacao, Date dataLeituraAnterior, Date dataLeituraAtual, BigDecimal percentualEsgoto,
					Integer tarifaImovel, Integer idImovel) throws ControladorException;

	/**
	 * @param imovel
	 * @param situacao
	 * @return
	 * @throws ControladorException
	 */
	public Date buscarDataLeituraCronograma(Imovel imovel, boolean situacao, Integer anoMesReferencia) throws ControladorException;

	/**
	 * Calcula os valores da conta de acordo com os parâmetros passados
	 * [UC0145] - Inserir Conta [SF0001] - Determinar Valores para Faturamento
	 * de Água e/ou Esgoto Author: Raphael Rossiter 05/12/2005
	 * 
	 * @param mesAnoConta
	 * @param imovelID
	 * @param situacaoAguaConta
	 * @param situacaoEsgotoConta
	 * @param colecaoCategoria
	 * @param consumoAgua
	 * @param consumoEsgoto
	 * @param percentualEsgoto
	 * @return Collection<CalcularValoresAguaEsgotoHelper>
	 * @throws ControladorException
	 */
	public Collection<CalcularValoresAguaEsgotoHelper> calcularValoresConta(String mesAnoConta, String imovelID, Integer situacaoAguaConta,
					Integer situacaoEsgotoConta, Collection colecaoCategoria, String consumoAgua, String consumoEsgoto,
					String percentualEsgoto, Integer idConsumoTarifaConta, Usuario usuarioLogado, Date dataLeituraAnterior,
					Date dataLeituraAtual) throws ControladorException;

	/**
	 * Cálcula o valor total dos débitos de uma conta de acordo com o informado
	 * pelo usuário
	 * [UC0145] - Inserir Conta Author: Raphael Rossiter Data: 10/01/2006
	 * 
	 * @param colecaoDebitoCobrado
	 * @param requestMap
	 * @return BigDecimal valorTotalDebitoConta
	 * @throws ControladorException
	 */
	public BigDecimal calcularValorTotalDebitoConta(Collection<DebitoCobrado> colecaoDebitoCobrado, Map<String, String[]> requestMap)
					throws ControladorException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 10/01/2006
	 * Cálcula o valor total dos créditos de uma conta de acordo com o informado
	 * pelo usuário
	 * 
	 * @param colecaoCreditoRealizado
	 * @param requestMap
	 * @return BigDecimal valorTotalCreditoConta
	 * @throws ControladorException
	 */
	public BigDecimal calcularValorTotalCreditoConta(Collection<CreditoRealizado> colecaoCreditoRealizado, Map<String, String[]> requestMap)
					throws ControladorException;

	/**
	 * [UC0145] - Inserir Conta Author: Raphael Rossiter 05/12/2005
	 * 
	 * @param mesAnoConta
	 * @param imovel
	 * @param colecaoDebitoCobrado
	 * @param ligacaoAguaSituacao
	 * @param ligacaoEsgotoSituacao
	 * @param colecaoCategoria
	 * @param consumoAgua
	 * @param consumoEsgoto
	 * @param percentualEsgoto
	 * @param dataVencimentoConta
	 * @param calcularValoresConta
	 * @param motivoInclusaoConta
	 * @throws ControladorException
	 */
	public Integer inserirConta(Integer mesAnoConta, Imovel imovel, Collection colecaoDebitoCobrado,
					LigacaoAguaSituacao ligacaoAguaSituacao, LigacaoEsgotoSituacao ligacaoEsgotoSituacao, Collection colecaoCategoria,
					String consumoAgua, String consumoEsgoto, String percentualEsgoto, Date dataVencimentoConta,
					Collection<CalcularValoresAguaEsgotoHelper> calcularValoresConta, ContaMotivoInclusao contaMotivoInclusao,
					Map<String, String[]> requestMap, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0147] - Cancelar Conta Author: Raphael Rossiter Data: 10/12/2005
	 * 
	 * @param colecaoContas
	 *            -
	 *            coleção com todas as contas do imóvel
	 * @param identificadores
	 *            -
	 *            identifica através do ID, quais as contas que serão canceladas
	 * @param motivoCancelamentoConta
	 *            -
	 *            motivo do cancelamento escolhido pelo usuário
	 */
	public void cancelarConta(Collection<Conta> colecaoContas, String identificadores, ContaMotivoCancelamento contaMotivoCancelamento,
					Usuario usuarioLogado, String numeroRA) throws ControladorException;

	/**
	 * [UC0148] - Colocar Conta em Revisão Author: Raphael Rossiter Data:
	 * 21/12/2005
	 * 
	 * @param colecaoContas
	 *            -
	 *            coleção com todas as contas do imóvel
	 * @param identificadores
	 *            -
	 *            identifica através do ID, quais as contas que serão colocadas
	 *            em revisão
	 * @param motivoRevisãoConta
	 *            -
	 *            motivo da revisão escolhida pelo usuário
	 */
	public void colocarRevisaoConta(Collection<Conta> colecaoContas, String identificadores, ContaMotivoRevisao contaMotivoRevisao,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * Colocar Conta em Revisão desconsiderando ou não a revisão anterior
	 * 
	 * @param colecaoContas
	 * @param identificadores
	 * @param contaMotivoRevisao
	 * @param usuarioLogado
	 * @param considerarContasRevisao
	 * @throws ControladorException
	 */
	public void colocarRevisaoConta(Collection<Conta> colecaoContas, String identificadores, ContaMotivoRevisao contaMotivoRevisao,
					Usuario usuarioLogado, boolean considerarContasRevisao) throws ControladorException;

	/**
	 * [UC0149] - Retirar Conta em Revisão Author: Raphael Rossiter Data:
	 * 22/12/2005
	 * 
	 * @param colecaoContas
	 *            -
	 *            coleção com todas as contas do imóvel
	 * @param identificadores
	 *            -
	 *            identifica através do ID, quais as contas que serão retiradas
	 *            de revisão
	 */
	public void retirarRevisaoConta(Collection<Conta> colecaoContas, String identificadores, Usuario usuario) throws ControladorException;

	/**
	 * [UC0151] - Alterar Vencimento de Conta Author: Raphael Rossiter Data:
	 * 22/12/2005
	 * 
	 * @param colecaoContas
	 *            -
	 *            coleção com todas as contas do imóvel
	 * @param identificadores
	 *            -
	 *            identifica através do ID, quais as contas que sofrerão
	 *            alteração na sua data de vencimento
	 * @param dataVencimento
	 *            -
	 *            a nova data de vencimento da conta
	 */
	public void alterarVencimentoConta(Collection<Conta> colecaoContas, String identificadores, Date dataVencimento, Usuario usuario)
					throws ControladorException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 26/12/2005
	 * 
	 * @param conta
	 * @return uma coleção com os débitos cobrados de uma conta
	 * @throws ControladorException
	 */
	public Collection<DebitoCobrado> obterDebitosCobradosConta(Conta conta) throws ControladorException;

	/**
	 * [UC0150] - Retificar Conta Author: Raphael Rossiter Data: 28/12/2005
	 * 
	 * @param conta
	 * @return uma coleção com os créditos realizados de uma conta
	 * @throws ControladorException
	 */
	public Collection<CreditoRealizado> obterCreditosRealizadosConta(Conta conta) throws ControladorException;

	/**
	 * @author eduardo henrique
	 * @date 15/02/2009
	 *       Alteração na assinatura do método para receber a instância da Conta criada na
	 *       Retificação , para atualizar dados de
	 *       debito automatico corretamente.
	 *       Solicitada a Inclusão de possibilidade de alteração de Dados de Leitura na Retificação
	 *       de Conta.
	 * @param mesAnoConta
	 * @param contaAtual
	 * @param imovel
	 * @param colecaoDebitoCobrado
	 * @param colecaoCreditoRealizado
	 * @param ligacaoAguaSituacao
	 * @param ligacaoEsgotoSituacao
	 * @param colecaoCategoria
	 * @param consumoAgua
	 * @param consumoEsgoto
	 * @param percentualEsgoto
	 * @param dataVencimentoConta
	 * @param calcularValoresConta
	 * @param contaMotivoRetificacao
	 * @param requestMap
	 * @param colecaoMedicaoHistorico
	 *            TODO
	 * @throws ControladorException
	 */
	public Integer retificarConta(Integer mesAnoConta, Conta contaAtual, Imovel imovel, Collection colecaoDebitoCobrado,
					Collection colecaoCreditoRealizado, LigacaoAguaSituacao ligacaoAguaSituacao,
					LigacaoEsgotoSituacao ligacaoEsgotoSituacao, Collection colecaoCategoria, String consumoAgua, String consumoEsgoto,
					String percentualEsgoto, Date dataVencimentoConta, Collection<CalcularValoresAguaEsgotoHelper> calcularValoresConta,
					ContaMotivoRetificacao contaMotivoRetificacao, Map<String, String[]> requestMap, Usuario usuarioLogado,
					Collection<MedicaoHistorico> colecaoMedicaoHistorico, ConsumoTarifa consumoTarifa, Cliente clienteResponsavelConta)
					throws ControladorException;

	/**
	 * @author Hiroshi Goncalves
	 * @date 26/11/2013 Adicionado a flag inRegistrarLancamentoContabilOrigem ao método original
	 *       para indicar se deverá ser registrado o lançamento contábil da Origem do Pagamento.
	 * @param anoMesConta
	 * @param contaAtual
	 * @param imovel
	 * @param colecaoDebitoCobrado
	 * @param colecaoCreditoRealizado
	 * @param ligacaoAguaSituacao
	 * @param ligacaoEsgotoSituacao
	 * @param colecaoCategoria
	 * @param consumoAgua
	 * @param consumoEsgoto
	 * @param percentualEsgoto
	 * @param dataVencimentoConta
	 * @param calcularValoresConta
	 * @param contaMotivoRetificacao
	 * @param requestMap
	 * @param colecaoMedicaoHistorico
	 * @param inRegistrarLancamentoContabilOrigem
	 *            Colecao de MedicoesHistorico(Leituras) que serao alteradas, referentes à conta
	 *            retificada.
	 * @throws ControladorException
	 */
	public Integer retificarConta(Integer anoMesConta, Conta contaAtual, Imovel imovel, Collection colecaoDebitoCobrado,
					Collection colecaoCreditoRealizado, LigacaoAguaSituacao ligacaoAguaSituacao,
					LigacaoEsgotoSituacao ligacaoEsgotoSituacao, Collection colecaoCategoria, String consumoAgua, String consumoEsgoto,
					String percentualEsgoto, Date dataVencimentoConta, Collection<CalcularValoresAguaEsgotoHelper> calcularValoresConta,
					ContaMotivoRetificacao contaMotivoRetificacao, Map<String, String[]> requestMap, Usuario usuarioLogado,
					Collection<MedicaoHistorico> colecaoMedicaoHistorico, ConsumoTarifa consumoTarifa,
					boolean inRegistrarLancamentoContabilOrigem, Cliente clienteResponsavelConta) throws ControladorException;

	/**
	 * @author Carlos Chrystian
	 * @date 31/07/2012
	 *       [UC0146 - ManterConta]
	 *       [SB0003] - Retificar Conta
	 *       Caucionar Conta
	 * @throws ControladorException
	 */
	public Map<Conta, Collection<Collection<ContaCategoriaConsumoFaixa>>> caucionarConta(Integer anoMesConta, Conta contaAtual,
					Imovel imovel, Collection colecaoDebitoCobrado, Collection colecaoCreditoRealizado,
					LigacaoAguaSituacao ligacaoAguaSituacao, LigacaoEsgotoSituacao ligacaoEsgotoSituacao, Collection colecaoCategoria,
					String consumoAgua, String consumoEsgoto, String percentualEsgoto, Date dataVencimentoConta,
					Collection<CalcularValoresAguaEsgotoHelper> calcularValoresConta, ContaMotivoRevisao contaMotivoRevisao,
					Map<String, String[]> requestMap, Usuario usuarioLogado, Collection<MedicaoHistorico> colecaoMedicaoHistorico,
					ConsumoTarifa consumoTarifa, Collection<Conta> colecaoContaImovel) throws ControladorException;

	/**
	 * [UC0183 - Inserir Débito A Cobrar] Author: Rafael Santos Data: 23/12/2005
	 * 
	 * @param debitoACobrar
	 *            DebitoACobrar
	 * @throws ControladorException
	 */
	Integer inserirDebitoACobrar(Integer numeroPrestacoes, DebitoACobrar debitoACobrar, BigDecimal valorTotalServico, Imovel imovel,
					BigDecimal percentualAbatimento, BigDecimal valorEntrada, Usuario usuarioLogado, boolean efetuarParcelamento,
					Integer numeroMesesEntreParcelas, Integer numeroParcelasALancar, Integer numeroMesesInicioCobranca)
					throws ControladorException;

	/**
	 * [UC0183 - Inserir Débito A Cobrar] sem registrar o lançamento contabil
	 * 
	 * @param numeroPrestacoes
	 * @param debitoACobrar
	 * @param valorTotalServico
	 * @param imovel
	 * @param percentualAbatimento
	 * @param valorEntrada
	 * @param usuarioLogado
	 * @param efetuarParcelamento
	 * @param numeroMesesEntreParcelas
	 * @param numeroParcelasALancar
	 * @param numeroMesesInicioCobranca
	 * @return
	 * @throws ControladorException
	 */
	DebitoACobrar inserirDebitoACobrarSemRegistrarLancamentoContabil(Integer numeroPrestacoes, DebitoACobrar debitoACobrar,
					BigDecimal valorTotalServico, Imovel imovel, BigDecimal percentualAbatimento, BigDecimal valorEntrada,
					Usuario usuarioLogado, boolean efetuarParcelamento, Integer numeroMesesEntreParcelas, Integer numeroParcelasALancar,
					Integer numeroMesesInicioCobranca) throws ControladorException;

	/**
	 * [UC0186 - Calcular Prestação] Author: Rafael Santos Data: 23/12/2005
	 * 
	 * @param taxaJurosFinanciamento
	 *            Taxa de Juros do Financiamento
	 * @param numeroPrestacoes
	 *            Numero de Prestacoes
	 * @param valorTotalServico
	 *            Valor Total de Servico
	 * @param valorEntrada
	 *            Valor de Entrada
	 * @param percentualAbatimento
	 *            PErcentual Abatimento
	 * @return O valor da Prestacao
	 */
	public ArrayList calcularValorPrestacao(BigDecimal taxaJurosFinanciamento, Integer numeroPrestacoes, BigDecimal valorTotalServico,
					BigDecimal valorEntrada, BigDecimal percentualAbatimento, String idTipoDebito, BigDecimal valorTotalServicoAParcelar,
					Imovel imovel, Usuario usuario) throws ControladorException;

	/**
	 * [UC0183 - Inserir Débito A Cobrar] Author: Rafael Santos Data: 29/12/2005
	 * Inserir Debito A Cobrar por Categoria
	 * 
	 * @param debitoACobrar
	 *            Debito A Cobrar
	 */
	public void inserirDebitoACobrarCategoria(DebitoACobrar debitoACobrar, Imovel imovel) throws ControladorException;

	/**
	 * [US0184] Manter Débito A Cobrar Author: Rafael Santos Data: 30/12/2005
	 * 
	 * @param registrarTransacao
	 *            TODO
	 * @param idsLista
	 *            de Id de Débito a Cobrar
	 * @throws ControladorException
	 */
	public void cancelarDebitoACobrar(String[] ids, Usuario usuarioLogado, Integer matriculaImovel, Boolean registrarTransacao)
					throws ControladorException;

	/**
	 * Remover Tarifa de Consumo
	 * 
	 * @throws ControladorException
	 */

	public void removerTarifaConsumo(String[] ids) throws ControladorException;

	/**
	 * [UC0168] - Inserir Tarifa de Consumo
	 * 
	 * @param consumoTarifa
	 * @param consumoTarifaVigencia
	 * @param colecaoConsumoTarifaCategoria
	 * @param colecaoConsumoTarifaFaixa
	 * @throws ControladorException
	 */
	public void inserirConsumoTarifa(ConsumoTarifa consumoTarifa, ConsumoTarifaVigencia consumoTarifaVigencia,
					Collection<ConsumoTarifaCategoria> colecaoConsumoTarifaCategoria) throws ControladorException;

	public void atualizarConsumoTarifa(ConsumoTarifaVigencia consumoTarifaVigencia,
					Collection<CategoriaFaixaConsumoTarifaHelper> colecaoCategoriaFaixaConsumoTarifaHelper) throws ControladorException;

	/**
	 * [UC0145] - Inserir Conta Author: Raphael Rossiter Data: 12/01/2006
	 * Cálcula o valor total de água ou esgoto
	 * 
	 * @param calcularValoresAguaEsgotoHelper
	 * @param tipoRetorno
	 * @return valorTotalAguaOuEsgoto
	 */
	public BigDecimal calcularValorTotalAguaOuEsgotoPorCategoria(
					Collection<CalcularValoresAguaEsgotoHelper> calcularValoresAguaEsgotoHelper, String tipoRetorno)
					throws ControladorException;

	/**
	 * [UC0187] - Inserir Guia de Pagamento
	 * 
	 * @author Rafael Corrêa, Pedro Alexandre
	 * @since 16/01/2006, 23/11/2006
	 * @author eduardo henrique
	 * @date 04/08/2008
	 *       Alterações realizadas no UC, basicamente incluído o conceito de Prestações de Guia de
	 *       Pagamento
	 * @param guiaPagamento
	 *            GuiaPagamento
	 * @param usuarioLogado
	 * @param dataVencimento
	 *            TODO
	 * @throws ControladorException
	 */
	public Integer inserirGuiaPagamento(GuiaPagamento guiaPagamento, Usuario usuarioLogado, String dataVencimento,
					Integer qtdeDiasVencimento, Collection<GuiaPagamentoPrestacaoHelper> colecaoPrestacoesGuiaPagamento,
					Collection<ListaDadosPrestacaoGuiaHelper> colecaoListaDadosPrestacoesGuia, String numeroContratoParcelOrgaoPublico)
					throws ControladorException;

	/**
	 * [UC0188] - Manter Guia de Pagamento
	 * 
	 * @author Rafael Corrêa, Pedro Alexandre
	 * @param usuarioLogado
	 * @since 16/01/2006, 23/11/2006
	 * @author eduardo henrique
	 * @date 07/08/2008
	 *       Alterações no UC, [SB0001] - Cancelar Guia Pagamento para a v0.04.
	 * @throws ControladorException
	 */
	public void cancelarGuiaPagamento(Collection<GuiaPagamentoPrestacaoHelper> guiasPagamento, String[] registrosRemocao,
					boolean indicadorContabilizar, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Verifica se o Mês/Ano informado é inferior ao Mês/Ano do Sistema
	 * 
	 * @param anoMesFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarReferenciaFaturamentoCorrente(String anoMesFaturamento) throws ControladorException;

	/**
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Rhawi Dantas
	 * @param registradorOperacao
	 * @created 18/01/2006
	 */
	public void inserirFaturamentoSituacaoHistorico(Collection collectionFaturamentoSituacaoHistorico,
					RegistradorOperacao registradorOperacao) throws ControladorException;

	/**
	 * Consulta ResumoFaturamento para a geração do relatório '[UC0173] Gerar
	 * Relatório de Resumo Faturamento' de acordo com a opção de totalização.
	 * 
	 * @author Rodrigo Silveira
	 * @created 18/01/2006
	 * @param opcaoTotalizacao
	 * @return
	 */
	public Collection consultarResumoFaturamentoRelatorio(String opcaoTotalizacao, int anoMesReferencia, Integer gerenciaRegional,
					Integer localidade, Integer unidadeNegocio) throws ControladorException;

	/**
	 * [UC0194] - Inserir Crédito a Realizar
	 * 
	 * @author Roberta Costa
	 * @since 12/01/2006
	 * @param creditoARealizar
	 *            CreditoARealizar
	 * @throws ControladorException
	 */
	public void inserirCreditoARealizar(Imovel imovel, CreditoARealizar creditoARealizaro, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0195] - Manter Crédito a Realizar Permite Cancelar um ou mais créditos
	 * a realizar de um determinado imóvel
	 * 
	 * @author Roberta Costa
	 * @since 18/01/2006
	 * @param creditoARealizar
	 *            CreditoARealizar
	 * @throws ControladorException
	 */
	public void cancelarCreditoARealizar(String[] ids, Imovel imovel, Usuario usuarioLogado, boolean ignorarValidacaoPermissaoEspecial)
					throws ControladorException;

	/**
	 * [UC0146] - Manter Conta Author: Raphael Rossiter Data: 21/01/2006
	 * Obtém as contas de um imóvel que poderão ser mantidas
	 * 
	 * @author Saulo Lima
	 * @date 10/09/2008
	 *       Alteração na chamada do método pra satifazer as especificações do UC0146 (ADA):
	 *       Remover os parâmetros: situacaoNormal, situacaoIncluida e situacaoRetificada
	 * @param imovel
	 * @return Collection<Conta>
	 * @throws ControladorException
	 */
	public Collection<Conta> obterContasImovelManter(Imovel imovel) throws ControladorException;

	/**
	 * [UC0146] - Manter Conta
	 * Obtém as contas de um imóvel que poderão ser mantidas
	 * 
	 * @author Hugo Lima
	 * @date 09/05/2012
	 * @param manterContaHelper
	 * @return Collection<Conta>
	 * @throws ControladorException
	 */
	public Collection<Conta> obterContasImovelManterParametros(ManterContaHelper manterContaHelper) throws ControladorException;

	/**
	 * [UC0302] - Gerar Debitos A Cobrar de Acrescimos por Impontualidade
	 * Author: Fernanda Paiva Data: 24/04/2006
	 * Obtém as contas de um imóvel com ano/mes da data de vencimento menor ou
	 * igual ao ano/mes de referencia da arrecadacao corrente e com situacao
	 * atual correspondente a normal, retificada ou incluida.
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterContasImovel(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida, Integer situacaoRetificada,
					Integer anoMesReferenciaArrecadacao) throws ControladorException;

	/**
	 * [UC0302] - Gerar Debitos A Cobrar de Acrescimos por Impontualidade
	 * Author: Fernanda Paiva Data: 24/04/2006
	 * Obtém as guias de pagamento de um imóvel com ano/mes da data de
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
	 * @throws ControladorException
	 */
	public Collection obterGuiasPagamentoImovel(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer anoMesReferenciaArrecadacao) throws ControladorException;

	/**
	 * Encerra o faturamento do mês
	 * [UC0155] - Encerrar Faturamento do Mês
	 * 
	 * @author Pedro Alexandre
	 * @date 07/10/2006
	 * @throws ControladorException
	 */
	public void encerrarFaturamentoMes(Collection<Integer> colecaoIdsLocalidades, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0167] - Obter Valor a Cobrar de Curto e Longo Prazo Author: Pedro
	 * Alexandre Data: 10/01/2006
	 * 
	 * @param numeroPrestacoes
	 *            nº de prestações
	 * @param numeroPrestacoesCobradas
	 *            nº de prestações cobradas
	 * @param valorCategoria
	 *            valor da categoria
	 * @throws ControladorException
	 */
	public BigDecimal[] obterValorACobrarDeCurtoELongoPrazo(short numeroPrestacoes, short numeroPrestacoesCobradas,
					BigDecimal valorCategoria) throws ControladorException;

	/**
	 * [UC0155] - Encerrar Faturamento do Mês [SB0002] - Obter Diferenças de
	 * Valores de Parcelamentos de Conta Retificada Author: Pedro Alexandre
	 * Data: 20/01/2006
	 * 
	 * @param anoMesReferencia
	 *            ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            código da localidade
	 * @param idCategoria
	 *            código da categoria
	 * @param tipoFinaciamento
	 *            tipo de financiamento
	 * @throws ControladorException
	 */
	public BigDecimal obterDiferencaValoresParcelamentoIndiretosContaRetificada(int anoMesReferencia, int idLocalidade, int idCategoria,
					Integer tipoFinaciamento) throws ControladorException;

	/**
	 * [UC0155] - Encerrar Faturamento do Mês [SB0001] - Obter Diferenças de
	 * Valores de Serviços Indiretos de Conta Retificada Author: Pedro Alexandre
	 * Data: 20/01/2006
	 * 
	 * @param anoMesReferencia
	 *            ano e mês de referência do faturamento
	 * @param idLocalidade
	 *            código da localidade
	 * @param idCategoria
	 *            código da categoria
	 * @param tipoFinaciamento
	 *            tipo de financiamento
	 * @throws ControladorException
	 */
	public BigDecimal obterDiferencaValoresServicoIndiretosContaRetificada(int anoMesReferencia, int idLocalidade, int idCategoria,
					Integer tipoFinaciamento, Integer itemContabil) throws ControladorException;

	/**
	 * @param colecaoCalcularValoresAguaEsgotoHelper
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CalcularValoresAguaEsgotoHelper> calcularValoresAguaEsgotoTotalizandoPorCategoria(
					Collection colecaoCalcularValoresAguaEsgotoHelper) throws ControladorException;

	/**
	 * Obtem os Debitos A Cobrar Categoria do Debito a Cobrar
	 * 
	 * @param debitoACobrarID
	 *            Id do Debito A Cobrar
	 * @return Coleção de Debitos a Cobrar Categoria
	 */
	public Collection obterDebitoACobrarCategoria(Integer debitoACobrarID) throws ControladorException;

	/**
	 * Obtem os Debitos A Cobrar do Imovel
	 * 
	 * @param imovelID
	 *            Id do Imovel
	 * @param debitoCreditoSituacaoAtualID
	 *            ID do Debito Credito Situação
	 * @return Coleção de Debitos a Cobrar
	 */
	public Collection obterDebitoACobrarImovel(Integer imovelID, Integer debitoCreditoSituacaoAtualID, int anoMesFaturamento)
					throws ControladorException;

	/**
	 * Obtem os Credito A Realizar do Imovel
	 * 
	 * @param imovelID
	 *            Id do Imovel
	 * @return Coleção de Creditos a Realizar
	 */
	public Collection<CreditoARealizar> obterCreditoARealizarImovel(Integer imovelID) throws ControladorException;

	/**
	 * Obtem os Creditos Realizado Categoria
	 * 
	 * @param creditoARealizarID
	 *            Id do Creditoa A Realizar
	 * @return Coleção de Creditos Realizados Categoria
	 */
	public Collection obterCreditoRealizarCategoria(Integer creditoARealizarID) throws ControladorException;

	/**
	 * Determina o Vencimento da Conta
	 * 
	 * @param imovel
	 *            Imovel
	 * @return Data do Vencimento da Conta
	 */
	// public Date determinarVencimentoConta(Imovel imovel);
	/**
	 * [UC0113] - Gerar Faturamento Grupo [SF007] - Determinar Vencimento da
	 * Conta Determina o Vencimento da Conta
	 * 
	 * @param usuarioLogado
	 * @param imovel
	 *            Imovel
	 * @return Data do Vencimento da Conta
	 */
	/*
	 * public Date determinarVencimentoConta(Imovel imovel, Rota rota) throws
	 * ControladorException;
	 */

	public void removerFaturamentoCronograma(String[] ids, String pacoteNomeObjeto, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Pesquisa a conta do imóvel com a referência informada pelo usuário
	 * [FS0012] - Verificar existência da conta
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @param referenciaConta
	 * @return
	 * @throws ControladorException
	 */
	public Conta pesquisarContaDigitada(String idImovel, String referenciaConta) throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Pesquisa o tipo de débito informado pelo usuário
	 * [FS0020] - Verificar existência do tipo de débito
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idTipoDebitoDigitado
	 * @return
	 * @throws ControladorException
	 */
	public DebitoTipo pesquisarTipoDebitoDigitado(Integer idTipoDebitoDigitado) throws ControladorException;

	/**
	 * Permite executar as atividades do faturamento previamente comandadas
	 * [UC0111] Executar Atividade do Faturamento
	 * Lista as atividades de faturamento do cronograma que foram comandadas
	 * obterAtividadesFaturamentoCronogramaComandada
	 * 
	 * @author Raphael Rossiter, Roberta Costa
	 * @date 29/03/2006, 29/04/20004
	 * @return Collection<ExecutarAtividadeFaturamentoHelper>
	 * @throws ControladorException
	 */
	public Collection<ExecutarAtividadeFaturamentoHelper> obterAtividadesFaturamentoCronogramaComandada(Integer numeroPagina)
					throws ControladorException;

	/**
	 * Inserir Debito A Cobrar para o Imovel
	 * [UC0183] - Inserir Debito a Cobrar
	 * 
	 * @author Rafael Santos
	 * @date 01/04/2006
	 * @param idDebitoTipo
	 * @return
	 */
	public DebitoTipo pesquisarDebitoTipo(String idDebitoTipo) throws ControladorException;

	/**
	 * [UC0186 - Calcular Prestação] Author: Rafael Santos Data: 03/04/2006
	 * 
	 * @param taxaJurosFinanciamento
	 *            Taxa de Juros do Financiamento
	 * @param numeroPrestacoes
	 *            Numero de Prestacoes
	 * @param valorTotalServico
	 *            Valor Total de Servico
	 * @param valorEntrada
	 *            Valor de Entrada
	 * @return O valor da Prestacao
	 */
	public BigDecimal calcularPrestacao(BigDecimal taxaJurosFinanciamento, Integer numeroPrestacoes, BigDecimal valorTotalServico,
					BigDecimal valorEntrada) throws ControladorException;

	public void reajustarTarifaConsumo(Map<ConsumoTarifaVigencia, Map<ConsumoTarifaCategoria, BigDecimal>> mapReajuste)
					throws ControladorException;

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador
	 * @date 03/04/2006
	 * @param rotas
	 * @throws ControladorException
	 */
	public void gerarTaxaEntregaDeContaEmOutroEndereco(Collection<Rota> rotas, Integer anoMes, int idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * O sistema seleciona os grupos de faturamento que possuem cronograma para
	 * o mês corrente * [UC0144] Inserir Comando Atividade de Faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 03/03/2006
	 * @return Collection<FaturamentoGrupo>
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoGrupo> pesquisarFaturamentoGrupoComCronogramaMensalParaMesCorrente() throws ControladorException;

	/**
	 * O sistema seleciona os grupos de faturamento que possuem cronograma para
	 * o mês corrente
	 * [UC0104] Manter Comando Atividade de Faturamento
	 * Caso esteja no atualizar pode escolher todos os grupos exceto o
	 * selecionado para atualização
	 * pesquisarFaturamentoGrupoComCronogramaMensalParaMesCorrenteSemGupoSelecionado
	 * 
	 * @author Roberta Costa
	 * @date 20/07/2006
	 * @return Collection<FaturamentoGrupo>
	 * @throws ControladorException
	 */
	public Collection<FaturamentoGrupo> pesquisarFaturamentoGrupoComCronogramaMensalParaMesCorrenteSemGupoSelecionado(
					Integer grupoSelecionado) throws ControladorException;

	/**
	 * Utilizado pelo [UC0302] Gerar Débitos a Cobrar de Acréscimos por
	 * Impontualidade
	 * 
	 * @author fernanda paiva
	 * @date 20/04/2006
	 * @param anoMes
	 * @return true se debitos foram gerados e false se não foram gerados debitos
	 */
	@Deprecated
	public boolean gerarDebitosACobrarDeAcrescimosPorImpontualidadeOLD(Pagamento pagamento) throws ControladorException;

	/**
	 * @author Saulo Lima
	 * @date 22/08/2012
	 *       Método criado para chamar a Function do Banco de Dados.
	 * @param pagamento
	 * @throws ControladorException
	 */
	public Collection<Integer> gerarDebitosACobrarDeAcrescimosPorImpontualidadeBancoDeDados(Pagamento pagamento, Date dataEmissaoDocumento)
					throws ControladorException;

	/**
	 * Metodo que retorna os imóveis das quadras pertencentes às rotas
	 * Utilizado pelo [UC0302] Gerar Débitos a Cobrar de Acréscimos por
	 * Impontualidade
	 * 
	 * @author fernanda paiva
	 * @date 20/04/2006
	 * @param anoMes
	 * @return
	 */
	public Collection gerarDebitosACobrarDeAcrescimosPorImpontualidade(Collection rotas, int idFuncionalidadeIniciada,
					boolean indicadorEncerrandoArrecadacao) throws ControladorException;

	/**
	 * [UC0320] - Gerar Fatura Cliente Responsável
	 * Gera fatura para os clientes e respectivas contas selecionadas pelo usuário
	 * 
	 * @author Saulo Lima
	 * @date 17/09/2008
	 * @author Luciano Galvao
	 * @date 28/06/2013
	 */
	public Collection<Fatura> gerarFaturaClienteResponsavel(Map<Cliente, Collection<Conta>> mapClienteContas, Date dataVencimento)
					throws ControladorException;

	public void inserirMensagemConta(ContaMensagem contaMensagem, String[] setorComercial) throws ControladorException;

	/**
	 * Permite executar as atividades do faturamento previamente comandadas
	 * [UC0111] Executar Atividade do Faturamento
	 * Executa as atividade do Faturamento
	 * executarAtividadeFaturamento
	 * 
	 * @author Roberta Costa
	 * @date 03/05/20006
	 * @param idsFaturamentoAtividadeCronograma
	 * @throws ControladorException
	 */
	public void executarAtividadeFaturamento(String[] idsFaturamentoAtividadeCronograma) throws ControladorException;

	/**
	 * Metódo responsável gerar os objetos do tipo FaturaClienteResponsavelHelper
	 * que serão usados no [UC0321] - Emitir Fatura de Cliente Responsável
	 * 
	 * @author Saulo Lima
	 * @date 23/09/2008
	 * @param Collection
	 *            <Integer>
	 *            Colecção de Inteiros com os Ids das faturas que serão impressas.
	 * @return Collection<FaturaClienteResponsavelHelper>
	 *         Coleção de FaturaClienteResponsavelHelper
	 * @throws ControladorException
	 */
	public Collection<FaturaClienteResponsavelHelper> gerarColecaoFaturaClienteResponsavelHelper(Collection<Integer> idsFaturas)
					throws ControladorException;

	/**
	 * Metódo responsável por emitir as faturas geradas pelo [UC0320]
	 * [UC0321] Emitir Fatura de Cliente Responsável
	 * 
	 * @author Pedro Alexandre, Pedro Alexandre, Pedro Alexandre, Saulo Lima
	 * @date 27/04/2006, 03/01/2007, 15/03/2007, 19/09/2008
	 * @param colecaoFatura
	 * @throws ControladorException
	 */
	public void emitirFaturaClienteResponsavel(Collection<Fatura> colecaoFatura, Integer anoMesFaturamentoCorrente)
					throws ControladorException;

	/**
	 * [UC0329] - Restabelecer Situação Anterior de Conta
	 * 
	 * @author Fernanda Paiva
	 * @date 05/05/2006
	 * @param registrosRemocao
	 * @throws ControladorException
	 */
	public void restabelecerSituacaoAnteriorConta(Collection colecaoContas, Usuario usuario) throws ControladorException;

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
	public Integer pesquisarFaturamentoAtividadeCronogramaComandadaNaoRealizadaCount() throws ControladorException;

	public void emitirContasOrgaoPublico(Integer anoMesReferenciaFaturamento, FaturamentoGrupo faturamentoGrupo,
					int idFuncionalidadeIniciada, int tipoConta, Integer idEmpresa, Short indicadorEmissaoExtratoFaturamento)
					throws ControladorException;

	public void atualizarMensagemConta(ContaMensagem contaMensagem) throws ControladorException;

	/**
	 * Pesquisa todas as contas para testar o batch
	 * 
	 * @author Sávio Luiz
	 * @date 02/06/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdsTodasConta() throws ControladorException;

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
					String numeroMoradoresInicial, String numeroMoradoresFinal, String idAreaConstruidaFaixa, int anoMesReferencia

	) throws ControladorException;

	public Collection<FaturamentoAtividadeCronograma> pesquisarRelacaoAtividadesGrupo(Integer faturamentoGrupoId)
					throws ControladorException;

	/**
	 * O método recebe uma coleção de faturamento atividades acha as que tem
	 * atividade predecessora e compara a data desta com a data da sua
	 * predecessora.
	 * 
	 * @param faturamentoAtividadeCronogramas
	 *            Descrição do parâmetro
	 * @param faturamentoGrupoCronogramaMensal
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public void validarFaturamentoCronograma(Collection faturamentoAtividadeCronogramas) throws ControladorException;

	/**
	 * [UC0169] Manter Taraifa de Consumo Prepara a Vigência para Ser reajustada
	 * 
	 * @author Rafel Santos
	 * @date 21/07/2006
	 */
	public void iniciarProcessoReajustarTarifaConsumo(Map listaParametrosValoresCategoria, Date dataNovaVigencia, String[] idsRecuperados)
					throws ControladorException;

	/**
	 * Metodo para validar: Caso usuário informe uma data prevista, de qualquer
	 * atividade, com o mês/ano maior que o mês ]/ano do cronograma+1, exibir a
	 * mensagem: "A data prevista da atividade nã pode ser superior a <<mês/ano
	 * do cronograma+1>>"
	 * 
	 * @param faturamentoAtividadeCronogramas
	 *            Descrição do parâmetro
	 * @param mesAno
	 * @throws ControladorException
	 */
	public void validarFaturamentoCronogramaAtividadeMaiorQueMesAnoCronograma(int anoMes, Collection faturamentoAtividadeCronogramas)
					throws ControladorException;

	public Integer calcularConsumoTotalAguaOuEsgotoPorCategoria(
					Collection<CalcularValoresAguaEsgotoHelper> calcularValoresAguaEsgotoHelper, String tipoRetorno)
					throws ControladorException;

	/**
	 * Este caso de uso calcula a tarifa miníma de água para um imóvel
	 * [UC0451] Obter Tarifa Miníma de Água para um Imóvel
	 * 
	 * @author Roberta Costa
	 * @date 09/08/2006
	 * @param imovel
	 * @param colecaoCategorias
	 * @return BigDecimal
	 * @throws ControladorException
	 */
	public BigDecimal obterTarifaMinimaAguaImovel(Imovel imovel) throws ControladorException;

	/**
	 * Este caso de uso inicia um processo para o mecanismo batch
	 * [UC0111] - Iniciar Processo
	 * Este subfluxo tem o papel de iniciar um processo de faturamento
	 * comandado, neste método é feita uma busca para obter as atividades
	 * comandadas e não realizadas
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Rodrigo Silveira
	 * @date 14/08/2006
	 * @return
	 * @throws ControladorException
	 */

	public Collection<FaturamentoAtividadeCronograma> pesquisarFaturamentoAtividadeCronogramaComandadasNaoRealizadas(int numeroPagina)
					throws ControladorException;

	/**
	 * Este caso de uso inicia um processo para o mecanismo batch
	 * [UC0111] - Iniciar Processo
	 * Este subfluxo tem o papel de iniciar um processo de faturamento
	 * comandado, neste método é feita uma busca para obter as atividades
	 * comandadas e não realizadas
	 * [SB0001] - Iniciar Processo de Faturamento Comandado
	 * 
	 * @author Rodrigo Silveira
	 * @date 14/08/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public int pesquisarFaturamentoAtividadeCronogramaComandadasNaoRealizadasCount() throws ControladorException;

	/**
	 * Pesquisa a existencia de uma conta pelo id da conta e pela data da ultima alteracao
	 * 
	 * @author Saulo Lima
	 * @date 31/01/2009
	 *       Alteração no tipo do parâmetro 'ultimaAlteracao' para Date
	 * @param String
	 *            Id da Conta pesquisada
	 * @param Date
	 *            Data da última alteração
	 * @return Integer
	 *         Id da Conta localizada
	 * @throws ControladorException
	 */
	@Deprecated
	public Integer pesquisarExistenciaContaParaConcorrencia(String idConta, Date ultimaAlteracao) throws ControladorException;

	/**
	 * Pesquisa a existencia de um debito tipo pelo id
	 * 
	 * @param id
	 *            Descrição do parâmetro
	 * @param ultimaAlteracao
	 *            Descrição do parâmetro
	 * @throws ControladorException
	 */
	public Integer verificarExistenciaDebitoTipo(Integer idDebitoTipo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 01/08/2006
	 */
	public DebitoTipo pesquisarDebitoTipo(Integer idDebitoTipo) throws ControladorException;

	/**
	 * [UC0410] - Inserir Tipo de Serviço
	 * 
	 * @author lms
	 * @date 07/08/2006
	 */
	public CreditoTipo pesquisarCreditoTipo(Integer idCreditoTipo) throws ControladorException;

	/**
	 * Obtem dados da conta
	 * 
	 * @param idConta
	 *            Id da Conta
	 * @author Fernanda Paiva, eduardo henrique
	 * @date 17/07/2009
	 *       Alteracao na assinatura do método para retorno de uma instancia de conta,
	 *       com seus principais dados atribuídos.
	 * @return Conta
	 */
	public Conta consultarConta(Integer idConta) throws ControladorException;

	/**
	 * Pesquisa a soma dos valores das multas cobradas para a conta.
	 * 
	 * @author Pedro Alexandre
	 * @date 19/09/2006
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */
	public BigDecimal pesquisarValorMultasCobradas(int idConta) throws ControladorException;

	/**
	 * [UC0482]Emitir 2ª Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 15/09/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public Collection<EmitirContaHelper> emitir2ViaContas(Collection idsContaEP, boolean cobrarTaxaEmissaoConta, Short contaSemCodigoBarras)
					throws ControladorException;

	/**
	 * Recupera o id do cliente responsável pela conta
	 * [UC0348] - Emitir Contas [UC0482]Emitir 2ª Via de Conta
	 * 
	 * @author Sávio Luiz, Vivianne Sousa
	 * @date 15/05/2006 , 22/05/2007
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarIdClienteResponsavelConta(Integer idConta, boolean contaHistorico) throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB0002] Determinar tipo de ligação e tipo de medição
	 * 
	 * @author Sávio Luiz
	 * @date 15/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public Integer[] determinarTipoLigacaoMedicao(EmitirContaHelper emitirContaHelper) throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB0003] Obter Dados do Consumo e Medicao Anterior
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public StringBuilder obterDadosConsumoAnterior(Integer idImovel, int anoMes, int qtdMeses, Integer tipoLigacao, Integer tipoMedicao)
					throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB0004] Obter Dados da Medição da Conta
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public Object[] obterDadosMedicaoConta(EmitirContaHelper emitirContaHelper, Integer tipoMedicao) throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB0005] Obter Consumo Faturado e Consumo médio Diário
	 * 
	 * @author Sávio Luiz
	 * @date 17/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public String[] obterConsumoFaturadoConsumoMedioDiario(EmitirContaHelper emitirContaHelper, Integer tipoMedicao, String diasConsumo)
					throws ControladorException;

	/**
	 * Método que retorna a soma de quantidade economia
	 * [UC0348] Emitir Contas [UC0482]Emitir 2ª Via de Conta
	 * [SB0007] Obter Quantidade de Economias da Conta
	 * 
	 * @author Sávio Luiz, Vivianne Sousa
	 * @date 19/05/2006, 22/05/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short obterQuantidadeEconomiasConta(Integer idConta, boolean contaHistorico) throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB0009] Obter Mensagem de Rateio de Consumo ou Consumo fixo de Esgoto
	 * 
	 * @author Sávio Luiz
	 * @date 19/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public StringBuilder obterMensagemRateioConsumo(EmitirContaHelper emitirContaHelper, String consumoRateio,
					Object[] parmsMedicaoHistorico, Integer tipoMedicao) throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB00016] Obter Mensagem da Conta em 3 Partes
	 * 
	 * @author Sávio Luiz
	 * @date 24/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public String[] obterMensagemConta3Partes(EmitirContaHelper emitirContaHelper, SistemaParametro sistemaParametro)
					throws ControladorException;

	/**
	 * Método que retorna uma array de object de qualidade de agua
	 * [UC0348] Emitir Contas
	 * 
	 * @author Sávio Luiz
	 * @date 25/05/2006
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarParmsQualidadeAgua(EmitirContaHelper emitirContaHelper) throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB00018] Gerar Linhas das Contas com Débito Automático
	 * 
	 * @author Sávio Luiz
	 * @date 24/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public StringBuilder[] gerarLinhasDemaisContas(EmitirContaHelper emitirContaHelper, Integer sequencialEmpresa, BigDecimal valorConta)
					throws ControladorException;

	/**
	 * Metódo responsável por emitir os txts das contas.
	 * [UC0348] Emitir Contas
	 * [SB00010] Gerar Linhas da Descrição dos Serviços e Tarifas
	 * 
	 * @author Sávio Luiz
	 * @date 26/05/2006
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public StringBuilder gerarLinhasDescricaoServicoTarifas(EmitirContaHelper emitirContaHelper, String consumoRateio,
					Object[] parmsMedicaoHistorico, Integer tipoMedicao) throws ControladorException;

	/**
	 * [UC0351 - Calcular Impostos Deduzidos da Conta] Author: Rafael Santos
	 * Data: 21/09/2006
	 * 
	 * @param idImovel
	 *            Id do Imóvel
	 * @param anoMesReferencia
	 *            Ano/Mês de Referência
	 * @param valorAgua
	 *            Valor da Água
	 * @param valorEsgoto
	 *            Valor do Esgoto
	 * @param valorDebito
	 *            Valor dos Débitos Cobrados
	 * @param valorCredito
	 *            Valor dos Créditos Realizados
	 * @return O valor da Prestacao e a lista de Impostos
	 */
	public GerarImpostosDeduzidosContaHelper gerarImpostosDeduzidosConta(Integer idImovel, Integer anoMesReferencia, BigDecimal valorAgua,
					BigDecimal valorEsgoto, BigDecimal valorDebito, BigDecimal valorCredito) throws ControladorException;

	/**
	 * [UC0150] - Retificar Conta Author: Fernanda Paiva Data: 25/09/2006
	 * Inseri na tabela ClienteConta os dados referentes aos clientes do imovel
	 * 
	 * @param conta
	 * @param colecaoCreditoRealizado
	 * @param imovel
	 * @param colecaoCategoria
	 * @throws ControladorException
	 */
	public void inserirClienteImovel(Imovel imovel, Conta contaAtual) throws ControladorException;

	/**
	 * [UC0150] - Retificar Conta Author: Fernanda Paiva Data: 25/09/2006
	 * Inseri na tabela impostos deduzidos da conta
	 * 
	 * @param conta
	 * @param colecaoCreditoRealizado
	 * @param imovel
	 * @param colecaoCategoria
	 * @throws ControladorException
	 */
	public void inserirImpostosDeduzidosConta(GerarImpostosDeduzidosContaHelper impostosDeduzidosConta, Conta contaAtual,
					boolean indicadorOperacaoCaucionamento) throws ControladorException;

	/**
	 * Permite gerar os débitos de doações para os imóveis contidos na coleção
	 * [UC0394] Gerar Débitos a Cobrar de Doações
	 * 
	 * @author César Araújo, Raphael Rossiter
	 * @date 05/08/2006
	 * @param Collection
	 *            <Rota> rotas
	 * @param int
	 *        idFuncionalidadeIniciada
	 * @return void
	 * @throws ControladorException
	 */
	public void gerarDebitoACobrarDoacao(Collection<Rota> rotas, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * @author Raphael Rossiter
	 * @date 30/10/2006
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Conta pesquisarContaRetificacao(Integer idConta) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * inserir guia de pagamento no momento da exibição.
	 * [FS0003] Validar registro de atendimento [FS0007] Validar ordem de
	 * servico.
	 * 
	 * @author Rafael Pinto
	 * @date 02/11/2006
	 * @param RegistroAtendimento
	 *            ,OrdemServico,idImovel,idCliente
	 */
	public void validarExibirInserirGuiaPagamento(RegistroAtendimento ra, OrdemServico ordemServico, Integer idImovel, Integer idCliente)
					throws ControladorException;

	/**
	 * [UC0259] - Processar Pagamento com Código de Barras
	 * [SB0005] - Processar Recebimento de Acrescimos por Impontualidade
	 * 
	 * @author Sávio Luiz
	 * @since 07/10/2006
	 * @author Saulo Lima
	 * @date 12/01/2009
	 *       Adicionar a GuiaPagamentoPrestacao
	 * @param guiaPagamento
	 * @param idDebitoTipo
	 * @param dataPagamento
	 * @return idGuiaPagamentoGerado (Integer)
	 * @throws ControladorException
	 */
	public Integer inserirGuiaPagamentoCodigoBarras(GuiaPagamento guiaPagamento, Integer idDebitoTipo, Date dataPagamento)
					throws ControladorException;

	public void atualizarAnoMesReferenciaFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo, Integer anoMesReferenciaFaturamento,
					int atividade) throws ControladorException;

	/**
	 * Seleciona as contaas agrupando por imóvel
	 * [UC0485] - Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Rafael Pinto, Pedro Alexandre
	 * @date 22/11/2006, 08/06/2007
	 * @param anoMesReferenciaContabil
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public HashMap obterContaAgrupadasPorImovel(int anoMesReferenciaContabil, int idLocalidade, int idQuadra) throws ControladorException;

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 23/11/2006
	 * @param filtroImovel
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelContaManter(FiltroImovel filtroImovel, Usuario usuarioLogado) throws ControladorException;

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

	public Object pesquisarDataUltimaAlteracaoConta(Integer idConta) throws ControladorException;

	public void atualizarDataHoraRealizacaoAtividade(Integer idAtividade, Integer anoMesReferencia, Integer idFaturamentoGrupo)
					throws ControladorException;

	/**
	 * Recupera a data de realização passando o id do imovel e a quantidade de
	 * meses que quer subtrair,caso não queira subtrair colocar 0 [UC0488]
	 * Informar Retorno Ordem de Fiscalização
	 * [SB0004] - Calcular Valor de Água e/ou Esgoto
	 * 
	 * @author Sávio Luiz
	 * @date 04/12/2006
	 * @param idOS
	 * @return OrdemServico
	 * @throws ControladorException
	 */

	public Date pesquisarDataRealizacaoFaturamentoAtividadeCronagrama(Integer idImovel, int quantidadeMeses) throws ControladorException;

	public Collection obterContasImovelIntervalo(Integer imovel, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer anoMesInicio, Integer anoMesFim, Integer idContaMotivoRevisao)
					throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Atualiza o ano/mês de referência do faturamento somando mais um mês.
	 * 
	 * @author Pedro Alexandre
	 * @date 08/01/2007
	 * @param anoMesFaturamentoSistemaParametro
	 * @throws ControladorException
	 */
	public void atualizarAnoMesFaturamento(Integer anoMesFaturamentoSistemaParametro) throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Pesquisar os ids das localidades para encerrar o faturamento do mês.
	 * 
	 * @author Pedro Alexandre
	 * @date 08/01/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarIdsLocalidadeParaEncerrarFaturamento() throws ControladorException;

	/**
	 * Pesquisar os ids das localidades para gerar o resumo das
	 * ligações/economias.
	 * 
	 * @author Rodrigo Silveira
	 * @date 17/01/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarIdsLocalidadeParaGerarResumoLigacoesEconomias() throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Tarnsfere para o histórico de contas
	 * 
	 * @author Pedro Alexandre
	 * @date 09/10/2006
	 * @param contas
	 * @param anoMesFaturamentoSistemaParametro
	 * @throws ControladorException
	 */
	public void transferirContasParaHistorico(Collection<Conta> contas, int anoMesFaturamentoSistemaParametro) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * Transfere para ativo as contas do histórico
	 * 
	 * @author Vitor Hora
	 * @date 22/08/2008
	 * @author Saulo Lima
	 * @date 20/08/2009
	 *       Remoção do parâmetro não utilizado ParcelamentoItem
	 * @param contasHistorico
	 * @param anoMesFaturamentoSistemaParametro
	 * @throws ControladorException
	 */
	public void transferirContasHistoricoParaConta(Collection<ContaHistorico> contasHistorico, int anoMesFaturamentoSistemaParametro,
					Integer idDebitoCreditoSituacao)
					throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Para cada conta transferida para o histórico, atualiza o indicador de que
	 * a conta está no histórico na tabela ContaGeral.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/10/2006
	 * @param colecaoContas
	 * @throws ControladorException
	 */
	public void atualizarIndicadorContaNoHistorico(Collection colecaoContas) throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Transfere para o histórico os débitos a cobrar.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/10/2006
	 * @param debitosACobrar
	 * @param registrarTransacao
	 *            TODO
	 * @throws ControladorException
	 */
	public void transferirDebitosACobrarParaHistorico(Collection<DebitoACobrar> debitosACobrar, Boolean registrarTransacao)
					throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Transfere para o histórico os débitos a cobrar.
	 * 
	 * @author Vitor Hora
	 * @date 02/09/2008
	 * @param debitosACobrar
	 * @throws ControladorException
	 */
	/*
	 * public void transferirDebitosACobrarParaAtivo(
	 * Collection<DebitoACobrarHistorico> debitosACobrarHistorico)
	 * throws ControladorException;
	 */

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Para cada débito a cobrar transferido para o histórico, atualiza o
	 * indicador de que o débito a cobrar está no histórico na tabela
	 * DebitoACobrarGeral.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/10/2006
	 * @param colecaoDebitosACobrar
	 * @throws ControladorException
	 */
	public void atualizarIndicadorDebitoACobrarNoHistorico(Collection colecaoDebitosACobrar) throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Transfere para o histórico os créditos a realizar.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/10/2006
	 * @param creditosARealizar
	 * @throws ControladorException
	 */
	public void transferirCreditoARealizarParaHistorico(Collection<CreditoARealizar> creditosARealizar) throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Para cada crédito a realizar transferido para o histórico, atualiza o
	 * indicador de que o crédito a realizar está no histórico na tabela
	 * CreditoARealizarGeral.
	 * 
	 * @author Pedro Alexandre
	 * @date 11/10/2006
	 * @param colecaoCreditosARealizar
	 * @throws ControladorException
	 */
	public void atualizarIndicadorCreditosARealizarNoHistorico(Collection<CreditoARealizar> colecaoCreditosARealizar)
					throws ControladorException;

	/**
	 * [UC0532] Gerar Relatório de Faturamento das Ligações com Medição
	 * Individualizada
	 * 
	 * @author Vivianne Sousa
	 * @date 10/01/2007
	 * @throws ControladorException
	 */
	public Collection pesquisarFaturamentoLigacoesMedicaoIndividualizadaRelatorio(FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql,
					String anoMesfaturamentoGrupo, Integer indicadorRateio) throws ControladorException;

	/**
	 * [UC0493] Emitir de Extrato de Consumo de Imóvel Condomínio
	 * Flávio Cordeiro 08/01/2007
	 */
	public void emitirExtratoConsumoImovelCondominio(String anoMesFaturamento, String idFaturamento, int idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * [UC0173] Gerar Relatório de Resumo do Faturamento
	 * 
	 * @author Vivianne Sousa
	 * @created 24/01/2007
	 * @return
	 * @throws ControladorException
	 */
	public Integer consultarQtdeRegistrosResumoFaturamentoRelatorio(int mesAnoReferencia, Integer localidade, Integer gerenciaRegional,
					String opcaoTotalizacao) throws ControladorException;

	/**
	 * [UC0335] Gerar Resumo de Pendência
	 * Pesquisar os ids das localidade
	 * 
	 * @author Ana Maria
	 * @date 29/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidade() throws ControladorException;

	/**
	 * @author Ana Maria
	 * @date 26/01/2007
	 * @param idConta
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection obterConta(Integer idConta) throws ControladorException;

	/**
	 * [UC] Gerar Relatório de Contas Emitidas
	 * 
	 * @author Vivianne Sousa
	 * @created 30/01/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarContasEmitidasRelatorio(int anoMesReferencia, Integer grupoFaturamento, Collection esferaPoder)
					throws ControladorException;

	/**
	 * [UC] Gerar Relatório de Contas Emitidas
	 * 
	 * @author Vivianne Sousa
	 * @created 02/02/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer consultarQtdeContasEmitidasRelatorio(int anoMesReferencia, Integer grupoFaturamento, Collection esferaPoder)
					throws ControladorException;

	/**
	 * [UC0155] Encerrar Faturamento do Mês
	 * Metodo responsável pela transferência das contas, débito a cobrar e
	 * crédito a realizar para o histórico, assim com a atualizaçaõ dos imóveis.
	 * 
	 * @author Pedro Alexandre
	 * @date 06/02/2007
	 * @param anoMesFaturamentoSistemaParametro
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void gerarHistoricoParaEncerrarFaturamento(int anoMesFaturamentoSistemaParametro, Integer idLocalidade,
					int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * retorna o anoMes do faturamento grupo do imóvel passado
	 */
	public Integer retornaAnoMesFaturamentoGrupo(Integer idImovel) throws ControladorException;

	/**
	 * Monta a colecao de resultdos apartir da tbela conta impressao para
	 * geracao do relatorio de MAPA DE CONTROLE DAS CONTAS EMITIDAS
	 * 
	 * @author Flávio Cordeiro
	 * @date 13/02/2007
	 * @param idGrupoFaturamento
	 * @param anoMes
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarMapaControleContaRelatorio(Integer idGrupoFaturamento, String mesAno, Usuario usuarioLogado,
					String tipoRelatorio, String indicadorFichaCompensacao) throws ControladorException;

	/**
	 * Monta a colecao de resultdos apartir da tabela conta impressao para
	 * geracao do relatorio de RESUMO CONTAS EMITIDAS POR LOCALIDADE NO GRUPO
	 * 
	 * @author Flávio Cordeiro
	 * @date 13/02/2007
	 * @param idGrupoFaturamento
	 * @param anoMes
	 * @param idLocalidade
	 * @param idSetorFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection filtrarResumoContasLocalidade(Integer idGrupoFaturamento, String anoMes, Integer idFirma, Integer idSetorFaturamento,
					Integer idLocalidade) throws ControladorException;

	/**
	 * Recupera as contas
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarContasImoveis(Integer anoMes, Collection idsImovel, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ControladorException;

	/**
	 * [UC0544] - Gerar Arquivo Texto do Faturamento
	 * 
	 * @author Flávio Cordeiro
	 * @date 23/03/2007
	 * @param anoMes
	 * @param idCliente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer[] chamarGerarArquivoTextoFaturamento(int anoMes, String idCliente, Collection colecaoClientesAptos)
					throws ControladorException;

	/**
	 * Recupera as contas do Imóveis
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Integer obterContasConjuntoImoveis(Integer anoMes, Collection idsImovel, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim, String inContasRevisao, Integer[] motivosRevisaoDisponiveis)
					throws ControladorException;

	/**
	 * Alterar Vencimento do Conjunto de Conta
	 * 
	 * @author Ana Maria
	 * @date 20/01/2007
	 * @param colecaoContas
	 * @param dataVencimento
	 * @throws ControladorException
	 */

	public void alterarVencimentoConjuntoConta(Collection colecaoImovel, Date dataVencimento, Integer anoMes,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer anoMesFim, Usuario usuario,
					Collection<Conta> colecaoContasSelecionadas)
					throws ControladorException;

	/**
	 * Retificar Conjunto de Conta
	 * 
	 * @author Ana Maria
	 * @date 24/01/2007
	 * @throws ControladorException
	 */
	public void retificarConjuntoConta(Collection colecaoImovel, Integer anoMes, ContaMotivoRetificacao contaMotivoRetificacao,
					Collection debitosTipoRetirar, Usuario usuarioLogado, Date dataVencimentoContaInicio, Date dataVencimentoContaFim,
					Integer anoMesFim, Collection<Conta> colecaoContasSelecionadas) throws ControladorException;

	/**
	 * Informar Tarifa de Consumo por Subcategoria
	 * 
	 * @autor Tiago Moreno
	 * @date 05/01/2006
	 * @param consumoTarifa
	 * @param consumoTarifaVigencia
	 * @param colecaoConsumoTarifaCategoria
	 * @param colecaoConsumoTarifaFaixa
	 * @throws ControladorException
	 */

	public void informarConsumoTarifaSubcategoria(ConsumoTarifa consumoTarifa, ConsumoTarifaVigencia consumoTarifaVigencia,
					Collection<ConsumoTarifaCategoria> colecaoConsumoTarifaCategoria) throws ControladorException;

	/**
	 * [UC0157] - Simular Cálculo da Conta
	 * [FS0003] - Verificar Consumo Mínimo
	 * 
	 * @author Raphael Rossiter
	 * @date 02/04/2007
	 * @param idLigacaoAguaSituacao
	 *            ,
	 *            consumoFaturado
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarConsumoFaturadoAgua(Integer idLigacaoAguaSituacao, Integer consumoFaturado) throws ControladorException;

	/**
	 * [UC0157] - Simular Cálculo da Conta
	 * [FS0004] - Verificar Volume Mínimo
	 * 
	 * @author Raphael Rossiter
	 * @date 02/04/2007
	 * @param idLigacaoEsgotoSituacao
	 *            ,
	 *            consumoFaturado
	 * @return void
	 * @throws ControladorException
	 */
	public void verificarConsumoFaturadoEsgoto(Integer idLigacaoEsgotoSituacao, Integer consumoFaturado) throws ControladorException;

	/**
	 * [UC0XXX] Emitir Aviso de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 09/04/2007
	 */
	public Object[] pesquisarAnoMesEDiaVencimentoFaturamentoGrupo(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa a soma dos valores das multas cobradas para a conta.
	 * 
	 * @author Sávio Luiz
	 * @date 13/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorMultasCobradasPorFinanciamnetoTipo(int idConta) throws ControladorException;

	/**
	 * Este caso de uso calcula a tarifa miníma de água para um imóvel
	 * (SUBCATEGORIA)
	 * [UC0451] Obter Tarifa Miníma de Água para um Imóvel
	 * 
	 * @author Raphael Rossiter
	 * @date 13/04/2006
	 * @param imovel
	 * @param colecaoSubcategorias
	 * @return BigDecimal
	 * @throws ControladorException
	 */
	public BigDecimal obterTarifaMinimaAguaImovelPorSubcategoria(Imovel imovel) throws ControladorException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0004] Verificar Critério
	 * de Cobrança para Imóvel Pesquisa a soma dos imoveis com parcelamento.
	 * 
	 * @author Sávio Luiz
	 * @date 13/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public int pesquisarQuantidadeDebitosCobradosComParcelamento(Collection<ContaValoresHelper> colecaoContasValores)
					throws ControladorException;

	/**
	 * Pesquisar conjunto de contas p/ emissão da 2°Via
	 * 
	 * @author Ana Maria
	 * @date 19/04/2007
	 * @param colecaoImovel
	 * @param anoMes
	 * @throws ControladorException
	 */

	public Collection pesquisarConjuntoContaEmitir2Via(Collection colecaoImovel, Integer anoMes, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ControladorException;

	/**
	 * Gera credito a realizar para os imóveis de determinados grupos
	 * BATCH PARA CORREÇÃO DA BASE
	 * 
	 * @author Sávio Luiz
	 * @date 02/05/2007
	 */
	public void gerarCreditoARealizarPorImoveisDoGrupo(Collection idsGrupos, Integer anoMesReferenciaConta, Integer anoMesReferenciaDebito)
					throws ControladorException;

	/**
	 * [UC0144] Inserir Comando Atividade Faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 05/05/2007
	 * @param diaVencimento
	 *            ,
	 *            mesVencimento, anoVencimento
	 * @throws ControladorException
	 */
	public Date obterDataVencimentoFaturamentoGrupo(int diaVencimento, int mesVencimento, int anoVencimento) throws ControladorException;

	/**
	 * Recupera o id da Conta Retificada
	 * 
	 * @author Vivianne Sousa
	 * @date 08/05/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarAnoMesReferenciaFaturamentoGrupo(Integer idImovel) throws ControladorException;

	/**
	 * [UC0XXX] - Gerar Relatório Tarifa de Consumo
	 * Pesquisas as tarifas de consumo para o relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 11/05/2007
	 * @param descricao
	 *            ,
	 *            dataVigenciaInicial, dataVigenciaFinal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarConsumoTarifaRelatorio(String descricao, Date dataVigenciaInicial, Date dataVigenciaFinal)
					throws ControladorException;

	/**
	 * [UC0XXX] - Gerar Relatório de Tarifa de Consumo
	 * Pesquisas a data final de validade de uma tarifa de consumo
	 * 
	 * @author Rafael Corrêa
	 * @date 11/05/2007
	 * @param Integer
	 *            idConsumoTarifa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarDataFinalValidadeConsumoTarifa(Integer idConsumoTarifa, Date dataInicioVigencia) throws ControladorException;

	/**
	 * Recupera as contas do Imóveis
	 * 
	 * @author Ana Maria
	 * @date 19/03/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Integer obterContasConjuntoImoveis(Integer anoMes, Collection idsImovel, Integer codigoCliente, Integer relacaoTipo,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer idGrupoFaturamento, Integer anoMesFinal,
					String inContasRevisao, Integer[] motivosRevisaoDisponiveis) throws ControladorException;

	/**
	 * [UC0146] Manter Conta
	 * Recupera as contas do Imóveis
	 * 
	 * @author Carlos Chrystian
	 * @date 07/05/2013
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection recuperarContasConjuntoImoveis(Integer anoMes, Collection idsImovel, Integer codigoCliente, Integer relacaoTipo,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer idGrupoFaturamento, Integer anoMesFinal,
					String inContasRevisao, Integer[] motivosRevisaoDisponiveis) throws ControladorException;

	/**
	 * [UC0147] - Cancelar Conjunto Conta
	 * 
	 * @author Ana Maria
	 * @date 10/12/2005
	 * @author Luciano Galvao
	 * @date 05/11/2013
	 */
	public void cancelarConjuntoConta(Collection<Conta> colecaoContasSelecionadas, ContaMotivoCancelamento contaMotivoCancelamento,
					Usuario usuarioLogado, String numeroRA) throws ControladorException;

	/**
	 * [UC0435] Encerrar Registro de Atendimento
	 * 
	 * @author Carlos Chrystian
	 * @date 13/05/2013
	 * @param numeroRA
	 * @param codigoCliente
	 * @param usuarioLogado
	 */
	public void encerrarRegistroAtendimento(String numeroRA, ClienteImovel clienteImovel, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Alterar Vencimento do Conjunto de Conta
	 * 
	 * @author Ana Maria
	 * @date 20/01/2007
	 * @param colecaoContas
	 * @param dataVencimento
	 * @throws ControladorException
	 */
	public void alterarVencimentoConjuntoContaCliente(Integer codigoCliente, Integer relacaoTipo, Date dataVencimentoInformada,
					Integer anoMes, Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer anoMesFim, Usuario usuario,
					Collection<Conta> colecaoContasSelecionadas)
					throws ControladorException;

	/**
	 * Retificar Conjunto de Conta
	 * 
	 * @author Ana Maria
	 * @date 24/01/2007
	 * @throws ControladorException
	 */
	public void retificarConjuntoContaCliente(Integer codigoCliente, Integer relacaoTipo, Integer anoMes,
					ContaMotivoRetificacao contaMotivoRetificacao, Collection debitosTipoRetirar, Usuario usuarioLogado,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer anoMesFim,
					Collection<Conta> colecaoContasSelecionadas) throws ControladorException;

	/**
	 * Pesquisar conjunto de contas p/ emissão da 2°Via
	 * 
	 * @author Ana Maria
	 * @date 19/04/2007
	 * @param colecaoImovel
	 * @param anoMes
	 * @throws ControladorException
	 */
	public Collection pesquisarConjuntoContaClienteEmitir2Via(Integer codigoCliente, Integer relacaoTipo, Integer anoMes,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim, Integer anoMesFim) throws ControladorException;

	/**
	 * Recupera id de conta(s) sem revisão ou em revisão por ação do usuário
	 * 
	 * @author Vivianne Sousa
	 * @date 14/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasNaoEmRevisaoOuEmRevisaoPorAcaoUsuario(Collection idsConta) throws ControladorException;

	/**
	 * Recupera id de contas que estão em revisão por acão do usuario
	 * 
	 * @author Vivianne Sousa
	 * @date 14/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection obterContasEmRevisaoPorAcaoUsuario(Collection idsConta) throws ControladorException;

	/**
	 * Metódo responsável por inserir um Tipo de Credito
	 * [UC0000 - Inserir Tipo Credito
	 * 
	 * @author Thiago Tenório
	 * @date 27/06/2006, 16/11/2006
	 * @param agencia
	 *            bancaria
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirTipoCredito(CreditoTipo creditoTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Metódo responsável por inserir um Tipo de Credito
	 * [UC0000 - Atualizar Tipo Credito
	 * 
	 * @author Thiago Tenório
	 * @date 27/06/2006, 16/11/2006
	 * @param agencia
	 *            bancaria
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer atualizarTipoCredito(CreditoTipo creditoTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0513] Manter Tipo de Credito
	 * Remover Tipo de Credito
	 * 
	 * @author Thiago Tenório
	 * @date 19/03/2007
	 */
	public void removerTipoCredito(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Este caso de uso permite gerar um relatório analítico do faturamento.
	 * [UC0593]Gerar Relatório Analítico do Faturamento
	 * 
	 * @author Flávio Cordeiro
	 * @date 18/05/2007
	 * @author Saulo Lima
	 * @date 07/02/2009
	 *       Colocados os Generics nas coleções.
	 * @author Virgínia Melo
	 * @date 26/03/2009
	 *       Adicionado o parâmetro 'valorMinimo'.
	 * @param anoMesFaturamento
	 * @param idFaturamentoGrupo
	 * @param indicadorLocalidadeInformatizada
	 * @param colecaoLocalidades
	 * @param colecaoSetores
	 * @param colecaoQuadras
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection<RelatorioAnaliticoFaturamentoHelper> pesquisarDadosRelatorioAnaliticoFaturamento(int anoMesFaturamento,
					Integer idFaturamentoGrupo, int indicadorLocalidadeInformatizada, Collection<Localidade> colecaoLocalidades,
					Collection<SetorComercial> colecaoSetores, Collection<Quadra> colecaoQuadras, String tipoRelatorio,
					Usuario usuarioLogado, BigDecimal valorMinimo) throws ControladorException;

	/**
	 * Metódo responsável por informar uma não entrega de documentos
	 * [UC0559] - Informar Nao Entrega de Documentos
	 * 
	 * @author Thiago Tenório
	 * @date 27/06/2006, 16/11/2006
	 * @author eduardo henrique
	 * @date 11/07/2008
	 * @param usuarioLogado
	 * @param anoMesDocumento
	 *            (adicionado)
	 * @param DocumentoNaoEntregue
	 * @return
	 * @throws ControladorException
	 */
	public Integer informarNaoEntregaDocumentos(Collection colecaoDocumentosNaoEntregues, Usuario usuarioLogado, String anoMesDocumento)
					throws ControladorException;

	/**
	 * [UC0482]Emitir 2ª Via de Conta
	 * 
	 * @author Vivianne Sousa
	 * @date 18/05/2007
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public Collection<EmitirContaHelper> emitir2ViaContasHistorico(Collection idsContaEP, boolean cobrarTaxaEmissaoConta,
					Short contaSemCodigoBarras) throws ControladorException;

	/**
	 * [UC0600] Emitir Histograma de Água
	 * 
	 * @author Rafael Pinto
	 * @date 04/06/2007
	 * @param FiltrarEmitirHistogramaAguaHelper
	 * @return Collection<OrdemServico>
	 * @throws ControladorException
	 */
	public Collection<EmitirHistogramaAguaHelper> pesquisarEmitirHistogramaAgua(FiltrarEmitirHistogramaAguaHelper filtro)
					throws ControladorException;

	/**
	 * [UC0145] - Inserir Conta Author: Raphael Rossiter Data: 13/01/2006
	 * Seleciona a partir da tabela CLIENTE_IMOVEL para IMOV_ID=Id do imóvel e
	 * CLIM_DTRELACAOFIM com o valor correspondente a nulo
	 * 
	 * @param IMOVEL
	 * @throws ControladorException
	 */
	public void inserirClienteConta(Conta conta, Imovel imovel, Cliente clienteResponsavelConta) throws ControladorException;

	/**
	 * Inseri o tipo de debito na base
	 * [UC0529] Inserir Tipo de Débito
	 * 
	 * @author Rômulo Aurélio
	 * @date 09/03/2007
	 * @author eduardo henrique
	 * @date 08/07/2008
	 * @param debitoTipo
	 *            TODO
	 * @param usuarioLogado
	 * @return
	 */

	public Integer inserirDebitoTipo(DebitoTipo debitoTipo, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Atualizar o tipo de debito na base
	 * [UC0530] Atualizar Tipo de Débito
	 * 
	 * @author Rômulo Aurélio
	 * @date 15/03/2007
	 * @param usuarioLogado
	 * @param valorLimeteDebito
	 * @return
	 */

	public void atualizarDebitoTipo(DebitoTipo debitoTipoBase, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Determina qual a menor datade vencimento para uma coleção de contas
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 19/06/2007
	 * @param colecaoContas
	 * @return
	 * @throws ControladorException
	 */
	public Date determinarMenorDataVencimentoConta(Collection colecaoContas) throws ControladorException;

	/**
	 * Remove os contratos de demanda selecionados pelo usuário
	 * [UC0513] - Manter Contrato de Demanda
	 * 
	 * @author Rafael Corrêa
	 * @date 27/06/2007
	 * @param idsContratosDemanda
	 * @throws ControladorException
	 */
	public void removerContratosDemanda(String[] idsContratosDemanda, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Obter a data de vencimento de um grupo de faturamento, no mês de
	 * faturamento corrente.
	 * [UC0618] Obter data de vencimento do grupo
	 * 
	 * @author Pedro Alexandre
	 * @date 26/06/2007
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ControladorException
	 */
	public Date obterDataVencimentoGrupo(Integer idFaturamentoGrupo) throws ControladorException;

	/**
	 * Permite atualizar os dados de um contrato de demanda
	 * [UC0513] Manter Contrato de Demanda
	 * [SB0001] Atualizar Contrato de Demanda
	 * 
	 * @author Rafael Corrêa
	 * @param usuarioLogado
	 * @date 28/06/2007
	 */
	public void atualizarContratoDemanda(ContratoDemanda contratoDemanda, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Metodo que retorna a data de revisão da conta
	 * 
	 * @author Vivianne Sousa
	 * @date 06/07/2007
	 * @param idsConta
	 * @return
	 */
	public Collection pesquisarDataRevisaoConta(Collection idsConta) throws ControladorException;

	/**
	 * [UC0146] - Manter Conta Author: Raphael Rossiter Data: 21/01/2006
	 * Obtém as contas de um imóvel que poderão ser mantidas
	 * 
	 * @param imovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean verificarImoveisComDebito(Integer idImovel) throws ControladorException;

	/**
	 * [UC0623] - Gerar Resumo de Metas CAERN Author: Sávio Luiz Data:
	 * 20/07/2007
	 * Obtém as contas de um imóvel que poderão ser mantidas
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarResumoMetas(Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0623] - Gerar Resumo de Metas CAERN Author: Sávio Luiz Data:
	 * 20/07/2007
	 * Obtém as contas de um imóvel que poderão ser mantidas
	 * 
	 * @param imovel
	 * @param situacaoNormal
	 * @param situacaoIncluida
	 * @param situacaoRetificada
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarResumoMetasAcumulado(Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0485] Gerar Resumo dos Devedores Duvidosos
	 * 
	 * @author Pedro Alexandre
	 * @date 27/07/2007
	 * @return
	 * @throws ControladorException
	 */
	public Short recuperarValorMaximoSequencialImpressaoMais10() throws ControladorException;

	/**
	 * Recupera o debitoCreditoSituacaoAtual da Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 10/08/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarDebitoCreditoSituacaoAtualConta(Integer idImovel, Integer anoMesReferencia) throws ControladorException;

	/**
	 * Alterar Vencimento do Conjunto de Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2007
	 * @throws ControladorException
	 */
	public void alterarVencimentoConjuntoConta(Integer idGrupoFaturamento, Date dataVencimentoInformada, Integer anoMes, Integer anoMesFim,
					Date dataVencimentoContaInicio, Date dataVencimentoContaFim)
					throws ControladorException;

	/**
	 * Retificar Conjunto de Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2007
	 * @throws ControladorException
	 */
	public void retificarConjuntoConta(Integer idGrupoFaturamento, Integer anoMes, ContaMotivoRetificacao contaMotivoRetificacao,
					Collection debitosTipoRetirar, Usuario usuarioLogado, Date dataVencimentoContaInicio, Date dataVencimentoContaFim,
					Integer anoMesFim, Collection<Conta> colecaoContasSelecionadas) throws ControladorException;

	/**
	 * Pesquisar conjunto de contas p/ emissão da 2°Via
	 * 
	 * @author Raphael Rossiter
	 * @date 21/08/2007
	 * @throws ControladorException
	 */
	public Collection pesquisarConjuntoContaEmitir2Via(Integer idGrupoFaturamento, Integer anoMes, Date dataVencimentoContaInicio,
					Date dataVencimentoContaFim, Integer anoMesFim) throws ControladorException;

	/**
	 * Metodo temporario para correção da base de dados
	 * Gerar Crédito a Realizar para os imóveis com contas com vencimento em
	 * 14/08/2007 com multa da conta 06/2007 cobrada na conta 07/2007 e que
	 * pagaram em 17/07/2007
	 * 
	 * @author Pedro Alexandre
	 * @date 20/08/2007
	 * @throws ControladorException
	 */

	public void gerarCreditoARealizarPorImoveisComContasComVencimento14_08_2007() throws ControladorException;

	/**
	 * [UC0216] Calcular Acrescimo por Impontualidade
	 * 
	 * @author Raphael Rossiter
	 * @date 28/08/2007
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Conta pesquisarContaAtualizacaoTarifaria(Integer idConta) throws ControladorException;

	/**
	 * Recupera o data prevista do faturamento atividade cronograma
	 * 
	 * @author Sávio Luiz
	 * @date 28/08/2007
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarFaturamentoAtividadeCronogramaDataPrevista(Integer faturamentoGrupoId, Integer faturamentoAtividadeId,
					Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0596] Inserir Qualidade de Agua
	 * 
	 * @author Kássia Albuquerque
	 * @date 06/08/2007
	 * @return
	 * @throws ControladorException
	 */
	public void inserirQualidadeAgua(QualidadeAgua qualidadeAgua, Collection colecaoQualidadeAgua, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0597] Atualizar Qualidade de Agua
	 * 
	 * @author Eduardo henrique
	 * @date 17/07/2008
	 * @return
	 * @throws ControladorException
	 */

	public void atualizarQualidadeAgua(QualidadeAgua qualidadeAgua, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0259] - Processar Pagamento com Código de Barras
	 * 
	 * @author Raphael Rossiter
	 * @date 30/09/2007
	 * @return Fatura
	 * @throws ErroRepositorioException
	 */
	public Fatura pesquisarFaturaPorQualificador(Short codigoQualificador, Integer anoMesReferencia, BigDecimal valorDebito)
					throws ControladorException;

	/**
	 * obtem o consumo médio faturado nos ultimos 6 meses
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 05/09/2007
	 * @param
	 * @throws ControladorException
	 */
	public Integer obterValorConsumoMedio6meses(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa os dados necessário para a geração do relatório
	 * [UC0637] - Gerar Relatórios Volumes Faturados
	 * 
	 * @author Rafael Corrêa
	 * @created 11/09/2007
	 * @throws ControladorException
	 */
	public Collection<VolumesFaturadosRelatorioHelper> pesquisarDadosRelatorioVolumesFaturados(Integer idLocalidade, Integer anoMes,
					Integer anoMes1, Integer anoMes2, Integer anoMes3, Integer anoMes4, Integer anoMes5, Integer anoMes6)
					throws ControladorException;

	/**
	 * Pesquisa os dados necessário para a geração do relatório resumido
	 * [UC0637] - Gerar Relatórios Volumes Faturados
	 * 
	 * @author Rafael Corrêa
	 * @created 13/09/2007
	 * @throws ControladorException
	 */
	public Collection<VolumesFaturadosRelatorioHelper> pesquisarDadosRelatorioVolumesFaturadosResumido(Integer idLocalidade,
					Integer anoMes, Integer anoMes1, Integer anoMes2, Integer anoMes3, Integer anoMes4, Integer anoMes5, Integer anoMes6)
					throws ControladorException;

	/**
	 * Pesquisa os dados necessário para a geração do relatório
	 * [UC0635] - Gerar Relatórios de Contas em Revisão
	 * 
	 * @author Rafael Corrêa
	 * @created 20/09/2007
	 * @throws ControladorException
	 */
	public Collection<ContasEmRevisaoRelatorioHelper> pesquisarDadosRelatorioContasRevisao(Integer idGerenciaRegional,
					Integer idUnidadeNegocio, Integer idElo, Integer idLocalidadeInicial, Integer idLocalidadeFinal,
					Integer idMotivoRevisao, Integer idImovelPerfil, Integer referenciaInicial, Integer referenciaFinal)
					throws ControladorException;

	/**
	 * Pesquisa os dados necessário para a geração do relatório resumido
	 * [UC0635] - Gerar Relatórios de Contas em Revisão
	 * 
	 * @author Rafael Corrêa
	 * @created 20/09/2007
	 * @throws ControladorException
	 */
	public Collection<ContasEmRevisaoRelatorioHelper> pesquisarDadosRelatorioContasRevisaoResumido(Integer idGerenciaRegional,
					Integer idElo, Integer idLocalidadeInicial, Integer idLocalidadeFinal, Integer idMotivoRevisao, Integer idImovelPerfil,
					Integer referenciaInicial, Integer referenciaFinal) throws ControladorException;

	/**
	 * Pesquisa os dados necessário para a geração do relatório
	 * [UC0638] - Gerar Relatórios Anormalidade Consumo
	 * 
	 * @author Rafael Corrêa
	 * @created 15/10/2007
	 * @throws ControladorException
	 */
	public Collection<GerarRelatorioAnormalidadeConsumoHelper> pesquisarDadosRelatorioAnormalidadeConsumo(Integer idGrupoFaturamento,
					Short codigoRota, Integer idGerenciaRegional, Integer idUnidadeNegocio, Integer idElo, Integer idLocalidadeInicial,
					Integer idLocalidadeFinal, Integer referencia, Integer idImovelPerfil, Integer numOcorConsecutivas,
					String indicadorOcorrenciasIguais, Integer mediaConsumoInicial, Integer mediaConsumoFinal,
					Integer idAnormalidadeConsumo, Integer idAnormalidadeLeitura) throws ControladorException;

	/**
	 * @author Vivianne Sousa
	 * @date 18/09/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasAtualizacaoTarifaria(Integer idImovel, Integer inicialReferencia, Integer finalReferencia,
					Date inicialVencimento, Date finalVencimento) throws ControladorException;

	/**
	 * Pesquisa os Valores das Faixas de Débitos
	 * 
	 * @author Ivan Sérgio
	 * @created 14/09/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDebitoFaixaValores(Integer idFaixaValor, Double valorFaixa) throws ControladorException;

	public void removerQualidadeAgua(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0626] Gerar Resumo de Metas Acumulado no Mês (CAERN)
	 * 
	 * @author Sávio Luiz
	 * @data 28/11/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public boolean verificarDebitoMais3MesesFaturaEmAberto(Integer anoMesReferencia, Integer idImovel) throws ControladorException;

	public Boolean pesquisarExisteciaParcelamentoConta(Integer idConta) throws ControladorException;

	/**
	 * Monta uma coleção de contas categoria a partir de uma coleção de categoria recebida
	 * 
	 * @param colecaoCategoria
	 * @param conta
	 * @return
	 */
	public Collection montarColecaoContaCategoria(Collection colecaoSubcategoria, Conta conta);

	/**
	 * Método que verifica se há Registro de Atendimento, que possui tipo de débito vinculado com
	 * Guia de Pagamento já cadastrada.
	 * [UC0187] - FS0008
	 * 
	 * @author eduardo henrique
	 * @date 25/07/2008
	 */
	public boolean verificarExistenciaRegistroAtendimentoGuiaPagamento(Integer idRegistroAtendimento, Integer idDebitoTipo)
					throws ControladorException;

	/**
	 * Pesquisa os dados de Guias de Pagamento (e suas Prestações) de um determinado Imóvel
	 * 
	 * @author eduardo henrique
	 * @date 08/08/2008
	 * @param idImovel
	 * @param idCliente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiasPagamentoPrestacaoImovelOuCliente(Integer idImovel, Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa o volume do faturamento situação histórico
	 * 
	 * @author Virgínia Melo
	 * @param idImovel
	 *            - Id do Imóvel
	 * @param anoMesFaturamento
	 *            - Ano/Mês de Faturamento
	 * @return FaturamentoSituacaoHistorico
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarVolumeFaturamentoSituacaoHistorico(Integer idImovel, Integer anoMesFaturamento) throws ControladorException;

	/**
	 * [UC0088] - Registrar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 08/10/2008
	 * @param colecaoRotas
	 *            (Coleção de Rotas que terão Faturamento Imediato gerado)
	 * @param faturamentoGrupo
	 *            (Grupo de Faturamento a ser Faturado)
	 * @param anoMesReferencia
	 *            (Ano/Mês referente ao Faturamento)
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public void registrarFaturamentoImediatoGrupoFaturamento(Collection<Rota> colecaoRotas, FaturamentoGrupo faturamentoGrupo,
					Integer anoMesCorrente, Integer idFuncionalidadeIniciada, Integer atividade) throws ControladorException;

	/**
	 * Emitir Contas
	 * [UC0352] - Emitir Contas
	 * 
	 * @author Saulo Lima, Eduardo Henrique, Anderson Italo
	 * @date 08/10/2008 , 18/11/2008, 03/08/2013
	 */
	public void emitirContas(Integer idEmpresa, Integer anoMesReferencia, FaturamentoGrupo faturamentoGrupo, Usuario usuario,
					Integer idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * Recupera o anoMesReferencia baseado em filtros do FaturamentoGrupo
	 * 
	 * @author eduardo henrique
	 * @date 10/12/2008
	 * @param idFaturamentoGrupo
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarAnoMesPorIdFaturamentoGrupo(Integer idFaturamentoGrupo) throws ControladorException;

	/**
	 * Recupera os FaturamentoSituacaoHistorico em vigência (baseado numa referência) de um Imóvel.
	 * 
	 * @author eduardo henrique
	 * @date 28/05/2009
	 * @param idImovel
	 *            [obrigatório]
	 * @param anoMesFaturamento
	 *            [obrigatório]
	 * @return Collection<FaturamentoSituacaoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<FaturamentoSituacaoHistorico> pesquisarFaturamentoSituacaoHistoricoImovel(Integer idImovel, Integer anoMesFaturamento)
					throws ControladorException;

	public int pesquisarQuantidadeDebitosCobradosComParcelamentoPorConta(Collection idsContas) throws ControladorException;

	public void transferirContasParaHistorico(Integer idConta) throws ControladorException;

	/**
	 * Metodo que retorno o somátorio dos debitos cobrados.
	 * [0] - Multas,
	 * [1] - Juros
	 * [2] - Outros Serviços
	 * 
	 * @author isilva
	 * @date 17/11/2010
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */
	public Object[] pesquisarDebitosCobradosPorConta(Integer idConta) throws ControladorException;

	/**
	 * Retorna os SetorComercialVencimento, Ativos ou Inativos;
	 * Ordenados pelo dia de Vencimento.
	 * Não Carrega Localidade, ném SetorComercial
	 * 
	 * @author isilva
	 * @date 21/01/2011
	 * @param idLocalidade
	 * @param idSetorComercial
	 * @param indicadorUso
	 * @return
	 * @throws ControladorException
	 */
	public Collection<SetorComercialVencimento> pesquisarSetorComercialVencimentoPorLocalidadeSetorComercial(Integer idLocalidade,
					Integer idSetorComercial, Short indicadorUso) throws ControladorException;

	/**
	 * Relatório Ordem de Serviço de Substituição de Hidrômetro
	 * Obter dados de até duas contas em revisão
	 * 
	 * @author Anderson Italo
	 * @date 26/05/2011
	 */
	public Collection<DadosContaEmRevisaoHelper> pesquisarDadosContasEmRevisao(Integer idImovel) throws ControladorException;

	/**
	 * [UC0083] Gerar Dados para Leitura
	 * - Atualizar situação do faturamento do imóvel
	 * - Atualizar o ano/mês de faturamento retirada
	 * 
	 * @author Hebert Falcão
	 * @date 28/06/2011
	 */
	public void atualizarImoveisSituacaoEspecialFaturamentoFinalizadaPorGrupo(Integer anoMesFaturamento, Integer idGrupoFaturamento)
					throws ControladorException;

	/**
	 * @param grupoFaturamentoID
	 * @param situacao
	 * @return
	 * @throws ControladorException
	 */
	public Date buscarDataLeituraCronograma(Integer grupoFaturamentoID, boolean situacao, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * @param faturamentoGrupoCronogramaMensal
	 * @param dataRealizada
	 * @param anoMesReferencia
	 * @param grupoFaturamento
	 * @throws ControladorException
	 */
	public void atualizarFaturamentoAtividadeCronograma(FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal)
					throws ControladorException;

	/**
	 * [UC00084] Gerar Faturamento Imediato
	 * 
	 * @author Yara Souza
	 * @date 17/08/2011
	 * @throws ControladorException
	 */
	public void gerarFaturamentoImediato(Collection colecaoRotas, Integer anoMesReferencia, Integer idGrupoFaturamentoRota,
					FuncionalidadeIniciada funcionalidade, Collection collLeituraTipo);

	/**
	 * @author Isaac Silva
	 * @date 15/09/2011
	 * @param codigoSetorComercial
	 * @param idLocalidade
	 * @return
	 * @throws ControladorException
	 */
	public Collection<FaturamentoGrupo> pesquisarFaturamentoGrupoPorCodigoSetorComercialELocalidade(Integer codigoSetorComercial,
					Integer idLocalidade) throws ControladorException;

	/**
	 * Verifica se a ROTA informada está em
	 * (SETOR COMERCIAL, FATURAMENTO_GRUPO)
	 * 
	 * @author Isaac Silva
	 * @date 15/09/2011
	 * @param codigoSetorComercial
	 * @param idLocalidade
	 * @return
	 * @throws ControladorException
	 */
	public boolean existeVinculoRotaSetorComercialFaturamentoGrupo(Quadra quadra) throws ControladorException;

	/**
	 * [UC0254] - Efetuar Análise do Movimento dos Arrecadadores
	 * obtem imovel, localidade e conta atraves do id da conta
	 * 
	 * @author Vivianne Sousa
	 * @date 29/01/2008
	 * @param idConta
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public Conta obterImovelLocalidadeConta(Integer idConta) throws ControladorException;

	/**
	 * [UC0254] - Efetuar Análise do Movimento dos Arrecadadores
	 * obtem imovel, localidade e contaHistorico atraves do id da conta historico
	 * 
	 * @author Vivianne Sousa
	 * @date 29/01/2008
	 * @param idConta
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public ContaHistorico obterImovelLocalidadeContaHistorico(Integer idConta) throws ControladorException;

	/**
	 * [UC0352] Emitir Contas e Cartas
	 * [SB0032] Obter Fator de Vencimento
	 * 
	 * @author Vivianne Sousa
	 * @date 13/11/2007
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public String obterFatorVencimento(Date dataVencimento) throws ControladorException;

	/**
	 * Retorna o Objeto FaturamentoGrupo pelo ID pesquisado.
	 * 
	 * @author Ailton Sousa
	 * @date 04/10/2011
	 * @param idFaturamentoGrupo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public FaturamentoGrupo pesquisarFaturamentoGrupoPorID(Integer idFaturamentoGrupo) throws ControladorException;

	/**
	 * [UC0148] - Colocar Conta em Revisão
	 * [FS0016] - Verificar contas que estejam em revisão
	 * 
	 * @author Anderson Italo
	 * @date 18/10/2011
	 * @throws ControladorException
	 */
	public void verificarContasRevisao(Collection<Conta> colecaoContas, String idsContasSelecionadas) throws ControladorException;

	/**
	 * [UC0146] - Manter Conta
	 * [FS0021] – Verificar situação da conta
	 * 
	 * @param conta
	 * @throws ControladorException
	 */
	public boolean verificarSituacaoContaPermitida(Conta conta) throws ControladorException;

	/**
	 * [UC3027] Filtrar Guia de Pagamento
	 * 
	 * @author Anderson Italo
	 * @date 27/10/2011
	 */
	public Collection<GuiaPagamentoHelper> pesquisarRegistrosManterGuiaPagamento(FiltroGuiaPagamentoHelper filtro)
					throws ControladorException;

	/**
	 * [UC3027] Filtrar Guia de Pagamento
	 * Obter total de registros retornados na consulta
	 * 
	 * @author Anderson Italo
	 * @date 27/10/2011
	 */
	public Integer pesquisarTotalRegistrosManterGuiaPagamento(FiltroGuiaPagamentoHelper filtro) throws ControladorException;

	/**
	 * Pesquisar Prestações de Guia de Pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @throws ControladorException
	 * @date 20/12/2011
	 */
	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoFiltrar(Integer guiaPagamentoId)
					throws ControladorException;

	/**
	 * Pesquisar Prestações de Guia de Pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hiroshi Gonçalves
	 * @throws ControladorException
	 * @date 06/05/2013
	 */
	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoFiltrar(Integer guiaPagamentoId, int nuConsulta)
					throws ControladorException;

	/**
	 * Pesquisar Prestações de Guia de Pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 05/01/2012
	 */
	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoRelatorio(Integer guiaPagamentoId)
					throws ControladorException;

	/**
	 * Pesquisar tipos de financiamento de débitos que não permitam o cancelamento de uma guia de
	 * pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 02/01/2011
	 */
	public Collection<String> pesquisarTipoFinanciamentoDebitoNaoPermiteCancelarGuiaPagamento(Integer guiaPagamentoId)
					throws ControladorException;

	/**
	 * Pesquisar parcelamentos de cobranca bancarias correspondentes a uma guia de pagamento
	 * pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 02/01/2011
	 */
	public Collection<String> pesquisarBoletoEmissaoGuiaPagamento(Integer guiaPagamentoId) throws ControladorException;

	/**
	 * Pesquisar parcelamentos de cobranca bancarias correspondentes a uma guia de pagamento
	 * [UC0188] Manter Guia de Pagamento
	 * 
	 * @author Hugo Lima
	 * @date 05/01/2011
	 */
	public Collection<Object[]> pesquisarBoletoGeradoGuiaPagamento(Integer guiaPagamentoId, Short numeroPrestacao)
					throws ControladorException;

	/**
	 * [UC3034] Gerar Multa por Descumprimento de Parcelamento
	 * Este caso de uso tem como objetivo verificar as situações que caracterizam o descumprimento
	 * de um parcelamento e, sendo confirmado, gerar multa.
	 * 
	 * @author Anderson Italo
	 * @date 04/02/2012
	 * @throws ControladorException
	 */
	public void gerarMultaPorDescumprimentoParcelamento(int idFuncionalidadeIniciada, FaturamentoGrupo faturamentoGrupo)
					throws ControladorException;

	/**
	 * [UC0111] Iniciar Processo
	 * [SB0006] – Obter Dados Complementares de Comando de Faturamento
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @param idComandoFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public String obterDadosComplementaresComandoFaturamento(Integer idComandoFaturamento) throws ControladorException;

	/**
	 * [UC3037] Filtrar Contas Pré-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 10/02/2012
	 *          Exibir Contas Pré-Faturadas.
	 */
	public Collection pesquisarContasPreFaturadas(FaturaContasPreFaturadasHelper faturaContasPreFaturadasHelper, Integer pageOffset,
					boolean indicadorRelatorio) throws ControladorException;

	/**
	 * [UC3035] Concluir Faturamento Contas Pré-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 16/02/2012
	 */
	public Conta pesquisarContaPeloID(Integer idConta) throws ControladorException;

	/**
	 * [UC3035] Concluir Faturamento Contas Pré-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 23/02/2012
	 */
	public void atualizarSituacaoConta(Conta conta, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC3037] Filtrar Contas Pré-Faturadas
	 * 
	 * @author Carlos Chrystian
	 * @created 10/02/2012
	 *          Exibir Contas Pré-Faturadas.
	 */
	public Collection<Integer> pesquisarQuantidadeContasPreFaturadas(FaturaContasPreFaturadasHelper faturaContasPreFaturadasHelper)
					throws ControladorException;

	/**
	 * [UC0101] - Consistir Leituras e Calcular Consumos
	 * [FS0005] - Verificar existência da conta
	 * 
	 * @author Anderson Italo
	 * @date 02/08/2011
	 * @return Object
	 * @exception ErroRepositorioException
	 */
	public Object verificarExistenciaConta(Integer imovelId, Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC3055] Encerrar Faturamento
	 * [SB0001] – Selecionar Grupos de Faturamento por Situação
	 * 
	 * @author Hebert Falcão
	 * @date 01/04/2012
	 */
	public Collection<GupoFaturamentoHelper> selecionarGruposFaturamentoPorSituacao(Integer referencia, Short situacao)
					throws ControladorException;

	/**
	 * [UC3055] Encerrar Faturamento
	 * Pesquisar Grupos Não Faturados
	 * 
	 * @author Hebert Falcão
	 * @date 01/04/2012
	 */
	public Collection<FaturamentoGrupo> pesquisarGruposNaoFaturados(Integer referencia) throws ControladorException;

	/**
	 * [UC3055] Encerrar Faturamento
	 * [SB0002] – Encerrar Faturamento
	 * 
	 * @author Hebert Falcão
	 * @date 01/04/2012
	 */
	public void encerrarFaturamento(Integer referencia, Usuario usuario) throws ControladorException;

	/**
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */

	public BigDecimal pesquisarValorJurosCobrados(int idConta) throws ControladorException;

	/**
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */

	public BigDecimal pesquisarValorAtualizacaoCobradas(int idConta) throws ControladorException;

	/***
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	public Collection<Object[]> selecionarContasCorrigirFaturamentoImediato() throws ControladorException;

	public void corrigirFaturamentoImediato(Collection<Object[]> colecao) throws ControladorException;

	/**
	 * Este caso de uso permite Retirar de Revisão Todas as Contas com o Prazo de Revisão vencido
	 * [UC3057] Retirar Conta de Revisão Prazo Vencido
	 * 
	 * @author Hugo Lima
	 * @created 15/05/2012
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void retirarContaRevisaoPrazoVencido(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * Pesquisa o valor do percentual de esgoto para o imóvel informado.
	 * 
	 * @author Pedro Alexandre
	 * @date 18/09/2006
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public BigDecimal obterPercentualLigacaoEsgotoImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0113] - Gerar Faturamento Grupo Author: Rafael Santos Data: 05/05/2006
	 * Dados da Ligacao Esgoto do Imovel
	 * 
	 * @param imovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public LigacaoEsgoto obterLigacaoEsgotoImovel(Integer idImovel) throws ControladorException;

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * Gerar um perído de leitura para faturamento
	 * 
	 * @author Raphael Rossiter
	 * @data 18/09/2007
	 */
	public Date[] gerarPeriodoLeituraFaturamento(Date dataLeituraAtualFaturamento, Date dataLeituraAnteriorFaturamento,
					FaturamentoAtivCronRota faturamentoAtivCronRota, Integer anoMesAnterior, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * Verifica se existem dados para processamento do Relatório Mapa Controle Contas
	 * 
	 * @author Hugo Lima
	 * @date 10/07/2012
	 * @param idGrupoFaturamento
	 * @param mesAno
	 * @param indicadorFichaCompensacao
	 * @return
	 * @throws ControladorException
	 */
	public void verificarDadosMapaControleContaRelatorio(Integer idGrupoFaturamento, String mesAno, String indicadorFichaCompensacao)
					throws ControladorException;

	/**
	 * [UC3061] Gerar Relatório Posição do Débito da Negativação – Legado CASAL
	 * Pesquisar as contas em processo de negativação e já transferidas para o histórico
	 * 
	 * @date 28/07/2012
	 * @author Hebert Falcão
	 */
	public Collection<ContaHistorico> pesquisarContaEmProcessoNegativacao(Integer anoMesFaturamento) throws ControladorException;

	public Collection<Integer> obterValorTotalContasSelicionadas(String anoMesFaturamento) throws ControladorException;

	/**
	 * Atualizar Imovel Cobrança Situação em Cobrança Administrativa
	 * 
	 * @date 17/08/2012
	 * @author Hebert Falcão
	 */
	public void atualizarImovelCobrancaSituacaoEmCobrancaAdministrativa(Collection<CobrancaDocumento> colecaoCobrancaDocumento)
					throws ControladorException;

	/**
	 * Verificar e Atualizar Itens Documento de Cobrança e da Cobrança Administrativa - Guia
	 * 
	 * @date 17/08/2012
	 * @author Hebert Falcão
	 */
	public void atualizarCobrancaDocumentoItemGuiaCobrancaAdministrativa(Collection<GuiaPagamento> colecaoGuiaPagamento)
					throws ControladorException;

	/**
	 * Verificar e Atualizar Itens Documento de Cobrança e da Cobrança Administrativa - Conta
	 * 
	 * @date 17/08/2012
	 * @author Hebert Falcão
	 */
	public void atualizarCobrancaDocumentoItemContaCobrancaAdministrativa(Collection<Integer> colecaoContaId) throws ControladorException;

	/**
	 * [UC0482]Emitir 2ª Via de Conta
	 * 
	 * @author Carlos Chrystian
	 * @date 14/08/2012
	 *       Emitir Contas Caucionadas
	 * @param colecaoConta
	 * @throws ControladorException
	 */
	public Collection<EmitirContaHelper> emitir2ViaContasCaucionadas(Collection colecaoContaCaucionamento, boolean cobrarTaxaEmissaoConta,
					Short contaSemCodigoBarras, Collection<MedicaoHistorico> colecaoDadosMedicaoLeitura,
					Collection<Collection<ContaCategoriaConsumoFaixa>> colecaoContaCategoriaConsumoFaixa) throws ControladorException;

	/**
	 * Verificar permissao as contas a partir da matricula passada
	 * 
	 * @param idConta
	 * @param matriculaImovel
	 *            *
	 * @throws FachadaException
	 */
	public void verificarPermissaoImovelConta(Integer idConta, Integer matriculaImovel) throws ControladorException;

	/**
	 * Retorna a lista de ids tipos de debitos dos itens de parcelamento (prestacoes)
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param idParcelamento
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> obterDebitosGuiasPagamentoPrestacoesParcelamento(Integer idParcelamento) throws ControladorException;

	/**
	 * [UC0188] Manter Guia de Pagamento
	 * [SB0005] – Validar autorização de acesso a prestação da guia de imóvel
	 * 
	 * @author Hugo Lima
	 * @date 21/08/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoGuiaPagamentoHelper
	 * @throws ControladorException
	 */
	public void validarAutorizacaoAcessoPrestacaoGuiaImovel(Usuario usuario, Integer idImovel,
					Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacaoHelper) throws ControladorException;

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
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, int anoMesReferencia) throws ControladorException;

	public BigDecimal calcularValorTotalMultasCobradasConta(Collection<DebitoCobrado> colecaoDebitoCobrado) throws ControladorException;

	/**
	 * @author Leonardo Maranhão
	 * @param imovel
	 * @param calcularAcrescimoPorImpontualidadeHelper
	 * @param sistemaParametros
	 * @param debitoTipo
	 * @param valorDebito
	 * @param referencia
	 * @throws ControladorException
	 */
	public void criarDebitoACobrar(Imovel imovel, CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidadeHelper,
					SistemaParametro sistemaParametros, DebitoTipo debitoTipo, BigDecimal valorDebito, Integer referencia)
					throws ControladorException;

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * 
	 * @author eduardo henrique
	 * @date 30/09/2008
	 *       Obtém o FaturamentoAtivCronogramaRota de um determinado grupo de AnoMesReferencia,
	 *       AtividadeFaturamento, FaturamentoGrupo e Rota
	 * @throws ControladorException
	 */
	public FaturamentoAtivCronRota obterFaturamentoAtividadeCronogramaPorGrupoFaturamentoRota(Integer idAtividadeComandoFaturamento,
					Integer anoMesReferenciaComandoFaturamento, FaturamentoGrupo faturamentoGrupo, Rota rota) throws ControladorException;

	/**
	 * Método que realiza a retificação da conta para ajuste do faturamento de serviços da DESO para
	 * referência 201211
	 * 
	 * @author Anderson Italo
	 * @date 14/11/2012
	 */
	public Integer retificarContaAjusteFaturamento(Integer anoMesConta, Conta contaAtual, Imovel imovel, Collection colecaoDebitoCobrado,
					LigacaoAguaSituacao ligacaoAguaSituacao, LigacaoEsgotoSituacao ligacaoEsgotoSituacao, String consumoAgua,
					String consumoEsgoto, String percentualEsgoto, Date dataVencimentoConta, ContaMotivoRetificacao contaMotivoRetificacao,
					Usuario usuarioLogado, ConsumoTarifa consumoTarifa, Collection colecaoCreditoRealizado) throws ControladorException;

	/**
	 * @param emitirContaHelper
	 * @param imovel
	 * @param dataLeituraAnterior
	 * @param dataLeituraAtual
	 * @return
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */

	public Collection<CalcularValoresAguaEsgotoHelper> obterCalcularValoresAguaEsgotoParaAjuste(EmitirContaHelper emitirContaHelper,
					Imovel imovel, Date dataLeituraAnterior, Date dataLeituraAtual) throws ErroRepositorioException, ControladorException;

	/**
	 * Método que realiza o ajuste do faturamento de tarifas da DESO para referência 201211
	 * 
	 * @author Yara Souza
	 * @date 15/11/2012
	 */
	public void ajustarFaturamentoTarifasDeso(Integer idFaturamentoGrupo, Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0352] [SB0103]
	 * Método responsável por formatar a descricao de um debito cobrado
	 * 
	 * @param idDebitoCobrado
	 * @return
	 * @throws ControladorException
	 */
	String formatarDescricaoDebitoCobrado(Integer idDebitoCobrado) throws ControladorException;

	/**
	 * [UC0084] Gerar Faturamento Imediato
	 * [SB0019] Formatar Descrição do Débito
	 * 
	 * @author Hebert Falcão
	 * @date 30/11/2012
	 */
	public String formatarDescricaoDebitoCobrado(Integer idConta, Integer idDebitoTipo, Short numeroPrestacaoDebito, Short numeroPrestacao)
					throws ControladorException;

	public void ajustarFaturamentoServicosRetificarContasErradasDeso(Integer idFaturamentoGrupo, Integer anoMesReferencia)
					throws ControladorException;

	public void cancelarDebitosRotinaAjusteFaturamentoDeso() throws ControladorException;

	public void verificarImoveisComErroRelacao1e2(Collection<FaturamentoGrupo> colecaoFaturamentoGrupo, Integer anoMesReferencia)
					throws ControladorException;

	public void verificarImoveisComErroRelacao5(Collection<FaturamentoGrupo> colecaoFaturamentoGrupo, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * @param idImovel
	 * @param anoMesReferencia
	 * @param idDebitoTipo
	 * @param numeroPrestacaoDebito
	 * @return
	 * @throws ControladorException
	 */

	public Collection<DebitoACobrar> pesquisarDebitosACobrarDoImovel(Integer idImovel, Collection anoMesReferencia, Integer idDebitoTipo)
					throws ControladorException;

	/**
	 * Método que realiza o ajuste do faturamento de débitos a cobrar da DESO para referência 201211
	 * 
	 * @author Yara Souza
	 * @date 26/11/2012
	 */
	public void executarAjusteConta(Integer idGrupoFaturamento) throws ControladorException;

	/**
	 * Método responsável por registrar o lançamento contabil de uma coleção de debitos a cobrar
	 * 
	 * @param idsDebitosACobrar
	 * @throws ControladorException
	 */
	void registrarLancamentoContabilDebitoACobrar(Collection<Integer> idsDebitosACobrar) throws ControladorException;

	public void executarAjusteBaixarPagamentosAMaior() throws ControladorException;

	public void executarAjusteContasRetificar(Integer idGrupo) throws ControladorException;

	public Object[] gerarDadosAtualizarContaCategoriaEInserirContaCategoriaConsumoFaixa(Collection colecaoCategorias,
					Collection colecaoCalcularValoresAguaEsgotoHelper, Conta conta) throws ControladorException;

	public Object[] ajustarValoresPorCategoriaEFaixa(BigDecimal valorTotalAgua, BigDecimal valorFaturadoAgua, BigDecimal valorTotalEsgoto,
					BigDecimal valorFaturadoEsgoto, Collection colecaoContaCategoriaConsumoFaixaInserir,
					Collection colecaoContaCategoriaAtualizar, Imovel imovel, Conta conta);

	/**
	 * [UC0113] Faturar Grupo de Faturamento
	 * [UC0088] Registrar Faturamento Imediato
	 * Verifica perda do beneficio da Tarifa Social <br>
	 * <br>
	 * [FS0011] – Verifica perda do beneficio da Tarifa Social
	 * - Caso o Id da tarifa temporária selecionada seja referente à Tarifa Social (PASI_VLPARAMETRO
	 * da tabela PARAMETRO_SISTEMA com PASI_DSPARAMETRO="P_TARIFA_CONSUMO _TARIFA_SOCIAL"):
	 * • Obter “Consumo máximo” (PASI_VLPARAMETRO da tabela PARAMETRO_SISTEMA com PASI_DSPARAMETRO =
	 * ”P_CONSUMO_MAXIMO_BENEFICIO_TARIFA_SOCIAL”);
	 * • Caso o consumo medido no mês seja maior que Consumo máximo, o imóvel não será beneficiado
	 * com a tarifa social, atribuir a tarifa Padrão (CSTF_ID da tabela IMOVEL).
	 * 
	 * @author Hebert Falcão
	 * @date 29/09/2012
	 */
	public ConsumoTarifa verificarPerdaBeneficioDaTarifaSocialParaFaturamento(Integer consumoMedido, Integer consumoMinimo, Imovel imovel)
					throws ControladorException;

	public void executarCancelamentoDebitoACobrar() throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [FS0012] – Verifica existência de imóveis de localidades associadas a concessionárias
	 * diversas
	 * 
	 * @author Anderson Italo
	 * @date 23/02/2013
	 */
	public void verificaExistenciaLocalidadesAssociadaConcessionariasDiversas(String idsContas, String idsGuias, String idsDebitos,
					String idsCreditos) throws ControladorException;

	/**
	 * Método responsável por listar CreditoOrigem a partir do tipo de crédito
	 * 
	 * @param idCreditoTipo
	 * @param indicadorUsoLivre
	 * @return
	 * @throws ControladorException
	 */
	Collection<CreditoOrigem> listarCreditoOrigem(Integer idCreditoTipo, Short indicadorUsoLivre) throws ControladorException;

	/**
	 * Método reponsável por obter o valor total disponivel de credito a realizar do imóvel por tipo
	 * de credito
	 * 
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	Map<Integer, BigDecimal> obterValorTotalDisponivelCreditoArealizarImovel(Integer idImovel) throws ControladorException;

	public void executarCancelamentoDebitoACobrarRateioCasal() throws ControladorException;

	public void executarAjusteDebitoACobrar() throws ControladorException;

	public void executarAjusteRetificarContasRetirarDebitoRateioDuplicado() throws ControladorException;

	/**
	 * Método responsável por agrupar e ordenar os débitos a cobrar
	 * 
	 * @param colecaoDebitoACobrar
	 * @param usarValorTotalDebito
	 * @return
	 * @throws ControladorException
	 */
	Collection<DebitoACobrar> agruparDebitoACobrar(Collection<DebitoACobrar> colecaoDebitoACobrar, Boolean usarValorTotalDebito)
					throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Pesquisa a conta do imóvel com a referência informada pelo usuário
	 * [FS0012] - Verificar existência da conta
	 */
	public ContaHistorico pesquisarContaHistoricoDigitada(String idImovel, String referenciaConta) throws ControladorException;

	/**
	 * Método responsável por obter o total de economias e o total de ligações do histograma de agua
	 * por economia
	 * 
	 * @param idGerenciaRegional
	 * @param idLocalidade
	 * @param idCategoria
	 * @return
	 * @throws ControladorException
	 */
	Object[] obterTotalEconomiasETotalLigacoesHistogramaAgua(Integer idGerenciaRegional, Integer idLocalidade, Integer idCategoria)
					throws ControladorException;

	Object[] listarSomatorioEValorFaturasDebito(Integer idImovel) throws ControladorException;

	/**
	 * [UC3013] Gerar Declaração Anual Quitação Débitos
	 * Este caso de uso indica se o imóvel está apto a receber a declaração anual de quitação de
	 * débitos para um determinado ano de referência.
	 * O imóvel está apto a receber a declaração caso não tenha contas em débito no ano de
	 * referência e que as contas vencidas no ano de referência tenham sido pagas no ano de
	 * referência.
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 18/04/2013
	 * @throws ControladorException
	 */
	public void gerarDeclaracaoAnualQuitacaoDebitos(int idFuncionalidadeIniciada, FaturamentoGrupo faturamentoGrupo)
					throws ControladorException;

	/**
	 * [UC3014] Emitir Declaração Anual Quitação Débitos
	 * 
	 * @author Hebert Falcão
	 * @created 27/04/2013
	 */
	public RelatorioArquivoDeclaracaoAnualQuitacaoDebitos emitirDeclaracaoAnualQuitacaoDebitos(Integer idFuncionalidadeIniciada,
					Integer idFaturamentoGrupo, Integer idImovel,
					Usuario usuario, Integer anoBaseDeclaracaoInformado)
					throws ControladorException;

	/**
	 * [UC3014] Emitir Declaração Anual Quitação Débitos
	 * [SB0001] Emitir Declaração Anual de Quitação de Débitos
	 * 
	 * @author Hebert Falcão
	 * @created 27/04/2013
	 */
	public Integer pesquisarQuitacaoDebitoAnualParaEmicaoQtd(Integer idFaturamentoGrupo, Integer anoReferencia)
					throws ControladorException;

	public BigDecimal calcularValorDebitoACobrar(Integer idImovel, Integer anoMesReferencia, boolean apenasDebitoTipoParcelamento);

	/**
	 * Método responsável por obter o percentual de esgoto
	 * 
	 * @param anoMesFaturamento
	 * @param idGerenciaRegional
	 * @param idLocalidade
	 * @param idCategoria
	 * @return
	 * @throws ControladorException
	 */
	Short obterPercentualEsgotoHistogramaEsgotoEconomia(Integer anoMesFaturamento, Integer idGerenciaRegional, Integer idLocalidade,
					Integer idCategoria) throws ControladorException;

	/**
	 * Método responsável por obter o valor da tarifa vigente por categoria
	 * 
	 * @param idConsumoTarifaDefault
	 * @param anoMesReferencia
	 * @param idCategoria
	 * @param isPrimeiraFaixa
	 * @return
	 * @throws ControladorException
	 */
	BigDecimal obterValorTarifaVigentePorCategoria(Integer idConsumoTarifaDefault, Integer anoMesReferencia, Integer idCategoria,
					Integer numeroFaixaInicio, Integer numeroFaixaFim, boolean isPrimeiraFaixa)
					throws ControladorException;
	
	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0004] – Obter os Créditos a Realizar
	 * 
	 * @author Anderson Italo
	 * @throws ControladorException
	 * @date 08/05/2013
	 */
	public Object[] obterCreditosARealizarFaturamentoImediato(Integer idImovel, Integer anoMesReferenciaFaturamento,
					boolean isRetornoFaturamento, Date dataGeracaoFaturamento)
					throws ControladorException;

	/**
	 * @param imovel
	 * @author Yara Souza
	 * @throws ControladorException
	 * @Date 19/08/2011
	 *       verifica se o imovel possui ligação de agua e esgoto e se possui hidrometro instalado.
	 *       Caso NÃO existam e ambas ligacoes de agua e esgoto nao possuam hidrometro instalado,
	 *       retorna TRUE.
	 *       Caso contrario retorna FALSE.
	 */
	public boolean verificarImovelNaoMedido(Imovel imovel) throws ControladorException;

	/**
	 * Inserir o Resumo Simulção Faturamento
	 * 
	 * @author eduardo henrique
	 * @date 15/10/2008 Alteração no método para chamar o método de inserção/Atualização por Sessão
	 *       Stateless (v0.05)
	 * @param colecaoResumoSimulacaoFaturamento
	 * @throws ControladorException
	 */
	public void inserirResumoSimulacaoFaturamento(Collection colecaoResumoSimulacaoFaturamento) throws ControladorException;

	/**
	 * @author Ailton Sousa
	 * @date 09/08/2011
	 * @param imovel
	 * @param anoMesFaturamento
	 * @return
	 */
	public BigDecimal obterValorTotalDebitosCobrados(Imovel imovel, Integer anoMesFaturamento);

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * 
	 * @author Yara Souza
	 * @data 20/08/2011
	 * @param colecaoCategorias
	 * @param valorTotalDebitos
	 * @param valorTotalCreditos
	 * @param colecaoCalcularValoresAguaEsgotoHelper
	 * @param colecaoResumoFaturamento
	 * @param faturamentoAtivCronRota
	 * @param imovel
	 * @param gerarAtividadeGrupoFaturamento
	 * @param anoMesReferencia
	 */
	public void gerarResumoSimulacaoFaturamento(Collection colecaoCategorias, BigDecimal valorTotalDebitos, BigDecimal valorTotalCreditos,
					Collection colecaoCalcularValoresAguaEsgotoHelper, Collection colecaoResumoFaturamento,
					FaturamentoAtivCronRota faturamentoAtivCronRota, Imovel imovel, Integer anoMesReferencia,
					FaturamentoSimulacaoComando faturamentoSimulacaoComando, FaturamentoGrupo faturamentoGrupo);

	public void gerarResumoFaturamentoSimulacaoAjusteCasal(FaturamentoGrupo faturamentoGrupo, Integer anoMesCorrente,
					Integer idFaturamentoAtividadeCronograma) throws ControladorException;

	/**
	 * @throws ControladorException
	 */

	public void desfazerPreFaturamentoPorGrupoERef() throws ControladorException;

	/**
	 * [UC0183] Inserir Débito A Cobrar
	 * [UC0479] Gerar Débito Ordem de Serviço
	 * [SB0001] - Verificar Marcação do Débito A Cobrar para Remuneração da Cobrança Administrativa
	 * [UC0145] Inserir Conta
	 * [SB0007] - Verificar Marcação do Débito Cobrado para Remuneração da Cobrança Administrativa
	 * 
	 * @param idImovel
	 * @param idDebitoTipo
	 * @return
	 */
	public Short verificarMarcacaoRemuneracaoCobAdminstrativa(Integer idImovel, Integer idDebitoTipo) throws ControladorException;

	/**
	 * @throws ControladorException
	 */
	public void executarAjusteValorDebitoCobradoCasal(Integer caso) throws ControladorException;

	/**
	 * @param idListaGrupos
	 * @throws ControladorException
	 */
	public void executarAjusteHidrometroInstaladoMeioCicloFaturamento(String idListaGrupos, Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * Consulta as contas por cliente para geração de fatura do cliente responsável
	 * 
	 * @author Luciano Galvao
	 * @date 26/06/2013
	 */
	public Map<Cliente, Collection<Conta>> consultarContasParaGerarFaturaClienteResponsavel(Collection<Cliente> clientes,
					GerarFaturaClienteResponsavelHelper gerarFaturaClienteResponsavelHelper) throws ControladorException;
	
	public Collection<Rota> consultarRotasGrupo(Integer idFaturamentoGrupo, Integer anoMesReferencia, int tipoConsulta)
					throws ControladorException;

	/**
	 * [UC3100] Consultar Histórico de Débito Automático Imóvel
	 * 24/07/2013
	 * 
	 * @param imovel
	 * @param ehContasOuGuia
	 *            true = ContaGeral, false GuiaPagamento
	 * @return retorna lista debito automatico movimento
	 */
	public Collection<DebitoAutomaticoMovimentoHelper> pesquisarDebitoAutomaticoMovimentoPorImovel(Integer idImovel, boolean ehContasOuGuia)
					throws ControladorException;

	/**
	 * [UC0146] Manter Conta
	 * [FS0035] Verificar vencimento débito automático
	 * 
	 * @author Hebert Falcão
	 * @date 08/08/2013
	 */
	public void verificarVencimentoContaDebitoAutomatico(Conta conta) throws ControladorException;

	/**
	 * [UC0352] Emitir Contas
	 * [SB0030] – Gerar Código de Barras
	 * 
	 * @author Anderson Italo
	 * @data 27/08/2011
	 * @param idImovel
	 * @throws ControladorException
	 */
	public void gerarCodigoDeBarras(EmitirContaTipo2Helper contaEmitirHelper) throws ControladorException;

	/**
	 * [UC0352] - Emitir Contas
	 * [SB0033] – Gerar Contas Modelo 2
	 * Responsável pela geração PDF com a conta
	 * 
	 * @author Anderson Italo
	 * @date 07/08/2013
	 */
	public List<RelatorioContaModelo2Bean> obterDadosRelatorioEmitirContasModelo2(FaturamentoGrupo faturamentoGrupo,
					Integer anoMesReferencia, Collection<EmitirContaTipo2Helper> colecaoEmitirContaTipo2Helper) throws ControladorException;

	public Object[] pesquisarContaMensagem(Integer anoMesFaturamento, Integer idFaturamentoGrupo, Integer idGerenciaRegional,
					Integer idLocalidade, Integer idSetorComercial) throws ControladorException;

	/**
	 * Método responsável por agrupar e ordenar os débitos cobrados
	 * 
	 * @param colecaoDebitoCobrado
	 * @param usarValorTotalDebito
	 * @return
	 * @throws ControladorException
	 */
	Collection<DebitoACobrar> agruparDebitoCobrado(Collection<DebitoCobrado> colecaoDebitoCobrado)
					throws ControladorException;

	/**
	 * @param debitoACobrar
	 * @param numeroTotalParcelas
	 * @param totalParcelasAntecipadas
	 * @return
	 * @throws ControladorException
	 */
	public BigDecimal calcularValorPrestacaoParcelasAntecipadas(DebitoACobrar debitoACobrar,
					Integer totalParcelasAntecipadas) throws ControladorException;
	
	/**
	 * [UC0482] Gerar Débito a Cobrar - Usado no 2aVia
	 * [UC0444] Gerar Emitir Extrato de débito [SB0006] - Gerar taxa cobrança extrato débito
	 * 
	 * @throws ControladorException
	 */
	public DebitoACobrar gerarDebitoACobrar(Integer idImovel, Integer anoMesReferencia, Integer idDebitoTipo, BigDecimal valorInformado)
					throws ControladorException;

	/**
	 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor.
	 * 6. O sistema apresenta os dados do imóvel Condomínio.
	 * 
	 * @param listaIdImovel
	 * @return
	 * @throws ControladorException
	 */
	public List<ClienteImovelCondominioHelper> pesquisarClienteImovelCondominioHelper(Integer idImovel, Integer idLigacaoTipo,
					Integer anoMesRefFaturamento)
					throws ControladorException;

	/**
	 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor.
	 * 6.4.7. Histórico de Medição Individualizada.
	 * 
	 * @param idImovel
	 * @param idLigacaoTipo
	 * @param anoMesRefFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public HistoricoMedicaoIndividualizadaHelper pesquisarHistoricoMedicaoIndividualizadaHelper(Integer idImovel,
					Integer idLigacaoTipo, Integer anoMesRefFaturamento) throws ControladorException;

	/**
	 * [UC3103] Cancelar Débito a Cobrar de Rateio por Macromedidor.
	 * 7. O sistema seleciona os débitos a cobrar de rateio dos imóveis vinculados para o mês
	 * informado.
	 * 
	 * @param idImovel
	 * @param idLigacaoTipo
	 * @param anoMesRefFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<DebitosACobrarRateioImoveisVinculadosHelper> pesquisarDebitosACobrarRateioImoveisVinculadosHelper(
					List<Integer> listaIdImovel,
					Integer idLigacaoTipo, Integer anoMesRefFaturamento) throws ControladorException;

	/**
	 * [UC0084] - Gerar Faturamento Imediato
	 * [SB0007] - Gerar Movimento Roteiro de Empresa
	 * 
	 * @author Anderson Italo
	 * @date 12/04/2013
	 * @throws ControladorException
	 */
	public Object[] obterDadosLeituraAnterior(Integer anoMesReferenciaFaturamento, Integer idMedicaoTipo, Imovel imovel)
					throws ControladorException;

	/**
	 * Pesquisa os dados necessário para a geração do relatório
	 * [UC3114] - Gerar Relatório Faturamento e Consumo Direto e Indireto Estadual
	 * 
	 * @author Victon Santos
	 * @created 27/09/2013
	 * @throws ControladorException
	 */
	public Collection<FaturamentoConsumoDiretoIndiretoEstadualRelatorioHelper> pesquisarDadosRelatorioFaturamentoConsumoDiretoIndiretoEstadual(
					Integer anoMes, Integer opcaoRelatorio) throws ControladorException;

	/**
	 * @param idImovel
	 * @param idDebitoTipo
	 * @return
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarPorDebitoTipo(Integer idImovel, Integer idDebitoTipo);

	/**
	 * @param colecaoContasSelecionadas
	 * @param cdValorARetirar
	 * @param contaMotivoRetificacao
	 * @param contaMotivoCancelamento
	 * @return
	 */
	public void retirarValorAguaEsgotoConjuntoContas(Collection colecaoContasSelecionadas, int cdValorARetirar,
					ContaMotivoRetificacao contaMotivoRetificacao, ContaMotivoCancelamento contaMotivoCancelamento, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Relatorio Maiores Consumidores
	 * 
	 * @author Victon Santos
	 * @return
	 */
	public Collection<RelatorioMaioresConsumidoresHelper> pesquisarDadosRelatorioMaioresConsumidores(Integer anoMes, Integer localidade,
					Integer registros)
					throws ControladorException;

	/**
	 * Relatorio Maiores Devedores
	 * 
	 * @author Victon Santos
	 * @return
	 */
	public Collection<RelatorioMaioresDevedoresHelper> pesquisarDadosRelatorioMaioresDevedores(Integer localidade, Integer registros)
					throws ControladorException;

	/**
	 * [UC3128] GerarRelatorioImóveiscomLigaçãoCortadacomConsumo
	 * 
	 * @author Hiroshi Gonçalves
	 * @created 09/12/2013
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarImoveisLigacaoCortadaComConsumo(int anoMesReferencia, Integer grupoFaturamento)
 throws ControladorException;

	/**
	 * @param anoMesRefInicial
	 * @param anoMesRefFinal
	 * @param idsCliente
	 * @throws ControladorException
	 */
	public void executarAjusteContasEnviadasHistorico(Integer anoMesRefInicial, Integer anoMesRefFinal, String[] idsCliente)
					throws ControladorException;

	/**
	 * [UC0146] Manter Conta
	 * [FS0038] - Verificar Bloqueio Colocar Conta em Revisão por Motivo de Conta Retida
	 * 
	 * @author Anderson Italo
	 * @date 20/12/2013
	 */
	public void verificarBloqueioColocarContaRevisaoMotivoContaRetida(Integer idContaMotivoRevisao, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Método que realiza o ajuste do faturamento para trazer de volta as contas que estão em
	 * conta_historico para tabela de conta devido a um erro do faturamento
	 * 
	 * @author Anderson Italo
	 * @date 09/01/2014
	 */
	public void executarAjusteContasEnviadasHistoricoPreFaturadasComValorZeroIndicadorEmissaoCampo3(String idsFaturamentoGrupo)
					throws ControladorException;

	/**
	 * @author Yara Souza
	 * @date 14/01/2014
	 */
	public void executarAjusteCoordenadasGIS(Collection colecao) throws ControladorException;

	/**
	 * [UC3118] Inserir Comando de Simulação de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 24/12/2013
	 */

	public Integer inserirComandoSimulacaoFaturamento(InserirComandoSimulacaoFaturamentoHelper inserirComandoSimulacaoFaturamentoHelper)
					throws ControladorException;

	/**
	 * [UC0111] Iniciar Processo
	 * [SB0009] - Obter Dados Complementares do Comando de Simulação de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 29/12/2013
	 * @throws ControladorException
	 */
	public String obterDadosComplementaresComandoSimulacaoFaturamento(FaturamentoSimulacaoComando faturamentoSimulacaoComando)
					throws ControladorException;

	/**
	 * Este caso de uso permite simular o faturamento quando a atividade executada for
	 * "simular faturamento do grupo"
	 * [UC0113] - Faturar Grupo de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 29/12/2013
	 * @throws ControladorException
	 */
	public void simularFaturamento(Integer anoMesFaturamentoCorrente, int idFaturamentoAtividade,
					FaturamentoSimulacaoComando faturamentoSimulacaoComando, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0146] Manter Conta
	 * [SB0003] - Retificar Conta
	 * 
	 * @author Anderson Italo
	 * @created 17/01/2014
	 * @throws ErroRepositorioException
	 */
	public ClienteConta pesquisarClienteContaPorTipoRelacao(Integer idConta, Integer idClienteRelacaoTipo) throws ControladorException;

	/**
	 * [UC0146] Manter Conta
	 * [SB0015] - Alterar Cliente Responsável de um Conjunto de Contas
	 * 
	 * @author Anderson Italo
	 * @date 20/01/2014
	 */
	public void alterarClienteResponsavelConjuntoContas(Collection<Conta> colecaoContas, Cliente clienteResponsavel, Usuario usuarioLogado)
					throws ControladorException;


	/**
	 * [UC3134] Manter Comando de Simulação de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 19/01/2014
	 */
	public Collection<FaturamentoSimulacaoComando> pesquisarFaturamentoSimulacaoComando(Integer numeroPagina, Date dataInicialComando,
					Date dataFinalComando, Short indicadorExecutado) throws ControladorException;

	/**
	 * [UC3134] Manter Comando de Simulação de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 19/01/2014
	 */
	public Integer pesquisarTotalRegistrosFaturamentoSimulacaoComando(Date dataInicialComando, Date dataFinalComando,
					Short indicadorExecutado) throws ControladorException;

	/**
	 * [UC3134] Manter Comando de Simulação de Faturamento
	 * [SB001] - Alterar comando de simulação de faturamento
	 * 
	 * @author Anderson Italo
	 * @date 28/01/2014
	 */
	public void atualizarComandoSimulacaoFaturamento(InserirComandoSimulacaoFaturamentoHelper inserirComandoSimulacaoFaturamentoHelper,
					FaturamentoSimulacaoComando faturamentoSimulacaoComando) throws ControladorException;

	/**
	 * @param valorTotalConta
	 * @param valorTotalCreditos
	 * @param valorTotalSemCreditos
	 * @param colecaoCreditosRealizados
	 * @param usuarioLogado
	 * @return
	 */

	public Object[] verificarVaorTotalContaNegativo(BigDecimal valorTotalConta, BigDecimal valorTotalCreditos,
					BigDecimal valorTotalSemCreditos, Collection colecaoCreditosRealizados, Usuario usuarioLogado,
					boolean gerarCreditoARealizar) throws ControladorException;

	/**
	 * @param colecaoCreditoRealizado
	 * @return
	 * @throws ControladorException
	 */

	public BigDecimal calcularValorTotalCreditoConta(Collection<CreditoRealizado> colecaoCreditoRealizado) throws ControladorException;

	/**
	 * Atauliza a tabela de consumo_historico com o consumo médio correto
	 * 
	 * @author Anderson Italo
	 * @date 10/02/2014
	 */
	public void executarAjusteErroCalculoConsumoMedioPercentualColeta(Collection<Integer> colecaoReferenciasAjuste)
					throws ControladorException;

	/**
	 * [UC0083] Gerar Dados Para Leitura
	 * 
	 * @author Hiroshi Gonçalves
	 * @date 11/02/2014
	 */
	public void enviarEmailImoveisContratoDemandaConsumoAVencer(Integer idGrupoFaturamento, Integer anoMesFaturamentoCorrente)
					throws ControladorException;
	
	/**
	 * [UC3132] Gerar Relatório de Contratos de Demanda de Consumo
	 * 
	 * @author Vicente Zarga
	 * @date 18/01/2014
	 * @throws ControladorException
	 */
	public Collection<ContratoDemandaConsumo> pesquisarDadosRelatorioContratoDemandaConsumo(Integer faturamentoGrupo,
					Integer[] localidades, String tipoContrato, Integer tarifaConsumo, Integer mesAnoFaturamentoInicial,
					Integer mesAnoFaturamentoFinal, Integer encerrado) throws ControladorException;

	/**
	 * [UC3132] Gerar Relatório de Contratos de Demanda de Consumo
	 * 
	 * @author Vicente Zarga
	 * @date 18/01/2014
	 * @throws ControladorException
	 */
	public Integer pesquisarDadosRelatorioContratoDemandaConsumoCount(Integer faturamentoGrupo, Integer[] localidades, String tipoContrato,
					Integer tarifaConsumo, Integer mesAnoFaturamentoInicial, Integer mesAnoFaturamentoFinal, Integer encerrado)
					throws ControladorException;

	/**
	 * [UC3055] Encerrar Faturamento
	 * [SB0007] Encerrar contratos de demanda de consumo
	 * 
	 * @author Vicente Zarga
	 * @date 27/01/2014
	 */
	public void encerrarContratoDemandaConsumo(String referencia) throws ControladorException;

	/**
	 * [UC0083] [UC0083]
	 * 
	 * @author Hiroshi Gonçalves
	 * @date 24/02/2014
	 */
	public ContratoDemandaConsumo pesquisarContratoDemandaConsumoVigenteComTarifa(Integer idImovel, Integer anoMesFaturamento)
					throws ControladorException;

	/**
	 * [UC0083] [UC0083]
	 * 
	 * @author Hiroshi Gonçalves
	 * @date 24/02/2014
	 */
	public ContratoDemandaConsumo pesquisarContratoDemandaConsumoVigenteComConsumoFixo(Integer idImovel, Integer anoMesFaturamento)
					throws ControladorException;


	/**
	 * Obter o endereço de entrega do cliente com detalhamentos
	 * 
	 * @author Hebert Falcão
	 * @date 02/02/2014
	 */
	public Object[] obterEnderecoEntregaClienteComDetalhamento(Imovel imovel) throws ControladorException;

	/**
	 * [UC3143] Inserir Débito a Cobrar de Rateio por Macromedidor
	 * 
	 * @author Ado Rocha
	 * @date 05/03/2014
	 */
	public void inserirDebitoACobrarRateioCondominio(RegistroAtendimento registroAtendimento, Usuario usuarioLogado, String[] ids,
					Integer numeroPrestacoes,
					BigDecimal valorTotalServico,
					BigDecimal valorEntrada, BigDecimal percentualAbatimento, Integer anoMesCobrancaDebito, DebitoTipo debitoTipo,
 CobrancaForma cobrancaForma, String parametroDebitoTipoRateio) throws ControladorException;


	/**
	 * @param faturamentoGrupo
	 * @param anoMesReferencia
	 * @param colecaoEmitirContaTipo2Helper
	 * @return
	 * @throws ControladorException
	 */

	public List<RelatorioContaModelo3Bean> obterDadosRelatorioEmitirContasModelo3(FaturamentoGrupo faturamentoGrupo,
					Integer anoMesReferencia, Collection<EmitirContaTipo2Helper> colecaoEmitirContaTipo2Helper) throws ControladorException;

	/**
	 * [XYZ] Gerar Relatório Situação Especial de Faturamento
	 * 
	 * @author Hebert Falcão
	 * @date 16/03/2014
	 */
	public Collection<FaturamentoSituacaoHistorico> consultarSituacaoEspecialDeFaturamento(RelatorioSituacaoEspecialFaturamentoHelper helper)
					throws ControladorException;

	/**
	 * [XYZ] Gerar Relatório Situação Especial de Faturamento
	 * 
	 * @author Hebert Falcão
	 * @date 16/03/2014
	 */
	public Integer consultarSituacaoEspecialDeFaturamentoCount(RelatorioSituacaoEspecialFaturamentoHelper helper)
					throws ControladorException;

	/**
	 * @param colecaoConta
	 * @throws ControladorException
	 */

	public void carregarContaParaHistograma(Collection colecaoConta) throws ControladorException;

	/**
	 * @author Yara Souza
	 * @date 04/03/2014
	 */
	public void executarRegeracaoHistograma(Integer anoMesRefInicial, Integer anoMesRefFinal) throws ControladorException;

	/**
	 * @throws ControladorException
	 */
	public void gerarDebitoACobrarContaComValorAMenor(Integer referencia, String idsGrupos) throws ControladorException;

	public void executarAjusteErroGeracaoContaCategoriaConsumoFaixa(Integer anoMesReferencia, String idsGrupos) throws ControladorException;

	public void executarAjusteContaZeradasEnviarHistorico(Integer anoMesReferencia) throws ControladorException;

	/**
	 * Pesquisa count dos dados necessário para a geração do relatório
	 * [UC0638] - Gerar Relatórios Anormalidade Consumo
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
	 * [UC0188] Manter Guia de Pagamento
	 * [SB0010] - Verificar Guia de Pagamento Prescrita
	 * 
	 * @author Anderson Italo
	 * @date 25/03/2014
	 */
	public void verificarGuiaPagamentoPrescrita(Usuario usuario,
					Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPagamentoPrestacaoHelper) throws ControladorException;
	
	/**
	 * [UC0146] Manter Conta
	 * [FS0039] - Verifica existência de débito prescrito
	 * 
	 * @author Anderson Italo
	 * @date 28/03/2014
	 */
	public void verificarContaPrescrita(Usuario usuario, Collection<Conta> colecaoConta) throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [FS0013 - Verifica existência de débito prescrito]
	 * 
	 * @author Anderson Italo
	 * @date 29/03/2014
	 */
	public void verificarExistenciaDebitoPrescrito(Usuario usuario, Collection<ContaValoresHelper> colecaoContaValoresHelper,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaValoresHelper) throws ControladorException;
}