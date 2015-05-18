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
 * Thiago Vieira
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

package gcom.gui.cobranca.spcserasa;

import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.cliente.EsferaPoder;
import gcom.cadastro.cliente.FiltroClienteTipo;
import gcom.cadastro.cliente.FiltroEsferaPoder;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.cadastro.localidade.*;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.cobranca.bean.DadosConsultaNegativacaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ConectorOr;
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
 * Description of the Class
 * 
 * @author Thiago Vieira
 */
public class InformarDadosConsultaNegativacaoAction
				extends GcomAction {

	private static final String GERAR_RELATORIO = "gerarRelatorio";

	private static final String OPÇÕES_SELECIONADAS = "OPÇÕES SELECIONADAS";

	private static final String TODOS = "TODOS";

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirResumoNegativacaoParametros");

		final HttpSession sessao = httpServletRequest.getSession(false);

		final Fachada fachada = Fachada.getInstancia();

		final InformarDadosConsultaNegativacaoActionForm form = (InformarDadosConsultaNegativacaoActionForm) actionForm;

		DadosConsultaNegativacaoHelper dadosConsultaNegativacaoHelper = new DadosConsultaNegativacaoHelper();

		if(!Util.isVazioOuBranco(form.getIdNegativador()) && Integer.valueOf(form.getIdNegativador()) > 0){
			dadosConsultaNegativacaoHelper.setIdNegativador(Integer.valueOf(form.getIdNegativador()));
			dadosConsultaNegativacaoHelper.setNomeNegativador(form.getNomeNegativador());
		}

		if(!Util.isVazioOuBranco(form.getPeriodoEnvioNegativacaoInicio())){
			String periodoEnvioNegativacaoInicio = form.getPeriodoEnvioNegativacaoInicio();
			sessao.setAttribute("periodoEnvioNegativacaoInicio", periodoEnvioNegativacaoInicio);

			dadosConsultaNegativacaoHelper.setPeriodoEnvioNegativacaoInicio(Util.converterStringParaDate(
							form.getPeriodoEnvioNegativacaoInicio(), "atencao.data_inicial_periodo_negativacao.invalida"));
		}

		if(!Util.isVazioOuBranco(form.getPeriodoEnvioNegativacaoFim())){
			String periodoEnvioNegativacaoFim = form.getPeriodoEnvioNegativacaoFim();
			sessao.setAttribute("periodoEnvioNegativacaoFim", periodoEnvioNegativacaoFim);

			dadosConsultaNegativacaoHelper.setPeriodoEnvioNegativacaoFim(Util.converterStringParaDate(form.getPeriodoEnvioNegativacaoFim(),
							"atencao.data_final_periodo_negativacao.invalida"));
		}

		if(!Util.isVazioOuBranco(form.getPeriodoExclusaoNegativacaoInicio())){
			String periodoExclusaoNegativacaoInicio = form.getPeriodoExclusaoNegativacaoInicio();
			sessao.setAttribute("periodoExclusaoNegativacaoInicio", periodoExclusaoNegativacaoInicio);

			dadosConsultaNegativacaoHelper.setPeriodoExclusaoNegativacaoInicio(Util.converterStringParaDate(
							form.getPeriodoExclusaoNegativacaoInicio(), "atencao.data_inicial_periodo_exclusao.invalida"));
		}

		if(!Util.isVazioOuBranco(form.getPeriodoExclusaoNegativacaoFim())){
			String periodoExclusaoNegativacaoFim = form.getPeriodoExclusaoNegativacaoFim();
			sessao.setAttribute("periodoExclusaoNegativacaoFim", periodoExclusaoNegativacaoFim);

			dadosConsultaNegativacaoHelper.setPeriodoExclusaoNegativacaoFim(Util.converterStringParaDate(
							form.getPeriodoExclusaoNegativacaoFim(), "atencao.data_final_periodo_exclusao.invalida"));
		}

		if(dadosConsultaNegativacaoHelper.getPeriodoEnvioNegativacaoInicio() != null
						&& dadosConsultaNegativacaoHelper.getPeriodoEnvioNegativacaoFim() != null){
			if(dadosConsultaNegativacaoHelper.getPeriodoEnvioNegativacaoFim().before(
							dadosConsultaNegativacaoHelper.getPeriodoEnvioNegativacaoInicio())){
				throw new ActionServletException("atencao.data_final_periodo_negativacao_anterior_data_inicial");
			}
		}

		if(dadosConsultaNegativacaoHelper.getPeriodoExclusaoNegativacaoInicio() != null
						&& dadosConsultaNegativacaoHelper.getPeriodoExclusaoNegativacaoFim() != null){
			if(dadosConsultaNegativacaoHelper.getPeriodoExclusaoNegativacaoFim().before(
							dadosConsultaNegativacaoHelper.getPeriodoExclusaoNegativacaoInicio())){
				throw new ActionServletException("atencao.data_final_periodo_negativacao_anterior_data_inicial");
			}
		}

		if(!Util.isVazioOuBranco(form.getIdNegativadorExclusaoMotivo()) && Integer.valueOf(form.getIdNegativadorExclusaoMotivo()) > 0){
			dadosConsultaNegativacaoHelper.setIdNegativadorExclusaoMotivo(Integer.valueOf(form.getIdNegativadorExclusaoMotivo()));
		}

		if(!Util.isVazioOuBranco(form.getIdNegativacaoComando())){
			dadosConsultaNegativacaoHelper.setIdNegativacaoComando(Integer.valueOf(form.getIdNegativacaoComando()));
		}

		// --------------------------------------------------------------------------------------------------------
		// Grupo Cobrança
		// --------------------------------------------------------------------------------------------------------
		String[] arrayCobrancaGrupoForm = form.getArrayCobrancaGrupo();
		String[] arrayCobrancaGrupo = null;

		Collection<String> arrayCobrancaGrupoAux = new ArrayList<String>();

		if(arrayCobrancaGrupoForm != null && !(arrayCobrancaGrupoForm.length <= 0)){
			for(String idCG : arrayCobrancaGrupoForm){
				if(!(idCG.trim().equalsIgnoreCase("")) || !(idCG.trim().length() < 1)){
					arrayCobrancaGrupoAux.add(idCG);
				}
			}

			if(arrayCobrancaGrupoAux != null && !arrayCobrancaGrupoAux.isEmpty()){
				arrayCobrancaGrupo = (String[]) arrayCobrancaGrupoAux.toArray(new String[arrayCobrancaGrupoAux.size()]);
			}
		}

		CobrancaGrupo cobrancaGrupoColecao = new CobrancaGrupo();
		cobrancaGrupoColecao.setId(-1);

		Collection colecaoCobrancaGrupo = new ArrayList();
		int i = 0;

		if(arrayCobrancaGrupo == null){
			cobrancaGrupoColecao.setDescricao(TODOS);
			colecaoCobrancaGrupo.add(cobrancaGrupoColecao);
		}else{
			cobrancaGrupoColecao.setDescricao(OPÇÕES_SELECIONADAS);
			colecaoCobrancaGrupo.add(cobrancaGrupoColecao);
			FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();

			for(i = 0; i < arrayCobrancaGrupo.length; i++){
				if(!arrayCobrancaGrupo[i].equals("")
								&& !arrayCobrancaGrupo[i].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
					if(i + 1 < arrayCobrancaGrupo.length){
						filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, arrayCobrancaGrupo[i],
										ConectorOr.CONECTOR_OR, arrayCobrancaGrupo.length));

					}else{
						filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.ID, arrayCobrancaGrupo[i]));
					}
				}
			}

			filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);

			Collection colecaoCobrancaGrupoPesquisa = fachada.pesquisar(filtroCobrancaGrupo, CobrancaGrupo.class.getName());

			if(colecaoCobrancaGrupoPesquisa != null && !colecaoCobrancaGrupoPesquisa.isEmpty()){
				colecaoCobrancaGrupo.addAll(colecaoCobrancaGrupoPesquisa);
			}
		}

		dadosConsultaNegativacaoHelper.setColecaoCobrancaGrupo(colecaoCobrancaGrupo);

		// --------------------------------------------------------------------------------------------------------

		if(form.getIdEloPolo() != null && !form.getIdEloPolo().equals("") && Integer.valueOf(form.getIdEloPolo()) > 0){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getIdEloPolo()));

			Collection coll = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());
			if(coll.size() == 1){
				dadosConsultaNegativacaoHelper.setIdEloPolo(Integer.valueOf(form.getIdEloPolo()));
			}else{
				throw new ActionServletException("pesquisa.elo.inexistente");
			}
		}

		if(form.getIdLocalidade() != null && !form.getIdLocalidade().equals("") && Integer.valueOf(form.getIdLocalidade()) > 0){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getIdLocalidade()));

			Collection coll = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());
			if(coll.size() == 1){
				dadosConsultaNegativacaoHelper.setIdLocalidade(Integer.valueOf(form.getIdLocalidade()));
			}else{
				throw new ActionServletException("pesquisa.localidade.inexistente");
			}
		}

		if(form.getIdSetorComercial() != null && !form.getIdSetorComercial().equals("") && Integer.valueOf(form.getIdSetorComercial()) > 0){
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, form.getIdLocalidade()));
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, form
							.getIdSetorComercial()));

			Collection coll = Fachada.getInstancia().pesquisar(filtroSetorComercial, SetorComercial.class.getName());
			if(coll.size() == 1){
				dadosConsultaNegativacaoHelper.setIdSetorComercial(Integer.valueOf(form.getIdSetorComercial()));
			}else{
				throw new ActionServletException("atencao.setor_comercial.inexistente");
			}
		}

		if(form.getIdQuadra() != null && !form.getIdQuadra().equals("") && Integer.valueOf(form.getIdQuadra()) > 0){
			FiltroQuadra filtroQuadra = new FiltroQuadra();
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, form.getIdLocalidade()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, form.getIdSetorComercial()));
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, form.getIdQuadra()));

			Collection coll = Fachada.getInstancia().pesquisar(filtroQuadra, Quadra.class.getName());
			if(coll.size() == 1){
				dadosConsultaNegativacaoHelper.setIdQuadra(Integer.valueOf(form.getIdQuadra()));
			}else{
				throw new ActionServletException("atencao.quadra.inexistente");
			}
		}

		// ************************************************************************************************
		// --------------------------------------------------------------------------------------------------------
		// Gerência Regional
		// --------------------------------------------------------------------------------------------------------
		String[] arrayGerenciaRegionalForm = form.getArrayGerenciaRegional();

		String[] arrayGerenciaRegional = null;

		Collection<String> arrayGerenciaRegionalAux = new ArrayList<String>();

		if(arrayGerenciaRegionalForm != null && !(arrayGerenciaRegionalForm.length <= 0)){
			for(String idGR : arrayGerenciaRegionalForm){
				if(!(idGR.trim().equalsIgnoreCase("")) || !(idGR.trim().length() < 1)){
					arrayGerenciaRegionalAux.add(idGR);
				}
			}

			if(arrayGerenciaRegionalAux != null && !arrayGerenciaRegionalAux.isEmpty()){
				arrayGerenciaRegional = (String[]) arrayGerenciaRegionalAux.toArray(new String[arrayGerenciaRegionalAux.size()]);
			}
		}

		GerenciaRegional gerenciaRegionalColecao = new GerenciaRegional();
		gerenciaRegionalColecao.setId(-1);

		Collection colecaoGerenciaRegional = new ArrayList();
		int j = 0;

		if(arrayGerenciaRegional == null){
			gerenciaRegionalColecao.setNome(TODOS);
			colecaoGerenciaRegional.add(gerenciaRegionalColecao);
		}else{
			gerenciaRegionalColecao.setNome(OPÇÕES_SELECIONADAS);
			colecaoGerenciaRegional.add(gerenciaRegionalColecao);
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			for(j = 0; j < arrayGerenciaRegional.length; j++){
				if(!arrayGerenciaRegional[j].equals("")
								&& !arrayGerenciaRegional[j].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(j + 1 < arrayGerenciaRegional.length){
						filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, arrayGerenciaRegional[j],
										ConectorOr.CONECTOR_OR, arrayGerenciaRegional.length));

					}else{
						filtroGerenciaRegional
										.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, arrayGerenciaRegional[j]));
					}
				}
			}

			filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);

			Collection colecaoGerenciaRegionalPesquisa = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(colecaoGerenciaRegionalPesquisa != null && !colecaoGerenciaRegionalPesquisa.isEmpty()){
				colecaoGerenciaRegional.addAll(colecaoGerenciaRegionalPesquisa);
			}
		}

		dadosConsultaNegativacaoHelper.setColecaoGerenciaRegional(colecaoGerenciaRegional);
		// --------------------------------------------------------------------------------------------------------
		// Unidade de Negócio
		// --------------------------------------------------------------------------------------------------------
		String[] arrayUnidadeNegocioForm = form.getArrayUnidadeNegocio();

		String[] arrayUnidadeNegocio = null;

		Collection<String> arrayUnidadeNegocioAux = new ArrayList<String>();

		if(arrayUnidadeNegocioForm != null && !(arrayUnidadeNegocioForm.length <= 0)){
			for(String idUN : arrayUnidadeNegocioForm){
				if(!(idUN.trim().equalsIgnoreCase("")) || !(idUN.trim().length() < 1)){
					arrayUnidadeNegocioAux.add(idUN);
				}
			}

			if(arrayUnidadeNegocioAux != null && !arrayUnidadeNegocioAux.isEmpty()){
				arrayUnidadeNegocio = (String[]) arrayUnidadeNegocioAux.toArray(new String[arrayUnidadeNegocioAux.size()]);
			}
		}

		UnidadeNegocio unidadeNegocioColecao = new UnidadeNegocio();
		unidadeNegocioColecao.setId(-1);

		Collection colecaoUnidadeNegocio = new ArrayList();
		int l = 0;

		if(arrayUnidadeNegocio == null){
			unidadeNegocioColecao.setNome(TODOS);
			colecaoUnidadeNegocio.add(unidadeNegocioColecao);
		}else{
			unidadeNegocioColecao.setNome(OPÇÕES_SELECIONADAS);
			colecaoUnidadeNegocio.add(unidadeNegocioColecao);
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

			for(l = 0; l < arrayUnidadeNegocio.length; l++){
				if(!arrayUnidadeNegocio[l].equals("")
								&& !arrayUnidadeNegocio[l].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(l + 1 < arrayUnidadeNegocio.length){
						filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, arrayUnidadeNegocio[l],
										ConectorOr.CONECTOR_OR, arrayUnidadeNegocio.length));

					}else{
						filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, arrayUnidadeNegocio[l]));
					}
				}
			}

			filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);

			Collection colecaoUnidadeNegocioPesquisa = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(colecaoUnidadeNegocioPesquisa != null && !colecaoUnidadeNegocioPesquisa.isEmpty()){
				colecaoUnidadeNegocio.addAll(colecaoUnidadeNegocioPesquisa);
			}
		}

		dadosConsultaNegativacaoHelper.setColecaoUnidadeNegocio(colecaoUnidadeNegocio);
		// --------------------------------------------------------------------------------------------------------
		// Perfil Imóvel
		// --------------------------------------------------------------------------------------------------------

		String[] arrayImovelPerfilForm = form.getArrayImovelPerfil();

		String[] arrayImovelPerfil = null;

		Collection<String> arrayImovelPerfilAux = new ArrayList<String>();

		if(arrayImovelPerfilForm != null && !(arrayImovelPerfilForm.length <= 0)){
			for(String idIP : arrayImovelPerfilForm){
				if(!(idIP.trim().equalsIgnoreCase("")) || !(idIP.trim().length() < 1)){
					arrayImovelPerfilAux.add(idIP);
				}
			}

			if(arrayImovelPerfilAux != null && !arrayImovelPerfilAux.isEmpty()){
				arrayImovelPerfil = (String[]) arrayImovelPerfilAux.toArray(new String[arrayImovelPerfilAux.size()]);
			}
		}

		ImovelPerfil imovelPerfilColecao = new ImovelPerfil();
		imovelPerfilColecao.setId(-1);

		Collection colecaoImovelPerfil = new ArrayList();
		int m = 0;

		if(arrayImovelPerfil == null){
			imovelPerfilColecao.setDescricao(TODOS);
			colecaoImovelPerfil.add(imovelPerfilColecao);
		}else{
			imovelPerfilColecao.setDescricao(OPÇÕES_SELECIONADAS);
			colecaoImovelPerfil.add(imovelPerfilColecao);
			FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();

			for(m = 0; m < arrayImovelPerfil.length; m++){
				if(!arrayImovelPerfil[m].equals("") && !arrayImovelPerfil[m].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(m + 1 < arrayImovelPerfil.length){
						filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, arrayImovelPerfil[m],
										ConectorOr.CONECTOR_OR, arrayImovelPerfil.length));

					}else{
						filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, arrayImovelPerfil[m]));
					}
				}
			}

			filtroImovelPerfil.setCampoOrderBy(FiltroImovelPerfil.DESCRICAO);

			Collection colecaoImovelPerfilPesquisa = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

			if(colecaoImovelPerfilPesquisa != null && !colecaoImovelPerfilPesquisa.isEmpty()){
				colecaoImovelPerfil.addAll(colecaoImovelPerfilPesquisa);
			}
		}

		dadosConsultaNegativacaoHelper.setColecaoImovelPerfil(colecaoImovelPerfil);
		// --------------------------------------------------------------------------------------------------------
		// Categoria
		// --------------------------------------------------------------------------------------------------------
		String[] arrayCategoriaForm = form.getArrayCategoria();

		String[] arrayCategoria = null;

		Collection<String> arrayCategoriaAux = new ArrayList<String>();

		if(arrayCategoriaForm != null && !(arrayCategoriaForm.length <= 0)){
			for(String idC : arrayCategoriaForm){
				if(!(idC.trim().equalsIgnoreCase("")) || !(idC.trim().length() < 1)){
					arrayCategoriaAux.add(idC);
				}
			}

			if(arrayCategoriaAux != null && !arrayCategoriaAux.isEmpty()){
				arrayCategoria = (String[]) arrayCategoriaAux.toArray(new String[arrayCategoriaAux.size()]);
			}
		}

		Categoria categoriaColecao = new Categoria();
		categoriaColecao.setId(-1);

		Collection colecaoCategoria = new ArrayList();
		int n = 0;

		if(arrayCategoria == null){
			categoriaColecao.setDescricao(TODOS);
			colecaoCategoria.add(categoriaColecao);
		}else{
			categoriaColecao.setDescricao(OPÇÕES_SELECIONADAS);
			colecaoCategoria.add(categoriaColecao);
			FiltroCategoria filtroCategoria = new FiltroCategoria();

			for(n = 0; n < arrayCategoria.length; n++){
				if(!arrayCategoria[n].equals("") && !arrayCategoria[n].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(n + 1 < arrayCategoria.length){
						filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, arrayCategoria[n],
										ConectorOr.CONECTOR_OR, arrayCategoria.length));

					}else{
						filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, arrayCategoria[n]));
					}
				}
			}

			filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

			Collection colecaoCategoriaPesquisa = fachada.pesquisar(filtroCategoria, Categoria.class.getName());

			if(colecaoCategoriaPesquisa != null && !colecaoCategoriaPesquisa.isEmpty()){
				colecaoCategoria.addAll(colecaoCategoriaPesquisa);
			}
		}

		dadosConsultaNegativacaoHelper.setColecaoCategoria(colecaoCategoria);
		// --------------------------------------------------------------------------------------------------------
		// TipoCliente
		// --------------------------------------------------------------------------------------------------------
		String[] arrayTipoClienteForm = form.getArrayTipoCliente();

		String[] arrayTipoCliente = null;

		Collection<String> arrayTipoClienteAux = new ArrayList<String>();

		if(arrayTipoClienteForm != null && !(arrayTipoClienteForm.length <= 0)){
			for(String idTC : arrayTipoClienteForm){
				if(!(idTC.trim().equalsIgnoreCase("")) || !(idTC.trim().length() < 1)){
					arrayTipoClienteAux.add(idTC);
				}
			}

			if(arrayTipoClienteAux != null && !arrayTipoClienteAux.isEmpty()){
				arrayTipoCliente = (String[]) arrayTipoClienteAux.toArray(new String[arrayTipoClienteAux.size()]);
			}
		}

		ClienteTipo tipoClienteColecao = new ClienteTipo();
		tipoClienteColecao.setId(-1);

		Collection colecaoTipoCliente = new ArrayList();
		int o = 0;

		if(arrayTipoCliente == null){
			tipoClienteColecao.setDescricao(TODOS);
			colecaoTipoCliente.add(tipoClienteColecao);
		}else{
			tipoClienteColecao.setDescricao(OPÇÕES_SELECIONADAS);
			colecaoTipoCliente.add(tipoClienteColecao);
			FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();

			for(o = 0; o < arrayTipoCliente.length; o++){
				if(!arrayTipoCliente[o].equals("") && !arrayTipoCliente[o].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(o + 1 < arrayTipoCliente.length){
						filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, arrayTipoCliente[o],
										ConectorOr.CONECTOR_OR, arrayTipoCliente.length));

					}else{
						filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, arrayTipoCliente[o]));
					}
				}
			}

			filtroClienteTipo.setCampoOrderBy(FiltroClienteTipo.DESCRICAO);

			Collection colecaoTipoClientePesquisa = fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName());

			if(colecaoTipoClientePesquisa != null && !colecaoTipoClientePesquisa.isEmpty()){
				colecaoTipoCliente.addAll(colecaoTipoClientePesquisa);
			}
		}

		dadosConsultaNegativacaoHelper.setColecaoClienteTipo(colecaoTipoCliente);
		// --------------------------------------------------------------------------------------------------------
		// Esfera Poder
		// --------------------------------------------------------------------------------------------------------
		String[] arrayEsferaPoderForm = form.getArrayEsferaPoder();

		String[] arrayEsferaPoder = null;

		Collection<String> arrayEsferaPoderAux = new ArrayList<String>();

		if(arrayEsferaPoderForm != null && !(arrayEsferaPoderForm.length <= 0)){
			for(String idEP : arrayEsferaPoderForm){
				if(!(idEP.trim().equalsIgnoreCase("")) || !(idEP.trim().length() < 1)){
					arrayEsferaPoderAux.add(idEP);
				}
			}

			if(arrayEsferaPoderAux != null && !arrayEsferaPoderAux.isEmpty()){
				arrayEsferaPoder = (String[]) arrayEsferaPoderAux.toArray(new String[arrayEsferaPoderAux.size()]);
			}
		}

		EsferaPoder esferaPoderColecao = new EsferaPoder();
		esferaPoderColecao.setId(-1);

		Collection colecaoEsferaPoder = new ArrayList();
		int p = 0;

		if(arrayEsferaPoder == null){
			esferaPoderColecao.setDescricao(TODOS);
			colecaoEsferaPoder.add(esferaPoderColecao);
		}else{
			esferaPoderColecao.setDescricao(OPÇÕES_SELECIONADAS);
			colecaoEsferaPoder.add(esferaPoderColecao);
			FiltroEsferaPoder filtroEsferaPoder = new FiltroEsferaPoder();

			for(p = 0; p < arrayEsferaPoder.length; p++){
				if(!arrayEsferaPoder[p].equals("") && !arrayEsferaPoder[p].equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					if(p + 1 < arrayEsferaPoder.length){
						filtroEsferaPoder.adicionarParametro(new ParametroSimples(FiltroEsferaPoder.ID, arrayEsferaPoder[p],
										ConectorOr.CONECTOR_OR, arrayEsferaPoder.length));

					}else{
						filtroEsferaPoder.adicionarParametro(new ParametroSimples(FiltroEsferaPoder.ID, arrayEsferaPoder[p]));
					}
				}
			}

			filtroEsferaPoder.setCampoOrderBy(FiltroEsferaPoder.DESCRICAO);

			Collection colecaoEsferaPoderPesquisa = fachada.pesquisar(filtroEsferaPoder, EsferaPoder.class.getName());

			if(colecaoEsferaPoderPesquisa != null && !colecaoEsferaPoderPesquisa.isEmpty()){
				colecaoEsferaPoder.addAll(colecaoEsferaPoderPesquisa);
			}
		}

		dadosConsultaNegativacaoHelper.setColecaoEsferaPoder(colecaoEsferaPoder);

		// ************************************************************************************************

		sessao.setAttribute("dadosConsultaNegativacaoHelper", dadosConsultaNegativacaoHelper);

		if(sessao.getAttribute(GERAR_RELATORIO) != null && sessao.getAttribute(GERAR_RELATORIO).equals("nao")){
			retorno = actionMapping.findForward("exibirResumoNegativacaoParametros");

		}else if(sessao.getAttribute(GERAR_RELATORIO) != null
						&& sessao.getAttribute(GERAR_RELATORIO).equals("relatorioAcompanhamentoClientesNegativados")){
			retorno = actionMapping.findForward("gerarRelatorioAcompanhamentoClientesNegativados");

		}else if(sessao.getAttribute(GERAR_RELATORIO) != null
						&& sessao.getAttribute(GERAR_RELATORIO).equals("relatorioNegativacoesExcluidas")){
			retorno = actionMapping.findForward("gerarRelatorioNegativacoesExcluidas");

		}
		return retorno;

	}
}