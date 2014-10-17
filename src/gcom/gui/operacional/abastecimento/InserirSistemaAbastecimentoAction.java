/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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

package gcom.gui.operacional.abastecimento;

import gcom.cadastro.localidade.GerenciaRegional;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.SistemaAbastecimento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UCXXXX] Inserir Sistema Abastecimento
 * 
 * @author Anderson Italo
 * @date 28/01/2011
 */
public class InserirSistemaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirSistemaAbastecimentoActionForm form = (InserirSistemaAbastecimentoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// [FS0002 - Verificar preenchimento dos campos]
		String descricao = form.getDescricao();
		String descricaoAbreviada = form.getDescricaoAbreviada();
		String codigo = form.getCodigo();

		if(codigo == null || codigo.equals("")){
			throw new ActionServletException("atencao.codigo_sistema_abastecimento_nao_informado");
		}

		if(descricao == null || descricao.equals("")){
			throw new ActionServletException("atencao.descricao_sistema_abastecimento_nao_informado");
		}

		// [FS0003 - Verificar exist�ncia do sistema Abastecimento].
		SistemaAbastecimento sistemaAbastecimento = this.getFachada().pesquisarSistemaAbastecimento(new Integer(codigo));

		if(sistemaAbastecimento != null){
			throw new ActionServletException("atencao.sistema_abastecimento_existente");
		}else{
			sistemaAbastecimento = null;
		}

		// Criar o objeto sistemaAbastecimento que ser� inserido na base
		sistemaAbastecimento = new SistemaAbastecimento();
		sistemaAbastecimento.setCodigo(new Integer(codigo));
		sistemaAbastecimento.setDescricao(descricao);
		sistemaAbastecimento.setDescricaoAbreviada(descricaoAbreviada);
		sistemaAbastecimento.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		sistemaAbastecimento.setUltimaAlteracao(new Date());
		GerenciaRegional gerenciaRegional = new GerenciaRegional();
		gerenciaRegional.setId(Integer.parseInt(form.getGerenciaRegional()));
		sistemaAbastecimento.setGerenciaRegional(gerenciaRegional);

		Integer codigoSistemaAbastecimentoInserido = (Integer) this.getFachada().inserirSistemaAbastecimento(sistemaAbastecimento, usuario);

		if(codigoSistemaAbastecimentoInserido == null || codigoSistemaAbastecimentoInserido.intValue() == 0){
			throw new ActionServletException("erro.sistema");
		}else{

			montarPaginaSucesso(httpServletRequest, "Sistema de Abastecimento de c�digo " + sistemaAbastecimento.getCodigo() + " - "
							+ sistemaAbastecimento.getDescricao().toUpperCase() + " inserido com sucesso.",
							"Inserir outro Sistema Abastecimento", "exibirInserirSistemaAbastecimentoAction.do?menu=sim",
							"exibirAtualizarSistemaAbastecimentoAction.do?menu=sim&idRegistroAtualizacao="
											+ codigoSistemaAbastecimentoInserido, "Atualizar Sistema de Abastecimento Inserido");
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

}
