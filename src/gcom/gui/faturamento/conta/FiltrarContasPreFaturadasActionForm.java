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

/**
 * [UC3037] Filtrar Contas Pré-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 06/02/2012
 *          Exibir Contas Pré-Faturadas.
 */

package gcom.gui.faturamento.conta;

import org.apache.struts.action.ActionForm;

public class FiltrarContasPreFaturadasActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String localidadeOrigemID;

	private String nomeLocalidadeOrigem;

	private String setorComercialOrigemCD;

	private String setorComercialOrigemID;

	private String nomeSetorComercialOrigem;

	private String localidadeDestinoID;

	private String nomeLocalidadeDestino;

	private String setorComercialDestinoCD;

	private String setorComercialDestinoID;

	private String nomeSetorComercialDestino;

	private String rotaOrigemID;

	private String rotaDestinoID;

	private String descricaoRotaOrigem;

	private String descricaoRotaDestino;

	private String imovelID;

	private String matriculaImovel;

	private String faturamentoGrupoID;

	private String dataReferenciaContaInicial;

	private String dataReferenciaContaFinal;

	private String dataVencimentoContaInicial;

	private String dataVencimentoContaFinal;

	private String inscricaoTipo;

	private String indicadorAtualizar;

	public String getLocalidadeOrigemID(){

		return localidadeOrigemID;
	}

	public void setLocalidadeOrigemID(String localidadeOrigemID){

		this.localidadeOrigemID = localidadeOrigemID;
	}

	public String getNomeLocalidadeOrigem(){

		return nomeLocalidadeOrigem;
	}

	public void setNomeLocalidadeOrigem(String nomeLocalidadeOrigem){

		this.nomeLocalidadeOrigem = nomeLocalidadeOrigem;
	}

	public String getSetorComercialOrigemCD(){

		return setorComercialOrigemCD;
	}

	public void setSetorComercialOrigemCD(String setorComercialOrigemCD){

		this.setorComercialOrigemCD = setorComercialOrigemCD;
	}

	public String getSetorComercialOrigemID(){

		return setorComercialOrigemID;
	}

	public void setSetorComercialOrigemID(String setorComercialOrigemID){

		this.setorComercialOrigemID = setorComercialOrigemID;
	}

	public String getNomeSetorComercialOrigem(){

		return nomeSetorComercialOrigem;
	}

	public void setNomeSetorComercialOrigem(String nomeSetorComercialOrigem){

		this.nomeSetorComercialOrigem = nomeSetorComercialOrigem;
	}

	public String getLocalidadeDestinoID(){

		return localidadeDestinoID;
	}

	public void setLocalidadeDestinoID(String localidadeDestinoID){

		this.localidadeDestinoID = localidadeDestinoID;
	}

	public String getNomeLocalidadeDestino(){

		return nomeLocalidadeDestino;
	}

	public void setNomeLocalidadeDestino(String nomeLocalidadeDestino){

		this.nomeLocalidadeDestino = nomeLocalidadeDestino;
	}

	public String getSetorComercialDestinoCD(){

		return setorComercialDestinoCD;
	}

	public void setSetorComercialDestinoCD(String setorComercialDestinoCD){

		this.setorComercialDestinoCD = setorComercialDestinoCD;
	}

	public String getSetorComercialDestinoID(){

		return setorComercialDestinoID;
	}

	public void setSetorComercialDestinoID(String setorComercialDestinoID){

		this.setorComercialDestinoID = setorComercialDestinoID;
	}

	public String getNomeSetorComercialDestino(){

		return nomeSetorComercialDestino;
	}

	public void setNomeSetorComercialDestino(String nomeSetorComercialDestino){

		this.nomeSetorComercialDestino = nomeSetorComercialDestino;
	}

	public String getRotaOrigemID(){

		return rotaOrigemID;
	}

	public void setRotaOrigemID(String rotaOrigemID){

		this.rotaOrigemID = rotaOrigemID;
	}

	public String getRotaDestinoID(){

		return rotaDestinoID;
	}

	public void setRotaDestinoID(String rotaDestinoID){

		this.rotaDestinoID = rotaDestinoID;
	}

	public String getImovelID(){

		return imovelID;
	}

	public void setImovelID(String imovelID){

		this.imovelID = imovelID;
	}

	public String getFaturamentoGrupoID(){

		return faturamentoGrupoID;
	}

	public void setFaturamentoGrupoID(String faturamentoGrupoID){

		this.faturamentoGrupoID = faturamentoGrupoID;
	}

	public String getDataReferenciaContaInicial(){

		return dataReferenciaContaInicial;
	}

	public void setDataReferenciaFaturamentoContaInicial(String dataReferenciaContaInicial){

		this.dataReferenciaContaInicial = dataReferenciaContaInicial;
	}

	public String getDataReferenciaContaFinal(){

		return dataReferenciaContaFinal;
	}

	public void setDataReferenciaContaFinal(String dataReferenciaContaFinal){

		this.dataReferenciaContaFinal = dataReferenciaContaFinal;
	}

	public String getDataVencimentoContaInicial(){

		return dataVencimentoContaInicial;
	}

	public void setDataVencimentoContaInicial(String dataVencimentoContaInicial){

		this.dataVencimentoContaInicial = dataVencimentoContaInicial;
	}

	public String getDataVencimentoContaFinal(){

		return dataVencimentoContaFinal;
	}

	public void setDataVencimentoContaFinal(String dataVencimentoContaFinal){

		this.dataVencimentoContaFinal = dataVencimentoContaFinal;
	}

	public String getInscricaoTipo(){

		return inscricaoTipo;
	}

	public void setInscricaoTipo(String inscricaoTipo){

		this.inscricaoTipo = inscricaoTipo;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getIndicadorAtualizar(){

		return indicadorAtualizar;
	}

	public void setIndicadorAtualizar(String indicadorAtualizar){

		this.indicadorAtualizar = indicadorAtualizar;
	}

	public String getDescricaoRotaOrigem(){

		return descricaoRotaOrigem;
	}

	public void setDescricaoRotaOrigem(String descricaoRotaOrigem){

		this.descricaoRotaOrigem = descricaoRotaOrigem;
	}

	public String getDescricaoRotaDestino(){

		return descricaoRotaDestino;
	}

	public void setDescricaoRotaDestino(String descricaoRotaDestino){

		this.descricaoRotaDestino = descricaoRotaDestino;
	}

	public void setDataReferenciaContaInicial(String dataReferenciaContaInicial){

		this.dataReferenciaContaInicial = dataReferenciaContaInicial;
	}

}
