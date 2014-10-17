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
 * Classe respons�vel por criar o Relat�rio de Ordem de Servi�o
 * 
 * @author Rafael Corr�a
 * @created 11 de Julho de 2005
 * @author Virg�nia Melo
 * @date 23/03/2009
 *       Exibir o n�mero do hidr�metro na OS.
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
	 * < <Descri��o do m�todo>>
	 * 
	 * @param bairros
	 *            Description of the Parameter
	 * @param bairroParametros
	 *            Description of the Parameter
	 * @return Descri��o do retorno
	 * @exception RelatorioVazioException
	 *                Descri��o da exce��o
	 */
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// Integer idOrdemServico = (Integer) getParametro("idOrdemServico");
		StringTokenizer idsOrdemServico = (StringTokenizer) getParametro("idsOrdemServico");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioOrdemServicoBean relatorioBean = null;

		OSRelatorioHelper ordemServicoRelatorioHelper = null;

		Collection colecaoIdsOrdemServico = new ArrayList();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		while(idsOrdemServico.hasMoreTokens()){
			// Coloca os ids da OS que ser�o impressas dentro de uma cole��o para serem pesquisadas
			// e atualizadas posteriormente
			colecaoIdsOrdemServico.add(new Integer(idsOrdemServico.nextToken()));
		}

		Collection colecaoOSRelatorioHelper = fachada.pesquisarOSRelatorio(colecaoIdsOrdemServico);

		if(colecaoOSRelatorioHelper != null && !colecaoOSRelatorioHelper.isEmpty()){

			Iterator colecaoOSRelatorioHelperIterator = colecaoOSRelatorioHelper.iterator();

			while(colecaoOSRelatorioHelperIterator.hasNext()){

				ordemServicoRelatorioHelper = (OSRelatorioHelper) colecaoOSRelatorioHelperIterator.next();

				// Prepara os campos na formata��o correta ou passando-os para String para ser
				// colocado no Bean do relat�rio

				// Data da Gera��o
				String dataGeracao = "";
				if(ordemServicoRelatorioHelper.getDataGeracao() != null){
					dataGeracao = Util.formatarData(ordemServicoRelatorioHelper.getDataGeracao());
				}

				// Matr�cula do Im�vel
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

				// Situa��o �gua/Esgoto
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

				// Servi�o Solicitado
				String servicoSolicitado = "";
				if(ordemServicoRelatorioHelper.getIdServicoSolicitado() != null){
					servicoSolicitado = ordemServicoRelatorioHelper.getIdServicoSolicitado() + " - "
									+ ordemServicoRelatorioHelper.getDescricaoServicoSolicitado();
				}

				// Valor Solicitado(Valor do servi�o tipo)
				String valorSolicitado = "";
				if(ordemServicoRelatorioHelper.getValorServicoSolicitado() != null
								&& !ordemServicoRelatorioHelper.getValorServicoSolicitado().equals("0,00")){
					valorSolicitado = "R$ " + ordemServicoRelatorioHelper.getValorServicoSolicitado();
				}

				// Recuperar n�mero do hidr�metro
				String numeroHidrometro = "";

				if(idImovel != null && !idImovel.equals("")){

					Imovel imovel = fachada.pesquisarImovel(Integer.valueOf(idImovel));

					if(imovel != null && imovel.getLigacaoAgua() != null
									&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
									&& imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro() != null){

						// HIDI_ID da liga��o de �gua
						numeroHidrometro = imovel.getLigacaoAgua().getHidrometroInstalacaoHistorico().getNumeroHidrometro();

					}else if(imovel.getHidrometroInstalacaoHistorico() != null){

						// HIDI_ID do imovel
						numeroHidrometro = imovel.getHidrometroInstalacaoHistorico().getNumeroHidrometro();
					}
				}

				if(sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAERN)
								|| sistemaParametro.getCodigoEmpresaFebraban().equals(SistemaParametro.CODIGO_EMPRESA_FEBRABAN_CAER)){

					// Previs�o
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

					// Local Ocorr�ncia
					String localOcorrencia = "";

					if(ordemServicoRelatorioHelper.getLocalOcorrencia() != null){
						localOcorrencia = ordemServicoRelatorioHelper.getLocalOcorrencia();
					}

					// Inscri��o
					String inscricaoImovel = "";
					if(ordemServicoRelatorioHelper.getInscricaoImovel() != null){
						inscricaoImovel = ordemServicoRelatorioHelper.getInscricaoImovel();
					}

					// Nome Solicitante
					String nomeSolicitante = "";
					if(ordemServicoRelatorioHelper.getNomeSolicitante() != null){
						nomeSolicitante = ordemServicoRelatorioHelper.getNomeSolicitante();
					}

					// Servi�o Tipo Refer�ncia
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

									// N�mero OS
									ordemServicoRelatorioHelper.getIdOrdemServico().toString(),

									// Id do RA
									idRA,

									// Data da Gera��o
									dataGeracao,

									// Previs�o
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

									// Inscri��o do Im�vel
									inscricaoImovel,

									// Endere�o/Fone
									enderecoFone,

									// Matr�cula do Im�vel
									idImovel,

									// Localidade/Rota
									localidadeRota,

									// Ponto de Refer�ncia
									ordemServicoRelatorioHelper.getPontoReferencia(),

									// Situa��o �gua/Esgoto
									situacaoAguaEsgoto,

									// Categoria/Quantidade de Economias
									categoriaQtdeEconomias,

									// Local da Ocorr�ncia
									localOcorrencia,

									// Pavimento Rua/Cal�ada
									ordemServicoRelatorioHelper.getPavimentoRua() + "/" + ordemServicoRelatorioHelper.getPavimentoCalcada(),

									// Servi�o Solicitado
									servicoSolicitado,

									// Servi�o Solicitado
									valorSolicitado,

									// Observa��o da OS
									ordemServicoRelatorioHelper.getObservacaoOS() + " " + ordemServicoRelatorioHelper.getObservacaoRA(),

									// Servi�o Tipo Refer�ncia
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

					// adiciona o bean a cole��o
					relatorioBeans.add(relatorioBean);

				}else{

					// Inscri��o do Im�vel
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

					// N�mero OS
									ordemServicoRelatorioHelper.getIdOrdemServico().toString(),

									// Data da Gera��o
									dataGeracao,

									// Inscri��o do Im�vel
									inscricao,

									// Matr�cula do Im�vel
									idImovel,

									// Categoria/Quantidade de Economias
									categoriaQtdeEconomias,

									// Origem
									ordemServicoRelatorioHelper.getUnidadeGeracao(),

									// Situa��o �gua/Esgoto
									situacaoAguaEsgoto,

									// Esgoto Fixo
									esgotoFixo,

									// Pavimento Rua
									ordemServicoRelatorioHelper.getPavimentoRua(),

									// Pavimento Cal�ada
									ordemServicoRelatorioHelper.getPavimentoCalcada(),

									// Meio
									ordemServicoRelatorioHelper.getMeio(),

									// Endere�o
									ordemServicoRelatorioHelper.getEndereco(),

									// Nome do Atendente
									ordemServicoRelatorioHelper.getNomeAtendente(),

									// Id do Atendente
									idAtendente,

									// Ponto de Refer�ncia
									ordemServicoRelatorioHelper.getPontoReferencia(),

									// Telefone
									ordemServicoRelatorioHelper.getTelefone(),

									// Servi�o Solicitado
									servicoSolicitado,

									// Local da Ocorr�ncia
									ordemServicoRelatorioHelper.getLocalOcorrencia(),

									// Previs�o
									previsao,

									// Observa��o RA
									ordemServicoRelatorioHelper.getObservacaoRA(),

									// Observa��o OS
									ordemServicoRelatorioHelper.getObservacaoOS(),

									// Solicitante
									solicitante,

									// Unidade
									unidade,

									// Id do RA
									idRA,

									// Tipo Solicitante Usu�rio
									tipoSolicitanteUsuario,

									// Tipo Solicitante Empresa
									tipoSolicitanteEmpresa,

									// Especifica��o
									ordemServicoRelatorioHelper.getEspecificao(),

									numeroHidrometro);

					// adiciona o bean a cole��o
					relatorioBeans.add(relatorioBean);
				}
			}
		}

		fachada.atualizarOrdemServicoRelatorio(colecaoIdsOrdemServico);

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ORDEM_SERVICO, parametros, ds, tipoFormatoRelatorio);

		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.ORDEM_SERVICO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
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