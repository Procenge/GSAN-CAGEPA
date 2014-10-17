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
 * Virg�nia Melo
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

package gcom.gui.cobranca.contrato;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorProdutividade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AdicionarRemuneracaoProdutividadeContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAdicionarRemuneracaoProdutividadeContrato");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		AdicionarRemuneracaoProdutividadeContratoActionForm form = (AdicionarRemuneracaoProdutividadeContratoActionForm) actionForm;

		// Par�metros recebidos para adicionar uma remunera��o Produtividade
		// String diasVencidos = form.getDiasVencidos();
		// String percentualRemuneracao = form.getPercentualRemuneracao();
		String valorFixo = form.getValorFixo();
		// String percentualParcelaPaga = form.getPercentualParcelaPaga();
		String servico = form.getServicoTipo();

		// Verifica se foi informado o valor

		if(StringUtils.isEmpty(valorFixo) || StringUtils.isEmpty(servico)){
			throw new ActionServletException("atencao.informe.tipo_servico_e_valor_fixo");
		}

		// Gerando o objeto RemuneracaoProdutividade que ser� inserido na cole��o
		CobrancaContratoRemuneracaoPorProdutividade cobrancaContratoServicoValor = new CobrancaContratoRemuneracaoPorProdutividade();
		cobrancaContratoServicoValor.setUltimaAlteracao(new Date());
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, servico));
		Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		ServicoTipo servicoTipo = null;

		if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
			servicoTipo = colecaoServicoTipo.iterator().next();
		}else{
			throw new ActionServletException("atencao.servico_tipo_nao_encontrado");
		}

		cobrancaContratoServicoValor.setServicoTipo(servicoTipo);
		/*
		 * if (StringUtils.isNotEmpty(percentualRemuneracao)) {
		 * 
		 * cobrancaContratoServicoValor.setPercentualRemuneracao(Util.formatarMoedaRealparaBigDecimal
		 * (percentualRemuneracao));
		 * }
		 * 
		 * cobrancaContratoServicoValor.setPercentualParcelaPaga(Util.formatarMoedaRealparaBigDecimal
		 * (percentualParcelaPaga));
		 */
		if(StringUtils.isNotEmpty(valorFixo)){
			cobrancaContratoServicoValor.setValor(Util.formatarMoedaRealparaBigDecimal(valorFixo));
		}

		// Removendo eventuais remuneracoes por sucesso
		// @author: Andre Nishimura
		if(sessao.getAttribute("colecaoRemuneracaoSucesso") != null){
			sessao.removeAttribute("colecaoRemuneracaoSucesso");
		}

		// Colocando o objeto gerado na cole��o que ficar� na sess�o
		if(sessao.getAttribute("colecaoRemuneracaoProdutividade") == null
						|| ((Collection) sessao.getAttribute("colecaoRemuneracaoProdutividade")).isEmpty()){
			Collection colecaoRemuneracaoProdutividade = new ArrayList();
			colecaoRemuneracaoProdutividade.add(cobrancaContratoServicoValor);

			// Ordenar por um �nico campo
			/*
			 * BeanComparator comparador = new BeanComparator("diasVencidos");
			 * Collections.sort((List) colecaoRemuneracaoProdutividade, comparador);
			 */

			sessao.setAttribute("colecaoRemuneracaoProdutividade", colecaoRemuneracaoProdutividade);

			httpServletRequest.setAttribute("reloadPage", "OK");

			// Definindo o caso de uso que receber� o retorno
			if(sessao.getAttribute("UseCase").equals("INSERIRCONTRATO")){
				httpServletRequest.setAttribute("reloadPageURL", "INSERIRCONTRATO");
			}else{
				httpServletRequest.setAttribute("reloadPageURL", "MANTERCONTRATO");
			}

		}else{
			Collection colecaoRemuneracaoProdutividade = (Collection) sessao.getAttribute("colecaoRemuneracaoProdutividade");

			// [FS0014] - Verificar d�bito j� existente
			if(!verificarRemuneracaoJaExistente(colecaoRemuneracaoProdutividade, cobrancaContratoServicoValor)){
				colecaoRemuneracaoProdutividade.add(cobrancaContratoServicoValor);

				// Ordenar por um �nico campo
				/*
				 * BeanComparator comparador = new BeanComparator("diasVencidos");
				 * Collections.sort((List) colecaoRemuneracaoProdutividade, comparador);
				 */

				httpServletRequest.setAttribute("reloadPage", "OK");

				sessao.setAttribute("colecaoRemuneracaoProdutividade", colecaoRemuneracaoProdutividade);

				// Definindo o caso de uso que receber� o retorno
				if(sessao.getAttribute("UseCase").equals("INSERIRCONTRATO")){
					httpServletRequest.setAttribute("reloadPageURL", "INSERIRCONTRATO");
				}else{
					httpServletRequest.setAttribute("reloadPageURL", "MANTERCONTRATO");
				}

			}else{
				throw new ActionServletException("atencao.adicionar_remuneracao_ja_existente");
			}

		}

		// Limpando o formul�rio para inserir um novo d�bito
		// form.setDiasVencidos("");
		// form.setPercentualRemuneracao("");
		form.setValorFixo("");
		// form.setPercentualParcelaPaga("");
		form.setServicoTipo("");

		return retorno;
	}

	/**
	 * [FS0014] - Caso o tipo do d�bito selecionado j� esteja na lista
	 * 
	 * @param colecaoDebitoCobrado
	 * @param debitoCobradoInsert
	 * @return um boleano
	 */

	private boolean verificarRemuneracaoJaExistente(Collection colecaoRemuneracaoProdutividade,
					CobrancaContratoRemuneracaoPorProdutividade remuneracaoProdutividadeInsert){

		boolean retorno = false;

		Iterator colecaoRemuneracaoIt = colecaoRemuneracaoProdutividade.iterator();
		CobrancaContratoRemuneracaoPorProdutividade remuneracaoColecao;

		while(colecaoRemuneracaoIt.hasNext()){
			remuneracaoColecao = (CobrancaContratoRemuneracaoPorProdutividade) colecaoRemuneracaoIt.next();
			if(remuneracaoColecao.getServicoTipo().getId().intValue() == remuneracaoProdutividadeInsert.getServicoTipo().getId().intValue()){
				retorno = true;
				break;
			}
		}

		return retorno;
	}

}
