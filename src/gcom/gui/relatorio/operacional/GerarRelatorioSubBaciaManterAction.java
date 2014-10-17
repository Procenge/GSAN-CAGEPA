
package gcom.gui.relatorio.operacional;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.operacional.FiltrarSubBaciaActionForm;
import gcom.operacional.Bacia;
import gcom.operacional.FiltroBacia;
import gcom.operacional.FiltroSubBacia;
import gcom.operacional.SubBacia;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.operacional.RelatorioManterSubBacia;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action responsável pela exibição do relatório de sub-bacia manter
 * 
 * @author Bruno Ferreira dos Santos
 * @created 01 de Fevereiro de 2011
 */

public class GerarRelatorioSubBaciaManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSubBaciaActionForm filtrarSubBaciaActionForm = (FiltrarSubBaciaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroSubBacia filtroSubBacia = (FiltroSubBacia) sessao.getAttribute("filtroSubBaciaSessao");

		// Inicio da parte que vai mandar os parametros para o relatório

		SubBacia subBaciaParametros = new SubBacia();

		String idBacia = (String) filtrarSubBaciaActionForm.getBacia();

		Bacia bacia = null;

		if(idBacia != null && !idBacia.equals("") && !idBacia.equals("-1")){
			FiltroBacia filtroBacia = new FiltroBacia();

			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.ID, idBacia));
			filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection bacias = fachada.pesquisar(filtroBacia, Bacia.class.getName());

			if(bacias != null && !bacias.isEmpty()){
				// O municipio foi encontrado
				Iterator baciaIterator = bacias.iterator();

				bacia = (Bacia) baciaIterator.next();

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Bacia");
			}

		}else{
			bacia = new Bacia();
		}

		int codigoSubBacia = 0;

		String codigoSubBaciaPesquisar = (String) filtrarSubBaciaActionForm.getCodigo();

		if(codigoSubBaciaPesquisar != null && !codigoSubBaciaPesquisar.equals("")){
			codigoSubBacia = Integer.parseInt(codigoSubBaciaPesquisar);
		}

		Short indicadorDeUso = null;

		if(filtrarSubBaciaActionForm.getIndicadorUso() != null && !filtrarSubBaciaActionForm.getIndicadorUso().equals("")){

			indicadorDeUso = new Short("" + filtrarSubBaciaActionForm.getIndicadorUso());
		}

		// seta os parametros que serão mostrados no relatório
		subBaciaParametros.setBacia(bacia);
		subBaciaParametros.setCodigo(codigoSubBacia);
		subBaciaParametros.setDescricao(filtrarSubBaciaActionForm.getDescricao());
		subBaciaParametros.setIndicadorUso(indicadorDeUso);

		// Fim da parte que vai mandar os parametros para o relatório
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterSubBacia relatorio = new RelatorioManterSubBacia((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"), "/relatorioManterSubBacia.jasper");
		relatorio.addParametro("filtroSubBaciaSessao", filtroSubBacia);
		relatorio.addParametro("subBaciaParametros", subBaciaParametros);
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}
}
