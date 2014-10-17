
package gcom.gui.faturamento.comandosimulacaofaturamento;

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

import gcom.cadastro.localidade.*;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
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
 * [UC3118] Inserir Comando de Simulação de Faturamento
 * 
 * @author Anderson Italo
 * @date 17/12/2013
 */
public class ExibirInserirComandoSimulacaoFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirComandoSimulacaoFaturamento");

		InserirComandoSimulacaoFaturamentoActionForm form = (InserirComandoSimulacaoFaturamentoActionForm) actionForm;

		// Faz a pesquisa dos Grupos de Faturamento
		this.pesquisarFaturamentoGrupo(httpServletRequest);

		// Faz a pesquisa das Gerencias Regionais
		this.pesquisarGerenciaRegional(httpServletRequest);

		// Faz a pesquisa das Unidades de Negócio
		this.pesquisarUnidadeNegocio(httpServletRequest);

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

		// Pesquisar Quadra
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("5") || objetoConsulta.trim().equals("6"))){

			// Faz a consulta de Quadra
			this.pesquisarQuadra(form, objetoConsulta, httpServletRequest);
		}

		// Pesquisar Rota
		if(!Util.isVazioOuBrancoOuZero(objetoConsulta) && (objetoConsulta.trim().equals("7") || objetoConsulta.trim().equals("8"))){

			// Faz a consulta de Rota
			this.pesquisarRota(form, objetoConsulta, httpServletRequest);
		}

		// Faz a pesquisa das Tarifas de Consumo
		this.pesquisarConsumoTarifa(httpServletRequest);

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	/**
	 * Pesquisar Gerencial Regional
	 * 
	 * @author Anderson Italo
	 * @date 17/12/2013
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
	 * @author Anderson Italo
	 * @date 17/12/2013
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
	 * @date 17/12/2013
	 */
	private void pesquisarLocalidade(InserirComandoSimulacaoFaturamentoActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidadeInicial();

		if(!objetoConsulta.trim().equals("1")){
			local = form.getIdLocalidadeFinal();
		}

		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(form.getIdLocalidadeFinal())){

			if(Util.obterInteger(form.getIdLocalidadeFinal()).intValue() > Util.obterInteger(form.getIdLocalidadeInicial()).intValue()){

				form.setCodigoSetorComercialInicial(null);
				form.setDescricaoSetorComercialInicial(null);
				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal(null);
			}
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
	 * @date 17/12/2013
	 */
	private void pesquisarSetorComercial(InserirComandoSimulacaoFaturamentoActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidadeInicial();
		Object setor = form.getCodigoSetorComercialInicial();

		if(!objetoConsulta.trim().equals("2")){

			local = form.getIdLocalidadeFinal();
			setor = form.getCodigoSetorComercialFinal();
		}

		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(form.getIdLocalidadeFinal())){

			if(Util.obterInteger(form.getIdLocalidadeFinal()).intValue() > Util.obterInteger(form.getIdLocalidadeInicial()).intValue()){

				form.setCodigoSetorComercialInicial(null);
				form.setDescricaoSetorComercialInicial(null);
				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal(null);
				return;
			}
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
	 * Seta os request com os id encontrados
	 * 
	 * @author Anderson Italo
	 * @date 17/12/2013
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, InserirComandoSimulacaoFaturamentoActionForm form){

		// Localidade Inicial
		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(form.getDescricaoLocalidadeInicial())){

			httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
			this.getSessao(httpServletRequest).setAttribute("idLocalidade", form.getIdLocalidadeInicial());
		}else{

			if(!Util.isVazioOuBranco(form.getIdLocalidadeFinal()) && !Util.isVazioOuBranco(form.getDescricaoLocalidadeFinal())){

				httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
				this.getSessao(httpServletRequest).setAttribute("idLocalidade", form.getIdLocalidadeFinal());
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

		// Rota Inicial
		if(!Util.isVazioOuBranco(form.getCodigoRotaInicial()) && !Util.isVazioOuBranco(form.getDescricaoRotaInicial())){

			httpServletRequest.setAttribute("rotaInicialEncontrada", "true");
			httpServletRequest.setAttribute("rotaFinalEncontrada", "true");
		}else{

			if(!Util.isVazioOuBranco(form.getCodigoRotaFinal()) && !Util.isVazioOuBranco(form.getDescricaoRotaFinal())){

				httpServletRequest.setAttribute("rotaFinalEncontrada", "true");
			}
		}

		// Quadra Inicial
		if(Util.isVazioOuBranco(form.getNumeroQuadraInicial()) && httpServletRequest.getAttribute("msgQuadraInicial") != null){

			httpServletRequest.setAttribute("quadraInicialNaoEncontrada", "true");
		}else{

			if(Util.isVazioOuBranco(form.getNumeroQuadraFinal()) && httpServletRequest.getAttribute("msgQuadraFinal") != null){

				httpServletRequest.setAttribute("quadraFinalNaoEncontrada", "true");
			}
		}
	}

	/**
	 * Pesquisar Setor comercial
	 * 
	 * @author Anderson Italo
	 * @date 17/12/2013
	 */
	private void pesquisarQuadra(InserirComandoSimulacaoFaturamentoActionForm form, String objetoConsulta,
					HttpServletRequest httpServletRequest){

		Object setor = form.getCodigoSetorComercialInicial();
		Object quadraNumero = form.getNumeroQuadraInicial();

		if(!objetoConsulta.trim().equals("5")){

			setor = form.getCodigoSetorComercialFinal();
			quadraNumero = form.getNumeroQuadraFinal();
		}

		if(!Util.isVazioOuBranco(form.getCodigoSetorComercialInicial()) && !Util.isVazioOuBranco(form.getCodigoSetorComercialFinal())){

			if(Util.obterInteger(form.getCodigoSetorComercialFinal()).intValue() > Util.obterInteger(form.getCodigoSetorComercialInicial())
							.intValue()){

				form.setNumeroQuadraInicial(null);
				form.setNumeroQuadraFinal(null);
				form.setLoteInicial(null);
				form.setLoteFinal(null);
				return;
			}
		}

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, Util.obterInteger(form
						.getIdLocalidadeInicial())));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));

		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNumero));

		// Recupera Quadra
		Collection colecaoQuadra = this.getFachada().pesquisar(filtroQuadra, Quadra.class.getName());

		if(colecaoQuadra != null && !colecaoQuadra.isEmpty()){

			Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

			if(objetoConsulta.trim().equals("5")){

				form.setNumeroQuadraInicial(String.valueOf(quadra.getNumeroQuadra()));
			}

			form.setNumeroQuadraFinal(String.valueOf(quadra.getNumeroQuadra()));

		}else{

			if(objetoConsulta.trim().equals("5")){

				form.setNumeroQuadraInicial(null);
				httpServletRequest.setAttribute("msgQuadraInicial", "Quadra Inicial inexistente");
				form.setNumeroQuadraFinal(null);
			}else{

				httpServletRequest.setAttribute("msgQuadraFinal", "Quadra Final inexistente");
				form.setNumeroQuadraFinal(null);
			}

		}
	}

	/**
	 * Pesquisar Rota
	 * 
	 * @author Anderson Italo
	 * @date 22/12/2013
	 */
	private void pesquisarRota(InserirComandoSimulacaoFaturamentoActionForm form, String objetoConsulta, HttpServletRequest request){

		Object setor = form.getCodigoSetorComercialInicial();
		Object rotaCodigo = form.getCodigoRotaInicial();

		if(!objetoConsulta.trim().equals("7")){

			setor = form.getCodigoSetorComercialFinal();
			rotaCodigo = form.getCodigoRotaFinal();
		}

		if(!Util.isVazioOuBranco(form.getCodigoSetorComercialInicial()) && !Util.isVazioOuBranco(form.getCodigoSetorComercialFinal())){

			if(Util.obterInteger(form.getCodigoSetorComercialFinal()).intValue() > Util.obterInteger(form.getCodigoSetorComercialInicial())
							.intValue()){

				form.setCodigoRotaInicial(null);
				form.setDescricaoRotaInicial(null);
				form.setCodigoRotaFinal(null);
				form.setDescricaoRotaFinal(null);
				form.setLoteInicial(null);
				form.setLoteFinal(null);
				return;
			}
		}

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor));
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, Util.obterInteger(form
						.getIdLocalidadeInicial())));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, setorComercial.getId()));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaCodigo));

		filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);

		// Recupera Rota
		Collection colecaoRota = this.getFachada().pesquisar(filtroRota, Rota.class.getName());

		if(colecaoRota != null && !colecaoRota.isEmpty()){

			Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoRota);

			if(objetoConsulta.trim().equals("7")){

				form.setCodigoRotaInicial(String.valueOf(rota.getCodigo()));
				form.setDescricaoRotaInicial(rota.getDescricao());
			}

			form.setCodigoRotaFinal(String.valueOf(rota.getCodigo()));
			form.setDescricaoRotaFinal(rota.getDescricao());

		}else{

			if(objetoConsulta.trim().equals("7")){

				form.setCodigoRotaInicial(null);
				form.setDescricaoRotaInicial("Rota Inicial inexistente");

				form.setCodigoRotaFinal(null);
				form.setDescricaoRotaFinal(null);
			}else{

				form.setCodigoRotaFinal(null);
				form.setDescricaoRotaFinal("Rota Final inexistente");
			}

		}
	}

	/**
	 * Pesquisar Grupo de Faturamento
	 * 
	 * @author Anderson Italo
	 * @date 17/12/2013
	 */
	private void pesquisarFaturamentoGrupo(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formulário.
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);

		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna faturamento_grupo
		Collection colecaoFaturamentoGrupo = this.getFachada().pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		if(colecaoFaturamentoGrupo == null || colecaoFaturamentoGrupo.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Grupo de Faturamento");
		}else{

			httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}
	}

	/**
	 * Pesquisar Tarifa de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 17/12/2013
	 */
	private void pesquisarConsumoTarifa(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formulário.
		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

		filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);

		filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna consumo_tarifa
		Collection colecaoConsumoTarifa = this.getFachada().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

		if(colecaoConsumoTarifa == null || colecaoConsumoTarifa.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tarifa de Consumo");
		}else{

			httpServletRequest.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
		}
	}
}

