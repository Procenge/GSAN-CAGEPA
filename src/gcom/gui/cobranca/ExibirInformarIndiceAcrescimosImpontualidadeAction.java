
package gcom.gui.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.FiltroIndicesAcrescimosImpontualidade;
import gcom.cobranca.IndicesAcrescimosImpontualidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre- processamento para informar indices de acrescimos por impontualidade.
 * 
 * @author S�vio Luiz
 * @date 26/09/2007
 */
public class ExibirInformarIndiceAcrescimosImpontualidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirAcaoCobranca");

		try{
			if(ParametroCobranca.P_EXIBIR_CALCULAR_FATOR_CORRECAO.executar().equals(ConstantesSistema.SIM.toString())){
				sessao.setAttribute("exibirCalcularFatorCorrecao", true);
			}
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		Fachada fachada = Fachada.getInstancia();

		IndiceAcrescimosImpontualidadeForm indiceAcrescimosImpontualidadeForm = (IndiceAcrescimosImpontualidadeForm) actionForm;
		if(httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").equals("")){
			indiceAcrescimosImpontualidadeForm.setMesAnoReferencia("");
			indiceAcrescimosImpontualidadeForm.setPercentualMulta("");
			indiceAcrescimosImpontualidadeForm.setPercentualJurosMora("");
			indiceAcrescimosImpontualidadeForm.setPercentualAtualizacaoMonetaria("");
			indiceAcrescimosImpontualidadeForm.setFatorCorrecao("");
			indiceAcrescimosImpontualidadeForm.setCamposDesabilitados("");

		}

		if(httpServletRequest.getParameter("calcular") != null && httpServletRequest.getParameter("calcular").equals("sim")){

			calcularFatorAtualizacaoMonetaria(indiceAcrescimosImpontualidadeForm, httpServletRequest, fachada);

		}else{

			// pesquisa os dados do enter
			pesquisarEnter(indiceAcrescimosImpontualidadeForm, httpServletRequest, fachada);
		}

		return retorno;
	}

	private void pesquisarEnter(IndiceAcrescimosImpontualidadeForm indiceAcrescimosImpontualidadeForm,
					HttpServletRequest httpServletRequest, Fachada fachada){

		// pesquisa enter de crit�rio de cobran�a
		if(indiceAcrescimosImpontualidadeForm.getMesAnoReferencia() != null
						&& !indiceAcrescimosImpontualidadeForm.getMesAnoReferencia().equals("")){

			FiltroIndicesAcrescimosImpontualidade filtroIndicesAcrescimosImpontualidade = new FiltroIndicesAcrescimosImpontualidade();
			Integer anoMesReferencia = null;
			try{
				anoMesReferencia = Util.formatarMesAnoComBarraParaAnoMes(indiceAcrescimosImpontualidadeForm.getMesAnoReferencia());

				// [FS0002] - Validar m�s e ano de refer�ncia
				Integer anoMesReferenciaMaximo = fachada.pesquisarMaximoAnoMesIndicesAcerscimosImpontualidade();
				Integer anoMesReferenciaMaisUm = Util.somaMesAnoMesReferencia(anoMesReferenciaMaximo, 1);
				if(anoMesReferencia > anoMesReferenciaMaisUm){
					throw new ActionServletException("atencao.mes_ano_referencia_maior", null, "" + anoMesReferenciaMaisUm);
				}
				filtroIndicesAcrescimosImpontualidade.adicionarParametro(new ParametroSimples(
								FiltroIndicesAcrescimosImpontualidade.ANO_MES_REFERENCIA, anoMesReferencia));
			}catch(NumberFormatException ex){
				throw new ActionServletException("atencao.campo_texto.numero_obrigatorio", null, "Mes/Ano Refer�ncia");
			}

			Collection colecaoIndicesAcrescimosImpontualidade = fachada.pesquisar(filtroIndicesAcrescimosImpontualidade,
							IndicesAcrescimosImpontualidade.class.getName());

			if(colecaoIndicesAcrescimosImpontualidade != null && !colecaoIndicesAcrescimosImpontualidade.isEmpty()){
				IndicesAcrescimosImpontualidade indicesAcrescimosImpontualidade = (IndicesAcrescimosImpontualidade) Util
								.retonarObjetoDeColecao(colecaoIndicesAcrescimosImpontualidade);
				indiceAcrescimosImpontualidadeForm.setPercentualMulta(Util.formatarMoedaReal(indicesAcrescimosImpontualidade
								.getPercentualMulta()));
				indiceAcrescimosImpontualidadeForm.setPercentualJurosMora(Util.formatarMoedaReal(indicesAcrescimosImpontualidade
								.getPercentualJurosMora()));
				indiceAcrescimosImpontualidadeForm.setFatorCorrecao(Util.formatarMoedaReal(indicesAcrescimosImpontualidade
								.getFatorAtualizacaoMonetaria()));
				indiceAcrescimosImpontualidadeForm.setPercentualAtualizacaoMonetaria("");
				httpServletRequest.setAttribute("nomeCampo", "percentualAtualizacaoMonetaria");

				SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

				// caso o ano mes de referencia for menor ou igual ao ano mes de
				// arrecada��o n�o deixa alterar os dados
				if(anoMesReferencia <= sistemaParametro.getAnoMesArrecadacao()){
					indiceAcrescimosImpontualidadeForm.setCamposDesabilitados("sim");
				}
			}else{
				httpServletRequest.setAttribute("nomeCampo", "percentualMulta");
			}

		}

	}

	private void calcularFatorAtualizacaoMonetaria(IndiceAcrescimosImpontualidadeForm indiceAcrescimosImpontualidadeForm,
					HttpServletRequest httpServletRequest, Fachada fachada){

		// pesquisa enter de crit�rio de cobran�a
		if(indiceAcrescimosImpontualidadeForm.getMesAnoReferencia() != null
						&& !indiceAcrescimosImpontualidadeForm.getMesAnoReferencia().equals("")){

			if(indiceAcrescimosImpontualidadeForm.getPercentualAtualizacaoMonetaria() != null){
				BigDecimal percentualAtualizacaoMonetaria = Util.formatarMoedaRealparaBigDecimal(indiceAcrescimosImpontualidadeForm
								.getPercentualAtualizacaoMonetaria());
				if(percentualAtualizacaoMonetaria.compareTo(new BigDecimal("0.00")) <= 0
								|| percentualAtualizacaoMonetaria.compareTo(new BigDecimal("100.00")) > 0){
					throw new ActionServletException("atencao.percentual_invalido", null, "Fator Atualiza��o Monet�ria");
				}
			}

			FiltroIndicesAcrescimosImpontualidade filtroIndicesAcrescimosImpontualidade = new FiltroIndicesAcrescimosImpontualidade();
			Integer anoMesReferencia = null;
			try{
				anoMesReferencia = Util.formatarMesAnoComBarraParaAnoMes(indiceAcrescimosImpontualidadeForm.getMesAnoReferencia());

				Integer anoMesReferenciaMenosUm = Util.subtraiAteSeisMesesAnoMesReferencia(anoMesReferencia, 1);

				filtroIndicesAcrescimosImpontualidade.adicionarParametro(new ParametroSimples(
								FiltroIndicesAcrescimosImpontualidade.ANO_MES_REFERENCIA, anoMesReferenciaMenosUm));
			}catch(NumberFormatException ex){
				throw new ActionServletException("atencao.campo_texto.numero_obrigatorio", null, "Mes/Ano Refer�ncia");
			}

			Collection colecaoIndicesAcrescimosImpontualidadeAnterior = fachada.pesquisar(filtroIndicesAcrescimosImpontualidade,
							IndicesAcrescimosImpontualidade.class.getName());

			if(colecaoIndicesAcrescimosImpontualidadeAnterior != null && !colecaoIndicesAcrescimosImpontualidadeAnterior.isEmpty()){
				IndicesAcrescimosImpontualidade indicesAcrescimosImpontualidade = (IndicesAcrescimosImpontualidade) Util
								.retonarObjetoDeColecao(colecaoIndicesAcrescimosImpontualidadeAnterior);

				if(indiceAcrescimosImpontualidadeForm.getPercentualAtualizacaoMonetaria() != null
								&& !indiceAcrescimosImpontualidadeForm.getPercentualAtualizacaoMonetaria().equals("")){

					BigDecimal percentualAtualizacaoMonetaria = Util.formatarMoedaRealparaBigDecimal(indiceAcrescimosImpontualidadeForm
									.getPercentualAtualizacaoMonetaria());

					BigDecimal fatorAtualizacao = indicesAcrescimosImpontualidade.getFatorAtualizacaoMonetaria().multiply(
									percentualAtualizacaoMonetaria);

					BigDecimal valorPrecentual = fatorAtualizacao.divide(new BigDecimal("100"));

					valorPrecentual = valorPrecentual.add(indicesAcrescimosImpontualidade.getFatorAtualizacaoMonetaria());

					valorPrecentual = valorPrecentual.setScale(4, BigDecimal.ROUND_HALF_UP);

					String valorPercentualString = "" + valorPrecentual;

					valorPercentualString = valorPercentualString.replace(".", ",");

					indiceAcrescimosImpontualidadeForm.setFatorCorrecao(valorPercentualString);
					httpServletRequest.setAttribute("nomeCampo", "botaoInformar");
				}else{
					throw new ActionServletException("atencao.campo_texto.numero_obrigatorio", null,
									"Percentual Fator Atualiza��o Monet�ria");
				}

			}
		}else{
			httpServletRequest.setAttribute("nomeCampo", "percentualAtualizacaoMonetaria");
		}

	}

}
