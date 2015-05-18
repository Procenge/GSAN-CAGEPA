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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorContrato;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.FiltroArrecadadorContrato;
import gcom.batch.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.util.FiltroRecursosExternos;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesConfig;
import gcom.util.IoUtil;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel para dar um onLoad no arquivo e importar os ceps.
 * 
 * @author S�vio Luiz
 * @created 24 de Agosto de 2005
 */
public class RegistrarMovimentoArrecadadoresAction
				extends GcomAction {

	private static final String COBRANCA_BANCARIA = "COBRANCA_BANCARIA";

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		Fachada fachada = this.getFachada();

		if(httpServletRequest.getParameter("objetoConsulta") != null && httpServletRequest.getParameter("objetoConsulta").equals("2")){

			Short idArrecadador = null;
			String idTipoMovimento = null;
			String quantidadeLinhas = null;
			Arrecadador arrecadador = null;
			Integer nsaEsperado = null;
			int quantidadeRegistros = 0;
			ArrayList listaProcessos = new ArrayList();
			StringBuilder stringBuilderTxt = null;
			Integer idProcessoIniciadoPrecedente = null;

			File diretorioOrigem = new File(ConstantesConfig.getPathRepositorioArquivosAProcessarArrecadacao());
			File diretorioDestino = null;


			if(diretorioOrigem.listFiles() == null){

				ActionServletException actionServletException = new ActionServletException("atencao.erro_acesso_diretorio",
								ConstantesConfig.getPathRepositorioArquivosAProcessarArrecadacao()
												+
								" - Registrar Movimento dos Arrecadadores");

				actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
				throw actionServletException;
			}

			// File fList[] = diretorioOrigem.listFiles();
			List helperList = this.ordenarListaPorNSAArrecadador(diretorioOrigem.listFiles());

			System.out.println("Numero de arquivos no diretorio : " + helperList.size());

			Iterator<RegistrarMovimentoArrecadadoresHelper> iteratorRegistrarMovimentoArrecadadoresHelper = helperList.iterator();

			while(iteratorRegistrarMovimentoArrecadadoresHelper.hasNext()){

				boolean isDebitoAutomatico = false;

				RegistrarMovimentoArrecadadoresHelper registrarMovimentoArrecadadoresHelper = iteratorRegistrarMovimentoArrecadadoresHelper
								.next();

				// for(int i = 0; i < fList.length; i++){

				System.out.println(registrarMovimentoArrecadadoresHelper.getFileName() + " " + registrarMovimentoArrecadadoresHelper.getUltimaAlteracao());

				String extensao = "";
				String nomeArquivo = registrarMovimentoArrecadadoresHelper.getFileName();
				String[] nomeArquivoPartido = nomeArquivo.split("\\.");

				if(nomeArquivo.startsWith("DA")){
					isDebitoAutomatico = true;
				}

				if(nomeArquivoPartido[1] != null){
					extensao = nomeArquivoPartido[1];
				}

				if(extensao.equals("DEB") || isDebitoAutomatico){
					idTipoMovimento = "DEBITO AUTOMATICO";
				}else if(extensao.equals("ARR") || extensao.equals("REM") || extensao.equals("RET")){
					idTipoMovimento = "CODIGO DE BARRAS";
				}else if(extensao.equals("FCO")){
					idTipoMovimento = "FICHA DE COMPENSACAO";
				}else if(extensao.equals("CBC")){
					idTipoMovimento = "COBRANCA BANCARIA";
				}else{
					idTipoMovimento = "";
				}

				FileInputStream fin = null;
				InputStreamReader reader = null;
				BufferedReader buffer = null;
				stringBuilderTxt = new StringBuilder();
				try{

					fin = new FileInputStream(ConstantesConfig.getPathRepositorioArquivosAProcessarArrecadacao()+"\\"+registrarMovimentoArrecadadoresHelper.getFileName());
					reader = new InputStreamReader(fin);
					buffer = new BufferedReader(reader);
					// cria uma variavel do tipo boolean
					boolean eof = false;
					boolean primeiraLinha = true;
					// enquanto a variavel for false
					while(!eof){
						// pega a linha do arquivo
						String linhaLida = buffer.readLine();
						stringBuilderTxt.append(linhaLida);
						stringBuilderTxt.append("\n");
						quantidadeRegistros = quantidadeRegistros + 1;

						if(primeiraLinha){

							// Precisa ver se todos os arquivos tem a mesma posi��o para essa
							// informa��o.
							String codigoAgenteArrecadador = Util.getConteudo(43, 3, linhaLida.toCharArray());

							FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
							filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE,
											codigoAgenteArrecadador));
							filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadador.CLIENTE);

							Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());


							if(!Util.isVazioOrNulo(colecaoArrecadador)){

								arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);
							}

							idArrecadador = arrecadador.getId().shortValue();

							primeiraLinha = false;
						}

						// se for a ultima linha do arquivo
						if(linhaLida != null && linhaLida.length() > 0 && !linhaLida.equals("")){
							quantidadeLinhas = Util.getConteudo(2, 6, linhaLida.toCharArray());
						}else{
							break;
						}
					}
					

				}catch(FileNotFoundException e1){

					e1.printStackTrace();
				}catch(IOException e){

					e.printStackTrace();
				}finally{
					IoUtil.fecharStream(buffer);
					IoUtil.fecharStream(reader);
					IoUtil.fecharStream(fin);
				}
				
				// Transferir arquivos ...

					if(arrecadador != null){

						nsaEsperado = obterNumeroSequencialEsperado(arrecadador, idTipoMovimento, fachada);
					}

					Usuario usuarioLogado = this.getUsuarioLogado(httpServletRequest);


					Integer codigoProcessoIniciadoGerado = this.inserirProcesso(arrecadador, quantidadeRegistros, idTipoMovimento,
								usuarioLogado, fachada, stringBuilderTxt, nsaEsperado, idProcessoIniciadoPrecedente);

				idProcessoIniciadoPrecedente = codigoProcessoIniciadoGerado;

					listaProcessos.add(codigoProcessoIniciadoGerado);


			}

			if(listaProcessos.size() > 0){

				try{


					File fListDeletar[] = diretorioOrigem.listFiles();

					System.out.println("Numero de arquivos no diretorio : " + fListDeletar.length);

					for(int i = 0; i < fListDeletar.length; i++){

						System.out.println("Removendo do diret�rio � processar : " + fListDeletar[i].getName() + " "
										+ new Date(fListDeletar[i].lastModified()));

						String arquivoDestinoAbsolutPath = FiltroRecursosExternos.DIR_ARQUIVOS_PROCESSADOS_ARRECADACAO
										+ fListDeletar[i].getName();
						diretorioDestino = new File(arquivoDestinoAbsolutPath);

						Util.copyFile(fListDeletar[i], diretorioDestino);

						fListDeletar[i].delete();

					}

				}catch(IOException e){
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// montando p�gina de sucesso
				montarPaginaSucesso(httpServletRequest, "Os Arquivos foram enviados para processamento.", "Voltar",
								"exibirRegistrarMovimentoArredadadoresAction.do");

			}else{

				ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_diretorio",
								String.valueOf(Processo.REGISTRA_MOVIMENTO_ARRECADADORES) + " - Registrar Movimento dos Arrecadadores");

				actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
				throw actionServletException;
			}


		}else{
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

				// cria um contador para saber quantas linhas ter� o txt
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

					// verifica se n�o � diretorio
					if(!item.isFormField()){

						// coloca o nome do item para maiusculo
						String nomeItem = item.getName().toUpperCase();

						// compara o final do nome do arquivo � .DEB se for debito autom�tico
						// ou .ARR se for c�digo de barras
						if((idTipoMovimento != null && idTipoMovimento.equals("DEBITO AUTOMATICO") && nomeItem.endsWith(".DEB"))
										|| (idTipoMovimento != null && idTipoMovimento.equals("CODIGO DE BARRAS"))
										&& nomeItem.endsWith(".ARR") || (idTipoMovimento != null && nomeItem.endsWith(".REM"))
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
									// Integer codigoRegistro = new Integer(linhaLida.substring(7,
									// 8));
									// if (((int) codigoRegistro) >= 0 && ((int) codigoRegistro) <=
									// 9) {

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

								ActionServletException actionServletException = new ActionServletException(
												"atencao.tipo_importacao.nao_deb");
								actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
								throw actionServletException;
							}else if(idTipoMovimento != null && idTipoMovimento.equals("CODIGO DE BARRAS")){

								ActionServletException actionServletException = new ActionServletException(
												"atencao.tipo_importacao.nao_arr");
								actionServletException.setUrlBotaoVoltar("/gsan/exibirRegistrarMovimentoArredadadoresAction.do");
								throw actionServletException;
							}else if(idTipoMovimento != null && idTipoMovimento.equals("FICHA DE COMPENSACAO")){

								ActionServletException actionServletException = new ActionServletException(
												"atencao.tipo_importacao.nao_ficha");
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


				FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
				filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, idArrecadador));
				filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadador.CLIENTE);

				Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());
				Arrecadador arrecadador = null;

				if(!Util.isVazioOrNulo(colecaoArrecadador)){

					arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);
				}

				if(quantidadeRegistros != 0){


					Integer nsaEsperado = null;

					if(arrecadador != null){

						nsaEsperado = obterNumeroSequencialEsperado(arrecadador, idTipoMovimento, fachada);
					}
					

					Usuario usuarioLogado = this.getUsuarioLogado(httpServletRequest);

					Integer codigoProcessoIniciadoGerado = this.inserirProcesso(arrecadador, quantidadeRegistros, idTipoMovimento,
									usuarioLogado, fachada, stringBuilderTxt, nsaEsperado, null);
					
					if(codigoProcessoIniciadoGerado > 0){

						// montando p�gina de sucesso
						montarPaginaSucesso(httpServletRequest, "O Arquivo do arrecadador " + arrecadador.getCodigoAgente()
										+ " foi enviado para processamento.", "Voltar", "exibirRegistrarMovimentoArredadadoresAction.do");
					}else{

						ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
										String.valueOf(Processo.REGISTRA_MOVIMENTO_ARRECADADORES)
														+ " - Registrar Movimento dos Arrecadadores");

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
		}

	

		return retorno;
	}
	
	private List ordenarListaPorNSAArrecadador(File[] listFiles){

		Fachada fachada = getFachada();
		Arrecadador arrecadador = null;
		int quantidadeRegistros = 0;
		List listFilesHelper = new ArrayList();
		RegistrarMovimentoArrecadadoresHelper helper = null;

		for(int i = 0; i < listFiles.length; i++){
			String nomeArquivo = listFiles[i].getName().toUpperCase();
			Date ultimaAlteracao = new Date(listFiles[i].lastModified());
			FileInputStream fin = null;
			InputStreamReader reader = null;
			BufferedReader buffer = null;
			StringBuilder stringBuilderTxt = null;
			stringBuilderTxt = new StringBuilder();
			try{

				fin = new FileInputStream(listFiles[i]);
				reader = new InputStreamReader(fin);
				buffer = new BufferedReader(reader);
				// cria uma variavel do tipo boolean
				boolean primeiraLinha = true;
				// enquanto a variavel for false
				while(primeiraLinha){
					// pega a linha do arquivo
					String linhaLida = buffer.readLine();
					stringBuilderTxt.append(linhaLida);
					stringBuilderTxt.append("\n");
					quantidadeRegistros = quantidadeRegistros + 1;
					String nsa = null;


					if(primeiraLinha){

						// Precisa ver se todos os arquivos tem a mesma posi��o para essa
						// informa��o.
						String codigoAgenteArrecadador = Util.getConteudo(43, 3, linhaLida.toCharArray());
						nsa = Util.getConteudo(76, 4, linhaLida.toCharArray());
						FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
						filtroArrecadador
										.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, codigoAgenteArrecadador));
						filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadador.CLIENTE);

						Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

						if(!Util.isVazioOrNulo(colecaoArrecadador)){

							arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);
						}


						primeiraLinha = false;
					}
					helper = new RegistrarMovimentoArrecadadoresHelper(nsa, nomeArquivo, ultimaAlteracao.toString(), arrecadador);
					listFilesHelper.add(helper);
				}

			}catch(FileNotFoundException e1){

				e1.printStackTrace();
			}catch(IOException e){

				e.printStackTrace();
			}finally{
				IoUtil.fecharStream(buffer);
				IoUtil.fecharStream(reader);
				IoUtil.fecharStream(fin);
			}

		}

		// Ordenando pela data de vencimento
		BeanComparator comparador = new BeanComparator("nsa");
		Collections.sort((List) listFilesHelper, comparador);

		return listFilesHelper;
	}

	/**
	 * @param arrecadador
	 * @param idTipoMovimento
	 * @param fachada
	 * @return
	 */
	
	private Integer obterNumeroSequencialEsperado(Arrecadador arrecadador, String idTipoMovimento, Fachada fachada){

		Integer nsaEsperado = null;

		FiltroArrecadadorContrato filtroArrecadadorContrato = new FiltroArrecadadorContrato();
		filtroArrecadadorContrato.adicionarParametro(new ParametroSimples(FiltroArrecadadorContrato.ARRECADADOR_ID, arrecadador.getId()));
		filtroArrecadadorContrato.adicionarParametro(new ParametroNulo(FiltroArrecadadorContrato.DATA_CONTRATO_ENCERRAMENTO));

		Collection<ArrecadadorContrato> colecaoArrecadadorContrato = fachada.pesquisar(filtroArrecadadorContrato,
						ArrecadadorContrato.class.getName());

		if(!Util.isVazioOrNulo(colecaoArrecadadorContrato)){
			ArrecadadorContrato arrecadadorContrato = (ArrecadadorContrato) Util.retonarObjetoDeColecao(colecaoArrecadadorContrato);

			if(idTipoMovimento != null && idTipoMovimento.equals("DEBITO AUTOMATICO")){
				Integer numeroSequencialArquivoRetornoDebitoAutomatico = arrecadadorContrato
								.getNumeroSequencialArquivoRetornoDebitoAutomatico();

				if(numeroSequencialArquivoRetornoDebitoAutomatico != null){
					nsaEsperado = numeroSequencialArquivoRetornoDebitoAutomatico + 1;
				}
			}else if(idTipoMovimento != null && idTipoMovimento.equals("CODIGO DE BARRAS")){
				Integer numeroSequecialArquivoRetornoCodigoBarras = arrecadadorContrato.getNumeroSequecialArquivoRetornoCodigoBarras();

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

		return nsaEsperado;
	}

	/**
	 * @param arrecadador
	 * @param quantidadeRegistros
	 * @param idTipoMovimento
	 * @param usuarioLogado
	 * @param fachada
	 * @param stringBuilderTxt
	 * @param nsaEsperado
	 * @return
	 */

	private Integer inserirProcesso(Arrecadador arrecadador, Integer quantidadeRegistros, String idTipoMovimento, Usuario usuarioLogado,
					Fachada fachada, StringBuilder stringBuilderTxt, Integer nsaEsperado, Integer idProcessoIniciadoPrecedente){
		
		Integer codigoProcessoIniciadoGerado = null;
		

			ProcessoIniciado processoIniciado = new ProcessoIniciado();

			Processo processo = new Processo();
			processo.setId(Processo.REGISTRA_MOVIMENTO_ARRECADADORES);
			processoIniciado.setDataHoraAgendamento(new Date());

		if(idProcessoIniciadoPrecedente != null){
			ProcessoIniciado processoIniciadoPrecedente = new ProcessoIniciado();
			processoIniciadoPrecedente.setId(idProcessoIniciadoPrecedente);
			processoIniciado.setProcessoIniciadoPrecedente(processoIniciadoPrecedente);

			ProcessoSituacao processoSituacao = new ProcessoSituacao();
			processoSituacao.setId(ProcessoSituacao.AGUARDANDO_PROCESSAMENTO);
			processoIniciado.setProcessoSituacao(processoSituacao);

		}

			// Verificar restri��o de execu��o simult�nea de processos
			if(fachada.isProcessoComRestricaoExecucaoSimultanea(processo.getId())){

				throw new ActionServletException("atencao.processo_restricao_execucao");
			}

			processoIniciado.setProcesso(processo);
		processoIniciado.setUsuario(usuarioLogado);

			List<Object> colecaoParametros = new ArrayList<Object>();
			colecaoParametros.add(stringBuilderTxt);
			colecaoParametros.add(arrecadador);
			colecaoParametros.add(idTipoMovimento);
			colecaoParametros.add(quantidadeRegistros);


			ProcessoIniciadoDadoComplementarHelper helper = new ProcessoIniciadoDadoComplementarHelper();
			helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADADOR_CODIGO_AGENTE, arrecadador.getCodigoAgente());
			helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADACAO_NSA_ESPERADO,
							Util.isVazioOuBranco(nsaEsperado) ? "NA" : nsaEsperado);
			helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADACAO_TIPO_MOVIMENTO, idTipoMovimento);
			helper.adcionarDadoComplementar(DadoComplementarEnumerator.ARRECADACAO_QUANTIDADE_REGISTROS, quantidadeRegistros);
			processoIniciado.setDescricaoDadosComplementares(helper.getStringFormatoPesistencia());

		codigoProcessoIniciadoGerado = (Integer) fachada.inserirProcessoIniciadoOnline(processoIniciado,
							colecaoParametros);
		

		return codigoProcessoIniciadoGerado;

		
	}
}
