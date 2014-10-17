
package gcom.gui.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarCobrancaGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterCobrancaGrupo");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		CobrancaGrupoActionForm cobrancaGrupoActionform = (CobrancaGrupoActionForm) actionForm;

		// Recupera todos os campos da página para ser colocada no filtro posteriormente

		String descricao = cobrancaGrupoActionform.getDescricao();
		String descAbreviada = cobrancaGrupoActionform.getDescAbreviada();
		String indicadorUso = cobrancaGrupoActionform.getIndicadorUso();
		String anoMesReferencia = null;
		if(cobrancaGrupoActionform.getAnoMesReferencia() != null && !cobrancaGrupoActionform.getAnoMesReferencia().trim().equals("")){
			String mesAno = cobrancaGrupoActionform.getAnoMesReferencia();
			String mes = mesAno.substring(0, 2);
			String ano = mesAno.substring(3, 7);
			anoMesReferencia = ano + "" + mes;
			// cobrancaGrupoActionform.setAnoMesReferencia(anoMes);
		}
		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");
		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){
			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{
			sessao.removeAttribute("indicadorAtualizar");
		}
		boolean peloMenosUmParametroInformado = false;
		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);

		if(descricao != null && !descricao.trim().equals("")){
			peloMenosUmParametroInformado = true;
			filtroCobrancaGrupo.adicionarParametro(new ComparacaoTexto(FiltroCobrancaGrupo.DESCRICAO, descricao));
		}

		if(descAbreviada != null && !descAbreviada.trim().equals("")){
			peloMenosUmParametroInformado = true;
			filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.DESCRICAO_ABREVIADA, descAbreviada));
		}
		if(indicadorUso != null && !indicadorUso.trim().equals("")){
			peloMenosUmParametroInformado = true;
			filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO, indicadorUso));
		}

		if(anoMesReferencia != null && !anoMesReferencia.trim().equals("")){
			peloMenosUmParametroInformado = true;
			filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ANO_MES_REFERENCIA, anoMesReferencia));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}
		Collection<CobrancaGrupo> colecaCobrancaGrupo = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());

		if(colecaCobrancaGrupo == null || colecaCobrancaGrupo.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "CobrancaGrupo");
		}else{
			httpServletRequest.setAttribute("colecaCobrancaGrupo", colecaCobrancaGrupo);
			CobrancaGrupo cobrancaGrupo = new CobrancaGrupo();
			cobrancaGrupo = (CobrancaGrupo) Util.retonarObjetoDeColecao(colecaCobrancaGrupo);
			String idRegistroAtualizacao = cobrancaGrupo.getId().toString();
			sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);
		}
		sessao.setAttribute("filtroCobrancaGrupo", filtroCobrancaGrupo);

		httpServletRequest.setAttribute("filtroCobrancaGrupo", filtroCobrancaGrupo);
		return retorno;
	}

}
