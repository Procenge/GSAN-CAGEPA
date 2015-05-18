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

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Im�vel
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("1"))){

			this.pesquisarImovel(form, fachada, httpServletRequest);
		}else{

			if(Util.isVazioOuBranco(form.getIdImovel())){

				this.getSessao(httpServletRequest).removeAttribute("colecaoImovelComentarioHelper");
			}
		}

		// Seta os request�s encontrados
		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	private void pesquisarImovel(InformarImovelComentarioActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		Imovel imovel = fachada.pesquisarImovel(Util.obterInteger(form.getIdImovel()));
		this.getSessao(httpServletRequest).removeAttribute("colecaoImovelComentarioHelper");

		if(imovel != null){


			form.setInscricaoImovel(imovel.getInscricaoFormatada());
			form.setIdImovel(imovel.getId().toString());

			// Imovel Coment�rio
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

					// Coment�rio
					imovelComentarioHelper.setDescricao(imovelComentario.getDescricao().toString());

					// Sequencial Inclus�o
					if(imovelComentario.getSequencial() != null){

						imovelComentarioHelper.setSequencialInclusao(imovelComentario.getSequencial().toString() + "�");
					}else{

						imovelComentarioHelper.setSequencialInclusao("");
					}

					// Data Inclus�o
					imovelComentarioHelper.setDataInclusao(Util.formatarData(imovelComentario.getDataInclusao()));

					// Usu�rio
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
			form.setInscricaoImovel("Im�vel inexistente");
		}

	}

	private void setaRequest(HttpServletRequest httpServletRequest, InformarImovelComentarioActionForm form){

		// Imovel
		if(!Util.isVazioOuBranco(form.getIdImovel()) && !Util.isVazioOuBranco(form.getInscricaoImovel())){

			httpServletRequest.setAttribute("imovelEncontrado", "true");
		}
	}
}
