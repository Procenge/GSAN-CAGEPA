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

package gcom.gui.gerencial.faturamento;

import gcom.fachada.Fachada;
import gcom.gerencial.faturamento.FiltroResumoFaturamentoSituacaoEspecial;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaFinalHelper;
import gcom.gerencial.faturamento.ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class InformarResumoSituacaoEspecialFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarResumoSituacaoEspecialFaturamento");

		InformarResumoSituacaoEspecialFaturamentoActionForm informarResumoSituacaoEspecialFaturamentoActionForm = (InformarResumoSituacaoEspecialFaturamentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		// Variavel para testar se o campo naum obrigatorio esta vazio

		Integer[] idsSituacaoTipo = informarResumoSituacaoEspecialFaturamentoActionForm.getSituacaoTipo();
		Integer[] idsSituacaoMotivo = informarResumoSituacaoEspecialFaturamentoActionForm.getSituacaoMotivo();

		if(sessao.getAttribute("totalGeral") != null){
			sessao.removeAttribute("totalGeral");
		}

		if(sessao.getAttribute("totalQtLigacoesGeral") != null){
			sessao.removeAttribute("totalQtLigacoesGeral");
		}

		if(sessao.getAttribute("totalPercentualGeral") != null){
			sessao.removeAttribute("totalPercentualGeral");
		}

		if(sessao.getAttribute("totalFatEstimadoGeral") != null){
			sessao.removeAttribute("totalFatEstimadoGeral");
		}

		FiltroResumoFaturamentoSituacaoEspecial filtroResumoFaturamentoSituacaoEspecial = new FiltroResumoFaturamentoSituacaoEspecial();

		// recupera os ids de situacao tipo e ja gera o filtro.
		int i = 0;
		if(idsSituacaoTipo != null){
			while(i < idsSituacaoTipo.length){
				if(!idsSituacaoTipo[i].equals("")){
					if(i + 1 < idsSituacaoTipo.length){
						filtroResumoFaturamentoSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoFaturamentoSituacaoEspecial.FATURAMENTO_SITUCACAO_TIPO_ID, idsSituacaoTipo[i],
										ConectorOr.CONECTOR_OR));
					}else{
						filtroResumoFaturamentoSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoFaturamentoSituacaoEspecial.FATURAMENTO_SITUCACAO_TIPO_ID, idsSituacaoTipo[i]));
					}
				}
				i++;
			}
		}
		// recupera os ids de situacao motivo e ja gera o filtro.
		int j = 0;
		if(idsSituacaoMotivo != null){
			while(j < idsSituacaoMotivo.length){
				if(!idsSituacaoMotivo[j].equals("")){
					if(j + 1 < idsSituacaoMotivo.length){
						filtroResumoFaturamentoSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoFaturamentoSituacaoEspecial.FATURAMENTO_SITUCACAO_MOTIVO_ID, idsSituacaoMotivo[j],
										ConectorOr.CONECTOR_OR));
					}else{
						filtroResumoFaturamentoSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoFaturamentoSituacaoEspecial.FATURAMENTO_SITUCACAO_MOTIVO_ID, idsSituacaoMotivo[j]));
					}
				}
				j++;
			}
		}

		filtroResumoFaturamentoSituacaoEspecial.setCampoOrderBy(FiltroResumoFaturamentoSituacaoEspecial.GERENCIA_REGIONAL_ID);
		filtroResumoFaturamentoSituacaoEspecial.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		filtroResumoFaturamentoSituacaoEspecial.adicionarCaminhoParaCarregamentoEntidade("faturamentoSituacaoTipo");
		filtroResumoFaturamentoSituacaoEspecial.adicionarCaminhoParaCarregamentoEntidade("faturamentoSituacaoMotivo");

		// Collection colecaoResumoFatSitEspecial = fachada.pesquisar(
		// filtroResumoFaturamentoSituacaoEspecial,
		// ResumoFaturamentoSituacaoEspecial.class.getName());

		Collection<ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper> colecaoRFSE = fachada.pesquisarResumoFatSitEspecial(
						idsSituacaoTipo, idsSituacaoMotivo);

		if(colecaoRFSE == null || colecaoRFSE.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		BigDecimal totalGeral = new BigDecimal("0.00");
		for(Iterator iter = colecaoRFSE.iterator(); iter.hasNext();){
			ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper = (ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper) iter
							.next();

			totalGeral = totalGeral.add(new BigDecimal(resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper
							.getTotalGerenciaRegional().toString()));
		}

		Integer totalQtLigacoesGeral = new Integer("0");
		for(Iterator iter = colecaoRFSE.iterator(); iter.hasNext();){
			ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper = (ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper) iter
							.next();

			totalQtLigacoesGeral = totalQtLigacoesGeral
							+ resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper.getTotalQtLigacoesGerencia();
		}

		BigDecimal totalPercentualGeral = new BigDecimal("0.00");

		BigDecimal percentual = new BigDecimal("100");
		if(totalGeral != null && totalQtLigacoesGeral != null && totalQtLigacoesGeral != 0){
			totalPercentualGeral = (totalGeral.multiply(percentual));

			totalPercentualGeral = totalPercentualGeral.divide(new BigDecimal(totalQtLigacoesGeral), 2, RoundingMode.HALF_UP);
		}

		BigDecimal totalFatEstimadoGeral = new BigDecimal("0.00");
		for(Iterator iter = colecaoRFSE.iterator(); iter.hasNext();){
			ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper = (ResumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper) iter
							.next();

			totalFatEstimadoGeral = totalFatEstimadoGeral.add(new BigDecimal(resumoFaturamentoSituacaoEspecialConsultaGerenciaRegHelper
							.getTotalFatEstimadoGerencia().toString()));
		}

		sessao.setAttribute("totalGeral", totalGeral);
		sessao.setAttribute("totalQtLigacoesGeral", totalQtLigacoesGeral);
		sessao.setAttribute("totalPercentualGeral", totalPercentualGeral);
		sessao.setAttribute("totalFatEstimadoGeral", Util.formatarMoedaReal(totalFatEstimadoGeral));

		sessao.setAttribute("colecaoRFSE", colecaoRFSE);

		ResumoFaturamentoSituacaoEspecialConsultaFinalHelper resumoFaturamentoSituacaoEspecialConsultaFinalHelper = new ResumoFaturamentoSituacaoEspecialConsultaFinalHelper(
						colecaoRFSE, new Integer(totalGeral.intValue()), totalPercentualGeral, totalFatEstimadoGeral, totalQtLigacoesGeral);
		sessao.setAttribute("resumoFaturamentoSituacaoEspecialConsultaFinalHelper", resumoFaturamentoSituacaoEspecialConsultaFinalHelper);

		return retorno;
	}
}