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

package gcom.relatorio.faturamento;


public class RelatorioImoveisLigacaoCortadaComConsumoHelper {

	private String localidade;

	private String setorComercial;

	private String quadra;

	private String lote;

	private String matriculaImovel;

	private String nomeCliente;

	private String dtCorte;

	private String leituraCorteLaguId;

	private String leituraCorteImovId;

	private String consumoAnterior;

	private String refConsumoAnterior;

	private String leituraAnteriorLaguId;

	private String leituraAtualLaguId;

	private String leituraAnteriorImovId;

	private String leituraAtualImovId;

	private String consumoMes;

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(String setorComercial){

		this.setorComercial = setorComercial;
	}

	public String getQuadra(){

		return quadra;
	}

	public void setQuadra(String quadra){

		this.quadra = quadra;
	}

	public String getLote(){

		return lote;
	}

	public void setLote(String lote){

		this.lote = lote;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getDtCorte(){

		return dtCorte;
	}

	public void setDtCorte(String dtCorte){

		this.dtCorte = dtCorte;
	}



	public String getLeituraCorteLaguId(){

		return leituraCorteLaguId;
	}

	public void setLeituraCorteLaguId(String leituraCorteLaguId){

		this.leituraCorteLaguId = leituraCorteLaguId;
	}

	public String getLeituraCorteImovId(){

		return leituraCorteImovId;
	}

	public void setLeituraCorteImovId(String leituraCorteImovId){

		this.leituraCorteImovId = leituraCorteImovId;
	}

	public String getConsumoAnterior(){

		return consumoAnterior;
	}

	public void setConsumoAnterior(String consumoAnterior){

		this.consumoAnterior = consumoAnterior;
	}

	public String getRefConsumoAnterior(){

		return refConsumoAnterior;
	}

	public void setRefConsumoAnterior(String refConsumoAnterior){

		this.refConsumoAnterior = refConsumoAnterior;
	}

	public String getLeituraAnteriorLaguId(){

		return leituraAnteriorLaguId;
	}

	public void setLeituraAnteriorLaguId(String leituraAnteriorLaguId){

		this.leituraAnteriorLaguId = leituraAnteriorLaguId;
	}

	public String getLeituraAtualLaguId(){

		return leituraAtualLaguId;
	}

	public void setLeituraAtualLaguId(String leituraAtualLaguId){

		this.leituraAtualLaguId = leituraAtualLaguId;
	}

	public String getLeituraAnteriorImovId(){

		return leituraAnteriorImovId;
	}

	public void setLeituraAnteriorImovId(String leituraAnteriorImovId){

		this.leituraAnteriorImovId = leituraAnteriorImovId;
	}

	public String getLeituraAtualImovId(){

		return leituraAtualImovId;
	}

	public void setLeituraAtualImovId(String leituraAtualImovId){

		this.leituraAtualImovId = leituraAtualImovId;
	}

	public String getConsumoMes(){

		return consumoMes;
	}

	public void setConsumoMes(String consumoMes){

		this.consumoMes = consumoMes;
	}


}
