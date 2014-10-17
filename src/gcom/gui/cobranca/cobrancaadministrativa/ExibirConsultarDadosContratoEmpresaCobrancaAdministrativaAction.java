
package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.cadastro.empresa.Empresa;
import gcom.cobranca.contrato.CobrancaContrato;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * [UC3060] Consultar Imóvel Cobrança Administrativa
 * [SB0002] - Consultar Dados do Contrato da Empresa
 * 
 * @author Anderson Italo
 * @date 17/09/2012
 */
public class ExibirConsultarDadosContratoEmpresaCobrancaAdministrativaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosContratoEmpresaCobrancaAdministrativa");
		Fachada fachada = Fachada.getInstancia();
		DynaActionForm form = (DynaActionForm) actionForm;

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("idEmpresa"))){

			Integer idEmpresa = Util.obterInteger(httpServletRequest.getParameter("idEmpresa"));
			Empresa empresa = (Empresa) fachada.pesquisar(idEmpresa, Empresa.class);
			form.set("nomeEmpresa", empresa.getDescricao());

			Collection<CobrancaContrato> colecaoCobrancaContrato = fachada.pesquisarCobrancaContratoPorEmpresa(empresa.getId());

			if(!Util.isVazioOrNulo(colecaoCobrancaContrato)){

				this.getSessao(httpServletRequest).setAttribute("colecaoCobrancaContrato", colecaoCobrancaContrato);
			}
		}

		return retorno;
	}
}
