
package gcom.agenciavirtual.cadastro.cliente;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.fachada.Fachada;
import gcom.util.FachadaException;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

/**
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>Nome [OBRIGATORIO]</li>
 * <li>Id do Tipo do Cliente [OBRIGATORIO]</li>
 * <li>CPF ou CNPJ [OBRIGATORIO]</li>
 * <li>RG [OBRIGATORIO]</li>
 * <li>Id do Orgao Expeditor RG [OBRIGATORIO]</li>
 * <li>Sigla Estado [OBRIGATORIO]</li>
 * <li>Id da Unidade Federação (Estado)[OBRIGATORIO]</li>
 * <li>Id da Pessoa Sexo (Sexo)[OBRIGATORIO]</li>
 * <li>Id do Endereco Tipo [OBRIGATORIO]</li>
 * <li>Id do Logradouro [OBRIGATORIO]</li>
 * <li>Número [OBRIGATORIO]</li>
 * <li>Id do Bairro [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/inserirClienteWebService.do
 * 
 * @author Yara Souza
 */
public class InserirClienteWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		ClienteJSONHelper clienteJSONHelper = this.obterClienteJSONhelper(request);
		try{

			Fachada.getInstancia().inserirCliente(clienteJSONHelper);


		}catch(FachadaException e){

			e.getStackTrace();

					informarStatus(STATUS_TIPO_ALERTA, e.getMensagem());

		}

		
		
	}


	@Override
	protected boolean isServicoRestrito(){

		return false;
	}



	private ClienteJSONHelper obterClienteJSONhelper(HttpServletRequest request) throws Exception{

		ClienteJSONHelper helper = new ClienteJSONHelper();

		helper.setNome(this.recuperarParametroString("nome", "NOME", Boolean.TRUE, Boolean.TRUE, request));
		helper.setIdTipoCliente(this.recuperarParametroInteiro("idTipoCliente", "TIPO CLIENTE", Boolean.TRUE, Boolean.TRUE, request));
		helper.setCpf(this.recuperarParametroString("cpf", "CPF", Boolean.FALSE, Boolean.FALSE, request));
		helper.setCnpj(this.recuperarParametroString("cnpj", "CNPJ", Boolean.FALSE, Boolean.FALSE, request));
		helper.setCpfCnpj(this.recuperarParametroString("cpfCnpj", "CPF/CNPJ", Boolean.TRUE, Boolean.TRUE, request));
		helper.setMatricula(this.recuperarParametroString("matricula", "MATRICULA", Boolean.TRUE, Boolean.TRUE, request));
		helper.setRg(this.recuperarParametroString("rg", "RG", Boolean.TRUE, Boolean.TRUE, request));
		helper.setIdOrgaoExpdidorRg(this.recuperarParametroInteiro("idOrgaoExpdidorRg", "ORGÃO EXPEDIDOR", Boolean.TRUE, Boolean.TRUE,
						request));
		helper.setSiglaEstado(this.recuperarParametroString("siglaEstado", "SIGLA ESTADO", Boolean.TRUE, Boolean.TRUE, request));
		helper.setIdUnidadeFederacao(this.recuperarParametroInteiro("idUnidadeFederacao", "UNIDADE FEDERAÇÃO", Boolean.TRUE, Boolean.TRUE,
						request));
		helper.setIdPessoaSexo(this.recuperarParametroInteiro("idPessoaSexo", "PESSOA SEXO", Boolean.TRUE, Boolean.TRUE, request));
		helper.setIdEnderecoTipo(this.recuperarParametroInteiro("idEnderecoTipo", "ENDEREÇO TIPO", Boolean.TRUE, Boolean.TRUE, request));
		helper.setIdLogradouro(this.recuperarParametroInteiro("idLogradouro", "LOGRADOURO", Boolean.TRUE, Boolean.TRUE, request));
		helper.setNumero(this.recuperarParametroString("numero", "NUMERO", Boolean.TRUE, Boolean.TRUE, request));
		helper.setIdBairro(this.recuperarParametroInteiro("idBairro", "BAIRRO", Boolean.TRUE, Boolean.TRUE, request));

		helper.setNomeAbreviado(this.recuperarParametroString("nomeAbreviado", "NOME ABREVIADO", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdTipoClienteEspecial(this.recuperarParametroInteiro("idTipoClienteEspecial", "TIPO CLIENTE ESPECIAL", Boolean.FALSE,
						Boolean.TRUE, request));
		helper.setEmail(this.recuperarParametroString("email", "EMAIL", Boolean.FALSE, Boolean.TRUE, request));
		helper.setDataEmissaoRg(this.recuperarParametroString("dataEmissaoRg", "DATA EMISSÃO RG", Boolean.FALSE, Boolean.TRUE, request));
		helper.setDataNascimento(this.recuperarParametroString("dataNascimento", "DATA NASCIMENTO", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdProfissao(this.recuperarParametroInteiro("idProfissao", "PROFISSÃO", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdRaca(this.recuperarParametroInteiro("idRaca", "RAÇA", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdNacionalidade(this.recuperarParametroInteiro("idNacionalidade", "NACIONALIADE", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdEstadoCivil(this.recuperarParametroInteiro("idEstadoCivil", "ESTADO CIVIL", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdEnderecoReferencia(this.recuperarParametroInteiro("idEnderecoReferencia", "ENDEREÇO REFERENCIA", Boolean.FALSE,
						Boolean.TRUE, request));
		helper.setComplementoEndereco(this.recuperarParametroString("complementoEndereco", "COMPLEMENTO ENDEREÇO", Boolean.FALSE,
						Boolean.TRUE, request));
		helper.setIdTipoTelefone(this.recuperarParametroInteiro("idTipoTelefone", "TIPO TELEFONE", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdMunicipio(this.recuperarParametroInteiro("idMunicipio", "MUNICIPIO", Boolean.FALSE, Boolean.TRUE, request));
		helper.setDdd(this.recuperarParametroString("ddd", "DDD", Boolean.FALSE, Boolean.TRUE, request));
		helper.setNumeroTelefone(this.recuperarParametroString("numeroTelefone", "NUMERO TELEFONE", Boolean.FALSE, Boolean.TRUE, request));
		helper.setRamal(this.recuperarParametroString("ramal", "RAMAL", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIcCobrancaMulta(this.recuperarParametroShort("icCobrancaMulta", "INDICADOR COBRANÇA MULTA", Boolean.FALSE, Boolean.TRUE,
						request));
		helper.setIcJuros(this.recuperarParametroShort("icJuros", "INDICADOR COBRANÇA JUROS", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIcCobrancaCorrecao(this.recuperarParametroShort("icCobrancaCorrecao", "INDICADOR COBRANÇA CORREÇÃO", Boolean.FALSE,
						Boolean.TRUE, request));
		helper.setIcCobrancaImpostoFederal(this.recuperarParametroShort("icCobrancaImpostoFederal", "INDICADOR COBRANÇA IMPOSTO FEDERAL",
						Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdBanco(this.recuperarParametroInteiro("idBanco", "BANCO", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIdAgencia(this.recuperarParametroInteiro("idAgencia", "AGÊNCIA", Boolean.FALSE, Boolean.TRUE, request));
		helper.setNumeroConta(this.recuperarParametroString("numeroConta", "NUMERO CONTA", Boolean.FALSE, Boolean.TRUE, request));
		helper.setIcInformarDadosResponsavel(this.recuperarParametroShort("icInformarDadosResponsavel",
						"INDICADOR INFORMAR DADOS RESPONSAVEL", Boolean.FALSE, Boolean.TRUE, request));

		return helper;

	}

}
