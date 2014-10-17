
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoSituacaoMotivo;
import gcom.faturamento.FaturamentoSituacaoTipo;
import gcom.faturamento.FiltroFaturamentoSituacaoMotivo;
import gcom.faturamento.FiltroFaturamentoSituacaoTipo;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [XYZ] Gerar Relatório Situação Especial de Faturamento
 * 
 * @author Hebert Falcão
 * @date 16/03/2014
 */
public class ExibirGerarRelatorioSituacaoEspecialFaturamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioSituacaoEspecialFaturamento");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		GerarRelatorioSituacaoEspecialFaturamentoActionForm relatorioForm = (GerarRelatorioSituacaoEspecialFaturamentoActionForm) actionForm;

		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		if(!Util.isVazioOuBranco(objetoConsulta)){
			if(objetoConsulta.trim().equals("1")){
				// Localidade
				this.pesquisarLocalidade(relatorioForm, objetoConsulta);
			}else if(objetoConsulta.trim().equals("2")){
				// Setor Comercial
				this.pesquisarSetorComercial(relatorioForm, objetoConsulta);
			}else if(objetoConsulta.trim().equals("3")){
				// Quadra
				this.pesquisarQuadra(relatorioForm, objetoConsulta, httpServletRequest);
			}else if(objetoConsulta.trim().equals("4")){
				// Rota
				this.pesquisarRota(relatorioForm, objetoConsulta);
			}
		}

		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo(
						FiltroFaturamentoSituacaoTipo.DESCRICAO);
		Collection<FaturamentoSituacaoTipo> collectionFaturamentoSituacaoTipo = fachada.pesquisar(filtroFaturamentoSituacaoTipo,
						FaturamentoSituacaoTipo.class.getName());
		httpServletRequest.setAttribute("colecaoFaturamentoSituacaoTipo", collectionFaturamentoSituacaoTipo);

		FiltroFaturamentoSituacaoMotivo filtroFaturamentoSituacaoMotivo = new FiltroFaturamentoSituacaoMotivo(
						FiltroFaturamentoSituacaoMotivo.DESCRICAO);
		Collection<FaturamentoSituacaoMotivo> collectionFaturamentoSituacaoMotivo = fachada.pesquisar(filtroFaturamentoSituacaoMotivo,
						FaturamentoSituacaoMotivo.class.getName());
		httpServletRequest.setAttribute("colecaoFaturamentoSituacaoMotivo", collectionFaturamentoSituacaoMotivo);

		// Localidade
		String idLocalidade = relatorioForm.getIdLocalidade();
		String descricaoLocalidade = relatorioForm.getDescricaoLocalidade();

		if(!Util.isVazioOuBranco(idLocalidade) && !Util.isVazioOuBranco(descricaoLocalidade)){
			httpServletRequest.setAttribute("localidadeEncontrada", "true");
		}

		// Setor Comercial
		String codigoSetorComercial = relatorioForm.getCodigoSetorComercial();
		String descricaoSetorComercial = relatorioForm.getDescricaoSetorComercial();

		if(!Util.isVazioOuBranco(codigoSetorComercial) && !Util.isVazioOuBranco(descricaoSetorComercial)){
			httpServletRequest.setAttribute("setorComercialEncontrado", "true");
		}

		// Rota
		String codigoRotaStr = relatorioForm.getCodigoRota();
		String descricaoRota = relatorioForm.getDescricaoRota();

		if(!Util.isVazioOuBranco(codigoRotaStr) && !Util.isVazioOuBranco(descricaoRota)){
			httpServletRequest.setAttribute("rotaEncontrada", "true");
		}

		return retorno;
	}

	private void pesquisarLocalidade(GerarRelatorioSituacaoEspecialFaturamentoActionForm form, String objetoConsulta){

		String idLocalidadeStr = form.getIdLocalidade();

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeStr));

		Collection<Localidade> colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(!Util.isVazioOrNulo(colecaoLocalidade)){
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			Integer idLocalidade = localidade.getId();
			idLocalidadeStr = Integer.toString(idLocalidade);

			String descricaoLocalidade = localidade.getDescricao();

			form.setIdLocalidade(idLocalidadeStr);
			form.setDescricaoLocalidade(descricaoLocalidade);

		}else{
			form.setIdLocalidade(null);
			form.setDescricaoLocalidade("Localidade inexistente");
		}
	}

	private void pesquisarSetorComercial(GerarRelatorioSituacaoEspecialFaturamentoActionForm form, String objetoConsulta){

		String idLocalidadeStr = form.getIdLocalidade();
		String codigoSetorComercialStr = form.getCodigoSetorComercial();

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, idLocalidadeStr));
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetorComercialStr));

		Collection<SetorComercial> colecaoSetorComercial = this.getFachada()
						.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(!Util.isVazioOrNulo(colecaoSetorComercial)){
			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			Integer codigoSetorComercial = setorComercial.getCodigo();
			codigoSetorComercialStr = Integer.toString(codigoSetorComercial);

			String descricaoSetorComercial = setorComercial.getDescricao();

			form.setCodigoSetorComercial(codigoSetorComercialStr);
			form.setDescricaoSetorComercial(descricaoSetorComercial);
		}else{
			form.setCodigoSetorComercial(null);
			form.setDescricaoSetorComercial("Setor Comercial inexistente");
		}
	}

	public void pesquisarQuadra(GerarRelatorioSituacaoEspecialFaturamentoActionForm form, String objetoConsulta,
					HttpServletRequest httpServletRequest){

		String idLocalidadeStr = form.getIdLocalidade();
		String codigoSetorComercialStr = form.getCodigoSetorComercial();
		String numeroQuadraStr = form.getNumeroQuadra();

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		if(!Util.isVazioOuBranco(idLocalidadeStr)){
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_LOCALIDADE, idLocalidadeStr));
		}

		if(!Util.isVazioOuBranco(codigoSetorComercialStr)){
			filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, codigoSetorComercialStr));
		}

		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, numeroQuadraStr));

		Collection<Quadra> colecaoQuadra = this.getFachada().pesquisar(filtroQuadra, Quadra.class.getName());

		if(!Util.isVazioOuBranco(colecaoQuadra)){
			Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

			Integer numeroQuadra = quadra.getNumeroQuadra();
			numeroQuadraStr = Integer.toString(numeroQuadra);

			form.setNumeroQuadra(numeroQuadraStr);
			httpServletRequest.setAttribute("idQuadraNaoEncontrada", "true");
		}else{
			form.setNumeroQuadra("");

			httpServletRequest.setAttribute("codigoQuadraNaoEncontrada", "true");
			httpServletRequest.setAttribute("idQuadraNaoEncontrada", "exception");
			httpServletRequest.setAttribute("msgQuadra", "QUADRA INEXISTENTE");
		}
	}

	public void pesquisarRota(GerarRelatorioSituacaoEspecialFaturamentoActionForm form, String objetoConsulta){

		String idLocalidadeStr = form.getIdLocalidade();
		String codigoSetorComercialStr = form.getCodigoSetorComercial();
		String codigoRotaStr = form.getCodigoRota();

		FiltroRota filtroRota = new FiltroRota();
		filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.SETOR_COMERCIAL);
		filtroRota.adicionarCaminhoParaCarregamentoEntidade(FiltroRota.LOCALIDADE);

		if(!Util.isVazioOuBranco(idLocalidadeStr)){
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.LOCALIDADE_ID, idLocalidadeStr));
		}

		if(!Util.isVazioOuBranco(codigoSetorComercialStr)){
			filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_CODIGO, codigoSetorComercialStr));
		}

		filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.CODIGO_ROTA, codigoRotaStr));

		Collection<Rota> colecaoRota = this.getFachada().pesquisar(filtroRota, Rota.class.getName());

		if(!Util.isVazioOuBranco(colecaoRota)){
			Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoRota);

			Short codigoRota = rota.getCodigo();
			codigoRotaStr = Short.toString(codigoRota);

			String descricaoRota = rota.getDescricao();

			form.setCodigoRota(codigoRotaStr);
			form.setDescricaoRota(descricaoRota);
		}else{
			form.setCodigoRota(null);
			form.setDescricaoRota("Rota inexistente");
		}
	}

}
