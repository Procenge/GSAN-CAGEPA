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

package gcom.gui.cadastro.documentoeletronico;

import gcom.cadastro.atendimento.AtendimentoDocumentoTipo;
import gcom.cadastro.atendimento.DocumentoEletronico;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

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
public class InserirDocumentoEletronicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirDocumentoEletronicoActionForm form = (InserirDocumentoEletronicoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		DocumentoEletronico documentoEletronico = new DocumentoEletronico();

		if((form.getIdCliente() == null || form.getIdCliente().equals("")) && (form.getIdImovel() == null || form.getIdImovel().equals(""))){
			throw new ActionServletException("atencao.documento_eletronico_sem_informar_cliente_imovel");
		}else{
			if(form.getIdCliente() != null && !form.getIdCliente().equals("")){
				Cliente cliente = new Cliente();

				cliente.setId(Integer.valueOf(form.getIdCliente()));

				documentoEletronico.setCliente(cliente);
			}

			if(form.getIdImovel() != null && !form.getIdImovel().equals("")){
				Imovel imovel = new Imovel();

				imovel.setId(Integer.valueOf(form.getIdImovel()));

				documentoEletronico.setImovel(imovel);
			}

		}

		if(form.getIdTipoDocumento() == null || form.getIdTipoDocumento().equals("") || form.getIdTipoDocumento().equals("-1")){
			throw new ActionServletException("atencao.documento_eletronico_sem_tipo_documento");
		} else {
			AtendimentoDocumentoTipo atendimentoDocumentoTipo = new AtendimentoDocumentoTipo();

			atendimentoDocumentoTipo.setId(Integer.valueOf(form.getIdTipoDocumento()));

			documentoEletronico.setAtendimentoDocumentoTipo(atendimentoDocumentoTipo);			
		}

		if(form.getConteudoArquivoUpload() == null || form.getConteudoArquivoUpload().equals("")){
			throw new ActionServletException("atencao.documento_eletronico_sem_arquivo");
		}


		FormFile conteudoArquivo = (FormFile) form.getConteudoArquivoUpload();
		if(conteudoArquivo != null){

			byte[] conteudoAquivoDocumento = null;

			try{
				conteudoAquivoDocumento = conteudoArquivo.getFileData();
			}catch(FileNotFoundException e){
				e.printStackTrace(); // TODO -> vsm: tratar exceção
			}catch(IOException e){
				e.printStackTrace(); // TODO -> vsm: tratar exceção
			}

			documentoEletronico.setNomeArquivo(conteudoArquivo.getFileName());
			documentoEletronico.setImagemDocumento(conteudoAquivoDocumento);
		}

		documentoEletronico.setUltimaAlteracao(new Date());

		// Registrar Transação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_DOCUMENTO_ELETRONICA,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_DOCUMENTO_ELETRONICA);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		Integer idDocumentoEletronico = (Integer) fachada.inserir(documentoEletronico);

		montarPaginaSucesso(httpServletRequest, "Documento Eletrônico " + idDocumentoEletronico + " inserido com sucesso.",
						"Inserir outro Documento Eletrônico", "exibirInserirDocumentoEletronicoAction.do?menu=sim");

		return retorno;
	}
}