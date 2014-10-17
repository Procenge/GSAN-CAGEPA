
package gcom.gui.cobranca.prescricao;

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

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.localidade.*;
import gcom.cobranca.bean.FiltroImoveisComDebitosPrescritosHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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
 * [UC3138] Filtrar Débito para Prescrição
 * 
 * @author Anderson Italo
 * @date 01/04/2014
 */
public class FiltrarImoveisComDebitosPrescritosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){



		ActionForward retorno = actionMapping.findForward("exibirAcompanharImovelComDebitosPrescritos");
		FiltrarImoveisComDebitosPrescritosActionForm formulario = (FiltrarImoveisComDebitosPrescritosActionForm) actionForm;
		boolean parametroInformado = false;
		FiltroImoveisComDebitosPrescritosHelper filtroHelper = new FiltroImoveisComDebitosPrescritosHelper();
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		String parametrosFiltro = "";

		// Comando
		if(!Util.isVazioOuBranco(formulario.getIdPrescricaoComando())){

			parametroInformado = true;
			filtroHelper.setIdPrescricaoComando(Util.obterInteger(formulario.getIdPrescricaoComando()));
			parametrosFiltro += "\nComando: " + formulario.getIdPrescricaoComando().toString();

			if(formulario.getTituloPrescricaoComando() != null){

				parametrosFiltro += " - " + formulario.getTituloPrescricaoComando().toString();
			}
		}

		// Indicador de Simulação
		filtroHelper.setIndicadorSituacaoDebito(Util.obterShort(formulario.getIndicadorSituacaoDebitoPrescrito()));

		// Imovel
		if(!Util.isVazioOuBranco(formulario.getIdImovel())){

			parametroInformado = true;
			filtroHelper.setIdImovel(Util.obterInteger(formulario.getIdImovel()));
			parametrosFiltro += "\nImóvel: " + formulario.getIdImovel().toString();
		}

		GerenciaRegional gerenciaRegional = null;

		// Gerência Regional
		if(!Util.isVazioOuBranco(formulario.getIdGerenciaRegional())){

			parametroInformado = true;
			FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
			filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Util.obterInteger(formulario
							.getIdGerenciaRegional())));

			Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());
			gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerenciaRegional);

			filtroHelper.setIdGerenciaRegional(gerenciaRegional.getId());
			parametrosFiltro += "\nGerência Regional: " + gerenciaRegional.getNome();
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

			filtroHelper.setIdUnidadeNegocio(unidadeNegocio.getId());
			parametrosFiltro += "\nUnidade de Negócio: " + unidadeNegocio.getNome();
		}

		// Localidade Elo
		if(!Util.isVazioOuBranco(formulario.getIdLocalidadeElo())){

			parametroInformado = true;
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO_ID, Util.obterInteger(formulario
							.getIdLocalidadeElo())));

			// Recupera Localidade
			Collection colecaoLocalidadeElo = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidadeElo == null || colecaoLocalidadeElo.isEmpty()){

				ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente", null, "Elo");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

				throw actionServletException;
			}else{

				Localidade elo = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidadeElo);
				filtroHelper.setIdElo(elo.getId());
				parametrosFiltro += "\nElo: " + elo.getDescricaoParaRegistroTransacao();
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
					actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

					throw actionServletException;
				}else{

					localidadeInicial = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
					parametrosFiltro += "\nLocalidade Inicial: " + localidadeInicial.getDescricaoParaRegistroTransacao();
				}

				filtroHelper.setIdLocalidadeInicial(localidadeInicial.getId());
				filtroHelper.setIdLocalidadeFinal(localidadeInicial.getId());

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
						actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

						throw actionServletException;
					}else{
						setorComercialInicial = (SetorComercial) gcom.util.Util.retonarObjetoDeColecao(colecaoSetorComercial);
						parametrosFiltro += "\nSetor Comercial Inicial: " + setorComercialInicial.getCodigo();
					}

					filtroHelper.setIdSetorComercialInicial(setorComercialInicial.getId());
					filtroHelper.setCodigoSetorComercialInicial(setorComercialInicial.getCodigo());
					filtroHelper.setIdSetorComercialFinal(setorComercialInicial.getId());
					filtroHelper.setCodigoSetorComercialFinal(setorComercialInicial.getCodigo());

					// Quadra Inicial
					Quadra quadraInicial = null;
					if(!Util.isVazioOuBranco(formulario.getNumeroQuadraInicial())){

						filtroQuadra = new FiltroQuadra();
						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util.obterInteger(formulario
										.getNumeroQuadraInicial())));
						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, filtroHelper
										.getIdSetorComercialInicial()));

						colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

						if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

							ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente",
											null, "Quadra Inicial");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

							throw actionServletException;
						}else{
							quadraInicial = (Quadra) gcom.util.Util.retonarObjetoDeColecao(colecaoQuadra);
							parametrosFiltro += "\nQuadra Inicial: " + quadraInicial.getNumeroQuadra();
						}

						filtroHelper.setIdQuadraInicial(quadraInicial.getId());
						filtroHelper.setNumeroQuadraInicial(quadraInicial.getNumeroQuadra());
						filtroHelper.setIdQuadraFinal(quadraInicial.getId());
						filtroHelper.setNumeroQuadraFinal(quadraInicial.getNumeroQuadra());
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
						actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

						throw actionServletException;
					}else{
						localidadeFinal = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
						parametrosFiltro += "\nLocalidade Final: " + localidadeFinal.getDescricaoParaRegistroTransacao();
					}

					if(localidadeInicial.getId().intValue() > localidadeFinal.getId().intValue()){

						ActionServletException actionServletException = new ActionServletException(
										"atencao.localidafinal.menor.localidadeinicial", null, "");
						actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

						throw actionServletException;
					}

					filtroHelper.setIdLocalidadeFinal(localidadeFinal.getId());

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
							actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

							throw actionServletException;
						}else{
							setorComercialFinal = (SetorComercial) gcom.util.Util.retonarObjetoDeColecao(colecaoSetorComercial);
							parametrosFiltro += "\nSetor Comercial Final: " + setorComercialFinal.getCodigo();
						}

						if(setorComercialInicial.getCodigo() > setorComercialFinal.getCodigo()){

							ActionServletException actionServletException = new ActionServletException(
											"atencao.setor.comercial.final.maior.setor.comercial.inicial", null, "");
							actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

							throw actionServletException;
						}

						filtroHelper.setIdSetorComercialFinal(setorComercialFinal.getId());
						filtroHelper.setCodigoSetorComercialFinal(setorComercialFinal.getCodigo());



						// Quadra Final
						Quadra quadraFinal = null;
						if(!Util.isVazioOuBranco(formulario.getNumeroQuadraFinal())){


							filtroQuadra = new FiltroQuadra();
							filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util.obterInteger(formulario
											.getNumeroQuadraFinal())));
							filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, filtroHelper
											.getIdSetorComercialFinal()));

							colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

							if(colecaoQuadra == null || colecaoQuadra.isEmpty()){

								ActionServletException actionServletException = new ActionServletException("atencao.pesquisa_inexistente",
												null, "Quadra Final");
								actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

								throw actionServletException;
							}else{

								quadraFinal = (Quadra) gcom.util.Util.retonarObjetoDeColecao(colecaoQuadra);
								parametrosFiltro += "\nQuadra Final: " + quadraFinal.getNumeroQuadra();
							}

							filtroHelper.setIdQuadraFinal(quadraFinal.getId());
							filtroHelper.setNumeroQuadraFinal(quadraFinal.getNumeroQuadra());
						}
					}
				}
			}
		}

		// Arquivo de Imóveis
		if(!Util.isVazioOuBranco(formulario.getArquivoCarregado()) && !Util.isVazioOrNulo(formulario.getIdsImoveis())){

			parametroInformado = true;
			filtroHelper.setColecaoIdsImoveis(formulario.getIdsImoveis());

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

				filtroHelper.setArquivoImoveis(Util.getBytesFromFile(arquivoImoveis));
				parametrosFiltro += "\nArquivos de Imóveis: SIM";

				arquivoImoveis.delete();

			}catch(FileNotFoundException e){

				ActionServletException actionServletException = new ActionServletException("", httpServletRequest.getRequestURI(), "");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

				throw actionServletException;
			}catch(IOException e){

				ActionServletException actionServletException = new ActionServletException("", httpServletRequest.getRequestURI(), "");
				actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

				throw actionServletException;
			}
		}

		// Cliente
		if(!Util.isVazioOuBranco(formulario.getIdCliente())){

			parametroInformado = true;

			filtroHelper.setIdCliente(Util.obterInteger(formulario.getIdCliente()));
			Cliente cliente = (Cliente) fachada.pesquisar(filtroHelper.getIdCliente(), Cliente.class);
			parametrosFiltro += "\nCliente: " + cliente.getDescricaoParaRegistroTransacao();

			// Tipo da Relação do Cliente
			if(Util.verificarIdNaoVazio(formulario.getIdClienteRelacaoTipo())){

				filtroHelper.setIdClienteRelacaoTipo(Util.obterInteger(formulario.getIdClienteRelacaoTipo()));

				ClienteRelacaoTipo clienteRelacaoTipo = (ClienteRelacaoTipo) fachada.pesquisar(
								Util.obterInteger(formulario.getIdClienteRelacaoTipo()), ClienteRelacaoTipo.class);
				parametrosFiltro += "\nTipo da Relação do Cliente: " + clienteRelacaoTipo.getDescricaoParaRegistroTransacao();
			}

			// Período da Relação
			if(!Util.isVazioOuBranco(formulario.getPeriodoRelacionamentoInicial())
							&& !Util.isVazioOuBranco(formulario.getPeriodoRelacionamentoFinal())){

				filtroHelper.setPeriodoRelacionamentoInicial(Util.converterStringParaData(formulario.getPeriodoRelacionamentoInicial()));
				filtroHelper.setPeriodoRelacionamentoFinal(Util.converterStringParaData(formulario.getPeriodoRelacionamentoFinal()));
				parametrosFiltro += "\nPeríodo da Relação do Cliente: " + formulario.getPeriodoRelacionamentoInicial() + " a "
								+ formulario.getPeriodoRelacionamentoFinal();
			}
		}

		// Categoria
		if(!Util.isVazioOrNulo(formulario.getIdCategoria())){

			Collection<Integer> colecaoIdsCategorias = new ArrayList<Integer>();
			String descricaoCategorias = "";
			String descricaoSubcategorias = "";

			for(int i = 0; i < formulario.getIdCategoria().length; i++){

				if(Util.obterInteger(formulario.getIdCategoria()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Util.obterInteger(formulario.getIdCategoria()[i]).intValue() != ConstantesSistema.ZERO.intValue()){

					colecaoIdsCategorias.add(Util.obterInteger(formulario.getIdCategoria()[i]));

					Categoria categoria = (Categoria) fachada.pesquisar(Util.obterInteger(formulario.getIdCategoria()[i]), Categoria.class);
					descricaoCategorias += ", " + categoria.getDescricaoParaRegistroTransacao();
				}
			}


			parametroInformado = true;
			filtroHelper.setColecaoIdsCategorias(colecaoIdsCategorias);

			if(!Util.isVazioOrNulo(formulario.getIdSubCategoria())){

				// Subcategoria
				Collection<Integer> colecaoIdsSubcategorias = new ArrayList<Integer>();
				for(int i = 0; i < formulario.getIdSubCategoria().length; i++){

					if(Util.obterInteger(formulario.getIdSubCategoria()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
									&& Util.obterInteger(formulario.getIdSubCategoria()[i]).intValue() != ConstantesSistema.ZERO.intValue()){

						colecaoIdsSubcategorias.add(Util.obterInteger(formulario.getIdSubCategoria()[i]));

						Subcategoria subcategoria = (Subcategoria) fachada.pesquisar(Util.obterInteger(formulario.getIdSubCategoria()[i]),
										Subcategoria.class);
						descricaoSubcategorias += ", " + subcategoria.getDescricaoParaRegistroTransacao();
					}
				}

				filtroHelper.setColecaoIdsSubcategorias(colecaoIdsSubcategorias);
			}

			if(!Util.isVazioOuBranco(descricaoSubcategorias)){

				parametrosFiltro += "\nCategoria: " + descricaoCategorias.substring(2);
				parametrosFiltro += "\nSubcategoria:" + descricaoSubcategorias.substring(2);
			}else if(!Util.isVazioOuBranco(descricaoCategorias)){

				parametrosFiltro += "\nCategoria: " + descricaoCategorias.substring(2);
			}
		}

		// Situação da Ligação de Água
		if(!Util.isVazioOrNulo(formulario.getIdLigacaoAguaSituacao())){

			Collection<Integer> colecaoIdsLigacaoAguaSituacao = new ArrayList<Integer>();
			String descricaoSituacaoLigacaoAgua = "";

			for(int i = 0; i < formulario.getIdLigacaoAguaSituacao().length; i++){

				if(Util.obterInteger(formulario.getIdLigacaoAguaSituacao()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Util.obterInteger(formulario.getIdLigacaoAguaSituacao()[i]).intValue() != ConstantesSistema.ZERO
												.intValue()){

					colecaoIdsLigacaoAguaSituacao.add(Util.obterInteger(formulario.getIdLigacaoAguaSituacao()[i]));

					LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) fachada.pesquisar(
									Util.obterInteger(formulario.getIdLigacaoAguaSituacao()[i]), LigacaoAguaSituacao.class);
					descricaoSituacaoLigacaoAgua += ", " + ligacaoAguaSituacao.getDescricaoParaRegistroTransacao();
				}
			}

			if(!Util.isVazioOrNulo(colecaoIdsLigacaoAguaSituacao)){

				parametroInformado = true;
				filtroHelper.setColecaoIdsLigacaoAguaSituacao(colecaoIdsLigacaoAguaSituacao);

				if(!Util.isVazioOuBranco(descricaoSituacaoLigacaoAgua)){

					parametrosFiltro += "\nSituação da Ligação de Água: " + descricaoSituacaoLigacaoAgua.substring(2);
				}
			}
		}

		// Situação da Ligação de Esgoto
		if(!Util.isVazioOrNulo(formulario.getIdLigacaoEsgotoSituacao())){

			Collection<Integer> colecaoIdsLigacaoEsgotoSituacao = new ArrayList<Integer>();
			String descricaoSituacaoLigacaoEsgoto = "";

			for(int i = 0; i < formulario.getIdLigacaoEsgotoSituacao().length; i++){

				if(Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()[i]).intValue() != ConstantesSistema.NUMERO_NAO_INFORMADO
								&& Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()[i]).intValue() != ConstantesSistema.ZERO
												.intValue()){

					colecaoIdsLigacaoEsgotoSituacao.add(Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()[i]));

					LigacaoEsgotoSituacao ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) fachada.pesquisar(
									Util.obterInteger(formulario.getIdLigacaoEsgotoSituacao()[i]), LigacaoEsgotoSituacao.class);
					descricaoSituacaoLigacaoEsgoto += ", " + ligacaoEsgotoSituacao.getDescricaoParaRegistroTransacao();
				}
			}

			if(!Util.isVazioOrNulo(colecaoIdsLigacaoEsgotoSituacao)){

				parametroInformado = true;
				filtroHelper.setColecaoIdsLigacaoEsgotoSituacao(colecaoIdsLigacaoEsgotoSituacao);

				if(!Util.isVazioOuBranco(descricaoSituacaoLigacaoEsgoto)){

					parametrosFiltro += "\nSituação da Ligação de Esgoto: " + descricaoSituacaoLigacaoEsgoto.substring(2);
				}
			}
		}

		// Período da Referência do Débito
		if(!Util.isVazioOuBranco(formulario.getPeriodoReferenciaDebitoInicial())
						&& !Util.isVazioOuBranco(formulario.getPeriodoReferenciaDebitoFinal())){

			parametroInformado = true;
			filtroHelper.setPeriodoReferenciaDebitoInicial(Util.obterInteger(Util.formatarMesAnoParaAnoMes((formulario
							.getPeriodoReferenciaDebitoInicial()))));
			filtroHelper.setPeriodoReferenciaDebitoFinal(Util.obterInteger(Util.formatarMesAnoParaAnoMes((formulario
							.getPeriodoReferenciaDebitoFinal()))));

			parametrosFiltro += "\nPeríodo de Referência do Débito: " + formulario.getPeriodoReferenciaDebitoInicial() + " a "
							+ formulario.getPeriodoReferenciaDebitoFinal();
		}

		// Período de Vencimento do Débito
		if(!Util.isVazioOuBranco(formulario.getPeriodoVencimentoDebitoInicial())
						&& !Util.isVazioOuBranco(formulario.getPeriodoVencimentoDebitoFinal())){

			parametroInformado = true;
			filtroHelper.setPeriodoVencimentoDebitoInicial(Util.converterStringParaData(formulario.getPeriodoVencimentoDebitoInicial()));
			filtroHelper.setPeriodoVencimentoDebitoFinal(Util.converterStringParaData(formulario.getPeriodoVencimentoDebitoFinal()));

			parametrosFiltro += "\nPeríodo de Vencimento do Débito: " + formulario.getPeriodoVencimentoDebitoInicial() + " a "
							+ formulario.getPeriodoVencimentoDebitoFinal();
		}

		// [FS0013 - Verificar preenchimento dos campos]
		if(!parametroInformado){

			ActionServletException actionServletException = new ActionServletException(
							"atencao.verificar_preenchimento_algum_filtro_selecao");
			actionServletException.setUrlBotaoVoltar("/gsan/exibirFiltrarImoveisComDebitosPrescritosAction.do");

			throw actionServletException;
		}

		sessao.setAttribute("filtroImoveisComDebitosPrescritosHelper", filtroHelper);
		sessao.setAttribute("parametrosFiltroRelatorioAcompanhamentoDebitosPrescritos", parametrosFiltro);

		return retorno;
	}
}

