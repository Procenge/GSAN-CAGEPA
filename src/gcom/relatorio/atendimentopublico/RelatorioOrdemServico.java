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

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.bean.OSRelatorioHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.AnaliseConsumoRelatorioOSHelper;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Classe responsável por criar o Relatório de Ordem de Serviço
 * 
 * @author Rafael Corrêa
 * @created 11 de Julho de 2005
 * @author Virgínia Melo
 * @date 23/03/2009
 *       Exibir o número do hidrômetro na OS.
 */
public class RelatorioOrdemServico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioOrdemServico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ORDEM_SERVICO);
	}

	@Deprecated
	public RelatorioOrdemServico() {

		super(null, "");
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// Integer idOrdemServico = (Integer) getParametro("idOrdemServico");
		StringTokenizer idsOrdemServico = (StringTokenizer) getParametro("idsOrdemServico");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioOrdemServicoBean relatorioBean = null;

		OSRelatorioHelper ordemServicoRelatorioHelper = null;

		Collection colecaoIdsOrdemServico = new ArrayList();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		while(idsOrdemServico.hasMoreTokens()){
			// Coloca os ids da OS que serão impressas dentro de uma coleção para serem pesquisadas
			// e atualizadas posteriormente
			colecaoIdsOrdemServico.add(new Integer(idsOrdemServico.nextToken()));
		}

		Collection colecaoOSRelatorioHelper = fachada.pesquisarOSRelatorio(colecaoIdsOrdemServico);

		if(colecaoOSRelatorioHelper != null && !colecaoOSRelatorioHelper.isEmpty()){

			Iterator colecaoOSRelatorioHelperIterator = colecaoOSRelatorioHelper.iterator();

			while(colecaoOSRelatorioHelperIterator.hasNext()){

				ordemServicoRelatorioHelper = (OSRelatorioHelper) colecaoOSRelatorioHelperIterator.next();

				// Prepara os campos na formatação correta ou passando-os para String para ser
				// colocado no Bean do relatório

				// Data da Geração
				String dataGeracao = "";
				if(ordemServicoRelatorioHelper.getDataGeracao() != null){
					dataGeracao = Util.formatarData(ordemServicoRelatorioHelper.getDataGeracao());
				}

				// Matrícula do Imóvel
				String idImovel = "";
				if(ordemServicoRelatorioHelper.getIdImovel() != null){
					idImovel = Util.retornaMatriculaImovelParametrizada(ordemServicoRelatorioHelper.getIdImovel());
				}

				// Categoria/Quantidade Economias
				String categoriaQtdeEconomias = "";
				if(ordemServicoRelatorioHelper.getCategoria() != null && !ordemServicoRelatorioHelper.getCategoria().equals("")){

					categoriaQtdeEconomias = ordemServicoRelatorioHelper.getCategoria() + "/"
									+ ordemServicoRelatorioHelper.getQuantidadeEconomias();
				}

				// Situação Água/Esgoto
				String situacaoAguaEsgoto = "";
				if(ordemServicoRelatorioHelper.getSituacaoAgua() != null && !ordemServicoRelatorioHelper.getSituacaoAgua().equals("")){
					situacaoAguaEsgoto = ordemServicoRelatorioHelper.getSituacaoAgua() + "/"
									+ ordemServicoRelatorioHelper.getSituacaoEsgoto();
				}

				// Id do RA
				String idRA = "";
				if(ordemServicoRelatorioHelper.getIdRA() != null){
					idRA = ordemServicoRelatorioHelper.getIdRA().toString();
				}

				// Serviço Solicitado
				String servicoSolicitado = "";
				if(ordemServicoRelatorioHelper.getIdServicoSolicitado() != null){
					servicoSolicitado = ordemServicoRelatorioHelper.getIdServicoSolicitado() + " - "
									+ ordemServicoRelatorioHelper.getDescricaoServicoSolicitado();
				}

				// Valor Solicitado(Valor do serviço tipo)
				String valorSolicitado = "";
				if(ordemServicoRelatorioHelper.getValorServicoSolicitado() != null
								&& !ordemServicoRelatorioHelper.getValorServicoSolicitado().equals("0,00")){
					valorSolicitado = "R$ " + ordemServicoRelatorioHelper.getValorServicoSolicitado();
				}

				// Recuperar número do hidrômetro
				String numeroHidrometro = "";

				if(idImovel != null && !idImovel.equals("")){

					Imovel imovel = fachada.pesquisarImovel(Integer.valueOf(idImovel));

					if(imovel != null && imovel.getLigacaoAgua() != null
									&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
									&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

						// HIDI_ID da ligação de água
						numeroHidrometro = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro();

					}else if(imovel.getHidrometroInstalacaoHistorico() != null){

						// HIDI_ID do imovel
						numeroHidrometro = imovel.getHidrometroInstalacaoHistorico().getNumeroHidrometro();
					}
				}

				if(sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAERN)
								|| sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAER)){

					// Previsão
					String previsao = "";

					if(ordemServicoRelatorioHelper.getTempoMedioExecucao() != null){
						previsao = ordemServicoRelatorioHelper.getTempoMedioExecucao().toString() + ":00";
					}

					// Endereco/Fone
					String enderecoFone = "";

					if(ordemServicoRelatorioHelper.getEndereco() != null){
						enderecoFone = ordemServicoRelatorioHelper.getEndereco();
					}

					if(enderecoFone.trim().equals("") && ordemServicoRelatorioHelper.getTelefone() != null){
						enderecoFone = ordemServicoRelatorioHelper.getTelefone();
					}else if(ordemServicoRelatorioHelper.getTelefone() != null){
						enderecoFone = enderecoFone + "/" + ordemServicoRelatorioHelper.getTelefone();
					}

					// Localidade/Rota
					String localidadeRota = "";

					if(ordemServicoRelatorioHelper.getIdLocalidade() != null){
						localidadeRota = ordemServicoRelatorioHelper.getIdLocalidade().toString();
					}

					if(ordemServicoRelatorioHelper.getCodigoRota() != null){
						localidadeRota = localidadeRota + "/" + ordemServicoRelatorioHelper.getCodigoRota().toString();
					}

					if(ordemServicoRelatorioHelper.getSequencialRota() != null){
						localidadeRota = localidadeRota + "/" + ordemServicoRelatorioHelper.getSequencialRota().toString();
					}

					// Local Ocorrência
					String localOcorrencia = "";

					if(ordemServicoRelatorioHelper.getLocalOcorrencia() != null){
						localOcorrencia = ordemServicoRelatorioHelper.getLocalOcorrencia();
					}

					// Inscrição
					String inscricaoImovel = "";
					if(ordemServicoRelatorioHelper.getInscricaoImovel() != null){
						inscricaoImovel = ordemServicoRelatorioHelper.getInscricaoImovel();
					}

					// Nome Solicitante
					String nomeSolicitante = "";
					if(ordemServicoRelatorioHelper.getNomeSolicitante() != null){
						nomeSolicitante = ordemServicoRelatorioHelper.getNomeSolicitante();
					}

					// Serviço Tipo Referência
					String servicoTipoReferencia = "";
					if(ordemServicoRelatorioHelper.getIdServicoTipoReferencia() != null){
						servicoTipoReferencia = ordemServicoRelatorioHelper.getIdServicoTipoReferencia() + " - "
										+ ordemServicoRelatorioHelper.getDescricaoServicoTipoReferencia();
					}

					String anoMesHistoricoConsumo1 = "";
					String dtLeituraAtualInformada1 = "";
					String leituraAtualInformada1 = "";
					String consumoFaturado1 = "";
					String descAbrevAnormalidadeConsumo1 = "";

					String anoMesHistoricoConsumo2 = "";
					String dtLeituraAtualInformada2 = "";
					String leituraAtualInformada2 = "";
					String consumoFaturado2 = "";
					String descAbrevAnormalidadeConsumo2 = "";

					String anoMesHistoricoConsumo3 = "";
					String dtLeituraAtualInformada3 = "";
					String leituraAtualInformada3 = "";
					String consumoFaturado3 = "";
					String descAbrevAnormalidadeConsumo3 = "";

					String anoMesHistoricoConsumo4 = "";
					String dtLeituraAtualInformada4 = "";
					String leituraAtualInformada4 = "";
					String consumoFaturado4 = "";
					String descAbrevAnormalidadeConsumo4 = "";

					String anoMesHistoricoConsumo5 = "";
					String dtLeituraAtualInformada5 = "";
					String leituraAtualInformada5 = "";
					String consumoFaturado5 = "";
					String descAbrevAnormalidadeConsumo5 = "";

					String anoMesHistoricoConsumo6 = "";
					String dtLeituraAtualInformada6 = "";
					String leituraAtualInformada6 = "";
					String consumoFaturado6 = "";
					String descAbrevAnormalidadeConsumo6 = "";

					AnaliseConsumoRelatorioOSHelper analiseConsumoRelatorioOSHelper = null;

					if(ordemServicoRelatorioHelper.getIdImovel() != null){
						analiseConsumoRelatorioOSHelper = fachada.obterDadosAnaliseConsumo(ordemServicoRelatorioHelper.getIdImovel());
					}

					ordemServicoRelatorioHelper.setAnaliseConsumoRelatorioOSHelper(analiseConsumoRelatorioOSHelper);

					if(ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper() != null){

						anoMesHistoricoConsumo1 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getAnoMesHistoricoConsumo1();
						dtLeituraAtualInformada1 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDtLeituraAtualInformada1();
						leituraAtualInformada1 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getLeituraAtualInformada1();
						consumoFaturado1 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper().getConsumoFaturado1();
						descAbrevAnormalidadeConsumo1 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDescAbrevAnormalidadeConsumo1();

						anoMesHistoricoConsumo2 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getAnoMesHistoricoConsumo2();
						dtLeituraAtualInformada2 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDtLeituraAtualInformada2();
						leituraAtualInformada2 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getLeituraAtualInformada2();
						consumoFaturado2 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper().getConsumoFaturado2();
						descAbrevAnormalidadeConsumo2 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDescAbrevAnormalidadeConsumo2();

						anoMesHistoricoConsumo3 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getAnoMesHistoricoConsumo3();
						dtLeituraAtualInformada3 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDtLeituraAtualInformada3();
						leituraAtualInformada3 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getLeituraAtualInformada3();
						consumoFaturado3 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper().getConsumoFaturado3();
						descAbrevAnormalidadeConsumo3 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDescAbrevAnormalidadeConsumo3();

						anoMesHistoricoConsumo4 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getAnoMesHistoricoConsumo4();
						dtLeituraAtualInformada4 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDtLeituraAtualInformada4();
						leituraAtualInformada4 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getLeituraAtualInformada4();
						consumoFaturado4 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper().getConsumoFaturado4();
						descAbrevAnormalidadeConsumo4 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDescAbrevAnormalidadeConsumo4();

						anoMesHistoricoConsumo5 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getAnoMesHistoricoConsumo5();
						dtLeituraAtualInformada5 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDtLeituraAtualInformada5();
						leituraAtualInformada5 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getLeituraAtualInformada5();
						consumoFaturado5 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper().getConsumoFaturado5();
						descAbrevAnormalidadeConsumo5 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDescAbrevAnormalidadeConsumo5();

						anoMesHistoricoConsumo6 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getAnoMesHistoricoConsumo6();
						dtLeituraAtualInformada6 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDtLeituraAtualInformada6();
						leituraAtualInformada6 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getLeituraAtualInformada6();
						consumoFaturado6 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper().getConsumoFaturado6();
						descAbrevAnormalidadeConsumo6 = ordemServicoRelatorioHelper.getAnaliseConsumoRelatorioOSHelper()
										.getDescAbrevAnormalidadeConsumo6();

					}

					if(ordemServicoRelatorioHelper.getIdImovel() != null){

						HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = fachada.obterDadosHidrometro(ordemServicoRelatorioHelper
										.getIdImovel());

						ordemServicoRelatorioHelper.setHidrometroRelatorioOSHelper(hidrometroRelatorioOSHelper);
					}

					String hidrometroNumero = "";
					String hidrometroFixo = "";
					String hidrometroMarca = "";
					String hidrometroCapacidade = "";
					String hidrometroDiametro = "";
					String hidrometroLocal = "";
					String hidrometroLeitura = "";
					String hidrometroNumeroDigitos = "";

					if(ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper() != null){
						hidrometroNumero = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroNumero();
						hidrometroFixo = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroFixo();
						hidrometroMarca = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroMarca();
						hidrometroCapacidade = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroCapacidade();
						hidrometroDiametro = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroDiametro();
						hidrometroLocal = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroLocal();
						hidrometroLeitura = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroLeitura();
						hidrometroNumeroDigitos = ordemServicoRelatorioHelper.getHidrometroRelatorioOSHelper().getHidrometroNumeroDigitos();
					}

					relatorioBean = new RelatorioOrdemServicoBean(

									// Número OS
									ordemServicoRelatorioHelper.getIdOrdemServico().toString(),

									// Id do RA
									idRA,

									// Data da Geração
									dataGeracao,

									// Previsão
									previsao,

									// Meio
									ordemServicoRelatorioHelper.getMeio(),

									// Origem
									ordemServicoRelatorioHelper.getOrigem(),

									// Atendente
									ordemServicoRelatorioHelper.getNomeAtendente(),

									// Destino
									"",

									// Solicitante
									nomeSolicitante,

									// Inscrição do Imóvel
									inscricaoImovel,

									// Endereço/Fone
									enderecoFone,

									// Matrícula do Imóvel
									idImovel,

									// Localidade/Rota
									localidadeRota,

									// Ponto de Referência
									ordemServicoRelatorioHelper.getPontoReferencia(),

									// Situação Água/Esgoto
									situacaoAguaEsgoto,

									// Categoria/Quantidade de Economias
									categoriaQtdeEconomias,

									// Local da Ocorrência
									localOcorrencia,

									// Pavimento Rua/Calçada
									ordemServicoRelatorioHelper.getPavimentoRua() + "/" + ordemServicoRelatorioHelper.getPavimentoCalcada(),

									// Serviço Solicitado
									servicoSolicitado,

									// Serviço Solicitado
									valorSolicitado,

									// Observação da OS
									ordemServicoRelatorioHelper.getObservacaoOS() + " " + ordemServicoRelatorioHelper.getObservacaoRA(),

									// Serviço Tipo Referência
									servicoTipoReferencia,

									anoMesHistoricoConsumo1, dtLeituraAtualInformada1, leituraAtualInformada1, consumoFaturado1,
									descAbrevAnormalidadeConsumo1,

									anoMesHistoricoConsumo2, dtLeituraAtualInformada2, leituraAtualInformada2, consumoFaturado2,
									descAbrevAnormalidadeConsumo2,

									anoMesHistoricoConsumo3, dtLeituraAtualInformada3, leituraAtualInformada3, consumoFaturado3,
									descAbrevAnormalidadeConsumo3,

									anoMesHistoricoConsumo4, dtLeituraAtualInformada4, leituraAtualInformada4, consumoFaturado4,
									descAbrevAnormalidadeConsumo4,

									anoMesHistoricoConsumo5, dtLeituraAtualInformada5, leituraAtualInformada5, consumoFaturado5,
									descAbrevAnormalidadeConsumo5,

									anoMesHistoricoConsumo6, dtLeituraAtualInformada6, leituraAtualInformada6, consumoFaturado6,
									descAbrevAnormalidadeConsumo6,

									hidrometroNumero, hidrometroFixo, hidrometroMarca, hidrometroCapacidade, hidrometroDiametro,
									hidrometroLocal, hidrometroLeitura, hidrometroNumeroDigitos);

					// adiciona o bean a coleção
					relatorioBeans.add(relatorioBean);

				}else{

					// Inscrição do Imóvel
					String inscricao = ordemServicoRelatorioHelper.getInscricaoImovel();
					inscricao = inscricao.replace(".", "");

					String esgotoFixo = "";
					if(ordemServicoRelatorioHelper.getEsgotoFixo() != null){
						esgotoFixo = ordemServicoRelatorioHelper.getEsgotoFixo().toString();
					}

					// Solicitante
					String solicitante = "";
					if(ordemServicoRelatorioHelper.getNomeSolicitante() != null
									&& !ordemServicoRelatorioHelper.getNomeSolicitante().equals("")){
						if(ordemServicoRelatorioHelper.getIdSolicitante() != null){
							solicitante = solicitante + ordemServicoRelatorioHelper.getIdSolicitante().toString() + " - ";
						}

						solicitante = solicitante + ordemServicoRelatorioHelper.getNomeSolicitante().trim();
					}

					String previsao = "";
					if(ordemServicoRelatorioHelper.getPrevisao() != null){
						previsao = Util.formatarData(ordemServicoRelatorioHelper.getPrevisao());
					}

					String unidade = "";
					String tipoSolicitanteUsuario = "";
					String tipoSolicitanteEmpresa = "";
					if(ordemServicoRelatorioHelper.getIdUnidade() != null){
						unidade = ordemServicoRelatorioHelper.getIdUnidade().toString() + " - "
										+ ordemServicoRelatorioHelper.getDescricaoUnidade();
						tipoSolicitanteEmpresa = "X";
					}else{
						tipoSolicitanteUsuario = "X";
					}

					String idAtendente = "";
					if(ordemServicoRelatorioHelper.getIdAtendente() != null){
						idAtendente = ordemServicoRelatorioHelper.getIdAtendente().toString();
					}

					relatorioBean = new RelatorioOrdemServicoBean(

					// Número OS
									ordemServicoRelatorioHelper.getIdOrdemServico().toString(),

									// Data da Geração
									dataGeracao,

									// Inscrição do Imóvel
									inscricao,

									// Matrícula do Imóvel
									idImovel,

									// Categoria/Quantidade de Economias
									categoriaQtdeEconomias,

									// Origem
									ordemServicoRelatorioHelper.getUnidadeGeracao(),

									// Situação Água/Esgoto
									situacaoAguaEsgoto,

									// Esgoto Fixo
									esgotoFixo,

									// Pavimento Rua
									ordemServicoRelatorioHelper.getPavimentoRua(),

									// Pavimento Calçada
									ordemServicoRelatorioHelper.getPavimentoCalcada(),

									// Meio
									ordemServicoRelatorioHelper.getMeio(),

									// Endereço
									ordemServicoRelatorioHelper.getEndereco(),

									// Nome do Atendente
									ordemServicoRelatorioHelper.getNomeAtendente(),

									// Id do Atendente
									idAtendente,

									// Ponto de Referência
									ordemServicoRelatorioHelper.getPontoReferencia(),

									// Telefone
									ordemServicoRelatorioHelper.getTelefone(),

									// Serviço Solicitado
									servicoSolicitado,

									// Local da Ocorrência
									ordemServicoRelatorioHelper.getLocalOcorrencia(),

									// Previsão
									previsao,

									// Observação RA
									ordemServicoRelatorioHelper.getObservacaoRA(),

									// Observação OS
									ordemServicoRelatorioHelper.getObservacaoOS(),

									// Solicitante
									solicitante,

									// Unidade
									unidade,

									// Id do RA
									idRA,

									// Tipo Solicitante Usuário
									tipoSolicitanteUsuario,

									// Tipo Solicitante Empresa
									tipoSolicitanteEmpresa,

									// Especificação
									ordemServicoRelatorioHelper.getEspecificao(),

									numeroHidrometro);

					// adiciona o bean a coleção
					relatorioBeans.add(relatorioBean);
				}
			}
		}

		fachada.atualizarOrdemServicoRelatorio(colecaoIdsOrdemServico);

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.ORDEM_SERVICO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;
		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioOrdemServico", this);
	}
}