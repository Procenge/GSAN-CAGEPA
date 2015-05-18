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

package gcom.seguranca.acesso.usuario;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UsuarioAcesso
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Usuario usuario;

	private Integer diaSemana;

	private Date horaInicio;

	private Date horaFim;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	private transient String descricaoDiaSemana;

	private transient int indicadorSelecionado;

	/** full constructor */
	public UsuarioAcesso(Usuario usuario, Integer diaSemana, Date horaInicio, Date horaFim, Short indicadorUso, Date ultimaAlteracao,
							String descricaoDiaSemana, int indicadorSelecionado) {

		this.usuario = usuario;
		this.diaSemana = diaSemana;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.descricaoDiaSemana = descricaoDiaSemana;
		this.indicadorSelecionado = indicadorSelecionado;
	}

	/** default constructor */
	public UsuarioAcesso() {

	}

	public UsuarioAcesso(Integer diaSemana, String descricaoDiaSemana, Date horaInicio, Date horaFim, int indicadorSelecionado) {

		this.diaSemana = diaSemana;
		this.descricaoDiaSemana = descricaoDiaSemana;
		this.horaInicio = horaInicio;
		this.horaFim = horaFim;
		this.indicadorUso = Short.valueOf("1");
		this.indicadorSelecionado = indicadorSelecionado;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Usuario getUsuario(){

		return usuario;
	}

	public void setUsuario(Usuario usuario){

		this.usuario = usuario;
	}

	public Integer getDiaSemana(){

		return diaSemana;
	}

	public void setDiaSemana(Integer diaSemana){

		this.diaSemana = diaSemana;
	}

	public Date getHoraInicio(){

		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio){

		this.horaInicio = horaInicio;
	}

	public Date getHoraFim(){

		return horaFim;
	}

	public void setHoraFim(Date horaFim){

		this.horaFim = horaFim;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getDescricaoDiaSemana(){

		return descricaoDiaSemana;
	}

	public void setDescricaoDiaSemana(String descricaoDiaSemana){

		this.descricaoDiaSemana = descricaoDiaSemana;
	}

	public int getIndicadorSelecionado(){

		return indicadorSelecionado;
	}

	public void setIndicadorSelecionado(int indicadorSelecionado){

		this.indicadorSelecionado = indicadorSelecionado;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public boolean equals(Object arg){

		if(arg == null){
			return false;
		}
		if(!(arg instanceof UsuarioAcesso)){
			return false;
		}
		return this.id.intValue() == ((UsuarioAcesso) arg).getId().intValue();
	}
}
