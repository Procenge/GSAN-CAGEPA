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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.bean.ImovelComentarioHelper;
import gcom.atendimentopublico.imovelcomentario.FiltroImovelComentario;
import gcom.atendimentopublico.imovelcomentario.ImovelComentario;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInformarImovelComentarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("informarImovelComentario");

		InformarImovelComentarioActionForm form = (InformarImovelComentarioActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		String limpar = httpServletRequest.getParameter("limpar");

		if(!Util.isVazioOuBranco(limpar) && limpar.equals(ConstantesSistema.SIM.toString())){

			form.setIdImovel("");
			form.setInscricaoImovel("");
			form.setComentario("");
			this.getSessao(httpServletRequest).removeAttribute("colecaoImovelComentarioHelper");
		}

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Imóvel
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("1"))){

			this.pesquisarImovel(form, fachada, httpServletRequest);
		}else{

			if(Util.isVazioOuBranco(form.getIdImovel())){

				this.getSessao(httpServletRequest).removeAttribute("colecaoImovelComentarioHelper");
			}
		}

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	private void pesquisarImovel(InformarImovelComentarioActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		Imovel imovel = fachada.pesquisarImovel(Util.obterInteger(form.getIdImovel()));
		this.getSessao(httpServletRequest).removeAttribute("colecaoImovelComentarioHelper");

		if(imovel != null){


			form.setInscricaoImovel(imovel.getInscricaoFormatada());
			form.setIdImovel(imovel.getId().toString());

			// Imovel Comentário
			FiltroImovelComentario filtroImovelComentario = new FiltroImovelComentario();
			filtroImovelComentario.adicionarParametro(new ParametroSimples(FiltroImovelComentario.IMOVEL_ID, imovel.getId()));
			filtroImovelComentario.adicionarParametro(new ParametroSimples(FiltroImovelComentario.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroImovelComentario.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelComentario.USUARIO);
			filtroImovelComentario.setCampoOrderByDesc(FiltroImovelComentario.SEQUENCIAL);

			Collection<ImovelComentario> colecaoImovelComentario = fachada.pesquisar(filtroImovelComentario,
							ImovelComentario.class.getName());

			Collection colecaoImovelComentarioHelper = null;

			if(colecaoImovelComentario != null && !colecaoImovelComentario.isEmpty()){

				Iterator iteratorColecaoImovelComentario = colecaoImovelComentario.iterator();
				colecaoImovelComentarioHelper = new ArrayList();
				ImovelComentarioHelper imovelComentarioHelper = null;

				while(iteratorColecaoImovelComentario.hasNext()){

					ImovelComentario imovelComentario = (ImovelComentario) iteratorColecaoImovelComentario.next();
					imovelComentarioHelper = new ImovelComentarioHelper();

					// Id
					imovelComentarioHelper.setId(imovelComentario.getId().toString());

					// Comentário
					imovelComentarioHelper.setDescricao(imovelComentario.getDescricao().toString());

					// Sequencial Inclusão
					if(imovelComentario.getSequencial() != null){

						imovelComentarioHelper.setSequencialInclusao(imovelComentario.getSequencial().toString() + "º");
					}else{

						imovelComentarioHelper.setSequencialInclusao("");
					}

					// Data Inclusão
					imovelComentarioHelper.setDataInclusao(Util.formatarData(imovelComentario.getDataInclusao()));

					// Usuário
					imovelComentarioHelper.setUsuario(imovelComentario.getUsuario().getNomeUsuario());

					if(imovelComentario.getUsuario().getId().equals(this.getUsuarioLogado(httpServletRequest).getId())){

						imovelComentarioHelper.setUsuarioPossuiPermissaoAlteracao(ConstantesSistema.SIM.toString());
					}else{

						imovelComentarioHelper.setUsuarioPossuiPermissaoAlteracao(ConstantesSistema.NAO.toString());
					}

					colecaoImovelComentarioHelper.add(imovelComentarioHelper);
				}
			}

			this.getSessao(httpServletRequest).setAttribute("colecaoImovelComentarioHelper", colecaoImovelComentarioHelper);
		}else{

			form.setIdImovel(null);
			form.setInscricaoImovel("Imóvel inexistente");
		}

	}

	private void setaRequest(HttpServletRequest httpServletRequest, InformarImovelComentarioActionForm form){

		// Imovel
		if(!Util.isVazioOuBranco(form.getIdImovel()) && !Util.isVazioOuBranco(form.getInscricaoImovel())){

			httpServletRequest.setAttribute("imovelEncontrado", "true");
		}
	}
}
