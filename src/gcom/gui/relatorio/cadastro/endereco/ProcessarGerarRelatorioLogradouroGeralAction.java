
package gcom.gui.relatorio.cadastro.endereco;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.endereco.RelatorioLogradouroGeral;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

/**
 * [UC0XXX] Gerar Relatório Logradouro Geral
 * 
 * @author Anderson Italo
 * @date 26/01/2011
 */
public class ProcessarGerarRelatorioLogradouroGeralAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// 4. Caso o usuário confirme o sistema gera o relatório de Logradouro Geral com os campos
		// abaixo,
		// classificado e quebrado por id da localidade.
		ActionForward retorno = null;
		GerarRelatorioLogradouroGeralActionForm formulario = (GerarRelatorioLogradouroGeralActionForm) actionForm;

		RelatorioLogradouroGeral relatorio = new RelatorioLogradouroGeral((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		relatorio.addParametro("idLocalidadeInicial", formulario.getLocalidadeInicial());
		relatorio.addParametro("idLocalidadeFinal", formulario.getLocalidadeFinal());
		relatorio.addParametro("nomeLocalidadeInicial", formulario.getNomeLocalidadeInicial());
		relatorio.addParametro("nomeLocalidadeFinal", formulario.getNomeLocalidadeFinal());
		relatorio.addParametro("totalRegistrosRelatorio", formulario.getTotalRegistrosRelatorio());

		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}
}
