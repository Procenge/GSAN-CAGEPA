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

package gcom.cadastro.cliente;

import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class ClienteEndereco
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_CLIENTE_ENDERECO_INSERIR = 188;

	public static final int ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR = 331;

	public static final int ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER = 571;

	public static final int ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER = 346;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private String numero;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private String complemento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private Short indicadorEnderecoCorrespondencia;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroClienteEndereco.ENDERECO_TIPO, funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private EnderecoTipo enderecoTipo;

	@ControleAlteracao(value = FiltroClienteEndereco.CLIENTE, funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private gcom.cadastro.cliente.Cliente cliente;

	@ControleAlteracao(value = FiltroClienteEndereco.ENDERECO_REFERENCIA, funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private EnderecoReferencia enderecoReferencia;

	@ControleAlteracao(value = FiltroClienteEndereco.LOGRADOURO_CEP, funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private LogradouroCep logradouroCep;

	@ControleAlteracao(value = FiltroClienteEndereco.LOGRADOURO_BAIRRO, funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private LogradouroBairro logradouroBairro;

	public final static Short INDICADOR_ENDERECO_CORRESPONDENCIA = new Short("1");

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_ENDERECO_INSERIR, ATRIBUTOS_CLIENTE_ENDERECO_ATUALIZAR, ATRIBUTOS_INSIRIR_CLIENTE_ENDERECO_REMOVER, ATRIBUTOS_ATUALIZAR_CLIENTE_ENDERECO_REMOVER})
	private Integer logradouro;

	private Imovel imovel;

	/** full constructor */
	public ClienteEndereco(String numero, String complemento, Short indicadorEnderecoCorrespondencia, Date ultimaAlteracao,
							EnderecoTipo enderecoTipo, gcom.cadastro.cliente.Cliente cliente, EnderecoReferencia enderecoReferencia,
							LogradouroCep logradouroCep, LogradouroBairro logradouroBairro, Integer logradouro) {

		this.numero = numero;
		this.complemento = complemento;
		this.indicadorEnderecoCorrespondencia = indicadorEnderecoCorrespondencia;
		this.ultimaAlteracao = ultimaAlteracao;
		this.enderecoTipo = enderecoTipo;
		this.cliente = cliente;
		this.enderecoReferencia = enderecoReferencia;
		this.logradouroCep = logradouroCep;
		this.logradouroBairro = logradouroBairro;
		this.logradouro = logradouro;
	}

	/** default constructor */
	public ClienteEndereco() {

	}

	/** minimal constructor */
	public ClienteEndereco(EnderecoTipo enderecoTipo, gcom.cadastro.cliente.Cliente cliente, EnderecoReferencia enderecoReferencia,
							LogradouroCep logradouroCep, LogradouroBairro logradouroBairro) {

		this.enderecoTipo = enderecoTipo;
		this.cliente = cliente;
		this.enderecoReferencia = enderecoReferencia;
		this.logradouroCep = logradouroCep;
		this.logradouroBairro = logradouroBairro;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNumero(){

		return this.numero;
	}

	public void setNumero(String numero){

		this.numero = numero;
	}

	public String getComplemento(){

		return this.complemento;
	}

	public void setComplemento(String complemento){

		this.complemento = complemento;
	}

	public Short getIndicadorEnderecoCorrespondencia(){

		return this.indicadorEnderecoCorrespondencia;
	}

	public void setIndicadorEnderecoCorrespondencia(Short indicadorEnderecoCorrespondencia){

		this.indicadorEnderecoCorrespondencia = indicadorEnderecoCorrespondencia;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public EnderecoTipo getEnderecoTipo(){

		return this.enderecoTipo;
	}

	public void setEnderecoTipo(EnderecoTipo enderecoTipo){

		this.enderecoTipo = enderecoTipo;
	}

	public gcom.cadastro.cliente.Cliente getCliente(){

		return this.cliente;
	}

	public void setCliente(gcom.cadastro.cliente.Cliente cliente){

		this.cliente = cliente;
	}

	public EnderecoReferencia getEnderecoReferencia(){

		return this.enderecoReferencia;
	}

	public void setEnderecoReferencia(EnderecoReferencia enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	/**
	 * Retorna o valor do endereco Formatado
	 * - Descricao do tipo do logradouro
	 * - Descricao do título do logradouro
	 * - Nome do logradouro
	 * - Referência
	 * - Número
	 * - Complemento
	 * - Bairro
	 * - Municipio
	 * - Estado
	 * - CEP
	 * 
	 * @return String
	 *         Endereço Formatado
	 */
	public String getEnderecoFormatado(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(new Integer("0"))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				// concatena o logradouro tipo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao() != null){
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				// concatena o logradouro titulo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao() != null){
					endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao().trim();
				}
			}

			// concatena o logradouro do imovel
			endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricao() != null && !this.getEnderecoReferencia().getDescricao().equals("")){
					endereco = endereco + " - " + this.getEnderecoReferencia().getDescricao().trim();
				}
			}

			// concate o numero do imovel
			if(this.getNumero() != null){
				endereco = endereco + " - " + this.getNumero().trim();
			}

			if(this.getComplemento() != null && !this.getComplemento().trim().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplemento().trim();
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
				endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getNome().trim();

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getNome() != null){
						endereco = endereco + " " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();
					}
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){
						endereco = endereco + " "
										+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
					}
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getLogradouroCep().getCep().getCepFormatado() != null){
					endereco = endereco + " " + this.getLogradouroCep().getCep().getCepFormatado().trim();
				}
			}

		}

		return endereco;
	}

	/**
	 * Retorna o valor do endereco Formatado Abreviado
	 * - Descricao do tipo do logradouro (Abreviado)
	 * - Descricao do título do logradouro (Abreviado)
	 * - Nome do logradouro
	 * - Referência (Abreviado)
	 * - Número
	 * - Complemento
	 * - Bairro
	 * - Municipio
	 * - Estado
	 * - CEP
	 * 
	 * @return O valor de enderecoFormatado <
	 */
	public String getEnderecoFormatadoAbreviado(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf(0))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro tipo do imovel
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro titulo do imovel
					endereco = endereco + " "
									+ this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().trim();
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){
				endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricaoAbreviada() != null
								&& !this.getEnderecoReferencia().getDescricaoAbreviada().equals("")){
					endereco = endereco + ", " + this.getEnderecoReferencia().getDescricaoAbreviada().trim();
				}
			}

			if(this.getNumero() != null && !this.getNumero().equals("")){
				// concate o numero do imovel
				if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
					endereco = endereco + " " + this.getNumero().trim();
				}else{
					endereco = endereco + ", " + this.getNumero().trim();
				}
			}

			if(this.getComplemento() != null && !this.getComplemento().trim().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplemento().trim();
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
				if(this.getLogradouroBairro().getBairro().getNome() != null){
					endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getNome().trim();
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getNome() != null){
						endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();
					}

					if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
									&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
						if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){
							endereco = endereco + " - "
											+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
						}
					}
				}

			}else if(this.getLogradouroCep().getLogradouro().getMunicipio() != null){
				endereco = endereco + " - " + this.getLogradouroCep().getLogradouro().getMunicipio().getNome().trim();
				if(this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao().getSigla() != null){
						endereco = endereco + " "
										+ this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao().getSigla().trim();
					}
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getLogradouroCep().getCep().getCepFormatado() != null){
					endereco = endereco + " " + this.getLogradouroCep().getCep().getCepFormatado().trim();
				}
			}

		}

		return endereco;
	}

	/**
	 * Retorna o valor do endereco mais que o endereco Formatado Abreviado
	 * - Descricao do tipo do logradouro (Abreviado)
	 * - Descricao do título do logradouro (Abreviado)
	 * - Nome do logradouro
	 * - Referência (Abreviado)
	 * - Número
	 * - Complemento
	 * 
	 * @return O valor de enderecoFormatado <
	 */
	public String getEnderecoAbreviado(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf(0))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro tipo do imovel
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro titulo do imovel
					endereco = endereco + " "
									+ this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().trim();
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){
				endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricaoAbreviada() != null
								&& !this.getEnderecoReferencia().getDescricaoAbreviada().equals("")){
					endereco = endereco + ", " + this.getEnderecoReferencia().getDescricaoAbreviada().trim();
				}
			}

			if(this.getNumero() != null && !this.getNumero().equals("")){
				// concate o numero do imovel
				endereco = endereco + ", " + this.getNumero().trim();
			}

			if(this.getComplemento() != null && !this.getComplemento().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplemento().trim();
			}

		}

		return endereco;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param other
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof ClienteEndereco)){
			return false;
		}
		ClienteEndereco castOther = (ClienteEndereco) other;

		return new EqualsBuilder().append(this.getCliente().getId(), castOther.getCliente().getId()).append(
						this.getLogradouroBairro() != null ? this.getLogradouroBairro().getBairro().getId() : null,
						castOther.getLogradouroBairro() != null ? castOther.getLogradouroBairro().getBairro().getId() : null).append(
						this.getLogradouroCep() != null ? this.getLogradouroCep().getCep().getCepId() : null,
						castOther.getLogradouroCep() != null ? castOther.getLogradouroCep().getCep().getCepId() : null).append(
						this.getComplemento(), castOther.getComplemento()).append(this.getUltimaAlteracao().getTime(),
						castOther.getUltimaAlteracao().getTime()).isEquals();
	}

	public LogradouroBairro getLogradouroBairro(){

		return logradouroBairro;
	}

	public void setLogradouroBairro(LogradouroBairro logradouroBairro){

		this.logradouroBairro = logradouroBairro;
	}

	public LogradouroCep getLogradouroCep(){

		return logradouroCep;
	}

	public void setLogradouroCep(LogradouroCep logradouroCep){

		this.logradouroCep = logradouroCep;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
		filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.ID, this.getId()));
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.CLIENTE);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_TIPO);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_REFERENCIA);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.LOGRADOURO_CEP);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.LOGRADOURO_BAIRRO);
		return filtroClienteEndereco;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public Integer getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(Integer logradouro){

		this.logradouro = logradouro;
	}

	/**
	 * @return O valor de endereco formatado
	 */
	public String getEnderecoFormatadoAbreviadoComDetalhamento(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf(0))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro tipo do imovel
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro titulo do imovel
					endereco = endereco + " "
									+ this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().trim();
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){
				endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricaoAbreviada() != null
								&& !this.getEnderecoReferencia().getDescricaoAbreviada().equals("")){
					endereco = endereco + ", " + this.getEnderecoReferencia().getDescricaoAbreviada().trim();
				}
			}

			if(this.getNumero() != null && !this.getNumero().trim().equals("")){
				// concate o numero do imovel
				endereco = endereco + ", n° " + this.getNumero().trim();
			}

			if(this.getComplemento() != null && !this.getComplemento().trim().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplemento().trim();
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
				if(this.getLogradouroBairro().getBairro().getNome() != null){
					endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getNome().trim();
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					if(this.getLogradouroBairro().getBairro().getMunicipio().getNome() != null){
						endereco = endereco + ", Cidade de " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();
					}

					if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
									&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
						if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){
							endereco = endereco + " - "
											+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
						}
					}
				}

			}else if(this.getLogradouroCep().getLogradouro().getMunicipio() != null){
				endereco = endereco + ", Cidade de " + this.getLogradouroCep().getLogradouro().getMunicipio().getNome().trim();
				if(this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					if(this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao().getSigla() != null){
						endereco = endereco + " - "
										+ this.getLogradouroCep().getLogradouro().getMunicipio().getUnidadeFederacao().getSigla().trim();
					}
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do imóvel
				if(this.getLogradouroCep().getCep().getCepFormatado() != null){
					endereco = endereco + ", CEP: " + this.getLogradouroCep().getCep().getCepFormatado().trim();
				}
			}

		}

		return endereco;
	}

	/**
	 * @return O valor de endereco formatado
	 */
	public String getEnderecoAbreviadoComDetalhamento(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(Integer.valueOf(0))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro tipo do imovel
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro titulo do imovel
					endereco = endereco + " "
									+ this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().trim();
				}
			}

			// concatena o logradouro do imovel
			if(this.getLogradouroCep().getLogradouro().getNome() != null){
				endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();
			}

			if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
				if(this.getEnderecoReferencia().getDescricaoAbreviada() != null
								&& !this.getEnderecoReferencia().getDescricaoAbreviada().equals("")){
					endereco = endereco + ", " + this.getEnderecoReferencia().getDescricaoAbreviada().trim();
				}
			}

			if(this.getNumero() != null && !this.getNumero().trim().equals("")){
				// concate o numero do imovel
				endereco = endereco + ", n° " + this.getNumero().trim();
			}

			if(this.getComplemento() != null && !this.getComplemento().trim().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplemento().trim();
			}

		}

		return endereco;
	}

	/**
	 * Retorna o valor do endereco Completo
	 * - Descricao do tipo do logradouro
	 * - Descricao do título do logradouro
	 * - Nome do logradouro
	 * - Referência
	 * - Número
	 * - Complemento
	 * - Bairro
	 * - Municipio
	 * - Estado
	 * - CEP
	 * 
	 * @return String
	 *         Endereço Formatado
	 */
	public String getEnderecoFormatadoSemCep(){

		String endereco = "";

		// verifica se o logradouro do imovel é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(new Integer("0"))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				// concatena o logradouro tipo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao() != null){
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				// concatena o logradouro titulo do imovel
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao() != null){
					endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao().trim();
				}
			}

			// concatena o logradouro do imovel
			endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();

		}else if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getLogradouro() != null
						&& !this.getLogradouroBairro().getLogradouro().getId().equals(new Integer("0"))){

			// verifica se o logradouro tipo do imovel é diferente de null
			if(this.getLogradouroBairro().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroBairro().getLogradouro().getLogradouroTipo().equals("")){
				// concatena o logradouro tipo do imovel
				if(this.getLogradouroBairro().getLogradouro().getLogradouroTipo().getDescricao() != null){
					endereco = this.getLogradouroBairro().getLogradouro().getLogradouroTipo().getDescricao().trim();
				}
			}
			// verifica se o logradouro titulo do imovel é diferente de null
			if(this.getLogradouroBairro().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroBairro().getLogradouro().getLogradouroTitulo().equals("")){
				// concatena o logradouro titulo do imovel
				if(this.getLogradouroBairro().getLogradouro().getLogradouroTitulo().getDescricao() != null){
					endereco = endereco + " " + this.getLogradouroBairro().getLogradouro().getLogradouroTitulo().getDescricao().trim();
				}
			}

			// concatena o logradouro do imovel
			endereco = endereco + " " + this.getLogradouroBairro().getLogradouro().getNome().trim();
		}

		if(this.getEnderecoReferencia() != null && !this.getEnderecoReferencia().equals("")){
			if(this.getEnderecoReferencia().getDescricao() != null && !this.getEnderecoReferencia().getDescricao().equals("")){
				endereco = endereco + " - " + this.getEnderecoReferencia().getDescricao().trim();
			}
		}

		// concate o numero do imovel
		if(this.getNumero() != null){
			endereco = endereco + " - " + this.getNumero().trim();
		}

		if(this.getComplemento() != null && !this.getComplemento().trim().equalsIgnoreCase("")){
			endereco = endereco + " - " + this.getComplemento().trim();
		}

		if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
						&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
			endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getNome().trim();

			if(this.getLogradouroBairro().getBairro().getMunicipio() != null
							&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
				if(this.getLogradouroBairro().getBairro().getMunicipio().getNome() != null){
					endereco = endereco + " " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();
				}
			}

			if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
							&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
				if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla() != null){
					endereco = endereco + " "
									+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
				}
			}
		}

		if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
			// concatena o cep formatado do imóvel
			if(this.getLogradouroCep().getCep().getCepFormatado() != null){
				endereco = endereco + " " + this.getLogradouroCep().getCep().getCepFormatado().trim();
			}
		}

		return endereco;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

}
