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
 * [UC0XXX] Gerar Relatório Grandes Consumidores
 * 
 * @author Anderson Italo
 * @date 15/02/2011
 */
public class RelatorioGrandesConsumidoresBean
				implements RelatorioBean {

	private String codigoLocalidade;

	private String nomeLocalidade;

	private String codigoGerencia;

	private String nomeGerencia;

	private String cliente;

	private String situacaoLigacaoAgua;

	private String situacaoLigacaoEsgoto;

	private String grandeConsumidorMes;

	private Integer totalRegistros;

	private String matriculaImovel;

	private String economias;

	private String media;

	private String inscricao;

	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCodigoGerencia(){

		return codigoGerencia;
	}

	public void setCodigoGerencia(String codigoGerencia){

		this.codigoGerencia = codigoGerencia;
	}

	public String getNomeGerencia(){

		return nomeGerencia;
	}

	public void setNomeGerencia(String nomeGerencia){

		this.nomeGerencia = nomeGerencia;
	}

	public String getCliente(){

		return cliente;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public String getSituacaoLigacaoAgua(){

		return situacaoLigacaoAgua;
	}

	public void setSituacaoLigacaoAgua(String situacaoLigacaoAgua){

		this.situacaoLigacaoAgua = situacaoLigacaoAgua;
	}

	public String getSituacaoLigacaoEsgoto(){

		return situacaoLigacaoEsgoto;
	}

	public void setSituacaoLigacaoEsgoto(String situacaoLigacaoEsgoto){

		this.situacaoLigacaoEsgoto = situacaoLigacaoEsgoto;
	}

	public String getGrandeConsumidorMes(){

		return grandeConsumidorMes;
	}

	public void setGrandeConsumidorMes(String grandeConsumidorMes){

		this.grandeConsumidorMes = grandeConsumidorMes;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getEconomias(){

		return economias;
	}

	public void setEconomias(String economias){

		this.economias = economias;
	}

	public String getMedia(){

		return media;
	}

	public void setMedia(String media){

		this.media = media;
	}

	public Integer getTotalRegistros(){

		return totalRegistros;
	}

	public void setTotalRegistros(Integer totalRegistros){

		this.totalRegistros = totalRegistros;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

}
