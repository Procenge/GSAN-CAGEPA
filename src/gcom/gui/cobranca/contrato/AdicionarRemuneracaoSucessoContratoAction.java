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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.cobranca.contrato;

import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorSucesso;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

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

public class AdicionarRemuneracaoSucessoContratoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAdicionarRemuneracaoSucessoContrato");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Instância do formulário que está sendo utilizado
		AdicionarRemuneracaoSucessoContratoActionForm form = (AdicionarRemuneracaoSucessoContratoActionForm) actionForm;

		// Parâmetros recebidos para adicionar uma remuneração sucesso
		String diasVencidos = form.getDiasVencidos();
		String percentualRemuneracao = form.getPercentualRemuneracao();
		String valorFixo = form.getValorFixo();
		String percentualParcelaPaga = form.getPercentualParcelaPaga();

		// Verifica se foi informado percentual ou valor
		if((StringUtils.isEmpty(percentualRemuneracao) && (StringUtils.isEmpty(valorFixo)))
						|| (StringUtils.isNotEmpty(percentualRemuneracao) && (StringUtils.isNotEmpty(valorFixo)))){
			throw new ActionServletException("atencao.informe.percentual_ou_valor_fixo");
		}

		// Gerando o objeto RemuneracaoSucesso que será inserido na coleção
		CobrancaContratoRemuneracaoPorSucesso remuneracaoSucesso = new CobrancaContratoRemuneracaoPorSucesso();
		remuneracaoSucesso.setUltimaAlteracao(new Date());
		remuneracaoSucesso.setDiasVencidos(Integer.valueOf(diasVencidos));

		if(StringUtils.isNotEmpty(percentualRemuneracao)){
			remuneracaoSucesso.setPercentualRemuneracao(Util.formatarMoedaRealparaBigDecimal(percentualRemuneracao));
		}
		if(StringUtils.isNotEmpty(valorFixo)){
			remuneracaoSucesso.setValorFixo(Util.formatarMoedaRealparaBigDecimal(valorFixo));
		}

		remuneracaoSucesso.setPercentualParcelaPaga(Util.formatarMoedaRealparaBigDecimal(percentualParcelaPaga));

		// Limpando as eventuais remuneraçoes por produtividade
		// @author: Andre Nishimura
		sessao.removeAttribute("colecaoRemuneracaoProdutividade");

		// Colocando o objeto gerado na coleção que ficará na sessão
		if(sessao.getAttribute("colecaoRemuneracaoSucesso") == null
						|| ((Collection) sessao.getAttribute("colecaoRemuneracaoSucesso")).isEmpty()){
			Collection colecaoRemuneracaoSucesso = new ArrayList();
			colecaoRemuneracaoSucesso.add(remuneracaoSucesso);

			// Ordenar por um único campo
			BeanComparator comparador = new BeanComparator("diasVencidos");
			Collections.sort((List) colecaoRemuneracaoSucesso, comparador);

			sessao.setAttribute("colecaoRemuneracaoSucesso", colecaoRemuneracaoSucesso);

			httpServletRequest.setAttribute("reloadPage", "OK");

			// Definindo o caso de uso que receberá o retorno
			if(sessao.getAttribute("UseCase").equals("INSERIRCONTRATO")){
				httpServletRequest.setAttribute("reloadPageURL", "INSERIRCONTRATO");
			}else{
				httpServletRequest.setAttribute("reloadPageURL", "MANTERCONTRATO");
			}

		}else{
			Collection colecaoRemuneracaoSucesso = (Collection) sessao.getAttribute("colecaoRemuneracaoSucesso");

			// [FS0014] - Verificar débito já existente
			if(!verificarRemuneracaoJaExistente(colecaoRemuneracaoSucesso, remuneracaoSucesso)){
				colecaoRemuneracaoSucesso.add(remuneracaoSucesso);

				// Ordenar por um único campo
				BeanComparator comparador = new BeanComparator("diasVencidos");
				Collections.sort((List) colecaoRemuneracaoSucesso, comparador);

				sessao.setAttribute("colecaoRemuneracaoSucesso", colecaoRemuneracaoSucesso);

				httpServletRequest.setAttribute("reloadPage", "OK");

				// Definindo o caso de uso que receberá o retorno
				if(sessao.getAttribute("UseCase").equals("INSERIRCONTRATO")){
					httpServletRequest.setAttribute("reloadPageURL", "INSERIRCONTRATO");
				}else{
					httpServletRequest.setAttribute("reloadPageURL", "MANTERCONTRATO");
				}

			}else{
				throw new ActionServletException("atencao.adicionar_remuneracao_ja_existente");
			}

		}

		// Limpando o formulário para inserir um novo débito
		form.setDiasVencidos("");
		form.setPercentualRemuneracao("");
		form.setValorFixo("");
		form.setPercentualParcelaPaga("");

		return retorno;
	}

	/**
	 * [FS0014] - Caso o tipo do débito selecionado já esteja na lista
	 * 
	 * @param colecaoDebitoCobrado
	 * @param debitoCobradoInsert
	 * @return um boleano
	 */

	private boolean verificarRemuneracaoJaExistente(Collection colecaoRemuneracaoSucesso,
					CobrancaContratoRemuneracaoPorSucesso remuneracaoSucessoInsert){

		boolean retorno = false;

		Iterator colecaoRemuneracaoIt = colecaoRemuneracaoSucesso.iterator();
		CobrancaContratoRemuneracaoPorSucesso remuneracaoColecao;

		while(colecaoRemuneracaoIt.hasNext()){
			remuneracaoColecao = (CobrancaContratoRemuneracaoPorSucesso) colecaoRemuneracaoIt.next();
			if(remuneracaoColecao.getDiasVencidos().equals(remuneracaoSucessoInsert.getDiasVencidos())){
				retorno = true;
				break;
			}
		}

		return retorno;
	}

}
