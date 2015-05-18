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
import gcom.cadastro.atendimento.bean.AtendimentoDocumentacaoInformadaHelper;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.FiltroFuncionalidade;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * Descrição da classe
 * 
 * @author Gicevalter Couto
 * @date 30/09/2014
 */
public class ExibirInserirAtendimentoAction
				extends GcomAction {


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno para a tela de inserir operação
		ActionForward retorno = actionMapping.findForward("atendimentoInserir");

		// Recupera o form de inserir procedmento de atendimento
		InserirAtendimentoActionForm form = (InserirAtendimentoActionForm) actionForm;

		// Cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getAttribute("enderecoURLChamada") != null){
			sessao.setAttribute("enderecoURLChamada", httpServletRequest.getAttribute("enderecoURLChamada").toString());
		}

		if (form.getDataInicioAtendimento() == null || !form.getDataInicioAtendimento().equals("") ) {
			SimpleDateFormat dataFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String dateStringFormatada = dataFormat.format(new Date());

			form.setDataInicioAtendimento(dateStringFormatada);
		}
		
		Collection<AtendimentoProcedimento> colecaoAtendimentoProcedimento = null;
		if(httpServletRequest.getAttribute("idFuncionalidade") != null || httpServletRequest.getParameter("desfazer") != null){
			FiltroFuncionalidade filtroFuncionalidade = new FiltroFuncionalidade();

			if(httpServletRequest.getParameter("desfazer") != null){
				filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, Integer.valueOf(form
								.getIdFuncionalidade().toString())));
			}else{
				filtroFuncionalidade.adicionarParametro(new ParametroSimples(FiltroFuncionalidade.ID, Integer.valueOf(httpServletRequest
								.getAttribute("idFuncionalidade").toString())));
			}

			gcom.seguranca.acesso.Funcionalidade funcionalidadeAtual = (gcom.seguranca.acesso.Funcionalidade) Util
							.retonarObjetoDeColecao(fachada.pesquisar(filtroFuncionalidade,
											gcom.seguranca.acesso.Funcionalidade.class.getName()));
			
			form.setIdFuncionalidade(String.valueOf(funcionalidadeAtual.getId()));
			form.setDescricaoFuncionalidade(funcionalidadeAtual.getDescricao());

			FiltroAtendimentoProcedimento filtroAtendimentoProcedimento = new FiltroAtendimentoProcedimento();
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao");
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao.solicitacaoTipo");
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");

			filtroAtendimentoProcedimento.setCampoOrderBy("solicitacaoTipoEspecificacao.solicitacaoTipo.descricao");
			filtroAtendimentoProcedimento.setCampoOrderBy("solicitacaoTipoEspecificacao.descricao");

			filtroAtendimentoProcedimento.adicionarParametro(new ParametroSimples(FiltroAtendimentoProcedimento.INDICADOR_USO,
							ConstantesSistema.SIM));
			filtroAtendimentoProcedimento.adicionarParametro(new ParametroSimples(FiltroAtendimentoProcedimento.FUNCIONALIDADE_ID,
							funcionalidadeAtual.getId()));

			colecaoAtendimentoProcedimento = fachada.pesquisar(filtroAtendimentoProcedimento,
							AtendimentoProcedimento.class.getName());

			sessao.setAttribute("colecaoAtendimentoProcedimento", colecaoAtendimentoProcedimento);

			for(AtendimentoProcedimento atendimentoProcedimentoAtual : colecaoAtendimentoProcedimento){
				carregarDadosAtendimentoProcedimento(atendimentoProcedimentoAtual, form, sessao);

				break;
			}

		}else if(httpServletRequest.getParameter("carregarEspecificacaoAtendimento") != null){

			FiltroAtendimentoProcedimento filtroAtendimentoProcedimento = new FiltroAtendimentoProcedimento();
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao");
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao.solicitacaoTipo");
			filtroAtendimentoProcedimento.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");

			filtroAtendimentoProcedimento.adicionarParametro(new ParametroSimples(FiltroAtendimentoProcedimento.ID, form
							.getIdAtendimentoProcedimento()));

			AtendimentoProcedimento atendimentoProcedimentoAtual = (AtendimentoProcedimento) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroAtendimentoProcedimento,
							AtendimentoProcedimento.class.getName()));

			carregarDadosAtendimentoProcedimento(atendimentoProcedimentoAtual, form, sessao);
		}
		
		
		if(httpServletRequest.getParameter("carregarImovel") != null && form.getIdImovel() != null
						&& !form.getIdImovel().trim().equalsIgnoreCase("")){

			Imovel imovelAtual = fachada.pesquisarImovel(Integer.valueOf(form.getIdImovel()));

			if(imovelAtual != null){
				form.setIdImovel(String.valueOf(imovelAtual.getId()));
				form.setInscricaoImovel(imovelAtual.getInscricaoFormatada().toString());
				httpServletRequest.setAttribute("imovelEncontrado", "true");

			}else{
				form.setIdImovel("");
				form.setInscricaoImovel("FUNCIONALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("imovelNaoEncontrado", "exception");
			}
		}

		if(httpServletRequest.getParameter("carregarCliente") != null && form.getIdCliente() != null
						&& !form.getIdCliente().trim().equalsIgnoreCase("")){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(form.getIdCliente())));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
			Cliente clienteAtual = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCliente,
							Cliente.class.getName()));


			if(clienteAtual != null){
				form.setIdCliente(String.valueOf(clienteAtual.getId()));
				form.setNomeCliente(clienteAtual.getDescricao().toString());
				httpServletRequest.setAttribute("clienteEncontrado", "true");

			}else{
				form.setIdCliente("");
				form.setNomeCliente("CLIENTE INEXISTENTE");
				httpServletRequest.setAttribute("clienteNaoEncontrado", "exception");
			}
		}

		if(httpServletRequest.getParameter("carregarImagem") != null){
			int indexDocumentacao = Integer.valueOf(httpServletRequest.getParameter("carregarImagem").toString()).intValue();
			AtendimentoDocumentacaoInformadaHelper atendimentoDocumentacao = form.getAtendimentoDocumentacao(indexDocumentacao);
			
			FormFile conteudoArquivo = (FormFile) form.getConteudoArquivoUpload(indexDocumentacao);
			if(conteudoArquivo != null){

				byte[] conteudoAquivoDocumento = null;

				try{
					conteudoAquivoDocumento = conteudoArquivo.getFileData();
				}catch(FileNotFoundException e){
					e.printStackTrace(); // TODO -> vsm: tratar exceção
				}catch(IOException e){
					e.printStackTrace(); // TODO -> vsm: tratar exceção
				}

				atendimentoDocumentacao.setNomeArquivo(conteudoArquivo.getFileName());
				atendimentoDocumentacao.setConteudoArquivo(conteudoAquivoDocumento);
			}
		}

		if(httpServletRequest.getParameter("visualizarNorma") != null && sessao.getAttribute("colecaoAtendProcNormaProcedimental") != null){

			Collection<AtendProcNormaProcedimental> colecaoAtendProcNormaProcedimental = (Collection<AtendProcNormaProcedimental>) sessao
							.getAttribute("colecaoAtendProcNormaProcedimental");

			if(colecaoAtendProcNormaProcedimental.size() > 0){
				OutputStream out = null;
				try{

					AtendProcNormaProcedimental atendProcNormaProcedimentalVisualizada = null;
					for(AtendProcNormaProcedimental atendProcNormaProcedimental : colecaoAtendProcNormaProcedimental){
						if(atendProcNormaProcedimental.getNormaProcedimental().getId()
										.equals(
										Integer.valueOf(httpServletRequest.getParameter("visualizarNorma").toString()))){
							atendProcNormaProcedimentalVisualizada = atendProcNormaProcedimental;
						}
					}

					if(atendProcNormaProcedimentalVisualizada != null){
						httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + '"'
										+ atendProcNormaProcedimentalVisualizada.getNormaProcedimental().getNomeArquivo().toString() + '"'
										+ ";");
						String mimeType = "application/octet-stream";
						
						httpServletResponse.setContentType(mimeType);
						out = httpServletResponse.getOutputStream();
						out.write(atendProcNormaProcedimentalVisualizada.getNormaProcedimental().getConteudo());
						out.flush();
						out.close();
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
			}
		}

		if(httpServletRequest.getParameter("desfazer") != null){
			form.setIdImovel("");
			form.setInscricaoImovel("");

			form.setIdCliente("");
			form.setNomeCliente("");

			form.setIdEspecificacao("");
			form.setIdAtendimentoProcedimento("");
			form.setIdEspecificacao("");
		}


		// Retorna o mapeamento contido na variável retorno
		return retorno;

	}

	private void carregarDadosAtendimentoProcedimento(AtendimentoProcedimento atendimentoProcedimento, InserirAtendimentoActionForm form,
					HttpSession sessao){

		if(atendimentoProcedimento.getIndicadorCliente().equals(ConstantesSistema.SIM)){
			sessao.setAttribute("exibirCliente", "S");

			form.setIdImovel("");
			form.setInscricaoImovel("");
			form.setIndicadorClienteImovel("C");

		}else{
			sessao.removeAttribute("exibirCliente");
		}

		if(atendimentoProcedimento.getIndicadorImovel().equals(ConstantesSistema.SIM)){
			sessao.setAttribute("exibirImovel", "S");

			form.setIdCliente("");
			form.setNomeCliente("");
			form.setIndicadorClienteImovel("I");

		}else{
			sessao.removeAttribute("exibirImovel");
		}

		if(atendimentoProcedimento.getIndicadorDispensadoDocumentacaoObrigatoria().equals(ConstantesSistema.SIM)){
			form.setIndicadorDispensadoDocumentacaoObrigatoria(ConstantesSistema.SIM.toString());
		}else{
			form.setIndicadorDispensadoDocumentacaoObrigatoria(ConstantesSistema.NAO.toString());
		}

		FiltroAtendProcDocumentoPessoaTipo filtroAtendProcDocumentoPessoaTipo = new FiltroAtendProcDocumentoPessoaTipo();
		filtroAtendProcDocumentoPessoaTipo.adicionarParametro(new ParametroSimples(
						FiltroAtendProcDocumentoPessoaTipo.ATENDIMENTO_PROCEDIMENTO_ID, atendimentoProcedimento.getId()));
		filtroAtendProcDocumentoPessoaTipo.adicionarCaminhoParaCarregamentoEntidade("atendimentoPessoaTipo");
		filtroAtendProcDocumentoPessoaTipo.adicionarCaminhoParaCarregamentoEntidade("atendimentoDocumentoTipo");

		Collection<AtendimentoDocumentacaoInformadaHelper> colecaoAtendimentoDocumentacao = new ArrayList<AtendimentoDocumentacaoInformadaHelper>();

		Collection<AtendProcDocumentoPessoaTipo> colecaoAtendProcDocumentoPessoaTipo = getFachada().pesquisar(
						filtroAtendProcDocumentoPessoaTipo, AtendProcDocumentoPessoaTipo.class.getName());

		for(AtendProcDocumentoPessoaTipo atendProcDocumentoPessoaTipo : colecaoAtendProcDocumentoPessoaTipo){
			AtendimentoDocumentacaoInformadaHelper atendimentoDocumentacao = new AtendimentoDocumentacaoInformadaHelper();

			String descricaoTipoEspecificacao="";
			
			if (atendProcDocumentoPessoaTipo.getAtendimentoPessoaTipo() != null && atendProcDocumentoPessoaTipo.getAtendimentoPessoaTipo().getDescricao() != null && !atendProcDocumentoPessoaTipo.getAtendimentoPessoaTipo().getDescricao().equals("")  ) {
				descricaoTipoEspecificacao = atendProcDocumentoPessoaTipo.getAtendimentoPessoaTipo().getDescricao();
			}
			
			if (atendProcDocumentoPessoaTipo.getAtendimentoDocumentoTipo() != null && atendProcDocumentoPessoaTipo.getAtendimentoDocumentoTipo().getDescricao() != null && !atendProcDocumentoPessoaTipo.getAtendimentoDocumentoTipo().getDescricao().equals("")  ) {
				if (descricaoTipoEspecificacao.equals("") ) {
					descricaoTipoEspecificacao= atendProcDocumentoPessoaTipo.getAtendimentoDocumentoTipo().getDescricao();
				} else {
					descricaoTipoEspecificacao = atendProcDocumentoPessoaTipo.getAtendimentoDocumentoTipo().getDescricao()
									.concat(" / " + descricaoTipoEspecificacao);
				}
			}
			
			atendimentoDocumentacao.setDescricaoEspecificacao(descricaoTipoEspecificacao);
			atendimentoDocumentacao.setAtendProcDocumentoPessoaTipo(atendProcDocumentoPessoaTipo);
			atendimentoDocumentacao.setConteudoArquivo(null);
			atendimentoDocumentacao.setNomeArquivo(null);
			atendimentoDocumentacao.setIndicadorDocumentoApresentado(ConstantesSistema.NAO.toString());
			atendimentoDocumentacao.setIdentificadorDocumentoApresentado(null);
			atendimentoDocumentacao.setIndicadorDocumentoObrigatorio(atendProcDocumentoPessoaTipo.getIndicadorDocumentoObrigatorio());
			atendimentoDocumentacao.setUltimaAlteracao(new Date());
			
			colecaoAtendimentoDocumentacao.add(atendimentoDocumentacao);
		}

		form.setConteudoArquivoUpload(new FormFile[colecaoAtendimentoDocumentacao.size()]);
		form.setColecaoAtendimentoDocumentacao((List) colecaoAtendimentoDocumentacao);

		FiltroAtendProcNormaProcedimental filtroAtendProcNormaProcedimental = new FiltroAtendProcNormaProcedimental();
		filtroAtendProcNormaProcedimental.adicionarParametro(new ParametroSimples(
						FiltroAtendProcNormaProcedimental.ATENDIMENTO_PROCEDIMENTO_ID, atendimentoProcedimento.getId()));
		filtroAtendProcNormaProcedimental.adicionarCaminhoParaCarregamentoEntidade("normaProcedimental");

		Collection colecaoAtendProcNormaProcedimental = getFachada().pesquisar(filtroAtendProcNormaProcedimental,
						AtendProcNormaProcedimental.class.getName());

		sessao.setAttribute("colecaoAtendProcNormaProcedimental", colecaoAtendProcNormaProcedimental);

	}

}