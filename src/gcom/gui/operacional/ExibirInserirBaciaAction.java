
package gcom.gui.operacional;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubsistemaEsgoto;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * UC0042 - Inserir Bacia
 * 
 * @author Hebert Falcão
 * @created 27 de Janeiro de 2011
 */
public class ExibirInserirBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirBacia");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirBaciaActionForm inserirBaciaActionForm = (InserirBaciaActionForm) actionForm;

		String limparCampos = (String) httpServletRequest.getParameter("limparCampos");
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");
		String removerEndereco = (String) httpServletRequest.getParameter("removerEndereco");

		if(objetoConsulta != null && !objetoConsulta.trim().equals("")){
			if(objetoConsulta.equals("1")){
				// Recebe o valor do campo Sistema de Esgoto do formulário.
				String idSistemaEsgoto = inserirBaciaActionForm.getIdSistemaEsgoto();

				FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
				filtroSubsistemaEsgoto.setCampoOrderBy(FiltroSubsistemaEsgoto.DESCRICAO);
				filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, idSistemaEsgoto));
				filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection colecaoSubsistemaEsgoto = this.getFachada().pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());
				sessao.setAttribute("colecaoSubsistemaEsgoto", colecaoSubsistemaEsgoto);
			}
		}else{
			if(sessao.getAttribute("colecaoSistemaEsgoto") == null){
				FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
				filtroSistemaEsgoto.setCampoOrderBy(FiltroSistemaEsgoto.DESCRICAO);
				filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection colecaoSistemaEsgoto = this.getFachada().pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

				if(colecaoSistemaEsgoto == null || colecaoSistemaEsgoto.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SISTEMA_ESGOTO");
				}else{
					sessao.setAttribute("colecaoSistemaEsgoto", colecaoSistemaEsgoto);
				}
			}
		}

		// Botão Desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

		if((limparCampos == null || limparCampos.trim().equalsIgnoreCase("")) || (desfazer != null && desfazer.equalsIgnoreCase("S"))){
			// Limpando o formulario
			inserirBaciaActionForm.setCodigo("");
			inserirBaciaActionForm.setDescricao("");
			inserirBaciaActionForm.setDescricaoAbreviada("");

			inserirBaciaActionForm.setNumeroUnidade("");
			inserirBaciaActionForm.setNumeroAducao("");
			inserirBaciaActionForm.setNumeroDemandaEnergia("");

			inserirBaciaActionForm.setNumeroCota("");
			inserirBaciaActionForm.setNumeroLatitude("");
			inserirBaciaActionForm.setNumeroLongitude("");

			// Campos do tipo lista no formulário
			inserirBaciaActionForm.setIdSistemaEsgoto("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirBaciaActionForm.setIdSubsistemaEsgoto("" + ConstantesSistema.NUMERO_NAO_INFORMADO);

			sessao.removeAttribute("colecaoSubsistemaEsgoto");
			sessao.removeAttribute("colecaoEnderecos");
		}

		// Remove o endereco informado.
		if(removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase("")){
			if(sessao.getAttribute("colecaoEnderecos") != null){
				Collection enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				if(!enderecos.isEmpty()){
					enderecos.remove(enderecos.iterator().next());
				}
			}
		}

		return retorno;
	}

}
