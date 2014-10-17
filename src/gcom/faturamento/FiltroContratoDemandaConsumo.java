package gcom.faturamento;

import gcom.util.filtro.Filtro;

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
 * Copyright (C) <2014> 
 * Vicente Zarga
 */
public class FiltroContratoDemandaConsumo
				extends Filtro {

	private static final long serialVersionUID = 1L;

	public final static String ID = "id";

	public final static String MUMEROCONTRATO = "numeroContrato";

	public final static String IMOVEL_ID = "imovel.id";

	public final static String INDICADORENCERRAMENTO = "indicacorEncerramento";

	public final static String ANOMESFATURAMENTOFIM = "anoMesFaturamentoFim";

	public final static String ANOMESFATURAMENTOINICIO = "anoMesFaturamentoInicio";

	public final static String CONSUMO_TARIFA = "consumoTarifa";

	public final static String NN_CONSUMO_FIXO = "numeroConsumoFixo";

	public FiltroContratoDemandaConsumo(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public FiltroContratoDemandaConsumo() {

	}

}
