
package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaGrupo;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class FiltrarEmitirOSCobrancaActionForm
				extends ActionForm {

	private String tipoFiltro;

	// Ação do Cronograma
	private String idCobrancaGrupoCronograma;

	private String idComandoCronograma;

	private String nomeComandoCronograma;

	private String idCobrancaAcaoCronograma;

	private String dataInicialCronograma;

	private String dataFinalCronograma;

	// Ação do Eventual
	private String idCobrancaGrupoEventual;

	private String idLocalidadeEventual;

	private String nomeLocalidadeEventual;

	private String idSetorComercialEventual;

	private String nomeSetorComercialEventual;

	private String idComandoEventual;

	private String nomeComandoEventual;

	private String idCobrancaAcaoEventual;

	private String dataInicialEventual;

	private String dataFinalEventual;

	private Collection<CobrancaAcao> cobrancasAcao;

	private Collection<CobrancaGrupo> cobrancaGrupos;

	public FiltrarEmitirOSCobrancaActionForm() {

		cobrancasAcao = new ArrayList<CobrancaAcao>();
		cobrancaGrupos = new ArrayList<CobrancaGrupo>();
	}

	public String getIdCobrancaGrupoCronograma(){

		return idCobrancaGrupoCronograma;
	}

	public void setIdCobrancaGrupoCronograma(String idCobrancaGrupoCronograma){

		this.idCobrancaGrupoCronograma = idCobrancaGrupoCronograma;
	}

	public String getIdComandoCronograma(){

		return idComandoCronograma;
	}

	public void setIdComandoCronograma(String idComandoCronograma){

		this.idComandoCronograma = idComandoCronograma;
	}

	public String getNomeComandoCronograma(){

		return nomeComandoCronograma;
	}

	public void setNomeComandoCronograma(String nomeComandoCronograma){

		this.nomeComandoCronograma = nomeComandoCronograma;
	}

	public String getIdCobrancaAcaoCronograma(){

		return idCobrancaAcaoCronograma;
	}

	public void setIdCobrancaAcaoCronograma(String idCobrancaAcaoCronograma){

		this.idCobrancaAcaoCronograma = idCobrancaAcaoCronograma;
	}

	public String getDataInicialCronograma(){

		return dataInicialCronograma;
	}

	public void setDataInicialCronograma(String dataInicialCronograma){

		this.dataInicialCronograma = dataInicialCronograma;
	}

	public String getDataFinalCronograma(){

		return dataFinalCronograma;
	}

	public void setDataFinalCronograma(String dataFinalCronograma){

		this.dataFinalCronograma = dataFinalCronograma;
	}

	public String getIdCobrancaGrupoEventual(){

		return idCobrancaGrupoEventual;
	}

	public void setIdCobrancaGrupoEventual(String idCobrancaGrupoEventual){

		this.idCobrancaGrupoEventual = idCobrancaGrupoEventual;
	}

	public String getIdLocalidadeEventual(){

		return idLocalidadeEventual;
	}

	public void setIdLocalidadeEventual(String idLocalidadeEventual){

		this.idLocalidadeEventual = idLocalidadeEventual;
	}

	public String getIdSetorComercialEventual(){

		return idSetorComercialEventual;
	}

	public void setIdSetorComercialEventual(String idSetorComercialEventual){

		this.idSetorComercialEventual = idSetorComercialEventual;
	}

	public String getIdComandoEventual(){

		return idComandoEventual;
	}

	public void setIdComandoEventual(String idComandoEventual){

		this.idComandoEventual = idComandoEventual;
	}

	public String getNomeComandoEventual(){

		return nomeComandoEventual;
	}

	public void setNomeComandoEventual(String nomeComandoEventual){

		this.nomeComandoEventual = nomeComandoEventual;
	}

	public String getIdCobrancaAcaoEventual(){

		return idCobrancaAcaoEventual;
	}

	public void setIdCobrancaAcaoEventual(String idCobrancaAcaoEventual){

		this.idCobrancaAcaoEventual = idCobrancaAcaoEventual;
	}

	public String getDataInicialEventual(){

		return dataInicialEventual;
	}

	public void setDataInicialEventual(String dataInicialEventual){

		this.dataInicialEventual = dataInicialEventual;
	}

	public String getDataFinalEventual(){

		return dataFinalEventual;
	}

	public void setDataFinalEventual(String dataFinalEventual){

		this.dataFinalEventual = dataFinalEventual;
	}

	public Collection<CobrancaAcao> getCobrancasAcao(){

		return cobrancasAcao;
	}

	public void setCobrancasAcao(Collection<CobrancaAcao> cobrancasAcao){

		this.cobrancasAcao = cobrancasAcao;
	}

	public Collection<CobrancaGrupo> getCobrancaGrupos(){

		return cobrancaGrupos;
	}

	public void setCobrancaGrupos(Collection<CobrancaGrupo> cobrancaGrupos){

		this.cobrancaGrupos = cobrancaGrupos;
	}

	public String getNomeLocalidadeEventual(){

		return nomeLocalidadeEventual;
	}

	public void setNomeLocalidadeEventual(String nomeLocalidadeEventual){

		this.nomeLocalidadeEventual = nomeLocalidadeEventual;
	}

	public String getNomeSetorComercialEventual(){

		return nomeSetorComercialEventual;
	}

	public void setNomeSetorComercialEventual(String nomeSetorComercialEventual){

		this.nomeSetorComercialEventual = nomeSetorComercialEventual;
	}

	public String getTipoFiltro(){

		return tipoFiltro;
	}

	public void setTipoFiltro(String tipoFiltro){

		this.tipoFiltro = tipoFiltro;
	}

	// public void reset(ActionMapping mapping, HttpServletRequest request) {
	// tipoFiltro = "";
	//		
	// //Ação do Cronograma
	// idCobrancaGrupoCronograma = "";
	// idComandoCronograma = "";
	// nomeComandoCronograma = "";
	// idCobrancaAcaoCronograma = "";
	// dataInicialCronograma = "";
	// dataFinalCronograma = "";
	//
	// //Ação do Eventual
	// idCobrancaGrupoEventual = "";
	// idLocalidadeEventual = "";
	// nomeLocalidadeEventual = "";
	// idSetorComercialEventual = "";
	// nomeSetorComercialEventual = "";
	// idComandoEventual = "";
	// nomeComandoEventual = "";
	// idCobrancaAcaoEventual = "";
	// dataInicialEventual = "";
	// dataFinalEventual = "";
	// }
}
