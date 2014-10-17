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

package gcom.gui.arrecadacao.aviso;

import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.aviso.bean.AvisoBancarioHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 *[UC0268] Apresentar An�lise do Aviso Banc�rio
 * 
 * @author Raphael Rossiter
 * @date 23/03/2006
 */
public class ExibirApresentarAnaliseAvisoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirApresentarAnaliseAvisoBancario");

		ApresentarAnaliseAvisoBancarioActionForm apresentarAnaliseAvisoBancarioActionForm = (ApresentarAnaliseAvisoBancarioActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		String idAvisoBancario = httpServletRequest.getParameter("idAvisoBancario");

		String relatorioTipo = httpServletRequest.getParameter("relatorioTipo");

		if(relatorioTipo == null){
			apresentarAnaliseAvisoBancarioActionForm.setRelatorioTipo("3");
		}else{
			sessao.setAttribute("relatorioTipo", relatorioTipo);
		}

		String botao = httpServletRequest.getParameter("botao");

		if(botao != null && !botao.equalsIgnoreCase("") && botao.equalsIgnoreCase("sim")){
			sessao.removeAttribute("habilitarBotao");
		}

		if(idAvisoBancario == null || idAvisoBancario.equalsIgnoreCase("")){
			if(httpServletRequest.getAttribute("idAvisoBancario") == null){
				idAvisoBancario = (String) sessao.getAttribute("idAvisoBancario");
			}else{
				idAvisoBancario = (String) httpServletRequest.getAttribute("idAvisoBancario").toString();
			}

		}else{
			sessao.setAttribute("i", true);
		}

		apresentarAnaliseAvisoBancarioActionForm.setIdAvisoBancario(idAvisoBancario);
		sessao.setAttribute("idAvisoBancario", idAvisoBancario);

		sessao.removeAttribute("filtrar_manter");

		Fachada fachada = Fachada.getInstancia();

		// O sistema apresenta os dados do aviso banc�rio recebido
		AvisoBancario avisoBancario = new AvisoBancario();
		avisoBancario.setId(Integer.valueOf(idAvisoBancario));

		AvisoBancarioHelper avisoBancarioHelper = fachada.apresentarAnaliseAvisoBancario(avisoBancario);

		// Arrecadador
		apresentarAnaliseAvisoBancarioActionForm.setCodigoNomeArrecadador(avisoBancarioHelper.getCodigoNomeArrecadador());

		// Data de Lan�amento
		if(avisoBancarioHelper.getAvisoBancario().getDataLancamento() != null){
			apresentarAnaliseAvisoBancarioActionForm.setDataLancamento(Util.formatarData(avisoBancarioHelper.getAvisoBancario()
							.getDataLancamento()));
		}

		// Seq�encial do Aviso
		if(avisoBancarioHelper.getAvisoBancario().getNumeroSequencial() != null){
			apresentarAnaliseAvisoBancarioActionForm.setSequencial(avisoBancarioHelper.getAvisoBancario().getNumeroSequencial().toString());
		}

		// N�mero do Documento
		if(avisoBancarioHelper.getAvisoBancario().getNumeroDocumento() != 0){
			apresentarAnaliseAvisoBancarioActionForm.setNumeroDocumento(String.valueOf(avisoBancarioHelper.getAvisoBancario()
							.getNumeroDocumento()));
		}

		// Dados Aviso
		// Valor da Arrecada��o informado
		if(avisoBancarioHelper.getAvisoBancario().getValorArrecadacaoInformado() != null){
			apresentarAnaliseAvisoBancarioActionForm.setValorArrecadacaoInformado(Util.formatarMoedaReal(avisoBancarioHelper
							.getAvisoBancario().getValorArrecadacaoInformado()));
		}

		// Valor da Devolu��o informado
		if(avisoBancarioHelper.getAvisoBancario().getValorDevolucaoInformado() != null){
			apresentarAnaliseAvisoBancarioActionForm.setValorDevolucaoInformado(Util.formatarMoedaReal(avisoBancarioHelper
							.getAvisoBancario().getValorDevolucaoInformado()));
		}

		// Valor das Dedu��es
		if(avisoBancarioHelper.getValorSomatorioDeducoes() != null){

			apresentarAnaliseAvisoBancarioActionForm.setValorSomatorioDeducoes(Util.formatarMoedaReal(avisoBancarioHelper
							.getValorSomatorioDeducoes()));
		}

		// Valor do Aviso
		if(avisoBancarioHelper.getAvisoBancario().getValorRealizado() != null){
			apresentarAnaliseAvisoBancarioActionForm.setValorRealizado(Util.formatarMoedaReal(avisoBancarioHelper.getAvisoBancario()
							.getValorRealizado()));
		}

		// Tipo do Aviso
		apresentarAnaliseAvisoBancarioActionForm.setTipoAviso(avisoBancarioHelper.getTipoAviso());

		// Data Prevista do Cr�dito
		if(avisoBancarioHelper.getAvisoBancario().getDataPrevista() != null){
			apresentarAnaliseAvisoBancarioActionForm.setDataPrevistaCredito(Util.formatarData(avisoBancarioHelper.getAvisoBancario()
							.getDataPrevista()));
		}

		// Data Real do Cr�dito
		if(avisoBancarioHelper.getAvisoBancario().getDataRealizada() != null){
			apresentarAnaliseAvisoBancarioActionForm.setDataRealCredito(Util.formatarData(avisoBancarioHelper.getAvisoBancario()
							.getDataRealizada()));
		}

		// M�s/Ano de Refer�ncia da Arrecada��o
		if(avisoBancarioHelper.getAvisoBancario().getAnoMesReferenciaArrecadacao() != 0){
			apresentarAnaliseAvisoBancarioActionForm.setAnoMesArrecadacao(Util.formatarAnoMesParaMesAno(avisoBancarioHelper
							.getAvisoBancario().getAnoMesReferenciaArrecadacao()));
		}

		// Conta Banc�ria onde foi efetuado o dep�sito (Banco, Ag�ncia e Conta)
		apresentarAnaliseAvisoBancarioActionForm.setBancoContaBancaria("" + avisoBancarioHelper.getIdBancoContaBancaria());
		apresentarAnaliseAvisoBancarioActionForm.setAgenciaContaBancaria("" + avisoBancarioHelper.getCodigoAgenciaContaBancaria());
		apresentarAnaliseAvisoBancarioActionForm.setNumeroContaBancaria(avisoBancarioHelper.getNumeroContaBancaria());

		// Situa��o do Aviso (Baseado na Diferen�a Calculado no Item Diferen�a)
		apresentarAnaliseAvisoBancarioActionForm.setSituacao(avisoBancarioHelper.getSituacao());

		// Valores do Aviso

		// Valor Informado
		if(avisoBancarioHelper.getValorInformado() != null){

			apresentarAnaliseAvisoBancarioActionForm.setValorInformado(Util.formatarMoedaReal(avisoBancarioHelper.getValorInformado()));
		}

		// Valor Acertos
		if(avisoBancarioHelper.getValorAcertos() != null){

			apresentarAnaliseAvisoBancarioActionForm.setValorAcertos(Util.formatarMoedaReal(avisoBancarioHelper.getValorAcertos()));
		}

		// Valor Calculado
		if(avisoBancarioHelper.getValorCalculado() != null){

			apresentarAnaliseAvisoBancarioActionForm.setValorCalculado(Util.formatarMoedaReal(avisoBancarioHelper.getValorCalculado()));
		}

		// Valor Diferen�a
		if(avisoBancarioHelper.getValorDiferenca() != null){

			apresentarAnaliseAvisoBancarioActionForm.setValorDiferenca(Util.formatarMoedaReal(avisoBancarioHelper.getValorDiferenca()));
		}

		// Descri��o do Valor Totalde Pagamentos N�o Calssificados e Classificados
		apresentarAnaliseAvisoBancarioActionForm.setDescricaoValorTotalPagamentos("Valor Total dos Pagamentos N�o Classificados: "
						+ Util.formatarMoedaReal(avisoBancarioHelper.getValorPagamentosNaoClassificados(), 2) + "\n"
						+ "Valor Total dos Pagamentos Classificados: "
						+ Util.formatarMoedaReal(avisoBancarioHelper.getValorPagamentosClassificados(), 2));

		// Id do Arrecadador movimento (armv_id)
		if(avisoBancarioHelper.getIdMovimentoArrecadador() != null){

			httpServletRequest.setAttribute("idMovimentoArrecadador", avisoBancarioHelper.getIdMovimentoArrecadador());
		}

		// caso ainda n�o tenha sido setado o nome campo(na primeira vez)
		if(httpServletRequest.getParameter("manter") != null){

			sessao.setAttribute("manter", "manter");
		}

		sessao.setAttribute("avisoBancarioHelper", avisoBancarioHelper);

		return retorno;
	}
}