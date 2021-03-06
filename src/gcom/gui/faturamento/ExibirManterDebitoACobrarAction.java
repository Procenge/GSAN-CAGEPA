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

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoCreditoSituacao;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.Menor;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manter D�bito a Cobrar ao Imovel [UC0184] Manter D�bito a Cobrar
 * 
 * @author Rafael Santos
 * @since 29/12/2005
 * @author Saulo Lima
 * @date 26/01/2009
 *       Altera��o para pegar o cliente usu�rio do imovel que tenha dataFimRelacao igual a nulo
 */
public class ExibirManterDebitoACobrarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirManterDebitoACobrar");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		ManterDebitoACobrarActionForm manterDebitoACobrarActionForm = (ManterDebitoACobrarActionForm) actionForm;

		String limparForm = (String) httpServletRequest.getParameter("limparForm");

		String codigoImovel = null;

		if(httpServletRequest.getParameter("idRegistroInseridoManter") != null
						&& !httpServletRequest.getParameter("idRegistroInseridoManter").equals("")){
			codigoImovel = "" + httpServletRequest.getParameter("idRegistroInseridoManter");
		}else{
			codigoImovel = manterDebitoACobrarActionForm.getCodigoImovel();
		}

		if(codigoImovel != null && !codigoImovel.trim().equals("")){
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovel));

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao");

			/*
			 * filtroImovel
			 * .adicionarCaminhoParaCarregamentoEntidade("ligacaoAguaSituacao.id");
			 * filtroImovel
			 * .adicionarCaminhoParaCarregamentoEntidade("ligacaoEsgotoSituacao.id");
			 */

			Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel
			// Inxistente
			if(imovelPesquisado != null && imovelPesquisado.isEmpty()){
				manterDebitoACobrarActionForm.setInscricaoImovel("Matr�cula Inexistente");
				manterDebitoACobrarActionForm.setNomeCliente("");
				manterDebitoACobrarActionForm.setSituacaoAgua("");
				manterDebitoACobrarActionForm.setSituacaoEsgoto("");
				httpServletRequest.setAttribute("corMatriculaImovel", null);
				httpServletRequest.setAttribute("nomeCampo", "codigoImovel");
				if(sessao.getAttribute("colecaoDebitosACobrar") != null){
					sessao.removeAttribute("colecaoDebitosACobrar");
				}

			}

			// [FS0001 - Verificar exist�ncioa da matr�cula do im�vel] Imovel
			// Excluido
			if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
				Imovel imovel = imovelPesquisado.iterator().next();
				if(imovel.getIndicadorExclusao() == Imovel.IMOVEL_EXCLUIDO){
					throw new ActionServletException("atencao.pesquisa.imovel.excluido");
				}
			}

			// [FS0003 - Verificar usu�rio com d�bito em cobran�a
			// administrativa]
			if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){
				Imovel imovel = imovelPesquisado.iterator().next();
				FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

				filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
				filtroImovelCobrancaSituacao
								.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel.getId()));

				Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class
								.getName());

				// Verifica se o im�vel tem d�bito em cobran�a administrativa
				if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){

					if(((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao() != null){

						if(((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao().getId()
										.equals(CobrancaSituacao.COBRANCA_ADMINISTRATIVA)
										&& ((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0))
														.getDataRetiradaCobranca() == null){
							// C�digo comentado para a customiza��o da cobran�a administrativa CASAL
							// throw new
							// ActionServletException("atencao.pesquisa.imovel.cobranca_administrativa");
						}
					}
				}
			}

			// Obtem o cliente imovel do imovel pesquisado
			if(imovelPesquisado != null && !imovelPesquisado.isEmpty()){

				Imovel imovel = imovelPesquisado.iterator().next();

				FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();

				filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("imovel");

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.IMOVEL_ID, imovel.getId()));

				filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO_ATUAL_ID,
								DebitoCreditoSituacao.NORMAL));

				filtroDebitoACobrar.adicionarParametro(new Menor(FiltroDebitoACobrar.NUMERO_PRESTACOES_COBRADAS,
								FiltroDebitoACobrar.NUMERO_PRESTACOES_DEBITO));

				filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrar.FINANCIAMENTO_TIPO);
				filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrar.DEBITO_TIPO);
				filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoACobrar.DEBITO_CREDITO_SITUACAO);

				Collection<DebitoACobrar> colecaoDebitoACobrar = fachada.pesquisar(filtroDebitoACobrar, DebitoACobrar.class.getName());
				colecaoDebitoACobrar = Fachada.getInstancia().agruparDebitoACobrar(colecaoDebitoACobrar, Boolean.FALSE);

				// [FS0002] - Verificar exist�ncia de d�bito a cobrar
				if(colecaoDebitoACobrar != null && colecaoDebitoACobrar.isEmpty()){
					throw new ActionServletException("atencao.debito_a_cobrar.inexistente", null, codigoImovel);
				}

				// manterDebitoACobrarActionForm.setCodigoImovel()
				sessao.setAttribute("colecaoDebitosACobrar", colecaoDebitoACobrar);

				sessao.setAttribute("imovelPesquisado", imovel);

				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, codigoImovel));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
								ClienteRelacaoTipo.USUARIO));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

				Collection<ClienteImovel> clienteImovelPesquisado = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				if(clienteImovelPesquisado != null && !clienteImovelPesquisado.isEmpty()){
					ClienteImovel clienteImovel = clienteImovelPesquisado.iterator().next();
					if(clienteImovel.getCliente() != null){
						manterDebitoACobrarActionForm.setNomeCliente(clienteImovel.getCliente().getNome());
					}
				}
				if(imovel.getLigacaoAguaSituacao() != null){
					manterDebitoACobrarActionForm.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
				}

				if(imovel.getLigacaoEsgotoSituacao() != null){
					manterDebitoACobrarActionForm.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());
				}
				manterDebitoACobrarActionForm.setCodigoImovel(codigoImovel);

				manterDebitoACobrarActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());

				httpServletRequest.setAttribute("corMatriculaImovel", "valor");
				httpServletRequest.setAttribute("nomeCampo", "idTipoDebito");

			}
		}

		if(limparForm != null && !limparForm.trim().equalsIgnoreCase("")){

			// manterDebitoACobrarActionForm.set
			manterDebitoACobrarActionForm.reset(actionMapping, httpServletRequest);

			if(sessao.getAttribute("imovelPesquisado") != null){
				sessao.removeAttribute("imovelPesquisado");
			}

			if(sessao.getAttribute("colecaoDebitosACobrar") != null){
				sessao.removeAttribute("colecaoDebitosACobrar");
			}

		}

		return retorno;
	}
}