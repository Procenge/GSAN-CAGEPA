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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.FiltroArrecadadorContrato;
import gcom.batch.DadoComplementarEnumerator;
import gcom.batch.Processo;
import gcom.batch.ProcessoIniciado;
import gcom.batch.ProcessoIniciadoDadoComplementarHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável para dar um onLoad no arquivo e importar os ceps.
 * 
 * @author Sávio Luiz
 * @created 24 de Agosto de 2005
 */
public class RegistrarMovimentoArrecadadoresAction
				extends GcomAction {

	private static final String COBRANCA_BANCARIA = "COBRANCA_BANCARIA";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		try{

			Short idArrecadador = null;

			String idTipoMovimento = null;

			DiskFileUpload upload = new DiskFileUpload();

			// Parse the request
			List items = upload.parseRequest(httpServletRequest);

			FileItem item = null;

			// cria uma string builder que recupera o txt e joga para o
			// controlador para o processamento
			StringBuilder stringBuilderTxt = new StringBuilder();

			// cria um contador para saber quantas linhas terá o txt
			int quantidadeRegistros = 0;

			// pega uma lista de itens do form
			Iterator iter = items.iterator();
			while(iter.hasNext()){

				item = (FileItem) iter.next();

				if(item.getFieldName().equals("idArrecadador")){
					idArrecadador = Short.valueOf(item.getString());
				}

				if(item.getFieldName().equals("idTipoMovimento")){
					idTipoMovimento = item.getString();
				}

				// verifica se não é diretorio
				if(!item.isFormField()){

					// coloca o nome do item para maiusculo
					String nomeItem = item.getName().toUpperCase();

					// compara o final do nome do arquivo é .DEB se for debito automático
					// ou .ARR se for código de barras
					if((idTipoMovimento != null && idTipoMovimento.equals("DEBITO AUTOMATICO") && nomeItem.endsWith(".DEB"))
									|| (idTipoMovimento != null && idTipoMovimento.equals("CODIGO DE BARRAS")) && nomeItem.endsWith(".ARR")
									|| (idTipoMovimento != null && nomeItem.endsWith(".REM"))
									|| (idTipoMovimento != null && nomeItem.endsWith(".RET"))){

						// abre o arquivo
						InputStreamReader reader = new InputStreamReader(item.getInputStream());
						BufferedReader buffer = new BufferedReader(reader);

						// StringBuffer linha = new StringBuffer();
						// cria uma variavel do tipo boolean
						boolean eof = false;
						// boolean primeiraLinha = true;
						// enquanto a variavel for false
						while(!eof){

							// pega a linha do arquivo
							String linhaLida = buffer.readLine();

							// se for a ultima linha do arquivo
							if(linhaLida != null && linhaLida.length() > 0){
								Character codigoRegistro = linhaLida.substring(0, 1).toUpperCase().charAt(0);
								if(((int) codigoRegistro) >= 65 && ((int) codigoRegistro) <= 90){

									// completa a string para o tamanho de 150 caso necessario.
									String linhaCompleta = null;
									if(linhaLida.length() != 150){
										linhaCompleta = Util.completaString(linhaLida, 150);
									}else{
										linhaCompleta = linhaLida;
									}

									stringBuilderTxt.append(linhaCompleta);
									stringBuilderTxt.append("\n");
									quantidadeRegistros = quantidadeRegistros + 1;
								}else{
									break;
								}
							}else{
								break;
							}

						}

						// fecha o arquivo
						buffer.close();
						reader.close();
						item.getInputStream().close();

					}else if(idTipoMovimento != null
									&& (idTipoMovimento.equals("FICHA DE COMPENSACAO") || idTipoMovimento.equals("COBRANCA BANCARIA"))
									&& nomeItem.endsWith(".BOL")){

						// abre o arquivo
						InputStreamReader reader = new InputStreamReader(item.getInputStream());
						BufferedReader buffer = new BufferedReader(reader);

						// StringBuffer linha = new StringBuffer();
						// cria uma variavel do tipo boolean
						boolean eof = false;
						// boolean primeiraLinha = true;
						// enquanto a variavel for false
						while(!eof){

							// pega a linha do arquivo
							String linhaLida = buffer.readLine();

							// se for a ultima linha do arquivo
							if(linhaLida != null && linhaLida.length() > 0){
								// Integer codigoRegistro = new Integer(linhaLida.substring(7, 8));
								// if (((int) codigoRegistro) >= 0 && ((int) codigoRegistro) <= 9) {

								// completa a string para o tamanho de 240 caso necessario.
								String linhaCompleta = null;
								if(linhaLida.length() != 240){
									linhaCompleta = Util.completaString(linhaLida, 240);
								}else{
									linhaCompleta = linhaLida;
								}

								stringBuilderTxt.append(linhaCompleta);
								stringBuilderTxt.append("\n");
								quantidadeRegistros = quantidadeRegistros + 1;
								// } else {
								// break;
								// }
							}else{
								break;
							}

						}

						// fecha o arquivo
						buffer.close();
						reader.close();
						item.getInputStream().close();

					}else{

						if(idTipoMovimento != null && idTipoMovimento.equals("DEBITO AUTOMATICO")){

							ActionServletException actionServletException = new ActionServletException("atencao.tipo_importacao.nao_deb");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
							throw actionServletException;
						}else if(idTipoMovimento != null && idTipoMovimento.equals("CODIGO DE BARRAS")){

							ActionServletException actionServletException = new ActionServletException("atencao.tipo_importacao.nao_arr");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
							throw actionServletException;
						}else if(idTipoMovimento != null && idTipoMovimento.equals("FICHA DE COMPENSACAO")){

							ActionServletException actionServletException = new ActionServletException("atencao.tipo_importacao.nao_ficha");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
							throw actionServletException;
						}else if(idTipoMovimento != null && idTipoMovimento.equals(COBRANCA_BANCARIA)){

							ActionServletException actionServletException = new ActionServletException(
											"atencao.tipo_importacao.nao_boleto_bancario");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
							throw actionServletException;
						}
					}

				}
			}

			Fachada fachada = this.getFachada();
			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, idArrecadador));
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadador.CLIENTE);

			Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());
			Arrecadador arrecadador = null;

			if(!Util.isVazioOrNulo(colecaoArrecadador)){

				arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);
			}

			if(quantidadeRegistros != 0){

				ProcessoIniciado processoIniciado = new ProcessoIniciado();

				Processo processo = new Processo();
				processo.setId(Processo.REGISTRA_MOVIMENTO_ARRECADADORES);
				processoIniciado.setDataHoraAgendamento(new Date());

				// Verificar restrição de execução simultânea de processos
				if(fachada.isProcessoComRestricaoExecucaoSimultanea(processo.getId())){

					throw new ActionServletException("atencao.processo_restricao_execucao");
				}

				processoIniciado.setProcesso(processo);
				processoIniciado.setUsuario(this.getUsuarioLogado(httpServletRequest));

				List<Object> colecaoParametros = new ArrayList<Object>();
				colecaoParametros.add(stringBuilderTxt);
				colecaoParametros.add(arrecadador);
				colecaoParametros.add(idTipoMovimento);
				colecaoParametros.add(quantidadeRegistros);

				Integer nsaEsperado = null;

				if(arrecadador != null){
					FiltroArrecadadorContrato filtroArrecadadorContrato = new FiltroArrecadadorContrato();
					filtroArrecadadorContrato.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.ARRECADADOR_ID, arrecadador
									.getId()));
					filtroArrecadadorContrato.adicionarParametro(new ParametroNulo(FiltroArrecadadorContrato.DATA_CONTRATO_ENCERRAMENTO));

					Collection<ArrecadadorContrato> colecaoArrecadadorContrato = fachada.pesquisar(filtroArrecadadorContrato,
									ArrecadadorContrato.class.getName());

					if(!Util.isVazioOrNulo(colecaoArrecadadorContrato)){
						ArrecadadorContrato arrecadadorContrato = (ArrecadadorContrato) Util
										.retonarObjetoDeColecao(colecaoArrecadadorContrato);


						if(idTipoMovimento != null && idTipoMovimento.equals("DEBITO AUTOMATICO")){
							Integer numeroSequencialArquivoRetornoDebitoAutomatico = arrecadadorContrato
											.getNumeroSequencialArquivoRetornoDebitoAutomatico();

							if(numeroSequencialArquivoRetornoDebitoAutomatico != null){
								nsaEsperado = numeroSequencialArquivoRetornoDebitoAutomatico + 1;
							}
						}else if(idTipoMovimento != null && idTipoMovimento.equals("CODIGO DE BARRAS")){
							Integer numeroSequecialArquivoRetornoCodigoBarras = arrecadadorContrato
											.getNumeroSequecialArquivoRetornoCodigoBarras();

							if(numeroSequecialArquivoRetornoCodigoBarras != null){
								nsaEsperado = numeroSequecialArquivoRetornoCodigoBarras + 1;
							}
						}else if(idTipoMovimento != null && idTipoMovimento.equals("FICHA DE COMPENSACAO")){
							Integer numeroSequencialArquivoRetornoFichaCompensacao = arrecadadorContrato
											.getNumeroSequencialArquivoRetornoFichaCompensacao();

							if(numeroSequencialArquivoRetornoFichaCompensacao != null){
								nsaEsperado = numeroSequencialArquivoRetornoFichaCompensacao + 1;
							}
						}else if(idTipoMovimento != null && idTipoMovimento.equals(COBRANCA_BANCARIA)){
							Integer numeroSequencialArquivoRetornoBoleto = arrecadadorContrato.getNumeroSequencialArquivoRetornoBoleto();

							if(numeroSequencialArquivoRetornoBoleto != null){
								nsaEsperado = numeroSequencialArquivoRetornoBoleto + 1;
							}
						}
					}
				}

				ProcessoIniciadoDadoComplementarHelper helper = new ProcessoIniciadoDadoComplementarHelper();
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADADOR_CODIGO_AGENTE,
								arrecadador.getCodigoAgente());
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADACAO_NSA_ESPERADO,
								Util.isVazioOuBranco(nsaEsperado) ? "NA" : nsaEsperado);
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADACAO_TIPO_MOVIMENTO, idTipoMovimento);
				helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADACAO_QUANTIDADE_REGISTROS,
								quantidadeRegistros);
				processoIniciado.setDescricaoDadosComplementares(helper.getStringFormatoPesistencia());

				Integer codigoProcessoIniciadoGerado = (Integer) fachada.inserirProcessoIniciadoOnline(processoIniciado, colecaoParametros);

				if(codigoProcessoIniciadoGerado > 0){

					// montando página de sucesso
					montarPaginaSucesso(httpServletRequest, "O Arquivo do arrecadador " + arrecadador.getCodigoAgente()
									+ " foi enviado para processamento.", "Voltar", "exibirRegistrarMovimentoArredadadoresAction.do");
				}else{

					ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
									String.valueOf(Processo.REGISTRA_MOVIMENTO_ARRECADADORES) + " - Registrar Movimento dos Arrecadadores");

					actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
					throw actionServletException;
				}

			}else{
				ActionServletException actionServletException = new ActionServletException("atencao.importacao.nao_concluida");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
				throw actionServletException;
			}

		}catch(IOException ex){
			throw new ActionServletException("erro.importacao.nao_concluida");

		}catch(NumberFormatException ex){
			throw new ActionServletException("erro.importacao.nao_concluida");
		}catch(FileUploadException e){
			throw new ActionServletException("erro.sistema", e);
		}

		return retorno;
	}
}
