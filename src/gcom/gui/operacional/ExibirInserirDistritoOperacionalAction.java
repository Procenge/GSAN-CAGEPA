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

package gcom.gui.operacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.FiltroTipoUnidadeOperacional;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.TipoUnidadeOperacional;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0521] INSERIR Distrito Operacional
 * 
 * @author Eduardo Bianchi
 * @date 29/01/2007
 */

public class ExibirInserirDistritoOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirDistritoOperacional");
		Fachada fachada = Fachada.getInstancia();

		InserirDistritoOperacionalActionForm inserirDistritoOperacionalActionForm = (InserirDistritoOperacionalActionForm) actionForm;

		// atribui os valores q vem pelo request as variaveis
		String idLocalidade = (String) inserirDistritoOperacionalActionForm.getLocalidade();
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		// cria a colecao para receber a pesquisa
		Collection localidades = new HashSet();
		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
			filtroLocalidade.limparListaParametros();
			// coloca parametro no filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));
			// pesquisa
			localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidades != null && !localidades.isEmpty()){
				// Localidade foi encontrada
				inserirDistritoOperacionalActionForm.setLocalidade(Util.adicionarZerosEsquedaNumero(3, new Integer(
								((Localidade) ((List) localidades).get(0)).getId().toString()).toString()));
				inserirDistritoOperacionalActionForm.setDescricaoLocalidade(((Localidade) ((List) localidades).get(0)).getDescricao());
			}else{
				httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "true");
				inserirDistritoOperacionalActionForm.setLocalidade("");
			}
		}

		// Sistema Abastecimento
		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSistemaAbastecimento.setCampoOrderBy(FiltroSistemaAbastecimento.DESCRICAO);

		Collection colecaoSistemaAbastecimento = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class.getName());

		if(colecaoSistemaAbastecimento == null || colecaoSistemaAbastecimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Sistema Abastecimento");
		}

		httpServletRequest.setAttribute("colecaoSistemaAbastecimento", colecaoSistemaAbastecimento);

		// Tipo Unidade Operacional
		FiltroTipoUnidadeOperacional filtroTipoUnidadeOperacional = new FiltroTipoUnidadeOperacional();
		filtroTipoUnidadeOperacional.adicionarParametro(new ParametroSimples(FiltroTipoUnidadeOperacional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoTipoUnidadeOperacional = fachada.pesquisar(filtroTipoUnidadeOperacional, TipoUnidadeOperacional.class.getName());

		if(colecaoTipoUnidadeOperacional == null || colecaoTipoUnidadeOperacional.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Tipo Unidade Operacional");
		}

		httpServletRequest.setAttribute("colecaoTipoUnidadeOperacional", colecaoTipoUnidadeOperacional);

		// Recupera os parâmetros do sistema
		SistemaParametro sistemaParametro = getFachada().pesquisarParametrosDoSistema();

		// Recupera o nome abreviado empresa
		// sistema
		String nomeEmpresa = sistemaParametro.getNomeAbreviadoEmpresa();

		if(nomeEmpresa.equalsIgnoreCase("DESO")){

			HttpSession sessao = httpServletRequest.getSession(false);

			sessao.setAttribute("nomeEmpresaDistrito", nomeEmpresa);

		}

		return retorno;
	}
}
