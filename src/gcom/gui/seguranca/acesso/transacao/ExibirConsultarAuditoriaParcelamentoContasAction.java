package gcom.gui.seguranca.acesso.transacao;

import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirConsultarAuditoriaParcelamentoContasAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarAuditoriaParcelamentoContas");

		Fachada fachada = Fachada.getInstancia();

		// Form
		ConsultarAuditoriaParcelamentoContasActionForm form = (ConsultarAuditoriaParcelamentoContasActionForm) actionForm;

		// Data atual
		if(form.getDataInicial() == null && form.getDataFinal() == null){
			form.setDataInicial(Util.formatarData(new Date()));
			form.setDataFinal(Util.formatarData(new Date()));
		}

		String idUsuario = form.getIdUsuario();

		// Filtro Usuario
		if(!Util.isVazioOuBranco(idUsuario)){

			FiltroUsuario filtroUsuario = new FiltroUsuario();
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idUsuario));
			Usuario usuario = (Usuario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUsuario, Usuario.class.getName()));
			if(!Util.isVazioOuBranco(usuario)){
				form.setIdUsuario(usuario.getId().toString());
				form.setNomeUsuario(usuario.getNomeUsuario());
			}else{
				form.setIdUsuario("");
				form.setNomeUsuario("Usuário inexistente");
				httpServletRequest.setAttribute("usuarioNaoEncontrado", true);
				httpServletRequest.setAttribute("nomeCampo", "idUsuario");
			}

		}

		// Recupera os valores do funcionário do form
		String idFuncionario = form.getIdFuncionario();
		String nomeFuncionario = form.getNomeFuncionario();

		// Reset no form
		String reset = httpServletRequest.getParameter("reset");
		if(reset != null && reset.equalsIgnoreCase("1")){
			form.setIdFuncionario("");
			form.setNomeFuncionario("");
			form.setIdUsuario("");
			form.setDataInicial(Util.formatarData(new Date()));
			form.setDataFinal(Util.formatarData(new Date()));
		}


		// ---Parte que trata o código quando o usuário tecla enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(objetoConsulta != null && objetoConsulta.equals("1")){
			if(idFuncionario != null && !idFuncionario.trim().equals("")){
				FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
				filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, idFuncionario));

				Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

				if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){

					Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

					form.setNomeFuncionario(funcionario.getNome());

				}else{

					form.setIdFuncionario("");
					form.setNomeFuncionario("Funcionário inexistente");
					httpServletRequest.setAttribute("idFuncionarioNaoEncontrado", "true");
					httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

				}

			}else if(nomeFuncionario != null && !nomeFuncionario.trim().equals("")
							&& (idFuncionario == null || idFuncionario.trim().equals(""))){
				form.setNomeFuncionario("");
			}

		}


		return retorno;
	}

}
