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
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.ControladorCobrancaLocal;
import gcom.cobranca.ControladorCobrancaLocalHome;
import gcom.cobranca.FiltroResolucaoDiretoriaLayout;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.debito.*;
import gcom.financeiro.lancamento.bean.LancamentoItemContabilParcelamentoHelper;
import gcom.relatorio.cobranca.parcelamento.GeradorRelatorioParcelamentoException;
import gcom.seguranca.ControladorPermissaoEspecialLocal;
import gcom.seguranca.ControladorPermissaoEspecialLocalHome;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesColecao;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;
import gcom.util.parametrizacao.cobranca.parcelamento.ParametroParcelamento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
			case 3: // Modelo 3
				retorno = identificarTipoDebitoContaModelo3(contaHistorico);
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
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(contaHistorico != null){
			indicadorContaCobrancaAdministrativa = contaHistorico.getIndicadorCobrancaAdministrativa();
			indicadorContaRemuneraCobrancaAdministrativa = contaHistorico.getIndicadorRemuneraCobrancaAdministrativa();
			numeroProcessoAdministrativoExecucaoFiscal = contaHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();
		}

		if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.NAO)
						&& Util.compararShort(indicadorContaRemuneraCobrancaAdministrativa, ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

		}else{
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(contaHistorico != null){
			indicadorContaCobrancaAdministrativa = contaHistorico.getIndicadorCobrancaAdministrativa();
			numeroProcessoAdministrativoExecucaoFiscal = contaHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();
		}

		if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

		}else if(Util.compararShort(indicadorContaCobrancaAdministrativa, ConstantesSistema.SIM)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito de uma Conta - Modelo 3
	 * 
	 * @author Saulo Lima
	 * @date 30/07/2014
	 */
	private Object[] identificarTipoDebitoContaModelo3(ContaHistorico contaHistorico) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Integer numeroProcessoAdministrativoExecucaoFiscal = ConstantesSistema.NUMERO_PROCESSO_ADM_EXEC_FISCAL_ZERO;
		Short indicadorDividaAtiva = null;
		Short indicadorExecucaoFiscal = null;

		if(contaHistorico != null){
			numeroProcessoAdministrativoExecucaoFiscal = contaHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();
			indicadorDividaAtiva = contaHistorico.getIndicadorDividaAtiva();
			indicadorExecucaoFiscal = contaHistorico.getIndicadorExecucaoFiscal();
		}

		if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.NAO)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());

		}else if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.SIM)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_DIVIDA_ATIVA.executar());

		}else if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.SIM)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.SIM)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_EXECUCAO_FISCAL.executar());

		}else{
			throw new ControladorException("erro.sistema");
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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
			case 3: // Modelo 3
				retorno = identificarTipoDebitoDebitoCobradoModelo3(contaHelper, debitoCobradoHistorico);
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
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(contaHelper != null && contaHelper.getConta() != null){
			indicadorContaCobrancaAdministrativa = contaHelper.getConta().getIndicadorCobrancaAdministrativa();
			indicadorContaRemuneraCobrancaAdministrativa = contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa();
		}

		numeroProcessoAdministrativoExecucaoFiscal = debitoCobradoHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();

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

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(contaHelper != null && contaHelper.getConta() != null){
			indicadorContaCobrancaAdministrativa = contaHelper.getConta().getIndicadorCobrancaAdministrativa();
			indicadorContaRemuneraCobrancaAdministrativa = contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa();
		}

		numeroProcessoAdministrativoExecucaoFiscal = debitoCobradoHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();

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

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Retorna o tipo de débito que deve ser considerado para um dado DebitoCobradoHistorico -
	 * Modelo 3
	 * 
	 * @author Saulo Lima
	 * @date 04/08/2014
	 */
	private Object[] identificarTipoDebitoDebitoCobradoModelo3(ContaValoresHelper contaHelper, DebitoCobradoHistorico debitoCobradoHistorico)
					throws ControladorException{

		Integer tipoDebito = null;

		Integer idDebitoTipo = debitoCobradoHistorico.getDebitoTipo().getId();

		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Integer numeroProcessoAdministrativoExecucaoFiscal = debitoCobradoHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		Collection<Integer> debitoTipoExecucaoFiscal = this.pesquisarDebitoTipoExecucaoFiscal();
		Collection<Integer> debitoTipoDividaAtiva = this.pesquisarDebitoTipoDividaAtiva();

		if(DebitoTipo.SUCUMBENCIA.equals(idDebitoTipo)){
			tipoDebito = DebitoTipo.SUCUMBENCIA;
		}else if(DebitoTipo.DILIGENCIAS.equals(idDebitoTipo)){
			tipoDebito = DebitoTipo.DILIGENCIAS;
		}else if(debitoTipoDividaAtiva.contains(idDebitoTipo)){
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_DIVIDA_ATIVA.executar());
			indicadorDividaAtiva = ConstantesSistema.SIM;
		}else if(debitoTipoExecucaoFiscal.contains(idDebitoTipo)){
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA_EXECUCAO_FISCAL.executar());
			indicadorExecucaoFiscal = ConstantesSistema.SIM;
		}else{
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CONTA.executar());
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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
			case 3: // Modelo 3
				retorno = identificarTipoDebitoDebitoACobrarModelo3(debitoACobrarHistorico);
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

		Integer numeroProcessoAdministrativoExecucaoFiscal = debitoACobrarHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

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

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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

		Integer numeroProcessoAdministrativoExecucaoFiscal = debitoACobrarHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

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

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito a ser considerado para um Débito a Cobrar - Modelo 3
	 * 
	 * @author Saulo Lima
	 * @date 30/07/2014
	 */
	private Object[] identificarTipoDebitoDebitoACobrarModelo3(DebitoACobrarHistorico debitoACobrarHistorico) throws ControladorException{

		Integer tipoDebito = null;
		Integer idDebitoTipo = debitoACobrarHistorico.getDebitoTipo().getId();

		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> colecaoFinanciamentosTiposParcelamento = this.getTiposParcelamentoFinanciamento();
		Collection<Integer> debitoTipoJurosParcelamento = this.pesquisarDebitoTipoJurosParcelamento();
		Collection<Integer> debitoTipoParcelamentoNormal = this.pesquisarDebitoTipoParcelamentoNormal();
		Collection<Integer> debitoTipoDividaAtiva = this.pesquisarDebitoTipoDividaAtiva();
		Collection<Integer> debitoTipoExecucaoFiscal = this.pesquisarDebitoTipoExecucaoFiscal();

		Integer numeroProcessoAdministrativoExecucaoFiscal = debitoACobrarHistorico.getNumeroProcessoAdministrativoExecucaoFiscal();
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(DebitoTipo.SUCUMBENCIA.equals(idDebitoTipo)){

			// lista de Item Contábil de Sucumbência
			tipoDebito = DebitoTipo.SUCUMBENCIA;

		}else if(!colecaoFinanciamentosTiposParcelamento.contains(debitoACobrarHistorico.getFinanciamentoTipo().getId())
						&& !DebitoTipo.JUROS_SOBRE_PARCELAMENTO.equals(idDebitoTipo) && !DebitoTipo.DILIGENCIAS.equals(idDebitoTipo)
						&& !debitoTipoJurosParcelamento.contains(idDebitoTipo)){

			// lista de Item Contábil de Débito a Cobrar-Financiamento
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_FINANCIAMENTO.executar());

		}else if(debitoTipoParcelamentoNormal.contains(idDebitoTipo)){

			// lista de Item Contábil de Débito a Cobrar-Parcelamento
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO.executar());

		}else if(debitoTipoDividaAtiva.contains(idDebitoTipo)){

			// Lista de Item Contábil de Débito a Cobrar-Parcelamento-DívidaAtiva
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_DIVIDA_ATIVA.executar());

		}else if(debitoTipoExecucaoFiscal.contains(idDebitoTipo) || DebitoTipo.DILIGENCIAS.equals(idDebitoTipo)){

			// Lista de Item Contábil de Débito a Cobrar-Parcelamento-ExecuçãoFiscal
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_PARCELAMENTO_EXECUCAO_FISCAL.executar());

		}else{
			throw new ControladorException("atencao.parametro_debito_tipo_nao_cadastrado");
		}

		// }else if(DebitoTipo.JUROS_SOBRE_PARCELAMENTO.equals(idDebitoTipo)){
		// // TODO Não fazer nada?
		// else{
		// // lista de Item Contábil de Débito a Cobrar-Financiamento
		// tipoDebito =
		// Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_FINANCIAMENTO.executar());
		// }

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito a ser considerado para uma Guia de Pagamento
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	public Object[] identificarTipoDebitoGuiaPagamento(Integer idGuiaPagamento, Short numeroPrestacao, Integer itemLancamentoContabilId,
					Integer idDebitoTipo)
					throws ControladorException{

		Short indicadorCobrancaAdministrativa = ConstantesSistema.NAO;
		Short indicadorRemuneraCobrancaAdministrativa = ConstantesSistema.NAO;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;

		// Collection<Integer> idsDebitoTipoPrestacao = new ArrayList<Integer>();

		Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico = this
						.pesquisarGuiaPagamentoPrestacoesHistoricoPeloLancamentoContabil(idGuiaPagamento, numeroPrestacao,
										itemLancamentoContabilId, idDebitoTipo);

		if(!Util.isVazioOrNulo(colecaoGuiaPagamentoPrestacaoHistorico)){
			// DebitoTipo debitoTipoPrestacao = null;
			// Integer idDebitoTipoPrestacao = null;

			for(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico : colecaoGuiaPagamentoPrestacaoHistorico){
				if(ConstantesSistema.SIM.equals(guiaPagamentoPrestacaoHistorico.getIndicadorCobrancaAdministrativa())){
					indicadorCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorCobrancaAdministrativa();
				}
				if(ConstantesSistema.SIM.equals(guiaPagamentoPrestacaoHistorico.getIndicadorRemuneraCobrancaAdministrativa())){
					indicadorRemuneraCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorRemuneraCobrancaAdministrativa();
				}
				if(ConstantesSistema.SIM.equals(guiaPagamentoPrestacaoHistorico.getIndicadorDividaAtiva())){
					indicadorDividaAtiva = guiaPagamentoPrestacaoHistorico.getIndicadorDividaAtiva();
				}
				if(ConstantesSistema.SIM.equals(guiaPagamentoPrestacaoHistorico.getIndicadorExecucaoFiscal())){
					indicadorExecucaoFiscal = guiaPagamentoPrestacaoHistorico.getIndicadorExecucaoFiscal();
				}

				// debitoTipoPrestacao = guiaPagamentoPrestacaoHistorico.getDebitoTipo();
				// if(debitoTipoPrestacao != null){
				// idDebitoTipoPrestacao = debitoTipoPrestacao.getId();
				// idsDebitoTipoPrestacao.add(idDebitoTipoPrestacao);
				// }

				numeroProcessoAdministrativoExecucaoFiscal = guiaPagamentoPrestacaoHistorico.getComp_id()
								.getNumeroProcessoAdministrativoExecucaoFiscal();
			}
		}

		Object[] retorno = null;
		Integer pModeloDefinicaoValores = getModeloDefinicaoValoresParcelamento();

		switch(pModeloDefinicaoValores.intValue()){
			case 1: // Modelo 1
				retorno = this.identificarTipoDebitoGuiaPagamentoModelo1(indicadorCobrancaAdministrativa,
								indicadorRemuneraCobrancaAdministrativa, idDebitoTipo /* idsDebitoTipoPrestacao */);
				break;
			case 2: // Modelo 2
				retorno = this.identificarTipoDebitoGuiaPagamentoModelo2(indicadorCobrancaAdministrativa,
								indicadorRemuneraCobrancaAdministrativa, idDebitoTipo /* idsDebitoTipoPrestacao */);
				break;
			case 3: // Modelo 3
				retorno = this.identificarTipoDebitoGuiaPagamentoModelo3(indicadorCobrancaAdministrativa,
								indicadorRemuneraCobrancaAdministrativa, idDebitoTipo /* idsDebitoTipoPrestacao */, indicadorDividaAtiva,
								indicadorExecucaoFiscal, numeroProcessoAdministrativoExecucaoFiscal);
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
					Short indicadorRemuneraCobrancaAdministrativa, Integer idDebitoTipo /*
																						 * Collection<
																						 * Integer>
																						 * idsDebitoTipoPrestacao
																						 */) throws ControladorException{

		Integer tipoDebito;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		Collection<Integer> tiposDebitoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativa(true);
		tiposDebitoCobrancaAdministrativa.add(DebitoTipo.ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA);

		// IndicadorCobrancaAdministrativa = NAO
		if(indicadorCobrancaAdministrativa != null && indicadorCobrancaAdministrativa.equals(ConstantesSistema.NAO)){

			// IndicadorRemuneraCobrancaAdministrativa = NAO
			if(indicadorRemuneraCobrancaAdministrativa != null && indicadorRemuneraCobrancaAdministrativa.equals(ConstantesSistema.NAO)){

				boolean encontrouDebitoCobrancaAdministrativa = false;

				// for(Integer idDebitoTipo : idsDebitoTipoPrestacao){
				encontrouDebitoCobrancaAdministrativa = tiposDebitoCobrancaAdministrativa.contains(idDebitoTipo);
				// if(encontrouDebitoCobrancaAdministrativa){
				// break;
				// }
				// }

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
				if(!verificarTipoDebitoDeAcrescimosOuServicosEspeciais(idDebitoTipo)){
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

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito a ser considerado para uma Guia de Pagamento - Modelo 2
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	private Object[] identificarTipoDebitoGuiaPagamentoModelo2(Short indicadorCobrancaAdministrativa,
					Short indicadorRemuneraCobrancaAdministrativa, Integer idDebitoTipo /*
																						 * Collection<
																						 * Integer>
																						 * idsDebitoTipoPrestacao
																						 */) throws ControladorException{

		Integer tipoDebito;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> tiposDebitoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativaModelo2();
		tiposDebitoCobrancaAdministrativa.add(DebitoTipo.ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA);

		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		// IndicadorCobrancaAdministrativa = NAO
		if(Util.compararShort(indicadorCobrancaAdministrativa, ConstantesSistema.NAO)){

			// IndicadorRemuneraCobrancaAdministrativa = NAO
			if(Util.compararShort(indicadorRemuneraCobrancaAdministrativa, ConstantesSistema.NAO)){

				tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO.executar());

				// IndicadorRemuneraCobrancaAdministrativa = SIM
			}else{

				boolean encontrouDebitoCobrancaAdministrativa = false;

				// for(Integer idDebitoTipo : idsDebitoTipoPrestacao){
				encontrouDebitoCobrancaAdministrativa = tiposDebitoCobrancaAdministrativa.contains(idDebitoTipo);
				// if(encontrouDebitoCobrancaAdministrativa){
				// break;
				// }
				// }

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

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito a ser considerado para uma Guia de Pagamento - Modelo 3
	 * 
	 * @author Saulo Lima
	 * @date 30/07/2014
	 */
	private Object[] identificarTipoDebitoGuiaPagamentoModelo3(Short indicadorCobrancaAdministrativa,
					Short indicadorRemuneraCobrancaAdministrativa, Integer idDebitoTipo, Short indicadorDividaAtiva,
					Short indicadorExecucaoFiscal, Integer numeroProcessoAdministrativoExecucaoFiscal) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Collection<Integer> tiposDebitoCobrancaAdministrativa = getTiposDebitoParcelamentoCobrancaAdministrativaModelo2();
		tiposDebitoCobrancaAdministrativa.add(DebitoTipo.ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA);
		Collection<Integer> debitoTipoDividaAtiva = this.pesquisarDebitoTipoDividaAtiva();
		Collection<Integer> debitoTipoExecucaoFiscal = this.pesquisarDebitoTipoExecucaoFiscal();
		Collection<Integer> debitoTipoJurosParcelamento = this.pesquisarDebitoTipoJurosParcelamento();

		boolean encontrouDebitoSucumbencia = false;
		boolean encontrouDebitoCobrancaAdministrativa = false;
		boolean encontrouDebitoDividaAtiva = false;
		boolean encontrouDebitoExecucaoFiscal = false;
		boolean encontrouDebitoJurosParcelamento = false;

		// for(Integer idDebitoTipo : idsDebitoTipoPrestacao){

		if(DebitoTipo.SUCUMBENCIA.equals(idDebitoTipo)){
			encontrouDebitoSucumbencia = true;
		}else if(debitoTipoDividaAtiva.contains(idDebitoTipo) || DebitoTipo.ENTRADA_PARCELAMENTO_DIVIDA_ATIVA.equals(idDebitoTipo)){
			encontrouDebitoDividaAtiva = true;
		}else if(debitoTipoExecucaoFiscal.contains(idDebitoTipo) || DebitoTipo.ENTRADA_PARCELAMENTO_EXECUCAO_FISCAL.equals(idDebitoTipo)
						|| DebitoTipo.DILIGENCIAS.equals(idDebitoTipo)){
			encontrouDebitoExecucaoFiscal = true;
		}else if(debitoTipoJurosParcelamento.contains(idDebitoTipo) || DebitoTipo.JUROS_SOBRE_PARCELAMENTO.equals(idDebitoTipo)){
			encontrouDebitoJurosParcelamento = true;
		}else if(tiposDebitoCobrancaAdministrativa.contains(idDebitoTipo)){
			encontrouDebitoCobrancaAdministrativa = true;
		}
		// }

		if(encontrouDebitoSucumbencia){
			tipoDebito = DebitoTipo.SUCUMBENCIA;
		}else if(indicadorDividaAtiva.equals(ConstantesSistema.NAO) && indicadorExecucaoFiscal.equals(ConstantesSistema.NAO)
						&& !encontrouDebitoCobrancaAdministrativa && !encontrouDebitoDividaAtiva && !encontrouDebitoExecucaoFiscal
						&& !encontrouDebitoJurosParcelamento){

			// lista de Item Contábil de Guia-Normal
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO.executar());

		}else if(indicadorDividaAtiva.equals(ConstantesSistema.NAO) && indicadorExecucaoFiscal.equals(ConstantesSistema.NAO)
						&& encontrouDebitoDividaAtiva){

			// lista de Item Contábil de Guia-DívidaAtiva
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_DIVIDA_ATIVA.executar());

		}else if(indicadorDividaAtiva.equals(ConstantesSistema.SIM) && indicadorExecucaoFiscal.equals(ConstantesSistema.NAO)
						&& !encontrouDebitoCobrancaAdministrativa && !encontrouDebitoExecucaoFiscal && !encontrouDebitoJurosParcelamento){

			// lista de Item Contábil de Guia-DívidaAtiva
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_DIVIDA_ATIVA.executar());

		}else if(indicadorDividaAtiva.equals(ConstantesSistema.NAO) && indicadorExecucaoFiscal.equals(ConstantesSistema.NAO)
						&& encontrouDebitoExecucaoFiscal){

			// lista de Item Contábil de Guia-ExecuçãoFiscal
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_EXECUCAO_FISCAL.executar());

		}else if(indicadorDividaAtiva.equals(ConstantesSistema.SIM) && indicadorExecucaoFiscal.equals(ConstantesSistema.NAO)
						&& encontrouDebitoExecucaoFiscal){

			// lista de Item Contábil de Guia-ExecuçãoFiscal
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_EXECUCAO_FISCAL.executar());

		}else if(indicadorDividaAtiva.equals(ConstantesSistema.SIM) && indicadorExecucaoFiscal.equals(ConstantesSistema.SIM)
						&& !encontrouDebitoJurosParcelamento){

			// lista de Item Contábil de Guia-ExecuçãoFiscal
			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_EXECUCAO_FISCAL.executar());

		}else if(encontrouDebitoCobrancaAdministrativa){

			tipoDebito = Integer
							.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_GUIA_PAGAMENTO_COBRANCA_ADMINISTRATIVA.executar());
			indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.SIM;

		}else{
			throw new ControladorException("atencao.parametro_debito_tipo_nao_cadastrado");
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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
			case 3: // Modelo 3
				retorno = identificarTipoDebitoAcrescimosContaModelo3(contaHelper);
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
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(contaHelper != null && contaHelper.getConta() != null){
			indicadorContaCobrancaAdministrativa = contaHelper.getConta().getIndicadorCobrancaAdministrativa();
			indicadorRemuneraCobrancaAdministrativa = contaHelper.getConta().getIndicadorRemuneraCobrancaAdministrativa();
			numeroProcessoAdministrativoExecucaoFiscal = contaHelper.getConta().getNumeroProcessoAdministrativoExecucaoFiscal();
		}

		if(indicadorContaCobrancaAdministrativa != null && indicadorContaCobrancaAdministrativa.equals(ConstantesSistema.NAO)
						&& indicadorRemuneraCobrancaAdministrativa != null
						&& indicadorRemuneraCobrancaAdministrativa.equals(ConstantesSistema.NAO)){

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar());

		}else{

			tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE_COBRANCA_ADMINISTRATIVA
							.executar());
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito dos acréscimos de uma Conta - Modelo 2
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	private Object[] identificarTipoDebitoAcrescimosContaModelo2(ContaValoresHelper contaHelper) throws ControladorException{

		Integer tipoDebito = null;
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(contaHelper != null && contaHelper.getConta() != null){
			numeroProcessoAdministrativoExecucaoFiscal = contaHelper.getConta().getNumeroProcessoAdministrativoExecucaoFiscal();
		}

		tipoDebito = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_ACRESCIMO_IMPONTUALIDADE.executar());

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito dos acréscimos de uma Conta - Modelo 3
	 * 
	 * @author Saulo Lima
	 * @date 30/07/2014
	 */
	private Object[] identificarTipoDebitoAcrescimosContaModelo3(ContaValoresHelper contaHelper) throws ControladorException{

		Object[] tipoDebito = new Object[3];
		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(contaHelper != null && contaHelper.getConta() != null){
			if(contaHelper.getConta().getNumeroProcessoAdministrativoExecucaoFiscal() != null){
				numeroProcessoAdministrativoExecucaoFiscal = contaHelper.getConta().getNumeroProcessoAdministrativoExecucaoFiscal();
			}else{
				numeroProcessoAdministrativoExecucaoFiscal = ConstantesSistema.NUMERO_PROCESSO_ADM_EXEC_FISCAL_ZERO;
			}
			indicadorDividaAtiva = contaHelper.getConta().getIndicadorDividaAtiva();
			indicadorExecucaoFiscal = contaHelper.getConta().getIndicadorExecucaoFiscal();
		}

		if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.NAO)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.NAO)){

			tipoDebito[0] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO.executar());
			tipoDebito[1] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO.executar());
			tipoDebito[2] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA.executar());

		}else if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.SIM)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.NAO)){

			tipoDebito[0] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO_DIVIDA_ATIVA.executar());
			tipoDebito[1] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO_DIVIDA_ATIVA.executar());
			tipoDebito[2] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA_DIVIDA_ATIVA.executar());

		}else if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.SIM)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.SIM)){

			tipoDebito[0] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO_EXECUCAO_FISCAL.executar());
			tipoDebito[1] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO_EXECUCAO_FISCAL.executar());
			tipoDebito[2] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA_EXECUCAO_FISCAL.executar());

		}else{
			throw new ControladorException("atencao.parametro_debito_tipo_nao_cadastrado");
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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
			case 3: // Modelo 3
				retorno = identificarTipoDebitoAcrescimosGuiaPagamentoModelo3(guiaPagamentoPrestacaoHistorico);
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
		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(guiaPagamentoPrestacaoHistorico != null){
			indicadorGuiaPagamentoCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorCobrancaAdministrativa();
			indicadorRemuneraCobrancaAdministrativa = guiaPagamentoPrestacaoHistorico.getIndicadorRemuneraCobrancaAdministrativa();
			numeroProcessoAdministrativoExecucaoFiscal = guiaPagamentoPrestacaoHistorico.getComp_id()
							.getNumeroProcessoAdministrativoExecucaoFiscal();
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

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito de uma Guia de Pagamento - Modelo 2
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

		Integer numeroProcessoAdministrativoExecucaoFiscal = null;
		Short indicadorDividaAtiva = ConstantesSistema.NAO;
		Short indicadorExecucaoFiscal = ConstantesSistema.NAO;

		if(guiaPagamentoPrestacaoHistorico != null){
			numeroProcessoAdministrativoExecucaoFiscal = guiaPagamentoPrestacaoHistorico.getComp_id()
							.getNumeroProcessoAdministrativoExecucaoFiscal();
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

		return retorno;
	}

	/**
	 * Identifica o tipo de débito de uma Guia de Pagamento - Modelo 3
	 * 
	 * @author Saulo Lima
	 * @date 30/07/2014
	 */
	private Object[] identificarTipoDebitoAcrescimosGuiaPagamentoModelo3(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico)
					throws ControladorException{

		Object[] tipoDebito = new Object[3];

		Short indicadorTotalRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;

		Integer numeroProcessoAdministrativoExecucaoFiscal = guiaPagamentoPrestacaoHistorico.getComp_id()
						.getNumeroProcessoAdministrativoExecucaoFiscal();
		Short indicadorDividaAtiva = guiaPagamentoPrestacaoHistorico.getIndicadorDividaAtiva();
		Short indicadorExecucaoFiscal = guiaPagamentoPrestacaoHistorico.getIndicadorExecucaoFiscal();

		if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.NAO)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.NAO)){

			tipoDebito[0] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO.executar());
			tipoDebito[1] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO.executar());
			tipoDebito[2] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA.executar());

		}else if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.SIM)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.NAO)){

			tipoDebito[0] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO_DIVIDA_ATIVA.executar());
			tipoDebito[1] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO_DIVIDA_ATIVA.executar());
			tipoDebito[2] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA_DIVIDA_ATIVA.executar());

		}else if(Util.compararShort(indicadorDividaAtiva, ConstantesSistema.SIM)
						&& Util.compararShort(indicadorExecucaoFiscal, ConstantesSistema.SIM)){

			tipoDebito[0] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO_EXECUCAO_FISCAL.executar());
			tipoDebito[1] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO_EXECUCAO_FISCAL.executar());
			tipoDebito[2] = Integer.valueOf(ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA_EXECUCAO_FISCAL.executar());

		}else{
			throw new ControladorException("atencao.parametro_debito_tipo_nao_cadastrado");
		}

		Object[] retorno = new Object[6];
		retorno[0] = tipoDebito;
		retorno[1] = indicadorTotalRemuneracaoCobrancaAdm;
		retorno[2] = indicadorParcialRemuneracaoCobrancaAdm;
		retorno[3] = numeroProcessoAdministrativoExecucaoFiscal;
		retorno[4] = indicadorDividaAtiva;
		retorno[5] = indicadorExecucaoFiscal;

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

	private ControladorCobrancaLocal controladorCobranca;

	protected ControladorCobrancaLocal getControladorCobranca(){

		if(controladorCobranca == null){
			ControladorCobrancaLocalHome localHome = null;

			// pega a instância do ServiceLocator.
			ServiceLocator locator = null;

			try{
				locator = ServiceLocator.getInstancia();

				localHome = (ControladorCobrancaLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_COBRANCA_SEJB);
				// guarda a referencia de um objeto capaz de fazer chamadas à objetos remotamente
				controladorCobranca = localHome.create();

				return controladorCobranca;
			}catch(CreateException e){
				throw new SistemaException(e);
			}catch(ServiceLocatorException e){
				throw new SistemaException(e);
			}
		}else{
			return controladorCobranca;
		}
	}

	/**
	 * Retorna o valor de controladorPermissaoEspecial
	 * 
	 * @return O valor de controladorPermissaoEspecial
	 */
	protected ControladorPermissaoEspecialLocal getControladorPermissaoEspecial(){

		ControladorPermissaoEspecialLocalHome localHome = null;
		ControladorPermissaoEspecialLocal local = null;

		// pega a instância do ServiceLocator.
		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorPermissaoEspecialLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_PERMISSAO_ESPECIAL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

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
					Integer idGuiaPagamento, Short numeroPrestacao, Integer itemLancamentoContabilId, Integer idDebitoTipo)
					throws ControladorException{

		Collection<GuiaPagamentoPrestacaoHistorico> colecaoGuiaPagamentoPrestacaoHistorico;
		FiltroGuiaPagamentoPrestacaoHistorico filtroGuiaPagamentoPrestacaoHistorico = new FiltroGuiaPagamentoPrestacaoHistorico();
		filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
						FiltroGuiaPagamentoPrestacaoHistorico.GUIA_PAGAMENTO_ID, idGuiaPagamento));
		filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
						FiltroGuiaPagamentoPrestacaoHistorico.NUMERO_PRESTACAO, numeroPrestacao));
		filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(
						FiltroGuiaPagamentoPrestacaoHistorico.LANCAMENTO_CONTABIL_ID, itemLancamentoContabilId));
		filtroGuiaPagamentoPrestacaoHistorico.adicionarParametro(new ParametroSimples(FiltroGuiaPagamentoPrestacaoHistorico.DEBITO_TIPO_ID,
						idDebitoTipo));

		filtroGuiaPagamentoPrestacaoHistorico
						.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacaoHistorico.LANCAMENTO_CONTABIL);
		filtroGuiaPagamentoPrestacaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroGuiaPagamentoPrestacaoHistorico.DEBITO_TIPO);


		colecaoGuiaPagamentoPrestacaoHistorico = this.getControladorUtil().pesquisar(filtroGuiaPagamentoPrestacaoHistorico,
						GuiaPagamentoPrestacaoHistorico.class.getName());

		return colecaoGuiaPagamentoPrestacaoHistorico;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0064] Verificar Parcelamento de Execução Fiscal
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoExecucaoFiscal() throws ControladorException{

		ArrayList<Integer> colecaoDebitoTipo = new ArrayList<Integer>();

		// try{
		String[] colecaoParametroTipoDebito_ParcEF = null;
		String pTipoDebito_ParcEF = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_EXECUCAO_FISCAL.executar());
		if(pTipoDebito_ParcEF != null){
			colecaoParametroTipoDebito_ParcEF = pTipoDebito_ParcEF.split(",");
		}

		String[] colecaoParametroTipoDebito_CorrecaoEF = null;
		String pTipoDebito_CorrecaoEF = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO_EXECUCAO_FISCAL.executar());
		if(pTipoDebito_CorrecaoEF != null){
			colecaoParametroTipoDebito_CorrecaoEF = pTipoDebito_CorrecaoEF.split(",");
		}

		String[] colecaoParametroTipoDebito_MultaAtrasoEF = null;
		String pTipoDebito_MultaAtrasoEF = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO_EXECUCAO_FISCAL
						.executar());
		if(pTipoDebito_MultaAtrasoEF != null){
			colecaoParametroTipoDebito_MultaAtrasoEF = pTipoDebito_MultaAtrasoEF.split(",");
		}

		String[] colecaoParametroTipoDebito_JurosMoraEF = null;
		String pTipoDebito_JurosMoraEF = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA_EXECUCAO_FISCAL.executar());
		if(pTipoDebito_JurosMoraEF != null){
			colecaoParametroTipoDebito_JurosMoraEF = pTipoDebito_JurosMoraEF.split(",");
		}

		String[] colecaoParametroTipoDebito_JurosParcEF = null;
		String pTipoDebito_JurosParcEF = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_EXECUCAO_FISCAL
						.executar());
		if(pTipoDebito_JurosParcEF != null){
			colecaoParametroTipoDebito_JurosParcEF = pTipoDebito_JurosParcEF.split(",");
		}

		// Integer tipoDebitoSucumbencia = DebitoTipo.SUCUMBENCIA;
		// Integer tipoDebitoDiligencias = DebitoTipo.DILIGENCIAS;

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_ParcEF)){
			for(int i = 0; i < colecaoParametroTipoDebito_ParcEF.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_ParcEF[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_ParcEF[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_CorrecaoEF)){
			for(int i = 0; i < colecaoParametroTipoDebito_CorrecaoEF.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_CorrecaoEF[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_CorrecaoEF[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_MultaAtrasoEF)){
			for(int i = 0; i < colecaoParametroTipoDebito_MultaAtrasoEF.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_MultaAtrasoEF[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_MultaAtrasoEF[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_JurosMoraEF)){
			for(int i = 0; i < colecaoParametroTipoDebito_JurosMoraEF.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_JurosMoraEF[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_JurosMoraEF[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_JurosParcEF)){
			for(int i = 0; i < colecaoParametroTipoDebito_JurosParcEF.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_JurosParcEF[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_JurosParcEF[i]));
				}
			}
		}

		// if(tipoDebitoSucumbencia.intValue() > 0){
		// colecaoDebitoTipo.add(tipoDebitoSucumbencia);
		// }
		// if(tipoDebitoDiligencias.intValue() > 0){
		// colecaoDebitoTipo.add(tipoDebitoDiligencias);
		// }

		// }catch(ControladorException e){
		// e.printStackTrace();
		// }

		return colecaoDebitoTipo;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0064] Verificar Parcelamento de Execução Fiscal
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoSucumbenciaDiligencia() throws ControladorException{

		ArrayList<Integer> colecaoDebitoTipo = new ArrayList<Integer>();

		Integer tipoDebitoSucumbencia = DebitoTipo.SUCUMBENCIA;
		Integer tipoDebitoDiligencias = DebitoTipo.DILIGENCIAS;

		if(tipoDebitoSucumbencia.intValue() > 0){
			colecaoDebitoTipo.add(tipoDebitoSucumbencia);
		}
		if(tipoDebitoDiligencias.intValue() > 0){
			colecaoDebitoTipo.add(tipoDebitoDiligencias);
		}

		return colecaoDebitoTipo;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoDividaAtiva() throws ControladorException{

		ArrayList<Integer> colecaoDebitoTipo = new ArrayList<Integer>();

		String[] colecaoParametroTipoDebito_ParcDA = null;
		String pTipoDebito_ParcDA = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_DIVIDA_ATIVA.executar());
		if(pTipoDebito_ParcDA != null){
			colecaoParametroTipoDebito_ParcDA = pTipoDebito_ParcDA.split(",");
		}

		String[] colecaoParametroTipoDebito_CorrecaoDA = null;
		String pTipoDebito_CorrecaoDA = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO_DIVIDA_ATIVA.executar());
		if(pTipoDebito_CorrecaoDA != null){
			colecaoParametroTipoDebito_CorrecaoDA = pTipoDebito_CorrecaoDA.split(",");
		}

		String[] colecaoParametroTipoDebito_MultaAtrasoDA = null;
		String pTipoDebito_MultaAtrasoDA = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO_DIVIDA_ATIVA.executar());
		if(pTipoDebito_MultaAtrasoDA != null){
			colecaoParametroTipoDebito_MultaAtrasoDA = pTipoDebito_MultaAtrasoDA.split(",");
		}

		String[] colecaoParametroTipoDebito_JurosMoraDA = null;
		String pTipoDebito_JurosMoraDA = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA_DIVIDA_ATIVA.executar());
		if(pTipoDebito_JurosMoraDA != null){
			colecaoParametroTipoDebito_JurosMoraDA = pTipoDebito_JurosMoraDA.split(",");
		}

		String[] colecaoParametroTipoDebito_JurosParcDA = null;
		String pTipoDebito_JurosParcDA = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_DIVIDA_ATIVA
						.executar());
		if(pTipoDebito_JurosParcDA != null){
			colecaoParametroTipoDebito_JurosParcDA = pTipoDebito_JurosParcDA.split(",");
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_ParcDA)){
			for(int i = 0; i < colecaoParametroTipoDebito_ParcDA.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_ParcDA[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_ParcDA[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_CorrecaoDA)){
			for(int i = 0; i < colecaoParametroTipoDebito_CorrecaoDA.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_CorrecaoDA[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_CorrecaoDA[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_MultaAtrasoDA)){
			for(int i = 0; i < colecaoParametroTipoDebito_MultaAtrasoDA.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_MultaAtrasoDA[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_MultaAtrasoDA[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_JurosMoraDA)){
			for(int i = 0; i < colecaoParametroTipoDebito_JurosMoraDA.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_JurosMoraDA[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_JurosMoraDA[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParametroTipoDebito_JurosParcDA)){
			for(int i = 0; i < colecaoParametroTipoDebito_JurosParcDA.length; i++){
				if(Integer.valueOf(colecaoParametroTipoDebito_JurosParcDA[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParametroTipoDebito_JurosParcDA[i]));
				}
			}
		}

		return colecaoDebitoTipo;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoJurosParcelamento() throws ControladorException{

		ArrayList<Integer> colecaoDebitoTipo = new ArrayList<Integer>();

		String[] colecaoJurosParcCA = null;
		String pJurosParcCA = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_COBRANCA_ADMINISTRATIVA
						.executar());
		if(pJurosParcCA != null){
			colecaoJurosParcCA = pJurosParcCA.split(",");
		}

		String[] colecaoJurosParc = null;
		String pJurosParc = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO.executar());
		if(pJurosParc != null){
			colecaoJurosParc = pJurosParc.split(",");
		}

		String[] colecaoJurosParcDA = null;
		String pJurosParcDA = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_DIVIDA_ATIVA.executar());
		if(pJurosParcDA != null){
			colecaoJurosParcDA = pJurosParcDA.split(",");
		}

		String[] colecaoJurosParcEF = null;
		String pJurosParcEF = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_PARCELAMENTO_EXECUCAO_FISCAL.executar());
		if(pJurosParcEF != null){
			colecaoJurosParcEF = pJurosParcEF.split(",");
		}

		if(!Util.isVazioOrNulo(colecaoJurosParcCA)){
			for(int i = 0; i < colecaoJurosParcCA.length; i++){
				if(Integer.valueOf(colecaoJurosParcCA[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoJurosParcCA[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoJurosParc)){
			for(int i = 0; i < colecaoJurosParc.length; i++){
				if(Integer.valueOf(colecaoJurosParc[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoJurosParc[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoJurosParcDA)){
			for(int i = 0; i < colecaoJurosParcDA.length; i++){
				if(Integer.valueOf(colecaoJurosParcDA[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoJurosParcDA[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoJurosParcEF)){
			for(int i = 0; i < colecaoJurosParcEF.length; i++){
				if(Integer.valueOf(colecaoJurosParcEF[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoJurosParcEF[i]));
				}
			}
		}

		return colecaoDebitoTipo;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoParcelamentoNormal() throws ControladorException{

		ArrayList<Integer> colecaoDebitoTipo = new ArrayList<Integer>();

		String[] colecaoParcNormal = null;
		String pParcNormal = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_NORMAL.executar());
		if(pParcNormal != null){
			colecaoParcNormal = pParcNormal.split(",");
		}

		String[] colecaoParcCorrecao = null;
		String pParcCorrecao = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_CORRECAO.executar());
		if(pParcCorrecao != null){
			colecaoParcCorrecao = pParcCorrecao.split(",");
		}

		String[] colecaoParcMultaAtraso = null;
		String pParcMultaAtraso = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_MULTA_ATRASO.executar());
		if(pParcMultaAtraso != null){
			colecaoParcMultaAtraso = pParcMultaAtraso.split(",");
		}

		String[] colecaoParcJurosMora = null;
		String pParcJurosMora = (String) (ParametroParcelamento.P_TIPO_DEBITO_PARCELAMENTO_JUROS_MORA.executar());
		if(pParcJurosMora != null){
			colecaoParcJurosMora = pParcJurosMora.split(",");
		}

		if(!Util.isVazioOrNulo(colecaoParcNormal)){
			for(int i = 0; i < colecaoParcNormal.length; i++){
				if(Integer.valueOf(colecaoParcNormal[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParcNormal[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParcCorrecao)){
			for(int i = 0; i < colecaoParcCorrecao.length; i++){
				if(Integer.valueOf(colecaoParcCorrecao[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParcCorrecao[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParcMultaAtraso)){
			for(int i = 0; i < colecaoParcMultaAtraso.length; i++){
				if(Integer.valueOf(colecaoParcMultaAtraso[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParcMultaAtraso[i]));
				}
			}
		}

		if(!Util.isVazioOrNulo(colecaoParcJurosMora)){
			for(int i = 0; i < colecaoParcJurosMora.length; i++){
				if(Integer.valueOf(colecaoParcJurosMora[i]).intValue() > 0){
					colecaoDebitoTipo.add(Integer.valueOf(colecaoParcJurosMora[i]));
				}
			}
		}

		return colecaoDebitoTipo;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [FS0054] Verificar quantidade de parcelas da sucumbência
	 * 
	 * @author Saulo Lima
	 * @date 15/08/2014
	 */
	public void verificarQuantidadeParcelasSucumbencia(Integer quantidadeParcelasSucumbencia, Usuario usuario) throws ControladorException{

		Integer pQtdMaxParcelasSucumbencia = Util.obterInteger((String) ParametroParcelamento.P_QUANTIDADE_MAXIMA_PARCELAS_SUCUMBENCIA
						.executar(ExecutorParametrosCobranca.getInstancia()));

		if(!Util.isVazioOuBranco(pQtdMaxParcelasSucumbencia)
						&& !pQtdMaxParcelasSucumbencia.equals(Integer.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			if(quantidadeParcelasSucumbencia.compareTo(pQtdMaxParcelasSucumbencia) > 0){
				if(!this.getControladorPermissaoEspecial().verificarPermissaoEspecial(
								PermissaoEspecial.ALTERAR_VALORES_PADROES_SUCUMBENCIA_EXECUCAO_FISCAL, usuario)){
					throw new ControladorException("atencao.quantidade.parcelas.sucumbencia", null, pQtdMaxParcelasSucumbencia.toString());
				}
			}
		}
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0047] Acumular Valor de Sucumbência
	 * 
	 * @author Saulo Lima
	 * @date 15/09/2014
	 */
	public Object[] acumularValorSucumbencia(BigDecimal valorSucumbenciaCobradoEntradaParc, BigDecimal valorTotalSucumbencia,
					Short quantidadeParcelasSucumbencia, Map<Integer, BigDecimal> mapProcessosSucumbencias, Imovel imovel,
					Map<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>>> valorEntradaPorTipoDebito,
					BigDecimal valorEntradaParaDeduzir, boolean existeContaComoEntradaParcelamento,
					Short[] indicadorTotalRemuneracaoCobrancaAdm, Collection<Categoria> colecaoCategoria,
					BigDecimal valorDescontoAcrescimos, String indicadorCobrancaParcelamento,
					Collection<ContaValoresHelper> colecaoContaValores, Collection<GuiaPagamentoValoresHelper> colecaoGuiaPagamentoValores,
					Collection<DebitoACobrar> colecaoDebitoACobrar) throws ControladorException{

		Object[] resultado = new Object[3];
		Boolean indicadorSucumbencia = new Boolean(true);

		// Map (TipoDebito -> ItemContabil/Processo -> Categoria -> Valor)
		Map<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>>> mapaPrincipal = new HashMap<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>>>();

		// Débitos Cobrados em Conta
		if(!Util.isVazioOrNulo(colecaoContaValores)){
			for(ContaValoresHelper contaHelper : colecaoContaValores){
				valorEntradaParaDeduzir = this.getControladorCobranca().acumularListaDebitoCobradoConta(mapaPrincipal, contaHelper,
								valorEntradaParaDeduzir, valorEntradaPorTipoDebito, existeContaComoEntradaParcelamento,
								indicadorTotalRemuneracaoCobrancaAdm, indicadorSucumbencia.booleanValue());
			}
		}

		Collection<Integer> idsTipoDebito = mapaPrincipal.keySet();
		if(!Util.isVazioOrNulo(idsTipoDebito)){
			for(Integer idTipoDebito : idsTipoDebito){

				Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>> helperMap = mapaPrincipal.get(idTipoDebito);
				if(helperMap != null && !helperMap.isEmpty()){

					Collection<LancamentoItemContabilParcelamentoHelper> helperCollecion = helperMap.keySet();
					for(LancamentoItemContabilParcelamentoHelper helper : helperCollecion){

						if(helper.getNumeroProcessoAdministrativoExecucaoFiscal() != null){

							Collection<Integer> processos = mapProcessosSucumbencias.keySet();

							Integer numeroProcessoEncontrado = null;
							for(Integer numeroProcesso : processos){
								if(numeroProcesso.equals(helper.getNumeroProcessoAdministrativoExecucaoFiscal())){
									numeroProcessoEncontrado = numeroProcesso;
								}
							}

							if(numeroProcessoEncontrado == null){

								Map<Categoria, BigDecimal> categoriaMap = helperMap.get(helper);
								Collection<Categoria> categoriaColecao = categoriaMap.keySet();
								BigDecimal valor = BigDecimal.ZERO;
								for(Categoria categoria : categoriaColecao){
									valor = valor.add(categoriaMap.get(categoria));
								}
								mapProcessosSucumbencias.put(helper.getNumeroProcessoAdministrativoExecucaoFiscal(), valor);
							}
						}
					}
				}
			}
		}

		// Débito a Cobrar
		Object[] retornoACobrar = this.getControladorCobranca().acumularListaDebitoACobrar(colecaoDebitoACobrar, valorEntradaParaDeduzir,
						valorEntradaPorTipoDebito, existeContaComoEntradaParcelamento, indicadorTotalRemuneracaoCobrancaAdm,
						indicadorSucumbencia.booleanValue());

		valorEntradaParaDeduzir = (BigDecimal) retornoACobrar[1];

		// Guia de Pagamento
		Object[] retornoGuia = this.getControladorCobranca().acumularListaGuiaPagamento(colecaoGuiaPagamentoValores,
						valorEntradaParaDeduzir, valorEntradaPorTipoDebito, existeContaComoEntradaParcelamento,
						indicadorTotalRemuneracaoCobrancaAdm, indicadorSucumbencia.booleanValue());

		valorEntradaParaDeduzir = (BigDecimal) retornoGuia[1];

		// Acréscimos por Impontualidade
		Object[] retornoAcrescimos = this.getControladorCobranca().acumularListaAcrescimos(colecaoContaValores,
						colecaoGuiaPagamentoValores, existeContaComoEntradaParcelamento, valorDescontoAcrescimos,
						indicadorCobrancaParcelamento, valorEntradaParaDeduzir, valorEntradaPorTipoDebito,
						indicadorTotalRemuneracaoCobrancaAdm, indicadorSucumbencia);

		valorEntradaParaDeduzir = (BigDecimal) retornoAcrescimos[1];

		// if(Util.isMaiorZero(valorEntradaParaDeduzir)){

		BigDecimal valorSucumbenciaParcelada = valorTotalSucumbencia.subtract(valorSucumbenciaCobradoEntradaParc);

		Map<Integer, BigDecimal> mapProcessosSucumbenciasEntrada = null;
		// if(quantidadeParcelasSucumbencia.intValue() > 1){
		mapProcessosSucumbenciasEntrada = this.getControladorCobranca().distribuirValorEntreProcessosExecucaoFiscal(
						valorSucumbenciaCobradoEntradaParc, mapProcessosSucumbencias, imovel.getId());
		// }else{
		// mapProcessosSucumbenciasEntrada = mapProcessosSucumbencias;
		// }

		quantidadeParcelasSucumbencia = Short.valueOf("" + (quantidadeParcelasSucumbencia.intValue() - 1));
		mapProcessosSucumbencias = this.getControladorCobranca().distribuirValorEntreProcessosExecucaoFiscal(valorSucumbenciaParcelada,
						mapProcessosSucumbencias, imovel.getId());

		// juntamente com seus débitos cobrados realizados que foram parcelados
		Object[] retorno = this.acumularListaSucumbenciaAtual(mapProcessosSucumbenciasEntrada, valorEntradaParaDeduzir,
						valorEntradaPorTipoDebito, existeContaComoEntradaParcelamento, indicadorTotalRemuneracaoCobrancaAdm,
						colecaoCategoria);
		// Map<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria,
		// BigDecimal>>> teste = (Map<Integer, Map<LancamentoItemContabilParcelamentoHelper,
		// Map<Categoria, BigDecimal>>>) retorno[0];
		valorEntradaParaDeduzir = (BigDecimal) retorno[1];
		// }
		
		resultado[0] = valorEntradaParaDeduzir;
		resultado[1] = quantidadeParcelasSucumbencia;
		resultado[2] = mapProcessosSucumbencias;

		return resultado;
	}

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0047] Acumular Valor de Sucumbência
	 * 
	 * @author Saulo Lima
	 * @date 15/09/2014
	 */
	private Object[] acumularListaSucumbenciaAtual(Map<Integer, BigDecimal> mapProcessosSucumbencias, BigDecimal valorEntradaParaDeduzir,
					Map<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>>> valorEntradaPorTipoDebito,
					boolean existeContaComoEntradaParcelamento, Short[] indicadorTotalRemuneracaoCobrancaAdm,
					Collection<Categoria> colecaoCategoria) throws ControladorException{

		// Map (TipoDebito -> ItemContabil/Processo -> Categoria -> Valor)
		Map<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>>> mapaPrincipal = new HashMap<Integer, Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>>>();
		Map<LancamentoItemContabilParcelamentoHelper, Map<Categoria, BigDecimal>> mapaItensCategoriaValor = null;
		LancamentoItemContabilParcelamentoHelper itemContabil = null;

		DebitoTipo debitoTipo = this.getControladorCobranca().filtrarDebitoTipo(DebitoTipo.SUCUMBENCIA);

		Short indicadorParcialRemuneracaoCobrancaAdm = ConstantesSistema.NAO;
		Short indicadorDividaAtiva = ConstantesSistema.SIM;
		Short indicadorExecucaoFiscal = ConstantesSistema.SIM;
		indicadorTotalRemuneracaoCobrancaAdm[0] = ConstantesSistema.NAO;

		if(mapProcessosSucumbencias != null && !mapProcessosSucumbencias.isEmpty()){

			for(Integer numeroProcesso : mapProcessosSucumbencias.keySet()){

				// Captura o Map (ItemContabil -> Categoria -> Valor) do Map principal
				mapaItensCategoriaValor = this.getControladorCobranca().obterMapaItensContabeis(mapaPrincipal, debitoTipo.getId());

				itemContabil = new LancamentoItemContabilParcelamentoHelper(debitoTipo.getLancamentoItemContabil().getId(),
								indicadorParcialRemuneracaoCobrancaAdm, numeroProcesso, indicadorDividaAtiva, indicadorExecucaoFiscal);

				// Captura o Map (Categoria -> Valor) que já existe para o item contábil.
				// Caso não exista, cria um Map vazio
				Map<Categoria, BigDecimal> mapaCategoriaValor = mapaItensCategoriaValor.get(itemContabil);
				if(mapaCategoriaValor == null){
					mapaCategoriaValor = new HashMap<Categoria, BigDecimal>();
				}

				BigDecimal valorPocesso = mapProcessosSucumbencias.get(numeroProcesso);

				Map<Categoria, BigDecimal> valorProcessoPorCategoria = this.getControladorCobranca()
								.distribuirValorParcelamentoPorCategoria(colecaoCategoria, valorPocesso);

				// Para cada ContaCategoria, será capturado o valor por item contábil
				// para ser acumulado no
				// Map (TipoDebito -> Item Contábil -> Categoria -> Valor)
				for(Categoria categoriaAtual : valorProcessoPorCategoria.keySet()){

					// Instancia uma Categoria apenas com o Id e com a quantidade de
					// economias desta Categoria, que pode ser alterada caso seja
					// encontrada outra conta com um número maior de economias

					// if(contaCategoriaHistorico.getQuantidadeEconomia() > 0){
					// categoriaAtual.setQuantidadeEconomiasCategoria(Integer.valueOf(contaCategoriaHistorico
					// .getQuantidadeEconomia()));
					// }

					BigDecimal valorProcessoCategoria = valorProcessoPorCategoria.get(categoriaAtual);
					if(valorProcessoCategoria != null && valorProcessoCategoria.compareTo(BigDecimal.ZERO) > 0){

						valorEntradaParaDeduzir = this.getControladorCobranca().acumularCategoriaValor(valorProcessoCategoria,
										valorEntradaParaDeduzir, debitoTipo.getId(), itemContabil, categoriaAtual, mapaCategoriaValor,
										valorEntradaPorTipoDebito, existeContaComoEntradaParcelamento);
					}
				}

				// ******************************************************************
				// Armazena os Maps manipulados acima
				// ******************************************************************

				// Se o Map (Categoria -> Valor) para o item contábil estiver preenchido, guarda no
				// Map (ItemContabil -> Categoria -> Valor)
				if(!mapaCategoriaValor.isEmpty()){
					mapaItensCategoriaValor.put(itemContabil, mapaCategoriaValor);
				}

				// Se o Map (ItemContabil -> Categoria -> Valor) para o tipo de débito em questão
				// estiver preenchido, guarda no
				// Map (TipoDebito -> ItemContabil -> Categoria -> Valor)
				if(!mapaItensCategoriaValor.isEmpty()){
					mapaPrincipal.put(debitoTipo.getId(), mapaItensCategoriaValor);
				}
			}
		}

		Object[] resultado = new Object[2];
		resultado[0] = mapaPrincipal;
		resultado[1] = valorEntradaParaDeduzir;

		return resultado;
	}

}