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
 * Vitor Hora
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

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaAcao;
import gcom.cadastro.empresa.Empresa;
import gcom.cobranca.DocumentoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ligacaoagua.CorteTipo;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Leonardo Maranhão */
public class CobrancaSucesso
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer anoMesReferencia;

	private Date dataVencimentoConta;

	private Integer diasVencidos;

	private Integer quantidadeContas;

	private BigDecimal valorContas;

	private Integer quantidadeParcelas;

	private BigDecimal valorParcelas;

	private Date ultimaAlteracao;

	private CobrancaAcaoAtividadeComando comandoAcaoEventual;

	private CobrancaAcaoAtividadeCronograma comandoAcaoCronograma;

	private CobrancaAcao cobrancaAcao;

	private Empresa empresa;

	private DocumentoTipo documentoTipo;

	private ServicoTipo servicoTipo;

	private SupressaoTipo supressaoTipo;

	private CorteTipo corteTipo;

	// /** full constructor */
	// public CobrancaSucesso(String descricao, Short indicadorUso, Date ultimaAlteracao) {
	// this.descricao = descricao;
	// this.indicadorUso = indicadorUso;
	// this.ultimaAlteracao = ultimaAlteracao;
	// }

	/** default constructor */
	public CobrancaSucesso() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Date getDataVencimentoConta(){

		return dataVencimentoConta;
	}

	public void setDataVencimentoConta(Date dataVencimentoConta){

		this.dataVencimentoConta = dataVencimentoConta;
	}

	public Integer getDiasVencidos(){

		return diasVencidos;
	}

	public void setDiasVencidos(Integer diasVencidos){

		this.diasVencidos = diasVencidos;
	}

	public Integer getQuantidadeContas(){

		return quantidadeContas;
	}

	public void setQuantidadeContas(Integer quantidadeContas){

		this.quantidadeContas = quantidadeContas;
	}

	public BigDecimal getValorContas(){

		return valorContas;
	}

	public void setValorContas(BigDecimal valorContas){

		this.valorContas = valorContas;
	}

	public Integer getQuantidadeParcelas(){

		return quantidadeParcelas;
	}

	public void setQuantidadeParcelas(Integer quantidadeParcelas){

		this.quantidadeParcelas = quantidadeParcelas;
	}

	public BigDecimal getValorParcelas(){

		return valorParcelas;
	}

	public void setValorParcelas(BigDecimal valorParcelas){

		this.valorParcelas = valorParcelas;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public CobrancaAcaoAtividadeCronograma getComandoAcaoCronograma(){

		return comandoAcaoCronograma;
	}

	public void setComandoAcaoCronograma(CobrancaAcaoAtividadeCronograma comandoAcaoCronograma){

		this.comandoAcaoCronograma = comandoAcaoCronograma;
	}

	public CobrancaAcaoAtividadeComando getComandoAcaoEventual(){

		return comandoAcaoEventual;
	}

	public void setComandoAcaoEventual(CobrancaAcaoAtividadeComando comandoAcaoEventual){

		this.comandoAcaoEventual = comandoAcaoEventual;
	}

	public CobrancaAcao getCobrancaAcao(){

		return cobrancaAcao;
	}

	public void setCobrancaAcao(CobrancaAcao cobrancaAcao){

		this.cobrancaAcao = cobrancaAcao;
	}

	public Empresa getEmpresa(){

		return empresa;
	}

	public void setEmpresa(Empresa empresa){

		this.empresa = empresa;
	}

	public DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public ServicoTipo getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public SupressaoTipo getSupressaoTipo(){

		return supressaoTipo;
	}

	public void setSupressaoTipo(SupressaoTipo supressaoTipo){

		this.supressaoTipo = supressaoTipo;
	}

	public CorteTipo getCorteTipo(){

		return corteTipo;
	}

	public void setCorteTipo(CorteTipo corteTipo){

		this.corteTipo = corteTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public Filtro retornaFiltro(){

		// TODO implentar filtro cobranca sucesso
		return null;
	}

}
