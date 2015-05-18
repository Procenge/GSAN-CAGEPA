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
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.conta.ContaMotivoInclusao;
import gcom.faturamento.debito.DebitoCobrado;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Instância do formulário que está sendo utilizado
		InserirContaActionForm inserirContaActionForm = (InserirContaActionForm) actionForm;

		String confirmado = httpServletRequest.getParameter("confirmado");

		// Recebendo os dados enviados pelo formulário
		String imovelIdJSP = inserirContaActionForm.getIdImovel();
		String mesAnoContaJSP = inserirContaActionForm.getMesAnoConta();
		String vencimentoContaJSP = inserirContaActionForm.getVencimentoConta();
		Integer situacaoAguaContaJSP = Integer.valueOf(inserirContaActionForm.getSituacaoAguaConta());

		Integer situacaoEsgotoContaJSP = Integer.valueOf(inserirContaActionForm.getSituacaoEsgotoConta());

		Integer motivoInclusaoContaJSP = Integer.valueOf(inserirContaActionForm.getMotivoInclusaoID());

		String consumoAguaJSP = "0";
		if(!"".equals(inserirContaActionForm.getConsumoAgua())){
			consumoAguaJSP = inserirContaActionForm.getConsumoAgua();
		}

		String consumoEsgotoJSP = "0";
		if(!"".equals(inserirContaActionForm.getConsumoEsgoto())){
			consumoEsgotoJSP = inserirContaActionForm.getConsumoEsgoto();
		}

		String percentualEsgotoJSP = inserirContaActionForm.getPercentualEsgoto();

		Map<String, String[]> requestMap = httpServletRequest.getParameterMap();

		// Carrega as coleções de acordo com os objetos da sessão
		Collection colecaoDebitoCobrado = new Vector();
		if(sessao.getAttribute("colecaoDebitoCobrado") != null){
			colecaoDebitoCobrado = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
		}

		Collection colecaoCategoriaOUSubcategoria = null;
		Integer quantidadeEconomiaForm = 0;

		if(sessao.getAttribute("colecaoCategoria") != null){

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoCategoria");

			quantidadeEconomiaForm = this.atualizarQuantidadeEconomiasCategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

		}else{

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoSubcategoria");

			quantidadeEconomiaForm = this.atualizarQuantidadeEconomiasSubcategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

		}

		// [FS0015] - Verificar se foi efetuado o cálculo da conta

		// ==============================================================================================
		Collection<CalcularValoresAguaEsgotoHelper> valoresConta = null;

		// [SF0001] - Determinar Valores para Faturamento de Água e/ou Esgoto
		// ********************************************************************
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		valoresConta = fachada.calcularValoresConta(mesAnoContaJSP, imovelIdJSP, situacaoAguaContaJSP, situacaoEsgotoContaJSP,
						colecaoCategoriaOUSubcategoria, consumoAguaJSP, consumoEsgotoJSP, percentualEsgotoJSP, Util
										.obterInteger(inserirContaActionForm.getTarifaID()), usuarioLogado, null, null);

		// Cálcula o valor total dos débitos de uma conta de acordo com o informado pelo usuário
		BigDecimal valorTotalDebitosConta = fachada.calcularValorTotalDebitoConta(colecaoDebitoCobrado, httpServletRequest
						.getParameterMap());

		// Totalizando os valores de água e esgoto
		BigDecimal valorTotalAgua = new BigDecimal("0");
		BigDecimal valorTotalEsgoto = new BigDecimal("0");

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

		String pAcumularConsumoEsgotoPoco = null;
		try{

			pAcumularConsumoEsgotoPoco = ParametroMicromedicao.P_ACUMULA_CONSUMO_ESGOTO_POCO.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso a empresa não acumule o volume do poço com o volume da ligação de água para cálculo
		// do valor de esgoto
		Integer consumoFixoPoco = null;
		if(pAcumularConsumoEsgotoPoco.equals(ConstantesSistema.NAO.toString())
						&& !Util.isVazioOuBranco(inserirContaActionForm.getConsumoFixoPoco())){

			consumoFixoPoco = Util.obterInteger(inserirContaActionForm.getConsumoFixoPoco());
			BigDecimal valorDebitoPoco = BigDecimal.ZERO;

			if(!Util.isVazioOuBranco(consumoFixoPoco)){

				Collection<CalcularValoresAguaEsgotoHelper> valoresContaPoco = fachada.calcularValoresConta(mesAnoContaJSP, imovelIdJSP,
								LigacaoAguaSituacao.POTENCIAL, situacaoEsgotoContaJSP, colecaoCategoriaOUSubcategoria, "0",
								consumoFixoPoco.toString(), percentualEsgotoJSP, Util.obterInteger(inserirContaActionForm.getTarifaID()),
								usuarioLogado, null, null);

				for(Iterator iteratorValoresPoco = valoresContaPoco.iterator(); iteratorValoresPoco.hasNext();){

					CalcularValoresAguaEsgotoHelper calcularValoresAguaEsgotoHelperPoco = (CalcularValoresAguaEsgotoHelper) iteratorValoresPoco
									.next();

					if(calcularValoresAguaEsgotoHelperPoco.getValorFaturadoEsgotoCategoria() != null){

						valorDebitoPoco = valorDebitoPoco.add(calcularValoresAguaEsgotoHelperPoco.getValorFaturadoEsgotoCategoria());
					}
				}
			}

			if(valorDebitoPoco.compareTo(BigDecimal.ZERO) == 1){

				String mesAnoDebito = mesAnoContaJSP;

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

		BigDecimal valorTotalConta = new BigDecimal("0.00");

		valorTotalConta = valorTotalConta.add(valorTotalAgua);
		valorTotalConta = valorTotalConta.add(valorTotalEsgoto);
		valorTotalConta = valorTotalConta.add(valorTotalDebitosConta);

		// [FS0008] - Verificar valor da conta igual a zero
		if(valorTotalConta.equals(new BigDecimal("0.00"))){
			throw new ActionServletException("atencao.valor_conta_igual_zero");
		}
		// **************************************************************************************

		// Invertendo o formato para yyyyMM (sem a barra)
		mesAnoContaJSP = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoContaJSP);

		// Carregando as informações do imóvel
		FiltroImovel filtroImovel = new FiltroImovel();

		// Objetos que serão retornados pelo hibernate
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA_SETOR_COMERCIAL_LOCALIDADE);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CONSUMO_TARIFA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.IMOVEL_PERFIL);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO);
		filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_PERFIL);

		filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, imovelIdJSP));

		Collection<Imovel> colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

		// [FS0001] - Verificar existência da matrícula do imóvel
		if(colecaoImovel == null || colecaoImovel.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "imóvel");
		}

		Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

		// LigacaoAguaSituacao
		LigacaoAguaSituacao ligacaoAguaSituacao = new LigacaoAguaSituacao();
		ligacaoAguaSituacao.setId(situacaoAguaContaJSP);

		// LigacaoEsgotoSituacao
		LigacaoEsgotoSituacao ligacaoEsgotoSituacao = new LigacaoEsgotoSituacao();
		ligacaoEsgotoSituacao.setId(situacaoEsgotoContaJSP);

		// Data de vencimento da conta
		// Para auxiliar na formatação de uma data
		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		Date dataVencimentoConta;

		try{
			dataVencimentoConta = formatoData.parse(vencimentoContaJSP);
		}catch(ParseException ex){
			dataVencimentoConta = null;
		}
		// Inicio - [FS0005] – Validar data de vencimento
		dataVencimentoConta = getFachada().verificarDataUtilVencimento(dataVencimentoConta, imovel.getLocalidade().getMunicipio());

		if(Util.compararData(dataVencimentoConta, new Date()) == -1){
			if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
				return montarPaginaConfirmacao("atencao.data_vencimento.inferior.atual", httpServletRequest, actionMapping);
			}
		}

		if(Util.getAnoMesComoInt(dataVencimentoConta) < Util.obterInteger(Util.formatarMesAnoParaAnoMes(mesAnoContaJSP)).intValue()){
			throw new ActionServletException("atencao.mesano_data_vencimento_menor_referencia_conta");
		}

		String qtdDiasVencimento = null;
		try{
			qtdDiasVencimento = (String) ParametroFaturamento.P_QUANTIDADE_DIAS_VENCIMENTO_CONTA.executar();
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		if(Util.compararData(dataVencimentoConta, Util.adicionarNumeroDiasDeUmaData(new Date(), Util.obterInteger(qtdDiasVencimento)
						.intValue())) > 0){
			if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
				return montarPaginaConfirmacao("atencao.data_vencimento.posterior.X_dias", httpServletRequest, actionMapping,
								qtdDiasVencimento);
			}
		}
		// Fim - [FS0005] – Validar data de vencimento

		// [SB0008] - Verificar exigência RA Dados Cadastrais divergentes
		String exigirRaDadosDivergentes = null;
		try{
			exigirRaDadosDivergentes = (String) ParametroFaturamento.P_EXIGIR_RA_INCLUSAO_CONTA_DADOS_CADATRAIS_DIVERGENTES_IMOVEL
							.executar();
		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		if(exigirRaDadosDivergentes != null && exigirRaDadosDivergentes.equalsIgnoreCase("1")){

			Integer indicadorDivergencia = 2;
			boolean existenciaRaAlteracaoDadosConta = fachada.verificaExistenciaRAInclusaoContaDadosCadastraisDivergentesImovel(imovel
							.getId());
			Integer valorComparacaoDataValidadeTarifaTemporaria = (Integer) sessao
							.getAttribute("valorComparacaoDataValidadeTarifaTemporaria");
			Integer tarifaTemporaria = (Integer) sessao
							.getAttribute("tarifaTemporaria");

			String concatenacaoCamposDivergentes = "";

			if(imovel.getConsumoTarifaTemporaria() != null
							&& !imovel.getConsumoTarifaTemporaria().getId().toString().equals(inserirContaActionForm.getTarifaID())
							&& valorComparacaoDataValidadeTarifaTemporaria != 1 && tarifaTemporaria == 1){
				indicadorDivergencia = 1;
				concatenacaoCamposDivergentes = ", Tarifa";
				imovel.getConsumoTarifa().setId(Util.converterStringParaInteger(inserirContaActionForm.getTarifaID()));
			}

			if(imovel.getConsumoTarifa() != null
							&& !imovel.getConsumoTarifa().getId().toString().equals(inserirContaActionForm.getTarifaID())
							&& tarifaTemporaria == 2){
				indicadorDivergencia = 1;
				concatenacaoCamposDivergentes = ", Tarifa";
				imovel.getConsumoTarifa().setId(Util.converterStringParaInteger(inserirContaActionForm.getTarifaID()));
			}

			if(imovel.getLigacaoAguaSituacao() != null
							&& !imovel.getLigacaoAguaSituacao().getId().toString()
											.equalsIgnoreCase(inserirContaActionForm.getSituacaoAguaConta().toString())){
				indicadorDivergencia = 1;
				concatenacaoCamposDivergentes = concatenacaoCamposDivergentes + ", Situação de Água";
			}

			if(imovel.getLigacaoEsgotoSituacao() != null
							&& !imovel.getLigacaoEsgotoSituacao().getId().toString()
											.equalsIgnoreCase(inserirContaActionForm.getSituacaoEsgotoConta())){
				indicadorDivergencia = 1;
				concatenacaoCamposDivergentes = concatenacaoCamposDivergentes + ", Situação de Esgoto";
			}

			if(imovel.getLigacaoEsgoto() != null
							&& imovel.getLigacaoEsgoto().getLigacaoEsgotoPerfil() != null
							&& !imovel.getLigacaoEsgoto().getLigacaoEsgotoPerfil().getPercentualEsgotoConsumidaColetadaFormatado()
											.toString().equalsIgnoreCase(inserirContaActionForm.getLigacaoEsgotoPerfilId())){

				indicadorDivergencia = 1;
				concatenacaoCamposDivergentes = concatenacaoCamposDivergentes + ", Perfil da Ligação de Esgoto";
			}

			if(imovel.getLigacaoEsgoto() != null){
				BigDecimal imovelPercentualEsgoto = imovel.getLigacaoEsgoto().getPercentual();
				BigDecimal inserirContaActionFormPercentualEsgoto = null;

				if(inserirContaActionForm.getPercentualEsgoto() != null){
					inserirContaActionFormPercentualEsgoto = Util
									.formatarStringParaBigDecimal(inserirContaActionForm.getPercentualEsgoto());
				}
				if(imovel.getLigacaoEsgoto() != null && inserirContaActionFormPercentualEsgoto != null
								&& imovelPercentualEsgoto.compareTo(inserirContaActionFormPercentualEsgoto) != 0){
					indicadorDivergencia = 1;
					concatenacaoCamposDivergentes = concatenacaoCamposDivergentes + ", Percentual de Esgoto";
				}
			}

			if(imovel.getLigacaoEsgoto() == null && inserirContaActionForm.getPercentualEsgoto() != null){
				indicadorDivergencia = 1;
				concatenacaoCamposDivergentes = concatenacaoCamposDivergentes + ", Percentual de Esgoto";
			}

			if(imovel.getQuantidadeEconomias() != null && quantidadeEconomiaForm != null
							&& imovel.getQuantidadeEconomias() != quantidadeEconomiaForm.shortValue()){
				indicadorDivergencia = 1;
				concatenacaoCamposDivergentes = concatenacaoCamposDivergentes + ", Categorias e Economias";
			}

			if(indicadorDivergencia == 1 && !existenciaRaAlteracaoDadosConta){
				throw new ActionServletException("atencao.inclusao_conta_dados_divergentes", concatenacaoCamposDivergentes, imovel.getId().toString());
			}
		}
		// Fim - [SB0008] - Verificar exigência RA Dados Cadastrais divergentes

		// Motivo da inclusão da conta
		ContaMotivoInclusao contaMotivoInclusao = new ContaMotivoInclusao();
		contaMotivoInclusao.setId(motivoInclusaoContaJSP);

		// [SF002] - Gerar dados da conta, [SF003] - Gerar os débitos cobrados

		Integer idConta = fachada.inserirConta(new Integer(mesAnoContaJSP), imovel, colecaoDebitoCobrado, ligacaoAguaSituacao,
						ligacaoEsgotoSituacao, colecaoCategoriaOUSubcategoria, consumoAguaJSP, consumoEsgotoJSP, percentualEsgotoJSP,
						dataVencimentoConta, valoresConta, contaMotivoInclusao, requestMap, usuarioLogado, consumoFixoPoco);

		montarPaginaSucesso(httpServletRequest, "Conta " + Util.formatarMesAnoReferencia(new Integer(mesAnoContaJSP).intValue())
						+ " do imóvel " + imovel.getId() + " inserida com sucesso.", "Inserir outra Conta",
						"exibirInserirContaAction.do?menu=sim", "exibirManterContaAction.do?idImovelRequest=" + imovel.getId(),
						"Atualizar Conta(s) do Imovel " + imovel.getId(), "Emitir 2ª Via de Conta",
						"gerarRelatorio2ViaContaAction.do?idConta=" + idConta);

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

					categoria.setQuantidadeEconomiasCategoria(Integer.valueOf(qtdPorEconomia));

					qtdEconnomia = Util.somaInteiros(qtdEconnomia, Integer.valueOf(qtdPorEconomia));
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

					subcategoria.setQuantidadeEconomias(Integer.valueOf(qtdPorEconomia));

					qtdEconnomia = Util.somaInteiros(qtdEconnomia, Integer.valueOf(qtdPorEconomia));
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
