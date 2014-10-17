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
 * GSANPCG
 Vitor Hora
 */

package gcom.cadastro.imovel;

import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.faturamento.FaturamentoGrupo;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.Rota;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

/**
 * Representa um registro de alteração da inscrição do imóvel
 * 
 * @created 17/01/2013
 * @author Luciano Galvão
 */
public class ImovelInscricaoAlterada
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/**
	 * identifier field
	 */
	private Integer id;

	/**
	 * persistent field
	 */
	private Imovel imovel;

	/**
	 * persistent field
	 */
	private FaturamentoGrupo faturamentoGrupo;

	/**
	 * persistent field
	 */
	private Localidade localidadeAnterior;

	/**
	 * persistent field
	 */
	private SetorComercial setorComercialAnterior;

	/**
	 * persistent field
	 */
	private Quadra quadraAnterior;

	/**
	 * persistent field
	 */
	private short loteAnterior;

	/**
	 * persistent field
	 */
	private short subLoteAnterior;

	/**
	 * persistent field
	 */
	private Short testadaLoteAnterior;

	/**
	 * persistent field
	 */
	private Rota rotaAnterior;

	/**
	 * persistent field
	 */
	private Localidade localidadeAtual;

	/**
	 * persistent field
	 */
	private SetorComercial setorComercialAtual;

	/**
	 * persistent field
	 */
	private Quadra quadraAtual;

	/**
	 * persistent field
	 */
	private short loteAtual;

	/**
	 * persistent field
	 */
	private short subLoteAtual;

	/**
	 * persistent field
	 */
	private Short testadaLoteAtual;

	/**
	 * persistent field
	 */
	private Rota rotaAtual;

	/**
	 * persistent field
	 */
	private Short indicadorAtualizado;

	/**
	 * persistent field
	 */
	private Short indicadorAlteracaoExcluida;

	/**
	 * persistent field
	 */
	private Short indicadorImovelExcluido;

	/**
	 * persistent field
	 */
	private Short indicadorErroAlteracao;

	/**
	 * nullable persistent field
	 */
	private Date dataAlteracaoOnline;

	/**
	 * nullable persistent field
	 */
	private Date dataAlteracaoBatch;

	/**
	 * nullable persistent field
	 */
	private Date ultimaAlteracao;

	public ImovelInscricaoAlterada() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroImovelInscricaoAlterada filtro = new FiltroImovelInscricaoAlterada();
		filtro.adicionarParametro(new ParametroSimples(FiltroImovelInscricaoAlterada.ID, this.getId()));
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.IMOVEL);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.FATURAMENTO_GRUPO);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.LOCALIDADE_ANTERIOR);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.SETOR_COMERCIAL_ANTERIOR);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.QUADRA_ANTERIOR);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.ROTA_ANTERIOR);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.LOCALIDADE_ATUAL);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.SETOR_COMERCIAL_ATUAL);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.QUADRA_ATUAL);
		filtro.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelInscricaoAlterada.ROTA_ATUAL);

		return filtro;
	}

	/**
	 * Gets the id attribute of the Imovel object
	 * 
	 * @return The id value
	 */
	public Integer getId(){

		return this.id;
	}

	/**
	 * Sets the id attribute of the Imovel object
	 * 
	 * @param id
	 *            The new id value
	 */
	public void setId(Integer id){

		this.id = id;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public FaturamentoGrupo getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public Localidade getLocalidadeAnterior(){

		return localidadeAnterior;
	}

	public void setLocalidadeAnterior(Localidade localidadeAnterior){

		this.localidadeAnterior = localidadeAnterior;
	}

	public SetorComercial getSetorComercialAnterior(){

		return setorComercialAnterior;
	}

	public void setSetorComercialAnterior(SetorComercial setorComercialAnterior){

		this.setorComercialAnterior = setorComercialAnterior;
	}

	public Quadra getQuadraAnterior(){

		return quadraAnterior;
	}

	public void setQuadraAnterior(Quadra quadraAnterior){

		this.quadraAnterior = quadraAnterior;
	}

	public short getLoteAnterior(){

		return loteAnterior;
	}

	public void setLoteAnterior(short loteAnterior){

		this.loteAnterior = loteAnterior;
	}

	public short getSubLoteAnterior(){

		return subLoteAnterior;
	}

	public void setSubLoteAnterior(short subLoteAnterior){

		this.subLoteAnterior = subLoteAnterior;
	}

	public Short getTestadaLoteAnterior(){

		return testadaLoteAnterior;
	}

	public void setTestadaLoteAnterior(Short testadaLoteAnterior){

		this.testadaLoteAnterior = testadaLoteAnterior;
	}

	public Rota getRotaAnterior(){

		return rotaAnterior;
	}

	public void setRotaAnterior(Rota rotaAnterior){

		this.rotaAnterior = rotaAnterior;
	}

	public Localidade getLocalidadeAtual(){

		return localidadeAtual;
	}

	public void setLocalidadeAtual(Localidade localidadeAtual){

		this.localidadeAtual = localidadeAtual;
	}

	public SetorComercial getSetorComercialAtual(){

		return setorComercialAtual;
	}

	public void setSetorComercialAtual(SetorComercial setorComercialAtual){

		this.setorComercialAtual = setorComercialAtual;
	}

	public Quadra getQuadraAtual(){

		return quadraAtual;
	}

	public void setQuadraAtual(Quadra quadraAtual){

		this.quadraAtual = quadraAtual;
	}

	public short getLoteAtual(){

		return loteAtual;
	}

	public void setLoteAtual(short loteAtual){

		this.loteAtual = loteAtual;
	}

	public short getSubLoteAtual(){

		return subLoteAtual;
	}

	public void setSubLoteAtual(short subLoteAtual){

		this.subLoteAtual = subLoteAtual;
	}

	public Short getTestadaLoteAtual(){

		return testadaLoteAtual;
	}

	public void setTestadaLoteAtual(Short testadaLoteAtual){

		this.testadaLoteAtual = testadaLoteAtual;
	}

	public Rota getRotaAtual(){

		return rotaAtual;
	}

	public void setRotaAtual(Rota rotaAtual){

		this.rotaAtual = rotaAtual;
	}

	public Short getIndicadorAtualizado(){

		return indicadorAtualizado;
	}

	public void setIndicadorAtualizado(Short indicadorAtualizado){

		this.indicadorAtualizado = indicadorAtualizado;
	}

	public Short getIndicadorAlteracaoExcluida(){

		return indicadorAlteracaoExcluida;
	}

	public void setIndicadorAlteracaoExcluida(Short indicadorAlteracaoExcluida){

		this.indicadorAlteracaoExcluida = indicadorAlteracaoExcluida;
	}

	public Short getIndicadorImovelExcluido(){

		return indicadorImovelExcluido;
	}

	public void setIndicadorImovelExcluido(Short indicadorImovelExcluido){

		this.indicadorImovelExcluido = indicadorImovelExcluido;
	}

	public Short getIndicadorErroAlteracao(){

		return indicadorErroAlteracao;
	}

	public void setIndicadorErroAlteracao(Short indicadorErroAlteracao){

		this.indicadorErroAlteracao = indicadorErroAlteracao;
	}

	public Date getDataAlteracaoOnline(){

		return dataAlteracaoOnline;
	}

	public void setDataAlteracaoOnline(Date dataAlteracaoOnline){

		this.dataAlteracaoOnline = dataAlteracaoOnline;
	}

	public Date getDataAlteracaoBatch(){

		return dataAlteracaoBatch;
	}

	public void setDataAlteracaoBatch(Date dataAlteracaoBatch){

		this.dataAlteracaoBatch = dataAlteracaoBatch;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getNovaInscricaoFormatada(){

		String novaInscricao = "";

		if(localidadeAtual != null && setorComercialAtual != null && quadraAtual != null){

			Imovel imovel = new Imovel();
			imovel.setLocalidade(localidadeAtual);
			imovel.setSetorComercial(setorComercialAtual);
			imovel.setQuadra(quadraAtual);
			imovel.setLote(loteAtual);
			imovel.setSubLote(subLoteAtual);

			novaInscricao = imovel.getInscricaoFormatada();
		}

		return novaInscricao;
	}

}
