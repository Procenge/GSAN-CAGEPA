
package gcom.cobranca.bean;

import java.util.ArrayList;
import java.util.Collection;

public class EmissaoOSCobrancaHelper {

	private Integer localidade;

	private Integer setor;

	private Collection<EmissaoOSCobrancaQuadraHelper> quadras;

	public Integer getLocalidade(){

		return localidade;
	}

	public void setLocalidade(Integer localidade){

		this.localidade = localidade;
	}

	public Integer getSetor(){

		return setor;
	}

	public void setSetor(Integer setor){

		this.setor = setor;
	}

	public Collection<EmissaoOSCobrancaQuadraHelper> getQuadras(){

		if(quadras == null){
			quadras = new ArrayList<EmissaoOSCobrancaQuadraHelper>();
		}
		return quadras;
	}

	public void setQuadras(Collection<EmissaoOSCobrancaQuadraHelper> quadras){

		this.quadras = quadras;
	}

	@Override
	public boolean equals(Object obj){

		try{
			EmissaoOSCobrancaHelper emosHelper = (EmissaoOSCobrancaHelper) obj;
			if((emosHelper.getLocalidade().equals(this.localidade)) && (emosHelper.getSetor().equals(this.setor))){
				return true;
			}else{
				return false;
			}
		}catch(ClassCastException ex){
			return false;
		}
	}

}
