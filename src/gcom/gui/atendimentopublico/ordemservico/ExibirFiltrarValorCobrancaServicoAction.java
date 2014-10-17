/**
 * 
 */
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

package gcom.gui.atendimentopublico.ordemservico;

import java.util.Collection;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 27/10/2006
 */
public class ExibirFiltrarValorCobrancaServicoAction
				extends GcomAction {

	/**
	 * [UC0392] Filtrar Valor de Cobrança do Serviço
	 * Este caso de uso cria um filtro que será usado na pesquisa do Valor de
	 * Cobrança de Serviço
	 * 
	 * @author Rômulo Aurélio
	 * @date 27/10/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);
		ActionForward retorno = actionMapping.findForward("filtrarValorCobrancaServico");

		Fachada fachada = Fachada.getInstancia();
		FiltrarValorCobrancaServicoActionForm filtrarValorCobrancaServicoActionForm = (FiltrarValorCobrancaServicoActionForm) actionForm;

		if(httpServletRequest.getParameter("menu") != null){
			filtrarValorCobrancaServicoActionForm.setIndicadorMedido("3");

		}
		filtrarValorCobrancaServicoActionForm.setAtualizar("1");

		this.getPerfilImovelCollection(sessao);
		this.getHidrometroCapacidadeCollection(sessao, fachada);
		if(filtrarValorCobrancaServicoActionForm.getTipoServico() != null
						&& !filtrarValorCobrancaServicoActionForm.getTipoServico().equals("")){
			this.getServicoTipo(filtrarValorCobrancaServicoActionForm, httpServletRequest);
		}

		return retorno;
	}

	/**
	 * Recupera Tipo de Serviço
	 * [FS0001] - Verificar Serviço Geral Débito
	 * 
	 * @author Rômulo Aurélio
	 * @date 29/10/2006
	 * @param filtrarRegistroAtendimentoActionForm
	 * @param fachada
	 */
	private void getServicoTipo(FiltrarValorCobrancaServicoActionForm form, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		// Filtra Tipo de Serviço
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, form.getTipoServico()));
		filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		// Recupera Tipo de Serviço
		Collection colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
		if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
			sessao.setAttribute("servicoTipoEncontrada", "true");
			ServicoTipo servicoTipo = (ServicoTipo) colecaoServicoTipo.iterator().next();
			form.setNomeTipoServico(servicoTipo.getDescricao());
			// if (servicoTipo.getDebitoTipo() == null) {
			// throw new ActionServletException(
			// "atencao.valor_cobranca_tipo_servico_sem_debito", null,
			// servicoTipo.getDescricao());
			// }
		}else{
			sessao.removeAttribute("servicoTipoEncontrada");
			form.setTipoServico("");
			form.setNomeTipoServico("Tipo de Serviço inexistente");
		}
	}

	/**
	 * Carrega a coleção de capacidade de hidrômetro
	 * 
	 * @author Rômulo Aurélio
	 * @date 29/10/2006
	 * @param sessao
	 */
	private void getHidrometroCapacidadeCollection(HttpSession sessao, Fachada fachada){

		// Filtro para o campo Capacidade do Hidrômetro
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.DESCRICAO);

		Collection colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());
		if(colecaoHidrometroCapacidade != null && !colecaoHidrometroCapacidade.isEmpty()){
			sessao.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Capacidade Hidrômetro");
		}
	}

	/**
	 * Carrega a coleção de perfil de imóvel
	 * 
	 * @author Rômulo Aurélio
	 * @date 29/10/2006
	 * @param sessao
	 */
	private void getPerfilImovelCollection(HttpSession sessao){

		Fachada fachada = Fachada.getInstancia();
		// Filtro para o campo Perfil do Imóvel
		FiltroImovelPerfil filtroPerfilImovel = new FiltroImovelPerfil();
		filtroPerfilImovel
						.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPerfilImovel.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);

		Collection colecaoImovelPerfil = fachada.pesquisar(filtroPerfilImovel, ImovelPerfil.class.getName());
		if(colecaoImovelPerfil != null && !colecaoImovelPerfil.isEmpty()){
			sessao.setAttribute("colecaoImovelPerfil", colecaoImovelPerfil);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Perfil do Imóvel");
		}
	}

}