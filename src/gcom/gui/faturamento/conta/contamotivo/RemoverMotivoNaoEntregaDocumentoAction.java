
package gcom.gui.faturamento.conta.contamotivo;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemoverMotivoNaoEntregaDocumentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// ------------ REGISTRAR TRANSA��O ----------------
		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CONTA_MOTIVO_RETIFICACAO_REMOVER);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSA��O ----------------

		String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// mensagem de erro quando o usu�rio tenta excluir sem ter selecionado nenhum registro
		if(ids == null || ids.length == 0){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		// ------------ REGISTRAR TRANSA��O ----------------
		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSA��O ----------------

		fachada.remover(ids, MotivoNaoEntregaDocumento.class.getName(), operacaoEfetuada, colecaoUsuarios);

		// Monta a p�gina de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " Motivo(s) de n�o entrega de documento removido(s) com sucesso",
							"Realizar outra Manuten��o de Motivo de n�o entrega de documento",
							"exibirManterMotivoNaoEntregaDocumentoAction.do?menu=sim");
		}

		return retorno;

	}
}
