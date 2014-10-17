/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class GerarGuiaImovelAguaParaTodosAction
				extends GcomAction {

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 26/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		GerarGuiaImovelAguaParaTodosActionForm form = (GerarGuiaImovelAguaParaTodosActionForm) actionForm;

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Integer idImovel = null;
		if(!Util.isVazioOuBranco(form.getMatricula())){
			idImovel = Integer.valueOf(form.getMatricula());
		}else{
			throw new ActionServletException("atencao.informe_matricula_imovel", null, form.getMatricula());
		}

		form.setMatricula(null);

		BigDecimal valorGuia = BigDecimal.ZERO;
		if(!Util.isVazioOuBranco(form.getValorGuia())){
			String valorGuiaStr = form.getValorGuia().replaceAll(",", ".");
			valorGuia = new BigDecimal(valorGuiaStr);
		}

		Collection<ContaValoresHelper> colecaoContaValores = (Collection) sessao.getAttribute("colecaoContaValores");

		Collection<Conta> contas = new ArrayList<Conta>();

		if(!Util.isVazioOrNulo(colecaoContaValores)){
			for(ContaValoresHelper contaValores : colecaoContaValores){
				if(contaValores.getConta() != null){
					contas.add(contaValores.getConta());
				}
			}
		}

		// [UC3053 - Gerar Guia de Pagamento Água para Todos]
		fachada.gerarGuiaPagamentoProgramaAguaParaTodos(idImovel, valorGuia, contas, usuarioLogado);

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			String mensagem = "Geração da guia de pagamento para inclusão do imóvel " + idImovel
							+ " no Programa Água Para Todos efetuada com sucesso.";

			montarPaginaSucesso(httpServletRequest, mensagem, "Gerar Guia para outro Imóvel", "exibirGerarGuiaImovelAguaParaTodosAction.do");
		}

		return retorno;
	}
}
