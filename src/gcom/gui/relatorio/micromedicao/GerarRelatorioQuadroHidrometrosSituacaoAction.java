package gcom.gui.relatorio.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.micromedicao.RelatorioQuadroHidrometrosSituacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioQuadroHidrometrosSituacaoAction
				extends ExibidorProcessamentoTarefaRelatorio {


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		Fachada fachada = Fachada.getInstancia();

		GerarRelatorioQuadroHidrometrosSituacaoActionForm gerarRelatorioQuadroHidrometrosActionForm = (GerarRelatorioQuadroHidrometrosSituacaoActionForm) actionForm;

		String mesAno = gerarRelatorioQuadroHidrometrosActionForm.getMesAno();

		if(mesAno == null || mesAno.equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Mês/Ano");
		}

		if(Util.validarAnoMes(mesAno)){
			throw new ActionServletException("atencao.campo.invalido", null, "Mês/Ano");
		}

		Date dateInicioMes = Util.converterMesAnoParaDataInicial(mesAno);
		Date dateFimMes = Util.converterMesAnoParaDataFinalMes(mesAno);

		Integer qtdeRegistros = fachada.pesquisarQuadroHidrometrosSituacaoCount(dateInicioMes, dateFimMes);

		if(qtdeRegistros.intValue() == 0){
			throw new ActionServletException("atencao.nenhum_hidrometro_encontrado", null, mesAno);
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioQuadroHidrometrosSituacao relatorioQuadroHidrometros = new RelatorioQuadroHidrometrosSituacao(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		relatorioQuadroHidrometros.addParametro("dataReferencia", mesAno);
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
