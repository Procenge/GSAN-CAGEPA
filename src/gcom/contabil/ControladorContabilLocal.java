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

package gcom.contabil;

import gcom.arrecadacao.pagamento.GuiaPagamentoCategoria;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.contabil.bean.LancamentoContabilAnaliticoConsultaHelper;
import gcom.contabil.bean.LancamentoContabilSinteticoConsultaHelper;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.util.Collection;
import java.util.Map;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gestão Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * 
 * @author Genival Barbosa
 * @created 6 de Julho de 2011
 * @version 1.0
 */

public interface ControladorContabilLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * XXXXXXXXXX
	 * [UC0***] XXXXXXXXXX
	 * 
	 * @author Genival Barbosa
	 * @date 06/07/2011
	 */

	public Collection<LancamentoContabilSinteticoConsultaHelper> consultarLancamentoContabilSintetico(Map<String, Object> filtro)
					throws ControladorException;

	/**
	 * Transfere todas as parcelas de longo para curto prazo dos débitos informados.
	 * 
	 * @param debitosACobrar
	 *            Débitos cujas parcela devem ser transferidas.
	 * @throws ControladorException
	 */
	public void transferirDebitosParaCurtoPrazo(Collection<DebitoACobrar> debitosACobrar) throws ControladorException;

	/**
	 * Prepara os dados necessários e chama a rotina de registro dos lançamentos contábeis
	 * sintéticos e analíticos.
	 * 
	 * @param objeto
	 * @param operacao
	 * @throws Exception
	 */
	void registrarLancamentoContabil(Object objeto, OperacaoContabil operacaoContabil) throws ControladorException;

	/**
	 * Prepara os dados necessários e chama a rotina de registro dos lançamentos contábeis
	 * sintéticos e analíticos.
	 * 
	 * @param objeto
	 * @param operacao
	 * @throws Exception
	 */
	public void registrarLancamentoContabil(Object objeto, OperacaoContabil operacaoContabil,
					Collection<GuiaPagamentoCategoria> colecaoGuiaPagamentoCategoria)
					throws ControladorException;

	/**
	 * Método responsável por obter o eventoComercial através do id
	 * 
	 * @param id
	 * @return eventoComercial
	 * @throws ErroRepositorioException
	 */
	public EventoComercial obterEventoComercial(Integer id) throws ControladorException;

	/**
	 * Método responsável por consultar e montar os LancamentoContabilAnaliticoConsultaHelper
	 * 
	 * @param lancamentoContabilSintetico
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilAnaliticoConsultaHelper
	 * @throws ErroRepositorioException
	 */
	public Collection<LancamentoContabilAnaliticoConsultaHelper> consultarLancamentoContabilAnaliticoPorSintetico(
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ControladorException;

	/**
	 * [UCXXXX] Provisão de devedores duvidosos
	 * 
	 * @autor Genival Barbosa
	 * @date 06/12/2011
	 * @param colecao
	 * @throws ControladorException
	 */
	public void provisaoDevedoresDuvidosos(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * @param collOperacaoContabilHelper
	 * @throws ControladorException
	 */
	public void registrarLancamentoContabil(Collection collOperacaoContabilHelper) throws ControladorException;

	/**
	 * Atualizar registro de provisão de devedores duvidosos e realizar o lançamento contábil do
	 * mesmo
	 * 
	 * @author Hugo Lima
	 * @date 27/06/2012
	 * @param conta
	 * @param sistemaParametro
	 * @param idProvisaoDevedoresDuvidososMotivoBaixa
	 * @throws ControladorException
	 */
	public void atualizarProvisaoDevedoresDuvidososLancamentoContabil(Object objetoConta, SistemaParametro sistemaParametro,
					Integer idProvisaoDevedoresDuvidososMotivoBaixa) throws ControladorException;

	public void ajustarContabilidadeArrecadacaoDeso(Integer limite) throws ControladorException;

	public void ajustarRegistrosContaEGuiaDeso() throws ControladorException;

	public void desfazerPreFaturamentoPorGrupoERef() throws ControladorException;

	// /**
	// * Método usado para obter o CodigoContaAuxiliarOrigem
	// *
	// * @param lancamentoContabilSintetico
	// * @return
	// * @throws ControladorException
	// */
	// String obterCodigoContaAuxiliarOrigemDebito(LancamentoContabilSintetico
	// lancamentoContabilSintetico)
	// throws ControladorException;

	/**
	 * Método usado para obter uma conta bancaria
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ControladorException
	 */
	String obterCodigoGrupoContaAuxiliarOrigemDebito(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ControladorException;

	// /**
	// * Método usado para obter o CodigoContaAuxiliarOrigem
	// *
	// * @param lancamentoContabilSintetico
	// * @return
	// * @throws ControladorException
	// */
	// String obterCodigoContaAuxiliarOrigemCredito(LancamentoContabilSintetico
	// lancamentoContabilSintetico) throws ControladorException;

	/**
	 * Método usado para obter uma conta bancaria
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ControladorException
	 */
	String obterCodigoGrupoContaAuxiliarOrigemCredito(LancamentoContabilSintetico lancamentoContabilSintetico) throws ControladorException;

}
