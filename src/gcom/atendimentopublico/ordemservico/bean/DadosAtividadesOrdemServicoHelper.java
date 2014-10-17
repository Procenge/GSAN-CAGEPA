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

package gcom.atendimentopublico.ordemservico.bean;

import gcom.atendimentopublico.ordemservico.AgenteExterno;
import gcom.atendimentopublico.ordemservico.CausaVazamento;
import gcom.atendimentopublico.ordemservico.DiametroCavaleteAgua;
import gcom.atendimentopublico.ordemservico.DiametroRamalAgua;
import gcom.atendimentopublico.ordemservico.DiametroRamalEsgoto;
import gcom.atendimentopublico.ordemservico.DiametroRedeAgua;
import gcom.atendimentopublico.ordemservico.DiametroRedeEsgoto;
import gcom.atendimentopublico.ordemservico.MaterialCavaleteAgua;
import gcom.atendimentopublico.ordemservico.MaterialRamalAgua;
import gcom.atendimentopublico.ordemservico.MaterialRamalEsgoto;
import gcom.atendimentopublico.ordemservico.MaterialRedeAgua;
import gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoDeslocamento;
import gcom.atendimentopublico.ordemservico.OrdemServicoInterrupcaoDeslocamento;
import gcom.atendimentopublico.ordemservico.OrdemServicoInterrupcaoExecucao;
import gcom.atendimentopublico.ordemservico.OrdemServicoProgramacao;
import gcom.atendimentopublico.ordemservico.OrdemServicoValaPavimento;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Classe com todos os dados das atividades da Ordem de Servico.
 */
public class DadosAtividadesOrdemServicoHelper {

	// Ordem de servico
	private OrdemServico ordemServico;

	// Ordem de servico Programacao
	private OrdemServicoProgramacao ordemServicoProgramacao;

	// Cole��o de Helpers que j� era usado para manter atividades, materiais e horas.
	private Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper;

	// Dados do deslocamento
	private OrdemServicoDeslocamento ordemServicoDeslocamento;

	// Cole��o de Interrup��es do deslocamento
	private Collection<OrdemServicoInterrupcaoDeslocamento> colecaoOrdemServicoInterrupcaoDeslocamento;

	// Cole��o de Interrup��es da execu��o
	private Collection<OrdemServicoInterrupcaoExecucao> colecaoOrdemServicoInterrupcaoExecucao;

	// Dados Rede/ Ramal �gua
	private CausaVazamento causaVazamentoAgua;

	private DiametroRedeAgua diametroRedeAgua;

	private DiametroCavaleteAgua diametroCavaleteAgua;

	private DiametroRamalAgua diametroRamalAgua;

	private MaterialRedeAgua materialRedeAgua;

	private MaterialCavaleteAgua materialCavaleteAgua;

	private MaterialRamalAgua materialRamalAgua;

	private AgenteExterno agenteExternoAgua;

	private BigDecimal profundidadeAgua;

	private BigDecimal pressaoAgua;

	private String idRedeRamalAgua;

	// Dados Rede/ Ramal Esgoto
	private CausaVazamento causaVazamentoEsgoto;

	private DiametroRedeEsgoto diametroRedeEsgoto;

	private DiametroRamalEsgoto diametroRamalEsgoto;

	private MaterialRedeEsgoto materialRedeEsgoto;

	private MaterialRamalEsgoto materialRamalEsgoto;

	private AgenteExterno agenteExternoEsgoto;

	private BigDecimal profundidadeEsgoto;

	private BigDecimal pressaoEsgoto;

	private String idRedeRamalEsgoto;

	// Cole��o de Valas
	private Collection<OrdemServicoValaPavimento> colecaoOrdemServicoValaPavimento;

	public Collection<ManterDadosAtividadesOrdemServicoHelper> getColecaoManterDadosAtividadesOrdemServicoHelper(){

		return colecaoManterDadosAtividadesOrdemServicoHelper;
	}

	public void setColecaoManterDadosAtividadesOrdemServicoHelper(
					Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper){

		this.colecaoManterDadosAtividadesOrdemServicoHelper = colecaoManterDadosAtividadesOrdemServicoHelper;
	}

	public OrdemServicoDeslocamento getOrdemServicoDeslocamento(){

		return ordemServicoDeslocamento;
	}

	public void setOrdemServicoDeslocamento(OrdemServicoDeslocamento ordemServicoDeslocamento){

		this.ordemServicoDeslocamento = ordemServicoDeslocamento;
	}

	public Collection<OrdemServicoInterrupcaoDeslocamento> getColecaoOrdemServicoInterrupcaoDeslocamento(){

		return colecaoOrdemServicoInterrupcaoDeslocamento;
	}

	public void setColecaoOrdemServicoInterrupcaoDeslocamento(
					Collection<OrdemServicoInterrupcaoDeslocamento> colecaoOrdemServicoInterrupcaoDeslocamento){

		this.colecaoOrdemServicoInterrupcaoDeslocamento = colecaoOrdemServicoInterrupcaoDeslocamento;
	}

	public CausaVazamento getCausaVazamentoAgua(){

		return causaVazamentoAgua;
	}

	public void setCausaVazamentoAgua(CausaVazamento causaVazamentoAgua){

		this.causaVazamentoAgua = causaVazamentoAgua;
	}

	public DiametroRedeAgua getDiametroRedeAgua(){

		return diametroRedeAgua;
	}

	public void setDiametroRedeAgua(DiametroRedeAgua diametroRedeAgua){

		this.diametroRedeAgua = diametroRedeAgua;
	}

	public DiametroRamalAgua getDiametroRamalAgua(){

		return diametroRamalAgua;
	}

	public void setDiametroRamalAgua(DiametroRamalAgua diametroRamalAgua){

		this.diametroRamalAgua = diametroRamalAgua;
	}

	public MaterialRedeAgua getMaterialRedeAgua(){

		return materialRedeAgua;
	}

	public void setMaterialRedeAgua(MaterialRedeAgua materialRedeAgua){

		this.materialRedeAgua = materialRedeAgua;
	}

	public MaterialRamalAgua getMaterialRamalAgua(){

		return materialRamalAgua;
	}

	public void setMaterialRamalAgua(MaterialRamalAgua materialRamalAgua){

		this.materialRamalAgua = materialRamalAgua;
	}

	public AgenteExterno getAgenteExternoAgua(){

		return agenteExternoAgua;
	}

	public void setAgenteExternoAgua(AgenteExterno agenteExternoAgua){

		this.agenteExternoAgua = agenteExternoAgua;
	}

	public BigDecimal getProfundidadeAgua(){

		return profundidadeAgua;
	}

	public void setProfundidadeAgua(BigDecimal profundidadeAgua){

		this.profundidadeAgua = profundidadeAgua;
	}

	public BigDecimal getPressaoAgua(){

		return pressaoAgua;
	}

	public void setPressaoAgua(BigDecimal pressaoAgua){

		this.pressaoAgua = pressaoAgua;
	}

	public CausaVazamento getCausaVazamentoEsgoto(){

		return causaVazamentoEsgoto;
	}

	public void setCausaVazamentoEsgoto(CausaVazamento causaVazamentoEsgoto){

		this.causaVazamentoEsgoto = causaVazamentoEsgoto;
	}

	public DiametroRedeEsgoto getDiametroRedeEsgoto(){

		return diametroRedeEsgoto;
	}

	public void setDiametroRedeEsgoto(DiametroRedeEsgoto diametroRedeEsgoto){

		this.diametroRedeEsgoto = diametroRedeEsgoto;
	}

	public DiametroRamalEsgoto getDiametroRamalEsgoto(){

		return diametroRamalEsgoto;
	}

	public void setDiametroRamalEsgoto(DiametroRamalEsgoto diametroRamalEsgoto){

		this.diametroRamalEsgoto = diametroRamalEsgoto;
	}

	public MaterialRedeEsgoto getMaterialRedeEsgoto(){

		return materialRedeEsgoto;
	}

	public void setMaterialRedeEsgoto(MaterialRedeEsgoto materialRedeEsgoto){

		this.materialRedeEsgoto = materialRedeEsgoto;
	}

	public MaterialRamalEsgoto getMaterialRamalEsgoto(){

		return materialRamalEsgoto;
	}

	public void setMaterialRamalEsgoto(MaterialRamalEsgoto materialRamalEsgoto){

		this.materialRamalEsgoto = materialRamalEsgoto;
	}

	public AgenteExterno getAgenteExternoEsgoto(){

		return agenteExternoEsgoto;
	}

	public void setAgenteExternoEsgoto(AgenteExterno agenteExternoEsgoto){

		this.agenteExternoEsgoto = agenteExternoEsgoto;
	}

	public BigDecimal getProfundidadeEsgoto(){

		return profundidadeEsgoto;
	}

	public void setProfundidadeEsgoto(BigDecimal profundidadeEsgoto){

		this.profundidadeEsgoto = profundidadeEsgoto;
	}

	public BigDecimal getPressaoEsgoto(){

		return pressaoEsgoto;
	}

	public void setPressaoEsgoto(BigDecimal pressaoEsgoto){

		this.pressaoEsgoto = pressaoEsgoto;
	}

	public Collection<OrdemServicoValaPavimento> getColecaoOrdemServicoValaPavimento(){

		return colecaoOrdemServicoValaPavimento;
	}

	public void setColecaoOrdemServicoValaPavimento(Collection<OrdemServicoValaPavimento> colecaoOrdemServicoValaPavimento){

		this.colecaoOrdemServicoValaPavimento = colecaoOrdemServicoValaPavimento;
	}

	public OrdemServico getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	public String getIdRedeRamalAgua(){

		return idRedeRamalAgua;
	}

	public void setIdRedeRamalAgua(String idRedeRamalAgua){

		this.idRedeRamalAgua = idRedeRamalAgua;
	}

	public String getIdRedeRamalEsgoto(){

		return idRedeRamalEsgoto;
	}

	public void setIdRedeRamalEsgoto(String idRedeRamalEsgoto){

		this.idRedeRamalEsgoto = idRedeRamalEsgoto;
	}

	public Collection<OrdemServicoInterrupcaoExecucao> getColecaoOrdemServicoInterrupcaoExecucao(){

		return colecaoOrdemServicoInterrupcaoExecucao;
	}

	public void setColecaoOrdemServicoInterrupcaoExecucao(Collection<OrdemServicoInterrupcaoExecucao> colecaoOrdemServicoInterrupcaoExecucao){

		this.colecaoOrdemServicoInterrupcaoExecucao = colecaoOrdemServicoInterrupcaoExecucao;
	}

	public OrdemServicoProgramacao getOrdemServicoProgramacao(){

		return ordemServicoProgramacao;
	}

	public void setOrdemServicoProgramacao(OrdemServicoProgramacao ordemServicoProgramacao){

		this.ordemServicoProgramacao = ordemServicoProgramacao;
	}

	public DiametroCavaleteAgua getDiametroCavaleteAgua(){

		return diametroCavaleteAgua;
	}

	public void setDiametroCavaleteAgua(DiametroCavaleteAgua diametroCavaleteAgua){

		this.diametroCavaleteAgua = diametroCavaleteAgua;
	}

	public MaterialCavaleteAgua getMaterialCavaleteAgua(){

		return materialCavaleteAgua;
	}

	public void setMaterialCavaleteAgua(MaterialCavaleteAgua materialCavaleteAgua){

		this.materialCavaleteAgua = materialCavaleteAgua;
	}

}
