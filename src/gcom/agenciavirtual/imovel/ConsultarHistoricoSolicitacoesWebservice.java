/**
 * 
 */

package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.fachada.Fachada;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * @author mgrb
 */
public class ConsultarHistoricoSolicitacoesWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice#preencherJSONBody(org.apache.struts
	 * .action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento(FiltroRegistroAtendimento.ID);
		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.IMOVEL_ID,
						recuperarParametroInteiroObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request)));
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO);
		Collection registros = Fachada.getInstancia().pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class.getName());

		if(Util.isVazioOrNulo(registros)){
			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.nenhumresultado"));
		}else{
			List<SolicatacaoHistoricoJSON> historico = new ArrayList<SolicatacaoHistoricoJSON>();
			for(Object object : registros){
				RegistroAtendimento ra = (RegistroAtendimento) object;
				historico.add(new SolicatacaoHistoricoJSON(ra));
			}
			Comparator<? super SolicatacaoHistoricoJSON> comparador = new Comparator<SolicatacaoHistoricoJSON>() {

				public int compare(SolicatacaoHistoricoJSON o1, SolicatacaoHistoricoJSON o2){

					return o1.getDataAtendimento().compareTo(o2.getDataAtendimento());
				}
			};
			comparador = Collections.reverseOrder(comparador);
			Collections.sort(historico, comparador);
			adicionarListaAoBody("historico", historico);
		}
	}

}
