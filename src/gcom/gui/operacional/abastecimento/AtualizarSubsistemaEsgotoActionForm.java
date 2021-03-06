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

package gcom.gui.operacional.abastecimento;

import gcom.cadastro.localidade.Localidade;
import gcom.operacional.EsgotoTratamentoTipo;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubsistemaEsgoto;
import gcom.util.Util;

import org.apache.struts.validator.ValidatorForm;

/**
 * [UCXXXX] Manter Subsistema Esgoto [SB0001]Atualizar Sistema Esgoto
 * 
 * @author Ailton Sousa
 * @date 28/01/2011
 */

public class AtualizarSubsistemaEsgotoActionForm
				extends ValidatorForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricaoSubsistemaEsgoto;

	private String descricaoAbreviada;

	private String sistemaEsgoto;

	private String indicadorUso;

	private String ultimaAlteracao;

	private String localidade;

	private String esgotoTratamentoTipo;

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getDescricaoSubsistemaEsgoto(){

		return descricaoSubsistemaEsgoto;
	}

	public void setDescricaoSubsistemaEsgoto(String descricaoSubsistemaEsgoto){

		this.descricaoSubsistemaEsgoto = descricaoSubsistemaEsgoto;
	}

	public String getSistemaEsgoto(){

		return sistemaEsgoto;
	}

	public void setSistemaEsgoto(String sistemaEsgoto){

		this.sistemaEsgoto = sistemaEsgoto;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getEsgotoTratamentoTipo(){

		return esgotoTratamentoTipo;
	}

	public void setEsgotoTratamentoTipo(String esgotoTratamentoTipo){

		this.esgotoTratamentoTipo = esgotoTratamentoTipo;
	}

	// Esse m�todo carrega todos os valores da base de dados
	// neces�rios para exibi��o da tela AtualizarSistemaEsgoto.

	public SubsistemaEsgoto setFormValues(SubsistemaEsgoto subsistemaEsgoto){

		// Metodo usado para setar todos os valores do Form na base de dados

		subsistemaEsgoto.setCodigo(Integer.parseInt(getCodigo()));
		subsistemaEsgoto.setDescricao(getDescricaoSubsistemaEsgoto());
		subsistemaEsgoto.setDescricaoAbreviada(getDescricaoAbreviada());

		if(!Util.isVazioOuBranco(getSistemaEsgoto())){
			SistemaEsgoto sistemaEsgoto = new SistemaEsgoto();
			sistemaEsgoto.setId(new Integer(getSistemaEsgoto()));
			subsistemaEsgoto.setSistemaEsgoto(sistemaEsgoto);
		}

		if(!Util.isVazioOuBranco(getLocalidade())){
			Localidade localidade = new Localidade();
			localidade.setId(Integer.parseInt(getLocalidade()));
			subsistemaEsgoto.setLocalidade(localidade);
		}

		if(!Util.isVazioOuBranco(getEsgotoTratamentoTipo())){
			EsgotoTratamentoTipo esgotoTratamentoTipo = new EsgotoTratamentoTipo();
			esgotoTratamentoTipo.setId(Integer.parseInt(getEsgotoTratamentoTipo()));
			subsistemaEsgoto.setEsgotoTratamentoTipo(esgotoTratamentoTipo);
		}

		subsistemaEsgoto.setIndicadorUso(new Short(getIndicadorUso()));
		subsistemaEsgoto.setUltimaAlteracao(Util.converteStringParaDateHora(getUltimaAlteracao()));

		return subsistemaEsgoto;
	}

}
