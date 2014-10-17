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

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

@ControleAlteracao()
public class OsSeletivaComandoHidrometroDiametro
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	private Integer referenciaInicialInstalacaoHidrometro;

	private Integer referenciaFinalInstalacaoHidrometro;

	private Integer leituraAcumuladaHidrometro;

	private OsSeletivaComando osSeletivaComando;

	private HidrometroDiametro hidrometroDiametro;

	private HidrometroCapacidade hidrometroCapacidade;

	private Date ultimaAlteracao;

	public OsSeletivaComandoHidrometroDiametro(Integer referenciaInicialInstalacaoHidrometro, Integer referenciaFinalInstalacaoHidrometro,
												Integer leituraAcumuladaHidrometro, OsSeletivaComando osSeletivaComando,
												HidrometroDiametro hidrometroDiametro, HidrometroCapacidade hidrometroCapacidade) {

		this.referenciaInicialInstalacaoHidrometro = referenciaInicialInstalacaoHidrometro;
		this.referenciaFinalInstalacaoHidrometro = referenciaFinalInstalacaoHidrometro;
		this.leituraAcumuladaHidrometro = leituraAcumuladaHidrometro;
		this.osSeletivaComando = osSeletivaComando;
		this.hidrometroDiametro = hidrometroDiametro;
		this.hidrometroCapacidade = hidrometroCapacidade;
	}

	/** default constructor */
	public OsSeletivaComandoHidrometroDiametro() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getReferenciaInicialInstalacaoHidrometro(){

		return referenciaInicialInstalacaoHidrometro;
	}

	public void setReferenciaInicialInstalacaoHidrometro(Integer referenciaInicialInstalacaoHidrometro){

		this.referenciaInicialInstalacaoHidrometro = referenciaInicialInstalacaoHidrometro;
	}

	public Integer getReferenciaFinalInstalacaoHidrometro(){

		return referenciaFinalInstalacaoHidrometro;
	}

	public void setReferenciaFinalInstalacaoHidrometro(Integer referenciaFinalInstalacaoHidrometro){

		this.referenciaFinalInstalacaoHidrometro = referenciaFinalInstalacaoHidrometro;
	}

	public Integer getLeituraAcumuladaHidrometro(){

		return leituraAcumuladaHidrometro;
	}

	public void setLeituraAcumuladaHidrometro(Integer leituraAcumuladaHidrometro){

		this.leituraAcumuladaHidrometro = leituraAcumuladaHidrometro;
	}

	public OsSeletivaComando getOsSeletivaComando(){

		return osSeletivaComando;
	}

	public void setOsSeletivaComando(OsSeletivaComando osSeletivaComando){

		this.osSeletivaComando = osSeletivaComando;
	}

	public HidrometroDiametro getHidrometroDiametro(){

		return hidrometroDiametro;
	}

	public void setHidrometroDiametro(HidrometroDiametro hidrometroDiametro){

		this.hidrometroDiametro = hidrometroDiametro;
	}

	public HidrometroCapacidade getHidrometroCapacidade(){

		return hidrometroCapacidade;
	}

	public void setHidrometroCapacidade(HidrometroCapacidade hidrometroCapacidade){

		this.hidrometroCapacidade = hidrometroCapacidade;
	}

	@Override
	public Date getUltimaAlteracao(){

		// TODO Auto-generated method stub
		return ultimaAlteracao;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;

	}

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
		if(!(obj instanceof OsSeletivaComandoHidrometroDiametro)) return false;
		final OsSeletivaComandoHidrometroDiametro other = (OsSeletivaComandoHidrometroDiametro) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroOsSeletivaComandoHidrometroDiametro filtroOsSeletivaComandoHidrometroDiametro = new FiltroOsSeletivaComandoHidrometroDiametro();

		filtroOsSeletivaComandoHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroOsSeletivaComandoHidrometroDiametro.ID,
						this.getId()));
		return filtroOsSeletivaComandoHidrometroDiametro;
	}

}
