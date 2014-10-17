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
 * Inserção de Valor de Cobrança do Serviço
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
		// Sessão
		HttpSession sessao = httpServletRequest.getSession(false);
		// Usuario logado no sistema
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Registrando a operação
		RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_INSERIR,
						new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

		Operacao operacao = new Operacao();
		operacao.setId(Operacao.OPERACAO_VALOR_COBRANCA_SERVICO_INSERIR);

		OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
		operacaoEfetuada.setOperacao(operacao);

		// Filtra Tipo de Serviço
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, cobrancaServicoActionForm.getTipoServico()));
		filtroServicoTipo.adicionarCaminhoParaCarregamentoEntidade("debitoTipo");
		// Recupera Tipo de Serviço
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

		// Seta Objeto Servico Cobrança Valor
		ServicoCobrancaValor servicoCobrancaValor = setServicoCobrancaValor(cobrancaServicoActionForm);

		// Registra operação
		servicoCobrancaValor.setOperacaoEfetuada(operacaoEfetuada);
		servicoCobrancaValor.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		registradorOperacao.registrarOperacao(servicoCobrancaValor);

		// [FS0001] Verificar Serviço Geral Débito.
		Fachada.getInstancia().inserirValorCobrancaServico(servicoCobrancaValor);

		// [FS008] Monta a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Valor da Cobrança do Serviço " + servicoTipo.getDescricao() + " inserido com sucesso.",
						"Inserir outro Valor de Cobrança do Serviço", "exibirInserirValorCobrancaServicoAction.do?menu=sim");
		return retorno;
	}

	/**
	 * Preenche objeto com informações vindas da tela
	 * 
	 * @author Leonardo Regis
	 * @date 29/09/2006
	 * @param form
	 * @return servicoCobrancaValor
	 */
	private ServicoCobrancaValor setServicoCobrancaValor(InserirValorCobrancaServicoActionForm form){

		ServicoCobrancaValor servicoCobrancaValor = new ServicoCobrancaValor();
		// Tipo do Serviço
		ServicoTipo tipoServico = new ServicoTipo();

		tipoServico.setId(new Integer(form.getTipoServico()));
		servicoCobrancaValor.setServicoTipo(tipoServico);
		// Perfil do Imóvel
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
		// Capacidade do Hidrômetro
		HidrometroCapacidade capacidadeHidrometro = null;
		if(form.getCapacidadeHidrometro() != null && !form.getCapacidadeHidrometro().equals("")){
			capacidadeHidrometro = new HidrometroCapacidade();
			capacidadeHidrometro.setId(new Integer(form.getCapacidadeHidrometro()));
		}

		// Valor do Serviço
		servicoCobrancaValor.setHidrometroCapacidade(capacidadeHidrometro);
		String valorSemPontos = form.getValorServico().replace(".", "");
		servicoCobrancaValor.setValor(new BigDecimal(valorSemPontos.replace(",", ".")));
		servicoCobrancaValor.setUltimaAlteracao(new Date());

		return servicoCobrancaValor;

	}

}