
package gcom.gui.cobranca.ajustetarifa;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.ajustetarifa.AjusteTarifa;
import gcom.cobranca.ajustetarifa.AjusteTarifaConta;
import gcom.cobranca.ajustetarifa.FiltroAjusteTarifa;
import gcom.cobranca.ajustetarifa.FiltroAjusteTarifaConta;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.DadosHistoricoCREDTACHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * [UC3155] Consultar Histórico CREDTAC (Sorocaba)
 * 
 * @author Anderson Italo
 * @date 20/09/2014
 */
public class ExibirDadosHistoricoCREDTACAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirDadosHistoricoCREDTAC");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		ExibirDadosHistoricoCREDTACActionForm form = (ExibirDadosHistoricoCREDTACActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String idImovelAjusteTarifaStr = (String) sessao.getAttribute("idImovelAjusteTarifa");

		FiltroAjusteTarifa filtroAjusteTarifa = new FiltroAjusteTarifa();

		filtroAjusteTarifa
						.adicionarParametro(new ParametroSimples(FiltroAjusteTarifa.IMOVEL_ID, Util
							.obterInteger(idImovelAjusteTarifaStr)));
		filtroAjusteTarifa.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifa.IMOVEL_LOCALIDADE);
		filtroAjusteTarifa.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifa.IMOVEL_SETOR_COMERCIAL);
		filtroAjusteTarifa.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifa.IMOVEL_QUADRA);
		filtroAjusteTarifa.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifa.CONSUMO_TARIFA);
		filtroAjusteTarifa.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifa.CATEGORIA);
		filtroAjusteTarifa.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifa.LIGACAO_AGUA_SITUACAO);
		filtroAjusteTarifa.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifa.LIGACAO_ESGOTO_SITUACAO);

		Collection<AjusteTarifa> colecaoAjusteTarifa = fachada.pesquisar(filtroAjusteTarifa, AjusteTarifa.class.getName());

		if(Util.isVazioOrNulo(colecaoAjusteTarifa)){

			ActionServletException actionServletException = new ActionServletException("atencao.pesquisa.nenhumresultado");
			actionServletException.setUrlBotaoVoltar("/gsan/exibirConsultarHistoricoCREDTACAction.do");

			throw actionServletException;
		}else{

			form.limpar();

			sessao.removeAttribute("colecaoDadosHistoricoCREDTACHelper");

			Imovel imovel = ((AjusteTarifa) Util.retonarObjetoDeColecao(colecaoAjusteTarifa)).getImovel();

			// Imóvel
			form.setIdImovel(imovel.getId().toString());

			// Dados do Imóvel
			// Inscrição
			String inscricaoFormatadaImovel = imovel.getInscricaoFormatada();
			form.setInscricaoFormatadaImovel(inscricaoFormatadaImovel);

			// Endereço do Imóvel
			String enderecoImovel = fachada.pesquisarEndereco(imovel.getId());
			form.setEnderecoImovel(enderecoImovel);

			Collection<DadosHistoricoCREDTACHelper> colecaoDadosHistoricoCREDTACHelper = new ArrayList<DadosHistoricoCREDTACHelper>();

			for(AjusteTarifa ajusteTarifa : colecaoAjusteTarifa){

				DadosHistoricoCREDTACHelper dadosHistoricoCREDTACHelper = new DadosHistoricoCREDTACHelper();

				// Situação da Ligação de Água
				LigacaoAguaSituacao ligacaoAguaSituacao = ajusteTarifa.getLigacaoAguaSituacao();

				if(ligacaoAguaSituacao != null){

					String descricaoLigacaoAguaSituacao = ligacaoAguaSituacao.getDescricao();
					dadosHistoricoCREDTACHelper.setDescricaoLigacaoAguaSituacao(descricaoLigacaoAguaSituacao);
				}

				// Situação da Ligação de Esgoto
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = ajusteTarifa.getLigacaoEsgotoSituacao();

				if(ligacaoEsgotoSituacao != null){

					String descricaoLigacaoEsgotoSituacao = ligacaoEsgotoSituacao.getDescricao();
					dadosHistoricoCREDTACHelper.setDescricaoLigacaoEsgotoSituacao(descricaoLigacaoEsgotoSituacao);
				}

				// Descrição da Categoria
				if(ajusteTarifa.getCategoria() != null){

					dadosHistoricoCREDTACHelper.setDescricaoCategoria(ajusteTarifa.getCategoria().getDescricao());
				}

				// Tarifa de Consumo
				if(ajusteTarifa.getConsumoTarifa() != null){

					dadosHistoricoCREDTACHelper.setTarifaConsumo(ajusteTarifa.getConsumoTarifa().getDescricao());
				}

				// Dados do recálculo

				// Data do cálculo
				if(ajusteTarifa.getDataCalculo() != null){

					dadosHistoricoCREDTACHelper.setDataCalculo(Util.formatarData(ajusteTarifa.getDataCalculo()));
				}

				// Número de parcelas
				if(ajusteTarifa.getNumeroParcelas() != null){

					dadosHistoricoCREDTACHelper.setNumeroParcelas(ajusteTarifa.getNumeroParcelas().toString());
				}

				// Descrição do processamento
				if(ajusteTarifa.getDescricaoProcessado() != null){

					dadosHistoricoCREDTACHelper.setDescricaoProcessamento(ajusteTarifa.getDescricaoProcessado());
				}

				// Total Anterior
				if(ajusteTarifa.getValorAnterior() != null){

					dadosHistoricoCREDTACHelper.setValorAnterior(Util.formatarMoedaReal(ajusteTarifa.getValorAnterior(), 2));
				}

				// Total Atual
				if(ajusteTarifa.getValorAtual() != null){

					dadosHistoricoCREDTACHelper.setValorAtual(Util.formatarMoedaReal(ajusteTarifa.getValorAtual(), 2));
				}

				// Total do Crédito
				if(ajusteTarifa.getValorCredito() != null){

					dadosHistoricoCREDTACHelper.setValorCredito(Util.formatarMoedaReal(ajusteTarifa.getValorCredito(), 2));
				}

				// Saldo
				if(ajusteTarifa.getValorSaldo() != null){

					dadosHistoricoCREDTACHelper.setValorSaldo(Util.formatarMoedaReal(ajusteTarifa.getValorSaldo(), 2));
				}

				// Residual
				if(ajusteTarifa.getValorResidual() != null){

					dadosHistoricoCREDTACHelper.setValorResidual(Util.formatarMoedaReal(ajusteTarifa.getValorResidual(), 2));
				}

				// Utilizado
				if(ajusteTarifa.getValorUtilizado() != null){

					dadosHistoricoCREDTACHelper.setValorUtilizado(Util.formatarMoedaReal(ajusteTarifa.getValorUtilizado(), 2));
				}

				// Relação das contas recalculadas
				FiltroAjusteTarifaConta filtroAjusteTarifaConta = new FiltroAjusteTarifaConta();
				filtroAjusteTarifaConta.adicionarParametro(new ParametroSimples(FiltroAjusteTarifaConta.AJUSTE_TARIFA_ID, ajusteTarifa
								.getId()));
				filtroAjusteTarifaConta
								.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifaConta.AJUSTE_TARIFA_CONTA_CONTA_GERAL_CONTA);
				filtroAjusteTarifaConta
								.adicionarCaminhoParaCarregamentoEntidade(FiltroAjusteTarifaConta.AJUSTE_TARIFA_CONTA_CONTA_GERAL_CONTA_HIST);
				filtroAjusteTarifaConta.setCampoOrderByDesc(FiltroAjusteTarifaConta.AJUSTE_TARIFA_CONTA_CONTA_GERAL_ID);

				Collection<AjusteTarifaConta> colecaoAjusteTarifaConta = fachada.pesquisar(filtroAjusteTarifaConta,
								AjusteTarifaConta.class.getName());

				if(!Util.isVazioOrNulo(colecaoAjusteTarifaConta)){

					dadosHistoricoCREDTACHelper.setColecaoAjusteTarifaConta(colecaoAjusteTarifaConta);
				}

				dadosHistoricoCREDTACHelper.setAjusteTarifa(ajusteTarifa);

				colecaoDadosHistoricoCREDTACHelper.add(dadosHistoricoCREDTACHelper);
			}

			sessao.setAttribute("colecaoDadosHistoricoCREDTACHelper", colecaoDadosHistoricoCREDTACHelper);
		}

		return retorno;

	}
}
