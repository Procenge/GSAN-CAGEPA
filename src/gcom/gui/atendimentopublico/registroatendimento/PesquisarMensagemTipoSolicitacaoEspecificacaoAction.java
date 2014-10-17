
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacaoMensagem;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacaoMensagem;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarMensagemTipoSolicitacaoEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("listaMensagemTipoSolicitacaoEspecificacao");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarMensagemTipoSolicitacaoEspecificacaoActionForm pesquisarActionForm = (PesquisarMensagemTipoSolicitacaoEspecificacaoActionForm) actionForm;

		// Obtendo dados do form
		String descricao = (String) pesquisarActionForm.getDescricao();
		String tipoPesquisa = (String) pesquisarActionForm.getTipoPesquisa();

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem(
						FiltroSolicitacaoTipoEspecificacaoMensagem.DESCRICAO);
		filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
						FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		if(!Util.isVazioOuBranco(descricao)){
			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ComparacaoTextoCompleto(
								FiltroSolicitacaoTipoEspecificacaoMensagem.DESCRICAO, descricao));
			}else{
				filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ComparacaoTexto(
								FiltroSolicitacaoTipoEspecificacaoMensagem.DESCRICAO, descricao));
			}
		}

		Collection<SolicitacaoTipoEspecificacaoMensagem> colecaoSolicitacaoTipoEspecificacaoMensagem = fachada.pesquisar(
						filtroSolicitacaoTipoEspecificacaoMensagem, SolicitacaoTipoEspecificacaoMensagem.class.getName());

		if(!Util.isVazioOrNulo(colecaoSolicitacaoTipoEspecificacaoMensagem)){
			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroSolicitacaoTipoEspecificacaoMensagem,
							SolicitacaoTipoEspecificacaoMensagem.class.getName());
			colecaoSolicitacaoTipoEspecificacaoMensagem = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			sessao.setAttribute("colecaoSolicitacaoTipoEspecificacaoMensagem", colecaoSolicitacaoTipoEspecificacaoMensagem);
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Solicitação Tipo Especificação Mensagem");
		}

		return retorno;
	}

}
