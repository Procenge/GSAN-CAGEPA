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
import br.com.procenge.parametrosistema.api.TipoParametroSistema;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * @author gmatos
 */
@ControleAlteracao()
public class ParametroSistemaImpl
				extends ObjetoTransacao
				implements ParametroSistema {

	public static final int ATRIBUTOS_PARAMETRO_ATUALIZAR = 271377;

	private static final long serialVersionUID = 8368890327866333280L;

	private long chavePrimaria;

	private int versao;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR})
	private String codigo;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR})
	private String descricao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR})
	private TipoParametroSistema tipoParametroSistema;

	private String classeEntidade;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR})
	private String valor;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_PARAMETRO_ATUALIZAR})
	private int uso;

	public long getChavePrimaria(){

		return chavePrimaria;
	}

	public void setChavePrimaria(long chavePrimaria){

		this.chavePrimaria = chavePrimaria;
	}

	public int getVersao(){

		return versao;
	}

	public void setVersao(int versao){

		this.versao = versao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public TipoParametroSistema getTipoParametroSistema(){

		return tipoParametroSistema;
	}

	public void setTipoParametroSistema(TipoParametroSistema tipoParametroSistema){

		this.tipoParametroSistema = tipoParametroSistema;
	}

	public String getClasseEntidade(){

		return classeEntidade;
	}

	public void setClasseEntidade(String classeEntidade){

		this.classeEntidade = classeEntidade;
	}

	public String getValor(){

		return valor;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public int getUso(){

		return uso;
	}

	public void setUso(int uso){

		this.uso = uso;
	}

	public Map validarDados(){

		Map<String, String> erros = new HashMap<String, String>();
		StringBuilder sb = new StringBuilder();
		String camposObrigatorios = null;

		if(this.codigo == null || this.codigo.length() == 0){
			sb.append(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_SISTEMA_ROTULO_CODIGO));
			sb.append(Constantes.STRING_VIRGULA_ESPACO);
		}

		if(this.descricao == null || this.descricao.length() == 0){
			sb.append(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_SISTEMA_ROTULO_DESCRICAO));
			sb.append(Constantes.STRING_VIRGULA_ESPACO);
		}

		if(this.tipoParametroSistema == null || this.tipoParametroSistema.getCodigo() < 1){
			sb.append(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, Constantes.PARAMETRO_SISTEMA_ROTULO_TIPO));
			sb.append(Constantes.STRING_VIRGULA_ESPACO);
		}

		// if(this.valor == null || this.valor.length() == 0){
		// sb.append(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
		// Constantes.PARAMETRO_SISTEMA_ROTULO_VALOR));
		// sb.append(Constantes.STRING_VIRGULA_ESPACO);
		// }

		camposObrigatorios = sb.toString();

		if(camposObrigatorios.length() > 0){
			erros.put(Constantes.ERRO_NEGOCIO_CAMPOS_OBRIGATORIOS, camposObrigatorios.substring(0, sb.toString().length() - 2));
		}

		return erros;
	}

	public int compareTo(ParametroSistema o){

		return this.getCodigo().compareToIgnoreCase(o.getCodigo());

	}

	public void incrementarVersao(){

		// TODO Auto-generated method stub

	}

	public void carregarLazy(){

		// TODO Auto-generated method stub

	}

	@Override
	public Filtro retornaFiltro(){

		// FiltroParametroSistema filtroParametroSistema = new FiltroParametroSistema();

		// filtroParametroSistema.adicionarParametro(new
		// ParametroSimples(FiltroParametroSistema.CHAVE_PRIMARIA, this.getChavePrimaria()));

		Filtro filtroParametroSistema = new Filtro() {};

		filtroParametroSistema.adicionarParametro(new ParametroSimples("chavePrimaria", this.getChavePrimaria()));

		return filtroParametroSistema;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[2];
		retorno[0] = "chavePrimaria";
		retorno[1] = "codigo";
		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"codigo"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"codigo"};
		return labels;
	}

}
