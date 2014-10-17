package gcom.atendimentopublico.ordemservico;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.hidrometro.HidrometroLocalInstalacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

@ControleAlteracao()
public class OsSeletivaComandoHidrometroLocalInstacao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private HidrometroLocalInstalacao hidrometroLocalInstalacao;

	private Date ultimaAlteracao;

	public OsSeletivaComandoHidrometroLocalInstacao(OsSeletivaComando osSeletivaComando, HidrometroLocalInstalacao hidrometroLocalInstalacao) {

		super();
		this.osSeletivaComando = osSeletivaComando;
		this.hidrometroLocalInstalacao = hidrometroLocalInstalacao;
	}

	/** default constructor */
	public OsSeletivaComandoHidrometroLocalInstacao() {

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

	public HidrometroLocalInstalacao getHidrometroLocalInstalacao(){

		return hidrometroLocalInstalacao;
	}

	public void setHidrometroLocalInstalacao(HidrometroLocalInstalacao hidrometroLocalInstalacao){

		this.hidrometroLocalInstalacao = hidrometroLocalInstalacao;
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
		if(!(obj instanceof OsSeletivaComandoHidrometroLocalInstacao)) return false;
		final OsSeletivaComandoHidrometroLocalInstacao other = (OsSeletivaComandoHidrometroLocalInstacao) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroOsSeletivaComandoHidrometroLocalInstacao filtroOsSeletivaComandoHidrometroLocalInstacao = new FiltroOsSeletivaComandoHidrometroLocalInstacao();

		filtroOsSeletivaComandoHidrometroLocalInstacao.adicionarParametro(new ParametroSimples(
						FiltroOsSeletivaComandoHidrometroLocalInstacao.ID, this.getId()));
		return filtroOsSeletivaComandoHidrometroLocalInstacao;
	}


	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

}
