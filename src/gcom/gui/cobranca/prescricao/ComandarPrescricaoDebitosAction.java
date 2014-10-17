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

package gcom.gui.cobranca.prescricao;

import gcom.batch.Processo;
import gcom.cadastro.localidade.*;
import gcom.cobranca.bean.ComandoDebitosPrescritosHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3118] Inserir Comando de Simulação de Faturamento
 * 
 * @author Anderson Italo
 * @date 17/12/2013
 */
public class ComandarPrescricaoDebitosAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		ComandarPrescricaoDebitosActionForm formulario = (ComandarPrescricaoDebitosActionForm) actionForm;
		boolean parametroInformado = false;
		ComandoDebitosPrescritosHelper comandoHelper = new ComandoDebitosPrescritosHelper();
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Título do Comando
		if(!Util.isVazioOuBranco(formulario.getTitulo())){

			comandoHelper.setTitulo(formulario.getTitulo());
		}

		// Descrição da Solicitação
		if(!Util.isVazioOuBranco(formulario.getTitulo())){

			comandoHelper.setDescricaoSolicitacao(formulario.getDescricaoSolicitacao());
		}

		// Indicador de Simulação
		comandoHelper.setIndicadorSimulacao(Util.obterShort(formulario.getIndicadorSimulacao()));

		GerenciaRegional gerenciaRegional = null;

		// Gerência Regional
		if(!Util.isVazioOuBranco(formulario.getIdGerenciaRegional())){

			parametroInformado = true;
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Util.obterInteger(formulario
							.getIdGerenciaRegional())));

			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());
			gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerenciaRegional);

			comandoHelper.setIdGerenciaRegional(gerenciaRegional.getId());
		}

		UnidadeNegocio unidadeNegocio = null;

		// Unidade de Negócio
		if(!Util.isVazioOuBranco(formulario.getIdUnidadeNegocio())){

			parametroInformado = true;
			FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
			filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, Util.obterInteger(formulario
							.getIdUnidadeNegocio())));

			Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());
			unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoUnidadeNegocio);

			comandoHelper.setIdUnidadeNegocio(unidadeNegocio.getId());
		}
		
		// Localidade Elo
		if (!Util.isVazioOuBranco(formulario.getIdLocalidadeElo())){
			
			parametroInformado = true;
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO_ID, Util.obterInteger(formulario.getIdLocalidadeElo())));

			// Recupera Localidade
			Collection colecaoLocalidadeElo = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
			
			if(colecaoLocalidadeElo == null || colecaoLocalidadeElo.isEmpty()){

				ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null,
								"Elo");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

				throw actionServletException;
			}else{
				
				Localidade elo = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidadeElo);
				comandoHelper.setIdElo(elo.getId());
			}
		}

		// Localidade Inicial
		if(Util.verificarIdNaoVazio(formulario.getIdLocalidadeInicial())){

			// [FS0002] – Verificar existência da localidade
			FiltroLocalidade filtroLocalidade = null;
			FiltroSetorComercial filtroSetorComercial = null;
			FiltroQuadra filtroQuadra = null;
			Collection colecaoLocalidade = null;
			Collection colecaoSetorComercial = null;
			Collection colecaoQuadra = null;

			// Localidade Inicial
			if(!Util.isVazioOuBranco(formulario.getIdLocalidadeInicial())){

				parametroInformado = true;
				Localidade localidadeInicial = null;

				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Util.obterInteger(formulario
								.getIdLocalidadeInicial())));

				colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){

					ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null,
									"Localidade Inicial");
					actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

					throw actionServletException;
				}else{

					localidadeInicial = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
				}

				comandoHelper.setIdLocalidadeInicial(localidadeInicial.getId());
				comandoHelper.setIdLocalidadeFinal(localidadeInicial.getId());

				// Setor Comercial Inicial
				SetorComercial setorComercialInicial = null;
				if(!Util.isVazioOuBranco(formulario.getCodigoSetorComercialInicial())){

					filtroSetorComercial = new FiltroSetorComercial();
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Util
									.obterInteger(formulario.getCodigoSetorComercialInicial())));
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeInicial
									.getId()));

					colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){

						ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null,
										"Setor Comercial Inicial");
						actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

						throw actionServletException;
					}else{
						setorComercialInicial = (SetorComercial) gcom.util.Util.retonarObjetoDeColecao(colecaoSetorComercial);
					}

					comandoHelper.setIdSetorComercialInicial(setorComercialInicial.getId());
					comandoHelper.setCodigoSetorComercialInicial(setorComercialInicial.getCodigo());
					comandoHelper.setIdSetorComercialFinal(setorComercialInicial.getId());
					comandoHelper.setCodigoSetorComercialFinal(setorComercialInicial.getCodigo());

					// Quadra Inicial
					Quadra quadraInicial = null;
					if(!Util.isVazioOuBranco(formulario.getNumeroQuadraInicial())){

						filtroQuadra = new FiltroQuadra();
						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util.obterInteger(formulario
										.getNumeroQuadraInicial())));
						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, comandoHelper
										.getIdSetorComercialInicial()));

						colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

						if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

							ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente",
											null, "Quadra Inicial");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

							throw actionServletException;
						}else{
							quadraInicial = (Quadra) gcom.util.Util.retonarObjetoDeColecao(colecaoQuadra);
						}

						comandoHelper.setIdQuadraInicial(quadraInicial.getId());
						comandoHelper.setNumeroQuadraInicial(quadraInicial.getNumeroQuadra());
						comandoHelper.setIdQuadraFinal(quadraInicial.getId());
						comandoHelper.setNumeroQuadraFinal(quadraInicial.getNumeroQuadra());
					}
				}

				// Localidade Final
				Localidade localidadeFinal = null;
				if(!Util.isVazioOuBranco(formulario.getIdLocalidadeFinal())){

					filtroLocalidade = null;
					colecaoLocalidade = null;

					filtroLocalidade = new FiltroLocalidade();
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Util.obterInteger(formulario
									.getIdLocalidadeFinal())));
					if(!Util.isVazioOuBranco(gerenciaRegional)){

						filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_GERENCIA, gerenciaRegional.getId()));
					}

					colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){

						ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null,
										"Localidade Final");
						actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

						throw actionServletException;
					}else{
						localidadeFinal = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
					}

					if(localidadeInicial.getId().intValue() > localidadeFinal.getId().intValue()){

						ActionServletException actionServletException = new ActionServletException(
										"atencao.localidafinal.menor.localidadeinicial", null, "");
						actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

						throw actionServletException;
					}

					comandoHelper.setIdLocalidadeFinal(localidadeFinal.getId());

					// Setor Comercial Final
					SetorComercial setorComercialFinal = null;
					if(!Util.isVazioOuBranco(formulario.getCodigoSetorComercialFinal())){

						filtroSetorComercial = null;
						colecaoSetorComercial = null;

						filtroSetorComercial = new FiltroSetorComercial();
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Util
										.obterInteger(formulario.getCodigoSetorComercialFinal())));
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeFinal
										.getId()));

						colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

						if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){

							ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente",
											null, "Setor Comercial Final");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

							throw actionServletException;
						}else{
							setorComercialFinal = (SetorComercial) gcom.util.Util.retonarObjetoDeColecao(colecaoSetorComercial);
						}

						if(setorComercialInicial.getCodigo() > setorComercialFinal.getCodigo()){

							ActionServletException actionServletException = new ActionServletException(
											"atencao.setor.comercial.final.maior.setor.comercial.inicial", null, "");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

							throw actionServletException;
						}

						comandoHelper.setIdSetorComercialFinal(setorComercialFinal.getId());
						comandoHelper.setCodigoSetorComercialFinal(setorComercialFinal.getCodigo());



						// Quadra Final
						Quadra quadraFinal = null;
						if(!Util.isVazioOuBranco(formulario.getNumeroQuadraFinal())){


							filtroQuadra = new FiltroQuadra();
							filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util.obterInteger(formulario
											.getNumeroQuadraFinal())));
							filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, comandoHelper
											.getIdSetorComercialFinal()));

							colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

							if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

								ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente",
												null, "Quadra Final");
								actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

								throw actionServletException;
							}else{

								quadraFinal = (Quadra) gcom.util.Util.retonarObjetoDeColecao(colecaoQuadra);
							}

							comandoHelper.setIdQuadraFinal(quadraFinal.getId());
							comandoHelper.setNumeroQuadraFinal(quadraFinal.getNumeroQuadra());
						}
					}
				}
			}
		}

		// Arquivo de Imóveis
		if(!Util.isVazioOuBranco(formulario.getArquivoCarregado()) && !Util.isVazioOrNulo(formulario.getIdsImoveis())){

			parametroInformado = true;
			comandoHelper.setColecaoIdsImoveis(formulario.getIdsImoveis());

			try{

				StringBuffer arquivoImoveisBuffer = new StringBuffer();

				for(Integer idImovel : formulario.getIdsImoveis()){

					arquivoImoveisBuffer.append(idImovel.toString());
					arquivoImoveisBuffer.append(System.getProperty("line.separator"));
				}

				String nomeArquivoComOuSemExtenssao = formulario.getArquivoCarregado();

				File arquivoImoveis = new File(nomeArquivoComOuSemExtenssao);

				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoImoveis.getAbsolutePath())));

				out.write(arquivoImoveisBuffer.toString());
				out.flush();
				out.close();

				comandoHelper.setArquivoImoveis(Util.getBytesFromFile(arquivoImoveis));

				arquivoImoveis.delete();

			}catch(FileNotFoundException e){

				ActionServletException actionServletException = new ActionServletException("", httpServletRequest.getRequestURI(), "");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

				throw actionServletException;
			}catch(IOException e){

				ActionServletException actionServletException = new ActionServletException("", httpServletRequest.getRequestURI(), "");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

				throw actionServletException;
			}
		}

		// Cliente
		if(!Util.isVazioOuBranco(formulario.getIdCliente())){

			parametroInformado = true;

			comandoHelper.setIdCliente(Util.obterInteger(formulario.getIdCliente()));

			// Tipo da Relação do Cliente
			if(Util.verificarIdNaoVazio(formulario.getIdClienteRelacaoTipo())){

				comandoHelper.setIdClienteRelacaoTipo(Util.obterInteger(formulario.getIdClienteRelacaoTipo()));
			}

			// Período da Relação
			if(!Util.isVazioOuBranco(formulario.getPeriodoRelacionamentoInicial())
							&& !Util.isVazioOuBranco(formulario.getPeriodoRelacionamentoFinal())){

				comandoHelper.setPeriodoRelacionamentoInicial(Util.converterStringParaData(formulario.getPeriodoRelacionamentoInicial()));
				comandoHelper.setPeriodoRelacionamentoFinal(Util.converterStringParaData(formulario.getPeriodoRelacionamentoFinal()));
			}
		}

		// Categoria
		if(!Util.isVazioOrNulo(formulario.getIdCategoria())){

			Collection<Integer> colecaoIdsCategorias = new ArrayList<Integer>();
			for(int i = 0; i < formulario.getIdCategoria().length; i++){

				if(Util.obterInteger(formulario.getIdCategoria()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Util.obterInteger(formulario.getIdCategoria()[i]).intValue() != ConstantesSistema.ZERO.intValue()){

					colecaoIdsCategorias.add(Util.obterInteger(formulario.getIdCategoria()[i]));
				}
			}

			parametroInformado = true;
			comandoHelper.setColecaoIdsCategorias(colecaoIdsCategorias);

			if(!Util.isVazioOrNulo(formulario.getIdSubCategoria())){

				// Subcategoria
				Collection<Integer> colecaoIdsSubcategorias = new ArrayList<Integer>();
				for(int i = 0; i < formulario.getIdSubCategoria().length; i++){

					if(Util.obterInteger(formulario.getIdSubCategoria()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
									&& Util.obterInteger(formulario.getIdSubCategoria()[i]).intValue() != ConstantesSistema.ZERO.intValue()){

						colecaoIdsSubcategorias.add(Util.obterInteger(formulario.getIdSubCategoria()[i]));
					}
				}

				comandoHelper.setColecaoIdsSubcategorias(colecaoIdsSubcategorias);
			}
		}

		// Situação da Ligação de Água
		if(!Util.isVazioOrNulo(formulario.getIdLigacaoAguaSituacao())){

			Collection<Integer> colecaoIdsLigacaoAguaSituacao = new ArrayList<Integer>();
			for(int i = 0; i < formulario.getIdLigacaoAguaSituacao().length; i++){

				if(Util.obterInteger(formulario.getIdLigacaoAguaSituacao()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Util.obterInteger(formulario.getIdLigacaoAguaSituacao()[i]).intValue() != ConstantesSistema.ZERO
												.intValue()){

					colecaoIdsLigacaoAguaSituacao.add(Util.obterInteger(formulario.getIdLigacaoAguaSituacao()[i]));
				}
			}

			if(!Util.isVazioOrNulo(colecaoIdsLigacaoAguaSituacao)){

				parametroInformado = true;
				comandoHelper.setColecaoIdsLigacaoAguaSituacao(colecaoIdsLigacaoAguaSituacao);
			}
		}

		// Situação da Ligação de Esgoto
		if(!Util.isVazioOrNulo(formulario.getIdLigacaoEsgotoSituacao())){

			Collection<Integer> colecaoIdsLigacaoEsgotoSituacao = new ArrayList<Integer>();
			for(int i = 0; i < formulario.getIdLigacaoEsgotoSituacao().length; i++){

				if(Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()[i]).intValue() != ConstantesSistema.ZERO
												.intValue()){

					colecaoIdsLigacaoEsgotoSituacao.add(Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()[i]));
				}
			}

			if(!Util.isVazioOrNulo(colecaoIdsLigacaoEsgotoSituacao)){

				parametroInformado = true;
				comandoHelper.setColecaoIdsLigacaoEsgotoSituacao(colecaoIdsLigacaoEsgotoSituacao);
			}
		}

		// Situação de Cobranca
		if(!Util.isVazioOrNulo(formulario.getIdCobrancaSituacao())){

			Collection<Integer> colecaoIdsCobrancaSituacao = new ArrayList<Integer>();
			for(int i = 0; i < formulario.getIdCobrancaSituacao().length; i++){

				if(Util.obterInteger(formulario.getIdCobrancaSituacao()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Util.obterInteger(formulario.getIdCobrancaSituacao()[i]).intValue() != ConstantesSistema.ZERO.intValue()){

					colecaoIdsCobrancaSituacao.add(Util.obterInteger(formulario.getIdCobrancaSituacao()[i]));
				}
			}

			if(!Util.isVazioOrNulo(colecaoIdsCobrancaSituacao)){

				parametroInformado = true;
				comandoHelper.setColecaoIdsCobrancaSituacao(colecaoIdsCobrancaSituacao);
			}
		}

		// Período da Referência do Débito
		if(!Util.isVazioOuBranco(formulario.getPeriodoReferenciaDebitoInicial())
						&& !Util.isVazioOuBranco(formulario.getPeriodoReferenciaDebitoFinal())){

			parametroInformado = true;
			comandoHelper.setPeriodoReferenciaDebitoInicial(Util.obterInteger(Util.formatarMesAnoParaAnoMes((formulario
							.getPeriodoReferenciaDebitoInicial()))));
			comandoHelper.setPeriodoReferenciaDebitoFinal(Util.obterInteger(Util.formatarMesAnoParaAnoMes((formulario
							.getPeriodoReferenciaDebitoFinal()))));
		}

		// Período de Vencimento do Débito
		if(!Util.isVazioOuBranco(formulario.getPeriodoVencimentoDebitoInicial())
						&& !Util.isVazioOuBranco(formulario.getPeriodoVencimentoDebitoFinal())){

			parametroInformado = true;
			comandoHelper.setPeriodoVencimentoDebitoInicial(Util.converterStringParaData(formulario.getPeriodoVencimentoDebitoInicial()));
			comandoHelper.setPeriodoVencimentoDebitoFinal(Util.converterStringParaData(formulario.getPeriodoVencimentoDebitoFinal()));
		}

		// [FS0013 - Verificar preenchimento dos campos]
		if(!parametroInformado){

			ActionServletException actionServletException = new ActionServletException(
							"atencao.verificar_preenchimento_algum_filtro_selecao");
			actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");

			throw actionServletException;
		}

		// Usuário que incluiu o comando
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		comandoHelper.setIdUsuario(usuario.getId());

		// Indicar se o comando é selecionado pelo usuário ou automático
		comandoHelper.setComandoPrescricaoAutomatico(ConstantesSistema.NAO);

		// [UC3137] Comandar Prescrição de Débito
		Integer codigoProcessoIniciadoGerado = 0;
		try{

			Object[] dadosComandoInserido = fachada.comandarPrescricaoDebitosComandoUsuarioOuAutomatico(comandoHelper,
							this.getUsuarioLogado(httpServletRequest));

			codigoProcessoIniciadoGerado = (Integer) dadosComandoInserido[0];
		}catch(FachadaException e){

			String[] parametros = new String[e.getParametroMensagem().size()];
			e.getParametroMensagem().toArray(parametros);
			ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
			ex.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");
			throw ex;

		}

		if(codigoProcessoIniciadoGerado > 0){

			// montando página de sucesso
			montarPaginaSucesso(httpServletRequest, "O comando de prescrição de débitos " + comandoHelper.getTitulo()
							+ " foi enviado para processamento.", "Voltar", "exibirComandarPrescricaoDebitosAction.do?menu=sim");
		}else{

			ActionServletException actionServletException = new ActionServletException("atencao.erro_iniciar_processo_online",
							String.valueOf(Processo.COMANDAR_PRESCRICAO_DEBITOS_USUARIO) + " - COMANDAR PRESCRIÇÃO DE DÉBITOS ");

			actionServletException.setUrlBotaoVoltar("/gsan/exibirComandarPrescricaoDebitosAction.do");
			throw actionServletException;
		}

		return retorno;
	}
}
