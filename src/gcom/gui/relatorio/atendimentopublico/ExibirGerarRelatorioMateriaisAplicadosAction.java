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

package gcom.gui.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.*;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3135] Gerar Relatório de Materiais Aplicados
 * 
 * @author Felipe Rosacruz
 * @date 29/01/2014
 */
public class ExibirGerarRelatorioMateriaisAplicadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioMateriaisAplicados");

		GerarRelatorioMateriaisAplicadosActionForm form = (GerarRelatorioMateriaisAplicadosActionForm) actionForm;

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("1"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(form, objetoConsulta);
		}

		// Pesquisar Setor Comercial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("")
 && (objetoConsulta.trim().equals("2"))){

			// Faz a consulta de Setor Comercial
			this.pesquisarSetorComercial(form, objetoConsulta);
		}

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo(FiltroServicoTipo.DESCRICAO);
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.SIM));
		Collection<ServicoTipo> colecaoServicoTipo = new ArrayList<ServicoTipo>(); 
		colecaoServicoTipo.addAll(Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName()));
		httpServletRequest.setAttribute("colecaoTipoServico", colecaoServicoTipo);

		FiltroMaterial filtroMaterial = new FiltroMaterial(FiltroMaterial.DESCRICAO);
		filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.INDICADOR_USO, ConstantesSistema.SIM));
		Collection<Material> colecaoMaterial = new ArrayList<Material>();
		colecaoMaterial.addAll(Fachada.getInstancia().pesquisar(filtroMaterial, Material.class.getName()));
		httpServletRequest.setAttribute("colecaoMaterial", colecaoMaterial);
		
		FiltroEquipe filtroEquipe = new FiltroEquipe(FiltroEquipe.NOME);
		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.INDICADOR_USO, ConstantesSistema.SIM));
		Collection<Equipe> colecaoEquipe = new ArrayList<Equipe>();
		colecaoEquipe.addAll(Fachada.getInstancia().pesquisar(filtroEquipe, Equipe.class.getName()));
		httpServletRequest.setAttribute("colecaoEquipe", colecaoEquipe);

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, form);


		return retorno;
	}

	/**
	 * Pesquisa Localidade
	 * 
	 * @author Felipe Rosacruz
	 * @date 29/01/2014
	 */
	private void pesquisarLocalidade(GerarRelatorioMateriaisAplicadosActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidade();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, local));

//		if(Util.verificarIdNaoVazio(form.getGerenciaRegional())){
//
//			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.GERENCIAREGIONAL, Util.obterInteger(form
//							.getGerenciaRegional())));
//		}

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setIdLocalidade(localidade.getId().toString());
			form.setNomeLocalidade(localidade.getDescricao());

		}else{
			form.setIdLocalidade(null);
			form.setNomeLocalidade("Localidade inexistente");
		}
	}

	/**
	 * Pesquisa Setor comercial
	 * 
	 * @author Felipe Rosacruz
	 * @date 29/01/2014
	 */
	private void pesquisarSetorComercial(GerarRelatorioMateriaisAplicadosActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidade();
		Object setor = form.getCdSetorComercial();

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, local));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

				form.setCdSetorComercial("" + setorComercial.getCodigo());
				form.setNomeSetorComercial(setorComercial.getDescricao());

		}else{

				form.setCdSetorComercial(null);
				form.setNomeSetorComercial("Setor Comercial Inicial inexistente");

		}
	}



	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Felipe Rosacruz
	 * @date 29/01/2014
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, GerarRelatorioMateriaisAplicadosActionForm form){

		// Localidade Inicial
		if(form.getIdLocalidade() != null && !form.getIdLocalidade().equals("") && form.getNomeLocalidade() != null
						&& !form.getNomeLocalidade().equals("")){

			httpServletRequest.setAttribute("localidadeEncontrada", "true");
		}

		 // Setor Comercial Inicial
		 if(form.getCdSetorComercial() != null && !form.getCdSetorComercial().equals("")
		 && form.getNomeSetorComercial() != null &&
		 !form.getNomeSetorComercial().equals("")){
		
		 httpServletRequest.setAttribute("setorComercialEncontrado", "true");
		 }
	}
}