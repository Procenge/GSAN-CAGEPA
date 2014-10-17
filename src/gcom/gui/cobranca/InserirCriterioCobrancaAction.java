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

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Processamento para inserir o crit�rio da cobran�a e as linhas do criterio da
 * cobran�a
 * 
 * @author S�vio Luiz
 * @date 03/05/2006
 * @author Cinthya
 * @date 21/10/2011
 *       adequa��o do caso de uso [UC0316] Inserir Crit�rio de Cobran�a
 */
public class InserirCriterioCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		CriterioCobrancaActionForm criterioCobrancaActionForm = (CriterioCobrancaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();
		Integer idCobrancaCriterio = null;

		// cria o objeto criterio cobran�a para ser inserido
		CobrancaCriterio cobrancaCriterio = new CobrancaCriterio();

		if(criterioCobrancaActionForm.getDescricaoCriterio() != null && !criterioCobrancaActionForm.getDescricaoCriterio().equals("")){
			cobrancaCriterio.setDescricaoCobrancaCriterio(criterioCobrancaActionForm.getDescricaoCriterio());
		}else{
			throw new ActionServletException("atencao.required", null, "Descri��o do Crit�rio de Cobran�a");
		}

		Date dataInicio = null;

		if(criterioCobrancaActionForm.getDataInicioVigencia() != null && !criterioCobrancaActionForm.getDataInicioVigencia().equals("")){
			String dataInicioVigencia = criterioCobrancaActionForm.getDataInicioVigencia();
			if(Util.validarDiaMesAno(dataInicioVigencia)){
				throw new ActionServletException("atencao.data.inicio.Vigencia.invalida");
			}

			dataInicio = Util.converteStringParaDate(dataInicioVigencia);
			Date dataAtualSemHora = Util.formatarDataSemHora(new Date());
			if(dataInicio.before(dataAtualSemHora)){
				String dataAtual = Util.formatarData(new Date());
				throw new ActionServletException("atencao.data.inicio.nao.superior.data.corrente", null, dataAtual);
			}

			cobrancaCriterio.setDataInicioVigencia(dataInicio);
		}else{
			throw new ActionServletException("atencao.required", null, "Data de In�cio da Vig�ncia");
		}

		if(criterioCobrancaActionForm.getNumeroAnoContaAntiga() != null && !criterioCobrancaActionForm.getNumeroAnoContaAntiga().equals("")){
			boolean valorNaoNumerico = Util.validarValorNaoNumerico(criterioCobrancaActionForm.getNumeroAnoContaAntiga());
			if(!valorNaoNumerico){
				cobrancaCriterio.setNumeroContaAntiga(Short.valueOf(criterioCobrancaActionForm.getNumeroAnoContaAntiga()));
			}else{
				throw new ActionServletException("atencao.integer", null, "N�mero de Anos para Determinar Conta Antiga");
			}
		}else{
			throw new ActionServletException("atencao.required", null, "N�mero Ano Conta Antiga");
		}

		if(criterioCobrancaActionForm.getValorLimitePrioridade() != null
						&& !criterioCobrancaActionForm.getValorLimitePrioridade().equalsIgnoreCase("")){
			BigDecimal valorLimitePrioridade = Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm.getValorLimitePrioridade());
			cobrancaCriterio.setValorLimitePrioridade(valorLimitePrioridade);
		}else{
			throw new ActionServletException("atencao.required", null, "Valor do Limite da Prioridade");
		}

		if(criterioCobrancaActionForm.getPercentualValorMinimoPagoParceladoCancelado() != null
						&& !criterioCobrancaActionForm.getPercentualValorMinimoPagoParceladoCancelado().equals("")){
			BigDecimal percentualValorMinimoPagoParceladoCancelado = Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm
							.getPercentualValorMinimoPagoParceladoCancelado());
			cobrancaCriterio.setPercentualValorMinimoPagoParceladoCancelado(percentualValorMinimoPagoParceladoCancelado);
		}else{
			throw new ActionServletException("atencao.required", null, "Percentual Valor");
		}

		if(criterioCobrancaActionForm.getPercentualQuantidadeMinimoPagoParceladoCancelado() != null
						&& !criterioCobrancaActionForm.getPercentualQuantidadeMinimoPagoParceladoCancelado().equals("")){
			BigDecimal percentualQuantidadeMinimoPagoParceladoCancelado = Util.formatarMoedaRealparaBigDecimal(criterioCobrancaActionForm
							.getPercentualQuantidadeMinimoPagoParceladoCancelado());
			cobrancaCriterio.setPercentualQuantidadeMinimoPagoParceladoCancelado(percentualQuantidadeMinimoPagoParceladoCancelado);
		}else{
			throw new ActionServletException("atencao.required", null, "Percentual Quantidade");
		}

		if(criterioCobrancaActionForm.getQtdDiasCortado() != null && !criterioCobrancaActionForm.getQtdDiasCortado().equals("")){
			Integer qtdDiasCortado = Util.converterStringParaInteger(criterioCobrancaActionForm.getQtdDiasCortado());
			cobrancaCriterio.setQtdDiasCortado(qtdDiasCortado);
		}

		if(!Util.isVazioOuBranco(criterioCobrancaActionForm.getIndicadorConsiderarApenasDebitoTitularAtual())){

			cobrancaCriterio.setIndicadorConsiderarApenasDebitoTitularAtual(Short.valueOf(criterioCobrancaActionForm
							.getIndicadorConsiderarApenasDebitoTitularAtual()));
		}else{

			throw new ActionServletException("atencao.required", null, "Considerar apenas o D�bito do Titular Atual do Im�vel");
		}

		if(cobrancaCriterio.getIndicadorConsiderarApenasDebitoTitularAtual().equals(ConstantesSistema.NAO)
						|| Util.isVazioOuBranco(criterioCobrancaActionForm.getIdClienteRelacaoTipo())
						|| criterioCobrancaActionForm.getIdClienteRelacaoTipo().equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

			cobrancaCriterio.setClienteRelacaoTipo(null);
		}else{

			ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();
			clienteRelacaoTipo.setId(Util.obterInteger(criterioCobrancaActionForm.getIdClienteRelacaoTipo()));
			cobrancaCriterio.setClienteRelacaoTipo(clienteRelacaoTipo);
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelSitEspecial() != null
						&& !criterioCobrancaActionForm.getOpcaoAcaoImovelSitEspecial().equals("")){
			cobrancaCriterio
							.setIndicadorEmissaoImovelParalisacao(Short.valueOf(criterioCobrancaActionForm.getOpcaoAcaoImovelSitEspecial()));
		}else{
			throw new ActionServletException("atencao.required", null, "Emiss�o da A��o para Im�vel com Situa��o Especial de Cobran�a");
		}

		Short opcaoAcaoImovelSit = 0;

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelSit() != null && !criterioCobrancaActionForm.getOpcaoAcaoImovelSit().equals("")){
			cobrancaCriterio.setIndicadorEmissaoImovelSituacaoCobranca(Short.valueOf(criterioCobrancaActionForm.getOpcaoAcaoImovelSit()));
			opcaoAcaoImovelSit = Short.valueOf(criterioCobrancaActionForm.getOpcaoAcaoImovelSit());
		}else{
			throw new ActionServletException("atencao.required", null, "Emiss�o da A��o para Im�vel com Situa��o de Cobran�a");
		}

		if(criterioCobrancaActionForm.getOpcaoContasRevisao() != null && !criterioCobrancaActionForm.getOpcaoContasRevisao().equals("")){
			cobrancaCriterio.setIndicadorEmissaoContaRevisao(Short.valueOf(criterioCobrancaActionForm.getOpcaoAcaoImovelSitEspecial()));
		}else{
			throw new ActionServletException("atencao.required", null, "Considerar Contas em Revis�o");
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoMesConta() != null
						&& !criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoMesConta().equals("")){
			cobrancaCriterio
							.setIndicadorEmissaoDebitoContaMes(Short.valueOf(criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoMesConta()));
		}else{
			throw new ActionServletException("atencao.required", null, "Emiss�o da A��o para Im�vel com D�bito s� da Conta do M�s");
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoInquilinoDebitoMesConta() != null
						&& !criterioCobrancaActionForm.getOpcaoAcaoInquilinoDebitoMesConta().equals("")){
			cobrancaCriterio.setIndicadorEmissaoInquilinoDebitoContaMes(Short.valueOf(criterioCobrancaActionForm
							.getOpcaoAcaoInquilinoDebitoMesConta()));
		}else{
			throw new ActionServletException("atencao.required", null,
							"Emiss�o da A��o para Inquilino Com D�bito s� da Conta do M�s Independentemente do Valor da Conta");
		}

		if(criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoContasAntigas() != null
						&& !criterioCobrancaActionForm.getOpcaoAcaoImovelDebitoContasAntigas().equals("")){
			cobrancaCriterio.setIndicadorEmissaoDebitoContaAntiga(Short.valueOf(criterioCobrancaActionForm
							.getOpcaoAcaoImovelDebitoContasAntigas()));
		}else{
			throw new ActionServletException("atencao.required", null, "Emiss�o da A��o para Im�vel com D�bito s� de Contas Antigas");
		}

		if(criterioCobrancaActionForm.getComCpf() != null && !criterioCobrancaActionForm.getComCpf().equals("")){
			cobrancaCriterio.setIndicadorComCpf(Short.valueOf(criterioCobrancaActionForm.getComCpf()));
		}else{
			throw new ActionServletException("atencao.required", null, "Com CPF");
		}

		if(criterioCobrancaActionForm.getComTelefone() != null && !criterioCobrancaActionForm.getComTelefone().equals("")){
			cobrancaCriterio.setIndicadorComTelefone(Short.valueOf(criterioCobrancaActionForm.getComTelefone()));
		}else{
			throw new ActionServletException("atencao.required", null, "Com Telefone");
		}

		// Verificando se ha algum item de situa��o de cobran�a selecionado, caso nao, selecionar
		// tudo
		if(criterioCobrancaActionForm.getIdsCobrancaSituacao() != null && criterioCobrancaActionForm.getIdsCobrancaSituacao().length > 0){

			Collection criteriosSituacaoCobranca = new ArrayList();

			for(int i = 0; i < criterioCobrancaActionForm.getIdsCobrancaSituacao().length; i++){
				CriterioSituacaoCobranca csc = new CriterioSituacaoCobranca();
				CriterioSituacaoCobrancaPK cscPK = new CriterioSituacaoCobrancaPK();
				CobrancaSituacao cobSit = new CobrancaSituacao();
				cobSit.setId(Integer.valueOf(criterioCobrancaActionForm.getIdsCobrancaSituacao()[i]));
				cscPK.setCobrancaSituacao(cobSit);
				csc.setComp_id(cscPK);
				criteriosSituacaoCobranca.add(csc);
			}
			cobrancaCriterio.setCriteriosSituacaoCobranca(new HashSet(criteriosSituacaoCobranca));

		}else if(opcaoAcaoImovelSit == 1 && sessao.getAttribute("colecaoCobrancaSituacao") != null
						&& !sessao.getAttribute("colecaoCobrancaSituacao").equals("")){
			Collection<CobrancaSituacao> colecaoCobrancaSituacao = (Collection<CobrancaSituacao>) sessao
							.getAttribute("colecaoCobrancaSituacao");
			Collection criteriosSituacaoCobranca = new ArrayList();
			for(CobrancaSituacao cobSit : colecaoCobrancaSituacao){
				CriterioSituacaoCobranca csc = new CriterioSituacaoCobranca();
				CriterioSituacaoCobrancaPK cscPK = new CriterioSituacaoCobrancaPK();
				cscPK.setCobrancaSituacao(cobSit);
				csc.setComp_id(cscPK);
				criteriosSituacaoCobranca.add(csc);
			}
			cobrancaCriterio.setCriteriosSituacaoCobranca(new HashSet(criteriosSituacaoCobranca));
		}

		// Verificando se ha algum item de situa��o ligacao de �gua selecionado, caso nao,
		// selecionar tudo
		if(criterioCobrancaActionForm.getIdsSituacaoLigacaoAgua() != null
						&& criterioCobrancaActionForm.getIdsSituacaoLigacaoAgua().length > 0){

			Collection criteriosSituacaoLigacaoAgua = new ArrayList();

			for(int i = 0; i < criterioCobrancaActionForm.getIdsSituacaoLigacaoAgua().length; i++){
				CriterioSituacaoLigacaoAgua csla = new CriterioSituacaoLigacaoAgua();
				CriterioSituacaoLigacaoAguaPK cslaPK = new CriterioSituacaoLigacaoAguaPK();
				LigacaoAguaSituacao ligAguaSit = new LigacaoAguaSituacao();
				ligAguaSit.setId(Integer.valueOf(criterioCobrancaActionForm.getIdsSituacaoLigacaoAgua()[i]));
				cslaPK.setLigacaoAguaSituacao(ligAguaSit);
				csla.setComp_id(cslaPK);
				criteriosSituacaoLigacaoAgua.add(csla);
			}
			cobrancaCriterio.setCriteriosSituacaoLigacaoAgua(new HashSet(criteriosSituacaoLigacaoAgua));
		}else if(sessao.getAttribute("colecaoSituacaoLigacaoAgua") != null && !sessao.getAttribute("colecaoSituacaoLigacaoAgua").equals("")){

			Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = (Collection<LigacaoAguaSituacao>) sessao
							.getAttribute("colecaoSituacaoLigacaoAgua");

			Collection criteriosSituacaoLigacaoAgua = new ArrayList();

			for(LigacaoAguaSituacao ligAguaSit : colecaoLigacaoAguaSituacao){
				CriterioSituacaoLigacaoAgua csla = new CriterioSituacaoLigacaoAgua();
				CriterioSituacaoLigacaoAguaPK cslaPK = new CriterioSituacaoLigacaoAguaPK();
				cslaPK.setLigacaoAguaSituacao(ligAguaSit);
				csla.setComp_id(cslaPK);
				criteriosSituacaoLigacaoAgua.add(csla);

			}
			cobrancaCriterio.setCriteriosSituacaoLigacaoAgua(new HashSet(criteriosSituacaoLigacaoAgua));
		}

		// Verificando se ha algum item de situa��o ligacao de esgoto selecionado, caso nao,
		// selecionar tudo
		if(criterioCobrancaActionForm.getIdsSituacaoLigacaoEsgoto() != null
						&& criterioCobrancaActionForm.getIdsSituacaoLigacaoEsgoto().length > 0){

			Collection criteriosSituacaoLigacaoEsgoto = new ArrayList();

			for(int i = 0; i < criterioCobrancaActionForm.getIdsSituacaoLigacaoEsgoto().length; i++){
				CriterioSituacaoLigacaoEsgoto csle = new CriterioSituacaoLigacaoEsgoto();
				CriterioSituacaoLigacaoEsgotoPK cslePK = new CriterioSituacaoLigacaoEsgotoPK();
				LigacaoEsgotoSituacao ligEsgotoSit = new LigacaoEsgotoSituacao();
				ligEsgotoSit.setId(Integer.valueOf(criterioCobrancaActionForm.getIdsSituacaoLigacaoEsgoto()[i]));
				cslePK.setLigacaoEsgotoSituacao(ligEsgotoSit);
				csle.setComp_id(cslePK);
				criteriosSituacaoLigacaoEsgoto.add(csle);
			}
			cobrancaCriterio.setCriteriosSituacaoLigacaoEsgoto(new HashSet(criteriosSituacaoLigacaoEsgoto));
		}else if(sessao.getAttribute("colecaoSituacaoLigacaoEsgoto") != null
						&& !sessao.getAttribute("colecaoSituacaoLigacaoEsgoto").equals("")){

			Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = (Collection<LigacaoEsgotoSituacao>) sessao
							.getAttribute("colecaoSituacaoLigacaoEsgoto");

			Collection criteriosSituacaoLigacaoEsgoto = new ArrayList();

			for(LigacaoEsgotoSituacao ligEsgotoSit : colecaoLigacaoEsgotoSituacao){
				CriterioSituacaoLigacaoEsgoto csle = new CriterioSituacaoLigacaoEsgoto();
				CriterioSituacaoLigacaoEsgotoPK cslePK = new CriterioSituacaoLigacaoEsgotoPK();
				cslePK.setLigacaoEsgotoSituacao(ligEsgotoSit);
				csle.setComp_id(cslePK);
				criteriosSituacaoLigacaoEsgoto.add(csle);

			}
			cobrancaCriterio.setCriteriosSituacaoLigacaoEsgoto(new HashSet(criteriosSituacaoLigacaoEsgoto));
		}

		cobrancaCriterio.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		cobrancaCriterio.setUltimaAlteracao(new Date());
		Collection colecaoCobrancaCriterioLinha = (Collection) sessao.getAttribute("colecaoCobrancaCriterioLinha");

		if(colecaoCobrancaCriterioLinha != null && !colecaoCobrancaCriterioLinha.isEmpty()){
			cobrancaCriterio.setCobrancaCriterioLinhas(new HashSet(colecaoCobrancaCriterioLinha));

			idCobrancaCriterio = fachada.inserirCobrancaCriterio(cobrancaCriterio, this.getUsuarioLogado(httpServletRequest));
		}else{
			throw new ActionServletException("atencao.informar.linha.criterio.cobranca");
		}

		sessao.removeAttribute("colecaoCobrancaCriterioLinha");

		montarPaginaSucesso(httpServletRequest, "Crit�rio de Cobran�a " + idCobrancaCriterio + " inserido com sucesso.",
						"Inserir outro Crit�rio de Cobran�a", "exibirInserirCriterioCobrancaAction.do?menu=sim",
						"exibirAtualizarCriterioCobrancaAction.do?idRegistroAtualizacao=" + idCobrancaCriterio + "&retornoFiltrar=1",
						"Atualizar Crit�rio de Cobran�a inserido");
		return retorno;
	}

}
