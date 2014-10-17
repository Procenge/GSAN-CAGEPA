
package gcom.relatorio.cobranca;

import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.util.ConstantesSistema;

import java.io.Serializable;

/**
 * Helper utilizado na construção do filtro para emissão do Relatório de Remuneração da Cobrança
 * Administrativa
 * 
 * @author Luciano Galvão
 * @date 11/09/2012
 */
public class FiltroLocalidadeSetorComercialHelper
				implements Serializable {

	private Localidade localidade;

	private SetorComercial setorComercial;

	/**
	 * Construtor da classe
	 * 
	 * @param localidade
	 */
	public FiltroLocalidadeSetorComercialHelper(Localidade localidade) {

		this.localidade = localidade;
	}

	public Localidade getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public Integer getSetorComercialId(){

		if(setorComercial != null){
			return setorComercial.getId();
		}else{
			return ConstantesSistema.NUMERO_NAO_INFORMADO;
		}
	}

	public void setSetorComercialId(Integer id){

		// nothing to do
	}

	public String getSetorComercialDescricao(){

		if(setorComercial != null){
			return setorComercial.getDescricaoComCodigo();
		}else{
			return "TODOS";
		}
	}

	public void setSetorComercialDescricao(String descricao){

		// nothing to do
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((localidade.getId() == null) ? 0 : localidade.getId().hashCode())
						+ ((setorComercial.getId() == null) ? 0 : setorComercial.getId().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){


		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		FiltroLocalidadeSetorComercialHelper other = (FiltroLocalidadeSetorComercialHelper) obj;

		if(localidade == null){
			if(other.localidade != null) return false;

		}else{
			if(other.localidade == null) return false;

			if(!localidade.getId().equals(other.localidade.getId())){
				return false;
			}
		}

		if(setorComercial == null){
			if(other.setorComercial != null) return false;

		}else{
			if(other.setorComercial == null) return false;

			if(!setorComercial.getId().equals(other.setorComercial.getId())){
				return false;
			}
		}

		return true;
	}

}
