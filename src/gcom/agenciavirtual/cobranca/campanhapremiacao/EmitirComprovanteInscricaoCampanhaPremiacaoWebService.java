
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualBinarioWebservice;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaCadastro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.campanhapremiacao.RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna o comprovante de inscricao da campanha de premiacao
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idCampanhaCadastro [OBRIGATORIO]</li>
 * <li>icEnvioComprovanteEmail [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/emitirComprovanteInscricaoCampanhaPremiacaoWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class EmitirComprovanteInscricaoCampanhaPremiacaoWebService
				extends AbstractAgenciaVirtualBinarioWebservice {

	@Override
	protected void processarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		Fachada fachada = Fachada.getInstancia();

		Integer idCampanhaCadastro = recuperarParametroInteiroObrigatorio("idCampanhaCadasto", "CampanhaCadastro", true, request);
		String icEnvioComprovanteEmail = recuperarParametroStringObrigatorio("icEnvioComprovanteEmail", "IndicadorEnvioComprovanteEmail",
						true, request);

		RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1 relatorioComprovanteInscricaoCampanhaPremiacao = new RelatorioComprovanteInscricaoCampanhaPremiacaoModelo1(
						null);

		CampanhaCadastro campanhaCadastro = null;

		FiltroCampanhaCadastro filtroCampanhaCadastro = new FiltroCampanhaCadastro();
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("campanha");
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("orgaoExpedidorRG");
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
		filtroCampanhaCadastro.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacao");

		filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.ID, idCampanhaCadastro));
		filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.INDICADOR_COMPROVANTE_BLOQUEADO, ConstantesSistema.NAO));

		Collection<CampanhaCadastro> colecaoCampanhaCadastros = fachada.pesquisar(filtroCampanhaCadastro,
						CampanhaCadastro.class.getName());
		
		if(!Util.isVazioOrNulo(colecaoCampanhaCadastros)){

			campanhaCadastro = (CampanhaCadastro) Util.retonarObjetoDeColecao(colecaoCampanhaCadastros);
		
			if(!campanhaCadastro.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.SIM)){

				relatorioComprovanteInscricaoCampanhaPremiacao.addParametro("indicadorEnvioComprovanteEmail", icEnvioComprovanteEmail);

				relatorioComprovanteInscricaoCampanhaPremiacao.addParametro("campanhaCadastro", campanhaCadastro);

				relatorioComprovanteInscricaoCampanhaPremiacao.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);

				try{
					processarExibicaoRelatorio(relatorioComprovanteInscricaoCampanhaPremiacao, TarefaRelatorio.TIPO_PDF, request, response,
									mapping);
				}catch(ActionServletException e){
					informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
									"atencao.pesquisa.nenhumresultado", campanhaCadastro.getDsEmail()));
				}
			}
		}
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
