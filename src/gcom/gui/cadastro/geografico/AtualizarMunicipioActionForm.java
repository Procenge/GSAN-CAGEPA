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

package gcom.gui.cadastro.geografico;

import java.util.Date;

import gcom.arrecadacao.banco.Agencia;
import gcom.cadastro.geografico.Microrregiao;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.geografico.RegiaoDesenvolvimento;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import org.apache.struts.validator.ValidatorForm;

/**
 * [UC005] Manter Municipio [SB0001]Atualizar Municipio
 * 
 * @author K�ssia Albuquerque
 * @date 08/01/2007
 */

public class AtualizarMunicipioActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String codigoMunicipio;

	private String nomeMunicipio;

	private String codigoDdd;

	private String unidadeFederacao;

	private String microregiao;

	private String regiaoDesenv;

	private String cepInicial;

	private String cepFinal;

	private String dataInicioConcessao;

	private String dataFimConcessao;

	private String indicadorUso;

	private String nomePrefeitura;

	private String enderecoPrefeitura;

	private String numeroCnpjPrefeitura;

	private String banco;

	private String agencia;

	private String numeroContaBancaria;

	private String nomePrefeito;

	private String numeroCpfPrefeito;

	private String nomePartidoPrefeito;

	private String nacionalidadePrefeito;

	private String estadoCivilPrefeito;

	private String cepPadrao;

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getCepFinal(){

		return cepFinal;
	}

	public void setCepFinal(String cepFinal){

		this.cepFinal = cepFinal;
	}

	public String getCepInicial(){

		return cepInicial;
	}

	public void setCepInicial(String cepInicial){

		this.cepInicial = cepInicial;
	}

	public String getCodigoDdd(){

		return codigoDdd;
	}

	public void setCodigoDdd(String codigoDdd){

		this.codigoDdd = codigoDdd;
	}

	public String getCodigoMunicipio(){

		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio){

		this.codigoMunicipio = codigoMunicipio;
	}

	public String getDataFimConcessao(){

		return dataFimConcessao;
	}

	public void setDataFimConcessao(String dataFimConcessao){

		this.dataFimConcessao = dataFimConcessao;
	}

	public String getDataInicioConcessao(){

		return dataInicioConcessao;
	}

	public void setDataInicioConcessao(String dataInicioConcessao){

		this.dataInicioConcessao = dataInicioConcessao;
	}

	public String getMicroregiao(){

		return microregiao;
	}

	public void setMicroregiao(String microregiao){

		this.microregiao = microregiao;
	}

	public String getNomeMunicipio(){

		return nomeMunicipio;
	}

	public void setNomeMunicipio(String nomeMunicipio){

		this.nomeMunicipio = nomeMunicipio;
	}

	public String getRegiaoDesenv(){

		return regiaoDesenv;
	}

	public void setRegiaoDesenv(String regiaoDesenv){

		this.regiaoDesenv = regiaoDesenv;
	}

	public String getUnidadeFederacao(){

		return unidadeFederacao;
	}

	public void setUnidadeFederacao(String unidadeFederacao){

		this.unidadeFederacao = unidadeFederacao;
	}

	public String getNomePrefeitura(){

		return nomePrefeitura;
	}

	public void setNomePrefeitura(String nomePrefeitura){

		this.nomePrefeitura = nomePrefeitura;
	}

	public String getEnderecoPrefeitura(){

		return enderecoPrefeitura;
	}

	public void setEnderecoPrefeitura(String enderecoPrefeitura){

		this.enderecoPrefeitura = enderecoPrefeitura;
	}

	public String getNumeroCnpjPrefeitura(){

		return numeroCnpjPrefeitura;
	}

	public void setNumeroCnpjPrefeitura(String numeroCnpjPrefeitura){

		this.numeroCnpjPrefeitura = numeroCnpjPrefeitura;
	}

	public String getBanco(){

		return banco;
	}

	public void setBanco(String banco){

		this.banco = banco;
	}

	public String getAgencia(){

		return agencia;
	}

	public void setAgencia(String agencia){

		this.agencia = agencia;
	}

	public String getNumeroContaBancaria(){

		return numeroContaBancaria;
	}

	public void setNumeroContaBancaria(String numeroContaBancaria){

		this.numeroContaBancaria = numeroContaBancaria;
	}

	public String getNomePrefeito(){

		return nomePrefeito;
	}

	public void setNomePrefeito(String nomePrefeito){

		this.nomePrefeito = nomePrefeito;
	}

	public String getNumeroCpfPrefeito(){

		return numeroCpfPrefeito;
	}

	public void setNumeroCpfPrefeito(String numeroCpfPrefeito){

		this.numeroCpfPrefeito = numeroCpfPrefeito;
	}

	public String getNomePartidoPrefeito(){

		return nomePartidoPrefeito;
	}

	public void setNomePartidoPrefeito(String nomePartidoPrefeito){

		this.nomePartidoPrefeito = nomePartidoPrefeito;
	}

	public String getNacionalidadePrefeito(){

		return nacionalidadePrefeito;
	}

	public void setNacionalidadePrefeito(String nacionalidadePrefeito){

		this.nacionalidadePrefeito = nacionalidadePrefeito;
	}

	public String getEstadoCivilPrefeito(){

		return estadoCivilPrefeito;
	}

	public void setEstadoCivilPrefeito(String estadoCivilPrefeito){

		this.estadoCivilPrefeito = estadoCivilPrefeito;
	}

	public String getCepPadrao(){

		return cepPadrao;
	}

	public void setCepPadrao(String cepPadrao){

		this.cepPadrao = cepPadrao;
	}

	// Esse m�todo carrega todos os valores da base de dados
	// neces�rios para exibi��o da tela AtualizarMunicipio.

	public Municipio setFormValues(Municipio municipio, Short parametroSistema){

		// Metodo usado para setar todos os valores do Form nn base de dados

		municipio.setId(Integer.valueOf(getCodigoMunicipio()));
		municipio.setNome(getNomeMunicipio());
		municipio.setDdd(Short.valueOf(getCodigoDdd()));

		if(getUnidadeFederacao() != null && !getUnidadeFederacao().equals("")){

			UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
			unidadeFederacao.setId(Integer.parseInt(getUnidadeFederacao()));
			municipio.setUnidadeFederacao(unidadeFederacao);
		}

		if(getMicroregiao() != null && !getMicroregiao().equals("")){

			Microrregiao microrregiao = new Microrregiao();
			microrregiao.setId(Integer.parseInt(getMicroregiao()));
			municipio.setMicrorregiao(microrregiao);
		}

		if(getRegiaoDesenv() != null && !getRegiaoDesenv().equals("")){

			RegiaoDesenvolvimento regiaoDesenv = new RegiaoDesenvolvimento();
			regiaoDesenv.setId(Integer.parseInt(getRegiaoDesenv()));
			municipio.setRegiaoDesenvolvimento(regiaoDesenv);
		}
		if(parametroSistema == SistemaParametro.INDICADOR_EMPRESA_ADA.shortValue()){
			municipio.setCepInicio(Integer.valueOf(getCepInicial()));
			municipio.setCepFim(Integer.valueOf(getCepFinal()));
		}
		if(parametroSistema == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
			municipio.setCepPadrao(Integer.valueOf(getCepPadrao()));
		}

		municipio.setDataConcessaoInicio(Util.converteStringParaDate(getDataInicioConcessao()));
		municipio.setDataConcessaoFim(Util.converteStringParaDate(getDataFimConcessao()));
		municipio.setUltimaAlteracao(new Date());
		municipio.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);
		if(parametroSistema == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
			municipio.setNomePrefeitura(getNomePrefeitura());
			municipio.setEnderecoPrefeitura(getEnderecoPrefeitura());

			municipio.setNumeroCnpjPrefeitura(getNumeroCnpjPrefeitura());

			if(getAgencia() != null && !getAgencia().equals("") && Integer.parseInt(getAgencia()) > 0){
				Agencia agenciaPrefeitura = new Agencia();
				agenciaPrefeitura.setId(Integer.parseInt(getAgencia()));
				municipio.setAgencia(agenciaPrefeitura);
			}

			if(getNumeroContaBancaria() != null && !getNumeroContaBancaria().equals("")){
				municipio.setNumeroContaBancaria(Integer.valueOf(getNumeroContaBancaria()));
			}

			municipio.setNomePrefeito(getNomePrefeito());
			municipio.setNumeroCpfPrefeito(getNumeroCpfPrefeito());

			municipio.setNomePartidoPrefeito(getNomePartidoPrefeito());
			municipio.setNacionalidadePrefeito(getNacionalidadePrefeito());
			municipio.setEstadoCivilPrefeito(getEstadoCivilPrefeito());
		}

		return municipio;
	}

}
