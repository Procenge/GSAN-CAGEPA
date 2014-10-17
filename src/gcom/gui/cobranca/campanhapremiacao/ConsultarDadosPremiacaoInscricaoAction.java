package gcom.gui.cobranca.campanhapremiacao;

import gcom.cadastro.cliente.FiltroOrgaoExpedidorRg;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cobranca.campanhapremiacao.CampanhaCadastroFone;
import gcom.cobranca.campanhapremiacao.CampanhaPremiacao;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaCadastroFone;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremiacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Felipe Rosacruz
 * @date 24/09/2013
 */
public class ConsultarDadosPremiacaoInscricaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("ConsultarDadosPremiacaoInscricao");

		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("idRegistroConsultar") != null){
			
			Integer idCampanhaPremiacao = Integer.parseInt(httpServletRequest.getParameter("idRegistroConsultar"));

			FiltroCampanhaPremiacao filtroCampanhaPremiacao = new FiltroCampanhaPremiacao();
			filtroCampanhaPremiacao.adicionarParametro(new ParametroSimples("id", idCampanhaPremiacao));

			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaSorteio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.orgaoExpedidorRG");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.unidadeFederacao");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.imovel");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.imovel.quadra");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.imovel.setorComercial");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaCadastro.imovel.localidade");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremiacaoMotCancel");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("usuarioCancelamentoPremiacao");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("usuarioEntregaPremio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.gerenciaRegional");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.unidadeNegocio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.eloPremio");
			filtroCampanhaPremiacao.adicionarCaminhoParaCarregamentoEntidade("campanhaPremio.localidade");
			

			CampanhaPremiacao campanhaPremiacao = (CampanhaPremiacao) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroCampanhaPremiacao, CampanhaPremiacao.class.getName()));

			String endereco = Fachada.getInstancia().pesquisarEndereco(campanhaPremiacao.getCampanhaCadastro().getImovel().getId());

			if(campanhaPremiacao.getCampanhaCadastro().getNumeroCPF() != null){
				sessao.setAttribute("indicadorResidencial", ConstantesSistema.SIM);
			}else if(campanhaPremiacao.getCampanhaCadastro().getNumeroCNPJ() != null){
				sessao.setAttribute("indicadorResidencial", ConstantesSistema.NAO);
			}

			sessao.setAttribute("campanhaPremiacao", campanhaPremiacao);
			sessao.setAttribute("endereco", endereco);

			FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
			Collection<OrgaoExpedidorRg> colecaOrgaoExpedidorRg = Fachada.getInstancia().pesquisar(filtroOrgaoExpedidorRg,
							OrgaoExpedidorRg.class.getName());
			sessao.setAttribute("colOrgaoExpedidorRg", colecaOrgaoExpedidorRg);

			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
			Collection<UnidadeFederacao> colecaoUnidadeFederacao = Fachada.getInstancia().pesquisar(filtroUnidadeFederacao,
							UnidadeFederacao.class.getName());
			sessao.setAttribute("colEstados", colecaoUnidadeFederacao);

			FiltroCampanhaCadastroFone filtroCampanhaCadastroFone = new FiltroCampanhaCadastroFone();
			filtroCampanhaCadastroFone.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastroFone.CAMPANHA_CADASTRO_ID,
							campanhaPremiacao.getCampanhaCadastro().getId()));
			filtroCampanhaCadastroFone.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastroFone.FONE_TIPO);
			Collection<CampanhaCadastroFone> colecaoCampanhaCadastroFones = Fachada.getInstancia().pesquisar(filtroCampanhaCadastroFone,
							CampanhaCadastroFone.class.getName());

			sessao.setAttribute("colecaoCadastroFone", colecaoCampanhaCadastroFones);
		}


			
		return retorno;
	}
}