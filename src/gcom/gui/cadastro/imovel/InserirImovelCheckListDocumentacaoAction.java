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

package gcom.gui.cadastro.imovel;

import gcom.cadastro.imovel.FiltroImovelCheckListDocumentacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCheckListDocumentacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3106] Inserir Check List de Documentação do Imóvel
 * Action responsável pela exibição da tela de Inserir Check List
 * 
 * @author Vicente Zarga
 * @created 06 de Setembro de 2013
 */
public class InserirImovelCheckListDocumentacaoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		ImovelCheckListDocumentacaoForm form = (ImovelCheckListDocumentacaoForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		String idImovel = form.getIdImovel();
		String indicadorContrato = form.getIndicadorContrato();
		String indicadorCpf = form.getIndicadorCpf();
		String indicadorRg = form.getIndicadorRg();
		String indicadorDocImovel = form.getIndicadorDocImovel();
		String indicadorTemoConfissaoDivida = form.getIndicadorTemoConfissaoDivida();
		String indicadorCorrespondencia = form.getIndicadorCorrespondencia();

		if(idImovel != null && !idImovel.equals("")){

			FiltroImovelCheckListDocumentacao filtroImovelCheckListDocumentacao = new FiltroImovelCheckListDocumentacao();
			filtroImovelCheckListDocumentacao.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelCheckListDocumentacao.IMOVEL);
			filtroImovelCheckListDocumentacao
							.adicionarParametro(new ParametroSimples(FiltroImovelCheckListDocumentacao.IMOVEL_ID, idImovel));
			Collection colecaoImovelCheckListDocumentacao = fachada.pesquisar(filtroImovelCheckListDocumentacao,
							ImovelCheckListDocumentacao.class.getName());

			if(colecaoImovelCheckListDocumentacao != null && !colecaoImovelCheckListDocumentacao.isEmpty()){

				ImovelCheckListDocumentacao imovelCheckListDocumentacao = (ImovelCheckListDocumentacao) Util
								.retonarObjetoDeColecao(colecaoImovelCheckListDocumentacao);

				throw new ActionServletException("atencao.check_list_documentos_imovel_ja_cadastrado", null, imovelCheckListDocumentacao
								.getImovel().getId().toString());
			}
		}else{

			throw new ActionServletException("atencao.imovel_nao_selecionado");
		}

		if(indicadorContrato == null){
			throw new ActionServletException("atencao.check_list_contrato_nao_informado");
		}
		if(indicadorCpf == null){
			throw new ActionServletException("atencao.check_list_cpf_nao_informado");
		}
		if(indicadorRg == null){
			throw new ActionServletException("atencao.check_list_rg_nao_informado");
		}
		if(indicadorDocImovel == null){
			throw new ActionServletException("atencao.check_list_documento_imovel_nao_informado");
		}
		if(indicadorTemoConfissaoDivida == null){
			throw new ActionServletException("atencao.check_list_termo_confissao_imovel_nao_informado");
		}
		if(indicadorCorrespondencia == null){
			throw new ActionServletException("atencao.check_list_correspondecia_nao_informado");
		}

		ImovelCheckListDocumentacao imovelCheckListDocumentacao = new ImovelCheckListDocumentacao();

		Imovel imovel = new Imovel();
		imovel.setId(Integer.parseInt(idImovel));

		imovelCheckListDocumentacao.setImovel(imovel);
		imovelCheckListDocumentacao.setIndicadorEntregaCopiaContrato(Short.parseShort(indicadorContrato));
		imovelCheckListDocumentacao.setIndicadorEntregaCopiaCpf(Short.parseShort(indicadorCpf));
		imovelCheckListDocumentacao.setIndicadorEntregaCopiaRg(Short.parseShort(indicadorRg));
		imovelCheckListDocumentacao.setIndicadorEntregaCopiaDocImovel(Short.parseShort(indicadorDocImovel));
		imovelCheckListDocumentacao.setIndicadorEntregaCopiaTermoConfDivida(Short.parseShort(indicadorTemoConfissaoDivida));
		imovelCheckListDocumentacao.setIndicadorEntregaCopiaCorrespondencia(Short.parseShort(indicadorCorrespondencia));
		imovelCheckListDocumentacao.setUltimaAlteracao(new Date());

		Integer chaveCheckList = (Integer) fachada.inserir(imovelCheckListDocumentacao);

		// Registrar Transação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL,
						imovelCheckListDocumentacao.getId(), new UsuarioAcaoUsuarioHelper(usuario,
										UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		imovelCheckListDocumentacao.setOperacaoEfetuada(operacaoEfetuada);
		imovelCheckListDocumentacao.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

		registradorOperacao.registrarOperacao(imovelCheckListDocumentacao);

		montarPaginaSucesso(httpServletRequest, "Check List para o imóvel " + idImovel + " inserido com sucesso.",
						"Inserir outro Check List de Documentos", "exibirInserirImovelCheckListDocumentacaoAction.do?menu=sim",
						"exibirConsultarImovelCheckListDocumentacaoAction.do?codigoCheckList=" + chaveCheckList,
						"Consultar Check List inserido");

		return retorno;
	}
}
