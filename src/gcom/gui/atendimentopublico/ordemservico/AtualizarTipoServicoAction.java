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
 * GSANPCG
 * Virg�nia Melo
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
import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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
 * [UC0387] MANTER TIPO PERFIL SERVICO
 * [SB0001] Atualizar Tipo Perfil de Servi�o
 * 
 * @author Thiago Ten�rio
 * @date 01/11/2006
 * @author Virg�nia Melo
 * @date 11/12/2008
 *       Altera��es/Corre��es no [UC0412] para a v0.07
 * @author Saulo Lima
 * @date 31/07/2009
 *       Altera��o ao inserir os 'ServicoTipoAtividades'
 */
public class AtualizarTipoServicoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		AtualizarTipoServicoActionForm atualizarTipoServicoActionForm = (AtualizarTipoServicoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		String idTipoServico = atualizarTipoServicoActionForm.getIdTipoServico();
		String descricao = atualizarTipoServicoActionForm.getDescricao();
		String abreviada = atualizarTipoServicoActionForm.getAbreviada();
		String subgrupo = atualizarTipoServicoActionForm.getSubgrupo();
		String valor = atualizarTipoServicoActionForm.getValorServico();
		String pavimento = atualizarTipoServicoActionForm.getPavimento();
		String atualizacaoComercial = atualizarTipoServicoActionForm.getAtualizacaoComercial();
		String terceirizado = atualizarTipoServicoActionForm.getServicoTerceirizado();
		String fiscalizacao = atualizarTipoServicoActionForm.getIndicadorFiscalizacaoInfracao();
		String vistoria = atualizarTipoServicoActionForm.getIndicadorVistoria();
		String codigo = atualizarTipoServicoActionForm.getCodigoServico();
		String tempoMedio = atualizarTipoServicoActionForm.getTempoMedioExecucao();
		String debitoTipo = atualizarTipoServicoActionForm.getIdTipoDebito();
		String creditoTipo = atualizarTipoServicoActionForm.getIdTipoCredito();
		String perfilTipo = atualizarTipoServicoActionForm.getPerfilServico();
		String referenciaTipo = atualizarTipoServicoActionForm.getIdTipoServicoReferencia();
		String indicadorUso = atualizarTipoServicoActionForm.getIndicadorUso();
		String idPrioridadeServico = atualizarTipoServicoActionForm.getIdPrioridadeServico();

		String valorRemuneracao = atualizarTipoServicoActionForm.getValorRemuneracao();
		String percentualRemuneracao = atualizarTipoServicoActionForm.getPercentualRemuneracao();
		String prazoExecucao = atualizarTipoServicoActionForm.getPrazoExecucao();
		String tipoRemuneracao = atualizarTipoServicoActionForm.getTipoRemuneracao();

		String idOrdemServicoLayout = atualizarTipoServicoActionForm.getIdOrdemServicoLayout();
		String indicadorDeslocamento = atualizarTipoServicoActionForm.getIndicadorDeslocamento();
		String indicadorHorariosExecucao = atualizarTipoServicoActionForm.getIndicadorHorariosExecucao();
		String indicadorCausaOcorrencia = atualizarTipoServicoActionForm.getIndicadorCausaOcorrencia();
		String indicadorRedeRamalAgua = atualizarTipoServicoActionForm.getIndicadorRedeRamalAgua();
		String indicadorRedeRamalEsgoto = atualizarTipoServicoActionForm.getIndicadorRedeRamalEsgoto();
		String indicadorMaterial = atualizarTipoServicoActionForm.getIndicadorMaterial();
		String indicadorVala = atualizarTipoServicoActionForm.getIndicadorVala();
		String indicadorOrdemSeletiva = atualizarTipoServicoActionForm.getIndicadorOrdemSeletiva();
		String indicadorServicoCritico = atualizarTipoServicoActionForm.getIndicadorServicoCritico();

		String indicadorAtividadeUnica = atualizarTipoServicoActionForm.getIndicadorAtividadeUnica();
		Short indicadorGerarHistoricoImovel = atualizarTipoServicoActionForm.getIndicadorGerarHistoricoImovel();

		ServicoTipo servicoTipo = new ServicoTipo();

		servicoTipo.setId(Integer.valueOf(idTipoServico));
		servicoTipo.setDescricao(descricao);
		servicoTipo.setDescricaoAbreviada(abreviada);

		ServicoTipoSubgrupo servicoTipoSubgrupo = new ServicoTipoSubgrupo();
		servicoTipoSubgrupo.setId(Integer.valueOf(subgrupo));
		servicoTipo.setServicoTipoSubgrupo(servicoTipoSubgrupo);

		if(valor != null && !valor.trim().equals("")){
			servicoTipo.setValor(Util.formatarMoedaRealparaBigDecimal(valor));
		}

		servicoTipo.setIndicadorPavimento(Short.valueOf(pavimento));
		servicoTipo.setIndicadorAtualizaComercial(Short.valueOf(atualizacaoComercial));
		servicoTipo.setIndicadorTerceirizado(Short.valueOf(terceirizado));
		servicoTipo.setIndicadorFiscalizacaoInfracao(Short.valueOf(fiscalizacao));
		servicoTipo.setIndicadorVistoria(Short.valueOf(vistoria));
		servicoTipo.setIndicadorFiscalizacaoInfracao(Short.valueOf(fiscalizacao));
		servicoTipo.setCodigoServicoTipo(codigo);
		servicoTipo.setTempoMedioExecucao(Short.valueOf(tempoMedio));

		ServicoTipoPrioridade servicoTipoPrioridade = new ServicoTipoPrioridade();
		servicoTipoPrioridade.setId(Integer.valueOf(idPrioridadeServico));
		servicoTipo.setServicoTipoPrioridade(servicoTipoPrioridade);

		OrdemServicoLayout ordemServicoLayout = new OrdemServicoLayout();
		ordemServicoLayout.setId(Integer.parseInt(idOrdemServicoLayout));
		servicoTipo.setOrdemServicoLayout(ordemServicoLayout);

		servicoTipo.setIndicadorDeslocamento(Integer.parseInt(indicadorDeslocamento));
		servicoTipo.setIndicadorHorariosExecucao(Integer.parseInt(indicadorHorariosExecucao));
		servicoTipo.setIndicadorCausaOcorrencia(Integer.parseInt(indicadorCausaOcorrencia));
		servicoTipo.setIndicadorRedeRamalAgua(Integer.parseInt(indicadorRedeRamalAgua));
		servicoTipo.setIndicadorRedeRamalEsgoto(Integer.parseInt(indicadorRedeRamalEsgoto));
		servicoTipo.setIndicadorMaterial(Integer.parseInt(indicadorMaterial));
		servicoTipo.setIndicadorVala(Integer.parseInt(indicadorVala));
		servicoTipo.setIndicadorOrdemSeletiva(Integer.parseInt(indicadorOrdemSeletiva));
		servicoTipo.setIndicadorServicoCritico(Integer.parseInt(indicadorServicoCritico));
		servicoTipo.setIndicadorGerarHistoricoImovel(indicadorGerarHistoricoImovel);

		// Verificando se tipo debito informado � v�lido
		if(debitoTipo != null && !debitoTipo.equals("")){
			DebitoTipo debitoTipoObj = new DebitoTipo();
			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();

			filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, debitoTipo));
			filtroDebitoTipo
							.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection debitosTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

			if(debitosTipo != null && !debitosTipo.isEmpty()){

				// foi encontrado
				Iterator debitoTipoIterator = debitosTipo.iterator();

				debitoTipoObj = (DebitoTipo) debitoTipoIterator.next();
				servicoTipo.setDebitoTipo(debitoTipoObj);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de D�bito");
			}
		}

		if(creditoTipo != null && !creditoTipo.trim().equals("-1")){
			CreditoTipo creditoTipoObj = new CreditoTipo();
			creditoTipoObj.setId(Integer.valueOf(creditoTipo));
			servicoTipo.setCreditoTipo(creditoTipoObj);
		}

		// Verificando se o PerfilTipo � v�lido
		if(perfilTipo != null && !perfilTipo.equals("")){
			ServicoPerfilTipo perfilTipoObj = new ServicoPerfilTipo();
			FiltroServicoPerfilTipo filtroPerfilTipo = new FiltroServicoPerfilTipo();

			filtroPerfilTipo.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.ID, perfilTipo));
			filtroPerfilTipo.adicionarParametro(new ParametroSimples(FiltroServicoPerfilTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoPerfilTipo = fachada.pesquisar(filtroPerfilTipo, ServicoPerfilTipo.class.getName());

			if(colecaoPerfilTipo != null && !colecaoPerfilTipo.isEmpty()){

				// O servicoPerfilTipo foi encontrado
				Iterator perfilTipoIterator = colecaoPerfilTipo.iterator();

				perfilTipoObj = (ServicoPerfilTipo) perfilTipoIterator.next();
				servicoTipo.setServicoPerfilTipo(perfilTipoObj);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Perfil do Tipo de Servi�o");
			}
		}

		// Verificando se referenciaTipo � v�lida
		if(referenciaTipo != null && !referenciaTipo.equals("")){
			ServicoTipoReferencia referenciaTipoObj = new ServicoTipoReferencia();
			FiltroServicoTipoReferencia filtroReferenciaTipo = new FiltroServicoTipoReferencia();

			filtroReferenciaTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipoReferencia.ID, referenciaTipo));
			filtroReferenciaTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipoReferencia.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoReferenciaTipo = fachada.pesquisar(filtroReferenciaTipo, ServicoTipoReferencia.class.getName());

			if(colecaoReferenciaTipo != null && !colecaoReferenciaTipo.isEmpty()){

				// ReferenciaTipo foi encontrado
				Iterator referenciaTipoIterator = colecaoReferenciaTipo.iterator();

				referenciaTipoObj = (ServicoTipoReferencia) referenciaTipoIterator.next();
				servicoTipo.setServicoTipoReferencia(referenciaTipoObj);

			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de Servi�o de Refer�ncia");
			}
		}

		servicoTipo.setIndicadorUso(Short.valueOf(indicadorUso));
		servicoTipo.setUltimaAlteracao(new Date());

		if(valorRemuneracao != null && !valorRemuneracao.trim().equals("")){
			servicoTipo.setValorRemuneracao(Util.formatarMoedaRealparaBigDecimal(valorRemuneracao));
		}else{
			servicoTipo.setValorRemuneracao(null);
		}

		if(percentualRemuneracao != null && !percentualRemuneracao.trim().equals("")){
			servicoTipo.setPercentualRemuneracao(Util.formatarMoedaRealparaBigDecimal(percentualRemuneracao));
		}else{
			servicoTipo.setPercentualRemuneracao(null);
		}

		if(prazoExecucao != null && !prazoExecucao.equals("")){
			servicoTipo.setPrazoExecucao(Integer.valueOf(prazoExecucao));
		}else{
			servicoTipo.setPrazoExecucao(null);
		}

		if(tipoRemuneracao != null && !tipoRemuneracao.equals("")){
			servicoTipo.setTipoRemuneracao(Short.parseShort(tipoRemuneracao));
		}

		if(indicadorAtividadeUnica != null && !indicadorAtividadeUnica.equals("1")){
			Collection colecaoServicoTipoAtividade = (Collection) sessao.getAttribute("colecaoServicoTipoAtividade");
			if(colecaoServicoTipoAtividade != null && !colecaoServicoTipoAtividade.isEmpty()){
				servicoTipo.setServicoTipoAtividades(colecaoServicoTipoAtividade);
			}else{
				throw new ActionServletException("atencao.informe_campo", null,
								"Atividades para Tipo de Servi�os com atividades n�o �nicas");
			}
		}

		if(sessao.getAttribute("colecaoServicoTipoMaterial") != null){
			servicoTipo.setServicoTipoMateriais((Collection) sessao.getAttribute("colecaoServicoTipoMaterial"));

		}

		if(sessao.getAttribute("colecaoServicoAssociado") != null){
			servicoTipo.setServicosAssociados((Collection) sessao.getAttribute("colecaoServicoAssociado"));
		}

			Collection<ServicoTipoTramite> colecaoServicoTipoTramite = (Collection<ServicoTipoTramite>) sessao
							.getAttribute("colecaoServicoTipoTramite");

			if(!Util.isVazioOrNulo(colecaoServicoTipoTramite)){
				servicoTipo.setServicosTipoTramite(colecaoServicoTipoTramite);
			}
		// Altera��o do caso de uso UC0412, OC1123944
		// else{
		// // [FS0019] - Criticar cole��o vazia
		// throw new ActionServletException("atencao.informe.configuracao.destino.tramite");
		// }

		if(servicoTipo.getIndicadorGerarHistoricoImovel() == null){

			Short valorPadrao = 2;

			servicoTipo.setIndicadorGerarHistoricoImovel(valorPadrao);

		}

		// OC1213341 - Inserir cole��o de Servi�os tipos e valores por Localidade
		Collection colecaoServicoTipoValorLocalidade = atualizarTipoServicoActionForm.getServicoTipoValorLocalidade();

		// Recuperando cole��o de servi�o tipo valor localidade
		FiltroServicoTipoValorLocalidade filtroServicoTipoValorLocalidade = new FiltroServicoTipoValorLocalidade();
		filtroServicoTipoValorLocalidade.adicionarParametro(new ParametroSimples(FiltroServicoTipoValorLocalidade.SERVICO_TIPO_ID,
						servicoTipo.getId()));

		Collection colecaoServicoTipoValorLocalidadeNaBase = fachada.pesquisar(filtroServicoTipoValorLocalidade,
						ServicoTipoValorLocalidade.class.getName());

		if(!Util.isVazioOrNulo(colecaoServicoTipoValorLocalidade)){

			checarServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidade, servicoTipo);

			// atualiza na base de dados Tipo Servi�o
			fachada.atualizarServicoTipo(servicoTipo);

			colecaoServicoTipoValorLocalidade = (Collection) inserirServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidade,
							servicoTipo, servicoTipo.getId());

			fachada.removerColecaoServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidadeNaBase);
			fachada.inserirColecaoServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidade);
		}else{
			// atualiza na base de dados Tipo Servi�o
			fachada.atualizarServicoTipo(servicoTipo);

			if(!Util.isVazioOrNulo(colecaoServicoTipoValorLocalidadeNaBase)){
				fachada.removerColecaoServicoTipoValorLocalidade(colecaoServicoTipoValorLocalidadeNaBase);
			}
		}
		// Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Tipo de Servi�o " + descricao + " atualizado com sucesso.",
						"Realizar outra Manuten��o de Tipo de Servi�o", "exibirFiltrarTipoServicoAction.do?menu=sim");

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