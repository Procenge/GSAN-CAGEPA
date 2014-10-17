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

import gcom.atendimentopublico.registroatendimento.AtendimentoIncompleto;
import gcom.atendimentopublico.registroatendimento.AtendimentoIncompletoMotivo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoIncompletoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarRegistroAtendimentoIncompletoAction
				extends GcomAction {

	/**
	 *@author Andre Nishimura
	 *@date 10/02/2010
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("manterRegistroAtendimentoIncompleto");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarRegistroAtendimentoIncompletoActionForm filtrarRegistroAtendimentoIncompletoActionForm = (FiltrarRegistroAtendimentoIncompletoActionForm) actionForm;

		if(sessao.getAttribute("filtroATIN") != null){
			filtrarRegistroAtendimentoIncompletoActionForm = (FiltrarRegistroAtendimentoIncompletoActionForm) sessao
							.getAttribute("filtroATIN");
		}
		boolean parametroInformado = false;

		AtendimentoIncompleto ra = new AtendimentoIncompleto();

		FiltrarRegistroAtendimentoIncompletoHelper filtroRA = new FiltrarRegistroAtendimentoIncompletoHelper();

		// ATIN_CDDDDCHAMADA
		if(filtrarRegistroAtendimentoIncompletoActionForm.getCodigoDDD() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getCodigoDDD().equals("")){
			filtroRA.setDdd(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getCodigoDDD()));
			parametroInformado = true;
		}

		// ATIN_NNFONECHAMADA
		if(filtrarRegistroAtendimentoIncompletoActionForm.getFoneChamada() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getFoneChamada().equals("")){
			filtroRA.setFone(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getFoneChamada()));
		}

		// AIMO_ID
		AtendimentoIncompletoMotivo aimotivo;
		Collection colecaoMotivoAtendimentoIncompleto = new ArrayList();
		if(filtrarRegistroAtendimentoIncompletoActionForm.getMotivoAtendimentoIncompleto() != null
						&& filtrarRegistroAtendimentoIncompletoActionForm.getMotivoAtendimentoIncompleto().length > 0){
			String[] motivoAtendimentoIncompleto = filtrarRegistroAtendimentoIncompletoActionForm.getMotivoAtendimentoIncompleto();
			for(int i = 0; i < motivoAtendimentoIncompleto.length; i++){
				if(Integer.parseInt(motivoAtendimentoIncompleto[i]) != ConstantesSistema.NUMERO_NAO_INFORMADO){
					aimotivo = new AtendimentoIncompletoMotivo();
					aimotivo.setId(Integer.valueOf(motivoAtendimentoIncompleto[i]));
					colecaoMotivoAtendimentoIncompleto.add(aimotivo);
					parametroInformado = true;
				}
			}
		}
		filtroRA.setAtendimentoIncompletoMotivo(colecaoMotivoAtendimentoIncompleto);

		// CLIE_ID
		if(filtrarRegistroAtendimentoIncompletoActionForm.getCliente() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getCliente().equals("")){
			filtroRA.setIdCliente(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getCliente()));
			parametroInformado = true;
		}

		// RGAT_ID
		if(filtrarRegistroAtendimentoIncompletoActionForm.getRADefinitivo() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getRADefinitivo().equals("")){
			filtroRA.setIdRegistroAtendimento(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getRADefinitivo()));
			parametroInformado = true;
		}

		// UNID_IDATENDIMENTO
		if(filtrarRegistroAtendimentoIncompletoActionForm.getUnidadeAtendimento() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getUnidadeAtendimento().equals("")){
			filtroRA.setUnidadeAtendimento(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getUnidadeAtendimento()));
			parametroInformado = true;
		}

		// USUR_IDATENDIMENTO
		if(filtrarRegistroAtendimentoIncompletoActionForm.getUsuarioAtendimento() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getUsuarioAtendimento().equals("")){
			filtroRA.setUsuarioAtendimento(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getUsuarioAtendimento()));
			parametroInformado = true;
		}

		// UNID_IDRETORNOCHAMADA
		if(filtrarRegistroAtendimentoIncompletoActionForm.getUnidadeRetornoChamada() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getUnidadeRetornoChamada().equals("")){
			filtroRA.setUnidadeRetornoChamada(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getUnidadeRetornoChamada()));
			parametroInformado = true;
		}

		// USUR_IDRETORNOCHAMADA
		if(filtrarRegistroAtendimentoIncompletoActionForm.getUsuarioRetornoChamada() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getUsuarioRetornoChamada().equals("")){
			filtroRA.setUsuarioRetornoChamada(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getUsuarioRetornoChamada()));
			parametroInformado = true;
		}

		// ATIN_ICRETORNOCHAMADA
		if(filtrarRegistroAtendimentoIncompletoActionForm.getIndicadorRetornoChamada() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getIndicadorRetornoChamada().equals("")){
			filtroRA.setIndicadorRetornoChamada(Integer
							.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getIndicadorRetornoChamada()));
			parametroInformado = true;
		}

		// ATIN_NMCONTATO
		if(filtrarRegistroAtendimentoIncompletoActionForm.getNomeContato() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getNomeContato().equals("")){
			filtroRA.setNome(filtrarRegistroAtendimentoIncompletoActionForm.getNomeContato());
			filtroRA.setTipoPesquisaNome(Short.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getTipoPesquisa()));
			parametroInformado = true;
		}

		// ATIN_ID
		if(filtrarRegistroAtendimentoIncompletoActionForm.getNumeroRA() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getNumeroRA().equals("")){
			filtroRA.setId(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getNumeroRA()));
			parametroInformado = true;
		}

		// IMOV_ID
		if(filtrarRegistroAtendimentoIncompletoActionForm.getMatriculaImovel() != null
						&& !filtrarRegistroAtendimentoIncompletoActionForm.getMatriculaImovel().equals("")){
			filtroRA.setIdImovel(Integer.valueOf(filtrarRegistroAtendimentoIncompletoActionForm.getMatriculaImovel()));
			parametroInformado = true;
		}

		// STEP_ID
		{
			Collection<Integer> colecaoSolicitacaoTipoSolicitacao = new ArrayList();
			if(filtrarRegistroAtendimentoIncompletoActionForm.getTipoSolicitacao() != null
							&& filtrarRegistroAtendimentoIncompletoActionForm.getTipoSolicitacao().length > 0){
				String[] tipoSolicitacao = filtrarRegistroAtendimentoIncompletoActionForm.getTipoSolicitacao();
				for(int i = 0; i < tipoSolicitacao.length; i++){
					if(Integer.parseInt(tipoSolicitacao[i]) != ConstantesSistema.NUMERO_NAO_INFORMADO){
						colecaoSolicitacaoTipoSolicitacao.add(Integer.valueOf(tipoSolicitacao[i]));
						parametroInformado = true;
					}
				}
			}
			filtroRA.setColecaoTipoSolicitacao(colecaoSolicitacaoTipoSolicitacao);

			Collection<SolicitacaoTipoEspecificacao> colecaoSolicitacaoTipoEspecificacao = new ArrayList();
			if(colecaoSolicitacaoTipoSolicitacao.size() < 2 && filtrarRegistroAtendimentoIncompletoActionForm.getEspecificacao() != null
							&& filtrarRegistroAtendimentoIncompletoActionForm.getEspecificacao().length > 0){

				String[] tipoSolicitacaoEspecificacao = filtrarRegistroAtendimentoIncompletoActionForm.getEspecificacao();
				for(int i = 0; i < tipoSolicitacaoEspecificacao.length; i++){
					if(Integer.parseInt(tipoSolicitacaoEspecificacao[i]) != ConstantesSistema.NUMERO_NAO_INFORMADO){
						SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
						solicitacaoTipoEspecificacao.setId(Integer.valueOf(tipoSolicitacaoEspecificacao[i]));
						colecaoSolicitacaoTipoEspecificacao.add(solicitacaoTipoEspecificacao);
						parametroInformado = true;
					}
				}
			}
			filtroRA.setColecaoTipoSolicitacaoEspecificacao(colecaoSolicitacaoTipoEspecificacao);
		}

		// ATIN_TMCHAMADA
		Date dataChamadaInicial = null;
		Date dataChamadaFinal = null;
		if(filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoInicial() == null
						|| filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoInicial().equals("")){

			if(filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoFinal() != null
							&& !filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoFinal().equals("")){

				dataChamadaFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoFinal());
				dataChamadaFinal = Util.formatarDataFinal(dataChamadaFinal);

				dataChamadaInicial = Util.converteStringParaDate("01/01/1900");
				dataChamadaInicial = Util.formatarDataInicial(dataChamadaInicial);

				parametroInformado = true;
			}
		}else{
			dataChamadaInicial = Util.converteStringParaDate(filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoInicial());
			dataChamadaInicial = Util.formatarDataInicial(dataChamadaInicial);

			dataChamadaFinal = null;
			if(filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoFinal() == null
							|| filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoFinal().equals("")){
				dataChamadaFinal = new Date();
				dataChamadaFinal = Util.formatarDataFinal(dataChamadaFinal);
			}else{
				dataChamadaFinal = Util.converteStringParaDate(filtrarRegistroAtendimentoIncompletoActionForm.getPeriodoAtendimentoFinal());
				dataChamadaFinal = Util.adaptarDataFinalComparacaoBetween(dataChamadaFinal);
			}
			int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataChamadaInicial, dataChamadaFinal);
			if(qtdeDias < 0){
				throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
			}
			parametroInformado = true;
		}
		filtroRA.setChamadaInicial(dataChamadaInicial);
		filtroRA.setChamadaFinal(dataChamadaFinal);

		// Filtra Registro Atendimento Incompleto
		if(parametroInformado){
			Collection<AtendimentoIncompleto> colecaoRegistroAtendimento = new ArrayList();
			filtroRA.setNumeroPagina(ConstantesSistema.NUMERO_NAO_INFORMADO);
			int totalRegistros = ConstantesSistema.NUMERO_NAO_INFORMADO;
			Integer tamanho = null;

			if(httpServletRequest.getParameter("page.offset") == null){

				Collection colecaoRAHelperCompleta = fachada.filtrarRegistroAtendimentoIncompleto(filtroRA);
				tamanho = colecaoRAHelperCompleta.size();

				sessao.setAttribute("colecaoCompleta", colecaoRAHelperCompleta);

			}else{
				tamanho = (Integer) sessao.getAttribute("totalRegistros");
			}

			if(tamanho == null || tamanho == 0){
				throw new ActionServletException("atencao.pesquisa.nenhumresultado");

			}else{

				totalRegistros = tamanho.intValue();

				retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

				int numeroPaginasPesquisa = ((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa")).intValue();

				filtroRA.setNumeroPagina(numeroPaginasPesquisa);

				colecaoRegistroAtendimento = fachada.filtrarRegistroAtendimentoIncompleto(filtroRA);

				sessao.removeAttribute("numeroOS");
				sessao.setAttribute("colecaoRAHelper", colecaoRegistroAtendimento);

			}
		}else{
			throw new ActionServletException("atencao.filtrar_informar_um_filtro",
							"exibirFiltrarRegistroAtendimentoIncompletoAction.do?menu=sim", "");
		}
		sessao.setAttribute("filtroATIN", filtrarRegistroAtendimentoIncompletoActionForm);
		return retorno;
	}
}