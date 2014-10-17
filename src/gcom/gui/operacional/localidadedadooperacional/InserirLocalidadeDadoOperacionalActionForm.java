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

package gcom.gui.operacional.localidadedadooperacional;

import gcom.cadastro.localidade.Localidade;
import gcom.operacional.LocalidadeDadoOperacional;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC] Inserir Localidade Dado Operacional
 * 
 * @author isilva
 * @date 26/01/2011
 */
public class InserirLocalidadeDadoOperacionalActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String id;

	private String indicadorUso;

	private String ultimaAlteracao;

	private String mesAnoReferencia;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String volumeProduzido;

	private String extensaoRedeAgua;

	private String extensaoRedeEsgoto;

	private String qtdFuncionariosAdministracao;

	private String qtdFuncionariosAdministracaoTercerizados;

	private String qtdFuncionariosOperacao;

	private String qtdFuncionariosOperacaoTercerizados;

	private String qtdSulfatoAluminio;

	private String qtdCloroGasoso;

	private String qtdHipocloritoSodio;

	private String quantidadeFluor;

	private String qtdConsumoEnergia;

	private String qtdHorasParalisadas;

	public InserirLocalidadeDadoOperacionalActionForm() {

	}

	public void limparForm(){

		this.id = null;
		this.indicadorUso = null;
		this.ultimaAlteracao = null;
		this.mesAnoReferencia = null;
		this.idLocalidade = null;
		this.descricaoLocalidade = null;
		this.volumeProduzido = null;
		this.extensaoRedeAgua = null;
		this.extensaoRedeEsgoto = null;
		this.qtdFuncionariosAdministracao = null;
		this.qtdFuncionariosAdministracaoTercerizados = null;
		this.qtdFuncionariosOperacao = null;
		this.qtdFuncionariosOperacaoTercerizados = null;
		this.qtdSulfatoAluminio = null;
		this.qtdCloroGasoso = null;
		this.qtdHipocloritoSodio = null;
		this.quantidadeFluor = null;
		this.qtdConsumoEnergia = null;
		this.qtdHorasParalisadas = null;
	}

	/**
	 * @return the id
	 */
	public String getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id){

		this.id = id;
	}

	/**
	 * @return the indicadorUso
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return the mesAnoReferencia
	 */
	public String getMesAnoReferencia(){

		return mesAnoReferencia;
	}

	/**
	 * @param mesAnoReferencia
	 *            the mesAnoReferencia to set
	 */
	public void setMesAnoReferencia(String mesAnoReferencia){

		this.mesAnoReferencia = mesAnoReferencia;
	}

	/**
	 * @return the idLocalidade
	 */
	public String getIdLocalidade(){

		return idLocalidade;
	}

	/**
	 * @param idLocalidade
	 *            the idLocalidade to set
	 */
	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return the descricaoLocalidade
	 */
	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	/**
	 * @param descricaoLocalidade
	 *            the descricaoLocalidade to set
	 */
	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	/**
	 * @return the volumeProduzido
	 */
	public String getVolumeProduzido(){

		return volumeProduzido;
	}

	/**
	 * @param volumeProduzido
	 *            the volumeProduzido to set
	 */
	public void setVolumeProduzido(String volumeProduzido){

		this.volumeProduzido = volumeProduzido;
	}

	/**
	 * @return the extensaoRedeAgua
	 */
	public String getExtensaoRedeAgua(){

		return extensaoRedeAgua;
	}

	/**
	 * @param extensaoRedeAgua
	 *            the extensaoRedeAgua to set
	 */
	public void setExtensaoRedeAgua(String extensaoRedeAgua){

		this.extensaoRedeAgua = extensaoRedeAgua;
	}

	/**
	 * @return the extensaoRedeEsgoto
	 */
	public String getExtensaoRedeEsgoto(){

		return extensaoRedeEsgoto;
	}

	/**
	 * @param extensaoRedeEsgoto
	 *            the extensaoRedeEsgoto to set
	 */
	public void setExtensaoRedeEsgoto(String extensaoRedeEsgoto){

		this.extensaoRedeEsgoto = extensaoRedeEsgoto;
	}

	/**
	 * @return the qtdFuncionariosAdministracao
	 */
	public String getQtdFuncionariosAdministracao(){

		return qtdFuncionariosAdministracao;
	}

	/**
	 * @param qtdFuncionariosAdministracao
	 *            the qtdFuncionariosAdministracao to set
	 */
	public void setQtdFuncionariosAdministracao(String qtdFuncionariosAdministracao){

		this.qtdFuncionariosAdministracao = qtdFuncionariosAdministracao;
	}

	/**
	 * @return the qtdFuncionariosAdministracaoTercerizados
	 */
	public String getQtdFuncionariosAdministracaoTercerizados(){

		return qtdFuncionariosAdministracaoTercerizados;
	}

	/**
	 * @param qtdFuncionariosAdministracaoTercerizados
	 *            the qtdFuncionariosAdministracaoTercerizados to set
	 */
	public void setQtdFuncionariosAdministracaoTercerizados(String qtdFuncionariosAdministracaoTercerizados){

		this.qtdFuncionariosAdministracaoTercerizados = qtdFuncionariosAdministracaoTercerizados;
	}

	/**
	 * @return the qtdFuncionariosOperacao
	 */
	public String getQtdFuncionariosOperacao(){

		return qtdFuncionariosOperacao;
	}

	/**
	 * @param qtdFuncionariosOperacao
	 *            the qtdFuncionariosOperacao to set
	 */
	public void setQtdFuncionariosOperacao(String qtdFuncionariosOperacao){

		this.qtdFuncionariosOperacao = qtdFuncionariosOperacao;
	}

	/**
	 * @return the qtdFuncionariosOperacaoTercerizados
	 */
	public String getQtdFuncionariosOperacaoTercerizados(){

		return qtdFuncionariosOperacaoTercerizados;
	}

	/**
	 * @param qtdFuncionariosOperacaoTercerizados
	 *            the qtdFuncionariosOperacaoTercerizados to set
	 */
	public void setQtdFuncionariosOperacaoTercerizados(String qtdFuncionariosOperacaoTercerizados){

		this.qtdFuncionariosOperacaoTercerizados = qtdFuncionariosOperacaoTercerizados;
	}

	/**
	 * @return the qtdSulfatoAluminio
	 */
	public String getQtdSulfatoAluminio(){

		return qtdSulfatoAluminio;
	}

	/**
	 * @param qtdSulfatoAluminio
	 *            the qtdSulfatoAluminio to set
	 */
	public void setQtdSulfatoAluminio(String qtdSulfatoAluminio){

		this.qtdSulfatoAluminio = qtdSulfatoAluminio;
	}

	/**
	 * @return the qtdCloroGasoso
	 */
	public String getQtdCloroGasoso(){

		return qtdCloroGasoso;
	}

	/**
	 * @param qtdCloroGasoso
	 *            the qtdCloroGasoso to set
	 */
	public void setQtdCloroGasoso(String qtdCloroGasoso){

		this.qtdCloroGasoso = qtdCloroGasoso;
	}

	/**
	 * @return the qtdHipocloritoSodio
	 */
	public String getQtdHipocloritoSodio(){

		return qtdHipocloritoSodio;
	}

	/**
	 * @param qtdHipocloritoSodio
	 *            the qtdHipocloritoSodio to set
	 */
	public void setQtdHipocloritoSodio(String qtdHipocloritoSodio){

		this.qtdHipocloritoSodio = qtdHipocloritoSodio;
	}

	/**
	 * @return the quantidadeFluor
	 */
	public String getQuantidadeFluor(){

		return quantidadeFluor;
	}

	/**
	 * @param quantidadeFluor
	 *            the quantidadeFluor to set
	 */
	public void setQuantidadeFluor(String quantidadeFluor){

		this.quantidadeFluor = quantidadeFluor;
	}

	/**
	 * @return the qtdConsumoEnergia
	 */
	public String getQtdConsumoEnergia(){

		return qtdConsumoEnergia;
	}

	/**
	 * @param qtdConsumoEnergia
	 *            the qtdConsumoEnergia to set
	 */
	public void setQtdConsumoEnergia(String qtdConsumoEnergia){

		this.qtdConsumoEnergia = qtdConsumoEnergia;
	}

	/**
	 * @return the qtdHorasParalisadas
	 */
	public String getQtdHorasParalisadas(){

		return qtdHorasParalisadas;
	}

	/**
	 * @param qtdHorasParalisadas
	 *            the qtdHorasParalisadas to set
	 */
	public void setQtdHorasParalisadas(String qtdHorasParalisadas){

		this.qtdHorasParalisadas = qtdHorasParalisadas;
	}

	public LocalidadeDadoOperacional setFormValues(LocalidadeDadoOperacional localidadeDadoOperacional, Localidade localidade){

		// Metodo usado para setar todos os valores do Form na base de dados
		localidadeDadoOperacional.setAnoMesReferencia(Integer.valueOf(Util.formatarMesAnoParaAnoMes(this.getMesAnoReferencia())));
		localidadeDadoOperacional.setLocalidade(localidade);
		localidadeDadoOperacional.setVolumeProduzido(Util.formatarStringParaBigDecimal(this.getVolumeProduzido()));
		localidadeDadoOperacional.setExtensaoRedeAgua(Util.formatarStringParaBigDecimal(this.getExtensaoRedeAgua()));
		localidadeDadoOperacional.setExtensaoRedeEsgoto(Util.formatarStringParaBigDecimal(this.getExtensaoRedeEsgoto()));
		localidadeDadoOperacional.setQtdFuncionariosAdministracao(Util.obterInteger(this.getQtdFuncionariosAdministracao()));
		localidadeDadoOperacional.setQtdFuncionariosAdministracaoTercerizados(Util.obterInteger(this
						.getQtdFuncionariosAdministracaoTercerizados()));
		localidadeDadoOperacional.setQtdFuncionariosOperacao(Util.obterInteger(this.getQtdFuncionariosOperacao()));
		localidadeDadoOperacional.setQtdFuncionariosOperacaoTercerizados(Util.obterInteger(this.getQtdFuncionariosOperacaoTercerizados()));
		localidadeDadoOperacional.setQtdSulfatoAluminio(Util.obterInteger(this.getQtdSulfatoAluminio()));
		localidadeDadoOperacional.setQtdCloroGasoso(Util.obterInteger(this.getQtdCloroGasoso()));
		localidadeDadoOperacional.setQtdHipocloritoSodio(Util.obterInteger(this.getQtdHipocloritoSodio()));
		localidadeDadoOperacional.setQuantidadeFluor(Util.obterInteger(this.getQuantidadeFluor()));

		// QtdConsumoEnergia
		BigDecimal qtdConsumoEnergia = null;
		String qtdConsumoEnergiaStr = this.getQtdConsumoEnergia();

		if(!Util.isVazioOuBranco(qtdConsumoEnergiaStr)){
			qtdConsumoEnergia = Util.formatarStringParaBigDecimal(qtdConsumoEnergiaStr, 2, false);
		}

		localidadeDadoOperacional.setQtdConsumoEnergia(qtdConsumoEnergia);

		// QtdHorasParalisadas
		BigDecimal qtdHorasParalisadas = null;
		String qtdHorasParalisadasStr = this.getQtdHorasParalisadas();

		if(!Util.isVazioOuBranco(qtdHorasParalisadasStr)){
			qtdHorasParalisadas = Util.formatarStringParaBigDecimal(qtdHorasParalisadasStr, 2, false);
		}

		localidadeDadoOperacional.setQtdHorasParalisadas(qtdHorasParalisadas);

		localidadeDadoOperacional.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

		return localidadeDadoOperacional;
	}
}
