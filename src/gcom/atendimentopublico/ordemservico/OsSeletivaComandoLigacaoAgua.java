package gcom.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

/** @author vsantos */
public class OsSeletivaComandoLigacaoAgua
				extends ObjetoTransacao {

	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private LigacaoAguaSituacao ligacaoAguaSituacao;

	private Date ultimaAlteracao;

	/** Construtor padrão */
	public OsSeletivaComandoLigacaoAgua() {

	}

	public OsSeletivaComandoLigacaoAgua(Integer id, OsSeletivaComando osSeletivaComando, LigacaoAguaSituacao ligacaoAguaSituacao,
									Date ultimaAlteracao) {

		this.id = id;
		this.osSeletivaComando = osSeletivaComando;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
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

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
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
		if(!(obj instanceof OsSeletivaComandoLigacaoAgua)) return false;
		final OsSeletivaComandoLigacaoAgua other = (OsSeletivaComandoLigacaoAgua) obj;
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
