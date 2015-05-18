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

package gcom.gui.micromedicao.hidrometro;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.*;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @created 1 de Setembro de 2005
 */
public class ExibirFiltrarHidrometroAction
				extends GcomAction {

	/**
	 * < <Descrição do método>>
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

		// Obtém o action form
		HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;

		Collection colecaoHidrometroCapacidade = null;

		String tela = (String) httpServletRequest.getParameter("tela");

		// Seta a ação de retorno
		ActionForward retorno = actionMapping.findForward("filtrarHidrometro");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém a facahda
		Fachada fachada = Fachada.getInstancia();

		// Obtém o objetoCosulta vindo na sessão
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		// Verifica se o objeto é diferente de nulo
		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("") && (Integer.parseInt(objetoConsulta)) == 1){

			// Filtro para obter o local de armazenagem ativo de id informado
			FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();

			filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.ID, new Integer(
							hidrometroActionForm.getIdLocalArmazenagem()), ParametroSimples.CONECTOR_AND));
			filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroLocalArmazenagem.setCampoOrderBy(FiltroHidrometroLocalArmazenagem.DESCRICAO);

			// Pesquisa de acordo com os parâmetros informados no filtro
			Collection colecaoHidrometroLocalArmazenagem = fachada.pesquisar(filtroHidrometroLocalArmazenagem,
							HidrometroLocalArmazenagem.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a coleção
			if(colecaoHidrometroLocalArmazenagem != null && !colecaoHidrometroLocalArmazenagem.isEmpty()){

				// Obtém o objeto da coleção pesquisada
				HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util
								.retonarObjetoDeColecao(colecaoHidrometroLocalArmazenagem);

				// Exibe o código e a descrição pesquisa na página
				httpServletRequest.setAttribute("corLocalArmazenagem", "valor");
				hidrometroActionForm.setIdLocalArmazenagem(hidrometroLocalArmazenagem.getId().toString());
				hidrometroActionForm.setLocalArmazenagemDescricao(hidrometroLocalArmazenagem.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "fixo");

			}else{

				// Exibe mensagem de código inexiste e limpa o campo de código
				httpServletRequest.setAttribute("corLocalArmazenagem", "exception");
				hidrometroActionForm.setIdLocalArmazenagem("");
				hidrometroActionForm.setLocalArmazenagemDescricao("LOCAL DE ARMAZENAGEM INEXISTENTE");

				httpServletRequest.setAttribute("nomeCampo", "idLocalArmazenagem");

			}
			// Verifica se os objetos estão na sessão
		}else{
			sessao.setAttribute("tela", tela);
		}
		if(sessao.getAttribute("colecaoHidrometroClasseMetrologica") == null && sessao.getAttribute("colecaoHidrometroMarca") == null
						&& sessao.getAttribute("colecaoHidrometroDiametro") == null
						&& sessao.getAttribute("colecaoHidrometroCapacidade") == null
						&& sessao.getAttribute("colecaoHidrometroTipo") == null
						&& sessao.getAttribute("colecaoHidrometroTipoTurbina") == null){

			hidrometroActionForm.setIndicadorMacromedidor("-1");

			// Filtro de hidrômetro classe metrológica para obter todas as
			// classes metrológicas ativas
			FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();

			filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroClasseMetrologica.setCampoOrderBy(FiltroHidrometroClasseMetrologica.DESCRICAO);

			// Pesquisa a coleção de classe metrológica
			Collection colecaoHidrometroClasseMetrologica = fachada.pesquisar(filtroHidrometroClasseMetrologica,
							HidrometroClasseMetrologica.class.getName());

			// Filtro de hidrômetro marca para obter todas as marcas de
			// hidrômetros ativas
			FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();

			filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroMarca.setCampoOrderBy(FiltroHidrometroMarca.DESCRICAO);

			// Pesquisa a coleção de hidrômetro marca
			Collection colecaoHidrometroMarca = fachada.pesquisar(filtroHidrometroMarca, HidrometroMarca.class.getName());

			// Filtro de hidrômetro diâmetro para obter todos os diâmetros de
			// hidrômetros ativos
			FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();

			filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroDiametro.setCampoOrderBy(FiltroHidrometroDiametro.NUMERO_ORDEM);

			// Pesquisa a coleção de hidrômetro capacidade
			Collection colecaoHidrometroDiametro = fachada.pesquisar(filtroHidrometroDiametro, HidrometroDiametro.class.getName());

			// Filtro de hidrômetro capacidade para obter todos as capacidade de
			// hidrômetros ativas
			FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

			filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.CODIGO_HIDROMETRO_CAPACIDADE);

			// Pesquisa a coleção de hidrômetro capacidade
			colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());

			// Filtro de hidrômetro tipo para obter todos os tipos de
			// hidrômetros ativos
			FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();

			filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroTipo.setCampoOrderBy(FiltroHidrometroTipo.DESCRICAO);

			// Pesquisa a coleção de hidrômetro tipo
			Collection colecaoHidrometroTipo = fachada.pesquisar(filtroHidrometroTipo, HidrometroTipo.class.getName());

			// Filtro de hidrômetro situação para obter todos os tipos de
			// hidrômetros ativos
			FiltroHidrometroSituacao filtroHidrometroSituacao = new FiltroHidrometroSituacao();

			filtroHidrometroSituacao.adicionarParametro(new ParametroSimples(FiltroHidrometroSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroSituacao.setCampoOrderBy(FiltroHidrometroTipo.DESCRICAO);

			// Pesquisa a coleção de hidrômetro tipo
			Collection colecaoHidrometroSituacao = fachada.pesquisar(filtroHidrometroSituacao, HidrometroSituacao.class.getName());

			// hidrometroActionForm
			// .setIndicadorMacromedidor(Hidrometro.INDICADOR_COMERCIAL
			// .toString());

			// Tipos de Turbina de Hidrômetro
			FiltroHidrometroTipoTurbina filtroHidrometroTipoTurbina = new FiltroHidrometroTipoTurbina();
			filtroHidrometroTipoTurbina.adicionarParametro(new ParametroSimples(FiltroHidrometroTipoTurbina.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroTipoTurbina.setCampoOrderBy(FiltroHidrometroTipoTurbina.DESCRICAO);

			Collection<HidrometroTipoTurbina> colecaoHidrometroTipoTurbina = fachada.pesquisar(filtroHidrometroTipoTurbina,
							HidrometroTipoTurbina.class.getName());

			// Formato da Numeração do Hidrômetro
			hidrometroActionForm.setCodigoFormatoNumeracao(Hidrometro.FORMATO_NUMERACAO_4_X_6.toString());

			// Envia as coleções na sessão
			sessao.setAttribute("colecaoHidrometroClasseMetrologica", colecaoHidrometroClasseMetrologica);
			sessao.setAttribute("colecaoHidrometroMarca", colecaoHidrometroMarca);
			sessao.setAttribute("colecaoHidrometroDiametro", colecaoHidrometroDiametro);
			sessao.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);
			sessao.setAttribute("colecaoHidrometroSituacao", colecaoHidrometroSituacao);
			sessao.setAttribute("colecaoHidrometroTipoTurbina", colecaoHidrometroTipoTurbina);
			sessao.setAttribute("colecaoHidrometroTipo", colecaoHidrometroTipo);
		}

		httpServletRequest.removeAttribute("i");

		String atualizar = httpServletRequest.getParameter("atualizar");
		String menu = httpServletRequest.getParameter("menu");

		if(atualizar == null && menu == null){
			boolean i = true;
			httpServletRequest.setAttribute("i", i);
		}
		httpServletRequest.setAttribute("nomeCampo", "numeroHidrometro");

		httpServletRequest.setAttribute("idSituacaoHidrometroInstalado", HidrometroSituacao.INSTALADO.toString());

		return retorno;
	}

}
