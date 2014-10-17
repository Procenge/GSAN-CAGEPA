
package gcom.cobranca;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.opcaoparcelamento.PreParcelamento;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

/**
 * Entidade CobrancaNegociacaoAtendimento
 * 
 * @author Hebert Falcão
 * @date 25/11/2011
 */
public class CobrancaNegociacaoAtendimento
				extends ObjetoTransacao {

	public static final String EXTRATO_DE_DEBITO = "Extrato de Débito";

	public static final String PRE_PARCELAMENTO = "Pré-parcelamento";

	private static final long serialVersionUID = 1L;

	private Integer id;

	private CobrancaDocumento cobrancaDocumento;

	private PreParcelamento preParcelamento;

	private Imovel imovel;

	private RegistroAtendimento registroAtendimento;

	private String numeroCpf;

	private String numeroCnpj;

	private String email;

	private Date dataVencimento;

	private Date ultimaAlteracao;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public CobrancaDocumento getCobrancaDocumento(){

		return cobrancaDocumento;
	}

	public void setCobrancaDocumento(CobrancaDocumento cobrancaDocumento){

		this.cobrancaDocumento = cobrancaDocumento;
	}

	public PreParcelamento getPreParcelamento(){

		return preParcelamento;
	}

	public void setPreParcelamento(PreParcelamento preParcelamento){

		this.preParcelamento = preParcelamento;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public String getNumeroCnpj(){

		return numeroCnpj;
	}

	public void setNumeroCnpj(String numeroCnpj){

		this.numeroCnpj = numeroCnpj;
	}

	public String getNumeroCpf(){

		return numeroCpf;
	}

	public void setNumeroCpf(String numeroCpf){

		this.numeroCpf = numeroCpf;
	}

	public String getEmail(){

		return email;
	}

	public void setEmail(String email){

		this.email = email;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Filtro retornaFiltro(){

		FiltroCobrancaNegociacaoAtendimento filtro = new FiltroCobrancaNegociacaoAtendimento();
		filtro.adicionarParametro(new ParametroSimples(FiltroCobrancaNegociacaoAtendimento.ID, this.getId()));

		return filtro;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

}
