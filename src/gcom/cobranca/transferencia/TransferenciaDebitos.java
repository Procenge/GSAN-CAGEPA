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

package gcom.cobranca.transferencia;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.seguranca.acesso.usuario.Usuario;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Gicevalter Couto
 * @date 26/02/2015
 */
public class TransferenciaDebitos
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Vari�veis para os c�lculos de Parcelamento do [UC0214]
	 */
	public final static int CASAS_DECIMAIS = 2;

	public final static int TIPO_ARREDONDAMENTO = BigDecimal.ROUND_DOWN;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Date dataHoraTransferencia;

	/** nullable persistent field */
	private Integer tipoTransferencia;

	/** nullable persistent field */
	private byte[] conteudoTermoAssuncao;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private RegistroAtendimento registroAtendimento;

	private Usuario usuario;

	/** persistent field */
	private Imovel imovelOrigem;

	/** persistent field */
	private Imovel imovelDestino;

	private Cliente clienteDestino;

	private ArrayList<TransferenciaDebitosItem> transfereciaDebitoItens = new ArrayList<TransferenciaDebitosItem>();

	public ArrayList<TransferenciaDebitosItem> getTransferenciaDebitosItens(){

		return this.transfereciaDebitoItens;
	}

	public void setTransferenciaDebitosItens(ArrayList<TransferenciaDebitosItem> transfereciaDebitoItens){

		this.transfereciaDebitoItens = transfereciaDebitoItens;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getTipoTransferencia(){

		return this.tipoTransferencia;
	}

	public void setTipoTransferencia(Integer tipoTransferencia){

		this.tipoTransferencia = tipoTransferencia;
	}

	public Date getDataHoraTransferencia(){

		return this.dataHoraTransferencia;
	}

	public void setDataHoraTransferencia(Date dataHoraTransferencia){

		this.dataHoraTransferencia = dataHoraTransferencia;
	}

	public void setConteudoTermoAssuncao(byte[] conteudoTermoAssuncao){

		this.conteudoTermoAssuncao = conteudoTermoAssuncao;
	}

	public byte[] getConteudoTermoAssuncao(){

		return conteudoTermoAssuncao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return this.registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public Imovel getImovelOrigem(){

		return this.imovelOrigem;
	}

	public void setImovelOrigem(Imovel imovelOrigem){

		this.imovelOrigem = imovelOrigem;
	}

	public Imovel getImovelDestino(){

		return this.imovelDestino;
	}

	public void setImovelDestino(Imovel imovelDestino){

		this.imovelDestino = imovelDestino;
	}

	public Cliente getClienteDestino(){

		return clienteDestino;
	}

	public void setClienteDestino(Cliente clienteDestino){

		this.clienteDestino = clienteDestino;
	}
}
