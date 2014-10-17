/* Feito por Felipe e Rafael na data 16/12/2005*/

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

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.micromedicao.consumo.ConsumoHistorico;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraSituacao;
import gcom.micromedicao.medicao.FiltroMedicaoTipo;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action para exibir a página de endereço
 * 
 * @author Felipe
 */
public class AlterarFaturamentoDadosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = null;

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("telaMedicaoConsumoDadosAnt") != null && !sessao.getAttribute("telaMedicaoConsumoDadosAnt").equals("")){
			retorno = actionMapping.findForward("exibirDadosAnalise");
		}else{
			retorno = actionMapping.findForward("telaSucesso");
		}

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		AlterarFaturamentoDadosActionForm alterarFaturamentoDadosActionForm = (AlterarFaturamentoDadosActionForm) actionForm;

		String idImovel = alterarFaturamentoDadosActionForm.getIdImovel();
		String leituraAnterior = alterarFaturamentoDadosActionForm.getLeituraAnterior();
		String dataLeituraAnteriorInformada = alterarFaturamentoDadosActionForm.getDataLeituraAnterior();
		String leituraAtual = alterarFaturamentoDadosActionForm.getLeituraAtual();
		String dataLeituraAtualInformada = alterarFaturamentoDadosActionForm.getDataLeituraAtual();
		String indicadorConfirmacao = alterarFaturamentoDadosActionForm.getIndicadorConfirmacao();
		String idAnormalidadeLeitura = alterarFaturamentoDadosActionForm.getIdAnormalidade();
		String consumoInformado = alterarFaturamentoDadosActionForm.getConsumoInformado();
		String consumoCreditoAnterior = alterarFaturamentoDadosActionForm.getConsumoCreditoAnterior();
		Integer consumoMedioH = null;
		Integer consumoMedioAtualizar = null;

		if(alterarFaturamentoDadosActionForm.getConsumoMedio() != null && !alterarFaturamentoDadosActionForm.getConsumoMedio().equals("")){

			consumoMedioH = Integer.parseInt(alterarFaturamentoDadosActionForm.getConsumoMedio());
		}

		String tipoMedicao = alterarFaturamentoDadosActionForm.getTipoMedicao();

		String tipoMedicaoSelecionada = alterarFaturamentoDadosActionForm.getTipoMedicaoSelecionada();

		Imovel imovel = (Imovel) sessao.getAttribute("imovel");

		MedicaoHistorico medicaoHistorico = (MedicaoHistorico) sessao.getAttribute("medicaoHistorico");

		LeituraSituacao leituraSituacao = new LeituraSituacao();

		// testa para ver se o tipo de medicao foi alterado
		if(tipoMedicao != null && tipoMedicaoSelecionada != null){
			FiltroMedicaoTipo filtroMedicaoTipo = new FiltroMedicaoTipo();
			filtroMedicaoTipo.adicionarParametro(new ParametroSimples(FiltroMedicaoTipo.ID, tipoMedicao));
			Collection colecaoTipomedicao = fachada.pesquisar(filtroMedicaoTipo, MedicaoTipo.class.getName());
			MedicaoTipo medicaoTipo = (MedicaoTipo) Util.retonarObjetoDeColecao(colecaoTipomedicao);

			if(!medicaoTipo.getDescricao().trim().equalsIgnoreCase(tipoMedicaoSelecionada.trim())){
				throw new ActionServletException("atencao.tipoMedicao.alterado");
			}
		}

		if(imovel != null){
			// Comparando o imóvel filtrado com o informado pelo usuário
			if(imovel.getId().toString().equals(idImovel)){

				/*
				 * [UC0107] Registrar Transação
				 */
				RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_CONSULTAR_DADOS_FATURAMENTO, imovel
								.getId(), imovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
								UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				Operacao operacao = new Operacao();
				operacao.setId(Operacao.OPERACAO_CONSULTAR_DADOS_FATURAMENTO);

				OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
				operacaoEfetuada.setOperacao(operacao);
				// [UC0107] -Fim- Registrar Transação

				// regitrando operacao
				imovel.setOperacaoEfetuada(operacaoEfetuada);
				imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
				registradorOperacao.registrarOperacao(imovel);

				SimpleDateFormat dataFormatada = new SimpleDateFormat("dd/MM/yyyy");

				Date dataLeituraAtual = null;
				Date dataLeituraAnterior = null;

				int qtdeEconomias = fachada.obterQuantidadeEconomias(imovel);


				if(consumoMedioH != null){

					if(sessao.getAttribute("consumoHistorico") != null){

						Integer consumoHistorico = (Integer) sessao.getAttribute("consumoHistorico");

						if(consumoMedioH != consumoHistorico){

							consumoMedioAtualizar = atualizarValorMedio(qtdeEconomias, consumoMedioH);
							
						}
					}else{

						consumoMedioAtualizar = atualizarValorMedio(qtdeEconomias, consumoMedioH);

					}
				}

				if(consumoMedioAtualizar != null){

					ConsumoHistorico consumoHistorico = null;

					try{
						consumoHistorico = this.getFachada().obterUltimoConsumoHistoricoImovel(idImovel);

					}catch(ControladorException e){

						throw new ActionServletException("erro.sistema");
					}

					if(consumoHistorico != null){

						try{
							this.getFachada().atualizarUltimoConsumoHistorico(consumoHistorico.getId(), consumoMedioAtualizar);

						}catch(ControladorException e){

							throw new ActionServletException("erro.sistema");
						}

					}

				}

				String anoMesReferencia = "" + medicaoHistorico.getAnoMesReferencia();

				if(imovel.getHidrometroInstalacaoHistorico() != null
								|| (imovel.getLigacaoAgua() != null && imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null)){

					if(dataLeituraAtualInformada != null && !dataLeituraAtualInformada.equals("")){

						try{
							dataLeituraAtual = dataFormatada.parse(dataLeituraAtualInformada);
						}catch(ParseException ex){
							throw new ActionServletException("erro.sistema");
						}
						Calendar dataAtual = new GregorianCalendar();
						dataAtual.setTime(dataLeituraAtual);

						String anoMesAtual = "" + dataAtual.get(Calendar.YEAR);
						String mes = "" + (dataAtual.get(Calendar.MONTH) + 1);

						if(!(mes.length() == 2)){
							mes = "0" + mes;
						}

						anoMesAtual += mes;

						String anoMesReferenciaMaisUmMes = "" + Util.somarData(medicaoHistorico.getAnoMesReferencia());

						// Comparando a data atual informada no form com o ano
						// mês
						// referência e com o ano mês seguinte
						/*
						 * if (!((Util.compararAnoMesReferencia(new Integer(
						 * anoMesReferencia), new Integer(anoMesAtual),
						 * "=")) || (Util.compararAnoMesReferencia(
						 * new Integer(anoMesReferenciaMaisUmMes),
						 * new Integer(anoMesAtual), "=")))) {
						 */
						boolean mesAnoValido = fachada.validaDataFaturamentoIncompativel(anoMesReferencia, anoMesAtual);
						if(!mesAnoValido){
							sessao.setAttribute("nomeCampo", "dataLeituraAtual");
							sessao.setAttribute("nomeCampo", "dataLeituraAtual");
							throw new ActionServletException("atencao.faturamento_data_incompativel", null,
											"Data de Leitura Atual Informada");
						}

						if(dataLeituraAnteriorInformada != null && !dataLeituraAnteriorInformada.equals("")){
							try{
								dataLeituraAnterior = dataFormatada.parse(dataLeituraAnteriorInformada);
							}catch(ParseException ex){
								throw new ActionServletException("erro.sistema");
							}
							Calendar dataAnterior = new GregorianCalendar();
							dataAnterior.setTime(dataLeituraAnterior);

							// Comparando a data atual informada com a data
							// anterior informada, garantindo que a data atual
							// seja posterior à data anterior.
							if(dataAtual.compareTo(dataAnterior) < 0){
								sessao.setAttribute("nomeCampo", "dataLeituraAtual");
								throw new ActionServletException("atencao.faturamento_data_atual_inferior_data_anterior");
							}
						}
						medicaoHistorico.setDataLeituraAtualFaturamento(dataLeituraAtual);
						medicaoHistorico.setDataLeituraAtualInformada(dataLeituraAtual);
					}

					if(dataLeituraAnteriorInformada != null && !dataLeituraAnteriorInformada.equals("")){
						try{
							dataLeituraAnterior = dataFormatada.parse(dataLeituraAnteriorInformada);
						}catch(ParseException ex){
							throw new ActionServletException("erro.sistema");
						}
						Calendar dataAnterior = new GregorianCalendar();
						dataAnterior.setTime(dataLeituraAnterior);
						String anoMesAnterior = "" + dataAnterior.get(Calendar.YEAR);
						String mesAnterior = "" + (dataAnterior.get(Calendar.MONTH) + 1);

						if(!(mesAnterior.length() == 2)){
							mesAnterior = "0" + mesAnterior;
						}

						anoMesAnterior += mesAnterior;

						String anoMesReferenciaMenosUmMes = "" + Util.subtrairData(medicaoHistorico.getAnoMesReferencia());

						// Comparando a data anterior faturada no form com o ano
						// mês
						// referência e com o ano mês anterior
						/*
						 * if (!((Util.compararAnoMesReferencia(new Integer(
						 * anoMesReferencia), new Integer(anoMesAnterior),
						 * "=")) || (Util.compararAnoMesReferencia(
						 * new Integer(anoMesReferenciaMenosUmMes),
						 * new Integer(anoMesAnterior), "=")))) {
						 */
						boolean anoMesInferiorValido = fachada.validaDataFaturamentoIncompativelInferior(anoMesReferencia, anoMesAnterior);
						if(!anoMesInferiorValido){
							sessao.setAttribute("nomeCampo", "dataLeituraAnterior");

							sessao.setAttribute("nomeCampo", "dataLeituraAnterior");

							throw new ActionServletException("atencao.faturamento_data_incompativel", null,
											"Data de Leitura Anterior de Faturamento");
						}

						medicaoHistorico.setDataLeituraAnteriorFaturamento(dataLeituraAnterior);

					}
					// Testando se o número de dígitos do hidrometro é menor que
					// o
					// número de dígitos da leitura
					/*
					 * if ((imovel.getHidrometroInstalacaoHistorico() == null ? imovel
					 * .getLigacaoAgua()
					 * .getHidrometroInstalacaoHistorico().getHidrometro()
					 * .getNumeroDigitosLeitura().intValue()
					 * : imovel.getHidrometroInstalacaoHistorico()
					 * .getHidrometro().getNumeroDigitosLeitura()
					 * .intValue()) <= leituraAtual.length()) {
					 * medicaoHistorico.setLeituraAtualInformada(leituraAtual
					 * .equals("") ? null : new Integer(leituraAtual));
					 * } else {
					 * sessao.setAttribute("nomeCampo", "leituraAtual");
					 * throw new ActionServletException(
					 * "atencao.digitos.leitura.maior.hidrometro");
					 * }
					 */

					if(((imovel.getHidrometroInstalacaoHistorico() == null ? imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico()
									.getHidrometro().getNumeroDigitosLeitura() : imovel.getHidrometroInstalacaoHistorico().getHidrometro()
									.getNumeroDigitosLeitura()) >= leituraAtual.length())
									&& ((imovel.getHidrometroInstalacaoHistorico() == null ? imovel.getLigacaoAgua()
													.getHidrometroInstalacaoHistorico().getHidrometro().getNumeroDigitosLeitura()
													.intValue() : imovel.getHidrometroInstalacaoHistorico().getHidrometro()
													.getNumeroDigitosLeitura().intValue()) >= leituraAnterior.length())){

						medicaoHistorico.setLeituraAtualInformada(leituraAtual.equals("") ? null : new Integer(leituraAtual));

						if(!("" + medicaoHistorico.getLeituraAnteriorFaturamento()).equals(leituraAnterior)){
							LeituraSituacao leituraSituacaoAnterior = new LeituraSituacao();
							leituraSituacaoAnterior.setId(LeituraSituacao.LEITURA_ALTERADA);
							medicaoHistorico.setLeituraSituacaoAnterior(leituraSituacaoAnterior);
						}

						if(Util.isNaoNuloBrancoZero(leituraAnterior)){
							medicaoHistorico.setLeituraAnteriorFaturamento(new Integer(leituraAnterior));
						}
					}else{

						sessao.setAttribute("nomeCampo", "leituraAtual");
						sessao.setAttribute("nomeCampo", "leituraAnterior");

						throw new ActionServletException("atencao.digitos.leitura.maior.hidrometro");
					}

					// /////////////////////////////////////////////////////////////////////////////////
					// [FS0007] Validar Consumo Informado do Mês

					String confirmado = httpServletRequest.getParameter("confirmado");

					if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
						try{
							int consumo = 0;
							int consumoMedioMes = 0;
							if(alterarFaturamentoDadosActionForm.getConsumoMedido() != null
											&& !alterarFaturamentoDadosActionForm.getConsumoMedido().equals("")){
								consumoMedioMes = new Integer(alterarFaturamentoDadosActionForm.getConsumoMedido());
							}

							SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
							imovel.setId(new Integer(idImovel));

							int[] consumoMedioImovel = fachada.obterConsumoMedioImovel(imovel, sistemaParametro);

							if(consumoMedioImovel != null && consumoMedioImovel.length > 0){

								if(consumoInformado != null && !consumoInformado.equals("")){
									consumo = new Integer(consumoInformado);
								}

								int consumoMedio = consumoMedioImovel[0];
								int consumoMedio5 = consumoMedio * 5;
								int qtdeEconomias30 = qtdeEconomias * 30;
								if(consumo > consumoMedio5 && consumo > qtdeEconomias30 && consumo > consumoMedioMes){

									httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/alterarDadosFaturamentoAction.do");
									// Monta a página de confirmação para perguntar se o usuário
									// quer aceitar o consumo informado superior a cinco vezes ao
									// consumo médio do imóvel e superior a trinta vezes a
									// quantidade de economias
									// e superior ao consumo médio

									return montarPaginaConfirmacao("atencao.invalido.consumo", httpServletRequest, actionMapping, null,
													null);
									// }
									/*
									 * throw new ActionServletException(
									 * "atencao.invalido.consumo");
									 */
								}
							}
						}catch(ControladorException e){
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

					// ////////////////////////////////////////////////////////////////////////////////////////

					if(idAnormalidadeLeitura != null && !idAnormalidadeLeitura.equals("")){
						FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
						filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
										idAnormalidadeLeitura));

						Collection anormalidadeLeituraEncontrada = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class
										.getName());
						if(anormalidadeLeituraEncontrada != null && !anormalidadeLeituraEncontrada.isEmpty()){

							medicaoHistorico.setLeituraAnormalidadeInformada(((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada)
											.get(0)));
							medicaoHistorico
											.setLeituraAnormalidadeFaturamento(((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada)
															.get(0)));

						}else{

							sessao.setAttribute("nomeCampo", "idAnormalidade");

							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Anormalidade de Leitura");
						}

					}else{
						medicaoHistorico.setLeituraAnormalidadeInformada(null);
						medicaoHistorico.setLeituraAnormalidadeFaturamento(null);
					}

					if(!leituraAtual.equals("") && new Integer(leituraAtual).intValue() == 0){
						leituraSituacao.setId(LeituraSituacao.NAO_REALIZADA);

					}else{

						if(indicadorConfirmacao.equals(ConstantesSistema.CONFIRMADA)){

							leituraSituacao.setId(LeituraSituacao.CONFIRMADA);

						}else{

							leituraSituacao.setId(LeituraSituacao.REALIZADA);

						}

					}

					medicaoHistorico.setImovel(imovel);
					medicaoHistorico.setLeituraSituacaoAtual(leituraSituacao);
					if(consumoInformado != null && !consumoInformado.equalsIgnoreCase("")){
						medicaoHistorico.setNumeroConsumoInformado(new Integer(consumoInformado));
					}

					if(consumoCreditoAnterior != null && !consumoCreditoAnterior.equals("")){
						medicaoHistorico.setConsumoCreditoAnterior(new Integer(consumoCreditoAnterior));
					}

					// regitrando operacao
					medicaoHistorico.setOperacaoEfetuada(operacaoEfetuada);
					medicaoHistorico.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
					registradorOperacao.registrarOperacao(medicaoHistorico);

					if(sessao.getAttribute("medicaoHistoricoGerada") != null){
						fachada.inserir(medicaoHistorico);
						sessao.removeAttribute("medicaoHistoricoGerada");
					}else{
						fachada.atualizarMedicaoHistorico(medicaoHistorico);
					}
				}else{
					if(idAnormalidadeLeitura != null && !idAnormalidadeLeitura.equals("")){
						FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
						filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
										idAnormalidadeLeitura));

						Collection anormalidadeLeituraEncontrada = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class
										.getName());

						if(anormalidadeLeituraEncontrada != null && !anormalidadeLeituraEncontrada.isEmpty()){

							// Comparar a anormalidade
							if(((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada).get(0)).getIndicadorImovelSemHidrometro()
											.equals(new Short("2"))){

								sessao.setAttribute("nomeCampo", "idAnormalidade");

								throw new ActionServletException("atencao.leitura.anormalidade.nao.permitida");

							}else{
								imovel.setLeituraAnormalidade((LeituraAnormalidade) ((List) anormalidadeLeituraEncontrada).get(0));
							}
						}else{

							sessao.setAttribute("nomeCampo", "idAnormalidade");

							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Anormalidade de Leitura");
						}

					}else{
						imovel.setLeituraAnormalidade(null);
						imovel.setUltimaAlteracao(new Date());
					}

					fachada.atualizar(imovel);

				}

			}else{

				sessao.setAttribute("nomeCampo", "idImovel");
				httpServletRequest.setAttribute("sucesso", "Dados do faturamento atualizados com sucesso");

				throw new ActionServletException("atencao.imovel.alterado");
			}
			
			/*
			 * [UC0107] Registrar Transação
			 */
			RegistradorOperacao registradorOperacaoAlterarDados = new RegistradorOperacao(Operacao.OPERACAO_ALTERAR_DADOS_FATURAMENTO,
							imovel.getId(), imovel.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacaoAlterarDados = new Operacao();
			operacaoAlterarDados.setId(Operacao.OPERACAO_ALTERAR_DADOS_FATURAMENTO);

			OperacaoEfetuada operacaoEfetuadaAlterarDados = new OperacaoEfetuada();
			operacaoEfetuadaAlterarDados.setOperacao(operacaoAlterarDados);

			// regitrando operacao
			imovel.setOperacaoEfetuada(operacaoEfetuadaAlterarDados);
			imovel.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacaoAlterarDados.registrarOperacao(imovel);
			
			
			if(retorno.getName().equalsIgnoreCase("telaSucesso")){

				montarPaginaSucesso(httpServletRequest, "Dados do faturamento do imóvel " + imovel.getId() + " atualizados com sucesso.",
								"Atualizar outros dados para faturamento", "exibirDadosFaturamentoAction.do?menu=sim");
			}

		}
		sessao.removeAttribute("telaMedicaoConsumoDadosAnt");
		sessao.removeAttribute("cosumoMedio");

		return retorno;
	}

	private Integer atualizarValorMedio(Integer qtdeEconomias, Integer valorAtualizado){

		Integer consumoMedioAtualizar = null;

		consumoMedioAtualizar = valorAtualizado / qtdeEconomias;

		if(consumoMedioAtualizar == ConstantesSistema.ZERO.intValue()){

			consumoMedioAtualizar = qtdeEconomias;

		}else{

			consumoMedioAtualizar = consumoMedioAtualizar * qtdeEconomias;
		}

		return consumoMedioAtualizar;
	}

}
