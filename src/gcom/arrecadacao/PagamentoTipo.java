/* This file is part of GSAN, an integrated service management system for Sanitation
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
 * GSANPCG
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
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

package gcom.arrecadacao;

import java.io.Serializable;
import java.util.Date;

/**
 * Interface que possui as constantes de Tipos de Pagamento
 * 
 * @author Saulo Lima
 * @date 04/12/2008
 */
public class PagamentoTipo
				implements Serializable {

	// Conta e Conta Segunda Via
	public static final int PAGAMENTO_TIPO_CONTA = 3;

	// Guia de Pagamento
	public static final int PAGAMENTO_TIPO_GUIA_PAGAMENTO_MATRICULA_IMOVEL = 4;

	public static final int PAGAMENTO_TIPO_GUIA_PAGAMENTO_CODIGO_CLIENTE = 6;

	// Cobrança
	public static final int PAGAMENTO_TIPO_COBANCA_MATRICULA_IMOVEL = 5;

	public static final int PAGAMENTO_TIPO_COBANCA_CODIGO_CLIENTE = 8;

	public static final int PAGAMENTO_TIPO_COBANCA_PRE_PARCELAMENTO = 2;

	// Fatura Cliente Responsável
	public static final int PAGAMENTO_TIPO_CLIENTE_RESPONSAVEL = 7;

	public static final int PAGAMENTO_TIPO_NOTA_RECEBIMENTO = 1;

	// CAERN - Legado Conta
	public static final int PAGAMENTO_TIPO_LEGADO_CONTA_CAERN = 0;

	// Legado DESO
	public static final int PAGAMENTO_TIPO_LEGADO_CONTA_SEGUNDA_VIA_DESO = 82;

	public static final int PAGAMENTO_TIPO_LEGADO_AVISO_CORTE_DEBITO_DESO = 90;

	public static final int PAGAMENTO_TIPO_LEGADO_CONTA_AGRUPADAS_DESO = 91;

	public static final int PAGAMENTO_TIPO_LEGADO_CARNE_DESO = 94;

	public static final int PAGAMENTO_TIPO_LEGADO_NOTA_RECEBIMENTO_DESO = 15;

	public static final int PAGAMENTO_TIPO_LEGADO_DESO_01 = 1;

	public static final int PAGAMENTO_TIPO_LEGADO_DESO_02 = 2;

	private Integer id;

	private String descricao;

	private String descricaoAbreviada;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
