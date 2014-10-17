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

package gcom.gui.cadastro.localidade;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarLocalidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("retornarFiltroLocalidade");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarLocalidadeActionForm filtrarLocalidadeActionForm = (FiltrarLocalidadeActionForm) actionForm;

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade(FiltroLocalidade.DESCRICAO);

		// Objetos que ser�o retornados pelo hibernate
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");

		String localidadeID = filtrarLocalidadeActionForm.getLocalidadeID();
		String localidadeNome = filtrarLocalidadeActionForm.getLocalidadeNome();
		String gerenciaRegionalID = filtrarLocalidadeActionForm.getGerenciaID();
		Integer concessionariaID = filtrarLocalidadeActionForm.getConcessionariaID();
		String idUnidadeNegocio = filtrarLocalidadeActionForm.getIdUnidadeNegocio();

		String indicadorUso = filtrarLocalidadeActionForm.getIndicadorUso();
		String tipoPesquisa = filtrarLocalidadeActionForm.getTipoPesquisa();

		// 1 check --- null uncheck
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		boolean peloMenosUmParametroInformado = false;

		if(localidadeID != null && !localidadeID.equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			Integer idLocalidade = new Integer(localidadeID);
			filtroLocalidade.setIdLocalidade(idLocalidade);
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
		}

		if(localidadeNome != null && !localidadeNome.equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtroLocalidade.setIndicadorNomeIniciandoOuContendo(ConstantesSistema.TIPO_PESQUISA_COMPLETA);
				filtroLocalidade.adicionarParametro(new ComparacaoTextoCompleto(FiltroLocalidade.DESCRICAO, localidadeNome));
			}else{
				filtroLocalidade.setIndicadorNomeIniciandoOuContendo(ConstantesSistema.TIPO_PESQUISA_INICIAL);
				filtroLocalidade.adicionarParametro(new ComparacaoTexto(FiltroLocalidade.DESCRICAO, localidadeNome));
			}
			filtroLocalidade.setNomeLocalidade(localidadeNome);
		}

		try{
			String indicadorConcessionaria = ParametroCadastro.P_INDICADOR_CONCESSIONARIA.executar();

			// Populando Concession�ria
			if(!ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){

				if(concessionariaID != null && ConstantesSistema.NUMERO_NAO_INFORMADO != concessionariaID.intValue()){
					peloMenosUmParametroInformado = true;
					filtroLocalidade.setConcessionaria(concessionariaID);
				}
			}

		}catch(ControladorException e){
			throw new IllegalArgumentException("erro.sistema");
		}

		if(gerenciaRegionalID != null
						&& !gerenciaRegionalID.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_GERENCIA, gerenciaRegionalID));
			filtroLocalidade.setGerenciaRegional(Integer.valueOf(gerenciaRegionalID));
		}

		// Unidade de Negocio
		if(idUnidadeNegocio != null && !idUnidadeNegocio.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			peloMenosUmParametroInformado = true;
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_UNIDADE_NEGOCIO, idUnidadeNegocio));
			filtroLocalidade.setUnidadeNegocio(Integer.valueOf(idUnidadeNegocio));
		}

		if(indicadorUso != null && !indicadorUso.equalsIgnoreCase("") && !indicadorUso.equalsIgnoreCase("3")){
			peloMenosUmParametroInformado = true;
			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroLocalidade.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
			}else{
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
				filtroLocalidade.setIndicadorUso(ConstantesSistema.INDICADOR_USO_DESATIVO);
			}
		}

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroLocalidade", filtroLocalidade);
		sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);

		// devolve o mapeamento de retorno
		return retorno;
	}

}
