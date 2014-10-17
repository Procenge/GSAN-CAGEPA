/* Feito por Felipe e Rafael na data 16/12/2005
 * para poder fazer a alteração do faturamento na [UC0091] Alterar Dados Faturamento */

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

package gcom.gui.faturamento;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoAtividade;
import gcom.faturamento.FiltroFaturamentoAtivCronRota;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.micromedicao.medicao.FiltroMedicaoHistorico;
import gcom.micromedicao.medicao.FiltroMedicaoTipo;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.parametrosistema.api.ParametroSistema;

public class ExibirDadosFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		ActionForward retorno = actionMapping.findForward("atualizarFaturamentoDados");

		// obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("medicoesTipo");
		sessao.removeAttribute("imovel");
		sessao.removeAttribute("medicaoHistorico");
		sessao.removeAttribute("alterarFaturamentoDadosActionForm");

		AlterarFaturamentoDadosActionForm alterarFaturamentoDadosActionForm = (AlterarFaturamentoDadosActionForm) actionForm;
		String alterarMedia = null;

		if(httpServletRequest.getParameter("telaMedicaoConsumoDadosAnt") != null
						&& !httpServletRequest.getParameter("telaMedicaoConsumoDadosAnt").equals("")){
			sessao.setAttribute("telaMedicaoConsumoDadosAnt", httpServletRequest.getParameter("telaMedicaoConsumoDadosAnt"));
			alterarFaturamentoDadosActionForm.setIdAnormalidade("");
		}

		if(httpServletRequest.getParameter("desfazer") != null && !httpServletRequest.getParameter("desfazer").equals("")){
			alterarFaturamentoDadosActionForm.setIdAnormalidade("");
		}

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Deve ser alterado por Leo
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		try{

			alterarMedia = ((ParametroSistema) Fachada.getInstancia().obterParametroSistema("P_PERMITIR_ALTERAR_MEDIA")).getValor();

		}catch(Exception e){

			throw new ActionServletException("erro.sistema");

		}

		if(!alterarMedia.equals(ConstantesSistema.SIM)){

			sessao.setAttribute("alterarMedia", alterarMedia);
		}else{
			sessao.removeAttribute("alterarMedia");
		}

		// Parte que pega as coleções da sessão

		// Medição Tipo
		FiltroMedicaoTipo filtroMedicaoTipoSessao = new FiltroMedicaoTipo();
		filtroMedicaoTipoSessao.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection medicoesTipo = fachada.pesquisar(filtroMedicaoTipoSessao, MedicaoTipo.class.getName());
		if(medicoesTipo.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "tipo de medição");
		}else{
			sessao.setAttribute("medicoesTipo", medicoesTipo);
		}

		// Limpa da Sessão a lista de Medição Histórico Geradas
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("pesquisaImovel"))
						&& !Util.isVazioOuBranco(sessao.getAttribute("medicaoHistoricoGerada"))){
			sessao.removeAttribute("medicaoHistoricoGerada");
		}

		// Pega os codigos que o usuario digitou para a pesquisa direta de
		// Imóvel Parte que cuida da pesquisa efetuada pelo enter
		if(httpServletRequest.getParameter("pesquisaImovel") != null){
			String codigoImovelDigitadoEnter = alterarFaturamentoDadosActionForm.getIdImovel();
			if(codigoImovelDigitadoEnter != null && !codigoImovelDigitadoEnter.trim().equalsIgnoreCase("")){

				// Seta os campos para vazio
				alterarFaturamentoDadosActionForm.setIdImovelSelecionado("");
				alterarFaturamentoDadosActionForm.setTipoMedicaoSelecionada("");
				alterarFaturamentoDadosActionForm.setIdLocalidade("");
				alterarFaturamentoDadosActionForm.setNomeLocalidade("");
				alterarFaturamentoDadosActionForm.setIdSetorComercial("");
				alterarFaturamentoDadosActionForm.setNomeSetorComercial("");
				alterarFaturamentoDadosActionForm.setLeituraAnterior("");
				alterarFaturamentoDadosActionForm.setDataLeituraAnterior("");
				alterarFaturamentoDadosActionForm.setIdLeiturista("");
				alterarFaturamentoDadosActionForm.setNomeLeiturista("");
				alterarFaturamentoDadosActionForm.setLeituraAtual("");
				alterarFaturamentoDadosActionForm.setDataLeituraAtual("");
				alterarFaturamentoDadosActionForm.setIdSituacaoLeituraAtual("");
				alterarFaturamentoDadosActionForm.setNomeSituacaoLeituraAtual("");
				alterarFaturamentoDadosActionForm.setIdAnormalidade("");
				alterarFaturamentoDadosActionForm.setNomeAnormalidade("");
				alterarFaturamentoDadosActionForm.setConsumoMedido("");
				alterarFaturamentoDadosActionForm.setConsumoInformado("");
				alterarFaturamentoDadosActionForm.setConsumoCreditoAnterior("");

				// filtro para pesquisar o imovel digitado --- pesquisa por
				// enter
				FiltroImovel filtroImovelPesquisa = new FiltroImovel();
				filtroImovelPesquisa.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
				filtroImovelPesquisa.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroImovelPesquisa.adicionarCaminhoParaCarregamentoEntidade("quadra");
				filtroImovelPesquisa.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovelDigitadoEnter));

				Collection colecaoImovel = fachada.pesquisar(filtroImovelPesquisa, Imovel.class.getName());
				if(!colecaoImovel.isEmpty()){
					Imovel imovelPesquisa = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

					alterarFaturamentoDadosActionForm.setInscricaoImovel(imovelPesquisa.getInscricaoFormatada());

					String enderecoCompleto = fachada.pesquisarEnderecoFormatado(new Integer(codigoImovelDigitadoEnter));
					httpServletRequest.setAttribute("enderecoCompleto", enderecoCompleto);

				}else{
					alterarFaturamentoDadosActionForm.setInscricaoImovel("IMÓVEL INEXISTENTE");
				}
			}
		}else{
			Imovel imovel = new Imovel();

			MedicaoHistorico medicaoHistorico = new MedicaoHistorico();

			String codigoImovelDigitadoEnter = null;

			String idTipoMedicao = null;

			if(httpServletRequest.getParameter("idImovel") != null && !httpServletRequest.getParameter("idImovel").equals("")
							&& httpServletRequest.getParameter("idTipoMedicao") != null
							&& !httpServletRequest.getParameter("idTipoMedicao").equals("")){
				codigoImovelDigitadoEnter = httpServletRequest.getParameter("idImovel");
				sessao.setAttribute("idImovelSessao", codigoImovelDigitadoEnter);
				idTipoMedicao = httpServletRequest.getParameter("idTipoMedicao");
				alterarFaturamentoDadosActionForm.setTipoMedicao(idTipoMedicao);
				sessao.setAttribute("idTipoMedicaoSessao", idTipoMedicao);
				sessao.setAttribute("bloqueaCampos", "S");
			}else{
				codigoImovelDigitadoEnter = alterarFaturamentoDadosActionForm.getIdImovel();
				idTipoMedicao = alterarFaturamentoDadosActionForm.getTipoMedicao();
			}

			String codigoAnormalidadeLeituraDigitadoEnter = alterarFaturamentoDadosActionForm.getIdAnormalidade();
			alterarFaturamentoDadosActionForm.setIndicadorConfirmacao(ConstantesSistema.NAO_CONFIRMADA);

			// Seta os campos para vazio
			alterarFaturamentoDadosActionForm.setIdImovelSelecionado("");
			alterarFaturamentoDadosActionForm.setTipoMedicaoSelecionada("");
			alterarFaturamentoDadosActionForm.setIdLocalidade("");
			alterarFaturamentoDadosActionForm.setNomeLocalidade("");
			alterarFaturamentoDadosActionForm.setIdSetorComercial("");
			alterarFaturamentoDadosActionForm.setNomeSetorComercial("");
			alterarFaturamentoDadosActionForm.setLeituraAnterior("");
			alterarFaturamentoDadosActionForm.setDataLeituraAnterior("");
			alterarFaturamentoDadosActionForm.setIdLeiturista("");
			alterarFaturamentoDadosActionForm.setNomeLeiturista("");
			alterarFaturamentoDadosActionForm.setLeituraAtual("");
			alterarFaturamentoDadosActionForm.setDataLeituraAtual("");
			alterarFaturamentoDadosActionForm.setIdSituacaoLeituraAtual("");
			alterarFaturamentoDadosActionForm.setNomeSituacaoLeituraAtual("");
			alterarFaturamentoDadosActionForm.setIdAnormalidade("");
			alterarFaturamentoDadosActionForm.setNomeAnormalidade("");
			alterarFaturamentoDadosActionForm.setConsumoMedido("");
			alterarFaturamentoDadosActionForm.setConsumoInformado("");
			alterarFaturamentoDadosActionForm.setConsumoCreditoAnterior("");

			if(sessao.getAttribute("nomeCampo") != null && !sessao.getAttribute("nomeCampo").toString().trim().equals("")){
				httpServletRequest.setAttribute("nomeCampo", sessao.getAttribute("nomeCampo").toString());
			}

			if(codigoImovelDigitadoEnter != null && !codigoImovelDigitadoEnter.trim().equals("")){

				// Pesquisa o imovel na base
				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial.municipio.unidadeFederacao");

				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("hidrometroInstalacaoHistorico.hidrometro");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("ligacaoAgua.hidrometroInstalacaoHistorico.hidrometro");
				filtroImovel.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidade");
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovelDigitadoEnter));

				Collection imovelEncontrado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				String enderecoCompleto = fachada.pesquisarEnderecoFormatado(new Integer(codigoImovelDigitadoEnter));
				httpServletRequest.setAttribute("enderecoCompleto", enderecoCompleto);

				if(imovelEncontrado != null && !imovelEncontrado.isEmpty()){
					// O imovel foi encontrado

					imovel = ((Imovel) ((List) imovelEncontrado).get(0));

					if(idTipoMedicao == null || idTipoMedicao.equals("-1")){
						httpServletRequest.setAttribute("nomeCampo", "idImovel");

						throw new ActionServletException("atencao.naoinformado", null, "Tipo Medição");
					}

					FiltroMedicaoTipo filtroMedicaoTipo = new FiltroMedicaoTipo();
					filtroMedicaoTipo.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.ID, idTipoMedicao));

					Collection tipoMedicaoEncontrada = fachada.pesquisar(filtroMedicaoTipo, MedicaoTipo.class.getName());

					FiltroFaturamentoAtivCronRota filtroFaturamentoAtividadeCronogramaRota = new FiltroFaturamentoAtivCronRota();
					filtroFaturamentoAtividadeCronogramaRota.adicionarParametro(new ParametroSimples(
									FiltroFaturamentoAtivCronRota.COMP_ID_ROTA_ID, imovel.getRota().getId()));
					filtroFaturamentoAtividadeCronogramaRota.adicionarParametro(new ParametroSimples(
									FiltroFaturamentoAtivCronRota.COMP_ID_FATURAMENTO_ATIVIDADE_CRONOGRAMA_FATURAMENTO_ATIVIDADE_ID,
									FaturamentoAtividade.FATURAR_GRUPO));
					filtroFaturamentoAtividadeCronogramaRota
									.adicionarParametro(new ParametroSimples(
													FiltroFaturamentoAtivCronRota.COMP_ID_FATURAMENTO_ATIVIDADE_CRONOGRAMA_FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA,
													imovel.getRota().getFaturamentoGrupo().getAnoMesReferencia()));
					filtroFaturamentoAtividadeCronogramaRota.adicionarParametro(new ParametroNaoNulo(
									FiltroFaturamentoAtivCronRota.COMP_ID_FATURAMENTO_ATIVIDADE_CRONOGRAMA_DATA_REALIZACAO));

					Collection faturamentoAtividadeCronogramaRota = fachada.pesquisar(filtroFaturamentoAtividadeCronogramaRota,
									FaturamentoAtivCronRota.class.getName());

					if(!faturamentoAtividadeCronogramaRota.isEmpty()){
						httpServletRequest.setAttribute("nomeCampo", "idImovel");
						throw new ActionServletException("atencao.imovel.faturamento.existente", null, ""
										+ imovel.getRota().getFaturamentoGrupo().getId());

					}else{
					
						sessao.setAttribute("imovel", imovel);

						Collection medicaoHistoricoEncontrada = null;

						alterarFaturamentoDadosActionForm.setIdImovel("" + imovel.getId());
						alterarFaturamentoDadosActionForm.setIdImovelSelecionado("" + imovel.getId());
						Integer consumoMedio = this.getFachada().obterConsumoMedio(imovel.getId());

						if(consumoMedio != null){

							alterarFaturamentoDadosActionForm.setConsumoMedio(consumoMedio.toString());
							sessao.setAttribute("consumoHistorico", consumoMedio);

						}

						if(!tipoMedicaoEncontrada.isEmpty()){
							alterarFaturamentoDadosActionForm.setTipoMedicaoSelecionada(((MedicaoTipo) ((List) tipoMedicaoEncontrada)
											.get(0)).getDescricao());
						}


						FiltroMedicaoHistorico filtroMedicaoHistorico = new FiltroMedicaoHistorico();

						filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("funcionario");
						filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraSituacaoAtual");
						filtroMedicaoHistorico.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeInformada");

						filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(
										FiltroMedicaoHistorico.ANO_MES_REFERENCIA_FATURAMENTO, ((Imovel) ((List) imovelEncontrado).get(0))
														.getRota().getFaturamentoGrupo().getAnoMesReferencia()));

						if(idTipoMedicao.trim().equalsIgnoreCase(MedicaoTipo.LIGACAO_AGUA.toString())){

							if(imovel.getLigacaoAgua() == null /*
																 * ||imovel.
																 * getHidrometroInstalacaoHistorico
																 * ().getId().equals(0)
																 */){
								throw new ActionServletException("atencao.imovel.agua.sem_ligacao");

							}else{
								filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.LIGACAO_AGUA_ID,
												codigoImovelDigitadoEnter));
							}
						}else{

							if(imovel.getHidrometroInstalacaoHistorico() == null
											|| imovel.getHidrometroInstalacaoHistorico().getId().equals(0)){
								throw new ActionServletException("atencao.imovel.poco.sem_indicacao");

							}else{
								filtroMedicaoHistorico.adicionarParametro(new ParametroSimples(FiltroMedicaoHistorico.IMOVEL_ID,
												codigoImovelDigitadoEnter));

								httpServletRequest.setAttribute("nomeCampo", "idImovel");
							}
						}

						medicaoHistoricoEncontrada = fachada.pesquisar(filtroMedicaoHistorico, MedicaoHistorico.class.getName());

						if(medicaoHistoricoEncontrada == null || medicaoHistoricoEncontrada.size() == 0){
							if(sessao.getAttribute("medicaoHistoricoGerada") != null){
								medicaoHistoricoEncontrada.add((MedicaoHistorico) sessao.getAttribute("medicaoHistoricoGerada"));
							}
						}

						if(medicaoHistoricoEncontrada != null && !medicaoHistoricoEncontrada.isEmpty()){

							medicaoHistorico = ((MedicaoHistorico) ((List) medicaoHistoricoEncontrada).get(0));

							if(medicaoHistorico.getLeituraSituacaoAtual().getId().equals(LeituraSituacao.CONFIRMADA)){
								alterarFaturamentoDadosActionForm.setIndicadorConfirmacao(ConstantesSistema.CONFIRMADA);
							}

							SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

							String dataLeituraAnterior = "";
							if(medicaoHistorico.getDataLeituraAnteriorFaturamento() != null
											&& !medicaoHistorico.getDataLeituraAnteriorFaturamento().equals("")){
								dataLeituraAnterior = dataFormatada.format(medicaoHistorico.getDataLeituraAnteriorFaturamento());
							}

							String dataLeituraAtual = "";
							if(medicaoHistorico.getDataLeituraAtualInformada() != null
											&& !medicaoHistorico.getDataLeituraAtualInformada().equals("")){
								dataLeituraAtual = dataFormatada.format(medicaoHistorico.getDataLeituraAtualInformada());
							}

							alterarFaturamentoDadosActionForm.setIdLocalidade("" + imovel.getLocalidade().getId());
							alterarFaturamentoDadosActionForm.setNomeLocalidade(imovel.getLocalidade().getDescricao());
							alterarFaturamentoDadosActionForm.setIdSetorComercial("" + imovel.getSetorComercial().getId());
							alterarFaturamentoDadosActionForm.setNomeSetorComercial(imovel.getSetorComercial().getDescricao());
							alterarFaturamentoDadosActionForm.setLeituraAnterior("" + medicaoHistorico.getLeituraAnteriorFaturamento());
							alterarFaturamentoDadosActionForm.setDataLeituraAnterior(dataLeituraAnterior);
							alterarFaturamentoDadosActionForm.setIdLeiturista(medicaoHistorico.getFuncionario() == null ? "" : ""
											+ medicaoHistorico.getFuncionario().getId());
							alterarFaturamentoDadosActionForm.setNomeLeiturista(medicaoHistorico.getFuncionario() == null ? ""
											: medicaoHistorico.getFuncionario().getNome());
							alterarFaturamentoDadosActionForm.setLeituraAtual(medicaoHistorico.getLeituraAtualInformada() == null ? "" : ""
											+ medicaoHistorico.getLeituraAtualInformada());
							alterarFaturamentoDadosActionForm.setDataLeituraAtual(dataLeituraAtual);
							alterarFaturamentoDadosActionForm.setIdSituacaoLeituraAtual(""
											+ medicaoHistorico.getLeituraSituacaoAtual().getId());
							alterarFaturamentoDadosActionForm.setNomeSituacaoLeituraAtual(""
											+ medicaoHistorico.getLeituraSituacaoAtual().getDescricao());
							alterarFaturamentoDadosActionForm
											.setIdAnormalidade(medicaoHistorico.getLeituraAnormalidadeInformada() == null ? "" : ""
															+ medicaoHistorico.getLeituraAnormalidadeInformada().getId());
							alterarFaturamentoDadosActionForm
											.setNomeAnormalidade(medicaoHistorico.getLeituraAnormalidadeInformada() == null ? ""
															: medicaoHistorico.getLeituraAnormalidadeInformada().getDescricao());
							alterarFaturamentoDadosActionForm.setConsumoMedido(medicaoHistorico.getNumeroConsumoMes() == null ? "" : ""
											+ medicaoHistorico.getNumeroConsumoMes());
							alterarFaturamentoDadosActionForm.setConsumoInformado(medicaoHistorico.getNumeroConsumoInformado() == null ? ""
											: "" + medicaoHistorico.getNumeroConsumoInformado());
							alterarFaturamentoDadosActionForm
											.setConsumoCreditoAnterior(medicaoHistorico.getConsumoCreditoAnterior() == null ? "" : ""
															+ medicaoHistorico.getConsumoCreditoAnterior());

							alterarFaturamentoDadosActionForm.setInscricaoImovel(fachada.pesquisarInscricaoImovel(new Integer(
											codigoImovelDigitadoEnter), true));

						}else{

							if(idTipoMedicao.equals(MedicaoTipo.LIGACAO_AGUA.toString())){
								if(imovel.getLigacaoAgua() != null
												&& imovel.getLigacaoAgua().getId().toString().equals(codigoImovelDigitadoEnter)
												&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() == null){
									alterarFaturamentoDadosActionForm.setIdAnormalidade(imovel.getLeituraAnormalidade() == null ? "" : ""
													+ imovel.getLeituraAnormalidade().getId());
									alterarFaturamentoDadosActionForm.setNomeAnormalidade(imovel.getLeituraAnormalidade() == null ? ""
													: imovel.getLeituraAnormalidade().getDescricao());
									httpServletRequest.setAttribute("hidrometroInexistente", "true");

								}else{
									sessao.removeAttribute("medicaoHistoricoGerada");
									MedicaoTipo medicaoTipo = ((MedicaoTipo) ((List) tipoMedicaoEncontrada).get(0));

									sistemaParametro.setAnoMesFaturamento(imovel.getRota().getFaturamentoGrupo().getAnoMesReferencia());

									MedicaoHistorico medicaoHistoricoGerada = fachada.gerarHistoricoMedicao(medicaoTipo, imovel, imovel
													.getRota().getFaturamentoGrupo(), sistemaParametro);

									sessao.setAttribute("medicaoHistoricoGerada", medicaoHistoricoGerada);

									retorno = actionMapping.findForward("rechecarFaturamentoDados");
								}
							}else{
								if(imovel.getHidrometroInstalacaoHistorico() == null){
									alterarFaturamentoDadosActionForm.setIdAnormalidade(imovel.getLeituraAnormalidade() == null ? "" : ""
													+ imovel.getLeituraAnormalidade().getId());
									alterarFaturamentoDadosActionForm.setNomeAnormalidade(imovel.getLeituraAnormalidade() == null ? ""
													: imovel.getLeituraAnormalidade().getDescricao());
									httpServletRequest.setAttribute("hidrometroInexistente", "true");

								}else{
									sessao.removeAttribute("medicaoHistoricoGerada");
									MedicaoTipo medicaoTipo = ((MedicaoTipo) ((List) tipoMedicaoEncontrada).get(0));

									sistemaParametro.setAnoMesFaturamento(imovel.getRota().getFaturamentoGrupo().getAnoMesReferencia());

									MedicaoHistorico medicaoHistoricoGerada = fachada.gerarHistoricoMedicao(medicaoTipo, imovel, imovel
													.getRota().getFaturamentoGrupo(), sistemaParametro);

									sessao.setAttribute("medicaoHistoricoGerada", medicaoHistoricoGerada);

									retorno = actionMapping.findForward("rechecarFaturamentoDados");
								}
							}
						}
					}


					// Verifica se o usuário alterou a Anormalidade de Leitura

					if(codigoAnormalidadeLeituraDigitadoEnter != null && !codigoAnormalidadeLeituraDigitadoEnter.equals("")){
						FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
						filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
										codigoAnormalidadeLeituraDigitadoEnter));

						Collection anormalidadeLeituraEncontrada = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class
										.getName());

						if(anormalidadeLeituraEncontrada != null && !anormalidadeLeituraEncontrada.isEmpty()){

							alterarFaturamentoDadosActionForm.setIdAnormalidade(""
											+ ((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada).get(0)).getId());
							alterarFaturamentoDadosActionForm
											.setNomeAnormalidade(((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada).get(0))
															.getDescricao());

						}else{
							alterarFaturamentoDadosActionForm.setIdAnormalidade("");
							httpServletRequest.setAttribute("idAnormalidadeNaoEncontrada", "true");
							alterarFaturamentoDadosActionForm.setNomeAnormalidade("Anormalidade de leitura inexistente");
							httpServletRequest.setAttribute("nomeCampo", "idAnormalidade");
						}

						sistemaParametro.setAnoMesFaturamento(imovel.getRota().getFaturamentoGrupo().getAnoMesReferencia());
						int qtdeEconomias = fachada.obterQuantidadeEconomias(imovel);
						int[] consumoMedioImovel = fachada.obterConsumoMedioImovel(imovel, sistemaParametro);
						if(consumoMedioImovel != null && consumoMedioImovel.length > 0){
							int consumoMedio = consumoMedioImovel[0];
							int consumoMedio5 = consumoMedio * 5;
							int qtdeEconomias30 = qtdeEconomias * 30;
							httpServletRequest.setAttribute("consumoMedio5", consumoMedio5);
							httpServletRequest.setAttribute("qtdeEconomias30", qtdeEconomias30);
						}
					}

				}else{

					httpServletRequest.setAttribute("nomeCampo", "idImovel");

					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Matrícula do imóvel");

				}
			}
			sessao.setAttribute("alterarFaturamentoDadosActionForm", alterarFaturamentoDadosActionForm);
			sessao.setAttribute("medicaoHistorico", medicaoHistorico);
			sessao.removeAttribute("nomeCampo");

			if(sessao.getAttribute("telaMedicaoConsumoDadosAnt") != null && !sessao.getAttribute("telaMedicaoConsumoDadosAnt").equals("")){
				httpServletRequest.setAttribute("voltar", "S");
			}
		}

		return retorno;
	}

}
