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

package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.FiltrarRelatorioResumoOrdensServicoPendentesHelper;
import gcom.relatorio.atendimentopublico.RelatorioResumoOrdensServicoPendentes;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
 * 
 * @author Anderson Italo
 * @date 06/06/2011
 */
public class GerarRelatorioResumoOrdensServicoPendentesAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		GerarRelatorioResumoOrdensServicoPendentesActionForm formulario = (GerarRelatorioResumoOrdensServicoPendentesActionForm) actionForm;
		boolean parametroInformado = false;
		FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro = new FiltrarRelatorioResumoOrdensServicoPendentesHelper();
		Fachada fachada = Fachada.getInstancia();

		// Localidade Inicial(campo obrigatório)
		if(Util.verificarIdNaoVazio(formulario.getIdLocalidadeInicial())){

			GerenciaRegional gerenciaRegional = null;
			UnidadeNegocio unidadeNegocio = null;

			parametroInformado = true;

			// Verificar existência da Gerência Regional
			if(!Util.isVazioOuBranco(formulario.getIdGerenciaRegional())){
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Util.obterInteger(formulario
								.getIdGerenciaRegional())));

				Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());
				gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerenciaRegional);

				filtro.setIdGerenciaRegional(gerenciaRegional.getId());
			}

			// Verificar existência da Unidade de Negócio
			if(!Util.isVazioOuBranco(formulario.getIdUnidadeNegocio())){
				FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.ID, Util.obterInteger(formulario
								.getIdUnidadeNegocio())));

				Collection colecaoUnidadeNegocio = fachada.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());
				unidadeNegocio = (UnidadeNegocio) Util.retonarObjetoDeColecao(colecaoUnidadeNegocio);

				filtro.setIdUnidadeNegocio(unidadeNegocio.getId());
			}

			if(!Util.isVazioOuBranco(formulario.getIdGerenciaRegional())){
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, Util.obterInteger(formulario
								.getIdGerenciaRegional())));

				Collection colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());
				gerenciaRegional = (GerenciaRegional) Util.retonarObjetoDeColecao(colecaoGerenciaRegional);

				filtro.setIdGerenciaRegional(gerenciaRegional.getId());
			}

			// [FS0002] – Verificar existência da localidade
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
			Collection colecaoLocalidade = null;
			Collection colecaoSetorComercial = null;

			// Localidade Inicial
			if(!Util.isVazioOuBranco(formulario.getIdLocalidadeInicial())){

				Localidade localidadeInicial = null;
				Localidade localidadeFinal = null;

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Util.obterInteger(formulario
								.getIdLocalidadeInicial())));

				if(!Util.isVazioOuBranco(gerenciaRegional)){
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_GERENCIA, gerenciaRegional.getId()));
				}

				colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Inicial");
				}else{
					localidadeInicial = (Localidade) gcom.util.Util.retonarObjetoDeColecao(colecaoLocalidade);
				}

				filtro.setIdLocalidadeInicial(localidadeInicial.getId());

				// Setor Comercial Inicial
				SetorComercial setorComercialInicial = null;
				SetorComercial setorComercialFinal = null;
				if(!Util.isVazioOuBranco(formulario.getCodigoSetorComercialInicial())){

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

					filtro.setCodigoSetorComercialInicial(setorComercialInicial.getCodigo());
				}

				// Localidade Final
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

					filtro.setIdLocalidadeFinal(localidadeFinal.getId());

					// Setor Comercial Final
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
							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Setor Comercial Final");
						}else{
							setorComercialFinal = (SetorComercial) gcom.util.Util.retonarObjetoDeColecao(colecaoSetorComercial);
						}

						// [FS0004] – Verificar Setor Comercial final menor que Setor Comercial
						// Inicial
						if(setorComercialInicial.getCodigo() > setorComercialFinal.getCodigo()){
							throw new ActionServletException("atencao.localidafinal.menor.localidadeinicial", null, "");
						}

						filtro.setCodigoSetorComercialFinal(setorComercialFinal.getCodigo());
					}
				}
			}

			// Tipo de Serviço
			Collection<Integer> colecaoTipoServico = new ArrayList();
			if(!Util.isVazioOrNulo(formulario.getTipoServicoSelecionados())){

				String[] tipoServico = formulario.getTipoServicoSelecionados();
				for(int i = 0; i < tipoServico.length; i++){

					if(!tipoServico[i].equals("")){

						colecaoTipoServico.add(Integer.valueOf(tipoServico[i]));
					}
				}

				filtro.setServicoTipo(colecaoTipoServico);
			}

			// Data de Geração
			if(formulario.getPeriodoGeracaoInicial() != null && !formulario.getPeriodoGeracaoInicial().equals("")){

				filtro.setDataGeracaoInicial(Util.converteStringParaDate(formulario.getPeriodoGeracaoInicial()));
				if(formulario.getPeriodoGeracaoFinal() != null && !formulario.getPeriodoGeracaoFinal().equals("")){

					filtro.setDataGeracaoFinal(Util.converteStringParaDate(formulario.getPeriodoGeracaoFinal()));
				}else{
					filtro.setDataGeracaoFinal(new Date());
				}
			}
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!parametroInformado){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		RelatorioResumoOrdensServicoPendentes relatorio = new RelatorioResumoOrdensServicoPendentes((Usuario) (httpServletRequest
						.getSession(false)).getAttribute("usuarioLogado"));

		relatorio.addParametro("filtroRelatorioHelper", filtro);
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}
}
