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

package gcom.relatorio.cadastro.imovel;

import gcom.relatorio.RelatorioBean;

/**
 * [UC]
 * 
 * @author Hiroshi Goncalves
 * @date 20/08/2013
 */
public class RelatorioBoletimCadastralModelo2Bean
				implements RelatorioBean {

	// DADOS CADASTRAIS DO IMÓVEL
	private String imovelCidade;

	private String imovelLocal;

	private String imovelMatricula;

	private String imovelSetor;

	private String imovelQuadra;

	private String imovelLote;

	private String imovelSubLote;

	private String imovelRota;

	private String imovelControle;

	private String imovelEndereco;

	private String imovelNumero;

	private String imovelCodLogradouro;

	private String imovelBairro;

	private String imovelReferencia;

	private String imovelComplemento;

	private String imovelCEP;

	private String imovelCategoria;

	private String imovelSubCategoria;

	private String imovelNuEconomias;

	private String imovelPavCalcada;

	private String imovelPavRua;

	private String imovelSitLigAgua;

	private String imovelSitLigEsgoto;

	private String imovelFonteAbastecimento;

	private String imovelNuPontos;

	private String imovelNuMoradores;

	// DADOS CADASTRAIS DO CLIENTE

	// Proprietário
	private String proprietarioNome;

	private String proprietarioCodCliente;

	private String proprietarioTipoCliente;

	private String proprietarioEndereco;

	private String proprietarioNumero;

	private String proprietarioCodLogradouro;

	private String proprietarioCPF;

	private String proprietarioRG;

	private String proprietarioDtExpedicao;

	private String proprietarioOrgaoExpedidor;

	private String proprietarioEstado;

	private String proprietarioDtNascimento;

	private String proprietarioCNPJ;

	private String proprietarioInscEstadual;

	private String proprietarioRamoAtividade;

	private String proprietarioNomeMae;

	private String proprietarioFone;

	private String proprietarioEmail;

	// Usuário
	private String usuarioNome;

	private String usuarioCodCliente;

	private String usuarioTipoCliente;

	private String usuarioEndereco;

	private String usuarioNumero;

	private String usuarioCodLogradouro;

	private String usuarioCPF;

	private String usuarioRG;

	private String usuarioDtExpedicao;

	private String usuarioOrgaoExpedidor;

	private String usuarioEstado;

	private String usuarioDtNascimento;

	private String usuarioCNPJ;

	private String usuarioInscEstadual;

	private String usuarioRamoAtividade;

	private String usuarioNomeMae;

	private String usuarioFone;

	private String usuarioEmail;

	// Responsável
	private String responsavelNome;

	private String responsavelCodCliente;

	private String responsavelTipoCliente;

	private String responsavelEndereco;

	private String responsavelNumero;

	private String responsavelCodLogradouro;

	private String responsavelCPF;

	private String responsavelRG;

	private String responsavelDtExpedicao;

	private String responsavelOrgaoExpedidor;

	private String responsavelEstado;

	private String responsavelDtNascimento;

	private String responsavelCNPJ;

	private String responsavelInscEstadual;

	private String responsavelRamoAtividade;

	private String responsavelNomeMae;

	private String responsavelFone;

	private String responsavelEmail;

	private String dataLigacao;

	private String dataCorte;

	private String dataReligacao;

	private String dataSupressao;

	private String dataRestabelecimento;

	private String hidrometro;

	private String dataInstalacao;

	private String leitura;

	private String protecao;

	private Boolean cavalete;

	public RelatorioBoletimCadastralModelo2Bean() {
		super();
		this.limpar();
	}

	public RelatorioBoletimCadastralModelo2Bean(String imovelCidade, String imovelLocal, String imovelMatricula, String imovelSetor,
												String imovelQuadra, String imovelLote, String imovelSubLote, String imovelRota,
												String imovelControle, String imovelEndereco, String imovelNumero,
												String imovelCodLogradouro, String imovelBairro, String imovelReferencia,
												String imovelComplemento, String imovelCEP, String imovelCategoria,
												String imovelSubCategoria, String imovelNuEconomias, String imovelPavCalcada,
												String imovelPavRua, String imovelSitLigAgua, String imovelSitLigEsgoto,
												String imovelFonteAbastecimento, String imovelNuPontos, String imovelNuMoradores,
												String proprietarioNome, String proprietarioCodCliente, String proprietarioTipoCliente,
												String proprietarioEndereco, String proprietarioNumero, String proprietarioCodLogradouro,
												String proprietarioCPF, String proprietarioRG, String proprietarioDtExpedicao,
												String proprietarioOrgaoExpedidor, String proprietarioEstado,
												String proprietarioDtNascimento, String proprietarioCNPJ, String proprietarioInscEstadual,
												String proprietarioRamoAtividade, String proprietarioNomeMae, String proprietarioFone,
												String proprietarioEmail, String usuarioNome, String usuarioCodCliente,
												String usuarioTipoCliente, String usuarioEndereco, String usuarioNumero,
												String usuarioCodLogradouro, String usuarioCPF, String usuarioRG,
												String usuarioDtExpedicao, String usuarioOrgaoExpedidor, String usuarioEstado,
												String usuarioDtNascimento, String usuarioCNPJ, String usuarioInscEstadual,
												String usuarioRamoAtividade, String usuarioNomeMae, String usuarioFone,
												String usuarioEmail, String responsavelNome, String responsavelCodCliente,
												String responsavelTipoCliente, String responsavelEndereco, String responsavelNumero,
												String responsavelCodLogradouro, String responsavelCPF, String responsavelRG,
												String responsavelDtExpedicao, String responsavelOrgaoExpedidor, String responsavelEstado,
												String responsavelDtNascimento, String responsavelCNPJ, String responsavelInscEstadual,
												String responsavelRamoAtividade, String responsavelNomeMae, String responsavelFone,
												String responsavelEmail, String dataLigacao, String dataCorte, String dataReligacao,
												String dataSupressao, String dataRestabelecimento, String hidrometro,
												String dataInstalacao, String leitura, String Protecao, Boolean cavalete) {

		super();
		this.imovelCidade = imovelCidade;
		this.imovelLocal = imovelLocal;
		this.imovelMatricula = imovelMatricula;
		this.imovelSetor = imovelSetor;
		this.imovelQuadra = imovelQuadra;
		this.imovelLote = imovelLote;
		this.imovelSubLote = imovelSubLote;
		this.imovelRota = imovelRota;
		this.imovelControle = imovelControle;
		this.imovelEndereco = imovelEndereco;
		this.imovelNumero = imovelNumero;
		this.imovelCodLogradouro = imovelCodLogradouro;
		this.imovelBairro = imovelBairro;
		this.imovelReferencia = imovelReferencia;
		this.imovelComplemento = imovelComplemento;
		this.imovelCEP = imovelCEP;
		this.imovelCategoria = imovelCategoria;
		this.imovelSubCategoria = imovelSubCategoria;
		this.imovelNuEconomias = imovelNuEconomias;
		this.imovelPavCalcada = imovelPavCalcada;
		this.imovelPavRua = imovelPavRua;
		this.imovelSitLigAgua = imovelSitLigAgua;
		this.imovelSitLigEsgoto = imovelSitLigEsgoto;
		this.imovelFonteAbastecimento = imovelFonteAbastecimento;
		this.imovelNuPontos = imovelNuPontos;
		this.imovelNuMoradores = imovelNuMoradores;
		this.proprietarioNome = proprietarioNome;
		this.proprietarioCodCliente = proprietarioCodCliente;
		this.proprietarioTipoCliente = proprietarioTipoCliente;
		this.proprietarioEndereco = proprietarioEndereco;
		this.proprietarioNumero = proprietarioNumero;
		this.proprietarioCodLogradouro = proprietarioCodLogradouro;
		this.proprietarioCPF = proprietarioCPF;
		this.proprietarioRG = proprietarioRG;
		this.proprietarioDtExpedicao = proprietarioDtExpedicao;
		this.proprietarioOrgaoExpedidor = proprietarioOrgaoExpedidor;
		this.proprietarioEstado = proprietarioEstado;
		this.proprietarioDtNascimento = proprietarioDtNascimento;
		this.proprietarioCNPJ = proprietarioCNPJ;
		this.proprietarioInscEstadual = proprietarioInscEstadual;
		this.proprietarioRamoAtividade = proprietarioRamoAtividade;
		this.proprietarioNomeMae = proprietarioNomeMae;
		this.proprietarioFone = proprietarioFone;
		this.proprietarioEmail = proprietarioEmail;
		this.usuarioNome = usuarioNome;
		this.usuarioCodCliente = usuarioCodCliente;
		this.usuarioTipoCliente = usuarioTipoCliente;
		this.usuarioEndereco = usuarioEndereco;
		this.usuarioNumero = usuarioNumero;
		this.usuarioCodLogradouro = usuarioCodLogradouro;
		this.usuarioCPF = usuarioCPF;
		this.usuarioRG = usuarioRG;
		this.usuarioDtExpedicao = usuarioDtExpedicao;
		this.usuarioOrgaoExpedidor = usuarioOrgaoExpedidor;
		this.usuarioEstado = usuarioEstado;
		this.usuarioDtNascimento = usuarioDtNascimento;
		this.usuarioCNPJ = usuarioCNPJ;
		this.usuarioInscEstadual = usuarioInscEstadual;
		this.usuarioRamoAtividade = usuarioRamoAtividade;
		this.usuarioNomeMae = usuarioNomeMae;
		this.usuarioFone = usuarioFone;
		this.usuarioEmail = usuarioEmail;
		this.responsavelNome = responsavelNome;
		this.responsavelCodCliente = responsavelCodCliente;
		this.responsavelTipoCliente = responsavelTipoCliente;
		this.responsavelEndereco = responsavelEndereco;
		this.responsavelNumero = responsavelNumero;
		this.responsavelCodLogradouro = responsavelCodLogradouro;
		this.responsavelCPF = responsavelCPF;
		this.responsavelRG = responsavelRG;
		this.responsavelDtExpedicao = responsavelDtExpedicao;
		this.responsavelOrgaoExpedidor = responsavelOrgaoExpedidor;
		this.responsavelEstado = responsavelEstado;
		this.responsavelDtNascimento = responsavelDtNascimento;
		this.responsavelCNPJ = responsavelCNPJ;
		this.responsavelInscEstadual = responsavelInscEstadual;
		this.responsavelRamoAtividade = responsavelRamoAtividade;
		this.responsavelNomeMae = responsavelNomeMae;
		this.responsavelFone = responsavelFone;
		this.responsavelEmail = responsavelEmail;
		this.dataLigacao = dataLigacao;
		this.dataCorte = dataCorte;
		this.dataReligacao = dataReligacao;
		this.dataSupressao = dataSupressao;
		this.dataRestabelecimento = dataRestabelecimento;
		this.hidrometro = hidrometro;
		this.dataInstalacao = dataInstalacao;
		this.leitura = leitura;
		this.protecao = protecao;
		this.cavalete = cavalete;
	}

	public void limpar(){

		this.imovelCidade = "";
		this.imovelLocal = "";
		this.imovelMatricula = "";
		this.imovelSetor = "";
		this.imovelQuadra = "";
		this.imovelLote = "";
		this.imovelSubLote = "";
		this.imovelRota = "";
		this.imovelControle = "";
		this.imovelEndereco = "";
		this.imovelNumero = "";
		this.imovelCodLogradouro = "";
		this.imovelBairro = "";
		this.imovelReferencia = "";
		this.imovelComplemento = "";
		this.imovelCEP = "";
		this.imovelCategoria = "";
		this.imovelSubCategoria = "";
		this.imovelNuEconomias = "";
		this.imovelPavCalcada = "";
		this.imovelPavRua = "";
		this.imovelSitLigAgua = "";
		this.imovelSitLigEsgoto = "";
		this.imovelFonteAbastecimento = "";
		this.imovelNuPontos = "";
		this.imovelNuMoradores = "";
		this.proprietarioNome = "";
		this.proprietarioCodCliente = "";
		this.proprietarioTipoCliente = "";
		this.proprietarioEndereco = "";
		this.proprietarioNumero = "";
		this.proprietarioCodLogradouro = "";
		this.proprietarioCPF = "";
		this.proprietarioRG = "";
		this.proprietarioDtExpedicao = "";
		this.proprietarioOrgaoExpedidor = "";
		this.proprietarioEstado = "";
		this.proprietarioDtNascimento = "";
		this.proprietarioCNPJ = "";
		this.proprietarioInscEstadual = "";
		this.proprietarioRamoAtividade = "";
		this.proprietarioNomeMae = "";
		this.proprietarioFone = "";
		this.proprietarioEmail = "";
		this.usuarioNome = "";
		this.usuarioCodCliente = "";
		this.usuarioTipoCliente = "";
		this.usuarioEndereco = "";
		this.usuarioNumero = "";
		this.usuarioCodLogradouro = "";
		this.usuarioCPF = "";
		this.usuarioRG = "";
		this.usuarioDtExpedicao = "";
		this.usuarioOrgaoExpedidor = "";
		this.usuarioEstado = "";
		this.usuarioDtNascimento = "";
		this.usuarioCNPJ = "";
		this.usuarioInscEstadual = "";
		this.usuarioRamoAtividade = "";
		this.usuarioNomeMae = "";
		this.usuarioFone = "";
		this.usuarioEmail = "";
		this.responsavelNome = "";
		this.responsavelCodCliente = "";
		this.responsavelTipoCliente = "";
		this.responsavelEndereco = "";
		this.responsavelNumero = "";
		this.responsavelCodLogradouro = "";
		this.responsavelCPF = "";
		this.responsavelRG = "";
		this.responsavelDtExpedicao = "";
		this.responsavelOrgaoExpedidor = "";
		this.responsavelEstado = "";
		this.responsavelDtNascimento = "";
		this.responsavelCNPJ = "";
		this.responsavelInscEstadual = "";
		this.responsavelRamoAtividade = "";
		this.responsavelNomeMae = "";
		this.responsavelFone = "";
		this.responsavelEmail = "";
		this.dataLigacao = "";
		this.dataCorte = "";
		this.dataReligacao = "";
		this.dataSupressao = "";
		this.dataRestabelecimento = "";
		this.hidrometro = "";
		this.dataInstalacao = "";
		this.leitura = "";
		this.protecao = "";
		this.cavalete = null;
	}

	public String getImovelCidade(){

		return imovelCidade;
	}

	public void setImovelCidade(String imovelCidade){

		this.imovelCidade = imovelCidade;
	}

	public String getImovelLocal(){

		return imovelLocal;
	}

	public void setImovelLocal(String imovelLocal){

		this.imovelLocal = imovelLocal;
	}

	public String getImovelMatricula(){

		return imovelMatricula;
	}

	public void setImovelMatricula(String imovelMatricula){

		this.imovelMatricula = imovelMatricula;
	}

	public String getImovelSetor(){

		return imovelSetor;
	}

	public void setImovelSetor(String imovelSetor){

		this.imovelSetor = imovelSetor;
	}

	public String getImovelQuadra(){

		return imovelQuadra;
	}

	public void setImovelQuadra(String imovelQuadra){

		this.imovelQuadra = imovelQuadra;
	}

	public String getImovelLote(){

		return imovelLote;
	}

	public void setImovelLote(String imovelLote){

		this.imovelLote = imovelLote;
	}

	public String getImovelSubLote(){

		return imovelSubLote;
	}

	public void setImovelSubLote(String imovelSubLote){

		this.imovelSubLote = imovelSubLote;
	}

	public String getImovelRota(){

		return imovelRota;
	}

	public void setImovelRota(String imovelRota){

		this.imovelRota = imovelRota;
	}

	public String getImovelControle(){

		return imovelControle;
	}

	public void setImovelControle(String imovelControle){

		this.imovelControle = imovelControle;
	}

	public String getImovelEndereco(){

		return imovelEndereco;
	}

	public void setImovelEndereco(String imovelEndereco){

		this.imovelEndereco = imovelEndereco;
	}

	public String getImovelNumero(){

		return imovelNumero;
	}

	public void setImovelNumero(String imovelNumero){

		this.imovelNumero = imovelNumero;
	}

	public String getImovelCodLogradouro(){

		return imovelCodLogradouro;
	}

	public void setImovelCodLogradouro(String imovelCodLogradouro){

		this.imovelCodLogradouro = imovelCodLogradouro;
	}

	public String getImovelBairro(){

		return imovelBairro;
	}

	public void setImovelBairro(String imovelBairro){

		this.imovelBairro = imovelBairro;
	}

	public String getImovelReferencia(){

		return imovelReferencia;
	}

	public void setImovelReferencia(String imovelReferencia){

		this.imovelReferencia = imovelReferencia;
	}

	public String getImovelComplemento(){

		return imovelComplemento;
	}

	public void setImovelComplemento(String imovelComplemento){

		this.imovelComplemento = imovelComplemento;
	}

	public String getImovelCEP(){

		return imovelCEP;
	}

	public void setImovelCEP(String imovelCEP){

		this.imovelCEP = imovelCEP;
	}

	public String getImovelCategoria(){

		return imovelCategoria;
	}

	public void setImovelCategoria(String imovelCategoria){

		this.imovelCategoria = imovelCategoria;
	}

	public String getImovelSubCategoria(){

		return imovelSubCategoria;
	}

	public void setImovelSubCategoria(String imovelSubCategoria){

		this.imovelSubCategoria = imovelSubCategoria;
	}

	public String getImovelNuEconomias(){

		return imovelNuEconomias;
	}

	public void setImovelNuEconomias(String imovelNuEconomias){

		this.imovelNuEconomias = imovelNuEconomias;
	}

	public String getImovelPavCalcada(){

		return imovelPavCalcada;
	}

	public void setImovelPavCalcada(String imovelPavCalcada){

		this.imovelPavCalcada = imovelPavCalcada;
	}

	public String getImovelPavRua(){

		return imovelPavRua;
	}

	public void setImovelPavRua(String imovelPavRua){

		this.imovelPavRua = imovelPavRua;
	}

	public String getImovelSitLigAgua(){

		return imovelSitLigAgua;
	}

	public void setImovelSitLigAgua(String imovelSitLigAgua){

		this.imovelSitLigAgua = imovelSitLigAgua;
	}

	public String getImovelSitLigEsgoto(){

		return imovelSitLigEsgoto;
	}

	public void setImovelSitLigEsgoto(String imovelSitLigEsgoto){

		this.imovelSitLigEsgoto = imovelSitLigEsgoto;
	}

	public String getImovelFonteAbastecimento(){

		return imovelFonteAbastecimento;
	}

	public void setImovelFonteAbastecimento(String imovelFonteAbastecimento){

		this.imovelFonteAbastecimento = imovelFonteAbastecimento;
	}

	public String getImovelNuPontos(){

		return imovelNuPontos;
	}

	public void setImovelNuPontos(String imovelNuPontos){

		this.imovelNuPontos = imovelNuPontos;
	}

	public String getImovelNuMoradores(){

		return imovelNuMoradores;
	}

	public void setImovelNuMoradores(String imovelNuMoradores){

		this.imovelNuMoradores = imovelNuMoradores;
	}

	public String getProprietarioNome(){

		return proprietarioNome;
	}

	public void setProprietarioNome(String proprietarioNome){

		this.proprietarioNome = proprietarioNome;
	}

	public String getProprietarioCodCliente(){

		return proprietarioCodCliente;
	}

	public void setProprietarioCodCliente(String proprietarioCodCliente){

		this.proprietarioCodCliente = proprietarioCodCliente;
	}

	public String getProprietarioTipoCliente(){

		return proprietarioTipoCliente;
	}

	public void setProprietarioTipoCliente(String proprietarioTipoCliente){

		this.proprietarioTipoCliente = proprietarioTipoCliente;
	}

	public String getProprietarioEndereco(){

		return proprietarioEndereco;
	}

	public void setProprietarioEndereco(String proprietarioEndereco){

		this.proprietarioEndereco = proprietarioEndereco;
	}

	public String getProprietarioNumero(){

		return proprietarioNumero;
	}

	public void setProprietarioNumero(String proprietarioNumero){

		this.proprietarioNumero = proprietarioNumero;
	}

	public String getProprietarioCodLogradouro(){

		return proprietarioCodLogradouro;
	}

	public void setProprietarioCodLogradouro(String proprietarioCodLogradouro){

		this.proprietarioCodLogradouro = proprietarioCodLogradouro;
	}

	public String getProprietarioCPF(){

		return proprietarioCPF;
	}

	public void setProprietarioCPF(String proprietarioCPF){

		this.proprietarioCPF = proprietarioCPF;
	}

	public String getProprietarioRG(){

		return proprietarioRG;
	}

	public void setProprietarioRG(String proprietarioRG){

		this.proprietarioRG = proprietarioRG;
	}

	public String getProprietarioDtExpedicao(){

		return proprietarioDtExpedicao;
	}

	public void setProprietarioDtExpedicao(String proprietarioDtExpedicao){

		this.proprietarioDtExpedicao = proprietarioDtExpedicao;
	}

	public String getProprietarioOrgaoExpedidor(){

		return proprietarioOrgaoExpedidor;
	}

	public void setProprietarioOrgaoExpedidor(String proprietarioOrgaoExpedidor){

		this.proprietarioOrgaoExpedidor = proprietarioOrgaoExpedidor;
	}

	public String getProprietarioEstado(){

		return proprietarioEstado;
	}

	public void setProprietarioEstado(String proprietarioEstado){

		this.proprietarioEstado = proprietarioEstado;
	}

	public String getProprietarioDtNascimento(){

		return proprietarioDtNascimento;
	}

	public void setProprietarioDtNascimento(String proprietarioDtNascimento){

		this.proprietarioDtNascimento = proprietarioDtNascimento;
	}

	public String getProprietarioCNPJ(){

		return proprietarioCNPJ;
	}

	public void setProprietarioCNPJ(String proprietarioCNPJ){

		this.proprietarioCNPJ = proprietarioCNPJ;
	}

	public String getProprietarioInscEstadual(){

		return proprietarioInscEstadual;
	}

	public void setProprietarioInscEstadual(String proprietarioInscEstadual){

		this.proprietarioInscEstadual = proprietarioInscEstadual;
	}

	public String getProprietarioRamoAtividade(){

		return proprietarioRamoAtividade;
	}

	public void setProprietarioRamoAtividade(String proprietarioRamoAtividade){

		this.proprietarioRamoAtividade = proprietarioRamoAtividade;
	}

	public String getProprietarioNomeMae(){

		return proprietarioNomeMae;
	}

	public void setProprietarioNomeMae(String proprietarioNomeMae){

		this.proprietarioNomeMae = proprietarioNomeMae;
	}

	public String getProprietarioFone(){

		return proprietarioFone;
	}

	public void setProprietarioFone(String proprietarioFone){

		this.proprietarioFone = proprietarioFone;
	}

	public String getProprietarioEmail(){

		return proprietarioEmail;
	}

	public void setProprietarioEmail(String proprietarioEmail){

		this.proprietarioEmail = proprietarioEmail;
	}

	public String getUsuarioNome(){

		return usuarioNome;
	}

	public void setUsuarioNome(String usuarioNome){

		this.usuarioNome = usuarioNome;
	}

	public String getUsuarioCodCliente(){

		return usuarioCodCliente;
	}

	public void setUsuarioCodCliente(String usuarioCodCliente){

		this.usuarioCodCliente = usuarioCodCliente;
	}

	public String getUsuarioTipoCliente(){

		return usuarioTipoCliente;
	}

	public void setUsuarioTipoCliente(String usuarioTipoCliente){

		this.usuarioTipoCliente = usuarioTipoCliente;
	}

	public String getUsuarioEndereco(){

		return usuarioEndereco;
	}

	public void setUsuarioEndereco(String usuarioEndereco){

		this.usuarioEndereco = usuarioEndereco;
	}

	public String getUsuarioNumero(){

		return usuarioNumero;
	}

	public void setUsuarioNumero(String usuarioNumero){

		this.usuarioNumero = usuarioNumero;
	}

	public String getUsuarioCodLogradouro(){

		return usuarioCodLogradouro;
	}

	public void setUsuarioCodLogradouro(String usuarioCodLogradouro){

		this.usuarioCodLogradouro = usuarioCodLogradouro;
	}

	public String getUsuarioCPF(){

		return usuarioCPF;
	}

	public void setUsuarioCPF(String usuarioCPF){

		this.usuarioCPF = usuarioCPF;
	}

	public String getUsuarioRG(){

		return usuarioRG;
	}

	public void setUsuarioRG(String usuarioRG){

		this.usuarioRG = usuarioRG;
	}

	public String getUsuarioDtExpedicao(){

		return usuarioDtExpedicao;
	}

	public void setUsuarioDtExpedicao(String usuarioDtExpedicao){

		this.usuarioDtExpedicao = usuarioDtExpedicao;
	}

	public String getUsuarioOrgaoExpedidor(){

		return usuarioOrgaoExpedidor;
	}

	public void setUsuarioOrgaoExpedidor(String usuarioOrgaoExpedidor){

		this.usuarioOrgaoExpedidor = usuarioOrgaoExpedidor;
	}

	public String getUsuarioEstado(){

		return usuarioEstado;
	}

	public void setUsuarioEstado(String usuarioEstado){

		this.usuarioEstado = usuarioEstado;
	}

	public String getUsuarioDtNascimento(){

		return usuarioDtNascimento;
	}

	public void setUsuarioDtNascimento(String usuarioDtNascimento){

		this.usuarioDtNascimento = usuarioDtNascimento;
	}

	public String getUsuarioCNPJ(){

		return usuarioCNPJ;
	}

	public void setUsuarioCNPJ(String usuarioCNPJ){

		this.usuarioCNPJ = usuarioCNPJ;
	}

	public String getUsuarioInscEstadual(){

		return usuarioInscEstadual;
	}

	public void setUsuarioInscEstadual(String usuarioInscEstadual){

		this.usuarioInscEstadual = usuarioInscEstadual;
	}

	public String getUsuarioRamoAtividade(){

		return usuarioRamoAtividade;
	}

	public void setUsuarioRamoAtividade(String usuarioRamoAtividade){

		this.usuarioRamoAtividade = usuarioRamoAtividade;
	}

	public String getUsuarioNomeMae(){

		return usuarioNomeMae;
	}

	public void setUsuarioNomeMae(String usuarioNomeMae){

		this.usuarioNomeMae = usuarioNomeMae;
	}

	public String getUsuarioFone(){

		return usuarioFone;
	}

	public void setUsuarioFone(String usuarioFone){

		this.usuarioFone = usuarioFone;
	}

	public String getUsuarioEmail(){

		return usuarioEmail;
	}

	public void setUsuarioEmail(String usuarioEmail){

		this.usuarioEmail = usuarioEmail;
	}

	public String getResponsavelNome(){

		return responsavelNome;
	}

	public void setResponsavelNome(String responsavelNome){

		this.responsavelNome = responsavelNome;
	}

	public String getResponsavelCodCliente(){

		return responsavelCodCliente;
	}

	public void setResponsavelCodCliente(String responsavelCodCliente){

		this.responsavelCodCliente = responsavelCodCliente;
	}

	public String getResponsavelTipoCliente(){

		return responsavelTipoCliente;
	}

	public void setResponsavelTipoCliente(String responsavelTipoCliente){

		this.responsavelTipoCliente = responsavelTipoCliente;
	}

	public String getResponsavelEndereco(){

		return responsavelEndereco;
	}

	public void setResponsavelEndereco(String responsavelEndereco){

		this.responsavelEndereco = responsavelEndereco;
	}

	public String getResponsavelNumero(){

		return responsavelNumero;
	}

	public void setResponsavelNumero(String responsavelNumero){

		this.responsavelNumero = responsavelNumero;
	}

	public String getResponsavelCodLogradouro(){

		return responsavelCodLogradouro;
	}

	public void setResponsavelCodLogradouro(String responsavelCodLogradouro){

		this.responsavelCodLogradouro = responsavelCodLogradouro;
	}

	public String getResponsavelCPF(){

		return responsavelCPF;
	}

	public void setResponsavelCPF(String responsavelCPF){

		this.responsavelCPF = responsavelCPF;
	}

	public String getResponsavelRG(){

		return responsavelRG;
	}

	public void setResponsavelRG(String responsavelRG){

		this.responsavelRG = responsavelRG;
	}

	public String getResponsavelDtExpedicao(){

		return responsavelDtExpedicao;
	}

	public void setResponsavelDtExpedicao(String responsavelDtExpedicao){

		this.responsavelDtExpedicao = responsavelDtExpedicao;
	}

	public String getResponsavelOrgaoExpedidor(){

		return responsavelOrgaoExpedidor;
	}

	public void setResponsavelOrgaoExpedidor(String responsavelOrgaoExpedidor){

		this.responsavelOrgaoExpedidor = responsavelOrgaoExpedidor;
	}

	public String getResponsavelEstado(){

		return responsavelEstado;
	}

	public void setResponsavelEstado(String responsavelEstado){

		this.responsavelEstado = responsavelEstado;
	}

	public String getResponsavelDtNascimento(){

		return responsavelDtNascimento;
	}

	public void setResponsavelDtNascimento(String responsavelDtNascimento){

		this.responsavelDtNascimento = responsavelDtNascimento;
	}

	public String getResponsavelCNPJ(){

		return responsavelCNPJ;
	}

	public void setResponsavelCNPJ(String responsavelCNPJ){

		this.responsavelCNPJ = responsavelCNPJ;
	}

	public String getResponsavelInscEstadual(){

		return responsavelInscEstadual;
	}

	public void setResponsavelInscEstadual(String responsavelInscEstadual){

		this.responsavelInscEstadual = responsavelInscEstadual;
	}

	public String getResponsavelRamoAtividade(){

		return responsavelRamoAtividade;
	}

	public void setResponsavelRamoAtividade(String responsavelRamoAtividade){

		this.responsavelRamoAtividade = responsavelRamoAtividade;
	}

	public String getResponsavelNomeMae(){

		return responsavelNomeMae;
	}

	public void setResponsavelNomeMae(String responsavelNomeMae){

		this.responsavelNomeMae = responsavelNomeMae;
	}

	public String getResponsavelFone(){

		return responsavelFone;
	}

	public void setResponsavelFone(String responsavelFone){

		this.responsavelFone = responsavelFone;
	}

	public String getResponsavelEmail(){

		return responsavelEmail;
	}

	public void setResponsavelEmail(String responsavelEmail){

		this.responsavelEmail = responsavelEmail;
	}

	public String getDataLigacao(){

		return dataLigacao;
	}

	public void setDataLigacao(String dataLigacao){

		this.dataLigacao = dataLigacao;
	}

	public String getDataCorte(){

		return dataCorte;
	}

	public void setDataCorte(String dataCorte){

		this.dataCorte = dataCorte;
	}

	public String getDataReligacao(){

		return dataReligacao;
	}

	public void setDataReligacao(String dataReligacao){

		this.dataReligacao = dataReligacao;
	}

	public String getDataSupressao(){

		return dataSupressao;
	}

	public void setDataSupressao(String dataSupressao){

		this.dataSupressao = dataSupressao;
	}

	public String getDataRestabelecimento(){

		return dataRestabelecimento;
	}

	public void setDataRestabelecimento(String dataRestabelecimento){

		this.dataRestabelecimento = dataRestabelecimento;
	}

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public String getDataInstalacao(){

		return dataInstalacao;
	}

	public void setDataInstalacao(String dataInstalacao){

		this.dataInstalacao = dataInstalacao;
	}

	public String getLeitura(){

		return leitura;
	}

	public void setLeitura(String leitura){

		this.leitura = leitura;
	}



	public String getProtecao(){

		return protecao;
	}

	public void setProtecao(String protecao){

		this.protecao = protecao;
	}

	public Boolean getCavalete(){

		return cavalete;
	}

	public void setCavalete(Boolean cavalete){

		this.cavalete = cavalete;
	}

}
