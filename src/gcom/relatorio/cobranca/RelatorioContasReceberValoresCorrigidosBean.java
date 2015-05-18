
package gcom.relatorio.cobranca;

import gcom.cadastro.cliente.ClienteFone;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.relatorio.RelatorioBean;

import java.util.ArrayList;
import java.util.Collection;

public class RelatorioContasReceberValoresCorrigidosBean
				implements RelatorioBean {

	private String cdRegional;

	private String cdLocal;

	private String matriculaImovel;

	private String abreviacaoLogradouro;

	private String abreviacaoTituloLogradouro;

	private String numeroLogradouroString;

	private String numeroImovel;

	private String numeroBairro;

	private String numeroMunicipio;

	private String numeroCep;

	private String descricaoComplementoEndereco;

	private String descricaoLigacaoAguaSituacao;

	private String cdCliente;

	private String nomeCliente;

	private String pessoFisicaoJuridica;

	private String cpf;

	private String cnpj;

	private String cdDDD;

	private String fone;

	private String ramal;

	private String quantidade;

	private String valorConta;

	private Imovel imovel;

	private String referencia;

	private String logradouroFormatado;

	private String cpfOrCnpj;

	private Collection<ClienteImovel> setClienteImovel;

	public RelatorioContasReceberValoresCorrigidosBean() {

	}

	public RelatorioContasReceberValoresCorrigidosBean(String cdRegional, String cdLocal, String matriculaImovel,
														String abreviacaoLogadouro, String abreviacaoTituloLogradouro,
														String numeroLogradouroString, String numeroImovel, String numeroBairro,
														String numeroMunicipio, String numeroCep, String descricaoComplementoEndereco,
														String descricaoLigacaoAguaSituacao, String quantidade, String valorConta,
														Imovel imovel, String referencia) {

		this.cdRegional = cdRegional;
		this.cdLocal = cdLocal;
		this.matriculaImovel = matriculaImovel;
		this.abreviacaoLogradouro = abreviacaoLogadouro;
		this.abreviacaoTituloLogradouro = abreviacaoTituloLogradouro;
		this.numeroLogradouroString = numeroLogradouroString;
		this.numeroImovel = numeroImovel;
		this.numeroBairro = numeroBairro;
		this.numeroMunicipio = numeroMunicipio;
		this.numeroCep = numeroCep;
		this.descricaoComplementoEndereco = descricaoComplementoEndereco;
		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
		this.quantidade = quantidade;

		this.valorConta = valorConta;

		this.imovel = imovel;
		this.referencia = referencia;

		this.setClienteImovel = imovel.getClienteImoveis();
	}

	public String getCdRegional(){

		return cdRegional;
	}

	public void setCdRegional(String cdRegional){

		this.cdRegional = cdRegional;
	}

	public String getCdLocal(){

		return cdLocal;
	}

	public void setCdLocal(String cdLocal){

		this.cdLocal = cdLocal;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getAbreviacaoLogradouro(){

		return abreviacaoLogradouro;
	}

	public void setAbreviacaoLogradouro(String abreviacaoLogradouro){

		this.abreviacaoLogradouro = abreviacaoLogradouro;
	}

	public String getAbreviacaoTituloLogradouro(){

		return abreviacaoTituloLogradouro;
	}

	public void setAbreviacaoTituloLogradouro(String abreviacaoTituloLogradouro){

		this.abreviacaoTituloLogradouro = abreviacaoTituloLogradouro;
	}

	public String getNumeroLogradouroString(){

		return numeroLogradouroString;
	}

	public void setNumeroLogradouroString(String numeroLogradouroString){

		this.numeroLogradouroString = numeroLogradouroString;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public String getNumeroBairro(){

		return numeroBairro;
	}

	public void setNumeroBairro(String numeroBairro){

		this.numeroBairro = numeroBairro;
	}

	public String getNumeroMunicipio(){

		return numeroMunicipio;
	}

	public void setNumeroMunicipio(String numeroMunicipio){

		this.numeroMunicipio = numeroMunicipio;
	}

	public String getNumeroCep(){

		return numeroCep;
	}

	public void setNumeroCep(String numeroCep){

		this.numeroCep = numeroCep;
	}

	public String getDescricaoComplementoEndereco(){

		return descricaoComplementoEndereco;
	}

	public void setDescricaoComplementoEndereco(String descricaoComplementoEndereco){

		this.descricaoComplementoEndereco = descricaoComplementoEndereco;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getCdCliente(){

		if(cdCliente == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				cdCliente = clienteImovel.getCliente().getId().toString();
				break;
			}
		}

		return cdCliente;
	}

	public void setCdCliente(String cdCliente){

		this.cdCliente = cdCliente;
	}

	public String getNomeCliente(){

		if(nomeCliente == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				nomeCliente = clienteImovel.getCliente().getNome().toUpperCase();
				break;
			}
		}

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getPessoFisicaoJuridica(){

		if(pessoFisicaoJuridica == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				Short indicador = clienteImovel.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica();
				pessoFisicaoJuridica = (indicador.equals(new Short("1")) ? "PF" : "PJ");

				break;
			}
		}

		return pessoFisicaoJuridica;
	}

	public void setPessoFisicaoJuridica(String pessoFisicaoJuridica){

		this.pessoFisicaoJuridica = pessoFisicaoJuridica;
	}

	public String getCpf(){

		if(cpf == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				cpf = gcom.util.Util.formatarCpf(clienteImovel.getCliente().getCpf());

				break;
			}
		}

		return cpf;
	}

	public void setCpf(String cpf){

		this.cpf = cpf;
	}

	public String getCnpj(){

		if(cnpj == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				cnpj = gcom.util.Util.formatarCnpj(clienteImovel.getCliente().getCnpj());

				break;
			}
		}

		return cnpj;
	}

	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	public String getCdDDD(){

		if(cdDDD == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				if(clienteImovel.getCliente().getClienteFones().isEmpty()){
					break;
				}
				cdDDD = new ArrayList<ClienteFone>(clienteImovel.getCliente().getClienteFones()).get(0).getDdd();

				break;
			}
		}

		return cdDDD;
	}

	public void setCdDDD(String cdDDD){

		this.cdDDD = cdDDD;
	}

	public String getFone(){

		if(fone == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				if(clienteImovel.getCliente().getClienteFones().isEmpty()){
					break;
				}
				fone = new ArrayList<ClienteFone>(clienteImovel.getCliente().getClienteFones()).get(0).getDddTelefone();

				break;
			}
		}

		return fone;
	}

	public void setFone(String fone){

		this.fone = fone;
	}

	public String getRamal(){

		if(ramal == null){

			for(ClienteImovel clienteImovel : setClienteImovel){
				if(clienteImovel.getCliente().getClienteFones().isEmpty()){
					break;
				}
				ramal = new ArrayList<ClienteFone>(clienteImovel.getCliente().getClienteFones()).get(0).getRamal();

				break;
			}
		}

		return ramal;
	}

	public void setRamal(String ramal){

		this.ramal = ramal;
	}

	public String getValorConta(){

		return valorConta;
	}

	public void setValorConta(String valorConta){

		this.valorConta = valorConta;
	}

	public String getQuantidade(){

		return quantidade;
	}

	public void setQuantidade(String quantidade){

		this.quantidade = quantidade;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public String getReferencia(){

		return referencia;
	}

	public void setReferencia(String referencia){

		this.referencia = referencia;
	}

	public String getLogradouroFormatado(){

		return logradouroFormatado;
	}

	public void setLogradouroFormatado(String logradouroFormatado){

		this.logradouroFormatado = logradouroFormatado;
	}

	public String getCpfOrCnpj(){

		return cpfOrCnpj;
	}

	public void setCpfOrCnpj(String cpfOrCnpj){

		this.cpfOrCnpj = cpfOrCnpj;
	}

}
