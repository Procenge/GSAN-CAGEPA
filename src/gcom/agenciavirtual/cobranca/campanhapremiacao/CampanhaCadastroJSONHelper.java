/**
 * 
 */
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

package gcom.agenciavirtual.cobranca.campanhapremiacao;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author
 */
public class CampanhaCadastroJSONHelper
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idCampanhaCadasto;

	private String idImovel;

	private String inscricaoCampanha;

	private String nomeCliente;

	private String tipoRelacao;

	private String nuCPF;

	private String nuCNPJ;

	private String nomeMae;

	private String nuRG;

	private String dtEmissaoRG;

	private String idOrgaoExpedidorRG;

	private String estado;

	private String dtNascimento;

	private String dsEmail;

	private String indicadorExibirBotaoEmitirComprovante;

	public String getIdCampanhaCadasto(){

		return idCampanhaCadasto;
	}

	public void setIdCampanhaCadasto(String idCampanhaCadasto){

		this.idCampanhaCadasto = idCampanhaCadasto;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoCampanha(){

		return inscricaoCampanha;
	}

	public void setInscricaoCampanha(String inscricaoCampanha){

		this.inscricaoCampanha = inscricaoCampanha;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getTipoRelacao(){

		return tipoRelacao;
	}

	public void setTipoRelacao(String tipoRelacao){

		this.tipoRelacao = tipoRelacao;
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

	public String getNomeMae(){

		return nomeMae;
	}

	public void setNomeMae(String nomeMae){

		this.nomeMae = nomeMae;
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

	public String getIdOrgaoExpedidorRG(){

		return idOrgaoExpedidorRG;
	}

	public void setIdOrgaoExpedidorRG(String idOrgaoExpedidorRG){

		this.idOrgaoExpedidorRG = idOrgaoExpedidorRG;
	}

	public String getEstado(){

		return estado;
	}


	public void setEstado(String estado){

		this.estado = estado;
	}

	public String getDtNascimento(){

		return dtNascimento;
	}

	public void setDtNascimento(String dtNascimento){

		this.dtNascimento = dtNascimento;
	}

	public String getDsEmail(){

		return dsEmail;
	}

	public void setDsEmail(String dsEmail){

		this.dsEmail = dsEmail;
	}

	public String getIndicadorExibirBotaoEmitirComprovante(){

		return indicadorExibirBotaoEmitirComprovante;
	}

	public void setIndicadorExibirBotaoEmitirComprovante(String indicadorExibirBotaoEmitirComprovante){

		this.indicadorExibirBotaoEmitirComprovante = indicadorExibirBotaoEmitirComprovante;
	}


}
