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

package gcom.gui.cadastro.cliente.atividadeeconomica;

import gcom.cadastro.cliente.AtividadeEconomica;
import gcom.cadastro.cliente.FiltroAtividadeEconomica;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3152] Pesquisar Atividade Econ�mica
 * 
 * @author Anderson Italo
 * @date 27/06/2014
 */
public class PesquisarAtividadeEconomicaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("listaAtividadeEconomica");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m o action form
		PesquisarAtividadeEconomicaActionForm pesquisarAtividadeActionForm = (PesquisarAtividadeEconomicaActionForm) actionForm;

		// Recupera os par�metros do form
		String codigoAtividade = pesquisarAtividadeActionForm.getCodigoAtividade();
		String descricaoAtividade = pesquisarAtividadeActionForm.getDescricaoAtividade();
		String tipoPesquisa = pesquisarAtividadeActionForm.getTipoPesquisa();

		boolean peloMenosUmParametroInformado = false;

		FiltroAtividadeEconomica filtroAtividadeEconomica = new FiltroAtividadeEconomica();

		if(codigoAtividade != null && !codigoAtividade.trim().equalsIgnoreCase("")){

			filtroAtividadeEconomica.adicionarParametro(new ComparacaoTexto(FiltroAtividadeEconomica.CODIGO, codigoAtividade));
			peloMenosUmParametroInformado = true;
		}

		if(descricaoAtividade != null && !descricaoAtividade.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroAtividadeEconomica.adicionarParametro(new ComparacaoTextoCompleto(FiltroAtividadeEconomica.DESCRICAO,
								descricaoAtividade));
			}else{

				filtroAtividadeEconomica.adicionarParametro(new ComparacaoTexto(FiltroAtividadeEconomica.DESCRICAO, descricaoAtividade));
			}
		}

		// [FS0001] - Verificar preenchimento dos campos
		if(!peloMenosUmParametroInformado){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		filtroAtividadeEconomica.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Faz a pesquisa baseada no filtro
		Collection<AtividadeEconomica> colecaoAtividadeEconomica = fachada.pesquisar(filtroAtividadeEconomica,
						AtividadeEconomica.class.getName());

		// Verificar se a pesquisa de atividade n�o est� vazia
		if(!Util.isVazioOrNulo(colecaoAtividadeEconomica)){

			// Aciona o controle de pagina��o para que sejam pesquisados apenas
			// os registros que aparecem na p�gina
			Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroAtividadeEconomica, AtividadeEconomica.class.getName());
			colecaoAtividadeEconomica = (Collection) resultado.get("colecaoRetorno");
			retorno = (ActionForward) resultado.get("destinoActionForward");

			// Manda a cole��o das Atividade pesquisadas para o request
			httpServletRequest.getSession(false).setAttribute("colecaoAtividadeEconomica", colecaoAtividadeEconomica);

		}else{

			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Atividade Ec�nomica");
		}

		return retorno;
	}

}
