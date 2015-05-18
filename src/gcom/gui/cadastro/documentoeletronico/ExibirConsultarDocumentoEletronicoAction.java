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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.cadastro.documentoeletronico;

import gcom.cadastro.atendimento.DocumentoEletronico;
import gcom.cadastro.atendimento.FiltroDocumentoEletronico;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.RelatorioVazioException;
import gcom.util.SistemaException;
import gcom.util.filtro.ParametroSimples;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirConsultarDocumentoEletronicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("consultarDocumentoEletronico");
		ConsultarDocumentoEletronicoActionForm form = (ConsultarDocumentoEletronicoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verifica se veio do filtrar ou do manter
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", true);
		}else if(httpServletRequest.getParameter("filtrar") != null){
			sessao.removeAttribute("manter");
		}

		if(httpServletRequest.getParameter("visualizarImagem") != null && form != null && form.getImagemDocumento() != null){
			OutputStream out = null;
			try{

				httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + '"' + form.getNomeArquivo().toString() + '"'
								+ ";");
				String mimeType = "application/octet-stream";

				httpServletResponse.setContentType(mimeType);
				out = httpServletResponse.getOutputStream();
				out.write(form.getImagemDocumento());
				out.flush();
				out.close();

			}catch(IOException ex){
				// manda o erro para a página no request atual
				reportarErros(httpServletRequest, "erro.sistema");

				// seta o mapeamento de retorno para a tela de erro de popup
				retorno = actionMapping.findForward("telaErroPopup");

			}catch(SistemaException ex){
				// manda o erro para a página no request atual
				reportarErros(httpServletRequest, "erro.sistema");

				// seta o mapeamento de retorno para a tela de erro de popup
				retorno = actionMapping.findForward("telaErroPopup");

			}catch(RelatorioVazioException ex1){
				// manda o erro para a página no request atual
				reportarErros(httpServletRequest, "erro.relatorio.vazio");

				// seta o mapeamento de retorno para a tela de atenção de popup
				retorno = actionMapping.findForward("telaAtencaoPopup");
			}
		}

		String codigoDocumentoEletronico = null;

		if(httpServletRequest.getParameter("idDocumentoEletronico") == null
						|| httpServletRequest.getParameter("idDocumentoEletronico").equals("")){
			DocumentoEletronico documentoEletronico = (DocumentoEletronico) sessao.getAttribute("objetoDocumentoEletronico");
			codigoDocumentoEletronico = documentoEletronico.getId().toString();
		}else{
			codigoDocumentoEletronico = (String) httpServletRequest.getParameter("idDocumentoEletronico");
			sessao.setAttribute("idDocumentoEletronico", codigoDocumentoEletronico);
		}

		httpServletRequest.setAttribute("idDocumentoEletronico", codigoDocumentoEletronico);
		sessao.setAttribute("idDocumentoEletronico", codigoDocumentoEletronico);

		FiltroDocumentoEletronico filtroDocumentoEletronico = new FiltroDocumentoEletronico();
		filtroDocumentoEletronico.adicionarParametro(new ParametroSimples(FiltroDocumentoEletronico.ID, codigoDocumentoEletronico));
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade(FiltroDocumentoEletronico.ATENDIMENTO_DOCUMENTO_TIPO);
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade(FiltroDocumentoEletronico.IMOVEL);
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
		filtroDocumentoEletronico.adicionarCaminhoParaCarregamentoEntidade(FiltroDocumentoEletronico.CLIENTE);

		// Pesquisando a DocumentoEletronico que foi escolhida
		Collection<DocumentoEletronico> colecaoDocumentoEletronico = fachada.pesquisar(filtroDocumentoEletronico, DocumentoEletronico.class.getName());

		// Não foi encontrado o registro
		if(colecaoDocumentoEletronico == null || colecaoDocumentoEletronico.isEmpty()){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		httpServletRequest.setAttribute("colecaoDocumentoEletronico", colecaoDocumentoEletronico);
		DocumentoEletronico documentoEletronico = (DocumentoEletronico) colecaoDocumentoEletronico.iterator().next();

		// Setando valores no form para ser exibido na tela de alualizar.
		if(documentoEletronico.getImovel() != null){
			form.setIdImovel(documentoEletronico.getImovel().getId().toString());
			form.setInscricaoImovel(documentoEletronico.getImovel().getInscricaoFormatada());
		}

		if(documentoEletronico.getCliente() != null){
			form.setIdCliente(documentoEletronico.getCliente().getId().toString());
			form.setNomeCliente(documentoEletronico.getCliente().getNome());
		}

		form.setDescricaoTipoDocumento(documentoEletronico.getAtendimentoDocumentoTipo().getDescricao());

		form.setNomeArquivo(documentoEletronico.getNomeArquivo());

		form.setImagemDocumento(documentoEletronico.getImagemDocumento());


		sessao.setAttribute("documentoEletronicoAtualizar", documentoEletronico);

		return retorno;

	}

}