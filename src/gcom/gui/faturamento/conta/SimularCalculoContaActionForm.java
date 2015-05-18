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

package gcom.gui.faturamento.conta;

import org.apache.struts.validator.ValidatorActionForm;

public class SimularCalculoContaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String mesAnoReferencia;

	private String ligacaoAguaSituacaoID;

	private String ligacaoEsgotoSituacaoID;

	private String consumoFaturadoAgua;

	private String consumoFaturadoEsgoto;

	private String percentualEsgoto;

	private String consumoTarifaID;

	private String faturamentoGrupoID;

	private Integer totalEconomia;

	private String situacaoMensagem;

	private String dataLeituraAnteriorMedicaoHistoricoAgua;

	private String dataLeituraAtualMedicaoHistoricoAgua;

	private String ligacaoEsgotoPerfilId;

	private String indicadorEsgotoFaturavel;

	private String indicadorAguaFaturavel;

	private String consumoFixoPoco;

	private String habilitarConsumoFixoPoco;

	public String getConsumoFaturadoAgua(){

		return consumoFaturadoAgua;
	}

	public void setConsumoFaturadoAgua(String consumoFaturadoAgua){

		this.consumoFaturadoAgua = consumoFaturadoAgua;
	}

	public String getConsumoFaturadoEsgoto(){

		return consumoFaturadoEsgoto;
	}

	public void setConsumoFaturadoEsgoto(String consumoFaturadoEsgoto){

		this.consumoFaturadoEsgoto = consumoFaturadoEsgoto;
	}

	public String getConsumoTarifaID(){

		return consumoTarifaID;
	}

	public void setConsumoTarifaID(String consumoTarifaID){

		this.consumoTarifaID = consumoTarifaID;
	}

	public String getFaturamentoGrupoID(){

		return faturamentoGrupoID;
	}

	public void setFaturamentoGrupoID(String faturamentoGrupoID){

		this.faturamentoGrupoID = faturamentoGrupoID;
	}

	public String getLigacaoAguaSituacaoID(){

		return ligacaoAguaSituacaoID;
	}

	public void setLigacaoAguaSituacaoID(String ligacaoAguaSituacaoID){

		this.ligacaoAguaSituacaoID = ligacaoAguaSituacaoID;
	}

	public String getLigacaoEsgotoSituacaoID(){

		return ligacaoEsgotoSituacaoID;
	}

	public void setLigacaoEsgotoSituacaoID(String ligacaoEsgotoSituacaoID){

		this.ligacaoEsgotoSituacaoID = ligacaoEsgotoSituacaoID;
	}

	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

	public String getPercentualEsgoto(){

		return percentualEsgoto;
	}

	public void setPercentualEsgoto(String percentualEsgoto){

		this.percentualEsgoto = percentualEsgoto;
	}

	/**
	 * @return Retorna o campo totalEconomia.
	 */
	public Integer getTotalEconomia(){

		return totalEconomia;
	}

	/**
	 * @param totalEconomia
	 *            O totalEconomia a ser setado.
	 */
	public void setTotalEconomia(Integer totalEconomia){

		this.totalEconomia = totalEconomia;
	}

	/**
	 * @return Retorna o campo situacaoMensagem.
	 */
	public String getSituacaoMensagem(){

		return situacaoMensagem;
	}

	/**
	 * @param situacaoMensagem
	 *            O situacaoMensagem a ser setado.
	 */
	public void setSituacaoMensagem(String situacaoMensagem){

		this.situacaoMensagem = situacaoMensagem;
	}

	public String getDataLeituraAnteriorMedicaoHistoricoAgua(){

		return dataLeituraAnteriorMedicaoHistoricoAgua;
	}

	public void setDataLeituraAnteriorMedicaoHistoricoAgua(String dataLeituraAnteriorMedicaoHistoricoAgua){

		this.dataLeituraAnteriorMedicaoHistoricoAgua = dataLeituraAnteriorMedicaoHistoricoAgua;
	}

	public String getDataLeituraAtualMedicaoHistoricoAgua(){

		return dataLeituraAtualMedicaoHistoricoAgua;
	}

	public void setDataLeituraAtualMedicaoHistoricoAgua(String dataLeituraAtualMedicaoHistoricoAgua){

		this.dataLeituraAtualMedicaoHistoricoAgua = dataLeituraAtualMedicaoHistoricoAgua;
	}

	public String getLigacaoEsgotoPerfilId(){

		return ligacaoEsgotoPerfilId;
	}

	public void setLigacaoEsgotoPerfilId(String ligacaoEsgotoPerfilId){

		this.ligacaoEsgotoPerfilId = ligacaoEsgotoPerfilId;
	}

	public String getIndicadorEsgotoFaturavel(){

		return indicadorEsgotoFaturavel;
	}

	public void setIndicadorEsgotoFaturavel(String indicadorEsgotoFaturavel){

		this.indicadorEsgotoFaturavel = indicadorEsgotoFaturavel;
	}

	public String getIndicadorAguaFaturavel(){

		return indicadorAguaFaturavel;
	}

	public void setIndicadorAguaFaturavel(String indicadorAguaFaturavel){

		this.indicadorAguaFaturavel = indicadorAguaFaturavel;
	}

	public String getConsumoFixoPoco(){

		return consumoFixoPoco;
	}

	public void setConsumoFixoPoco(String consumoFixoPoco){

		this.consumoFixoPoco = consumoFixoPoco;
	}

	public String getHabilitarConsumoFixoPoco(){

		return habilitarConsumoFixoPoco;
	}

	public void setHabilitarConsumoFixoPoco(String habilitarConsumoFixoPoco){

		this.habilitarConsumoFixoPoco = habilitarConsumoFixoPoco;
	}

}
