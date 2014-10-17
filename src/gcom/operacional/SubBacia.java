
package gcom.operacional;

import gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()
public class SubBacia
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_SUBBACIA_INSERIR = 1491;

	public static final int ATRIBUTOS_SUBBACIA_ATUALIZAR = 1493;

	public static final int ATRIBUTOS_SUBBACIA_REMOVER = 3572;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private String descricao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private String descricaoAbreviada;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private Integer codigo;

	/** nullable persistent field */
	@ControleAlteracao(value = FiltroSubBacia.BACIA, funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private Bacia bacia;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private BigDecimal extensao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private BigDecimal diametro;

	/** nullable persistent field */
	@ControleAlteracao(value = FiltroSubBacia.MATERIAL_REDE_ESGOTO, funcionalidade = {ATRIBUTOS_SUBBACIA_INSERIR, ATRIBUTOS_SUBBACIA_ATUALIZAR, ATRIBUTOS_SUBBACIA_REMOVER})
	private MaterialRedeEsgoto materialRedeEsgoto;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Integer getCodigo(){

		return codigo;
	}

	public void setCodigo(Integer codigo){

		this.codigo = codigo;
	}

	public Bacia getBacia(){

		return bacia;
	}

	public void setBacia(Bacia bacia){

		this.bacia = bacia;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao", "bacia.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao", "Bac"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroSubBacia filtroSubBacia = new FiltroSubBacia();
		filtroSubBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroSubBacia.BACIA);
		filtroSubBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroSubBacia.MATERIAL_REDE_ESGOTO);

		filtroSubBacia.adicionarParametro(new ParametroSimples(FiltroSubBacia.ID, this.getId()));

		return filtroSubBacia;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public BigDecimal getExtensao(){

		return extensao;
	}

	public void setExtensao(BigDecimal extensao){

		this.extensao = extensao;
	}

	public BigDecimal getDiametro(){

		return diametro;
	}

	public void setDiametro(BigDecimal diametro){

		this.diametro = diametro;
	}

	public MaterialRedeEsgoto getMaterialRedeEsgoto(){

		return materialRedeEsgoto;
	}

	public void setMaterialRedeEsgoto(MaterialRedeEsgoto materialRedeEsgoto){

		this.materialRedeEsgoto = materialRedeEsgoto;
	}

	public String getDescricaoComCodigo(){

		String descricaoComCodigo = null;

		Integer codigo = this.getCodigo();
		String descricao = this.getDescricao();

		if(codigo != null && codigo.compareTo(10) == -1){
			descricaoComCodigo = "0" + codigo + " - " + descricao;
		}else if(codigo != null){
			descricaoComCodigo = codigo + " - " + descricao;
		}else{
			descricaoComCodigo = " - " + descricao;
		}

		return descricaoComCodigo;
	}

}
