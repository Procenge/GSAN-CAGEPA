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

package gcom.financeiro.lancamento.bean;


/**
 * Esta classe agrupa o Id do Lan�amento de Item Cont�bil e seu indicador de remunera��o por
 * cobran�a administrativa. Esta entidade � utilizada no Parcelamento, conforme UC0214
 * 
 * @author Luciano Galvao
 * @date 29/05/2013
 */
public class LancamentoItemContabilParcelamentoHelper {

	// Constantes
	// public static final LancamentoItemContabilParcelamentoHelper TARIFA_DE_AGUA = new
	// LancamentoItemContabilParcelamentoHelper(LancamentoItemContabil.TARIFA_DE_AGUA,
	// ConstantesSistema.NAO);

	// public static final LancamentoItemContabilParcelamentoHelper TARIFA_DE_ESGOTO = new
	// LancamentoItemContabilParcelamentoHelper(LancamentoItemContabil.TARIFA_DE_ESGOTO,
	// ConstantesSistema.NAO);

	// Atributos
	private Integer idLancamentoItemContabil;

	private Short indicadorRemuneracaoCobrancaAdm;

	private Integer numeroProcessoAdministrativoExecucaoFiscal;

	private Short indicadorDividaAtiva;

	private Short indicadorExecucaoFiscal;

	// Construtores
	public LancamentoItemContabilParcelamentoHelper(Integer idLancamentoItemContabil, Short indicadorRemuneracaoCobrancaAdm,
													Integer numeroProcessoAdministrativoExecucaoFiscal, Short indicadorDividaAtiva,
													Short indicadorExecucaoFiscal) {

		this.idLancamentoItemContabil = idLancamentoItemContabil;
		this.setIndicadorRemuneracaoParcialCobrancaAdm(indicadorRemuneracaoCobrancaAdm);
		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
		this.indicadorDividaAtiva = indicadorDividaAtiva;
		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}

	public LancamentoItemContabilParcelamentoHelper() {

	}

	// Getters e Setters
	public Integer getIdLancamentoItemContabil(){

		return idLancamentoItemContabil;
	}

	public void setIdLancamentoItemContabil(Integer idLancamentoItemContabil){

		this.idLancamentoItemContabil = idLancamentoItemContabil;
	}

	public Short getIndicadorRemuneracaoParcialCobrancaAdm(){

		return indicadorRemuneracaoCobrancaAdm;
	}

	public void setIndicadorRemuneracaoParcialCobrancaAdm(Short indicadorRemuneracaoParcialCobrancaAdm){

		this.indicadorRemuneracaoCobrancaAdm = indicadorRemuneracaoParcialCobrancaAdm;
	}

	public Integer getNumeroProcessoAdministrativoExecucaoFiscal(){

		return numeroProcessoAdministrativoExecucaoFiscal;
	}

	public void setNumeroProcessoAdministrativoExecucaoFiscal(Integer numeroProcessoAdministrativoExecucaoFiscal){

		this.numeroProcessoAdministrativoExecucaoFiscal = numeroProcessoAdministrativoExecucaoFiscal;
	}

	public Short getIndicadorDividaAtiva(){

		return indicadorDividaAtiva;
	}

	public void setIndicadorDividaAtiva(Short indicadorDividaAtiva){

		this.indicadorDividaAtiva = indicadorDividaAtiva;
	}

	public Short getIndicadorExecucaoFiscal(){

		return indicadorExecucaoFiscal;
	}

	public void setIndicadorExecucaoFiscal(Short indicadorExecucaoFiscal){

		this.indicadorExecucaoFiscal = indicadorExecucaoFiscal;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((idLancamentoItemContabil == null) ? 0 : idLancamentoItemContabil.hashCode());
		result = prime
						* result
						+ ((numeroProcessoAdministrativoExecucaoFiscal == null) ? 0 : numeroProcessoAdministrativoExecucaoFiscal.hashCode());
		// result = prime * result + ((indicadorDividaAtiva == null) ? 0 :
		// indicadorDividaAtiva.hashCode());
		// result = prime * result + ((indicadorExecucaoFiscal == null) ? 0 :
		// indicadorExecucaoFiscal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		LancamentoItemContabilParcelamentoHelper other = (LancamentoItemContabilParcelamentoHelper) obj;

		if(idLancamentoItemContabil == null){
			if(other.idLancamentoItemContabil != null) return false;
		}else if(!idLancamentoItemContabil.equals(other.idLancamentoItemContabil)) return false;

		if(numeroProcessoAdministrativoExecucaoFiscal == null){
			if(other.numeroProcessoAdministrativoExecucaoFiscal != null
							&& !other.numeroProcessoAdministrativoExecucaoFiscal.equals(Integer.valueOf(0))){
				return false;
			}
		}else if(!(numeroProcessoAdministrativoExecucaoFiscal.equals(Integer.valueOf(0)) && other.numeroProcessoAdministrativoExecucaoFiscal == null)
						&& !numeroProcessoAdministrativoExecucaoFiscal.equals(other.numeroProcessoAdministrativoExecucaoFiscal)){
			return false;
		}

		// if(indicadorDividaAtiva == null){
		// if(other.indicadorDividaAtiva != null) return false;
		// }else if(!indicadorDividaAtiva.equals(other.indicadorDividaAtiva)) return false;
		//
		// if(indicadorExecucaoFiscal == null){
		// if(other.indicadorExecucaoFiscal != null) return false;
		// }else if(!indicadorExecucaoFiscal.equals(other.indicadorExecucaoFiscal)) return false;

		return true;
	}

}
