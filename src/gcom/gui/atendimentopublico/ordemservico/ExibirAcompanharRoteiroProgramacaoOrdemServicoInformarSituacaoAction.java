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
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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

public class ExibirAcompanharRoteiroProgramacaoOrdemServicoInformarSituacaoAction
				extends GcomAction {

	private static final String ATRIBUTO_ORIGEM_ENCERRAMENTO_OS = "origemEncerramentoOS";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);
		sessao.setAttribute(ATRIBUTO_ORIGEM_ENCERRAMENTO_OS, OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO);

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("situacaoOs");

		Fachada fachada = Fachada.getInstancia();

		// Form
		AcompanharRoteiroProgramacaoOrdemServicoActionForm acompanharActionForm = (AcompanharRoteiroProgramacaoOrdemServicoActionForm) actionForm;

		if(httpServletRequest.getParameter("modificarMotivoEncerramento") != null
						&& httpServletRequest.getParameter("modificarMotivoEncerramento").equals("S")){
			FiltroOsProgramNaoEncerMotivo filtroOsProgramNaoEncerMotivo = new FiltroOsProgramNaoEncerMotivo();
			filtroOsProgramNaoEncerMotivo.adicionarParametro(new ParametroSimples(FiltroOsProgramNaoEncerMotivo.ID, acompanharActionForm
							.getMotivoNaoEncerramento()));

			OsProgramNaoEncerMotivo osProgramNaoEncerMotivo = (OsProgramNaoEncerMotivo) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroOsProgramNaoEncerMotivo, OsProgramNaoEncerMotivo.class.getName()));

			acompanharActionForm.setMotivoCobrarVisitaImprodutiva("2");
			acompanharActionForm.setMotivoVisitaImprodutiva("2");

			if(osProgramNaoEncerMotivo != null
							&& acompanharActionForm.getSituacaoOrdemServico().equals(OrdemServico.SITUACAO_PENDENTE.toString())
							&& osProgramNaoEncerMotivo.getIndicadorCobraVisitaImprodutiva().equals(ConstantesSistema.SIM)){
				FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();
				filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
				filtroOrdemServico
								.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, acompanharActionForm.getIdOrdemServico()));
				
				OrdemServico ordemServicoPesquisado = (OrdemServico) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroOrdemServico,
								OrdemServico.class.getName()));
				Integer qtdMaximaVisitaImprodutivas = ordemServicoPesquisado.getServicoTipo()
								.getNumeroMaximoVisitasImprodutivasPermitidas();

				FiltroOrdemServicoProgramacao filtroOrdemServicoProgramacao = new FiltroOrdemServicoProgramacao();
				filtroOrdemServicoProgramacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServicoProgramacao.ORDEM_SERVICO);
				filtroOrdemServicoProgramacao.adicionarCaminhoParaCarregamentoEntidade("osProgramNaoEncerMotivo");

				filtroOrdemServicoProgramacao.adicionarParametro(new ParametroSimples(FiltroOrdemServicoProgramacao.ORDEM_SERVICO_ID,
								acompanharActionForm.getIdOrdemServico()));
				filtroOrdemServicoProgramacao.adicionarParametro(new ParametroSimples("osProgramNaoEncerMotivo.id", osProgramNaoEncerMotivo
								.getId()));
				
				Collection<OrdemServicoProgramacao> colecaoOrdemServicoProgramacao = fachada.pesquisar(
								filtroOrdemServicoProgramacao, OrdemServicoProgramacao.class.getName());

				if(qtdMaximaVisitaImprodutivas == null || colecaoOrdemServicoProgramacao.size() < qtdMaximaVisitaImprodutivas){
					acompanharActionForm.setMotivoCobrarVisitaImprodutiva(osProgramNaoEncerMotivo.getIndicadorCobraVisitaImprodutiva()
									.toString());
					acompanharActionForm.setMotivoVisitaImprodutiva(osProgramNaoEncerMotivo.getIndicadorVisitaImprodutiva().toString());
				}else{
					String[] parametros = new String[2];
					parametros[0] = acompanharActionForm.getIdOrdemServico().toString();
					parametros[1] = qtdMaximaVisitaImprodutivas.toString();
					
					throw new ActionServletException("atencao.ordem_servico_limite_maximo", null, parametros);
				}
				
				this.pesquisarOsProgramNaoEncerMotivo(httpServletRequest);

				return retorno;
			}
		}else{
			acompanharActionForm.setMotivoCobrarVisitaImprodutiva("2");
			acompanharActionForm.setMotivoVisitaImprodutiva("2");
		}

		if(httpServletRequest.getParameter("submitAutomatico") != null && httpServletRequest.getParameter("submitAutomatico").equals("ok")){
			httpServletRequest.setAttribute("submitAutomatico", "ok");
		}

		String chaveOs = httpServletRequest.getParameter("chave");
		String chaveEquipe = httpServletRequest.getParameter("chaveEquipe");
		String idOrdemServicoProgramacao = httpServletRequest.getParameter("idOrdemServicoProgramacao");
		String dataRoteiroParametro = httpServletRequest.getParameter("dataRoteiro");

		if(dataRoteiroParametro != null && !"".equals(dataRoteiroParametro)){
			sessao.setAttribute("dataRoteiro", dataRoteiroParametro);
		}

		if(chaveOs == null){
			chaveOs = acompanharActionForm.getIdOrdemServico();
		}
		if(idOrdemServicoProgramacao == null){
			idOrdemServicoProgramacao = acompanharActionForm.getIdOrdemServicoProgramacao();
		}
		OrdemServico ordemServico = fachada.recuperaOSPorId(new Integer(chaveOs));

		if(ordemServico.getSituacao() == OrdemServico.SITUACAO_ENCERRADO.shortValue()){
			throw new ActionServletException("atencao.ordem_servico_encerrada_para_alocar");
		}

		Date dataRoteiro = Util.converteStringParaDate(acompanharActionForm.getDataRoteiro());

		fachada.verificaExitenciaProgramacaoAtivaParaDiasAnteriores(new Integer(chaveOs), dataRoteiro);

		acompanharActionForm.setDataRoteiro(dataRoteiroParametro);
		acompanharActionForm.setIdOrdemServico(chaveOs);
		acompanharActionForm.setIdOrdemServicoProgramacao(idOrdemServicoProgramacao);
		acompanharActionForm.setDescricaoOrdemServico(ordemServico.getServicoTipo().getDescricao());
		acompanharActionForm.setChaveEquipe(chaveEquipe);
		acompanharActionForm.setQtdFotos(fachada.pesquisarQuantidadeFotosOrdemServicoProgramacao(Integer.valueOf(chaveOs), Integer
						.valueOf(idOrdemServicoProgramacao)));

		ObterDescricaoSituacaoOSHelper obter = fachada.obterDescricaoSituacaoOS(new Integer(chaveOs));
		acompanharActionForm.setSituacaoAtual(obter.getDescricaoSituacao());

		this.pesquisarOsProgramNaoEncerMotivo(httpServletRequest);

		return retorno;
	}

	/**
	 * Pesquisa todas as OsProgramNaoEncerMotivo
	 * 
	 * @author Rafael Pinto
	 * @date 25/08/2006
	 * @return Collection de OsProgramNaoEncerMotivo
	 */
	private Collection<OsProgramNaoEncerMotivo> pesquisarOsProgramNaoEncerMotivo(HttpServletRequest httpServletRequest){

		Collection<OsProgramNaoEncerMotivo> retorno = new ArrayList();

		FiltroOsProgramNaoEncerMotivo filtroOsProgramNaoEncerMotivo = new FiltroOsProgramNaoEncerMotivo();

		filtroOsProgramNaoEncerMotivo.setCampoOrderBy(FiltroOsProgramNaoEncerMotivo.DESCRICAO);

		retorno = Fachada.getInstancia().pesquisar(filtroOsProgramNaoEncerMotivo, OsProgramNaoEncerMotivo.class.getName());

		httpServletRequest.setAttribute("colecaoMotivoNaoEncerramento", retorno);

		return retorno;

	}

}