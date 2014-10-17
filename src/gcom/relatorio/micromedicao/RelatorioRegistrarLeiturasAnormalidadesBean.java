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

package gcom.relatorio.micromedicao;

import gcom.relatorio.RelatorioBean;

/**
 * <p>
 * Title: GCOM
 * </p>
 * <p>
 * Description: Sistema de Gest�o Comercial
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: COMPESA - Companhia Pernambucana de Saneamento
 * </p>
 * 
 * @author Rafael Corr�a
 * @version 1.0
 */

public class RelatorioRegistrarLeiturasAnormalidadesBean
				implements RelatorioBean {

	private Integer localidadeId;

	private Integer rotaId;

	private Integer quantidadeMovimentosLeitura;

	private Integer quantidadeMovimentosAnormalidade;

	private Integer quantidadeMovimentosLeituraAnormalidade;

	private Integer quantidadeMovimentos;

	public RelatorioRegistrarLeiturasAnormalidadesBean(Integer localidadeId, Integer rotaId, Integer quantidadeMovimentosLeitura,
														Integer quantidadeMovimentosAnormalidade,
														Integer quantidadeMovimentosLeituraAnormalidade, Integer quantidadeMovimentos) {

		super();
		this.localidadeId = localidadeId;
		this.rotaId = rotaId;
		this.quantidadeMovimentosLeitura = quantidadeMovimentosLeitura;
		this.quantidadeMovimentosAnormalidade = quantidadeMovimentosAnormalidade;
		this.quantidadeMovimentosLeituraAnormalidade = quantidadeMovimentosLeituraAnormalidade;
		this.quantidadeMovimentos = quantidadeMovimentos;
	}

	public Integer getLocalidadeId(){

		return localidadeId;
	}

	public void setLocalidadeId(Integer localidadeId){

		this.localidadeId = localidadeId;
	}

	public Integer getRotaId(){

		return rotaId;
	}

	public void setRotaId(Integer rotaId){

		this.rotaId = rotaId;
	}

	public Integer getQuantidadeMovimentosLeitura(){

		return quantidadeMovimentosLeitura;
	}

	public void setQuantidadeMovimentosLeitura(Integer quantidadeMovimentosLeitura){

		this.quantidadeMovimentosLeitura = quantidadeMovimentosLeitura;
	}

	public Integer getQuantidadeMovimentosAnormalidade(){

		return quantidadeMovimentosAnormalidade;
	}

	public void setQuantidadeMovimentosAnormalidade(Integer quantidadeMovimentosAnormalidade){

		this.quantidadeMovimentosAnormalidade = quantidadeMovimentosAnormalidade;
	}

	public Integer getQuantidadeMovimentosLeituraAnormalidade(){

		return quantidadeMovimentosLeituraAnormalidade;
	}

	public void setQuantidadeMovimentosLeituraAnormalidade(Integer quantidadeMovimentosLeituraAnormalidade){

		this.quantidadeMovimentosLeituraAnormalidade = quantidadeMovimentosLeituraAnormalidade;
	}

	public Integer getQuantidadeMovimentos(){

		return quantidadeMovimentos;
	}

	public void setQuantidadeMovimentos(Integer quantidadeMovimentos){

		this.quantidadeMovimentos = quantidadeMovimentos;
	}

}
