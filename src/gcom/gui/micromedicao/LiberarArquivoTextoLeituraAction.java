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

package gcom.gui.micromedicao;

import java.util.ArrayList;
import java.util.Collection;

import gcom.cadastro.localidade.GerenciaRegional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.ArquivoTextoRoteiroEmpresa;
import gcom.micromedicao.SituacaoTransmissaoLeitura;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LiberarArquivoTextoLeituraAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarArquivoTextoLeituraActionForm consultarArquivoTextoLeituraActionForm = (ConsultarArquivoTextoLeituraActionForm) actionForm;

		// ArquivoTextoRoteiroEmpresa arquivoTextoRoteiroEmpresa = new ArquivoTextoRoteiroEmpresa();
		// sessao.getAttribute("arquivoTextoRoteiroEmpresaLiberar");

		// // Situação do Arquivo
		// SituacaoTransmissaoLeitura situacaoTransmissaoLeitura = new SituacaoTransmissaoLeitura();
		// situacaoTransmissaoLeitura.setId(new
		// Integer(consultarArquivoTextoLeituraActionForm.getSituaTransmLeitura()));
		// arquivoTextoRoteiroEmpresa.setSituacaoTransmissaoLeitura(situacaoTransmissaoLeitura);

		Collection colecaoArquivosText = (Collection) sessao.getAttribute("colecaoArquivosText");

		ArquivoTextoRoteiroEmpresa arquivoTextoRoteiroEmpresa = new ArquivoTextoRoteiroEmpresa();

		if(colecaoArquivosText != null && !colecaoArquivosText.isEmpty()){

			arquivoTextoRoteiroEmpresa = (ArquivoTextoRoteiroEmpresa) Util.retonarObjetoDeColecao(colecaoArquivosText);

			// if(arquivoTextoRoteiroEmpresa.getSituacaoTransmissaoLeitura() != null && !

		}

		// colecaoArquivosText.getClass(ArquivoTextoRoteiroEmpresa.)

		SituacaoTransmissaoLeitura situacaoTransmissaoLeitura = new SituacaoTransmissaoLeitura();
		situacaoTransmissaoLeitura.setId(new Integer(consultarArquivoTextoLeituraActionForm.getSituaTransmLeitura()));

		String situaTransmLeitura = consultarArquivoTextoLeituraActionForm.getSituaTransmLeitura();

		// O numero maximo de digitos de leitura do hidrômetro é obrigatório.
		if(situaTransmLeitura != null && !situaTransmLeitura.equalsIgnoreCase("")){
			if(new Integer(situaTransmLeitura).intValue() != new Integer(2).intValue()){
				throw new ActionServletException("atencao.mudanca_de_situacao_invalida_para_o_arquivo_texto", null, "Situação do Arquivo");
			}else{

				arquivoTextoRoteiroEmpresa.getSituacaoTransmissaoLeitura().setId(new Integer(1));
			}
		}

		// arquivoTextoRoteiroEmpresa
		// .setSituacaoTransmissaoLeitura(consultarArquivoTextoLeituraActionForm
		// .getSituaTransmLeitura());

		// String mesAno = consultarArquivoTextoLeituraActionForm
		// .getMesAno();
		//		
		// arquivoTextoRoteiroEmpresa.setNumeroArquivo(consultarArquivoTextoLeituraActionForm.getNumeroArquivo());
		//		
		// arquivoTextoRoteiroEmpresa.setQuantidadeImovel(new
		// Integer(consultarArquivoTextoLeituraActionForm.getQuantidadeImovel()));
		//		
		// arquivoTextoRoteiroEmpresa.getLocalidade().setLocalidade(consultarArquivoTextoLeituraActionForm.getLocalidadeID());
		//
		// arquivoTextoRoteiroEmpresa.setNumeroFoneLeiturista(consultarArquivoTextoLeituraActionForm.getNumeroFoneLeiturista());

		// consultarArquivoTextoLeituraActionForm.setSituaTransmLeitura("2");

		// String situaTransmLeitura =
		// consultarArquivoTextoLeituraActionForm.getSituaTransmLeitura();

		// arquivoTextoRoteiroEmpresa.setSituacaoTransmissaoLeitura(new Integer
		// (consultarArquivoTextoLeituraActionForm.getSituaTransmLeitura()));
		//		
		// String situaTransmLeitura = consultarArquivoTextoLeituraActionForm
		// .getSituaTransmLeitura();
		//
		// String quantidadeImovel = consultarArquivoTextoLeituraActionForm
		// .getQuantidadeImovel();
		//
		// String localidadeID = consultarArquivoTextoLeituraActionForm
		// .getLocalidadeID();
		//		
		// String numeroFoneLeiturista = atualizarConsultarArquivoTextoLeituraActionForm
		// .getNumeroFoneLeiturista();

		// // Empresa
		// Empresa empresa = null;
		//
		// if (consultarArquivoTextoLeituraActionForm.getEmpresaID() != null
		// && !consultarArquivoTextoLeituraActionForm
		// .getEmpresaID().equals("")
		// && !consultarArquivoTextoLeituraActionForm
		// .getEmpresaID().equals(
		// "" + ConstantesSistema.NUMERO_NAO_INFORMADO)) {
		// empresa = new Empresa();
		//
		// arquivoTextoRoteiroEmpresa.setId(new Integer(
		// consultarArquivoTextoLeituraActionForm
		// .getEmpresaID()));
		// }
		//
		// arquivoTextoRoteiroEmpresa.setEmpresa(empresa);
		//
		// // Grupo Faturamento
		//
		// if (consultarArquivoTextoLeituraActionForm
		// .getGrupoFaturamentoID() != null) {
		//
		// Integer grupoFaturamentoID = new Integer(
		// consultarArquivoTextoLeituraActionForm
		// .getGrupoFaturamentoID());
		//
		// if (grupoFaturamentoID
		// .equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) {
		//
		// arquivoTextoRoteiroEmpresa.setFaturamentoGrupo(null);
		// } else {
		// FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		// filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(
		// FiltroFaturamentoGrupo.ID,
		// consultarArquivoTextoLeituraActionForm
		// .getGrupoFaturamentoID().toString()));
		// Collection colecaoGrupoFaturamento = (Collection) fachada
		// .pesquisar(filtroFaturamentoGrupo,
		// FaturamentoGrupo.class.getName());
		//
		// // setando
		// arquivoTextoRoteiroEmpresa
		// .setFaturamentoGrupo((FaturamentoGrupo) colecaoGrupoFaturamento
		// .iterator().next());
		// }
		// }

		// Indicador de Uso
		// arquivoTextoRoteiroEmpresa.setSituacaoTransmissaoLeitura(new Short("1"));

		// Date mesAnoFormatada = Util
		// .converteStringParaDate(mesAno);
		//		
		// arquivoTextoRoteiroEmpresa
		// .setDataTentativaEntrega(dataDevolucaoFormatada);

		// SituacaoTransmissaoLeitura situacaoTransmissaoLeitura = new SituacaoTransmissaoLeitura();
		//		
		//		
		// situacaoTransmissaoLeitura
		// .setId(situacaoTransmissaoLeitura);
		// arquivoTextoRoteiroEmpresa
		// .setSituacaoTransmissaoLeitura(situacaoTransmissaoLeitura);

		// arquivoTextoRoteiroEmpresa.setUltimaAlteracao(new Date());

		// fachada.liberarArquivoTextoLeitura(arquivoTextoRoteiroEmpresa);

		montarPaginaSucesso(httpServletRequest, "Arquivo Texto para Leitura" + arquivoTextoRoteiroEmpresa.getSituacaoTransmissaoLeitura()
						+ " liberado com sucesso.", "Realizar outra Manutenção de Arquivo Texto para Leitura",
						"exibirConsultarArquivoTextoLeituraAction.do");

		return retorno;

	}

}
