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

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.Util;

/**
 * Realiza o filtro de boleto bancário de acordo com os parâmetros informados
 * 
 * @author Cinthya Cavalcanti
 * @created 04 de Outubro de 2011
 */
public class FiltrarBoletoBancarioAction
				extends GcomAction {

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
					HttpServletResponse httpServletResponse) throws PCGException{

		ActionForward retorno = actionMapping.findForward("exibirManterBoletoBancarioAction");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		FiltrarBoletoBancarioActionForm filtrarBoletoBancarioActionForm = (FiltrarBoletoBancarioActionForm) actionForm;

		// Recupera os parâmetros do form
		String idBanco = (String) filtrarBoletoBancarioActionForm.getBancoBoletoBancarioFiltro();
		String idImovel = (String) filtrarBoletoBancarioActionForm.getImovelBoletoBancarioFiltro();
		String idCliente = (String) filtrarBoletoBancarioActionForm.getClienteBoletoBancarioFiltro();
		String numeroBoleto = (String) filtrarBoletoBancarioActionForm.getNumeroBoletoBancarioFiltro();
		String numeroBoletoCartaCobrancaBoletoBancario = (String) filtrarBoletoBancarioActionForm
						.getNumeroBoletoCartaCobrancaBoletoBancarioFiltro();

		boolean pageOffset = httpServletRequest.getParameter("page.offset") == null;

		if(pageOffset && httpServletRequest.getParameter("idsSituacaoBoletoBancarioFiltro") == null){
			filtrarBoletoBancarioActionForm.setIdsSituacaoBoletoBancarioFiltro(null);
		}

		if(pageOffset && httpServletRequest.getParameter("idsTipoDocumentoBoletoBancarioFiltro") == null){
			filtrarBoletoBancarioActionForm.setIdsTipoDocumentoBoletoBancarioFiltro(null);
		}

		if(pageOffset && httpServletRequest.getParameter("idsMotivoCancelamentoBoletoBancarioFiltro") == null){
			filtrarBoletoBancarioActionForm.setIdsMotivoCancelamentoBoletoBancarioFiltro(null);
		}

		String[] idsSituacao = (String[]) filtrarBoletoBancarioActionForm.getIdsSituacaoBoletoBancarioFiltro();
		String[] idsTipoDocumento = (String[]) filtrarBoletoBancarioActionForm.getIdsTipoDocumentoBoletoBancarioFiltro();
		String[] idsMotivoCancelamento = (String[]) filtrarBoletoBancarioActionForm.getIdsMotivoCancelamentoBoletoBancarioFiltro();

		String dataInicialEntrada = (String) filtrarBoletoBancarioActionForm.getDataInicialEntradaBoletoBancarioFiltro();
		String dataFinalEntrada = (String) filtrarBoletoBancarioActionForm.getDataFinalEntradaBoletoBancarioFiltro();

		String dataInicialVencimento = (String) filtrarBoletoBancarioActionForm.getDataInicialVencimentoBoletoBancarioFiltro();
		String dataFinalVencimento = (String) filtrarBoletoBancarioActionForm.getDataFinalVencimentoBoletoBancarioFiltro();

		String dataInicialCredito = (String) filtrarBoletoBancarioActionForm.getDataInicialCreditoBoletoBancarioFiltro();
		String dataFinalCredito = (String) filtrarBoletoBancarioActionForm.getDataFinalCreditoBoletoBancarioFiltro();

		String dataInicialCancelamento = (String) filtrarBoletoBancarioActionForm.getDataInicialCancelamentoBoletoBancarioFiltro();
		String dataFinalCancelamento = (String) filtrarBoletoBancarioActionForm.getDataFinalCancelamentoBoletoBancarioFiltro();

		String dataInicialPagamento = (String) filtrarBoletoBancarioActionForm.getDataInicialPagamentoBoletoBancarioFiltro();
		String dataFinalPagamento = (String) filtrarBoletoBancarioActionForm.getDataFinalPagamentoBoletoBancarioFiltro();

		String indicadorTotalizarImovel = (String) filtrarBoletoBancarioActionForm.getIndicadorTotalizarImovel();

		String indicadorAtualizar = httpServletRequest.getParameter("atualizarFiltro");

		boolean isCamposPreenchidos = false;

		if(indicadorAtualizar != null && !indicadorAtualizar.equals("")){
			filtrarBoletoBancarioActionForm.setAtualizarFiltro("1");
		}else{
			filtrarBoletoBancarioActionForm.setAtualizarFiltro("");
		}

		BoletoBancarioHelper boletoBancarioHelper = new BoletoBancarioHelper();

		boolean desconsiderarParametros = false;
		boolean verificarNumeroBoletoCartaCobranca = false;

		// Insere os parâmetros informados no boletoBancarioHelper
		boolean idBancoNaoInformado = true;
		if(idBanco != null && !idBanco.trim().equalsIgnoreCase("")){

			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, idBanco));

			Collection colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());
			if(colecaoArrecadador != null && !colecaoArrecadador.isEmpty()){
				boletoBancarioHelper.setArrecadador((Arrecadador) colecaoArrecadador.iterator().next());
				idBancoNaoInformado = false;
			}else{
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Bancário");
			}
			isCamposPreenchidos = true;
		}

		if(numeroBoleto != null && !numeroBoleto.trim().equalsIgnoreCase("")){
			desconsiderarParametros = true;
			boletoBancarioHelper.setNumeroSequencial(Integer.parseInt(numeroBoleto));

			if(idBancoNaoInformado){
				throw new ActionServletException("atencao.required", null, "Arrecadador");
			}
			isCamposPreenchidos = true;
		}

		if(!desconsiderarParametros){

			if(numeroBoletoCartaCobrancaBoletoBancario != null && !numeroBoletoCartaCobrancaBoletoBancario.trim().equalsIgnoreCase("")){
				verificarNumeroBoletoCartaCobranca = true;

				boletoBancarioHelper.setNumeroSequencial(Integer.parseInt(numeroBoletoCartaCobrancaBoletoBancario));

				if(idBancoNaoInformado){
					throw new ActionServletException("atencao.required", null, "Arrecadador");
				}
				isCamposPreenchidos = true;
			}

			if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
				Imovel imovel = new Imovel();
				imovel.setId(Integer.parseInt(idImovel));
				boletoBancarioHelper.setImovel(imovel);

				isCamposPreenchidos = true;
			}

			if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
				Cliente cliente = new Cliente();
				cliente.setId(Integer.parseInt(idCliente));
				boletoBancarioHelper.setCliente(cliente);

				isCamposPreenchidos = true;
			}

			if(dataInicialEntrada != null && !dataInicialEntrada.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataInicialEntrada(Util.converterCampoStringParaData("", dataInicialEntrada,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;
			}

			if(dataFinalEntrada != null && !dataFinalEntrada.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataFinalEntrada(Util
								.converterCampoStringParaData("", dataFinalEntrada, Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;
			}

			if(dataInicialVencimento != null && !dataInicialVencimento.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataInicialVencimento(Util.converterCampoStringParaData("", dataInicialVencimento,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}

			if(dataFinalVencimento != null && !dataFinalVencimento.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataFinalVencimento(Util.converterCampoStringParaData("", dataFinalVencimento,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}

			if(dataInicialCredito != null && !dataInicialCredito.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataInicialCredito(Util.converterCampoStringParaData("", dataInicialCredito,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}

			if(dataFinalCredito != null && !dataFinalCredito.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataFinalCredito(Util
								.converterCampoStringParaData("", dataFinalCredito, Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}

			if(dataInicialCancelamento != null && !dataInicialCancelamento.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataInicialCancelamento(Util.converterCampoStringParaData("", dataInicialCancelamento,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}

			if(dataFinalCancelamento != null && !dataFinalCancelamento.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataFinalCancelamento(Util.converterCampoStringParaData("", dataFinalCancelamento,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}

			if(idsSituacao != null && idsSituacao.length > 0){
				if(!verificarCampoInvalido(idsSituacao)){
					boletoBancarioHelper.setIdsBoletoBancarioSituacao(idsSituacao);
					isCamposPreenchidos = true;
				}
			}

			if(idsTipoDocumento != null && idsTipoDocumento.length > 0){
				if(!verificarCampoInvalido(idsTipoDocumento)){
					boletoBancarioHelper.setIdsTipoDocumento(idsTipoDocumento);
					isCamposPreenchidos = true;
				}
			}

			if(idsMotivoCancelamento != null && idsMotivoCancelamento.length > 0){
				if(!verificarCampoInvalido(idsMotivoCancelamento)){
					boletoBancarioHelper.setIdsMotivoCancelamento(idsMotivoCancelamento);
					isCamposPreenchidos = true;
				}
			}

			if(dataInicialPagamento != null && !dataInicialPagamento.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataInicialPagamento(Util.converterCampoStringParaData("", dataInicialPagamento,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}

			if(dataFinalPagamento != null && !dataFinalPagamento.trim().equalsIgnoreCase("")){
				boletoBancarioHelper.setDataFinalPagamento(Util.converterCampoStringParaData("", dataFinalPagamento,
								Constantes.FORMATO_DATA_BR));

				isCamposPreenchidos = true;

			}
		}

		if(!isCamposPreenchidos){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("boletoBancarioHelper", boletoBancarioHelper);
		sessao.setAttribute("verificarNumeroBoletoCartaCobranca", verificarNumeroBoletoCartaCobranca);
		sessao.setAttribute("desconsiderarParametros", desconsiderarParametros);
		sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		sessao.setAttribute("indicadorTotalizarImovel", indicadorTotalizarImovel);

		sessao.removeAttribute("voltar");
		sessao.removeAttribute("codigoAgenteArrecadador");
		sessao.removeAttribute("idImovel");
		sessao.removeAttribute("idBoletoBancario");
		sessao.removeAttribute("voltarAtualizar");

		return retorno;
	}

	private boolean verificarCampoInvalido(String[] ids){

		boolean isCampoInvalido = false;
		for(int i = 0; i < ids.length; i++){
			if(ids[i].equals("-1")){
				isCampoInvalido = true;
				break;
			}
		}
		return isCampoInvalido;
	}
}
