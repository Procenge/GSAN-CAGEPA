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
 */

package gcom.gui.gerencial.cobranca;

import gcom.fachada.Fachada;
import gcom.gerencial.cobranca.FiltroResumoCobrancaSituacaoEspecial;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaFinalHelper;
import gcom.gerencial.cobranca.ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class InformarResumoSituacaoEspecialCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarResumoSituacaoEspecialCobranca");

		InformarResumoSituacaoEspecialCobrancaActionForm informarResumoSituacaoEspecialCobrancaActionForm = (InformarResumoSituacaoEspecialCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		// Variavel para testar se o campo naum obrigatorio esta vazio

		Integer[] idsSituacaoTipo = informarResumoSituacaoEspecialCobrancaActionForm.getSituacaoTipo();
		Integer[] idsSituacaoMotivo = informarResumoSituacaoEspecialCobrancaActionForm.getSituacaoMotivo();

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

		FiltroResumoCobrancaSituacaoEspecial filtroResumoCobrancaSituacaoEspecial = new FiltroResumoCobrancaSituacaoEspecial();

		int i = 0;
		if(idsSituacaoTipo != null && idsSituacaoTipo.length > 0){
			while(i < idsSituacaoTipo.length){
				if(!idsSituacaoTipo[i].equals("")){
					if(i + 1 < idsSituacaoTipo.length){
						filtroResumoCobrancaSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoCobrancaSituacaoEspecial.COBRANCA_SITUCACAO_TIPO_ID, idsSituacaoTipo[i],
										ConectorOr.CONECTOR_OR));
					}else{
						filtroResumoCobrancaSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoCobrancaSituacaoEspecial.COBRANCA_SITUCACAO_TIPO_ID, idsSituacaoTipo[i]));
					}
				}
				i++;
			}
		}
		int j = 0;
		if(idsSituacaoMotivo != null && idsSituacaoMotivo.length > 0){
			while(j < idsSituacaoMotivo.length){
				if(!idsSituacaoMotivo[j].equals("")){
					if(j + 1 < idsSituacaoMotivo.length){
						filtroResumoCobrancaSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoCobrancaSituacaoEspecial.COBRANCA_SITUCACAO_MOTIVO_ID, idsSituacaoMotivo[j],
										ConectorOr.CONECTOR_OR));
					}else{
						filtroResumoCobrancaSituacaoEspecial.adicionarParametro(new ParametroSimples(
										FiltroResumoCobrancaSituacaoEspecial.COBRANCA_SITUCACAO_MOTIVO_ID, idsSituacaoMotivo[j]));
					}
				}
				j++;
			}
		}

		filtroResumoCobrancaSituacaoEspecial.setCampoOrderBy(FiltroResumoCobrancaSituacaoEspecial.GERENCIA_REGIONAL_ID);
		filtroResumoCobrancaSituacaoEspecial.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		filtroResumoCobrancaSituacaoEspecial.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacaoTipo");
		filtroResumoCobrancaSituacaoEspecial.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacaoMotivo");

		// Collection colecaoResumoCobSitEspecial = fachada.pesquisar(
		// filtroResumoCobrancaSituacaoEspecial,
		// ResumoCobrancaSituacaoEspecial.class.getName());

		Collection<ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper> colecaoRCSE = fachada.pesquisarResumoCobSitEspecial(
						idsSituacaoTipo, idsSituacaoMotivo);

		if(colecaoRCSE == null || colecaoRCSE.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		BigDecimal totalGeral = new BigDecimal("0");
		for(Iterator iter = colecaoRCSE.iterator(); iter.hasNext();){
			ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper = (ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper) iter
							.next();

			totalGeral = totalGeral.add(new BigDecimal(resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalGerenciaRegional()
							.toString()));
		}

		Integer totalQtLigacoesGeral = new Integer("0");
		for(Iterator iter = colecaoRCSE.iterator(); iter.hasNext();){
			ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper = (ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper) iter
							.next();

			totalQtLigacoesGeral = totalQtLigacoesGeral
							+ resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper.getTotalQtLigacoesGerencia();
		}

		BigDecimal totalPercentualGeral = new BigDecimal("0.00");

		BigDecimal p = new BigDecimal("100");
		if(totalGeral != null && totalQtLigacoesGeral != null && totalQtLigacoesGeral != 0){
			totalPercentualGeral = (totalGeral.multiply(p));
			totalPercentualGeral = totalPercentualGeral.divide(new BigDecimal(totalQtLigacoesGeral), 2, RoundingMode.HALF_UP);

		}

		BigDecimal totalFatEstimadoGeral = new BigDecimal("0.00");
		for(Iterator iter = colecaoRCSE.iterator(); iter.hasNext();){
			ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper = (ResumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper) iter
							.next();

			totalFatEstimadoGeral = totalFatEstimadoGeral.add(new BigDecimal(resumoCobrancaSituacaoEspecialConsultaGerenciaRegHelper
							.getTotalFatEstimadoGerencia().toString()));
		}

		sessao.setAttribute("totalGeral", totalGeral);
		sessao.setAttribute("totalQtLigacoesGeral", totalQtLigacoesGeral);
		sessao.setAttribute("totalPercentualGeral", totalPercentualGeral);
		sessao.setAttribute("totalFatEstimadoGeral", totalFatEstimadoGeral);

		sessao.setAttribute("colecaoRCSE", colecaoRCSE);

		ResumoCobrancaSituacaoEspecialConsultaFinalHelper resumoCobrancaSituacaoEspecialConsultaFinalHelper = new ResumoCobrancaSituacaoEspecialConsultaFinalHelper(
						colecaoRCSE, new Integer(totalGeral.intValue()), totalPercentualGeral, totalFatEstimadoGeral, totalQtLigacoesGeral);
		sessao.setAttribute("resumoCobrancaSituacaoEspecialConsultaFinalHelper", resumoCobrancaSituacaoEspecialConsultaFinalHelper);

		return retorno;
	}
}