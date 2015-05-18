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

package gcom.gui.faturamento.conta;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteConta;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.conta.*;
import gcom.faturamento.credito.CreditoRealizado;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.micromedicao.medicao.MedicaoTipo;
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

public class RetificarContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		RetificarContaActionForm retificarContaActionForm = (RetificarContaActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Recebendo os dados enviados pelo formul�rio
		String imovelIdJSP = retificarContaActionForm.getIdImovel();
		String mesAnoContaJSP = retificarContaActionForm.getMesAnoConta();
		String vencimentoContaJSP = retificarContaActionForm.getVencimentoConta();
		String vencimentoContaJSPAnterior = retificarContaActionForm.getVencimentoContaAnterior();
		Integer situacaoAguaContaJSP = new Integer(retificarContaActionForm.getSituacaoAguaConta());
		Integer situacaoEsgotoContaJSP = new Integer(retificarContaActionForm.getSituacaoEsgotoConta());
		Integer motivoRetificacaoContaJSP = new Integer(retificarContaActionForm.getMotivoRetificacaoID());
		String consumoAguaJSP = retificarContaActionForm.getConsumoAgua();
		String consumoEsgotoJSP = retificarContaActionForm.getConsumoEsgoto();
		String percentualEsgotoJSP = retificarContaActionForm.getPercentualEsgoto();
		Date dataLeituraAtual = null;
		Date dataLeituraAnterior = null;

		// Indicador da opera��o caucionamento de conta
		String indicadorOperacao = (String) sessao.getAttribute("indicadorOperacao");

		if(!Util.isVazioOuBranco(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua())){
			dataLeituraAtual = Util.converteStringParaDate(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua(), true);
		}

		if(!Util.isVazioOuBranco(retificarContaActionForm.getDataLeituraAnteriorMedicaoHistoricoAgua())){
			dataLeituraAnterior = Util.converteStringParaDate(retificarContaActionForm.getDataLeituraAnteriorMedicaoHistoricoAgua(), true);
		}

		// Carregando a conta com os dados atuais
		Conta contaAtual = (Conta) sessao.getAttribute("contaRetificar");

		// fachada.verificarVencimentoContaDebitoAutomatico(contaAtual);

		// Tarifa de Consumo
		ConsumoTarifa consumoTarifa = null;
		Integer idConsumoTarifa = null;

		String idConsumoTarifaStr = retificarContaActionForm.getConsumoTarifaId();

		if(!Util.isVazioOuBranco(idConsumoTarifaStr) && !idConsumoTarifaStr.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
			idConsumoTarifa = Integer.valueOf(idConsumoTarifaStr);

			consumoTarifa = new ConsumoTarifa();
			consumoTarifa.setId(idConsumoTarifa);
		}

		// Cliente
		Cliente cliente = null;
		String pPermitirAlterarClienteConta = null;

		try{

			pPermitirAlterarClienteConta = (String) ParametroFaturamento.P_PERMITIR_ALTERAR_CLIENTE_CONTA.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso seja permitido alterar cliente conta
		if(Util.isNaoNuloBrancoZero(pPermitirAlterarClienteConta)
							&& pPermitirAlterarClienteConta.equals(ConstantesSistema.SIM.toString())){

			if(Util.isNaoNuloBrancoZero(retificarContaActionForm.getIdCliente())){

				cliente = (Cliente) fachada.pesquisar(Integer.valueOf(retificarContaActionForm.getIdCliente()), Cliente.class);

				if(cliente == null){

					throw new ActionServletException("atencao.cliente.inexistente");
				}
			}else{

				ClienteConta clienteConta = fachada.pesquisarClienteContaPorTipoRelacao(contaAtual.getId(), ClienteRelacaoTipo.RESPONSAVEL);

				// Caso a conta selecionada tenha respons�vel � obrigat�rio que seja informado um
				// respons�vel para a conta retificada
				if(clienteConta != null){

					if(Util.isNaoNuloBrancoZero(retificarContaActionForm.getIdCliente())){

						cliente = (Cliente) fachada.pesquisar(Integer.valueOf(retificarContaActionForm.getIdCliente()), Cliente.class);

						if(cliente == null){

							throw new ActionServletException("atencao.cliente.inexistente");
						}
					}else{

						throw new ActionServletException("atencao.required", null, "Cliente Respons�vel da Conta");
					}
				}
			}
		}

		Integer faturamentoGrupoAnoMesReferencia = getFachada().pesquisarAnoMesReferenciaFaturamentoGrupo(contaAtual.getImovel().getId());
		int anoMesReferencia = 0;

		if(faturamentoGrupoAnoMesReferencia != null){
			anoMesReferencia = faturamentoGrupoAnoMesReferencia.intValue();
		}

		// [UC0146][FS0025] Validar Leitura de faturamento
		if(contaAtual.getReferencia() == anoMesReferencia
						&& Util.obterInteger(retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua()).intValue() == 0){
			throw new ActionServletException("atencao.leitura_faturamento_zero");
		}

		String agua = null;
		String consumoAguaAntes = null;
		String esgoto = null;
		String consumoEsgotoAntes = null;
		if(consumoAguaJSP == null){
			agua = "1";
		}else{
			agua = consumoAguaJSP;
		}
		if(sessao.getAttribute("consumoAguaAntes") == null){
			consumoAguaAntes = "1";
		}else{
			consumoAguaAntes = sessao.getAttribute("consumoAguaAntes").toString();
		}

		if(consumoEsgotoJSP == null){
			esgoto = "1";
		}else{
			esgoto = consumoEsgotoJSP;
		}
		if(sessao.getAttribute("consumoEsgotoAntes") == null){
			consumoEsgotoAntes = "1";
		}else{
			consumoEsgotoAntes = sessao.getAttribute("consumoEsgotoAntes").toString();
		}

		String percentualEsgotoConta = null;

		if(percentualEsgotoJSP != null){
			percentualEsgotoConta = percentualEsgotoJSP.toString().replace(".", "");
			percentualEsgotoConta = percentualEsgotoConta.replace(",", "");
		}else{
			percentualEsgotoConta = "1";
		}

		String numeroLeituraAtualMedicaoHistoricoAguaAntes = (String) sessao.getAttribute("numeroLeituraAtualMedicaoHistoricoAguaAntes");
		if(numeroLeituraAtualMedicaoHistoricoAguaAntes == null){
			numeroLeituraAtualMedicaoHistoricoAguaAntes = "";
		}

		String numeroLeituraAtualMedicaoHistoricoAgua = retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoAgua();
		if(numeroLeituraAtualMedicaoHistoricoAgua == null){
			numeroLeituraAtualMedicaoHistoricoAgua = "";
		}

		Boolean flag = true;
		if(situacaoAguaContaJSP.toString().equals(sessao.getAttribute("situacaoAguaContaAntes"))
						&& situacaoEsgotoContaJSP.toString().equals(sessao.getAttribute("situacaoEsgotoContaAntes"))
						&& agua.equals(consumoAguaAntes) && esgoto.equals(consumoEsgotoAntes)
						&& percentualEsgotoConta.equals(sessao.getAttribute("percentualEsgotoAntes"))
						&& numeroLeituraAtualMedicaoHistoricoAgua.equals(numeroLeituraAtualMedicaoHistoricoAguaAntes)
						&& !vencimentoContaJSP.equals(sessao.getAttribute("vencimentoContaAntes"))){
			flag = false;
		}
		// Carrega as cole��es de acordo com os objetos da sess�o
		Collection colecaoDebitoCobrado = new Vector();
		if(sessao.getAttribute("colecaoDebitoCobrado") != null){
			colecaoDebitoCobrado = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
		}

		Collection colecaoCreditoRealizado = new Vector();
		if(sessao.getAttribute("colecaoCreditoRealizado") != null){
			colecaoCreditoRealizado = (Collection) sessao.getAttribute("colecaoCreditoRealizado");
		}

		// Alterado por Raphael Rossiter em 17/04/2007
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		Collection colecaoCategoriaOUSubcategoria = new Vector();
		Collection colecaoCategoriaOUSubcategoriaInicial = new Vector();

		if(sistemaParametro.getNomeAbreviadoEmpresa().trim().equalsIgnoreCase(SistemaParametro.EMPRESA_CAERN)){

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoSubcategoria");

			if(sessao.getAttribute("colecaoSubcategoriaInicial") != null){

				colecaoCategoriaOUSubcategoriaInicial = (Collection) sessao.getAttribute("colecaoSubcategoriaInicial");

				if(colecaoCategoriaOUSubcategoria == null
								|| colecaoCategoriaOUSubcategoriaInicial.size() != colecaoCategoriaOUSubcategoria.size()
								|| !colecaoCategoriaOUSubcategoriaInicial.containsAll(colecaoCategoriaOUSubcategoria)){

					flag = true;
				}

				// Atualizando a quantidade de economias por subcategoria de acordo com o informado
				// pelo usu�rio
				// -------------------------------------------------------------------------------------------
				Iterator colecaoCategoriaOUSubcategoriaInicialIt = colecaoCategoriaOUSubcategoriaInicial.iterator();
				Subcategoria subcategoria;
				Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
				String qtdPorEconomia;

				while(colecaoCategoriaOUSubcategoriaInicialIt.hasNext()){
					subcategoria = (Subcategoria) colecaoCategoriaOUSubcategoriaInicialIt.next();

					if(requestMap.get("subcategoria" + subcategoria.getId().intValue()) != null){

						qtdPorEconomia = (requestMap.get("subcategoria" + subcategoria.getId().intValue()))[0];
						if(!subcategoria.getQuantidadeEconomias().toString().equals(qtdPorEconomia)){
							flag = true;
						}

						if(qtdPorEconomia == null || qtdPorEconomia.equalsIgnoreCase("")){
							throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Quantidade de economias");
						}

						subcategoria.setQuantidadeEconomias(new Integer(qtdPorEconomia));
					}
				}
				// ------------------------------------------------------------------------------------------
			}
		}else{

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoCategoria");

			if(sessao.getAttribute("colecaoCategoriaInicial") != null){

				colecaoCategoriaOUSubcategoriaInicial = (Collection) sessao.getAttribute("colecaoCategoriaInicial");

				if(colecaoCategoriaOUSubcategoria == null
								|| colecaoCategoriaOUSubcategoriaInicial.size() != colecaoCategoriaOUSubcategoria.size()
								|| !colecaoCategoriaOUSubcategoriaInicial.containsAll(colecaoCategoriaOUSubcategoria)){

					flag = true;
				}

				// Atualizando a quantidade de economias por categoria de acordo com o informado
				// pelo usu�rio
				// -------------------------------------------------------------------------------------------
				Iterator colecaoCategoriaOUSubcategoriaInicialIt = colecaoCategoriaOUSubcategoriaInicial.iterator();
				Categoria categoria;
				Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
				String qtdPorEconomia;

				while(colecaoCategoriaOUSubcategoriaInicialIt.hasNext()){
					categoria = (Categoria) colecaoCategoriaOUSubcategoriaInicialIt.next();

					if(requestMap.get("categoria" + categoria.getId().intValue()) != null){

						qtdPorEconomia = (requestMap.get("categoria" + categoria.getId().intValue()))[0];
						if(!categoria.getQuantidadeEconomiasCategoria().toString().equals(qtdPorEconomia)){
							flag = true;
						}

						if(qtdPorEconomia == null || qtdPorEconomia.equalsIgnoreCase("")){
							throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Quantidade de economias");
						}

						categoria.setQuantidadeEconomiasCategoria(new Integer(qtdPorEconomia));
					}
				}
				// ------------------------------------------------------------------------------------------
			}
		}

		Collection colecaoDebito = new Vector();
		if(sessao.getAttribute("colecaoDebitoCobradoInicial") != null){
			colecaoDebito = (Collection) sessao.getAttribute("colecaoDebitoCobradoInicial");

			if(sessao.getAttribute("colecaoDebitoCobrado") == null
							|| colecaoDebito.size() != ((Collection) sessao.getAttribute("colecaoDebitoCobrado")).size()){
				flag = true;
			}

			// Atualizando o valor do debito de acordo com o informado pelo usu�rio
			// -------------------------------------------------------------------------------------------
			Iterator colecaoDebitoIt = colecaoDebito.iterator();
			DebitoCobrado debitoCobrado;
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			BigDecimal valorInformado = new BigDecimal("0.00");
			BigDecimal valorPrestaIncial = new BigDecimal("0.00");
			FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
			filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");

			while(colecaoDebitoIt.hasNext()){
				debitoCobrado = (DebitoCobrado) colecaoDebitoIt.next();

				if(requestMap.get("debitoCobrado" + GcomAction.obterTimestampIdObjeto(debitoCobrado)) != null){
					// Valor da presta��o inicial do d�bito cobrado
					valorPrestaIncial = debitoCobrado.getValorPrestacao();

					// Valor informado da presta��o do d�bito cobrado
					valorInformado = Util.formatarStringParaBigDecimal((requestMap.get("debitoCobrado"
									+ GcomAction.obterTimestampIdObjeto(debitoCobrado)))[0], 2, true);

					if(!valorPrestaIncial.equals(valorInformado)){
						// [FS0026]
						filtroDebitoTipo.limparListaParametros();
						filtroDebitoTipo
										.adicionarParametro(new ParametroSimples(FiltroDebitoTipo.ID, debitoCobrado.getDebitoTipo().getId()));

						DebitoTipo debitoTipo = (DebitoTipo) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroDebitoTipo,
										DebitoTipo.class.getName()));

						if(!getFachada().verificarPermissaoEspecial(PermissaoEspecial.RETIFICAR_CONTA_EM_COBRANCA_JUDICIAL_OU_SUBJUDICE,
										usuario)
										|| debitoTipo.getFinanciamentoTipo().getIndicadorAlteraValor().equals(ConstantesSistema.NAO)){
							throw new ActionServletException("atencao.nao_permitido_alterar_valor_servico", null, debitoTipo.getDescricao());
						}
						flag = true;
					}

					if(valorInformado == null){
						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "D�bito Cobrado");
					}

					debitoCobrado.setValorPrestacao(valorInformado);
				}
			}
			// ------------------------------------------------------------------------------------------
		}

		Collection colecaoCredito = new Vector();
		if(sessao.getAttribute("colecaoCreditoRealizadoInicial") != null){
			colecaoCredito = (Collection) sessao.getAttribute("colecaoCreditoRealizadoInicial");

			if(sessao.getAttribute("colecaoCreditoRealizado") == null
							|| colecaoCredito.size() != ((Collection) sessao.getAttribute("colecaoCreditoRealizado")).size()){
				flag = true;
			}

			// Atualizando o valor do credito de acordo com o informado pelo usu�rio
			// -------------------------------------------------------------------------------------------
			Iterator colecaoCreditoIt = colecaoCredito.iterator();
			CreditoRealizado creditoRealizado;
			Map<String, String[]> requestMap = httpServletRequest.getParameterMap();
			BigDecimal valor = new BigDecimal("0.00");
			BigDecimal valorCreditoIncial = new BigDecimal("0.00");

			while(colecaoCreditoIt.hasNext()){
				creditoRealizado = (CreditoRealizado) colecaoCreditoIt.next();

				if(requestMap.get("creditoRealizado" + GcomAction.obterTimestampIdObjeto(creditoRealizado)) != null){
					// Valor do inicial do cr�dito realizado
					valorCreditoIncial = creditoRealizado.getValorCredito();

					// Valor informado cr�dito realizado
					valor = Util.formatarStringParaBigDecimal((requestMap.get("creditoRealizado"
									+ GcomAction.obterTimestampIdObjeto(creditoRealizado)))[0], 2, true);

					if(!valorCreditoIncial.equals(valor)){
						flag = true;
					}

					if(valor == null){
						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Cr�dito Realizado");
					}

					creditoRealizado.setValorCredito(valor);
				}
			}
			// ------------------------------------------------------------------------------------------
		}
		// [FS0015] - Verificar se foi efetuado o c�lculo da conta

		// [SF0001] - Determinar Valores para Faturamento de �gua e/ou Esgoto
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		if(consumoEsgotoJSP == null || consumoEsgotoJSP.equals("")){
			if(consumoAguaJSP != null && !consumoAguaJSP.equals("")){
				consumoEsgotoJSP = consumoAguaJSP;
			}else{
				if(contaAtual != null && contaAtual.getConsumoAgua() != null){
					consumoEsgotoJSP = contaAtual.getConsumoAgua().toString();
				}else{
					consumoEsgotoJSP = "0";
				}

			}

		}

		Collection<CalcularValoresAguaEsgotoHelper> valoresConta = fachada.calcularValoresConta(mesAnoContaJSP, imovelIdJSP,
						situacaoAguaContaJSP, situacaoEsgotoContaJSP, colecaoCategoriaOUSubcategoria, consumoAguaJSP, consumoEsgotoJSP,
						percentualEsgotoJSP, idConsumoTarifa, usuarioLogado, dataLeituraAnterior, dataLeituraAtual);

		// // C�lcula o valor total dos d�bitos de uma conta de acordo com o informado pelo usu�rio
		// BigDecimal valorTotalDebitosConta =
		// fachada.calcularValorTotalDebitoConta(colecaoDebitoCobrado, httpServletRequest
		// .getParameterMap());
		//
		// // C�lcula o valor total dos cr�ditos de uma conta de acordo com o informado pelo usu�rio
		// BigDecimal valorTotalCreditosConta =
		// fachada.calcularValorTotalCreditoConta(colecaoCreditoRealizado, httpServletRequest
		// .getParameterMap());

		// Totalizando os valores de �gua e esgoto
		BigDecimal valorTotalAgua = new BigDecimal("0");
		BigDecimal valorTotalEsgoto = new BigDecimal("0");

		if(valoresConta != null && !valoresConta.isEmpty()){

			Iterator valoresContaIt = valoresConta.iterator();
			CalcularValoresAguaEsgotoHelper valoresContaObjeto = null;

			while(valoresContaIt.hasNext()){

				valoresContaObjeto = (CalcularValoresAguaEsgotoHelper) valoresContaIt.next();

				// Valor Faturado de �gua
				if(valoresContaObjeto.getValorFaturadoAguaCategoria() != null){
					valorTotalAgua = valorTotalAgua.add(valoresContaObjeto.getValorFaturadoAguaCategoria());
				}

				// Valor Faturado de Esgoto
				if(valoresContaObjeto.getValorFaturadoEsgotoCategoria() != null){
					valorTotalEsgoto = valorTotalEsgoto.add(valoresContaObjeto.getValorFaturadoEsgotoCategoria());
				}

			}

		}

		// BigDecimal valorTotalConta = new BigDecimal("0");
		//
		// valorTotalConta = valorTotalConta.add(valorTotalAgua);
		// valorTotalConta = valorTotalConta.add(valorTotalEsgoto);
		// valorTotalConta = valorTotalConta.add(valorTotalDebitosConta);
		//
		// valorTotalConta = valorTotalConta.subtract(valorTotalCreditosConta);

		// // [FS0008] - Verificar valor da conta igual a zero
		//
		// if(valorTotalConta.equals(new BigDecimal("0.00"))
		// && (valorTotalCreditosConta == null || valorTotalCreditosConta.equals(new
		// BigDecimal("0.00")))){
		// throw new ActionServletException("atencao.valor_conta_igual_zero");
		// }else if(valorTotalConta.compareTo(new BigDecimal("0.00")) == -1){
		// throw new ActionServletException("atencao.valor_conta_negativo");
		// }

		String pAcumularConsumoEsgotoPoco = null;
		try{

			pAcumularConsumoEsgotoPoco = ParametroMicromedicao.P_ACUMULA_CONSUMO_ESGOTO_POCO.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso a empresa n�o acumule o volume do po�o com o volume da liga��o de �gua para c�lculo
		// do valor de esgoto
		if(pAcumularConsumoEsgotoPoco.equals(ConstantesSistema.NAO.toString())){

			String consumoFixoPoco = retificarContaActionForm.getConsumoFixoPoco();
			BigDecimal valorDebitoPoco = BigDecimal.ZERO;

			if(!Util.isVazioOuBranco(consumoFixoPoco)){

				Collection<CalcularValoresAguaEsgotoHelper> valoresContaPoco = fachada.calcularValoresConta(mesAnoContaJSP, imovelIdJSP,
								LigacaoAguaSituacao.POTENCIAL, situacaoEsgotoContaJSP, colecaoCategoriaOUSubcategoria, "0",
								consumoFixoPoco, percentualEsgotoJSP, idConsumoTarifa,
								usuarioLogado, null, null);

				for(Iterator iteratorValoresPoco = valoresContaPoco.iterator(); iteratorValoresPoco.hasNext();){

					CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelperPoco = (CalcularValoresAguaEsgotoHelper) iteratorValoresPoco
									.next();

					if(calcularValoresAguaEsgotoHelperPoco.getValorFaturadoEsgotoCategoria() != null){

						valorDebitoPoco = valorDebitoPoco.add(calcularValoresAguaEsgotoHelperPoco.getValorFaturadoEsgotoCategoria());
					}
				}

				contaAtual.setConsumoPoco(Util.obterInteger(consumoFixoPoco));
			}

			if(valorDebitoPoco.compareTo(BigDecimal.ZERO) == 1){

				String mesAnoDebito = mesAnoContaJSP;

				DebitoCobrado debitoCobradoPoco = new DebitoCobrado();
				debitoCobradoPoco.setUltimaAlteracao(new Date());

				if(!Util.isVazioOuBranco(mesAnoDebito)){

					// [FS0002] - Validar ano e m�s de refer�ncia
					if(!Util.validarMesAno(mesAnoDebito)){
						throw new ActionServletException("atencao.adicionar_debito_ano_mes_referencia_invalido");
					}

					// Quando o ano for menor que 1985 (ANO_LIMITE) exibir a mensagem,
					// "Ano de refer�ncia n�o deve ser menor que 1985".
					if(Integer.valueOf(mesAnoDebito.substring(3, 7)).intValue() < ConstantesSistema.ANO_LIMITE.intValue()){

						throw new ActionServletException("atencao.ano_mes_referencia_menor", null,
										String.valueOf(ConstantesSistema.ANO_LIMITE.intValue()));
					}

					// Invertendo o formato para yyyyMM (sem a barra)
					mesAnoDebito = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoDebito);
					debitoCobradoPoco.setAnoMesReferenciaDebito(new Integer(mesAnoDebito));
					debitoCobradoPoco.setAnoMesCobrancaDebito(new Integer(mesAnoDebito));

				}else{

					debitoCobradoPoco.setAnoMesReferenciaDebito(sistemaParametro.getAnoMesFaturamento());
					debitoCobradoPoco.setAnoMesCobrancaDebito(sistemaParametro.getAnoMesFaturamento());
				}

				debitoCobradoPoco.setValorPrestacao(valorDebitoPoco);

				// Realizando consulta para obter os dados do tipo do d�bito selecionado
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

				// Colocando o objeto gerado na cole��o que ficar� na sess�o
				if(sessao.getAttribute("colecaoDebitoCobrado") == null){

					colecaoDebitoCobrado = new Vector();
					colecaoDebitoCobrado.add(debitoCobradoPoco);
					sessao.setAttribute("colecaoDebitoCobrado", colecaoDebitoCobrado);

				}else{

					// [FS0014] - Verificar d�bito j� existente
					if(!verificarDebitoJaExistente(colecaoDebitoCobrado, debitoCobradoPoco)){

						colecaoDebitoCobrado.add(debitoCobradoPoco);
						sessao.setAttribute("colecaoDebitoCobrado", colecaoDebitoCobrado);
					}
				}
			}
		}

		// Invertendo o formato para yyyyMM (sem a barra)
		mesAnoContaJSP = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoContaJSP);


		// LigacaoAguaSituacao
		LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
		ligacaoAguaSituacao.setId(situacaoAguaContaJSP);

		// LigacaoEsgotoSituacao
		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
		ligacaoEsgotoSituacao.setId(situacaoEsgotoContaJSP);

		// Data de vencimento da conta
		Date dataVencimentoConta = Util.converteStringParaDate(vencimentoContaJSP, true);

		// Motivo da Retifica��o da conta
		ContaMotivoRetificacao contaMotivoRetificacao = new ContaMotivoRetificacao();
		contaMotivoRetificacao.setId(motivoRetificacaoContaJSP);
		if(!flag){
			throw new ActionServletException("atencao.so_vencimento_alterado_para_retificar_conta");
		}

		// Dados de Leitura Agua e Esgoto
		Collection<MedicaoHistorico> colecaoDadosMedicaoLeitura = new ArrayList<MedicaoHistorico>();
		if((retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoAgua() != null && !retificarContaActionForm
						.getNumeroLeituraAtualMedicaoHistoricoAgua().trim().equals(""))
						|| (retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua() != null && !retificarContaActionForm
										.getDataLeituraAtualMedicaoHistoricoAgua().trim().equals(""))
						|| (retificarContaActionForm.getNumeroLeituraAnteriorMedicaoHistoricoAgua() != null && !retificarContaActionForm
										.getNumeroLeituraAnteriorMedicaoHistoricoAgua().trim().equals(""))){ // Leitura
			// existente
			// ou
			// informada
			MedicaoHistorico medicaoHistoricoAgua = new MedicaoHistorico();

			MedicaoTipo medicaoAgua = new MedicaoTipo();
			medicaoAgua.setId(MedicaoTipo.LIGACAO_AGUA);
			medicaoHistoricoAgua.setMedicaoTipo(medicaoAgua);

			LigacaoAgua ligacaoAgua = new LigacaoAgua();
			ligacaoAgua.setId(contaAtual.getImovel().getId());
			medicaoHistoricoAgua.setLigacaoAgua(ligacaoAgua);
			medicaoHistoricoAgua.setAnoMesReferencia(contaAtual.getReferencia());

			if(retificarContaActionForm.getNumeroLeituraAnteriorMedicaoHistoricoAgua() != null){

				medicaoHistoricoAgua.setLeituraAnteriorFaturamento(new Integer(retificarContaActionForm
							.getNumeroLeituraAnteriorMedicaoHistoricoAgua()));
			}

			medicaoHistoricoAgua.setLeituraAtualFaturamento(new Integer(retificarContaActionForm
							.getNumeroLeituraAtualMedicaoHistoricoAgua()));

			if(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua() != null){

				medicaoHistoricoAgua.setDataLeituraAtualFaturamento(Util.converteStringParaDate(
								retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoAgua(), true));
			}

			if(retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua() != null
							&& !retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				LeituraAnormalidade leituraAnormalidadeAgua = new LeituraAnormalidade();
				leituraAnormalidadeAgua.setId(new Integer(retificarContaActionForm
								.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdAgua()));
				medicaoHistoricoAgua.setLeituraAnormalidadeFaturamento(leituraAnormalidadeAgua);
			}

			colecaoDadosMedicaoLeitura.add(medicaoHistoricoAgua);
		}

		if((retificarContaActionForm.getNumeroLeituraAtualMedicaoHistoricoEsgoto() != null && !retificarContaActionForm
						.getNumeroLeituraAtualMedicaoHistoricoEsgoto().trim().equals(""))
						|| (retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoEsgoto() != null && !retificarContaActionForm
										.getDataLeituraAtualMedicaoHistoricoEsgoto().trim().equals(""))){ // Leitura
			// existente
			// ou
			// informada
			MedicaoHistorico medicaoHistoricoEsgoto = new MedicaoHistorico();
			MedicaoTipo medicaoEsgoto = new MedicaoTipo();
			medicaoEsgoto.setId(MedicaoTipo.POCO);
			medicaoHistoricoEsgoto.setMedicaoTipo(medicaoEsgoto);
			medicaoHistoricoEsgoto.setAnoMesReferencia(contaAtual.getReferencia());
			medicaoHistoricoEsgoto.setImovel(contaAtual.getImovel());

			if(retificarContaActionForm.getNumeroLeituraAnteriorMedicaoHistoricoEsgoto() != null){

				medicaoHistoricoEsgoto.setLeituraAnteriorFaturamento(new Integer(retificarContaActionForm
							.getNumeroLeituraAnteriorMedicaoHistoricoEsgoto()));
			}

			medicaoHistoricoEsgoto.setLeituraAtualFaturamento(new Integer(retificarContaActionForm
							.getNumeroLeituraAtualMedicaoHistoricoEsgoto()));

			if(retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoEsgoto() != null){

				medicaoHistoricoEsgoto.setDataLeituraAtualFaturamento(Util.converteStringParaDate(
								retificarContaActionForm.getDataLeituraAtualMedicaoHistoricoEsgoto(), true));
			}

			if(retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto() != null
							&& !retificarContaActionForm.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto().equalsIgnoreCase(
											String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
				LeituraAnormalidade leituraAnormalidadeEsgoto = new LeituraAnormalidade();
				leituraAnormalidadeEsgoto.setId(new Integer(retificarContaActionForm
								.getLeituraAnormalidadeFaturamentoMedicaoHistoricoIdEsgoto()));
				medicaoHistoricoEsgoto.setLeituraAnormalidadeFaturamento(leituraAnormalidadeEsgoto);
			}

			colecaoDadosMedicaoLeitura.add(medicaoHistoricoEsgoto);
		}

		Date dataVencimentoContaAnterior = Util.converteStringParaDate(vencimentoContaJSPAnterior, true);

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

		Integer idConta = null;

		// Caso a opera��o seja de caucionar a conta
		if(!Util.isVazioOuBranco(indicadorOperacao) && indicadorOperacao.equals("caucionar")){
			// Recupera a cole��o conta im�vel da sess�o
			Collection<Conta> colecaoContaImovel = (Collection) sessao.getAttribute("colecaoContaImovel");

			Collection colecaoMotivoRevisaoConta = null;
			ContaMotivoRevisao contaMotivoRevisao = null;

			// Consulta o motivo da Revis�o para Caucionamento
			FiltroMotivoRevisaoConta filtroMotivoRevisaoConta = new FiltroMotivoRevisaoConta(
							FiltroMotivoRevisaoConta.DESCRICAO_MOTIVO_REVISAO_CONTA);

			filtroMotivoRevisaoConta
							.adicionarParametro(new ParametroSimples(FiltroMotivoRevisaoConta.ID, ContaMotivoRevisao.CAUCIONAMENTO));

			filtroMotivoRevisaoConta.adicionarParametro(new ParametroSimples(FiltroMotivoRevisaoConta.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoMotivoRevisaoConta = fachada.pesquisar(filtroMotivoRevisaoConta, ContaMotivoRevisao.class.getName());

			// Motivo Revis�o da Conta
			if(!Util.isVazioOrNulo(colecaoMotivoRevisaoConta)){
				contaMotivoRevisao = (ContaMotivoRevisao) Util.retonarObjetoDeColecao(colecaoMotivoRevisaoConta);
			}

			// Caucionar Conta
			Map<Conta, Collection<Collection<ContaCategoriaConsumoFaixa>>> caucionamento = fachada.caucionarConta(new Integer(
							mesAnoContaJSP), contaAtual, contaAtual.getImovel(), colecaoDebitoCobrado, colecaoCreditoRealizado,
							ligacaoAguaSituacao, ligacaoEsgotoSituacao, colecaoCategoriaOUSubcategoria, consumoAguaJSP, consumoEsgotoJSP,
							percentualEsgotoJSP, dataVencimentoConta, valoresConta, contaMotivoRevisao, httpServletRequest
											.getParameterMap(), usuarioLogado, colecaoDadosMedicaoLeitura, consumoTarifa,
							colecaoContaImovel);

			// Incluir conta na sess�o
			sessao.setAttribute("caucionamento", caucionamento);

			sessao.setAttribute("colecaoDadosMedicaoLeitura", colecaoDadosMedicaoLeitura);

			montarPaginaSucesso(httpServletRequest, "Conta " + Util.formatarAnoMesParaMesAno(new Integer(mesAnoContaJSP).intValue())
							+ " do im�vel " + contaAtual.getImovel().getId().intValue() + " caucionada com sucesso.",
							"Realizar outra Manuten�ao de Conta", "exibirManterContaAction.do?menu=sim",
							"gerarRelatorio2ViaContaAction.do?idNomeRelatorio=3&cobrarTaxaEmissaoConta=N&idConta="
											+ contaAtual.getId().toString(), "Emitir 2� Via de Conta");

		}else{

			if(sessao.getAttribute("checarValorNegativo") != null && sessao.getAttribute("checarValorNegativo").equals(true)){
				colecaoCreditoRealizado = (Collection) sessao.getAttribute("colecaoCreditoRealizadoInicial");
			}

			idConta = fachada.retificarConta(new Integer(mesAnoContaJSP), contaAtual, contaAtual.getImovel(), colecaoDebitoCobrado,
							colecaoCreditoRealizado, ligacaoAguaSituacao, ligacaoEsgotoSituacao, colecaoCategoriaOUSubcategoria,
							consumoAguaJSP, consumoEsgotoJSP, percentualEsgotoJSP, dataVencimentoConta, valoresConta,
							contaMotivoRetificacao, httpServletRequest.getParameterMap(), usuarioLogado, colecaoDadosMedicaoLeitura,
							consumoTarifa, cliente);

			// Recupera conta anterior e posterior da sess�o
			String idAnterior = (String) sessao.getAttribute("idAnterior");
			String idPosterior = (String) sessao.getAttribute("idPosterior");
			String proximaConta = "";

			if(!Util.isVazioOuBranco(idAnterior) || !Util.isVazioOuBranco(idPosterior)){
				if(!Util.isVazioOuBranco(idPosterior)){
					proximaConta = idPosterior;
				}else{
					proximaConta = idAnterior;
				}

				montarPaginaSucesso(
								httpServletRequest,
								"Conta " + Util.formatarAnoMesParaMesAno(new Integer(mesAnoContaJSP).intValue()) + " do im�vel "
												+ contaAtual.getImovel().getId().intValue() + " retificada com sucesso.",
								"Realizar outra Manuten�ao de Conta",
								"exibirManterContaAction.do?menu=sim",
								"gerarRelatorio2ViaContaAction.do?idNomeRelatorio=3&cobrarTaxaEmissaoConta=N&idConta=" + idConta.toString(),
								"Emitir 2� Via de Conta", "Voltar", "exibirAtualizarSituacaoContaAction.do?idConta=" + proximaConta);

			}else{
				montarPaginaSucesso(
								httpServletRequest,
								"Conta " + Util.formatarAnoMesParaMesAno(new Integer(mesAnoContaJSP).intValue()) + " do im�vel "
												+ contaAtual.getImovel().getId().intValue() + " retificada com sucesso.",
								"Realizar outra Manuten�ao de Conta",
								"exibirManterContaAction.do?menu=sim",
								"gerarRelatorio2ViaContaAction.do?idNomeRelatorio=3&cobrarTaxaEmissaoConta=N&idConta=" + idConta.toString(),
								"Emitir 2� Via de Conta", "Voltar", "exibirFiltrarContasPreFaturadasAction.do?desfazer=S");

			}

			sessao.setAttribute("proximaConta", proximaConta);
		}

		return retorno;
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
