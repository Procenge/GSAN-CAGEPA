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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirConsultarAuditoriaTransferenciaDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarAuditoriaTransferenciaDebitos");

		Fachada fachada = Fachada.getInstancia();

		// Form
		ConsultarAuditoriaTransferenciaDebitosActionForm form = (ConsultarAuditoriaTransferenciaDebitosActionForm) actionForm;

		// Recupera os valores do funcionário do form
		String idFuncionario = form.getIdFuncionario();
		String nomeFuncionario = form.getNomeFuncionario();
		String idUsuarioDestino = form.getNomeUsuarioDestino();
		String idUsuarioOrigem = form.getNomeUsuarioOrigem();

		// Filtro Usuario Destino
		if(!Util.isVazioOuBranco(idUsuarioDestino)){

			FiltroUsuario filtroUsuario = new FiltroUsuario();
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idUsuarioDestino));
			Usuario usuario = (Usuario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUsuario, Usuario.class.getName()));
			if(!Util.isVazioOuBranco(usuario)){
				form.setIdUsuarioDestino(usuario.getId().toString());
				form.setNomeUsuarioDestino(usuario.getNomeUsuario());
			}else{
				form.setIdUsuarioDestino("");
				form.setNomeUsuarioDestino("Usuário inexistente");
				httpServletRequest.setAttribute("usuarioNaoEncontradoDestino", true);
				httpServletRequest.setAttribute("nomeCampo", "idUsuarioDestino");
			}

		}

		// Filtro Usuario Origem
		if(!Util.isVazioOuBranco(idUsuarioOrigem)){

			FiltroUsuario filtroUsuario = new FiltroUsuario();
			filtroUsuario.adicionarParametro(new ParametroSimples(FiltroUsuario.ID, idUsuarioOrigem));
			Usuario usuario = (Usuario) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroUsuario, Usuario.class.getName()));
			if(!Util.isVazioOuBranco(usuario)){
				form.setIdUsuarioOrigem(usuario.getId().toString());
				form.setNomeUsuarioOrigem(usuario.getNomeUsuario());
			}else{
				form.setIdUsuarioOrigem("");
				form.setNomeUsuarioOrigem("Usuário inexistente");
				httpServletRequest.setAttribute("usuarioNaoEncontradoOrigem", true);
				httpServletRequest.setAttribute("nomeCampo", "idUsuarioOrigem");
			}

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
