
package gcom.gui.operacional;

import gcom.cadastro.localidade.Localidade;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.ZonaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Atualizar ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class AtualizarZonaAbastecimentoActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String sistemaAbastecimento;

	private String indicadorUso;

	private String ultimaAlteracao;

	private String idDistritoOperacional;

	private String idLocalidade;

	private String faixaPressaoInferior;

	private String faixaPressaoSuperior;

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

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(String sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	// Esse método carrega todos os valores da base de dados
	// necesários para exibição da tela AtualizarZonaAbastecimento.

	/**
	 * @return the idDistritoOperacional
	 */
	public String getIdDistritoOperacional(){

		return idDistritoOperacional;
	}

	/**
	 * @param idDistritoOperacional
	 *            the idDistritoOperacional to set
	 */
	public void setIdDistritoOperacional(String idDistritoOperacional){

		this.idDistritoOperacional = idDistritoOperacional;
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
	 * @return the faixaPressaoInferior
	 */
	public String getFaixaPressaoInferior(){

		return faixaPressaoInferior;
	}

	/**
	 * @param faixaPressaoInferior
	 *            the faixaPressaoInferior to set
	 */
	public void setFaixaPressaoInferior(String faixaPressaoInferior){

		this.faixaPressaoInferior = faixaPressaoInferior;
	}

	/**
	 * @return the faixaPressaoSuperior
	 */
	public String getFaixaPressaoSuperior(){

		return faixaPressaoSuperior;
	}

	/**
	 * @param faixaPressaoSuperior
	 *            the faixaPressaoSuperior to set
	 */
	public void setFaixaPressaoSuperior(String faixaPressaoSuperior){

		this.faixaPressaoSuperior = faixaPressaoSuperior;
	}

	public ZonaAbastecimento setFormValues(ZonaAbastecimento zonaAbastecimento){

		// Metodo usado para setar todos os valores do Form na base de dados

		zonaAbastecimento.setCodigo(Integer.parseInt(getCodigo()));
		zonaAbastecimento.setDescricao(getDescricao());
		zonaAbastecimento.setDescricaoAbreviada(getDescricaoAbreviada());

		if(!Util.isVazioOuBranco(getSistemaAbastecimento())
						&& !getSistemaAbastecimento().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			SistemaAbastecimento sistemaAbastecimento = new SistemaAbastecimento();
			sistemaAbastecimento.setId(new Integer(getSistemaAbastecimento()));
			zonaAbastecimento.setSistemaAbastecimento(sistemaAbastecimento);
		}

		if(!Util.isVazioOuBranco(getIdDistritoOperacional())
						&& !getIdDistritoOperacional().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			DistritoOperacional distritoOperacional = new DistritoOperacional();
			distritoOperacional.setId((new Integer(getIdDistritoOperacional())));
			zonaAbastecimento.setDistritoOperacional(distritoOperacional);
		}

		if(!Util.isVazioOuBranco(getIdLocalidade()) && !getIdLocalidade().equalsIgnoreCase(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			Localidade localidade = new Localidade();
			localidade.setId((new Integer(getIdLocalidade())));
			zonaAbastecimento.setLocalidade(localidade);
		}

		// Faixa Pressao Inferior
		BigDecimal faixaPressaoInferior = null;
		String faixaPressaoInferiorStr = this.getFaixaPressaoInferior();

		if(!Util.isVazioOuBranco(faixaPressaoInferiorStr)){
			faixaPressaoInferior = Util.formatarStringParaBigDecimal(faixaPressaoInferiorStr, 4, false);
		}

		zonaAbastecimento.setFaixaPressaoInferior(faixaPressaoInferior);

		// Faixa Pressao Superior
		BigDecimal faixaPressaoSuperior = null;
		String faixaPressaoSuperiorStr = this.getFaixaPressaoSuperior();

		if(!Util.isVazioOuBranco(faixaPressaoSuperiorStr)){
			faixaPressaoSuperior = Util.formatarStringParaBigDecimal(faixaPressaoSuperiorStr, 4, false);
		}

		zonaAbastecimento.setFaixaPressaoSuperior(faixaPressaoSuperior);

		zonaAbastecimento.setIndicadorUso(new Short(getIndicadorUso()));
		zonaAbastecimento.setUltimaAlteracao(Util.converteStringParaDateHora(getUltimaAlteracao()));

		return zonaAbastecimento;
	}

}
