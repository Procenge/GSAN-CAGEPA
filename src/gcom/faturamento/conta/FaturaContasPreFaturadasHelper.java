
package gcom.faturamento.conta;

import java.util.Date;

/**
 * [UC3037] Filtrar Contas Pré-Faturadas
 * 
 * @author Carlos Chrystian
 * @created 06/02/2012
 *          Exibir Contas Pré-Faturadas.
 */

public class FaturaContasPreFaturadasHelper {

	private Integer localidadeOrigemID;

	private String nomeLocalidadeOrigem;

	private Integer setorComercialOrigemCD;

	private Integer setorComercialOrigemID;

	private String nomeSetorComercialOrigem;

	private Integer localidadeDestinoID;

	private String nomeLocalidadeDestino;

	private Integer setorComercialDestinoCD;

	private Integer setorComercialDestinoID;

	private String nomeSetorComercialDestino;

	private Integer rotaOrigemID;

	private Integer rotaDestinoID;

	private Integer imovelID;

	private String matriculaImovel;

	private Integer faturamentoGrupoID;

	private Integer dataReferenciaContaInicial;

	private Integer dataReferenciaContaFinal;

	private Date dataVencimentoContaInicial;

	private Date dataVencimentoContaFinal;

	public Integer getLocalidadeOrigemID(){

		return localidadeOrigemID;
	}

	public void setLocalidadeOrigemID(Integer localidadeOrigemID){

		this.localidadeOrigemID = localidadeOrigemID;
	}

	public String getNomeLocalidadeOrigem(){

		return nomeLocalidadeOrigem;
	}

	public void setNomeLocalidadeOrigem(String nomeLocalidadeOrigem){

		this.nomeLocalidadeOrigem = nomeLocalidadeOrigem;
	}

	public Integer getSetorComercialOrigemCD(){

		return setorComercialOrigemCD;
	}

	public void setSetorComercialOrigemCD(Integer setorComercialOrigemCD){

		this.setorComercialOrigemCD = setorComercialOrigemCD;
	}

	public Integer getSetorComercialOrigemID(){

		return setorComercialOrigemID;
	}

	public void setSetorComercialOrigemID(Integer setorComercialOrigemID){

		this.setorComercialOrigemID = setorComercialOrigemID;
	}

	public String getNomeSetorComercialOrigem(){

		return nomeSetorComercialOrigem;
	}

	public void setNomeSetorComercialOrigem(String nomeSetorComercialOrigem){

		this.nomeSetorComercialOrigem = nomeSetorComercialOrigem;
	}

	public Integer getLocalidadeDestinoID(){

		return localidadeDestinoID;
	}

	public void setLocalidadeDestinoID(Integer localidadeDestinoID){

		this.localidadeDestinoID = localidadeDestinoID;
	}

	public String getNomeLocalidadeDestino(){

		return nomeLocalidadeDestino;
	}

	public void setNomeLocalidadeDestino(String nomeLocalidadeDestino){

		this.nomeLocalidadeDestino = nomeLocalidadeDestino;
	}

	public Integer getSetorComercialDestinoCD(){

		return setorComercialDestinoCD;
	}

	public void setSetorComercialDestinoCD(Integer setorComercialDestinoCD){

		this.setorComercialDestinoCD = setorComercialDestinoCD;
	}

	public Integer getSetorComercialDestinoID(){

		return setorComercialDestinoID;
	}

	public void setSetorComercialDestinoID(Integer setorComercialDestinoID){

		this.setorComercialDestinoID = setorComercialDestinoID;
	}

	public String getNomeSetorComercialDestino(){

		return nomeSetorComercialDestino;
	}

	public void setNomeSetorComercialDestino(String nomeSetorComercialDestino){

		this.nomeSetorComercialDestino = nomeSetorComercialDestino;
	}

	public Integer getRotaOrigemID(){

		return rotaOrigemID;
	}

	public void setRotaOrigemID(Integer rotaOrigemID){

		this.rotaOrigemID = rotaOrigemID;
	}

	public Integer getRotaDestinoID(){

		return rotaDestinoID;
	}

	public void setRotaDestinoID(Integer rotaDestinoID){

		this.rotaDestinoID = rotaDestinoID;
	}

	public Integer getImovelID(){

		return imovelID;
	}

	public void setImovelID(Integer imovelID){

		this.imovelID = imovelID;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public Integer getFaturamentoGrupoID(){

		return faturamentoGrupoID;
	}

	public void setFaturamentoGrupoID(Integer faturamentoGrupoID){

		this.faturamentoGrupoID = faturamentoGrupoID;
	}

	public Integer getDataReferenciaContaInicial(){

		return dataReferenciaContaInicial;
	}

	public void setDataReferenciaContaInicial(Integer dataReferenciaContaInicial){

		this.dataReferenciaContaInicial = dataReferenciaContaInicial;
	}

	public Integer getDataReferenciaContaFinal(){

		return dataReferenciaContaFinal;
	}

	public void setDataReferenciaContaFinal(Integer dataReferenciaContaFinal){

		this.dataReferenciaContaFinal = dataReferenciaContaFinal;
	}

	public Date getDataVencimentoContaInicial(){

		return dataVencimentoContaInicial;
	}

	public void setDataVencimentoContaInicial(Date dataVencimentoContaInicial){

		this.dataVencimentoContaInicial = dataVencimentoContaInicial;
	}

	public Date getDataVencimentoContaFinal(){

		return dataVencimentoContaFinal;
	}

	public void setDataVencimentoContaFinal(Date dataVencimentoContaFinal){

		this.dataVencimentoContaFinal = dataVencimentoContaFinal;
	}

}
