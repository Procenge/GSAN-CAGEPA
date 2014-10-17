
package gcom.relatorio.atendimentopublico;

import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.micromedicao.Rota;
import gcom.util.Util;

import java.util.Date;

/**
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
 * 
 * @author Anderson Italo
 * @date 08/06/2011
 */
public class RelatorioResumoOrdensServicoPendentesHelper {

	private static final long serialVersionUID = 1L;

	private Integer idOrdemServico;

	private Integer idGerenciaRegional;

	private String nomeGerenciaRegional;

	private Integer idLocalidade;

	private String descricaoLocalidade;

	private int codigoSetorComercial;

	private String descricaoSetorComercial;

	private int numeroQuadra;

	private short lote;

	private short subLote;

	private Integer idRota;

	private Short segmento;

	private Date dataEmissao;

	private Integer idServicoTipo;

	private String descricaoServicoTipo;

	private Integer idImovel;

	private Integer idUnidadeNegocio;

	private String nomeUnidadeNegocio;

	public RelatorioResumoOrdensServicoPendentesHelper(Integer idOrdemServico, Integer idGerenciaRegional, String nomeGerenciaRegional,
														Integer idLocalidade, String descricaoLocalidade, int codigoSetorComercial,
														String descricaoSetorComercial, int numeroQuadra, short lote, short subLote,
														Integer idRota, Short segmento, Date dataEmissao, Integer idServicoTipo,
														String descricaoServicoTipo, Integer idImovel, Integer idUnidadeNegocio,
														String nomeUnidadeNegocio) {

		this.idOrdemServico = idOrdemServico;
		this.idGerenciaRegional = idGerenciaRegional;
		this.nomeGerenciaRegional = nomeGerenciaRegional;
		this.idLocalidade = idLocalidade;
		this.descricaoLocalidade = descricaoLocalidade;
		this.codigoSetorComercial = codigoSetorComercial;
		this.descricaoSetorComercial = descricaoSetorComercial;
		this.numeroQuadra = numeroQuadra;
		this.lote = lote;
		this.subLote = subLote;
		this.idRota = idRota;
		this.segmento = segmento;
		this.dataEmissao = dataEmissao;
		this.idServicoTipo = idServicoTipo;
		this.descricaoServicoTipo = descricaoServicoTipo;
		this.idImovel = idImovel;
		this.idUnidadeNegocio = idUnidadeNegocio;
		this.nomeUnidadeNegocio = nomeUnidadeNegocio;
	}

	public Integer getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public String getNomeGerenciaRegional(){

		return nomeGerenciaRegional;
	}

	public void setNomeGerenciaRegional(String nomeGerenciaRegional){

		this.nomeGerenciaRegional = nomeGerenciaRegional;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public int getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(int codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	public int getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(int numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public short getLote(){

		return lote;
	}

	public void setLote(short lote){

		this.lote = lote;
	}

	public short getSubLote(){

		return subLote;
	}

	public void setSubLote(short subLote){

		this.subLote = subLote;
	}

	public Integer getIdRota(){

		return idRota;
	}

	public void setIdRota(Integer idRota){

		this.idRota = idRota;
	}

	public Short getSegmento(){

		return segmento;
	}

	public void setSegmento(Short segmento){

		this.segmento = segmento;
	}

	public Date getDataEmissao(){

		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public Integer getIdServicoTipo(){

		return idServicoTipo;
	}

	public void setIdServicoTipo(Integer idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	public String getDescricaoServicoTipo(){

		return descricaoServicoTipo;
	}

	public void setDescricaoServicoTipo(String descricaoServicoTipo){

		this.descricaoServicoTipo = descricaoServicoTipo;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoImovel(){

		Imovel imovel = new Imovel();

		imovel.setLocalidade(new Localidade(this.idLocalidade));
		imovel.setSetorComercial(new SetorComercial(0, this.codigoSetorComercial, this.descricaoSetorComercial));
		imovel.setQuadra(new Quadra(0, this.numeroQuadra, new Rota()));
		imovel.setLote(this.lote);
		imovel.setSubLote(this.subLote);

		return imovel.getInscricaoFormatada();
	}

	public String getDataEmissaoOSFormatada(){

		String retorno = "";

		if(this.dataEmissao != null){
			retorno = Util.formatarData(this.dataEmissao);
		}

		return retorno;
	}

	public Integer getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(Integer idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public String getNomeUnidadeNegocio(){

		return nomeUnidadeNegocio;
	}

	public void setNomeUnidadeNegocio(String nomeUnidadeNegocio){

		this.nomeUnidadeNegocio = nomeUnidadeNegocio;
	}

}
