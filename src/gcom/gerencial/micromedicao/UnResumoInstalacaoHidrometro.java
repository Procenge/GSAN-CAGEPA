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

package gcom.gerencial.micromedicao;

import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaPerfil;
import gcom.gerencial.atendimentopublico.ligacaoagua.GLigacaoAguaSituacao;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoPerfil;
import gcom.gerencial.atendimentopublico.ligacaoesgoto.GLigacaoEsgotoSituacao;
import gcom.gerencial.cadastro.cliente.GClienteTipo;
import gcom.gerencial.cadastro.cliente.GEsferaPoder;
import gcom.gerencial.cadastro.imovel.GCategoria;
import gcom.gerencial.cadastro.imovel.GImovelPerfil;
import gcom.gerencial.cadastro.imovel.GSubcategoria;
import gcom.gerencial.cadastro.localidade.GGerenciaRegional;
import gcom.gerencial.cadastro.localidade.GLocalidade;
import gcom.gerencial.cadastro.localidade.GQuadra;
import gcom.gerencial.cadastro.localidade.GSetorComercial;
import gcom.gerencial.cadastro.localidade.GUnidadeNegocio;
import gcom.gerencial.micromedicao.GRota;
import java.io.Serializable;
import java.util.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class UnResumoInstalacaoHidrometro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private int referencia;

	/** persistent field */
	private int codigoSetorComercial;

	/** persistent field */
	private int numeroQuadra;

	/** persistent field */
	private int quantidadeHidrometrosInstaladosRamal;

	private int quantidadeHidrometrosInstaladosPoco;

	/** persistent field */
	private int quantidadeHidrometrosSubstituidosRamal;

	private int quantidadeHidrometrosSubstituidosPoco;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private int quantidadeHidrometrosRemanejadosRamal;

	private int quantidadeHidrometrosRemanejadosPoco;

	/** persistent field */
	private int quantidadeHidrometrosRetiradosRamal;

	private int quantidadeHidrometrosRetiradosPoco;

	/** nullable persistent field */
	private Integer quantidadeHidrometrosDadosAtualizados;

	/** persistent field */
	private GSubcategoria gerSubcategoria;

	/** persistent field */
	private GClienteTipo gerClienteTipo;

	/** persistent field */
	private GLigacaoAguaSituacao gerLigacaoAguaSituacao;

	/** persistent field */
	private GUnidadeNegocio gerUnidadeNegocio;

	/** persistent field */
	private GLocalidade gerLocalidade;

	/** persistent field */
	private GLocalidade gerLocalidadeElo;

	/** persistent field */
	private GQuadra gerQuadra;

	/** persistent field */
	private GLigacaoEsgotoSituacao gerLigacaoEsgotoSituacao;

	/** persistent field */
	private GLigacaoEsgotoPerfil gerLigacaoEsgotoPerfil;

	/** persistent field */
	private GGerenciaRegional gerGerenciaRegional;

	/** persistent field */
	private GSetorComercial gerSetorComercial;

	/** persistent field */
	private GLigacaoAguaPerfil gerLigacaoAguaPerfil;

	/** persistent field */
	private GEsferaPoder gerEsferaPoder;

	/** persistent field */
	private GCategoria gerCategoria;

	/** persistent field */
	private GImovelPerfil gerImovelPerfil;

	/** persistent field */
	private GRota gerRota;

	private int quantidadeHidrometrosAtualInstaladoRamal;

	private int quantidadeHidrometrosAtualInstaladoPoco;

	/** full constructor */
	public UnResumoInstalacaoHidrometro(Integer id, int referencia, int codigoSetorComercial, int numeroQuadra,
										int quantidadeHidrometrosInstaladosRamal, int quantidadeHidrometrosSubstituidosRamal,
										Date ultimaAlteracao, int quantidadeHidrometrosRemanejadosRamal,
										int quantidadeHidrometrosRetiradosRamal, Integer quantidadeHidrometrosDadosAtualizados,
										GSubcategoria gerSubcategoria, GClienteTipo gerClienteTipo,
										GLigacaoAguaSituacao gerLigacaoAguaSituacao, GUnidadeNegocio gerUnidadeNegocio,
										GLocalidade gerLocalidade, GLocalidade gerLocalidadeElo, GQuadra gerQuadra,
										GLigacaoEsgotoSituacao gerLigacaoEsgotoSituacao, GLigacaoEsgotoPerfil gerLigacaoEsgotoPerfil,
										GGerenciaRegional gerGerenciaRegional, GSetorComercial gerSetorComercial,
										GLigacaoAguaPerfil gerLigacaoAguaPerfil, GEsferaPoder gerEsferaPoder, GCategoria gerCategoria,
										GImovelPerfil gerImovelPerfil, GRota gerRota, int quantidadeHidrometrosInstaladosPoco,
										int quantidadeHidrometrosSubstituidosPoco, int quantidadeHidrometrosRemanejadosPoco,
										int quantidadeHidrometrosRetiradosPoco, int quantidadeHidrometrosAtualInstaladoRamal,
										int quantidadeHidrometrosAtualInstaladoPoco) {

		this.id = id;
		this.referencia = referencia;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.quantidadeHidrometrosInstaladosRamal = quantidadeHidrometrosInstaladosRamal;
		this.quantidadeHidrometrosSubstituidosRamal = quantidadeHidrometrosSubstituidosRamal;
		this.ultimaAlteracao = ultimaAlteracao;
		this.quantidadeHidrometrosRemanejadosRamal = quantidadeHidrometrosRemanejadosRamal;
		this.quantidadeHidrometrosRetiradosRamal = quantidadeHidrometrosRetiradosRamal;
		this.quantidadeHidrometrosDadosAtualizados = quantidadeHidrometrosDadosAtualizados;
		this.gerSubcategoria = gerSubcategoria;
		this.gerClienteTipo = gerClienteTipo;
		this.gerLigacaoAguaSituacao = gerLigacaoAguaSituacao;
		this.gerUnidadeNegocio = gerUnidadeNegocio;
		this.gerLocalidade = gerLocalidade;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerQuadra = gerQuadra;
		this.gerLigacaoEsgotoSituacao = gerLigacaoEsgotoSituacao;
		this.gerLigacaoEsgotoPerfil = gerLigacaoEsgotoPerfil;
		this.gerGerenciaRegional = gerGerenciaRegional;
		this.gerSetorComercial = gerSetorComercial;
		this.gerLigacaoAguaPerfil = gerLigacaoAguaPerfil;
		this.gerEsferaPoder = gerEsferaPoder;
		this.gerCategoria = gerCategoria;
		this.gerImovelPerfil = gerImovelPerfil;
		this.gerRota = gerRota;
		this.quantidadeHidrometrosInstaladosPoco = quantidadeHidrometrosInstaladosPoco;
		this.quantidadeHidrometrosSubstituidosPoco = quantidadeHidrometrosSubstituidosPoco;
		this.quantidadeHidrometrosRemanejadosPoco = quantidadeHidrometrosRemanejadosPoco;
		this.quantidadeHidrometrosRetiradosPoco = quantidadeHidrometrosRetiradosPoco;
		this.quantidadeHidrometrosAtualInstaladoRamal = quantidadeHidrometrosAtualInstaladoRamal;
		this.quantidadeHidrometrosAtualInstaladoPoco = quantidadeHidrometrosAtualInstaladoPoco;
	}

	/** constructor para [UC0564] */
	public UnResumoInstalacaoHidrometro(int referencia, int codigoSetorComercial, int numeroQuadra,
										int quantidadeHidrometrosInstaladosRamal, int quantidadeHidrometrosSubstituidosRamal,
										Date ultimaAlteracao, int quantidadeHidrometrosRemanejadosRamal,
										int quantidadeHidrometrosRetiradosRamal, Integer quantidadeHidrometrosDadosAtualizados,
										GSubcategoria gerSubcategoria, GClienteTipo gerClienteTipo,
										GLigacaoAguaSituacao gerLigacaoAguaSituacao, GUnidadeNegocio gerUnidadeNegocio,
										GLocalidade gerLocalidade, GLocalidade gerLocalidadeElo, GQuadra gerQuadra,
										GLigacaoEsgotoSituacao gerLigacaoEsgotoSituacao, GLigacaoEsgotoPerfil gerLigacaoEsgotoPerfil,
										GGerenciaRegional gerGerenciaRegional, GSetorComercial gerSetorComercial,
										GLigacaoAguaPerfil gerLigacaoAguaPerfil, GEsferaPoder gerEsferaPoder, GCategoria gerCategoria,
										GImovelPerfil gerImovelPerfil, GRota gerRota, int quantidadeHidrometrosInstaladosPoco,
										int quantidadeHidrometrosSubstituidosPoco, int quantidadeHidrometrosRemanejadosPoco,
										int quantidadeHidrometrosRetiradosPoco, int quantidadeHidrometrosAtualInstaladoRamal,
										int quantidadeHidrometrosAtualInstaladoPoco) {

		this.referencia = referencia;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.quantidadeHidrometrosInstaladosRamal = quantidadeHidrometrosInstaladosRamal;
		this.quantidadeHidrometrosSubstituidosRamal = quantidadeHidrometrosSubstituidosRamal;
		this.ultimaAlteracao = ultimaAlteracao;
		this.quantidadeHidrometrosRemanejadosRamal = quantidadeHidrometrosRemanejadosRamal;
		this.quantidadeHidrometrosRetiradosRamal = quantidadeHidrometrosRetiradosRamal;
		this.quantidadeHidrometrosDadosAtualizados = quantidadeHidrometrosDadosAtualizados;
		this.gerSubcategoria = gerSubcategoria;
		this.gerClienteTipo = gerClienteTipo;
		this.gerLigacaoAguaSituacao = gerLigacaoAguaSituacao;
		this.gerUnidadeNegocio = gerUnidadeNegocio;
		this.gerLocalidade = gerLocalidade;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerQuadra = gerQuadra;
		this.gerLigacaoEsgotoSituacao = gerLigacaoEsgotoSituacao;
		this.gerLigacaoEsgotoPerfil = gerLigacaoEsgotoPerfil;
		this.gerGerenciaRegional = gerGerenciaRegional;
		this.gerSetorComercial = gerSetorComercial;
		this.gerLigacaoAguaPerfil = gerLigacaoAguaPerfil;
		this.gerEsferaPoder = gerEsferaPoder;
		this.gerCategoria = gerCategoria;
		this.gerImovelPerfil = gerImovelPerfil;
		this.gerRota = gerRota;
		this.quantidadeHidrometrosInstaladosPoco = quantidadeHidrometrosInstaladosPoco;
		this.quantidadeHidrometrosSubstituidosPoco = quantidadeHidrometrosSubstituidosPoco;
		this.quantidadeHidrometrosRemanejadosPoco = quantidadeHidrometrosRemanejadosPoco;
		this.quantidadeHidrometrosRetiradosPoco = quantidadeHidrometrosRetiradosPoco;
		this.quantidadeHidrometrosAtualInstaladoRamal = quantidadeHidrometrosAtualInstaladoRamal;
		this.quantidadeHidrometrosAtualInstaladoPoco = quantidadeHidrometrosAtualInstaladoPoco;
	}

	/** default constructor */
	public UnResumoInstalacaoHidrometro() {

	}

	/** minimal constructor */
	public UnResumoInstalacaoHidrometro(Integer id, int referencia, int codigoSetorComercial, int numeroQuadra,
										int quantidadeHidrometrosInstaladosRamal, int quantidadeHidrometrosSubstituidosRamal,
										Date ultimaAlteracao, int quantidadeHidrometrosRemanejadosRamal,
										int quantidadeHidrometrosRetiradosRamal, GSubcategoria gerSubcategoria,
										GClienteTipo gerClienteTipo, GLigacaoAguaSituacao gerLigacaoAguaSituacao,
										GUnidadeNegocio gerUnidadeNegocio, GLocalidade gerLocalidade, GLocalidade gerLocalidadeElo,
										GQuadra gerQuadra, GLigacaoEsgotoSituacao gerLigacaoEsgotoSituacao,
										GLigacaoEsgotoPerfil gerLigacaoEsgotoPerfil, GGerenciaRegional gerGerenciaRegional,
										GSetorComercial gerSetorComercial, GLigacaoAguaPerfil gerLigacaoAguaPerfil,
										GEsferaPoder gerEsferaPoder, GCategoria gerCategoria, GImovelPerfil gerImovelPerfil, GRota gerRota,
										int quantidadeHidrometrosInstaladosPoco, int quantidadeHidrometrosSubstituidosPoco,
										int quantidadeHidrometrosRemanejadosPoco, int quantidadeHidrometrosRetiradosPoco) {

		this.id = id;
		this.referencia = referencia;
		this.codigoSetorComercial = codigoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.quantidadeHidrometrosInstaladosRamal = quantidadeHidrometrosInstaladosRamal;
		this.quantidadeHidrometrosSubstituidosRamal = quantidadeHidrometrosSubstituidosRamal;
		this.ultimaAlteracao = ultimaAlteracao;
		this.quantidadeHidrometrosRemanejadosRamal = quantidadeHidrometrosRemanejadosRamal;
		this.quantidadeHidrometrosRetiradosRamal = quantidadeHidrometrosRetiradosRamal;
		this.gerSubcategoria = gerSubcategoria;
		this.gerClienteTipo = gerClienteTipo;
		this.gerLigacaoAguaSituacao = gerLigacaoAguaSituacao;
		this.gerUnidadeNegocio = gerUnidadeNegocio;
		this.gerLocalidade = gerLocalidade;
		this.gerLocalidadeElo = gerLocalidadeElo;
		this.gerQuadra = gerQuadra;
		this.gerLigacaoEsgotoSituacao = gerLigacaoEsgotoSituacao;
		this.gerLigacaoEsgotoPerfil = gerLigacaoEsgotoPerfil;
		this.gerGerenciaRegional = gerGerenciaRegional;
		this.gerSetorComercial = gerSetorComercial;
		this.gerLigacaoAguaPerfil = gerLigacaoAguaPerfil;
		this.gerEsferaPoder = gerEsferaPoder;
		this.gerCategoria = gerCategoria;
		this.gerImovelPerfil = gerImovelPerfil;
		this.gerRota = gerRota;
		this.quantidadeHidrometrosInstaladosPoco = quantidadeHidrometrosInstaladosPoco;
		this.quantidadeHidrometrosSubstituidosPoco = quantidadeHidrometrosSubstituidosPoco;
		this.quantidadeHidrometrosRemanejadosPoco = quantidadeHidrometrosRemanejadosPoco;
		this.quantidadeHidrometrosRetiradosPoco = quantidadeHidrometrosRetiradosPoco;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public int getReferencia(){

		return this.referencia;
	}

	public void setReferencia(int referencia){

		this.referencia = referencia;
	}

	public int getCodigoSetorComercial(){

		return this.codigoSetorComercial;
	}

	public void setCodigoSetorComercial(int codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public int getNumeroQuadra(){

		return this.numeroQuadra;
	}

	public void setNumeroQuadra(int numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public int getQuantidadeHidrometrosInstaladosPoco(){

		return quantidadeHidrometrosInstaladosPoco;
	}

	public void setQuantidadeHidrometrosInstaladosPoco(int quantidadeHidrometrosInstaladosPoco){

		this.quantidadeHidrometrosInstaladosPoco = quantidadeHidrometrosInstaladosPoco;
	}

	public int getQuantidadeHidrometrosInstaladosRamal(){

		return quantidadeHidrometrosInstaladosRamal;
	}

	public void setQuantidadeHidrometrosInstaladosRamal(int quantidadeHidrometrosInstaladosRamal){

		this.quantidadeHidrometrosInstaladosRamal = quantidadeHidrometrosInstaladosRamal;
	}

	public int getQuantidadeHidrometrosRemanejadosPoco(){

		return quantidadeHidrometrosRemanejadosPoco;
	}

	public void setQuantidadeHidrometrosRemanejadosPoco(int quantidadeHidrometrosRemanejadosPoco){

		this.quantidadeHidrometrosRemanejadosPoco = quantidadeHidrometrosRemanejadosPoco;
	}

	public int getQuantidadeHidrometrosRemanejadosRamal(){

		return quantidadeHidrometrosRemanejadosRamal;
	}

	public void setQuantidadeHidrometrosRemanejadosRamal(int quantidadeHidrometrosRemanejadosRamal){

		this.quantidadeHidrometrosRemanejadosRamal = quantidadeHidrometrosRemanejadosRamal;
	}

	public int getQuantidadeHidrometrosRetiradosPoco(){

		return quantidadeHidrometrosRetiradosPoco;
	}

	public void setQuantidadeHidrometrosRetiradosPoco(int quantidadeHidrometrosRetiradosPoco){

		this.quantidadeHidrometrosRetiradosPoco = quantidadeHidrometrosRetiradosPoco;
	}

	public int getQuantidadeHidrometrosRetiradosRamal(){

		return quantidadeHidrometrosRetiradosRamal;
	}

	public void setQuantidadeHidrometrosRetiradosRamal(int quantidadeHidrometrosRetiradosRamal){

		this.quantidadeHidrometrosRetiradosRamal = quantidadeHidrometrosRetiradosRamal;
	}

	public int getQuantidadeHidrometrosSubstituidosPoco(){

		return quantidadeHidrometrosSubstituidosPoco;
	}

	public void setQuantidadeHidrometrosSubstituidosPoco(int quantidadeHidrometrosSubstituidosPoco){

		this.quantidadeHidrometrosSubstituidosPoco = quantidadeHidrometrosSubstituidosPoco;
	}

	public int getQuantidadeHidrometrosSubstituidosRamal(){

		return quantidadeHidrometrosSubstituidosRamal;
	}

	public void setQuantidadeHidrometrosSubstituidosRamal(int quantidadeHidrometrosSubstituidosRamal){

		this.quantidadeHidrometrosSubstituidosRamal = quantidadeHidrometrosSubstituidosRamal;
	}

	public Integer getQuantidadeHidrometrosDadosAtualizados(){

		return this.quantidadeHidrometrosDadosAtualizados;
	}

	public void setQuantidadeHidrometrosDadosAtualizados(Integer quantidadeHidrometrosDadosAtualizados){

		this.quantidadeHidrometrosDadosAtualizados = quantidadeHidrometrosDadosAtualizados;
	}

	public GSubcategoria getGerSubcategoria(){

		return this.gerSubcategoria;
	}

	public void setGerSubcategoria(GSubcategoria gerSubcategoria){

		this.gerSubcategoria = gerSubcategoria;
	}

	public GClienteTipo getGerClienteTipo(){

		return this.gerClienteTipo;
	}

	public void setGerClienteTipo(GClienteTipo gerClienteTipo){

		this.gerClienteTipo = gerClienteTipo;
	}

	public GLigacaoAguaSituacao getGerLigacaoAguaSituacao(){

		return this.gerLigacaoAguaSituacao;
	}

	public void setGerLigacaoAguaSituacao(GLigacaoAguaSituacao gerLigacaoAguaSituacao){

		this.gerLigacaoAguaSituacao = gerLigacaoAguaSituacao;
	}

	public GUnidadeNegocio getGerUnidadeNegocio(){

		return this.gerUnidadeNegocio;
	}

	public void setGerUnidadeNegocio(GUnidadeNegocio gerUnidadeNegocio){

		this.gerUnidadeNegocio = gerUnidadeNegocio;
	}

	public GLocalidade getGerLocalidade(){

		return this.gerLocalidade;
	}

	public void setGerLocalidade(GLocalidade gerLocalidade){

		this.gerLocalidade = gerLocalidade;
	}

	public GLocalidade getGerLocalidadeElo(){

		return this.gerLocalidadeElo;
	}

	public void setGerLocalidadeElo(GLocalidade gerLocalidadeElo){

		this.gerLocalidadeElo = gerLocalidadeElo;
	}

	public GQuadra getGerQuadra(){

		return this.gerQuadra;
	}

	public void setGerQuadra(GQuadra gerQuadra){

		this.gerQuadra = gerQuadra;
	}

	public GLigacaoEsgotoSituacao getGerLigacaoEsgotoSituacao(){

		return this.gerLigacaoEsgotoSituacao;
	}

	public void setGerLigacaoEsgotoSituacao(GLigacaoEsgotoSituacao gerLigacaoEsgotoSituacao){

		this.gerLigacaoEsgotoSituacao = gerLigacaoEsgotoSituacao;
	}

	public GLigacaoEsgotoPerfil getGerLigacaoEsgotoPerfil(){

		return this.gerLigacaoEsgotoPerfil;
	}

	public void setGerLigacaoEsgotoPerfil(GLigacaoEsgotoPerfil gerLigacaoEsgotoPerfil){

		this.gerLigacaoEsgotoPerfil = gerLigacaoEsgotoPerfil;
	}

	public GGerenciaRegional getGerGerenciaRegional(){

		return this.gerGerenciaRegional;
	}

	public void setGerGerenciaRegional(GGerenciaRegional gerGerenciaRegional){

		this.gerGerenciaRegional = gerGerenciaRegional;
	}

	public GSetorComercial getGerSetorComercial(){

		return this.gerSetorComercial;
	}

	public void setGerSetorComercial(GSetorComercial gerSetorComercial){

		this.gerSetorComercial = gerSetorComercial;
	}

	public GLigacaoAguaPerfil getGerLigacaoAguaPerfil(){

		return this.gerLigacaoAguaPerfil;
	}

	public void setGerLigacaoAguaPerfil(GLigacaoAguaPerfil gerLigacaoAguaPerfil){

		this.gerLigacaoAguaPerfil = gerLigacaoAguaPerfil;
	}

	public GEsferaPoder getGerEsferaPoder(){

		return this.gerEsferaPoder;
	}

	public void setGerEsferaPoder(GEsferaPoder gerEsferaPoder){

		this.gerEsferaPoder = gerEsferaPoder;
	}

	public GCategoria getGerCategoria(){

		return this.gerCategoria;
	}

	public void setGerCategoria(GCategoria gerCategoria){

		this.gerCategoria = gerCategoria;
	}

	public GImovelPerfil getGerImovelPerfil(){

		return this.gerImovelPerfil;
	}

	public void setGerImovelPerfil(GImovelPerfil gerImovelPerfil){

		this.gerImovelPerfil = gerImovelPerfil;
	}

	public GRota getGerRota(){

		return this.gerRota;
	}

	public void setGerRota(GRota gerRota){

		this.gerRota = gerRota;
	}

	public int getQuantidadeHidrometrosAtualInstaladoPoco(){

		return quantidadeHidrometrosAtualInstaladoPoco;
	}

	public void setQuantidadeHidrometrosAtualInstaladoPoco(int quantidadeHidrometrosAtualInstaladoPoco){

		this.quantidadeHidrometrosAtualInstaladoPoco = quantidadeHidrometrosAtualInstaladoPoco;
	}

	public int getQuantidadeHidrometrosAtualInstaladoRamal(){

		return quantidadeHidrometrosAtualInstaladoRamal;
	}

	public void setQuantidadeHidrometrosAtualInstaladoRamal(int quantidadeHidrometrosAtualInstaladoRamal){

		this.quantidadeHidrometrosAtualInstaladoRamal = quantidadeHidrometrosAtualInstaladoRamal;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
