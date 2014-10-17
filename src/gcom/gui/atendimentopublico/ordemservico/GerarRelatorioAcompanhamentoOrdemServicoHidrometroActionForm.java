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
 * Ivan Sérgio Virginio da Silva Júnior
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
 * [UC0732] Gerar Relatorio Acompanhamento Ordem de Servico Hidrometro
 * 
 * @author Ivan Sérgio
 * @date 04/12/2007
 */
public class GerarRelatorioAcompanhamentoOrdemServicoHidrometroActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String tipoOrdem;

	private String situacaoOrdemServico;

	private String firma;

	private String nomeFirma;

	private String localidade;

	private String nomeLocalidade;

	private String dataEncerramentoInicial;

	private String dataEncerramentoFinal;

	private String tipoRelatorioAcompanhamento;

	public static final String TIPO_ORDEM_INSTALACAO = "INSTALACAO";

	public static final String TIPO_ORDEM_SUBSTITUICAO = "SUBSTITUICAO";

	public static final String SITUACAO_ORDEM_SERVICO_ENCERRADAS = "ENCERRADAS";

	public static final String SITUACAO_ORDEM_SERVICO_PENDENTES = "PENDENTES";

	public static final Integer TIPO_RELATORIO_ANALITICO = 1;

	public static final Integer TIPO_RELATORIO_SINTETICO = 2;

	public String getDataEncerramentoFinal(){

		return dataEncerramentoFinal;
	}

	public void setDataEncerramentoFinal(String dataEncerramentoFinal){

		this.dataEncerramentoFinal = dataEncerramentoFinal;
	}

	public String getDataEncerramentoInicial(){

		return dataEncerramentoInicial;
	}

	public void setDataEncerramentoInicial(String dataEncerramentoInicial){

		this.dataEncerramentoInicial = dataEncerramentoInicial;
	}

	public String getFirma(){

		return firma;
	}

	public void setFirma(String firma){

		this.firma = firma;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getNomeFirma(){

		return nomeFirma;
	}

	public void setNomeFirma(String nomeFirma){

		this.nomeFirma = nomeFirma;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getSituacaoOrdemServico(){

		return situacaoOrdemServico;
	}

	public void setSituacaoOrdemServico(String situacaoOrdemServico){

		this.situacaoOrdemServico = situacaoOrdemServico;
	}

	public String getTipoOrdem(){

		return tipoOrdem;
	}

	public void setTipoOrdem(String tipoOrdem){

		this.tipoOrdem = tipoOrdem;
	}

	public String gettipoRelatorioAcompanhamento(){

		return tipoRelatorioAcompanhamento;
	}

	public void settipoRelatorioAcompanhamento(String tipoRelatorioAcompanhamento){

		this.tipoRelatorioAcompanhamento = tipoRelatorioAcompanhamento;
	}
}