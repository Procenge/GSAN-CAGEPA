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

import java.io.Serializable;

/**
 * [UC3014] Emitir Declaração Anual Quitação Débitos
 * 
 * @author Carlos Chrystian
 * @date 29/04/2013
 */
public class DeclaracaoAnualQuitacaoDebitosHelper
				implements Serializable {

	private String inscricao;

	private String usuario;

	private String endereco;

	private String matricula;

	private String codigoResponsavel;

	private String sequencialImpressao;

	private String ciclo;

	private String anoBase;

	private String local;

	private String setor;

	private String rota;

	private String segmento;

	private String lote;

	private String sublote;

	private String cidade;

	/**
	 * @return the inscricao
	 */
	public String getInscricao(){

		return inscricao;
	}

	/**
	 * @param inscricao
	 *            the inscricao to set
	 */
	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario(){

		return usuario;
	}

	/**
	 * @param usuario
	 *            the usuario to set
	 */
	public void setUsuario(String usuario){

		this.usuario = usuario;
	}

	/**
	 * @return the endereco
	 */
	public String getEndereco(){

		return endereco;
	}

	/**
	 * @param endereco
	 *            the endereco to set
	 */
	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	/**
	 * @return the matricula
	 */
	public String getMatricula(){

		return matricula;
	}

	/**
	 * @param matricula
	 *            the matricula to set
	 */
	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	/**
	 * @return the codigoResponsavel
	 */
	public String getCodigoResponsavel(){

		return codigoResponsavel;
	}

	/**
	 * @param codigoResponsavel
	 *            the codigoResponsavel to set
	 */
	public void setCodigoResponsavel(String codigoResponsavel){

		this.codigoResponsavel = codigoResponsavel;
	}

	/**
	 * @return the sequencialImpressao
	 */
	public String getSequencialImpressao(){

		return sequencialImpressao;
	}

	/**
	 * @param sequencialImpressao
	 *            the sequencialImpressao to set
	 */
	public void setSequencialImpressao(String sequencialImpressao){

		this.sequencialImpressao = sequencialImpressao;
	}

	/**
	 * @return the ciclo
	 */
	public String getCiclo(){

		return ciclo;
	}

	/**
	 * @param ciclo
	 *            the ciclo to set
	 */
	public void setCiclo(String ciclo){

		this.ciclo = ciclo;
	}

	/**
	 * @return the anoBase
	 */
	public String getAnoBase(){

		return anoBase;
	}

	/**
	 * @param anoBase
	 *            the anoBase to set
	 */
	public void setAnoBase(String anoBase){

		this.anoBase = anoBase;
	}

	public String getLocal(){

		return local;
	}

	public void setLocal(String local){

		this.local = local;
	}

	public String getSetor(){

		return setor;
	}

	public void setSetor(String setor){

		this.setor = setor;
	}

	public String getRota(){

		return rota;
	}

	public void setRota(String rota){

		this.rota = rota;
	}

	public String getSegmento(){

		return segmento;
	}

	public void setSegmento(String segmento){

		this.segmento = segmento;
	}

	public String getLote(){

		return lote;
	}

	public void setLote(String lote){

		this.lote = lote;
	}

	public String getSublote(){

		return sublote;
	}

	public void setSublote(String sublote){

		this.sublote = sublote;
	}

	public String getCidade(){

		return cidade;
	}

	public void setCidade(String cidade){

		this.cidade = cidade;
	}

}
