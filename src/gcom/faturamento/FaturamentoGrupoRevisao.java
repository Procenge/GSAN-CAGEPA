
package gcom.faturamento;

import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

/**
 * @author Hugo Lima
 */
public class FaturamentoGrupoRevisao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer anoMesReferenciaFaturamentoInicial;

	private Integer anoMesReferenciaFaturamentoFinal;

	private Date ultimaAlteracao;

	private FaturamentoGrupo faturamentoGrupo;

	private ContaMotivoRevisao contaMotivoRevisao;

	private Short indicadorImpressao;

	private Short indicadorImpressaoCampo;

	public FaturamentoGrupoRevisao() {

	}

	public FaturamentoGrupoRevisao(Integer id, Integer anoMesReferenciaFaturamentoInicial, Integer anoMesReferenciaFaturamentoFinal,
									Date ultimaAlteracao, FaturamentoGrupo faturamentoGrupo, ContaMotivoRevisao contaMotivoRevisao) {

		this.id = id;
		this.anoMesReferenciaFaturamentoInicial = anoMesReferenciaFaturamentoInicial;
		this.anoMesReferenciaFaturamentoFinal = anoMesReferenciaFaturamentoFinal;
		this.ultimaAlteracao = ultimaAlteracao;
		this.faturamentoGrupo = faturamentoGrupo;
		this.contaMotivoRevisao = contaMotivoRevisao;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getAnoMesReferenciaFaturamentoInicial(){

		return anoMesReferenciaFaturamentoInicial;
	}

	public void setAnoMesReferenciaFaturamentoInicial(Integer anoMesReferenciaFaturamentoInicial){

		this.anoMesReferenciaFaturamentoInicial = anoMesReferenciaFaturamentoInicial;
	}

	public Integer getAnoMesReferenciaFaturamentoFinal(){

		return anoMesReferenciaFaturamentoFinal;
	}

	public void setAnoMesReferenciaFaturamentoFinal(Integer anoMesReferenciaFaturamentoFinal){

		this.anoMesReferenciaFaturamentoFinal = anoMesReferenciaFaturamentoFinal;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public FaturamentoGrupo getFaturamentoGrupo(){

		return faturamentoGrupo;
	}

	public void setFaturamentoGrupo(FaturamentoGrupo faturamentoGrupo){

		this.faturamentoGrupo = faturamentoGrupo;
	}

	public ContaMotivoRevisao getContaMotivoRevisao(){

		return contaMotivoRevisao;
	}

	public void setContaMotivoRevisao(ContaMotivoRevisao contaMotivoRevisao){

		this.contaMotivoRevisao = contaMotivoRevisao;
	}

	public Short getIndicadorImpressao(){

		return indicadorImpressao;
	}

	public void setIndicadorImpressao(Short indicadorImpressao){

		this.indicadorImpressao = indicadorImpressao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroFaturamentoGrupoRevisao filtroFaturamentoGrupoRevisao = new FiltroFaturamentoGrupoRevisao();
		filtroFaturamentoGrupoRevisao.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoGrupoRevisao.FATURAMENTO_GRUPO);
		filtroFaturamentoGrupoRevisao.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoGrupoRevisao.CONTA_MOTIVO_REVISAO);
		filtroFaturamentoGrupoRevisao.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupoRevisao.ID, this.getId()));

		return filtroFaturamentoGrupoRevisao;
	}

	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public Short getIndicadorImpressaoCampo(){

		return indicadorImpressaoCampo;
	}

	public void setIndicadorImpressaoCampo(Short indicadorImpressaoCampo){

		this.indicadorImpressaoCampo = indicadorImpressaoCampo;
	}

}
