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
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.gui.micromedicao.leitura;

import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.MedicaoTipo;

public class InformarDadosLeituraAnormalidadeHelper {

	private String numeroInscricao;

	private Short indicadorFase;

	private MedicaoTipo medicaoTipo;

	private String numeroHidrometro;

	private Integer movimentoRoteiroEmpresaId;

	private String dataLeituraFormatada;

	private Integer numeroLeitura;

	private LeituraAnormalidade leituraAnormalidade;

	private Integer anoMesMovimento;

	private String dataLeituraAnteriorFormatada;

	public final String getNumeroInscricao(){

		return numeroInscricao;
	}

	public final void setNumeroInscricao(String numeroInscricao){

		this.numeroInscricao = numeroInscricao;
	}

	public final Short getIndicadorFase(){

		return indicadorFase;
	}

	public final void setIndicadorFase(Short indicadorFase){

		this.indicadorFase = indicadorFase;
	}

	public final MedicaoTipo getMedicaoTipo(){

		return medicaoTipo;
	}

	public final void setMedicaoTipo(MedicaoTipo medicaoTipo){

		this.medicaoTipo = medicaoTipo;
	}

	public final String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public final void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public final Integer getMovimentoRoteiroEmpresaId(){

		return movimentoRoteiroEmpresaId;
	}

	public final void setMovimentoRoteiroEmpresaId(Integer movimentoRoteiroEmpresaId){

		this.movimentoRoteiroEmpresaId = movimentoRoteiroEmpresaId;
	}



	public final Integer getNumeroLeitura(){

		return numeroLeitura;
	}

	public final void setNumeroLeitura(Integer numeroLeitura){

		this.numeroLeitura = numeroLeitura;
	}

	public final LeituraAnormalidade getLeituraAnormalidade(){

		return leituraAnormalidade;
	}

	public final void setLeituraAnormalidade(LeituraAnormalidade leituraAnormalidade){

		this.leituraAnormalidade = leituraAnormalidade;
	}

	public final Integer getAnoMesMovimento(){

		return anoMesMovimento;
	}

	public final void setAnoMesMovimento(Integer anoMesMovimento){

		this.anoMesMovimento = anoMesMovimento;
	}

	public final String getDataLeituraFormatada(){

		return dataLeituraFormatada;
	}

	public final void setDataLeituraFormatada(String dataLeituraFormatada){

		this.dataLeituraFormatada = dataLeituraFormatada;
	}

	public final String getDataLeituraAnteriorFormatada(){

		return dataLeituraAnteriorFormatada;
	}

	public final void setDataLeituraAnteriorFormatada(String dataLeituraAnteriorFormatada){

		this.dataLeituraAnteriorFormatada = dataLeituraAnteriorFormatada;
	}

}
