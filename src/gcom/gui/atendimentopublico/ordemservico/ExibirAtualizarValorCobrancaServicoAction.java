/**
 * 
 */
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
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 30/10/2006
 */
public class ExibirAtualizarValorCobrancaServicoAction
				extends GcomAction {

	/**
	 * [UC0393] Atualizar Valor de Cobran�a do Servi�o
	 * Este caso de uso permite alterar um valor de cobran�a de servi�o
	 * 
	 * @author R�mulo Aur�lio
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

		// Verifica se o servicoCobrancaValor j� est� na sess�o, em caso
		// afirmativo
		// significa que o usu�rio j� entrou na tela e apenas selecionou algum
		// item que deu um reload na tela e em caso negativo significa que ele
		// est� entrando pela primeira vez

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
		// Filtro para o campo Perfil do Im�vel
		FiltroImovelPerfil filtroPerfilImovel = new FiltroImovelPerfil();
		filtroPerfilImovel
						.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroPerfilImovel.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);

		Collection colecaoImovelPerfil = fachada.pesquisar(filtroPerfilImovel, ImovelPerfil.class.getName());
		if(colecaoImovelPerfil != null && !colecaoImovelPerfil.isEmpty()){
			sessao.setAttribute("colecaoImovelPerfil", colecaoImovelPerfil);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Perfil do Im�vel");
		}
	}

	private void getHidrometroCapacidadeCollection(HttpSession sessao, Fachada fachada){

		// Filtro para o campo Capacidade do Hidr�metro
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.DESCRICAO);

		Collection colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());
		if(colecaoHidrometroCapacidade != null && !colecaoHidrometroCapacidade.isEmpty()){
			sessao.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Capacidade Hidr�metro");
		}
	}

}
