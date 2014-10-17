
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroVencimentoAlternativo;
import gcom.faturamento.SetorComercialVencimento;
import gcom.faturamento.VencimentoAlternativo;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.json.JSONException;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/informarVencimentoAlternativoWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class InformarVencimentoAlternativoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer idImovel = recuperarParametroInteiroObrigatorio("matricula", "Imóvel", true, request);
		Fachada fachada = Fachada.getInstancia();

		FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoAguaSituacao");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.ligacaoEsgotoSituacao");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("cliente");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.rota");
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.rota.faturamentoGrupo");
		
		filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel");

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID,
						idImovel));

		filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
						ClienteRelacaoTipo.USUARIO));

		filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));

		Collection colecaoClienteImovel = fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

		if(colecaoClienteImovel == null || colecaoClienteImovel.isEmpty()){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.matricula.imovel.inexistente"));
		}else{

			ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

			Short diaVencimento = clienteImovel.getImovel().getDiaVencimento();

			adicionarValorPrimitivoAoBody("dataVencimentoAtual", (diaVencimento == null || diaVencimento == 0) ? "" : "" + diaVencimento);
			Imovel imovel = clienteImovel.getImovel();
			Cliente cliente = clienteImovel.getCliente();
			Integer novoDiaVencimento = clienteImovel.getImovel().getRota().getFaturamentoGrupo().getDiaVencimento().intValue();

			adicionarValorPrimitivoAoBody("dataVencimentoGrupo", (novoDiaVencimento == null || novoDiaVencimento == 0) ? "" : ""
							+ novoDiaVencimento);

			FiltroVencimentoAlternativo filtroVencimentoAlternativo = new FiltroVencimentoAlternativo(
							FiltroVencimentoAlternativo.DATA_IMPLANTACAO);

			filtroVencimentoAlternativo.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.IMOVEL_ID, imovel.getId()));

			filtroVencimentoAlternativo
							.adicionarParametro(new ParametroSimples(FiltroVencimentoAlternativo.CLIENTE_ID, cliente.getId()));

			filtroVencimentoAlternativo.adicionarParametro(new ParametroNulo(FiltroVencimentoAlternativo.DATA_EXCLUSAO));

			Collection vencimentosAlternativos = fachada.pesquisar(filtroVencimentoAlternativo, VencimentoAlternativo.class.getName());

			if(vencimentosAlternativos != null && !vencimentosAlternativos.isEmpty()){

				VencimentoAlternativo vencimentoAlternativo = (VencimentoAlternativo) Util.retonarObjetoDeColecao(vencimentosAlternativos);

				Date dataVencimento = vencimentoAlternativo.getDataImplantacao();

				SimpleDateFormat dataFormato = new SimpleDateFormat("dd/MM/yyyy");
				String dataVencimentoString = null;
				if(dataVencimento != null){
					dataVencimentoString = dataFormato.format(dataVencimento);
				}

				adicionarValorPrimitivoAoBody("dataAlteracaovencimento", dataVencimentoString == null ? "" : ""
								+ dataVencimentoString);

			}

				montaColecaoDiasVencimentoAlternativo(imovel);

		}
	
		
		
	}

	private void montaColecaoDiasVencimentoAlternativo(Imovel imovel) throws JSONException{

		Collection<SetorComercialVencimento> colecaoSetorComercialVencimentos = Fachada.getInstancia()
						.pesquisarSetorComercialVencimentoPorLocalidadeSetorComercial(imovel.getLocalidade().getId(),
										imovel.getSetorComercial().getId(), ConstantesSistema.INDICADOR_USO_ATIVO);
		Collection<String> dias = new ArrayList<String>();

		if(colecaoSetorComercialVencimentos != null && !colecaoSetorComercialVencimentos.isEmpty()){

			for(SetorComercialVencimento setorComercialVencimento : colecaoSetorComercialVencimentos){
				dias.add(setorComercialVencimento.getDiaVencimento().toString());
			}

		}
		adicionarListaAoBody("listaDiasVencimentoAlternativo", dias);
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
