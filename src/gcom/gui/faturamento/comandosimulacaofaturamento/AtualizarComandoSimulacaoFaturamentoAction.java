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

package gcom.gui.faturamento.comandosimulacaofaturamento;

import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.bean.InserirComandoSimulacaoFaturamentoHelper;
import gcom.faturamento.faturamentosimulacaocomando.FaturamentoSimulacaoComando;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarComandoSimulacaoFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarComandoSimulacaoFaturamentoActionForm formulario = (AtualizarComandoSimulacaoFaturamentoActionForm) actionForm;
		boolean parametroInformado = false;
		InserirComandoSimulacaoFaturamentoHelper comandoHelper = new InserirComandoSimulacaoFaturamentoHelper();
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		// Verificar existência de Grupo de Faturamento
		if(!Util.isVazioOuBranco(formulario.getIdFaturamentoGrupo())){

			parametroInformado = true;
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, Util.obterInteger(formulario
							.getIdFaturamentoGrupo())));

			Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
			FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo);

			comandoHelper.setIdFaturamentoGrupo(faturamentoGrupo.getId());
		}else{

			GerenciaRegional gerenciaRegional = null;
			UnidadeNegocio unidadeNegocio = null;

			// Verificar existência da Gerência Regional
			if(!Util.isVazioOuBranco(formulario.getIdGerenciaRegional())){

				parametroInformado = true;
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Util.obterInteger(formulario
								.getIdGerenciaRegional())));

				Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());
				gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerenciaRegional);

				comandoHelper.setIdGerenciaRegional(gerenciaRegional.getId());
			}

			// Verificar existência da Unidade de Negócio
			if(!Util.isVazioOuBranco(formulario.getIdUnidadeNegocio())){

				parametroInformado = true;
				FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, Util.obterInteger(formulario
								.getIdUnidadeNegocio())));

				Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());
				unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoUnidadeNegocio);

				comandoHelper.setIdUnidadeNegocio(unidadeNegocio.getId());
			}

			// Localidade Inicial
			if(Util.verificarIdNaoVazio(formulario.getIdLocalidadeInicial())){

				// [FS0002] – Verificar existência da localidade
				FiltroLocalidade filtroLocalidade = null;
				FiltroSetorComercial filtroSetorComercial = null;
				FiltroQuadra filtroQuadra = null;
				FiltroRota filtroRota = null;
				Collection colecaoLocalidade = null;
				Collection colecaoSetorComercial = null;
				Collection colecaoQuadra = null;
				Collection colecaoRota = null;

				// Localidade Inicial
				if(!Util.isVazioOuBranco(formulario.getIdLocalidadeInicial())){

					parametroInformado = true;
					Localidade localidadeInicial = null;

					filtroLocalidade = new FiltroLocalidade();
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Util.obterInteger(formulario
									.getIdLocalidadeInicial())));

					colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Inicial");
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
							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial Inicial");
						}else{
							setorComercialInicial = (SetorComercial) gcom.util.Util.retonarObjetoDeColecao(colecaoSetorComercial);
						}

						comandoHelper.setIdSetorComercialInicial(setorComercialInicial.getId());
						comandoHelper.setCodigoSetorComercialInicial(setorComercialInicial.getCodigo());
						comandoHelper.setIdSetorComercialFinal(setorComercialInicial.getId());
						comandoHelper.setCodigoSetorComercialFinal(setorComercialInicial.getCodigo());

						boolean informouQuadraOuRota = false;

						// Quadra Inicial
						Quadra quadraInicial = null;
						if(!Util.isVazioOuBranco(formulario.getNumeroQuadraInicial())){

							informouQuadraOuRota = true;
							filtroQuadra = new FiltroQuadra();
							filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util.obterInteger(formulario
											.getNumeroQuadraInicial())));
							filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, comandoHelper
											.getIdSetorComercialInicial()));

							colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

							if(colecaoQuadra == null || colecaoQuadra.isEmpty()){
								throw new ActionServletException("atencao.pesquisa_inexistente", null, "Quadra Inicial");
							}else{
								quadraInicial = (Quadra) gcom.util.Util.retonarObjetoDeColecao(colecaoQuadra);
							}

							comandoHelper.setIdQuadraInicial(quadraInicial.getId());
							comandoHelper.setNumeroQuadraInicial(quadraInicial.getNumeroQuadra());
							comandoHelper.setIdQuadraFinal(quadraInicial.getId());
							comandoHelper.setNumeroQuadraFinal(quadraInicial.getNumeroQuadra());
						}

						// Rota Inicial
						Rota rotaInicial = null;
						if(!Util.isVazioOuBranco(formulario.getCodigoRotaInicial())){

							informouQuadraOuRota = true;
							filtroRota = new FiltroRota();
							filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, Util.obterInteger(formulario
											.getCodigoRotaInicial())));
							filtroRota.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, comandoHelper
											.getIdSetorComercialInicial()));

							colecaoRota = fachada.pesquisar(filtroRota, Rota.class.getName());

							if(colecaoRota == null || colecaoRota.isEmpty()){
								throw new ActionServletException("atencao.pesquisa_inexistente", null, "Rota Inicial");
							}else{
								rotaInicial = (Rota) gcom.util.Util.retonarObjetoDeColecao(colecaoRota);
							}

							comandoHelper.setIdRotaInicial(rotaInicial.getId());
							comandoHelper.setIdRotaFinal(rotaInicial.getId());
						}

						if(informouQuadraOuRota && !Util.isVazioOuBranco(formulario.getLoteInicial())){

							comandoHelper.setLoteInicial(Util.obterShort(formulario.getLoteInicial()));
							comandoHelper.setLoteFinal(Util.obterShort(formulario.getLoteInicial()));
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
							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Final");
						}else{
							localidadeFinal = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
						}

						// [FS0003] – Verificar Localidade final menor que Localidade Inicial
						if(localidadeInicial.getId().intValue() > localidadeFinal.getId().intValue()){
							throw new ActionServletException("atencao.localidafinal.menor.localidadeinicial", null, "");
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
							filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE,
											localidadeFinal.getId()));

							colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

							if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){
								throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial Final");
							}else{
								setorComercialFinal = (SetorComercial) gcom.util.Util.retonarObjetoDeColecao(colecaoSetorComercial);
							}

							// [FS0004] – Verificar Setor Comercial final menor que Setor Comercial
							// Inicial
							if(setorComercialInicial.getCodigo() > setorComercialFinal.getCodigo()){
								throw new ActionServletException("atencao.localidafinal.menor.localidadeinicial", null, "");
							}

							comandoHelper.setIdSetorComercialFinal(setorComercialFinal.getId());
							comandoHelper.setCodigoSetorComercialFinal(setorComercialFinal.getCodigo());

							boolean informouQuadraOuRota = false;

							// Quadra Final
							Quadra quadraFinal = null;
							if(!Util.isVazioOuBranco(formulario.getNumeroQuadraFinal())){

								informouQuadraOuRota = true;
								filtroQuadra = new FiltroQuadra();
								filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, Util
												.obterInteger(formulario.getNumeroQuadraFinal())));
								filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, comandoHelper
												.getIdSetorComercialFinal()));

								colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

								if(colecaoQuadra == null || colecaoQuadra.isEmpty()){
									throw new ActionServletException("atencao.pesquisa_inexistente", null, "Quadra Final");
								}else{
									quadraFinal = (Quadra) gcom.util.Util.retonarObjetoDeColecao(colecaoQuadra);
								}

								comandoHelper.setIdQuadraFinal(quadraFinal.getId());
								comandoHelper.setNumeroQuadraFinal(quadraFinal.getNumeroQuadra());
							}

							// Rota Final
							Rota rotaFinal = null;
							if(!Util.isVazioOuBranco(formulario.getCodigoRotaFinal())){

								informouQuadraOuRota = true;
								filtroRota = new FiltroRota();
								filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, Util.obterInteger(formulario
												.getCodigoRotaFinal())));
								filtroRota.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, comandoHelper
												.getIdSetorComercialFinal()));

								colecaoRota = fachada.pesquisar(filtroRota, Rota.class.getName());

								if(colecaoRota == null || colecaoRota.isEmpty()){
									throw new ActionServletException("atencao.pesquisa_inexistente", null, "Rota Final");
								}else{
									rotaFinal = (Rota) gcom.util.Util.retonarObjetoDeColecao(colecaoRota);
								}

								comandoHelper.setIdRotaFinal(rotaFinal.getId());
							}

							if(informouQuadraOuRota && !Util.isVazioOuBranco(formulario.getLoteFinal())){

								comandoHelper.setLoteFinal(Util.obterShort(formulario.getLoteFinal()));
							}
						}
					}
				}
			}
		}

		// [FS0005] - Verificar preenchimento de algum filtro de seleção
		if(!parametroInformado){

			throw new ActionServletException("atencao.verificar_preenchimento_algum_filtro_selecao");
		}

		// Título do Comando
		if(!Util.isVazioOuBranco(formulario.getTituloComando())){

			comandoHelper.setTituloComando(formulario.getTituloComando());
		}

		// Indicadores Critérios de Tipo de Consumo Água e Esgoto
		comandoHelper.setCodigoTipoConsumoAgua(Util.obterShort(formulario.getIndicadorCriterioTipoConsumoAgua()));
		comandoHelper.setCodigoTipoConsumoEsgoto(Util.obterShort(formulario.getIndicadorCriterioTipoConsumoEsgoto()));

		// Tarifa de Consumo
		if(Util.verificarIdNaoVazio(formulario.getIdConsumoTarifa())){

			comandoHelper.setIdConsumoTarifa(Util.obterInteger(formulario.getIdConsumoTarifa()));
		}

		FaturamentoSimulacaoComando faturamentoSimulacaoComando = (FaturamentoSimulacaoComando) sessao
						.getAttribute("faturamentoSimulacaoComandoAtualizar");

		fachada.atualizarComandoSimulacaoFaturamento(comandoHelper, faturamentoSimulacaoComando);

		montarPaginaSucesso(httpServletRequest, "Comando de Simulação de Faturamento atualizado com sucesso.",
						"Realizar outra Manutenção de Comando de Simulação de Faturamento",
						"exibirFiltrarComandoSimulacaoFaturamentoAction.do?menu=sim");
		return retorno;
	}
}
