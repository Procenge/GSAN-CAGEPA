
package gcom.atendimentopublico.ordemservico;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.hidrometro.HidrometroProtecao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

@ControleAlteracao()
public class OsSeletivaComandoHidrometroProtecao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	private OsSeletivaComando osSeletivaComando;

	private HidrometroProtecao hidrometroProtecao;

	private Date ultimaAlteracao;

	public OsSeletivaComandoHidrometroProtecao(OsSeletivaComando osSeletivaComando, HidrometroProtecao hidrometroProtecao) {

		super();
		this.osSeletivaComando = osSeletivaComando;
		this.hidrometroProtecao = hidrometroProtecao;
	}

	/** default constructor */
	public OsSeletivaComandoHidrometroProtecao() {

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

	public HidrometroProtecao getHidrometroProtecao(){

		return hidrometroProtecao;
	}

	public void setHidrometroProtecao(HidrometroProtecao hidrometroProtecao){

		this.hidrometroProtecao = hidrometroProtecao;
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
		if(!(obj instanceof OsSeletivaComandoHidrometroProtecao)) return false;
		final OsSeletivaComandoHidrometroProtecao other = (OsSeletivaComandoHidrometroProtecao) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroOsSeletivaComandoHidrometroProtecao filtroOsSeletivaComandoHidrometroProtecao = new FiltroOsSeletivaComandoHidrometroProtecao();

		filtroOsSeletivaComandoHidrometroProtecao.adicionarParametro(new ParametroSimples(FiltroOsSeletivaComandoHidrometroProtecao.ID,
						this.getId()));
		return filtroOsSeletivaComandoHidrometroProtecao;
	}


}
