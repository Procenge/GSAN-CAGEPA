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

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.FiltrarRegistroAtendimentoHelper;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.atendimentopublico.registroatendimento.bean.RAFiltroHelper;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

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
 * [UC0503]Tramitar Conjunto de Registro de Atendimento
 * 
 * @author Ana Maria
 * @date 08/01/2007
 */
public class FiltrarRegistroAtendimentoTramitacaoAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		FiltrarRegistroAtendimentoTramitacaoActionForm form = (FiltrarRegistroAtendimentoTramitacaoActionForm) actionForm;

		ActionForward retorno = actionMapping.findForward("tramitacaoRegistroAtendimento");

		boolean parametroInformado = false;

		String retornaDoPopup = (String) httpServletRequest.getParameter("retornaDoPopup");

		if(retornaDoPopup == null){
			RegistroAtendimento ra = new RegistroAtendimento();
			FiltrarRegistroAtendimentoHelper filtroRA = new FiltrarRegistroAtendimentoHelper();
			// Numero RA
			if(form.getNumeroRa() != null && !form.getNumeroRa().equals("")){
				ra.setId(new Integer(form.getNumeroRa()));
				parametroInformado = true;
			}

			String indicadorOSProgramada = null;

			if(!Util.isVazioOuBranco(form.getIndicadorOSProgramada())){
				indicadorOSProgramada = form.getIndicadorOSProgramada();
			}

			// Tipo Solicita��o
			Collection<Integer> colecaoSolicitacaoTipoSolicitacao = new ArrayList();
			if(form.getTipoSolicitacao() != null && form.getTipoSolicitacao().length > 0){
				String[] tipoSolicitacao = form.getTipoSolicitacao();
				for(int i = 0; i < tipoSolicitacao.length; i++){
					if(new Integer(tipoSolicitacao[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
						colecaoSolicitacaoTipoSolicitacao.add(new Integer(tipoSolicitacao[i]));
						// passar a cole��o de especifica��o por par�metro
						parametroInformado = true;
					}
				}
			}

			// Tipo Especifica��o
			Collection<Integer> colecaoSolicitacaoTipoEspecificacao = new ArrayList();
			if(colecaoSolicitacaoTipoSolicitacao.size() < 2 && form.getEspecificacao() != null && form.getEspecificacao().length > 0){
				String[] tipoSolicitacaoEspecificacao = form.getEspecificacao();
				for(int i = 0; i < tipoSolicitacaoEspecificacao.length; i++){
					if(new Integer(tipoSolicitacaoEspecificacao[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
						colecaoSolicitacaoTipoEspecificacao.add(new Integer(tipoSolicitacaoEspecificacao[i]));
						// passar a cole��o de especifica��o por par�metro
						parametroInformado = true;
					}
				}
			}
			// Data de Atendimento
			Date dataAtendimentoInicial = null;
			Date dataAtendimentoFinal = null;
			if(form.getPeriodoAtendimentoInicial() != null && !form.getPeriodoAtendimentoInicial().equals("")){
				dataAtendimentoInicial = Util.converteStringParaDate(form.getPeriodoAtendimentoInicial());
				dataAtendimentoFinal = null;
				if(form.getPeriodoAtendimentoFinal() != null && !form.getPeriodoAtendimentoFinal().equals("")){
					dataAtendimentoFinal = Util.converteStringParaDate(form.getPeriodoAtendimentoFinal());
					dataAtendimentoFinal = Util.adaptarDataFinalComparacaoBetween(dataAtendimentoFinal);
				}else{
					dataAtendimentoFinal = new Date();
				}
				// Verificar data final menor que data inicial
				int qtdeDias = Util.obterQuantidadeDiasEntreDuasDatas(dataAtendimentoInicial, dataAtendimentoFinal);
				if(qtdeDias < 0){
					throw new ActionServletException("atencao.filtrar_data_final_maior_que_inicial");
				}
				// passar as datas de atendimento por par�metro
				parametroInformado = true;
			}

			// Unidade de Atual
			UnidadeOrganizacional unidadeAtual = null;
			if(form.getUnidadeAtualId() != null && !form.getUnidadeAtualId().equals("")){
				unidadeAtual = new UnidadeOrganizacional();
				unidadeAtual.setId(new Integer(form.getUnidadeAtualId()));
				// passar cole��o de unidades por par�metro
				parametroInformado = true;
			}
			// Unidade de Superior
			UnidadeOrganizacional unidadeSuperior = null;
			if(form.getUnidadeSuperiorId() != null && !form.getUnidadeSuperiorId().equals("")){
				unidadeSuperior = new UnidadeOrganizacional();
				unidadeSuperior.setId(new Integer(form.getUnidadeSuperiorId()));
				// passar cole��o de unidades por par�metro
				parametroInformado = true;
			}
			// Munic�pio
			String municipioId = null;
			if(form.getMunicipioId() != null && !form.getMunicipioId().equals("")){
				municipioId = form.getMunicipioId();
				parametroInformado = true;
			}
			// Bairro
			String bairroId = null;
			if(form.getBairroId() != null && !form.getBairroId().equals("")){
				bairroId = form.getBairroId();
				parametroInformado = true;
			}
			// Bairro �rea
			if(new Integer(form.getAreaBairroId()).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
				BairroArea bairroArea = new BairroArea();
				bairroArea.setId(new Integer(form.getAreaBairroId()));
				ra.setBairroArea(bairroArea);
				parametroInformado = true;
			}
			// Logradouro
			String logradouroId = null;
			if(form.getLogradouroId() != null && !form.getLogradouroId().equals("")){
				logradouroId = form.getLogradouroId();
				parametroInformado = true;
			}

			ra.setCodigoSituacao(new Short("1"));

			// Filtra Registro Atendimento
			if(parametroInformado){
				Collection<RegistroAtendimento> colecaoRegistroAtendimento = new ArrayList();

				filtroRA.setRegistroAtendimento(ra);
				filtroRA.setUnidadeAtual(unidadeAtual);
				filtroRA.setUnidadeSuperior(unidadeSuperior);
				filtroRA.setDataAtendimentoInicial(dataAtendimentoInicial);
				filtroRA.setDataAtendimentoFinal(dataAtendimentoFinal);
				filtroRA.setColecaoTipoSolicitacaoEspecificacao(colecaoSolicitacaoTipoEspecificacao);
				filtroRA.setColecaoTipoSolicitacao(colecaoSolicitacaoTipoSolicitacao);
				filtroRA.setMunicipioId(municipioId);
				filtroRA.setBairroId(bairroId);
				filtroRA.setLogradouroId(logradouroId);

				filtroRA.setNumeroPagina(new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO));

				colecaoRegistroAtendimento = fachada.filtrarRegistroAtendimento(filtroRA);

				if(colecaoRegistroAtendimento != null){
					// Carrega Cole��o
					Collection colecaoRAHelper = loadColecaoRAHelper(colecaoRegistroAtendimento, indicadorOSProgramada);

					sessao.removeAttribute("numeroOS");
					sessao.setAttribute("quantidadeRAHelper", new Integer(colecaoRAHelper.size()));
					sessao.setAttribute("colecaoRAHelper", colecaoRAHelper);

				}else{
					// Nenhum resultado
					throw new ActionServletException("atencao.pesquisa.nenhumresultado");
				}
			}else{
				throw new ActionServletException("atencao.filtrar_informar_um_filtro");
			}
		}
		return retorno;
	}

	/**
	 * Carrega cole��o de registro atendimento, situa��o abreviada e unidade atual no
	 * objeto facilitador
	 * 
	 * @param colecaoRegistroAtendimento
	 * @return
	 */
	private Collection loadColecaoRAHelper(Collection<RegistroAtendimento> colecaoRegistroAtendimento, String indicadorOSProgramada){

		Fachada fachada = Fachada.getInstancia();
		Collection colecaoRAHelper = new ArrayList();
		UnidadeOrganizacional unidadeAtual = null;
		ObterDescricaoSituacaoRAHelper situacao = null;
		RAFiltroHelper helper = null;
		for(Iterator iter = colecaoRegistroAtendimento.iterator(); iter.hasNext();){
			RegistroAtendimento registroAtendimento = (RegistroAtendimento) iter.next();

			situacao = fachada.obterDescricaoSituacaoRA(registroAtendimento.getId());
			Collection<OrdemServico> oSs = fachada.obterOSRA(registroAtendimento.getId());
			String programada = "N�O";

			if(!Util.isVazioOrNulo(oSs)){
				for(OrdemServico os : oSs){
					if(os.getSituacao() == 1){
						if(fachada.verificarExistenciaOSProgramada(os.getId())){
							programada = "SIM";
							break;
						}
					}
				}
			}

			unidadeAtual = fachada.obterUnidadeAtualRA(registroAtendimento.getId());
			helper = new RAFiltroHelper();
			helper.setRegistroAtendimento(registroAtendimento);
			helper.setOsProgramada(programada);
			helper.setUnidadeAtual(unidadeAtual);
			helper.setSituacao(situacao.getDescricaoAbreviadaSituacao());

			// Preenche a cole��o conforme op��o selecionada na tela
			if(!Util.isVazioOuBranco(indicadorOSProgramada) && indicadorOSProgramada.equals(ConstantesSistema.SIM.toString())){
				if(programada.equals("SIM")){
					colecaoRAHelper.add(helper);
				}
			}else if(!Util.isVazioOuBranco(indicadorOSProgramada) && indicadorOSProgramada.equals(ConstantesSistema.NAO.toString())){
				if(programada.equals("N�O")){
					colecaoRAHelper.add(helper);
				}
			}else{
				colecaoRAHelper.add(helper);
			}
		}
		return colecaoRAHelper;
	}
}
