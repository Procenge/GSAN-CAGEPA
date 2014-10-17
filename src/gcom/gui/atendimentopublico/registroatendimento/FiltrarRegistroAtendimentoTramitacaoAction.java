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
	 * < <Descrição do método>>
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
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

			// Tipo Solicitação
			Collection<Integer> colecaoSolicitacaoTipoSolicitacao = new ArrayList();
			if(form.getTipoSolicitacao() != null && form.getTipoSolicitacao().length > 0){
				String[] tipoSolicitacao = form.getTipoSolicitacao();
				for(int i = 0; i < tipoSolicitacao.length; i++){
					if(new Integer(tipoSolicitacao[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
						colecaoSolicitacaoTipoSolicitacao.add(new Integer(tipoSolicitacao[i]));
						// passar a coleção de especificação por parâmetro
						parametroInformado = true;
					}
				}
			}

			// Tipo Especificação
			Collection<Integer> colecaoSolicitacaoTipoEspecificacao = new ArrayList();
			if(colecaoSolicitacaoTipoSolicitacao.size() < 2 && form.getEspecificacao() != null && form.getEspecificacao().length > 0){
				String[] tipoSolicitacaoEspecificacao = form.getEspecificacao();
				for(int i = 0; i < tipoSolicitacaoEspecificacao.length; i++){
					if(new Integer(tipoSolicitacaoEspecificacao[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO){
						colecaoSolicitacaoTipoEspecificacao.add(new Integer(tipoSolicitacaoEspecificacao[i]));
						// passar a coleção de especificação por parâmetro
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
				// passar as datas de atendimento por parâmetro
				parametroInformado = true;
			}

			// Unidade de Atual
			UnidadeOrganizacional unidadeAtual = null;
			if(form.getUnidadeAtualId() != null && !form.getUnidadeAtualId().equals("")){
				unidadeAtual = new UnidadeOrganizacional();
				unidadeAtual.setId(new Integer(form.getUnidadeAtualId()));
				// passar coleção de unidades por parâmetro
				parametroInformado = true;
			}
			// Unidade de Superior
			UnidadeOrganizacional unidadeSuperior = null;
			if(form.getUnidadeSuperiorId() != null && !form.getUnidadeSuperiorId().equals("")){
				unidadeSuperior = new UnidadeOrganizacional();
				unidadeSuperior.setId(new Integer(form.getUnidadeSuperiorId()));
				// passar coleção de unidades por parâmetro
				parametroInformado = true;
			}
			// Município
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
			// Bairro Área
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
					// Carrega Coleção
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
	 * Carrega coleção de registro atendimento, situação abreviada e unidade atual no
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
			String programada = "NÃO";

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

			// Preenche a coleção conforme opção selecionada na tela
			if(!Util.isVazioOuBranco(indicadorOSProgramada) && indicadorOSProgramada.equals(ConstantesSistema.SIM.toString())){
				if(programada.equals("SIM")){
					colecaoRAHelper.add(helper);
				}
			}else if(!Util.isVazioOuBranco(indicadorOSProgramada) && indicadorOSProgramada.equals(ConstantesSistema.NAO.toString())){
				if(programada.equals("NÃO")){
					colecaoRAHelper.add(helper);
				}
			}else{
				colecaoRAHelper.add(helper);
			}
		}
		return colecaoRAHelper;
	}
}
