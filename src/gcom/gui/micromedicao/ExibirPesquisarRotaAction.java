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

package gcom.gui.micromedicao;

import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pr�-processamento da p�gina de pesquisa de Rota
 * 
 * @author Rafael Santos
 * @since 23/08/2006
 */
public class ExibirPesquisarRotaAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirPesquisarRota");
		HttpSession sessao = httpServletRequest.getSession(false);
		PesquisarRotaActionForm pesquisarActionForm = (PesquisarRotaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		String idLocalidade = httpServletRequest.getParameter("idLocalidade");
		String codigoSetorComercial = httpServletRequest.getParameter("codigoSetorComercial");
		String idQuadra = httpServletRequest.getParameter("idQuadra");
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Collection colecaoFaturamentoGrupo = null;
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		// Verifica se informou alguma restri��o (localidade ou setor comercial)
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("restringirPesquisa"))){

			if(!Util.isVazioOuBranco(httpServletRequest.getParameter("destinoCampo"))){

				sessao.setAttribute("destinoCampo", httpServletRequest.getParameter("destinoCampo"));
			}

			if(!Util.isVazioOuBranco(idLocalidade)){

				if(!fachada.verificarPermissaoEspecial(PermissaoEspecial.INFORMAR_ROTA_NAO_PERTENCENTE_AO_SETOR_COMERCIAL_DO_IMOVEL,
								usuario)){
					this.pesquisarLocalidade(idLocalidade, pesquisarActionForm, fachada, httpServletRequest);

					if(!Util.isVazioOuBranco(pesquisarActionForm.getDescricaoLocalidade())
									&& !pesquisarActionForm.getDescricaoLocalidade().equals("Localidade Inexistente")){

						sessao.setAttribute("desabilitarLocalidade", "true");
					}else{

						throw new ActionServletException("atencao.pesquisa.localidade_inexistente");
					}

				
					if(!Util.isVazioOuBranco(codigoSetorComercial)){

						this.pesquisarSetorComercial(idLocalidade, codigoSetorComercial, pesquisarActionForm, fachada, httpServletRequest);

						if(!Util.isVazioOuBranco(pesquisarActionForm.getCodigoSetorComercial())
										&& pesquisarActionForm.getDescricaoSetorComercial() != null
										&& !pesquisarActionForm.getDescricaoSetorComercial().equals("Setor Comercial Inexistente")){

							sessao.setAttribute("desabilitarSetor", "true");
						}else{

							throw new ActionServletException("atencao.setor_comercial.inexistente");
						}
					}

				}else{
					sessao.removeAttribute("desabilitarLocalidade");
					sessao.removeAttribute("desabilitarSetor");
				}


			}else if(!Util.isVazioOuBranco(codigoSetorComercial)){

				if(!fachada.verificarPermissaoEspecial(PermissaoEspecial.INFORMAR_ROTA_NAO_PERTENCENTE_AO_SETOR_COMERCIAL_DO_IMOVEL,
								usuario)){

				this.pesquisarSetorComercial(idLocalidade, codigoSetorComercial, pesquisarActionForm, fachada, httpServletRequest);

				if(!Util.isVazioOuBranco(pesquisarActionForm.getDescricaoSetorComercial())
								&& !pesquisarActionForm.getDescricaoSetorComercial().equals("Setor Comercial Inexistente")){

					sessao.setAttribute("desabilitarSetor", "true");
				}

				}else{

					sessao.removeAttribute("desabilitarSetor");
				}

			}else{

				sessao.removeAttribute("desabilitarLocalidade");
				sessao.removeAttribute("desabilitarSetor");

			}
		}else{

			if(httpServletRequest.getParameter("objetoConsulta") == null && httpServletRequest.getParameter("tipoConsulta") == null){

				pesquisarActionForm.setIdLocalidade("");
				pesquisarActionForm.setDescricaoLocalidade("");
				pesquisarActionForm.setCodigoSetorComercial("");
				pesquisarActionForm.setDescricaoSetorComercial("");
				pesquisarActionForm.setNumeroQuadra("");
				pesquisarActionForm.setCodigoRota("");
				pesquisarActionForm.setFaturamentoGrupo("");
				pesquisarActionForm.setIndicadorUso("");
				sessao.removeAttribute("caminhoRetornoTelaPesquisa");
			}
		}

		if(sessao.getAttribute("caminhoRetornoTelaPesquisaLocalidade") != null){

			sessao.removeAttribute("caminhoRetornoTelaPesquisaLocalidade");
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			if(httpServletRequest.getParameter("tipoConsulta").equals("localidade")){

				pesquisarActionForm.setDescricaoLocalidade(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
				pesquisarActionForm.setIdLocalidade(httpServletRequest.getParameter("idCampoEnviarDados"));
				httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
				httpServletRequest.setAttribute("descricaoLocalidade", httpServletRequest.getParameter("descricaoCampoEnviarDados"));

			}

			if(httpServletRequest.getParameter("tipoConsulta").equals("setorComercial")){

				pesquisarActionForm.setCodigoSetorComercial(httpServletRequest.getParameter("idCampoEnviarDados"));
				pesquisarActionForm.setDescricaoSetorComercial(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
				httpServletRequest.setAttribute("nomeCampo", "numeroQuadra");
				pesquisarActionForm.setDescricaoLocalidade(sessao.getAttribute("descricaoLocalidade").toString());
			}

			if(httpServletRequest.getParameter("tipoConsulta").equals("quadra")){

				pesquisarActionForm.setNumeroQuadra(httpServletRequest.getParameter("idCampoEnviarDados"));
				httpServletRequest.setAttribute("nomeCampo", "codigoRota");
			}

		}

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		if(colecaoFaturamentoGrupo != null && !colecaoFaturamentoGrupo.isEmpty()){

			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){

			switch(Integer.parseInt(objetoConsulta)){

				case 1:

					// Localidade
					pesquisarLocalidade(idLocalidade, pesquisarActionForm, fachada, httpServletRequest);
					break;
				case 2:

					// Setor Comercial
					pesquisarSetorComercial(idLocalidade, codigoSetorComercial, pesquisarActionForm, fachada, httpServletRequest);
					break;
				case 3:

					// Quadra
					pesquisarQuadra(idQuadra, codigoSetorComercial, idLocalidade, pesquisarActionForm, fachada, httpServletRequest);
					break;

				default:
					break;
			}
		}

		if(Util.isVazioOuBranco(httpServletRequest.getAttribute("nomeCampo"))){

			httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
		}

		return retorno;
	}

	public void pesquisarLocalidade(String idLocalidade, PesquisarRotaActionForm pesquisarActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		// coloca parametro no filtro
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(idLocalidade)));

		// pesquisa
		Collection localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
		if(localidades != null && !localidades.isEmpty()){

			// A localidade foi encontrada
			pesquisarActionForm.setIdLocalidade((((Localidade) ((List) localidades).get(0)).getId().toString()));
			pesquisarActionForm.setDescricaoLocalidade((((Localidade) ((List) localidades).get(0)).getDescricao()));
			httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");
		}else{

			httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "exception");
			pesquisarActionForm.setIdLocalidade("");
			pesquisarActionForm.setDescricaoLocalidade("Localidade Inexistente");
			httpServletRequest.setAttribute("nomeCampo", "idLocalidadeFiltro");
		}
	}

	public void pesquisarSetorComercial(String idLocalidade, String codigoSetorComercial, PesquisarRotaActionForm pesquisarActionForm,
					Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){

			// coloca parametro no filtro
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, new Integer(idLocalidade)));
		}

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, new Integer(
						codigoSetorComercial)));

		// pesquisa
		Collection setorComerciais = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
		if(setorComerciais != null && !setorComerciais.isEmpty()){

			pesquisarActionForm.setCodigoSetorComercial(("" + ((SetorComercial) ((List) setorComerciais).get(0)).getCodigo()));
			pesquisarActionForm.setDescricaoSetorComercial((((SetorComercial) ((List) setorComerciais).get(0)).getDescricao()));
			httpServletRequest.setAttribute("nomeCampo", "numeroQuadra");
		}else{

			pesquisarActionForm.setCodigoSetorComercial("");
			httpServletRequest.setAttribute("idSetorComercialNaoEncontrada", "exception");
			pesquisarActionForm.setDescricaoSetorComercial("Setor Comercial Inexistente");
			httpServletRequest.setAttribute("nomeCampo", "codigoSetorComercial");

		}
	}

	public void pesquisarQuadra(String numeroQuadra, String codigoSetorComercial, String idLocalidade,
					PesquisarRotaActionForm pesquisarActionForm, Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){

			// coloca parametro no filtro
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, new Integer(idLocalidade)));
		}

		if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){

			// coloca parametro no filtro
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, new Integer(idLocalidade)));
		}

		if(codigoSetorComercial != null && !codigoSetorComercial.toString().trim().equalsIgnoreCase("")){

			// coloca parametro no filtro
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, new Integer(codigoSetorComercial)));
		}

		if(numeroQuadra != null && !numeroQuadra.equals("")){
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(numeroQuadra)));
		}

		// pesquisa
		Collection quadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());
		if(quadras != null && !quadras.isEmpty()){

			// O cliente foi encontrado
			pesquisarActionForm.setNumeroQuadra(("" + ((Quadra) ((List) quadras).get(0)).getNumeroQuadra()));
			httpServletRequest.setAttribute("nomeCampo", "codigoRota");
		}else{

			httpServletRequest.setAttribute("codigoQuadraNaoEncontrada", "exception");
			pesquisarActionForm.setNumeroQuadra("");
			httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");
			httpServletRequest.setAttribute("nomeCampo", "numeroQuadra");
		}
	}

}
