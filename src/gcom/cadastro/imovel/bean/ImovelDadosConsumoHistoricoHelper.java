
package gcom.cadastro.imovel.bean;

/**
 * Retorna os dados de consumo obtido no historico de um imovel
 * 
 * @author Hugo Lima
 * @date 22/03/2012
 */
public class ImovelDadosConsumoHistoricoHelper {

	private Integer idTipoConsumo;

	private String descricaoTipoConsumo;

	private Integer consumoMedioImovel;

	private Integer idAnormalidadeConsumo;

	private String descricaoAnormalidadeConsumo;

	private Integer consumoRateio;

	private Integer consumoMes;

	public ImovelDadosConsumoHistoricoHelper(Integer idTipoConsumo, String descricaoTipoConsumo, Integer consumoMedioImovel,
												Integer idAnormalidadeConsumo, String descricaoAnormalidadeConsumo, Integer consumoRateio,
												Integer consumoMes) {

		this.idTipoConsumo = idTipoConsumo;
		this.descricaoTipoConsumo = descricaoTipoConsumo;
		this.consumoMedioImovel = consumoMedioImovel;
		this.idAnormalidadeConsumo = idAnormalidadeConsumo;
		this.descricaoAnormalidadeConsumo = descricaoAnormalidadeConsumo;
		this.consumoRateio = consumoRateio;
		this.consumoMes = consumoMes;
	}

	public ImovelDadosConsumoHistoricoHelper() {

	}

	public Integer getIdTipoConsumo(){

		return idTipoConsumo;
	}

	public void setIdTipoConsumo(Integer idTipoConsumo){

		this.idTipoConsumo = idTipoConsumo;
	}

	public String getDescricaoTipoConsumo(){

		return descricaoTipoConsumo;
	}

	public void setDescricaoTipoConsumo(String descricaoTipoConsumo){

		this.descricaoTipoConsumo = descricaoTipoConsumo;
	}

	public Integer getConsumoMedioImovel(){

		return consumoMedioImovel;
	}

	public void setConsumoMedioImovel(Integer consumoMedioImovel){

		this.consumoMedioImovel = consumoMedioImovel;
	}

	public Integer getIdAnormalidadeConsumo(){

		return idAnormalidadeConsumo;
	}

	public void setIdAnormalidadeConsumo(Integer idAnormalidadeConsumo){

		this.idAnormalidadeConsumo = idAnormalidadeConsumo;
	}

	public String getDescricaoAnormalidadeConsumo(){

		return descricaoAnormalidadeConsumo;
	}

	public void setDescricaoAnormalidadeConsumo(String descricaoAnormalidadeConsumo){

		this.descricaoAnormalidadeConsumo = descricaoAnormalidadeConsumo;
	}

	public Integer getConsumoRateio(){

		return consumoRateio;
	}

	public void setConsumoRateio(Integer consumoRateio){

		this.consumoRateio = consumoRateio;
	}

	public Integer getConsumoMes(){

		return consumoMes;
	}

	public void setConsumoMes(Integer consumoMes){

		this.consumoMes = consumoMes;
	}

}
