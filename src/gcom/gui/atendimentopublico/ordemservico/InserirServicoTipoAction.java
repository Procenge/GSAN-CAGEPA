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

import gcom.atendimentopublico.ordemservico.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para inserir o tipo de servi�o.
 * 
 * @author lms
 * @date 07/08/2006
 * @author Virg�nia Melo
 * @date 11/12/2008
 *       Altera��es no [UC0410] para a v0.07
 * @author Saulo Lima
 * @date 31/07/2009
 *       Altera��o ao inserir o ServicoTipoAtividade �nica
 */
public class InserirServicoTipoAction
				extends GcomAction {

	/**
	 * Este caso de uso permite inserir tipo retorno da os referida
	 * [UC0410] Inserir o Servi�o Tipo
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InserirServicoTipoActionForm form = (InserirServicoTipoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Atualiza a entidade com os valores do formul�rio
		ServicoTipo servicoTipo = form.getServicoTipo();

		// Recuperando servi�os associados
		Collection servicosAssociados = new ArrayList<ServicoAssociado>();
		servicosAssociados = (Collection) sessao.getAttribute("colecaoServicoAssociado");

		if(servicosAssociados != null && !servicosAssociados.isEmpty()){
			servicoTipo.setServicosAssociados(servicosAssociados);
		}

		// Servi�o Tipo Tr�mite
		Boolean indicadorParametroTramite = (Boolean) sessao.getAttribute("indicadorParametroTramite");

		if(indicadorParametroTramite != null && indicadorParametroTramite){
			Collection<ServicoTipoTramite> colecaoServicoTipoTramite = (Collection<ServicoTipoTramite>) sessao
							.getAttribute("colecaoServicoTipoTramite");

			if(!Util.isVazioOrNulo(colecaoServicoTipoTramite)){
				servicoTipo.setServicosTipoTramite(colecaoServicoTipoTramite);
			}
			// Altera��o do caso de uso UC0410, OC1123944
			// else{
			// // [FS0019] - Criticar cole��o vazia
			// throw new ActionServletException("atencao.informe.configuracao.destino.tramite");
			// }
		}

		if(servicoTipo.getServicoTipoAtividades() == null || servicoTipo.getServicoTipoAtividades().isEmpty()){

			if(form.getAtividadeUnica() != null && form.getAtividadeUnica().trim().equals("1")){

				FiltroAtividade filtroAtividade = new FiltroAtividade();
				filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORATIVIDADEUNICA,
								Atividade.INDICADOR_ATIVIDADE_UNICA_SIM));

				Collection<Atividade> colecaoAtividade = fachada.pesquisar(filtroAtividade, Atividade.class.getName());

				if(colecaoAtividade.isEmpty()){
					throw new ActionServletException("atencao.naocadastrado", null, "Atividade");
				}else{
					Atividade atividadeUnica = (Atividade) Util.retonarObjetoDeColecao(colecaoAtividade);

					ServicoTipoAtividade servicoTipoAtividade = new ServicoTipoAtividade();
					servicoTipoAtividade.setAtividade(atividadeUnica);
					servicoTipoAtividade.setUltimaAlteracao(new Date());
					servicoTipoAtividade.setNumeroExecucao(Short.valueOf((short) 1));

					servicoTipo.getServicoTipoAtividades().add(servicoTipoAtividade);
				}
			}else{
				throw new ActionServletException("atencao.adionar.atividade.servico.tipo");
			}
		}

		if(sessao.getAttribute("servicoTipoReferencia") != null){
			servicoTipo.setServicoTipoReferencia((ServicoTipoReferencia) sessao.getAttribute("servicoTipoReferencia"));
		}

		if(servicoTipo.getIndicadorFiscalizacaoInfracao() == 1){
			if(servicoTipo.getServicoTipoReferencia() != null){
				throw new ActionServletException("atencao.infracao.servico.tipo");
			}
		}

		if(servicoTipo.getIndicadorGerarHistoricoImovel() == null){

			Short valorPadrao = 2;

			servicoTipo.setIndicadorGerarHistoricoImovel(valorPadrao);

		}

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// OC1213341 - Inserir cole��o de Servi�os tipos e valores por Localidade
		Collection colecaoServicoTipoValorLocalidade = form.getServicoTipoValorLocalidade();
		Integer idServicoTipo = null;
		if(!Util.isVazioOrNulo(colecaoServicoTipoValorLocalidade)){

			checarServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidade, servicoTipo);

			// Insere na base de dados
			idServicoTipo = fachada.inserirServicoTipo(servicoTipo, usuarioLogado);

			colecaoServicoTipoValorLocalidade = (Collection) inserirServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidade,
							servicoTipo, idServicoTipo);

			fachada.inserirColecaoServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidade);
		}else{
			// Insere na base de dados
			idServicoTipo = fachada.inserirServicoTipo(servicoTipo, usuarioLogado);
		}

		// Exibe a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Tipo de Servi�o " + servicoTipo.getDescricao() + " inserido com sucesso.",
						"Inserir Outro Tipo de Servi�o", "exibirInserirServicoTipoAction.do?menu=sim",
						"exibirAtualizarTipoServicoAction.do?pesquisa=S&idRegistroAtualizacao=" + idServicoTipo,
						"Atualizar Tipo de Servi�o Inserido");

		return retorno;

	}

	public void checarServicoTipoValorLocalidade(Collection colecao, ServicoTipo servicoTipo){

		if(!Util.isVazioOrNulo(colecao)){
			for(Iterator iter = colecao.iterator(); iter.hasNext();){
				ServicoTipoValorLocalidade stvl = (ServicoTipoValorLocalidade) iter.next();
				BigDecimal valorNaColecao = stvl.getValorServico();
				BigDecimal valorNoForm = servicoTipo.getValor();
				if(valorNaColecao.compareTo(valorNoForm) == 0){
					throw new ActionServletException("atencao.valor_diferente_padrao");
				}

			}
		}
	}

	public Collection inserirServicoTipoValorLocalidade(Collection colecao, ServicoTipo servicoTipo, Integer idServicoTipo){

		Collection colecaoRetorno = null;

		if(!Util.isVazioOrNulo(colecao)){

			colecaoRetorno = new ArrayList();

			for(Iterator iter = colecao.iterator(); iter.hasNext();){
				ServicoTipoValorLocalidade stvl = (ServicoTipoValorLocalidade) iter.next();
				servicoTipo.setId(idServicoTipo);
				stvl.setServicoTipo(servicoTipo);
				colecaoRetorno.add(stvl);
			}
		}
		return colecaoRetorno;
	}
}