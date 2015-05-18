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

package gcom.gui.faturamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.GerarFaturaClienteResponsavelHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarContasParaFaturaClienteResponsavelAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirContasParaFaturaClienteResponsavel");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o formul�rio
		GerarFaturaClienteResponsavelActionForm gerarFaturaClienteResponsavelActionForm = (GerarFaturaClienteResponsavelActionForm) actionForm;

		// Recupera a vari�vel para indicar se o usu�rio apertou o bot�o de confirmar da tela de
		// confirma��o do wizard
		String confirmado = httpServletRequest.getParameter("confirmado");

		// Inicio - Transforma os campos em objetos
		Integer periodoReferenciaContasInicial = null;
		Integer periodoReferenciaContasFinal = null;
		Date periodoVencimentoContasInicial = null;
		Date periodoVencimentoContasFinal = null;
		Short indicadorContasRevisao = null;
		Collection<ContaMotivoRevisao> motivosRevisao = null;
		// Date dataVencimento = null;
		Collection<Cliente> clientes = null;

		// Per�odo de Refer�ncia das Contas
		if(!Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getPeriodoReferenciaContasInicial())){
			periodoReferenciaContasInicial = Util.formatarMesAnoComBarraParaAnoMes(gerarFaturaClienteResponsavelActionForm
							.getPeriodoReferenciaContasInicial());
		}

		if(!Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getPeriodoReferenciaContasFinal())){
			periodoReferenciaContasFinal = Util.formatarMesAnoComBarraParaAnoMes(gerarFaturaClienteResponsavelActionForm
							.getPeriodoReferenciaContasFinal());
		}

		// Per�odo de Vencimento das Contas
		if(!Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getPeriodoVencimentoContasInicial())){
			periodoVencimentoContasInicial = Util.converterStringParaData(gerarFaturaClienteResponsavelActionForm
							.getPeriodoVencimentoContasInicial());
		}

		if(!Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getPeriodoVencimentoContasFinal())){
			periodoVencimentoContasFinal = Util.converterStringParaData(gerarFaturaClienteResponsavelActionForm
							.getPeriodoVencimentoContasFinal());
		}

		// Indicador 'Selecionar apenas as contas em revis�o?'
		if(!Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getIndicadorContasRevisao())){

			indicadorContasRevisao = Short.valueOf(gerarFaturaClienteResponsavelActionForm.getIndicadorContasRevisao());

			// Motivos de Revis�o
			if(!Util.isVazioOrNulo(gerarFaturaClienteResponsavelActionForm.getMotivosRevisao())
							&& indicadorContasRevisao.equals(ConstantesSistema.CONTAS_EM_REVISAO)){

				motivosRevisao = new ArrayList<ContaMotivoRevisao>();
				FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao();
				Collection<Integer> contaMotivoRevisaoIds = new ArrayList<Integer>();
				for(String idMotivoRevisao : gerarFaturaClienteResponsavelActionForm.getMotivosRevisao()){
					if(!Util.isVazioOuBranco(idMotivoRevisao)){
						contaMotivoRevisaoIds.add(Integer.valueOf(idMotivoRevisao));
					}
				}

				if(!Util.isVazioOrNulo(contaMotivoRevisaoIds)){
					motivosRevisao = fachada.pesquisar(contaMotivoRevisaoIds, filtroContaMotivoRevisao, ContaMotivoRevisao.class.getName());
				}
			}
		}

		// Data de vencimento da fatura
		// if(!Util.isVazioOuBranco(gerarFaturaClienteResponsavelActionForm.getDataVencimento())){
		// dataVencimento =
		// Util.converterStringParaData(gerarFaturaClienteResponsavelActionForm.getDataVencimento());
		// }else{
		// throw new ActionServletException("atencao.data_vencimento.nao.informado");
		// }

		if(!Util.isVazioOrNulo(gerarFaturaClienteResponsavelActionForm.getClientesSelecionados())){
			FiltroCliente filtroCliente = new FiltroCliente();
			Collection<Integer> clienteIds = new ArrayList<Integer>();

			for(String clienteId : gerarFaturaClienteResponsavelActionForm.getClientesSelecionados()){
				clienteIds.add(Integer.valueOf(clienteId));
			}
			clientes = fachada.pesquisar(clienteIds, filtroCliente, Cliente.class.getName());

		}else{
			throw new ActionServletException("atencao.cliente.nao.selecionado");
		}
		// Fim - Transforma os campos em objetos

		// Inicio - [FS0005] � Validar data de vencimento
		// if(Util.compararData(dataVencimento, Util.converterStringParaData("01/01/1985")) == -1){
		// throw new ActionServletException("atencao.data_vencimento.inferior.1985");
		// }
		//
		// if(Util.compararData(dataVencimento, new Date()) == -1){
		// if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
		// return montarPaginaConfirmacao("atencao.data_vencimento.inferior.atual",
		// httpServletRequest, actionMapping);
		// }
		// }

		// String qtdDiasVencimento = null;
		// try{
		// qtdDiasVencimento = (String)
		// ParametroFaturamento.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA.executar();
		// }catch(ControladorException e){
		// throw new ActionServletException(e.getMessage(), e);
		// }

		// if(Util.compararData(dataVencimento, Util.adicionarNumeroDiasDeUmaData(new Date(),
		// Util.obterInteger(qtdDiasVencimento).intValue())) > 0){
		// if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
		// return montarPaginaConfirmacao("atencao.data_vencimento.posterior.X_dias",
		// httpServletRequest, actionMapping,
		// qtdDiasVencimento);
		// }
		// }
		// Fim - [FS0005] � Validar data de vencimento

		GerarFaturaClienteResponsavelHelper gerarFaturaClienteResponsavelHelper = new GerarFaturaClienteResponsavelHelper(
						periodoReferenciaContasInicial, periodoReferenciaContasFinal, periodoVencimentoContasInicial,
						periodoVencimentoContasFinal, indicadorContasRevisao, motivosRevisao, null);

		// Consulta as contas para gera��o da fatura para cliente respons�vel
		Map<Cliente, Collection<Conta>> mapClienteContas = fachada.consultarContasParaGerarFaturaClienteResponsavel(clientes,
						gerarFaturaClienteResponsavelHelper);

		Collection<Cliente> colecaoClientesOrdenada = fachada.ordernarColecaoClientesPorNome(mapClienteContas.keySet());

		httpServletRequest.setAttribute("mapClienteContas", mapClienteContas);
		httpServletRequest.setAttribute("colecaoClientesOrdenada", colecaoClientesOrdenada);
		sessao.setAttribute("gerarFaturaClienteResponsavelHelper", gerarFaturaClienteResponsavelHelper);

		return retorno;

	}

}