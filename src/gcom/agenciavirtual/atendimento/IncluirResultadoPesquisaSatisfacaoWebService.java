
package gcom.agenciavirtual.atendimento;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.atendimentopublico.registroatendimento.FiltroPesquisaSatisfacao;
import gcom.atendimentopublico.registroatendimento.PesquisaSatisfacao;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.fachada.Fachada;
import gcom.util.FachadaException;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Insere no banco o resultado da pesquisa de inclusão caso não exista
 * uma pesquisa de inclusão registrado para o mesmo RA.
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>icBemAtendido [OBRIGATORIO]</li>
 * <li>comentarioBemAtendido</li>
 * <li>icSolicitacaoResolvida [OBRIGATORIO]</li>
 * <li>comentarioSolicitacaoResolvida</li>
 * <li>icBemAtendidoEquipeCampo</li>
 * <li>comentarioAtendimentoEquipeCampo</li>
 * <li>notaAtendimento [OBRIGATORIO]</li>
 * <li>comentarioGeral</li>
 * <li>idRegistroAtendimento [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/incluirResultadoPesquisaSatisfacaoWebService.do
 * 
 * @author Felipe Rosacruz
 */
public class IncluirResultadoPesquisaSatisfacaoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		try{
			Integer icBemAtendido = recuperarParametroInteiroObrigatorio("icBemAtendido", "Indicador Bem Atendido", true, request);
			String comentarioAtendimento = recuperarParametroString("comentarioBemAtendido", "Comentario Atendimento", false, true, request);
			Integer icSolicitacaoResolvida = recuperarParametroInteiroObrigatorio("icSolicitacaoResolvida",
							"Indicador Solicitação Resolvida", true, request);
			String comentarioSolicitacaoResolvida = recuperarParametroString("comentarioSolicitacaoResolvida",
							"Comentario Solicitação Resolvida", false, true, request);
			Integer icBemAtendidoEquipeCampo = recuperarParametroInteiro("icBemAtendidoEquipeCampo",
							"Indicador Bem Atendido pela Equipe de Campo", false, true, request);
			String comentarioAtendimentoEquipeCampo = recuperarParametroString("comentarioAtendimentoEquipeCampo",
							"Comentario Atendimento Equipe Campo", false, true, request);
			Integer notaAtendimento = recuperarParametroInteiroObrigatorio("notaAtendimento", "Nota Atendimento", false, request);
			String comentarioGeral = recuperarParametroString("comentarioGeral", "Comentario Geral", false, true, request);
			Integer idRegistroAtendimento = recuperarParametroInteiroObrigatorio("idRegistroAtendimento", "ID do RA", false, request);

			if(icBemAtendidoEquipeCampo != null && icBemAtendidoEquipeCampo != 1 && icBemAtendidoEquipeCampo != 2){
				icBemAtendidoEquipeCampo = null;
			}

			FiltroPesquisaSatisfacao filtroPesquisaSatisfacao = new FiltroPesquisaSatisfacao();
			filtroPesquisaSatisfacao.adicionarParametro(new ParametroSimples(FiltroPesquisaSatisfacao.REGISTRO_ATENDIMENTO_ID,
							idRegistroAtendimento));
			if(Fachada.getInstancia().pesquisar(filtroPesquisaSatisfacao, PesquisaSatisfacao.class.getName()).size() != 0){
				informarStatus(STATUS_TIPO_ERROR,
								MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.pesquisa.satisfacao.cadastrada.RA"));
			}else{

			PesquisaSatisfacao pesquisaSatisfacao = new PesquisaSatisfacao();
			pesquisaSatisfacao.setIndicadorBemAtendido(icBemAtendido.shortValue());
			pesquisaSatisfacao.setIndicadorSolicitacaoResolvida(icSolicitacaoResolvida.shortValue());
			if(icBemAtendidoEquipeCampo != null){
				pesquisaSatisfacao.setIndicadorBemAtendidoEquipeCampo(icBemAtendidoEquipeCampo.shortValue());
			}

			pesquisaSatisfacao.setComentarioAtendimento(comentarioAtendimento);
			pesquisaSatisfacao.setComentarioSolicitacaoResolvida(comentarioSolicitacaoResolvida);
			pesquisaSatisfacao.setComentarioAtendimentoEquipeCampo(comentarioAtendimentoEquipeCampo);

			pesquisaSatisfacao.setNotaAtendimento(notaAtendimento);
			pesquisaSatisfacao.setComentarioGeral(comentarioGeral);

			RegistroAtendimento registroAtendimento = new RegistroAtendimento();
			registroAtendimento.setId(idRegistroAtendimento);
			pesquisaSatisfacao.setRegistroAtendimento(registroAtendimento);

				pesquisaSatisfacao.setUltimaAlteracao(new Date());

			Fachada.getInstancia().inserir(pesquisaSatisfacao);

			informarStatus(STATUS_TIPO_SUCESSO, "Pesquisa de Satisfação registrada com Sucesso.");
			}
		}catch(FachadaException e){
			if(e.getMensagem() != null){
				if(e.getParametroMensagem() != null){
					informarStatus(STATUS_TIPO_ALERTA, e.getMensagem().replace("{0}", e.getParametroMensagem().get(0)));
				}else{
					informarStatus(STATUS_TIPO_ALERTA, e.getMensagem());
				}
			}
		}

	}

	
	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
