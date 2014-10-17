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

		// Obt�m a inst�ncia da fachada
		// Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("exibirManterLocalidadeDadoOperacionalAction");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarLocalidadeDadoOperacionalActionForm pesquisarFiltrarLocalidadeDadoOperacionalActionForm = (FiltrarLocalidadeDadoOperacionalActionForm) actionForm;

		// String indicadorAtualizar =
		// pesquisarFiltrarLocalidadeDadoOperacionalActionForm.getIndicadorAtualizar();

		FiltroLocalidadeDadoOperacional filtroLocalidadeDadoOperacional = new FiltroLocalidadeDadoOperacional();

		// Objetos que ser� retornados pelo hibernate
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

		// Caso o usu�rio tenha informado a refer�ncia inicial do dado operacional, pesquisa os
		// dados operacionais entre a refer�ncia inicial e final, replicando
		// a refer�ncia inicial na final caso essa n�o tenha sido informada
		// Caso contr�rio seta a refer�ncia final para vazio, e pesquisa os dados operacionais para
		// todas as refer�ncias existentes
		if(mesAnoReferenciaInicial != null && !mesAnoReferenciaInicial.trim().equalsIgnoreCase("")){

			// Formata a refer�ncia inicial informada, para o formato de ano e depois m�s
			mesAnoReferenciaInicial = Util.formatarMesAnoParaAnoMesSemBarra((String) pesquisarFiltrarLocalidadeDadoOperacionalActionForm
							.getMesAnoReferenciaInicial());

			// Caso a refer�ncia final n�o tenha sido informada , replica a refer�ncia inicial na
			// final
			// Caso contr�rio, formata a refer�ncia final para ano e m�s
			if(mesAnoReferenciaFinal == null || mesAnoReferenciaFinal.trim().equalsIgnoreCase("")){
				// Replica a refer�ncia inicial na refer�ncia final
				mesAnoReferenciaFinal = mesAnoReferenciaInicial;
			}else{
				// Formata a refer�ncia final informada, para o formato de ano e depois m�s
				mesAnoReferenciaFinal = Util.formatarMesAnoParaAnoMesSemBarra((String) pesquisarFiltrarLocalidadeDadoOperacionalActionForm
								.getMesAnoReferenciaFinal());

				// Caso a refer�ncia final dos dados oepracionais seja anterior a inicial, levanta a
				// exce��o para o
				// usu�rio indicando que a refer�ncia final � anterior a inicial
				if((new Integer(mesAnoReferenciaInicial)).intValue() > (new Integer(mesAnoReferenciaFinal)).intValue()){
					throw new ActionServletException("atencao.referenciafinal.menorque");
				}
			}
		}else{
			// Seta a refer�ncia final do dado operacional para nula
			mesAnoReferenciaFinal = null;
		}

		// Caso a refer�ncia final do dado operacionail esteja diferente de nulo,
		// Pesquisa os dados operacionais entre a refer�ncia inicial e final
		if(!Util.isVazioOuBranco(mesAnoReferenciaFinal)){
			// Indica que o usu�rio informou um par�metro para pesquisar os dados operacionais da
			// localidade
			peloMenosUmParametroInformado = true;

			// Indica no filtro para pesquisar os dados operacionais no intervalo entre a refer�ncia
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

		// Erro caso o usu�rio mandou filtrar sem nenhum par�metro
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
