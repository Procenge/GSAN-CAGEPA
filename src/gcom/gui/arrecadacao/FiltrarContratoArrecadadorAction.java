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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.FiltroArrecadadorContrato;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0536]FILTRAR ARRECADADOR
 * 
 * @author Marcio Roberto
 * @date 01/02/2007
 */

public class FiltrarContratoArrecadadorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterContratoArrecadador");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarContratoArrecadadorActionForm filtrarContratoArrecadadorActionForm = (FiltrarContratoArrecadadorActionForm) actionForm;

		// Recupera todos os campos da p�gina para ser colocada no filtro
		// posteriormente
		String idArrecadador = filtrarContratoArrecadadorActionForm.getIdArrecadador();
		String idCliente = filtrarContratoArrecadadorActionForm.getIdCliente();
		// String nomeCliente = filtrarContratoArrecadadorActionForm.getNomeCliente();
		String numeroContrato = filtrarContratoArrecadadorActionForm.getNumeroContrato();
		Integer idConcessionaria = filtrarContratoArrecadadorActionForm.getIdConcessionaria();

		// Indicador Atualizar
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){

			sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		boolean peloMenosUmParametroInformado = false;
		FiltroArrecadadorContrato filtroArrecadadorContrato = new FiltroArrecadadorContrato();

		filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("arrecadador.cliente");
		filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("contaBancariaDepositoArrecadacao");
		filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadadorContrato.CONCESSIONARIA);
		// filtroArrecadadorContrato.adicionarCaminhoParaCarregamentoEntidade("contaBancariaDepositoTarifa");

		// C�digo do Arrecadador
		if(idArrecadador != null && !idArrecadador.trim().equals("")){
			// [FS0003] - Verificando a exist�ncia do Agente
			boolean achou = fachada.verificarExistenciaArrecadador(new Integer(idArrecadador));
			if(achou){
				peloMenosUmParametroInformado = true;
				filtroArrecadadorContrato.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.ARRECADADOR_CODIGO_AGENTE,
								idArrecadador));
			}
		}

		// Numero do Contrato
		if(numeroContrato != null && !numeroContrato.trim().equals("")){
			boolean achou = fachada.verificarExistenciaContrato(new Integer(numeroContrato));
			if(achou){
				peloMenosUmParametroInformado = true;
				filtroArrecadadorContrato
								.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.NUMEROCONTRATO, numeroContrato));
			}
		}

		// Cliente
		if(idCliente != null && !idCliente.trim().equals("")){
			peloMenosUmParametroInformado = true;
			filtroArrecadadorContrato.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.CLIENTE_ID, idCliente));
		}

		// Concession�ria
		if(Util.isNaoNuloBrancoZero(idConcessionaria)
						&& !String.valueOf(idConcessionaria).equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			peloMenosUmParametroInformado = true;
			filtroArrecadadorContrato
							.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.ID_CONCESSIONARIA, idConcessionaria));
		}

		Collection<ArrecadadorContrato> colecaoArrecadadorContrato = fachada.pesquisar(filtroArrecadadorContrato, ArrecadadorContrato.class
						.getName());

		if(colecaoArrecadadorContrato == null || colecaoArrecadadorContrato.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Contrato de Arrecadador");
		}else{
			httpServletRequest.setAttribute("colecaoArrecadadorContrato", colecaoArrecadadorContrato);
			ArrecadadorContrato arrecadadorContrato = new ArrecadadorContrato();
			arrecadadorContrato = (ArrecadadorContrato) Util.retonarObjetoDeColecao(colecaoArrecadadorContrato);
			String idRegistroAtualizacao = arrecadadorContrato.getId().toString();
			sessao.setAttribute("idRegistroAtualizacao", idRegistroAtualizacao);
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroArrecadadorContrato", filtroArrecadadorContrato);

		httpServletRequest.setAttribute("filtroArrecadadorContrato", filtroArrecadadorContrato);

		return retorno;

	}
}
