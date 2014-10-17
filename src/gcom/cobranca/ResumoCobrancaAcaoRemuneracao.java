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

package gcom.cobranca;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class ResumoCobrancaAcaoRemuneracao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private BigDecimal percentualRemuneracao;

	/** persistent field */
	private BigDecimal valorRemuneracao;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private gcom.cobranca.ResumoCobrancaAcaoEventual resumoCobrancaAcaoEventual;

	/** persistent field */
	private gcom.cobranca.DocumentoTipo documentoTipo;

	/** full constructor */
	public ResumoCobrancaAcaoRemuneracao(Integer id, BigDecimal percentualRemuneracao, BigDecimal valorRemuneracao, Date ultimaAlteracao,
											ResumoCobrancaAcaoEventual resumoCobrancaAcaoEventual, DocumentoTipo documentoTipo) {

		this.id = id;
		this.percentualRemuneracao = percentualRemuneracao;
		this.valorRemuneracao = valorRemuneracao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.resumoCobrancaAcaoEventual = resumoCobrancaAcaoEventual;
		this.documentoTipo = documentoTipo;

	}

	/** default constructor */
	public ResumoCobrancaAcaoRemuneracao() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.cobranca.ResumoCobrancaAcaoEventual getResumoCobrancaAcaoEventual(){

		return resumoCobrancaAcaoEventual;
	}

	public void setResumoCobrancaAcaoEventual(gcom.cobranca.ResumoCobrancaAcaoEventual resumoCobrancaAcaoEventual){

		this.resumoCobrancaAcaoEventual = resumoCobrancaAcaoEventual;
	}

	public gcom.cobranca.DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(gcom.cobranca.DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((documentoTipo == null) ? 0 : documentoTipo.hashCode());
		result = prime * result + ((percentualRemuneracao == null) ? 0 : percentualRemuneracao.hashCode());
		result = prime * result + ((resumoCobrancaAcaoEventual == null) ? 0 : resumoCobrancaAcaoEventual.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;

		ResumoCobrancaAcaoRemuneracao other = (ResumoCobrancaAcaoRemuneracao) obj;

		// resumoCobrancaAcaoEventual.id
		if(resumoCobrancaAcaoEventual == null){
			if(other.resumoCobrancaAcaoEventual != null) return false;
		}else if(other.resumoCobrancaAcaoEventual == null || !resumoCobrancaAcaoEventual.equals(other.resumoCobrancaAcaoEventual)) return false;

		// documentoTipo.id
		if(documentoTipo == null || documentoTipo.getId() == null){
			if(other.documentoTipo != null && other.documentoTipo.getId() != null) return false;
		}else if(other.documentoTipo == null || other.documentoTipo.getId() == null
						|| !documentoTipo.getId().equals(other.documentoTipo.getId())) return false;

		// percentualRemuneracao
		if(percentualRemuneracao == null){
			if(other.percentualRemuneracao != null) return false;
		}else if(other.percentualRemuneracao == null || percentualRemuneracao.compareTo(other.percentualRemuneracao) != 0) return false;

		return true;
	}

}
