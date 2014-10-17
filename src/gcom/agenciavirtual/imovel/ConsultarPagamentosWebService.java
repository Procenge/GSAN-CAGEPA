
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoHistorico;
import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.*;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.FiltroDebitoACobrarHistorico;
import gcom.gui.ActionServletException;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna os pagamentos do imovel
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO] caso cliente não informado</li> ou
 * <li>cliente [OBRIGATORIO] caso matricula não informada</li>
 * <li>periodoPagamentoInicio</li>
 * <li>periodoPagamentoFim</li>
 * <li>idsSituacaoPagamento</li>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarPagamentosWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class ConsultarPagamentosWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		String imovelId = recuperarParametroString("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, false, request);

		String idCliente = recuperarParametroString("cliente", LABEL_CAMPO_CODIGO_DO_CLIENTE, false, false, request);

		String periodoPagamentoInicio = recuperarParametroString("periodoPagamentoInicio", "Período de Pagamento Inicial", false, false,
						request);

		String periodoPagamentoFim = recuperarParametroString("periodoPagamentoFim", "Período de Pagamento Final", false,
						false, request);

		Collection<String> colecaoIdSituacaoPagamento = recuperarParametroColecaoString("idsSituacaoPagamento", "Situação do Pagamento",
						false, false, request);

		Fachada fachada = Fachada.getInstancia();

		boolean parametroInformado = false;

		Imovel imovel = null;
		if(imovelId != null){
			imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(imovelId));
			parametroInformado = true;
			if(imovel == null){
				informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.imovel.inexistente"));
			}
		}
		Cliente clienteConsulta = null;
		if(idCliente != null){
			clienteConsulta = fachada.consultarCliente(Integer.valueOf(idCliente));
			parametroInformado = true;
			if(clienteConsulta == null){
				informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.cliente.inexistente"));
			}
		}
		if(periodoPagamentoInicio != null){
			if(!Util.validaDataLinear(periodoPagamentoInicio)){
				informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.data.inicial.invalida"));
			}

		}
		if(periodoPagamentoFim != null){
			if(!Util.validaDataLinear(periodoPagamentoFim)){
				informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.data.final.invalida"));
			}

		}
		Date dataPagamentoInicial = null;
		Date dataPagamentoFinal = null;
		if(!Util.isVazioOuBranco(periodoPagamentoInicio)){

			dataPagamentoInicial = Util.converteStringParaDate((String) periodoPagamentoInicio, false);

		}
		if(!Util.isVazioOuBranco(periodoPagamentoFim)){
			dataPagamentoFinal = Util.converteStringParaDate((String) periodoPagamentoFim, false);
		}

		if(dataPagamentoInicial != null && dataPagamentoFinal != null){
			if(dataPagamentoInicial.after(dataPagamentoFinal)){
				throw new ActionServletException("atencao.data_final.anterior.data_inicial");
			}
		}


		String[] idsSituacaoPagamento = (String[]) colecaoIdSituacaoPagamento.toArray(new String[colecaoIdSituacaoPagamento.size()]);


		if(!parametroInformado){
			informarStatus(STATUS_TIPO_ALERTA,
							MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.informe_matricula_ou_cliente"));
		}

		String idImovelString = null;
		String idClienteString = null;
		
		if(imovelId != null){
			idImovelString = imovelId.toString();
		}
		if(idCliente != null){
			idClienteString = idCliente.toString();
		}

		Collection colecaoImoveisPagamentos = fachada.pesquisarPagamentoImovel(idImovelString, idClienteString, null, null, null, null,
						null, null, null, null, null, dataPagamentoInicial, dataPagamentoFinal, idsSituacaoPagamento, null, null, null,
						null, null, null, null, null);

		Collection colecaoImoveisPagamentosHistorico = fachada.pesquisarPagamentoHistoricoImovel(idImovelString, idClienteString, null,
						null, null, null, null, null, null, null, null, dataPagamentoInicial, dataPagamentoFinal, idsSituacaoPagamento,
						null, null, null, null, null, null, null, null);

		Collection<Pagamento> colecaoPagamentosImovelConta = new ArrayList();
		Collection<Pagamento> colecaoPagamentosImovelGuiaPagamento = new ArrayList();
		Collection<Pagamento> colecaoPagamentosImovelDebitoACobrar = new ArrayList();

		// define as ordenações para Pagamentos de Conta, Guias e Debitos A Cobrar
		ComparatorChain sortPagamentosConta = new ComparatorChain();
		sortPagamentosConta.addComparator(new BeanComparator("anoMesReferenciaPagamento"), true);
		sortPagamentosConta.addComparator(new BeanComparator("dataPagamento"), true);
		sortPagamentosConta.addComparator(new BeanComparator("localidade.id"));
		sortPagamentosConta.addComparator(new BeanComparator("imovel.id"));

		ComparatorChain sortPagamentosGuiasDebitos = new ComparatorChain();
		sortPagamentosGuiasDebitos.addComparator(new BeanComparator("localidade.id"));
		sortPagamentosGuiasDebitos.addComparator(new BeanComparator("imovel.id"));
		sortPagamentosGuiasDebitos.addComparator(new BeanComparator("dataPagamento"), true);

		// Integer qtdePagContas = 0;
		// Integer qtdePagGuiaPagamento = 0;
		// Integer qtdePagDebitoACobrar = 0;

		// Consultar Pagamentos do Imóvel
		if(colecaoImoveisPagamentos != null && !colecaoImoveisPagamentos.isEmpty()){

			Iterator colecaoPagamentoIterator = colecaoImoveisPagamentos.iterator();

			// Divide os pagamentos do imóvel pelo tipo de pagamento
			while(colecaoPagamentoIterator.hasNext()){
				Pagamento pagamento = ((Pagamento) colecaoPagamentoIterator.next());

				if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA)
								|| pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.FATURA_CLIENTE)){

					// se o pagamento estiver referenciando uma conta que esteja no
					// historico, haverá um erro.
					// TODO Modificar depois. Saulo Lima 23/01/09
					// Pq não usar o instanceof
					try{
						pagamento.getConta().getReferencia();
					}catch(Exception e){

						Integer contaGeralId = fachada.pesquisarIdDaContaGeralNoPagamento(pagamento.getId());

						ContaHistorico contaHistorico = null;
						if(contaGeralId != null){
							FiltroContaGeral filtroContaGeral = new FiltroContaGeral();
							filtroContaGeral.adicionarParametro(new ParametroSimples(FiltroContaGeral.ID, contaGeralId));
							Collection<ContaGeral> colecaoContaGeral = fachada.pesquisar(filtroContaGeral, ContaGeral.class.getName());

							// Pega a ContaGeral
							ContaGeral contaGeral = null;
							if(colecaoContaGeral != null && !colecaoContaGeral.isEmpty()){
								contaGeral = (ContaGeral) Util.retonarObjetoDeColecao(colecaoContaGeral);

								// Verifica se está no Histórico
								if(contaGeral.getIndicadorHistorico() == ConstantesSistema.SIM.shortValue()){
									FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
									filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, contaGeral
													.getId()));

									Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisar(filtroContaHistorico,
													ContaHistorico.class.getName());

									if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
										contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);
									}
								}
							}
						}

						if(contaHistorico == null){

							if(pagamento.getImovel() != null && pagamento.getImovel().getId() != null
											&& pagamento.getAnoMesReferenciaPagamento() != null){

								FiltroContaHistorico filtroContaHistoricoNovo = new FiltroContaHistorico();
								filtroContaHistoricoNovo.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ANO_MES_REFERENCIA,
												pagamento.getAnoMesReferenciaPagamento()));
								filtroContaHistoricoNovo.adicionarParametro(new ParametroSimples(FiltroContaHistorico.IMOVEL_ID, pagamento
												.getImovel().getId()));

								Collection<ContaHistorico> colecaoContaHistorico = fachada.pesquisar(filtroContaHistoricoNovo,
												ContaHistorico.class.getName());

								if(colecaoContaHistorico != null && !colecaoContaHistorico.isEmpty()){
									contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);
								}
							}
						}

						if(contaHistorico != null){

							// Setta os valores
							Conta conta = new Conta();
							conta.setId(contaGeralId);
							conta.setReferencia(contaHistorico.getAnoMesReferenciaConta());
							conta.setDebitos(contaHistorico.getValorDebitos());
							conta.setValorAgua(contaHistorico.getValorAgua());
							conta.setValorEsgoto(contaHistorico.getValorEsgoto());
							conta.setValorCreditos(contaHistorico.getValorCreditos());
							conta.setValorImposto(contaHistorico.getValorImposto());

							// Adiciona a "nova" conta ao pagamento
							pagamento.setConta(conta);
						}
					}
					colecaoPagamentosImovelConta.add(pagamento);

				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){

					// Se DebitoACobrar não vier com imovel todo preenchido pesquisar da
					// base.
					// TODO Modificar depois. Saulo Lima 28/09/09
					if(pagamento.getDebitoACobrar() != null){
						try{
							pagamento.getDebitoACobrar().getImovel().getId();
						}catch(Exception e){
							Integer idDebitoACobrar = pagamento.getDebitoACobrar().getId();

							FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
							filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ID,
											idDebitoACobrar));
							filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.IMOVEL);
							filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.DEBITO_TIPO);
							Collection<DebitoACobrarHistorico> colecaoDebitoACobrarHistorico = fachada.pesquisar(
											filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class.getName());

							if(colecaoDebitoACobrarHistorico != null && !colecaoDebitoACobrarHistorico.isEmpty()){
								DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util
												.retonarObjetoDeColecao(colecaoDebitoACobrarHistorico);
								DebitoACobrar debitoACobrar = new DebitoACobrar();
								debitoACobrar.setId(debitoACobrarHistorico.getId());
								debitoACobrar.setImovel(debitoACobrarHistorico.getImovel());
								debitoACobrar.setDebitoTipo(debitoACobrarHistorico.getDebitoTipo());
								pagamento.setDebitoACobrar(debitoACobrar);
							}
						}
					}

					colecaoPagamentosImovelDebitoACobrar.add(pagamento);
				}else if(pagamento.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){
					colecaoPagamentosImovelGuiaPagamento.add(pagamento);
				}
			}

			// Organizar a coleção de Conta
			if(colecaoPagamentosImovelConta != null && !colecaoPagamentosImovelConta.isEmpty()){

				Collections.sort((List) colecaoPagamentosImovelConta, sortPagamentosConta);
				Collection<ConsultarPagamentoJSONHelper> colecaoConsultarPagamentoJSONHelpers = new ArrayList<ConsultarPagamentoJSONHelper>();
				for(Object object : colecaoPagamentosImovelConta){
					colecaoConsultarPagamentoJSONHelpers.add(preencherConsultarPagamentoJSONHelperConta(object));
				}
				adicionarListaAoBody("colecaoPagamentosImovelConta", colecaoConsultarPagamentoJSONHelpers);
				// qtdePagContas = colecaoPagamentosImovelConta.size();

			}

			// Organizar a coleção de Guia de Pagamento
			if(colecaoPagamentosImovelGuiaPagamento != null && !colecaoPagamentosImovelGuiaPagamento.isEmpty()){

				Collections.sort((List) colecaoPagamentosImovelGuiaPagamento, sortPagamentosGuiasDebitos);
				Collection<ConsultarPagamentoJSONHelper> colecaoConsultarPagamentoJSONHelpers = new ArrayList<ConsultarPagamentoJSONHelper>();
				for(Object object : colecaoPagamentosImovelGuiaPagamento){
					colecaoConsultarPagamentoJSONHelpers.add(preencherConsultarPagamentoJSONHelperGuiaPagamento(object));
				}
				adicionarListaAoBody("colecaoPagamentosImovelGuiaPagamento", colecaoConsultarPagamentoJSONHelpers);
				// qtdePagGuiaPagamento = colecaoPagamentosImovelGuiaPagamento.size();

			}

			// Organizar a coleção de Guia de Pagamento
			if(colecaoPagamentosImovelDebitoACobrar != null && !colecaoPagamentosImovelDebitoACobrar.isEmpty()){

				// Organizar a coleção
				Collections.sort((List) colecaoPagamentosImovelDebitoACobrar, sortPagamentosGuiasDebitos);
				Collection<ConsultarPagamentoJSONHelper> colecaoConsultarPagamentoJSONHelpers = new ArrayList<ConsultarPagamentoJSONHelper>();
				for(Object object : colecaoPagamentosImovelDebitoACobrar){
					colecaoConsultarPagamentoJSONHelpers.add(preencherConsultarPagamentoJSONHelperDebitoCobrar(object));
				}
				adicionarListaAoBody("colecaoPagamentosImovelDebitoACobrar", colecaoConsultarPagamentoJSONHelpers);
				// qtdePagDebitoACobrar = colecaoPagamentosImovelDebitoACobrar.size();

			}

		}

		// Imóvel - Pagamento Historico
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelConta = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelGuiaPagamento = new ArrayList();
		Collection<PagamentoHistorico> colecaoPagamentosHistoricoImovelDebitoACobrar = new ArrayList();

		// Consultar Pagamentos do Imóvel
		if(colecaoImoveisPagamentosHistorico != null && !colecaoImoveisPagamentosHistorico.isEmpty()){

			Iterator colecaoPagamentoHistoricoIterator = colecaoImoveisPagamentosHistorico.iterator();

			// Divide os pagamentos do imóvel pelo tipo de pagamento
			while(colecaoPagamentoHistoricoIterator.hasNext()){
				PagamentoHistorico pagamentoHistorico = ((PagamentoHistorico) colecaoPagamentoHistoricoIterator.next());

				if(pagamentoHistorico.getAvisoBancario() == null){
					AvisoBancario avisoBancario = new AvisoBancario();
					Arrecadador arrecadador = new Arrecadador();
					Cliente cliente = new Cliente();

					String nomeCliente = fachada.pesquisarNomeAgenteArrecadador(pagamentoHistorico.getId());

					if(nomeCliente != null){

						cliente.setNome(nomeCliente);
						arrecadador.setCliente(cliente);
						avisoBancario.setArrecadador(arrecadador);
						pagamentoHistorico.setAvisoBancario(avisoBancario);
					}
				}

				if((pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.CONTA) || pagamentoHistorico.getDocumentoTipo()
								.getId().equals(DocumentoTipo.FATURA_CLIENTE))){

					colecaoPagamentosHistoricoImovelConta.add(pagamentoHistorico);

				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.DEBITO_A_COBRAR)){

					// Se DebitoACobrar não vier com imovel todo preenchido pesquisar da
					// base.
					if(pagamentoHistorico.getDebitoACobrar() != null){
						try{
							pagamentoHistorico.getDebitoACobrar().getImovel().getId();
						}catch(Exception e){
							Integer idDebitoACobrar = pagamentoHistorico.getDebitoACobrar().getId();

							FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
							filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ID,
											idDebitoACobrar));
							filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.IMOVEL);
							filtroDebitoACobrarHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrarHistorico.DEBITO_TIPO);
							Collection<DebitoACobrarHistorico> colecaoDebitoACobrarHistorico = fachada.pesquisar(
											filtroDebitoACobrarHistorico, DebitoACobrarHistorico.class.getName());

							if(colecaoDebitoACobrarHistorico != null && !colecaoDebitoACobrarHistorico.isEmpty()){
								DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util
												.retonarObjetoDeColecao(colecaoDebitoACobrarHistorico);
								DebitoACobrar debitoACobrar = new DebitoACobrar();
								debitoACobrar.setId(debitoACobrarHistorico.getId());
								debitoACobrar.setImovel(debitoACobrarHistorico.getImovel());
								debitoACobrar.setDebitoTipo(debitoACobrarHistorico.getDebitoTipo());
								pagamentoHistorico.setDebitoACobrar(debitoACobrar);
							}
						}
					}
					colecaoPagamentosHistoricoImovelDebitoACobrar.add(pagamentoHistorico);

				}else if(pagamentoHistorico.getDocumentoTipo().getId().equals(DocumentoTipo.GUIA_PAGAMENTO)){

					// Se GuiaPagamento não vier com guiaPagamento toda preenchida
					// pesquisar da base.
					if(pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico() != null){
						try{
							pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getValorDebito();
						}catch(Exception e){
							Integer idGuiaPagamento = pagamentoHistorico.getGuiaPagamentoGeral().getGuiaPagamentoHistorico().getId();

							FiltroGuiaPagamentoHistorico filtroGuiaPagamentoHistorico = new FiltroGuiaPagamentoHistorico();
							filtroGuiaPagamentoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoHistorico.ID,
											idGuiaPagamento));
							Collection<GuiaPagamentoHistorico> colecaoGuiaPagamentoHistorico = fachada.pesquisar(
											filtroGuiaPagamentoHistorico, GuiaPagamentoHistorico.class.getName());

							if(colecaoGuiaPagamentoHistorico != null && !colecaoGuiaPagamentoHistorico.isEmpty()){
								GuiaPagamentoHistorico guiaPagamentoHistorico = (GuiaPagamentoHistorico) Util
												.retonarObjetoDeColecao(colecaoGuiaPagamentoHistorico);

								pagamentoHistorico.getGuiaPagamentoGeral().setGuiaPagamentoHistorico(guiaPagamentoHistorico);
							}
						}
					}
					colecaoPagamentosHistoricoImovelGuiaPagamento.add(pagamentoHistorico);
				}
			}

			// Organizar a coleção de Conta
			if(colecaoPagamentosHistoricoImovelConta != null && !colecaoPagamentosHistoricoImovelConta.isEmpty()){

				Collections.sort((List) colecaoPagamentosHistoricoImovelConta, sortPagamentosConta);
				Collection<ConsultarPagamentoJSONHelper> colecaoConsultarPagamentoJSONHelpers = new ArrayList<ConsultarPagamentoJSONHelper>();
				for(Object object : colecaoPagamentosHistoricoImovelConta){
					colecaoConsultarPagamentoJSONHelpers.add(preencherConsultarPagamentoJSONHelperConta(object));
				}
				adicionarListaAoBody("colecaoPagamentosHistoricoImovelConta", colecaoConsultarPagamentoJSONHelpers);
				// qtdePagContas = qtdePagContas + colecaoPagamentosHistoricoImovelConta.size();

			}

			// Organizar a coleção de Guia de Pagamento
			if(colecaoPagamentosHistoricoImovelGuiaPagamento != null && !colecaoPagamentosHistoricoImovelGuiaPagamento.isEmpty()){

				Collections.sort((List) colecaoPagamentosHistoricoImovelGuiaPagamento, sortPagamentosGuiasDebitos);
				Collection<ConsultarPagamentoJSONHelper> colecaoConsultarPagamentoJSONHelpers = new ArrayList<ConsultarPagamentoJSONHelper>();
				for(Object object : colecaoPagamentosHistoricoImovelGuiaPagamento){
					colecaoConsultarPagamentoJSONHelpers.add(preencherConsultarPagamentoJSONHelperGuiaPagamento(object));
				}
				adicionarListaAoBody("colecaoPagamentosHistoricoImovelGuiaPagamento", colecaoConsultarPagamentoJSONHelpers);
				// qtdePagGuiaPagamento = qtdePagGuiaPagamento +
				// colecaoPagamentosHistoricoImovelGuiaPagamento.size();

			}

			// Organizar a coleção de Débito a Cobrar
			if(colecaoPagamentosHistoricoImovelDebitoACobrar != null && !colecaoPagamentosHistoricoImovelDebitoACobrar.isEmpty()){

				Collections.sort((List) colecaoPagamentosHistoricoImovelDebitoACobrar, sortPagamentosGuiasDebitos);
				Collection<ConsultarPagamentoJSONHelper> colecaoConsultarPagamentoJSONHelpers = new ArrayList<ConsultarPagamentoJSONHelper>();
				for(Object object : colecaoPagamentosHistoricoImovelDebitoACobrar){
					colecaoConsultarPagamentoJSONHelpers.add(preencherConsultarPagamentoJSONHelperDebitoCobrar(object));
				}
				adicionarListaAoBody("colecaoPagamentosHistoricoImovelDebitoACobrar", colecaoConsultarPagamentoJSONHelpers);
				// qtdePagDebitoACobrar = qtdePagDebitoACobrar +
				// colecaoPagamentosHistoricoImovelDebitoACobrar.size();

			}
		}
		
		// sessao.setAttribute("qtdePagContas", qtdePagContas);
		// sessao.setAttribute("qtdePagGuiaPagamento", qtdePagGuiaPagamento);
		// sessao.setAttribute("qtdePagDebitoACobrar", qtdePagDebitoACobrar);

		// // O sistema obtém os dados da campanha de premiação mais recente da empresa
		// FiltroCampanha filtroCampanha = new FiltroCampanha(FiltroCampanha.ULTIMA_ALTERACAO +
		// " desc");
		//
		// Collection colecaoCampanha = fachada.pesquisar(filtroCampanha, Campanha.class.getName(),
		// Campanha.class.getSimpleName());
		//
		// if(!colecaoCampanha.isEmpty()){
		// Campanha campanha = (Campanha) Util.retonarObjetoDeColecao(colecaoCampanha);
		//
		// adicionarValorPrimitivoAoBody("idCampanha", campanha.getId());
		// adicionarValorPrimitivoAoBody("nomeCampanha", campanha.getDsTituloCampanha());
		// adicionarValorPrimitivoAoBody("regulamentoCamanha",
		// IoUtil.lerConteudoCampoBlobTipoTxt(campanha.getRegulamentoCampanha())
		// .toString());
		//
		// // Total de Inscrições Registradas, Total de Inscrições Bloqueadas
		// FiltroCampanhaCadastro filtroCampanhaCadastro = new FiltroCampanhaCadastro();
		// filtroCampanhaCadastro.adicionarParametro(new
		// ParametroSimples(FiltroCampanhaCadastro.CAMPANHA_ID, campanha.getId()));
		//
		// int qtInscricoesRegistradas = fachada.totalRegistrosPesquisa(filtroCampanhaCadastro,
		// CampanhaCadastro.class.getName());
		// adicionarValorPrimitivoAoBody("inscricoesRegistradas", qtInscricoesRegistradas);
		//
		// // Total de Inscrições Bloqueadas
		// filtroCampanhaCadastro.adicionarParametro(new
		// ParametroSimples(FiltroCampanhaCadastro.INDICADOR_COMPROVANTE_BLOQUEADO,
		// ConstantesSistema.SIM));
		// int qtInscricoesBloqueadas = fachada.totalRegistrosPesquisa(filtroCampanhaCadastro,
		// CampanhaCadastro.class.getName());
		// adicionarValorPrimitivoAoBody("inscricoesBloqueadas", qtInscricoesBloqueadas);
		//
		// }else{
		//
		// informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
		// "atencao.pesquisa.nenhumresultado"));
		// }

	}

	private ConsultarPagamentoJSONHelper preencherConsultarPagamentoJSONHelper(Object object){

		ConsultarPagamentoJSONHelper consultarPagamentoJSONHelper = new ConsultarPagamentoJSONHelper();

		if(object instanceof Pagamento){
			Pagamento pagamento = (Pagamento) object;

			consultarPagamentoJSONHelper.setValorPagamento(pagamento.getValorPagamento().toString());
			consultarPagamentoJSONHelper.setDatapagamento(Util.formatarData(pagamento.getDataPagamento()));
			if(pagamento.getAvisoBancario() != null && pagamento.getAvisoBancario().getArrecadador() != null
							&& pagamento.getAvisoBancario().getArrecadador().getCliente() != null){
			consultarPagamentoJSONHelper.setArrecadador(pagamento.getAvisoBancario().getArrecadador().getCliente().getNome());
			}
			if(pagamento.getPagamentoSituacaoAnterior() != null){
			consultarPagamentoJSONHelper.setSituacaoAnterior(pagamento.getPagamentoSituacaoAnterior().getDescricao());
			}
			if(pagamento.getPagamentoSituacaoAtual() != null){
			consultarPagamentoJSONHelper.setSituacaoAtual(pagamento.getPagamentoSituacaoAtual().getDescricao());
			}

		}else if(object instanceof PagamentoHistorico){
			PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) object;

			consultarPagamentoJSONHelper.setValorPagamento(pagamentoHistorico.getValorPagamento().toString());
			consultarPagamentoJSONHelper.setDatapagamento(Util.formatarData(pagamentoHistorico.getDataPagamento()));
			if(pagamentoHistorico.getAvisoBancario() != null && pagamentoHistorico.getAvisoBancario().getArrecadador() != null
							&& pagamentoHistorico.getAvisoBancario().getArrecadador().getCliente() != null){
				consultarPagamentoJSONHelper.setArrecadador(pagamentoHistorico.getAvisoBancario().getArrecadador().getCliente().getNome());
			}
			if(pagamentoHistorico.getPagamentoSituacaoAnterior() != null){
				consultarPagamentoJSONHelper.setSituacaoAnterior(pagamentoHistorico.getPagamentoSituacaoAnterior().getDescricao());
			}
			if(pagamentoHistorico.getPagamentoSituacaoAtual() != null){
				consultarPagamentoJSONHelper.setSituacaoAtual(pagamentoHistorico.getPagamentoSituacaoAtual().getDescricao());
			}
		}

		return consultarPagamentoJSONHelper;
	}

	private ConsultarPagamentoJSONHelper preencherConsultarPagamentoJSONHelperConta(Object object){

		ConsultarPagamentoJSONHelper consultarPagamentoJSONHelper = preencherConsultarPagamentoJSONHelper(object);

		if(object instanceof Pagamento){
			Pagamento pagamento = (Pagamento) object;

			if(pagamento.getConta() != null){
				consultarPagamentoJSONHelper.setMesAnoConta(pagamento.getConta().getFormatarAnoMesParaMesAno());
				consultarPagamentoJSONHelper.setValorConta(pagamento.getConta().getValorTotal().toString());
			}

		}else if(object instanceof PagamentoHistorico){
			PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) object;

			if(pagamentoHistorico.getConta() != null){
				consultarPagamentoJSONHelper.setMesAnoConta(pagamentoHistorico.getConta().getFormatarAnoMesParaMesAno());
				consultarPagamentoJSONHelper.setValorConta(pagamentoHistorico.getConta().getValorTotal().toString());
			}
		}

		return consultarPagamentoJSONHelper;
	}

	private ConsultarPagamentoJSONHelper preencherConsultarPagamentoJSONHelperGuiaPagamento(Object object){

		ConsultarPagamentoJSONHelper consultarPagamentoJSONHelper = preencherConsultarPagamentoJSONHelper(object);

		if(object instanceof Pagamento){
			Pagamento pagamento = (Pagamento) object;

			if(pagamento.getCliente() != null){
				consultarPagamentoJSONHelper.setCliente(pagamento.getCliente().getId().toString());
			}
			if(pagamento.getGuiaPagamentoGeral() != null){
				consultarPagamentoJSONHelper.setNumeroDocumento(pagamento.getGuiaPagamentoGeral().getId().toString() + "/"
								+ pagamento.getNumeroPrestacao());
			}else{
				consultarPagamentoJSONHelper.setNumeroDocumento(pagamento.getDebitoTipo().getDescricao());
			}

			consultarPagamentoJSONHelper.setValorPrestacao(pagamento.getValorPgt());

		}else if(object instanceof PagamentoHistorico){
			PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) object;

			if(pagamentoHistorico.getCliente() != null){
				consultarPagamentoJSONHelper.setCliente(pagamentoHistorico.getCliente().getId().toString());
			}
			if(pagamentoHistorico.getGuiaPagamentoGeral() != null){
				consultarPagamentoJSONHelper.setNumeroDocumento(pagamentoHistorico.getGuiaPagamentoGeral().getId().toString() + "/"
								+ pagamentoHistorico.getNumeroPrestacao());
			}else{
				consultarPagamentoJSONHelper.setNumeroDocumento(pagamentoHistorico.getDebitoTipo().getDescricao());
			}

			consultarPagamentoJSONHelper.setValorPrestacao(pagamentoHistorico.getValorPgt());
		}

		return consultarPagamentoJSONHelper;
	}

	private ConsultarPagamentoJSONHelper preencherConsultarPagamentoJSONHelperDebitoCobrar(Object object){

		ConsultarPagamentoJSONHelper consultarPagamentoJSONHelper = preencherConsultarPagamentoJSONHelper(object);

		if(object instanceof Pagamento){
			Pagamento pagamento = (Pagamento) object;

			if(pagamento.getDebitoACobrar() != null && pagamento.getDebitoACobrar().getDebitoTipo() != null){
				consultarPagamentoJSONHelper.setTipoDebito(pagamento.getDebitoACobrar().getDebitoTipo().getDescricao());
			}
			if(pagamento.getDebitoACobrar() != null){
			consultarPagamentoJSONHelper.setValorSerCobrado(pagamento.getDebitoACobrar().getValorRestanteCobrado().toString());
			}
			consultarPagamentoJSONHelper.setPrestacao(pagamento.getNumeroPrestacao().toString());

		}else if(object instanceof PagamentoHistorico){
			PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) object;

			if(pagamentoHistorico.getDebitoACobrar() != null && pagamentoHistorico.getDebitoACobrar().getDebitoTipo() != null){
				consultarPagamentoJSONHelper.setTipoDebito(pagamentoHistorico.getDebitoACobrar().getDebitoTipo().getDescricao());
			}
			if(pagamentoHistorico.getDebitoACobrar() != null){
				consultarPagamentoJSONHelper.setValorSerCobrado(pagamentoHistorico.getDebitoACobrar().getValorRestanteCobrado().toString());
			}
			consultarPagamentoJSONHelper.setPrestacao(pagamentoHistorico.getNumeroPrestacao().toString());
		}

		return consultarPagamentoJSONHelper;
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
