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

package gcom.gui.faturamento.conta;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 */
public class ExibirFiltrarMapaControleContaRelatorioAction
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarMapaControleContaRelatorio");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarMapaControleContaRelatorioActionForm filtrarMapaControleContaRelatorioActionForm = (FiltrarMapaControleContaRelatorioActionForm) actionForm;

		filtrarMapaControleContaRelatorioActionForm.setIndicadorFichaCompensacao(ConstantesSistema.NAO.toString());

		Fachada fachada = Fachada.getInstancia();

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// [UC0023] - Pesquisar Localidade
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("1")){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(filtrarMapaControleContaRelatorioActionForm);
		}

		// [UC0142] - Pesquisar Setor Comercial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && objetoConsulta.trim().equals("2")){

			// Faz a consulta de Setor Comercial
			this.pesquisarSetorComercial(filtrarMapaControleContaRelatorioActionForm);
		}


		if(httpServletRequest.getParameter("caminho") != null){
			sessao.setAttribute("caminho", "ResumoContaLocalidade");

			FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
			Collection colecaoEmpresas = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			sessao.setAttribute("colecaoEmpresas", colecaoEmpresas);

			retorno = actionMapping.findForward("filtrarResumoContaLocalidadeRelatorio");
		}

		if(sessao.getAttribute("faturamentoGrupos") == null){
			// Carrega Cole�ao de Faturamento Grupos
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			// filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);
			filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);
			Collection faturamentoGrupos = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

			if(faturamentoGrupos.isEmpty()){
				throw new ActionServletException("atencao.naocadastrado", null, "grupo de faturamento");
			}else{
				sessao.setAttribute("faturamentoGrupos", faturamentoGrupos);
			}
		}


		return retorno;

	}

	private void pesquisarLocalidade(FiltrarMapaControleContaRelatorioActionForm filtrarMapaControleContaRelatorioActionForm){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, filtrarMapaControleContaRelatorioActionForm
						.getIdLocalidade()));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			filtrarMapaControleContaRelatorioActionForm.setIdLocalidade(localidade.getId().toString());
			filtrarMapaControleContaRelatorioActionForm.setDescricaoLocalidade(localidade.getDescricao());

		}else{
			filtrarMapaControleContaRelatorioActionForm.setIdLocalidade(null);
			filtrarMapaControleContaRelatorioActionForm.setDescricaoLocalidade("Localidade inexistente");
		}
	}

	private void pesquisarSetorComercial(FiltrarMapaControleContaRelatorioActionForm filtrarMapaControleContaRelatorioActionForm){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial
						.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, filtrarMapaControleContaRelatorioActionForm.getIdSetorComercial()));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, filtrarMapaControleContaRelatorioActionForm.getIdLocalidade()));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			filtrarMapaControleContaRelatorioActionForm.setIdSetorComercial("" + setorComercial.getCodigo());
			filtrarMapaControleContaRelatorioActionForm.setDescricaoSetorComercial((setorComercial.getDescricao()));

		}else{
			filtrarMapaControleContaRelatorioActionForm.setIdSetorComercial(null);
			filtrarMapaControleContaRelatorioActionForm.setDescricaoSetorComercial("Setor Comercial inexistente");
		}
	}
}
