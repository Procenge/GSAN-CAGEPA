
package gcom.gui.cobranca;

import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarEmitirOSCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		String retorno = "sucesso";

		FiltrarEmitirOSCobrancaActionForm emitirOSCobrancaActionForm = (FiltrarEmitirOSCobrancaActionForm) form;
		Fachada fachada = Fachada.getInstancia();

		String tipoFiltro = emitirOSCobrancaActionForm.getTipoFiltro();

		boolean acaoTipoCronograma = false;
		if(tipoFiltro == null || tipoFiltro.equals("")){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Ação de Cobrança");
		}else{
			if(tipoFiltro.equals("cronograma")){
				acaoTipoCronograma = true;
			}else if(tipoFiltro.equals("eventual")){
				acaoTipoCronograma = false;
			}else{
				throw new ActionServletException("atencao.informe_campo", null, "Tipo de Ação de Cobrança");
			}
		}

		// Ação do Cronograma
		if(acaoTipoCronograma){

			String idCobrancaGrupoCronograma = emitirOSCobrancaActionForm.getIdCobrancaGrupoCronograma();
			String idComandoCronograma = emitirOSCobrancaActionForm.getIdComandoCronograma();
			String idCobrancaAcaoCronograma = emitirOSCobrancaActionForm.getIdCobrancaAcaoCronograma();
			String dataInicialCronograma = emitirOSCobrancaActionForm.getDataInicialCronograma();
			String dataFinalCronograma = emitirOSCobrancaActionForm.getDataFinalCronograma();

			boolean campoNaoInformado = true;

			Integer idCobrancaGrupo = null;
			if(idCobrancaGrupoCronograma != null && !idCobrancaGrupoCronograma.equals("") && !idCobrancaGrupoCronograma.equals("-1")){
				idCobrancaGrupo = Util.converterStringParaInteger(idCobrancaGrupoCronograma);
				campoNaoInformado = false;
			}

			Integer idComando = null;
			if(idComandoCronograma != null && !idComandoCronograma.equals("") && !idComandoCronograma.equals("-1")){
				idComando = Util.converterStringParaInteger(idComandoCronograma);
				campoNaoInformado = false;
			}

			Integer idCobrancaAcao = null;
			if(idCobrancaAcaoCronograma != null && !idCobrancaAcaoCronograma.equals("") && !idCobrancaAcaoCronograma.equals("-1")){
				idCobrancaAcao = Util.converterStringParaInteger(idCobrancaAcaoCronograma);
				campoNaoInformado = false;
			}

			Date dataInicial = null;
			if(dataInicialCronograma != null && !dataInicialCronograma.equals("")){
				dataInicial = Util.converteStringParaDate(dataInicialCronograma);
				campoNaoInformado = false;
			}

			Date dataFinal = null;
			if(dataFinalCronograma != null && !dataFinalCronograma.equals("")){
				dataFinal = Util.converteStringParaDate(dataFinalCronograma);
				campoNaoInformado = false;
			}

			if(campoNaoInformado){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			Collection<CobrancaAcaoAtividadeCronograma> colecaoCobrancaAcaoAtividadeCronograma = fachada
							.pesquisarCobrancaAcaoAtividadeCronograma(idCobrancaGrupo, idCobrancaAcao, idComando, dataInicial, dataFinal);
			// adicionando os comandos ao request
			request.setAttribute("colecaoCobrancaAcaoAtividadeCronograma", colecaoCobrancaAcaoAtividadeCronograma);
		}else{

			// Ação do Eventual
			String idCobrancaGrupoEventual = emitirOSCobrancaActionForm.getIdCobrancaGrupoEventual();
			String idLocalidadeEventual = emitirOSCobrancaActionForm.getIdLocalidadeEventual();
			String idSetorComercialEventual = emitirOSCobrancaActionForm.getIdSetorComercialEventual();
			String idComandoEventual = emitirOSCobrancaActionForm.getIdComandoEventual();
			String idCobrancaAcaoEventual = emitirOSCobrancaActionForm.getIdCobrancaAcaoEventual();
			String dataInicialEventual = emitirOSCobrancaActionForm.getDataInicialEventual();
			String dataFinalEventual = emitirOSCobrancaActionForm.getDataFinalEventual();

			boolean campoNaoInformado = true;

			Integer idCobrancaGrupo = null;
			if(idCobrancaGrupoEventual != null && !idCobrancaGrupoEventual.equals("") && !idCobrancaGrupoEventual.equals("-1")){
				idCobrancaGrupo = Util.converterStringParaInteger(idCobrancaGrupoEventual);
				campoNaoInformado = false;
			}

			Integer idLocalidade = null;
			if(idLocalidadeEventual != null && !idLocalidadeEventual.equals("") && !idLocalidadeEventual.equals("-1")){
				idLocalidade = Util.converterStringParaInteger(idLocalidadeEventual);
				campoNaoInformado = false;
			}

			Integer codigoSetorComercial = null;
			if(idSetorComercialEventual != null && !idSetorComercialEventual.equals("") && !idSetorComercialEventual.equals("-1")){

				Integer idSetorComercial = Util.converterStringParaInteger(idSetorComercialEventual);

				// Pesquisa o SetorComercial pelo ID
				// SetorComercial setorComercial = new SetorComercial();
				// setorComercial.setId(idSetorComercial);
				// Collection<Object> colecaoSetorComercial = fachada.pesquisar(setorComercial);
				// if (colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()) {
				// throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
				// }
				// setorComercial = (SetorComercial)
				// Util.retonarObjetoDeColecao(colecaoSetorComercial);

				// TESTE
				FiltroSetorComercial filtro = new FiltroSetorComercial();
				filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, idSetorComercial));
				Collection<Object> colecaoSetorComercial2 = fachada.pesquisar(filtro, SetorComercial.class.getName());
				if(colecaoSetorComercial2 == null || colecaoSetorComercial2.isEmpty()){
					throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
				}
				SetorComercial setorComercial2 = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial2);

				// Recupera o código do SetorComercial
				codigoSetorComercial = setorComercial2.getCodigo();
				campoNaoInformado = false;
			}

			Integer idComando = null;
			if(idComandoEventual != null && !idComandoEventual.equals("") && !idComandoEventual.equals("-1")){
				idComando = Util.converterStringParaInteger(idComandoEventual);
				campoNaoInformado = false;
			}

			Integer idCobrancaAcao = null;
			if(idCobrancaAcaoEventual != null && !idCobrancaAcaoEventual.equals("") && !idCobrancaAcaoEventual.equals("-1")){
				idCobrancaAcao = Util.converterStringParaInteger(idCobrancaAcaoEventual);
				campoNaoInformado = false;
			}

			Date dataInicial = null;
			if(dataInicialEventual != null && !dataInicialEventual.equals("")){
				dataInicial = Util.converteStringParaDate(dataInicialEventual);
				campoNaoInformado = false;
			}

			Date dataFinal = null;
			if(dataFinalEventual != null && !dataFinalEventual.equals("")){
				dataFinal = Util.converteStringParaDate(dataFinalEventual);
				campoNaoInformado = false;
			}

			if(campoNaoInformado){
				throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
			}

			Collection<CobrancaAcaoAtividadeComando> colecaoCobrancaAcaoAtividadeComando = fachada.pesquisarCobrancaAcaoAtividadeComando(
							idCobrancaGrupo, idCobrancaAcao, idComando, dataInicial, dataFinal, idLocalidade, codigoSetorComercial);

			// adicionando os comandos ao request
			request.setAttribute("colecaoCobrancaAcaoAtividadeComando", colecaoCobrancaAcaoAtividadeComando);
		}

		return mapping.findForward(retorno);
	}
}
