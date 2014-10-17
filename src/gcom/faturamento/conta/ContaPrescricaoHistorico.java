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

package gcom.faturamento.conta;

import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.prescricao.PrescricaoComando;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

@ControleAlteracao()
public class ContaPrescricaoHistorico
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final Short MARCACAO_PRESCRICAO = new Short("1");

	public static final Short DESMARCACAO_PRESCRICAO = new Short("2");

	public static final int ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS = 900204;

	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private Short codigoEvento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private Date dataEvento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private BigDecimal valorConta;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private Imovel imovel;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private ContaGeral contaGeral;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private PrescricaoComando prescricaoComando;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_DESMARCAR_PRESCRICAO_DEBITOS})
	private Usuario usuario;

	public ContaPrescricaoHistorico() {

	}

	public ContaPrescricaoHistorico(Integer id, Short codigoEvento, Date dataEvento, BigDecimal valorConta, Date ultimaAlteracao,
									Imovel imovel, ContaGeral contaGeral, PrescricaoComando prescricaoComando, Usuario usuario) {

		this.id = id;
		this.codigoEvento = codigoEvento;
		this.dataEvento = dataEvento;
		this.valorConta = valorConta;
		this.ultimaAlteracao = ultimaAlteracao;
		this.imovel = imovel;
		this.contaGeral = contaGeral;
		this.prescricaoComando = prescricaoComando;
		this.usuario = usuario;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Short getCodigoEvento(){

		return codigoEvento;
	}

	public void setCodigoEvento(Short codigoEvento){

		this.codigoEvento = codigoEvento;
	}

	public Date getDataEvento(){

		return dataEvento;
	}

	public void setDataEvento(Date dataEvento){

		this.dataEvento = dataEvento;
	}

	public BigDecimal getValorConta(){

		return valorConta;
	}

	public void setValorConta(BigDecimal valorConta){

		this.valorConta = valorConta;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public ContaGeral getContaGeral(){

		return contaGeral;
	}

	public void setContaGeral(ContaGeral contaGeral){

		this.contaGeral = contaGeral;
	}

	public PrescricaoComando getPrescricaoComando(){

		return prescricaoComando;
	}

	public void setPrescricaoComando(PrescricaoComando prescricaoComando){

		this.prescricaoComando = prescricaoComando;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroContaPrescricaoHistorico filtroContaPrescricaoHistorico = new FiltroContaPrescricaoHistorico();

		filtroContaPrescricaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroContaPrescricaoHistorico.CONTA_GERAL);
		filtroContaPrescricaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroContaPrescricaoHistorico.PRESCRICAO_COMANDO);
		filtroContaPrescricaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroContaPrescricaoHistorico.USUARIO);
		filtroContaPrescricaoHistorico.adicionarCaminhoParaCarregamentoEntidade(FiltroContaPrescricaoHistorico.IMOVEL);

		filtroContaPrescricaoHistorico.adicionarParametro(new ParametroSimples(FiltroContaPrescricaoHistorico.ID, this.getId()));
		return filtroContaPrescricaoHistorico;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String getDescricaoEvento(){

		String retorno = "";

		if(this.codigoEvento.equals(Short.valueOf("1"))){

			retorno = "MARCAÇÃO";
		}else if(this.codigoEvento.equals(Short.valueOf("2"))){

			retorno = "DESMARCAÇÃO";
		}

		return retorno;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"imovel.id", "contaGeral.conta.referenciaFormatada"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Imovel", "Referencia da Conta"};
		return labels;
	}
}
