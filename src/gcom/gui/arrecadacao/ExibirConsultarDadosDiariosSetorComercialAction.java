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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadacaoDadosDiarios;
import gcom.arrecadacao.FiltroArrecadacaoDadosDiarios;
import gcom.cadastro.localidade.*;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Bruno Ferreira dos Santos
 * @created 26 de Maio de 2006
 **/
public class ExibirConsultarDadosDiariosSetorComercialAction
				extends GcomAction {

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

		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosDiariosSetorComercial");

		String referencia = httpServletRequest.getParameter("referencia");

		String idGerencia = httpServletRequest.getParameter("idGerencia");

		String idEloPopup = httpServletRequest.getParameter("idEloPopup");

		String idUnidadeNegocio = httpServletRequest.getParameter("idUnidadeNegocio");

		String idLocalidade = httpServletRequest.getParameter("idLocalidade");

		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		BigDecimal valorTotal = new BigDecimal("0.00");
		BigDecimal valorTotalElo = (BigDecimal) sessao.getAttribute("valorTotalElo");

		Collection colecaoArrecadacaoDadosDiariosSetorComercial = new ArrayList();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples("id", idEloPopup));

		Collection colecaoElo = getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoElo != null && !colecaoElo.isEmpty()){
			Localidade elo = (Localidade) colecaoElo.iterator().next();
			httpServletRequest.setAttribute("nomeElo", elo.getDescricao());
		}

		filtroLocalidade.limparListaParametros();

		filtroLocalidade.adicionarParametro(new ParametroSimples("id", idLocalidade));

		Collection colecaoLocalidadeDescricao = getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(!Util.isVazioOrNulo(colecaoLocalidadeDescricao)){
			Localidade loc = (Localidade) colecaoLocalidadeDescricao.iterator().next();
			httpServletRequest.setAttribute("nomeLocalidade", loc.getDescricao());
		}

		Collection colecaoArrecadacaoDadosDiarios = (Collection) sessao.getAttribute("colecaoArrecadacaoDadosDiarios");

		Iterator iteratorColecaoDadosDiarios = colecaoArrecadacaoDadosDiarios.iterator();

		ArrecadacaoDadosDiarios arrecadacaoDadosDiarios = null;

		Collection colecaoIdSetorComercial = new ArrayList();

		while(iteratorColecaoDadosDiarios.hasNext()){

			arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios) iteratorColecaoDadosDiarios.next();

			if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
							&& arrecadacaoDadosDiarios.getGerenciaRegional().getId().toString().equals(idGerencia)
							&& arrecadacaoDadosDiarios.getSetorComercial() != null
							&& arrecadacaoDadosDiarios.getSetorComercial().getLocalidade() != null
							&& arrecadacaoDadosDiarios.getSetorComercial().getLocalidade().getId().toString().equals(idLocalidade)){

				if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
					valorTotalElo = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
				}

			}

			if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
							&& arrecadacaoDadosDiarios.getSetorComercial() != null
							&& arrecadacaoDadosDiarios.getSetorComercial().getLocalidade() != null
							&& arrecadacaoDadosDiarios.getSetorComercial().getLocalidade().getId().toString().equals(idLocalidade)){

				if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
					valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
				}

				boolean naoAchou = true;
				if(!colecaoIdSetorComercial.isEmpty()){
					Iterator iterator = colecaoIdSetorComercial.iterator();

					while(iterator.hasNext() && naoAchou){
						SetorComercial setorComercial = (SetorComercial) iterator.next();

						if(setorComercial.getId().equals(arrecadacaoDadosDiarios.getSetorComercial().getId())
										&& setorComercial.getLocalidade().getId().equals(
														arrecadacaoDadosDiarios.getSetorComercial().getLocalidade().getId())){
							naoAchou = false;
							arrecadacaoDadosDiarios.getSetorComercial().setDescricao(setorComercial.getDescricao());
						}
					}
				}

				if(naoAchou){
					// pesquisar na base a gerencia
					FiltroSetorComercial filtroSetorComercial2 = new FiltroSetorComercial();
					filtroSetorComercial2
									.adicionarParametro(new ParametroSimples("id", arrecadacaoDadosDiarios.getSetorComercial().getId()));
					filtroSetorComercial2.adicionarParametro(new ParametroSimples("localidade.id", arrecadacaoDadosDiarios.getLocalidade()
									.getId()));

					Collection colecaoSetorComercial = getFachada().pesquisar(filtroSetorComercial2, SetorComercial.class.getName());

					if(!Util.isVazioOrNulo(colecaoSetorComercial)){

						SetorComercial setorComercial = (SetorComercial) colecaoSetorComercial.iterator().next();

						arrecadacaoDadosDiarios.getSetorComercial().setDescricao(setorComercial.getDescricao());

						colecaoIdSetorComercial.add(setorComercial);
					}
				}

				if(colecaoArrecadacaoDadosDiariosSetorComercial.isEmpty()){

					ArrecadacaoDadosDiarios arre = new ArrecadacaoDadosDiarios();

					arre.setId(arrecadacaoDadosDiarios.getId());
					arre.setAnoMesReferenciaArrecadacao(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao());
					arre.setCodigoSetorComercial(arrecadacaoDadosDiarios.getCodigoSetorComercial());
					arre.setNumeroQuadra(arrecadacaoDadosDiarios.getNumeroQuadra());
					arre.setIndicadorHidrometro(arrecadacaoDadosDiarios.getIndicadorHidrometro());
					arre.setDataPagamento(arrecadacaoDadosDiarios.getDataPagamento());
					arre.setQuantidadePagamentos(arrecadacaoDadosDiarios.getQuantidadePagamentos());
					arre.setValorPagamentos(arrecadacaoDadosDiarios.getValorPagamentos());
					arre.setRota(arrecadacaoDadosDiarios.getRota());
					arre.setArrecadador(arrecadacaoDadosDiarios.getArrecadador());
					arre.setSetorComercial(arrecadacaoDadosDiarios.getSetorComercial());
					arre.setArrecadacaoForma(arrecadacaoDadosDiarios.getArrecadacaoForma());
					arre.setLigacaoEsgotoSituacao(arrecadacaoDadosDiarios.getLigacaoEsgotoSituacao());
					arre.setDocumentoTipo(arrecadacaoDadosDiarios.getDocumentoTipo());
					arre.setEsferaPoder(arrecadacaoDadosDiarios.getEsferaPoder());
					arre.setImovelPerfil(arrecadacaoDadosDiarios.getImovelPerfil());
					arre.setQuadra(arrecadacaoDadosDiarios.getQuadra());
					arre.setGerenciaRegional(arrecadacaoDadosDiarios.getGerenciaRegional());
					arre.setLocalidade(arrecadacaoDadosDiarios.getLocalidade());
					arre.setLigacaoAguaSituacao(arrecadacaoDadosDiarios.getLigacaoAguaSituacao());
					arre.setCategoria(arrecadacaoDadosDiarios.getCategoria());

					colecaoArrecadacaoDadosDiariosSetorComercial.add(arre);
				}else{

					Iterator iteratorcolecaoArrecadacaoDadosDiariosSetorComercial = colecaoArrecadacaoDadosDiariosSetorComercial.iterator();

					boolean acumular = false;
					ArrecadacaoDadosDiarios arrecadacaoDadosDiariosSubstituir = null;
					boolean achou = true;
					while(iteratorcolecaoArrecadacaoDadosDiariosSetorComercial.hasNext() && achou){
						acumular = false;

						arrecadacaoDadosDiariosSubstituir = (ArrecadacaoDadosDiarios) iteratorcolecaoArrecadacaoDadosDiariosSetorComercial
										.next();

						if(arrecadacaoDadosDiarios.getLocalidade() != null && arrecadacaoDadosDiariosSubstituir.getSetorComercial() != null){
							// se o SetorComercial for igual
							if(arrecadacaoDadosDiarios.getSetorComercial().getId().equals(
											arrecadacaoDadosDiariosSubstituir.getSetorComercial().getId())){

								acumular = true;
								achou = false;
							}
						}
					}// fim while

					// verfica se é para acumular
					if(acumular){
						arrecadacaoDadosDiariosSubstituir.setValorPagamentos(arrecadacaoDadosDiariosSubstituir.getValorPagamentos().add(
										arrecadacaoDadosDiarios.getValorPagamentos()));
					}else{

						ArrecadacaoDadosDiarios arre = new ArrecadacaoDadosDiarios();

						arre.setId(arrecadacaoDadosDiarios.getId());
						arre.setAnoMesReferenciaArrecadacao(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao());
						arre.setCodigoSetorComercial(arrecadacaoDadosDiarios.getCodigoSetorComercial());
						arre.setNumeroQuadra(arrecadacaoDadosDiarios.getNumeroQuadra());
						arre.setIndicadorHidrometro(arrecadacaoDadosDiarios.getIndicadorHidrometro());
						arre.setDataPagamento(arrecadacaoDadosDiarios.getDataPagamento());
						arre.setQuantidadePagamentos(arrecadacaoDadosDiarios.getQuantidadePagamentos());
						arre.setValorPagamentos(arrecadacaoDadosDiarios.getValorPagamentos());
						arre.setRota(arrecadacaoDadosDiarios.getRota());
						arre.setArrecadador(arrecadacaoDadosDiarios.getArrecadador());
						arre.setSetorComercial(arrecadacaoDadosDiarios.getSetorComercial());
						arre.setArrecadacaoForma(arrecadacaoDadosDiarios.getArrecadacaoForma());
						arre.setLigacaoEsgotoSituacao(arrecadacaoDadosDiarios.getLigacaoEsgotoSituacao());
						arre.setDocumentoTipo(arrecadacaoDadosDiarios.getDocumentoTipo());
						arre.setEsferaPoder(arrecadacaoDadosDiarios.getEsferaPoder());
						arre.setImovelPerfil(arrecadacaoDadosDiarios.getImovelPerfil());
						arre.setQuadra(arrecadacaoDadosDiarios.getQuadra());
						arre.setGerenciaRegional(arrecadacaoDadosDiarios.getGerenciaRegional());
						arre.setLocalidade(arrecadacaoDadosDiarios.getLocalidade());
						arre.setLigacaoAguaSituacao(arrecadacaoDadosDiarios.getLigacaoAguaSituacao());
						arre.setCategoria(arrecadacaoDadosDiarios.getCategoria());

						// se for diferente
						colecaoArrecadacaoDadosDiariosSetorComercial.add(arre);
					}
				}
			}
		}

		if(idGerencia != null){
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroArrecadacaoDadosDiarios.ID, idGerencia));

			Collection colecaoGerenciaRegional = getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

			if(colecaoGerenciaRegional != null && !colecaoGerenciaRegional.isEmpty()){

				GerenciaRegional dadosGerencia = (GerenciaRegional) ((List) colecaoGerenciaRegional).get(0);

				if(dadosGerencia.getNome() != null && !dadosGerencia.getNome().equals("")){
					httpServletRequest.setAttribute("nomeGerencia", dadosGerencia.getNome());
				}
			}
		}

		if(idUnidadeNegocio != null){
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, idUnidadeNegocio));

			Collection colecaoUnidadeNegocio = getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(colecaoUnidadeNegocio != null && !colecaoUnidadeNegocio.isEmpty()){

				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) ((List) colecaoUnidadeNegocio).get(0);

				if(unidadeNegocio.getNome() != null && !unidadeNegocio.getNome().equals("")){
					sessao.setAttribute("nomeUnidadeNegocio", unidadeNegocio.getNome());
					httpServletRequest.setAttribute("nomeUnidadeNegocio", unidadeNegocio.getNome());
					httpServletRequest.setAttribute("idUnidadeNegocio", unidadeNegocio.getId().toString());
				}
			}
		}

		if(colecaoArrecadacaoDadosDiariosSetorComercial != null){
			sessao.setAttribute("colecaoDadosDiarios", colecaoArrecadacaoDadosDiariosSetorComercial);
			sessao.setAttribute("valorTotal", valorTotal);
			sessao.setAttribute("valorTotalElo", valorTotalElo);
		}
		sessao.setAttribute("idGerencia", idGerencia);
		sessao.setAttribute("referencia", referencia);
		httpServletRequest.setAttribute("idEloPopup", idEloPopup);
		return retorno;
	}
}