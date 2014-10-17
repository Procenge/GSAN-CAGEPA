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
 * Yara Taciane de Souza
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

package gcom.gui.cobranca.spcserasa;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.spcserasa.bean.NegativadorMovimentoHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Realiza o filtro do Movimento do Negativador de acordo com os par�metros informados
 * 
 * @author Yara Taciane de Souza
 * @created 21/01/2008
 */
public class FiltrarNegativadorMovimentoAction
				extends GcomAction {

	/**
	 * Este caso de uso permite o filtro de um Movimento Negativador
	 * [UC0682] Filtrar Movimento do Negativador
	 * 
	 * @author Yara Taciane de Souza
	 * @date 21/01/2007
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("retornarFiltroNegativadorMovimento");

		FiltrarNegativadorMovimentoActionForm filtrarNegativadorMovimentoActionForm = (FiltrarNegativadorMovimentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Boolean peloMenosUmParametroInformado = false;

		// -------------------------------------------------------------------------
		Integer idNegativador = null;
		if(filtrarNegativadorMovimentoActionForm.getIdNegativador() != null
						&& !filtrarNegativadorMovimentoActionForm.getIdNegativador().equals("-1")){
			idNegativador = Integer.parseInt(filtrarNegativadorMovimentoActionForm.getIdNegativador());
			peloMenosUmParametroInformado = true;

		}

		Short codigoMovimento = -1;
		if(!"".equals(filtrarNegativadorMovimentoActionForm.getTipoMovimento())
						&& filtrarNegativadorMovimentoActionForm.getTipoMovimento() != null){

			if(!filtrarNegativadorMovimentoActionForm.getTipoMovimento().equals(ConstantesSistema.TODOS.toString())){
				codigoMovimento = Short.parseShort(filtrarNegativadorMovimentoActionForm.getTipoMovimento());
			}

			peloMenosUmParametroInformado = true;
		}

		Integer idImovel = null;
		if(!"".equals(filtrarNegativadorMovimentoActionForm.getIdImovel()) && filtrarNegativadorMovimentoActionForm.getIdImovel() != null){
			idImovel = Util.converterStringParaInteger(filtrarNegativadorMovimentoActionForm.getIdImovel());

			peloMenosUmParametroInformado = true;
		}

		Integer numeroSequencialArquivo = null;
		if(!"".equals(filtrarNegativadorMovimentoActionForm.getNumeroSequencialArquivo())
						&& filtrarNegativadorMovimentoActionForm.getNumeroSequencialArquivo() != null){
			numeroSequencialArquivo = Integer.parseInt(filtrarNegativadorMovimentoActionForm.getNumeroSequencialArquivo());
			peloMenosUmParametroInformado = true;
		}

		Date dataProcessamentoInicial = null;
		Date dataProcessamentoFinal = null;
		String dataInicio = null;
		String dataFim = null;
		if((!"".equals(filtrarNegativadorMovimentoActionForm.getDataProcessamentoInicial()) && filtrarNegativadorMovimentoActionForm
						.getDataProcessamentoInicial() != null)
						&& (!"".equals(filtrarNegativadorMovimentoActionForm.getDataProcessamentoFinal()) && filtrarNegativadorMovimentoActionForm
										.getDataProcessamentoFinal() != null)){
			dataProcessamentoInicial = Util.converteStringParaDate(filtrarNegativadorMovimentoActionForm.getDataProcessamentoInicial());
			dataProcessamentoFinal = Util.converteStringParaDate(filtrarNegativadorMovimentoActionForm.getDataProcessamentoFinal());

			dataInicio = Util.formatarData(dataProcessamentoInicial);
			dataFim = Util.formatarData(dataProcessamentoFinal);

			// Se data inicio maior que data fim
			if(Util.compararData(dataProcessamentoInicial, dataProcessamentoFinal) == 1){

				throw new ActionServletException("atencao.data_inicial_maior_data_final", dataInicio, dataFim);
			}

			peloMenosUmParametroInformado = true;
		}

		short indicadorMovimento = -1;
		if(!"".equals(filtrarNegativadorMovimentoActionForm.getMovimento()) && filtrarNegativadorMovimentoActionForm.getMovimento() != null){
			if(!filtrarNegativadorMovimentoActionForm.getMovimento().equals(ConstantesSistema.TODOS.toString())){
				indicadorMovimento = Short.parseShort(filtrarNegativadorMovimentoActionForm.getMovimento());
			}

			peloMenosUmParametroInformado = true;
		}

		short indicadorRegistro = -1;
		if(!"".equals(filtrarNegativadorMovimentoActionForm.getMovimentoRegistro())
						&& filtrarNegativadorMovimentoActionForm.getMovimentoRegistro() != null){
			if(!filtrarNegativadorMovimentoActionForm.getMovimentoRegistro().equals(ConstantesSistema.TODOS.toString())){
				indicadorRegistro = Short.parseShort(filtrarNegativadorMovimentoActionForm.getMovimentoRegistro());
			}

			peloMenosUmParametroInformado = true;
		}

		short indicadorCorrigido = -1;
		if(!"".equals(filtrarNegativadorMovimentoActionForm.getMovimentoCorrigido())
						&& filtrarNegativadorMovimentoActionForm.getMovimentoCorrigido() != null){
			if(!filtrarNegativadorMovimentoActionForm.getMovimentoCorrigido().equals(ConstantesSistema.TODOS.toString())){
				indicadorCorrigido = Short.parseShort(filtrarNegativadorMovimentoActionForm.getMovimentoCorrigido());
			}

			peloMenosUmParametroInformado = true;
		}

		NegativadorMovimentoHelper negativadorMovimentoHelper = new NegativadorMovimentoHelper(idNegativador, codigoMovimento, idImovel,
						numeroSequencialArquivo, dataInicio, dataFim, indicadorMovimento, indicadorRegistro, indicadorCorrigido);

		// Collection collNegativadorMovimento =
		// fachada.pesquisarNegativadorMovimento(negativadorMovimentoHelper);

		// [FS0003] Verificar preenchimento dos campos
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		// sessao.setAttribute("colecao", collNegativadorMovimento);

		sessao.setAttribute("negativadorMovimentoHelper", negativadorMovimentoHelper);

		return retorno;
	}

}
