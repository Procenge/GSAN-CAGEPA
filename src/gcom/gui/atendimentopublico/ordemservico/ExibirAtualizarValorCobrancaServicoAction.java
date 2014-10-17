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

import gcom.atendimentopublico.ordemservico.FiltroServicoCobrancaValor;
import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

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
 * @date 30/10/2006
 */
public class ExibirAtualizarValorCobrancaServicoAction
				extends GcomAction {

	/**
	 * [UC0393] Atualizar Valor de Cobrança do Serviço
	 * Este caso de uso permite alterar um valor de cobrança de serviço
	 * 
	 * @author Rômulo Aurélio
	 * @date 31/10/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarValorCobrancaServico");

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarValorCobrancaServicoActionForm atualizarValorCobrancaServicoActionForm = (AtualizarValorCobrancaServicoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		this.getPerfilImovelCollection(sessao);

		this.getHidrometroCapacidadeCollection(sessao, fachada);

		String id = null;

		String idServicoCobrancaValor = null;

		if(httpServletRequest.getParameter("idServicoCobrancaValor") != null
						&& !httpServletRequest.getParameter("idServicoCobrancaValor").equals("")){

			sessao.removeAttribute("objetoServicoCobrancaValor");
			sessao.removeAttribute("colecaoServicoCobrancaValorTela");

		}

		// Verifica se veio do filtrar ou do manter

		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		// Verifica se o servicoCobrancaValor já está na sessão, em caso
		// afirmativo
		// significa que o usuário já entrou na tela e apenas selecionou algum
		// item que deu um reload na tela e em caso negativo significa que ele
		// está entrando pela primeira vez

		if(sessao.getAttribute("colecaoServicoCobrancaValorTela") == null){

			if(sessao.getAttribute("objetoServicoCobrancaValor") != null){

				ServicoCobrancaValor servicoCobrancaValor = (ServicoCobrancaValor) sessao.getAttribute("objetoServicoCobrancaValor");

				sessao.setAttribute("idServicoCobrancaValor", servicoCobrancaValor.getId().toString());

				atualizarValorCobrancaServicoActionForm.setIdServicoCobrancaValor(servicoCobrancaValor.getId().toString());

				atualizarValorCobrancaServicoActionForm.setTipoServico(servicoCobrancaValor.getServicoTipo().getId().toString());

				atualizarValorCobrancaServicoActionForm.setNomeTipoServico(servicoCobrancaValor.getServicoTipo().getDescricao());

				atualizarValorCobrancaServicoActionForm.setCapacidadeHidrometro(servicoCobrancaValor.getHidrometroCapacidade().getId()
								.toString());

				atualizarValorCobrancaServicoActionForm.setPerfilImovel(servicoCobrancaValor.getImovelPerfil().getId().toString());

				atualizarValorCobrancaServicoActionForm.setIndicadorMedido("" + servicoCobrancaValor.getIndicadorMedido());

				atualizarValorCobrancaServicoActionForm.setValorServico("" + servicoCobrancaValor.getValor());

				id = servicoCobrancaValor.getId().toString();

				sessao.setAttribute("servicoCobrancaValorAtualizar", servicoCobrancaValor);
				sessao.removeAttribute("objetoServicoCobrancaValor");

			}else{

				ServicoCobrancaValor servicoCobrancaValor = null;

				idServicoCobrancaValor = null;

				if(httpServletRequest.getParameter("idServicoCobrancaValor") == null
								|| httpServletRequest.getParameter("idServicoCobrancaValor").equals("")){
					servicoCobrancaValor = (ServicoCobrancaValor) sessao.getAttribute("objetoservicoCobrancaValor");
				}else{
					idServicoCobrancaValor = (String) httpServletRequest.getParameter("idServicoCobrancaValor");
					sessao.setAttribute("idServicoCobrancaValor", idServicoCobrancaValor);
				}

				httpServletRequest.setAttribute("idServicoCobrancaValor", idServicoCobrancaValor);

				if(idServicoCobrancaValor != null){

					id = idServicoCobrancaValor;

					FiltroServicoCobrancaValor filtroServicoCobrancaValor = new FiltroServicoCobrancaValor();
					filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");

					filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

					filtroServicoCobrancaValor.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.ID,
									idServicoCobrancaValor));

					Collection<ServicoCobrancaValor> colecaoServicoCobrancaValor = fachada.pesquisar(filtroServicoCobrancaValor,
									ServicoCobrancaValor.class.getName());

					if(colecaoServicoCobrancaValor == null || colecaoServicoCobrancaValor.isEmpty()){
						throw new ActionServletException("atencao.atualizacao.timestamp");
					}

					httpServletRequest.setAttribute("colecaoServicoCobrancaValor", colecaoServicoCobrancaValor);

					servicoCobrancaValor = (ServicoCobrancaValor) colecaoServicoCobrancaValor.iterator().next();

				}

				atualizarValorCobrancaServicoActionForm.setIdServicoCobrancaValor(servicoCobrancaValor.getId().toString());

				atualizarValorCobrancaServicoActionForm.setTipoServico(servicoCobrancaValor.getServicoTipo().getId().toString());

				atualizarValorCobrancaServicoActionForm.setNomeTipoServico(servicoCobrancaValor.getServicoTipo().getDescricao());

				if(servicoCobrancaValor.getHidrometroCapacidade() != null){
					atualizarValorCobrancaServicoActionForm.setCapacidadeHidrometro(servicoCobrancaValor.getHidrometroCapacidade().getId()
									.toString());
				}else{
					atualizarValorCobrancaServicoActionForm.setCapacidadeHidrometro("");
				}

				if(servicoCobrancaValor.getImovelPerfil() != null){
					atualizarValorCobrancaServicoActionForm.setPerfilImovel(servicoCobrancaValor.getImovelPerfil().getId().toString());
				}else{

					atualizarValorCobrancaServicoActionForm.setPerfilImovel("");
				}

				atualizarValorCobrancaServicoActionForm.setIndicadorMedido("" + servicoCobrancaValor.getIndicadorMedido());

				atualizarValorCobrancaServicoActionForm.setValorServico("" + servicoCobrancaValor.getValor());

				sessao.setAttribute("servicoCobrancaValorAtualizar", servicoCobrancaValor);

			}
		}

		// -------------- bt DESFAZER ---------------

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			sessao.removeAttribute("colecaoServicoCobrancaValorTela");

			String servicoCobrancaValorID = null;

			if(httpServletRequest.getParameter("idServicoCobrancaValor") == null
							|| httpServletRequest.getParameter("idServicoCobrancaValor").equals("")){
				servicoCobrancaValorID = (String) sessao.getAttribute("idServicoCobrancaValor");
			}else{
				servicoCobrancaValorID = (String) httpServletRequest.getParameter("idServicoCobrancaValor");
				sessao.setAttribute("idServicoCobrancaValor", servicoCobrancaValorID);
			}

			if(servicoCobrancaValorID.equalsIgnoreCase("")){
				servicoCobrancaValorID = null;
			}

			if((servicoCobrancaValorID == null) && (id == null)){

				ServicoCobrancaValor servicoCobrancaValor = (ServicoCobrancaValor) sessao.getAttribute("objetoServicoCobrancaValor");

				atualizarValorCobrancaServicoActionForm.setIdServicoCobrancaValor(servicoCobrancaValor.getId().toString());

				atualizarValorCobrancaServicoActionForm.setTipoServico(servicoCobrancaValor.getServicoTipo().getId().toString());

				atualizarValorCobrancaServicoActionForm.setNomeTipoServico(servicoCobrancaValor.getServicoTipo().getDescricao());

				atualizarValorCobrancaServicoActionForm.setCapacidadeHidrometro(servicoCobrancaValor.getHidrometroCapacidade().getId()
								.toString());

				atualizarValorCobrancaServicoActionForm.setPerfilImovel(servicoCobrancaValor.getImovelPerfil().getId().toString());

				atualizarValorCobrancaServicoActionForm.setIndicadorMedido("" + servicoCobrancaValor.getIndicadorMedido());

				atualizarValorCobrancaServicoActionForm.setValorServico("" + servicoCobrancaValor.getValor());

				sessao.setAttribute("servicoCobrancaValorAtualizar", servicoCobrancaValor);
				sessao.removeAttribute("servicoCobrancaValor");
			}

			if((idServicoCobrancaValor == null) && (id != null)){

				idServicoCobrancaValor = id;
			}

			if(idServicoCobrancaValor != null){

				FiltroServicoCobrancaValor filtroServicoCobrancaValor = new FiltroServicoCobrancaValor();
				filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");

				filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");

				filtroServicoCobrancaValor.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.ID, idServicoCobrancaValor));

				Collection<ServicoCobrancaValor> colecaoServicoCobrancaValor = fachada.pesquisar(filtroServicoCobrancaValor,
								ServicoCobrancaValor.class.getName());

				if(colecaoServicoCobrancaValor == null || colecaoServicoCobrancaValor.isEmpty()){
					throw new ActionServletException("atencao.atualizacao.timestamp");
				}

				httpServletRequest.setAttribute("colecaoServicoCobrancaValor", colecaoServicoCobrancaValor);

				ServicoCobrancaValor servicoCobrancaValor = (ServicoCobrancaValor) colecaoServicoCobrancaValor.iterator().next();

				atualizarValorCobrancaServicoActionForm.setIdServicoCobrancaValor(servicoCobrancaValor.getId().toString());

				atualizarValorCobrancaServicoActionForm.setTipoServico(servicoCobrancaValor.getServicoTipo().getId().toString());

				atualizarValorCobrancaServicoActionForm.setNomeTipoServico(servicoCobrancaValor.getServicoTipo().getDescricao());

				atualizarValorCobrancaServicoActionForm.setCapacidadeHidrometro(servicoCobrancaValor.getHidrometroCapacidade().getId()
								.toString());

				atualizarValorCobrancaServicoActionForm.setPerfilImovel(servicoCobrancaValor.getImovelPerfil().getId().toString());

				atualizarValorCobrancaServicoActionForm.setIndicadorMedido("" + servicoCobrancaValor.getIndicadorMedido());

				atualizarValorCobrancaServicoActionForm.setValorServico("" + servicoCobrancaValor.getValor());

				httpServletRequest.setAttribute("idServicoCobrancaValor", idServicoCobrancaValor);
				sessao.setAttribute("servicoCobrancaValorAtualizar", servicoCobrancaValor);

			}
		}
		// -------------- bt DESFAZER ---------------

		httpServletRequest.setAttribute("colecaoServicoCobrancaValorTela", sessao.getAttribute("colecaoServicoCobrancaValorTela"));

		return retorno;

	}

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

}
