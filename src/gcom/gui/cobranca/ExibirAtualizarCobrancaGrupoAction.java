
package gcom.gui.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarCobrancaGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarCobrancaGrupo");

		CobrancaGrupoActionForm cobrancaGrupoActionForm = (CobrancaGrupoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String idCobrancaGrupo = httpServletRequest.getParameter("id");

		if(idCobrancaGrupo == null){

			if(httpServletRequest.getAttribute("idRegistroAtualizacao") == null){
				idCobrancaGrupo = (String) sessao.getAttribute("idRegistroAtualizacao");
			}else{
				idCobrancaGrupo = (String) httpServletRequest.getAttribute("idRegistroAtualizacao").toString();
			}
		}

		CobrancaGrupo cobrancaGrupo = null;
		String anoMesReferencia = null;
		if(cobrancaGrupoActionForm.getAnoMesReferencia() != null && !cobrancaGrupoActionForm.getAnoMesReferencia().trim().equals("")){
			String mesAno = cobrancaGrupoActionForm.getAnoMesReferencia();

			String mes = mesAno.substring(0, 2);
			String ano = mesAno.substring(3, 7);
			anoMesReferencia = ano + "" + mes;

		}

		if(idCobrancaGrupo != null && !idCobrancaGrupo.trim().equals("") && Integer.parseInt(idCobrancaGrupo) > 0){
			FiltroCobrancaGrupo filtro = new FiltroCobrancaGrupo();
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, idCobrancaGrupo));
			Collection colCobrancaGrupo = fachada.pesquisar(filtro, CobrancaGrupo.class.getName());

			if(colCobrancaGrupo != null && !colCobrancaGrupo.isEmpty()){
				cobrancaGrupo = (CobrancaGrupo) Util.retonarObjetoDeColecao(colCobrancaGrupo);
			}
		}

		cobrancaGrupoActionForm.setId(idCobrancaGrupo);
		cobrancaGrupoActionForm.setDescricao(cobrancaGrupo.getDescricao());
		cobrancaGrupoActionForm.setDescAbreviada(cobrancaGrupo.getDescricaoAbreviada());
		cobrancaGrupoActionForm.setIndicadorUso(cobrancaGrupo.getIndicadorUso().toString());
		if(cobrancaGrupo.getAnoMesReferencia() != null && !cobrancaGrupo.getAnoMesReferencia().toString().trim().equals("")){
			String mesAno = cobrancaGrupo.getAnoMesReferencia().toString();

			String mes = mesAno.substring(4, 6);
			String ano = mesAno.substring(0, 4);
			String anoMes = mes + "/" + ano;
			cobrancaGrupoActionForm.setAnoMesReferencia(anoMes);

		}

		sessao.setAttribute("cobrancaGrupo", cobrancaGrupo);

		if(sessao.getAttribute("colecaoCobrancaGrupo") != null){
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/filtrarCobrancaGrupoAction.do");
		}else{
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarCobrancaGrupoAction.do");
		}

		return retorno;
	}
}