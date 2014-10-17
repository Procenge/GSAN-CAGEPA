/*
 * Copyright (C) 2007-2007 the GSAN ¬ñ Sistema Integrado de Gest√£o de Servi√ßos de Saneamento
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
 * Foundation, Inc., 59 Temple Place ¬ñ Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN ¬ñ Sistema Integrado de Gest√£o de Servi√ßos de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara√∫jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl√°udio de Andrade Lira
 * Denys Guimar√£es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab√≠ola Gomes de Ara√∫jo
 * Fl√°vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J√∫nior
 * Homero Sampaio Cavalcanti
 * Ivan S√©rgio da Silva J√∫nior
 * Jos√© Edmar de Siqueira
 * Jos√© Thiago Ten√≥rio Lopes
 * K√°ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M√°rcio Roberto Batista da Silva
 * Maria de F√°tima Sampaio Leite
 * Micaela Maria Coelho de Ara√∫jo
 * Nelson Mendon√ßa de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr√™a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara√∫jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S√°vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa √© software livre; voc√™ pode redistribu√≠-lo e/ou
 * modific√°-lo sob os termos de Licen√ßa P√∫blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers√£o 2 da
 * Licen√ßa.
 * Este programa √© distribu√≠do na expectativa de ser √∫til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl√≠cita de
 * COMERCIALIZA√á√ÉO ou de ADEQUA√á√ÉO A QUALQUER PROP√ìSITO EM
 * PARTICULAR. Consulte a Licen√ßa P√∫blica Geral GNU para obter mais
 * detalhes.
 * Voc√™ deve ter recebido uma c√≥pia da Licen√ßa P√∫blica Geral GNU
 * junto com este programa; se n√£o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3094] Autenticar Pagamento
 * Este caso de uso gera uma autentica√ß√£o do pagamento.
 * 
 * @author Josenildo Neves
 * @created 08 de abril de 2013
 */
public class AutenticarPagamentosAction
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

		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();
				
		String codigoAutenticacaoPagamento = null;

		// ActionForward retorno = actionMapping.findForward("exibirAutenticarPagamentoAction");

		// Mudar isso quando tiver esquema de seguran√ßa
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Recupera a vari√°vel para indicar se o usu√°rio apertou o bot√£o de confirmar da tela de
		// confirma√ß√£o
		// String confirmado = httpServletRequest.getParameter("confirmado");

		AutenticarPagamentosActionForm form = (AutenticarPagamentosActionForm) actionForm;

		if(Util.isNaoNuloBrancoZero(form.getIdPagamento())){

			// [SB0002] ¬ñ Imprimir Autentica√ß√£o
			// if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
			String idPagamento = form.getIdPagamento();
			String idImovel = null;
			String idCliente = null;
			String idConta = null;
			String anoMesReferenciaConta = null;
			String idGuiaPagamento = null;
			String numeroPrestacaoGuiaPagamento = null;
			String valorPagamento = null;
			String dataPagamento = form.getDataPagamento();
			String usuarioPagamento = usuarioLogado.getId().toString();

			boolean temImovel = false;
			boolean temCliente = false;
			boolean temConta = false;
			boolean temGuia = false;

			// 1. O sistema exibe a mensagem ‚ÄúConfirma impress„o? SIM ou N√ÉOÄù:

			Collection<Pagamento> pagamentos = fachada.pesquisarPagamentoPeloId(Integer.valueOf(idPagamento));
			Collection<PagamentoHistorico> pagamentosHistorico = fachada.pesquisarPagamentoHistoricoPeloId(Integer.valueOf(idPagamento));
			
			if(!Util.isVazioOrNulo(pagamentos)){
				Pagamento pagamento = (Pagamento) Util.retonarObjetoDeColecao(pagamentos);

				// ImÛvel
				if(!Util.isVazioOuBranco(pagamento.getImovel())){					
					idImovel = pagamento.getImovel().getId().toString();
					temImovel = true;
				}

				// Cliente
				if(!Util.isVazioOuBranco(pagamento.getCliente())){
					idCliente = pagamento.getCliente().getId().toString();
					temCliente = true;
				}

				// Conta
				if(!Util.isVazioOuBranco(pagamento.getConta())){
					idConta = pagamento.getConta().getId().toString();

					Conta conta = fachada.pesquisarContaPeloID(Integer.valueOf(idConta));

					anoMesReferenciaConta = conta.getReferencia() + "";
					temConta = true;
				}

				// Guia
				if(!Util.isVazioOuBranco(pagamento.getGuiaPagamentoGeral())){
					idGuiaPagamento = pagamento.getGuiaPagamentoGeral().getId().toString();

					numeroPrestacaoGuiaPagamento = pagamento.getNumeroPrestacao().toString();
					temGuia = true;
				}

				// Valor Pagamento
				if(!Util.isVazioOuBranco(pagamento.getValorPagamento())){
					valorPagamento = Util.formataBigDecimal(pagamento.getValorPagamento(), 2, true);
				}

			}else{
			
				if(!Util.isVazioOrNulo(pagamentosHistorico)){
					PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) Util.retonarObjetoDeColecao(pagamentosHistorico);

					// ImÛvel
					if(!Util.isVazioOuBranco(pagamentoHistorico.getImovel())){					
						idImovel = pagamentoHistorico.getImovel().getId().toString();
						temImovel = true;
					}

					// Cliente
					if(!Util.isVazioOuBranco(pagamentoHistorico.getCliente())){
						idCliente = pagamentoHistorico.getCliente().getId().toString();
						temCliente = true;
					}

					// Conta
					if(!Util.isVazioOuBranco(pagamentoHistorico.getConta())){
						idConta = pagamentoHistorico.getConta().getId().toString();

						FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
						filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ID, idConta));

						Collection colecaoContaHistorico = fachada.pesquisar(filtroContaHistorico, ContaHistorico.class.getName());

						if(!Util.isVazioOrNulo(colecaoContaHistorico)){
							ContaHistorico contaHistorico = (ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico);

							anoMesReferenciaConta = contaHistorico.getAnoMesReferenciaConta() + "";
							temConta = true;
						}
					}

					// Guia
					if(!Util.isVazioOuBranco(pagamentoHistorico.getGuiaPagamentoGeral())){
						idGuiaPagamento = pagamentoHistorico.getGuiaPagamentoGeral().getId().toString();

						numeroPrestacaoGuiaPagamento = pagamentoHistorico.getNumeroPrestacao().toString();
						temGuia = true;
					}

					// Valor Pagamento
					if(!Util.isVazioOuBranco(pagamentoHistorico.getValorPagamento())){
						valorPagamento = Util.formataBigDecimal(pagamentoHistorico.getValorPagamento(), 2, true);
					}
				}

			}

			// 1.1. Caso o usu·rio confirme, o sistema cria um identificador do pagamento
			// concatenando com:

			// 1.1.1. PARM_NMABREVIADOEMPRESA da TABELA SISTEMA_PARAMETROS
			// 1.1.2. 'Äò-'Äò
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			codigoAutenticacaoPagamento = sistemaParametro.getNomeAbreviadoEmpresa() + "-";

			// 1.1.3. 'ÄòI'Äô caso exista IMOV_ID em PAGAMENTO ou PAGAMENTO_HISTORICO ou 'ÄòC'Äô
			// caso exista
			// CLIE_ID em PAGAMENTO ou PAGAMENTO_HISTORICO.
			if(temImovel){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + "I";
			}else{
				// Caso n√o tenha imovel em Pagamento ou Pagamento Historico
				// Insere o cliente caso exista
				if(temCliente){
					codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + "C";
				}
			}
						
			// 1.1.4. IMOV_ID caso exista IMOV_ID em PAGAMENTO ou PAGAMENTO_HISTORICO ou CLIE_ID
			// caso exista CLIE_ID em PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.5. 'Äò-'Äò
			if(temImovel){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + idImovel + "-";
			}else{
				if(temCliente){
					codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + idCliente + "-";
				}
			}
			
			// 1.1.6. CNTA_AMREFERENCIACONTA ou CNHI_AMREFERENCIACONTA caso exista CNTA_ID em
			// PAGAMENTO ou PAGAMENTO_HISTORICO ou
			// GPAG_ID e GPPR_NNPRESTACAO caso exista GPAG_ID em PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.7. 'Äò-'Äò
			if(temConta){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + anoMesReferenciaConta + "-";
			}else{
				if(temGuia){
					if(!Util.isVazioOuBranco(idGuiaPagamento) && !Util.isVazioOuBranco(numeroPrestacaoGuiaPagamento)){
						codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + idGuiaPagamento + " " + numeroPrestacaoGuiaPagamento
										+ "-";
					}
				}
			}

			// 1.1.8. PGMT_VLPAGAMENTO ou PGHI_VLPAGAMENTO de PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.9. 'Äò-'Äò
			if(!Util.isVazioOuBranco(valorPagamento)){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + valorPagamento + "-";
			}

			// 1.1.10. PGMT_DTPAGAMENTO ou PGHI_DTPAGAMENTO no formato YYYYMMDD de PAGAMENTO ou
			// PAGAMENTO_HISTORICO.
			// 1.1.11. 'Äò-'Äò
			if(!Util.isVazioOuBranco(dataPagamento)){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + dataPagamento + "-";
			}

			// 1.1.12. USUR_ID de PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.13. 'Äò-'Äò
			if(!Util.isVazioOuBranco(usuarioPagamento)){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + usuarioPagamento + "-";
			}

			// 1.1.14. PGMT_ID ou PGHI_ID de PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.15. 'Äò.'Äô
			codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + idPagamento + ".";

			// 1.2. E em seguida envia para impress„o.
			httpServletRequest.setAttribute("codigoAutenticacaoPagamento", codigoAutenticacaoPagamento);

			httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/exibirAutenticarPagamentoAction.do");

			return montarPaginaConfirmacaoImpressao("atencao.gerar_autenticacao.confirmacao", httpServletRequest, actionMapping);
			// }
		}else{
			throw new ActionServletException("atencao.nenhum.pagamento.selecionado");
		}
		
	}

}
