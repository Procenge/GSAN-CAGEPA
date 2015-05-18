
package gcom.agenciavirtual.atendimento;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.atendimentopublico.ordemservico.bean.ImovelEmissaoOrdensSeletivaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.GerenciadorExecucaoTarefaRelatorio;
import gcom.relatorio.atendimentopublico.RelatorioEmitirOrdemServicoSeletiva;
import gcom.relatorio.atendimentopublico.RelatorioEmitirOrdemServicoSeletivaSugestao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.parametrizacao.ordemservico.ParametroOrdemServico;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.comum.exception.NegocioException;

/**
 * Pesquisa os débitos de um imóvel
 * <b>Este serviço não é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO]</li>
 * <li>cpfcnpj [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/gerarOsSeletivaWebservice.do
 * 
 * @author Yara Souza
 */
public class GerarOsSeletivaWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		ImovelEmissaoOrdensSeletivaHelper imovelEmissaoOrdensSeletivaHelper = obterImovelEmissaoOrdensSeletivaHelper(request);

		try{
			Usuario usuarioLogado = Usuario.USUARIO_AGENCIA_VIRTUAL;
			Fachada fachada = Fachada.getInstancia();

			if(!imovelEmissaoOrdensSeletivaHelper.getSimulacao().equals("1")){


				// Fim da parte que vai mandar os parametros para o relatório
				int tipoRelatorio = TarefaRelatorio.TIPO_PDF;

				try{
					// PDF = 1 e TXT = 2
					String tipoArquivo = ParametroOrdemServico.P_COD_TIPO_ARQUIVO_EMISSAO_OS_SELETIVA.executar();

					if(tipoArquivo.equals(ConstantesSistema.TXT)){
						tipoRelatorio = TarefaRelatorio.TIPO_ZIP;
					}
				}catch(ControladorException ex){
					throw new ActionServletException(ex.getMessage(), ex);
				}

				Object[] retornoRelatorio = fachada.gerarRelatorioOrdemSeletiva(imovelEmissaoOrdensSeletivaHelper, usuarioLogado);

				RelatorioEmitirOrdemServicoSeletiva relatorioEmitirOrdemServicoSeletiva = (RelatorioEmitirOrdemServicoSeletiva) retornoRelatorio[0];

				try{

					GerenciadorExecucaoTarefaRelatorio.analisarExecucao(relatorioEmitirOrdemServicoSeletiva, tipoRelatorio);


				}catch(TarefaException e){
					throw new ActionServletException(e.getMessage(), "exibirFiltrarImovelEmissaoOrdensSeletivas.do?menu=sim", e, "");
				}catch(Exception e2){
					throw new ActionServletException(
									e2.getMessage(),
									"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
									e2);
				}

			}else{

				Object[] retornoRelatorio = fachada.gerarRelatorioOrdemSeletiva(imovelEmissaoOrdensSeletivaHelper, usuarioLogado);

				RelatorioEmitirOrdemServicoSeletivaSugestao relatorioEmitirOrdemServicoSeletivaSugestao = (RelatorioEmitirOrdemServicoSeletivaSugestao) retornoRelatorio[1];

				// Fim da parte que vai mandar os parametros para o relatório
				String tipoRelatorio = request.getParameter("tipoRelatorio");

				tipoRelatorio = String.valueOf(TarefaRelatorio.TIPO_PDF);

				relatorioEmitirOrdemServicoSeletivaSugestao.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
				try{

					GerenciadorExecucaoTarefaRelatorio.analisarExecucao(relatorioEmitirOrdemServicoSeletivaSugestao,
									Integer.parseInt(tipoRelatorio));


				}catch(TarefaException e){
					throw new ActionServletException(
									e.getMessage(),
									"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
									e);
				}catch(Exception e2){
					throw new ActionServletException(e2.getMessage(), e2);
				}
			}


		}catch(FachadaException e){

			e.getStackTrace();

			informarStatus(STATUS_TIPO_ALERTA, e.getMensagem());

		}

	}

	/**
	 * @param request
	 * @return
	 * @throws NegocioException
	 */
	private ImovelEmissaoOrdensSeletivaHelper obterImovelEmissaoOrdensSeletivaHelper(HttpServletRequest request) throws NegocioException{

		ImovelEmissaoOrdensSeletivaHelper imovelEmissaoOrdensSeletivaHelper = new ImovelEmissaoOrdensSeletivaHelper();

		String idTipoServico = this.recuperarParametroString("idTipoServico", "Tipo de Serviço", Boolean.TRUE, Boolean.TRUE, request);
		if(!Util.isVazioOuBranco(idTipoServico)){
			imovelEmissaoOrdensSeletivaHelper.setServicoTipo(idTipoServico);
		}

		String descricaoTipoServico = this.recuperarParametroString("descricaoTipoServico", "Descrição Tipo de Serviço", Boolean.TRUE,
						Boolean.TRUE, request);
		if(!Util.isVazioOuBranco(idTipoServico)){
			imovelEmissaoOrdensSeletivaHelper.setServicoTipoDescricao(descricaoTipoServico);
		}

		String indicadorSimulacao = this.recuperarParametroString("indicadorSimulacao", "Indicador Simulação", Boolean.TRUE, Boolean.TRUE,
						request);
		if(!Util.isVazioOuBranco(indicadorSimulacao)){
			imovelEmissaoOrdensSeletivaHelper.setSimulacao(indicadorSimulacao);
		}

		String empresa = this.recuperarParametroString("empresa", "Empresa", Boolean.TRUE, Boolean.TRUE, request);
		if(!Util.isVazioOuBranco(empresa)){
			imovelEmissaoOrdensSeletivaHelper.setFirma(empresa);
		}

		String titulo = this.recuperarParametroString("titulo", "Título", Boolean.TRUE, Boolean.TRUE, request);
		if(!Util.isVazioOuBranco(titulo)){
			imovelEmissaoOrdensSeletivaHelper.setTitulo(titulo);
		}
		
		Collection<Integer> idsImoveis = recuperarParametroColecaoInteiro("idsImoveis", "Imoveis", false, false, request);
		if(!Util.isVazioOrNulo(idsImoveis)){
			imovelEmissaoOrdensSeletivaHelper.setIdsImoveis(idsImoveis);
		}

		return imovelEmissaoOrdensSeletivaHelper;
	}


}
