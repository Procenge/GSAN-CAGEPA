/**
 * 
 */

package gcom.cadastro.cliente;

import gcom.arrecadacao.banco.Agencia;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Bruno Ferreira dos Santos
 */
@ControleAlteracao()
public class ClienteResponsavel
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR = 1480;

	public static final int ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR = 1480;

	public static final int ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER = 1480;

	/**
	 * identifier field
	 */
	private Integer id;

	@ControleAlteracao(value = FiltroClienteResponsavel.CLIENTE, funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private Cliente cliente;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private Short indMulta;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private Short indJuros;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private Short indCorrecao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private Short indImpostoFederal;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private Short indUso;

	@ControleAlteracao(value = FiltroClienteResponsavel.AGENCIA, funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private Agencia agencia;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_CLIENTE_RESPONSAVEL_INSERIR, ATRIBUTOS_CLIENTE_RESPONSAVEL_ATUALIZAR, ATRIBUTOS_CLIENTE_RESPONSAVEL_REMOVER})
	private String contaBancaria;

	/**
	 * nullable persistent field
	 */
	private Date ultimaAlteracao;

	public ClienteResponsavel() {

	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Cliente getCliente(){

		return cliente;
	}

	public void setCliente(Cliente cliente){

		this.cliente = cliente;
	}

	public Short getIndMulta(){

		return indMulta;
	}

	public void setIndMulta(Short indMulta){

		this.indMulta = indMulta;
	}

	public Short getIndJuros(){

		return indJuros;
	}

	public void setIndJuros(Short indJuros){

		this.indJuros = indJuros;
	}

	public Short getIndCorrecao(){

		return indCorrecao;
	}

	public void setIndCorrecao(Short indCorrecao){

		this.indCorrecao = indCorrecao;
	}

	public Short getIndImpostoFederal(){

		return indImpostoFederal;
	}

	public void setIndImpostoFederal(Short indImpostoFederal){

		this.indImpostoFederal = indImpostoFederal;
	}

	public Short getIndUso(){

		return indUso;
	}

	public void setIndUso(Short indUso){

		this.indUso = indUso;
	}

	public Agencia getAgencia(){

		return agencia;
	}

	public void setAgencia(Agencia agencia){

		this.agencia = agencia;
	}

	public String getContaBancaria(){

		return contaBancaria;
	}

	public void setContaBancaria(String contaBancaria){

		this.contaBancaria = contaBancaria;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "cliente.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Cliente Responsavel", "Cliente"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroClienteResponsavel filtroClienteResponsavel = new FiltroClienteResponsavel();
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteTipoEspecial.ID, this.getId()));
		filtroClienteResponsavel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteResponsavel.CLIENTE);
		filtroClienteResponsavel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteResponsavel.AGENCIA);
		return filtroClienteResponsavel;
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

}
