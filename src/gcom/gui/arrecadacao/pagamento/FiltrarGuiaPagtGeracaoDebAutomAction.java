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

package gcom.gui.arrecadacao.pagamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3046] Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico
 * 
 * @author Carlos Chrystian
 * @created 15/03/2012
 *          Filtrar Guia Pagamento Para Gera��o D�bito Autom�tico.
 */
public class FiltrarGuiaPagtGeracaoDebAutomAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirConsultaGuiaPagtGeracaoDebAutom");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		boolean peloMenosUmParametroInformado = false;

		// Recupera os campos que foram preenchidos no formul�rio
		ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm form = (ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm) actionForm;

		// Per�odo de Vencimento
		String dataVencimentoGuiaPagamentoInicial = form.getDataVencimentoGuiaPagamentoInicial();
		String dataVencimentoGuiaPagamentoFinal = form.getDataVencimentoGuiaPagamentoFinal();

		if(!Util.isVazioOuBranco(dataVencimentoGuiaPagamentoInicial) && !Util.isVazioOuBranco(dataVencimentoGuiaPagamentoFinal)){
			// [FS0002 � Verificar data final menor que data inicial].
			this.verificarDataFinalMenorDataInicial(form, httpServletRequest);
			peloMenosUmParametroInformado = true;
		}

		// Cliente Respons�vel
		String clienteResponsavel = form.getClienteResponsavel();
		if(!Util.isVazioOuBranco(clienteResponsavel)){
			// [FS0003 � Verificar exist�ncia do c�digo do cliente]
			this.verificarExistenciaCodigoClienteResponsavel(form, httpServletRequest);
			peloMenosUmParametroInformado = true;
		}

		// **** Verifica Situa��o das Guias ****

		// Indicador Guias Vencidas
		String indicadorTipoGuia = form.getIndicadorTipoGuia();
		if(!Util.isVazioOuBranco(indicadorTipoGuia)){
			peloMenosUmParametroInformado = true;
		}

		// [FS0005] � Nenhum registro encontrado
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// Indicador Atualizar
		String indicadorAtualizar = form.getIndicadorAtualizar();

		if(!Util.isVazioOuBranco(indicadorAtualizar) && indicadorAtualizar.equals("1")){

			sessao.setAttribute("indicadorAtualizar", "1");
		}else{

			sessao.removeAttribute("indicadorAtualizar");
		}

		return retorno;
	}

	/**
	 * [FS0002 � Verificar data final menor que data inicial].
	 * 
	 * @author Carlos Chrystian
	 * @created 15/03/2012
	 *          Verificar data final menor que data inicial.
	 */
	private void verificarDataFinalMenorDataInicial(ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm form, HttpServletRequest request){

		Date dataDataVencimentoGuiaPagamentoInicial = null;
		Date dataDataVencimentoGuiaPagamentoFinal = null;

		if(!Util.isVazioOuBranco(form.getDataVencimentoGuiaPagamentoInicial())){

			dataDataVencimentoGuiaPagamentoInicial = Util.converteStringParaDate(form.getDataVencimentoGuiaPagamentoInicial());
			dataDataVencimentoGuiaPagamentoInicial = Util.formatarDataInicial(dataDataVencimentoGuiaPagamentoInicial);

			if(!Util.isVazioOuBranco(form.getDataVencimentoGuiaPagamentoFinal())){
				dataDataVencimentoGuiaPagamentoFinal = Util.converteStringParaDate(form.getDataVencimentoGuiaPagamentoFinal());
				dataDataVencimentoGuiaPagamentoFinal = Util.adaptarDataFinalComparacaoBetween(dataDataVencimentoGuiaPagamentoFinal);
			}else{
				dataDataVencimentoGuiaPagamentoFinal = new Date();
				dataDataVencimentoGuiaPagamentoFinal = Util.formatarDataFinal(dataDataVencimentoGuiaPagamentoFinal);
			}

			// [FS0002 � Verificar data final menor que data inicial].
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataDataVencimentoGuiaPagamentoInicial,
							dataDataVencimentoGuiaPagamentoFinal);
			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
		}
	}

	/**
	 * [FS0003 � Verificar exist�ncia do c�digo do cliente]
	 * 
	 * @author Carlos Chrystian
	 * @created 15/03/2012
	 *          Verificar exist�ncia do c�digo do cliente.
	 */
	private void verificarExistenciaCodigoClienteResponsavel(ExibirFiltrarGuiaPagtGeracaoDebAutomActionForm form, HttpServletRequest request){

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, new Integer(form.getClienteResponsavel())));

		Collection colecaoCliente = Fachada.getInstancia().pesquisar(filtroCliente, Cliente.class.getName());

		// [FS0002 � Verificar exist�ncia do c�digo do cliente
		if(!Util.isVazioOrNulo(colecaoCliente)){

			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

			form.setClienteResponsavel(cliente.getId().toString());
			form.setNomeClienteResponsavel(cliente.getNome());
			request.setAttribute("codigoClienteResponsavelEncontrado", true);

		}else{

			form.setClienteResponsavel("");
			form.setNomeClienteResponsavel("Cliente inexistente");
		}
	}

}
