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

package gcom.gui.relatorio.cadastro.imovel;

import gcom.arrecadacao.pagamento.FiltroPagamentoSituacao;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Gerar Relação de Pagamentos não Classificados por Banco e Numero do Aviso.
 * 
 * @author Diogo Monteiro
 * @date 23/03/2012
 */

public class ExibirGerarRelatorioPagamentosNaoClassificadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioPagamentosNaoClassificadosAction");

		this.pesquisarSituacaoPagamento(httpServletRequest);

		return retorno;
	}

	private void pesquisarSituacaoPagamento(HttpServletRequest httpServletRequest){

		FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();

		filtroPagamentoSituacao.setConsultaSemLimites(true);
		filtroPagamentoSituacao.setCampoOrderBy(FiltroCategoria.CODIGO);
		filtroPagamentoSituacao.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoPagamentoSituacao = this.getFachada().pesquisar(filtroPagamentoSituacao, PagamentoSituacao.class.getName());

		if(!Util.isVazioOrNulo(colecaoPagamentoSituacao)){

			for(Object object : colecaoPagamentoSituacao){
				PagamentoSituacao pagamentoSituacao = (PagamentoSituacao) object;
				pagamentoSituacao.setDescricao(pagamentoSituacao.getId() + " - " + pagamentoSituacao.getDescricao());
			}

			httpServletRequest.setAttribute("colecaoPagamentoSituacao", colecaoPagamentoSituacao);

		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Situação do Pagamento");
		}
	}

}
