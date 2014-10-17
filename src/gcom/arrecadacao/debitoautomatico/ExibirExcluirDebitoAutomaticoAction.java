
package gcom.arrecadacao.debitoautomatico; // FIXME ALERT: O q essa classe está fazendo aqui?

import gcom.cadastro.imovel.Imovel;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirExcluirDebitoAutomaticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirExcluirDebitoAutomatico");

		HttpSession sessao = httpServletRequest.getSession(false);

		ExibirExcluirDebitoAutomaticoActionForm formulario = (ExibirExcluirDebitoAutomaticoActionForm) actionForm;

		sessao.removeAttribute("debitoAutomaticoSessao");

		if(formulario.getIdImovel() != null && !formulario.getIdImovel().equals("")){

			Imovel imovel = getFachada().pesquisarImovel(Util.obterInteger(formulario.getIdImovel()));

			// [FS0001] – Verificar existência da matrícula do imóvel
			if(imovel == null){

				httpServletRequest.setAttribute("corInscricao", "exception");
				formulario.setIdImovel("");
				formulario.setInscricaoImovel("Matrícula Inexistente");
			}else{

				FiltroDebitoAutomatico filtroDebitoAutomatico = new FiltroDebitoAutomatico();
				filtroDebitoAutomatico.adicionarParametro(new ParametroSimples(FiltroDebitoAutomatico.IMOVEL_ID, imovel.getId()));
				filtroDebitoAutomatico.adicionarParametro(new ParametroNulo(FiltroDebitoAutomatico.DATA_EXCLUSAO));

				filtroDebitoAutomatico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoAutomatico.BANCO);
				filtroDebitoAutomatico.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoAutomatico.IMOVEL);

				DebitoAutomatico debitoAutomatico = (DebitoAutomatico) Util.retonarObjetoDeColecao(getFachada().pesquisar(
								filtroDebitoAutomatico, DebitoAutomatico.class.getName()));

				if(debitoAutomatico == null || debitoAutomatico.getDataExclusao() != null){

					throw new ActionServletException("atencao.imovel.nao_debito_automatico");
				}

				httpServletRequest.setAttribute("corInscricao", null);
				sessao.setAttribute("debitoAutomaticoSessao", debitoAutomatico);
				formulario.setInscricaoImovel(imovel.getInscricaoFormatada());
				formulario.setCodigoAgencia(debitoAutomatico.getAgencia().getDescricaoComCodigo());
				formulario.setCodigoBanco(debitoAutomatico.getAgencia().getBanco().getDescricaoComId());
				formulario.setIdentificacaoClienteBanco(debitoAutomatico.getIdentificacaoClienteBanco());
				formulario.setDataOpcao(Util.formatarData(debitoAutomatico.getDataOpcaoDebitoContaCorrente()));
				formulario.setDataInclusao(Util.formatarData(debitoAutomatico.getDataInclusaoNovoDebitoAutomatico()));
			}
		}

		return retorno;
	}

}
