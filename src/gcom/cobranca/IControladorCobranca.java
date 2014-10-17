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

package gcom.cobranca;

import gcom.arrecadacao.ArrecadadorMovimento;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.*;
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorProdutividade;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorSucesso;
import gcom.cobranca.opcaoparcelamento.PreParcelamentoOpcao;
import gcom.cobranca.parcelamento.*;
import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FaturamentoGrupoCronogramaMensal;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.DebitoTipo;
import gcom.financeiro.lancamento.bean.LancamentoItemContabilParcelamentoHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaHelper;
import gcom.gui.cobranca.*;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.ConsumoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.relatorio.cobranca.RelatorioRemuneracaoCobrancaAdministrativaBean;
import gcom.relatorio.cobranca.RelatorioRemuneracaoCobrancaAdministrativaHelper;
import gcom.relatorio.cobranca.parcelamento.ExtratoDebitoRelatorioHelper;
import gcom.relatorio.cobranca.parcelamento.RelacaoParcelamentoRelatorioHelper;
import gcom.relatorio.cobranca.parcelamento.RelatorioExtratoDebito;
import gcom.relatorio.ordemservico.DadosUltimosConsumosHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.procenge.comum.exception.NegocioException;

/**
 * Interface Controlador Cobranca PADRÃO
 * 
 * @author Raphael Rossiter
 * @date 26/06/2007
 */
public interface IControladorCobranca {

	/**
	 * [UC0178] Religar Automaticamente Imóvel Cortado BATCH - Permite a
	 * Religação automática de imóveis cortados Author: Rafael Santos Data:
	 * 02/01/2006
	 * 
	 * @exception ControladorException
	 *                Controlador Execpetion
	 */
	public void religarAutomaticamenteImovelCortado() throws ControladorException;

	/**
	 * [UC0178] Religar Automaticamente Imóvel Cortado Auhtor: Rafael Santos
	 * Data: 03/01/2006
	 * 
	 * @param id
	 *            Matricula do Imovel
	 * @param situacaoAguaLigado
	 *            Situação Agua
	 * @param dataReligacaoAgua
	 *            Data Religacao Agua
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void religarImovelCortado(String id, String situacaoAguaLigado, Date dataReligacaoAgua) throws ControladorException;

	/**
	 * [UC0067] Obter Débito do Imóvel ou Cliente Author: Rafael Santos Data:
	 * 04/01/2006 Permite a obtenção dos débtiso de um imóvel ou de um cliente
	 * 
	 * @param indicadorDebito
	 *            Inidicador de débito(1 - Imovel e 2 - Cliente
	 * @param idImovel
	 *            Matricula do Imovel
	 * @param codigoCliente
	 *            Codigo do Cliente
	 * @param clienteRelacaoTipo
	 *            Relação do Imovel com o Cliente
	 * @param anoMesInicialReferenciaDebito
	 *            Período de Referencia de Débito - Inicial
	 * @param anoMesFinalReferenciaDebito
	 *            Período de Referencia de Débito - Final
	 * @param anoMesInicialVencimentoDebito
	 *            Período de Vencimento de Débito - Inicial
	 * @param anoMesFinalVencimentoDebito
	 *            Período de Vencimento de Débito - Final
	 * @param indicadorPagamento
	 *            Indicador de Pagamento
	 * @param indicadorConta
	 *            Indicador de Conta
	 * @param indicadorDebitoACobrar
	 *            Indicador de Debito a Cobrar
	 * @param indicadorCreditoARealizar
	 *            Indicador de Credito a Realizar
	 * @param indicadorNotasPromissorias
	 *            Indicador de Notas Promissorias
	 * @param indicadorGuiasPagamento
	 *            Indicador de Guias de Pagamento
	 * @param indicadorCalcularAcrescimoImpontualidade
	 *            Indicador de Calculasr Acrescimo por Inpontualidade
	 * @param indicadorConsiderarPagamentoNaoClassificado
	 *            Indicador de Não considerar pagamento não classificados
	 * @exception ControladorException
	 *                Controlador Execption
	 */
	public ObterDebitoImovelOuClienteHelper obterDebitoImovelOuCliente(int indicadorDebito, String idImovel, String codigoCliente,
					Integer clienteRelacaoTipo, String anoMesInicialReferenciaDebito, String anoMesFinalReferenciaDebito,
					Date anoMesInicialVencimentoDebito, Date anoMesFinalVencimentoDebito, int indicadorPagamento, int indicadorConta,
					int indicadorDebitoACobrar, int indicadorCreditoARealizar, int indicadorNotasPromissorias, int indicadorGuiasPagamento,
					int indicadorCalcularAcrescimoImpontualidade, Boolean indicadorContas, SistemaParametro sistemaParametro,
					Date dataEmissaoDocumento, Short indicadorEmissaoDocumento, Short indicadorConsiderarPagamentoNaoClassificado,
					Short multa, Short jurosMora, Short atualizacaoTarifaria)
					throws ControladorException;

	/**
	 * [UC0216] Calcular Acrescimo por Impontualidade
	 * Calcula os acrescimmos por Impontualidade(multa,juros de mora e atualização monetaria)
	 * 
	 * @author Yara Souza
	 * @date 03/04/2012
	 * @param anoMesReferenciaDebito
	 * @param dataVencimento
	 * @param dataPagamento
	 * @param valorDebito
	 * @param valorMultasCobradas
	 * @param indicadorMulta
	 * @param anoMesArrecadacao
	 * @param idConta
	 * @param dataEmissaoDocumento
	 * @param emissaoDocumento
	 *            Na emissão do documento o indicador que estiver como 1 no
	 *            ParametroAcrescimosEmissaoDocumento deve ser considerado, o que tiver como 2 deve
	 *            ter o valor zerado, pois não será cobrado nesse momento. Caso não seja emissão, a
	 *            lógica
	 *            é invertida.
	 * @return
	 * @throws ControladorException
	 */
	@Deprecated
	public CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidadeOLD(int anoMesReferenciaDebito, Date dataVencimento,
					Date dataPagamento, BigDecimal valorDebito, BigDecimal valorMultasCobradas, short indicadorMulta,
					String anoMesArrecadacao, Integer idConta, Date dataEmissaoDocumento, Short emissaoDocumento)
					throws ControladorException;

	/**
	 * [UC0200] Inserir Débito Automático
	 * 
	 * @author Roberta Costa
	 * @created 04/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @param codigoBanco
	 *            Código do Banco
	 * @param codigoAgencia
	 *            Código da Agência
	 * @param identificacaoCliente
	 *            Identificação do Cliente no Banco
	 * @param dataOpcao
	 *            Data da Opção
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public String[] inserirDebitoAutomatico(String matriculaImovel, String codigoBanco, String codigoAgencia, String identificacaoCliente,
					Date dataOpcao) throws ControladorException;

	/**
	 * [UC0200] Inserir Negativador
	 * 
	 * @author Saulo Lima
	 * @created 09/06/2008
	 * @param Negativador
	 *            Negativador
	 * @throws ControladorException
	 *             Controlador Exception
	 * @return String
	 */
	public String inserirNegativador(Negativador negativador) throws ControladorException;

	/**
	 * [UC0201] Remover Débito Automático
	 * 
	 * @author Roberta Costa
	 * @created 09/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @param codigoBanco
	 *            Código do Banco
	 * @param codigoAgencia
	 *            Código da Agência
	 * @param identificacaoCliente
	 *            Identificação do Cliente no Banco
	 * @param dataOpcao
	 *            Data da Opção
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public String removerDebitoAutomatico(String matriculaImovel, String codigoBanco, String codigoAgencia, String identificacaoCliente,
					Date dataOpcao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * 
	 * @author Pedro Alexandre
	 * @created 01/02/2006
	 * @param grupoCobranca
	 *            Grupo de Cobrança
	 * @param anoMesReferencia
	 *            Ano/Mês de referência do ciclo de cobrança
	 * @param idCronogramaAtividadeAcaoCobranca
	 *            Código do cronograma da atividade da ação de cobrança
	 * @param idComandoAtividadeAcaoCobranca
	 *            Código do comando da atividade da ação de cobrança
	 * @param rotas
	 *            Coleção de rotas
	 * @param acaoCobranca
	 *            Ação de cobrança
	 * @param atividadeCobranca
	 *            Atividade de cobrança
	 * @param indicadorCriterio
	 *            Indicador do critério a ser utilizado
	 * @param criterioCobranca
	 *            Critério de cobrança
	 * @param cliente
	 *            Cliente
	 * @param relacaoClienteImovel
	 *            Tipo de relação entre cliente e imóvel
	 * @param anoMesReferenciaInicial
	 *            Ano/Mês de referência inicial
	 * @param anoMesReferenciaFinal
	 *            Ano/Mês de referência final
	 * @param dataVencimentoInicial
	 *            Data de vencimento inicial
	 * @param dataVencimentoFinal
	 *            Data de vencimento final
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	/*
	 * public void gerarAtividadeAcaoCobranca(CobrancaGrupo grupoCobranca, int
	 * anoMesReferencia, Integer idCronogramaAtividadeAcaoCobranca, Integer
	 * idComandoAtividadeAcaoCobranca, Collection<Rota> rotas, CobrancaAcao
	 * acaoCobranca, CobrancaAtividade atividadeCobranca, Integer
	 * indicadorCriterio, CobrancaCriterio criterioCobranca, Cliente cliente,
	 * Integer relacaoClienteImovel, int anoMesReferenciaInicial, int
	 * anoMesReferenciaFinal, Date dataVencimentoInicial, Date
	 * dataVencimentoFinal) throws ControladorException;
	 */

	/**
	 * Consultar Dados do Cliente Imovel Vinculado Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medição Indiviualizada
	 * 
	 * @param inscricaoImovel
	 *            Inscrição do Imovel
	 * @return Dados do Imovel Vinculado
	 * @throws ControladorException
	 */
	public Cliente consultarDadosClienteImovelUsuario(Imovel imovel) throws ControladorException;

	/**
	 * Consultar Historico Medição Individualizada Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medição Indiviualizada
	 * 
	 * @param imovelCondominio
	 *            Imovel Condominio
	 * @param anoMesFaturamento
	 *            Ano Mês Fauramento
	 * @return Coleção deDados do Historico Medição Individualizada
	 * @throws ControladorException
	 */
	public Collection consultarHistoricoMedicaoIndividualizada(Imovel imovelCondominio, String anoMesFaturamento)
					throws ControladorException;

	/**
	 * Consultar Matriculas dos Imoveis Vinculados do Imovel condominio Auhtor:
	 * Rafael Santos Data: 23/01/2006 [UC0179] Consultar Historico Medição
	 * Indiviualizada
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarConsumoHistoricoImoveisVinculados(ConsumoHistorico consumoHistorico) throws ControladorException;

	/**
	 * Consultar Dados Consumo Tipo Imovel Auhtor: Rafael Santos Data:
	 * 23/01/2006 [UC0179] Consultar Historico Medição Indiviualizada
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return
	 * @throws ControladorException
	 */
	public ConsumoTipo consultarDadosConsumoTipoConsumoHistorico(ConsumoHistorico consumoHistorico) throws ControladorException;

	/**
	 * Consultar Consumo Historico da Medicao Individualizada Auhtor: Rafael
	 * Santos Data: 23/01/2006 [UC0179] Consultar Historico Medição
	 * Indiviualizada
	 * 
	 * @param imovel
	 *            Imovel
	 * @param ligcaoTipo
	 *            Tipo de Ligacação
	 * @param anoMesFaturamento
	 *            Ano Mes Faturamento
	 * @return
	 * @throws ControladorException
	 */
	public ConsumoHistorico obterConsumoHistoricoMedicaoIndividualizada(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * 
	 * @author Fernanda Paiva
	 * @created 14/02/2006
	 * @param DebitoCreditoSituacaoAnterior
	 *            DebitoCreditoSituacaoAtual idConta
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarSituacaoConta(String codigoConta, int situacaoAtual, int anoMesReferenciaContabil) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * 
	 * @author Fernanda Paiva
	 * @created 15/02/2006
	 * @param DebitoCreditoSituacaoAtual
	 *            idGuiaPagamento
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarSituacaoGuiaPagamento(String codigoGuiaPagamento, int situacaoAtualGuia, int anoMesReferenciaContabil)
					throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * 
	 * @author Fernanda Paiva
	 * @created 20/02/2006
	 * @param idParcelamento
	 *            motivo parcelamentoSituacao
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarParcelamento(Integer codigoParcelamento, Integer parcelamentoSituacao, String motivo) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * 
	 * @author Fernanda Paiva
	 * @created 16/02/2006
	 * @param DebitoCreditoSituacaoAtual
	 *            idDebitoACobrar
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarSituacaoDebitoACobrar(String codigoDebitoACobrar, int situacaoAtualDebito, int anoMesReferenciaContabil)
					throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * 
	 * @author Vitor
	 * @created 02/09/2008
	 * @param DebitoCreditoSituacaoAtual
	 *            idDebitoACobrar
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarSituacaoDebitoACobrarHistorico(String codigoDebitoACobrar, int situacaoAtualDebito, int anoMesReferenciaContabil)
					throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * 
	 * @author Fernanda Paiva
	 * @created 16/02/2006
	 * @param DebitoCreditoSituacaoAtual
	 *            idCreditoARealizar
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarSituacaoCreditoARealizar(String codigoCreditoARealizar, int situacaoAtualCredito, int anoMesReferenciaContabil)
					throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito - remover debito a cobrar referente
	 * ao parcelamento
	 * 
	 * @author Fernanda Paiva
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerDebitoACobrarDoParcelamento(Integer codigoImovel, Integer codigoParcelamento) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito - remover credito a realizar
	 * referente ao parcelamento
	 * 
	 * @author Fernanda Paiva
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerCreditoARealizarDoParcelamento(Integer codigoImovel, Integer codigoParcelamento) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito - remover guia pagamento referente
	 * ao parcelamento
	 * 
	 * @author Fernanda Paiva
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerGuiaPagamentoDoParcelamento(Integer codigoImovel, Integer codigoParcelamento) throws ControladorException;

	/**
	 * [UC0246] Executar Atividade de Ação de Cobrança
	 * 
	 * @author Pedro Alexandre
	 * @created 03/02/2006
	 * @param idsAtividadesCobrancaCronograma
	 *            Array de id's de atividades de cobrança do cronograma
	 * @param idsAtividadesCobrancaEventuais
	 *            Array de id´s de atividades de cobrança eventuais
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void executarAtividadeAcaoCobranca(String[] idsAtividadesCobrancaCronograma, String[] idsAtividadesCobrancaEventuais,
					Usuario usuario) throws ControladorException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * 
	 * @author Pedro Alexandre
	 * @created 01/02/2006
	 * @param grupoCobranca
	 *            Grupo de Cobrança
	 * @param anoMesReferencia
	 *            Ano/Mês de referência do ciclo de cobrança
	 * @param idCronogramaAtividadeAcaoCobranca
	 *            Código do cronograma da atividade da ação de cobrança
	 * @param idComandoAtividadeAcaoCobranca
	 *            Código do comando da atividade da ação de cobrança
	 * @param rotas
	 *            Coleção de rotas
	 * @param acaoCobranca
	 *            Ação de cobrança
	 * @param atividadeCobranca
	 *            Atividade de cobrança
	 * @param indicadorCriterio
	 *            Indicador do critério a ser utilizado
	 * @param criterioCobranca
	 *            Critério de cobrança
	 * @param cliente
	 *            Cliente
	 * @param relacaoClienteImovel
	 *            Tipo de relação entre cliente e imóvel
	 * @param anoMesReferenciaInicial
	 *            Ano/Mês de referência inicial
	 * @param anoMesReferenciaFinal
	 *            Ano/Mês de referência final
	 * @param dataVencimentoInicial
	 *            Data de vencimento inicial
	 * @param dataVencimentoFinal
	 *            Data de vencimento final
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public GerarAtividadeAcaoCobrancaHelper gerarAtividadeAcaoCobranca(CobrancaGrupo grupoCobranca, int anoMesReferenciaCicloCobranca,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Collection<Rota> rotas, CobrancaAcao acaoCobranca,
					CobrancaAtividade atividadeCobranca, Integer indicadorCriterio, CobrancaCriterio criterioCobranca, Cliente cliente,
					ClienteRelacaoTipo relacaoClienteImovel, String anoMesReferenciaInicial, String anoMesReferenciaFinal,
					Date dataVencimentoInicial, Date dataVencimentoFinal, Date dataAtual, int idFuncionalidadeIniciada,
					Cliente clienteSuperior, Usuario usuario) throws ControladorException;

	/**
	 * [UC0246] Executar Atividade de Ação de Cobrança [SF0001] Selecionar Lista
	 * de Rotas
	 * 
	 * @author Pedro Alexandre
	 * @created 06/02/2006
	 * @param cobrancaGrupo
	 *            Grupo de cobrança
	 * @param cobrancaAcaoAtividadeComando
	 *            Cobrança Ação Atividade Comando
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public Collection<Rota> pesquisarListaRotasComando(CobrancaGrupo cobrancaGrupo,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando) throws ControladorException;

	/**
	 * [UC0246] Executar Atividade de Ação de Cobrança Pesquisa uma coleção de
	 * CobrancaAcaoAtividadeCronograma
	 * 
	 * @author Pedro Alexandre
	 * @created 01/02/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> pesquisarCobrancaAcaoAtividadeCronograma() throws ControladorException;

	/**
	 * [UC0246] Executar Atividade de Ação de Cobrança Pesquisa uma coleção de
	 * CobrancaAcaoAtividadeComando
	 * 
	 * @author Pedro Alexandre
	 * @created 01/02/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public Collection<CobrancaAcaoAtividadeComando> pesquisarCobrancaAcaoAtividadeComando() throws ControladorException;

	/**
	 * Inseri a cobrança situação historico na base passando a coleção de
	 * cobrança situação historico
	 * [UC0177] Informar Situacao Especial de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @date 20/03/2006
	 * @param collectionCobrancaSituacaoHistorico
	 * @return
	 */
	public void inserirCobrancaSituacaoHistorico(Collection collectionCobrancaSituacaoHistorico) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Verifica a existência de parcelamento no mês
	 * [FS0012] Verifica a existência de parcelamento no mês
	 * 
	 * @author Roberta Costa
	 * @date 21/03/2006
	 * @param codigoImovel
	 * @return Collection<Parcelamento>
	 */
	public Collection<Parcelamento> verificarParcelamentoMesImovel(Integer codigoImovel) throws ControladorException;

	/**
	 * Obtem a Lista de Rotas
	 * [UC0244] Manter Comando Ação Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 22/03/2006
	 * @param codigoImovel
	 * @param idRotaInicial
	 * @param idRotaFinal
	 * @param idSetorComercialInicial
	 * @param idSetorComercialFinal
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param idGerenciaRegional
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterListasRotas(String idRotaInicial, String idRotaFinal, String idSetorComercialInicial, String nuQuadraInicial,
					String nuQuadraFinal,
					String idSetorComercialFinal, String idLocalidadeInicial, String idLocalidadeFinal, String idGerenciaRegional,
					String idUnidadeNegocio) throws ControladorException;

	/**
	 * Obter Lista de Rotas Comando
	 * [UC0244] - Manter Comando de Ação de Cobrança
	 * Selecionar as Lsitas de Rotas do Comando
	 * [SF0009] - Selecionar Lista de Rotas do Comando
	 * 
	 * @author Rafael Santos
	 * @date 22/03/2006
	 * @param idCobrancaGrupo
	 * @return
	 */
	public Collection obterListaRotasComando(String idCobrancaGrupo, Collection colecaoIdCobrancaAtividadeComandoRota)
					throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Executa o Comando Eventual
	 * [SF0009] Executar Comando Eventual
	 * 
	 * @author Rafael Santos
	 * @date 23/03/2006
	 * @param cobrancaAtividade
	 * @param cobrancaAcaoAtividadeComando
	 * @param cobrancaAcao
	 * @param colecaoRotas
	 * @return
	 * @throws ControladorException
	 */
	public GerarAtividadeAcaoCobrancaHelper executarComandoEventual(CobrancaAtividade cobrancaAtividade,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, CobrancaAcao cobrancaAcao, Collection colecaoRotas,
					Usuario usuario) throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Verificar Seleção de pelo menos uma atividade de cobrança
	 * [FS0002] - Verificar Seleção de pelo menos uma atividade de cobrança
	 * 
	 * @author Rafael Santos
	 * @date 23/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterListaAtividadeCronogramaAcaoCobrancaComandadas() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Verificar Seleção de pelo menos uma atividade de cobrança
	 * [FS0002] - Verificar Seleção de pelo menos uma atividade de cobrança
	 * 
	 * @author Rafael Santos
	 * @date 23/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterListaAtividadesEventuaisAcaoCobrancaComandadas() throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Atualiza a situação das Contas para Efetuar Parcelamento
	 * atualizarContaEfetuarParcelamentoDebito
	 * 
	 * @author Roberta Costa
	 * @date 21/03/2006
	 * @param efetuarParcelamentoDebitosActionForm
	 * @param colecaoContaValores
	 * @return Collection
	 */
	public void atualizarContaEfetuarParcelamentoDebito(Conta conta) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Obtém as Opções de Parcelamento do Débito do Imóvel
	 * [SB0002] Obter Opções Parcelamento
	 * 
	 * @author Roberta Costa
	 * @date 23/03/2006
	 * @param colecaoContaValores
	 * @param verificaNulidade
	 *            TODO
	 * @param parcelamentoQuantidadePrestacao
	 *            TODO
	 * @param efetuarParcelamentoDebitosActionForm
	 * @return Collection
	 */
	public NegociacaoOpcoesParcelamentoHelper obterOpcoesDeParcelamento(Integer resolucaoDiretoria, Integer codigoImovel,
					BigDecimal valorEntradaInformado, Integer situacaoAguaId, Integer situacaoEsgotoId, Integer perfilImovel,
					String inicioIntervaloParcelamento, Integer indicadorRestabelecimento, Collection colecaoContaValores,
					BigDecimal valorDebitoAtualizado, BigDecimal valorTotalMultas, BigDecimal valorTotalJurosMora,
					BigDecimal valorTotalAtualizacoesMonetarias, Integer numeroReparcelamentoConsecutivos,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoHelper, Usuario usuarioLogado,
					BigDecimal valorDebitoACobrarParcelamentoImovel, Integer anoMesInicialReferenciaDebito,
					Integer anoMesFinalReferenciaDebito, IndicadoresParcelamentoHelper indicadoresParcelamentoHelper,
					String dataVencimentoEntradaParcelamento, boolean verificaNulidade,
					ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao) throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Exclui Comando de Atividade do Cronograma de Ação de Cobrança
	 * [SB0001] - Excluir Comando de Atividade de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 24/03/2006
	 * @param idCobrancaAcaoAtividadeCrongrama
	 */
	public void excluirComandoAtividadeCronogramaAcaoCobranca(String[] idsCobrancaAcaoAtividadeCronograma) throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Exclui Comando de Atividade de Eventual de Ação de Cobrança
	 * [SB0003] - Excluir Comando de Atividade Eventual de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 24/03/2006
	 * @param idCobrancaAcaoAtividadeCrongrama
	 */
	public void excluirComandoAtividadeEventualAcaoCobranca(String[] idsCobrancaAcaoAtividadeEventual) throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar Linhas do Criterios
	 * [SB0005] - Consultar Linhas do Criterios
	 * 
	 * @author Rafael Santos
	 * @date 24/03/2006
	 * @param idCriterioCobranca
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarLinhasCriterio(String idCriterioCobranca) throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Selecionar Critérios do Comando
	 * [SB0004] - Selecionar Critérios do Comando
	 * 
	 * @author Rafael Santos
	 * @date 24/03/2006
	 * @param idCobrancaAcao
	 * @param idCobrancaAcaoAtividadeComando
	 * @param indicadorCriterioComandoMarcado
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarCriteriosComando(String idCobrancaAcao) throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar Cobranca Ação CAtividade Comando
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public CobrancaAcaoAtividadeComando consultarCobrancaAcaoAtividadeComando(String idCobrancaAcaoAtividadeComando)
					throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar O Periodo Final da Conta para usar em Cobranca Ação Atividade
	 * Comando
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public String consultarPeriodoFinalContaCobrancaAcaoAtividadeComando() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar O Periodo Vencimento da Conta para usar em Cobranca Ação
	 * Atividade Comando
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 */
	public String consultarPeriodoVencimentoContaFinalCobrancaAcaoAtividadeComando() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Cobranca Grupo
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoCobrancaGrupo() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Cobranca Atividade
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoCobrancaAtividade() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Cobranca Acao
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoCobrancaAcao() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Gerencia Regionais
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoGerenciaRegional() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Unidade de Negocio
	 * 
	 * @author Rafael Santos
	 * @date 11/10/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoUnidadeNegocio() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Relações Cliente Tipo
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoClienteRelacaoTipo() throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Cobrança Atividade pela Atividade
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public CobrancaAtividade obterCobrancaAtividade(String idCobrancaAtividade) throws ControladorException;

	/**
	 * Permite excluir um comando de atividade de cobrança do crongrama ou
	 * alterar/excluir um comando deatividade de cobrança eventual
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Consultar as Coleção de Rotas do Setor Comercial
	 * 
	 * @author Rafael Santos
	 * @date 25/03/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoRota(String idSetorComercial) throws ControladorException;

	/**
	 * Permite inserir uma Resolução Diretoria
	 * [UC0217] Inserir Resolução de Diretoria
	 * 
	 * @author Rafael Corrêa
	 * @date 30/03/2006
	 */

	public Integer inserirResolucaoDiretoria(ResolucaoDiretoria resolucaoDiretoria, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Gera os Dados do Parcelamento
	 * [SB0008] - Gerar Dados do Parcelamento
	 * 
	 * @author Roberta Costa - Vivianne Sousa
	 * @date 29/03/2006 - 26/09/2006
	 * @param dataParcelamento
	 * @param valorConta
	 * @param valorGuiaPapagamento
	 * @param valorServicosACobrar
	 * @param valorParcelamentosACobrar
	 * @param valorCreditoARealizar
	 * @param valorAtualizacaoMonetaria
	 * @param valorJurosMora
	 * @param valorMulta
	 * @param valorDebitoAtualizado
	 * @param valorDescontoAcrescimos
	 * @param valorDescontoAntiguidade
	 * @param valorDescontoInatividade
	 * @param valorEntrada
	 * @param valorJurosParcelamento
	 * @param numeroPrestacoes
	 * @param valorPrestacao
	 * @param indicadorRestabelecimento
	 * @param indicadorContasRevisao
	 * @param indicadorGuiasPagamento
	 * @param indicadorAcrescimosImpotualidade
	 * @param indicadorDebitosACobrar
	 * @param indicadorCreditoARealizar
	 * @param percentualDescontoAcrescimos
	 * @param percentualDescontoAntiguidade
	 * @param percentualDescontoInatividadeLigacaoAgua
	 * @param imovel
	 * @param usuario
	 * @param parcelamentoPerfilId
	 * @param colecaoContaValores
	 * @param colecaoGuiaPagamentoValores
	 * @param colecaoDebitoACobrar
	 * @param colecaoCreditoARealizar
	 * @param taxaJuros
	 * @param indicadorConfirmacaoParcelamento
	 * @param cliente
	 * @return
	 * @throws ControladorException
	 */
	public Parcelamento gerarDadosParcelamento(Date dataParcelamento, BigDecimal valorConta, BigDecimal valorGuiaPapagamento,
					BigDecimal valorServicosACobrar, BigDecimal valorParcelamentosACobrar, BigDecimal valorCreditoARealizar,
					BigDecimal valorAtualizacaoMonetaria, BigDecimal valorJurosMora, BigDecimal valorMulta,
					BigDecimal valorDebitoAtualizado, BigDecimal valorDescontoAcrescimos, BigDecimal valorDescontoAntiguidade,
					BigDecimal valorDescontoInatividade, BigDecimal valorEntrada, BigDecimal valorJurosParcelamento,
					Short numeroPrestacoes, BigDecimal valorPrestacao, Short indicadorRestabelecimento, Short indicadorContasRevisao,
					Short indicadorGuiasPagamento, Short indicadorAcrescimosImpotualidade, Short indicadorDebitosACobrar,
					Short indicadorCreditoARealizar, BigDecimal percentualDescontoAcrescimos, BigDecimal percentualDescontoAntiguidade,
					BigDecimal percentualDescontoInatividadeLigacaoAgua, Imovel imovel, Usuario usuario, Integer parcelamentoPerfilId,
					Collection<ContaValoresHelper> colecaoContaValores, Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores,
					Collection<DebitoACobrar> colecaoDebitoACobrar, Collection<CreditoARealizar> colecaoCreditoARealizar,
					BigDecimal taxaJuros, Short indicadorConfirmacaoParcelamento, Cliente cliente, BigDecimal descontoSancoesRDEspecial,
					BigDecimal descontoTarifaSocialRDEspecial, Date dataEntradaParcelamento, String indicadorCobrancaParcelamento,
					Integer anoMesReferenciaDebitoInicial, Integer anoMesReferenciaDebitoFinal, BigDecimal percentualDescontoJurosMora,
					BigDecimal percentualDescontoMulta, BigDecimal percentualDescontoCorrecaoMonetaria,
					BigDecimal valorDescontoAcrescimosImpontualidadeNaPrestacao,
					Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao) throws ControladorException;

	/**
	 * Inserir um comando de atividade de cobrança eventual
	 * [UC0243] - Inserir Comando Ação de Cobrança
	 * Inserir cobranca acao atividade comando
	 * [SB0007] - Inserir cobranca acao atividade comando
	 * 
	 * @author Rafael Santos
	 * @throws ControladorException
	 * @date 04/04/2006
	 */
	//
	// public void inserirComandoAcaoCobrancaEventual(String idCobrancaAcao, String
	// idCobrancaAtividade, String idCobrancaGrupo,
	// String idGerenciaRegional, String idLocalidadeInicial, String idLocalidadeFinal, String
	// codigoSetorComercialInicial,
	// String codigoSetorComercialFinal, String idSetorComercialInicial, String
	// idSetorComercialFinal, String idCliente,
	// String idClienteRelacaoTipo, String anoMesReferencialInicial, String anoMesReferencialFinal,
	// String dataVencimentoContaInicial, String dataVencimentoContaFinal, String indicador, String
	// idRotaInicial,
	// String idRotaFinal, String idUnidadeNegocio) throws ControladorException;

	/**
	 * Este caso de uso permite a consulta de documentos de cobrança
	 * [UC0257] - Consultar Documentos de Cobrança
	 * Apresenta os itens dos documentos de cobrança
	 * [SB0001] - Apresenta Itens do Documento de Cobrança
	 * 
	 * @author Rafael Corrêa & Raphael Rossiter
	 * @date 05/04/2006
	 */

	public CobrancaDocumentoHelper apresentaItensDocumentoCobranca(CobrancaDocumento cobrancaDocumento) throws ControladorException;

	/**
	 * Inserir um comando de atividade de cobrança eventual
	 * [UC0243] - Inserir Comando Ação de Cobrança
	 * Verificar referência final menor que referência inicial
	 * [FS0012] - Verificar referência final menor que referência inicial
	 * 
	 * @author Rafael Santos
	 * @throws ControladorException
	 * @date 04/04/2006
	 */
	public void validarAnoMesInicialFinalComandoAcaoCobranca(String anoMesContaInicial, String anoMesContaFinal)
					throws ControladorException;

	/**
	 * Inserir um comando de atividade de cobrança eventual
	 * [UC0243] - Inserir Comando Ação de Cobrança
	 * Verificar data final menos que data inicial
	 * [FS0014] - Verificar data final menos que data inicial
	 * 
	 * @author Rafael Santos
	 * @throws ControladorException
	 * @date 04/04/2006
	 */
	public void verificarVencimentoContaComandoAcaoCobranca(String anoMesVencimentoInicial, String anoMesVencimentoFinal)
					throws ControladorException;

	/**
	 * Inserir Comando de Ação de Cobrança
	 * [UC0243] Inserir Comando Ação de Cobrança
	 * Consultar Cobranca Ação
	 * 
	 * @author Rafael Santos
	 * @date 04/04/2006
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public CobrancaAcao consultarCobrancaAcao(String idCobrancaAcao) throws ControladorException;

	/**
	 * Inserir Comando de Ação de Cobrança
	 * [UC0243] Inserir Comando Ação de Cobrança
	 * Consultar CobrancaAtividade
	 * 
	 * @author Rafael Santos
	 * @date 04/04/2006
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public CobrancaAtividade consultarCobrancaAtividade(String idCobrancaAtividade) throws ControladorException;

	/**
	 * Inserir Comando de Ação de Cobrança
	 * [UC0243] Inserir Comando Ação de Cobrança
	 * Atualizar Comando
	 * 
	 * @author Rafael Santos
	 * @date 05/04/2006
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public void atualizarCobrancaAcaoAtividadeComando(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando,
					GerarAtividadeAcaoCobrancaHelper gerarAtividadeAcaoCobrancaHelper) throws ControladorException;

	/**
	 * Inserir Comando de Ação de Cobrança
	 * [UC0243] Inserir Comando Ação de Cobrança
	 * Concluir Comando de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 05/04/2006
	 * @author eduardo henrique
	 * @date 30/08/2008
	 *       Alterações no [UC0243] para a v0.04
	 * @author Virgínia Melo
	 * @date 08/11/2008
	 *       Alterações no [UC0243] para a v0.06
	 * @author Virgínia Melo
	 * @date 05/08/2009
	 *       Adicionado campo valorLimiteEmissao
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public Collection concluirComandoAcaoCobranca(String periodoInicialConta, String periodoFinalConta,
					String periodoVencimentoContaInicial, String periodoVencimentoContaFinal, String[] idsCobrancaAcao,
					String idCobrancaAtividade, String idCobrancaGrupo, String idGerenciaRegional, String localidadeOrigemID,
					String localidadeDestinoID, String setorComercialOrigemCD, String setorComercialDestinoCD, String idCliente,
					String clienteRelacaoTipo, String indicador, String quadraInicial, String quadraFinal, String rotaInicial,
					String rotaFinal, String setorComercialOrigemID,
					String setorComercialDestinoID, String idComando, String unidadeNegocio, Usuario usuarioLogado, String titulo,
					String descricaoSolicitacao, String prazoExecucao, String quantidadeMaximaDocumentos, String indicadorImoveisDebito,
					String indicadorGerarBoletimCadastro, String codigoClienteSuperior, String empresa, String valorLimiteEmissao,
					byte[] arquivoImoveis, String arrecadador, CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComandoPrecedente,
					CobrancaCriterio cobrancaCriterio, String indicadorGerarRelacaoDocumento, String idCobrancaAcaoAtividadeComando,
					String formatoArquivo) throws ControladorException;

	/**
	 * Inserir Comando de Ação de Cobrança
	 * [UC0243] Inserir Comando Ação de Cobrança
	 * Executar Comando Concluir Comando de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 05/04/2006
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public Collection executarComandoAcaoCobranca(String periodoInicialConta, String periodoFinalConta,
					String periodoVencimentoContaInicial, String periodoVencimentoContaFinal, String[] idsCobrancaAcao,
					String idCobrancaAtividade, String idCobrancaGrupo, String idGerenciaRegional, String localidadeOrigemID,
					String localidadeDestinoID, String setorComercialOrigemCD, String setorComercialDestinoCD, String idCliente,
					String clienteRelacaoTipo, String indicador, String rotaInicial, String rotaFinal, String setorComercialOrigemID,
					String setorComercialDestinoID, String idComando, Usuario usuarioLogado, String titulo, String descricaoSolicitacao,
					String prazoExecucao, String quantidadeMaximaDocumentos, String indicadorImoveisDebito,
					String indicadorGerarBoletimCadastro, String codigoClienteSuperior) throws ControladorException;

	/**
	 * Obter Lista de Rotas Comando
	 * [UC0243] - Inserir Comando de Ação de Cobrança
	 * Selecionar as Lsitas de Rotas do Comando
	 * [SF0009] - Selecionar Lista de Rotas do Comando
	 * 
	 * @author Rafael Santos
	 * @date 22/03/2006
	 * @param idCobrancaGrupo
	 * @return
	 */
	public Collection obterListaRotasComando(String idCobrancaGrupo, String idCobrancaAcaoAtividadeComando) throws ControladorException;

	/**
	 * Permite atualizar uma Resolução de Diretoria
	 * [UC0218] Manter Resolução de Diretoria
	 * [SF0001] - Atualizar Resolução de Diretoria
	 * 
	 * @author Rafael Corrêa
	 * @date 10/04/2006
	 */

	public void atualizarResolucaoDiretoria(ResolucaoDiretoria resolucaoDiretoria, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Obtém o Perfil do parcelamento para o imóvel
	 * 
	 * @author Roberta Costa
	 * @date 24/04/2006
	 * @param codigoImovel
	 * @param resolucaoDiretoria
	 * @param verificaNulidadde
	 *            TODO
	 * @param imovelSituacao
	 * @param subcategoria
	 * @return ParcelamentoPerfil
	 */
	public ParcelamentoPerfil obterPerfilParcelamento(Integer codigoImovel, Integer imovelSituacaoId, Integer perfilImovelId,
					Integer subcategoriaId, Integer resolucaoDiretoria, boolean verificaNulidadde) throws ControladorException;

	/**
	 * Manter Comando de Ação de Cobrança
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Concluir Comando de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 24/04/2006
	 * @author eduardo henrique
	 * @date 02/09/2008
	 *       Alterações no [UC0244] para a v0.04
	 * @author Virgínia Melo
	 * @date 07/11/2008
	 *       Alterações no [UC0244] para a v0.06
	 * @author Virgínia Melo
	 * @date 06/08/2009
	 *       Adicionado campo valorLimiteEmissao
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public void concluirManterComandoAcaoCobranca(String periodoInicialConta, String periodoFinalConta,
					String periodoVencimentoContaInicial, String periodoVencimentoContaFinal, String idCobrancaAcao,
					String idCobrancaAtividade, String idCobrancaGrupo, String idGerenciaRegional, String localidadeOrigemID,
					String localidadeDestinoID, String setorComercialOrigemCD, String setorComercialDestinoCD, String idCliente,
					String clienteRelacaoTipo, String indicador, String quadraInicial, String quadraFinal, String rotaInicial,
					String rotaFinal, String setorComercialOrigemID,
					String setorComercialDestinoID, String idCobrancaAcaoAtividadeComando, Date realizacao, Date comando,
					Date ultimaDataAtualizacao, Usuario usuario, Empresa empresa, Integer quantidadeDocumentos, BigDecimal valorDocumentos,
					Integer quantidadeItensCobrados, String idComando, String unidadeNegocio, String titulo, String descricaoSolicitacao,
					String prazoExecucao, String quantidadeMaximaDocumentos, String indicadorImoveisDebito,
					String indicadorGerarBoletimCadastro, String codigoClienteSuperior, String valorLimiteEmissao, byte[] arquivoImoveis,
					String arrecadador, CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComandoPrecedente,
					CobrancaCriterio cobrancaCriterio, String indicadorGerarRelacaoDocumento, String formatoArquivo)
					throws ControladorException;

	/**
	 * Manter Comando de Ação de Cobrança
	 * [UC0244] Manter Comando Ação de Cobrança
	 * Executar Comando Concluir Comando de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 24/04/2006
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ControladorException
	 */
	public void executarComandoManterAcaoCobranca(String periodoInicialConta, String periodoFinalConta,
					String periodoVencimentoContaInicial, String periodoVencimentoContaFinal, String idCobrancaAcao,
					String idCobrancaAtividade, String idCobrancaGrupo, String idGerenciaRegional, String localidadeOrigemID,
					String localidadeDestinoID, String setorComercialOrigemCD, String setorComercialDestinoCD, String idCliente,
					String clienteRelacaoTipo, String indicador, String rotaInicial, String rotaFinal, String setorComercialOrigemID,
					String setorComercialDestinoID, String idComando, String idCobrancaAcaoAtividadeComando, Date ultimaDataAtualizacao,
					Date comando, Date realizacao, Usuario usuario, Empresa empresa, Integer quantidadeDocumentos,
					BigDecimal valorDocumentos, Integer quantidadeItensCobrados, String titulo, String descricaoSolicitacao,
					String prazoExecucao, String quantidadeMaximaDocumentos, String indicadorImoveisDebito,
					String indicadorGerarBoletimCadastro, String codigoClienteSuperior) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Faz as atualizações e inserções do parcelamento do débito
	 * concluirParcelamentoDebitos
	 * 
	 * @author Roberta Costa - Vivianne Sousa
	 * @date 26/04/2006 - 26/09/2006
	 * @param colecaoContaValores
	 * @param colecaoGuiaPagamentoValores
	 * @param colecaoDebitoACobrar
	 * @param colecaoCreditoARealizar
	 * @param indicadorRestabelecimento
	 * @param indicadorContasRevisao
	 * @param indicadorGuiasPagamento
	 * @param indicadorAcrescimosImpotualidade
	 * @param indicadorDebitosACobrar
	 * @param indicadorCreditoARealizar
	 * @param imovel
	 * @param valorEntradaInformado
	 * @param valorASerNegociado
	 * @param valorASerParcelado
	 * @param dataParcelamento
	 * @param valorTotalContaValores
	 * @param valorGuiasPagamento
	 * @param valorDebitoACobrarServico
	 * @param valorDebitoACobrarParcelamento
	 * @param valorCreditoARealizar
	 * @param valorAtualizacaoMonetaria
	 * @param valorJurosMora
	 * @param valorMulta
	 * @param valorDebitoTotalAtualizado
	 * @param descontoAcrescimosImpontualidade
	 * @param descontoAntiguidadeDebito
	 * @param descontoInatividadeLigacaoAgua
	 * @param percentualDescontoAcrescimosImpontualidade
	 * @param percentualDescontoAntiguidadeDebito
	 * @param percentualDescontoInatividadeLigacaoAgua
	 * @param parcelamentoPerfilId
	 * @param valorAcrescimosImpontualidade
	 * @param valorDebitoACobrarServicoLongoPrazo
	 * @param valorDebitoACobrarServicoCurtoPrazo
	 * @param valorDebitoACobrarParcelamentoLongoPrazo
	 * @param valorDebitoACobrarParcelamentoCurtoPrazo
	 * @param numeroPrestacoes
	 * @param valorPrestacao
	 * @param valorEntradaMinima
	 * @param taxaJuros
	 * @param indicadorConfirmacaoParcelamento
	 * @param cliente
	 * @param usuarioLogado
	 * @param cpfClienteParcelamentoDigitado
	 * @param descontoSancoesRDEspecial
	 * @param descontoTarifaSocialRDEspecial
	 * @param dataEntradaParcelamento
	 * @param pagamento
	 * @param numeroMesesEntreParcelas
	 * @param numeroParcelasALancar
	 * @param numeroMesesInicioCobranca
	 * @return
	 * @throws ControladorException
	 */
	public Integer concluirParcelamentoDebitos(Collection<ContaValoresHelper> colecaoContaValores,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores, Collection<DebitoACobrar> colecaoDebitoACobrar,
					Collection<CreditoARealizar> colecaoCreditoARealizar, String indicadorRestabelecimento, String indicadorContasRevisao,
					String indicadorGuiasPagamento, String indicadorAcrescimosImpotualidade, String indicadorDebitosACobrar,
					String indicadorCreditoARealizar, Imovel imovel, BigDecimal valorEntradaInformado, BigDecimal valorASerNegociado,
					BigDecimal valorASerParcelado, Date dataParcelamento, BigDecimal valorTotalContaValores,
					BigDecimal valorGuiasPagamento, BigDecimal valorDebitoACobrarServico, BigDecimal valorDebitoACobrarParcelamento,
					BigDecimal valorCreditoARealizar, BigDecimal valorAtualizacaoMonetaria, BigDecimal valorJurosMora,
					BigDecimal valorMulta, BigDecimal valorDebitoTotalAtualizado, BigDecimal descontoAcrescimosImpontualidade,
					BigDecimal descontoAntiguidadeDebito, BigDecimal descontoInatividadeLigacaoAgua,
					BigDecimal percentualDescontoAcrescimosImpontualidade, BigDecimal percentualDescontoAntiguidadeDebito,
					BigDecimal percentualDescontoInatividadeLigacaoAgua, Integer parcelamentoPerfilId,
					BigDecimal valorAcrescimosImpontualidade, BigDecimal valorDebitoACobrarServicoLongoPrazo,
					BigDecimal valorDebitoACobrarServicoCurtoPrazo, BigDecimal valorDebitoACobrarParcelamentoLongoPrazo,
					BigDecimal valorDebitoACobrarParcelamentoCurtoPrazo, Short numeroPrestacoes, BigDecimal valorPrestacao,
					BigDecimal valorEntradaMinima, BigDecimal taxaJuros, String indicadorConfirmacaoParcelamento, Cliente cliente,
					Usuario usuarioLogado, String cpfCnpjClienteParcelamentoDigitado, BigDecimal descontoSancoesRDEspecial,
					BigDecimal descontoTarifaSocialRDEspecial, Date dataEntradaParcelamento, Pagamento pagamento,
					Integer numeroMesesEntreParcelas, Integer numeroParcelasALancar, Integer numeroMesesInicioCobranca,
					Integer idBoletoBancario, String indicadorCobrancaParcelamento, String indicadorParcelamentoCobrancaBancaria,
					Integer numeroDiasVencimentoEntrada, String indicadorPessoaFisicaJuridica, Integer anoMesReferenciaDebitoInicial,
					Integer anoMesReferenciaDebitoFinal, BigDecimal percentualDescontoJurosMora, BigDecimal percentualDescontoMulta,
					BigDecimal percentualDescontoCorrecaoMonetaria, BigDecimal valorDescontoAcrescimosImpontualidadeNaPrestacao,
					BigDecimal valorFinalFinanciamento,
					Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao) throws ControladorException;

	/**
	 * Inseri um Cronograma de Cobrança com as Ações de Cobranças e suas
	 * Atividades
	 * [UC0312] Inserir Cronograma de Cobrança
	 * 
	 * @author Flávio Cordeiro
	 * @data 25/04/2006
	 * @param cobrancaGrupoCronogramaMes
	 *            ,
	 *            cobrancaAcaoCronograma,
	 *            cobrancasAtividadesParaInsercao(Collection)
	 */
	public void inserirCobrancaCronograma(Collection colecaoCobrancaCronogramaHelper, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Filtrar Cronograma de Cobrança com as Ações de Cobranças e suas
	 * Atividades
	 * [UC03125] Inserir Cronograma de Cobrança
	 * 
	 * @author Flávio Cordeiro
	 * @data 29/04/2006
	 *       Customização v0.05
	 * @author Virgínia Melo
	 * @data 15/09/2008
	 *       Desfazer alteração para v0.06
	 * @author Virgínia Melo
	 * @data 03/11/2008
	 * @param idGrupoCobranca
	 *            , mesAno, programaCobranca
	 * @return Colecao de CobrancaAcaoAtividadeCronograma
	 */
	public FiltroCobrancaAcaoAtividadeCronograma filtrarCobrancaCronograma(String idGrupoCobranca, String mesAno)
					throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito
	 * 
	 * @author Fernanda Paiva
	 * @created 29/04/2006
	 * @param codigoImovel
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarDadosParcelamentoParaImovel(Integer codigoImovel) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamentos por entrada não paga
	 * Este caso de uso permite desfazer os parcelamentos de débitos efetuados
	 * no mês cuja entrada não tenha sido paga.
	 * 
	 * @author Fernanda Paiva
	 * @created 02/05/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void desfazerParcelamentosPorEntradaNaoPaga(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Desfazer Parcelamentos Débitos
	 * Este caso de uso permite desfazer os parcelamentos de débitos
	 * 
	 * @author Fernanda Paiva
	 * @created 02/05/2006
	 * @author Saulo Lima
	 * @date 13/07/2009
	 *       Recuperar Guias de Pagamento do Histórico + Identação + Tipo de retorno
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void desfazerParcelamentosDebito(String motivo, Integer codigo, Usuario usuario) throws ControladorException;

	/**
	 * [UC0316] Inserir Critério de Cobrança
	 * Este caso de uso inseriR a cobrança critério e as linhas da cobrança
	 * critério
	 * 
	 * @author Sávio luiz
	 * @created 04/05/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public Integer inserirCobrancaCriterio(CobrancaCriterio cobrancaCriterio, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0317] Manter Critério de Cobrança
	 * Este caso de uso atualiza a cobrança critério e as linhas da cobrança
	 * critério
	 * [SB0001] Atualizar Critério de Cobrança
	 * 
	 * @author Sávio luiz
	 * @created 11/05/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void atualizarCobrancaCriterio(CobrancaCriterio cobrancaCriterio, Collection colecaoCobrancaCriterioLinha,
					Collection colecaoCobrancaCriterioLinhaRemovidas, Collection colecaoCriterioSituacaoCobrancaNovos,
					Collection colecaoCriterioSituacaoLigacaoAguaNovos, Collection colecaoCriterioSituacaoLigacaoEsgotoNovos,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0317] Manter Critério de Cobrança
	 * Este caso de uso remove a cobrança critério e as linhas da cobrança
	 * critério
	 * [SB0002] Excluir Critério de Cobrança
	 * 
	 * @author Sávio luiz
	 * @created 11/05/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerCobrancaCriterio(String[] idsCobrancaCriterio, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Remover Contratos de Cobrança
	 * 
	 * @author Virgínia Melo
	 * @date 04/12/2008
	 * @param idsContratoCobranca
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void removerContratoCobranca(Integer[] idsContratoCobranca, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Manter - Atualizar um Cornograma de Cobrança com as Ações de Cobranças e
	 * suas Atividades
	 * [UC0313] Manter Cornograma de Cobrança
	 * 
	 * @author Flávio Cordeiro
	 * @data 05/05/2006
	 * @param cobrancaGrupoCronogramaMes
	 *            ,
	 *            cobrancaAcaoCronograma,
	 *            cobrancasAtividadesParaInsercao(Collection)
	 */

	public void atualizarCobrancaCronograma(Collection colecaoCobrancaCronogramaHelper, Collection colecaoCronogramaHelperErroAtualizacao,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0313] Manter Cronograma Cobrança
	 * [SB0002] Excluir Cronograma de Cobrança
	 * 
	 * @param ids
	 * @throws ControladorException
	 */

	public void removerCobrancaCronograma(Collection<CobrancaCronogramaHelper> colecaocobrancaCronogramaHelperRemover)
					throws ControladorException;

	/**
	 * [UC0313] Manter Cronograma Cobrança
	 * [SB0002] Excluir Cronograma de Cobrança
	 * 
	 * @param ids
	 * @throws ControladorException
	 */
	public void removerCobrancaCronograma(String[] idsCobrancaCronograma, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0313] Manter Cronograma Cobrança
	 * [SB0002] Excluir Cobrança Acao Cronograma
	 * 
	 * @param ids
	 * @throws ControladorException
	 */

	public void removerCobrancaAtividadeCronograma(String[] ids) throws ControladorException;

	/**
	 * Inseri o Perfil de Parcelamento na base
	 * [UC0220] Inserir Perfil de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 10/05/2006
	 * @param parcelamentoPerfilNova
	 * @param collectionParcelamentoQuantidadeReparcelamentoHelper
	 * @param collectionParcelamentoDescontoInatividade
	 * @param collectionParcelamentoDescontoAntiguidade
	 * @return o idPerfilParcelamneto
	 */
	public Integer inserirPerfilParcelamento(ParcelamentoPerfil parcelamentoPerfilNova,
					Collection collectionParcelamentoQuantidadeReparcelamentoHelper, Collection collectionParcelamentoDescontoInatividade,
					Collection collectionParcelamentoDescontoAntiguidade, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0221] Manter Perfil de Parcelamento
	 * Remove um objeto do tipo ParcelamentoPerfil no BD
	 * 
	 * @author Vivianne Sousa
	 * @date 11/05/2006
	 * @param ids
	 * @return
	 */
	public void removerPerfilParcelamento(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Atualizar o Perfil de Parcelamento na base
	 * [UC0221] Manter Perfil de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 17/05/2006
	 * @param parcelamentoPerfilNova
	 * @param collectionParcelamentoQuantidadeReparcelamentoHelper
	 * @param collectionParcelamentoDescontoInatividade
	 * @param collectionParcelamentoDescontoAntiguidade
	 * @param collectionParcelamentoQuantidadeReparcelamentoHelperLinhaRemovidas
	 * @param collectionParcelamentoDescontoInatividadeLinhaRemovidas
	 * @param collectionParcelamentoDescontoAntiguidadeLinhaRemovidas
	 * @param collectionParcelamentoQuantidadePrestacaoLinhaRemovidas
	 * @return
	 */
	public void atualizarPerfilParcelamento(ParcelamentoPerfil parcelamentoPerfil,
					Collection collectionParcelamentoQuantidadeReparcelamentoHelper, Collection collectionParcelamentoDescontoInatividade,
					Collection collectionParcelamentoDescontoAntiguidade,
					Collection collectionParcelamentoQuantidadeReparcelamentoHelperLinhaRemovidas,
					Collection collectionParcelamentoDescontoInatividadeLinhaRemovidas,
					Collection collectionParcelamentoDescontoAntiguidadeLinhaRemovidas,
					Collection collectionParcelamentoQuantidadePrestacaoLinhaRemovidas, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Filtrar os Comandos de Ação de Cobrança tipo comando Cronograma
	 * [UC0326] - Filtrar Comandos de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 10/05/2006
	 *       Customização ADA 01/09/2008
	 * @author Virgínia Melo
	 * @return filtroCobrancaAcaoAtividadeCronograma
	 * @throws ControladorException
	 */
	public FiltroCobrancaAcaoAtividadeCronograma construirFiltroCobrancaAcaoAtividadeCronograma(
					String anoMesPeriodoReferenciaCobrancaInicial, String anoMesPeriodoReferenciaCobrancaFinal, String[] grupoCobranca,
					String[] acaoCobranca, String[] atividadeCobranca, String dataPeriodoPrevisaoComandoInicial,
					String dataPeriodoPrevisaoComandoFinal, String dataPeriodoComandoInicial, String dataPeriodoComandoFinal,
					String dataPeriodoRealizacaoComandoInicial, String dataPeriodoRealizacaoComandoFinal,
					String intervaloValorDocumentosInicial, String intervaloValorDocumentosFinal,
					String intervaloQuantidadeDocumentosInicial, String intervaloQuantidadeDocumentosFinal,
					String intervaloQuantidadeItensDocumentosInicial, String intervaloQuantidadeItensDocumentosFinal,
					String situacaoCronograma, String situacaoComando) throws ControladorException;

	/**
	 * Consultar Comando de Ação de Cobrança
	 * [UC0325] - Consultar Comandos de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 10/05/2006
	 * @param filtroCobrancaAcaoAtividadeCronograma
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> pesquisarCobrancaAcaoAtividadeCronograma(
					FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronograma) throws ControladorException;

	/**
	 * Consultar Comando de Ação de Cobrança - Cronograma
	 * 
	 * @author Saulo Lima
	 * @date 25/06/2010
	 * @param idCobrancaGrupo
	 * @param idCobrancaAcao
	 * @param idComando
	 * @param dataInicial
	 * @param dataFinal
	 * @return Collection<CobrancaAcaoAtividadeCronograma>
	 * @throws ControladorException
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> pesquisarCobrancaAcaoAtividadeCronograma(Integer idCobrancaGrupo,
					Integer idCobrancaAcao, Integer idComando, Date dataInicial, Date dataFinal) throws ControladorException;

	/**
	 * Consultar Comando de Ação de Cobrança - Comando
	 * 
	 * @author Saulo Lima
	 * @date 25/06/2010
	 * @param idCobrancaGrupo
	 * @param idCobrancaAcao
	 * @param idComando
	 * @param dataInicial
	 * @param dataFinal
	 * @param idLocalidade
	 * @param codigoSetorComercial
	 * @return Collection<CobrancaAcaoAtividadeComando>
	 * @throws ControladorException
	 */
	public Collection<CobrancaAcaoAtividadeComando> pesquisarCobrancaAcaoAtividadeComando(Integer idCobrancaGrupo, Integer idCobrancaAcao,
					Integer idComando, Date dataInicial, Date dataFinal, Integer idLocalidade, Integer codigoSetorComercial)
					throws ControladorException;

	/**
	 * [UC0325] Consultar Comandos Ação de Cobrança
	 * Consultar Comando Cobranca Ação Atividade CobrancaVerificar pelo ID
	 * [FS0002] - Verificar Seleção de pelo menos uma atividade de cobrança
	 * 
	 * @author Rafael Santos
	 * @date 11/05/2006
	 * @return
	 * @throws ControladorException
	 */
	public CobrancaAcaoAtividadeCronograma obterCobrancaAcaoAtividadeCronograma(String idCobrancaAcaoAtividadeCronograma)
					throws ControladorException;

	/**
	 * Filtrar os Comandos de Ação de Cobrança tipo comando Eventual
	 * [UC0326] - Filtrar Comandos de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 12/05/2006
	 * @return filtroCobrancaAcaoAtividadeComando
	 * @throws ControladorException
	 */
	public FiltroCobrancaAcaoAtividadeComando construirFiltroCobrancaAcaoAtividadeEventual(String[] grupoCobranca, String[] acaoCobranca,
					String[] atividadeCobranca, String anoMesPeriodoReferenciaContasInicial, String anoMesPeriodoReferenciaContasFinal,
					String dataPeriodoComandoInicial, String dataPeriodoComandoFinal, String dataPeriodoRealizacaoComandoInicial,
					String dataPeriodoRealizacaoComandoFinal, String dataPeriodoVencimentoContasInicial,
					String dataPeriodoVencimentoContasFinal, String intervaloValorDocumentosInicial, String intervaloValorDocumentosFinal,
					String intervaloQuantidadeDocumentosInicial, String intervaloQuantidadeDocumentosFinal,
					String intervaloQuantidadeItensDocumentosInicial, String intervaloQuantidadeItensDocumentosFinal,
					String situacaoComando, String indicadorCriterio, String idGerenciaRegional, String idLocalidadeInicial,
					String idLocalidadeFinal, String codigoSetorComercialInicial, String codigoSetorComercialFinal, String nuQuadraInicial,
					String nuQuadraFinal, String idRotaInicial, String idRotaFinal, String idCliente, String idClienteRelacaoTipo,
					String criterioCobranca, String unidadeNegocio)
					throws ControladorException;

	/**
	 * Filtrar os Comandos de Ação de Cobrança tipo comando Eventual
	 * [UC0326] - Filtrar Comandos de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 15/05/2006
	 * @param localidadeID
	 * @param setorComercialCD
	 * @return
	 * @throws ControladorException
	 */
	public SetorComercial obterSetorComercialLocalidade(String localidadeID, String setorComercialCD) throws ControladorException;

	/**
	 * Filtrar os Comandos de Ação de Cobrança tipo comando Eventual
	 * [UC0326] - Filtrar Comandos de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 15/05/2006
	 * @param codigoSetorComercial
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterColecaoRotaSetorComercialLocalidade(String codigoSetorComercial, String idLocalidade)
					throws ControladorException;

	/**
	 * Filtrar os Comandos de Ação de Cobrança tipo comando Eventual
	 * [UC0326] - Filtrar Comandos de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 15/05/2006
	 * @param localidadeID
	 * @return
	 */
	public Localidade obterLocalidadeGerenciaRegional(String localidadeID) throws ControladorException;

	/**
	 * Filtrar os Comandos de Ação de Cobrança tipo comando Eventual
	 * [UC0326] - Filtrar Comandos de Ação de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 15/05/2006
	 * @param idCliente
	 * @return
	 */
	public Cliente obterCliente(String idCliente) throws ControladorException;

	/**
	 * Filtrar os Comandos de Ação de Cobrança tipo comando Eventual
	 * [UC0326] - Filtrar Comandos de Ação de Cobrança
	 * 
	 * @author Administrador
	 * @date 19/05/2006
	 * @param idCobrancaCriterio
	 * @return
	 */
	public CobrancaCriterio obterCobrancaCriterio(String idCobrancaCriterio) throws ControladorException;

	/**
	 * Consultar Comando de Ação de Cobrança
	 * [UC0325] - Consultar Comandos de Ação de Cobrança - Tipo Eventual
	 * 
	 * @author Rafael Santos
	 * @date 10/05/2006
	 * @param filtroCobrancaAcaoAtividadeCronograma
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CobrancaAcaoAtividadeComando> pesquisarCobrancaAcaoAtividadeEventual(
					FiltroCobrancaAcaoAtividadeComando filtroCobrancaAcaoAtividadeComando) throws ControladorException;

	/**
	 * [UC0325] Consultar Comandos Ação de Cobrança
	 * Consultar Comando Cobranca Ação Atividade Cobranca - Verificar pelo ID
	 * [SB0004] - Consultar Dados do Comando de Ação de cobrança Eventual
	 * 
	 * @author Rafael Santos
	 * @date 11/05/2006
	 * @return
	 * @throws ControladorException
	 */
	public CobrancaAcaoAtividadeComando obterCobrancaAcaoAtividadeComando(String idCobrancaAcaoAtividadeComando)
					throws ControladorException;

	/**
	 * Este caso de uso permite a emissão de um ou mais documentos de cobrança
	 * [UC0349] Emitir Documento de Cobrança
	 * 
	 * @author Raphael Rossiter
	 * @param indicadorOrdenacao
	 * @data 26/05/2006
	 * @param
	 * @return void
	 */
	/*
	 * public void emitirDocumentoCobranca( Integer
	 * idCronogramaAtividadeAcaoCobranca, Integer
	 * idComandoAtividadeAcaoCobranca, Date dataAtualPesquisa, Integer
	 * idAcaoCobranca) throws ControladorException;
	 */

	public Collection gerarRelacaoDebitos(String idImovelCondominio, String idImovelPrincipal, String idNomeConta,
					String[] idSituacaoLigacaoAgua, String consumoMinimoInicialAgua, String consumoMinimoFinalAgua,
					String[] idSituacaoLigacaoEsgoto, String consumoMinimoInicialEsgoto, String consumoMinimoFinalEsgoto,
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
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, String[] tipoDebito, String valorDebitoInicial,
					String valorDebitoFinal, String qtdContasInicial, String qtdContasFinal, String referenciaFaturaInicial,
					String referenciaFaturaFinal, String vencimentoInicial, String vencimentoFinal, String qtdImoveis, String qtdMaiores,
					String indicadorOrdenacao, String idUnidadeNegocio) throws ControladorException;

	/**
	 * Retorna o count do resultado da pesquisa de Cobrança Cronograma
	 * 
	 * @author Flávio Leonardo
	 * @date 14/06/2006
	 * @return Integer retorno
	 * @throws ControladorException
	 */
	public Integer pesquisarCobrancaCronogramaCount(Filtro filtro) throws ControladorException;

	/**
	 * Consultar Relação de Debitos do Imovel Consulta o Consumo Medio do Imovel
	 * [UC0227] - Gerar Relção de Débitos
	 * 
	 * @author Rafael Santos
	 * @date 15/06/2006
	 * @param imovelId
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarConsumoMedioConsumoHistoricoImovel(Integer imovelId) throws ControladorException;

	/**
	 * Gerar Relatório de Critério de Cobrança
	 * Pesquisa as linhas de critério de cobrança através do id do critério de
	 * cobrança
	 * 
	 * @author Rafael Corrêa
	 * @data 09/08/2006
	 * @param CobrancaDocumento
	 * @return Collection<CobrancaDocumentoItem>
	 */
	public Collection pesquisarCobrancaCriterioLinha(Integer idCriterioCobranca) throws ControladorException;

	/**
	 * Gerar Relatório de Perfil de Parcelamento
	 * Pesquisa os Parcelamentos Desconto Antiguidade através do id de Perfil de
	 * Parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @data 22/08/2006
	 */
	public Collection pesquisarParcelamentoDescontoAntiguidade(Integer idParcelamentoPerfil) throws ControladorException;

	/**
	 * Gerar Relatório de Perfil de Parcelamento
	 * Pesquisa os Parcelamentos Desconto Inatividade através do id de Perfil de
	 * Parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @data 22/08/2006
	 */
	public Collection pesquisarParcelamentoDescontoInatividade(Integer idParcelamentoPerfil) throws ControladorException;

	/**
	 * Gerar Relatório de Perfil de Parcelamento
	 * Pesquisa os Reparcelamentos Consecutivos através do id de Perfil de
	 * Parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @data 22/08/2006
	 */
	public Collection pesquisarReparcelamentoConsecutivo(Integer idParcelamentoPerfil) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Pesquisa os débitos do imóvel a partir das informações do formulário
	 * pesquisarDebitosImovel
	 * 
	 * @author Roberta Costa
	 * @date 23/08/2006
	 * @param codigoImovel
	 * @param codigoImovelAntes
	 * @param dataParcelamento
	 * @param resolucaoDiretoria
	 * @param fimIntervaloParcelamento
	 * @param inicioIntervaloParcelamento
	 * @param indicadorContasRevisao
	 * @param indicadorGuiasPagamento
	 * @param indicadorAcrescimosImpotualidade
	 * @param indicadorDebitosACobrar
	 * @param indicadorCreditoARealizar
	 * @param chavesPrestacoes
	 * @return Object[]
	 * @throws ControladorException
	 */
	public Object[] pesquisarDebitosImovel(String codigoImovel, String codigoImovelAntes, String dataParcelamento,
					String resolucaoDiretoria, String fimIntervaloParcelamento, String inicioIntervaloParcelamento,
					String indicadorContasRevisao, String indicadorGuiasPagamento, String indicadorAcrescimosImpotualidade,
					String indicadorDebitosACobrar, String indicadorCreditoARealizar, Boolean indicadorContas, String chavesPrestacoes)
					throws ControladorException;

	/**
	 * Este caso de uso permite iniciar processos batch de faturamento ou cobrança previdamento
	 * comandados e processos mensais ou eventuais
	 * [UC0001] - Iniciar Processo
	 * Este subfluxo inicia os processo batch de cobrança do sistema
	 * [SB0002] - Iniciar Process de Cobrança Comandado
	 * 
	 * @author Rodrigo Silveira
	 * @date 17/08/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> pesquisarCobrancaAcaoAtividadeCronogramaComandadosNaoRealizados()
					throws ControladorException;

	/**
	 * Este caso de uso permite iniciar processos batch de faturamento ou
	 * cobrança previdamento comandados e processos mensais ou eventuais
	 * [UC0001] - Iniciar Processo
	 * Este subfluxo inicia os processo batch de cobrança do sistema
	 * [SB0002] - Iniciar Process de Cobrança Comandado
	 * 
	 * @author Rodrigo Silveira
	 * @date 17/08/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CobrancaAcaoAtividadeComando> pesquisarCobrancaAcaoAtividadeCronogramaEventuaisComandadosNaoRealizados()
					throws ControladorException;

	/**
	 * Este caso de uso permite gerar e emitir extrato dos débitos de um imóvel
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * 
	 * @author Roberta Costa, Vivianne Sousa
	 * @date 06/09/2006, 11/09/2006
	 * @param imovel
	 * @param indicadorGeracaoTaxaCobranca
	 * @param colecaoContas
	 * @param colecaoGuiasPagamento
	 * @param colecaoDebitosACobrar
	 * @param valorAcrescimosImpontualidade
	 * @param valorDesconto
	 * @return
	 * @throws ControladorException
	 */
	// Quando implementar Notas Promissórias acrescentar nos parâmetros
	public ExtratoDebitoRelatorioHelper gerarEmitirExtratoDebito(Imovel imovel, Short indicadorGeracaoTaxaCobranca,
					Collection colecaoContas, Collection colecaoGuiasPagamento, Collection colecaoDebitosACobrar,
					BigDecimal valorAcrescimosImpontualidade, BigDecimal valorDesconto, BigDecimal valorDocumento,
					Collection<CreditoARealizar> colecaoCreditoARealizar, Cliente cliente,
					NegociacaoOpcoesParcelamentoHelper opcaoParcelamento, Integer idResolucaoDiretoria) throws ControladorException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Calcular valor e Data de vencimento anterior
	 * [SB0001] - Calcular Valor e Data de Vencimento Anterior
	 * 
	 * @author Raphael Rossiter, Vivianne Sousa
	 * @data 30/05/2006, 14/09/2006
	 * @param Collection
	 *            <CobrancaDocumentoItem>
	 * @return CalcularValorDataVencimentoAnteriorHelper
	 */
	public CalcularValorDataVencimentoAnteriorHelper calcularValorDataVencimentoAnterior(
					Collection<CobrancaDocumentoItem> colecaoCobrancaDocumentoItem, int qtdMaxItens) throws ControladorException;

	/**
	 * Este caso de consulta os dados do imovel, esse metodo consulta os
	 * documentos de cobrança do imovel
	 * [UC0472] - Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 18/09/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CobrancaDocumento> consultarImovelDocumentosCobranca(Integer idImovel, Integer numeroPagina)
					throws ControladorException;

	/**
	 * Este caso de consulta os dados do imovel, esse metodo consulta a
	 * quantidade de documentos de cobrança do imovel
	 * [UC0472] - Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 18/09/2006
	 * @return
	 * @throws ControladorException
	 */
	public Integer consultarQuantidadeImovelDocumentosCobranca(Integer idImovel) throws ControladorException;

	/**
	 * Este caso de consulta os dados do imovel, esse metodo consulta a
	 * quantidade de documentos de itens de cobrança do imovel
	 * [UC0476] - Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 18/09/2006
	 * @return
	 * @throws ControladorException
	 */
	public Integer consultarQuantidadeImovelDocumentosItemCobranca(Integer idImovel) throws ControladorException;

	/**
	 * Este caso de uso permite a emissão de um ou mais documentos de cobrança
	 * [UC0477] Emitir Documento de Cobrança - Ordem de Supressão
	 * 
	 * @author Ana Maria
	 * @data 15/09/2006
	 * @param
	 * @return void
	 */
	/*
	 * public void emitirDocumentoCobrancaOrdemSupressao(
	 * Integer idCronogramaAtividadeAcaoCobranca, Integer
	 * idComandoAtividadeAcaoCobranca, Date dataAtualPesquisa, Integer
	 * idAcaoCobranca) throws ControladorException;
	 */

	/**
	 * Pesquisa os dados do parcelamento necessários para o relatório através do
	 * id do parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @date 25/09/2006
	 * @return
	 * @throws ControladorException
	 */
	public ParcelamentoRelatorioHelper pesquisarParcelamentoRelatorio(Integer idParcelamento) throws ControladorException;

	/**
	 * Pesquisa os itens do parcelamento necessários para o relatório através do
	 * id do parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @date 25/09/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarParcelamentoItemPorIdParcelamentoRelatorio(Integer idParcelamento) throws ControladorException;

	/**
	 * [UC0349] Emitir Documento de Cobrança - Ordem de Fiscalização
	 * 
	 * @author Ana Maria
	 * @data 11/10/2006
	 * @param
	 * @return void
	 */
	/*
	 * public void emitirDocumentoCobrancaOrdemFiscalizacao( Integer
	 * idCronogramaAtividadeAcaoCobranca, Integer
	 * idComandoAtividadeAcaoCobranca, Date dataAtualPesquisa, Integer
	 * idAcaoCobranca) throws ControladorException;
	 */

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Pós-oncidção: Resumo das ações de cobrança gerado e atividade encerrar da
	 * ação de cobrança, se for o caso, realizada
	 * 
	 * @author Rafael Santos
	 * @date 16/10/2006
	 */
	public void gerarResumoAcoesCobrancaCronograma(Object[] dadosCobrancaAcaoAtividadeCronograma, int idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0001] - Processar Documento de Cobrança
	 * 
	 * @author Rafael Santos
	 * @date 16/10/2006
	 */
	public void processarDocumentoCobranca(int idCobrancaAtividadeAcaoCronogramaEmitir, Date dataPrevistaAtividadeEncerrar,
					Date dataComandoAtividadeEncerrar, Usuario usuarioLogado, int idCobrancaAtividadeAcaoCronogramaEncerrar,
					int anoMesReferenciaCobrancaGrupoCronogramaMes, int idCobrancaAcaoCronograma, int idCobrancaGrupo, int idCobrancaAcao,
					Date dataRealizacaoAtividadeEmitir, Date dataRealizacaoAtividadeEncerrar, Integer idServicoTipoAcaoCobranca)
					throws ControladorException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0004] - Atualizar Item do Documento de Cobrança
	 * Acumula a quantidade e o valor do item, na situiaão de débito
	 * correspondente Armazena a data da situação do débito do imte do documento
	 * de cobrança refrente a situação do débito do item do
	 * documento de cobrança
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 */
	public void atualizarItemDocumentoCobranca(int

	idSituacaoDebito, BigDecimal valorItemCobrado, Date dataSituacaoDebito, Collection<GerarResumoAcoesCobrancaCronogramaHelper>

	colecaoGerarResumoAcoesCobrancaCronogramaHelper);

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0001] - Processar Documentos de Cobrança
	 * Pesquisa o Imovel para ser usado para acumular valores no
	 * RESUMO_COBRANCA_ACAO
	 * 
	 * @author Rafael Santos
	 * @date 23/10/2006
	 */
	public Imovel pesquisarDadosImovel(int idImovel) throws ControladorException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0001] - Processar Documentos de Cobrança
	 * Acumular ou insere na coelção de RESUMO_COBRANCA_ACAO
	 * 
	 * @author Rafael Santos
	 * @date 24/10/2006
	 */
	// public Collection<ResumoCobrancaAcao> acumularResumoCobrancaAcao(
	// Collection<ResumoCobrancaAcao>
	//
	// colecaoResumoCobrancaAcao,
	// int anoMesReferenciaCobrancaGrupoCronogramaMes,
	// int idCobrancaAcaoCronograma, Date dataRealizacaoAtividadeEmitir,
	// Date dataPrevistaAtividadeEncerrar, int idCobrancaGrupo,
	// Imovel imovel, Categoria categoria, int idCobrancaAcao,
	// Integer idSituacaoAcao, Integer idSituacaoPredominanteDebito,
	// Integer idFiscalizacao, int indicadorCronogramaComando,
	// Date dataSituacaoAcao, Date dataSituacaoDebito,
	// BigDecimal valorDocumento, BigDecimal valorLimitePrioridade, Integer
	// idCobrancaCriterio,Date dataRealizacaoAtividadeEncerrar);
	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Cria um Objto ResumoCobrancaAcao com os valores informados
	 * 
	 * @author Rafael Santos
	 * @date 24/10/2006
	 */
	// public ResumoCobrancaAcao criarResumoCobrancaAcao(
	// int anoMesReferenciaCobrancaGrupoCronogramaMes,
	// int idCobrancaAcaoCronograma, Date dataRealizacaoAtividadeEmitir,
	// Date dataPrevistaAtividadeEncerrar, int idCobrancaGrupo,
	// Imovel imovel, Categoria categoria, int idCobrancaAcao,
	// Integer idSituacaoAcao, Integer idSituacaoPredominanteDebito,
	// Integer idFiscalizacao, int indicadorCronogramaComando,
	// Integer indicadorAntesApos, Integer indicadorAcimaLimite,
	// BigDecimal valorDocumento,Integer idCobrancaCriterio);
	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0006] Processar Ação com Ordens de Serviço
	 * 
	 * @author Rafael Santos
	 * @throws ControladorException
	 * @date 25/10/2006
	 */
	public void processarAcaoOrdemServico(int idServicoTipo, Date dataPrevistaAtividadeEncerrar, Date dataPrevistaAtividadeEmitir,
					Date dataComandoAtividadeEncerrar, Date dataRealizacaoAtividadeEmitir, Usuario usuarioLogado,
					int anoMesReferenciaCobrancaGrupoCronogramaMes, int idCobrancaAcaoCronograma, int idCobrancaGrupo, int idCobrancaAcao,
					Date dataRealizacaoAtividadeEncerrar) throws ControladorException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0006] - Processar Ação com Ordens de Serviço
	 * Acumular ou insere na coelção de RESUMO_COBRANCA_ACAO
	 * 
	 * @author Rafael Santos
	 * @date 25/10/2006
	 */
	public void acumularResumoCobrancaAcaoOrdemServico(Collection<ResumoCobrancaAcao> colecaoResumoCobrancaAcao,
					int anoMesReferenciaCobrancaGrupoCronogramaMes, int idCobrancaAcaoCronograma, Date dataRealizacaoAtividadeEmitir,
					Date dataPrevistaAtividadeEncerrar, int idCobrancaGrupo, Imovel imovel, Categoria categoria, int idCobrancaAcao,
					Integer idSituacaoAcao, Integer idSituacaoPredominanteDebito, int idFiscalizacao, int indicadorCronogramaComando,
					BigDecimal valorDocumento, Integer indicadorAntesApos, Integer indicadorAcimaLimite,
					Date dataRealizacaoAtividadeEncerrar);

	/**
	 * retorna o objeto ResolucaoDiretoria com a maior data Vigência inicial
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 08/11/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaMaiorDataVigenciaInicio(Collection<Integer> idsGrupoUsuario)
					throws ControladorException;

	/**
	 * Pesquisa o imóvel para parcelamento com controle de abrangência.
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 27/11/2006
	 * @param filtroImovel
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Imovel> pesquisarImovelEfetuarParcelamento(FiltroImovel filtroImovel, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 28/11/2006
	 * @return
	 * @throws ControladorException
	 */
	public Boolean verificarQtdeReparcelamentoPerfil(Integer idPerfilParc, Short numeroReparcelamentoConsecutivos)
					throws ControladorException;

	public CobrancaAcaoAtividadeCronograma pesquisarCobrancaAcaoAtividadeCronogramaId(Integer idCobrancaAcaoAtividadeCronograma)
					throws ControladorException;

	/**
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Item 1 O sistema seleciona os cronogramas de cobrança dos grupos de
	 * cobrança e meses de referência.
	 * 
	 * @author Pedro Alexandre
	 * @date 19/01/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarCobrancaGrupoCronogramaMes() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento Debito [SB0011] Verificar Única Fatura
	 * 
	 * @author Vivianne Sousa
	 * @created 15/02/2007
	 * @exception controladorException
	 *                controlador Exception
	 */
	public void verificarUnicaFatura(Collection colecaoContas, ParcelamentoPerfil parcelamentoPerfil) throws ControladorException;

	/**
	 * Metodo criado para criar os debitos para os parcelamentos q tenham juros
	 * e nao tenha criado o debito dos juros DBTP_ID = 44
	 * 
	 * @author Flávio Cordeiro
	 * @date 23/02/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void gerarDebitoCobrarNaoCriados();

	/**
	 * Verificar se os itens do parcelamento(Conta, Debito a cobrar e Credit a
	 * realizar) já estão no historico
	 * [UC0252] Desfazer Parcelamentos de Debito
	 * 
	 * @author Vivianne Sousa
	 * @date 09/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public boolean verificarItensParcelamentoNoHistorico(Integer idImovel, Integer idParcelamento) throws ControladorException;

	// /**
	// *
	// * Este caso de uso gera os avisos de cobrança dos documentos de cobrança
	// *
	// * [UC0575] Emitir Aviso de Cobrança
	// *
	// *
	// * @author Sávio Luiz
	// * @data 02/04/2007
	// *
	// * @param
	// * @return void
	// */
	// public void emitirAvisoCobranca(Integer
	// idCronogramaAtividadeAcaoCobranca,
	// Integer idComandoAtividadeAcaoCobranca, Date dataAtualPesquisa,
	// Integer idAcaoCobranca, CobrancaGrupo grupoCobranca)
	// throws ControladorException;

	/**
	 * Consulta o id e a situação da ordem de serviço associada ao documento de
	 * cobrança passado como parâmetro
	 * 
	 * @author Sávio Luiz
	 * @created 13/04/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Object[] pesquisarDadosOrdemServicoDocumentoCobranca(Integer idDocumentoCobranca) throws ControladorException;

	/**
	 * [UC0394] - Gerar Débitos a Cobrar de Doações
	 * 
	 * @author Sávio Luiz
	 * @date 09/05/2007
	 * @param colecaoRotas
	 *            ,
	 *            Integer anoMesReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarParcelamentoItensDebitoACobrar(Collection colecaoIdsDebitoACobrar) throws ControladorException;

	/**
	 * [UC0394] - Gerar Débitos a Cobrar de Doações
	 * 
	 * @author Sávio Luiz
	 * @date 09/05/2007
	 * @param colecaoRotas
	 *            ,
	 *            Integer anoMesReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void removerDocumentosItensDebitoACobrar(Collection colecaoIdsDebitoACobrar) throws ControladorException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Pós-oncidção: Resumo das ações de cobrança gerado e atividade encerrar da
	 * ação de cobrança, se for o caso, realizada
	 * 
	 * @author Sávio Luiz
	 * @date 11/05/2007
	 */
	public void inserirResumoAcoesCobrancaCronograma(Object[] dadosCobrancaAcaoAtividadeCronograma, int idFuncionalidadeIniciada)
					throws ControladorException;

	// /**
	// *
	// * Este caso de uso gera os avisos de cobrança dos documentos de cobrança
	// *
	// * [UC0575] Emitir Parcelamento em Atraso
	// *
	// *
	// * @author Sávio Luiz
	// * @data 12/04/2007
	// *
	// * @param
	// * @return void
	// */
	// public void emitirParcelamentoEmAtraso(
	// Integer idCronogramaAtividadeAcaoCobranca,
	// Integer idComandoAtividadeAcaoCobranca, Date dataAtualPesquisa,
	// Integer idAcaoCobranca, CobrancaGrupo grupoCobranca)
	// throws ControladorException;

	/**
	 * Esta funcionalidade permite informar dados para geração de relatórios ou
	 * consultas
	 * [UC0304] - Informar Dados para Geração de Relatório ou Consulta
	 * 
	 * @author Sávio Luiz
	 * @date 22/05/2007
	 */
	public InformarDadosGeracaoResumoAcaoConsultaHelper informarDadosGeracaoResumoAcaoConsulta(String mesAnoFaturamento,
					String[] idsCobrancaGrupo, String[] idsGerenciaRegional, Integer idEloPolo, Integer idLocalidade,
					Integer idSetorComercial, Integer nmQuadra, String[] idsImovelPerfil, String[] idsLigacaoAguaSituacao,
					String[] idsLigacaoEsgotoSituacao, String[] idsCategoria, String[] idsEsferaPoder, String[] idsEmpresas,
					Integer idUnidadeNegocio) throws ControladorException;

	/**
	 * Pesquisar relação de protocolos de documentos de cobrança do cronograma
	 * 
	 * @author Ana Maria
	 * @date 15/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarProtocoloDocumentoCobrancaCronograma(Integer idCobrancaAcaoAtividadeCronograma) throws ControladorException;

	/**
	 * Pesquisar relação de protocolos de documentos de cobrança do eventual
	 * 
	 * @author Ana Maria
	 * @date 21/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarProtocoloDocumentoCobrancaEventual(Integer idCobrancaAcaoAtividadeComand) throws ControladorException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Retorna os CBCR_ID da tabela COBRANCA_ACAO_CRONOGRAMA com CBCM_ID da
	 * tabela COBRANCA_GRUPO_CRONOGRAMA_MES
	 * 
	 * @author Rafael Santos,Sávio Luiz
	 * @date 16/10/2006,04/06/2007
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaAcaoCronograma(int idCobrancaGrupoCronogramaMes) throws ControladorException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Retorna CAAC_TMREALIZACAO do COBRANCA_ATIVIDADE_ACAO_CRONOGRAMA
	 * 
	 * @author Rafael Santos,Sávio Luiz
	 * @date 16/10/2006,04/06/2007
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDataRelizacaoCobrancaAtividadeAcaoConograma(int idCobrancaAcaoCronograma, int idCobrancaAtividade)
					throws ControladorException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * [FS0004] Validar Registro Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @created 05/06/2007
	 * @param idSolicitacaoTipoEspecificacao
	 * @exception ControladorException
	 */
	public Object[] validarRegistroAtendimentoTransferenciaDebitoCredito(Integer idRA, boolean levantarExcecao) throws ControladorException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * 
	 * @author Raphael Rossiter
	 * @created 08/06/2007
	 * @param idRA
	 *            ,
	 *            idImovelDestino
	 * @exception ControladorException
	 */
	public Integer validarTransferenciaDebitoCreditoDadosImoveis(Integer idRA, Integer idImovelDestino) throws ControladorException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * [SB0001] - Apresentar Débitos/Créditos do Imóvel de Origem
	 * [FS0002] - Verificar existência de débitos/créditos no imóvel de origem
	 * 
	 * @author Raphael Rossiter
	 * @created 08/06/2007
	 * @param idImovelOrigem
	 * @exception ControladorException
	 */
	public ObterDebitoImovelOuClienteHelper apresentarDebitoCreditoImovelOrigem(Integer idImovelOrigem) throws ControladorException;

	/**
	 * [UC0609] Transferência de Débitos/Créditos
	 * [SB00002] Transferência dos Débitos/Créditos selecionados para o imóvel
	 * destino
	 * 
	 * @author Vivianne Sousa
	 * @date 09/06/2007
	 * @param idImovelDestino
	 * @param colecaoConta
	 * @param colecaoDebitosACobrar
	 * @param colecaoCreditosARealizar
	 * @param colecaoGuiasPagamento
	 * @throws ControladorException
	 */
	public void transferirDebitosCreditos(Integer idImovelDestino, Collection colecaoContas, Collection colecaoDebitosACobrar,
					Collection colecaoCreditosARealizar, Collection colecaoGuiasPagamento, Usuario usuarioLogado,
					Integer idRegistroAtendimento, String identificadoresConta) throws ControladorException;

	/**
	 * [UC0594] Gerar Relação de Parcelamento
	 * 
	 * @author Ana Maria
	 * @date 30/05/2007
	 */
	public Collection<RelacaoParcelamentoRelatorioHelper> filtrarRelacaoParcelamento(
					FiltrarRelacaoParcelamentoHelper filtrarRelacaoParcelamento) throws ControladorException;

	public Collection gerarColecaoDocumentoCobrancaOrdemServico(Integer idServicoTipoAcaoCobranca, Date dataRealizacaoAtividadeEncerrar,
					Usuario usuarioLogado, int idCobrancaAtividadeAcaoCronogramaEmitir) throws ControladorException;

	/**
	 * [UC0XXXX] Gerar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @author Sávio Luiz
	 * @created 15/06/2006
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection<Object[]> pesquisarCobrancaAcaoAtividadeComandoSemRealizacao(String idsAcoesSemPrazoValidade)
					throws ControladorException;

	/**
	 * Esta funcionalidade permite informar dados para geração de relatórios ou
	 * consultas
	 * [UC0616] - Informar Dados para Consulta do Resumo das Ações de Cobrança
	 * Eventual
	 * 
	 * @author Sávio Luiz
	 * @param idsCobrancaAcao
	 * @date 25/06/2007
	 */
	public InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventual(String dataEmissaoInicial,
					String dataEmissaoFinal, String idCobrancaAcaoAtividadeComando, String tituloCobrancaAcaoAtividadeComando,
					String[] idsCobrancaGrupo, String[] idsGerenciaRegional, Integer idEloPolo, Integer idLocalidade,
					Integer idSetorComercial, Integer nmQuadra, String[] idsImovelPerfil, String[] idsLigacaoAguaSituacao,
					String[] idsLigacaoEsgotoSituacao, String[] idsCategoria, String[] idsEsferaPoder, String[] idsEmpresas,
					Integer idUnidadeNegocio, String[] idsCobrancaAcao) throws ControladorException;

	/**
	 * Gerar Curva ABC de Debitos
	 * [UC0621] Gerar Curva ABC de Debitos
	 * 
	 * @author Ivan Sérgio
	 * @date 07/08/2007
	 */
	public Collection gerarCurvaAbcDebitos(String classificacao, String referenciaCobrancaInicial, String referenciaCobrancaFinal,
					String indicadorImovelMedicaoIndividualizada, String indicadorImovelParalizacaoFaturamentoCobranca,
					String idGerenciaRegional, String idUnidadeNegocio, String idLocalidadeInicial, String idLocalidadeFinal,
					String idSetorComercialInicial, String idSetorComercialFinal, String[] situacaoLigacaoAgua,
					String[] situacaoLigacaoEsgoto, String intervaloMesesCortadoSuprimidoInicial,
					String intervaloMesesCortadoSuprimidoFinal, String intervaloConsumoMinimoFixadoEsgotoInicial,
					String intervaloConsumoMinimoFixadoEsgotoFinal, String indicadorMedicao, String idTipoMedicao, String idPerfilImovel,
					String idTipoCategoria, String[] categoria, String idSubCategoria, String valorMinimoDebito,
					String intervaloQuantidadeDocumentosInicial, String intervaloQuantidadeDocumentosFinal,
					String indicadorPagamentosNaoClassificados) throws ControladorException;

	/**
	 * [UC0630] - Solicitar Emissão do Extrato de Débitos
	 * Apresentar Débitos/Créditos do Imóvel
	 * 
	 * @author Vivianne Sousa
	 * @created 21/08/2007
	 * @param idImovel
	 * @exception ControladorException
	 */
	public ObterDebitoImovelOuClienteHelper apresentarDebitoCreditoImovelExtratoDebito(Integer idImovel, boolean indicadorParcelamento,
					Short multa, Short jurosMora, Short atualizacaoTarifaria)
					throws ControladorException;

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débitos
	 * [UC0251] Gerar Atividade de Ação de Cobranca
	 * Recuparea o valor da taxa de cobrança para gerar o documento de cobrança
	 * gerarDocumentoCobranca
	 * 
	 * @author Vivianne Sousa
	 * @date 31/08/2007
	 * @param imovel
	 * @param sistemaParametro
	 * @throws ControladorException
	 */
	public BigDecimal obterValorTaxaDocumentoCobranca(Imovel imovel, SistemaParametro sistemaParametro) throws ControladorException;

	/**
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 01/09/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarRDUtilizadaPeloImovel(Integer idRD, Integer idImovel) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento Debito [SB0011] Verificar Única Fatura
	 * 
	 * @author Vivianne Sousa
	 * @created 01/09/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorDebitoACobrarSancoes(Integer idImovel, Integer anoMesLimiteMinimo, Integer anoMesLimiteMaximo)
					throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 14/09/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorDebitoCobradoContas(Integer idImovel, Integer anoMesInicialReferenciaDebito,
					Integer anoMesFinalReferenciaDebito) throws ControladorException;

	/**
	 * [UC0214] Inserir Ação de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @created 14/09/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer inserirAcaoCobranca(CobrancaAcaoHelper cobrancaAcaoHelper) throws ControladorException;

	/**
	 * [UC0701] Informar Índices dos Acréscimos de Impontualidade
	 * 
	 * @author Sávio Luiz
	 * @created 26/09/2007
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarMaximoAnoMesIndicesAcerscimosImpontualidade() throws ControladorException;

	/**
	 * [UC0644] Filtrar Ação de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @created 10/10/2007
	 * @author eduardo henrique
	 * @date 26/08/2008 Alterações no [UC0644] para a v0.04
	 * @author Virgínia Melo
	 * @date 31/10/2008 Desfazer alterações no [UC0644] para a v0.06
	 * @param idCobrancaEstagio
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public FiltroCobrancaAcao filtrarAcaoCobranca(String descricaoAcaoCobranca, String numeroDiasValidade, String qtdDiasRealizacao,
					String idAcaoPredecessora, String numeroDiasEntreAcoes, String idTipoDocumentoGerado, String idSituacaoLigacaoAgua,
					String idSituacaoLigacaoEsgoto, String idCobrancaCriterio, String descricaoCobrancaCriterio, String idServicoTipo,
					String descricaoServicoTipo, String ordemCronograma, String icCompoeCronograma, String icAcaoObrigatoria,
					String icRepetidaCiclo, String icSuspensaoAbastecimento, String icDebitosACobrar, String icAcrescimosImpontualidade,
					String icGeraTaxa, String icEmitirBoletimCadastro, String icImoveisSemDebitos, String icUso,
					String idPrimeiraResolucaoDiretoria, String idSegundaResolucaoDiretoria, String idTerceiraResolucaoDiretoria,
					String indicadorConsideraCreditoRealizar, String indicadorConsideraGuiaPagamento) throws ControladorException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas associdas a um Critério de Cobrança
	public Collection pesquisarRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao) throws ControladorException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas associdas a um Critério de Cobrança
	public Collection pesquisarRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ControladorException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas associdas a um Critério de Cobrança
	public Collection pesquisarRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String idLocalidade, String idCobrancaAcao) throws ControladorException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas associdas a um Critério de Cobrança
	public Collection pesquisarRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao) throws ControladorException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas associdas a um Critério de Cobrança
	public Collection pesquisarRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao) throws ControladorException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas associdas a um Critério de Cobrança
	public Collection pesquisarRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ControladorException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas que NÃO estão associdas a um Critério de Cobrança
	public Collection pesquisarRotasSemCriterioIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ControladorException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas que NÃO estão associdas a um Critério de Cobrança
	public Collection pesquisarRotasSemCriterioIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao)
					throws ControladorException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas que NÃO estão associdas a um Critério de Cobrança
	public Collection pesquisarRotasSemCriterioIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String idLocalidade, String idCobrancaAcao) throws ControladorException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas que NÃO estão associdas a um Critério de Cobrança
	public Collection pesquisarRotasSemCriterio(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao) throws ControladorException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas que NÃO estão associdas a um Critério de Cobrança
	public Collection pesquisarRotasSemCriterioIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao)
					throws ControladorException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	// Retorna uma Coleção de IDs das rotas que NÃO estão associdas a um Critério de Cobrança
	public Collection pesquisarRotasIntervaloLocalidadeSemCriterio(String idLocalidadeInicial, String idLocalidadeFinal,
					String idCobrancaAcao) throws ControladorException;

	/**
	 * [UC0645] Manter Ação de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @created 06/11/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */

	public void atualizarAcaoCobranca(CobrancaAcao cobrancaAcao, CobrancaAcaoHelper cobrancaAcaoHelper) throws ControladorException;

	/**
	 * [UC0067] Inserir Comando Negaivação
	 * 
	 * @autor: Ana Maria
	 *         [FS0019] Verificar existência de Parcelamento
	 * @param idImovel
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarClienteResponsavelParcelamento(Integer idImovel) throws ControladorException;

	/**
	 * Inserir um comando de atividade de cobrança de Cronograma
	 * [UC0243] - Inserir Comando Ação de Cobrança
	 * Inserir cobranca acao atividade comando
	 * 
	 * @author ebandeira henrique
	 * @date 29/08/2008
	 * @since v0.04
	 *        Alteração p/ v0.06
	 * @author Virgínia Melo
	 * @date 06/11/2008
	 */
	public void inserirComandoAcaoCobrancaCronograma(CobrancaAtividade cobrancaAtividade, CobrancaAcao cobrancaAcao,
					CobrancaGrupo cobrancaGrupo, Empresa empresa, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Método responsável por inserir um CobrancaContrato
	 * 
	 * @author Virgínia Melo
	 * @date 24/11/2008
	 * @param cobrancaContrato
	 * @param colecaoCobrancaContratoRemuneracao
	 * @param colecaoCobrancaContratoRemuneracaoVencimento
	 * @param usuarioLogado
	 * @throws ControladorException
	 *             Alteracao para contemplar remuneraçao por Produtividade
	 * @author Andre Nishimura, William Mathias.
	 * @date 15/04/2010
	 */
	// entidade CobrancaContratoRemuneracao dropada
	public void inserirCobrancaContrato(CobrancaContrato cobrancaContrato,
					Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoCobrancaContratoRemuneracaoPorSucesso,
					Collection<CobrancaContratoRemuneracaoPorProdutividade> colecaoCobrancaContratoRemuneracaoPorProdutividade,
					Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * Método responsável por atualizar um CobrancaContrato
	 * 
	 * @author Virgínia Melo
	 * @date 01/12/2008
	 * @param cobrancaContrato
	 * @param colecaoCobrancaContratoRemuneracao
	 * @param colecaoCobrancaContratoRemuneracaoVencimento
	 * @param usuarioLogado
	 * @throws ControladorException
	 *             Alteracao para contemplar remuneraçao por Produtividade
	 * @author Andre Nishimura, William Mathias.
	 * @date 15/04/2010
	 */
	// entidade CobrancaContratoRemuneracao dropada
	public void atualizarCobrancaContrato(CobrancaContrato cobrancaContrato,
					Collection<CobrancaContratoRemuneracaoPorSucesso> colecaoCobrancaContratoRemuneracaoPorSucesso,
					Collection<CobrancaContratoRemuneracaoPorProdutividade> colecaoCobrancaContratoRemuneracaoPorProdutividade,
					Usuario usuarioLogado)
	 throws ControladorException;

	/**
	 * Método responsável por gerar dados relativos documentos gerados
	 * CHAMADO POR UC 0251 - UC 0355 - UC 0357 - UC 0359 - UC 0360
	 * 
	 * @author Leonardo Maranhão
	 * @date 16/12/2008
	 * @param ordemServico
	 * @param atendimentoMotivoEncerramentoNULO
	 * @param identificadorPrazoNULO
	 * @param idCobrancaDebitoSituacao
	 * @throws ControladorException
	 */
	public void gerarAcumuladoDadosRelativosDocumentosGerados(OrdemServico ordemServico, boolean atendimentoMotivoEncerramentoNULO,
					boolean identificadorPrazoNULO, Integer idCobrancaDebitoSituacao) throws ControladorException;

	/**
	 * Pesquisa o Documento de Cobrança pelo ID
	 * 
	 * @author Saulo Lima
	 * @date 20/05/2009
	 * @param idCobrancaDocumento
	 * @return CobrancaDocumento
	 * @throws ControladorException
	 */
	public CobrancaDocumento pesquisarCobrancaDocumento(Integer idCobrancaDocumento) throws ControladorException;

	/**
	 * Método responsável por retornar a soma dos valores de um determinado 'tipoConsulta' da
	 * 'colecaoGuiaPagamentoValores'
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 08/07/2009
	 * @param colecaoGuiaPagamentoValores
	 * @param tipoConsulta
	 * @return BigDecimal
	 * @throws ControladorException
	 */
	public BigDecimal calcularValoresGuia(Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores, int tipoConsulta)
					throws ControladorException;

	/**
	 * Método responsável por retornar apenas as 'GuiaPagamentoValoresHelper' selecionas pelo
	 * usuário na tela utilizando o parâmetro
	 * 'chavesPrestacoes' (Ex.: 9988-1$9988-2$7766-1)
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 08/07/2009
	 * @param chavesPrestacoes
	 * @param colecaoGuiaPagamentoValores
	 * @return Collection<GuiaPagamentoValoresHelper>
	 * @throws ControladorException
	 */
	public Collection<GuiaPagamentoValoresHelper> retornarGuiaPagamentoValoresSelecionadas(String chavesPrestacoes,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores) throws ControladorException;

	/**
	 * Pesquisa a quantidade de Rotas que nao possui um Criterio definido para cada uma das Acoes de
	 * Cobrancas passadas no filtro
	 * 
	 * @author Victor Cisneiros
	 * @date 10/12/2008
	 */
	public Integer pesquisarQtdeRotasSemCriteriosParaAcoesCobranca(PesquisarQtdeRotasSemCriteriosParaAcoesCobranca filtro)
					throws ControladorException;

	/**
	 * [UC0676] - Consultar Resumo da Negativacao
	 * 
	 * @author Marcio Roberto
	 * @date 28/02/2008
	 * @return NegativacaoHelper
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoNegativacao(DadosConsultaNegativacaoHelper dadosConsultaNegativacaoHelper, int tipo)
					throws ControladorException;

	/**
	 * Solicitar Emissão do Extrato de Débitos
	 * Author: Vivianne Sousa
	 * Data: 14/09/2009
	 * Obtem os parcelamentos de débitos efetuados que estejam com situação normal
	 * 
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarParcelamentosSituacaoNormal(Integer idImovel) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito - remover debito a cobrar referente
	 * ao parcelamento historico
	 * 
	 * @author lucas medeiros
	 * @date 21/08/2009
	 *       trouxe do repositorio para o controlador, e mudanças.
	 * @param DebitoACobrarHistorico
	 * @exception controladorException
	 *                controlador Exception
	 */
	public void transferirDebitoACobrarHistoricoParaDebitoACobrar(DebitoACobrarHistorico debitoACobrarHistorico,
					boolean indicadorParcelamento) throws ControladorException;

	void validaDocumentoCobrancaPagamentoParaContraAcao(Integer id) throws ControladorException;

	/**
	 * FIXME: REVER
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 * @throws ControladorException
	 */
	public Collection<EmissaoOSCobrancaHelper> pesquisarOS(Integer idComando, CobrancaAcaoAtividade tipoComandoAcaoCobrancas)
					throws ControladorException;

	public void emitirOSCobranca(Map<Integer, List<EmissaoOSCobrancaHelper>> associacoes, List<Empresa> empresas,
					CobrancaAcaoAtividade tipoComandoAcaoCobranca, Integer idComando, Usuario usuarioLogado) throws ControladorException;

	public void atualizarLigacaoAgua(CriticaOSLoteHelper criticaHelper) throws ControladorException;

	/**
	 * @param periodoInicio
	 * @param periodoFim
	 * @param acao
	 * @param empresa
	 * @param stringComandoTipo
	 * @param acaoAtividadeComando
	 * @param acaoAtividadeCronograma
	 * @return
	 * @author Andre Nishimura
	 * @throws ControladorException
	 * @date 30 de Junho de 2010
	 */
	public Collection<RelatorioFechamentoCobrancaHelper> filtrarRelatorioFechamentoCobranca(Date periodoInicio, Date periodoFim,
					CobrancaAcao acao, Empresa empresa, CobrancaAcaoAtividadeComando acaoAtividadeComando,
					CobrancaAcaoAtividadeCronograma acaoAtividadeCronograma, String stringComandoTipo) throws ControladorException;

	public Collection filtrarRelatorioEficienciaCobranca(Date dataInicial, Date dataFinal, CobrancaAcao acao, Empresa empresa,
					CobrancaAcaoAtividadeComando comando, CobrancaAcaoAtividadeCronograma cronograma, String[] setores, String[] grupos)
					throws ControladorException;

	public Collection pesquisarBairroPorGrupoEmQuadraRota(Integer grupo) throws ControladorException;

	public Collection filtrarAcompanhamentoExecucaoServicoCobranca(Integer cobrancaAcao, Integer comandoCronograma,
					Integer comandoEventual, Integer cobrancaAcao2, Date periodoInicial, Date periodoFinal, Integer situacao,
					Integer religado, Integer servico, Integer localidade, Integer grupo, String[] bairro, String[] setorComercial)
					throws ControladorException;

	public Integer consultarQtdeDocumentosItensPorCobrancaDocumento(CobrancaDocumento cobrancaDocumento) throws ControladorException;

	/**
	 * @author Eduardo Castor
	 * @throws ErroRepositorioException
	 * @throws NumberFormatException
	 * @date 21/07/2010
	 *       Criticar uma linha do arquivo de OSs para baixar em lote
	 */
	public CriticaOSLoteHelper criticaOSLote(String linhaOS) throws ControladorException, NumberFormatException, ErroRepositorioException;

	/**
	 * Efetua a baixa das ordens de serviço de cobrança
	 * 
	 * @author wpereira
	 * @date 22/07/2010
	 * @param arquivo
	 * @param usuario
	 * @throws ControladorException
	 */
	public void efetuarBaixaOrdensServicoCobranca(String arquivo, Usuario usuario) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @date 23/07/2010
	 * @param comando
	 * @param idCobrancaAcaoAtividadeComando
	 * @param idCobrancaAcaoAtividadeCronograma
	 * @param acao
	 * @param dataInicial
	 * @param dataFinal
	 * @param grupo
	 * @param setorComercial
	 * @param bairro
	 * @param categoria
	 * @return
	 * @throws ControladorException
	 */
	public Collection<RelatorioImovelPorAcaoCobrancaHelper> filtrarRelatorioImovelPorAcaoCobranca(String comando,
					String idCobrancaAcaoAtividadeComando, String idCobrancaAcaoAtividadeCronograma, String[] acao, Date dataInicial,
					Date dataFinal, String grupo, String[] setorComercial, String[] bairro, String[] categoria, String localidade)
					throws ControladorException;

	/**
	 * @author wpereira
	 * @date 29/07/2010
	 * @param comandoCronograma
	 * @param comandoEventual
	 * @param dataInicio
	 * @param dataFim
	 * @param idsAcaoCobranca
	 * @param idGrupoCobranca
	 * @param idsSetorComercial
	 * @param idsBairro
	 * @param idsCategoria
	 * @param idsServicoTipo
	 * @param idComandoCronograma
	 * @param idComandoEventual
	 * @return
	 * @throws ControladorException
	 */
	public Collection<RelatorioReligacoesPorImovelHelper> obterRegistrosRelatorioReligacoesPorImovel(boolean comandoCronograma,
					boolean comandoEventual, Date dataInicio, Date dataFim, Collection<Integer> idsAcaoCobranca, Integer idGrupoCobranca,
					Collection<Integer> idsSetorComercial, Collection<Integer> idsBairro, Collection<Integer> idsCategoria,
					Collection<Integer> idsServicoTipo, Integer idComandoCronograma, Integer idComandoEventual) throws ControladorException;

	/**
	 * @author wpereira
	 * @date 29/07/2010
	 * @param comandoCronograma
	 * @param comandoEventual
	 * @param dataInicio
	 * @param dataFim
	 * @param idsAcaoCobranca
	 * @param idGrupoCobranca
	 * @param idsSetorComercial
	 * @param idsBairro
	 * @param idsCategoria
	 * @param idsServicoTipo
	 * @param idComandoCronograma
	 * @param idComandoEventual
	 * @return
	 * @throws ControladorException
	 */
	public Integer consultarQuantidadeRegistrosRelatorioReligacoesPorImovel(boolean comandoCronograma, boolean comandoEventual,
					Date dataInicio, Date dataFim, Collection<Integer> idsAcaoCobranca, Integer idGrupoCobranca,
					Collection<Integer> idsSetorComercial, Collection<Integer> idsBairro, Collection<Integer> idsCategoria,
					Collection<Integer> idsServicoTipo, Integer idComandoCronograma, Integer idComandoEventual) throws ControladorException;

	public Integer filtrarRelatorioImovelPorAcaoCobrancaCount(String comando, String idComando, String idCronograma, String[] acao,
					Date dataInicial, Date dataFinal, String grupo, String[] setorComercial, String[] bairro, String[] categoria,
					String localidade)
					throws ControladorException;

	/**
	 * @author isilva
	 * @param tipoComando
	 * @param idCobrancaAcaoComando
	 * @param idCobrancaAcaoCronograma
	 * @param padraoPeriodo
	 * @param periodoInicio
	 * @param periodoFim
	 * @param periodoMesInicio
	 * @param periodoMesFim
	 * @param periodoAnoInicio
	 * @param periodoAnoFim
	 * @param localidade
	 * @param acaoSelecionada
	 * @param empresa
	 * @param grupos
	 * @param setores
	 * @param bairros
	 * @param grupoServicos
	 * @param subGrupoServicos
	 * @param servicos
	 * @param tiposCorte
	 * @param tiposSupressao
	 * @return
	 * @throws ControladorException
	 */
	public Object[] obterQuantidadeOrdensPeriodo(String tipoComando, String idCobrancaAcaoComando, String idCobrancaAcaoCronograma,
					String padraoPeriodo, String periodoInicio, String periodoFim, String periodoMesInicio, String periodoMesFim,
					String periodoAnoInicio, String periodoAnoFim, String localidade, Integer acaoSelecionada, Integer empresa,
					Integer[] grupos, Integer[] setores, Integer[] bairros, Integer[] grupoServicos, Integer[] subGrupoServicos,
					Integer[] servicos, Integer[] tiposCorte, Integer[] tiposSupressao) throws ControladorException;

	/**
	 * @author isilva
	 * @return
	 * @throws ControladorException
	 */
	public Object[] pesquisarProdutividadeMensalExecucaoServico(String tipoComando, String idCobrancaAcaoComando,
					String idCobrancaAcaoCronograma, String padraoPeriodo, String periodoInicio, String periodoFim,
					String periodoMesInicio, String periodoMesFim, String periodoAnoInicio, String periodoAnoFim, String localidade,
					Integer acaoSelecionada, Integer[] empresas, Integer[] grupos, Integer[] setores, Integer[] bairros,
					Integer[] grupoServicos, Integer[] subGrupoServicos, Integer[] servicos, Integer[] tiposCorte, Integer[] tiposSupressao)
					throws ControladorException;

	// public ObterDebitoImovelOuClienteHelper obterDebitoImovelParaMensagem(int indicadorDebito,
	// String idImovel, String codigoCliente,
	// Integer clienteRelacaoTipo, String anoMesInicialReferenciaDebito, String
	// anoMesFinalReferenciaDebito, Date anoMesInicialVencimentoDebito,
	// Date anoMesFinalVencimentoDebito, int indicadorPagamento, int indicadorConta, int
	// indicadorDebitoACobrar, int indicadorCreditoARealizar,
	// int indicadorNotasPromissorias, int indicadorGuiasPagamento, int
	// indicadorCalcularAcrescimoImpontualidade, Boolean indicadorContas,Integer anoRefereciaDebito)
	// throws ControladorException;

	ObterDebitoImovelOuClienteHelper obterDebitoImovelParaMensagem(int indicadorDebito, String idImovel,
					String anoMesInicialReferenciaDebito, String anoMesFinalReferenciaDebito, Date anoMesInicialVencimentoDebito,
					Date anoMesFinalVencimentoDebito, int indicadorPagamento, int indicadorConta, int indicadorDebitoACobrar,
					int indicadorGuiasPagamento, Boolean indicadorContas, Integer anoRefereciaDebito) throws ControladorException;

	public Date verificarDataUtilVencimento(Date dataVencimento, Municipio municipio) throws ControladorException;

	/**
	 * @author isilva
	 * @param tipoComando
	 * @param acaoSelecionada
	 * @param empresa
	 * @param periodoInicio
	 * @param periodoFim
	 * @param idCobrancaAcaoAtividadeComando
	 * @param idCobrancaAcaoAtividadeCronograma
	 * @return
	 * @throws ControladorException
	 */
	public Object[] pesquisarRelatorioAcompanhamentoAcao(String tipoComando, String acaoSelecionada, String empresa, String periodoInicio,
					String periodoFim, String idCobrancaAcaoAtividadeComando, String idCobrancaAcaoAtividadeCronograma)
					throws ControladorException;

	/**
	 * @author isilva
	 * @param idEmpresa
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CobrancaContratoRemuneracaoPorSucesso> pesquisarCobrancaContratoRemuneracaoVencimentoPorContratoVigente(
					Integer idEmpresa) throws ControladorException;

	/**
	 * @author isilva
	 * @param idEmpresa
	 * @param tipoComando
	 * @param acaoSelecionada
	 * @param empresa
	 * @param periodoInicio
	 * @param periodoFim
	 * @param idCobrancaAcaoAtividadeComando
	 * @param idCobrancaAcaoAtividadeCronograma
	 * @param diasAteAnterior
	 * @param diasAte
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Object[]> pesquisarCobrancaAcaoEmFaixa(Integer idEmpresa, String tipoComando, String acaoSelecionada, String empresa,
					String periodoInicio, String periodoFim, String idCobrancaAcaoAtividadeComando,
					String idCobrancaAcaoAtividadeCronograma, Integer diasAteAnterior, Integer diasAte) throws ControladorException;

	/**
	 * @author isilva
	 * @param idUnidade
	 * @param periodoInicio
	 * @param periodoFim
	 * @param idGrupo
	 * @param setores
	 * @param bairros
	 * @param tiposServico
	 * @return
	 * @throws ControladorException
	 */
	public Integer relacaoImovelReligacaoEspecialDiaCount(Integer idUnidade, Date periodoInicio, Date periodoFim, Integer idGrupo,
					String[] setores, String[] bairros, String[] tiposServico) throws ControladorException;

	/**
	 * @author isilva
	 * @param idUnidade
	 * @param periodoInicio
	 * @param periodoFim
	 * @param idGrupo
	 * @param setores
	 * @param bairros
	 * @param tiposServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Object[]> relacaoImovelReligacaoEspecialDia(Integer idUnidade, Date periodoInicio, Date periodoFim, Integer idGrupo,
					String[] setores, String[] bairros, String[] tiposServico) throws ControladorException;

	/**
	 * @author isilva
	 * @param idUnidade
	 * @param periodoInicio
	 * @param periodoFim
	 * @param idGrupo
	 * @param setores
	 * @param bairros
	 * @param tiposServico
	 * @return
	 * @throws ControladorException
	 */
	public Collection<RelatorioAcompReligacaoEspecialHelper> relacaoRelatorioImovelReligacaoEspecialDia(Integer idUnidade,
					Date periodoInicio, Date periodoFim, Integer idGrupo, String[] setores, String[] bairros, String[] tiposServico)
					throws ControladorException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * 
	 * @author Pedro Alexandre
	 * @created 01/02/2006
	 * @param grupoCobranca
	 *            Grupo de Cobrança
	 * @param anoMesReferencia
	 *            Ano/Mês de referência do ciclo de cobrança
	 * @param idCronogramaAtividadeAcaoCobranca
	 *            Código do cronograma da atividade da ação de cobrança
	 * @param idComandoAtividadeAcaoCobranca
	 *            Código do comando da atividade da ação de cobrança
	 * @param rotas
	 *            Coleção de rotas
	 * @param acaoCobranca
	 *            Ação de cobrança
	 * @param atividadeCobranca
	 *            Atividade de cobrança
	 * @param indicadorCriterio
	 *            Indicador do critério a ser utilizado
	 * @param criterioCobranca
	 *            Critério de cobrança
	 * @param cliente
	 *            Cliente
	 * @param relacaoClienteImovel
	 *            Tipo de relação entre cliente e imóvel
	 * @param anoMesReferenciaInicial
	 *            Ano/Mês de referência inicial
	 * @param anoMesReferenciaFinal
	 *            Ano/Mês de referência final
	 * @param dataVencimentoInicial
	 *            Data de vencimento inicial
	 * @param dataVencimentoFinal
	 *            Data de vencimento final
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public GerarAtividadeAcaoCobrancaHelper gerarAtividadeAcaoCobrancaPorSetorComercial(CobrancaGrupo grupoCobranca,
					int anoMesReferenciaCicloCobranca, CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Integer idSetorComercial, CobrancaAcao acaoCobranca,
					CobrancaAtividade atividadeCobranca, Integer indicadorCriterio, CobrancaCriterio criterioCobranca, Cliente cliente,
					ClienteRelacaoTipo relacaoClienteImovel, String anoMesReferenciaInicial, String anoMesReferenciaFinal,
					Date dataVencimentoInicial, Date dataVencimentoFinal, Date dataAtual, int idFuncionalidadeIniciada,
					Cliente clienteSuperior, Usuario usuario, SistemaParametro sistemaParametros, Integer idFaturamentoGrupoCronogramaMensal)
					throws ControladorException;

	/**
	 * @param helper
	 * @throws ControladorException
	 */
	public void emitirAtividadeAcaoCobrancaPorSetorComercial(ParametrosHelper helper, Integer idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * @author isilva
	 * @param idPerfilParcelamento
	 * @param idRD
	 * @param idImovelSituacaoTipo
	 * @param idImovelPerfil
	 * @param idSubcategoria
	 * @param valorMinimoDebitoParcelar
	 * @param valorMaximoDebitoParcelar
	 * @param tipoPesquisa
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarExstenciaPerfilParcelamento(Integer idPerfilParcelamento, Integer idRD, Integer idImovelSituacaoTipo,
					Integer idImovelPerfil, Integer idSubcategoria, BigDecimal valorMinimoDebitoParcelar,
					BigDecimal valorMaximoDebitoParcelar, String tipoPesquisa) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @date 10/02/2011
	 * @param descricao
	 */
	void inserirOrgaoExterno(String descricao) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @param descricao
	 * @param descricaoAbreviada
	 * @throws ControladorException
	 */
	public void inserirInfracaoTipo(String descricao, String descricaoAbreviada) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @param descricao
	 * @param descricaoAbreviada
	 * @throws ControladorException
	 */
	public void inserirInfracaoLigacaoSituacao(String descricao, String descricaoAbreviada) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @param descricao
	 * @param descricaoAbreviada
	 * @throws ControladorException
	 */
	public void inserirInfracaoImovelSituacao(String descricao, String descricaoAbreviada) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @param bean
	 * @throws ControladorException
	 */
	public void atualizarInfracaoImovelSituacao(InfracaoImovelSituacao bean) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @param bean
	 * @throws ControladorException
	 */
	public void atualizarInfracaoLigacaoSituacao(InfracaoLigacaoSituacao bean) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @param bean
	 * @throws ControladorException
	 */
	public void atualizarInfracaoTipo(InfracaoTipo bean) throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @param idCategoria
	 * @param idSubcategoria
	 * @param idImovelPerfil
	 * @param idInfracaoTipo
	 */
	public void inserirInfracaoPerfil(Integer idCategoria, Integer idSubcategoria, Integer idImovelPerfil, Integer idInfracaoTipo)
					throws ControladorException;

	/**
	 * @param id
	 * @param idCategoria
	 * @param idSubcategoria
	 * @param idImovelPerfil
	 * @param idInfracaoTipo
	 * @throws ControladorException
	 */
	public void atualizarInfracaoPerfil(Integer id, Integer idCategoria, Integer idSubcategoria, Integer idImovelPerfil,
					Integer idInfracaoTipo) throws ControladorException;

	/**
	 * @param imovel
	 * @return
	 * @throws ControladorException
	 */
	public String[] retornarCodBarrasExtratoDebito(Imovel imovel) throws ControladorException;

	/**
	 * [UC0645] Manter Ação de Cobrança
	 * [SB0002] - Remover Ação de Cobrança
	 * 
	 * @author Anderson Italo
	 * @date 11/03/2011
	 * @throws ControladorException
	 */
	public void removerAcaoCobranca(String[] selecaoCobrancaAcao, Usuario usuario) throws ControladorException;

	/**
	 * @author Bruno Ferreira dos Santos
	 * @param id
	 * @return
	 * @throws ControladorException
	 */
	public PreParcelamentoOpcao obterPreParcelamentoOpcao(Integer id) throws ControladorException;

	/**
	 * @author Bruno Ferreira dos Santos
	 * @param seguencialDocCobrancao
	 * @param idPreParcelamentoOpcao
	 * @return
	 * @throws ControladorException
	 */
	public PreParcelamentoOpcao obterCobrancaDocumentoPreParcelamento(Integer seguencialDocCobrancao, Integer idPreParcelamentoOpcao)
					throws ControladorException;

	/**
	 * UC01101 - Emitir Carta com Opção de parcelamento
	 * Esse método gera Cartas com opções de parcelamento
	 * Emitir Carta Opção de Parcelamento
	 * 
	 * @return void
	 */
	public void emitirCartaOpcaoParcelamento(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio, Usuario usuario) throws ControladorException;

	public PreParcelamentoHelper obterPreParcelamento(CobrancaDocumento documentoCobranca) throws ControladorException;

	public Collection<CobrancaDocumentoItem> obterCobrancaDocumentoItemPrePacelamento(CobrancaDocumento cobrancaDocumento)
					throws ControladorException;

	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaMaiorDataVigenciaInicioComEntrada() throws ControladorException;

	/**
	 * retorna as Resolucões da Diretoria com data Vigência maior
	 * ou igual que a data atual
	 * [UC0643] Inserir Ação de Cobrança
	 * 
	 * @author Anderson Italo
	 * @date 08/11/2006
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual() throws ControladorException;

	public void gerarEntradaParcelamento(Imovel imovel,
					Map<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>>> valorEntradaPorTipoDebito,
					Integer parcelamentoId, Collection<ContaValoresHelper> colecaoContaValores, Usuario usuarioLogado, Pagamento pagamento,
					String indicadorContasRevisao, String indicadorCobrancaParcelamento, Integer idGuiaPagamentoGeral,
					Integer numeroDiasVencimentoEntrada, Date dataEntradaParcelamento, Short indicadorTotalRemuneracaoCobrancaAdm)
					throws ControladorException;

	public Collection<Conta> classificarContasParaHistorico(Collection<ContaValoresHelper> colecaoContaValores, Usuario usuarioLogado,
					String indicadorContasRevisao) throws ControladorException;

	public Collection<GuiaPagamento> classificarGuiaPagamentoParaHistorico(
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores, String indicadorGuiasPagamento)
					throws ControladorException;

	public void classificarDebitoACobrarParaHistorico(Collection<DebitoACobrar> colecaoDebitoACobrar, String indicadorDebitosACobrar,
					SistemaParametro sistemaParametros, Collection<DebitoACobrar> colecaoDebitoACobrarHelp,
					Collection<DebitoACobrar> colecaoDebitoACobrarTemp, Map debitoACobrarValorDivida) throws ControladorException;

	public Collection<CreditoARealizar> classificarCreditoARealizarParaHistorico(Collection<CreditoARealizar> colecaoCreditoARealizar,
					String indicadorCreditoARealizar, SistemaParametro sistemaParametros) throws ControladorException;

	public void classificarDebitoACobrarTempParaHistorico(Collection<DebitoACobrar> colecaoDebitoACobrarTemp, Map debitoACobrarValorDivida,
					Integer parcelamentoId) throws ControladorException;

	public Collection<CobrancaSituacaoHistorico> pesquisarCobrancaSituacaoHistorico(Integer idImovel, Integer anoMesFinal)
					throws ControladorException;

	public Integer pesquisarDebitosACobrarDeParcelamentoEmAberto(Integer idImovel, Integer anoMesReferencia,
					Collection<Integer> idsDebitoTipo) throws ControladorException;

	public void atualizarInfracaoDebitoTipo(Collection colecaoDebitoTipo, Collection remocao, Collection inserir)
					throws ControladorException;

	/**
	 * @date 01/03/2011
	 * @author Andre Nishimura
	 * @return sequence infracao perfil
	 * @throws ControladorException
	 */
	public Integer obterSequenceInfracaoPerfil() throws ControladorException;

	/**
	 * @date 02/03/2011
	 * @author Andre Nishimura
	 * @param colecaoInfracaoPerfil
	 * @param mapInfracaoPerfilDebitoTipo
	 * @throws ControladorException
	 */
	public void inserirInfracaoPerfil(Collection colecaoInfracaoPerfil, Map mapInfracaoPerfilDebitoTipo) throws ControladorException;

	public void removerInfracaoPerfil(String[] ids, Usuario usuarioLogado) throws ControladorException;

	public Collection<BoletoBancario> pesquisarBoletoBancario(BoletoBancarioHelper boletoBancarioHelper,
					boolean verificarNumeroBoletoCartaCobranca, boolean desconsiderarParametros, boolean verificarDocumentoCobranca,
					int pageOffset) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Insere Débito A Cobrar de acordo com Tipo do Débito do Imóvel
	 * inserirDebitoACobrarDebitoTipo
	 * 
	 * @author Roberta Costa
	 * @date 05/04/2006
	 * @author Isaac Silva
	 * @date 22/08/2011
	 * @param debitoTipo
	 * @param imovel
	 * @param numeroPrestacao
	 * @param valorDebito
	 * @param taxaJuros
	 * @param parcelamentoId
	 * @param colecaoCategoria
	 * @param parcelamentoGrupoId
	 * @param debitoCreditoSituacaoId
	 * @param efetuarParcelamento
	 *            se true atualiza os atributos: numeroMesesEntreParcelas, numeroParcelasALancar,
	 *            numeroMesesInicioCobranca que serão usados no faturamento;
	 * @param numeroMesesEntreParcelas
	 * @param numeroParcelasALancar
	 * @param numeroMesesInicioCobranca
	 * @throws ControladorException
	 */
	public void inserirDebitoACobrarDebitoTipo(DebitoTipo debitoTipo, Imovel imovel, Short numeroPrestacao, BigDecimal valorDebito,
					BigDecimal taxaJuros, Integer parcelamentoId, Collection<Categoria> colecaoCategoria, Integer parcelamentoGrupoId,
					Integer debitoCreditoSituacaoId, boolean efetuarParcelamento, Integer numeroMesesEntreParcelas,
					Integer numeroParcelasALancar, Integer numeroMesesInicioCobranca, Integer anoMesReferenciaDebito)
					throws ControladorException;

	/**
	 * Gera os Débitos a Cobrar das diferença entre os débitos
	 * 
	 * @author Isaac Silva
	 * @date 31/08/2011
	 * @param imovel
	 * @param valorAtualizacaoMonetaria
	 * @param valorJurosMora
	 * @param valorMulta
	 * @param taxaJuros
	 * @param parcelamentoId
	 * @param colecaoCategoria
	 * @param efetuarParcelamento
	 *            se true atualiza os atributos: numeroMesesEntreParcelas, numeroParcelasALancar,
	 *            numeroMesesInicioCobranca que serão usados no faturamento;
	 * @param numeroMesesEntreParcelas
	 * @param numeroParcelasALancar
	 * @param numeroMesesInicioCobranca
	 * @throws ControladorException
	 */
	public void gerarDebitosACobrarDiferencaEntreDebitos(Imovel imovel, BigDecimal valorAtualizacaoMonetaria, BigDecimal valorJurosMora,
					BigDecimal valorMulta, BigDecimal taxaJuros, Integer parcelamentoId, Collection<Categoria> colecaoCategoria,
					boolean efetuarParcelamento, Integer numeroMesesEntreParcelas, Integer numeroParcelasALancar,
					Integer numeroMesesInicioCobranca) throws ControladorException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imóvel ou Cliente Obtem o Valor
	 * Total dos Pagamentos da Conta Author: Rafael Santos Data: 05/01/2006
	 * 
	 * @param idConta
	 *            Id Conta
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarValorTotalPagamentoMenorDataPagamento(Integer idConta) throws ControladorException;

	/**
	 * Permite efetuar o parcelamento dos débitos de um imóvel
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Pega o Tipo do Débito de acordo com a constante informada
	 * filtrarDebitoTipo
	 * 
	 * @author Roberta Costa
	 * @date 05/04/2006
	 * @param debitoTipo
	 * @param imovel
	 * @param valorDebito
	 * @param taxaJuros
	 * @param parcelamentoId
	 */
	public DebitoTipo filtrarDebitoTipo(Integer tipoDebito) throws ControladorException;

	/**
	 * @author Isaac Silva
	 * @date 23/09/2011
	 * @param cobrancaDocumento
	 * @return
	 * @throws ControladorException
	 */
	public Collection<CobrancaDocumentoItem> obterCobrancaDocumentoItem(CobrancaDocumento cobrancaDocumento) throws ControladorException;

	/**
	 * [UC3018] Gerar TXT Cartas Cobrança Bancária.
	 * Método que vai gerar o arquivo TXT com as cartas que serão enviadas com o aviso de cobrança.
	 * 
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idComandoCobranca
	 * @throws ControladorException
	 */
	public void gerarTXTCartasCobrancaBancaria(Integer idComandoCobranca, Usuario usuario) throws ControladorException;

	/**
	 * [UC3019] Identificar Cobrança Bancária com Negociação.
	 * Método que Identifica cobranças bancárias que foram Negociadas, verificando o cumprimento ou
	 * não do acordo firmado entre cliente e Empresa, através do pagamento da 1ª. Prestação.
	 * 
	 * @author Ailton Sousa
	 * @date 19/10/2011
	 * @throws ControladorException
	 */
	public void identificarCobrancaBancariaComNegociacao(int idFuncionalidadeIniciada, Integer idProcessoIniciado)
					throws ControladorException;

	/**
	 * [UC3016] Gerar Boleto Cobrança Bancária
	 * 
	 * @date 18/10/2011
	 * @author Péricles Tavares
	 * @param idComandoCobrancaEventual
	 * @param idGuiaPagamento
	 * @param numeroPrestacaoInicialGuia
	 * @param usuario
	 * @throws ControladorException
	 */
	public Collection<BoletoBancario> gerarBoletoCobrancaBancaria(Integer idComandoCobrancaEventual, Integer idGuiaPagamento,
					Integer numeroPrestacaoInicialGuia,
					Usuario usuario) throws ControladorException;

	/**
	 * @author Yara Souza
	 * @date 14/10/2011
	 * @param anoMesReferenciaFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public Map<Banco, Collection> pesquisaCobrancaBancariaMovimento(Integer anoMesReferenciaFaturamento) throws ControladorException;

	/**
	 * @param boletoBancarioMovimentacaoBancosMap
	 * @param usuario
	 * @throws ControladorException
	 */

	public void gerarMovimentoCobrancaBancaria(Map<Banco, Collection> boletoBancarioMovimentacaoBancosMap, Usuario usuario)
					throws ControladorException;

	/**
	 * @param arrecadadorMovimento
	 * @param envioBanco
	 * @param usuario
	 * @throws ControladorException
	 */
	public void regerarArquivoTxtMovimentoCobrancaBancaria(ArrecadadorMovimento arrecadadorMovimento, String envioBanco, Usuario usuario)
					throws ControladorException;

	/**
	 * Obter Criterio de cobranca associado ao documento de cobranca correspondente a negativação
	 * [UC0688]-Gerar Resumo Diario de Negativacao
	 * [SB0002] / Step 2
	 * 
	 * @autor Genival Barbosa
	 * @date 26/10/2011
	 * @return
	 * @throws ControladorException
	 */
	public Collection obterCobrancaCriterioAssociadoDocumentoCobrancaCorrespNegativacao(Integer idNegativadorMovimento)
					throws ControladorException;

	/**
	 * Pesquisar Quantidade de Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Integer pesquisarQuantidadeBoletoBancario(BoletoBancarioHelper boletoBancarioHelper, boolean verificarNumeroBoletoCartaCobranca,
					boolean desconsiderarParametros, boolean verificarDocumentoCobranca) throws ControladorException;

	/**
	 * Pesquisar Boleto Bancário pelo Id
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public BoletoBancario pesquisarBoletoBancarioPeloId(Integer id) throws ControladorException;

	/**
	 * [SB0001] Atualizar Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public void atualizarBoletoBancario(BoletoBancario boletoBancario, Integer idBoletoBancarioLancamentoEnvio, Date dataVencimentoTitulo,
					Usuario usuarioLogado) throws ControladorException;

	/**
	 * Pesquisar Quantidade de Movimentação Pendente ou sem Retorno
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Integer pesquisarQuantidadeMovimentacaoPendenteOuSemRetorno(Integer idBoletoBancario) throws ControladorException;

	/**
	 * [SB0002] Cancelar Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public void cancelarBoletoBancario(Integer idMotivoCancelamento, String[] idRegistrosCancelamento, Usuario usuario)
					throws ControladorException;

	/**
	 * Pesquisar Guia de pagamento Prestação pelo Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoBoleto(Integer guiaPagamentoId, Integer numeroPrestacao)
					throws ControladorException;

	/**
	 * Pesquisar Contas Pelo Boleo Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Collection<BoletoBancarioContaHelper> pesquisarContasPeloBoletoBancario(Integer idBoletoBancario) throws ControladorException;

	/**
	 * Pesquisar Quantidade de Boletos Vigentes Filtrando pelo Imóvel
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Integer pesquisarQuantidadeBoletosVigentesDoImovel(Integer idImovel, Integer idBoleto) throws ControladorException;

	/**
	 * Pesquisar Conteúdo do Arrecadador Movimento Ítem pelo Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public String pesquisarConteudoArrecadadorMovimentoItem(Integer idBoletoBancario) throws ControladorException;

	/**
	 * Pesquisar Boleto Bancário Totalizador
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Collection<BoletoBancarioTotalizadorHelper> pesquisarBoletoBancarioTotalizadorPorImovel(
					BoletoBancarioHelper boletoBancarioHelper, boolean desconsiderarParametros, boolean verificarDocumentoCobranca,
					boolean verificarNumeroBoletoCartaCobranca, int pageOffset) throws ControladorException;

	/**
	 * @author Hugo Lima
	 * @date 02/12/2011
	 * @param resolucaoDiretoria
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResolucaoDiretoria> consultarResolucaoDiretoriaGrupo(ResolucaoDiretoriaGrupoHelper resolucaoDiretoriaGrupoHelper,
					int pageOffset) throws ControladorException;

	/**
	 * Obter Cobrança Negociação Atendimento
	 * 
	 * @author Hebert Falcão
	 * @date 25/11/2011
	 */
	public CobrancaNegociacaoAtendimento obterCobrancaNegociacaoAtendimento(Integer idCobrancaDocumento) throws ControladorException;

	/**
	 * Re-enviar documento de negociação atendimento
	 * 
	 * @author Hebert Falcão
	 * @date 25/11/2011
	 */
	public void reEnviarDocumentoNegociacaoAtendimento(String idCobrancaNegociacaoAtendimento, String emailNegociacao)
					throws ControladorException;

	public RelatorioExtratoDebito obterRelatorioExtratoDebito(ExtratoDebitoRelatorioHelper extratoDebitoRelatorioHelper, Imovel imovel,
					BigDecimal valorDebitosACobrar, BigDecimal valorAcrescimosImpontualidade, BigDecimal valorDescontoCredito,
					BigDecimal valorDesconto, BigDecimal valorDocumento, Usuario usuario, String inscricao, String nomeUsuario,
					String matricula, String enderecoImovel, String quantidadeParcelas, String mensagemPagamentoAVista,
					String quantidadeParcelasDebitos, Integer quantidadeDebitoACobrar, Integer quantidadeParcelamento)
					throws ControladorException;

	/**
	 * Inserir Boleto Bancário Movimentação
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public void inserirBoletoBancarioMovimentacao(BoletoBancario boletoBancario, Usuario usuarioLogado,
					BoletoBancarioLancamentoEnvio boletoBancarioLancamentoEnvio) throws ControladorException;

	/**
	 * Pesquisar Quantidade de Ítens Pagos
	 * 
	 * @author Hebert Falcão
	 * @date 19/12/2011
	 */
	public Integer pesquisarQuantidadeDeItensPagos(Integer idCobrancaDocumento) throws ControladorException;

	public void atualizarCobrancaDocumento(CobrancaDocumento cobrancaDocumento) throws ControladorException;

	public Object[] verificarSituacaoImovelPerfilParcelamento(Integer situacaoAguaId, Integer situacaoEsgotoId, Integer codigoImovel,
					Integer perfilImovelId, Integer resolucaoDiretoria, Integer numeroReparcelamentoConsecutivos,
					BigDecimal valorDebitoACobrarParcelamentoImovel, boolean verificaNulidade,
					ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao, Usuario usuario) throws ControladorException;

	/**
	 * [UC3031] Efetuar Negociação de Débitos
	 * 
	 * @author Hebert Falcão
	 * @date 11/12/2011
	 */
	public void efetuarNegociacaoDeDebitos(Integer idImovel, Integer idClienteUsuario, Short indicadorPessoaFisicaJuridica,
					String cpfCnpjCliente, String emailCliente, Usuario usuarioLogado, Short negociacaoQuantidadeParcelas,
					BigDecimal negociacaoValorParcela, BigDecimal negociacaoValorEntrada, BigDecimal negociacaoValorDebitoAposDesconto,
					String negociacaoIndicadorPagamentoCartaoCredito, Collection<ContaValoresHelper> colecaoContaValores,
					Collection<DebitoACobrar> colecaoDebitoACobrar, Collection<CreditoARealizar> colecaoCreditoARealizar,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores, BigDecimal valorAcrescimosImpontualidade,
					BigDecimal valorDebitoACobrarServico, BigDecimal valorDebitoACobrarParcelamento, BigDecimal valorDesconto,
					BigDecimal valorDescontoCredito, String inscricaoImovel, String enderecoImovel,
					NegociacaoOpcoesParcelamentoHelper negociacaoOpcoesParcelamento, BigDecimal valorTotalConta, BigDecimal valorTotalGuia,
					BigDecimal valorTotalAtualizacaoMonetaria, BigDecimal valorTotalJurosMora, BigDecimal valorTotalMulta,
					BigDecimal valorDebitoTotalAtualizado, BigDecimal negociacaoValorDescontoAntiguidade,
					BigDecimal negociacaoValorDescontoInatividade, Integer negociacaoNumeroMesesEntreParcelas,
					Integer negociacaoNumeroParcelasALancar, Integer negociacaoNumeroMesesInicioCobranca,
					Integer negociacaoNumeroDiasVencimentoDaEntrada, BigDecimal negociacaoTaxaJuros, Integer negociacaoIdRD)
					throws ControladorException;

	public Collection obterColecaoDebitosACobrarDoParcelamento(Collection<DebitoACobrar> colecaoDebitosACobrar) throws ControladorException;

	/**
	 * Este caso de uso emite a relação dos documentos de cobrança
	 * [UC3032] Emitir Relação Documentos Cobrança
	 * 
	 * @author Cinthya Cavalcanti
	 * @created 23/12/2011
	 * @param
	 * @return void
	 * @throws ControladorException
	 */
	public void emitirRelacaoDocumentosCobranca(Usuario usuario, Integer idFuncionalidadeIniciada,
					CobrancaAcaoAtividadeComando comandoAtividadeAcaoComando) throws ControladorException;

	public void emitirRelacaoDocumentosCobranca(Usuario usuario, String descricaoCobrancaAcao, Integer idFuncionalidadeIniciada,
					Integer idFaturamentoGrupoCronogramaMensal, CobrancaAcaoAtividadeComando comandoAtividadeAcaoComando)
					throws ControladorException;

	/**
	 * Pesquisar Documentos de Cobrança para geração do relatório
	 * 
	 * @author Cinthya Cavalcanti
	 * @created 23/12/2011
	 * @param
	 * @return void
	 * @throws ControladorException
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoRelatorioEmitirRelacaoDocumentos(int idCobrancaAtividadeAcaoComando)
					throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * [SB0016] – Obter Boletos Bancários para Negociação.
	 * O sistema obtém os boletos bancários do imóvel possíveis de serem negociados.
	 * 
	 * @author Ailton Sousa
	 * @date 20/12/2011
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection<BoletoBancario> obterBoletosBancariosParaNegociacao(Integer idImovel) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * [SB0016] – Obter Boletos Bancários para Negociação.
	 * Obtém a data de entrada através da entidade BoletoBancarioSituacaoHistorico pelo ID do
	 * BoletoBancario.
	 * 
	 * @author Ailton Sousa
	 * @date 20/12/2011
	 * @param idBoletoBancario
	 * @return
	 * @throws ControladorException
	 */
	public Date obterDataEntradaBoletoBancarioSituacaoHistorico(Integer idBoletoBancario) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * [SB0017] – Determinar Parametrização do Parcelamento de Cobrança Bancária
	 * Obtém a menor referência das contas associadas ao boleto bancário selecionado para
	 * negociação.
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @param idBoletoBancario
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterMenorReferenciaContaBoletoBancario(Integer idBoletoBancario) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * [SB0017] – Determinar Parametrização do Parcelamento de Cobrança Bancária
	 * Obtém a maior referência das contas associadas ao boleto bancário selecionado para
	 * negociação.
	 * 
	 * @author Ailton Sousa
	 * @date 22/12/2011
	 * @param idBoletoBancario
	 * @return
	 * @throws ControladorException
	 */
	public Integer obterMaiorReferenciaContaBoletoBancario(Integer idBoletoBancario) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * [FS0016] – Verificar se usuário possui autorização para utilizar a RD
	 * 
	 * @author Ailton Sousa
	 * @date 27/12/2011
	 * @param idUsuario
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaPermitidaAoUsuario(Integer idUsuario) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * Verifica se a conta está associada ao boleto bancário da negociação.
	 * 
	 * @author Ailton Sousa
	 * @date 18/01/2012
	 * @param idBoletoBancario
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */

	public boolean isContaAssociadaAoBoletoBancario(Integer idBoletoBancario, Integer idConta) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * 
	 * @param idResolucaoDiretoria
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ParcelamentoSituacaoEspecial> verificarRDComRestricao(Integer idResolucaoDiretoria) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * 
	 * @param idLocalidade
	 * @param intervalorParcelamentoInicial
	 * @param intervalorParcelamentoFinal
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ParcelamentoSituacaoEspecial> pesquisarParcelamentoSituacaoEspecialPorLocalidade(Integer idLocalidade,
					Integer intervalorParcelamentoInicial, Integer intervalorParcelamentoFinal) throws ControladorException;

	/**
	 * [UC0111] Iniciar Processo
	 * [SB0007] – Obter Dados Complementares do Comando de Cronograma de Cobrança
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @param idComandoFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public String obterDadosComplementaresComandoCronogramaCobranca(Integer idComandoFaturamento) throws ControladorException;

	/**
	 * [UC0111] Iniciar Processo
	 * [SB0008] – Obter Dados Complementares do Comando Eventual de Cobrança
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @param idComandoFaturamento
	 * @return
	 * @throws ControladorException
	 */
	public String obterDadosComplementaresComandoEventualCobranca(Integer idComandoFaturamento) throws ControladorException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public Object[] realizarArrastoDeParcelamento(Integer idImovel) throws ControladorException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Consultar os parcelamentos ativos do imóvel recebido, realizados com forma de cobrança em
	 * conta e com prestações a serem cobradas de tipo de lançamento contábil diferente de Juros de
	 * Parcelamento
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public Collection<Parcelamento> pesquisarParcelamentosAtivo(Integer idImovel) throws ControladorException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Verifica se o imóvel possua multa por descumprimento para a referência do parcelamento
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public boolean verificarExistenciaMultaPorDescumprimento(Integer idParcelamento) throws ControladorException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Selecionar as prestações que não sejam referentes ao tipo de lançamento contábil Juros de
	 * Parcelamento e que estejam vencidos
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public Integer obterQuantidadePrestacoesVencidas(Integer idParcelamento, Integer fatorReducao) throws ControladorException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Obter débito a cobrar ativo associado a um parcelamento e que tenha prestações a serem
	 * cobradas
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public Collection<DebitoACobrar> obterDebitoACobrarAtivoAssociadoParcelamento(Integer idParcelamento) throws ControladorException;

	/**
	 * [UC3052] Gerar e Emitir Aviso de Corte e Ordem de Corte Individual
	 * 
	 * @author Hebert Falcão
	 * @throws ControladorException
	 * @date 16/03/2012
	 */
	// public RelatorioAvisoEOrdemCorteIndividual
	// gerarEmitirAvisoDeCorteOrdemDeCorteIndividual(Integer idImovel, BigDecimal valorDocumento,
	// Collection<ContaValoresHelper> colecaoContas, Collection<DebitoACobrar>
	// colecaoDebitosACobrar,
	// Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamento, Collection<CreditoARealizar>
	// colecaoCreditoARealizar,
	// Usuario usuario) throws ControladorException;
	public CobrancaAcaoIndividualHelper gerarEmitirAvisoDeCorteOrdemDeCorteIndividual(Integer idImovel,
					BigDecimal valorDocumento, Collection<ContaValoresHelper> colecaoContas,
					Collection<DebitoACobrar> colecaoDebitosACobrar, Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamento,
					Collection<CreditoARealizar> colecaoCreditoARealizar, Usuario usuario,
					Collection<CobrancaAcaoIndividualHelper> colecaoCobrancaAcaoHelper, Short indicadorAtividade)
					throws ControladorException;

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * [SB9000] - Obter Data Corte ou Supressao
	 * 
	 * @author Yara Souza
	 * @date 20/03/2012
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Date obterDataCorteOuSupressao(OrdemServico ordemServico, String indicadorDataRetorno) throws ControladorException;

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * [SB9001] - Obter Contas Vencidas do Imóvel
	 * 
	 * @author Yara Souza
	 * @date 20/03/2012
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Object[] obterContasVencidasDoImovel(Integer idImovel, boolean limitarQuantidade) throws ControladorException;

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * [SB9002] – Obter Dados Últimos Consumos
	 * 
	 * @author Carlos Chrystian
	 * @date 11/04/2013
	 * @param ordemServico
	 * @return
	 */
	public Collection<DadosUltimosConsumosHelper> obterDadosUltimosConsumos(Integer idImovel, int numeroMeses) throws ControladorException;

	/**
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * [FS0003] - Verificar permissão para inclusão de contas em revisão por Cobrança Bancária
	 * 
	 * @author Anderson Italo
	 * @date 24/04/2012
	 * @throws ControladorException
	 */
	public void verificarPermissaoInclusaoContasRevisaoCobrancaBancaria(Collection idsContas, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [FS0003] - Verificar permissão para inclusão de contas em revisão por Cobrança Bancária
	 * 
	 * @author Yara Souza
	 * @date 30/04/2012
	 * @throws ControladorException
	 */

	public boolean verificarPermissaoImpressaoContasRevisaoCobrancaBancaria(Usuario usuarioLogado, Conta conta) throws ControladorException;

	/**
	 * Conta os registros para mandar pra batch ou gerar online o relatório
	 * 
	 * @Autor: Ítalo Almeida
	 * @Date: 03/05/2012
	 */

	public Integer gerarCurvaAbcDebitosCount(String classificacao, String indicadorImovelMedicaoIndividualizada,
					String indicadorImovelParalizacaoFaturamentoCobranca, String[] gerenciaRegional, String idLocalidadeInicial,
					String idLocalidadeFinal, String idSetorComercialInicial, String idSetorComercialFinal, String[] situacaoLigacaoAgua,
					String[] situacaoLigacaoEsgoto, String intervaloConsumoMinimoFixadoEsgotoInicial,
					String intervaloConsumoMinimoFixadoEsgotoFinal, String indicadorMedicao, String idTipoMedicao, String idPerfilImovel,
					String idTipoCategoria, String[] categoria, String idSubCategoria) throws ControladorException;

	// /**
	// * Guias de pagamento de parcelamento de cobrança bancária com boleto gerado e válido
	// *
	// * @author Hebert Falcão
	// * @date 07/05/2012
	// */
	// public boolean
	// verificarGuiaPagamentoParcelamentoCobrancaBancariaComBoletoGeradoValido(Integer
	// idGuiaPagamento, Short numeroPrestacoes)
	// throws ControladorException;
	//
	// /**
	// * Guias de pagamento de parcelamento de cobrança bancária pendentes de geração do boleto
	// *
	// * @author Hebert Falcão
	// * @date 07/05/2012
	// */
	// public boolean
	// verificarGuiaPagamentoParcelamentoCobrancaBancariaPendentesGeracaoBoleto(Integer
	// idGuiaPagamento, Short numeroPrestacoes)
	// throws ControladorException;

	/**
	 * [FS0037] - Verificar existência de guia de parcelamento de cobrança bancária
	 * Remover guia de pagamento na composição do débito para o parcelamento
	 * 
	 * @author Hebert Falcão
	 * @date 13/05/2012
	 */
	public void removerGuiaDePagamentoNaComposicaoDoDebitoParcelamennto(Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores)
					throws ControladorException;

	/**
	 * Permite a obtenção dos débitos de um imóvel ou de um cliente
	 * [UC0067] Obter Débito do Imóvel ou Cliente
	 * 
	 * @author Rafael Santos ,Rafael Santos, Pedro Alexandre
	 * @date 04/01/2006,22/03/2006,13/03/2007
	 * @author eduardo henrique
	 * @date 12/08/2008
	 *       Alteração na obtenção de Guias de Pagamento para o Imóvel / Cliente
	 * @author Saulo Lima
	 * @date 17/07/2009
	 *       Mudança no objeto 'GuiaPagamentoValoresHelper'
	 * @author Josenildo Neves
	 * @date 23/05/2012
	 *       Refactor da recuperação dos débitos dos imóveis
	 * @param indicadorDebito
	 * @param idImovel
	 * @param codigoCliente
	 * @param clienteRelacaoTipo
	 * @param anoMesInicialReferenciaDebito
	 * @param anoMesFinalReferenciaDebito
	 * @param anoMesInicialVencimentoDebito
	 * @param anoMesFinalVencimentoDebito
	 * @param indicadorPagamento
	 * @param indicadorConta
	 * @param indicadorCalcularAcrescimoImpontualidade
	 * @param indicadorContas
	 * @param anoMesArrecadacao
	 * @param idImoveis
	 * @return
	 * @throws ControladorException
	 */
	public ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteContas(int indicadorDebito, String idImovel, String codigoCliente,
					Integer clienteRelacaoTipo, String anoMesInicialReferenciaDebito, String anoMesFinalReferenciaDebito,
					Date anoMesInicialVencimentoDebito, Date anoMesFinalVencimentoDebito, int indicadorPagamento, int indicadorConta,
					int indicadorCalcularAcrescimoImpontualidade, Boolean indicadorContas, String anoMesArrecadacao, Collection idImoveis,
					Date dataEmissaoDocumento, Short indicadorEmissaoDocumento, Short indicadorConsiderarPagamentoNaoClassificado,
					Short multa, Short jurosMora, Short atualizacaoTarifaria)
					throws ControladorException;

	/**
	 * @param indicadorDebito
	 * @param idImovel
	 * @param anoMesInicialReferenciaDebito
	 * @param anoMesFinalReferenciaDebito
	 * @param anoMesInicialVencimentoDebito
	 * @param anoMesFinalVencimentoDebito
	 * @return
	 * @throws ControladorException
	 */
	public ObterDebitoImovelOuClienteHelper obterDebitoImovelContas(int indicadorDebito, String idImovel,
					String anoMesInicialReferenciaDebito, String anoMesFinalReferenciaDebito, Date anoMesInicialVencimentoDebito,
					Date anoMesFinalVencimentoDebito) throws ControladorException;

	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0476] [SB0003] – Gerar Arquivo TXT Ordem de Corte – Modelo 2
	 * 
	 * @author Hugo Lima
	 * @date 06/06/2012
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param usuario
	 * @throws ControladorException
	 */
	public void emitirOrdemCorteArquivoTXT(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario) throws ControladorException;

	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0476] [SB0002] – Gerar Arquivo TXT Ordem de Corte – Modelo 1
	 * 
	 * @author Hugo Lima
	 * @data 06/06/2012
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param dataAtualPesquisa
	 * @param acaoCobranca
	 * @param grupoCobranca
	 * @param cobrancaCriterio
	 * @return void
	 * @throws ControladorException
	 */
	public void emitirDocumentoCobrancaOrdemCorte(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Date dataAtualPesquisa, CobrancaAcao acaoCobranca,
					CobrancaGrupo grupoCobranca, CobrancaCriterio cobrancaCriterio) throws ControladorException;

	/**
	 * Este caso de uso gera os avisos de corte
	 * [UC0476] [SB0006] – Gerar Arquivo TXT Ordem de Corte – Modelo 3
	 * 
	 * @author Hugo Lima
	 * @date 07/06/2012
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param usuario
	 * @throws ControladorException
	 */
	public void emitirOrdemCorteArquivoTXTModelo3(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * Separa a lista de documentos cobrança em:
	 * Lista dos Documentos de Cobrança com Entrega para o Cliente Responsável
	 * Lista dos Documentos de Cobrança com Entrega para o Cliente Usuário
	 * Lista dos Documentos de Cobrança com Entrega para o Cliente Proprietário
	 * Lista dos Documentos de Cobrança com Entrega para o Imóvel
	 * E ordena de acordo com cada critérios.
	 * [UC0349] Emitir Documento de Cobrança - Aviso de Corte
	 * 
	 * @author Josenildo Neves
	 * @date 07/06/2012
	 * @param colecaoCobrancaDocumento
	 * @return
	 */
	public CobrancaDocumentoColecoesOrdenadasHelper tratarColecaoDocumentosCobranca(Collection<CobrancaDocumento> colecaoCobrancaDocumento)
					throws ControladorException;

	public Collection<CobrancaDocumento> pesquisarTodosCobrancaDocumentoParaEmitir(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao) throws ControladorException;

	/**
	 * Atualiza o sequencial de acordo com a metade da coleção e da situação.
	 * Ex.: se situação for igual a 1 e a metade da coleção for 500 e o
	 * sequencial for 503 então: sequencial será igual a 3.
	 * 
	 * @author Sávio Luiz
	 * @date 22/01/2007
	 * @return
	 * @throws ControladorException
	 */
	public int atualizaSequencial(int sequencial, int situacao, int metadeColecao);

	/**
	 * Divide uma coleçao de object de tamanho de acordo com o parametro,
	 * utilizado para limitar a geraçao de relatorios de pdf em xxx itens por arquivo.
	 * 
	 * @param colecao
	 * @param int dlimitando o tamanho da divisao
	 * @return Collection<Collection<Object>>
	 */
	public Collection<Collection<Object>> dividirColecaoEmBlocos(List<Object> colecao, int quantidadeLimitePorArquivo);

	/**
	 * [UC0349] [SB0003] – Gerar Arquivo TXT Aviso de Corte – Modelo 2
	 * pesquisarSetorComercialCobrancaDocumento
	 * 
	 * @author Carlos Chrystian
	 * @date 28/12/2011
	 * @param idCobrancaAcaoCronograma
	 * @param idCobrancaAcaoComando
	 * @throws ControladorException
	 */
	public Collection<CobrancaDocumento> pesquisarSetorComercialCobrancaDocumento(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando) throws ControladorException;

	/**
	 * [UC0349] [SB0003] – Gerar Arquivo TXT Aviso de Corte – Modelo 2
	 * [FS001] – Formatar conteúdo referente a contas em revisão
	 * 
	 * @author Carlos Chrystian
	 * @date 20/12/2011
	 * @param idComandoCobranca
	 * @throws ControladorException
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoArquivoTXT(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Integer idSetorComercial) throws ControladorException;

	/**
	 * [UC0349] [SB0003] – Gerar Arquivo TXT Aviso de Corte – Modelo 2
	 * [FS001] – Formatar conteúdo referente a contas em revisão
	 * 
	 * @author Carlos Chrystian
	 * @date 20/12/2011
	 * @param idComandoCobranca
	 * @throws ControladorException
	 */
	public String obterMensagemContaEmRevisaoImovel(Integer idCobrancaDocumento) throws ControladorException;

	/**
	 * [UC0349] - Emitir documento de cobrança
	 * Retorna os itens de documento de cobrança ordenados pela referência da conta
	 * 
	 * @created 11/06/2012
	 * @author Luciano Galvao
	 */
	public Collection pesquisarCobrancaDocumentoItens(Integer idCobrancaDocumento) throws ControladorException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Aviso de corte Arquivo TXT
	 * Seleciona os ids dos documentos de cobrança para a geração Modelo 3
	 * 
	 * @return Collection<Integer>
	 */
	public Collection pesquisarCobrancaDocumentoIds(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando)
					throws ControladorException;

	/**
	 * Método que busca o setor comercial por cobranca documento.
	 * 
	 * @param idCobrancaDocumento
	 *            id a ser informado.
	 */
	public Object[] pesquisarSetorComercialPorCobrancaDocumento(Integer idCobrancaDocumento) throws ControladorException;

	/**
	 * Método que obtém total de descrições em Ação de Cobrança a partir de um setor
	 * 
	 * @author José Cláudio
	 * @param cdSetorComercial
	 *            codigo a ser informado.
	 */
	public Integer pesquisarTotalDescricoesCobrancaAcaoPorSetor(Integer cdSetorComercial) throws ControladorException;

	/**
	 * Método que obtém total de descrições em Ação de Cobrança a partir de uma localidade
	 * 
	 * @author José Cláudio
	 * @param idLocalidade
	 *            id a ser informado.
	 */
	public Integer pesquisarTotalDescricoesCobrancaAcaoPorLocalidade(Integer idLocalidade) throws ControladorException;

	/**
	 * Obtém total de ocorrencias em documento de Cobrança a partir de um setor comercial
	 * 
	 * @autor José Cláudio
	 * @param Integer
	 *            cdSetorComercial
	 * @return Total de ocorrencias em documento de Cobrança a partir de um setor comercial
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 06/06/2012
	 */

	public Integer pesquisarTotalOcorrenciasCobrancaAcaoPorSetor(Integer cdSetorComercial, Integer localidadeId,
					Integer faturamentoGrupoMensalId, Integer cobrancaAcaoAtividadeComandoId, Integer idCobrancaAcao)
					throws ControladorException;

	/**
	 * Obtém total de ocorrencias em documento de Cobrança a partir de uma localidade
	 * 
	 * @autor José Cláudio
	 * @param Integer
	 *            idLocalidade
	 * @return Total de ocorrencias em documento de Cobrança a partir de uma localidade
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 * @date 06/06/2012
	 */

	public Integer pesquisarTotalOcorrenciasCobrancaAcaoPorLocalidade(Integer idLocalidade, Integer faturamentoGrupoMensalId,
					Integer cobrancaAcaoAtividadeComandoId, Integer idCobrancaAcao) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Verifica se existe, dentro da listagem de debitos de um imovel ou cliente, uma ocorrencia de
	 * conta ou guia de pagamento em cobranca administrativa
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param colecaoContasValoresHelper
	 * @param colecaoGuiasPagamentoValoresHelper
	 * @return
	 * @throws ControladorException
	 */
	public boolean existeContaOuGuiaPagamentoDebitoImovelOuCliente(Collection<ContaValoresHelper> colecaoContasValoresHelper,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValoresHelper) throws ControladorException;

	/**
	 * [UC0614] Gerar Resumo das Ações de Cobrança Eventuais
	 * Pós-condição: Resumo das ações de cobrança gerado e atividade encerrar da
	 * ação de cobrança, se for o caso, realizada
	 * 
	 * @author Sávio Luiz
	 * @date 15/06/2007
	 */
	public void gerarResumoAcoesCobrancaEventual(Object[] dadosCobrancaAcaoAtividadeEventual, int idFuncionalidadeIniciada,
					Collection<CobrancaAcaoAtividadeComando> colecaoCobrancaAcaoAtividadeComando,
					Collection<CobrancaDocumento> colecaoCobrancaDocumento) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0026] – Verificar Débito em Cobrança Administrativa - Retirar Contas
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoContasValoresHelper
	 * @throws ControladorException
	 */
	public void removerContaCobrancaAdministrativaDebitoImovel(Usuario usuario, Integer idImovel,
					Collection<ContaValoresHelper> colecaoContasValoresHelper) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0026] – Verificar Débito em Cobrança Administrativa - Retirar Guias
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param colecaoContasValoresHelper
	 * @param colecaoGuiasPagamentoValoresHelper
	 * @return
	 * @throws ControladorException
	 */
	public void removerGuiaPagamentoCobrancaAdministrativaDebitoImovel(Usuario usuario, Integer idImovel,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelper) throws ControladorException;

	/**
	 * @author Hugo Lima
	 * @date 02/07/2012
	 * @param idEmpresa
	 * @return
	 * @throws ControladorException
	 */
	public boolean existeEmpresaCobrancaContrato(Integer idEmpresa) throws ControladorException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * Este caso de uso permite Consultar e/ou Retirar Imóveis da Cobrança Administrativa.
	 * 
	 * @created 31/07/2012
	 * @author Josenildo Neves.
	 */
	public Collection<ImovelCobrancaSituacaoAdministrativaHelper> consultarRetirarImovelCobrancaAdministrativa(Integer idLocalidade,
					Integer cdSetorComercial, Integer idImovel, Integer numeroPaginas) throws ControladorException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * Este caso de uso permite Consultar e/ou Retirar Imóveis da Cobrança Administrativa.
	 * 
	 * @created 31/07/2012
	 * @author Josenildo Neves.
	 */
	public Integer consultarRetirarImovelCobrancaAdministrativaCount(Integer idLocalidade, Integer cdSetorComercial, Integer idImovel)
					throws ControladorException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * [SB0001] – Exibir dados da Cobrança Administrativa do Imóvel.
	 * 
	 * @created 31/07/2012
	 * @author Josenildo Neves.
	 * @return ImovelCobrancaSituacaoAdministrativaHelper
	 */
	public ImovelCobrancaSituacaoAdministrativaHelper consultarRetirarImovelCobrancaAdministrativaDadosRemuneracao(
					Integer idImovelCobrancaSituacao) throws ControladorException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * [SB0003] – Encerrar Cobrança Administrativa do Imóvel.
	 * 
	 * @created 31/07/2012
	 */
	public void encerrarCobrancaAdministrativaImovel(List<Integer> idsImovelCobrancaSituacao, Integer motivoRetirada, Usuario usuario)
					throws ControladorException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * [SB0003] – Encerrar Cobrança Administrativa do Imóvel. Item 1.
	 * 
	 * @return
	 */
	public Collection<ImovelCobrancaMotivoRetirada> consultarImovelCobrancaMotivoRetirada() throws ControladorException;

	/**
	 * [UC0349] [SB0003] – Gerar Arquivo TXT Aviso de Corte
	 * 
	 * @author Carlos Chrystian
	 * @date 20/12/2011
	 * @param sb
	 * @param nomeArquivo
	 * @param usuario
	 * @throws ControladorException
	 */
	public void enviarArquivoTxtCobrancaDocumentoAvisoCorte(StringBuilder sb, String nomeArquivo, Usuario usuario)
					throws ControladorException;

	/**
	 * Retorna a lista de ids de contas em cobrança administrativa de um imóvel onde não existe
	 * ocorrência da empresa passada como parametro
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param idEmpresa
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> obterIdsContasCobrancaAdministrativaEmpresaDiferente(Integer idEmpresa, Integer idImovel)
					throws ControladorException;

	/**
	 * Obter os Ids de parcelamento feitos por usuario não pertencentes a empresa passada no
	 * parametro
	 * 
	 * @author Hugo Lima
	 * @data 15/08/2012
	 * @param idEmpresa
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> obterIdsParcelamentoEmpresaDiferente(Integer idEmpresa, Integer idImovel) throws ControladorException;

	/**
	 * [UC0252] Consultar Parcelamentos de Débitos
	 * [SB0003] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
	 * da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 23/08/2012
	 */
	public Collection<DebitoACobrar> pesquisarItensDebitosACobrarPorParcelamento(Integer idParcelamento) throws ControladorException;

	/**
	 * [UC3068] Gerar Aviso Corte Faturamento
	 * 
	 * @author Hebert Falcão
	 * @date 26/08/2012
	 */
	public void gerarAvisoCorteFaturamento(Collection<FaturamentoAtivCronRota> colecaoFaturamentoAtivCronRota,
					Integer anoMesFaturamentoGrupo, FaturamentoGrupo faturamentoGrupo, Integer idFuncionalidadeIniciada,
					FaturamentoAtividade faturamentoAtividade, FaturamentoGrupoCronogramaMensal faturamentoGrupoCronogramaMensal,
					Integer idProcessoIniciado) throws ControladorException;

	/**
	 * [UC0617] Consultar Resumo das Ações de Cobrança Eventuais
	 * 4.2.1.5.6. Para cada percentual de remuneração (o percentual de remuneração
	 * 
	 * @author Josenildo Neves
	 * @date 31/08/2012
	 */
	public List<ResumoCobrancaAcaoRemuneracaoHelper> consultarCobrancaAcaoRemuneracao(Integer idCobrancaAcao,
					Integer idCobrancaAcaoSituacao,
					InformarDadosGeracaoResumoAcaoConsultaEventualHelper informarDadosGeracaoResumoAcaoConsultaEventualHelper)
					throws ControladorException;

	/**
	 * Cosulta o total de remunerações de cobrança administrativa que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * @return
	 */
	public int consultarQuantidadeRemuneracaoCobrancaAdministrativa(
					RelatorioRemuneracaoCobrancaAdministrativaHelper remuneracaoCobrancaAdministrativaHelper) throws ControladorException,
					NegocioException;

	/**
	 * Valida o {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * 
	 * @param helper
	 * @throws NegocioException
	 */
	public void validarCamposObrigatoriosHelperRemuneracaoCobrancaAdministrativa(RelatorioRemuneracaoCobrancaAdministrativaHelper helper)
					throws ControladorException, NegocioException;

	/**
	 * Consulta os dados de remuneração de cobrança administrativa filtrados pelo
	 * {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * 
	 * @param helper
	 * @return {@link List} de {@link RelatorioRemuneracaoCobrancaAdministrativaBean}
	 */
	public List<RelatorioRemuneracaoCobrancaAdministrativaBean> consultarDadosRemuneracaoCobrancaAdministrativa(
					RelatorioRemuneracaoCobrancaAdministrativaHelper helper) throws ControladorException, NegocioException;

	/**
	 * [UC0630] [SB0003] Método responsável verificar e remover os debitos que estão em cobrança
	 * 
	 * @param usuario
	 * @param idImovel
	 * @param helper
	 * @throws ControladorException
	 */
	void removerDebitosCobAdministrativaEmpresaDiversas(Usuario usuario, Integer idImovel, ObterDebitoImovelOuClienteHelper helper)
					throws ControladorException;

	/**
	 * [UC3070] Filtrar Imóvel Cobrança Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 07/09/2012
	 */
	public Collection<Empresa> pesquisarEmpresaCobrancaAdministrativa(Collection<Integer> idsEmpresa) throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Imóvel Cobrança Situação pelo Id
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public ImovelCobrancaSituacao pesquisarImovelCobrancaSituacaoPeloId(Integer id) throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0005] Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * Pesquisar Última Cobrança Administrativa do Imóvel
	 * 
	 * @author Saulo Lima
	 * @date 25/07/2013
	 */
	public CobrancaAcaoAtividadeComando pesquisarUltimaCobrancaAdministrativaImovel(Integer imovelId) throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Totalizar Cobrança Documento Ítem filtrando pelo Id do Comando
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> totalizarCobrancaDocumentoItemPeloComando(Integer idCobrancaAcaoAtividadeComando, Integer idImovel)
					throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Contas pelo Id do Comando
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<CobrancaAdministrativaContaHelper> pesquisarContasPeloComandoParaCobrancaAdministrativa(
					Integer idCobrancaAcaoAtividadeComando, Integer idImovel) throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Guias pelo Id do Comando
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<CobrancaAdministrativaGuiaHelper> pesquisarGuiasPeloComandoParaCobrancaAdministrativa(
					Integer idCobrancaAcaoAtividadeComando, Integer idImovel) throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Totalizar Imovel Cobrança Administrativa pelo Id da Situação de Cobrança do Imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> totalizarImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao)
					throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * <<Inclui>> [UC3070 - Filtrar Imóvel Cobrança Administrativa]
	 * Pesquisar Imóvel em Cobrança Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 16/09/2012
	 */
	public Collection<ImovelCobrancaSituacao> pesquisarImovelCobrancaAdministrativa(FiltroImovelCobrancaAdministrativaHelper filtro,
					int pageOffset) throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Contas Cobrança Administrativa pelo Id da Situação de Cobrança do Imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Object[] pesquisarContasImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao) throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Guias Cobrança Administrativa pelo Id da Situação de Cobrança do Imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Object[] pesquisarGuiasImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao) throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * <<Inclui>> [UC3070 - Filtrar Imóvel Cobrança Administrativa]
	 * Pesquisar Imóvel em Cobrança Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 16/09/2012
	 */
	public Integer pesquisarQuantidadeImovelCobrancaAdministrativa(FiltroImovelCobrancaAdministrativaHelper filtro)
					throws ControladorException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0002] - Consultar Dados do Contrato da Empresa
	 * 
	 * @author Anderson Italo
	 * @date 17/09/2012
	 */
	public Collection<CobrancaContrato> pesquisarCobrancaContratoPorEmpresa(Integer idEmpresa) throws ControladorException;

	/**
	 * [UC0216] Calcular Acrescimo por Impontualidade
	 * Calcula os acrescimmos por Impontualidade(multa,juros de mora e atualização monetaria)
	 * 
	 * @author Hebert Falcão
	 * @date 14/09/2012
	 * @param indicadorEmissaoDocumento
	 *            Na emissão do documento o indicador que estiver como 1 no
	 *            ParametroAcrescimosEmissaoDocumento deve ser considerado, o que tiver como 2 deve
	 *            ter o valor zerado, pois não será cobrado nesse momento. Caso não seja emissão, a
	 *            lógica é invertida.
	 */
	public CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidadeBancoDeDados(int anoMesReferenciaDebito,
					Date dataVencimento, Date dataPagamento, BigDecimal valorDebito, BigDecimal valorMultasCobradas, short indicadorMulta,
					String anoMesArrecadacao, Integer idConta, Date dataEmissaoDocumento, Short indicadorEmissaoDocumento, Short multa,
					Short jurosMora, Short atualizacaoTarifaria)
					throws ControladorException;

	/**
	 * Pesquisar rotas dos imóveis gerados no comando precedente
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2012
	 */
	public Collection<Rota> pesquisarRotasDoComandoPrecedente(Integer idComandoCobrancaPrecedente) throws ControladorException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * Verificar se o imóvel já está em cobrança bancária
	 * 
	 * @author Hebert Falcão
	 * @date 13/10/2012
	 */
	public boolean isImovelEmCobrancaBancaria(Integer idImovel) throws ControladorException;

	/**
	 * @param indicadorDebito
	 * @param idImovel
	 * @param anoMesInicialReferenciaDebito
	 * @param anoMesFinalReferenciaDebito
	 * @param anoMesInicialVencimentoDebito
	 * @param anoMesFinalVencimentoDebito
	 * @return
	 * @throws ControladorException
	 */
	public ObterDebitoImovelOuClienteHelper obterDebitoImovelContasAgenciaVirtual(int indicadorDebito, String idImovel,
					String anoMesInicialReferenciaDebito, String anoMesFinalReferenciaDebito, Date anoMesInicialVencimentoDebito,
					Date anoMesFinalVencimentoDebito) throws ControladorException;

	/**
	 * Pesquisar Resolução de Diretoria Parâmetros Pagamento À Vista
	 * 
	 * @author Hebert Falcão
	 * @date 31/10/2012
	 */
	public ResolucaoDiretoriaParametrosPagamentoAVista pesquisarResolucaoDiretoriaParametrosPagamentoAVista(Integer idResolucaoDiretoria,
					Date dataPagamento) throws ControladorException;

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * Pesquisar mensagem para pagamento à vista
	 * 
	 * @author Hebert Falcão
	 * @date 31/10/2012
	 */
	public ResolucaoDiretoriaParametrosPagamentoAVista pesquisarMensagemExtratoParcelamentoPagamentoAVista(Integer idResolucaoDiretoria,
					Date dataEmissao) throws ControladorException;

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * Pesquisar Contas do Documento de Cobrança
	 * 
	 * @author Hebert Falcão
	 * @date 01/11/2012
	 */
	public Collection<Conta> pesquisarContasCobrancaDocumento(Integer idCobrancaDocumento) throws ControladorException;

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * Pesquisar Guias Pagamento Prestação do Documento de Cobrança
	 * 
	 * @author Hebert Falcão
	 * @date 01/11/2012
	 */
	public Collection<GuiaPagamentoPrestacao> pesquisarGuiasPagamentoPrestacaoCobrancaDocumento(Integer idCobrancaDocumento)
					throws ControladorException;

	/**
	 * Pesquisar Resolução de Diretoria Parâmetros Valor da Entrada
	 * 
	 * @author Hebert Falcão
	 * @date 31/10/2012
	 */
	public ResolucaoDiretoriaParametrosValorEntrada pesquisarResolucaoDiretoriaParametrosValorEntrada(Integer idResolucaoDiretoria,
					Date dataNegociacao) throws ControladorException;

	/**
	 * Guias de pagamento de parcelamento de cobrança bancária
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * [FS0004] - Verificar existência de guia de parcelamento de cobrança bancária
	 * 
	 * @author Josenildo Neves
	 * @date 22/11/2012
	 */
	public boolean verificarGuiaPagamentoParcelamentoCobrancaBancaria(Integer idGuiaPagamento, Short numeroPrestacoes)
					throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Retornar ids de parcelamentos ativos na referência
	 * 
	 * @author Hebert Falcão
	 * @date 03/12/2012
	 */
	public Collection<Integer> retornarIdsDeParcelamentosAtivosNaReferencia(Integer idImovel, Integer anoMesFaturamentoAtual)
					throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [FS0040] Verificar existência de itens de parcelamento anterior na mesma referência
	 * 
	 * @author Hebert Falcão
	 * @date 03/12/2012
	 */
	public void verificarExistenciaDeItensDeParcelamentoAnteriorNaMesmaReferencia(Integer idImovel,
					Collection<ContaValoresHelper> colecaoContaValoresHelper,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelper,
					Collection<DebitoACobrar> colecaoDebitoACobrar, Collection<CreditoARealizar> colecaoCreditoARealizar)
					throws ControladorException;

	/**
	 * Consulta a quantidade de registros de para o relatório de Liquidos Recebíveis.
	 * UC3081-GerarRelatoriodeLiquidosRecebiveis
	 * 
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 */
	public int consultarQuantidadeRegistrosDeLiquidosRecebiveis(Date dataPagamentoInicial, Date dataPagamentoFinal)
					throws ControladorException;

	/**
	 * Consulta os registros de para o relatório de Liquidos Recebíveis analitico.
	 * UC3081-GerarRelatoriodeLiquidosRecebiveis
	 * 
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 */
	public List<RelatorioLiquidosRecebiveisHelper> consultarRegistrosDeLiquidosRecebiveisAnalitico(Date dataPagamentoInicial,
					Date dataPagamentoFinal) throws ControladorException;

	/**
	 * Consulta os registros de para o relatório de Liquidos Recebíveis Sintetico.
	 * UC3081-GerarRelatoriodeLiquidosRecebiveis
	 * 
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 */
	public List<RelatorioLiquidosRecebiveisHelper> consultarRegistrosDeLiquidosRecebiveisSintetico(Date dataPagamentoInicial,
					Date dataPagamentoFinal) throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0009] Verificar Contas em Cobrança Bancária
	 * Obter o mais recente boleto bancário da conta em cobrança bancária
	 * 
	 * @author Hebert Falcão
	 * @date 24/11/2012
	 */
	public BoletoBancario obterMaisRecenteBoletoBancarioDaContaEmCobrancaBancaria(Integer idConta) throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0009] Verificar Contas em Cobrança Bancária
	 * Verificar se a situação do boleto não permite a emissão do extrato
	 * 
	 * @author Hebert Falcão
	 * @date 24/11/2012
	 */
	public boolean verificarSituacaoBoletoNaoPermiteEmissaoExtrato(Integer idBoletoBancario) throws ControladorException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0009] Verificar Contas em Cobrança Bancária
	 * Verificar se a situação do boleto não permite a emissão do extrato
	 * 
	 * @author Hebert Falcão
	 * @date 24/11/2012
	 */
	public Collection<BoletoBancario> pesquisarBoletoBaixadoEProtestadoPelaGuiaDePagamento(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ControladorException;

	/**
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * [SB0003] – Processar Pagamento de Documento de Cobrança
	 * Pesquisar Boletos Baixados e Protestados filtrando pelo Id
	 * 
	 * @author Hebert Falcão
	 * @date 26/11/2012
	 */
	public Collection<BoletoBancario> pesquisarBoletoBaixadoEProtestadoPeloId(Collection<Integer> idsBoletoBancario)
					throws ControladorException;

	/**
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * [SB0003] – Processar Pagamento de Documento de Cobrança
	 * Obter os boletos bancários das contas em cobrança bancária
	 * 
	 * @author Hebert Falcão
	 * @date 26/11/2012
	 */
	public Collection<Integer> obterBoletosBancariosDasContasEmCobrancaBancaria(Collection<Integer> idsConta, Integer idCobrancaDocumento,
					Date dataEmissaoDocumento) throws ControladorException;

	/**
	 * [UC3023] Manter Boleto Bancário
	 * [SB000B] Apresentar Boletos Não Agrupados
	 * Verifica boleto agregador de contas com situação correspondente a baixado e protestado
	 * 
	 * @author Hebert Falcão
	 * @date 04/12/2012
	 */
	public boolean verificaExistenciaBoletoAgregadorComSituacaoBaixadoEProtestado(Integer idBoletoBancario) throws ControladorException;

	/**
	 * Método responsável por obter um cobrança documento gerado pela acao de cobranca
	 * 
	 * @param cobrancaDocumento
	 * @return
	 * @throws ControladorException
	 */
	CobrancaDocumento obterCobrancaDocumentoGeradoAcaoCobranca(CobrancaDocumento cobrancaDocumento) throws ControladorException;

	/**
	 * Método responsável por obter o periodo do débito de um parcelamento
	 * 
	 * @param idParcelamento
	 * @return
	 * @throws ControladorException
	 */
	Map<String, Integer> obterPeriodoDebitoParcelmento(Integer idParcelamento) throws ControladorException;

	/**
	 * retorna uma coleção com todos os parcelamentos de um determinado imovel
	 * 
	 * @author Ítalo Almeida
	 * @date 12/12/2012
	 */
	public Collection<Parcelamento> consultarImovelParcelamentoDebito(int imovelId) throws ControladorException;

	/**
	 * [UC3082] Atualizar Item Documento Cobrança
	 * 
	 * @author Hebert Falcão
	 * @date 08/12/2012
	 */
	public void atualizarItemDocumentoCobranca(Integer idConta, Integer idGuiaPagamento, Short numeroPrestacao, Integer idDebitoACobrar,
					Integer idSituacaoDeDebitoDoItem, Date dataDaSituacaoDeDebitoDoItem, Integer idSituacaoDeDebitoAtual)
					throws ControladorException;

	/**
	 * [UC3082] Atualizar Item Documento Cobrança
	 * Obter cobrança documento com data imediatamente menor ou igual a data passada
	 * 
	 * @author Hebert Falcão
	 * @date 09/12/2012
	 */
	public CobrancaDocumento obterCobrancaDocumentoComDataImediatamenteMenorOuIgual(Integer idCobrancaAcao, Integer idImovel,
					Date dataCalculada) throws ControladorException;

	/**
	 * [UC3082] Atualizar Item Documento Cobrança
	 * Verifica se a ação sucessora foi gerada
	 * 
	 * @author Hebert Falcão
	 * @date 09/12/2012
	 */
	public boolean verificaSeAcaoSucessoraFoiGerada(Integer idCobrancaAcao, Integer idImovel, Date dataCalculada)
					throws ControladorException;

	/**
	 * [UC3089] Atualizar Situação Débito e da Ação dos Avisos Corte e Corte Individual
	 * 
	 * @author Hebert Falcão
	 * @date 14/12/2012
	 */
	public void atualizarSituacaoDebitoEAcaoDosAvisosCorteECorteIndividual(int idFuncionalidadeIniciada, Integer idProcessoIniciado)
					throws ControladorException;

	/**
	 * [UC3089] Atualizar Situação Débito e da Ação dos Avisos Corte e Corte Individual
	 * Verifica a existência de aviso de corte, sem comando, fora do prazo de validade, com situação
	 * da ação "entregue" e com situação do débito "pendente"
	 * 
	 * @date 28/12/2012
	 * @author Hebert Falcão
	 */
	public Collection<CobrancaDocumento> obterAvisoDeCorteSemComandoForaDoPrazoDeValidadeEntegueEPendente() throws ControladorException;

	/**
	 * [UC3089] Atualizar Situação Débito e da Ação dos Avisos Corte e Corte Individual
	 * Verifica a existência de corte, sem comando, fora do prazo de validade, com situação da ação
	 * "pendente" e com situação do débito "pendente"
	 * 
	 * @date 28/12/2012
	 * @author Hebert Falcão
	 */
	public Collection<CobrancaDocumento> obterCorteSemComandoForaDoPrazoDeValidadePendente() throws ControladorException;

	/**
	 * @author Andre Nishimura
	 * @date 14 de Março de 2011
	 * @param cobrancaAcaoAtividadeComando
	 * @param cobrancaAcaoAtividadeCronograma
	 */
	public void atualizarTotalizadoresCobrancaAcaoAtividade(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma) throws ControladorException;

	/**
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 28/12/2012
	 */
	public Map<Categoria, BigDecimal> calcularReajustaConta(String localidade, String unidadeNegocio, String gerenciaRegional,
					boolean isFinanciamento) throws ControladorException;

	/**
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 03/01/2012
	 */
	public Collection<Object[]> obterSomatorioAguaEsgoto(String idLocalidade, String idUnidadeNegocio, String idGerenciaRegional)
					throws ControladorException;

	/**
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 03/01/2012
	 */
	public Collection<Object[]> obterSomatorioValorDebitosCobradosFinanciamentoOuParcelamento(String idLocalidade, String idUnidadeNegocio,
					String idGerenciaRegional, boolean opcaoConsultaFinanciamento) throws ControladorException;

	/**
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 03/01/2012
	 */
	public Collection<Object[]> obterSomatorioValorDebitosACobrarFinanciamentoOuParcelamento(String idLocalidade, String idUnidadeNegocio,
					String idGerenciaRegional, boolean isFinanciado) throws ControladorException;


	/**
	 * @param colecaoCreditoARealizar
	 * @return
	 */
	public Collection verificarCreditoARealiazarComConcessaoDesconto(Collection colecaoCreditoARealizar) throws ControladorException;

	/**
	 * @param parcelamento
	 * @return
	 * @throws ControladorException
	 */
	public boolean podeDesfazer(Parcelamento parcelamento) throws ControladorException;

	/**
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * Método principal chamado pela Rotina Batch. Responsável por marcar, de acordo com algumas
	 * regras, os itens remuneráveis por cobrança administrativa, atualizando para 1 (Sim) um
	 * indicador criado em cada um dos itens (Conta, ContaHistorico, GuiaPagamento,
	 * GuiaPagamentoHistorico, DebitoACobrar, DebitoACobrarHistorico, DebitoCobrado e
	 * DebitoCobradoHistorico)
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 */
	public void marcarItensRemuneraveisCobrancaAdministrativa(int idFuncionalidadeIniciada) throws ControladorException;

	
	/**
	 * [UC0243] Inserir Comando de Ação de Cobrança
	 */
	public Collection<Empresa> consultarEmpresaCobrancaAdministrativa() throws ControladorException;

	/**
	 * UC3099-GerarCobrancaAdministrativa
	 * 
	 * @author Hiroshi Gonçalves
	 * @date 15/07/2013
	 * @return
	 * @throws ControladorException
	 */
	public void gerarCobrancaAdministrativa(Integer idCobrancaAcaoAtividadeComando) throws ControladorException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * [SB0001C] - Selecionar Itens Remuneráveis
	 * 
	 * @param idImovelCobrancaSituacao
	 * @return
	 */
	public List<ItensRemuradosHelper> selecionarItensRemureraveis(ImovelCobrancaSituacao imovelCobrancaSituacao)
					throws ControladorException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * [SB0010 – Obter Dados Conta].
	 * 
	 * @param listaItensRemuradosHelper
	 * @return
	 * @throws ControladorException
	 */
	public List<CobrancaAdministrativaHelper> obterDadosDaConta(List<ItensRemuradosHelper> listaItensRemuradosHelper)
					throws ControladorException;
	
	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * [SB0011] - Obter Dados Guia de Pagamento.
	 * 
	 * @param listaItensRemuradosHelper
	 * @return
	 * @throws ControladorException
	 */
	public List<CobrancaAdministrativaHelper> obterDadosDaGuiaPagamento(List<ItensRemuradosHelper> listaItensRemuradosHelper)
					throws ControladorException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * [SB0012] - Obter Dados Débito A Cobrar.
	 * 
	 * @param listaItensRemuradosHelper
	 * @return
	 * @throws ControladorException
	 */
	public List<CobrancaAdministrativaHelper> obterDadosDoDebitoACobrar(List<ItensRemuradosHelper> listaItensRemuradosHelper)
					throws ControladorException;
	
	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * [SB0001B] - Exibir Dados da Remuneração da Cobrança Administrativa do Imóvel.
	 * 1.4. Documentos Remunerados.
	 * 1.4.1.2.3. Caso o tipo de documento corresponda à “DEBITO A COBRAR”.
	 * 
	 * @param idImovelCobrancaSituacao
	 * @return
	 * @throws ControladorException
	 */
	public Object[] pesquisarDebitoACobrarImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao)
					throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [UC0252] Consultar Parcelamentos de Débitos
	 * Método responsável por identificar Característica do Parcelamento
	 * 1: Parcelamento de Cobrança Bancária
	 * 2: Parcelamento de Cobrança Administrativa
	 * 3: Parcelamento Normal
	 * 
	 * @author Saulo Lima
	 * @since 22/08/2013
	 * @param parcelamento
	 * @return int
	 * @throws ControladorException
	 */
	public int obterCaracteristicaParcelamento(Parcelamento parcelamento) throws ControladorException;

	/**
	 * Cancelar Aviso de Corte Pendente
	 * 
	 * @author Hebert Falcão
	 * @date 11/09/2013
	 */
	public void cancelarAvisoDeCortePendente(Integer idFuncionalidadeIniciada, FaturamentoGrupo faturamentoGrupo)
					throws ControladorException;


	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [FS0044] Retirar Débitos Não Vencidos
	 * 
	 * @author Saulo Lima
	 * @date 09/09/2013
	 * @param colecaoContasImovel
	 * @return Collection<ContaValoresHelper>
	 * @throws ControladorException
	 */
	public Collection<ContaValoresHelper> retirarContasNaoVencidas(Collection<ContaValoresHelper> colecaoContasImovel)
					throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Retirar da listagem Contas com Motivo de Revisão com Impedimento de Parcelamento
	 * 
	 * @author Saulo Lima
	 * @date 09/09/2013
	 * @param colecaoContasImovel
	 * @param usuario
	 * @return Collection<ContaValoresHelper>
	 * @throws ControladorException
	 */
	public Collection<ContaValoresHelper> retirarContasMotivoRevisaoComImpedimentoParcelamento(
					Collection<ContaValoresHelper> colecaoContasImovel, Usuario usuario) throws ControladorException;



	/**
	 * [UC0203][SB0010]
	 * 
	 * @author Felipe Rosacruz
	 * @param idImovel
	 * @return
	 * @date 26/09/2013
	 **/
	public String obterMsgSituacaoImovelCampanhaPremiacao(Integer idImovel) throws ControladorException;

	/**
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * Verifica se existem ocorrencias de um imovel em situação de cobranca
	 * 
	 * @author Anderson Italo
	 * @date 28/11/2013
	 * @throws ErroRepositorioException
	 */
	public boolean existeProcessoCobrancaImovelPorSituacaoInformada(Integer idImovel, String idsSituacaoesCobranca)
					throws ControladorException;

	/**
	 * [UC0216] Calcular Acrescimo por Impontualidade
	 * Calcula os acrescimmos por Impontualidade(multa,juros de mora e atualização monetaria)
	 * 
	 * @author Hebert Falcão
	 * @date 14/09/2012
	 * @param indicadorEmissaoDocumento
	 *            Na emissão do documento o indicador que estiver como 1 no
	 *            ParametroAcrescimosEmissaoDocumento deve ser considerado, o que tiver como 2 deve
	 *            ter o valor zerado, pois não será cobrado nesse momento. Caso não seja emissão, a
	 *            lógica é invertida.
	 */
	public CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidadeBancoDeDados(int anoMesReferenciaDebito,
					Date dataVencimento, Date dataPagamento, BigDecimal valorDebito, BigDecimal valorMultasCobradas, short indicadorMulta,
					String anoMesArrecadacao, Integer idConta, Date dataEmissaoDocumento, Short indicadorEmissaoDocumento, Short multa,
					Short jurosMora, Short atualizacaoTarifaria, Date dataBaseDeCalculo) throws ControladorException;

	/**
	 * [UC3044] Informar Entrega/Devolução de Documentos de Cobrança
	 * [FS0002] - Verificar existência do documento de cobrança para entrega/devolução
	 * 
	 * @author Eduardo Oliveira
	 * @data 04/02/2014
	 */
	public Integer pesquisarIdDocumentoCobrancaEntregaDevolucao(Integer idImovel, Integer idDocumentoTipo) throws ControladorException;

	/**
	 * [UC0259] - Processar Pagamento com Código de Barras
	 * [SB0023 - Tratar Desconto Extrato Parcelamento].
	 * 
	 * @param anoMesReferenciaConta
	 * @param dataVencimentoConta
	 * @param idResolucaoDiretoria
	 * @param dataPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ResolucaoDiretoriaParametrosPagamentoAVista obterResolucaoDiretoriaParametrosPagamentoAVista(Integer anoMesReferencia,
					Date dataVencimento, Integer idResolucaoDiretoria, Date dataPagamento) throws ControladorException;

	/**
	 * @param idImovel
	 * @param usuario
	 * @return
	 * @throws ControladorException
	 */
	public RelatorioExtratoDebito gerarRelatorioRelatorioExtratoDebitoParaEmissaoPorImovel(Integer idImovel, Usuario usuario)
					throws ControladorException;
	/**
	 * [UC3137] Comandar Prescrição de Débito
	 * 
	 * @author Anderson Italo
	 * @date 25/02/2014
	 */
	public Object[] comandarPrescricaoDebitosComandoUsuarioOuAutomatico(ComandoDebitosPrescritosHelper comandoHelper, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC3137] Comandar Prescrição de Débito
	 * 
	 * @author Anderson Italo
	 * @date 26/02/2014
	 */
	public void comandarPrescricaoDebitosComandoUsuario(int idFuncionalidadeIniciada, Object[] dadosImoveisMarcacaoDebitoPrescrito)
					throws ControladorException;

	/**
	 * [UC3139] Comandar Prescrição Automática Débito
	 * 
	 * @author Anderson Italo
	 * @date 26/02/2014
	 */
	public void comandarPrescricaoAutomaticaDebitos(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [FS0049] - Verificar retirada de débitos prescritos do débito do parcelamento
	 * Caso esteja indicado para não considerar débitos prescritos retirar a conta ou guia prestção
	 * da lista
	 * de débitos
	 * 
	 * @author Anderson Italo
	 * @created 10/03/2014
	 */
	public void verificarRetirarContaOuGuiaPrescrita(Integer idImovel, Short indicadorConsiderarDebitoPrescrito,
					ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper) throws ControladorException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Collection<ImovelComDebitosPrescritosHelper> pesquisarImoveisComDebitoPrescrito(
					FiltroImoveisComDebitosPrescritosHelper filtroHelper, int pageOffset) throws ControladorException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Integer pesquisarQuantidadeImoveisComDebitosPrescritos(FiltroImoveisComDebitosPrescritosHelper filtroHelper)
					throws ControladorException;

	/**
	 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
	 * [SB0002] - Exibir Dados dos Itens de Débito Prescritos do Imóvel
	 * 
	 * @author Anderson Italo
	 * @date 04/04/2014
	 */
	public Collection<PrescricaoContaHelper> pesquisarContasPrescritas(Integer idImovel,
					FiltroImoveisComDebitosPrescritosHelper filtroHelper) throws ControladorException;

	/**
	 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
	 * [SB0002] - Exibir Dados dos Itens de Débito Prescritos do Imóvel
	 * 
	 * @author Anderson Italo
	 * @date 04/04/2014
	 */
	public Collection<PrescricaoGuiaPrestacaoHelper> pesquisarGuiasPrestacaoPrescritas(Integer idImovel,
					FiltroImoveisComDebitosPrescritosHelper filtroHelper) throws ControladorException;

	/**
	 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
	 * [SB0003] - Desmarcar Prescrição de Conta
	 * 
	 * @author Anderson Italo
	 * @date 08/04/2014
	 */
	public Integer desmarcarPrescricaoConta(Collection<Integer> colecaoIdsConta, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC3140] Acompanhar Imóveis com Débitos Prescritos
	 * [SB0005] - Desmarcar Prescrição de Prestação de Guia de Pagamento
	 * 
	 * @author Anderson Italo
	 * @date 08/04/2014
	 */
	public Integer desmarcarPrescricaoGuia(Collection<String> colecaoIdsGuiaPrestacao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * 
	 */
	public Collection pesquisarImovelEmCobrancaAdministrativaAjuste() throws ControladorException;
	
	
	/**
	 * 
	 */
	public void executarAjusteResumoAcaoCobranca() throws ControladorException;
}
