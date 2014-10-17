
package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.Quadra;
import gcom.cobranca.ResolucaoDiretoria;
import gcom.cobranca.bean.*;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.financeiro.FinanciamentoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.Grupo;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3031] Efetuar Negociação de Débitos
 * 
 * @author Hebert Falcão
 * @date 01/12/2011
 */
public class ExibirEfetuarNegociacaoDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirEfetuarNegociacaoDebitosAction");

		// Form
		EfetuarNegociacaoDebitosActionForm form = (EfetuarNegociacaoDebitosActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		String desfazer = httpServletRequest.getParameter("desfazer");
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(!Util.isVazioOuBranco(desfazer)){
			// Apagar campos do form

			form.setIdImovelAntes("");
			form.setInscricaoImovel("");
			form.setSituacaoAgua("");
			form.setSituacaoEsgoto("");
			form.setNumeroQuadra("");

			form.setEnderecoImovel("");

			form.setValorTotalConta("");
			form.setValorTotalGuia("");
			form.setValorTotalAcrescimo("");
			form.setValorTotalCreditoARealizar("");

			form.setValorTotalMulta("");
			form.setValorTotalJurosMora("");
			form.setValorTotalAtualizacaoMonetaria("");

			form.setValorTotalRestanteServicosACobrar("");
			form.setValorTotalRestanteParcelamentosACobrar("");

			form.setValorDebitoTotalAtualizado("");

			form.setIdClienteUsuario("");
			form.setNomeCliente("");
			form.setEmailCliente("");
			form.setCpfCnpjCliente("");
			form.setCpfCnpjClienteAntes("");
			form.setIndicadorPessoaFisicaJuridica("");

			sessao.removeAttribute("colecaoClientesImovel");
			sessao.removeAttribute("colecaoNegociacaoDebitoOpcoesRDHelper");
			sessao.removeAttribute("negociacaoDebitoImovelHelper");

			// Negociação Selecionada
			form.setNegociacaoIdRD("");
			form.setNegociacaoNumeroRD("");
			form.setNegociacaoValorDebitoTotalAtualizado("");
			form.setNegociacaoPercentualTotalDesconto("");
			form.setNegociacaoValorTotalDesconto("");
			form.setNegociacaoValorDebitoAposDesconto("");
			form.setNegociacaoValorEntrada("");
			form.setNegociacaoValorTotalJurosFinanciamento("");
			form.setNegociacaoQuantidadeParcelas("");
			form.setNegociacaoValorParcela("");
			form.setNegociacaoValorDescontoPagamentoAVista("");
			form.setNegociacaoValorDescontoInatividade("");
			form.setNegociacaoValorDescontoAntiguidade("");
			form.setNegociacaoNumeroMesesEntreParcelas("");
			form.setNegociacaoNumeroParcelasALancar("");
			form.setNegociacaoNumeroMesesInicioCobranca("");
			form.setNegociacaoNumeroDiasVencimentoDaEntrada("");
			form.setNegociacaoTaxaJuros("");
		}else if(!Util.isVazioOuBranco(objetoConsulta)){
			// Lupas
			String idImovelStr = form.getIdImovel();

			if(objetoConsulta.equals("1")){
				// Imóvel

				if(!Util.isVazioOuBranco(idImovelStr)){
					Integer idImovel = Integer.valueOf(idImovelStr.trim());

					String matriculaImovel = "";
					String descricaoLigacaoAguaSituacao = "";
					String descricaoLigacaoEsgotoSituacao = "";
					String numeroQuadraStr = "";

					String enderecoImovel = "";

					Collection<ClienteImovel> colecaoClientesImovel = null;

					String idClienteUsuarioStr = "";
					String nomeCliente = "";
					String emailCliente = "";
					String cpfCnpjCliente = "";
					String cpfCnpjClienteAntes = "";
					String indicadorPessoaFisicaJuridicaStr = "";

					String valorTotalContaStr = "";
					String valorTotalGuiaStr = "";
					String valorTotalAcrescimoStr = "";
					String valorTotalCreditoARealizarStr = "";

					String valorTotalMultaStr = "";
					String valorTotalJurosMoraStr = "";
					String valorTotalAtualizacaoMonetariaStr = "";

					String valorTotalRestanteServicosACobrarStr = "";
					String valorTotalRestanteParcelamentosACobrarStr = "";

					String valorDebitoTotalAtualizadoStr = "";

					Collection<NegociacaoDebitoOpcoesRDHelper> colecaoNegociacaoDebitoOpcoesRDHelper = null;

					NegociacaoDebitoImovelHelper negociacaoDebitoImovelHelper = null;

					FiltroImovel filtroImovel = new FiltroImovel();
					filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
					filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);

					Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

					if(!Util.isVazioOrNulo(colecaoImovel)){
						Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

						// Inscrição
						matriculaImovel = fachada.pesquisarInscricaoImovel(idImovel, true);

						// Situação da Ligação de Água
						Integer idLigacaoAguaSituacao = null;
						LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();

						if(ligacaoAguaSituacao != null){
							idLigacaoAguaSituacao = ligacaoAguaSituacao.getId();
							descricaoLigacaoAguaSituacao = ligacaoAguaSituacao.getDescricao();
						}

						// Situação da Ligação de Esgoto
						Integer idLigacaoEsgotoSituacao = null;
						LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();

						if(ligacaoEsgotoSituacao != null){
							idLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getId();
							descricaoLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getDescricao();
						}

						// Quadra
						Quadra quadra = imovel.getQuadra();

						if(quadra != null){
							int numeroQuadra = quadra.getNumeroQuadra();
							numeroQuadraStr = Integer.toString(numeroQuadra);
						}

						// Perfil do Imóvel
						Integer idPerfilImovel = null;
						ImovelPerfil imovelPerfil = imovel.getImovelPerfil();

						if(imovelPerfil != null){
							idPerfilImovel = imovelPerfil.getId();
						}

						// Endereço do Imóvel
						enderecoImovel = fachada.pesquisarEndereco(idImovel);

						// Lista dos clientes associados ao imóvel
						colecaoClientesImovel = fachada.pesquisarClientesImovel(idImovel);

						if(!Util.isVazioOrNulo(colecaoClientesImovel)){
							Integer idClienteUsuario = null;

							Cliente cliente = null;
							ClienteTipo clienteTipo = null;
							ClienteRelacaoTipo clienteRelacaoTipo = null;

							Integer idClienteRelacaoTipo = null;
							Short indicadorPessoaFisicaJuridica = null;

							// Informações do Cliente Usuário
							for(ClienteImovel clienteImovel : colecaoClientesImovel){
								clienteRelacaoTipo = clienteImovel.getClienteRelacaoTipo();
								idClienteRelacaoTipo = clienteRelacaoTipo.getId();

								if(ClienteRelacaoTipo.USUARIO.equals(idClienteRelacaoTipo)){
									cliente = clienteImovel.getCliente();

									idClienteUsuario = cliente.getId();
									idClienteUsuarioStr = Integer.toString(idClienteUsuario);

									nomeCliente = cliente.getNome();

									emailCliente = cliente.getEmail();

									if(!Util.isVazioOuBranco(emailCliente)){
										emailCliente = emailCliente.trim();
									}else{
										emailCliente = "";
									}

									clienteTipo = cliente.getClienteTipo();

									if(clienteTipo != null){
										indicadorPessoaFisicaJuridica = clienteTipo.getIndicadorPessoaFisicaJuridica();
										indicadorPessoaFisicaJuridicaStr = Short.toString(indicadorPessoaFisicaJuridica);

										if(ClienteTipo.INDICADOR_PESSOA_FISICA.equals(indicadorPessoaFisicaJuridica)){
											cpfCnpjCliente = cliente.getCpfFormatado();
											cpfCnpjClienteAntes = cliente.getCpf();
										}else{
											cpfCnpjCliente = cliente.getCnpjFormatado();
											cpfCnpjClienteAntes = cliente.getCnpj();
										}
									}

									break;
								}
							}
						}

						// Débitos do Imóvel

						int tipoImovel = 1;
						String anoMesInicialReferenciaDebito = "000101";
						String anoMesFinalReferenciaDebito = "999912";
						Date dataInicialVencimentoDebito = Util.converteStringParaDate("01/01/0001");
						Date dataFinalVencimentoDebito = Util.converteStringParaDate("31/12/9999");
						int indicadorPagamento = 2;
						int indicadorConta = 2;
						int indicadorDebito = 1;
						int indicadorCredito = 1;
						int indicadorNotas = 1;
						int indicadorGuias = 1;
						int indicadorAtualizar = 1;

						ObterDebitoImovelOuClienteHelper colecaoDebitoCliente = fachada.obterDebitoImovelOuCliente(tipoImovel, idImovelStr,
										null, null, anoMesInicialReferenciaDebito, anoMesFinalReferenciaDebito,
										dataInicialVencimentoDebito, dataFinalVencimentoDebito, indicadorPagamento, indicadorConta,
										indicadorDebito, indicadorCredito, indicadorNotas, indicadorGuias, indicadorAtualizar, null, null,
										null, null, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);

						Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoCliente.getColecaoContasValores();
						Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoCliente.getColecaoDebitoACobrar();
						Collection<CreditoARealizar> colecaoCreditoARealizar = colecaoDebitoCliente.getColecaoCreditoARealizar();
						Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoCliente
										.getColecaoGuiasPagamentoValores();

						// [FS0004] – Verificar existência de débitos para o imóvel
						if(Util.isVazioOrNulo(colecaoContaValores) && Util.isVazioOrNulo(colecaoDebitoACobrar)
										&& Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){
							throw new ActionServletException("atencao.imovel.informado.sem.debitos");
						}

						// [FS0007] – Verificar existência de juros sobre parcelamento
						if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){
							Iterator<DebitoACobrar> colecaoDebitoACobrarIterator = colecaoDebitoACobrar.iterator();

							DebitoACobrar debitoACobrar = null;
							DebitoTipo debitoTipo = null;

							while(colecaoDebitoACobrarIterator.hasNext()){
								debitoACobrar = colecaoDebitoACobrarIterator.next();

								debitoTipo = debitoACobrar.getDebitoTipo();
								Integer idDebitoTipo = debitoTipo.getId();

								if(DebitoTipo.JUROS_SOBRE_PARCELAMENTO.equals(idDebitoTipo)){
									colecaoDebitoACobrarIterator.remove();
								}
							}
						}

						// Valor total dos acréscimos por impontualidade
						BigDecimal valorTotalAcrescimoImpontualidade = BigDecimal.ZERO;

						BigDecimal valorTotalMulta = BigDecimal.ZERO;
						BigDecimal valorTotalJurosMora = BigDecimal.ZERO;
						BigDecimal valorTotalAtualizacaoMonetaria = BigDecimal.ZERO;

						// Valor total das contas
						BigDecimal valorTotalConta = BigDecimal.ZERO;

						if(!Util.isVazioOrNulo(colecaoContaValores)){
							BigDecimal valorTotalContaAux = BigDecimal.ZERO;
							BigDecimal valorTotalAcrescimoAux = BigDecimal.ZERO;

							BigDecimal valorMulta = BigDecimal.ZERO;
							BigDecimal valorJurosMora = BigDecimal.ZERO;
							BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;

							for(ContaValoresHelper contaValoresHelper : colecaoContaValores){
								valorTotalContaAux = contaValoresHelper.getValorTotalConta();
								valorTotalConta = valorTotalConta.add(valorTotalContaAux);

								valorTotalAcrescimoAux = contaValoresHelper.getValorTotalContaValores();
								valorTotalAcrescimoImpontualidade = valorTotalAcrescimoImpontualidade.add(valorTotalAcrescimoAux);

								valorMulta = contaValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
												Parcelamento.TIPO_ARREDONDAMENTO);
								valorJurosMora = contaValoresHelper.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
												Parcelamento.TIPO_ARREDONDAMENTO);
								valorAtualizacaoMonetaria = contaValoresHelper.getValorAtualizacaoMonetaria().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

								valorTotalMulta = valorTotalMulta.add(valorMulta);
								valorTotalJurosMora = valorTotalJurosMora.add(valorJurosMora);
								valorTotalAtualizacaoMonetaria = valorTotalAtualizacaoMonetaria.add(valorAtualizacaoMonetaria);
							}
						}

						valorTotalContaStr = Util.formatarMoedaReal(valorTotalConta);

						// Valor total das guias de pagamento
						BigDecimal valorTotalGuiaPagamento = BigDecimal.ZERO;

						if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){
							BigDecimal valorTotalPrestacao = BigDecimal.ZERO;
							BigDecimal valorTotalAcrescimoAux = BigDecimal.ZERO;

							BigDecimal valorMulta = BigDecimal.ZERO;
							BigDecimal valorJurosMora = BigDecimal.ZERO;
							BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;

							for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiaPagamentoValores){
								valorTotalPrestacao = guiaPagamentoValoresHelper.getValorTotalPrestacao();
								valorTotalGuiaPagamento = valorTotalGuiaPagamento.add(valorTotalPrestacao);

								valorTotalAcrescimoAux = guiaPagamentoValoresHelper.getValorTotalContaValores();
								valorTotalAcrescimoImpontualidade = valorTotalAcrescimoImpontualidade.add(valorTotalAcrescimoAux);

								valorMulta = guiaPagamentoValoresHelper.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
												Parcelamento.TIPO_ARREDONDAMENTO);
								valorJurosMora = guiaPagamentoValoresHelper.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
												Parcelamento.TIPO_ARREDONDAMENTO);
								valorAtualizacaoMonetaria = guiaPagamentoValoresHelper.getValorAtualizacaoMonetaria().setScale(
												Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

								valorTotalMulta = valorTotalMulta.add(valorMulta);
								valorTotalJurosMora = valorTotalJurosMora.add(valorJurosMora);
								valorTotalAtualizacaoMonetaria = valorTotalAtualizacaoMonetaria.add(valorAtualizacaoMonetaria);
							}
						}

						valorTotalGuiaStr = Util.formatarMoedaReal(valorTotalGuiaPagamento);

						// Valor total dos acréscimos por impontualidade
						valorTotalAcrescimoStr = Util.formatarMoedaReal(valorTotalAcrescimoImpontualidade);

						valorTotalMultaStr = Util.formatarMoedaReal(valorTotalMulta);
						valorTotalJurosMoraStr = Util.formatarMoedaReal(valorTotalJurosMora);
						valorTotalAtualizacaoMonetariaStr = Util.formatarMoedaReal(valorTotalAtualizacaoMonetaria);

						// Valor total restante de débitos a cobrar referente a serviço e
						// parcelamento
						final int indiceCurtoPrazo = 0;
						final int indiceLongoPrazo = 1;

						BigDecimal valorTotalRestanteServicosACobrar = BigDecimal.ZERO;
						BigDecimal valorTotalRestanteServicosACobrarCurtoPrazo = BigDecimal.ZERO;
						BigDecimal valorTotalRestanteServicosACobrarLongoPrazo = BigDecimal.ZERO;

						BigDecimal valorTotalRestanteParcelamentosACobrar = BigDecimal.ZERO;
						BigDecimal valorTotalRestanteParcelamentosACobrarCurtoPrazo = BigDecimal.ZERO;
						BigDecimal valorTotalRestanteParcelamentosACobrarLongoPrazo = BigDecimal.ZERO;

						Collection<Integer> tiposParcelamento = null;
						try{
							tiposParcelamento = Util
											.converterStringParaColecaoInteger(ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO
											.executar());
						}catch(ControladorException e){
							e.printStackTrace();
						}

						if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){
							FinanciamentoTipo financiamentoTipo = null;
							BigDecimal[] valoresDeCurtoELongoPrazo = null;

							BigDecimal curtoPrazo = BigDecimal.ZERO;
							BigDecimal longoPrazo = BigDecimal.ZERO;

							for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){
								financiamentoTipo = debitoACobrar.getFinanciamentoTipo();
								Integer idFinanciamentoTipo = financiamentoTipo.getId();

								if(FinanciamentoTipo.SERVICO_NORMAL.equals(idFinanciamentoTipo)){
									// Serviço

									valoresDeCurtoELongoPrazo = this.obterValoresCurtoELongoPrazo(debitoACobrar);

									curtoPrazo = valoresDeCurtoELongoPrazo[indiceCurtoPrazo].setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO);
									valorTotalRestanteServicosACobrarCurtoPrazo = valorTotalRestanteServicosACobrarCurtoPrazo
													.add(curtoPrazo);

									longoPrazo = valoresDeCurtoELongoPrazo[indiceLongoPrazo].setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO);
									valorTotalRestanteServicosACobrarLongoPrazo = valorTotalRestanteServicosACobrarLongoPrazo
													.add(longoPrazo);

								}else if(tiposParcelamento != null && tiposParcelamento.contains(idFinanciamentoTipo)){
									// Parcelamento

									valoresDeCurtoELongoPrazo = this.obterValoresCurtoELongoPrazo(debitoACobrar);

									curtoPrazo = valoresDeCurtoELongoPrazo[indiceCurtoPrazo].setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO);
									valorTotalRestanteParcelamentosACobrarCurtoPrazo = valorTotalRestanteParcelamentosACobrarCurtoPrazo
													.add(curtoPrazo);

									longoPrazo = valoresDeCurtoELongoPrazo[indiceLongoPrazo].setScale(Parcelamento.CASAS_DECIMAIS,
													Parcelamento.TIPO_ARREDONDAMENTO);
									valorTotalRestanteParcelamentosACobrarLongoPrazo = valorTotalRestanteParcelamentosACobrarLongoPrazo
													.add(longoPrazo);
								}
							}

							// Serviço Total
							valorTotalRestanteServicosACobrar = valorTotalRestanteServicosACobrarCurtoPrazo
											.add(valorTotalRestanteServicosACobrarLongoPrazo);

							valorTotalRestanteServicosACobrarStr = Util.formatarMoedaReal(valorTotalRestanteServicosACobrar);

							// Parcelamento Total
							valorTotalRestanteParcelamentosACobrar = valorTotalRestanteParcelamentosACobrarCurtoPrazo
											.add(valorTotalRestanteParcelamentosACobrarLongoPrazo);

							valorTotalRestanteParcelamentosACobrarStr = Util.formatarMoedaReal(valorTotalRestanteParcelamentosACobrar);
						}

						// Valor total restante a ser creditado da lista dos créditos a realizar
						BigDecimal valorTotalCreditoARealizar = BigDecimal.ZERO;

						if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){
							BigDecimal valorTotalCreditoARealizarAux = BigDecimal.ZERO;

							for(CreditoARealizar creditoARealizar : colecaoCreditoARealizar){
								valorTotalCreditoARealizarAux = creditoARealizar.getValorTotal();
								valorTotalCreditoARealizar = valorTotalCreditoARealizar.add(valorTotalCreditoARealizarAux);
							}
						}

						valorTotalCreditoARealizarStr = Util.formatarMoedaReal(valorTotalCreditoARealizar);

						// Valor do débito atualizado
						BigDecimal valorDebitoTotalAtualizado = BigDecimal.ZERO;
						BigDecimal valorDebitoTotalOriginal = BigDecimal.ZERO;

						valorDebitoTotalOriginal = valorDebitoTotalOriginal.add(valorTotalConta);
						valorDebitoTotalOriginal = valorDebitoTotalOriginal.add(valorTotalGuiaPagamento);
						valorDebitoTotalOriginal = valorDebitoTotalOriginal.add(valorTotalRestanteServicosACobrar);
						valorDebitoTotalOriginal = valorDebitoTotalOriginal.add(valorTotalRestanteParcelamentosACobrar);
						valorDebitoTotalOriginal = valorDebitoTotalOriginal.subtract(valorTotalCreditoARealizar);
						valorDebitoTotalAtualizado = valorDebitoTotalOriginal.add(valorTotalAcrescimoImpontualidade);

						valorDebitoTotalAtualizadoStr = Util.formatarMoedaReal(valorDebitoTotalAtualizado);

						// Relação de RD's

						// [FS0005] Verificar se o usuário possui autorização para utilizar a RD
						boolean temPermissaoResolucaoDiretoria = fachada.verificarPermissaoEspecial(
										PermissaoEspecial.USAR_PLANO_PAI_PARA_ORGAO_PUBLICO, usuarioLogado);

						colecaoNegociacaoDebitoOpcoesRDHelper = new ArrayList<NegociacaoDebitoOpcoesRDHelper>();

						Collection<ResolucaoDiretoria> colecaoResolucaoDiretoriaAux = null;

						if(temPermissaoResolucaoDiretoria){
							colecaoResolucaoDiretoriaAux = fachada.pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual();
						}else{
							// RD's filtrando pela grupo do usuário e com o o indicador de livre
							Integer idUsuarioLogado = usuarioLogado.getId();
							Collection<Grupo> colecaoGrupoUsuario = fachada.pesquisarGruposUsuario(idUsuarioLogado);

							Collection<Integer> idsGrupoUsuario = new ArrayList();

							if(!Util.isVazioOrNulo(colecaoGrupoUsuario)){
								Integer idGrupo = null;

								for(Grupo grupo : colecaoGrupoUsuario){
									idGrupo = grupo.getId();
									idsGrupoUsuario.add(idGrupo);
								}
							}

							colecaoResolucaoDiretoriaAux = fachada.pesquisarResolucaoDiretoriaMaiorDataVigenciaInicio(idsGrupoUsuario);
						}

						if(!Util.isVazioOrNulo(colecaoResolucaoDiretoriaAux)){
							NegociacaoDebitoOpcoesRDHelper negociacaoDebitoOpcoesRDHelper = null;

							Object[] situacaoImovelPerfilParcelamento = null;

							Collection<ParcelamentoQuantidadePrestacao> colecaoParcelamentoQuantidadePrestacao = null;

							Integer idResolucaoDiretoria = null;
							String numeroResolucaoDiretoria = null;
							// BigDecimal percentualEntradaSugerida = null;
							Short maiorQuantidadeParcelas = null;

							Short quantidadeMaximaPrestacao = null;
							Integer verificarRDUtilizadaPeloImovel = null;
							Short numeroReparcelamentoConsecutivos = null;
							Integer idParcelamentoQuantidadeReparcelamento = null;
							ParcelamentoQuantidadeReparcelamento parcelamentoQuantidadeReparcelamento = null;
							FiltroParcelamentoQuantidadeReparcelamento filtroParcelamentoQuantidadeReparcelamento = null;
							Collection<ParcelamentoQuantidadeReparcelamento> colecaoParcelamentoQuantidadeReparcelamento = null;
							Integer numeroDiasVencimentoDaEntrada = null;
							BigDecimal valorDebitoAposDesconto = null;
							BigDecimal valorDebitoMaiorParcela = null;
							Short quantidadePrestacao = null;
							BigDecimal maiorQuantidadeParcelasBD = null;

							Date dataVencimentoEntrada = null;
							String dataVencimentoEntradaStr = null;

							String inicioIntervaloParcelamento = "01/0001";
							Integer indicadorRestabelecimento = 2;

							IndicadoresParcelamentoHelper indicadoresParcelamentoHelper = new IndicadoresParcelamentoHelper();
							indicadoresParcelamentoHelper.setIndicadorContasRevisao(2);
							indicadoresParcelamentoHelper.setIndicadorDebitosACobrar(1);
							indicadoresParcelamentoHelper.setIndicadorCreditoARealizar(1);
							indicadoresParcelamentoHelper.setIndicadorGuiasPagamento(1);
							indicadoresParcelamentoHelper.setIndicadorAcrescimosImpotualidade(1);

							Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = null;
							NegociacaoOpcoesParcelamentoHelper opcoesParcelamento = null;

							// BigDecimal valorDescontoInatividade = null;
							// BigDecimal valorDescontoAntiguidade = null;
							// BigDecimal valorTotalDesconto = null;
							BigDecimal valorTotalJurosFinanciamento = null;
							BigDecimal percentualTotalDesconto = null;

							String idMunicipioStr = ConstantesAplicacao.get("empresa.municipio");
							Integer idMunicipio = Util.converterStringParaInteger(idMunicipioStr);

							Municipio municipio = new Municipio();
							municipio.setId(idMunicipio);

							for(ResolucaoDiretoria resolucaoDiretoria : colecaoResolucaoDiretoriaAux){
								idResolucaoDiretoria = resolucaoDiretoria.getId();
								numeroResolucaoDiretoria = resolucaoDiretoria.getNumeroResolucaoDiretoria();

								// [FS0009] Verificar uso da RD para outro parcelamento
								verificarRDUtilizadaPeloImovel = fachada.verificarRDUtilizadaPeloImovel(idResolucaoDiretoria, idImovel);

								if(verificarRDUtilizadaPeloImovel == null){
									try{
										// [SB0004] Verificar Situação do Imóvel e Perfil
										// Parcelamento
										situacaoImovelPerfilParcelamento = this.verificarSituacaoImovelPerfilParcelamento(imovel,
														idResolucaoDiretoria, valorTotalRestanteParcelamentosACobrar, usuarioLogado);

										colecaoParcelamentoQuantidadePrestacao = (Collection<ParcelamentoQuantidadePrestacao>) situacaoImovelPerfilParcelamento[2];

										maiorQuantidadeParcelas = null;
										// percentualEntradaSugerida = null;
										BigDecimal valorMinimoEntrada = null;
										numeroReparcelamentoConsecutivos = null;
										numeroDiasVencimentoDaEntrada = null;
										dataVencimentoEntrada = null;
										dataVencimentoEntradaStr = null;

										if(!Util.isVazioOrNulo(colecaoParcelamentoQuantidadePrestacao)){
											// Pesquisa o registro com a maior quantidade de
											// parcelas
											for(ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao : colecaoParcelamentoQuantidadePrestacao){
												quantidadeMaximaPrestacao = parcelamentoQuantidadePrestacao.getQuantidadeMaximaPrestacao();

												if(maiorQuantidadeParcelas == null || quantidadeMaximaPrestacao > maiorQuantidadeParcelas){
													maiorQuantidadeParcelas = quantidadeMaximaPrestacao;

													// percentualEntradaSugerida =
													// parcelamentoQuantidadePrestacao.getPercentualEntradaSugerida();

													// valorMinimoEntrada =
													// parcelamentoQuantidadePrestacao.getValorMinimoEntrada();

													parcelamentoQuantidadeReparcelamento = parcelamentoQuantidadePrestacao
																	.getParcelamentoQuantidadeReparcelamento();

													if(parcelamentoQuantidadeReparcelamento != null){
														idParcelamentoQuantidadeReparcelamento = parcelamentoQuantidadeReparcelamento
																		.getId();

														filtroParcelamentoQuantidadeReparcelamento = new FiltroParcelamentoQuantidadeReparcelamento();
														filtroParcelamentoQuantidadeReparcelamento.adicionarParametro(new ParametroSimples(
																		FiltroParcelamentoQuantidadeReparcelamento.ID,
																		idParcelamentoQuantidadeReparcelamento));

														colecaoParcelamentoQuantidadeReparcelamento = fachada.pesquisar(
																		filtroParcelamentoQuantidadeReparcelamento,
																		ParcelamentoQuantidadeReparcelamento.class.getName());

														if(!Util.isVazioOrNulo(colecaoParcelamentoQuantidadeReparcelamento)){
															parcelamentoQuantidadeReparcelamento = (ParcelamentoQuantidadeReparcelamento) Util
																			.retonarObjetoDeColecao(colecaoParcelamentoQuantidadeReparcelamento);
															numeroReparcelamentoConsecutivos = parcelamentoQuantidadeReparcelamento
																			.getQuantidadeMaximaReparcelamento();
														}
													}

													numeroDiasVencimentoDaEntrada = parcelamentoQuantidadePrestacao
																	.getNumeroDiasVencimentoDaEntrada();
												}
											}
										}

										if(maiorQuantidadeParcelas == null){
											maiorQuantidadeParcelas = 0;
										}

										// if(percentualEntradaSugerida == null){
										// percentualEntradaSugerida = BigDecimal.ZERO;
										// }

										// if(valorMinimoEntrada == null){
										// valorMinimoEntrada = BigDecimal.ZERO;
										// }

										if(numeroDiasVencimentoDaEntrada == null){
											numeroDiasVencimentoDaEntrada = 1;
										}

										// Calcula a data de entrada verificando se é dia util
										if(numeroDiasVencimentoDaEntrada != null){
											dataVencimentoEntrada = Util.adicionarNumeroDiasDeUmaData(new Date(),
															numeroDiasVencimentoDaEntrada);

											dataVencimentoEntrada = fachada.verificarDataUtilVencimento(dataVencimentoEntrada, municipio);

											dataVencimentoEntradaStr = Util.formatarData(dataVencimentoEntrada);
										}

										if(numeroReparcelamentoConsecutivos == null){
											numeroReparcelamentoConsecutivos = 0;
										}

										// [SB0002] - Obter Opções de Parcelamento
										opcoesParcelamento = fachada.obterOpcoesDeParcelamento(idResolucaoDiretoria, idImovel, null,
														idLigacaoAguaSituacao, idLigacaoEsgotoSituacao, idPerfilImovel,
														inicioIntervaloParcelamento, indicadorRestabelecimento, colecaoContaValores,
														valorDebitoTotalAtualizado, valorTotalMulta, valorTotalJurosMora,
														valorTotalAtualizacaoMonetaria, numeroReparcelamentoConsecutivos.intValue(),
														colecaoGuiaPagamentoValores, usuarioLogado, valorTotalRestanteParcelamentosACobrar,
														Integer.valueOf(anoMesInicialReferenciaDebito), Integer
																		.valueOf(anoMesFinalReferenciaDebito),
														indicadoresParcelamentoHelper, dataVencimentoEntradaStr);

										ParcelamentoPerfil parcelamentoPerfil = opcoesParcelamento.getParcelamentoPerfil();

										if(opcoesParcelamento.getValorEntradaMinima() != null){
											valorMinimoEntrada = opcoesParcelamento.getValorEntradaMinima().setScale(
															Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
										}

										// Avaliar valor do debito abaixo ou acima da faixa
										if(parcelamentoPerfil.getValorMaximoDebitoAParcelarFaixaDebito() != null){
											if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
															ParcelamentoPerfil.INDICADOR_DEBITO_ATUALIZADO)){
												if(valorDebitoTotalAtualizado.compareTo(parcelamentoPerfil
																.getValorMaximoDebitoAParcelarFaixaDebito()) > 0){
													continue;
												}
											}else if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
															ParcelamentoPerfil.INDICADOR_DEBITO_ORIGINAL)){
												if(valorDebitoTotalOriginal.compareTo(parcelamentoPerfil
																.getValorMaximoDebitoAParcelarFaixaDebito()) > 0){
													continue;
												}
											}
										}
										if(parcelamentoPerfil.getValorMinimoDebitoAParcelarFaixaDebito() != null){
											if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
															ParcelamentoPerfil.INDICADOR_DEBITO_ATUALIZADO)){
												if(valorDebitoTotalAtualizado.compareTo(parcelamentoPerfil
																.getValorMinimoDebitoAParcelarFaixaDebito()) < 0){
													continue;
												}
											}else if(parcelamentoPerfil.getIndicadorDebitoOriginalOuAtualizadoFaixaDebito().equals(
															ParcelamentoPerfil.INDICADOR_DEBITO_ORIGINAL)){
												if(valorDebitoTotalOriginal.compareTo(parcelamentoPerfil
																.getValorMinimoDebitoAParcelarFaixaDebito()) < 0){
													continue;
												}
											}
										}

										colecaoOpcoesParcelamento = opcoesParcelamento.getOpcoesParcelamento();

										// valorDescontoInatividade =
										// opcoesParcelamento.getValorDescontoInatividade();
										// valorDescontoAntiguidade =
										// opcoesParcelamento.getValorDescontoAntiguidade();

										BigDecimal descontoInatividadeLigacaoAgua = opcoesParcelamento.getValorDescontoInatividade();
										BigDecimal descontoAntiguidadeDebito = opcoesParcelamento.getValorDescontoAntiguidade();
										BigDecimal descontoAcrescimosImpontualidade = opcoesParcelamento
														.getValorDescontoAcrecismosImpotualidade();
										BigDecimal descontoSancoesRDEspecial = opcoesParcelamento.getValorDescontoSancoesRDEspecial();
										BigDecimal descontoTarifaSocialRDEspecial = opcoesParcelamento
														.getValorDescontoTarifaSocialRDEspecial();

										BigDecimal valorTotalDescontos = BigDecimal.ZERO;
										valorTotalDescontos.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
										valorTotalDescontos = descontoAcrescimosImpontualidade.add(descontoAntiguidadeDebito);
										valorTotalDescontos = valorTotalDescontos.add(descontoInatividadeLigacaoAgua);
										valorTotalDescontos = valorTotalDescontos.add(descontoSancoesRDEspecial);
										valorTotalDescontos = valorTotalDescontos.add(descontoTarifaSocialRDEspecial);

										BigDecimal resultadoDiferenca = BigDecimal.ZERO;
										resultadoDiferenca.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);
										resultadoDiferenca = valorDebitoTotalAtualizado.subtract(valorTotalDescontos);

										// valorTotalDesconto =
										// valorDescontoInatividade.add(valorDescontoAntiguidade);

										percentualTotalDesconto = Util.calcularPercentualBigDecimal(valorTotalDescontos,
														valorDebitoTotalAtualizado);

										// Pesquisa maior parcela de todas as opções
										valorDebitoMaiorParcela = BigDecimal.ZERO;

										if(!Util.isVazioOrNulo(colecaoOpcoesParcelamento)){
											quantidadePrestacao = null;

											for(OpcoesParcelamentoHelper opcoesParcelamentoHelper : colecaoOpcoesParcelamento){
												quantidadePrestacao = opcoesParcelamentoHelper.getQuantidadePrestacao();

												if(quantidadePrestacao.equals(maiorQuantidadeParcelas)){
													valorDebitoMaiorParcela = opcoesParcelamentoHelper.getValorPrestacao();
													break;
												}
											}
										}

										maiorQuantidadeParcelasBD = new BigDecimal(maiorQuantidadeParcelas);

										// valorDebitoAposDesconto =
										// valorDebitoMaiorParcela.multiply(maiorQuantidadeParcelasBD);
										valorDebitoAposDesconto = valorDebitoTotalAtualizado.subtract(valorTotalDescontos);

										BigDecimal valorSerParcelado = BigDecimal.ZERO;
										valorSerParcelado = valorDebitoMaiorParcela.multiply(maiorQuantidadeParcelasBD);
										valorSerParcelado = valorSerParcelado.add(valorMinimoEntrada);

										// valorTotalJurosFinanciamento =
										// valorDebitoAposDesconto.subtract(valorDebitoTotalAtualizado);
										valorTotalJurosFinanciamento = valorSerParcelado.subtract(valorDebitoAposDesconto);
										valorTotalJurosFinanciamento
														.setScale(Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO);

										if(valorTotalJurosFinanciamento.compareTo(BigDecimal.ZERO) < 0){
											valorTotalJurosFinanciamento = BigDecimal.ZERO;
										}

										// Monta objeto para ser apresentado na listagem de RD's
										negociacaoDebitoOpcoesRDHelper = new NegociacaoDebitoOpcoesRDHelper();
										negociacaoDebitoOpcoesRDHelper.setIdResolucaoDiretoria(idResolucaoDiretoria);
										negociacaoDebitoOpcoesRDHelper.setNumeroResolucaoDiretoria(numeroResolucaoDiretoria);
										negociacaoDebitoOpcoesRDHelper.setValorDebitoTotalAtualizado(valorDebitoTotalAtualizado);
										negociacaoDebitoOpcoesRDHelper.setPercentualTotalDesconto(percentualTotalDesconto);
										negociacaoDebitoOpcoesRDHelper.setValorTotalDesconto(valorTotalDescontos);
										negociacaoDebitoOpcoesRDHelper.setValorDebitoAposDesconto(valorDebitoAposDesconto);
										negociacaoDebitoOpcoesRDHelper.setValorMinimoEntrada(valorMinimoEntrada);
										negociacaoDebitoOpcoesRDHelper.setValorTotalJurosFinanciamento(valorTotalJurosFinanciamento);
										negociacaoDebitoOpcoesRDHelper.setQuantidadeParcelas(maiorQuantidadeParcelas);
										negociacaoDebitoOpcoesRDHelper.setValorParcela(valorDebitoMaiorParcela);
										// negociacaoDebitoOpcoesRDHelper.setPercentualEntradaSugerida(percentualEntradaSugerida);

										negociacaoDebitoOpcoesRDHelper.setDataVencimentoEntrada(dataVencimentoEntradaStr);
										negociacaoDebitoOpcoesRDHelper.setNumeroReparcelamentoConsecutivos(numeroReparcelamentoConsecutivos
														.intValue());

										// Objeto com as informações do imóvel
										negociacaoDebitoImovelHelper = new NegociacaoDebitoImovelHelper();
										negociacaoDebitoImovelHelper.setIdImovel(idImovel);
										negociacaoDebitoImovelHelper.setIdLigacaoAguaSituacao(idLigacaoAguaSituacao);
										negociacaoDebitoImovelHelper.setIdLigacaoEsgotoSituacao(idLigacaoEsgotoSituacao);
										negociacaoDebitoImovelHelper.setIdPerfilImovel(idPerfilImovel);
										negociacaoDebitoImovelHelper.setColecaoContaValores(colecaoContaValores);
										negociacaoDebitoImovelHelper.setColecaoGuiaPagamentoValores(colecaoGuiaPagamentoValores);
										negociacaoDebitoImovelHelper.setColecaoDebitoACobrar(colecaoDebitoACobrar);
										negociacaoDebitoImovelHelper.setColecaoCreditoARealizar(colecaoCreditoARealizar);
										negociacaoDebitoImovelHelper.setValorTotalMulta(valorTotalMulta);
										negociacaoDebitoImovelHelper.setValorTotalJurosMora(valorTotalJurosMora);
										negociacaoDebitoImovelHelper.setValorTotalAtualizacaoMonetaria(valorTotalAtualizacaoMonetaria);
										negociacaoDebitoImovelHelper
														.setValorTotalRestanteParcelamentosACobrar(valorTotalRestanteParcelamentosACobrarLongoPrazo);

									}catch(Exception ex){
										// Ignorar RD's que não se enquadram

										String message = ex.getMessage();

										String msgSituacaoImovel = "atencao.nao.existe.situacao.imovel.correspondente.situacao.agua.esgoto";
										String msgPerfilParcelamento = "atencao.nao.existe.perfil.parcelamento.correspondente.situacao.imovel";
										String msgQtdReparcelamento = "atencao.nao.existe.condicao.por.quantidade.reparcelamentos.perfil";
										String msgQtdPrestacoes = "atencao.nao.existe.condicao.parcelamento.quantidade.prestacoes";

										if(message.equals(msgSituacaoImovel) || message.equals(msgPerfilParcelamento)
														|| message.equals(msgQtdReparcelamento) || message.equals(msgQtdPrestacoes)){
											continue;
										}else{
											throw new ActionServletException(message);
										}
									}

									colecaoNegociacaoDebitoOpcoesRDHelper.add(negociacaoDebitoOpcoesRDHelper);
								}
							}
						}

					}else{
						// Imóvel não encontrado

						httpServletRequest.setAttribute("imovelNaoEncontrado", "true");

						matriculaImovel = "MATRÍCULA INEXISTENTE";
					}

					// Seta os valores no form

					form.setIdImovelAntes(idImovelStr);
					form.setInscricaoImovel(matriculaImovel);
					form.setSituacaoAgua(descricaoLigacaoAguaSituacao);
					form.setSituacaoEsgoto(descricaoLigacaoEsgotoSituacao);
					form.setNumeroQuadra(numeroQuadraStr);

					form.setEnderecoImovel(enderecoImovel);

					form.setValorTotalConta(valorTotalContaStr);
					form.setValorTotalGuia(valorTotalGuiaStr);
					form.setValorTotalAcrescimo(valorTotalAcrescimoStr);
					form.setValorTotalCreditoARealizar(valorTotalCreditoARealizarStr);

					form.setValorTotalMulta(valorTotalMultaStr);
					form.setValorTotalJurosMora(valorTotalJurosMoraStr);
					form.setValorTotalAtualizacaoMonetaria(valorTotalAtualizacaoMonetariaStr);

					form.setValorTotalRestanteServicosACobrar(valorTotalRestanteServicosACobrarStr);
					form.setValorTotalRestanteParcelamentosACobrar(valorTotalRestanteParcelamentosACobrarStr);

					form.setValorDebitoTotalAtualizado(valorDebitoTotalAtualizadoStr);

					form.setIdClienteUsuario(idClienteUsuarioStr);
					form.setNomeCliente(nomeCliente);
					form.setEmailCliente(emailCliente);
					form.setCpfCnpjCliente(cpfCnpjCliente);
					form.setCpfCnpjClienteAntes(cpfCnpjClienteAntes);
					form.setIndicadorPessoaFisicaJuridica(indicadorPessoaFisicaJuridicaStr);

					sessao.setAttribute("colecaoClientesImovel", colecaoClientesImovel);
					sessao.setAttribute("colecaoNegociacaoDebitoOpcoesRDHelper", colecaoNegociacaoDebitoOpcoesRDHelper);
					sessao.setAttribute("negociacaoDebitoImovelHelper", negociacaoDebitoImovelHelper);

					// Negociação Selecionada
					form.setNegociacaoIdRD("");
					form.setNegociacaoNumeroRD("");
					form.setNegociacaoValorDebitoTotalAtualizado("");
					form.setNegociacaoPercentualTotalDesconto("");
					form.setNegociacaoValorTotalDesconto("");
					form.setNegociacaoValorDebitoAposDesconto("");
					form.setNegociacaoValorEntrada("");
					form.setNegociacaoValorTotalJurosFinanciamento("");
					form.setNegociacaoQuantidadeParcelas("");
					form.setNegociacaoValorParcela("");
					form.setNegociacaoValorDescontoPagamentoAVista("");
					form.setNegociacaoValorDescontoInatividade("");
					form.setNegociacaoValorDescontoAntiguidade("");
					form.setNegociacaoNumeroMesesEntreParcelas("");
					form.setNegociacaoNumeroParcelasALancar("");
					form.setNegociacaoNumeroMesesInicioCobranca("");
					form.setNegociacaoNumeroDiasVencimentoDaEntrada("");
					form.setNegociacaoTaxaJuros("");
				}
			}
		}

		return retorno;
	}

	// [SB0001] - Obter Valores de Curto e Longo Prazo
	private BigDecimal[] obterValoresCurtoELongoPrazo(DebitoACobrar debitoACobrar){

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		BigDecimal[] retorno = null;

		short numeroPrestacaoDebito = debitoACobrar.getNumeroPrestacaoDebito();
		short numeroPrestacaoCobradas = debitoACobrar.getNumeroPrestacaoCobradas();
		BigDecimal valorRestanteACobrar = debitoACobrar.getValorTotal();

		retorno = fachada.obterValorACobrarDeCurtoELongoPrazo(numeroPrestacaoDebito, numeroPrestacaoCobradas, valorRestanteACobrar);

		return retorno;
	}

	// [SB0004] - Verificar Situação do Imóvel e Perfil Parcelamento
	private Object[] verificarSituacaoImovelPerfilParcelamento(Imovel imovel, Integer idResolucaoDiretoria,
					BigDecimal valorTotalRestanteParcelamentosACobrar, Usuario usuarioLogado){

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		Object[] retorno = null;

		LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();
		Integer idLigacaoAguaSituacao = ligacaoAguaSituacao.getId();

		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();
		Integer idLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getId();

		Integer idImovel = imovel.getId();

		ImovelPerfil imovelPerfil = imovel.getImovelPerfil();
		Integer idImovelPerfil = imovelPerfil.getId();

		Integer numeroReparcelamentoConsecutivosInt = 0;
		Short numeroReparcelamentoConsecutivos = imovel.getNumeroReparcelamentoConsecutivos();

		if(numeroReparcelamentoConsecutivos != null){
			numeroReparcelamentoConsecutivosInt = numeroReparcelamentoConsecutivos.intValue();
		}

		retorno = fachada.verificarSituacaoImovelPerfilParcelamento(idLigacaoAguaSituacao, idLigacaoEsgotoSituacao, idImovel,
						idImovelPerfil, idResolucaoDiretoria, numeroReparcelamentoConsecutivosInt, valorTotalRestanteParcelamentosACobrar,
						false, null, usuarioLogado);

		return retorno;
	}

}
