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

import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RetirarValorAguaEsgotoConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirRetirarValorAguaEsgotoConjuntoConta");

		HttpSession sessao = httpServletRequest.getSession(false);

		// Instância do formulário que está sendo utilizado
		CancelarContaActionForm cancelarContaActionForm = (CancelarContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		
		// MotivoReatificacaoConta selecinado pelo usuário
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(new Integer(cancelarContaActionForm.getIdMotivoRetificacao()));

		// MotivoCancelamentoConta selecinado pelo usuário
		ContaMotivoCancelamento contaMotivoCancelamento = new ContaMotivoCancelamento();
		contaMotivoCancelamento.setId(new Integer(cancelarContaActionForm.getMotivoCancelamentoContaID()));

		String cdValorARetirar = cancelarContaActionForm.getCdValorARetirar();

//		Collection debitosTipoRetirar = new ArrayList();
//
//		if(sessao.getAttribute("debitosTipoRetirar") != null){
//			debitosTipoRetirar = (Collection) sessao.getAttribute("debitosTipoRetirar");
//		}else{
//			throw new ActionServletException("atencao.campo.informado", null, "Tipo de Débito");
//		}
//
//		if(sessao.getAttribute("colecaoImovel") != null){
//
//			Collection<Conta> colecaoImovel = (Collection) sessao.getAttribute("colecaoImovel");
//
//			Integer anoMes = null;
//			if(sessao.getAttribute("anoMes") != null){
//				anoMes = (Integer) sessao.getAttribute("anoMes");
//			}
//
//			Integer anoMesFim = null;
//			if(sessao.getAttribute("anoMesFim") != null){
//				anoMesFim = (Integer) sessao.getAttribute("anoMesFim");
//			}
//
//			Date dataVencimentoContaInicio = null;
//			Date dataVencimentoContaFim = null;
//			Integer idGrupoFaturamento = null;
//
//			if(sessao.getAttribute("dataVencimentoContaInicial") != null){
//
//				dataVencimentoContaInicio = (Date) sessao.getAttribute("dataVencimentoContaInicial");
//			}
//
//			if(sessao.getAttribute("dataVencimentoContaFinal") != null){
//
//				dataVencimentoContaFim = (Date) sessao.getAttribute("dataVencimentoContaFinal");
//			}
//
//			if(sessao.getAttribute("idGrupoFaturamento") != null){
//
//				idGrupoFaturamento = (Integer) sessao.getAttribute("idGrupoFaturamento");
//			}

			// Retificar uma ou várias contas
			// Usuario logado no sistema
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

			// Cancelando uma ou várias contas
			Collection<Conta> colecaoContasSelecionadas = new ArrayList<Conta>();

			// Carregando contas selecionadas
		String contaSelected = cancelarContaActionForm.getContaSelected();

			if(!Util.isVazioOuBranco(contaSelected)){
				// Contas selecionadas pelo usuário
				String[] arrayIdentificadores = contaSelected.split(",");

				for(int i = 0; i < arrayIdentificadores.length; i++){

					String dadosConta = arrayIdentificadores[i];
					String[] idContaArray = dadosConta.split("-");
					Integer idConta = new Integer(idContaArray[0]);

					// [FS0021] Verificar situação da conta
					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.QUADRA);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.SETOR_COMERCIAL);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LOCALIDADE);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
					Collection colecaoContas = fachada.pesquisar(filtroConta, Conta.class.getName());
					Conta contaSelecao = (Conta) colecaoContas.iterator().next();

					if(contaSelecao != null){
						if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){
							throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
											.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "ação");
						}
					}

					colecaoContasSelecionadas.add(contaSelecao);
				}
			}

		fachada.retirarValorAguaEsgotoConjuntoContas(colecaoContasSelecionadas, Integer.parseInt(cdValorARetirar), contaMotivoRetificacao,
						contaMotivoCancelamento, usuarioLogado);
			// Integer codigoCliente = null;
			// if(sessao.getAttribute("codigoCliente") != null){
			// codigoCliente = new Integer((String) sessao.getAttribute("codigoCliente"));
			// }
			//
			// if(codigoCliente != null){
			// Integer relacaoTipo = null;
			// if(sessao.getAttribute("relacaoTipo") != null){
			// relacaoTipo = new Integer(sessao.getAttribute("relacaoTipo") + "");
			// }
			// fachada.retificarConjuntoContaCliente(codigoCliente, relacaoTipo, anoMes,
			// contaMotivoRetificacao, debitosTipoRetirar,
			// usuarioLogado, dataVencimentoContaInicio, dataVencimentoContaFim, anoMesFim,
			// colecaoContasSelecionadas);
			// }else if(idGrupoFaturamento != null){
			//
			// fachada.retificarConjuntoConta(idGrupoFaturamento, anoMes, contaMotivoRetificacao,
			// debitosTipoRetirar, usuarioLogado,
			// dataVencimentoContaInicio, dataVencimentoContaFim, anoMesFim,
			// colecaoContasSelecionadas);
			// }else{
			//
			// fachada.retificarConjuntoConta(colecaoImovel, anoMes, contaMotivoRetificacao,
			// debitosTipoRetirar, usuarioLogado,
			// dataVencimentoContaInicio, dataVencimentoContaFim, anoMesFim,
			// colecaoContasSelecionadas);
			// }

			// Realizar um reload na tela de manter conjunto conta
			httpServletRequest.setAttribute("reloadPage", "OK");

			// Realizar um reload na tela de manter conta
			httpServletRequest.setAttribute("fecharPopup", "OK");

		// }
		// sessao.setAttribute("cancelar", "1");
		//sessao.removeAttribute("anoMes");

		return retorno;
	}

}
