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

package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.FiltroCobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.ComparatorChain;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Pre- processamento para inserir o criterio da cobran�a
 * 
 * @author S�vio Luiz
 * @date 17/04/2006
 */
public class ExibirInserirCriterioCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirCriterioCobranca");

		HttpSession sessao = httpServletRequest.getSession(false);

		CriterioCobrancaActionForm criterioCobrancaActionForm = (CriterioCobrancaActionForm) actionForm;
		if(httpServletRequest.getParameter("limpaSessao") != null && !httpServletRequest.getParameter("limpaSessao").equals("")){

			criterioCobrancaActionForm.setDescricaoCriterio("");
			criterioCobrancaActionForm.setQtdDiasCortado("");

			criterioCobrancaActionForm.setIdsCobrancaSituacao(null);
			criterioCobrancaActionForm.setIdsSituacaoLigacaoAgua(null);
			criterioCobrancaActionForm.setIdsSituacaoLigacaoEsgoto(null);

			sessao.removeAttribute("colecaoCobrancaCriterioLinha");

		}

		if(criterioCobrancaActionForm.getDataInicioVigencia() == null || criterioCobrancaActionForm.getDataInicioVigencia().equals("")){
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String dataInicioVigencia = formatter.format(new Date());

			criterioCobrancaActionForm.setDataInicioVigencia(dataInicioVigencia);
		}

		if(criterioCobrancaActionForm.getNumeroAnoContaAntiga() == null || criterioCobrancaActionForm.getNumeroAnoContaAntiga().equals("")){
			criterioCobrancaActionForm.setNumeroAnoContaAntiga("99");
		}

		if(criterioCobrancaActionForm.getValorLimitePrioridade() == null
						|| criterioCobrancaActionForm.getValorLimitePrioridade().equalsIgnoreCase("")){
			criterioCobrancaActionForm.setValorLimitePrioridade("0");
		}

		if(criterioCobrancaActionForm.getPercentualValorMinimoPagoParceladoCancelado() == null
						|| criterioCobrancaActionForm.getPercentualValorMinimoPagoParceladoCancelado().equals("")){
			criterioCobrancaActionForm.setPercentualValorMinimoPagoParceladoCancelado("100");
		}

		if(criterioCobrancaActionForm.getPercentualQuantidadeMinimoPagoParceladoCancelado() == null
						|| criterioCobrancaActionForm.getPercentualQuantidadeMinimoPagoParceladoCancelado().equals("")){
			criterioCobrancaActionForm.setPercentualQuantidadeMinimoPagoParceladoCancelado("100");
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelSitEspecial() == null
						|| criterioCobrancaActionForm.getOpcaoAcaoImovelSitEspecial().equals("")){
			criterioCobrancaActionForm.setOpcaoAcaoImovelSitEspecial("2");
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelSit() == null || criterioCobrancaActionForm.getOpcaoAcaoImovelSit().equals("")){
			criterioCobrancaActionForm.setOpcaoAcaoImovelSit("2");
		}

		if(criterioCobrancaActionForm.getOpcaoContasRevisao() == null || criterioCobrancaActionForm.getOpcaoContasRevisao().equals("")){
			criterioCobrancaActionForm.setOpcaoContasRevisao("2");
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoMesConta() == null
						|| criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoMesConta().equals("")){
			criterioCobrancaActionForm.setOpcaoAcaoImovelDebitoMesConta("2");
		}
		if(criterioCobrancaActionForm.getOpcaoAcaoInquilinoDebitoMesConta() == null
						|| criterioCobrancaActionForm.getOpcaoAcaoInquilinoDebitoMesConta().equals("")){
			criterioCobrancaActionForm.setOpcaoAcaoInquilinoDebitoMesConta("2");
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoContasAntigas() == null
						|| criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoContasAntigas().equals("")){
			criterioCobrancaActionForm.setOpcaoAcaoImovelDebitoContasAntigas("2");
		}

		if(criterioCobrancaActionForm.getComCpf() == null || criterioCobrancaActionForm.getComCpf().equals("")){
			criterioCobrancaActionForm.setComCpf("3");
		}

		if(criterioCobrancaActionForm.getComTelefone() == null || criterioCobrancaActionForm.getComTelefone().equals("")){
			criterioCobrancaActionForm.setComTelefone("3");
		}

		if(criterioCobrancaActionForm.getIndicadorConsiderarApenasDebitoTitularAtual() == null
						|| criterioCobrancaActionForm.getIndicadorConsiderarApenasDebitoTitularAtual().equals("")){

			criterioCobrancaActionForm.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO.toString());
		}

		Fachada fachada = Fachada.getInstancia();

		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();

		filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroClienteRelacaoTipo.setCampoOrderBy(FiltroClienteRelacaoTipo.DESCRICAO);
		List<ClienteRelacaoTipo> colecaoClienteRelacaoTipo = (List<ClienteRelacaoTipo>) fachada.pesquisar(filtroClienteRelacaoTipo,
						ClienteRelacaoTipo.class.getName());

		if(Util.isVazioOrNulo(colecaoClienteRelacaoTipo)){

			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Tipo da Rela��o do Cliente");
		}else{

			ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo("Cliente com Nome na Conta",
							ConstantesSistema.INDICADOR_USO_DESATIVO, new Date());
			clienteRelacaoTipo.setId(ConstantesSistema.NUMERO_NAO_INFORMADO);
			colecaoClienteRelacaoTipo.add(clienteRelacaoTipo);

			// Ordenar a cole��o por mais de um campo
			List sortFields = new ArrayList();
			sortFields.add(new BeanComparator("descricao"));

			ComparatorChain multiSort = new ComparatorChain(sortFields);
			Collections.sort(colecaoClienteRelacaoTipo, multiSort);

			sessao.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);
		}

		// consultar as situacoes de cobranca
		FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();

		filtroCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaSituacao.setCampoOrderBy(FiltroCobrancaSituacao.DESCRICAO);

		Collection colecaoCobrancaSituacao = fachada.pesquisar(filtroCobrancaSituacao, CobrancaSituacao.class.getName());
		sessao.setAttribute("colecaoCobrancaSituacao", colecaoCobrancaSituacao);

		// Verificando se ha algum item selecionado, caso nao, selecionar tudo
		/*
		 * if(criterioCobrancaActionForm.getIdsCobrancaSituacao() == null ||
		 * criterioCobrancaActionForm.getIdsCobrancaSituacao().length == 0){
		 * Iterator iterCobSit = colecaoCobrancaSituacao.iterator();
		 * String[] idsCobSit = new String[colecaoCobrancaSituacao.size()];
		 * int i = 0;
		 * while(iterCobSit.hasNext()){
		 * CobrancaSituacao cobSit = (CobrancaSituacao) iterCobSit.next();
		 * idsCobSit[i++] = cobSit.getId() + "";
		 * }
		 * criterioCobrancaActionForm.setIdsCobrancaSituacao(idsCobSit);
		 * }
		 */

		// consultar as situacoes de ligacao de agua
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

		Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());
		sessao.setAttribute("colecaoSituacaoLigacaoAgua", colecaoLigacaoAguaSituacao);

		// Verificando se ha algum item selecionado, caso nao, selecionar tudo
		/*
		 * if(criterioCobrancaActionForm.getIdsSituacaoLigacaoAgua() == null
		 * || criterioCobrancaActionForm.getIdsSituacaoLigacaoAgua().length == 0){
		 * Iterator iterLigAguaSit = colecaoLigacaoAguaSituacao.iterator();
		 * String[] idsLigAguaSit = new String[colecaoLigacaoAguaSituacao.size()];
		 * int i = 0;
		 * while(iterLigAguaSit.hasNext()){
		 * LigacaoAguaSituacao ligAguaSit = (LigacaoAguaSituacao) iterLigAguaSit.next();
		 * idsLigAguaSit[i++] = ligAguaSit.getId() + "";
		 * }
		 * criterioCobrancaActionForm.setIdsSituacaoLigacaoAgua(idsLigAguaSit);
		 * }
		 */

		// consultar as situacoes de ligacao de agua
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

		Collection colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());
		sessao.setAttribute("colecaoSituacaoLigacaoEsgoto", colecaoLigacaoEsgotoSituacao);

		// Verificando se ha algum item selecionado, caso nao, selecionar tudo
		/*
		 * if(criterioCobrancaActionForm.getIdsSituacaoLigacaoEsgoto() == null
		 * || criterioCobrancaActionForm.getIdsSituacaoLigacaoEsgoto().length == 0){
		 * Iterator iterLigEsgotoSit = colecaoLigacaoEsgotoSituacao.iterator();
		 * String[] idsLigEsgotoSit = new String[colecaoLigacaoEsgotoSituacao.size()];
		 * int i = 0;
		 * while(iterLigEsgotoSit.hasNext()){
		 * LigacaoEsgotoSituacao ligEsgotoSit = (LigacaoEsgotoSituacao) iterLigEsgotoSit.next();
		 * idsLigEsgotoSit[i++] = ligEsgotoSit.getId() + "";
		 * }
		 * criterioCobrancaActionForm.setIdsSituacaoLigacaoEsgoto(idsLigEsgotoSit);
		 * }
		 */

		return retorno;
	}
}
