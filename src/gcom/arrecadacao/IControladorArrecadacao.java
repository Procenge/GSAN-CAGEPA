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

package gcom.arrecadacao;

import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.aviso.AvisoDeducoes;
import gcom.arrecadacao.aviso.bean.*;
import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.arrecadacao.bean.*;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimento;
import gcom.arrecadacao.pagamento.*;
import gcom.arrecadacao.pagamento.bean.ClassificarLotePagamentosNaoClassificadosHelper;
import gcom.arrecadacao.pagamento.bean.ClassificarPagamentosNaoClassificadosHelper;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaDocumento;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.relatorio.arrecadacao.GuiaDevolucaoRelatorioHelper;
import gcom.relatorio.arrecadacao.pagamento.GuiaPagamentoRelatorioHelper;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;
import gcom.util.InserirPagamentoException;
import gcom.util.ParametroNaoInformadoException;

import java.math.BigDecimal;
import java.util.*;

/**
 * Interface Controlador Arrecadacao PADRÃO
 * 
 * @author Raphael Rossiter
 * @date 30/04/2007
 */
public interface IControladorArrecadacao {

	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores
	 * 
	 * @author Sávio Luiz, Vivianne Sousa
	 * @date 30/01/2006, 23/11/2007
	 * @author Saulo Lima
	 * @date 02/12/2008
	 *       Alteração para tratar registros legados
	 * @author Saulo Lima
	 * @date 19/02/2009
	 *       Remover o retorno do método.
	 * @author Saulo Lima
	 * @date 27/02/2010
	 *       Tratamento da Ficha de Compensação a partir de novo método.
	 * @author Saulo Lima
	 * @date 21/05/2010
	 *       Alteração para evitar consulta desnecessária à base de dados
	 */
	public void registrarMovimentoArrecadadores(StringBuilder stringBuilderTxt, Arrecadador arrecadador, String idTipoMovimento,
					int quantidadeRegistros, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores
	 * [SF0004] - Processar Registro Código F
	 * 
	 * @author Saulo Lima
	 * @date 31/12/2008
	 *       Método Criado para processar os arquivos do GSAN e os arquivos de Legados (método
	 *       sobrescrito)
	 * @param registroHelperCodigoF
	 * @param sistemaParametro
	 * @param linhaRegistro
	 * @param arrecadadorMovimento
	 * @param indicadorAceitacaoRegistroMovimento
	 * @param registroHelperCodigoA
	 * @param dataGeracao
	 * @param avisosBancarios
	 * @param numeroSequencialAvisoBancario
	 * @param registroHelperCodigoZ
	 * @param pagamentos
	 * @throws ControladorException
	 */
	public void processarRegistroCodigoF(RegistroHelperCodigoF registroHelperCodigoF, SistemaParametro sistemaParametro,
					String linhaRegistro, ArrecadadorMovimento arrecadadorMovimento, int indicadorAceitacaoRegistroMovimento,
					RegistroHelperCodigoA registroHelperCodigoA, Date dataGeracao, Collection<AvisoBancario> avisosBancarios,
					short numeroSequencialAvisoBancario, RegistroHelperCodigoZ registroHelperCodigoZ, Collection<Pagamento> pagamentos,
					long linhaArrecadacao, ArrecadadorContrato arrecadadorContrato, Arrecadador arrecadadorSelecionado)
					throws ControladorException;

	/**
	 * Coisa de Rafael Corrêa tem que comentar
	 * 
	 * @author Administrador
	 * @date 16/03/2006
	 * @param codigoAgente
	 * @param dataLancamento
	 * @return
	 * @throws ControladorException
	 */
	public Double pesquisarDeducoesAvisoBancario(String codigoAgente, Date dataLancamento, String numeroSequencial)
					throws ControladorException;

	/**
	 * [UC0235] Inserir Aviso Bancário [FS0003] Verificar existência de avisos
	 * bancários não realizados [FS0004] Verificar seleção de aviso Retorna o
	 * valor do maior número sequencial do arrecadador selecionado
	 * 
	 * @author Rafael Corrêa
	 * @date 24/03/2006
	 * @param dataLancamento
	 * @return
	 * @throws ControladorException
	 */
	public Short pesquisarValorMaximoNumeroSequencial(Date dataLancamento, String idArrecadador) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro do movimento dos arrecadadores
	 * [UC0263] - Filtrar Movimento dos Arrecadadores
	 * 
	 * @author Raphael Rossiter
	 * @date 23/02/2006
	 * @param filtroArrecadadorMovimento
	 * @param movimentoOcorrencia
	 * @param movimentoAceito
	 * @param movimentoAbertoFechado
	 * @return Uma coleçao com os movimentos selecionados
	 * @throws ControladorException
	 */
	public FiltroArrecadadorMovimento filtrarMovimentoArrecadadores(FiltroArrecadadorMovimento filtroArrecadadorMovimento,
					String movimentoOcorrencia, String movimentoAceito, String movimentoAbertoFechado) throws ControladorException;

	/**
	 * Obtém o número de registros em ocorrência de um determinado movimento
	 * (número de linhas da tabela ARRECADADOR_MOVIMENTO_ITEM com ARMV_ID =
	 * ARMV_ID da tabela ARRECADADOR_MOVIMENTO e AMIT_DSOCORRENCIA diferente de
	 * "OK")
	 * 
	 * @author Raphael Rossiter
	 * @date 08/03/2006
	 * @param arrecadadorMovimento
	 * @return Um integer que representa a quantidade de registros selecionados
	 * @throws ControladorException
	 */
	public Integer obterNumeroRegistrosEmOcorrenciaPorMovimentoArrecadadores(ArrecadadorMovimento arrecadadorMovimento,
					String descricaoOcorrencia) throws ControladorException;

	/**
	 * Obtém o número de registros que não foram aceitos de um determinado
	 * movimento (número de linhas da tabela ARRECADADOR_MOVIMENTO_ITEM com
	 * ARMV_ID = ARMV_ID da tabela ARRECADADOR_MOVIMENTO e AMIT_ICACEITACAO
	 * igual a 2 (NÃO))
	 * 
	 * @author Raphael Rossiter
	 * @date 08/03/2006
	 * @param arrecadadorMovimento
	 * @return Um integer que representa a quantidade de registros selecionados
	 * @throws ControladorException
	 */
	public Integer obterNumeroRegistrosNaoAceitosPorMovimentoArrecadadores(ArrecadadorMovimento arrecadadorMovimento,
					Short indicadorAceitacao) throws ControladorException;

	/**
	 * Lista os avisos bancários associados ao movimento com os seguintes dados:
	 * Data do Lançamento Sequencial do Aviso Tipo do Aviso Data do Crédito
	 * Valor do Crédito Valor da Arrecadação Valor Total dos pagamentos
	 * associados ao aviso Situação do Aviso
	 * 
	 * @author Raphael Rossiter
	 * @date 08/03/2006
	 * @param arrecadadorMovimento
	 * @return Uma Collection<AvisoBancarioHelper> que representa a os avisos
	 *         bancários selecionados
	 * @throws ControladorException
	 */
	public Collection<AvisoBancarioHelper> obterColecaoAvisosBancariosPorArrecadadorMovimento(ArrecadadorMovimento arrecadadorMovimento)
					throws ControladorException;

	public void verificarExistenciaContaParaAvisoBancario(String idArrecadador, String idConta) throws ControladorException;

	/**
	 * Método que atualiza o aviso bancario, adiciona as deducoes e acertos
	 * novas e remove as deducoes e os acertos que forma para remover
	 * 
	 * @author thiago
	 * @date 14/03/2006
	 * @param avisoBancario
	 * @param duducoes
	 * @param deducoesParaRemover
	 * @param acertos
	 * @param acertosParaRemover
	 */
	void atualizarAvisoBancario(AvisoBancario avisoBancario, Collection duducoes, Collection deducoesParaRemover, Collection acertos,
					Collection acertosParaRemover, Usuario usuario) throws ControladorException;

	/**
	 * Faz a pesquisa de devolução fazendo os carregamentos de clienteContas,
	 * clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corrêa
	 * @date
	 * @param FiltroDevolucao
	 * @return Collection<Devolucao>
	 * @throws ControladorException
	 */
	public Collection<Devolucao> pesquisarDevolucao(FiltroDevolucao filtroDevolucao) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imóvel pesquisarPagamentoImovel
	 * 
	 * @author Tiago Moreno, Roberta Costa
	 * @date 12/06/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoImovel(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * para pesquisar os pagamento historicos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos historicos do Imóvel
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoImovel(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Método que pesquisa os pagamentos histórico para tela de consulta de Imóvel
	 * 
	 * @author Saulo Lima
	 * @date 09/02/2009
	 * @param idImovel
	 * @return Collection<PagamentoHistorico>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoConsultaImovel(Integer idImovel) throws ControladorException;

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador
	 * @date 16/03/2006
	 * @param codigoBarras
	 * @param dataPagamento
	 * @param idFormaPagamento
	 * @param sistemaParametro
	 * @param idArrecadadorMovimentoItem
	 * @return
	 * @throws ControladorException
	 */
	public PagamentoHelperCodigoBarras processarPagamentosCodigoBarras(RegistroHelperCodigoBarras registroHelperCodigoBarras,
					Date dataPagamento, Integer idFormaPagamento, SistemaParametro sistemaParametro, String subStringIdPagamento,
					Integer idConcessionaria) throws ControladorException;

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Administrador
	 * @date 17/03/2006
	 * @param codigoBarras
	 * @return
	 */
	public RegistroHelperCodigoBarras distribuirDadosCodigoBarras(String codigoBarras) throws ControladorException;

	/**
	 * [UC0262] - Distribuir Dados do Registro de Movimento do Arrecadador
	 * [SB0005] – Distribuir Registro Código F
	 * 
	 * @author Saulo Lima
	 * @date 30/12/2008
	 *       Método Criado para processar os arquivos do GSAN e os arquivos de Legados (método
	 *       sobrescrito)
	 * @param codigoRegistro
	 *            char que identifica o tipo do registro que está sendo processado
	 * @param linha
	 *            String contendo a linha completa
	 * @return RegistroHelperCodigoF
	 *         RegistroHelperCodigoF preenchido
	 * @throws ControladorException
	 */
	public RegistroHelperCodigoF distribuirDadosRegistroCodigoF(char codigoRegistro, String linha) throws ControladorException;

	/**
	 * [UC0239] Filtrar Aviso Bancário
	 * Validar Filtrar Aviso Bancário
	 * 
	 * @author Vivianne Sousa
	 * @date 18/03/2006
	 * @param dataLancamentoInicio
	 * @param dataLancamentoFim
	 * @param periodoArrecadacaoInicio
	 * @param periodoArrecadacaoFim
	 * @param dataPrevisaoCreditoDebitoInicio
	 * @param dataPrevisaoCreditoDebitoFim
	 * @param intervaloValorPrevistoInicio
	 * @param intervaloValorPrevistoFim
	 * @param dataRealizacaoCreditoDebitoInicio
	 * @param dataRealizacaoCreditoDebitoFim
	 * @param intervaloValorRealizadoInicio
	 * @param intervaloValorRealizadoFim
	 *            return void
	 * @throws ControladorException
	 */
	public void validacaoFinal(Date dataLancamentoInicio, Date dataLancamentoFim, Integer periodoArrecadacaoInicio,
					Integer periodoArrecadacaoFim, Date dataPrevisaoCreditoDebitoInicio, Date dataPrevisaoCreditoDebitoFim,
					BigDecimal intervaloValorPrevistoInicio, BigDecimal intervaloValorPrevistoFim, Date dataRealizacaoCreditoDebitoInicio,
					Date dataRealizacaoCreditoDebitoFim, BigDecimal intervaloValorRealizadoInicio, BigDecimal intervaloValorRealizadoFim)
					throws ControladorException;

	/**
	 * Método que recebe um array de Integer e remove os Avisos Bancarios dos
	 * ids passado, caso exista um Aviso Bancario que tenha um relacionamento
	 * com outra tabela entao nao remove nenhum. Outra tabela fora aviso_deducao
	 * e aviso_acerto
	 * 
	 * @author Thiago Toscano
	 * @date 20/03/2006
	 * @param ids
	 * @throws ControladorException
	 */
	public void removerAvisosBancarios(Integer[] ids, OperacaoEfetuada operacaoEfetuada,
					Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper) throws ControladorException;

	/**
	 * [UC0270] Apresentar Análise do Movimento dos Arrecadadores
	 * O sistema seleciona os itens do movimento do arrecadador com os seguintes
	 * dados: 1 - Código do Registro 2 - Identificação do Imóvel/Cliente 3 -
	 * Ocorrência 4 - Indicador de Aceitação 5 - Descrição do Indicador de
	 * Aceitação
	 * [SF0001] Consultar os Itens do Movimento do Arrecadador
	 * 
	 * @author Raphael Rossiter
	 * @data 20/03/2006
	 * @param arrecadadorMovimento
	 * @return Collection<ArrecadadorMovimentoItemHelper>
	 */
	public Collection<ArrecadadorMovimentoItemHelper> consultarItensMovimentoArrecadador(ArrecadadorMovimento arrecadadorMovimento,
					Integer idImovel, Short indicadorAceitacao, String descricaoOcorrencia) throws ControladorException;

	/**
	 * [UC0270] Apresentar Análise do Movimento dos Arrecadadores
	 * O sistema seleciona os itens do movimento do arrecadador com os seguintes
	 * dados: 1 - Código do Registro 2 - Identificação do Imóvel/Cliente 3 -
	 * Ocorrência 4 - Indicador de Aceitação 5 - Descrição do Indicador de
	 * Aceitação
	 * [SF0001] Consultar os Itens do Movimento do Arrecadador
	 * 
	 * @author Raphael Rossiter, Kassia Albuquerque
	 * @data 20/03/2006, 24/08/2007
	 * @param arrecadadorMovimento
	 * @return Collection<ArrecadadorMovimentoItemHelper>
	 */
	public Collection<ArrecadadorMovimentoItemHelper> consultarItensMovimentoArrecadador(ArrecadadorMovimento arrecadadorMovimento,
					Integer idImovel, Short indicadorAceitacao, String descricaoOcorrencia, String codigoArrecadacaoForma)
					throws ControladorException;

	/**
	 * [UC0270] Apresentar Análise do Movimento dos Arrecadadores
	 * O sistema captura os dados referentes ao conteúdo do registro de
	 * Movimento do arrecadador
	 * [SF0002] Apresentar Dados do Conteúdo do Registro de Movimento do
	 * Arrecadador
	 * 
	 * @author Raphael Rossiter
	 * @data 21/03/2006
	 * @param arrecadadorMovimentoItem
	 * @return DadosConteudoRegistroMovimentoArrecadador
	 */
	public DadosConteudoRegistroMovimentoArrecadadorHelper apresentarDadosConteudoRegistroMovimentoArrecadador(
					ArrecadadorMovimentoItem arrecadadorMovimentoItem) throws ControladorException;

	/**
	 * <Breve descrição sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * <Breve descrição sobre o subfluxo>
	 * <Identificador e nome do subfluxo>
	 * <Breve descrição sobre o fluxo secundário>
	 * <Identificador e nome do fluxo secundário>
	 * 
	 * @author Pedro Alexandre
	 * @date 22/03/2006
	 * @param idsPagamentos
	 * @throws ControladorException
	 */
	public void removerPagamentos(String[] idsPagamentos, Usuario usuarioLogado) throws ControladorException;

	public void removerPagamentosAjusteDESO(String idMovimentoCorreto, String idMovimentoDuplicado) throws ControladorException;

	/**
	 * Este caso de uso apresenta a análise do aviso bancário e os
	 * pagamentos/devoluções associados.
	 * [UC0267] - Apresentar Análise do Aviso Bancário
	 * 
	 * @author Raphael Rossiter
	 * @date 23/03/2006
	 * @param avisoBancario
	 * @return AvisoBancarioHelper
	 */
	public AvisoBancarioHelper apresentarAnaliseAvisoBancario(AvisoBancario avisoBancario) throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Pesquisa o débito a cobrar do imóvel informado pelo usuário
	 * [FS0024] - Verificar existência do débito a cobrar
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @param idDebitoACobrar
	 * @return
	 * @throws ControladorException
	 */
	public DebitoACobrar pesquisarDebitoACobrarDigitado(String idImovel, String idDebitoACobrar) throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Pesquisa o débito a cobrar do imóvel informado pelo usuário
	 * [FS0024] - Verificar existência do débito a cobrar
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @param idDebitoACobrar
	 * @return
	 * @throws ControladorException
	 */
	public DebitoACobrar pesquisarDebitoACobrarDigitado2(String idImovel, String idDebitoACobrar) throws ControladorException;

	/**
	 * Pesquisa o débito a cobrar do imóvel informado pelo usuário
	 * 
	 * @param idImovel
	 * @param idDebitoACobrar
	 * @return DebitoACobrarHistorico
	 * @throws ControladorException
	 */
	public DebitoACobrarHistorico pesquisarDebitoACobrarHistoricoDigitado(String idImovel, String idDebitoACobrar)
					throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Pesquisa a guia de pagamento do imóvel informado pelo usuário
	 * [FS0022] - Verificar existência da guia de pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idImovel
	 * @param idCliente
	 * @param idGuiaPagamento
	 * @return
	 * @throws ControladorException
	 */
	public GuiaPagamento pesquisarGuiaPagamentoDigitada(String idImovel, String idCliente, String idGuiaPagamento)
					throws ControladorException;

	/**
	 * Atualizar uma coleção de pagamentos no sistema
	 * [UC0265] Atualizar Pagamentos
	 * Pesquisa a guia de pagamento histórico do imóvel informado pelo usuário
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 18/06/2013
	 * @param idImovel
	 * @param idCliente
	 * @param idGuiaPagamento
	 * @return
	 * @throws ControladorException
	 */
	public GuiaPagamentoHistorico pesquisarGuiaPagamentoHistoricoDigitada(String idImovel, String idCliente, String idGuiaPagamento)
					throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se o usuário informou o código da guia de pagamento e o tipo de
	 * débito, só pode ser informado um dos dois
	 * [FS0021] Verificar preenchimento da guia de pagamento e do tipo de débito
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idGuiaPagamento
	 * @param idTipoDebito
	 * @throws ControladorException
	 */
	public void verificarPreeenchimentoGuiaPagamentoETipoDebito(String idGuiaPagamento, String idTipoDebito) throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se o usuário informou o código do débito a cobrar e o tipo de
	 * débito, só pode ser informado um dos dois
	 * [FS0023] Verificar preenchimento do débito a cobrar e do tipo de débito
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param idDebitoACobrar
	 * @param idTipoDebito
	 * @throws ControladorException
	 */
	public void verificarPreeenchimentoDebitoACobrarETipoDebito(String idDebitoACobrar, String idTipoDebito) throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se a localidade informada é a mesma da guia de pagamento
	 * [FS0014] Verificar localidade da guia de pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param guiaPagamento
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void verificarLocalidadeGuiaPagamento(GuiaPagamento guiaPagamento, String idLocalidade) throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica se a localidade informada é a mesma do débito a cobrar
	 * [FS0017] Verificar localidade do débito a cobrar
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param debitoACobrar
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void verificarLocalidadeDebitoACobrar(DebitoACobrar debitoACobrar, String idLocalidade) throws ControladorException;

	/**
	 * Verifica se a localidade informada é a mesma do débito a cobrar Historico
	 * 
	 * @param DebitoACobrarHistorico
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void verificarLocalidadeDebitoACobrarHistorico(DebitoACobrarHistorico debitoACobrarHistorico, String idLocalidade)
					throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica a existência de débito a cobrar com o tipo de débito e o imóvel
	 * informados
	 * [FS0016] Verificar existência de débito a cobrar com tipo de débito
	 * informado
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param tipoDebito
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public DebitoACobrar verificarExistenciaDebitoACobrarComTipoDebito(DebitoTipo tipoDebito, String idImovel) throws ControladorException;

	/**
	 * Verifica a existência de débito a cobrar com o tipo de débito e o imóvel
	 * informados
	 * 
	 * @param tipoDebito
	 * @param idImovel
	 * @return
	 * @throws ControladorException
	 */
	public DebitoACobrarHistorico verificarExistenciaDebitoACobrarHistoricoComTipoDebito(DebitoTipo tipoDebito, String idImovel)
					throws ControladorException;

	/**
	 * Inseri uma coleção de pagamentos no sistema
	 * [UC0265] Inserir Pagamentos
	 * Verifica a existência de guia de pagamento com o tipo de débito e o
	 * imóvel informados
	 * [FS0013] Verificar existência de guia de pagamento com tipo de débito
	 * informado
	 * 
	 * @author Pedro Alexandre
	 * @date 16/02/2006
	 * @param tipoDebito
	 * @param idImovel
	 * @param idCliente
	 * @return
	 * @throws ControladorException
	 */
	public GuiaPagamento verificarExistenciaGuiaPagamentoComTipoDebito(DebitoTipo tipoDebito, String idImovel, String idCliente)
					throws ControladorException;

	/**
	 * Atualizar uma coleção de pagamentos no sistema
	 * [UC0265] Atualizar Pagamentos
	 * Pesquisa a guia de pagamento histórico do imóvel informado pelo usuário
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 19/06/2013
	 * @param idImovel
	 * @param idCliente
	 * @param tipoDebito
	 * @return
	 * @throws ControladorException
	 */
	public GuiaPagamentoHistorico verificarExistenciaGuiaPagamentoHistoricoComTipoDebito(DebitoTipo tipoDebito, String idImovel,
					String idCliente) throws ControladorException;

	/**
	 * Responsável pela manutenção das informações de pagamento
	 * [UC0266] Manter Pagamentos
	 * Atualiza um pagamento no sistema, verificando se a atualização já foi
	 * executada por outro usuário
	 * [SB0001] Atualizar Pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 25/03/2006
	 * @param pagamento
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void atualizarPagamento(Pagamento pagamento, Usuario usuarioLogado, String gerarDevolucaoValores, Integer idCreditoTipo,
					PagamentoSituacao situacaoPagamentoOriginal)
					throws ControladorException;

	/**
	 * Insere os aviso deduções no aviso bancário
	 * [UC0000] Inserir Aviso Bancário
	 * 
	 * @author Rafael Corrêa
	 * @date 18/04/2006
	 * @throws ControladorException
	 */
	public void inserirAvisosDeducoes(AvisoDeducoes avisoDeducoes, AvisoBancario avisoBancario) throws ControladorException;

	/**
	 * Gera os dados diários da arrecadação acumulando a quantidade e o valor
	 * dos pagamentos
	 * [UC0301] Gerar Dados Diários da Arrecadação
	 * 
	 * @author Pedro Alexandre
	 * @date 11/04/2006
	 * @author Saulo Lima
	 * @date 19/01/2009
	 *       Acumular dados de Pagamento e PagamentoHistorico
	 * @throws ControladorException
	 */
	public void gerarDadosDiariosArrecadacao(int idFuncionalidadeIniciada, Collection<Integer> colecaoLocalidades)
					throws ControladorException;

	/**
	 * [UC0319] Gerar Movimento de Débito Automático para o banco
	 * pesquisa todos os bancos que tenham contrato vigente para arrecadador
	 * contas com forma de arrecadação correspondente a debito automático
	 * [SB0002] - Carregar Lista de Bancos
	 * 
	 * @author Sávio Luiz
	 * @date 18/04/2006
	 * @return Coleção de Bancos
	 * @throws ControladorException
	 */
	/*
	 * public Collection<Banco> pesquisaBancosDebitoAutomatico() throws
	 * ControladorException;
	 */

	/**
	 * [UC0319] Gerar Movimento de Débito Automático para o banco
	 * pesquisa os movimentos de débito automático para o banco,referentes ao
	 * grupo e ano/mês de faturamento informados
	 * [SB0002] - Carregar Lista de Bancos
	 * 
	 * @author Sávio Luiz
	 * @date 18/04/2006
	 * @param idFaturamentoGrupo
	 *            ,anoMesReferenciaFaturamento,idBanco
	 * @return Coleção de DebitoAutomaticoMovimento
	 * @throws ControladorException
	 */
	public Map<Banco, Collection<DebitoAutomaticoMovimento>> pesquisaDebitoAutomaticoMovimento(Collection colecaoIdsFaturamentoGrupo,
					Integer anoMesReferenciaFaturamento, String opcaoDebitoAutomatico) throws ControladorException;

	/**
	 * [UC0319] Filtrar Aviso Bancario
	 * 
	 * @author Vivianne Sousa
	 * @date 20/04/2006
	 * @param avisoBancarioHelper
	 * @return Coleção de DebitoAutomaticoMovimento
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarAvisoBancarioAbertoFechado(AvisoBancarioHelper avisoBancarioHelper) throws ControladorException;

	/**
	 * Este caso de uso permite classificar os pagamentos e as devoluções
	 * [UC0300] Classificar Pagamentos e Devoluções
	 * 
	 * @author Saulo Lima
	 * @since 02/12/2008
	 * @param Devolucao
	 * @return void
	 */
	public void classificarDevolucoes(Devolucao devolucao) throws ControladorException;

	/**
	 * [UC0319] Gerar Movimento de Débito Automático para o banco
	 * Movimento de débito automático em arquivo TXT gerado e enviado ao banco.
	 * 
	 * @author Sávio Luiz
	 * @date 20/04/2006
	 * @param idFaturamentoGrupo
	 *            ,anoMesReferenciaFaturamento,idBanco
	 * @return Collection<GerarMovimentoDebitoAutomaticoBancoHelper>
	 * @throws ControladorException
	 */
	public void gerarMovimentoDebitoAutomaticoBanco(Map<Banco, Collection<DebitoAutomaticoMovimento>> debitosAutomaticoBancosMap,
					Usuario usuario, Integer anoMesReferencia, String opcaoDebitoAutomatico) throws ControladorException;

	/**
	 * [UC0319] Gerar Movimento de Débito Automático para o banco
	 * Cria uma linha de 150 posições com o registro tipo E.
	 * [SB0003] - Regerar arquivo TXT para um movimento de débito automático
	 * gerado anteriormente
	 * 
	 * @author Sávio Luiz
	 * @date 25/04/2006
	 * @param arrecadadorMovimento
	 * @return Object[]
	 * @throws ControladorException
	 */
	public void regerarArquivoTxtMovimentoDebitoAutomatico(ArrecadadorMovimento arrecadadorMovimento, String envioBanco, Usuario usuario)
					throws ControladorException;

	/**
	 * [UC0322] - Inserir Guia de Devolução
	 * Insere uma Guia de Devolução
	 * 
	 * @author Rafael Corrêa, Pedro Alexandre
	 * @date 02/05/2006, 21/11/2006
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer inserirGuiaDevolucao(GuiaDevolucao guiaDevolucao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Faz a pesquisa de guia de devolução para o relatório fazendo os
	 * carregamentos de clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corrêa
	 * @date 11/09/2006
	 * @param FiltroGuiaDevolucao
	 * @return Collection<GuiaDevolucao>
	 * @throws ControladorException
	 */
	public Collection<GuiaDevolucao> pesquisarGuiaDevolucaoRelatorio(FiltroGuiaDevolucao filtroGuiaDevolucao) throws ControladorException;

	/**
	 * [UC0324] - Filtrar Guia de Devolucao
	 * [SF0001] - Seleciona Guias de Devolução do Cliente
	 * Faz a pesquisa de guia de devolução fazendo os carregamentos de
	 * clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corrêa
	 * @date
	 * @param FiltroGuiaDevolucao
	 * @return Collection<GuiaDevolucao>
	 * @throws ControladorException
	 */
	public Collection<GuiaDevolucao> pesquisarGuiaDevolucao(FiltroGuiaDevolucao filtroGuiaDevolucao, Integer numeroPagina)
					throws ControladorException;

	/**
	 * [UC0324] - Filtrar Guia de Devolucao
	 * [SF0001] - Seleciona Guias de Devolução do Cliente
	 * Faz a pesquisa de guia de devolução fazendo os carregamentos de
	 * clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corrêa
	 * @date
	 * @param FiltroGuiaDevolucao
	 * @return Collection<GuiaDevolucao>
	 * @throws ControladorException
	 */
	public Integer pesquisarGuiaDevolucaoCount(FiltroGuiaDevolucao filtroGuiaDevolucao) throws ControladorException;

	/**
	 * [UC0266] Manter Guia de Devolução
	 * [SB0001] - Atualizar Guia de Devolução
	 * Atualiza uma Guia de Devolução e as Devoluções associadas a ela
	 * 
	 * @author Rafael Corrêa
	 * @date 10/05/2006
	 * @throws ControladorException
	 */
	public void atualizarGuiaDevolucao(GuiaDevolucao guiaDevolucao, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC0339] - Consultar Dados Diarios da Arrecadacao
	 * [SB0007] - Apresentar Dados Diarios da Arrecadacao por Categoria
	 * Acumula os dados de uma Colecao de Dados Diarios da Arrecadação
	 * 
	 * @author Fernanda Paiva
	 * @date 24/05/2006
	 * @throws ControladorException
	 */
	public void acumularDadosArrecadacao(Collection colecaoCategoriaPorArrecadacaoDadosDiarios,
					ArrecadacaoDadosDiarios arrecadacaoDadosDiarios, int indicador, String idElo, String idGerencia, String idLocalidade)
					throws ControladorException;

	/**
	 * Consulta ResumoArrecadacao para a geração do relatório '[UC0345] Gerar
	 * Relatório de Resumo do Arrecadacao' de acordo com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 24/05/2006
	 * @param opcaoTotalizacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection consultarResumoArrecadacaoRelatorio(String opcaoTotalizacao, int mesAnoReferencia, Integer gerenciaRegional,
					Integer localidade, Integer unidadeNegocio, Integer idSetorComercial) throws ControladorException;

	/**
	 * Consulta a qtde de registros ResumoArrecadacao para a geração do
	 * relatório '[UC0345] Gerar Relatório de Resumo do Arrecadacao' de acordo
	 * com a opção de totalização.
	 * 
	 * @author Vivianne Sousa
	 * @created 02/06/2006
	 * @param opcaoTotalizacao
	 * @param mesAnoReferencia
	 * @param gerenciaRegional
	 * @param localidade
	 * @return
	 * @throws ControladorException
	 */
	public Integer consultarQtdeRegistrosResumoArrecadacaoRelatorio(String opcaoTotalizacao, int mesAnoReferencia,
					Integer gerenciaRegional, Integer localidade, Integer idSetorComercial) throws ControladorException;

	/**
	 * Encerra a arrecadação do ano/mês atual
	 * [UC0276] Encerrar Arrecadação do Mês
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @throws ControladorException
	 */
	public void encerrarArrecadacaoMes(Collection<Integer> colecaoIdsLocalidades, int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imóvel pesquisarPagamentoImovel
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoClienteConta(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra os Pagamento Historicos do Cliente Conta
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoClienteConta(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imóvel pesquisarPagamentoImovel
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoClienteDebitoACobrar(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra os pagamentos historicos do debito a cobrar
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 12/06/06,06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoClienteDebitoACobrar(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imóvel pesquisarPagamentoImovel
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoClienteGuiaPagamento(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtrar os pagamentos historicos do Cliente Guia Pagamento
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoClienteGuiaPagamento(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imóvel pesquisarPagamentoImovel
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoLocalidade(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra os Pagamento Historicos da Localidade
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoLocalidade(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imóvel pesquisarPagamentoImovel
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoAvisoBancario(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra oas pagamento historicos do Aviso Bancario
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoAvisoBancario(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imóvel pesquisarPagamentoImovel
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoMovimentoArrecadador(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtrar pagamentos historicos do movimento arrecador
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoMovimentoArrecadador(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * obtem colecao com dados diarios da arrecadacao
	 * 
	 * @author Fernanda Paiva
	 * @date 09/06/2006
	 * @param anoMesReferencia
	 *            id descricao - para informar por onde fazer a pesquisa
	 * @return Uma Colecao
	 * @throws ControladorException
	 */
	public Collection consultarDadosDiarios(int anoMesReferencia, int id, String descricao, int idElo) throws ControladorException;

	/**
	 * [UC0319] Filtrar Aviso Bancario
	 * 
	 * @author Fernanda Paiva
	 * @date 16/08/2006
	 * @param avisoBancarioHelper
	 * @return Coleção de DebitoAutomaticoMovimento
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarAvisoBancarioAbertoFechadoParaPaginacao(AvisoBancarioHelper avisoBancarioHelper, Integer numeroPagina)
					throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Cliente
	 * 
	 * @author Vivianne Sousa
	 * @date 21/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoCliente(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra os pagamento historicos do cliente
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 21/08/06,06/10/2006
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoCliente(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategorias) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Cliente
	 * 
	 * @author Vivianne Sousa
	 * @date 21/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoClienteCount(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtrar a quantidade de pagamento historicos do cliente
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Cliente
	 * 
	 * @author Rafael Santos
	 * @date 06/10/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoHistoricoClienteCount(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, Collection<Integer> idsArrecadadores,
					String[] idsCategoria) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Aviso Bancario
	 * pesquisarPagamentoAvisoBancario
	 * 
	 * @author Vivianne Sousa
	 * @date 21/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoAvisoBancarioParaPaginacao(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra os pagamento historicos do aviso bancario para paginação
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoAvisoBancarioParaPaginacao(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Aviso Bancario
	 * pesquisarPagamentoAvisoBancario
	 * 
	 * @author Vivianne Sousa
	 * @date 21/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoAvisoBancarioCount(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra a quantidade de pagamento historicos do avio bancario
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoHistoricoAvisoBancarioCount(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, Collection<Integer> idsArrecadadores,
					String[] idsCategoria) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Vivianne Sousa
	 * @date 22/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoImovelCount(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtar a quantiade de pagamento historicos do imovel [UC0255] Filtrar
	 * Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoHistoricoImovelCount(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, Collection<Integer> idsArrecadadores,
					String[] idsCategoria) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Vivianne Sousa
	 * @date 22/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoImovelParaPaginacao(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra os pagamento historicos do Imovel para paginação
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoImovelParaPaginacao(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * para o Relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 29/08/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoImovelRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um sql que será usado na pesquisa de pagamentos
	 * para o Relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 12/12/2006
	 * @param FiltroPagamento
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarPagamentoClienteRelatorio(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String indicadorTotalizarPorDataPagamento, Collection<Integer> idsArrecadadores, String[] idsCategoria)
					throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * para o Relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 29/08/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoAvisoBancarioRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * para o Relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 29/08/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoMovimentoArrecadadorRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * para o Relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 29/08/2006
	 * @author Saulo Lima
	 * @date 08/01/2009
	 *       Nova forma de preencher o PagamentoRelatorioHelper e acréscimo do Generics nas coleções
	 * @param FiltroPagamento
	 * @return Collection<PagamentoRelatorioHelper>
	 * @throws ControladorException
	 */
	public Collection<PagamentoRelatorioHelper> pesquisarPagamentoLocalidadeRelatorio(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Rafael Corrêa
	 * @date 29/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoMovimentoArrecadadorCount(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, Collection<Integer> idsArrecadadores,
					String[] idsCategoria) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Saulo Lima
	 * @date 25/08/2009
	 * @return Integer
	 * @throws ControladorException
	 */
	public Integer pesquisarPagamentoHistoricoMovimentoArrecadadorCount(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, Collection<Integer> idsArrecadadores,
					String[] idsCategoria) throws ControladorException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Rafael Corrêa
	 * @date 29/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoLocalidadeCount(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Filtra a quantiadade dos Pagamento Historicos da Localidade
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoHistoricoLocalidadeCount(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, Collection<Integer> idsArrecadadores,
					String[] idsCategoria) throws ControladorException;

	public Integer verificarExistenciaGuiaDevolucao(Integer idGuiaDevolucao) throws ControladorException;

	/**
	 * Pesquisa os avisos bancários para o relatório através das opções
	 * selecionadas no Filtrar Aviso Bancário
	 * 
	 * @author Rafael Corrêa
	 * @date 04/09/06
	 * @return Collection<AvisoBancarioRelatorioHelper>
	 * @throws ControladorException
	 */
	public Collection pesquisarAvisoBancarioRelatorio(AvisoBancarioHelper avisoBancarioHelper) throws ControladorException;

	/**
	 * Pesquisa os avisos deduções de um aviso bancário para o relatório através
	 * do id do aviso bancário
	 * 
	 * @author Rafael Corrêa
	 * @date 05/09/06
	 * @return Collection<DeducoesRelatorioHelper>
	 * @throws ControladorException
	 */
	public Collection pesquisarAvisoDeducoesAvisoBancarioRelatorio(Integer idAvisoBancario) throws ControladorException;

	/**
	 * Pesquisa os avisos acertos de um aviso bancário para o relatório através
	 * do id do aviso bancário
	 * 
	 * @author Rafael Corrêa
	 * @date 05/09/06
	 * @return Collection<AcertosRelatorioHelper>
	 * @throws ControladorException
	 */
	public Collection pesquisarAvisoAcertosAvisoBancarioRelatorio(Integer idAvisoBancario) throws ControladorException;

	/**
	 * Pesquisa dos dados diários da arrecadação
	 * [UC0333] Filtrar Dados Diários da Arrecadação
	 * 
	 * @author Rafael Santos
	 * @date 05/09/2006
	 * @return
	 */
	public Collection filtrarDadosDiariosArrecadacao(String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String idLocalidade,
					String idGerenciaRegional, String unidadeNegocioId, String idArrecadador, String idElo, String[] idsImovelPerfil,
					String[] idsLigacaoAgua, String[] idsLigacaoEsgoto, String[] idsDocumentosTipos, String[] idsCategoria,
					String[] idsEsferaPoder, String setorComercial, String idConcessionaria) throws ControladorException;

	/**
	 * Pesquisa dos dados diários da arrecadação pela Gerencia
	 * [UC0333] Filtrar Dados Diários da Arrecadação
	 * 
	 * @author Rafael Santos
	 * @date 05/09/2006
	 * @return
	 */
	public Collection filtrarDadosDiariosArrecadacaoValoresDiarios(String idGerenciaRegional) throws ControladorException;

	/**
	 * Pesquisa os dados da Guia de Pagamento necessários para o relatório
	 * através do id da Guia de Pagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 03/10/06
	 * @return Collection<GuiaPagamentoRelatorioHelper>
	 * @throws ControladorException
	 */

	public Collection<GuiaPagamentoRelatorioHelper> pesquisarGuiaPagamentoRelatorio(
					Collection<GuiaPagamentoPrestacaoHelper> prestacoesGuiaPagamento, String[] registrosImpressao)
					throws ControladorException;

	/**
	 * Pesquisa os dados da Guia de Devolução necessários para o relatório
	 * através do id da Guia de Devolução
	 * 
	 * @author Ana Maria
	 * @date 05/10/06
	 * @return Collection<GuiaDevolucaoRelatorioHelper>
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */

	public Collection<GuiaDevolucaoRelatorioHelper> pesquisarGuiaDevolucaoRelatorio(String[] ids) throws ControladorException;

	/**
	 * Faz a pesquisa de devoluçãoHistorico fazendo os carregamentos de
	 * clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 09/10/2006
	 * @param FiltroDevolucaoHistorico
	 * @return Collection<DevolucaoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<DevolucaoHistorico> pesquisarDevolucaoHistorico(FiltroDevolucaoHistorico filtroDevolucaoHistorico)
					throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * histórico para o Relatório
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos histórico do tipo Debito a Cobrar do Cliente
	 * 
	 * @author Vivianne Sousa
	 * @date 17/10/06
	 * @author Saulo Lima
	 * @date 08/01/2009
	 *       Nova forma de preencher o PagamentoRelatorioHelper e acréscimo do Generics nas coleções
	 * @return Collection<PagamentoRelatorioHelper>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoRelatorioHelper> pesquisarPagamentoHistoricoLocalidadeRelatorio(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Consulta dados da tabela dados diarios arrecadacao
	 * 
	 * @author Rafael Santos
	 * @created 21/10/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public ArrecadacaoDadosDiarios consultarDadosDiarios(int idGerenciaRegional, int idLocalidade, int idElo) throws ControladorException;

	/**
	 * Este método se destina a validar todas as situações e particularidades da
	 * inserir guia de devolução no momento da exibição.
	 * [FS0002] Validar registro de atendimento [FS0004] Validar ordem de
	 * servico.
	 * 
	 * @author Rafael Pinto
	 * @date 06/11/2006
	 * @param RegistroAtendimento
	 *            ,OrdemServico
	 */
	public void validarExibirInserirGuiaDevolucao(RegistroAtendimento ra, OrdemServico ordemServico) throws ControladorException;

	/**
	 * Metodo responsável pela remoção das guias de devolução
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 24/11/2006
	 * @param idImovel
	 * @param usuarioLogado
	 * @param ids
	 * @param pacoteNomeObjeto
	 * @param operacaoEfetuada
	 * @param acaoUsuarioHelper
	 * @throws ControladorException
	 */
	public void removerGuiaDevolucao(String idImovel, Usuario usuarioLogado, String[] ids, String pacoteNomeObjeto,
					OperacaoEfetuada operacaoEfetuada, Collection<UsuarioAcaoUsuarioHelper> acaoUsuarioHelper) throws ControladorException;

	/**
	 * [UC0263] Filtrar Movimento dos Arrecadadores
	 * 
	 * @author Vivianne Sousa
	 * @date 30/11/06
	 * @param codigoBanco
	 * @param codigoRemessa
	 * @param descricaoIdentificacaoServico
	 * @param numeroSequencialArquivo
	 * @param dataGeracaoInicio
	 * @param dataGeracaoFim
	 * @param ultimaAlteracaoInicio
	 * @param ultimaAlteracaoFim
	 * @param descricaoOcorrencia
	 * @param indicadorAceitacao
	 * @param numeroPagina
	 * @param concessionaria
	 * @param indicadorAbertoFechado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<FiltrarMovimentoArrecadadoresHelper> filtrarMovimentoArrecadadorParaPaginacao(String codigoBanco,
					String codigoRemessa,
					String descricaoIdentificacaoServico, String idImovel, String numeroSequencialArquivo, Date dataGeracaoInicio,
					Date dataGeracaoFim, Date ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia,
					String indicadorAceitacao, Integer numeroPagina, String idConcessionaria) throws ControladorException;

	/**
	 * [UC0263] Filtrar Movimento dos Arrecadadores
	 * 
	 * @author Vivianne Sousa
	 * @date 30/11/06
	 * @param codigoBanco
	 * @param codigoRemessa
	 * @param descricaoIdentificacaoServico
	 * @param numeroSequencialArquivo
	 * @param dataGeracaoInicio
	 * @param dataGeracaoFim
	 * @param ultimaAlteracaoInicio
	 * @param ultimaAlteracaoFim
	 * @param descricaoOcorrencia
	 * @param indicadorAceitacao
	 * @param concessionaria
	 * @param indicadorAbertoFechado
	 * @throws ControladorException
	 */
	public Integer filtrarMovimentoArrecadadoresCount(String codigoBanco, String codigoRemessa, String descricaoIdentificacaoServico,
					String idImovel, String numeroSequencialArquivo, Date dataGeracaoInicio, Date dataGeracaoFim,
					Date ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia, String indicadorAceitacao,
					String idConcessionaria)
					throws ControladorException;

	/**
	 * Pesquisar os ids das localidades que possuem pagamentos
	 * [UC0301] Gerar Dados Diários da Arrecadação
	 * 
	 * @author Pedro Alexandre
	 * @date 04/12/2006
	 * @return
	 * @throws ControladorException
	 */
	// public Collection<Integer> pesquisarIdsLocalidadeComPagamentos() throws
	// ControladorException;

	/**
	 * Pesquisa o imóvel pelo id fazendo os carregamentos necessários
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corrêa
	 * @date 16/12/06
	 * @return Imovel
	 * @throws ControladorException
	 */
	public Imovel pesquisarImovelPagamento(Integer idImovel) throws ControladorException;

	/**
	 * Pesquisa o cliente pelo id fazendo os carregamentos necessários
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corrêa
	 * @date 16/12/06
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarClientePagamento(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa o endereço de correspondência do cliente pelo seu id fazendo os
	 * carregamentos necessários
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corrêa
	 * @date 16/12/06
	 * @return ClienteEndereco
	 * @throws ControladorException
	 */
	public ClienteEndereco pesquisarClienteEnderecoPagamento(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa o telefone padrão do cliente pelo seu id fazendo os
	 * carregamentos necessários
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corrêa
	 * @date 16/12/06
	 * @return ClienteFone
	 * @throws ControladorException
	 */
	public ClienteFone pesquisarClienteFonePagamento(Integer idCliente) throws ControladorException;

	/**
	 * Pesquisa os clientes do imóvel pelo seu id do imóvel fazendo os
	 * carregamentos necessários
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corrêa
	 * @date 16/12/06
	 * @return Collection<ClienteImovel>
	 * @throws ControladorException
	 */
	public Collection<ClienteImovel> pesquisarClientesImoveisPagamento(Integer idImovel) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoLocalidade
	 * 
	 * @author Rafael Corrêa
	 * @date 21/12/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoMovimentoArrecadadorParaPaginacao(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Saulo Lima
	 * @date 25/08/2009
	 * @return Collection<PagamentoHistorico>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoMovimentoArrecadadorParaPaginacao(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ControladorException;

	public Collection<Integer> pesquisarIdsLocalidadeComPagamentosOuDevolucoes() throws ControladorException;

	/**
	 * [UC0268] - Apresentar Análise do Aviso Bancário
	 * 
	 * @author Vivianne Sousa
	 * @date 13/12/2006
	 * @return Collection<AcertosAvisoBancarioHelper>
	 * @throws ControladorException
	 */

	public Collection<AcertosAvisoBancarioHelper> pesquisarAcertosAvisoBancario(Integer idAvisoBancario,
					Integer indicadorArrecadacaoDevolucao) throws ControladorException;

	/**
	 * [UC0268] - Apresentar Análise do Aviso Bancário
	 * 
	 * @author Vivianne Sousa
	 * @date 13/12/2006
	 * @return Collection<DeducoesHelper>
	 * @throws ControladorException
	 */

	public Collection<DeducoesHelper> pesquisarDeducoesAvisoBancario(Integer idAvisoBancario) throws ControladorException;

	/**
	 * [UC0263] Filtrar Movimento dos Arrecadadores
	 * 
	 * @author Vivianne Sousa
	 * @date 30/11/06
	 * @param codigoBanco
	 * @param codigoRemessa
	 * @param descricaoIdentificacaoServico
	 * @param numeroSequencialArquivo
	 * @param dataGeracaoInicio
	 * @param dataGeracaoFim
	 * @param ultimaAlteracaoInicio
	 * @param ultimaAlteracaoFim
	 * @param descricaoOcorrencia
	 * @param indicadorAceitacao
	 * @param indicadorAbertoFechado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarMovimentoArrecadadorParaRelatorio(String codigoBanco, String codigoRemessa,
					String descricaoIdentificacaoServico, String numeroSequencialArquivo, Date dataGeracaoInicio, Date dataGeracaoFim,
					Date ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia, String indicadorAceitacao,
					String indicadorAbertoFechado, String idConcessionaria) throws ControladorException;

	/**
	 * [UC0263] Filtrar Movimento dos Arrecadadores
	 * 
	 * @author Vivianne Sousa
	 * @date 04/01/07
	 * @param codigoBanco
	 * @param codigoRemessa
	 * @param descricaoIdentificacaoServico
	 * @param numeroSequencialArquivo
	 * @param dataGeracaoInicio
	 * @param dataGeracaoFim
	 * @param ultimaAlteracaoInicio
	 * @param ultimaAlteracaoFim
	 * @param descricaoOcorrencia
	 * @param indicadorAceitacao
	 * @param indicadorAbertoFechado
	 * @throws ControladorException
	 */
	public Integer filtrarMovimentoArrecadadoresRelatorioCount(String codigoBanco, String codigoRemessa,
					String descricaoIdentificacaoServico, String numeroSequencialArquivo, Date dataGeracaoInicio, Date dataGeracaoFim,
					Date ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia, String indicadorAceitacao,
					String indicadorAbertoFechado, String idConcessionaria) throws ControladorException;

	/**
	 * [UC0276] Encerrar Arrecadação do Mês
	 * Para cada guia de pagamento transferida para o histórico atualiza o
	 * indicador de que a guia de pagamento está no histórico.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param colecaoGuiasPagamento
	 * @throws ControladorException
	 */
	public void atualizarIndicadorGuiaPagamentoNoHistorico(Collection colecaoGuiasPagamento) throws ControladorException;


	/**
	 * [UC0276] Gerar Resumo do Faturamento / [UC0188] - Manter Guia de Pagamento
	 * Transfere para o histórico as guias de pagamentos e os relacionamentos
	 * ligados a ela.
	 * 
	 * @author Felipe Rosacruz
	 * @date 31/05/2013
	 *       Alteração para a Geração de Históricos por Prestação de Guia de Pagamento
	 * @param colecaoGuiasPagamento
	 * @throws ControladorException
	 */
	public void transferirGuiaPagamentoParaHistorico(Collection<GuiaPagamento> colecaoGuiasPagamento) throws ControladorException;

	/**
	 * [UC0276] Gerar Resumo do Faturamento / [UC0188] - Manter Guia de Pagamento
	 * Transfere para o histórico as guias de pagamentos e os relacionamentos
	 * ligados a ela.
	 * 
	 * @author Felipe Rosacruz
	 * @date 31/05/2013
	 *       Alteração para a Geração de Históricos por Prestação de Guia de Pagamento. Chamar esse
	 *       método para registrar a operação.
	 * @param colecaoGuiasPagamento
	 * @throws ControladorException
	 */
	public void transferirGuiaPagamentoParaHistorico(Collection<GuiaPagamento> colecaoGuiasPagamento, Usuario usuario, Integer idOperacao)
					throws ControladorException;

	/**
	 * Transfere as 'Guias de Pagamento Prestação Histórico' para 'Guia de Pagamento Prestação'
	 * segundo o parâmetro numeroPrestação. Caso 'Guia de
	 * Pagamento' esteja no histórico, ela também será recuperada.
	 * 
	 * @author Saulo Lima
	 * @date 10/07/2009
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @throws ControladorException
	 */
	public void transferirGuiaPagamentoPrestacaoHistoricoParaGuiaPagamentoPrestacao(Integer idGuiaPagamento, Short numeroPrestacao)
					throws ControladorException;

	/**
	 * [UC0276] Encerrar Arrecadação do Mês
	 * Transfere para o histórico as guias de pagamentos e os relacionamentos
	 * ligados a ela.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param colecaoGuiasPagamento
	 * @param anoMesFaturamentoSistemaParametro
	 * @throws ControladorException
	 */
	public void transferirPagamentoParaHistorico(Collection<Pagamento> colecaoPagamentos) throws ControladorException;

	/**
	 * [UC0276] Encerrar Arrecadação do Mês
	 * Transfere para o histórico as devoluções informadas.
	 * 
	 * @author Administrador
	 * @date 09/01/2007
	 * @param colecaoDevolucoes
	 * @throws ControladorException
	 */
	public void transferirDevolucaoParaHistorico(Collection<Devolucao> colecaoDevolucoes) throws ControladorException;

	/**
	 * [UC0276] Encerrar Arrecadação do Mês
	 * Atualiza o ano/mês de referência da arrecadação.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param anoMesArrecadacaoSistemaParametro
	 * @throws ControladorException
	 */
	public void atualizarAnoMesArrecadacao(Integer anoMesArrecadacaoSistemaParametro) throws ControladorException;

	/**
	 * Metódo responsável por inserir uma Agência Bancaria
	 * [UC0000 - Inserir Agência Bancaria
	 * 
	 * @author Thiago Tenório
	 * @date 27/06/2006, 16/11/2006
	 * @param agencia
	 *            bancaria
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirAgenciaBancaria(Agencia agencia) throws ControladorException;

	/**
	 * [UC0391] Atualizar Agência Bancária.
	 * 
	 * @author Thiago Tenório
	 * @date 01/11/2006
	 * @param
	 * @throws ControladorException
	 */
	public void atualizarAgenciaBancaria(Agencia agencia) throws ControladorException;

	/**
	 * [UC0506] Inserir Arrecadador
	 * Inclusão de um novo arrecadador.
	 * 
	 * @author Marcio Roberto
	 * @date 29/01/2007
	 * @param String
	 *            idAgente, String idCliente, String inscricaoEstadual, String
	 *            idImovel, Usuario usuarioLogado
	 * @throws ControladorException
	 */
	public Integer inserirArrecadador(String idAgente, String idCliente, String inscricaoEstadual, String idImovel, Usuario usuarioLogado,
					Short indicadorCaixaEmpresa)
					throws ControladorException;

	/**
	 * @author Ana Maria
	 * @date 29/01/2007
	 * @param idGuiaPagamento
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiaPagemento(Integer idGuiaPagamento) throws ControladorException;

	/**
	 * [UC0276] Encerrar Arrecadação do Mês
	 * Metodo responsável pela transferência das contas, guias de pagamento,
	 * pagamentos e devoluções para o histórico.
	 * 
	 * @author Pedro Alexandre
	 * @date 06/02/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @throws ControladorException
	 */
	public void gerarHistoricoParaEncerrarArrecadacaoMes(Integer anoMesReferenciaArrecadacao, Integer idLocalidade,
					int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * @author Marcio Roberto
	 * @date 07/02/2007
	 * @param codigoAgente
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaAgente(Integer codigoAgente) throws ControladorException;

	/**
	 * [UC0507] Manter Arrecadador
	 * Remover Arrecadador
	 * 
	 * @author Marcio Roberto
	 * @date 08/02/2007
	 * @pparam ids, usuarioLogado
	 * @throws ControladorException
	 */
	public void removerArrecadador(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * [UC507] Manter Arrecadador [SB0001]Atualizar Arrecadador
	 * 
	 * @author Marcio Roberto
	 * @date 8/02/2007
	 * @pparam
	 * @throws ControladorException
	 */
	public void atualizarArrecadador(Arrecadador arrecadador, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Atualiza logradouroCep de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ControladorException;

	/**
	 * Atualiza logradouroBairro de um ou mais imóveis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ControladorException;

	/**
	 * Este caso de uso cria um filtro que será usado na pesquisa de pagamentos
	 * para o Relatório
	 * 
	 * @author Rafael Corrêa
	 * @date 29/08/2006
	 * @param FiltroPagamento
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarPagamentoImovelAmbosRelatorio(String idImovel) throws ControladorException;

	/**
	 * Metódo responsável por inserir uma Conta Bancaria
	 * [UC0000 - Inserir Conta Bancaria
	 * 
	 * @author Thiago Tenório
	 * @date 27/06/2006, 16/11/2006
	 * @param agencia
	 *            bancaria
	 * @param usuarioLogado
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirContaBancaria(ContaBancaria contaBancaria) throws ControladorException;

	/**
	 * [UC0391] Atualizar Conta Bancária.
	 * 
	 * @author Thiago Tenório
	 * @date 01/11/2006
	 * @param
	 * @throws ControladorException
	 */
	public void atualizarContaBancaria(ContaBancaria contaBancaria) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito - remover guia pagamento referente
	 * ao parcelamento
	 * remove a guia de pagamento do Pagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 06/03/2007
	 * @param
	 * @return void
	 */
	public void removerGuiaPagamentoPagamento(Integer idPagamento) throws ControladorException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito - remover guia pagamento referente
	 * ao parcelamento historico
	 * remove a guia de pagamento do Pagamento
	 * 
	 * @author Vitor
	 * @date 21/08/2008
	 * @param
	 * @return void
	 */
	public void removerGuiaPagamentoPagamentoHistorico(Integer idPagamento) throws ControladorException;

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 20/03/2007
	 * @param idDebitoACobrar
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadePagamentosPorDebitoACobrar(Integer idDebitoACobrar) throws ControladorException;

	/**
	 * Pesquisa os movimentos dos arrecadores para a geração do relatório
	 * [UCXXXX] Acompanhar Movimento dos Arrecadadores
	 * 
	 * @author Rafael Corrêa
	 * @param idConcessionaria
	 * @date 02/04/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarMovimentoArrecadadoresRelatorio(Integer mesAnoReferencia, Integer idArrecadador, Integer idFormaArrecadacao,
					Date dataPagamentoInicial, Date dataPagamentoFinal, Integer idConcessionaria) throws ControladorException;

	/**
	 * Relatório para acompanhar o movimento dos arrecadadores
	 * 
	 * @author Sávio Luiz
	 * @date 02/04/2007
	 * @param idDebitoACobrar
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public void gerarResumoAcompanhamentoMovimentoArrecadadores(Usuario usuario, String mesAnoReferencia, Arrecadador arrecadador,
					ArrecadacaoForma arrecadacaoForma, Concessionaria concessionaria) throws ControladorException;

	public Collection<Integer> pesquisarIdsSetoresComPagamentosOuDevolucoes() throws ControladorException;

	public void gerarHistoricoConta(Integer anoMesReferenciaArrecadacao, Integer idSetorComercial, int idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * Seleciona os pagamentos histórios de um aviso
	 * 
	 * @author Rafael Corrêa
	 * @date 23/04/2007
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarPagamentoHistoricoAvisoBancario(Integer idAvisoBancario) throws ControladorException;

	/**
	 * [UC0150] Retificar Conta
	 * 
	 * @author Vivianne Sousa
	 * @data 23/04/2006
	 * @param idConta
	 * @return idParcelamento
	 */
	public Pagamento pesquisarPagamentoDeConta(Integer idConta) throws ControladorException;

	/**
	 * @author Sávio Luiz
	 * @data 28/04/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public Integer pesquisarIdPagamentoDoDebitoACobrar(Integer idDebitoACobrar) throws ControladorException;

	/**
	 * @author Sávio Luiz
	 * @data 28/04/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public Integer pesquisarIdPagamentoDaGuia(Integer idGuiaPagamento) throws ControladorException;

	/**
	 * [UC0259] - Processar Pagamento com código de Barras [SB0008] - Alterar
	 * Vencimento dos Itens do documento de cobrança Autor: Sávio Luiz
	 * Data:15/02/2006
	 */

	public void alterarVencimentoItensDocumentoCobranca(Integer idCobrancaDocumento, Date dataEmissao) throws ControladorException;

	/**
	 * [UC0509] Inserir Contrato Arrecadador -
	 * 
	 * @author Marcio Roberto
	 * @date 22/03/2007
	 * @param
	 * @return void
	 */
	public Integer inserirContratoArrecadador(ArrecadadorContrato contrato,
					Collection<ArrecadadorContratoTarifa> colecaoArrecadadorContratoTarifa, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * @author Marcio Roberto
	 * @date 09/04/2007
	 * @param codigoArrecadador
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaArrecadador(Integer codigoArrecadador) throws ControladorException;

	/**
	 * @author Marcio Roberto
	 * @date 09/04/2007
	 * @param numeroContrato
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public boolean verificarExistenciaContrato(Integer numeroContrato) throws ControladorException;

	/**
	 * [UC507] Manter Contrato de Arrecadador [SB0001]Atualizar Contrato Arrecadador
	 * 
	 * @author Marcio Roberto
	 * @date 12/04/2007
	 * @pparam
	 * @throws ControladorException
	 */
	public void atualizarContratoArrecadador(ArrecadadorContrato arrecadadorContrato,
					Collection<ArrecadadorContratoTarifa> colecaoArrecadadorContratoTarifa, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0510] Manter Arrecadador
	 * Remover Contrato de Arrecadador
	 * 
	 * @author Marcio Roberto
	 * @date 12/04/2007
	 * @pparam ids, usuarioLogado
	 * @throws ControladorException
	 */
	public void removerContratoArrecadador(String[] ids, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Retorna uma coleção de ids de categoria
	 * 
	 * @author Pedro Alexandre
	 * @date 13/06/2007
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Integer> pesquisarIdsCategoria() throws ControladorException;

	/**
	 * Pesquisar pagamentos pelo aviso bancário
	 * 
	 * @author Ana Maria
	 * @date 11/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public PagamentosDevolucoesHelper filtrarPagamentos(FiltroPagamento filtroPagamento, FiltroPagamentoHistorico filtroPagamentoHistorico)
					throws ControladorException;

	/**
	 * Pesquisar devoluções pelo aviso bancário
	 * 
	 * @author Ana Maria
	 * @date 11/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public PagamentosDevolucoesHelper filtrarDevolucoes(FiltroDevolucao filtroDevolucao, FiltroDevolucaoHistorico filtroDevolucaoHistorico)
					throws ControladorException;

	/**
	 * Pesquisar valores de arrecadação e devolução do aviso bancário
	 * 
	 * @author Ana Maria
	 * @date 14/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public ValoresArrecadacaoDevolucaoAvisoBancarioHelper pesquisarValoresAvisoBancario(Integer idAvisoBancario)
					throws ControladorException;

	/**
	 * Atualizar Pagamentos e Aviso Bancário
	 * 
	 * @author Ana Maria
	 * @date 15/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public void atualizarAvisoBancarioPagamentos(Collection<Integer> idsPagamentos, Collection<Integer> idsPagamentosHistorico,
					String arrecadacaoCalculadoDepoisOrigem, String arrecadacaoCalculadoDepoisDestino, Integer idAvisoBancarioO,
					Integer idAvisoBancarioD, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Atualizar Devoluções e Aviso Bancário
	 * 
	 * @author Ana Maria
	 * @date 15/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public void atualizarAvisoBancarioDevolucoes(Collection<Integer> idsDevolucoes, Collection<Integer> idsDevolucoesHistorico,
					String devolucaoInformadoDepoisOrigem, String devolucaoCalculadoDepoisOrigem, String devolucaoInformadoDepoisDestino,
					String devolucaoCalculadoDepoisDestino, Integer idAvisoBancarioO, Integer idAvisoBancarioD, Usuario usuarioLogado)
					throws ControladorException;

	/**
	 * [UC0263] Filtrar Movimento dos Arrecadadores - Relatório
	 * 
	 * @author Ana Maria
	 * @date 13/07/07
	 * @param codigoBanco
	 * @param codigoRemessa
	 * @param descricaoIdentificacaoServico
	 * @param numeroSequencialArquivo
	 * @param dataGeracaoInicio
	 * @param dataGeracaoFim
	 * @param ultimaAlteracaoInicio
	 * @param ultimaAlteracaoFim
	 * @param descricaoOcorrencia
	 * @param indicadorAceitacao
	 * @param indicadorAbertoFechado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> filtrarIdsMovimentoArrecadador(String codigoBanco, String codigoRemessa,
					String descricaoIdentificacaoServico, String idImovel, String numeroSequencialArquivo, Date dataGeracaoInicio,
					Date dataGeracaoFim, Date ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia,
					String indicadorAceitacao, String idConcessionaria) throws ControladorException;

	/**
	 * [UC0619] Gerar Relação de Acompanhamento dos Movimentos Arrecadadores por NSA
	 * 
	 * @author Ana Maria
	 * @date 12/07/2007
	 * @param idMovimentoArrecadador
	 * @return
	 */
	public Collection<MovimentoArrecadadoresPorNSAHelper> gerarMovimentoArrecadadoresNSA(Collection<Integer> idsArrecadadorMovimento,
					Integer codigoFormaArrecadacao, String idConcessionaria) throws ControladorException;

	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados
	 * [UC0229] Obter Representação Numérica do Código de Barras
	 * Formata a identificação do pagamento de acordo com o tipo de pagamento
	 * informado
	 * [SB0001] Obter Identificação do Pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 20/04/2006
	 * @param tipoPagamento
	 * @param idLocalidade
	 * @param matriculaImovel
	 * @param anoMesReferenciaConta
	 * @param digitoVerificadorRefContaModulo10
	 * @param idTipoDebito
	 * @param anoEmissaoGuiaPagamento
	 * @param sequencialDocumentoCobranca
	 * @param idTipoDocumento
	 * @param idCliente
	 * @param seqFaturaClienteResponsavel
	 * @return
	 */
	public String obterIdentificacaoPagamento(Integer tipoPagamento, Integer idLocalidade, Integer matriculaImovel,
					String mesAnoReferenciaConta, Integer digitoVerificadorRefContaModulo10, Integer idTipoDebito,
					String anoEmissaoGuiaPagamento, String sequencialDocumentoCobranca, Integer idTipoDocumento, Integer idCliente,
					Integer seqFaturaClienteResponsavel, Integer idOpcao) throws ControladorException;

	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados
	 * [UC0229] Obter Representação Numérica do Código de Barras
	 * 
	 * @author Pedro Alexandre
	 * @date 20/04/2006
	 * @author eduardo henrique
	 * @date 14/08/2008
	 *       Alterado para Guia de Pagamento utilizar novo método de Linha de Código de Barras. Na
	 *       versão final, todos devem usar o novo método.
	 *       Adicionado Parâmetro de Numero da Prestação
	 * @param tipoPagamento
	 * @param valorCodigoBarra
	 * @param idLocalidade
	 * @param matriculaImovel
	 * @param anoMesReferenciaConta
	 * @param digitoVerificadorRefContaModulo10
	 * @param idTipoDebito
	 * @param anoEmissaoGuiaPagamento
	 * @param sequencialDocumentoCobranca
	 * @param idTipoDocumento
	 * @param idCliente
	 * @param seqFaturaClienteResponsavel
	 * @param numeroPrestacaoDocumento
	 * @return
	 * @throws ParametroNaoInformadoException
	 */
	public String obterRepresentacaoNumericaCodigoBarra(Integer tipoPagamento, BigDecimal valorCodigoBarra, Integer idLocalidade,
					Integer matriculaImovel, String mesAnoReferenciaConta, Integer digitoVerificadorRefContaModulo10, Integer idTipoDebito,
					String anoEmissaoGuiaPagamento, String sequencialDocumentoCobranca, Integer idTipoDocumento,
					Integer idClienteResponsavel, Integer seqFaturaClienteResponsavel, Short numeroPrestacaoDocumento,
					Integer idOpcaoPreParcelamento, Integer idGuiaPagamento, Integer idCliente) throws ControladorException;

	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados.
	 * [UC0229] Obter Representação Numérica do Código de Barras
	 * Formata a identificação do pagamento de acordo com o tipo de pagamento
	 * informado.
	 * (Método foi sobrescrito pois será utilizado atendendo às mudanças do GSANPCG)
	 * [SB0001] Obter Identificação do Pagamento
	 * 
	 * @author eduardo henrique
	 * @date 14/08/2008
	 * @since v0.04
	 * @param tipoPagamento
	 *            - Tipo do Documento
	 *            3: Conta
	 *            4 ou 6: Guia Pagamento
	 *            7: Fatura
	 * @param idIdentificacaoImovel
	 *            - Pode ser a Matrícula do Imóvel ou o Código do Cliente
	 * @param idDocumento
	 *            - Numero do Documento
	 * @param idPrestacaoDocumento
	 *            - Numero da Prestação do Documento (se existir)
	 * @param idCliente
	 *            - Código do Cliente
	 * @param seqFaturaClienteResponsavel
	 *            - Numero da Fatura
	 * @return
	 * @throws ControladorException
	 */
	public String obterIdentificacaoPagamento(Integer tipoPagamento, Integer idIdentificacaoImovel, Integer idDocumento,
					Integer idPrestacaoDocumento, Integer idCliente, Integer seqFaturaClienteResponsavel, String mesAnoReferenciaConta)
					throws ControladorException;

	/**
	 * Processamento Rápido
	 * 
	 * @author Raphael Rossiter
	 * @date 17/08/2007
	 * @return Collection<Conta>
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> pesquisarContaComPagamentoHistorico() throws ControladorException;

	/**
	 * Inserir uma coleção de pagamentos informados manualmente
	 * 
	 * @author Raphael Rossiter
	 * @date 26/09/2007
	 * @author Vitor Hora, Saulo Lima
	 * @date 08/10/2008, 26/11/2008
	 *       Chamada ao classificarPagamentos
	 * @param Collection
	 *            <Pagamento>
	 * @param Usuario
	 * @param AvisoBancario
	 * @return Integer
	 *         Id do último pagamento inserido
	 * @throws ControladorException
	 */
	public Integer inserirPagamentos(Collection<Pagamento> colecaoPagamento, Usuario usuarioLogado, AvisoBancario avisoBancario,
					Integer idTipoOperacao, boolean usuarioConfirmou) throws ControladorException, InserirPagamentoException;

	/**
	 * Inserir Pagamentos por código de barras
	 * 
	 * @author Raphael Rossiter
	 * @date 30/10/2007
	 * @return
	 * @throws ControladorException
	 */
	public Integer inserirPagamentosCodigoBarras(Collection<Pagamento> colecaoPagamentos, Collection<Devolucao> colecaoDevolucoes,
					Usuario usuarioLogado, AvisoBancario avisoBancario, boolean usuarioConfirmou) throws ControladorException,
					InserirPagamentoException;

	/**
	 * Obtém a representação númerica do código de barras da Ficha de Compensação
	 * [UC0716] Obter Representação Numérica do Código de Barras da Ficha de Compensação
	 * 
	 * @author Vivianne Sousa
	 * @date 12/11/2007
	 * @param codigoBanco
	 * @param codigoMoeda
	 * @param valorCodigoBarra
	 * @param nossoNumero
	 * @param carteira
	 * @param fatorVencimento
	 * @return
	 * @throws ParametroNaoInformadoException
	 */
	public String obterRepresentacaoNumericaCodigoBarraFichaCompensacao(String especificacaoCodigoBarra) throws ControladorException;

	/**
	 * Obtém a representação númerica do código de barras da Ficha de Compensação
	 * [UC0716] Obter Representação Numérica do Código de Barras da Ficha de Compensação
	 * 
	 * @author Vivianne Sousa
	 * @date 12/11/2007
	 * @param codigoBanco
	 * @param codigoMoeda
	 * @param valorCodigoBarra
	 * @param nossoNumero
	 * @param carteira
	 * @param fatorVencimento
	 * @return
	 * @throws ParametroNaoInformadoException
	 */
	public String obterEspecificacaoCodigoBarraFichaCompensacao(String codigoBanco, String codigoMoeda, BigDecimal valorCodigoBarra,
					String nossoNumeroSemDV, String carteira, String fatorVencimento) throws ControladorException;

	/**
	 * [UC0626] Gerar Resumo de Metas Acumulado no Mês (CAERN)
	 * 
	 * @author Sávio Luiz
	 * @data 28/11/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public Collection pesquisarPagamentoDeContas(Collection colecaoConta) throws ControladorException;

	/**
	 * [UC0739] - Informar Situação de Expurgo do Pagamento
	 * Autor: Sávio Luiz
	 * Data: 02/01/2008
	 */
	public Object[] gerarColecaoDadosPagamentoPelaData(String dataPagamento, Integer idCliente) throws ControladorException;

	/**
	 * [UC0739] - Informar Situação de Expurgo do Pagamento
	 * Autor: Sávio Luiz
	 * Data: 02/01/2008
	 */
	public void atualizarSituacaoExpurgoPagamento(Collection colecaoPagamento) throws ControladorException;

	/**
	 * Método que recebe uma coleção de Pagamentos com os Ids preenchidos e retorna uma coleção com
	 * os objetos preenchidos
	 * [UC0300] - Classificar Pagamentos e Devoluções
	 * 
	 * @author Saulo Lima
	 * @date 24/11/2008
	 * @param colecaoPagamentos
	 *            <Pagamento>
	 * @return colecaoRetorno<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> carregarAtributosPagamento(Collection<Pagamento> colecaoPagamentos) throws ControladorException;

	/**
	 * Método que recebe uma coleção de Pagamentos e retorna uma Coleção de PagamentosHistorico a
	 * partir dos Pagamentos
	 * [UC0300] - Classificar Pagamentos e Devoluções
	 * 
	 * @author Saulo Lima
	 * @date 25/11/2008
	 * @author Saulo Lima
	 * @date 26/01/2009
	 *       Inclusão do parâmetro pagamentoSituacaoId
	 * @param colecaoPagamentos
	 *            <Pagamento>
	 *            colecaoPagamentos
	 * @param Integer
	 *            Id da situacao do pagamento
	 * @return colecaoHistoricoRetorno<PagamentoHistorico>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> criarPagamentoHistoricoDoPagamento(Collection<Pagamento> colecaoPagamentos,
					Integer pagamentoSituacaoId) throws ControladorException;

	/**
	 * Recupera o ID da ContaGeral do pagamento especificado
	 * 
	 * @author Saulo Lima
	 * @date 23/01/2009
	 * @param idPagamento
	 * @return idContaGeral
	 * @throws ControladorException
	 */
	public Integer pesquisarIdDaContaGeralNoPagamento(Integer idPagamento) throws ControladorException;

	/**
	 * [UC1016] Estornar Pagamentos
	 * 
	 * @author Saulo Lima
	 * @date 19/08/2009
	 * @param pagamentoHistorico
	 * @param usuarioLogado
	 * @throws ControladorException
	 */
	public void estornarPagamento(PagamentoHistorico pagamentoHistorico, Usuario usuarioLogado) throws ControladorException;

	/**
	 * Método responsável por consultar os dados de um PagamentoHistorico
	 * 
	 * @date 17/08/2009
	 * @author Virgínia Melo
	 * @param idPagamentoHistorico
	 * @return PagamentoHistorico
	 */
	public PagamentoHistorico consultarPagamentoHistorico(Integer idPagamentoHistorico) throws ControladorException;

	/**
	 * Inserir devoluções para os pagamentos efetuados a partir do código de
	 * barras
	 * 
	 * @author Raphael Rossiter
	 * @date 30/10/2007
	 * @return
	 * @throws ControladorException
	 */
	public void inserirDevolucoes(Collection<Devolucao> colecaoDevolucoes, Usuario usuarioLogado, AvisoBancario avisoBancario)
					throws ControladorException;

	/**
	 * Atualizar Devolucao
	 * 
	 * @author Fernanda Paiva
	 * @created 03/05/2006
	 * @param valor
	 *            arrecadacao
	 * @exception controladorException
	 *                controlador Exception
	 */
	public void atualizaValorArrecadacaoAvisoBancario(BigDecimal valor, Integer codigoAvisoBancario) throws ControladorException;

	/**
	 * Método responsável por consultar o ultimo cobranca documento
	 * 
	 * @date 02/06/2010
	 * @author isilva
	 * @param idContaHistorico
	 * @return
	 * @throws ControladorException
	 */
	public CobrancaDocumento consultarUltimoCobrancaDocumento(Integer idContaHistorico) throws ControladorException;

	// /**
	// * Método responsável por consultar conta historico
	// * @date 05/06/2010
	// * @author isilva
	// * @return
	// * @throws ControladorException
	// */
	// public ContaHistorico consultarContaHistorico(Integer idContaHistorico) throws
	// ControladorException;

	/**
	 * obtem colecao com dados diarios da arrecadacao
	 * 
	 * @author Genival Barbosa
	 * @date 25/07/2011
	 * @param anoMesReferencia
	 *            ,
	 *            idArrecadador, isArrecadacaoForma
	 * @return Uma Colecao
	 * @throws ControladorException
	 */
	public Collection consultarDadosRelatorioControleDocumentosArrecadacaoAnalitico(int anoMesReferencia, Integer idArrecadador,
					Integer idArrecadacaoForma) throws ControladorException;

	/**
	 * Retorna o objeto distribuido de acordo com o tipo de pagamento
	 * [UC0264] - Distribuir Dados do Código de Barras
	 * [SB0007] - Distribuir Pagamento de Conta
	 * [SF0002] - Distribuir Pagamento de Guia de Pagamento
	 * [SF0003] - Distribuir Pagamento de Documento de Cobrança
	 * [SF0004] - Distribuir Pagamento de Fatura do Cliente Responsável
	 * 
	 * @author Vitor Hora, Saulo Lima
	 * @date 06/08/2008, 04/12/2008
	 * @author eduardo henrique
	 * @date 18/12/2008
	 *       Alteração no método para tratamento de questão de Docs. Agregadores de Legado ADA
	 */
	public Object[] distribuirDadosCodigoBarrasPorTipoPagamentoLegadoADA(String idPagamento, int tipoPagamento, String idEmpresa)
					throws ControladorException;

	/**
	 * Retorna o objeto distribuido de acordo com o tipo de pagamento
	 * [UC0264] - Distribuir Dados do Código de Barras
	 * [SF0001] - Distribuir Pagamento de Conta
	 * [SF0002] - Distribuir Pagamento de Guia de Pagamento
	 * [SF0003] - Distribuir Pagamento de Documento de Cobrança
	 * [SF0004] - Distribuir Pagamento de Fatura do Cliente Responsável
	 * 
	 * @author Sávio Luiz
	 * @date 15/02/2006
	 */
	public Object[] distribuirDadosCodigoBarrasPorTipoPagamento(String idPagamento, int tipoPagamento, String idEmpresa)
					throws ControladorException;

	/**
	 * Identificação da Empresa for ADA (G.05.6 = 0477) e posição 20 a 24(G.05.7 = 00000 e posição
	 * 43 a 44(G.05.7 = 04 ou 03),
	 * 
	 * @author Vitor Hora
	 * @author Saulo Lima
	 * @date 10/06/2009
	 *       Adicionar mais uma identificação: adaTipoDocumento <> "000"
	 */
	public boolean validarRegistroLegadoADA(Short codigoEmpresaFebraban, String adaGrupoFaturamentoConstanteLegado,
					String adaAnoMesReferenciaContaLegado, String adaTipoDocumento);

	/**
	 * definir a origem do pagamento (Pagamento ou PagamentoHistorico) e a operação contabil do
	 * pagamento
	 * 
	 * @author Genival Barbosa
	 * @date 23/09/2011
	 * @param pagamento
	 * @return Uma Colecao
	 * @throws ControladorException
	 */
	public OperacaoContabilHelper definirOrigemOperacaoContabilPagamento(Pagamento pagamento, Boolean ehClassificadoAnterior)
					throws ControladorException;

	/**
	 * @autor Bruno Ferreira dos Santos
	 * @date 26/09/2011
	 * @param imovelId
	 * @return
	 * @throws ControladorException
	 */
	public Collection pesquisarAcrescimoImpontualidade(Integer imovelId) throws ControladorException;

	public void classificarPagamentosRegistroMovimentoArrecadadores(Pagamento pagamento) throws ControladorException;

	public void classificarPagamentosAjuste() throws ControladorException;

	/**
	 * @param idConta
	 * @param indicadorExclusao
	 * @return
	 * @throws ControladorException
	 */
	DebitoAutomaticoMovimento pesquisarUltimoDebitoAutomaticoMovimentoConta(Integer idConta) throws ControladorException;

	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores
	 * 
	 * @author Anderson Italo
	 * @date 11/03/2012
	 * @throws ControladorException
	 */
	public void gerarEnviarRelatorioResumoArrecadacao(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC3053] - Gerar Guia de Pagamento Programa Água Para Todos
	 * Recupera o tipo de débito referente ao Programa Água para Todos
	 * 
	 * @author Luciano Galvão
	 * @date 23/03/2012
	 * @throws ControladorException
	 */
	public DebitoTipo consultarDebitoTipoAguaParaTodos() throws ControladorException;

	/**
	 * [UC3053] - Gerar Guia de Pagamento Programa Água Para Todos
	 * Recupera a coleção de contas que não possuem o motivo de revisão correspondente ao Programa
	 * Água Para Todos
	 * 
	 * @author Luciano Galvão
	 * @date 23/03/2012
	 * @throws ControladorException
	 */
	public Collection obterContasProgramaAguaParaTodos(Imovel imovel) throws ControladorException;

	/**
	 * [UC3053]: Gerar Guia Pagamento Programa Água para Todos
	 * Calcula o valor da Guia de Pagamento para o programa Água Para Todos
	 * 
	 * @param valorContas
	 * @author Luciano Galvão
	 * @date 23/03/2012
	 * @throws ControladorException
	 */
	public BigDecimal calcularValorGuiaAguaParaTodos(BigDecimal valorContas) throws ControladorException;

	/**
	 * [UC3053 - Gerar Guia de Pagamento Programa Água para Todos]
	 * 
	 * @author Luciano Galvão
	 * @date 26/03/2012
	 * @param idImovel
	 * @param valorGuia
	 * @param contas
	 * @param usuario
	 * @throws ControladorException
	 */
	public void gerarGuiaPagamentoProgramaAguaParaTodos(Integer idImovel, BigDecimal valorGuia, Collection<Conta> contas, Usuario usuario)
					throws ControladorException;

	/**
	 * Atualizar Valores do Aviso Bancário
	 * 
	 * @author Anderson Italo
	 * @date 16/06/2012
	 */
	public void atualizarValoresAvisoBancario(AvisoBancario avisoBancario, boolean alterarValorRealizado) throws ControladorException;

	/**
	 * [UC0339] - Consultar Dados Diários da Arrecadação
	 * [SB0011] – Obter Valor Despesa Bancária do Arrecadador
	 * Obtém os dados da tarifa do arrecadador a partir da tabela ARRECADADOR_CONTRATO_TARIFA.
	 * 
	 * @author Josenildo Neves
	 * @date 19/06/2012
	 * @throws ErroRepositorioException
	 */
	List<ArrecadadorContratoTarifaHelper> pesquisarArrecadadorContratoTarifaPorArrecadacaoDadosDiarios(Integer idArrecadacaoDadosDiarios,
					Integer anoMesArrecadacao, String localidade, String idElo, String setorComercial, String idGerenciaRegional,
					String unidadeNegocioId, String[] idsImovelPerfil, String[] idsLigacaoAgua, String[] idsLigacaoEsgoto,
					String[] idsCategoria, String[] idsEsferaPoder, String[] idsDocumentosTipos) throws ControladorException;

	/**
	 * [UC0235] - Inserir aviso bancário
	 * [SB0001] - Preparar Preenchimento da Conta Bancária
	 * 
	 * @author André Lopes
	 * @date 07/05/2013
	 * @throws ErroRepositorioException
	 */
	public List<ArrecadadorContrato> pesquisarContaBancaria(Integer idArrecadacao) throws ControladorException;

	/**
	 * [SB0020] – Distribuir Código de Barras – Modelo Gsan Parametrizado
	 * 
	 * @author Hebert Falcão
	 * @date 20/06/2012
	 */
	public Object[] distribuirDadosCodigoBarrasPeloModeloParametrizado(String idPagamento)
					throws ControladorException;

	/**
	 * [UC0238] Manter Aviso Bancário
	 * 
	 * @author Hugo Lima
	 * @date 30/08/2012
	 * @param idAvisoBancario
	 * @return
	 * @throws ControladorException
	 */
	public Hashtable<Short, BigDecimal> obterValorTotalAcertosArrecadacaoAvisoBancario(Integer idAvisoBancario) throws ControladorException;

	/**
	 * Pesquiar Parâmetros de Acrescimos
	 * 
	 * @author Hebert Falcão
	 * @date 07/09/2012
	 */
	public ParametroAcrescimosEmissaoDocumento pesquisarParametroAcrescimosEmissaoDocumento(Date dataEmissao) throws ControladorException;

	/**
	 * Retornar o somatório dos valores pagos da conta
	 * 
	 * @author Hebert Falcão
	 * @date 16/10/2012
	 */
	public BigDecimal retornarSomatorioPagamentoDaConta(Integer idConta) throws ControladorException;

	/**
	 * Retornar o somatório dos valores pagos da guia
	 * 
	 * @author Hebert Falcão
	 * @date 16/10/2012
	 */
	public BigDecimal retornarSomatorioPagamentoDaGuia(Integer idGuiaPagamento, Integer numeroPrestacao) throws ControladorException;

	/**
	 * [UC0247] Consultar Pagamentos
	 * Retornar Valor de Remuneração da Conta Histórico
	 * 
	 * @author Hebert Falcão
	 * @date 11/10/2012
	 */
	public BigDecimal retornarValorRemuneracaoContaHistorico(Integer idConta, Integer referencia) throws ControladorException;

	/**
	 * [UC0247] Consultar Pagamentos
	 * Retornar Valor de Remuneração da Conta
	 * 
	 * @author Hebert Falcão
	 * @date 11/10/2012
	 */
	public BigDecimal retornarValorRemuneracaoConta(Integer idConta, Integer referencia) throws ControladorException;

	/**
	 * Método pesquisarIdsLocalidadeComPagamentosOuPagamentosHistoricos
	 * <p>
	 * Esse método implementa pesquisa dos Ids das Localidade Com {@link Pagamento} Ou
	 * {@link PagamentoHistorico}
	 * </p>
	 * RASTREIO: [OC920431][UC0301]
	 * 
	 * @return Lista de {@link Integer}.
	 * @author Marlos Ribeiro
	 * @since 14/11/2012
	 */
	public Collection<Integer> pesquisarIdsLocalidadeComPagamentosOuPagamentosHistoricos(Integer referenciaArrecadacao)
					throws ControladorException;

	public void distribuirValoresGuiaPorCategoria(Integer idGuiaPagamento, Short numeroPrestacao, Imovel imovel)
					throws ControladorException;

	public Collection<GuiaPagamentoCategoria> distribuirValoresGuiaPorCategoria(Integer idGuiaPagamento, Short numeroPrestacao,
					Imovel imovel, Collection<GuiaPagamentoPrestacao> colecaoGuiaPagamentoPrestacao) throws ControladorException;

	public void distribuirValoresGuiaPorCategoriaHistorico(Integer idGuiaPagamento, Short numeroPrestacao, Imovel imovel)
					throws ControladorException;

	/**
	 * Metódo responsável por atualizar a arrecadação do mês.
	 * [UC0276] Encerrar Arrecadação do Mês
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 15/02/2013
	 * @param colecaoIdsLocalidades
	 * @throws ControladorException
	 */
	public void atualizarPDDParaEncerrarArrecadacao(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Metódo responsável por encerrar a arrecadação do mês.
	 * [UC0276] Encerrar Arrecadação do Mês
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 15/02/2013
	 * @param colecaoIdsLocalidades
	 * @throws ControladorException
	 */
	public void encerrarArrecadacao(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos Não Classificados
	 * 2.6. Situação do Pagamento
	 * 
	 * @author Josenildo Neves
	 * @since 29/11/2012
	 * @return listaPagamentoSituacao
	 * @throws ControladorException
	 */
	public Collection<PagamentoSituacao> pesquisarPagamentoSituacao() throws ControladorException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
	 * Retorna a quantidade de pagamentos a serem processados.
	 * 
	 * @author Josenildo Neves
	 * @date 14/11/2012
	 */
	public Integer pesquisarQuantidadePagamentos(ClassificarLotePagamentosNaoClassificadosHelper helper) throws ControladorException;

	/**
	 * Action responsável por exibir a página de Classificar em Lote Pagamentos Não Classificados.
	 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public Collection<ClassificarPagamentosNaoClassificadosHelper> classificarLotePagamentosNaoClassificados(
					ClassificarLotePagamentosNaoClassificadosHelper helper, Usuario usuario) throws ControladorException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
	 * 
	 * @author Josenildo Neves
	 * @date 14/11/2012
	 */
	public void classificarLotePagamentosNaoClassificados(ClassificarLotePagamentosNaoClassificadosHelper helper, Usuario usuario,
					int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Pagamento> selecionarPagamentoPorGuiaPagamento(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ControladorException;

	/**
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> selecionarPagamentoHistoricoPorGuiaPagamentoHistorico(Integer idGuiaPagamento)
					throws ControladorException;

	/**
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */
	public Collection<Pagamento> selecionarPagamentoPorConta(Integer idConta) throws ControladorException;

	/**
	 * @param idConta
	 * @return
	 * @throws ControladorException
	 */

	public Collection<PagamentoHistorico> selecionarPagamentoHistoricoPorContaHistorico(Integer idConta) throws ControladorException;

	/**
	 * [UC0235][FS0011] e [UC0238][FS0010]
	 * Método responsável por verificar se o agente é caixa de empresa
	 * 
	 * @param idArrecadador
	 * @param idUsuario
	 * @return
	 * @throws ControladorException
	 */
	Boolean isAgenteCaixaDeEmpresaAutorizado(Integer idArrecadador, Integer idUsuario) throws ControladorException;

	/**
	 * [UC0235][FS0012]
	 * Método responsável por Verificar existência de aviso de arrecadador do tipo caixa de empresa
	 * 
	 * @param idArrecadador
	 * @param dataLancamento
	 * @return
	 * @throws ControladorException
	 */
	Boolean existeAvisoArrecadadorTipoCaixaEmpresa(Integer idArrecadador, Date dataLancamento) throws ControladorException;

	/**
	 * Remuneração do Legado Cobrança Administrativa - CASAL
	 * 
	 * @author Hebert Falcão
	 * @date 22/0/2013
	 */
	public void ajusteRemuneracaoDoLegadoCobrancaAdministrativaCASAL() throws ControladorException;

	/**
	 * @param idImovel
	 * @param idCobrancaSituacao
	 * @param prazoGeracao
	 * @return
	 * @throws ControladorException
	 */
	public Integer pesquisarImovelCobrancaSituacao(Integer idImovel, Integer idCobrancaSituacao, Integer prazoGeracao)
					throws ControladorException;

	/**
	 * [UC0188][SB0009]
	 * 
	 * @param Collection
	 *            <GuiaPagamentoPrestacaoHelper>
	 * @return
	 * @throws ControladorException
	 */
	public void atualizarVencimentoGuiaPagamentoPrestacao(
					HashMap<GuiaPagamentoPrestacaoHelper, Date> hashMapGuiasPrestacoesVencimentoAlterado)
					throws ControladorException;

	public Collection<Object[]> pesquisarConcessionariaPorArrecadadorMovimento(Integer idArrecadadorMovimento) 
					throws ControladorException;

	/**
	 * Comprovantes da Arrecadação por Recebedor
	 * 
	 * @author Hebert Falcão
	 * @since 28/09/2013
	 */
	public Integer pesquisarComprovantesDaArrecadacaoPorRecebedorCount(Integer anoMesReferencia) throws ControladorException;

	/**
	 * Comprovantes da Arrecadação por Recebedor
	 * 
	 * @author Hebert Falcão
	 * @since 28/09/2013
	 */
	public Collection<Object[]> pesquisarComprovantesDaArrecadacaoPorRecebedorSintetico(Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * Comprovantes da Arrecadação por Recebedor
	 * 
	 * @author Hebert Falcão
	 * @since 28/09/2013
	 */
	public Collection<Object[]> pesquisarComprovantesDaArrecadacaoPorRecebedorAnalitico(Integer anoMesReferencia)
					throws ControladorException;

	/**
	 * Situação dos avisos bancários
	 * 
	 * @author Hebert Falcão
	 * @since 04/10/2013
	 */
	public int pesquisarSituacaoDosAvisosBancariosCount(Integer anoMesReferencia) throws ControladorException;

	/**
	 * Situação dos avisos bancários
	 * 
	 * @author Hebert Falcão
	 * @since 04/10/2013
	 */
	public Collection<Object[]> pesquisarSituacaoDosAvisosBancarios(Integer anoMesReferencia) throws ControladorException;

	/**
	 * [UC0300] Classificar Pagamentos e Devoluções
	 * [SB2000] Gerar Remuneração Cobrança Administrativa - Modelo 1
	 * 
	 * @author Hebert Falcão
	 * @date 24/05/2013
	 */
	public void gerarRemuneracaoCobrancaAdministrativaModelo1(Integer idPagamento) throws ControladorException;

	/**
	 * [UC0300] Classificar Pagamentos e Devoluções
	 * [SB2001] Gerar Remuneração Cobrança Administrativa - Modelo 2
	 * 
	 * @author Hebert Falcão
	 * @date 09/11/2013
	 */
	public void gerarRemuneracaoCobrancaAdministrativaModelo2(Integer idPagamento) throws ControladorException;

	/**
	 * [UC0300] Classificar Pagamentos e Devoluções
	 * [SB1000] Gerar Remuneração Acréscimos para Cobrança Administrativa - Modelo 1
	 * 
	 * @author Hebert Falcão
	 * @date 09/11/2013
	 */
	public boolean gerarRemuneracaoAcrescimosParaCobrancaAdministrativaModelo1(Pagamento pagamento) throws ControladorException;

	/**
	 * [UC0300] Classificar Pagamentos e Devoluções
	 * [SB1001] Gerar Remuneração Acréscimos para Cobrança Administrativa - Modelo 2
	 * 
	 * @author Hebert Falcão
	 * @date 09/11/2013
	 */
	public boolean gerarRemuneracaoAcrescimosParaCobrancaAdministrativaModelo2(Pagamento pagamento) throws ControladorException;

	/**
	 * Ajustar arrecacador movimento item - código de barras inválido
	 * Esse processo foi criado para corrigir um erro nos códigos de barras criados pela leitura da
	 * CAB no faturamento imediato da CASAL
	 * 
	 * @author Hebert Falcão
	 * @date 11/12/2013
	 */
	public void ajustarArrecadadorMovimentoItemCodigoBarrasInvalido(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * [OC1196098] - Quadro Comparativo de Faturamento e Arrecadação
	 * 
	 * @author Yara Souza
	 * @date 15/04/2014
	 */

	public void gerarQuadroComparativoFaturamentoEArrecadacao(int idFuncionalidadeIniciada) throws ControladorException;

}
