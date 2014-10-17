
package gcom.gui.cadastro.imovel;

import gcom.arrecadacao.ContratoDemandaConsumo;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroContratoDemandaConsumo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
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
 * @date 12/02/2014
 */
public class ExibirAtualizarContratoDemandaConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAtualizarContratoDemandaConsumo");

		HttpSession sessao = httpServletRequest.getSession();

		AtualizarContratoDemandaConsumoActionForm form = (AtualizarContratoDemandaConsumoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Recuperando o id para ConstratoDemandaConsumo
		Integer idRegistroAtualizacao = null;
		if(httpServletRequest.getAttribute("idRegistroAtualizacao") != null){
			idRegistroAtualizacao = (Integer) httpServletRequest.getAttribute("idRegistroAtualizacao");
		}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
			idRegistroAtualizacao = Util.converterStringParaInteger((String) httpServletRequest.getParameter("idRegistroAtualizacao"));
		}else{
			idRegistroAtualizacao = (Integer) sessao.getAttribute("idRegistroAtualizacao");
		}

		// ---Parte que trata o código quando o usuário tecla enter
		String consultarImovel = httpServletRequest.getParameter("consultarImovel");

		if(consultarImovel != null){
			Integer idImovel = Integer.valueOf(form.getIdImovel());
			Imovel imovel = fachada.consultarImovel(idImovel);
			if(imovel == null){
				throw new ActionServletException("atencao.matricula.imovel.inexistente");
			}

			FiltroContratoDemandaConsumo filtroContratoDemandaConsumo = new FiltroContratoDemandaConsumo();
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.IMOVEL_ID, imovel.getId()));
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.INDICADORENCERRAMENTO,
							ConstantesSistema.NAO));
			if(Util.isNaoNuloBrancoZero(fachada.pesquisar(filtroContratoDemandaConsumo, ContratoDemandaConsumo.class.getName()))){
				throw new ActionServletException("atencao.imovel.possui.contrato.demanda.consumo.vigente");
			}

			form.setIdImovelAux(form.getIdImovel());
			form.setInscricaoImovel(imovel.getInscricaoFormatada());
			form.setLigacaoAguaSituacao(imovel.getLigacaoAguaSituacao().getDescricao());
			form.setLigacaoEsgotoSituacao(imovel.getLigacaoEsgotoSituacao().getDescricao());
			try{
				form.setEnderecoImovel(fachada.pesquisarEnderecoFormatado(idImovel));
			}catch(ControladorException e){
				throw new ActionServletException("atencao.imovel_endereco.nao_cadastrado");
			}
			form.setTarifaConsumoAtual(imovel.getConsumoTarifa().getDescricao());

		}else{
			sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);

			Collection<ConsumoTarifa> colecaoTarifaConsumo = null;

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO, ConstantesSistema.SIM));
			colecaoTarifaConsumo = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			sessao.setAttribute("colecaoTarifaConsumo", colecaoTarifaConsumo);

			FiltroContratoDemandaConsumo filtroContratoDemandaConsumo = new FiltroContratoDemandaConsumo();
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.ID, idRegistroAtualizacao));
			filtroContratoDemandaConsumo.adicionarCaminhoParaCarregamentoEntidade("imovel");
			filtroContratoDemandaConsumo.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
			filtroContratoDemandaConsumo.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
			filtroContratoDemandaConsumo.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroContratoDemandaConsumo.adicionarCaminhoParaCarregamentoEntidade("consumoTarifa");
			ContratoDemandaConsumo contratoDemandaConsumo = (ContratoDemandaConsumo) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroContratoDemandaConsumo, ContratoDemandaConsumo.class.getName()));

			form.setIdImovel(contratoDemandaConsumo.getImovel().getId().toString());
			form.setInscricaoImovel(contratoDemandaConsumo.getImovel().getInscricaoFormatada());
			if(contratoDemandaConsumo.getNumeroContrato() != null){
			form.setNumeroContrato(contratoDemandaConsumo.getNumeroContrato().toString());
			}
			form.setMesAnoFaturamentoInicial(contratoDemandaConsumo.getMesAnoFaturamentoInicio());
			form.setMesAnoFaturamentoFinal(contratoDemandaConsumo.getMesAnoFaturamentoFim());
			if(contratoDemandaConsumo.getNumeroConsumoFixo() != null){
			form.setConsumoFixo(contratoDemandaConsumo.getNumeroConsumoFixo().toString());
			}
			if(contratoDemandaConsumo.getConsumoTarifa() != null){
			form.setIdTarifaConsumo(contratoDemandaConsumo.getConsumoTarifa().getId().toString());
			}
			form.setEmailgestorContrato(contratoDemandaConsumo.getEmail());

		}
		return retorno;
	}

}
