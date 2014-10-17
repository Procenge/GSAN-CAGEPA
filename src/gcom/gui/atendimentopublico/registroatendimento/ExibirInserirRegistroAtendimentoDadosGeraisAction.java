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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.*;
import gcom.atendimentopublico.registroatendimento.bean.DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os
 * par�metros para realiza��o da inser��o de um R.A (Aba n� 01 - Dados gerais)
 * 
 * @author Raphael Rossiter
 * @date 24/07/2006
 */
public class ExibirInserirRegistroAtendimentoDadosGeraisAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirRegistroAtendimentoDadosGerais");

		InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm = (InserirRegistroAtendimentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession();

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Verifica se o conte�do dos campos foram modificados.
		// Caso o usu�rio volte a tela n�o perde as informa��es
		String indicadorProcessoAdmJu = inserirRegistroAtendimentoActionForm.getIndicadorProcessoAdmJud();

		if(!Util.isVazioOuBranco(indicadorProcessoAdmJu) && indicadorProcessoAdmJu.equals(ConstantesSistema.SIM.toString())){
			sessao.setAttribute("indicadorProcessoAdmJud", ConstantesSistema.SIM.toString());
		}

		/**
		 * Verifica permiss�o especial para alterar o indicador para inserir processo
		 * administrativo/judiciario
		 */
		boolean temPermissaoAlterarProcessoAdmJud = fachada.verificarPermissaoIndicadorProcessoAdministrativoJuduciario(usuarioLogado);

		if(temPermissaoAlterarProcessoAdmJud){
			sessao.setAttribute("permissaoAlterarProcessoAdmJud", ConstantesSistema.SIM.toString());
		}else{
			sessao.setAttribute("permissaoAlterarProcessoAdmJud", ConstantesSistema.NAO.toString());
		}

		FiltroSolicitacaoTipoEspecificacao fste = new FiltroSolicitacaoTipoEspecificacao();
		fste.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, sistemaParametro
						.getSolicitacaoTipoEspecificacaoDefault()));
		fste.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO);
		fste.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);
		Collection<SolicitacaoTipoEspecificacao> colecaoEspecificacao = fachada.pesquisar(fste, SolicitacaoTipoEspecificacao.class
						.getName());

		Collection colecaoSolicitacaoTipoEspecificacao = null;

		if(sessao.getAttribute("passouPrimeiraVez") == null){
			sessao.setAttribute("raAReiterar", true);
			if(sistemaParametro.getSolicitacaoTipoEspecificacaoDefault() != null){
				inserirRegistroAtendimentoActionForm.setEspecificacao(sistemaParametro.getSolicitacaoTipoEspecificacaoDefault().toString());
				inserirRegistroAtendimentoActionForm.setTipoSolicitacao(colecaoEspecificacao.iterator().next().getSolicitacaoTipo().getId()
								.toString());
				sessao.setAttribute("tipoSolicitacao", colecaoEspecificacao.iterator().next().getSolicitacaoTipo().getId());
				sessao.setAttribute("especificacao", sistemaParametro.getSolicitacaoTipoEspecificacaoDefault());
				// Seta, na observa��o, a mensagem autom�tica caso exista no banco
				if(colecaoEspecificacao.iterator().next().getSolicitacaoTipoEspecificacaoMensagem() != null
								&& !colecaoEspecificacao.iterator().next().getSolicitacaoTipoEspecificacaoMensagem().getDescricao()
												.equals("")){
					inserirRegistroAtendimentoActionForm.setObservacao(colecaoEspecificacao.iterator().next()
									.getSolicitacaoTipoEspecificacaoMensagem().getDescricao());
				}
			}

		}else{
			sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacao");

			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao2 = new FiltroSolicitacaoTipoEspecificacao(
							FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
			filtroSolicitacaoTipoEspecificacao2.setConsultaSemLimites(true);
			filtroSolicitacaoTipoEspecificacao2.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroSolicitacaoTipoEspecificacao2.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO, Integer.valueOf(inserirRegistroAtendimentoActionForm
											.getTipoSolicitacao())));

			filtroSolicitacaoTipoEspecificacao2
							.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);
			filtroSolicitacaoTipoEspecificacao2.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);

			colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao2, SolicitacaoTipoEspecificacao.class
							.getName());

			sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

			if(sistemaParametro.getSolicitacaoTipoEspecificacaoReiteracao().toString().equalsIgnoreCase(
							inserirRegistroAtendimentoActionForm.getEspecificacao())){
				sessao.setAttribute("raAReiterar", true);
			}else{
				sessao.removeAttribute("raAReiterar");
			}

			// Verifica se o conte�do do campo observa��o foi modificado.
			// Caso o usu�rio volte a tela n�o perde as informa��es
			String definirDataPrevista = (String) httpServletRequest.getParameter("definirDataPrevista");

			if(definirDataPrevista != null && definirDataPrevista.equals("OK")){
				Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao2 = null;

				filtroSolicitacaoTipoEspecificacao2.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, Integer
								.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao())));
				filtroSolicitacaoTipoEspecificacao2
								.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

				colecaoSolicitacaoTipoEspecificacao2 = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao2,
								SolicitacaoTipoEspecificacao.class.getName());
				// Seta, na observa��o, a mensagem autom�tica caso exista no banco
				if(colecaoSolicitacaoTipoEspecificacao2 != null
								&& !colecaoSolicitacaoTipoEspecificacao2.isEmpty()
								&& colecaoSolicitacaoTipoEspecificacao2.iterator().next().getSolicitacaoTipoEspecificacaoMensagem() != null
								&& !colecaoSolicitacaoTipoEspecificacao2.iterator().next().getSolicitacaoTipoEspecificacaoMensagem()
												.getDescricao().equals("")){
					inserirRegistroAtendimentoActionForm.setObservacao(colecaoSolicitacaoTipoEspecificacao2.iterator().next()
									.getSolicitacaoTipoEspecificacaoMensagem().getDescricao());

				}else{

					inserirRegistroAtendimentoActionForm.setObservacao("");
				}
			}
		}

		if(inserirRegistroAtendimentoActionForm.getSequenceRA() == null){
			try{
				inserirRegistroAtendimentoActionForm.setSequenceRA(fachada.obterSequenceRA().toString());
			}catch(ControladorException e){
				throw new ActionServletException("");
			}
		}

		/*
		 * Tipo de Atendimento (exibir a tela com a op��o �on-line� selecionada e permitir que o
		 * usu�rio selecione entre �on-line� ou �manual�)
		 * [SB0001 � Habilita/Desabilita Dados do Momento do Atendimento]
		 */
		if(inserirRegistroAtendimentoActionForm.getTipo() == null || inserirRegistroAtendimentoActionForm.getTipo().equalsIgnoreCase("")){

			inserirRegistroAtendimentoActionForm.setTipo("1");

			/*
			 * Unidade de Atendimento (exibir a tela com a unidade associada ao usu�rio que estiver
			 * efetuando o registro de atendimento. (Tela
			 * inicial)
			 * Meio de Solicita��o (exibir na tela com o meio de solicita��o associado � unidade de
			 * atendimento)
			 */

			// Usu�rio logado no sistema
			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

			UnidadeOrganizacional unidadeOrganizacionalUsuario = fachada.obterUnidadeOrganizacionalAberturaRAAtivoUsuario(usuario
							.getLogin());

			if(unidadeOrganizacionalUsuario != null){
				inserirRegistroAtendimentoActionForm.setUnidade(unidadeOrganizacionalUsuario.getId().toString());
				inserirRegistroAtendimentoActionForm.setDescricaoUnidade(unidadeOrganizacionalUsuario.getDescricao());
				httpServletRequest.setAttribute("corUnidade", "");
				httpServletRequest.setAttribute("nomeCampo", "unidade");

				if(unidadeOrganizacionalUsuario.getMeioSolicitacao() != null){
					inserirRegistroAtendimentoActionForm.setMeioSolicitacao(unidadeOrganizacionalUsuario.getMeioSolicitacao().getId()
									.toString());
				}
			}

			// [SB0001 � Habilita/Desabilita Dados do Momento do Atendimento]
			habilitacaoDadosMomentoAtendimento(inserirRegistroAtendimentoActionForm, httpServletRequest);
		}

		String mudarTipo = httpServletRequest.getParameter("mudarTipo");

		if(mudarTipo != null && !mudarTipo.equalsIgnoreCase("")){

			// [SB0001 � Habilita/Desabilita Dados do Momento do Atendimento]
			habilitacaoDadosMomentoAtendimento(inserirRegistroAtendimentoActionForm, httpServletRequest);
		}

		/*
		 * Unidade de Atendimento (Permite que o usu�rio informe ou selecione outra)
		 * [FS0004] � Verificar exist�ncia da unidade de atendimento
		 * [FS0033] � Verificar autoriza��o da unidade de atendimento para abertura de registro de
		 * atendimento
		 */
		String pesquisarUnidade = httpServletRequest.getParameter("pesquisarUnidade");

		if(pesquisarUnidade != null && !pesquisarUnidade.equalsIgnoreCase("")){
			UnidadeOrganizacional unidadeOrganizacionalSelecionada = fachada.verificarAutorizacaoUnidadeAberturaRA(Integer
							.valueOf(inserirRegistroAtendimentoActionForm.getUnidade()), false);
			if(unidadeOrganizacionalSelecionada != null){
				inserirRegistroAtendimentoActionForm.setUnidade(unidadeOrganizacionalSelecionada.getId().toString());
				inserirRegistroAtendimentoActionForm.setDescricaoUnidade(unidadeOrganizacionalSelecionada.getDescricao());

				if(unidadeOrganizacionalSelecionada.getMeioSolicitacao() != null){
					inserirRegistroAtendimentoActionForm.setMeioSolicitacao(unidadeOrganizacionalSelecionada.getMeioSolicitacao().getId()
									.toString());
				}
				httpServletRequest.setAttribute("nomeCampo", "meioSolicitacao");

			}else{
				inserirRegistroAtendimentoActionForm.setUnidade("");
				inserirRegistroAtendimentoActionForm.setDescricaoUnidade("Unidade Inexistente");
				httpServletRequest.setAttribute("corUnidade", "exception");
				httpServletRequest.setAttribute("nomeCampo", "unidade");
			}

		}

		/*
		 * Meio de Solicita��o - Carregando a cole��o que ir� ficar dispon�vel para escolha do
		 * usu�rio
		 * [FS0003] � Verificar exist�ncia de dados
		 */
		Collection colecaoMeioSolicitacao = (Collection) sessao.getAttribute("colecaoMeioSolicitacao");

		if(colecaoMeioSolicitacao == null){

			FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao(FiltroMeioSolicitacao.DESCRICAO);
			filtroMeioSolicitacao.setConsultaSemLimites(true);
			filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			colecaoMeioSolicitacao = fachada.pesquisar(filtroMeioSolicitacao, MeioSolicitacao.class.getName());
			if(colecaoMeioSolicitacao == null || colecaoMeioSolicitacao.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "MEIO_SOLICITACAO");
			}else{
				sessao.setAttribute("colecaoMeioSolicitacao", colecaoMeioSolicitacao);
			}
		}

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = null;

		if(sessao.getAttribute("passouPrimeiraVez") == null){
			/*
			 * Tipo de Solicita��o - Carregando a cole��o que ir� ficar dispon�vel para escolha do
			 * usu�rio
			 * [FS0003] � Verificar exist�ncia de dados
			 */
			FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo();
			filtroSolicitacaoTipo.setConsultaSemLimites(true);
			filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroSolicitacaoTipo.setCampoOrderBy(FiltroSolicitacaoTipo.DESCRICAO);

			Collection colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

			if(colecaoSolicitacaoTipo == null || colecaoSolicitacaoTipo.isEmpty()){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "SOLICITACAO_TIPO");
			}else{
				sessao.setAttribute("colecaoSolicitacaoTipo", colecaoSolicitacaoTipo);
			}

			/*
			 * Especifica��o - Carregando a cole��o que ir� ficar dispon�vel para escolha do usu�rio
			 * [FS0003] � Verificar exist�ncia de dados
			 */
			filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
			filtroSolicitacaoTipoEspecificacao.setConsultaSemLimites(true);
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			if(colecaoEspecificacao != null && !colecaoEspecificacao.isEmpty()){
				filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, colecaoEspecificacao.iterator().next()
												.getSolicitacaoTipo().getId()));

			}

			filtroSolicitacaoTipoEspecificacao
							.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);
			filtroSolicitacaoTipoEspecificacao.setCampoOrderBy(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);

			colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class
							.getName());

			if(colecaoSolicitacaoTipoEspecificacao == null || colecaoSolicitacaoTipoEspecificacao.isEmpty()){
				sessao.removeAttribute("colecaoSolicitacaoTipoEspecificacao");
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "SOLICITACAO_TIPO_ESPECIFICACAO");
			}else{
				sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);
			}
		}

		sessao.setAttribute("passouPrimeiraVez", true);

		/*
		 * Data Prevista - (exibir a data prevista calculada no SB0003 e n�o permitir altera��o).
		 * [SB0003 � Define Data Prevista e Unidade Destino da Especifica��o] [FS0018] � Verificar
		 * exist�ncia da unidade centralizadora
		 */
		if(inserirRegistroAtendimentoActionForm.getDataAtendimento() != null
						&& !inserirRegistroAtendimentoActionForm.getDataAtendimento().equalsIgnoreCase("")
						&& inserirRegistroAtendimentoActionForm.getEspecificacao() != null
						&& !inserirRegistroAtendimentoActionForm.getEspecificacao().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			this.definirDataPrevistaUnidadeDestinoEspecificacao(inserirRegistroAtendimentoActionForm, fachada, sessao);

			// Verifica se o cliente � obrigat�rio
			if(fachada.clienteObrigatorioInserirRegistroAtendimento(Integer
							.valueOf(inserirRegistroAtendimentoActionForm.getEspecificacao()))){

				inserirRegistroAtendimentoActionForm.setClienteObrigatorio("1");
			}else{
				inserirRegistroAtendimentoActionForm.setClienteObrigatorio("2");
			}

			httpServletRequest.setAttribute("nomeCampo", "observacao");
		}

		/*
		 * Caso o Tempo de Espera Final esteja desabilitado e o Tempo de Espera Inicial para
		 * Atendimento esteja preenchido, atribuir o valor
		 * correspondente � hora corrente e n�o permitir altera��o
		 */
		String tempoEsperaFinalDesabilitado = httpServletRequest.getParameter("tempoEsperaFinalDesabilitado");

		if(tempoEsperaFinalDesabilitado != null && !tempoEsperaFinalDesabilitado.equalsIgnoreCase("")){
			this.atribuirHoraCorrenteTempoEsperaFinal(inserirRegistroAtendimentoActionForm);
			httpServletRequest.setAttribute("nomeCampo", "unidade");
		}

		/*
		 * Para os casos que forem inserir RA para falta de �gua generalizada, o tipo e a
		 * especifica��o ser�o pr�-determinados e n�o poder�o ser
		 * altarados.
		 */
		if(httpServletRequest.getParameter("raFaltaAguaGeneralizada") != null){

			sessao.setAttribute("generalizada", "OK");

			SolicitacaoTipoEspecificacao especificacao = fachada.pesquisarTipoEspecificacaoFaltaAguaGeneralizada();
			filtroSolicitacaoTipoEspecificacao = null;
			filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao(FiltroSolicitacaoTipoEspecificacao.DESCRICAO);
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, especificacao.getSolicitacaoTipo().getId()));
			filtroSolicitacaoTipoEspecificacao.setConsultaSemLimites(true);
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroSolicitacaoTipoEspecificacao
							.adicionarCaminhoParaCarregamentoEntidade(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ESPECIFICACAO_MENSAGEM);

			colecaoSolicitacaoTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class
							.getName());

			sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

			inserirRegistroAtendimentoActionForm.setTipoSolicitacao(especificacao.getSolicitacaoTipo().getId().toString());
			inserirRegistroAtendimentoActionForm.setEspecificacao(especificacao.getId().toString());

			if(inserirRegistroAtendimentoActionForm.getDataAtendimento() != null
							&& !inserirRegistroAtendimentoActionForm.getDataAtendimento().equalsIgnoreCase("")){
				this.definirDataPrevistaUnidadeDestinoEspecificacao(inserirRegistroAtendimentoActionForm, fachada, sessao);
			}
		}

		try{
			Collection colecaoMotivoAtendimentoIncompleto = fachada.listarMotivoAtendimentoIncompleto();
			sessao.setAttribute("colecaoMotivoAtendimentoIncompleto", colecaoMotivoAtendimentoIncompleto);
		}catch(ControladorException e){
			throw new ActionServletException("", e);
		}

		if(httpServletRequest.getParameter("veioDeInserirRA") != null && httpServletRequest.getParameter("veioDeInserirRA").equals("true")){
			FiltroRegistroAtendimento filtroRA = new FiltroRegistroAtendimento();
			filtroRA.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
			filtroRA.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, inserirRegistroAtendimentoActionForm
							.getIdRaReiteracao().toString()));

			Collection<RegistroAtendimento> colecaoRA = fachada.pesquisar(filtroRA, RegistroAtendimento.class.getName());

			if(colecaoRA == null || colecaoRA.isEmpty()){
				inserirRegistroAtendimentoActionForm.setDescricaoRA("RA - Registro de Atendimento inexistente.");
			}else{
				inserirRegistroAtendimentoActionForm.setDescricaoRA(colecaoRA.iterator().next().getSolicitacaoTipoEspecificacao()
								.getDescricao());
			}
		}

		// se veio das outras abas
		validarLocalidadeMuniciopio(inserirRegistroAtendimentoActionForm, fachada, httpServletRequest);

		controlarFormularioSelecionarContaRevisao(httpServletRequest, sessao, inserirRegistroAtendimentoActionForm);

		return retorno;
	}

	/**
	 * @author isilva
	 * @param inserirRegistroAtendimentoActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	@SuppressWarnings("unchecked")
	private void validarLocalidadeMuniciopio(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
			if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdLocalidade())
							&& !Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdMunicipio())){

				Integer idMunicipio = Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdMunicipio());
				Integer idLocalidade = Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdLocalidade());

				FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection<Municipio> colecaoMunicipio = (ArrayList<Municipio>) fachada.pesquisar(filtroMunicipio, Municipio.class
								.getName());

				if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Munic�pio");
				}

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection<Localidade> colecaoLocalidade = (ArrayList<Localidade>) fachada.pesquisar(filtroLocalidade, Localidade.class
								.getName());

				if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
				}

				if(!fachada.existeVinculoLocalidadeMunicipio(Integer.valueOf(idLocalidade), Integer.valueOf(idMunicipio))){
					throw new ActionServletException("atencao.localidade.nao.esta.municipio.informado");
				}
			}
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Habilitar ou desabilitar os campos Tempo de Espera para Atendimento, Data do Atendimento e
	 * Hora do Atendimento
	 * [SF0001] Habilita/Desabilita Dados do Momento do Atendimento
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 * @param InserirRegistroAtendimentoActionForm
	 * @return void
	 */
	private void habilitacaoDadosMomentoAtendimento(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm,
					HttpServletRequest httpServletRequest){

		// On-line
		if(inserirRegistroAtendimentoActionForm.getTipo().equalsIgnoreCase("1")){
			Date dataCorrente = new Date();

			inserirRegistroAtendimentoActionForm.setNumeroAtendimentoManual("");
			inserirRegistroAtendimentoActionForm.setDataAtendimento(Util.formatarData(dataCorrente));
			inserirRegistroAtendimentoActionForm.setHora(Util.formatarHoraSemSegundos(dataCorrente));

			httpServletRequest.setAttribute("nomeCampo", "tempoEsperaInicial");
		}
		// Manual
		else{
			inserirRegistroAtendimentoActionForm.setDataAtendimento("");
			inserirRegistroAtendimentoActionForm.setHora("");
			inserirRegistroAtendimentoActionForm.setTempoEsperaFinal("");
			// inserirRegistroAtendimentoActionForm.setDataPrevista("");

			httpServletRequest.setAttribute("nomeCampo", "numeroAtendimentoManual");
		}
	}

	/**
	 * [UC0366] Inserir Registro de Atendimento
	 * Atribui o valor correspondente � hora corrente
	 * 
	 * @author Raphael Rossiter
	 * @date 27/07/2006
	 * @param InserirRegistroAtendimentoActionForm
	 * @return void
	 */
	private void atribuirHoraCorrenteTempoEsperaFinal(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm){

		Date dataCorrente = new Date();

		inserirRegistroAtendimentoActionForm.setTempoEsperaFinal(Util.formatarHoraSemSegundos(dataCorrente));
	}

	private void definirDataPrevistaUnidadeDestinoEspecificacao(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm,
					Fachada fachada, HttpSession sessao){

		Date dataAtendimento = Util.converteStringParaDateHora(inserirRegistroAtendimentoActionForm.getDataAtendimento() + " "
						+ inserirRegistroAtendimentoActionForm.getHora() + ":00");

		if(dataAtendimento == null){
			throw new ActionServletException("atencao.data.hora.invalido");
		}

		String idEspecificacao = inserirRegistroAtendimentoActionForm.getEspecificacao();

		DefinirDataPrevistaUnidadeDestinoEspecificacaoHelper dataPrevistaUnidadeDestino = fachada
						.definirDataPrevistaUnidadeDestinoEspecificacao(dataAtendimento, Integer.valueOf(idEspecificacao));

		if(dataPrevistaUnidadeDestino.getDataPrevista() != null){
			Date dataPrev = dataPrevistaUnidadeDestino.getDataPrevista();
			SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute(SistemaParametro.SISTEMA_PARAMETRO);
			if(sistemaParametro.getParmId().shortValue() == SistemaParametro.INDICADOR_EMPRESA_ADA){
				inserirRegistroAtendimentoActionForm.setDataPrevista(Util.formatarData(dataPrev) + " "
								+ Util.formatarHoraSemSegundos(dataPrev));
			}else if(sistemaParametro.getParmId().shortValue() == SistemaParametro.INDICADOR_EMPRESA_DESO){
				inserirRegistroAtendimentoActionForm.setDataPrevista(Util.formatarData(dataPrev));
			}
		}

		if(dataPrevistaUnidadeDestino.getUnidadeOrganizacional() != null){
			inserirRegistroAtendimentoActionForm.setIdUnidadeDestino(dataPrevistaUnidadeDestino.getUnidadeOrganizacional().getId()
							.toString());
			inserirRegistroAtendimentoActionForm.setDescricaoUnidadeDestino(dataPrevistaUnidadeDestino.getUnidadeOrganizacional()
							.getDescricao());
		}

		inserirRegistroAtendimentoActionForm.setIndFaltaAgua(dataPrevistaUnidadeDestino.getIndFaltaAgua());

		inserirRegistroAtendimentoActionForm.setIndMatricula(dataPrevistaUnidadeDestino.getIndMatricula());

		inserirRegistroAtendimentoActionForm.setImovelObrigatorio(dataPrevistaUnidadeDestino.getImovelObrigatorio());

		inserirRegistroAtendimentoActionForm.setPavimentoRuaObrigatorio(dataPrevistaUnidadeDestino.getPavimentoRuaObrigatorio());

		inserirRegistroAtendimentoActionForm.setPavimentoCalcadaObrigatorio(dataPrevistaUnidadeDestino.getPavimentoCalcadaObrigatorio());

		// Identificar tipo de gera��o da ordem de servi�o (AUTOM�TICA, OPCIONAL ou N�O GERAR)
		Integer idEspecificacaoInt = Integer.valueOf(idEspecificacao);

		if(fachada.gerarOrdemServicoAutomatica(idEspecificacaoInt)){
			Collection<Integer> colecaoIdServicoTipo = fachada.consultarIdServicoTipoGeracaoAutomaticaPorEspecificacao(idEspecificacaoInt);

			sessao.setAttribute("servicoTipo", colecaoIdServicoTipo);
		}else{
			sessao.removeAttribute("servicoTipo");
		}

	}

	/**
	 * @author isilva
	 * @param httpServletRequest
	 * @param sessao
	 * @param inserirRegistroAtendimentoActionForm
	 */
	private void controlarFormularioSelecionarContaRevisao(HttpServletRequest httpServletRequest, HttpSession sessao,
					InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){

			SelecionarContaRevisaoActionForm selecionarContaRevisaoActionForm = (SelecionarContaRevisaoActionForm) sessao
							.getAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);

			boolean removeFormulario = false;

			if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm)){
				if(Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdImovel())){
					removeFormulario = true;
				}else{
					if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getCodigoImovel())){
						if(!inserirRegistroAtendimentoActionForm.getIdImovel().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getCodigoImovel())){
							removeFormulario = true;
						}
					}else{
						removeFormulario = true;
					}
				}

				if(Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getEspecificacao())){
					removeFormulario = true;
				}else{
					if(!Util.isVazioOuBranco(selecionarContaRevisaoActionForm.getEspecificacao())){
						if(!inserirRegistroAtendimentoActionForm.getEspecificacao().equalsIgnoreCase(
										selecionarContaRevisaoActionForm.getEspecificacao())){
							removeFormulario = true;
						}
					}else{
						removeFormulario = true;
					}
				}
			}

			if(removeFormulario){
				sessao.removeAttribute(SelecionarContaRevisaoActionForm.NOME_SESSAO);
			}

		}
	}

}