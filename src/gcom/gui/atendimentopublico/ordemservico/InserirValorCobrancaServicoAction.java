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

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoCobrancaValor;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.imovel.ImovelPerfil;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inser��o de Valor de Cobran�a do Servi�o
 * 
 * @author Leonardo Regis
 * @date 29/09/2006
 */
public class InserirValorCobrancaServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Forward
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Form
		InserirValorCobrancaServicoActionForm cobrancaServicoActionForm = (InserirValorCobrancaServicoActionForm) actionForm;
		// Sess�o
		HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Registrando a opera��o
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// Filtra Tipo de Servi�o
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, cobrancaServicoActionForm.getTipoServico()));
		filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		// Recupera Tipo de Servi�o
		Collection colecaoServicoTipo = Fachada.getInstancia().pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
		if(colecaoServicoTipo == null || colecaoServicoTipo.isEmpty()){
			throw new ActionServletException("atencao.tipo_servico_inexistente");

		}

		ServicoTipo servicoTipo = new ServicoTipo();

		servicoTipo = (ServicoTipo) colecaoServicoTipo.iterator().next();

		// if (servicoTipo.getDebitoTipo() == null) {
		// throw new ActionServletException(
		// "atencao.valor_cobranca_tipo_servico_sem_debito", null,
		// servicoTipo.getDescricao());
		// }

		// Seta Objeto Servico Cobran�a Valor
		ServicoCobrancaValor servicoCobrancaValor = setServicoCobrancaValor(cobrancaServicoActionForm);

		// Registra opera��o
		servicoCobrancaValor.setOperacaoEfetuada(operacaoEfetuada);
		servicoCobrancaValor.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(servicoCobrancaValor);

		// [FS0001] Verificar Servi�o Geral D�bito.
		Fachada.getInstancia().inserirValorCobrancaServico(servicoCobrancaValor);

		// [FS008] Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Valor da Cobran�a do Servi�o " + servicoTipo.getDescricao() + " inserido com sucesso.",
						"Inserir outro Valor de Cobran�a do Servi�o", "exibirInserirValorCobrancaServicoAction.do?menu=sim");
		return retorno;
	}

	/**
	 * Preenche objeto com informa��es vindas da tela
	 * 
	 * @author Leonardo Regis
	 * @date 29/09/2006
	 * @param form
	 * @return servicoCobrancaValor
	 */
	private ServicoCobrancaValor setServicoCobrancaValor(InserirValorCobrancaServicoActionForm form){

		ServicoCobrancaValor servicoCobrancaValor = new ServicoCobrancaValor();
		// Tipo do Servi�o
		ServicoTipo tipoServico = new ServicoTipo();

		tipoServico.setId(new Integer(form.getTipoServico()));
		servicoCobrancaValor.setServicoTipo(tipoServico);
		// Perfil do Im�vel
		ImovelPerfil perfilImovel = null;
		if(form.getPerfilImovel() != null && (!form.getPerfilImovel().equals("") && !form.getPerfilImovel().equals("-1"))){
			perfilImovel = new ImovelPerfil();
			perfilImovel.setId(new Integer(form.getPerfilImovel()));
		}
		servicoCobrancaValor.setImovelPerfil(perfilImovel);
		// Indicador de Medido
		if(form.getIndicadorMedido().equals(ConstantesSistema.SIM.toString())){
			servicoCobrancaValor.setIndicadorMedido(ConstantesSistema.SIM);
		}else{
			servicoCobrancaValor.setIndicadorMedido(ConstantesSistema.NAO);
		}
		// Capacidade do Hidr�metro
		HidrometroCapacidade capacidadeHidrometro = null;
		if(form.getCapacidadeHidrometro() != null && !form.getCapacidadeHidrometro().equals("")){
			capacidadeHidrometro = new HidrometroCapacidade();
			capacidadeHidrometro.setId(new Integer(form.getCapacidadeHidrometro()));
		}

		// Valor do Servi�o
		servicoCobrancaValor.setHidrometroCapacidade(capacidadeHidrometro);
		String valorSemPontos = form.getValorServico().replace(".", "");
		servicoCobrancaValor.setValor(new BigDecimal(valorSemPontos.replace(",", ".")));
		servicoCobrancaValor.setUltimaAlteracao(new Date());

		return servicoCobrancaValor;

	}

}