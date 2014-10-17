
package gcom.gui.operacional;

import gcom.atendimentopublico.ordemservico.FiltroMaterialRedeEsgoto;
import gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.operacional.FiltroBacia;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubBacia;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubBacia;
import gcom.operacional.SubsistemaEsgoto;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Atualizar SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class ExibirAtualizarSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarSubBacia");

		AtualizarSubBaciaActionForm atualizarSubBaciaActionForm = (AtualizarSubBaciaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();

		filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroSistemaEsgoto.setCampoOrderBy(FiltroBacia.DESCRICAO);

		Collection colecaoSistema = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		httpServletRequest.setAttribute("colecaoSistema", colecaoSistema);

		FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();

		filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroSubsistemaEsgoto.setCampoOrderBy(FiltroBacia.DESCRICAO);

		Collection colecaoSubSistema = fachada.pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

		httpServletRequest.setAttribute("colecaoSubSistema", colecaoSubSistema);

		FiltroBacia filtroBacia = new FiltroBacia();

		filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		filtroBacia.setCampoOrderBy(FiltroBacia.DESCRICAO);

		Collection colecaoBacia = fachada.pesquisar(filtroBacia, Bacia.class.getName());

		httpServletRequest.setAttribute("colecaoBacia", colecaoBacia);

		SubBacia subBacia = null;

		String idSubBacia = null;

		if(httpServletRequest.getParameter("idSubBacia") != null){
			// tela do manter
			idSubBacia = (String) httpServletRequest.getParameter("idSubBacia");
			sessao.setAttribute("idSubBacia", idSubBacia);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterSubBaciaAction.do");
		}else if(sessao.getAttribute("idSubBacia") != null){
			// tela do filtrar
			idSubBacia = (String) sessao.getAttribute("idSubBacia");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSubBaciaAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir
			idSubBacia = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarSubBaciaAction.do?menu=sim");
		}

		if(idSubBacia == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				subBacia = (SubBacia) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idSubBacia = (String) httpServletRequest.getParameter("idSubBacia").toString();
			}
		}

		// Coleção material
		FiltroMaterialRedeEsgoto filtroMaterialRedeEsgoto = new FiltroMaterialRedeEsgoto();
		filtroMaterialRedeEsgoto.setCampoOrderBy(FiltroMaterialRedeEsgoto.DESCRICAO);
		filtroMaterialRedeEsgoto.adicionarParametro(new ParametroSimples(FiltroMaterialRedeEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoMaterialRedeEsgoto = getFachada().pesquisar(filtroMaterialRedeEsgoto, MaterialRedeEsgoto.class.getName());

		if(colecaoMaterialRedeEsgoto == null || colecaoMaterialRedeEsgoto.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MATERIAL_REDE_ESGOTO");
		}else{
			sessao.setAttribute("colecaoMaterialRedeEsgoto", colecaoMaterialRedeEsgoto);
		}

		// Inicio da parte que verifica se vem da página de subbacia_manter.jsp

		if(subBacia == null){

			if(idSubBacia != null && !idSubBacia.equals("")){

				FiltroSubBacia filtroSubBacia = new FiltroSubBacia();

				filtroSubBacia.adicionarCaminhoParaCarregamentoEntidade("bacia");

				filtroSubBacia.adicionarCaminhoParaCarregamentoEntidade("bacia.subsistemaEsgoto");

				filtroSubBacia.adicionarCaminhoParaCarregamentoEntidade("bacia.subsistemaEsgoto.sistemaEsgoto");

				filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.ID, idSubBacia));

				Collection colecaoSubBacia = fachada.pesquisar(filtroSubBacia, SubBacia.class.getName());

				if(colecaoSubBacia != null && !colecaoSubBacia.isEmpty()){

					subBacia = (SubBacia) Util.retonarObjetoDeColecao(colecaoSubBacia);

				}
			}
		}

		// O sub-bacia foi encontrada

		atualizarSubBaciaActionForm.setCodigo("" + subBacia.getCodigo());

		atualizarSubBaciaActionForm.setDescricao(subBacia.getDescricao());

		atualizarSubBaciaActionForm.setDescricaoAbreviada(subBacia.getDescricaoAbreviada());

		atualizarSubBaciaActionForm.setBacia(subBacia.getBacia().getId().toString());

		atualizarSubBaciaActionForm.setSubSistema(subBacia.getBacia().getSubsistemaEsgoto().getId().toString());

		atualizarSubBaciaActionForm.setSistema(subBacia.getBacia().getSubsistemaEsgoto().getSistemaEsgoto().getId().toString());

		// Extensão
		String extensaoStr = "";
		BigDecimal extensao = subBacia.getExtensao();

		if(extensao != null){
			extensaoStr = Util.formataBigDecimal(extensao, 4, false);
		}

		atualizarSubBaciaActionForm.setExtensao(extensaoStr);

		// Diâmetro
		String diametroStr = "";
		BigDecimal diametro = subBacia.getDiametro();

		if(diametro != null){
			diametroStr = Util.formataBigDecimal(diametro, 4, false);
		}

		atualizarSubBaciaActionForm.setDiametro(diametroStr);

		// Material
		String idMaterialStr = Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO);
		MaterialRedeEsgoto material = subBacia.getMaterialRedeEsgoto();

		if(material != null){
			Integer idMaterial = material.getId();
			idMaterialStr = Integer.toString(idMaterial);
		}

		atualizarSubBaciaActionForm.setIdMaterial(idMaterialStr);

		atualizarSubBaciaActionForm.setIndicadorUso("" + subBacia.getIndicadorUso());

		atualizarSubBaciaActionForm.setUltimaAlteracao(Util.formatarDataComHora(subBacia.getUltimaAlteracao()));

		sessao.setAttribute("subBacia", subBacia);

		httpServletRequest.setAttribute("idSubBacia", idSubBacia);

		// Fim da parte que verifica se vem da página de subbacia_manter.jsp

		return retorno;
	}

}
