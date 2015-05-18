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

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CalcularValoresRetificarContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirRetificarConta");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// Instància do formulário que está sen`o utilizado
		RetificarContaActionForm retificarContaActionForm = (RetificarContaActionForm) actionForm;

		Conta contaAtual = (Conta) sessao.getAttribute("contaRetificar");
		if(contaAtual == null){
			throw new ActionServletException("atencao.conta_retificacao_nao_existente_sessao");
		}

		// Recebendo os dados enviados pelo formUlário
		String imovelID = retificarContaActionForm.getIdImovel();
		String mesAnoConta = retificarContaActionForm.getMesAnoConta();
		Integer situacaoAguaConta = new Integer(retificarContaActionForm.getSituacaoAguaConta());
		Integer situacaoEsgotoConta = new Integer(retificarContaActionForm.getSituacaoEsgotoConta());
		String consumoAgua = retificarContaActionForm.getConsumoAgua();
		String consumoEsgoto = retificarContaActionForm.getConsumoEsgoto();
		String percentualEsgoto = retificarContaActionForm.getPercentualEsgoto();
		String motivoRetificacao = retificarContaActionForm.getMotivoRetificacaoID();
		Date dataLeituraAtual = null;
		Date dataLeituraAnterior = null;
		// Data de vencimento da conta
		Date dataVencimentoConta = Util.converteStringParaDate(retificarContaActionForm.getVencimentoConta());
		Date dataVencimentoContaAnterior = Util.converteStringParaDate(retificarContaActionForm.getVencimentoContaAnterior());

		if(Util.compararData(dataVencimentoConta, dataVencimentoContaAnterior) != 0){
			if(!retificarContaActionForm.getVencimentoConta().equals("") && retificarContaActionForm.getVencimentoConta() != null){
				if(!getFachada().verificarPermissaoEspecial(
								PermissaoEspecial.RETIFICAR_DATA_VENCIMENTO_ANTERIOR_OU_POSTERIOR_DATA_CORRENTE, usuario)){

					Calendar dataCorrente = new GregorianCalendar();
					if(Util.compararData(dataVencimentoConta, dataCorrente.getTime()) == -1){
						throw new ActionServletException("atencao.data_vencimento_menor_data_corrente");
					}

					Integer qtdDiasVencimentoConta = null;
					try{
						qtdDiasVencimentoConta = Util
										.converterStringParaInteger((String) ParametroFaturamento.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA
														.executar());
					}catch(ControladorException e){
						throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(
										new String[e.getParametroMensagem().size()]));
					}
					dataCorrente.add(Calendar.DATE, qtdDiasVencimentoConta.intValue());

					if(Util.compararData(dataVencimentoConta, dataCorrente.getTime()) == 1){
						throw new ActionServletException("atencao.data_vencimento_maior_data_corrente_parametro", qtdDiasVencimentoConta
										.toString());
					}
				}
			}
		}

		if(!Util.isVazioOuBranco(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua())){
			dataLeituraAtual = Util.converteStringParaDate(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua());
		}
		if(!Util.isVazioOuBranco(retificarContaActionForm.getDataLeituraAnteriorMedicaoHistoricoAgua())){
			dataLeituraAnterior = Util.converteStringParaDate(retificarContaActionForm.getDataLeituraAnteriorMedicaoHistoricoAgua());
		}

		// Carrega as coleções de acordo com os Objetos da sessão
		Collection colecaoDebitoCobrado = null;
		if(sessao.getAttribute("colecaoDebitoCobrado") != null){
			colecaoDebitoCobrado = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
		}

		Collection colecaoCreditoRealizado = new ArrayList();
		if(sessao.getAttribute("colecaoCreditoRealizado") != null){
			colecaoCreditoRealizado = (Collection) sessao.getAttribute("colecaoCreditoRealizado");
		}

		// Alterado por Raphael Rossiter em 17/04/2007
		Collection colecaoCategoriaOUSubcategoria = null;

		// Criação do consumo tarifa
		Imovel imovel = new Imovel();

		ConsumoTarifa consumoTarifa = null;
		Integer idConsumoTarifa = null;

		String idConsumoTarifaStr = retificarContaActionForm.getConsumoTarifaId();

		if(!Util.isVazioOuBranco(idConsumoTarifaStr)
						&& !idConsumoTarifaStr.equals(Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			idConsumoTarifa = Integer.valueOf(idConsumoTarifaStr);

			consumoTarifa = new ConsumoTarifa();
			consumoTarifa.setId(idConsumoTarifa);
		}

		imovel.setConsumoTarifa(consumoTarifa);

		Integer qtdEconnomia = null;
		int consumoMinimoLigacao = 0;

		if(sessao.getAttribute("colecaoCategoria") != null){

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoCategoria");
			qtdEconnomia = this.atualizarQuantidadeEconomiasCategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

			// Obtém o consumo mínimo ligação por categoria
			consumoMinimoLigacao = fachada.obterConsumoMinimoLigacaoPeriodo(imovel, colecaoCategoriaOUSubcategoria, contaAtual
							.getReferenciaFormatada(), idConsumoTarifa);
		}else{

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoSubcategoria");
			qtdEconnomia = this.atualizarQuantidadeEconomiasSubcategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

			// Obtém o consumo mínimo ligação por categoria
			consumoMinimoLigacao = fachada.obterConsumoMinimoLigacaoPeriodo(imovel, colecaoCategoriaOUSubcategoria, contaAtual
							.getReferenciaFormatada(), idConsumoTarifa);
		}

		// SB0010 – Ajuste do Consumo para Múltiplo da Quantidade de Economias
		Integer consumoAguaAux = null;
		if(consumoAgua != null){
			consumoAguaAux = Integer.parseInt(consumoAgua);
		}

		Integer consumoEsgotoAux = null;
		if(consumoEsgoto != null && !consumoEsgoto.equals("")){
			consumoEsgotoAux = Integer.parseInt(consumoEsgoto);
		}

		Map<String, String> consumoFaturadoAguaEsgoto = fachada.ajusteConsumoMultiploQuantidadeEconomia(consumoAguaAux,
						consumoMinimoLigacao, consumoEsgotoAux, qtdEconnomia);

		if(consumoFaturadoAguaEsgoto != null && !consumoFaturadoAguaEsgoto.isEmpty()){
			if(consumoFaturadoAguaEsgoto.get("agua") != null){
				consumoAgua = consumoFaturadoAguaEsgoto.get("agua");
			}

			if(consumoFaturadoAguaEsgoto.get("esgoto") != null){
				consumoEsgoto = consumoFaturadoAguaEsgoto.get("esgoto");
			}
		}

		// [SF0001] - Determinar Valores para Faturamento de Água e/ou Esgoto
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		Collection<CalcularValoresAguaEsgotoHelper> valoresConta = fachada.calcularValoresConta(mesAnoConta, imovelID, situacaoAguaConta,
						situacaoEsgotoConta, colecaoCategoriaOUSubcategoria, consumoAgua, consumoEsgoto, percentualEsgoto, idConsumoTarifa,
						usuarioLogado, dataLeituraAnterior, dataLeituraAtual);

		// Cálcula o valor total dos débitos de uma conta de acordo com o informado pelo usuário
		BigDecimal valorTotalDebitosConta = fachada.calcularValorTotalDebitoConta(colecaoDebitoCobrado, httpServletRequest
						.getParameterMap());

		// Cálcula o valor total dos créditos de uma conta de acordo com o informado pelo usuário
		BigDecimal valorTotalCreditosConta = fachada.calcularValorTotalCreditoConta(colecaoCreditoRealizado, httpServletRequest
						.getParameterMap());

		// Totalizando os valores de água e esgoto
		BigDecimal valorTotalAgua = new BigDecimal("0.00");
		BigDecimal valorTotalEsgoto = new BigDecimal("0.00");

		if(valoresConta != null && !valoresConta.isEmpty()){

			Iterator valoresContaIt = valoresConta.iterator();
			CalcularValoresAguaEsgotoHelper valoresContaObjeto = null;

			while(valoresContaIt.hasNext()){

				valoresContaObjeto = (CalcularValoresAguaEsgotoHelper) valoresContaIt.next();

				// Valor Faturado de Água
				if(valoresContaObjeto.getValorFaturadoAguaCategoria() != null){
					valorTotalAgua = valorTotalAgua.add(valoresContaObjeto.getValorFaturadoAguaCategoria());
				}

				// Valor Faturado de Esgoto
				if(valoresContaObjeto.getValorFaturadoEsgotoCategoria() != null){
					valorTotalEsgoto = valorTotalEsgoto.add(valoresContaObjeto.getValorFaturadoEsgotoCategoria());
				}

			}

		}

		BigDecimal valorTotalConta = new BigDecimal("0.00");

		valorTotalConta = valorTotalConta.add(valorTotalAgua);
		valorTotalConta = valorTotalConta.add(valorTotalEsgoto);
		valorTotalConta = valorTotalConta.add(valorTotalDebitosConta);

		valorTotalConta = valorTotalConta.subtract(valorTotalCreditosConta);

		if(valoresConta != null){
			// Consumo de Esgoto
			Integer consumoAgua2 = fachada.calcularConsumoTotalAguaOuEsgotoPorCategoria(valoresConta, ConstantesSistema.CALCULAR_AGUA);
			if(consumoAgua2 != null && consumoAgua2 > 0){
				retificarContaActionForm.setConsumoAgua(consumoAgua2.toString());
			}
			Integer consumoEsgoto2 = fachada.calcularConsumoTotalAguaOuEsgotoPorCategoria(valoresConta, ConstantesSistema.CALCULAR_ESGOTO);
			if(consumoEsgoto2 != null && consumoEsgoto2 > 0){
				retificarContaActionForm.setConsumoEsgoto(consumoEsgoto2.toString());
			}
		}

		// [UC0146][FS0025] – Validar leitura de faturamento
		try{

			if(ParametroFaturamento.P_MOTIVO_RETIFICACAO_OCORRENCIA_LEITURA.executar().equals(motivoRetificacao)){

				Integer consumoAguaFaturada = 0;
				Integer leituraFaturamento = 0;

				if(sessao.getAttribute("consumoAguaFaturada") != null){
					consumoAguaFaturada = new Integer(sessao.getAttribute("consumoAguaFaturada").toString());
				}

				if(sessao.getAttribute("leituraFaturamento") != null){
					leituraFaturamento = new Integer(sessao.getAttribute("leituraFaturamento").toString());
				}

				leituraFaturamento = leituraFaturamento - consumoAguaFaturada + consumoAguaAux;
				retificarContaActionForm.setNumeroLeituraAtualMedicaoHistoricoAgua(leituraFaturamento.toString());

			}

		}catch(ControladorException e){
			throw new ActionServletException("erro.parametro.nao.informado", "P_MOTIVO_RETIFICACAO_OCORRENCIA_LEITURA");
		}


		// // [FS0008] - Verificar valor da conta igual a zero
		// if(valorTotalConta.equals(new BigDecimal("0.00"))
		// && (valorTotalCreditosConta == null || valorTotalCreditosConta.equals(new
		// BigDecimal("0.00")))){
		// throw new ActionServletException("atencao.valor_conta_igual_zero");
		// }else if(valorTotalConta.compareTo(new BigDecimal("0.00")) == -1){
		// throw new ActionServletException("atencao.valor_conta_negativo");
		// }

		// Arredondando os valores obtidos para duas casas decimais
		valorTotalAgua.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalEsgoto.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalDebitosConta.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalCreditosConta.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalConta.setScale(2, BigDecimal.ROUND_HALF_UP);

		Collection colecaoAnteriorCreditosRealizados = new ArrayList();
		colecaoAnteriorCreditosRealizados.addAll(colecaoCreditoRealizado);

		String pAcumularConsumoEsgotoPoco = null;
		try{

			pAcumularConsumoEsgotoPoco = ParametroMicromedicao.P_ACUMULA_CONSUMO_ESGOTO_POCO.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso a empresa não acumule o volume do poço com o volume da ligação de água para cálculo
		// do valor de esgoto
		if(pAcumularConsumoEsgotoPoco.equals(ConstantesSistema.NAO.toString())){

			String consumoFixoPoco = retificarContaActionForm.getConsumoFixoPoco();
			BigDecimal valorDebitoPoco = BigDecimal.ZERO;

			if(!Util.isVazioOuBranco(consumoFixoPoco)){

				Collection<CalcularValoresAguaEsgotoHelper> valoresContaPoco = fachada.calcularValoresConta(mesAnoConta, imovelID,
								LigacaoAguaSituacao.POTENCIAL, situacaoEsgotoConta, colecaoCategoriaOUSubcategoria, "0", consumoFixoPoco,
								percentualEsgoto, idConsumoTarifa, usuarioLogado, dataLeituraAtual, dataLeituraAnterior);

				for(Iterator iteratorValoresPoco = valoresContaPoco.iterator(); iteratorValoresPoco.hasNext();){

					CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelperPoco = (CalcularValoresAguaEsgotoHelper) iteratorValoresPoco
									.next();

					if(calcularValoresAguaEsgotoHelperPoco.getValorFaturadoEsgotoCategoria() != null){

						valorDebitoPoco = valorDebitoPoco.add(calcularValoresAguaEsgotoHelperPoco.getValorFaturadoEsgotoCategoria());
					}
				}
			}

			if(valorDebitoPoco.compareTo(BigDecimal.ZERO) == 1){

				String mesAnoDebito = mesAnoConta;

				DebitoCobrado debitoCobradoPoco = new DebitoCobrado();
				debitoCobradoPoco.setUltimaAlteracao(new Date());

				if(!Util.isVazioOuBranco(mesAnoDebito)){

					// [FS0002] - Validar ano e mês de referência
					if(!Util.validarMesAno(mesAnoDebito)){
						throw new ActionServletException("atencao.adicionar_debito_ano_mes_referencia_invalido");
					}

					// Quando o ano for menor que 1985 (ANO_LIMITE) exibir a mensagem,
					// "Ano de referência não deve ser menor que 1985".
					if(Integer.valueOf(mesAnoDebito.substring(3, 7)).intValue() < ConstantesSistema.ANO_LIMITE.intValue()){

						throw new ActionServletException("atencao.ano_mes_referencia_menor", null,
										String.valueOf(ConstantesSistema.ANO_LIMITE.intValue()));
					}

					// Invertendo o formato para yyyyMM (sem a barra)
					mesAnoDebito = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoDebito);
					debitoCobradoPoco.setAnoMesReferenciaDebito(new Integer(mesAnoDebito));
					debitoCobradoPoco.setAnoMesCobrancaDebito(new Integer(mesAnoDebito));

				}else{

					SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
					debitoCobradoPoco.setAnoMesReferenciaDebito(sistemaParametro.getAnoMesFaturamento());
					debitoCobradoPoco.setAnoMesCobrancaDebito(sistemaParametro.getAnoMesFaturamento());
				}

				debitoCobradoPoco.setValorPrestacao(valorDebitoPoco);

				// Realizando consulta para obter os dados do tipo do débito selecionado
				FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
				filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade(FiltroDebitoTipo.LANCAMENTO_ITEM_CONTABIL);
				filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, DebitoTipo.ESGOTO_ESPECIAL));
				filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getName());

				if(colecaoDebitoTipo == null || colecaoDebitoTipo.isEmpty()){

					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "DEBITO_TIPO");
				}else{

					DebitoTipo objDebitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(colecaoDebitoTipo);
					debitoCobradoPoco.setDebitoTipo(objDebitoTipo);
				}

				debitoCobradoPoco.setNumeroPrestacao(new Short("1").shortValue());
				debitoCobradoPoco.setNumeroPrestacaoDebito(new Short("1").shortValue());

				// Colocando o objeto gerado na coleção que ficará na sessão
				if(sessao.getAttribute("colecaoDebitoCobrado") == null){

					colecaoDebitoCobrado = new Vector();
					colecaoDebitoCobrado.add(debitoCobradoPoco);
					sessao.setAttribute("colecaoDebitoCobrado", colecaoDebitoCobrado);
					valorTotalDebitosConta = valorTotalDebitosConta.add(valorDebitoPoco);

				}else{

					// [FS0014] - Verificar débito já existente
					if(!verificarDebitoJaExistente(colecaoDebitoCobrado, debitoCobradoPoco)){

						colecaoDebitoCobrado.add(debitoCobradoPoco);
						sessao.setAttribute("colecaoDebitoCobrado", colecaoDebitoCobrado);
						valorTotalDebitosConta = valorTotalDebitosConta.add(valorDebitoPoco);
					}
				}
			}
		}


		// [FS0001 - Verificar valor total da conta negativo].
		Object[] retornoValorContaNegativo = fachada.verificarVaorTotalContaNegativo(valorTotalConta, valorTotalCreditosConta,
						valorTotalAgua.add(valorTotalEsgoto).add(valorTotalDebitosConta), colecaoAnteriorCreditosRealizados, usuarioLogado,
						false);

		Collection colecaoNovosCreditosRealizados = null;


		if(retornoValorContaNegativo != null){

			colecaoAnteriorCreditosRealizados = (Collection) retornoValorContaNegativo[1];
			colecaoNovosCreditosRealizados = (Collection) retornoValorContaNegativo[2];
			if(!Util.isVazioOrNulo(colecaoNovosCreditosRealizados)){
				colecaoAnteriorCreditosRealizados.addAll(colecaoNovosCreditosRealizados);

				sessao.setAttribute("checarValorNegativo", true);
			}

		}


		// Recalculando :
		valorTotalConta = new BigDecimal("0.00");

		valorTotalConta = valorTotalConta.add(valorTotalAgua);
		valorTotalConta = valorTotalConta.add(valorTotalEsgoto);
		valorTotalConta = valorTotalConta.add(valorTotalDebitosConta);

		if(!Util.isVazioOrNulo(colecaoAnteriorCreditosRealizados)){
			// Cálcula o valor total dos créditos de uma conta de acordo com o informado pelo
			// usuário
			valorTotalCreditosConta = fachada.calcularValorTotalCreditoConta(colecaoAnteriorCreditosRealizados);
		}else{
			// Cálcula o valor total dos créditos de uma conta de acordo com o informado pelo
			// usuário
			valorTotalCreditosConta = fachada.calcularValorTotalCreditoConta(colecaoCreditoRealizado, httpServletRequest.getParameterMap());
		}

		valorTotalConta = valorTotalConta.subtract(valorTotalCreditosConta);

		// Arredondando os valores obtidos para duas casas decimais
		valorTotalAgua.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalEsgoto.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalDebitosConta.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalCreditosConta.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalConta.setScale(2, BigDecimal.ROUND_HALF_UP);

		// Exibindo os valores calculados
		retificarContaActionForm.setValorAgua(Util.formatarMoedaReal(valorTotalAgua));
		retificarContaActionForm.setValorEsgoto(Util.formatarMoedaReal(valorTotalEsgoto));
		retificarContaActionForm.setValorDebito(Util.formatarMoedaReal(valorTotalDebitosConta));
		retificarContaActionForm.setValorCredito(Util.formatarMoedaReal(valorTotalCreditosConta));
		retificarContaActionForm.setValorTotal(Util.formatarMoedaReal(valorTotalConta));

		/*
		 * Colocado por Raphael Rossiter em 17/04/2007
		 * Objetivo: Manipulação dos objetos que serão exibidos no formulário de acordo com a
		 * empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());

		if(!Util.isVazioOuBranco(sessao.getAttribute("isPermitidoAlterarDataLeituraAnterior"))){
			httpServletRequest.setAttribute("isPermitidoAlterarDataLeituraAnterior", sessao
							.getAttribute("isPermitidoAlterarDataLeituraAnterior"));
		}

		// Verifica se a situação da ligação de esgoto é faturável, caso não seja limpa e desabilita
		// campos relativos aos dados da ligação de esgoto.
		if(retificarContaActionForm.getIndicadorEsgotoFaturavel() == null
						|| retificarContaActionForm.getIndicadorEsgotoFaturavel().equals(ConstantesSistema.NAO.toString())){

			retificarContaActionForm.setConsumoEsgoto(null);
			retificarContaActionForm.setPercentualEsgoto(null);
			retificarContaActionForm.setLigacaoEsgotoPerfilId(null);
		}

		return retorno;
	}

	private Integer atualizarQuantidadeEconomiasCategoria(Collection colecaoCategorias, HttpServletRequest httpServletRequest){

		/*
		 * Atualizando a quantidade de economias por categoria de acordo com o
		 * informado pelo usuário
		 */

		Integer qtdEconnomia = 0;

		if(colecaoCategorias != null && !colecaoCategorias.isEmpty()){

			Iterator colecaoCategoriaIt = colecaoCategorias.iterator();
			Categoria categoria;
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			String qtdPorEconomia;

			while(colecaoCategoriaIt.hasNext()){
				categoria = (Categoria) colecaoCategoriaIt.next();

				if(requestMap.get("categoria" + categoria.getId().intValue()) != null){

					qtdPorEconomia = (requestMap.get("categoria" + categoria.getId().intValue()))[0];

					if(qtdPorEconomia == null || qtdPorEconomia.equalsIgnoreCase("")){

						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Quantidade de economias");
					}

					categoria.setQuantidadeEconomiasCategoria(new Integer(qtdPorEconomia));

					qtdEconnomia = Util.somaInteiros(qtdEconnomia, new Integer(qtdPorEconomia));
				}
			}
		}

		return qtdEconnomia;
	}

	private Integer atualizarQuantidadeEconomiasSubcategoria(Collection colecaoSubcategorias, HttpServletRequest httpServletRequest){

		/*
		 * Atualizando a quantidade de economias por subcategoria de acordo com o
		 * informado pelo usuário
		 */

		Integer qtdEconnomia = 0;

		if(colecaoSubcategorias != null && !colecaoSubcategorias.isEmpty()){

			Iterator colecaoSubcategoriaIt = colecaoSubcategorias.iterator();
			Subcategoria subcategoria;
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			String qtdPorEconomia;

			while(colecaoSubcategoriaIt.hasNext()){
				subcategoria = (Subcategoria) colecaoSubcategoriaIt.next();

				if(requestMap.get("subcategoria" + subcategoria.getId().intValue()) != null){

					qtdPorEconomia = (requestMap.get("subcategoria" + subcategoria.getId().intValue()))[0];

					if(qtdPorEconomia == null || qtdPorEconomia.equalsIgnoreCase("")){

						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Quantidade de economias");
					}

					subcategoria.setQuantidadeEconomias(new Integer(qtdPorEconomia));

					qtdEconnomia = Util.somaInteiros(qtdEconnomia, new Integer(qtdPorEconomia));
				}
			}
		}

		return qtdEconnomia;
	}

	private boolean verificarDebitoJaExistente(Collection colecaoDebitoCobrado, DebitoCobrado debitoCobradoInsert){

		boolean retorno = false;

		Iterator colecaoDebitoCobradoIt = colecaoDebitoCobrado.iterator();
		DebitoCobrado debitoCobradoColecao;

		while(colecaoDebitoCobradoIt.hasNext()){
			debitoCobradoColecao = (DebitoCobrado) colecaoDebitoCobradoIt.next();
			if(debitoCobradoColecao.getDebitoTipo().getId().equals(debitoCobradoInsert.getDebitoTipo().getId())){
				retorno = true;
				break;
			}
		}

		return retorno;
	}

}
