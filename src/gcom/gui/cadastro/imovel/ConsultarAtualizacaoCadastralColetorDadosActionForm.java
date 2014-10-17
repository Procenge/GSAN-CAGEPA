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

package gcom.gui.cadastro.imovel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3113] Atualizacao Cadastral Coletor de Dados
 * 
 * @author Victon Malcolm
 * @since 08/10/2013
 */
public class ConsultarAtualizacaoCadastralColetorDadosActionForm
				extends ActionForm {

	private String inscricao;

	private String nomeCliente;

	private String cpfCnpj;

	private String dsLigacaoAguaSituacao;

	private String dsLigacaoEsgotoSituacao;

	private String endereco;

	private String funcionario;

	private String dataLeitura;

	private String anoMesMovimento;

	private String numeroImovel;

	private String numeroImovelAlteracao;

	private String dsComplementoEndereco;

	private String dsComplementoEnderecoAlteracao;

	private String numeroHidrometro;

	private String numeroHidrometroAlteracao;

	private String qntEconomiaResidencial;

	private String qntEconomiaResidencialAlteracao;

	private String qntEconomiaComercial;

	private String qntEconomiaComercialAlteracao;

	private String qntEconomiaIndustrial;

	private String qntEconomiaIndustrialAlteracao;

	private String qntEconomiaPublica;

	private String qntEconomiaPublicaAlteracao;

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getCpfCnpj(){

		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj){

		this.cpfCnpj = cpfCnpj;
	}

	public String getDsLigacaoAguaSituacao(){

		return dsLigacaoAguaSituacao;
	}

	public void setDsLigacaoAguaSituacao(String dsLigacaoAguaSituacao){

		this.dsLigacaoAguaSituacao = dsLigacaoAguaSituacao;
	}

	public String getDsLigacaoEsgotoSituacao(){

		return dsLigacaoEsgotoSituacao;
	}

	public void setDsLigacaoEsgotoSituacao(String dsLigacaoEsgotoSituacao){

		this.dsLigacaoEsgotoSituacao = dsLigacaoEsgotoSituacao;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(String funcionario){

		this.funcionario = funcionario;
	}

	public String getDataLeitura(){

		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura){

		this.dataLeitura = dataLeitura;
	}

	public String getAnoMesMovimento(){

		return anoMesMovimento;
	}

	public void setAnoMesMovimento(String anoMesMovimento){

		this.anoMesMovimento = anoMesMovimento;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getNumeroImovelAlteracao(){

		return numeroImovelAlteracao;
	}

	public void setNumeroImovelAlteracao(String numeroImovelAlteracao){

		this.numeroImovelAlteracao = numeroImovelAlteracao;
	}

	public String getDsComplementoEndereco(){

		return dsComplementoEndereco;
	}

	public void setDsComplementoEndereco(String dsComplementoEndereco){

		this.dsComplementoEndereco = dsComplementoEndereco;
	}

	public String getDsComplementoEnderecoAlteracao(){

		return dsComplementoEnderecoAlteracao;
	}

	public void setDsComplementoEnderecoAlteracao(String dsComplementoEnderecoAlteracao){

		this.dsComplementoEnderecoAlteracao = dsComplementoEnderecoAlteracao;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getNumeroHidrometroAlteracao(){

		return numeroHidrometroAlteracao;
	}

	public void setNumeroHidrometroAlteracao(String numeroHidrometroAlteracao){

		this.numeroHidrometroAlteracao = numeroHidrometroAlteracao;
	}

	public String getQntEconomiaResidencial(){

		return qntEconomiaResidencial;
	}

	public void setQntEconomiaResidencial(String qntEconomiaResidencial){

		this.qntEconomiaResidencial = qntEconomiaResidencial;
	}

	public String getQntEconomiaResidencialAlteracao(){

		return qntEconomiaResidencialAlteracao;
	}

	public void setQntEconomiaResidencialAlteracao(String qntEconomiaResidencialAlteracao){

		this.qntEconomiaResidencialAlteracao = qntEconomiaResidencialAlteracao;
	}

	public String getQntEconomiaComercial(){

		return qntEconomiaComercial;
	}

	public void setQntEconomiaComercial(String qntEconomiaComercial){

		this.qntEconomiaComercial = qntEconomiaComercial;
	}

	public String getQntEconomiaComercialAlteracao(){

		return qntEconomiaComercialAlteracao;
	}

	public void setQntEconomiaComercialAlteracao(String qntEconomiaComercialAlteracao){

		this.qntEconomiaComercialAlteracao = qntEconomiaComercialAlteracao;
	}

	public String getQntEconomiaIndustrial(){

		return qntEconomiaIndustrial;
	}

	public void setQntEconomiaIndustrial(String qntEconomiaIndustrial){

		this.qntEconomiaIndustrial = qntEconomiaIndustrial;
	}

	public String getQntEconomiaIndustrialAlteracao(){

		return qntEconomiaIndustrialAlteracao;
	}

	public void setQntEconomiaIndustrialAlteracao(String qntEconomiaIndustrialAlteracao){

		this.qntEconomiaIndustrialAlteracao = qntEconomiaIndustrialAlteracao;
	}

	public String getQntEconomiaPublica(){

		return qntEconomiaPublica;
	}

	public void setQntEconomiaPublica(String qntEconomiaPublica){

		this.qntEconomiaPublica = qntEconomiaPublica;
	}

	public String getQntEconomiaPublicaAlteracao(){

		return qntEconomiaPublicaAlteracao;
	}

	public void setQntEconomiaPublicaAlteracao(String qntEconomiaPublicaAlteracao){

		this.qntEconomiaPublicaAlteracao = qntEconomiaPublicaAlteracao;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}
}