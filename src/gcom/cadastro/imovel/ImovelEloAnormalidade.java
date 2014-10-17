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
public class ImovelEloAnormalidade
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private Date dataAnormalidade;

	/** nullable persistent field */
	private byte[] fotoAnormalidade;

	/** nullable persistent field */
	private Blob fotoAnormalidadeBlob;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private gcom.cadastro.imovel.EloAnormalidade eloAnormalidade;

	/** persistent field */
	private gcom.cadastro.imovel.Imovel imovel;

	/** persistent field */
	private gcom.cadastro.funcionario.Funcionario funcionario;

	public Date getDataAnormalidade(){

		return dataAnormalidade;
	}

	public ImovelEloAnormalidade() {

	}

	public ImovelEloAnormalidade(Integer id, Date dataAnormalidade, byte[] fotoAnormalidade, Date ultimaAlteracao,
									EloAnormalidade eloAnormalidade, Imovel imovel, Funcionario funcionario) {

		// TODO Auto-generated constructor stub
		this.id = id;
		this.dataAnormalidade = dataAnormalidade;
		this.fotoAnormalidade = fotoAnormalidade;
		this.ultimaAlteracao = ultimaAlteracao;
		this.eloAnormalidade = eloAnormalidade;
		this.imovel = imovel;
		this.funcionario = funcionario;
	}

	public void setDataAnormalidade(Date dataAnormalidade){

		this.dataAnormalidade = dataAnormalidade;
	}

	public gcom.cadastro.imovel.EloAnormalidade getEloAnormalidade(){

		return eloAnormalidade;
	}

	public void setEloAnormalidade(gcom.cadastro.imovel.EloAnormalidade eloAnormalidade){

		this.eloAnormalidade = eloAnormalidade;
	}

	public byte[] getFotoAnormalidade(){

		return fotoAnormalidade;
	}

	/**
	 * the FotoAnormalidadeBlob to get
	 * Apenas para o Tratamento de Blobs do Hibernate, n�o invocar.
	 */
	public byte[] getFotoAnormalidadeBlob(){

		return this.fotoAnormalidade;
	}

	/**
	 * @param fotoAnormalidadeBlob
	 *            the fotoAnormalidadeBlob to set
	 *            Apenas para o Tratamento de Blobs do Hibernate, n�o invocar.
	 */
	public void setFotoAnormalidadeBlob(Blob fotoAnormalidadeBlob){

		this.fotoAnormalidade = IoUtil.toByteArray(fotoAnormalidadeBlob);
	}

	public void setFotoAnormalidade(byte[] fotoAnormalidade){

		this.fotoAnormalidade = fotoAnormalidade;
		this.popularFotoAnormalidadeBlob();
	}

	private void popularFotoAnormalidadeBlob(){

		if(this.getFotoAnormalidadeBlob() != null){
			this.fotoAnormalidadeBlob = Hibernate.createBlob(this.getFotoAnormalidadeBlob());
		}else{
			this.fotoAnormalidadeBlob = null;
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

		FiltroImovelEloAnormalidade filtroImovelEloAnormalidade = new FiltroImovelEloAnormalidade();

		filtroImovelEloAnormalidade.adicionarCaminhoParaCarregamentoEntidade("funcionario");
		filtroImovelEloAnormalidade.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroImovelEloAnormalidade.adicionarCaminhoParaCarregamentoEntidade("eloAnormalidade");

		filtroImovelEloAnormalidade.adicionarParametro(new ParametroSimples(FiltroImovelEloAnormalidade.ID, this.getId()));
		return filtroImovelEloAnormalidade;
	}

	public Funcionario getFuncionario(){

		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario){

		this.funcionario = funcionario;
	}

}
