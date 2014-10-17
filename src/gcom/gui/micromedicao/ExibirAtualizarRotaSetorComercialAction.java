/**
 * 
 */

package gcom.gui.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;
import gcom.micromedicao.leitura.FiltroLeituraTipo;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.seguranca.acesso.usuario.Usuario;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 * @created 26/05/2011
 */
public class ExibirAtualizarRotaSetorComercialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarRotaSetorComercial");

		InserirRotaActionForm inserirRotaActionForm = (InserirRotaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		if(httpServletRequest.getParameter("atualizar") != null && httpServletRequest.getParameter("atualizar").equals("1")){

			Collection rotas = (Collection) sessao.getAttribute("colecaoRotaAtualizacaoSetorComercial");

			if(rotas != null && !rotas.isEmpty()){
				Iterator itRotas = rotas.iterator();

				FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
				faturamentoGrupo.setId(new Integer(inserirRotaActionForm.getFaturamentoGrupo()));

				LeituraTipo leituraTipo = new LeituraTipo();
				leituraTipo.setId(new Integer(inserirRotaActionForm.getLeituraTipo()));

				Empresa empresa = new Empresa();
				empresa.setId(new Integer(inserirRotaActionForm.getEmpresaLeituristica()));

				while(itRotas.hasNext()){
					Rota rota = (Rota) itRotas.next();

					rota.setFaturamentoGrupo(faturamentoGrupo);
					rota.setLeituraTipo(leituraTipo);
					rota.setEmpresa(empresa);
					rota.setIndicadorGerarFalsaFaixa(new Short(inserirRotaActionForm.getIndicadorGerarFalsaFaixa()));
					if(inserirRotaActionForm.getPercentualGeracaoFaixaFalsa() != null
									&& !inserirRotaActionForm.getPercentualGeracaoFaixaFalsa().equals("")){
						rota.setPercentualGeracaoFaixaFalsa(new BigDecimal(inserirRotaActionForm.getPercentualGeracaoFaixaFalsa()));
					}
					rota.setIndicadorGerarFiscalizacao(new Short(inserirRotaActionForm.getIndicadorGerarFiscalizacao()));
					if(inserirRotaActionForm.getPercentualGeracaoFiscalizacao() != null
									&& !inserirRotaActionForm.getPercentualGeracaoFiscalizacao().equals("")){
						rota.setPercentualGeracaoFiscalizacao(new BigDecimal(inserirRotaActionForm.getPercentualGeracaoFiscalizacao()));
					}
				}
			}

			httpServletRequest.setAttribute("fecharJanela", "1");

			return retorno;

		}

		// Pesquisando grupo de faturamento
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO_ABREVIADA);
		Collection<FaturamentoGrupo> collectionFaturamentoGrupo = getFachada().pesquisar(filtroFaturamentoGrupo,
						FaturamentoGrupo.class.getName());
		sessao.setAttribute("collectionFaturamentoGrupo", collectionFaturamentoGrupo);
		// Fim de pesquisando grupo de faturamento

		// Pesquisando tipo de leitura
		FiltroLeituraTipo filtroLeituraTipo = new FiltroLeituraTipo();
		filtroLeituraTipo.setCampoOrderBy(FiltroLeituraTipo.DESCRICAO);
		Collection<LeituraTipo> collectionLeituraTipo = getFachada().pesquisar(filtroLeituraTipo, LeituraTipo.class.getName());
		sessao.setAttribute("collectionLeituraTipo", collectionLeituraTipo);
		// Fim de pesquisando tipo de leitura

		// Pesquisando empresa leiturística
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
		Collection<Empresa> collectionEmpresa = getFachada().pesquisar(filtroEmpresa, Empresa.class.getName());
		sessao.setAttribute("collectionEmpresa", collectionEmpresa);
		// Fim de pesquisando empresa leiturística

		return retorno;
	}

}
