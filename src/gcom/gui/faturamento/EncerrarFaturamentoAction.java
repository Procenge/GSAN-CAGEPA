
package gcom.gui.faturamento;

import gcom.batch.Processo;
import gcom.batch.ProcessoIniciado;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3055] Encerrar Faturamento
 * 
 * @author Hebert Falcão
 * @date 01/04/2012
 */
public class EncerrarFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Form
		EncerrarFaturamentoActionForm form = (EncerrarFaturamentoActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		String referenciaStr = form.getReferencia();
		Integer referencia = Util.formatarMesAnoComBarraParaAnoMes(referenciaStr);

		fachada.encerrarFaturamento(referencia, usuarioLogado);

		// Iniciar Batch Resumo Ligações Economias
		fachada.gerarResumoLigacoesEconomias(referencia, usuarioLogado);

		// Iniciar Batch Resumo de Anormalidades (Leitura e Consumo)
		fachada.gerarResumoAnormalidades(referencia, usuarioLogado);

		fachada.encerrarContratoDemandaConsumo(referencia.toString());

		// this.montarPaginaSucesso(httpServletRequest,
		// "Encerramento do faturamento realizado com sucesso.", "", "");

		ProcessoIniciado processoIniciado = new ProcessoIniciado();
		Processo processo = new Processo();
		processo.setId(Processo.EFETIVAR_ATUALIZACAO_INSCRICAO_IMOVEL);
		processoIniciado.setDataHoraAgendamento(new Date());
		processoIniciado.setProcesso(processo);
		processoIniciado.setUsuario(this.getUsuarioLogado(httpServletRequest));

		Integer codigoProcessoIniciadoGerado = (Integer) Fachada.getInstancia().inserirProcessoIniciadoOnline(processoIniciado, null);

		this.montarPaginaSucesso(httpServletRequest, "Encerramento do faturamento realizado com sucesso.", "", "");

		return retorno;
	}

}
