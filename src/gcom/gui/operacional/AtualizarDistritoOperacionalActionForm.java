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

package gcom.gui.operacional;

import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;

import org.apache.struts.validator.ValidatorForm;

/**
 * [UC005] DISTRITO OPERACIONAL
 * 
 * @author Eduardo Bianchi
 * @date 02/02/2007
 */

public class AtualizarDistritoOperacionalActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String codigoDistritoOperacional;

	private String descricao;

	private String descricaoAbreviada;

	private String indicadorUso;

	private String setorAbastecimento;

	private String ultimaAlteracao;

	private String localidade;

	private String descricaoLocalidade;

	private String endereco;

	private String telefone;

	private String ramal;

	private String fax;

	private String email;

	private String descricaoTipoInstalacao;

	private String numeroCota;

	private String latitude;

	private String longitude;

	private String sistemaAbastecimento;

	private String tipoUnidadeOperacional;

	private LogradouroCep cep;

	private Logradouro logradouro;

	private LogradouroBairro bairro;

	private EnderecoReferencia enderecoReferencia;

	private String numeroImovel;

	private String complementoEndereco;

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getCodigoDistritoOperacional(){

		return codigoDistritoOperacional;
	}

	public void setCodigoDistritoOperacional(String codigoDistritoOperacional){

		this.codigoDistritoOperacional = codigoDistritoOperacional;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getSetorAbastecimento(){

		return setorAbastecimento;
	}

	public void setSetorAbastecimento(String setorAbastecimento){

		this.setorAbastecimento = setorAbastecimento;
	}

	public String getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(String sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
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

	public String getFax(){

		return fax;
	}

	public void setFax(String fax){

		this.fax = fax;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public String getDescricaoTipoInstalacao(){

		return descricaoTipoInstalacao;
	}

	public void setDescricaoTipoInstalacao(String descricaoTipoInstalacao){

		this.descricaoTipoInstalacao = descricaoTipoInstalacao;
	}

	public String getNumeroCota(){

		return numeroCota;
	}

	public void setNumeroCota(String numeroCota){

		this.numeroCota = numeroCota;
	}

	public String getLatitude(){

		return latitude;
	}

	public void setLatitude(String latitude){

		this.latitude = latitude;
	}

	public String getLongitude(){

		return longitude;
	}

	public void setLongitude(String longitude){

		this.longitude = longitude;
	}

	public String getTipoUnidadeOperacional(){

		return tipoUnidadeOperacional;
	}

	public void setTipoUnidadeOperacional(String tipoUnidadeOperacional){

		this.tipoUnidadeOperacional = tipoUnidadeOperacional;
	}

	public LogradouroCep getCep(){

		return cep;
	}

	public void setCep(LogradouroCep cep){

		this.cep = cep;
	}

	public Logradouro getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro){

		this.logradouro = logradouro;
	}

	public LogradouroBairro getBairro(){

		return bairro;
	}

	public void setBairro(LogradouroBairro bairro){

		this.bairro = bairro;
	}

	public EnderecoReferencia getEnderecoReferencia(){

		return enderecoReferencia;
	}

	public void setEnderecoReferencia(EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getComplementoEndereco(){

		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco){

		this.complementoEndereco = complementoEndereco;
	}

}