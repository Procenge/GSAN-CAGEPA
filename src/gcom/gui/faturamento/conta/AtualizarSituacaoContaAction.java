
package gcom.gui.faturamento.conta;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite atualizar as contas Pré-Faturadas
 * [UC3035] Concluir Faturamento Contas Pré-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 22/02/2012
 */
public class AtualizarSituacaoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String idAnterior = httpServletRequest.getParameter("idAnterior");
		String idPosterior = httpServletRequest.getParameter("idPosterior");

		Conta conta = (Conta) sessao.getAttribute("conta");
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Atualizar Boleto Bancário
		fachada.atualizarSituacaoConta(conta, usuario);

		// Remove da coleção a conta atualizada
		Collection<Conta> colecaoContasPreFaturadas = (Collection<Conta>) sessao.getAttribute("colecaoContasPreFaturadas");

		if(!Util.isVazioOrNulo(colecaoContasPreFaturadas)){
			Iterator iterator = colecaoContasPreFaturadas.iterator();
			Conta contaRemover = new Conta();
			Conta contaRemocao = new Conta();

			while(iterator.hasNext()){
				contaRemover = (Conta) iterator.next();
				if(conta.getId().equals(contaRemover.getId())){
					contaRemocao = contaRemover;
				}
			}
			if(contaRemocao.getId() != null){
				if(colecaoContasPreFaturadas.contains(contaRemocao)){
					colecaoContasPreFaturadas.remove(contaRemocao);
				}
			}

		}

		Collection<Integer> colecaoIdcontas = (Collection<Integer>) sessao.getAttribute("colecaoIdcontas");

		if(!Util.isVazioOuBranco(colecaoIdcontas)){
			Iterator iterator = colecaoIdcontas.iterator();
			Integer contaColecaoIdContas = null;
			Integer idContaRemocao = null;

			while(iterator.hasNext()){
				contaColecaoIdContas = (Integer) iterator.next();
				if(conta.getId().equals(contaColecaoIdContas)){
					idContaRemocao = contaColecaoIdContas;
				}
			}
			colecaoIdcontas.remove(idContaRemocao);
		}

		// 2.6.1.4. Apresenta a tela de sucesso
		String contaIDParametro = conta.getId().toString();
		String idImovelParametro = conta.getImovel().getId().toString();
		String referenciaParametro = Integer.valueOf(conta.getReferencia()).toString();
		String proximaConta = "";

		if(!Util.isVazioOuBranco(idAnterior) || !Util.isVazioOuBranco(idPosterior)){

			if(!Util.isVazioOuBranco(idPosterior)){
				proximaConta = idPosterior;
			}else{
				proximaConta = idAnterior;
			}

			montarPaginaSucesso(httpServletRequest, "Conta do imóvel " + conta.getImovel().getId() + " e referência "
							+ conta.getReferencia() + " atualizada com sucesso.", "Menu Principal", "telaPrincipal.do?menu=sim",
							"exibirFiltrarContasPreFaturadasAction.do?desfazer=S", "Concluir o Faturamento de Outras Contas Pré-Faturadas",
							"Retificar Conta", "exibirRetificarContaAction.do?contaID=" + contaIDParametro + "&idImovel="
											+ idImovelParametro + "&referencia=" + referenciaParametro, "Voltar",
							"exibirAtualizarSituacaoContaAction.do?idConta=" + proximaConta);
		}else{
			montarPaginaSucesso(httpServletRequest, "Conta do imóvel " + conta.getImovel().getId() + " e referência "
							+ conta.getReferencia() + " atualizada com sucesso.", "Menu Principal", "telaPrincipal.do?menu=sim",
							"exibirFiltrarContasPreFaturadasAction.do?desfazer=S", "Concluir o Faturamento de Outras Contas Pré-Faturadas",
							"Retificar Conta", "exibirRetificarContaAction.do?contaID=" + contaIDParametro + "&idImovel="
											+ idImovelParametro + "&referencia=" + referenciaParametro, null, null);
		}

		sessao.setAttribute("proximaConta", proximaConta);

		return retorno;
	}
}
