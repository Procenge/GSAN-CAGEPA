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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
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
 * @author isilva
 */
public class SelecionarContaRevisaoAction
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

		// Seta a ação de retorno
		ActionForward retorno = actionMapping.findForward("selecionarContaRevisao");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		sessao.removeAttribute("closePage");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm = (SelecionarContaRevisaoActionForm) actionForm;

		if(!Util.isVazioOuBranco((String) httpServletRequest.getParameter("cancelar"))
						&& "S".equalsIgnoreCase(((String) httpServletRequest.getParameter("cancelar")))){

			selecionarContaRevisaoActionForm.limparEnvioRevisao();
			sessao.setAttribute("closePage", "S");

		}else{

			String codigoImovel = null;

			if(!Util.isVazioOuBranco((String) httpServletRequest.getParameter("codigoImovel"))){
				codigoImovel = (String) httpServletRequest.getParameter("codigoImovel");
				selecionarContaRevisaoActionForm.setCodigoImovel(codigoImovel);
			}else{
				codigoImovel = (String) selecionarContaRevisaoActionForm.getCodigoImovel();
			}

			if(Util.isVazioOuBranco(codigoImovel)){
				throw new ActionServletException("atencao.informe_campo", null, "Imóvel");
			}

			// if (!Util.isVazioOuBranco((String)httpServletRequest.getParameter("especificacao")))
			// {
			// selecionarContaRevisaoActionForm.setEspecificacao((String)httpServletRequest.getParameter("especificacao"));
			// }

			String[] selecaoConta = selecionarContaRevisaoActionForm.getContaSelectID();

			if(selecaoConta == null || selecaoConta.length < 1){
				throw new ActionServletException("atencao.conta.nao_selecionadas");
			}else{

				selecionarContaRevisaoActionForm.setCodigoImovel(codigoImovel);

				ContaMotivoRevisao contaMotivoRevisao = null;

				if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getIdContaMotivoRevisao())
								&& !selecionarContaRevisaoActionForm.getIdContaMotivoRevisao().equalsIgnoreCase(
												"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					contaMotivoRevisao = pesquisarContaMotivoRevisao(fachada, selecionarContaRevisaoActionForm);

					if(contaMotivoRevisao == null){
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Motivo da Revisão");
					}
				}else{
					throw new ActionServletException("atencao.informe_campo", null, "Motivo da Revisão");
				}

				atualizarColecaoContas(sessao, selecionarContaRevisaoActionForm, selecaoConta, contaMotivoRevisao);

				sessao.setAttribute("closePage", "S");
			}

		}

		return retorno;
	}

	/**
	 * @author isilva
	 * @param sessao
	 * @param selecionarContaRevisaoActionForm
	 * @param selecaoConta
	 * @param contaMotivoRevisao
	 */
	@SuppressWarnings("unchecked")
	private void atualizarColecaoContas(HttpSession sessao, SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm,
					String[] selecaoConta, ContaMotivoRevisao contaMotivoRevisao){

		selecionarContaRevisaoActionForm.setContaMotivoRevisao(contaMotivoRevisao);

		Collection<Conta> colecaoContaImovelParaRevisao = new ArrayList<Conta>();
		Collection<Conta> colecaoContaImovel = (Collection<Conta>) sessao.getAttribute("colecaoContaImovel");

		if(selecaoConta != null && selecaoConta.length > 0){

			StringBuffer select = new StringBuffer("");
			selecionarContaRevisaoActionForm.setContaSelectAuxID("");

			for(String selecionado : selecaoConta){

				if(!Util.isVazioOuBranco(select.toString())){
					select.append(",");
				}

				select.append(selecionado);
			}

			selecionarContaRevisaoActionForm.setContaSelectAuxID(select.toString());
		}

		if(colecaoContaImovel != null && !colecaoContaImovel.isEmpty()){

			String[] idsUltimAlteracaoColecao = selecionarContaRevisaoActionForm.getContaSelectAuxID().split(",");

			for(String idUltimAlteracaoColecao : idsUltimAlteracaoColecao){

				String[] idUltimAlteracao = idUltimAlteracaoColecao.split("-");

				for(Conta conta : colecaoContaImovel){
					if(Integer.valueOf(idUltimAlteracao[0]).intValue() == conta.getId().intValue()){
						colecaoContaImovelParaRevisao.add(conta);
					}
				}
			}
		}

		selecionarContaRevisaoActionForm.setColecaoContas(colecaoContaImovelParaRevisao);
	}

	/**
	 * @author isilva
	 * @param fachada
	 * @param selecionarContaRevisaoActionForm
	 * @return
	 */
	private ContaMotivoRevisao pesquisarContaMotivoRevisao(Fachada fachada,
					SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm){

		ContaMotivoRevisao contaMotivoRevisao;
		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao();
		filtroContaMotivoRevisao.adicionarParametro(new ParametroSimples(FiltroContaMotivoRevisao.ID, Integer
						.valueOf(selecionarContaRevisaoActionForm.getIdContaMotivoRevisao())));
		filtroContaMotivoRevisao.adicionarParametro(new ParametroSimples(FiltroContaMotivoRevisao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		contaMotivoRevisao = (ContaMotivoRevisao) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroContaMotivoRevisao,
						ContaMotivoRevisao.class.getName()));
		return contaMotivoRevisao;
	}
}
