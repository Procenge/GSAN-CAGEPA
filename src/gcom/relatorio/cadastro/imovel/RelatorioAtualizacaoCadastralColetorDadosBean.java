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
 * @author Victon Santos
 * @date 10/10/2013
 */
public class RelatorioAtualizacaoCadastralColetorDadosBean
				implements RelatorioBean {
	
	private String referencia;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String leiturista;

	private String dataHora;

	private String numeroImovel;

	private String complemento;

	private String numeroHidrometro;

	private String qntEconomiaResidencial;

	private String qntEconomiaComercial;

	private String qntEconomiaIndustrial;

	private String qntEconomiaPublica;

	private String numeroImovelAlteracao;

	private String complementoAlteracao;

	private String numeroHidrometroAlteracao;

	private String qntEconomiaResidencialAlteracao;

	private String qntEconomiaComercialAlteracao;

	private String qntEconomiaIndustrialAlteracao;

	private String qntEconomiaPublicaAlteracao;

	public RelatorioAtualizacaoCadastralColetorDadosBean() {

	}

	public RelatorioAtualizacaoCadastralColetorDadosBean(String referencia, String matriculaImovel, String inscricaoImovel,
															String leiturista, String dataHora, String numeroImovel, String complemento,
															String numeroHidrometro, String qntEconomiaResidencial,
															String qntEconomiaComercial, String qntEconomiaIndustrial,
															String qntEconomiaPublica, String numeroImovelAlteracao,
															String complementoAlteracao, String numeroHidrometroAlteracao,
															String qntEconomiaResidencialAlteracao, String qntEconomiaComercialAlteracao,
															String qntEconomiaIndustrialAlteracao, String qntEconomiaPublicaAlteracao) {

		this.referencia = referencia;
		this.matriculaImovel = matriculaImovel;
		this.inscricaoImovel = inscricaoImovel;
		this.leiturista = leiturista;
		this.dataHora = dataHora;
		this.numeroImovel = numeroImovel;
		this.complemento = complemento;
		this.numeroHidrometro = numeroHidrometro;
		this.qntEconomiaResidencial = qntEconomiaResidencial;
		this.qntEconomiaComercial = qntEconomiaComercial;
		this.qntEconomiaIndustrial = qntEconomiaIndustrial;
		this.qntEconomiaPublica = qntEconomiaPublica;
		this.numeroImovelAlteracao = numeroImovelAlteracao;
		this.complementoAlteracao = complementoAlteracao;
		this.numeroHidrometroAlteracao = numeroHidrometroAlteracao;
		this.qntEconomiaResidencialAlteracao = qntEconomiaResidencialAlteracao;
		this.qntEconomiaComercialAlteracao = qntEconomiaComercialAlteracao;
		this.qntEconomiaIndustrialAlteracao = qntEconomiaIndustrialAlteracao;
		this.qntEconomiaPublicaAlteracao = qntEconomiaPublicaAlteracao;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getLeiturista(){

		return leiturista;
	}

	public void setLeiturista(String leiturista){

		this.leiturista = leiturista;
	}

	public String getDataHora(){

		return dataHora;
	}

	public void setDataHora(String dataHora){

		this.dataHora = dataHora;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getComplemento(){

		return complemento;
	}

	public void setComplemento(String complemento){

		this.complemento = complemento;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public String getQntEconomiaResidencial(){

		return qntEconomiaResidencial;
	}

	public void setQntEconomiaResidencial(String qntEconomiaResidencial){

		this.qntEconomiaResidencial = qntEconomiaResidencial;
	}

	public String getQntEconomiaComercial(){

		return qntEconomiaComercial;
	}

	public void setQntEconomiaComercial(String qntEconomiaComercial){

		this.qntEconomiaComercial = qntEconomiaComercial;
	}

	public String getQntEconomiaIndustrial(){

		return qntEconomiaIndustrial;
	}

	public void setQntEconomiaIndustrial(String qntEconomiaIndustrial){

		this.qntEconomiaIndustrial = qntEconomiaIndustrial;
	}

	public String getQntEconomiaPublica(){

		return qntEconomiaPublica;
	}

	public void setQntEconomiaPublica(String qntEconomiaPublica){

		this.qntEconomiaPublica = qntEconomiaPublica;
	}

	public String getNumeroImovelAlteracao(){

		return numeroImovelAlteracao;
	}

	public void setNumeroImovelAlteracao(String numeroImovelAlteracao){

		this.numeroImovelAlteracao = numeroImovelAlteracao;
	}

	public String getComplementoAlteracao(){

		return complementoAlteracao;
	}

	public void setComplementoAlteracao(String complementoAlteracao){

		this.complementoAlteracao = complementoAlteracao;
	}

	public String getNumeroHidrometroAlteracao(){

		return numeroHidrometroAlteracao;
	}

	public void setNumeroHidrometroAlteracao(String numeroHidrometroAlteracao){

		this.numeroHidrometroAlteracao = numeroHidrometroAlteracao;
	}

	public String getQntEconomiaResidencialAlteracao(){

		return qntEconomiaResidencialAlteracao;
	}

	public void setQntEconomiaResidencialAlteracao(String qntEconomiaResidencialAlteracao){

		this.qntEconomiaResidencialAlteracao = qntEconomiaResidencialAlteracao;
	}

	public String getQntEconomiaComercialAlteracao(){

		return qntEconomiaComercialAlteracao;
	}

	public void setQntEconomiaComercialAlteracao(String qntEconomiaComercialAlteracao){

		this.qntEconomiaComercialAlteracao = qntEconomiaComercialAlteracao;
	}

	public String getQntEconomiaIndustrialAlteracao(){

		return qntEconomiaIndustrialAlteracao;
	}

	public void setQntEconomiaIndustrialAlteracao(String qntEconomiaIndustrialAlteracao){

		this.qntEconomiaIndustrialAlteracao = qntEconomiaIndustrialAlteracao;
	}

	public String getQntEconomiaPublicaAlteracao(){

		return qntEconomiaPublicaAlteracao;
	}

	public void setQntEconomiaPublicaAlteracao(String qntEconomiaPublicaAlteracao){

		this.qntEconomiaPublicaAlteracao = qntEconomiaPublicaAlteracao;
	}

}
