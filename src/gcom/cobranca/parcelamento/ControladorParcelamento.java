/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * GSANPCG
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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
 * @author Luciano Galvão
 * @date 21/11/2013
 */
public class ControladorParcelamento
				implements SessionBean, IControladorParcelamento {

	protected static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger.getLogger(ControladorParcelamento.class);

	SessionContext sessionContext;

	/**
	 * < <Descrição do método>>
	 * 
	 * @exception CreateException
	 *                Descrição da exceção
	 */
	public void ejbCreate() throws CreateException{

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbRemove(){

	}

	/**
	 * < <Descrição do método>>
	 */
	public void ejbActivate(){

	}

	/**
	 * < <Descrição do método>>
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
	 * Identifica o tipo de débito de uma Conta
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
	 * Identifica o tipo de débito de uma Conta - Modelo 1
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
	 * Identifica o tipo de débito de uma Conta - Modelo 2
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
	 * Retorna o tipo de débito que deve ser considerado para um dado DebitoCobradoHistorico
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
	 * Retorna o tipo de débito que deve ser considerado para um dado DebitoCobradoHistorico -
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

			// Se DBTP_ID não está contido nos tipos de débito de Cobrança Administrativa
			if(!tiposDebitoCobrancaAdministrativa.contains(debitoCobradoHistorico.getDebitoTipo().getId())){

				if(Util.compararShort(debitoCobradoHistorico.getIndicadorRemuneraCobrancaAdministrativa(), ConstantesSistema.NAO)){

					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

				}else{
					tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());

					// [SB0041] - Determinar Indicador de Remuneração
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
	 * Retorna o tipo de débito que deve ser considerado para um dado DebitoCobradoHistorico -
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

				// Se DBTP_ID não está contido nos tipos de débito de Cobrança Administrativa
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
	 * Identifica o tipo de débito a ser considerado para um Débito a Cobrar
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
	 * Identifica o tipo de débito a ser considerado para um Débito a Cobrar - Modelo 1
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

				// [SB0041] - Determinar Indicador de Remuneração
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
	 * Identifica o tipo de débito a ser considerado para um Débito a Cobrar - Modelo 2
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
	 * Identifica o tipo de débito a ser considerado para uma Guia de Pagamento
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
	 * Identifica o tipo de débito a ser considerado para uma Guia de Pagamento - Modelo 1
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

				// Tipo de débito não é de parcelamento de cobrança administrativa, inclusive de
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

				// [SB0041] - Determinar Indicador de Remuneração
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
	 * Identifica o tipo de débito a ser considerado para uma Guia de Pagamento - Modelo 1
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

				// Tipo de débito não é de parcelamento de cobrança administrativa, inclusive de
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
	 * Identifica o tipo de débito dos acréscimos de uma Conta
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
	 * Identifica o tipo de débito dos acréscimos de uma Conta - Modelo 1
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
	 * Identifica o tipo de débito dos acréscimos de uma Conta - Modelo 1
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
	 * Identifica o tipo de débito de uma Guia de Pagamento
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
	 * Identifica o tipo de débito de uma Guia de Pagamento - Modelo 1
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
	 * Identifica o tipo de débito de uma Guia de Pagamento - Modelo 1
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
	 * Inicia o valor do Indicador de Remuneração Total, verificando se a conta é do tipo Entrada de
	 * parcelamento
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB1000] Gerar Valores Parcelamento por Tipo Débito e Item Lançamento Contábil - Modelo 1
	 * [SB1001] Gerar Valores Parcelamento por Tipo Débito e Item Lançamento Contábil - Modelo 2
	 * 
	 * @author Luciano Galvão
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
	 * Inicia o valor do Indicador de Remuneração Total, verificando se a conta é do tipo Entrada de
	 * parcelamento
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB1001] Gerar Valores Parcelamento por Tipo Débito e Item Lançamento Contábil - Modelo 2
	 * 
	 * @author Luciano Galvão
	 * @date 25/11/2013
	 */
	private void iniciarIndicadorRemuneracaoTotalModelo2(Short[] indicadorTotalRemuneracaoCobrancaAdm, ContaValoresHelper contaHelper)
					throws ControladorException{

		// Caso a conta esteja marcada na cobrança administrativa
		if(Util.compararShort(contaHelper.getConta().getIndicadorCobrancaAdministrativa(), ConstantesSistema.SIM)){

			// Atribuir o valor 1 (sim) ao Indicador de Remuneração Total
			// Cobrança Administrativa
			indicadorTotalRemuneracaoCobrancaAdm[0] = ConstantesSistema.SIM;

		}else{
			// Caso a conta esteja marcada como remunerável

			FiltroDebitoCobrado filtroDebitoCobrado = new FiltroDebitoCobrado();
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.CONTA_ID, contaHelper.getConta().getId()));
			filtroDebitoCobrado.adicionarParametro(new ParametroSimples(FiltroDebitoCobrado.INDICADOR_REMUNERA_COBRANCA_ADM,
							ConstantesSistema.SIM));
			filtroDebitoCobrado.adicionarParametro(new ParametroSimplesColecao(FiltroDebitoCobrado.DEBITO_TIPO_ID,
							getTiposDebitoParcelamentoCobrancaAdministrativaModelo2()));

			// Pesquisa os débitos cobrados da conta que sejam parcelamento de
			// cobrança administrativa
			Collection debitosCobradosCobrAdm = getControladorUtil().pesquisar(filtroDebitoCobrado, DebitoCobrado.class.getName());

			// e tenha algum débito cobrado de parcelamento de cobrança
			// administrativa
			if(!Util.isVazioOrNulo(debitosCobradosCobrAdm)){

				// Atribuir o valor 1 (sim) ao Indicador de Remuneração Total
				// Cobrança Administrativa
				indicadorTotalRemuneracaoCobrancaAdm[0] = ConstantesSistema.SIM;
			}
		}
	}

	/**
	 * Inicia o valor do Indicador de Remuneração Total, verificando se a conta é do tipo Entrada de
	 * parcelamento
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB1000] Gerar Valores Parcelamento por Tipo Débito e Item Lançamento Contábil - Modelo 1
	 * 
	 * @author Luciano Galvão
	 * @date 25/11/2013
	 */
	private void iniciarIndicadorRemuneracaoTotalModelo1(Short[] indicadorTotalRemuneracaoCobrancaAdm, ContaValoresHelper contaHelper){

		// Caso a conta esteja marcada na cobrança administrativa ou esteja
		// marcada como remunerável
		if(Util.compararShort(contaHelper.getConta().getIndicadorCobrancaAdministrativa(), ConstantesSistema.SIM)
						|| Util.compararShort(contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa(),
										ConstantesSistema.SIM)){

			// Atribuir o valor 1 (sim) ao Indicador de Remuneração Total
			// Cobrança Administrativa
			indicadorTotalRemuneracaoCobrancaAdm[0] = ConstantesSistema.SIM;
		}
	}

	/**
	 * Retorna os tipos de débito correspondentes a parcelamento de cobrança administrativa,
	 * utilizados no UC0214 -
	 * Efetuar Parcelamento de Débitos
	 * 
	 * @author Luciano Galvao
	 * @date 03/10/2012
	 * @return Coleção de Ids de tipos de débito correspondentes a parcelamento de cobrança
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
	 * Retorna os tipos de débito correspondentes a parcelamento de cobrança administrativa,
	 * utilizados no UC0214 - Efetuar Parcelamento de Débitos, SUB1001 (Modelo 2)
	 * 
	 * @author Luciano Galvao
	 * @date 22/11/2013
	 * @return Coleção de Ids de tipos de débito correspondentes a parcelamento de cobrança
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
	 * [UC0214] - Efetuar Parcelamento de Débitos
	 * [SB0041] - Determinar Indicador de Remuneração
	 * Retorna <true> se o tipo de débito é de acréscimos de pontualidade ou de serviços especiais
	 * Retorna <false> caso contrário
	 * 
	 * @author Luciano Galvão
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
	 * Retorna os tipos de débito correspondentes a acréscimos de impontualidade, utilizados no
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Coleção de Ids de tipos de débito correspondentes a acréscimos de impontualidade
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
	 * Retorna os tipos de débito correspondentes a serviços especiais, utilizados no Rotina batch:
	 * Marcar Itens Remuneráveis por Cobrança Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Coleção de Ids de tipos de débito correspondentes a serviços especiais
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

		// Parâmetro que contém os tipos de financiamentos de parcelamento
		String[] parametroFinanciamentoTipoParcelamento = null;
		// Recupera os valores do parâmetro
		parametroFinanciamentoTipoParcelamento = ((String) ParametroParcelamento.P_FINANCIAMENTO_TIPO_PARCELAMENTO.executar()).split(",");

		Collection<Integer> colecaoFinanciamentosTiposParcelamento = new ArrayList<Integer>();

		// carrega valores dos tipos de finaciamentos para parcelamento
		for(String parametroFinanciamentoTipo : parametroFinanciamentoTipoParcelamento){
			colecaoFinanciamentosTiposParcelamento.add(Integer.valueOf(parametroFinanciamentoTipo));
		}

		return colecaoFinanciamentosTiposParcelamento;
	}

	/**
	 * Retorna os tipos de débito correspondentes a parcelamento normal, utilizados no UC0214 -
	 * Efetuar Parcelamento de Débitos
	 * 
	 * @author Luciano Galvao
	 * @date 03/10/2012
	 * @return Coleção de Ids de tipos de débito correspondentes a parcelamento normal
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
	 * Retorna os tipos de débito correspondentes a parcelamento. Utilizados nas Rotinas:
	 * Marcar Itens Remuneráveis por Cobrança Administrativa
	 * e
	 * [UC0252] - Consultar Parcelamentos de Débitos
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Coleção de Ids de tipos de débito correspondentes a parcelamento
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
	 * Retorna o valor do parâmetro
	 * P_DEFINICAO_VALORES_PARCELAMENTO_TIPO_DEBITO_ITEM_LANCAMENTO_CONTABIL, que identifica o
	 * modelo de definição dos valores do parcelamento
	 * 
	 * @author Luciano Galvão
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
	 * Obtém o Layout de Resolução de Diretoria para o parcelamento de cobrança administrativa
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Luciano Galvão
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
	 * Pesquisa as prestações a partir do id da guia de pagamento, o número da prestação e o id do
	 * lançamento contábil
	 * 
	 * @author Hebert Falcão
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