package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.PesquisarOrdemServicoHelper;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class GerarRelatorioOrdemServicoManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		// Form
		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		// ******************************************************************************************
		// fachada
		Fachada fachada = Fachada.getInstancia();
		// ******************************************************************************************
		// coleção gerada e carregada no momento de filtrar
		String idOrdemServico = null;
		
		PesquisarOrdemServicoHelper pesquisarOrdemServicoHelper = (PesquisarOrdemServicoHelper) sessao
						.getAttribute("pesquisarOrdemServicoHelper");
		if(pesquisarOrdemServicoHelper != null){
			pesquisarOrdemServicoHelper.setNumeroPaginasPesquisa(ConstantesSistema.NUMERO_NAO_INFORMADO);
		}
		Collection<OrdemServico> colecaoOrdemServico = Fachada.getInstancia().pesquisarOrdemServico(pesquisarOrdemServicoHelper);
		// String[] idsOS = idOrdemServico.split("\\$");

		Collection<OrdemServico> ordemServicoSelecionadaList = new ArrayList<OrdemServico>();

		for(OrdemServico ordemServico : colecaoOrdemServico){
			OrdemServico ordemServicoPesq = fachada.pesquisarOrdemServico(ordemServico.getId());
			ordemServicoSelecionadaList.add(ordemServicoPesq);
		}
		// ******************************************************************************************
		
		if(!Util.isVazioOrNulo(ordemServicoSelecionadaList)){
			// tipo de relatorio
			String tipoRelatorio = (String) httpServletRequest.getParameter("tipoRelatorio");
			// ******************************************************************************************
			if(tipoRelatorio == null){
				tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
			}
			// ******************************************************************************************
			// ordenador
			String ordenador = (String) sessao.getAttribute("ordenador");
			// ******************************************************************************************
			// Relatório Analítico
			RelatorioOrdemServicoManter relatorioOrdemServicoManter = new RelatorioOrdemServicoManter(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorioOrdemServicoManter.addParametro("ordemServicoSelecionadaList", ordemServicoSelecionadaList);
			relatorioOrdemServicoManter.addParametro("tipoRelatorio", Integer.parseInt(tipoRelatorio));
			relatorioOrdemServicoManter.addParametro("ordenador", ordenador);
			retorno = processarExibicaoRelatorio(relatorioOrdemServicoManter, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}

		return retorno;


	}

}
