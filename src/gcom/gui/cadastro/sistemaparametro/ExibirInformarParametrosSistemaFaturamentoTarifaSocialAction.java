/**
 * 
 */
/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.cadastro.sistemaparametro;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.util.ConstantesAplicacao;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 08/01/2007
 */
public class ExibirInformarParametrosSistemaFaturamentoTarifaSocialAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirInformarParametrosSistemaFaturamentoTarifaSocial");

		// obt�m a inst�ncia da sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		InformarSistemaParametrosActionForm form = (InformarSistemaParametrosActionForm) actionForm;

		// Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");

		Integer cont;

		// Verifica se ja entrou nesse action, caso nao coloca no form os dados
		// do objeto sistemaParametro

		// if(sessao.getAttribute("FaturamentoTarifaSocial") == null){
		cont = 1;
		sessao.setAttribute("FaturamentoTarifaSocial", cont);

		String anoMesFaturamento = Util.formatarAnoMesParaMesAno(sistemaParametro.getAnoMesFaturamento().toString());
		form.setMesAnoReferencia("" + anoMesFaturamento);

		form.setMenorConsumo("" + sistemaParametro.getMenorConsumoGrandeUsuario());

		if(sistemaParametro.getValorMinimoEmissaoConta() != null){

			String valorAux = Util.formatarMoedaReal(sistemaParametro.getValorMinimoEmissaoConta());

			form.setMenorValor("" + valorAux);
		}

		form.setQtdeEconomias("" + sistemaParametro.getMenorEconomiasGrandeUsuario());
		form.setIndicadorClienteAtualFatura("" + sistemaParametro.getIndicadorClienteAtualFatura());

		form.setMesesCalculoMedio("" + sistemaParametro.getMesesMediaConsumo());

		form.setDiasVencimentoCobranca("" + sistemaParametro.getNumeroMinimoDiasEmissaoVencimento());

		form.setDiasMinimoVencimento("" + sistemaParametro.getNumeroMinimoDiasEmissaoVencimento());

		form.setDiasMinimoVencimentoCorreio("" + sistemaParametro.getNumeroDiasAdicionaisCorreios());

		form.setNumeroMesesValidadeConta("" + sistemaParametro.getNumeroMesesValidadeConta());

		form.setNumeroMesesAlteracaoVencimento("" + sistemaParametro.getNumeroMesesMinimoAlteracaoVencimento());

		if(sistemaParametro.getValorSalarioMinimo() != null){

			String valorAux = Util.formatarMoedaReal(sistemaParametro.getValorSalarioMinimo());

			form.setSalarioMinimo("" + valorAux);

		}

		form.setAreaMaxima("" + sistemaParametro.getAreaMaximaTarifaSocial());

		form.setConsumoMaximo("" + sistemaParametro.getConsumoEnergiaMaximoTarifaSocial());

		if(sistemaParametro.getTituloPagina() == null){
			form.setTitulosRelatorio("");
		}else{
			form.setTitulosRelatorio("" + sistemaParametro.getTituloPagina());
		}

		if(sistemaParametro.getNumeroMaximoTiposDebitoEmissaoDocumento() != null){
			form.setNumeroMaximoTiposDebitoEmissaoDocumento("" + sistemaParametro.getNumeroMaximoTiposDebitoEmissaoDocumento());
		}

		if(sistemaParametro.getNumeroDiasEsperaExtratoDebito() != null){
			form.setNumeroDiasEsperaExtratoDebito("" + sistemaParametro.getNumeroDiasEsperaExtratoDebito());
		}else{
			form.setNumeroDiasEsperaExtratoDebito("");
		}

		if(sistemaParametro.getIndicadorClienteAtualFatura() != null){
			form.setIndicadorClienteAtualFatura("" + sistemaParametro.getIndicadorClienteAtualFatura());
		}else{
			form.setIndicadorClienteAtualFatura("");
		}

		if(sistemaParametro.getAnoReferenciaDebitoConta() != null){
			form.setAnoReferenciaDebitoConta("" + sistemaParametro.getAnoReferenciaDebitoConta());
		}else{
			form.setAnoReferenciaDebitoConta("");
		}

		if(sistemaParametro.getNumeroMinDebitosAguaParaTodos() != null){
			form.setNumeroMinDebitosAguaParaTodos("" + sistemaParametro.getNumeroMinDebitosAguaParaTodos());
		}else{
			form.setNumeroMinDebitosAguaParaTodos("");
		}

		if(sistemaParametro.getCodMotivoExclusaoAguaParaTodos() != null){
			form.setCodMotivoExclusaoAguaParaTodos("" + sistemaParametro.getCodMotivoExclusaoAguaParaTodos());
		}else{
			form.setCodMotivoExclusaoAguaParaTodos("");
		}

		if(sistemaParametro.getNumeroConsumoMinAguaParaTodos() != null){
			form.setNumeroConsumoMinAguaParaTodos("" + sistemaParametro.getNumeroConsumoMinAguaParaTodos());
		}else{
			form.setNumeroConsumoMinAguaParaTodos("");
		}

		if(sistemaParametro.getNumeroConsumoExcedidoAguaParaTodos() != null){
			form.setNumeroConsumoExcedidoAguaParaTodos("" + sistemaParametro.getNumeroConsumoExcedidoAguaParaTodos());
		}else{
			form.setNumeroConsumoExcedidoAguaParaTodos("");
		}

		if(sistemaParametro.getCodMotExclusaoConsumoAguaParaTodos() != null){
			form.setCodMotExclusaoConsumoAguaParaTodos("" + sistemaParametro.getCodMotExclusaoConsumoAguaParaTodos());
		}else{
			form.setCodMotExclusaoConsumoAguaParaTodos("");
		}

		if(sistemaParametro.getCodTarifaAguaParaTodos() != null){
			form.setCodTarifaAguaParaTodos("" + sistemaParametro.getCodTarifaAguaParaTodos());
		}else{
			form.setCodTarifaAguaParaTodos("");
		}

		if(sistemaParametro.getNumeroMaxDiasVigenciaTarifaAguaParaTodos() != null){
			form.setNumeroMaxDiasVigenciaTarifaAguaParaTodos("" + sistemaParametro.getNumeroMaxDiasVigenciaTarifaAguaParaTodos());
		}else{
			form.setNumeroMaxDiasVigenciaTarifaAguaParaTodos("");
		}

		String numeroDiasEsperaMenor = ConstantesAplicacao.get("sistema_parametro.numero_dias_espera.menor");
		String numeroDiasEsperaMaior = ConstantesAplicacao.get("sistema_parametro.numero_dias_espera.maior");
		form.setNumeroDiasEsperaMenor(numeroDiasEsperaMenor);
		form.setNumeroDiasEsperaMaior(numeroDiasEsperaMaior);
		// }
		return retorno;
	}
}