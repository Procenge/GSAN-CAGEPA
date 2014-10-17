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
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.util.filtro.ParametroSimplesColecao;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 */
public class MovimentarHidrometroAction
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

		// Define ação de retorno
		ActionForward retorno = actionMapping.findForward("exibirConfirmarMovimentarHidrometro");

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		// Obtém a facahda
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		// HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém os ids para movimentação
		String[] idsRegistro = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		Collection<Integer> ids = new ArrayList<Integer>();
		for(String id : idsRegistro){
			ids.add(Integer.valueOf(id));
		}

		// Mensagem de erro quando o usuário tenta excluir sem ter selecionado nenhum registro
		if(ids == null || ids.size() == 0){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		// Collection colecaoHidrometro = (Collection) sessao.getAttribute("colecaoHidrometro");

		FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
		filtroHidrometro.adicionarParametro(new ParametroSimplesColecao(FiltroHidrometro.ID, ids));
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_LOCAL_ARMAZENAGEM);

		Collection colecaoHidrometroSelecionado = fachada.pesquisar(filtroHidrometro, Hidrometro.class.getName());

		// Collection colecaoHidrometroSelecionado =
		// fachada.obterColecaoObjetosSelecionados(colecaoHidrometro, ids);

		// Obtém a quantidade de registros selecionados
		String numeroHidrometrosSelecionados = new Integer(ids.size()).toString();

		// Verifca se existe uniformidade dos locais de armazenagem
		// e verifica se existe armazenagem local do Hidrometro
		String verificacao = fachada.verificarLocalArmazenagemSituacao(colecaoHidrometroSelecionado);

		// Caso Exista, hidrometros sem Local Armazenagem Origem
		if(verificacao.equals("hidrometroSemArmazenagemLocal")){
			throw new ActionServletException("atencao.hidrometros.selecionados.nao.contem.local.armazenagem.local");
		}

		// Caso o local de armazenagem seja diferente entre os hidrômetros selecionados
		if(verificacao.equals("localArmazenagemDiferente")){
			throw new ActionServletException("atencao.hidrometros.selecionados.nao.uniformidade.local.armazenagem");
		}

		// Caso os hidrômetros selecionados estejam com algum instalado
		if(verificacao.equals("hidrometroInstalado")){
			throw new ActionServletException("atencao.nao.possivel.movimentar.hidrometros.instalados");
		}

		httpServletRequest.setAttribute("numeroHidrometrosSelecionados", numeroHidrometrosSelecionados);
		httpServletRequest.setAttribute("colecaoHidrometroSelecionado", colecaoHidrometroSelecionado);

		return retorno;
	}
}