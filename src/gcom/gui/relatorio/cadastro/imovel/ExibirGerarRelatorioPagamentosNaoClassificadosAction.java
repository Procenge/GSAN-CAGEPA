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
 * Gerar Rela��o de Pagamentos n�o Classificados por Banco e Numero do Aviso.
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

			throw new ActionServletException("atencao.naocadastrado", null, "Situa��o do Pagamento");
		}
	}

}
