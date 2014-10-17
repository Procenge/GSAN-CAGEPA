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
 * Eduardo Henrique
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

package gcom.financeiro;

/**
 * Interface que possui todas as Constantes de Tipos de Eventos
 * de Contabilização de Eventos
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
