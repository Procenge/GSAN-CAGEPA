
package gcom.gui.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3009] - Substituir Leituras Anteriores
 * 
 * @author Hebert Falcão
 * @date 09/06/2011
 */
public class SubstituirLeiturasAnterioresAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		List<MedicaoHistorico> colecaoMedicaoHistorico = (List<MedicaoHistorico>) sessao
						.getAttribute("colecaoMedicaoHistorico");

		if(Util.isVazioOrNulo(colecaoMedicaoHistorico)){
			throw new ActionServletException("atencao.nenhuma_leitura_substituir");
		}

		Integer idImovel = (Integer) sessao.getAttribute("idImovelSelecionado");
		String idImovelStr = idImovel.toString();

		Short permiteAlterarConsumoMedio = null;

		Integer consumoMedio = null;
		Integer consumoMedioAux = null;
		Integer consumoMedioAuxTmp = null;

		String consumoMedioAuxTmpStr = "";

		HashMap<Integer, MedicaoHistorico> mapMedicaoHistoricoAtualizado = new HashMap<Integer, MedicaoHistorico>();
		HashMap<Integer, Integer> mapConsumoMedioAtualizado = new HashMap<Integer, Integer>();

		for(int i = 0; i < colecaoMedicaoHistorico.size(); i++){

			MedicaoHistorico medicaoHistorico = colecaoMedicaoHistorico.get(i);
			Integer idMedicaoHistorico = medicaoHistorico.getId();
			Integer leituraAtualFaturamento = medicaoHistorico.getLeituraAtualFaturamento();

			// Medição Tipo
			MedicaoTipo medicaoTipo = medicaoHistorico.getMedicaoTipo();
			Integer idMedicaoTipo = medicaoTipo.getId();

			String novaLeituraAtualFaturamentoStr = "";

			if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){
				novaLeituraAtualFaturamentoStr = (String) httpServletRequest.getParameter("leituraAgua" + idMedicaoHistorico);
			}else if(idMedicaoTipo.equals(MedicaoTipo.POCO)){
				novaLeituraAtualFaturamentoStr = (String) httpServletRequest.getParameter("leituraPoco" + idMedicaoHistorico);
			}

			permiteAlterarConsumoMedio = medicaoHistorico.getPermiteAlterarConsumoMedio();

			if(permiteAlterarConsumoMedio != null && permiteAlterarConsumoMedio.equals(ConstantesSistema.SIM)){
				consumoMedioAux = medicaoHistorico.getConsumoMedioAux();

				if(idMedicaoTipo.equals(MedicaoTipo.LIGACAO_AGUA)){
					consumoMedioAuxTmpStr = (String) httpServletRequest.getParameter("mediaAgua" + idMedicaoHistorico);
				}else if(idMedicaoTipo.equals(MedicaoTipo.POCO)){
					consumoMedioAuxTmpStr = (String) httpServletRequest.getParameter("mediaPoco" + idMedicaoHistorico);
				}

				if(!Util.isVazioOuBranco(consumoMedioAuxTmpStr)){
					consumoMedioAuxTmp = Integer.valueOf(consumoMedioAuxTmpStr);

					if(consumoMedioAux != null && consumoMedioAuxTmp != null && consumoMedioAux.intValue() != consumoMedioAuxTmp.intValue()){
						consumoMedio = consumoMedioAuxTmp;
					}
				}
			}

			Integer novaLeituraAtualFaturamento = Util.converterStringParaInteger(novaLeituraAtualFaturamentoStr);

			if((novaLeituraAtualFaturamento != null && !novaLeituraAtualFaturamento.equals(leituraAtualFaturamento))
							|| consumoMedio != null){

				medicaoHistorico.setLeituraAtualFaturamento(novaLeituraAtualFaturamento);
				mapConsumoMedioAtualizado.put(medicaoHistorico.getId(), consumoMedio);
				mapMedicaoHistoricoAtualizado.put(medicaoHistorico.getId(), medicaoHistorico);

				if(colecaoMedicaoHistorico.size() == 1){

					fachada.atualizarLeiturasAnteriores(medicaoHistorico, usuarioLogado, consumoMedio);
				}else if(i > 0 && i < (colecaoMedicaoHistorico.size())){

					MedicaoHistorico medicaoHistoricoPosterior = colecaoMedicaoHistorico.get(i - 1);

					if(mapMedicaoHistoricoAtualizado.containsKey(medicaoHistoricoPosterior.getId())){

						medicaoHistoricoPosterior = mapMedicaoHistoricoAtualizado.get(medicaoHistoricoPosterior.getId());
						medicaoHistoricoPosterior.setLeituraAnteriorFaturamento(novaLeituraAtualFaturamento);
						mapMedicaoHistoricoAtualizado.put(medicaoHistoricoPosterior.getId(), medicaoHistoricoPosterior);
					}else{

						medicaoHistoricoPosterior.setLeituraAnteriorFaturamento(novaLeituraAtualFaturamento);
						mapMedicaoHistoricoAtualizado.put(medicaoHistoricoPosterior.getId(), medicaoHistoricoPosterior);
					}
				}
			}
		}

		Collection colecaoIdsMedicaoHistoricoAtualizados = mapMedicaoHistoricoAtualizado.keySet();

		for(Object idMedicaoHistoricoAtualizado : colecaoIdsMedicaoHistoricoAtualizados){

			fachada.atualizarLeiturasAnteriores(mapMedicaoHistoricoAtualizado.get(idMedicaoHistoricoAtualizado), usuarioLogado,
							mapConsumoMedioAtualizado.get(idMedicaoHistoricoAtualizado));
		}

		httpServletRequest.setAttribute("sucesso", "Leituras substituídas com sucesso.");

		montarPaginaSucesso(httpServletRequest, "Substituição das leituras anteriores do imóvel " + idImovelStr
						+ " efetuada com sucesso com sucesso.", "Realizar outra Substituição de Leituras Anteriores",
						"exibirSubstituirLeituraAnteriorAction.do?menu=sim&peloMenu=true");

		return retorno;
	}

}
