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

package gcom.integracao.acquagis;

import gcom.util.ErroRepositorioException;

/**
 * Interface para o repositório de integração com as tabela do sistema AcquaGis no GSAN
 * 
 * @author Josenildo Neves
 * @created 02 de Julho de 2013
 */
public interface IRepositorioIntegracaoAcquaGis {

	/**
	 * OC1054974 - Sem UC.
	 * 
	 * @author Josenildo Neves
	 * @created 02 de Julho de 2013
	 * @throws ErroRepositorioException
	 */
	void excluirContaAtualizada() throws ErroRepositorioException;
}
