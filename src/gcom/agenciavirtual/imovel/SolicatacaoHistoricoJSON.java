
package gcom.agenciavirtual.imovel;

import gcom.atendimentopublico.registroatendimento.*;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

public class SolicatacaoHistoricoJSON {

	private Integer idRegistroAtendimento;

	private Date dataAtendimento;

	private String solicitacaoTipo;

	private String especiticacaoSolicitacaoTipo;

	private String situacao;

	private Integer indicadorExistePesquisaSatisfacao;

	public SolicatacaoHistoricoJSON(RegistroAtendimento ra) {

		idRegistroAtendimento = ra.getId();
		dataAtendimento = ra.getRegistroAtendimento();
		String descricaoSolicitacaoTipo = null;
		String descricaoSolicitacaoTipoEspecificacao = null;
		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = ra.getSolicitacaoTipoEspecificacao();
		if(solicitacaoTipoEspecificacao != null){
			descricaoSolicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao.getDescricao();
			SolicitacaoTipo solicitacaoTipo = solicitacaoTipoEspecificacao.getSolicitacaoTipo();
			if(solicitacaoTipo != null){
				descricaoSolicitacaoTipo = solicitacaoTipo.getDescricao();
			}
		}

		especiticacaoSolicitacaoTipo = descricaoSolicitacaoTipoEspecificacao;
		solicitacaoTipo = descricaoSolicitacaoTipo;
		situacao = ra.getDescricaoSituacao();
		
		FiltroPesquisaSatisfacao filtroPesquisaSatisfacao = new FiltroPesquisaSatisfacao();
		filtroPesquisaSatisfacao.adicionarParametro(new ParametroSimples(FiltroPesquisaSatisfacao.REGISTRO_ATENDIMENTO_ID, ra.getId()));
		if(Util.isVazioOrNulo(Fachada.getInstancia().pesquisar(filtroPesquisaSatisfacao, PesquisaSatisfacao.class.getName()))){
			indicadorExistePesquisaSatisfacao = ConstantesSistema.NAO.intValue();
		}else{
			indicadorExistePesquisaSatisfacao = ConstantesSistema.SIM.intValue();
		}
		
	}

	public Integer getIdRegistroAtendimento(){

		return idRegistroAtendimento;
	}

	public void setIdRegistroAtendimento(Integer idRegistroAtendimento){

		this.idRegistroAtendimento = idRegistroAtendimento;
	}

	public Date getDataAtendimento(){

		return dataAtendimento;
	}

	public void setDataAtendimento(Date dataAtendimento){

		this.dataAtendimento = dataAtendimento;
	}

	public String getSolicitacaoTipo(){

		return solicitacaoTipo;
	}

	public void setSolicitacaoTipo(String solicitacaoTipo){

		this.solicitacaoTipo = solicitacaoTipo;
	}

	public String getEspeciticacaoSolicitacaoTipo(){

		return especiticacaoSolicitacaoTipo;
	}

	public void setEspeciticacaoSolicitacaoTipo(String especiticacaoSolicitacaoTipo){

		this.especiticacaoSolicitacaoTipo = especiticacaoSolicitacaoTipo;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public Integer getIndicadorExistePesquisaSatisfacao(){

		return indicadorExistePesquisaSatisfacao;
	}

	public void setIndicadorExistePesquisaSatisfacao(Integer indicadorExistePesquisaSatisfacao){

		this.indicadorExistePesquisaSatisfacao = indicadorExistePesquisaSatisfacao;
	}

}
