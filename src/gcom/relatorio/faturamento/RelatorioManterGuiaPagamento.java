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

package gcom.relatorio.faturamento;

import gcom.arrecadacao.pagamento.*;
import gcom.batch.Relatorio;
import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.bean.FiltroGuiaPagamentoHelper;
import gcom.faturamento.bean.GuiaPagamentoHelper;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.FiltroDebitoCreditoSituacao;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

/**
 * classe responsável por criar o relatório de guia de pagamento manter
 * 
 * @author Hugo Lima
 * @created 22/12/2011
 */
public class RelatorioManterGuiaPagamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	/**
	 * Construtor da classe RelatorioAnaliseFisicoQuimicaAgua
	 */
	public RelatorioManterGuiaPagamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_GUIA_PAGAMENTO_MANTER);
	}

	@Deprecated
	public RelatorioManterGuiaPagamento() {

		super(null, "");
	}

	/**
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 */

	public Object executar() throws TarefaException{

		// Parâmetros da tela de filtro
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		FiltroGuiaPagamentoHelper filtroGuiaPagamentoHelper = (FiltroGuiaPagamentoHelper) getParametro("filtroGuiaPagamentoHelper");
		// limpa a paginacao
		filtroGuiaPagamentoHelper.setNumeroPagina(ConstantesSistema.NUMERO_NAO_INFORMADO);
		RelatorioManterGuiaPagamentoParametrosHelper relatorioManterGuiaPagamentoParametrosHelper = (RelatorioManterGuiaPagamentoParametrosHelper) getParametro("relatorioManterGuiaPagamentoParametrosHelper");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		// Bean do relatório e sub-relatório
		RelatorioManterGuiaPagamentoBean relatorioManterGuiaPagamentoBean = null;
		RelatorioManterGuiaPagamentoPrestacoesBean relatorioManterGuiaPagamentoPrestacoesBean = null;

		// Coleção do sub-relatório de contas
		Collection<RelatorioManterGuiaPagamentoPrestacoesBean> colecaoRelatorioManterGuiaPagamentoPrestacoesBean = null;

		// Pesquisa as guias de pagamento
		Collection<GuiaPagamentoHelper> colecaoGuiasPagamento = fachada.pesquisarRegistrosManterGuiaPagamento(filtroGuiaPagamentoHelper);

		// se a coleção de parâmetros da analise não for vazia
		if(!Util.isVazioOrNulo(colecaoGuiasPagamento)){
			for(GuiaPagamentoHelper guiaPagamentoHelper : colecaoGuiasPagamento){

				Integer idGuiaPagamento = guiaPagamentoHelper.getNumeroGuia();

				// Campos da Guia de pagamento
				String dataInclusao = "";
				String idDocumentoTipo = "";
				String dsDocumentoTipo = "";
				String dsDebitoCreditoGuia = "";
				BigDecimal nuValorTotal = BigDecimal.ZERO;
				BigDecimal nuValorPago = BigDecimal.ZERO;
				BigDecimal nuValorPendente = BigDecimal.ZERO;
				Short nuValorTotalPrestacao = 0;
				Short nuValorPagoPrestacao = 0;
				Short nuValorPendentePrestacao = 0;
				// Campos do Imovel
				String idImovel = "";
				String idIncricaoImovelFormatada = "";
				String dsSituacaoAgua = "";
				String dsSituacaoEsgoto = "";
				String nmClienteUsuario = "";
				String nmClienteResponsavel = "";
				// campos de cliente
				String idCliente = "";
				String nmCliente = "";
				String nuDocumentoIdentificacao = "";
				String dsProfissao = "";
				String dsRamoAtividade = "";

				FiltroGuiaPagamentoGeral filtroGuiaPagamentoGeral = new FiltroGuiaPagamentoGeral();
				filtroGuiaPagamentoGeral.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoGeral.ID, idGuiaPagamento));
				filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO);
				filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO_HISTORICO);
				filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO_DOCUMENTO_TIPO);
				filtroGuiaPagamentoGeral
								.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO_DEBITO_CREDITO_SITUACAO);
				filtroGuiaPagamentoGeral.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoGeral.GUIA_PAGAMENTO_HISTORICO);

				Collection<GuiaPagamentoGeral> colecaoGuiaPagamento = fachada.pesquisar(filtroGuiaPagamentoGeral, GuiaPagamentoGeral.class
								.getName());

				GuiaPagamentoGeral guiaPagamentoGeral = null;
				if(!Util.isVazioOrNulo(colecaoGuiaPagamento)){
					guiaPagamentoGeral = (GuiaPagamentoGeral) Util.retonarObjetoDeColecao(colecaoGuiaPagamento);
				}

				Imovel imovelGuia = null;

				Cliente clienteGuia = null;

				if(guiaPagamentoGeral.getGuiaPagamento() != null){
					if(guiaPagamentoGeral.getGuiaPagamento().getImovel() != null){
						imovelGuia = guiaPagamentoGeral.getGuiaPagamento().getImovel();
					}
					if(guiaPagamentoGeral.getGuiaPagamento().getCliente() != null){
						clienteGuia = guiaPagamentoGeral.getGuiaPagamento().getCliente();
					}
				}else{
					if(guiaPagamentoGeral.getGuiaPagamentoHistorico().getImovel() != null){
						imovelGuia = guiaPagamentoGeral.getGuiaPagamentoHistorico().getImovel();
					}
					if(guiaPagamentoGeral.getGuiaPagamentoHistorico().getCliente() != null){
						clienteGuia = guiaPagamentoGeral.getGuiaPagamentoHistorico().getCliente();
					}
				}

				// Caso a guia esteja associada a um imovel
				if(!Util.isVazioOuBranco(imovelGuia)){

					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelGuia.getId()));

					Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

					imovelGuia = (Imovel) colecaoImovel.iterator().next();

					// Pega os campos do imovel
					idImovel = imovelGuia.getId().toString();
					idIncricaoImovelFormatada = imovelGuia.getInscricaoFormatada();
					dsSituacaoAgua = imovelGuia.getLigacaoAguaSituacao().getDescricao();
					dsSituacaoEsgoto = imovelGuia.getLigacaoEsgotoSituacao().getDescricao();

					FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
					filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, imovelGuia.getId()));
					filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
					filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RELACAO_TIPO);

					Collection<ClienteImovel> clientesImovelEncontrado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class
									.getName());

					// verifica o nome do cliente usuário
					if(!Util.isVazioOrNulo(clientesImovelEncontrado)){

						for(ClienteImovel clienteImovel : clientesImovelEncontrado){

							if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.USUARIO.intValue()
											&& clienteImovel.getDataFimRelacao() == null){
								nmClienteUsuario = clienteImovel.getCliente().getNome();
								break;
							}
						}
					}

					// Pesquisa o relacionamento com um cliente Responsável
					Collection clientesGuiaPagamento = null;

					if(guiaPagamentoGeral.getGuiaPagamento() != null){
						FiltroClienteGuiaPagamento filtroClienteGuiaPagamento = new FiltroClienteGuiaPagamento();
						filtroClienteGuiaPagamento.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteGuiaPagamento.CLIENTE);
						filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(FiltroClienteGuiaPagamento.GUIA_PAGAMENTO_ID,
										idGuiaPagamento));
						filtroClienteGuiaPagamento.adicionarParametro(new ParametroSimples(
										FiltroClienteGuiaPagamento.CLIENTE_RELACAO_TIPO_ID, ClienteRelacaoTipo.RESPONSAVEL));

						clientesGuiaPagamento = fachada.pesquisar(filtroClienteGuiaPagamento, ClienteGuiaPagamento.class.getName());
					}else if(guiaPagamentoGeral.getGuiaPagamentoHistorico() != null){
						// Caso a guia esteja histórico
						FiltroClienteGuiaPagamentoHistorico filtroClienteGuiaPagamentoHistorico = new FiltroClienteGuiaPagamentoHistorico();
						filtroClienteGuiaPagamentoHistorico
										.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteGuiaPagamentoHistorico.CLIENTE);
						filtroClienteGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
										FiltroClienteGuiaPagamentoHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
						filtroClienteGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(
										FiltroClienteGuiaPagamentoHistorico.CLIENTE_RELACAO_TIPO_ID, ClienteRelacaoTipo.RESPONSAVEL));

						clientesGuiaPagamento = fachada.pesquisar(filtroClienteGuiaPagamentoHistorico, ClienteGuiaPagamentoHistorico.class
										.getName());
					}

					// Verifica o nome do cliente responsável
					if(!Util.isVazioOrNulo(clientesGuiaPagamento)){
						Object clienteGuiaPagamento = Util.retonarObjetoDeColecao(clientesGuiaPagamento);

						if(clienteGuiaPagamento instanceof ClienteGuiaPagamento){
							nmClienteResponsavel = ((ClienteGuiaPagamento) clienteGuiaPagamento).getCliente().getNome();
						}else if(clienteGuiaPagamento instanceof ClienteGuiaPagamentoHistorico){
							nmClienteResponsavel = ((ClienteGuiaPagamentoHistorico) clienteGuiaPagamento).getCliente().getNome();
						}
					}
				}

				// Caso a guia esteja associada a um cliente
				if(!Util.isVazioOuBranco(clienteGuia)){

					FiltroCliente filtroCliente = new FiltroCliente();
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteGuia.getId()));

					Collection<Cliente> colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

					clienteGuia = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

					// Pega os campos do cliente
					idCliente = clienteGuia.getId().toString();
					nmCliente = clienteGuia.getNome();

					// verifica se cliente é pessoa física ou jurídica e pega os campos de profissão
					// e ramo de atividade
					if(clienteGuia.getClienteTipo().getIndicadorPessoaFisicaJuridica().shortValue() == ClienteTipo.INDICADOR_PESSOA_FISICA){
						nuDocumentoIdentificacao = clienteGuia.getCpfFormatado();

						FiltroCliente filtroClienteCompleto = new FiltroCliente();
						filtroClienteCompleto.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
						filtroClienteCompleto.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteGuia.getId()));

						Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteCompleto, Cliente.class
										.getName()));

						if(cliente.getProfissao() != null){

							dsProfissao = cliente.getProfissao().getDescricao();
						}
					}else{
						nuDocumentoIdentificacao = clienteGuia.getCnpjFormatado();

						FiltroCliente filtroClienteCompleto = new FiltroCliente();
						filtroClienteCompleto.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.RAMO_ATIVIDADE);
						filtroClienteCompleto.adicionarParametro(new ParametroSimples(FiltroCliente.ID, clienteGuia.getId()));

						Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroClienteCompleto, Cliente.class
										.getName()));

						if(!Util.isVazioOuBranco(cliente.getRamoAtividade())){
							dsRamoAtividade = cliente.getRamoAtividade().getDescricao();
						}
					}
				}

				// Pesquisa as prestações da guia de pagamento
				Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiasPrestacoes = fachada.pesquisarGuiasPagamentoPrestacaoRelatorio(Integer
								.valueOf(idGuiaPagamento));

				// Prepara a coleção com as prestações de cada guia
				if(!Util.isVazioOrNulo(colecaoGuiasPrestacoes)){

					colecaoRelatorioManterGuiaPagamentoPrestacoesBean = new ArrayList<RelatorioManterGuiaPagamentoPrestacoesBean>();

					Short numeroPrestacao = null;
					Short numeroPrestacaoTotal = null;

					Date dataPagamento = null;
					BigDecimal valorPagamento = null;

					String dataPagamentoStr = null;
					String valorPagamentoStr = null;

					FiltroPagamento filtroPagamento = null;
					Collection<Pagamento> colecaoPagamento = null;
					Pagamento pagamento = null;

					FiltroPagamentoHistorico filtroPagamentoHistorico = null;
					Collection<PagamentoHistorico> colecaoPagamentoHistorico = null;
					PagamentoHistorico pagamentoHistorico = null;

					for(GuiaPagamentoPrestacaoHelper guiaPagamentoPrestacaoHelper : colecaoGuiasPrestacoes){
						relatorioManterGuiaPagamentoPrestacoesBean = new RelatorioManterGuiaPagamentoPrestacoesBean();

						numeroPrestacao = guiaPagamentoPrestacaoHelper.getNumeroPrestacao();
						numeroPrestacaoTotal = guiaPagamentoPrestacaoHelper.getNumeroPrestacaoTotal();

						relatorioManterGuiaPagamentoPrestacoesBean.setNuPrestacao(numeroPrestacao + "/" + numeroPrestacaoTotal);

						relatorioManterGuiaPagamentoPrestacoesBean
										.setDsTipoDebito(guiaPagamentoPrestacaoHelper.getDescricaoTipoDebito() != null ? guiaPagamentoPrestacaoHelper
														.getDescricaoTipoDebito()
														: "");
						relatorioManterGuiaPagamentoPrestacoesBean.setNuValorPrestacao(Util.formatarMoedaReal(guiaPagamentoPrestacaoHelper
										.getValorTotalPorPrestacao()));
						relatorioManterGuiaPagamentoPrestacoesBean.setDataEmissao(Util.formatarData(guiaPagamentoPrestacaoHelper
										.getDataEmissao()));
						relatorioManterGuiaPagamentoPrestacoesBean.setDataVencimento(Util.formatarData(guiaPagamentoPrestacaoHelper
										.getDataVencimento()));

						dataPagamento = null;
						valorPagamento = null;

						if(!Util.isVazioOuBranco(guiaPagamentoPrestacaoHelper.getIndicadorPagamento())
										&& guiaPagamentoPrestacaoHelper.getIndicadorPagamento().equals(ConstantesSistema.SIM)){
							relatorioManterGuiaPagamentoPrestacoesBean.setDsSituacaoPagamento("Paga");

							filtroPagamento = new FiltroPagamento();
							filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.GUIA_PAGAMENTO_ID, idGuiaPagamento));
							filtroPagamento.adicionarParametro(new ParametroSimples(FiltroPagamento.NUMERO_PRESTACAO, numeroPrestacao));

							colecaoPagamento = fachada.pesquisar(filtroPagamento, Pagamento.class.getName());

							if(!Util.isVazioOrNulo(colecaoPagamento)){
								pagamento = (Pagamento) Util.retonarObjetoDeColecao(colecaoPagamento);

								dataPagamento = pagamento.getDataPagamento();
								valorPagamento = pagamento.getValorPagamento();
							}else{
								filtroPagamentoHistorico = new FiltroPagamentoHistorico();
								filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(
												FiltroPagamentoHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
								filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.NUMERO_PRESTACAO,
												numeroPrestacao));

								colecaoPagamentoHistorico = fachada.pesquisar(filtroPagamentoHistorico, PagamentoHistorico.class.getName());

								if(!Util.isVazioOrNulo(colecaoPagamentoHistorico)){
									pagamentoHistorico = (PagamentoHistorico) Util.retonarObjetoDeColecao(colecaoPagamentoHistorico);

									dataPagamento = pagamentoHistorico.getDataPagamento();
									valorPagamento = pagamentoHistorico.getValorPagamento();
								}
							}
						}else{
							relatorioManterGuiaPagamentoPrestacoesBean.setDsSituacaoPagamento("Pendente");
						}

						dataPagamentoStr = Util.formatarData(dataPagamento);
						valorPagamentoStr = Util.formatarMoedaReal(valorPagamento);

						relatorioManterGuiaPagamentoPrestacoesBean.setDataPagamento(dataPagamentoStr);
						relatorioManterGuiaPagamentoPrestacoesBean.setNuValorPago(valorPagamentoStr);

						relatorioManterGuiaPagamentoPrestacoesBean.setDsSituacao(guiaPagamentoPrestacaoHelper
										.getDescricaoDebitoCreditoSituacao());
						
						if(guiaPagamentoPrestacaoHelper.getIndicadorExecucaoFiscal().equals(ConstantesSistema.SIM)){
							relatorioManterGuiaPagamentoPrestacoesBean.setStatusDividaAtiva("E");
						}else if(guiaPagamentoPrestacaoHelper.getIndicadorDividaAtiva().equals(ConstantesSistema.SIM)){
							relatorioManterGuiaPagamentoPrestacoesBean.setStatusDividaAtiva("A");
						}else{
							relatorioManterGuiaPagamentoPrestacoesBean.setStatusDividaAtiva("N");
						}

						if(guiaPagamentoPrestacaoHelper.getNumeroProcessoAdministrativoExecucaoFiscal() != null){
							relatorioManterGuiaPagamentoPrestacoesBean
											.setNumeroProcessoAdministrativoExecucaoFiscal(guiaPagamentoPrestacaoHelper
															.getNumeroProcessoAdministrativoExecucaoFiscal().toString());
						}

						colecaoRelatorioManterGuiaPagamentoPrestacoesBean.add(relatorioManterGuiaPagamentoPrestacoesBean);
					}
				}

				// Verifica os campos que dependem ou não de histórico
				if(!Util.isVazioOuBranco(guiaPagamentoGeral.getGuiaPagamento())){
					dataInclusao = Util.formatarData(guiaPagamentoGeral.getGuiaPagamento().getDataInclusao());
					nuValorTotal = guiaPagamentoGeral.getGuiaPagamento().getValorDebito();
					nuValorTotalPrestacao = guiaPagamentoGeral.getGuiaPagamento().getNumeroPrestacaoTotal();
					idDocumentoTipo = guiaPagamentoGeral.getGuiaPagamento().getDocumentoTipo().getId().toString();

					dsDocumentoTipo = guiaPagamentoGeral.getGuiaPagamento().getDocumentoTipo().getDescricaoDocumentoTipo();

					FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao();
					filtroDebitoCreditoSituacao.adicionarParametro(new ParametroSimples(FiltroDebitoCreditoSituacao.ID, Integer
									.valueOf(guiaPagamentoGeral.getGuiaPagamento().getDebitoCreditoSituacaoAtual().getId())));

					DebitoCreditoSituacao debitoCreditoSituacao = (DebitoCreditoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
									filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName()));

					dsDebitoCreditoGuia = debitoCreditoSituacao.getDescricaoAbreviada();
				}else{
					dataInclusao = Util.formatarData(guiaPagamentoGeral.getGuiaPagamentoHistorico().getDataInclusao());
					nuValorTotal = guiaPagamentoGeral.getGuiaPagamentoHistorico().getValorDebito();
					nuValorTotalPrestacao = guiaPagamentoGeral.getGuiaPagamentoHistorico().getNumeroPrestacaoTotal();
					idDocumentoTipo = guiaPagamentoGeral.getGuiaPagamentoHistorico().getDocumentoTipo().getId().toString();

					FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
					filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, Integer.valueOf(idDocumentoTipo)));

					DocumentoTipo documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroDocumentoTipo,
									DocumentoTipo.class.getName()));

					dsDocumentoTipo = documentoTipo.getDescricaoDocumentoTipo();

					FiltroDebitoCreditoSituacao filtroDebitoCreditoSituacao = new FiltroDebitoCreditoSituacao();
					filtroDebitoCreditoSituacao.adicionarParametro(new ParametroSimples(FiltroDebitoCreditoSituacao.ID, Integer
									.valueOf(guiaPagamentoGeral.getGuiaPagamentoHistorico().getDebitoCreditoSituacaoAtual().getId())));

					DebitoCreditoSituacao debitoCreditoSituacao = (DebitoCreditoSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
									filtroDebitoCreditoSituacao, DebitoCreditoSituacao.class.getName()));

					dsDebitoCreditoGuia = debitoCreditoSituacao.getDescricaoAbreviada();
				}

				// Pesquisa valor pago até o momento a partir do historico
				FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
				filtroPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.GUIA_PAGAMENTO_GERAL_ID,
								idGuiaPagamento));
				filtroPagamentoHistorico.setCampoOrderBy(FiltroPagamentoHistorico.NUMERO_PRESTACAO);

				Collection<PagamentoHistorico> colecaoPagamentoHistorico = fachada.pesquisar(filtroPagamentoHistorico,
								PagamentoHistorico.class.getName());

				// Caso a guia contenha pagamentos no histórico soma todos os valors de
				// pagamentos para calcular o total pago
				if(!Util.isVazioOrNulo(colecaoPagamentoHistorico)){
					Short nuPrestacaoAnterior = 0;
					for(PagamentoHistorico pagamentoHistorico : colecaoPagamentoHistorico){
						if(pagamentoHistorico.getValorPagamento() != null){
							nuValorPago = nuValorPago.add(pagamentoHistorico.getValorPagamento());

							// testa o maior valor de prestacao
							if(!Util.isVazioOuBranco(pagamentoHistorico.getNumeroPrestacao())){
								if(pagamentoHistorico.getNumeroPrestacao() > nuPrestacaoAnterior){

									nuValorPagoPrestacao = pagamentoHistorico.getNumeroPrestacao().shortValue();
								}

								nuPrestacaoAnterior = pagamentoHistorico.getNumeroPrestacao().shortValue();
							}
						}
					}
				}

				// Calcula o valor pendente de pagamento
				nuValorPendente = BigDecimal.valueOf(nuValorTotal.doubleValue());
				nuValorPendente = nuValorPendente.subtract(nuValorPago);
				Integer resultadoSomaValorPendentePrestacao = nuValorTotalPrestacao.intValue() - nuValorPagoPrestacao.intValue();
				nuValorPendentePrestacao = resultadoSomaValorPendentePrestacao.shortValue();

				// Cria um novo Bean para o relatório
				relatorioManterGuiaPagamentoBean = new RelatorioManterGuiaPagamentoBean();

				// Seta os campos do relatório atual
				relatorioManterGuiaPagamentoBean.setIdGuiaPagamento(idGuiaPagamento.toString());
				relatorioManterGuiaPagamentoBean.setDataInclusao(dataInclusao);
				relatorioManterGuiaPagamentoBean.setIdDocumentoTipo(idDocumentoTipo);
				relatorioManterGuiaPagamentoBean.setDsDocumentoTipo(dsDocumentoTipo);
				relatorioManterGuiaPagamentoBean.setNuValorTotal(Util.formatarMoedaReal(nuValorTotal));
				relatorioManterGuiaPagamentoBean.setNuValorPago(Util.formatarMoedaReal(nuValorPago));
				relatorioManterGuiaPagamentoBean.setNuValorPendente(Util.formatarMoedaReal(nuValorPendente));
				relatorioManterGuiaPagamentoBean.setNuValorTotalPrestacao(nuValorTotalPrestacao);
				relatorioManterGuiaPagamentoBean.setNuValorPagoPrestacao(nuValorPagoPrestacao);
				relatorioManterGuiaPagamentoBean.setNuValorPendentePrestacao(nuValorPendentePrestacao);
				relatorioManterGuiaPagamentoBean.setDsSituacaoAtual(dsDebitoCreditoGuia);

				// Seta os campos do imóvel no relatório atual
				relatorioManterGuiaPagamentoBean.setIdImovel(idImovel);
				relatorioManterGuiaPagamentoBean.setIdIncricaoImovelFormatada(idIncricaoImovelFormatada);
				relatorioManterGuiaPagamentoBean.setDsSituacaoAgua(dsSituacaoAgua);
				relatorioManterGuiaPagamentoBean.setDsSituacaoEsgoto(dsSituacaoEsgoto);
				relatorioManterGuiaPagamentoBean.setNmClienteUsuario(nmClienteUsuario);
				relatorioManterGuiaPagamentoBean.setNmClienteResponsavel(nmClienteResponsavel);

				// seta os campos do cliente no relatório atual
				relatorioManterGuiaPagamentoBean.setIdCliente(idCliente);
				relatorioManterGuiaPagamentoBean.setNmCliente(nmCliente);
				relatorioManterGuiaPagamentoBean.setNuDocumentoIdentificacao(nuDocumentoIdentificacao);
				relatorioManterGuiaPagamentoBean.setDsProfissao(dsProfissao);
				relatorioManterGuiaPagamentoBean.setDsRamoAtividade(dsRamoAtividade);

				// Seta a colecao de detalhe das prestacoes no relatorio atual
				relatorioManterGuiaPagamentoBean.setColecaoRelGuiPagDetalhePrestacaoBean(colecaoRelatorioManterGuiaPagamentoPrestacoesBean);

				// Adiciona os dados de uma guia na coleção que será utilizada como fonte de dados
				// pelo relatório
				relatorioBeans.add(relatorioManterGuiaPagamentoBean);
			}
		}

		// Seta os parametros utilizados na pesquisa
		Map parametros = new HashMap();

		// Pesquisa parametros do sistema
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		if(relatorioManterGuiaPagamentoParametrosHelper.getCodigoClienteGuia() != null){
			parametros.put("codigoClienteGuia", relatorioManterGuiaPagamentoParametrosHelper.getCodigoClienteGuia());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getNomeClienteGuia() != null){
			parametros.put("nomeClienteGuia", relatorioManterGuiaPagamentoParametrosHelper.getNomeClienteGuia());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getMatriculaImovel() != null){
			parametros.put("matriculaImovel", relatorioManterGuiaPagamentoParametrosHelper.getMatriculaImovel());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getInscricaoImovel() != null){
			parametros.put("inscricaoImovel", relatorioManterGuiaPagamentoParametrosHelper.getInscricaoImovel());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getIdLocalidade() != null){
			parametros.put("idLocalidade", relatorioManterGuiaPagamentoParametrosHelper.getIdLocalidade());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getNomeLocalidade() != null){
			parametros.put("nomeLocalidade", relatorioManterGuiaPagamentoParametrosHelper.getNomeLocalidade());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getCodigoClienteResponsavel() != null){
			parametros.put("codigoClienteResponsavel", relatorioManterGuiaPagamentoParametrosHelper.getCodigoClienteResponsavel());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getNomeClienteResponsavel() != null){
			parametros.put("nomeClienteResponsavel", relatorioManterGuiaPagamentoParametrosHelper.getNomeClienteResponsavel());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getPeriodoReferenciaFaturamento() != null){
			parametros.put("periodoReferenciaFaturamento", relatorioManterGuiaPagamentoParametrosHelper.getPeriodoReferenciaFaturamento());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getPeriodoEmissao() != null){
			parametros.put("periodoEmissao", relatorioManterGuiaPagamentoParametrosHelper.getPeriodoEmissao());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getPeriodoVencimento() != null){
			parametros.put("periodoVencimento", relatorioManterGuiaPagamentoParametrosHelper.getPeriodoVencimento());
		}
		if(relatorioManterGuiaPagamentoParametrosHelper.getIdGuiaPagamento() != null){
			parametros.put("idGuiaPagamento", relatorioManterGuiaPagamentoParametrosHelper.getIdGuiaPagamento());
		}

		Short exibirDividaAtivaColuna = ConstantesSistema.NAO;
		if(fachada.existeProcessoExecucaoFiscal().equals(ConstantesSistema.SIM)){
			exibirDividaAtivaColuna = ConstantesSistema.SIM;
		}
		parametros.put("exibirDividaAtivaColuna", exibirDividaAtivaColuna.toString());

		RelatorioDataSource dataSource = new RelatorioDataSource((List) relatorioBeans);

		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_GUIA_PAGAMENTO_MANTER, parametros, dataSource, tipoFormatoRelatorio);

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_MANTER_GUIA_PAGAMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Collection<GuiaPagamentoHelper> colecao = Fachada.getInstancia().pesquisarRegistrosManterGuiaPagamento(
						(FiltroGuiaPagamentoHelper) getParametro("filtroGuiaPagamentoHelper"));
		int retorno = colecao.size();

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterGuiaPagamento", this);
	}
}
