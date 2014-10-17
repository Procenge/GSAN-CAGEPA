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

package gcom.gerencial.faturamento;

import java.util.Collection;
import java.util.List;

import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

/**
 * @author Thiago Toscano
 * @created 19/04/2006
 */
public interface ControladorGerencialFaturamentoLocal
				extends javax.ejb.EJBLocalObject {

	/**
	 * M�todo que gera o resumo das liga��es e economias
	 * [UC0275] - Gerar Resumo das Liga��es/Economias
	 * 
	 * @author Thiago Toscano
	 * @date 19/04/2006
	 */
	public void gerarResumoSituacaoEspecialFaturamento(int idLocalidade, int idFuncionalidadeIniciada) throws ControladorException;

	public Collection recuperaResumoSituacaoEspecialFaturamento(Integer[] idSituacaoTipo, Integer[] idSituacaoMotivo)
					throws ControladorException;

	/**
	 * Este caso de uso permite consultar o resumo da pend�ncia, com a op��o de impress�o da
	 * consulta.
	 * Dependendo da op��o de totaliza��o sempre � gerado o relat�rio, sem a fera��o da consulta.
	 * [UC0305] Consultar An�lise do Faturamento
	 * consultarResumoAnaliseFaturamento
	 * 
	 * @author Fernanda Paiva
	 * @date 31/05/2006
	 * @param informarDadosGeracaoRelatorioConsultaHelper
	 * @return
	 * @throws ControladorException
	 */
	public List consultarResumoAnaliseFaturamento(InformarDadosGeracaoRelatorioConsultaHelper informarDadosGeracaoRelatorioConsultaHelper)
					throws ControladorException;

	/**
	 * M�todo que gera o resumo do Faturamento
	 * [UC0571] - Gerar Resumo do Faturamento
	 * 
	 * @author Marcio Roberto
	 * @param idLocalidade
	 * @param anoMes
	 * @date 12/05/2007
	 */
	public void gerarResumoFaturamentoAguaEsgoto(int idSetor, int idFuncionalidadeIniciada, int anoMes) throws ControladorException;

	/**
	 * M�todo que gera o resumo do FaturamentoOutros
	 * [UC0565] - Gerar Resumo do FaturamentoOutros
	 * 
	 * @author Marcio Roberto
	 * @param idLocalidade
	 * @param anoMes
	 * @date 04/07/2007
	 */

	public void gerarResumoFaturamentoOutros(int idSetor, int anoMes, int indice, int qtRegistros, List resumo)
					throws ControladorException, ErroRepositorioException;

	/**
	 * M�todo que gera o resumo do FaturamentoCreditos
	 * [UC0565] - Gerar Resumo do FaturamentoCreditos
	 * 
	 * @author Marcio Roberto
	 * @param idLocalidade
	 * @param anoMes
	 * @date 04/07/2007
	 */
	public void gerarResumoFaturamentoCreditos(int idSetor, int anoMes, int indice, int qtRegistros, List resumo)
					throws ControladorException, ErroRepositorioException;

	public Collection pesquisarIdsSetores() throws ControladorException;

	/**
	 * gerarResumoFaturamentoDebitoACobrar
	 * Marcio Roberto - 11/07/2007
	 * 
	 * @param idSetor
	 * @param anoMes
	 * @param indice
	 * @param qtRegistros
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 */
	public void gerarResumoFaturamentoDebitosACobrar(int idSetor, int anoMes) throws ControladorException, ErroRepositorioException;

}
