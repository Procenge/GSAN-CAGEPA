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

package gcom.micromedicao.consumo;

import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hibernate CodeGenerator
 */
public class ConsumoAnormalidade
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	private String descricaoAbreviada;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	private ConsumoTipo consumoTipoLeituraAnteriorProjetada;

	private ConsumoTipo consumoTipoLeituraAnteriorReal;

	private ConsumoAnormalidadeConsumo consumoAnormalidadeConsumoLeituraAnteriorProjetada;

	private ConsumoAnormalidadeConsumo consumoAnormalidadeConsumoLeituraAnteriorReal;

	private ConsumoAnormalidadeLeitura consumoAnormalidadeLeituraAnteriorProjetada;

	private ConsumoAnormalidadeLeitura consumoAnormalidadeLeituraAnteriorReal;

	private String codigoConstante;

	private Short indicadorCreditoConsumo;

	// CONTANTES
	public static Integer CONSUMO_NORMAL;

	public static Integer CONSUMO_ALTERADO;

	public static Integer CONSUMO_INFORMADO;

	public static Integer CONSUMO_RETIFICADO;

	public static Integer BAIXO_CONSUMO;

	public static Integer ESTOURO_CONSUMO;

	public static Integer ALTO_CONSUMO;

	public static Integer LEITURA_ATUAL_MENOR_PROJETADA;

	public static Integer LEITURA_ATUAL_MENOR_ANTERIOR;

	public static Integer HIDROMETRO_SUBSTITUIDO_INFORMADO;

	public static Integer LEITURA_NAO_INFORMADA;

	public static Integer ESTOURO_CONSUMO_COBRANCA_MEDIA;

	public static Integer CONSUMO_MINIMO_FIXADO;

	public static Integer FORA_FAIXA;

	public static Integer HIDROMETRO_SUBSTITUIDO_NAO_INFORMADO;

	public static Integer FATURAMENTO_ANTECIPADO;

	public static Integer VIRADA_HIDROMETRO;

	public static Integer HIDROMETRO_INSTALADO;

	public static Integer ALTO_CONSUMO_COM_DESCONTO;

	public static Integer LEITURA_IGUAL_ANTERIOR;

	public static Integer CONSUMO_MUITO_ABAIXO_MEDIA;

	public static Integer HIDROMETRO_NOVO;

	public static Integer CONSUMO_COM_LEITURA_AJUSTADA;

	public static Integer ESTOURO_ACUM_ACIMA_TETO;

	public static Integer LEIT_ATUAL_IGUAL_LEIT_ANT;

	public static Integer AC_ANORMAL_LEIT_ANTERIOR;

	public static Integer AC_FALTA_LEITURA_ANTERIOR;

	public static Integer LEITURA_ATUAL_MENOR_PROJETADA_CREDITO_CONSUMO;

	public static Integer ALTO_CONSUMO_FALTA_LEITURA;

	public static Integer ALTO_CONSUMO_ANORMALIDADE_LEITURA;

	public ConsumoAnormalidade(String descricao, String descricaoAbreviada, Short indicadorUso, Date ultimaAlteracao) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public ConsumoAnormalidade() {

	}

	public ConsumoAnormalidade(int id) {

		this.id = id;
	}

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

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
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

	public ConsumoTipo getConsumoTipoLeituraAnteriorProjetada(){

		return consumoTipoLeituraAnteriorProjetada;
	}

	public void setConsumoTipoLeituraAnteriorProjetada(ConsumoTipo consumoTipoLeituraAnteriorProjetada){

		this.consumoTipoLeituraAnteriorProjetada = consumoTipoLeituraAnteriorProjetada;
	}

	public ConsumoTipo getConsumoTipoLeituraAnteriorReal(){

		return consumoTipoLeituraAnteriorReal;
	}

	public void setConsumoTipoLeituraAnteriorReal(ConsumoTipo consumoTipoLeituraAnteriorReal){

		this.consumoTipoLeituraAnteriorReal = consumoTipoLeituraAnteriorReal;
	}

	public ConsumoAnormalidadeConsumo getConsumoAnormalidadeConsumoLeituraAnteriorProjetada(){

		return consumoAnormalidadeConsumoLeituraAnteriorProjetada;
	}

	public void setConsumoAnormalidadeConsumoLeituraAnteriorProjetada(
					ConsumoAnormalidadeConsumo consumoAnormalidadeConsumoLeituraAnteriorProjetada){

		this.consumoAnormalidadeConsumoLeituraAnteriorProjetada = consumoAnormalidadeConsumoLeituraAnteriorProjetada;
	}

	public ConsumoAnormalidadeConsumo getConsumoAnormalidadeConsumoLeituraAnteriorReal(){

		return consumoAnormalidadeConsumoLeituraAnteriorReal;
	}

	public void setConsumoAnormalidadeConsumoLeituraAnteriorReal(ConsumoAnormalidadeConsumo consumoAnormalidadeConsumoLeituraAnteriorReal){

		this.consumoAnormalidadeConsumoLeituraAnteriorReal = consumoAnormalidadeConsumoLeituraAnteriorReal;
	}

	public ConsumoAnormalidadeLeitura getConsumoAnormalidadeLeituraAnteriorProjetada(){

		return consumoAnormalidadeLeituraAnteriorProjetada;
	}

	public void setConsumoAnormalidadeLeituraAnteriorProjetada(ConsumoAnormalidadeLeitura consumoAnormalidadeLeituraAnteriorProjetada){

		this.consumoAnormalidadeLeituraAnteriorProjetada = consumoAnormalidadeLeituraAnteriorProjetada;
	}

	public ConsumoAnormalidadeLeitura getConsumoAnormalidadeLeituraAnteriorReal(){

		return consumoAnormalidadeLeituraAnteriorReal;
	}

	public void setConsumoAnormalidadeLeituraAnteriorReal(ConsumoAnormalidadeLeitura consumoAnormalidadeLeituraAnteriorReal){

		this.consumoAnormalidadeLeituraAnteriorReal = consumoAnormalidadeLeituraAnteriorReal;
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public Short getIndicadorCreditoConsumo(){

		return indicadorCreditoConsumo;
	}

	public void setIndicadorCreditoConsumo(Short indicadorCreditoConsumo){

		this.indicadorCreditoConsumo = indicadorCreditoConsumo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Hebert Falcão
	 * @date 26/02/2012
	 */
	public static void inicializarConstantes(){

		CONSUMO_NORMAL = ConsumoAnormalidadeEnum.CONSUMO_NORMAL.getId();

		CONSUMO_ALTERADO = ConsumoAnormalidadeEnum.CONSUMO_ALTERADO.getId();

		CONSUMO_INFORMADO = ConsumoAnormalidadeEnum.CONSUMO_INFORMADO.getId();

		CONSUMO_RETIFICADO = ConsumoAnormalidadeEnum.CONSUMO_RETIFICADO.getId();

		BAIXO_CONSUMO = ConsumoAnormalidadeEnum.BAIXO_CONSUMO.getId();

		ESTOURO_CONSUMO = ConsumoAnormalidadeEnum.ESTOURO_CONSUMO.getId();

		ALTO_CONSUMO = ConsumoAnormalidadeEnum.ALTO_CONSUMO.getId();

		LEITURA_ATUAL_MENOR_PROJETADA = ConsumoAnormalidadeEnum.LEITURA_ATUAL_MENOR_PROJETADA.getId();

		LEITURA_ATUAL_MENOR_ANTERIOR = ConsumoAnormalidadeEnum.LEITURA_ATUAL_MENOR_ANTERIOR.getId();

		HIDROMETRO_SUBSTITUIDO_INFORMADO = ConsumoAnormalidadeEnum.HIDROMETRO_SUBSTITUIDO_INFORMADO.getId();

		LEITURA_NAO_INFORMADA = ConsumoAnormalidadeEnum.LEITURA_NAO_INFORMADA.getId();

		ESTOURO_CONSUMO_COBRANCA_MEDIA = ConsumoAnormalidadeEnum.ESTOURO_CONSUMO_COBRANCA_MEDIA.getId();

		CONSUMO_MINIMO_FIXADO = ConsumoAnormalidadeEnum.CONSUMO_MINIMO_FIXADO.getId();

		FORA_FAIXA = ConsumoAnormalidadeEnum.FORA_FAIXA.getId();

		HIDROMETRO_SUBSTITUIDO_NAO_INFORMADO = ConsumoAnormalidadeEnum.HIDROMETRO_SUBSTITUIDO_NAO_INFORMADO.getId();

		FATURAMENTO_ANTECIPADO = ConsumoAnormalidadeEnum.FATURAMENTO_ANTECIPADO.getId();

		VIRADA_HIDROMETRO = ConsumoAnormalidadeEnum.VIRADA_HIDROMETRO.getId();

		HIDROMETRO_INSTALADO = ConsumoAnormalidadeEnum.HIDROMETRO_INSTALADO.getId();

		ALTO_CONSUMO_COM_DESCONTO = ConsumoAnormalidadeEnum.ALTO_CONSUMO_COM_DESCONTO.getId();

		LEITURA_IGUAL_ANTERIOR = ConsumoAnormalidadeEnum.LEITURA_IGUAL_ANTERIOR.getId();

		CONSUMO_MUITO_ABAIXO_MEDIA = ConsumoAnormalidadeEnum.CONSUMO_MUITO_ABAIXO_MEDIA.getId();

		HIDROMETRO_NOVO = ConsumoAnormalidadeEnum.HIDROMETRO_NOVO.getId();

		CONSUMO_COM_LEITURA_AJUSTADA = ConsumoAnormalidadeEnum.CONSUMO_COM_LEITURA_AJUSTADA.getId();

		ESTOURO_ACUM_ACIMA_TETO = ConsumoAnormalidadeEnum.ESTOURO_ACUM_ACIMA_TETO.getId();

		AC_ANORMAL_LEIT_ANTERIOR = ConsumoAnormalidadeEnum.AC_ANORMAL_LEIT_ANTERIOR.getId();

		AC_FALTA_LEITURA_ANTERIOR = ConsumoAnormalidadeEnum.AC_FALTA_LEITURA_ANTERIOR.getId();

		LEITURA_ATUAL_MENOR_PROJETADA_CREDITO_CONSUMO = ConsumoAnormalidadeEnum.LEITURA_ATUAL_MENOR_PROJETADA_CREDITO_CONSUMO.getId();

		ALTO_CONSUMO_FALTA_LEITURA = ConsumoAnormalidadeEnum.ALTO_CONSUMO_FALTA_LEITURA.getId();

		ALTO_CONSUMO_ANORMALIDADE_LEITURA = ConsumoAnormalidadeEnum.ALTO_CONSUMO_ANORMALIDADE_LEITURA.getId();

		// CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA =
		// ConsumoAnormalidadeEnum.CONFIRMAR_DADOS_CORTE_LIGACAO_AGUA.getId();
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Hebert Falcão
	 * @date 26/02/2012
	 */
	public static enum ConsumoAnormalidadeEnum {
		CONSUMO_NORMAL, CONSUMO_ALTERADO, CONSUMO_INFORMADO, CONSUMO_RETIFICADO, BAIXO_CONSUMO, ESTOURO_CONSUMO, ALTO_CONSUMO,
		LEITURA_ATUAL_MENOR_PROJETADA, LEITURA_ATUAL_MENOR_ANTERIOR, HIDROMETRO_SUBSTITUIDO_INFORMADO, LEITURA_NAO_INFORMADA,
		ESTOURO_CONSUMO_COBRANCA_MEDIA, CONSUMO_MINIMO_FIXADO, FORA_FAIXA, HIDROMETRO_SUBSTITUIDO_NAO_INFORMADO, FATURAMENTO_ANTECIPADO,
		VIRADA_HIDROMETRO, HIDROMETRO_INSTALADO, ALTO_CONSUMO_COM_DESCONTO, LEITURA_IGUAL_ANTERIOR, CONSUMO_MUITO_ABAIXO_MEDIA,
		HIDROMETRO_NOVO, CONSUMO_COM_LEITURA_AJUSTADA, ESTOURO_ACUM_ACIMA_TETO, AC_ANORMAL_LEIT_ANTERIOR, AC_FALTA_LEITURA_ANTERIOR,
		LEITURA_ATUAL_MENOR_PROJETADA_CREDITO_CONSUMO, ALTO_CONSUMO_FALTA_LEITURA, ALTO_CONSUMO_ANORMALIDADE_LEITURA;

		private Integer id = -1;

		private ConsumoAnormalidadeEnum() {

			try{
				ConsumoAnormalidade consumoAnormalidade = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								ConsumoAnormalidade.class);

				if(consumoAnormalidade != null){
					id = consumoAnormalidade.getId();
				}
			}catch(ErroRepositorioException e){
				e.printStackTrace();
				throw new SistemaException(e, e.getMessage());
			}
		}

		public Integer getId(){

			return id;
		}
	}

}
