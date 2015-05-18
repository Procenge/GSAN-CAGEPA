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

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.bean.CalcularValoresAguaEsgotoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
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
import gcom.util.parametrizacao.micromedicao.ParametroMicromedicao;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CalcularValoresContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirConta");

		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		InserirContaActionForm inserirContaActionForm = (InserirContaActionForm) actionForm;

		// Recebendo os dados enviados pelo formul�rio
		String imovelID = inserirContaActionForm.getIdImovel();
		String mesAnoConta = inserirContaActionForm.getMesAnoConta();
		Integer situacaoAguaConta = new Integer(inserirContaActionForm.getSituacaoAguaConta());
		Imovel imovel = new Imovel();

		if(!Util.isVazioOuBranco(imovelID) && !Util.isVazioOuBranco(mesAnoConta)){

			FaturamentoGrupo faturamentoGrupo = getFachada().pesquisarGrupoImovel(Util.obterInteger(imovelID));

			// [FS0022] Valida Refer�ncia da Conta
			if(Util.compararAnoMesReferencia(Util.formatarMesAnoComBarraParaAnoMes(mesAnoConta), faturamentoGrupo.getAnoMesReferencia(),
							">")){
				throw new ActionServletException("atencao.mesano_ref_conta_maior_mesano_faturamento");
			}

			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("faturamentoSituacaoTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ROTA_FATURAMENTO_GRUPO);
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, Util.obterInteger(imovelID)));

			imovel = (Imovel) Util.retonarObjetoDeColecao(getFachada().pesquisar(filtroImovel,
							Imovel.class.getName()));

			if(imovel.getFaturamentoSituacaoTipo() != null
							&& imovel.getFaturamentoSituacaoTipo().getIndicadorFaturamentoParalisacaoEsgoto()
											.equals(ConstantesSistema.SIM)){
				throw new ActionServletException("atencao.inclusao_conta_indevido_faturamento_suspenso");
			}
		}

		Integer situacaoEsgotoConta = new Integer(inserirContaActionForm.getSituacaoEsgotoConta());
		String consumoAgua = inserirContaActionForm.getConsumoAgua();
		String consumoEsgoto = inserirContaActionForm.getConsumoEsgoto();
		String percentualEsgoto = inserirContaActionForm.getPercentualEsgoto();

		// Carrega as cole��es de acordo com os objetos da sess�o
		Collection colecaoDebitoCobrado = null;
		if(sessao.getAttribute("colecaoDebitoCobrado") != null){
			colecaoDebitoCobrado = (Collection) sessao.getAttribute("colecaoDebitoCobrado");
		}

		Integer consumoTarifaID = null;
		if(inserirContaActionForm.getTarifaID() != null && !inserirContaActionForm.getTarifaID().equalsIgnoreCase("")){
			consumoTarifaID = new Integer(inserirContaActionForm.getTarifaID());
		}

		// Cria��o do consumo tarifa
		ConsumoTarifa consumoTarifa = new ConsumoTarifa();
		consumoTarifa.setId(consumoTarifaID);
		imovel.setConsumoTarifa(consumoTarifa);

		Collection colecaoCategoriaOUSubcategoria = null;
		Integer qtdEconnomia = null;
		int consumoMinimoLigacao = 0;

		if(sessao.getAttribute("colecaoCategoria") != null){

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoCategoria");
			qtdEconnomia = this.atualizarQuantidadeEconomiasCategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

			// Obt�m o consumo m�nimo liga��o por categoria
			consumoMinimoLigacao = fachada.obterConsumoMinimoLigacaoPeriodo(imovel, colecaoCategoriaOUSubcategoria, mesAnoConta,
							consumoTarifaID);
		}else{

			colecaoCategoriaOUSubcategoria = (Collection) sessao.getAttribute("colecaoSubcategoria");
			qtdEconnomia = this.atualizarQuantidadeEconomiasSubcategoria(colecaoCategoriaOUSubcategoria, httpServletRequest);

			// Obt�m o consumo m�nimo liga��o por categoria
			consumoMinimoLigacao = fachada.obterConsumoMinimoLigacaoPeriodo(imovel, colecaoCategoriaOUSubcategoria, mesAnoConta,
							consumoTarifaID);
		}

		// SB0010 � Ajuste do Consumo para M�ltiplo da Quantidade de Economias
		Integer consumoAguaAux = null;
		if(consumoAgua != null && !"".equals(consumoAgua)){
			consumoAguaAux = Integer.parseInt(consumoAgua);
		}

		Integer consumoEsgotoAux = null;
		if(consumoEsgoto != null && !"".equals(consumoEsgoto)){
			consumoEsgotoAux = Integer.parseInt(consumoEsgoto);
		}else{
			consumoEsgotoAux = consumoAguaAux;
		}

		Map<String, String> consumoFaturadoAguaEsgoto = fachada.ajusteConsumoMultiploQuantidadeEconomia(consumoAguaAux,
						consumoMinimoLigacao, consumoEsgotoAux, qtdEconnomia);

		if(consumoFaturadoAguaEsgoto != null && !consumoFaturadoAguaEsgoto.isEmpty()){
			if(consumoFaturadoAguaEsgoto.get("agua") != null){
				consumoAgua = consumoFaturadoAguaEsgoto.get("agua");
			}

			if(consumoFaturadoAguaEsgoto.get("esgoto") != null && "".equals(consumoFaturadoAguaEsgoto.get("esgoto"))){
				consumoEsgoto = consumoFaturadoAguaEsgoto.get("esgoto");
			}else{
				consumoEsgoto = consumoAgua;
			}
		}

		Collection<CalcularValoresAguaEsgotoHelper> valoresConta = null;

		// [SF0001] - Determinar Valores para Faturamento de �gua e/ou Esgoto
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		valoresConta = fachada.calcularValoresConta(mesAnoConta, imovelID, situacaoAguaConta, situacaoEsgotoConta,
						colecaoCategoriaOUSubcategoria, consumoAgua, consumoEsgoto, percentualEsgoto, Util
										.obterInteger(inserirContaActionForm.getTarifaID()), usuarioLogado, null, null);

		// C�lcula o valor total dos d�bitos de uma conta de acordo com o informado pelo usu�rio
		BigDecimal valorTotalDebitosConta = fachada.calcularValorTotalDebitoConta(colecaoDebitoCobrado, httpServletRequest
						.getParameterMap());

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

		if(valoresConta != null){
			// Consumo de Esgoto
			Integer consumoAgua2 = fachada.calcularConsumoTotalAguaOuEsgotoPorCategoria(valoresConta, ConstantesSistema.CALCULAR_AGUA);
			if(consumoAgua2 != null && consumoAgua2 > 0){
				inserirContaActionForm.setConsumoAgua(consumoAgua2.toString());
			}
			Integer consumoEsgoto2 = fachada.calcularConsumoTotalAguaOuEsgotoPorCategoria(valoresConta, ConstantesSistema.CALCULAR_ESGOTO);
			if(consumoEsgoto2 != null && consumoEsgoto2 > 0){
				inserirContaActionForm.setConsumoEsgoto(consumoEsgoto2.toString());
			}else{
				inserirContaActionForm.setConsumoEsgoto("");
			}
		}

		String pAcumularConsumoEsgotoPoco = null;
		try{

			pAcumularConsumoEsgotoPoco = ParametroMicromedicao.P_ACUMULA_CONSUMO_ESGOTO_POCO.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		// Caso a empresa n�o acumule o volume do po�o com o volume da liga��o de �gua para c�lculo
		// do valor de esgoto
		if(pAcumularConsumoEsgotoPoco.equals(ConstantesSistema.NAO.toString())){

			String consumoFixoPoco = inserirContaActionForm.getConsumoFixoPoco();
			BigDecimal valorDebitoPoco = BigDecimal.ZERO;

			if(!Util.isVazioOuBranco(consumoFixoPoco)){

				Collection<CalcularValoresAguaEsgotoHelper> valoresContaPoco = fachada.calcularValoresConta(mesAnoConta, imovelID,
								LigacaoAguaSituacao.POTENCIAL, situacaoEsgotoConta, colecaoCategoriaOUSubcategoria, "0", consumoFixoPoco,
								percentualEsgoto, Util.obterInteger(inserirContaActionForm.getTarifaID()), usuarioLogado, null, null);

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

					SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
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
					valorTotalDebitosConta = valorTotalDebitosConta.add(valorDebitoPoco);

				}else{

					// [FS0014] - Verificar d�bito j� existente
					if(!verificarDebitoJaExistente(colecaoDebitoCobrado, debitoCobradoPoco)){

						colecaoDebitoCobrado.add(debitoCobradoPoco);
						sessao.setAttribute("colecaoDebitoCobrado", colecaoDebitoCobrado);
						valorTotalDebitosConta = valorTotalDebitosConta.add(valorDebitoPoco);
					}
				}
			}
		}

		BigDecimal valorTotalConta = new BigDecimal("0");

		valorTotalConta = valorTotalConta.add(valorTotalAgua);
		valorTotalConta = valorTotalConta.add(valorTotalEsgoto);
		valorTotalConta = valorTotalConta.add(valorTotalDebitosConta);

		// [FS0008] - Verificar valor da conta igual a zero
		if(valorTotalConta.equals(new BigDecimal("0.00"))){
			throw new ActionServletException("atencao.valor_conta_igual_zero");
		}

		// Arredondando os valores obtidos para duas casas decimais
		valorTotalAgua.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalEsgoto.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalDebitosConta.setScale(2, BigDecimal.ROUND_HALF_UP);
		valorTotalConta.setScale(2, BigDecimal.ROUND_HALF_UP);

		// Exibindo os valores calculados
		inserirContaActionForm.setValorAgua(Util.formatarMoedaReal(valorTotalAgua));
		inserirContaActionForm.setValorEsgoto(Util.formatarMoedaReal(valorTotalEsgoto));
		inserirContaActionForm.setValorDebito(Util.formatarMoedaReal(valorTotalDebitosConta));
		inserirContaActionForm.setValorTotal(Util.formatarMoedaReal(valorTotalConta));

		// setando o valor do percentual
		if(!Util.isVazioOuBranco(percentualEsgoto)){
			httpServletRequest.setAttribute("percentualEsgoto", Util.formatarMoedaRealparaBigDecimal(percentualEsgoto).toString().replace(
							'.', ','));
		}

		/*
		 * Colocado por Raphael Rossiter em 17/04/2007
		 * Objetivo: Manipula��o dos objetos que ser�o exibidos no formul�rio de acordo com a
		 * empresa
		 */
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		httpServletRequest.setAttribute("empresaNome", sistemaParametro.getNomeAbreviadoEmpresa().trim());

		// Verifica se a situa��o da liga��o de esgoto � fatur�vel, caso n�o seja limpa e desabilita
		// campos relativos aos dados da liga��o de esgoto.
		if(inserirContaActionForm.getIndicadorEsgotoFaturavel() == null
						|| inserirContaActionForm.getIndicadorEsgotoFaturavel().equals(ConstantesSistema.NAO.toString())){

			inserirContaActionForm.setLigacaoEsgotoPerfilId(null);
			inserirContaActionForm.setConsumoEsgoto(null);
			inserirContaActionForm.setPercentualEsgoto(null);
		}

		if(inserirContaActionForm.getIndicadorAguaFaturavel() == null
						|| inserirContaActionForm.getIndicadorAguaFaturavel().equals(ConstantesSistema.NAO.toString())){

			inserirContaActionForm.setConsumoAgua(null);

		}

		return retorno;
	}

	private Integer atualizarQuantidadeEconomiasCategoria(Collection colecaoCategorias, HttpServletRequest httpServletRequest){

		/*
		 * Atualizando a quantidade de economias por categoria de acordo com o
		 * informado pelo usu�rio
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
		 * informado pelo usu�rio
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
