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

package gcom.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.micromedicao.ConsultarArquivoTextoLeituraActionForm;
import gcom.seguranca.acesso.usuario.UsuarioSituacao;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Thiago Ten�rio
 * @date 05/08/2006
 */
public class ConsultarArquivoTextoLeituraAction
				extends GcomAction {

	/**
	 * Este caso de uso permite Pesquisar um Tipo de Servic�o
	 * [UC0437] Pesquisar Tipo de Servi�o de Refer�ncia
	 * 
	 * @author Thiago Ten�rio
	 * @date 31/07/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarArquivoTextoLeitura");

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarArquivoTextoLeituraActionForm consultarArquivoTextoLeituraActionForm = (ConsultarArquivoTextoLeituraActionForm) actionForm;

		FiltroArquivoTextoRoteiroEmpresa filtroArquivoTextoRoteiroEmpresa = new FiltroArquivoTextoRoteiroEmpresa();

		filtroArquivoTextoRoteiroEmpresa.adicionarCaminhoParaCarregamentoEntidade("localidade");
		// Fachada fachada = Fachada.getInstancia();

		boolean peloMenosUmParametroInformado = false;

		String empresaID = consultarArquivoTextoLeituraActionForm.getEmpresaID();

		String grupoFaturamentoID = consultarArquivoTextoLeituraActionForm.getGrupoFaturamentoID();

		String mesAno = consultarArquivoTextoLeituraActionForm.getMesAno();

		String situaTransmLeitura = consultarArquivoTextoLeituraActionForm.getSituaTransmLeitura();

		// Verifica se o campo Empresa foi informado

		if(empresaID != null && !empresaID.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			peloMenosUmParametroInformado = true;

			filtroArquivoTextoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroArquivoTextoRoteiroEmpresa.EMPRESA, empresaID));

		}

		// Verifica se o campo Grupo de Faturamento foi informado

		if(grupoFaturamentoID != null && !grupoFaturamentoID.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			peloMenosUmParametroInformado = true;

			filtroArquivoTextoRoteiroEmpresa.adicionarParametro(new ParametroSimples(FiltroArquivoTextoRoteiroEmpresa.GRUPO_FATURAMENTO,
							grupoFaturamentoID));

		}

		// // M�s de Refer�ncia
		// if (mesAno != null && !mesAno.trim().equals("")) {
		//
		// peloMenosUmParametroInformado = true;
		// filtroArquivoTextoRoteiroEmpresa.adicionarParametro(new
		// ParametroSimples(FiltroArquivoTextoRoteiroEmpresa.TEMPO_ENVIO_EMPRESA, Util
		// .formatarAnoMes(mesAno)));
		// }

		// Verifica se o campo Situa��o Texto para Leitura foi informado

		if(situaTransmLeitura != null && !situaTransmLeitura.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			filtroArquivoTextoRoteiroEmpresa.adicionarParametro(new ParametroSimples(
							FiltroArquivoTextoRoteiroEmpresa.SITUACAO_TRANS_LEITURA, situaTransmLeitura));

		}

		// Erro caso o usu�rio mandou Pesquisar sem nenhum par�metro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoArquivoTextoRoteiroEmpresa = Fachada.getInstancia().pesquisar(filtroArquivoTextoRoteiroEmpresa,
						ArquivoTextoRoteiroEmpresa.class.getName());

		sessao.setAttribute("colecaoArquivoTextoRoteiroEmpresa", colecaoArquivoTextoRoteiroEmpresa);

		// Aciona o controle de pagina��o para que sejam pesquisados apenas
		// os registros que aparecem na p�gina
		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroArquivoTextoRoteiroEmpresa, ArquivoTextoRoteiroEmpresa.class
						.getName());
		Collection<ArquivoTextoRoteiroEmpresa> colecaoArquivosText = (Collection) resultado.get("colecaoRetorno");
		retorno = (ActionForward) resultado.get("destinoActionForward");

		if(colecaoArquivosText == null || colecaoArquivosText.isEmpty()){
			throw new ActionServletException("atencao.nenhum_arquivo_texto_roteiro_empresa", null, "Arquivo Texto Leitura");

		}

		sessao.setAttribute("colecaoArquivosText", colecaoArquivosText);

		return retorno;
	}

}