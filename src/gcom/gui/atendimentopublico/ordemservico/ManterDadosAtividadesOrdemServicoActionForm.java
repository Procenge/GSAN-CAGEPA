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

package gcom.gui.atendimentopublico.ordemservico;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Esta classe tem por finalidade gerar o formulário que receberá os parâmetros
 * para realização da atualização do dados das atividades de uma OS
 * 
 * @author Raphael Rossiter
 * @date 15/09/2006
 */
public class ManterDadosAtividadesOrdemServicoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String numeroOSForm;

	private String dataRoteiroForm;

	private String dataEncerramentoForm;

	private String idAtividade;

	private String descricaoAtividade;

	private String caminhoRetorno;

	private String idEquipe;

	private String placaVeiculo;

	// Deslocamento
	private String kmInicial;

	private String kmFinal;

	private String dataInicioInterrupcao;

	private String horaInicioInterrupcao;

	private String dataFimInterrupcao;

	private String horaFimInterrupcao;

	private String kmInterrupcao;

	private String idMotivoInterrupcao;

	private String descricaoMotivoInterrupcao;

	private String dataInicioDeslocamento;

	private String dataFimDeslocamento;

	private String horaInicioDeslocamento;

	private String horaFimDeslocamento;

	// Vala
	private String numeroVala;

	private String idLocalOcorrencia;

	private String idPavimento;

	private String comprimentoVala;

	private String larguraVala;

	private String profundidadeVala;

	private String indicadorValaAterrada;

	private String indicadorEntulho;

	private String descricaoLocalOcorrencia;

	private String descricaoPavimento;

	// Rede/Ramal Água
	private String idCausaAgua;

	private String idRedeRamalAgua;

	private String idDiametroAgua;

	private String idMaterialAgua;

	private String profundidadeAgua;

	private String pressaoAgua;

	private String idAgenteExternoAgua;

	// Rede/Ramal Esgoto
	private String idCausaEsgoto;

	private String idRedeRamalEsgoto;

	private String idDiametroEsgoto;

	private String idMaterialEsgoto;

	private String profundidadeEsgoto;

	private String pressaoEsgoto;

	private String idAgenteExternoEsgoto;

	public String getIdCausaAgua(){

		return idCausaAgua;
	}

	public void setIdCausaAgua(String idCausaAgua){

		this.idCausaAgua = idCausaAgua;
	}

	public String getIdRedeRamalAgua(){

		return idRedeRamalAgua;
	}

	public void setIdRedeRamalAgua(String idRedeRamalAgua){

		this.idRedeRamalAgua = idRedeRamalAgua;
	}

	public String getIdDiametroAgua(){

		return idDiametroAgua;
	}

	public void setIdDiametroAgua(String idDiametroAgua){

		this.idDiametroAgua = idDiametroAgua;
	}

	public String getIdMaterialAgua(){

		return idMaterialAgua;
	}

	public void setIdMaterialAgua(String idMaterialAgua){

		this.idMaterialAgua = idMaterialAgua;
	}

	public String getProfundidadeAgua(){

		return profundidadeAgua;
	}

	public void setProfundidadeAgua(String profundidadeAgua){

		this.profundidadeAgua = profundidadeAgua;
	}

	public String getPressaoAgua(){

		return pressaoAgua;
	}

	public void setPressaoAgua(String pressaoAgua){

		this.pressaoAgua = pressaoAgua;
	}

	public String getIdAgenteExternoAgua(){

		return idAgenteExternoAgua;
	}

	public void setIdAgenteExternoAgua(String idAgenteExternoAgua){

		this.idAgenteExternoAgua = idAgenteExternoAgua;
	}

	public String getIdCausaEsgoto(){

		return idCausaEsgoto;
	}

	public void setIdCausaEsgoto(String idCausaEsgoto){

		this.idCausaEsgoto = idCausaEsgoto;
	}

	public String getIdRedeRamalEsgoto(){

		return idRedeRamalEsgoto;
	}

	public void setIdRedeRamalEsgoto(String idRedeRamalEsgoto){

		this.idRedeRamalEsgoto = idRedeRamalEsgoto;
	}

	public String getIdDiametroEsgoto(){

		return idDiametroEsgoto;
	}

	public void setIdDiametroEsgoto(String idDiametroEsgoto){

		this.idDiametroEsgoto = idDiametroEsgoto;
	}

	public String getIdMaterialEsgoto(){

		return idMaterialEsgoto;
	}

	public void setIdMaterialEsgoto(String idMaterialEsgoto){

		this.idMaterialEsgoto = idMaterialEsgoto;
	}

	public String getProfundidadeEsgoto(){

		return profundidadeEsgoto;
	}

	public void setProfundidadeEsgoto(String profundidadeEsgoto){

		this.profundidadeEsgoto = profundidadeEsgoto;
	}

	public String getPressaoEsgoto(){

		return pressaoEsgoto;
	}

	public void setPressaoEsgoto(String pressaoEsgoto){

		this.pressaoEsgoto = pressaoEsgoto;
	}

	public String getIdAgenteExternoEsgoto(){

		return idAgenteExternoEsgoto;
	}

	public void setIdAgenteExternoEsgoto(String idAgenteExternoEsgoto){

		this.idAgenteExternoEsgoto = idAgenteExternoEsgoto;
	}

	public String getNumeroOSForm(){

		return numeroOSForm;
	}

	public void setNumeroOSForm(String numeroOSForm){

		this.numeroOSForm = numeroOSForm;
	}

	public String getDataRoteiroForm(){

		return dataRoteiroForm;
	}

	public void setDataRoteiroForm(String dataRoteiroForm){

		this.dataRoteiroForm = dataRoteiroForm;
	}

	public String getDataEncerramentoForm(){

		return dataEncerramentoForm;
	}

	public void setDataEncerramentoForm(String dataEncerramentoForm){

		this.dataEncerramentoForm = dataEncerramentoForm;
	}

	public String getIdAtividade(){

		return idAtividade;
	}

	public void setIdAtividade(String idAtividade){

		this.idAtividade = idAtividade;
	}

	public String getDescricaoAtividade(){

		return descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade){

		this.descricaoAtividade = descricaoAtividade;
	}

	public String getCaminhoRetorno(){

		return caminhoRetorno;
	}

	public void setCaminhoRetorno(String caminhoRetorno){

		this.caminhoRetorno = caminhoRetorno;
	}

	public String getKmInicial(){

		return kmInicial;
	}

	public void setKmInicial(String kmInicial){

		this.kmInicial = kmInicial;
	}

	public String getKmFinal(){

		return kmFinal;
	}

	public void setKmFinal(String kmFinal){

		this.kmFinal = kmFinal;
	}

	public String getDataInicioInterrupcao(){

		return dataInicioInterrupcao;
	}

	public void setDataInicioInterrupcao(String dataInicioInterrupcao){

		this.dataInicioInterrupcao = dataInicioInterrupcao;
	}

	public String getHoraInicioInterrupcao(){

		return horaInicioInterrupcao;
	}

	public void setHoraInicioInterrupcao(String horaInicioInterrupcao){

		this.horaInicioInterrupcao = horaInicioInterrupcao;
	}

	public String getDataFimInterrupcao(){

		return dataFimInterrupcao;
	}

	public void setDataFimInterrupcao(String dataFimInterrupcao){

		this.dataFimInterrupcao = dataFimInterrupcao;
	}

	public String getHoraFimInterrupcao(){

		return horaFimInterrupcao;
	}

	public void setHoraFimInterrupcao(String horaFimInterrupcao){

		this.horaFimInterrupcao = horaFimInterrupcao;
	}

	public String getKmInterrupcao(){

		return kmInterrupcao;
	}

	public void setKmInterrupcao(String kmInterrupcao){

		this.kmInterrupcao = kmInterrupcao;
	}

	public String getIdMotivoInterrupcao(){

		return idMotivoInterrupcao;
	}

	public void setIdMotivoInterrupcao(String idMotivoInterrupcao){

		this.idMotivoInterrupcao = idMotivoInterrupcao;
	}

	public String getDescricaoMotivoInterrupcao(){

		return descricaoMotivoInterrupcao;
	}

	public void setDescricaoMotivoInterrupcao(String descricaoMotivoInterrupcao){

		this.descricaoMotivoInterrupcao = descricaoMotivoInterrupcao;
	}

	public String getNumeroVala(){

		return numeroVala;
	}

	public void setNumeroVala(String numeroVala){

		this.numeroVala = numeroVala;
	}

	public String getIdLocalOcorrencia(){

		return idLocalOcorrencia;
	}

	public void setIdLocalOcorrencia(String idLocalOcorrencia){

		this.idLocalOcorrencia = idLocalOcorrencia;
	}

	public String getIdPavimento(){

		return idPavimento;
	}

	public void setIdPavimento(String idPavimento){

		this.idPavimento = idPavimento;
	}

	public String getComprimentoVala(){

		return comprimentoVala;
	}

	public void setComprimentoVala(String comprimentoVala){

		this.comprimentoVala = comprimentoVala;
	}

	public String getLarguraVala(){

		return larguraVala;
	}

	public void setLarguraVala(String larguraVala){

		this.larguraVala = larguraVala;
	}

	public String getProfundidadeVala(){

		return profundidadeVala;
	}

	public void setProfundidadeVala(String profundidadeVala){

		this.profundidadeVala = profundidadeVala;
	}

	public String getIndicadorValaAterrada(){

		return indicadorValaAterrada;
	}

	public void setIndicadorValaAterrada(String indicadorValaAterrada){

		this.indicadorValaAterrada = indicadorValaAterrada;
	}

	public String getIndicadorEntulho(){

		return indicadorEntulho;
	}

	public void setIndicadorEntulho(String indicadorEntulho){

		this.indicadorEntulho = indicadorEntulho;
	}

	public String getDescricaoLocalOcorrencia(){

		return descricaoLocalOcorrencia;
	}

	public void setDescricaoLocalOcorrencia(String descricaoLocalOcorrencia){

		this.descricaoLocalOcorrencia = descricaoLocalOcorrencia;
	}

	public String getDescricaoPavimento(){

		return descricaoPavimento;
	}

	public void setDescricaoPavimento(String descricaoPavimento){

		this.descricaoPavimento = descricaoPavimento;
	}

	public String getDataInicioDeslocamento(){

		return dataInicioDeslocamento;
	}

	public void setDataInicioDeslocamento(String dataInicioDeslocamento){

		this.dataInicioDeslocamento = dataInicioDeslocamento;
	}

	public String getDataFimDeslocamento(){

		return dataFimDeslocamento;
	}

	public void setDataFimDeslocamento(String dataFimDeslocamento){

		this.dataFimDeslocamento = dataFimDeslocamento;
	}

	public String getHoraInicioDeslocamento(){

		return horaInicioDeslocamento;
	}

	public void setHoraInicioDeslocamento(String horaInicioDeslocamento){

		this.horaInicioDeslocamento = horaInicioDeslocamento;
	}

	public String getHoraFimDeslocamento(){

		return horaFimDeslocamento;
	}

	public void setHoraFimDeslocamento(String horaFimDeslocamento){

		this.horaFimDeslocamento = horaFimDeslocamento;
	}

	public String getIdEquipe(){

		return idEquipe;
	}

	public void setIdEquipe(String idEquipe){

		this.idEquipe = idEquipe;
	}

	public String getPlacaVeiculo(){

		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo){

		this.placaVeiculo = placaVeiculo;
	}

}
