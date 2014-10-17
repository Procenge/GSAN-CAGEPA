package gcom.atendimentopublico.ordemservico;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

@ControleAlteracao()
public class OsSeletivaComandoLeituraAnormalidade
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private LeituraAnormalidade leituraAnormalidade;

	private Date ultimaAlteracao;


	public OsSeletivaComandoLeituraAnormalidade(OsSeletivaComando osSeletivaComando, LeituraAnormalidade leituraAnormalidade) {

		super();
		this.osSeletivaComando = osSeletivaComando;
		this.leituraAnormalidade = leituraAnormalidade;
	}

	/** default constructor */
	public OsSeletivaComandoLeituraAnormalidade() {

	}

	public Integer getId(){

		return this.id;
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

	public LeituraAnormalidade getLeituraAnormalidade(){

		return leituraAnormalidade;
	}

	public void setLeituraAnormalidade(LeituraAnormalidade leituraAnormalidade){

		this.leituraAnormalidade = leituraAnormalidade;
	}

	@Override
	public Date getUltimaAlteracao(){

		// TODO Auto-generated method stub
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
		if(!(obj instanceof OsSeletivaComandoLeituraAnormalidade)) return false;
		final OsSeletivaComandoLeituraAnormalidade other = (OsSeletivaComandoLeituraAnormalidade) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroOsSeletivaComandoLeituraAnormalidade filtroOsSeletivaComandoLeituraAnormalidade = new FiltroOsSeletivaComandoLeituraAnormalidade();

		filtroOsSeletivaComandoLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroOsSeletivaComandoLeituraAnormalidade.ID,
						this.getId()));
		return filtroOsSeletivaComandoLeituraAnormalidade;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}


}
