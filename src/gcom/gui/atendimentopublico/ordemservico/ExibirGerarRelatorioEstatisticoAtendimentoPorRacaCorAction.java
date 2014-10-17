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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.cliente.FiltroRaca;
import gcom.cadastro.cliente.Raca;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Hiroshi Gonçalves
 * @date 14/02/2014
 */
public class ExibirGerarRelatorioEstatisticoAtendimentoPorRacaCorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioEstatisticoAtendimentoPorRacaCor");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		// Form
		GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm form = (GerarRelatorioEstatisticoAtendimentoPorRacaCorActionForm) actionForm;

		// Consulta descricao da Unidade
		if(form.getUnidadeAtendimento() != null && !form.getUnidadeAtendimento().equals("")){

			UnidadeOrganizacional unidadeAtendimento = (UnidadeOrganizacional) fachada.pesquisar(
							Integer.parseInt(form.getUnidadeAtendimento()), UnidadeOrganizacional.class);

			if(unidadeAtendimento != null){
				form.setDsUnidadeAtendimento(unidadeAtendimento.getDescricao());
			}else{
				throw new ActionServletException("atencao.unidadeOrganizacional.inexistente");
			}
		}

		// Consulta localidade
		if(form.getLocalidade() != null && !form.getLocalidade().equals("")){

			Localidade localidade = (Localidade) fachada.pesquisar(Integer.parseInt(form.getLocalidade()), Localidade.class);

			if(localidade != null){
				form.setDsLocalidade(localidade.getDescricao());
			}else{
				throw new ActionServletException("atencao.localidade.inexistente");
			}
		}

		// Raça
		this.getRaca(sessao, fachada);

		// Tipo Solicitação
		getSolicitacaoTipoCollection(sessao, fachada);

		// Especificação
		if(form.getTipoSolicitacao() != null && form.getTipoSolicitacao().length == 1){

			this.getSolicitacaoTipoEspecificacao(sessao, fachada, form.getTipoSolicitacao());
		}else{

			sessao.removeAttribute("colecaoEspecificacao");
		}

		// Gerencia Regional
		this.getGerenciaRegional(sessao, fachada);

		return retorno;
	}

	/**
	 * Carrega coleção de solicitação tipo
	 * 
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoCollection(HttpSession sessao, Fachada fachada){

		// Filtra Solicitação Tipo
		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
		filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);

		Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		if(colecaoSolicitacaoTipo != null && !colecaoSolicitacaoTipo.isEmpty()){

			sessao.setAttribute("colecaoTipoSolicitacao", colecaoSolicitacaoTipo);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Especificação");
		}
	}

	private void getRaca(HttpSession sessao, Fachada fachada){

		// Filtra Raca
		FiltroRaca filtroRaca = new FiltroRaca();
		filtroRaca.setCampoOrderBy(FiltroRaca.DESCRICAO);

		Collection colecaoRaca = fachada.pesquisar(filtroRaca, Raca.class.getName());

		if(colecaoRaca != null && !colecaoRaca.isEmpty()){

			sessao.setAttribute("colecaoRaca", colecaoRaca);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Raça");
		}
	}

	private void getGerenciaRegional(HttpSession sessao, Fachada fachada){

		// Filtra Raca
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);

		Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegional != null && !colecaoGerenciaRegional.isEmpty()){

			sessao.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}else{

			throw new ActionServletException("atencao.naocadastrado", null, "Gerência Regional");
		}
	}

	/**
	 * Carrega as especificações dos tipos de solicitação selecionados.
	 * 
	 * @param sessao
	 * @param fachada
	 */
	private void getSolicitacaoTipoEspecificacao(HttpSession sessao, Fachada fachada, Integer[] arSolicitacaoTipo){

		if(arSolicitacaoTipo != null && arSolicitacaoTipo.length == 1){
			// Filtra Solicitação Tipo Especificação
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, arSolicitacaoTipo[0]));
			filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
			filtroSolicitacaoTipoEspecificacao.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipo");

			Collection colecaoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
							SolicitacaoTipoEspecificacao.class.getName());

			sessao.setAttribute("colecaoEspecificacao", colecaoEspecificacao);
		}

	}
}