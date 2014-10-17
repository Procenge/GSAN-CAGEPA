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

import java.io.Serializable;
import java.util.Date;

public class OrdemServicoLayout
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final Integer PADRAO = 1;

	public static final Integer MANUTENCAO_REDE_RAMAL_ESGOTO = 2;

	public static final Integer VERIFICACAO_LIMPEZA = 3;

	public static final Integer DESOBSTRUCAO_REDE_RAMAL_ESGOTO = 4;

	public static final Integer INSTALACAO_HIDROMETRO = 5;

	public static final Integer SUBSTITUICAO_HIDROMETRO = 6;

	public static final Integer VERFICACAO_LIGACAO_AGUA = 7;

	public static final Integer ORDEM_CORTE = 8;

	public static final String EMITE_OS_HIDROMETRO_TXT = "emiteOSHidrometroTxt()";

	public static final String EMITE_OS_FISCALIZACAO_TXT = "emiteOSFiscalizacaoTxt()";

	private Integer id;

	private String descricao;

	private String nomeRelatorio;

	private Integer indicadorPadrao;

	private Date ultimaAlteracao;

	private Short indicadorUso;

	private String nomeClasse;

	private Integer indicadorDoisPorPagina;

	private String nomeMetodoGeracaoArquivoTxt;

	private String nomeRelatorioCarta;

	private String nomeClasseCarta;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getNomeRelatorio(){

		return nomeRelatorio;
	}

	public void setNomeRelatorio(String nomeRelatorio){

		this.nomeRelatorio = nomeRelatorio;
	}

	public Integer getIndicadorPadrao(){

		return indicadorPadrao;
	}

	public void setIndicadorPadrao(Integer indicadorPadrao){

		this.indicadorPadrao = indicadorPadrao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getNomeClasse(){

		return nomeClasse;
	}

	public void setNomeClasse(String nomeClasse){

		this.nomeClasse = nomeClasse;
	}

	public Integer getIndicadorDoisPorPagina(){

		return indicadorDoisPorPagina;
	}

	public void setIndicadorDoisPorPagina(Integer indicadorDoisPorPagina){

		this.indicadorDoisPorPagina = indicadorDoisPorPagina;
	}

	public String getNomeMetodoGeracaoArquivoTxt(){

		return nomeMetodoGeracaoArquivoTxt;
	}

	public void setNomeMetodoGeracaoArquivoTxt(String nomeMetodoGeracaoArquivoTxt){

		this.nomeMetodoGeracaoArquivoTxt = nomeMetodoGeracaoArquivoTxt;
	}

	public String getNomeRelatorioCarta(){

		return nomeRelatorioCarta;
	}

	public void setNomeRelatorioCarta(String nomeRelatorioCarta){

		this.nomeRelatorioCarta = nomeRelatorioCarta;
	}

	public String getNomeClasseCarta(){

		return nomeClasseCarta;
	}

	public void setNomeClasseCarta(String nomeClasseCarta){

		this.nomeClasseCarta = nomeClasseCarta;
	}

}
