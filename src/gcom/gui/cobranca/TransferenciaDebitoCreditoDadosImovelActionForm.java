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

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Esta classe tem por finalidade gerar o formulário que receberá os parâmetros
 * para realização da da transferência de débitos e créditos entre imóveis.
 * 
 * @author Raphael Rossiter
 * @date 06/06/2007
 */
public class TransferenciaDebitoCreditoDadosImovelActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String idRegistroAtendimento;

	private String descricaoEspecificacaoRA;

	private String idImovelOrigem;

	private String inscricaoImovelOrigem;

	private String nomeClienteUsuarioImovelOrigem;

	private String descricaoLigacaoAguaSituacaoImovelOrigem;

	private String descricaoLigacaoEsgotoSituacaoImovelOrigem;

	private String idImovelDestino;

	private String inscricaoImovelDestino;

	private String nomeClienteUsuarioImovelDestino;

	private String descricaoLigacaoAguaSituacaoImovelDestino;

	private String descricaoLigacaoEsgotoSituacaoImovelDestino;

	private String tipoTransferencia;

	private String idRelacaoClienteOrigem;

	private String idClienteDestino;

	private String nomeClienteDestino;

	private String cpfCnpjClienteDestino;

	private String idClienteImovelSelecionado;

	private Integer idClienteOrigemSelecionado;

	private Integer idClienteRelacaoOrigemSelecionado;

	List<Integer> idClienteOrigem = new ArrayList<Integer>();

	List<Integer> idClienteRelacaoOrigem = new ArrayList<Integer>();

	List<String> idsContas = new ArrayList<String>();

	List<String> idsDebitos = new ArrayList<String>();

	List<String> idsCreditos = new ArrayList<String>();

	List<String> idsGuias = new ArrayList<String>();

	String idsContasSelecionadas;

	String idsDebitosSelecionadas;

	String idsCreditosSelecionadas;

	String idsGuiasSelecionadas;

	public String getIdsContasSelecionadas(){

		return idsContasSelecionadas;
	}

	public void setIdsContasSelecionadas(String idsContasSelecionadas){

		this.idsContasSelecionadas = idsContasSelecionadas;
	}

	public String getIdsDebitosSelecionadas(){

		return idsDebitosSelecionadas;
	}

	public void setIdsDebitosSelecionadas(String idsDebitosSelecionadas){

		this.idsDebitosSelecionadas = idsDebitosSelecionadas;
	}

	public String getIdsCreditosSelecionadas(){

		return idsCreditosSelecionadas;
	}

	public void setIdsCreditosSelecionadas(String idsCreditosSelecionadas){

		this.idsCreditosSelecionadas = idsCreditosSelecionadas;
	}

	public String getIdsGuiasSelecionadas(){

		return idsGuiasSelecionadas;
	}

	public void setIdsGuiasSelecionadas(String idsGuiasSelecionadas){

		this.idsGuiasSelecionadas = idsGuiasSelecionadas;
	}

	public List<String> getIdsGuias(){

		return idsGuias;
	}

	public List<String> getIdsCreditos(){

		return idsCreditos;
	}

	public List<String> getIdsDebitos(){

		return idsDebitos;
	}

	public List<String> getIdsContas(){

		return idsContas;
	}

	public List<Integer> getIdClienteRelacaoOrigem(){

		return idClienteRelacaoOrigem;
	}

	public List<Integer> getIdClienteOrigem(){

		return idClienteOrigem;
	}

	public String getIdClienteImovelSelecionado(){

		return idClienteImovelSelecionado;
	}

	public void setIdClienteImovelSelecionado(String idClienteImovelSelecionado){

		this.idClienteImovelSelecionado = idClienteImovelSelecionado;
	}

	public String getCpfCnpjClienteDestino(){

		return cpfCnpjClienteDestino;
	}

	public void setCpfCnpjClienteDestino(String cpfCnpjClienteDestino){

		this.cpfCnpjClienteDestino = cpfCnpjClienteDestino;
	}

	public String getNomeClienteDestino(){

		return nomeClienteDestino;
	}

	public void setNomeClienteDestino(String nomeClienteDestino){

		this.nomeClienteDestino = nomeClienteDestino;
	}

	public String getTipoTransferencia(){

		return tipoTransferencia;
	}

	public void setTipoTransferencia(String tipoTransferencia){

		this.tipoTransferencia = tipoTransferencia;
	}

	public String getIdClienteDestino(){

		return idClienteDestino;
	}

	public void setIdClienteDestino(String idClienteDestino){

		this.idClienteDestino = idClienteDestino;
	}

	public String getIdRelacaoClienteOrigem(){

		return idRelacaoClienteOrigem;
	}

	public void setIdRelacaoClienteOrigem(String idRelacaoClienteOrigem){

		this.idRelacaoClienteOrigem = idRelacaoClienteOrigem;
	}

	public String getDescricaoEspecificacaoRA(){

		return descricaoEspecificacaoRA;
	}

	public void setDescricaoEspecificacaoRA(String idClienteOrigem){

		this.descricaoEspecificacaoRA = idClienteOrigem;
	}

	public String getNomeClienteUsuarioImovelDestino(){

		return nomeClienteUsuarioImovelDestino;
	}

	public void setNomeClienteUsuarioImovelDestino(String nomeClienteUsuarioImovelDestino){

		this.nomeClienteUsuarioImovelDestino = nomeClienteUsuarioImovelDestino;
	}

	public String getNomeClienteUsuarioImovelOrigem(){

		return nomeClienteUsuarioImovelOrigem;
	}

	public void setNomeClienteUsuarioImovelOrigem(String nomeClienteUsuarioImovelOrigem){

		this.nomeClienteUsuarioImovelOrigem = nomeClienteUsuarioImovelOrigem;
	}

	public String getIdImovelDestino(){

		return idImovelDestino;
	}

	public void setIdImovelDestino(String idImovelDestino){

		this.idImovelDestino = idImovelDestino;
	}

	public String getIdImovelOrigem(){

		return idImovelOrigem;
	}

	public void setIdImovelOrigem(String idImovelOrigem){

		this.idImovelOrigem = idImovelOrigem;
	}

	public String getDescricaoLigacaoAguaSituacaoImovelDestino(){

		return descricaoLigacaoAguaSituacaoImovelDestino;
	}

	public void setDescricaoLigacaoAguaSituacaoImovelDestino(String descricaoLigacaoAguaSituacaoImovelDestino){

		this.descricaoLigacaoAguaSituacaoImovelDestino = descricaoLigacaoAguaSituacaoImovelDestino;
	}

	public String getDescricaoLigacaoAguaSituacaoImovelOrigem(){

		return descricaoLigacaoAguaSituacaoImovelOrigem;
	}

	public void setDescricaoLigacaoAguaSituacaoImovelOrigem(String descricaoLigacaoAguaSituacaoImovelOrigem){

		this.descricaoLigacaoAguaSituacaoImovelOrigem = descricaoLigacaoAguaSituacaoImovelOrigem;
	}

	public String getDescricaoLigacaoEsgotoSituacaoImovelDestino(){

		return descricaoLigacaoEsgotoSituacaoImovelDestino;
	}

	public void setDescricaoLigacaoEsgotoSituacaoImovelDestino(String descricaoLigacaoEsgotoSituacaoImovelDestino){

		this.descricaoLigacaoEsgotoSituacaoImovelDestino = descricaoLigacaoEsgotoSituacaoImovelDestino;
	}

	public String getDescricaoLigacaoEsgotoSituacaoImovelOrigem(){

		return descricaoLigacaoEsgotoSituacaoImovelOrigem;
	}

	public void setDescricaoLigacaoEsgotoSituacaoImovelOrigem(String descricaoLigacaoEsgotoSituacaoImovelOrigem){

		this.descricaoLigacaoEsgotoSituacaoImovelOrigem = descricaoLigacaoEsgotoSituacaoImovelOrigem;
	}

	public String getIdRegistroAtendimento(){

		return idRegistroAtendimento;
	}

	public void setIdRegistroAtendimento(String idRegistroAtendimento){

		this.idRegistroAtendimento = idRegistroAtendimento;
	}

	public String getInscricaoImovelDestino(){

		return inscricaoImovelDestino;
	}

	public void setInscricaoImovelDestino(String inscricaoImovelDestino){

		this.inscricaoImovelDestino = inscricaoImovelDestino;
	}

	public String getInscricaoImovelOrigem(){

		return inscricaoImovelOrigem;
	}

	public void setInscricaoImovelOrigem(String inscricaoImovelOrigem){

		this.inscricaoImovelOrigem = inscricaoImovelOrigem;
	}
	
	public Integer getIdClienteOrigemSelecionado(){

		return idClienteOrigemSelecionado;
	}

	public void setIdClienteOrigemSelecionado(Integer idClienteOrigemSelecionado){

		this.idClienteOrigemSelecionado = idClienteOrigemSelecionado;
	}

	public Integer getIdClienteRelacaoOrigemSelecionado(){

		return idClienteRelacaoOrigemSelecionado;
	}

	public void setIdClienteRelacaoOrigemSelecionado(Integer idClienteRelacaoOrigemSelecionado){

		this.idClienteRelacaoOrigemSelecionado = idClienteRelacaoOrigemSelecionado;

	}	

}
