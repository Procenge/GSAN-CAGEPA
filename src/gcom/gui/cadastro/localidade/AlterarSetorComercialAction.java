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

package gcom.gui.cadastro.localidade;

import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.faturamento.SetorComercialVencimento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AlterarSetorComercialAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		PesquisarAtualizarSetorComercialActionForm pesquisarAtualizarSetorComercialActionForm = (PesquisarAtualizarSetorComercialActionForm) actionForm;

		String setorComercialID = pesquisarAtualizarSetorComercialActionForm.getSetorComercialID();
		String localidadeID = pesquisarAtualizarSetorComercialActionForm.getLocalidadeID();
		String setorComercialCD = pesquisarAtualizarSetorComercialActionForm.getSetorComercialCD();
		String setorComercialNome = pesquisarAtualizarSetorComercialActionForm.getSetorComercialNome();
		String municipioID = pesquisarAtualizarSetorComercialActionForm.getMunicipioID();
		String indicadorUso = pesquisarAtualizarSetorComercialActionForm.getIndicadorUso();

		if(setorComercialID == null || setorComercialID.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.setor_comercial_nao_informado");
		}else if(localidadeID == null || localidadeID.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.localidade_nao_informada");
		}else if(setorComercialCD == null || setorComercialCD.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.codigo_setor_comercial_nao_informado");
		}else if(municipioID == null || municipioID.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.municipio_nao_informado");
		}else if(setorComercialNome == null || setorComercialNome.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.nome_setor_comercial_nao_informado");
		}else if(indicadorUso == null || indicadorUso.equalsIgnoreCase("")){
			throw new ActionServletException("atencao.indicador_uso_nao_informado");
		}

		else{

			// =====================================================================
			// Validando os dados informados pelo usu�rio.

			Municipio municipioNovo = (Municipio) pesquisarObjeto(municipioID, 3);
			if(municipioNovo == null){
				throw new ActionServletException("atencao.pesquisa.municipio_inexistente");
			}

			Short indicadorUsoNovo = new Short(indicadorUso);

			// ======================================================================

			SetorComercial setorComercialAtual = (SetorComercial) sessao.getAttribute("setorComercialManter");

			if(setorComercialAtual == null){
				// Setor comercial nao encontrado
				throw new ActionServletException("atencao.processo.setorComercialNaoCadastrada");
			}else{

				setorComercialAtual.setDescricao(setorComercialNome);
				setorComercialAtual.setMunicipio(municipioNovo);
				setorComercialAtual.setIndicadorUso(indicadorUsoNovo);

				getFachada().atualizarSetorComercial(setorComercialAtual, new Vector<SetorComercialVencimento>(),
								new Vector<SetorComercialVencimento>(), usuario, new Vector());

				montarPaginaSucesso(httpServletRequest, "Setor comercial de c�digo  " + setorComercialAtual.getCodigo()
								+ "  atualizado com sucesso.", " Realizar outra manuten��o de setor comercial",
								"exibirAtualizarSetorComercialAction.do");
			}
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * @param objetoPesquisa
	 * @param objetoPai
	 * @param tipoObjeto
	 * @return
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	private Object pesquisarObjeto(String objetoPesquisa, int tipoObjeto){

		Object retorno = null;
		Collection colecaoPesquisa = null;

		switch(tipoObjeto){
			// Setor Comercial
			case 2:

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, objetoPesquisa));

				colecaoPesquisa = getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}

				break;

			case 3:

				FiltroMunicipio filtroMunicipio = new FiltroMunicipio();

				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, objetoPesquisa));

				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = getFachada().pesquisar(filtroMunicipio, Municipio.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}

				break;

			default:
				break;
		}

		return retorno;
	}

}