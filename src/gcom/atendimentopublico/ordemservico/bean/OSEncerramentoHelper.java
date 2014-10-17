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
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto Matos
 * Saulo Vasconcelos de Lima
 * Virgínia Santos de Melo
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

package gcom.atendimentopublico.ordemservico.bean;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ordemservico.OcorrenciaInfracao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoFotoOcorrencia;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;
import java.util.Date;

/**
 * @author Saulo Lima
 * @date 18/05/2009
 */
public class OSEncerramentoHelper {

	private Integer numeroOS;

	private Date dataEncerramento;

	private Usuario usuarioLogado;

	private String idMotivoEncerramento;

	private Date ultimaAlteracao;

	private String parecerEncerramento;

	private String indicadorPavimento;

	private String pavimento;

	private String idTipoRetornoOSReferida;

	private String indicadorDeferimento;

	private String indicadorTrocaServicoTipo;

	private String idServicoTipo;

	private String idOSReferencia;

	private String idServicoTipoReferenciaOS;

	private IntegracaoComercialHelper integracaoComercialHelper;

	private String tipoServicoOSId;

	private OrdemServico osFiscalizacao;

	private String indicadorVistoriaServicoTipo;

	private String codigoRetornoVistoriaOs;

	private String dimensao1;

	private String dimensao2;

	private String dimensao3;

	private Collection<OrdemServicoFotoOcorrencia> ordemServicoFotoOcorrencia;

	private Integer material;

	private Integer diametro;

	private Integer profundidade;

	private Integer pressao;

	private OcorrenciaInfracao ocorrenciaInfracao;

	private Integer[] colecaoInfracaoTipo;

	private Date dataExecucao;

	public Integer getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(Integer numeroOS){

		this.numeroOS = numeroOS;
	}

	public Date getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public Usuario getUsuarioLogado(){

		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado){

		this.usuarioLogado = usuarioLogado;
	}

	public String getIdMotivoEncerramento(){

		return idMotivoEncerramento;
	}

	public void setIdMotivoEncerramento(String idMotivoEncerramento){

		this.idMotivoEncerramento = idMotivoEncerramento;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getParecerEncerramento(){

		return parecerEncerramento;
	}

	public void setParecerEncerramento(String parecerEncerramento){

		this.parecerEncerramento = parecerEncerramento;
	}

	public String getIndicadorPavimento(){

		return indicadorPavimento;
	}

	public void setIndicadorPavimento(String indicadorPavimento){

		this.indicadorPavimento = indicadorPavimento;
	}

	public String getPavimento(){

		return pavimento;
	}

	public void setPavimento(String pavimento){

		this.pavimento = pavimento;
	}

	public String getIdTipoRetornoOSReferida(){

		return idTipoRetornoOSReferida;
	}

	public void setIdTipoRetornoOSReferida(String idTipoRetornoOSReferida){

		this.idTipoRetornoOSReferida = idTipoRetornoOSReferida;
	}

	public String getIndicadorDeferimento(){

		return indicadorDeferimento;
	}

	public void setIndicadorDeferimento(String indicadorDeferimento){

		this.indicadorDeferimento = indicadorDeferimento;
	}

	public String getIndicadorTrocaServicoTipo(){

		return indicadorTrocaServicoTipo;
	}

	public void setIndicadorTrocaServicoTipo(String indicadorTrocaServicoTipo){

		this.indicadorTrocaServicoTipo = indicadorTrocaServicoTipo;
	}

	public String getIdServicoTipo(){

		return idServicoTipo;
	}

	public void setIdServicoTipo(String idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	public String getIdOSReferencia(){

		return idOSReferencia;
	}

	public void setIdOSReferencia(String idOSReferencia){

		this.idOSReferencia = idOSReferencia;
	}

	public String getIdServicoTipoReferenciaOS(){

		return idServicoTipoReferenciaOS;
	}

	public void setIdServicoTipoReferenciaOS(String idServicoTipoReferenciaOS){

		this.idServicoTipoReferenciaOS = idServicoTipoReferenciaOS;
	}

	public IntegracaoComercialHelper getIntegracaoComercialHelper(){

		return integracaoComercialHelper;
	}

	public void setIntegracaoComercialHelper(IntegracaoComercialHelper integracaoComercialHelper){

		this.integracaoComercialHelper = integracaoComercialHelper;
	}

	public String getTipoServicoOSId(){

		return tipoServicoOSId;
	}

	public void setTipoServicoOSId(String tipoServicoOSId){

		this.tipoServicoOSId = tipoServicoOSId;
	}

	public OrdemServico getOsFiscalizacao(){

		return osFiscalizacao;
	}

	public void setOsFiscalizacao(OrdemServico osFiscalizacao){

		this.osFiscalizacao = osFiscalizacao;
	}

	public String getIndicadorVistoriaServicoTipo(){

		return indicadorVistoriaServicoTipo;
	}

	public void setIndicadorVistoriaServicoTipo(String indicadorVistoriaServicoTipo){

		this.indicadorVistoriaServicoTipo = indicadorVistoriaServicoTipo;
	}

	public String getCodigoRetornoVistoriaOs(){

		return codigoRetornoVistoriaOs;
	}

	public void setCodigoRetornoVistoriaOs(String codigoRetornoVistoriaOs){

		this.codigoRetornoVistoriaOs = codigoRetornoVistoriaOs;
	}

	public String getDimensao1(){

		return dimensao1;
	}

	public void setDimensao1(String dimensao1){

		this.dimensao1 = dimensao1;
	}

	public String getDimensao2(){

		return dimensao2;
	}

	public void setDimensao2(String dimensao2){

		this.dimensao2 = dimensao2;
	}

	public String getDimensao3(){

		return dimensao3;
	}

	public void setDimensao3(String dimensao3){

		this.dimensao3 = dimensao3;
	}

	public void setOrdemServicoFotoOcorrencia(Collection<OrdemServicoFotoOcorrencia> ordemServicoFotoOcorrencia){

		this.ordemServicoFotoOcorrencia = ordemServicoFotoOcorrencia;
	}

	public Collection<OrdemServicoFotoOcorrencia> getOrdemServicoFotoOcorrencia(){

		return ordemServicoFotoOcorrencia;
	}

	public Integer getMaterial(){

		return material;
	}

	public void setMaterial(Integer material){

		this.material = material;
	}

	public Integer getDiametro(){

		return diametro;
	}

	public void setDiametro(Integer diametro){

		this.diametro = diametro;
	}

	public Integer getProfundidade(){

		return profundidade;
	}

	public void setProfundidade(Integer profundidade){

		this.profundidade = profundidade;
	}

	public Integer getPressao(){

		return pressao;
	}

	public void setPressao(Integer pressao){

		this.pressao = pressao;
	}

	public OcorrenciaInfracao getOcorrenciaInfracao(){

		return ocorrenciaInfracao;
	}

	public void setOcorrenciaInfracao(OcorrenciaInfracao ocorrenciaInfracao){

		this.ocorrenciaInfracao = ocorrenciaInfracao;
	}

	public Integer[] getColecaoInfracaoTipo(){

		return colecaoInfracaoTipo;
	}

	public void setColecaoInfracaoTipo(Integer[] colecaoInfracaoTipo){

		this.colecaoInfracaoTipo = colecaoInfracaoTipo;
	}

	public Date getDataExecucao(){

		return dataExecucao;
	}

	public void setDataExecucao(Date dataExecucao){

		this.dataExecucao = dataExecucao;
	}

}
