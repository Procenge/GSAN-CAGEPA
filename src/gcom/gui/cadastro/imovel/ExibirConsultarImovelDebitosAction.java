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
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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

package gcom.gui.cadastro.imovel;

import gcom.atendimentopublico.registroatendimento.bean.ObterIndicadorExistenciaHidrometroHelper;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.ObterDebitoImovelOuClienteHelper;
import gcom.cobranca.parcelamento.Parcelamento;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.credito.CreditoOrigem;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0473] Consultar Imovel
 * Metodo da 4° Aba - Débitos do Imóvel
 * 
 * @author Rafael Santos
 * @since 14/09/2006
 */
public class ExibirConsultarImovelDebitosAction
				extends GcomAction
				implements ExecutorParametro {

	private static final String NUMERO_PRESTACAO_DEBITO = "numeroPrestacaoDebito";

	private static final String NUMERO_PRESTACAO_COBRADAS = "numeroPrestacaoCobradas";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("consultarImovelDebitos");

		// cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(true);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		Boolean indicadorFauramentoTitularDebito = false;
		try{
			if(ParametroFaturamento.P_INDICADOR_FATURAMENTO_ATUAL_TITULAR_DEBITO_IMOVEL.executar().equals("1")){
				indicadorFauramentoTitularDebito = true;
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}

		Collection<ClienteImovel> colecaoRelacaoImovel = new ArrayList<ClienteImovel>();

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Limpa os indicadores de bloqueio
		sessao.removeAttribute("indicadorEmitirExtratoDebitos");
		sessao.removeAttribute("indicadorEmitirExtratoDebitosSelecao");
		sessao.removeAttribute("indicadorEmitirCertidaoNegativa");
		sessao.removeAttribute("indicadorEmitir2aViaConta");
		sessao.removeAttribute("indicadorGerarArquivoEOrdemCorte");
		sessao.removeAttribute("indicadorEmitirDeclaracaoDebito");
		sessao.removeAttribute("indicadorEmitirCertidaoQuitacaoAnual");
		sessao.removeAttribute("indicadorEfetuarParcelamento");

		// recupera a flag de limpar o form
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;
		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}


		int quantidadeContasRevisao = 0;
		Collection<ContaValoresHelper> colecaoContaValores = new ArrayList();
		Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacaoAberto = new ArrayList();
		
		BigDecimal valorTotalSemAcrescimo = BigDecimal.ZERO;

		BigDecimal valorConta = BigDecimal.ZERO;


		/*
		 * Pesquisar o imóvel a partir da matrícula do imóvel informada na página
		 * ======================================================================
		 */
		// recupera o código do imóvel
		String idImovelDebitos = consultarImovelActionForm.getIdImovelDebitos();

		if(limparForm != null && !limparForm.equals("")){

			// limpar os dados
			httpServletRequest.setAttribute("idImovelDebitosNaoEncontrado", null);

			sessao.removeAttribute("imovelDebitos");
			sessao.removeAttribute("colecaoContaValores");
			sessao.removeAttribute("colecaoImovelCobrancaSituacaoAberto");
			sessao.removeAttribute("idImovelPrincipalAba");
			sessao.removeAttribute("imovelClientes");

			// Manda a colecao e os valores total de conta pelo request
			sessao.removeAttribute("colecaoDebitoACobrar");
			sessao.removeAttribute("valorConta");
			sessao.removeAttribute("valorAcrescimo");
			sessao.removeAttribute("valorAgua");
			sessao.removeAttribute("valorEsgoto");
			sessao.removeAttribute("valorDebito");
			sessao.removeAttribute("valorCredito");
			sessao.removeAttribute("valorImposto");
			sessao.removeAttribute("valorContaAcrescimo");

			// Manda a colecao e o valor total de DebitoACobrar pelo request
			sessao.removeAttribute("colecaoDebitoACobrar");
			sessao.removeAttribute("valorDebitoACobrar");

			// Manda a colecao e o valor total de CreditoARealizar pelo request
			sessao.removeAttribute("colecaoCreditoARealizar");
			sessao.removeAttribute("valorCreditoARealizar");
			sessao.removeAttribute("valorCreditoARealizarSemDescontosParcelamento");

			// Manda a colecao e o valor total de GuiaPagamento pelo request
			sessao.removeAttribute("colecaoGuiaPagamentoValores");
			sessao.removeAttribute("valorGuiaPagamento");

			sessao.removeAttribute("valorTotalSemAcrescimo");
			sessao.removeAttribute("valorTotalComAcrescimo");
			sessao.removeAttribute("valorToralSemAcrescimoESemJurosParcelamento");

			consultarImovelActionForm.setIdImovelDadosComplementares(null);
			consultarImovelActionForm.setIdImovelDadosCadastrais(null);
			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setIdImovelDebitos(null);
			consultarImovelActionForm.setIdImovelPagamentos(null);
			consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
			consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
			consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setIdImovelRegistroAtendimento(null);

			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);

			consultarImovelActionForm.setIdImovelDebitos(null);
			consultarImovelActionForm.setMatriculaImovelDebitos(null);
			consultarImovelActionForm.setDigitoVerificadorImovelDebitos(null);
			consultarImovelActionForm.setSituacaoEsgotoDebitos(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setSituacaoAguaDebitos(null);
			consultarImovelActionForm.setCodigoImovel(null);
			consultarImovelActionForm.setTipoRelacao(null);
			consultarImovelActionForm.setReferenciaInicial(null);
			consultarImovelActionForm.setReferenciaFinal(null);
			consultarImovelActionForm.setDataVencimentoInicial(null);
			consultarImovelActionForm.setDataVencimentoFinal(null);
			consultarImovelActionForm.setLigacaoAgua(null);
			consultarImovelActionForm.setLigacaoEsgoto(null);
			consultarImovelActionForm.setMaticula(null);
			consultarImovelActionForm.setInscricao(null);
			consultarImovelActionForm.setNomeCliente(null);
			consultarImovelActionForm.setTipoRelacaoCliente(null);
			consultarImovelActionForm.setCpf(null);
			consultarImovelActionForm.setCnpj(null);
			consultarImovelActionForm.setRefInicial(null);
			consultarImovelActionForm.setRefFinal(null);
			consultarImovelActionForm.setDtInicial(null);
			consultarImovelActionForm.setDtFinal(null);

			if(indicadorNovo == null || indicadorNovo.equals("")){
				idImovelDebitos = null;
				idImovelPrincipalAba = null;
			}

		}
		if((idImovelDebitos != null && !idImovelDebitos.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelDebitos != null && !idImovelDebitos.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){

						consultarImovelActionForm.setIdImovelDebitos(idImovelDebitos);

					}else if(!(idImovelDebitos.equals(idImovelPrincipalAba))){

						consultarImovelActionForm.setIdImovelDebitos(idImovelPrincipalAba);
						idImovelDebitos = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){
				consultarImovelActionForm.setIdImovelRegistroAtendimento(idImovelPrincipalAba);
				idImovelDebitos = idImovelPrincipalAba;
			}

			if(idImovelDebitos != null && !idImovelDebitos.equalsIgnoreCase("")){
				// pesquisar cliente do imovel
				Collection clientesImovel = fachada.pesquisarClientesImovel(Integer.valueOf(idImovelDebitos.trim()));
				sessao.setAttribute("imovelClientes", clientesImovel);
			}

			// Obtém a instância da Fachada
			Imovel imovel = null;

			// verifica se o objeto imovel é o mesmo ja pesquisado, para não precisar pesquisar mas.
			boolean imovelNovoPesquisado = false;

			if(sessao.getAttribute("imovelDebitos") != null){

				imovel = (Imovel) sessao.getAttribute("imovelDebitos");
				if(!(imovel.getId().toString().equals(idImovelDebitos.trim()))){
					imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelDebitos.trim()));
					imovelNovoPesquisado = true;
				}

			}else{
				imovel = fachada.consultarImovelHistoricoFaturamento(Integer.valueOf(idImovelDebitos.trim()));
				imovelNovoPesquisado = true;
			}

			if(imovel != null){

				if(sessao.getAttribute("idImovelDebitoAnterior") == null){
					consultarImovelActionForm.setIndicadorPrimeiroAcessoImovelDebito("1");
					sessao.setAttribute("idImovelDebitoAnterior", imovel.getId().toString());
				}else{
					if(sessao.getAttribute("idImovelDebitoAnterior").toString().compareTo(imovel.getId().toString()) != 0){
						consultarImovelActionForm.setIndicadorPrimeiroAcessoImovelDebito("1");
						sessao.setAttribute("idImovelDebitoAnterior", imovel.getId().toString());
					}
				}

				sessao.setAttribute("imovelDebitos", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());
				consultarImovelActionForm.setIdImovelDebitos(imovel.getId().toString());

				httpServletRequest.setAttribute("msgImovelProcessoCorte", fachada.obterMsgImovelProcessoCorte(imovel.getId()));

				httpServletRequest.setAttribute("msgSituacaoImovelCampanhaPremiacao",
								fachada.obterMsgSituacaoImovelCampanhaPremiacao(imovel.getId()));

				consultarImovelActionForm.setIndicadorImovelEmExecucaoFiscal(fachada.verificarImovelEmExecucaoFiscal(imovel.getId())
								.toString());

				// cobranca situacao
				if(imovel.getCobrancaSituacao() != null){
					consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(imovel.getCobrancaSituacao().getDescricao());
				}

				// caso o imovel pesquisado seja diferente do pesquisado
				// anterior ou seja a primeira vez que se esteja pesquisando
				if(imovelNovoPesquisado){
					// seta na tela a inscrição do imovel
					httpServletRequest.setAttribute("idImovelDebitosNaoEncontrado", null);

					consultarImovelActionForm.setMatriculaImovelDebitos(fachada.pesquisarInscricaoImovel(Integer.valueOf(idImovelDebitos
									.trim()), true));

					try{
						if(ParametroCadastro.P_MATRICULA_COM_DIGITO_VERIFICADOR.executar().toString()
										.equals(ConstantesSistema.NAO.toString())){
							if(ParametroCadastro.P_METODO_CALCULO_DIGITO_VERIFICADOR.executar().toString().equals("1")){
								consultarImovelActionForm.setDigitoVerificadorImovelDebitos(Imovel
												.getDigitoVerificadorMatricula(idImovelDebitos
												.trim()));
							}
						}
					}catch(ControladorException e1){
						// TODO Auto-generated catch block
						e1.printStackTrace();
						throw new ActionServletException(e1.getMessage(), e1);
					}

					// seta a situação de agua
					consultarImovelActionForm.setSituacaoAguaDebitos(imovel.getLigacaoAguaSituacao().getDescricao());

					// seta a situação de esgoto
					consultarImovelActionForm.setSituacaoEsgotoDebitos(imovel.getLigacaoEsgotoSituacao().getDescricao());

					// seta o tipo de ligação
					if(idImovelDebitos != null || idImovelDebitos != ""){
						boolean tipoLigacaoBoolean = false;
						ObterIndicadorExistenciaHidrometroHelper obterIndicadorExistenciaHidrometroHelper = fachada
										.obterIndicadorExistenciaHidrometroLigacaoAguaPoco(Util.obterInteger(idImovelDebitos),
														tipoLigacaoBoolean);
						if(obterIndicadorExistenciaHidrometroHelper.getIndicadorLigacaoAgua().intValue() == 1
										|| obterIndicadorExistenciaHidrometroHelper.getIndicadorPoco().intValue() == 1){
							consultarImovelActionForm.setTipoLigacao("Hidrometrado");
						}else{
							consultarImovelActionForm.setTipoLigacao("Consumo Fixo");
						}

					}

					String referenciaInicial = "01/0001";
					String referenciaFinal = "12/9999";
					String dataVencimentoInicial = "01/01/0001";
					String dataVencimentoFinal = "31/12/9999";

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
					Integer tipoCliente = Integer.valueOf(2);
					Integer indicadorPagamento = Integer.valueOf(1);
					Integer indicadorConta = Integer.valueOf(1);
					Integer indicadorDebito = Integer.valueOf(1);
					Integer indicadorCredito = Integer.valueOf(1);
					Integer indicadorNotas = Integer.valueOf(1);
					Integer indicadorGuias = Integer.valueOf(1);
					Integer indicadorAtualizar = Integer.valueOf(1);
					Short indicadorConsiderarPagamentoNaoClassificado = 1;
					int indicadorCalcularAcrescimosSucumbenciaAnterior = 2;
					
					Integer idClienteDebito = null;
					Integer idRelacaoClienteDebito = null;

					if(consultarImovelActionForm.getIdClienteRelacaoImovelSelecionado() != null
									&& !consultarImovelActionForm.getIdClienteRelacaoImovelSelecionado().equals("")){
						String[] dados = consultarImovelActionForm.getIdClienteRelacaoImovelSelecionado().split("\\.");

						idClienteDebito = Integer.valueOf(dados[1]);
						idRelacaoClienteDebito = Integer.valueOf(dados[0]);
					}
					
					colecaoRelacaoImovel = fachada.obterListaClientesRelacaoDevedor(Integer.valueOf(idImovelDebitos.trim()),
									Integer.valueOf("000101"), Integer.valueOf("999912"), indicadorPagamento, indicadorConta,
									indicadorDebito, indicadorCredito, indicadorNotas, indicadorGuias, indicadorAtualizar,
									indicadorConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM, ConstantesSistema.SIM,
									ConstantesSistema.SIM, indicadorCalcularAcrescimosSucumbenciaAnterior, ConstantesSistema.SIM, null);

					// Obtendo os débitos do imovel
					ObterDebitoImovelOuClienteHelper colecaoDebitoImovel = new ObterDebitoImovelOuClienteHelper();
					if(idClienteDebito != null && idRelacaoClienteDebito != null){
						colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(tipoCliente.intValue(), idImovelDebitos.trim(),
										idClienteDebito.toString(), idRelacaoClienteDebito, anoMesInicial, anoMesFinal,
										dataVencimentoDebitoI, dataVencimentoDebitoF, indicadorPagamento.intValue(),
										indicadorConta.intValue(), indicadorDebito.intValue(), indicadorCredito.intValue(),
										indicadorNotas.intValue(), indicadorGuias.intValue(), indicadorAtualizar.intValue(), null, null,
										new Date(), ConstantesSistema.SIM, indicadorConsiderarPagamentoNaoClassificado,
										ConstantesSistema.SIM, ConstantesSistema.SIM, ConstantesSistema.SIM,
										indicadorCalcularAcrescimosSucumbenciaAnterior, null);
					}else{
						colecaoDebitoImovel = fachada.obterDebitoImovelOuCliente(tipoImovel.intValue(), idImovelDebitos.trim(), null, null,
										anoMesInicial, anoMesFinal, dataVencimentoDebitoI, dataVencimentoDebitoF,
										indicadorPagamento.intValue(), indicadorConta.intValue(), indicadorDebito.intValue(),
										indicadorCredito.intValue(), indicadorNotas.intValue(), indicadorGuias.intValue(),
										indicadorAtualizar.intValue(), null, null, new Date(), ConstantesSistema.SIM,
										indicadorConsiderarPagamentoNaoClassificado, ConstantesSistema.SIM, ConstantesSistema.SIM,
										ConstantesSistema.SIM, indicadorCalcularAcrescimosSucumbenciaAnterior, null);
					}

					colecaoContaValores = colecaoDebitoImovel.getColecaoContasValores();

					// 9.	O sistema apresenta a lista das 'Situações de Cobrança em Aberto' para o imóvel, caso existam 
					FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, Integer.valueOf(idImovelDebitos.trim()) ));
					filtroImovelCobrancaSituacao.adicionarParametro(new ParametroNulo(FiltroImovelCobrancaSituacao.DATA_RETIRADA_COBRANCA));
					filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelCobrancaSituacao.COBRANCA_SITUACAO);
					filtroImovelCobrancaSituacao.setCampoOrderByDesc(FiltroImovelCobrancaSituacao.DATA_IMPLANTACAO_COBRANCA);

					colecaoImovelCobrancaSituacaoAberto = fachada.pesquisar(filtroImovelCobrancaSituacao,
									ImovelCobrancaSituacao.class.getName());
					
					ContaValoresHelper dadosConta = null;

					valorConta = BigDecimal.ZERO;
					BigDecimal valorAcrescimo = BigDecimal.ZERO;
					BigDecimal valorAgua = BigDecimal.ZERO;
					BigDecimal valorEsgoto = BigDecimal.ZERO;
					BigDecimal valorDebito = BigDecimal.ZERO;
					BigDecimal valorCredito = BigDecimal.ZERO;
					BigDecimal valorImposto = BigDecimal.ZERO;

					BigDecimal valorMulta = BigDecimal.ZERO;
					BigDecimal valorJurosMora = BigDecimal.ZERO;
					BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;

					Collection<DebitoACobrar> colecaoDebitoACobrar = colecaoDebitoImovel.getColecaoDebitoACobrar();

					try{

						String valorParametro = ParametroAtendimentoPublico.P_TRATAR_DEBITO_TIPO_PARCELAMENTO_AGRUPADO.executar();
						httpServletRequest.setAttribute(ConstantesSistema.DEBITOS_AGRUPADOS, valorParametro);
						colecaoDebitoACobrar = Fachada.getInstancia().agruparDebitoACobrar(colecaoDebitoACobrar, Boolean.TRUE);

					}catch(ControladorException e){

						throw new ActionServletException("erro.sistema");

					}

					BigDecimal valorDebitoACobrar = new BigDecimal("0.00");
					BigDecimal valorDebitoACobrarSemJurosParcelamento = new BigDecimal("0.00");
					DebitoACobrar dadosDebito = null;

					if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){
						Iterator<DebitoACobrar> colecaoDebitoACobrarIterator = colecaoDebitoACobrar.iterator();

						// percorre a colecao de debito a cobrar somando o valor para obter um valor
						// total
						while(colecaoDebitoACobrarIterator.hasNext()){

							dadosDebito = colecaoDebitoACobrarIterator.next();
							valorDebitoACobrar = valorDebitoACobrar.add(dadosDebito.getValorTotal());

							if(dadosDebito.getDebitoTipo() != null
											&& !dadosDebito.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){
								valorDebitoACobrarSemJurosParcelamento = valorDebitoACobrarSemJurosParcelamento.add(dadosDebito
												.getValorTotal());
							}
						}
					}

					Collection<CreditoARealizar> colecaoCreditoARealizar = colecaoDebitoImovel.getColecaoCreditoARealizar();
					Collection<CreditoARealizar> colecaoCreditoARealizarSemDescontoParcelamento = new ArrayList<CreditoARealizar>();

					BigDecimal valorCreditoARealizar = new BigDecimal("0.00");
					BigDecimal valorCreditoARealizarSemDescontosParcelamento = new BigDecimal("0.00");
					CreditoARealizar dadosCredito = null;

					if(colecaoCreditoARealizar != null && !colecaoCreditoARealizar.isEmpty()){
						Iterator<CreditoARealizar> colecaoCreditoARealizarIterator = colecaoCreditoARealizar.iterator();

						// percorre a colecao de credito a realizar somando o valor para obter um
						// valor total
						while(colecaoCreditoARealizarIterator.hasNext()){

							dadosCredito = colecaoCreditoARealizarIterator.next();
							valorCreditoARealizar = valorCreditoARealizar.add(dadosCredito.getValorTotal());

							if(dadosCredito.getCreditoOrigem() != null
											&& !dadosCredito.getCreditoOrigem().getId().equals(
															CreditoOrigem.DESCONTOS_CONCEDIDOS_NO_PARCELAMENTO)){
								valorCreditoARealizarSemDescontosParcelamento = valorCreditoARealizarSemDescontosParcelamento
												.add(dadosCredito.getValorTotal());
								colecaoCreditoARealizarSemDescontoParcelamento.add(dadosCredito);
							}
						}
					}

					BigDecimal valorGuiaPagamento = new BigDecimal("0.00");

					Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = colecaoDebitoImovel
									.getColecaoGuiasPagamentoValores();
					if(colecaoGuiaPagamentoValores != null && !colecaoGuiaPagamentoValores.isEmpty()){
						Integer idGuiaPagamento = null;
						Short numeroPrestacao = null;

						boolean existeGuiaParcelamento = false;

						Iterator<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValoresHelperIterator = colecaoGuiaPagamentoValores
										.iterator();

						// Percorre a colecao de Prestações da Guia de Pagamento somando o valor
						// para obter o total em aberto
						while(colecaoGuiaPagamentoValoresHelperIterator.hasNext()){
							GuiaPagamentoValoresHelper dadosGuiaPagamentoValoresHelper = colecaoGuiaPagamentoValoresHelperIterator.next();

							valorGuiaPagamento = valorGuiaPagamento.add(dadosGuiaPagamentoValoresHelper.getValorTotalPrestacao().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

							idGuiaPagamento = dadosGuiaPagamentoValoresHelper.getIdGuiaPagamento();
							numeroPrestacao = dadosGuiaPagamentoValoresHelper.getNumeroPrestacao();

							existeGuiaParcelamento = fachada.verificarGuiaPagamentoParcelamentoCobrancaBancaria(idGuiaPagamento,
											numeroPrestacao);

							// existeGuiaParcelamento =
							// fachada.verificarGuiaPagamentoParcelamentoCobrancaBancariaComBoletoGeradoValido(
							// idGuiaPagamento, numeroPrestacao);
							//
							// if(!existeGuiaParcelamento){
							// existeGuiaParcelamento =
							// fachada.verificarGuiaPagamentoParcelamentoCobrancaBancariaPendentesGeracaoBoleto(
							// idGuiaPagamento, numeroPrestacao);
							// }

							if(existeGuiaParcelamento){
								// Marcar para colocar registro em vermelho
								dadosGuiaPagamentoValoresHelper.setDesativarSelecao(ConstantesSistema.SIM);
							}
						}
					}

					// Manda a colecao pelo request
					if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){
						ComparatorChain sortConta = new ComparatorChain();
						sortConta.addComparator(new BeanComparator("conta.referencia"), true);
						Collections.sort((List) colecaoContaValores, sortConta);
					}
					
					// [SB0005 – Validar autorização de acesso ao imóvel pelos usuários das empresas
					// de cobrança administrativa]
					this.validarAcessoImovelUsuarioEmpresaCobrancaAdministrativa(usuario, sessao, idImovelDebitos.trim(),
									colecaoDebitoImovel);

					// [SB0006 – Validar autorização de acesso ao imóvel em cobrança administrativa
					// pelos usuários da empresa contratante]
					this.validarAcessoImovelCobrancaAdministrativaUsuarioEmpresaContratante(usuario, sessao, idImovelDebitos.trim(),
									colecaoDebitoImovel);

					// [SB0007 – Verificar Débito em Cobrança Administrativa]
					this.verificarDebitoCobrancaAdministrativa(usuario, idImovelDebitos.trim(), colecaoContaValores,
									colecaoGuiaPagamentoValores);



					if(colecaoContaValores != null && !colecaoContaValores.isEmpty()){

						Iterator<ContaValoresHelper> colecaoContaValoresIterator = colecaoContaValores.iterator();

						// percorre a colecao de conta somando o valor para obter um valor total
						while(colecaoContaValoresIterator.hasNext()){

							dadosConta = colecaoContaValoresIterator.next();
							valorConta = valorConta.add(dadosConta.getConta().getValorTotal().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorAcrescimo = valorAcrescimo.add(dadosConta.getValorTotalContaValores().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));
							valorAgua = valorAgua.add(dadosConta.getConta().getValorAgua().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorEsgoto = valorEsgoto.add(dadosConta.getConta().getValorEsgoto().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorDebito = valorDebito.add(dadosConta.getConta().getDebitos().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorCredito = valorCredito.add(dadosConta.getConta().getValorCreditos().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorImposto = valorImposto.add(dadosConta.getConta().getValorImposto().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorMulta = valorMulta.add(dadosConta.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorJurosMora = valorJurosMora.add(dadosConta.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							valorAtualizacaoMonetaria = valorAtualizacaoMonetaria.add(dadosConta.getValorAtualizacaoMonetaria().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));

							dadosConta.getConta().setValorAgua(
											dadosConta.getConta().getValorAgua().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
							dadosConta.getConta().setValorEsgoto(
											dadosConta.getConta().getValorEsgoto().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
							dadosConta.getConta().setDebitos(
											dadosConta.getConta().getDebitos().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
							dadosConta.getConta().setValorCreditos(
											dadosConta.getConta().getValorCreditos().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
							dadosConta.getConta().setValorImposto(
											dadosConta.getConta().getValorImposto().setScale(Parcelamento.CASAS_DECIMAIS,
															Parcelamento.TIPO_ARREDONDAMENTO));
							dadosConta.setValorMulta(dadosConta.getValorMulta().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							dadosConta.setValorJurosMora(dadosConta.getValorJurosMora().setScale(Parcelamento.CASAS_DECIMAIS,
											Parcelamento.TIPO_ARREDONDAMENTO));
							dadosConta.setValorAtualizacaoMonetaria(dadosConta.getValorAtualizacaoMonetaria().setScale(
											Parcelamento.CASAS_DECIMAIS, Parcelamento.TIPO_ARREDONDAMENTO));


							if(dadosConta.getConta().getContaMotivoRevisao() != null){
								quantidadeContasRevisao = quantidadeContasRevisao + 1;
							}
						}
					}

					sessao.setAttribute("colecaoContaValores", colecaoContaValores);
					sessao.setAttribute("colecaoImovelCobrancaSituacaoAberto", colecaoImovelCobrancaSituacaoAberto);

					// Manda a coleção e os valores total de conta pelo request
					sessao.setAttribute("colecaoDebitoACobrar", colecaoDebitoACobrar);
					sessao.setAttribute("valorConta", Util.formatarMoedaReal(valorConta));
					sessao.setAttribute("valorAcrescimo", Util.formatarMoedaReal(valorAcrescimo));
					sessao.setAttribute("valorAgua", Util.formatarMoedaReal(valorAgua));
					sessao.setAttribute("valorEsgoto", Util.formatarMoedaReal(valorEsgoto));
					sessao.setAttribute("valorDebito", Util.formatarMoedaReal(valorDebito));
					sessao.setAttribute("valorCredito", Util.formatarMoedaReal(valorCredito));
					sessao.setAttribute("valorContaAcrescimo", Util.formatarMoedaReal(valorConta.add(valorAcrescimo)));
					sessao.setAttribute("valorImposto", Util.formatarMoedaReal(valorImposto));

					// Manda a colecao e o valor total de DebitoACobrar pelo request
					sessao.setAttribute("colecaoDebitoACobrar", colecaoDebitoACobrar);

					sessao.setAttribute("valorDebitoACobrar", Util.formatarMoedaReal(valorDebitoACobrar));

					// Manda a colecao e o valor total de CreditoARealizar pelo request
					sessao.setAttribute("colecaoCreditoARealizar", colecaoCreditoARealizar);
					sessao.setAttribute("colecaoCreditoARealizarSemDescontoParcelamento", colecaoCreditoARealizarSemDescontoParcelamento);
					sessao.setAttribute("valorCreditoARealizar", Util.formatarMoedaReal(valorCreditoARealizar));
					sessao.setAttribute("valorCreditoARealizarSemDescontosParcelamento", Util
									.formatarMoedaReal(valorCreditoARealizarSemDescontosParcelamento));

					// Manda a colecao e o valor total de GuiaPagamento pelo request
					sessao.setAttribute("colecaoGuiaPagamentoValores", colecaoGuiaPagamentoValores);
					sessao.setAttribute("valorGuiaPagamento", Util.formatarMoedaReal(valorGuiaPagamento));

					// Soma o valor total dos debitos e subtrai dos creditos
					valorTotalSemAcrescimo = valorConta.add(valorDebitoACobrar);
					valorTotalSemAcrescimo = valorTotalSemAcrescimo.add(valorGuiaPagamento);
					valorTotalSemAcrescimo = valorTotalSemAcrescimo.subtract(valorCreditoARealizar);

					BigDecimal valorTotalComAcrescimo = valorTotalSemAcrescimo.add(valorAcrescimo);

					BigDecimal valorToralSemAcrescimoESemJurosParcelamento = valorConta.add(valorDebitoACobrarSemJurosParcelamento);

					valorToralSemAcrescimoESemJurosParcelamento = valorToralSemAcrescimoESemJurosParcelamento.add(valorGuiaPagamento);

					valorToralSemAcrescimoESemJurosParcelamento = valorToralSemAcrescimoESemJurosParcelamento
									.subtract(valorCreditoARealizarSemDescontosParcelamento);

					sessao.setAttribute("valorTotalSemAcrescimo", Util.formatarMoedaReal(valorTotalSemAcrescimo));
					sessao.setAttribute("valorTotalComAcrescimo", Util.formatarMoedaReal(valorTotalComAcrescimo));
					sessao.setAttribute("valorToralSemAcrescimoESemJurosParcelamento", Util
									.formatarMoedaReal(valorToralSemAcrescimoESemJurosParcelamento));

				}

			}else{

				// imovel não encontrado
				httpServletRequest.setAttribute("idImovelDebitosNaoEncontrado", "true");

				consultarImovelActionForm.setMatriculaImovelDebitos("IMÓVEL INEXISTENTE");
				sessao.removeAttribute("imovelDebitos");
				sessao.removeAttribute("colecaoContaValores");
				sessao.removeAttribute("colecaoImovelCobrancaSituacaoAberto");
				
				// Manda a colecao e os valores total de conta pelo request
				sessao.removeAttribute("colecaoDebitoACobrar");
				sessao.removeAttribute("valorConta");
				sessao.removeAttribute("valorAcrescimo");
				sessao.removeAttribute("valorAgua");
				sessao.removeAttribute("valorEsgoto");
				sessao.removeAttribute("valorDebito");
				sessao.removeAttribute("valorCredito");
				sessao.removeAttribute("valorContaAcrescimo");
				sessao.removeAttribute("valorImposto");

				// Manda a colecao e o valor total de DebitoACobrar pelo request
				sessao.removeAttribute("colecaoDebitoACobrar");
				sessao.removeAttribute("valorDebitoACobrar");

				// Manda a colecao e o valor total de CreditoARealizar pelo request
				sessao.removeAttribute("colecaoCreditoARealizar");
				sessao.removeAttribute("valorCreditoARealizar");
				sessao.removeAttribute("valorCreditoARealizarSemDescontosParcelamento");

				// Manda a colecao e o valor total de GuiaPagamento pelo request
				sessao.removeAttribute("colecaoGuiaPagamentoValores");
				sessao.removeAttribute("valorGuiaPagamento");

				sessao.removeAttribute("valorTotalSemAcrescimo");
				sessao.removeAttribute("valorTotalComAcrescimo");
				sessao.removeAttribute("valorToralSemAcrescimoESemJurosParcelamento");

				consultarImovelActionForm.setDigitoVerificadorImovelDebitos(null);
				consultarImovelActionForm.setIdImovelDebitos(null);
				consultarImovelActionForm.setSituacaoEsgotoDebitos(null);
				consultarImovelActionForm.setTipoLigacao(null);
				consultarImovelActionForm.setSituacaoAguaDebitos(null);
				consultarImovelActionForm.setCodigoImovel(null);
				consultarImovelActionForm.setTipoRelacao(null);
				consultarImovelActionForm.setReferenciaInicial(null);
				consultarImovelActionForm.setReferenciaFinal(null);
				consultarImovelActionForm.setDataVencimentoInicial(null);
				consultarImovelActionForm.setDataVencimentoFinal(null);
				consultarImovelActionForm.setLigacaoAgua(null);
				consultarImovelActionForm.setLigacaoEsgoto(null);
				consultarImovelActionForm.setMaticula(null);
				consultarImovelActionForm.setInscricao(null);
				consultarImovelActionForm.setNomeCliente(null);
				consultarImovelActionForm.setTipoRelacaoCliente(null);
				consultarImovelActionForm.setCpf(null);
				consultarImovelActionForm.setCnpj(null);
				consultarImovelActionForm.setRefInicial(null);
				consultarImovelActionForm.setRefFinal(null);
				consultarImovelActionForm.setDtInicial(null);
				consultarImovelActionForm.setDtFinal(null);
			}

		}else{
			// matricula do imovel incorreta
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(idImovelDebitos);

			httpServletRequest.setAttribute("idImovelDebitosNaoEncontrado", null);
			sessao.removeAttribute("imovelDebitos");
			sessao.removeAttribute("colecaoContaValores");
			sessao.removeAttribute("colecaoImovelCobrancaSituacaoAberto");
			sessao.removeAttribute("idImovelPrincipalAba");

			// Manda a colecao e os valores total de conta pelo request
			sessao.removeAttribute("colecaoDebitoACobrar");
			sessao.removeAttribute("valorConta");
			sessao.removeAttribute("valorAcrescimo");
			sessao.removeAttribute("valorAgua");
			sessao.removeAttribute("valorEsgoto");
			sessao.removeAttribute("valorDebito");
			sessao.removeAttribute("valorCredito");
			sessao.removeAttribute("valorContaAcrescimo");
			sessao.removeAttribute("valorImposto");

			// Manda a colecao e o valor total de DebitoACobrar pelo request
			sessao.removeAttribute("colecaoDebitoACobrar");
			sessao.removeAttribute("valorDebitoACobrar");

			// Manda a colecao e o valor total de CreditoARealizar pelo request
			sessao.removeAttribute("colecaoCreditoARealizar");
			sessao.removeAttribute("valorCreditoARealizar");
			sessao.removeAttribute("valorCreditoARealizarSemDescontosParcelamento");

			// Manda a colecao e o valor total de GuiaPagamento pelo request
			sessao.removeAttribute("colecaoGuiaPagamentoValores");
			sessao.removeAttribute("valorGuiaPagamento");

			sessao.removeAttribute("valorTotalSemAcrescimo");
			sessao.removeAttribute("valorTotalComAcrescimo");

			consultarImovelActionForm.setMatriculaImovelDebitos(null);
			consultarImovelActionForm.setDigitoVerificadorImovelDebitos(null);
			consultarImovelActionForm.setSituacaoEsgotoDebitos(null);
			consultarImovelActionForm.setTipoLigacao(null);
			consultarImovelActionForm.setSituacaoAguaDebitos(null);
			consultarImovelActionForm.setCodigoImovel(null);
			consultarImovelActionForm.setTipoRelacao(null);
			consultarImovelActionForm.setReferenciaInicial(null);
			consultarImovelActionForm.setReferenciaFinal(null);
			consultarImovelActionForm.setDataVencimentoInicial(null);
			consultarImovelActionForm.setDataVencimentoFinal(null);
			consultarImovelActionForm.setLigacaoAgua(null);
			consultarImovelActionForm.setLigacaoEsgoto(null);
			consultarImovelActionForm.setMaticula(null);
			consultarImovelActionForm.setInscricao(null);
			consultarImovelActionForm.setNomeCliente(null);
			consultarImovelActionForm.setTipoRelacaoCliente(null);
			consultarImovelActionForm.setCpf(null);
			consultarImovelActionForm.setCnpj(null);
			consultarImovelActionForm.setRefInicial(null);
			consultarImovelActionForm.setRefFinal(null);
			consultarImovelActionForm.setDtInicial(null);
			consultarImovelActionForm.setDtFinal(null);
		}

		try{
			if(ParametroCadastro.P_EMITE_AVISO_E_ORDEM_CORTE_INDIVIDUAL.executar().equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("indicadorEmiteAvisoEOrdemCorteIndividual", true);
			}else{
				sessao.removeAttribute("indicadorEmiteAvisoEOrdemCorteIndividual");
			}

			Short indicadorEmitirExtratoDebitosSelecao = (Short) sessao.getAttribute("indicadorEmitirExtratoDebitosSelecao");

			if(ParametroCadastro.P_INDICADOR_EXTRATO_DEBITO_SELECAO.executar().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)
							&& (indicadorEmitirExtratoDebitosSelecao == null || !indicadorEmitirExtratoDebitosSelecao
											.equals(ConstantesSistema.SIM))){
				sessao.removeAttribute("indicadorEmitirExtratoDebitosSelecao");
			}else{
				sessao.setAttribute("indicadorEmitirExtratoDebitosSelecao", ConstantesSistema.SIM);
			}

			String exibirPerguntaCobrancaDeTaxa = ParametroAtendimentoPublico.P_INDICADOR_COBRAR_TAXA_EXTRATO_RELACAO_DEBITO.executar();
			if(exibirPerguntaCobrancaDeTaxa != null && exibirPerguntaCobrancaDeTaxa.equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("exibirPopUpTaxaDebitoACobrar", ConstantesSistema.SIM);
			}

			if(fachada.existeProcessoExecucaoFiscal().equals(ConstantesSistema.SIM)){
				sessao.setAttribute("exibirDividaAtivaColuna", "S");
			}else{
				sessao.removeAttribute("exibirDividaAtivaColuna");
			}


		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		if(indicadorFauramentoTitularDebito){
			httpServletRequest.setAttribute("indicadorFauramentoTitularDebito", "S");
		}else{
			httpServletRequest.removeAttribute("indicadorFauramentoTitularDebito");
		}

		try{
			if(ParametroCadastro.P_MATRICULA_COM_DIGITO_VERIFICADOR.executar().toString().equals(ConstantesSistema.NAO.toString())){
				if(ParametroCadastro.P_METODO_CALCULO_DIGITO_VERIFICADOR.executar().toString().equals("1")){
					httpServletRequest.setAttribute("matriculaSemDigitoVerificador", '1');
				}else{
					throw new ControladorException("erro.parametro.nao.informado", null, "P_METODO_CALCULO_DIGITO_VERIFICADOR");
				}

			}else{
				httpServletRequest.setAttribute("matriculaSemDigitoVerificador", '0');
			}
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ActionServletException(e.getMessage(), e);
		}

		sessao.setAttribute("colecaoRelacaoImovel", colecaoRelacaoImovel);

		// retorna o mapeamento contido na variável retorno
		return retorno;
	}

	/**
	 * [SB0005] – Validar autorização de acesso ao imóvel pelos usuários das empresas de cobrança
	 * administrativa
	 * 
	 * @author Hugo Lima
	 * @date 15/08/2012
	 * @author Saulo Lima
	 * @date 25/07/2013
	 * @param usuario
	 * @param sessao
	 * @param idImovel
	 * @param colecaoDebitoImovel
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