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
 * 
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

package gcom.integracao.piramide;

import gcom.util.ControladorException;

/**
 * Interface Controlador Integra��o PADR�O
 * 
 * @author Josenildo Neves
 * @date 15/08/2012
 */
public interface IControladorIntegracaoPiramide {

	/**
	 * Este caso de uso permite gerar os dados de integra��o com o Sistema Cont�bil da empresa.
	 * [UC3065] Gerar Integra��o Cont�bil
	 * 
	 * @author Josenildo Neves
	 * @data 15/08/2012
	 * @param
	 * @return void
	 */
	public void gerarIntegracaoContabil(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Metodo tempor�rio para ajuste cont�bil. Analista de neg�cio Lu�s Eduardo
	 * Ocorr�ncia: OC1128813
	 * 
	 * @author Saulo Lima
	 * @since 20/08/2013
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarIntegracaoContabilAjusteTemp(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Este caso de uso realiza a gera��o dos dados de Integra��o relativos � reten��o com o Sistema Financeiro da empresa atrav�s de tabelas de integra��o.
	 * [UC3066] Gerar Integra��o Reten��o
	 * 
	 * @author Josenildo Neves
	 * @data 15/08/2012
	 * @param
	 * @return void
	 */
	public void gerarIntegracaoRetencao(int idFuncionalidadeIniciada) throws ControladorException;


	/**
	 * M�todo gerarIntegracaoDeferimento
	 * <p>
	 * Esse m�todo implementa a integra��o deferimento.
	 * </p>
	 * RASTREIO: [OC827869][UC3067]
	 * 
	 * @param idFuncionalidadeIniciada
	 *            : id da Funcionalidade Iniciada
	 * @throws ControladorException
	 * @author Marlos Ribeiro
	 * @since 01/10/2012
	 */
	void gerarIntegracaoDeferimento(Integer idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * M�todo gerarIntegracaoSpedPisCofins
	 * <p>
	 * Esse m�todo implementa o processo de geracao da integra��o Sped Pis/Cofins
	 * </p>
	 * RASTREIO: [OC777360][UC3078]
	 * 
	 * @param idFuncionalidadeIniciada
	 * @author Marlos Ribeiro
	 * @param idLocalidade
	 * @param referenciaBase
	 * @since 14/03/2013
	 */
	void gerarIntegracaoSpedPisCofins(Integer idFuncionalidadeIniciada) throws ControladorException;

}
