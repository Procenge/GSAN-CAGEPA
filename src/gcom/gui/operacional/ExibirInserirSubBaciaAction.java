
package gcom.gui.operacional;

import gcom.atendimentopublico.ordemservico.FiltroMaterialRedeEsgoto;
import gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.operacional.FiltroBacia;
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
 * Inserir SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @created 31 de Janeiro de 2011
 */

public class ExibirInserirSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirSubBacia");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirSubBaciaActionForm inserirSubBaciaActionForm = (InserirSubBaciaActionForm) actionForm;

		sessao.setAttribute("colecaoBacia", null);
		sessao.setAttribute("colecaoSistema", null);
		sessao.setAttribute("colecaoSubSistema", null);
		sessao.setAttribute("colecaoMaterialRedeEsgoto", null);

		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
		filtroSistemaEsgoto.setCampoOrderBy(FiltroSistemaEsgoto.DESCRICAO);
		filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoSistema = getFachada().pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		if(colecaoSistema == null || colecaoSistema.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SISTEMA_ESGOTO");
		}else{
			sessao.setAttribute("colecaoSistema", colecaoSistema);
			if((httpServletRequest.getParameter("consultaSistema") != null && inserirSubBaciaActionForm.getIdSistema() != null)
							|| (httpServletRequest.getParameter("consultaSubSistema") != null && inserirSubBaciaActionForm
											.getIdSubSistema() != null)){

				Collection colecaoSubSistema = null;

				if(!inserirSubBaciaActionForm.getIdSistema().equals("-1")){
					FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
					filtroSubsistemaEsgoto.setCampoOrderBy(FiltroSubsistemaEsgoto.DESCRICAO);
					filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, Integer
									.valueOf(inserirSubBaciaActionForm.getIdSistema())));

					colecaoSubSistema = getFachada().pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());
				}

				if(colecaoSubSistema == null || colecaoSubSistema.isEmpty()){
					if(!inserirSubBaciaActionForm.getIdSistema().equals("-1")){
						throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SUBSISTEMA_ESGOTO");
					}
				}else{
					sessao.setAttribute("colecaoSubSistema", colecaoSubSistema);
					if(httpServletRequest.getParameter("consultaSubSistema") != null && inserirSubBaciaActionForm.getIdSubSistema() != null){

						Collection colecaoBacia = null;

						if(!inserirSubBaciaActionForm.getIdSubSistema().equals("-1")){
							FiltroBacia filtroBacia = new FiltroBacia();
							filtroBacia.setCampoOrderBy(FiltroBacia.DESCRICAO);
							filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO,
											ConstantesSistema.INDICADOR_USO_ATIVO));
							filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_ID, Integer
											.valueOf(inserirSubBaciaActionForm.getIdSubSistema())));

							colecaoBacia = this.getFachada().pesquisar(filtroBacia, Bacia.class.getName());
						}

						if(colecaoBacia == null || colecaoBacia.isEmpty()){
							if(!inserirSubBaciaActionForm.getIdSubSistema().equals("-1")){
								throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "BACIA");
							}

						}else{
							sessao.setAttribute("colecaoBacia", colecaoBacia);
						}
					}
				}
			}
		}

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

		// Botão Desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

		if((desfazer != null && desfazer.equalsIgnoreCase("S"))){
			// Limpando o formulario
			inserirSubBaciaActionForm.setCodigo("");
			inserirSubBaciaActionForm.setDescricao("");
			inserirSubBaciaActionForm.setDescricaoAbreviada("");
			inserirSubBaciaActionForm.setExtensao("");
			inserirSubBaciaActionForm.setDiametro("");

			// Campos do tipo lista no formulário
			inserirSubBaciaActionForm.setIdBacia("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirSubBaciaActionForm.setIdMaterial("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		}

		return retorno;
	}
}
