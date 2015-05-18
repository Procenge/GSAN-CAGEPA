
package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.interceptor.ObjetoGcom;
import gcom.operacional.*;

import java.util.Date;

/**
 * Serviço Tipo Tramite
 * 
 * @author Hebert Falcão
 * @date 25/03/2011
 */
public class ServicoTipoTramite
				extends ObjetoGcom {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	private ServicoTipo servicoTipo;

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

	private Short indicadorPrimeiroTramite;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public ServicoTipo getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
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

	public Bairro getBairro(){

		return bairro;
	}

	public void setBairro(Bairro bairro){

		this.bairro = bairro;
	}

	public SistemaAbastecimento getSistemaAbastecimento(){

		return sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(SistemaAbastecimento sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public DistritoOperacional getDistritoOperacional(){

		return distritoOperacional;
	}

	public void setDistritoOperacional(DistritoOperacional distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public ZonaAbastecimento getZonaAbastecimento(){

		return zonaAbastecimento;
	}

	public void setZonaAbastecimento(ZonaAbastecimento zonaAbastecimento){

		this.zonaAbastecimento = zonaAbastecimento;
	}

	public SetorAbastecimento getSetorAbastecimento(){

		return setorAbastecimento;
	}

	public void setSetorAbastecimento(SetorAbastecimento setorAbastecimento){

		this.setorAbastecimento = setorAbastecimento;
	}

	public SistemaEsgoto getSistemaEsgoto(){

		return sistemaEsgoto;
	}

	public void setSistemaEsgoto(SistemaEsgoto sistemaEsgoto){

		this.sistemaEsgoto = sistemaEsgoto;
	}

	public SubsistemaEsgoto getSubsistemaEsgoto(){

		return subsistemaEsgoto;
	}

	public void setSubsistemaEsgoto(SubsistemaEsgoto subsistemaEsgoto){

		this.subsistemaEsgoto = subsistemaEsgoto;
	}

	public Bacia getBacia(){

		return bacia;
	}

	public void setBacia(Bacia bacia){

		this.bacia = bacia;
	}

	public SubBacia getSubBacia(){

		return subBacia;
	}

	public void setSubBacia(SubBacia subBacia){

		this.subBacia = subBacia;
	}

	public UnidadeOrganizacional getUnidadeOrganizacionalOrigem(){

		return unidadeOrganizacionalOrigem;
	}

	public void setUnidadeOrganizacionalOrigem(UnidadeOrganizacional unidadeOrganizacionalOrigem){

		this.unidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem;
	}

	public UnidadeOrganizacional getUnidadeOrganizacionalDestino(){

		return unidadeOrganizacionalDestino;
	}

	public void setUnidadeOrganizacionalDestino(UnidadeOrganizacional unidadeOrganizacionalDestino){

		this.unidadeOrganizacionalDestino = unidadeOrganizacionalDestino;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public Short getIndicadorPrimeiroTramite(){

		return indicadorPrimeiroTramite;
	}

	public void setIndicadorPrimeiroTramite(Short indicadorPrimeiroTramite){

		this.indicadorPrimeiroTramite = indicadorPrimeiroTramite;
	}

}
