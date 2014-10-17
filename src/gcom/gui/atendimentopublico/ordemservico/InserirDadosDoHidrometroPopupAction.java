
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0711] Filtro para Emissão de Ordens Seletivas - Dados do Hidrômetro
 * 
 * @author Hebert Falcão
 * @date 13/05/2011
 */
public class InserirDadosDoHidrometroPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirDadosDoHidrometroPopup");

		DadosDoHidrometroPopupActionForm dadosDoHidrometroPopupActionForm = (DadosDoHidrometroPopupActionForm) actionForm;

		// Obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivasActionForm = (ImovelEmissaoOrdensSeletivasActionForm) sessao
						.getAttribute("ImovelEmissaoOrdensSeletivasActionForm");

		Collection<DadosDoHidrometroHelper> colecaoDadosDoHidrometro = imovelEmissaoOrdensSeletivasActionForm.getColecaoDadosDoHidrometro();

		if(colecaoDadosDoHidrometro == null){
			colecaoDadosDoHidrometro = new ArrayList<DadosDoHidrometroHelper>();
		}

		boolean peloMenosUmParametroInformado = false;

		String numeroNaoInformadoStr = Integer.toString(ConstantesSistema.NUMERO_NAO_INFORMADO);

		DadosDoHidrometroHelper dadosDoHidrometroHelper = new DadosDoHidrometroHelper();

		// Id
		Date dataAtual = new Date();
		long id = dataAtual.getTime();
		dadosDoHidrometroHelper.setId(id);

		// Diâmetro
		Integer idHidrometroDiametro = null;
		String descricaoHidrometroDiametro = "";
		String idHidrometroDiametroStr = dadosDoHidrometroPopupActionForm.getIdHidrometroDiametro();

		if(!Util.isVazioOuBranco(idHidrometroDiametroStr) && !numeroNaoInformadoStr.equals(idHidrometroDiametroStr)){
			peloMenosUmParametroInformado = true;

			idHidrometroDiametro = Util.converterStringParaInteger(idHidrometroDiametroStr);
			descricaoHidrometroDiametro = dadosDoHidrometroPopupActionForm.getDescricaoHidrometroDiametro();
		}

		dadosDoHidrometroHelper.setIdHidrometroDiametro(idHidrometroDiametro);
		dadosDoHidrometroHelper.setDescricaoHidrometroDiametro(descricaoHidrometroDiametro);

		// Capacidade
		Integer idHidrometroCapacidade = null;
		String descricaoHidrometroCapacidade = "";

		String idHidrometroCapacidadeStr = dadosDoHidrometroPopupActionForm.getIdHidrometroCapacidade();

		if(!Util.isVazioOuBranco(idHidrometroCapacidadeStr) && !numeroNaoInformadoStr.equals(idHidrometroCapacidadeStr)){
			peloMenosUmParametroInformado = true;

			idHidrometroCapacidade = Util.converterStringParaInteger(idHidrometroCapacidadeStr);
			descricaoHidrometroCapacidade = dadosDoHidrometroPopupActionForm.getDescricaoHidrometroCapacidade();
		}

		dadosDoHidrometroHelper.setIdHidrometroCapacidade(idHidrometroCapacidade);
		dadosDoHidrometroHelper.setDescricaoHidrometroCapacidade(descricaoHidrometroCapacidade);

		// Período de instalação
		String intervaloInstalacaoInicialStr = dadosDoHidrometroPopupActionForm.getIntervaloInstalacaoInicial();
		String intervaloInstalacaoFinalStr = dadosDoHidrometroPopupActionForm.getIntervaloInstalacaoFinal();

		if(!Util.isVazioOuBranco(intervaloInstalacaoInicialStr) || !Util.isVazioOuBranco(intervaloInstalacaoFinalStr)){
			peloMenosUmParametroInformado = true;
		}

		dadosDoHidrometroHelper.setIntervaloInstalacaoInicial(intervaloInstalacaoInicialStr);
		dadosDoHidrometroHelper.setIntervaloInstalacaoFinal(intervaloInstalacaoFinalStr);

		// Leitura Acumulada
		Integer numeroLeituraAcumulada = null;
		String numeroLeituraAcumuladaStr = dadosDoHidrometroPopupActionForm.getNumeroLeituraAcumulada();

		if(!Util.isVazioOuBranco(numeroLeituraAcumuladaStr)){
			peloMenosUmParametroInformado = true;

			numeroLeituraAcumulada = Util.converterStringParaInteger(numeroLeituraAcumuladaStr);
		}

		dadosDoHidrometroHelper.setNumeroLeituraAcumulada(numeroLeituraAcumulada);

		// Erro caso nenhum campo seja informado
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.informe_pelo_menos_um_dado_do_hidrometro");
		}

		if(!Util.isVazioOrNulo(colecaoDadosDoHidrometro)){
			for(DadosDoHidrometroHelper obj : colecaoDadosDoHidrometro){
				Integer idHidrometroDiametroAux = obj.getIdHidrometroDiametro();
				Integer idHidrometroCapacidadeAux = obj.getIdHidrometroCapacidade();
				String intervaloInstalacaoInicialAux = obj.getIntervaloInstalacaoInicial();
				String intervaloInstalacaoFinalAux = obj.getIntervaloInstalacaoFinal();
				Integer numeroLeituraAcumuladaAux = obj.getNumeroLeituraAcumulada();

				if(idHidrometroDiametroAux == idHidrometroDiametro
								&& idHidrometroCapacidadeAux == idHidrometroCapacidade
								&& numeroLeituraAcumuladaAux == numeroLeituraAcumulada
								&& (intervaloInstalacaoInicialAux != null && intervaloInstalacaoInicialAux
												.equals(intervaloInstalacaoInicialStr))
								&& (intervaloInstalacaoFinalAux != null && intervaloInstalacaoFinalAux.equals(intervaloInstalacaoFinalStr))
								&& (intervaloInstalacaoInicialStr != null && intervaloInstalacaoInicialStr
												.equals(intervaloInstalacaoInicialAux))
								&& (intervaloInstalacaoFinalStr != null && intervaloInstalacaoFinalStr.equals(intervaloInstalacaoFinalAux))){
					throw new ActionServletException("atencao.dados.existente.dados_do_hidrometro");
				}
			}
		}

		colecaoDadosDoHidrometro.add(dadosDoHidrometroHelper);

		imovelEmissaoOrdensSeletivasActionForm.setColecaoDadosDoHidrometro(colecaoDadosDoHidrometro);

		sessao.setAttribute("closePage", "S");

		return retorno;
	}
}
