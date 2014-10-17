/**
 * 
 */

package br.com.procenge.parametrosistema.impl;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.parametrosistema.api.ParametroSistema;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * @author fmarconi
 */
@ControleAlteracao
public class ParametroSistemaValor
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_PARAMETRO_ATUALIZAR = 323904;

	public static final int ATRIBUTOS_PARAMETRO_INSERIR = 323903;

	public static final int ATRIBUTOS_PARAMETRO_REMOVER = 323902;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR, ATRIBUTOS_PARAMETRO_INSERIR, ATRIBUTOS_PARAMETRO_REMOVER})
	private Long id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR, ATRIBUTOS_PARAMETRO_INSERIR, ATRIBUTOS_PARAMETRO_REMOVER})
	private String valor;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR, ATRIBUTOS_PARAMETRO_INSERIR, ATRIBUTOS_PARAMETRO_REMOVER})
	private String descricao;

	@ControleAlteracao(value = "parametroSistema", funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR, ATRIBUTOS_PARAMETRO_INSERIR, ATRIBUTOS_PARAMETRO_REMOVER})
	private ParametroSistema parametroSistema;

	public void setParametroSistema(ParametroSistema parametroSistema){

		this.parametroSistema = parametroSistema;
	}

	public ParametroSistema getParametroSistema(){

		return parametroSistema;
	}

	public void setId(Long id){

		this.id = id;
	}

	public Long getId(){

		return id;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public String getValor(){

		return valor;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricao(){

		return descricao;
	}

	public Map validarDados(){

		Map<String, String> erros = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		String camposObrigatorios = null;

		if(this.parametroSistema == null || this.parametroSistema.getChavePrimaria() < 1){
			sb.append(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.ENTIDADE_ROTULO_PARAMETRO_SISTEMA));
			sb.append(Constantes.STRING_VIRGULA_ESPACO);
		}

		camposObrigatorios = sb.toString();

		if(camposObrigatorios.length() > 0){
			erros.put(Constantes.ERRO_NEGOCIO_CAMPOS_OBRIGATORIOS, camposObrigatorios.substring(0, sb.toString().length() - 2));
		}

		return erros;
	}

	@Override
	public Date getUltimaAlteracao(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		// TODO Auto-generated method stub

	}

	@Override
	public Filtro retornaFiltro(){

		Filtro filtroParametroSistemaValor = new Filtro() {};

		filtroParametroSistemaValor.adicionarParametro(new ParametroSimples("id", this.getId()));

		return filtroParametroSistemaValor;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "parametroSistema.chavePrimaria"};

		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Valor", "ParametroSistema"};
		return labels;
	}

}
