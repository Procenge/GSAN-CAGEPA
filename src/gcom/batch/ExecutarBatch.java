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

package gcom.batch;

import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesAplicacao;
import gcom.util.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ExecutarBatch
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Inst�ncia do formul�rio que est� sendo utilizado
		DynaActionForm executarBatchActionForm = (DynaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Integer tipoProcesso = null;
		if(executarBatchActionForm.get("tipoProcesso") != null && !"".equals("tipoProcesso")){

			tipoProcesso = Util.converterStringParaInteger((String) executarBatchActionForm.get("tipoProcesso"));
		}



		FaturamentoGrupo faturamentoGrupo = null;
		if(tipoProcesso != null){
			switch(tipoProcesso){

				case 1:
					System.out.println("*** executarAjustarRegistrosContaEGuiaDeso");
					fachada.executarAjustarRegistrosContaEGuiaDeso();
					break;

				case 2:
					System.out.println("*** executarAjustarContabilidadeArrecadacao");
					// fachada.executarAjustarContabilidadeArrecadacao(limite);
					break;

				case 3:
					System.out.println("*** executarCancelarDebitos");
					fachada.executarCancelarDebitos();
					break;

				case 4:
					System.out.println("*** executarInserirDebitos");
					fachada.executarInserirDebitos();
					break;

				case 5:

					System.out.println("*** Gerar resumo_faturamento_simulacao grupo 18 ");
					faturamentoGrupo = fachada.pesquisarFaturamentoGrupoPorID(18);
					fachada.gerarResumoFaturamentoSimulacaoAjusteCasal(faturamentoGrupo, 201304, 6909);
					break;

				case 6:

					System.out.println("*** Gerar resumo_faturamento_simulacao grupo 19 ");
					faturamentoGrupo = fachada.pesquisarFaturamentoGrupoPorID(19);
					fachada.gerarResumoFaturamentoSimulacaoAjusteCasal(faturamentoGrupo, 201304, 6915);
					break;

				case 7:

					System.out.println("*** Gerar resumo_faturamento_simulacao grupo 46 ");
					faturamentoGrupo = fachada.pesquisarFaturamentoGrupoPorID(46);
					fachada.gerarResumoFaturamentoSimulacaoAjusteCasal(faturamentoGrupo, 201304, 7047);
					break;

				case 8:

					System.out.println("*** Gerar resumo_faturamento_simulacao grupo 104 ");
					faturamentoGrupo = fachada.pesquisarFaturamentoGrupoPorID(104);
					fachada.gerarResumoFaturamentoSimulacaoAjusteCasal(faturamentoGrupo, 201304, 7371);
					break;

				case 9:

					System.out.println("*** Desfazer pr� - faturamento");
					fachada.desfazerPreFaturamentoPorGrupoERef();
					break;

				case 10:

					System.out.println("*** ajusteRemuneracaoDoLegadoCobrancaAdministrativaCASAL ");
					fachada.ajusteRemuneracaoDoLegadoCobrancaAdministrativaCASAL();

					break;

				case 11:

					System.out.println("*** executarCancelamentoDebitoACobrar ");
					fachada.executarCancelamentoDebitoACobrar();

					break;

				case 12:

					System.out.println("***CASO 1 : executarAjusteValorDebitoCobradoCasal [ VALOR CONTA  >  SUM [DEBITOS COBRADO] ");
					fachada.executarAjusteValorDebitoCobradoCasal(1);

					break;

				case 13:

					System.out.println("***CASO 2 : executarAjusteValorDebitoCobradoCasal [ VALOR CONTA_HISTORICO  >  SUM [DEBITOS COBRADO HISTORICO]");
					fachada.executarAjusteValorDebitoCobradoCasal(2);

					break;

				case 14:

					System.out.println("***CASO 3 : executarAjusteValorDebitoCobradoCasal [ VALOR CONTA_HISTORICO  <  SUM [DEBITOS COBRADO HISTORICO] ");
					fachada.executarAjusteValorDebitoCobradoCasal(3);

					break;

				case 15:

					System.out.println("***CASO 15: Ajuste Contas Pre-Faturadas Hidr�metro Instalado no Meio do Ciclo de Faturamento");

					String idListaGrupos = (String) executarBatchActionForm.get("listaGrupos");

					if(Util.isInteger(executarBatchActionForm.get("anoMesReferencia").toString())){

						Integer anoMesReferencia = Util.obterInteger(executarBatchActionForm.get("anoMesReferencia").toString());
						fachada.executarAjusteHidrometroInstaladoMeioCicloFaturamento(idListaGrupos, anoMesReferencia);
					}else{

						throw new ActionServletException("atencao.required", null, "Ano M�s Refer�ncia");
					}

					break;

				case 16:

					System.out.println("*** DESO 16 : cancelar pagamentos duplicados do BANESE (OC1180769) ");
					String idAvisoCorreto = (String) executarBatchActionForm.get("idAvisoCorreto");
					String idMovimentoDuplicado = (String) executarBatchActionForm.get("idMovimentoDuplicado");

					fachada.removerPagamentosAjusteDESO(idAvisoCorreto, idMovimentoDuplicado);

					break;

				case 17:

					System.out.println("*** CASAL 17 : Classificar pagamentos (fixos) com acr�scimo de Multa ");
					fachada.classificarPagamentosAjuste();

					break;

				case 18:

					Integer anoMesRefInicial = null;
					Integer anoMesRefFinal = null;
					String[] idsCliente = null;

					System.out.println("*** executarAjusteContasEnviadasHistorico");

					if(Util.isInteger(executarBatchActionForm.get("anoMesRefInicial").toString())){

						anoMesRefInicial = Util.obterInteger(executarBatchActionForm.get("anoMesRefInicial").toString());

					}else{

						throw new ActionServletException("atencao.required", null, "Ano M�s Refer�ncia Inicial");
					}

					if(Util.isInteger(executarBatchActionForm.get("anoMesRefFinal").toString())){

						anoMesRefFinal = Util.obterInteger(executarBatchActionForm.get("anoMesRefFinal").toString());

					}else{

						throw new ActionServletException("atencao.required", null, "Ano M�s Refer�ncia Final");
					}

					if(Util.isNaoNuloBrancoZero(executarBatchActionForm.get("idsCliente").toString())){

						String idsClienteString = (String) executarBatchActionForm.get("idsCliente");
						idsCliente = idsClienteString.split(",");

					}else{

						throw new ActionServletException("atencao.required", null, "Lista de clientes");
					}

					fachada.executarAjusteContasEnviadasHistorico(anoMesRefInicial, anoMesRefFinal, idsCliente);
					break;
				case 19:

					System.out.println("*** CAERD 19 : realiza o ajuste do faturamento para trazer de volta as contas que est�o em conta_historico para tabela de conta devido a um erro do faturamento ");
					fachada.executarAjusteContasEnviadasHistoricoPreFaturadasComValorZeroIndicadorEmissaoCampo3((String) executarBatchActionForm
									.get("listaGrupos"));

					break;

				case 20:

					String dadosAcquaGis = null;
					Collection colecaoDadosAcquaGis = new ArrayList();

					String caminhoArquivoLog = ConstantesAplicacao.get("caminho_pasta_gsan");
					File file = new File(caminhoArquivoLog + "DadosAcquaGisAjuste.txt");
					FileReader fileReader = null;

					try{
						fileReader = new FileReader(file);

						if(fileReader != null){
							BufferedReader br = null;
							br = new BufferedReader(fileReader);
							String linha = null;

							while((linha = br.readLine()) != null){
								if(!Util.isVazioOuBranco(linha)){
									dadosAcquaGis = linha;
									colecaoDadosAcquaGis.add(dadosAcquaGis);
								}else{
									break;
								}
							}
						}
					}catch(IOException e){
						e.printStackTrace();
					}

					if(Util.isVazioOrNulo(colecaoDadosAcquaGis)){

						System.out.println("xxxx  N�O LEU O ARQUIVO DE GRUPOS!!!!!!!!!!");
					}

					System.out.println("*** CAERD '20 : Popula a tabela de REGISTRO DE ATENDIMENTO COM AS COORDENADAS DO IM�VEL INFORMADAS PELO GIS ");
					fachada.executarAjusteCoordenadasGIS((Collection) colecaoDadosAcquaGis);

					break;
				case 21:

					System.out.println("***CASO 15: Ajuste Contas Pre-Faturadas Hidr�metro Instalado no Meio do Ciclo de Faturamento");

					String listaReferencias = (String) executarBatchActionForm.get("listaReferencias");

					Collection<Integer> colecaoReferencias = new ArrayList<Integer>();
					if(!Util.isVazioOuBranco(listaReferencias)){

						String[] referencias = listaReferencias.split(",");

						for(int i = 0; i < referencias.length; i++){

							colecaoReferencias.add(Util.obterInteger(referencias[i]));
						}
					}

					if(!Util.isVazioOrNulo(colecaoReferencias)){

						fachada.executarAjusteErroCalculoConsumoMedioPercentualColeta(colecaoReferencias);
					}else{

						throw new ActionServletException("atencao.required", null, "Lista de Refer�ncias");
					}

					break;

				case 22:

					System.out.println("***CASO 16: Regera��o Histograma");

					Integer anoMesRefIni = null;
					Integer anoMesRefFim = null;



					if(Util.isInteger(executarBatchActionForm.get("anoMesRefInicial").toString())){

						anoMesRefIni = Util.obterInteger(executarBatchActionForm.get("anoMesRefInicial").toString());

					}else{

						throw new ActionServletException("atencao.required", null, "Ano M�s Refer�ncia Inicial");
					}

					if(Util.isInteger(executarBatchActionForm.get("anoMesRefFinal").toString())){

						anoMesRefFim = Util.obterInteger(executarBatchActionForm.get("anoMesRefFinal").toString());

					}else{

						throw new ActionServletException("atencao.required", null, "Ano M�s Refer�ncia Final");
					}

					fachada.executarRegeracaoHistograma(anoMesRefIni, anoMesRefFim);

					break;

				case 23:

					System.out.println("***CASO 17: Gerar Debito A Cobrar Conta Com Valor A Menor");

					Integer referencia = null;
					String idsGrupos = null;

					if(Util.isInteger(executarBatchActionForm.get("anoMesReferencia").toString())){

						referencia = Util.obterInteger(executarBatchActionForm.get("anoMesReferencia").toString());

					}else{

						throw new ActionServletException("atencao.required", null, "Ano M�s Refer�ncia ");
					}

					if(executarBatchActionForm.get("listaGrupos") != null && !"".equals(executarBatchActionForm.get("listaGrupos"))){
						idsGrupos = executarBatchActionForm.get("listaGrupos").toString();
					}

					fachada.gerarDebitoACobrarContaComValorAMenor(referencia, idsGrupos);

					break;

				case 24:

					System.out.println("***CASO 24: Ajuste das Faixas das Contas que Faturarm com Vig�ncia Errada de Tarifa");

					String referenciaAjustefaixas = (String) executarBatchActionForm.get("anoMesReferencia");
					String idsGruposAjusteFaixas = (String) executarBatchActionForm.get("listaGrupos");

					if(!Util.isVazioOuBranco(idsGruposAjusteFaixas)){

						fachada.executarAjusteErroGeracaoContaCategoriaConsumoFaixa(Util.obterInteger(referenciaAjustefaixas),
										idsGruposAjusteFaixas);
					}else{

						throw new ActionServletException("atencao.required", null, "Lista de Grupos");
					}

					break;

				case 25:

					System.out.println("***CASO 25: Ajuste para enviar contas zeradas para hist�rico");

					String referenciaAjusteContaZerada = (String) executarBatchActionForm.get("anoMesReferencia");

					if(!Util.isVazioOuBranco(referenciaAjusteContaZerada)){

						fachada.executarAjusteContaZeradasEnviarHistorico(Util.obterInteger(referenciaAjusteContaZerada));
					}else{

						throw new ActionServletException("atencao.required", null, "Refer�ncia");
					}

					break;

				case 26:

					System.out.println("***CASO 26: Ajuste Resumo A��o de Cobran�a");

					fachada.ajusteResumoAcaoCobranca();

					break;
			}
		}



		// montando p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Rotina de Ajuste Enviada para Processamento em batch", "Voltar",
						"/exibirExecutarBatchAction.do");

		return retorno;
	}
}