/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * GSANPCG
 * Andr� Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Jos� Gilberto de Fran�a Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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

package gcom.cobranca.parcelamento;

import gcom.arrecadacao.pagamento.FiltroGuiaPagamentoPrestacaoHistorico;
import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico;
import gcom.cobranca.FiltroResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.debito.*;
import gcom.relatorio.cobranca.parcelamento.GeradorRelatorioParcelamentoException;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

import org.apache.log4j.Logger;

/**
 * Controlador Parcelamento
 * 
 * @author Luciano Galv�o
 * @date 21/11/2013
 */
public class ControladorParcelamento
				implements SessionBean, IControladorParcelamento {

	protected static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(ControladorParcelamento.class);

	SessionContext sessionContext;

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @exception CreateException
	 *                Descri��o da exce��o
	 */
	public void ejbCreate() throws CreateException{

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descri��o do m�todo>>
	 */
	public void ejbPassivate(){

	}

	/**
	 * Seta o valor de sessionContext
	 * 
	 * @param sessionContext
	 *            O novo valor de sessionContext
	 */
	public void setSessionContext(SessionContext sessionContext){

		this.sessionContext = sessionContext;
	}

	/**
	 * Identifica o tipo de d�bito de uma Conta
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoConta(ContaHistorico contaHistorico) throws ControladorException{

		Object[] retorno = null;
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		switch(pModeloDefinicaoValores.intValue()){
			case 1: // Modelo 1
				retorno = identificarTipoDebitoContaModelo1(contaHistorico);
				break;
			case 2: // Modelo 2
				retorno = identificarTipoDebitoContaModelo2(contaHistorico);
				break;
		}

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito de uma Conta - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoContaModelo1(ContaHistorico contaHistorico) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Short indicadorContaCobrancaAdministrativa = null;
		Short indicadorContaRemuneraCobrancaAdministrativa = null;

		if(contaHistorico != null){
			indicadorContaCobrancaAdministrativa = contaHistorico.getIndicadorCobrancaAdministrativa();
			indicadorContaRemuneraCobrancaAdministrativa = contaHistorico.getIndicadorRemuneraCobrancaAdministrativa();
		}

		if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.NAO)
						&& Util.compararShort(indicadorContaRemuneraCobrancaAdministrativa, ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

		}else{
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito de uma Conta - Modelo 2
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoContaModelo2(ContaHistorico contaHistorico) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Short indicadorContaCobrancaAdministrativa = null;

		if(contaHistorico != null){
			indicadorContaCobrancaAdministrativa = contaHistorico.getIndicadorCobrancaAdministrativa();
		}

		if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

		}else if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.SIM)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}


	/**
	 * Retorna o tipo de d�bito que deve ser considerado para um dado DebitoCobradoHistorico
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoDebitoCobrado(ContaValoresHelper contaHelper, DebitoCobradoHistorico debitoCobradoHistorico)
					throws ControladorException{

		Object[] retorno = null;
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		switch(pModeloDefinicaoValores.intValue()){
			case 1: // Modelo 1
				retorno = identificarTipoDebitoDebitoCobradoModelo1(contaHelper, debitoCobradoHistorico);
				break;
			case 2: // Modelo 2
				retorno = identificarTipoDebitoDebitoCobradoModelo2(contaHelper, debitoCobradoHistorico);
				break;
		}

		return retorno;
	}

	/**
	 * Retorna o tipo de d�bito que deve ser considerado para um dado DebitoCobradoHistorico -
	 * Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoDebitoCobradoModelo1(ContaValoresHelper contaHelper, DebitoCobradoHistorico debitoCobradoHistorico)
					throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> tiposDebitoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativa(true);

		Short indicadorContaCobrancaAdministrativa = null;
		Short indicadorContaRemuneraCobrancaAdministrativa = null;

		if(contaHelper != null && contaHelper.getConta() != null){
			indicadorContaCobrancaAdministrativa = contaHelper.getConta().getIndicadorCobrancaAdministrativa();
			indicadorContaRemuneraCobrancaAdministrativa = contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa();
		}

		if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.NAO)
						&& Util.compararShort(indicadorContaRemuneraCobrancaAdministrativa, ConstantesSistema.NAO)){

			// Se DBTP_ID n�o est� contido nos tipos de d�bito de Cobran�a Administrativa
			if(!tiposDebitoCobrancaAdministrativa.contains(debitoCobradoHistorico.getDebitoTipo().getId())){

				if(Util.compararShort(debitoCobradoHistorico.getIndicadorRemuneraCobrancaAdministrativa(), ConstantesSistema.NAO)){

					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

				}else{
					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());

					// [SB0041] - Determinar Indicador de Remunera��o
					if(!verificarTipoDebitoDeAcrescimosOuServicosEspeciais(debitoCobradoHistorico.getDebitoTipo().getId())){
						indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;

					}else{
						indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
					}
				}

			}else{
				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
				indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
			}

		}else{
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Retorna o tipo de d�bito que deve ser considerado para um dado DebitoCobradoHistorico -
	 * Modelo 2
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoDebitoCobradoModelo2(ContaValoresHelper contaHelper, DebitoCobradoHistorico debitoCobradoHistorico)
					throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> tiposDebitoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativaModelo2();

		Short indicadorContaCobrancaAdministrativa = null;
		Short indicadorContaRemuneraCobrancaAdministrativa = null;

		if(contaHelper != null && contaHelper.getConta() != null){
			indicadorContaCobrancaAdministrativa = contaHelper.getConta().getIndicadorCobrancaAdministrativa();
			indicadorContaRemuneraCobrancaAdministrativa = contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa();
		}

		if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.NAO)){

			if(Util.compararShort(indicadorContaRemuneraCobrancaAdministrativa, ConstantesSistema.NAO)){

				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

			}else if(Util.compararShort(indicadorContaRemuneraCobrancaAdministrativa, ConstantesSistema.SIM)){

				// Se DBTP_ID n�o est� contido nos tipos de d�bito de Cobran�a Administrativa
				if(!tiposDebitoCobrancaAdministrativa.contains(debitoCobradoHistorico.getDebitoTipo().getId())){

					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

				}else{
					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
					indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
				}
			}

		}else if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.SIM)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito a ser considerado para um D�bito a Cobrar
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	public Object[] identificarTipoDebitoDebitoACobrar(DebitoACobrarHistorico debitoACobrarHistorico) throws ControladorException{

		Object[] retorno = null;
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		switch(pModeloDefinicaoValores.intValue()){
			case 1: // Modelo 1
				retorno = identificarTipoDebitoDebitoACobrarModelo1(debitoACobrarHistorico);
				break;
			case 2: // Modelo 2
				retorno = identificarTipoDebitoDebitoACobrarModelo2(debitoACobrarHistorico);
				break;
		}

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito a ser considerado para um D�bito a Cobrar - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	private Object[] identificarTipoDebitoDebitoACobrarModelo1(DebitoACobrarHistorico debitoACobrarHistorico) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> colecaoFinanciamentosTiposParcelamento = getTiposParcelamentoFinanciamento();
		Collection tiposDebitoParcelamentoNormal = getTiposDebitoParcelamentoNormal(false);
		Collection tiposDebitoParcelamentoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativa(false);

		if(colecaoFinanciamentosTiposParcelamento.contains(debitoACobrarHistorico.getFinanciamentoTipo().getId())){

			if(tiposDebitoParcelamentoNormal.contains(debitoACobrarHistorico.getDebitoTipo().getId())){

				if(debitoACobrarHistorico.getIndicadorRemuneraCobrancaAdministrativa() != null
								&& debitoACobrarHistorico.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.NAO)){

					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO.executar());

				}else{

					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
									.executar());
					indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
				}

			}else if(tiposDebitoParcelamentoCobrancaAdministrativa.contains(debitoACobrarHistorico.getDebitoTipo().getId())){

				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
								.executar());
				indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
			}

		}else if(!debitoACobrarHistorico.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_FINANCIAMENTO.executar());

			if(debitoACobrarHistorico.getIndicadorRemuneraCobrancaAdministrativa() != null
							&& debitoACobrarHistorico.getIndicadorRemuneraCobrancaAdministrativa().equals(ConstantesSistema.SIM)){

				// [SB0041] - Determinar Indicador de Remunera��o
				if(!verificarTipoDebitoDeAcrescimosOuServicosEspeciais(debitoACobrarHistorico.getDebitoTipo().getId())){
					indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;

				}else{
					indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
				}
			}
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito a ser considerado para um D�bito a Cobrar - Modelo 2
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	private Object[] identificarTipoDebitoDebitoACobrarModelo2(DebitoACobrarHistorico debitoACobrarHistorico) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> colecaoFinanciamentosTiposParcelamento = getTiposParcelamentoFinanciamento();
		Collection tiposDebitoParcelamentoNormal = getTiposDebitoParcelamentoNormal(true);
		Collection tiposDebitoParcelamentoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativaModelo2();

		if(colecaoFinanciamentosTiposParcelamento.contains(debitoACobrarHistorico.getFinanciamentoTipo().getId())){

			if(tiposDebitoParcelamentoNormal.contains(debitoACobrarHistorico.getDebitoTipo().getId())){

				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO.executar());

			}else if(tiposDebitoParcelamentoCobrancaAdministrativa.contains(debitoACobrarHistorico.getDebitoTipo().getId())){

				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
								.executar());
				indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
			}

		}else if(!debitoACobrarHistorico.getDebitoTipo().getId().equals(DebitoTipo.JUROS_SOBRE_PARCELAMENTO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_FINANCIAMENTO.executar());
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito a ser considerado para uma Guia de Pagamento
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	public Object[] identificarTipoDebitoGuiaPagamento(Integer idGuiaPagamento, Short numeroPrestacao, Integer itemLancamentoContabilId)
					throws ControladorException{

		Short indicadorCobrancaAdministrativa = null;
		Short indicadorRemuneraCobrancaAdministrativa = null;

		Collection<Integer> idsDebitoTipoPrestacao = new ArrayList<Integer>();

		Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico = this
						.pesquisarGuiaPagamentoPrestacoesHistoricoPeloLancamentoContabil(idGuiaPagamento, numeroPrestacao,
										itemLancamentoContabilId);

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacaoHistorico)){
			DebitoTipo debitoTipoPrestacao = null;
			Integer idDebitoTipoPrestacao = null;

			for(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico : colecaoGuiaPagamentoPrestacaoHistorico){
				indicadorCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorCobrancaAdministrativa();
				indicadorRemuneraCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorRemuneraCobrancaAdministrativa();

				debitoTipoPrestacao = guiaPagamentoPrestacaoHistorico.getDebitoTipo();

				if(debitoTipoPrestacao != null){
					idDebitoTipoPrestacao = debitoTipoPrestacao.getId();
					idsDebitoTipoPrestacao.add(idDebitoTipoPrestacao);
				}
			}
		}

		Object[] retorno = null;
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		switch(pModeloDefinicaoValores.intValue()){
			case 1: // Modelo 1
				retorno = this.identificarTipoDebitoGuiaPagamentoModelo1(indicadorCobrancaAdministrativa,
								indicadorRemuneraCobrancaAdministrativa, idsDebitoTipoPrestacao);
				break;
			case 2: // Modelo 2
				retorno = this.identificarTipoDebitoGuiaPagamentoModelo2(indicadorCobrancaAdministrativa,
								indicadorRemuneraCobrancaAdministrativa, idsDebitoTipoPrestacao);
				break;
		}

		return retorno;

	}

	/**
	 * Identifica o tipo de d�bito a ser considerado para uma Guia de Pagamento - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	private Object[] identificarTipoDebitoGuiaPagamentoModelo1(Short indicadorCobrancaAdministrativa,
					Short indicadorRemuneraCobrancaAdministrativa, Collection<Integer> idsDebitoTipoPrestacao) throws ControladorException{

		Integer tipoDebito;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> tiposDebitoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativa(true);
		tiposDebitoCobrancaAdministrativa.add(DebitoTipo.ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA);

		// IndicadorCobrancaAdministrativa = NAO
		if(indicadorCobrancaAdministrativa != null && indicadorCobrancaAdministrativa.equals(ConstantesSistema.NAO)){

			// IndicadorRemuneraCobrancaAdministrativa = NAO
			if(indicadorRemuneraCobrancaAdministrativa != null && indicadorRemuneraCobrancaAdministrativa.equals(ConstantesSistema.NAO)){

				boolean encontrouDebitoCobrancaAdministrativa = false;

				for(Integer idDebitoTipo : idsDebitoTipoPrestacao){
					encontrouDebitoCobrancaAdministrativa = tiposDebitoCobrancaAdministrativa.contains(idDebitoTipo);

					if(encontrouDebitoCobrancaAdministrativa){
						break;
					}
				}

				// Tipo de d�bito n�o � de parcelamento de cobran�a administrativa, inclusive de
				// entrada de parcelamento
				if(!encontrouDebitoCobrancaAdministrativa){
					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO.executar());
				}else{
					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA
									.executar());
					indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
				}

				// IndicadorRemuneraCobrancaAdministrativa = SIM
			}else{

				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA
								.executar());

				// [SB0041] - Determinar Indicador de Remunera��o
				if(!verificarTipoDebitoDeAcrescimosOuServicosEspeciais(idsDebitoTipoPrestacao)){
					indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
				}else{
					indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
				}
			}

			// IndicadorCobrancaAdministrativa = SIM
		}else{
			tipoDebito = Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito a ser considerado para uma Guia de Pagamento - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	private Object[] identificarTipoDebitoGuiaPagamentoModelo2(Short indicadorCobrancaAdministrativa,
					Short indicadorRemuneraCobrancaAdministrativa, Collection<Integer> idsDebitoTipoPrestacao) throws ControladorException{

		Integer tipoDebito;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> tiposDebitoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativaModelo2();
		tiposDebitoCobrancaAdministrativa.add(DebitoTipo.ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA);

		// IndicadorCobrancaAdministrativa = NAO
		if(Util.compararShort(indicadorCobrancaAdministrativa, ConstantesSistema.NAO)){

			// IndicadorRemuneraCobrancaAdministrativa = NAO
			if(Util.compararShort(indicadorRemuneraCobrancaAdministrativa, ConstantesSistema.NAO)){

				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO.executar());

				// IndicadorRemuneraCobrancaAdministrativa = SIM
			}else{

				boolean encontrouDebitoCobrancaAdministrativa = false;

				for(Integer idDebitoTipo : idsDebitoTipoPrestacao){
					encontrouDebitoCobrancaAdministrativa = tiposDebitoCobrancaAdministrativa.contains(idDebitoTipo);

					if(encontrouDebitoCobrancaAdministrativa){
						break;
					}
				}

				// Tipo de d�bito n�o � de parcelamento de cobran�a administrativa, inclusive de
				// entrada de parcelamento
				if(!encontrouDebitoCobrancaAdministrativa){
					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO.executar());
				}else{
					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA
									.executar());
					indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
				}
			}

			// IndicadorCobrancaAdministrativa = SIM
		}else{
			tipoDebito = Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito dos acr�scimos de uma Conta
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoAcrescimosConta(ContaValoresHelper contaHelper) throws ControladorException{

		Object[] retorno = null;
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		switch(pModeloDefinicaoValores.intValue()){
			case 1: // Modelo 1
				retorno = identificarTipoDebitoAcrescimosContaModelo1(contaHelper);
				break;
			case 2: // Modelo 2
				retorno = identificarTipoDebitoAcrescimosContaModelo2(contaHelper);
				break;
		}

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito dos acr�scimos de uma Conta - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoAcrescimosContaModelo1(ContaValoresHelper contaHelper) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Short indicadorContaCobrancaAdministrativa = null;
		Short indicadorRemuneraCobrancaAdministrativa = null;

		if(contaHelper != null && contaHelper.getConta() != null){
			indicadorContaCobrancaAdministrativa = contaHelper.getConta().getIndicadorCobrancaAdministrativa();
			indicadorRemuneraCobrancaAdministrativa = contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa();
		}

		if(indicadorContaCobrancaAdministrativa != null && indicadorContaCobrancaAdministrativa.equals(ConstantesSistema.NAO)
						&& indicadorRemuneraCobrancaAdministrativa != null
						&& indicadorRemuneraCobrancaAdministrativa.equals(ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar());

		}else{

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
							.executar());
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito dos acr�scimos de uma Conta - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoAcrescimosContaModelo2(ContaValoresHelper contaHelper) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar());

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito de uma Guia de Pagamento
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoAcrescimosGuiaPagamento(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico)
					throws ControladorException{

		Object[] retorno = null;
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		switch(pModeloDefinicaoValores.intValue()){
			case 1: // Modelo 1
				retorno = identificarTipoDebitoAcrescimosGuiaPagamentoModelo1(guiaPagamentoPrestacaoHistorico);
				break;
			case 2: // Modelo 2
				retorno = identificarTipoDebitoAcrescimosGuiaPagamentoModelo2(guiaPagamentoPrestacaoHistorico);
				break;
		}

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito de uma Guia de Pagamento - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoAcrescimosGuiaPagamentoModelo1(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico)
					throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Short indicadorGuiaPagamentoCobrancaAdministrativa = null;
		Short indicadorRemuneraCobrancaAdministrativa = null;

		if(guiaPagamentoPrestacaoHistorico != null){
			indicadorGuiaPagamentoCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorCobrancaAdministrativa();
			indicadorRemuneraCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorRemuneraCobrancaAdministrativa();
		}

		if(indicadorGuiaPagamentoCobrancaAdministrativa != null
						&& indicadorGuiaPagamentoCobrancaAdministrativa.equals(ConstantesSistema.NAO)
						&& indicadorRemuneraCobrancaAdministrativa != null
						&& indicadorRemuneraCobrancaAdministrativa.equals(ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar());

		}else{

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
							.executar());
		}

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Identifica o tipo de d�bito de uma Guia de Pagamento - Modelo 1
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoAcrescimosGuiaPagamentoModelo2(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico)
					throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar());

		Object[] retorno = new Object[3];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;

		return retorno;
	}

	/**
	 * Inicia o valor do Indicador de Remunera��o Total, verificando se a conta � do tipo Entrada de
	 * parcelamento
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB1000] Gerar Valores Parcelamento por Tipo D�bito e Item Lan�amento Cont�bil - Modelo 1
	 * [SB1001] Gerar Valores Parcelamento por Tipo D�bito e Item Lan�amento Cont�bil - Modelo 2
	 * 
	 * @author Luciano Galv�o
	 * @date 25/11/2013
	 */
	public void iniciarIndicadorRemuneracaoTotal(Short[] indicadorTotalRemuneracaoCobrancaAdm, ContaValoresHelper contaHelper)
					throws ControladorException{

		Integer indicadorContaEP = contaHelper.getIndicadorContasDebito();
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		// Caso o indicador de entrada de parcelamento esteja marcado (EP)
		if(indicadorContaEP != null && indicadorContaEP.equals(Integer.valueOf(ConstantesSistema.SIM))){

			switch(pModeloDefinicaoValores.intValue()){
				case 1: // Modelo 1
					iniciarIndicadorRemuneracaoTotalModelo1(indicadorTotalRemuneracaoCobrancaAdm, contaHelper);
					break;

				case 2: // Modelo 2
					iniciarIndicadorRemuneracaoTotalModelo2(indicadorTotalRemuneracaoCobrancaAdm, contaHelper);
					break;
			}
		}
	}

	/**
	 * Inicia o valor do Indicador de Remunera��o Total, verificando se a conta � do tipo Entrada de
	 * parcelamento
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB1001] Gerar Valores Parcelamento por Tipo D�bito e Item Lan�amento Cont�bil - Modelo 2
	 * 
	 * @author Luciano Galv�o
	 * @date 25/11/2013
	 */
	private void iniciarIndicadorRemuneracaoTotalModelo2(Short[] indicadorTotalRemuneracaoCobrancaAdm, ContaValoresHelper contaHelper)
					throws ControladorException{

		// Caso a conta esteja marcada na cobran�a administrativa
		if(Util.compararShort(contaHelper.getConta().getIndicadorCobrancaAdministrativa(), ConstantesSistema.SIM)){

			// Atribuir o valor 1 (sim) ao Indicador de Remunera��o Total
			// Cobran�a Administrativa
			indicadorTotalRemuneracaoCobrancaAdm[0] = ConstantesSistema.SIM;

		}else{
			// Caso a conta esteja marcada como remuner�vel

			FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, contaHelper.getConta().getId()));
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.INDICADOR_REMUNERA_COBRANCA_ADM,
							ConstantesSistema.SIM));
			filtroDebitoCobrado.adicionarParametro(new ParametroSimplesColecao(FiltroDebitoCobrado.DEBITO_TIPO_ID,
							getTiposDebitoParcelamentoCobrancaAdministrativaModelo2()));

			// Pesquisa os d�bitos cobrados da conta que sejam parcelamento de
			// cobran�a administrativa
			Collection debitosCobradosCobrAdm = getControladorUtil().pesquisar(filtroDebitoCobrado, DebitoCobrado.class.getName());

			// e tenha algum d�bito cobrado de parcelamento de cobran�a
			// administrativa
			if(!Util.isVazioOrNulo(debitosCobradosCobrAdm)){

				// Atribuir o valor 1 (sim) ao Indicador de Remunera��o Total
				// Cobran�a Administrativa
				indicadorTotalRemuneracaoCobrancaAdm[0] = ConstantesSistema.SIM;
			}
		}
	}

	/**
	 * Inicia o valor do Indicador de Remunera��o Total, verificando se a conta � do tipo Entrada de
	 * parcelamento
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB1000] Gerar Valores Parcelamento por Tipo D�bito e Item Lan�amento Cont�bil - Modelo 1
	 * 
	 * @author Luciano Galv�o
	 * @date 25/11/2013
	 */
	private void iniciarIndicadorRemuneracaoTotalModelo1(Short[] indicadorTotalRemuneracaoCobrancaAdm, ContaValoresHelper contaHelper){

		// Caso a conta esteja marcada na cobran�a administrativa ou esteja
		// marcada como remuner�vel
		if(Util.compararShort(contaHelper.getConta().getIndicadorCobrancaAdministrativa(), ConstantesSistema.SIM)
						|| Util.compararShort(contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa(),
										ConstantesSistema.SIM)){

			// Atribuir o valor 1 (sim) ao Indicador de Remunera��o Total
			// Cobran�a Administrativa
			indicadorTotalRemuneracaoCobrancaAdm[0] = ConstantesSistema.SIM;
		}
	}

	/**
	 * Retorna os tipos de d�bito correspondentes a parcelamento de cobran�a administrativa,
	 * utilizados no UC0214 -
	 * Efetuar Parcelamento de D�bitos
	 * 
	 * @author Luciano Galvao
	 * @date 03/10/2012
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a parcelamento de cobran�a
	 *         administrativa
	 */
	public Collection<Integer> getTiposDebitoParcelamentoCobrancaAdministrativa(boolean consideraJuros) throws ControladorException{

		Collection<Integer> tiposDebito = new ArrayList<Integer>();

		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
						.executar()));
		if(consideraJuros){
			tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
							.executar()));
		}
		return tiposDebito;
	}

	/**
	 * Retorna os tipos de d�bito correspondentes a parcelamento de cobran�a administrativa,
	 * utilizados no UC0214 - Efetuar Parcelamento de D�bitos, SUB1001 (Modelo 2)
	 * 
	 * @author Luciano Galvao
	 * @date 22/11/2013
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a parcelamento de cobran�a
	 *         administrativa
	 */
	public Collection<Integer> getTiposDebitoParcelamentoCobrancaAdministrativaModelo2() throws ControladorException{

		Collection<Integer> tiposDebito = new ArrayList<Integer>();

		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA.executar()));

		return tiposDebito;
	}

	/**
	 * [UC0214] - Efetuar Parcelamento de D�bitos
	 * [SB0041] - Determinar Indicador de Remunera��o
	 * Retorna <true> se o tipo de d�bito � de acr�scimos de pontualidade ou de servi�os especiais
	 * Retorna <false> caso contr�rio
	 * 
	 * @author Luciano Galv�o
	 * @date 29/05/2013
	 */
	private boolean verificarTipoDebitoDeAcrescimosOuServicosEspeciais(Integer tipoDebitoId) throws ControladorException{

		Collection<Integer> tiposDebitoAcrescimoImpontualidade = getTiposDebitoAcrescimoImpontualidade();
		Collection<Integer> tiposDebitoServicosEspeciais = getTiposDebitoServicosEspeciais();

		return tiposDebitoAcrescimoImpontualidade.contains(tipoDebitoId) || tiposDebitoServicosEspeciais.contains(tipoDebitoId);

	}

	private boolean verificarTipoDebitoDeAcrescimosOuServicosEspeciais(Collection<Integer> idsDebitoTipo) throws ControladorException{

		boolean retorno = false;

		for(Integer idDebitoTipo : idsDebitoTipo){
			retorno = this.verificarTipoDebitoDeAcrescimosOuServicosEspeciais(idDebitoTipo);

			if(!retorno){
				break;
			}
		}

		return retorno;
	}

	/**
	 * Retorna os tipos de d�bito correspondentes a acr�scimos de impontualidade, utilizados no
	 * Rotina batch: Marcar Itens Remuner�veis por Cobran�a Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a acr�scimos de impontualidade
	 */
	public Collection<Integer> getTiposDebitoAcrescimoImpontualidade() throws ControladorException{

		Collection<Integer> tiposDebito = new ArrayList<Integer>();

		if(DebitoTipo.MULTA_IMPONTUALIDADE != null){
			tiposDebito.add(DebitoTipo.MULTA_IMPONTUALIDADE);
		}
		if(DebitoTipo.ATUALIZACAO_MONETARIA != null){
			tiposDebito.add(DebitoTipo.ATUALIZACAO_MONETARIA);
		}
		if(DebitoTipo.JUROS_MORA != null){
			tiposDebito.add(DebitoTipo.JUROS_MORA);
		}

		return tiposDebito;
	}

	/**
	 * Retorna os tipos de d�bito correspondentes a servi�os especiais, utilizados no Rotina batch:
	 * Marcar Itens Remuner�veis por Cobran�a Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a servi�os especiais
	 */
	public Collection<Integer> getTiposDebitoServicosEspeciais() throws ControladorException{

		Collection<Integer> tiposDebito = new ArrayList<Integer>();
		Integer tipoDebitoInt = null;

		String paramServicosEspeciaisCobrancaAdm = ParametroParcelamento.P_SERVICOS_ESPECIAIS_COBRANCA_ADMINISTRATIVA.executar();

		for(String tipoDebito : paramServicosEspeciaisCobrancaAdm.split(",")){
			tipoDebitoInt = Util.converterStringParaInteger(tipoDebito);
			if(tipoDebitoInt != null){
				tiposDebito.add(Integer.valueOf(tipoDebito));
			}
		}

		return tiposDebito;
	}

	/**
	 * Captura os tipos de parcelamento que representam um financiamento
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Collection<Integer> getTiposParcelamentoFinanciamento() throws ControladorException{

		// Par�metro que cont�m os tipos de financiamentos de parcelamento
		String[] parametroFinanciamentoTipoParcelamento = null;
		// Recupera os valores do par�metro
		parametroFinanciamentoTipoParcelamento = ((String) ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO.executar()).split(",");

		Collection<Integer> colecaoFinanciamentosTiposParcelamento = new ArrayList<Integer>();

		// carrega valores dos tipos de finaciamentos para parcelamento
		for(String parametroFinanciamentoTipo : parametroFinanciamentoTipoParcelamento){
			colecaoFinanciamentosTiposParcelamento.add(Integer.valueOf(parametroFinanciamentoTipo));
		}

		return colecaoFinanciamentosTiposParcelamento;
	}

	/**
	 * Retorna os tipos de d�bito correspondentes a parcelamento normal, utilizados no UC0214 -
	 * Efetuar Parcelamento de D�bitos
	 * 
	 * @author Luciano Galvao
	 * @date 03/10/2012
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a parcelamento normal
	 */
	private Collection<Integer> getTiposDebitoParcelamentoNormal(boolean consideraTipoDebitoAcrescimoCobrAdm) throws ControladorException{

		Collection<Integer> tiposDebito = new ArrayList<Integer>();
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar()));

		if(consideraTipoDebitoAcrescimoCobrAdm){
			tiposDebito.add(Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
											.executar()));
		}
		return tiposDebito;
	}

	/**
	 * Retorna os tipos de d�bito correspondentes a parcelamento. Utilizados nas Rotinas:
	 * Marcar Itens Remuner�veis por Cobran�a Administrativa
	 * e
	 * [UC0252] - Consultar Parcelamentos de D�bitos
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a parcelamento
	 */
	public Collection<Integer> getTiposDebitoParcelamento() throws ControladorException{

		Collection<Integer> tiposDebito = new ArrayList<Integer>();

		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
						.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
						.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_FINANCIAMENTO.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO.executar()));
		tiposDebito.add(Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_COBRANCA_ADMINISTRATIVA.executar()));
		return tiposDebito;
	}

	/**
	 * Retorna o valor do par�metro
	 * P_DEFINICAO_VALORES_PARCELAMENTO_TIPO_DEBITO_ITEM_LANCAMENTO_CONTABIL, que identifica o
	 * modelo de defini��o dos valores do parcelamento
	 * 
	 * @author Luciano Galv�o
	 * @date 25/11/2013
	 */
	private Integer getModeloDefinicaoValoresParcelamento() throws ControladorException{

		Integer pModeloDefinicaoValores = Util
						.obterInteger(ParametroParcelamento.P_DEFINICAO_VALORES_PARCELAMENTO_TIPO_DEBITO_ITEM_LANCAMENTO_CONTABIL
										.executar());

		if(pModeloDefinicaoValores == null || pModeloDefinicaoValores.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			throw new ControladorException("erro.parametro.sistema.valor.invalido", null,
							"P_DEFINICAO_VALORES_PARCELAMENTO_TIPO_DEBITO_ITEM_LANCAMENTO_CONTABIL");
		}
		return pModeloDefinicaoValores;
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		return (ControladorUtilLocal) ServiceLocator.getInstancia().getControladorUtil();
	}

	/**
	 * Obt�m o Layout de Resolu��o de Diretoria para o parcelamento de cobran�a administrativa
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Luciano Galv�o
	 * @date 27/11/2013
	 */
	public ResolucaoDiretoriaLayout obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa() throws GeradorRelatorioParcelamentoException{

		String idResolucaoDiretoriaLayout = null;

		try{

			idResolucaoDiretoriaLayout = ((String) ParametroCobranca.P_LAYOUT_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
							.executar(ExecutorParametrosCobranca.getInstancia()));

		}catch(ControladorException e){

			throw new GeradorRelatorioParcelamentoException(e.getMessage(), e);

		}

		FiltroResolucaoDiretoriaLayout filtroResolucaoDiretoriaLayout = new FiltroResolucaoDiretoriaLayout();
		filtroResolucaoDiretoriaLayout.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoriaLayout.ID,
						idResolucaoDiretoriaLayout));

		Collection<ResolucaoDiretoriaLayout> colecaoResolucaoDiretoriaLayout = Fachada.getInstancia().pesquisar(
						filtroResolucaoDiretoriaLayout, ResolucaoDiretoriaLayout.class.getName());

		ResolucaoDiretoriaLayout resolucaoDiretoriaLayoutPadrao = (ResolucaoDiretoriaLayout) Util
						.retonarObjetoDeColecao(colecaoResolucaoDiretoriaLayout);

		return resolucaoDiretoriaLayoutPadrao;

	}

	/**
	 * Pesquisa as presta��es a partir do id da guia de pagamento, o n�mero da presta��o e o id do
	 * lan�amento cont�bil
	 * 
	 * @author Hebert Falc�o
	 * @date 08/12/2013
	 */
	private Collection<GuiaPagamentoPrestacaoHistorico> pesquisarGuiaPagamentoPrestacoesHistoricoPeloLancamentoContabil(
					Integer idGuiaPagamento, Short numeroPrestacao, Integer itemLancamentoContabilId) throws ControladorException{

		Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico;
		FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
		filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
						FiltroGuiaPagamentoPrestacaoHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
		filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
						FiltroGuiaPagamentoPrestacaoHistorico.NUMERO_PRESTACAO, numeroPrestacao));
		filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
						FiltroGuiaPagamentoPrestacaoHistorico.LANCAMENTO_CONTABIL_ID, itemLancamentoContabilId));

		filtroGuiaPagamentoPrestacaoHistorico
						.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacaoHistorico.LANCAMENTO_CONTABIL);
		filtroGuiaPagamentoPrestacaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacaoHistorico.DEBITO_TIPO);

		colecaoGuiaPagamentoPrestacaoHistorico = this.getControladorUtil().pesquisar(filtroGuiaPagamentoPrestacaoHistorico,
						GuiaPagamentoPrestacaoHistorico.class.getName());

		return colecaoGuiaPagamentoPrestacaoHistorico;
	}

}