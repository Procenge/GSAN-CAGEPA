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
 * @author Hiroshi Gonçalves
 * @date 17/10/2013
 */
public class RelatorioPremiacoesCampanhaPremiacaoBean
				implements RelatorioBean {

	private String dsTituloCampanha;

	private String dsUnidadePremiacao;

	private String dsUnidadePremiacaoLinha3;

	private String dsPremio;

	private String nuOrdemPremiacao;

	private String dtSorteio;

	private String nuInscricao;

	private String idImovel;

	private String nmCliente;

	private String cdTipoRelacaoClienteImovel;

	private String nuCPF;

	private String nuCNPJ;

	private String nuRG;

	private String dtEmissaoRG;

	private String orgaoExpedidorRG;

	private String estadoRG;

	private String dtNascimentoCliente;

	private String nmMaeCliente;

	private String dsEmailCliente;

	private String dtRetiradaPremio;

	private String nmUsuarioRespEntregaPremio;

	private String dtCancelamentoPremiacao;

	private String nmUsuarioRespCancelPremiacao;

	private String dsMotivoCancelPremiacao;

	private String dsEnderecoImovel;

	private String qtdPremios;

	public RelatorioPremiacoesCampanhaPremiacaoBean() {

	}



	public String getDsTituloCampanha(){

		return dsTituloCampanha;
	}

	public void setDsTituloCampanha(String dsTituloCampanha){

		this.dsTituloCampanha = dsTituloCampanha;
	}

	public String getDsUnidadePremiacao(){

		return dsUnidadePremiacao;
	}

	public void setDsUnidadePremiacao(String dsUnidadePremiacao){

		this.dsUnidadePremiacao = dsUnidadePremiacao;
	}

	public String getDsUnidadePremiacaoLinha3(){

		return dsUnidadePremiacaoLinha3;
	}

	public void setDsUnidadePremiacaoLinha3(String dsUnidadePremiacaoLinha3){

		this.dsUnidadePremiacaoLinha3 = dsUnidadePremiacaoLinha3;
	}

	public String getDsPremio(){

		return dsPremio;
	}

	public void setDsPremio(String dsPremio){

		this.dsPremio = dsPremio;
	}

	public String getNuOrdemPremiacao(){

		return nuOrdemPremiacao;
	}

	public void setNuOrdemPremiacao(String nuOrdemPremiacao){

		this.nuOrdemPremiacao = nuOrdemPremiacao;
	}

	public String getDtSorteio(){

		return dtSorteio;
	}

	public void setDtSorteio(String dtSorteio){

		this.dtSorteio = dtSorteio;
	}

	public String getNuInscricao(){

		return nuInscricao;
	}

	public void setNuInscricao(String nuInscricao){

		this.nuInscricao = nuInscricao;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getNmCliente(){

		return nmCliente;
	}

	public void setNmCliente(String nmCliente){

		this.nmCliente = nmCliente;
	}

	public String getCdTipoRelacaoClienteImovel(){

		return cdTipoRelacaoClienteImovel;
	}

	public void setCdTipoRelacaoClienteImovel(String cdTipoRelacaoClienteImovel){

		this.cdTipoRelacaoClienteImovel = cdTipoRelacaoClienteImovel;
	}

	public String getNuCPF(){

		return nuCPF;
	}

	public void setNuCPF(String nuCPF){

		this.nuCPF = nuCPF;
	}

	public String getNuCNPJ(){

		return nuCNPJ;
	}

	public void setNuCNPJ(String nuCNPJ){

		this.nuCNPJ = nuCNPJ;
	}

	public String getNuRG(){

		return nuRG;
	}

	public void setNuRG(String nuRG){

		this.nuRG = nuRG;
	}

	public String getDtEmissaoRG(){

		return dtEmissaoRG;
	}

	public void setDtEmissaoRG(String dtEmissaoRG){

		this.dtEmissaoRG = dtEmissaoRG;
	}

	public String getOrgaoExpedidorRG(){

		return orgaoExpedidorRG;
	}

	public void setOrgaoExpedidorRG(String orgaoExpedidorRG){

		this.orgaoExpedidorRG = orgaoExpedidorRG;
	}

	public String getEstadoRG(){

		return estadoRG;
	}

	public void setEstadoRG(String estadoRG){

		this.estadoRG = estadoRG;
	}

	public String getDtNascimentoCliente(){

		return dtNascimentoCliente;
	}

	public void setDtNascimentoCliente(String dtNascimentoCliente){

		this.dtNascimentoCliente = dtNascimentoCliente;
	}

	public String getNmMaeCliente(){

		return nmMaeCliente;
	}

	public void setNmMaeCliente(String nmMaeCliente){

		this.nmMaeCliente = nmMaeCliente;
	}

	public String getDsEmailCliente(){

		return dsEmailCliente;
	}

	public void setDsEmailCliente(String dsEmailCliente){

		this.dsEmailCliente = dsEmailCliente;
	}

	public String getDtRetiradaPremio(){

		return dtRetiradaPremio;
	}

	public void setDtRetiradaPremio(String dtRetiradaPremio){

		this.dtRetiradaPremio = dtRetiradaPremio;
	}

	public String getNmUsuarioRespEntregaPremio(){

		return nmUsuarioRespEntregaPremio;
	}

	public void setNmUsuarioRespEntregaPremio(String nmUsuarioRespEntregaPremio){

		this.nmUsuarioRespEntregaPremio = nmUsuarioRespEntregaPremio;
	}

	public String getDtCancelamentoPremiacao(){

		return dtCancelamentoPremiacao;
	}

	public void setDtCancelamentoPremiacao(String dtCancelamentoPremiacao){

		this.dtCancelamentoPremiacao = dtCancelamentoPremiacao;
	}

	public String getNmUsuarioRespCancelPremiacao(){

		return nmUsuarioRespCancelPremiacao;
	}

	public void setNmUsuarioRespCancelPremiacao(String nmUsuarioRespCancelPremiacao){

		this.nmUsuarioRespCancelPremiacao = nmUsuarioRespCancelPremiacao;
	}

	public String getDsMotivoCancelPremiacao(){

		return dsMotivoCancelPremiacao;
	}

	public void setDsMotivoCancelPremiacao(String dsMotivoCancelPremiacao){

		this.dsMotivoCancelPremiacao = dsMotivoCancelPremiacao;
	}

	public String getDsEnderecoImovel(){

		return dsEnderecoImovel;
	}

	public void setDsEnderecoImovel(String dsEnderecoImovel){

		this.dsEnderecoImovel = dsEnderecoImovel;
	}

	public String getQtdPremios(){

		return qtdPremios;
	}

	public void setQtdPremios(String qtdPremios){

		this.qtdPremios = qtdPremios;
	}

}
