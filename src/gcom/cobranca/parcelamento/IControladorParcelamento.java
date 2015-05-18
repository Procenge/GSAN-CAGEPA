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
 * @author Luciano Galvão
 * @date 21/11/2013
 */
public interface IControladorParcelamento {

	/**
	 * Identifica o tipo de débito de uma Conta
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoConta(ContaHistorico contaHistorico) throws ControladorException;

	/**
	 * Retorna o tipo de débito que deve ser considerado para um dado DebitoCobradoHistorico
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoDebitoCobrado(ContaValoresHelper contaHelper, DebitoCobradoHistorico debitoCobradoHistorico)
					throws ControladorException;

	/**
	 * Identifica o tipo de débito a ser considerado para um Débito a Cobrar
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	public Object[] identificarTipoDebitoDebitoACobrar(DebitoACobrarHistorico debitoACobrarHistorico) throws ControladorException;

	/**
	 * Identifica o tipo de débito a ser considerado para uma Guia de Pagamento
	 * 
	 * @author Luciano Galvao
	 * @date 02/11/2012
	 */
	public Object[] identificarTipoDebitoGuiaPagamento(Integer idGuiaPagamento, Short numeroPrestacao, Integer itemLancamentoContabilId,
					Integer idDebitoTipo) throws ControladorException;

	/**
	 * Identifica o tipo de débito dos acréscimos de uma Conta
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoAcrescimosConta(ContaValoresHelper contaHelper) throws ControladorException;

	/**
	 * Identifica o tipo de débito de uma Guia de Pagamento
	 * 
	 * @author Luciano Galvao
	 * @date 31/10/2012
	 */
	public Object[] identificarTipoDebitoAcrescimosGuiaPagamento(GuiaPagamentoPrestacaoHistorico guiaPagamentoPrestacaoHistorico)
					throws ControladorException;

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
	public Collection<Integer> getTiposDebitoParcelamentoCobrancaAdministrativa(boolean consideraJuros) throws ControladorException;

	/**
	 * Retorna os tipos de débito correspondentes a acréscimos de impontualidade, utilizados no
	 * Rotina batch: Marcar Itens Remuneráveis por Cobrança Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Coleção de Ids de tipos de débito correspondentes a acréscimos de impontualidade
	 */
	public Collection<Integer> getTiposDebitoAcrescimoImpontualidade() throws ControladorException;

	/**
	 * Retorna os tipos de débito correspondentes a serviços especiais, utilizados no Rotina batch:
	 * Marcar Itens Remuneráveis por Cobrança Administrativa
	 * 
	 * @author Luciano Galvao
	 * @date 22/05/2013
	 * @return Coleção de Ids de tipos de débito correspondentes a serviços especiais
	 */
	public Collection<Integer> getTiposDebitoServicosEspeciais() throws ControladorException;

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
	public Collection<Integer> getTiposDebitoParcelamento() throws ControladorException;

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
					throws ControladorException;

	/**
	 * Obtém o Layout de Resolução de Diretoria para o parcelamento de cobrança administrativa
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Luciano Galvão
	 * @date 27/11/2013
	 */
	public ResolucaoDiretoriaLayout obterResolucaoDiretoriaLayoutParcCobrancaAdministrativa() throws GeradorRelatorioParcelamentoException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0064] Verificar Parcelamento de Execução Fiscal
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoExecucaoFiscal() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [SB0064] Verificar Parcelamento de Execução Fiscal
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoSucumbenciaDiligencia() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoDividaAtiva() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoJurosParcelamento() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * 
	 * @author Saulo Lima
	 * @date 03/07/2014
	 */
	public ArrayList<Integer> pesquisarDebitoTipoParcelamentoNormal() throws ControladorException;

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [FS0054] Verificar quantidade de parcelas da sucumbência
	 * 
	 * @author Saulo Lima
	 * @date 15/08/2014
	 */
	public void verificarQuantidadeParcelasSucumbencia(Integer quantidadeParcelasSucumbencia, Usuario usuario) throws ControladorException;

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
					Collection<DebitoACobrar> colecaoDebitoACobrar) throws ControladorException;
}
