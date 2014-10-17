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

package gcom.micromedicao;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaGrupo;
import gcom.faturamento.FaturamentoAtivCronRota;
import gcom.faturamento.FaturamentoGrupo;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.leitura.LeituraTipo;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class Rota
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	private Short codigo;

	/** nullable persistent field */
	private Short indicadorAjusteConsumo;

	/** nullable persistent field */
	private Date dataAjusteLeitura;

	/** nullable persistent field */
	private Short indicadorFiscalizarCortado;

	/** nullable persistent field */
	private Short indicadorFiscalizarSuprimido;

	/** nullable persistent field */
	private Short indicadorGerarFalsaFaixa;

	/** nullable persistent field */
	private BigDecimal percentualGeracaoFaixaFalsa;

	/** nullable persistent field */
	private Short indicadorGerarFiscalizacao;

	/** nullable persistent field */
	private BigDecimal percentualGeracaoFiscalizacao;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private Empresa empresa;

	/** persistent field */
	private FaturamentoGrupo faturamentoGrupo;

	/** persistent field */
	private LeituraTipo leituraTipo;

	/** persistent field */
	private SetorComercial setorComercial;

	/** persistent field */
	private CobrancaGrupo cobrancaGrupo;

	/** persistent field */
	private Empresa empresaCobranca;

	/** persistent field */
	private Leiturista leiturista;

	private Set<Quadra> quadras;

	private Set<FaturamentoAtivCronRota> faturamentoAtivCronRotas;

	/** persistent field */
	// private CobrancaCriterio cobrancaCriterio;

	/**
	 * Description of the Field
	 */
	public final static int INDICADOR_AJUSTE_MENSAL = 1;

	/**
	 * Description of the Field
	 */
	public final static int INDICADOR_SUPRIMIDO_ATIVO = 1;

	/**
	 * Description of the Field
	 */
	public final static int INDICADOR_CORTADO_ATIVO = 1;

	/**
	 * Description of the Field
	 */
	public final static int LEITURA_TIPO_CONVENCIONAL = 1;

	/**
	 * Description of the Field
	 */
	public final static int LEITURA_TIPO_RELACAO = 2;

	public final static Short INDICADOR_GERAR_FISCALIZACAO = Short.valueOf("1");

	public final static Short INDICADOR_NAO_GERAR_FAIXA_FALSA = Short.valueOf("2");

	/** full constructor */
	public Rota(Short indicadorAjusteConsumo, Date dataAjusteLeitura, Short indicadorFiscalizarCortado, Short indicadorFiscalizarSuprimido,
				Short indicadorGerarFalsaFaixa, BigDecimal percentualGeracaoFaixaFalsa, Short indicadorGerarFiscalizacao,
				BigDecimal percentualGeracaoFiscalizacao, Short indicadorUso, Date ultimaAlteracao, Empresa empresa,
				FaturamentoGrupo faturamentoGrupo, LeituraTipo leituraTipo, SetorComercial setorComercial, CobrancaGrupo cobrancaGrupo) {

		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
		this.dataAjusteLeitura = dataAjusteLeitura;
		this.indicadorFiscalizarCortado = indicadorFiscalizarCortado;
		this.indicadorFiscalizarSuprimido = indicadorFiscalizarSuprimido;
		this.indicadorGerarFalsaFaixa = indicadorGerarFalsaFaixa;
		this.percentualGeracaoFaixaFalsa = percentualGeracaoFaixaFalsa;
		this.indicadorGerarFiscalizacao = indicadorGerarFiscalizacao;
		this.percentualGeracaoFiscalizacao = percentualGeracaoFiscalizacao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.empresa = empresa;
		this.faturamentoGrupo = faturamentoGrupo;
		this.leituraTipo = leituraTipo;
		this.setorComercial = setorComercial;
		this.cobrancaGrupo = cobrancaGrupo;
	}

	/** full constructor */
	public Rota(Short codigo, Short indicadorAjusteConsumo, Date dataAjusteLeitura, Short indicadorFiscalizarCortado,
				Short indicadorFiscalizarSuprimido, Short indicadorGerarFalsaFaixa, BigDecimal percentualGeracaoFaixaFalsa,
				Short indicadorGerarFiscalizacao, BigDecimal percentualGeracaoFiscalizacao, Short indicadorUso, Date ultimaAlteracao,
				Empresa empresa, FaturamentoGrupo faturamentoGrupo, LeituraTipo leituraTipo, SetorComercial setorComercial,
				CobrancaGrupo cobrancaGrupo) {

		this.codigo = codigo;
		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
		this.dataAjusteLeitura = dataAjusteLeitura;
		this.indicadorFiscalizarCortado = indicadorFiscalizarCortado;
		this.indicadorFiscalizarSuprimido = indicadorFiscalizarSuprimido;
		this.indicadorGerarFalsaFaixa = indicadorGerarFalsaFaixa;
		this.percentualGeracaoFaixaFalsa = percentualGeracaoFaixaFalsa;
		this.indicadorGerarFiscalizacao = indicadorGerarFiscalizacao;
		this.percentualGeracaoFiscalizacao = percentualGeracaoFiscalizacao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.empresa = empresa;
		this.faturamentoGrupo = faturamentoGrupo;
		this.leituraTipo = leituraTipo;
		this.setorComercial = setorComercial;
		this.cobrancaGrupo = cobrancaGrupo;
	}

	/** default constructor */
	public Rota() {

	}

	/** minimal constructor */
	public Rota(Empresa empresa, FaturamentoGrupo faturamentoGrupo, LeituraTipo leituraTipo, SetorComercial setorComercial,
				CobrancaGrupo cobrancaGrupo) {

		this.empresa = empresa;
		this.faturamentoGrupo = faturamentoGrupo;
		this.leituraTipo = leituraTipo;
		this.setorComercial = setorComercial;
		this.cobrancaGrupo = cobrancaGrupo;
	}

	public Rota(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Short getIndicadorAjusteConsumo(){

		return this.indicadorAjusteConsumo;
	}

	public void setIndicadorAjusteConsumo(Short indicadorAjusteConsumo){

		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
	}

	public Date getDataAjusteLeitura(){

		return this.dataAjusteLeitura;
	}

	public void setDataAjusteLeitura(Date dataAjusteLeitura){

		this.dataAjusteLeitura = dataAjusteLeitura;
	}

	public Short getIndicadorFiscalizarCortado(){

		return this.indicadorFiscalizarCortado;
	}

	public void setIndicadorFiscalizarCortado(Short indicadorFiscalizarCortado){

		this.indicadorFiscalizarCortado = indicadorFiscalizarCortado;
	}

	public Short getIndicadorFiscalizarSuprimido(){

		return this.indicadorFiscalizarSuprimido;
	}

	public void setIndicadorFiscalizarSuprimido(Short indicadorFiscalizarSuprimido){

		this.indicadorFiscalizarSuprimido = indicadorFiscalizarSuprimido;
	}

	public Short getIndicadorGerarFalsaFaixa(){

		return this.indicadorGerarFalsaFaixa;
	}

	public void setIndicadorGerarFalsaFaixa(Short indicadorGerarFalsaFaixa){

		this.indicadorGerarFalsaFaixa = indicadorGerarFalsaFaixa;
	}

	public BigDecimal getPercentualGeracaoFaixaFalsa(){

		return this.percentualGeracaoFaixaFalsa;
	}

	public void setPercentualGeracaoFaixaFalsa(BigDecimal percentualGeracaoFaixaFalsa){

		this.percentualGeracaoFaixaFalsa = percentualGeracaoFaixaFalsa;
	}

	public Short getIndicadorGerarFiscalizacao(){

		return this.indicadorGerarFiscalizacao;
	}

	public void setIndicadorGerarFiscalizacao(Short indicadorGerarFiscalizacao){

		this.indicadorGerarFiscalizacao = indicadorGerarFiscalizacao;
	}

	public BigDecimal getPercentualGeracaoFiscalizacao(){

		return this.percentualGeracaoFiscalizacao;
	}

	public void setPercentualGeracaoFiscalizacao(BigDecimal percentualGeracaoFiscalizacao){

		this.percentualGeracaoFiscalizacao = percentualGeracaoFiscalizacao;
	}

	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Empresa getEmpresa(){

		return this.empresa;
	}

	public void setEmpresa(Empresa empresa){

		this.empresa = empresa;
	}

	public FaturamentoGrupo getFaturamentoGrupo(){

		return this.faturamentoGrupo;
	}

	public void setFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public LeituraTipo getLeituraTipo(){

		return this.leituraTipo;
	}

	public void setLeituraTipo(LeituraTipo leituraTipo){

		this.leituraTipo = leituraTipo;
	}

	public SetorComercial getSetorComercial(){

		return this.setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public CobrancaGrupo getCobrancaGrupo(){

		return this.cobrancaGrupo;
	}

	public void setCobrancaGrupo(CobrancaGrupo cobrancaGrupo){

		this.cobrancaGrupo = cobrancaGrupo;
	}

	/*
	 * public CobrancaCriterio getCobrancaCriterio() {
	 * return this.cobrancaCriterio;
	 * }
	 * public void setCobrancaCriterio(CobrancaCriterio cobrancaCriterio) {
	 * this.cobrancaCriterio = cobrancaCriterio;
	 * }
	 */
	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Short getCodigo(){

		return codigo;
	}

	public void setCodigo(Short codigo){

		this.codigo = codigo;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroRota filtroRota = new FiltroRota();

		filtroRota.adicionarCaminhoParaCarregamentoEntidade("empresa");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("faturamentoGrupo");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("leituraTipo");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroRota.adicionarCaminhoParaCarregamentoEntidade("cobrancaGrupo");
		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, this.getId()));
		return filtroRota;
	}

	public Empresa getEmpresaCobranca(){

		return empresaCobranca;
	}

	public void setEmpresaCobranca(Empresa empresaCobranca){

		this.empresaCobranca = empresaCobranca;
	}

	public Leiturista getLeiturista(){

		return leiturista;
	}

	public void setLeiturista(Leiturista leiturista){

		this.leiturista = leiturista;
	}

	public Set<Quadra> getQuadras(){

		return quadras;
	}

	public void setQuadras(Set<Quadra> quadras){

		this.quadras = quadras;
	}

	public Set<FaturamentoAtivCronRota> getFaturamentoAtivCronRotas(){

		return faturamentoAtivCronRotas;
	}

	public void setFaturamentoAtivCronRotas(Set<FaturamentoAtivCronRota> faturamentoAtivCronRotas){

		this.faturamentoAtivCronRotas = faturamentoAtivCronRotas;
	}

	public String getDescricaoParaRegistroTransacao(){

		return this.getDescricao();
	}

	/**
	 * [UC0779] Pesquisar Rota
	 * Este método permite obter a descrição da rota no formato do seguinte exemplo: Localid.:
	 * 001;Setor Comerc.: 003;Cód. Rota: 003
	 * 
	 * @author Anderson Italo
	 * @date 31/01/2012
	 */
	public String getDescricao(){

		String descricao = "";

		descricao = "Local: " + Util.adicionarZerosEsquedaNumero(3, this.setorComercial.getLocalidade().getId().toString()) + "; ";
		descricao += "Setor: " + Util.adicionarZerosEsquedaNumero(3, String.valueOf(this.setorComercial.getCodigo())) + "; ";
		descricao += "Rota: " + Util.adicionarZerosEsquedaNumero(3, this.codigo.toString());

		return descricao;

	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		Rota other = (Rota) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}
}
