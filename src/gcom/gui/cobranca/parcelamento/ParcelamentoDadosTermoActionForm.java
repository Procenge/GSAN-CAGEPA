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

package gcom.gui.cobranca.parcelamento;

import org.apache.struts.validator.ValidatorForm;

public class ParcelamentoDadosTermoActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String idParcelamentoAcordoTipo;

	private String idParcelamento;

	private String indicadorProcurador;

	private String idCliente;

	private String nomeCliente;

	private String numeroRgCliente;

	private String numeroRgClienteInicial;

	private String idOrgaoExpedidorRgCliente;

	private String idOrgaoExpedidorRgClienteInicial;

	private String idUnidadeFederacaoCliente;

	private String idUnidadeFederacaoClienteInicial;

	private String numeroCpfCliente;

	private String numeroCpfClienteInicial;

	private String idEnderecoCliente;

	private String idEnderecoTipoCliente;

	private String indicadorEnderecoCorrespondenciaCliente;

	private String descricaoEnderecoCliente;

	private String idEstadoCivilCliente;

	private String idEstadoCivilClienteInicial;

	private String idProfissaoCliente;

	private String idProfissaoClienteInicial;

	private String idNacionalidadeCliente;

	private String idNacionalidadeClienteInicial;

	private String numeroProcesso;

	private String numeroVara;

	private String nomeExecutado;

	private String tituloPosse;

	private String idClienteEmpresa;

	private String idClienteEmpresaExistente;

	private String nomeClienteEmpresa;

	private String idRamoAtividadeClienteEmpresa;

	private String idRamoAtividadeClienteEmpresaInicial;

	private String numeroCnpjClienteEmpresa;

	private String numeroCnpjClienteEmpresaInicial;

	private String idEnderecoClienteEmpresa;

	private String idEnderecoTipoClienteEmpresa;

	private String indicadorEnderecoCorrespondenciaClienteEmpresa;

	private String descricaoEnderecoClienteEmpresa;

	private String nomeProcurador;

	private String numeroRgProcurador;

	private String idOrgaoExpedidorRgProcurador;

	private String idUnidadeFederacaoProcurador;

	private String numeroCpfProcurador;

	private String descricaoEnderecoProcurador;

	private String idEstadoCivilProcurador;

	private String idProfissaoProcurador;

	private String idNacionalidadeProcurador;

	private String visivelNacionalidadeCliente;

	private String visivelNumeroProcesso;

	private String visivelNumeroVara;

	private String visivelNomeExecutado;

	private String visivelTituloPosse;

	private String visivelDadosEmpresa;

	private String idImovel;

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getVisivelDadosEmpresa(){

		return visivelDadosEmpresa;
	}

	public void setVisivelDadosEmpresa(String visivelDadosEmpresa){

		this.visivelDadosEmpresa = visivelDadosEmpresa;
	}

	public String getVisivelTituloPosse(){

		return visivelTituloPosse;
	}

	public void setVisivelTituloPosse(String visivelTituloPosse){

		this.visivelTituloPosse = visivelTituloPosse;
	}

	public String getVisivelNacionalidadeCliente(){

		return visivelNacionalidadeCliente;
	}

	public void setVisivelNacionalidadeCliente(String visivelNacionalidadeCliente){

		this.visivelNacionalidadeCliente = visivelNacionalidadeCliente;
	}

	public String getVisivelNumeroProcesso(){

		return visivelNumeroProcesso;
	}

	public void setVisivelNumeroProcesso(String visivelNumeroProcesso){

		this.visivelNumeroProcesso = visivelNumeroProcesso;
	}

	public String getVisivelNumeroVara(){

		return visivelNumeroVara;
	}

	public void setVisivelNumeroVara(String visivelNumeroVara){

		this.visivelNumeroVara = visivelNumeroVara;
	}

	public String getVisivelNomeExecutado(){

		return visivelNomeExecutado;
	}

	public void setVisivelNomeExecutado(String visivelNomeExecutado){

		this.visivelNomeExecutado = visivelNomeExecutado;
	}

	public String getId(){
		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getIdParcelamentoAcordoTipo(){

		return idParcelamentoAcordoTipo;
	}

	public void setIdParcelamentoAcordoTipo(String idParcelamentoAcordoTipo){

		this.idParcelamentoAcordoTipo = idParcelamentoAcordoTipo;
	}

	public String getIdParcelamento(){

		return idParcelamento;
	}

	public void setIdParcelamento(String idParcelamento){

		this.idParcelamento = idParcelamento;
	}

	public String getIndicadorProcurador(){

		return indicadorProcurador;
	}

	public void setIndicadorProcurador(String indicadorProcurador){

		this.indicadorProcurador = indicadorProcurador;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNumeroRgCliente(){

		return numeroRgCliente;
	}

	public void setNumeroRgCliente(String numeroRgCliente){

		this.numeroRgCliente = numeroRgCliente;
	}

	public String getIdOrgaoExpedidorRgCliente(){

		return idOrgaoExpedidorRgCliente;
	}

	public void setIdOrgaoExpedidorRgCliente(String idOrgaoExpedidorRgCliente){

		this.idOrgaoExpedidorRgCliente = idOrgaoExpedidorRgCliente;
	}

	public String getIdUnidadeFederacaoCliente(){

		return idUnidadeFederacaoCliente;
	}

	public void setIdUnidadeFederacaoCliente(String idUnidadeFederacaoCliente){

		this.idUnidadeFederacaoCliente = idUnidadeFederacaoCliente;
	}

	public String getNumeroCpfCliente(){

		return numeroCpfCliente;
	}

	public void setNumeroCpfCliente(String numeroCpfCliente){

		this.numeroCpfCliente = numeroCpfCliente;
	}

	public String getNumeroRgClienteInicial(){

		return numeroRgClienteInicial;
	}

	public void setNumeroRgClienteInicial(String numeroRgClienteInicial){

		this.numeroRgClienteInicial = numeroRgClienteInicial;
	}

	public String getIdOrgaoExpedidorRgClienteInicial(){

		return idOrgaoExpedidorRgClienteInicial;
	}

	public void setIdOrgaoExpedidorRgClienteInicial(String idOrgaoExpedidorRgClienteInicial){

		this.idOrgaoExpedidorRgClienteInicial = idOrgaoExpedidorRgClienteInicial;
	}

	public String getIdUnidadeFederacaoClienteInicial(){

		return idUnidadeFederacaoClienteInicial;
	}

	public void setIdUnidadeFederacaoClienteInicial(String idUnidadeFederacaoClienteInicial){

		this.idUnidadeFederacaoClienteInicial = idUnidadeFederacaoClienteInicial;
	}

	public String getNumeroCpfClienteInicial(){

		return numeroCpfClienteInicial;
	}

	public void setNumeroCpfClienteInicial(String numeroCpfClienteInicial){

		this.numeroCpfClienteInicial = numeroCpfClienteInicial;
	}

	public String getIdEnderecoTipoCliente(){

		return idEnderecoTipoCliente;
	}

	public void setIdEnderecoTipoCliente(String idEnderecoTipoCliente){

		this.idEnderecoTipoCliente = idEnderecoTipoCliente;
	}

	public String getIdEnderecoCliente(){

		return idEnderecoCliente;
	}

	public void setIdEnderecoCliente(String idEnderecoCliente){

		this.idEnderecoCliente = idEnderecoCliente;
	}

	public String getIndicadorEnderecoCorrespondenciaCliente(){

		return indicadorEnderecoCorrespondenciaCliente;
	}

	public void setIndicadorEnderecoCorrespondenciaCliente(String indicadorEnderecoCorrespondenciaCliente){

		this.indicadorEnderecoCorrespondenciaCliente = indicadorEnderecoCorrespondenciaCliente;
	}

	public String getDescricaoEnderecoCliente(){

		return descricaoEnderecoCliente;
	}

	public void setDescricaoEnderecoCliente(String descricaoEnderecoCliente){

		this.descricaoEnderecoCliente = descricaoEnderecoCliente;
	}

	public String getIdEstadoCivilCliente(){

		return idEstadoCivilCliente;
	}

	public void setIdEstadoCivilCliente(String idEstadoCivilCliente){

		this.idEstadoCivilCliente = idEstadoCivilCliente;
	}

	public String getIdEstadoCivilClienteInicial(){

		return idEstadoCivilClienteInicial;
	}

	public void setIdEstadoCivilClienteInicial(String idEstadoCivilClienteInicial){

		this.idEstadoCivilClienteInicial = idEstadoCivilClienteInicial;
	}

	public String getIdProfissaoCliente(){

		return idProfissaoCliente;
	}

	public void setIdProfissaoCliente(String idProfissaoCliente){

		this.idProfissaoCliente = idProfissaoCliente;
	}

	public String getIdProfissaoClienteInicial(){

		return idProfissaoClienteInicial;
	}

	public void setIdProfissaoClienteInicial(String idProfissaoClienteInicial){

		this.idProfissaoClienteInicial = idProfissaoClienteInicial;
	}

	public String getIdNacionalidadeCliente(){

		return idNacionalidadeCliente;
	}

	public void setIdNacionalidadeCliente(String idNacionalidadeCliente){

		this.idNacionalidadeCliente = idNacionalidadeCliente;
	}

	public String getIdNacionalidadeClienteInicial(){

		return idNacionalidadeClienteInicial;
	}

	public void setIdNacionalidadeClienteInicial(String idNacionalidadeClienteInicial){

		this.idNacionalidadeClienteInicial = idNacionalidadeClienteInicial;
	}

	public String getNumeroProcesso(){

		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso){

		this.numeroProcesso = numeroProcesso;
	}

	public String getNumeroVara(){

		return numeroVara;
	}

	public void setNumeroVara(String numeroVara){

		this.numeroVara = numeroVara;
	}

	public String getNomeExecutado(){

		return nomeExecutado;
	}

	public void setNomeExecutado(String nomeExecutado){

		this.nomeExecutado = nomeExecutado;
	}

	public String getTituloPosse(){

		return tituloPosse;
	}

	public void setTituloPosse(String tituloPosse){

		this.tituloPosse = tituloPosse;
	}

	public String getIdClienteEmpresa(){

		return idClienteEmpresa;
	}

	public void setIdClienteEmpresa(String idClienteEmpresa){

		this.idClienteEmpresa = idClienteEmpresa;
	}

	public String getIdClienteEmpresaExistente(){

		return idClienteEmpresaExistente;
	}

	public void setIdClienteEmpresaExistente(String idClienteEmpresaExistente){

		this.idClienteEmpresaExistente = idClienteEmpresaExistente;
	}

	public String getNomeClienteEmpresa(){

		return nomeClienteEmpresa;
	}

	public void setNomeClienteEmpresa(String nomeClienteEmpresa){

		this.nomeClienteEmpresa = nomeClienteEmpresa;
	}

	public String getIdRamoAtividadeClienteEmpresa(){

		return idRamoAtividadeClienteEmpresa;
	}

	public void setIdRamoAtividadeClienteEmpresa(String idRamoAtividadeClienteEmpresa){

		this.idRamoAtividadeClienteEmpresa = idRamoAtividadeClienteEmpresa;
	}

	public String getIdRamoAtividadeClienteEmpresaInicial(){

		return idRamoAtividadeClienteEmpresaInicial;
	}

	public void setIdRamoAtividadeClienteEmpresaInicial(String idRamoAtividadeClienteEmpresaInicial){

		this.idRamoAtividadeClienteEmpresaInicial = idRamoAtividadeClienteEmpresaInicial;
	}

	public String getNumeroCnpjClienteEmpresa(){

		return numeroCnpjClienteEmpresa;
	}

	public void setNumeroCnpjClienteEmpresa(String numeroCnpjClienteEmpresa){

		this.numeroCnpjClienteEmpresa = numeroCnpjClienteEmpresa;
	}

	public String getNumeroCnpjClienteEmpresaInicial(){

		return numeroCnpjClienteEmpresaInicial;
	}

	public void setNumeroCnpjClienteEmpresaInicial(String numeroCnpjClienteEmpresaInicial){

		this.numeroCnpjClienteEmpresaInicial = numeroCnpjClienteEmpresaInicial;
	}

	public String getIdEnderecoTipoClienteEmpresa(){

		return idEnderecoTipoClienteEmpresa;
	}

	public void setIdEnderecoTipoClienteEmpresa(String idEnderecoTipoClienteEmpresa){

		this.idEnderecoTipoClienteEmpresa = idEnderecoTipoClienteEmpresa;
	}

	public String getIdEnderecoClienteEmpresa(){

		return idEnderecoClienteEmpresa;
	}

	public void setIdEnderecoClienteEmpresa(String idEnderecoClienteEmpresa){

		this.idEnderecoClienteEmpresa = idEnderecoClienteEmpresa;
	}

	public String getIndicadorEnderecoCorrespondenciaClienteEmpresa(){

		return indicadorEnderecoCorrespondenciaClienteEmpresa;
	}

	public void setIndicadorEnderecoCorrespondenciaClienteEmpresa(String indicadorEnderecoCorrespondenciaClienteEmpresa){

		this.indicadorEnderecoCorrespondenciaClienteEmpresa = indicadorEnderecoCorrespondenciaClienteEmpresa;
	}

	public String getDescricaoEnderecoClienteEmpresa(){

		return descricaoEnderecoClienteEmpresa;
	}

	public void setDescricaoEnderecoClienteEmpresa(String descricaoEnderecoClienteEmpresa){

		this.descricaoEnderecoClienteEmpresa = descricaoEnderecoClienteEmpresa;
	}

	public String getNomeProcurador(){

		return nomeProcurador;
	}

	public void setNomeProcurador(String nomeProcurador){

		this.nomeProcurador = nomeProcurador;
	}

	public String getNumeroRgProcurador(){

		return numeroRgProcurador;
	}

	public void setNumeroRgProcurador(String numeroRgProcurador){

		this.numeroRgProcurador = numeroRgProcurador;
	}

	public String getIdOrgaoExpedidorRgProcurador(){

		return idOrgaoExpedidorRgProcurador;
	}

	public void setIdOrgaoExpedidorRgProcurador(String idOrgaoExpedidorRgProcurador){

		this.idOrgaoExpedidorRgProcurador = idOrgaoExpedidorRgProcurador;
	}

	public String getIdUnidadeFederacaoProcurador(){

		return idUnidadeFederacaoProcurador;
	}

	public void setIdUnidadeFederacaoProcurador(String idUnidadeFederacaoProcurador){

		this.idUnidadeFederacaoProcurador = idUnidadeFederacaoProcurador;
	}

	public String getNumeroCpfProcurador(){

		return numeroCpfProcurador;
	}

	public void setNumeroCpfProcurador(String numeroCpfProcurador){

		this.numeroCpfProcurador = numeroCpfProcurador;
	}

	public String getDescricaoEnderecoProcurador(){

		return descricaoEnderecoProcurador;
	}

	public void setDescricaoEnderecoProcurador(String descricaoEnderecoProcurador){

		this.descricaoEnderecoProcurador = descricaoEnderecoProcurador;
	}

	public String getIdEstadoCivilProcurador(){

		return idEstadoCivilProcurador;
	}

	public void setIdEstadoCivilProcurador(String idEstadoCivilProcurador){

		this.idEstadoCivilProcurador = idEstadoCivilProcurador;
	}

	public String getIdProfissaoProcurador(){

		return idProfissaoProcurador;
	}

	public void setIdProfissaoProcurador(String idProfissaoProcurador){

		this.idProfissaoProcurador = idProfissaoProcurador;
	}

	public String getIdNacionalidadeProcurador(){

		return idNacionalidadeProcurador;
	}

	public void setIdNacionalidadeProcurador(String idNacionalidadeProcurador){

		this.idNacionalidadeProcurador = idNacionalidadeProcurador;
	}

}
