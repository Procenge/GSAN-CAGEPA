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

package gcom.cobranca;

import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.ErroRepositorioException;

import java.io.IOException;

import javax.ejb.CreateException;

/**
 * Interface Controlador Cobranca Ordem Corte PADR�O
 * 
 * @author Josenildo Neves
 * @date 22/05/2013
 */
public interface IControladorCobrancaOrdemCorte {

	/**
	 * [UC0476] Emitir Documento de Cobran�a � Ordem de Corte
	 * [SB0007] - Modelo 4
	 * 
	 * @author Andre Lopes
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	void gerarOrdemCorteArquivoPDFModelo4(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * [UC0476] Emitir Documento de Cobran�a � Ordem de Corte
	 * [SB0008] - Modelo 5
	 * 
	 * @author Andre Lopes
	 * @param cobrancaAcaoAtividadeCronograma
	 * @param cobrancaAcaoAtividadeComando
	 * @param usuario
	 * @param idFuncionalidadeIniciada
	 * @throws ControladorException
	 */
	void gerarOrdemCorteArquivoPDFModelo5(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario, Integer idFuncionalidadeIniciada)
					throws ControladorException;

	/**
	 * [UC0349] Emitir Documento de Cobran�a � Aviso de Corte
	 * [SB0009] � Gerar Arquivo PDF Aviso de Corte � Modelo 5 (CAERD)
	 * 
	 * @author Josenildo Neves
	 * @date 21/05/2013
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	void gerarArquivoPdfAvisoCorteModelo5(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario) throws ControladorException;

	/**
	 * [UC0349] Emitir Documento de Cobran�a � Aviso de Corte
	 * [SB0009] � Gerar Arquivo PDF Aviso de Corte � Modelo 5 (CAERD)
	 * 
	 * @author Vicente Zarga
	 * @date 14/06/2013
	 * @throws ControladorException
	 * @throws ErroRepositorioException
	 * @throws IOException
	 * @throws CreateException
	 */
	void gerarArquivoPdfAvisoCorteModelo4(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma,
					CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando, Usuario usuario) throws ControladorException;

}
