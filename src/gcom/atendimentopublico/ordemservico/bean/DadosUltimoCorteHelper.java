/**
 * 
 */

package gcom.atendimentopublico.ordemservico.bean;

import gcom.util.Util;

import java.io.Serializable;
import java.util.Date;

/**
 * @author isilva
 */
public class DadosUltimoCorteHelper
				implements Serializable {

	private Date dataCorte;

	private Integer numeroSeloCorte;

	private Integer numeroLeituraCorte;

	private Integer idCorteTipo;

	private String descricaoCorteTipo;

	private Date dataCorteAdministrativo;

	public DadosUltimoCorteHelper(Date dataCorte, Integer numeroSeloCorte, Integer numeroLeituraCorte, Integer idCorteTipo,
									String descricaoCorteTipo, Date dataCorteAdministrativo) {

		super();
		this.dataCorte = dataCorte;
		this.numeroSeloCorte = numeroSeloCorte;
		this.numeroLeituraCorte = numeroLeituraCorte;
		this.idCorteTipo = idCorteTipo;
		this.descricaoCorteTipo = descricaoCorteTipo;
		this.dataCorteAdministrativo = dataCorteAdministrativo;
	}

	public Date getDataCorte(){

		return dataCorte;
	}

	public void setDataCorte(Date dataCorte){

		this.dataCorte = dataCorte;
	}

	public Integer getNumeroSeloCorte(){

		return numeroSeloCorte;
	}

	public void setNumeroSeloCorte(Integer numeroSeloCorte){

		this.numeroSeloCorte = numeroSeloCorte;
	}

	public Integer getNumeroLeituraCorte(){

		return numeroLeituraCorte;
	}

	public void setNumeroLeituraCorte(Integer numeroLeituraCorte){

		this.numeroLeituraCorte = numeroLeituraCorte;
	}

	public Integer getIdCorteTipo(){

		return idCorteTipo;
	}

	public void setIdCorteTipo(Integer idCorteTipo){

		this.idCorteTipo = idCorteTipo;
	}

	public String getDescricaoCorteTipo(){

		return descricaoCorteTipo;
	}

	public void setDescricaoCorteTipo(String descricaoCorteTipo){

		this.descricaoCorteTipo = descricaoCorteTipo;
	}

	public Date getDataCorteAdministrativo(){

		return dataCorteAdministrativo;
	}

	public void setDataCorteAdministrativo(Date dataCorteAdministrativo){

		this.dataCorteAdministrativo = dataCorteAdministrativo;
	}

	public String getIdDescricao(){

		if(!Util.isVazioOuBranco(getIdCorteTipo()) && !Util.isVazioOuBranco(getDescricaoCorteTipo())){
			return getIdCorteTipo().toString() + " - " + getDescricaoCorteTipo();
		}else if(!Util.isVazioOuBranco(getIdCorteTipo())){
			return getIdCorteTipo().toString();
		}else if(!Util.isVazioOuBranco(getDescricaoCorteTipo())){
			return getDescricaoCorteTipo();
		}

		return "";
	}
}
