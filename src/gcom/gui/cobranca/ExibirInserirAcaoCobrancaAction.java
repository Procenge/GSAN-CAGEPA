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

package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.spcserasa.FiltroNegativador;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre- processamento para inserir o criterio da cobrança
 * 
 * @author Sávio Luiz
 * @date 17/04/2006
 * @author eduardo henrique
 * @date 01/12/2008 - Retirada da validação de existência de Ação de Cobrança
 */
public class ExibirInserirAcaoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirAcaoCobranca");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		AcaoCobrancaActionForm acaoCobrancaActionForm = (AcaoCobrancaActionForm) actionForm;
		if(httpServletRequest.getParameter("menu") != null && !httpServletRequest.getParameter("menu").equals("")){
			acaoCobrancaActionForm.setDescricaoAcao("");
			acaoCobrancaActionForm.setDescricaoCobrancaCriterio("");
			acaoCobrancaActionForm.setDescricaoServicoTipo("");
			acaoCobrancaActionForm.setIcAcaoObrigatoria("");
			acaoCobrancaActionForm.setIcAcrescimosImpontualidade("");
			acaoCobrancaActionForm.setIcCompoeCronograma("");
			acaoCobrancaActionForm.setIcDebitosACobrar("");
			acaoCobrancaActionForm.setIcEmitirBoletimCadastro("");
			acaoCobrancaActionForm.setIcGeraTaxa("");
			acaoCobrancaActionForm.setIcImoveisSemDebitos("");
			acaoCobrancaActionForm.setIcRepetidaCiclo("");
			acaoCobrancaActionForm.setIcSuspensaoAbastecimento("");
			acaoCobrancaActionForm.setIdAcaoPredecessora("");
			acaoCobrancaActionForm.setIdCobrancaCriterio("");
			acaoCobrancaActionForm.setIdServicoTipo("");
			acaoCobrancaActionForm.setIdSituacaoCobranca("");
			acaoCobrancaActionForm.setIdSituacaoLigacaoAgua("");
			acaoCobrancaActionForm.setIdSituacaoLigacaoEsgoto("");
			acaoCobrancaActionForm.setIdTipoDocumentoGerado("");
			acaoCobrancaActionForm.setNumeroDiasEntreAcoes("");
			acaoCobrancaActionForm.setNumeroDiasValidade("");
			acaoCobrancaActionForm.setQtdDiasRealizacao("");
			acaoCobrancaActionForm.setOrdemCronograma("");
			acaoCobrancaActionForm.setIndicadorConsideraCreditoRealizar("");
			acaoCobrancaActionForm.setIndicadorConsideraGuiaPagamento("");
			acaoCobrancaActionForm.setIdPrimeiraResolucaoDiretoria("");
			acaoCobrancaActionForm.setIdSegundaResolucaoDiretoria("");
			acaoCobrancaActionForm.setIdTerceiraResolucaoDiretoria("");
			acaoCobrancaActionForm.setIcEmpresaObrigatoria("");
			// faz as pesquisas obrigatórias
			pesquisasObrigatorias(fachada, sessao);

		}

		if(acaoCobrancaActionForm.getIdTipoDocumentoGerado() == null || acaoCobrancaActionForm.getIdTipoDocumentoGerado().equals("")
						|| !acaoCobrancaActionForm.getIdTipoDocumentoGerado().equals(DocumentoTipo.CARTA_OPCAO_PARCELAMENTO.toString())){

			acaoCobrancaActionForm.setIndicadorConsideraCreditoRealizar(null);
			acaoCobrancaActionForm.setIndicadorConsideraGuiaPagamento(null);
			acaoCobrancaActionForm.setIdPrimeiraResolucaoDiretoria(null);
			acaoCobrancaActionForm.setIdSegundaResolucaoDiretoria(null);
			acaoCobrancaActionForm.setIdTerceiraResolucaoDiretoria(null);
			sessao.removeAttribute("colecaoResolucaoDiretoria");
		}else{

			// [FS0009] - Verificar se o usuário possui autorização para utilizar a RD
			boolean temPermissaoResolucaoDiretoria = fachada.verificarPermissaoEspecial(
							PermissaoEspecial.USAR_PLANO_PAI_PARA_ORGAO_PUBLICO, ((Usuario) sessao.getAttribute("usuarioLogado")));
			Collection<ResolucaoDiretoria> colecaoResolucaoDiretoria = null;

			if(temPermissaoResolucaoDiretoria){

				colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual();

			}else{

				colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaMaiorDataVigenciaInicioComEntrada();
			}

			// [FS0001] - Verificar existência de dados
			if(colecaoResolucaoDiretoria != null && !colecaoResolucaoDiretoria.isEmpty()){
				sessao.setAttribute("colecaoResolucaoDiretoria", colecaoResolucaoDiretoria);
			}else{
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Resolução Diretoria");
			}
		}

		FiltroNegativador filtroNegativador = new FiltroNegativador();
		filtroNegativador.adicionarCaminhoParaCarregamentoEntidade(FiltroNegativador.CLIENTE);
		filtroNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.INDICADOR_USO, ConstantesSistema.SIM));
		filtroNegativador.adicionarParametro(new ParametroNaoNulo(FiltroNegativador.NEGATIVADOR_CLIENTE));

		Collection colecaoNegativador = getFachada().pesquisar(filtroNegativador, Negativador.class.getName());

		sessao.setAttribute("colecaoNegativador", colecaoNegativador);

		// pesquisa os dados do enter
		pesquisarEnter(acaoCobrancaActionForm, httpServletRequest, fachada);

		return retorno;
	}

	private void pesquisarEnter(AcaoCobrancaActionForm acaoCobrancaActionForm, HttpServletRequest httpServletRequest, Fachada fachada){

		// pesquisa enter de critério de cobrança
		if(acaoCobrancaActionForm.getIdCobrancaCriterio() != null
						&& !acaoCobrancaActionForm.getIdCobrancaCriterio().equals("")
						&& (acaoCobrancaActionForm.getDescricaoCobrancaCriterio() == null || acaoCobrancaActionForm
										.getDescricaoCobrancaCriterio().equals(""))){

			FiltroCobrancaCriterio filtroCobrancaCriterio = new FiltroCobrancaCriterio();
			try{
				filtroCobrancaCriterio.adicionarParametro(new ParametroSimples(FiltroCobrancaCriterio.ID, Integer
								.valueOf(acaoCobrancaActionForm.getIdCobrancaCriterio())));
			}catch(NumberFormatException ex){
				throw new ActionServletException("atencao.campo_texto.numero_obrigatorio", null, "Critério de Cobrança");
			}
			filtroCobrancaCriterio.setCampoOrderBy(FiltroCobrancaCriterio.DESCRICAO_COBRANCA_CRITERIO);
			Collection colecaoCobrancaCriterio = fachada.pesquisar(filtroCobrancaCriterio, CobrancaCriterio.class.getName());

			if(colecaoCobrancaCriterio != null && !colecaoCobrancaCriterio.isEmpty()){
				CobrancaCriterio cobrancaCriterio = (CobrancaCriterio) Util.retonarObjetoDeColecao(colecaoCobrancaCriterio);
				acaoCobrancaActionForm.setDescricaoCobrancaCriterio(cobrancaCriterio.getDescricaoCobrancaCriterio());
			}else{
				acaoCobrancaActionForm.setIdCobrancaCriterio("");
				acaoCobrancaActionForm.setDescricaoCobrancaCriterio("COBRANÇA CRITÉRIO INEXISTENTE");
			}

		}

		// pesquisa enter de tipo de serviço
		if(acaoCobrancaActionForm.getIdServicoTipo() != null
						&& !acaoCobrancaActionForm.getIdServicoTipo().equals("")
						&& (acaoCobrancaActionForm.getDescricaoServicoTipo() == null || acaoCobrancaActionForm.getDescricaoServicoTipo()
										.equals(""))){

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			try{
				filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, Integer.valueOf(acaoCobrancaActionForm
								.getIdServicoTipo())));
			}catch(NumberFormatException ex){
				throw new ActionServletException("atencao.campo_texto.numero_obrigatorio", null, "Serviço Tipo");
			}
			filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);
			Collection colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
				ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);
				acaoCobrancaActionForm.setDescricaoServicoTipo(servicoTipo.getDescricao());
			}else{
				acaoCobrancaActionForm.setIdServicoTipo("");
				acaoCobrancaActionForm.setDescricaoServicoTipo("TIPO DE SERVIÇO INEXISTENTE");
			}

		}
	}

	private void pesquisasObrigatorias(Fachada fachada, HttpSession sessao){

		// pesquisa as ações predecessoras
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoAcaoPredecessora = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());
		sessao.setAttribute("colecaoAcaoPredecessora", colecaoAcaoPredecessora);

		// pesquisa os tipos de documentos
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		filtroDocumentoTipo.setCampoOrderBy(FiltroDocumentoTipo.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());
		if(colecaoDocumentoTipo == null || colecaoDocumentoTipo.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Documento Tipo");
		}else{
			sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);
		}

		// pesquisa as situações de ligações de agua
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
		filtroDocumentoTipo.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
		if(colecaoLigacaoAguaSituacao == null || colecaoLigacaoAguaSituacao.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Ligação Agua Situação");
		}else{
			sessao.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);
		}

		// pesquisa cobranca_situacao
		FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();
		filtroCobrancaSituacao.setCampoOrderBy(FiltroCobrancaSituacao.DESCRICAO);
		filtroCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoCobrancaSituacao = fachada.pesquisar(filtroCobrancaSituacao, CobrancaSituacao.class.getName());
		if(colecaoCobrancaSituacao == null || colecaoCobrancaSituacao.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cobrança Situação");
		}else{
			sessao.setAttribute("colecaoCobrancaSituacao", colecaoCobrancaSituacao);
		}

		// pesquisa as situações de ligações de esgoto
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
		filtroDocumentoTipo.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);
		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());
		if(colecaoLigacaoEsgotoSituacao == null || colecaoLigacaoEsgotoSituacao.isEmpty()){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Ligação Esgoto Situação");
		}else{
			sessao.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);
		}

		// Pesquisa as Contra Açoes.
		FiltroAcaoCobrancaEfeito filtroAcaoCobrancaEfeito = new FiltroAcaoCobrancaEfeito();
		filtroAcaoCobrancaEfeito.adicionarParametro(new ParametroSimples(FiltroAcaoCobrancaEfeito.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroAcaoCobrancaEfeito.setCampoOrderBy(FiltroAcaoCobrancaEfeito.DESCRICAO);
		Collection colecaoContraAcao = fachada.pesquisar(filtroAcaoCobrancaEfeito, AcaoCobrancaEfeito.class.getName());
		sessao.setAttribute("colecaoAcaoCobrancaEfeito", colecaoContraAcao);

		// [FS0009] - Verificar se o usuário possui autorização para utilizar a RD
		boolean temPermissaoResolucaoDiretoria = fachada.verificarPermissaoEspecial(PermissaoEspecial.USAR_PLANO_PAI_PARA_ORGAO_PUBLICO,
						((Usuario) sessao.getAttribute("usuarioLogado")));
		Collection<ResolucaoDiretoria> colecaoResolucaoDiretoria = null;

		if(temPermissaoResolucaoDiretoria){

			colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaDataVigenciaFimMaiorIgualDataAtual();

		}else{

			colecaoResolucaoDiretoria = fachada.pesquisarResolucaoDiretoriaMaiorDataVigenciaInicioComEntrada();
		}

		// [FS0001] - Verificar existência de dados
		if(colecaoResolucaoDiretoria != null && !colecaoResolucaoDiretoria.isEmpty()){
			sessao.setAttribute("colecaoResolucaoDiretoria", colecaoResolucaoDiretoria);
		}else{
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Resolução Diretoria");
		}

	}
}
