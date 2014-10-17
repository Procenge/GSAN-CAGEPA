
package gcom.gui.operacional.abastecimento;

import gcom.cadastro.unidadeoperacional.FiltroUnidadeOperacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.ZonaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] Manter Setor Abastecimento
 * 
 * @author Péricles Tavares
 * @date 08/02/2011
 */
public class ExibirFiltrarSetorAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarSetorAbastecimento");

		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = this.getFachada();

		FiltrarSetorAbastecimentoActionForm form = (FiltrarSetorAbastecimentoActionForm) actionForm;

		// Código para checar ou não o ATUALIZAR e Passar o valor do Indicador de Uso como "TODOS"
		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
		}

		// Recuperar Sistema Abastecimento
		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		filtroSistemaAbastecimento.setCampoOrderBy(FiltroSistemaAbastecimento.DESCRICAO);
		Collection<SistemaAbastecimento> sistemasAbastecimento = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class
						.getName());

		// [FS0001] - Verificar existência de dados
		if(sistemasAbastecimento == null || sistemasAbastecimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Sistema de Abastecimento");
		}

		// Recuperar Distrito Operacional
		Collection<DistritoOperacional> distritosOperacionais = new ArrayList<DistritoOperacional>();
		if(httpServletRequest.getParameter("consultaSistema") != null
						&& ConstantesSistema.NUMERO_NAO_INFORMADO != Short.parseShort(form.getSistemaAbastecimento())){
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOperacional.SISTEMA_ABASTECIMENTO_ID, form
							.getSistemaAbastecimento()));
			filtroDistritoOperacional.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);
			distritosOperacionais = fachada.pesquisar(filtroDistritoOperacional, DistritoOperacional.class.getName());
			// [FS0001] - Verificar existência de dados
			if(distritosOperacionais == null || distritosOperacionais.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Distrito Operacional");
			}
		}

		// Recuperar Zona Abastecimento
		Collection<ZonaAbastecimento> zonaAbastecimento = new ArrayList<ZonaAbastecimento>();

		if(httpServletRequest.getParameter("consultaSistema") != null
						&& ConstantesSistema.NUMERO_NAO_INFORMADO != Short.parseShort(form.getSistemaAbastecimento())
						&& ConstantesSistema.NUMERO_NAO_INFORMADO != Short.parseShort(form.getDistritoOperacional())){
			FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();
			filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO_ID, form
							.getSistemaAbastecimento()));
			filtroZonaAbastecimento.setCampoOrderBy(FiltroZonaAbastecimento.DESCRICAO);
			zonaAbastecimento = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

			// [FS0001] - Verificar existência de dados
			if(zonaAbastecimento == null || zonaAbastecimento.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Zona de Abastecimento");
			}
		}

		// Setando as coleções na sessão
		sessao.setAttribute("colecaoSistemaAbastecimento", sistemasAbastecimento);
		sessao.setAttribute("colecaoDistritoOperacional", distritosOperacionais);
		sessao.setAttribute("colecaoZonaAbastecimento", zonaAbastecimento);

		return retorno;
	}

}
