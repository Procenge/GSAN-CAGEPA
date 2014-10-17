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
 * Ailton Francisco de Sousa Junior
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

import gcom.atendimentopublico.ordemservico.OcorrenciaInfracao;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cobranca.InfracaoImovelSituacao;
import gcom.cobranca.InfracaoLigacaoSituacao;

import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UCXXXX] INSERIR DADOS NO ENCERRAMENTO DA O.S.
 * 
 * @author Ailton Sousa
 * @date 16/05/2011
 */
public class InserirDadosEncerramentoOrdemServicoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String nnDoctoInfracao;

	private String nnAnoDoctoInfracao;

	private Integer[] infracaoTipo;

	private String infracaoImovelSituacao;

	private String infracaoLigacaoSituacao;

	private String funcionario;

	private String nomeFuncionario;

	private String numeroOrdemServico;

	public String getNnDoctoInfracao(){

		return nnDoctoInfracao;
	}

	public void setNnDoctoInfracao(String nnDoctoInfracao){

		this.nnDoctoInfracao = nnDoctoInfracao;
	}

	public String getNnAnoDoctoInfracao(){

		return nnAnoDoctoInfracao;
	}

	public void setNnAnoDoctoInfracao(String nnAnoDoctoInfracao){

		this.nnAnoDoctoInfracao = nnAnoDoctoInfracao;
	}

	public Integer[] getInfracaoTipo(){

		return infracaoTipo;
	}

	public void setInfracaoTipo(Integer[] infracaoTipo){

		this.infracaoTipo = infracaoTipo;
	}

	public String getInfracaoImovelSituacao(){

		return infracaoImovelSituacao;
	}

	public void setInfracaoImovelSituacao(String infracaoImovelSituacao){

		this.infracaoImovelSituacao = infracaoImovelSituacao;
	}

	public String getInfracaoLigacaoSituacao(){

		return infracaoLigacaoSituacao;
	}

	public void setInfracaoLigacaoSituacao(String infracaoLigacaoSituacao){

		this.infracaoLigacaoSituacao = infracaoLigacaoSituacao;
	}

	public String getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(String funcionario){

		this.funcionario = funcionario;
	}

	public String getNomeFuncionario(){

		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario){

		this.nomeFuncionario = nomeFuncionario;
	}

	public String getNumeroOrdemServico(){

		return numeroOrdemServico;
	}

	public void setNumeroOrdemServico(String numeroOrdemServico){

		this.numeroOrdemServico = numeroOrdemServico;
	}

	public OcorrenciaInfracao setFormValues(OcorrenciaInfracao ocorrenciaInfracao){

		// Metodo usado para setar todos os valores do Form na base de dados

		ocorrenciaInfracao.setNnDoctoInfracao(Integer.valueOf(getNnDoctoInfracao()));
		ocorrenciaInfracao.setNnAnoDoctoInfracao(Integer.valueOf(getNnAnoDoctoInfracao()));

		if(getInfracaoImovelSituacao() != null && !getInfracaoImovelSituacao().equals("")){
			InfracaoImovelSituacao infracaoImovelSituacao = new InfracaoImovelSituacao();
			infracaoImovelSituacao.setId(Integer.parseInt(getInfracaoImovelSituacao()));
			ocorrenciaInfracao.setInfracaoImovelSituacao(infracaoImovelSituacao);
		}

		if(getInfracaoLigacaoSituacao() != null && !getInfracaoLigacaoSituacao().equals("")){
			InfracaoLigacaoSituacao infracaoLigacaoSituacao = new InfracaoLigacaoSituacao();
			infracaoLigacaoSituacao.setId(Integer.parseInt(getInfracaoLigacaoSituacao()));
			ocorrenciaInfracao.setInfracaoLigacaoSituacao(infracaoLigacaoSituacao);
		}

		if(getFuncionario() != null && !getFuncionario().equals("")){
			Funcionario funcionario = new Funcionario();
			funcionario.setId(Integer.parseInt(getFuncionario()));
			funcionario.setNome(getNomeFuncionario());
			ocorrenciaInfracao.setFuncionario(funcionario);
		}

		if(getNumeroOrdemServico() != null && !getNumeroOrdemServico().equals("")){
			OrdemServico ordemServico = new OrdemServico();
			ordemServico.setId(Integer.parseInt(getNumeroOrdemServico()));
			ocorrenciaInfracao.setOrdemServico(ordemServico);
		}

		ocorrenciaInfracao.setUltimaAlteracao(new Date());

		return ocorrenciaInfracao;
	}

	public void setValuesForm(OcorrenciaInfracao ocorrenciaInfracao, Integer[] ocorrenciasTipo){

		// Metodo usado para setar todos os valores da ocorrencia no Form

		setNnDoctoInfracao(ocorrenciaInfracao.getNnDoctoInfracao().toString());
		setNnAnoDoctoInfracao(ocorrenciaInfracao.getNnAnoDoctoInfracao().toString());
		setInfracaoImovelSituacao(ocorrenciaInfracao.getInfracaoImovelSituacao().getId().toString());
		setInfracaoLigacaoSituacao(ocorrenciaInfracao.getInfracaoLigacaoSituacao().getId().toString());
		if(ocorrenciaInfracao.getFuncionario() != null){
			setFuncionario(ocorrenciaInfracao.getFuncionario().getId().toString());
			setNomeFuncionario(ocorrenciaInfracao.getFuncionario().getNome().toString());
		}
		setNumeroOrdemServico(ocorrenciaInfracao.getOrdemServico().getId().toString());
		setInfracaoTipo(ocorrenciasTipo);
	}

}
