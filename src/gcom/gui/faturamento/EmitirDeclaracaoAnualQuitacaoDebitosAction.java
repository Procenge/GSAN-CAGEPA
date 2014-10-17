
package gcom.gui.faturamento;

import gcom.batch.DadoComplementarEnumerator;
import gcom.batch.Processo;
import gcom.batch.ProcessoIniciado;
import gcom.batch.ProcessoIniciadoDadoComplementarHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.faturamento.RelatorioArquivoDeclaracaoAnualQuitacaoDebitos;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3014] Emitir Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 27/04/2013
 */
public class EmitirDeclaracaoAnualQuitacaoDebitosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		EmitirDeclaracaoAnualQuitacaoDebitosActionForm emitirDeclaracaoAnualQuitacaoDebitosActionForm = (EmitirDeclaracaoAnualQuitacaoDebitosActionForm) actionForm;

		if(Util.verificarIdNaoVazio(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdFaturamentoGrupo())
						&& Util.isVazioOuBranco(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdImovel())){

			String idFaturamentoGrupoStr = emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdFaturamentoGrupo();

			Integer idFaturamentoGrupo = Integer.valueOf(idFaturamentoGrupoStr);

			Integer qtdQuitacaoDebitoAnual = null;


			Integer anoBaseDeclaracaoQuitacaoDebitoAnual = null;

			if(!Util.isVazioOuBranco(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getAnoBaseDeclaracaoInformado())){

				anoBaseDeclaracaoQuitacaoDebitoAnual = Util.obterInteger(emitirDeclaracaoAnualQuitacaoDebitosActionForm
								.getAnoBaseDeclaracaoInformado());
			}

			if(Util.isVazioOuBranco(anoBaseDeclaracaoQuitacaoDebitoAnual)){

				try{
					anoBaseDeclaracaoQuitacaoDebitoAnual = Integer
									.valueOf((String) ParametroFaturamento.P_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL.executar());
				}catch(ControladorException e){
					throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL");
				}

				if(anoBaseDeclaracaoQuitacaoDebitoAnual == null
								|| anoBaseDeclaracaoQuitacaoDebitoAnual
												.equals(ConstantesSistema.SEM_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL)){

					throw new ActionServletException("atencao.required", null, "Ano Base da Declaração");
				}
			}

			qtdQuitacaoDebitoAnual = fachada.pesquisarQuitacaoDebitoAnualParaEmicaoQtd(idFaturamentoGrupo,
								anoBaseDeclaracaoQuitacaoDebitoAnual);

			// [FS0002] Nenhum imóvel selecionado para emissão da declaração
			if(qtdQuitacaoDebitoAnual == null || qtdQuitacaoDebitoAnual == 0){
				throw new ActionServletException("atencao.nenhum.imovel.selecionado.para.emissso.declaracao", idFaturamentoGrupoStr);
			}

			ProcessoIniciado processoIniciado = new ProcessoIniciado();

			Processo processo = new Processo();
			processo.setId(Processo.EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS);
			processoIniciado.setDataHoraAgendamento(new Date());

			// Verificar restrição de execução simultânea de processos
			if(fachada.isProcessoComRestricaoExecucaoSimultanea(processo.getId())){
				throw new ActionServletException("atencao.processo_restricao_execucao");
			}

			processoIniciado.setProcesso(processo);
			processoIniciado.setUsuario(this.getUsuarioLogado(httpServletRequest));

			ProcessoIniciadoDadoComplementarHelper helper = new ProcessoIniciadoDadoComplementarHelper();
			helper.adcionarDadoComplementar(DadoComplementarEnumerator.GRUPO_DESCRICAO, idFaturamentoGrupo);

			processoIniciado.setDescricaoDadosComplementares(helper.getStringFormatoPesistencia());

			List<Object> colecaoParametros = new ArrayList<Object>();
			colecaoParametros.add(idFaturamentoGrupo);
			colecaoParametros.add(anoBaseDeclaracaoQuitacaoDebitoAnual);

			Integer codigoProcessoIniciadoGerado = (Integer) fachada.inserirProcessoIniciadoOnline(processoIniciado, colecaoParametros);

			if(codigoProcessoIniciadoGerado > 0){
				// montando página de sucesso
				montarPaginaSucesso(httpServletRequest, "A Emissão Declaração Anual de Quitação de Débitos do Grupo "
								+ idFaturamentoGrupoStr + " foi enviada para processamento.", "Voltar",
								"exibirEmitirDeclaracaoAnualQuitacaoDebitosAction.do");
			}else{
				ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
								String.valueOf(Processo.EMITIR_DECLARACAO_ANUAL_QUITACAO_DEBITOS)
												+ " - EMITIR DECLARACAO ANUAL QUITACAO DEBITOS");

				throw actionServletException;
			}

			sessao.removeAttribute("colecaoFaturamentoGrupo");
		}else if(!Util.isVazioOuBranco(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdImovel())){

			Integer idFaturamentoGrupo = null;
			if(Util.verificarIdNaoVazio(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdFaturamentoGrupo())){

				idFaturamentoGrupo = Util.obterInteger(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdFaturamentoGrupo());
			}

			Integer anoBaseDeclaracaoQuitacaoDebitoAnual = null;

			if(!Util.isVazioOuBranco(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getAnoBaseDeclaracaoInformado())){

				anoBaseDeclaracaoQuitacaoDebitoAnual = Util.obterInteger(emitirDeclaracaoAnualQuitacaoDebitosActionForm
								.getAnoBaseDeclaracaoInformado());
			}

			if(anoBaseDeclaracaoQuitacaoDebitoAnual == null){

				try{
					anoBaseDeclaracaoQuitacaoDebitoAnual = Integer
									.valueOf((String) ParametroFaturamento.P_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL.executar());
				}catch(ControladorException e){
					throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL");
				}

				if(anoBaseDeclaracaoQuitacaoDebitoAnual == null
								|| anoBaseDeclaracaoQuitacaoDebitoAnual
												.equals(ConstantesSistema.SEM_ANO_BASE_DECLARACAO_QUITACAO_DEBITO_ANUAL)){

					throw new ActionServletException("atencao.required", null, "Ano Base da Declaração");
				}
			}

			RelatorioArquivoDeclaracaoAnualQuitacaoDebitos relatorioArquivoDeclaracaoAnualQuitacaoDebitos = fachada
							.emitirDeclaracaoAnualQuitacaoDebitos(idFaturamentoGrupo,
											Util.obterInteger(emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdImovel()),
											this.getUsuarioLogado(httpServletRequest), anoBaseDeclaracaoQuitacaoDebitoAnual);

			if(relatorioArquivoDeclaracaoAnualQuitacaoDebitos != null){

				relatorioArquivoDeclaracaoAnualQuitacaoDebitos.addParametro(RelatorioArquivoDeclaracaoAnualQuitacaoDebitos.P_IC_BATCH,
								ConstantesSistema.INATIVO);

				retorno = processarExibicaoRelatorio(relatorioArquivoDeclaracaoAnualQuitacaoDebitos, TarefaRelatorio.TIPO_PDF,
								httpServletRequest,
								httpServletResponse, actionMapping);
			}else{

				throw new ActionServletException("atencao.imovel_nao_possui_declaracao_quitacao_ano",
								new String[] {emitirDeclaracaoAnualQuitacaoDebitosActionForm.getIdImovel(), anoBaseDeclaracaoQuitacaoDebitoAnual
												.toString()});
			}
		}else{

			throw new ActionServletException("atencao.informe_grupo_faturamento_ou_matricula");
		}

		return retorno;
	}

}
