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

package gcom.gui.micromedicao;

import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ImovelMicromedicao;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.FiltroMedicaoHistoricoSql;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Sávio Luiz
 * @created 28 de Junho de 2004
 * @author Virgínia Melo
 * @date 29/12/2008
 *       Alterações - Adicionado campo CreditoFaturado
 *       - Corrigido erro ao pesquisar um novo imóvel
 * @author eduardo henrique
 * @date 02/01/2009
 *       Alteração na forma de carregamento do Consumo_Histórico, para adequar às situações
 *       dos imóveis estimados, que não possuem medição no período.
 * @author Andre Nishimura
 * @date 22/04/2009
 *       Alteração na forma de selecionar os itens para o relatorio.
 */
public class ExibirDadosAnaliseExcecoesLeituraResumidoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("dadosAnaliseMedicaoConsumoResumo");

		LeituraConsumoActionForm leituraConsumoActionForm = (LeituraConsumoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		marcarRegistroParaImpressao(httpServletRequest, leituraConsumoActionForm, sessao);

		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) sessao.getAttribute("faturamentoGrupo");

		sessao.setAttribute("imoveisMicromedicao", null);

		if(leituraConsumoActionForm.getIdAnormalidade() != null && !leituraConsumoActionForm.getIdAnormalidade().trim().equals("")
						&& httpServletRequest.getParameter("pesquisarAnormalidade") != null){
			String codigoAnormalidadeLeituraDigitadoEnter = leituraConsumoActionForm.getIdAnormalidade();

			// Verifica se o usuário alterou a Anormalidade de Leitura
			FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
							codigoAnormalidadeLeituraDigitadoEnter));

			Collection anormalidadeLeituraEncontrada = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());

			if(anormalidadeLeituraEncontrada != null && !anormalidadeLeituraEncontrada.isEmpty()){

				leituraConsumoActionForm.setIdAnormalidade(""
								+ ((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada).get(0)).getId());
				leituraConsumoActionForm.setDescricaoAnormalidade(((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada).get(0))
								.getDescricao());

			}else{
				leituraConsumoActionForm.setIdAnormalidade("");
				httpServletRequest.setAttribute("idAnormalidadeNaoEncontrada", "true");
				leituraConsumoActionForm.setDescricaoAnormalidade("Anormalidade de leitura inexistente");
				httpServletRequest.setAttribute("nomeCampo", "idAnormalidade");
			}
		}else{

			leituraConsumoActionForm.setDataLeituraAnteriorFaturamento("");
			leituraConsumoActionForm.setDataLeituraAtualFaturamento("");
			leituraConsumoActionForm.setDataLeituraAtualInformada("");
			leituraConsumoActionForm.setConsumo("");
			leituraConsumoActionForm.setLeituraAnterior("");
			leituraConsumoActionForm.setLeituraAnteriorFaturamento("");
			leituraConsumoActionForm.setLeituraAtualFaturada("");
			leituraConsumoActionForm.setLeituraAtualInformada("");
			leituraConsumoActionForm.setIdAnormalidade("");
			leituraConsumoActionForm.setDescricaoAnormalidade("");
			leituraConsumoActionForm.setConsumoMedioImovel("");
			leituraConsumoActionForm.setDiasConsumo("");
			leituraConsumoActionForm.setVarConsumo("");
			leituraConsumoActionForm.setLeituraSituacaoAtual("");
			leituraConsumoActionForm.setIdFuncionario("");
			leituraConsumoActionForm.setConsumoInformado("");
			leituraConsumoActionForm.setConsumoTipo("");
			leituraConsumoActionForm.setConsumoAnormalidadeAbreviada("");

			String codigoImovel = httpServletRequest.getParameter("idRegistroAtualizacao");

			String idMedicaoTipo = httpServletRequest.getParameter("medicaoTipo");

			// ================ parte de controle de paginas (Avançar e Voltar) ===
			int quantidadeTotalPaginas = 0;
			if(sessao.getAttribute("totalRegistros") != null && !sessao.getAttribute("totalRegistros").equals("")){
				int totalRegistros = (Integer) sessao.getAttribute("totalRegistros");
				quantidadeTotalPaginas = ((Double) Math.ceil(Double.parseDouble("" + totalRegistros) / 10)).intValue();
			}

			FiltroMedicaoHistoricoSql filtroMedicaoHistoricoSql = new FiltroMedicaoHistoricoSql();
			int numeroPaginasPesquisa = 0;
			// numeroPaginasPesquisa é inicialmente passado pela sessao pelo
			// ExibirManterAnaliseExcecoesConsumosAction
			// depois é sobreposto nesse action
			if(sessao.getAttribute("numeroPaginasPesquisa") != null && !sessao.getAttribute("numeroPaginasPesquisa").equals("")){
				numeroPaginasPesquisa = (Integer) sessao.getAttribute("numeroPaginasPesquisa");
			}

			if(sessao.getAttribute("filtroMedicaoHistoricoSql") != null){
				filtroMedicaoHistoricoSql = (FiltroMedicaoHistoricoSql) sessao.getAttribute("filtroMedicaoHistoricoSql");
			}
			// index é a variavel q guarda a posição do objeto imovel dentro da colecao de imoveis

			int index = 0;
			if(sessao.getAttribute("index") != null){
				index = (Integer) sessao.getAttribute("index");
			}

			Integer totalRegistros = (Integer) sessao.getAttribute("totalRegistros");
			Collection colecaoIdsImovel = null;

			if(sessao.getAttribute("colecaoIdsImovelTotal") != null){
				colecaoIdsImovel = (Collection) sessao.getAttribute("colecaoIdsImovelTotal");
			}else{
				colecaoIdsImovel = fachada.filtrarExcecoesLeiturasConsumos(faturamentoGrupo, filtroMedicaoHistoricoSql, totalRegistros,
								true);
				sessao.setAttribute("colecaoIdsImovelTotal", colecaoIdsImovel);
			}

			// verifica se é a primeira vez.Se for então pesquisa o index do id do
			// imovel na coleção para não precisar ficar rodando a coleção toda vez
			// que o usuário quiser o imovel anterior ou o proximo imovel
			if(codigoImovel != null && !codigoImovel.equals("")){
				int i = 0;
				Iterator iterator = colecaoIdsImovel.iterator();

				while(iterator.hasNext()){
					ImovelMicromedicao imovelMicromedicao = (ImovelMicromedicao) iterator.next();
					if(!imovelMicromedicao.getImovel().getId().equals(Integer.valueOf(codigoImovel))){
						i = i + 1;
					}else{
						break;
					}
				}
				index = i;
				sessao.setAttribute("index", index);

				// caso não seja a primeira vez então, dependendo do parametro que
				// foi passado, recupera o id do imóvel para ser pesquisado

			}else{

				if(httpServletRequest.getParameter("imovelAnterior") != null){

					index = index - 1;

				}
				if(httpServletRequest.getParameter("proximoImovel") != null){
					index = index + 1;

				}

				// caso
				if(index == colecaoIdsImovel.size() || index == -1){

					if(colecaoIdsImovel != null && !colecaoIdsImovel.isEmpty()){

						// recupera o id do imovel
						codigoImovel = ((ImovelMicromedicao) ((List) colecaoIdsImovel).get(index)).getImovel().getId().toString();
						if(((ImovelMicromedicao) ((List) colecaoIdsImovel).get(index)).getMedicaoHistorico().getMedicaoTipo() != null){
							idMedicaoTipo = ((ImovelMicromedicao) ((List) colecaoIdsImovel).get(index)).getMedicaoHistorico()
											.getMedicaoTipo().getId().toString();
						}
						sessao.setAttribute("index", index);
					}
				}else{

					// recupera o id do imovel
					codigoImovel = ((ImovelMicromedicao) ((List) colecaoIdsImovel).get(index)).getImovel().getId().toString();

					if(((ImovelMicromedicao) ((List) colecaoIdsImovel).get(index)).getMedicaoHistorico().getMedicaoTipo() != null){
						if(((ImovelMicromedicao) ((List) colecaoIdsImovel).get(index)).getMedicaoHistorico().getMedicaoTipo().getId() != null){
							idMedicaoTipo = ((ImovelMicromedicao) ((List) colecaoIdsImovel).get(index)).getMedicaoHistorico()
											.getMedicaoTipo().getId().toString();
						}
					}
					sessao.setAttribute("index", index);
				}
			}
			sessao.setAttribute("indiceImovel", "" + (index + 1));
			// ====================================================================

			if(codigoImovel != null && !codigoImovel.equals("")){
				// obtem dados de sistema parametros
				// FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();
				// Collection colecaoSistemaParametro = fachada.pesquisar(filtroSistemaParametro,
				// SistemaParametro.class.getName());
				// SistemaParametro sistemaParametro =
				// (SistemaParametro)colecaoSistemaParametro.iterator().next();

				// Rota e Seq de Rota
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota");
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Integer.valueOf(codigoImovel)));
				Collection imoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());
				if(!imoveis.isEmpty()){
					Imovel imovel = (Imovel) imoveis.iterator().next();
					leituraConsumoActionForm.setRota(imovel.getRota().getCodigo() + "");
					if(imovel.getNumeroSequencialRota() != null){
						leituraConsumoActionForm.setSeqRota(imovel.getNumeroSequencialRota().toString());
					}
				}

				boolean ligacaoAgua = false;
				if(idMedicaoTipo != null && idMedicaoTipo.trim().equals("" + MedicaoTipo.LIGACAO_AGUA)){
					ligacaoAgua = true;
				}

				sessao.setAttribute("ligacaoAgua", ligacaoAgua);
				sessao.setAttribute("tipoMedicao", idMedicaoTipo);

				// Cria as istancias dos objetos q receberam os dados q iram compor a tela
				ImovelMicromedicao imovelMicromedicaoDadosResumo = new ImovelMicromedicao();
				ImovelMicromedicao imovelMicromedicaoCarregaMedicaoResumo = new ImovelMicromedicao();

				imovelMicromedicaoDadosResumo = fachada.pesquiarImovelExcecoesApresentaDadosResumido(Integer.valueOf(codigoImovel),
								ligacaoAgua);
				imovelMicromedicaoCarregaMedicaoResumo = fachada.carregarDadosMedicaoResumido(Integer.valueOf(codigoImovel), ligacaoAgua,
								faturamentoGrupo.getAnoMesReferencia().toString());

				if(imovelMicromedicaoDadosResumo.getImovel() != null
								&& imovelMicromedicaoDadosResumo.getImovel().getHidrometroInstalacaoHistorico() != null){
					leituraConsumoActionForm.setInstalacaoHidrometro(Util.formatarData(imovelMicromedicaoDadosResumo.getImovel()
									.getHidrometroInstalacaoHistorico().getDataInstalacao()));
				}else{
					leituraConsumoActionForm.setInstalacaoHidrometro("");
				}

				if(imovelMicromedicaoDadosResumo.getImovel() != null){
					imovelMicromedicaoDadosResumo.getImovel().setId(Integer.valueOf(codigoImovel));
				}else{
					throw new ActionServletException("atencao.pesquisa.nenhumresultado");
				}
				sessao.setAttribute("imovelMicromedicaoDadosResumo", imovelMicromedicaoDadosResumo);
				sessao.setAttribute("idImovel", codigoImovel);
				sessao.setAttribute("imovelMicromedicaoCarregaMedicaoResumo", imovelMicromedicaoCarregaMedicaoResumo);

				if(imovelMicromedicaoDadosResumo.getImovel() != null
								&& imovelMicromedicaoDadosResumo.getImovel().getHidrometroInstalacaoHistorico() != null
								&& imovelMicromedicaoDadosResumo.getImovel().getHidrometroInstalacaoHistorico().getId() != null){
					sessao.setAttribute("poco", true);
				}else{
					sessao.removeAttribute("poco");
				}

				Imovel imovel = new Imovel();
				imovel.setId(Integer.valueOf(codigoImovel));
				Categoria categoria = fachada.obterDescricoesCategoriaImovel(imovel);
				sessao.setAttribute("categoria", categoria);

				String inscricaoImovel = fachada.pesquisarInscricaoImovel(Integer.valueOf(codigoImovel), true);
				sessao.setAttribute("inscricaoFormatada", inscricaoImovel);

				Collection colecaoMedicaoHistorico = fachada.carregarDadosMedicaoResumo(Integer.valueOf(codigoImovel), ligacaoAgua);

				Collection imoveisMicromedicaoCarregamento = null;
				Collection colecaoImovelMicromedicao = new ArrayList();

				MedicaoHistorico medicaoHistoricoAnoMesAtual = new MedicaoHistorico();
				ImovelMicromedicao imovelMicromedicaoConsumo = new ImovelMicromedicao();

				if(colecaoMedicaoHistorico != null && !colecaoMedicaoHistorico.isEmpty()){
					Iterator iteratorMedicaoHistorico = colecaoMedicaoHistorico.iterator();

					while(iteratorMedicaoHistorico.hasNext()){
						MedicaoHistorico medicaoHistoricoConsumo = (MedicaoHistorico) iteratorMedicaoHistorico.next();
						if(medicaoHistoricoConsumo.getAnoMesReferencia() != 0){

							imoveisMicromedicaoCarregamento = fachada.carregarDadosConsumo(Integer.valueOf(codigoImovel),
											medicaoHistoricoConsumo.getAnoMesReferencia(), ligacaoAgua);

							if(imoveisMicromedicaoCarregamento != null){

								ImovelMicromedicao imovelMicromedicao = (ImovelMicromedicao) imoveisMicromedicaoCarregamento.iterator()
												.next();

								if(imovelMicromedicao.getMedicaoHistorico() != null
												&& imovelMicromedicao.getMedicaoHistorico().getNumeroConsumoMes() != null){
									medicaoHistoricoConsumo.setNumeroConsumoMes(imovelMicromedicao.getMedicaoHistorico()
													.getNumeroConsumoMes());

								}

								if(faturamentoGrupo.getAnoMesReferencia().intValue() == medicaoHistoricoConsumo.getAnoMesReferencia()){
									if(medicaoHistoricoConsumo.getNumeroConsumoMes() != null){
										leituraConsumoActionForm.setMedido(medicaoHistoricoConsumo.getNumeroConsumoMes().toString());
									}else{
										leituraConsumoActionForm.setMedido("");
									}

									leituraConsumoActionForm.setConsumo(imovelMicromedicao.getConsumoHistorico()
													.getNumeroConsumoFaturadoMes().toString());

									// Adicionado em 19/12/2008 v0.08 - vsm
									if(imovelMicromedicao.getConsumoHistorico().getConsumoMinimoCreditado() != null){
										leituraConsumoActionForm.setCreditoFaturado(imovelMicromedicao.getConsumoHistorico()
														.getConsumoMinimoCreditado().toString());
									}else{
										leituraConsumoActionForm.setCreditoFaturado("");
									}

									if(imovelMicromedicao.getConsumoHistorico().getConsumoMedio() != null){
										leituraConsumoActionForm.setConsumoMedioImovel(imovelMicromedicao.getConsumoHistorico()
														.getConsumoMedio().toString());
									}else{
										leituraConsumoActionForm.setConsumoMedioImovel("");
									}

									if(imovelMicromedicao.getConsumoHistorico().getConsumoRateio() != null){
										leituraConsumoActionForm.setRateio(imovelMicromedicao.getConsumoHistorico().getConsumoRateio()
														.toString());
									}
								}

								imovelMicromedicao.setMedicaoHistorico(medicaoHistoricoConsumo);

								colecaoImovelMicromedicao.add(imovelMicromedicao);
							}
							if(faturamentoGrupo.getAnoMesReferencia().intValue() == medicaoHistoricoConsumo.getAnoMesReferencia()){
								medicaoHistoricoAnoMesAtual = medicaoHistoricoConsumo;
								sessao.setAttribute("medicaoHistoricoAnoMesAtual", medicaoHistoricoAnoMesAtual);

								if(imoveisMicromedicaoCarregamento != null){
									imovelMicromedicaoConsumo = (ImovelMicromedicao) imoveisMicromedicaoCarregamento.iterator().next();

									if(imovelMicromedicaoConsumo.getQtdDias() != null){
										leituraConsumoActionForm.setDiasConsumo(imovelMicromedicaoConsumo.getQtdDias());
									}

									// fazer a parte de montagem do form aki etirar os value no jsp
									if(imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico().getDataLeituraAnteriorFaturamento() != null){
										leituraConsumoActionForm.setDataLeituraAnteriorFaturamento(Util
														.formatarData(imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico()
																		.getDataLeituraAnteriorFaturamento()));
									}else{
										leituraConsumoActionForm.setDataLeituraAnteriorFaturamento("");
									}
									if(imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico().getDataLeituraAtualInformada() != null){
										leituraConsumoActionForm.setDataLeituraAtualInformada(Util
														.formatarData(imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico()
																		.getDataLeituraAtualInformada()));
									}else{
										leituraConsumoActionForm.setDataLeituraAtualInformada("");
									}
									if(imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico().getConsumoMedioHidrometro() != null){
										leituraConsumoActionForm.setConsumoMedioHidrometro(imovelMicromedicaoCarregaMedicaoResumo
														.getMedicaoHistorico().getConsumoMedioHidrometro()
														+ "");
									}else{
										leituraConsumoActionForm.setConsumoMedioHidrometro("");
									}

									if(medicaoHistoricoAnoMesAtual.getDataLeituraAtualFaturamento() != null){
										leituraConsumoActionForm.setDataLeituraAtualFaturamento(Util
														.formatarData(medicaoHistoricoAnoMesAtual.getDataLeituraAtualFaturamento()));
									}else{
										leituraConsumoActionForm.setDataLeituraAtualFaturamento("");
									}

									leituraConsumoActionForm.setLeituraAnteriorFaturamento(imovelMicromedicaoCarregaMedicaoResumo
													.getMedicaoHistorico().getLeituraAnteriorFaturamento()
													+ "");

									if(medicaoHistoricoAnoMesAtual.getLeituraAtualInformada() != null){
										leituraConsumoActionForm.setLeituraAtualInformada(medicaoHistoricoAnoMesAtual
														.getLeituraAtualInformada()
														+ "");
									}else{
										leituraConsumoActionForm.setLeituraAtualInformada("");
									}

									leituraConsumoActionForm.setLeituraAtualFaturada(medicaoHistoricoAnoMesAtual
													.getLeituraAtualFaturamento()
													+ "");

									if(medicaoHistoricoAnoMesAtual.getLeituraSituacaoAtual() != null){
										leituraConsumoActionForm.setLeituraSituacaoAtual(medicaoHistoricoAnoMesAtual
														.getLeituraSituacaoAtual().getDescricao());
									}
									// value="${medicaoHistoricoAnoMesAtual.funcionario.id}"
									if(medicaoHistoricoAnoMesAtual.getFuncionario() != null){
										leituraConsumoActionForm.setIdFuncionario(medicaoHistoricoAnoMesAtual.getFuncionario().getId()
														.toString());
									}

									if(imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico().getNumeroConsumoInformado() != null){
										leituraConsumoActionForm.setConsumoInformado(imovelMicromedicaoCarregaMedicaoResumo
														.getMedicaoHistorico().getNumeroConsumoInformado().toString());
									}

									if(imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico() != null
													&& imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico()
																	.getLeituraAnormalidadeInformada() != null
													&& imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico()
																	.getLeituraAnormalidadeInformada().getId() != null){
										FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
										filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
														imovelMicromedicaoCarregaMedicaoResumo.getMedicaoHistorico()
																		.getLeituraAnormalidadeInformada().getId()));

										Collection anormalidadeLeituraEncontrada = fachada.pesquisar(filtroLeituraAnormalidade,
														LeituraAnormalidade.class.getName());

										if(anormalidadeLeituraEncontrada != null && !anormalidadeLeituraEncontrada.isEmpty()){

											leituraConsumoActionForm
															.setIdAnormalidade(""
																			+ ((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada)
																							.get(0)).getId());
											leituraConsumoActionForm
															.setDescricaoAnormalidade(((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada)
																			.get(0)).getDescricao());
										}
									}

									leituraConsumoActionForm.setConfirmacao(httpServletRequest.getParameter("confirmacao"));

									sessao.setAttribute("imovelMicromedicaoConsumo", imovelMicromedicaoConsumo);
									// % Var.Consumo
									if(imovelMicromedicaoConsumo.getConsumoHistorico().getNumeroConsumoFaturadoMes() != null
													&& imovelMicromedicaoConsumo.getConsumoHistorico().getNumeroConsumoFaturadoMes() != 0
													&& imovelMicromedicaoConsumo.getConsumoHistorico().getConsumoMedio() != null
													&& imovelMicromedicaoConsumo.getConsumoHistorico().getConsumoMedio() != 0){
										int operacaoSubMult = (imovelMicromedicaoConsumo.getConsumoHistorico()
														.getNumeroConsumoFaturadoMes() - imovelMicromedicaoConsumo.getConsumoHistorico()
														.getConsumoMedio()) * 100;
										BigDecimal percentual = new BigDecimal(operacaoSubMult).divide(new BigDecimal(
														imovelMicromedicaoConsumo.getConsumoHistorico().getConsumoMedio()), 2,
														BigDecimal.ROUND_HALF_UP);
										String valorPercentual = "" + percentual;
										leituraConsumoActionForm.setVarConsumo(valorPercentual.replace(".", ",") + "%");
										// sessao.setAttribute("varConsumo",valorPercentual.replace(".",
										// ",") + "%");

									}
								}
								if(imovelMicromedicaoConsumo.getConsumoHistorico() != null
												&& imovelMicromedicaoConsumo.getConsumoHistorico().getConsumoAnormalidade() != null){

									leituraConsumoActionForm.setConsumoAnormalidadeAbreviada(imovelMicromedicaoConsumo
													.getConsumoHistorico().getConsumoAnormalidade().getDescricaoAbreviada());
									leituraConsumoActionForm.setConsumoAnormalidadeDescricao(imovelMicromedicaoConsumo
													.getConsumoHistorico().getConsumoAnormalidade().getDescricao());

								}else if(imovelMicromedicaoConsumo.getConsumoHistoricoEsgoto() != null
												&& imovelMicromedicaoConsumo.getConsumoHistoricoEsgoto().getConsumoAnormalidade() != null){

									leituraConsumoActionForm.setConsumoAnormalidadeAbreviada(imovelMicromedicaoConsumo
													.getConsumoHistoricoEsgoto().getConsumoAnormalidade().getDescricaoAbreviada());
									leituraConsumoActionForm.setConsumoAnormalidadeDescricao(imovelMicromedicaoConsumo
													.getConsumoHistoricoEsgoto().getConsumoAnormalidade().getDescricao());
								}

								iteratorMedicaoHistorico.remove();
							}
						}
					}

					// Organizar a coleção de Conta
					if(colecaoImovelMicromedicao != null && !colecaoImovelMicromedicao.isEmpty()){

						Collections.sort((List) colecaoImovelMicromedicao, new Comparator() {

							public int compare(Object a, Object b){

								int retorno = 0;
								Integer anoMesReferencia1 = ((ImovelMicromedicao) a).getMedicaoHistorico().getAnoMesReferencia();
								Integer anoMesReferencia2 = ((ImovelMicromedicao) b).getMedicaoHistorico().getAnoMesReferencia();

								if(anoMesReferencia1.compareTo(anoMesReferencia2) == 1){
									retorno = -1;
								}else if(anoMesReferencia1.compareTo(anoMesReferencia2) == -1){
									retorno = 1;
								}

								return retorno;

							}
						});
					}

					// --- fim variavel

					// coloca a colecao de medicao historico no request
					sessao.setAttribute("medicoesHistoricos", colecaoMedicaoHistorico);

					// coloca colecao de consumo historico no request
					sessao.setAttribute("imoveisMicromedicao", colecaoImovelMicromedicao);
				}
			}

			// verifica se o check de relatorio devera vir marcado ou nao.
			// nao altera a seleçao dos itens para impressao do relatorio
			if(codigoImovel != null){
				HashMap<String, String[]> imoveisPorPagina = (HashMap) sessao.getAttribute("idsImoveisJaSelecionados");
				int indice = 0, pagina = 0;
				if(sessao.getAttribute("index") != null){
					indice = (Integer) sessao.getAttribute("index");
				}
				if(indice > 0){
					pagina = (indice / 10) + 1;
				}else{
					pagina = 1;
				}
				if(imoveisPorPagina != null && imoveisPorPagina.containsKey(String.valueOf(pagina))){
					String[] idsImoveis = imoveisPorPagina.get(String.valueOf(pagina));
					boolean desmarca = true;
					for(String imovel : idsImoveis){
						if(imovel != null && imovel.equals(codigoImovel)){
							desmarca = false;
							leituraConsumoActionForm.setRelatorio(true);
							break;
						}
					}
					if(desmarca){
						leituraConsumoActionForm.setRelatorio(false);
					}
				}else{
					leituraConsumoActionForm.setRelatorio(false);
				}
			}

			sessao.setAttribute("leituraConsumoActionForm", leituraConsumoActionForm);
			if(index == 0 && numeroPaginasPesquisa == 0){
				sessao.setAttribute("desabilitaBotaoAnterior", 1);
			}else{
				sessao.removeAttribute("desabilitaBotaoAnterior");
			}
			if(index >= (colecaoIdsImovel.size() - 1) && numeroPaginasPesquisa == (quantidadeTotalPaginas - 1)){
				sessao.setAttribute("desabilitaBotaoProximo", 1);
			}else{
				sessao.removeAttribute("desabilitaBotaoProximo");
			}
		}
		return retorno;
	}

	/**
	 * @param httpServletRequest
	 * @param leituraConsumoActionForm
	 * @param sessao
	 */
	protected void marcarRegistroParaImpressao(HttpServletRequest httpServletRequest, LeituraConsumoActionForm leituraConsumoActionForm,
					HttpSession sessao){

		// inserir registro no grupo para impressao
		if(sessao.getAttribute("imovelMicromedicaoDadosResumo") != null){
			ImovelMicromedicao imovelMicromedicaoDadosResumo = (ImovelMicromedicao) sessao.getAttribute("imovelMicromedicaoDadosResumo");
			HashMap<String, String[]> imoveisPorPagina = (HashMap) sessao.getAttribute("idsImoveisJaSelecionados");
			if(imoveisPorPagina == null){
				imoveisPorPagina = new HashMap<String, String[]>();
			}
			int indice = 0, pagina = 0;
			String idImovel = null;
			if(httpServletRequest.getParameter("idRegistroAtualizacao") != null
							&& !httpServletRequest.getParameter("idRegistroAtualizacao").equals("")){
				idImovel = httpServletRequest.getParameter("idRegistroAtualizacao");
			}else if(imovelMicromedicaoDadosResumo.getImovel() != null && imovelMicromedicaoDadosResumo.getImovel().getId() != null){
				idImovel = String.valueOf(imovelMicromedicaoDadosResumo.getImovel().getId());
			}
			if(sessao.getAttribute("index") != null){
				indice = (Integer) sessao.getAttribute("index");
			}
			if(indice > 0){
				pagina = (indice / 10) + 1;
			}else{
				pagina = 1;
			}
			// Utilizado o request para obter o status do checkbox devido a um bug no struts que nao
			// permite desmarcar o checkbox.
			if(httpServletRequest.getParameter("relatorio") != null && !httpServletRequest.getParameter("relatorio").equals("")
							&& httpServletRequest.getParameter("relatorio").equalsIgnoreCase("on")){
				// verifica se este registro esta na fila de impressao e, caso nao, incluir
				if(imoveisPorPagina.containsKey(String.valueOf(pagina))){
					String[] idsImoves = imoveisPorPagina.get(String.valueOf(pagina));
					boolean inserir = true;
					String temp = "";
					for(String imovel : idsImoves){
						if(temp.equals("")){
							temp = imovel;
						}else{
							temp = temp + "," + imovel;
						}
						if(imovel != null && imovel.equals(idImovel)){
							inserir = false;
							break;
						}
					}
					if(inserir){
						temp = temp + "," + idImovel;
						String[] idsImoveisAtualizado = temp.split(",");
						imoveisPorPagina.put(pagina + "", idsImoveisAtualizado);
					}
				}else{
					String[] idsImoveis = new String[10];
					idsImoveis[0] = idImovel;
					imoveisPorPagina.put(pagina + "", idsImoveis);
				}
			}else{
				// verificar se este registr esta na fila de impressao e remove-lo
				if(imoveisPorPagina.containsKey(String.valueOf(pagina))){
					String[] idsImoves = imoveisPorPagina.get(String.valueOf(pagina));
					boolean remover = false;
					String temp = "";
					for(String imovel : idsImoves){
						if(temp.equals("")){
							temp = imovel;
						}else{
							temp = temp + "," + imovel;
						}
						if(imovel != null && imovel.equals(idImovel)){
							remover = true;
							break;
						}
					}
					if(remover){
						temp = temp.replace(idImovel, "");
						temp = temp.replace(",,", ",");
						String[] idsImoveisAtualizado = temp.split(",");
						imoveisPorPagina.put(String.valueOf(pagina), idsImoveisAtualizado);
					}
				}
				leituraConsumoActionForm.setRelatorio(false);
			}
			sessao.setAttribute("idsImoveisJaSelecionados", imoveisPorPagina);
		}
	}
}
