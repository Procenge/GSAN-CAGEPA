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

package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Virg�nia Melo
 * @create 29/08/2008
 */

public class PesquisarAcaoCobrancaAction
				extends GcomAction {

	boolean peloMenosUmParametroInformado = false;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("pesquisarAcaoCobrancaResultado");

		// cria sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		PesquisarAcaoCobrancaActionForm form = (PesquisarAcaoCobrancaActionForm) actionForm;

		// Recuperando informa��es do form
		String descricaoAcao = form.getDescricaoAcao();
		String estagioCobranca = form.getIdCobrancaEstagio();
		String tipoDocumento = form.getIdTipoDocumentoGerado();
		String tipoServico = form.getIdServicoTipo();

		FiltroCobrancaAcao filtro = new FiltroCobrancaAcao(FiltroCobrancaAcao.ID);
		filtro.adicionarCaminhoParaCarregamentoEntidade("cobrancaEstagio");

		peloMenosUmParametroInformado = false;

		// Inserindo os par�metros informados no filtro

		// Verifica se a descricao foi informada
		if(descricaoAcao != null && !descricaoAcao.trim().equalsIgnoreCase("")){
			filtro.adicionarParametro(new ComparacaoTexto(FiltroCobrancaAcao.DESCRICAO, descricaoAcao));
			peloMenosUmParametroInformado = true;
		}

		// Verifica se o estagio foi informado
		if(estagioCobranca != null && !estagioCobranca.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.COBRANCA_ESTAGIO_ID, Integer.valueOf(estagioCobranca)));
		}

		// Verifica se o tipo documento foi informado
		if(tipoDocumento != null && !tipoDocumento.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.DOCUMENTO_TIPO_ID, Integer.valueOf(tipoDocumento)));
		}

		// Verifica se o tipo servico foi informado
		if(tipoServico != null && !tipoServico.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.SERVICO_TIPO_ID_ACAO_COBRANCA, Integer.valueOf(tipoServico)));
		}

		// Caso o usu�rio n�o tenha informado nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, CobrancaAcao.class.getName());

		Collection colecaoAcaoCobranca = (Collection) resultado.get("colecaoRetorno");

		retorno = (ActionForward) resultado.get("destinoActionForward");

		if(colecaoAcaoCobranca == null || colecaoAcaoCobranca.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}else{
			sessao.setAttribute("colecaoAcaoCobranca", colecaoAcaoCobranca);
		}

		return retorno;
	}
}