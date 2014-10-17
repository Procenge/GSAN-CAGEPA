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

package gcom.gui.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.GcomAction;
import gcom.micromedicao.ArquivoTextoRoteiroEmpresa;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarConsultarArquivoTextoLeituraAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarConsultarArquivoTextoLeituraActionForm atualizarConsultarArquivoTextoLeituraActionForm = (AtualizarConsultarArquivoTextoLeituraActionForm) actionForm;

		ArquivoTextoRoteiroEmpresa arquivoTextoRoteiroEmpresa = new ArquivoTextoRoteiroEmpresa();

		arquivoTextoRoteiroEmpresa = (ArquivoTextoRoteiroEmpresa) sessao.getAttribute("atualizarConsultarArquivoTextoLeituraAction");

		// String mesAno = atualizarConsultarArquivoTextoLeituraActionForm
		// .getMesAno();

		arquivoTextoRoteiroEmpresa.setNumeroArquivo(atualizarConsultarArquivoTextoLeituraActionForm.getNumeroArquivo());

		arquivoTextoRoteiroEmpresa.setQuantidadeImovel(new Integer(atualizarConsultarArquivoTextoLeituraActionForm.getQuantidadeImovel()));

		// arquivoTextoRoteiroEmpresa.getLocalidade().setLocalidade(atualizarConsultarArquivoTextoLeituraActionForm.getLocalidadeID());

		arquivoTextoRoteiroEmpresa.setNumeroFoneLeiturista(atualizarConsultarArquivoTextoLeituraActionForm.getNumeroFoneLeiturista());

		String numeroArquivo = atualizarConsultarArquivoTextoLeituraActionForm.getNumeroArquivo();

		String quantidadeImovel = atualizarConsultarArquivoTextoLeituraActionForm.getQuantidadeImovel();

		String localidadeID = atualizarConsultarArquivoTextoLeituraActionForm.getLocalidadeID();

		String numeroFoneLeiturista = atualizarConsultarArquivoTextoLeituraActionForm.getNumeroFoneLeiturista();

		// Empresa
		Empresa empresa = null;

		if(atualizarConsultarArquivoTextoLeituraActionForm.getEmpresaID() != null
						&& !atualizarConsultarArquivoTextoLeituraActionForm.getEmpresaID().equals("")
						&& !atualizarConsultarArquivoTextoLeituraActionForm.getEmpresaID().equals(
										"" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			empresa = new Empresa();

			arquivoTextoRoteiroEmpresa.setId(new Integer(atualizarConsultarArquivoTextoLeituraActionForm.getEmpresaID()));
		}

		arquivoTextoRoteiroEmpresa.setEmpresa(empresa);

		// Grupo Faturamento

		if(atualizarConsultarArquivoTextoLeituraActionForm.getGrupoFaturamentoID() != null){

			Integer grupoFaturamentoID = new Integer(atualizarConsultarArquivoTextoLeituraActionForm.getGrupoFaturamentoID());

			if(grupoFaturamentoID.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				arquivoTextoRoteiroEmpresa.setFaturamentoGrupo(null);
			}else{
				FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
				filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID,
								atualizarConsultarArquivoTextoLeituraActionForm.getGrupoFaturamentoID().toString()));
				Collection colecaoGrupoFaturamento = (Collection) fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class
								.getName());

				// setando
				arquivoTextoRoteiroEmpresa.setFaturamentoGrupo((FaturamentoGrupo) colecaoGrupoFaturamento.iterator().next());
			}
		}

		// Date mesAnoFormatada = Util
		// .converteStringParaDate(mesAno);
		//		
		// arquivoTextoRoteiroEmpresa
		// .setDataTentativaEntrega(dataDevolucaoFormatada);

		arquivoTextoRoteiroEmpresa.setUltimaAlteracao(new Date());

		// fachada.atualizarConsultarArquivoTextoLeitura(arquivoTextoRoteiroEmpresa);

		montarPaginaSucesso(httpServletRequest, "Situa��o Usuario " + arquivoTextoRoteiroEmpresa.getId() + " atualizado com sucesso.",
						"Realizar outra Manuten��o de Situa��o Usuario", "exibirManterSituacaoUsuarioAction.do");

		return retorno;

	}

}