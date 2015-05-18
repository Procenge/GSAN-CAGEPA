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

import gcom.cobranca.bean.IntervaloReferenciaHelper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.apache.struts.action.ActionForm;

/**
 * @author
 * @created
 */

public class ConverterContasEmGuiaPorResponsavelActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String cliente;

	private String codigoCliente;

	private String nomeCliente;

	private String imovel;

	private String codigoImovel;

	private String inscricaoImovel;

	private String referenciaInicial;

	private String referenciaFinal;

	private String jurosParcelamento;

	private String idIntervaloReferenciaHelper;

	private String valorTotalContas;

	private String valorTotalListaContas;

	private String valorTotalGuiaPagamento;

	private Collection<IntervaloReferenciaHelper> colecaoIntervaloReferenciaHelper = new ArrayList<IntervaloReferenciaHelper>();


	public String getCodigoCliente(){

		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente){

		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getCliente(){

		return cliente;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public String getImovel(){

		return imovel;
	}

	public void setImovel(String imovel){

		this.imovel = imovel;
	}

	public String getCodigoImovel(){

		return codigoImovel;
	}

	public void setCodigoImovel(String codigoImovel){

		this.codigoImovel = codigoImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getReferenciaInicial(){

		return referenciaInicial;
	}

	public void setReferenciaInicial(String referenciaInicial){

		this.referenciaInicial = referenciaInicial;
	}

	public String getReferenciaFinal(){

		return referenciaFinal;
	}

	public void setReferenciaFinal(String referenciaFinal){

		this.referenciaFinal = referenciaFinal;
	}

	public String getJurosParcelamento(){

		return jurosParcelamento;
	}

	public void setJurosParcelamento(String jurosParcelamento){

		this.jurosParcelamento = jurosParcelamento;
	}

	public Collection<IntervaloReferenciaHelper> getColecaoIntervaloReferenciaHelper(){

		return colecaoIntervaloReferenciaHelper;
	}

	public void setColecaoIntervaloReferenciaHelper(Collection<IntervaloReferenciaHelper> colecaoIntervaloReferenciaHelper){

		this.colecaoIntervaloReferenciaHelper = colecaoIntervaloReferenciaHelper;
	}

	public String getIdIntervaloReferenciaHelper(){

		return idIntervaloReferenciaHelper;
	}

	public void setIdIntervaloReferenciaHelper(String idIntervaloReferenciaHelper){

		this.idIntervaloReferenciaHelper = idIntervaloReferenciaHelper;
	}

	public String getValorTotalListaContas(){

		return valorTotalListaContas;
	}

	public void setValorTotalListaContas(String valorTotalListaContas){

		this.valorTotalListaContas = valorTotalListaContas;
	}

	public String getValorTotalContas(){

		return valorTotalContas;
	}

	public void setValorTotalContas(String valorTotalContas){

		this.valorTotalContas = valorTotalContas;
	}

	public String getValorTotalGuiaPagamento(){

		return valorTotalGuiaPagamento;
	}

	public void setValorTotalGuiaPagamento(String valorTotalGuiaPagamento){

		this.valorTotalGuiaPagamento = valorTotalGuiaPagamento;
	}

	public void addIntervaloReferencia(){

		IntervaloReferenciaHelper intervaloReferenciaHelper = new IntervaloReferenciaHelper();

		Integer id = this.colecaoIntervaloReferenciaHelper.size() + 1;

		intervaloReferenciaHelper.setReferenciaInicial(this.getReferenciaInicial());
		intervaloReferenciaHelper.setReferenciaFinal(this.getReferenciaFinal());
		intervaloReferenciaHelper.setId(id);
		intervaloReferenciaHelper.setValorTotalContas(this.getValorTotalContas());

		colecaoIntervaloReferenciaHelper.add(intervaloReferenciaHelper);
	}

	public void removeIntervaloReferencia(){

		for(Iterator iter = getColecaoIntervaloReferenciaHelper().iterator(); iter.hasNext();){
			IntervaloReferenciaHelper helper = (IntervaloReferenciaHelper) iter.next();
			String string = helper.getId().toString();
			if(string.equals(getIdIntervaloReferenciaHelper())){
				iter.remove();
			}
		}
	}

}
