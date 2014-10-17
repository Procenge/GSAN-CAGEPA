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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.gui.arrecadacao.banco;

import gcom.arrecadacao.banco.FiltroBanco;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * @author hgoncalves
 *         21/03/2014
 */
public class FiltrarBancoAction
				extends GcomAction {


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterBanco");
		HttpSession sessao = httpServletRequest.getSession(false);

		DynaActionForm form = (DynaActionForm) actionForm;

		String idBanco = (String) form.get("idBanco");
		String nome = (String) form.get("nome");
		String nomeAbreviado = (String) form.get("nomeAbreviado");
		String indicadorUso = (String) form.get("indicadorUso");
		String tipoPesquisa = (String) form.get("tipoPesquisa");

		FiltroBanco filtroBanco = new FiltroBanco(FiltroBanco.ID);
		boolean peloMenosUmParametroInformado = false;

		if(idBanco != null && !idBanco.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroBanco.adicionarParametro(new ParametroSimples(FiltroBanco.ID, idBanco));
		}
		
		if(nome != null && !nome.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroBanco.adicionarParametro(new ComparacaoTextoCompleto(FiltroBanco.NOME_BANCO, nome));
			}else{
				filtroBanco.adicionarParametro(new ComparacaoTexto(FiltroBanco.NOME_BANCO, nome));
			}
		}
		
		if(nomeAbreviado != null && !nomeAbreviado.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroBanco.adicionarParametro(new ParametroSimples(FiltroBanco.NOME_ABREVIADO_BANCO, nomeAbreviado));
		}

		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase(ConstantesSistema.TODOS.toString())){
			peloMenosUmParametroInformado = true;
			filtroBanco.adicionarParametro(new ParametroSimples(FiltroBanco.INDICADOR_USO, indicadorUso));
		}

		// Caso o usuário não tenha informado nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		/*
		 * Verifica se o checkbox Atualizar está marcado e em caso
		 * afirmativo manda pelo um request uma variável para o
		 * ExibirManterBancoAction e nele verificar se irá para o
		 * atualizar ou para o manter
		 */
		if(ConstantesSistema.SIM.toString().equalsIgnoreCase(form.get("checkAtualizar").toString())){
			httpServletRequest.setAttribute("atualizar", form.get("checkAtualizar"));

		}

		// Manda o filtro pelo request para o ExibirManterBancoAction
		// httpServletRequest.setAttribute("filtroBanco", filtroBanco); // ?
		sessao.setAttribute("filtroBanco", filtroBanco);

		return retorno;

	}

}