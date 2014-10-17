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
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
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
	 *       Altera��o para autorizar OS's associadas, quando for o caso
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
				throw new ActionServletException("atencao.required", null, "Ordem de Servi�o");
			}

			if(indicadorTipoLigacao == null){
				throw new ActionServletException("atencao.required", null, "Tipo de Ligacao a ser Removida");
			}

			if(indicadorTipoLigacao != null){
				if(indicadorTipoLigacao.equalsIgnoreCase("1")
								&& idSituacaoLigacaoAguaNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.required", null, "Nova Situa��o da Liga��o de �gua");
				}
			}

			if(indicadorTipoLigacao != null){
				if(indicadorTipoLigacao.equalsIgnoreCase("2")
								&& idSituacaoLigacaoEsgotoNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.required", null, "Nova Situa��o da Liga��o de Esgoto");
				}
			}

			if(indicadorTipoLigacao != null){
				if(indicadorTipoLigacao.equalsIgnoreCase("3")
								&& idSituacaoLigacaoAguaNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)
								&& idSituacaoLigacaoEsgotoNova.equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
					throw new ActionServletException("atencao.required", null, "Nova Situa��o da Liga��o de �gua e de Esgoto");
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

				// Parametros obrigatorios para a tela de autoriza��o
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

		montarPaginaSucesso(httpServletRequest, "A Altera��o da Situa��o da Liga��o do Im�vel " + idImovel + " efetuada com sucesso.",
						"Alterar outra Situa��o da Liga��o", "exibirAlterarSituacaoLigacaoAction.do?menu=sim");

		return retorno;
	}
}
