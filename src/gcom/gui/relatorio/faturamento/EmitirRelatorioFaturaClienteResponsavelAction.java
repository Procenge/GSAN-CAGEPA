
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Fatura;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioFaturaClienteResponsavel;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class EmitirRelatorioFaturaClienteResponsavelAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection<Fatura> idsFaturas = (Collection<Fatura>) sessao.getAttribute("idsFaturas");
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		String nomeEmpresa = sistemaParametro.getNomeAbreviadoEmpresa();

		RelatorioFaturaClienteResponsavel relatorioFaturaClienteResponsavel = new RelatorioFaturaClienteResponsavel(usuario);

		relatorioFaturaClienteResponsavel.setUsuario(usuario);
		relatorioFaturaClienteResponsavel.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		relatorioFaturaClienteResponsavel.addParametro("nomeEmpresa", nomeEmpresa);
		relatorioFaturaClienteResponsavel.addParametro("idsFaturas", idsFaturas);

		try{
			retorno = processarExibicaoRelatorio(relatorioFaturaClienteResponsavel, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(RelatorioVazioException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}

}
