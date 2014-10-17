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

package gcom.gui.operacional.bacia;

import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.operacional.Bacia;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;

import org.apache.struts.action.ActionForm;

/**
 * @author isilva
 * @date 03/02/2011
 */
public class AtualizarBaciaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idBacia;

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String idSistemaEsgoto;

	private String descricaoSistemaEsgoto;

	private String idSubsistemaEsgoto;

	private String descricaoSubsistemaEsgoto;

	private String indicadorUso;

	private String ultimaAlteracao;

	private String numeroUnidade;

	private String numeroAducao;

	private String numeroDemandaEnergia;

	private String numeroCota;

	private String numeroLatitude;

	private String numeroLongitude;

	/**
	 * @return the idBacia
	 */
	public String getIdBacia(){

		return idBacia;
	}

	/**
	 * @param idBacia
	 *            the idBacia to set
	 */
	public void setIdBacia(String idBacia){

		this.idBacia = idBacia;
	}

	/**
	 * @return the codigo
	 */
	public String getCodigo(){

		return codigo;
	}

	/**
	 * @param codigo
	 *            the codigo to set
	 */
	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	/**
	 * @return the descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * @param descricao
	 *            the descricao to set
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * @return the descricaoAbreviada
	 */
	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	/**
	 * @param descricaoAbreviada
	 *            the descricaoAbreviada to set
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	/**
	 * @return the idSistemaEsgoto
	 */
	public String getIdSistemaEsgoto(){

		return idSistemaEsgoto;
	}

	/**
	 * @param idSistemaEsgoto
	 *            the idSistemaEsgoto to set
	 */
	public void setIdSistemaEsgoto(String idSistemaEsgoto){

		this.idSistemaEsgoto = idSistemaEsgoto;
	}

	/**
	 * @return the descricaoSistemaEsgoto
	 */
	public String getDescricaoSistemaEsgoto(){

		return descricaoSistemaEsgoto;
	}

	/**
	 * @param descricaoSistemaEsgoto
	 *            the descricaoSistemaEsgoto to set
	 */
	public void setDescricaoSistemaEsgoto(String descricaoSistemaEsgoto){

		this.descricaoSistemaEsgoto = descricaoSistemaEsgoto;
	}

	/**
	 * @return the idSubsistemaEsgoto
	 */
	public String getIdSubsistemaEsgoto(){

		return idSubsistemaEsgoto;
	}

	/**
	 * @param idSubsistemaEsgoto
	 *            the idSubsistemaEsgoto to set
	 */
	public void setIdSubsistemaEsgoto(String idSubsistemaEsgoto){

		this.idSubsistemaEsgoto = idSubsistemaEsgoto;
	}

	/**
	 * @return the descricaoSubsistemaEsgoto
	 */
	public String getDescricaoSubsistemaEsgoto(){

		return descricaoSubsistemaEsgoto;
	}

	/**
	 * @param descricaoSubsistemaEsgoto
	 *            the descricaoSubsistemaEsgoto to set
	 */
	public void setDescricaoSubsistemaEsgoto(String descricaoSubsistemaEsgoto){

		this.descricaoSubsistemaEsgoto = descricaoSubsistemaEsgoto;
	}

	/**
	 * @return the indicadorUso
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the numeroUnidade
	 */
	public String getNumeroUnidade(){

		return numeroUnidade;
	}

	/**
	 * @param numeroUnidade
	 *            the numeroUnidade to set
	 */
	public void setNumeroUnidade(String numeroUnidade){

		this.numeroUnidade = numeroUnidade;
	}

	/**
	 * @return the numeroAducao
	 */
	public String getNumeroAducao(){

		return numeroAducao;
	}

	/**
	 * @param numeroAducao
	 *            the numeroAducao to set
	 */
	public void setNumeroAducao(String numeroAducao){

		this.numeroAducao = numeroAducao;
	}

	/**
	 * @return the numeroDemandaEnergia
	 */
	public String getNumeroDemandaEnergia(){

		return numeroDemandaEnergia;
	}

	/**
	 * @param numeroDemandaEnergia
	 *            the numeroDemandaEnergia to set
	 */
	public void setNumeroDemandaEnergia(String numeroDemandaEnergia){

		this.numeroDemandaEnergia = numeroDemandaEnergia;
	}

	/**
	 * @return the numeroCota
	 */
	public String getNumeroCota(){

		return numeroCota;
	}

	/**
	 * @param numeroCota
	 *            the numeroCota to set
	 */
	public void setNumeroCota(String numeroCota){

		this.numeroCota = numeroCota;
	}

	/**
	 * @return the numeroLatitude
	 */
	public String getNumeroLatitude(){

		return numeroLatitude;
	}

	/**
	 * @param numeroLatitude
	 *            the numeroLatitude to set
	 */
	public void setNumeroLatitude(String numeroLatitude){

		this.numeroLatitude = numeroLatitude;
	}

	/**
	 * @return the numeroLongitude
	 */
	public String getNumeroLongitude(){

		return numeroLongitude;
	}

	/**
	 * @param numeroLongitude
	 *            the numeroLongitude to set
	 */
	public void setNumeroLongitude(String numeroLongitude){

		this.numeroLongitude = numeroLongitude;
	}

	public void setFormValues(Bacia bacia){

		// Metodo usado para setar todos os valores da entidade bacia no Form
		this.setIdBacia(String.valueOf(bacia.getId()));
		this.setCodigo(String.valueOf(bacia.getCodigo()));
		this.setDescricao(bacia.getDescricao());
		this.setDescricaoAbreviada(bacia.getDescricaoAbreviada());

		if(bacia.getSubsistemaEsgoto() != null && bacia.getSubsistemaEsgoto().getSistemaEsgoto() != null){
			this.setIdSistemaEsgoto(String.valueOf(bacia.getSubsistemaEsgoto().getSistemaEsgoto().getId()));
			this.setDescricaoSistemaEsgoto(String.valueOf(bacia.getSubsistemaEsgoto().getSistemaEsgoto().getDescricao()));
		}

		if(bacia.getSubsistemaEsgoto() != null){
			this.setIdSubsistemaEsgoto(String.valueOf(bacia.getSubsistemaEsgoto().getId()));
			this.setDescricaoSubsistemaEsgoto(String.valueOf(bacia.getSubsistemaEsgoto().getDescricao()));
		}

		// Unidade
		String numeroUnidadeStr = "";
		BigDecimal numeroUnidade = bacia.getNumeroUnidade();

		if(numeroUnidade != null){
			numeroUnidadeStr = Util.formataBigDecimal(numeroUnidade, 4, false);
		}

		this.setNumeroUnidade(numeroUnidadeStr);

		// Adu��o
		String numeroAducaoStr = "";
		BigDecimal numeroAducao = bacia.getNumeroAducao();

		if(numeroAducao != null){
			numeroAducaoStr = Util.formataBigDecimal(numeroAducao, 4, false);
		}

		this.setNumeroAducao(numeroAducaoStr);

		// Demanda de energia
		String numeroDemandaEnergiaStr = "";
		BigDecimal numeroDemandaEnergia = bacia.getNumeroDemandaEnergia();

		if(numeroDemandaEnergia != null){
			numeroDemandaEnergiaStr = Util.formataBigDecimal(numeroDemandaEnergia, 4, false);
		}

		this.setNumeroDemandaEnergia(numeroDemandaEnergiaStr);

		// Cota
		String numeroCotaStr = "";
		Integer numeroCota = bacia.getNumeroCota();

		if(numeroCota != null){
			numeroCotaStr = String.valueOf(numeroCota);
		}

		this.setNumeroCota(numeroCotaStr);

		// Latitude
		String numeroLatitudeStr = "";
		Integer numeroLatitude = bacia.getNumeroLatitude();

		if(numeroLatitude != null){
			numeroLatitudeStr = String.valueOf(numeroLatitude);
		}

		this.setNumeroLatitude(numeroLatitudeStr);

		// Longitude
		String numeroLongitudeStr = "";
		Integer numeroLongitude = bacia.getNumeroLongitude();

		if(numeroLongitude != null){
			numeroLongitudeStr = String.valueOf(numeroLongitude);
		}

		this.setNumeroLongitude(numeroLongitudeStr);

		this.setIndicadorUso(String.valueOf(bacia.getIndicadorUso()));
	}

	public Bacia setBaciaValues(Bacia bacia, Collection<Bacia> colecaoEnderecos){

		bacia.setDescricao(this.getDescricao());
		bacia.setDescricaoAbreviada(this.getDescricaoAbreviada());
		bacia.setIndicadorUso(Short.valueOf(this.getIndicadorUso()));

		// Unidade
		BigDecimal numeroUnidade = null;
		String numeroUnidadeStr = this.getNumeroUnidade();

		if(!Util.isVazioOuBranco(numeroUnidadeStr)){
			numeroUnidade = Util.formatarStringParaBigDecimal(numeroUnidadeStr, 4, false);
		}

		bacia.setNumeroUnidade(numeroUnidade);

		// Adu��o
		BigDecimal numeroAducao = null;
		String numeroAducaoStr = this.getNumeroAducao();

		if(!Util.isVazioOuBranco(numeroAducaoStr)){
			numeroAducao = Util.formatarStringParaBigDecimal(numeroAducaoStr, 4, false);
		}

		bacia.setNumeroAducao(numeroAducao);

		// Demanda de energia
		BigDecimal numeroDemandaEnergia = null;
		String numeroDemandaEnergiaStr = this.getNumeroDemandaEnergia();

		if(!Util.isVazioOuBranco(numeroDemandaEnergiaStr)){
			numeroDemandaEnergia = Util.formatarStringParaBigDecimal(numeroDemandaEnergiaStr, 4, false);
		}

		bacia.setNumeroDemandaEnergia(numeroDemandaEnergia);

		// Cole��o de endere�os
		String numeroImovel = null;
		String complementoEndereco = null;
		EnderecoReferencia enderecoReferencia = null;
		LogradouroBairro logradouroBairro = null;
		LogradouroCep logradouroCep = null;

		if(colecaoEnderecos != null && !colecaoEnderecos.isEmpty()){
			Bacia baciaEndereco = (Bacia) Util.retonarObjetoDeColecao(colecaoEnderecos);

			numeroImovel = baciaEndereco.getNumeroImovel();
			complementoEndereco = baciaEndereco.getComplementoEndereco();
			enderecoReferencia = baciaEndereco.getEnderecoReferencia();
			logradouroBairro = baciaEndereco.getLogradouroBairro();
			logradouroCep = baciaEndereco.getLogradouroCep();
		}

		bacia.setNumeroImovel(numeroImovel);
		bacia.setComplementoEndereco(complementoEndereco);
		bacia.setEnderecoReferencia(enderecoReferencia);
		bacia.setLogradouroBairro(logradouroBairro);
		bacia.setLogradouroCep(logradouroCep);

		// Cota
		Integer numeroCota = null;
		String numeroCotaStr = this.getNumeroCota();

		if(numeroCotaStr != null && !numeroCotaStr.equals("")){
			numeroCota = Integer.valueOf(numeroCotaStr);
		}

		bacia.setNumeroCota(numeroCota);

		// Latitude
		Integer numeroLatitude = null;
		String numeroLatitudeStr = this.getNumeroLatitude();

		if(numeroLatitudeStr != null && !numeroLatitudeStr.equals("")){
			numeroLatitude = Integer.valueOf(numeroLatitudeStr);
		}

		bacia.setNumeroLatitude(numeroLatitude);

		// Longitude
		Integer numeroLongitude = null;
		String numeroLongitudeStr = this.getNumeroLongitude();

		if(numeroLongitudeStr != null && !numeroLongitudeStr.equals("")){
			numeroLongitude = Integer.valueOf(numeroLongitudeStr);
		}

		bacia.setNumeroLongitude(numeroLongitude);

		return bacia;
	}
}