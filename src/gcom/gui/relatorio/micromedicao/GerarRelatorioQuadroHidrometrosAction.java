package gcom.gui.relatorio.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.micromedicao.RelatorioQuadroHidrometros;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioQuadroHidrometrosAction
				extends ExibidorProcessamentoTarefaRelatorio {


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		Fachada fachada = Fachada.getInstancia();

		GerarRelatorioQuadroHidrometrosActionForm gerarRelatorioQuadroHidrometrosActionForm = (GerarRelatorioQuadroHidrometrosActionForm) actionForm;

		String dataReferencia = gerarRelatorioQuadroHidrometrosActionForm.getDataReferencia();

		if(Util.validarDiaMesAno(dataReferencia)){
			throw new ActionServletException("atencao.campo.invalido", null, "Data de Referência");
		}

		if(dataReferencia == null){
			throw new ActionServletException("atencao.informe_campo", null, "Data de Referência");
		}

		Date dateReferencia = Util.converterStringParaData(dataReferencia);
			
		Integer qtdeRegistros = fachada.pesquisarQuadroHidrometrosCount(dateReferencia);

		if(qtdeRegistros.intValue() == 0){
			throw new ActionServletException("atencao.nenhum_hidrometro_encontrado", null, dataReferencia);
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioQuadroHidrometros relatorioQuadroHidrometros = new RelatorioQuadroHidrometros(
						(Usuario) (httpServletRequest
						.getSession(false)).getAttribute("usuarioLogado"));

		relatorioQuadroHidrometros.addParametro("dataReferencia", dataReferencia);
		relatorioQuadroHidrometros.addParametro("qtdeRegistros", qtdeRegistros);

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioQuadroHidrometros.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioQuadroHidrometros, tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}

}
