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
 * Descri��o da classe
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
				e.printStackTrace(); // TODO -> vsm: tratar exce��o
			}catch(IOException e){
				e.printStackTrace(); // TODO -> vsm: tratar exce��o
			}

			documentoEletronico.setNomeArquivo(conteudoArquivo.getFileName());
			documentoEletronico.setImagemDocumento(conteudoAquivoDocumento);
		}

		documentoEletronico.setUltimaAlteracao(new Date());

		// Registrar Transa��o
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_DOCUMENTO_ELETRONICA,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_DOCUMENTO_ELETRONICA);
		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		Integer idDocumentoEletronico = (Integer) fachada.inserir(documentoEletronico);

		montarPaginaSucesso(httpServletRequest, "Documento Eletr�nico " + idDocumentoEletronico + " inserido com sucesso.",
						"Inserir outro Documento Eletr�nico", "exibirInserirDocumentoEletronicoAction.do?menu=sim");

		return retorno;
	}
}