
package gcom.cobranca.bean;

import gcom.cobranca.CobrancaDocumentoItem;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class EmitirDocumentoOrdemCorteModelo3Helper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCobrancaDocumento;

	private Integer idImovel;

	private Date dataApresentacaoCorte;

	private Short quantidadeDiasRealizacaoAtividadeComando;

	private Short quantidadeDiasRealizacaoCobrancaAcao;

	private Integer numeroDocumentoCobranca;

	private String nomeImovelOuClienteUsuario;

	private String enderecoParte1;

	private String enderecoParte2;

	private Integer idLocalidade;

	private String nomeLocalidade;

	private Integer setor;

	private Integer quadra;

	private Short lote;

	private Short numeroEconomiasImovel;

	private String categoriaImovel;

	private Collection<CobrancaDocumentoItem> colecaoItemConta;

	private BigDecimal valorTotalItensExcedentes;

	private BigDecimal valorDocumentoCobranca;

	private String numeroHidrometro;

	private Date dataRetiradaHidrometro;

	private String perfilImovel;

	private String foneCliente;

	private String descricaoCorteRegistroTipo;

	private Date dataApresentacaoAviso;

	private Integer idGerenciaRegional;

	private Integer idPerfilImovel;

	private String idCorteRegistroTipo;

	private Short subLote;

	private String enderecoTipoLogradouro;

	private String enderecoNomeLogradouro;

	private String enderecoTituloLogradouro;

	private String enderecoReferencia;

	private String enderecoNumeroImovel;

	private String enderecoComplemento;

	private String enderecoBairro;

	private String enderecoCodigoCEP;

	private Integer idCobrancaAcaoAtividadeComando;

	private String enderecoFormatado;

	private int sequencialImpressao;

	private int totalContasImpressao;

	private Integer idOrdemServico;

	public EmitirDocumentoOrdemCorteModelo3Helper() {

		this.sequencialImpressao = 0;
		this.totalContasImpressao = 0;
	}

	public Integer getIdCobrancaDocumento(){

		return idCobrancaDocumento;
	}

	public void setIdCobrancaDocumento(Integer idCobrancaDocumento){

		this.idCobrancaDocumento = idCobrancaDocumento;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Date getDataApresentacaoCorte(){

		return dataApresentacaoCorte;
	}

	public void setDataApresentacaoCorte(Date dataApresentacaoCorte){

		this.dataApresentacaoCorte = dataApresentacaoCorte;
	}

	public Short getQuantidadeDiasRealizacaoAtividadeComando(){

		return quantidadeDiasRealizacaoAtividadeComando;
	}

	public void setQuantidadeDiasRealizacaoAtividadeComando(Short quantidadeDiasRealizacaoAtividadeComando){

		this.quantidadeDiasRealizacaoAtividadeComando = quantidadeDiasRealizacaoAtividadeComando;
	}

	public Short getQuantidadeDiasRealizacaoCobrancaAcao(){

		return quantidadeDiasRealizacaoCobrancaAcao;
	}

	public void setQuantidadeDiasRealizacaoCobrancaAcao(Short quantidadeDiasRealizacaoCobrancaAcao){

		this.quantidadeDiasRealizacaoCobrancaAcao = quantidadeDiasRealizacaoCobrancaAcao;
	}

	public Integer getNumeroDocumentoCobranca(){

		return numeroDocumentoCobranca;
	}

	public void setNumeroDocumentoCobranca(Integer numeroDocumentoCobranca){

		this.numeroDocumentoCobranca = numeroDocumentoCobranca;
	}

	public String getNomeImovelOuClienteUsuario(){

		return nomeImovelOuClienteUsuario;
	}

	public void setNomeImovelOuClienteUsuario(String nomeImovelOuClienteUsuario){

		this.nomeImovelOuClienteUsuario = nomeImovelOuClienteUsuario;
	}

	public String getEnderecoParte1(){

		return enderecoParte1;
	}

	public void setEnderecoParte1(String enderecoParte1){

		this.enderecoParte1 = enderecoParte1;
	}

	public String getEnderecoParte2(){

		return enderecoParte2;
	}

	public void setEnderecoParte2(String enderecoParte2){

		this.enderecoParte2 = enderecoParte2;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public Integer getSetor(){

		return setor;
	}

	public void setSetor(Integer setor){

		this.setor = setor;
	}

	public Integer getQuadra(){

		return quadra;
	}

	public void setQuadra(Integer quadra){

		this.quadra = quadra;
	}

	public Short getLote(){

		return lote;
	}

	public void setLote(Short lote){

		this.lote = lote;
	}

	public Short getNumeroEconomiasImovel(){

		return numeroEconomiasImovel;
	}

	public void setNumeroEconomiasImovel(Short numeroEconomiasImovel){

		this.numeroEconomiasImovel = numeroEconomiasImovel;
	}

	public String getCategoriaImovel(){

		return categoriaImovel;
	}

	public void setCategoriaImovel(String categoriaImovel){

		this.categoriaImovel = categoriaImovel;
	}

	public Collection<CobrancaDocumentoItem> getColecaoItemConta(){

		return colecaoItemConta;
	}

	public void setColecaoItemConta(Collection<CobrancaDocumentoItem> colecaoItemConta){

		this.colecaoItemConta = colecaoItemConta;
	}

	public BigDecimal getValorTotalItensExcedentes(){

		return valorTotalItensExcedentes;
	}

	public void setValorTotalItensExcedentes(BigDecimal valorTotalItensExcedentes){

		this.valorTotalItensExcedentes = valorTotalItensExcedentes;
	}

	public BigDecimal getValorDocumentoCobranca(){

		return valorDocumentoCobranca;
	}

	public void setValorDocumentoCobranca(BigDecimal valorDocumentoCobranca){

		this.valorDocumentoCobranca = valorDocumentoCobranca;
	}

	public String getNumeroHidrometro(){

		return numeroHidrometro;
	}

	public void setNumeroHidrometro(String numeroHidrometro){

		this.numeroHidrometro = numeroHidrometro;
	}

	public Date getDataRetiradaHidrometro(){

		return dataRetiradaHidrometro;
	}

	public void setDataRetiradaHidrometro(Date dataRetiradaHidrometro){

		this.dataRetiradaHidrometro = dataRetiradaHidrometro;
	}

	public String getPerfilImovel(){

		return perfilImovel;
	}

	public void setPerfilImovel(String perfilImovel){

		this.perfilImovel = perfilImovel;
	}

	public String getFoneCliente(){

		return foneCliente;
	}

	public void setFoneCliente(String foneCliente){

		this.foneCliente = foneCliente;
	}

	public String getDescricaoCorteRegistroTipo(){

		return descricaoCorteRegistroTipo;
	}

	public void setDescricaoCorteRegistroTipo(String descricaoCorteRegistroTipo){

		this.descricaoCorteRegistroTipo = descricaoCorteRegistroTipo;
	}

	public Date getDataApresentacaoAviso(){

		return dataApresentacaoAviso;
	}

	public void setDataApresentacaoAviso(Date dataApresentacaoAviso){

		this.dataApresentacaoAviso = dataApresentacaoAviso;
	}

	public Integer getIdGerenciaRegional(){

		return idGerenciaRegional;
	}

	public void setIdGerenciaRegional(Integer idGerenciaRegional){

		this.idGerenciaRegional = idGerenciaRegional;
	}

	public Integer getIdPerfilImovel(){

		return idPerfilImovel;
	}

	public void setIdPerfilImovel(Integer idPerfilImovel){

		this.idPerfilImovel = idPerfilImovel;
	}

	public String getIdCorteRegistroTipo(){

		return idCorteRegistroTipo;
	}

	public void setIdCorteRegistroTipo(String idCorteRegistroTipo){

		this.idCorteRegistroTipo = idCorteRegistroTipo;
	}

	public Short getSubLote(){

		return subLote;
	}

	public void setSubLote(Short subLote){

		this.subLote = subLote;
	}

	public int getSequencialImpressao(){

		return sequencialImpressao;
	}

	public void setSequencialImpressao(int sequencialImpressao){

		this.sequencialImpressao = sequencialImpressao;
	}

	public int getTotalContasImpressao(){

		return totalContasImpressao;
	}

	public void setTotalContasImpressao(int totalContasImpressao){

		this.totalContasImpressao = totalContasImpressao;
	}

	public String getEnderecoTipoLogradouro(){

		return enderecoTipoLogradouro;
	}

	public void setEnderecoTipoLogradouro(String enderecoTipoLogradouro){

		this.enderecoTipoLogradouro = enderecoTipoLogradouro;
	}

	public String getEnderecoNomeLogradouro(){

		return enderecoNomeLogradouro;
	}

	public void setEnderecoNomeLogradouro(String enderecoNomeLogradouro){

		this.enderecoNomeLogradouro = enderecoNomeLogradouro;
	}

	public String getEnderecoTituloLogradouro(){

		return enderecoTituloLogradouro;
	}

	public void setEnderecoTituloLogradouro(String enderecoTituloLogradouro){

		this.enderecoTituloLogradouro = enderecoTituloLogradouro;
	}

	public String getEnderecoReferencia(){

		return enderecoReferencia;
	}

	public void setEnderecoReferencia(String enderecoReferencia){

		this.enderecoReferencia = enderecoReferencia;
	}

	public String getEnderecoNumeroImovel(){

		return enderecoNumeroImovel;
	}

	public void setEnderecoNumeroImovel(String enderecoNumeroImovel){

		this.enderecoNumeroImovel = enderecoNumeroImovel;
	}

	public String getEnderecoComplemento(){

		return enderecoComplemento;
	}

	public void setEnderecoComplemento(String enderecoComplemento){

		this.enderecoComplemento = enderecoComplemento;
	}

	public String getEnderecoBairro(){

		return enderecoBairro;
	}

	public void setEnderecoBairro(String enderecoBairro){

		this.enderecoBairro = enderecoBairro;
	}

	public String getEnderecoCodigoCEP(){

		return enderecoCodigoCEP;
	}

	public void setEnderecoCodigoCEP(String enderecoCodigoCEP){

		this.enderecoCodigoCEP = enderecoCodigoCEP;
	}

	public Integer getIdCobrancaAcaoAtividadeComando(){

		return idCobrancaAcaoAtividadeComando;
	}

	public void setIdCobrancaAcaoAtividadeComando(Integer idCobrancaAcaoAtividadeComando){

		this.idCobrancaAcaoAtividadeComando = idCobrancaAcaoAtividadeComando;
	}

	public String getEnderecoFormatado(){

		return enderecoFormatado;
	}

	public void setEnderecoFormatado(String enderecoFormatado){

		this.enderecoFormatado = enderecoFormatado;
	}

	public Integer getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public void organizarCamposEndereco(){

		this.enderecoParte1 = "";
		this.enderecoParte2 = "";

		ArrayList<String> enderecoParametros = new ArrayList<String>();

		enderecoParametros.add(this.getEnderecoTipoLogradouro());
		enderecoParametros.add(this.getEnderecoTituloLogradouro());
		enderecoParametros.add(this.getEnderecoNomeLogradouro());
		enderecoParametros.add(this.getEnderecoReferencia());
		enderecoParametros.add(this.getEnderecoNumeroImovel());
		enderecoParametros.add(this.getEnderecoComplemento());
		enderecoParametros.add(this.getEnderecoBairro());
		enderecoParametros.add(this.getEnderecoCodigoCEP());

		int limiteCampoEndereco = 53;
		boolean primeiroValorRestante = true;
		boolean primeiroValorValido = false;
		String parametrosRestantes = "";
		String conector = "";

		for(String parametro : enderecoParametros){
			if(parametro != null){

				if((this.enderecoParte1 + conector + parametro.trim()).length() < limiteCampoEndereco){

					this.enderecoParte1 = this.enderecoParte1 + conector + parametro.trim();

					if(primeiroValorValido == false && !parametro.trim().equals("")){
						conector = " ";
						primeiroValorValido = true;
					}

				}else{
					if(primeiroValorRestante){
						parametrosRestantes = parametro.trim();
						primeiroValorRestante = false;
					}else{
						parametrosRestantes = parametrosRestantes + conector + parametro.trim();
					}
				}
			}
		}

		this.enderecoParte2 = parametrosRestantes;
	}
}
