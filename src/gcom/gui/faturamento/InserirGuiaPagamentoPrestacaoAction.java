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

import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.GuiaPagamentoPrestacaoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0187] - Manter Guia de pagamento
 * 
 * @author eduardo henrique
 * @date 31/07/2008
 * @since v0.04
 */
public class InserirGuiaPagamentoPrestacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirGuiaPagamentoPrestacao");

		InserirGuiaPagamentoActionForm inserirGuiaPagamentoForm = (InserirGuiaPagamentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		if(inserirGuiaPagamentoForm.getNumeroTotalPrestacao() != null
						&& !inserirGuiaPagamentoForm.getNumeroTotalPrestacao().equalsIgnoreCase("")
						&& inserirGuiaPagamentoForm.getIdTipoDebito() != null
						&& !inserirGuiaPagamentoForm.getIdTipoDebito().equalsIgnoreCase("")){

			// valida se o tipo do d�bito informado � v�lido
			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();

			filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");

			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, new Integer(inserirGuiaPagamentoForm
							.getIdTipoDebito()), ParametroSimples.CONECTOR_AND));
			filtroDebitoTipo
							.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Pesquisa de acordo com os par�metros informados no filtro
			Collection colecaoFiltroDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a cole��o
			if(colecaoFiltroDebitoTipo == null || colecaoFiltroDebitoTipo.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.debitoTipo.inexistente", null, inserirGuiaPagamentoForm
								.getIdTipoDebito());
			}

			DebitoTipo debitoTipo = (DebitoTipo) colecaoFiltroDebitoTipo.iterator().next();

			Collection colecaoGuiaPagamentoPrestacao = null;

			if(sessao.getAttribute("colecaoGuiaPrestacaoHelper") != null && !sessao.getAttribute("colecaoGuiaPrestacaoHelper").equals("")){
				colecaoGuiaPagamentoPrestacao = (Collection) sessao.getAttribute("colecaoGuiaPrestacaoHelper");
			}else{
				colecaoGuiaPagamentoPrestacao = new ArrayList();
			}

			BigDecimal valorDebito = new BigDecimal(0.00);
			if(inserirGuiaPagamentoForm.getValorDebito() != null && !inserirGuiaPagamentoForm.getValorDebito().equals("")){
				valorDebito = Util.formatarMoedaRealparaBigDecimal(inserirGuiaPagamentoForm.getValorDebito());

				// [FS0009] - Validar valor do d�bito
				BigDecimal valorLimite = debitoTipo.getValorLimite();

				if(valorLimite != null && valorDebito.compareTo(valorLimite) == 1){
					Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
					String valorLimiteStr = Util.formatarMoedaReal(valorLimite);

					if(!fachada.verificarPermissaoInserirGuiaPagamentoValorDebitoMaiorLimite(usuarioLogado)){
						throw new ActionServletException("atencao.guia_debito_tipo_valor_maior_limite", null, valorLimiteStr);
					}
				}

				if(valorDebito.compareTo(new BigDecimal(0.00)) == 0){
					throw new ActionServletException("atencao.guia_debito_tipo_valor_zero");
				}

			}

			Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPrestacaoHelper = null;
			if(sessao.getAttribute("colecaoGuiaPrestacaoHelper") != null && !sessao.getAttribute("colecaoGuiaPrestacaoHelper").equals("")){
				colecaoGuiaPrestacaoHelper = (Collection<GuiaPagamentoPrestacaoHelper>) sessao.getAttribute("colecaoGuiaPrestacaoHelper");
			}else{
				colecaoGuiaPrestacaoHelper = new ArrayList();
			}

			this.validarDebitoTipoDuplicado(colecaoGuiaPrestacaoHelper, debitoTipo);

			BigDecimal valorTotalGuia = valorDebito;
			for(Iterator iteratorColecaoGuiaPagamentoPrestacao = colecaoGuiaPagamentoPrestacao.iterator(); iteratorColecaoGuiaPagamentoPrestacao
							.hasNext();){
				GuiaPagamentoPrestacaoHelper guiaPrestacaoHelper = (GuiaPagamentoPrestacaoHelper) iteratorColecaoGuiaPagamentoPrestacao
								.next();

				if(guiaPrestacaoHelper.getId().equals(new Integer(inserirGuiaPagamentoForm.getIdTipoDebito()))){
					throw new ActionServletException("atencao.tipo_debito_guia_ja_existente");
				}

				// Acumula o valor existente dos Tipos de D�bito para exibi��o
				valorTotalGuia = valorTotalGuia.add(guiaPrestacaoHelper.getValorTipoDebito());
			}

			sessao.setAttribute("valorTotalGuiaPagamento", valorTotalGuia);

			// gera as Presta��es
			int numeroPrestacoes = Integer.parseInt(inserirGuiaPagamentoForm.getNumeroTotalPrestacao());
			BigDecimal valorPrestacao = Util.calcularValorPrestacao(valorDebito, numeroPrestacoes, 1);

			// adiciona � classe helper para exibi��o
			GuiaPagamentoPrestacaoHelper beanHelper = new GuiaPagamentoPrestacaoHelper(Long.valueOf(debitoTipo.getId()), debitoTipo
							.getDescricao(), valorDebito, valorPrestacao);
			beanHelper.setUltimaAlteracao(new Date());
			beanHelper.setIdItemLancamentoContabil(debitoTipo.getLancamentoItemContabil().getId());

			if(inserirGuiaPagamentoForm.getNumeroProcessoAdministrativoExecucaoFiscal() != null
							&& !inserirGuiaPagamentoForm.getNumeroProcessoAdministrativoExecucaoFiscal().equals("")){
				beanHelper.setNumeroProcessoAdministrativoExecucaoFiscal(Integer.valueOf(inserirGuiaPagamentoForm
								.getNumeroProcessoAdministrativoExecucaoFiscal()));
			}

			colecaoGuiaPrestacaoHelper.add(beanHelper);

			// Caso o n�mero de presta��es tenha sido alterado
			// Atualiza os d�bitos que j� tinham sido inseridos
			for(Iterator iteratorColecaoGuiaPagamentoPrestacao = colecaoGuiaPagamentoPrestacao.iterator(); iteratorColecaoGuiaPagamentoPrestacao
							.hasNext();){
				GuiaPagamentoPrestacaoHelper guiaPrestacaoHelper = (GuiaPagamentoPrestacaoHelper) iteratorColecaoGuiaPagamentoPrestacao
								.next();

				// gera as Presta��es
				BigDecimal valorDebitoAlterado = guiaPrestacaoHelper.getValorTipoDebito();
				BigDecimal valorPrestacaoAlterado = Util.calcularValorPrestacao(valorDebitoAlterado, numeroPrestacoes, 1);

				guiaPrestacaoHelper.setValorPrestacaoTipoDebito(valorPrestacaoAlterado);

			}

			sessao.setAttribute("colecaoGuiaPrestacaoHelper", colecaoGuiaPrestacaoHelper);
		}
		httpServletRequest.setAttribute("fechaPopup", "true");

		return retorno;
	}

	/**
	 * @author Saulo Lima
	 * @date 14/04/2012
	 * @param colecaoGuiaPrestacaoHelper
	 * @param debitoTipo
	 */
	public void validarDebitoTipoDuplicado(Collection<GuiaPagamentoPrestacaoHelper> colecaoGuiaPrestacaoHelper, DebitoTipo debitoTipo){

		if(colecaoGuiaPrestacaoHelper.size() > 0){
			Iterator<GuiaPagamentoPrestacaoHelper> iterator = colecaoGuiaPrestacaoHelper.iterator();
			while(iterator.hasNext()){
				GuiaPagamentoPrestacaoHelper beanHelper = iterator.next();
				if(beanHelper.getId().equals(Long.valueOf(debitoTipo.getId()))){
					throw new ActionServletException("atencao.guia_debito_tipo_duplicado");
				}
			}
		}

	}

}
