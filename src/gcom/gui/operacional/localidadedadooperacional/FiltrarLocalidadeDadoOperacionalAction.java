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

package gcom.gui.operacional.localidadedadooperacional;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroLocalidadeDadoOperacional;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 * @date 28/01/2011
 */
public class FiltrarLocalidadeDadoOperacionalAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obtém a instância da fachada
		// Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("exibirManterLocalidadeDadoOperacionalAction");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarLocalidadeDadoOperacionalActionForm pesquisarFiltrarLocalidadeDadoOperacionalActionForm = (FiltrarLocalidadeDadoOperacionalActionForm) actionForm;

		// String indicadorAtualizar =
		// pesquisarFiltrarLocalidadeDadoOperacionalActionForm.getIndicadorAtualizar();

		FiltroLocalidadeDadoOperacional filtroLocalidadeDadoOperacional = new FiltroLocalidadeDadoOperacional();

		// Objetos que serã retornados pelo hibernate
		filtroLocalidadeDadoOperacional.setCampoOrderBy(FiltroLocalidadeDadoOperacional.LOCALIDADE_ID,
						FiltroLocalidadeDadoOperacional.ANOMES_REFERENCIA);
		filtroLocalidadeDadoOperacional.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidadeDadoOperacional.LOCALIDADE);

		String idLocalidade = pesquisarFiltrarLocalidadeDadoOperacionalActionForm.getIdLocalidade();
		String mesAnoReferenciaInicial = pesquisarFiltrarLocalidadeDadoOperacionalActionForm.getMesAnoReferenciaInicial();
		String mesAnoReferenciaFinal = pesquisarFiltrarLocalidadeDadoOperacionalActionForm.getMesAnoReferenciaFinal();
		String indicadorUso = pesquisarFiltrarLocalidadeDadoOperacionalActionForm.getIndicadorUso();

		// 1 check --- null uncheck
		String indicadorAtualizar = httpServletRequest.getParameter("indicadorAtualizar");

		boolean peloMenosUmParametroInformado = false;

		if(!Util.isVazioOuBranco(idLocalidade)){
			peloMenosUmParametroInformado = true;
			filtroLocalidadeDadoOperacional.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadoOperacional.LOCALIDADE_ID,
							new Integer(idLocalidade)));
		}

		// Caso o usuário tenha informado a referência inicial do dado operacional, pesquisa os
		// dados operacionais entre a referência inicial e final, replicando
		// a referência inicial na final caso essa não tenha sido informada
		// Caso contrário seta a referência final para vazio, e pesquisa os dados operacionais para
		// todas as referências existentes
		if(mesAnoReferenciaInicial != null && !mesAnoReferenciaInicial.trim().equalsIgnoreCase("")){

			// Formata a referência inicial informada, para o formato de ano e depois mês
			mesAnoReferenciaInicial = Util.formatarMesAnoParaAnoMesSemBarra((String) pesquisarFiltrarLocalidadeDadoOperacionalActionForm
							.getMesAnoReferenciaInicial());

			// Caso a referência final não tenha sido informada , replica a referência inicial na
			// final
			// Caso contrário, formata a referência final para ano e mês
			if(mesAnoReferenciaFinal == null || mesAnoReferenciaFinal.trim().equalsIgnoreCase("")){
				// Replica a referência inicial na referência final
				mesAnoReferenciaFinal = mesAnoReferenciaInicial;
			}else{
				// Formata a referência final informada, para o formato de ano e depois mês
				mesAnoReferenciaFinal = Util.formatarMesAnoParaAnoMesSemBarra((String) pesquisarFiltrarLocalidadeDadoOperacionalActionForm
								.getMesAnoReferenciaFinal());

				// Caso a referência final dos dados oepracionais seja anterior a inicial, levanta a
				// exceção para o
				// usuário indicando que a referência final é anterior a inicial
				if((new Integer(mesAnoReferenciaInicial)).intValue() > (new Integer(mesAnoReferenciaFinal)).intValue()){
					throw new ActionServletException("atencao.referenciafinal.menorque");
				}
			}
		}else{
			// Seta a referência final do dado operacional para nula
			mesAnoReferenciaFinal = null;
		}

		// Caso a referência final do dado operacionail esteja diferente de nulo,
		// Pesquisa os dados operacionais entre a referência inicial e final
		if(!Util.isVazioOuBranco(mesAnoReferenciaFinal)){
			// Indica que o usuário informou um parâmetro para pesquisar os dados operacionais da
			// localidade
			peloMenosUmParametroInformado = true;

			// Indica no filtro para pesquisar os dados operacionais no intervalo entre a referência
			// inicial e a final
			filtroLocalidadeDadoOperacional.adicionarParametro(new MaiorQue(FiltroLocalidadeDadoOperacional.ANOMES_REFERENCIA,
							mesAnoReferenciaInicial, ParametroSimples.CONECTOR_AND));
			filtroLocalidadeDadoOperacional.adicionarParametro(new MenorQue(FiltroLocalidadeDadoOperacional.ANOMES_REFERENCIA,
							mesAnoReferenciaFinal));
		}

		if(!Util.isVazioOuBranco(indicadorUso) && !indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.TODOS))){
			peloMenosUmParametroInformado = true;
			if(indicadorUso.equalsIgnoreCase(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO))){
				filtroLocalidadeDadoOperacional.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadoOperacional.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
			}else{
				filtroLocalidadeDadoOperacional.adicionarParametro(new ParametroSimples(FiltroLocalidadeDadoOperacional.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_DESATIVO));
			}
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroLocalidadeDadoOperacional", filtroLocalidadeDadoOperacional);
		sessao.setAttribute("indicadorAtualizar", indicadorAtualizar);
		sessao.setAttribute("voltar", "manter");

		// devolve o mapeamento de retorno
		return retorno;
	}

}
