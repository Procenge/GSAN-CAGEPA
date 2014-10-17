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
 * Vitor Hora
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

package gcom.cadastro.imovel;

import gcom.cadastro.funcionario.Funcionario;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.IoUtil;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.sql.Blob;
import java.util.Date;

import org.hibernate.Hibernate;

/** @author Hibernate CodeGenerator */
public class ImovelCadastroOcorrencia
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Date dataOcorrencia;

	/** nullable persistent field */
	private byte[] fotoOcorrencia;

	/** nullable persistent field */
	private Blob fotoOcorrenciaBlob;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private gcom.cadastro.imovel.Imovel imovel;

	/** persistent field */
	private gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia;

	/** persistent field */
	private Funcionario funcionario;

	public ImovelCadastroOcorrencia() {

		super();
		// TODO Auto-generated constructor stub
	}

	public ImovelCadastroOcorrencia(Integer id, Date dataOcorrencia, byte[] fotoOcorrencia, Date ultimaAlteracao, Imovel imovel,
									CadastroOcorrencia cadastroOcorrencia, Funcionario funcionario) {

		super();
		this.id = id;
		this.dataOcorrencia = dataOcorrencia;
		this.fotoOcorrencia = fotoOcorrencia;
		this.ultimaAlteracao = ultimaAlteracao;
		this.imovel = imovel;
		this.cadastroOcorrencia = cadastroOcorrencia;
		this.funcionario = funcionario;
	}

	public gcom.cadastro.imovel.CadastroOcorrencia getCadastroOcorrencia(){

		return cadastroOcorrencia;
	}

	public void setCadastroOcorrencia(gcom.cadastro.imovel.CadastroOcorrencia cadastroOcorrencia){

		this.cadastroOcorrencia = cadastroOcorrencia;
	}

	public Date getDataOcorrencia(){

		return dataOcorrencia;
	}

	public void setDataOcorrencia(Date dataOcorrencia){

		this.dataOcorrencia = dataOcorrencia;
	}

	public byte[] getFotoOcorrencia(){

		return fotoOcorrencia;
	}

	/**
	 * the FotoOcorrenciaBlob to get
	 * Apenas para o Tratamento de Blobs do Hibernate, não invocar.
	 */
	public byte[] getFotoOcorrenciaBlob(){

		return this.fotoOcorrencia;
	}

	public void setFotoOcorrencia(byte[] fotoOcorrencia){

		this.fotoOcorrencia = fotoOcorrencia;
		this.popularFotoOcorrenciaBlob();
	}

	/**
	 * @param fotoOcorrenciaBlob
	 *            the FotoOcorrenciaBlob to set
	 *            Apenas para o Tratamento de Blobs do Hibernate, não invocar.
	 */
	public void setFotoOcorrenciaBlob(Blob fotoOcorrenciaBlob){

		this.fotoOcorrencia = IoUtil.toByteArray(fotoOcorrenciaBlob);
	}

	private void popularFotoOcorrenciaBlob(){

		if(this.getFotoOcorrenciaBlob() != null){
			this.fotoOcorrenciaBlob = Hibernate.createBlob(this.getFotoOcorrenciaBlob());
		}else{
			this.fotoOcorrenciaBlob = null;
		}
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public gcom.cadastro.imovel.Imovel getImovel(){

		return imovel;
	}

	public void setImovel(gcom.cadastro.imovel.Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the funcionario
	 */
	public Funcionario getFuncionario(){

		return funcionario;
	}

	/**
	 * @param funcionario
	 *            the funcionario to set
	 */
	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroImovelCadastroOcorrencia filtroImovelCadastroOcorrencia = new FiltroImovelCadastroOcorrencia();

		filtroImovelCadastroOcorrencia.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroImovelCadastroOcorrencia.adicionarCaminhoParaCarregamentoEntidade("cadastroOcorrencia");
		filtroImovelCadastroOcorrencia.adicionarCaminhoParaCarregamentoEntidade("funcionario");
		filtroImovelCadastroOcorrencia.adicionarParametro(new ParametroSimples(FiltroImovelCadastroOcorrencia.ID, this.getId()));
		return filtroImovelCadastroOcorrencia;
	}

}
