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

package gcom.relatorio.faturamento;

import java.io.Serializable;

/**
 * [UC3014] Emitir Declara��o Anual Quita��o D�bitos
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
