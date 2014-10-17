
package gcom.gui.operacional;

import gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto;
import gcom.operacional.Bacia;
import gcom.operacional.SubBacia;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class InserirSubBaciaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String codigo;

	private String descricao;

	private String descricaoAbreviada;

	private String idSistema;

	private String idSubSistema;

	private String idBacia;

	private String extensao;

	private String diametro;

	private String idMaterial;

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

	public String getIdBacia(){

		return idBacia;
	}

	public void setIdBacia(String idBacia){

		this.idBacia = idBacia;
	}

	public SubBacia setFormValues(SubBacia subBacia){

		if(getCodigo() != null && !getCodigo().equals("")){
			subBacia.setCodigo((Integer.valueOf(getCodigo())));
		}

		subBacia.setDescricao(getDescricao());
		subBacia.setDescricaoAbreviada(getDescricaoAbreviada());

		if(getIdBacia() != null && !getIdBacia().equals("") && !getIdBacia().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			Bacia bacia = new Bacia();
			bacia.setId((Integer.valueOf(getIdBacia())));
			subBacia.setBacia(bacia);
		}

		// Extensão
		String extensaoStr = this.getExtensao();
		if(!Util.isVazioOuBranco(extensaoStr)){
			BigDecimal extensao = Util.formatarStringParaBigDecimal(extensaoStr, 4, false);
			subBacia.setExtensao(extensao);
		}

		// Diâmetro
		String diametroStr = this.getDiametro();
		if(!Util.isVazioOuBranco(diametroStr)){
			BigDecimal diametro = Util.formatarStringParaBigDecimal(diametroStr, 4, false);
			subBacia.setDiametro(diametro);
		}

		if(!Util.isVazioOuBranco(getIdMaterial()) && !getIdMaterial().equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){
			MaterialRedeEsgoto material = new MaterialRedeEsgoto();
			material.setId(Integer.valueOf(getIdMaterial()));
			subBacia.setMaterialRedeEsgoto(material);
		}

		return subBacia;
	}

	public String getIdSistema(){

		return idSistema;
	}

	public void setIdSistema(String idSistema){

		this.idSistema = idSistema;
	}

	public String getIdSubSistema(){

		return idSubSistema;
	}

	public void setIdSubSistema(String idSubSistema){

		this.idSubSistema = idSubSistema;
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
