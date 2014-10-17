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
	 *       Inclus�o dos atributos: Mensagem de Leitura , Mensagem de manuten��o, Mensagem de
	 *       preven��o de acidentes
	 *       Incid�ncia da anormalidade para a gera��o da ordem de servi�o,
	 *       Emiss�o autom�tica de documento, Indicador de que a ocorr�ncia aceita leitura,
	 *       Indicador de que a ocorr�ncia deve ser listada nos relat�rios de cr�tica/fiscaliza��o
	 *       de leitura
	 *       Indicador de reten��o de conta , Indicador de concess�o de cr�dito de consumo
	 *       Indicador de isen��o de cobran�a de �gua, Indicador de isen��o de cobran�a de esgoto
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
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

		montarPaginaSucesso(httpServletRequest, "Anormalidade de Leitura de c�digo " + leituraAnormalidade.getId().toString()
						+ " atualizada com sucesso.", "Realizar outra Manuten��o de Anormalidade de Leitura ",
						"exibirFiltrarAnormalidadeLeituraAction.do?menu=sim");
		return retorno;
	}
}
