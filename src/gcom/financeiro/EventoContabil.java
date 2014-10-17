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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 *
 * GSANPCG
 * Eduardo Henrique
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

package gcom.financeiro;

/**
 * Interface que possui todas as Constantes de Tipos de Eventos
 * de Contabiliza��o de Eventos
 * 
 * @author eduardo henrique
 * @date 23/10/2008
 */
public interface EventoContabil {

	public static Integer INCLUSAO_GUIA_PAGAMENTO = new Integer("0");

	public static Integer CANCELAMENTO_GUIA_PAGAMENTO = new Integer("1");

	public static Integer FATURAMENTO_CONTA = new Integer("2");

	public static Integer INCLUSAO_DEBITO_A_COBRAR = new Integer("3");

	public static Integer INCLUSAO_CREDITO_A_REALIZAR = new Integer("4");

	public static Integer CANCELAMENTO_CREDITO_A_REALIZAR = new Integer("5");

	public static Integer CANCELAMENTO_DEBITO_A_COBRAR = new Integer("6");

	public static Integer INCLUSAO_CONTA = new Integer("7");

	public static Integer CANCELAMENTO_CONTA = new Integer("8");

	public static Integer INCLUSAO_GUIA_DEVOLUCAO = new Integer("9");
}
