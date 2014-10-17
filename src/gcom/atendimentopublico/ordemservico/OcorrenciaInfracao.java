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

package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.funcionario.Funcionario;
import gcom.cobranca.InfracaoImovelSituacao;
import gcom.cobranca.InfracaoLigacaoSituacao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

/**
 * Ocorrência de Infração
 * 
 * @author Ailton Junior
 * @date 13/05/2011
 */
public class OcorrenciaInfracao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Integer nnDoctoInfracao;

	/** persistent field */
	private Integer nnAnoDoctoInfracao;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private OrdemServico ordemServico;

	/** persistent field */
	private InfracaoImovelSituacao infracaoImovelSituacao;

	/** persistent field */
	private InfracaoLigacaoSituacao infracaoLigacaoSituacao;

	/** persistent field */
	private Funcionario funcionario;

	/** full constructor */
	public OcorrenciaInfracao(Integer nnDoctoInfracao, Integer nnAnoDoctoInfracao, Date ultimaAlteracao, OrdemServico ordemServico,
								InfracaoImovelSituacao infracaoImovelSituacao, InfracaoLigacaoSituacao infracaoLigacaoSituacao,
								Funcionario funcionario) {

		this.nnDoctoInfracao = nnDoctoInfracao;
		this.nnAnoDoctoInfracao = nnAnoDoctoInfracao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.ordemServico = ordemServico;
		this.infracaoImovelSituacao = infracaoImovelSituacao;
		this.infracaoLigacaoSituacao = infracaoLigacaoSituacao;
		this.funcionario = funcionario;
	}

	/** default constructor */
	public OcorrenciaInfracao() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getNnDoctoInfracao(){

		return nnDoctoInfracao;
	}

	public void setNnDoctoInfracao(Integer nnDoctoInfracao){

		this.nnDoctoInfracao = nnDoctoInfracao;
	}

	public Integer getNnAnoDoctoInfracao(){

		return nnAnoDoctoInfracao;
	}

	public void setNnAnoDoctoInfracao(Integer nnAnoDoctoInfracao){

		this.nnAnoDoctoInfracao = nnAnoDoctoInfracao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public OrdemServico getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	public InfracaoImovelSituacao getInfracaoImovelSituacao(){

		return infracaoImovelSituacao;
	}

	public void setInfracaoImovelSituacao(InfracaoImovelSituacao infracaoImovelSituacao){

		this.infracaoImovelSituacao = infracaoImovelSituacao;
	}

	public InfracaoLigacaoSituacao getInfracaoLigacaoSituacao(){

		return infracaoLigacaoSituacao;
	}

	public void setInfracaoLigacaoSituacao(InfracaoLigacaoSituacao infracaoLigacaoSituacao){

		this.infracaoLigacaoSituacao = infracaoLigacaoSituacao;
	}

	public Funcionario getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroOcorrenciaInfracao filtroOcorrenciaInfracao = new FiltroOcorrenciaInfracao();

		filtroOcorrenciaInfracao.adicionarCaminhoParaCarregamentoEntidade("ordemServico");
		filtroOcorrenciaInfracao.adicionarCaminhoParaCarregamentoEntidade("infracaoImovelSituacao");
		filtroOcorrenciaInfracao.adicionarCaminhoParaCarregamentoEntidade("infracaoLigacaoSituacao");
		filtroOcorrenciaInfracao.adicionarCaminhoParaCarregamentoEntidade("funcionario");

		filtroOcorrenciaInfracao.adicionarParametro(new ParametroSimples(FiltroOcorrenciaInfracao.ID, this.getId()));
		return filtroOcorrenciaInfracao;
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
		if(!(obj instanceof OcorrenciaInfracao)) return false;
		final OcorrenciaInfracao other = (OcorrenciaInfracao) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}
}
