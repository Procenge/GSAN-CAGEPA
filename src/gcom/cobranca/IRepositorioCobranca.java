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

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.RegistroCodigo;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.debitoautomatico.DebitoAutomatico;
import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.arrecadacao.pagamento.GuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacao;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.EspecificacaoTipoValidacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaMotivoRetirada;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.bean.*;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.CampanhaPremio;
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorSucesso;
import gcom.cobranca.opcaoparcelamento.PreParcelamentoOpcao;
import gcom.cobranca.parcelamento.*;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gerencial.bean.InformarDadosGeracaoResumoAcaoConsultaEventualHelper;
import gcom.gui.cobranca.BoletoBancarioHelper;
import gcom.gui.cobranca.BoletoBancarioTotalizadorHelper;
import gcom.gui.cobranca.ResolucaoDiretoriaGrupoHelper;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.Rota;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroInstalacaoHistorico;
import gcom.relatorio.atendimentopublico.RelatorioEstatisticoRegistroAtendimentoHelper;
import gcom.relatorio.cobranca.RelatorioRemuneracaoCobrancaAdministrativaHelper;
import gcom.relatorio.cobranca.parcelamento.RelacaoParcelamentoRelatorioHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.*;

/**
 * Interface para o repositório de cobranca
 * 
 * @author Rafael Santos
 * @since 02/01/2006
 */
public interface IRepositorioCobranca {

	/**
	 * Faz parte de [UC0178] Religar Automaticamente Imóvel Cortado Author:
	 * Rafael Santos Data: 02/01/2006 Pesquisa os imoveis cortados há 60 dias ou
	 * mais a data do último dia do mês de faturamento
	 * 
	 * @return Colecção de Matriculas
	 * @exception ErroRepositorioException
	 *                Descrição da exceção
	 */
	public Collection pesquisarImoveisCortados(String situacaoEsgotoLigado, String situacaoAguaCortado, Date anoMesReferenciaFaturamento)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0178] Religar Automaticamente Imóvel Cortado Author:
	 * Rafael Santos Data: 02/01/2006 Caso o imovel possua hidrometro na ligação
	 * de água e o tipo do ultimo consumo faturado tenha sido real
	 * 
	 * @param id
	 *            Matricula do Imovel
	 * @param anoMesFaturamento
	 *            Ano Mes Faturamento
	 * @param consumoTipoReal
	 *            Tipo de Consumo Real
	 * @param ligacaoTipoLigacaoAgua
	 *            Tipo de Ligação Agua
	 * @return Consumo Historico do Imovel
	 * @throws ErroRepositorioException
	 *             Erro no Repositorio
	 */
	public String pesquisarImoveisHidrometroAguaConsumoFaturadoReal(String id, String anoMesFaturamento, String consumoTipoReal,
					String ligacaoTipoLigacaoAgua) throws ErroRepositorioException;

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
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void religarImovelCortado(String id, String situacaoAguaLigado, Date dataReligacaoAgua) throws ErroRepositorioException;

	/**
	 * [UC0067] Obter Débito do Imóvel ou Cliente Obtem os débitos de um imóvel
	 * Author: Rafael Santos Data: 03/01/2006
	 * 
	 * @param id
	 *            Matricula do Imovel
	 * @param contaSituacaoNormal
	 *            Situação Normal de Conta
	 * @param contaSituacaoRetificada
	 *            Situação Retificada de Conta
	 * @param contaSituacaoIncluida
	 *            Situação Incluída de Conta
	 * @param anoMesInicialReferenciaDebito
	 *            Ano Mes Inicial Referencia Debito
	 * @param anoMesFinalReferenciaDebito
	 *            Ano Mes Final Referencia Debito
	 * @param anoMesInicialVecimentoDebito
	 *            Ano Mes Inicial Vencimento Debito
	 * @param anoMesFinalVencimentoDebito
	 *            Ano Mes Inicial Vencimento Debito
	 * @return Coleção de Contas do Imovel
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImovel(String id, Integer contaSituacaoNormal, Integer contaSituacaoRetificada,
					Integer contaSituacaoIncluida, Integer contaSituacaoParcelada, String anoMesInicialReferenciaDebito,
					String anoMesFinalReferenciaDebito, Date anoMesInicialVecimentoDebito, Date anoMesFinalVencimentoDebito,
					Integer contaSituacaoPrescrita)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imóvel ou Cliente Obtem os débitos
	 * de um cliente Author: Rafael Santos Data: 05/01/2006
	 * 
	 * @param idsContas
	 *            Coleção de Ids das Contas
	 * @param contaSituacaoNormal
	 *            Situação Normal de Conta
	 * @param contaSituacaoRetificada
	 *            Situação Retificada de Conta
	 * @param contaSituacaoIncluida
	 *            Situação Incluída de Conta
	 * @param anoMesInicialReferenciaDebito
	 *            Ano Mes Inicial Referencia Debito
	 * @param anoMesFinalReferenciaDebito
	 *            Ano Mes Final Referencia Debito
	 * @param anoMesInicialVecimentoDebito
	 *            Ano Mes Inicial Vencimento Debito
	 * @param anoMesFinalVencimentoDebito
	 *            Ano Mes Inicial Vencimento Debito
	 * @return Coleção de Contas do Imovel
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasCliente(Collection idsContas, String contaSituacaoNormal, String contaSituacaoRetificada,
					String contaSituacaoIncluida, String contaSituacaoParcelada, String anoMesInicialReferenciaDebito,
					String anoMesFinalReferenciaDebito, Date anoMesInicialVecimentoDebito, Date anoMesFinalVencimentoDebito,
					String contaSituacaoPrescrita)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imóvel ou Cliente Obtem o Valor
	 * Total dos Pagamentos da Conta Author: Rafael Santos Data: 05/01/2006
	 * 
	 * @param idConta
	 *            Id Conta
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarValorTotalPagamentoMenorDataPagamento(Integer idConta) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imóvel ou Cliente Obtem o Valor
	 * Total dos Pagamentos da Guia de Pagamento
	 * 
	 * @author Rafael Santos, Rafael Santos
	 * @date 07/01/2006, 21/03/2006
	 * @author eduardo henrique
	 * @date 30/04/2009
	 *       Alteração no mapeamento do pagamento e pagamentoGeral para apontamento para
	 *       GuiaPagamentoGeral.
	 * @author Saulo Lima
	 * @date 29/06/2009
	 *       Adicionar parâmetro numeroPrestacao
	 * @param idGuiaPagamento
	 *            Id Guia Pagamento
	 * @param numeroPrestacao
	 *            Número da Prestação
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarValorTotalGuiaPagamentoMenorDataGuiaPagamento(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Author: Rafael
	 * Santos Data: 05/01/2006
	 * 
	 * @param idImovel
	 *            Matricula do Imovel
	 * @param situacaoNormal
	 *            situacao de debito credito
	 * @return Coleção de Debitos A Cobrar
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitosACobrarImovel(String idImovel, String situacaoNormal, Integer anoMesReferenciaDebitoInicial,
					Integer anoMesReferenciaDebitoFinal) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param situacaoNormal
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitosACobrarImovel(String idImovel, String situacaoNormal) throws ErroRepositorioException;

	public Collection pesquisarDebitosACobrarImovelSimplificado(String idImovel, String situacaoNormal,
					Integer anoMesReferenciaDebitoInicial, Integer anoMesReferenciaDebitoFinal) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Author: Rafael
	 * Santos Data: 05/01/2006 Pesquisa os ID dos imoveis dos cliente
	 * 
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @param relacaoTipo
	 *            Relação Tipo Cliente Imovel
	 * @return Coleção de Debitos A Cobrar do Cliente
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIDImoveisClienteImovel(String codigoCliente, Integer relacaoTipo) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Author: Rafael
	 * Santos Data: 07/01/2006 Pesquisa os ID dos clientes contas
	 * 
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @param relacaoTipo
	 *            Relação Tipo Cliente Imovel
	 * @return ID dos Imvoeis Cliente Conta
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIDImoveisClienteConta(String codigoCliente, Integer relacaoTipo) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Author: Rafael
	 * Santos Data: 06/01/2006 Colecção de Debitos a Cobrar de Cliente
	 * 
	 * @param colecaoIdImoveis
	 *            Coleção de ID dos Imoveis
	 * @param situacaoNormal
	 *            Situação Normal
	 * @return Coleção de Debitos A Cobrar do Cliente
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitosACobrarCliente(Collection idsImoveis, String situacaoNormal) throws ErroRepositorioException;

	public Collection pesquisarDebitosACobrarClienteSimplificado(Collection idsImoveis, String situacaoNormal)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Author: Rafael
	 * Santos Data: 05/01/2006 Colecção de Creditos a Realizar de Cliente
	 * 
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @param situacaoNormal
	 *            Situação Normal
	 * @return Coleção de Creditos A Realizar do Cliente
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditosARealizarCliente(Collection idsImoveis, String situacaoNormal) throws ErroRepositorioException;

	public Collection pesquisarCreditosARealizarClienteSimplificado(Collection idsImoveis, String situacaoNormal)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Author: Rafael
	 * Santos Data: 05/01/2006
	 * 
	 * @param idImovel
	 *            Matricula do Imovel
	 * @return Coleção de Creditos A Realizar
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditosARealizarImovel(String idImovel, String situacaoNormal) throws ErroRepositorioException;

	public Collection pesquisarCreditosARealizarImovelSimplificado(String idImovel, String situacaoNormal) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente
	 * 
	 * @author Rafael Santos, Rafael Santos
	 * @date 07/01/2006, 21/03/2006
	 *       Colecção de Guias de Pagamento do Cliente
	 * @author eduardo henrique
	 * @date 29/07/2008
	 *       Alterações na consulta devido à mudanças na GuiaPagamento (v0.03)
	 * @author Virgínia Melo
	 * @date 07/04/2009
	 *       Alterações devido à mudanças na GuiaPagamento.
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @param situacaoNormal
	 *            Situação Normal
	 * @param clienteRelacaoTipo
	 *            Relação Cliente Tipo
	 * @param dataVencimentoInicial
	 *            Data Vencimento Inicial
	 * @param dataVencimentoFinal
	 *            Data Vecimento Final
	 * @return Coleção de Prestações de Guias de Pagamento do Cliente
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiasPagamentoCliente(Integer codigoCliente, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer situacaoParcelada, Integer clienteRelacaoTipo, Date dataVencimentoInicial,
					Date dataVencimentoFinal, Integer situacaoPrescrita) throws ErroRepositorioException;

	public Collection pesquisarGuiasPagamentoClienteSimplificado(String codigoCliente, String situacaoNormal, Integer clienteRelacaoTipo,
					Date dataVencimentoInicial, Date dataVencimentoFinal) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0216] Calcular Acréscimo por Impontualidade Rafael Santos
	 * Data: 09/01/2006 Dados do Indices Acrescimo Impontualidade
	 * 
	 * @param anoMesReferenciaDebito
	 *            Ano Mês de Referencia de Débito
	 * @return O Indices Acrescimos por Impontualidade
	 * @throws ErroRepositorioException
	 */
	//

	public IndicesAcrescimosImpontualidade pesquisarIndiceAcrescimoImpontualidade(int anoMesReferenciaDebito)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0216] Calcular Acréscimo por Impontualidade Rafael Santos
	 * Data: 09/01/2006 Pesquisa os dados do Indices Acrescimo Impontualidade
	 * menor ao ano mes referencia
	 * 
	 * @param anoMesReferenciaDebito
	 *            Ano Mês de Referencia de Débito
	 * @return O Indices Acrescimos por Impontualidade
	 * @throws ErroRepositorioException
	 */
	public IndicesAcrescimosImpontualidade pesquisarMenorIndiceAcrescimoImpontualidade(int anoMesReferenciaDebito)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente
	 * 
	 * @author Rafael Santos
	 * @date 07/01/2006
	 * @author eduardo henrique
	 * @date 28/07/2008
	 *       Alterações na entidade GuiaPagamento para v0.03
	 * @author Saulo Lima
	 * @date 22/06/2009
	 * @param idImovel
	 *            Matricula do Imovel
	 * @param situacaoNormal
	 *            Situação Normal
	 * @param dataVencimentoInicial
	 *            Data Vencimento Inicial
	 * @param dataVencimentoFinal
	 *            Data Vecimento Final
	 * @return Coleção de Prestações das Guias de Pagamentos
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiasPagamentoImovel(String idImovel, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer situacaoParcelada, Date dataVencimentoInicial, Date dataVencimentoFinal,
					Integer anoMesInicialReferenciaDebito, Integer anoMesFinalReferenciaDebito, Integer situacaoPrescrita)
					throws ErroRepositorioException;

	public Collection pesquisarGuiasPagamentoImovelSimplificado(String idImovel, String situacaoNormal, Date dataVencimentoInicial,
					Date dataVencimentoFinal, Integer anoMesInicialReferenciaDebito, Integer anoMesFinalReferenciaDebito)
					throws ErroRepositorioException;

	/**
	 * [UC0200] Inserir Débito Automático [FS0004] Verificar Data de Opção
	 * posterior já informada
	 * 
	 * @author Roberta Costa
	 * @created 04/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public String verificarDataOpcao(String matriculaImovel, Date dataOpcao, String identificadorCliente, String codigoAgencia)
					throws ErroRepositorioException;

	/**
	 * [UC0201] Excluir Débito Automático [FS0004] Verificar Data de Opção
	 * posterior já informada
	 * 
	 * @author Roberta Costa
	 * @created 05/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public String verificarDataOpcaoExclusao(String matriculaImovel, Date dataOpcao, String identificadorCliente)
					throws ErroRepositorioException;

	/**
	 * [UC0200] Inserir Débito Automático Verificar se o Imóvel já é Débito
	 * Automático
	 * 
	 * @author Roberta Costa
	 * @created 05/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public String verificarDebitoAutomatico(String matriculaImovel) throws ErroRepositorioException;

	/**
	 * [UC0200] Inserir Débito Automático Atualiza a data da exclusão com a data
	 * corrente em Débio Automático
	 * 
	 * @author Roberta Costa
	 * @created 05/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarDataExclusao(String matriculaImovel) throws ErroRepositorioException;

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
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void inserirDebitoAutomatico(DebitoAutomatico debitoAutomatic) throws ErroRepositorioException;

	/**
	 * [UC0200] Inserir Débito Automático Atualiza o indicador de débio
	 * automático em Imóvel
	 * 
	 * @author Roberta Costa
	 * @created 05/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarIndicadorDebitoAutomatico(String matriculaImovel, Integer indicadorDebito) throws ErroRepositorioException;

	/**
	 * [UC0201] Remover Débito Automático Verificar se o Imóvel já é Débito
	 * Automático para o mesmo Banco e Agência
	 * 
	 * @author Roberta Costa
	 * @created 09/01/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public String verificarDebitoAutomaticoBancoAgencia(String codigoBanco, String codigoAgencia) throws ErroRepositorioException;

	/**
	 * [UC0246] Executar Atividade de Ação de Cobrança Pesquisa uma coleção de
	 * CobrancaAcaoAtividadeCronograma
	 * 
	 * @author Pedro Alexandre
	 * @created 01/02/2006
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> pesquisarCobrancaAcaoAtividadeCronograma() throws ErroRepositorioException;

	/**
	 * [UC0246] Executar Atividade de Ação de Cobrança Pesquisa uma coleção de
	 * CobrancaAcaoAtividadeComando
	 * 
	 * @author Pedro Alexandre
	 * @created 01/02/2006
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection<CobrancaAcaoAtividadeComando> pesquisarCobrancaAcaoAtividadeComando() throws ErroRepositorioException;

	/**
	 * Consultar Dados do Cliente Imovel Vinculado Auhtor: Rafael Santos Data:
	 * 23/01/2006
	 * 
	 * @param inscricaoImovel
	 *            Inscrição do Imovel
	 * @return Dados do Imovel Vinculado
	 * @throws ControladorException
	 */
	public Object[] consultarDadosClienteImovelUsuario(Imovel imovel) throws ErroRepositorioException;

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
	public Collection consultarConsumoHistoricoImoveisVinculados(ConsumoHistorico consumoHistorico) throws ErroRepositorioException;

	/**
	 * Consultar Consumo Tipo do Consumo Historico Auhtor: Rafael Santos Data:
	 * 23/01/2006
	 * 
	 * @param consumoHistorico
	 *            Consumo Historico
	 * @return Dados do Consumo Tipo
	 * @throws ControladorException
	 */
	public Object[] consultarDadosConsumoTipoConsumoHistorico(ConsumoHistorico consumoHistorico) throws ErroRepositorioException;

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
	public Object[] obterConsumoHistoricoMedicaoIndividualizada(Imovel imovel, LigacaoTipo ligacaoTipo, int anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito Atualiza o debitocreditosituacao em
	 * conta
	 * 
	 * @author Vitor
	 * @created 21/08/2008
	 * @param DebitoCreditoSituacaoAnterior
	 *            DebitoCreditoSituacaoAtual idConta
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarSituacaoConta(String codigoConta, int situacaoAtual, int anoMesReferenciaContabil) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito Atualiza o debitocreditosituacao em
	 * guia pagamento
	 * 
	 * @author Fernanda Paiva
	 * @created 15/02/2006
	 * @param DebitoCreditoSituacaoAtual
	 *            idGuiaPagamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarSituacaoGuiaPagamento(String codigoGuiaPagamento, int situacaoAtualGuia, int anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito Atualiza o parcelamento
	 * 
	 * @author Fernanda Paiva
	 * @created 15/02/2006
	 * @param idParcelamento
	 *            motivo parcelamentoSituacao
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarParcelamento(Integer codigoParcelamento, Integer parcelamentoSituacao, String motivo)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito Atualiza o debitocreditosituacao em
	 * debitoacobrar
	 * 
	 * @author Fernanda Paiva
	 * @created 16/02/2006
	 * @param DebitoCreditoSituacaoAtual
	 *            idDebitoACobrar
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarSituacaoDebitoACobrar(String codigoDebitoACobrar, int situacaoAtualDebito, int anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito Atualiza o debitocreditosituacao em
	 * debitoacobrar
	 * 
	 * @author Vitor Hora
	 * @created 02/09/2008
	 * @param DebitoCreditoSituacaoAtual
	 *            idDebitoACobrar
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarSituacaoDebitoACobrarHistorico(String codigoDebitoACobrar, int situacaoAtualDebito, int anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito Atualiza o debitocreditosituacao em
	 * creditoarealizar
	 * 
	 * @author Fernanda Paiva
	 * @created 16/02/2006
	 * @param DebitoCreditoSituacaoAtual
	 *            idCreditoARealizar
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void atualizarSituacaoCreditoARealizar(String codigoCreditoARealizar, int situacaoAtualCredito, int anoMesReferenciaContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove débitos a cobrar categoria
	 * referentes ao parcelamento
	 * 
	 * @author Fernanda Karla
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerDebitoACobrarCategoriaDoParcelamento(Integer idDebito) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove débitos a cobrar categoria
	 * referentes ao parcelamento historico
	 * 
	 * @author Vitor
	 * @created 21/08/2008
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerDebitoACobrarCategoriaDoParcelamentoHistorico(Integer idDebito) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove débitos a cobrar referentes
	 * ao parcelamento
	 * 
	 * @author Fernanda Karla
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerDebitoACobrarDoParcelamento(Integer codigoImovel, Integer codigoParcelamento) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Pesquisa débito a cobrar
	 * referentes ao parcelamento
	 * 
	 * @author Fernanda Karla
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection<DebitoACobrar> pesquisarDebitoACobrarDoParcelamento(Integer codigoImovel, Integer codigoParcelamento)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove creditos a realizar
	 * referentes ao parcelamento
	 * 
	 * @author Fernanda Karla
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerCreditoARealizarDoParcelamento(Integer codigoImovel, Integer codigoParcelamento) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove creditos a realizar
	 * referentes ao parcelamento historico
	 * 
	 * @author Vitor
	 * @param idParcelamento
	 * @created 21/08/2008
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void transferirCreditoARealizarHistoricoParaCreditoARealizar(Integer idCreditoARealizarHistorico, Integer idParcelamento)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove guia pagamento referente ao
	 * parcelamento
	 * 
	 * @author Fernanda Karla
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerGuiaPagamentoDoParcelamento(Integer codigoImovel, Integer codigoParcelamento) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove guia pagamento referente ao
	 * parcelamento historico
	 * 
	 * @author Vitor
	 * @created 21/08/2008
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerGuiaPagamentoDoParcelamentoHistorico(Integer codigoImovel, Integer codigoParcelamento)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove guia pagamento referentes
	 * ao parcelamento
	 * 
	 * @author Fernanda Karla
	 * @created 20/02/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerGuiaPagamentoCobrancaDoParcelamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove guia pagamento referentes
	 * ao parcelamento historico
	 * 
	 * @author Vitor
	 * @created 21/08/2008
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerGuiaPagamentoCobrancaDoParcelamentoHistorico(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com Código de Barras
	 * [SF0003] - Processar Pagamento de Documento de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @created 16/02/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarCobrancaDocumentoItem(Integer idImovel, int numeroSequencialDocumento) throws ErroRepositorioException;

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
	public void inserirCobrancaSituacaoHistorico(Collection collectionCobrancaSituacaoHistorico) throws ErroRepositorioException;

	/**
	 * Atualiza o ano mes de cobranca situação historico
	 * [UC0156] Informar Situacao Especial Faturamento
	 * 
	 * @author Sávio Luiz
	 * @date 17/03/2006
	 * @param situacaoEspecialFaturamentoHelper
	 * @throws ErroRepositorioException
	 */
	public void atualizarAnoMesCobrancaSituacaoHistorico(SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper,
					Integer anoMesReferenciaFaturamentoGrupo, Integer idUsuarioExclusao) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Author: Rafael
	 * Santos Data: 05/01/2006 Pesquisa os ID das Contas dos cliente
	 * 
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @param relacaoTipo
	 *            Relação Tipo Cliente Imovel
	 * @return Coleção de Debitos A Cobrar do Cliente
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIDContasClienteConta(String codigoCliente, Integer relacaoTipo) throws ErroRepositorioException;

	/**
	 * Atualiza o numero de parcelamento e reparcelamento na tabela de imovel
	 * Desfazer Parcelamento de Debitos
	 * 
	 * @author Fernanda Paiva
	 * @date 29/04/2006
	 * @param imovel
	 * @param numeroParcelamento
	 * @param numeroReparcelamento
	 * @param numeroReparcelamentoConsecutivo
	 * @throws ErroRepositorioException
	 */
	public void atualizarDadosParcelamentoImovel(Integer codigoImovel, Short numeroParcelamento, Short numeroReparcelamento,
					Short numeroReparcelamentoConsecutivo) throws ErroRepositorioException;

	/**
	 * [UC0314] - Desfazer Parcelamentos por Entrada Não Paga Author: Fernanda
	 * Paiva Data: 02/05/2006
	 * Obtem os parcelamentos de débitos efetuados no mês de faturamento
	 * corrente e que estejam com situação normal
	 * 
	 * @param situacaoNormal
	 *            ,
	 *            anoMesFaturamento
	 * @return
	 */
	public Collection pesquisarParcelamentosSituacaoNormalNoMes(String parcelamentoSituacao, Date dataParcelamentoInicio,
					Date dataParcelamentoFim) throws ErroRepositorioException;

	/**
	 * [UC0314] - Desfazer Parcelamentos por Entrada Não Paga Author: Fernanda
	 * Paiva Data: 02/05/2006
	 * Obtem as guias de pagamentos dos parcelamentos de débitos efetuados no
	 * mês de faturamento corrente e que estejam com situação normal
	 * 
	 * @param situacaoNormal
	 *            ,
	 *            anoMesFaturamento
	 * @return
	 */
	public Collection pesquisarGuiaPagamentoDoParcelamento(String parcelamento) throws ErroRepositorioException;

	/**
	 * [UC0314] - Desfazer Parcelamentos por Entrada Não Paga Author: Fernanda
	 * Paiva Data: 02/05/2006
	 * Obtem os pagamentos para a guia de pagamentos dos parcelamentos de
	 * débitos efetuados no mês de faturamento corrente e que estejam com
	 * situação normal
	 * 
	 * @param situacaoNormal
	 *            ,
	 *            anoMesFaturamento
	 * @return
	 */
	public Collection pesquisarPagamentoParaGuiaPagamentoDoParcelamento(String pagamento) throws ErroRepositorioException;

	/**
	 * [UC0317] Manter Critério de Cobrança
	 * Este caso de uso remove as linhas da cobrança critério passando a colecao
	 * de cobrança criterio
	 * [SB0002] Excluir Critério de Cobrança
	 * 
	 * @author Sávio luiz
	 * @created 11/05/2006
	 * @throws ControladorException
	 *             Controlador Exception
	 */
	public void removerCobrancaCriterioLinha(String[] idscobrancaCriterio) throws ErroRepositorioException;

	/**
	 * Remover contratos de cobrança de acordo com ids passados.
	 * 
	 * @author Virgínia Melo
	 * @date 04/12/2008
	 * @param idsContratoCobranca
	 * @throws ErroRepositorioException
	 */
	public void removerContratoCobranca(Integer[] idsContratoCobranca) throws ErroRepositorioException;

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
					String numeroMoradoresFinal, String idAreaConstruidaFaixa, int quantidadeImovelInicio, String indicadorOrdenacao,
					String idUnidadeNegocio) throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * O sistema ordena a lista de documentos de cobrança por empresa (EMPR_ID
	 * da tabela DOCUMENTO_COBRANCA), localidade (LOCA_ID), setor
	 * (CBDO_CDSETORCOMERCIAL), quadra (CBDO_NNQUADRA), lote e sublote
	 * (IMOV_NNLOTE e IMOV_SUBLOTE da tabela IMOVEL com IMOV_ID da tabela
	 * DOCUMENTO_COBRANCA)
	 * 
	 * @author Raphael Rossiter
	 * @data 26/05/2006
	 * @param idBairro
	 *            ,
	 *            idLogradouro
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoParaEmitir(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao, int quantidadeCobrancaDocumentoInicio)
					throws ErroRepositorioException;

	public Collection<Object[]> pesquisarTodosCobrancaDocumentoParaEmitir(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando,
					Date dataEmissao, Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Aviso de corte Aquivo TXT
	 * Seleciona os setores comerciais em documento de cobrança
	 * Ordenar pelo setor comercial
	 * 
	 * @author Carlos Chrsytian
	 * @data 23/12/2011
	 * @param CobrancaDocumento
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection pesquisarSetorComercialCobrancaDocumento(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando)
					throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Aviso de corte Aquivo TXT
	 * Seleciona os documento de cobrança
	 * Ordenar por quadra, lote e sublote
	 * 
	 * @author Carlos Chrsytian
	 * @data 23/12/2011
	 * @param CobrancaDocumento
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection pesquisarCobrancaDocumentoArquivoTXT(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando,
					Integer cdSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * O sistema ordena a lista de documentos de cobrança por empresa (EMPR_ID
	 * da tabela DOCUMENTO_COBRANCA), localidade (LOCA_ID), setor
	 * (CBDO_CDSETORCOMERCIAL), rota (ROTA_ID), sequencial da rota (IMOV_NNSEQUENCIALROTA)e
	 * cobranca documento (CBDO_ID)
	 * 
	 * @author Raphael Rossiter
	 * @data 27/06/2007
	 * @param idBairro
	 *            ,
	 *            idLogradouro
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoParaEmitirPorRota(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao, int quantidadeCobrancaDocumentoInicio)
					throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Seleciona os ítns do documento de cobrança correspondentes a conta e
	 * ordenar por ano/mês de referência da conta
	 * 
	 * @author Raphael Rossiter
	 * @data 26/05/2006
	 * @param CobrancaDocumento
	 * @return Collection<CobrancaDocumentoItem>
	 */
	public Collection<CobrancaDocumentoItem> selecionarCobrancaDocumentoItemReferenteConta(CobrancaDocumento cobrancaDocumento)
					throws ErroRepositorioException;

	/**
	 * Retorna o count do resultado da pesquisa de Cobranca Cronograma
	 * pesquisarCobrancaCronogramaCount
	 * 
	 * @author Flávio Cordeiro
	 * @date 14/06/2006
	 * @return Integer retorno
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarCobrancaCronogramaCount(Filtro filtro) throws ErroRepositorioException;

	public void removerCobrancaCronograma(Integer idGrupoCronogramaMes) throws ErroRepositorioException;

	/**
	 * Consultar Relação de Debitos do Imovel Consulta o Consumo Medio do Imovel
	 * [UC0227] - Gerar Relção de Débitos
	 * 
	 * @author Rafael Santos
	 * @date 15/06/2006
	 * @param imovelId
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarConsumoMedioConsumoHistoricoImovel(Integer imovelId) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Critério de Cobrança
	 * Pesquisa as linhas de critério de cobrança através do id do critério de
	 * cobrança
	 * 
	 * @author Rafael Corrêa
	 * @data 09/08/2006
	 */
	public Collection pesquisarCobrancaCriterioLinha(Integer idCriterioCobranca) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Perfil de Parcelamento
	 * Pesquisa os Parcelamentos Desconto Antiguidade através do id de Perfil de
	 * Parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @data 22/08/2006
	 */
	public Collection pesquisarParcelamentoDescontoAntiguidade(Integer idParcelamentoPerfil) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Perfil de Parcelamento
	 * Pesquisa os Parcelamentos Desconto Inatividade através do id de Perfil de
	 * Parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @data 22/08/2006
	 */
	public Collection pesquisarParcelamentoDescontoInatividade(Integer idParcelamentoPerfil) throws ErroRepositorioException;

	/**
	 * Gerar Relatório de Perfil de Parcelamento
	 * Pesquisa os Reparcelamentos Consecutivos através do id de Perfil de
	 * Parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @data 22/08/2006
	 */
	public Collection pesquisarReparcelamentoConsecutivo(Integer idParcelamentoPerfil) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> pesquisarCobrancaAcaoAtividadeCronogramaComandadosNaoRealizados()
					throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaAcaoAtividadeComando> pesquisarCobrancaAcaoAtividadeCronogramaEventuaisComandadosNaoRealizados()
					throws ErroRepositorioException;

	/**
	 * [UC0476] Emitir Documento de Cobrança - Ordem de Corte
	 * O sistema ordena a lista de documentos de cobrança por empresa (EMPR_ID
	 * da tabela DOCUMENTO_COBRANCA), localidade (LOCA_ID), setor
	 * (CBDO_CDSETORCOMERCIAL), quadra (CBDO_NNQUADRA), lote e sublote
	 * (IMOV_NNLOTE e IMOV_SUBLOTE da tabela IMOVEL com IMOV_ID da tabela
	 * DOCUMENTO_COBRANCA)
	 * 
	 * @author Ana Maria
	 * @data 07/09/2006
	 * @param Collection
	 *            <CobrancaDocumento>
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection<EmitirDocumentoCobrancaHelper> pesquisarCobrancaDocumentoOrdemCorte(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao, int quantidadeCobrancaDocumentoInicio)
					throws ErroRepositorioException;

	/**
	 * [UC0476] Emitir Documento de Cobrança
	 * O sistema ordena a lista de documentos de cobrança por empresa (EMPR_ID
	 * da tabela DOCUMENTO_COBRANCA), localidade (LOCA_ID), setor
	 * (CBDO_CDSETORCOMERCIAL), rota (ROTA_ID), sequencial da rota
	 * (IMOV_NNSEQUENCIALROTA)e cobranca documento (CBDO_ID)
	 * 
	 * @author Raphael Rossiter
	 * @data 07/08/2007
	 * @return Collection<EmitirDocumentoCobrancaHelper>
	 */
	public Collection<EmitirDocumentoCobrancaHelper> pesquisarCobrancaDocumentoOrdemCortePorRota(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao, int quantidadeCobrancaDocumentoInicio)
					throws ErroRepositorioException;

	/**
	 * [UC0582] Emitir Boletim de Cadastro
	 * O sistema ordena a lista de documentos de cobrança por empresa (EMPR_ID
	 * da tabela DOCUMENTO_COBRANCA), localidade (LOCA_ID), setor
	 * (CBDO_CDSETORCOMERCIAL), quadra (CBDO_NNQUADRA), lote e sublote
	 * (IMOV_NNLOTE e IMOV_SUBLOTE da tabela IMOVEL com IMOV_ID da tabela
	 * DOCUMENTO_COBRANCA)
	 * 
	 * @author Rafael Corrêa
	 * @data 16/05/2007
	 * @param Collection
	 *            <CobrancaDocumento>
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection<EmitirDocumentoCobrancaBoletimCadastroHelper> pesquisarCobrancaDocumentoBoletimCadastro(
					Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao,
					int quantidadeCobrancaDocumentoInicio) throws ErroRepositorioException;

	/**
	 * Este caso de consulta os dados do imovel, esse metodo consulta os
	 * documentos de cobrança do imovel
	 * [UC0472] - Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 18/09/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarImovelDocumentosCobranca(Integer idImovel, Integer numeroPagina) throws ErroRepositorioException;

	/**
	 * Este caso de consulta os dados do imovel, esse metodo consulta a
	 * quantidade de documentos de cobrança do imovel
	 * [UC0472] - Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 18/09/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer consultarQuantidadeImovelDocumentosCobranca(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Este caso de consulta os dados do imovel, esse metodo consulta a
	 * quantidade de documentos de itens de cobrança do imovel
	 * [UC0472] - Consultar Imovel
	 * 
	 * @author Rafael Santos
	 * @date 18/09/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer consultarQuantidadeImovelDocumentosItemCobranca(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados do parcelamento necessários para o relatório através do
	 * id do parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @date 25/09/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarParcelamentoRelatorio(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * Pesquisa os itens do parcelamento necessários para o relatório através do
	 * id do parcelamento
	 * 
	 * @author Rafael Corrêa
	 * @date 25/09/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarParcelamentoItemPorIdParcelamentoRelatorio(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * Obtem os percentuais de desconto por tempo de inatividade
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 2/10/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ParcelamentoDescontoInatividade obterPercentualDescontoInatividade(Integer idPerfilParc, int qtdeMeses)
					throws ErroRepositorioException;

	/**
	 * Consultar Serviços/Atualizações do documento de cobraça
	 * [UC0349] - Emitir Documento de cobrança - Ordem de Fiscalização
	 * 
	 * @author Ana Maria
	 * @date 18/10/2006
	 * @param idDocumentoCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarServioAtualizacao(Integer idDocumentoCobranca) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @created 23/10/2006
	 * @param conta
	 * @throws ErroRepositorioException
	 */
	public void associarContaParcelamento(Conta conta) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Retorna os CBCM_ID da tabela COBRANCA_GRUPO_CRONOGRAMA_MES
	 * 
	 * @author Rafael Santos
	 * @date 16/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaGrupoCronogramaMes() throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Retorna os CBCR_ID da tabela COBRANCA_ACAO_CRONOGRAMA com CBCM_ID da
	 * tabela COBRANCA_GRUPO_CRONOGRAMA_MES
	 * 
	 * @author Rafael Santos
	 * @date 16/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaAcaoCronograma(int idCobrancaGrupoCronogramaMes) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Retorna CAAC_TMREALIZACAO do COBRANCA_ATIVIDADE_ACAO_CRONOGRAMA
	 * 
	 * @author Rafael Santos
	 * @date 16/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDataRelizacaoCobrancaAtividadeAcaoConograma(int idCobrancaAcaoCronograma, int idCobrancaAtividade)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Retorna os DOTP_ID da tabela COBRANCA_ACAO com CBAC_ID de
	 * COBRANCA_ACAO_CRONOGRAMA
	 * 
	 * @author Rafael Santos
	 * @date 16/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaAcao(int idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0001] - Processar Documentos Cobrança
	 * Retorna os CBDO_ID da tabela COBRANCA_DOCUMENTO com CAAC_ID da tabela
	 * COBRANCA_ATIVIDADE_ACAO_CRONOGRAMA
	 * 
	 * @author Rafael Santos,Sávio Luiz
	 * @date 17/10/2006,28/05/2007
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection<DadosPesquisaCobrancaDocumentoHelper> pesquisarCobrancaDocumento(int idCobrancaAtividadeAcaoCronograma)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0002] - Determinar Situação da Ação de Cobrança
	 * Retorna os ORSE_ID da tabela ORDEM_SERVICO com CBDO_ID da tabela
	 * COBRANCA_DOCUMENTO
	 * 
	 * @author Rafael Santos,Sávio Luiz
	 * @date 17/10/2006,28/05/2007
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarOrdemServico(int idCobrancaAtividadeAcaoCronogramaEmitir) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0007] - Determinar Situação da Ordem de Serviço
	 * Retorna os AMEN_ICEXECUCAO da tabela ATENDIMENTO_MOTIVO_ENCERRAMENTO com
	 * AMEN_ID da tabela ORDEM_SERVIÇO
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAtendimentoMotivoEncerramento(int idAtendimentoMotivoEncerramento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os CNTA_ID,GPAG_ID,DBAC_ID da tabela COBRANCA_DOCUMENTO_ITEM com
	 * CBDO_ID da tabela COBRANCA_DOCUMENTO
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaDocumentoItem(int idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os CNTG_ID da tabela COBRANCA_GERAL com CNTA_ID da tabela
	 * COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaGeral(int idConta) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os DCST_IDATUAL,CNHI_DTCANCELAMENTO da tabela CONTA_HISTORICO com
	 * CNTA_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarContaHistorico(int idContaHistorico) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os DCST_IDATUAL,CNTA_DTCANCELAMENTO da tabela CONTA com CNTA_ID
	 * da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarConta(int idConta) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os PARC_TM_PARCELAMENTO da tabela PARCELAMENTO com PARCcom
	 * CNTA_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarParcelamentoConta(int idConta, int idParcelamentoSituacao) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os GPGE_ID da tabela GUIA_PAGAMENTO_GERAL com GPAG_ID da tabela
	 * COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiaPagamentoGeral(int idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os DCST_IDATUAL,GPGE_DTCANCELAMENTO da tabela
	 * GUIA_PAGAMENTO_HISTORICO com GPAG_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarGuiaPagamentoHistorico(int idGuiaPagamentoHistorico) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os DCST_IDATUAL,GPAG_DTCANCELAMENTO da tabela GUIA_PAGAMENTO com
	 * CNTA_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarGuiaPagamento(int idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os PARC_TM_PARCELAMENTO da tabela PARCELAMENTO com GPAG_ID da
	 * tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarParcelamentoGuiaPagamento(int idGuiaPagamento, int idParcelamentoSituacao) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os DAGE_ID da tabela DEBITO_A_COBRAR_GERAL com DBAC_ID da tabela
	 * COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitoACobrarGeral(int idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os DCST_IDATUAL,DAGE_DTCANCELAMENTO da tabela
	 * DEBITO_A_COBRAR_HISTORICO com DBAC_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDebitoACobrarHistorico(int idDebitoACobrarHistorico) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os DCST_IDATUAL,GPAG_DTCANCELAMENTO da tabela DEBITO_A_COBRAR com
	 * DBAC_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDebitoACobrar(int idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna os PARC_TM_PARCELAMENTO da tabela PARCELAMENTO com DBAC_ID da
	 * tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos,Sávio Luiz
	 * @date 17/10/2006,29/05/2007
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarParcelamentoDebitoACobrar(int idDebitoACobrar, int idParcelamentoSituacao) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna o Menor PGHI_DTPAGAMENTO da tabela PAGAMENTO_HISTORICO com
	 * CNTA_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarMenorDataPagamentosContaHistorico(int idContaHistorico) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna o Menor PGMT_DTPAGAMENTO da tabela PAGAMENTO com CNTA_ID da
	 * tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarMenorDataPagamentosConta(int idConta) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna o Menor PGHI_DTPAGAMENTO da tabela PAGAMENTO_HISTORICO com
	 * GPAG_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarMenorDataPagamentosGuiaPagamentoHistorico(int idGuiaPagamentoHistorico) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna o Menor PGMT_DTPAGAMENTO da tabela PAGAMENTO com GPAG_ID da
	 * tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarMenorDataPagamentosGuiaPagamento(int idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna o Menor PGHI_DTPAGAMENTO da tabela PAGAMENTO_HISTORICO com
	 * DBAC_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarMenorDataPagamentosDebitoACobrarHistorico(int idDebitoACobrarHistorico) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0003] - Determinar Situação do Débito do Item do Documento de Cobrança
	 * Retorna o Menor PGMT_DTPAGAMENTO da tabela PAGAMENTO_HISTORICO com
	 * DBAC_ID da tabela COBRANCA_DOCUMENTO_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarMenorDataPagamentosDebitoACobrar(int idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0005] - Determinar Situação Predominante do Débito do Documento de
	 * Cobrança
	 * Retorna o CBCT_PCVLMINIMOPGPARCCANC, CBCBT_PCQTMINIMOPGPARCCANC da tabela
	 * COBRANCA_CRITERIO com CBCT_ID da tabela COBRANCA_DOCUMENTO
	 * 
	 * @author Rafael Santos
	 * @date 18/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaCriterio(int idCobrancaCriterio) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0004] - Atualizar item do documento de cobrança
	 * Atualizar os COBRANCA_DOCUMENT_ITEM
	 * 
	 * @author Rafael Santos
	 * @date 19/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public void atualizarCobrancaDocumentoItem(Collection colecaoCobrancaDocumentoItem) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0004] - Processar Documento de Cobrança
	 * Atualizar os COBRANCA_DOCUMENTO
	 * 
	 * @author Rafael Santos
	 * @date 19/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public void atualizarCobrancaDocumento(Collection colecaoCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0001] - Processar Documentos de Cobrança
	 * Retorna os dados do Imovel
	 * 
	 * @author Rafael Santos
	 * @date 19/10/2006
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosImovel(int idImovel) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0001] - Processar Documento de Cobrança
	 * Atualizar os COBRANCA_ACAO_ATIVIDADE_CRONOGRAMA
	 * 
	 * @author Rafael Santos
	 * @date 25/10/2006
	 * @throws ErroRepositorioException
	 */
	public void atualizarCobrancaAcaoAtividadeCronograma(int idCobrancaAcaoAtividadeCrongrama) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0006] - Processar Ação com Ordens de Serviço
	 * Retorna os ORSE_ID da tabela ORDEM_SERVICO com SVTP_ID da tabela
	 * COBRANCA_ACAO e ORSE_TMGERACAO entre CAAC_DTPREVISTA do Emitir e do
	 * Encerrar
	 * 
	 * @author Rafael Santos
	 * @date 25/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicos(int idServicoTipo, Date dataPrevistaAtividadeEncerrar, Date dataPrevistaAtividadeEmitir,
					int indice) throws ErroRepositorioException;

	/**
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 31/10/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ParcelamentoFaixaValor obterParcelamentoFaixaValor(Integer idParcelamentoQtdePrestacao, BigDecimal valorFaixa,
					Short indicadorPercentualFaixaValor) throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com Código de Barras
	 * [SF0003] - Processar Pagamento de Documento de Cobrança
	 * 
	 * @author Sávio Luiz
	 * @created 16/02/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Object[] pesquisarParmsCobrancaDocumento(Integer idImovel, int numeroSequencialDocumento) throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com código de Barras [SB0005] - Processar
	 * Recebimento de Acréscimos por Inpontualidade Autor: Sávio Luiz
	 * Data:06/11/2006
	 */
	public Collection pesquisarCobrancaDocumentoItemComConta(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * O sistema exclui o resumo das ações de cobrança correspondente ao
	 * cronograma de ação de cobrança que esta sendo processado
	 * 
	 * @author Rafael Santos
	 * @date 08/11/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public void deletarResumoCobrancaAcao(int idCobrancaAcaoCronograma) throws ErroRepositorioException;

	/**
	 * retorna o objeto ResolucaoDiretoria com a maior data Vigência inicial
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 08/11/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaMaiorDataVigenciaInicio(Collection<Integer> idsGrupoUsuario)
					throws ErroRepositorioException;

	/**
	 * Obtem a condição referente à qtde de reparcelamentos consecutivos já
	 * realizadas para o perfil do parcelamento para o imóvel
	 * a partir da tabela PARCELAMENTO_QUANTIDADE_REPARCELAMENTO com
	 * PCPF_ID=PCPF_ID da tabela PARCELAMENTO_PERFIL e
	 * PQTR_QTMAXIMAREPARCELAMENTO igual ou menor que
	 * IMOV_NNREPARCELAMENTOCONSECUTVOS, caso mais de uma ocorrencia seja
	 * selecionada, escolher a que tiver o maior valor de
	 * PQTR_QTMAXIMAREPARCELAMENTO
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 28/11/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ParcelamentoQuantidadeReparcelamento obterQtdeReparcelamentoPerfil(Integer idPerfilParc, Short numeroReparcelamentoConsecutivos)
					throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove ClienteGuiaPagamento
	 * referentes ao parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @created 28/11/2006
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerClienteGuiaPagamentoDoParcelamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Débitos Remove ClienteGuiaPagamento
	 * referentes ao parcelamento
	 * 
	 * @author Vitor
	 * @created 21/08/2008
	 * @param idImovel
	 *            idParcelamento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public void removerClienteGuiaPagamentoDoParcelamentoHistorico(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisa o critério de cobrança linha definido para a rota
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Leonardo Vieira
	 * @created 12/12/2006
	 * @param idRota
	 * @param idCobrancaAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public CobrancaCriterio pesquisarCriterioCobrancaRota(Integer idRota, Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * Pesquisa o critério de cobrança linha definido para o critério de
	 * cobrança
	 * [UC0251] Gerar Atividade de Ação de Cobrança [SB0001] Gerar Atividade de
	 * Ação de Cobrança para os Imóveis do Cliente
	 * 
	 * @author Leonardo Vieira
	 * @created 12/12/2006
	 * @param idCobrancaCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaCriterioLinha> pesquisarCobrancaCriterioLinhaCriterio(Integer idCobrancaCriterio)
					throws ErroRepositorioException;

	public CobrancaCriterio pesquisarCobrancaCriterioIdCriterio(Integer idCobrancaCriterio) throws ErroRepositorioException;

	public Integer pesquisarOrdemServicoAcaoPrecedente(Integer idImovel, Integer idServicoTipo, Short indicadorExecucao,
					Date dataEncerramento) throws ErroRepositorioException;

	public Integer pesquisarDocumentoCobrancaRelativoAcaoPrecedente(Integer idImovel, Integer idDocTipoAcaoCobrancaPrecedente,
					Integer idAcaoCobranca, Date dataEmissao, Date dataEmissaoValidade) throws ErroRepositorioException;

	public Integer pesquisarExistenciaImovelCobracaSituacao(Integer idImovel) throws ErroRepositorioException;

	public DebitoTipo pesquisarDebitoTipo(Integer idDebitoTipo) throws ErroRepositorioException;

	public CobrancaAcaoAtividadeCronograma pesquisarCobrancaAcaoAtividadeCronograma(Integer idCronogramaAtividadeAcaoCobranca)
					throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> pesquisarCobrancaAcaoAtividadeCronograma(Integer idCobrancaGrupo,
					Integer idCobrancaAcao, Integer idComando, Date dataInicial, Date dataFinal) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaAcaoAtividadeComando> pesquisarCobrancaAcaoAtividadeComando(Integer idCobrancaGrupo, Integer idCobrancaAcao,
					Integer idComando, Date dataInicial, Date dataFinal, Integer idLocalidade, Integer codigoSetorComercial)
					throws ErroRepositorioException;

	public CobrancaAcaoAtividadeComando pesquisarCobrancaAcaoAtividadeComando(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	public Collection pesquisarCobrancaDocumentoItemContaGuiaPagamentoDebitoACobrar(Integer idCobrancaDocumento)
					throws ErroRepositorioException;

	public Collection pesquisarCobrancaDocumento(Integer idImovel, Integer idDocumentoTipo) throws ErroRepositorioException;

	/**
	 * Pesquisa o Documento de Cobrança pelo ID
	 * 
	 * @author Saulo Lima
	 * @date 20/05/2009
	 * @param idCobrancaDocumento
	 * @return CobrancaDocumento
	 * @throws ErroRepositorioException
	 */
	public CobrancaDocumento pesquisarCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	public CobrancaAcaoAtividadeCronograma pesquisarCobrancaAcaoAtividadeCronogramaId(Integer idCobrancaAcaoAtividadeCronograma)
					throws ErroRepositorioException;

	/**
	 * [UC0314] - Desfazer Parcelamentos por Entrada Não Paga Author: Raphael
	 * Rossiter Data: 28/12/2006
	 * Obtem as contas dos parcelamentos de débitos efetuados no mês de
	 * faturamento corrente e que estejam com situação normal
	 * 
	 * @param situacaoNormal
	 *            ,
	 *            anoMesFaturamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaDoParcelamento(Integer parcelamento) throws ErroRepositorioException;

	/**
	 * [UC0314] - Desfazer Parcelamentos por Entrada Não Paga Author: Raphael
	 * Rossiter Data: 28/12/2006
	 * Obtem os pagamentos para a conta dos parcelamentos de débitos efetuados
	 * no mês de faturamento corrente e que estejam com situação normal
	 * 
	 * @param conta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentoParaContaDoParcelamento(String idConta) throws ErroRepositorioException;

	/**
	 * verifica se conta tem debito cobrado (CNTA_ID ocorre na tabela
	 * DEBITO_COBRADO)
	 * [UC0214] Efetuar Parcelamento Debito [SB0011] Verificar Única Fatura
	 * 
	 * @author Vivianne Sousa
	 * @created 15/02/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Conta verificarContaDebitoCobrado(Integer idConta) throws ErroRepositorioException;

	/**
	 * obtem o consumo médio do imovel CSHI_NNCONSUMOCONSUMOMEDIO da tabla
	 * CONSUMO_HISTORICO com IMOV_ID = IMOV_ID da tabela CONTA e o maior mês/ano
	 * de consumo(CSHI_AMFATURAMENTO)
	 * [UC0214] Efetuar Parcelamento Debito [SB0011] Verificar Única Fatura
	 * 
	 * @author Vivianne Sousa
	 * @created 15/02/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterConsumoMedioImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * (DBTP_ID da tabela DEBITO_COBRADO com CNTA_ID = CNTA_ID da conta a ser
	 * parcelada ocorrendo na tabela FISCALIZACAO_SITUACAO_SERVICO_A_COBRAR)
	 * [UC0214] Efetuar Parcelamento Debito [SB0011] Verificar Única Fatura
	 * 
	 * @author Vivianne Sousa
	 * @created 15/02/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterIdDebitoTipoDeFiscalizacaoSituacaoServicoACobrar(Integer idConta) throws ErroRepositorioException;

	/**
	 * DBCB_NNPRESTACAO da tabela DEBITO_COBRADO com CNTA_ID = CNTA_ID da conta
	 * e DBTP_ID da tabela DEBITO_COBRADO com CNTA_ID = CNTA_ID ocorrendo na
	 * tabela FISCALIZACAO_SITUACAO_SERVICO_A_COBRAR
	 * [UC0214] Efetuar Parcelamento Debito [SB0011] Verificar Única Fatura
	 * 
	 * @author Vivianne Sousa
	 * @created 15/02/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterNumeroPrestacaoDebitoCobrado(Integer idConta) throws ErroRepositorioException;

	/**
	 * Metodo criado para pesquisar os parcelamentos q tenham juros e nao tenha
	 * criado o debito dos juros DBTP_ID = 44
	 * 
	 * @author Flávio Cordeiro
	 * @date 23/02/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarParcelamentosSemDebitos() throws ErroRepositorioException;

	/**
	 * Pesquisa a colecao de ação de cobrança passando o id da acao precedente
	 * 
	 * @author Sávio Luiz
	 * @created 27/02/2007
	 * @param idCobracaoAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesqsuisarAcaoCobrancaPelaPrecedente(Integer idCobracaoAcao) throws ErroRepositorioException;

	/**
	 * Pesquisa a colecao de ação de cobrança passando o id da acao precedente
	 * 
	 * @author Sávio Luiz
	 * @created 27/02/2007
	 * @param idCobracaoAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarIdDocumentoCobranca(Integer idImovel, Integer idDocumentoTipo, Date dataEmissao)
					throws ErroRepositorioException;

	/**
	 * Obtém a menor data de pagamento para as guias de pagamento
	 * [UC0302] Gerar Débitos a Cobrar de Acréscimos por Impontualidade
	 * 
	 * @author Pedro Alexandre
	 * @date 19/03/2007
	 * @param idGuiaPagamento
	 * @param idImovel
	 * @param idDebitoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarMenorDataPagamentoGuiaPagamento(Integer idGuiaPagamento, Integer idImovel, Integer idDebitoTipo)
					throws ErroRepositorioException;

	/**
	 * obtem o numero de Consumo Faturado Mes CSHI_NNCONSUMOFATURADOMES da
	 * tabela CONSUMO_HISTORICO com IMOV_ID = IMOV_ID da tabela CONTA e o maior
	 * mês/ano de consumo(CSHI_AMFATURAMENTO)
	 * [UC0214] Efetuar Parcelamento Debito [SB0011] Verificar Única Fatura
	 * 
	 * @author Vivianne Sousa
	 * @created 19/03/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterNumeroConsumoFaturadoMes(Integer idImovel) throws ErroRepositorioException;

	/**
	 * verificar se a(s) conta(s) parceladas já estão no histórico [UC0252]
	 * Desfazer Parcelamentos de Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 09/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection verificarContaHistoricoParcelamento(Integer idImovel, Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * verificar se a(s) debito(s) a cobrar parcelados já estão no histórico
	 * [UC0252] Desfazer Parcelamentos de Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 09/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection verificarDebitoACobrarHistoricoParcelamento(Integer idImovel, Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * verificar se a(s) credito(s) a realizar utilizados no parcelados já estão
	 * no histórico [UC0252] Desfazer Parcelamentos de Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 09/04/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection verificarCreditoARealizarHistoricoParcelamento(Integer idImovel, Integer idParcelamento)
					throws ErroRepositorioException;

	/**
	 * [UC0XXX] Emitir Aviso de Cobrança
	 * Seleciona os itens do documento de cobrança correspondentes a guia
	 * pagamento
	 * 
	 * @author Sávio Luiz
	 * @data 09/04/2007
	 * @param CobrancaDocumento
	 * @return Collection<CobrancaDocumentoItem>
	 */
	public Collection<Object[]> selecionarDadosCobrancaDocumentoItemReferenteGuiaPagamento(CobrancaDocumento cobrancaDocumento)
					throws ErroRepositorioException;

	/**
	 * retorna referenciaContabil da conta cancelada por retificação [UC0252]
	 * Desfazer Parcelamentos de Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 13/04/2007
	 * @param idImovel
	 * @param anoMesReferencia
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaCanceladaRetificacao(Integer idImovel, int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * retorna o objeto ParcelamentoFaixaValor com o valor do debito(valorFaixa)
	 * com desconto maior q o da faixa e menor que próxima faixa
	 * [UC0575] - Emitir Parcelamento em Atraso
	 * 
	 * @author Sávio Luiz
	 * @date 14/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosParcelamentoComMaiorTimestemp(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0458] - Imprimir Ordem de Serviço
	 * [SB9002] – Obter Dados Últimos Consumos
	 * 
	 * @author Carlos Chrystian
	 * @date 11/04/2013
	 * @param ordemServico
	 * @return
	 */
	public Collection<Object[]> obterDadosUltimosConsumos(Integer idImovel, Integer referenciaInicial, Integer referenciaFinal)
					throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com Código de Barras
	 * [SF0003] - Processar Pagamento de Documento de Cobrança
	 * 
	 * @author Ana Maria
	 * @created 13/04/2007
	 * @param idCliente
	 * @param numeroSequencialDocumento
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarCobrancaDocumentoItemCliente(Integer idCliente, int numeroSequencialDocumento)
					throws ErroRepositorioException;

	/**
	 * Consulta o id e a situação da ordem de serviço associada ao documento de
	 * cobrança passado como parâmetro
	 * 
	 * @author Sávio Luiz
	 * @created 13/04/2007
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Object[] pesquisarDadosOrdemServicoDocumentoCobranca(Integer idDocumentoCobranca) throws ErroRepositorioException;

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
	public Collection pesquisarIdsParcelamentosItemDebitoACobrar(Collection idsDebitoACobrar) throws ErroRepositorioException;

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
	public Collection pesquisarIdsCobrancaDocumentoItemDebitoACobrar(Collection idsDebitoACobrar) throws ErroRepositorioException;

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
	public void deletarCobrancaDocumentoItemDebitoACobrar(Collection idsDocumentoItemDebitoACobrar) throws ErroRepositorioException;

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
	public void atualizarParcelamentosItemDebitoACobrar(Collection idsParcelamentosItemDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Pesquisa os dados de cobranca documento agrupado para pegar a quantidade
	 * e o valor dos documentos
	 * 
	 * @author Sávio Luiz
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosCobrancaDocumentoAgrupadoPorDataPrevista(int idCobrancaAtividadeAcaoCronograma)
					throws ErroRepositorioException;

	/**
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Pesquisa os dados de cobranca documento agrupado para pegar a quantidade
	 * e o valor dos documentos
	 * 
	 * @author Sávio Luiz
	 * @date 17/10/2006
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosCobrancaDocumentoAgrupadoPorDataComando(int idCobrancaAtividadeAcaoCronograma)
					throws ErroRepositorioException;

	/**
	 * atualiza o sequencial de conta impressão
	 * 
	 * @author Sávio Luiz
	 * @date 18/05/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarSequencialCobrancaDocumentoImpressao(Map<Integer, Integer> mapAtualizaSequencial) throws ErroRepositorioException;

	/**
	 * Pesquisar relação de protocolos de documentos de cobrança do cronograma
	 * 
	 * @author Ana Maria
	 * @date 15/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarProtocoloDocumentoCobrancaCronograma(Integer idCobrancaAcaoAtividadeCronograma)
					throws ErroRepositorioException;

	/**
	 * Pesquisar relação de protocolos de documentos de cobrança do cronograma
	 * 
	 * @author Ana Maria
	 * @date 21/05/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarProtocoloDocumentoCobrancaEventual(Integer idCobrancaAcaoAtividadeComand) throws ErroRepositorioException;

	/**
	 * Recupera a coleção de OS para o encerramento
	 * [UC0478] - Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0007] - Determinar Situação da Ordem de Serviço
	 * 
	 * @author Sávio Luiz
	 * @date 28/05/2007
	 * @throws ControladorException
	 */
	public void atualizarParmsOS(Collection colecaoIdsOS, Integer idMotivoEncerramento) throws ErroRepositorioException;

	/**
	 * Recupera os dados de documento item
	 * 
	 * @author Sávio Luiz
	 * @created 29/05/2006
	 * @param matriculaImovel
	 *            Matrícula do Imovel
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection pesquisarDadosCobrancaDocumentoItem(Integer idDocumentoCobranca) throws ErroRepositorioException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * [FS0004] Validar Registro Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @created 05/06/2007
	 * @param idRA
	 * @exception ErroRepositorioException
	 */
	public Object[] pesquisarRegistroAtendimentoTransferenciaDebitoCredito(Integer idRA) throws ErroRepositorioException;

	/**
	 * [UC00609] Transferencia de Debitos/Creditos
	 * [FS0004] Validar Registro Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @created 05/06/2007
	 * @param idSolicitacaoTipoEspecificacao
	 * @exception ErroRepositorioException
	 */
	public EspecificacaoTipoValidacao pesquisarEspecificacaoTipoValidacaoTransferenciaDebitoCredito(Integer idSolicitacaoTipoEspecificacao)
					throws ErroRepositorioException;

	/**
	 * Pesquisar relação de parcelamento
	 * 
	 * @author Ana Maria
	 * @date 01/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection<RelacaoParcelamentoRelatorioHelper> pesquisarRelacaoParcelamento(
					FiltrarRelacaoParcelamentoHelper filtrarRelacaoParcelamento) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar o resumo das ações de cobrança com a
	 * atividade emitir já realizada e a atividade encerrar ainda não realizada
	 * e realizar a atividade encerrar das ações que estejam comandadas.
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * [SB0001] - Processar Documentos de Cobrança
	 * Retorna os dados do Imovel
	 * 
	 * @author Sávio Luiz
	 * @date 11/06/2007
	 * @return Date retorno
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosImovelPorOS(int idOrdemServico) throws ErroRepositorioException;

	/**
	 * [UC0XXXX] Gerar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @author Sávio Luiz
	 * @created 15/06/2006
	 * @exception ErroRepositorioException
	 *                Repositorio Exception
	 */
	public Collection<Object[]> pesquisarCobrancaAcaoAtividadeComandoSemRealizacao(String idsAcoesSemPrazoValidade,
					Date dataUltimoProcessamentoResumo) throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar os resumos das ações de cobrança eventuais.
	 * [UC0XXXX] Gerar Resumo das Ações de Cobrança Eventuais
	 * [SB0001] - Processar Documentos Cobrança
	 * Retorna os CBDO_ID da tabela COBRANCA_DOCUMENTO com CAAC_ID da tabela
	 * COBRANCA_ATIVIDADE_ACAO_CRONOGRAMA
	 * 
	 * @author Sávio Luiz
	 * @date 18/06/2007
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarOrdemServicoEventual(int idCobrancaAtividadeAcaoComando) throws ErroRepositorioException;

	/**
	 * [UC0614] Gerar Resumo das Ações de Cobrança Eventuais
	 * O sistema exclui o resumo das ações de cobrança eventual referente ao comando de ação de
	 * cobrança ou o comando de faturamento que está sendo processado.
	 * 
	 * @author Anderson Italo
	 * @date 12/07/2012
	 */
	public void excluirResumoCobrancaAcaoEventual(Integer idCobrancaAcaoAtividadeComando, Integer idFaturamentoGrupoCronogramaMensal)
					throws ErroRepositorioException;

	/**
	 * [UC0478] Gerar Resumo das Ações de Cobrança do Cronograma
	 * Pesquisa os dados de cobranca documento para geração dos dados da tabela de
	 * RESUMO_COBRANCA_ACAO_EVENTUAL
	 * 
	 * @author Sávio Luiz
	 * @date 19/10/2006
	 */
	public Collection pesquisarDadosCobrancaDocumentoGeracaoResumoEventual(Integer idCobrancaAtividadeAcaoComando)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso permite gerar os resumos das ações de cobrança eventuais.
	 * [UC0XXXX] Gerar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @author Sávio Luiz
	 * @date 19/06/2007
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public void atualizarCobrancaAcaoAtividadeComando(int idCobrancaAtividadeAcaoComando) throws ErroRepositorioException;

	/**
	 * Ana Maria Data: 08/07/2007 Pesquisa os ID dos Imovéis pelo Cliente
	 * 
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @return Coleção de Ids dos Imóveis
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdsImoveisCliente(String codigoCliente) throws ErroRepositorioException;

	/**
	 * [UC0067] Obter Débito do Imóvel ou Cliente Obtem os débitos dos imóveis
	 * Author: Ana Maria Data: 28/06/2006
	 * 
	 * @param idsImoveis
	 *            Matriculas dos Imoveis
	 * @param contaSituacaoNormal
	 *            Situação Normal de Conta
	 * @param contaSituacaoRetificada
	 *            Situação Retificada de Conta
	 * @param contaSituacaoIncluida
	 *            Situação Incluída de Conta
	 * @param anoMesInicialReferenciaDebito
	 *            Ano Mes Inicial Referencia Debito
	 * @param anoMesFinalReferenciaDebito
	 *            Ano Mes Final Referencia Debito
	 * @param anoMesInicialVecimentoDebito
	 *            Ano Mes Inicial Vencimento Debito
	 * @param anoMesFinalVencimentoDebito
	 *            Ano Mes Inicial Vencimento Debito
	 * @return Coleção de Contas do Imovel
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImoveis(Collection idsImoveis, String contaSituacaoNormal, String contaSituacaoRetificada,
					String contaSituacaoIncluida, String contaSituacaoParcelada, String anoMesInicialReferenciaDebito,
					String anoMesFinalReferenciaDebito, Date anoMesInicialVecimentoDebito, Date anoMesFinalVencimentoDebito,
					String contaSituacaoPrescrita)
					throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente
	 * 
	 * @author Ana Maria
	 * @date 08/08/2007
	 * @author eduardo henrique
	 * @date 29/07/2008
	 *       Alterações na consulta devido à mudanças na GuiaPagamento (v0.03)
	 * @author Virgínia Melo
	 * @date 07/04/2009
	 *       Adicionado valor da prestação.
	 * @author Saulo Lima
	 * @date 25/06/2009
	 *       Alteração para retornar coleção de prestações. Mudança na assinatura.
	 * @param idsImoveis
	 *            Matriculas dos Imoveis
	 * @param situacaoNormal
	 *            Situação Normal
	 * @param dataVencimentoInicial
	 *            Data Vencimento Inicial
	 * @param dataVencimentoFinal
	 *            Data Vecimento Final
	 * @return Coleção de Prestações das Guias de Pagamentos
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiasPagamentoIdsImoveis(Collection idsImoveis, Integer situacaoNormal, Integer situacaoIncluida,
					Integer situacaoRetificada, Integer situacaoParcelada, Date dataVencimentoInicial, Date dataVencimentoFinal,
					Integer situacaoPrescrita)
					throws ErroRepositorioException;

	public Collection pesquisarGuiasPagamentoIdsImoveisSimplificado(Collection idsImoveis, String situacaoNormal,
					Date dataVencimentoInicial, Date dataVencimentoFinal) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente
	 * Pesquisa os ID dos imoveis dos cliente sem ralação fim
	 * 
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @param relacaoTipo
	 *            Relação Tipo Cliente Imovel
	 * @return Coleção de Debitos A Cobrar do Cliente
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdImoveisClienteSemRelacaoFim(String codigoCliente, Integer relacaoTipo) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imovel ou Cliente Pesquisa os ID
	 * dos imoveis dos cliente sem ralação fim
	 * 
	 * @param codigoCliente
	 *            Codigo Cliente
	 * @param relacaoTipo
	 *            Relação Tipo Cliente Imovel
	 * @return Coleção de Debitos A Cobrar do Cliente
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdImoveisClienteSuperiorSemRelacaoFim(String codigoCliente) throws ErroRepositorioException;

	/**
	 * Gerar Curva ABC de Debitos
	 * [UC0621] Gerar Curva ABC de Debitos
	 * 
	 * @author Ivan Sérgio
	 * @date 01/08/2007
	 */
	public Collection gerarCurvaAbcDebitos(String classificacao, String indicadorImovelMedicaoIndividualizada,
					String indicadorImovelParalizacaoFaturamentoCobranca, String idGerenciaRegional, String idUnidadeNegocio,
					String idLocalidadeInicial, String idLocalidadeFinal, String idSetorComercialInicial, String idSetorComercialFinal,
					String[] situacaoLigacaoAgua, String[] situacaoLigacaoEsgoto, String intervaloConsumoMinimoFixadoEsgotoInicial,
					String intervaloConsumoMinimoFixadoEsgotoFinal, String indicadorMedicao, String idTipoMedicao, String idPerfilImovel,
					String idTipoCategoria, String[] categoria, String idSubCategoria) throws ErroRepositorioException;

	/**
	 * [UC0630] - Solicitar Emissão do Extrato de Débitos
	 * Author: Vivianne Sousa
	 * Data: 22/08/2007
	 * Obtem os parcelamentos de débitos efetuados que estejam com situação normal
	 * 
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarParcelamentosSituacaoNormal(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0630] - Solicitar Emissão do Extrato de Débitos
	 * Author: Vivianne Sousa
	 * Data: 22/08/2007
	 * 
	 * @param idParcelamento
	 * @return Coleção de Debitos A Cobrar
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitosACobrarImovelParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * [UC0630] - Solicitar Emissão do Extrato de Débitos
	 * Author: Vivianne Sousa
	 * Data: 22/08/2007
	 * 
	 * @param idParcelamento
	 * @return Coleção de Creditos A Realizar
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCreditosARealizarParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * O sistema ordena a lista de documentos de cobrança por empresa (EMPR_ID
	 * da tabela DOCUMENTO_COBRANCA), localidade (LOCA_ID), setor
	 * (CBDO_CDSETORCOMERCIAL), quadra (CBDO_NNQUADRA), lote e sublote
	 * (IMOV_NNLOTE e IMOV_SUBLOTE da tabela IMOVEL com IMOV_ID da tabela
	 * DOCUMENTO_COBRANCA)
	 * 
	 * @author Sávio Luiz
	 * @data 26/05/2006
	 * @param idBairro
	 *            ,
	 *            idLogradouro
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoParaEmitirCAER(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao, int quantidadeCobrancaDocumentoInicio)
					throws ErroRepositorioException;

	/**
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * 
	 * @author Vivianne Sousa
	 * @date 01/09/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarRDUtilizadaPeloImovel(Integer idRD, Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 01/09/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorDebitoACobrarSancoes(Integer idImovel, Integer anoMesInicialReferenciaDebito,
					Integer anoMesFinalReferenciaDebito) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 06/09/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorDebitoACobrar(Integer idImovel, Integer anoMesInicialReferenciaDebito,
					Integer anoMesFinalReferenciaDebito) throws ErroRepositorioException;

	/**
	 * Faz parte de [UC0067] Obter Débito do Imóvel ou Cliente Obtem os débitos total
	 * de um cliente
	 */
	public Collection pesquisarDebitosCliente(Collection idsContas, Collection idsImoveis, String contaSituacaoNormal,
					String contaSituacaoRetificada, String contaSituacaoIncluida, String contaSituacaoParcelada,
					String anoMesInicialReferenciaDebito, String anoMesFinalReferenciaDebito, Date anoMesInicialVecimentoDebito,
					Date anoMesFinalVencimentoDebito, String contaSituacaoPrescrita) throws ErroRepositorioException;

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
					Integer anoMesFinalReferenciaDebito) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento Debito
	 * 
	 * @author Vivianne Sousa
	 * @created 20/09/2007
	 * @param idImovel
	 * @param anoMesInicialReferenciaDebito
	 * @param anoMesFinalReferenciaDebito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorDebitoCobradoSancoes(Integer idImovel, Integer anoMesInicialReferenciaDebito,
					Integer anoMesFinalReferenciaDebito) throws ErroRepositorioException;

	/**
	 * [UC0701] Informar Índices dos Acréscimos de Impontualidade
	 * 
	 * @author Sávio Luiz
	 * @created 26/09/2007
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarMaximoAnoMesIndicesAcerscimosImpontualidade() throws ErroRepositorioException;

	/**
	 * [UC0216] Calcular Acréscimo por Impontualidade
	 * 
	 * @autor: Raphael Rossiter
	 *         Pesquisa os dados do Indices Acrescimo Impontualidade menor ao
	 *         ano mes referencia
	 * @param anoMesReferenciaDebito
	 * @return O Indices Acrescimos por Impontualidade
	 * @throws ErroRepositorioException
	 */
	public IndicesAcrescimosImpontualidade pesquisarMenorIndiceAcrescimoImpontualidade() throws ErroRepositorioException;

	/**
	 * Retorna uma colecao de Debitos
	 * por Faixa de Valores dos Imoveis
	 * 
	 * @author Ivan Sergio
	 * @created 20/09/2007
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDebitoImovelPorFaixaValores(String idImovel, String valorMinimoDebito, String anoMesReferenciaInicial,
					String anoMesReferenciaFinal, String classificacao) throws ErroRepositorioException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao) throws ErroRepositorioException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao) throws ErroRepositorioException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao) throws ErroRepositorioException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal, String idCobrancaAcao)
					throws ErroRepositorioException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String idLocalidade, String idCobrancaAcao) throws ErroRepositorioException;

	// Flávio Cordeiro
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotas(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao) throws ErroRepositorioException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasSemCriterioIntervaloUnidadeNegocio(String idUnidadeNegocio, String idCobrancaAcao)
					throws ErroRepositorioException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasSemCriterioIntervaloGrupo(String idGrupoCobranca, String idCobrancaAcao)
					throws ErroRepositorioException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasSemCriterioIntervaloGerencia(String idGerenciaRegional, String idCobrancaAcao)
					throws ErroRepositorioException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasSemCriterioIntervaloLocalidade(String idLocalidadeInicial, String idLocalidadeFinal,
					String idCobrancaAcao) throws ErroRepositorioException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasSemCriterioIntervaloSetor(String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String idLocalidade, String idCobrancaAcao) throws ErroRepositorioException;

	// Saulo Lima
	// caso de uso [UC0543] Associar Conjunto de Rotas a Criterio de Cobranca
	public Collection pesquisarRotasSemCriterio(String codigoSetorComercial, String rotaInicial, String rotaFinal, String idLocalidade,
					String idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * [UC0067] Obter Débito do Imóvel ou Cliente
	 * 
	 * @autor: Raphael Rossiter
	 *         Verifica se existe uma devolução associada ao credito
	 * @param creditoARealizar
	 * @return boolean
	 * @throws ErroRepositorioException
	 */
	public boolean existeDevolucao(CreditoARealizar creditoARealizar) throws ErroRepositorioException;

	/**
	 * [UC0067] Inserir Comando Negaivação
	 * 
	 * @autor: Ana Maria
	 *         [FS0019] Verificar existência de Parcelamento
	 * @param idImovel
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarClienteResponsavelParcelamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @author Deyverson Santos
	 * @date 17.11.2008
	 * @author Saulo Lima
	 * @date 19/01/2009
	 *       Inclusão do JOIN com debitoTipo e cláusulas FETCH
	 * @param CobrancaDocumento
	 * @return Collection<CobrancaDocumentoItem>
	 * @throws ErroHirbenateException
	 */
	public Collection<CobrancaDocumentoItem> pesquisarDocumentoItem(CobrancaDocumento cobrancaDocumento) throws ErroRepositorioException;

	public void removerDebitosACobrarECreditosARealizarDoParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * Pesquisar Guia Pagamento Prestacao
	 * 
	 * @author Virgínia Melo
	 * @date 02/03/2009
	 * @param idCobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarGuiaPagamentoPrestacao(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Atualizar Guia Pagamento Prestacao
	 * 
	 * @author Virgínia Melo
	 * @date 02/03/2009
	 * @param guiaPagamentoPrestacoes
	 * @param dataVencimento
	 * @throws ErroRepositorioException
	 */
	public void atualizarGuiaPagamentoPrestacao(Collection<Object[]> guiaPagamentoPrestacoes, Date dataVencimento)
					throws ErroRepositorioException;

	/**
	 * Pesquisar Feriado Municipal
	 * 
	 * @author eduardo henrique
	 * @date 15/07/2009
	 * @param dataFeriado
	 * @returns FeriadoMunicipal
	 * @throws ErroRepositorioException
	 */
	public Collection<MunicipioFeriado> pesquisarFeriadoMunicipal(Municipio municipio, Date dataFeriado) throws ErroRepositorioException;

	/**
	 * Pesquisar Feriado Nacional
	 * 
	 * @author eduardo henrique
	 * @date 15/07/2009
	 * @param dataFeriado
	 * @returns FeriadoNacional
	 * @throws ErroRepositorioException
	 */
	public Collection<NacionalFeriado> pesquisarFeriadoNacional(Date dataFeriado) throws ErroRepositorioException;

	/**
	 * Pesquisa o documento de cobranca da acao precedente (2.3.1)
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0003] Gerar Atividade de Ação de Cobrança o Imóvel
	 * 
	 * @author Francisco do Nascimento
	 * @date 22/12/2008
	 * @param idImovel
	 * @param idServicoTipo
	 * @param indicadorExecucao
	 * @param dataEncerramento
	 * @return Id do documento de cobrança
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarDocumentoCobrancaAcaoPrecedente(Integer idImovel, Integer idServicoTipo, Short indicadorExecucao,
					Date dataEncerramento) throws ErroRepositorioException;

	public Collection pesquisarImovelCobrancaSituacao(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa a quantidade de Rotas que nao possui um Criterio definido para cada uma das Acoes de
	 * Cobrancas passadas no filtro
	 * 
	 * @author Victor Cisneiros
	 * @date 10/12/2008
	 */
	public Integer pesquisarQtdeRotasSemCriteriosParaAcoesCobranca(PesquisarQtdeRotasSemCriteriosParaAcoesCobranca filtro)
					throws ErroRepositorioException;

	/**
	 * [UC0676] - Consultar Resumo da Negativacao
	 * Pesquisa resumo Negativacao
	 * 
	 * @author Marcio Roberto
	 * @date 28/02/2008
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection consultarNegativacao(DadosConsultaNegativacaoHelper dadosConsultaNegativacaoHelper, int tipo)
					throws ErroRepositorioException;

	/**
	 * Pesquisa os debitos a cobrar pelo documento de cobrança
	 * 
	 * @param cobrancaDocumento
	 * @return CobrancaDocumentoItem
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumentoItem> selecionarCobrancaDocumentoItemReferenteDebitoACobrar(CobrancaDocumento cobrancaDocumento)
					throws ErroRepositorioException;

	public AcaoCobrancaEfeito pesquisarAcaoCobrancaEfeitoPorId(String acaoCobrancaEfeito) throws ErroRepositorioException;

	public Collection<Object[]> pesquisarDocumentoCobrancaConta(Integer idConta) throws ErroRepositorioException;

	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoParaEmitirJuridico(Integer idCronogramaAtividadeAcaoCobranca,
					Integer idComandoAtividadeAcaoCobranca, Date dataAtualPesquisa, Integer idAcaoCobranca) throws ErroRepositorioException;

	public Collection<OrdemServico> pesquisarOrdemServicoCobrancaAtividadeAcao(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma) throws ErroRepositorioException;

	public Collection<EmissaoOSCobrancaHelper> pesquisarOS(Integer idComando, CobrancaAcaoAtividade tipoComandoAcaoCobrancas)
					throws ErroRepositorioException;

	public List<Integer> associarAgenteOS(Empresa empresa, Integer local, Integer setor, Integer[] quadras,
					CobrancaAcaoAtividade tipoComandoAcaoCobranca, Integer idComando);

	public List<OrdemServico> getOsCancelar(CobrancaAcaoAtividade tipoComandoAcaoCobranca, Integer idComando);

	public Collection<CobrancaSucesso> filtrarRelatorioFechamentoCobranca(Date periodoInicio, Date periodoFim, CobrancaAcao acao,
					Empresa empresa, CobrancaAcaoAtividadeComando acaoAtividadeComando,
					CobrancaAcaoAtividadeCronograma acaoAtividadeCronograma, String stringComandoTipo) throws ErroRepositorioException;

	/*
	 * RelatorioEficienciaCobranca
	 */
	public Collection<CobrancaAcaoAtividadeComando> filtrarComandosRelatorioEficienciaCobranca(Date dataInicial, Date dataFinal,
					CobrancaAcao acao, Empresa empresa, String[] setores, String[] grupos) throws ErroRepositorioException;

	/*
	 * RelatorioEficienciaCobranca
	 */
	public Collection<CobrancaAcaoAtividadeCronograma> filtrarCronogramasRelatorioEficienciaCobranca(Date dataInicial, Date dataFinal,
					CobrancaAcao acao, Empresa empresa, String[] setores, String[] grupos) throws ErroRepositorioException;

	/*
	 * RelatorioEficienciaCobranca
	 */
	public EficienciaCobrancaRelatorioHelper calcularRecuperacaoDividaRelatorioEficienciaCobranca(EficienciaCobrancaRelatorioHelper helper,
					String tipoComando) throws ErroRepositorioException;

	public Integer somatorioQtdContaCobrancaSucesso(CobrancaAcaoAtividadeComando cacm, CobrancaAcaoAtividadeCronograma caac,
					Integer qtdDiasVencidos, Integer qtdDiasVencidosMax) throws ErroRepositorioException;

	public BigDecimal somatorioValorContaCobrancaSucesso(CobrancaAcaoAtividadeComando cacm, CobrancaAcaoAtividadeCronograma caac,
					Integer qtdDiasVencidosMin, Integer qtdDiasVencidosMax) throws ErroRepositorioException;

	public Collection pesquisarBairroPorGrupoEmQuadraRota(Integer grupo) throws ErroRepositorioException;

	public Collection filtrarAcompanhamentoExecucaoServicoCobranca(Integer comandoTipo, Integer comandoCronograma, Integer comandoEventual,
					Integer cobrancaAcao, Date periodoInicial, Date periodoFinal, Integer situacao, Integer religado, Integer servico,
					Integer localidade, Integer grupo, String[] bairro, String[] setorComercial) throws ErroRepositorioException;

	public Integer consultarQtdeDocumentosItensPorCobrancaDocumento(CobrancaDocumento cobrancaDocumento) throws ErroRepositorioException;

	public Collection filtrarRelatorioImovelPorAcaoCobranca(String comando, String idCobrancaAcaoAtividadeComando,
					String idCobrancaAcaoAtividadeCronograma, String[] acao, Date dataInicial, Date dataFinal, String grupo,
					String[] setorComercial, String[] bairro, String[] categoria, String localidade) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection<OrdemServico> consultarOrdensServico(boolean comandoCronograma, boolean comandoEventual, Date dataInicio,
					Date dataFim, Collection<Integer> idsAcaoCobranca, Integer idGrupoCobranca, Collection<Integer> idsSetorComercial,
					Collection<Integer> idsBairro, Collection<Integer> idsCategoria, Collection<Integer> idsServicoTipo,
					Integer idComandoCronograma, Integer idComandoEventual) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Integer consultarQuantidadeOrdensServico(boolean comandoCronograma, boolean comandoEventual, Date dataInicio, Date dataFim,
					Collection<Integer> idsAcaoCobranca, Integer idGrupoCobranca, Collection<Integer> idsSetorComercial,
					Collection<Integer> idsBairro, Collection<Integer> idsCategoria, Collection<Integer> idsServicoTipo,
					Integer idComandoCronograma, Integer idComandoEventual) throws ErroRepositorioException;

	public Integer filtrarRelatorioImovelPorAcaoCobrancaCount(String comando, String idComando, String idCronograma, String[] acao,
					Date dataInicial, Date dataFinal, String grupo, String[] setorComercial, String[] bairro, String[] categoria,
					String localidade)
					throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Object[] obterQuantidadeOrdensPeriodo(String tipoComando, String idCobrancaAcaoComando, String idCobrancaAcaoCronograma,
					String padraoPeriodo, String periodoInicio, String periodoFim, String periodoMesInicio, String periodoMesFim,
					String periodoAnoInicio, String periodoAnoFim, String localidade, Integer acaoSelecionada, Integer empresa,
					Integer[] grupos, Integer[] setores, Integer[] bairros, Integer[] grupoServicos, Integer[] subGrupoServicos,
					Integer[] servicos, Integer[] tiposCorte, Integer[] tiposSupressao) throws ErroRepositorioException;

	/**
	 * @author isilva
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaContratoRemuneracaoPorSucesso> pesquisarCobrancaContratoRemuneracaoVencimentoPorContratoVigente(
					Integer idEmpresa) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarCobrancaAcaoEmFaixa(Integer idEmpresa, String tipoComando, String acaoSelecionada, String empresa,
					String periodoInicio, String periodoFim, String idCobrancaAcaoAtividadeComando,
					String idCobrancaAcaoAtividadeCronograma, Integer diasAteAnterior, Integer diasAte) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Integer relacaoImovelReligacaoEspecialDiaCount(Integer idUnidade, Date periodoInicio, Date periodoFim, Integer idGrupo,
					String[] setores, String[] bairros, String[] tiposServico) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> relacaoImovelReligacaoEspecialDia(Integer idUnidade, Date periodoInicio, Date periodoFim, Integer idGrupo,
					String[] setores, String[] bairros, String[] tiposServico) throws ErroRepositorioException;

	public void pesquisasql(String string);

	public Collection<CriterioSituacaoLigacaoAgua> pesquisarCobrancaCriterioSituacaoLigacaoAgua(Integer idCobrancaCriterio,
					Integer idSituacaoAguaLigacao) throws ErroRepositorioException;

	public Collection<CriterioSituacaoLigacaoEsgoto> pesquisarCobrancaCriterioSituacaoLigacaoEsgoto(Integer idCobrancaCriterio,
					Integer idSituacaoAguaLigacao) throws ErroRepositorioException;

	public Collection pesquisarRotasPorSetorComercial(Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * @author Saulo Lima
	 * @date 01/10/2010
	 * @param idSetorComercial
	 * @param idCobrancaGrupo
	 * @return Collection<Rota>
	 * @throws ErroRepositorioException
	 */
	public Collection<Rota> pesquisarRotasPorSetorComercialCobrancaGrupo(Integer idSetorComercial, Integer idCobrancaGrupo)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentosComAcaoContraEfeitoPorImovel(Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * @param idCobrancaDocumentoItem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasDeCobrancaDocumentoItem(Integer idCobrancaDocumentoItem) throws ErroRepositorioException;

	/**
	 * @param idCobrancaAcaoCronograma
	 * @param idCobrancaAcaoComando
	 * @param dataEmissao
	 * @param idCobrancaAcao
	 * @param quantidadeCobrancaDocumentoInicio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoPorCobrancaAcao(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao, Integer idCobrancaAcao, int quantidadeCobrancaDocumentoInicio)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param idCobrancaAcaoAtividadeCronograma
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Boolean verificaNegativacao(Integer idImovel, Integer idCobrancaAcaoAtividadeCronograma, Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * @param idContaHistorico
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosImovelPorContaHistorico(Integer idContaHistorico) throws ErroRepositorioException;

	/**
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarContraAcaoPorCorteTipo(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * @param idServicoTipo
	 * @param dataEmissaoDocumentoCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarOSEncOuPenDoDocCobranca(Integer idServicoTipo, Date dataEmissaoDocumentoCobranca, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * @param idLigacaoAgua
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarContraAcaoPorSupressaoTipo(Integer idLigacaoAgua) throws ErroRepositorioException;

	/**
	 * @param idCobrancaCriterio
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaSituacaoPorCobrancaCriterio(Integer idCobrancaCriterio) throws ErroRepositorioException;

	/**
	 * @param idDebitoCreditoSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DebitoCreditoSituacao pesquisarDebitoCreditoSituacao(Integer idDebitoCreditoSituacaoAtual) throws ErroRepositorioException;

	/**
	 * @param idCobrancaDocumento
	 * @throws ErroRepositorioException
	 */
	public void atualizarNumeroSequenciaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarExstenciaPerfilParcelamento(Integer idPerfilParcelamento, Integer idRD, Integer idImovelSituacaoTipo,
					Integer idImovelPerfil, Integer idSubcategoria, BigDecimal valorMinimoDebitoParcelar,
					BigDecimal valorMaximoDebitoParcelar, String tipoPesquisa) throws ErroRepositorioException;

	/**
	 * Remove os ParcelamentoQuantidadePrestacao e seus ParcelamentoFaixaValor se existir.
	 * 
	 * @author isilva
	 * @created 10/11/2010
	 * @param idParcelamentoQuantidadeReparcelamento
	 * @throws ErroRepositorioException
	 */
	public void removerParcelamentoQuantidadePrestacaoEParcelamentoFaixaValor(Integer idParcelamentoQuantidadeReparcelamento)
					throws ErroRepositorioException;

	/**
	 * Remove os ParcelamentoQuantidadePrestacao e seus ParcelamentoFaixaValor se existir.
	 * 
	 * @author isilva
	 * @created 11/11/2010
	 * @param idsParcelamentoQuantidadePrestacao
	 * @throws ErroRepositorioException
	 */
	public void removerParcelamentoQuantidadePrestacaoEParcelamentoFaixaValor(Collection<Integer> idsParcelamentoQuantidadePrestacao)
					throws ErroRepositorioException;

	/**
	 * @param idCobrancaAcaoCronograma
	 * @param idCobrancaAcaoComando
	 * @param dataEmissao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoNaoNegativado(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando, Date dataEmissao) throws ErroRepositorioException;

	/**
	 * @param numeroHidrometro
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Hidrometro getHidrometro(String numeroHidrometro) throws ErroRepositorioException;

	/**
	 * @param numeroHidrometro
	 * @return
	 * @throws ErroRepositorioException
	 */
	public HidrometroInstalacaoHistorico getHidrometroInstalacaoHistorico(String numeroHidrometro) throws ErroRepositorioException;

	/**
	 * @param idCobrancaAcaoCronograma
	 * @param idCobrancaAcaoComando
	 * @param dataEmissao
	 * @param idCobrancaAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarTodosCobrancaDocumentoParaEmitirJuridico(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando,
					Date dataEmissao, Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * @param idCobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadeCobrancaDocumentoItem(int idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * @param idCobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicoPorDocumentoCobranca(Integer idCobrancaDocumento) throws ErroRepositorioException;

	public Object[] pesquisarContraAcao(ContraAcao contraAcao, Integer idCobrancaDocumento) throws ErroRepositorioException;

	public ParcelamentoQuantidadePrestacao pesquisarParcelamentoQuantidadePrestacaoMaximaParcela(Integer idResolucaoDiretoria)
					throws ErroRepositorioException;

	public PreParcelamentoHelper obterPreParcelamento(CobrancaDocumento documentoCobranca) throws ErroRepositorioException;

	public PreParcelamentoOpcao obterPreParcelamentoOpcao(Integer id) throws ErroRepositorioException;

	/**
	 * @author Andre Nishimura
	 * @date 01/03/2011
	 * @return Numero Sequence Infraçao Perfil
	 * @throws ErroRepositorioException
	 */
	public Integer obterSequenceInfracaoPerfil() throws ErroRepositorioException;

	public List consultarTotalizadoresAcaoCobrancaAtividade(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma) throws ErroRepositorioException;

	public Integer consultarTotalizadoresAcaoCobrancaAtividadeItens(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando,
					CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma) throws ErroRepositorioException;

	public Integer obterIdAcaoCobrancaEfeitoPelaCobrancaAcao(Integer idCbac) throws ErroRepositorioException;

	/**
	 * @param idCobrancaDocumento
	 * @param idServicoTipoAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarOrdemServicoPorDocumentoCobranca(Integer idCobrancaDocumento, Integer idServicoTipoAcao)
					throws ErroRepositorioException;

	public PreParcelamentoOpcao obterCobrancaDocumentoPreParcelamentoOpcao(Integer seguencialDocCobrancao, Integer idPreParcelamentoOpcao)
					throws ErroRepositorioException;

	public Collection<CobrancaDocumentoItem> obterCobrancaDocumentoItemPrePacelamento(CobrancaDocumento cobrancaDocumento)
					throws ErroRepositorioException;

	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaMaiorDataVigenciaInicioComEntrada() throws ErroRepositorioException;

	/**
	 * retorna uma colecao de ResolucaoDiretoria
	 * 
	 * @author Anderson Italo
	 * @date 07/04/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual() throws ErroRepositorioException;

	public Collection<CobrancaSituacaoHistorico> pesquisarCobrancaSituacaoHistorico(Integer idImovel, Integer anoMesFinal)
					throws ErroRepositorioException;

	public Integer pesquisarDebitosACobrarDeParcelamentoEmAberto(Integer idImovel, Integer anoMesReferencia,
					Collection<Integer> idsDebitoTipo) throws ErroRepositorioException;

	/**
	 * Recupera contas do parcelamento através dos itens do parcelamento.
	 * 
	 * @param parcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContaDoParcelamentoPorItem(Integer parcelamento) throws ErroRepositorioException;

	public Collection<BoletoBancario> pesquisarBoletoBancario(BoletoBancarioHelper boletoBancarioHelper, boolean desconsiderarParametros,
					boolean verificarDocumentoCobranca, boolean verificarNumeroBoletoCartaCobranca, int pageOffset)
					throws ErroRepositorioException;

	/**
	 * @author Isaac Silva
	 * @date 23/09/2011
	 * @param cobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumentoItem> obterCobrancaDocumentoItem(CobrancaDocumento cobrancaDocumento)
					throws ErroRepositorioException;

	/**
	 * @author Carlos Chrystian
	 * @date 28/12/2011
	 * @param cobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumentoItem> obterCobrancaDocumentoItemComConta(CobrancaDocumento cobrancaDocumento)
					throws ErroRepositorioException;

	public BoletoBancario obterBoletoBancarioDebito(ObjetoTransacao debito) throws ErroRepositorioException;

	/**
	 * [UC3018] Gerar TXT Cartas Cobrança Bancária.
	 * Método que vai retornar o id dos Documentos de Cobrança que vão gerar o arquivo TXt com as
	 * cartas que serão enviadas com o aviso de cobrança.
	 * 
	 * @author Ailton Sousa
	 * @date 12/10/2011
	 * @param idComandoCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDocumentosCobrancaBancaria(Integer idComandoCobranca) throws ErroRepositorioException;

	/**
	 * Pesquisa CobrancaDocumentoItem pelo CobrancaDocumento e Pela Conta.
	 * 
	 * @author Ailton Sousa
	 * @date 13/10/2011
	 * @param idCobrancaDocumento
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public CobrancaDocumentoItem pesquisarCobrancaDocumentoItemPelaConta(Integer idCobrancaDocumento, Integer idConta)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o Valor do Débito do Boleto Bancário pelo Documento de Cobrança.
	 * 
	 * @author Ailton Sousa
	 * @date 13/10/2011
	 * @param idCobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BoletoBancario pesquisarBoletoBancarioPorCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Pesquisa a Quantidade de Dias de Validade da CobrancaAcao.
	 * 
	 * @author Ailton Sousa
	 * @date 13/10/2011
	 * @param idCobrancaAcao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarNumeroDiasVencimentoCobrancaAcao(Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * Pesquisa o Código do Agente Arrecadador pelo ID da COBRANCA_ACAO_ATIVIDADE_COMAND.
	 * 
	 * @author Ailton Sousa
	 * @date 13/10/2011
	 * @param idComandoCobranca
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short pesquisarCodigoAgenteArrecadadorPorComandoCobranca(Integer idComandoCobranca) throws ErroRepositorioException;

	/**
	 * [UC3021] – Processar Pagamento com Boleto Bancário
	 * [SB0001] – Processar Pagamento de Documento de Cobrança
	 * Este método obtém informações sobre os itens do documento de cobrança para posteriormente ser
	 * gerado o pagamento para esses itens
	 * 
	 * @author Anderson Lima
	 * @date 06/10/2011
	 */
	public Collection pesquisarItensCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * [UC3019] Identificar Cobrança Bancária com Negociação.
	 * Retorna as guias de pagamento prestação que terão a data de vencimento da primeira prestação
	 * atualizadas.
	 * 
	 * @author Ailton Sousa
	 * @date 19/10/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiaPagamentoPrestacaoComNegociacao() throws ErroRepositorioException;

	/**
	 * [UC3019] Identificar Cobrança Bancária com Negociação.
	 * 
	 * @author Ailton Sousa
	 * @date 19/10/2011
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param idDebitoTipo
	 * @param idItemLancamento
	 * @return GuiaPagamentoPrestacao
	 * @throws ErroRepositorioException
	 */
	public GuiaPagamentoPrestacao obterGuiaPagamentoPrestacao(Integer idGuiaPagamento, Short numeroPrestacao, Integer idDebitoTipo,
					Integer idItemLancamento) throws ErroRepositorioException;

	/**
	 * Retorna uma CobrancaAcaoAtividadeComando para o seu id.
	 * 
	 * @date 13/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaAcaoAtividadeComando
	 * @return CobrancaAcaoAtividadeComando
	 * @throws ErroRepositorioException
	 */
	public CobrancaAcaoAtividadeComando pesquisarCobrancaAcaoAtividadeComando(int idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * Retorna ArrecadadorContrato dado um idCobrancaAcaoAtividadeComando.
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaAcaoAtividadeComando
	 * @return ArrecadadorContrato
	 * @throws ErroRepositorioException
	 */
	public ArrecadadorContrato pesquisarContratoArrecadadorPorCobrancaAtividadeComando(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * Retorna último Sequencial boleto bancário.
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaAcaoAtividadeComando
	 * @return Ultimo sequencial
	 * @throws ErroRepositorioException
	 */
	public Integer retornaUltimoSequencialBoleto(Integer idCobrancaAcaoAtividadeComando) throws ErroRepositorioException;

	/**
	 * Retorna os documentos de cobrança vigentes gerados pelo comando de cobrança bancária e sem
	 * boleto bancário gerado
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaAcaoAtividadeComando
	 * @return CobrancaDocumento
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumento> retornaCobrancaDocumentoVigentesGeradosComandoCobranca(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * Para cada conta da lista de itens do documento de cobrança
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaDocumento
	 * @return Collection<Conta>
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> recuperarContasCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Retorna ArrecadadorContrato dado um idGuiaPagamento.
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idGuiaPagamento
	 * @return ArrecadadorContrato
	 * @throws ErroRepositorioException
	 */
	public ArrecadadorContrato pesquisarContratoArrecadadorPorGuiaPagamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Retorna prestações da guia de pagamento dado um idGuiaPagamento.
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idGuiaPagamento
	 * @return ArrecadadorContrato
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarGuiaPagamentoPrestacaoBoletoBancario(Integer idGuiaPagamento, Integer numeroPrestacaoInicialGuia)
					throws ErroRepositorioException;

	/**
	 * Pesquisar Boleto Bancario Original.
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idGuiaPagamento
	 * @return ArrecadadorContrato
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarBoletoBancarioOriginal(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Retorna ArrecadadorContrato dado um idCobrancaAcaoAtividadeComando.
	 * 
	 * @date 18/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaAcaoAtividadeComando
	 * @return ArrecadadorContrato
	 * @throws ErroRepositorioException
	 */
	public Arrecadador pesquisarArrecadadorPorCobrancaAtividadeComando(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * Retorna Arrecadador dado um idGuiaPagamento.
	 * 
	 * @date 18/10/2011
	 * @author Péricles Tavares
	 * @param idGuiaPagamento
	 * @return ArrecadadorContrato
	 * @throws ErroRepositorioException
	 */
	public Arrecadador pesquisarArrecadadorPorGuiaPagamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Retorna os ação de cobrança para o idCobrancaAcaoAtividadeComando
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaAcaoAtividadeComando
	 * @return CobrancaDocumento
	 * @throws ErroRepositorioException
	 */
	public CobrancaAcao retornaCobrancaAcao(Integer idCobrancaAcaoAtividadeComando) throws ErroRepositorioException;

	/**
	 * Para cada conta da lista de itens do documento de cobrança
	 * 
	 * @date 17/10/2011
	 * @author Péricles Tavares
	 * @param idCobrancaDocumento
	 * @return CobrancaDocumentoItem
	 * @throws ErroRepositorioException
	 */
	public Conta recuperarContaCobrancaDocumentoId(Integer idConta) throws ErroRepositorioException;

	/**
	 * @param idArrecadacaoForma
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarBancoPorArrecadacaoForma(Integer idArrecadacaoForma) throws ErroRepositorioException;

	/**
	 * @param anoMesReferencia
	 * @param colecaoidsBancos
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarBoletoBancarioMovimento(Integer anoMesReferencia, Collection colecaoidsBancos)
					throws ErroRepositorioException;

	/**
	 * @param cdAgenteArrecadador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Banco pesquisarBanco(Short cdAgenteArrecadador) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarCliente(Integer idImovel) throws ErroRepositorioException;

	/**
	 * @param codigoRegistro
	 * @return
	 * @throws ErroRepositorioException
	 */
	public RegistroCodigo pesquisarIdRegistroCodigo(String codigoRegistro) throws ErroRepositorioException;

	/**
	 * @param idArrecadador
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ArrecadadorContrato pesquisarArrecadadorContrato(Integer idBanco) throws ErroRepositorioException;

	/**
	 * @param idArrecadadorContrato
	 * @param numeroSequencialArquivo
	 * @throws ErroRepositorioException
	 */
	public void atualizarNumeroSequencialArrecadadorContratoBoleto(Integer idArrecadadorContrato, Integer numeroSequencialArquivo)
					throws ErroRepositorioException;

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
					throws ErroRepositorioException;

	/**
	 * Pesquisar Quantidade de Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Integer pesquisarQuantidadeBoletoBancario(BoletoBancarioHelper boletoBancarioHelper, boolean verificarNumeroBoletoCartaCobranca,
					boolean desconsiderarParametros, boolean verificarDocumentoCobranca) throws ErroRepositorioException;

	/**
	 * Pesquisar Boleto Bancário pelo Id
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public BoletoBancario pesquisarBoletoBancarioPeloId(Integer id) throws ErroRepositorioException;

	/**
	 * Pesquisar Quantidade de Movimentação Pendente ou sem Retorno
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Integer pesquisarQuantidadeMovimentacaoPendenteOuSemRetorno(Integer idBoletoBancario) throws ErroRepositorioException;

	/**
	 * Pesquisar Guia de pagamento Prestação pelo Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Collection<GuiaPagamentoPrestacaoHelper> pesquisarGuiasPagamentoPrestacaoBoleto(Integer guiaPagamentoId, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * Pesquisar Contas Pelo Boleo Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Collection<Object[]> pesquisarContasPeloBoletoBancario(Integer idBoletoBancario) throws ErroRepositorioException;

	/**
	 * Pesquisar Quantidade de Boletos Vigentes Filtrando pelo Imóvel
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Integer pesquisarQuantidadeBoletosVigentesDoImovel(Integer idImovel, Integer idBoleto) throws ErroRepositorioException;

	/**
	 * Pesquisar Conteúdo do Arrecadador Movimento Ítem pelo Boleto Bancário
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public String pesquisarConteudoArrecadadorMovimentoItem(Integer idBoletoBancario) throws ErroRepositorioException;

	/**
	 * Pesquisar Boleto Bancário Totalizador
	 * [UC3023] Manter Boleto Bancário
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2011
	 */
	public Collection<BoletoBancarioTotalizadorHelper> pesquisarBoletoBancarioTotalizadorPorImovel(
					BoletoBancarioHelper boletoBancarioHelper, boolean desconsiderarParametros, boolean verificarDocumentoCobranca,
					boolean verificarNumeroBoletoCartaCobranca, int pageOffset) throws ErroRepositorioException;

	/**
	 * @param idBoletoBancarioLancamentoEnvio
	 * @param idBoletoBancarioLancamentoRetorno
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarBoletoBancarioEnvioRetornoSituacao(Integer idBoletoBancarioLancamentoEnvio,
					Integer idBoletoBancarioLancamentoRetorno) throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança.
	 * [SB0008] – Gerar Atividade de Ação de Cobrança para os Imóveis do Comando Precedente.
	 * 1. O sistema seleciona os imóveis que fazem parte do comando precedente cujos documentos
	 * foram entregues.
	 * 
	 * @author Ailton Sousa
	 * @date 26/10/2011
	 * @param idComandoCobrancaPrecedente
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarImoveisComandoPrecedente(Integer idComandoCobrancaPrecedente) throws ErroRepositorioException;

	/**
	 * @author Hugo Lima
	 * @date 02/12/2011
	 * @param resolucaoDiretoria
	 * @return
	 * @throws ControladorException
	 */
	public Collection<ResolucaoDiretoria> consultarResolucaoDiretoriaGrupo(ResolucaoDiretoriaGrupoHelper resolucaoDiretoriaGrupoHelper,
					int pageOffset) throws ErroRepositorioException;

	/**
	 * Obter Cobrança Negociação Atendimento
	 * 
	 * @author Hebert Falcão
	 * @date 25/11/2011
	 */
	public CobrancaNegociacaoAtendimento obterCobrancaNegociacaoAtendimento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Pesquisar Cobrança Negociação Atendimento para Imóvel
	 * 
	 * @author Yara Souza
	 * @date 28/11/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarCobrancaNegociacaoAtendimento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisar Cobrança Negociação Atendimento Não Encerrado
	 * 
	 * @author Hugo Lima
	 * @date 30/11/2011
	 */
	public Collection<CobrancaNegociacaoAtendimento> pesquisarCobrancaNegociacaoAtendimentoNaoEncerrado() throws ErroRepositorioException;

	/**
	 * Pesquisar Quantidade de Ítens Pagos
	 * 
	 * @author Hebert Falcão
	 * @date 19/12/2011
	 */
	public Integer pesquisarQuantidadeDeItensPagos(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * [UC3019] Identificar Cobrança Bancária com Negociação.
	 * Retorna os boletos bancários para pedido de baixa
	 * 
	 * @author Hugo Lima
	 * @date 09/12/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarBoletosBancariosPermissaoPedidoBaixa() throws ErroRepositorioException;

	/**
	 * [UC3032] Emitir Relação Documentos de Cobrança
	 * Retorna os objetos com valores do documentos de dobrança para geração do relatório
	 * 
	 * @author Cinthya Cavalcanti
	 * @date 28/12/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarCobrancaDocumentoRelatorioEmitirRelacaoDocumentos(int idCobrancaAtividadeAcaoComando)
					throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * [SB0016] – Obter Boletos Bancários para Negociação.
	 * O sistema obtém os boletos bancários do imóvel possíveis de serem negociados.
	 * 
	 * @author Ailton Sousa
	 * @date 20/12/2011
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<BoletoBancario> obterBoletosBancariosParaNegociacao(Integer idImovel) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Date obterDataEntradaBoletoBancarioSituacaoHistorico(Integer idBoletoBancario) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Integer obterMenorReferenciaContaBoletoBancario(Integer idBoletoBancario) throws ErroRepositorioException;

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
	 * @throws ErroRepositorioException
	 */
	public Integer obterMaiorReferenciaContaBoletoBancario(Integer idBoletoBancario) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * [FS0016] – Verificar se usuário possui autorização para utilizar a RD
	 * 
	 * @author Ailton Sousa
	 * @date 27/12/2011
	 * @param idUsuario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ResolucaoDiretoria> pesquisarResolucaoDiretoriaPermitidaAoUsuario(Integer idUsuario) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * Verifica se a conta está associada ao boleto bancário da negociação.
	 * 
	 * @author Ailton Sousa
	 * @date 18/01/2012
	 * @param idBoletoBancario
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean isContaAssociadaAoBoletoBancario(Integer idBoletoBancario, Integer idConta) throws ErroRepositorioException;

	/**
	 * Pesquisar Boleto Bancário Ação Cobrança Sem Entrada para pedido de baixa
	 * [UC3019] Identificar Cobrança com Negociação
	 * 
	 * @author Hugo Lima
	 * @date 23/01/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<BoletoBancario> pesquisarBoletosBancariosSemEntradaPermissaoPedidoBaixa() throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * 
	 * @param idResolucaoDiretoria
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<ParcelamentoSituacaoEspecial> verificarRDComRestricao(Integer idResolucaoDiretoria) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos.
	 * 
	 * @param idLocalidade
	 * @param intervalorParcelamentoInicial
	 * @param intervalorParcelamentoFinal
	 * @return
	 */
	public Collection<ParcelamentoSituacaoEspecial> pesquisarParcelamentoSituacaoEspecialPorLocalidade(Integer idLocalidade,
					Integer intervalorParcelamentoInicial, Integer intervalorParcelamentoFinal) throws ErroRepositorioException;

	/**
	 * [UC0111] Iniciar Processo
	 * [SB0007] – Obter Dados Complementares do Comando de Cronograma de Cobrança
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosComplementaresComandoCronogramaCobranca(Integer idComandoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC0111] Iniciar Processo
	 * [SB0008] – Obter Dados Complementares do Comando Eventual de Cobrança
	 * 
	 * @author Hugo Lima
	 * @date 28/02/2012
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarDadosComplementaresComandoEventualCobranca(Integer idComandoFaturamento) throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Consultar os parcelamentos ativos do imóvel recebido, realizados com forma de cobrança em
	 * conta e com prestações a serem cobradas de tipo de lançamento contábil diferente de Juros de
	 * Parcelamento
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public Collection<Parcelamento> pesquisarParcelamentosAtivo(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Verifica se o imóvel possua multa por descumprimento para a referência do parcelamento
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public boolean verificarExistenciaMultaPorDescumprimento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Selecionar as prestações que não sejam referentes ao tipo de lançamento contábil Juros de
	 * Parcelamento e que estejam vencidos
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public Integer obterQuantidadePrestacoesVencidas(Integer idParcelamento, Integer fatorReducao) throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Obter débito a cobrar ativo associado a um parcelamento e que tenha prestações a serem
	 * cobradas
	 * 
	 * @author Hebert Falcão
	 * @date 02/03/2012
	 */
	public Collection<DebitoACobrar> obterDebitoACobrarAtivoAssociadoParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * [UC0216] Calcular Acréscimo por Impontualidade
	 * 
	 * @Autor: Yara Souza
	 * @Date:03/04/2012
	 */
	public HashMap<Integer, IndicesAcrescimosImpontualidade> pesquisarIndiceAcrescimoImpontualidade(Integer anoMesDataVencimento,
					Integer anoMesDataFinal) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Obtém as Opções de Parcelamento do Débito do Imóvel
	 * [SB0002] Obter Opções Parcelamento
	 * 
	 * @param referencia
	 * @return
	 * @throws ErroRepositorioException
	 */

	public BigDecimal obterMediaFatorAtualizacaoMonetaria() throws ErroRepositorioException;

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
					String idTipoCategoria, String[] categoria, String idSubCategoria) throws ErroRepositorioException;

	/**
	 * Consulta as quitações de débitos anuais pelo anoBase
	 * 
	 * @author Josenildo Neves
	 * @date 07/05/2012
	 */
	public Collection<QuitacaoDebitoAnual> consultarQuitacaoDebitoAnual(String anoBase) throws ErroRepositorioException;

	// /**
	// * Guias de pagamento de parcelamento de cobrança bancária com boleto gerado e válido
	// *
	// * @author Hebert Falcão
	// * @date 07/05/2012
	// */
	// public boolean
	// verificarGuiaPagamentoParcelamentoCobrancaBancariaComBoletoGeradoValido(Integer
	// idGuiaPagamento, Short numeroPrestacoes)
	// throws ErroRepositorioException;

	// /**
	// * Guias de pagamento de parcelamento de cobrança bancária pendentes de geração do boleto
	// *
	// * @author Hebert Falcão
	// * @date 07/05/2012
	// */
	// public boolean
	// verificarGuiaPagamentoParcelamentoCobrancaBancariaPendentesGeracaoBoleto(Integer
	// idGuiaPagamento, Short numeroPrestacoes)
	// throws ErroRepositorioException;

	/**
	 * Verifica se o débito do imóvel é válido para a agência virtual
	 * 
	 * @author Josenildo Neves
	 * @date 237/05/2012
	 */
	public boolean verificarDebitoImovelValidoAgenciaVirtual(Integer idConta) throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param idDocumentoTipo
	 * @param numDiasValidade
	 * @return
	 * @throws ErroRepositorioException
	 * @author Yara Souza
	 * @date 18/05/2012
	 */
	public Integer pesquisarDocumentoCobrancaNoPeriodoDeValidade(Integer idImovel, Integer idDocumentoTipo, Integer numDiasValidade)
					throws ErroRepositorioException;

	/**
	 * [UC0476] Emitir Documento de Cobrança - Ordem Corte Modelo 3
	 * 
	 * @author Hugo Lima
	 * @data 07/06/2012
	 * @param CobrancaDocumento
	 * @return Collection<CobrancaDocumento>
	 */
	public Collection pesquisarCobrancaDocumentoArquivoTXTModelo3(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando)
					throws ErroRepositorioException;

	/**
	 * [UC0476] Emitir Documento de Cobrança - Ordem Corte Modelo 3 e Ordem Corte Modelo 5
	 * Método que obtem a data de apresentacao do corte emitido previamente ao mesmo
	 * 
	 * @author Hugo Lima
	 * @data 11/06/2012
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date obterDataApresentacaoAvisoCorte(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0476] Emitir Documento de Cobrança - Ordem Corte Modelo 5
	 * Método que obtem a informação da Notificação 1 Emitida em: cbdo_tmemissao
	 * 
	 * @author André Lopes
	 * @data 11/06/2012
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date obterDataEmissaoApresentacaoOrdemCorte(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Método que busca o setor comercial por cobranca documento.
	 * 
	 * @param idCobrancaDocumento
	 *            id a ser informado.
	 */
	public Object[] pesquisarSetorComercialPorCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Método que obtém total de descrições em Ação de Cobrança a partir de um setor
	 * 
	 * @author José Cláudio
	 * @param cdSetorComercial
	 *            codigo a ser informado.
	 */
	public Integer pesquisarTotalDescricoesCobrancaAcaoPorSetor(Integer cdSetorComercial) throws ErroRepositorioException;

	/**
	 * Método que obtém total de descrições em Ação de Cobrança a partir de uma localidade
	 * 
	 * @author José Cláudio
	 * @param idLocalidade
	 *            id a ser informado.
	 */
	public Integer pesquisarTotalDescricoesCobrancaAcaoPorLocalidade(Integer idLocalidade) throws ErroRepositorioException;

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
					throws ErroRepositorioException;

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
					Integer cobrancaAcaoAtividadeComandoId, Integer idCobrancaAcao) throws ErroRepositorioException;

	/**
	 * [UC0349] - Emitir documento de cobrança
	 * Retorna os itens de documento de cobrança ordenados pela referência da conta
	 * 
	 * @created 11/06/2012
	 * @author Luciano Galvao
	 */
	public Collection pesquisarCobrancaDocumentoItens(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Aviso de corte Arquivo TXT
	 * Seleciona os ids dos documentos de cobrança para a geração Modelo 3
	 * 
	 * @return Collection<Integer>
	 */
	public Collection pesquisarCobrancaDocumentoIds(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando)
					throws ErroRepositorioException;

	/**
	 * Obter o indicador de cobranca administrativa de conta
	 * 
	 * @author Hugo Lima
	 * @data 11/06/2012
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Short obterIndicadorCobrancaAdministrativaConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC3062] Validar Autorização Acesso Imóvel - Cobrança Administrativa
	 * Verifica se existem ocorrencias de uma empresa na tablela cobranca_contrato
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean existeEmpresaCobrancaContrato(Integer idEmpresa) throws ErroRepositorioException;

	/**
	 * [UC3062] Validar Autorização Acesso Imóvel - Cobrança Administrativa
	 * Verifica se existem ocorrencias de um imovel em situacao de cobranca administrativa
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean existeProcessoCobrancaAdministrativa(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3062] Validar Autorização Acesso Imóvel - Cobrança Administrativa
	 * Verifica se existem ocorrencias de uma empresa em situacao de cobranca administrativa
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public boolean existeEmpresaProcessoCobrancaAdministrativa(Integer idEmpresa, Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0026] – Verificar Débito em Cobrança Administrativa - Retirar Contas
	 * Retorna a lista de ids de contas em cobrança administrativa de um imóvel onde não existe
	 * ocorrência da empresa passada como parametro
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param idEmpresa
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterIdsContasCobrancaAdministrativaEmpresaDiferente(Integer idEmpresa, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0026] – Verificar Débito em Cobrança Administrativa - Retirar Guias
	 * Retorna a lista de ids de guias de pagamento em cobrança administrativa de um imóvel onde não
	 * existe ocorrência da empresa passada como parametro
	 * 
	 * @author Hugo Lima
	 * @date 31/07/2012
	 * @param idEmpresa
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection obterIdsGuiasPagamentoCobrancaAdministrativaEmpresaDiferente(Integer idEmpresa, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * Este caso de uso permite Consultar e/ou Retirar Imóveis da Cobrança Administrativa.
	 * 
	 * @created 30/07/2012
	 * @author Josenildo Neves.
	 * @return Collection<ImovelCobrancaSituacaoAdministrativaHelper>
	 */
	public Collection<ImovelCobrancaSituacaoAdministrativaHelper> consultarRetirarImovelCobrancaAdministrativa(Integer idLocalidade,
					Integer cdSetorComercial, Integer idImovel, Integer numeroPaginas) throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * Este caso de uso permite Consultar e/ou Retirar Imóveis da Cobrança Administrativa.
	 * 
	 * @created 30/07/2012
	 * @author Josenildo Neves.
	 * @return Integer
	 */
	public Integer consultarRetirarImovelCobrancaAdministrativaCount(Integer idLocalidade, Integer cdSetorComercial, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * [SB0001] – Exibir dados da Cobrança Administrativa do Imóvel.
	 * 
	 * @created 31/07/2012
	 * @author Josenildo Neves.
	 * @return ImovelCobrancaSituacaoAdministrativaHelper
	 */
	public ImovelCobrancaSituacaoAdministrativaHelper consultarRetirarImovelCobrancaAdministrativaDadosRemuneracao(
					Integer idImovelCobrancaSituacao) throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * [SB0003] – Exibir dados da Cobrança Administrativa do Imóvel. Item 1.
	 * 
	 * @created 31/07/2012
	 * @return ImovelCobrancaMotivoRetirada
	 */
	public Collection<ImovelCobrancaMotivoRetirada> consultarImovelCobrancaMotivoRetirada() throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Retirar Imovel Cobranca Administrativa
	 * [SB0003] – Exibir dados da Cobrança Administrativa do Imóvel. Item 1.2.
	 * 
	 * @created 31/07/2012
	 * @return ImovelCobrancaMotivoRetirada
	 */
	public Collection<CobrancaDocumentoItem> obterDocumentosCobrancaPendentes(Integer idImovelCobrancaSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * Obtém os documentos de cobrança com o CBDO_ID da tabela COBRANCA_DOCUMENTO com CAAC_ID da
	 * tabela COBRANCA_ATIVIDADE_ACAO_CRONOGRAMA
	 * 
	 * @author Anderson Italo
	 * @date 13/07/2012
	 */
	public Collection<CobrancaDocumento> pesquisarCobrancaDocumentoParaGeracaoResumoEventual(Integer idCobrancaAtividadeAcaoComando)
					throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * Obtém os itens associados ao documento de cobrança
	 * 
	 * @author Anderson Italo
	 * @date 13/07/2012
	 */
	public Collection<Object[]> pesquisarSituacaoDebitoItensParaGeracaoResumoEventual(Integer idCobrancaDocumento)
					throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * Obtém os parcelamentos do documento
	 * 
	 * @author Anderson Italo
	 * @date 13/07/2012
	 */
	public Collection<Parcelamento> pesquisarParcelamentosResumoCobrancaAcaoEventual(Integer idCobrancaDocumento)
					throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * [SB0004] – Tratar Cobrança Administrativa
	 * obtém os valores de remuneração agrupando por tipo de documento e percentual de remuneração
	 * 
	 * @author Anderson Italo
	 * @date 17/07/2012
	 */
	public Collection<Object[]> pesquisarValoresImovelCobrancaAdministrativaItem(Integer idCobrancaAcaoAtividadeComando, Integer IdImovel)
					throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * [SB0004] – Tratar Cobrança Administrativa
	 * obtém os itens dos documentos de cobrança do imóvel para um determinado comando
	 * 
	 * @author Anderson Italo
	 * @date 17/07/2012
	 */
	public Collection<CobrancaDocumentoItem> pesquisarItensDocumentoCobranca(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * [SB0004] – Tratar Cobrança Administrativa
	 * obtém os itens do parcelamento
	 * 
	 * @author Anderson Italo
	 * @date 17/07/2012
	 */
	public Collection<ParcelamentoItem> pesquisarItensPorParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @author Anderson Italo
	 * @date 20/07/2012
	 */
	public CobrancaAcaoAtividadeComando pesquisarCobrancaAcaoAtividadeComandoPorId(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * [SB0003 – Identificar Situação do Débito do Item do Documento de Cobrança]
	 * Atualiza o item do documento de cobrança - Com valor igual a “não” (2)
	 * 
	 * @author Anderson Italo
	 * @date 20/07/2012
	 */
	public void atualizarIndicadorAtualizadoCobrancaDocumentoItensPorDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Obtém um ResumoCobrancaAcaoEventual
	 * 
	 * @author Anderson Italo
	 * @date 18/08/2012
	 * @throws ErroRepositorioException
	 */
	public ResumoCobrancaAcaoEventual pesquisarResumoCobrancaAcaoEventual(ResumoCobrancaAcaoEventual resumoCobrancaAcaoEventual)
					throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * Obtém um ResumoCobrancaAcaoSituacao
	 * 
	 * @author Anderson Italo
	 * @date 20/07/2012
	 */
	public ResumoCobrancaAcaoSituacao pesquisarResumoCobrancaAcaoSituacao(Integer idResumoCobrancaAcaoEventual, Integer idDocumentoTipo,
					Integer idCobrancaDebitoSituacao) throws ErroRepositorioException;

	/**
	 * [UC00614] Gerar Resumo das Ações de Cobrança Eventuais
	 * Obtém um ResumoCobrancaAcaoRemuneracao
	 * 
	 * @author Anderson Italo
	 * @date 18/08/2012
	 */
	public ResumoCobrancaAcaoRemuneracao pesquisarResumoCobrancaAcaoRemuneracao(Integer idResumoCobrancaAcaoEventual,
					Integer idDocumentoTipo, BigDecimal percentualRemuneracao) throws ErroRepositorioException;

	/**
	 * Obter os Ids de parcelamento feitos por usuario não pertencentes a empresa passada no
	 * parametro
	 * 
	 * @author Hugo Lima
	 * @data 15/08/2012
	 * @param idEmpresa
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterIdsParcelamentoEmpresaDiferente(Integer idEmpresa, Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0252] Consultar Parcelamentos de Débitos
	 * [SB0003] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
	 * da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 23/08/2012
	 */
	public Collection<DebitoACobrar> pesquisarItensDebitosACobrarPorParcelamento(Integer idParcelamento) throws ErroRepositorioException;

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
					throws ErroRepositorioException;

	/**
	 * Cosulta o total de remunerações de cobrança administrativa que atendam ao filtro informado.
	 * 
	 * @param parametro
	 *            {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * @return
	 */
	public int consultarQuantidadeRemuneracaoCobrancaAdministrativa(
					RelatorioRemuneracaoCobrancaAdministrativaHelper remuneracaoCobrancaAdministrativaHelper)
					throws ErroRepositorioException;

	/**
	 * Consulta os dados estatisticos de RA filtrados pelo
	 * {@link RelatorioEstatisticoRegistroAtendimentoHelper}
	 * 
	 * @param {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> consultarDadosRemuneracaoCobrancaAdministrativa(RelatorioRemuneracaoCobrancaAdministrativaHelper helper)
					throws ErroRepositorioException;

	/**
	 * Consulta os dados da conta para o Relatório de Remuneração da Cobrança Administrativa
	 * {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * 
	 * @param {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * @return Object[]
	 */
	public Object[] consultarContaRemuneracaoCobrancaAdministrativa(Integer contaId, BigDecimal valorRemuneracao)
					throws ErroRepositorioException;

	/**
	 * Consulta os dados da guia de pagamento para o Relatório de Remuneração da Cobrança
	 * Administrativa {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * 
	 * @param {@link RelatorioRemuneracaoCobrancaAdministrativaHelper}
	 * @return Object[]
	 */
	public Object[] consultarGuiaPagamentoRemuneracaoCobrancaAdministrativa(Integer guiaPagamentoId, BigDecimal valorRemuneracao,
					Integer numeroPrestacao) throws ErroRepositorioException;

	/**
	 * Método responsável por verificar se uma conta esta na carteira de cobrança de outra empresa
	 * 
	 * @param idConta
	 * @param idUsuario
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	Boolean isContaCobrancaAdministrativaEmpresaDiversa(Integer idConta, Integer idImovel, Integer idUsuario, Integer idEmpresa)
					throws ErroRepositorioException;

	/**
	 * Método responsável por verificar se uma guia de pagamento esta na carteira de cobrança de
	 * outra empresa
	 * 
	 * @param idGuiaPgt
	 * @param numeroPrestacao
	 * @param idImovel
	 * @param idUsuario
	 * @param idEmpresa
	 * @return
	 * @throws ErroRepositorioException
	 */
	Boolean isGuiaPgtPrestacaoCobrancaAdministrativaEmpresaDiversa(Integer idGuiaPgt, Short numeroPrestacao, Integer idImovel,
					Integer idUsuario, Integer idEmpresa) throws ErroRepositorioException;

	/**
	 * [UC3070] Filtrar Imóvel Cobrança Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 07/09/2012
	 */
	public Collection<Empresa> pesquisarEmpresaCobrancaAdministrativa(Collection<Integer> idsEmpresa) throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Imóvel Cobrança Situação pelo Id
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public ImovelCobrancaSituacao pesquisarImovelCobrancaSituacaoPeloId(Integer id) throws ErroRepositorioException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0005] Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * Pesquisar Última Cobrança Administrativa do Imóvel
	 * 
	 * @author Saulo Lima
	 * @date 25/07/2013
	 */
	public CobrancaAcaoAtividadeComando pesquisarUltimaCobrancaAdministrativaImovel(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Totalizar Cobrança Documento Ítem filtrando pelo Id do Comando
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> totalizarCobrancaDocumentoItemPeloComando(Integer idCobrancaAcaoAtividadeComando, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Contas pelo Id do Comando
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> pesquisarContasPeloComandoParaCobrancaAdministrativa(Integer idCobrancaAcaoAtividadeComando,
					Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Guias pelo Id do Comando
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> pesquisarGuiasPeloComandoParaCobrancaAdministrativa(Integer idCobrancaAcaoAtividadeComando, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Totalizar Imovel Cobrança Administrativa pelo Id da Situação de Cobrança do Imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> totalizarImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * <<Inclui>> [UC3070 - Filtrar Imóvel Cobrança Administrativa]
	 * Pesquisar Imóvel em Cobrança Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 16/09/2012
	 */
	public Collection<ImovelCobrancaSituacao> pesquisarImovelCobrancaAdministrativa(FiltroImovelCobrancaAdministrativaHelper filtro,
					int pageOffset) throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Contas Cobrança Administrativa pelo Id da Situação de Cobrança do Imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> pesquisarContasImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
	 * Pesquisar Guias Cobrança Administrativa pelo Id da Situação de Cobrança do Imóvel
	 * 
	 * @author Hebert Falcão
	 * @date 15/09/2012
	 */
	public Collection<Object[]> pesquisarGuiasImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * <<Inclui>> [UC3070 - Filtrar Imóvel Cobrança Administrativa]
	 * Pesquisar Imóvel em Cobrança Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 16/09/2012
	 */
	public Integer pesquisarQuantidadeImovelCobrancaAdministrativa(FiltroImovelCobrancaAdministrativaHelper filtro)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Consultar Imóvel Cobrança Administrativa
	 * [SB0002] - Consultar Dados do Contrato da Empresa
	 * 
	 * @author Anderson Italo
	 * @date 17/09/2012
	 */
	public Collection<CobrancaContrato> pesquisarCobrancaContratoPorEmpresa(Integer idEmpresa) throws ErroRepositorioException;

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
	public Object[] calcularAcrescimoPorImpontualidadeBancoDeDados(int anoMesReferenciaDebito, Date dataVencimento, Date dataPagamento,
					BigDecimal valorDebito, BigDecimal valorMultasCobradas, short indicadorMulta, String anoMesArrecadacao,
					Integer idConta, Date dataEmissaoDocumento, Short indicadorEmissaoDocumento, Date dataBaseDeCalculo)
					throws ErroRepositorioException;

	/**
	 * Método responsável por pesquisar um documento de cobrança
	 * 
	 * @param idImovel
	 * @param idCobrancaAcaoSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	List<CobrancaDocumento> pesquisarCobrancaDocumentoImovelProcessoCorte(Integer idImovel, Integer idCobrancaAcaoSituacao)
					throws ErroRepositorioException;

	/**
	 * Método responsavel por verificar se existe uma os de corte para um determinado imovel
	 * 
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	Boolean existeOsCortePendente(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Consultar os parcelamentos ativos do imóvel recebido, realizados com forma de cobrança em
	 * conta e com prestações a serem cobradas de tipo de lançamento contábil diferente de Juros de
	 * Parcelamento
	 */
	public Collection pesquisarDebitoACobrarDeParcelamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 */
	public Collection<Object[]> pesquisarDebitoACobrarParcelamentoHelper(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Verifica se o imóvel possua multa por descumprimento para a referência do debito a cobrar
	 */
	public boolean verificarExistenciaMultaPorDescumprimentoPorDebitoACobrar(Integer anoMesReferenciaDebito, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC3042] Realizar Arrasto de Parcelamento
	 * Selecionar as prestações que não sejam referentes ao tipo de lançamento contábil Juros de
	 * Parcelamento e que estejam vencidos
	 * 
	 * @author Yara Souza
	 * @date 27/09/2012
	 */
	public Integer obterQuantidadePrestacoesVencidas(Integer anoMesReferenciaDebito, Integer idImovel, Integer fatorReducao,
					Short numeroPrestacoesCobradas) throws ErroRepositorioException;

	public Collection<DebitoACobrar> obterDebitoACobrarAtivoAssociado(Integer anoMesReferenciaDebito, Integer numeroPrestacaoDebito,
					Integer numeroPrestacaoCobradas, Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisar rotas dos imóveis gerados no comando precedente
	 * 
	 * @author Hebert Falcão
	 * @date 12/10/2012
	 */
	public Collection<Rota> pesquisarRotasDoComandoPrecedente(Integer idComandoCobrancaPrecedente) throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * Verificar se o imóvel já está em cobrança bancária
	 * 
	 * @author Hebert Falcão
	 * @date 13/10/2012
	 */
	public boolean isImovelEmCobrancaBancaria(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3019] Identificar Cobrança Bancária com Negociação.
	 * Seleciona os imóveis com boletos de documento de cobrança, baixados, onde o pedido de baixa
	 * foi feito pelo usuário batch, com itens pendentes e sem novo boleto de documento de cobrança
	 * para o imóvel com algum desses itens
	 * 
	 * @author Hebert Falcão
	 * @date 13/10/2012
	 */
	public Collection pesquisarImoveisComBoletosBaixados(Integer idUsuarioBatch) throws ErroRepositorioException;

	/**
	 * Pesquisar Resolução de Diretoria Parâmetros Pagamento À Vista
	 * 
	 * @author Hebert Falcão
	 * @date 31/10/2012
	 */
	public ResolucaoDiretoriaParametrosPagamentoAVista pesquisarResolucaoDiretoriaParametrosPagamentoAVista(Integer idResolucaoDiretoria,
					Date dataPagamento) throws ErroRepositorioException;

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * Pesquisar mensagem para pagamento à vista
	 * 
	 * @author Hebert Falcão
	 * @date 31/10/2012
	 */
	public Object[] pesquisarMensagemExtratoParcelamentoPagamentoAVista(Integer idResolucaoDiretoria, Date dataEmissao)
					throws ErroRepositorioException;

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * Pesquisar Contas do Documento de Cobrança
	 * 
	 * @author Hebert Falcão
	 * @date 01/11/2012
	 */
	public Collection<Conta> pesquisarContasCobrancaDocumento(Integer idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * [UC0444] Gerar e Emitir Extrato de Débito
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * Pesquisar Contas do Documento de Cobrança
	 * 
	 * @author Hebert Falcão
	 * @date 01/11/2012
	 */
	public Collection<GuiaPagamentoPrestacao> pesquisarGuiasPagamentoPrestacaoCobrancaDocumento(Integer idCobrancaDocumento)
					throws ErroRepositorioException;

	/**
	 * Pesquisar Resolução de Diretoria Parâmetros Valor da Entrada
	 * 
	 * @author Hebert Falcão
	 * @date 31/10/2012
	 */
	public ResolucaoDiretoriaParametrosValorEntrada pesquisarResolucaoDiretoriaParametrosValorEntrada(Integer idResolucaoDiretoria,
					Date dataNegociacao) throws ErroRepositorioException;

	/**
	 * [UC3019] Identificar Cobrança Bancária com Negociação.
	 * [SB0007] Trata cobrança bancária com pagamento da primeira prestação e sem boleto.
	 * Retorna as guias de pagamento prestação que estão pagas.
	 * 
	 * @author Hebert Falcão
	 * @date 22/11/2012
	 */
	public Collection pesquisarGuiaPagamentoPrestacaoComNegociacaoPaga() throws ErroRepositorioException;

	/**
	 * Guias de pagamento de parcelamento de cobrança bancária
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * [FS0004] - Verificar existência de guia de parcelamento de cobrança bancária
	 * 
	 * @author Josenildo Neves
	 * @date 22/11/2012
	 */
	public boolean verificarGuiaPagamentoParcelamentoCobrancaBancaria(Integer idGuiaPagamento, Short numeroPrestacoes)
					throws ErroRepositorioException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * Retornar ids de parcelamentos ativos na referência
	 * 
	 * @author Hebert Falcão
	 * @date 03/12/2012
	 */
	public Collection<Integer> retornarIdsDeParcelamentosAtivosNaReferencia(Integer idImovel, Integer anoMesFaturamentoAtual)
					throws ErroRepositorioException;

	/**
	 * Consulta a quantidade de registros de para o relatório de Liquidos Recebíveis.
	 * UC3081-GerarRelatoriodeLiquidosRecebiveis
	 * 
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 */
	public int consultarQuantidadeRegistrosDeLiquidosRecebiveis(Date dataPagamentoInicial, Date dataPagamentoFinal)
					throws ErroRepositorioException;

	/**
	 * Consulta os registros de para o relatório de Liquidos Recebíveis analitico.
	 * UC3081-GerarRelatoriodeLiquidosRecebiveis
	 * 
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 */
	public Collection<Object[]> consultarRegistrosDeLiquidosRecebiveisAnalitico(Date dataPagamentoInicial, Date dataPagamentoFinal)
					throws ErroRepositorioException;

	/**
	 * Consulta os registros de para o relatório de Liquidos Recebíveis sintético.
	 * UC3081-GerarRelatoriodeLiquidosRecebiveis
	 * 
	 * @param dataPagamentoInicial
	 * @param dataPagamentoFinal
	 */
	public Collection<Object[]> consultarRegistrosDeLiquidosRecebiveisSintetico(Date dataPagamentoInicial, Date dataPagamentoFinal)
					throws ErroRepositorioException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0009] Verificar Contas em Cobrança Bancária
	 * Obter o mais recente boleto bancário da conta em cobrança bancária
	 * 
	 * @author Hebert Falcão
	 * @date 24/11/2012
	 */
	public BoletoBancario obterMaisRecenteBoletoBancarioDaContaEmCobrancaBancaria(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0009] Verificar Contas em Cobrança Bancária
	 * Verificar se a situação do boleto não permite a emissão do extrato
	 * 
	 * @author Hebert Falcão
	 * @date 24/11/2012
	 */
	public boolean verificarSituacaoBoletoNaoPermiteEmissaoExtrato(Integer idBoletoBancario) throws ErroRepositorioException;

	/**
	 * [UC0203] Consultar Débitos
	 * [SB0009] Verificar Contas em Cobrança Bancária
	 * Verificar se a situação do boleto não permite a emissão do extrato
	 * 
	 * @author Hebert Falcão
	 * @date 24/11/2012
	 */
	public Collection<BoletoBancario> pesquisarBoletoBaixadoEProtestadoPelaGuiaDePagamento(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * [SB0003] – Processar Pagamento de Documento de Cobrança
	 * Pesquisar Boletos Baixados e Protestados filtrando pelo Id
	 * 
	 * @author Hebert Falcão
	 * @date 26/11/2012
	 */
	public Collection<BoletoBancario> pesquisarBoletoBaixadoEProtestadoPeloId(Collection<Integer> idsBoletoBancario)
					throws ErroRepositorioException;

	/**
	 * [UC0259] – Processar Pagamento com Código de Barras
	 * [SB0003] – Processar Pagamento de Documento de Cobrança
	 * Obter os boletos bancários das contas em cobrança bancária
	 * 
	 * @author Hebert Falcão
	 * @date 26/11/2012
	 */
	public Collection<Integer> obterBoletosBancariosDasContasEmCobrancaBancaria(Collection<Integer> idsConta, Integer idCobrancaDocumento,
					Date dataEmissaoDocumento) throws ErroRepositorioException;

	/**
	 * [UC3023] Manter Boleto Bancário
	 * [SB000B] Apresentar Boletos Não Agrupados
	 * Verifica boleto agregador de contas com situação correspondente a baixado e protestado
	 * 
	 * @author Hebert Falcão
	 * @date 04/12/2012
	 */
	public boolean verificaExistenciaBoletoAgregadorComSituacaoBaixadoEProtestado(Integer idBoletoBancario) throws ErroRepositorioException;

	/**
	 * Método responsável por obter um cobrança documento gerado pela acao de cobranca
	 * 
	 * @param cobrancaDocumento
	 * @return
	 * @throws ErroRepositorioException
	 */
	CobrancaDocumento obterCobrancaDocumentoGeradoAcaoCobranca(CobrancaDocumento cobrancaDocumento) throws ErroRepositorioException;

	/**
	 * retorna uma coleção com todos os parcelamentos de um determinado imovel
	 * 
	 * @author Ítalo Almeida
	 * @date 12/12/2012
	 */
	public Collection<Parcelamento> consultarImovelParcelamentoDebito(int imovelId) throws ErroRepositorioException;

	/**
	 * [UC3082] Atualizar Item Documento Cobrança
	 * Obter cobrança documento com data imediatamente menor ou igual a data passada
	 * 
	 * @author Hebert Falcão
	 * @date 09/12/2012
	 */
	public CobrancaDocumento obterCobrancaDocumentoComDataImediatamenteMenorOuIgual(Integer idCobrancaAcao, Integer idImovel,
					Date dataCalculada) throws ErroRepositorioException;

	/**
	 * [UC3082] Atualizar Item Documento Cobrança
	 * Verifica se a ação sucessora foi gerada
	 * 
	 * @author Hebert Falcão
	 * @date 09/12/2012
	 */
	public boolean verificaSeAcaoSucessoraFoiGerada(Integer idCobrancaAcao, Integer idImovel, Date dataCalculada)
					throws ErroRepositorioException;

	/**
	 * [UC3089] Atualizar Situação Débito e da Ação dos Avisos Corte e Corte Individual
	 * Obter cobrança documento de aviso de corte e corte, com ítem atualizados
	 * 
	 * @author Hebert Falcão
	 * @date 21/12/2012
	 */
	public Collection<CobrancaDocumento> obterCobrancaDocumentoDeAvisoDeCorteECorteComItemAualizado() throws ErroRepositorioException;

	/**
	 * [UC3089] Atualizar Situação Débito e da Ação dos Avisos Corte e Corte Individual
	 * Verifica a existência de aviso de corte, sem comando, fora do prazo de validade, com situação
	 * da ação "entregue" e com situação do débito "pendente"
	 * 
	 * @date 28/12/2012
	 * @author Hebert Falcão
	 */
	public Collection<CobrancaDocumento> obterAvisoDeCorteSemComandoForaDoPrazoDeValidadeEntegueEPendente() throws ErroRepositorioException;

	/**
	 * [UC3089] Atualizar Situação Débito e da Ação dos Avisos Corte e Corte Individual
	 * Verifica a existência de corte, sem comando, fora do prazo de validade, com situação da ação
	 * "pendente" e com situação do débito "pendente"
	 * 
	 * @date 28/12/2012
	 * @author Hebert Falcão
	 */
	public Collection<CobrancaDocumento> obterCorteSemComandoForaDoPrazoDeValidadePendente() throws ErroRepositorioException;

	/**
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 03/01/2012
	 */
	public Collection<Object[]> obterSomatorioAguaEsgoto(Integer mesAnoReferenciaContabil, String idLocalidade, String idUnidadeNegocio,
					String idGerenciaRegional) throws ErroRepositorioException;

	/**
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 03/01/2012
	 */
	public Collection<Object[]> obterSomatorioValorDebitosCobradosFinanciamentoOuParcelamento(Integer mesAnoReferenciaContabil,
					String idLocalidade, String idUnidadeNegocio, String idGerenciaRegional, boolean opcaoConsultaFinanciamento)
					throws ErroRepositorioException;

	/**
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 03/01/2012
	 */
	public Collection<Object[]> obterSomatorioValorDebitosACobrarFinanciamentoOuParcelamento(Integer mesAnoReferenciaContabil,
					String idLocalidade, String idUnidadeNegocio, String idGerenciaRegional, boolean isFinanciado)
					throws ErroRepositorioException;

	/**
	 * Retorna coleção de Contas a vencer.
	 * Usado em: [UC3084] Gerar Relatório Contas A Receber Corrigido
	 * 
	 * @author André Lopes
	 * @date 28/12/2012
	 */
	public Collection<Object[]> pesquisarContasVencidasParaReajustes(Integer mesAnoReferenciaContabil, String localidade,
					String unidadeNegocio, String gerenciaRegional, boolean isFinanciamento) throws ErroRepositorioException;

	/**
	 * [FS0041] - Verificar existência de guias correspondentes a prestações de parcelamento com
	 * concessão de desconto nos acréscimos
	 * 
	 * @author Yara Souza
	 * @date 19/01/2013
	 */
	public boolean verificarGuiaPagamentoParcelamentoComConcessaoDesconto(Integer idGuiaPagamento) throws ErroRepositorioException;


	/**
	 * [FS0042] - Verificar existência de créditos a realizar correspondentes a desconto nos
	 * acréscimos concedido no parcelamento
	 * 
	 * @author Yara Souza
	 * @date 19/01/2013
	 */
	public boolean verificarCreditoARealizarParcelamentoComConcessaoDesconto(Integer idCreditoARealizar) throws ErroRepositorioException;

	public Collection obterCreditoARealizarParcelamentoComConcessaoDesconto(Integer idParcelamento) throws ErroRepositorioException;

	public Collection obterCreditoRealizadoPorCreditoTipo(Integer idConta, Integer idCreditoTipo) throws ErroRepositorioException;

	public BigDecimal obterValorDescontosAcrescimos(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * Obtém o id da localidade do imóvel relacionado à ordem de serviço, caso exista
	 * 
	 * @author Luciano Galvao
	 * @date 04/03/2013
	 */
	public Integer obterLocalidadeOrdemServico(Integer idOrdemServico) throws ErroRepositorioException;

	/**
	 * Método que insere em batch uma lista de ResumoCobrancaAcaoEventual, setando o id do objeto
	 * com o identificador gerado pelo banco de dados
	 * [UC0614] Gerar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @author Luciano Galvao
	 * @date 14/03/2013
	 */
	public void inserirResumoCobrancaAcaoEventualBatch(Collection<ResumoCobrancaAcaoEventual> colecaoResumoCobrancaAcaoEventual)
					throws ErroRepositorioException;

	/**
	 * @param idImovel
	 * @param idParcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection verificarGuiaPagamentoHistoricoParcelamento(Integer idImovel, Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * @param idParcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public GuiaPagamento obterGuiaPagamentoDoParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * @param idParcelamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public GuiaPagamentoHistorico obterGuiaPagamentoHistoricoDoParcelamento(Integer idParcelamento) throws ErroRepositorioException;


	/**
	 * [UC3019] Identificar Cobrança com Negociação
	 * [SB0007] – Trata cobrança bancária com pagamento da primeira prestação e sem boleto
	 * Verifica se o a situação do boleto permite gerar pedido de baixa
	 * 
	 * @author Carlos Chrystian
	 * @date 12/03/2013
	 */
	public boolean verificaPermissaoGerarPedidoBaixaBoleto(Integer idBoletoBancario) throws ErroRepositorioException;

	/**
	 * [UC0476] Emitir Documento de Cobrança - Ordem Corte Modelo 4 e Ordem Corte Modelo 5
	 * 
	 * @author André Lopes
	 * @data 20/05/2013
	 * @param CobrancaDocumento
	 * @return Collection
	 */
	public Collection pesquisarCobrancaDocumentoArquivoPdfModelo4e5(Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando)
					throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Aviso de corte Aquivo PDF
	 * Seleciona os documento de cobrança
	 * Ordenar por localidade, setor, quadra, lote e sublote
	 * 
	 * @author Josenildo Neves
	 * @data 21/05/2013
	 * @param idCobrancaAcaoCronograma
	 * @param idCobrancaAcaoComando
	 * @return Collection<EmitirArquivoPdfAvisoCorteHelper>
	 */
	public Collection<IEmitirArquivoAvisoOrdemCorteHelper> pesquisarCobrancaDocumentoArquivoPDFAvisoCorteModelo4e5(
					Integer idCobrancaAcaoCronograma, Integer idCobrancaAcaoComando)
					throws ErroRepositorioException;

	/**
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * Consulta os imoveis marcados na cobrança administrativa (a partir da tabela
	 * IMOVEL_COBRANCA_SITUACAO com CBST_ID=4 e ISCB_DTRETIRADACOBRANCA com o valor nulo)
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 */
	public Collection<ImovelCobrancaSituacao> pesquisarImovelCobrancaSituacaoPorCobrancaSituacaoId(Integer cobrancaSituacaoId, Integer qtd)
					throws ErroRepositorioException;

	/**
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * Consulta os itens de cobrança administrativa pendente (ocorrências na tabela
	 * COBRANCA_DOCUMENTO_ITEM com CDST_ID=1 (pendente) e CBDO_ID=(CBDO_ID da tabela
	 * COBRANCA_DOCUMENTO com IMOV_ID=(IMOV_ID da tabela IMOVEL_COBRANCA_SITUACAO) e
	 * CACM_ID=(CACM_ID da tabela IMOVEL_COBRANCA_SITUACAO)))
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 */
	public Collection pesquisarCobrancaDocumentoItensEmCobrancaAdmPendente(Integer imovelId, Integer cobrancaAcaoAtividadeComandoId)
					throws ErroRepositorioException;

	/**
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * Obtém a maior data de situação do débito (CDIT_DTSITUACAODEBITO) da tabela
	 * COBRANCA_DOCUMENTO_ITEM com CBDO_ID=(CBDO_ID da tabela COBRANCA_DOCUMENTO com
	 * IMOV_ID=(IMOV_ID da tabela IMOVEL_COBRANCA_SITUACAO) e CACM_ID=(CACM_ID da tabela
	 * IMOVEL_COBRANCA_SITUACAO)))
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 */
	public Date obterMaiorDataSituacaoDebitoCobrancaDocumentoItem(Integer imovelId, Integer cobrancaAcaoAtividadeComandoId)
					throws ErroRepositorioException;

	/**
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * Consulta as prestações histórico de guias de pagamento a partir da tabela
	 * GUIA_PAGAMENTO_PRESTACAO_HIST com GPAG_ID=(GPAG_ID da tabela GUIA_PAGAMENTO com
	 * GPAG_DTINCLUSAO maior ou igual à 1/02/2013 e IMOV_ID=(IMOV_ID da tabela
	 * IMOVEL_COBRANCA_SITUACAO)) E a partir da tabela GUIA_PAGAMENTO_PRESTACAO_HIST com
	 * GPAG_ID=(GPAG_ID da tabela GUIA_PAGAMENTO_HISTORICO com GPHI_DTINCLUSAO maior ou igual à
	 * 1/02/2013 e IMOV_ID=(IMOV_ID da tabela IMOVEL_COBRANCA_SITUACAO)))
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 */
	public Collection<GuiaPagamentoPrestacaoHistorico> pesquisarGuiaPagamentoPrestacaoHistParaRemunerarCobrancaAdm(Integer imovelId,
					Date dataInclusao) throws ErroRepositorioException;

	/**
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * Atualiza o indicador remunera cobrança administrativa da entidade
	 * GuiaPagamentoPrestacaoHistorico
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 */
	public void atualizarIndicadorRemuneraCobrancaAdm(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico)
					throws ErroRepositorioException;
	
	/**
	 * @param idCobrancaDocumento
	 * @param idSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarItensPorSituacaoDebitoParaGeracaoResumoEventual(Integer idCobrancaDocumento, Integer idSituacao)
					throws ErroRepositorioException;

	/**
	 * @param idCobrancaDocumentoItem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public CobrancaDocumentoItem pesquisarCobrancaDocumentoItem(Integer idCobrancaDocumentoItem) throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarIdParcelamento(Integer idConta, Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * @throws ErroRepositorioException
	 */
	public Collection<Empresa> consultarEmpresaCobrancaAdministrativa() throws ErroRepositorioException;

	/**
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal consultarPercentualRemuneracaoCobrancaAdministrativa(int idCobrancaAcaoAtividadeComando, int idDocumentoCobranca)
					throws ErroRepositorioException;

	/**
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CobrancaDocumento> obterCobrancaDocumentoPorCACM(Integer idCobrancaAcaoAtividadeComando)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * 1.3.1. Caso a situação atual da cobrança administrativa seja “Pendente”
	 * 
	 * @author Josenildo Neves
	 * @date 06/08/2013
	 * @return boolean retorno
	 * @throws ErroRepositorioException
	 */
	public boolean verificarOcorrenciaCobrancaAdministrativaPosterior(Integer idImovelCobrancaSituacao, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * 1.3.1.1. Seleciona as Contas Remuneráveis Marcadas na Cobrança Administrativa
	 * 
	 * @param idImovel
	 * @param idCobrancaAcaoAtividadeComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<Conta> selecionarContasRemuneraveisMarcadasNaCobrancaAdministrativa(Integer idImovel,
					Integer idCobrancaAcaoAtividadeComando) throws ErroRepositorioException;
	
	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * 1.3.1.2.	Seleciona as Contas Remuneráveis Com Vencimento a partir da Entrada do Imóvel na Cobrança Administrativa.
	 * 
	 * @param dataImplantacaoCobranca
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<Conta> selecionarContasRemuneraveisComVencimentoAPartirEntradaImovelNaCobrancaAdministrativa(Date dataImplantacaoCobranca,
					Integer idImovel, Date dataRetiradaCobranca) throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * 1.3.1.4. Seleciona os Débitos Cobrados Remuneráveis Com Vencimento a partir da Entrada do
	 * Imóvel na Cobrança Administrativa.
	 * 
	 * @param dataImplantacaoCobranca
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<DebitoCobrado> selecionarDebitosCobradosRemuneraveisComVencimentoAPartirEntradaImovelNaCobrancaAdministrativa(
					Date dataImplantacaoCobranca, Integer idImovel, Date dataRetiradaCobranca) throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * 1.3.1.6. Seleciona as Guias Remuneráveis Marcadas na Cobrança Administrativa.
	 * 
	 * @param idCobrancaAcaoAtividadeComando
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<GuiaPagamentoPrestacao> selecionarGuiasPagamentoRemuneraveisMarcadaNaCobrancaAdministrativa(
					Integer idCobrancaAcaoAtividadeComando, Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * 1.3.1.7. Seleciona as Guias Remuneráveis Com Vencimento a partir da Entrada do Imóvel na
	 * Cobrança Administrativa.
	 * 
	 * @param idCobrancaAcaoAtividadeComando
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<GuiaPagamentoPrestacao> selecionarGuiasPagamentoRemuneraveisComVencimentoAPartirEntradaImovelNaCobrancaAdministrativa(
					Date dataImplantacaoCobranca, Integer idImovel, Date dataRetiradaCobranca) throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa
	 * 1.3.1.9. Seleciona os Débitos A Cobrar Remuneráveis Com Referência a partir da Entrada do
	 * Imóvel na Cobrança Administrativa.
	 * 
	 * @param anoMesDataImplantacaoCobranca
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public List<DebitoACobrar> selecionarDebitoACobrarRemuneraveisComReferenciaAPartirEntradaImovelNaCobrancaAdministrativa(
					Integer anoMesDataImplantacaoCobranca, Integer idImovel, Integer anoMesDataRetiradaCobranca)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * 1.2.3.2. Caso o tipo de documento corresponda à “GUIA DE PAGAMENTO”.
	 * 
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object selecionarGuiaPagamentoPrestacaoCobrancaAdministrativa(Integer idGuiaPagamento, Short numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * SB0012 – Obter Dados Débito A Cobrar.
	 * 
	 * @param idDebitoACobrar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object selecionarDebitoACobrarOuHistoricoCobrancaAdministrativa(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * [SB0012] - Obter Dados Débito A Cobrar
	 * 1.6. Cliente Usuário
	 * 
	 * @param idDebitoACobrar
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarClienteDebitoACobrarOuHistorcico(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * [SB0001B] - Exibir Dados da Remuneração da Cobrança Administrativa do Imóvel.
	 * 1.4. Documentos Remunerados.
	 * 1.4.1.2.3. Caso o tipo de documento corresponda à “DEBITO A COBRAR”.
	 * 
	 * @param idImovelCobrancaSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarDebitoACobrarImovelCobrancaAdmPelaSituacaoCobranca(Integer idImovelCobrancaSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC3060] Manter Imóvel Cobrança Administrativa.
	 * [SB0010] - Obter Dados Conta.
	 * 1.4. Cliente Usuário.
	 * 
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarClienteUsuarioPorContaOuContaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * Cancelar Aviso de Corte Pendente
	 * 
	 * @author Hebert Falcão
	 * @date 11/09/2013
	 */
	public Integer cancelarAvisoDeCortePendente(Integer idFaturamentoGrupo, Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Relação dos imóveis faturados do grupo
	 * 
	 * @date 19/09/2013
	 * @author Anderson Italo
	 */
	public Collection pesquisarMatriculasImoveisFaturadosPorGrupo(Integer idFaturamentoGrupo, Integer anoMesFaturamento)
					throws ErroRepositorioException;

	/**
	 * [UC3105] Manter Imóvel Cobrança Administrativa.
	 * [SB1000] -€“ Gerar Número Inscrição -€“ Modelo 1
	 * 
	 * @param idImovel
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarCamposGerarNuInscricaoCampanhaPremiacaoModelo1(Integer idImovel, Integer idCampanha)
					throws ErroRepositorioException;

	/**
	 * [UC3109] Efetuar Sorteio Campanha Premiação
	 * 
	 * @author Hiroshi Goncalves
	 * @date 03/10/2013
	 */
	public Collection<CampanhaPremio> pesquisarPremiosParaSorteio(Integer idCampanha) throws ErroRepositorioException;

	/**
	 * [UC3109] Efetuar Sorteio Campanha Premiação
	 * 
	 * @author Hiroshi Goncalves
	 * @date 03/10/2013
	 */
	public Collection<CampanhaCadastro> pesquisarInscricoesPorUnidadePremiacao(Integer idCampanha, CampanhaPremio campanhaPremio)
					throws ErroRepositorioException;

	public Object[] pesquisarDocumentoCobrancaValido(Integer idImovel, Integer idDocumentoTipo, Short numeroDiasValidade,
					boolean restringirSituacaoDebito) throws ErroRepositorioException;

	public Integer pesquisarDocumentoCobrancaSucessorValido(Integer idImovel, Collection<Integer> acoesSucessorasIdDocTipo)
					throws ErroRepositorioException;

	public List pesquisarDocumentoCobrancaSucessor(Integer idImovel, Collection<Integer> acoesSucessorasIdDocTipo,
					Date dataEmissaoAcaoPredecessora) throws ErroRepositorioException;

	public Collection<IEmitirRelatorioAvisoDebitoHelper> pesquisarCobrancaDocumentoRelatorioAvisoDebito(Integer idCobrancaAcaoCronograma,
					Integer idCobrancaAcaoComando) throws ErroRepositorioException;

	/**
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * Verifica se existem ocorrencias de um imovel em situação de cobranca
	 * 
	 * @author Anderson Italo
	 * @date 28/11/2013
	 * @throws ErroRepositorioException
	 */
	public boolean existeProcessoCobrancaImovelPorSituacaoInformada(Integer idImovel, String idsSituacaoesCobranca)
					throws ErroRepositorioException;

	/**
	 * [UC0349] Emitir Documento de Cobrança
	 * Seleciona os itens do documento de cobrança correspondentes a conta e
	 * ordenar por ano/mês de referência da conta
	 * 
	 * @author Anderson Italo
	 * @data 09/12/2013
	 * @param idCobrancaDocumento
	 * @return Collection<CobrancaDocumentoItem>
	 */
	public Collection<CobrancaDocumentoItem> pesquisarCobrancaDocumentoItemContaAvisoDebitoModelo2(Integer idCobrancaDocumento)
					throws ErroRepositorioException;

	/**
	 * [UC0614] Gerar Resumo das Ações de Cobrança Eventuais
	 * [SB0002] - Determinar Situação da Ação de Cobrança
	 * 
	 * @author Anderson Italo
	 * @data 11/12/2013
	 * @param idCobrancaDocumento
	 * @return Collection<CobrancaDocumentoItem>
	 */
	public Collection<CobrancaDocumentoItem> pesquisarCobrancaDocumentoItemPorSituacao(Integer idCobrancaDocumento, Integer idSituacao)
					throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0013] - Verificar Titularidade do Débito
	 * 
	 * @author Anderson Italo
	 * @data 14/01/2014
	 */
	public Integer pesquisarIdClienteGuiaPagamentoComNomeConta(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0013] - Verificar Titularidade do Débito
	 * 
	 * @author Anderson Italo
	 * @data 14/01/2014
	 */
	public Integer pesquisarIdClienteDebitoACobrarComNomeConta(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0013] - Verificar Titularidade do Débito
	 * 
	 * @author Anderson Italo
	 * @data 14/01/2014
	 */
	public Integer pesquisarIdClienteImovelPorTipoRelacao(Integer idImovel, Integer idClienteRelacaoTipo) throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0013] - Verificar Titularidade do Débito
	 * 
	 * @author Anderson Italo
	 * @data 14/01/2014
	 */
	public Integer pesquisarIdClienteGuiaPagamentoTitularRelacao(Integer idGuiaPagamento, Integer idClienteRelacaoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC0251] Gerar Atividade de Ação de Cobrança
	 * [SB0013] - Verificar Titularidade do Débito
	 * 
	 * @author Anderson Italo
	 * @data 14/01/2014
	 */
	public Integer pesquisarIdClienteDebitoACobrarTitularRelacao(Integer idDebitoACobrar, Integer idClienteRelacaoTipo)
					throws ErroRepositorioException;

	/**
	 * [UC3044] Informar Entrega/Devolução de Documentos de Cobrança
	 * [FS0002] - Verificar existência do documento de cobrança para entrega/devolução
	 * 
	 * @author Eduardo Oliveira
	 * @data 04/02/2014
	 */
	public Integer pesquisarIdDocumentoCobrancaEntregaDevolucao(Integer idImovel, Integer idDocumentoTipo) throws ErroRepositorioException;

	/**
	 * @param anoMesReferenciaConta
	 * @param dataVencimentoConta
	 * @param idResolucaoDiretoria
	 * @param dataPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> obterResolucaoDiretoriaParametrosPagamentoAVista(Integer anoMesReferencia, Date dataVencimento,
					Integer idResolucaoDiretoria, Date dataPagamento) throws ErroRepositorioException;

	/**
	 * [UC3138] Filtrar Débito para Prescrição
	 * Selecionar imóveis com dados do filtro
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2014
	 */
	public Collection pesquisarImoveisComandoPrescricaoDebitos(ComandoDebitosPrescritosHelper helper) throws ErroRepositorioException;

	/**
	 * [UC3138] Filtrar Débito para Prescrição
	 * Selecionar imóveis com dados do filtro
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2014
	 */
	public Collection pesquisarDadosContasComandoPrescricaoDebitos(Integer idImovel, Date dataCorrenteMenosNumeroAnosPrescDebOrgaoPublico,
					Date dataCorrenteMenosNumeroAnosPrescDebParticular, ComandoDebitosPrescritosHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC3138] Filtrar Débito para Prescrição
	 * 
	 * @author Anderson Italo
	 * @date 24/02/2014
	 */
	public Collection pesquisarDadosGuiasPagamentoComandoPrescricaoDebitos(Integer idImovel,
					Date dataCorrenteMenosNumeroAnosPrescDebOrgaoPublico, Date dataCorrenteMenosNumeroAnosPrescDebParticular,
					ComandoDebitosPrescritosHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Collection pesquisarImoveisComDebitosPrescritos(FiltroImoveisComDebitosPrescritosHelper helper, int pageOffset)
					throws ErroRepositorioException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Integer pesquisarQuantidadeImoveisComDebitosPrescritos(FiltroImoveisComDebitosPrescritosHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Collection pesquisarContasPrescritasPorImovel(Integer idImovel, FiltroImoveisComDebitosPrescritosHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Collection pesquisarContasHistoricoPrescritasPorImovel(Integer idImovel, FiltroImoveisComDebitosPrescritosHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Collection pesquisarGuiasPagamentoPrestacaoPrescritasPorImovel(Integer idImovel, FiltroImoveisComDebitosPrescritosHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC3141] Filtrar Imóveis com Débitos Prescritos
	 * 
	 * @author Anderson Italo
	 * @date 02/04/2014
	 */
	public Collection pesquisarGuiasPagamentoPrestacaoHistoricoPrescritasPorImovel(Integer idImovel,
					FiltroImoveisComDebitosPrescritosHelper helper) throws ErroRepositorioException;

	/**
	 * [UC0614] Gerar Resumo das Ações de Cobrança Eventuais
	 * 
	 * @param idImovel
	 * @param idComando
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ImovelCobrancaSituacao pesquisarImovelEmCobrancaAdministrativa(Integer idImovel, Integer idComando)
					throws ErroRepositorioException;

	/**
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarImovelEmCobrancaAdministrativaAjuste() throws ErroRepositorioException;

}
