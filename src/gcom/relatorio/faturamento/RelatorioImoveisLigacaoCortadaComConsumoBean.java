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

package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;

/**
 * [UC]
 * 
 * @author Hiroshi Goncalves
 * @date 09/12/2013
 */
public class RelatorioImoveisLigacaoCortadaComConsumoBean
				implements RelatorioBean {

	private String localidade;

	private String setorComercial;

	private String matriculaImovel;

	private String nomeCliente;

	private String quadra;

	private String lote;

	private String dtCorte;

	private String leituraCorte;

	private String consumoAnterior;

	private String refConsumoAnterior;

	private String leituraAnterior;

	private String leituraAtual;

	private String consumo;

	public RelatorioImoveisLigacaoCortadaComConsumoBean(String localidade, String setorComercial, String matriculaImovel,
														String nomeCliente, String quadra, String lote, String dtCorte,
														String leituraCorte, String consumoAnterior, String refConsumoAnterior,
														String leituraAnterior, String leituraAtual, String consumo) {

		super();
		this.localidade = localidade;
		this.setorComercial = setorComercial;
		this.matriculaImovel = matriculaImovel;
		this.nomeCliente = nomeCliente;
		this.quadra = quadra;
		this.lote = lote;
		this.dtCorte = dtCorte;
		this.leituraCorte = leituraCorte;
		this.consumoAnterior = consumoAnterior;
		this.refConsumoAnterior = refConsumoAnterior;
		this.leituraAnterior = leituraAnterior;
		this.leituraAtual = leituraAtual;
		this.consumo = consumo;
	}



	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(String setorComercial){

		this.setorComercial = setorComercial;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getQuadra(){

		return quadra;
	}

	public void setQuadra(String quadra){

		this.quadra = quadra;
	}

	public String getLote(){

		return lote;
	}

	public void setLote(String lote){

		this.lote = lote;
	}

	public String getDtCorte(){

		return dtCorte;
	}

	public void setDtCorte(String dtCorte){

		this.dtCorte = dtCorte;
	}

	public String getLeituraCorte(){

		return leituraCorte;
	}

	public void setLeituraCorte(String leituraCorte){

		this.leituraCorte = leituraCorte;
	}

	public String getConsumoAnterior(){

		return consumoAnterior;
	}

	public void setConsumoAnterior(String consumoAnterior){

		this.consumoAnterior = consumoAnterior;
	}

	public String getRefConsumoAnterior(){

		return refConsumoAnterior;
	}

	public void setRefConsumoAnterior(String refConsumoAnterior){

		this.refConsumoAnterior = refConsumoAnterior;
	}

	public String getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(String leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public String getLeituraAtual(){

		return leituraAtual;
	}

	public void setLeituraAtual(String leituraAtual){

		this.leituraAtual = leituraAtual;
	}

	public String getConsumo(){

		return consumo;
	}

	public void setConsumo(String consumo){

		this.consumo = consumo;
	}



}
