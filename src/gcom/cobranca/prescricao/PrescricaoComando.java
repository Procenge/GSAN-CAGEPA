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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.cobranca.prescricao;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

public class PrescricaoComando
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String titulo;

	private String descricaoSolicitacao;

	private Short indicadorSimulacao;

	private Integer codigoSetorComercialInicial;

	private Integer codigoSetorComercialFinal;

	private Integer numeroQuadraInicial;

	private Integer numeroQuadraFinal;

	private Date dataRelacaoClienteInicial;

	private Date dataRelacaoClienteFinal;

	private byte[] arquivoImoveis;

	private Integer anoMesReferenciaDebitoInicial;

	private Integer anoMesReferenciaDebitoFinal;

	private Date dataVencimentoDebitoInicial;

	private Date dataVencimentoDebitoFinal;

	private Integer quantidadeImoveis;

	private Integer quantidadeContasPrescritas;

	private Integer quantidadeGuiasPrescritas;

	private BigDecimal valorContasPrescritas;

	private BigDecimal valorGuiasPrescritas;

	private Date dataComando;

	private Date dataRealizacao;

	private Date ultimaAlteracao;

	private GerenciaRegional gerenciaRegional;

	private UnidadeNegocio unidadeNegocio;

	private Localidade elo;

	private Localidade localidadeInicial;

	private Localidade localidadeFinal;

	private Cliente cliente;

	private ClienteRelacaoTipo clienteRelacaoTipo;

	private Usuario usuario;

	public PrescricaoComando() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getTitulo(){

		return titulo;
	}

	public void setTitulo(String titulo){

		this.titulo = titulo;
	}

	public String getDescricaoSolicitacao(){

		return descricaoSolicitacao;
	}

	public void setDescricaoSolicitacao(String descricaoSolicitacao){

		this.descricaoSolicitacao = descricaoSolicitacao;
	}

	public Short getIndicadorSimulacao(){

		return indicadorSimulacao;
	}

	public void setIndicadorSimulacao(Short indicadorSimulacao){

		this.indicadorSimulacao = indicadorSimulacao;
	}

	public Integer getCodigoSetorComercialInicial(){

		return codigoSetorComercialInicial;
	}

	public void setCodigoSetorComercialInicial(Integer codigoSetorComercialInicial){

		this.codigoSetorComercialInicial = codigoSetorComercialInicial;
	}

	public Integer getCodigoSetorComercialFinal(){

		return codigoSetorComercialFinal;
	}

	public void setCodigoSetorComercialFinal(Integer codigoSetorComercialFinal){

		this.codigoSetorComercialFinal = codigoSetorComercialFinal;
	}

	public Integer getNumeroQuadraInicial(){

		return numeroQuadraInicial;
	}

	public void setNumeroQuadraInicial(Integer numeroQuadraInicial){

		this.numeroQuadraInicial = numeroQuadraInicial;
	}

	public Integer getNumeroQuadraFinal(){

		return numeroQuadraFinal;
	}

	public void setNumeroQuadraFinal(Integer numeroQuadraFinal){

		this.numeroQuadraFinal = numeroQuadraFinal;
	}

	public Date getDataRelacaoClienteInicial(){

		return dataRelacaoClienteInicial;
	}

	public void setDataRelacaoClienteInicial(Date dataRelacaoClienteInicial){

		this.dataRelacaoClienteInicial = dataRelacaoClienteInicial;
	}

	public Date getDataRelacaoClienteFinal(){

		return dataRelacaoClienteFinal;
	}

	public void setDataRelacaoClienteFinal(Date dataRelacaoClienteFinal){

		this.dataRelacaoClienteFinal = dataRelacaoClienteFinal;
	}

	public byte[] getArquivoImoveis(){

		return arquivoImoveis;
	}

	public void setArquivoImoveis(byte[] arquivoImoveis){

		this.arquivoImoveis = arquivoImoveis;
	}

	public Integer getAnoMesReferenciaDebitoInicial(){

		return anoMesReferenciaDebitoInicial;
	}

	public void setAnoMesReferenciaDebitoInicial(Integer anoMesReferenciaDebitoInicial){

		this.anoMesReferenciaDebitoInicial = anoMesReferenciaDebitoInicial;
	}

	public Integer getAnoMesReferenciaDebitoFinal(){

		return anoMesReferenciaDebitoFinal;
	}

	public void setAnoMesReferenciaDebitoFinal(Integer anoMesReferenciaDebitoFinal){

		this.anoMesReferenciaDebitoFinal = anoMesReferenciaDebitoFinal;
	}

	public Date getDataVencimentoDebitoInicial(){

		return dataVencimentoDebitoInicial;
	}

	public void setDataVencimentoDebitoInicial(Date dataVencimentoDebitoInicial){

		this.dataVencimentoDebitoInicial = dataVencimentoDebitoInicial;
	}

	public Date getDataVencimentoDebitoFinal(){

		return dataVencimentoDebitoFinal;
	}

	public void setDataVencimentoDebitoFinal(Date dataVencimentoDebitoFinal){

		this.dataVencimentoDebitoFinal = dataVencimentoDebitoFinal;
	}

	public Integer getQuantidadeImoveis(){

		return quantidadeImoveis;
	}

	public void setQuantidadeImoveis(Integer quantidadeImoveis){

		this.quantidadeImoveis = quantidadeImoveis;
	}

	public Integer getQuantidadeContasPrescritas(){

		return quantidadeContasPrescritas;
	}

	public void setQuantidadeContasPrescritas(Integer quantidadeContasPrescritas){

		this.quantidadeContasPrescritas = quantidadeContasPrescritas;
	}

	public Integer getQuantidadeGuiasPrescritas(){

		return quantidadeGuiasPrescritas;
	}

	public void setQuantidadeGuiasPrescritas(Integer quantidadeGuiasPrescritas){

		this.quantidadeGuiasPrescritas = quantidadeGuiasPrescritas;
	}

	public BigDecimal getValorContasPrescritas(){

		return valorContasPrescritas;
	}

	public void setValorContasPrescritas(BigDecimal valorContasPrescritas){

		this.valorContasPrescritas = valorContasPrescritas;
	}

	public BigDecimal getValorGuiasPrescritas(){

		return valorGuiasPrescritas;
	}

	public void setValorGuiasPrescritas(BigDecimal valorGuiasPrescritas){

		this.valorGuiasPrescritas = valorGuiasPrescritas;
	}

	public Date getDataComando(){

		return dataComando;
	}

	public void setDataComando(Date dataComando){

		this.dataComando = dataComando;
	}

	public Date getDataRealizacao(){

		return dataRealizacao;
	}

	public void setDataRealizacao(Date dataRealizacao){

		this.dataRealizacao = dataRealizacao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public GerenciaRegional getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public UnidadeNegocio getUnidadeNegocio(){

		return unidadeNegocio;
	}

	public void setUnidadeNegocio(UnidadeNegocio unidadeNegocio){

		this.unidadeNegocio = unidadeNegocio;
	}

	public Localidade getLocalidadeInicial(){

		return localidadeInicial;
	}

	public void setLocalidadeInicial(Localidade localidadeInicial){

		this.localidadeInicial = localidadeInicial;
	}

	public Localidade getLocalidadeFinal(){

		return localidadeFinal;
	}

	public void setLocalidadeFinal(Localidade localidadeFinal){

		this.localidadeFinal = localidadeFinal;
	}

	public Cliente getCliente(){

		return cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	public ClienteRelacaoTipo getClienteRelacaoTipo(){

		return clienteRelacaoTipo;
	}

	public void setClienteRelacaoTipo(ClienteRelacaoTipo clienteRelacaoTipo){

		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Localidade getElo(){

		return elo;
	}

	public void setElo(Localidade elo){

		this.elo = elo;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + " - " + getTitulo();
	}

	public String getDescricaoTituloAbreviado(){

		String retorno = "";

		if(this.titulo != null){

			retorno = Util.completaString(titulo, 10);
		}else{

			retorno = Util.completaString("", 10);
		}

		return retorno;
	}

	public String getDescricaoIndicadorSimulacao(){

		String retorno = "";

		if(this.indicadorSimulacao.equals(ConstantesSistema.SIM)){

			retorno = "SIM";
		}else{

			retorno = "NÃO";
		}

		return retorno;
	}

	public String getAnoMesReferenciaDebitoInicialFormatado(){

		String retorno = "";

		if(this.anoMesReferenciaDebitoInicial != null){

			retorno = Util.formatarAnoMesParaMesAno(this.anoMesReferenciaDebitoInicial);
		}

		return retorno;
	}

	public String getAnoMesReferenciaDebitoFinalFormatado(){

		String retorno = "";

		if(this.anoMesReferenciaDebitoFinal != null){

			retorno = Util.formatarAnoMesParaMesAno(this.anoMesReferenciaDebitoFinal);
		}

		return retorno;
	}

}
