
package gcom.gui.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.bean.GupoFaturamentoHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3055] Encerrar Faturamento
 * 
 * @author Hebert Falcão
 * @date 01/04/2012
 */
public class ExibirEncerrarFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirEncerrarFaturamentoAction");

		// Form
		EncerrarFaturamentoActionForm form = (EncerrarFaturamentoActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String operacao = httpServletRequest.getParameter("operacao");

		if(!Util.isVazioOuBranco(operacao)){
			String referenciaStr = form.getReferencia();
			Integer referencia = Util.formatarMesAnoComBarraParaAnoMes(referenciaStr);

			String grupoSituacaoStr = form.getGrupoSituacao();
			Short grupoSituacao = Short.valueOf(grupoSituacaoStr);

			if(operacao.equals("1")){
				Collection<GupoFaturamentoHelper> colecaoGupoFaturamentoHelper = fachada.selecionarGruposFaturamentoPorSituacao(referencia,
								grupoSituacao);

				if(Util.isVazioOrNulo(colecaoGupoFaturamentoHelper)){
					ActionServletException actionServletException = new ActionServletException("atencao.grupo_faturamento_nenhum_resultado");
					actionServletException.setUrlBotaoVoltar("/gsan/exibirEncerrarFaturamentoAction.do");
					throw actionServletException;
				}

				sessao.setAttribute("colecaoGupoFaturamentoHelper", colecaoGupoFaturamentoHelper);
			}else if(operacao.equals("2")){
				// [FS0002] - Verificar existência de grupos de faturamento não faturados
				Collection<FaturamentoGrupo> colecaoGruposNaoFaturados = fachada.pesquisarGruposNaoFaturados(referencia);

				if(!Util.isVazioOrNulo(colecaoGruposNaoFaturados)){
					String descricaoAux = null;
					StringBuffer descricaoGrupo = new StringBuffer();

					for(FaturamentoGrupo faturamentoGrupo : colecaoGruposNaoFaturados){
						descricaoAux = faturamentoGrupo.getDescricao();

						descricaoGrupo.append(descricaoAux);
						descricaoGrupo.append(", ");
					}

					descricaoAux = descricaoGrupo.substring(0, descricaoGrupo.length() - 2);

					Integer referenciaAux = Util.somaUmMesAnoMesReferencia(referencia);
					String referenciaAuxStr = Util.formatarMesAnoReferencia(referenciaAux);

					httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/encerrarFaturamentoAction.do");

					return montarPaginaConfirmacao("atencao.grupos_nao_faturados", httpServletRequest, actionMapping, descricaoAux,
									referenciaAuxStr);
				}else{
					retorno = actionMapping.findForward("encerrarFaturamentoAction");
				}
			}
		}else{
			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			Integer referencia = sistemaParametro.getAnoMesFaturamento();
			String referenciaStr = Util.formatarMesAnoReferencia(referencia);
			form.setReferencia(referenciaStr);

			Short grupoSituacao = ConstantesSistema.TODOS;
			String grupoSituacaoStr = Short.toString(grupoSituacao);
			form.setGrupoSituacao(grupoSituacaoStr);

			Collection<GupoFaturamentoHelper> colecaoGupoFaturamentoHelper = fachada.selecionarGruposFaturamentoPorSituacao(referencia,
							grupoSituacao);

			if(Util.isVazioOrNulo(colecaoGupoFaturamentoHelper)){
				ActionServletException actionServletException = new ActionServletException("atencao.grupo_faturamento_nenhum_resultado");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirEncerrarFaturamentoAction.do");
				throw actionServletException;
			}

			sessao.setAttribute("colecaoGupoFaturamentoHelper", colecaoGupoFaturamentoHelper);
		}

		return retorno;
	}

}
