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
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
 * @created 25 de Maio de 2006
 **/
public class ExibirConsultarDadosDiariosValoresDiariosAction
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

		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosDiariosValoresDiarios");

		String referencia = httpServletRequest.getParameter("referencia");

		String idGerencia = httpServletRequest.getParameter("idGerencia");

		String idUnidadeNegocio = httpServletRequest.getParameter("idUnidadeNegocio");

		String idPerfil = httpServletRequest.getParameter("idPerfil");

		String idDocumento = httpServletRequest.getParameter("idDocumento");

		String idArrecadadorPopup = httpServletRequest.getParameter("idArrecadadorPopup");

		String idCategoria = httpServletRequest.getParameter("idCategoria");

		String idEloPopup = httpServletRequest.getParameter("idEloPopup");

		String idLocalidade = httpServletRequest.getParameter("idLocalidade");

		String idConcessionaria = httpServletRequest.getParameter("idConcessionaria");

		String idSetorComercial = httpServletRequest.getParameter("idSetorComercial");

		String mostraUnidadeGerencia = httpServletRequest.getParameter("mostraUnidadeGerencia");
		httpServletRequest.setAttribute("mostraUnidadeGerencia", mostraUnidadeGerencia);

		Fachada fachada = Fachada.getInstancia();

		// String descricao = "";
		// String nomeAgente = "";

		// Collection colecaoArrecadacaoDadosDiariosValoresDiarios = new ArrayList();
		// Mudar isso quando implementar a parte de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Collection colecaoArrecadacaoDadosDiarios = null;

		// Collection colecaoDadosDiarios = new ArrayList();

		// colecaoArrecadacaoDadosDiarios = (Collection) sessao
		// .getAttribute("colecaoArrecadacaoDadosDiarios");

		BigDecimal valorTotal = new BigDecimal("0.00");
		/*
		 * if(idEloPopup != null && idLocalidade == null)
		 * {
		 * descricao = "VALORESELO";
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		 * descricao, Integer.parseInt(idEloPopup));
		 * }
		 * else if(idEloPopup != null && idLocalidade != null)
		 * {
		 * descricao = "VALORESPORDIA";
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		 * descricao, Integer.parseInt(idLocalidade));
		 * }
		 * else if(idGerencia != null)
		 * {
		 * descricao = "VALORESGERENCIA";
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		 * descricao, 0);
		 * }
		 * else if(idArrecadadorPopup != null)
		 * {
		 * descricao = "VALORESARRECADADOR";
		 * 
		 * httpServletRequest.setAttribute("nomeAgente",httpServletRequest.getParameter("nomeAgente")
		 * );
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt
		 * (referencia),Integer.parseInt(idArrecadadorPopup), descricao, 0);
		 * }
		 * else if(idCategoria != null)
		 * {
		 * descricao = "VALORESCATEGORIA";
		 * httpServletRequest.setAttribute("nomeCategoria",httpServletRequest.getParameter(
		 * "nomeCategoria"));
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idCategoria),
		 * descricao, 0);
		 * }
		 * else if(idPerfil != null)
		 * {
		 * descricao = "VALORESPERFIL";
		 * 
		 * httpServletRequest.setAttribute("nomePerfil",httpServletRequest.getParameter("nomePerfil")
		 * );
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idPerfil),
		 * descricao, 0);
		 * }
		 * else if(idDocumento != null)
		 * {
		 * descricao = "VALORESDOCUMENTO";
		 * httpServletRequest.setAttribute("nomeDocumento",httpServletRequest.getParameter(
		 * "nomeDocumento"));
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idDocumento),
		 * descricao, 0);
		 * }
		 * else
		 * {
		 * descricao = "VALORES";
		 * colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),0, descricao, 0);
		 * }
		 */

		/*
		 * Map<Integer,Map<BigDecimal,Collection>> map =
		 * (Map<Integer,Map<BigDecimal,Collection>>)
		 * sessao.getAttribute("map");
		 * int contador = 0;
		 * Iterator iteratorColecaoDadosDiarios = map.keySet().iterator();
		 * while (iteratorColecaoDadosDiarios.hasNext()) {
		 * Integer chave = (Integer) iteratorColecaoDadosDiarios
		 * .next();
		 * Map<BigDecimal,Collection> mapDadosDiarios = map.get(chave);
		 * Iterator iteratorValorAcumulado = mapDadosDiarios.keySet().iterator();
		 * while (iteratorValorAcumulado.hasNext()) {
		 * BigDecimal valor = (BigDecimal) iteratorValorAcumulado
		 * .next();
		 * Collection dadosDiarios = mapDadosDiarios.get(valor);
		 * Iterator iteratorColecaoArrecadacaoDadosDiarios = dadosDiarios.iterator();
		 * //boolean primeiraVez = true;
		 * //String cor = "#cbe5fe";
		 * while(iteratorColecaoArrecadacaoDadosDiarios.hasNext()){
		 * contador++;
		 * ArrecadacaoDadosDiarios arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios)
		 * iteratorColecaoArrecadacaoDadosDiarios.next();
		 * //if (arrecadacaoDadosDiarios.getGerenciaRegional() != null ){
		 * if(idEloPopup != null && idLocalidade == null){
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
		 * &&
		 * 
		 * arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId().toString().equals(idEloPopup
		 * )){
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * // descricao = "VALORESELO";
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		 * descricao, Integer.parseInt(idEloPopup));
		 * }else if(idEloPopup != null && idLocalidade != null){
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
		 * && arrecadacaoDadosDiarios.getLocalidade().getId().toString().equals(idLocalidade)
		 * &&
		 * 
		 * arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId().toString().equals(idEloPopup
		 * )){
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * // descricao = "VALORESPORDIA";
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		 * descricao, Integer.parseInt(idLocalidade));
		 * }else if(idGerencia != null){
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
		 * && arrecadacaoDadosDiarios.getGerenciaRegional().getId().toString().equals(idGerencia)){
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * // descricao = "VALORESGERENCIA";
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
		 * descricao, 0);
		 * }else if(idArrecadadorPopup != null){
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
		 * &&
		 * arrecadacaoDadosDiarios.getArrecadador().getId().toString().equals(idArrecadadorPopup)){
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * // descricao = "VALORESARRECADADOR";
		 * //
		 * httpServletRequest.setAttribute("nomeAgente",httpServletRequest.getParameter("nomeAgente"
		 * ));
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt
		 * (referencia),Integer.parseInt(idArrecadadorPopup), descricao, 0);
		 * }else if(idCategoria != null){
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
		 * && arrecadacaoDadosDiarios.getCategoria().getId().toString().equals(idCategoria)){
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * //descricao = "VALORESCATEGORIA";
		 * //httpServletRequest.setAttribute("nomeCategoria",httpServletRequest.getParameter(
		 * "nomeCategoria"));
		 * //colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idCategoria),
		 * descricao, 0);
		 * }else if(idPerfil != null){
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
		 * && arrecadacaoDadosDiarios.getImovelPerfil() != null
		 * && arrecadacaoDadosDiarios.getImovelPerfil().getId() != null
		 * && arrecadacaoDadosDiarios.getImovelPerfil().getId().toString().equals(idPerfil)){
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * // descricao = "VALORESPERFIL";
		 * //
		 * httpServletRequest.setAttribute("nomePerfil",httpServletRequest.getParameter("nomePerfil"
		 * ));
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idPerfil),
		 * descricao, 0);
		 * }else if(idDocumento != null){
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
		 * && arrecadacaoDadosDiarios.getDocumentoTipo().getId().toString().equals(idDocumento)){
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * // descricao = "VALORESDOCUMENTO";
		 * //httpServletRequest.setAttribute("nomeDocumento",httpServletRequest.getParameter(
		 * "nomeDocumento"));
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idDocumento),
		 * descricao, 0);
		 * }else{
		 * // descricao = "VALORES";
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios =
		 * fachada.consultarDadosDiarios(Integer.parseInt(referencia),0, descricao, 0);
		 * 
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia))
		 * {
		 * colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * }
		 * }
		 * //
		 * if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia
		 * )){
		 * // colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
		 * // }
		 * //}
		 * }
		 * }
		 * }
		 */

		Collection colecaoArrecadacaoDadosDiariosValoresDiariosFinal = new ArrayList();

		Collection colecaoArrecadacaoDadosDiarios = (Collection) sessao.getAttribute("colecaoArrecadacaoDadosDiarios");

		Iterator iteratorColecaoDadosDiarios = colecaoArrecadacaoDadosDiarios.iterator();

		ArrecadacaoDadosDiarios arrecadacaoDadosDiarios = null;

		while(iteratorColecaoDadosDiarios.hasNext()){

			arrecadacaoDadosDiarios = (ArrecadacaoDadosDiarios) iteratorColecaoDadosDiarios.next();

			// /////////////////////

			if(idEloPopup != null && idLocalidade == null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId().toString().equals(idEloPopup)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

				// descricao = "VALORESELO";
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
				// descricao, Integer.parseInt(idEloPopup));
			}else if(idEloPopup != null && idLocalidade != null && idSetorComercial != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getLocalidade().getId().toString().equals(idLocalidade)
								&& arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId().toString().equals(idEloPopup)
								&& arrecadacaoDadosDiarios.getSetorComercial() != null
								&& arrecadacaoDadosDiarios.getSetorComercial().getId().toString().equals(idSetorComercial)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}
			}else if(idEloPopup != null && idLocalidade != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getLocalidade().getId().toString().equals(idLocalidade)
								&& arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId().toString().equals(idEloPopup)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

				// descricao = "VALORESPORDIA";
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
				// descricao, Integer.parseInt(idLocalidade));
			}else if(idGerencia != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getGerenciaRegional().getId().toString().equals(idGerencia)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}
			}else if(idUnidadeNegocio != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getUnidadeNegocio().getId().toString().equals(idUnidadeNegocio)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

				// descricao = "VALORESGERENCIA";
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idGerencia),
				// descricao, 0);
			}else if(idArrecadadorPopup != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getArrecadador().getId().toString().equals(idArrecadadorPopup)){

					if(idConcessionaria == null
									|| (idConcessionaria != null && arrecadacaoDadosDiarios.getConcessionaria().getId().toString()
													.equals(idConcessionaria))){

						if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
							valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
						}

						agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
					}

				}

				// descricao = "VALORESARRECADADOR";
				// httpServletRequest.setAttribute("nomeAgente",httpServletRequest.getParameter("nomeAgente"));
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idArrecadadorPopup),
				// descricao, 0);
			}else if(idCategoria != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getCategoria().getId().toString().equals(idCategoria)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

				// descricao = "VALORESCATEGORIA";
				// httpServletRequest.setAttribute("nomeCategoria",httpServletRequest.getParameter("nomeCategoria"));
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idCategoria),
				// descricao, 0);
			}else if(idPerfil != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getImovelPerfil() != null
								&& arrecadacaoDadosDiarios.getImovelPerfil().getId() != null
								&& arrecadacaoDadosDiarios.getImovelPerfil().getId().toString().equals(idPerfil)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

				// descricao = "VALORESPERFIL";
				// httpServletRequest.setAttribute("nomePerfil",httpServletRequest.getParameter("nomePerfil"));
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idPerfil),
				// descricao, 0);
			}else if(idDocumento != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getDocumentoTipo().getId().toString().equals(idDocumento)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

				// descricao = "VALORESDOCUMENTO";
				// httpServletRequest.setAttribute("nomeDocumento",httpServletRequest.getParameter("nomeDocumento"));
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),Integer.parseInt(idDocumento),
				// descricao, 0);
			}else if(idConcessionaria != null){
				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)
								&& arrecadacaoDadosDiarios.getConcessionaria().getId().toString().equals(idConcessionaria)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

			}else{
				// descricao = "VALORES";
				// colecaoArrecadacaoDadosDiariosValoresDiarios =
				// fachada.consultarDadosDiarios(Integer.parseInt(referencia),0, descricao, 0);

				if(arrecadacaoDadosDiarios.getAnoMesReferenciaArrecadacao().toString().equals(referencia)){
					// colecaoArrecadacaoDadosDiariosValoresDiarios.add(arrecadacaoDadosDiarios);
					if(arrecadacaoDadosDiarios.getValorPagamentos() != null){
						valorTotal = valorTotal.add((BigDecimal) arrecadacaoDadosDiarios.getValorPagamentos());
					}

					agrupar(colecaoArrecadacaoDadosDiariosValoresDiariosFinal, arrecadacaoDadosDiarios);
				}

			}

			// //////////////

		}

		// colecaoArrecadacaoDadosDiariosValoresDiarios =
		// colecaoArrecadacaoDadosDiariosValoresDiariosFinal;

		/*
		 * if(colecaoArrecadacaoDadosDiariosValoresDiariosFinal != null){
		 * Iterator iteratorcolecaoArrecadacaoDadosDiariosValoresDiariosFinal =
		 * colecaoArrecadacaoDadosDiariosValoresDiariosFinal.iterator();
		 * while (iteratorcolecaoArrecadacaoDadosDiariosValoresDiariosFinal.hasNext()) {
		 * // Obtém os dados do crédito realizado
		 * ArrecadacaoDadosDiarios valoresDadosDiarios = (ArrecadacaoDadosDiarios)
		 * iteratorcolecaoArrecadacaoDadosDiariosValoresDiariosFinal
		 * .next();
		 * }
		 * }
		 */

		if((idGerencia != null) && (idLocalidade != null) && (idEloPopup != null)){
			/*
			 * FiltroArrecadacaoDadosDiarios filtroArrecadacaoDadosDiarios = new
			 * FiltroArrecadacaoDadosDiarios();
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new ParametroSimples(
			 * FiltroArrecadacaoDadosDiarios.GERENCIA_REGIONAL,
			 * idGerencia));
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new ParametroSimples(
			 * FiltroArrecadacaoDadosDiarios.LOCALIDADE_ID,
			 * idLocalidade));
			 * filtroArrecadacaoDadosDiarios.adicionarParametro(new ParametroSimples(
			 * "localidade.localidade.id",
			 * idEloPopup));
			 */

			// filtroArrecadacaoDadosDiarios
			// .adicionarCaminhoParaCarregamentoEntidade("gerenciaRegional");
			// filtroArrecadacaoDadosDiarios
			// .adicionarCaminhoParaCarregamentoEntidade("localidade");
			// filtroArrecadacaoDadosDiarios
			// .adicionarCaminhoParaCarregamentoEntidade("localidade.localidade");

			// Collection colecaoArrecadacaoDadosDiarios2 =
			// fachada.pesquisar(filtroArrecadacaoDadosDiarios,
			// ArrecadacaoDadosDiarios.class.getName());

			// if (colecaoArrecadacaoDadosDiarios2 != null
			// && !colecaoArrecadacaoDadosDiarios2.isEmpty()){

			// ArrecadacaoDadosDiarios dadosArrecadacao = (ArrecadacaoDadosDiarios) ((List)
			// colecaoArrecadacaoDadosDiarios2).get(0);
			ArrecadacaoDadosDiarios dadosArrecadacao = fachada.consultarDadosDiarios(new Integer(idGerencia).intValue(), new Integer(
							idLocalidade).intValue(), new Integer(idEloPopup).intValue());

			if(dadosArrecadacao != null && dadosArrecadacao.getGerenciaRegional() != null
							&& dadosArrecadacao.getGerenciaRegional().getNome() != null
							&& !dadosArrecadacao.getGerenciaRegional().getNome().equals("")){

				httpServletRequest.setAttribute("nomeGerencia", dadosArrecadacao.getGerenciaRegional().getNome());
			}
			if(dadosArrecadacao != null && dadosArrecadacao.getLocalidade() != null
							&& dadosArrecadacao.getLocalidade().getDescricao() != null
							&& !dadosArrecadacao.getLocalidade().getDescricao().equals("")){

				httpServletRequest.setAttribute("descricaoLocalidade", dadosArrecadacao.getLocalidade().getDescricao());
			}
			if(dadosArrecadacao != null && dadosArrecadacao.getLocalidade() != null
							&& dadosArrecadacao.getLocalidade().getLocalidade() != null
							&& dadosArrecadacao.getLocalidade().getLocalidade().getDescricao() != null
							&& !dadosArrecadacao.getLocalidade().getLocalidade().getDescricao().equals("")){

				httpServletRequest.setAttribute("descricaoElo", dadosArrecadacao.getLocalidade().getLocalidade().getDescricao());
			}
			// }
		}

		sessao.setAttribute("colecaoDadosDiarios", colecaoArrecadacaoDadosDiariosValoresDiariosFinal);
		sessao.setAttribute("referencia", referencia);
		sessao.setAttribute("valorTotal", valorTotal);
		return retorno;
	}

	public void agrupar(Collection colecaoArrecadacaoDadosDiariosValoresDiariosFinal, ArrecadacaoDadosDiarios arrecadacaoDadosDiarios){

		if(colecaoArrecadacaoDadosDiariosValoresDiariosFinal.isEmpty()){

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

			colecaoArrecadacaoDadosDiariosValoresDiariosFinal.add(arre);
		}else{

			Iterator iteratorcolecaoArrecadacaoDadosDiariosElo = colecaoArrecadacaoDadosDiariosValoresDiariosFinal.iterator();

			boolean acumular = false;
			ArrecadacaoDadosDiarios arrecadacaoDadosDiariosSubstituir = null;
			boolean achou = true;
			while(iteratorcolecaoArrecadacaoDadosDiariosElo.hasNext() && achou){
				acumular = false;

				arrecadacaoDadosDiariosSubstituir = (ArrecadacaoDadosDiarios) iteratorcolecaoArrecadacaoDadosDiariosElo.next();

				/*
				 * if( arrecadacaoDadosDiarios.getLocalidade() != null &&
				 * arrecadacaoDadosDiarios.getLocalidade().getLocalidade() != null
				 * && arrecadacaoDadosDiariosSubstituir.getLocalidade() != null &&
				 * arrecadacaoDadosDiariosSubstituir.getLocalidade().getLocalidade() != null) {
				 * // se o Elo for igual
				 * if (arrecadacaoDadosDiarios.getLocalidade().getLocalidade().getId()
				 * 
				 * .toString().equals(arrecadacaoDadosDiariosSubstituir.getLocalidade().getLocalidade
				 * ()
				 * .getId().toString())) {
				 */
				if(arrecadacaoDadosDiarios.getDataPagamento().compareTo(arrecadacaoDadosDiariosSubstituir.getDataPagamento()) == 0){
					// if(Util.formatarData(arrecadacaoDadosDiarios.getDataPagamento()).equals(Util.formatarData(arrecadacaoDadosDiariosSubstituir.getDataPagamento()))){
					acumular = true;
					achou = false;
				}
				// }
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
				colecaoArrecadacaoDadosDiariosValoresDiariosFinal.add(arre);
			}
		}

	}
}