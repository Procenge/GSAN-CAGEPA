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

package gcom.operacional;

import gcom.util.filtro.Filtro;

/**
 * Descri��o da classe
 * 
 * @author Rafael Pinto
 * @date 04/12/2006
 */
public class FiltroSetorAbastecimento
				extends Filtro {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroSetorAbastecimento object
	 */
	public FiltroSetorAbastecimento() {

	}

	/**
	 * Constructor for the FiltroSetorAbastecimento object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroSetorAbastecimento(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	/**
	 * Description of the Field
	 */
	public final static String ID = "id";

	public final static String INDICADOR_USO = "indicadorUso";

	public final static String SISTEMA_ABASTECIMENTO_ID = "sistemaAbastecimento.id";

	public final static String CODIGO_SETOR_ABASTECIMENTO = "codigoSetorAbastecimento";

	public final static String UNIDADE_OPERACIONAL_ID = "unidadeOperacional.id";

	// public final static String UNIDADE_OPERACIONAL = "unidadeOperacional";

	// public final static String UNIDADE_OPERACIONAL_LOCALIDADE = "unidadeOperacional.localidade";

	public final static String SISTEMA_ABASTECIMENTO = "sistemaAbastecimento";

	public final static String ZONA_ABASTECIMENTO_LOCALIDADE = "zonaAbastecimento.localidade";

	public final static String DISTRITO_OPERACIONAL = "distritoOperacional";

	public final static String DISTRITO_OPERACIONAL_LOCALIDADE = "distritoOperacional.localidade";

	public final static String DISTRITO_OPERACIONAL_ID = "distritoOperacional.id";

	public final static String ZONA_ABASTECIMENTO_ID = "zonaAbastecimento.id";

	public final static String ZONA_ABASTECIMENTO = "zonaAbastecimento";

	public static final String DESCRICAO = "descricao";

	public static final String DESCRICAO_ABREVIADA = "descricaoAbreviada";
	
	public static final String EXTENSAO_REDE= "extensaoRede";
	
	public static final String PRESSAO_MAXIMA = "pressaoMaxima";

	public static final String PRESSAO_MEDIA = "pressaoMedia";

	public static final String PRESSAO_MINIMA = "pressaoMinima";

	public static final String MONITORAMENTO_INICIAL = "monitoramentoInicial";

	public static final String MONITORAMENTO_FINAL = "monitoramentoFinal";

	public static final String ID_DIAMENTRO = "diametroRedeAgua.id";
	

}
