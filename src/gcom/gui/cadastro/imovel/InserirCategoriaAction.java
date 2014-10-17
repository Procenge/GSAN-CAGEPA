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

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.CategoriaTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Roberta Costa
 * @created 22 de Dezembro de 2005
 */
public class InserirCategoriaAction
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
	 * @author Andre Nishimura
	 * @date 02 de Janeiro de 2009
	 *       Descomentado o bloco que registra a inserção de uma nova categoria e envia para
	 *       pagina de sucesso.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		CategoriaActionForm categoriaActionForm = (CategoriaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CATEGORIA_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_CATEGORIA_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String descricao = (String) categoriaActionForm.getDescricao();
		String descricaoAbreviada = (String) categoriaActionForm.getDescricaoAbreviada();
		String tipoCategoria = (String) categoriaActionForm.getTipoCategoria();
		Integer consumoMinimo = Util.obterInteger(categoriaActionForm.getConsumoMinimo());
		Integer consumoEstouro = Util.obterInteger(categoriaActionForm.getConsumoEstouro());
		BigDecimal vezesMediaEstouro = new BigDecimal(categoriaActionForm.getVezesMediaEstouro().replace(",", "."));
		Integer mediaBaixoConsumo = Util.obterInteger(categoriaActionForm.getMediaBaixoConsumo());
		BigDecimal porcentagemMediaBaixoConsumo = new BigDecimal(categoriaActionForm.getPorcentagemMediaBaixoConsumo().replace(",", "."));
		Integer consumoAlto = Util.obterInteger(categoriaActionForm.getConsumoAlto());
		BigDecimal vezesMediaAltoConsumo = new BigDecimal(categoriaActionForm.getVezesMediaAltoConsumo().replace(",", "."));
		Integer consumoMedioEconomiaMes = Util.obterInteger(categoriaActionForm.getConsumoMedioEconomiaMes());
		Short indicadorDeUso = ConstantesSistema.INDICADOR_USO_ATIVO;

		// Tipo de Categoria
		CategoriaTipo categoriaTipo = new CategoriaTipo();
		categoriaTipo.setId(Util.obterInteger(tipoCategoria));

		Categoria categoria = new Categoria(descricao, descricaoAbreviada, consumoMinimo, consumoEstouro, vezesMediaEstouro,
						mediaBaixoConsumo, porcentagemMediaBaixoConsumo, consumoAlto, vezesMediaAltoConsumo, indicadorDeUso, new Date(),
						categoriaTipo);

		categoria.setConsumoMedioEconomiaMes(consumoMedioEconomiaMes);

		// ------------ REGISTRAR TRANSAÇÃO ----------------
		categoria.setOperacaoEfetuada(operacaoEfetuada);
		categoria.adicionarUsuario(getUsuarioLogado(httpServletRequest), UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(categoria);
		// ------------ REGISTRAR TRANSAÇÃO ----------------
		// Insere a Categoria - As validações estão no Controlador Integer
		int idCategoriaInserida = (Integer) fachada.inserirCategoria(categoria);

		montarPaginaSucesso(httpServletRequest, "Categoria de código " + idCategoriaInserida + " inserida com sucesso.", "Inserir outra"
						+ " Categoria", "exibirInserirCategoriaAction.do", "exibirAtualizarCategoriaAction.do?idRegistroAtualizacao="
						+ idCategoriaInserida, "Atualizar Categoria Inserida");

		return retorno;
	}
}