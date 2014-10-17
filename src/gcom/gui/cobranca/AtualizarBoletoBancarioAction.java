
package gcom.gui.cobranca;

import gcom.cobranca.BoletoBancario;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class AtualizarBoletoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		BoletoBancario boletoBancario = (BoletoBancario) sessao.getAttribute("boletoBancarioAtualizacao");

		AtualizarBoletoBancarioActionForm form = (AtualizarBoletoBancarioActionForm) actionForm;

		// Lançamento de Envio
		Integer idBoletoBancarioLancamentoEnvio = null;

		String idBoletoBancarioLancamentoEnvioStr = form.getIdBoletoBancarioLancamentoEnvio();

		if(!Util.isVazioOuBranco(idBoletoBancarioLancamentoEnvioStr)){
			idBoletoBancarioLancamentoEnvio = Integer.valueOf(idBoletoBancarioLancamentoEnvioStr);
		}

		// Data de Vencimento do Título
		Date dataVencimentoTitulo = null;

		String dataVencimentoTituloStr = form.getDataVencimentoTitulo();

		if(!Util.isVazioOuBranco(dataVencimentoTituloStr)){
			dataVencimentoTitulo = Util.converteStringParaDate(dataVencimentoTituloStr);
		}

		// Atualizar Boleto Bancário
		fachada.atualizarBoletoBancario(boletoBancario, idBoletoBancarioLancamentoEnvio, dataVencimentoTitulo, usuario);

		montarPaginaSucesso(httpServletRequest, "Boleto Bancário - " + form.getNumeroSequencialBoletoBancario()
						+ " - atualizado com sucesso.", "Realizar outra Manutenção de Boleto Bancário",
						"exibirFiltrarBoletoBancarioAction.do?menu=sim");

		return retorno;
	}
}
