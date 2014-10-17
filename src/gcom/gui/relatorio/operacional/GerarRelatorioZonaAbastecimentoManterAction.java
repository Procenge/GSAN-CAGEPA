
package gcom.gui.relatorio.operacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.operacional.FiltrarZonaAbastecimentoActionForm;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroBacia;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.ZonaAbastecimento;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.operacional.RelatorioManterZonaAbastecimento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action responsável pela exibição do relatório de zona de abastecimento manter
 * 
 * @author Bruno Ferreira dos Santos
 * @created 04 de Fevereiro de 2011
 */

public class GerarRelatorioZonaAbastecimentoManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarZonaAbastecimentoActionForm filtrarZonaAbastecimentoActionForm = (FiltrarZonaAbastecimentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroZonaAbastecimento filtroZonaAbastecimento = (FiltroZonaAbastecimento) sessao.getAttribute("filtroZonaAbastecimentoSessao");

		// Inicio da parte que vai mandar os parametros para o relatório

		ZonaAbastecimento zonaAbastecimentoParametros = new ZonaAbastecimento();

		String idDistritoOperacional = (String) filtrarZonaAbastecimentoActionForm.getIdDistritoOperacional();
		String idLocalidade = (String) filtrarZonaAbastecimentoActionForm.getIdLocalidade();

		DistritoOperacional distritoOperacional = null;

		if(!Util.isVazioOuBranco(idDistritoOperacional)
						&& !idDistritoOperacional.equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){

			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroBacia.ID, idDistritoOperacional));
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			distritoOperacional = (DistritoOperacional) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroDistritoOperacional,
							DistritoOperacional.class.getName()));

			if(distritoOperacional == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Operacional");
			}

		}else{
			distritoOperacional = new DistritoOperacional();
		}

		Localidade localidade = null;

		if(!Util.isVazioOuBranco(idLocalidade) && !idLocalidade.equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){

			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroBacia.ID, idLocalidade));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			localidade = (Localidade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidade, Localidade.class.getName()));

			if(localidade == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
			}

		}else{
			localidade = new Localidade();
		}

		int codigoZonaAbastecimento = 0;

		String codigoZonaAbastecimentoPesquisar = (String) filtrarZonaAbastecimentoActionForm.getCodigo();

		if(codigoZonaAbastecimentoPesquisar != null && !codigoZonaAbastecimentoPesquisar.equals("")){
			codigoZonaAbastecimento = Integer.parseInt(codigoZonaAbastecimentoPesquisar);
		}

		Short indicadorDeUso = null;

		if(filtrarZonaAbastecimentoActionForm.getIndicadorUso() != null && !filtrarZonaAbastecimentoActionForm.getIndicadorUso().equals("")){

			indicadorDeUso = new Short("" + filtrarZonaAbastecimentoActionForm.getIndicadorUso());
		}

		// seta os parametros que serão mostrados no relatório
		zonaAbastecimentoParametros.setCodigo(codigoZonaAbastecimento);
		zonaAbastecimentoParametros.setDescricao(filtrarZonaAbastecimentoActionForm.getDescricao());
		zonaAbastecimentoParametros.setIndicadorUso(indicadorDeUso);
		zonaAbastecimentoParametros.setLocalidade(localidade);
		zonaAbastecimentoParametros.setDistritoOperacional(distritoOperacional);

		// Fim da parte que vai mandar os parametros para o relatório
		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioManterZonaAbastecimento relatorio = new RelatorioManterZonaAbastecimento((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"), "/relatorioManterZonaAbastecimento.jasper");
		relatorio.addParametro("filtroZonaAbastecimentoSessao", filtroZonaAbastecimento);
		relatorio.addParametro("zonaAbastecimentoParametros", zonaAbastecimentoParametros);
		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

}
