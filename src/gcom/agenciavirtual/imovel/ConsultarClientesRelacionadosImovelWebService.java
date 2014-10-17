
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.fachada.Fachada;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna os Referências Endereço
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarClientesRelacionadosImovelWebService.do?matricula=xx
 * 
 * @author Ado Rocha
 */
public class ConsultarClientesRelacionadosImovelWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		Integer idImovel = recuperarParametroInteiroObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);

		Collection<ClienteImovel> colecaoClientesImovel = fachada.pesquisarClientesImovel(idImovel);

		if(!Util.isVazioOrNulo(colecaoClientesImovel)){
			Collection<ClienteImovelJSONHelper> colecaoClienteImovelJSONHelper = new ArrayList<ClienteImovelJSONHelper>();

			for(ClienteImovel clienteImovel : colecaoClientesImovel){
				ClienteImovelJSONHelper helper = new ClienteImovelJSONHelper();
				helper.setIdCliente(clienteImovel.getCliente().getId());
				helper.setNomeCliente(clienteImovel.getCliente().getNome());
				helper.setDescricaoTipoRelacao(clienteImovel.getClienteRelacaoTipo().getDescricao());
				helper.setDataInicioRelacao(clienteImovel.getDataInicioRelacao());
				helper.setClienteFones(clienteImovel.getCliente().getClienteFones());
				helper.setCnpj(clienteImovel.getCliente().getCnpj());
				helper.setCpf(clienteImovel.getCliente().getCpf());
				helper.setIdTipoRelacao(clienteImovel.getClienteRelacaoTipo().getId());
				helper.setIdRelacionamentoClienteImovel(clienteImovel.getId());
				helper.setEmail(clienteImovel.getCliente().getEmail());
				helper.setIdTipoCliente(clienteImovel.getCliente().getClienteTipo().getId());
				helper.setIndicadorPessoaFisicaJuridica(clienteImovel.getCliente().getClienteTipo().getIndicadorPessoaFisicaJuridica());
				helper.setRg(clienteImovel.getCliente().getRg());

				colecaoClienteImovelJSONHelper.add(helper);
			}

			adicionarListaAoBody("clientesImovel", colecaoClienteImovelJSONHelper);

		}else{
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
