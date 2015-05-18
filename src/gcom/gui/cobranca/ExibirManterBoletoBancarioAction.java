
package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorMovimentoItem;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
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
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class ExibirManterBoletoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirManterBoletoBancarioAction");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String action = httpServletRequest.getParameter("action");

		if(!Util.isVazioOuBranco(action)){
			// Tela de sucesso da operação de cancelamento

			String[] idRegistrosCancelamento = (String[]) sessao.getAttribute("idRegistrosCancelamento");

			retorno = actionMapping.findForward("telaSucesso");

			montarPaginaSucesso(httpServletRequest, idRegistrosCancelamento.length + " Boletos Bancários cancelados com sucesso.",
							"Realizar outra Manutenção de Boleto Bancário", "exibirFiltrarBoletoBancarioAction.do?menu=sim");
		}else{
			// Obtém a instância da fachada
			Fachada fachada = Fachada.getInstancia();

			BoletoBancarioHelper boletoBancarioHelperOriginal = (BoletoBancarioHelper) sessao.getAttribute("boletoBancarioHelper");
			boolean verificarNumeroBoletoCartaCobranca = (Boolean) sessao.getAttribute("verificarNumeroBoletoCartaCobranca");
			boolean desconsiderarParametros = (Boolean) sessao.getAttribute("desconsiderarParametros");
			String indicadorAtualizar = (String) sessao.getAttribute("indicadorAtualizar");
			String indicadorTotalizarImovel = (String) sessao.getAttribute("indicadorTotalizarImovel");

			// Clone do objeto
			BoletoBancarioHelper boletoBancarioHelper = boletoBancarioHelperOriginal.clone();

			// Parâmetros passado pela tela de totalizador
			String codigoAgenteArrecadador = httpServletRequest.getParameter("codigoAgenteArrecadador");
			String idImovel = httpServletRequest.getParameter("idImovel");

			if(Util.isVazioOuBranco(codigoAgenteArrecadador) && Util.isVazioOuBranco(idImovel)){
				codigoAgenteArrecadador = (String) sessao.getAttribute("codigoAgenteArrecadador");
				idImovel = (String) sessao.getAttribute("idImovel");
			}

			if(!Util.isVazioOuBranco(codigoAgenteArrecadador) && !Util.isVazioOuBranco(idImovel)){
				FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
				filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, codigoAgenteArrecadador));

				Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());
				Arrecadador arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);
				boletoBancarioHelper.setArrecadador(arrecadador);

				Imovel imovel = new Imovel();
				imovel.setId(Integer.valueOf(idImovel));
				boletoBancarioHelper.setImovel(imovel);

				sessao.setAttribute("voltar", "totalizador");
				sessao.setAttribute("codigoAgenteArrecadador", codigoAgenteArrecadador);
				sessao.setAttribute("idImovel", idImovel);
			}else{
				sessao.setAttribute("voltar", "filtrar");
			}

			// Remove da sessão o indicador que fecha popup de cancelamento
			sessao.removeAttribute("closePage");
			sessao.removeAttribute("idRegistrosCancelamento");

			// Total de registros
			Integer totalRegistros = fachada.pesquisarQuantidadeBoletoBancario(boletoBancarioHelper, verificarNumeroBoletoCartaCobranca,
							desconsiderarParametros, false);

			if(totalRegistros == 0){
				// Nenhuma registro encontrado
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Bancário");
			}

			// Paginação
			retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

			Collection<BoletoBancario> colecaoBoletoBancario = fachada.pesquisarBoletoBancario(boletoBancarioHelper,
							verificarNumeroBoletoCartaCobranca, desconsiderarParametros, false, (Integer) httpServletRequest
											.getAttribute("numeroPaginasPesquisa"));

			if(!Util.isVazioOuBranco(indicadorAtualizar) && !Util.isVazioOrNulo(colecaoBoletoBancario) && colecaoBoletoBancario.size() == 1){
				// Coleção com apenas um registro e indicador de atualizar marcado
				BoletoBancario boletoBancario = (BoletoBancario) Util.retonarObjetoDeColecao(colecaoBoletoBancario);
				Integer idBoletoBancario = boletoBancario.getId();

				sessao.setAttribute("idBoletoBancario", idBoletoBancario.toString());

				retorno = actionMapping.findForward("exibirAtualizarBoletoBancarioAction");
			}else if(!Util.isVazioOuBranco(indicadorTotalizarImovel) && indicadorTotalizarImovel.equals(ConstantesSistema.SIM.toString())){
				// Página de totalização
				retorno = actionMapping.findForward("exibirTotalizadorBoletoBancarioAction");

				sessao.removeAttribute("indicadorTotalizarImovel");
			}else{
				Collection<BoletoBancarioFiltroHelper> colecaoBoletoBancarioFiltroHelper = new ArrayList();

				BoletoBancarioFiltroHelper boletoBancarioFiltroHelper = null;
				BoletoBancarioSituacaoHistorico boletoBancarioSituacaoHistorico = null;
				BoletoBancarioMovimentacao boletoBancarioMovimentacao = null;
				ArrecadadorMovimentoItem arrecadadorMovimentoItem = null;

				Collection<BoletoBancarioSituacaoHistorico> colecaoSituacaoHistorico = null;
				Collection<BoletoBancarioMovimentacao> colecaoMovimentacao = null;
				FiltroBoletoBancarioSituacaoHistorico filtroSituacaoHistorico = null;

				for(BoletoBancario boletoBancario : colecaoBoletoBancario){
					Integer boletoBancarioId = boletoBancario.getId();

					boletoBancarioFiltroHelper = new BoletoBancarioFiltroHelper();
					boletoBancarioFiltroHelper.setBoletoBancario(boletoBancario);

					filtroSituacaoHistorico = new FiltroBoletoBancarioSituacaoHistorico();
					filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_ID, boletoBancarioId));
					filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_SITUACAO_ID,
									BoletoBancarioSituacao.GERADO_NAO_ENVIADO_AO_BANCO));

					colecaoSituacaoHistorico = fachada.pesquisar(filtroSituacaoHistorico, BoletoBancarioSituacaoHistorico.class.getName());

					if(!Util.isVazioOrNulo(colecaoSituacaoHistorico)){
						boletoBancarioSituacaoHistorico = (BoletoBancarioSituacaoHistorico) Util
										.retonarObjetoDeColecao(colecaoSituacaoHistorico);

						boletoBancarioFiltroHelper.setBoletoBancarioSituacaoHistorico(boletoBancarioSituacaoHistorico);
					}

					// Só é possível cancelar um boleto, caso o mesmo nunca tenha sido enviado ao
					// banco

					boolean habilitarCancelamento = false;

					BoletoBancarioMotivoCancelamento motivoCancelamento = boletoBancario.getBoletoBancarioMotivoCancelamento();

					// Verifica se não foi cancelado
					if(motivoCancelamento == null){
						// Valida se o boleto por ser cancelado [UC3023][SB000B]
						if(fachada.boletoBancarioPodeSerCancelado(boletoBancarioId)){
							habilitarCancelamento = true;
						}

						if(!habilitarCancelamento){
							habilitarCancelamento = fachada
											.verificaExistenciaBoletoAgregadorComSituacaoBaixadoEProtestado(boletoBancarioId);
						}
					}

					// Situação do checkbox de seleção
					boletoBancarioFiltroHelper.setHabilitarCancelamento(habilitarCancelamento);

					colecaoBoletoBancarioFiltroHelper.add(boletoBancarioFiltroHelper);
				}

				sessao.setAttribute("colecaoBoletoBancarioFiltroHelper", colecaoBoletoBancarioFiltroHelper);
			}
		}

		sessao.removeAttribute("indicadorAtualizar");

		return retorno;
	}
}
