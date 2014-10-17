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

package gcom.cobranca.bean;

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ordemservico.DiametroRedeAgua;
import gcom.atendimentopublico.ordemservico.MaterialRedeAgua;
import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.cadastro.imovel.PavimentoRua;

import java.util.Date;

public class CriticaOSLoteHelper {

	private Integer numeroOS;

	private Integer idImovel;

	private Date dataEncerramento;

	private Integer motivoEncerramento;

	private Integer tipoCorte;

	private Integer tipoSupressao;

	private Integer motivoCorteSupressao;

	private Integer leituraCorteSupressao;

	private String seloCorteSupressao;

	private String parecer;

	private DiametroRedeAgua diametroRedeAgua;

	private Double profundidadeRede;

	private MaterialRedeAgua materialRedeAgua;

	private Double larguraVala;

	private Double profundidadeVala;

	private LocalOcorrencia localOcorrencia;

	private PavimentoRua pavimentoRua;

	private Integer indicadorValaEntulho;

	private Integer indicadorValaAterrada;

	private String resultadoCritica;

	private boolean statusCritica;

	private String mensagemCritica;

	private String linhaArquivoLote;

	private Double comprimento;

	private Double pressao;

	// NOVO

	private IntegracaoComercialHelper integracaoComercialHelper;

	private String indicadorInsSubHidrometro;

	private boolean indicadorVerificacaoIrregularidades;

	public CriticaOSLoteHelper() {

		super();
	}

	public CriticaOSLoteHelper(Integer numeroOS, Integer idImovel, Date dataExecucao, Date dataEncerramento, Integer motivoEncerramento,
								Integer tipoCorte, Integer tipoSupressao, Integer motivoCorteSupressao, Integer leituraCorteSupressao,
								String seloCorteSupressao, String parecer, String resultadoCritica, String mensagemCritica,
								String linhaArquivoLote) {

		super();
		this.numeroOS = numeroOS;
		this.idImovel = idImovel;
		// this.dataExecucao = dataExecucao;
		this.dataEncerramento = dataEncerramento;
		this.motivoEncerramento = motivoEncerramento;
		this.tipoCorte = tipoCorte;
		this.tipoSupressao = tipoSupressao;
		this.motivoCorteSupressao = motivoCorteSupressao;
		this.leituraCorteSupressao = leituraCorteSupressao;
		this.seloCorteSupressao = seloCorteSupressao;
		this.parecer = parecer;
		this.resultadoCritica = resultadoCritica;
		this.mensagemCritica = mensagemCritica;
		this.linhaArquivoLote = linhaArquivoLote;
	}

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

	public Integer getMotivoEncerramento(){

		return motivoEncerramento;
	}

	public void setMotivoEncerramento(Integer motivoEncerramento){

		this.motivoEncerramento = motivoEncerramento;
	}

	public Integer getTipoCorte(){

		return tipoCorte;
	}

	public void setTipoCorte(Integer tipoCorte){

		this.tipoCorte = tipoCorte;
	}

	public Integer getTipoSupressao(){

		return tipoSupressao;
	}

	public void setTipoSupressao(Integer tipoSupressao){

		this.tipoSupressao = tipoSupressao;
	}

	public Integer getMotivoCorteSupressao(){

		return motivoCorteSupressao;
	}

	public void setMotivoCorteSupressao(Integer motivoCorteSupressao){

		this.motivoCorteSupressao = motivoCorteSupressao;
	}

	public Integer getLeituraCorteSupressao(){

		return leituraCorteSupressao;
	}

	public void setLeituraCorteSupressao(Integer leituraCorteSupressao){

		this.leituraCorteSupressao = leituraCorteSupressao;
	}

	public String getSeloCorteSupressao(){

		return seloCorteSupressao;
	}

	public void setSeloCorteSupressao(String seloCorteSupressao){

		this.seloCorteSupressao = seloCorteSupressao;
	}

	public String getParecer(){

		return parecer;
	}

	public void setParecer(String parecer){

		this.parecer = parecer;
	}

	public String getResultadoCritica(){

		return resultadoCritica;
	}

	public void setResultadoCritica(String resultadoCritica){

		this.resultadoCritica = resultadoCritica;
	}

	public String getMensagemCritica(){

		return mensagemCritica;
	}

	public void setMensagemCritica(String mensagemCritica){

		this.mensagemCritica = mensagemCritica;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getLinhaArquivoLote(){

		return linhaArquivoLote;
	}

	public void setLinhaArquivoLote(String linhaArquivoLote){

		this.linhaArquivoLote = linhaArquivoLote;
	}

	public boolean isStatusCritica(){

		return statusCritica;
	}

	public void setStatusCritica(boolean statusCritica){

		this.statusCritica = statusCritica;
	}

	public DiametroRedeAgua getDiametroRedeAgua(){

		return diametroRedeAgua;
	}

	public void setDiametroRedeAgua(DiametroRedeAgua diametroRedeAgua){

		this.diametroRedeAgua = diametroRedeAgua;
	}

	public Double getProfundidadeRede(){

		return profundidadeRede;
	}

	public void setProfundidadeRede(Double profundidadeRede){

		this.profundidadeRede = profundidadeRede;
	}

	public Double getLarguraVala(){

		return larguraVala;
	}

	public void setLarguraVala(Double larguraVala){

		this.larguraVala = larguraVala;
	}

	public Double getProfundidadeVala(){

		return profundidadeVala;
	}

	public void setProfundidadeVala(Double profundidadeVala){

		this.profundidadeVala = profundidadeVala;
	}

	public Double getComprimento(){

		return comprimento;
	}

	public void setComprimento(Double comprimento){

		this.comprimento = comprimento;
	}

	public Double getPressao(){

		return pressao;
	}

	public void setPressao(Double pressao){

		this.pressao = pressao;
	}

	public Integer getIndicadorValaEntulho(){

		return indicadorValaEntulho;
	}

	public void setIndicadorValaEntulho(Integer indicadorValaEntulho){

		this.indicadorValaEntulho = indicadorValaEntulho;
	}

	public Integer getIndicadorValaAterrada(){

		return indicadorValaAterrada;
	}

	public void setIndicadorValaAterrada(Integer indicadorValaAterrada){

		this.indicadorValaAterrada = indicadorValaAterrada;
	}

	public LocalOcorrencia getLocalOcorrencia(){

		return localOcorrencia;
	}

	public void setLocalOcorrencia(LocalOcorrencia localOcorrencia){

		this.localOcorrencia = localOcorrencia;
	}

	public PavimentoRua getPavimentoRua(){

		return pavimentoRua;
	}

	public void setPavimentoRua(PavimentoRua pavimentoRua){

		this.pavimentoRua = pavimentoRua;
	}

	public MaterialRedeAgua getMaterialRedeAgua(){

		return materialRedeAgua;
	}

	public void setMaterialRedeAgua(MaterialRedeAgua materialRedeAgua){

		this.materialRedeAgua = materialRedeAgua;
	}

	public String getIndicadorInsSubHidrometro(){

		return indicadorInsSubHidrometro;
	}

	public void setIndicadorInsSubHidrometro(String indicadorInsSubHidrometro){

		this.indicadorInsSubHidrometro = indicadorInsSubHidrometro;
	}

	public IntegracaoComercialHelper getIntegracaoComercialHelper(){

		return integracaoComercialHelper;
	}

	public void setIntegracaoComercialHelper(IntegracaoComercialHelper integracaoComercialHelper){

		this.integracaoComercialHelper = integracaoComercialHelper;
	}

	public boolean getIndicadorVerificacaoIrregularidades(){

		return indicadorVerificacaoIrregularidades;
	}

	public void setIndicadorVerificacaoIrregularidades(boolean indicadorVerificacaoIrregularidades){

		this.indicadorVerificacaoIrregularidades = indicadorVerificacaoIrregularidades;
	}

}