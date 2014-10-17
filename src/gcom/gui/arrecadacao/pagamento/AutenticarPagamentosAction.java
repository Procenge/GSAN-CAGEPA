/*
 * Copyright (C) 2007-2007 the GSAN  Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place  Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN  Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Este caso de uso gera uma autenticação do pagamento.
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

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Recupera a variável para indicar se o usuário apertou o botão de confirmar da tela de
		// confirmação
		// String confirmado = httpServletRequest.getParameter("confirmado");

		AutenticarPagamentosActionForm form = (AutenticarPagamentosActionForm) actionForm;

		if(Util.isNaoNuloBrancoZero(form.getIdPagamento())){

			// [SB0002]  Imprimir Autenticação
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

			// 1. O sistema exibe a mensagem “Confirma impress�o? SIM ou NÃO��:

			Collection<Pagamento> pagamentos = fachada.pesquisarPagamentoPeloId(Integer.valueOf(idPagamento));
			Collection<PagamentoHistorico> pagamentosHistorico = fachada.pesquisarPagamentoHistoricoPeloId(Integer.valueOf(idPagamento));
			
			if(!Util.isVazioOrNulo(pagamentos)){
				Pagamento pagamento = (Pagamento) Util.retonarObjetoDeColecao(pagamentos);

				// Im�vel
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

					// Im�vel
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

			// 1.1. Caso o usu�rio confirme, o sistema cria um identificador do pagamento
			// concatenando com:

			// 1.1.1. PARM_NMABREVIADOEMPRESA da TABELA SISTEMA_PARAMETROS
			// 1.1.2. '��-'��
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			codigoAutenticacaoPagamento = sistemaParametro.getNomeAbreviadoEmpresa() + "-";

			// 1.1.3. '��I'�� caso exista IMOV_ID em PAGAMENTO ou PAGAMENTO_HISTORICO ou '��C'��
			// caso exista
			// CLIE_ID em PAGAMENTO ou PAGAMENTO_HISTORICO.
			if(temImovel){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + "I";
			}else{
				// Caso n�o tenha imovel em Pagamento ou Pagamento Historico
				// Insere o cliente caso exista
				if(temCliente){
					codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + "C";
				}
			}
						
			// 1.1.4. IMOV_ID caso exista IMOV_ID em PAGAMENTO ou PAGAMENTO_HISTORICO ou CLIE_ID
			// caso exista CLIE_ID em PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.5. '��-'��
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
			// 1.1.7. '��-'��
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
			// 1.1.9. '��-'��
			if(!Util.isVazioOuBranco(valorPagamento)){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + valorPagamento + "-";
			}

			// 1.1.10. PGMT_DTPAGAMENTO ou PGHI_DTPAGAMENTO no formato YYYYMMDD de PAGAMENTO ou
			// PAGAMENTO_HISTORICO.
			// 1.1.11. '��-'��
			if(!Util.isVazioOuBranco(dataPagamento)){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + dataPagamento + "-";
			}

			// 1.1.12. USUR_ID de PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.13. '��-'��
			if(!Util.isVazioOuBranco(usuarioPagamento)){
				codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + usuarioPagamento + "-";
			}

			// 1.1.14. PGMT_ID ou PGHI_ID de PAGAMENTO ou PAGAMENTO_HISTORICO.
			// 1.1.15. '��.'��
			codigoAutenticacaoPagamento = codigoAutenticacaoPagamento + idPagamento + ".";

			// 1.2. E em seguida envia para impress�o.
			httpServletRequest.setAttribute("codigoAutenticacaoPagamento", codigoAutenticacaoPagamento);

			httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/exibirAutenticarPagamentoAction.do");

			return montarPaginaConfirmacaoImpressao("atencao.gerar_autenticacao.confirmacao", httpServletRequest, actionMapping);
			// }
		}else{
			throw new ActionServletException("atencao.nenhum.pagamento.selecionado");
		}
		
	}

}
