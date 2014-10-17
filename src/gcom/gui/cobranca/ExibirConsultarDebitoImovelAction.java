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
 * GSANPCG
 * Eduardo Henrique
 * Saulo Lima
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

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Fernanda Paiva
 * @created 18 de Janeiro de 2006
 * @author eduardo henrique
 * @date 12/08/2009
 *       Alteração para considerar Parcelas da Guia e exibí-las corretamente
 *       Aleração para permitir navegar para a tela , mesmo quando não houver débitos do imóvel.
 */
public class ExibirConsultarDebitoImovelAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirDebitoImovel");

		// Mudar isso quando tiver esquema de segurança
		// HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarDebitoImovelActionForm consultarDebitoImovelActionForm = (ConsultarDebitoImovelActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		// cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(true);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Limpa os indicadores de bloqueio
		sessao.removeAttribute("indicadorEmitirExtratoDebitos");
		sessao.removeAttribute("indicadorEmitirExtratoDebitosSelecao");
		sessao.removeAttribute("indicadorEmitirCertidaoNegativa");
		sessao.removeAttribute("indicadorEmitir2aViaConta");
		sessao.removeAttribute("indicadorGerarArquivoEOrdemCorte");
		sessao.removeAttribute("indicadorEmitirDeclaracaoDebito");
		sessao.removeAttribute("indicadorEmitirCertidaoQuitacaoAnual");

		try{
			if(ParametroCadastro.P_EMITE_AVISO_E_ORDEM_CORTE_INDIVIDUAL.executar().equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorEmiteAvisoEOrdemCorteIndividual", true);
			}else{
				sessao.removeAttribute("indicadorEmiteAvisoEOrdemCorteIndividual");
			}
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		String codigoImovel = (String) consultarDebitoImovelActionForm.getCodigoImovel();
		Integer tipoRelacao = null;

		if(consultarDebitoImovelActionForm.getTipoRelacao() != null){
			tipoRelacao = Integer.valueOf(consultarDebitoImovelActionForm.getTipoRelacao());
		}

		String referenciaInicial;
		String referenciaFinal;
		String dataVencimentoInicial;
		String dataVencimentoFinal;

		// seta os dados das datas digitadas ou informa datas padrão
		// no caso de não ter sido digitado nenhum dado para esses campos
		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getReferenciaInicial())){
			referenciaInicial = consultarDebitoImovelActionForm.getReferenciaInicial();
		}else{
			referenciaInicial = "01/0001";
		}

		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getReferenciaFinal())){
			referenciaFinal = consultarDebitoImovelActionForm.getReferenciaFinal();
		}else{
			referenciaFinal = "12/9999";
		}

		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getDataVencimentoInicial())){
			dataVencimentoInicial = consultarDebitoImovelActionForm.getDataVencimentoInicial();
		}else{
			dataVencimentoInicial = "01/01/0001";
		}

		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getDataVencimentoFinal())){
			dataVencimentoFinal = consultarDebitoImovelActionForm.getDataVencimentoFinal();
		}else{
			dataVencimentoFinal = "31/12/9999";
		}

		// Para auxiliar na formatação de uma data
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");
		String mesInicial = referenciaInicial.substring(0, 2);
		String anoInicial = referenciaInicial.substring(3, referenciaInicial.length());
		String anoMesInicial = anoInicial + mesInicial;
		String mesFinal = referenciaFinal.substring(0, 2);
		String anoFinal = referenciaFinal.substring(3, referenciaFinal.length());
		String anoMesFinal = anoFinal + mesFinal;

		Date dataVencimentoDebitoI;
		Date dataVencimentoDebitoF;

		try{
			dataVencimentoDebitoI = formatoData.parse(dataVencimentoInicial);
		}catch(ParseException ex){
			dataVencimentoDebitoI = null;
		}
		try{
			dataVencimentoDebitoF = formatoData.parse(dataVencimentoFinal);
		}catch(ParseException ex){
			dataVencimentoDebitoF = null;
		}

		// seta valores constantes para chamar o metodo que consulta debitos do imovel
		Integer tipoImovel = Integer.valueOf(1);
		Integer indicadorPagamento = Integer.valueOf(1);
		Integer indicadorConta = Integer.valueOf(1);
		Integer indicadorDebito = Integer.valueOf(1);
		Integer indicadorCredito = Integer.valueOf(1);
		Integer indicadorNotas = Integer.valueOf(1);
		Integer indicadorGuias = Integer.valueOf(1);
		Integer indicadorAtualizar = Integer.valueOf(1);

		// Obtendo dados do imovel
		if(!Util.isVazioOuBranco(codigoImovel) && Integer.parseInt(codigoImovel) > 0){

			FiltroImovel filtroImovel = new FiltroImovel();

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

			// pesquisa a coleção do imovel
			Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(!Util.isVazioOrNulo(colecaoImovel)){

				Imovel dadosImovel = (Imovel) ((List) colecaoImovel).get(0);

				// Seta os dados do imovel encontrado no formulario
				if(dadosImovel.getId() != null){
					consultarDebitoImovelActionForm.setCodigoImovel("" + dadosImovel.getId());
				}
				if(dadosImovel.getInscricaoFormatada() != null){
					consultarDebitoImovelActionForm.setInscricao("" + dadosImovel.getInscricaoFormatada());
				}
				if(dadosImovel.getLigacaoAguaSituacao() != null){
					consultarDebitoImovelActionForm.setLigacaoAgua("" + dadosImovel.getLigacaoAguaSituacao().getDescricao());
				}
				if(dadosImovel.getLigacaoEsgotoSituacao() != null){
					consultarDebitoImovelActionForm.setLigacaoEsgoto("" + dadosImovel.getLigacaoEsgotoSituacao().getDescricao());
				}

				// pesquisa dados da relacao do cliente com o imovel para
				// carregar na tela
				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel(FiltroClienteImovel.CLIENTE_NOME);
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, codigoImovel));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente.clienteTipo");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");

				// Faz a busca dos clientesimoveis
				Collection<ClienteImovel> clienteImoveis = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				if(!Util.isVazioOrNulo(clienteImoveis)){
					// Manda a colecao pelo request
					httpServletRequest.setAttribute("colecaoClienteImovel", clienteImoveis);
				}
			}else{

				throw new ActionServletException("atencao.imovel.inexistente");
			}

			String enderecoFormatado = "";
			try{
				enderecoFormatado = fachada.pesquisarEnderecoFormatado(Integer.valueOf(codigoImovel));
			}catch(NumberFormatException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(ControladorException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			httpServletRequest.setAttribute("enderecoFormatado", enderecoFormatado);
		}

		// Obtendo os débitos do imovel
		ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(tipoImovel.intValue(), codigoImovel,
						null, tipoRelacao, anoMesInicial, anoMesFinal, dataVencimentoDebitoI, dataVencimentoDebitoF, indicadorPagamento
										.intValue(), indicadorConta.intValue(), indicadorDebito.intValue(), indicadorCredito.intValue(),
						indicadorNotas.intValue(), indicadorGuias.intValue(), indicadorAtualizar.intValue(), null, null, new Date(),
						ConstantesSistema.SIM, null, ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM);

		Collection<ContaValoresHelper> colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();

		ContaValoresHelper dadosConta = null;

		BigDecimal valorConta = BigDecimal.ZERO;
		BigDecimal valorAcrescimo = BigDecimal.ZERO;
		BigDecimal valorAgua = BigDecimal.ZERO;
		BigDecimal valorEsgoto = BigDecimal.ZERO;
		BigDecimal valorDebito = BigDecimal.ZERO;
		BigDecimal valorCredito = BigDecimal.ZERO;
		BigDecimal valorImposto = BigDecimal.ZERO;

		if(!Util.isVazioOrNulo(colecaoContaValores)){
			java.util.Iterator<ContaValoresHelper> colecaoContaValoresIterator = colecaoContaValores.iterator();
			// percorre a colecao de conta somando o valor para obter um valor
			// total
			while(colecaoContaValoresIterator.hasNext()){

				dadosConta = (ContaValoresHelper) colecaoContaValoresIterator.next();

				// só considera contas vencidas

				if(dadosConta.getConta().getDataVencimentoConta() != null
								&& Util.compararData(dadosConta.getConta().getDataVencimentoConta(), new Date()) == -1){
					valorConta = valorConta.add(dadosConta.getConta().getValorTotal());
				}

				valorAcrescimo = valorAcrescimo.add(dadosConta.getValorTotalContaValores());
				valorAgua = valorAgua.add(dadosConta.getConta().getValorAgua());
				valorEsgoto = valorEsgoto.add(dadosConta.getConta().getValorEsgoto());
				valorDebito = valorDebito.add(dadosConta.getConta().getDebitos());
				valorCredito = valorCredito.add(dadosConta.getConta().getValorCreditos());
				valorImposto = valorImposto.add(dadosConta.getConta().getValorImposto());
			}
		}

		Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoImovel.getColecaoDebitoACobrar();

		BigDecimal valorDebitoACobrar = BigDecimal.ZERO;
		DebitoACobrar dadosDebito = null;

		if(!Util.isVazioOrNulo(colecaoDebitoACobrar)){
			java.util.Iterator<DebitoACobrar> colecaoDebitoACobrarIterator = colecaoDebitoACobrar.iterator();

			// percorre a colecao de debito a cobrar somando o valor para obter um valor total
			while(colecaoDebitoACobrarIterator.hasNext()){

				dadosDebito = (DebitoACobrar) colecaoDebitoACobrarIterator.next();
				valorDebitoACobrar = valorDebitoACobrar.add(dadosDebito.getValorTotal());
			}
		}

		Collection<CreditoARealizar> colecaoCreditoARealizar = colecaoDebitoImovel.getColecaoCreditoARealizar();

		BigDecimal valorCreditoARealizar = BigDecimal.ZERO;
		CreditoARealizar dadosCredito = null;

		if(!Util.isVazioOrNulo(colecaoCreditoARealizar)){
			java.util.Iterator<CreditoARealizar> colecaoCreditoARealizarIterator = colecaoCreditoARealizar.iterator();

			// percorre a colecao de credito a realizar somando o valor para obter um valor total
			while(colecaoCreditoARealizarIterator.hasNext()){

				dadosCredito = (CreditoARealizar) colecaoCreditoARealizarIterator.next();
				valorCreditoARealizar = valorCreditoARealizar.add(dadosCredito.getValorTotal());
			}
		}

		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoImovel.getColecaoGuiasPagamentoValores();

		BigDecimal valorGuiaPagamento = BigDecimal.ZERO;

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoValores)){

			Iterator<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperIterator = colecaoGuiaPagamentoValores.iterator();

			// Percorre a colecao de Prestações da Guia de Pagamento somando o valor para obter o
			// total em aberto
			while(colecaoGuiaPagamentoValoresHelperIterator.hasNext()){

				GuiaPagamentoValoresHelper dadosGuiaPagamentoValoresHelper = (GuiaPagamentoValoresHelper) colecaoGuiaPagamentoValoresHelperIterator
								.next();
				valorGuiaPagamento = valorGuiaPagamento.add(dadosGuiaPagamentoValoresHelper.getValorTotalPrestacao().setScale(
								Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
			}
		}

		// [SB0005 – Validar autorização de acesso ao imóvel pelos usuários das empresas
		// de cobrança administrativa]
		this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario, sessao, codigoImovel.trim(), colecaoDebitoImovel);

		// [SB0006 – Validar autorização de acesso ao imóvel em cobrança administrativa
		// pelos usuários da empresa contratante]
		this.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuario, sessao, codigoImovel.trim(), colecaoDebitoImovel);

		// [SB0007 – Verificar Débito em Cobrança Administrativa]
		this.verificarDebitoCobrancaAdministrativa(usuario, codigoImovel.trim(), colecaoContaValores, colecaoGuiaPagamentoValores);

		// Manda a colecao pelo request
		httpServletRequest.setAttribute("colecaoContaValores", colecaoContaValores);

		try{

			colecaoDebitoACobrar = Fachada.getInstancia().agruparDebitoACobrar(colecaoDebitoACobrar, Boolean.TRUE);
			String valorParametro = ParametroAtendimentoPublico.P_TRATAR_DEBITO_TIPO_PARCELAMENTO_AGRUPADO.executar();
			httpServletRequest.setAttribute(ConstantesSistema.DEBITOS_AGRUPADOS, valorParametro);

		}catch(ControladorException e){

			throw new ActionServletException("erro.sistema");

		}

		// Manda a colecao e os valores total de conta pelo request
		httpServletRequest.setAttribute("colecaoDebitoACobrar", colecaoDebitoACobrar);
		httpServletRequest.setAttribute("valorConta", Util.formatarMoedaReal(valorConta));
		httpServletRequest.setAttribute("valorAcrescimo", Util.formatarMoedaReal(valorAcrescimo));
		httpServletRequest.setAttribute("valorAgua", Util.formatarMoedaReal(valorAgua));
		httpServletRequest.setAttribute("valorEsgoto", Util.formatarMoedaReal(valorEsgoto));
		httpServletRequest.setAttribute("valorDebito", Util.formatarMoedaReal(valorDebito));
		httpServletRequest.setAttribute("valorCredito", Util.formatarMoedaReal(valorCredito));
		httpServletRequest.setAttribute("valorContaAcrescimo", Util.formatarMoedaReal(valorConta.add(valorAcrescimo)));
		httpServletRequest.setAttribute("valorImposto", Util.formatarMoedaReal(valorImposto));

		// Manda a colecao e o valor total de DebitoACobrar pelo request
		httpServletRequest.setAttribute("colecaoDebitoACobrar", colecaoDebitoACobrar);
		httpServletRequest.setAttribute("valorDebitoACobrar", Util.formatarMoedaReal(valorDebitoACobrar));

		// Manda a colecao e o valor total de CreditoARealizar pelo request
		httpServletRequest.setAttribute("colecaoCreditoARealizar", colecaoCreditoARealizar);
		httpServletRequest.setAttribute("valorCreditoARealizar", Util.formatarMoedaReal(valorCreditoARealizar));

		// Manda a colecao e o valor total de GuiaPagamento pelo request
		httpServletRequest.setAttribute("colecaoGuiaPagamentoValores", colecaoGuiaPagamentoValores);
		httpServletRequest.setAttribute("valorGuiaPagamento", Util.formatarMoedaReal(valorGuiaPagamento));

		// Soma o valor total dos debitos e subtrai dos creditos
		// BigDecimal valorTotalSemAcrescimo = valorConta.add(valorDebitoACobrar);

		BigDecimal valorTotalSemAcrescimo = valorConta.add(valorGuiaPagamento);
		valorTotalSemAcrescimo = valorTotalSemAcrescimo.subtract(valorCreditoARealizar);

		BigDecimal valorTotalComAcrescimo = valorTotalSemAcrescimo.add(valorAcrescimo);

		httpServletRequest.setAttribute("valorTotalSemAcrescimo", Util.formatarMoedaReal(valorTotalSemAcrescimo));
		httpServletRequest.setAttribute("valorTotalComAcrescimo", Util.formatarMoedaReal(valorTotalComAcrescimo));

		if(httpServletRequest.getAttribute("caminhoRetornoTelaConsultaDebito") != null){
			httpServletRequest.setAttribute("caminhoRetornoTelaConsultaDebito", httpServletRequest
							.getAttribute("caminhoRetornoTelaConsultaDebito"));
		}

		return retorno;
	}

	/**
	 * [SB0005] – Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 */
	private void validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(Usuario usuario, HttpSession sessao, String idImovel,
					ObterDebitoImovelOuClienteHelper colecaoDebitoImovel){

		Integer resposta = Fachada.getInstancia().validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario,
						Integer.valueOf(idImovel), colecaoDebitoImovel);

		if(resposta.equals(Integer.valueOf(1)) || resposta.equals(Integer.valueOf(3))){

			// 1.2.2.1.1.1. Emitir o extrato de débitos.
			sessao.setAttribute("indicadorEmitirExtratoDebitos", ConstantesSistema.SIM);
			sessao.setAttribute("indicadorEmitirExtratoDebitosSelecao", ConstantesSistema.SIM);
			// 1.2.2.1.1.2. Emitir certidão negativa.
			sessao.setAttribute("indicadorEmitirCertidaoNegativa", ConstantesSistema.SIM);
			// 1.2.2.1.1.3. Emitir segunda via da conta.
			sessao.setAttribute("indicadorEmitir2aViaConta", ConstantesSistema.SIM);
			// 1.2.2.1.1.4. Efetuar Parcelamento.
			sessao.setAttribute("indicadorEfetuarParcelamento", ConstantesSistema.SIM);
			// 1.2.2.1.1.5. Gerar o aviso de corte e a ordem de corte.
			sessao.setAttribute("indicadorGerarArquivoEOrdemCorte", ConstantesSistema.SIM);
			// 1.2.2.1.1.6. Emitir Declaração de Débito.
			sessao.setAttribute("indicadorEmitirDeclaracaoDebito", ConstantesSistema.SIM);
			// 1.2.2.1.1.7. Emitir Certidão de Quitação Anual.
			sessao.setAttribute("indicadorEmitirCertidaoQuitacaoAnual", ConstantesSistema.SIM);

		}else if(resposta.equals(Integer.valueOf(2)) || resposta.equals(Integer.valueOf(4))){
			// 1.3.1. Bloquear as opções:
			// 1.3.1.1. Emitir certidão negativa.
			sessao.setAttribute("indicadorEmitirCertidaoNegativa", ConstantesSistema.SIM);
			// 1.3.1.2. Gerar o aviso de corte e a ordem de corte.
			sessao.setAttribute("indicadorGerarArquivoEOrdemCorte", ConstantesSistema.SIM);
			// 1.3.1.3. Emitir Declaração de Débito.
			sessao.setAttribute("indicadorEmitirDeclaracaoDebito", ConstantesSistema.SIM);
			// 1.3.1.4. Emitir Certidão de Quitação Anual.
			sessao.setAttribute("indicadorEmitirCertidaoQuitacaoAnual", ConstantesSistema.SIM);

			// 1.3.2. Liberar as opções:
			// 1.3.2.1. Emitir o extrato de débitos.
			sessao.removeAttribute("indicadorEmitirExtratoDebitos");
			sessao.removeAttribute("indicadorEmitirExtratoDebitosSelecao");
			// 1.3.2.2. Emitir segunda via da conta.
			sessao.removeAttribute("indicadorEmitir2aViaConta");
			// 1.2.2.2.2.3. Efetuar Parcelamento.
			sessao.removeAttribute("indicadorEfetuarParcelamento");
		}
	}

	/**
	 * [SB0006] – Validar autorização de acesso ao imóvel em cobrança administrativa pelos usuários
	 * da empresa contratante
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @author Saulo Lima
	 * @date 29/07/2013
	 * @param usuario
	 * @param sessao
	 * @param idImovel
	 * @param colecaoDebitoImovel
	 */
	private void validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(Usuario usuario, HttpSession sessao, String idImovel,
					ObterDebitoImovelOuClienteHelper colecaoDebitoImovel){

		Integer permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante = Fachada.getInstancia()
						.validarAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante(usuario, Integer.valueOf(idImovel),
										colecaoDebitoImovel);

		if(permiteAcessoImovelEmCobrancaAdministrativaUsuarioEmpresaContratante != null){
			// 1.1.2.1.1. Emitir o extrato de débitos.
			sessao.setAttribute("indicadorEmitirExtratoDebitos", ConstantesSistema.SIM);
			sessao.setAttribute("indicadorEmitirExtratoDebitosSelecao", ConstantesSistema.SIM);
			// 1.1.2.1.2. Emitir certidão negativa.
			sessao.setAttribute("indicadorEmitirCertidaoNegativa", ConstantesSistema.SIM);
			// 1.1.2.1.3. Efetuar Parcelamento.
			sessao.setAttribute("indicadorEfetuarParcelamento", ConstantesSistema.SIM);
			// 1.1.2.1.4. Gerar o aviso de corte e a ordem de corte.
			sessao.setAttribute("indicadorGerarArquivoEOrdemCorte", ConstantesSistema.SIM);
			// 1.1.2.1.5. Emitir Declaração de Débito.
			sessao.setAttribute("indicadorEmitirDeclaracaoDebito", ConstantesSistema.SIM);
			// 1.1.2.1.6. Emitir Certidão de Quitação Anual.
			sessao.setAttribute("indicadorEmitirCertidaoQuitacaoAnual", ConstantesSistema.SIM);

			// 1.1.2.1.x. Emitir segunda via da conta.
			// sessao.setAttribute("indicadorEmitir2aViaConta", ConstantesSistema.SIM);
		}

	}
	
	/**
	 * [SB0007] – Verificar Débito em Cobrança Administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @param usuario
	 * @param idImovel
	 * @param colecaoContasValoresHelper
	 * @param colecaoGuiaPagamentoValoresHelper
	 */
	private void verificarDebitoCobrancaAdministrativa(Usuario usuario, String idImovel,
					Collection<ContaValoresHelper> colecaoContasValoresHelper,
					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelper){

		Fachada.getInstancia().removerContaCobrancaAdministrativaDebitoImovel(usuario, Integer.valueOf(idImovel),
						colecaoContasValoresHelper);
		Fachada.getInstancia().removerGuiaPagamentoCobrancaAdministrativaDebitoImovel(usuario, Integer.valueOf(idImovel),
						colecaoGuiaPagamentoValoresHelper);

	}
}
