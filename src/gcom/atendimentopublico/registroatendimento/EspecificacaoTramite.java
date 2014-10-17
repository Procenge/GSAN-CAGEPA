
package gcom.atendimentopublico.registroatendimento;

import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.interceptor.ObjetoGcom;
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.SetorAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubBacia;
import gcom.operacional.SubsistemaEsgoto;
import gcom.operacional.ZonaAbastecimento;

import java.util.Date;

/**
 * Trâmite Especificação
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class EspecificacaoTramite
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao;

	private Localidade localidade;

	private SetorComercial setorComercial;

	private Bairro bairro;

	private SistemaAbastecimento sistemaAbastecimento;

	private DistritoOperacional distritoOperacional;

	private ZonaAbastecimento zonaAbastecimento;

	private SetorAbastecimento setorAbastecimento;

	private SistemaEsgoto sistemaEsgoto;

	private SubsistemaEsgoto subsistemaEsgoto;

	private Bacia bacia;

	private SubBacia subBacia;

	private UnidadeOrganizacional unidadeOrganizacionalOrigem;

	private UnidadeOrganizacional unidadeOrganizacionalDestino;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the solicitacaoTipoEspecificacao
	 */
	public SolicitacaoTipoEspecificacao getSolicitacaoTipoEspecificacao(){

		return solicitacaoTipoEspecificacao;
	}

	/**
	 * @param solicitacaoTipoEspecificacao
	 *            the solicitacaoTipoEspecificacao to set
	 */
	public void setSolicitacaoTipoEspecificacao(SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

	/**
	 * @return the localidade
	 */
	public Localidade getLocalidade(){

		return localidade;
	}

	/**
	 * @param localidade
	 *            the localidade to set
	 */
	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	/**
	 * @return the setorComercial
	 */
	public SetorComercial getSetorComercial(){

		return setorComercial;
	}

	/**
	 * @param setorComercial
	 *            the setorComercial to set
	 */
	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	/**
	 * @return the bairro
	 */
	public Bairro getBairro(){

		return bairro;
	}

	/**
	 * @param bairro
	 *            the bairro to set
	 */
	public void setBairro(Bairro bairro){

		this.bairro = bairro;
	}

	/**
	 * @return the sistemaAbastecimento
	 */
	public SistemaAbastecimento getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	/**
	 * @param sistemaAbastecimento
	 *            the sistemaAbastecimento to set
	 */
	public void setSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	/**
	 * @return the distritoOperacional
	 */
	public DistritoOperacional getDistritoOperacional(){

		return distritoOperacional;
	}

	/**
	 * @param distritoOperacional
	 *            the distritoOperacional to set
	 */
	public void setDistritoOperacional(DistritoOperacional distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	/**
	 * @return the zonaAbastecimento
	 */
	public ZonaAbastecimento getZonaAbastecimento(){

		return zonaAbastecimento;
	}

	/**
	 * @param zonaAbastecimento
	 *            the zonaAbastecimento to set
	 */
	public void setZonaAbastecimento(ZonaAbastecimento zonaAbastecimento){

		this.zonaAbastecimento = zonaAbastecimento;
	}

	/**
	 * @return the setorAbastecimento
	 */
	public SetorAbastecimento getSetorAbastecimento(){

		return setorAbastecimento;
	}

	/**
	 * @param setorAbastecimento
	 *            the setorAbastecimento to set
	 */
	public void setSetorAbastecimento(SetorAbastecimento setorAbastecimento){

		this.setorAbastecimento = setorAbastecimento;
	}

	/**
	 * @return the sistemaEsgoto
	 */
	public SistemaEsgoto getSistemaEsgoto(){

		return sistemaEsgoto;
	}

	/**
	 * @param sistemaEsgoto
	 *            the sistemaEsgoto to set
	 */
	public void setSistemaEsgoto(SistemaEsgoto sistemaEsgoto){

		this.sistemaEsgoto = sistemaEsgoto;
	}

	/**
	 * @return the subsistemaEsgoto
	 */
	public SubsistemaEsgoto getSubsistemaEsgoto(){

		return subsistemaEsgoto;
	}

	/**
	 * @param subsistemaEsgoto
	 *            the subsistemaEsgoto to set
	 */
	public void setSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto){

		this.subsistemaEsgoto = subsistemaEsgoto;
	}

	/**
	 * @return the bacia
	 */
	public Bacia getBacia(){

		return bacia;
	}

	/**
	 * @param bacia
	 *            the bacia to set
	 */
	public void setBacia(Bacia bacia){

		this.bacia = bacia;
	}

	/**
	 * @return the subBacia
	 */
	public SubBacia getSubBacia(){

		return subBacia;
	}

	/**
	 * @param subBacia
	 *            the subBacia to set
	 */
	public void setSubBacia(SubBacia subBacia){

		this.subBacia = subBacia;
	}

	/**
	 * @return the unidadeOrganizacionalOrigem
	 */
	public UnidadeOrganizacional getUnidadeOrganizacionalOrigem(){

		return unidadeOrganizacionalOrigem;
	}

	/**
	 * @param unidadeOrganizacionalOrigem
	 *            the unidadeOrganizacionalOrigem to set
	 */
	public void setUnidadeOrganizacionalOrigem(UnidadeOrganizacional unidadeOrganizacionalOrigem){

		this.unidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem;
	}

	/**
	 * @return the unidadeOrganizacionalDestino
	 */
	public UnidadeOrganizacional getUnidadeOrganizacionalDestino(){

		return unidadeOrganizacionalDestino;
	}

	/**
	 * @param unidadeOrganizacionalDestino
	 *            the unidadeOrganizacionalDestino to set
	 */
	public void setUnidadeOrganizacionalDestino(UnidadeOrganizacional unidadeOrganizacionalDestino){

		this.unidadeOrganizacionalDestino = unidadeOrganizacionalDestino;
	}

	/**
	 * @return the indicadorUso
	 */
	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof EspecificacaoTramite)){
			return false;
		}
		EspecificacaoTramite castOther = (EspecificacaoTramite) other;

		if(this.getSolicitacaoTipoEspecificacao() == null){
			if(castOther.getSolicitacaoTipoEspecificacao() != null) return false;
		}else if(castOther.getSolicitacaoTipoEspecificacao() == null) return false;
		else if(!this.getSolicitacaoTipoEspecificacao().getId().equals(castOther.getSolicitacaoTipoEspecificacao().getId())) return false;
		if(this.getLocalidade() == null){
			if(castOther.getLocalidade() != null) return false;
		}else if(castOther.getLocalidade() == null) return false;
		else if(!this.getLocalidade().getId().equals(castOther.getLocalidade().getId())) return false;
		if(this.getSetorComercial() == null){
			if(castOther.getSetorComercial() != null) return false;
		}else if(castOther.getSetorComercial() == null) return false;
		else if(!this.getSetorComercial().getId().equals(castOther.getSetorComercial().getId())) return false;
		if(this.getBairro() == null){
			if(castOther.getBairro() != null) return false;
		}else if(castOther.getBairro() == null) return false;
		else if(!this.getBairro().getId().equals(castOther.getBairro().getId())) return false;
		if(this.getSistemaAbastecimento() == null){
			if(castOther.getSistemaAbastecimento() != null) return false;
		}else if(castOther.getSistemaAbastecimento() == null) return false;
		else if(!this.getSistemaAbastecimento().getId().equals(castOther.getSistemaAbastecimento().getId())) return false;
		if(this.getDistritoOperacional() == null){
			if(castOther.getDistritoOperacional() != null) return false;
		}else if(castOther.getDistritoOperacional() == null) return false;
		else if(!this.getDistritoOperacional().getId().equals(castOther.getDistritoOperacional().getId())) return false;
		if(this.getZonaAbastecimento() == null){
			if(castOther.getZonaAbastecimento() != null) return false;
		}else if(castOther.getZonaAbastecimento() == null) return false;
		else if(!this.getZonaAbastecimento().getId().equals(castOther.getZonaAbastecimento().getId())) return false;
		if(this.getSetorAbastecimento() == null){
			if(castOther.getSetorAbastecimento() != null) return false;
		}else if(castOther.getSetorAbastecimento() == null) return false;
		else if(!this.getSetorAbastecimento().getId().equals(castOther.getSetorAbastecimento().getId())) return false;
		if(this.getSistemaEsgoto() == null){
			if(castOther.getSistemaEsgoto() != null) return false;
		}else if(castOther.getSistemaEsgoto() == null) return false;
		else if(!this.getSistemaEsgoto().getId().equals(castOther.getSistemaEsgoto().getId())) return false;
		if(this.getSubsistemaEsgoto() == null){
			if(castOther.getSubsistemaEsgoto() != null) return false;
		}else if(castOther.getSubsistemaEsgoto() == null) return false;
		else if(!this.getSubsistemaEsgoto().getId().equals(castOther.getSubsistemaEsgoto().getId())) return false;
		if(this.getBacia() == null){
			if(castOther.getBacia() != null) return false;
		}else if(castOther.getBacia() == null) return false;
		else if(!this.getBacia().getId().equals(castOther.getBacia().getId())) return false;
		if(this.getSubBacia() == null){
			if(castOther.getSubBacia() != null) return false;
		}else if(castOther.getSubBacia() == null) return false;
		else if(!this.getSubBacia().getId().equals(castOther.getSubBacia().getId())) return false;
		if(this.getUnidadeOrganizacionalOrigem() == null){
			if(castOther.getUnidadeOrganizacionalOrigem() != null) return false;
		}else if(castOther.getUnidadeOrganizacionalOrigem() == null) return false;
		else if(!this.getUnidadeOrganizacionalOrigem().getId().equals(castOther.getUnidadeOrganizacionalOrigem().getId())) return false;
		if(this.getUnidadeOrganizacionalDestino() == null){
			if(castOther.getUnidadeOrganizacionalDestino() != null) return false;
		}else if(castOther.getUnidadeOrganizacionalDestino() == null) return false;
		else if(!this.getUnidadeOrganizacionalDestino().getId().equals(castOther.getUnidadeOrganizacionalDestino().getId())) return false;
		return true;
	}
}
