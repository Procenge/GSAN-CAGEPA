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
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
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
 * @author Fernanda Paiva
 * @created 26 de Maio de 2006
 **/
public class ExibirConsultarDadosDiariosLocalidadeAction
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

		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosDiariosLocalidade");

		String referencia = httpServletRequest.getParameter("referencia");

		String idGerencia = httpServletRequest.getParameter("idGerencia");

		String idEloPopup = httpServletRequest.getParameter("idEloPopup");

		String idUnidadeNegocio = httpServletRequest.getParameter("idUnidadeNegocio");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Collection colecaoArrecadacaoDadosDiarios = null;

		// Collection colecaoDadosDiarios = new ArrayList();

		// colecaoArrecadacaoDadosDiarios = (Collection) sessao
		// .getAttribute("colecaoArrecadacaoDadosDiarios");

		BigDecimal valorTotal = new BigDecimal("0.00");
		BigDecimal valorTotalElo = (BigDecimal) sessao.getAttribute("valorTotalElo");

		Collection colecaoArrecadacaoDadosDiariosLocalidade = new ArrayList();

		String descricao = "LOCALIDADE";
		// colecaoArrecadacaoDadosDiariosLocalidade =
		// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		// descricao, Integer.parseInt(idEloPopup));

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples("id", idEloPopup));

		Collection colecaoElo = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoElo != null && !colecaoElo.isEmpty()){
			Localidade elo = (Localidade) colecaoElo.iterator().next();
			httpServletRequest.setAttribute("nomeElo", elo.getDescricao());
		}

		Collection colecaoArrecadacaoDadosDiarios = (Collection) sessao.getAttribute("colecaoArrecadacaoDadosDiarios");

		Iterator iteratorColecaoDadosDiarios = colecaoArrecadacaoDadosDiarios.iterator();

		ArrecadacaoDadosDiarios arrecadacaoDadosDiarios = null;

		Collection colecaoIdLocalidade = new ArrayList();

		while(iteratorColecaoDadosDiarios.hasNext()){

			arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios) iteratorColecaoDadosDiarios.next();

			if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
							&& arrecadacaoDadosDiarios.getGerenciaRegional().getId().toString().equals(idGerencia)
							&& arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId().toString().equals(idEloPopup)){

				if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
					valorTotalElo = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
				}

			}

			if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
			// &&
							// arrecadacaoDadosDiarios.getGerenciaRegional().getId().toString().equals(idGerencia)
							&& arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId().toString().equals(idEloPopup)){

				if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
					valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
				}

				boolean naoAchou = true;
				if(!colecaoIdLocalidade.isEmpty()){
					Iterator iterator = colecaoIdLocalidade.iterator();

					while(iterator.hasNext() && naoAchou){
						Localidade localidade = (Localidade) iterator.next();

						if(localidade.getId().equals(arrecadacaoDadosDiarios.getLocalidade().getId()) &&

						localidade.getLocalidade().getId().equals(arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId())){
							naoAchou = false;
							arrecadacaoDadosDiarios.getLocalidade().setDescricao(localidade.getDescricao());
						}
					}
				}

				if(naoAchou){
					// pesquisar na base a gerencia
					FiltroLocalidade filtroLocalidade2 = new FiltroLocalidade();
					filtroLocalidade2.adicionarParametro(new ParametroSimples("id", arrecadacaoDadosDiarios.getLocalidade().getId()));
					filtroLocalidade2.adicionarParametro(new ParametroSimples("localidade.id", arrecadacaoDadosDiarios.getLocalidade()
									.getLocalidade().getId()));
					// filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

					Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade2, Localidade.class.getName());

					Localidade localidade = (Localidade) colecaoLocalidade.iterator().next();

					arrecadacaoDadosDiarios.getLocalidade().setDescricao(localidade.getDescricao());

					colecaoIdLocalidade.add(localidade);
				}

				if(colecaoArrecadacaoDadosDiariosLocalidade.isEmpty()){

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
					arre.setSetorComercial(arrecadacaoDadosDiarios.getSetorComercial());
					arre.setLigacaoAguaSituacao(arrecadacaoDadosDiarios.getLigacaoAguaSituacao());
					arre.setCategoria(arrecadacaoDadosDiarios.getCategoria());
					arre.setUnidadeNegocio(arrecadacaoDadosDiarios.getUnidadeNegocio());

					colecaoArrecadacaoDadosDiariosLocalidade.add(arre);
				}else{

					Iterator iteratorcolecaoArrecadacaoDadosDiariosLocalidade = colecaoArrecadacaoDadosDiariosLocalidade.iterator();

					boolean acumular = false;
					ArrecadacaoDadosDiarios arrecadacaoDadosDiariosSubstituir = null;
					boolean achou = true;
					while(iteratorcolecaoArrecadacaoDadosDiariosLocalidade.hasNext() && achou){
						acumular = false;

						arrecadacaoDadosDiariosSubstituir = (ArrecadacaoDadosDiarios) iteratorcolecaoArrecadacaoDadosDiariosLocalidade
										.next();

						if( /*
							 * arrecadacaoDadosDiarios.getGerenciaRegional() != null &&
							 * arrecadacaoDadosDiariosSubstituir.getGerenciaRegional() != null &&
							 */
						arrecadacaoDadosDiarios.getLocalidade() != null && arrecadacaoDadosDiariosSubstituir.getLocalidade() != null){
							// se o Elo for igual
							if(/*
								 * arrecadacaoDadosDiarios.getGerenciaRegional().getId()
								 * .equals(arrecadacaoDadosDiariosSubstituir.getGerenciaRegional()
								 * .getId()) &&
								 */
							arrecadacaoDadosDiarios.getLocalidade().getId().equals(
											arrecadacaoDadosDiariosSubstituir.getLocalidade().getId())){
								// if (arrecadacaoDadosDiarios.getArrecadador().getId()
								// .toString().equals(arrecadacaoDadosDiariosSubstituir.getArrecadador()
								// .getId().toString())) {

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
						arre.setSetorComercial(arrecadacaoDadosDiarios.getSetorComercial());
						arre.setLigacaoAguaSituacao(arrecadacaoDadosDiarios.getLigacaoAguaSituacao());
						arre.setCategoria(arrecadacaoDadosDiarios.getCategoria());
						arre.setUnidadeNegocio(arrecadacaoDadosDiarios.getUnidadeNegocio());

						// se for diferente
						colecaoArrecadacaoDadosDiariosLocalidade.add(arre);
					}
				}
			}
		}

		if(idGerencia != null){
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroArrecadacaoDadosDiarios.ID, idGerencia));

			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

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

			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

			if(colecaoUnidadeNegocio != null && !colecaoUnidadeNegocio.isEmpty()){

				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) ((List) colecaoUnidadeNegocio).get(0);

				if(unidadeNegocio.getNome() != null && !unidadeNegocio.getNome().equals("")){
					sessao.setAttribute("nomeUnidadeNegocio", unidadeNegocio.getNome());
					httpServletRequest.setAttribute("nomeUnidadeNegocio", unidadeNegocio.getNome());
					httpServletRequest.setAttribute("idUnidadeNegocio", unidadeNegocio.getId().toString());
				}
			}
		}

		if(colecaoArrecadacaoDadosDiariosLocalidade != null){
			sessao.setAttribute("colecaoDadosDiarios", colecaoArrecadacaoDadosDiariosLocalidade);
			sessao.setAttribute("valorTotal", valorTotal);
			sessao.setAttribute("valorTotalElo", valorTotalElo);
		}
		sessao.setAttribute("idGerencia", idGerencia);
		sessao.setAttribute("referencia", referencia);
		httpServletRequest.setAttribute("idEloPopup", idEloPopup);
		return retorno;
	}
}