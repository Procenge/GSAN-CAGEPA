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
import gcom.cadastro.imovel.FiltroImovelPerfil;
import gcom.cadastro.imovel.ImovelPerfil;
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
 * @created 24 de Maio de 2006
 **/
public class ExibirConsultarDadosDiariosPerfilAction
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

		ActionForward retorno = actionMapping.findForward("exibirConsultarDadosDiariosPerfil");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando implementar a parte de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("dadosArrecadacaoPerfil") == null){

			Collection colecaoArrecadacaoDadosDiariosPerfil = null;

			colecaoArrecadacaoDadosDiariosPerfil = (Collection) sessao.getAttribute("colecaoArrecadacaoDadosDiarios");

			Iterator iteratorColecaoArrecadacaoDadosDiariosPerfil = colecaoArrecadacaoDadosDiariosPerfil.iterator();
			Integer anoMesAnterior = null;
			Integer anoMes = null;
			boolean primeiraVez = true;
			int contador = 0;
			BigDecimal valor = new BigDecimal("0.00");
			BigDecimal valorPorReferencia = new BigDecimal("0.00");

			Collection colecaoArrecadacaoDadosDiariosArrecadacao = new ArrayList();

			Map<BigDecimal, Collection> map = new TreeMap();

			Map<Integer, Map<BigDecimal, Collection>> mapPrincipal = new TreeMap();

			ArrecadacaoDadosDiarios dadosDiarios = null;
			ArrecadacaoDadosDiarios dadosDiariosCopia = null;
			int indicador = 4;

			Collection colecaoIdImovelPerfil = new ArrayList();

			while(iteratorColecaoArrecadacaoDadosDiariosPerfil.hasNext()){
				contador++;

				dadosDiarios = (ArrecadacaoDadosDiarios) iteratorColecaoArrecadacaoDadosDiariosPerfil.next();



					boolean naoAchou = true;
					if(!colecaoIdImovelPerfil.isEmpty()){
						Iterator iterator = colecaoIdImovelPerfil.iterator();

						while(iterator.hasNext() && naoAchou){
							ImovelPerfil imovelPerfil = (ImovelPerfil) iterator.next();
						if(dadosDiarios.getImovelPerfil() != null){
							if(imovelPerfil.getId().equals(dadosDiarios.getImovelPerfil().getId())){
								naoAchou = false;
								dadosDiarios.setImovelPerfil(imovelPerfil);
							}
							}else{
								ImovelPerfil imovelPerfilNulo = new ImovelPerfil();
							imovelPerfilNulo.setId(-1);
								imovelPerfilNulo.setDescricao("N�O IDENTIFICADO");
								naoAchou = false;
								dadosDiarios.setImovelPerfil(imovelPerfilNulo);
							}
						}
					}

					if(naoAchou){
						// pesquisar na base a gerencia
						FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
						filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, dadosDiarios.getImovelPerfil()
										.getId()));

						Collection colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

						ImovelPerfil imovelPerfil = (ImovelPerfil) colecaoImovelPerfil.iterator().next();

						dadosDiarios.getImovelPerfil().setDescricao(imovelPerfil.getDescricao());
						colecaoIdImovelPerfil.add(imovelPerfil);
					}

				dadosDiariosCopia = new ArrecadacaoDadosDiarios();
				dadosDiariosCopia.setAnoMesReferenciaArrecadacao(dadosDiarios.getAnoMesReferenciaArrecadacao());
				dadosDiariosCopia.setImovelPerfil(dadosDiarios.getImovelPerfil());
				dadosDiariosCopia.setId(dadosDiarios.getId());
				dadosDiariosCopia.setValorPagamentos(dadosDiarios.getValorPagamentos());
				dadosDiariosCopia.setDataPagamento(dadosDiarios.getDataPagamento());

				anoMes = dadosDiariosCopia.getAnoMesReferenciaArrecadacao();

				if(anoMes.equals(anoMesAnterior) || primeiraVez){

					valorPorReferencia = valorPorReferencia.add(dadosDiariosCopia.getValorPagamentos());

					fachada.acumularDadosArrecadacao(colecaoArrecadacaoDadosDiariosArrecadacao, dadosDiariosCopia, indicador, null, null,
									null);

					// guarda o valor do ultimo ano/mes
					if(contador == colecaoArrecadacaoDadosDiariosPerfil.size()){
						map = new TreeMap();
						map.put(valorPorReferencia, colecaoArrecadacaoDadosDiariosArrecadacao);
						mapPrincipal.put(new Integer(contador), map);
					}
					primeiraVez = false;

				}else{
					// armazena os dados do ano/mes
					map = new TreeMap();
					map.put(valorPorReferencia, colecaoArrecadacaoDadosDiariosArrecadacao);
					mapPrincipal.put(new Integer(contador), map);

					valorPorReferencia = new BigDecimal("0.00");
					colecaoArrecadacaoDadosDiariosArrecadacao = new ArrayList();

					valorPorReferencia = valorPorReferencia.add(dadosDiariosCopia.getValorPagamentos());

					fachada.acumularDadosArrecadacao(colecaoArrecadacaoDadosDiariosArrecadacao, dadosDiariosCopia, indicador, null, null,
									null);
				}

				anoMesAnterior = anoMes;

				if(dadosDiariosCopia.getValorPagamentos() != null){
					valor = valor.add(dadosDiariosCopia.getValorPagamentos());
				}
			}

			colecaoArrecadacaoDadosDiariosArrecadacao = null;
			dadosDiariosCopia = null;
			dadosDiarios = null;

			Iterator iteratorColecaoDadosDiarios = mapPrincipal.keySet().iterator();

			while(iteratorColecaoDadosDiarios.hasNext()){

				Integer chave = (Integer) iteratorColecaoDadosDiarios.next();

				Map<BigDecimal, Collection> mapDadosDiariosArrecadacao = mapPrincipal.get(chave);

				Iterator iteratorValorAcumulado = mapDadosDiariosArrecadacao.keySet().iterator();

				while(iteratorValorAcumulado.hasNext()){

					Collection colecaoDadosDiariosArrecadacao = mapDadosDiariosArrecadacao.get((BigDecimal) iteratorValorAcumulado.next());
					Collections.sort((List) colecaoDadosDiariosArrecadacao, new Comparator() {

						public int compare(Object a, Object b){

							String codigo1 = null;
							if(((ArrecadacaoDadosDiarios) a).getImovelPerfil() != null){
								codigo1 = ((ArrecadacaoDadosDiarios) a).getImovelPerfil().getDescricao();
							}
							String codigo2 = null;
							if(((ArrecadacaoDadosDiarios) b).getImovelPerfil() != null){
								codigo2 = ((ArrecadacaoDadosDiarios) b).getImovelPerfil().getDescricao();
							}

							if(codigo1 == null || codigo1.equals("")){
								return -1;
							}else{
								if(codigo2 != null){
									return codigo1.compareTo(codigo2);
								}else{
									return 1;
								}
							}
						}
					});
				}
			}

			sessao.setAttribute("dadosArrecadacaoPerfil", mapPrincipal);
			sessao.setAttribute("valordadosArrecadacaoPerfil", valor);
		}

		return retorno;
	}
}