package gcom.gui.relatorio.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.atendimentopublico.RelatorioParecerEncerramentoOS;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioParecerEncerramentoOSAction
				extends ExibidorProcessamentoTarefaRelatorio {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm form, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse)
					throws Exception{

		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		RelatorioParecerEncerramentoOSHelper relatorioParecerEncerramentoOSHelper = (RelatorioParecerEncerramentoOSHelper) sessao
						.getAttribute("relatorioParecerEncerramentoOSHelper");

		RelatorioParecerEncerramentoOS relatorioParecerEncerramentoOS = new RelatorioParecerEncerramentoOS(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		// Incluindo dados do cliente e imóvel no relatório de parecer [OC1181106]
		FiltroOrdemServico filtroOS = new FiltroOrdemServico();

		filtroOS.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, relatorioParecerEncerramentoOSHelper.getNumeroOS()));
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.REGISTRO_ATENDIMENTO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_CLIENTE_IMOVEIS);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_CEP);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_CEP_CEP);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_BAIRRO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_BAIRRO_BAIRRO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_BAIRRO_MUNICIPIO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_BAIRRO_LOGRADOURO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_BAIRRO_LOGRADOURO_TIPO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_BAIRRO_LOGRADOURO_TITULO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.IMOVEL_LOGRADOURO_BAIRRO_MUNICIPIO_UNIDADE_FEDERACAO);

		Collection colecaoOS = (Collection) fachada.pesquisar(filtroOS, OrdemServico.class.getName());
		OrdemServico osBase = (OrdemServico) Util.retonarObjetoDeColecao(colecaoOS);

		// Situação
		String descricaoSituacao = "";
		ObterDescricaoSituacaoOSHelper situacaoOS = fachada.obterDescricaoSituacaoOS(osBase.getId());

		if(situacaoOS != null){
			descricaoSituacao = situacaoOS.getDescricaoSituacao();

			if(descricaoSituacao != null){
				descricaoSituacao = descricaoSituacao.toUpperCase();
			}
		}

		relatorioParecerEncerramentoOSHelper.setSituacaoOS(descricaoSituacao);

		if(osBase.getImovel() != null){
			relatorioParecerEncerramentoOSHelper.setMatriculaImovel(osBase.getImovel().getId().toString());
			relatorioParecerEncerramentoOSHelper.setEnderecoImovel(osBase.getImovel().getEnderecoFormatado());

			FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, osBase.getImovel().getId()));
			filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.CLIENTE_RELACAO_TIPO,
							ConstantesSistema.CLIENTE_IMOVEL_TIPO_USUARIO));
			filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_RG_ORGAO_EXPEDIDOR);
			filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE_UNIDADE_FEDERACAO);

			Collection colecaoClienteImovel = (Collection) fachada.pesquisar(filtroClienteImovel, ClienteImovel.class.getName());
			ClienteImovel clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);

			if(clienteImovel != null){
				relatorioParecerEncerramentoOSHelper.setMatriculaCliente(clienteImovel.getId().toString());
				relatorioParecerEncerramentoOSHelper.setNomeCliente(clienteImovel.getCliente().getNome());

				if(clienteImovel.getCliente().getRg() != null && !clienteImovel.getCliente().getRg().equals("")){
					relatorioParecerEncerramentoOSHelper.setRgCliente(clienteImovel.getCliente().getRg());
				}

				if(clienteImovel.getCliente().getRg() != null && !clienteImovel.getCliente().getRg().equals("")
								&& clienteImovel.getCliente().getOrgaoExpedidorRg() != null
								&& !clienteImovel.getCliente().getOrgaoExpedidorRg().equals("")){
					relatorioParecerEncerramentoOSHelper.setRgCliente(clienteImovel.getCliente().getRg() + " "
								+ clienteImovel.getCliente().getOrgaoExpedidorRg().getDescricaoAbreviada());
				}

				if(relatorioParecerEncerramentoOSHelper.getRgCliente() != null && clienteImovel.getCliente() != null
								&& clienteImovel.getCliente().getUnidadeFederacao() != null){
					relatorioParecerEncerramentoOSHelper.setRgCliente(relatorioParecerEncerramentoOSHelper.getRgCliente()
 + "/"
									+ clienteImovel.getCliente().getUnidadeFederacao().getSigla());
				}

				relatorioParecerEncerramentoOSHelper.setCpfCliente(clienteImovel.getCliente().getCpfFormatado());
			}

			if(osBase.getRegistroAtendimento() != null){
				relatorioParecerEncerramentoOSHelper.setNumeroRA(osBase.getRegistroAtendimento().getId().toString());
			}
		}

		// Fim de Incluindo dados do cliente e imóvel no relatório de parecer [OC1181106]

		String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

		relatorioParecerEncerramentoOS.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		relatorioParecerEncerramentoOS.addParametro("relatorioParecerEncerramentoOSHelper", relatorioParecerEncerramentoOSHelper);

		try{
			retorno = processarExibicaoRelatorio(relatorioParecerEncerramentoOS, tipoRelatorio, httpServletRequest, httpServletResponse,
							actionMapping);

		}catch(SistemaException ex){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "erro.sistema");

			// seta o mapeamento de retorno para a tela de erro de popup
			retorno = actionMapping.findForward("telaErroPopup");

		}catch(RelatorioVazioException ex1){
			// manda o erro para a página no request atual
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			// seta o mapeamento de retorno para a tela de atenção de popup
			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}

}
