/**
 * 
 */

package gcom.cadastro.aguaparatodos;

import gcom.cadastro.imovel.Imovel;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Bruno Ferreira dos Santos
 */
@ControleAlteracao()
public class ImovelAguaParaTodos
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR = 2535;

	private Integer id;

	private Long idContribuinte;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private String nomeParticipante;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Usuario idUsuarioInclusao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Usuario idUsuarioExclusao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Usuario idUsuarioRenovacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Date dataCadastramento;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Date dataHabilitacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Date dataExclusao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Date dataRenovacao;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Integer anoMesReferenciaInicial;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Integer anoMesReferenciaFinal;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Integer codigoSituacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Imovel idImovel;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_IMOVEL_AGUA_PARA_TODOS_INSERIR})
	private Integer motivoExclusao;

	public static final Integer DESCADASTRADO = null;

	public static final Integer CADASTRADO = 9;

	public static final Integer HABILITADO = 0;

	public static final Integer EXCLUIDO = 1;

	public ImovelAguaParaTodos() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public String getNomeParticipante(){

		return nomeParticipante;
	}

	public void setNomeParticipante(String nomeParticipante){

		this.nomeParticipante = nomeParticipante;
	}

	public Imovel getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Imovel idImovel){

		this.idImovel = idImovel;
	}

	public Usuario getIdUsuarioInclusao(){

		return idUsuarioInclusao;
	}

	public void setIdUsuarioInclusao(Usuario idUsuarioInclusao){

		this.idUsuarioInclusao = idUsuarioInclusao;
	}

	public Usuario getIdUsuarioExclusao(){

		return idUsuarioExclusao;
	}

	public void setIdUsuarioExclusao(Usuario idUsuarioExclusao){

		this.idUsuarioExclusao = idUsuarioExclusao;
	}

	public Date getDataCadastramento(){

		return dataCadastramento;
	}

	public void setDataCadastramento(Date dataCadastramento){

		this.dataCadastramento = dataCadastramento;
	}

	public Date getDataHabilitacao(){

		return dataHabilitacao;
	}

	public void setDataHabilitacao(Date dataHabilitacao){

		this.dataHabilitacao = dataHabilitacao;
	}

	public Date getDataExclusao(){

		return dataExclusao;
	}

	public void setDataExclusao(Date dataExclusao){

		this.dataExclusao = dataExclusao;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getAnoMesReferenciaInicial(){

		return anoMesReferenciaInicial;
	}

	public String getAnoMesReferenciaInicialFormatado(){

		return formataAnoMes(anoMesReferenciaInicial);
	}

	public void setAnoMesReferenciaInicial(Integer anoMesReferenciaInicial){

		this.anoMesReferenciaInicial = anoMesReferenciaInicial;
	}

	public Integer getAnoMesReferenciaFinal(){

		return anoMesReferenciaFinal;
	}

	public String getAnoMesReferenciaFinalFormatado(){

		return formataAnoMes(anoMesReferenciaFinal);
	}

	private String formataAnoMes(Integer anoMes){

		String resultado = anoMes.toString();
		resultado = resultado.substring(4) + "/" + resultado.substring(0, 4);
		return resultado;
	}

	public void setAnoMesReferenciaFinal(Integer anoMesReferenciaFinal){

		this.anoMesReferenciaFinal = anoMesReferenciaFinal;
	}

	public Integer getCodigoSituacao(){

		return codigoSituacao;
	}

	public void setCodigoSituacao(Integer codigoSituacao){

		this.codigoSituacao = codigoSituacao;
	}

	public Integer getMotivoExclusao(){

		return motivoExclusao;
	}

	public void setMotivoExclusao(Integer motivoExclusao){

		this.motivoExclusao = motivoExclusao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Filtro retornaFiltro(){

		FiltroImovelAguaParaTodos filtroImovelAguaParaTodos = new FiltroImovelAguaParaTodos();

		filtroImovelAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroImovelAguaParaTodos.ID, this.getId()));

		return filtroImovelAguaParaTodos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Contribuinte"};
		return labels;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"idContribuinte"};
		return atributos;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Long getIdContribuinte(){

		return idContribuinte;
	}

	public void setIdContribuinte(Long idContribuinte){

		this.idContribuinte = idContribuinte;
	}

	public void setDataRenovacao(Date dataRenovacao){

		this.dataRenovacao = dataRenovacao;
	}

	public Date getDataRenovacao(){

		return dataRenovacao;
	}

	public void setIdUsuarioRenovacao(Usuario idUsuarioRenovacao){

		this.idUsuarioRenovacao = idUsuarioRenovacao;
	}

	public Usuario getIdUsuarioRenovacao(){

		return idUsuarioRenovacao;
	}

}
