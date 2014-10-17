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

package gcom.gui.cobranca.parcelamento;

import gcom.cobranca.parcelamento.ParcelamentoQuantidadePrestacao;
import gcom.cobranca.parcelamento.ParcelamentoQuantidadePrestacaoHelper;
import gcom.cobranca.parcelamento.ParcelamentoQuantidadeReparcelamentoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action respons�vel por adicionar na collectionParcelamentoQuantidadePrestacao
 * um ou mais objeto(s) do tipo ParcelamentoQuantidadePrestacao
 * 
 * @author Vivianne Sousa
 * @created 05/05/2006
 */
public class InserirPrestacoesParcelamentoPerfilAction
				extends GcomAction {

	/**
	 * Este caso de uso permite a inclus�o de um novo perfil de parcelamento
	 * [UC0220] Inserir Perfil de Parcelamento
	 * 
	 * @author Vivianne Sousa
	 * @date 05/05/2006
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirPrestacoesParcelamentoPerfilAction");
		HttpSession sessao = httpServletRequest.getSession(false);

		atualizaColecaoNaSessao(sessao, httpServletRequest);
		@SuppressWarnings("unused")
		Fachada fachada = Fachada.getInstancia();
		sessao.removeAttribute("collectionParcelamentoFaixaValor");

		if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null
						&& !sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper").equals("")){

			Collection collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");
			// cria as vari�veis para recuperar os par�metros do request e jogar
			// no objeto ParcelamentoDescontoInatividade

			atualizarCollectionParcelamentoQuantidadePrestacaoHelper(collectionParcelamentoQuantidadePrestacaoHelper, httpServletRequest,
							sessao);

			Collection collectionParcelamentoQuantidadeReparcelamentoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper");

			Collection collectionParcelamentoQuantidadeReparcelamentoHelper1 = new ArrayList();

			if(collectionParcelamentoQuantidadeReparcelamentoHelper != null
							&& !collectionParcelamentoQuantidadeReparcelamentoHelper.isEmpty()){

				Iterator iterator = collectionParcelamentoQuantidadeReparcelamentoHelper.iterator();

				boolean jaInformado = false;

				while(iterator.hasNext()){
					ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQuantidadeReparcelamentoHelper = (ParcelamentoQuantidadeReparcelamentoHelper) iterator
									.next();

					if(parcelamentoQuantidadeReparcelamentoHelper.getQuantidadeMaximaReparcelamento().toString().equals(
									(String) sessao.getAttribute("qtdeMaxReparcelamento"))){
						parcelamentoQuantidadeReparcelamentoHelper
										.setCollectionParcelamentoQuantidadePrestacaoHelper(collectionParcelamentoQuantidadePrestacaoHelper);
						parcelamentoQuantidadeReparcelamentoHelper.setInformacaoParcelamentoQtdeReparcelamento("INFORMADAS");
						parcelamentoQuantidadeReparcelamentoHelper.setUltimaAlteracao(new Date());

						jaInformado = true;
					}
					collectionParcelamentoQuantidadeReparcelamentoHelper1.add(parcelamentoQuantidadeReparcelamentoHelper);
				}

				if(!jaInformado){
					// TODO Isilva
					ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQuantidadeReparcelamentoHelper = new ParcelamentoQuantidadeReparcelamentoHelper();
					parcelamentoQuantidadeReparcelamentoHelper.setQuantidadeMaximaReparcelamento(Short.valueOf((String) sessao
									.getAttribute("qtdeMaxReparcelamento")));
					parcelamentoQuantidadeReparcelamentoHelper
									.setCollectionParcelamentoQuantidadePrestacaoHelper(collectionParcelamentoQuantidadePrestacaoHelper);
					parcelamentoQuantidadeReparcelamentoHelper.setInformacaoParcelamentoQtdeReparcelamento("INFORMADAS");
					parcelamentoQuantidadeReparcelamentoHelper.setUltimaAlteracao(new Date());

					collectionParcelamentoQuantidadeReparcelamentoHelper1.add(parcelamentoQuantidadeReparcelamentoHelper);
				}

			}else{
				// TODO Isilva
				ParcelamentoQuantidadeReparcelamentoHelper parcelamentoQuantidadeReparcelamentoHelper = new ParcelamentoQuantidadeReparcelamentoHelper();
				parcelamentoQuantidadeReparcelamentoHelper.setQuantidadeMaximaReparcelamento(Short.valueOf((String) sessao
								.getAttribute("qtdeMaxReparcelamento")));
				parcelamentoQuantidadeReparcelamentoHelper
								.setCollectionParcelamentoQuantidadePrestacaoHelper(collectionParcelamentoQuantidadePrestacaoHelper);
				parcelamentoQuantidadeReparcelamentoHelper.setInformacaoParcelamentoQtdeReparcelamento("INFORMADAS");
				parcelamentoQuantidadeReparcelamentoHelper.setUltimaAlteracao(new Date());

				collectionParcelamentoQuantidadeReparcelamentoHelper1.add(parcelamentoQuantidadeReparcelamentoHelper);
			}

			if(collectionParcelamentoQuantidadeReparcelamentoHelper1 != null
							&& !collectionParcelamentoQuantidadeReparcelamentoHelper1.isEmpty()){
				// Ordena a cole��o pela qunatidade de reparcelamento
				Collections.sort((List) collectionParcelamentoQuantidadeReparcelamentoHelper1, new Comparator() {

					public int compare(Object a, Object b){

						Integer valorMinPrestacao1 = new Integer(((ParcelamentoQuantidadeReparcelamentoHelper) a)
										.getQuantidadeMaximaReparcelamento().toString());
						Integer valorMinPrestacao2 = new Integer(((ParcelamentoQuantidadeReparcelamentoHelper) b)
										.getQuantidadeMaximaReparcelamento().toString());

						return valorMinPrestacao1.compareTo(valorMinPrestacao2);

					}
				});
			}

			sessao.setAttribute("collectionParcelamentoQuantidadeReparcelamentoHelper",
							collectionParcelamentoQuantidadeReparcelamentoHelper1);

			if(sessao.getAttribute("UseCase") != null && sessao.getAttribute("UseCase").equals("INSERIRPERFIL")){
				httpServletRequest.setAttribute("reloadPage", "INSERIRPERFIL");
			}else{
				httpServletRequest.setAttribute("reloadPage", "ATUALIZARPERFIL");
			}

		}

		// Limpa a sess�o
		sessao.removeAttribute("primeiraVez");
		sessao.removeAttribute("qtdeMaximaReparcelamento");
		sessao.removeAttribute("adicionarReparcelamento");
		sessao.removeAttribute("desabilitarTaxaJuros");

		return retorno;
	}

	/**
	 * @author isilva
	 * @param sessao
	 * @param fachada
	 * @param inserirPrestacoesParcelamentoPerfilActionForm
	 */
	private void adicionarParcelamentoQuantidadePrestacao(HttpSession sessao, Fachada fachada,
					InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm){

		if(Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao())){
			// Informe Quantidade M�xima de Presta��es
			throw new ActionServletException("atencao.required", null, " Quantidade M�xima de Presta��es");
		}else if(Util.validarValorNaoNumericoComoBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao())){
			// Quantidade M�xima de Presta��es deve ser numerico
			throw new ActionServletException("atencao.integer", null, " Quantidade M�xima de Presta��es");
		}

		Collection<ParcelamentoQuantidadePrestacaoHelper> collectionParcelamentoQuantidadePrestacaoHelper = null;

		if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null){
			collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");
		}else{
			collectionParcelamentoQuantidadePrestacaoHelper = new ArrayList();
		}

		Short qtdeMaximaPrestacao = new Short(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaximaPrestacao());

		if(collectionParcelamentoQuantidadePrestacaoHelper != null && !collectionParcelamentoQuantidadePrestacaoHelper.isEmpty()){
			// se cole��o n�o estiver vazia
			// verificar se a qtd m�xima de presta��es do parcelamento ja foi informada
			ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoAntigoHelper = null;
			Iterator iterator = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

			while(iterator.hasNext()){
				parcelamentoQuantidadePrestacaoAntigoHelper = (ParcelamentoQuantidadePrestacaoHelper) iterator.next();

				if(qtdeMaximaPrestacao.intValue() == parcelamentoQuantidadePrestacaoAntigoHelper.getParcelamentoQuantidadePrestacao()
								.getQuantidadeMaximaPrestacao().intValue()){
					// Quantidade M�xima de Presta��es j� informada
					throw new ActionServletException("atencao.qtde_maxima_prestacoes_ja_informado");
				}
			}
		}

		// Valida os dados
		validarDados(inserirPrestacoesParcelamentoPerfilActionForm, sessao);

		Short qtdeMaxPrestacaoPermissaoEspecial = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaxPrestacaoEspecial())){
			qtdeMaxPrestacaoPermissaoEspecial = new Short(inserirPrestacoesParcelamentoPerfilActionForm.getQuantidadeMaxPrestacaoEspecial());
		}

		BigDecimal taxaJuros = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getTaxaJuros())){
			taxaJuros = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm.getTaxaJuros());
		}else if(sessao.getAttribute("desabilitarTaxaJuros") != null
						&& sessao.getAttribute("desabilitarTaxaJuros").toString().equals("true")){

			taxaJuros = Util.formatarMoedaRealparaBigDecimal("0,00");
		}

		BigDecimal valorMinimoEntrada = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getValorMinimoEntrada())){
			valorMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm.getValorMinimoEntrada()
							.toString(), 2);
		}

		BigDecimal percentualMinimoEntrada = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada())){
			percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualMinimoEntrada().toString(), 2);
		}

		BigDecimal percentualTarifaMinimaImovel = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel())){
			percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualTarifaMinimaImovel().toString(), 2);
		}

		// if ((inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada()== null
		// ||
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada().equalsIgnoreCase("")
		// || inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada().equals(0))
		// &&(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel()== null
		// ||
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel().equalsIgnoreCase("")
		// ||
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel().equals(0))){
		//	    	
		// percentualMinimoEntrada = new BigDecimal(0);
		// percentualTarifaMinimaImovel = new BigDecimal(0);
		//
		// }else{
		//	    	
		// if (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada()!= null
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada().equalsIgnoreCase("")){
		//	    		 
		// percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal
		// (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada());
		//	    		 
		// }else if
		// (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel()!= null
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel().equalsIgnoreCase("")){
		//	    		 
		// percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal
		// (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel());
		// }
		//			
		// }

		Short indicadorOpcoesEntradaParcelamento = Short.valueOf("1");
		if(!Util.isVazioOuBranco(percentualMinimoEntrada)
						&& !Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getIndicadorOpcoesEntradaParcelamento())){
			indicadorOpcoesEntradaParcelamento = Short.valueOf(inserirPrestacoesParcelamentoPerfilActionForm
							.getIndicadorOpcoesEntradaParcelamento());
		}

		BigDecimal percentualValorReparcelado = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado())){
			percentualValorReparcelado = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualValorReparcelado());
		}

		// Meses entre Parcelas
		Integer numeroMesesEntreParcelas = null;
		String numeroMesesEntreParcelasStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroMesesEntreParcelas();

		if(!Util.isVazioOuBranco(numeroMesesEntreParcelasStr)){
			numeroMesesEntreParcelas = Util.converterStringParaInteger(numeroMesesEntreParcelasStr);
		}

		// Parcelas a Lan�ar
		Integer numeroParcelasALancar = null;
		String numeroParcelasALancarStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroParcelasALancar();

		if(!Util.isVazioOuBranco(numeroParcelasALancarStr)){
			numeroParcelasALancar = Util.converterStringParaInteger(numeroParcelasALancarStr);
		}

		// Meses para In�cio da Cobran�a
		Integer numeroMesesInicioCobranca = null;
		String numeroMesesInicioCobrancaStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroMesesInicioCobranca();

		if(!Util.isVazioOuBranco(numeroMesesInicioCobrancaStr)){
			numeroMesesInicioCobranca = Util.converterStringParaInteger(numeroMesesInicioCobrancaStr);
		}

		// Dias Vencimento da Entrada
		Integer numeroDiasVencimentoDaEntrada = null;
		String numeroDiasVencimentoDaEntradaStr = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroDiasVencimentoDaEntrada();

		if(!Util.isVazioOuBranco(numeroDiasVencimentoDaEntradaStr)){
			numeroDiasVencimentoDaEntrada = Util.converterStringParaInteger(numeroDiasVencimentoDaEntradaStr);
		}

		BigDecimal percentualDescontoJurosMora = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualDescontoJurosMora())){
			percentualDescontoJurosMora = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualDescontoJurosMora().toString(), 2);
		}

		BigDecimal percentualDescontoMulta = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualDescontoMulta())){
			percentualDescontoMulta = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualDescontoMulta().toString(), 2);
		}

		BigDecimal percentualDescontoCorrecaoMonetaria = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualDescontoCorrecaoMonetaria())){
			percentualDescontoCorrecaoMonetaria = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualDescontoCorrecaoMonetaria().toString(), 2);
		}

		BigDecimal percentualEntradaSugerida = null;
		if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualEntradaSugerida())){
			percentualEntradaSugerida = Util.formatarMoedaRealparaBigDecimal(inserirPrestacoesParcelamentoPerfilActionForm
							.getPercentualEntradaSugerida().toString(), 2);
		}

		// vivi !!!!!!! aceita zero????
		// insere como null ou zero??
		// if (inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado() != null
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado().equals("")
		// &&
		// !inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado().equals(0)){
		// percentualValorReparcelado =Util.formatarMoedaRealparaBigDecimal(
		// inserirPrestacoesParcelamentoPerfilActionForm.getPercentualValorReparcelado());
		// }

		ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacaoNovo = new ParcelamentoQuantidadePrestacao();

		parcelamentoQuantidadePrestacaoNovo.setValorMinimoEntrada(valorMinimoEntrada);
		parcelamentoQuantidadePrestacaoNovo.setIndicadorEntradaParcelamento(indicadorOpcoesEntradaParcelamento);
		parcelamentoQuantidadePrestacaoNovo.setQuantidadeMaximaPrestacao(qtdeMaximaPrestacao);
		parcelamentoQuantidadePrestacaoNovo.setQuantidadeMaxPrestacaoEspecial(qtdeMaxPrestacaoPermissaoEspecial);
		parcelamentoQuantidadePrestacaoNovo.setTaxaJuros(taxaJuros);
		parcelamentoQuantidadePrestacaoNovo.setPercentualMinimoEntrada(percentualMinimoEntrada);
		parcelamentoQuantidadePrestacaoNovo.setPercentualTarifaMinimaImovel(percentualTarifaMinimaImovel);
		parcelamentoQuantidadePrestacaoNovo.setPercentualValorReparcelado(percentualValorReparcelado);
		parcelamentoQuantidadePrestacaoNovo.setUltimaAlteracao(new Date());
		parcelamentoQuantidadePrestacaoNovo.setNumeroMesesEntreParcelas(numeroMesesEntreParcelas);
		parcelamentoQuantidadePrestacaoNovo.setNumeroParcelasALancar(numeroParcelasALancar);
		parcelamentoQuantidadePrestacaoNovo.setNumeroMesesInicioCobranca(numeroMesesInicioCobranca);
		parcelamentoQuantidadePrestacaoNovo.setNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntrada);
		parcelamentoQuantidadePrestacaoNovo.setPercentualDescontoJurosMora(percentualDescontoJurosMora);
		parcelamentoQuantidadePrestacaoNovo.setPercentualDescontoMulta(percentualDescontoMulta);
		parcelamentoQuantidadePrestacaoNovo.setPercentualDescontoCorrecaoMonetaria(percentualDescontoCorrecaoMonetaria);
		parcelamentoQuantidadePrestacaoNovo.setPercentualEntradaSugerida(percentualEntradaSugerida);

		ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelperNovo = new ParcelamentoQuantidadePrestacaoHelper();

		parcelamentoQuantidadePrestacaoHelperNovo.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacaoNovo);

		if(sessao.getAttribute("collectionParcelamentoFaixaValor") != null){
			Collection collectionParcelamentoFaixaValor = (Collection) sessao.getAttribute("collectionParcelamentoFaixaValor");
			parcelamentoQuantidadePrestacaoHelperNovo.setCollectionParcelamentoFaixaValor(collectionParcelamentoFaixaValor);
			sessao.removeAttribute("collectionParcelamentoFaixaValor");
		}

		collectionParcelamentoQuantidadePrestacaoHelper.add(parcelamentoQuantidadePrestacaoHelperNovo);

		inserirPrestacoesParcelamentoPerfilActionForm.setValorMinimoEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setQuantidadeMaximaPrestacao("");
		inserirPrestacoesParcelamentoPerfilActionForm.setQuantidadeMaxPrestacaoEspecial("");
		inserirPrestacoesParcelamentoPerfilActionForm.setTaxaJuros("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualMinimoEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualTarifaMinimaImovel("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualValorReparcelado("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroMesesEntreParcelas("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroParcelasALancar("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroMesesInicioCobranca("");
		inserirPrestacoesParcelamentoPerfilActionForm.setNumeroDiasVencimentoDaEntrada("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualDescontoJurosMora("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualDescontoMulta("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualDescontoCorrecaoMonetaria("");
		inserirPrestacoesParcelamentoPerfilActionForm.setPercentualEntradaSugerida("");

		// Ordena a cole��o pela Qtde. M�xima Meses de Inatividade da Lig. de �gua
		Collections.sort((List) collectionParcelamentoQuantidadePrestacaoHelper, new Comparator() {

			public int compare(Object a, Object b){

				Integer valorMinPrestacao1 = new Integer(((ParcelamentoQuantidadePrestacaoHelper) a).getParcelamentoQuantidadePrestacao()
								.getQuantidadeMaximaPrestacao().toString());
				Integer valorMinPrestacao2 = new Integer(((ParcelamentoQuantidadePrestacaoHelper) b).getParcelamentoQuantidadePrestacao()
								.getQuantidadeMaximaPrestacao().toString());

				return valorMinPrestacao1.compareTo(valorMinPrestacao2);

			}
		});

		// manda para a sess�o a cole��o de ParcelamentoQuantidadePrestacao
		sessao.setAttribute("collectionParcelamentoQuantidadePrestacaoHelper", collectionParcelamentoQuantidadePrestacaoHelper);

	}

	/**
	 * @author isilva
	 * @param inserirPrestacoesParcelamentoPerfilActionForm
	 */
	private void validarDados(InserirPrestacoesParcelamentoPerfilActionForm inserirPrestacoesParcelamentoPerfilActionForm,
					HttpSession sessao){

		if(Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getTaxaJuros())){
			// Informe Taxa de Juros a.m.
			throw new ActionServletException("atencao.required", null, "  Taxa de Juros a.m.");
		}

		// Dias Vencimento da Entrada
		String numeroDiasVencimentoDaEntrada = inserirPrestacoesParcelamentoPerfilActionForm.getNumeroDiasVencimentoDaEntrada();

		if(Util.isVazioOuBranco(numeroDiasVencimentoDaEntrada)){
			throw new ActionServletException("atencao.required", null, "  Dias Vencimento da Entrada");
		}

		if(sessao.getAttribute("collectionParcelamentoFaixaValor") == null){
			int qtdInformada = 0;

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getValorMinimoEntrada())){
				qtdInformada++;
			}

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualTarifaMinimaImovel())){
				qtdInformada++;
			}

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada())){
				qtdInformada++;
			}

			if(qtdInformada != 1){
				throw new ActionServletException("atencao.informe.tarifa.minima.valor.ou.percentual.valor.debito.ou.valor.minimo.prestacao");
			}

			if(!Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getPercentualMinimoEntrada())
							&& Util.isVazioOuBranco(inserirPrestacoesParcelamentoPerfilActionForm.getIndicadorOpcoesEntradaParcelamento())){
				throw new ActionServletException("atencao.required", null, "Informe Op��es da Presta��o M�nima");
			}
		}
	}

	/**
	 * @author isilva
	 * @param collectionParcelamentoQuantidadePrestacaoHelper
	 * @param httpServletRequest
	 * @param sessao
	 */
	private void atualizarCollectionParcelamentoQuantidadePrestacaoHelper(Collection collectionParcelamentoQuantidadePrestacaoHelper,
					HttpServletRequest httpServletRequest, HttpSession sessao){

		Iterator iteratorParcelamentoQuantidadePrestacao = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

		while(iteratorParcelamentoQuantidadePrestacao.hasNext()){
			ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iteratorParcelamentoQuantidadePrestacao
							.next();

			ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = parcelamentoQuantidadePrestacaoHelper
							.getParcelamentoQuantidadePrestacao();

			long valorTempo = parcelamentoQuantidadePrestacao.getUltimaAlteracao().getTime();

			// cria as vari�veis para recuperar os par�metros do request e jogar
			// no objeto ParcelamentoQuantidadePrestacao
			String indicadorEntradaParcelamento = (String) httpServletRequest.getParameter("indicadorEntradaParcelamento" + valorTempo);
			String vlMinimoEntrada = (String) httpServletRequest.getParameter("vlMinimoEntrada" + valorTempo);
			String txJuros = (String) httpServletRequest.getParameter("txJuros" + valorTempo);
			String percMinEntrada = (String) httpServletRequest.getParameter("percMinEntrada" + valorTempo);
			String percTarMinImovel = (String) httpServletRequest.getParameter("percTarMinImovel" + valorTempo);
			String percVlReparcelado = (String) httpServletRequest.getParameter("percVlReparcelado" + valorTempo);
			String numeroMesesEntreParcelasStr = (String) httpServletRequest.getParameter("numeroMesesEntreParcelas" + valorTempo);
			String numeroParcelasALancarStr = (String) httpServletRequest.getParameter("numeroParcelasALancar" + valorTempo);
			String numeroMesesInicioCobrancaStr = (String) httpServletRequest.getParameter("numeroMesesInicioCobranca" + valorTempo);
			String numeroDiasVencimentoDaEntradaStr = (String) httpServletRequest
							.getParameter("numeroDiasVencimentoDaEntrada" + valorTempo);
			String percentualDescontoJurosMoraStr = (String) httpServletRequest.getParameter("percentualDescontoJurosMora" + valorTempo);
			String percentualDescontoMultaStr = (String) httpServletRequest.getParameter("percentualDescontoMulta" + valorTempo);
			String percentualDescontoCorrecaoMonetariaStr = (String) httpServletRequest.getParameter("percentualDescontoCorrecaoMonetaria"
							+ valorTempo);
			String percentualEntradaSugeridaStr = (String) httpServletRequest.getParameter("percentualEntradaSugerida" + valorTempo);

			// insere essas vari�veis no objeto ParcelamentoDescontoInatividade
			Short indicadorEntradaParcelamentoShort = null;
			if(!Util.isVazioOuBranco(indicadorEntradaParcelamento)){
				indicadorEntradaParcelamentoShort = Short.valueOf(indicadorEntradaParcelamento);
			}

			BigDecimal vlMinimoEntradaBigDecimal = null;
			if(!Util.isVazioOuBranco(vlMinimoEntrada)){
				vlMinimoEntradaBigDecimal = Util.formatarMoedaRealparaBigDecimal(vlMinimoEntrada);
			}

			BigDecimal taxaJuros = null;
			if(!Util.isVazioOuBranco(txJuros)){
				taxaJuros = Util.formatarMoedaRealparaBigDecimal(txJuros);
			}else if(sessao.getAttribute("desabilitarTaxaJuros") != null
							&& sessao.getAttribute("desabilitarTaxaJuros").toString().equals("true")){

				taxaJuros = Util.formatarMoedaRealparaBigDecimal("0,00");
			}

			BigDecimal percentualMinimoEntrada = null;
			if(!Util.isVazioOuBranco(percMinEntrada)){
				percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(percMinEntrada);
			}

			BigDecimal percentualTarifaMinimaImovel = null;
			if(!Util.isVazioOuBranco(percTarMinImovel)){
				percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal(percTarMinImovel);
			}

			BigDecimal percentualValorReparcelado = null;
			if(!Util.isVazioOuBranco(percVlReparcelado)){
				percentualValorReparcelado = Util.formatarMoedaRealparaBigDecimal(percVlReparcelado);
			}

			// Meses entre Parcelas
			Integer numeroMesesEntreParcelas = null;
			if(!Util.isVazioOuBranco(numeroMesesEntreParcelasStr)){
				numeroMesesEntreParcelas = Util.converterStringParaInteger(numeroMesesEntreParcelasStr);
			}

			// Parcelas a Lan�ar
			Integer numeroParcelasALancar = null;
			if(!Util.isVazioOuBranco(numeroParcelasALancarStr)){
				numeroParcelasALancar = Util.converterStringParaInteger(numeroParcelasALancarStr);
			}

			// Meses para In�cio da Cobran�a
			Integer numeroMesesInicioCobranca = null;
			if(!Util.isVazioOuBranco(numeroMesesInicioCobrancaStr)){
				numeroMesesInicioCobranca = Util.converterStringParaInteger(numeroMesesInicioCobrancaStr);
			}

			// Dias Vencimento da Entrada
			Integer numeroDiasVencimentoDaEntrada = null;
			if(!Util.isVazioOuBranco(numeroDiasVencimentoDaEntradaStr)){
				numeroDiasVencimentoDaEntrada = Util.converterStringParaInteger(numeroDiasVencimentoDaEntradaStr);
			}

			// Percentual de Desconto em Juros Mora
			BigDecimal percentualDescontoJurosMora = null;
			if(!Util.isVazioOuBranco(percentualDescontoJurosMoraStr)){
				percentualDescontoJurosMora = Util.formatarMoedaRealparaBigDecimal(percentualDescontoJurosMoraStr);
			}

			// Percentual de Desconto em Multa
			BigDecimal percentualDescontoMulta = null;
			if(!Util.isVazioOuBranco(percentualDescontoMultaStr)){
				percentualDescontoMulta = Util.formatarMoedaRealparaBigDecimal(percentualDescontoMultaStr);
			}

			// Percentual de Desconto em Corre��o Monet�ria
			BigDecimal percentualDescontoCorrecaoMonetaria = null;
			if(!Util.isVazioOuBranco(percentualDescontoCorrecaoMonetariaStr)){
				percentualDescontoCorrecaoMonetaria = Util.formatarMoedaRealparaBigDecimal(percentualDescontoCorrecaoMonetariaStr);
			}
			BigDecimal percentualEntradaSugerida = null;
			if(!Util.isVazioOuBranco(percentualEntradaSugeridaStr)){
				percentualEntradaSugerida = Util.formatarMoedaRealparaBigDecimal(percentualEntradaSugeridaStr);
			}

			parcelamentoQuantidadePrestacao.setIndicadorEntradaParcelamento(indicadorEntradaParcelamentoShort);
			parcelamentoQuantidadePrestacao.setValorMinimoEntrada(vlMinimoEntradaBigDecimal);
			parcelamentoQuantidadePrestacao.setTaxaJuros(taxaJuros);
			parcelamentoQuantidadePrestacao.setPercentualMinimoEntrada(percentualMinimoEntrada);
			parcelamentoQuantidadePrestacao.setPercentualTarifaMinimaImovel(percentualTarifaMinimaImovel);
			parcelamentoQuantidadePrestacao.setPercentualValorReparcelado(percentualValorReparcelado);
			parcelamentoQuantidadePrestacaoHelper.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacao);
			parcelamentoQuantidadePrestacao.setNumeroMesesEntreParcelas(numeroMesesEntreParcelas);
			parcelamentoQuantidadePrestacao.setNumeroParcelasALancar(numeroParcelasALancar);
			parcelamentoQuantidadePrestacao.setNumeroMesesInicioCobranca(numeroMesesInicioCobranca);
			parcelamentoQuantidadePrestacao.setNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntrada);
			parcelamentoQuantidadePrestacao.setPercentualDescontoJurosMora(percentualDescontoJurosMora);
			parcelamentoQuantidadePrestacao.setPercentualDescontoMulta(percentualDescontoMulta);
			parcelamentoQuantidadePrestacao.setPercentualDescontoCorrecaoMonetaria(percentualDescontoCorrecaoMonetaria);
			parcelamentoQuantidadePrestacao.setPercentualEntradaSugerida(percentualEntradaSugerida);

			// sessao.setAttribute("collectionParcelamentoQuantidadePrestacao",collectionParcelamentoQuantidadePrestacao);
		}

		validacaoFinal(collectionParcelamentoQuantidadePrestacaoHelper, Fachada.getInstancia());

	}

	private void atualizaColecaoNaSessao(HttpSession sessao, HttpServletRequest httpServletRequest){

		// collectionParcelamentoQuantidadePrestacao
		if(sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper") != null
						&& !sessao.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper").equals("")){

			Collection collectionParcelamentoQuantidadePrestacaoHelper = (Collection) sessao
							.getAttribute("collectionParcelamentoQuantidadePrestacaoHelper");

			Iterator iterator = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

			while(iterator.hasNext()){
				ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iterator
								.next();

				ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = parcelamentoQuantidadePrestacaoHelper
								.getParcelamentoQuantidadePrestacao();

				long valorTempo = parcelamentoQuantidadePrestacao.getUltimaAlteracao().getTime();

				// cria as vari�veis para recuperar os par�metros do request e jogar
				// no objeto ParcelamentoQuantidadePrestacao
				String indicadorEntradaParcelamento = (String) httpServletRequest.getParameter("indicadorEntradaParcelamento" + valorTempo);
				String vlMinimoEntrada = (String) httpServletRequest.getParameter("vlMinimoEntrada" + valorTempo);
				String txJuros = (String) httpServletRequest.getParameter("txJuros" + valorTempo);
				String percMinEntrada = (String) httpServletRequest.getParameter("percMinEntrada" + valorTempo);
				String percTarMinImovel = (String) httpServletRequest.getParameter("percTarMinImovel" + valorTempo);
				String percVlReparcelado = (String) httpServletRequest.getParameter("percVlReparcelado" + valorTempo);
				String numeroMesesEntreParcelasStr = (String) httpServletRequest.getParameter("numeroMesesEntreParcelas" + valorTempo);
				String numeroParcelasALancarStr = (String) httpServletRequest.getParameter("numeroParcelasALancar" + valorTempo);
				String numeroMesesInicioCobrancaStr = (String) httpServletRequest.getParameter("numeroMesesInicioCobranca" + valorTempo);
				String numeroDiasVencimentoDaEntradaStr = (String) httpServletRequest.getParameter("numeroDiasVencimentoDaEntrada"
								+ valorTempo);
				String percentualDescontoJurosMoraStr = (String) httpServletRequest
								.getParameter("percentualDescontoJurosMora" + valorTempo);
				String percentualDescontoMultaStr = (String) httpServletRequest.getParameter("percentualDescontoMulta" + valorTempo);
				String percentualDescontoCorrecaoMonetariaStr = (String) httpServletRequest
								.getParameter("percentualDescontoCorrecaoMonetaria" + valorTempo);
				String percentualEntradaSugeridaStr = (String) httpServletRequest.getParameter("percentualEntradaSugerida" + valorTempo);

				// insere essas vari�veis no objeto ParcelamentoQuantidadePrestacao
				Short indicadorEntradaParcelamentoShort = null;
				if(!Util.isVazioOuBranco(indicadorEntradaParcelamento)){
					indicadorEntradaParcelamentoShort = Short.valueOf(indicadorEntradaParcelamento);
				}

				BigDecimal vlMinimoEntradaBigDecimal = null;
				if(!Util.isVazioOuBranco(vlMinimoEntrada)){
					vlMinimoEntradaBigDecimal = Util.formatarMoedaRealparaBigDecimal(vlMinimoEntrada);
				}

				BigDecimal taxaJuros = null;
				if(!Util.isVazioOuBranco(txJuros)){
					taxaJuros = Util.formatarMoedaRealparaBigDecimal(txJuros);
				}else if(sessao.getAttribute("desabilitarTaxaJuros") != null
								&& sessao.getAttribute("desabilitarTaxaJuros").toString().equals("true")){

					taxaJuros = Util.formatarMoedaRealparaBigDecimal("0,00");
				}

				BigDecimal percentualMinimoEntrada = null;
				if(!Util.isVazioOuBranco(percMinEntrada)){
					percentualMinimoEntrada = Util.formatarMoedaRealparaBigDecimal(percMinEntrada);
				}

				BigDecimal percentualTarifaMinimaImovel = null;
				if(!Util.isVazioOuBranco(percTarMinImovel)){
					percentualTarifaMinimaImovel = Util.formatarMoedaRealparaBigDecimal(percTarMinImovel);
				}

				BigDecimal percentualValorReparcelado = null;
				if(!Util.isVazioOuBranco(percVlReparcelado)){
					percentualValorReparcelado = Util.formatarMoedaRealparaBigDecimal(percVlReparcelado);
				}

				// Meses entre Parcelas
				Integer numeroMesesEntreParcelas = null;
				if(!Util.isVazioOuBranco(numeroMesesEntreParcelasStr)){
					numeroMesesEntreParcelas = Util.converterStringParaInteger(numeroMesesEntreParcelasStr);
				}

				// Parcelas a Lan�ar
				Integer numeroParcelasALancar = null;
				if(!Util.isVazioOuBranco(numeroParcelasALancarStr)){
					numeroParcelasALancar = Util.converterStringParaInteger(numeroParcelasALancarStr);
				}

				// Meses para In�cio da Cobran�a
				Integer numeroMesesInicioCobranca = null;
				if(!Util.isVazioOuBranco(numeroMesesInicioCobrancaStr)){
					numeroMesesInicioCobranca = Util.converterStringParaInteger(numeroMesesInicioCobrancaStr);
				}


				// Dias Vencimento da Entrada
				Integer numeroDiasVencimentoDaEntrada = null;
				if(!Util.isVazioOuBranco(numeroDiasVencimentoDaEntradaStr)){
					numeroDiasVencimentoDaEntrada = Util.converterStringParaInteger(numeroDiasVencimentoDaEntradaStr);
				}

				BigDecimal percentualDescontoJurosMora = null;
				if(!Util.isVazioOuBranco(percentualDescontoJurosMoraStr)){
					percentualDescontoJurosMora = Util.formatarMoedaRealparaBigDecimal(percentualDescontoJurosMoraStr);
				}

				BigDecimal percentualDescontoMulta = null;
				if(!Util.isVazioOuBranco(percentualDescontoMultaStr)){
					percentualDescontoMulta = Util.formatarMoedaRealparaBigDecimal(percentualDescontoMultaStr);
				}

				BigDecimal percentualDescontoCorrecaoMonetaria = null;
				if(!Util.isVazioOuBranco(percentualDescontoCorrecaoMonetariaStr)){
					percentualDescontoCorrecaoMonetaria = Util.formatarMoedaRealparaBigDecimal(percentualDescontoCorrecaoMonetariaStr);
				}

				BigDecimal percentualEntradaSugerida = null;
				if(!Util.isVazioOuBranco(percentualEntradaSugeridaStr)){
					percentualEntradaSugerida = Util.formatarMoedaRealparaBigDecimal(percentualEntradaSugeridaStr);
				}

				parcelamentoQuantidadePrestacao.setIndicadorEntradaParcelamento(indicadorEntradaParcelamentoShort);
				parcelamentoQuantidadePrestacao.setValorMinimoEntrada(vlMinimoEntradaBigDecimal);
				parcelamentoQuantidadePrestacao.setTaxaJuros(taxaJuros);
				parcelamentoQuantidadePrestacao.setPercentualMinimoEntrada(percentualMinimoEntrada);
				parcelamentoQuantidadePrestacao.setPercentualTarifaMinimaImovel(percentualTarifaMinimaImovel);
				parcelamentoQuantidadePrestacao.setPercentualValorReparcelado(percentualValorReparcelado);
				parcelamentoQuantidadePrestacaoHelper.setParcelamentoQuantidadePrestacao(parcelamentoQuantidadePrestacao);
				parcelamentoQuantidadePrestacao.setNumeroMesesEntreParcelas(numeroMesesEntreParcelas);
				parcelamentoQuantidadePrestacao.setNumeroParcelasALancar(numeroParcelasALancar);
				parcelamentoQuantidadePrestacao.setNumeroMesesInicioCobranca(numeroMesesInicioCobranca);
				parcelamentoQuantidadePrestacao.setNumeroDiasVencimentoDaEntrada(numeroDiasVencimentoDaEntrada);
				parcelamentoQuantidadePrestacao.setPercentualDescontoJurosMora(percentualDescontoJurosMora);
				parcelamentoQuantidadePrestacao.setPercentualDescontoMulta(percentualDescontoMulta);
				parcelamentoQuantidadePrestacao.setPercentualDescontoCorrecaoMonetaria(percentualDescontoCorrecaoMonetaria);
				parcelamentoQuantidadePrestacao.setPercentualEntradaSugerida(percentualEntradaSugerida);
			}
		}

	}

	private void validacaoFinal(Collection collectionParcelamentoQuantidadePrestacaoHelper, Fachada fachada){

		// Recupera o percentual m�nimo de entrada permitido para parcelamento
		BigDecimal percentualEntradaMinimaParcelamento = BigDecimal.ZERO;

		// Par�metro que cont�m os tipos de financiamentos de parcelamento
		String parametroEntradaMinimaParcelamento = null;

		try{
			// Recupera os valores do par�metro
			parametroEntradaMinimaParcelamento = (String) ParametroCobranca.P_PERCENTUAL_ENTRADA_MINIMA_PARCELAMENTO.executar();

			percentualEntradaMinimaParcelamento = BigDecimal.valueOf(Double.valueOf(parametroEntradaMinimaParcelamento).doubleValue());

		}catch(ControladorException e){
			e.printStackTrace();
		}

		Iterator iteratorParcelamentoQuantidadePrestacaoHelper = collectionParcelamentoQuantidadePrestacaoHelper.iterator();

		while(iteratorParcelamentoQuantidadePrestacaoHelper.hasNext()){

			ParcelamentoQuantidadePrestacaoHelper parcelamentoQuantidadePrestacaoHelper = (ParcelamentoQuantidadePrestacaoHelper) iteratorParcelamentoQuantidadePrestacaoHelper
							.next();

			ParcelamentoQuantidadePrestacao parcelamentoQuantidadePrestacao = parcelamentoQuantidadePrestacaoHelper
							.getParcelamentoQuantidadePrestacao();

			if(parcelamentoQuantidadePrestacao.getQuantidadeMaximaPrestacao() == null){
				throw new ActionServletException(
				// Informe Quantidade M�xima de Presta��es
								"atencao.required", null, " Quantidade M�xima de Presta��es");
			}

			if(parcelamentoQuantidadePrestacao.getTaxaJuros() == null){

				throw new ActionServletException(
				// Informe Taxa de Juros a.m
								"atencao.required", null, " Taxa de Juros a.m");
			}

			// Dias Vencimento da Entrada
			Integer numeroDiasVencimentoDaEntrada = parcelamentoQuantidadePrestacao.getNumeroDiasVencimentoDaEntrada();

			if(Util.isVazioOuBranco(numeroDiasVencimentoDaEntrada)){
				throw new ActionServletException("atencao.required", null, " Dias Vencimento da Entrada");
			}

			
			// Se n�o foi informada Entrada por Faixa de Valor, valida o preenchimento da entrada
			// �nica de parcelamento
			if(Util.isVazioOrNulo(parcelamentoQuantidadePrestacaoHelper.getCollectionParcelamentoFaixaValor())){

				if(parcelamentoQuantidadePrestacao.getPercentualMinimoEntrada() == null
								&& parcelamentoQuantidadePrestacao.getValorMinimoEntrada() == null
								&& parcelamentoQuantidadePrestacao.getPercentualValorReparcelado() == null){

					throw new ActionServletException(
									// Informe Percentual M�nimo de Entrada
									"atencao.required", null,
									" Valor M�nimo de Entrada, Percentual M�nimo de Entrada ou Percentual de Valor Reparcelado");
				}
			}

			if(parcelamentoQuantidadePrestacao.getPercentualMinimoEntrada() != null){
				// [FS0005]Verificar percentual m�nimo de entrada
				BigDecimal percentualMinimoEntrada = parcelamentoQuantidadePrestacao.getPercentualMinimoEntrada();

				// Caso o percentual m�nimo de entrada seja menor que o percentual m�nimo de entrada
				// permitido para parcelamento exibir a mensagem
				if(percentualEntradaMinimaParcelamento.compareTo(percentualMinimoEntrada) > 0){
					throw new ActionServletException("atencao.percentual_min_entrada_inferior_percentual_min_parcelamento", null, ""
									+ percentualEntradaMinimaParcelamento);
				}
			}
		}
	}
}
