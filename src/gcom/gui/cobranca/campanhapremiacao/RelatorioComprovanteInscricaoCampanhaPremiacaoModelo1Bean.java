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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.relatorio.RelatorioBean;

/**
 * [UC0XXX] Gerar Relatório Grandes Consumidores
 * 
 * @author Anderson Italo
 * @date 15/02/2011
 */
public class RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1Bean
				implements RelatorioBean {

	private String dsTituloCampanha;

	private String numeroInscricao;
	private String nomeCliente;

	private String idImovel;

	private String enderecoImovel;

	private String dtHoraInsricao;

	private String localInscricao;

	private String cdTipoRelacao;

	private String numeroCPF;

	private String numeroRG;

	private String dtEmissao;

	private String dsOrgaoExpedidor;

	private String dsUF;

	private String dtNascimento;

	private String nomeMae;

	private String dsEmail;

	private String numeroCNPJ;

	public RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1Bean() {

	}

	public RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1Bean(String dsTituloCampanha, String numeroInscricao, String nomeCliente,
																		String idImovel, String enderecoImovel, String dtHoraInsricao,
																		String localInscricao, String cdTipoRelacao, String numeroCPF,
																		String numeroRG, String dtEmissao, String dsOrgaoExpedidor,
																		String dsUF, String dtNascimento, String nomeMae, String dsEmail,
																		String numeroCNPJ) {

		this.dsTituloCampanha = dsTituloCampanha;
		this.numeroInscricao = numeroInscricao;
		this.nomeCliente = nomeCliente;
		this.idImovel = idImovel;
		this.enderecoImovel = enderecoImovel;
		this.dtHoraInsricao = dtHoraInsricao;
		this.localInscricao = localInscricao;
		this.cdTipoRelacao = cdTipoRelacao;
		this.numeroCPF = numeroCPF;
		this.numeroRG = numeroRG;
		this.dtEmissao = dtEmissao;
		this.dsOrgaoExpedidor = dsOrgaoExpedidor;
		this.dsUF = dsUF;
		this.dtNascimento = dtNascimento;
		this.nomeMae = nomeMae;
		this.dsEmail = dsEmail;
		this.numeroCNPJ = numeroCNPJ;
	}

	public String getDsTituloCampanha(){

		return dsTituloCampanha;
	}

	public void setDsTituloCampanha(String dsTituloCampanha){

		this.dsTituloCampanha = dsTituloCampanha;
	}

	public String getNumeroInscricao(){

		return numeroInscricao;
	}

	public void setNumeroInscricao(String numeroInscricao){

		this.numeroInscricao = numeroInscricao;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getDtHoraInsricao(){

		return dtHoraInsricao;
	}

	public void setDtHoraInsricao(String dtHoraInsricao){

		this.dtHoraInsricao = dtHoraInsricao;
	}

	public String getLocalInscricao(){

		return localInscricao;
	}

	public void setLocalInscricao(String localInscricao){

		this.localInscricao = localInscricao;
	}

	public String getCdTipoRelacao(){

		return cdTipoRelacao;
	}

	public void setCdTipoRelacao(String cdTipoRelacao){

		this.cdTipoRelacao = cdTipoRelacao;
	}

	public String getNumeroCPF(){

		return numeroCPF;
	}

	public void setNumeroCPF(String numeroCPF){

		this.numeroCPF = numeroCPF;
	}

	public String getNumeroRG(){

		return numeroRG;
	}

	public void setNumeroRG(String numeroRG){

		this.numeroRG = numeroRG;
	}

	public String getDtEmissao(){

		return dtEmissao;
	}

	public void setDtEmissao(String dtEmissao){

		this.dtEmissao = dtEmissao;
	}

	public String getDsOrgaoExpedidor(){

		return dsOrgaoExpedidor;
	}

	public void setDsOrgaoExpedidor(String dsOrgaoExpedidor){

		this.dsOrgaoExpedidor = dsOrgaoExpedidor;
	}

	public String getDsUF(){

		return dsUF;
	}

	public void setDsUF(String dsUF){

		this.dsUF = dsUF;
	}

	public String getDtNascimento(){

		return dtNascimento;
	}

	public void setDtNascimento(String dtNascimento){

		this.dtNascimento = dtNascimento;
	}

	public String getNomeMae(){

		return nomeMae;
	}

	public void setNomeMae(String nomeMae){

		this.nomeMae = nomeMae;
	}

	public String getDsEmail(){

		return dsEmail;
	}

	public void setDsEmail(String dsEmail){

		this.dsEmail = dsEmail;
	}

	public String getNumeroCNPJ(){

		return numeroCNPJ;
	}

	public void setNumeroCNPJ(String numeroCNPJ){

		this.numeroCNPJ = numeroCNPJ;
	}

}
