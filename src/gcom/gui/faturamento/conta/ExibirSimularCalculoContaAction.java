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

package gcom.gui.faturamento.conta;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirSimularCalculoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Define a ação de retorno
		ActionForward retorno = actionMapping.findForward("exibirSimularCalculoConta");

		SimularCalculoContaActionForm simularCalculoContaActionForm = (SimularCalculoContaActionForm) actionForm;

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		/*
		 * Costante que informa o ano limite para o campo anoMesReferencia da conta
		 */
		httpServletRequest.setAttribute("anoLimite", ConstantesSistema.ANO_LIMITE);

		String limparForm = httpServletRequest.getParameter("limparForm");
		String carregarData = httpServletRequest.getParameter("carregarData");

		// Removendo coleções da sessão
		if(limparForm != null && !limparForm.equalsIgnoreCase("")){
			sessao.removeAttribute("colecaoLigacaoAguaSituacao");
			sessao.removeAttribute("colecaoLigacaoEsgotoSituacao");
			sessao.removeAttribute("colecaoConsumoTarifa");
			sessao.removeAttribute("colecaoFaturamentoGrupo");
			sessao.removeAttribute("colecaoCategoria");
		}

		Fachada fachada = Fachada.getInstancia();

		// Verifica se existe a coleção de ligação de água
		if(sessao.getAttribute("colecaoLigacaoAguaSituacao") == null){

			// Criação do filtro para ligação água situação
			FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao(FiltroLigacaoAguaSituacao.DESCRICAO);

			filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Pesquisa ligação água situação
			Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

			sessao.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);

		}

		if(sessao.getAttribute("colecaoLigacaoEsgotoSituacao") == null){

			// Criação do filtro para ligação esgoto situação
			FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao(FiltroLigacaoEsgotoSituacao.DESCRICAO);

			filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Pesquisa ligação esgoto situação
			Collection colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao, LigacaoEsgotoSituacao.class.getName());

			sessao.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);

		}

		// Verifica se a situação da ligação de esgoto é faturável, caso não seja limpa e desabilita
		// campos relativos aos dados da ligação de esgoto.
		if(simularCalculoContaActionForm.getIndicadorEsgotoFaturavel() == null
						|| simularCalculoContaActionForm.getIndicadorEsgotoFaturavel().equals(ConstantesSistema.NAO.toString())){

			simularCalculoContaActionForm.setLigacaoEsgotoPerfilId(null);
			simularCalculoContaActionForm.setConsumoFaturadoEsgoto(null);
			simularCalculoContaActionForm.setPercentualEsgoto(null);
		}

		// Verifica se existe a coleção de ligação de esgoto perfil
		if(sessao.getAttribute("colecaoLigacaoEsgotoPerfil") == null){

			FiltroLigacaoEsgotoPerfil filtroLigacaoEsgotoPerfil = new FiltroLigacaoEsgotoPerfil();
			filtroLigacaoEsgotoPerfil.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoPerfil.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoLigacaoEsgotoPerfil = fachada.pesquisar(filtroLigacaoEsgotoPerfil, LigacaoEsgotoPerfil.class.getName());

			// [FS0002 - Verificar existência de dados]
			if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoPerfil)){

				sessao.setAttribute("colecaoLigacaoEsgotoPerfil", colecaoLigacaoEsgotoPerfil);
			}else{

				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Ligação Esgoto Perfil");
			}
		}

		if(sessao.getAttribute("colecaoConsumoTarifa") == null){

			// Criação do filtro para consumo tarifa
			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa(FiltroConsumoTarifa.DESCRICAO);
			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Pesquisa consumo tarifa
			Collection colecaoConsumoTarifa = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			sessao.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
		}

		if(sessao.getAttribute("colecaoFaturamentoGrupo") == null){
			// Criação do filtro para faturamento grupo
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo(FiltroFaturamentoGrupo.DESCRICAO);
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());

			sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
		}

		Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
		if(sessao.getAttribute("colecaoCategoria") != null){
			Collection colecao = (Collection) sessao.getAttribute("colecaoCategoria");
			Iterator iteratorColecaoCategoria = colecao.iterator();

			Categoria categoria = null;
			String quantidadeEconomia = null;
			Integer qtdEconnomia = 0;
			while(iteratorColecaoCategoria.hasNext()){
				categoria = (Categoria) iteratorColecaoCategoria.next();
				// valor minimo
				if(requestMap.get("categoria" + categoria.getId()) != null){

					quantidadeEconomia = (requestMap.get("categoria" + categoria.getId()))[0];

					if(quantidadeEconomia == null || quantidadeEconomia.equalsIgnoreCase("")){
						throw new ActionServletException("atencao.required", null, "Quantidade de Economias");
					}

					categoria.setQuantidadeEconomiasCategoria(new Integer(quantidadeEconomia));
					qtdEconnomia = Util.somaInteiros(qtdEconnomia, new Integer(quantidadeEconomia));
				}
			}
			if(!sessao.getAttribute("retorno").equals("sim")){
				sessao.setAttribute("totalEconomia", qtdEconnomia);
			}
		}

		// DEFINIÇÃO QUE IRÁ AUXILIAR O RETORNO DOS POPUPS
		sessao.setAttribute("UseCase", "SIMULARCALCULOCONTA");

		if(sessao.getAttribute("retorno") == null){
			sessao.setAttribute("existeColecao", "nao");
			sessao.removeAttribute("colecao");
		}else if(sessao.getAttribute("colecao") == null){
			sessao.removeAttribute("existeColecao");
		}else if(sessao.getAttribute("colecao") != null && sessao.getAttribute("adicionar") == null){
			sessao.setAttribute("existeColecao", "nao");
		}else if(sessao.getAttribute("adicionar") != null){
			sessao.removeAttribute("existeColecao");
		}

		/*
		 * Colocado por Raphael Rossiter em 14/03/2007
		 * Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a
		 * empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());

		/*
		 * Colocado por Ailton Sousa em 25/07/2011
		 * Objetivo: Adicionar, na tela, os campos Data de Leitura Anterior e Atual e carregar os
		 * valores dessas datas.
		 */
		// Se o grupo for escolhido e o Mes/Ano de Referência for preenchido, entra nesse fluxo.
		if(!Util.isVazioOuBranco(carregarData) && carregarData.equals("OK")){

			if(!Util.isVazioOuBranco(simularCalculoContaActionForm.getMesAnoReferencia())
							&& (!Util.isInteger(simularCalculoContaActionForm.getMesAnoReferencia().replace("/", "")) || !Util
											.validarMesAno(simularCalculoContaActionForm.getMesAnoReferencia()))){

				throw new ActionServletException("atencao.campo.invalido", "Mês e Ano da Conta");
			}

			// Pega as datas de leitura anterior e atual pela tabela
			// FATURAMENTO_ATIVIDADE_CRONOGRAMA.
			if(!Util.isVazioOuBranco(simularCalculoContaActionForm.getMesAnoReferencia())){

				if(Util.verificarIdNaoVazio(simularCalculoContaActionForm.getFaturamentoGrupoID())){

					simularCalculoContaActionForm.setDataLeituraAnteriorMedicaoHistoricoAgua(Util.formatarData(buscarDataLeituraCronograma(
									Util.converterStringParaInteger(simularCalculoContaActionForm.getFaturamentoGrupoID()), true, Util
													.formatarMesAnoComBarraParaAnoMes(simularCalculoContaActionForm.getMesAnoReferencia()),
									fachada)));
					simularCalculoContaActionForm.setDataLeituraAtualMedicaoHistoricoAgua(Util.formatarData(buscarDataLeituraCronograma(
									Util.converterStringParaInteger(simularCalculoContaActionForm.getFaturamentoGrupoID()), false, Util
													.formatarMesAnoComBarraParaAnoMes(simularCalculoContaActionForm.getMesAnoReferencia()),
									fachada)));
				}
			}

			if(Util.isVazioOuBranco(simularCalculoContaActionForm.getDataLeituraAnteriorMedicaoHistoricoAgua())
							&& !Util.isVazioOuBranco(simularCalculoContaActionForm.getMesAnoReferencia())){

				simularCalculoContaActionForm.setDataLeituraAnteriorMedicaoHistoricoAgua("01/"
								+ simularCalculoContaActionForm.getMesAnoReferencia());
			}

			if(Util.isVazioOuBranco(simularCalculoContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua())
							&& !Util.isVazioOuBranco(simularCalculoContaActionForm.getMesAnoReferencia())){

				int mes = Util.converterStringParaInteger(simularCalculoContaActionForm.getMesAnoReferencia().substring(0, 2)).intValue();
				int ano = Util.converterStringParaInteger(simularCalculoContaActionForm.getMesAnoReferencia().substring(3, 7)).intValue();
				String ultimoDiaMes = Util.obterUltimoDiaMes(mes, ano);

				simularCalculoContaActionForm.setDataLeituraAtualMedicaoHistoricoAgua(ultimoDiaMes + "/"
								+ simularCalculoContaActionForm.getMesAnoReferencia());
			}
		}

		return retorno;

	}

	/**
	 * Pesquisa a data de leitura (Campo FTAC_TMREALIZACAO da tabela
	 * FATURAMENTO_ATIVIDADE_CRONOGRAMA)
	 * 
	 * @author Ailton Sousa
	 * @date 22/07/2011
	 * @param imovel
	 * @param situacao
	 * @param anoMesReferencia
	 * @param fachada
	 */
	public Date buscarDataLeituraCronograma(Integer grupoFaturamentoID, boolean situacao, Integer anoMesReferencia, Fachada fachada){

		return fachada.buscarDataLeituraCronograma(grupoFaturamentoID, situacao, anoMesReferencia);
	}
}
