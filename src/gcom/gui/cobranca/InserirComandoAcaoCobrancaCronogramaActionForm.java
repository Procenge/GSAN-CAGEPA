/*
 * 
 */
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
 * GSANPCG
 * Virgínia Melo
 * Eduardo Henrique
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

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0243] Inserir Comando de Ação de Cobrança - Tipo de Comando Cronograma
 * 
 * @author Rafael Santos
 * @since 24/01/2006
 * @author eduardo henrique
 * @date 28/08/2008
 * @author Virgínia Melo
 * @date 06/11/2008
 */
public class InserirComandoAcaoCobrancaCronogramaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String referenciaCobranca;

	private String cobrancaGrupo;

	private String cobrancaAcao;

	// TODO -> vsm: retirar programa cobranca
	private String idPrograma;

	private String idAcao;

	private String descricaoPrograma;

	private String descricaoAcao;

	private String empresa;

	// TODO -> vsm: retirar 2 datas
	private String dataPrevista;

	private String dataPrazo;

	private String cobrancaAtividade;

	/**
	 * @return Returns the acaoCobranca.
	 */
	public String getCobrancaAcao(){

		return cobrancaAcao;
	}

	/**
	 * @param acaoCobranca
	 *            The acaoCobranca to set.
	 */
	public void setCobrancaAcao(String acaoCobranca){

		this.cobrancaAcao = acaoCobranca;
	}

	/**
	 * @return Returns the grupoCobranca.
	 */
	public String getCobrancaGrupo(){

		return cobrancaGrupo;
	}

	/**
	 * @param grupoCobranca
	 *            The grupoCobranca to set.
	 */
	public void setCobrancaGrupo(String grupoCobranca){

		this.cobrancaGrupo = grupoCobranca;
	}

	public ActionErrors validate(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		/** @todo: finish this method, this is just the skeleton. */
		return null;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		referenciaCobranca = null;
		cobrancaGrupo = null;
		cobrancaAcao = null;

	}

	/**
	 * @return Returns the referenciaCobranca.
	 */
	public String getReferenciaCobranca(){

		return referenciaCobranca;
	}

	/**
	 * @param referenciaCobranca
	 *            The referenciaCobranca to set.
	 */
	public void setReferenciaCobranca(String referenciaCobranca){

		this.referenciaCobranca = referenciaCobranca;
	}

	public String getIdPrograma(){

		return idPrograma;
	}

	public void setIdPrograma(String idPrograma){

		this.idPrograma = idPrograma;
	}

	public String getDescricaoPrograma(){

		return descricaoPrograma;
	}

	public void setDescricaoPrograma(String descricaoPrograma){

		this.descricaoPrograma = descricaoPrograma;
	}

	public String getIdAcao(){

		return idAcao;
	}

	public void setIdAcao(String idAcao){

		this.idAcao = idAcao;
	}

	public String getDescricaoAcao(){

		return descricaoAcao;
	}

	public void setDescricaoAcao(String descricaoAcao){

		this.descricaoAcao = descricaoAcao;
	}

	public String getEmpresa(){

		return empresa;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public String getDataPrevista(){

		return dataPrevista;
	}

	public void setDataPrevista(String dataPrevista){

		this.dataPrevista = dataPrevista;
	}

	public String getDataPrazo(){

		return dataPrazo;
	}

	public void setDataPrazo(String dataPrazo){

		this.dataPrazo = dataPrazo;
	}

	public String getCobrancaAtividade(){

		return cobrancaAtividade;
	}

	public void setCobrancaAtividade(String cobrancaAtividade){

		this.cobrancaAtividade = cobrancaAtividade;
	}

}
