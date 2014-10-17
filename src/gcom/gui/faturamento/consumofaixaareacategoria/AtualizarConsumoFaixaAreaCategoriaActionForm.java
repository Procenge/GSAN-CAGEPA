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

package gcom.gui.faturamento.consumofaixaareacategoria;

import gcom.cadastro.imovel.Categoria;
import gcom.faturamento.consumofaixaareacategoria.ConsumoFaixaAreaCategoria;
import gcom.util.Util;

import org.apache.struts.validator.ValidatorForm;

/**
 * [UC3006] MANTER Consumo por Faixa de �rea e Categoria [SB0001]Atualizar Consumo por Faixa de �rea
 * e Categoria
 * 
 * @author Ailton Sousa
 * @date 02/03/2011
 */
public class AtualizarConsumoFaixaAreaCategoriaActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String categoria;

	private String faixaInicialArea;

	private String faixaFinalArea;

	private String consumoEstimadoArea;

	private String indicadorUso;

	private String ultimaAlteracao;

	/**
	 * M�todos Get e Set
	 */
	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getFaixaInicialArea(){

		return faixaInicialArea;
	}

	public void setFaixaInicialArea(String faixaInicialArea){

		this.faixaInicialArea = faixaInicialArea;
	}

	public String getFaixaFinalArea(){

		return faixaFinalArea;
	}

	public void setFaixaFinalArea(String faixaFinalArea){

		this.faixaFinalArea = faixaFinalArea;
	}

	public String getConsumoEstimadoArea(){

		return consumoEstimadoArea;
	}

	public void setConsumoEstimadoArea(String consumoEstimadoArea){

		this.consumoEstimadoArea = consumoEstimadoArea;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	// Esse m�todo carrega todos os valores da base de dados
	// neces�rios para exibi��o da tela AtualizarConsumoFaixaAreaCategoria.
	public ConsumoFaixaAreaCategoria setFormValues(ConsumoFaixaAreaCategoria consumoFaixaAreaCategoria){

		// Metodo usado para setar todos os valores do Form na base de dados
		if(getCategoria() != null && !getCategoria().equals("")){
			Categoria categoria = new Categoria();
			categoria.setId(Integer.valueOf(getCategoria()));
			consumoFaixaAreaCategoria.setCategoria(categoria);
		}

		consumoFaixaAreaCategoria.setFaixaInicialArea(Integer.valueOf(getFaixaInicialArea()));
		consumoFaixaAreaCategoria.setFaixaFinalArea(Integer.valueOf(getFaixaFinalArea()));
		consumoFaixaAreaCategoria.setConsumoEstimadoArea(Integer.valueOf(getConsumoEstimadoArea()));
		consumoFaixaAreaCategoria.setIndicadorUso(Short.valueOf(getIndicadorUso()));
		consumoFaixaAreaCategoria.setUltimaAlteracao(Util.converteStringParaDateHora(getUltimaAlteracao()));

		return consumoFaixaAreaCategoria;
	}

}
