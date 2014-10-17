
package gcom.gui.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirCobrancaGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		CobrancaGrupoActionForm form = (CobrancaGrupoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		CobrancaGrupo cobrancaGrupo = new CobrancaGrupo();

		cobrancaGrupo.setDescricao(form.getDescricao());

		cobrancaGrupo.setDescricaoAbreviada(form.getDescAbreviada());

		cobrancaGrupo.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		cobrancaGrupo.setAnoMesReferencia(null);
		String mesAno = form.getAnoMesReferencia();
		if(mesAno != null && !mesAno.trim().equals("")){
			String mes = mesAno.substring(0, 2);
			String ano = mesAno.substring(3, 7);
			String anoMes = ano + "" + mes;
			cobrancaGrupo.setAnoMesReferencia(new Integer(anoMes));

		}
		cobrancaGrupo.setUltimaAlteracao(new Date());

		Integer idCobrancaGrupo = (Integer) fachada.inserir(cobrancaGrupo);
		String idRegistroAtualizacao = cobrancaGrupo.getId().toString();
		sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);

		// Monta a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Grupo de Cobrança " + cobrancaGrupo.getDescricao() + " inserido com sucesso.",
						"Inserir outro Grupo de Cobrança", "exibirInserirCobrancaGrupoAction.do?menu=sim",
						"exibirAtualizarCobrancaGrupoAction.do?manter=sim&id=" + idCobrancaGrupo, "Atualizar Grupo de Cobrança Inserido");

		return retorno;

	}
}
