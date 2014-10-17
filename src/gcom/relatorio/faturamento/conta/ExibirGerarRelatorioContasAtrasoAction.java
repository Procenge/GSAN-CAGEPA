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

package gcom.relatorio.faturamento.conta;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroSubCategoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
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

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Gerar Relatório de Contas em Atraso
 * 
 * @author Diogo Monteiro
 * @date 21/03/2012
 */

public class ExibirGerarRelatorioContasAtrasoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContasAtrasoAction");

		// GerarRelatorioContasAtrasoDynaActionForm form =
		// (GerarRelatorioContasAtrasoDynaActionForm) actionForm;

		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("1"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidadeInicial(actionForm, objetoConsulta);
		}

		// Pesquisar Localidade Final
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("3"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidadeFinal(actionForm, objetoConsulta);
		}

		// Pesquisar Setor Comercial Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("2"))){

			// Faz a consulta de Setor ComercialInicial
			this.pesquisarSetorComercialInicial(actionForm, objetoConsulta);
		}

		// Pesquisar Setor Comercial Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("4"))){

			// Faz a consulta de Setor ComercialInicial
			this.pesquisarSetorComercialFinal(actionForm, objetoConsulta);
		}

		// Pesquisar Cliente Responsável Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("5"))){

			// Faz a consulta de Cliente Responsável
			this.pesquisarClienteResponsavelInicial(actionForm, objetoConsulta);
		}

		// Pesquisar Cliente Responsável Final
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("6"))){

			// Faz a consulta de Cliente Responsável
			this.pesquisarClienteResponsavelFinal(actionForm, objetoConsulta);
		}

		this.pesquisarGerenciaRegional(httpServletRequest);
		this.pesquisarUnidadeNegocio(httpServletRequest);
		this.pesquisarLigacaoAguaSituacao(httpServletRequest);
		this.pesquisarCategoria(httpServletRequest);
		this.pesquisarSubcategoria(httpServletRequest);
		this.pesquisarLigacaoEsgotoSituacao(httpServletRequest);
		this.pesquisarGrupoFaturamento(httpServletRequest);

		// Seta os request´s encontrados
		this.setaRequest(httpServletRequest, actionForm);

		configurarCheck(actionForm);
		return retorno;
	}

	private void configurarCheck(ActionForm actionForm){

		DynaActionForm form = (DynaActionForm) actionForm;
		String grande = (String) form.get("GRAND_USU");
		if(Util.isVazioOuBranco(grande)){
			form.set("GRAND_USU", "N");
		}
		String parcela = (String) form.get("IND_PARCEL_ATRASO");
		if(Util.isVazioOuBranco(parcela)){
			form.set("IND_PARCEL_ATRASO", "N");
		}
	}

	private void pesquisarLocalidadeInicial(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer loca_i = (Integer) form.get("LOCA_I");

		if(!objetoConsulta.trim().equals("1")){
			loca_i = (Integer) form.get("LOCA_F");
		}

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_i));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("1")){
				form.set("LOCA_I", localidade.getId());
				form.set("nomeLocalidadeInicial", localidade.getDescricao());
			}

			form.set("LOCA_F", localidade.getId());
			form.set("nomeLocalidadeFinal", localidade.getDescricao());

		}else{
			if(objetoConsulta.trim().equals("1")){
				form.set("LOCA_I", null);
				form.set("nomeLocalidadeInicial", "Localidade Inicial inexistente");

				form.set("LOCA_F", null);
				form.set("nomeLocalidadeFinal", null);
			}else{
				form.set("LOCA_F", null);
				form.set("nomeLocalidadeFinal", "Localidade Final inexistente");
			}
		}
	}

	private void pesquisarLocalidadeFinal(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer loca_f = (Integer) form.get("LOCA_F");

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_f));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("3")){
				form.set("LOCA_F", localidade.getId());
				form.set("nomeLocalidadeFinal", localidade.getDescricao());
			}

		}else{
			form.set("LOCA_F", null);
			form.set("nomeLocalidadeFinal", "Localidade Final inexistente");
		}
	}

	private void pesquisarSetorComercialInicial(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer loca_i = (Integer) form.get("LOCA_I");

		Integer setor_i = (Integer) form.get("SETOR_I");

		if(!objetoConsulta.trim().equals("2")){
			setor_i = (Integer) form.get("SETOR_F");
		}

		if(loca_i == null || loca_i == 0){
			throw new ActionServletException("atencao.naoinformado", "Localidade");
		}

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor_i));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, loca_i));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			if(objetoConsulta.trim().equals("2")){
				form.set("SETOR_I", setorComercial.getCodigo());
				form.set("nomeSetorComercialInicial", setorComercial.getDescricao());
			}

			form.set("SETOR_F", setorComercial.getCodigo());
			form.set("nomeSetorComercialFinal", setorComercial.getDescricao());

		}else{

			if(objetoConsulta.trim().equals("2")){
				form.set("SETOR_I", null);
				form.set("nomeSetorComercialInicial", "Setor Comercial Inicial inexistente");

				form.set("SETOR_F", null);
				form.set("nomeSetorComercialFinal", null);
			}else{
				form.set("SETOR_F", null);
				form.set("nomeSetorComercialFinal", "Setor Comercial Final inexistente");
			}

		}
	}

	private void pesquisarSetorComercialFinal(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		Integer loca_f = (Integer) form.get("LOCA_F");

		Integer setor_f = (Integer) form.get("SETOR_F");

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor_f));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, loca_f));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			if(objetoConsulta.trim().equals("4")){
				form.set("SETOR_F", setorComercial.getCodigo());
				form.set("nomeSetorComercialFinal", setorComercial.getDescricao());
			}

		}else{
			form.set("SETOR_F", null);
			form.set("nomeSetorComercialFinal", "Setor Comercial Final inexistente");

		}
	}

	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.ID);
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		if(!Util.isVazioOrNulo(colecaoGerenciaRegional)){

			for(Object object : colecaoGerenciaRegional){

				GerenciaRegional gerenciaRegional = (GerenciaRegional) object;
				gerenciaRegional.setNome(gerenciaRegional.getId() + " - " + gerenciaRegional.getNome());

			}

			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Gerência Regional");
		}

	}

	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest){

		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.setConsultaSemLimites(true);
		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.ID);
		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		if(!Util.isVazioOrNulo(colecaoUnidadeNegocio)){

			for(Object object : colecaoUnidadeNegocio){

				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) object;
				unidadeNegocio.setNome(unidadeNegocio.getId() + " - " + unidadeNegocio.getNome());

			}

			httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);

		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Unidade Negócio");
		}

	}

	private void pesquisarGrupoFaturamento(HttpServletRequest httpServletRequest){

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();

		filtroFaturamentoGrupo.setConsultaSemLimites(true);
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.ID);
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoFaturamentoGrupo = this.getFachada().pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

		if(!Util.isVazioOrNulo(colecaoFaturamentoGrupo)){

			for(Object object : colecaoFaturamentoGrupo){

				FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) object;
				faturamentoGrupo.setDescricao(faturamentoGrupo.getId() + " - " + faturamentoGrupo.getDescricao());

			}

			httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Grupo Faturamento");
		}
	}

	private void pesquisarLigacaoAguaSituacao(HttpServletRequest httpServletRequest){

		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

		filtroLigacaoAguaSituacao.setConsultaSemLimites(true);
		filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);
		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoSituacaoLigacaoAgua = this.getFachada().pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

		if(colecaoSituacaoLigacaoAgua == null || colecaoSituacaoLigacaoAgua.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Ligaçao de Água");
		}else{
			httpServletRequest.setAttribute("colecaoSituacaoLigacaoAgua", colecaoSituacaoLigacaoAgua);
		}
	}

	private void pesquisarLigacaoEsgotoSituacao(HttpServletRequest httpServletRequest){

		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		filtroLigacaoEsgotoSituacao.setConsultaSemLimites(true);
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);
		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoSituacaoLigacaoEsgoto = this.getFachada().pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());

		if(colecaoSituacaoLigacaoEsgoto == null || colecaoSituacaoLigacaoEsgoto.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Ligaçao de Esgoto");
		}else{
			httpServletRequest.setAttribute("colecaoSituacaoLigacaoEsgoto", colecaoSituacaoLigacaoEsgoto);
		}
	}

	private void setaRequest(HttpServletRequest httpServletRequest, ActionForm actionForm){

		DynaActionForm form = (DynaActionForm) actionForm;

		// Localidade Inicial
		if(form.get("LOCA_I") != null && !form.get("LOCA_I").equals("") && form.get("nomeLocalidadeInicial") != null
						&& !form.get("nomeLocalidadeInicial").equals("")){

			httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

		}else{

			if(form.get("LOCA_F") != null && !form.get("LOCA_F").equals("") && form.get("nomeLocalidadeFinal") != null
							&& !form.get("nomeLocalidadeFinal").equals("")){

				httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");

			}
		}

		// Setor Comercial Inicial
		if(form.get("SETOR_I") != null && !form.get("SETOR_I").equals("") && form.get("nomeSetorComercialInicial") != null
						&& !form.get("nomeSetorComercialInicial").equals("")){

			httpServletRequest.setAttribute("setorComercialInicialEncontrado", "true");
			httpServletRequest.setAttribute("setorComercialFinalEncontrado", "true");

		}else{

			if(form.get("SETOR_F") != null && !form.get("SETOR_F").equals("") && form.get("nomeSetorComercialFinal") != null
							&& !form.get("nomeSetorComercialFinal").equals("")){

				httpServletRequest.setAttribute("setorComercialFinalEncontrado", "true");

			}
		}

		if(form.get("RESP_I") != null && !form.get("RESP_I").equals("")){
			httpServletRequest.setAttribute("clienteInexistente", "true");
		}

		if(form.get("RESP_F") != null && !form.get("RESP_F").equals("")){
			httpServletRequest.setAttribute("clienteInexistente", "true");
		}
	}

	private void pesquisarCategoria(HttpServletRequest httpServletRequest){

		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.setConsultaSemLimites(true);
		filtroCategoria.setCampoOrderBy(FiltroCategoria.CODIGO);
		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoCategoria = this.getFachada().pesquisar(filtroCategoria, Categoria.class.getName());

		if(!Util.isVazioOrNulo(colecaoCategoria)){

			for(Object object : colecaoCategoria){

				Categoria categoria = (Categoria) object;
				categoria.setDescricao(categoria.getId() + " - " + categoria.getDescricao());
			}

			httpServletRequest.setAttribute("colecaoCategoria", colecaoCategoria);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Categoria");

		}
	}

	private void pesquisarSubcategoria(HttpServletRequest httpServletRequest){

		FiltroSubCategoria filtroSubcategoria = new FiltroSubCategoria();

		filtroSubcategoria.setConsultaSemLimites(true);
		filtroSubcategoria.setCampoOrderBy(FiltroSubCategoria.ID);
		filtroSubcategoria.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoSubcategoria = this.getFachada().pesquisar(filtroSubcategoria, Subcategoria.class.getName());

		if(!Util.isVazioOrNulo(colecaoSubcategoria)){

			for(Object object : colecaoSubcategoria){

				Subcategoria subcategoria = (Subcategoria) object;
				subcategoria.setDescricao(subcategoria.getId() + " - " + subcategoria.getDescricao());
			}

			httpServletRequest.setAttribute("colecaoSubcategoria", colecaoSubcategoria);
		}else{
			throw new ActionServletException("atencao.naocadastrado", null, "Subcategoria");
		}
	}

	private void pesquisarClienteResponsavelInicial(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		String resp = String.valueOf(form.get("RESP_I"));

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, resp));

		// Recupera Cliente
		Collection colecaoCliente = this.getFachada().pesquisar(filtroCliente, Cliente.class.getName());

		if(!Util.isVazioOrNulo(colecaoCliente)){

			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

			form.set("RESP_I", cliente.getId().toString());
			form.set("nomeResponsavelInicial", cliente.getNome());

		}else{

			form.set("RESP_I", null);
			form.set("nomeResponsavelInicial", "Responsável inexistente");

		}
	}

	private void pesquisarClienteResponsavelFinal(ActionForm actionForm, String objetoConsulta){

		DynaActionForm form = (DynaActionForm) actionForm;

		String resp = String.valueOf(form.get("RESP_F"));

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, resp));

		// Recupera Cliente
		Collection colecaoCliente = this.getFachada().pesquisar(filtroCliente, Cliente.class.getName());

		if(!Util.isVazioOrNulo(colecaoCliente)){

			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);

			form.set("RESP_F", cliente.getId().toString());
			form.set("nomeResponsavelFinal", cliente.getNome());

		}else{

			form.set("RESP_F", null);
			form.set("nomeResponsavelFinal", "Responsável inexistente");

		}
	}
}
