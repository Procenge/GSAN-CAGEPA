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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.micromedicao.leitura;

import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cobranca.DocumentoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidadeLeitura;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.LeituraAnormalidadeLeitura;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarAnormalidadeLeituraAction
				extends GcomAction {

	/**
	 * @author eduardo henrique [UC0190]
	 * @date 16/06/2008
	 *       Inclusão dos atributos: Mensagem de Leitura , Mensagem de manutenção, Mensagem de
	 *       prevenção de acidentes
	 *       Incidência da anormalidade para a geração da ordem de serviço,
	 *       Emissão automática de documento, Indicador de que a ocorrência aceita leitura,
	 *       Indicador de que a ocorrência deve ser listada nos relatórios de crítica/fiscalização
	 *       de leitura
	 *       Indicador de retenção de conta , Indicador de concessão de crédito de consumo
	 *       Indicador de isenção de cobrança de água, Indicador de isenção de cobrança de esgoto
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		AtualizarAnormalidadeLeituraActionForm atualizarAnormalidadeLeituraActionForm = (AtualizarAnormalidadeLeituraActionForm) actionForm;

		LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) sessao.getAttribute("leituraAnormalidadeAtualizar");

		leituraAnormalidade.setDescricao(atualizarAnormalidadeLeituraActionForm.getDescricao());
		leituraAnormalidade.setDescricaoAbreviada(atualizarAnormalidadeLeituraActionForm.getAbreviatura());

		leituraAnormalidade.setIndicadorRelativoHidrometro(new Short(atualizarAnormalidadeLeituraActionForm
						.getIndicadorRelativoHidrometro()));

		if(atualizarAnormalidadeLeituraActionForm.getUsoRestritoSistema() != null){
			leituraAnormalidade.setIndicadorSistema(new Short(atualizarAnormalidadeLeituraActionForm.getUsoRestritoSistema()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getPerdaTarifaSocial() != null){
			leituraAnormalidade.setIndicadorPerdaTarifaSocial(new Short(atualizarAnormalidadeLeituraActionForm.getPerdaTarifaSocial()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getIndicadorImovelSemHidrometro() != null){
			leituraAnormalidade.setIndicadorImovelSemHidrometro(new Short(atualizarAnormalidadeLeituraActionForm
							.getIndicadorImovelSemHidrometro()));
		}

		leituraAnormalidade.setIndicadorEmissaoOrdemServico(new Short(atualizarAnormalidadeLeituraActionForm.getOsAutomatico()));

		if(atualizarAnormalidadeLeituraActionForm.getIndicadorUso() != null){
			leituraAnormalidade.setIndicadorUso(new Short(atualizarAnormalidadeLeituraActionForm.getIndicadorUso()));
		}
		// AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = null;

		// setando

		ServicoTipo servicoTipo = null;

		if(atualizarAnormalidadeLeituraActionForm.getTipoServico() != null
						&& !atualizarAnormalidadeLeituraActionForm.getTipoServico().equals("")
						&& !atualizarAnormalidadeLeituraActionForm.getTipoServico().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			servicoTipo = new ServicoTipo();

			servicoTipo.setId(new Integer(atualizarAnormalidadeLeituraActionForm.getTipoServico()));
		}

		leituraAnormalidade.setServicoTipo(servicoTipo);

		if(atualizarAnormalidadeLeituraActionForm.getConsumoLeituraNaoInformado() != null){

			Integer idConsumoLeituraNaoInformado = new Integer(atualizarAnormalidadeLeituraActionForm.getConsumoLeituraNaoInformado());

			if(idConsumoLeituraNaoInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				leituraAnormalidade.setLeituraAnormalidadeConsumoSemleitura(null);
			}else{
				FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumo = new FiltroLeituraAnormalidadeConsumo();
				filtroLeituraAnormalidadeConsumo.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeConsumo.ID,
								atualizarAnormalidadeLeituraActionForm.getConsumoLeituraNaoInformado().toString()));
				Collection colecaoConsumoLeituraNaoInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeConsumo,
								LeituraAnormalidadeConsumo.class.getName());

				// setando
				leituraAnormalidade.setLeituraAnormalidadeConsumoSemleitura((LeituraAnormalidadeConsumo) colecaoConsumoLeituraNaoInformado
								.iterator().next());
			}
		}

		if(atualizarAnormalidadeLeituraActionForm.getConsumoLeituraInformado() != null){

			Integer idConsumoLeituraInformado = new Integer(atualizarAnormalidadeLeituraActionForm.getConsumoLeituraInformado());

			if(idConsumoLeituraInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				leituraAnormalidade.setLeituraAnormalidadeConsumoComleitura(null);
			}else{
				FiltroLeituraAnormalidadeConsumo filtroLeituraAnormalidadeConsumo = new FiltroLeituraAnormalidadeConsumo();
				filtroLeituraAnormalidadeConsumo.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeConsumo.ID,
								atualizarAnormalidadeLeituraActionForm.getConsumoLeituraInformado().toString()));
				Collection colecaoConsumoLeituraInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeConsumo,
								LeituraAnormalidadeConsumo.class.getName());

				// setando
				leituraAnormalidade.setLeituraAnormalidadeConsumoComleitura((LeituraAnormalidadeConsumo) colecaoConsumoLeituraInformado
								.iterator().next());
			}
		}

		if(atualizarAnormalidadeLeituraActionForm.getLeituraLeituraNaoturaInformado() != null){

			Integer idLeituraLeituraNaoturaInformado = new Integer(atualizarAnormalidadeLeituraActionForm
							.getLeituraLeituraNaoturaInformado());

			if(idLeituraLeituraNaoturaInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				leituraAnormalidade.setLeituraAnormalidadeLeituraSemleitura(null);
			}else{
				FiltroLeituraAnormalidadeLeitura filtroLeituraAnormalidadeLeitura = new FiltroLeituraAnormalidadeLeitura();
				filtroLeituraAnormalidadeLeitura.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeLeitura.ID,
								atualizarAnormalidadeLeituraActionForm.getLeituraLeituraNaoturaInformado().toString()));
				Collection colecaoLeituraLeituraNaoturaInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeLeitura,
								LeituraAnormalidadeLeitura.class.getName());

				// setando
				leituraAnormalidade
								.setLeituraAnormalidadeLeituraSemleitura((LeituraAnormalidadeLeitura) colecaoLeituraLeituraNaoturaInformado
												.iterator().next());
			}
		}

		if(atualizarAnormalidadeLeituraActionForm.getLeituraLeituraInformado() != null){

			Integer idLeituraLeituraInformado = new Integer(atualizarAnormalidadeLeituraActionForm.getLeituraLeituraInformado());

			if(idLeituraLeituraInformado.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

				leituraAnormalidade.setLeituraAnormalidadeLeituraComleitura(null);
			}else{
				FiltroLeituraAnormalidadeLeitura filtroLeituraAnormalidadeLeitura = new FiltroLeituraAnormalidadeLeitura();
				filtroLeituraAnormalidadeLeitura.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidadeLeitura.ID,
								atualizarAnormalidadeLeituraActionForm.getLeituraLeituraInformado().toString()));
				Collection colecaoLeituraLeituraInformado = (Collection) fachada.pesquisar(filtroLeituraAnormalidadeLeitura,
								LeituraAnormalidadeLeitura.class.getName());

				// setando
				leituraAnormalidade.setLeituraAnormalidadeLeituraComleitura((LeituraAnormalidadeLeitura) colecaoLeituraLeituraInformado
								.iterator().next());
			}
		}

		// Novos atributos
		DocumentoTipo documentoTipo = null;

		if(atualizarAnormalidadeLeituraActionForm.getTipoDocumento() != null
						&& !atualizarAnormalidadeLeituraActionForm.getTipoDocumento().equals("")
						&& !atualizarAnormalidadeLeituraActionForm.getTipoDocumento().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			documentoTipo = new DocumentoTipo();

			documentoTipo.setId(new Integer(atualizarAnormalidadeLeituraActionForm.getTipoDocumento()));
		}

		leituraAnormalidade.setDocumentoTipo(documentoTipo);

		if(atualizarAnormalidadeLeituraActionForm.getMensagemImpressaoConta() != null
						&& !atualizarAnormalidadeLeituraActionForm.getMensagemImpressaoConta().equalsIgnoreCase("")){
			leituraAnormalidade.setMensagemContaLeituraAnormalidade(atualizarAnormalidadeLeituraActionForm.getMensagemImpressaoConta());
		}else{
			leituraAnormalidade.setMensagemContaLeituraAnormalidade(null);
		}

		if(atualizarAnormalidadeLeituraActionForm.getSugestaoAgenteManutencao() != null
						&& !atualizarAnormalidadeLeituraActionForm.getSugestaoAgenteManutencao().equalsIgnoreCase("")){
			leituraAnormalidade.setMensagemSugestaoManutencaoLeituraAnormalidade(atualizarAnormalidadeLeituraActionForm
							.getSugestaoAgenteManutencao());
		}else{
			leituraAnormalidade.setMensagemSugestaoManutencaoLeituraAnormalidade(null);
		}

		if(atualizarAnormalidadeLeituraActionForm.getSugestaoAgentePrevencao() != null
						&& !atualizarAnormalidadeLeituraActionForm.getSugestaoAgentePrevencao().equalsIgnoreCase("")){
			leituraAnormalidade.setMensagemSugestaoPrevencaoLeituraAnormalidade(atualizarAnormalidadeLeituraActionForm
							.getSugestaoAgentePrevencao());
		}else{
			leituraAnormalidade.setMensagemSugestaoPrevencaoLeituraAnormalidade(null);
		}

		if(atualizarAnormalidadeLeituraActionForm.getNumeroIncidenciasGeracaoOS() != null
						&& !atualizarAnormalidadeLeituraActionForm.getNumeroIncidenciasGeracaoOS().equalsIgnoreCase("")
						&& !atualizarAnormalidadeLeituraActionForm.getNumeroIncidenciasGeracaoOS().equals(
										ConstantesSistema.NUMERO_NAO_INFORMADO)){
			leituraAnormalidade.setNumeroIncidenciasGeracaoOrdemServico(new Short(atualizarAnormalidadeLeituraActionForm
							.getNumeroIncidenciasGeracaoOS()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getAceiteLeitura() != null
						&& !atualizarAnormalidadeLeituraActionForm.getAceiteLeitura().equalsIgnoreCase("")){
			leituraAnormalidade.setIndicadorAceiteLeitura(new Short(atualizarAnormalidadeLeituraActionForm.getAceiteLeitura()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getImpressaoRelatorioCriticaFiscalizacao() != null
						&& !atualizarAnormalidadeLeituraActionForm.getImpressaoRelatorioCriticaFiscalizacao().equalsIgnoreCase("")){
			leituraAnormalidade.setIndicadorListagemAnormalidadeRelatorios(new Short(atualizarAnormalidadeLeituraActionForm
							.getImpressaoRelatorioCriticaFiscalizacao()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getIndicadorRetencaoConta() != null
						&& !atualizarAnormalidadeLeituraActionForm.getIndicadorRetencaoConta().equalsIgnoreCase("")){
			leituraAnormalidade.setIndicadorRetencaoConta(new Short(atualizarAnormalidadeLeituraActionForm.getIndicadorRetencaoConta()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getIndicadorConcessaoCreditoConsumo() != null
						&& !atualizarAnormalidadeLeituraActionForm.getIndicadorConcessaoCreditoConsumo().equalsIgnoreCase("")){
			leituraAnormalidade.setIndicadorCreditoConsumo(new Short(atualizarAnormalidadeLeituraActionForm
							.getIndicadorConcessaoCreditoConsumo()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaAgua() != null
						&& !atualizarAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaAgua().equalsIgnoreCase("")){
			leituraAnormalidade
							.setIndicadorIsencaoAgua(new Short(atualizarAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaAgua()));
		}

		if(atualizarAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaEsgoto() != null
						&& !atualizarAnormalidadeLeituraActionForm.getIndicadorIsencaoCobrancaEsgoto().equalsIgnoreCase("")){
			leituraAnormalidade.setIndicadorIsencaoEsgoto(new Short(atualizarAnormalidadeLeituraActionForm
							.getIndicadorIsencaoCobrancaEsgoto()));
		}

		fachada.atualizarAnormalidadeLeitura(leituraAnormalidade, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Anormalidade de Leitura de código " + leituraAnormalidade.getId().toString()
						+ " atualizada com sucesso.", "Realizar outra Manutenção de Anormalidade de Leitura ",
						"exibirFiltrarAnormalidadeLeituraAction.do?menu=sim");
		return retorno;
	}
}
