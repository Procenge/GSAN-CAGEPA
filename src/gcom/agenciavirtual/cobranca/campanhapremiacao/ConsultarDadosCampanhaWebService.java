
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cobranca.campanhapremiacao.Campanha;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.FiltroCampanha;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaCadastro;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.IoUtil;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.hibernate.Hibernate;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Retorna o endereco do Imovel
 * <b>Este servi�o � P�BLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarDadosCampanhaWebService.do
 * 
 * @author Hiroshi Goncalves
 * @author Felipe Rosacruz
 */
public class ConsultarDadosCampanhaWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();
		
		// O sistema obt�m os dados da campanha de premia��o mais recente da empresa
		FiltroCampanha filtroCampanha = new FiltroCampanha(FiltroCampanha.ULTIMA_ALTERACAO + " desc");

		Collection colecaoCampanha = fachada.pesquisar(filtroCampanha, Campanha.class.getName(), Campanha.class.getSimpleName());
		
		if(!colecaoCampanha.isEmpty()){
			Campanha campanha = (Campanha) Util.retonarObjetoDeColecao(colecaoCampanha);
			
			adicionarValorPrimitivoAoBody("idCampanha", campanha.getId());
			adicionarValorPrimitivoAoBody("nomeCampanha", campanha.getDsTituloCampanha());
			adicionarValorPrimitivoAoBody("regulamentoCamanha",
							IoUtil.lerConteudoCampoBlobTipoTxt(Hibernate.createBlob(campanha.getRegulamentoCampanha())).toString());

			// Total de Inscri��es Registradas, Total de Inscri��es Bloqueadas
			FiltroCampanhaCadastro filtroCampanhaCadastro = new FiltroCampanhaCadastro();
			filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.CAMPANHA_ID, campanha.getId()));

			int qtInscricoesRegistradas = fachada.totalRegistrosPesquisa(filtroCampanhaCadastro, CampanhaCadastro.class.getName());
			adicionarValorPrimitivoAoBody("inscricoesRegistradas", qtInscricoesRegistradas);

			// Total de Inscri��es Bloqueadas
			filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.INDICADOR_COMPROVANTE_BLOQUEADO,
							ConstantesSistema.SIM));
			int qtInscricoesBloqueadas = fachada.totalRegistrosPesquisa(filtroCampanhaCadastro, CampanhaCadastro.class.getName());
			adicionarValorPrimitivoAoBody("inscricoesBloqueadas", qtInscricoesBloqueadas);

		}else{

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}
		
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
