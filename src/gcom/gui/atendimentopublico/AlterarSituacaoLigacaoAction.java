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

package gcom.gui.atendimentopublico;

import gcom.atendimentopublico.ordemservico.EventoGeracaoServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrigemEncerramentoOrdemServico;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.atendimentopublico.ordemservico.AutorizarServicoAssociadoHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 20/03/2007
 */
public class AlterarSituacaoLigacaoAction
				extends GcomAction {

	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";

	private static final String CAMINHO_ENCERRAR_ORDEM_SERVICO = "alterarSituacaoLigacaoAction";

	private static final String CAMINHO_RETORNO_CONSULTAR_OS = "exibirAlterarSituacaoLigacaoAction";

	/**
	 * [UC0555] Alterar Situacao da Ligacao
	 * Este caso de uso permite alterar a situacao da ligacao de agua e/ou
	 * esgoto de acordo com o indicadorde rede e Ordem de Servico gerada.
	 * 
	 * @author Saulo Lima
	 * @date 20/05/2009
	 *       Alteração para autorizar OS's associadas, quando for o caso
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

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();
		AlterarSituacaoLigacaoActionForm form = (AlterarSituacaoLigacaoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = null;
		Imovel imovel = null;
		String idOrdemServico = null;
		String indicadorTipoLigacao = null;
		String idSituacaoLigacaoAguaNova = null;
		String idSituacaoLigacaoEsgotoNova = null;

		Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados = (Map<Integer, ServicoAssociadoAutorizacaoHelper>) httpServletRequest
						.getAttribute("mapServicosAutorizados");

		if(mapServicosAutorizados == null || mapServicosAutorizados.isEmpty()){

			OrdemServico ordemServico = (OrdemServico) sessao.getAttribute("ordemServico");

			boolean veioEncerrarOS = false;

			idOrdemServico = form.getIdOrdemServico();
			indicadorTipoLigacao = form.getIndicadorTipoLigacao();
			idSituacaoLigacaoAguaNova = form.getSituacaoLigacaoAguaNova();
			idSituacaoLigacaoEsgotoNova = form.getSituacaoLigacaoEsgotoNova();

			if(idOrdemServico == null){
				throw new ActionServletException("atencao.required", null, "Ordem de Serviço");
			}

			if(indicadorTipoLigacao == null){
				throw new ActionServletException("atencao.required", null, "Tipo de Ligacao a ser Removida");
			}

			if(indicadorTipoLigacao != null){
				if(indicadorTipoLigacao.equalsIgnoreCase("1")
								&& idSituacaoLigacaoAguaNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.required", null, "Nova Situação da Ligação de Água");
				}
			}

			if(indicadorTipoLigacao != null){
				if(indicadorTipoLigacao.equalsIgnoreCase("2")
								&& idSituacaoLigacaoEsgotoNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.required", null, "Nova Situação da Ligação de Esgoto");
				}
			}

			if(indicadorTipoLigacao != null){
				if(indicadorTipoLigacao.equalsIgnoreCase("3")
								&& idSituacaoLigacaoAguaNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
								&& idSituacaoLigacaoEsgotoNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.required", null, "Nova Situação da Ligação de Água e de Esgoto");
				}
			}

			fachada.validarOrdemServicoAlterarSituacaoLigacao(ordemServico, veioEncerrarOS);

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, ordemServico.getImovel().getId()));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO);

			Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			imovel = colecaoImovel.iterator().next();

			ServicoTipo servicoTipo = ordemServico.getServicoTipo();
			List<ServicoAssociadoAutorizacaoHelper> servicoAssociadoAutorizacaoHelperList = fachada
							.verificarServicosAssociadosParaAutorizacao(servicoTipo, EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO,
											OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, ordemServico.getId());

			if(servicoAssociadoAutorizacaoHelperList != null && !servicoAssociadoAutorizacaoHelperList.isEmpty()){

				// Parametros obrigatorios para a tela de autorização
				autorizarServicoAssociadoHelper = new AutorizarServicoAssociadoHelper();

				Map<String, Object> parametrosCaminhoAutorizacao = new HashMap<String, Object>();
				parametrosCaminhoAutorizacao.put("imovel", imovel);
				parametrosCaminhoAutorizacao.put("indicadorTipoLigacao", indicadorTipoLigacao);
				parametrosCaminhoAutorizacao.put("idSituacaoLigacaoAguaNova", idSituacaoLigacaoAguaNova);
				parametrosCaminhoAutorizacao.put("idSituacaoLigacaoEsgotoNova", idSituacaoLigacaoEsgotoNova);
				parametrosCaminhoAutorizacao.put("idOrdemServico", idOrdemServico);

				autorizarServicoAssociadoHelper.setParametrosCaminhoAutorizacao(parametrosCaminhoAutorizacao);
				autorizarServicoAssociadoHelper.setCaminhoAutorizacao(CAMINHO_ENCERRAR_ORDEM_SERVICO);

				Map<String, Object> parametrosCaminhoRetorno = new HashMap<String, Object>();
				parametrosCaminhoRetorno.put("numeroOS", ordemServico.getId());
				autorizarServicoAssociadoHelper.setParametrosCaminhoRetorno(parametrosCaminhoRetorno);
				autorizarServicoAssociadoHelper.setCaminhoRetorno(CAMINHO_RETORNO_CONSULTAR_OS);

				autorizarServicoAssociadoHelper.setServicosParaAutorizacao(servicoAssociadoAutorizacaoHelperList);

				sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER, autorizarServicoAssociadoHelper);

				retorno = actionMapping.findForward("mostrarAutorizarServicoAssociado");

				return retorno;
			}

		}else{
			autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
							.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);
			Map<String, Object> parametrosCaminhoAutorizacao = autorizarServicoAssociadoHelper.getParametrosCaminhoAutorizacao();

			imovel = (Imovel) parametrosCaminhoAutorizacao.get("imovel");
			idOrdemServico = (String) parametrosCaminhoAutorizacao.get("idOrdemServico");
			indicadorTipoLigacao = (String) parametrosCaminhoAutorizacao.get("indicadorTipoLigacao");
			idSituacaoLigacaoAguaNova = (String) parametrosCaminhoAutorizacao.get("idSituacaoLigacaoAguaNova");
			idSituacaoLigacaoEsgotoNova = (String) parametrosCaminhoAutorizacao.get("idSituacaoLigacaoEsgotoNova");
		}
		Integer idImovel = imovel.getId();
		try{
			idImovel = fachada.alterarSituacaoLigacao(imovel, indicadorTipoLigacao, idSituacaoLigacaoAguaNova, idSituacaoLigacaoEsgotoNova,
							idOrdemServico, usuarioLogado, mapServicosAutorizados);
		}catch(FachadaException fex){

			ActionServletException aex = new ActionServletException(fex.getMessage(), idImovel.toString());
			aex.setUrlBotaoVoltar("/gsan/exibirAlterarSituacaoLigacaoAction.do");
			throw aex;
		}

		montarPaginaSucesso(httpServletRequest, "A Alteração da Situação da Ligação do Imóvel " + idImovel + " efetuada com sucesso.",
						"Alterar outra Situação da Ligação", "exibirAlterarSituacaoLigacaoAction.do?menu=sim");

		return retorno;
	}
}
