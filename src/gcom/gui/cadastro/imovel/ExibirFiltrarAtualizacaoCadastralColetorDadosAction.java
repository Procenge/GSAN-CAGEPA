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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
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
 * [UC3112] Atualizacao Cadastral Coletor de Dados
 * 
 * @author Victon Malcolm
 * @since 02/10/2013
 */

public class ExibirFiltrarAtualizacaoCadastralColetorDadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirFiltrarAtualizacaoCadastralColetorDados");
		// Flag indicando que o usuário fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
		FiltrarAtualizacaoCadastralColetorDadosActionForm form = (FiltrarAtualizacaoCadastralColetorDadosActionForm) actionForm;

		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("1"))){

			this.pesquisarImovel(form, httpServletRequest);
		}
		// Pesquisar Localidade Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("2"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(form, httpServletRequest);
		}

		// Pesquisar Setor Comercial Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("3"))){

			// Faz a consulta de Setor ComercialInicial
			this.pesquisarSetorComercial(form, httpServletRequest);
		}
		// Pesquisar Setor Comercial Inicial
		if(objetoConsulta != null && !objetoConsulta.trim().equals("") && (objetoConsulta.trim().equals("4"))){

			// Faz a consulta de Setor ComercialInicial
			this.pesquisarRota(form, httpServletRequest);
		}

		// Seta os request´s encontrados
		return retorno;
	}

	private void pesquisarImovel(FiltrarAtualizacaoCadastralColetorDadosActionForm form, HttpServletRequest httpServletRequest){

		Fachada fachada = Fachada.getInstancia();
		FiltroImovel filtroImovel = new FiltroImovel();
		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, form.getMatriculaImovel()));

		// Recupera Imóvel
		Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		HttpSession sessao = httpServletRequest.getSession();
		sessao.removeAttribute("inscricaoImovelEncontrada");
		if(colecaoImovel != null && !colecaoImovel.isEmpty()){
			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);
			this.preencherForm(form, imovel.getId().toString(), fachada.pesquisarInscricaoImovel(imovel.getId(), true), "", "", "", "", "",
							"");
			sessao.setAttribute("inscricaoImovelEncontrada", "true");
		}else{
			form.setMatriculaImovel("");
			form.setInscricaoImovel("Imovel inexistente");
		}

	}

	private void pesquisarLocalidade(FiltrarAtualizacaoCadastralColetorDadosActionForm form, HttpServletRequest httpServletRequest){

		Integer loca_i = Integer.parseInt(form.getLocalidade());

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, loca_i));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
		HttpSession sessao = httpServletRequest.getSession();
		sessao.removeAttribute("localidadeEncontrada");
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
			this.preencherForm(form, "", "", localidade.getId().toString(), localidade.getDescricao(), "", "", "", "");
			sessao.setAttribute("localidadeEncontrada", "true");
		}else{
			form.setLocalidade("");
			form.setNomeLocalidade("Localidade inexistente");
		}
	}

	private void pesquisarSetorComercial(FiltrarAtualizacaoCadastralColetorDadosActionForm form, HttpServletRequest httpServletRequest){

		Integer loca_i = null;
		if(form.getLocalidade() != null && !form.getLocalidade().equals("")){
			loca_i = Integer.parseInt(form.getLocalidade());
		}else{
			throw new ActionServletException("atencao.naoinformado", "Localidade");
		}

		Integer setor_i = Integer.parseInt(form.getSetorComercial());

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor_i));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, loca_i));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());
		HttpSession sessao = httpServletRequest.getSession();
		sessao.removeAttribute("setorComercialEncontrado");
		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){
			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
			form.setSetorComercial(Integer.toString(setorComercial.getCodigo()));
			form.setNomeSetorComercial(setorComercial.getDescricao());
			sessao.setAttribute("setorComercialEncontrado", "true");
		}else{
			form.setSetorComercial("");
			form.setNomeSetorComercial("Setor Comercial inexistente");

		}
	}

	private void pesquisarRota(FiltrarAtualizacaoCadastralColetorDadosActionForm form, HttpServletRequest httpServletRequest){

		Integer loca_i = null;
		if(form.getLocalidade() != null && !form.getLocalidade().equals("")){
			loca_i = Integer.parseInt(form.getLocalidade());
		}else{
			throw new ActionServletException("atencao.naoinformado", "Localidade");
		}
		Integer setor_i = null;
		if(form.getSetorComercial() != null && !form.getSetorComercial().equals("")){
			setor_i = Integer.parseInt(form.getSetorComercial());
		}else{
			throw new ActionServletException("atencao.naoinformado", "Setor Comercial");
		}
		Integer rota_i = Integer.parseInt(form.getRota());
		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE, loca_i));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, setor_i));
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, rota_i));

		// Recupera Localidade
		Collection colecaoRota = this.getFachada().pesquisar(filtroRota, Rota.class.getName());
		HttpSession sessao = httpServletRequest.getSession();
		sessao.removeAttribute("RotaEncontrada");
		if(colecaoRota != null && !colecaoRota.isEmpty()){
			Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoRota);
			form.setRota(Integer.toString(rota.getId()));
			form.setNomeRota(rota.getCodigo().toString());
			sessao.setAttribute("rotaEncontrado", "true");
		}else{
			form.setRota("");
			form.setNomeRota("Rota inexistente");
		}
	}

	private void preencherForm(FiltrarAtualizacaoCadastralColetorDadosActionForm form, String matricula, String inscricao,
					String localidade, String nomeLocalidade, String setorComercial, String nomeSetor, String rota, String nomeRota){

		form.setMatriculaImovel(matricula);
		form.setInscricaoImovel(inscricao);
		form.setLocalidade(localidade);
		form.setNomeLocalidade(nomeLocalidade);
		form.setSetorComercial(setorComercial);
		form.setNomeSetorComercial(nomeSetor);
		form.setRota(rota);
		form.setNomeRota(nomeRota);
	}
}
