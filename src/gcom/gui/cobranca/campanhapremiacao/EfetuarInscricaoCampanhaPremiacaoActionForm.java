/**
 * 
 */
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

package gcom.gui.cobranca.campanhapremiacao;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author gcom
 */
public class EfetuarInscricaoCampanhaPremiacaoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String idImovel;

	private String inscricaoCampanha;

	private String nomeCliente;

	private String tipoRelacao;

	private String nuCPF;

	private String nuCNPJ;

	private String nomeMae;

	private String nuRG;

	private String dtEmissaoRG;

	private String orgaoExpedidorRG;

	private String estado;

	private String dtNascimento;

	private String email;

	private String endereco;

	private String txRegulamentoCampanha;

	private String idTipoTelefone;

	private String textoSelecionado;

	private String idMunicipioFone;

	private String ddd;

	private String telefone;

	private String ramal;

	private String botaoClicado;

	private String botaoAdicionar;

	private String indicadorTelefonePadrao;

	private String concordaRegulamentoCampanha;

	private String indexTelefone;

	private String tituloCampanha;

	private String inscricoesRegistradas;

	private String inscricoesBloqueadas;

	public void reset(ActionMapping mapping, HttpServletRequest request){

		inscricaoCampanha = null;
		nomeCliente = null;
		tipoRelacao = null;
		nuCPF = null;
		nuCNPJ = null;
		nomeMae = null;
		nuRG = null;
		dtEmissaoRG = null;
		orgaoExpedidorRG = null;
		estado = null;
		dtNascimento = null;
		email = null;
		endereco = null;
		txRegulamentoCampanha = null;
		idTipoTelefone = null;
		textoSelecionado = null;
		idMunicipioFone = null;
		ddd = null;
		telefone = null;
		ramal = null;
		indicadorTelefonePadrao = null;
		concordaRegulamentoCampanha = null;
		indexTelefone = null;
		tituloCampanha = null;
		inscricoesRegistradas = null;
		inscricoesBloqueadas = null;

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

	public String getTxRegulamentoCampanha(){

		return txRegulamentoCampanha;
	}

	public void setTxRegulamentoCampanha(String txRegulamentoCampanha){

		this.txRegulamentoCampanha = txRegulamentoCampanha;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public String getIdTipoTelefone(){

		return idTipoTelefone;
	}

	public void setIdTipoTelefone(String idTipoTelefone){

		this.idTipoTelefone = idTipoTelefone;
	}

	public String getTextoSelecionado(){

		return textoSelecionado;
	}

	public void setTextoSelecionado(String textoSelecionado){

		this.textoSelecionado = textoSelecionado;
	}

	public String getIdMunicipioFone(){

		return idMunicipioFone;
	}

	public void setIdMunicipioFone(String idMunicipioFone){

		this.idMunicipioFone = idMunicipioFone;
	}

	public String getDdd(){

		return ddd;
	}

	public void setDdd(String ddd){

		this.ddd = ddd;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getRamal(){

		return ramal;
	}

	public void setRamal(String ramal){

		this.ramal = ramal;
	}

	public String getBotaoClicado(){

		return botaoClicado;
	}

	public void setBotaoClicado(String botaoClicado){

		this.botaoClicado = botaoClicado;
	}

	public String getBotaoAdicionar(){

		return botaoAdicionar;
	}

	public void setBotaoAdicionar(String botaoAdicionar){

		this.botaoAdicionar = botaoAdicionar;
	}

	public String getIndicadorTelefonePadrao(){

		return indicadorTelefonePadrao;
	}

	public void setIndicadorTelefonePadrao(String indicadorTelefonePadrao){

		this.indicadorTelefonePadrao = indicadorTelefonePadrao;
	}

	public String getNomeMae(){

		return nomeMae;
	}

	public void setNomeMae(String nomeMae){

		this.nomeMae = nomeMae;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getConcordaRegulamentoCampanha(){

		return concordaRegulamentoCampanha;
	}

	public void setConcordaRegulamentoCampanha(String concordaRegulamentoCampanha){

		this.concordaRegulamentoCampanha = concordaRegulamentoCampanha;
	}

	public String getIndexTelefone(){

		return indexTelefone;
	}

	public void setIndexTelefone(String indexTelefone){

		this.indexTelefone = indexTelefone;
	}

	public String getTituloCampanha(){

		return tituloCampanha;
	}

	public void setTituloCampanha(String tituloCampanha){

		this.tituloCampanha = tituloCampanha;
	}

	public String getInscricoesRegistradas(){

		return inscricoesRegistradas;
	}

	public void setInscricoesRegistradas(String inscricoesRegistradas){

		this.inscricoesRegistradas = inscricoesRegistradas;
	}

	public String getInscricoesBloqueadas(){

		return inscricoesBloqueadas;
	}

	public void setInscricoesBloqueadas(String inscricoesBloqueadas){

		this.inscricoesBloqueadas = inscricoesBloqueadas;
	}

}
