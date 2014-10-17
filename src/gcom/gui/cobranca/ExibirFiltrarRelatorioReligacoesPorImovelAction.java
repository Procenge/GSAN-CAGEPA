
package gcom.gui.cobranca;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.FiltroAtendimentoMotivoEncerramento;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Relatorio de Religações por Imóvel
 * 
 * @author rslo
 * @date 28/07/2010
 */
public class ExibirFiltrarRelatorioReligacoesPorImovelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltrarRelatorioReligacoesPorImovelAction");

		FiltroRelatorioReligacoesPorImovelActionForm form = (FiltroRelatorioReligacoesPorImovelActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// ColecaoAcao
		Collection colecaoAcao = null;
		Collection colecaoMotivoEncerramento = null;
		Collection colecaoServicoTipo = null;
		Collection colecaoCobrancaGrupo = null;
		Collection colecaoSetorComercial = null;
		Collection colecaoBairro = null;
		Collection colecaoCategoria = null;

		if(httpServletRequest.getParameter("bairro") == null){
			form.setBairro(null);
		}
		if(httpServletRequest.getParameter("setorComercial") == null){
			form.setSetorComercial(null);
		}

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
		colecaoAcao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

		httpServletRequest.setAttribute("colecaoCobrancaAcao", colecaoAcao);

		FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		colecaoMotivoEncerramento = fachada.pesquisar(filtroAtendimentoMotivoEncerramento, AtendimentoMotivoEncerramento.class.getName());

		httpServletRequest.setAttribute("colecaoMotivoEncerramento", colecaoMotivoEncerramento);

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		// filtroServicoTipo.adicionarParametro(new
		// ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));
		// filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("servicoTipoSubgrupo");
		// filtroServicoTipo.adicionarParametro(new
		// ParametroSimples(FiltroServicoTipo.SERVICOTIPOSUBGRUPO, ServicoTipoSubgrupo.RELIGACAO));
		// filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);

		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);
		colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		httpServletRequest.setAttribute("colecaoServicoTipo", colecaoServicoTipo);

		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);
		colecaoCobrancaGrupo = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());

		httpServletRequest.setAttribute("colecaoCobrancaGrupo", colecaoCobrancaGrupo);

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSetorComercial.setCampoOrderBy(FiltroSetorComercial.DESCRICAO);
		colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		FiltroBairro filtroBairro = new FiltroBairro();
		filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroBairro.setCampoOrderBy(FiltroBairro.NOME);
		colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

		FiltroCategoria filtroCategoria = new FiltroCategoria();
		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);
		colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

		httpServletRequest.setAttribute("colecaoSetorComercial", colecaoSetorComercial);
		httpServletRequest.setAttribute("colecaoBairro", colecaoBairro);
		httpServletRequest.setAttribute("colecaoCategoria", colecaoCategoria);

		return retorno;
	}
}