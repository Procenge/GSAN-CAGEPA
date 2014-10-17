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

package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
 * 
 * @author Anderson Italo
 * @date 06/06/2011
 */
public class ExibirGerarRelatorioResumoOrdensServicoPendentesAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioResumoOrdensServicoPendentes");

		GerarRelatorioResumoOrdensServicoPendentesActionForm form = (GerarRelatorioResumoOrdensServicoPendentesActionForm) actionForm;

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("1") || objetoConsulta.trim().equals("3"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(form, objetoConsulta);
		}

		// Pesquisar Setor Comercial
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("2") || objetoConsulta.trim().equals("4"))){

			// Faz a consulta de Setor Comercial
			this.pesquisarSetorComercial(form, objetoConsulta);
		}

		// Faz a pesquisa das Gerencias Regionais
		this.pesquisarGerenciaRegional(httpServletRequest);

		// Faz a pesquisa das Unidades de Negócio
		this.pesquisarUnidadeNegocio(httpServletRequest);

		// Faz a pesquisa dos Tipos de Serviço
		this.pesquisarTipoServico(httpServletRequest);

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	/**
	 * Pesquisar Gerencial Regional
	 * 
	 * @author Anderson Italo
	 * @date 06/06/2011
	 */
	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Gerência Regional");
		}else{
			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}
	}

	/**
	 * Pesquisar Unidade de Negócio
	 * 
	 * @author Carlos Chrystian
	 * @date 06/06/2011
	 */
	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formulário.
		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME_ABREVIADO);

		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Gerencia_Regional
		Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
			// Nenhum registro na tabela gerencia_regional foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Unidade de Negócio");
		}else{

			UnidadeNegocio unidadeNegocio = null;
			Iterator iterator = colecaoUnidadeNegocio.iterator();

			while(iterator.hasNext()){
				unidadeNegocio = (UnidadeNegocio) iterator.next();

				String descUnidadeNegocio = unidadeNegocio.getNomeAbreviado() + "-" + unidadeNegocio.getNome();
				unidadeNegocio.setNome(descUnidadeNegocio);

			}

			httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
		}

	}

	/**
	 * Pesquisar Localidade
	 * 
	 * @author Anderson Italo
	 * @date 06/06/2011
	 */
	private void pesquisarLocalidade(GerarRelatorioResumoOrdensServicoPendentesActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidadeInicial();

		if(!objetoConsulta.trim().equals("1")){
			local = form.getIdLocalidadeFinal();
		}

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, local));

		if(!Util.isVazioOuBranco(form.getIdGerenciaRegional())){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_GERENCIA, form.getIdGerenciaRegional()));
		}

		if(!Util.isVazioOuBranco(form.getIdUnidadeNegocio())){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_UNIDADE_NEGOCIO, form.getIdUnidadeNegocio()));
		}

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("1")){
				form.setIdLocalidadeInicial(localidade.getId().toString());
				form.setDescricaoLocalidadeInicial(localidade.getDescricao());
			}

			form.setIdLocalidadeFinal(localidade.getId().toString());
			form.setDescricaoLocalidadeFinal(localidade.getDescricao());

		}else{

			if(objetoConsulta.trim().equals("1")){
				form.setIdLocalidadeInicial(null);
				form.setDescricaoLocalidadeInicial("Localidade Inicial inexistente");

				form.setIdLocalidadeFinal(null);
				form.setDescricaoLocalidadeFinal(null);
			}else{
				form.setIdLocalidadeFinal(null);
				form.setDescricaoLocalidadeFinal("Localidade Final inexistente");
			}
		}
	}

	/**
	 * Pesquisar Setor comercial
	 * 
	 * @author Anderson Italo
	 * @date 06/06/2011
	 */
	private void pesquisarSetorComercial(GerarRelatorioResumoOrdensServicoPendentesActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidadeInicial();
		Object setor = form.getCodigoSetorComercialInicial();

		if(!objetoConsulta.trim().equals("2")){

			local = form.getIdLocalidadeFinal();
			setor = form.getCodigoSetorComercialFinal();
		}

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, local));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			if(objetoConsulta.trim().equals("2")){

				form.setCodigoSetorComercialInicial(String.valueOf(setorComercial.getCodigo()));
				form.setDescricaoSetorComercialInicial(setorComercial.getDescricao());
			}

			form.setCodigoSetorComercialFinal(String.valueOf(setorComercial.getCodigo()));
			form.setDescricaoSetorComercialFinal(setorComercial.getDescricao());

		}else{

			if(objetoConsulta.trim().equals("2")){

				form.setCodigoSetorComercialInicial(null);
				form.setDescricaoSetorComercialInicial("Setor Comercial Inicial inexistente");

				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal(null);
			}else{

				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal("Setor Comercial Final inexistente");
			}

		}
	}

	/**
	 * Pesquisar Tipo Servico
	 * 
	 * @author Anderson Italo
	 * @date 06/06/2011
	 */
	private void pesquisarTipoServico(HttpServletRequest httpServletRequest){

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.setConsultaSemLimites(true);
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);

		Collection colecaoTipoServico = Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

		if(colecaoTipoServico == null || colecaoTipoServico.isEmpty()){

			throw new ActionServletException("atencao.naocadastrado", null, "Tipo de Serviço");
		}else{

			httpServletRequest.setAttribute("colecaoTipoServico", colecaoTipoServico);
		}
	}

	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Anderson Italo
	 * @date 06/06/2011
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, GerarRelatorioResumoOrdensServicoPendentesActionForm form){

		// Localidade Inicial
		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(form.getDescricaoLocalidadeInicial())){

			httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
		}else{

			if(!Util.isVazioOuBranco(form.getIdLocalidadeFinal()) && !Util.isVazioOuBranco(form.getDescricaoLocalidadeFinal())){

				httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
			}
		}

		// Setor Comercial Inicial
		if(!Util.isVazioOuBranco(form.getCodigoSetorComercialInicial()) && !Util.isVazioOuBranco(form.getDescricaoSetorComercialInicial())){

			httpServletRequest.setAttribute("setorComercialInicialEncontrado", "true");
			httpServletRequest.setAttribute("setorComercialFinalEncontrado", "true");
		}else{

			if(!Util.isVazioOuBranco(form.getCodigoSetorComercialFinal()) && !Util.isVazioOuBranco(form.getDescricaoSetorComercialFinal())){

				httpServletRequest.setAttribute("setorComercialFinalEncontrado", "true");
			}
		}
	}
}
