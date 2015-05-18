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

package gcom.cobranca.parcelamento;

import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.geografico.UnidadeFederacao;

import java.util.Date;

public class ParcelamentoDadosTermo {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private ParcelamentoAcordoTipo parcelamentoAcordoTipo;

	private Parcelamento parcelamento;

	private Integer indicadorProcurador;

	private Cliente cliente;

	private String nomeCliente;

	private String numeroRgCliente;

	private OrgaoExpedidorRg orgaoExpedidorRgCliente;

	private UnidadeFederacao unidadeFederacaoCliente;

	private String numeroCpfCliente;

	private EnderecoTipo enderecoTipoCliente;

	private Integer indicadorEnderecoCorrespondenciaCliente;

	private String descricaoEnderecoCliente;

	private EstadoCivil estadoCivilCliente;

	private Profissao profissaoCliente;

	private Nacionalidade nacionalidadeCliente;

	private String numeroProcesso;

	private Integer numeroVara;

	private String nomeExecutado;

	private String tituloPosse;

	private Cliente clienteEmpresa;

	private String nomeClienteEmpresa;

	private RamoAtividade ramoAtividadeClienteEmpresa;

	private String numeroCnpjClienteEmpresa;

	private EnderecoTipo enderecoTipoClienteEmpresa;

	private Integer indicadorEnderecoCorrespondenciaClienteEmpresa;

	private String descricaoEnderecoClienteEmpresa;

	private String nomeProcurador;

	private String numeroRgProcurador;

	private OrgaoExpedidorRg orgaoExpedidorRgProcurador;

	private UnidadeFederacao unidadeFederacaoProcurador;

	private String numeroCpfProcurador;

	private String descricaoEnderecoProcurador;

	private EstadoCivil estadoCivilProcurador;

	private Profissao profissaoProcurador;

	private Nacionalidade nacionalidadeProcurador;

	private Date ultimaAlteracao;

	public ParcelamentoDadosTermo() {

	}

	public Integer getId(){
		return id;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public ParcelamentoAcordoTipo getParcelamentoAcordoTipo(){

		return parcelamentoAcordoTipo;
	}

	public void setParcelamentoAcordoTipo(ParcelamentoAcordoTipo parcelamentoAcordoTipo){

		this.parcelamentoAcordoTipo = parcelamentoAcordoTipo;
	}

	public Parcelamento getParcelamento(){

		return parcelamento;
	}

	public void setParcelamento(Parcelamento parcelamento){

		this.parcelamento = parcelamento;
	}

	public Integer getIndicadorProcurador(){

		return indicadorProcurador;
	}

	public void setIndicadorProcurador(Integer indicadorProcurador){

		this.indicadorProcurador = indicadorProcurador;
	}

	public Cliente getCliente(){

		return cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
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

	public OrgaoExpedidorRg getOrgaoExpedidorRgCliente(){

		return orgaoExpedidorRgCliente;
	}

	public void setOrgaoExpedidorRgCliente(OrgaoExpedidorRg orgaoExpedidorRgCliente){

		this.orgaoExpedidorRgCliente = orgaoExpedidorRgCliente;
	}

	public UnidadeFederacao getUnidadeFederacaoCliente(){

		return unidadeFederacaoCliente;
	}

	public void setUnidadeFederacaoCliente(UnidadeFederacao unidadeFederacaoCliente){

		this.unidadeFederacaoCliente = unidadeFederacaoCliente;
	}

	public String getNumeroCpfCliente(){

		return numeroCpfCliente;
	}

	public void setNumeroCpfCliente(String numeroCpfCliente){

		this.numeroCpfCliente = numeroCpfCliente;
	}

	public EnderecoTipo getEnderecoTipoCliente(){

		return enderecoTipoCliente;
	}

	public void setEnderecoTipoCliente(EnderecoTipo enderecoTipoCliente){

		this.enderecoTipoCliente = enderecoTipoCliente;
	}

	public Integer getIndicadorEnderecoCorrespondenciaCliente(){

		return indicadorEnderecoCorrespondenciaCliente;
	}

	public void setIndicadorEnderecoCorrespondenciaCliente(Integer indicadorEnderecoCorrespondenciaCliente){

		this.indicadorEnderecoCorrespondenciaCliente = indicadorEnderecoCorrespondenciaCliente;
	}

	public String getDescricaoEnderecoCliente(){

		return descricaoEnderecoCliente;
	}

	public void setDescricaoEnderecoCliente(String descricaoEnderecoCliente){

		this.descricaoEnderecoCliente = descricaoEnderecoCliente;
	}

	public EstadoCivil getEstadoCivilCliente(){

		return estadoCivilCliente;
	}

	public void setEstadoCivilCliente(EstadoCivil estadoCivilCliente){

		this.estadoCivilCliente = estadoCivilCliente;
	}

	public Profissao getProfissaoCliente(){

		return profissaoCliente;
	}

	public void setProfissaoCliente(Profissao profissaoCliente){

		this.profissaoCliente = profissaoCliente;
	}

	public Nacionalidade getNacionalidadeCliente(){

		return nacionalidadeCliente;
	}

	public void setNacionalidadeCliente(Nacionalidade nacionalidadeCliente){

		this.nacionalidadeCliente = nacionalidadeCliente;
	}

	public String getNumeroProcesso(){

		return numeroProcesso;
	}

	public void setNumeroProcesso(String numeroProcesso){

		this.numeroProcesso = numeroProcesso;
	}

	public Integer getNumeroVara(){

		return numeroVara;
	}

	public void setNumeroVara(Integer numeroVara){

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

	public Cliente getClienteEmpresa(){

		return clienteEmpresa;
	}

	public void setClienteEmpresa(Cliente clienteEmpresa){

		this.clienteEmpresa = clienteEmpresa;
	}

	public String getNomeClienteEmpresa(){

		return nomeClienteEmpresa;
	}

	public void setNomeClienteEmpresa(String nomeClienteEmpresa){

		this.nomeClienteEmpresa = nomeClienteEmpresa;
	}

	public RamoAtividade getRamoAtividadeClienteEmpresa(){

		return ramoAtividadeClienteEmpresa;
	}

	public void setRamoAtividadeClienteEmpresa(RamoAtividade ramoAtividadeClienteEmpresa){

		this.ramoAtividadeClienteEmpresa = ramoAtividadeClienteEmpresa;
	}
	
	public String getNumeroCnpjClienteEmpresa(){

		return numeroCnpjClienteEmpresa;
	}

	public void setNumeroCnpjClienteEmpresa(String numeroCnpjClienteEmpresa){

		this.numeroCnpjClienteEmpresa = numeroCnpjClienteEmpresa;
	}

	public EnderecoTipo getEnderecoTipoClienteEmpresa(){

		return enderecoTipoClienteEmpresa;
	}

	public void setEnderecoTipoClienteEmpresa(EnderecoTipo enderecoTipoClienteEmpresa){

		this.enderecoTipoClienteEmpresa = enderecoTipoClienteEmpresa;
	}

	public Integer getIndicadorEnderecoCorrespondenciaClienteEmpresa(){

		return indicadorEnderecoCorrespondenciaClienteEmpresa;
	}

	public void setIndicadorEnderecoCorrespondenciaClienteEmpresa(Integer indicadorEnderecoCorrespondenciaClienteEmpresa){

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

	public OrgaoExpedidorRg getOrgaoExpedidorRgProcurador(){

		return orgaoExpedidorRgProcurador;
	}

	public void setOrgaoExpedidorRgProcurador(OrgaoExpedidorRg orgaoExpedidorRgProcurador){

		this.orgaoExpedidorRgProcurador = orgaoExpedidorRgProcurador;
	}

	public UnidadeFederacao getUnidadeFederacaoProcurador(){

		return unidadeFederacaoProcurador;
	}

	public void setUnidadeFederacaoProcurador(UnidadeFederacao unidadeFederacaoProcurador){

		this.unidadeFederacaoProcurador = unidadeFederacaoProcurador;
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

	public EstadoCivil getEstadoCivilProcurador(){

		return estadoCivilProcurador;
	}

	public void setEstadoCivilProcurador(EstadoCivil estadoCivilProcurador){

		this.estadoCivilProcurador = estadoCivilProcurador;
	}

	public Profissao getProfissaoProcurador(){

		return profissaoProcurador;
	}

	public void setProfissaoProcurador(Profissao profissaoProcurador){

		this.profissaoProcurador = profissaoProcurador;
	}

	public Nacionalidade getNacionalidadeProcurador(){

		return nacionalidadeProcurador;
	}

	public void setNacionalidadeProcurador(Nacionalidade nacionalidadeProcurador){

		this.nacionalidadeProcurador = nacionalidadeProcurador;
	}

	public Date getUltimaAlteracao(){
		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){
		this.ultimaAlteracao = ultimaAlteracao;
	}

}
