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
 * 
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

package gcom.integracao.piramide;

import gcom.util.ControladorException;

/**
 * Interface Controlador Integração PADRÃO
 * 
 * @author Josenildo Neves
 * @date 15/08/2012
 */
public interface IControladorIntegracaoPiramide {

	/**
	 * Este caso de uso permite gerar os dados de integração com o Sistema Contábil da empresa.
	 * [UC3065] Gerar Integração Contábil
	 * 
	 * @author Josenildo Neves
	 * @data 15/08/2012
	 * @param
	 * @return void
	 */
	public void gerarIntegracaoContabil(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Metodo temporário para ajuste contábil. Analista de negócio Luís Eduardo
	 * Ocorrência: OC1128813
	 * 
	 * @author Saulo Lima
	 * @since 20/08/2013
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	public void gerarIntegracaoContabilAjusteTemp(int idFuncionalidadeIniciada) throws ControladorException;

	/**
	 * Este caso de uso realiza a geração dos dados de Integração relativos à retenção com o Sistema Financeiro da empresa através de tabelas de integração.
	 * [UC3066] Gerar Integração Retenção
	 * 
	 * @author Josenildo Neves
	 * @data 15/08/2012
	 * @param
	 * @return void
	 */
	public void gerarIntegracaoRetencao(int idFuncionalidadeIniciada) throws ControladorException;


	/**
	 * Método gerarIntegracaoDeferimento
	 * <p>
	 * Esse método implementa a integração deferimento.
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
	 * Método gerarIntegracaoSpedPisCofins
	 * <p>
	 * Esse método implementa o processo de geracao da integração Sped Pis/Cofins
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
