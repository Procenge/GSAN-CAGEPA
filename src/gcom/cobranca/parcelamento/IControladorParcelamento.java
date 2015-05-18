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

package gcom.cobranca.parcelamento;

import gcom.arrecadacao.pagamento.GuiaPagamentoPrestacaoHistorico;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.ResolucaoDiretoriaLayout;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.cobranca.bean.GuiaPagamentoValoresHelper;
import gcom.faturamento.conta.ContaHistorico;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.DebitoCobradoHistorico;
import gcom.financeiro.lancamento.bean.LancamentoItemContabilParcelamentoHelper;
import gcom.relatorio.cobranca.parcelamento.GeradorRelatorioParcelamentoException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;


/**
 * Interface Controlador Parcelamento
 * 
 * @author Luciano Galv�o
 * @date 21/11/2013
 */
public interface IControladorParcelamento {

	/**
	 * Identifica o tipo de d�bito de uma Conta
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoConta(ContaHistorico contaHistorico) throws ControladorException;

	/**
	 * Retorna o tipo de d�bito que deve ser considerado para um dado DebitoCobradoHistorico
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoDebitoCobrado(ContaValoresHelper contaHelper, DebitoCobradoHistorico debitoCobradoHistorico)
					throws ControladorException;

	/**
	 * Identifica o tipo de d�bito a ser considerado para um D�bito a Cobrar
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	public Object[] identificarTipoDebitoDebitoACobrar(DebitoACobrarHistorico debitoACobrarHistorico) throws ControladorException;

	/**
	 * Identifica o tipo de d�bito a ser considerado para uma Guia de Pagamento
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	public Object[] identificarTipoDebitoGuiaPagamento(Integer idGuiaPagamento, Short numeroPrestacao, Integer itemLancamentoContabilId,
					Integer idDebitoTipo) throws ControladorException;

	/**
	 * Identifica o tipo de d�bito dos acr�scimos de uma Conta
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoAcrescimosConta(ContaValoresHelper contaHelper) throws ControladorException;

	/**
	 * Identifica o tipo de d�bito de uma Guia de Pagamento
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoAcrescimosGuiaPagamento(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico)
					throws ControladorException;

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
	public Collection<Integer> getTiposDebitoParcelamentoCobrancaAdministrativa(boolean consideraJuros) throws ControladorException;

	/**
	 * Retorna os tipos de d�bito correspondentes a acr�scimos de impontualidade, utilizados no
	 * Rotina batch: Marcar Itens Remuner�veis por Cobran�a Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a acr�scimos de impontualidade
	 */
	public Collection<Integer> getTiposDebitoAcrescimoImpontualidade() throws ControladorException;

	/**
	 * Retorna os tipos de d�bito correspondentes a servi�os especiais, utilizados no Rotina batch:
	 * Marcar Itens Remuner�veis por Cobran�a Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Cole��o de Ids de tipos de d�bito correspondentes a servi�os especiais
	 */
	public Collection<Integer> getTiposDebitoServicosEspeciais() throws ControladorException;

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
	public Collection<Integer> getTiposDebitoParcelamento() throws ControladorException;

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
					throws ControladorException;

	/**
	 * Obt�m o Layout de Resolu��o de Diretoria para o parcelamento de cobran�a administrativa
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Luciano Galv�o
	 * @date 27/11/2013
	 */
	public ResolucaoDiretoriaLayout obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa() throws GeradorRelatorioParcelamentoException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB0064] Verificar Parcelamento de Execu��o Fiscal
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoExecucaoFiscal() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB0064] Verificar Parcelamento de Execu��o Fiscal
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoSucumbenciaDiligencia() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoDividaAtiva() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoJurosParcelamento() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoParcelamentoNormal() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [FS0054] Verificar quantidade de parcelas da sucumb�ncia
	 * 
	 * @author Saulo Lima
	 * @date 15/08/2014
	 */
	public void verificarQuantidadeParcelasSucumbencia(Integer quantidadeParcelasSucumbencia, Usuario usuario) throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de D�bitos
	 * [SB0047] Acumular Valor de Sucumb�ncia
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
					Collection<DebitoACobrar> colecaoDebitoACobrar) throws ControladorException;
}
