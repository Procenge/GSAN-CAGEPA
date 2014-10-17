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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.gui.relatorio.atendimentopublico;

import gcom.cadastro.imovel.AreaConstruidaFaixa;
import gcom.cadastro.imovel.FiltroAreaConstruidaFaixa;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.atendimentopublico.ordemservico.ImovelEmissaoOrdensSeletivasActionForm;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioEmitirOrdemServicoSeletiva;
import gcom.relatorio.atendimentopublico.RelatorioEmitirOrdemServicoSeletivaSugestao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.ordemservico.ParametroOrdemServico;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarEmissaoOrdensSeletivasAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * @author Ivan Sérgio
	 * @created 06/11/2007
	 *          <<Descrição do método>>
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		// Recupera a Sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;

		if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial() != null
						&& !imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial().equals("")
						&& imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal() != null
						&& !imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal().equals("")){

			String areaInicial = imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial();
			String areaFinal = imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal();

			areaInicial = areaInicial.replace(".", "");
			areaInicial = areaInicial.replace(",", ".");
			areaFinal = areaFinal.replace(".", "");
			areaFinal = areaFinal.replace(",", ".");

			imovelEmissaoOrdensSeletivas.setIntervaloAreaConstruidaInicial(areaInicial);
			imovelEmissaoOrdensSeletivas.setIntervaloAreaConstruidaFinal(areaFinal);
		}

		// Valor do Débito
		String valorTotalDebitoVencido = imovelEmissaoOrdensSeletivas.getValorTotalDebitoVencido();

		if(!Util.isVazioOuBranco(valorTotalDebitoVencido)){
			valorTotalDebitoVencido = valorTotalDebitoVencido.replace(".", "");
			valorTotalDebitoVencido = valorTotalDebitoVencido.replace(",", ".");
		}

		if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida() != null){

			if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida().trim().equalsIgnoreCase(
							String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				imovelEmissaoOrdensSeletivas.setIntervaloAreaConstruidaPredefinida(null);
			}

			if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida() != null){
				// Verifica se o usuario informou o Intervalo de Area Construida Pre definida
				FiltroAreaConstruidaFaixa filtroAreaConstruidaFaixa = new FiltroAreaConstruidaFaixa();

				filtroAreaConstruidaFaixa.adicionarParametro(new ParametroSimples(FiltroAreaConstruidaFaixa.CODIGO,
								imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida()));

				Collection<AreaConstruidaFaixa> colecaoAreaConstruida = fachada.pesquisar(filtroAreaConstruidaFaixa,
								AreaConstruidaFaixa.class.getName());

				if(colecaoAreaConstruida != null && !colecaoAreaConstruida.isEmpty()){
					Iterator<AreaConstruidaFaixa> iColecaoAreaConstruida = colecaoAreaConstruida.iterator();
					AreaConstruidaFaixa faixa = iColecaoAreaConstruida.next();

					// Seta os Intervalos
					imovelEmissaoOrdensSeletivas.setIntervaloAreaConstruidaInicial(faixa.getVolumeMenorFaixa().toString());
					imovelEmissaoOrdensSeletivas.setIntervaloAreaConstruidaFinal(faixa.getVolumeMaiorFaixa().toString());
				}
			}
		}

		if(imovelEmissaoOrdensSeletivas.getImovelCondominio() == null){
			imovelEmissaoOrdensSeletivas.setImovelCondominio("2");
		}

		if(imovelEmissaoOrdensSeletivas.getTipoMedicao() != null
						&& imovelEmissaoOrdensSeletivas.getTipoMedicao().trim().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			imovelEmissaoOrdensSeletivas.setTipoMedicao(null);
		}else{
			if(imovelEmissaoOrdensSeletivas.getTipoMedicao() == null){
				imovelEmissaoOrdensSeletivas.setTipoMedicao(String.valueOf(MedicaoTipo.LIGACAO_AGUA));
			}
		}

		// Grupo de Faturamento
		if(imovelEmissaoOrdensSeletivas.getFaturamentoGrupo() != null
						&& imovelEmissaoOrdensSeletivas.getFaturamentoGrupo().trim().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			imovelEmissaoOrdensSeletivas.setFaturamentoGrupo(null);
		}

		// Regional
		if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
						&& imovelEmissaoOrdensSeletivas.getGerenciaRegional().trim().equalsIgnoreCase(
										String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			imovelEmissaoOrdensSeletivas.setGerenciaRegional(null);
		}

		// Cortado no Período - Inicial
		String intervaloDataCorteInicial = imovelEmissaoOrdensSeletivas.getIntervaloDataCorteInicial();

		if(!Util.isVazioOuBranco(intervaloDataCorteInicial)){
			intervaloDataCorteInicial = Util.formatarMesAnoParaAnoMes(intervaloDataCorteInicial);
		}

		// Cortado no Período - Final
		String intervaloDataCorteFinal = imovelEmissaoOrdensSeletivas.getIntervaloDataCorteFinal();

		if(!Util.isVazioOuBranco(intervaloDataCorteFinal)){
			intervaloDataCorteFinal = Util.formatarMesAnoParaAnoMes(intervaloDataCorteFinal);
		}

		// Cortado no Período - Inicial
		String dataCorteInicial = imovelEmissaoOrdensSeletivas.getDataCorteInicial();
		// Cortado no Período - Final
		String dataCorteFinal = imovelEmissaoOrdensSeletivas.getDataCorteFinal();

		// Verifica se o usuario selecionou simulação
		if(!imovelEmissaoOrdensSeletivas.getSimulacao().equals("1")){
			RelatorioEmitirOrdemServicoSeletiva relatorioEmitirOrdemServicoSeletiva = new RelatorioEmitirOrdemServicoSeletiva(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorioEmitirOrdemServicoSeletiva.addParametro("servicoTipo", imovelEmissaoOrdensSeletivas.getServicoTipo());

			relatorioEmitirOrdemServicoSeletiva
							.addParametro("servicoTipoDescricao", imovelEmissaoOrdensSeletivas.getServicoTipoDescricao());

			relatorioEmitirOrdemServicoSeletiva.addParametro("simulacao", imovelEmissaoOrdensSeletivas.getSimulacao());

			relatorioEmitirOrdemServicoSeletiva.addParametro("firma", imovelEmissaoOrdensSeletivas.getFirma());

			relatorioEmitirOrdemServicoSeletiva.addParametro("nomeFirma", imovelEmissaoOrdensSeletivas.getNomeFirma());

			relatorioEmitirOrdemServicoSeletiva.addParametro("quantidadeMaxima", imovelEmissaoOrdensSeletivas.getQuantidadeMaxima());

			// Valida o Elo e recupera a Descricao
			if(imovelEmissaoOrdensSeletivas.getElo() == null || imovelEmissaoOrdensSeletivas.getElo().equals("")){
				relatorioEmitirOrdemServicoSeletiva.addParametro("elo", "");
				relatorioEmitirOrdemServicoSeletiva.addParametro("nomeElo", "");
			}else{
				Localidade localidadeElo = pesquisaElo(Integer.decode(imovelEmissaoOrdensSeletivas.getElo()));

				if(localidadeElo == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Elo");
				}
				relatorioEmitirOrdemServicoSeletiva.addParametro("elo", localidadeElo.getLocalidade().getId().toString());
				relatorioEmitirOrdemServicoSeletiva.addParametro("nomeElo", localidadeElo.getLocalidade().getDescricao());

			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("faturamentoGrupo", imovelEmissaoOrdensSeletivas.getFaturamentoGrupo());

			relatorioEmitirOrdemServicoSeletiva.addParametro("gerenciaRegional", imovelEmissaoOrdensSeletivas.getGerenciaRegional());

			relatorioEmitirOrdemServicoSeletiva.addParametro("localidade", imovelEmissaoOrdensSeletivas.getLocalidade());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getBairro())
							&& !imovelEmissaoOrdensSeletivas.getBairro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("bairro", imovelEmissaoOrdensSeletivas.getBairro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLogradouro())
							&& !imovelEmissaoOrdensSeletivas.getLogradouro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("logradouro", imovelEmissaoOrdensSeletivas.getLogradouro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getSetor())
							&& !imovelEmissaoOrdensSeletivas.getSetor()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("setor", imovelEmissaoOrdensSeletivas.getSetor());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getQuadra())
							&& !imovelEmissaoOrdensSeletivas.getQuadra()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("quadra", imovelEmissaoOrdensSeletivas.getQuadra());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getRota())
							&& !imovelEmissaoOrdensSeletivas.getRota()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("rota", imovelEmissaoOrdensSeletivas.getRota());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLote())
							&& !imovelEmissaoOrdensSeletivas.getLote()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("lote", imovelEmissaoOrdensSeletivas.getLote());
			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("inscricaoTipo", imovelEmissaoOrdensSeletivas.getInscricaoTipo());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getPerfilImovel())
							&& !imovelEmissaoOrdensSeletivas.getPerfilImovel()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("perfilImovel", imovelEmissaoOrdensSeletivas.getPerfilImovel());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getCategoria())
							&& !imovelEmissaoOrdensSeletivas.getCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("categoria", imovelEmissaoOrdensSeletivas.getCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getSubCategoria())
							&& !imovelEmissaoOrdensSeletivas.getSubCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("subCategoria", imovelEmissaoOrdensSeletivas.getSubCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao())
							&& !imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("ligacaoAguaSituacao",
								imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao())
							&& !imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("ligacaoEsgotoSituacao",
								imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao());
			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloDataCorteInicial", intervaloDataCorteInicial);

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloDataCorteFinal", intervaloDataCorteFinal);

			relatorioEmitirOrdemServicoSeletiva.addParametro("dataCorteInicial", dataCorteInicial);

			relatorioEmitirOrdemServicoSeletiva.addParametro("dataCorteFinal", dataCorteFinal);

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeEconomiasInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeEconomiasInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeEconomiasFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeEconomiasFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroPontosUtilizacaoInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroPontosUtilizacaoInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroPontosUtilizacaoFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroPontosUtilizacaoFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeDocumentosInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeDocumentosInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeDocumentosFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeDocumentosFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroMoradoresInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroMoradoresInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroMoradoresFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroMoradoresFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloAreaConstruidaInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloAreaConstruidaInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloAreaConstruidaFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloAreaConstruidaFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloAreaConstruidaPredefinida", imovelEmissaoOrdensSeletivas
							.getIntervaloAreaConstruidaPredefinida());

			relatorioEmitirOrdemServicoSeletiva.addParametro("imovelCondominio", imovelEmissaoOrdensSeletivas.getImovelCondominio());

			relatorioEmitirOrdemServicoSeletiva.addParametro("consumoPorEconomia", imovelEmissaoOrdensSeletivas.getConsumoPorEconomia());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroConsumoMesInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroConsumoMesInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloNumeroConsumoMesFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroConsumoMesFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("colecaoConsumoMedioImovel", imovelEmissaoOrdensSeletivas
							.getColecaoConsumoMedioImovel());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeContasVencidasInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeContasVencidasInicial());

			relatorioEmitirOrdemServicoSeletiva.addParametro("intervaloQuantidadeContasVencidasFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeContasVencidasFinal());

			relatorioEmitirOrdemServicoSeletiva.addParametro("valorTotalDebitoVencido", valorTotalDebitoVencido);

			relatorioEmitirOrdemServicoSeletiva.addParametro("tipoMedicao", imovelEmissaoOrdensSeletivas.getTipoMedicao());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getMarca())
							&& !imovelEmissaoOrdensSeletivas.getMarca()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("marca", imovelEmissaoOrdensSeletivas.getMarca());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica())
							&& !imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("hidrometroClasseMetrologica",
								imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getHidrometroProtecao())
							&& !imovelEmissaoOrdensSeletivas.getHidrometroProtecao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva
								.addParametro("hidrometroProtecao", imovelEmissaoOrdensSeletivas.getHidrometroProtecao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao())
							&& !imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletiva.addParametro("hidrometroLocalInstalacao",
								imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao());
			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("anormalidadeHidrometro", imovelEmissaoOrdensSeletivas
							.getAnormalidadeHidrometro());

			relatorioEmitirOrdemServicoSeletiva.addParametro("numeroOcorrenciasConsecutivas", imovelEmissaoOrdensSeletivas
							.getNumeroOcorrenciasConsecutivas());

			relatorioEmitirOrdemServicoSeletiva.addParametro("colecaoDadosDoHidrometro", imovelEmissaoOrdensSeletivas
							.getColecaoDadosDoHidrometro());

			relatorioEmitirOrdemServicoSeletiva.addParametro("colecaoImoveis", imovelEmissaoOrdensSeletivas.getIdsImoveis());

			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
			relatorioEmitirOrdemServicoSeletiva.addParametro("idUsuario", String.valueOf(usuario.getId()));

			relatorioEmitirOrdemServicoSeletiva.addParametro("idComandoOsSeletiva",
							String.valueOf(imovelEmissaoOrdensSeletivas.getIdComandoOsServicoSeletiva()));

			// Fim da parte que vai mandar os parametros para o relatório

			int tipoRelatorio = TarefaRelatorio.TIPO_PDF;

			try{
				// PDF = 1 e TXT = 2
				String tipoArquivo = ParametroOrdemServico.P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA.executar();

				if(tipoArquivo.equals(ConstantesSistema.TXT)){
					tipoRelatorio = TarefaRelatorio.TIPO_ZIP;
				}
			}catch(ControladorException ex){
				throw new ActionServletException(ex.getMessage(), ex);
			}

			relatorioEmitirOrdemServicoSeletiva.addParametro("tipoFormatoRelatorio", tipoRelatorio);
			try{
				retorno = this.processarExibicaoRelatorioOrdemSeletiva(relatorioEmitirOrdemServicoSeletiva, tipoRelatorio,
								httpServletRequest, httpServletResponse, actionMapping);

			}catch(TarefaException e){
				throw new ActionServletException(e.getMessage(), "exibirFiltrarImovelEmissaoOrdensSeletivas.do?menu=sim", e, "");
			}catch(Exception e2){
				throw new ActionServletException(
								e2.getMessage(),
								"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
								e2);
			}
		}else{
			RelatorioEmitirOrdemServicoSeletivaSugestao relatorioEmitirOrdemServicoSeletivaSugestao = new RelatorioEmitirOrdemServicoSeletivaSugestao(
							(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("servicoTipo", imovelEmissaoOrdensSeletivas.getServicoTipo());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("servicoTipoDescricao", imovelEmissaoOrdensSeletivas
							.getServicoTipoDescricao());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("simulacao", imovelEmissaoOrdensSeletivas.getSimulacao());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("firma", imovelEmissaoOrdensSeletivas.getFirma());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("nomeFirma", imovelEmissaoOrdensSeletivas.getNomeFirma());

			relatorioEmitirOrdemServicoSeletivaSugestao
							.addParametro("quantidadeMaxima", imovelEmissaoOrdensSeletivas.getQuantidadeMaxima());

			// Valida o Elo e recupera a Descricao
			if(imovelEmissaoOrdensSeletivas.getElo() == null || imovelEmissaoOrdensSeletivas.getElo().equals("")){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("elo", "");
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("nomeElo", "");
			}else{
				Localidade localidadeElo = pesquisaElo(Integer.decode(imovelEmissaoOrdensSeletivas.getElo()));

				if(localidadeElo == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Elo");
				}
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("elo", localidadeElo.getLocalidade().getId().toString());
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("nomeElo", localidadeElo.getLocalidade().getDescricao());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao
							.addParametro("faturamentoGrupo", imovelEmissaoOrdensSeletivas.getFaturamentoGrupo());

			relatorioEmitirOrdemServicoSeletivaSugestao
							.addParametro("gerenciaRegional", imovelEmissaoOrdensSeletivas.getGerenciaRegional());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("localidade", imovelEmissaoOrdensSeletivas.getLocalidade());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getBairro())
							&& !imovelEmissaoOrdensSeletivas.getBairro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("bairro", imovelEmissaoOrdensSeletivas.getBairro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLogradouro())
							&& !imovelEmissaoOrdensSeletivas.getLogradouro()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("logradouro", imovelEmissaoOrdensSeletivas.getLogradouro());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getSetor())
							&& !imovelEmissaoOrdensSeletivas.getSetor()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("setor", imovelEmissaoOrdensSeletivas.getSetor());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getQuadra())
							&& !imovelEmissaoOrdensSeletivas.getQuadra()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("quadra", imovelEmissaoOrdensSeletivas.getQuadra());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getRota())
							&& !imovelEmissaoOrdensSeletivas.getRota()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("rota", imovelEmissaoOrdensSeletivas.getRota());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLote())
							&& !imovelEmissaoOrdensSeletivas.getLote()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("lote", imovelEmissaoOrdensSeletivas.getLote());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("inscricaoTipo", imovelEmissaoOrdensSeletivas.getInscricaoTipo());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getPerfilImovel())
							&& !imovelEmissaoOrdensSeletivas.getPerfilImovel()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("perfilImovel", imovelEmissaoOrdensSeletivas.getPerfilImovel());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getCategoria())
							&& !imovelEmissaoOrdensSeletivas.getCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("categoria", imovelEmissaoOrdensSeletivas.getCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getSubCategoria())
							&& !imovelEmissaoOrdensSeletivas.getSubCategoria()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("subCategoria", imovelEmissaoOrdensSeletivas.getSubCategoria());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao())
							&& !imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("ligacaoAguaSituacao",
								imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao())
							&& !imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("ligacaoEsgotoSituacao",
								imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloDataCorteInicial", intervaloDataCorteInicial);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloDataCorteFinal", intervaloDataCorteFinal);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("dataCorteInicial", dataCorteInicial);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("dataCorteFinal", dataCorteFinal);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeEconomiasInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeEconomiasInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeEconomiasFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeEconomiasFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroPontosUtilizacaoInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroPontosUtilizacaoInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroPontosUtilizacaoFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroPontosUtilizacaoFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeDocumentosInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeDocumentosInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeDocumentosFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeDocumentosFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroMoradoresInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroMoradoresInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroMoradoresFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroMoradoresFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloAreaConstruidaInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloAreaConstruidaInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloAreaConstruidaFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloAreaConstruidaFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloAreaConstruidaPredefinida", imovelEmissaoOrdensSeletivas
							.getIntervaloAreaConstruidaPredefinida());

			relatorioEmitirOrdemServicoSeletivaSugestao
							.addParametro("imovelCondominio", imovelEmissaoOrdensSeletivas.getImovelCondominio());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("consumoPorEconomia", imovelEmissaoOrdensSeletivas
							.getConsumoPorEconomia());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroConsumoMesInicial", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroConsumoMesInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloNumeroConsumoMesFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloNumeroConsumoMesFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("colecaoConsumoMedioImovel", imovelEmissaoOrdensSeletivas
							.getColecaoConsumoMedioImovel());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeContasVencidasInicial",
							imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeContasVencidasInicial());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("intervaloQuantidadeContasVencidasFinal", imovelEmissaoOrdensSeletivas
							.getIntervaloQuantidadeContasVencidasFinal());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("valorTotalDebitoVencido", valorTotalDebitoVencido);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("tipoMedicao", imovelEmissaoOrdensSeletivas.getTipoMedicao());

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getMarca())
							&& !imovelEmissaoOrdensSeletivas.getMarca()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("marca", imovelEmissaoOrdensSeletivas.getMarca());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica())
							&& !imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("hidrometroClasseMetrologica",
								imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getHidrometroProtecao())
							&& !imovelEmissaoOrdensSeletivas.getHidrometroProtecao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("hidrometroProtecao",
								imovelEmissaoOrdensSeletivas.getHidrometroProtecao());
			}

			if(!Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao())
							&& !imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao()[0].trim().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("hidrometroLocalInstalacao",
								imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao());
			}

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("anormalidadeHidrometro", imovelEmissaoOrdensSeletivas
							.getAnormalidadeHidrometro());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("numeroOcorrenciasConsecutivas", imovelEmissaoOrdensSeletivas
							.getNumeroOcorrenciasConsecutivas());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("colecaoDadosDoHidrometro", imovelEmissaoOrdensSeletivas
							.getColecaoDadosDoHidrometro());

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("colecaoImoveis", imovelEmissaoOrdensSeletivas.getIdsImoveis());

			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("idUsuario", String.valueOf(usuario.getId()));
			// Fim da parte que vai mandar os parametros para o relatório
			String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

			tipoRelatorio = String.valueOf(TarefaRelatorio.TIPO_PDF);

			relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			try{

				retorno = this.processarExibicaoRelatorio(relatorioEmitirOrdemServicoSeletivaSugestao, tipoRelatorio, httpServletRequest,
								httpServletResponse, actionMapping);

			}catch(TarefaException e){
				throw new ActionServletException(
								e.getMessage(),
								"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
								e);
			}catch(Exception e2){
				throw new ActionServletException(e2.getMessage(), e2);
			}

		}

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}

	/**
	 * Pesquisa o Elo da Localidade
	 * 
	 * @param elo
	 * @return
	 */
	private Localidade pesquisaElo(Integer elo){

		Fachada fachada = Fachada.getInstancia();
		Localidade localidade = null;

		FiltroLocalidade filtro = new FiltroLocalidade();
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, elo));
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());
		Iterator<Localidade> iColecaoLocalidade = colecaoLocalidade.iterator();
		localidade = iColecaoLocalidade.next();

		return localidade;
	}

	/**
	 * Pesquisa a Localidade
	 * 
	 * @param elo
	 * @return
	 */
	private Localidade pesquisaLocalidade(Integer idLocalidade){

		Fachada fachada = Fachada.getInstancia();
		Localidade localidade = null;

		FiltroLocalidade filtro = new FiltroLocalidade();
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
		filtro.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtro, Localidade.class.getName());
		Iterator<Localidade> iColecaoLocalidade = colecaoLocalidade.iterator();
		localidade = iColecaoLocalidade.next();

		return localidade;
	}

	/**
	 * Pesquisa o Setor Comercial
	 * 
	 * @param elo
	 * @return
	 */
	private SetorComercial pesquisaSetorComercial(Integer codigoSetorComercial, Integer idLocalidadeInicial, Integer idLocalidadeFinal){

		Fachada fachada = Fachada.getInstancia();
		SetorComercial setorComercial = null;

		FiltroSetorComercial filtro = new FiltroSetorComercial();
		filtro.adicionarParametro(new Intervalo(FiltroSetorComercial.ID_LOCALIDADE, idLocalidadeInicial, idLocalidadeFinal));
		filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercial));
		filtro.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<SetorComercial> colecaoSetor = fachada.pesquisar(filtro, SetorComercial.class.getName());
		Iterator<SetorComercial> iColecaoSetor = colecaoSetor.iterator();
		setorComercial = iColecaoSetor.next();

		return setorComercial;
	}

	/**
	 * @param numeroQuadra
	 * @param idLocalidadeInicial
	 * @param idLocalidadeFinal
	 * @param codigoSetorComercialInicial
	 * @param codigoSetorComercialFinal
	 * @return
	 */
	private Quadra pesquisaQuadra(Integer numeroQuadra, Integer idLocalidadeInicial, Integer idLocalidadeFinal,
					Integer codigoSetorComercialInicial, Integer codigoSetorComercialFinal){

		Fachada fachada = Fachada.getInstancia();
		Quadra quadra = null;

		FiltroQuadra filtro = new FiltroQuadra();
		filtro.adicionarParametro(new Intervalo(FiltroQuadra.ID_LOCALIDADE, idLocalidadeInicial, idLocalidadeFinal));
		filtro
						.adicionarParametro(new Intervalo(FiltroQuadra.CODIGO_SETORCOMERCIAL, codigoSetorComercialInicial,
										codigoSetorComercialFinal));
		filtro.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, numeroQuadra));
		filtro.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<Quadra> colecaoQuadra = fachada.pesquisar(filtro, Quadra.class.getName());
		if(colecaoQuadra == null || colecaoQuadra.isEmpty()){
			throw new ActionServletException("atencao.quadra.inexistente");
		}
		Iterator<Quadra> iColecaoQuadra = colecaoQuadra.iterator();
		quadra = iColecaoQuadra.next();

		return quadra;
	}

	/*
	 * private String retornaParametrosSelecionados(ImovelEmissaoOrdensSeletivasActionForm
	 * imovelEmissaoOrdensSeletivas) {
	 * String retorno = "";
	 * if (imovelEmissaoOrdensSeletivas.getTipoOrdem() != null) {
	 * retorno += "Tipo de Ordem: " + imovelEmissaoOrdensSeletivas.getTipoOrdem() + "<br>";
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getSugestao() != null) {
	 * retorno += "Sugestão: ";
	 * if (imovelEmissaoOrdensSeletivas.getSugestao().equals("1")) {
	 * retorno += "SIM" + "<br>";
	 * }else {
	 * retorno += "NÃO" + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getFirma() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getFirma().equals("-1")) {
	 * retorno += "Firma: " + imovelEmissaoOrdensSeletivas.getNomeFirma() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getQuantidadeMaxima() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getQuantidadeMaxima().equals("")) {
	 * retorno += "Quantidade Máxima: " + imovelEmissaoOrdensSeletivas.getQuantidadeMaxima() +
	 * "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getElo() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getElo().equals("")) {
	 * retorno += "Elo: " + imovelEmissaoOrdensSeletivas.getNomeElo() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getLocalidadeInicial() != null &&
	 * imovelEmissaoOrdensSeletivas.getLocalidadeFinal() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getLocalidadeInicial().equals("") &&
	 * !imovelEmissaoOrdensSeletivas.getLocalidadeFinal().equals("")) {
	 * retorno += "Localidade Inicial: " + imovelEmissaoOrdensSeletivas.getLocalidadeInicial() +
	 * " - " + imovelEmissaoOrdensSeletivas.getNomeLocalidadeInicial() + "<br>";
	 * retorno += "Localidade Final: " + imovelEmissaoOrdensSeletivas.getLocalidadeFinal() +
	 * " - " + imovelEmissaoOrdensSeletivas.getNomeLocalidadeFinal() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getSetorComercialInicial() != null &&
	 * imovelEmissaoOrdensSeletivas.getSetorComercialFinal() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getSetorComercialInicial().equals("") &&
	 * !imovelEmissaoOrdensSeletivas.getSetorComercialFinal().equals("")) {
	 * retorno += "Setor Comercial Inicial: " +
	 * imovelEmissaoOrdensSeletivas.getCodigoSetorComercialInicial() +
	 * " - " + imovelEmissaoOrdensSeletivas.getNomeSetorComercialInicial() + "<br>";
	 * retorno += "Setor Comercial Final: " +
	 * imovelEmissaoOrdensSeletivas.getCodigoSetorComercialFinal() +
	 * " - " + imovelEmissaoOrdensSeletivas.getNomeSetorComercialFinal() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getQuadraInicial() != null &&
	 * imovelEmissaoOrdensSeletivas.getQuadraFinal() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getQuadraInicial().equals("") &&
	 * !imovelEmissaoOrdensSeletivas.getQuadraFinal().equals("")) {
	 * retorno += "Quadra Inicial: " + imovelEmissaoOrdensSeletivas.getQuadraInicial() + "<br>";
	 * retorno += "Quadra Final: " + imovelEmissaoOrdensSeletivas.getQuadraFinal() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getPerfilImovel() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getPerfilImovel().equals("-1")) {
	 * retorno += "Perfil do Imóvel: " + imovelEmissaoOrdensSeletivas.getPerfilImovelDescricao() +
	 * "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getCategoria() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getCategoria().equals("-1")) {
	 * retorno += "Categoria: " + imovelEmissaoOrdensSeletivas.getCategoriaDescricao() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getSubCategoria() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getSubCategoria().equals("-1")) {
	 * retorno += "Sub Categoria: " + imovelEmissaoOrdensSeletivas.getSubCategoriaDescricao() +
	 * "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasInicial() != null &&
	 * imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasFinal() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasInicial().equals("") &&
	 * !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasFinal().equals("")) {
	 * retorno += "Quant. Econom.: " +
	 * imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasInicial() +
	 * " à " + imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasFinal() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosInicial() != null &&
	 * imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosInicial() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosInicial().equals("") &&
	 * !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosInicial().equals("")) {
	 * retorno += "Quant. Documentos: " +
	 * imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosInicial() +
	 * " à " + imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosFinal() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresInicial() != null &&
	 * imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresFinal() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresInicial().equals("") &&
	 * !imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresFinal().equals("")) {
	 * retorno += "Número Moradores: " +
	 * imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresInicial() +
	 * " à " + imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresFinal() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial() != null &&
	 * imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial().equals("") &&
	 * !imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal().equals("")) {
	 * retorno += "Área Construida: " +
	 * imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial() +
	 * " à " + imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getImovelCondominio() != null) {
	 * retorno += "Imóvel Condomínio: ";
	 * if (imovelEmissaoOrdensSeletivas.getImovelCondominio().equals("1")) {
	 * retorno += "SIM" + "<br>";
	 * }else {
	 * retorno += "NÃO" + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getMediaImovel() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getMediaImovel().equals("")) {
	 * retorno += "Média do Imóvel: " + imovelEmissaoOrdensSeletivas.getMediaImovel() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getConsumoPorEconomia() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getConsumoPorEconomia().equals("")) {
	 * retorno += "Cons. por Economia: " + imovelEmissaoOrdensSeletivas.getConsumoPorEconomia() +
	 * "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getTipoMedicao() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getTipoMedicao().equals("-1")) {
	 * retorno += "Tipo Medicao: " + imovelEmissaoOrdensSeletivas.getTipoMedicaoDescricao() +
	 * "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getCapacidade() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getCapacidade().equals("-1")) {
	 * retorno += "Capacidade Hid.: " + imovelEmissaoOrdensSeletivas.getCapacidadeDescricao() +
	 * "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getMarca() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getMarca().equals("-1")) {
	 * retorno += "Marca Hid.: " + imovelEmissaoOrdensSeletivas.getMarcaDescricao() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getAnormalidadeHidrometro() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getAnormalidadeHidrometro().equals("-1")) {
	 * retorno += "Anormalidade Hid: " + imovelEmissaoOrdensSeletivas.getAnormalidadeHidrometro() +
	 * "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getNumeroOcorrenciasConsecutivas() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getNumeroOcorrenciasConsecutivas().equals("")) {
	 * retorno += "Núm. Ocorrências Consecutivas: " +
	 * imovelEmissaoOrdensSeletivas.getNumeroOcorrenciasConsecutivas() + "<br>";
	 * }
	 * }
	 * if (imovelEmissaoOrdensSeletivas.getMesAnoInstalacao() != null) {
	 * if (!imovelEmissaoOrdensSeletivas.getMesAnoInstalacao().equals("")) {
	 * String mesAno = imovelEmissaoOrdensSeletivas.getMesAnoInstalacao();
	 * mesAno = mesAno.substring(4, 6) + "/" + mesAno.substring(0, 4);
	 * retorno += "Mes/Ano Instalação Hid.: " + mesAno + "<br>";
	 * }
	 * }
	 * return retorno;
	 * }
	 */
}