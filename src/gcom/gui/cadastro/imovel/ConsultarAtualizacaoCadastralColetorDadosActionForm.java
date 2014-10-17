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