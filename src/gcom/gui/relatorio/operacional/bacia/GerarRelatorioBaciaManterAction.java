
package gcom.gui.relatorio.operacional.bacia;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.operacional.bacia.FiltrarBaciaActionForm;
import gcom.operacional.FiltroBacia;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubsistemaEsgoto;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.operacional.bacia.RelatorioBacia;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @date 03/02/2011
 */
public class GerarRelatorioBaciaManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarBaciaActionForm filtrarBaciaActionForm = (FiltrarBaciaActionForm) actionForm;

		FiltroBacia filtroBacia = (FiltroBacia) sessao.getAttribute("filtroBacia");

		// Cria uma instância da classe do relatório
		RelatorioBacia relatorioManterBacia = new RelatorioBacia((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));

		// Inicio da parte que vai mandar os parametros para o relatório
		relatorioManterBacia.addParametro("filtroBacia", filtroBacia);
		relatorioManterBacia.addParametro("codigoParamentro", filtrarBaciaActionForm.getCodigo());
		relatorioManterBacia.addParametro("descricaoParamentro", filtrarBaciaActionForm.getDescricao());
		relatorioManterBacia.addParametro("descricaoAbreviadaParamentro", filtrarBaciaActionForm.getDescricaoAbreviada());

		if(!Util.isVazioOuBranco(filtrarBaciaActionForm.getIdSistemaEsgoto())
						&& !("" + ConstantesSistema.NUMERO_NAO_INFORMADO).equalsIgnoreCase(filtrarBaciaActionForm.getIdSistemaEsgoto())){
			FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
			filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.ID, Integer.valueOf(filtrarBaciaActionForm
							.getIdSistemaEsgoto())));

			SistemaEsgoto sistemaEsgoto = (SistemaEsgoto) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroSistemaEsgoto,
							SistemaEsgoto.class.getName()));

			if(sistemaEsgoto == null){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			relatorioManterBacia.addParametro("sistemaEsgotoDescricaoComIdParamentro", sistemaEsgoto.getDescricaoComId());
		}else{
			relatorioManterBacia.addParametro("sistemaEsgotoDescricaoComIdParamentro", "");
		}

		if(!Util.isVazioOuBranco(filtrarBaciaActionForm.getIdSubsistemaEsgoto())
						&& !("" + ConstantesSistema.NUMERO_NAO_INFORMADO).equalsIgnoreCase(filtrarBaciaActionForm.getIdSubsistemaEsgoto())){
			FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
			filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.ID, Integer
							.valueOf(filtrarBaciaActionForm.getIdSubsistemaEsgoto())));

			SubsistemaEsgoto subsistemaEsgoto = (SubsistemaEsgoto) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName()));

			if(subsistemaEsgoto == null){
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			relatorioManterBacia.addParametro("subsistemaEsgotoDescricaoComCodigoParamentro", subsistemaEsgoto.getDescricaoComCodigo());
		}else{
			relatorioManterBacia.addParametro("subsistemaEsgotoDescricaoComCodigoParamentro", "");
		}

		relatorioManterBacia.addParametro("indicadorUso", filtrarBaciaActionForm.getIndicadorUso());
		// Fim da parte que vai mandar os parametros para o relatório

		// Chama o metódo de gerar relatório passando o código da analise como parâmetro
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterBacia.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		try{
			retorno = processarExibicaoRelatorio(relatorioManterBacia, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);
		}catch(SistemaException ex){
			// Manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// Seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// Manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.relatorio.vazio");

			// Seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		// Devolve o mapeamento contido na variável retorno
		return retorno;
	}

}