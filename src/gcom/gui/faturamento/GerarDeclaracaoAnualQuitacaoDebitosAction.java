
package gcom.gui.faturamento;

import gcom.batch.DadoComplementarEnumerator;
import gcom.batch.Processo;
import gcom.batch.ProcessoIniciado;
import gcom.batch.ProcessoIniciadoDadoComplementarHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3013] Gerar Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 28/04/2013
 */
public class GerarDeclaracaoAnualQuitacaoDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		GerarDeclaracaoAnualQuitacaoDebitosActionForm gerarDeclaracaoAnualQuitacaoDebitosActionForm = (GerarDeclaracaoAnualQuitacaoDebitosActionForm) actionForm;

		String idFaturamentoGrupoStr = gerarDeclaracaoAnualQuitacaoDebitosActionForm.getIdFaturamentoGrupo();

		Integer idFaturamentoGrupo = Integer.valueOf(idFaturamentoGrupoStr);

		ProcessoIniciado processoIniciado = new ProcessoIniciado();

		Processo processo = new Processo();
		processo.setId(Processo.GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS);
		processoIniciado.setDataHoraAgendamento(new Date());

		// Verificar restrição de execução simultânea de processos
		if(fachada.isProcessoComRestricaoExecucaoSimultanea(processo.getId())){
			throw new ActionServletException("atencao.processo_restricao_execucao");
		}

		processoIniciado.setProcesso(processo);
		processoIniciado.setUsuario(this.getUsuarioLogado(httpServletRequest));

		ProcessoIniciadoDadoComplementarHelper helper = new ProcessoIniciadoDadoComplementarHelper();
		helper.adcionarDadoComplementar(DadoComplementarEnumerator.GRUPO_DESCRICAO, idFaturamentoGrupo);

		processoIniciado.setDescricaoDadosComplementares(helper.getStringFormatoPesistencia());

		FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
		faturamentoGrupo.setId(idFaturamentoGrupo);

		List<Object> colecaoParametros = new ArrayList<Object>();
		colecaoParametros.add(faturamentoGrupo);

		Integer codigoProcessoIniciadoGerado = (Integer) fachada.inserirProcessoIniciadoOnline(processoIniciado, colecaoParametros);

		if(codigoProcessoIniciadoGerado > 0){
			// montando página de sucesso
			montarPaginaSucesso(httpServletRequest, "A Geração da Declaração Anual de Quitação de Débitos do Grupo "
							+ idFaturamentoGrupoStr + " foi enviada para processamento.", "Voltar",
							"exibirGerarDeclaracaoAnualQuitacaoDebitosAction.do");
		}else{
			ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
							String.valueOf(Processo.GERAR_DECLARACAO_ANUAL_QUITACAO_DEBITOS) + " - GERAR DECLARACAO ANUAL QUITACAO DEBITOS");

			throw actionServletException;
		}

		sessao.removeAttribute("colecaoFaturamentoGrupo");

		return retorno;
	}

}
