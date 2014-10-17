
package gcom.gui.operacional.abastecimento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSetorAbastecimento;
import gcom.operacional.SetorAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] Manter Setor Abastecimento
 * 
 * @author Péricles Tavares
 * @date 08/02/2011
 */
public class FiltrarSetorAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterSetorAbastecimento");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSetorAbastecimentoActionForm form = (FiltrarSetorAbastecimentoActionForm) actionForm;

		// Recupera todos os campos da página para ser colocada no filtro
		// posteriormente
		String codigo = form.getCodigo();
		String descricao = form.getDescricao();
		String descricaoAbreviada = form.getDescricaoAbreviada();
		String indicadorUso = form.getIndicadorUso();
		String sistemaAbastecimento = form.getSistemaAbastecimento();
		String distritoOperacional = form.getDistritoOperacional();
		String zonaAbastecimento = form.getZonaAbastecimento();
		String tipoPesquisa = form.getTipoPesquisa();

		// Indicador Atualizar
		String indicadorAtualizar = form.getIndicadorAtualizar();
		if(indicadorAtualizar != null && !indicadorAtualizar.equals("") && indicadorAtualizar.equals("1")){

			sessao.setAttribute("indicadorAtualizar", "1");
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		boolean peloMenosUmParametroInformado = false;
		FiltroSetorAbastecimento filtroSetorAbastecimento = new FiltroSetorAbastecimento();
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL_LOCALIDADE);

		// Código Sistema Abastecimento
		if(codigo != null && !codigo.trim().equals("")){
			peloMenosUmParametroInformado = true;
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.CODIGO_SETOR_ABASTECIMENTO, codigo));
		}

		// Descricao
		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroSetorAbastecimento.adicionarParametro(new ComparacaoTextoCompleto(FiltroSetorAbastecimento.DESCRICAO, descricao));
			}else{

				filtroSetorAbastecimento.adicionarParametro(new ComparacaoTexto(FiltroSetorAbastecimento.DESCRICAO, descricao));
			}
		}

		// Descrição Abreviada
		if(descricaoAbreviada != null && !descricaoAbreviada.trim().equals("")){
			peloMenosUmParametroInformado = true;
			filtroSetorAbastecimento.adicionarParametro(new ComparacaoTexto(FiltroSetorAbastecimento.DESCRICAO_ABREVIADA,
							descricaoAbreviada));

		}

		// Sistema Abastecimento
		if(sistemaAbastecimento != null && !sistemaAbastecimento.trim().equals("")
						&& !sistemaAbastecimento.trim().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			peloMenosUmParametroInformado = true;
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.SISTEMA_ABASTECIMENTO_ID,
							sistemaAbastecimento));

		}

		// Distrito Operacional
		if(distritoOperacional != null && !distritoOperacional.trim().equals("")
						&& !distritoOperacional.trim().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			peloMenosUmParametroInformado = true;
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL_ID,
							distritoOperacional));

		}

		// Zona Abastecimento
		if(zonaAbastecimento != null && !zonaAbastecimento.trim().equals("")
						&& !zonaAbastecimento.trim().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			peloMenosUmParametroInformado = true;
			filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO_ID,
							zonaAbastecimento));

		}

		if(indicadorUso != null && !indicadorUso.equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(FiltroSetorAbastecimento.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		filtroSetorAbastecimento.setCampoOrderBy(FiltroSetorAbastecimento.CODIGO_SETOR_ABASTECIMENTO, FiltroSetorAbastecimento.DESCRICAO);

		Collection<SetorAbastecimento> colecaoSetorAbastecimento = fachada.pesquisar(filtroSetorAbastecimento, SetorAbastecimento.class
						.getName());

		if(colecaoSetorAbastecimento == null || colecaoSetorAbastecimento.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Setor de Abastecimento");
		}else{
			httpServletRequest.setAttribute("colecaoSetorAbastecimento", colecaoSetorAbastecimento);
			SetorAbastecimento SetorAbastecimento = new SetorAbastecimento();
			SetorAbastecimento = (SetorAbastecimento) Util.retonarObjetoDeColecao(colecaoSetorAbastecimento);
			String idRegistroAtualizacao = SetorAbastecimento.getId().toString();
			sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);
		}

		sessao.setAttribute("filtroSetorAbastecimento", filtroSetorAbastecimento);

		httpServletRequest.setAttribute("filtroSetorAbastecimento", filtroSetorAbastecimento);

		return retorno;

	}
}
