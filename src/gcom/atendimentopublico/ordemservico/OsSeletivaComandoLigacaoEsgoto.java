package gcom.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

/** @author vsantos */
public class OsSeletivaComandoLigacaoEsgoto
				extends ObjetoTransacao {

	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	private Date ultimaAlteracao;

	/** Construtor padrão */
	public OsSeletivaComandoLigacaoEsgoto() {

	}

	public OsSeletivaComandoLigacaoEsgoto(Integer id, OsSeletivaComando osSeletivaComando, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
									Date ultimaAlteracao) {

		this.id = id;
		this.osSeletivaComando = osSeletivaComando;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
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

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
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
		if(!(obj instanceof OsSeletivaComandoLigacaoEsgoto)) return false;
		final OsSeletivaComandoLigacaoEsgoto other = (OsSeletivaComandoLigacaoEsgoto) obj;
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
