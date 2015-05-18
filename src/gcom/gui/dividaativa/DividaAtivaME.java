
package gcom.gui.dividaativa;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Yara Souza
 * @date 11/09/2014
 */
public class DividaAtivaME
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("dividaAtivaME");
		
		StringBuilder moduloDividaAtivaEndereco = new StringBuilder("http://");
		try{
			moduloDividaAtivaEndereco.append(ParametroCobranca.P_MODULO_DIVIDA_ATIVA_ENDERECO.executar());
		}catch(ControladorException e){
			e.printStackTrace();
			throw new ActionServletException("erro.parametro.sistema.gestao.leitura.endereco");
		}

		String paginaDividaAtivaME = httpServletRequest.getParameter("paginaDividaAtivaME");
		moduloDividaAtivaEndereco.append(paginaDividaAtivaME);

		Usuario usuariologado = getUsuarioLogado(httpServletRequest);

		if(usuariologado == null){
			throw new ActionServletException("atencao.usuario.logado.nao.associado.funcionario");
		}

		moduloDividaAtivaEndereco.append("&usuario=" + usuariologado.getId());

		httpServletRequest.getSession().setAttribute("moduloDividaAtivaEndereco", moduloDividaAtivaEndereco);

		return retorno;
	}

	}
