
package gcom.gui.gestaoLeitura;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Felipe Rosacruz
 * @date 24/03/2014
 */
public class GcsME
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("gcsME");
		
		StringBuilder gestaoLeituraEndereco = new StringBuilder("http://");
		try{
			gestaoLeituraEndereco.append(ParametroMicromedicao.P_GESTAO_LEITURA_ENDERECO.executar());
		}catch(ControladorException e){
			e.printStackTrace();
			throw new ActionServletException("erro.parametro.sistema.gestao.leitura.endereco");
		}

		String paginaGCSME = httpServletRequest.getParameter("paginaGCSME");
		gestaoLeituraEndereco.append("/" + paginaGCSME);

		Usuario usuariologado = getUsuarioLogado(httpServletRequest);
		Object[] dadosAcessoGcsME = null;
		if(usuariologado.getFuncionario() != null){
			dadosAcessoGcsME = Fachada.getInstancia().consultarDadosAcessoGcsME(usuariologado);

			if(dadosAcessoGcsME == null || dadosAcessoGcsME[1] == null || dadosAcessoGcsME[2] == null){
				throw new ActionServletException("atencao.usuario.logado.nao.acesso.modulo.gestao.leitura");
			}
		}else{
			throw new ActionServletException("atencao.usuario.logado.nao.associado.funcionario");
		}
		
		String login =  ((Integer)dadosAcessoGcsME[0]).toString();
		String regional = ((Integer) dadosAcessoGcsME[1]).toString();
		String perfil = ((Integer) dadosAcessoGcsME[2]).toString();

		gestaoLeituraEndereco.append("?login=" + login);
		gestaoLeituraEndereco.append("&regional=" + regional);
		gestaoLeituraEndereco.append("&perfil=" + perfil);

		httpServletRequest.getSession().setAttribute("gestaoLeituraEndereco", gestaoLeituraEndereco);

		return retorno;
	}

	}
