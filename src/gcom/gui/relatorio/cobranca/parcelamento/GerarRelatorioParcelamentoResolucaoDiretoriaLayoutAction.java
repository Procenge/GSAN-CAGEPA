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

package gcom.gui.relatorio.cobranca.parcelamento;

import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaForma;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.cobranca.bean.NegociacaoOpcoesParcelamentoHelper;
import gcom.cobranca.bean.OpcoesParcelamentoHelper;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.faturamento.credito.CreditoARealizar;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.EfetuarParcelamentoDebitosWizardAction;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.parcelamento.GeradorRelatorioParcelamentoException;
import gcom.relatorio.cobranca.parcelamento.GeradorRelatorioResolucaoDiretoria;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GerarRelatorioParcelamentoResolucaoDiretoriaLayoutAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [UC0252] Consultar Parcelamentos de Débitos
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author Cinthya
	 * @date 19/10/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;
		Parcelamento parcelamentoRelatorio=null;
		Collection<ContaValoresHelper> colecaoContaValores = new ArrayList();
		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = new ArrayList();
		Collection<DebitoACobrar> colecaoDebitoACobrar = new ArrayList();
		Collection<CreditoARealizar> colecaoCreditoARealizar = new ArrayList();
		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		boolean indicadorAtualizarTermoTestemunha = false;
		if(sessao.getAttribute("atualizarEmitirTermoTestemunha") != null
						&& ((String) sessao.getAttribute("atualizarEmitirTermoTestemunha")).equals("true")){
			indicadorAtualizarTermoTestemunha = true;
			sessao.removeAttribute("atualizarEmitirTermoTestemunha");
		}

		boolean indicadorAtualizarDadosTermo = false;
		if(sessao.getAttribute("atualizarEmitirDadosTermo") != null
						&& ((String) sessao.getAttribute("atualizarEmitirDadosTermo")).equals("true")){
			indicadorAtualizarDadosTermo = true;
			sessao.removeAttribute("atualizarEmitirDadosTermo");
		}

		boolean indicadorInformarDadosTermoParcelamento = false;
		try{
			if(ParametroParcelamento.P_INFORMAR_DADOS_TERMO_PARCELAMENTO_NORMAL.executar().equals("1")){
				indicadorInformarDadosTermoParcelamento = true;
			}
		}catch(ControladorException e1){
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		boolean indicadorExecucaoFiscalParcelamento = false;
		if(sessao.getAttribute("indicadorParcelamentoExecucaoFiscal") != null
						&& sessao.getAttribute("indicadorParcelamentoExecucaoFiscal").toString().equals("S")){
			indicadorExecucaoFiscalParcelamento = true;
		}

		String indicadorCobrancaBancaria = "";
		if(sessao.getAttribute("indicadorCobrancaBancaria") != null
						&& sessao.getAttribute("indicadorCobrancaBancaria").toString().equals("1")){
			indicadorCobrancaBancaria = "1";
		}else{
			indicadorCobrancaBancaria = "2";
		}

		boolean indicadorEditarTermoParcelameto = false;
		if(sessao.getAttribute("atualizarEditarTermoParcelameto") != null
						&& ((String) sessao.getAttribute("atualizarEditarTermoParcelameto")).equals("true")){
			indicadorEditarTermoParcelameto = true;
			sessao.removeAttribute("atualizarEditarTermoParcelameto");
		}

		Integer numeroDiasVencimentoEntrada = null;
		NegociacaoOpcoesParcelamentoHelper negociacaoOpcoesParcelamento = (NegociacaoOpcoesParcelamentoHelper) sessao
						.getAttribute("opcoesParcelamento");
		if(negociacaoOpcoesParcelamento != null){
			numeroDiasVencimentoEntrada = negociacaoOpcoesParcelamento.getNumeroDiasVencimentoEntrada();
		}

		boolean indicadorTermoParcelamentoPreview = false;
		if(sessao.getAttribute("TermoParcelamentoPreview") != null
						&& ((String) sessao.getAttribute("TermoParcelamentoPreview")).equals("True")){
			indicadorTermoParcelamentoPreview = true;

			String indicadorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento");
			String indicadorDebitosACobrar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar");
			String indicadorCreditoARealizar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCreditoARealizar");

			if(sessao.getAttribute("colecaoContaValores") != null){
				colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
			}else{
				colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresImovel");
			}

			if(sessao.getAttribute("colecaoGuiaPagamentoValoresSelecionadas") != null || indicadorGuiasPagamento.equals("2")){
				colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao
								.getAttribute("colecaoGuiaPagamentoValoresSelecionadas");
			}else{
				colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao
								.getAttribute("colecaoGuiaPagamentoValoresImovel");
			}

			if(sessao.getAttribute("colecaoDebitoACobrar") != null || indicadorDebitosACobrar.equals("2")){
				colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar");
			}else{
				colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrarImovel");
			}

			if(sessao.getAttribute("colecaoCreditoARealizar") != null || indicadorCreditoARealizar.equals("2")){
				colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizar");
			}else{
				colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizarImovel");
			}
		}

		String idParcelamento = (String) sessao.getAttribute("idParcelamento");
		boolean abrirTelaTestemunhas = false;
		boolean abrirDadosTermoParcelamento = false;
		boolean abrirDadosExecucaoFiscal = false;

		String indicadorParcelamentoCobrancaBancaria = httpServletRequest.getParameter("indicadorParcelamentoCobrancaBancaria");
		String consultaParcDebitos = httpServletRequest.getParameter("consultaParcDebitos");

		String[] idsParcelamento = null;

		if(idParcelamento != null){
			idsParcelamento = idParcelamento.split("\\$");
		}

		Collection<Integer> colecaoIdsParcelamento = new ArrayList<Integer>();

		if(idsParcelamento != null){
			for(String idParc : idsParcelamento){
				colecaoIdsParcelamento.add(Integer.valueOf(idParc));
			}
		}

		// INÍCIO :: Relatório de Cobrança Administrativa
		ParcelamentoTermoTestemunhas parcelamentoTermoTestemunhas = (ParcelamentoTermoTestemunhas) sessao
						.getAttribute("parcelamentoTermoTestemunhas");

		// INICIO :: Relatorio de Dados Parcelamento
		ParcelamentoDadosTermo parcelamentoDadosTermos = (ParcelamentoDadosTermo) sessao
						.getAttribute("parcelamentoDadosTermo");

		// Caso a variável parcelamentoTermoTestemunhas diferente de NULL significa que é um
		// Parcelamento de Cobrança Administrativa e já tem os dados das testemunhas para
		// emissão do
		// Termo
		if((colecaoIdsParcelamento.size() == 1) || (indicadorTermoParcelamentoPreview))
		{
			int caracteristicaParcelamento = 0;

			if(!indicadorTermoParcelamentoPreview){
				FiltroParcelamento filtroParcelamento = new FiltroParcelamento();
				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria");
				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("resolucaoDiretoria.resolucaoDiretoriaLayout");
				filtroParcelamento.adicionarCaminhoParaCarregamentoEntidade("cobrancaForma");
				filtroParcelamento
								.adicionarParametro(new ParametroSimples(FiltroParcelamento.ID, colecaoIdsParcelamento.iterator().next()));

				List<Parcelamento> listaParcelamento = (List<Parcelamento>) fachada.pesquisar(filtroParcelamento,
								Parcelamento.class.getName());
				if(!Util.isVazioOrNulo(listaParcelamento)){
					parcelamentoRelatorio = listaParcelamento.iterator().next();
					caracteristicaParcelamento = fachada.obterCaracteristicaParcelamento(parcelamentoRelatorio);
				}
			}else{
				parcelamentoRelatorio = carregarDadosParcelamento(actionForm, sessao, httpServletRequest);
				caracteristicaParcelamento = fachada.obterCaracteristicaParcelamento(colecaoContaValores, colecaoGuiaPagamentoValores,
								parcelamentoRelatorio.getCobrancaForma().getId(), indicadorCobrancaBancaria, colecaoDebitoACobrar);
			}

			if(parcelamentoRelatorio != null){
				if(!indicadorExecucaoFiscalParcelamento){
					if((caracteristicaParcelamento == 2 && indicadorTermoParcelamentoPreview && indicadorAtualizarTermoTestemunha == false && !indicadorEditarTermoParcelameto)
									|| (caracteristicaParcelamento == 2 && indicadorAtualizarTermoTestemunha == false
													&& parcelamentoRelatorio.getId() != null
													&& parcelamentoRelatorio.getConteudoTermoParcelamentoFinal() == null && !indicadorEditarTermoParcelameto)){
						// 13.2.2. Caso seja parcelamento de cobrança administrativa

						try{
							ResolucaoDiretoriaLayout resolucaoDiretoriaLayout = fachada
											.obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa();

							// Caso o termo do parcelamento exija a assinatura de testemunhas
							if(resolucaoDiretoriaLayout != null
											&& resolucaoDiretoriaLayout.getIndicadorSolicitaTestemunhas() != null
											&& resolucaoDiretoriaLayout.getIndicadorSolicitaTestemunhas().intValue() == ConstantesSistema.SIM
															.intValue()){

								// Solicitar dados das testemunhas do parcelamento
								sessao.setAttribute("TipoParcelamentoDadosTermo", "TESTEMUNHA");
								abrirTelaTestemunhas = true;
								retorno = actionMapping.findForward("exibirParcelamentoTermoTestemunhasAction");
							}

						}catch(GeradorRelatorioParcelamentoException e){
							throw new ActionServletException("erro.sistema", e);
						}
						// FIM :: Relatório de Cobrança Administrativa

					}else if((caracteristicaParcelamento != 2) && (indicadorInformarDadosTermoParcelamento)
									&& (!indicadorEditarTermoParcelameto)){
						// 1.2.2.2. Caso contrário, ou seja, não seja parcelamento de cobrança
						// administrativa e
						// 1.2.2.2.1. Caso existam dados a serem informados para a emissão do termo
						// do parcelamento normal
						// SB0068
						if((indicadorTermoParcelamentoPreview && indicadorAtualizarDadosTermo == false)
										|| (indicadorAtualizarDadosTermo == false && parcelamentoRelatorio.getId() != null && parcelamentoRelatorio
														.getConteudoTermoParcelamentoFinal() == null)){

							// Solicitar dados do parcelamento
							if(parcelamentoRelatorio.getCliente() != null && parcelamentoRelatorio.getCliente().getId() != null){
								sessao.setAttribute("idClienteResponsavel", parcelamentoRelatorio.getCliente().getId());
							}else{
								sessao.removeAttribute("idClienteResponsavel");
							}

							sessao.setAttribute("TipoParcelamentoDadosTermo", "DADOSTERMO");
							abrirDadosTermoParcelamento = true;
							retorno = actionMapping.findForward("exibirParcelamentoDadosTermoAction");
						}
						// FIM
					}
				}else{
					// [SB0056] Emitir Termo do Parcelamento
					// 1.1. Caso seja parcelamento de execução fiscal (Indicador de Parcelamento de
					// Execução
					if((indicadorTermoParcelamentoPreview && indicadorAtualizarDadosTermo == false && !indicadorEditarTermoParcelameto)
									|| (indicadorAtualizarDadosTermo == false && parcelamentoRelatorio.getId() != null
													&& parcelamentoRelatorio.getConteudoTermoParcelamentoFinal() == null && !indicadorEditarTermoParcelameto)){

						// Solicitar dados do parcelamento
						if(parcelamentoRelatorio.getCliente() != null && parcelamentoRelatorio.getCliente().getId() != null){
							sessao.setAttribute("idClienteResponsavel", parcelamentoRelatorio.getCliente().getId());
						}else{
							sessao.removeAttribute("idClienteResponsavel");
						}

						sessao.setAttribute("TipoParcelamentoDadosTermo", "DADOSTERMOEXECFISCAL");
						abrirDadosTermoParcelamento = true;
						retorno = actionMapping.findForward("exibirParcelamentoDadosTermoExecFiscalAction");

					}

					// FIM
				}
			}
		} 


		if((!abrirTelaTestemunhas) && (!abrirDadosTermoParcelamento) && (!abrirDadosExecucaoFiscal)){
			String nomeRelatorio = null;

			if(sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAER)){
				nomeRelatorio = ConstantesRelatorios.RELATORIO_PARCELAMENTO_CAER;
			}else{
				nomeRelatorio = ConstantesRelatorios.RELATORIO_PARCELAMENTO;
			}

			TarefaRelatorio tarefaRelatorio = new GeradorRelatorioResolucaoDiretoria(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"), nomeRelatorio);

			if(!indicadorTermoParcelamentoPreview){
				tarefaRelatorio.addParametro("colecaoIdsParcelamento", colecaoIdsParcelamento);
			}else{
				tarefaRelatorio.addParametro("parcelamento", parcelamentoRelatorio);
				tarefaRelatorio.addParametro("colecaoGuiaPagamentoValores", colecaoGuiaPagamentoValores);
				tarefaRelatorio.addParametro("colecaoDebitoACobrar", colecaoDebitoACobrar);
				tarefaRelatorio.addParametro("colecaoCreditoARealizar", colecaoCreditoARealizar);
				tarefaRelatorio.addParametro("indicadorCobrancaBancaria", indicadorCobrancaBancaria);
				tarefaRelatorio.addParametro("numeroDiasVencimentoEntrada", numeroDiasVencimentoEntrada);
			}

			tarefaRelatorio.addParametro("colecaoContaValores", sessao.getAttribute("colecaoContaValores"));
			tarefaRelatorio.addParametro("indicadorParcelamentoCobrancaBancaria", indicadorParcelamentoCobrancaBancaria);
			tarefaRelatorio.addParametro("consultarParcelamentoDebitos", consultaParcDebitos != null ? Boolean.TRUE : Boolean.FALSE);
			tarefaRelatorio.addParametro("parcelamentoTermoTestemunhas", parcelamentoTermoTestemunhas);
			tarefaRelatorio.addParametro("parcelamentoDadosTermos", parcelamentoDadosTermos);

			boolean temPermissaoEditarTermoPacelamento = fachada.verificarPermissaoEspecial(PermissaoEspecial.ALTERAR_TERMO_PARCELAMENTO,
							this.getUsuarioLogado(httpServletRequest));

			if(!temPermissaoEditarTermoPacelamento){
				if(parcelamentoRelatorio.getConteudoTermoParcelamentoFinal() == null || indicadorTermoParcelamentoPreview
								|| indicadorAtualizarTermoTestemunha || indicadorAtualizarDadosTermo){
					retorno = processarExibicaoRelatorio(tarefaRelatorio, TarefaRelatorio.TIPO_PDF, httpServletRequest,
									httpServletResponse, actionMapping);

					if((parcelamentoRelatorio.getConteudoTermoParcelamentoInicial() == null && parcelamentoRelatorio.getId() != null)
									|| (sessao.getAttribute("conteudoTermoParcelamentoInicial") == null && parcelamentoRelatorio.getId() == null)){
						if(parcelamentoRelatorio.getId() != null){
							parcelamentoRelatorio.setConteudoTermoParcelamentoInicial((byte[]) tarefaRelatorio.getParametro("relatorio"));
						}else{
							sessao.setAttribute("conteudoTermoParcelamentoInicial", (byte[]) tarefaRelatorio.getParametro("relatorio"));
						}
					}

					if(parcelamentoRelatorio.getId() != null){
						parcelamentoRelatorio.setConteudoTermoParcelamentoFinal((byte[]) tarefaRelatorio.getParametro("relatorio"));	
					} else {
						sessao.setAttribute("conteudoTermoParcelamentoFinal", (byte[]) tarefaRelatorio.getParametro("relatorio"));	
					}

					if(parcelamentoRelatorio.getId() != null){
						fachada.atualizar(parcelamentoRelatorio);
					}
				} else {
					OutputStream out = null;
					try{
						
						httpServletResponse.addHeader("Content-Disposition", "attachment; filename=relatorio.pdf");
						String mimeType = "application/pdf";

						httpServletResponse.setContentType(mimeType);
						out = httpServletResponse.getOutputStream();
						out.write(parcelamentoRelatorio.getConteudoTermoParcelamentoFinal());
						out.flush();
						out.close();

					}catch(IOException ex){
						// manda o erro para a página no request atual
						reportarErros(httpServletRequest, "erro.sistema");

						// seta o mapeamento de retorno para a tela de erro de popup
						retorno = actionMapping.findForward("telaErroPopup");

					}catch(SistemaException ex){
						// manda o erro para a página no request atual
						reportarErros(httpServletRequest, "erro.sistema");

						// seta o mapeamento de retorno para a tela de erro de popup
						retorno = actionMapping.findForward("telaErroPopup");

					}catch(RelatorioVazioException ex1){
						// manda o erro para a página no request atual
						reportarErros(httpServletRequest, "erro.relatorio.vazio");

						// seta o mapeamento de retorno para a tela de atenção de popup
						retorno = actionMapping.findForward("telaAtencaoPopup");
					}
				}
			}else{
				if (!indicadorEditarTermoParcelameto) {
					if(parcelamentoRelatorio.getConteudoTermoParcelamentoFinal() == null || indicadorAtualizarDadosTermo
									|| indicadorAtualizarTermoTestemunha || indicadorTermoParcelamentoPreview){
						byte[] dadosPDF = (byte[]) tarefaRelatorio.executar();

						if(sessao.getAttribute("conteudoTermoParcelamentoInicial") == null){
							sessao.setAttribute("conteudoTermoParcelamentoInicial", dadosPDF);
						}

						sessao.setAttribute("stringTextoHtml", tarefaRelatorio.getParametro("stringTextoHtml"));

						retorno = actionMapping.findForward("exibirParcelamentoEditarResolucaoDiretoriaAction");
					}else{
						OutputStream out = null;
						try{

							httpServletResponse.addHeader("Content-Disposition", "attachment; filename=relatorio.pdf");
							String mimeType = "application/pdf";

							httpServletResponse.setContentType(mimeType);
							out = httpServletResponse.getOutputStream();
							out.write(parcelamentoRelatorio.getConteudoTermoParcelamentoFinal());
							out.flush();
							out.close();

						}catch(IOException ex){
							// manda o erro para a página no request atual
							reportarErros(httpServletRequest, "erro.sistema");

							// seta o mapeamento de retorno para a tela de erro de popup
							retorno = actionMapping.findForward("telaErroPopup");

						}catch(SistemaException ex){
							// manda o erro para a página no request atual
							reportarErros(httpServletRequest, "erro.sistema");

							// seta o mapeamento de retorno para a tela de erro de popup
							retorno = actionMapping.findForward("telaErroPopup");

						}catch(RelatorioVazioException ex1){
							// manda o erro para a página no request atual
							reportarErros(httpServletRequest, "erro.relatorio.vazio");

							// seta o mapeamento de retorno para a tela de atenção de popup
							retorno = actionMapping.findForward("telaAtencaoPopup");
						}
					}
				} else {
					String stringTextoHtml = "";
					if(sessao.getAttribute("stringTextoHtmlEditado") != null){
						stringTextoHtml = sessao.getAttribute("stringTextoHtmlEditado").toString();
						sessao.removeAttribute("stringTextoHtmlEditado");
					}
					
					tarefaRelatorio.addParametro("stringTextoHtmlEditado", stringTextoHtml);
					
					retorno = processarExibicaoRelatorio(tarefaRelatorio, TarefaRelatorio.TIPO_PDF, httpServletRequest,
									httpServletResponse, actionMapping);


					if(parcelamentoRelatorio.getId() != null){
						parcelamentoRelatorio.setConteudoTermoParcelamentoFinal((byte[]) tarefaRelatorio.getParametro("relatorio"));	
					} else {
						sessao.setAttribute("conteudoTermoParcelamentoFinal", (byte[]) tarefaRelatorio.getParametro("relatorio"));	
					}

					if(parcelamentoRelatorio.getId() != null){
						fachada.atualizar(parcelamentoRelatorio);
					}					
				}
			}

		}

		return retorno;
	}

	public Parcelamento carregarDadosParcelamento(ActionForm actionForm, HttpSession sessao, HttpServletRequest httpServletRequest){

		// obtem variaveis do form a tela sendo chamada pelo preview
		DynaActionForm efetuarParcelamentoDebitosActionForm = (DynaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// ABA 1 - 6.1 Caso o usuario confirme o parecelamento
		Integer situacaoAguaId = null;
		if(efetuarParcelamentoDebitosActionForm.get("situacaoAguaId") != null
						&& !((String) efetuarParcelamentoDebitosActionForm.get("situacaoAguaId")).trim().equals("")){
			situacaoAguaId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("situacaoAguaId")));
		}
		String codigoImovel = (String) efetuarParcelamentoDebitosActionForm.get("matriculaImovel");

		Date dataParcelamento = Util.converteStringParaDate((String) efetuarParcelamentoDebitosActionForm.get("dataParcelamento"));
		String resolucaoDiretoria = (String) efetuarParcelamentoDebitosActionForm.get("resolucaoDiretoria");
		String inicioIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("inicioIntervaloParcelamento");
		String fimIntervaloParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("fimIntervaloParcelamento");
		String indicadorRestabelecimento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorRestabelecimento");
		String indicadorContasRevisao = (String) efetuarParcelamentoDebitosActionForm.get("indicadorContasRevisao");
		String indicadorGuiasPagamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorGuiasPagamento");
		String indicadorAcrescimosImpotualidade = (String) efetuarParcelamentoDebitosActionForm.get("indicadorAcrescimosImpotualidade");
		String indicadorDebitosACobrar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorDebitosACobrar");
		String indicadorCreditoARealizar = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCreditoARealizar");
		String indicadorConfirmacaoParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorConfirmacaoParcelamento");
		String indicadorCobrancaParcelamento = (String) efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento");
		String indicadorParcelamentoCobrancaBancaria = (String) efetuarParcelamentoDebitosActionForm
						.get("indicadorParcelamentoCobrancaBancaria");
		String cpfClienteParcelamentoDigitado = (String) efetuarParcelamentoDebitosActionForm.get("cpfClienteParcelamentoDigitado");

		String indicadorPessoaFisicaJuridica = (String) efetuarParcelamentoDebitosActionForm.get("indicadorPessoaFisicaJuridica");
		if(indicadorPessoaFisicaJuridica == null){
			if(httpServletRequest.getAttribute("indicadorPessoaFisicaJuridica") != null){
				sessao.setAttribute("indicadorPessoaFisicaJuridica", httpServletRequest.getAttribute("indicadorPessoaFisicaJuridica"));
			}
		}

		// Caso o fim do parcelamento não seja informado
		if(fimIntervaloParcelamento == null || fimIntervaloParcelamento.equals("")){
			fimIntervaloParcelamento = "12/9999";
		}

		// [FS0009] - Verificar preenchimento dos campos
		if(codigoImovel == null || codigoImovel.trim().equals("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Matricula do Imóvel");
		}
		if(dataParcelamento == null || dataParcelamento.equals("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Data do Parcelamento");
		}
		if(resolucaoDiretoria == null || resolucaoDiretoria.trim().equals("")){
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "RD do Parcelamento");
		}
		// if(situacaoAguaId.equals(LigacaoAguaSituacao.SUPRIMIDO) ||
		// situacaoAguaId.equals(LigacaoAguaSituacao.SUPR_PARC)
		// || situacaoAguaId.equals(LigacaoAguaSituacao.SUPR_PARC_PEDIDO)){
		// if(indicadorRestabelecimento == null || indicadorRestabelecimento.trim().equals("")){
		// throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null,
		// "Com Restabelecimento?");
		// }
		// }

		Collection collIdsSitLigacaoAguaRestabelecimento;
		try{
			collIdsSitLigacaoAguaRestabelecimento = EfetuarParcelamentoDebitosWizardAction.getSitLigacaoAguaRestabelecimento();
			if(collIdsSitLigacaoAguaRestabelecimento != null && collIdsSitLigacaoAguaRestabelecimento.size() > 0){

				if(collIdsSitLigacaoAguaRestabelecimento.contains(situacaoAguaId)){
					if(indicadorRestabelecimento == null || indicadorRestabelecimento.trim().equals("")){
						throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Com Restabelecimento?");
					}
				}

			}
		}catch(ControladorException e){
			throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_SIT_LIGACAO_AGUA_RESTABELECIMENTO");
		}


		if(indicadorContasRevisao == null || indicadorContasRevisao.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Contas em Revisão?");
		}
		if(indicadorGuiasPagamento == null || indicadorGuiasPagamento.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Guias de Pagamento?");
		}
		if(indicadorAcrescimosImpotualidade == null || indicadorAcrescimosImpotualidade.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Acréscimos por Impontualidade?");
		}
		if(indicadorDebitosACobrar == null || indicadorDebitosACobrar.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "Considerar Débitos a Cobrar?");
		}

		if(indicadorCreditoARealizar == null || indicadorCreditoARealizar.trim().equals("")){
			throw new ActionServletException("atencao.campo_selecionado.obrigatorio", null, "'Considerar Créditos a Realizar?'");
		}

		// ABA 3 - Verifica se foi escolhido alguma opção de parcelamento

		Integer numeroMesesEntreParcelas = null;
		Integer numeroParcelasALancar = null;
		Integer numeroMesesInicioCobranca = null;

		Collection<OpcoesParcelamentoHelper> colecaoOpcoesParcelamento = (Collection<OpcoesParcelamentoHelper>) sessao
						.getAttribute("colecaoOpcoesParcelamento");
		
		Collection<ParcelamentoConfiguracaoPrestacao> colecaoParcelamentoConfiguracaoPrestacao = (Collection<ParcelamentoConfiguracaoPrestacao>) sessao
						.getAttribute("colecaoParcelamentoConfiguracaoPrestacao");

		Short numeroPrestacoes = Short.valueOf("0");
		BigDecimal valorPrestacao = BigDecimal.ZERO;
		BigDecimal valorEntradaMinima = BigDecimal.ZERO;
		BigDecimal taxaJuros = BigDecimal.ZERO;
		BigDecimal percentualDescontoJurosMora = BigDecimal.ZERO;
		BigDecimal percentualDescontoMulta = BigDecimal.ZERO;
		BigDecimal percentualDescontoCorrecaoMonetaria = BigDecimal.ZERO;
		BigDecimal valorDescontoAcrescimosImpontualidadeNaPrestacao = null;
		boolean checarValorRestante = false;

		if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.isEmpty()
						&& !Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))){

			Iterator opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
			boolean opcaoMarcada = false;
			while(opcoesParcelamentoValores.hasNext()){
				OpcoesParcelamentoHelper opcoesParcelamentoHelper = (OpcoesParcelamentoHelper) opcoesParcelamentoValores.next();
				if(((String) efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas")).equals(opcoesParcelamentoHelper
								.getQuantidadePrestacao().toString())){
					opcaoMarcada = true;
					// valorJurosParcelamento = opcoesParcelamentoHelper.getTaxaJuros();
					numeroPrestacoes = opcoesParcelamentoHelper.getQuantidadePrestacao();
					valorPrestacao = opcoesParcelamentoHelper.getValorPrestacao();
					valorEntradaMinima = opcoesParcelamentoHelper.getValorEntradaMinima();
					taxaJuros = opcoesParcelamentoHelper.getTaxaJuros();

					numeroMesesEntreParcelas = opcoesParcelamentoHelper.getNumeroMesesEntreParcelas();
					numeroParcelasALancar = opcoesParcelamentoHelper.getNumeroParcelasALancar();
					numeroMesesInicioCobranca = opcoesParcelamentoHelper.getNumeroMesesInicioCobranca();

					percentualDescontoJurosMora = opcoesParcelamentoHelper.getPercentualDescontoJurosMora();
					percentualDescontoMulta = opcoesParcelamentoHelper.getPercentualDescontoMulta();
					percentualDescontoCorrecaoMonetaria = opcoesParcelamentoHelper.getPercentualDescontoCorrecaoMonetaria();

					valorDescontoAcrescimosImpontualidadeNaPrestacao = opcoesParcelamentoHelper
									.getValorDescontoAcrescimosImpontualidadeNaPrestacao();
				}
			}
			if(!opcaoMarcada && Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

				throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
			}else{

				if(!opcaoMarcada){

					checarValorRestante = true;
				}
			}
		}else{

			if(Util.isVazioOrNulo(colecaoParcelamentoConfiguracaoPrestacao)){

				throw new ActionServletException("atencao.pelo.menos.uma.opcao.parcelamento.marcada");
			}else{

				checarValorRestante = true;

				for(ParcelamentoConfiguracaoPrestacao parcelamentoConfiguracaoPrestacao : colecaoParcelamentoConfiguracaoPrestacao){

					numeroPrestacoes = (short) (numeroPrestacoes.shortValue() + parcelamentoConfiguracaoPrestacao.getNumeroPrestacao()
									.shortValue());
				}
			}
		}

		if(checarValorRestante){

			// [FS0047] - Verificar valor restante do parcelamento configurável igual a zero
			if(efetuarParcelamentoDebitosActionForm.get("indicadorCobrancaParcelamento").toString()
							.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_GUIA_PAGAMENTO)){

				BigDecimal valorRestante = Util.formatarMoedaRealparaBigDecimal(efetuarParcelamentoDebitosActionForm.get("valorRestante")
								.toString());

				if(!(valorRestante.compareTo(BigDecimal.ZERO) == 0)){

					throw new ActionServletException("atencao.valor_restante_configuravel_nao_equivalente");
				}
			}
		}

		NegociacaoOpcoesParcelamentoHelper negociacaoOpcoesParcelamento = (NegociacaoOpcoesParcelamentoHelper) sessao
						.getAttribute("opcoesParcelamento");

		if(negociacaoOpcoesParcelamento == null){
			negociacaoOpcoesParcelamento = new NegociacaoOpcoesParcelamentoHelper();
		}

		// //[FS0012] Verificar existência de parcelamento no mês
		// Collection<Parcelamento> colecaoParcelamento = fachada.verificarParcelamentoMesImovel(new
		// Integer(codigoImovel));
		//
		// if (colecaoParcelamento != null && !colecaoParcelamento.isEmpty()) {
		// throw new
		// ActionServletException("atencao.debito.ja.parcelado.mes.faturamento.corrente",null);
		// }

		// Pega os dados do formulário
		BigDecimal valorEntradaInformado = valorEntradaMinima;
		// if(efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado") != null
		// &&
		// !efetuarParcelamentoDebitosActionForm.get("valorEntradaInformado").toString().trim().equals("")){
		// valorEntradaInformado = Util.formatarMoedaRealparaBigDecimal((String)
		// (efetuarParcelamentoDebitosActionForm
		// .get("valorEntradaInformado")));
		// }

		Date dataEntradaParcelamento = null;
		if(efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("dataVencimentoEntradaParcelamento").equals("")){
			dataEntradaParcelamento = Util.converteStringParaDate((String) efetuarParcelamentoDebitosActionForm
							.get("dataVencimentoEntradaParcelamento"));
		}

		BigDecimal valorTotalContaValores = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorTotalContaValores") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorTotalContaValores").equals("")){
			valorTotalContaValores = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorTotalContaValores")));
		}
		BigDecimal valorGuiasPagamento = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorGuiasPagamento").equals("")){
			valorGuiasPagamento = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorGuiasPagamento")));
		}

		BigDecimal valorDebitoACobrarServico = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServico") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServico").equals("")){
			valorDebitoACobrarServico = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarServico")));
		}

		BigDecimal valorDebitoACobrarParcelamento = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamento").equals("")){
			valorDebitoACobrarParcelamento = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamento")));
		}

		BigDecimal valorCreditoARealizar = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorCreditoARealizar") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorCreditoARealizar").equals("")){
			valorCreditoARealizar = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorCreditoARealizar")));
		}

		BigDecimal valorAtualizacaoMonetaria = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorAtualizacaoMonetaria") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorAtualizacaoMonetaria").equals("")){
			valorAtualizacaoMonetaria = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorAtualizacaoMonetaria")));
		}

		BigDecimal valorJurosMora = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorJurosMora") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorJurosMora").equals("")){
			valorJurosMora = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm.get("valorJurosMora")));
		}

		BigDecimal valorMulta = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorMulta") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorMulta").equals("")){
			valorMulta = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm.get("valorMulta")));
		}

		BigDecimal valorDebitoTotalAtualizado = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizado") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoTotalAtualizado").equals("")){
			valorDebitoTotalAtualizado = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoTotalAtualizado")));
		}

		BigDecimal descontoAcrescimosImpontualidade = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoAcrescimosImpontualidade") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoAcrescimosImpontualidade").equals("")){
			descontoAcrescimosImpontualidade = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoAcrescimosImpontualidade")));
		}

		BigDecimal descontoSancoesRDEspecial = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoSancoesRDEspecial") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoSancoesRDEspecial").equals("")){
			descontoSancoesRDEspecial = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoSancoesRDEspecial")));
		}

		BigDecimal descontoTarifaSocialRDEspecial = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoTarifaSocialRDEspecial") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoTarifaSocialRDEspecial").equals("")){
			descontoTarifaSocialRDEspecial = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoTarifaSocialRDEspecial")));
		}

		BigDecimal descontoAntiguidadeDebito = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoAntiguidadeDebito") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoAntiguidadeDebito").equals("")){
			descontoAntiguidadeDebito = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoAntiguidadeDebito")));
		}

		BigDecimal descontoInatividadeLigacaoAgua = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("descontoInatividadeLigacaoAgua") != null
						&& !efetuarParcelamentoDebitosActionForm.get("descontoInatividadeLigacaoAgua").equals("")){
			descontoInatividadeLigacaoAgua = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("descontoInatividadeLigacaoAgua")));
		}

		BigDecimal percentualDescontoAcrescimosImpontualidade = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("percentualDescontoAcrescimosImpontualidade") != null
						&& !efetuarParcelamentoDebitosActionForm.get("percentualDescontoAcrescimosImpontualidade").equals("")){
			percentualDescontoAcrescimosImpontualidade = Util
							.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
											.get("percentualDescontoAcrescimosImpontualidade")));
		}

		BigDecimal percentualDescontoAntiguidadeDebito = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("percentualDescontoAntiguidadeDebito") != null
						&& !efetuarParcelamentoDebitosActionForm.get("percentualDescontoAntiguidadeDebito").equals("")){
			percentualDescontoAntiguidadeDebito = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("percentualDescontoAntiguidadeDebito")));
		}

		BigDecimal percentualDescontoInatividadeLigacaoAgua = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("percentualDescontoInatividadeLigacaoAgua") != null
						&& !efetuarParcelamentoDebitosActionForm.get("percentualDescontoInatividadeLigacaoAgua").equals("")){
			percentualDescontoInatividadeLigacaoAgua = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("percentualDescontoInatividadeLigacaoAgua")));
		}

		BigDecimal valorAcrescimosImpontualidade = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorAcrescimosImpontualidade") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorAcrescimosImpontualidade").equals("")){
			valorAcrescimosImpontualidade = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorAcrescimosImpontualidade")));
		}

		Integer parcelamentoPerfilId = Integer.valueOf((String) (efetuarParcelamentoDebitosActionForm.get("parcelamentoPerfilId")));

		BigDecimal valorDebitoACobrarServicoLongoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoLongoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoLongoPrazo").equals("")){
			valorDebitoACobrarServicoLongoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarServicoLongoPrazo")));
		}

		BigDecimal valorDebitoACobrarServicoCurtoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoCurtoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarServicoCurtoPrazo").equals("")){
			valorDebitoACobrarServicoCurtoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarServicoCurtoPrazo")));
		}

		BigDecimal valorDebitoACobrarParcelamentoLongoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoLongoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoLongoPrazo").equals("")){
			valorDebitoACobrarParcelamentoLongoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamentoLongoPrazo")));
		}

		BigDecimal valorDebitoACobrarParcelamentoCurtoPrazo = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoCurtoPrazo") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDebitoACobrarParcelamentoCurtoPrazo").equals("")){
			valorDebitoACobrarParcelamentoCurtoPrazo = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorDebitoACobrarParcelamentoCurtoPrazo")));
		}

		BigDecimal valorSucumbenciaAnterior = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorTotalSucumbenciaImovel") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorTotalSucumbenciaImovel").equals("")){
			valorSucumbenciaAnterior = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorTotalSucumbenciaImovel")));
		}

		BigDecimal valorSucumbenciaAtual = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorSucumbenciaAtual") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorSucumbenciaAtual").equals("")){
			valorSucumbenciaAtual = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorSucumbenciaAtual")));
		}

		Short quantidadeParcelasSucumbencia = Short.valueOf("0");
		if(efetuarParcelamentoDebitosActionForm.get("quantidadeParcelasSucumbencia") != null
						&& !efetuarParcelamentoDebitosActionForm.get("quantidadeParcelasSucumbencia").equals("")){
			quantidadeParcelasSucumbencia = Short.valueOf((String) (efetuarParcelamentoDebitosActionForm
							.get("quantidadeParcelasSucumbencia")));
		}

		BigDecimal valorDiligencias = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorDiligencias") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorDiligencias").equals("")){
			valorDiligencias = Util
							.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm.get("valorDiligencias")));
		}

		BigDecimal valorAtualizacaoMonetariaSucumbencia = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorAtualizacaoMonetariaSucumbenciaImovel") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorAtualizacaoMonetariaSucumbenciaImovel").equals("")){
			valorAtualizacaoMonetariaSucumbencia = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorAtualizacaoMonetariaSucumbenciaImovel")));
		}

		BigDecimal valorJurosMoraSucumbencia = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorJurosMoraSucumbenciaImovel") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorJurosMoraSucumbenciaImovel").equals("")){
			valorJurosMoraSucumbencia = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorJurosMoraSucumbenciaImovel")));
		}

		// Valor a ser Negociado
		BigDecimal valorASerParcelado = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorASerParcelado") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorASerParcelado").equals("")){
			valorASerParcelado = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorASerParcelado")));
		}

		BigDecimal valorASerNegociado = BigDecimal.ZERO;
		if(efetuarParcelamentoDebitosActionForm.get("valorNegociado") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorNegociado").equals("")){
			valorASerNegociado = Util
							.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm.get("valorNegociado")));
		}

		BigDecimal valorFinalFinanciamento = BigDecimal.ZERO;
		boolean existeValor = false;
		if(efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento").equals("")){
			valorFinalFinanciamento = Util.formatarMoedaRealparaBigDecimal((String) (efetuarParcelamentoDebitosActionForm
							.get("valorFinalFinanciamento")));
			existeValor = true;
		}
		if(efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("valorFinalFinanciamento").equals("")){
			// valorASerNegociado = Util.formatarMoedaRealparaBigDecimal((String)
			// (efetuarParcelamentoDebitosActionForm.get("valorNegociado")));
			existeValor = true;
		}
		if(!existeValor){
			if(colecaoOpcoesParcelamento != null && !colecaoOpcoesParcelamento.isEmpty()){
				Iterator opcoesParcelamentoValores = colecaoOpcoesParcelamento.iterator();
				while(opcoesParcelamentoValores.hasNext()){
					OpcoesParcelamentoHelper opcoesParcelamento = (OpcoesParcelamentoHelper) opcoesParcelamentoValores.next();
					if(!Util.isVazioOuBranco(efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))
									&& ((String) efetuarParcelamentoDebitosActionForm.get("indicadorQuantidadeParcelas"))
													.equals(opcoesParcelamento
									.getQuantidadePrestacao().toString())){
						valorFinalFinanciamento = opcoesParcelamento.getValorPrestacao().multiply(
										new BigDecimal(opcoesParcelamento.getQuantidadePrestacao()));

					}
				}
			}

			BigDecimal valorDesconto = BigDecimal.ZERO;

			if(valorDescontoAcrescimosImpontualidadeNaPrestacao != null){
				valorDesconto = valorDesconto.add(valorDescontoAcrescimosImpontualidadeNaPrestacao);
			}else{
				valorDesconto = valorDesconto.add(descontoAcrescimosImpontualidade);
			}

			valorDesconto = valorDesconto.add(descontoAntiguidadeDebito);
			valorDesconto = valorDesconto.add(descontoInatividadeLigacaoAgua);

			efetuarParcelamentoDebitosActionForm.set("valorDesconto", Util.formatarMoedaReal(valorDesconto));

			// valorASerNegociado = valorDebitoTotalAtualizado.subtract(valorDesconto);

			// valorASerParcelado =
			// valorDebitoTotalAtualizado.subtract(valorEntradaMinima.add(valorDesconto));

		}

		// Montar o objeto Imovel para as inserções no banco
		Imovel imovel = null;

		FiltroImovel filtroImovel = new FiltroImovel();

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovel));
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CONSUMO_TARIFA);

		Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
			imovel = imovelPesquisado.iterator().next();
		}

		Collection<ContaValoresHelper> colecaoContaValores = new ArrayList();
		if(sessao.getAttribute("colecaoContaValores") != null){
			colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValores");
		}else{
			colecaoContaValores = (Collection<ContaValoresHelper>) sessao.getAttribute("colecaoContaValoresImovel");
		}

		Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores = new ArrayList();
		if(sessao.getAttribute("colecaoGuiaPagamentoValoresSelecionadas") != null || indicadorGuiasPagamento.equals("2")){
			colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao
							.getAttribute("colecaoGuiaPagamentoValoresSelecionadas");
		}else{
			colecaoGuiaPagamentoValores = (Collection<GuiaPagamentoValoresHelper>) sessao.getAttribute("colecaoGuiaPagamentoValoresImovel");
		}

		Collection<DebitoACobrar> colecaoDebitoACobrar = new ArrayList();
		if(sessao.getAttribute("colecaoDebitoACobrar") != null || indicadorDebitosACobrar.equals("2")){
			colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrar");
		}else{
			colecaoDebitoACobrar = (Collection<DebitoACobrar>) sessao.getAttribute("colecaoDebitoACobrarImovel");
		}

		Collection<CreditoARealizar> colecaoCreditoARealizar = new ArrayList();
		if(sessao.getAttribute("colecaoCreditoARealizar") != null || indicadorCreditoARealizar.equals("2")){
			colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizar");
		}else{
			colecaoCreditoARealizar = (Collection<CreditoARealizar>) sessao.getAttribute("colecaoCreditoARealizarImovel");
		}

		Cliente cliente = new Cliente();
		Integer idCliente = 0;
		// Id do cliente responsável pelo parcelamento, se tiver sido informado
		// caso contrário Id do Cliente usuário do imóvel
		if(efetuarParcelamentoDebitosActionForm.get("idClienteParcelamento") != null
						&& !efetuarParcelamentoDebitosActionForm.get("idClienteParcelamento").equals("")){
			idCliente = Integer.valueOf((String) efetuarParcelamentoDebitosActionForm.get("idClienteParcelamento"));
		}else{
			idCliente = (Integer) sessao.getAttribute("idClienteImovel");
		}

		if(idCliente != 0){
			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("cliente");

			// Recupera Cliente para preencher o formulário
			Collection colecaoCliente = this.getFachada().pesquisar(filtroCliente, Cliente.class.getName());
			if(colecaoCliente != null && !colecaoCliente.isEmpty()){
				cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

				if(cliente.getClienteTipo() != null && cliente.getClienteTipo().getId() != null){
					FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();
					filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, cliente.getClienteTipo().getId()));
					Collection<ClienteTipo> colecaoClienteTipo = fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName());

					if(colecaoClienteTipo != null || !colecaoClienteTipo.isEmpty()){
						cliente.setClienteTipo((ClienteTipo) colecaoClienteTipo.iterator().next());
					}
				}

				if(cliente.getOrgaoExpedidorRg() != null && cliente.getOrgaoExpedidorRg().getId() != null){
					FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
					filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.ID, cliente.getOrgaoExpedidorRg()
									.getId()));
					filtroOrgaoExpedidorRg.setCampoOrderBy(FiltroOrgaoExpedidorRg.DESCRICAO);
					Collection colOrgaoExpedidor = fachada.pesquisar(filtroOrgaoExpedidorRg, OrgaoExpedidorRg.class.getName());

					if(colOrgaoExpedidor != null || !colOrgaoExpedidor.isEmpty()){
						cliente.setOrgaoExpedidorRg((OrgaoExpedidorRg) colOrgaoExpedidor.iterator().next());
					}
				}

				String cpfCNPJDigitado = (String) efetuarParcelamentoDebitosActionForm.get("cpfClienteParcelamentoDigitado");
				if((cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().intValue() == ClienteTipo.INDICADOR_PESSOA_JURIDICA
								.intValue())){
					if(cliente.getCnpj() == null && cpfCNPJDigitado != null && cpfCNPJDigitado != ""){
						cliente.setCnpj(cpfCNPJDigitado);
					}
				}else{
					if(cliente.getCpf() == null && cpfCNPJDigitado != null && cpfCNPJDigitado != ""){
						cliente.setCpf(cpfCNPJDigitado);
					}
				}


			}
		}

		Integer anoMesReferenciaDebitoInicial = null;

		if(!Util.isVazioOuBranco(inicioIntervaloParcelamento)){
			String anoMesReferenciaDebitoInicialStr = Util.formatarMesAnoParaAnoMesSemBarra(inicioIntervaloParcelamento);
			anoMesReferenciaDebitoInicial = Integer.valueOf(anoMesReferenciaDebitoInicialStr);
		}

		Integer anoMesReferenciaDebitoFinal = null;

		if(!Util.isVazioOuBranco(inicioIntervaloParcelamento)){
			String anoMesReferenciaDebitoFinalStr = Util.formatarMesAnoParaAnoMesSemBarra(fimIntervaloParcelamento);
			anoMesReferenciaDebitoFinal = Integer.valueOf(anoMesReferenciaDebitoFinalStr);
		}		
		
		
		/*
		 * Validaçoes do Controlador
		 */
		// valida as coleções recebidas para garantir que todos os elementos de contas, guias e
		// debitos a cobrar recebidos sejam
		// do imóvel. Alguns casos ocorreram em produção que foram passados coleções de contas
		// de outro imóvel
		Parcelamento parcelamentoValidacao = new Parcelamento();
		parcelamentoValidacao.setImovel(imovel);
		parcelamentoValidacao.setValorConta(valorTotalContaValores);
		parcelamentoValidacao.setValorGuiaPapagamento(valorGuiasPagamento);
		parcelamentoValidacao.setValorServicosACobrar(valorDebitoACobrarServico);
		parcelamentoValidacao.setValorParcelamentosACobrar(valorDebitoACobrarParcelamento);
		parcelamentoValidacao.setValorCreditoARealizar(valorCreditoARealizar);

		fachada.validarEntidadesDebitoParcelamentoImovel(parcelamentoValidacao, colecaoContaValores, colecaoGuiaPagamentoValores,
						colecaoDebitoACobrar, colecaoCreditoARealizar);
		parcelamentoValidacao = null;


		/*
		 * [SB0009] - Gerar Dados do Parcelamento
		 */
		BigDecimal valorJurosParcelamento = BigDecimal.ZERO;
		if((taxaJuros != null) && (taxaJuros.compareTo(BigDecimal.ZERO) != 0)){
			valorJurosParcelamento = valorFinalFinanciamento.add(valorEntradaInformado);
			valorJurosParcelamento = valorJurosParcelamento.subtract(valorASerNegociado);

			if(valorJurosParcelamento.compareTo(BigDecimal.ZERO) == -1){
				valorJurosParcelamento = BigDecimal.ZERO;
			}
		}

		byte[] conteudoTermoPacelamentoInicial = null;
		if(sessao.getAttribute("conteudoTermoParcelamentoInicial") != null){
			conteudoTermoPacelamentoInicial = (byte[]) (sessao.getAttribute("conteudoTermoParcelamentoInicial"));
		}

		byte[] conteudoTermoPacelamentoFinal = null;
		if(sessao.getAttribute("conteudoTermoParcelamentoFinal") != null){
			conteudoTermoPacelamentoFinal = (byte[]) (sessao.getAttribute("conteudoTermoParcelamentoFinal"));
		}

		Parcelamento parcelamento = fachada.carregarDadosParcelamento(
						dataParcelamento,
						valorTotalContaValores,
						valorGuiasPagamento,
						valorDebitoACobrarServico,
						valorDebitoACobrarParcelamento,
						valorCreditoARealizar,
						valorAtualizacaoMonetaria,
						valorJurosMora,
						valorMulta,
						valorDebitoTotalAtualizado,
						descontoAcrescimosImpontualidade,
						descontoAntiguidadeDebito,
						descontoInatividadeLigacaoAgua,
						valorEntradaInformado,
						valorJurosParcelamento,
						numeroPrestacoes,
						valorPrestacao,
						indicadorRestabelecimento.equals("") ? null : Short.valueOf(indicadorRestabelecimento),
						indicadorContasRevisao.equals("") ? null : Short.valueOf(indicadorContasRevisao),
						indicadorGuiasPagamento.equals("") ? null : Short.valueOf(indicadorGuiasPagamento),
						indicadorAcrescimosImpotualidade.equals("") ? null : Short.valueOf(indicadorAcrescimosImpotualidade),
						indicadorDebitosACobrar.equals("") ? null : Short.valueOf(indicadorDebitosACobrar),
						indicadorCreditoARealizar.equals("") ? null : Short.valueOf(indicadorCreditoARealizar),
						percentualDescontoAcrescimosImpontualidade,
						percentualDescontoAntiguidadeDebito,
						percentualDescontoInatividadeLigacaoAgua,
						imovel,
						usuarioLogado,
						parcelamentoPerfilId,
						colecaoContaValores,
						colecaoGuiaPagamentoValores,
						colecaoDebitoACobrar,
						colecaoCreditoARealizar,
						taxaJuros,
						indicadorConfirmacaoParcelamento.equals("") ? Short.valueOf((short) 1) : Short
										.valueOf(indicadorConfirmacaoParcelamento), cliente, descontoSancoesRDEspecial,
						descontoTarifaSocialRDEspecial, dataEntradaParcelamento, indicadorCobrancaParcelamento,
						anoMesReferenciaDebitoInicial, anoMesReferenciaDebitoFinal, percentualDescontoJurosMora, percentualDescontoMulta,
						percentualDescontoCorrecaoMonetaria, valorDescontoAcrescimosImpontualidadeNaPrestacao,
						colecaoParcelamentoConfiguracaoPrestacao, conteudoTermoPacelamentoInicial, conteudoTermoPacelamentoFinal,
						valorSucumbenciaAnterior, valorSucumbenciaAtual, quantidadeParcelasSucumbencia, valorDiligencias,
						valorAtualizacaoMonetariaSucumbencia, valorJurosMoraSucumbencia, valorASerNegociado, valorASerParcelado);

		CobrancaForma cobrancaForma = new CobrancaForma();
		if(indicadorCobrancaParcelamento != null
						&& indicadorCobrancaParcelamento.equals(ConstantesSistema.INDICADOR_COBRANCA_PARC_DEBITO_A_COBRAR)){
			cobrancaForma.setId(CobrancaForma.COBRANCA_EM_CONTA);
		}else{
			cobrancaForma.setId(CobrancaForma.COBRANCA_EM_BOLETO_BANCARIO);
		}
		parcelamento.setCobrancaForma(cobrancaForma);

		return parcelamento;

	}


}
