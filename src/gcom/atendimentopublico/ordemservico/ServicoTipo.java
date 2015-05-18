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
 * Saulo Lima
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

package gcom.atendimentopublico.ordemservico;

import gcom.faturamento.credito.CreditoTipo;
import gcom.faturamento.debito.DebitoTipo;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ServicoTipo
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private String descricao;

	/** persistent field */
	private String descricaoAbreviada;

	/** persistent field */
	private BigDecimal valor;

	/** persistent field */
	private short indicadorPavimento;

	/** persistent field */
	private short indicadorAtualizaComercial;

	/** persistent field */
	private short indicadorTerceirizado;

	/** persistent field */
	private String codigoServicoTipo;

	/** persistent field */
	private short tempoMedioExecucao;

	/** persistent field */
	private short indicadorUso;

	/** persistent field */
	private short indicadorVistoria;

	/** persistent field */
	private short indicadorPermiteAlterarValor;

	/** persistent field */
	private short indicadorIncluirDebito;

	/** persistent field */
	private short indicadorCobrarJuros;

	/** persistent field */
	private short indicadorFiscalizacaoInfracao;

	/** persistent field */
	private Short indicadorFiscalizacaoSituacao;

	/** persistent field */
	private Short indicadorGerarHistoricoImovel;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private gcom.atendimentopublico.ordemservico.ServicoTipoReferencia servicoTipoReferencia;

	/** persistent field */
	private CreditoTipo creditoTipo;

	/** persistent field */
	private gcom.atendimentopublico.ordemservico.ServicoPerfilTipo servicoPerfilTipo;

	/** persistent field */
	private gcom.atendimentopublico.ordemservico.ServicoTipoSubgrupo servicoTipoSubgrupo;

	/** persistent field */
	private gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridade;

	/** persistent field */
	private DebitoTipo debitoTipo;

	private Collection servicoTipoAtividades;

	private Collection servicoTipoMateriais;

	private Collection<ServicoAssociado> servicosAssociados;

	private Collection<ServicoTipoTramite> servicosTipoTramite;

	private BigDecimal valorRemuneracao;

	private BigDecimal percentualRemuneracao;

	private Integer prazoExecucao;

	private Short tipoRemuneracao;

	private OrdemServicoLayout ordemServicoLayout;

	private int indicadorDeslocamento;

	private int indicadorHorariosExecucao;

	private int indicadorCausaOcorrencia;

	private int indicadorRedeRamalAgua;

	private int indicadorRedeRamalEsgoto;

	private int indicadorMaterial;

	private int indicadorVala;

	private int indicadorOrdemSeletiva;

	private int indicadorServicoCritico;

	private int indicadorPagamentoAntecipado;

	private String codigoConstante;

	private Integer prazoRestricaoNovaOrdemServico = 0;

	private transient Integer qtidadeOs;

	private Short indicadorAfericaoHidrometro;

	private Integer numeroMaximoVisitasImprodutivasPermitidas;

	private Short numeroMaximoGuiaPrestacaoAntecipadaPermitidas;

	// Indicadores

	public final static short INDICADOR_PAVIMENTO_SIM = 1;

	public final static String INDICADOR_PAVIMENTO_NAO = "2";

	public final static short INDICADOR_ATUALIZA_COMERCIAL_SIM = 1;

	public final static short INDICADOR_ATUALIZA_COMERCIAL_NAO = 2;

	public final static short INDICADOR_VISTORIA_SIM = 1;

	public final static String INDICADOR_VISTORIA_SERVICO_TIPO_NAO = "2";

	public final static short INDICADOR_AFERICAO_SIM = 1;

	public final static short INDICADOR_AFERICAO_NAO = 2;

	// Constantes

	public static Collection<Integer> CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA;

	public static Collection<Integer> CONFIRMAR_DADOS_LIGACAO_AGUA;

	public static Collection<Integer> CONFIRMAR_DADOS_LIGACAO_ESGOTO;

	public static Collection<Integer> CONFIRMAR_DADOS_SUPRESSAO_LIGACAO_AGUA;

	public static Collection<Integer> CORTE_LIGACAO_AGUA;

	public static Collection<Integer> DESATIVACAO_LIGACAO_ESGOTO;

	public static Collection<Integer> INSTALACAO_HIDROMETRO;

	public static Collection<Integer> LIGACAO_AGUA;

	public static Collection<Integer> LIGACAO_ESGOTO;

	public static Collection<Integer> REATIVACAO_LIGACAO_ESGOTO;

	public static Collection<Integer> RESTABELECIMENTO_LIGACAO_ESGOTO;

	public static Collection<Integer> SUBSTITUICAO_HIDROMETRO;

	public static Collection<Integer> SUPRESSAO_LIGACAO_AGUA;

	public static Collection<Integer> TAMPONAMENTO_LIGACAO_ESGOTO;

	public static Collection<Integer> RELIGACAO_AGUA;

	public static Collection<Integer> RELIGACAO_REGISTRO_MAGNETICO;

	public static Collection<Integer> RELIGACAO_TUBETE;

	public static Collection<Integer> CORTE;

	public static Collection<Integer> CORTE_REGISTRO_MAGNETICO;

	public static Collection<Integer> CORTE_TUBETE;

	/** full constructor */
	public ServicoTipo(String descricao, String descricaoAbreviada, BigDecimal valor, short prioridade, short indicadorPavimento,
						short indicadorAtualizaComercial, short indicadorTerceirizado, String codigoServicoTipo, short tempoMedioExecucao,
						short indicadorUso, Date ultimaAlteracao,
						gcom.atendimentopublico.ordemservico.ServicoTipoReferencia servicoTipoReferencia, CreditoTipo creditoTipo,
						gcom.atendimentopublico.ordemservico.ServicoPerfilTipo servicoPerfilTipo,
						gcom.atendimentopublico.ordemservico.ServicoTipoSubgrupo servicoTipoSubgrupo,
						gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridade, DebitoTipo debitoTipo) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.valor = valor;
		// this.prioridade = prioridade;
		this.indicadorPavimento = indicadorPavimento;
		this.indicadorAtualizaComercial = indicadorAtualizaComercial;
		this.indicadorTerceirizado = indicadorTerceirizado;
		this.codigoServicoTipo = codigoServicoTipo;
		this.tempoMedioExecucao = tempoMedioExecucao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.servicoTipoReferencia = servicoTipoReferencia;
		this.creditoTipo = creditoTipo;
		this.servicoPerfilTipo = servicoPerfilTipo;
		this.servicoTipoSubgrupo = servicoTipoSubgrupo;
		this.servicoTipoPrioridade = servicoTipoPrioridade;
		this.debitoTipo = debitoTipo;
	}

	/** default constructor */
	public ServicoTipo() {

	}

	public Integer getNumeroMaximoVisitasImprodutivasPermitidas(){

		return this.numeroMaximoVisitasImprodutivasPermitidas;
	}

	public void setNumeroMaximoVisitasImprodutivasPermitidas(Integer numeroMaximoVisitasImprodutivasPermitidas){

		this.numeroMaximoVisitasImprodutivasPermitidas = numeroMaximoVisitasImprodutivasPermitidas;
	}

	public Short getNumeroMaximoGuiaPrestacaoAntecipadaPermitidas(){

		return this.numeroMaximoGuiaPrestacaoAntecipadaPermitidas;
	}

	public void setNumeroMaximoGuiaPrestacaoAntecipadaPermitidas(Short numeroMaximoGuiaPrestacaoAntecipadaPermitidas){

		this.numeroMaximoGuiaPrestacaoAntecipadaPermitidas = numeroMaximoGuiaPrestacaoAntecipadaPermitidas;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return this.descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public BigDecimal getValor(){

		return this.valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public short getIndicadorPavimento(){

		return this.indicadorPavimento;
	}

	public void setIndicadorPavimento(short indicadorPavimento){

		this.indicadorPavimento = indicadorPavimento;
	}

	public short getIndicadorAtualizaComercial(){

		return this.indicadorAtualizaComercial;
	}

	public void setIndicadorAtualizaComercial(short indicadorAtualizaComercial){

		this.indicadorAtualizaComercial = indicadorAtualizaComercial;
	}

	public short getIndicadorTerceirizado(){

		return this.indicadorTerceirizado;
	}

	public void setIndicadorTerceirizado(short indicadorTerceirizado){

		this.indicadorTerceirizado = indicadorTerceirizado;
	}

	public String getCodigoServicoTipo(){

		return this.codigoServicoTipo;
	}

	public void setCodigoServicoTipo(String codigoServicoTipo){

		this.codigoServicoTipo = codigoServicoTipo;
	}

	public short getTempoMedioExecucao(){

		return this.tempoMedioExecucao;
	}

	public void setTempoMedioExecucao(short tempoMedioExecucao){

		this.tempoMedioExecucao = tempoMedioExecucao;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipoReferencia getServicoTipoReferencia(){

		return this.servicoTipoReferencia;
	}

	public void setServicoTipoReferencia(gcom.atendimentopublico.ordemservico.ServicoTipoReferencia servicoTipoReferencia){

		this.servicoTipoReferencia = servicoTipoReferencia;
	}

	public CreditoTipo getCreditoTipo(){

		return this.creditoTipo;
	}

	public void setCreditoTipo(CreditoTipo creditoTipo){

		this.creditoTipo = creditoTipo;
	}

	public gcom.atendimentopublico.ordemservico.ServicoPerfilTipo getServicoPerfilTipo(){

		return this.servicoPerfilTipo;
	}

	public void setServicoPerfilTipo(gcom.atendimentopublico.ordemservico.ServicoPerfilTipo servicoPerfilTipo){

		this.servicoPerfilTipo = servicoPerfilTipo;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipoSubgrupo getServicoTipoSubgrupo(){

		return this.servicoTipoSubgrupo;
	}

	public void setServicoTipoSubgrupo(gcom.atendimentopublico.ordemservico.ServicoTipoSubgrupo servicoTipoSubgrupo){

		this.servicoTipoSubgrupo = servicoTipoSubgrupo;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade getServicoTipoPrioridade(){

		return this.servicoTipoPrioridade;
	}

	public void setServicoTipoPrioridade(gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridade){

		this.servicoTipoPrioridade = servicoTipoPrioridade;
	}

	public DebitoTipo getDebitoTipo(){

		return this.debitoTipo;
	}

	public void setDebitoTipo(DebitoTipo debitoTipo){

		this.debitoTipo = debitoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/**
	 * @return Retorna o campo indicadorUso.
	 */
	public short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            O indicadorUso a ser setado.
	 */
	public void setIndicadorUso(short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Collection getServicoTipoAtividades(){

		return servicoTipoAtividades;
	}

	public void setServicoTipoAtividades(Collection servicoTipoAtividades){

		this.servicoTipoAtividades = servicoTipoAtividades;
	}

	public Collection getServicoTipoMateriais(){

		return servicoTipoMateriais;
	}

	public void setServicoTipoMateriais(Collection servicoTipoMateriais){

		this.servicoTipoMateriais = servicoTipoMateriais;
	}

	public short getIndicadorFiscalizacaoInfracao(){

		return indicadorFiscalizacaoInfracao;
	}

	public void setIndicadorFiscalizacaoInfracao(short indicadorFiscalizacaoInfracao){

		this.indicadorFiscalizacaoInfracao = indicadorFiscalizacaoInfracao;
	}

	public short getIndicadorVistoria(){

		return indicadorVistoria;
	}

	public void setIndicadorVistoria(short indicadorVistoria){

		this.indicadorVistoria = indicadorVistoria;
	}

	public short getIndicadorPermiteAlterarValor(){

		return indicadorPermiteAlterarValor;
	}

	public void setIndicadorPermiteAlterarValor(short indicadorPermiteAlterarValor){

		this.indicadorPermiteAlterarValor = indicadorPermiteAlterarValor;
	}

	public short getIndicadorCobrarJuros(){

		return indicadorCobrarJuros;
	}

	public void setIndicadorCobrarJuros(short indicadorCobrarJuros){

		this.indicadorCobrarJuros = indicadorCobrarJuros;
	}

	public short getIndicadorIncluirDebito(){

		return indicadorIncluirDebito;
	}

	public void setIndicadorIncluirDebito(short indicadorIncluirDebito){

		this.indicadorIncluirDebito = indicadorIncluirDebito;
	}

	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public Integer getPrazoExecucao(){

		return prazoExecucao;
	}

	public void setPrazoExecucao(Integer prazoExecucao){

		this.prazoExecucao = prazoExecucao;
	}

	public Short getTipoRemuneracao(){

		return tipoRemuneracao;
	}

	public void setTipoRemuneracao(Short tipoRemuneracao){

		this.tipoRemuneracao = tipoRemuneracao;
	}

	public Collection<ServicoAssociado> getServicosAssociados(){

		return servicosAssociados;
	}

	public void setServicosAssociados(Collection<ServicoAssociado> servicosAssociados){

		this.servicosAssociados = servicosAssociados;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof ServicoTipo)) return false;
		final ServicoTipo other = (ServicoTipo) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	public int getIndicadorDeslocamento(){

		return indicadorDeslocamento;
	}

	public void setIndicadorDeslocamento(int indicadorDeslocamento){

		this.indicadorDeslocamento = indicadorDeslocamento;
	}

	public int getIndicadorHorariosExecucao(){

		return indicadorHorariosExecucao;
	}

	public void setIndicadorHorariosExecucao(int indicadorHorariosExecucao){

		this.indicadorHorariosExecucao = indicadorHorariosExecucao;
	}

	public int getIndicadorCausaOcorrencia(){

		return indicadorCausaOcorrencia;
	}

	public void setIndicadorCausaOcorrencia(int indicadorCausaOcorrencia){

		this.indicadorCausaOcorrencia = indicadorCausaOcorrencia;
	}

	public int getIndicadorRedeRamalAgua(){

		return indicadorRedeRamalAgua;
	}

	public void setIndicadorRedeRamalAgua(int indicadorRedeRamalAgua){

		this.indicadorRedeRamalAgua = indicadorRedeRamalAgua;
	}

	public int getIndicadorRedeRamalEsgoto(){

		return indicadorRedeRamalEsgoto;
	}

	public void setIndicadorRedeRamalEsgoto(int indicadorRedeRamalEsgoto){

		this.indicadorRedeRamalEsgoto = indicadorRedeRamalEsgoto;
	}

	public int getIndicadorMaterial(){

		return indicadorMaterial;
	}

	public void setIndicadorMaterial(int indicadorMaterial){

		this.indicadorMaterial = indicadorMaterial;
	}

	public int getIndicadorVala(){

		return indicadorVala;
	}

	public void setIndicadorVala(int indicadorVala){

		this.indicadorVala = indicadorVala;
	}

	public OrdemServicoLayout getOrdemServicoLayout(){

		return ordemServicoLayout;
	}

	public void setOrdemServicoLayout(OrdemServicoLayout ordemServicoLayout){

		this.ordemServicoLayout = ordemServicoLayout;
	}

	public int getIndicadorOrdemSeletiva(){

		return indicadorOrdemSeletiva;
	}

	public void setIndicadorOrdemSeletiva(int indicadorOrdemSeletiva){

		this.indicadorOrdemSeletiva = indicadorOrdemSeletiva;
	}

	public int getIndicadorServicoCritico(){

		return indicadorServicoCritico;
	}

	public void setIndicadorServicoCritico(int indicadorServicoCritico){

		this.indicadorServicoCritico = indicadorServicoCritico;
	}

	public int getIndicadorPagamentoAntecipado(){

		return indicadorPagamentoAntecipado;
	}

	public void setIndicadorPagamentoAntecipado(int indicadorPagamentoAntecipado){

		this.indicadorPagamentoAntecipado = indicadorPagamentoAntecipado;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		String retorno = null;

		if(this.getId() != null && this.getDescricao() != null){
			retorno = this.getId() + " - " + this.getDescricao();
		}else{
			retorno = null;
		}

		return retorno;
	}

	public Short getIndicadorFiscalizacaoSituacao(){

		return indicadorFiscalizacaoSituacao;
	}

	public void setIndicadorFiscalizacaoSituacao(Short indicadorFiscalizacaoSituacao){

		this.indicadorFiscalizacaoSituacao = indicadorFiscalizacaoSituacao;
	}

	public Integer getQtidadeOs(){

		return qtidadeOs;
	}

	public void setQtidadeOs(Integer qtidadeOs){

		this.qtidadeOs = qtidadeOs;
	}

	public String getDescricaoQtidadeOs(){

		StringBuilder st = new StringBuilder();
		st.append(getDescricao());
		st.append(" - ");

		if(getQtidadeOs() == null){
			st.append(0);
		}else{
			st.append(getQtidadeOs());
		}

		return st.toString();
	}

	public String getIdDescricao(){

		String retorno = "";

		if(this.getId() != null && !Util.isVazioOuBranco(this.getDescricao())){
			retorno = this.getId() + " - " + this.getDescricao();
		}else if(this.getId() != null){
			retorno = String.valueOf(this.getId());
		}else if(!Util.isVazioOuBranco(this.getDescricao())){
			retorno = this.getDescricao();
		}

		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, this.getId()));

		return filtroServicoTipo;
	}

	public Collection<ServicoTipoTramite> getServicosTipoTramite(){

		return servicosTipoTramite;
	}

	public void setServicosTipoTramite(Collection<ServicoTipoTramite> servicosTipoTramite){

		this.servicosTipoTramite = servicosTipoTramite;
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public Integer getPrazoRestricaoNovaOrdemServico(){

		return prazoRestricaoNovaOrdemServico;
	}

	public void setPrazoRestricaoNovaOrdemServico(Integer prazoRestricaoNovaOrdemServico){

		this.prazoRestricaoNovaOrdemServico = prazoRestricaoNovaOrdemServico;
	}

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Hebert Falcão
	 * @date 26/02/2012
	 */
	public static void inicializarConstantes(){

		CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA = ServicoTipoEnum.CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA.getColecaoIds();

		CONFIRMAR_DADOS_LIGACAO_AGUA = ServicoTipoEnum.CONFIRMAR_DADOS_LIGACAO_AGUA.getColecaoIds();

		CONFIRMAR_DADOS_LIGACAO_ESGOTO = ServicoTipoEnum.CONFIRMAR_DADOS_LIGACAO_ESGOTO.getColecaoIds();

		CONFIRMAR_DADOS_SUPRESSAO_LIGACAO_AGUA = ServicoTipoEnum.CONFIRMAR_DADOS_SUPRESSAO_LIGACAO_AGUA.getColecaoIds();

		CORTE_LIGACAO_AGUA = ServicoTipoEnum.CORTE_LIGACAO_AGUA.getColecaoIds();

		DESATIVACAO_LIGACAO_ESGOTO = ServicoTipoEnum.DESATIVACAO_LIGACAO_ESGOTO.getColecaoIds();

		INSTALACAO_HIDROMETRO = ServicoTipoEnum.INSTALACAO_HIDROMETRO.getColecaoIds();

		LIGACAO_AGUA = ServicoTipoEnum.LIGACAO_AGUA.getColecaoIds();

		LIGACAO_ESGOTO = ServicoTipoEnum.LIGACAO_ESGOTO.getColecaoIds();

		REATIVACAO_LIGACAO_ESGOTO = ServicoTipoEnum.REATIVACAO_LIGACAO_ESGOTO.getColecaoIds();

		RESTABELECIMENTO_LIGACAO_ESGOTO = ServicoTipoEnum.RESTABELECIMENTO_LIGACAO_ESGOTO.getColecaoIds();

		SUBSTITUICAO_HIDROMETRO = ServicoTipoEnum.SUBSTITUICAO_HIDROMETRO.getColecaoIds();

		SUPRESSAO_LIGACAO_AGUA = ServicoTipoEnum.SUPRESSAO_LIGACAO_AGUA.getColecaoIds();

		TAMPONAMENTO_LIGACAO_ESGOTO = ServicoTipoEnum.TAMPONAMENTO_LIGACAO_ESGOTO.getColecaoIds();

		RELIGACAO_AGUA = ServicoTipoEnum.RELIGACAO_AGUA.getColecaoIds();

		RELIGACAO_REGISTRO_MAGNETICO = ServicoTipoEnum.RELIGACAO_REGISTRO_MAGNETICO.getColecaoIds();

		RELIGACAO_TUBETE = ServicoTipoEnum.RELIGACAO_TUBETE.getColecaoIds();

		CORTE = ServicoTipoEnum.CORTE.getColecaoIds();

		CORTE_REGISTRO_MAGNETICO = ServicoTipoEnum.CORTE_REGISTRO_MAGNETICO.getColecaoIds();

		CORTE_TUBETE = ServicoTipoEnum.CORTE_TUBETE.getColecaoIds();
	}

	public Short getIndicadorGerarHistoricoImovel(){

		return indicadorGerarHistoricoImovel;
	}

	public void setIndicadorGerarHistoricoImovel(Short indicadorGerarHistoricoImovel){

		this.indicadorGerarHistoricoImovel = indicadorGerarHistoricoImovel;
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído vazio a
	 * sua coleção de valores.
	 * 
	 * @author Hebert Falcão, Anderson Italo
	 * @date 26/02/2012, 31/07/2012
	 */
	public static enum ServicoTipoEnum {

		CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA, CONFIRMAR_DADOS_LIGACAO_AGUA, CONFIRMAR_DADOS_LIGACAO_ESGOTO,
		CONFIRMAR_DADOS_SUPRESSAO_LIGACAO_AGUA, CORTE_LIGACAO_AGUA, DESATIVACAO_LIGACAO_ESGOTO, INSTALACAO_HIDROMETRO, LIGACAO_AGUA,
		LIGACAO_ESGOTO, REATIVACAO_LIGACAO_ESGOTO, RESTABELECIMENTO_LIGACAO_ESGOTO, SUBSTITUICAO_HIDROMETRO, SUPRESSAO_LIGACAO_AGUA,
		TAMPONAMENTO_LIGACAO_ESGOTO, RELIGACAO_AGUA, RELIGACAO_REGISTRO_MAGNETICO, RELIGACAO_TUBETE, CORTE, CORTE_REGISTRO_MAGNETICO,
		CORTE_TUBETE;

		private Collection<Integer> colecaoIds = new ArrayList<Integer>();

		private ServicoTipoEnum() {

			try{

				Collection<ServicoTipo> colecaoServicoTipo = (Collection<ServicoTipo>) RepositorioUtilHBM.getInstancia()
								.pesquisarColecaoObjetosPorCodigoConstante(name(), ServicoTipo.class);

				if(!Util.isVazioOrNulo(colecaoServicoTipo)){

					for(ServicoTipo servicoTipo : colecaoServicoTipo){

						colecaoIds.add(servicoTipo.getId());
					}
				}
			}catch(ErroRepositorioException e){

				e.printStackTrace();
				throw new SistemaException(e, e.getMessage());
			}
		}

		public Collection<Integer> getColecaoIds(){

			return colecaoIds;
		}
	}

	public Short getIndicadorAfericaoHidrometro(){

		return indicadorAfericaoHidrometro;
	}

	public void setIndicadorAfericaoHidrometro(Short indicadorAfericaoHidrometro){

		this.indicadorAfericaoHidrometro = indicadorAfericaoHidrometro;
	}

}
