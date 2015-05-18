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

package gcom.micromedicao.bean;

import java.io.Serializable;

/**
 * Relatorio Ordem de Servi�o
 * 
 * @author Vivianne Sousa
 * @date 08/06/2007
 */
public class HidrometroRelatorioOSHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String hidrometroNumero;

	private String hidrometroFixo;

	private String hidrometroMarca;

	private String hidrometroCapacidade;

	private String hidrometroDiametro;

	private String hidrometroLocal;

	private String hidrometroLeitura;

	private String hidrometroNumeroDigitos;

	private String dataInstalacaoHidrometo;

	private String idProtecaoHidrometro;

	private String descricaoProtecaoHidrometro;

	private String idTipoHidrometro;

	private String descricaoTipoHidrometro;

	private String indicadorCavalete;

	private String anoFabricacaoHidrometro;

	private String descricaoLocalInstalacao;

	public String getHidrometroCapacidade(){

		return hidrometroCapacidade;
	}

	public void setHidrometroCapacidade(String hidrometroCapacidade){

		this.hidrometroCapacidade = hidrometroCapacidade;
	}

	public String getHidrometroDiametro(){

		return hidrometroDiametro;
	}

	public void setHidrometroDiametro(String hidrometroDiametro){

		this.hidrometroDiametro = hidrometroDiametro;
	}

	public String getHidrometroFixo(){

		return hidrometroFixo;
	}

	public void setHidrometroFixo(String hidrometroFixo){

		this.hidrometroFixo = hidrometroFixo;
	}

	public String getHidrometroLeitura(){

		return hidrometroLeitura;
	}

	public void setHidrometroLeitura(String hidrometroLeitura){

		this.hidrometroLeitura = hidrometroLeitura;
	}

	public String getHidrometroLocal(){

		return hidrometroLocal;
	}

	public void setHidrometroLocal(String hidrometroLocal){

		this.hidrometroLocal = hidrometroLocal;
	}

	public String getHidrometroMarca(){

		return hidrometroMarca;
	}

	public void setHidrometroMarca(String hidrometroMarca){

		this.hidrometroMarca = hidrometroMarca;
	}

	public String getHidrometroNumero(){

		return hidrometroNumero;
	}

	public void setHidrometroNumero(String hidrometroNumero){

		this.hidrometroNumero = hidrometroNumero;
	}

	public String getHidrometroNumeroDigitos(){

		return hidrometroNumeroDigitos;
	}

	public void setHidrometroNumeroDigitos(String hidrometroNumeroDigitos){

		this.hidrometroNumeroDigitos = hidrometroNumeroDigitos;
	}

	public String getDataInstalacaoHidrometo(){

		return dataInstalacaoHidrometo;
	}

	public void setDataInstalacaoHidrometo(String dataInstalacaoHidrometo){

		this.dataInstalacaoHidrometo = dataInstalacaoHidrometo;
	}

	public String getIdProtecaoHidrometro(){

		return idProtecaoHidrometro;
	}

	public void setIdProtecaoHidrometro(String idProtecaoHidrometro){

		this.idProtecaoHidrometro = idProtecaoHidrometro;
	}

	public String getDescricaoProtecaoHidrometro(){

		return descricaoProtecaoHidrometro;
	}

	public void setDescricaoProtecaoHidrometro(String descricaoProtecaoHidrometro){

		this.descricaoProtecaoHidrometro = descricaoProtecaoHidrometro;
	}

	public String getIdTipoHidrometro(){

		return idTipoHidrometro;
	}

	public void setIdTipoHidrometro(String idTipoHidrometro){

		this.idTipoHidrometro = idTipoHidrometro;
	}

	public String getDescricaoTipoHidrometro(){

		return descricaoTipoHidrometro;
	}

	public void setDescricaoTipoHidrometro(String descricaoTipoHidrometro){

		this.descricaoTipoHidrometro = descricaoTipoHidrometro;
	}

	public String getIndicadorCavalete(){

		return indicadorCavalete;
	}

	public void setIndicadorCavalete(String indicadorCavalete){

		this.indicadorCavalete = indicadorCavalete;
	}

	public String getAnoFabricacaoHidrometro(){

		return anoFabricacaoHidrometro;
	}

	public void setAnoFabricacaoHidrometro(String anoFabricacaoHidrometro){

		this.anoFabricacaoHidrometro = anoFabricacaoHidrometro;
	}

	public String getDescricaoLocalInstalacao(){

		return descricaoLocalInstalacao;
	}

	public void setDescricaoLocalInstalacao(String descricaoLocalInstalacao){

		this.descricaoLocalInstalacao = descricaoLocalInstalacao;
	}

}
