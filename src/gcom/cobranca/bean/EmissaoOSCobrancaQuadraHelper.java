
package gcom.cobranca.bean;

public class EmissaoOSCobrancaQuadraHelper {

	private int numeroQuadra;

	private Long quantidadeOS;

	private String locSetorQuadra;

	private String nomeAgente = "";

	@Override
	public boolean equals(Object obj){

		try{
			if(numeroQuadra == ((EmissaoOSCobrancaQuadraHelper) obj).getNumeroQuadra()){
				return true;
			}
		}catch(ClassCastException ex){
			return false;
		}
		return false;
	}

	public int getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(int numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Long getQuantidadeOS(){

		return quantidadeOS;
	}

	public void setQuantidadeOS(Long quantidadeOS){

		this.quantidadeOS = quantidadeOS;
	}

	public String getLocSetorQuadra(){

		return locSetorQuadra;
	}

	public void setLocSetorQuadra(String locSetorQuadra){

		this.locSetorQuadra = locSetorQuadra;
	}

	public String getNomeAgente(){

		return nomeAgente;
	}

	public void setNomeAgente(String nomeAgente){

		this.nomeAgente = nomeAgente;
	}

}
