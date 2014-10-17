package gcom.atendimentopublico.ordemservico;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

/** @author vsantos */
public class OsSeletivaComandoConsumoMedio
				extends ObjetoTransacao {

	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private Integer numeroConsumoMedioInicial;

	private Integer numeroConsumoMedioFinal;

	private Date ultimaAlteracao;

	/** Construtor padrão */
	public OsSeletivaComandoConsumoMedio() {

	}

	public OsSeletivaComandoConsumoMedio(Integer id, OsSeletivaComando osSeletivaComando, Integer numeroConsumoMedioInicial,
										Integer numeroConsumoMedioFinal,
									Date ultimaAlteracao) {

		this.id = id;
		this.osSeletivaComando = osSeletivaComando;
		this.numeroConsumoMedioInicial = numeroConsumoMedioInicial;
		this.numeroConsumoMedioFinal = numeroConsumoMedioFinal;
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

	public Integer getNumeroConsumoMedioInicial(){

		return numeroConsumoMedioInicial;
	}

	public void setNumeroConsumoMedioInicial(Integer numeroConsumoMedioInicial){

		this.numeroConsumoMedioInicial = numeroConsumoMedioInicial;
	}

	public Integer getNumeroConsumoMedioFinal(){

		return numeroConsumoMedioFinal;
	}

	public void setNumeroConsumoMedioFinal(Integer numeroConsumoMedioFinal){

		this.numeroConsumoMedioFinal = numeroConsumoMedioFinal;
	}

	@Override
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}
	
	public String getDescricaoConsumoFaixa(){

		String descricaoFaixaConsumoMedio;
		descricaoFaixaConsumoMedio = getNumeroConsumoMedioInicial() + " - " + getNumeroConsumoMedioFinal();

		return descricaoFaixaConsumoMedio;
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
		if(!(obj instanceof OsSeletivaComandoConsumoMedio)) return false;
		final OsSeletivaComandoConsumoMedio other = (OsSeletivaComandoConsumoMedio) obj;
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
