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

package gcom.gui.cobranca;

import org.apache.struts.action.ActionForm;

/**
 * InformarEntregaDevolucaoDocumentoCobrancaActionForm
 * [UC3044] Informar Entrega/Devolução de Documentos de Cobrança
 * 
 * @author Carlos Chrystian
 * @created 29 de Fevereiro de 2012
 */

public class InformarEntregaDevolucaoDocumentoCobrancaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String cobrancaAcaoID;

	private String cobrancaAcaoDescricao;

	private String matriculaImovel;

	private String matriculaImovel2;

	private String matriculaImovel3;

	private String matriculaImovel4;

	private String matriculaImovel5;

	private String matriculaImovel6;

	private String matriculaImovel7;

	private String matriculaImovel8;

	private String matriculaImovel9;

	private String matriculaImovel10;

	private String indicadorEntregaDevolucao;

	private String indicadorEntregaDevolucao2;

	private String indicadorEntregaDevolucao3;

	private String indicadorEntregaDevolucao4;

	private String indicadorEntregaDevolucao5;

	private String indicadorEntregaDevolucao6;

	private String indicadorEntregaDevolucao7;

	private String indicadorEntregaDevolucao8;

	private String indicadorEntregaDevolucao9;

	private String indicadorEntregaDevolucao10;

	private String dataEntregaDevolucao;

	private String dataEntregaDevolucao2;

	private String dataEntregaDevolucao3;

	private String dataEntregaDevolucao4;

	private String dataEntregaDevolucao5;

	private String dataEntregaDevolucao6;

	private String dataEntregaDevolucao7;

	private String dataEntregaDevolucao8;

	private String dataEntregaDevolucao9;

	private String dataEntregaDevolucao10;

	private String motivoNaoEntrega;

	private String motivoNaoEntrega2;

	private String motivoNaoEntrega3;

	private String motivoNaoEntrega4;

	private String motivoNaoEntrega5;

	private String motivoNaoEntrega6;

	private String motivoNaoEntrega7;

	private String motivoNaoEntrega8;

	private String motivoNaoEntrega9;

	private String motivoNaoEntrega10;

	private String parecer;

	private String parecer2;

	private String parecer3;

	private String parecer4;

	private String parecer5;

	private String parecer6;

	private String parecer7;

	private String parecer8;

	private String parecer9;

	private String parecer10;

	private String parecerDescricao;

	private String parecerDescricao2;

	private String parecerDescricao3;

	private String parecerDescricao4;

	private String parecerDescricao5;

	private String parecerDescricao6;

	private String parecerDescricao7;

	private String parecerDescricao8;

	private String parecerDescricao9;

	private String parecerDescricao10;

	private String campoMatriculaImovel;

	public String getCobrancaAcaoID(){

		return cobrancaAcaoID;
	}

	public void setCobrancaAcaoID(String cobrancaAcaoID){

		this.cobrancaAcaoID = cobrancaAcaoID;
	}

	public String getCobrancaAcaoDescricao(){

		return cobrancaAcaoDescricao;
	}

	public void setCobrancaAcaoDescricao(String cobrancaAcaoDescricao){

		this.cobrancaAcaoDescricao = cobrancaAcaoDescricao;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getMatriculaImovel2(){

		return matriculaImovel2;
	}

	public void setMatriculaImovel2(String matriculaImovel2){

		this.matriculaImovel2 = matriculaImovel2;
	}

	public String getMatriculaImovel3(){

		return matriculaImovel3;
	}

	public void setMatriculaImovel3(String matriculaImovel3){

		this.matriculaImovel3 = matriculaImovel3;
	}

	public String getMatriculaImovel4(){

		return matriculaImovel4;
	}

	public void setMatriculaImovel4(String matriculaImovel4){

		this.matriculaImovel4 = matriculaImovel4;
	}

	public String getMatriculaImovel5(){

		return matriculaImovel5;
	}

	public void setMatriculaImovel5(String matriculaImovel5){

		this.matriculaImovel5 = matriculaImovel5;
	}

	public String getMatriculaImovel6(){

		return matriculaImovel6;
	}

	public void setMatriculaImovel6(String matriculaImovel6){

		this.matriculaImovel6 = matriculaImovel6;
	}

	public String getMatriculaImovel7(){

		return matriculaImovel7;
	}

	public void setMatriculaImovel7(String matriculaImovel7){

		this.matriculaImovel7 = matriculaImovel7;
	}

	public String getMatriculaImovel8(){

		return matriculaImovel8;
	}

	public void setMatriculaImovel8(String matriculaImovel8){

		this.matriculaImovel8 = matriculaImovel8;
	}

	public String getMatriculaImovel9(){

		return matriculaImovel9;
	}

	public void setMatriculaImovel9(String matriculaImovel9){

		this.matriculaImovel9 = matriculaImovel9;
	}

	public String getMatriculaImovel10(){

		return matriculaImovel10;
	}

	public void setMatriculaImovel10(String matriculaImovel10){

		this.matriculaImovel10 = matriculaImovel10;
	}

	public String getIndicadorEntregaDevolucao(){

		return indicadorEntregaDevolucao;
	}

	public void setIndicadorEntregaDevolucao(String indicadorEntregaDevolucao){

		this.indicadorEntregaDevolucao = indicadorEntregaDevolucao;
	}

	public String getIndicadorEntregaDevolucao2(){

		return indicadorEntregaDevolucao2;
	}

	public void setIndicadorEntregaDevolucao2(String indicadorEntregaDevolucao2){

		this.indicadorEntregaDevolucao2 = indicadorEntregaDevolucao2;
	}

	public String getIndicadorEntregaDevolucao3(){

		return indicadorEntregaDevolucao3;
	}

	public void setIndicadorEntregaDevolucao3(String indicadorEntregaDevolucao3){

		this.indicadorEntregaDevolucao3 = indicadorEntregaDevolucao3;
	}

	public String getIndicadorEntregaDevolucao4(){

		return indicadorEntregaDevolucao4;
	}

	public void setIndicadorEntregaDevolucao4(String indicadorEntregaDevolucao4){

		this.indicadorEntregaDevolucao4 = indicadorEntregaDevolucao4;
	}

	public String getIndicadorEntregaDevolucao5(){

		return indicadorEntregaDevolucao5;
	}

	public void setIndicadorEntregaDevolucao5(String indicadorEntregaDevolucao5){

		this.indicadorEntregaDevolucao5 = indicadorEntregaDevolucao5;
	}

	public String getIndicadorEntregaDevolucao6(){

		return indicadorEntregaDevolucao6;
	}

	public void setIndicadorEntregaDevolucao6(String indicadorEntregaDevolucao6){

		this.indicadorEntregaDevolucao6 = indicadorEntregaDevolucao6;
	}

	public String getIndicadorEntregaDevolucao7(){

		return indicadorEntregaDevolucao7;
	}

	public void setIndicadorEntregaDevolucao7(String indicadorEntregaDevolucao7){

		this.indicadorEntregaDevolucao7 = indicadorEntregaDevolucao7;
	}

	public String getIndicadorEntregaDevolucao8(){

		return indicadorEntregaDevolucao8;
	}

	public void setIndicadorEntregaDevolucao8(String indicadorEntregaDevolucao8){

		this.indicadorEntregaDevolucao8 = indicadorEntregaDevolucao8;
	}

	public String getIndicadorEntregaDevolucao9(){

		return indicadorEntregaDevolucao9;
	}

	public void setIndicadorEntregaDevolucao9(String indicadorEntregaDevolucao9){

		this.indicadorEntregaDevolucao9 = indicadorEntregaDevolucao9;
	}

	public String getIndicadorEntregaDevolucao10(){

		return indicadorEntregaDevolucao10;
	}

	public void setIndicadorEntregaDevolucao10(String indicadorEntregaDevolucao10){

		this.indicadorEntregaDevolucao10 = indicadorEntregaDevolucao10;
	}

	public String getDataEntregaDevolucao(){

		return dataEntregaDevolucao;
	}

	public void setDataEntregaDevolucao(String dataEntregaDevolucao){

		this.dataEntregaDevolucao = dataEntregaDevolucao;
	}

	public String getDataEntregaDevolucao2(){

		return dataEntregaDevolucao2;
	}

	public void setDataEntregaDevolucao2(String dataEntregaDevolucao2){

		this.dataEntregaDevolucao2 = dataEntregaDevolucao2;
	}

	public String getDataEntregaDevolucao3(){

		return dataEntregaDevolucao3;
	}

	public void setDataEntregaDevolucao3(String dataEntregaDevolucao3){

		this.dataEntregaDevolucao3 = dataEntregaDevolucao3;
	}

	public String getDataEntregaDevolucao4(){

		return dataEntregaDevolucao4;
	}

	public void setDataEntregaDevolucao4(String dataEntregaDevolucao4){

		this.dataEntregaDevolucao4 = dataEntregaDevolucao4;
	}

	public String getDataEntregaDevolucao5(){

		return dataEntregaDevolucao5;
	}

	public void setDataEntregaDevolucao5(String dataEntregaDevolucao5){

		this.dataEntregaDevolucao5 = dataEntregaDevolucao5;
	}

	public String getDataEntregaDevolucao6(){

		return dataEntregaDevolucao6;
	}

	public void setDataEntregaDevolucao6(String dataEntregaDevolucao6){

		this.dataEntregaDevolucao6 = dataEntregaDevolucao6;
	}

	public String getDataEntregaDevolucao7(){

		return dataEntregaDevolucao7;
	}

	public void setDataEntregaDevolucao7(String dataEntregaDevolucao7){

		this.dataEntregaDevolucao7 = dataEntregaDevolucao7;
	}

	public String getDataEntregaDevolucao8(){

		return dataEntregaDevolucao8;
	}

	public void setDataEntregaDevolucao8(String dataEntregaDevolucao8){

		this.dataEntregaDevolucao8 = dataEntregaDevolucao8;
	}

	public String getDataEntregaDevolucao9(){

		return dataEntregaDevolucao9;
	}

	public void setDataEntregaDevolucao9(String dataEntregaDevolucao9){

		this.dataEntregaDevolucao9 = dataEntregaDevolucao9;
	}

	public String getDataEntregaDevolucao10(){

		return dataEntregaDevolucao10;
	}

	public void setDataEntregaDevolucao10(String dataEntregaDevolucao10){

		this.dataEntregaDevolucao10 = dataEntregaDevolucao10;
	}

	public String getMotivoNaoEntrega(){

		return motivoNaoEntrega;
	}

	public void setMotivoNaoEntrega(String motivoNaoEntrega){

		this.motivoNaoEntrega = motivoNaoEntrega;
	}

	public String getMotivoNaoEntrega2(){

		return motivoNaoEntrega2;
	}

	public void setMotivoNaoEntrega2(String motivoNaoEntrega2){

		this.motivoNaoEntrega2 = motivoNaoEntrega2;
	}

	public String getMotivoNaoEntrega3(){

		return motivoNaoEntrega3;
	}

	public void setMotivoNaoEntrega3(String motivoNaoEntrega3){

		this.motivoNaoEntrega3 = motivoNaoEntrega3;
	}

	public String getMotivoNaoEntrega4(){

		return motivoNaoEntrega4;
	}

	public void setMotivoNaoEntrega4(String motivoNaoEntrega4){

		this.motivoNaoEntrega4 = motivoNaoEntrega4;
	}

	public String getMotivoNaoEntrega5(){

		return motivoNaoEntrega5;
	}

	public void setMotivoNaoEntrega5(String motivoNaoEntrega5){

		this.motivoNaoEntrega5 = motivoNaoEntrega5;
	}

	public String getMotivoNaoEntrega6(){

		return motivoNaoEntrega6;
	}

	public void setMotivoNaoEntrega6(String motivoNaoEntrega6){

		this.motivoNaoEntrega6 = motivoNaoEntrega6;
	}

	public String getMotivoNaoEntrega7(){

		return motivoNaoEntrega7;
	}

	public void setMotivoNaoEntrega7(String motivoNaoEntrega7){

		this.motivoNaoEntrega7 = motivoNaoEntrega7;
	}

	public String getMotivoNaoEntrega8(){

		return motivoNaoEntrega8;
	}

	public void setMotivoNaoEntrega8(String motivoNaoEntrega8){

		this.motivoNaoEntrega8 = motivoNaoEntrega8;
	}

	public String getMotivoNaoEntrega9(){

		return motivoNaoEntrega9;
	}

	public void setMotivoNaoEntrega9(String motivoNaoEntrega9){

		this.motivoNaoEntrega9 = motivoNaoEntrega9;
	}

	public String getMotivoNaoEntrega10(){

		return motivoNaoEntrega10;
	}

	public void setMotivoNaoEntrega10(String motivoNaoEntrega10){

		this.motivoNaoEntrega10 = motivoNaoEntrega10;
	}

	public String getParecer(){

		return parecer;
	}

	public void setParecer(String parecer){

		this.parecer = parecer;
	}

	public String getParecer2(){

		return parecer2;
	}

	public void setParecer2(String parecer2){

		this.parecer2 = parecer2;
	}

	public String getParecer3(){

		return parecer3;
	}

	public void setParecer3(String parecer3){

		this.parecer3 = parecer3;
	}

	public String getParecer4(){

		return parecer4;
	}

	public void setParecer4(String parecer4){

		this.parecer4 = parecer4;
	}

	public String getParecer5(){

		return parecer5;
	}

	public void setParecer5(String parecer5){

		this.parecer5 = parecer5;
	}

	public String getParecer6(){

		return parecer6;
	}

	public void setParecer6(String parecer6){

		this.parecer6 = parecer6;
	}

	public String getParecer7(){

		return parecer7;
	}

	public void setParecer7(String parecer7){

		this.parecer7 = parecer7;
	}

	public String getParecer8(){

		return parecer8;
	}

	public void setParecer8(String parecer8){

		this.parecer8 = parecer8;
	}

	public String getParecer9(){

		return parecer9;
	}

	public void setParecer9(String parecer9){

		this.parecer9 = parecer9;
	}

	public String getParecer10(){

		return parecer10;
	}

	public void setParecer10(String parecer10){

		this.parecer10 = parecer10;
	}

	public String getParecerDescricao(){

		return parecerDescricao;
	}

	public void setParecerDescricao(String parecerDescricao){

		this.parecerDescricao = parecerDescricao;
	}

	public String getParecerDescricao2(){

		return parecerDescricao2;
	}

	public void setParecerDescricao2(String parecerDescricao2){

		this.parecerDescricao2 = parecerDescricao2;
	}

	public String getParecerDescricao3(){

		return parecerDescricao3;
	}

	public void setParecerDescricao3(String parecerDescricao3){

		this.parecerDescricao3 = parecerDescricao3;
	}

	public String getParecerDescricao4(){

		return parecerDescricao4;
	}

	public void setParecerDescricao4(String parecerDescricao4){

		this.parecerDescricao4 = parecerDescricao4;
	}

	public String getParecerDescricao5(){

		return parecerDescricao5;
	}

	public void setParecerDescricao5(String parecerDescricao5){

		this.parecerDescricao5 = parecerDescricao5;
	}

	public String getParecerDescricao6(){

		return parecerDescricao6;
	}

	public void setParecerDescricao6(String parecerDescricao6){

		this.parecerDescricao6 = parecerDescricao6;
	}

	public String getParecerDescricao7(){

		return parecerDescricao7;
	}

	public void setParecerDescricao7(String parecerDescricao7){

		this.parecerDescricao7 = parecerDescricao7;
	}

	public String getParecerDescricao8(){

		return parecerDescricao8;
	}

	public void setParecerDescricao8(String parecerDescricao8){

		this.parecerDescricao8 = parecerDescricao8;
	}

	public String getParecerDescricao9(){

		return parecerDescricao9;
	}

	public void setParecerDescricao9(String parecerDescricao9){

		this.parecerDescricao9 = parecerDescricao9;
	}

	public String getParecerDescricao10(){

		return parecerDescricao10;
	}

	public void setParecerDescricao10(String parecerDescricao10){

		this.parecerDescricao10 = parecerDescricao10;
	}

	public String getCampoMatriculaImovel(){

		return campoMatriculaImovel;
	}

	public void setCampoMatriculaImovel(String campoMatriculaImovel){

		this.campoMatriculaImovel = campoMatriculaImovel;
	}

}
