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

package gcom.gui.cadastro.unidadeoperacional;

import java.util.Collection;

import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidadeoperacional.UnidadeOperacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Péricles Tavares
 */

public class AtualizarUnidadeOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarUnidadeOperacionalActionForm atualizarUnidadeOperacionalActionForm = (AtualizarUnidadeOperacionalActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		// Verifica se a localidade existe na base de dados.
		if(atualizarUnidadeOperacionalActionForm.getLocalidade() != null){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer
							.parseInt(atualizarUnidadeOperacionalActionForm.getLocalidade())));
			Collection<Localidade> localidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidade == null || localidade.isEmpty()){
				throw new ActionServletException("atencao.localidade.inexistente");
			}else{
				Localidade localidadeBase = (Localidade) Util.retonarObjetoDeColecao(localidade);
				atualizarUnidadeOperacionalActionForm.setLocalidade(localidadeBase.getId().toString());
				atualizarUnidadeOperacionalActionForm.setDescricaoLocalidade(localidadeBase.getDescricao());
			}
		}

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		UnidadeOperacional unidadeOperacional = (UnidadeOperacional) sessao.getAttribute("unidadeOperacional");

		String[] idMunicipio = new String[1];

		idMunicipio[0] = unidadeOperacional.getId().toString();

		// Atualiza a entidade com os valores do formulário
		atualizarUnidadeOperacionalActionForm.setFormValues(unidadeOperacional);
		if(sessao.getAttribute("colecaoEnderecos") != null){
			Collection colecaoEndereco = (Collection) sessao.getAttribute("colecaoEnderecos");
			if(!colecaoEndereco.isEmpty()){
				UnidadeOperacional unidadeEndereco = (UnidadeOperacional) colecaoEndereco.iterator().next();
				unidadeOperacional.setCep(unidadeEndereco.getCep());
				unidadeOperacional.setLogradouro(unidadeEndereco.getLogradouro());
				unidadeOperacional.setBairro(unidadeEndereco.getBairro());
				unidadeOperacional.setEnderecoReferencia(unidadeEndereco.getEnderecoReferencia());
				unidadeOperacional.setNumeroImovel(unidadeEndereco.getNumeroImovel());
				unidadeOperacional.setComplementoEndereco(unidadeEndereco.getComplementoEndereco());
			}
		}else{
			unidadeOperacional.setCep(null);
			unidadeOperacional.setLogradouro(null);
			unidadeOperacional.setBairro(null);
			unidadeOperacional.setEnderecoReferencia(null);
			unidadeOperacional.setNumeroImovel(null);
			unidadeOperacional.setComplementoEndereco(null);
		}

		// atualiza na base de dados de Municipio
		fachada.atualizarUnidadeOperacional(unidadeOperacional, usuarioLogado);

		// Monta a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Unidade Operacional de código "
						+ atualizarUnidadeOperacionalActionForm.getCodigoUnidadeOperacional() + " atualizada com sucesso.",
						"Realizar outra Manutenção de Unidade Operacional", "exibirManterUnidadeOperacionalAction.do?menu=sim");

		return retorno;
	}

}
