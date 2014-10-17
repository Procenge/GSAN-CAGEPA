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
 * GSANPCG
 * Eduardo Henrique
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

import gcom.cadastro.cliente.*;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterGuiaPagamentoAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @author eduardo henrique
	 * @date 08/08/2008
	 *       Altera��es contidas no [UC0188] para v0.04
	 * @author Saulo Lima
	 * @date 26/01/2009
	 *       Altera��o para pegar o cliente usu�rio do imovel que tenha dataFimRelacao igual a nulo
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterGuiaPagamento");

		ManterGuiaPagamentoActionForm manterGuiaPagamentoActionForm = (ManterGuiaPagamentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// -------Parte que trata do c�digo quando o usu�rio tecla enter

		// C�digo do Cliente
		String codigoDigitadoClienteEnter = null;

		if(httpServletRequest.getParameter("idCliente") != null && !httpServletRequest.getParameter("idCliente").equals("")){
			codigoDigitadoClienteEnter = httpServletRequest.getParameter("idCliente");
			manterGuiaPagamentoActionForm.setCodigoCliente(codigoDigitadoClienteEnter);
		}else{
			codigoDigitadoClienteEnter = (String) manterGuiaPagamentoActionForm.getCodigoCliente();
		}

		// Matr�cula do Im�vel
		String codigoDigitadoImovelEnter = null;

		if(httpServletRequest.getParameter("idImovel") != null && !httpServletRequest.getParameter("idImovel").equals("")){
			codigoDigitadoImovelEnter = httpServletRequest.getParameter("idImovel");
			manterGuiaPagamentoActionForm.setIdImovel(codigoDigitadoImovelEnter);
		}else{
			codigoDigitadoImovelEnter = (String) manterGuiaPagamentoActionForm.getIdImovel();
		}

		Collection guiasPagamentos = null;

		// Verifica se o c�digo do im�vel foi digitado
		if(codigoDigitadoImovelEnter != null && !codigoDigitadoImovelEnter.trim().equals("")
						&& Integer.parseInt(codigoDigitadoImovelEnter) > 0){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoDigitadoImovelEnter));

			Collection imovelEncontrado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(imovelEncontrado != null && !imovelEncontrado.isEmpty()){
				// O imovel foi encontrado
				Imovel imovel = (Imovel) imovelEncontrado.iterator().next();

				FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

				filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
				filtroImovelCobrancaSituacao
								.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel.getId()));

				Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class
								.getName());

				if(imovel.getIndicadorExclusao() != null && imovel.getIndicadorExclusao().equals(Imovel.IMOVEL_EXCLUIDO)){
					throw new ActionServletException("atencao.imovel.excluido");
				}

				// Verifica se o im�vel tem d�bito em cobran�a administrativa
				if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){

					ImovelCobrancaSituacao imovelCobrancaSituacao = new ImovelCobrancaSituacao();

					sessao.setAttribute("imovelCobrancaSituacao", imovelCobrancaSituacao);

					if(imovelCobrancaSituacao.getCobrancaSituacao() != null){

						if(imovelCobrancaSituacao.getCobrancaSituacao().getId().equals(CobrancaSituacao.COBRANCA_ADMINISTRATIVA)
										&& imovelCobrancaSituacao.getDataRetiradaCobranca() == null){
							// C�digo comentado para a customiza��o da cobran�a administrativa CASAL
							// throw new
							// ActionServletException("atencao.pesquisa.imovel.cobranca_administrativa");
						}
					}
				}

				manterGuiaPagamentoActionForm.setIdImovel("" + ((Imovel) ((List) imovelEncontrado).get(0)).getId());
				manterGuiaPagamentoActionForm.setInscricaoImovel("" + ((Imovel) ((List) imovelEncontrado).get(0)).getLocalidade().getId()
								+ "." + ((Imovel) ((List) imovelEncontrado).get(0)).getSetorComercial().getCodigo() + "."
								+ ((Imovel) ((List) imovelEncontrado).get(0)).getQuadra().getId() + "."
								+ ((Imovel) ((List) imovelEncontrado).get(0)).getLote() + "."
								+ ((Imovel) ((List) imovelEncontrado).get(0)).getSubLote());
				manterGuiaPagamentoActionForm.setSituacaoAgua(((Imovel) ((List) imovelEncontrado).get(0)).getLigacaoAguaSituacao()
								.getDescricao());
				manterGuiaPagamentoActionForm.setSituacaoEsgoto(((Imovel) ((List) imovelEncontrado).get(0)).getLigacaoEsgotoSituacao()
								.getDescricao());

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("clienteRelacaoTipo");

				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, codigoDigitadoImovelEnter));
				Collection clientesImovelEncontrado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
				ClienteImovel clienteImovel = null;

				if(clientesImovelEncontrado != null && !clientesImovelEncontrado.isEmpty()){
					// O cliente imovel foi encontrado

					Iterator clienteImovelEncontradoIterator = clientesImovelEncontrado.iterator();

					while(clienteImovelEncontradoIterator.hasNext()){
						clienteImovel = (ClienteImovel) clienteImovelEncontradoIterator.next();

						if(clienteImovel.getClienteRelacaoTipo().getId().intValue() == ClienteRelacaoTipo.USUARIO.intValue()
										&& clienteImovel.getDataFimRelacao() == null){

							break;
						}
					}
					manterGuiaPagamentoActionForm.setNomeClienteUsuario(clienteImovel.getCliente().getNome());
				}

				guiasPagamentos = fachada.pesquisarGuiasPagamentoPrestacaoImovelOuCliente(imovel.getId(), null);

				if(guiasPagamentos == null || guiasPagamentos.isEmpty()){
					throw new ActionServletException("atencao.guia_pagamento.imovel.inexistente", null, "" + imovel.getId());
				}

			}else{
				manterGuiaPagamentoActionForm.setIdImovel("");
				throw new ActionServletException("atencao.pesquisa.imovel.inexistente.guia");

			}
		}

		// Verifica se o do cliente c�digo foi digitado
		if(codigoDigitadoClienteEnter != null && !codigoDigitadoClienteEnter.trim().equals("")
						&& Integer.parseInt(codigoDigitadoClienteEnter) > 0){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("profissao");
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("ramoAtividade");

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, codigoDigitadoClienteEnter));

			Collection clienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(clienteEncontrado != null && !clienteEncontrado.isEmpty()){
				// O Cliente foi encontrado
				if(((Cliente) ((List) clienteEncontrado).get(0)).getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
					throw new ActionServletException("atencao.cliente.inativo", null, ""
									+ ((Cliente) ((List) clienteEncontrado).get(0)).getId());
				}

				manterGuiaPagamentoActionForm.setNomeCliente(((Cliente) ((List) clienteEncontrado).get(0)).getNome());

				if(((Cliente) ((List) clienteEncontrado).get(0)).getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(
								ClienteTipo.INDICADOR_PESSOA_FISICA)){
					manterGuiaPagamentoActionForm.setCpf(((Cliente) ((List) clienteEncontrado).get(0)).getCpfFormatado());
					manterGuiaPagamentoActionForm.setProfissao(((Cliente) ((List) clienteEncontrado).get(0)).getProfissao() == null ? ""
									: ((Cliente) ((List) clienteEncontrado).get(0)).getProfissao().getDescricao());

				}else{
					manterGuiaPagamentoActionForm.setCpf(((Cliente) ((List) clienteEncontrado).get(0)).getCnpjFormatado());
					manterGuiaPagamentoActionForm
									.setRamoAtividade(((Cliente) ((List) clienteEncontrado).get(0)).getRamoAtividade() == null ? ""
													: ((Cliente) ((List) clienteEncontrado).get(0)).getRamoAtividade().getDescricao());
				}

				/*
				 * filtroGuiaPagamento.adicionarParametro(new ParametroSimples(
				 * FiltroGuiaPagamento.CLIENTE_ID,
				 * ((Cliente) ((List) clienteEncontrado).get(0)).getId()));
				 * filtroGuiaPagamento.adicionarParametro(new ParametroSimples(
				 * FiltroGuiaPagamento.DEBITO_CREDITO_SITUACAO_ATUAL_ID,
				 * DebitoCreditoSituacao.NORMAL));
				 */
				guiasPagamentos = fachada.pesquisarGuiasPagamentoPrestacaoImovelOuCliente(null, ((Cliente) ((List) clienteEncontrado)
								.get(0)).getId());

				if(guiasPagamentos == null || guiasPagamentos.isEmpty()){
					manterGuiaPagamentoActionForm.setCodigoCliente("");
					throw new ActionServletException("atencao.guia_pagamento.cliente.inexistente", null, ""
									+ ((Cliente) ((List) clienteEncontrado).get(0)).getId());

				}
			}else{
				throw new ActionServletException("atencao.pesquisa.cliente.inexistente.guia");
			}

		}

		// Realiza um tratamento na Cole��o retornada, pois a exibi��o ser� por Guia e Presta��o
		// (somando todos os tipos de D�bito de cada Presta��o)
		// Ser� utilizado como "id" do helper, o HashCode do Nr. da Guia + Nr. da Prestacao. Esse
		// ser� o identificador para saber-se que Guia/Presta��o dever�
		// ser Cancelado ou Impresso.
		if(guiasPagamentos != null && !guiasPagamentos.isEmpty()){
			Map mapPrestacoesGuiaPagamento = new TreeMap();

			for(Iterator iterator = guiasPagamentos.iterator(); iterator.hasNext();){
				GuiaPagamentoPrestacaoHelper prestacaoHelper = (GuiaPagamentoPrestacaoHelper) iterator.next();

				prestacaoHelper.setId(Long.valueOf(prestacaoHelper.getIdGuiaPagamento().toString()
								+ prestacaoHelper.getNumeroPrestacao().toString()));

				if(!mapPrestacoesGuiaPagamento.containsKey(prestacaoHelper.getId().toString())){
					prestacaoHelper.setValorTotalPorPrestacao(prestacaoHelper.getValorPrestacaoTipoDebito());

					mapPrestacoesGuiaPagamento.put(prestacaoHelper.getId().toString(), prestacaoHelper);
				}else{
					GuiaPagamentoPrestacaoHelper prestacaoHelperMap = (GuiaPagamentoPrestacaoHelper) mapPrestacoesGuiaPagamento
									.get(prestacaoHelper.getId().toString());

					prestacaoHelperMap.setValorTotalPorPrestacao(prestacaoHelperMap.getValorTotalPorPrestacao().add(
									prestacaoHelper.getValorPrestacaoTipoDebito()));
				}

			}
			// Necess�rio para ordernar a cole��o
			guiasPagamentos = mapPrestacoesGuiaPagamento.values();
		}

		sessao.setAttribute("guiasPagamentos", guiasPagamentos);

		return retorno;
	}
}
