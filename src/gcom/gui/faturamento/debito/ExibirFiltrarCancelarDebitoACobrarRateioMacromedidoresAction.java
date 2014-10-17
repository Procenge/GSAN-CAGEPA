/**
 * 
 */
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

package gcom.gui.faturamento.debito;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroLigacaoTipo;
import gcom.micromedicao.consumo.LigacaoTipo;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Tela de filtrar Cancelar Debito A Cobrar Rateio Macromedidores.
 * 
 * @author Josenildo Neves
 * @date 12/08/2013
 */
public class ExibirFiltrarCancelarDebitoACobrarRateioMacromedidoresAction
				extends GcomAction {

	/**
	 * [UC3103] Cancelar D�bito a Cobrar de Rateio por Macromedidor
	 * 
	 * @author Josenildo Neves
	 * @date 12/08/2013
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarCancelarDebitoACobrarRateioMacromedidores");

		FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm form = (FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		filtroDebitoTipo.setCampoOrderBy(FiltroDebitoTipo.DESCRICAO);
		Collection colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

		if(Util.isNaoNuloBrancoZero(colecaoDebitoTipo)){
			httpServletRequest.setAttribute("colecaoDebitoTipo", colecaoDebitoTipo);
		}

		if(Util.isNaoNuloBrancoZero(form.getMatriculaImovel())){

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getMatriculaImovel()));
			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

			if(Util.isNaoNuloBrancoZero(imovel)){

				if(!imovel.getIndicadorImovelCondominio().equals(Imovel.IMOVEL_CONDOMINIO)){
					throw new ActionServletException("atencao.imovel.nao_condominio");
				}
			}else{
				throw new ActionServletException("atencao.imovel.inexistente");
			}

		}

		FiltroLigacaoTipo filtroLigacaoTipo = new FiltroLigacaoTipo();
		filtroLigacaoTipo.setCampoOrderBy(FiltroLigacaoTipo.DESCRICAO);
		Collection colecaoLigacaoTipo = fachada.pesquisar(filtroLigacaoTipo, LigacaoTipo.class.getName());

		if(Util.isNaoNuloBrancoZero(colecaoLigacaoTipo)){
			httpServletRequest.setAttribute("colecaoLigacaoTipo", colecaoLigacaoTipo);
		}

		if(Util.isVazioOuBranco(form.getIdTipoLigacao())){

			form.setIdTipoLigacao(LigacaoTipo.LIGACAO_AGUA);
		}

		return retorno;

	}

}
