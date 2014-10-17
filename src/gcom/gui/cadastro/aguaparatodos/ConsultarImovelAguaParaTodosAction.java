/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import gcom.cadastro.aguaparatodos.FiltroImovelAguaParaTodos;
import gcom.cadastro.aguaparatodos.ImovelAguaParaTodos;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.FiltroUsuario;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ConsultarImovelAguaParaTodosAction
				extends GcomAction {

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 27/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarImovelAguaParaTodos");

		ConsultarImovelAguaParaTodosActionForm form = (ConsultarImovelAguaParaTodosActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		form.limparFormulario();

		if(form != null && httpServletRequest.getParameter("id") != null && !httpServletRequest.getParameter("id").equals("")){
			Integer idImovel = Integer.valueOf((String) form.getMatricula());
			Imovel imovel = null;
			try{
				imovel = fachada.pesquisarImovel(idImovel);
			}catch(NumberFormatException e){
				throw new ActionServletException("atencao.nao.numerico", null, form.getMatricula());
			}

			if(imovel != null){
				FiltroImovelAguaParaTodos filtroImovelAguaParaTodos = new FiltroImovelAguaParaTodos();
				filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID, httpServletRequest
								.getParameter("id")));
				filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroImovelAguaParaTodos.CODIGO_SITUACAO,
								ImovelAguaParaTodos.EXCLUIDO));

				Collection colImovelAguaParaTodos = fachada.pesquisar(filtroImovelAguaParaTodos, ImovelAguaParaTodos.class.getName());
				if((!colImovelAguaParaTodos.isEmpty()) && (colImovelAguaParaTodos != null)){
					ImovelAguaParaTodos imovelAguaParaTodos = (ImovelAguaParaTodos) colImovelAguaParaTodos.iterator().next();

					String flagBotaoHist = form.NAO_EXIBE;

					form.setMatricula(imovel.getId().toString());
					form.setNic(imovelAguaParaTodos.getIdContribuinte().toString());
					form.setSituacao(imovelAguaParaTodos.getCodigoSituacao().toString());
					form.setNomeContribuinte(imovelAguaParaTodos.getNomeParticipante());
					form.setDataCadastramento(Util.formatarData(imovelAguaParaTodos.getDataCadastramento()));
					if(imovelAguaParaTodos.getDataHabilitacao() != null){
						form.setDataHabilitacao(Util.formatarData(imovelAguaParaTodos.getDataHabilitacao()));
						form.setDataHabilitacaoTime("" + imovelAguaParaTodos.getDataHabilitacao().getTime());
					}
					FiltroUsuario filtroUsuario = new FiltroUsuario();
					filtroUsuario.adicionarParametro(new ParametroSimples(filtroUsuario.ID, imovelAguaParaTodos.getIdUsuarioInclusao()
									.getId()));

					Usuario usuario = (Usuario) fachada.pesquisar(filtroUsuario, Usuario.class.getName()).iterator().next();
					form.setUsuarioInclusao(usuario.getId().toString() + " - " + usuario.getNomeUsuario());
					if(imovelAguaParaTodos.getAnoMesReferenciaInicial() != null){
						form.setDataReferenciaFaruramentoInicial(imovelAguaParaTodos.getAnoMesReferenciaInicialFormatado());
						form.setDataReferencia(imovelAguaParaTodos.getAnoMesReferenciaInicial().toString());
						flagBotaoHist = form.EXIBE;
					}
					form.setFlagBotaoHist(flagBotaoHist);
					if(imovelAguaParaTodos.getDataExclusao() != null){
						form.setDataExclusao(Util.formatarData(imovelAguaParaTodos.getDataExclusao()));
						String motivoExclusao = "";
						if(imovelAguaParaTodos.getMotivoExclusao().equals(1)){
							motivoExclusao = "Por Infração";
						}else if(imovelAguaParaTodos.getMotivoExclusao().equals(2)){
							motivoExclusao = "Perda Água Para Todos";
						}else if(imovelAguaParaTodos.getMotivoExclusao().equals(3)){
							motivoExclusao = "Mudança de Categoria";
						}else if(imovelAguaParaTodos.getMotivoExclusao().equals(4)){
							motivoExclusao = "Consumo Acima de 10m³";
						}else if(imovelAguaParaTodos.getMotivoExclusao().equals(5)){
							motivoExclusao = "Falta de Pagamento";
						}else if(imovelAguaParaTodos.getMotivoExclusao().equals(6)){
							motivoExclusao = "Validade";
						}
						form.setMotivoExclusao(motivoExclusao);
						filtroUsuario
										.adicionarParametro(new ParametroSimples(filtroUsuario.ID, imovelAguaParaTodos
														.getIdUsuarioExclusao()));

						usuario = (Usuario) fachada.pesquisar(filtroUsuario, Usuario.class.getName()).iterator().next();
						form.setUsuarioExclusao(usuario.getId().toString() + " - " + usuario.getNomeUsuario());
						if(imovelAguaParaTodos.getAnoMesReferenciaFinal() != null){
							form.setDataReferenciaFaruramentoFinal(imovelAguaParaTodos.getAnoMesReferenciaFinalFormatado());
						}
					}
				}else{
					throw new ActionServletException("atencao.pesquisa.imovel.inexistente", null, form.getMatricula());
				}

				/*
				 * form.setCodigoTarifa(imovel.getCodigoTarifaTemporaria().toString());
				 * 
				 * form.setDataValidadeTarifa(Util.formatarData(imovel.getDataValidadeTarifaTemporaria
				 * ()));
				 */
			}else{
				throw new ActionServletException("atencao.pesquisa.imovel.inexistente", null, form.getMatricula());
			}

		}

		return retorno;
	}

}
