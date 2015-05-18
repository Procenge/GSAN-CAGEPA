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

package gcom.gui.cadastro.atendimento;

import gcom.cadastro.atendimento.*;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.relatorio.RelatorioVazioException;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de exibir consultar Atendimento Popup
 * 
 * @author Gicevalter Couto
 * @created 02/10/2014
 */
public class ExibirConsultarDadosAtendimentoPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarDadosAtendimentoPopup");
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarDadosAtendimentoPopupActionForm form = (ConsultarDadosAtendimentoPopupActionForm) actionForm;

		if (httpServletRequest.getParameter("idAtendimento") != null) {
			form.resetarConsultarDadosAtendimentoPopup();

			Integer idAtendimento = Integer.valueOf(httpServletRequest.getParameter("idAtendimento").toString());

			FiltroAtendimento filtroAtendimento = new FiltroAtendimento();
			filtroAtendimento.adicionarParametro(new ParametroSimples(FiltroAtendimento.ID, idAtendimento));
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATENDIMENTO);
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATEND_FUNCIONALIDADE);
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATEND_TIPO_ESPECIFICACAO);
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.PROCEDIMENTO_ATEND_TIPO_ESPECIFICACAO_TIPO);
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.IMOVEL);
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.CLIENTE);
			filtroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroAtendimento.USUARIO);
			filtroAtendimento.setCampoOrderByDesc(FiltroAtendimento.DATA_INICIO_ATENDIMENTO);

			Atendimento atendimento = (Atendimento) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroAtendimento,
							Atendimento.class.getName()));		

			
			form.setIdAtendimento(atendimento.getId().toString());
			form.setDescricaoAtendimentoProcedimento(atendimento.getAtendimentoProcedimento().getDescricao());

			form.setDescricaoFuncionalidade(atendimento.getAtendimentoProcedimento().getFuncionalidade().getDescricao());

			if(atendimento.getAtendimentoProcedimento().getSolicitacaoTipoEspecificacao() != null){
				form.setDescricaoTipoEspecificacao(atendimento.getAtendimentoProcedimento().getDescricaoTipoAndEspecificacao());
			}

			if(atendimento.getImovel() != null){
				sessao.setAttribute("exibirImovel", "S");

				form.setIdImovel(atendimento.getImovel().getId().toString());
				form.setInscricaoImovel(atendimento.getImovel().getInscricaoFormatada());

				form.setIdCliente("");
				form.setNomeCliente("");

			}else{
				sessao.removeAttribute("exibirImovel");

				form.setIdImovel("");
				form.setInscricaoImovel("");
			}

			if(atendimento.getCliente() != null){
				sessao.setAttribute("exibirCliente", "S");

				form.setIdCliente(atendimento.getCliente().getId().toString());
				form.setNomeCliente(atendimento.getCliente().getNome());

				form.setIdImovel("");
				form.setInscricaoImovel("");
			}else{

				form.setIdCliente("");
				form.setNomeCliente("");

				sessao.removeAttribute("exibirCliente");
			}
			
			form.setNomeUsuario(atendimento.getUsuario().getNomeUsuario());

			SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			form.setDataInicioAtendimento(dataFormat.format(atendimento.getDataInicioAtendimento()));

			if(atendimento.getAtendimentoProcedimento().getIndicadorDispensadoDocumentacaoObrigatoria().equals(ConstantesSistema.SIM)){
				form.setIndicadorDispensadoDocumentacaoObrigatoria("SIM");
			}else{
				form.setIndicadorDispensadoDocumentacaoObrigatoria("NAO");
			}

			// Documentos
			FiltroAtendimentoDocumentacao filtroAtendimentoDocumentacao = new FiltroAtendimentoDocumentacao();
			filtroAtendimentoDocumentacao.adicionarParametro(new ParametroSimples(FiltroAtendimentoDocumentacao.ID_ATENDIMENTO, atendimento
							.getId()));
			filtroAtendimentoDocumentacao.adicionarCaminhoParaCarregamentoEntidade("atendProcDocumentoPessoaTipo");
			filtroAtendimentoDocumentacao.adicionarCaminhoParaCarregamentoEntidade("atendProcDocumentoPessoaTipo.atendimentoPessoaTipo");
			filtroAtendimentoDocumentacao.adicionarCaminhoParaCarregamentoEntidade("atendProcDocumentoPessoaTipo.atendimentoDocumentoTipo");

			Collection<AtendimentoDocumentacao> colecaoAtendimentoDocumentacao = getFachada().pesquisar(filtroAtendimentoDocumentacao,
							AtendimentoDocumentacao.class.getName());

			form.setColecaoAtendimentoDocumentacao((List) colecaoAtendimentoDocumentacao);
		}

		if(httpServletRequest.getParameter("idAtendimentoDownload") != null){
			Integer idAtendimentoSelecionado = Integer.valueOf(httpServletRequest.getParameter("idAtendimentoDownload").toString());

			FiltroDocumentoEletronico filtroDocumentoEletronico = new FiltroDocumentoEletronico();
			filtroDocumentoEletronico.adicionarParametro(new ParametroSimples(FiltroDocumentoEletronico.ID_ATENDIMENTO_DOCUMENTACAO,
							idAtendimentoSelecionado));

			DocumentoEletronico documentoEletronico = (DocumentoEletronico) Util.retonarObjetoDeColecao(getFachada().pesquisar(
							filtroDocumentoEletronico, DocumentoEletronico.class.getName()));

			if(documentoEletronico != null){
				OutputStream out = null;
				try{

					if(documentoEletronico.getImagemDocumento() != null){
						httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + '"'
										+ documentoEletronico.getNomeArquivo().toString() + '"' + ";");
						String mimeType = "application/octet-stream";

						httpServletResponse.setContentType(mimeType);
						out = httpServletResponse.getOutputStream();
						out.write(documentoEletronico.getImagemDocumento());
						out.flush();
						out.close();
					}else{
						httpServletRequest.setAttribute("naoExisteArquivo", "S");
					}

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
			}else{
				httpServletRequest.setAttribute("naoExisteArquivo", "S");
			}

		}

		return retorno;
	}
}