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

package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cobranca.CobrancaDocumento;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.DebitoCreditoParcelamentoHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.credito.FiltroCreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0630]Solicitar Emissão do Extrato de Débitos
 * Esta classe tem por finalidade exibir para o usuário a tela que exibirá
 * as contas, débitos, créditos e guias para seleção e posteriormente
 * emissao do extrato de débito dos selecionados
 * 
 * @author Vivianne Sousa
 * @date 02/08/2007
 */
public class ExibirDebitoCreditoDadosSelecaoExtratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("debitoCreditoDadosSelecaoExtrato");

		DebitoCreditoDadosSelecaoExtratoActionForm form = (DebitoCreditoDadosSelecaoExtratoActionForm) actionForm;

		Boolean indicadorFauramentoTitularDebito = false;
		try{
			if(ParametroFaturamento.P_INDICADOR_FATURAMENTO_ATUAL_TITULAR_DEBITO_IMOVEL.executar().equals("1")){
				indicadorFauramentoTitularDebito = true;
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}

		if("sim".equalsIgnoreCase(httpServletRequest.getParameter("popup"))){
			httpServletRequest.setAttribute("popup", "sim");
		}

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		Fachada fachada = Fachada.getInstancia();
		String pIndicadorEmissaoExtratoDebitoSemAcrescimo;

		try{
			pIndicadorEmissaoExtratoDebitoSemAcrescimo = ParametroCobranca.P_INDICADOR_EMISSAO_EXTRATO_DEBITO_SEM_ACRESCIMOS
							.executar();
			httpServletRequest.setAttribute("indicadorEmissaoExtratoDebitoSemAcrescimo", pIndicadorEmissaoExtratoDebitoSemAcrescimo);
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_INDICADOR_EMISSAO_EXTRATO_DEBITO_SEM_ACRESCIMOS");
		}

		boolean temPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto = fachada
						.verificarPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto(usuarioLogado);
		httpServletRequest.setAttribute("temPermissaoIncluirAcrescExtratoComDesconto",
						temPermissaoIncluirAcrescimoImpontualidadeNoExtratoDeDebitosComDesconto);

		boolean temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos = false;
		Short indicadorCobrarTaxaExtrato = fachada.pesquisarParametrosDoSistema().getIndicadorCobrarTaxaExtrato();

		if(indicadorCobrarTaxaExtrato.equals(ConstantesSistema.SIM)){
			temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos = fachada
							.verificarPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos(usuarioLogado);
			httpServletRequest.setAttribute("temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos",
							temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos);
		}

		if(httpServletRequest.getParameter("menu") == null){
			httpServletRequest.setAttribute("habilitaIncluirDebito", 1);
		}else{
			if(temPermissaoRetirarTaxaCobrancaDoExtratoDeDebitos){
				form.setIndicadorTaxaCobranca(ConstantesSistema.SIM.toString());
			}else{
				form.setIndicadorTaxaCobranca(ConstantesSistema.NAO.toString());
			}
		}

		if("sim".equalsIgnoreCase(httpServletRequest.getParameter("popup"))){
			httpServletRequest.setAttribute("popup", "sim");
		}
		if(httpServletRequest.getParameter("reloadPage") == null){
			String idImovel;
			if(httpServletRequest.getParameter("imovel") != null){
				idImovel = httpServletRequest.getParameter("imovel");
				form.setIdImovel(idImovel);
			}else{
				idImovel = form.getIdImovel();
			}

			sessao.removeAttribute("colecaoContaExtrato");
			sessao.removeAttribute("colecaoDebitoACobrarExtrato");
			sessao.removeAttribute("colecaoCreditoARealizarExtrato");
			sessao.removeAttribute("colecaoGuiaPagamentoExtrato");
			sessao.removeAttribute("colecaoDebitoCreditoParcelamento");
			sessao.removeAttribute("idImovel");
			// ------------------------
			if(pIndicadorEmissaoExtratoDebitoSemAcrescimo.equals("2")){
				form.setIndicadorIncluirAcrescimosImpontualidade(CobrancaDocumento.INCLUIR_ACRESCIMOS);
			}else{
				form.setIndicadorIncluirAcrescimosImpontualidade(CobrancaDocumento.NAO_INCLUIR_ACRESCIMOS);
			}
			// ------------------------
			httpServletRequest.removeAttribute("habilitaIncluirDebito");

			if(idImovel != null && !idImovel.equals("")){

				String inscricaoImovel = fachada.pesquisarInscricaoImovel(Integer.valueOf(idImovel), true);

				if(inscricaoImovel == null){
					form.setInscricaoImovel("IMOVEL INEXISTENTE");
					httpServletRequest.setAttribute("corImovel", "exception");
					httpServletRequest.setAttribute("nomeCampo", "idImovel");
					form.setDescricaoLigacaoAguaSituacaoImovel("");
					form.setDescricaoLigacaoEsgotoSituacaoImovel("");
					form.setNomeClienteUsuarioImovel("");

					Collection<ClienteImovel> colecaoRelacaoImovel = new ArrayList<ClienteImovel>();
					sessao.setAttribute("colecaoRelacaoImovel", colecaoRelacaoImovel);
				}else{

					// INSCRICAO IMOVEL
					form.setInscricaoImovel(inscricaoImovel);
					httpServletRequest.setAttribute("nomeCampo", "idImovel");
					sessao.setAttribute("idImovel", idImovel);

					// CLIENTE USUARIO DO IMOVEL
					Cliente cliente = fachada.pesquisarClienteUsuarioImovel(Integer.valueOf(idImovel));
					form.setNomeClienteUsuarioImovel(cliente.getNome());
					// sessao.setAttribute("nomeCliente",cliente.getNome());

					// LIGACAO AGUA SITUACAO
					LigacaoAguaSituacao ligacaoAguaSituacao = fachada.pesquisarLigacaoAguaSituacao(Integer.valueOf(idImovel));
					form.setDescricaoLigacaoAguaSituacaoImovel(ligacaoAguaSituacao.getDescricao());

					// LIGACAO ESGOTO SITUACAO
					LigacaoEsgotoSituacao ligacaoEsgotoSituacao = fachada.pesquisarLigacaoEsgotoSituacao(Integer.valueOf(idImovel));
					form.setDescricaoLigacaoEsgotoSituacaoImovel(ligacaoEsgotoSituacao.getDescricao());

					this.preencheTela(httpServletRequest, form, sessao, usuarioLogado, fachada, idImovel);

					this.preencheListas(httpServletRequest, form, sessao);
				}

			}

		}else{

			this.preencheListas(httpServletRequest, form, sessao);

		}

		/**
		 * [UC0630] Solicitar Emissão do Extrato de Débitos
		 * [SB0007] Verificar Existência de Conta em Execução Fiscal
		 * 
		 * @author Gicevalter Couto
		 * @date 06/08/2014
		 */
		boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));

		Short indicadorExecFiscal;
		indicadorExecFiscal = fachada.verificarImovelDebitoExecucaoFiscal(
						(Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar"),
						(Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoValores"),
						(Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores"));

		if(!temPermissaoAtualizarDebitosExecFiscal && indicadorExecFiscal.equals(Short.valueOf("1"))){
			throw new ActionServletException("atencao.imprimir.imovel.possui.debito.execucao.fiscal");
		}

		if(indicadorFauramentoTitularDebito){
			httpServletRequest.setAttribute("indicadorFauramentoTitularDebito", "S");
		}else{
			httpServletRequest.removeAttribute("indicadorFauramentoTitularDebito");
		}

		return retorno;
	}

	/**
	 * @param httpServletRequest
	 * @param form
	 * @param sessao
	 */
	private void preencheListas(HttpServletRequest httpServletRequest, DebitoCreditoDadosSelecaoExtratoActionForm form, HttpSession sessao){

		String[] idsContas = httpServletRequest.getParameterValues("conta");
		form.setIdsConta(Arrays.toString(idsContas).replace("[", "").replace("]", ""));

		String[] idsDebitos = httpServletRequest.getParameterValues("debito");
		if(sessao.getAttribute("idDebitoACobrarInserido") != null){
			if(idsDebitos == null){
				form.setIdsDebito((String) sessao.getAttribute("idDebitoACobrarInserido"));
			}else{
				String debitos = Arrays.toString(idsDebitos).replace("[", "").replace("]", "");
				debitos = debitos + "," + (String) sessao.getAttribute("idDebitoACobrarInserido");
				form.setIdsDebito(debitos);
			}
		}else{
			form.setIdsDebito(Arrays.toString(idsDebitos).replace("[", "").replace("]", ""));
		}

		String[] idsCreditos = httpServletRequest.getParameterValues("credito");
		form.setIdsCredito(Arrays.toString(idsCreditos).replace("[", "").replace("]", ""));

		String[] idsguias = httpServletRequest.getParameterValues("guiaPagamento");
		form.setIdsGuia(Arrays.toString(idsguias).replace("[", "").replace("]", ""));

		String[] idsParcelamentos = httpServletRequest.getParameterValues("parcelamento");
		form.setIdsParcelamento(Arrays.toString(idsParcelamentos).replace("[", "").replace("]", ""));
	}

	/**
	 * @param httpServletRequest
	 * @param form
	 * @param sessao
	 * @param usuarioLogado
	 * @param fachada
	 * @param idImovel
	 * @param inscricaoImovel
	 * @throws NumberFormatException
	 * @throws ActionServletException
	 */
	private void preencheTela(HttpServletRequest httpServletRequest, DebitoCreditoDadosSelecaoExtratoActionForm form, HttpSession sessao,
					Usuario usuarioLogado, Fachada fachada, String idImovel) throws NumberFormatException,
					ActionServletException{

		Short indicadorMulta = ConstantesSistema.SIM;
		Short indicadorJurosMora = ConstantesSistema.SIM;
		Short indicadorAtualizaoMonetaria = ConstantesSistema.SIM;

		if(Util.isNaoNuloBrancoZero(form.getIndicadorMulta()) && form.getIndicadorMulta().equals(ConstantesSistema.NAO.toString())){
			indicadorMulta = ConstantesSistema.NAO;
		}

		if(Util.isNaoNuloBrancoZero(form.getIndicadorJurosMora()) && form.getIndicadorJurosMora().equals(ConstantesSistema.NAO.toString())){
			indicadorJurosMora = ConstantesSistema.NAO;
		}

		if(Util.isNaoNuloBrancoZero(form.getIndicadorAtualizacaoMonetaria())
						&& form.getIndicadorAtualizacaoMonetaria().equals(ConstantesSistema.NAO.toString())){
			indicadorAtualizaoMonetaria = ConstantesSistema.NAO;
		}

		httpServletRequest.setAttribute("indicadorMulta", indicadorMulta);
		httpServletRequest.setAttribute("indicadorJurosMora", indicadorJurosMora);
		httpServletRequest.setAttribute("indicadorAtualizacaoMonetaria", indicadorAtualizaoMonetaria);

		if(Util.isVazioOuBranco(form.getPermitirSelecaoAcrescimoExtrato())){
			try{
				String permitirSelecaoAcrescimoExtrato = ParametroCobranca.P_PERMITIR_SELECAO_ACRESCIMOS_EXTRATO.executar();
				form.setPermitirSelecaoAcrescimoExtrato(permitirSelecaoAcrescimoExtrato);
			}catch(ControladorException e){
				throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_PERMITIR_SELECAO_ACRESCIMOS_EXTRATO");
			}
			if(form.getPermitirSelecaoAcrescimoExtrato().equals(ConstantesSistema.SIM.toString())){
				indicadorJurosMora = ConstantesSistema.NAO;
				indicadorAtualizaoMonetaria = ConstantesSistema.NAO;
				httpServletRequest.setAttribute("indicadorJurosMora", indicadorJurosMora);
				httpServletRequest.setAttribute("indicadorAtualizacaoMonetaria", indicadorAtualizaoMonetaria);
			}
		}

		Integer idClienteDebito = null;
		Integer idRelacaoClienteDebito = null;

		if(form.getIdClienteRelacaoImovelSelecionado() != null && !form.getIdClienteRelacaoImovelSelecionado().equals("")){
			String[] dados = form.getIdClienteRelacaoImovelSelecionado().split("\\.");

			idClienteDebito = Integer.valueOf(dados[1]);
			idRelacaoClienteDebito = Integer.valueOf(dados[0]);
		}

		Collection<ClienteImovel> colecaoRelacaoImovel = fachada.obterListaClientesRelacaoDevedor(Integer.valueOf(idImovel),
						Integer.valueOf("000101"), Integer.valueOf("999912"), 1, 1, 1, 1, 1, 1, 1, null, indicadorMulta,
						indicadorJurosMora, indicadorAtualizaoMonetaria, 2, null, null);
		sessao.setAttribute("colecaoRelacaoImovel", colecaoRelacaoImovel);

		// [SB0001] - Apresentar Débitos/Créditos do Imóvel de Origem
		ObterDebitoImovelOuClienteHelper obterDebitoImovelOuClienteHelper = fachada.apresentarDebitoCreditoImovelExtratoDebito(
						Integer.valueOf(idImovel), idClienteDebito, idRelacaoClienteDebito, false, indicadorMulta, indicadorJurosMora,
						indicadorAtualizaoMonetaria);

		// [SB0001] – Validar autorização de acesso ao imóvel pelos usuários das empresas de
		// cobrança administrativa
		// [SB0002] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos
		// usuários da empresa contratante
		Boolean impressaoPermitida = fachada.isImpressaoExtratoDebitoLiberada(usuarioLogado, Integer.valueOf(idImovel),
						obterDebitoImovelOuClienteHelper);

		fachada.removerDebitosCobAdministrativaEmpresaDiversas(usuarioLogado, Integer.valueOf(idImovel),
						obterDebitoImovelOuClienteHelper);

		// CONTA
		this.habilitarContasComPermissaoImpressao(obterDebitoImovelOuClienteHelper, httpServletRequest);
		sessao.setAttribute("colecaoContaExtrato", obterDebitoImovelOuClienteHelper.getColecaoContasValoresImovel());

		// DEBITO_A_COBRAR
		sessao.setAttribute("colecaoDebitoACobrarExtrato", obterDebitoImovelOuClienteHelper.getColecaoDebitoACobrar());

		// CREDITO_A_REALIZAR
		sessao.setAttribute("colecaoCreditoARealizarExtrato", obterDebitoImovelOuClienteHelper.getColecaoCreditoARealizar());

		// GUIA_PAGAMENTO
		Collection<GuiaPagamentoValoresHelper> colecaoGuiasPagamentoValores = obterDebitoImovelOuClienteHelper
						.getColecaoGuiasPagamentoValores();

		// [FS0004] - Verificar existência de guia de parcelamento de cobrança bancária
		if(!Util.isVazioOrNulo(colecaoGuiasPagamentoValores)){
			Integer idGuiaPagamento = null;
			Short numeroPrestacao = null;

			boolean existeGuiaParcelamento = false;

			for(GuiaPagamentoValoresHelper guiaPagamentoValoresHelper : colecaoGuiasPagamentoValores){
				idGuiaPagamento = guiaPagamentoValoresHelper.getIdGuiaPagamento();
				numeroPrestacao = guiaPagamentoValoresHelper.getNumeroPrestacao();

				existeGuiaParcelamento = fachada.verificarGuiaPagamentoParcelamentoCobrancaBancaria(idGuiaPagamento,
								numeroPrestacao);

				// existeGuiaParcelamento =
				// fachada.verificarGuiaPagamentoParcelamentoCobrancaBancariaComBoletoGeradoValido(idGuiaPagamento,
				// numeroPrestacao);

				// if(!existeGuiaParcelamento){
				// existeGuiaParcelamento =
				// fachada.verificarGuiaPagamentoParcelamentoCobrancaBancariaPendentesGeracaoBoleto(
				// idGuiaPagamento, numeroPrestacao);
				// }

				if(existeGuiaParcelamento){
					// Desabilitar a seleção da guia de pagamento
					guiaPagamentoValoresHelper.setDesativarSelecao(ConstantesSistema.SIM);
				}
			}
		}

		sessao.setAttribute("colecaoGuiaPagamentoExtrato", colecaoGuiasPagamentoValores);

		// PARCELAMENTO
		boolean procurar = true;
		if(obterDebitoImovelOuClienteHelper.getColecaoDebitoCreditoParcelamentoHelper() != null){
			for(DebitoCreditoParcelamentoHelper helper : obterDebitoImovelOuClienteHelper
							.getColecaoDebitoCreditoParcelamentoHelper()){
				if(helper.getColecaoDebitoACobrarParcelamento() != null
								&& !helper.getColecaoDebitoACobrarParcelamento().isEmpty()){
					procurar = true;
					Iterator<DebitoACobrar> iterator = helper.getColecaoDebitoACobrarParcelamento().iterator();
					while(iterator.hasNext() && procurar){

						DebitoACobrar debitoACobrarTMP = iterator.next();
						if(!debitoACobrarTMP.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

							Integer totalParcelas = debitoACobrarTMP.getNumeroPrestacaoDebito()
											- debitoACobrarTMP.getNumeroPrestacaoCobradas();
							Collection<Integer> parcelasAberto = new ArrayList<Integer>();
							for(Integer i = totalParcelas - 1; i > 0; i--){
								parcelasAberto.add(i);
							}
							helper.setQuantidadeParcelasAberto(parcelasAberto);
							procurar = false;
						}
					}
				}
			}
		}
		if(!procurar){
			sessao.setAttribute("colecaoDebitoCreditoParcelamento", obterDebitoImovelOuClienteHelper
							.getColecaoDebitoCreditoParcelamentoHelper());
		}

		httpServletRequest.setAttribute("habilitaIncluirDebito", 1);

		httpServletRequest.setAttribute("impressaoPermitida", impressaoPermitida.equals(Boolean.TRUE) ? "1" : "0");

	}
	
	/**
	 * [UC0630][SB0005] – Verificar créditos de descontos nos acréscimos por impontualidade
	 * 
	 * @param creditoARealizar
	 * @return
	 */
	private Boolean isCreditoCorrespondenteDescontoAcrescimosPorImpontualidade(CreditoARealizar creditoARealizar){

		Boolean retorno = Boolean.FALSE;

		if(creditoARealizar.getCreditoTipo().getId().equals(CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE)){

			retorno = Boolean.TRUE;

		}

		return retorno;

	}
	
	/**
	 * [UC0630][SB0006] – Verificar parcelamento com descontos nos acréscimos por impontualidade
	 * 
	 * @param debitoCreditoParcelamentoHelper
	 * @return
	 */
	private Boolean isParcelamentoRealizadoConcessaoDescontoAcrescimoPorImpontualidade(
					DebitoCreditoParcelamentoHelper debitoCreditoParcelamentoHelper){

		Boolean retorno = Boolean.FALSE;

		Parcelamento parcelamento = debitoCreditoParcelamentoHelper.getParcelamento();

		if(parcelamento != null){

			FiltroCreditoARealizar filtro = new FiltroCreditoARealizar();
			filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.PARCELAMENTO_ID, parcelamento.getId()));
			filtro.adicionarParametro(new ParametroSimples(FiltroCreditoARealizar.ID_CREDITO_TIPO,
							CreditoTipo.DESCONTO_ACRESCIMOS_IMPONTUALIDADE));

			Collection<CreditoARealizar> coll = Fachada.getInstancia().pesquisar(filtro, CreditoARealizar.class.getName());

			if(!coll.isEmpty()){

				retorno = Boolean.TRUE;

			}

		}

		return retorno;

	}
	
	/**
	 * [UC0630] Solicitar Emissão do Extrato de Débitos
	 * [FS0003] - Verificar permissão para inclusão de contas em revisão por Cobrança Bancária
	 * 
	 * @author Anderson Italo
	 * @date 24/04/2012
	 */
 	private void habilitarContasComPermissaoImpressao(ObterDebitoImovelOuClienteHelper helper, HttpServletRequest request){

		Collection idsContasPermitidas = new ArrayList();

		if(!Util.isVazioOrNulo(helper.getColecaoContasValoresImovel())){

			Iterator iteratorColecaoContas = helper.getColecaoContasValoresImovel().iterator();

			while(iteratorColecaoContas.hasNext()){

				ContaValoresHelper contaValoresHelper = (ContaValoresHelper) iteratorColecaoContas.next();
				idsContasPermitidas.add(contaValoresHelper.getConta().getId());
			}

			// [FS0003 – Verificar permissão para inclusão de contas em revisão por Cobrança
			// Bancária]
			Fachada.getInstancia().verificarPermissaoInclusaoContasRevisaoCobrancaBancaria(idsContasPermitidas, getUsuarioLogado(request));

			if(!Util.isVazioOrNulo(idsContasPermitidas)){

				for(ContaValoresHelper contaValorHelper : helper.getColecaoContasValoresImovel()){

					if(idsContasPermitidas.contains(contaValorHelper.getConta().getId())){

						contaValorHelper.setContaPermitida(ConstantesSistema.SIM);

					}else{

						contaValorHelper.setContaPermitida(ConstantesSistema.NAO);
					}
				}
			}else{

				for(ContaValoresHelper contaValorHelper : helper.getColecaoContasValoresImovel()){

					contaValorHelper.setContaPermitida(ConstantesSistema.NAO);
				}
			}
		}
	}
}
