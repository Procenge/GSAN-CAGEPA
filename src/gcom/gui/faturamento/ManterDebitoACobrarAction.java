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

import gcom.cadastro.imovel.FiltroImovelCobrancaSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.cobranca.CobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manter D�bito a Cobrar ao Imovel
 * [UC0184] Manter D�bito a Cobrar
 * 
 * @author Rafael Santos
 * @since 30/12/2005
 */
public class ManterDebitoACobrarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ManterDebitoACobrarActionForm manterDebitoACobrarActionForm = (ManterDebitoACobrarActionForm) actionForm;

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);
		Imovel imovel = null;

		if(sessao.getAttribute("imovelPesquisado") != null){
			imovel = (Imovel) sessao.getAttribute("imovelPesquisado");
		}

		Fachada fachada = Fachada.getInstancia();

		// Obt�m os ids de remo��o
		String[] ids = manterDebitoACobrarActionForm.getIdDebitoACobrar();

		// mensagem de erro quando o usu�rio tenta excluir sem ter selecionado
		// nenhum
		// registro
		if(ids == null || ids.length == 0){

			throw new ActionServletException("atencao.registros.nao_selecionados");

		}else{

			try{

				String valorParametro = ParametroAtendimentoPublico.P_TRATAR_DEBITO_TIPO_PARCELAMENTO_AGRUPADO.executar();

				if(valorParametro.equals(ConstantesSistema.SIM.toString())){

					ids = this.obterIdsDebitosACobrarAgrupados(ids, httpServletRequest);

				}

			}catch(Exception e){

				throw new ActionServletException("erro.sistema");

			}

		}

		// [FS0003 - Verificar usu�rio com d�bito em cobran�a
		// administrativa]
		if(imovel != null){
			FiltroImovelCobrancaSituacao filtroImovelCobrancaSituacao = new FiltroImovelCobrancaSituacao();

			filtroImovelCobrancaSituacao.adicionarCaminhoParaCarregamentoEntidade("cobrancaSituacao");
			filtroImovelCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroImovelCobrancaSituacao.IMOVEL_ID, imovel.getId()));

			Collection imovelCobrancaSituacaoEncontrada = fachada.pesquisar(filtroImovelCobrancaSituacao, ImovelCobrancaSituacao.class
							.getName());

			// Verifica se o im�vel tem d�bito em cobran�a administrativa
			if(imovelCobrancaSituacaoEncontrada != null && !imovelCobrancaSituacaoEncontrada.isEmpty()){

				if(((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao() != null){

					if(((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0)).getCobrancaSituacao().getId().equals(
									CobrancaSituacao.COBRANCA_ADMINISTRATIVA)
									&& ((ImovelCobrancaSituacao) ((List) imovelCobrancaSituacaoEncontrada).get(0))
													.getDataRetiradaCobranca() == null){

						// C�digo comentado para a customiza��o da cobran�a administrativa CASAL
						// throw new
						// ActionServletException("atencao.pesquisa.imovel.cobranca_administrativa");
					}
				}
			}
		}
		FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
		filtroDebitoACobrar.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		for(int i = 0; i < ids.length; i++){
			filtroDebitoACobrar.limparListaParametros();
			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ID, ids[i]));
			DebitoACobrar debitoACobrar = (DebitoACobrar) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroDebitoACobrar,
							DebitoACobrar.class.getName()));
			if(debitoACobrar.getFinanciamentoTipo().getIndicadorCancelaDebito().equals(ConstantesSistema.NAO)){
				throw new ActionServletException("atencao.nao_permitido_cancelar_debito_a_cobrar", null, debitoACobrar.getDebitoTipo()
								.getDescricao());
			}
		}
		Integer matriculaImovel = null;
		if(imovel != null){
			matriculaImovel = imovel.getId();
		}

		fachada.cancelarDebitoACobrar(ids, getUsuarioLogado(httpServletRequest), matriculaImovel, Boolean.TRUE);

		// Monta a p�gina de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " D�bito(s) a Cobrar do im�vel " + imovel.getId()
							+ " cancelado(s) com sucesso.", "Realizar outro Cancelamento de D�bito a Cobrar",
							"exibirManterDebitoACobrarAction.do?menu=sim");
		}

		return retorno;
	}

	private String[] obterIdsDebitosACobrarAgrupados(String[] ids, HttpServletRequest httpServletRequest){

		Collection<DebitoACobrar> colecaoDebitoACobrar = (Collection<DebitoACobrar>) httpServletRequest.getSession().getAttribute(
						"colecaoDebitosACobrar");

		if(colecaoDebitoACobrar != null && !colecaoDebitoACobrar.isEmpty()){

			List<String> listIds = new ArrayList<String>();

			for(String id : ids){

				listIds.add(id);

				for(DebitoACobrar debitoACobrar : colecaoDebitoACobrar){

					if(debitoACobrar.getId().equals(Integer.valueOf(id))){

						if(debitoACobrar.getIdsDebitosACobrarAgrupados() != null){

							for(Integer idDebitoAgrupado : debitoACobrar.getIdsDebitosACobrarAgrupados()){

								listIds.add(String.valueOf(idDebitoAgrupado));

							}

						}

						break;

					}

				}

			}

			String[] idsArray = new String[listIds.size()];

			int i = 0;

			for(String id : listIds){

				idsArray[i] = id;

				i++;

			}

			return idsArray;

		}else{

			return ids;

		}

	}

}