
package gcom.gui.atendimentopublico.registroatendimento;

import java.util.Collection;

import gcom.atendimentopublico.registroatendimento.FiltroMeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirReiterarRegistroAtendimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirReiterarRegistroAtendimentoAction");
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		RegistroAtendimento registroAtendimento = fachada.obterDadosRegistroAtendimento(
						Integer.parseInt(httpServletRequest.getParameter("numeroRA"))).getRegistroAtendimento();
		sessao.setAttribute("registroAtendimento", registroAtendimento);

		FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();
		filtroMeioSolicitacao.setConsultaSemLimites(true);
		filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoMeioSolicitacao = fachada.pesquisar(filtroMeioSolicitacao, MeioSolicitacao.class.getName());

		if(colecaoMeioSolicitacao == null || colecaoMeioSolicitacao.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "MEIO_SOLICITACAO");
		}else{
			sessao.setAttribute("colecaoMeioSolicitacao", colecaoMeioSolicitacao);
		}
		Collection<RegistroAtendimento> RAsReiteradas = null;
		try{
			RAsReiteradas = fachada.listarDuasUltimasRAsReiteradas(registroAtendimento);
			if(RAsReiteradas != null && !RAsReiteradas.isEmpty()){
				for(RegistroAtendimento registroAtendimentoReiterada : RAsReiteradas){
					registroAtendimentoReiterada.setRegistroAtendimentoUnidade((RegistroAtendimentoUnidade) registroAtendimentoReiterada
									.getRegistroAtendimentoUnidades().iterator().next());
				}
			}
		}catch(ControladorException e){
			e.printStackTrace();
		}
		sessao.setAttribute("colecaoRAsReiteradas", RAsReiteradas);
		return retorno;
	}

}