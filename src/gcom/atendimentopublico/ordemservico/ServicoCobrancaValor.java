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

package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.imovel.ImovelPerfil;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class ServicoCobrancaValor
				extends ObjetoTransacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static Short INDICADOR_MEDICAO_SIM = new Short("1");

	public final static Short INDICADOR_MEDICAO_NAO = new Short("2");

	/** identifier field */
	private Integer id;

	/** persistent field */
	private BigDecimal valor;

	/** persistent field */
	private short indicadorMedido;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private HidrometroCapacidade hidrometroCapacidade;

	/** persistent field */
	private ImovelPerfil imovelPerfil;

	/** persistent field */
	private gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo;

	/** full constructor */
	public ServicoCobrancaValor(BigDecimal valor, short indicadorMedido, Date ultimaAlteracao, HidrometroCapacidade hidrometroCapacidade,
								ImovelPerfil imovelPerfil, gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo) {

		this.valor = valor;
		this.indicadorMedido = indicadorMedido;
		this.ultimaAlteracao = ultimaAlteracao;
		this.hidrometroCapacidade = hidrometroCapacidade;
		this.imovelPerfil = imovelPerfil;
		this.servicoTipo = servicoTipo;
	}

	/** default constructor */
	public ServicoCobrancaValor() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public BigDecimal getValor(){

		return this.valor;
	}

	public void setValor(BigDecimal valor){

		this.valor = valor;
	}

	public short getIndicadorMedido(){

		return this.indicadorMedido;
	}

	public void setIndicadorMedido(short indicadorMedido){

		this.indicadorMedido = indicadorMedido;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public HidrometroCapacidade getHidrometroCapacidade(){

		return this.hidrometroCapacidade;
	}

	public void setHidrometroCapacidade(HidrometroCapacidade hidrometroCapacidade){

		this.hidrometroCapacidade = hidrometroCapacidade;
	}

	public ImovelPerfil getImovelPerfil(){

		return this.imovelPerfil;
	}

	public void setImovelPerfil(ImovelPerfil imovelPerfil){

		this.imovelPerfil = imovelPerfil;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipo(){

		return this.servicoTipo;
	}

	public void setServicoTipo(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroServicoCobrancaValor filtroServicoCobrancaValor = new FiltroServicoCobrancaValor();

		// filtroServicoCobrancaValor
		// .adicionarCaminhoParaCarregamentoEntidade("valor");
		filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("hidrometroCapacidade");
		filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("imovelPerfil");
		filtroServicoCobrancaValor.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");
		// filtroServicoCobrancaValor
		// .adicionarCaminhoParaCarregamentoEntidade("ultimaAlteracao");

		filtroServicoCobrancaValor.adicionarParametro(new ParametroSimples(FiltroServicoCobrancaValor.ID, this.getId()));
		return filtroServicoCobrancaValor;
	}
}
