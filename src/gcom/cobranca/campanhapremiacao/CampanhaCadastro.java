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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.cobranca.campanhapremiacao;

import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

@ControleAlteracao
public class CampanhaCadastro
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public final static Integer TIPO_RELACAO_USUARIO = 1;

	public final static Integer TIPO_RELACAO_RESPONSAVEL = 2;

	public final static Integer TIPO_RELACAO_USUARIO_RESPONSAVEL = 3;

	private final static int CAMPANHA_CADASTRO_INSERIR = 323967;

	@ControleAlteracao(funcionalidade = {CAMPANHA_CADASTRO_INSERIR})
	private Integer id;

	private String numeroInscricao;

	private Date tmInscricao;

	private String nomeCliente;

	private Integer codigoTipoRelacaoClienteImovel;

	private String numeroCPF;

	private String numeroCNPJ;

	private String numeroRG;

	private Date dataRGEmissao;

	private Date dataNascimento;

	private String nomeMae;

	private String dsEmail;

	private Integer idInscricao;

	private Integer idEmissaoComprovante;

	private Date dataEmissaoComprovante;

	private Short indicadorComprovanteBloqueado;

	private Date ultimaAlteracao;

	private Campanha campanha;

	private Imovel imovel;

	private OrgaoExpedidorRg orgaoExpedidorRG;

	private UnidadeFederacao unidadeFederacao;

	private MeioSolicitacao meioSolicitacao;

	private UnidadeOrganizacional unidadeOrganizacional;

	public CampanhaCadastro() {

	}

	@Override
	public Filtro retornaFiltro(){

		FiltroCampanhaCadastro filtroCampanhaCadastro = new FiltroCampanhaCadastro();
		filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.ID, this.getId()));
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.CAMPANHA);
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.ORGAO_EXPEDIDOR);
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.UNIDADE_ORGANIZACIONAL);
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade(FiltroCampanhaCadastro.UNIDADE_FEDERACAO);

		return filtroCampanhaCadastro;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNumeroInscricao(){

		return numeroInscricao;
	}

	public void setNumeroInscricao(String numeroInscricao){

		this.numeroInscricao = numeroInscricao;
	}

	public Date getTmInscricao(){

		return tmInscricao;
	}

	public void setTmInscricao(Date tmInscricao){

		this.tmInscricao = tmInscricao;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public Integer getCodigoTipoRelacaoClienteImovel(){

		return codigoTipoRelacaoClienteImovel;
	}

	public void setCodigoTipoRelacaoClienteImovel(Integer codigoTipoRelacaoClienteImovel){

		this.codigoTipoRelacaoClienteImovel = codigoTipoRelacaoClienteImovel;
	}

	public String getNumeroCPF(){

		return numeroCPF;
	}

	public void setNumeroCPF(String numeroCPF){

		this.numeroCPF = numeroCPF;
	}

	public String getNumeroCNPJ(){

		return numeroCNPJ;
	}

	public void setNumeroCNPJ(String numeroCNPJ){

		this.numeroCNPJ = numeroCNPJ;
	}

	public String getNumeroRG(){

		return numeroRG;
	}

	public void setNumeroRG(String numeroRG){

		this.numeroRG = numeroRG;
	}

	public Date getDataRGEmissao(){

		return dataRGEmissao;
	}

	public void setDataRGEmissao(Date dataRGEmissao){

		this.dataRGEmissao = dataRGEmissao;
	}

	public Date getDataNascimento(){

		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento){

		this.dataNascimento = dataNascimento;
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

	public Integer getIdInscricao(){

		return idInscricao;
	}

	public void setIdInscricao(Integer idInscricao){

		this.idInscricao = idInscricao;
	}

	public Integer getIdEmissaoComprovante(){

		return idEmissaoComprovante;
	}

	public void setIdEmissaoComprovante(Integer idEmissaoComprovante){

		this.idEmissaoComprovante = idEmissaoComprovante;
	}

	public Date getDataEmissaoComprovante(){

		return dataEmissaoComprovante;
	}

	public void setDataEmissaoComprovante(Date dataEmissaoComprovante){

		this.dataEmissaoComprovante = dataEmissaoComprovante;
	}

	public Short getIndicadorComprovanteBloqueado(){

		return indicadorComprovanteBloqueado;
	}

	public void setIndicadorComprovanteBloqueado(Short indicadorComprovanteBloqueado){

		this.indicadorComprovanteBloqueado = indicadorComprovanteBloqueado;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Campanha getCampanha(){

		return campanha;
	}

	public void setCampanha(Campanha campanha){

		this.campanha = campanha;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public OrgaoExpedidorRg getOrgaoExpedidorRG(){

		return orgaoExpedidorRG;
	}

	public void setOrgaoExpedidorRG(OrgaoExpedidorRg orgaoExpedidorRG){

		this.orgaoExpedidorRG = orgaoExpedidorRG;
	}

	public UnidadeFederacao getUnidadeFederacao(){

		return unidadeFederacao;
	}

	public void setUnidadeFederacao(UnidadeFederacao unidadeFederacao){

		this.unidadeFederacao = unidadeFederacao;
	}

	public MeioSolicitacao getMeioSolicitacao(){

		return meioSolicitacao;
	}

	public void setMeioSolicitacao(MeioSolicitacao meioSolicitacao){

		this.meioSolicitacao = meioSolicitacao;
	}

	public UnidadeOrganizacional getUnidadeOrganizacional(){

		return unidadeOrganizacional;
	}

	public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional){

		this.unidadeOrganizacional = unidadeOrganizacional;
	}

	public String getNumeroCPFCNPJ(){

		String retorno = numeroCPF;

		if(Util.isVazioOuBranco(retorno)){
			retorno = numeroCNPJ;
		}

		return retorno;
	}

	public String getNomeClienteAbreviado(){

		String retorno = nomeCliente;

		if(retorno.length() > 15){
			retorno = retorno.substring(0, 15);
		}
		return retorno;
	}

}
