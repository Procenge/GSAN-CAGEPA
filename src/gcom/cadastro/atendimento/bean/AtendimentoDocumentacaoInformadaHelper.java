/**
 * 
 */

package gcom.cadastro.atendimento.bean;

import gcom.cadastro.atendimento.AtendProcDocumentoPessoaTipo;
import gcom.cadastro.atendimento.Atendimento;
import gcom.cadastro.atendimento.FiltroAtendimentoDocumentacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Gicevalter Couto 
 */
public class AtendimentoDocumentacaoInformadaHelper {

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	private Atendimento atendimento;

	private AtendProcDocumentoPessoaTipo atendProcDocumentoPessoaTipo;

	private String identificadorDocumentoApresentado;

	private String indicadorDocumentoApresentado;

	public String descricaoEspecificacao;

	public String nomeArquivo;

	private byte[] conteudoArquivo;

	private Short indicadorDocumentoObrigatorio;

	public Short getIndicadorDocumentoObrigatorio(){

		return this.indicadorDocumentoObrigatorio;
	}

	public void setIndicadorDocumentoObrigatorio(Short indicadorDocumentoObrigatorio){

		this.indicadorDocumentoObrigatorio = indicadorDocumentoObrigatorio;
	}

	public String getDescricaoEspecificacao(){

		return this.descricaoEspecificacao;
	}

	public void setDescricaoEspecificacao(String descricaoEspecificacao){

		this.descricaoEspecificacao = descricaoEspecificacao;
	}

	public byte[] getConteudoArquivo(){

		return conteudoArquivo;
	}

	public void setConteudoArquivo(byte[] conteudoArquivo){

		this.conteudoArquivo = conteudoArquivo;
	}

	public String getNomeArquivo(){

		return this.nomeArquivo;
	}

	public void setNomeArquivo(String nomeArquivo){

		this.nomeArquivo = nomeArquivo;
	}

	public String getIndicadorDocumentoApresentado(){

		return this.indicadorDocumentoApresentado;
	}

	public void setIndicadorDocumentoApresentado(String indicadorDocumentoApresentado){

		this.indicadorDocumentoApresentado = indicadorDocumentoApresentado;
	}

	public String getIdentificadorDocumentoApresentado(){

		return this.identificadorDocumentoApresentado;
	}

	public void setIdentificadorDocumentoApresentado(String identificadorDocumentoApresentado){

		this.identificadorDocumentoApresentado = identificadorDocumentoApresentado;
	}

	public Atendimento getAtendimento(){

		return this.atendimento;
	}

	public void setAtendimento(Atendimento atendimento){

		this.atendimento = atendimento;
	}

	public AtendProcDocumentoPessoaTipo getAtendProcDocumentoPessoaTipo(){

		return this.atendProcDocumentoPessoaTipo;
	}

	public void setAtendProcDocumentoPessoaTipo(AtendProcDocumentoPessoaTipo atendProcDocumentoPessoaTipo){

		this.atendProcDocumentoPessoaTipo = atendProcDocumentoPessoaTipo;
	}

	/** default constructor */
	public AtendimentoDocumentacaoInformadaHelper() {

	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		return new FiltroAtendimentoDocumentacao();
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
