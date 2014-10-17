package gcom.agenciavirtual.cadastro.cliente;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.*;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.brazilutils.br.cpfcnpj.CpfCnpj;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

public class PesquisarClienteWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		String campoMatricula = this.recuperarParametroString("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, Boolean.FALSE, Boolean.FALSE,
						request);
		Integer campoId = this.recuperarParametroInteiro("codigo", "CÓDIGO", Boolean.FALSE, Boolean.FALSE, request);
		CpfCnpj cpfCnpj = new CpfCnpj(this.recuperarParametroString("cpfcnpj", LABEL_CAMPO_CPF_CNPJ, Boolean.FALSE, Boolean.FALSE, request));
		
		FiltroCliente filtroCliente = new FiltroCliente();

		if(campoId != null){
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, campoId));
		}

		if(cpfCnpj.isCpf()){
			
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, cpfCnpj.getNumber()));
			
		}else{
			
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, cpfCnpj.getNumber()));
			
		}
		
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.SEXO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO_ESPECIAL);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.RACA);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.NACIONALIDADE);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ESTADO_CIVIL);

		Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente, Cliente.class.getName()));

		if(cliente == null){

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));

		}else{

			this.adicionarObjetoAoBody("cliente", this.obterClienteJSONHelper(cliente, campoMatricula));

		}

	}

	private ClienteJSONHelper obterClienteJSONHelper(Cliente cliente, String matricula){

		ClienteJSONHelper clienteHelper = new ClienteJSONHelper();
		clienteHelper.setId(cliente.getId());
		clienteHelper.setNome(cliente.getNome());
		clienteHelper.setIdTipoCliente(cliente.getClienteTipo().getId());

		if(cliente.getCpf() != null){

			CpfCnpj cpf = new CpfCnpj(cliente.getCpf());
			clienteHelper.setCpf(cpf.getCpfCnpj());
			clienteHelper.setCpfCnpj(cpf.getNumber());

		}else if(cliente.getCnpj() != null){

			CpfCnpj cnpj = new CpfCnpj(cliente.getCpf());
			clienteHelper.setCnpj(cnpj.getCpfCnpj());
			clienteHelper.setCpfCnpj(cnpj.getNumber());

		}

		clienteHelper.setRg(cliente.getRg());
		clienteHelper.setIdOrgaoExpdidorRg(cliente.getOrgaoExpedidorRg().getId());
		clienteHelper.setSiglaEstado(cliente.getUnidadeFederacao().getSigla());
		clienteHelper.setIdUnidadeFederacao(cliente.getUnidadeFederacao().getId());
		clienteHelper.setIdPessoaSexo(cliente.getPessoaSexo().getId());
		clienteHelper.setMatricula(matricula);

		ClienteEndereco clienteEndereco = this.obterClienteEndereco(cliente.getId());
		
		if(clienteEndereco != null){

			clienteHelper.setIdEnderecoTipo(clienteEndereco.getEnderecoTipo() != null ? clienteEndereco.getEnderecoTipo().getId() : null);
			
			if(clienteEndereco.getLogradouroBairro() != null){

				clienteHelper.setIdLogradouro(clienteEndereco.getLogradouroBairro().getLogradouro().getId());
				clienteHelper.setIdBairro(clienteEndereco.getLogradouroBairro().getBairro().getId());
				clienteHelper.setIdMunicipio(clienteEndereco.getLogradouroBairro().getBairro().getMunicipio().getId());

			}
			
			clienteHelper.setNumero(clienteEndereco.getNumero());
			clienteHelper.setIdEnderecoReferencia(clienteEndereco.getEnderecoReferencia() != null ? clienteEndereco.getEnderecoReferencia()
							.getId() : null);
			clienteHelper.setComplementoEndereco(clienteEndereco.getComplemento());

		}
		
		clienteHelper.setNomeAbreviado(clienteHelper.getNomeAbreviado());
		clienteHelper.setIdTipoClienteEspecial(cliente.getClienteTipoEspecial() != null ? cliente.getClienteTipoEspecial().getId() : null);
		clienteHelper.setEmail(cliente.getEmail());
		clienteHelper.setDataEmissaoRg(Util.formatarData(cliente.getDataEmissaoRg()));
		clienteHelper.setDataNascimento(Util.formatarData(cliente.getDataNascimento()));
		clienteHelper.setIdProfissao(cliente.getProfissao() != null ? cliente.getProfissao().getId() : null);
		clienteHelper.setIdRaca(cliente.getRaca() != null ? cliente.getRaca().getId() : null);
		clienteHelper.setIdNacionalidade(cliente.getNacionalidade() != null ? cliente.getNacionalidade().getId() : null);
		clienteHelper.setIdEstadoCivil(cliente.getEstadoCivil() != null ? cliente.getEstadoCivil().getId() : null);

		ClienteFone clienteFone = this.obterClienteFone(cliente.getId());

		if(clienteFone != null){

			clienteHelper.setIdTipoTelefone(clienteFone.getFoneTipo() != null ? clienteFone.getFoneTipo().getId() : null);
			clienteHelper.setDdd(clienteFone.getDdd());
			clienteHelper.setNumeroTelefone(clienteFone.getTelefone());
			clienteHelper.setRamal(clienteFone.getRamal());

		}

		ClienteResponsavel clienteResponsavel = this.obterClienteResponsavel(cliente.getId());

		if(clienteResponsavel != null){

			clienteHelper.setIcCobrancaMulta(clienteResponsavel.getIndMulta());
			clienteHelper.setIcJuros(clienteResponsavel.getIndJuros());
			clienteHelper.setIcCobrancaCorrecao(clienteResponsavel.getIndCorrecao());
			clienteHelper.setIcCobrancaImpostoFederal(clienteResponsavel.getIndImpostoFederal());

			if(clienteResponsavel.getAgencia() != null){

				clienteHelper.setIdBanco(clienteResponsavel.getAgencia().getBanco().getId());
				clienteHelper.setIdAgencia(clienteResponsavel.getAgencia().getId());
				clienteHelper.setNumeroConta(clienteResponsavel.getContaBancaria());

			}

			clienteHelper.setIcInformarDadosResponsavel(ConstantesSistema.SIM);

		}else{

			clienteHelper.setIcInformarDadosResponsavel(ConstantesSistema.NAO);

		}

		return clienteHelper;

	}

	private ClienteResponsavel obterClienteResponsavel(Integer idCliente){

		FiltroClienteResponsavel filtroClienteResponsavel = new FiltroClienteResponsavel();
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.CLIENTE_ID, idCliente));
		filtroClienteResponsavel.adicionarParametro(new ParametroSimples(FiltroClienteResponsavel.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroClienteResponsavel.adicionarCaminhoParaCarregamentoEntidade("agencia.banco");

		ClienteResponsavel clienteResponsavel = (ClienteResponsavel) Util.retonarObjetoDeColecao(Fachada.getInstancia()
						.pesquisarTabelaAuxiliar(filtroClienteResponsavel, ClienteResponsavel.class.getName()));

		return clienteResponsavel;

	}

	private ClienteFone obterClienteFone(Integer idCliente){

		FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
		filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, idCliente));
		filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteFone.FONE_TIPO);

		return (ClienteFone) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroClienteFone, ClienteFone.class.getName()));

	}

	private ClienteEndereco obterClienteEndereco(Integer idCliente){

		FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
		filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, idCliente));
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_TIPO);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteEndereco.ENDERECO_REFERENCIA);
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro");
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
		filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio");

		return (ClienteEndereco) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroClienteEndereco,
						ClienteEndereco.class.getName()));

	}

}
