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

package gcom.micromedicao.hidrometro;

import gcom.cadastro.empresa.Empresa;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class Hidrometro
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_HIDROMETRO_INSERIR = 21;

	public static final int ATRIBUTOS_HIDROMETRO_ATUALIZAR = 245;

	public static final int ATRIBUTOS_HIDROMETRO_REMOVER = 244;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private String numero;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Date dataAquisicao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Short anoFabricacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Short indicadorMacromedidor;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Date dataUltimaRevisao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Date dataBaixa;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Integer numeroLeituraAcumulada;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Short numeroDigitosLeitura;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Integer numeroNotaFiscal;
	
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private String loteEntrega;

	private Empresa empresaUltimaAfericao;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_TIPO, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroTipo hidrometroTipo;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_SITUACAO, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroSituacao hidrometroSituacao;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_MARCA, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroMarca hidrometroMarca;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_CAPACIDADE, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroCapacidade hidrometroCapacidade;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_MOTIVO_BAIXA, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroMotivoBaixa hidrometroMotivoBaixa;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_LOCAL_ARMAZENAGEM, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem hidrometroLocalArmazenagem;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_CLASSE_METROLOGICA, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroClasseMetrologica hidrometroClasseMetrologica;

	@ControleAlteracao(value = FiltroHidrometro.HIDROMETRO_DIAMETRO, funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private gcom.micromedicao.hidrometro.HidrometroDiametro hidrometroDiametro;

	private HidrometroTipoTurbina hidrometroTipoTurbina;

	private Integer codigoFormatoNumeracao;

	private Set hidrometroMovimentados;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private Short indicadorHidrometroComposto;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_HIDROMETRO_INSERIR, ATRIBUTOS_HIDROMETRO_ATUALIZAR, ATRIBUTOS_HIDROMETRO_REMOVER})
	private BigDecimal fatorConversao;

	public final static Integer INDICADOR_COMERCIAL = Integer.valueOf("2");

	public final static Integer INDICADOR_OPERACIONAL = Integer.valueOf("1");

	public final static Integer SITUACAO_INSTALADO = Integer.valueOf("1");

	// Formato da Numeração do Hidrômetro
	public final static Integer FORMATO_NUMERACAO_4_X_6 = 1;

	public final static Integer FORMATO_NUMERACAO_5_X_5 = 2;

	public final static Integer FORMATO_NUMERACAO_LIVRE = 3;

	/** full constructor */
	public Hidrometro(String numero, Date dataAquisicao, Short anoFabricacao, Short indicadorMacromedidor, Date dataUltimaRevisao,
						Date dataBaixa, Integer numeroLeituraAcumulada, Short numeroDigitosLeitura, Date ultimaAlteracao,
						gcom.micromedicao.hidrometro.HidrometroTipo hidrometroTipo,
						gcom.micromedicao.hidrometro.HidrometroSituacao hidrometroSituacao,
						gcom.micromedicao.hidrometro.HidrometroMarca hidrometroMarca,
						gcom.micromedicao.hidrometro.HidrometroCapacidade hidrometroCapacidade,
						gcom.micromedicao.hidrometro.HidrometroMotivoBaixa hidrometroMotivoBaixa,
						gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem hidrometroLocalArmazenagem,
						gcom.micromedicao.hidrometro.HidrometroClasseMetrologica hidrometroClasseMetrologica,
						gcom.micromedicao.hidrometro.HidrometroDiametro hidrometroDiametro, Set hidrometroMovimentados) {

		this.numero = numero;
		this.dataAquisicao = dataAquisicao;
		this.anoFabricacao = anoFabricacao;
		this.indicadorMacromedidor = indicadorMacromedidor;
		this.dataUltimaRevisao = dataUltimaRevisao;
		this.dataBaixa = dataBaixa;
		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
		this.numeroDigitosLeitura = numeroDigitosLeitura;
		this.ultimaAlteracao = ultimaAlteracao;
		this.hidrometroTipo = hidrometroTipo;
		this.hidrometroSituacao = hidrometroSituacao;
		this.hidrometroMarca = hidrometroMarca;
		this.hidrometroCapacidade = hidrometroCapacidade;
		this.hidrometroMotivoBaixa = hidrometroMotivoBaixa;
		this.hidrometroLocalArmazenagem = hidrometroLocalArmazenagem;
		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
		this.hidrometroDiametro = hidrometroDiametro;
		this.hidrometroMovimentados = hidrometroMovimentados;
	}

	/** default constructor */
	public Hidrometro() {

	}

	/** minimal constructor */
	public Hidrometro(String numero, Date dataAquisicao, Short anoFabricacao, Integer numeroLeituraAcumulada,
						gcom.micromedicao.hidrometro.HidrometroTipo hidrometroTipo,
						gcom.micromedicao.hidrometro.HidrometroSituacao hidrometroSituacao,
						gcom.micromedicao.hidrometro.HidrometroMarca hidrometroMarca,
						gcom.micromedicao.hidrometro.HidrometroCapacidade hidrometroCapacidade,
						gcom.micromedicao.hidrometro.HidrometroMotivoBaixa hidrometroMotivoBaixa,
						gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem hidrometroLocalArmazenagem,
						gcom.micromedicao.hidrometro.HidrometroClasseMetrologica hidrometroClasseMetrologica,
						gcom.micromedicao.hidrometro.HidrometroDiametro hidrometroDiametro, Set hidrometroMovimentados) {

		this.numero = numero;
		this.dataAquisicao = dataAquisicao;
		this.anoFabricacao = anoFabricacao;
		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
		this.hidrometroTipo = hidrometroTipo;
		this.hidrometroSituacao = hidrometroSituacao;
		this.hidrometroMarca = hidrometroMarca;
		this.hidrometroCapacidade = hidrometroCapacidade;
		this.hidrometroMotivoBaixa = hidrometroMotivoBaixa;
		this.hidrometroLocalArmazenagem = hidrometroLocalArmazenagem;
		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
		this.hidrometroDiametro = hidrometroDiametro;
		this.hidrometroMovimentados = hidrometroMovimentados;
	}



	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNumero(){

		return this.numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	public Date getDataAquisicao(){

		return this.dataAquisicao;
	}

	public void setDataAquisicao(Date dataAquisicao){

		this.dataAquisicao = dataAquisicao;
	}

	public Short getAnoFabricacao(){

		return this.anoFabricacao;
	}

	public void setAnoFabricacao(Short anoFabricacao){

		this.anoFabricacao = anoFabricacao;
	}

	public Short getIndicadorMacromedidor(){

		return this.indicadorMacromedidor;
	}

	public void setIndicadorMacromedidor(Short indicadorMacromedidor){

		this.indicadorMacromedidor = indicadorMacromedidor;
	}

	public Date getDataUltimaRevisao(){

		return this.dataUltimaRevisao;
	}

	public void setDataUltimaRevisao(Date dataUltimaRevisao){

		this.dataUltimaRevisao = dataUltimaRevisao;
	}

	public Date getDataBaixa(){

		return this.dataBaixa;
	}

	public void setDataBaixa(Date dataBaixa){

		this.dataBaixa = dataBaixa;
	}

	public Integer getNumeroLeituraAcumulada(){

		return this.numeroLeituraAcumulada;
	}

	public void setNumeroLeituraAcumulada(Integer numeroLeituraAcumulada){

		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
	}

	public Short getNumeroDigitosLeitura(){

		return this.numeroDigitosLeitura;
	}

	public void setNumeroDigitosLeitura(Short numeroDigitosLeitura){

		this.numeroDigitosLeitura = numeroDigitosLeitura;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.micromedicao.hidrometro.HidrometroTipo getHidrometroTipo(){

		return this.hidrometroTipo;
	}

	public void setHidrometroTipo(gcom.micromedicao.hidrometro.HidrometroTipo hidrometroTipo){

		this.hidrometroTipo = hidrometroTipo;
	}

	public gcom.micromedicao.hidrometro.HidrometroSituacao getHidrometroSituacao(){

		return this.hidrometroSituacao;
	}

	public void setHidrometroSituacao(gcom.micromedicao.hidrometro.HidrometroSituacao hidrometroSituacao){

		this.hidrometroSituacao = hidrometroSituacao;
	}

	public gcom.micromedicao.hidrometro.HidrometroMarca getHidrometroMarca(){

		return this.hidrometroMarca;
	}

	public void setHidrometroMarca(gcom.micromedicao.hidrometro.HidrometroMarca hidrometroMarca){

		this.hidrometroMarca = hidrometroMarca;
	}

	public gcom.micromedicao.hidrometro.HidrometroCapacidade getHidrometroCapacidade(){

		return this.hidrometroCapacidade;
	}

	public void setHidrometroCapacidade(gcom.micromedicao.hidrometro.HidrometroCapacidade hidrometroCapacidade){

		this.hidrometroCapacidade = hidrometroCapacidade;
	}

	public gcom.micromedicao.hidrometro.HidrometroMotivoBaixa getHidrometroMotivoBaixa(){

		return this.hidrometroMotivoBaixa;
	}

	public void setHidrometroMotivoBaixa(gcom.micromedicao.hidrometro.HidrometroMotivoBaixa hidrometroMotivoBaixa){

		this.hidrometroMotivoBaixa = hidrometroMotivoBaixa;
	}

	public gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem getHidrometroLocalArmazenagem(){

		return this.hidrometroLocalArmazenagem;
	}

	public void setHidrometroLocalArmazenagem(gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem hidrometroLocalArmazenagem){

		this.hidrometroLocalArmazenagem = hidrometroLocalArmazenagem;
	}

	public gcom.micromedicao.hidrometro.HidrometroClasseMetrologica getHidrometroClasseMetrologica(){

		return this.hidrometroClasseMetrologica;
	}

	public void setHidrometroClasseMetrologica(gcom.micromedicao.hidrometro.HidrometroClasseMetrologica hidrometroClasseMetrologica){

		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
	}

	public gcom.micromedicao.hidrometro.HidrometroDiametro getHidrometroDiametro(){

		return this.hidrometroDiametro;
	}

	public void setHidrometroDiametro(gcom.micromedicao.hidrometro.HidrometroDiametro hidrometroDiametro){

		this.hidrometroDiametro = hidrometroDiametro;
	}

	public Set getHidrometroMovimentados(){

		return this.hidrometroMovimentados;
	}

	public void setHidrometroMovimentados(Set hidrometroMovimentados){

		this.hidrometroMovimentados = hidrometroMovimentados;
	}

	/**
	 * @return the indicadorHidrometroComposto
	 */
	public Short getIndicadorHidrometroComposto(){

		return indicadorHidrometroComposto;
	}

	/**
	 * @param indicadorHidrometroComposto
	 *            the indicadorHidrometroComposto to set
	 */
	public void setIndicadorHidrometroComposto(Short indicadorHidrometroComposto){

		this.indicadorHidrometroComposto = indicadorHidrometroComposto;
	}

	/**
	 * @return the fatorConversao
	 */
	public BigDecimal getFatorConversao(){

		return fatorConversao;
	}

	/**
	 * @param fatorConversao
	 *            the fatorConversao to set
	 */
	public void setFatorConversao(BigDecimal fatorConversao){

		this.fatorConversao = fatorConversao;
	}

	public String getLoteEntrega(){

		return loteEntrega;
	}

	public void setLoteEntrega(String loteEntrega){

		this.loteEntrega = loteEntrega;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/*
	 * Constutores Anteriores ao Mapeamento da Iteração
	 */
	/** full constructor */
	public Hidrometro(String numero, Date dataAquisicao, Short anoFabricacao, Short indicadorMacromedidor, Date dataUltimaRevisao,
						Date dataBaixa, Integer numeroLeituraAcumulada, Short numeroDigitosLeitura, Date ultimaAlteracao,
						gcom.micromedicao.hidrometro.HidrometroTipo hidrometroTipo,
						gcom.micromedicao.hidrometro.HidrometroSituacao hidrometroSituacao,
						gcom.micromedicao.hidrometro.HidrometroMarca hidrometroMarca,
						gcom.micromedicao.hidrometro.HidrometroCapacidade hidrometroCapacidade,
						gcom.micromedicao.hidrometro.HidrometroMotivoBaixa hidrometroMotivoBaixa,
						gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem hidrometroLocalArmazenagem,
						gcom.micromedicao.hidrometro.HidrometroClasseMetrologica hidrometroClasseMetrologica,
						gcom.micromedicao.hidrometro.HidrometroDiametro hidrometroDiametro) {

		this.numero = numero;
		this.dataAquisicao = dataAquisicao;
		this.anoFabricacao = anoFabricacao;
		this.indicadorMacromedidor = indicadorMacromedidor;
		this.dataUltimaRevisao = dataUltimaRevisao;
		this.dataBaixa = dataBaixa;
		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
		this.numeroDigitosLeitura = numeroDigitosLeitura;
		this.ultimaAlteracao = ultimaAlteracao;
		this.hidrometroTipo = hidrometroTipo;
		this.hidrometroSituacao = hidrometroSituacao;
		this.hidrometroMarca = hidrometroMarca;
		this.hidrometroCapacidade = hidrometroCapacidade;
		this.hidrometroMotivoBaixa = hidrometroMotivoBaixa;
		this.hidrometroLocalArmazenagem = hidrometroLocalArmazenagem;
		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
		this.hidrometroDiametro = hidrometroDiametro;
	}

	public Hidrometro(String numero, Date dataAquisicao, Short anoFabricacao, Short indicadorMacromedidor, Date dataUltimaRevisao,
						Date dataBaixa, Integer numeroLeituraAcumulada, Short numeroDigitosLeitura, Date ultimaAlteracao,
						gcom.micromedicao.hidrometro.HidrometroTipo hidrometroTipo,
						gcom.micromedicao.hidrometro.HidrometroSituacao hidrometroSituacao,
						gcom.micromedicao.hidrometro.HidrometroMarca hidrometroMarca,
						gcom.micromedicao.hidrometro.HidrometroCapacidade hidrometroCapacidade,
						gcom.micromedicao.hidrometro.HidrometroMotivoBaixa hidrometroMotivoBaixa,
						gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem hidrometroLocalArmazenagem,
						gcom.micromedicao.hidrometro.HidrometroClasseMetrologica hidrometroClasseMetrologica,
						gcom.micromedicao.hidrometro.HidrometroDiametro hidrometroDiametro, String loteEntrega) {

		this.numero = numero;
		this.dataAquisicao = dataAquisicao;
		this.anoFabricacao = anoFabricacao;
		this.indicadorMacromedidor = indicadorMacromedidor;
		this.dataUltimaRevisao = dataUltimaRevisao;
		this.dataBaixa = dataBaixa;
		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
		this.numeroDigitosLeitura = numeroDigitosLeitura;
		this.ultimaAlteracao = ultimaAlteracao;
		this.hidrometroTipo = hidrometroTipo;
		this.hidrometroSituacao = hidrometroSituacao;
		this.hidrometroMarca = hidrometroMarca;
		this.hidrometroCapacidade = hidrometroCapacidade;
		this.hidrometroMotivoBaixa = hidrometroMotivoBaixa;
		this.hidrometroLocalArmazenagem = hidrometroLocalArmazenagem;
		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
		this.hidrometroDiametro = hidrometroDiametro;
		this.loteEntrega = loteEntrega;
	}

	/** minimal constructor */
	public Hidrometro(String numero, Date dataAquisicao, Short anoFabricacao, Date dataUltimaRevisao, Integer numeroLeituraAcumulada,
						gcom.micromedicao.hidrometro.HidrometroTipo hidrometroTipo,
						gcom.micromedicao.hidrometro.HidrometroSituacao hidrometroSituacao,
						gcom.micromedicao.hidrometro.HidrometroMarca hidrometroMarca,
						gcom.micromedicao.hidrometro.HidrometroCapacidade hidrometroCapacidade,
						gcom.micromedicao.hidrometro.HidrometroMotivoBaixa hidrometroMotivoBaixa,
						gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem hidrometroLocalArmazenagem,
						gcom.micromedicao.hidrometro.HidrometroClasseMetrologica hidrometroClasseMetrologica,
						gcom.micromedicao.hidrometro.HidrometroDiametro hidrometroDiametro) {

		this.numero = numero;
		this.dataAquisicao = dataAquisicao;
		this.anoFabricacao = anoFabricacao;
		this.dataUltimaRevisao = dataUltimaRevisao;
		this.numeroLeituraAcumulada = numeroLeituraAcumulada;
		this.hidrometroTipo = hidrometroTipo;
		this.hidrometroSituacao = hidrometroSituacao;
		this.hidrometroMarca = hidrometroMarca;
		this.hidrometroCapacidade = hidrometroCapacidade;
		this.hidrometroMotivoBaixa = hidrometroMotivoBaixa;
		this.hidrometroLocalArmazenagem = hidrometroLocalArmazenagem;
		this.hidrometroClasseMetrologica = hidrometroClasseMetrologica;
		this.hidrometroDiametro = hidrometroDiametro;
	}


	/**
	 * Hidrometro
	 * <p>
	 * Esse método Constroi uma instância de Hidrometro.
	 * </p>
	 * 
	 * @param obterShort
	 * @author Marlos Ribeiro
	 * @since 17/04/2013
	 */
	public Hidrometro(Short numeroDigitosLeitura) {

		this();
		this.numeroDigitosLeitura = numeroDigitosLeitura;
	}

	/**
	 * Verifica a igualdade dos hidrometros para poder atualizar um conjunto de
	 * hidrometro
	 * 
	 * @param other
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean equalsHidrometro(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof Hidrometro)){
			return false;
		}
		Hidrometro castOther = (Hidrometro) other;

		return ((this.getDataAquisicao().equals(castOther.getDataAquisicao()))
						&& (this.getAnoFabricacao().equals(castOther.getAnoFabricacao()))
						&& (this.getIndicadorMacromedidor().equals(castOther.getIndicadorMacromedidor()))
						&& (this.getHidrometroClasseMetrologica().getId().equals(castOther.getHidrometroClasseMetrologica().getId()))
						&& (this.getHidrometroMarca().getId().equals(castOther.getHidrometroMarca().getId()))
						&& (this.getHidrometroDiametro().getId().equals(castOther.getHidrometroDiametro().getId()))
						&& (this.getHidrometroCapacidade().getId().equals(castOther.getHidrometroCapacidade().getId()))
						&& (this.getHidrometroTipo().getId().equals(castOther.getHidrometroTipo().getId()))
						&& (this.getCodigoFormatoNumeracao().equals(castOther.getCodigoFormatoNumeracao()))
						&& ((this.getHidrometroTipoTurbina() != null && castOther.getHidrometroTipoTurbina() != null && this
										.getHidrometroTipoTurbina().getId().equals(castOther.getHidrometroTipoTurbina().getId())) || (this
										.getHidrometroTipoTurbina() == null && castOther.getHidrometroTipoTurbina() == null))
						&& this.getFatorConversao().equals(castOther.getFatorConversao()) && this.getIndicadorHidrometroComposto().equals(
						castOther.getIndicadorHidrometroComposto()));
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroHidrometro filtroHidrometro = new FiltroHidrometro();

		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_TIPO);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_SITUACAO);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_MARCA);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_CAPACIDADE);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_DIAMETRO);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_MOTIVO_BAIXA);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_LOCAL_ARMAZENAGEM);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_CLASSE_METROLOGICA);
		filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_TIPO_TURBINA);

		filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.ID, this.getId()));
		return filtroHidrometro;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"numero", "anoFabricacao", "hidrometroMarca.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Numero", "Ano de Fabricacao", "Marca"};
		return labels;
	}

	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public HidrometroTipoTurbina getHidrometroTipoTurbina(){

		return hidrometroTipoTurbina;
	}

	public void setHidrometroTipoTurbina(HidrometroTipoTurbina hidrometroTipoTurbina){

		this.hidrometroTipoTurbina = hidrometroTipoTurbina;
	}

	public Integer getCodigoFormatoNumeracao(){

		return codigoFormatoNumeracao;
	}

	public void setCodigoFormatoNumeracao(Integer codigoFormatoNumeracao){

		this.codigoFormatoNumeracao = codigoFormatoNumeracao;
	}

	public Empresa getEmpresaUltimaAfericao(){

		return empresaUltimaAfericao;
	}

	public void setEmpresaUltimaAfericao(Empresa empresaUltimaAfericao){

		this.empresaUltimaAfericao = empresaUltimaAfericao;
	}

	public Integer getNumeroNotaFiscal(){

		return numeroNotaFiscal;
	}

	public void setNumeroNotaFiscal(Integer numeroNotaFiscal){

		this.numeroNotaFiscal = numeroNotaFiscal;
	}

}