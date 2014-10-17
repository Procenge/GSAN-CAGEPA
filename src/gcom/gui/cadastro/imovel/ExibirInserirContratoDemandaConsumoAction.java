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
 * Inserir Contrato Demanda Consumo
 * 
 * @author Felipe Rosacruz
 * @date 12/01/2014
 */
public class ExibirInserirContratoDemandaConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirContratoDemandaConsumo");
		InserirContratoDemandaConsumoActionForm form = (InserirContratoDemandaConsumoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("consultarImovel") != null){
			Integer idImovel = Integer.valueOf(form.getIdImovel());
			Imovel imovel = fachada.consultarImovel(idImovel);
			if(imovel == null){
				throw new ActionServletException("atencao.matricula.imovel.inexistente");
			}
			
			FiltroContratoDemandaConsumo filtroContratoDemandaConsumo = new FiltroContratoDemandaConsumo();
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.IMOVEL_ID, imovel.getId()));
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.INDICADORENCERRAMENTO, ConstantesSistema.NAO));
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

		}

		Collection<ConsumoTarifa> colecaoTarifaConsumo = null;

		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa(FiltroConsumoTarifa.DESCRICAO);
		filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO, ConstantesSistema.SIM));
		colecaoTarifaConsumo = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

		sessao.setAttribute("colecaoTarifaConsumo", colecaoTarifaConsumo);



		return retorno;
	}

}
