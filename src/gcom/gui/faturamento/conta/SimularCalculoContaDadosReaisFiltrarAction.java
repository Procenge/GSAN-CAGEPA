
package gcom.gui.faturamento.conta;

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

import gcom.faturamento.bean.FiltroContaSimularCalculoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3156] Simular C�lculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 22/09/2014
 */
public class SimularCalculoContaDadosReaisFiltrarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("simularCalculoContaDadosReaisFiltrar");
		SimularCalculoContaDadosReaisFiltrarActionForm formulario = (SimularCalculoContaDadosReaisFiltrarActionForm) actionForm;
		boolean parametroInformado = false;
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroContaSimularCalculoHelper filtroContaSimularCalculoHelper = new FiltroContaSimularCalculoHelper();

		// Per�odo de Refer�ncia do Faturamento
		if(!Util.isVazioOuBranco(formulario.getPeriodoReferenciaFaturamentoInicial())
						&& !Util.isVazioOuBranco(formulario.getPeriodoReferenciaFaturamentoFinal())){

			filtroContaSimularCalculoHelper.setPeriodoReferenciaFaturamentoInicial(Util.obterInteger(Util
							.formatarMesAnoParaAnoMes(formulario.getPeriodoReferenciaFaturamentoInicial())));
			
			filtroContaSimularCalculoHelper.setPeriodoReferenciaFaturamentoFinal(Util.obterInteger(Util.formatarMesAnoParaAnoMes(formulario
							.getPeriodoReferenciaFaturamentoFinal())));
		}

		// Imovel
		if(!Util.isVazioOuBranco(formulario.getIdImovel())){

			filtroContaSimularCalculoHelper.setIdImovel(Util.obterInteger(formulario.getIdImovel()));
			parametroInformado = true;
		}
		
		// Situa��o da Liga��o de �gua
		if(!Util.isVazioOuBranco(formulario.getIdLigacaoAguaSituacao())){

			filtroContaSimularCalculoHelper.setIdLigacaoAguaSituacao(Util.obterInteger(formulario.getIdLigacaoAguaSituacao()));
		}

		// Situa��o da Liga��o de Esgoto
		if(!Util.isVazioOuBranco(formulario.getIdLigacaoEsgotoSituacao())){

			filtroContaSimularCalculoHelper.setIdLigacaoEsgotoSituacao(Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()));
		}

		// Tarifa de Consumo
		if(!Util.isVazioOuBranco(formulario.getIdConsumoTarifa())){

			filtroContaSimularCalculoHelper.setIdConsumoTarifa(Util.obterInteger(formulario.getIdConsumoTarifa()));
		}

		// Grupo de Faturamento
		if(!Util.isVazioOuBranco(formulario.getIdFaturamentoGrupo())){

			filtroContaSimularCalculoHelper.setIdFaturamentoGrupo(Util.obterInteger(formulario.getIdFaturamentoGrupo()));
			parametroInformado = true;
		}

		// Categeoria(s)
		if(!Util.isVazioOrNulo(formulario.getIdCategoria())){

			String idsCategorias = "";
			for(int i = 0; i < formulario.getIdCategoria().length; i++){

				if(Util.isInteger(formulario.getIdCategoria()[i])){

					idsCategorias += formulario.getIdCategoria()[i] + ",";
				}
			}
			
			if(!Util.isVazioOuBranco(idsCategorias)){

				filtroContaSimularCalculoHelper.setIdsCategorias(idsCategorias.substring(0, idsCategorias.length() - 1));
			}
		}

		// [FS0013 - Verificar preenchimento dos campos]
		if(!parametroInformado){

			ActionServletException actionServletException = new ActionServletException(
							"atencao.verificar_preenchimento_algum_filtro_selecao_contas_recalcular");
			actionServletException.setUrlBotaoVoltar("/gsan/exibirSimularCalculoContaDadosReaisFiltrarAction.do");

			throw actionServletException;
		}else{

			sessao.setAttribute("filtroContaSimularCalculoHelper", filtroContaSimularCalculoHelper);
		}

		return retorno;
	}
}

