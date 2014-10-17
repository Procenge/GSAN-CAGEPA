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
 * Description: Sistema de Gest�o Comercial
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
	 * Transfere todas as parcelas de longo para curto prazo dos d�bitos informados.
	 * 
	 * @param debitosACobrar
	 *            D�bitos cujas parcela devem ser transferidas.
	 * @throws ControladorException
	 */
	public void transferirDebitosParaCurtoPrazo(Collection<DebitoACobrar> debitosACobrar) throws ControladorException;

	/**
	 * Prepara os dados necess�rios e chama a rotina de registro dos lan�amentos cont�beis
	 * sint�ticos e anal�ticos.
	 * 
	 * @param objeto
	 * @param operacao
	 * @throws Exception
	 */
	void registrarLancamentoContabil(Object objeto, OperacaoContabil operacaoContabil) throws ControladorException;

	/**
	 * Prepara os dados necess�rios e chama a rotina de registro dos lan�amentos cont�beis
	 * sint�ticos e anal�ticos.
	 * 
	 * @param objeto
	 * @param operacao
	 * @throws Exception
	 */
	public void registrarLancamentoContabil(Object objeto, OperacaoContabil operacaoContabil,
					Collection<GuiaPagamentoCategoria> colecaoGuiaPagamentoCategoria)
					throws ControladorException;

	/**
	 * M�todo respons�vel por obter o eventoComercial atrav�s do id
	 * 
	 * @param id
	 * @return eventoComercial
	 * @throws ErroRepositorioException
	 */
	public EventoComercial obterEventoComercial(Integer id) throws ControladorException;

	/**
	 * M�todo respons�vel por consultar e montar os LancamentoContabilAnaliticoConsultaHelper
	 * 
	 * @param lancamentoContabilSintetico
	 *            o filtro de pesquisa
	 * @return os LancamentoContabilAnaliticoConsultaHelper
	 * @throws ErroRepositorioException
	 */
	public Collection<LancamentoContabilAnaliticoConsultaHelper> consultarLancamentoContabilAnaliticoPorSintetico(
					LancamentoContabilSintetico lancamentoContabilSintetico) throws ControladorException;

	/**
	 * [UCXXXX] Provis�o de devedores duvidosos
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
	 * Atualizar registro de provis�o de devedores duvidosos e realizar o lan�amento cont�bil do
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
	// * M�todo usado para obter o CodigoContaAuxiliarOrigem
	// *
	// * @param lancamentoContabilSintetico
	// * @return
	// * @throws ControladorException
	// */
	// String obterCodigoContaAuxiliarOrigemDebito(LancamentoContabilSintetico
	// lancamentoContabilSintetico)
	// throws ControladorException;

	/**
	 * M�todo usado para obter uma conta bancaria
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ControladorException
	 */
	String obterCodigoGrupoContaAuxiliarOrigemDebito(LancamentoContabilSintetico lancamentoContabilSintetico)
					throws ControladorException;

	// /**
	// * M�todo usado para obter o CodigoContaAuxiliarOrigem
	// *
	// * @param lancamentoContabilSintetico
	// * @return
	// * @throws ControladorException
	// */
	// String obterCodigoContaAuxiliarOrigemCredito(LancamentoContabilSintetico
	// lancamentoContabilSintetico) throws ControladorException;

	/**
	 * M�todo usado para obter uma conta bancaria
	 * 
	 * @param lancamentoContabilSintetico
	 * @return
	 * @throws ControladorException
	 */
	String obterCodigoGrupoContaAuxiliarOrigemCredito(LancamentoContabilSintetico lancamentoContabilSintetico) throws ControladorException;

}
