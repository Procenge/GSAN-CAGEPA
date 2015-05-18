package gcom.gui.relatorio.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.micromedicao.RelatorioQuadroHidrometrosSituacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
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

		GerarRelatorioQuadroHidrometrosSituacaoActionForm form = (GerarRelatorioQuadroHidrometrosSituacaoActionForm) actionForm;

		String mesAno = form.getMesAno();

		if(mesAno == null || mesAno.equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Mês/Ano");
		}

		if(Util.validarAnoMes(mesAno)){
			throw new ActionServletException("atencao.campo.invalido", null, "Mês/Ano");
		}

		Date dateInicioMes = Util.converterMesAnoParaDataInicial(mesAno);
		Date dateFimMes = Util.converterMesAnoParaDataFinalMes(mesAno);

		Integer idGerenciaRegional = null;
		if(!Util.isVazioOuBranco(form.getIdGerenciaRegional())
						&& !form.getIdGerenciaRegional().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			idGerenciaRegional = Util.converterStringParaInteger(form.getIdGerenciaRegional());
		}

		Integer idUnidadeNegocio = null;
		if(!Util.isVazioOuBranco(form.getIdUnidadeNegocio())
						&& !form.getIdUnidadeNegocio().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			idUnidadeNegocio = Util.converterStringParaInteger(form.getIdUnidadeNegocio());
		}

		Integer idUnidadeFederacao = null;
		if(!Util.isVazioOuBranco(form.getIdUnidadeFederacao())
						&& !form.getIdUnidadeFederacao().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			idUnidadeFederacao = Util.converterStringParaInteger(form.getIdUnidadeFederacao());
		}

		Integer idLocalidade = null;
		if(!Util.isVazioOuBranco(form.getIdLocalidade()) && !form.getIdLocalidade().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			idLocalidade = Util.converterStringParaInteger(form.getIdLocalidade());
		}

		Integer idHidrometroCapacidade = null;
		if(!Util.isVazioOuBranco(form.getIdHidrometroCapacidade())
						&& !form.getIdHidrometroCapacidade().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			idHidrometroCapacidade = Util.converterStringParaInteger(form.getIdHidrometroCapacidade());
		}

		Integer idHidrometroDiametro = null;
		if(!Util.isVazioOuBranco(form.getIdHidrometroDiametro())
						&& !form.getIdHidrometroDiametro().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			idHidrometroDiametro = Util.converterStringParaInteger(form.getIdHidrometroDiametro());
		}

		Integer idHidrometroMarca = null;
		if(!Util.isVazioOuBranco(form.getIdHidrometroMarca())
						&& !form.getIdHidrometroMarca().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			idHidrometroMarca = Util.converterStringParaInteger(form.getIdHidrometroMarca());
		}


		Integer qtdeRegistros = fachada
						.pesquisarQuadroHidrometrosSituacaoCount(dateInicioMes, dateFimMes, idGerenciaRegional, idUnidadeNegocio,
										idUnidadeFederacao, idLocalidade, idHidrometroCapacidade, idHidrometroMarca, idHidrometroDiametro);

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
