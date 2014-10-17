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
 * Vitor Hora
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

package gcom.gui.cadastro.imovel;

import gcom.arrecadacao.ContratoDemandaConsumo;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.ImovelCadastroOcorrencia;
import gcom.faturamento.FiltroContratoDemandaConsumo;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0473] Consultar Imovel
 * 
 * @author Rafael Santos
 * @since 07/09/2006
 */
public class ExibirConsultarImovelDadosComplementaresAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("consultarImovelDadosComplementares");

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarImovelActionForm consultarImovelActionForm = (ConsultarImovelActionForm) actionForm;

		// id do imovel da aba dados cadastrais
		String idImovelDadosComplementares = consultarImovelActionForm.getIdImovelDadosComplementares();

		// id do imovel da aba dados cadastrais
		String limparForm = httpServletRequest.getParameter("limparForm");
		String indicadorNovo = httpServletRequest.getParameter("indicadorNovo");
		String idImovelPrincipalAba = null;

		if(sessao.getAttribute("idImovelPrincipalAba") != null){
			idImovelPrincipalAba = (String) sessao.getAttribute("idImovelPrincipalAba");
		}

		if(limparForm != null && !limparForm.equals("")){

			// limpar os dados
			httpServletRequest.setAttribute("idImovelDadosComplementaresNaoEncontrado", null);

			sessao.removeAttribute("imovelDadosComplementares");
			sessao.removeAttribute("colecaoVencimentosAlternativos");
			sessao.removeAttribute("colecaoSituacoesCobranca");
			sessao.removeAttribute("colecaoDebitosAutomaticos");
			sessao.removeAttribute("colecaoFaturamentosSituacaoHistorico");
			sessao.removeAttribute("colecaoCobrancasSituacaoHistorico");
			sessao.removeAttribute("idImovelPrincipalAba");
			sessao.removeAttribute("colecaoContratoDemandaConsumo");

			// Colocado por Raphael Rossiter em 13/01/2007
			sessao.removeAttribute("colecaoImovelCadastroOcorrencia");
			sessao.removeAttribute("colecaoImovelEloAnormalidade");

			consultarImovelActionForm.setIdImovelDadosComplementares(null);
			consultarImovelActionForm.setIdImovelDadosCadastrais(null);
			consultarImovelActionForm.setIdImovelAnaliseMedicaoConsumo(null);
			consultarImovelActionForm.setIdImovelHistoricoFaturamento(null);
			consultarImovelActionForm.setIdImovelDebitos(null);
			consultarImovelActionForm.setIdImovelPagamentos(null);
			consultarImovelActionForm.setIdImovelDevolucoesImovel(null);
			consultarImovelActionForm.setIdImovelDocumentosCobranca(null);
			consultarImovelActionForm.setIdImovelParcelamentosDebitos(null);
			consultarImovelActionForm.setIdImovelRegistroAtendimento(null);

			consultarImovelActionForm.setMatriculaImovelDadosComplementares(null);
			consultarImovelActionForm.setSituacaoAguaDadosComplementares(null);
			consultarImovelActionForm.setSituacaoEsgotoDadosComplementares(null);
			consultarImovelActionForm.setTarifaConsumoDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeRetificacoesDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeParcelamentosDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeReparcelamentoDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeReparcelamentoConsecutivosDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeCortes(null);
			consultarImovelActionForm.setQuantidadeCortesAdministrativos(null);
			consultarImovelActionForm.setQuantidadeSupressoes(null);
			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);
			consultarImovelActionForm.setDescricaoOcorrenciaDadosComplementares(null);
			consultarImovelActionForm.setIdFuncionario(null);
			consultarImovelActionForm.setNomeFuncionario(null);
			consultarImovelActionForm.setDescricaoAnormalidadeDadosComplementares(null);

		}else if((idImovelDadosComplementares != null && !idImovelDadosComplementares.equalsIgnoreCase(""))
						|| (idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase(""))){

			if(idImovelDadosComplementares != null && !idImovelDadosComplementares.equalsIgnoreCase("")){

				if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

					if(indicadorNovo != null && !indicadorNovo.equals("")){

						consultarImovelActionForm.setIdImovelDadosComplementares(idImovelDadosComplementares);

					}else if(!(idImovelDadosComplementares.equals(idImovelPrincipalAba))){

						consultarImovelActionForm.setIdImovelDadosComplementares(idImovelPrincipalAba);

						idImovelDadosComplementares = idImovelPrincipalAba;
					}

				}
			}else if(idImovelPrincipalAba != null && !idImovelPrincipalAba.equalsIgnoreCase("")){

				consultarImovelActionForm.setIdImovelDadosComplementares(idImovelPrincipalAba);
				idImovelDadosComplementares = idImovelPrincipalAba;
			}

			// verifica se o objeto imovel é o mesmo ja pesquisado, para não
			// precisar pesquisar mas.
			Imovel imovel = null;
			boolean imovelNovoPesquisado = false;
			if(sessao.getAttribute("imovelDadosComplementares") != null){

				imovel = (Imovel) sessao.getAttribute("imovelDadosComplementares");

				if(!(imovel.getId().toString().equals(idImovelDadosComplementares.trim()))){
					imovel = this.getFachada().consultarImovelDadosComplementares(Util.obterInteger(idImovelDadosComplementares.trim()));

					imovelNovoPesquisado = true;
				}

			}else{

				imovel = this.getFachada().consultarImovelDadosComplementares(Util.obterInteger(idImovelDadosComplementares.trim()));

				imovelNovoPesquisado = true;
			}

			if(imovel != null){

				sessao.setAttribute("imovelDadosComplementares", imovel);
				sessao.setAttribute("idImovelPrincipalAba", imovel.getId().toString());

				consultarImovelActionForm.setIdImovelDadosComplementares(imovel.getId().toString());

				if(imovel.getConsumoTarifa() != null && imovel.getDataValidadeTarifaTemporaria() != null){
					int comparacaoDatas = Util.compararData(imovel.getDataValidadeTarifaTemporaria(), new Date());

					if(comparacaoDatas != -1){
						httpServletRequest.setAttribute("exibirDataValidadeTarifaTemporaria", true);
					}
				}

				// caso o imovel pesquisado seja diferente do pesquisado
				// anterior ou seja a primeira vez que se esteja pesquisando
				if(imovelNovoPesquisado){

					// seta na tela a inscrição do imovel
					consultarImovelActionForm.setMatriculaImovelDadosComplementares(this.getFachada().pesquisarInscricaoImovel(
									Util.obterInteger(idImovelDadosComplementares.trim()), true));

					httpServletRequest.setAttribute("idImovelDadosComplementaresNaoEncontrado", null);

					// seta a situação de agua
					consultarImovelActionForm.setSituacaoAguaDadosComplementares(imovel.getLigacaoAguaSituacao().getDescricao());

					// seta a situação de esgoto
					consultarImovelActionForm.setSituacaoEsgotoDadosComplementares(imovel.getLigacaoEsgotoSituacao().getDescricao());

					// Obter Contratos Demanda Consumo
					pesquisarContratoDemandaConsumo(imovel.getId(), httpServletRequest);

					// consumo tarifa
					String descricaoConsumoTarifa = "";
					if(imovel.getConsumoTarifa() != null){
						descricaoConsumoTarifa = imovel.getConsumoTarifa().getDescricao();
					}

					consultarImovelActionForm.setTarifaConsumoDadosComplementares(descricaoConsumoTarifa);

					// data validade tarifa temporaria
					String dataValidadeTarifaTemporaria = "";

					if(imovel.getDataValidadeTarifaTemporaria() != null){
						dataValidadeTarifaTemporaria = Util.formatarData(imovel.getDataValidadeTarifaTemporaria());
					}

					consultarImovelActionForm.setDataValidadeTarifaConsumo(dataValidadeTarifaTemporaria);

					// numero retificacao
					String numeroRetificacaoStr = "";
					if(imovel.getNumeroRetificacao() != null){
						numeroRetificacaoStr = Short.toString(imovel.getNumeroRetificacao());
					}

					consultarImovelActionForm.setQuantidadeRetificacoesDadosComplementares(numeroRetificacaoStr);

					// numero parcelamento
					String numeroParcelamentoStr = "";
					if(imovel.getNumeroParcelamento() != null){
						numeroParcelamentoStr = Short.toString(imovel.getNumeroParcelamento());
					}

					consultarImovelActionForm.setQuantidadeParcelamentosDadosComplementares(numeroParcelamentoStr);

					// numero reparcelamento
					String numeroReparcelamentoStr = "";
					if(imovel.getNumeroReparcelamento() != null){
						numeroReparcelamentoStr = Short.toString(imovel.getNumeroReparcelamento());
					}

					consultarImovelActionForm.setQuantidadeReparcelamentoDadosComplementares(numeroReparcelamentoStr);

					// numero reparcelamento consecutivos
					String numeroReparcelamentoConsecutivosStr = "";
					if(imovel.getNumeroReparcelamentoConsecutivos() != null){
						numeroReparcelamentoConsecutivosStr = Short.toString(imovel.getNumeroReparcelamentoConsecutivos());
					}

					consultarImovelActionForm
									.setQuantidadeReparcelamentoConsecutivosDadosComplementares(numeroReparcelamentoConsecutivosStr);

					LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();

					// Quantidade de cortes
					String numeroCorteStr = "";
					if(ligacaoAgua != null && ligacaoAgua.getNumeroCorte() != null){
						numeroCorteStr = Integer.toString(ligacaoAgua.getNumeroCorte());
					}

					consultarImovelActionForm.setQuantidadeCortes(numeroCorteStr);

					// Quantidade de cortes administrativos
					String numeroCorteAdministrativoStr = "";
					if(ligacaoAgua != null && ligacaoAgua.getNumeroCorteAdministrativo() != null){
						numeroCorteAdministrativoStr = Integer.toString(ligacaoAgua.getNumeroCorteAdministrativo());
					}

					consultarImovelActionForm.setQuantidadeCortesAdministrativos(numeroCorteAdministrativoStr);

					// Quantidade de Supressões
					String numeroSupressaoAguaStr = "";
					if(ligacaoAgua != null && ligacaoAgua.getNumeroSupressaoAgua() != null){
						numeroSupressaoAguaStr = Integer.toString(ligacaoAgua.getNumeroSupressaoAgua());
					}

					consultarImovelActionForm.setQuantidadeSupressoes(numeroSupressaoAguaStr);

					// cobranca situacao
					String descricaoCobrancaSituacao = "";
					if(imovel.getCobrancaSituacao() != null){
						descricaoCobrancaSituacao = imovel.getCobrancaSituacao().getDescricao();
					}

					consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(descricaoCobrancaSituacao);

					// funcionario
					String idFuncionarioStr = "";
					String nomeFuncionario = "";
					if(imovel.getFuncionario() != null){
						idFuncionarioStr = Integer.toString(imovel.getFuncionario().getId());
						nomeFuncionario = imovel.getFuncionario().getNome();
					}

					consultarImovelActionForm.setIdFuncionario(idFuncionarioStr);
					consultarImovelActionForm.setNomeFuncionario(nomeFuncionario);

					// cadastro ocorrencia
					String descricaoCadastroOcorrencia = "";
					if(imovel.getCadastroOcorrencia() != null){
						descricaoCadastroOcorrencia = imovel.getCadastroOcorrencia().getDescricao();
					}

					consultarImovelActionForm.setDescricaoOcorrenciaDadosComplementares(descricaoCadastroOcorrencia);

					// elo anormalidade
					String descricaoEloAnormalidade = "";
					if(imovel.getEloAnormalidade() != null){
						descricaoEloAnormalidade = imovel.getEloAnormalidade().getDescricao();
					}

					consultarImovelActionForm.setDescricaoAnormalidadeDadosComplementares(descricaoEloAnormalidade);

					// Situações de cobrança
					Collection colecaoSituacoesCobranca = this.getFachada().pesquisarSituacoesCobrancaImovel(
									Util.obterInteger(idImovelDadosComplementares.trim()));

					sessao.setAttribute("colecaoSituacoesCobranca", colecaoSituacoesCobranca);

					// vencimentos alternativos
					Collection colecaoVencimentosAlternativos = this.getFachada().pesquisarVencimentoAlternativoImovel(
									Util.obterInteger(idImovelDadosComplementares.trim()));

					sessao.setAttribute("colecaoVencimentosAlternativos", colecaoVencimentosAlternativos);

					// debitos automaticos
					Collection colecaoDebitosAutomaticos = this.getFachada().pesquisarDebitosAutomaticosImovel(
									Util.obterInteger(idImovelDadosComplementares.trim()));

					sessao.setAttribute("colecaoDebitosAutomaticos", colecaoDebitosAutomaticos);

					// Faturamentos Situacao Historico
					Collection colecaoFaturamentosSituacaoHistorico = this.getFachada().pesquisarFaturamentosSituacaoHistorico(
									Util.obterInteger(idImovelDadosComplementares.trim()));

					sessao.setAttribute("colecaoFaturamentosSituacaoHistorico", colecaoFaturamentosSituacaoHistorico);

					// Cobrancas Situacao Historico
					Collection colecaoCobrancasSituacaoHistorico = this.getFachada().pesquisarCobrancasSituacaoHistorico(
									Util.obterInteger(idImovelDadosComplementares.trim()));

					sessao.setAttribute("colecaoCobrancasSituacaoHistorico", colecaoCobrancasSituacaoHistorico);

					Collection<ImovelCadastroOcorrencia> colecaoImovelCadastroOcorrencia = this.getFachada().pesquisarOcorrenciasCadastro(
									idImovelDadosComplementares);

					sessao.setAttribute("colecaoImovelCadastroOcorrencia", colecaoImovelCadastroOcorrencia);

					Collection colecaoImovelEloAnormalidade = this.getFachada().pesquisarEloAnormalidade(idImovelDadosComplementares);

					sessao.setAttribute("colecaoImovelEloAnormalidade", colecaoImovelEloAnormalidade);
				}
			}else{
				httpServletRequest.setAttribute("idImovelDadosComplementaresNaoEncontrado", "true");

				consultarImovelActionForm.setMatriculaImovelDadosComplementares("IMÓVEL INEXISTENTE");

				// limpar os dados
				sessao.removeAttribute("imovelDadosComplementares");

				sessao.removeAttribute("colecaoVencimentosAlternativos");
				sessao.removeAttribute("colecaoSituacoesCobranca");
				sessao.removeAttribute("colecaoDebitosAutomaticos");
				sessao.removeAttribute("colecaoFaturamentosSituacaoHistorico");
				sessao.removeAttribute("colecaoCobrancasSituacaoHistorico");

				// Colocado por Raphael Rossiter em 13/01/2007
				sessao.removeAttribute("colecaoImovelCadastroOcorrencia");
				sessao.removeAttribute("colecaoImovelEloAnormalidade");

				consultarImovelActionForm.setIdImovelDadosComplementares(null);
				consultarImovelActionForm.setSituacaoAguaDadosComplementares(null);
				consultarImovelActionForm.setSituacaoEsgotoDadosComplementares(null);
				consultarImovelActionForm.setTarifaConsumoDadosComplementares(null);
				consultarImovelActionForm.setQuantidadeRetificacoesDadosComplementares(null);
				consultarImovelActionForm.setQuantidadeParcelamentosDadosComplementares(null);
				consultarImovelActionForm.setQuantidadeReparcelamentoDadosComplementares(null);
				consultarImovelActionForm.setQuantidadeReparcelamentoConsecutivosDadosComplementares(null);
				consultarImovelActionForm.setQuantidadeCortes(null);
				consultarImovelActionForm.setQuantidadeCortesAdministrativos(null);
				consultarImovelActionForm.setQuantidadeSupressoes(null);
				consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);
				consultarImovelActionForm.setIdFuncionario(null);
				consultarImovelActionForm.setNomeFuncionario(null);
				consultarImovelActionForm.setDescricaoOcorrenciaDadosComplementares(null);
				consultarImovelActionForm.setDescricaoAnormalidadeDadosComplementares(null);
			}

		}else{

			consultarImovelActionForm.setIdImovelDadosComplementares(idImovelDadosComplementares);

			httpServletRequest.setAttribute("idImovelDadosComplementaresNaoEncontrado", null);

			sessao.removeAttribute("imovelDadosComplementares");
			sessao.removeAttribute("colecaoVencimentosAlternativos");
			sessao.removeAttribute("colecaoSituacoesCobranca");
			sessao.removeAttribute("colecaoDebitosAutomaticos");
			sessao.removeAttribute("colecaoFaturamentosSituacaoHistorico");
			sessao.removeAttribute("colecaoCobrancasSituacaoHistorico");
			sessao.removeAttribute("idImovelPrincipalAba");
			sessao.removeAttribute("colecaoContratoDemandaConsumo");

			// Colocado por Raphael Rossiter em 13/01/2007
			sessao.removeAttribute("colecaoImovelCadastroOcorrencia");
			sessao.removeAttribute("colecaoImovelEloAnormalidade");

			consultarImovelActionForm.setMatriculaImovelDadosComplementares(null);
			consultarImovelActionForm.setSituacaoAguaDadosComplementares(null);
			consultarImovelActionForm.setSituacaoEsgotoDadosComplementares(null);
			consultarImovelActionForm.setTarifaConsumoDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeRetificacoesDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeParcelamentosDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeReparcelamentoDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeReparcelamentoConsecutivosDadosComplementares(null);
			consultarImovelActionForm.setQuantidadeCortes(null);
			consultarImovelActionForm.setQuantidadeCortesAdministrativos(null);
			consultarImovelActionForm.setQuantidadeSupressoes(null);
			consultarImovelActionForm.setSituacaoCobrancaDadosComplementares(null);
			consultarImovelActionForm.setIdFuncionario(null);
			consultarImovelActionForm.setNomeFuncionario(null);
			consultarImovelActionForm.setDescricaoOcorrenciaDadosComplementares(null);
			consultarImovelActionForm.setDescricaoAnormalidadeDadosComplementares(null);
		}

		return retorno;
	}

	public void pesquisarContratoDemandaConsumo(Integer idImovel, HttpServletRequest httpServletRequest){

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroContratoDemandaConsumo filtroContratoDemandaConsumo = new FiltroContratoDemandaConsumo();
		filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.IMOVEL_ID, idImovel));

		Collection colecaoContratoDemandaConsumo = this.getFachada().pesquisar(filtroContratoDemandaConsumo,
						ContratoDemandaConsumo.class.getName());

		if(colecaoContratoDemandaConsumo != null && !colecaoContratoDemandaConsumo.isEmpty()){

			sessao.setAttribute("colecaoContratoDemandaConsumo", colecaoContratoDemandaConsumo);

		}
	}
}