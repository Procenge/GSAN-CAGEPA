/**
 * 
 */
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
 * @date 01/11/2006
 */
public class AtualizarValorCobrancaServicoAction
				extends GcomAction {

	/**
	 * [UC0393] Manter Valor de Cobran�a do Servi�o
	 * Este caso de uso cria um filtro que ser� usado na pesquisa do Valor de
	 * Cobran�a de Servi�o
	 * [SB0001] Atualizar Valor de Cobran�a do Servi�o
	 * 
	 * @author R�mulo Aur�lio
	 * @date 01/11/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarValorCobrancaServicoActionForm atualizarValorCobrancaServicoActionForm = (AtualizarValorCobrancaServicoActionForm) actionForm;
		// ------------Registrar Transacao------------------
		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Registrando a opera��o
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_ATUALIZAR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_ATUALIZAR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);
		// -------------------------------------------

		ServicoCobrancaValor servicoCobrancaValor = (ServicoCobrancaValor) sessao.getAttribute("servicoCobrancaValorAtualizar");
		String descricaoTipoServico = atualizarValorCobrancaServicoActionForm.getNomeTipoServico();
		String indicadorMedido = atualizarValorCobrancaServicoActionForm.getIndicadorMedido();
		String tipoServico = atualizarValorCobrancaServicoActionForm.getTipoServico();
		String perfilImovel = atualizarValorCobrancaServicoActionForm.getPerfilImovel();
		String capacidadeHidrometro = atualizarValorCobrancaServicoActionForm.getCapacidadeHidrometro();
		String valorServico = atualizarValorCobrancaServicoActionForm.getValorServico();
		String valorSemPontos = valorServico.replace(".", "");
		valorServico = valorSemPontos.replace(",", ".");
		servicoCobrancaValor.setValor(new BigDecimal(valorServico));

		// Seta no Objeto os dados do form

		ServicoTipo servicoTipo = new ServicoTipo();
		servicoTipo.setId(Integer.valueOf(tipoServico));
		servicoCobrancaValor.setServicoTipo(servicoTipo);
		servicoCobrancaValor.setIndicadorMedido(Short.valueOf(indicadorMedido));

		if(capacidadeHidrometro != null && !capacidadeHidrometro.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
			hidrometroCapacidade.setId(Integer.valueOf(capacidadeHidrometro));
			servicoCobrancaValor.setHidrometroCapacidade(hidrometroCapacidade);
		}

		if(perfilImovel != null && !perfilImovel.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){

			ImovelPerfil imovelPerfil = new ImovelPerfil();
			imovelPerfil.setId(Integer.valueOf(perfilImovel));
			servicoCobrancaValor.setImovelPerfil(imovelPerfil);

		}

		// ------------Registra opera��o------------
		servicoCobrancaValor.setOperacaoEfetuada(operacaoEfetuada);
		servicoCobrancaValor.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(servicoCobrancaValor);
		// -----------------------------------------
		fachada.atualizarValorCobrancaServico(servicoCobrancaValor);

		montarPaginaSucesso(httpServletRequest, "Valor da Cobran�a do Servi�o " + descricaoTipoServico + " atualizado com sucesso.",
						"Realizar outra Manuten��o Valor da Cobran�a do Servi�o", "exibirFiltrarValorCobrancaServicoAction.do?menu=sim");

		return retorno;
	}
}
