
package gcom.gui.micromedicao.hidrometro;

import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.bean.OrdemServicoManutencaoHidrometroHelper;
import gcom.micromedicao.hidrometro.*;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ConsultarHistoricoInstalacaoHidrometroPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta a ação de retorno
		ActionForward retorno = null;

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String codigoImovel = null;
		if(httpServletRequest.getParameter("codigoImovel") != null){
			codigoImovel = httpServletRequest.getParameter("codigoImovel");
		}

		String codigoHidrometro = null;
		if(httpServletRequest.getParameter("codigoHidrometro") != null){
			codigoHidrometro = httpServletRequest.getParameter("codigoHidrometro");
		}

		// Verifica se o usuário digitou os dois códigos
		if(Util.isVazioOuBranco(codigoImovel) && Util.isVazioOuBranco(codigoHidrometro)){
			throw new ActionServletException("atencao.verificar.informado.imovel_hidrometro.ambos");
		}

		// Verifica se o usuário não digitou código
		if(!Util.isVazioOuBranco(codigoImovel) && !Util.isVazioOuBranco(codigoHidrometro)){
			throw new ActionServletException("atencao.verificar.informado.imovel_hidrometro");
		}

		// Obtém a facahda
		Fachada fachada = Fachada.getInstancia();

		if(!Util.isVazioOuBranco(codigoImovel)){
			codigoImovel = codigoImovel.trim();

			// Seta o retorno para a página que vai detalhar o imovel
			retorno = actionMapping.findForward("consultarImovelPopup");

			// Pesquisa o imovel na base
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, codigoImovel, FiltroParametro.CONECTOR_OR));
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.LIGACAO_AGUA_ID, codigoImovel));

			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL_MUNICIPIO_UF);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CEP);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TIPO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TITULO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ENDERECO_REFERENCIA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.UNIDADE_FEDERACAO);

			Collection<Imovel> imovelPesquisado = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			// obtem o imovel pesquisado
			if(!Util.isVazioOrNulo(imovelPesquisado)){
				Imovel imovel = imovelPesquisado.iterator().next();

				// Manda o Imovel pelo request
				httpServletRequest.setAttribute("imovel", imovel);

				// Faz uma busca do historico de instalacao do hidrometro para mostrar na pagina
				FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();

				Integer idImovel = imovel.getId();

				LigacaoAgua ligacaoAgua = imovel.getLigacaoAgua();
				if(ligacaoAgua != null){
					Integer idLigacaoAgua = ligacaoAgua.getId();

					filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroHidrometroInstalacaoHistorico.IMOVEL_ID, idImovel, FiltroParametro.CONECTOR_OR));
					filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroHidrometroInstalacaoHistorico.LIGACAO_AGUA_ID, idLigacaoAgua));
				}else{
					filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
									FiltroHidrometroInstalacaoHistorico.IMOVEL_ID, idImovel));
				}

				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.MEDICAO_TIPO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO_LOCAL_INSTALACAO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO_PROTECAO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroInstalacaoHistorico.HIDROMETRO);

				Collection<HidrometroInstalacaoHistorico> hidrometrosInstalacaoHistorico = fachada.pesquisar(
								filtroHidrometroInstalacaoHistorico, HidrometroInstalacaoHistorico.class.getName());

				// Manda a colecao do historico de instalacao do hidrometro para a pagina
				httpServletRequest.setAttribute("hidrometrosInstalacaoHistorico", hidrometrosInstalacaoHistorico);

				sessao.setAttribute("codigoImovel", codigoImovel);
				sessao.setAttribute("codigoHidrometro", "");
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Matrícula");
			}

		}

		if(!Util.isVazioOuBranco(codigoHidrometro)){
			codigoHidrometro = codigoHidrometro.trim();
			codigoHidrometro = codigoHidrometro.toUpperCase();

			// Seta o retorno para a página que vai detalhar o hidrometro
			retorno = actionMapping.findForward("consultarHidrometroPopup");

			// Pesquisa o hidrometro na base
			FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_MARCA);
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_SITUACAO);
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_MOTIVO_BAIXA);
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_CLASSE_METROLOGICA);
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_DIAMETRO);
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_CAPACIDADE);
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_TIPO);
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.EMPRESA_ULTIMA_AFERICAO);

			filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.NUMERO_HIDROMETRO, codigoHidrometro));

			Collection<Hidrometro> hidrometroPesquisado = fachada.pesquisar(filtroHidrometro, Hidrometro.class.getName());

			// obtem o hidrometro pesquisado
			if(!Util.isVazioOuBranco(hidrometroPesquisado)){
				Hidrometro hidrometro = hidrometroPesquisado.iterator().next();

				// Manda o Hidrometro pelo request
				httpServletRequest.setAttribute("hidrometro", hidrometro);

				Integer idHidrometro = hidrometro.getId();

				// Faz uma busca do historico de instalacao do hidrometro para mostrar na pagina
				FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();
				filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroHidrometroInstalacaoHistorico.HIDROMETRO_ID, idHidrometro));
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(filtroHidrometroInstalacaoHistorico.MEDICAO_TIPO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(filtroHidrometroInstalacaoHistorico.HIDROMETRO_LOCAL_INSTALACAO);
				filtroHidrometroInstalacaoHistorico
								.adicionarCaminhoParaCarregamentoEntidade(filtroHidrometroInstalacaoHistorico.HIDROMETRO_PROTECAO);

				Collection<HidrometroInstalacaoHistorico> hidrometrosInstalacaoHistorico = fachada.pesquisar(
								filtroHidrometroInstalacaoHistorico, HidrometroInstalacaoHistorico.class.getName());

				// Dados das Ordens de Servico
				Collection<OrdemServicoManutencaoHidrometroHelper> colecaoOrdemServicoManutencaoHidrometroHelper = fachada
								.pesquisarDadosOrdensServicoManutencaoHidrometro(hidrometro.getId());

				if(!Util.isVazioOrNulo(colecaoOrdemServicoManutencaoHidrometroHelper)){

					sessao.setAttribute("colecaoOrdemServicoManutencaoHidrometroHelper", colecaoOrdemServicoManutencaoHidrometroHelper);
				}else{

					sessao.removeAttribute("colecaoOrdemServicoManutencaoHidrometroHelper");
				}

				// Histórico de Aferições relacionadas ao hidrômetro
				FiltroHidrometroHistoricoAfericao filtroHidrometroHistoricoAfericao = new FiltroHidrometroHistoricoAfericao();
				filtroHidrometroHistoricoAfericao.adicionarParametro(new ParametroSimples(FiltroHidrometroHistoricoAfericao.HIDROMETRO_ID,
								hidrometro.getId()));
				filtroHidrometroHistoricoAfericao
								.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroHistoricoAfericao.HIDROMETRO_CONDICAO);
				filtroHidrometroHistoricoAfericao.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometroHistoricoAfericao.FUNCIONARIO);

				Collection<HidrometroHistoricoAfericao> colecaoHidrometroHistoricoAfericao = fachada.pesquisar(
								filtroHidrometroHistoricoAfericao, HidrometroHistoricoAfericao.class.getName());

				if(!Util.isVazioOrNulo(colecaoHidrometroHistoricoAfericao)){

					sessao.setAttribute("colecaoHidrometroHistoricoAfericao", colecaoHidrometroHistoricoAfericao);
				}else{

					sessao.removeAttribute("colecaoHidrometroHistoricoAfericao");
				}

				// Manda a colecao do historico de instalacao do hidrometro para a pagina
				httpServletRequest.setAttribute("hidrometrosInstalacaoHistorico", hidrometrosInstalacaoHistorico);

				sessao.setAttribute("codigoHidrometro", codigoHidrometro);
				sessao.setAttribute("codigoImovel", "");
			}else{
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Hidrômetro");
			}
		}

		return retorno;
	}

}
