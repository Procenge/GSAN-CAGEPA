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

package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.ContaRevisaoFaixaValor;
import gcom.faturamento.bean.ContasEmRevisaoRelatorioHelper;
import gcom.faturamento.conta.FiltroContaRevisaoFaixaValor;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ParametroGeral;

import java.math.BigDecimal;
import java.util.*;

/**
 * classe respons�vel por criar o relat�rio de volumes faturados
 * 
 * @author Rafael Corr�a
 * @created 12/09/2007
 */
public class RelatorioContasEmRevisao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioContasEmRevisao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTAS_EM_REVISAO);
	}

	@Deprecated
	public RelatorioContasEmRevisao() {

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

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer idGerenciaRegional = (Integer) getParametro("idGerenciaRegional");
		Integer idUnidadeNegocio = (Integer) getParametro("idUnidadeNegocio");
		Integer idElo = (Integer) getParametro("idElo");
		Integer idLocalidadeInicial = (Integer) getParametro("idLocalidadeInicial");
		Integer idLocalidadeFinal = (Integer) getParametro("idLocalidadeFinal");
		Integer idMotivoRevisao = (Integer) getParametro("idMotivoRevisao");
		Integer idImovelPerfil = (Integer) getParametro("idImovelPerfil");
		Integer referenciaInicial = (Integer) getParametro("referenciaInicial");
		Integer referenciaFinal = (Integer) getParametro("referenciaFinal");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioContasEmRevisaoBean relatorioBean = null;

		Collection colecaoContasEmRevisaoRelatorioHelper = fachada.pesquisarDadosRelatorioContasRevisao(idGerenciaRegional,
						idUnidadeNegocio, idElo, idLocalidadeInicial, idLocalidadeFinal, idMotivoRevisao, idImovelPerfil,
						referenciaInicial, referenciaFinal);

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoContasEmRevisaoRelatorioHelper != null && !colecaoContasEmRevisaoRelatorioHelper.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoContasEmRevisaoRelatorioHelperIterator = colecaoContasEmRevisaoRelatorioHelper.iterator();

			// Cria as vari�veis para verificar se os totalizadores de ger�ncia
			// e elo ser�o mostrados no relat�rio
			String imprimeElo = null;
			String imprimeGerenciaRegional = null;

			if(idGerenciaRegional != null){
				imprimeGerenciaRegional = "SIM";
				imprimeElo = "SIM";
			}else if(idElo != null){
				imprimeElo = "SIM";
			}

			FiltroContaRevisaoFaixaValor filtroContaRevisaoFaixaValor = new FiltroContaRevisaoFaixaValor(
							FiltroContaRevisaoFaixaValor.VALOR_INICIO_FAIXA);

			Collection colecaoContaRevisaoFaixaValor = fachada.pesquisar(filtroContaRevisaoFaixaValor, ContaRevisaoFaixaValor.class
							.getName());

			// Cria as vari�veis de totaliza��o
			Integer qtdeContasTotalLocalidade = new Integer("0");
			BigDecimal valorContasTotalLocalidade = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa1Localidade = new Integer("0");
			BigDecimal valorContasTotalFaixa1Localidade = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa2Localidade = new Integer("0");
			BigDecimal valorContasTotalFaixa2Localidade = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa3Localidade = new Integer("0");
			BigDecimal valorContasTotalFaixa3Localidade = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa4Localidade = new Integer("0");
			BigDecimal valorContasTotalFaixa4Localidade = new BigDecimal("0.00");

			Integer qtdeContasTotalElo = new Integer("0");
			BigDecimal valorContasTotalElo = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa1Elo = new Integer("0");
			BigDecimal valorContasTotalFaixa1Elo = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa2Elo = new Integer("0");
			BigDecimal valorContasTotalFaixa2Elo = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa3Elo = new Integer("0");
			BigDecimal valorContasTotalFaixa3Elo = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa4Elo = new Integer("0");
			BigDecimal valorContasTotalFaixa4Elo = new BigDecimal("0.00");

			Integer qtdeContasTotalGerenciaRegional = new Integer("0");
			BigDecimal valorContasTotalGerenciaRegional = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa1GerenciaRegional = new Integer("0");
			BigDecimal valorContasTotalFaixa1GerenciaRegional = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa2GerenciaRegional = new Integer("0");
			BigDecimal valorContasTotalFaixa2GerenciaRegional = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa3GerenciaRegional = new Integer("0");
			BigDecimal valorContasTotalFaixa3GerenciaRegional = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa4GerenciaRegional = new Integer("0");
			BigDecimal valorContasTotalFaixa4GerenciaRegional = new BigDecimal("0.00");

			Integer qtdeContasTotalGeral = new Integer("0");
			BigDecimal valorContasTotalGeral = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa1Geral = new Integer("0");
			BigDecimal valorContasTotalFaixa1Geral = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa2Geral = new Integer("0");
			BigDecimal valorContasTotalFaixa2Geral = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa3Geral = new Integer("0");
			BigDecimal valorContasTotalFaixa3Geral = new BigDecimal("0.00");
			Integer qtdeContasTotalFaixa4Geral = new Integer("0");
			BigDecimal valorContasTotalFaixa4Geral = new BigDecimal("0.00");

			Integer idLocalidadeAnterior = null;
			Integer idEloAnterior = null;
			Integer idGerenciaRegionalAnterior = null;
			boolean zerarLocalidade = false;
			boolean zerarElo = false;
			boolean zerarGerenciaRegional = false;

			Hashtable<String, Integer> htContadorMotivoRevisao = new Hashtable<String, Integer>();
			String dsMotivoRevisao = "";
			int qtMotivoRevisao = 0;
			String totalizadorPorMotivoRevisao = "";

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoContasEmRevisaoRelatorioHelperIterator.hasNext()){

				ContasEmRevisaoRelatorioHelper contasEmRevisaoRelatorioHelper = (ContasEmRevisaoRelatorioHelper) colecaoContasEmRevisaoRelatorioHelperIterator
								.next();

				// Seta os valores das vari�veis de controle de totaliza��o para
				// verificar quando deve ser zerado os totalizadores
				if(idLocalidadeAnterior == null){
					idLocalidadeAnterior = contasEmRevisaoRelatorioHelper.getIdLocalidade();
				}

				if(idEloAnterior == null){
					idEloAnterior = contasEmRevisaoRelatorioHelper.getIdElo();
				}

				if(idGerenciaRegionalAnterior == null){
					idGerenciaRegionalAnterior = contasEmRevisaoRelatorioHelper.getIdGerenciaRegional();
				}

				// Faz as valida��es dos campos necess�riose e formata a String
				// para a forma como ir� aparecer no relat�rio

				// Ger�ncia Regional
				String gerenciaRegional = "";

				if(contasEmRevisaoRelatorioHelper.getIdGerenciaRegional() != null){
					gerenciaRegional = contasEmRevisaoRelatorioHelper.getIdGerenciaRegional() + " - "
									+ contasEmRevisaoRelatorioHelper.getNomeGerenciaRegional();

					// Caso tenha mudado a ger�ncia regional do im�vel seta a
					// vari�vel para true, para posteriormente zerar todas as
					// vari�veis de totaliza��o da ger�ncia regional
					if(!idGerenciaRegionalAnterior.equals(contasEmRevisaoRelatorioHelper.getIdGerenciaRegional())){
						zerarGerenciaRegional = true;
					}
				}

				// Unidade de Neg�cio
				String unidadeNegocio = "";

				if(contasEmRevisaoRelatorioHelper.getIdUnidadeNegocio() != null){
					unidadeNegocio = contasEmRevisaoRelatorioHelper.getIdUnidadeNegocio() + " - "
									+ contasEmRevisaoRelatorioHelper.getNomeUnidadeNegocio();
				}

				// Elo
				String elo = "";

				if(contasEmRevisaoRelatorioHelper.getIdElo() != null){
					elo = contasEmRevisaoRelatorioHelper.getIdElo() + " - " + contasEmRevisaoRelatorioHelper.getNomeElo();

					// Caso tenha mudado o elo do im�vel seta a vari�vel para
					// true, para posteriormente zerar todas as vari�veis de
					// totaliza��o do elo
					if(!idEloAnterior.equals(contasEmRevisaoRelatorioHelper.getIdElo())){
						zerarElo = true;
					}
				}

				// Localidade
				String localidade = "";

				if(contasEmRevisaoRelatorioHelper.getIdLocalidade() != null){
					localidade = contasEmRevisaoRelatorioHelper.getIdLocalidade() + " - "
									+ contasEmRevisaoRelatorioHelper.getNomeLocalidade();

					// Caso tenha mudado a localidade do im�vel seta a vari�vel
					// para true, para posteriormente zerar todas as vari�veis
					// de totaliza��o da localidade
					if(!idLocalidadeAnterior.equals(contasEmRevisaoRelatorioHelper.getIdLocalidade())){
						zerarLocalidade = true;
					}
				}

				// Zera os totalizadores da localidade
				if(zerarLocalidade){
					qtdeContasTotalLocalidade = new Integer("0");
					valorContasTotalLocalidade = new BigDecimal("0.00");
					qtdeContasTotalFaixa1Localidade = new Integer("0");
					valorContasTotalFaixa1Localidade = new BigDecimal("0.00");
					qtdeContasTotalFaixa2Localidade = new Integer("0");
					valorContasTotalFaixa2Localidade = new BigDecimal("0.00");
					qtdeContasTotalFaixa3Localidade = new Integer("0");
					valorContasTotalFaixa3Localidade = new BigDecimal("0.00");
					qtdeContasTotalFaixa4Localidade = new Integer("0");
					valorContasTotalFaixa4Localidade = new BigDecimal("0.00");

					zerarLocalidade = false;
					idLocalidadeAnterior = contasEmRevisaoRelatorioHelper.getIdLocalidade();
				}

				// Zera os totalizadores do elo
				if(zerarElo){
					qtdeContasTotalElo = new Integer("0");
					valorContasTotalElo = new BigDecimal("0.00");
					qtdeContasTotalFaixa1Elo = new Integer("0");
					valorContasTotalFaixa1Elo = new BigDecimal("0.00");
					qtdeContasTotalFaixa2Elo = new Integer("0");
					valorContasTotalFaixa2Elo = new BigDecimal("0.00");
					qtdeContasTotalFaixa3Elo = new Integer("0");
					valorContasTotalFaixa3Elo = new BigDecimal("0.00");
					qtdeContasTotalFaixa4Elo = new Integer("0");
					valorContasTotalFaixa4Elo = new BigDecimal("0.00");

					zerarElo = false;
					idEloAnterior = contasEmRevisaoRelatorioHelper.getIdElo();
				}

				// Zera os totalizadores da ger�ncia regional
				if(zerarGerenciaRegional){
					qtdeContasTotalGerenciaRegional = new Integer("0");
					valorContasTotalGerenciaRegional = new BigDecimal("0.00");
					qtdeContasTotalFaixa1GerenciaRegional = new Integer("0");
					valorContasTotalFaixa1GerenciaRegional = new BigDecimal("0.00");
					qtdeContasTotalFaixa2GerenciaRegional = new Integer("0");
					valorContasTotalFaixa2GerenciaRegional = new BigDecimal("0.00");
					qtdeContasTotalFaixa3GerenciaRegional = new Integer("0");
					valorContasTotalFaixa3GerenciaRegional = new BigDecimal("0.00");
					qtdeContasTotalFaixa4GerenciaRegional = new Integer("0");
					valorContasTotalFaixa4GerenciaRegional = new BigDecimal("0.00");

					zerarGerenciaRegional = false;
					idGerenciaRegionalAnterior = contasEmRevisaoRelatorioHelper.getIdGerenciaRegional();
				}

				// Inscri��o
				String inscricao = contasEmRevisaoRelatorioHelper.getInscricaoFormatada();

				// Im�vel
				String idImovel = "";

				if(contasEmRevisaoRelatorioHelper.getIdImovel() != null){
					idImovel = contasEmRevisaoRelatorioHelper.getIdImovel().toString();
				}

				// Nome do Usu�rio
				String nomeUsuario = "";

				if(contasEmRevisaoRelatorioHelper.getNomeUsuario() != null){
					nomeUsuario = contasEmRevisaoRelatorioHelper.getNomeUsuario();
				}

				// Telefone
				String telefone = "";

				if(contasEmRevisaoRelatorioHelper.getTelefone() != null && !contasEmRevisaoRelatorioHelper.getTelefone().trim().equals("")){
					telefone = contasEmRevisaoRelatorioHelper.getDddTelefone();
				}

				// M�s/Ano da Fatura
				String mesAnoFatura = "";

				if(contasEmRevisaoRelatorioHelper.getAnoMesReferenciaConta() != null){
					mesAnoFatura = Util.formatarMesAnoReferencia(contasEmRevisaoRelatorioHelper.getAnoMesReferenciaConta());
				}

				// Data da Reclama��o
				String dataReclamacao = "";

				if(contasEmRevisaoRelatorioHelper.getDataRevisao() != null){
					dataReclamacao = Util.formatarData(contasEmRevisaoRelatorioHelper.getDataRevisao());
				}

				// Valor da Conta
				String valorConta = "";

				if(contasEmRevisaoRelatorioHelper.getValorConta() != null){
					valorConta = Util.formatarMoedaReal(contasEmRevisaoRelatorioHelper.getValorConta());

					// Soma os valores aos totalizadores de cada grupo
					qtdeContasTotalLocalidade = qtdeContasTotalLocalidade + 1;
					valorContasTotalLocalidade = valorContasTotalLocalidade.add(contasEmRevisaoRelatorioHelper.getValorConta());

					qtdeContasTotalElo = qtdeContasTotalElo + 1;
					valorContasTotalElo = valorContasTotalElo.add(contasEmRevisaoRelatorioHelper.getValorConta());

					qtdeContasTotalGerenciaRegional = qtdeContasTotalGerenciaRegional + 1;
					valorContasTotalGerenciaRegional = valorContasTotalGerenciaRegional.add(contasEmRevisaoRelatorioHelper.getValorConta());

					qtdeContasTotalGeral = qtdeContasTotalGeral + 1;
					valorContasTotalGeral = valorContasTotalGeral.add(contasEmRevisaoRelatorioHelper.getValorConta());

				}

				// Motivo da Reclama��o
				String motivoReclamacao = "";

				if(contasEmRevisaoRelatorioHelper.getIdMotivoRevisao() != null){
					motivoReclamacao = contasEmRevisaoRelatorioHelper.getIdMotivoRevisao() + "-"
									+ contasEmRevisaoRelatorioHelper.getDescricaoMotivoRevisao();

					dsMotivoRevisao = contasEmRevisaoRelatorioHelper.getDescricaoMotivoRevisao();

					if(htContadorMotivoRevisao.containsKey(dsMotivoRevisao)){
						qtMotivoRevisao = htContadorMotivoRevisao.get(dsMotivoRevisao);
						htContadorMotivoRevisao.put(dsMotivoRevisao, ++qtMotivoRevisao);
					}else{
						htContadorMotivoRevisao.put(dsMotivoRevisao, 1);
					}
				}

				Enumeration<String> enumeration = htContadorMotivoRevisao.keys();
				totalizadorPorMotivoRevisao = "";
				while(enumeration.hasMoreElements()){
					String keyDsMotivoRevisao = enumeration.nextElement();

					totalizadorPorMotivoRevisao = totalizadorPorMotivoRevisao + htContadorMotivoRevisao.get(keyDsMotivoRevisao)
									+ " conta(s) com motivo de revis�o: " + keyDsMotivoRevisao + "\n";
				}

				// Total Faixas
				String totalFaixasLocalidade = "";
				String totalFaixasElo = "";
				String totalFaixasGerenciaRegional = "";
				String totalFaixasGeral = "";

				if(colecaoContaRevisaoFaixaValor != null && !colecaoContaRevisaoFaixaValor.isEmpty()){
					Iterator colecaoContaRevisaoFaixaValorIterator = colecaoContaRevisaoFaixaValor.iterator();
					int i = 1;

					while(colecaoContaRevisaoFaixaValorIterator.hasNext()){

						ContaRevisaoFaixaValor contaRevisaoFaixaValor = (ContaRevisaoFaixaValor) colecaoContaRevisaoFaixaValorIterator
										.next();

						// Se for a primeira faixa
						if(i == 1){

							// Verifica se o valor da conta est� no intervalo da
							// faixa
							if(contasEmRevisaoRelatorioHelper.getValorConta() != null
											&& contaRevisaoFaixaValor.getValorFaixaInicio().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) <= 0
											&& contaRevisaoFaixaValor.getValorFaixaFim().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) >= 0){

								qtdeContasTotalFaixa1Localidade = qtdeContasTotalFaixa1Localidade + 1;
								valorContasTotalFaixa1Localidade = valorContasTotalFaixa1Localidade.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

								qtdeContasTotalFaixa1Elo = qtdeContasTotalFaixa1Elo + 1;
								valorContasTotalFaixa1Elo = valorContasTotalFaixa1Elo.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa1GerenciaRegional = qtdeContasTotalFaixa1GerenciaRegional + 1;
								valorContasTotalFaixa1GerenciaRegional = valorContasTotalFaixa1GerenciaRegional
												.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa1Geral = qtdeContasTotalFaixa1Geral + 1;
								valorContasTotalFaixa1Geral = valorContasTotalFaixa1Geral.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

							}

							// Localidade
							if(qtdeContasTotalFaixa1Localidade != null){
								totalFaixasLocalidade = totalFaixasLocalidade + "  "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa1Localidade.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa1Localidade);
							}
							// Elo
							if(qtdeContasTotalFaixa1Elo != null){
								totalFaixasElo = totalFaixasElo + "  "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa1Elo.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa1Elo);
							}
							// Ger�ncia Regional
							if(qtdeContasTotalFaixa1GerenciaRegional != null){
								totalFaixasGerenciaRegional = totalFaixasGerenciaRegional + "  "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa1GerenciaRegional.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa1GerenciaRegional);
							}
							// Geral
							if(qtdeContasTotalFaixa1Geral != null){
								totalFaixasGeral = totalFaixasGeral + "  "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa1Geral.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa1Geral);
							}
						}
						// Se for a segunda faixa
						else if(i == 2){

							// Verifica se o valor da conta est� no intervalo da faixa
							if(contasEmRevisaoRelatorioHelper.getValorConta() != null
											&& contaRevisaoFaixaValor.getValorFaixaInicio().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) <= 0
											&& contaRevisaoFaixaValor.getValorFaixaFim().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) >= 0){

								qtdeContasTotalFaixa2Localidade = qtdeContasTotalFaixa2Localidade + 1;
								valorContasTotalFaixa2Localidade = valorContasTotalFaixa2Localidade.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

								qtdeContasTotalFaixa2Elo = qtdeContasTotalFaixa2Elo + 1;
								valorContasTotalFaixa2Elo = valorContasTotalFaixa2Elo.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa2GerenciaRegional = qtdeContasTotalFaixa2GerenciaRegional + 1;
								valorContasTotalFaixa2GerenciaRegional = valorContasTotalFaixa2GerenciaRegional
												.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa2Geral = qtdeContasTotalFaixa2Geral + 1;
								valorContasTotalFaixa2Geral = valorContasTotalFaixa2Geral.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

							}

							// Localidade
							if(qtdeContasTotalFaixa2Localidade != null){
								totalFaixasLocalidade = totalFaixasLocalidade + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa2Localidade.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa2Localidade);
							}
							// Elo
							if(qtdeContasTotalFaixa2Elo != null){
								totalFaixasElo = totalFaixasElo + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa2Elo.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa2Elo);
							}
							// Ger�ncia Regional
							if(qtdeContasTotalFaixa2GerenciaRegional != null){
								totalFaixasGerenciaRegional = totalFaixasGerenciaRegional + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa2GerenciaRegional.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa2GerenciaRegional);
							}
							// Geral
							if(qtdeContasTotalFaixa2Geral != null){
								totalFaixasGeral = totalFaixasGeral + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa2Geral.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa2Geral);
							}
						}
						// Se for a terceira faixa
						else if(i == 3){

							// Verifica se o valor da conta est� no intervalo da faixa
							if(contasEmRevisaoRelatorioHelper.getValorConta() != null
											&& contaRevisaoFaixaValor.getValorFaixaInicio().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) <= 0
											&& contaRevisaoFaixaValor.getValorFaixaFim().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) >= 0){

								qtdeContasTotalFaixa3Localidade = qtdeContasTotalFaixa3Localidade + 1;
								valorContasTotalFaixa3Localidade = valorContasTotalFaixa3Localidade.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

								qtdeContasTotalFaixa3Elo = qtdeContasTotalFaixa3Elo + 1;
								valorContasTotalFaixa3Elo = valorContasTotalFaixa3Elo.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa3GerenciaRegional = qtdeContasTotalFaixa3GerenciaRegional + 1;
								valorContasTotalFaixa3GerenciaRegional = valorContasTotalFaixa3GerenciaRegional
												.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa3Geral = qtdeContasTotalFaixa3Geral + 1;
								valorContasTotalFaixa3Geral = valorContasTotalFaixa3Geral.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

							}

							// Localidade
							if(qtdeContasTotalFaixa3Localidade != null){
								totalFaixasLocalidade = totalFaixasLocalidade + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa3Localidade.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa3Localidade);
							}
							// Elo
							if(qtdeContasTotalFaixa3Elo != null){
								totalFaixasElo = totalFaixasElo + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa3Elo.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa3Elo);
							}
							// Ger�ncia Regional
							if(qtdeContasTotalFaixa3GerenciaRegional != null){
								totalFaixasGerenciaRegional = totalFaixasGerenciaRegional + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa3GerenciaRegional.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa3GerenciaRegional);
							}
							// Geral
							if(qtdeContasTotalFaixa3Geral != null){
								totalFaixasGeral = totalFaixasGeral + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A       "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa3Geral.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa3Geral);
							}
						}
						// Se for a quarta faixa
						else if(i == 4){

							// Verifica se o valor da conta est� no intervalo da faixa
							if(contasEmRevisaoRelatorioHelper.getValorConta() != null
											&& contaRevisaoFaixaValor.getValorFaixaInicio().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) <= 0
											&& contaRevisaoFaixaValor.getValorFaixaFim().compareTo(
															contasEmRevisaoRelatorioHelper.getValorConta()) >= 0){

								qtdeContasTotalFaixa4Localidade = qtdeContasTotalFaixa4Localidade + 1;
								valorContasTotalFaixa4Localidade = valorContasTotalFaixa4Localidade.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

								qtdeContasTotalFaixa4Elo = qtdeContasTotalFaixa4Elo + 1;
								valorContasTotalFaixa4Elo = valorContasTotalFaixa4Elo.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa4GerenciaRegional = qtdeContasTotalFaixa4GerenciaRegional + 1;
								valorContasTotalFaixa4GerenciaRegional = valorContasTotalFaixa4GerenciaRegional
												.add(contasEmRevisaoRelatorioHelper.getValorConta());

								qtdeContasTotalFaixa4Geral = qtdeContasTotalFaixa4Geral + 1;
								valorContasTotalFaixa4Geral = valorContasTotalFaixa4Geral.add(contasEmRevisaoRelatorioHelper
												.getValorConta());

							}

							// Localidade
							if(qtdeContasTotalFaixa4Localidade != null){
								totalFaixasLocalidade = totalFaixasLocalidade + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa4Localidade.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa4Localidade);
							}
							// Elo
							if(qtdeContasTotalFaixa4Elo != null){
								totalFaixasElo = totalFaixasElo + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa4Elo.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa4Elo);
							}
							// Ger�ncia Regional
							if(qtdeContasTotalFaixa4GerenciaRegional != null){
								totalFaixasGerenciaRegional = totalFaixasGerenciaRegional + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa4GerenciaRegional.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa4GerenciaRegional);
							}
							// Geral
							if(qtdeContasTotalFaixa4Geral != null){
								totalFaixasGeral = totalFaixasGeral + " \n"
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaInicio()) + " A "
												+ Util.formatarMoedaReal(contaRevisaoFaixaValor.getValorFaixaFim()) + "    CONTAS "
												+ qtdeContasTotalFaixa4Geral.toString() + "    VALOR  "
												+ Util.formatarMoedaReal(valorContasTotalFaixa4Geral);
							}
						}else{
							break;
						}

						i++;

					}
				}

				String idPerfilImovel = "";
				String descricaoPerfilImovel = "";

				if(contasEmRevisaoRelatorioHelper.getIdPerfilImovel() != null){
					idPerfilImovel = contasEmRevisaoRelatorioHelper.getIdPerfilImovel();
				}
				if(contasEmRevisaoRelatorioHelper.getDescricaoPerfilImovel() != null){
					descricaoPerfilImovel = contasEmRevisaoRelatorioHelper.getDescricaoPerfilImovel();
				}

				relatorioBean = new RelatorioContasEmRevisaoBean(

				// Ger�ncia Regional
								gerenciaRegional,

								// Elo
								elo,

								// Localidade
								localidade,

								// Inscri��o
								inscricao,

								// Im�vel
								idImovel,

								// Usu�rio
								nomeUsuario,

								// Telefone
								telefone,

								// M�s/Ano Fatura
								mesAnoFatura,

								// Data da Reclama��o
								dataReclamacao,

								// Valor da Conta
								valorConta,

								// Motivo da Reclama��o
								motivoReclamacao,

								// Totalizadores de Localidade
								// Quantidade Total de Contas na Localidade
								qtdeContasTotalLocalidade.toString(),

								// Valor Total de Contas na Localidade
								Util.formatarMoedaReal(valorContasTotalLocalidade),

								// Totais por Faixa de Localidade
								totalFaixasLocalidade,

								// Totalizadores de Elo
								// Quantidade Total de Contas no Elo
								qtdeContasTotalElo.toString(),

								// Valor Total de Contas no Elo
								Util.formatarMoedaReal(valorContasTotalElo),

								// Totais por Faixa de Elo
								totalFaixasElo,

								// Totalizadores de Ger�ncia Regional
								// Quantidade Total de Contas na Ger�ncia Regional
								qtdeContasTotalGerenciaRegional.toString(),

								// Valor Total de Contas na Ger�ncia Regional
								Util.formatarMoedaReal(valorContasTotalGerenciaRegional),

								// Totais por Faixa de Ger�ncia Regional
								totalFaixasGerenciaRegional,

								// Totalizadores Gerais
								// Quantidade Total de Contas Geral
								qtdeContasTotalGeral.toString(),

								// Valor Total de Contas Geral
								Util.formatarMoedaReal(valorContasTotalGeral),

								// Totais por Faixa de Geral
								totalFaixasGeral,

								// Imprime Elo
								imprimeElo,

								// Imprime Ger�ncia Regional
								imprimeGerenciaRegional,

								// Unidade de Neg�cio
								unidadeNegocio,
								// Quantidade Motivo
								totalizadorPorMotivoRevisao,

								// id do perfil do Imovel
								idPerfilImovel,

								// descri��o do Perfil do im�vel
								descricaoPerfilImovel,

								// existe pagamento
								contasEmRevisaoRelatorioHelper.getExistePagamento());

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);

			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("referenciaInicial", Util.formatarAnoMesParaMesAno(referenciaInicial));
		parametros.put("referenciaFinal", Util.formatarAnoMesParaMesAno(referenciaFinal));

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		try{
			parametros.put("P_NOME_EMPRESA_RELATORIO", ParametroGeral.P_NOME_EMPRESA_RELATORIO.executar());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e1);
		}

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTAS_EM_REVISAO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.CONTAS_EM_REVISAO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContasEmRevisao", this);
	}
}