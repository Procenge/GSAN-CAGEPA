
package gcom.gui.operacional;

import gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto;
import gcom.operacional.Bacia;
import gcom.operacional.SubBacia;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Atualizar SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class AtualizarSubBaciaActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String bacia;

	private String subSistema;

	private String sistema;

	private String extensao;

	private String diametro;

	private String idMaterial;

	private String indicadorUso;

	private String ultimaAlteracao;

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

	public String getBacia(){

		return bacia;
	}

	public void setBacia(String bacia){

		this.bacia = bacia;
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

	// Esse método carrega todos os valores da base de dados necesários para exibição da tela
	// AtualizarSubBacia.
	public SubBacia setFormValues(SubBacia subBacia){

		// Metodo usado para setar todos os valores do Form na base de dados

		subBacia.setCodigo(Integer.parseInt(getCodigo()));
		subBacia.setDescricao(getDescricao());
		subBacia.setDescricaoAbreviada(getDescricaoAbreviada());

		if(getBacia() != null && !getBacia().equals("")){
			Bacia bacia = new Bacia();
			bacia.setId(new Integer(getBacia()));
			subBacia.setBacia(bacia);
		}

		// Extensão
		BigDecimal extensao = null;
		String extensaoStr = this.getExtensao();

		if(!Util.isVazioOuBranco(extensaoStr)){
			extensao = Util.formatarStringParaBigDecimal(extensaoStr, 4, false);
		}

		subBacia.setExtensao(extensao);

		// Diâmetro
		BigDecimal diametro = null;
		String diametroStr = this.getDiametro();

		if(!Util.isVazioOuBranco(diametroStr)){
			diametro = Util.formatarStringParaBigDecimal(diametroStr, 4, false);
		}

		subBacia.setDiametro(diametro);

		// Material
		MaterialRedeEsgoto material = null;
		String idMaterialStr = this.getIdMaterial();

		if(idMaterialStr != null && !idMaterialStr.equals("") && !idMaterialStr.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			Integer idMaterial = Integer.valueOf(idMaterialStr);

			material = new MaterialRedeEsgoto();
			material.setId(idMaterial);
		}

		subBacia.setMaterialRedeEsgoto(material);

		subBacia.setIndicadorUso(new Short(getIndicadorUso()));
		subBacia.setUltimaAlteracao(Util.converteStringParaDateHora(getUltimaAlteracao()));

		return subBacia;
	}

	public String getSubSistema(){

		return subSistema;
	}

	public void setSubSistema(String subSistema){

		this.subSistema = subSistema;
	}

	public String getSistema(){

		return sistema;
	}

	public void setSistema(String sistema){

		this.sistema = sistema;
	}

	public String getExtensao(){

		return extensao;
	}

	public void setExtensao(String extensao){

		this.extensao = extensao;
	}

	public String getDiametro(){

		return diametro;
	}

	public void setDiametro(String diametro){

		this.diametro = diametro;
	}

	public String getIdMaterial(){

		return idMaterial;
	}

	public void setIdMaterial(String idMaterial){

		this.idMaterial = idMaterial;
	}

}
