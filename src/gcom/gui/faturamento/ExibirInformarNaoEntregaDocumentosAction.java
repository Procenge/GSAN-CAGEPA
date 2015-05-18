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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.faturamento;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.cobranca.FiltroMotivoNaoEntregaDocumento;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0559]Informar Nao Entrega de Documentos
 * 
 * @author Thiago Tenório
 * @date 29/03/2007
 * @author eduardo henrique
 * @date 10/07/2008
 *       Alteração no JSP para seleção de motivos a partir de comboboxes
 */

public class ExibirInformarNaoEntregaDocumentosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("informarNaoEntregaDocumentos");

		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InformarNaoEntregaDocumentosActionForm form = (InformarNaoEntregaDocumentosActionForm) actionForm;

		// Limpando os objetos
		String limparForm = (String) httpServletRequest.getParameter("limparCampos");

		Collection colecaoPesquisa = null;

		// Verificar Existência do Responsável pela Entrega

		if((form.getIdResponsavelEntrega() != null && !form.getIdResponsavelEntrega().equals(""))){

			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, form.getIdResponsavelEntrega()));

			Collection colecaoResponsavelEntrega = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoResponsavelEntrega != null && !colecaoResponsavelEntrega.isEmpty()){

				Cliente cliente = (Cliente) colecaoResponsavelEntrega.iterator().next();
				form.setDescricaoResponsavelEntrega(cliente.getNome());

			}else{
				httpServletRequest.setAttribute("clienteEncontrado", "exception");
				form.setIdResponsavelEntrega("");
				form.setDescricaoResponsavelEntrega("RESPONSAVEL PELA ENTREGA INEXISTENTE");
			}

		}
		// Verificar Existência do Motivo

		if((form.getIdCodigo() != null && !form.getIdCodigo().equals(""))){

			FiltroMotivoNaoEntregaDocumento filtroMotivoNaoEntregaDocumento = new FiltroMotivoNaoEntregaDocumento();

			filtroMotivoNaoEntregaDocumento
							.adicionarParametro(new ParametroSimples(FiltroMotivoNaoEntregaDocumento.ID, form.getIdCodigo()));

			Collection colecaoMotivo = fachada.pesquisar(filtroMotivoNaoEntregaDocumento, MotivoNaoEntregaDocumento.class.getName());

			if(colecaoMotivo != null && !colecaoMotivo.isEmpty()){

				MotivoNaoEntregaDocumento motivoNaoEntregaDocumento = (MotivoNaoEntregaDocumento) colecaoMotivo.iterator().next();
				form.setDescricaoCodigo(motivoNaoEntregaDocumento.getDescricao());

			}else{
				httpServletRequest.setAttribute("motivoEncontrado", "exception");
				form.setIdCodigo("");
				form.setDescricaoCodigo("MOTIVO INEXISTENTE");
			}

		}

		// Carrega a coleção de Motivos de Não Entrega
		if(sessao.getAttribute("colecaoMotivosNaoEntrega") == null){
			FiltroMotivoNaoEntregaDocumento filtroMotivoNaoEntrega = new FiltroMotivoNaoEntregaDocumento();

			filtroMotivoNaoEntrega.adicionarParametro(new ParametroSimples(FiltroMotivoNaoEntregaDocumento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoMotivosNaoEntrega = fachada.pesquisar(filtroMotivoNaoEntrega, MotivoNaoEntregaDocumento.class.getName());

			if(colecaoMotivosNaoEntrega == null || colecaoMotivosNaoEntrega.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "MOTIVOS DE NÃO ENTREGA DE DOCUMENTO");
			}

			sessao.setAttribute("colecaoMotivosNaoEntrega", colecaoMotivosNaoEntrega);
		}

		// Passando a coleção do Tipo do Documento para o JSP
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();

		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);

		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna o Tipo do Documento
		colecaoPesquisa = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
			// Nenhum registro na tabela Documento_Tipo foi encontrado
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tipo do Documento");
		}else{
			sessao.setAttribute("colecaoTipoDocumento", colecaoPesquisa);
		}

		/*
		 * // Constrói filtro para pesquisa de Tipo do Documento
		 * filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.ID);
		 * filtroDocumentoTipo.adicionarParametro(new ParametroSimples(
		 * FiltroDocumentoTipo.ID, ConstantesSistema.INDICADOR_USO_ATIVO));
		 * sessao.setAttribute("colecaoTipoDocumento", fachada.pesquisar(
		 * filtroDocumentoTipo, DocumentoTipo.class.getName(),
		 * "Tipo do Documento"));
		 * httpServletRequest
		 * .setAttribute("colecaoTipoDocumento", colecaoPesquisa);
		 */

		// DESFAZER
		if((limparForm != null && !limparForm.trim().equalsIgnoreCase(""))
						|| (httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer")
										.equalsIgnoreCase("S"))){
			// -------------- bt DESFAZER ---------------

			// Limpando o formulario e setando alguns valores aos objetos
			form.setTipoDocumento("1");
			form.setMesAnoConta("");
			form.setMatricula1("");
			form.setMatricula2("");
			form.setMatricula3("");
			form.setMatricula4("");
			form.setMatricula5("");
			form.setMatricula6("");
			form.setMatricula7("");
			form.setMatricula8("");
			form.setMatricula9("");
			form.setMatricula10("");
			form.setMatricula11("");
			form.setMatricula12("");
			form.setMatricula13("");
			form.setMatricula14("");
			form.setMatricula15("");
			form.setMatricula16("");
			form.setMatricula17("");
			form.setMatricula18("");
			form.setMatricula19("");
			form.setMatricula20("");

			form.setQtTentativas1("1");
			form.setQtTentativas2("1");
			form.setQtTentativas3("1");
			form.setQtTentativas3("1");
			form.setQtTentativas4("1");
			form.setQtTentativas5("1");
			form.setQtTentativas6("1");
			form.setQtTentativas7("1");
			form.setQtTentativas8("1");
			form.setQtTentativas9("1");
			form.setQtTentativas10("1");
			form.setQtTentativas11("1");
			form.setQtTentativas12("1");
			form.setQtTentativas13("1");
			form.setQtTentativas14("1");
			form.setQtTentativas15("1");
			form.setQtTentativas16("1");
			form.setQtTentativas17("1");
			form.setQtTentativas18("1");
			form.setQtTentativas19("1");
			form.setQtTentativas20("1");
			form.setResponsavelEntrega("");
			form.setMotivo("");

		}

		if(httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").equals("")){
			// httpServletRequest
			// .setAttribute("nomeCampo", "idResponsavelEntrega");
			form.setTipoDocumento("1");
			form.setQtTentativas1("1");
			form.setQtTentativas2("1");
			form.setQtTentativas3("1");
			form.setQtTentativas4("1");
			form.setQtTentativas5("1");
			form.setQtTentativas6("1");
			form.setQtTentativas7("1");
			form.setQtTentativas8("1");
			form.setQtTentativas9("1");
			form.setQtTentativas10("1");
			form.setQtTentativas11("1");
			form.setQtTentativas12("1");
			form.setQtTentativas13("1");
			form.setQtTentativas14("1");
			form.setQtTentativas15("1");
			form.setQtTentativas16("1");
			form.setQtTentativas17("1");
			form.setQtTentativas18("1");
			form.setQtTentativas19("1");
			form.setQtTentativas20("1");
		}

		if(form.getTipoDocumento() != null && form.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
			httpServletRequest.setAttribute("exibirTabela", true);
		}

		return retorno;
	}
}