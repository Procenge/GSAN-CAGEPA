
package gcom.gui.cadastro.imovel;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

public class ImovelCheckListDocumentacaoForm
				extends ValidatorActionForm {

	private String idCheckList;

	private String idImovel;

	private String inscricaoImovel;

	private String matriculaImovelDados;

	private String nomeLocalidade;

	private String codigoSituacaoAgua;

	private String descricaoSituacaoAgua;

	private String codigoSituacaoEsgoto;

	private String descricaoSituacaoEsgoto;

	private String numeroRota;

	private String numeroSegmento;

	private String nomeCliente;

	private String endereco;

	private String indicadorContrato;

	private String indicadorCpf;

	private String indicadorRg;

	private String indicadorDocImovel;

	private String indicadorTemoConfissaoDivida;

	private String indicadorCorrespondencia;

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 */
	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}

	public String getIdCheckList(){

		return idCheckList;
	}

	public void setIdCheckList(String idCheckList){

		this.idCheckList = idCheckList;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getMatriculaImovelDados(){

		return matriculaImovelDados;
	}

	public void setMatriculaImovelDados(String matriculaImovelDados){

		this.matriculaImovelDados = matriculaImovelDados;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getCodigoSituacaoAgua(){

		return codigoSituacaoAgua;
	}

	public void setCodigoSituacaoAgua(String codigoSituacaoAgua){

		this.codigoSituacaoAgua = codigoSituacaoAgua;
	}

	public String getDescricaoSituacaoAgua(){

		return descricaoSituacaoAgua;
	}

	public void setDescricaoSituacaoAgua(String descricaoSituacaoAgua){

		this.descricaoSituacaoAgua = descricaoSituacaoAgua;
	}

	public String getCodigoSituacaoEsgoto(){

		return codigoSituacaoEsgoto;
	}

	public void setCodigoSituacaoEsgoto(String codigoSituacaoEsgoto){

		this.codigoSituacaoEsgoto = codigoSituacaoEsgoto;
	}

	public String getDescricaoSituacaoEsgoto(){

		return descricaoSituacaoEsgoto;
	}

	public void setDescricaoSituacaoEsgoto(String descricaoSituacaoEsgoto){

		this.descricaoSituacaoEsgoto = descricaoSituacaoEsgoto;
	}

	public String getNumeroRota(){

		return numeroRota;
	}

	public void setNumeroRota(String numeroRota){

		this.numeroRota = numeroRota;
	}

	public String getNumeroSegmento(){

		return numeroSegmento;
	}

	public void setNumeroSegmento(String numeroSegmento){

		this.numeroSegmento = numeroSegmento;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getIndicadorContrato(){

		return indicadorContrato;
	}

	public void setIndicadorContrato(String indicadorContrato){

		this.indicadorContrato = indicadorContrato;
	}

	public String getIndicadorCpf(){

		return indicadorCpf;
	}

	public void setIndicadorCpf(String indicadorCpf){

		this.indicadorCpf = indicadorCpf;
	}

	public String getIndicadorRg(){

		return indicadorRg;
	}

	public void setIndicadorRg(String indicadorRg){

		this.indicadorRg = indicadorRg;
	}

	public String getIndicadorDocImovel(){

		return indicadorDocImovel;
	}

	public void setIndicadorDocImovel(String indicadorDocImovel){

		this.indicadorDocImovel = indicadorDocImovel;
	}

	public String getIndicadorTemoConfissaoDivida(){

		return indicadorTemoConfissaoDivida;
	}

	public void setIndicadorTemoConfissaoDivida(String indicadorTemoConfissaoDivida){

		this.indicadorTemoConfissaoDivida = indicadorTemoConfissaoDivida;
	}

	public String getIndicadorCorrespondencia(){

		return indicadorCorrespondencia;
	}

	public void setIndicadorCorrespondencia(String indicadorCorrespondencia){

		this.indicadorCorrespondencia = indicadorCorrespondencia;
	}

}
