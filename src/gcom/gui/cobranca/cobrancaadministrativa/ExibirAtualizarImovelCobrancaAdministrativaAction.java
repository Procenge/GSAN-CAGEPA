
package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaMotivoRetirada;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaDebitoSituacao;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.cobranca.bean.ItensRemuradosHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.cobranca.CobrancaAdministrativaContaHelper;
import gcom.gui.cobranca.CobrancaAdministrativaGuiaHelper;
import gcom.gui.cobranca.CobrancaAdministrativaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3060] Consultar Imóvel Cobrança Administrativa
 * [SB0001] Consultar Dados da Cobrança Administrativa do Imóvel
 * 
 * @author Hebert Falcão
 * @date 15/09/2012
 */
public class ExibirAtualizarImovelCobrancaAdministrativaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("atualizarImovelCobrancaAdministrativa");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarImovelCobrancaAdministrativaActionForm form = (AtualizarImovelCobrancaAdministrativaActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String idImovelCobrancaSituacaoStr = httpServletRequest.getParameter("idImovelCobrancaSituacao");

		if(Util.isVazioOuBranco(idImovelCobrancaSituacaoStr)){
			throw new ActionServletException("atencao.naoinformado", null, "Id Imóvel Cobrança Situação");
		}

		Integer idImovelCobrancaSituacao = Integer.valueOf(idImovelCobrancaSituacaoStr);

		form.limpar();

		sessao.removeAttribute("colecaoImovelSubcategorias");
		sessao.removeAttribute("colecaoClientesImovel");
		sessao.removeAttribute("colecaoItemTotalizado");
		sessao.removeAttribute("colecaoCobrancaAdministrativaContaHelper");
		sessao.removeAttribute("colecaoCobrancaAdministrativaDebitoHelper");
		sessao.removeAttribute("valorTotalAgua");
		sessao.removeAttribute("valorTotalEsgoto");
		sessao.removeAttribute("valorTotalDebito");
		sessao.removeAttribute("valorTotalCredito");
		sessao.removeAttribute("valorTotalImposto");
		sessao.removeAttribute("valorTotalDebitoConta");
		sessao.removeAttribute("colecaoCobrancaAdministrativaGuiaHelper");
		sessao.removeAttribute("valorTotalDebitoGuia");
		sessao.removeAttribute("colecaoImovelCobAdmTotalizado");
		sessao.removeAttribute("colecaoContasDocumentosRemunerados");
		sessao.removeAttribute("valorTotalContasDocumentosRemunerados");
		sessao.removeAttribute("valorTotalRemuneracaoContaDocumentosRemunerados");
		sessao.removeAttribute("colecaoGuiasDocumentosRemunerados");
		sessao.removeAttribute("valorTotalGuiasDocumentosRemunerados");
		sessao.removeAttribute("valorTotalRemuneracaoGuiaDocumentosRemunerados");
		sessao.removeAttribute("colecaoCobrancaAdministrativaHelper");
		sessao.removeAttribute("colecaoDebitosDocumentosRemunerados");
		sessao.removeAttribute("valorTotalDebitosDocumentosRemunerados");
		sessao.removeAttribute("valorTotalRemuneracaoDebitosDocumentosRemunerados");


		ImovelCobrancaSituacao imovelCobrancaSituacao = fachada.pesquisarImovelCobrancaSituacaoPeloId(idImovelCobrancaSituacao);

		if(imovelCobrancaSituacao == null){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		Imovel imovel = imovelCobrancaSituacao.getImovel();
		Integer idImovel = null;

		if(imovel != null){

			// 1.1. Imóvel
			idImovel = imovel.getId();
			form.setIdImovel(idImovel.toString());

			//
			// 1.2. Dados do Imóvel
			//

			// 1.2.1. Inscrição
			String inscricaoFormatadaImovel = imovel.getInscricaoFormatada();
			form.setInscricaoFormatadaImovel(inscricaoFormatadaImovel);

			// 1.2.2. Situação de Água
			LigacaoAguaSituacao ligacaoAguaSituacao = imovel.getLigacaoAguaSituacao();

			if(ligacaoAguaSituacao != null){
				String descricaoLigacaoAguaSituacao = ligacaoAguaSituacao.getDescricao();
				form.setDescricaoLigacaoAguaSituacao(descricaoLigacaoAguaSituacao);
			}

			// 1.2.3. Situação de Esgoto
			LigacaoEsgotoSituacao ligacaoEsgotoSituacao = imovel.getLigacaoEsgotoSituacao();

			if(ligacaoEsgotoSituacao != null){
				String descricaoLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getDescricao();
				form.setDescricaoLigacaoEsgotoSituacao(descricaoLigacaoEsgotoSituacao);
			}

			// 1.2.4. Perfil do Imóvel
			ImovelPerfil imovelPerfil = imovel.getImovelPerfil();

			if(imovelPerfil != null){
				String descricaoImovelPerfil = imovelPerfil.getDescricao();
				form.setDescricaoImovelPerfil(descricaoImovelPerfil);
			}

			// 1.2.5. Endereço do Imóvel
			String enderecoImovel = fachada.pesquisarEndereco(idImovel);
			form.setEnderecoImovel(enderecoImovel);

			// 1.2.6. Lista das subcategorias e quantidades de economias do imóvel
			Collection colecaoImovelSubcategorias = fachada.pesquisarCategoriasImovel(idImovel);
			sessao.setAttribute("colecaoImovelSubcategorias", colecaoImovelSubcategorias);

			// 1.2.7. Lista dos clientes associados ao imóvel
			Collection colecaoClientesImovel = fachada.pesquisarClientesImovel(idImovel);
			sessao.setAttribute("colecaoClientesImovel", colecaoClientesImovel);
		}

		// 1.3. Dados da Cobrança Administrativa
		CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = imovelCobrancaSituacao.getCobrancaAcaoAtividadeComando();

		if(cobrancaAcaoAtividadeComando != null){
			Empresa empresa = cobrancaAcaoAtividadeComando.getEmpresa();

			if(empresa != null){
				String descricaoEmpresa = empresa.getDescricao();
				form.setEmpresa(descricaoEmpresa);
			}
		}

		// 1.3.2. Data da Implantação
		Date dataImplantacaoCobranca = imovelCobrancaSituacao.getDataImplantacaoCobranca();

		if(dataImplantacaoCobranca != null){
			String dataImplantacao = Util.formatarData(dataImplantacaoCobranca);
			form.setDataImplantacao(dataImplantacao);
		}

		// 1.3.3. Data da Retirada
		Date dataRetiradaCobranca = imovelCobrancaSituacao.getDataRetiradaCobranca();

		if(dataRetiradaCobranca != null){
			String dataRetirada = Util.formatarData(dataRetiradaCobranca);
			form.setDataRetirada(dataRetirada);

			// 1.3.4. Motivo da Retirada
			ImovelCobrancaMotivoRetirada imovelCobrancaMotivoRetirada = imovelCobrancaSituacao.getImovelCobrancaMotivoRetirada();

			if(imovelCobrancaMotivoRetirada != null){
				String descricaoImovelCobrancaMotivoRetirada = imovelCobrancaMotivoRetirada.getDescricao();
				form.setMotivoRetirada(descricaoImovelCobrancaMotivoRetirada);
			}
		}

		// 1.3.5. Data da Adimplência
		Date dataAdimplencia = imovelCobrancaSituacao.getDataAdimplencia();

		if(dataAdimplencia != null){
			String dataAdimplenciaStr = Util.formatarData(dataAdimplencia);
			form.setDataAdimplencia(dataAdimplenciaStr);

			// 1.3.6. Motivo da Adimplência
			CobrancaDebitoSituacao cobrancaDebitoSituacao = imovelCobrancaSituacao.getCobrancaDebitoSituacao();

			if(cobrancaDebitoSituacao != null){
				String descricaoCobrancaDebitoSituacao = cobrancaDebitoSituacao.getDescricao();
				form.setMotivoAdimplencia(descricaoCobrancaDebitoSituacao);
			}
		}

		// 1.3.7. Quantidade de Débitos
		Integer quantidadeDebito = imovelCobrancaSituacao.getQuantidadeDebito();

		if(quantidadeDebito != null){
			String quantidadeDebitoStr = Integer.toString(quantidadeDebito);
			form.setQuantidadeDebitos(quantidadeDebitoStr);
		}

		// 1.3.8. Valor dos Débitos
		BigDecimal valorDebito = imovelCobrancaSituacao.getValorDebito();

		if(valorDebito != null){
			String valorDebitoStr = Util.formatarMoedaReal(valorDebito);
			form.setValorDebitos(valorDebitoStr);
		}

		Integer idCobrancaAcaoAtividadeComando = null;

		if(cobrancaAcaoAtividadeComando != null && idImovel != null){
			idCobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando.getId();

			Collection<Object[]> colecaoItemTotalizado = fachada.totalizarCobrancaDocumentoItemPeloComando(idCobrancaAcaoAtividadeComando,
							idImovel);

			if(!Util.isVazioOrNulo(colecaoItemTotalizado)){
				sessao.setAttribute("colecaoItemTotalizado", colecaoItemTotalizado);
			}
		}

		// 1.3.12. Para cada grupo de itens remuneráveis com a mesma situação de remuneração (a
		// partir da lista dos itens remuneráveis retornada pelo [SB0001C], agrupando e ordenando
		// pela situação da remuneração (Indicador de Remuneração Realizada)), exibir grid com o
		// título “Situação dos Itens Remuneráveis”:
		form.setItensRemuradosHelper(fachada.selecionarItensRemureraveis(imovelCobrancaSituacao));
		if(Util.isNaoNuloBrancoZero(form.getItensRemuradosHelper())){
			List<String> listaDebitoACobrar = new ArrayList<String>();
			BigDecimal valorTotal = BigDecimal.ZERO;
			Collection<Object[]> colecaoItensRemunerados = new ArrayList<Object[]>();
			Object[] itensRemunerados = null;
			int contRemuneracaoRealizada = 0;
			int contRemuneracaoPendente = 0;
			BigDecimal totalDaBaseRemuneracaoRealizada = BigDecimal.ZERO;
			BigDecimal totalDaRemuneracaoRealizada = BigDecimal.ZERO;
			BigDecimal totalDaBaseRemuneracaoPendente = BigDecimal.ZERO;
			BigDecimal totalDaRemuneracaoPendente = BigDecimal.ZERO;
			
			for(ItensRemuradosHelper helper : form.getItensRemuradosHelper()){
				valorTotal = valorTotal.add(helper.getValorDaBaseRemuneracao());
				
				if(helper.getIndicadorRemuneracaoRealizada().equals(ConstantesSistema.SIM)){
					if(helper.getIdDocumentoTipo().equals(DocumentoTipo.DEBITO_A_COBRAR)){
						if(!listaDebitoACobrar.contains(helper.getIdentificacaoItem())){
							listaDebitoACobrar.add(helper.getIdentificacaoItem());
							contRemuneracaoRealizada++;
						}
					}else{
						contRemuneracaoRealizada++;
					}

					totalDaBaseRemuneracaoRealizada = totalDaBaseRemuneracaoRealizada.add(helper.getValorDaBaseRemuneracao());
					totalDaRemuneracaoRealizada = totalDaRemuneracaoRealizada.add((BigDecimal) helper.getValorDaRemuneracao());
				}else{
					contRemuneracaoPendente++;
					totalDaBaseRemuneracaoPendente = totalDaBaseRemuneracaoPendente.add(helper.getValorDaBaseRemuneracao());
					totalDaRemuneracaoPendente = totalDaRemuneracaoPendente.add((BigDecimal) helper.getValorDaRemuneracao());
				}
			}
			if(contRemuneracaoRealizada > 0){
				itensRemunerados = new Object[5];
				itensRemunerados[0] = "REMUNERAÇÃO REALIZADA";
				itensRemunerados[1] = contRemuneracaoRealizada;
				itensRemunerados[2] = Util.formatarMoedaReal(totalDaBaseRemuneracaoRealizada, Parcelamento.CASAS_DECIMAIS);
				itensRemunerados[3] = Util.formatarMoedaReal(totalDaRemuneracaoRealizada, Parcelamento.CASAS_DECIMAIS);
				colecaoItensRemunerados.add(itensRemunerados);
			}
			if(contRemuneracaoPendente > 0){
				itensRemunerados = new Object[5];
				itensRemunerados[0] = "REMUNERAÇÃO PENDENTE";
				itensRemunerados[1] = contRemuneracaoPendente;
				itensRemunerados[2] = Util.formatarMoedaReal(totalDaBaseRemuneracaoPendente, Parcelamento.CASAS_DECIMAIS);
				itensRemunerados[3] = Util.formatarMoedaReal(totalDaRemuneracaoPendente, Parcelamento.CASAS_DECIMAIS);
				colecaoItensRemunerados.add(itensRemunerados);
			}
			form.setQuantidadeItensRemunerados(String.valueOf(contRemuneracaoRealizada + contRemuneracaoPendente));
			form.setValorItensRemunerados(Util.formatarMoedaReal(valorTotal, Parcelamento.CASAS_DECIMAIS));
			sessao.setAttribute("colecaoItensRemunerados", colecaoItensRemunerados);
		}

		if(idCobrancaAcaoAtividadeComando != null && idImovel != null){
			DocumentoTipo documentoTipo = null;

			String descricaoDocumentoTipo = null;

			FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
			filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.CONTA));

			Collection<DocumentoTipo> colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

			if(!Util.isVazioOrNulo(colecaoDocumentoTipo)){
				documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);

				descricaoDocumentoTipo = documentoTipo.getDescricaoDocumentoTipo();

				form.setDescricaoDocumentoTipoConta(descricaoDocumentoTipo);
			}

			filtroDocumentoTipo = new FiltroDocumentoTipo();
			filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.GUIA_PAGAMENTO));

			colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

			if(!Util.isVazioOrNulo(colecaoDocumentoTipo)){
				documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);

				descricaoDocumentoTipo = documentoTipo.getDescricaoDocumentoTipo();

				form.setDescricaoDocumentoTipoGuia(descricaoDocumentoTipo);
			}

			filtroDocumentoTipo = new FiltroDocumentoTipo();
			filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, DocumentoTipo.DEBITO_A_COBRAR));

			colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

			if(!Util.isVazioOrNulo(colecaoDocumentoTipo)){
				documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);

				descricaoDocumentoTipo = documentoTipo.getDescricaoDocumentoTipo();

				form.setDescricaoDocumentoTipoDebito(descricaoDocumentoTipo);
			}

			//
			// 1.4. Dados dos Itens do Débito do Imóvel
			//
			// Conta
			List<CobrancaAdministrativaHelper> colecaoCobrancaAdministrativaHelper = fachada.obterDadosDaConta(form
							.getItensRemuradosHelper());
			if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaHelper)){
				sessao.setAttribute("colecaoCobrancaAdministrativaHelper", colecaoCobrancaAdministrativaHelper);

				// Totalizador
				BigDecimal valorTotalDebitoConta = BigDecimal.ZERO;
				for(CobrancaAdministrativaHelper cobrancaAdministrativaHelper : colecaoCobrancaAdministrativaHelper){
					valorTotalDebitoConta = valorTotalDebitoConta.add(cobrancaAdministrativaHelper.getValorDebito());
				}
				sessao.setAttribute("valorTotalDebitoConta", Util.formatarMoedaReal(valorTotalDebitoConta));
			}

			// Guia de Pagamento
			List<CobrancaAdministrativaHelper> colecaoCobrancaAdministrativaHelperGuia = fachada.obterDadosDaGuiaPagamento(form
							.getItensRemuradosHelper());
			if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaHelperGuia)){
				sessao.setAttribute("colecaoCobrancaAdministrativaGuiaHelper", colecaoCobrancaAdministrativaHelperGuia);

				// Totalizador
				BigDecimal valorTotalDebitoGuia = BigDecimal.ZERO;
				for(CobrancaAdministrativaHelper cobrancaAdministrativaHelper : colecaoCobrancaAdministrativaHelperGuia){
					valorTotalDebitoGuia = valorTotalDebitoGuia.add(cobrancaAdministrativaHelper.getValorDebito());
				}
				sessao.setAttribute("valorTotalDebitoGuia", Util.formatarMoedaReal(valorTotalDebitoGuia));
			}

			// Debito A Cobrar
			List<CobrancaAdministrativaHelper> colecaoCobrancaAdministrativaDebitoHelper = fachada.obterDadosDoDebitoACobrar(form
							.getItensRemuradosHelper());
			if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaDebitoHelper)){
				sessao.setAttribute("colecaoCobrancaAdministrativaDebitoHelper", colecaoCobrancaAdministrativaDebitoHelper);

				// Totalizador
				BigDecimal valorTotalDebito = BigDecimal.ZERO;
				for(CobrancaAdministrativaHelper cobrancaAdministrativaHelper : colecaoCobrancaAdministrativaDebitoHelper){
					valorTotalDebito = valorTotalDebito.add(cobrancaAdministrativaHelper.getValorDebito());
				}
				sessao.setAttribute("valorTotalDebito", Util.formatarMoedaReal(valorTotalDebito));
			}
			// Conta
			// Collection<CobrancaAdministrativaContaHelper>
			// colecaoCobrancaAdministrativaContaHelper = fachada
			// .pesquisarContasPeloComandoParaCobrancaAdministrativa(idCobrancaAcaoAtividadeComando,
			// idImovel);
			//
			// if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaContaHelper)){
			// sessao.setAttribute("colecaoCobrancaAdministrativaContaHelper",
			// colecaoCobrancaAdministrativaContaHelper);
			//
			// // Totalizador
			// BigDecimal valorTotalDebitoConta = BigDecimal.ZERO;
			//
			// for(CobrancaAdministrativaContaHelper cobrancaAdministrativaContaHelper :
			// colecaoCobrancaAdministrativaContaHelper){
			// valorTotalDebitoConta =
			// valorTotalDebitoConta.add(cobrancaAdministrativaContaHelper.getValorItemCobrado());
			// valorTotalDebitoConta =
			// valorTotalDebitoConta.add(cobrancaAdministrativaContaHelper.getValorAcrescimos());
			// }
			//
			// sessao.setAttribute("valorTotalDebitoConta",
			// Util.formatarMoedaReal(valorTotalDebitoConta));
			// }

			// Guia de Pagamento
			// Collection<CobrancaAdministrativaGuiaHelper> colecaoCobrancaAdministrativaGuiaHelper
			// = fachada
			// .pesquisarGuiasPeloComandoParaCobrancaAdministrativa(idCobrancaAcaoAtividadeComando,
			// idImovel);
			//
			// if(!Util.isVazioOrNulo(colecaoCobrancaAdministrativaGuiaHelper)){
			// sessao.setAttribute("colecaoCobrancaAdministrativaGuiaHelper",
			// colecaoCobrancaAdministrativaGuiaHelper);
			//
			// BigDecimal valorTotalDebitoGuia = BigDecimal.ZERO;
			//
			// for(CobrancaAdministrativaGuiaHelper cobrancaAdministrativaGuiaHelper :
			// colecaoCobrancaAdministrativaGuiaHelper){
			// valorTotalDebitoGuia =
			// valorTotalDebitoGuia.add(cobrancaAdministrativaGuiaHelper.getValorItemCobrado());
			// valorTotalDebitoGuia =
			// valorTotalDebitoGuia.add(cobrancaAdministrativaGuiaHelper.getValorAcrescimos());
			// }
			//
			// sessao.setAttribute("valorTotalDebitoGuia",
			// Util.formatarMoedaReal(valorTotalDebitoGuia));
			//
			// }

			//
			// 1.5. Dados da Remuneração da Cobrança Administrativa do Imóvel
			//

			// Percentual de Remuneração Padrão do Imóvel
			BigDecimal percentualRemuneracao = imovelCobrancaSituacao.getPercentualRemuneracao();

			if(percentualRemuneracao != null){
				String percentualRemuneracaoStr = Util.formataBigDecimal(percentualRemuneracao, 2, true);
				form.setPercentualRemuneracao(percentualRemuneracaoStr);
			}

			Collection<Object[]> colecaoImovelCobAdmTotalizado = fachada
							.totalizarImovelCobrancaAdmPelaSituacaoCobranca(idImovelCobrancaSituacao);

			if(!Util.isVazioOrNulo(colecaoImovelCobAdmTotalizado)){
				sessao.setAttribute("colecaoImovelCobAdmTotalizado", colecaoImovelCobAdmTotalizado);
			}

			// Contas
			Object[] arrayContasImovelCobrancaAdm = fachada.pesquisarContasImovelCobrancaAdmPelaSituacaoCobranca(idImovelCobrancaSituacao);

			Collection<CobrancaAdministrativaContaHelper> colecaoContasDocumentosRemunerados = (ArrayList<CobrancaAdministrativaContaHelper>) arrayContasImovelCobrancaAdm[0];

			if(!Util.isVazioOrNulo(colecaoContasDocumentosRemunerados)){
				sessao.setAttribute("colecaoContasDocumentosRemunerados", colecaoContasDocumentosRemunerados);
			}

			String valorTotalContasDocumentosRemunerados = (String) arrayContasImovelCobrancaAdm[1];
			sessao.setAttribute("valorTotalContasDocumentosRemunerados", valorTotalContasDocumentosRemunerados);

			String valorTotalRemuneracaoContaDocumentosRemunerados = (String) arrayContasImovelCobrancaAdm[2];
			sessao.setAttribute("valorTotalRemuneracaoContaDocumentosRemunerados", valorTotalRemuneracaoContaDocumentosRemunerados);

			// Guias
			Object[] arrayGuiasImovelCobrancaAdm = fachada.pesquisarGuiasImovelCobrancaAdmPelaSituacaoCobranca(idImovelCobrancaSituacao);

			Collection<CobrancaAdministrativaGuiaHelper> colecaoGuiasDocumentosRemunerados = (ArrayList<CobrancaAdministrativaGuiaHelper>) arrayGuiasImovelCobrancaAdm[0];

			if(!Util.isVazioOrNulo(colecaoGuiasDocumentosRemunerados)){
				sessao.setAttribute("colecaoGuiasDocumentosRemunerados", colecaoGuiasDocumentosRemunerados);
			}

			String valorTotalGuiasDocumentosRemunerados = (String) arrayGuiasImovelCobrancaAdm[1];
			sessao.setAttribute("valorTotalGuiasDocumentosRemunerados", valorTotalGuiasDocumentosRemunerados);

			String valorTotalRemuneracaoGuiaDocumentosRemunerados = (String) arrayGuiasImovelCobrancaAdm[2];
			sessao.setAttribute("valorTotalRemuneracaoGuiaDocumentosRemunerados", valorTotalRemuneracaoGuiaDocumentosRemunerados);

			// Debitos A Cobrar
			Object[] arrayDebitosImovelCobrancaAdm = fachada
							.pesquisarDebitoACobrarImovelCobrancaAdmPelaSituacaoCobranca(idImovelCobrancaSituacao);

			List<CobrancaAdministrativaHelper> colecaoDebitosDocumentosRemunerados = (ArrayList<CobrancaAdministrativaHelper>) arrayDebitosImovelCobrancaAdm[0];

			if(!Util.isVazioOrNulo(colecaoDebitosDocumentosRemunerados)){
				sessao.setAttribute("colecaoDebitosDocumentosRemunerados", colecaoDebitosDocumentosRemunerados);
			}

			String valorTotalDebitosDocumentosRemunerados = (String) arrayDebitosImovelCobrancaAdm[1];
			sessao.setAttribute("valorTotalDebitosDocumentosRemunerados", valorTotalDebitosDocumentosRemunerados);

			String valorTotalRemuneracaoDebitosDocumentosRemunerados = (String) arrayDebitosImovelCobrancaAdm[2];
			sessao.setAttribute("valorTotalRemuneracaoDebitosDocumentosRemunerados", valorTotalRemuneracaoDebitosDocumentosRemunerados);
		}

		try{
			String indRemunCobAdmPorComando = (String) ParametroCobranca.P_IND_REMUNERACAO_COBRANCA_ADMINISTRATIVA_POR_COMANDO.executar();
			sessao.setAttribute("indRemunCobAdmPorComando", indRemunCobAdmPorComando);
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		return retorno;
	}
}
