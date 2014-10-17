
package gcom.cadastro.imovel;

import gcom.faturamento.conta.Conta;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @author Vicente Zarga
 * @date 05/09/2013
 *       Check List documentos Imóvel
 */
@ControleAlteracao()
public class ImovelCheckListDocumentacao
				extends ObjetoTransacao {

	public static final int ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL = 900125;// OPERACAO.OPERACAO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL

	public static final int ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL = 900126;// OPERACAO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL

	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL, ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL})
	private Imovel imovel;

	@ControleAlteracao(funcionalidade = {ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL, ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL})
	private Short indicadorEntregaCopiaContrato;

	@ControleAlteracao(funcionalidade = {ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL, ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL})
	private Short indicadorEntregaCopiaCpf;

	@ControleAlteracao(funcionalidade = {ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL, ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL})
	private Short indicadorEntregaCopiaRg;

	@ControleAlteracao(funcionalidade = {ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL, ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL})
	private Short indicadorEntregaCopiaDocImovel;

	@ControleAlteracao(funcionalidade = {ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL, ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL})
	private Short indicadorEntregaCopiaTermoConfDivida;

	@ControleAlteracao(funcionalidade = {ATRIBUTO_INSERIR_CHECK_LIST_DOCUMENTACAO_IMOVEL, ATRIBUTO_ATUALIZAR_CHECK_LIST_DOCUMENTACAO_IMOVEL})
	private Short indicadorEntregaCopiaCorrespondencia;

	private Date ultimaAlteracao;

	public ImovelCheckListDocumentacao(Integer id, Imovel imovel, Short indicadorEntregaCopiaContrato, Short indicadorEntregaCopiaCpf,
										Short indicadorEntregaCopiaRg, Short indicadorEntregaCopiaDocImovel,
										Short indicadorEntregaCopiaTermoConfDivida, Short indicadorEntregaCopiaCorrespondencia,
										Date ultimaAlteracao) {

		this.id = id;
		this.imovel = imovel;
		this.indicadorEntregaCopiaContrato = indicadorEntregaCopiaContrato;
		this.indicadorEntregaCopiaCpf = indicadorEntregaCopiaCpf;
		this.indicadorEntregaCopiaRg = indicadorEntregaCopiaRg;
		this.indicadorEntregaCopiaDocImovel = indicadorEntregaCopiaDocImovel;
		this.indicadorEntregaCopiaTermoConfDivida = indicadorEntregaCopiaTermoConfDivida;
		this.indicadorEntregaCopiaCorrespondencia = indicadorEntregaCopiaCorrespondencia;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public ImovelCheckListDocumentacao() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroImovelCheckListDocumentacao filtroImovelCheckListDocumentacao = new FiltroImovelCheckListDocumentacao();
		filtroImovelCheckListDocumentacao.adicionarParametro(new ParametroSimples(FiltroImovelCheckListDocumentacao.ID, this.getId()));
		filtroImovelCheckListDocumentacao.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelCheckListDocumentacao.IMOVEL);

		return filtroImovelCheckListDocumentacao;
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public Short getIndicadorEntregaCopiaContrato(){

		return indicadorEntregaCopiaContrato;
	}

	public void setIndicadorEntregaCopiaContrato(Short indicadorEntregaCopiaContrato){

		this.indicadorEntregaCopiaContrato = indicadorEntregaCopiaContrato;
	}

	public Short getIndicadorEntregaCopiaCpf(){

		return indicadorEntregaCopiaCpf;
	}

	public void setIndicadorEntregaCopiaCpf(Short indicadorEntregaCopiaCpf){

		this.indicadorEntregaCopiaCpf = indicadorEntregaCopiaCpf;
	}

	public Short getIndicadorEntregaCopiaRg(){

		return indicadorEntregaCopiaRg;
	}

	public void setIndicadorEntregaCopiaRg(Short indicadorEntregaCopiaRg){

		this.indicadorEntregaCopiaRg = indicadorEntregaCopiaRg;
	}

	public Short getIndicadorEntregaCopiaDocImovel(){

		return indicadorEntregaCopiaDocImovel;
	}

	public void setIndicadorEntregaCopiaDocImovel(Short indicadorEntregaCopiaDocImovel){

		this.indicadorEntregaCopiaDocImovel = indicadorEntregaCopiaDocImovel;
	}

	public Short getIndicadorEntregaCopiaTermoConfDivida(){

		return indicadorEntregaCopiaTermoConfDivida;
	}

	public void setIndicadorEntregaCopiaTermoConfDivida(Short indicadorEntregaCopiaTermoConfDivida){

		this.indicadorEntregaCopiaTermoConfDivida = indicadorEntregaCopiaTermoConfDivida;
	}

	public Short getIndicadorEntregaCopiaCorrespondencia(){

		return indicadorEntregaCopiaCorrespondencia;
	}

	public void setIndicadorEntregaCopiaCorrespondencia(Short indicadorEntregaCopiaCorrespondencia){

		this.indicadorEntregaCopiaCorrespondencia = indicadorEntregaCopiaCorrespondencia;
	}

	@Override
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;

	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param other
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof Conta)){
			return false;
		}
		Conta castOther = (Conta) other;

		return (this.getId().equals(castOther.getId()));
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

}
