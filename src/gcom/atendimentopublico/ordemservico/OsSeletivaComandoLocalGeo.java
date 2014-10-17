package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.Rota;
import gcom.util.filtro.Filtro;

import java.util.Date;

/** @author vsantos */
public class OsSeletivaComandoLocalGeo
				extends ObjetoTransacao {

	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private GerenciaRegional gerenciaRegional;

	private Localidade localidade;

	private SetorComercial setorComercial;

	private Integer codigoSetorComercial;

	private Quadra quadra;

	private Integer numeroQuadra;

	private Integer numeroLote;

	private Rota rota;

	private Integer codigoRota;

	private Bairro bairro;

	private Integer codigoBairro;

	private Logradouro logradouro;

	private Date ultimaAlteracao;

	/** Construtor padrão */
	public OsSeletivaComandoLocalGeo() {

	}

	/** Construtor completo */
	public OsSeletivaComandoLocalGeo(Integer id, OsSeletivaComando osSeletivaComando, GerenciaRegional gerenciaRegional,
									Localidade localidade, SetorComercial setorComercial, Integer codigoSetorComercial, Quadra quadra,
									Integer numeroQuadra, Integer numeroLote, Rota rota, Integer codigoRota, Bairro bairro,
									Integer codigoBairro, Logradouro logradouro, Date ultimaAlteracao) {

		this.id = id;
		this.osSeletivaComando = osSeletivaComando;
		this.gerenciaRegional = gerenciaRegional;
		this.localidade = localidade;
		this.setorComercial = setorComercial;
		this.codigoSetorComercial = codigoSetorComercial;
		this.quadra = quadra;
		this.numeroQuadra = numeroQuadra;
		this.numeroLote = numeroLote;
		this.rota = rota;
		this.codigoRota = codigoRota;
		this.bairro = bairro;
		this.codigoBairro = codigoBairro;
		this.logradouro = logradouro;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	/** Construtor mínimo */
	public OsSeletivaComandoLocalGeo(Integer id, OsSeletivaComando osSeletivaComando, Date ultimaAlteracao) {

		this.id = id;
		this.osSeletivaComando = osSeletivaComando;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public OsSeletivaComando getOsSeletivaComando(){

		return osSeletivaComando;
	}

	public void setOsSeletivaComando(OsSeletivaComando osSeletivaComando){

		this.osSeletivaComando = osSeletivaComando;
	}

	public GerenciaRegional getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
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

	public Integer getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(Integer codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public Quadra getQuadra(){

		return quadra;
	}

	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public Integer getNumeroLote(){

		return numeroLote;
	}

	public void setNumeroLote(Integer numeroLote){

		this.numeroLote = numeroLote;
	}

	public Rota getRota(){

		return rota;
	}

	public void setRota(Rota rota){

		this.rota = rota;
	}

	public Integer getCodigoRota(){

		return codigoRota;
	}

	public void setCodigoRota(Integer codigoRota){

		this.codigoRota = codigoRota;
	}

	public Bairro getBairro(){

		return bairro;
	}

	public void setBairro(Bairro bairro){

		this.bairro = bairro;
	}

	public Integer getCodigoBairro(){

		return codigoBairro;
	}

	public void setCodigoBairro(Integer codigoBairro){

		this.codigoBairro = codigoBairro;
	}

	public Logradouro getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro){

		this.logradouro = logradouro;
	}

	@Override
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof OsSeletivaComandoLocalGeo)) return false;
		final OsSeletivaComandoLocalGeo other = (OsSeletivaComandoLocalGeo) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}
}
