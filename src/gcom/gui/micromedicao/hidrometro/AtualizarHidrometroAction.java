/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.micromedicao.hidrometro;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.*;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author compesa
 * @created 13 de Setembro de 2005
 */
public class AtualizarHidrometroAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		AtualizarHidrometroActionForm atualizarHidrometroActionForm = (AtualizarHidrometroActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Hidrometro hidrometro = (Hidrometro) sessao.getAttribute("hidrometro");

		// Obt�m a fachada
		Fachada fachada = Fachada.getInstancia();

		FiltroHidrometro filtroHidrometro = new FiltroHidrometro();

		filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.ID, hidrometro.getId()));

		Collection hidrometros = fachada.pesquisar(filtroHidrometro, Hidrometro.class.getName());

		if(hidrometros == null || hidrometros.isEmpty()){
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		// Cria o objeto classe metrol�gica e seta o id
		FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();
		filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.ID, Integer
						.valueOf(atualizarHidrometroActionForm.getIdHidrometroClasseMetrologica())));

		HidrometroClasseMetrologica hidrometroClasseMetrologica = (HidrometroClasseMetrologica) Util.retonarObjetoDeColecao(fachada
						.pesquisar(filtroHidrometroClasseMetrologica, HidrometroClasseMetrologica.class.getName()));

		if(hidrometroClasseMetrologica == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Classe Metrol�gica");
		}

		hidrometro.setHidrometroClasseMetrologica(hidrometroClasseMetrologica);

		// Formato da Numera��o do Hidr�metro
		String codigoFormatoNumeracaoStr = atualizarHidrometroActionForm.getCodigoFormatoNumeracao();
		Integer codigoFormatoNumeracao = Integer.valueOf(codigoFormatoNumeracaoStr);

		hidrometro.setCodigoFormatoNumeracao(codigoFormatoNumeracao);

		// Cria o objeto hidr�metro marca e seta o id
		FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();
		filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.ID, Integer
						.valueOf(atualizarHidrometroActionForm.getIdHidrometroMarca())));

		HidrometroMarca hidrometroMarca = (HidrometroMarca) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroHidrometroMarca,
						HidrometroMarca.class.getName()));

		if(hidrometroMarca == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Marca");
		}

		if((codigoFormatoNumeracao.equals(Hidrometro.FORMATO_NUMERACAO_4_X_6) || codigoFormatoNumeracao
						.equals(Hidrometro.FORMATO_NUMERACAO_5_X_5))
						&& !hidrometroMarca.getCodigoHidrometroMarca().equalsIgnoreCase(
										atualizarHidrometroActionForm.getNumeroHidrometro().substring(3, 4))){
			// throw new ActionServletException("atencao.marca_incompativel_numero_fixo");
		}

		hidrometro.setHidrometroMarca(hidrometroMarca);

		// Cria o objeto hidr�metro capacidade e seta o id
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID, Integer
						.valueOf(atualizarHidrometroActionForm.getIdHidrometroCapacidade())));

		HidrometroCapacidade hidrometroCapacidade = (HidrometroCapacidade) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroHidrometroCapacidade, HidrometroCapacidade.class.getName()));

		if(hidrometroCapacidade == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Capacidade");
		}

		if((codigoFormatoNumeracao.equals(Hidrometro.FORMATO_NUMERACAO_4_X_6) || codigoFormatoNumeracao
						.equals(Hidrometro.FORMATO_NUMERACAO_5_X_5))
						&& !hidrometroCapacidade.getCodigoHidrometroCapacidade().equalsIgnoreCase(
										atualizarHidrometroActionForm.getNumeroHidrometro().substring(0, 1))){
			throw new ActionServletException("atencao.capacidade_incompativel_numero_fixo");
		}

		hidrometro.setHidrometroCapacidade(hidrometroCapacidade);

		// Tipo de Instala��o da Turbina
		HidrometroTipoTurbina hidrometroTipoTurbina = null;

		if(codigoFormatoNumeracao.equals(Hidrometro.FORMATO_NUMERACAO_5_X_5)){
			FiltroHidrometroTipoTurbina filtroHidrometroTipoTurbina = new FiltroHidrometroTipoTurbina();
			filtroHidrometroTipoTurbina.adicionarParametro(new ParametroSimples(FiltroHidrometroTipoTurbina.ID, Integer
							.valueOf(atualizarHidrometroActionForm.getIdHidrometroTipoTurbina())));

			hidrometroTipoTurbina = (HidrometroTipoTurbina) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroHidrometroTipoTurbina,
							HidrometroTipoTurbina.class.getName()));

			if(hidrometroTipoTurbina == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de Instala��o da Turbina");
			}

			char codigoHidrometroTipoTurbina = hidrometroTipoTurbina.getCodigo();
			String codigoHidrometroTipoTurbinaStr = Character.toString(codigoHidrometroTipoTurbina);

			if(!codigoHidrometroTipoTurbinaStr.equalsIgnoreCase(atualizarHidrometroActionForm.getNumeroHidrometro().substring(4, 5))){
				throw new ActionServletException("atencao.tipo_turbina_incompativel_numero_fixo");
			}
		}

		hidrometro.setHidrometroTipoTurbina(hidrometroTipoTurbina);

		// Cria o objeto hidr�metro di�metro e seta o id
		FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
		filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.ID, Integer
						.valueOf(atualizarHidrometroActionForm.getIdHidrometroDiametro())));

		HidrometroDiametro hidrometroDiametro = (HidrometroDiametro) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroHidrometroDiametro, HidrometroDiametro.class.getName()));

		if(hidrometroDiametro == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Di�metro");
		}

		hidrometro.setHidrometroDiametro(hidrometroDiametro);

		// Cria o objeto hidr�metro tipo e seta o id
		FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();
		filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.ID, Integer.valueOf(atualizarHidrometroActionForm
						.getIdHidrometroTipo())));

		HidrometroTipo hidrometroTipo = (HidrometroTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroHidrometroTipo,
						HidrometroTipo.class.getName()));

		if(hidrometroTipo == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo");
		}

		hidrometro.setHidrometroTipo(hidrometroTipo);

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		Date dataAquisicao = null;
		try{
			dataAquisicao = formatoData.parse(atualizarHidrometroActionForm.getDataAquisicao());

		}catch(ParseException ex){
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}

		Integer numeroNotaFiscal = null;
		if(!Util.isVazioOuBranco(atualizarHidrometroActionForm.getNumeroNotaFiscal())){

			numeroNotaFiscal = Util.obterInteger(atualizarHidrometroActionForm.getNumeroNotaFiscal());
		}

		Integer anoFabricacao = new Integer(atualizarHidrometroActionForm.getAnoFabricacao());

		Date dataAquisicaoAnterior = null;
		try{
			dataAquisicaoAnterior = formatoData.parse("01/01/1985");
		}catch(ParseException ex){
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}
		Calendar dataAtual = new GregorianCalendar();
		int anoAtual = dataAtual.get(Calendar.YEAR);
		// caso a data de aquisi��o seja menor que a data atual
		if(dataAquisicao.after(new Date())){
			throw new ActionServletException("atencao.data.aquisicao.nao.superior.data.corrente");
		}
		Integer anoDataAquisicao = Util.getAno(dataAquisicao);
		// caso a data de aquisi��o seja menor que o ano fabrica��o
		if(anoDataAquisicao < anoFabricacao){
			throw new ActionServletException("atencao.ano.aquisicao.menor.ano.fabricacao");
		}

		// caso a data de aquisi��o seja menor que 01/01/1985
		if(dataAquisicao.before(dataAquisicaoAnterior)){
			throw new ActionServletException("atencao.data.aquisicao.nao.inferior.1985");
		}

		// caso o ano de fabrica��o seja maior que o atual
		if(anoFabricacao > anoAtual){
			throw new ActionServletException("atencao.ano.fabricacao.nao.superior.data.corrente");

		}

		// caso o ano de fabrica��o seja menor que 1985
		if(anoFabricacao < 1985){
			throw new ActionServletException("atencao.ano.fabricacao.nao.inferior.1985");
		}

		hidrometro.setNumero(atualizarHidrometroActionForm.getNumeroHidrometro());
		hidrometro.setDataAquisicao(dataAquisicao);
		hidrometro.setNumeroNotaFiscal(numeroNotaFiscal);
		hidrometro.setAnoFabricacao(new Short(atualizarHidrometroActionForm.getAnoFabricacao()));
		hidrometro.setLoteEntrega(atualizarHidrometroActionForm.getLoteEntrega());
		if(!Util.isVazioOuBranco(atualizarHidrometroActionForm.getIndicadorMacromedidor())){
			hidrometro.setIndicadorMacromedidor(new Short(atualizarHidrometroActionForm.getIndicadorMacromedidor()));
		}
		hidrometro.setNumeroDigitosLeitura(new Short(atualizarHidrometroActionForm.getIdNumeroDigitosLeitura()));

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
			boolean indicadorHidrometroComposto = atualizarHidrometroActionForm.isIndicadorHidrometroComposto();

			if(indicadorHidrometroComposto){
				hidrometro.setIndicadorHidrometroComposto(ConstantesSistema.SIM);
			}else{
				hidrometro.setIndicadorHidrometroComposto(ConstantesSistema.NAO);
			}

			String fatorConversao = atualizarHidrometroActionForm.getFatorConversao();

			if(fatorConversao != null && !fatorConversao.equals("")){
				hidrometro.setFatorConversao(new BigDecimal(fatorConversao.replace(",", ".")));
			}
		}else{
			hidrometro.setIndicadorHidrometroComposto(null);
			hidrometro.setFatorConversao(null);
		}

		// Cria o objeto hidr�metro di�metro e seta o id
		FiltroHidrometroSituacao filtroHidrometroSituacao = new FiltroHidrometroSituacao();
		filtroHidrometroSituacao.adicionarParametro(new ParametroSimples(FiltroHidrometroSituacao.ID, Integer
						.valueOf(atualizarHidrometroActionForm.getIdHidrometroSituacao())));

		HidrometroSituacao hidrometroSituacao = (HidrometroSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroHidrometroSituacao, HidrometroSituacao.class.getName()));

		if(hidrometroSituacao == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Situa��o");
		}else{

			if(HidrometroSituacao.INSTALADO.equals(Integer.valueOf(atualizarHidrometroActionForm.getIdHidrometroSituacao()))){

				FiltroHidrometroInstalacaoHistorico filtroHidrometroInstalacaoHistorico = new FiltroHidrometroInstalacaoHistorico();
				filtroHidrometroInstalacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroHidrometroInstalacaoHistorico.HIDROMETRO_ID, hidrometro.getId()));
				filtroHidrometroInstalacaoHistorico
								.adicionarParametro(new ParametroNulo(FiltroHidrometroInstalacaoHistorico.DATA_RETIRADA));

				Collection colecao = fachada.pesquisar(filtroHidrometroInstalacaoHistorico, HidrometroInstalacaoHistorico.class.getName());

				if(Util.isVazioOrNulo(colecao)){
					throw new ActionServletException("atencao.hidrometro_situacao_instalado", null, "Situa��o");
				}

			}

		}


		hidrometro.setHidrometroSituacao(hidrometroSituacao);


		// Inseri hidr�metro
		fachada.atualizarHidrometro(hidrometro, usuario);

		// M�todo utilizado para montar a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Hidr�metro de n�mero " + hidrometro.getNumero() + " atualizado com sucesso.",
						"Realizar outra Manuten��o de Hidr�metro", "exibirManterHidrometroAction.do?menu=sim");

		// Remove objetos da sess�o
		sessao.removeAttribute("HidrometroActionForm");
		sessao.removeAttribute("colecaoIntervalo");
		sessao.removeAttribute("colecaoHidrometroClasseMetrologica");
		sessao.removeAttribute("colecaoHidrometroMarca");
		sessao.removeAttribute("colecaoHidrometroDiametro");
		sessao.removeAttribute("colecaoHidrometroCapacidade");
		sessao.removeAttribute("colecaoHidrometroTipo");
		sessao.removeAttribute("fixo");
		sessao.removeAttribute("faixaInicial");
		sessao.removeAttribute("faixaFinal");
		sessao.removeAttribute("hidrometros");
		sessao.removeAttribute("colecaoHidrometroTipoTurbina");

		return retorno;
	}

}
