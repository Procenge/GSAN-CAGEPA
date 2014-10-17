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
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.bean.ArrecadacaoDadosDiariosHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

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
public class ExibirConsultarDadosDiariosConcessionariaPorArrecadadorAction
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

		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosDiariosConcessionariaPorArrecadador");

		String referencia = httpServletRequest.getParameter("referencia");

		String idConcessionaria = httpServletRequest.getParameter("idConcessionaria");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoArrecadacaoDadosDiariosArrecadador = new ArrayList();

		BigDecimal valorTotal = new BigDecimal("0.00");

		Collection colecaoArrecadacaoDadosDiarios = (Collection) sessao.getAttribute("colecaoArrecadacaoDadosDiarios");

		Iterator iteratorColecaoDadosDiarios = colecaoArrecadacaoDadosDiarios.iterator();

		ArrecadacaoDadosDiarios arrecadacaoDadosDiarios = null;

		Collection colecaoIdArrecadador = new ArrayList();

		ArrecadacaoDadosDiariosHelper arrecadacaoDadosDiariosHelper = null;
		Collection colecaoArrecadacaoDadosDiariosHelper = new ArrayList();

		while(iteratorColecaoDadosDiarios.hasNext()){

			arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios) iteratorColecaoDadosDiarios.next();

			if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
							&& arrecadacaoDadosDiarios.getConcessionaria().getId().equals(Integer.valueOf(idConcessionaria))){

				if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
					valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
				}

				boolean naoAchou = true;
				if(!colecaoIdArrecadador.isEmpty()){
					Iterator iterator = colecaoIdArrecadador.iterator();

					while(iterator.hasNext() && naoAchou){
						Arrecadador arrecadador = (Arrecadador) iterator.next();

						if(arrecadador.getId().equals(arrecadacaoDadosDiarios.getArrecadador().getId())){
							naoAchou = false;
							arrecadacaoDadosDiarios.getArrecadador().setCliente(arrecadador.getCliente());
						}
					}
				}

				if(naoAchou){
					// pesquisar na base a gerencia
					FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
					filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.ID, arrecadacaoDadosDiarios
									.getArrecadador().getId()));
					filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");

					Collection colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

					Arrecadador arrecadador = (Arrecadador) colecaoArrecadador.iterator().next();

					arrecadacaoDadosDiarios.getArrecadador().setCliente(arrecadador.getCliente());

					colecaoIdArrecadador.add(arrecadador);
				}

				if(colecaoArrecadacaoDadosDiariosArrecadador.isEmpty()){

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
					arre.setConcessionaria(arrecadacaoDadosDiarios.getConcessionaria());

					colecaoArrecadacaoDadosDiariosArrecadador.add(arre);
				}else{

					Iterator iteratorcolecaoArrecadacaoDadosDiariosArrecadador = colecaoArrecadacaoDadosDiariosArrecadador.iterator();

					boolean acumular = false;
					ArrecadacaoDadosDiarios arrecadacaoDadosDiariosSubstituir = null;
					boolean achou = true;
					while(iteratorcolecaoArrecadacaoDadosDiariosArrecadador.hasNext() && achou){
						acumular = false;

						arrecadacaoDadosDiariosSubstituir = (ArrecadacaoDadosDiarios) iteratorcolecaoArrecadacaoDadosDiariosArrecadador
										.next();

						if(arrecadacaoDadosDiarios.getArrecadador() != null && arrecadacaoDadosDiariosSubstituir.getArrecadador() != null){
							// se o Elo for igual
							if(arrecadacaoDadosDiarios.getArrecadador().getId()
											.equals(arrecadacaoDadosDiariosSubstituir.getArrecadador().getId())){

								acumular = true;
								achou = false;
							}
						}
					}// fim while

					// verfica se � para acumular
					if(acumular){

						arrecadacaoDadosDiariosSubstituir.setValorPagamentos(arrecadacaoDadosDiariosSubstituir.getValorPagamentos().add(
										arrecadacaoDadosDiarios.getValorPagamentos()));

						arrecadacaoDadosDiariosSubstituir.setQuantidadePagamentos(arrecadacaoDadosDiariosSubstituir
										.getQuantidadePagamentos() + arrecadacaoDadosDiarios.getQuantidadePagamentos());

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
						arre.setConcessionaria(arrecadacaoDadosDiarios.getConcessionaria());

						// se for diferente
						colecaoArrecadacaoDadosDiariosArrecadador.add(arre);
					}
				}
			}
		}

		Collections.sort((List) colecaoArrecadacaoDadosDiariosArrecadador, new Comparator() {

			public int compare(Object a, Object b){

				String codigo1 = null;
				if(((ArrecadacaoDadosDiarios) a).getArrecadador() != null
								&& ((ArrecadacaoDadosDiarios) a).getArrecadador().getCliente() != null){
					codigo1 = ((ArrecadacaoDadosDiarios) a).getArrecadador().getCliente().getNome();
				}

				String codigo2 = null;
				if(((ArrecadacaoDadosDiarios) b).getArrecadador() != null
								&& ((ArrecadacaoDadosDiarios) b).getArrecadador().getCliente() != null){
					codigo2 = ((ArrecadacaoDadosDiarios) b).getArrecadador().getCliente().getNome();
				}

				if(codigo1 == null || codigo1.equals("")){
					return -1;
				}else{
					return codigo1.compareTo(codigo2);
				}
			}
		});

		for(Object object : colecaoArrecadacaoDadosDiariosArrecadador){

			arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios) object;


			arrecadacaoDadosDiariosHelper = converter(arrecadacaoDadosDiarios);


			colecaoArrecadacaoDadosDiariosHelper.add(arrecadacaoDadosDiariosHelper);

		}

		sessao.setAttribute("colecaoDadosDiarios", colecaoArrecadacaoDadosDiariosHelper);

		sessao.setAttribute("referencia", referencia);
		sessao.setAttribute("valorTotal", valorTotal);

		return retorno;
	}

	private ArrecadacaoDadosDiariosHelper converter(ArrecadacaoDadosDiarios arrecadacaoDadosDiarios){

		ArrecadacaoDadosDiariosHelper arrecadacaoDadosDiariosHelper = new ArrecadacaoDadosDiariosHelper(arrecadacaoDadosDiarios.getId(),
						arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao(), arrecadacaoDadosDiarios.getCodigoSetorComercial(),
						arrecadacaoDadosDiarios.getNumeroQuadra(), arrecadacaoDadosDiarios.getIndicadorHidrometro(),
						arrecadacaoDadosDiarios.getDataPagamento(), arrecadacaoDadosDiarios.getQuantidadePagamentos(),
						arrecadacaoDadosDiarios.getValorPagamentos(), arrecadacaoDadosDiarios.getRota(),
						arrecadacaoDadosDiarios.getArrecadador(), arrecadacaoDadosDiarios.getSetorComercial(),
						arrecadacaoDadosDiarios.getArrecadacaoForma(), arrecadacaoDadosDiarios.getLigacaoEsgotoSituacao(),
						arrecadacaoDadosDiarios.getDocumentoTipo(), arrecadacaoDadosDiarios.getEsferaPoder(),
						arrecadacaoDadosDiarios.getImovelPerfil(), arrecadacaoDadosDiarios.getQuadra(),
						arrecadacaoDadosDiarios.getGerenciaRegional(), arrecadacaoDadosDiarios.getLocalidade(),
						arrecadacaoDadosDiarios.getLigacaoAguaSituacao(), arrecadacaoDadosDiarios.getCategoria(),
						arrecadacaoDadosDiarios.getUnidadeNegocio(), arrecadacaoDadosDiarios.getConcessionaria());

		return arrecadacaoDadosDiariosHelper;

	}

}