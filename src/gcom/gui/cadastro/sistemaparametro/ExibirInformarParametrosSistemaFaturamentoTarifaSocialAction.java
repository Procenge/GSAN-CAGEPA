/**
 * 
 */
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
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
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

		// obtém a instância da sessão
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