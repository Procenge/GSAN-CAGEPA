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

package gcom.gui.cobranca;

import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.CobrancaSituacaoHistorico;
import gcom.cobranca.CobrancaSituacaoMotivo;
import gcom.cobranca.CobrancaSituacaoTipo;
import gcom.cobranca.FiltroCobrancaSituacaoTipo;
import gcom.cobranca.OrgaoExterno;
import gcom.cobranca.bean.SituacaoEspecialCobrancaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ValidarSituacaoEspecialCobrancaInserirAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		SituacaoEspecialCobrancaActionForm situacaoEspecialCobrancaActionForm = (SituacaoEspecialCobrancaActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		// Comparar Ano Mes Referencia
		String mesAnoReferenciaCobrancaInicial = situacaoEspecialCobrancaActionForm.getMesAnoReferenciaCobrancaInicial();
		boolean mesAnoReferenciaInicialValido = Util.validarMesAno(mesAnoReferenciaCobrancaInicial);
		String mesAnoReferenciaCobrancaFinal = situacaoEspecialCobrancaActionForm.getMesAnoReferenciaCobrancaFinal();
		boolean mesAnoReferenciaFinalValido = Util.validarMesAno(mesAnoReferenciaCobrancaFinal);

		Integer anoMesReferenciaInicial = null;
		Integer anoMesReferenciaFinal = null;

		if((mesAnoReferenciaCobrancaInicial != null && mesAnoReferenciaCobrancaFinal != null)
						&& (!mesAnoReferenciaCobrancaInicial.equals("") && !mesAnoReferenciaCobrancaFinal.equals(""))){
			if(!mesAnoReferenciaInicialValido){
				throw new ActionServletException("atencao.adicionar_debito_ano_mes_referencia_invalido", null, "inicial");
			}
			if(!mesAnoReferenciaFinalValido){
				throw new ActionServletException("atencao.adicionar_debito_ano_mes_referencia_invalido", null, "final");
			}

			anoMesReferenciaInicial = Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaCobrancaInicial);
			anoMesReferenciaFinal = Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaCobrancaFinal);
			boolean dataInicialSuperiorMenor = Util.compararAnoMesReferencia(Integer.valueOf(anoMesReferenciaInicial), Integer
							.valueOf(anoMesReferenciaFinal), "<");
			boolean dataInicialSuperiorIgual = Util.compararAnoMesReferencia(Integer.valueOf(anoMesReferenciaInicial), Integer
							.valueOf(anoMesReferenciaFinal), "=");

			if(dataInicialSuperiorMenor || dataInicialSuperiorIgual){

				Integer anoMesInicial = fachada
								.validarMesAnoReferenciaCobranca(transferirActionFormParaHelper(situacaoEspecialCobrancaActionForm));
				if(!Util.isVazioOuBranco(anoMesInicial) && !Util.isVazioOuBranco(anoMesReferenciaInicial)
								&& anoMesInicial > (anoMesReferenciaInicial)){
					throw new ActionServletException("atencao.mes.ano.anterior.mes.ano.corrente.imovel.cobranca");
				}

			}else{
				throw new ActionServletException("atencao.mes.ano.inicial.cobranca.maior.mes.ano.final.cobranca");
			}

		}else{
			throw new ActionServletException("atencao.campo_texto.obrigatorio", null,
							"Mês e Ano de Refer~encia do Cobrançaa Inicial e Final");
		}

		SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper = transferirActionFormParaHelper(situacaoEspecialCobrancaActionForm);

		Collection pesquisarImoveisParaSerInseridos = fachada
						.pesquisarImovelSituacaoEspecialCobranca("SEM", situacaoEspecialCobrancaHelper);

		// Incluir tabela Cobranca situacao historico

		Iterator iterator = pesquisarImoveisParaSerInseridos.iterator();
		Collection collectionCobrancaSituaoHistorico = new ArrayList();
		while(iterator.hasNext()){
			Integer id = (Integer) iterator.next();

			// Construindo as variaveis
			CobrancaSituacaoHistorico cobrancaSituacaoHistorico = new CobrancaSituacaoHistorico();
			Imovel imovel = new Imovel();
			CobrancaSituacaoMotivo CobrancaSituacaoMotivo = new CobrancaSituacaoMotivo();
			CobrancaSituacaoTipo cobrancaSituacaoTipo = new CobrancaSituacaoTipo();

			// Setando as Variaveis
			imovel.setId(Integer.valueOf(id));
			cobrancaSituacaoHistorico.setImovel(imovel);
			CobrancaSituacaoMotivo.setId(Integer.valueOf(situacaoEspecialCobrancaHelper.getIdCobrancaSituacaoMotivo()));
			cobrancaSituacaoTipo.setId(Integer.valueOf(situacaoEspecialCobrancaHelper.getIdCobrancaSituacaoTipo()));
			cobrancaSituacaoHistorico.setAnoMesCobrancaSituacaoInicio(anoMesReferenciaInicial);
			cobrancaSituacaoHistorico.setAnoMesCobrancaSituacaoFim(anoMesReferenciaFinal);

			cobrancaSituacaoHistorico.setAnoMesCobrancaRetirada(null);
			cobrancaSituacaoHistorico.setCobrancaSituacaoMotivo(CobrancaSituacaoMotivo);

			cobrancaSituacaoHistorico.setCobrancaSituacaoTipo(cobrancaSituacaoTipo);
			cobrancaSituacaoHistorico.setDataHoraInclusao(Calendar.getInstance().getTime());
			cobrancaSituacaoHistorico.setUsuarioLogadoInclusao(getUsuarioLogado(httpServletRequest));

			cobrancaSituacaoHistorico.setOrgaoExterno(situacaoEspecialCobrancaHelper.getIdOrgaoExterno());

			cobrancaSituacaoHistorico.setNumeroProcesso(situacaoEspecialCobrancaHelper.getNumeroProcesso());

			// Setando as Variaveis*/

			// insere no banco
			// fachada.inserir(CobrancaSituacaoHistorico);
			collectionCobrancaSituaoHistorico.add(cobrancaSituacaoHistorico);
			// insere no banco
		}
		// Incluir tabela Cobranca situacao historico
		fachada.inserirCobrancaSituacaoHistorico(collectionCobrancaSituaoHistorico);

		// Atualizar Imoveis
		fachada.atualizarCobrancaSituacaoTipo(pesquisarImoveisParaSerInseridos, Integer.valueOf(situacaoEspecialCobrancaActionForm
						.getIdCobrancaSituacaoTipo()));

		FiltroCobrancaSituacaoTipo filtroCobrancaSituacaoTipo = new FiltroCobrancaSituacaoTipo();

		filtroCobrancaSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacaoTipo.ID,
						situacaoEspecialCobrancaActionForm.getIdCobrancaSituacaoTipo()));
		Collection collectionCobrancaSituacaoTipo = fachada.pesquisar(filtroCobrancaSituacaoTipo, CobrancaSituacaoTipo.class.getName());
		String descricaoCobrancaSituacaoTipo = ((CobrancaSituacaoTipo) Util.retonarObjetoDeColecao(collectionCobrancaSituacaoTipo))
						.getDescricao();

		montarPaginaSucesso(httpServletRequest, situacaoEspecialCobrancaActionForm.getQuantidadeImoveisSEMSituacaoEspecialCobranca()
						+ " imóvel(eis) inserido(s) na Situação Especial de Cobrança " + descricaoCobrancaSituacaoTipo + " com sucesso.",
						"Realizar outra Manutenção de Situação Especial de Cobrança", "exibirSituacaoEspecialCobrancaInformarAction.do");

		return retorno;
	}

	private SituacaoEspecialCobrancaHelper transferirActionFormParaHelper(
					SituacaoEspecialCobrancaActionForm situacaoEspecialCobrancaActionForm){

		SituacaoEspecialCobrancaHelper situacaoEspecialCobrancaHelper = new SituacaoEspecialCobrancaHelper();

		situacaoEspecialCobrancaHelper.setIdImovel(situacaoEspecialCobrancaActionForm.getIdImovel() == null ? ""
						: situacaoEspecialCobrancaActionForm.getIdImovel());

		situacaoEspecialCobrancaHelper.setInscricaoTipo(situacaoEspecialCobrancaActionForm.getInscricaoTipo() == null ? ""
						: situacaoEspecialCobrancaActionForm.getInscricaoTipo());

		situacaoEspecialCobrancaHelper.setLoteDestino(situacaoEspecialCobrancaActionForm.getLoteDestino() == null ? ""
						: situacaoEspecialCobrancaActionForm.getLoteDestino());

		situacaoEspecialCobrancaHelper.setQuadraDestinoNM(situacaoEspecialCobrancaActionForm.getQuadraDestinoNM() == null ? ""
						: situacaoEspecialCobrancaActionForm.getQuadraDestinoNM());

		situacaoEspecialCobrancaHelper.setLoteOrigem(situacaoEspecialCobrancaActionForm.getLoteOrigem() == null ? ""
						: situacaoEspecialCobrancaActionForm.getLoteOrigem());

		situacaoEspecialCobrancaHelper.setNomeLocalidadeOrigem(situacaoEspecialCobrancaActionForm.getNomeLocalidadeOrigem() == null ? ""
						: situacaoEspecialCobrancaActionForm.getNomeLocalidadeOrigem());

		situacaoEspecialCobrancaHelper
						.setNomeSetorComercialOrigem(situacaoEspecialCobrancaActionForm.getNomeSetorComercialOrigem() == null ? ""
										: situacaoEspecialCobrancaActionForm.getNomeSetorComercialOrigem());

		situacaoEspecialCobrancaHelper.setQuadraOrigemNM(situacaoEspecialCobrancaActionForm.getQuadraOrigemNM() == null ? ""
						: situacaoEspecialCobrancaActionForm.getQuadraOrigemNM());

		situacaoEspecialCobrancaHelper.setQuadraMensagemOrigem(situacaoEspecialCobrancaActionForm.getQuadraMensagemOrigem() == null ? ""
						: situacaoEspecialCobrancaActionForm.getQuadraMensagemOrigem());

		situacaoEspecialCobrancaHelper.setNomeLocalidadeDestino(situacaoEspecialCobrancaActionForm.getNomeLocalidadeDestino() == null ? ""
						: situacaoEspecialCobrancaActionForm.getNomeLocalidadeDestino());

		situacaoEspecialCobrancaHelper
						.setSetorComercialDestinoCD(situacaoEspecialCobrancaActionForm.getSetorComercialDestinoCD() == null ? ""
										: situacaoEspecialCobrancaActionForm.getSetorComercialDestinoCD());

		situacaoEspecialCobrancaHelper
						.setSetorComercialOrigemCD(situacaoEspecialCobrancaActionForm.getSetorComercialOrigemCD() == null ? ""
										: situacaoEspecialCobrancaActionForm.getSetorComercialOrigemCD());

		situacaoEspecialCobrancaHelper
						.setSetorComercialOrigemID(situacaoEspecialCobrancaActionForm.getSetorComercialOrigemID() == null ? ""
										: situacaoEspecialCobrancaActionForm.getSetorComercialOrigemID());

		situacaoEspecialCobrancaHelper.setQuadraOrigemID(situacaoEspecialCobrancaActionForm.getQuadraOrigemID() == null ? ""
						: situacaoEspecialCobrancaActionForm.getQuadraOrigemID());

		situacaoEspecialCobrancaHelper.setLocalidadeDestinoID(situacaoEspecialCobrancaActionForm.getLocalidadeDestinoID() == null ? ""
						: situacaoEspecialCobrancaActionForm.getLocalidadeDestinoID());

		situacaoEspecialCobrancaHelper.setLocalidadeOrigemID(situacaoEspecialCobrancaActionForm.getLocalidadeOrigemID() == null ? ""
						: situacaoEspecialCobrancaActionForm.getLocalidadeOrigemID());

		situacaoEspecialCobrancaHelper
						.setNomeSetorComercialDestino(situacaoEspecialCobrancaActionForm.getNomeSetorComercialDestino() == null ? ""
										: situacaoEspecialCobrancaActionForm.getNomeSetorComercialDestino());

		situacaoEspecialCobrancaHelper
						.setSetorComercialDestinoID(situacaoEspecialCobrancaActionForm.getSetorComercialDestinoID() == null ? ""
										: situacaoEspecialCobrancaActionForm.getSetorComercialDestinoID());

		situacaoEspecialCobrancaHelper.setQuadraMensagemDestino(situacaoEspecialCobrancaActionForm.getQuadraMensagemDestino() == null ? ""
						: situacaoEspecialCobrancaActionForm.getQuadraMensagemDestino());

		situacaoEspecialCobrancaHelper.setQuadraDestinoID(situacaoEspecialCobrancaActionForm.getQuadraDestinoID() == null ? ""
						: situacaoEspecialCobrancaActionForm.getQuadraDestinoID());

		situacaoEspecialCobrancaHelper
						.setTipoSituacaoEspecialCobranca(situacaoEspecialCobrancaActionForm.getTipoSituacaoEspecialCobranca() == null ? ""
										: situacaoEspecialCobrancaActionForm.getTipoSituacaoEspecialCobranca());

		situacaoEspecialCobrancaHelper.setLoteOrigem(situacaoEspecialCobrancaActionForm.getLoteOrigem() == null ? ""
						: situacaoEspecialCobrancaActionForm.getLoteOrigem());

		situacaoEspecialCobrancaHelper.setLoteDestino(situacaoEspecialCobrancaActionForm.getLoteDestino() == null ? ""
						: situacaoEspecialCobrancaActionForm.getLoteDestino());

		situacaoEspecialCobrancaHelper.setSubloteOrigem(situacaoEspecialCobrancaActionForm.getSubloteOrigem() == null ? ""
						: situacaoEspecialCobrancaActionForm.getSubloteOrigem());

		situacaoEspecialCobrancaHelper.setSubloteDestino(situacaoEspecialCobrancaActionForm.getSubloteDestino() == null ? ""
						: situacaoEspecialCobrancaActionForm.getSubloteDestino());

		situacaoEspecialCobrancaHelper
						.setIdCobrancaSituacaoMotivo(situacaoEspecialCobrancaActionForm.getIdCobrancaSituacaoMotivo() == null ? ""
										: situacaoEspecialCobrancaActionForm.getIdCobrancaSituacaoMotivo());

		situacaoEspecialCobrancaHelper
						.setIdCobrancaSituacaoTipo(situacaoEspecialCobrancaActionForm.getIdCobrancaSituacaoTipo() == null ? ""
										: situacaoEspecialCobrancaActionForm.getIdCobrancaSituacaoTipo());

		situacaoEspecialCobrancaHelper.setMesAnoReferenciaCobrancaInicial(situacaoEspecialCobrancaActionForm
						.getMesAnoReferenciaCobrancaInicial() == null ? "" : situacaoEspecialCobrancaActionForm
						.getMesAnoReferenciaCobrancaInicial());

		situacaoEspecialCobrancaHelper.setMesAnoReferenciaCobrancaFinal(situacaoEspecialCobrancaActionForm
						.getMesAnoReferenciaCobrancaFinal() == null ? "" : situacaoEspecialCobrancaActionForm
						.getMesAnoReferenciaCobrancaFinal());

		situacaoEspecialCobrancaHelper.setQuantidadeImoveisCOMSituacaoEspecialCobranca(situacaoEspecialCobrancaActionForm
						.getQuantidadeImoveisCOMSituacaoEspecialCobranca() == null ? "" : situacaoEspecialCobrancaActionForm
						.getQuantidadeImoveisCOMSituacaoEspecialCobranca());

		situacaoEspecialCobrancaHelper.setQuantidadeImoveisSEMSituacaoEspecialCobranca(situacaoEspecialCobrancaActionForm
						.getQuantidadeImoveisSEMSituacaoEspecialCobranca() == null ? "" : situacaoEspecialCobrancaActionForm
						.getQuantidadeImoveisSEMSituacaoEspecialCobranca());

		situacaoEspecialCobrancaHelper
						.setQuantidadeImoveisAtualizados(situacaoEspecialCobrancaActionForm.getQuantidadeImoveisAtualizados() == null ? ""
										: situacaoEspecialCobrancaActionForm.getQuantidadeImoveisAtualizados());

		situacaoEspecialCobrancaHelper.setCodigoRotaInicial(situacaoEspecialCobrancaActionForm.getCdRotaInicial() == null ? ""
						: situacaoEspecialCobrancaActionForm.getCdRotaInicial());

		situacaoEspecialCobrancaHelper.setCodigoRotaFinal(situacaoEspecialCobrancaActionForm.getCdRotaFinal() == null ? ""
						: situacaoEspecialCobrancaActionForm.getCdRotaFinal());

		situacaoEspecialCobrancaHelper.setSequencialRotaInicial(situacaoEspecialCobrancaActionForm.getSequencialRotaInicial() == null ? ""
						: situacaoEspecialCobrancaActionForm.getSequencialRotaInicial());

		situacaoEspecialCobrancaHelper.setSequencialRotaFinal(situacaoEspecialCobrancaActionForm.getSequencialRotaFinal() == null ? ""
						: situacaoEspecialCobrancaActionForm.getSequencialRotaFinal());

		// situacaoEspecialCobrancaHelper.setIdUsuario(getUsuarioLogado(httpServletRequest).getId().toString());

		situacaoEspecialCobrancaHelper.setNumeroProcesso(situacaoEspecialCobrancaActionForm.getNumeroProcesso() == null
						|| situacaoEspecialCobrancaActionForm.getNumeroProcesso().equals("") ? null : situacaoEspecialCobrancaActionForm
						.getNumeroProcesso());

		situacaoEspecialCobrancaHelper.setIdOrgaoExterno(situacaoEspecialCobrancaActionForm.getOrgao() == null
						|| situacaoEspecialCobrancaActionForm.getOrgao().equals("") ? null : new OrgaoExterno(Integer
						.valueOf(situacaoEspecialCobrancaActionForm.getOrgao())));

		return situacaoEspecialCobrancaHelper;
	}
}
