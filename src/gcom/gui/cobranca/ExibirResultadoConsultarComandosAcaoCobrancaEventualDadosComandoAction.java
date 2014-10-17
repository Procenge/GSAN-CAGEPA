/**
 * 
 */
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

import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeImovel;
import gcom.cobranca.CobrancaAtividade;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeImovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.cobranca.RelatorioImoveisAcaoCobranca;
import gcom.seguranca.acesso.Funcionalidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesAplicacao;
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

/**
 * Permite consultar comandos de a��o de cobran�a
 * [UC0325] Consultar Comandos de A��o de Conbran�a
 * 
 * @author Rafael Santos
 * @since 11/05/2006
 * @author Virg�nia Melo
 * @date 11/11/2008
 *       Altera��es para a v0.06
 */
public class ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirComandosAcaoCobrancaEventualDadosComando");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String idCobrancaAcaoAtividadeComando = httpServletRequest.getParameter("idCobrancaAcaoAtividadeEventual");
		String idCobrancaAcaoAtividadeEventualListaImoveis = httpServletRequest.getParameter("idCobrancaAcaoAtividadeEventualListaImoveis");

		if(!Util.isVazioOuBranco(idCobrancaAcaoAtividadeEventualListaImoveis)){
			FiltroCobrancaAcaoAtividadeImovel filtroCobrancaAcaoAtividadeImovel = new FiltroCobrancaAcaoAtividadeImovel();
			filtroCobrancaAcaoAtividadeImovel.setCampoDistinct(FiltroCobrancaAcaoAtividadeImovel.IMOVEL_ID);
			filtroCobrancaAcaoAtividadeImovel.adicionarParametro(new ParametroSimples(
							FiltroCobrancaAcaoAtividadeImovel.COBRANCA_ACAO_ATIVIDADE_COMANDO_ID,
							idCobrancaAcaoAtividadeEventualListaImoveis));

			Collection colCobrancaAcaoAtvImovel = getFachada().pesquisar(filtroCobrancaAcaoAtividadeImovel,
							CobrancaAcaoAtividadeImovel.class.getName());

			if(!Util.isVazioOrNulo(colCobrancaAcaoAtvImovel)){
				Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

				RelatorioImoveisAcaoCobranca relatorio = new RelatorioImoveisAcaoCobranca(usuarioLogado);
				relatorio.setIdFuncionalidadeIniciada(Funcionalidade.GERAR_ARQUIVO_IMOVEIS_COBRANCA);
				relatorio.addParametro("idCobrancaAcaoAtividadeComando", Integer.valueOf(idCobrancaAcaoAtividadeEventualListaImoveis));
				relatorio.addParametro("idCobrancaAcaoAtividadeCronograma", ConstantesSistema.NUMERO_NAO_INFORMADO);
				relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_ZIP);

				fachada.iniciarProcessoRelatorio(relatorio);

				retorno = actionMapping.findForward("telaSucessoPopup");
				montarPaginaSucesso(httpServletRequest, ConstantesAplicacao.get("atencao.execucao.relatorio.batch"), "", "");

				return retorno;
			}else{
				throw new ActionServletException("atencao.nao_ha_imovel_para_cobranca", null, "");
			}
		}

		httpServletRequest.setAttribute("idCobrancaAcaoAtividadeEventualListaImoveis", idCobrancaAcaoAtividadeComando);

		CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando = fachada
						.obterCobrancaAcaoAtividadeComando(idCobrancaAcaoAtividadeComando);

		ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm = (ExibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm) actionForm;
		
		exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.limpar();
		exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm
						.setIdCobrancaAcaoAtividadeComando(idCobrancaAcaoAtividadeComando);

		// cobran�a acao
		if(cobrancaAcaoAtividadeComando.getCobrancaAcao() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setAcaoCobranca(cobrancaAcaoAtividadeComando
							.getCobrancaAcao().getDescricaoCobrancaAcao());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setAcaoCobranca("");
		}
		// atividade cobranca
		if(cobrancaAcaoAtividadeComando.getCobrancaAtividade() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setAtividadeCobranca(cobrancaAcaoAtividadeComando
							.getCobrancaAtividade().getDescricaoCobrancaAtividade());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setAcaoCobranca("");
		}
		// criterio utilizado
		if(cobrancaAcaoAtividadeComando.getIndicadorCriterio() != null){
			if(cobrancaAcaoAtividadeComando.getIndicadorCriterio().shortValue() == (short) 1){
				exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setCriterioUtilizado("Rota");
			}else{
				exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setCriterioUtilizado("Comando");
			}

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setCriterioUtilizado("");
		}
		// criterio
		if(cobrancaAcaoAtividadeComando.getIndicadorCriterio() != null
						&& cobrancaAcaoAtividadeComando.getIndicadorCriterio().shortValue() == (short) 2){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setCriterio(cobrancaAcaoAtividadeComando
							.getCobrancaCriterio().getDescricaoCobrancaCriterio());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setCriterio("");
		}

		// grupo cobranca
		if(cobrancaAcaoAtividadeComando.getCobrancaGrupo() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setGrupoCobranca(cobrancaAcaoAtividadeComando
							.getCobrancaGrupo().getDescricao());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setGrupoCobranca("");
		}

		// dados de localiza��o geografica
		// gerencia regional
		if(cobrancaAcaoAtividadeComando.getGerenciaRegional() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setGerenciaRegional(cobrancaAcaoAtividadeComando
							.getGerenciaRegional().getNome());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setGerenciaRegional("");
		}

		// dados de localiza��o geografica
		// unidade negocio
		if(cobrancaAcaoAtividadeComando.getUnidadeNegocio() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setUnidadeNegocio(cobrancaAcaoAtividadeComando
							.getUnidadeNegocio().getNome());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setUnidadeNegocio("");
		}

		// localidade inicial
		if(cobrancaAcaoAtividadeComando.getLocalidadeInicial() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setLocalidadeInicial(cobrancaAcaoAtividadeComando
							.getLocalidadeInicial().getId().toString());
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm
							.setDescricaoLocalidadeInicial(cobrancaAcaoAtividadeComando.getLocalidadeInicial().getDescricao());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setLocalidadeInicial("");
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDescricaoLocalidadeInicial("");
		}
		// localidade final
		if(cobrancaAcaoAtividadeComando.getLocalidadeFinal() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setLocalidadeFinal(cobrancaAcaoAtividadeComando
							.getLocalidadeFinal().getId().toString());
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm
							.setDescricaoLocalidadeFinal(cobrancaAcaoAtividadeComando.getLocalidadeFinal().getDescricao());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setLocalidadeFinal("");
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDescricaoLocalidadeFinal("");
		}

		// setor comericial final
		if(cobrancaAcaoAtividadeComando.getCodigoSetorComercialFinal() != null){
			SetorComercial setorComercial = fachada.obterSetorComercialLocalidade(cobrancaAcaoAtividadeComando.getLocalidadeInicial()
							.getId().toString(), cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial().toString());

			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setSetorComercialFinal(cobrancaAcaoAtividadeComando
							.getCodigoSetorComercialFinal().toString());
			if(setorComercial != null){
				exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDescricaoSetorComercialInicial(setorComercial
								.getDescricao());

			}
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setSetorComercialFinal("");
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDescricaoSetorComercialInicial("");
		}
		// setor comercial inicial
		if(cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial() != null){
			SetorComercial setorComercial = fachada.obterSetorComercialLocalidade(cobrancaAcaoAtividadeComando.getLocalidadeFinal().getId()
							.toString(), cobrancaAcaoAtividadeComando.getCodigoSetorComercialFinal().toString());

			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm
							.setSetorComercialInicial(cobrancaAcaoAtividadeComando.getCodigoSetorComercialInicial().toString());
			if(setorComercial != null){
				exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDescricaoSetorComercialFinal(setorComercial
								.getDescricao());

			}

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setSetorComercialInicial("");
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDescricaoSetorComercialFinal("");
		}

		// quadra inicial
		if(cobrancaAcaoAtividadeComando.getNumeroQuadraInicial() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setQuadraInicial(cobrancaAcaoAtividadeComando
							.getNumeroQuadraInicial().toString());
		}
		// rota final
		if(cobrancaAcaoAtividadeComando.getNumeroQuadraFinal() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setQuadraFinal(cobrancaAcaoAtividadeComando
							.getNumeroQuadraFinal().toString());
		}

		// rota inicial
		if(cobrancaAcaoAtividadeComando.getRotaInicial() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setRotaInicial(cobrancaAcaoAtividadeComando
							.getRotaInicial().getCodigo().toString());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setRotaInicial("");
		}
		// rota final
		if(cobrancaAcaoAtividadeComando.getRotaFinal() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setRotaFinal(cobrancaAcaoAtividadeComando
							.getRotaFinal().getCodigo().toString());
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setRotaFinal("");
		}
		// cliente
		if(cobrancaAcaoAtividadeComando.getCliente() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setCliente(cobrancaAcaoAtividadeComando.getCliente()
							.getNome());

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setCliente("");
		}
		// cliente relacao tipo
		if(cobrancaAcaoAtividadeComando.getClienteRelacaoTipo() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setTipoRelacaoCliente(cobrancaAcaoAtividadeComando
							.getClienteRelacaoTipo().getDescricao());

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setTipoRelacaoCliente("");
		}

		// data e hora do comando
		if(cobrancaAcaoAtividadeComando.getComando() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDataComando(Util
							.formatarData(cobrancaAcaoAtividadeComando.getComando()));
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setHoraComando(Util
							.formatarHoraSemData(cobrancaAcaoAtividadeComando.getComando()));

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDataComando("");
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setHoraComando("");
		}

		// data e hora de realizacao
		if(cobrancaAcaoAtividadeComando.getRealizacao() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDataRealizacao(Util
							.formatarData(cobrancaAcaoAtividadeComando.getRealizacao()));
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setHoraRealizacao(Util
							.formatarHoraSemData(cobrancaAcaoAtividadeComando.getRealizacao()));
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setDataRealizacao("");
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setHoraRealizacao("");
		}

		// periodo de refernecia das contas inicial
		if(cobrancaAcaoAtividadeComando.getAnoMesReferenciaContaInicial() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoReferenciaContasInicial(Util
							.formatarAnoMesParaMesAno(cobrancaAcaoAtividadeComando.getAnoMesReferenciaContaInicial().intValue()));
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoReferenciaContasInicial("");
		}

		// periodo de refernecia das contas final
		if(cobrancaAcaoAtividadeComando.getAnoMesReferenciaContaFinal() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoReferenciaContasFinal(Util
							.formatarAnoMesParaMesAno(cobrancaAcaoAtividadeComando.getAnoMesReferenciaContaFinal().intValue()));

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoReferenciaContasFinal("");
		}

		// periodo de vencimentos das contas inicial
		if(cobrancaAcaoAtividadeComando.getDataVencimentoContaInicial() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoVencimentoContasInicial(Util
							.formatarData(cobrancaAcaoAtividadeComando.getDataVencimentoContaInicial()));
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoVencimentoContasInicial("");
		}

		// periodo de vencimentos das contas final
		if(cobrancaAcaoAtividadeComando.getDataVencimentoContaFinal() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoVencimentoContasFinal(Util
							.formatarData(cobrancaAcaoAtividadeComando.getDataVencimentoContaFinal()));
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setPeriodoVencimentoContasFinal("");
		}

		// valor dos documentos
		if(cobrancaAcaoAtividadeComando.getValorDocumentos() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setValorDocumentos(Util
							.formatarMoedaReal(cobrancaAcaoAtividadeComando.getValorDocumentos()));

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setValorDocumentos("");
		}
		// quantidade de documentos
		if(cobrancaAcaoAtividadeComando.getQuantidadeDocumentos() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setQuantidadeDocumentos(cobrancaAcaoAtividadeComando
							.getQuantidadeDocumentos().toString());

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setQuantidadeDocumentos("");
		}
		// quantidade de itens dos documentos
		if(cobrancaAcaoAtividadeComando.getQuantidadeItensCobrados() != null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm
							.setQuantidedeItensDocumentos(cobrancaAcaoAtividadeComando.getQuantidadeItensCobrados().toString());

		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setQuantidedeItensDocumentos("");
		}
		// situacao do comando
		if(cobrancaAcaoAtividadeComando.getRealizacao() == null){
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setSituacaoComando("N�o Realizado");
		}else{
			exibirResultadoConsultarComandosAcaoCobrancaEventualDadosComandoActionForm.setSituacaoComando("Realizado");
			if(cobrancaAcaoAtividadeComando.getCobrancaAtividade().getId().equals(CobrancaAtividade.EMITIR)){
				httpServletRequest.setAttribute("emitir", "sim");
			}
		}

		sessao.removeAttribute("cobrancaAcaoAtividadeCronograma");
		return retorno;
	}

}
