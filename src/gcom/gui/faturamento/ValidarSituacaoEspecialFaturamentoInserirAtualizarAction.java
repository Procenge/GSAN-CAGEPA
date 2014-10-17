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
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoSituacaoHistorico;
import gcom.faturamento.FaturamentoSituacaoMotivo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ValidarSituacaoEspecialFaturamentoInserirAtualizarAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		SituacaoEspecialFaturamentoActionForm situacaoEspecialFaturamentoActionForm = (SituacaoEspecialFaturamentoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Collection<FaturamentoSituacaoHistorico> collectionFaturmentoSituaoHistorico = new ArrayList<FaturamentoSituacaoHistorico>();
		Collection<SituacaoEspecialFaturamentoHelper> helperParaSeremInseridos = situacaoEspecialFaturamentoActionForm
						.getColecaoSituacaoEspecialFaturamentoHelper();
		Collection<SituacaoEspecialFaturamentoHelper> helperParaSeremRemovidos = situacaoEspecialFaturamentoActionForm
						.getColecaoSituacaoEspecialFaturamentoHelperRemovidas();

		if(helperParaSeremInseridos != null && !helperParaSeremInseridos.isEmpty()){
			Iterator<SituacaoEspecialFaturamentoHelper> iterator = helperParaSeremInseridos.iterator();
			Collection<Integer> imoveisParaSerInseridos = new ArrayList<Integer>();
			while(iterator.hasNext()){
				SituacaoEspecialFaturamentoHelper helper = iterator.next();

				// Inicio - Comparar Ano Mes Referencia
				String mesAnoReferenciaFaturamentoInicial = (String) httpServletRequest
								.getParameter("mesAnoInicial" + helper.getIdImovel());
				boolean mesAnoReferenciaInicialValido = false;
				if(mesAnoReferenciaFaturamentoInicial != null){
					mesAnoReferenciaInicialValido = Util.validarMesAno(mesAnoReferenciaFaturamentoInicial);
				}else{
					throw new ActionServletException("atencao.situacao_especial.ano_mes_referencia_invalido", "inicial",
									helper.getIdImovel());
				}

				String mesAnoReferenciaFaturamentoFinal = (String) httpServletRequest.getParameter("mesAnoFinal" + helper.getIdImovel());
				boolean mesAnoReferenciaFinalValido = false;
				if(mesAnoReferenciaFaturamentoFinal != null){
					mesAnoReferenciaFinalValido = Util.validarMesAno(mesAnoReferenciaFaturamentoFinal);
				}else{
					throw new ActionServletException("atencao.situacao_especial.ano_mes_referencia_invalido", "final", helper.getIdImovel());
				}

				Integer anoMesReferenciaInicial = null;
				Integer anoMesReferenciaFinal = null;

				if(helper.getIndicadorNovo() != null && helper.getIndicadorNovo().equals("1")){
					if((mesAnoReferenciaFaturamentoInicial != null && mesAnoReferenciaFaturamentoFinal != null)
									&& (!mesAnoReferenciaFaturamentoInicial.equals("") && !mesAnoReferenciaFaturamentoFinal.equals(""))){
						if(!mesAnoReferenciaInicialValido){
							throw new ActionServletException("atencao.situacao_especial.ano_mes_referencia_invalido", "inicial", helper
											.getIdImovel());
						}
						if(!mesAnoReferenciaFinalValido){
							throw new ActionServletException("atencao.situacao_especial.ano_mes_referencia_invalido", "final", helper
											.getIdImovel());
						}
						anoMesReferenciaInicial = Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaFaturamentoInicial);
						anoMesReferenciaFinal = Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaFaturamentoFinal);
						boolean dataInicialSuperiorMenor = Util.compararAnoMesReferencia(new Integer(anoMesReferenciaInicial), new Integer(
										anoMesReferenciaFinal), "<");
						boolean dataInicialSuperiorIgual = Util.compararAnoMesReferencia(new Integer(anoMesReferenciaInicial), new Integer(
										anoMesReferenciaFinal), "=");
						if(dataInicialSuperiorMenor || dataInicialSuperiorIgual){
							Integer anoMesInicial = fachada.validarMesAnoReferencia(helper);
							if(anoMesInicial > (anoMesReferenciaInicial)){
								throw new ActionServletException("atencao.mes.ano.anterior.mes.ano.corrente.imovel", null, helper
												.getIdImovel());
							}
						}else{
							throw new ActionServletException("atencao.mes.ano.inicial.maior.mes.ano.final", null, helper.getIdImovel());
						}
					}else{
						throw new ActionServletException("atencao.campo_texto.obrigatorio", null,
										"Mês e Ano de Referência do Faturamento Inicial e Final");
					}
					// Fim - Comparar Ano Mes Referencia
				}

				// Inicio - Construindo as variaveis
				FaturamentoSituacaoHistorico faturamentoSituacaoHistorico = new FaturamentoSituacaoHistorico();
				Imovel imovel = new Imovel();
				FaturamentoSituacaoMotivo faturamentoSituacaoMotivo = new FaturamentoSituacaoMotivo();
				faturamentoSituacaoMotivo.setId(new Integer(helper.getIdFaturamentoSituacaoMotivo()));
				String volume = httpServletRequest.getParameter("volume" + helper.getIdImovel());
				String percentualEsgoto = httpServletRequest.getParameter("percentualEsgoto" + helper.getIdImovel());

				FaturamentoSituacaoTipo faturamentoSituacaoTipo = null;
				if(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo() != null
								&& !situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo().equals("")){
					FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
					filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(filtroFaturamentoSituacaoTipo.ID,
									situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo()));
					filtroFaturamentoSituacaoTipo.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoComLeitura");
					filtroFaturamentoSituacaoTipo.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoSemLeitura");

					Collection<FaturamentoSituacaoTipo> tipos = fachada.pesquisar(filtroFaturamentoSituacaoTipo,
									FaturamentoSituacaoTipo.class.getName());

					if(tipos != null && !tipos.isEmpty()){
						faturamentoSituacaoTipo = (FaturamentoSituacaoTipo) tipos.iterator().next();
					}
				}

				if(faturamentoSituacaoTipo != null){
					// ESGOTO OBRIGATÓRIO
					if(percentualEsgoto == null || percentualEsgoto.equals("")){
						if(faturamentoSituacaoTipo.getIndicadorPercentualEsgoto() != null
										&& faturamentoSituacaoTipo.getIndicadorPercentualEsgoto().equals(ConstantesSistema.SIM)){
							throw new ActionServletException("atencao.esgoto.informe", null, "" + helper.getIdImovel());
						}
					}

					// VOLUME OBRIGATÓRIO
					if(volume == null || volume.equals("")){
						if(faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoComLeitura() != null){
							if(faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoComLeitura().getindicadorVolumeAFaturar() != null
											&& faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoComLeitura()
															.getindicadorVolumeAFaturar().equals(ConstantesSistema.SIM)){
								throw new ActionServletException("atencao.volume.informe", null, "" + helper.getIdImovel());
							}
						}
						if(faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoSemLeitura() != null){
							if(faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoSemLeitura().getindicadorVolumeAFaturar() != null
											&& faturamentoSituacaoTipo.getLeituraAnormalidadeConsumoSemLeitura()
															.getindicadorVolumeAFaturar().equals(ConstantesSistema.SIM)){
								throw new ActionServletException("atencao.volume.informe", null, "" + helper.getIdImovel());
							}
						}
					}
				}

				Integer idImovel = new Integer(helper.getIdImovel());
				imovel.setId(idImovel);
				imoveisParaSerInseridos.add(idImovel);
				// Fim - Construindo as variaveis

				// Inicio - Setando as Variaveis
				faturamentoSituacaoHistorico.setImovel(imovel);
				faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoInicio(new Integer(Util
								.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaFaturamentoInicial)));
				faturamentoSituacaoHistorico.setAnoMesFaturamentoSituacaoFim(new Integer(Util
								.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaFaturamentoFinal)));
				faturamentoSituacaoHistorico.setAnoMesFaturamentoRetirada(null);
				faturamentoSituacaoHistorico.setFaturamentoSituacaoMotivo(faturamentoSituacaoMotivo);
				faturamentoSituacaoHistorico.setFaturamentoSituacaoTipo(faturamentoSituacaoTipo);
				faturamentoSituacaoHistorico.setUltimaAlteracao(Calendar.getInstance().getTime());
				faturamentoSituacaoHistorico.setUsuario(usuarioLogado);

				if(volume != null && !volume.equals("")){
					faturamentoSituacaoHistorico.setVolume(new Integer(volume));
				}
				if(percentualEsgoto != null && !percentualEsgoto.equals("")){

					Double pctEsgotoDouble = Double.parseDouble(percentualEsgoto.replace(',', '.'));
					faturamentoSituacaoHistorico.setPercentualEsgoto(new BigDecimal(pctEsgotoDouble));
				}
				// Fim - Setando as Variaveis

				// Inicio - Adiciona à coleção
				collectionFaturmentoSituaoHistorico.add(faturamentoSituacaoHistorico);
				// Fim - Adiciona à coleção
			}

			// Incluir tabela faturamento situacao historico
//			fachada.inserirFaturamentoSituacaoHistorico(collectionFaturmentoSituaoHistorico);

			// Atualizar Imoveis
			fachada.atualizarFaturamentoSituacaoTipo(imoveisParaSerInseridos,
							new Integer(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo()),
							collectionFaturmentoSituaoHistorico, usuarioLogado, helperParaSeremRemovidos);

		}else{
			if(helperParaSeremRemovidos == null || helperParaSeremRemovidos.isEmpty()){
				throw new ActionServletException("atencao.nao.parametro.informado");
			}
		}

		// if(helperParaSeremRemovidos != null && !helperParaSeremRemovidos.isEmpty()){
		// fachada.retirarSituacaoEspecialFaturamento(helperParaSeremRemovidos);
		// }

		montarPaginaSucesso(httpServletRequest, "" + collectionFaturmentoSituaoHistorico.size()
						+ " imóvel(is) inserido(s) na situação especial de faturamento "
						+ situacaoEspecialFaturamentoActionForm.getTipoSituacaoEspecialFaturamento().toUpperCase() + " com sucesso.",
						"Realizar outra Manutenção de Situação Especial de Faturamento",
						"exibirSituacaoEspecialFaturamentoInserirAtualizarAction.do?menu=sim");
		return retorno;
	}

}
