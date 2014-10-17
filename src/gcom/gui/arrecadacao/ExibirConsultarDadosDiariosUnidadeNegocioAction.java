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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadacaoDadosDiarios;
import gcom.arrecadacao.FiltroArrecadacaoDadosDiarios;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
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
 * @author Rafael Santos
 * @created 03/01/2007
 **/
public class ExibirConsultarDadosDiariosUnidadeNegocioAction
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

		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosDiariosUnidadeNegocio");

		String referencia = httpServletRequest.getParameter("referencia");

		String idGerencia = httpServletRequest.getParameter("idGerencia");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoArrecadacaoDadosDiariosUnidadeNegocio = new ArrayList();

		sessao.removeAttribute("colecaoDadosDiarios");
		sessao.removeAttribute("valorTotal");
		sessao.removeAttribute("valorTotalUnidadeNegocio");

		// String descricao = "ELO"; //colecaoArrecadacaoDadosDiariosUnidadeNegocio =
		// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		// descricao, 0);

		Collection colecaoArrecadacaoDadosDiarios = (Collection) sessao.getAttribute("colecaoArrecadacaoDadosDiarios");

		Iterator iteratorColecaoDadosDiarios = colecaoArrecadacaoDadosDiarios.iterator();

		ArrecadacaoDadosDiarios arrecadacaoDadosDiarios = null;

		BigDecimal valorTotal = new BigDecimal("0.00");

		Collection colecaoIdUnidadeNegocio = new ArrayList();

		while(iteratorColecaoDadosDiarios.hasNext()){

			arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios) iteratorColecaoDadosDiarios.next();

			if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
							&& arrecadacaoDadosDiarios.getGerenciaRegional().getId().toString().equals(idGerencia)){

				// acumula o vlor do pagamento
				if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
					valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
				}

				boolean naoAchou = true;
				if(!colecaoIdUnidadeNegocio.isEmpty()){
					Iterator iterator = colecaoIdUnidadeNegocio.iterator();

					while(iterator.hasNext() && naoAchou){
						UnidadeNegocio unidadeNegocio = (UnidadeNegocio) iterator.next();

						if(unidadeNegocio.getId().equals(arrecadacaoDadosDiarios.getUnidadeNegocio().getId())){
							naoAchou = false;
							arrecadacaoDadosDiarios.setUnidadeNegocio(unidadeNegocio);
						}
					}
				}

				if(naoAchou){
					// pesquisar na base a gerencia
					FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

					filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, arrecadacaoDadosDiarios
									.getUnidadeNegocio().getId()));

					Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

					UnidadeNegocio unidadeNegocio = (UnidadeNegocio) colecaoUnidadeNegocio.iterator().next();

					arrecadacaoDadosDiarios.getUnidadeNegocio().setNome(unidadeNegocio.getNome());

					colecaoIdUnidadeNegocio.add(unidadeNegocio);
				}

				if(colecaoArrecadacaoDadosDiariosUnidadeNegocio.isEmpty()){

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
					arre.setUnidadeNegocio(arrecadacaoDadosDiarios.getUnidadeNegocio());

					colecaoArrecadacaoDadosDiariosUnidadeNegocio.add(arre);
				}else{

					Iterator iteratorcolecaoArrecadacaoDadosDiariosUnidadeNegocio = colecaoArrecadacaoDadosDiariosUnidadeNegocio.iterator();

					boolean acumular = false;
					ArrecadacaoDadosDiarios arrecadacaoDadosDiariosSubstituir = null;
					boolean achou = true;
					while(iteratorcolecaoArrecadacaoDadosDiariosUnidadeNegocio.hasNext() && achou){
						acumular = false;

						arrecadacaoDadosDiariosSubstituir = (ArrecadacaoDadosDiarios) iteratorcolecaoArrecadacaoDadosDiariosUnidadeNegocio
										.next();

						if(arrecadacaoDadosDiarios.getUnidadeNegocio() != null
										&& arrecadacaoDadosDiariosSubstituir.getUnidadeNegocio() != null){
							// se a unidade negocio for igual
							if(arrecadacaoDadosDiarios.getUnidadeNegocio().getId().equals(
											arrecadacaoDadosDiariosSubstituir.getUnidadeNegocio().getId())){
								acumular = true;
								achou = false;
							}
						}
					}// fim while

					// verfica se � para acumular
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
						arre.setUnidadeNegocio(arrecadacaoDadosDiarios.getUnidadeNegocio());

						// se for diferente
						colecaoArrecadacaoDadosDiariosUnidadeNegocio.add(arre);
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

		if(colecaoArrecadacaoDadosDiariosUnidadeNegocio != null){
			sessao.setAttribute("colecaoDadosDiarios", colecaoArrecadacaoDadosDiariosUnidadeNegocio);
			sessao.setAttribute("valorTotalGerencia", valorTotal);
			sessao.setAttribute("valorTotalUnidadeNegocio", valorTotal);
		}

		sessao.setAttribute("idGerencia", idGerencia);
		sessao.setAttribute("referencia", referencia);
		return retorno;
	}
}