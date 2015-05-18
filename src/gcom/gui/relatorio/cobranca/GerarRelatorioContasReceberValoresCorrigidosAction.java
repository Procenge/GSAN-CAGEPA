
package gcom.gui.relatorio.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioContasReceberValoresCorrigidos;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;

import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioContasReceberValoresCorrigidosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltroRelatorioContasReceberValoresCorrigidos");

		RelatorioContasReceberValoresCorrigidosForm form = (RelatorioContasReceberValoresCorrigidosForm) actionForm;

		String matriculaImovel = form.getMatriculaImovel();
		Integer idImovel = null;
		if(matriculaImovel != null && !matriculaImovel.trim().equals("")){
			idImovel = Integer.valueOf(matriculaImovel);
		}

		String referenciaConta = form.getReferencia();

		if(referenciaConta == null || referenciaConta.trim().equals("")){
			throw new ActionServletException("atencao.referencia.obrigatorio");

		}

		Integer referencia = null;
		referenciaConta = referenciaConta.substring(3) + referenciaConta.substring(0, 2);
		referencia = Integer.valueOf(referenciaConta);

		Long quantidade = Fachada.getInstancia().quantidadeRegistrosRelatorioContasReceberValoresCorrigidos(idImovel, referencia);
		if(quantidade == null || quantidade.equals(0L)){
			throw new ActionServletException("atencao.relatorio.vazio");

		}

		Usuario usuario = getUsuarioLogado(httpServletRequest);

		RelatorioContasReceberValoresCorrigidos relatorio = new RelatorioContasReceberValoresCorrigidos(usuario,
						ConstantesRelatorios.RELATORIO_CONTAS_RECEBER_VALORES_CORRIGIDOS);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		Fachada fachada = Fachada.getInstancia();
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(tipoRelatorio != null && tipoRelatorio.equals(String.valueOf(TarefaRelatorio.TIPO_XLS))){
			tipoRelatorio = String.valueOf(TarefaRelatorio.TIPO_XLS);

			final String path = getServlet().getServletContext().getRealPath("/");

			relatorio.addParametro("template", new File(path.concat("\\xls\\relatorioContasReceberValoresCorrigidosTemplate.xls")));
			relatorio.addParametro("nomeEmpresa", sistemaParametro.getNomeEmpresa());

		}else{
			tipoRelatorio = String.valueOf(TarefaRelatorio.TIPO_PDF);
		}

		relatorio.addParametro("matriculaImovel", matriculaImovel);
		relatorio.addParametro("referencia", referencia);
		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}
}
