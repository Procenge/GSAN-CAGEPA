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

package gcom.gui.faturamento.conta;

import gcom.arrecadacao.pagamento.FiltroPagamentoHistorico;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.conta.FiltroContaHistorico;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Desfaz o cancelamento e/ou retificação da conta
 * [UC0327] Desfazer Cancelamento e/ou Retificação de Conta
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
 */
public class ManterDesfazerCancelamentoRetificacaoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		DesfazerCancelamentoRetificacaoContaActionForm desfazerCancelamentoRetificacaoContaActionForm = (DesfazerCancelamentoRetificacaoContaActionForm) actionForm;

		String[] registrosRemocao = desfazerCancelamentoRetificacaoContaActionForm.getIdRegistrosRemocao();

		String idImovel = desfazerCancelamentoRetificacaoContaActionForm.getIdImovel();

		Collection colecaoContasRemocao = new ArrayList();
		if(sessao.getAttribute("contas") != null){
			Collection contas = (Collection) sessao.getAttribute("contas");

			Iterator iteratorContas = contas.iterator();
			while(iteratorContas.hasNext()){
				// ContaHistorico contaHistorico = (ContaHistorico) iteratorContas.next();
				for(int i = 0; i < registrosRemocao.length; i++){
					String idConta = registrosRemocao[i];
					Object object = iteratorContas.next();
					if(object instanceof Conta){

						Conta conta = (Conta) object;

						if(idConta.equals(conta.getId().toString())){
							
							// Verifica se já existe um pagamento em histórico para essa conta
							FiltroContaHistorico filtroContaHistorico = new FiltroContaHistorico();
							filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.IMOVEL_ID, conta.getImovel()
											.getId()));
							filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.ANO_MES_REFERENCIA, Integer
											.valueOf(conta.getReferencia())));
							filtroContaHistorico.adicionarParametro(new ParametroSimples(FiltroContaHistorico.DEBITO_CREDITO_SITUACAO_ATUAL,
											DebitoCreditoSituacao.CANCELADA_POR_RETIFICACAO));
							Collection<ContaHistorico> colecaoContaHistorico = Fachada.getInstancia().pesquisar(filtroContaHistorico,
											ContaHistorico.class.getName());

							FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
							filtroPagamentoHistorico.adicionarParametro(new ParametroSimplesColecao(FiltroPagamentoHistorico.CONTA_ID,
											((ContaHistorico) Util.retonarObjetoDeColecao(colecaoContaHistorico)).getId()));
							Collection colecaoPagamentoHistorico = Fachada.getInstancia().pesquisar(filtroPagamentoHistorico,
											PagamentoHistorico.class.getName());
							if(colecaoPagamentoHistorico != null && colecaoPagamentoHistorico.size() > 0){
								throw new ActionServletException("atencao.conta.possui.pagamento.historico");
							}
							colecaoContasRemocao.add(conta);
						}
					}else if(object instanceof ContaHistorico){

						ContaHistorico contaHistorico = (ContaHistorico) object;

						if(idConta.equals(contaHistorico.getId().toString())){
							
							// Verifica se já existe um pagamento em histórico para essa conta
							FiltroPagamentoHistorico filtroPagamentoHistorico = new FiltroPagamentoHistorico();
							filtroPagamentoHistorico.adicionarParametro(new ParametroSimplesColecao(FiltroPagamentoHistorico.CONTA_ID,
											contaHistorico.getId()));
							Collection colecaoPagamentoHistorico = Fachada.getInstancia().pesquisar(filtroPagamentoHistorico,
											PagamentoHistorico.class.getName());
							if(colecaoPagamentoHistorico != null && colecaoPagamentoHistorico.size() > 0){
								throw new ActionServletException("atencao.conta.possui.pagamento.historico");
							}
							colecaoContasRemocao.add(contaHistorico);
						}

					}

				}
			}
		}

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		// [UC0329] - Restabelecer Situação Anterior de Conta
		fachada.restabelecerSituacaoAnteriorConta(colecaoContasRemocao, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Desfazimento do Cancelamento e/ou Retificação de " + registrosRemocao.length
						+ " Conta(s) do imóvel " + idImovel + " efetuado com sucesso.",
						"Desfazer Cancelamento e/ou Retificação de outra Conta",
						"exibirManterDesfazerCancelamentoRetificacaoContaAction.do?menu=sim");

		sessao.removeAttribute("contas");
		return retorno;
	}
}
