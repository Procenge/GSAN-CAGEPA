/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.cobranca;

import gcom.cobranca.CobrancaCriterioLinha;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Processamento para atualizar a linha do criterio da cobrança
 * 
 * @author Sávio Luiz
 * @date 05/06/2006
 *       Retirada dos campos 'Situação de Ligação de Água' e 'Campo Situação de Ligação de Esgoto'.
 * @author Virgínia Melo
 * @date 29/07/2009
 */
public class AtualizarCriterioCobrancaLinhaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("atualizarCriterioCobrancaLinha");
		CriterioCobrancaActionForm criterioCobrancaActionForm = (CriterioCobrancaActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		CobrancaCriterioLinha cobrancaCriterioLinha = (CobrancaCriterioLinha) sessao.getAttribute("cobrancaCriteriolinha");

		// atualiza cobranca criterio linha para ser exibido na tela de inserir ou atualizar

		// Novos campos adicionados - Campo Situação Medição
		Short vlSituacaoMedicao = null;
		String situacaoMedicao = criterioCobrancaActionForm.getSituacaoMedicao();
		if(situacaoMedicao != null && !situacaoMedicao.equals("") && !situacaoMedicao.equals("-1")){
			vlSituacaoMedicao = Short.valueOf(situacaoMedicao);
		}

		// Campo Situação de Ligação de Água
		/*
		 * String situacaoLigacaoAguaID = criterioCobrancaActionForm.getStLigacaoAguaID();
		 * if (situacaoLigacaoAguaID != null && !situacaoLigacaoAguaID.equals("")) {
		 * LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
		 * ligacaoAguaSituacao.setId(Integer.parseInt(situacaoLigacaoAguaID));
		 * cobrancaCriterioLinha.setLigacaoAguaSituacao(ligacaoAguaSituacao);
		 * }
		 */

		// Campo Situação de Ligação de Esgoto
		/*
		 * String situacaoLigacaoEsgotoID = criterioCobrancaActionForm.getStLigacaoEsgotoID();
		 * if (situacaoLigacaoEsgotoID != null && !situacaoLigacaoEsgotoID.equals("")) {
		 * LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
		 * ligacaoEsgotoSituacao.setId(Integer.parseInt(situacaoLigacaoEsgotoID));
		 * cobrancaCriterioLinha.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
		 * }
		 */

		// Campo Número de Dias para data de vencimento (obrigatório)
		String diasParaVencimento = criterioCobrancaActionForm.getDiasParaVencimento();
		if(diasParaVencimento != null && !diasParaVencimento.equals("")){
			cobrancaCriterioLinha.setDiasParaVencimento(Integer.parseInt(diasParaVencimento));
		}else{
			throw new ActionServletException("atencao.required", null, "Nº de Dias para Vencimento");
		}

		// Campo Data de Ligação Limite
		Date dataLimite = null;
		String strDataLimite = criterioCobrancaActionForm.getDataLimite();
		if(strDataLimite != null && !strDataLimite.equals("")){

			// [FS0008]
			if(Util.validarDiaMesAno(strDataLimite)){
				throw new ActionServletException("atencao.data.inicio.invalida");
			}
			dataLimite = Util.converteStringParaDate(strDataLimite);
		}

		String referenciaDebitoInicial = criterioCobrancaActionForm.getReferenciaDebitoInicial();
		String referenciaDebitoFinal = criterioCobrancaActionForm.getReferenciaDebitoFinal();

		// [FS0009] – Validar referenciaDebitoInicial
		if(referenciaDebitoInicial != null && !referenciaDebitoInicial.equals("")){

			if(!Util.validarMesAno(referenciaDebitoInicial)){
				throw new ActionServletException("atencao.periodoReferencia.invalido");
			}

			// Invertendo o formato para yyyyMM (sem a barra)
			referenciaDebitoInicial = Util.formatarMesAnoParaAnoMesSemBarra(referenciaDebitoInicial);
			cobrancaCriterioLinha.setReferenciaDebitoInicial(Integer.parseInt(referenciaDebitoInicial));

		}else{
			cobrancaCriterioLinha.setReferenciaDebitoInicial(null);
		}

		// [FS0009] – Validar referenciaDebitoFinal
		if(referenciaDebitoFinal != null && !referenciaDebitoFinal.equals("")){

			if(!Util.validarMesAno(referenciaDebitoFinal) || !Util.validarMesAno(referenciaDebitoFinal)){
				throw new ActionServletException("atencao.periodoReferencia.invalido");
			}

			// [FS0010] – Verificar referência final menor que referência inicial
			referenciaDebitoFinal = Util.formatarMesAnoParaAnoMesSemBarra(referenciaDebitoFinal);
			if(referenciaDebitoInicial != null && !referenciaDebitoInicial.equals("")){
				boolean referenciaFinalMenorInicial = Util.compararAnoMesReferencia(Integer.valueOf(referenciaDebitoFinal), Integer
								.valueOf(referenciaDebitoInicial), "<");

				if(referenciaFinalMenorInicial){
					throw new ActionServletException("atencao.periodo_ref_final.anterior.periodo_ref_inicial");
				}

			}
			cobrancaCriterioLinha.setReferenciaDebitoFinal(Integer.parseInt(referenciaDebitoFinal));
		}else{
			cobrancaCriterioLinha.setReferenciaDebitoFinal(null);
		}

		// verifica se o valor maximo é menor que o mínimo
		BigDecimal valorDebitoMinimo = null;
		BigDecimal valorDebitoMaximo = null;

		if(criterioCobrancaActionForm.getValorDebitoMinimo() != null && !criterioCobrancaActionForm.getValorDebitoMinimo().equals("")){
			valorDebitoMinimo = Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm.getValorDebitoMinimo());
		}
		if(criterioCobrancaActionForm.getValorDebitoMaximo() != null && !criterioCobrancaActionForm.getValorDebitoMaximo().equals("")){
			valorDebitoMaximo = Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm.getValorDebitoMaximo());
		}
		if(valorDebitoMinimo != null && valorDebitoMaximo != null){
			if(valorDebitoMinimo.compareTo(valorDebitoMaximo) == 1){
				throw new ActionServletException("atencao.valor.maximo.debito.menor.valor.minimo.debito");
			}
		}

		Short qtdContasMinima = null;
		if(criterioCobrancaActionForm.getQtdContasMinima() != null && !criterioCobrancaActionForm.getQtdContasMinima().equals("")){
			qtdContasMinima = Short.valueOf(criterioCobrancaActionForm.getQtdContasMinima());
		}
		Integer qtdContasMaxima = null;
		if(criterioCobrancaActionForm.getQtdContasMaxima() != null && !criterioCobrancaActionForm.getQtdContasMaxima().equals("")){
			qtdContasMaxima = Integer.valueOf(criterioCobrancaActionForm.getQtdContasMaxima());
		}
		if(qtdContasMinima != null && qtdContasMaxima != null){
			if(qtdContasMinima > qtdContasMaxima){
				throw new ActionServletException("atencao.quantidade.maxima.contas.menor.quantidade.minima.contas");
			}
		}

		if(criterioCobrancaActionForm.getVlMinimoDebitoCliente() != null
						&& !criterioCobrancaActionForm.getVlMinimoDebitoCliente().equals("")){
			cobrancaCriterioLinha.setValorMinimoDebitoDebitoAutomatico(Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm
							.getVlMinimoDebitoCliente()));
		}else{
			cobrancaCriterioLinha.setValorMinimoDebitoDebitoAutomatico(null);
		}

		Short qtdMinContasCliente = null;
		if(criterioCobrancaActionForm.getQtdMinContasCliente() != null && !criterioCobrancaActionForm.getQtdMinContasCliente().equals("")){
			qtdMinContasCliente = Short.valueOf(criterioCobrancaActionForm.getQtdMinContasCliente());
		}

		Integer qtdDiasVencimentosInicial = null;
		if(criterioCobrancaActionForm.getQtdDiasVencMinima() != null && !criterioCobrancaActionForm.getQtdDiasVencMinima().equals("")){
			qtdDiasVencimentosInicial = Integer.valueOf(criterioCobrancaActionForm.getQtdDiasVencMinima());
		}

		Integer qtdDiasVencimentosFinal = null;
		if(criterioCobrancaActionForm.getQtdDiasVencMaxima() != null && !criterioCobrancaActionForm.getQtdDiasVencMaxima().equals("")){
			qtdDiasVencimentosFinal = Integer.valueOf(criterioCobrancaActionForm.getQtdDiasVencMaxima());
		}

		if(qtdDiasVencimentosInicial != null && qtdDiasVencimentosFinal != null){
			if(qtdDiasVencimentosInicial > qtdDiasVencimentosFinal){
				throw new ActionServletException("atencao.quantidade.maxima.dias.vencimento.menor.quantidade.minima.dias.vencimento");
			}
		}

		if(criterioCobrancaActionForm.getVlMinimoContasMes() != null && !criterioCobrancaActionForm.getVlMinimoContasMes().equals("")){
			cobrancaCriterioLinha.setValorMinimoContaMes(Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm
							.getVlMinimoContasMes()));
		}else{
			cobrancaCriterioLinha.setValorMinimoContaMes(null);
		}

		if(criterioCobrancaActionForm.getQuantidadeMinimaParcelasAtraso() != null
						&& !criterioCobrancaActionForm.getQuantidadeMinimaParcelasAtraso().equals("")){
			cobrancaCriterioLinha.setQuantidadeMinimaContasParcelamento(Short.valueOf(criterioCobrancaActionForm
							.getQuantidadeMinimaParcelasAtraso()));

		}else{
			cobrancaCriterioLinha.setQuantidadeMinimaContasParcelamento(Short.valueOf("0"));
		}

		// Atribuindo valores alterados
		cobrancaCriterioLinha.setSituacaoMedicao(vlSituacaoMedicao);
		cobrancaCriterioLinha.setDataLimite(dataLimite);
		cobrancaCriterioLinha.setValorMinimoDebito(valorDebitoMinimo);
		cobrancaCriterioLinha.setValorMaximoDebito(valorDebitoMaximo);
		cobrancaCriterioLinha.setQuantidadeMinimaContas(qtdContasMinima);
		cobrancaCriterioLinha.setQuantidadeMaximaContas(qtdContasMaxima);
		cobrancaCriterioLinha.setQuantidadeMinimaContasDebitoAutomatico(qtdMinContasCliente);
		cobrancaCriterioLinha.setQuantidadeDiasVencimentoInicial(qtdDiasVencimentosInicial);
		cobrancaCriterioLinha.setQuantidadeDiasVencimentoFinal(qtdDiasVencimentosFinal);
		cobrancaCriterioLinha.setUltimaAlteracao(new Date());

		httpServletRequest.setAttribute("fechaPopup", "true");
		sessao.removeAttribute("cobrancaCriteriolinha");

		return retorno;
	}
}
