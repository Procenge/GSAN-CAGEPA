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

package gcom.gui.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
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

public class ExibirFiltrarExcecoesLeiturasConsumosLocalidadeAction
				extends GcomAction {

	private Collection colecaoPesquisa = null;

	private String localidadeID = null;

	private String setorComercialCD = null;

	private String setorComercialID = null;

	private String rotaNM = null;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarExcecoesLeiturasConsumosLocalidade");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		//
		// sessao.removeAttribute("leituraConsumoActionForm");
		//

		LeituraConsumoActionForm leituraConsumoActionForm = (LeituraConsumoActionForm) actionForm;

		if(httpServletRequest.getAttribute("objetoConsulta") != null){
			leituraConsumoActionForm.setImovelFiltro("");
			leituraConsumoActionForm.setImovelCondominioFiltro("");
			leituraConsumoActionForm.setLocalidadeFiltro("");
			leituraConsumoActionForm.setSetorComercialFiltro("");
			leituraConsumoActionForm.setQuadraFinalFiltro("");
			leituraConsumoActionForm.setQuadraInicialFiltro("");
			leituraConsumoActionForm.setTipoApresentacao("2");
		}
		if(httpServletRequest.getParameter("menu") != null){
			leituraConsumoActionForm.setTipoApresentacao("2");
		}

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");

		String inscricaoTipo = (String) httpServletRequest.getParameter("inscricaoTipo");

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("") && inscricaoTipo != null
						&& !inscricaoTipo.trim().equalsIgnoreCase("")){

			pesquisarLocalidade(inscricaoTipo, leituraConsumoActionForm, fachada, httpServletRequest);
			pesquisarLocalidade(inscricaoTipo, leituraConsumoActionForm, fachada, httpServletRequest);

			pesquisarSetorComercial(inscricaoTipo, leituraConsumoActionForm, fachada, httpServletRequest);
			pesquisarLocalidade(inscricaoTipo, leituraConsumoActionForm, fachada, httpServletRequest);

			pesquisarSetorComercial(inscricaoTipo, leituraConsumoActionForm, fachada, httpServletRequest);
			if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
				pesquisarRota(inscricaoTipo, leituraConsumoActionForm, fachada, httpServletRequest);
			}else if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_ADA.shortValue()){
				pesquisarQuadra(inscricaoTipo, leituraConsumoActionForm, fachada, httpServletRequest);
			}

		}else{
			// sessao.removeAttribute("imovelOutrosCriteriosActionForm");
		}
		//

		// --- Empresa
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Empresa> colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		httpServletRequest.setAttribute("colecaoEmpresa", colecaoEmpresa);

		// --- Faturamento Grupo
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);

		Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);

		// Pesquisar Gerências Regionais
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.ID);

		Collection<GerenciaRegional> colecaoGerenciasRegionais = fachada
						.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		httpServletRequest.setAttribute("colecaoGerenciasRegionais", colecaoGerenciasRegionais);

		String matriculaImovel = leituraConsumoActionForm.getImovelFiltro();
		// String matriculaImovelFiltro = leituraConsumoActionForm
		// .getImovelMatriculaFiltro();
		String matriculaImovelCondominio = leituraConsumoActionForm.getImovelCondominioFiltro();
		String matriculaImovelCondominioFiltro = leituraConsumoActionForm.getImovelMatriculaCondominioFiltro();
		String inscricaoImovel = "";
		if(matriculaImovel != null && !matriculaImovel.trim().equalsIgnoreCase("")){
			// && (matriculaImovelFiltro == null || matriculaImovelFiltro
			// .equals(""))
			inscricaoImovel = fachada.pesquisarInscricaoImovel(new Integer(matriculaImovel), true);
			if(inscricaoImovel != null && !inscricaoImovel.trim().equals("")){
				leituraConsumoActionForm.setImovelMatriculaFiltro(inscricaoImovel);
				httpServletRequest.setAttribute("nomeCampo", "imovelCondominioFiltro");
				FaturamentoGrupo faturamentoGrupo = fachada.pesquisarGrupoImovel(new Integer(matriculaImovel));
				leituraConsumoActionForm.setIdGrupoFaturamentoFiltro(faturamentoGrupo.getId().toString());
			}else{
				leituraConsumoActionForm.setImovelFiltro("");
				leituraConsumoActionForm.setImovelMatriculaFiltro("Matrícula do Imóvel inexistente.");
				httpServletRequest.setAttribute("codigoImovelNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "imovelFiltro");
			}
		}

		String inscricaoCondominio = "";
		if(matriculaImovelCondominio != null && !matriculaImovelCondominio.trim().equalsIgnoreCase("")
						&& (matriculaImovelCondominioFiltro == null || matriculaImovelCondominioFiltro.equals(""))){
			inscricaoCondominio = fachada.pesquisarInscricaoImovel(new Integer(matriculaImovelCondominio), true);

			if(inscricaoCondominio != null && !inscricaoCondominio.trim().equals("")){
				leituraConsumoActionForm.setImovelMatriculaCondominioFiltro(inscricaoCondominio);
				httpServletRequest.setAttribute("nomeCampo", "idGrupoFaturamentoFiltro");
				FaturamentoGrupo faturamentoGrupo = fachada.pesquisarGrupoImovel(new Integer(matriculaImovelCondominio));
				leituraConsumoActionForm.setIdGrupoFaturamentoFiltro(faturamentoGrupo.getId().toString());
			}else{
				leituraConsumoActionForm.setImovelCondominioFiltro("");
				leituraConsumoActionForm.setImovelMatriculaCondominioFiltro("Mat. do Imóvel Condomínio inexistente.");
				httpServletRequest.setAttribute("codigoImovelCondominioNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "imovelCondominioFiltro");
			}
		}

		// ===============================================================================
		// leituraConsumoActionForm.setTipoApresentacao("2");
		sessao.setAttribute("leituraConsumoActionForm", leituraConsumoActionForm);

		return retorno;
	}

	private void pesquisarLocalidade(String inscricaoTipo, LeituraConsumoActionForm leituraConsumoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		localidadeID = (String) leituraConsumoActionForm.getLocalidadeFiltro();
		String nomeLocalidade = leituraConsumoActionForm.getNomeLocalidade();

		if(localidadeID != null && !localidadeID.equals("") && (nomeLocalidade == null || nomeLocalidade.equals(""))){

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna localidade
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade nao encontrada
				// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do
				// formulário
				leituraConsumoActionForm.setLocalidadeFiltro("");
				leituraConsumoActionForm.setNomeLocalidade("localidade inexistente");
				httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeFiltro");
				// httpServletRequest.setAttribute("corLocalidadeOrigem","exception");
			}else{
				Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				leituraConsumoActionForm.setLocalidadeFiltro(String.valueOf(objetoLocalidade.getId()));
				leituraConsumoActionForm.setNomeLocalidade(objetoLocalidade.getDescricao());

				httpServletRequest.setAttribute("nomeCampo", "setorComercialFiltro");
				// httpServletRequest.setAttribute("corLocalidadeOrigem",
				// "valor");
			}
		}
	}

	private void pesquisarSetorComercial(String inscricaoTipo, LeituraConsumoActionForm leituraConsumoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		// Recebe o valor do campo localidadeOrigemID do formulário.
		localidadeID = (String) leituraConsumoActionForm.getLocalidadeFiltro();

		setorComercialCD = (String) leituraConsumoActionForm.getSetorComercialFiltro();
		String nomeSetorComercial = leituraConsumoActionForm.getSetorComercialNome();

		// O campo localidadeOrigemID será obrigatório
		if(setorComercialCD != null && !setorComercialCD.trim().equalsIgnoreCase("")
						&& (nomeSetorComercial == null || nomeSetorComercial.equals(""))){

			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){
				// Adiciona o id da localidade que está no formulário para
				// compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
			}
			// Adiciona o código do setor comercial que esta no formulário
			// para compor a pesquisa.
			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna setorComercial
			colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Setor Comercial nao encontrado
				// Limpa os campos setorComercialOrigemCD,
				// nomeSetorComercialOrigem e setorComercialOrigemID do
				// formulário
				leituraConsumoActionForm.setSetorComercialFiltro("");
				leituraConsumoActionForm.setSetorComercialID("");
				leituraConsumoActionForm.setSetorComercialNome("Setor comercial inexistente.");
				httpServletRequest.setAttribute("codigoSetorComercialNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialFiltro");
			}else{
				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				leituraConsumoActionForm.setSetorComercialFiltro(String.valueOf(objetoSetorComercial.getCodigo()));
				leituraConsumoActionForm.setSetorComercialID(String.valueOf(objetoSetorComercial.getId()));
				leituraConsumoActionForm.setSetorComercialNome(objetoSetorComercial.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "quadraInicialFiltro");
				// httpServletRequest.setAttribute("corSetorComercialOrigem","valor");
			}
		}
	}

	private void pesquisarQuadra(String inscricaoTipo, LeituraConsumoActionForm leituraConsumoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		short quadraInicial = 0;
		short quadraFinal = 0;

		if(leituraConsumoActionForm.getQuadraInicialFiltro() != null
						&& !leituraConsumoActionForm.getQuadraInicialFiltro().trim().equalsIgnoreCase("")
						&& leituraConsumoActionForm.getQuadraFinalFiltro() != null
						&& !leituraConsumoActionForm.getQuadraFinalFiltro().trim().equalsIgnoreCase("")){
			quadraInicial = (new Short(leituraConsumoActionForm.getQuadraInicialFiltro())).shortValue();
			quadraFinal = (new Short(leituraConsumoActionForm.getQuadraFinalFiltro())).shortValue();
			if(quadraInicial > quadraFinal){
				throw new ActionServletException("atencao.quadra_final_menor");
			}
		}

		leituraConsumoActionForm.setInscricaoTipo("origem");
		// Recebe os valores dos campos setorComercialOrigemCD e
		// setorComercialOrigemID do formulário.
		setorComercialCD = (String) leituraConsumoActionForm.getSetorComercialFiltro();

		setorComercialID = (String) leituraConsumoActionForm.getSetorComercialID();

		rotaNM = (String) leituraConsumoActionForm.getQuadraInicialFiltro();
		String nomeQuadraInicial = leituraConsumoActionForm.getQuadraInicialNome();
		// Os campos setorComercialOrigemCD e setorComercialID serão
		// obrigatórios
		if(rotaNM != null && !rotaNM.trim().equalsIgnoreCase("") && (nomeQuadraInicial == null || nomeQuadraInicial.equals(""))){

			if(setorComercialID != null && !setorComercialID.trim().equalsIgnoreCase("")){
				// Adiciona o id do setor comercial que está no formulário
				// para
				// compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercialID));
			}
			// Adiciona o número da quadra que esta no formulário para
			// compor a pesquisa.
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, rotaNM));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna quadra
			colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Quadra nao encontrada
				// Limpa os campos quadraOrigemNM e quadraOrigemID do
				// formulário
				leituraConsumoActionForm.setQuadraInicialNome("");
				leituraConsumoActionForm.setQuadraInicialFiltro("");
				// Mensagem de tela
				leituraConsumoActionForm.setQuadraInicialMensagem("QUADRA INICIAL INEXISTENTE.");
				httpServletRequest.setAttribute("codigoQuadraInicialNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "quadraInicialFiltro");
			}else{
				Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
				leituraConsumoActionForm.setQuadraInicialFiltro(String.valueOf(objetoQuadra.getNumeroQuadra()));
				leituraConsumoActionForm.setQuadraInicialID(String.valueOf(objetoQuadra.getId()));
				// Mensagem de tela
				leituraConsumoActionForm.setQuadraInicialMensagem("");
				// -----------quadra final recebe o mesmo q a inicial

			}
		}

		// Recebe os valores dos campos setorComercialOrigemCD e
		// setorComercialOrigemID do formulário.
		setorComercialCD = (String) leituraConsumoActionForm.getSetorComercialFiltro();
		setorComercialID = (String) leituraConsumoActionForm.getSetorComercialID();

		rotaNM = (String) leituraConsumoActionForm.getQuadraFinalFiltro();
		String nomeQuadraFinal = leituraConsumoActionForm.getQuadraInicialNome();

		// Os campos setorComercialOrigemCD e setorComercialID serão
		// obrigatórios
		if(rotaNM != null && !rotaNM.trim().equalsIgnoreCase("") && (nomeQuadraFinal == null || nomeQuadraFinal.equals(""))){

			rotaNM = (String) leituraConsumoActionForm.getQuadraFinalFiltro();

			if(setorComercialID != null && !setorComercialID.trim().equalsIgnoreCase("")){
				// Adiciona o id do setor comercial que está no formulário
				// para
				// compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercialID));
			}

			// Adiciona o número da quadra que esta no formulário para
			// compor a pesquisa.
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, rotaNM));

			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna quadra
			colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Quadra nao encontrada
				// Limpa os campos quadraOrigemNM e quadraOrigemID do
				// formulário
				leituraConsumoActionForm.setQuadraFinalNome("");
				leituraConsumoActionForm.setQuadraFinalFiltro("");
				// Mensagem de tela
				leituraConsumoActionForm.setQuadraFinalMensagem("QUADRA FINAL INEXISTENTE.");
				httpServletRequest.setAttribute("codigoQuadraFinalNaoEncontrada", "exception");
			}else{
				Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
				leituraConsumoActionForm.setQuadraFinalID(String.valueOf(objetoQuadra.getId()));
				leituraConsumoActionForm.setQuadraFinalFiltro(String.valueOf(objetoQuadra.getNumeroQuadra()));
				httpServletRequest.setAttribute("nomeCampo", "indicadorImovelCondominioFiltro");
				// Mensagem de tela
				leituraConsumoActionForm.setQuadraFinalMensagem("");
				// httpServletRequest.setAttribute("corQuadraDestino",
				// "valor");
			}
		}

	}

	//

	private void pesquisarRota(String inscricaoTipo, LeituraConsumoActionForm leituraConsumoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroRota filtroRota = new FiltroRota();

		short rotaInicial = 0;
		short rotaFinal = 0;

		if(leituraConsumoActionForm.getRotaInicialFiltro() != null
						&& !leituraConsumoActionForm.getRotaInicialFiltro().trim().equalsIgnoreCase("")
						&& leituraConsumoActionForm.getRotaFinalFiltro() != null
						&& !leituraConsumoActionForm.getRotaFinalFiltro().trim().equalsIgnoreCase("")){
			rotaInicial = (new Short(leituraConsumoActionForm.getRotaInicialFiltro())).shortValue();
			rotaFinal = (new Short(leituraConsumoActionForm.getRotaFinalFiltro())).shortValue();
			if(rotaInicial > rotaFinal){
				throw new ActionServletException("atencao.rota_final_menor");
			}
		}

		leituraConsumoActionForm.setInscricaoTipo("origem");
		// Recebe os valores dos campos setorComercialOrigemCD e
		// setorComercialOrigemID do formulário.
		setorComercialCD = (String) leituraConsumoActionForm.getSetorComercialFiltro();

		setorComercialID = (String) leituraConsumoActionForm.getSetorComercialID();

		rotaNM = (String) leituraConsumoActionForm.getRotaInicialFiltro();
		String nomeRotaInicial = leituraConsumoActionForm.getRotaInicialNome();
		// Os campos setorComercialOrigemCD e setorComercialID serão
		// obrigatórios
		if(rotaNM != null && !rotaNM.trim().equalsIgnoreCase("") && (nomeRotaInicial == null || nomeRotaInicial.equals(""))){

			if(setorComercialID != null && !setorComercialID.trim().equalsIgnoreCase("")){
				// Adiciona o id do setor comercial que está no formulário
				// para
				// compor a pesquisa.
				filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, setorComercialID));
			}
			// Adiciona o número da rota que esta no formulário para
			// compor a pesquisa.
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaNM));

			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna rota
			colecaoPesquisa = fachada.pesquisar(filtroRota, Rota.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// rota nao encontrada
				// Limpa os campos rotaOrigemNM e rotaOrigemID do
				// formulário
				leituraConsumoActionForm.setRotaInicialNome("");
				leituraConsumoActionForm.setRotaInicialFiltro("");
				// Mensagem de tela
				leituraConsumoActionForm.setRotaInicialMensagem("ROTA INICIAL INEXISTENTE.");
				httpServletRequest.setAttribute("codigoRotaInicialNaoEncontrada", "exception");
				httpServletRequest.setAttribute("nomeCampo", "rotaInicialFiltro");
			}else{
				Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisa);
				leituraConsumoActionForm.setRotaInicialFiltro(String.valueOf(objetoRota.getCodigo()));
				leituraConsumoActionForm.setRotaInicialID(String.valueOf(objetoRota.getId()));
				// Mensagem de tela
				leituraConsumoActionForm.setQuadraInicialMensagem("");
				// -----------Rota final recebe o mesmo q a inicial

			}
		}

		// Recebe os valores dos campos setorComercialOrigemCD e
		// setorComercialOrigemID do formulário.
		setorComercialCD = (String) leituraConsumoActionForm.getSetorComercialFiltro();
		setorComercialID = (String) leituraConsumoActionForm.getSetorComercialID();

		rotaNM = (String) leituraConsumoActionForm.getRotaFinalFiltro();
		String nomeRotaFinal = leituraConsumoActionForm.getRotaFinalNome();

		// Os campos setorComercialOrigemCD e setorComercialID serão
		// obrigatórios
		if(rotaNM != null && !rotaNM.trim().equalsIgnoreCase("") && (nomeRotaFinal == null || nomeRotaFinal.equals(""))){

			rotaNM = (String) leituraConsumoActionForm.getRotaFinalFiltro();

			if(setorComercialID != null && !setorComercialID.trim().equalsIgnoreCase("")){
				// Adiciona o id do setor comercial que está no formulário
				// para compor a pesquisa.
				filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, setorComercialID));
			}

			// Adiciona o número da Rota que esta no formulário para
			// compor a pesquisa.
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, rotaNM));

			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Rota
			colecaoPesquisa = fachada.pesquisar(filtroRota, Rota.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Rota nao encontrada
				// Limpa os campos quadraOrigemNM e quadraOrigemID do
				// formulário
				leituraConsumoActionForm.setRotaFinalNome("");
				leituraConsumoActionForm.setRotaFinalFiltro("");
				// Mensagem de tela
				leituraConsumoActionForm.setRotaFinalMensagem("ROTA FINAL INEXISTENTE.");
				httpServletRequest.setAttribute("codigoRotaFinalNaoEncontrada", "exception");
			}else{
				Rota objetoRota = (Rota) Util.retonarObjetoDeColecao(colecaoPesquisa);
				leituraConsumoActionForm.setRotaFinalID(String.valueOf(objetoRota.getId()));
				leituraConsumoActionForm.setRotaFinalFiltro(String.valueOf(objetoRota.getCodigo()));
				httpServletRequest.setAttribute("nomeCampo", "indicadorImovelCondominioFiltro");
				// Mensagem de tela
				leituraConsumoActionForm.setRotaFinalMensagem("");
				// httpServletRequest.setAttribute("corQuadraDestino",
				// "valor");
			}
		}

	}
	//
}
