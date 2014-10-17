
package gcom.gui.cobranca;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.FiltroAtendimentoMotivoEncerramento;
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

public class ExibirFiltrarRelatorioAcompanhamentoExecucaoServicoCobrancaAction
				extends GcomAction {

	public ActionForward execute(final ActionMapping actionMapping, final ActionForm actionForm,
					final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltrarRelatorioAcompanhamentoExecucaoServicoCobrancaAction");

		GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm form = (GerarRelatorioAcompanhamentoExecucaoServicoCobrancaActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		// ColecaoAcao
		Collection colecaoAcao = null;
		Collection colecaoMotivoEncerramento = null;
		Collection colecaoServicoTipo = null;
		Collection colecaoCobrancaGrupo = null;
		Collection colecaoSetorComercial = null;
		Collection colecaoBairro = null;

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

		if(form.getGrupo() != null && !form.getGrupo().equals("")){
			colecaoSetorComercial = fachada.pesquisarSetorComercialPorGrupoEmRota(Integer.valueOf(form.getGrupo()));
			colecaoBairro = fachada.pesquisarBairroPorGrupoEmQuadraRota(Integer.valueOf(form.getGrupo()));
			httpServletRequest.setAttribute("colecaoSetorComercial", colecaoSetorComercial);
			httpServletRequest.setAttribute("colecaoBairro", colecaoBairro);
		}

		return retorno;
	}
}
