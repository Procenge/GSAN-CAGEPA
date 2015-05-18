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

package gcom.gui.cobranca.cobrancaadministrativa;

import gcom.cadastro.imovel.ImovelCobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3060] Consultar Imóvel Cobrança Administrativa
 * [SB0003] - Retirar Imóvel da Cobrança Administrativa
 * 
 * @author Anderson Italo
 * @date 17/09/2012
 */
public class RetirarImovelCobrancaAdministrativaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		RetirarImovelCobrancaAdministrativaActionForm form = (RetirarImovelCobrancaAdministrativaActionForm) actionForm;
		ActionForward retorno = actionMapping.findForward("informarMotivoRetiradaCobrancaAdministrativa");
		Fachada fachada = Fachada.getInstancia();

		// Motivo da Retirada
		Integer motivoRetirada = Util.converterStringParaInteger(form.getIdMotivoRetirada());

		// Ids dos Imóveis Cobrança Situação Selecionados
		
		String[] idRegistrosRetirada = null;
		if(httpServletRequest.getParameter("todos") == null || httpServletRequest.getParameter("todos").equals("0")){
			idRegistrosRetirada = (String[]) this.getSessao(httpServletRequest).getAttribute("idRegistrosRetirada");
		}else{
			Collection<ImovelCobrancaSituacao> colecaoImovelCobrancaSituacaoTodos = (Collection<ImovelCobrancaSituacao>) this.getSessao(
							httpServletRequest).getAttribute("colecaoImovelCobrancaSituacaoTodos");

			if(colecaoImovelCobrancaSituacaoTodos != null){

				String[] idRegistrosRetiradaAux = new String[colecaoImovelCobrancaSituacaoTodos.size()];

				int i = 0;

				for(ImovelCobrancaSituacao imovelCobrancaSituacao : colecaoImovelCobrancaSituacaoTodos){
					if(imovelCobrancaSituacao.getDataRetiradaCobranca() == null){
						idRegistrosRetiradaAux[i] = imovelCobrancaSituacao.getId().toString();
						i++;
					}
				}

				idRegistrosRetirada = new String[i];
				for(int j = 0; j < i; j++){
					idRegistrosRetirada[j] = idRegistrosRetiradaAux[j];
				}

			}
		}

		if(Util.isVazioOrNulo(idRegistrosRetirada)){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		if(idRegistrosRetirada != null){

			List<Integer> idsImovelCobrancaSituacao = new ArrayList<Integer>();
			for(String id : idRegistrosRetirada){

				idsImovelCobrancaSituacao.add(new Integer(id));
			}

			fachada.encerrarCobrancaAdministrativaImovel(idsImovelCobrancaSituacao, motivoRetirada, getUsuarioLogado(httpServletRequest));
		}

		this.getSessao(httpServletRequest).setAttribute("closePage", "S");

		return retorno;
	}
}