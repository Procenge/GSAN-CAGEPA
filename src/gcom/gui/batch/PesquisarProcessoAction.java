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

package gcom.gui.batch;

import gcom.batch.FiltroProcesso;
import gcom.batch.Processo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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
 * @author Cinthya Cavalcanti
 * @date 02/12/2011
 */

public class PesquisarProcessoAction
				extends GcomAction {

	/**
	 * <<Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("pesquisarProcesso");

		// Mudar isso quando tiver esquema de segurança
		// HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		PesquisarProcessoActionForm pesquisarProcessoActionForm = (PesquisarProcessoActionForm) actionForm;

		// Recupera os parâmetros do form
		String descricaoProcesso = pesquisarProcessoActionForm.getDescricao();
		String descricaoAbreviada = pesquisarProcessoActionForm.getDescricaoAbreviada();
		String idProcessoTipo = pesquisarProcessoActionForm.getIdProcessoTipo();
		String tipoPesquisa = pesquisarProcessoActionForm.getTipoPesquisa();

		FiltroProcesso filtroProcesso = new FiltroProcesso();
		boolean peloMenosUmParametroInformado = false;

		// Tipo de Processo
		if(idProcessoTipo != null && !idProcessoTipo.equalsIgnoreCase("-1")){
			filtroProcesso.adicionarParametro(new ParametroSimples(FiltroProcesso.PROCESSO_TIPO, idProcessoTipo));
			peloMenosUmParametroInformado = true;
		}

		// Descricao
		if(descricaoProcesso != null && !descricaoProcesso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){

				filtroProcesso.adicionarParametro(new ComparacaoTextoCompleto(FiltroProcesso.DESCRICAO, descricaoProcesso));
			}else{

				filtroProcesso.adicionarParametro(new ComparacaoTexto(FiltroProcesso.DESCRICAO, descricaoProcesso));
			}
		}

		// Descricao Abreviada
		if(descricaoAbreviada != null && !descricaoAbreviada.equalsIgnoreCase("")){
			filtroProcesso.adicionarParametro(new ComparacaoTexto(FiltroProcesso.DESCRICAO_ABREVIADA, descricaoAbreviada));
			peloMenosUmParametroInformado = true;
		}

		// verifica se pelo menos um parâmetro foi informado
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		filtroProcesso.adicionarParametro(new ParametroSimples(FiltroProcesso.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroProcesso.adicionarCaminhoParaCarregamentoEntidade("processoTipo");
		filtroProcesso.setCampoOrderBy(FiltroProcesso.DESCRICAO);

		// Faz a busca dos processos
		Collection colecaoProcesso = fachada.pesquisar(filtroProcesso, Processo.class.getName());

		if(colecaoProcesso == null || colecaoProcesso.isEmpty()){
			// Nenhum processo cadastrado de acordo com os parâmetros passados
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Processo");
		}

		// Paginação
		retorno = this.controlarPaginacao(httpServletRequest, retorno, colecaoProcesso.size());

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroProcesso, Processo.class.getName());
		colecaoProcesso = (Collection) resultado.get("colecaoRetorno");

		httpServletRequest.setAttribute("colecaoProcesso", colecaoProcesso);
		httpServletRequest.setAttribute("tipoConsulta", pesquisarProcessoActionForm.getProcessoIniciadoPrecedente());

		return retorno;
	}
}
