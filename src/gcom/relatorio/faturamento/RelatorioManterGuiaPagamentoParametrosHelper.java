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
 * GSANPCG
 * Eduardo Henrique
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

/**
 * Classe Helper utilizada na emiss�o de relat�rio manter guia pagamento
 * 
 * @author Hugo Lima
 * @date 26/12/2011
 */
public class RelatorioManterGuiaPagamentoParametrosHelper {

	private String codigoClienteGuia;

	private String nomeClienteGuia;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String idLocalidade;

	private String nomeLocalidade;

	private String codigoClienteResponsavel;

	private String nomeClienteResponsavel;

	private String periodoReferenciaFaturamento;

	private String periodoEmissao;

	private String periodoVencimento;

	private String idGuiaPagamento;

	private String numeroContratoParcelOrgaoPublico;

	private String numeroRA;

	private String descricaoRA;

	public RelatorioManterGuiaPagamentoParametrosHelper() {

		super();
		this.codigoClienteGuia = "";
		this.nomeClienteGuia = "";
		this.matriculaImovel = "";
		this.inscricaoImovel = "";
		this.idLocalidade = "";
		this.nomeLocalidade = "";
		this.codigoClienteResponsavel = "";
		this.nomeClienteResponsavel = "";
		this.periodoReferenciaFaturamento = "";
		this.periodoEmissao = "";
		this.periodoVencimento = "";
		this.idGuiaPagamento = "";
		this.numeroContratoParcelOrgaoPublico = "";
		this.numeroRA = "";
		this.descricaoRA = "";
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getDescricaoRA(){

		return descricaoRA;
	}

	public void setDescricaoRA(String descricaoRA){

		this.descricaoRA = descricaoRA;
	}

	public String getCodigoClienteGuia(){

		return codigoClienteGuia;
	}

	public void setCodigoClienteGuia(String codigoClienteGuia){

		this.codigoClienteGuia = codigoClienteGuia;
	}

	public String getNomeClienteGuia(){

		return nomeClienteGuia;
	}

	public void setNomeClienteGuia(String nomeClienteGuia){

		this.nomeClienteGuia = nomeClienteGuia;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCodigoClienteResponsavel(){

		return codigoClienteResponsavel;
	}

	public void setCodigoClienteResponsavel(String codigoClienteResponsavel){

		this.codigoClienteResponsavel = codigoClienteResponsavel;
	}

	public String getNomeClienteResponsavel(){

		return nomeClienteResponsavel;
	}

	public void setNomeClienteResponsavel(String nomeClienteResponsavel){

		this.nomeClienteResponsavel = nomeClienteResponsavel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getPeriodoReferenciaFaturamento(){

		return periodoReferenciaFaturamento;
	}

	public void setPeriodoReferenciaFaturamento(String periodoReferenciaFaturamento){

		this.periodoReferenciaFaturamento = periodoReferenciaFaturamento;
	}

	public String getPeriodoEmissao(){

		return periodoEmissao;
	}

	public void setPeriodoEmissao(String periodoEmissao){

		this.periodoEmissao = periodoEmissao;
	}

	public String getPeriodoVencimento(){

		return periodoVencimento;
	}

	public void setPeriodoVencimento(String periodoVencimento){

		this.periodoVencimento = periodoVencimento;
	}

	public String getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(String idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public String getNumeroContratoParcelOrgaoPublico(){

		return numeroContratoParcelOrgaoPublico;
	}

	public void setNumeroContratoParcelOrgaoPublico(String numeroContratoParcelOrgaoPublico){

		this.numeroContratoParcelOrgaoPublico = numeroContratoParcelOrgaoPublico;
	}

}
