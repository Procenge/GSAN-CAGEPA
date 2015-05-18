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
 * Copyright (C) <2012>
 * 
 * Andr� Lopes  
 * Saulo Lima
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

package gcom.seguranca.acesso;

import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Argumento
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** Representa o Id do argumento. */
	public static final Integer IMOVEL = 1;

	/** Representa o Id do argumento. */
	public static final Integer CLIENTE = 2;

	/** Representa o Id do argumento. */
	public static final Integer ORDEM_SERVICO = 3;

	/** Representa o Id do argumento. */
	public static final Integer HIDROMETRO = 4;

	/** Representa o Id do argumento. */
	public static final Integer COBRANCA_ACAO = 5;

	/** Representa o Id do argumento. */
	public static final Integer LOCALIDADE = 6;

	/** Representa o Id do argumento. */
	public static final Integer MUNICIPIO = 7;

	/** Representa o Id do argumento. */
	public static final Integer SETOR_COMERCIAL = 8;

	/** Representa o Id do argumento. */
	public static final Integer UNIDADE_OPERACIONAL = 9;

	/** Representa o Id do argumento. */
	public static final Integer AVISO_BANCARIO = 10;

	/** Representa o Id do argumento. */
	public static final Integer REGISTRO_ATENDIMENTO = 11;

	/** Representa o Id do argumento. */
	public static final Integer RESOLUCAO_DIRETORIA = 12;

	/** Representa o Id do argumento. */
	public static final Integer RESOLUCAO_DIRETORIA_PARCELAMENTO = 13;

	/** Representa o Id do argumento. */
	public static final Integer PAGAMENTO = 14;

	/** default constructor */
	public Argumento() {

	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

}
