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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.cadastro.sistemaparametro;

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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
 * @date 10/01/2007
 * @author eduardo Henrique
 * @date 05/06/2008
 */
public class ExibirInformarParametrosSistemaAtendimentoPublicoSegurancaAction
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

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("exibirInformarParametrosSistemaAtendimentoPublicoSeguranca");

		HttpSession sessao = httpServletRequest.getSession(false);

		InformarSistemaParametrosActionForm form = (InformarSistemaParametrosActionForm) actionForm;

		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");

		if(sistemaParametro.getIndicadorSugestaoTramite() != null){

			form.setIndicadorSugestaoTramite("" + sistemaParametro.getIndicadorSugestaoTramite());
		}

		if(sistemaParametro.getDiasReativacao() != null){
			form.setDiasMaximoReativarRA("" + sistemaParametro.getDiasReativacao());
		}

		if(sistemaParametro.getDiasMaximoAlterarOS() != null){
			form.setDiasMaximoAlterarOS("" + sistemaParametro.getDiasMaximoAlterarOS());
		}

		if(sistemaParametro.getUltimoRAManual() != null){
			form.setUltimoIDGeracaoRA("" + sistemaParametro.getUltimoRAManual());
		}

		if(sistemaParametro.getNumeroDiasExpiracaoAcesso() != null){
			form.setDiasMaximoExpirarAcesso("" + sistemaParametro.getNumeroDiasExpiracaoAcesso());
		}

		if(sistemaParametro.getNumeroDiasMensagemExpiracao() != null){
			form.setDiasMensagemExpiracaoSenha("" + sistemaParametro.getNumeroDiasMensagemExpiracao());
		}

		if(sistemaParametro.getNumeroMaximoLoginFalho() != null){
			form.setNumeroMaximoTentativasAcesso("" + sistemaParametro.getNumeroMaximoLoginFalho());
		}

		if(sistemaParametro.getNumeroMaximoFavorito() != null){
			form.setNumeroMaximoFavoritosMenu("" + sistemaParametro.getNumeroMaximoFavorito());
		}

		// -------Parte que trata do c�digo quando o usu�rio tecla enter na pesquisa da Unidade
		// Organiz.
		Fachada fachada = Fachada.getInstancia();

		String idDigitadoEnterUnidadeEmpresa = form.getIdUnidadeOrganizacionalPresidencia();
		// Verifica se o c�digo da Unidade Empresa foi digitado

		FiltroUnidadeOrganizacional filtroUnidadeEmpresa = new FiltroUnidadeOrganizacional();

		if(idDigitadoEnterUnidadeEmpresa != null && !idDigitadoEnterUnidadeEmpresa.trim().equals("")){

			filtroUnidadeEmpresa.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, idDigitadoEnterUnidadeEmpresa));

			Collection<UnidadeOrganizacional> unidadeEmpresaEncontrada = fachada.pesquisar(filtroUnidadeEmpresa,
							UnidadeOrganizacional.class.getName());

			if(unidadeEmpresaEncontrada != null && !unidadeEmpresaEncontrada.isEmpty()){
				// a unidade de Unidade Empresa foi encontrado
				form.setIdUnidadeOrganizacionalPresidencia("" + ((UnidadeOrganizacional) ((List) unidadeEmpresaEncontrada).get(0)).getId());
				form.setNomeUnidadeOrganizacionalPresidencia(((UnidadeOrganizacional) ((List) unidadeEmpresaEncontrada).get(0))
								.getDescricao());
				httpServletRequest.setAttribute("idUnidadeEmpresaNaoEncontrada", "true");
				httpServletRequest.setAttribute("nomeCampo", "nomeUnidadeEmpresaNaoEncontrada");

			}else{

				form.setIdUnidadeOrganizacionalPresidencia("");
				httpServletRequest.setAttribute("idUnidadeEmpresaNaoEncontrada", "exception");
				form.setNomeUnidadeOrganizacionalPresidencia("UNIDADE EMPRESA INEXISTENTE");
				httpServletRequest.setAttribute("nomeCampo", "idUnidadeEmpresa");

			}

		}else{
			if(sistemaParametro.getUnidadeOrganizacionalIdPresidencia() != null){
				form.setIdUnidadeOrganizacionalPresidencia(sistemaParametro.getUnidadeOrganizacionalIdPresidencia().getId().toString());

				filtroUnidadeEmpresa.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, sistemaParametro
								.getUnidadeOrganizacionalIdPresidencia().getId()));

				Collection<UnidadeOrganizacional> unidadeEmpresaEncontrada = fachada.pesquisar(filtroUnidadeEmpresa,
								UnidadeOrganizacional.class.getName());
				if(unidadeEmpresaEncontrada != null && !unidadeEmpresaEncontrada.isEmpty()){

					form.setNomeUnidadeOrganizacionalPresidencia(((UnidadeOrganizacional) ((List) unidadeEmpresaEncontrada).get(0))
									.getDescricao());
				}
			}

		}

		/*
		 * Tipo de Solicita��o Padr�o:
		 * Especifica��o Padr�o:* -> colecaoEspecificacaoPadrao
		 * Tipo de Solicita��o Reitera��o:*
		 * Especifica��o Reitera��o:* -> colecaoEspecificacaoReiteracao
		 */

		/*
		 * Na tela informar par�metros do sistema, na aba "Atendimento Seguran�a"
		 * os campos "Especifica��o Padr�o:" e "Especifica��o Reitera��o:" precisam trazer somente
		 * os "Tipos de Solicita��o Especifica��o" referentes ao "SolicitacaoTipo" do tipo de cada
		 * um respectivamente.
		 */

		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacaoDefault = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoEspecificacaoDefault.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID,
						sistemaParametro.getSolicitacaoTipoEspecificacaoDefault()));
		filtroSolicitacaoTipoEspecificacaoDefault.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection solicitacoesTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoDefault,
						SolicitacaoTipoEspecificacao.class.getName());
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacaoDefault = new SolicitacaoTipoEspecificacao();

		if(solicitacoesTipoEspecificacao != null && !solicitacoesTipoEspecificacao.isEmpty()){

			for(Iterator iterator = solicitacoesTipoEspecificacao.iterator(); iterator.hasNext();){
				solicitacaoTipoEspecificacaoDefault = (SolicitacaoTipoEspecificacao) iterator.next();
			}
			filtroSolicitacaoTipoEspecificacaoDefault.limparListaParametros();

			if(solicitacaoTipoEspecificacaoDefault != null && solicitacaoTipoEspecificacaoDefault.getSolicitacaoTipo() != null){
				filtroSolicitacaoTipoEspecificacaoDefault.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID, solicitacaoTipoEspecificacaoDefault
												.getSolicitacaoTipo().getId()));

			}
			filtroSolicitacaoTipoEspecificacaoDefault.adicionarParametro(new ParametroSimples(
							FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			solicitacoesTipoEspecificacao = fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoDefault,
							SolicitacaoTipoEspecificacao.class.getName());

		}

		/*
		 * encontrei a SolicitacaoTipoEspecificacao que est� setada no banco, agora, atrav�s dela,
		 * vou encontrar as outras do mesmo tipo.
		 */


		/*
		 * Encontrar agora as SolicitacaoTipoEspecificacao que tem o mesmo SolicitacaoTipo da
		 * SolicitacaoTipoEspecificacaoReiteracao
		 * setada no banco.
		 */
		FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoReiteracao = new FiltroSolicitacaoTipoEspecificacao();
		filtroSolicitacaoTipoReiteracao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.ID, sistemaParametro
						.getSolicitacaoTipoEspecificacaoReiteracao()));
		filtroSolicitacaoTipoReiteracao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection solicitacoesTipoReiteracao = fachada.pesquisar(filtroSolicitacaoTipoReiteracao, SolicitacaoTipoEspecificacao.class
						.getName());
		SolicitacaoTipoEspecificacao solicitacaoTipoReiteracao = new SolicitacaoTipoEspecificacao();
		for(Iterator iterator = solicitacoesTipoReiteracao.iterator(); iterator.hasNext();){
			solicitacaoTipoReiteracao = (SolicitacaoTipoEspecificacao) iterator.next();
		}
		filtroSolicitacaoTipoReiteracao.limparListaParametros();

		if(solicitacaoTipoReiteracao.getSolicitacaoTipo() != null){
			filtroSolicitacaoTipoReiteracao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.SOLICITACAO_TIPO_ID,
							solicitacaoTipoReiteracao.getSolicitacaoTipo().getId()));
			filtroSolicitacaoTipoReiteracao.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			solicitacoesTipoReiteracao = fachada.pesquisar(filtroSolicitacaoTipoReiteracao, SolicitacaoTipoEspecificacao.class.getName());
		}

		// carrega as combos de Tipo de Solicita��o e Especifica��o

		// FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new
		// FiltroSolicitacaoTipoEspecificacao();
		// filtroSolicitacaoTipoEspecificacao.adicionarParametro(new
		// ParametroSimples(FiltroSolicitacaoTipoEspecificacao.INDICADOR_USO,
		// ConstantesSistema.INDICADOR_USO_ATIVO));
		// Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao =
		// fachada.pesquisar(filtroSolicitacaoTipoEspecificacao,
		// SolicitacaoTipoEspecificacao.class.getName());

		FiltroSolicitacaoTipo filtroSolicitacaoTipo = new FiltroSolicitacaoTipo(FiltroSolicitacaoTipo.DESCRICAO);
		filtroSolicitacaoTipo.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection<SolicitacaoTipo> colecaoSolicitacaoTipo = fachada.pesquisar(filtroSolicitacaoTipo, SolicitacaoTipo.class.getName());

		// SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
		// solicitacaoTipo.setDescricao("");
		// solicitacaoTipo.setId(null);
		// colecaoSolicitacaoTipo.add(solicitacaoTipo);

		// BeanComparator comparador = new BeanComparator("descricao");
		// Collections.sort((List) colecaoSolicitacaoTipo, comparador);

		sessao.setAttribute("colecaoSolicitacaoTipo", colecaoSolicitacaoTipo);
		sessao.setAttribute("colecaoEspecificacaoPadrao", solicitacoesTipoEspecificacao);
		sessao.setAttribute("colecaoEspecificacaoReiteracao", solicitacoesTipoReiteracao);
		// sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao",
		// colecaoSolicitacaoTipoEspecificacao);



		return retorno;

	}
}
