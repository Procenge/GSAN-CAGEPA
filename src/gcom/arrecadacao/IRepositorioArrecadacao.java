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

package gcom.arrecadacao;

import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.aviso.bean.AvisoBancarioHelper;
import gcom.arrecadacao.aviso.bean.PagamentosDevolucoesHelper;
import gcom.arrecadacao.aviso.bean.ValoresArrecadacaoDevolucaoAvisoBancarioHelper;
import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.Banco;
import gcom.arrecadacao.bean.ArrecadadorContratoTarifaHelper;
import gcom.arrecadacao.bean.MovimentoArrecadadoresPorNSAHelper;
import gcom.arrecadacao.debitoautomatico.DebitoAutomaticoMovimento;
import gcom.arrecadacao.pagamento.*;
import gcom.arrecadacao.pagamento.bean.ClassificarLotePagamentosNaoClassificadosHelper;
import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.*;
import gcom.cobranca.bean.IntervaloReferenciaHelper;
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoCobradoHistorico;
import gcom.faturamento.debito.DebitoTipo;
import gcom.financeiro.lancamento.AjusteContabilidade;
import gcom.relatorio.arrecadacao.GuiaDevolucaoRelatorioHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Interface para o reposit�rio de cliente
 * 
 * @author S�vio Luiz
 * @created 22 de Abril de 2005
 */
/**
 * @author hgoncalves
 */
public interface IRepositorioArrecadacao {

	public Integer pesquisarIdRegistroCodigo(String codigoRegistro) throws ErroRepositorioException;

	public Short pesquisarNumeroDiasFloat(Integer idArrecadadorContrato, Integer idFormaArrecadacao) throws ErroRepositorioException;

	public AvisoBancario pesquisarAvisoBancario(Integer codigoBanco, Date dataGeracaoArquivo, Date dataPrevistaCredito,
					Integer idArrecadadorMovimento) throws ErroRepositorioException;

	public Integer pesquisarExistenciaGuiaPagamento(Imovel imovel, Integer idDebitoTipo) throws ErroRepositorioException;

	public Integer pesquisarExistenciaGuiaPagamentoCliente(Integer idCliente, Integer idDebitoTipo) throws ErroRepositorioException;

	public Double pesquisarDeducoesAvisoBancario(String codigoAgente, Date dataLancamento, String numeroSequencial)
					throws ErroRepositorioException;

	public Short pesquisarValorMaximoNumeroSequencial(Date dataLancamento, String idArrecadador) throws ErroRepositorioException;

	public Collection<ArrecadadorContrato> pesquisarNumeroSequecialArrecadadorContrato(Short idArrecadador, String cdConvenio)
					throws ErroRepositorioException;

	public Integer pesquisarIdArrecadacaoForma(String codigoArrecadacaoForma) throws ErroRepositorioException;

	public Integer verificarExistenciaAgencia(String codigoAgencia, Integer idBanco) throws ErroRepositorioException;

	public Integer verificarExistenciaBanco(Integer idBanco) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro do movimento dos arrecadadores
	 * [UC0263] - Filtrar Movimento dos Arrecadadores
	 * 
	 * @author Raphael Rossiter
	 * @date 02/03/2006
	 * @param filtroArrecadadorMovimento
	 * @return Uma cole��o de objetos do tipo ArrecadadorMovimento de acordo com
	 *         os par�metros recebidos atrav�s do filtro. Est� consulta inclui
	 *         os movimentos abertos e fechados
	 * @throws ErroRepositorioException
	 */
	public Collection<ArrecadadorMovimento> filtrarMovimentoArrecadadores(FiltroArrecadadorMovimento filtroArrecadadorMovimento)
					throws ErroRepositorioException;

	/**
	 * Obt�m o n�mero de registros em ocorr�ncia de um determinado movimento
	 * (n�mero de linhas da tabela ARRECADADOR_MOVIMENTO_ITEM com ARMV_ID =
	 * ARMV_ID da tabela ARRECADADOR_MOVIMENTO e AMIT_DSOCORRENCIA diferente de
	 * "OK")
	 * 
	 * @author Raphael Rossiter
	 * @date 06/03/2006
	 * @param arrecadadorMovimento
	 * @param descricaoOcorrencia
	 * @return Um Integer que representa a quantidade de registros selecionados
	 * @throws ErroRepositorioException
	 */
	public Integer obterNumeroRegistrosEmOcorrenciaPorMovimentoArrecadadores(ArrecadadorMovimento arrecadadorMovimento,
					String descricaoOcorrencia) throws ErroRepositorioException;

	/**
	 * Obt�m o n�mero de registros que n�o foram aceitos de um determinado
	 * movimento (n�mero de linhas da tabela ARRECADADOR_MOVIMENTO_ITEM com
	 * ARMV_ID = ARMV_ID da tabela ARRECADADOR_MOVIMENTO e AMIT_ICACEITACAO
	 * igual a 2 (N�O))
	 * 
	 * @author Raphael Rossiter
	 * @date 08/03/2006
	 * @param arrecadadorMovimento
	 * @return Um integer que representa a quantidade de registros selecionados
	 * @throws ControladorException
	 */
	public Integer obterNumeroRegistrosNaoAceitosPorMovimentoArrecadadores(ArrecadadorMovimento arrecadadorMovimento,
					Short indicadorAceitacao) throws ErroRepositorioException;

	/**
	 * Seleciona os avisos banc�rios de um determinado movimento
	 * 
	 * @author Raphael Rossiter
	 * @date 09/03/2006
	 * @param arrecadadorMovimento
	 * @return Uma Collection com os avisos banc�rios de um determinado
	 *         movimento
	 * @throws ErroRepositorioException
	 */
	public Collection<AvisoBancario> obterAvisosBancariosPorArrecadadorMovimento(ArrecadadorMovimento arrecadadorMovimento)
					throws ErroRepositorioException;

	/**
	 * Calcula o valor total das devolu��es associados a um determinado aviso
	 * banc�rio (soma (DEVL_VLDEVOLUCAO) da tabela DEVOLUCAO com AVBC_ID =
	 * AVBC_ID da tabela AVISO_BANCARIO)
	 * 
	 * @author Raphael Rossiter
	 * @date 09/03/2006
	 * @param avisoBancario
	 * @return Um BigDecimal que representa o somat�rio de todos as devolu��es
	 *         de um determinado aviso
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterTotalDevolucaoPorAvisoBancario(AvisoBancario avisoBancario) throws ErroRepositorioException;

	/**
	 * [UC0270] Apresentar An�lise do Movimento dos Arrecadadores
	 * O sistema seleciona os itens do movimento do arrecadador.
	 * [SF0001] Consultar os Itens do Movimento do Arrecadador
	 * 
	 * @author Raphael Rossiter,Vivianne Sousa
	 * @data 20/03/2006,05/12/2006
	 * @param arrecadadorMovimento
	 * @return Collection<ArrecadadorMovimentoItem>
	 */
	public Collection<ArrecadadorMovimentoItem> consultarItensMovimentoArrecadador(ArrecadadorMovimento arrecadadorMovimento,
					Integer idImovel, Short indicadorAceitacao, String descricaoOcorrencia) throws ErroRepositorioException;

	/**
	 * [UC0270] Apresentar An�lise do Movimento dos Arrecadadores
	 * O sistema seleciona os itens do movimento do arrecadador.
	 * [SF0001] Consultar os Itens do Movimento do Arrecadador
	 * 
	 * @author Raphael Rossiter,Vivianne Sousa, Kassia Albuquerque
	 * @data 20/03/2006,05/12/2006,22/08/2007
	 * @param arrecadadorMovimento
	 * @return Collection<ArrecadadorMovimentoItem>
	 */
	public Collection<ArrecadadorMovimentoItem> consultarItensMovimentoArrecadador(ArrecadadorMovimento arrecadadorMovimento,
					Integer idImovel, Short indicadorAceitacao, String descricaoOcorrencia, String codigoArrecadacaoForma)
					throws ErroRepositorioException;

	/**
	 * Faz a pesquisa de devolu��o fazendo os carregamentos de clienteContas,
	 * clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corr�a
	 * @date
	 * @param FiltroDevolucao
	 * @return Collection<Devolucao>
	 * @throws ErroRepositorioException
	 */
	public Collection<Devolucao> pesquisarDevolucao(FiltroDevolucao filtroDevolucao) throws ErroRepositorioException;

	/**
	 * Exclui os dados di�rios da arrecada��o do ano/m�s da arrecada��o corrente
	 * [UC0301] Gerar Dados Di�rios da Arrecada��o
	 * 
	 * @author Pedro Alexandre
	 * @date 11/04/2006
	 * @param anoMesReferenciaArrecadacao
	 * @throws ErroRepositorioException
	 */
	public void excluirDadosDiariosArrecadacaoPorAnoMesArrecadacao(int anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * Acumula a quantidade e o valor dos pagamentos com ano/m�s de refer�ncia
	 * da arrecada��o igual ao ano/m�s de refer�ncia da arrecada��o corrente
	 * [UC0301] Gerar Dados Di�rios da Arrecada��o
	 * 
	 * @author Pedro Alexandre
	 * @date 11/04/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return Collection<Object>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object> acumularQuantidadeEValorPagamentoPorAnoMesArrecadacao(int anoMesReferenciaArrecadacao, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * Acumula a quantidade e o valor dos pagamentos com ano/m�s de refer�ncia
	 * da arrecada��o igual ao ano/m�s de refer�ncia da arrecada��o corrente do PagamentoHist�rico
	 * [UC0301] Gerar Dados Di�rios da Arrecada��o
	 * 
	 * @author Saulo Lima
	 * @date 19/01/2009
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return Collection<Object>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object> acumularQuantidadeEValorPagamentoPorAnoMesArrecadacaoDoHistorico(int anoMesReferenciaArrecadacao,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situa��o atual dos pagamentos (PGST_IDATUAL) com valor
	 * correspondente a pagamento classificado (tabela PAGAMENTO_SITUACAO) e
	 * atualiza o id da conta nos pagamentos (seta CNTA_ID da tabela PAGAMENTO
	 * para CNTA_ID da tabela CONTA)
	 * [SF0002] Processar Pagamento de Conta
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 19/04/2006, 06/12/2006
	 * @param
	 * @return void
	 */
	public void processarPagamentoConta(Collection<Pagamento> colecaoPagamentosProcessados, Integer idConta)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situacao atual dos pagamentos (PGST_IDATUAL) com valor
	 * correspondente ao par�metro passado
	 * 
	 * @author Raphael Rossiter
	 * @date 19/04/2006
	 * @param idsPagamentos
	 *            ,
	 *            pagamentoSituacao
	 * @return void
	 */
	public void atualizarSituacaoPagamento(String[] idsPagamentos, Integer pagamentoSituacao) throws ErroRepositorioException;

	/**
	 * [UC0319] Gerar Movimento de D�bito Autom�tico para o banco
	 * pesquisa todos os bancos que tenham contrato vigente para arrecadador
	 * contas com forma de arrecada��o correspondente a debito autom�tico
	 * [SB0002] - Carregar Lista de Bancos
	 * 
	 * @author S�vio Luiz
	 * @date 18/04/2006
	 * @return Cole��o de Bancos
	 * @throws ErroRepositorioException
	 */

	public Collection<Banco> pesquisaBancosDebitoAutomatico() throws ErroRepositorioException;

	/**
	 * [UC0319] Gerar Movimento de D�bito Autom�tico para o banco
	 * pesquisa os movimentos de d�bito autom�tico para o banco,referentes ao
	 * grupo e ano/m�s de faturamento informados
	 * [SB0002] - Carregar Lista de Bancos
	 * 
	 * @author S�vio Luiz
	 * @date 18/04/2006
	 * @param idFaturamentoGrupo
	 *            ,anoMesReferenciaFaturamento,idBanco
	 * @return Cole��o de DebitoAutomaticoMovimento
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisaDebitoAutomaticoMovimento(Collection colecaoFaturamentoGrupo, Integer anoMesReferenciaFaturamento,
					Collection colecaoBancos, String opcaoDebitoAutomatico) throws ErroRepositorioException;

	/**
	 * [UC0319] Filtrar Aviso Bancario
	 * 
	 * @author Vivianne Sousa
	 * @date 20/04/2006
	 * @param avisoBancarioHelper
	 * @return Cole��o de DebitoAutomaticoMovimento
	 * @throws ErroRepositorioException
	 */

	public Collection filtrarAvisoBancarioAbertoFechado(AvisoBancarioHelper avisoBancarioHelper) throws ErroRepositorioException;

	/**
	 * [UC0319] Gerar Movimento de D�bito Autom�tico para o banco
	 * pesquisa 2 campos do arrecadador contrato
	 * 
	 * @author S�vio Luiz
	 * @date 18/04/2006
	 * @param idFaturamentoGrupo
	 *            ,anoMesReferenciaFaturamento,idBanco
	 * @return C�digo do Conv�nio, numero sequencial de envio
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisaCamposArrecadadorContrato(Integer idBanco) throws ErroRepositorioException;

	/**
	 * [UC0319] Gerar Movimento de D�bito Autom�tico para o banco
	 * pesquisa a agencia passando o id do banco
	 * 
	 * @author S�vio Luiz
	 * @date 20/04/2006
	 * @param idBanco
	 * @return Agencia
	 * @throws ErroRepositorioException
	 */

	public Agencia pesquisaAgenciaPorBanco(Integer idBanco) throws ErroRepositorioException;

	/**
	 * [UC0319] Gerar Movimento de D�bito Autom�tico para o banco
	 * atualiza o numero sequencial arquivo envio debito automatico
	 * 
	 * @author S�vio Luiz
	 * @date 20/04/2006
	 * @param idBanco
	 * @return C�digo do Conv�nio, numero sequencial de envio
	 * @throws ErroRepositorioException
	 */

	public void atualizarNumeroSequencialArrecadadorContrato(Integer idArrecadadorContrato, Integer numeroSequencialArquivo,
					String opcaoDebitoAutomatico) throws ErroRepositorioException;

	/**
	 * [UC0319] Gerar Movimento de D�bito Autom�tico para o banco
	 * pesquisa o email do arrecadador contrato passando o c�digo do banco
	 * [SB0003] - Regerar arquivo TXT para um movimento de d�bito autom�tico
	 * gerado anteriormente
	 * 
	 * @author S�vio Luiz
	 * @date 25/04/2006
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public String pesquisarEmailArrecadadorContrato(Short codigoBanco) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualizar Valor Excedente do Pagamento
	 * [SF0009] Atualizar Valor Excedente do Pagamento
	 * 
	 * @author Raphael Rossiter
	 * @date 25/04/2006
	 * @param pagamento
	 * @return void
	 */
	public void atualizarValorExcedentePagamento(Pagamento pagamento) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualizar Valor Excedente do Pagamento
	 * [SF0009] Atualizar Valor Excedente do Pagamento
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 25/04/2006, 29/11/2006
	 * @param colecaoPagamento
	 * @return void
	 */
	public void atualizarValorExcedentePagamento(Collection<Pagamento> colecaoPagamento) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situa��o atual dos pagamentos (PGST_IDATUAL) com valor
	 * correspondente a pagamento classificado (tabela PAGAMENTO_SITUACAO) e
	 * atualiza o id da guia de pagamento nos pagamentos (seta GPAG_ID da tabela
	 * PAGAMENTO para GPAG_ID da tabela GUIA_PAGAMENTO)
	 * [SF0004] Processar Pagamento de Guia de Pagamento
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 26/04/2006, 11/12/2006
	 * @author Saulo Lima
	 * @date 25/11/2008
	 *       Atualizar o campo NumeroPrestacao
	 * @param mapPagamentosProcessados
	 * @throws ErroRepositorioException
	 */
	public void processarPagamentoGuiaPagamento(Map<Integer, Collection> mapPagamentosProcessados, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona a guia de pagamento correspondente ao pagamento
	 * atrav�s do im�vel, cliente e do tipo de d�bito (a partir da tabela
	 * GUIA_PAGAMENTO com IMOV_ID, CLIE_ID e DBTP_ID da tabela PAGAMENTO e
	 * DCST_IDATUAL com valor correspondente a normal da tabela
	 * DEBITO_CREDITO_SITUACAO)
	 * [SF0003] Selecionar Guia de Pagamento pela Localidade, Im�vel, Cliente e
	 * D�bito Tipo
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre, Pedro Alexandre
	 * @date 26/04/2006, 14/03/2007, 05/06/2007
	 * @param imovel
	 * @param cliente
	 * @param debitoTipo
	 * @return Collection<GuiaPagamento>
	 */
	public Collection<GuiaPagamento> selecionarGuiaPagamentoPelaLocalidadeImovelClienteDebitoTipo(Imovel imovel, Cliente cliente,
					DebitoTipo debitoTipo) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situa��o atual dos pagamentos (PGST_IDATUAL) com valor
	 * correspondente a pagamento classificado (tabela PAGAMENTO_SITUACAO) e
	 * atualiza o id do d�bito a cobrar nos pagamentos (seta DBAC_ID da tabela
	 * PAGAMENTO para DBAC_ID da tabela DEBITO_A_COBRAR)
	 * [SF0004] Processar Pagamento de D�bito a Cobrar
	 * 
	 * @author Raphael Rossiter ,Pedro Alexandre
	 * @date 27/04/2006, 12/12/2006
	 * @param mapPagamentosProcessados
	 * @throws ErroRepositorioException
	 */
	public void processarPagamentoDebitoACobrar(Map<Integer, Collection> mapPagamentosProcessados) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona o d�bito a cobrar correspondente ao pagamento atrav�s
	 * do im�vel e do tipo de d�bito (a partir da tabela DEBITO_A_COBRAR com
	 * IMOV_ID e DBTP_ID da tabela PAGAMENTO e DCST_IDATUAL com valor
	 * correspondente a normal da tabela DEBITO_CREDITO_SITUACAO)
	 * [SF0005] Selecionar D�bito a Cobrar pela Localidade, Im�vel e D�bito Tipo
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 26/04/2006, 05/06/2007
	 * @param imovel
	 *            ,
	 * @param debitoTipo
	 * @return Collection<DebitoACobrar>
	 */
	public Collection<DebitoACobrar> selecionarDebitoACobrarPelaLocalidadeImovelDebitoTipo(Imovel imovel, DebitoTipo debitoTipo)
					throws ErroRepositorioException;

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
	public void atualizaValorArrecadacaoAvisoBancario(BigDecimal valor, Integer codigoAvisoBancario) throws ErroRepositorioException;

	/**
	 * Faz a pesquisa de guia de devolu��o para o relat�rio fazendo os
	 * carregamentos de clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corr�a
	 * @date 11/09/2006
	 * @param FiltroGuiaDevolucao
	 * @return Collection<GuiaDevolucao>
	 * @throws ErroRepositorioException
	 */
	public Collection<GuiaDevolucao> pesquisarGuiaDevolucaoRelatorio(FiltroGuiaDevolucao filtroGuiaDevolucao)
					throws ErroRepositorioException;

	/**
	 * [UC0324] - Filtrar Guia de Devolucao
	 * [SF0001] - Seleciona Guias de Devolu��o do Cliente
	 * Faz a pesquisa de guia de devolu��o fazendo os carregamentos de
	 * clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corr�a
	 * @date
	 * @param FiltroGuiaDevolucao
	 * @return Collection<GuiaDevolucao>
	 * @throws ErroRepositorioException
	 */
	public Collection<GuiaDevolucao> pesquisarGuiaDevolucao(FiltroGuiaDevolucao filtroGuiaDevolucao, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * [UC0324] - Filtrar Guia de Devolucao
	 * [SF0001] - Seleciona Guias de Devolu��o do Cliente
	 * Faz a pesquisa de guia de devolu��o fazendo os carregamentos de
	 * clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Rafael Corr�a
	 * @date
	 * @param FiltroGuiaDevolucao
	 * @return Collection<GuiaDevolucao>
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarGuiaDevolucaoCount(FiltroGuiaDevolucao filtroGuiaDevolucao) throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 23/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorEstado(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 23/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorEstadoPorGerenciaRegional(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 23/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorEstadoPorLocalidade(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 23/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorGerenciaRegional(int anoMesReferencia, Integer gerenciaRegional)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 23/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorGerenciaRegionalPorLocalidade(int anoMesReferencia, Integer gerenciaRegional)
					throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 23/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorLocalidade(int anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	/**
	 * [UC0352] Emitir Conta
	 * pesquisa o nome do banco e c�digo da agencia passando o id do im�vel
	 * [SB0017] - Gerar Linhas das contas com D�bito Autom�tico
	 * 
	 * @author S�vio Luiz
	 * @date 26/05/2006
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarParmsDebitoAutomatico(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Consulta a qtde de registros ResumoArrecadacao para a gera��o do
	 * relat�rio '[UC0345] Gerar Relat�rio de Resumo do Arrecadacao' de acordo
	 * com a op��o de totaliza��o.
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
	public Integer consultarQtdeRegistrosResumoArrecadacaoRelatorio(int anoMesReferencia, Integer localidade, Integer gerenciaRegional,
					String opcaoTotalizacao, Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Inseri os resumos das arrecada��es gerados pelo batch no sistema
	 * 
	 * @author Pedro Alexandre
	 * @date 17/05/2006
	 * @param colecaoResumoArrecadacao
	 * @throws ErroRepositorioException
	 */
	public void inserirResumoArrecadacao(Collection<ResumoArrecadacao> colecaoResumoArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Verifica se j� existe resumo da arrecada��o para o ano/m�s de refer�ncia
	 * da arrecada��o
	 * [FS0003] - Verificar a exist�ncia do resumo da arrecada��o
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarResumoArrecadacaoPorAnoMesArrecadacao(Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos classificados de conta do ano/m�s de refer�ncia da
	 * arrecada��o com a situa��o atual(PGST_IDATUAL) igual a pagamento
	 * classificado ou baixar valor excedente e com o c�digo da conta diferente
	 * de nulo (CNTA_ID <> NULL)
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosClassificadosContas(Integer anoMesReferenciaArrecadacao)
					throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos classificados de guia de pagamento do ano/m�s de
	 * refer�ncia da arrecada��o com a situa��o atual(PGST_IDATUAL) igual a
	 * pagamento classificado ou baixar valor excedente e com o c�digo da guia
	 * de pagamento diferente de nulo (GPAG_ID <> NULL)
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosClassificadosGuiasPagamento(Integer anoMesReferenciaArrecadacao)
					throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos classificados de d�bito a cobrar do ano/m�s de
	 * refer�ncia da arrecada��o com a situa��o atual(PGST_IDATUAL) igual a
	 * pagamento classificado ou baixar valor excedente e com o c�digo do d�bito
	 * a cobrar diferente de nulo (DBAC_ID <> NULL)
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosClassificadosDebitoACobrar(Integer anoMesReferenciaArrecadacao)
					throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos n�o classificados do m�s, que s�o do ano/m�s de
	 * refer�ncia da arrecada��o com a situa��o atual(PGST_IDATUAL) diferente de
	 * pagamento classificado
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosNaoClassificadosMes(Integer anoMesReferenciaArrecadacao)
					throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es classificadas do ano/m�s de refer�ncia da
	 * arrecada��o e com situa��o atual igual a devolu��o classificada ou
	 * devolu��o de outros valores
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Devolucao> pesquisarDevolucoesClassificadas(Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es n�o classificadas do m�s, para situa��o atual
	 * diferente de devolu��o classificada e devolu��o de outros valores
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Devolucao> pesquisarDevolucoesNaoClassificadasMes(Integer anoMesReferenciaArrecadacao)
					throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s, que s�oos do ano/m�s de refer�ncia anterior ao da
	 * arrecada��o, que foram classificados no m�s, com situa��o atual igual a
	 * pagamento classificado ou baixar valor excedente e com c�digo da conta
	 * diferente de nulo (CNTA_ID <> NULL)
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosContasEfetuadosEmMesesAnterioresClassificadosMes(Integer anoMesReferenciaArrecadacao)
					throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos de guias de pagamento efetuados em meses
	 * anteriores classificados no m�s, que s�o os do ano/m�s de refer�ncia
	 * anterior ao da arrecada��o, que foram classificados no m�s, com situa��o
	 * atual igual a pagamento classificado ou baixar valor excedente e com
	 * c�digo da guia de pagamento diferente de nulo (GPAG_ID <> NULL)
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosGuiasPagamentoEfetuadosEmMesesAnterioresClassificadosMes(
					Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos de d�bitos a cobrar efetuados em meses anteriores
	 * classificados no m�s, que s�o os do ano/m�s de refer�ncia anterior ao da
	 * arrecada��o, que foram classificados no m�s, com situa��o atual igual a
	 * pagamento classificado ou baixar valor excedente e com c�digo do d�bito a
	 * cobrar diferente de nulo (DBAC_ID <> NULL)
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosDebitoACobrarEfetuadosEmMesesAnterioresClassificadosMes(
					Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es efetuadas em meses anteriores classificadas no
	 * m�s, que s�o as do ano/m�s de refer�ncia anterior ao da arrecada��o e que
	 * foram classificadas no m�s, comsitua��o atual igual a devolu��o
	 * classificada ou devolu��o de outros valores.
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Devolucao> pesquisarDevolucoesEfetuadasEmMesesAnterioresClassificadasNoMes(Integer anoMesReferenciaArrecadacao)
					throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos n�o classificados com baixa comandada, que s�o os
	 * que est�o com a situa��o atual com o valor correspondente a baixar
	 * excedente
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosNaoClassificadosComBaixaComandada() throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos n�o classificados, que s�o os do ano/m�s de
	 * refer�ncia igual ou anterior ao da arrecada��o e que est�o n�o
	 * classificados,com situa��o atual diferente de pagamento classificado e de
	 * baixar valor excedente
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosNaoClassificados(Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es n�o classificadas, que s�o as do ano/m�s de
	 * refer�ncia igual ou anterior ao da arrecada��o e que continuam n�o
	 * classificados, com situa��o atual com o valor diferente de devolu��o
	 * classificada e devolu��o de outros valores.
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Devolucao> pesquisarDevolucoesNaoClassificadas(Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 100
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor de �gua por categoria e localidade paa os pagamentos
	 * classificados de conta
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorAguaPagamentosClassificadosConta(Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
					Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 200
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor de esgoto por categoria e localidade paa os pagamentos
	 * classificados de conta
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEsgotoPagamentosClassificadosConta(Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
					Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 300
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria e item
	 * cont�bil dos pagamentos classificados de conta para tipo de financiamento
	 * igual a servi�o
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idLancamentoItemContabil
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosClassificadosContaFinanciamentoTipoServico(
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idLancamentoItemContabil, Integer idCategoria)
	 * throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 400
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria dos
	 * pagamentos classificados de conta para tipo de financiamento igual a
	 * parcelamento de �gua
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosClassificadosContaFinanciamentoTipoParcelamentoAgua(
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria) throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 500
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria dos
	 * pagamentos classificados de conta para tipo de financiamento igual a
	 * parcelamento de esgoto
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosClassificadosContaFinanciamentoTipoParcelamentoEsgoto(
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria) throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 600
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria dos
	 * pagamentos classificados de conta para tipo de financiamento igual a
	 * parcelamento de servi�o e grupo de parcelamento diferente de juros
	 * cobrados
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosClassificadosContaFinanciamentoTipoParcelamentoServicoGrupoParcelamentoDiferenteJurosCobrados
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria, Integer idLancamentoItemContabil)
	 * throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 700
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria dos
	 * pagamentos classificados de conta para tipo de financiamento igual a
	 * parcelamento de servi�o e grupo de parcelamento igual a juros cobrados
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosClassificadosContaFinanciamentoTipoParcelamentoServicoGrupoParcelamentoIgualJurosCobrados(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 800
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos cr�ditos realizados por localidade e categoria para
	 * os pagamentos classificados de contas, para origem de cr�dito igual a
	 * contas pagas em duplicidade/excesso.
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorCreditoRealizadoPagamentosClassificadosContaOrigemCreditoContasPagasEmDuplicidadeExcesso
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria) throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 900
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos cr�ditos realizados por localidade, categoria e item
	 * cont�bil para os pagamentos classificados de contas, para origem de
	 * cr�dito igual a valores cobrados indevidamente.
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCreditoRealizadoPagamentosClassificadosContaOrigemCreditoValoresCobradosIndevidamente(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idLancamentoItemContabil)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 1000
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos cr�ditos realizados por localidade e categoria para
	 * os pagamentos classificados de contas, para origem de cr�dito igual a
	 * descontos concedidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCreditoRealizadoPagamentosClassificadosContaOrigemCredito(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idCreditoOrigem) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 1700
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor da entrada do parcelamento por localidade e categoria dos
	 * pagamentos classificados de guias de pagamento com tipo de financiamento
	 * igual a entrada de parcelamento
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEntradaParcelamentoPagamentosClassificadosGuiaPagamentoFinanciamentoTipoEntradaParcelamento(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 1800
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor da guia de pagamento por localidade, categoria e item
	 * cont�bil dos pagamentos classificados de guias de pagamento com tipo de
	 * financiamento igual a servi�o
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idLancamentoItemContabil
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEntradaParcelamentoPagamentosClassificadosGuiaPagamentoFinanciamentoTipoServico(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idLancamentoItemContabil, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 1900
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor que falta ser cobrado dos d�bitos a cobrar dos pagamentos
	 * classificados de d�bito a cobrar por localidade, categoria e item
	 * cont�bil
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorQueFaltaSerCobradoPagamentosClassificadosDebitoACobrar(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idLancamentoItemContabil, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 2600
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es classificadas para acumular o valor da devolu��o
	 * por categoria com situa��o igual a devolu��o classificada.
	 * 
	 * @author Pedro Alexandre
	 * @date 25/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDevolucoesClassificadasSituacaoAtualDevolucaoClassificada(Integer anoMesReferenciaArrecadacao,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 2700
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es classificadas para acumular o valor da devolu��o
	 * por categoria e item cont�bil com situa��o igual a devolu��o de outros
	 * valores.
	 * 
	 * @author Pedro Alexandre
	 * @date 25/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDevolucoesClassificadasSituacaoAtualDevolucaoOutrosValores(Integer anoMesReferenciaArrecadacao,
					Integer idLocalidade, Integer idLancamentoItemContabil) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 3500
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor de �gua por localidade e categoria para os pagamentos de
	 * contas efetuados em meses anteriores classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorAguaPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMes(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 3600
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor de esgoto por localidade e categoria para os pagamentos
	 * de contas efetuados em meses anteriores classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEsgotoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMes(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 3700
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade, categoria e item
	 * cont�bil para os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s para tipo de financiamento igual a servi�o.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idLancamentoItemContabil
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoServico
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idLancamentoItemContabil, Integer idCategoria)
	 * throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 3800
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade e categoria para os
	 * pagamentos de contas efetuados em meses anteriores classificados no m�s
	 * para tipo de financiamento igual a parcelamento de �gua.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoParcelamentoAgua
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria) throws ErroRepositorioException;
	 */
	/**
	 * Sequencial do tipo lan�amento igual a 3900
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade e categoria para os
	 * pagamentos de contas efetuados em meses anteriores classificados no m�s
	 * para tipo de financiamento igual a parcelamento de esgoto.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoParcelamentoEsgoto
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria) throws ErroRepositorioException;
	 */
	/**
	 * Sequencial do tipo lan�amento igual a 4000
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade, categoria e item
	 * cont�bil para os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s para tipo de financiamento igual a parcelamento de
	 * servi�o e grupo de parcelamento diferente de juros cobrados.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoParcelamentoServicoGrupoParcelamentoDiferenteJurosCobrados
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria, Integer idLancamentoItemContabil)
	 * throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 4100
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade, categoria e item
	 * cont�bil para os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s para tipo de financiamento igual a parcelamento de
	 * servi�o e grupo de parcelamento igual a juros cobrados.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoParcelamentoServicoGrupoParcelamentoIgualJurosCobrados(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 4200
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos cr�ditos realizados por localidade e categoria para
	 * os pagamentos de contas efetuados em meses anteriores classificados no
	 * m�s, para origem do cr�dito igual a documentos pagos em
	 * duplicidade/excesso.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorCreditoRealizadoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesOrigemCreditoContasPagasEmDuplicidadeExcesso
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria) throws ErroRepositorioException;
	 */
	/**
	 * Sequencial do tipo lan�amento igual a 4300
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos cr�ditos realizados por localidade, categoria e item
	 * cont�bil para os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s, para origem do cr�dito igual a valores cobrados
	 * indevidamente.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCreditoRealizadoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesOrigemCreditoValoresCobradosIndevidamente(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idLancamentoItemContabil)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 4400
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos cr�ditos realizados por localidade e categoria, para
	 * os pagamentos de contas efetuados em meses anteriores classificados no
	 * m�s, para origem do cr�dito igual a descontos concedidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	/*
	 * public BigDecimal
	 * acumularValorCreditoRealizadoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesOrigemCreditoDescontosConcedidos
	 * (
	 * Integer idLocalidade, Integer anoMesReferenciaArrecadacao,
	 * Integer idCategoria) throws ErroRepositorioException;
	 */

	/**
	 * Sequencial do tipo lan�amento igual a 5100
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor das entrads do parcelamento por localidade e categoria
	 * dos pagamento de guias de pagamento efetuados em meses anteriores
	 * classificados no m�s com tipo de financiamento igual a entrada de
	 * parcelamento.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEntradaParcelamentoPagamentosGuiaPagamentoEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoEntradaParcelamento(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 5200
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor das entrads do parcelamento por localidade, categoria e
	 * item cont�bil dos pagamento de guias de pagamento efetuados em meses
	 * anteriores classificados no m�s com tipo de financiamento igual a
	 * servi�o.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idLancamentoItemContabil
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorEntradaParcelamentoPagamentosGuiaPagamentoEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoServico(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idLancamentoItemContabil, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 5300
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor que falta ser cobrado dos d�bitos a cobrar por
	 * localidade, categoria e item cont�bil para os pagamentos de d�bitos a
	 * cobrar efetuados em meses anteriores classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idLancamentoItemContabil
	 * @param idCategoria
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorQueFaltaSerCobradoPagamentosDebitoACobrarEfetuadosEmMesesAnteriores(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idLancamentoItemContabil, Integer idCategoria)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 5500
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es efetuadas em meses anteriores classificadas no m�s
	 * para acumular o valor da devolu��o por localidade e categoria com
	 * situa��o atual igual a devolu��o classificada.
	 * 
	 * @author Pedro Alexandre
	 * @date 30/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDevolucoesEfetuadasEmMesesAnterioresClassificadasNoMesSituacaoAtualDevolucaoClassificada(
					Integer anoMesReferenciaArrecadacao, Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 5600
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es efetuadas em meses anteriores classificadas no m�s
	 * para acumular o valor da devolu��o por localidade, categoria e item
	 * cont�bil, com situa��o atual igual a devolu��o de outros valores.
	 * 
	 * @author Pedro Alexandre
	 * @date 31/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDevolucoesEfetuadasEmMesesAnterioresClassificadasNoMesSituacaoAtualDevolucaoOutrosValores(
					Integer anoMesReferenciaArrecadacao, Integer idLocalidade, Integer idLancamentoItemContabil)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 6200 (SOMA DOS
	 * SEQ.1200,1300,1400,1500,4600,4700,4800 e 4900)
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Este met�do acumular os valores dos sequencias : 1200, 1300, 1400, 1500,
	 * 4600, 4700, 4800 e 4900, para ser acumulado negativamente � soma dos
	 * recebimentos de valores contabilizados como perdas.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/06/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImpostosDeduzidosPagamentosClassificadosNoMesMesesAnterioresContaContabilizadasComoPerdasImpostoTipoIRCSLLCOFINSPISPASEP(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os d�bitos cobrados das contas dos pagamentos classificados de
	 * contas e dos pagamentos anteriores de conta classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/06/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<DebitoCobrado> pesquisarDebitosCobradosContasPagamentosClassificadosPagamentosAnterioresContaClassificadosNoMes(
					Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os cr�ditos realizados das contas dos pagamentos classificados
	 * de contas e dos pagamentos anteriores de conta classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 02/06/2006
	 * @param anoMesReferenciaArrecadacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<CreditoRealizado> pesquisarCreditosRealizadosContasPagamentosClassificadosPagamentosAnterioresContaClassificadosNoMes(
					Integer anoMesReferenciaArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona os pagamentos com ano/m�s de refer�ncia da
	 * arrecada��o igual ou menor que o ano/m�s de refer�ncia da arrecada��o
	 * corrente (seleciona a partir da tabela PAGAMENTO para
	 * PGMT_AMREFERENCIAARRECADACAO igual ou menor ao
	 * PARM_AMREFERENCIAARRECADACAO) e com o tipo do documento correspondente a
	 * conta
	 * 
	 * @author Raphael Rossiter
	 * @data 18/04/2006
	 * @param anoMesReferenciaFaturamento
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarPagamentosPorConta(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona os pagamentos com ano/m�s de refer�ncia da
	 * arrecada��o igual ou menor que o ano/m�s de refer�ncia da arrecada��o
	 * corrente (seleciona a partir da tabela PAGAMENTO para
	 * PGMT_AMREFERENCIAARRECADACAO igual ou menor ao
	 * PARM_AMREFERENCIAARRECADACAO) e com o tipo do documento correspondente a
	 * conta
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @data 18/04/2006, 28/11/2006
	 * @param idImovel
	 * @param anoMesReferenciaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarPagamentosPorConta(Integer idImovel, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona os pagamentos com ano/m�s de refer�ncia da
	 * arrecada��o igual ou menor que o ano/m�s de refer�ncia da arrecada��o
	 * corrente (seleciona a partir da tabela PAGAMENTO para
	 * PGMT_AMREFERENCIAARRECADACAO igual ou menor ao
	 * PARM_AMREFERENCIAARRECADACAO), com o tipo do documento correspondente a
	 * guia de pagamento e o campo GPAG_ID informado
	 * 
	 * @author Raphael Rossiter
	 * @data 18/04/2006
	 * @param anoMesReferenciaFaturamento
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarPagamentosPorGuiaPagamentoComGuiaInformada(Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre, Saulo Lima
	 * @data 18/04/2006, 28/11/2006, 24/11/2008
	 * @param anoMesReferenciaFaturamento
	 * @param idLocalidade
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarPagamentosPorGuiaPagamentoComGuiaInformada(Integer idLocalidade, Integer idGuiaPagamento,
					Integer numeroPrestacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @data 18/04/2006, 12/12/2006
	 * @author Saulo Lima
	 * @date 07/08/2009
	 *       Novos Par�metros idImovel e idCliente
	 * @param idLocalidade
	 * @param idImovel
	 * @param idCliente
	 * @return Collection<Object[]>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarPagamentosPorGuiaPagamentoSemGuiaInformada(Integer idLocalidade, Integer idImovel,
					Integer idCliente) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @data 18/04/2006, 28/11/2006
	 * @author Saulo Lima
	 * @date 04/08/2009
	 *       Novo par�metro idImovel
	 * @param idLocalidade
	 * @param idImovel
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarPagamentosPorDebitoACobrarSemDebitoInformada(Integer idLocalidade, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @data 18/04/2006, 28/11/2006
	 * @author Saulo Lima
	 * @date 04/08/2009
	 *       Novo par�metro idImovel
	 * @author Thiago Toscano
	 * @date 05/10/09
	 *       Novo par�metro prestacao
	 * @param idLocalidade
	 * @param idImovel
	 * @parm prestacao - prestacao
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarPagamentosPorDebitoACobrarComDebitoInformado(Integer idLocalidade, Integer idImovel,
					Integer prestacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona a conta correspondente ao pagamento atrav�s do im�vel
	 * e ano/m�s de refer�ncia do pagamento (a partir da tabela CONTA com
	 * IMOV_ID = IMOV_ID da tabela PAGAMENTO, PGMT_AMREFERENCIAPAGAMENTO da
	 * tabela PAGAMENTO e DCST_IDATUAL com o valor correspondente a normal,
	 * retificada ou inclu�da, da tabela DEBTIO_CREDITO_SITUACAO)
	 * [SF0001] Selecionar Conta pelo Im�vel e Ano/M�s de Refer�ncia
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 18/04/2006, 05/06/2007
	 * @param imovel
	 * @param anoMesReferenciaPagamento
	 * @return Conta
	 */
	public Object[] selecionarContaPorImovelAnoMesReferencia(Imovel imovel, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	/**
	 * Pesquisa a ContaHistorico para o im�vel no ano/m�s de refer�ncia informados
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Pedro Alexandre
	 * @date 15/01/2008
	 * @param imovel
	 * @param anoMesReferenciaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] selecionarContaHistoricoPorImovelAnoMesReferencia(Imovel imovel, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Im�vel pesquisarPagamento
	 * 
	 * @author Roberta Costa
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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Conta do Cliente
	 * pesquisarPagamentoClienteConta
	 * 
	 * @author Rafael Corr�a
	 * @date 12/12/06
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection pesquisarPagamentoClienteConta(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String indicadorTotalizarPorDataPagamento, Collection<Integer> idsArrecadadores, String[] idsCategoria)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Guia de Pagamento do Cliente
	 * pesquisarPagamentoClienteGuiaPagamento
	 * 
	 * @author Rafael Corr�a
	 * @date 12/06/06
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection pesquisarPagamentoClienteGuiaPagamento(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoClienteDebitoACobrar
	 * 
	 * @author Rafael Corr�a
	 * @date 12/06/06
	 * @return Collection
	 * @throws ControladorException
	 */
	public Collection pesquisarPagamentoClienteDebitoACobrar(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoClienteDebitoACobrar
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoLocalidade(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoClienteDebitoACobrar
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoAvisoBancario(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoClienteDebitoACobrar
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @return Collection<Pagamento>
	 * @throws ControladorException
	 */
	public Collection<Pagamento> pesquisarPagamentoMovimentoArrecadador(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Consultar dados diarios da arrecadacao
	 * 
	 * @author Fernanda Paiva
	 * @date 09/06/2006
	 * @param anoMesReferencia
	 *            ,
	 *            id
	 * @return Cole��o de DebitoAutomaticoMovimento
	 * @throws ErroRepositorioException
	 */

	public Collection consultarDadosDiarios(int anoMesReferencia, int id, String descricao, int idElo) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona as devolu��es com ano/m�s de refer�ncia da
	 * arrecada��o igual ou menor que o ano/m�s de refer�ncia da arrecada��o
	 * corrente (seleciona a partir da tabela DEVOLUCAO para
	 * DEVL_AMREFERENCIAARRECADACAO igual ou menor ao
	 * PARM_AMREFERENCIAARRECADACAO) e com o ano/m�s de refer�ncia preenchido
	 * (DEVL_AMREFERENCIADEVOLUCAO com valor diferente de nulo)
	 * 
	 * @author Raphael Rossiter
	 * @data 14/06/2006
	 * @param anoMesReferenciaFaturamento
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarDevolucoesEmDuplicidadeOUExcesso(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona as devolu��es com ano/m�s de refer�ncia da
	 * arrecada��o igual ou menor que o ano/m�s de refer�ncia da arrecada��o
	 * corrente (seleciona a partir da tabela DEVOLUCAO para
	 * DEVL_AMREFERENCIAARRECADACAO igual ou menor ao
	 * PARM_AMREFERENCIAARRECADACAO) e com o ano/m�s de refer�ncia preenchido
	 * (DEVL_AMREFERENCIADEVOLUCAO com valor diferente de nulo)
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @data 14/06/2006, 28/11/2006
	 * @param Devolucao
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarDevolucoesEmDuplicidadeOUExcesso(Devolucao devolucao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona as devolu��es com ano/m�s de refer�ncia da
	 * arrecada��o igual ou menor que o ano/m�s de refer�ncia da arrecada��o
	 * corrente (seleciona a partir da tabela DEVOLUCAO para
	 * DEVL_AMREFERENCIAARRECADACAO igual ou menor ao
	 * PARM_AMREFERENCIAARRECADACAO) e com o ano/m�s de refer�ncia n�o
	 * preenchido (DEVL_AMREFERENCIADEVOLUCAO com valor diferente nulo)
	 * 
	 * @author Raphael Rossiter
	 * @data 14/06/2006
	 * @param anoMesReferenciaFaturamento
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarDevolucoesCobradasIndevidamente(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * O sistema seleciona as devolu��es com ano/m�s de refer�ncia da
	 * arrecada��o igual ou menor que o ano/m�s de refer�ncia da arrecada��o
	 * corrente (seleciona a partir da tabela DEVOLUCAO para
	 * DEVL_AMREFERENCIAARRECADACAO igual ou menor ao
	 * PARM_AMREFERENCIAARRECADACAO) e com o ano/m�s de refer�ncia n�o
	 * preenchido (DEVL_AMREFERENCIADEVOLUCAO com valor diferente nulo)
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @data 14/06/2006, 28/11/2006
	 * @param anoMesReferenciaFaturamento
	 * @param idLocalidade
	 * @return Collection<Object[]>
	 */
	public Collection<Object[]> pesquisarDevolucoesCobradasIndevidamente(Integer anoMesReferencia, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SF0010] Selecionar Pagamentos n�o Classificados de Conta
	 * 
	 * @author Raphael Rossiter
	 * @date 26/04/2006
	 * @param imovel
	 *            ,
	 *            anoMesReferenciaDevolucao
	 * @return Collection<Pagamento>
	 */
	public Collection<Pagamento> selecionarPagamentosNaoClassificadosConta(Imovel imovel, Integer anoMesReferenciaDevolucao)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situacao atual das devolucoes (DVST_IDATUAL) com valor
	 * correspondente a pagamento em duplicidade n�o encontrado (tabela
	 * DEVOLUCAO_SITUACAO)
	 * [SF0011] Processar Devolu��es de Pagamentos
	 * 
	 * @author Raphael Rossiter
	 * @date 15/06/2006
	 * @param idsDevolucoes
	 * @return void
	 */
	public void atualizarSituacaoDevolucao(String[] idsDevolucao, Integer devolucaoSituacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situacao anterior dos pagamentos (PGST_IDANTERIOR) (tabela
	 * PAGAMENTO_SITUACAO)
	 * [SF0008] Processar Pagamento a Maior ou a Menor
	 * 
	 * @author Raphael Rossiter
	 * @date 19/04/2006
	 * @param idsPagamentos
	 * @return void
	 */
	public void atualizarSituacaoAnteriorPagamento(String[] idsPagamentos, Integer pagamentoSituacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SF0012] Selecionar Pagamentos n�o classificados de guia de pagamento ou
	 * d�bito a cobrar
	 * 
	 * @author Raphael Rossiter
	 * @date 15/06/2006
	 * @param imovel
	 *            ,
	 *            anoMesReferenciaDevolucao
	 * @return Collection<Pagamento>
	 */
	public Collection<Pagamento> selecionarPagamentosNaoClassificadosGuiaPagamentoDebitoACobrar(Imovel imovel, Cliente cliente,
					DebitoTipo debitoTipo) throws ErroRepositorioException;

	/**
	 * [UC0319] Filtrar Aviso Bancario
	 * 
	 * @author Fernanda Paiva
	 * @date 16/08/2006
	 * @param avisoBancarioHelper
	 * @return Cole��o de DebitoAutomaticoMovimento
	 * @throws ErroRepositorioException
	 */

	public Collection filtrarAvisoBancarioAbertoFechadoParaPaginacao(AvisoBancarioHelper avisoBancarioHelper, Integer numeroPagina)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Cliente
	 * 
	 * @author Rafael Corr�a
	 * @date 21/12/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentoClienteCount(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * para o Relat�rio
	 * 
	 * @author Rafael Corr�a
	 * @date 29/08/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoImovelRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um sql que ser� usado na pesquisa de pagamentos
	 * para o Relat�rio
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Cliente
	 * 
	 * @author Rafael Corr�a
	 * @date 12/12/06
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentoClienteRelatorio(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					String indicadorTotalizarPorDataPagamento, Collection<Integer> idsArrecadadores, String[] idsCategoria)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * para o Relat�rio
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Aviso Bancario
	 * pesquisarPagamentoAvisoBancario
	 * 
	 * @author Rafael Corr�a
	 * @date 29/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoAvisoBancarioRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * para o Relat�rio
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoLocalidade
	 * 
	 * @author Rafael Corr�a
	 * @date 29/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoMovimentoArrecadadorRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * para o Relat�rio
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoLocalidade
	 * 
	 * @author Roberta Costa
	 * @date 12/06/06
	 * @author Saulo Lima
	 * @date 08/01/2009
	 *       Altera��o nos campos de retorno e Generics na cole��o de retorno
	 * @return Collection<Object>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object> pesquisarPagamentoLocalidadeRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Rafael Corr�a
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
					String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Saulo Lima
	 * @date 25/08/2009
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarPagamentoHistoricoMovimentoArrecadadorCount(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, Collection<Integer> idsArrecadadores,
					String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do Imovel pesquisarPagamentoImovelParaPaginacao
	 * 
	 * @author Vivianne Sousa
	 * @date 29/08/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */

	public Integer pesquisarPagamentoLocalidadeCount(String idImovel, String idCliente, String idTipoRelacao, String localidadeInicial,
					String localidadeFinal, String idAvisoBancario, String idArrecadador, String periodoArrecadacaoInicial,
					String periodoArrecadacaoFinal, String periodoPagamentoInicio, String periodoPagamentoFim, Date dataPagamentoInicial,
					Date dataPagamentoFinal, String[] idsPagamentosSituacoes, String[] idsDebitosTipos, String[] idsArrecadacaoForma,
					String[] idsDocumentosTipos, String codigoSetorComercialInicial, String codigoSetorComercialFinal,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	public Integer verificarExistenciaGuiaDevolucao(Integer idGuiaDevolucao) throws ErroRepositorioException;

	/**
	 * Pesquisa os avisos banc�rios para o relat�rio atrav�s das op��es
	 * selecionadas no Filtrar Aviso Banc�rio
	 * 
	 * @author Rafael Corr�a
	 * @date 04/09/06
	 * @return Collection<AvisoBancarioRelatorioHelper>
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarAvisoBancarioRelatorio(AvisoBancarioHelper avisoBancarioHelper) throws ErroRepositorioException;

	/**
	 * Pesquisa os avisos dedu��es de um aviso banc�rio para o relat�rio atrav�s
	 * do id do aviso banc�rio
	 * 
	 * @author Rafael Corr�a
	 * @date 05/09/06
	 * @return Collection<DeducoesRelatorioHelper>
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarAvisoDeducoesAvisoBancarioRelatorio(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * Pesquisa os avisos acertos de um aviso banc�rio para o relat�rio atrav�s
	 * do id do aviso banc�rio
	 * 
	 * @author Rafael Corr�a
	 * @date 05/09/06
	 * @return Collection<AcertosRelatorioHelper>
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarAvisoAcertosAvisoBancarioRelatorio(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * Pesquisa dos dados di�rios da arrecada��o
	 * [UC0333] Filtrar Dados Di�rios da Arrecada��o
	 * 
	 * @author Rafael Santos
	 * @date 05/09/2006
	 * @return
	 */
	public Collection filtrarDadosDiariosArrecadacao(String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String idLocalidade,
					String idGerenciaRegional, String unidadeNegocioId, String idArrecadador, String idElo, String[] idsImovelPerfil,
					String[] idsLigacaoAgua, String[] idsLigacaoEsgoto, String[] idsDocumentosTipos, String[] idsCategoria,
					String[] idsEsferaPoder, String setorComercial, String idConcessionaria) throws ErroRepositorioException;

	/**
	 * Pesquisa dos dados di�rios da arrecada��o pela Gerencia
	 * [UC0333] Filtrar Dados Di�rios da Arrecada��o
	 * 
	 * @author Rafael Santos
	 * @date 05/09/2006
	 * @return
	 */
	public Collection filtrarDadosDiariosArrecadacaoValoresDiarios(String idGerenciaRegional) throws ErroRepositorioException;

	/**
	 * Retornar o valor do somatorio dos acertos daquele aviso bancario
	 * 
	 * @author Fernanda Paiva
	 * @date
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarSomatorioAvisoAcerto(Integer indicadorCreditoDebito, Integer idAviso, Integer indicadorArrecadacaoDevolucao)
					throws ErroRepositorioException;

	/**
	 * Retornar os avisos bancario aberto e/ou fechado
	 * 
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarAvisoBancarioAbertoFechadoFinal(AvisoBancarioHelper avisoBancarioHelper) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados da Guia de Pagamento necess�rios para o relat�rio
	 * atrav�s do id da Guia de Pagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 03/10/06
	 * @param numeroPrestacoesGuiaPagamento
	 *            TODO
	 * @author eduardo henrique
	 * @date 20/08/2008
	 *       Altera��o para Impress�o de Guia de Pagamento por Presta��o
	 * @param prestacoesGuiaPagamento
	 *            Cole��o de GuiaPagamentoPresta��o que poder�(�o) ser�(�o) impressa(s)
	 * @param registrosImpressao
	 *            Id's selecionados na Apresenta��o ou de 'Outra Origem'.
	 * @return GuiaPagamentoRelatorioHelper
	 * @throws ErroRepositorioException
	 */

	public Collection<Object[]> pesquisarGuiaPagamentoRelatorio(Integer idGuiaPagamento, List numeroPrestacoesGuiaPagamento)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o nome do cliente da guia de pagamento atrav�s do id da Guia de
	 * Pagamento e com CRTP_ID com o valor correspondente a usu�rio(2)
	 * 
	 * @author Vivianne Sousa
	 * @date 04/10/06
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public String pesquisarNomeClienteGuiaPagamentoRelatorio(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados da Guia de Devolu��o necess�rios para o relat�rio
	 * atrav�s do id da Guia de Devolu��o
	 * 
	 * @author Ana Maria
	 * @date 05/10/06
	 * @return GuiaDevolucaoRelatorioHelper
	 * @throws ErroRepositorioException
	 */

	public GuiaDevolucaoRelatorioHelper pesquisarGuiaDevolucaoRelatorio(Integer idGuiaDevolucao) throws ErroRepositorioException;

	/**
	 * Pesquisa os dados do Cliente pelo Im�vel
	 * 
	 * @author Ana Maria
	 * @date 06/10/06
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarClienteImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * para pesquisar os pagamento historicos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos historicos do Im�vel
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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * M�todo que pesquisa os pagamentos hist�rico para tela de consulta de Im�vel
	 * 
	 * @author Saulo Lima
	 * @date 09/02/2009
	 * @param idImovel
	 * @return Collection<PagamentoHistorico>
	 * @throws ControladorException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoConsultaImovel(Integer idImovel) throws ErroRepositorioException;

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
					String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtra os pagamento historicos do Imovel para pagina��o
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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtra os Pagamento Historicos do Cliente Conta
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoClienteConta(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtrar os pagamentos historicos do Cliente Guia Pagamento
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/06
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object[]> pesquisarPagamentoHistoricoClienteGuiaPagamento(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtra os pagamentos historicos do debito a cobrar
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 12/06/06,06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoClienteDebitoACobrar(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

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
					String[] idsCategoria) throws ErroRepositorioException;

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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

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
					String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtra os Pagamento Historicos da Localidade
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoLocalidade(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtra oas pagamento historicos do Aviso Bancario
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoAvisoBancario(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

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
					String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtra os pagamento historicos do aviso bancario para pagina��o
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
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Filtrar pagamentos historicos do movimento arrecador
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Santos
	 * @date 06/10/2006
	 * @param FiltroPagamento
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoMovimentoArrecadador(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Faz a pesquisa de devolu��oHistorico fazendo os carregamentos de
	 * clienteContas, clienteImoveis, clientesGuiaPagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 09/10/2006
	 * @param FiltroDevolucaoHistorico
	 * @return Collection<DevolucaoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<DevolucaoHistorico> pesquisarDevolucaoHistorico(FiltroDevolucaoHistorico filtroDevolucaoHistorico)
					throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * hist�rico para o Relat�rio
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos hist�rico do tipo Debito a Cobrar do Cliente
	 * 
	 * @author Vivianne Sousa
	 * @date 17/10/06
	 * @author Saulo Lima
	 * @date 13/01/2009
	 *       Altera��o nos campos de retorno e Generics na cole��o de retorno
	 * @return Collection<Object>
	 * @throws ErroRepositorioException
	 */
	public Collection<Object> pesquisarPagamentoHistoricoLocalidadeRelatorio(String idImovel, String idCliente, String idTipoRelacao,
					String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Consulta dados da tabela dados diarios arrecadacao
	 * 
	 * @author Rafael Santos
	 * @created 21/10/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosDiarios(int idGerenciaRegional, int idLocalidade, int idElo) throws ErroRepositorioException;

	/**
	 * Pesquisa conta e ag�ncia do sistema de par�metros
	 * 
	 * @author Ana Maria
	 * @date 23/10/06
	 * @return Object[]
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarContaAgenciaSistemaParametro() throws ErroRepositorioException;

	/**
	 * Pesquisa id do lan�amento contabil
	 * 
	 * @author S�vio Luiz
	 * @date 08/11/06
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdLancamentoItemContabil(Integer idCreditoTipo) throws ErroRepositorioException;

	/**
	 * pesquisar descri��o do D�bito Autom�tico
	 * 
	 * @author S�vio Luiz
	 * @date 22/11/06
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public String pesquisarDescricaoDebitoAutomatico(Integer codigoRetorno) throws ErroRepositorioException;

	/**
	 * Pesquisa a lista de ano/m�s de arrecada�a� menores e igual ao ano/m�s de
	 * arrecada��o atual.
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 18/04/2006, 29/11/2006
	 * @param anoMesArrecadacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnoMesArrecadacaoMenorIgualAtual(Integer anoMesArrecadacaoAtual) throws ErroRepositorioException;

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
	 * @param indicadorAbertoFechado
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection filtrarMovimentoArrecadadorParaPaginacao(String codigoBanco, String codigoRemessa,
					String descricaoIdentificacaoServico, String idImovel, String numeroSequencialArquivo, Date dataGeracaoInicio,
					Date dataGeracaoFim, Date ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia,
					String indicadorAceitacao, Integer numeroPagina, String idConcessionaria) throws ErroRepositorioException;

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
	public Integer filtrarMovimentoArrecadadoresCount(String codigoBanco, String codigoRemessa, String descricaoIdentificacaoServico,
					String idImovel, String numeroSequencialArquivo, Date dataGeracaoInicio, Date dataGeracaoFim,
					Date ultimaAlteracaoInicio, Date ultimaAlteracaoFim, String descricaoOcorrencia, String indicadorAceitacao,
					String idConcessionaria)
					throws ErroRepositorioException;

	/**
	 * retorna o somatorio de PGMT_VLPAGAMENTO da tabela PAGAMENTO com AMIT_ID
	 * =AMIT_ID da tabela ARRECADADOR_MOVIMENTO_ITEM
	 * [UC0254] Efetuar An�lise do Movimento dos Arrecadadores
	 * 
	 * @author Vivianne Sousa
	 * @date 05/12/2006
	 * @param idArrecadadorMovimentoItem
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal recuperaValorPagamentoArrecadadorMovimentoItem(Integer idArrecadadorMovimentoItem) throws ErroRepositorioException;

	/**
	 * retorna a decri��o da Forma de Arrecada��o (arfm_dsarrecadacaoforma) da
	 * tabela ARRECADACAO_FORMA a partir do codigoArrecadacaoForma
	 * (arfm_cdarrecadacaoforma) passado
	 * [UC0262] Distribuir Dados do Registro do Movimento do Arrecadador
	 * 
	 * @author Vivianne Sousa
	 * @date 06/12/2006
	 * @param codigoArrecadacaoForma
	 * @return
	 * @throws ErroRepositorioException
	 */
	public String recuperaDescricaoArrecadacaoForma(String codigoArrecadacaoForma) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situacao atual dos pagamentos (PGST_IDATUAL) (tabela
	 * PAGAMENTO_SITUACAO)
	 * [SF0008] Processar Pagamento a Maior ou a Menor
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 19/04/2006, 30/11/2006
	 * @param colecaoIdsPagamentos
	 * @param pagamentoSituacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoPagamento(Collection colecaoIdsPagamentos, Integer pagamentoSituacao) throws ErroRepositorioException;

	/**
	 * Atualiza o valor excedente e a situa��o dos pagamentos informados para o
	 * tipode situa��o informada.
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 18/04/2006, 12/12/2006
	 * @param colecaoPagamento
	 * @param pagamentoSituacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoEValorExcedentePagamento(Collection<Pagamento> colecaoPagamento, Integer pagamentoSituacao)
					throws ErroRepositorioException;

	/**
	 * Pesquisa a lista de ano/m�s de arrecada��o menores e igual ao ano/m�s de
	 * arrecada��o atual e igual ao id do im�vel informado.
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 18/04/2006, 29/11/2006
	 * @param anoMesArrecadacaoAtual
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnoMesArrecadacaoMenorIgualAtualPorImovel(Integer anoMesArrecadacaoAtual, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * Pesquisar uma cole��o de ids de localidades que possuem pagamentos
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Pedro Alexandre
	 * @date 29/11/2006
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarIdsImovelPorLocalidade(Integer idLocalidade, Integer numeroPaginas, Integer quantidadeRegistros)
					throws ErroRepositorioException;

	/**
	 * Pesquisar os ids das localidades que possuem pagamentos
	 * [UC0301] Gerar Dados Di�rios da Arrecada��o
	 * 
	 * @author Pedro Alexandre
	 * @date 04/12/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsLocalidadeComPagamentosOuDevolucoes() throws ErroRepositorioException;

	/**
	 * Pesquisar os ano/m�s de refer�ncia do pagamentos para um im�vel e ano/m�s
	 * de arrecada��o informados para o tipo de documento informado.
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Pedro Alexandre
	 * @date 06/12/2006
	 * @param anoMesArrecadacaoAtual
	 * @param idImovel
	 * @param idDocumentoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnoMesReferenciaPagamentoParaImovel(Integer anoMesArrecadacaoAtual, Integer idImovel, Integer idDocumentoTipo)
					throws ErroRepositorioException;

	/**
	 * Pesquisa a esfera do poder do cliente respons�vel pelo im�vel.
	 * [UC0301] - Gerar Dados Di�rios da Arrecadacao
	 * 
	 * @author Pedro Alexandre
	 * @date 05/12/2006
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarEsferaPoderClienteResponsavelPeloImovel(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Atualiza a situ��o dos pagamentos informados.
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Raphael Rossiter, Pedro Alexandre
	 * @date 18/04/2006, 12/12/2006
	 * @param pagamentoSituacao
	 * @param colecaoPagamentos
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoPagamento(Integer pagamentoSituacao, Collection<Pagamento> colecaoPagamentos)
					throws ErroRepositorioException;

	/**
	 * Pesquisa o im�vel pelo id fazendo os carregamentos necess�rios
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corr�a
	 * @date 16/12/06
	 * @return Imovel
	 * @throws ErroRepositorioException
	 */
	public Imovel pesquisarImovelPagamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Pesquisa o cliente pelo id fazendo os carregamentos necess�rios
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corr�a
	 * @date 16/12/06
	 * @return Cliente
	 * @throws ErroRepositorioException
	 */
	public Cliente pesquisarClientePagamento(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa o endere�o de correspond�ncia do cliente pelo seu id fazendo os carregamentos
	 * necess�rios
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corr�a
	 * @date 16/12/06
	 * @return ClienteEndereco
	 * @throws ErroRepositorioException
	 */
	public ClienteEndereco pesquisarClienteEnderecoPagamento(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa o telefone padr�o do cliente pelo seu id fazendo os carregamentos necess�rios
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corr�a
	 * @date 16/12/06
	 * @return ClienteFone
	 * @throws ErroRepositorioException
	 */
	public ClienteFone pesquisarClienteFonePagamento(Integer idCliente) throws ErroRepositorioException;

	/**
	 * Pesquisa os clientes do im�vel pelo seu id do im�vel fazendo os carregamentos necess�rios
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Rafael Corr�a
	 * @date 16/12/06
	 * @return Collection<ClienteImovel>
	 * @throws ErroRepositorioException
	 */
	public Collection<ClienteImovel> pesquisarClientesImoveisPagamento(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoLocalidade
	 * 
	 * @author Rafael Corr�a
	 * @date 12/06/06
	 * @return Collection<Pagamento>
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoMovimentoArrecadadorParaPaginacao(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * [UC0255] Filtrar Pagamentos
	 * 
	 * @author Saulo Lima
	 * @date 25/08/2009
	 * @return Collection<PagamentoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoMovimentoArrecadadorParaPaginacao(String idImovel, String idCliente,
					String idTipoRelacao, String localidadeInicial, String localidadeFinal, String idAvisoBancario, String idArrecadador,
					String periodoArrecadacaoInicial, String periodoArrecadacaoFinal, String periodoPagamentoInicio,
					String periodoPagamentoFim, Date dataPagamentoInicial, Date dataPagamentoFinal, String[] idsPagamentosSituacoes,
					String[] idsDebitosTipos, String[] idsArrecadacaoForma, String[] idsDocumentosTipos, Integer numeroPagina,
					String codigoSetorComercialInicial, String codigoSetorComercialFinal, String indicadorTotalizarPorDataPagamento,
					Collection<Integer> idsArrecadadores, String[] idsCategoria) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Verifica se j� existe resumo da arrecada��o para o ano/m�s de refer�ncia
	 * da arrecada��o
	 * [FS0003] - Verificar a exist�ncia do resumo da arrecada��o
	 * 
	 * @author Pedro Alexandre
	 * @date 16/05/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarResumoArrecadacaoPorAnoMesArrecadacao(Integer anoMesReferenciaArrecadacao, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * Pesquisa uma cole��o de ids das categorias cadastradas
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 15/12/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarIdsCategorias() throws ErroRepositorioException;

	/**
	 * Pesquisa uma cole��o de ids dos lan�amentos de itens cont�beis cadastrados
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @author Pedro Alexandre
	 * @date 15/12/2006
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDadosLancamentosItemContabil() throws ErroRepositorioException;

	/**
	 * pesquisa a lista dos acertos da Arrecada��o/Devolucao do Aviso Bancario
	 * [UC0268] - Apresentar An�lise do Aviso Banc�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 13/12/2006
	 * @param idAvisoBancario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAcertosAvisoBancario(Integer idAvisoBancario, Integer indicadorArrecadacaoDevolucao)
					throws ErroRepositorioException;

	/**
	 * O sistema seleciona a lista de pagamentos associados ao aviso banc�rio
	 * a partir da tabela PAGAMENTO com AVBC_ID=AVBC_ID da tabela AVISO_BANCARIO
	 * classificados por LOCA_ID ,IMOV_ID e PGMT_AMREFERENCIAPAGAMENTO
	 * [UC0268] - Apresentar An�lise do Aviso Banc�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 15/12/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentoAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * O sistema seleciona a lista de desvolu��es associados ao aviso banc�rio
	 * a partir da tabela DEVOLUCAO com AVBC_ID=AVBC_ID da tabela AVISO_BANCARIO
	 * [UC0268] - Apresentar An�lise do Aviso Banc�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 15/12/2006
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDevolucaoAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * Pesquisa os avisos dedu��es de um aviso banc�rio para o relat�rio atrav�s
	 * do id do aviso banc�rio
	 * [UC0268] - Apresentar An�lise do Aviso Banc�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 13/12/2006
	 * @param idAvisoBancario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDeducoesAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * [UC0268] - Apresentar An�lise do Aviso Banc�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 13/12/2006
	 * @param idAvisoBancario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarValorAcertosAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * somatorio do valor das dedu��es existentes para o aviso bancario
	 * [UC0268] - Apresentar An�lise do Aviso Banc�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 13/12/2006
	 * @param idAvisoBancario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarSomatorioDeducoesAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * [UC0268] - Apresentar An�lise do Aviso Banc�rio
	 * 
	 * @author Vivianne Sousa
	 * @date 13/12/2006
	 * @param idAvisoBancario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * Pesquisa alguns valores necessarios para
	 * obter a situa��o do aviso bancario, se aberto ou fechado
	 * [UC0254] - Efetuar An�lise do Movimento dos Arrecadadores
	 * 
	 * @author Vivianne Sousa
	 * @date 11/12/2006
	 * @param idAvisoBancario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Object[] pesquisarAvisoBancarioAvisoAcertos(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 1200
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os daods de ContaImpostosDeduzidos dos pagamentos
	 * classificados de contas para acumular o valor do imposto por
	 * localidade e categoria e tipo de imposto.
	 * 
	 * @author Pedro ALexandre
	 * @date 15/12/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idTipoImposto
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImpostosDeduzidosPagamentosClassificadosContaPorTipoImposto(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idTipoImposto) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/12/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param idSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentosNaoClassificadosMesPorSituacaoAnterior(Integer anoMesReferenciaArrecadacao, Integer idLocalidade,
					Integer idSituacaoAnterior) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/12/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param idDevolucaoSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDevolucoesNaoClassificadasMesPorSituacaoAtual(Integer anoMesReferenciaArrecadacao, Integer idLocalidade,
					Integer idDevolucaoSituacaoAtual) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/12/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idImpostoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarContasImpostosDeduzidosPagamentosContasEfetuadosEmMesesAnterioresClassificadosMesPorTipoImposto(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idImpostoTipo) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/12/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idPagamentoSituacaoAnterior
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentosNaoClassificadosComBaixaComandadaPorSituacaoAnterior(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idPagamentoSituacaoAnterior) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 18/12/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param idPagamentoSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentosNaoClassificadosMesEMesesAnterioresPorSituacaoAtual(Integer anoMesReferenciaArrecadacao,
					Integer idLocalidade, Integer idPagamentoSituacaoAtual) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro ALexandre
	 * @date 18/12/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param idDevolucaoSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarDevolucoesNaoClassificadasMesEAnterioresPorSituacaoAtual(Integer anoMesReferenciaArrecadacao,
					Integer idLocalidade, Integer idDevolucaoSituacaoAtual) throws ErroRepositorioException;

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 16/12/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param idSituacaoAtual
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentosNaoClassificadosMesPorSituacaoAtual(Integer anoMesReferenciaArrecadacao, Integer idLocalidade,
					Integer idSituacaoAtual) throws ErroRepositorioException;

	/**
	 * Exclui os dados di�rios da arrecada��o do ano/m�s da arrecada��o corrente por localidade
	 * [UC0301] Gerar Dados Di�rios da Arrecada��o
	 * 
	 * @author Pedro Alexandre
	 * @date 11/04/2006
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @throws ErroRepositorioException
	 */
	public void excluirDadosDiariosArrecadacaoPorAnoMesArrecadacaoPorLocalidade(int anoMesReferenciaArrecadacao, Integer idLocalidade)
					throws ErroRepositorioException;

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
					String indicadorAbertoFechado, String idConcessionaria) throws ErroRepositorioException;

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
					String indicadorAbertoFechado, String idConcessionaria) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa a cole��o de guias de pagamento categoria
	 * para o id da guia informada.
	 * 
	 * @author Vitor
	 * @date 14/08/2008
	 * @param idGuiaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiaPagamentoPrestacao(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa a cole��o de guias de pagamento categoria
	 * para o id da guia informada.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param idGuiaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiaPagamentoCategoria(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa a cole��o de cliente de guias de pagamento
	 * para o id da guia informada.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param idGuiaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarClienteGuiaPagamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisa a cole��o de "cliente de guias de pagamento hist�rico" para o id da guia informada.
	 * 
	 * @author Saulo Lima
	 * @date 13/07/2009
	 * @param idGuiaPagamentoHistorico
	 * @return Collection<ClienteGuiaPagamentoHistorico>
	 * @throws ErroRepositorioException
	 */
	public Collection<ClienteGuiaPagamentoHistorico> pesquisarClienteGuiaPagamentoHistorico(Integer idGuiaPagamentoHistorico)
					throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Para cada guia de pagamento transferida para o hist�rico
	 * atualiza o indicador de que a guia de pagamento est� no hist�rico.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param idsGuiasPagamento
	 * @throws ErroRepositorioException
	 */
	public void atualizarIndicadorGuiaPagamentoNoHistorico(Collection idsGuiasPagamento) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Atualiza o ano/m�s de refer�ncia da arrecada��o.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param anoMesArrecadacaoAtual
	 * @param anoMesArrecadacaoNovo
	 * @throws ErroRepositorioException
	 */
	public void atualizarAnoMesArrecadacao(int anoMesArrecadacaoAtual, int anoMesArrecadacaoNovo) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa as contas correspondentes aos pagamentos classificados de conta
	 * e os pagamentos anteriores de conta classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param numeroIndice
	 * @param quantidadeRegistros
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection pesquisarContasDePagamentosClassificadosContaEPagamentosAnterioresContaClassificadosNoMes(
					Integer anoMesReferenciaArrecadacao, Integer idLocalidade, Integer numeroIndice, Integer quantidadeRegistros,
					Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa as guias de pagamento correspondentes aos pagamentos classificados de guia de
	 * pagamento e aos pagamentos anteriores de guia de pagamento classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param numeroIndice
	 * @param quantidadeRegistros
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<GuiaPagamento> pesquisarGuiasPagamentoDePagamentosClassificadosGuiasPagamentoEPagamentosAnterioresGuiaPagamentoClassificadosNoMes(
					Integer anoMesReferenciaArrecadacao, Integer idLocalidade, Integer numeroIndice, Integer quantidadeRegistros)
					throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisar os pagamentos classificados ou com valor excedente baixado e com
	 * valor excedente maior do que zero.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param numeroIndice
	 * @param quantidadeRegistros
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> pesquisarPagamentosClassificadosOuValorExcedenteBaixado(Integer anoMesReferenciaArrecadacao,
					Integer idLocalidade, int numeroIndice, int quantidadeRegistros) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es classificadas para transferir para o hist�rico.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @param numeroIndice
	 * @param quantidadeRegistros
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Devolucao> pesquisarDevolucoesClassificadasPorLocalidade(Integer anoMesReferenciaArrecadacao, Integer idLocalidade,
					Integer numeroIndice, Integer quantidadeRegistros) throws ErroRepositorioException;

	/**
	 * O sistema seleciona a lista de pagamentos associados ao aviso banc�rio
	 * a partir da tabela PAGAMENTO com AVBC_ID=AVBC_ID da tabela AVISO_BANCARIO
	 * 
	 * @author Vivianne Sousa
	 * @date 17/01/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentoPorAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * @author Ana Maria
	 * @date 29/01/2007
	 * @param idGuiaPagamento
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarGuiaPagamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Atualiza logradouroBairro de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroBairro(LogradouroBairro logradouroBairroAntigo, LogradouroBairro logradouroBairroNovo)
					throws ErroRepositorioException;

	/**
	 * Atualiza logradouroCep de um ou mais im�veis
	 * [UC0] Atualizar Logradouro
	 * 
	 * @author Raphael Rossiter
	 * @date 22/02/2007
	 * @param
	 * @return void
	 */
	public void atualizarLogradouroCep(LogradouroCep logradouroCepAntigo, LogradouroCep logradouroCepNovo) throws ErroRepositorioException;

	/**
	 * Pesquisa o cliente da guia de pagamento
	 * atrav�s do id da Guia de Pagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 28/02/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarClienteDeGuiaPagamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisa o cliente da guia de pagamento
	 * atrav�s do id da Guia de Pagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 06/03/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarImovelDeClienteGuiaPagamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisa o cliente da guia de pagamento
	 * atrav�s do id da Guia de Pagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 28/02/2007
	 * @return String
	 * @throws ErroRepositorioException
	 */

	public Object[] pesquisarClienteDeClienteImovel(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Este caso de uso cria um filtro que ser� usado na pesquisa de pagamentos
	 * para o Relat�rio
	 * [UC0255] Filtrar Pagamentos
	 * Pesquisa os pagamentos do tipo Debito a Cobrar do Cliente
	 * pesquisarPagamentoLocalidade
	 * 
	 * @author Rafael Corr�a
	 * @date 12/12/06
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentoImovelAmbosRelatorio(String idImovel) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito -
	 * remover guia pagamento referente ao parcelamento
	 * remove a guia de pagamento do Pagamento
	 * 
	 * @author Vivianne Sousa
	 * @date 06/03/2007
	 * @param
	 * @return void
	 */
	public void removerGuiaPagamentoPagamento(Integer idPagamento) throws ErroRepositorioException;

	/**
	 * [UC0213] Desfazer Parcelamento Debito -
	 * remover guia pagamento referente ao parcelamento historico
	 * remove a guia de pagamento do Pagamento historico
	 * 
	 * @author Vitor
	 * @date 21/08/2008
	 * @param
	 * @return void
	 */
	public void removerGuiaPagamentoPagamentoHistorico(Integer idPagamento) throws ErroRepositorioException;

	/**
	 * [UC0301] Gerar Dados Di�rios da Arrecada��o
	 * Pesquisa os ano/m�s de refer�ncia dos pagamentos para ano/m�s
	 * de refer�ncia maior ou igual ao ano/m�s de refer�ncia atual da arrecada��o
	 * 
	 * @author Pedro Alexandre
	 * @date 07/03/2007
	 * @param anoMesArrecadacaoAtual
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnoMesArrecadacaoPagamentoMaiorIgualAnoMesArrecadacaoAtual(Integer anoMesArrecadacaoAtual,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0301] Gerar Dados Di�rios da Arrecada��o
	 * Pesquisa os ano/m�s de refer�ncia dos pagamentos no hist�rico para ano/m�s de
	 * refer�ncia maior ou igual ao ano/m�s de refer�ncia atual da arrecada��o
	 * 
	 * @author Hugo Lima
	 * @date 02/05/2012
	 * @param anoMesArrecadacaoAtual
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnoMesArrecadacaoPagamentoMaiorIgualAnoMesArrecadacaoAtualHistorico(Integer anoMesArrecadacaoAtual,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0113] - Faturar Grupo de Faturamento
	 * 
	 * @author Raphael Rossiter
	 * @date 20/03/2007
	 * @param idDebitoACobrar
	 * @return Integer
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarQuantidadePagamentosPorDebitoACobrar(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Atualiza a situacao atual dos pagamentos (PGST_IDATUAL) (tabela
	 * PAGAMENTO_SITUACAO)
	 * 
	 * @author Pedro Alexandre
	 * @date 23/03/2007
	 * @param colecaoIdsPagamentos
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoPagamentoClassificado(Collection<Integer> colecaoIdsPagamentos) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Caso o valor total dos pagamentos seja menor que o valor do documento,
	 * atualiza a situa��o atual dos pagamentos (PGST_IDATUAL) com valor
	 * correspondente a valor n�o confere (tabela PAGAMENTO_SITUACAO) e
	 * atualiza o id da conta nos pagamentos (seta CNTA_ID da tabela PAGAMENTO
	 * para CNTA_ID da tabela CONTA)
	 * [SB0008] Processar Pagamento a Maior ou a Menor
	 * 
	 * @author Pedro Alexandre
	 * @date 28/03/2007
	 * @param mapPagamentosValorNaoConfere
	 * @throws ErroRepositorioException
	 */
	public void processarPagamentoValorNaoConfereConta(Map<Integer, Collection> mapPagamentosValorNaoConfere)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Caso o valor total dos pagamentos seja menor que o valor do documento,
	 * atualiza a situa��o atual dos pagamentos (PGST_IDATUAL) com valor
	 * correspondente a valor n�o confere (tabela PAGAMENTO_SITUACAO) e
	 * atualiza o id da guia de pagamento nos pagamentos (seta GPAG_ID da tabela PAGAMENTO
	 * para GPAG_ID da tabela GUIA PAGAMENTO)
	 * [SB0008] Processar Pagamento a Maior ou a Menor
	 * 
	 * @author Pedro Alexandre
	 * @date 28/03/2007
	 * @author Saulo Lima
	 * @date 27/11/2008
	 *       Adicionar parametro numeroPrestacao
	 * @param mapPagamentosValorNaoConfere
	 * @param numeroPrestacao
	 * @throws ErroRepositorioException
	 */
	public void processarPagamentoValorNaoConfereGuiaPagamento(Map<Integer, Collection> mapPagamentosValorNaoConfere,
					Integer numeroPrestacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Caso o valor total dos pagamentos seja menor que o valor do documento,
	 * atualiza a situa��o atual dos pagamentos (PGST_IDATUAL) com valor
	 * correspondente a valor n�o confere (tabela PAGAMENTO_SITUACAO) e
	 * atualiza o id do d�bito a cobrar nos pagamentos (seta DBAC_ID da tabela PAGAMENTO
	 * para DBAC_ID da tabela DEBITO A COBRAR)
	 * [SB0008] Processar Pagamento a Maior ou a Menor
	 * 
	 * @author Pedro Alexandre
	 * @date 28/03/2007
	 * @param mapPagamentosValorNaoConfere
	 * @throws ErroRepositorioException
	 */
	public void processarPagamentoValorNaoConfereDebitoACobrar(Map<Integer, Collection> mapPagamentosValorNaoConfere)
					throws ErroRepositorioException;

	/**
	 * [UC0300] - Classificar Pagamentos e Devolu��es
	 * <Breve descri��o sobre o subfluxo>
	 * [SB0008] - Processar Pagamento a Maior ou a Menor
	 * 
	 * @author Pedro Alexandre
	 * @date 28/03/2007
	 * @param colecaoPagamentos
	 * @throws ErroRepositorioException
	 */
	public void processarPagamentoValorNaoConfereIdentificadorDocumentoIgualANulo(Collection colecaoPagamentos)
					throws ErroRepositorioException;

	/**
	 * Remove o id da guia de pagamento dos pagamentos referentes a conta
	 * para poder mandar a guia de pagamento para o hist�rico.
	 * [UC0000] Gerar Hist�rco para encerrar Faturamento
	 * 
	 * @author Pedro Alexandre
	 * @date 01/04/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void apagarIdGuiaPagamentoPagamentos(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisa os movimentos dos arrecadores para a gera��o do relat�rio
	 * [UCXXXX] Acompanhar Movimento dos Arrecadadores
	 * 
	 * @author Rafael Corr�a
	 * @param idConcessionaria
	 * @date 02/04/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarMovimentoArrecadadoresRelatorio(Integer mesAnoReferencia, Integer idArrecadador, Integer idFormaArrecadacao,
					Date dataPagamentoInicial, Date dataPagamentoFinal, Integer idConcessionaria) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 750
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria dos
	 * pagamentos classificados de conta para tipo de financiamento igual a
	 * doa��es
	 * 
	 * @author Pedro Alexandre
	 * @date 03/04/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosClassificadosContaFinanciamentoTipoDoacoes(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idLancamentoItemContabil)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 4150
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade, categoria e item
	 * cont�bil para os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s para tipo de financiamento igual doa��es.
	 * 
	 * @author Pedro Alexandre
	 * @date 03/04/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoParcelamentoDoacoes(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idLancamentoItemContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa as guias de pagamento correspondentes aos pagamentos classificados de guia de
	 * pagamento e aos pagamentos anteriores de guia de pagamento classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<GuiaPagamento> pesquisarGuiasPagamentoDePagamentosClassificadosGuiasPagamentoEPagamentosAnterioresGuiaPagamentoClassificadosNoMes(
					Integer anoMesReferenciaArrecadacao, Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisar os pagamentos classificados ou com valor excedente baixado e com
	 * valor excedente maior do que zero para transferir para o hist�rico.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosClassificadosOuValorExcedenteBaixado(Integer anoMesReferenciaArrecadacao,
					Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 4000
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade, categoria e item
	 * cont�bil para os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s para tipo de financiamento igual a parcelamento de
	 * servi�o e grupo de parcelamento diferente de juros cobrados.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idLancamentoItemContabil
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesFinanciamentoTipoDoacoes(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idLancamentoItemContabil)
					throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa as devolu��es classificadas para transferir para o hist�rico.
	 * 
	 * @author Pedro Alexandre
	 * @date 10/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Devolucao> pesquisarDevolucoesClassificadasPorLocalidade(Integer anoMesReferenciaArrecadacao, Integer idLocalidade)
					throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa as contas correspondentes aos pagamentos classificados de conta
	 * e os pagamentos anteriores de conta classificados no m�s.
	 * 
	 * @author Pedro Alexandre
	 * @date 09/01/2007
	 * @param anoMesReferenciaArrecadacao
	 * @param idLocalidade
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> pesquisarContasDePagamentosClassificadosContaEPagamentosAnterioresContaClassificadosNoMes(
					Integer anoMesReferenciaArrecadacao, Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisa a conta
	 * 
	 * @author Pedro Alexandre
	 * @date 10/01/2007
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Conta pesquisarConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Pesquisa a ContaHistorico
	 * 
	 * @author Saulo Lima
	 * @date 11/08/2009
	 * @param idContaGeral
	 * @return ContaHistorico
	 * @throws ErroRepositorioException
	 */
	public ContaHistorico pesquisarContaHistorico(Integer idContaGeral) throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * Pesquisar o pagamento
	 * 
	 * @author Pedro Alexandre
	 * @date 10/04/2007
	 * @param idPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Pagamento pesquisarPagamento(Integer idPagamento) throws ErroRepositorioException;

	public String[] pesquisarIdsPagamentoAjsuteDESO(String idAvisoCorreto, String idAvisoDuplicado) throws ErroRepositorioException;

	public Collection<Integer> pesquisarIdsSetoresComPagamentosOuDevolucoes() throws ErroRepositorioException;

	public Integer pesquisarIdLocalidadePorSetorComercial(Integer idSetorComercial) throws ErroRepositorioException;

	public Collection<DebitoACobrar> pesquisarDebitosACobrarDePagamentosClassificadosGuiasPagamentoEPagamentosAnterioresGuiaPagamentoClassificadosNoMes(
					Integer anoMesReferenciaArrecadacao, Integer idLocalidade) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 4400, 4410, 4420, 4430
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos cr�ditos realizados por localidade e categoria, para
	 * os pagamentos de contas efetuados em meses anteriores classificados no
	 * m�s, para origem do cr�dito igual a descontos concedidos.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idOrigemCredito
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorCreditoRealizadoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesPorOrigemCredito(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idOrigemCredito)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 400 e 500
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria dos
	 * pagamentos classificados de conta para tipo de financiamento igual a
	 * parcelamento de �gua ou parcelamento de esgoto.
	 * 
	 * @author Pedro Alexandre
	 * @date 18/04/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idFinanciamentoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosClassificadosContaPorFinanciamentoTipo(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idFinanciamentoTipo) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 3800
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade e categoria para os
	 * pagamentos de contas efetuados em meses anteriores classificados no m�s
	 * para tipo de financiamento igual a parcelamento de �gua.
	 * 
	 * @author Pedro Alexandre
	 * @date 18/04/2007
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idCategoria
	 * @param idFinanciamentoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesPorFinanciamentoTipo(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idCategoria, Integer idFinanciamentoTipo)
					throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 300
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor do d�bitos cobrados por localidade, categoria e item
	 * cont�bil dos pagamentos classificados de conta para tipo de financiamento
	 * igual a servi�o
	 * 
	 * @author Pedro Alexandre
	 * @date 22/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idLancamentoItemContabil
	 * @param idCategoria
	 * @param colecaoIdsFinanciamentoTipo
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosClassificadosContaPorFinanciamentoTipo(Integer idLocalidade,
					Integer anoMesReferenciaArrecadacao, Integer idLancamentoItemContabil, Integer idCategoria,
					Collection<Integer> colecaoIdsFinanciamentoTipo) throws ErroRepositorioException;

	/**
	 * Sequencial do tipo lan�amento igual a 3700
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Acumula o valor dos d�bitos cobrados por localidade, categoria e item
	 * cont�bil para os pagamentos de contas efetuados em meses anteriores
	 * classificados no m�s para tipo de financiamento igual a servi�o.
	 * 
	 * @author Pedro Alexandre
	 * @date 23/05/2006
	 * @param idLocalidade
	 * @param anoMesReferenciaArrecadacao
	 * @param idLancamentoItemContabil
	 * @param idCategoria
	 * @param idsFinanciamentoTipos
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal acumularValorDebitoCobradoPagamentosContasEfetuadosEmMesesAnterioresClassificadosNoMesPorFinanciamentoTipo(
					Integer idLocalidade, Integer anoMesReferenciaArrecadacao, Integer idLancamentoItemContabil, Integer idCategoria,
					Collection<Integer> idsFinanciamentoTipos) throws ErroRepositorioException;

	/**
	 * [UC0242] Registrar Movimento Arrecadadores
	 * Atualiza o arrecadador contrato
	 * 
	 * @author S�vio Luiz,Vivianne Sousa
	 * @date 19/04/2007,28/11/2007
	 * @return Cole��o de Bancos
	 * @throws ErroRepositorioException
	 */

	public void atualizarDadosArrecadadorContrato(ArrecadadorContrato arrecadadorContrato, boolean flagEnvioDebitoAutomatico,
					boolean flagRetornoCodigoBarras, boolean flagRetornoDebitoAutomatico, boolean flagRetornoFichaCompensacao,
					boolean flagRetornoBoletoBancario, boolean indicadorAtualizaNSADebitoAutomatico,
					boolean indicadorAtualizaNSAParcelamentoResponsavel) throws ErroRepositorioException;

	/**
	 * Pesquisar os ano/m�s de refer�ncia do pagamentos para um im�vel e ano/m�s
	 * de arrecada��o informados para o tipo de documento informado.
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * 
	 * @author Pedro Alexandre
	 * @date 06/12/2006
	 * @param anoMesArrecadacaoAtual
	 * @param idImovel
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAnoMesReferenciaPagamentoParaImovel(Integer anoMesArrecadacaoAtual, Integer idImovel)
					throws ErroRepositorioException;

	/**
	 * Seleciona os pagamentos hist�rios de um aviso
	 * 
	 * @author Rafael Corr�a
	 * @date 23/04/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarPagamentoHistoricoAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * 
	 * @author Vivianne Sousa
	 * @data 23/04/2006
	 * @param idConta
	 * @return idParcelamento
	 */
	public Object[] pesquisarPagamentoDeConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0150] Retificar Conta
	 * 
	 * @author Vivianne Sousa
	 * @data 23/04/2006
	 * @param idPagamento
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	public void atualizarContaEmPagamento(Integer idPagamento, Integer idConta) throws ErroRepositorioException;

	/**
	 * @author S�vio Luiz
	 * @data 28/04/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public Integer pesquisarIdPagamentoDaGuia(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * @author S�vio Luiz
	 * @data 28/04/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public Integer pesquisarIdPagamentoDoDebitoACobrar(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 04/06/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorEstadoPorUnidadeNegocio(int anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Vivianne Sousa
	 * @created 04/06/2007
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorUnidadeNegocio(int anoMesReferencia, Integer localidade)
					throws ErroRepositorioException;

	/**
	 * Pesquisar pagamentos pelo aviso banc�rio
	 * 
	 * @author Ana Maria
	 * @date 11/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public PagamentosDevolucoesHelper filtrarPagamentos(FiltroPagamento filtroPagamento, FiltroPagamentoHistorico filtroPagamentoHistorico)
					throws ErroRepositorioException;

	/**
	 * Pesquisar devolu��oes pelo aviso banc�rio
	 * 
	 * @author Ana Maria
	 * @date 11/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public PagamentosDevolucoesHelper filtrarDevolucoes(FiltroDevolucao filtroDevolucao, FiltroDevolucaoHistorico filtroDevolucaoHistorico)
					throws ErroRepositorioException;

	/**
	 * Pesquisar valores de arrecada��o e devolu��o do aviso banc�rio
	 * 
	 * @author Ana Maria
	 * @date 14/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */
	public ValoresArrecadacaoDevolucaoAvisoBancarioHelper pesquisarValoresAvisoBancario(Integer idAvisoBancario)
					throws ErroRepositorioException;

	/**
	 * Atualizar Pagamentos
	 * 
	 * @author Ana Maria
	 * @date 15/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public void atualizarAvisoBancarioPagamentos(Collection<Integer> idsPagamentos, Collection<Integer> idsPagamentosHistorico,
					Integer idAvisoBancarioD) throws ErroRepositorioException;

	/**
	 * Atualizar valor de arrecada��o calculado
	 * 
	 * @author Ana Maria
	 * @date 15/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public void atualizarValorArrecadacaoAvisoBancario(String valorArrecadacaoCalculado, Integer idAvisoBancario)
					throws ErroRepositorioException;

	/**
	 * Atualizar Devolu��es
	 * 
	 * @author Ana Maria
	 * @date 15/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public void atualizarAvisoBancarioDevolucoes(Collection<Integer> idsDevolucoes, Collection<Integer> idsDevolucoesHistorico,
					Integer idAvisoBancarioD) throws ErroRepositorioException;

	/**
	 * Atualizar valor de devolu��o calculado
	 * 
	 * @author Ana Maria
	 * @date 15/06/2007
	 * @return Collection
	 * @throws ErroRepositorioException
	 */

	public void atualizarValorDevolucaoAvisoBancario(String valorDevolucaoInformado, String valorDevolucaoCalculado, Integer idAvisoBancario)
					throws ErroRepositorioException;

	/**
	 * [UC0263] Filtrar Movimento dos Arrecadadores - Relat�rio
	 * 
	 * @author Ana Maria
	 * @date 13/07/2007
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
					String indicadorAceitacao, String idConcessionaria) throws ErroRepositorioException;

	/**
	 * [UC0619] Gerar Rela��o de Acompanhamento dos Movimentos Arrecadadores por NSA
	 * 
	 * @author Ana Maria
	 * @date 12/07/2007
	 * @param idMovimentoArrecadador
	 * @return
	 */
	public Collection<MovimentoArrecadadoresPorNSAHelper> gerarMovimentoArrecadadoresNSA(Collection<Integer> idsArrecadadorMovimento,
					Integer codigoFormaArrecadacao, String idConcessionaria) throws ErroRepositorioException;

	/**
	 * Processamento R�pido
	 * 
	 * @author Raphael Rossiter
	 * @date 17/08/2007
	 * @return Collection<Conta>
	 * @throws ErroRepositorioException
	 */
	public Collection<Conta> pesquisarContaComPagamentoHistorico() throws ErroRepositorioException;

	/**
	 * Pesquisa a agencia
	 * 
	 * @author S�vio Luiz
	 * @date 05/11/2007
	 * @return Agencia
	 * @throws ErroRepositorioException
	 */
	public Agencia pesquisarAgencia(String codigoAgencia, Integer idBanco) throws ErroRepositorioException;

	/**
	 * [UC0626] Gerar Resumo de Metas Acumulado no M�s (CAERN)
	 * 
	 * @author S�vio Luiz
	 * @data 28/11/2007
	 * @param idConta
	 * @return idParcelamento
	 */
	public Collection pesquisarPagamentoDeContas(Collection colecaoConta) throws ErroRepositorioException;

	/**
	 * [UC0739] Informar Situa��o de Expurgo do Pagamento
	 * 
	 * @author S�vio Luiz
	 * @data 02/01/2008
	 * @param idConta
	 * @return idParcelamento
	 */
	public Collection pesquisarDadosPagamentoExpurgado(String dataPagamento, Integer idCliente) throws ErroRepositorioException;

	/**
	 * [UC0739] Informar Situa��o de Expurgo do Pagamento
	 * 
	 * @author S�vio Luiz
	 * @data 02/01/2008
	 * @param idConta
	 * @return idParcelamento
	 */
	public Collection pesquisarDadosPagamentoHistoricoExpurgado(String dataPagamento, Integer idCliente) throws ErroRepositorioException;

	/**
	 * [UC0739] Informar Situa��o de Expurgadodo Pagamento
	 * 
	 * @author S�vio Luiz
	 * @date 04/01/2008
	 * @param idsPagamentos
	 * @return void
	 */
	public void atualizarSituacaoExpurgado(Collection colecaoPagamento) throws ErroRepositorioException;

	/**
	 * @author eduardo henrique
	 * @date 18/08/2008
	 *       [UC0188] - Manter Guia de Pagamento
	 * @param idGuiaPagamento
	 *            - Guia de Pagamento a ser verificada
	 * @param prestacoesNaoConsideradas
	 *            - Nr's das Presta��es que n�o ser�o consideradas para verifica��o. Ex.: As mesmas
	 *            podem estar sendo canceladas.
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer verificarExistenciaPrestacaoGuiaPagamento(Integer idGuiaPagamento, List prestacoesNaoConsideradas)
					throws ErroRepositorioException;

	/**
	 * Recupera o ID da ContaGeral do pagamento especificado
	 * 
	 * @author Saulo Lima
	 * @date 23/01/2009
	 * @param idPagamento
	 * @return idContaGeral
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarIdDaContaGeralNoPagamento(Integer idPagamento) throws ErroRepositorioException;

	public List consultarPagamentoHistoricoSemAcrescimoImpontualidade(Integer grupoFaturamento) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por consultar os dados de um PagamentoHistorico
	 * 
	 * @date 17/08/2009
	 * @author Virg�nia Melo
	 * @param idPagamentoHistorico
	 * @return PagamentoHistorico
	 */
	public PagamentoHistorico consultarPagamentoHistorico(Integer idPagamentoHistorico) throws ErroRepositorioException;

	public Integer consultarQtdePagamentoHistoricoParaDebitoACobrarParcelamentoComPrestacaoNula(Integer id) throws ErroRepositorioException;

	public DebitoACobrar pesquisarDebitoACobrar(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * obt�m uma Devolucao
	 * 
	 * @param devolucao
	 * @return Devolucao
	 * @throws ErroRepositorioException
	 */
	public Devolucao obterDevolucao(Devolucao devolucao) throws ErroRepositorioException;

	public Collection pesquisarPagamentosHistoricoPorConta(Integer localidade, Integer id, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	public Collection pesquisarPagamentosHistoricoPorGuiaPagamentoComGuiaInformada(Integer localidade, Integer id, Integer numeroPrestacao)
					throws ErroRepositorioException;

	public Collection pesquisarPagamentosHistoricoPorGuiaPagamentoSemGuiaInformada(Integer localidade, Integer id, Integer idCliente)
					throws ErroRepositorioException;

	public Collection<PagamentoHistorico> pesquisarPagamentoHistoricoCriteria(FiltroPagamentoHistorico filtroPagamentoHistorico)
					throws ErroRepositorioException;

	public Collection pesquisarArrecadadorMovimentoItem(Integer idArrecadadorMovimento, Short indicadorAceitacao)
					throws ErroRepositorioException;

	public BigDecimal pesquisarValorDevolucao(Integer idAvisoBancario) throws ErroRepositorioException;

	public BigDecimal pesquisarValorDevolucaoHistorico(Integer idAvisoBancario) throws ErroRepositorioException;

	public Collection pesquisarAvisoBancarioPorMovimentoArrecadador(Integer idArrecadadorMovimento) throws ErroRepositorioException;

	public Collection pesquisarGuiaPagamentoPrestacaoHistorico(Integer idGuiaPagamento) throws ErroRepositorioException;

	public PagamentoHistorico pesquisarPagamentoHistorico(Integer idGuiaPagamento) throws ErroRepositorioException;

	public Collection pesquisarGuiaPagamentoHistorico(Integer idGuiaPagamento) throws ErroRepositorioException;

	public Collection<Object[]> pesquisarPagamentosPorGuiaPagamentoHistorico(Integer idLocalidade, Integer idGuiaPagamento,
					Integer numeroPrestacao, Integer idAvisoBancario) throws ErroRepositorioException;

	public Collection<GuiaPagamentoPrestacaoHistorico> pesquisarGuiaPagamentoPrestacaoHistorico(Integer idGuiaPagamento,
					Integer numeroPrestacao) throws ErroRepositorioException;

	public Collection pesquisarDebitoACobrar(Integer idImovel, Integer idDebitoTipo) throws ErroRepositorioException;

	public Collection pesquisarDebitoACobrarHistorico(Integer idImovel, Integer idDebitoTipo) throws ErroRepositorioException;

	public Collection<Object[]> pesquisarPagamentosPorDebitoACobrarHistoricoComDebitoInformado(Integer idDebitoTipo, Integer idImovel,
					Integer prestacao) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por consultar o ultimo cobranca documento
	 * 
	 * @date 13/05/20010
	 * @author Willian Pereira
	 * @param idContaHistorico
	 */
	@Deprecated
	public CobrancaDocumento consultarUltimoCobrancaDocumento(Integer idContaHistorico) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por consultar conta historico
	 * 
	 * @date 13/05/20010
	 * @author Willian Pereira
	 * @param idContaHistorico
	 */

	@Deprecated
	public ContaHistorico consultarContaHistorico(Integer idContaHistorico) throws ErroRepositorioException;

	public Parcelamento consultarParcelamentoConta(Integer idConta) throws ErroRepositorioException;

	public ContaHistorico consultarContaHistoricoRemuneracoes(Integer idContaHistorico) throws ErroRepositorioException;

	public CobrancaDocumento consultarUltimoCobrancaDocumentoRemuneracao(Integer idContaHistorico) throws ErroRepositorioException;

	/**
	 * Otimiza��o do m�todo atualizarSituacaoEValorExcedentePagamento
	 * 
	 * @see atualizarSituacaoEValorExcedentePagamento
	 * @author jns
	 * @param colecaoPagamento
	 * @param pagamentoSituacao
	 * @throws ErroRepositorioException
	 */
	public void atualizarSituacaoValorExcedentePagamento(Collection<Pagamento> colecaoPagamento, Integer pagamentoSituacao)
					throws ErroRepositorioException;

	/**
	 * @author jns
	 * @date 06/10/2010
	 *       ----------------------------------------------------------------
	 *       Otimiza��o do m�todo pesquisarPagamentosPorConta. Trazendo apenas
	 *       valores necess�rios. Mantido o m�todo original para evitar poss�veis
	 *       erros em l�gicas anteriores.
	 * @see pesquisarPagamentosPorConta
	 *      ----------------------------------------------------------------
	 * @param idImovel
	 * @param anoMesReferenciaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentosPorContaMovimentoArrecadadores(Integer idImovel, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	public Collection<Object[]> pesquisarPagamentosPorContaJDBC(Integer idImovel, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	public Collection pesquisarPagamentosPorContaNovo(Integer idImovel, Integer anoMesReferenciaPagamento) throws ErroRepositorioException;

	public Collection<DebitoCobradoHistorico> consultarHistoricoDebitoCobrado(Integer idContaHistorico) throws ErroRepositorioException;

	public void atualizarIndPagamentoContaNaoClassif(Integer idConta) throws ErroRepositorioException;

	public CobrancaDocumento consultarCobrancaDocumentoParcelamento(Integer idParcelamento) throws ErroRepositorioException;

	/**
	 * Consulta ResumoArrecadacao para a gera��o do relat�rio '[UC0345] Gerar
	 * Relat�rio de Resumo Arrecada��o' de acordo com a op��o de totaliza��o.
	 * 
	 * @author Hebert Falc�o
	 * @created 22/07/2011
	 */
	public Collection consultarResumoArrecadacaoRelatorioPorLocalidadePorSetorComercial(int anoMesReferencia, Integer idLocalidade,
					Integer idSetorComercial) throws ErroRepositorioException;

	/**
	 * Consulta dados da tabela dados diarios arrecadacao
	 * 
	 * @author Genival Barbosa
	 * @created 25/07/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection consultarDadosRelatorioControleDocumentosArrecadacaoAnalitico(int idGerenciaRegional, Integer idArrecadador,
					Integer idArrecadacaoForma) throws ErroRepositorioException;

	/**
	 * Retorna imovel com os dados para o processamento de c�digo de barras para o legado do cliente
	 * DESO.
	 * 
	 * @author P�ricles Tavares
	 * @created 15/09/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Imovel pesquisarImovelCodigoBarrasLegadoDESO(Integer idImovel) throws ErroRepositorioException;

	/**
	 * Retorna Debito Tipo com os dados para o processamento de c�digo de barras para o legado do
	 * cliente
	 * DESO.
	 * 
	 * @author P�ricles Tavares
	 * @created 15/09/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public DebitoTipo pesquisarDebitoTipoPorServicoTipo(Integer idServicoTipo) throws ErroRepositorioException;

	/**
	 * Retorna o Documento Cobranca com os dados para o processamento de c�digo de barras para o
	 * legado do cliente DESO.
	 * 
	 * @author P�ricles Tavares
	 * @created 16/09/2011
	 * @return
	 * @throws ErroRepositorioException
	 */
	public CobrancaDocumento pesquisarDocumentoCobrancaPorImovelSequencial(Integer idImovel, Integer numeroSequencialDocumento)
					throws ErroRepositorioException;

	/**
	 * Retorna o Documento Cobranca Item com os dados para o processamento de c�digo de barras para
	 * o legado do cliente DESO.
	 * 
	 * @author P�ricles Tavares
	 * @date 16/09/2011
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarCobrancaDocumentoItem(int idCobrancaDocumento) throws ErroRepositorioException;

	/**
	 * Retorna a Guia Pagamento Prestacao com os dados para o processamento de c�digo de barras para
	 * o legado do cliente DESO.
	 * 
	 * @author P�ricles Tavares
	 * @date 16/09/2011
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public GuiaPagamentoPrestacao pesquisarGuiaPagamentoPrestacao(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * Retorna o Documento Cobranca Item com os dados para o processamento de c�digo de barras para
	 * o legado do cliente DESO.
	 * 
	 * @author P�ricles Tavares
	 * @date 16/09/2011
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public GuiaPagamentoPrestacaoHistorico pesquisarGuiaPagamentoHistoricoPrestacao(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * [SB0016] � Processar Pagamento Legado DESO
	 * Retorna a Guia Pagamento com os dados para o processamento de c�digo de
	 * barras para
	 * o legado do cliente DESO.
	 * 
	 * @author P�ricles Tavares
	 * @date 16/09/2011
	 * @return Collection retorno
	 * @throws ErroRepositorioException
	 */
	public GuiaPagamento pesquisarGuiaPagamentoNormalRetificadaIncluidaEntradaParcelamento(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 26/09/2011
	 * @param imovelId
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAcrescimoImpontualidade(Integer imovelId) throws ErroRepositorioException;

	/**
	 * Retorna o BoletoBancarioMovimentacao dado o ID do ArrecadadorMovimentoItem.
	 * 
	 * @author P�ricles Tavares
	 * @date 06/10/2011
	 * @param idArrecadadorMovimentoItem
	 * @return BoletoBancarioMovimentacao
	 * @throws ErroRepositorioException
	 */
	public BoletoBancarioMovimentacao pesquisarBoletoBancarioMovimentacao(Integer idArrecadadorMovimentoItem)
					throws ErroRepositorioException;

	/**
	 * Retorna Guia de pagamento.
	 * 
	 * @param idGuiaPagamento
	 *            idGuiaPagamento
	 * @author P�ricles Tavares
	 * @date 06/10/2011
	 * @return Descri��o do retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public GuiaPagamento pesquisarGuiaPagamentoCliente(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * Retorna Documento cobranca.
	 * 
	 * @param idDocumentoCobranca
	 *            idDocumentoCobranca
	 * @author P�ricles Tavares
	 * @date 06/10/2011
	 * @return Documento cobranca
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public CobrancaDocumento pesquisarDocumentoCobrancaCliente(Integer idDocumentoCobranca) throws ErroRepositorioException;

	/**
	 * Retorna Boleto Banc�rio Lan�amento Retorno.
	 * 
	 * @param idBoletoBancarioLancamentoRetorno
	 *            idBoletoBancarioLancamentoRetorno
	 * @author P�ricles Tavares
	 * @date 06/10/2011
	 * @return Boleto Banc�rio Lan�amento Retorno
	 * @exception ErroRepositorioException
	 *                Descri��o da exce��o
	 */
	public BoletoBancarioLancamentoRetorno pesquisarBoletoBancarioLancamentoRetorno(Integer idBoletoBancarioLancamentoRetorno)
					throws ErroRepositorioException;

	/**
	 * Retorna o BoletoBancarioMotivoOcorrencia dado o ID do ArrecadadorMovimentoItem.
	 * 
	 * @author P�ricles Tavares
	 * @date 06/10/2011
	 * @param idArrecadadorMovimentoItem
	 * @return BoletoBancarioMovimentacao
	 * @throws ErroRepositorioException
	 */
	public List<BoletoBancarioMotivoOcorrencia> pesquisarBoletoBancarioMotivoOcorrencia(Integer idArrecadadorMovimentoItem)
					throws ErroRepositorioException;

	/**
	 * Retorna o BoletoBancarioLancamentoEnvio dado o ID do idBoletoBancarioLancamentoEnvio.
	 * 
	 * @author P�ricles Tavares
	 * @date 10/10/2011
	 * @param idBoletoBancarioLancamentoEnvio
	 * @return BoletoBancarioMovimentacao
	 * @throws ErroRepositorioException
	 */
	public BoletoBancarioLancamentoEnvio pesquisarBoletoBancarioLancamentoEnvio(Integer... idBoletoBancarioLancamentoEnvio)
					throws ErroRepositorioException;

	/**
	 * [UC0259] - Processar Pagamento com C�digo de Barras
	 * [SB0017] � Gerar boletos banc�rios para presta��es de guia de pagamento
	 * 
	 * @author Ailton Sousa
	 * @date 21/10/2011
	 * @param idGuiaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarBoletoBancarioAssociadoParcelamento(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * [UC3024] � Processar Movimento de Retorno do Boleto Banc�rio
	 * [SB0002] � Inserir ocorr�ncias do retorno
	 * M�todo que obt�m os motivos de ocorr�ncia restringindo ou n�o pelo tipo da ocorr�ncia
	 * 
	 * @author Anderson Italo
	 * @date 21/10/2011
	 * @throws ErroRepositorioException
	 */
	public List<BoletoBancarioMotivoOcorrencia> pesquisarBoletoBancarioMotivoOcorrencia(Integer idTipoOcorrencia,
					String codigosMotivosOcorrencias) throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */
	DebitoAutomaticoMovimento pesquisarUltimoDebitoAutomaticoMovimentoConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0319] � Gerar Movimento de D�bito Autom�tico para o Banco.
	 * 
	 * @author Ailton Sousa
	 * @date 03/11/2011
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return Date
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarDataVencimentoGuiaPagamentoPrestacao(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0319] � Gerar Movimento de D�bito Autom�tico para o Banco.
	 * Pesquisa o valor total da guia de pagamentro presta��o, pela guia de pagamento e pelo n�mero
	 * da presta��o.
	 * 
	 * @author Ailton Sousa
	 * @date 03/11/2011
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal pesquisarValorTotalGuiaPagamentoPrestacao(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * @param imovelId
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection pesquisarAcrescimoImpontualidadeHistorico(Integer imovelId) throws ErroRepositorioException;

	/**
	 * [UC0276] - Encerrar Arrecada��o do M�s
	 * Pesquisa os pagamentos n�o classificados, que s�o os do ano/m�s de
	 * refer�ncia igual ou anterior ao da arrecada��o e que est�o n�o
	 * classificados,com situa��o atual diferente de pagamento classificado e de
	 * baixar valor excedente
	 * 
	 * @author Josenildo Neves
	 * @date 02/03/2012
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Pagamento> pesquisarPagamentoNaoClassificado(Integer anoMes) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento de Arrecadadores
	 * [SB0002] � Processar Pagamento de Guia de Pagamento Matr�cula
	 * 
	 * @author Anderson
	 * @date 09/03/2012
	 */
	public GuiaPagamentoHistorico pesquisarGuiaPagamentoHistoricoNormalRetificadaIncluidaEntradaParcelamento(Integer idGuiaPagamento,
					Integer numeroPrestacao) throws ErroRepositorioException;

	public void atualizarContaESituacaoPagamento(String idsPagamento, Integer idConta, Integer idSituacao) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores
	 * M�todo que obt�m os dados do �ltimo movimento processado para gerar o relat�rio e enviar por
	 * email
	 * 
	 * @author Anderson Italo
	 * @date 11/03/2010
	 * @throws ErroRepositorioException
	 */
	public ArrecadadorMovimento obterUltimoMovimentoGerado() throws ErroRepositorioException;

	/**
	 * [UC3046] Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico
	 * 
	 * @author Carlos Chrystian
	 * @created 21/03/2012
	 *          Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico.
	 */
	public Collection pesquisarGuiaPagamentoPrestacao(Date dataVencimentoGuiaPagamentoInicial, Date dataVencimentoGuiaPagamentoFinal,
					Integer clienteResponsavel, Short indicadorTipoGuia, Integer pageOffset) throws ErroRepositorioException;

	/**
	 * [UC3046] Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico
	 * 
	 * @author Carlos Chrystian
	 * @created 21/03/2012
	 *          Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico.
	 */
	public Integer pesquisarQuantidadeGuiaPagamentoPrestacao(Date dataVencimentoGuiaPagamentoInicial,
					Date dataVencimentoGuiaPagamentoFinal, Integer clienteResponsavel, Short indicadorTipoGuia)
					throws ErroRepositorioException;

	/**
	 * [UC3021] � Processar Pagamento com Boleto Banc�rio
	 * Remover Guia de Pagamento Categoria
	 * 
	 * @author Hebert Falc�o
	 * @date 27/04/2012
	 */
	public void removerGuiaPagamentoCategoria(Integer idGuiaPagamento, Short numeroPrestacao) throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @throws ErroRepositorioException
	 */
	public void atualizarValorImpostoConta(Integer idConta, BigDecimal valorImposto) throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @throws ErroRepositorioException
	 */
	public void removerContaImpostosDeduzidos(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0242] - Registrar Movimento dos Arrecadadores
	 * [SB0001] - Validar Arquivo de Movimento de Arrecadador
	 * Verificar a j� exist�ncia do Movimento em Quest�o na tabela ARRECADADOR_MOVIMENTO
	 * 
	 * @author Anderson Italo
	 * @date 13/05/2012
	 * @throws ErroRepositorioException
	 */
	public ArrecadadorMovimento pesquisaArrecadadorMovimentoExistente(Short codigoBanco, String codigoConvenio,
					Integer numeroSequencialArquivo) throws ErroRepositorioException;

	/**
	 * [UC0268] Apresentar An�lise do Aviso Banc�rio
	 * Obter Valor Total de Pagamentos N�o Classificados associados ao Aviso Banc�rio
	 * 
	 * @author Anderson Italo
	 * @date 14/06/2012
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterValorTotalPagamentosNaoClassificadosPorAviso(AvisoBancario avisoBancario) throws ErroRepositorioException;

	/**
	 * [UC0268] Apresentar An�lise do Aviso Banc�rio
	 * Obter Valor Total de Pagamentos Classificados associados ao Aviso Banc�rio
	 * 
	 * @author Anderson Italo
	 * @date 14/06/2012
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterValorTotalPagamentosClassificadosPorAviso(AvisoBancario avisoBancario) throws ErroRepositorioException;

	/**
	 * [UC0339] - Consultar Dados Di�rios da Arrecada��o
	 * [SB0011] � Obter Valor Despesa Banc�ria do Arrecadador
	 * Obt�m os dados da tarifa do arrecadador a partir da tabela ARRECADADOR_CONTRATO_TARIFA.
	 * 
	 * @author Josenildo Neves
	 * @date 19/06/2012
	 * @throws ErroRepositorioException
	 */
	List<ArrecadadorContratoTarifaHelper> pesquisarArrecadadorContratoTarifaPorArrecadacaoDadosDiarios(Integer idArrecadacaoDadosDiarios,
					Integer anoMesArrecadacao, String localidade, String idElo, String setorComercial, String idGerenciaRegional,
					String unidadeNegocioId, String[] idsImovelPerfil, String[] idsLigacaoAgua, String[] idsLigacaoEsgoto,
					String[] idsCategoria, String[] idsEsferaPoder, String[] idsDocumentosTipos) throws ErroRepositorioException;

	/**
	 * [UC0235] - Inserir aviso banc�rio
	 * [SB0001] - Preparar Preenchimento da Conta Banc�ria
	 * 
	 * @author Andr� Lopes
	 * @date 07/05/2013
	 * @throws ErroRepositorioException
	 */
	public List<ArrecadadorContrato> pesquisarContaBancaria(Integer idArrecadacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public ImovelCobrancaSituacao pesquisarImovelEmCobrancaAdministrativa(Integer idImovel) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa
	 * Obt�m os itens dos documentos de cobran�a associados ao comando informado
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public Collection pesquisarItensCobrancaDocumentoPorComando(Integer idCobrancaAcaoAtividadeComando, Integer idConta,
					Integer idGuiaPagamento, Integer idImovel, Integer numeroPrestacao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public DebitoCobradoHistorico pesquisarDebitoCobradoCobrancaAdministrativa(Integer idConta,
					String parametroTiposDebitosCobrancaAdministrativa, Date dataImplantacaoCobranca, Date dataRetiradaCobranca)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public Object pesquisarGuiaPagamentoCobrancaAdministrativa(Integer idGuiaPagamento, Integer idGuiaPagamentoHistorico,
					Date dataImplantacaoCobranca, Date dataRetiradaCobranca, String parametroTiposDebitosCobrancaAdministrativa,
					Integer idDebitoTipoEntradaParcelamentoCobrancaAdm) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public ContaHistorico pesquisarContaEntradaParcelamentoCobrancaAdministrativa(Integer idConta, Date dataImplantacaoCobranca,
					Date dataRetiradaCobranca) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es.
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa.
	 * Obt�m o somat�rio dos valores dos srvi�os cobrados de
	 * parcelamento associados a itens da cobran�a.
	 * Administrativa.
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public BigDecimal obterValorBaseParcelamentoCobrancaAdministrativa(Integer idConta, String parametroTiposDebitosCobrancaAdministrativa,
					Date dataImplantacaoCobranca, Date dataRetiradaCobranca) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es.
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa.
	 * Obt�m o somat�rio dos valores dos servi�os cobrados ditos
	 * especiais (Remuner�veis).
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public BigDecimal obterValorBaseEspecialCobrancaAdministrativa(Integer idConta, String parametroTiposDebitosCobrancaAdministrativa,
					String parametroServicoEspecialCobrancaAdministrativa, Date dataImplantacaoCobranca, Date dataRetiradaCobranca)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es.
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa.
	 * Obt�m o somat�rio dos valores dos servi�os cobrados ditos (N�o Remuner�veis).
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public BigDecimal obterValorServicosComumCobrancaAdministrativa(Integer idConta, String parametroTiposDebitosCobrancaAdministrativa,
					String parametroServicoEspecialCobrancaAdministrativa, Date dataImplantacaoCobranca, Date dataRetiradaCobranca)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es.
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa.
	 * 
	 * @author Anderson Italo
	 * @date 06/07/2012
	 */
	public CobrancaContrato pesquisarContratoCobrancaAdministrativa(Integer idCobrancaAcaoAtividadeComando) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es.
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa.
	 * M�todo que obt�m o valor total das presta��es referentes a guia e a presta��o informada.
	 * 
	 * @author Anderson Italo
	 * @date 09/07/2012
	 */
	public BigDecimal pesquisarValorTotalGuiaPagamentoPrestacaoHistorico(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es.
	 * [SB0019] � Gerar Remunera��o Cobran�a Administrativa.
	 * Obtemm guia de pagamento presta��o associada ao im�vel e ao comando de a��o de cobran�a
	 * informado
	 * 
	 * @author Anderson Italo
	 * @date 09/07/2012
	 */
	public Collection<GuiaPagamentoPrestacao> pesquisarGuiaPagamentoPrestacaoCobrancaAdministrativa(Integer idImovel,
					Integer idCobrancaAcaoAtividadeComando) throws ErroRepositorioException;

	/**
	 * [UC0238] Manter Aviso Banc�rio
	 * 
	 * @author Hugo Lima
	 * @date 30/08/2012
	 * @param idAvisoBancario
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Date pesquisarDataPagamentoAvisoBancario(Integer idAvisoBancario) throws ErroRepositorioException;

	/**
	 * [UC0238] Manter Aviso Banc�rio
	 * 
	 * @author Hugo Lima
	 * @date 30/08/2012
	 * @param idAvisoBancario
	 * @param indicadorCreditoDebito
	 * @param indicadorArrecadacaoDevolucao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public BigDecimal obterValorTotalAcertosAvisoBancario(Integer idAvisoBancario, Short indicadorCreditoDebito,
					Short indicadorArrecadacaoDevolucao) throws ErroRepositorioException;

	/**
	 * Pesquiar Par�metros de Acrescimos
	 * 
	 * @author Hebert Falc�o
	 * @date 07/09/2012
	 */
	public ParametroAcrescimosEmissaoDocumento pesquisarParametroAcrescimosEmissaoDocumento(Date dataEmissao)
					throws ErroRepositorioException;

	/**
	 * Obter Valor Total das Presta��es da Guia em Hist�rico
	 * 
	 * @author Hebert Falc�o
	 * @date 21/09/2012
	 */
	public BigDecimal obterValorTotalDasPrestacoesDaGuiaEmHistorico(Integer idGuiaPagamento) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por atualizar o indicador de pagamento de uma conta
	 * 
	 * @param idConta
	 * @param indicadorPagamento
	 * @throws ErroRepositorioException
	 */
	void atualizarIndicadorPagamentoConta(Integer idConta, Short indicadorPagamento) throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por atualizar o indicador de pagamento de uma guia de pagamento
	 * 
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param indicadorPagamento
	 * @throws ErroRepositorioException
	 */
	void atualizarIndicadorGuiaPagamentoPrestacao(Integer idGuiaPagamento, Short numeroPrestacao, Short indicadorPagamento)
					throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por atualizar o indicador de pagamento de uma guia de pagamento que est�
	 * no historico
	 * 
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @param indicadorPagamento
	 * @throws ErroRepositorioException
	 */
	void atualizarIndicadorGuiaPagamentoPrestacaoHistorico(Integer idGuiaPagamento, Short numeroPrestacao, Short indicadorPagamento)
					throws ErroRepositorioException;

	/**
	 * Retornar o somat�rio dos valores pagos da conta
	 * 
	 * @author Hebert Falc�o
	 * @date 16/10/2012
	 */
	public BigDecimal retornarSomatorioPagamentoDaConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Retornar o somat�rio dos valores pagos da conta
	 * 
	 * @author Hebert Falc�o
	 * @date 16/10/2012
	 */
	public BigDecimal retornarSomatorioPagamentoHistoricoDaConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * Retornar o somat�rio dos valores pagos da guia
	 * 
	 * @author Hebert Falc�o
	 * @date 16/10/2012
	 */
	public BigDecimal retornarSomatorioPagamentoDaGuia(Integer idGuiaPagamento, Integer numeroPrestacao) throws ErroRepositorioException;

	/**
	 * Retornar o somat�rio dos valores pagos da guia
	 * 
	 * @author Hebert Falc�o
	 * @date 16/10/2012
	 */
	public BigDecimal retornarSomatorioPagamentoHistoricoDaGuia(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC0247] Consultar Pagamentos
	 * Retornar Valor de Remunera��o da Conta Hist�rico
	 * 
	 * @author Hebert Falc�o
	 * @date 11/10/2012
	 */
	public BigDecimal retornarValorRemuneracaoContaHistorico(Integer idConta, Integer referencia) throws ErroRepositorioException;

	/**
	 * [UC0247] Consultar Pagamentos
	 * Retornar Valor de Remunera��o da Conta
	 * 
	 * @author Hebert Falc�o
	 * @date 11/10/2012
	 */
	public BigDecimal retornarValorRemuneracaoConta(Integer idConta, Integer referencia) throws ErroRepositorioException;

	/**
	 * M�todo pesquisarIdsLocalidadeComPagamentosOuPagamentosHistoricos
	 * <p>
	 * Esse m�todo implementa pesquisa dos Ids das Localidade Com {@link Pagamento} Ou
	 * {@link PagamentoHistorico}
	 * </p>
	 * RASTREIO: [OC920431][UC0301]
	 * 
	 * @return Lista de {@link Integer}.
	 * @author Marlos Ribeiro
	 * @param referenciaArrecadacao
	 * @since 14/11/2012
	 */
	public Collection<Integer> pesquisarIdsLocalidadeComPagamentosOuPagamentosHistoricos(Integer referenciaArrecadacao)
					throws ErroRepositorioException;

	public Collection<AjusteContabilidade> pesquisarPagamentoHistoricoParaAjusteContabilidadeArrecadacao(Integer limite)
					throws ErroRepositorioException;

	public Collection<Integer> pesquisarClassificarPagamentosAjuste() throws ErroRepositorioException;

	public void removerGuiaPagamentoCategoriaHistorico(Integer idGuiaPagamento, Short numeroPrestacao) throws ErroRepositorioException;

	public Collection<Object[]> pesquisarGuiaPagamentoPrestacaoComCategoriaInvalida() throws ErroRepositorioException;

	public Collection<Object[]> pesquisarGuiaPagamentoPrestacaoHistoricoComCategoriaInvalida() throws ErroRepositorioException;

	public void removerContaCategoria(Integer idConta) throws ErroRepositorioException;

	public void removerContaCategoriaHistorico(Integer idConta) throws ErroRepositorioException;

	public Collection<Integer> pesquisarContaComCategoriaInvalida() throws ErroRepositorioException;

	public Collection<Integer> pesquisarContaHistoricoComCategoriaInvalida() throws ErroRepositorioException;

	public Integer obterNextValContaCategoriaConsumoFaixaHistorico() throws ErroRepositorioException;

	public void atualizarProvisaoDevedoresDuvidosos(Integer idMotivoBaixa, Date dataBaixa, Integer amReferenciaBaixa,
					Date dataInicialConta, Date dataFinalConta, String[] idSituacaoAtualConta) throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados
	 * 2.6. Situa��o do Pagamento
	 * 
	 * @author Josenildo Neves
	 * @since 29/11/2012
	 * @return listaPagamentoSituacao
	 * @throws ControladorException
	 */
	public Collection<PagamentoSituacao> pesquisarPagamentoSituacao() throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * 3. O sistema seleciona lista pagamentos conforme os crit�rios informados, verificando os
	 * seguintes itens:
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public Collection<Pagamento> pesquisarPagamentos(ClassificarLotePagamentosNaoClassificadosHelper helper)
					throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * [SB0001] � Selecionar Conta pelo Im�vel e Ano/M�s de Refer�ncia
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public Conta selecionarContaPorImovelAnoMesReferenciaPagamento(Integer idImovel, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * [SB0002] � Verificar Pagamento de Conta
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public Collection<Pagamento> selecionarPagamentoPorConta(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * [SB0001] � Selecionar Conta pelo Im�vel e Ano/M�s de Refer�ncia
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public ContaHistorico selecionarContaHistoricoPorImovelAnoMesReferenciaPagamento(Integer idImovel, Integer anoMesReferenciaPagamento)
					throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * 3.2.1.2. Seleciona a guia de pagamento correspondente (a partir da tabela GUIA_PAGAMENTO com
	 * GPAG_ID do pagamento e da tabela GUIA_PAGAMENTO_PRESTACAO com GPAG_ID do pagamento e
	 * GPPR_NNPRESTACAO=PGMT_NNPRESTACAO).
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public GuiaPagamentoPrestacao selecionarGuiaPagamentoPorGuiaPagamentoNumeroPrestacao(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * Seleciona Pagamentos da guia de pagamento.
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public Collection<Pagamento> selecionarPagamentoPorGuiaPagamento(Integer idGuiaPagamento, Integer numeroPrestacao)
					throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * [SB0003] � Selecionar Guia de Pagamento pela Localidade, Im�vel, Cliente e Tipo do D�bito
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public List<GuiaPagamentoPrestacao> selecionarGuiaPagamentoPorLocalidadeImovelClienteTipoDebito(Integer idLocalidade, Integer idImovel,
					Integer idCliente, Integer idTipoDepito) throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * Seleciona Pagamentos do debito a cobrar.
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public Collection<Pagamento> selecionarPagamentoPorDebitoACobrar(Integer idDebitoACobrar) throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * [SB0005 � Selecionar D�bito a Cobrar pela Localidade, Im�vel, e Tipo do D�bito]
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 */
	public DebitoACobrar selecionarDebitoACobrarPorImovelTipoDebito(Integer idImovel, Integer idTipoDepito) throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * Retorna a quantidade de pagamentos a serem processados.
	 * 
	 * @author Josenildo Neves
	 * @date 14/11/2012
	 */
	public Integer pesquisarQuantidadePagamentos(ClassificarLotePagamentosNaoClassificadosHelper helper) throws ErroRepositorioException;

	/**
	 * @param idGuiaPagamento
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<PagamentoHistorico> selecionarPagamentoHistoricoPorGuiaPagamentoHistorico(Integer idGuiaPagamento)
					throws ErroRepositorioException;

	/**
	 * @param idConta
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<PagamentoHistorico> selecionarPagamentoHistoricoPorContaHistorico(Integer idConta) throws ErroRepositorioException;

	/**
	 * [UC0266] Manter Pagamentos.
	 * 1.2.2. Caso a guia n�o seja NULL, pesquisar em GUIA_PAGAMENTO_PRESTACAO pelo Id da guia e
	 * N�mero da presta��o do pagamento:
	 * 
	 * @author Josenildo Neves
	 * @date 21/03/2013
	 */
	public BigDecimal selecionarGuiaPagamentoPrestacaoPorGuiaPagamentoENumeroPrestacao(Integer idGuiaPagamento,
					Integer numeroPrestacao) throws ErroRepositorioException;

	/**
	 * Consulta gen�rica.
	 * 
	 * @param contrato
	 * @param parametroConsulta
	 * @param valorConsulta
	 * @param mensagemErro
	 * @param isAlteracao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<ArrecadadorContrato> consultarArrecadadorContratoGenerico(ArrecadadorContrato contrato, String parametroConsulta,
					String valorConsulta, boolean isAlteracao) throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Retornar Percentual de Remunera��o Reincidencia
	 * 
	 * @author Hebert Falc�o
	 * @date 22/05/2013
	 */
	public BigDecimal retornarPercentualRemuneracaoReincidencia(Integer idCobrancaAcaoAtividadeComando, Date dataPagamento)
					throws ErroRepositorioException;

	/**
	 * [UC0300] Classificar Pagamentos e Devolu��es
	 * Retornar N�mero de dias m�nimo determinado para remunera��o dos acr�scimos
	 * 
	 * @author Hebert Falc�o
	 * @date 22/05/2013
	 */
	public Integer retornarNumeroDeDiasMinimoDeterminadoParaRemuneracaoAcrescimos(Integer idCobrancaAcaoAtividadeComando, Date dataPagamento)
					throws ErroRepositorioException;

	/**
	 * @param idGuiaPagamento
	 * @param numeroPrestacao
	 * @return
	 * @throws ErroRepositorioException
	 */

	public Collection<PagamentoHistorico> selecionarPagamentoHistoricoPorGuiaPagamentoHistorico(Integer idGuiaPagamento,
					Integer numeroPrestacao) throws ErroRepositorioException;



	/**
	 * @param idImovel
	 * @param idCobrancaSituacao
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Integer pesquisarImovelCobrancaSituacao(Integer idImovel, Integer idCobrancaSituacao, Integer prazoGeracao)
					throws ErroRepositorioException;


	public Collection<Object[]> pesquisarConcessionariaPorArrecadadorMovimento(Integer idArrecadadorMovimento)
					throws ErroRepositorioException;

	/**
	 * M�todo respons�vel por atualizar o indicador de pagamento de uma conta
	 * 
	 * @author Felipe Rosacruz
	 * @param idimovel
	 * @param anoMesReferenciaConta
	 * @throws ErroRepositorioException
	 */
	void atualizarIndicadorPagamentoConta(Integer idImovel, Integer anoMesReferenciaConta) throws ErroRepositorioException;


	public AcrescimoImpontualidadeDesconto pesquisarAcrescimoImpontualidadeFiltro(FiltroAcrescimoImpontualidadeDesconto filtro)
					throws ErroRepositorioException;

	/**
	 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
	 * Consulta o objeto Pagamento mais recente do banco, para chamada do Atualizar Pagamento
	 * 
	 * @author Luciano Galv�o
	 * @date 11/09/2013
	 */
	public Pagamento consultarPagamentoParaAtualizacao(Integer pagamentoId) throws ErroRepositorioException;

	/**
	 * Comprovantes da Arrecada��o por Recebedor
	 * 
	 * @author Hebert Falc�o
	 * @since 28/09/2013
	 */
	public Integer pesquisarComprovantesDaArrecadacaoPorRecebedorCount(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Comprovantes da Arrecada��o por Recebedor
	 * 
	 * @author Hebert Falc�o
	 * @since 28/09/2013
	 */
	public Collection<Object[]> pesquisarComprovantesDaArrecadacaoPorRecebedorSintetico(Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Comprovantes da Arrecada��o por Recebedor
	 * 
	 * @author Hebert Falc�o
	 * @since 28/09/2013
	 */
	public Collection<Object[]> pesquisarComprovantesDaArrecadacaoPorRecebedorAnalitico(Integer anoMesReferencia)
					throws ErroRepositorioException;

	/**
	 * Situa��o dos avisos banc�rios
	 * 
	 * @author Hebert Falc�o
	 * @since 04/10/2013
	 */
	public int pesquisarSituacaoDosAvisosBancariosCount(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * Situa��o dos avisos banc�rios
	 * 
	 * @author Hebert Falc�o
	 * @since 04/10/2013
	 */
	public Collection<Object[]> pesquisarSituacaoDosAvisosBancarios(Integer anoMesReferencia) throws ErroRepositorioException;

	/**
	 * [UC0265] Inserir Pagamentos
	 * 
	 * @author Anderson Italo
	 * @date 21/10/2013
	 */
	public Pagamento pesquisarPagamentoComSituacaoAtual(Integer idPagamento) throws ErroRepositorioException;

	/**
	 * Pesquisar arrecadador movimento item inv�lido
	 * 
	 * @author Hebert Falc�o
	 * @date 10/12/2014
	 */
	public Collection<ArrecadadorMovimentoItem> pesquisarArrecadadorMovimentoItemCodigoBarrasInvalido(String conteudoRegistro,
					String ocorrenciaImovel, Short indicadorAceitacao) throws ErroRepositorioException;

	/**
	 * [OC1196098] - Quadro Comparativo de Faturamento e Arrecada��o
	 * 
	 * @author Ado Rocha
	 * @date 26/12/2013
	 * @throws ControladorException
	 */
	public void gerarQuadroComparativoFaturamentoArrecadacao(String dateInicioMes, String dateFimMes, Integer anoMesInicio,
					Integer anoMesFim) throws ErroRepositorioException;

	/**
	 * @author Victon Santos
	 * @date 02/01/14
	 */
	public Collection filtrarItemMovimentoArrecadadorAjustado(Integer movimentoArrecadadorItem) throws ErroRepositorioException;
	/**
	 * Retorna dados da cobran�a administrativa
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @throws ErroRepositorioException
	 * @author Felipe Rosacruz
	 * @date 28/12/2013
	 */
	public Collection<Object[]> gerarDadosRemuneracaoCobrancaAdministrativaModelo1() throws ErroRepositorioException;

	/**
	 * [UC0276] Encerrar Arrecada��o do M�s
	 * 
	 * @throws ErroRepositorioException
	 * @author Felipe Rosacruz
	 * @date 28/12/2013
	 */
	public double retornaPercentualRemuneracaoPorEmpresa(Integer idEmpresa, double percentualDesempenho) throws ErroRepositorioException;

	/**
	 * Rotina de ajuste - CAERD
	 * 
	 * @return
	 * @throws ErroRepositorioException
	 */
	public Collection<Integer> obterContasParaCancelamento(Integer idClienteResponsavel,
					Collection<IntervaloReferenciaHelper> colecaoReferencias) throws ErroRepositorioException;

	/**
	 * @return
	 * @throws ErroRepositorioException
	 */

	public BigDecimal obterValorTotalContasParaCancelamento(Integer idClienteRelacaoTipo, Integer idClienteResponsavel,
					Collection<IntervaloReferenciaHelper> colecaoReferencias) throws ErroRepositorioException;


}
