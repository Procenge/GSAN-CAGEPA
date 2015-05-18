/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
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
 * Action responsável pela atualização do conjunto de hidrometros
 * 
 * @author Sávio Luiz
 * @created 14 de Setembro de 2005
 */
public class AtualizarConjuntoHidrometroAction
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

		HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		Collection hidrometros = (Collection) sessao.getAttribute("hidrometros2");

		if(hidrometros == null || hidrometros.isEmpty()){
			hidrometros = (Collection) sessao.getAttribute("hidrometros");
		}
		// Hidrometro que terá todas as modificações feitas no
		// hidrometro_atualizar_conjunto
		Hidrometro hidrometroAtualizado = new Hidrometro();
		// Obtém a fachada
		Fachada fachada = Fachada.getInstancia();

		// Formato da Numeração do Hidrômetro
		String codigoFormatoNumeracaoStr = hidrometroActionForm.getCodigoFormatoNumeracao();
		Integer codigoFormatoNumeracao = Integer.valueOf(codigoFormatoNumeracaoStr);
		hidrometroAtualizado.setCodigoFormatoNumeracao(codigoFormatoNumeracao);

		// Cria o objeto classe metrológica e seta o id
		HidrometroClasseMetrologica hidrometroClasseMetrologica = new HidrometroClasseMetrologica();
		hidrometroClasseMetrologica.setId(new Integer(hidrometroActionForm.getIdHidrometroClasseMetrologica()));
		hidrometroAtualizado.setHidrometroClasseMetrologica(hidrometroClasseMetrologica);

		// Cria o objeto hidrômetro diâmetro e seta o id
		HidrometroDiametro hidrometroDiametro = new HidrometroDiametro();
		hidrometroDiametro.setId(new Integer(hidrometroActionForm.getIdHidrometroDiametro()));
		hidrometroAtualizado.setHidrometroDiametro(hidrometroDiametro);

		// Cria o objeto hidrômetro tipo e seta o id
		HidrometroTipo hidrometroTipo = new HidrometroTipo();
		hidrometroTipo.setId(new Integer(hidrometroActionForm.getIdHidrometroTipo()));
		hidrometroAtualizado.setHidrometroTipo(hidrometroTipo);

		// Cria o objeto hidrômetro marca e seta o id
		HidrometroMarca hidrometroMarca = new HidrometroMarca();
		hidrometroMarca.setId(new Integer(hidrometroActionForm.getIdHidrometroMarca()));
		hidrometroAtualizado.setHidrometroMarca(hidrometroMarca);

		// Cria o objeto hidrômetro tipo turbina e seta o id
		HidrometroTipoTurbina hidrometroTipoTurbina = null;

		String idHidrometroTipoTurbinaStr = hidrometroActionForm.getIdHidrometroTipoTurbina();

		if(!Util.isVazioOuBranco(idHidrometroTipoTurbinaStr)){
			Integer idHidrometroTipoTurbina = Integer.valueOf(idHidrometroTipoTurbinaStr);

			hidrometroTipoTurbina = new HidrometroTipoTurbina();
			hidrometroTipoTurbina.setId(idHidrometroTipoTurbina);
		}

		hidrometroAtualizado.setHidrometroTipoTurbina(hidrometroTipoTurbina);

		// Cria o objeto hidrômetro capacidade e seta o id
		HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();
		hidrometroCapacidade.setId(new Integer(hidrometroActionForm.getIdHidrometroCapacidade()));
		hidrometroAtualizado.setHidrometroCapacidade(hidrometroCapacidade);

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		Date dataAquisicao = null;
		try{
			dataAquisicao = formatoData.parse(hidrometroActionForm.getDataAquisicao());
		}catch(ParseException ex){
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}

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
		// caso a data de aquisição seja menor que a data atual
		if(dataAquisicao.after(new Date())){
			throw new ActionServletException("atencao.data.aquisicao.nao.superior.data.corrente");
		}
		// caso a data de aquisição seja menor que 01/01/1985
		if(dataAquisicao.before(dataAquisicaoAnterior)){
			throw new ActionServletException("atencao.data.aquisicao.nao.inferior.1985");

		}

		Integer numeroNotaFiscal = null;

		if(!Util.isVazioOuBranco(numeroNotaFiscal)){

			numeroNotaFiscal = Util.obterInteger(hidrometroActionForm.getNumeroNotaFiscal());
		}

		Integer anoFabricacao = 0;

		if(hidrometroActionForm.getAnoFabricacao() != null){
			anoFabricacao = new Integer(hidrometroActionForm.getAnoFabricacao());
		}

		// caso o ano de fabricação seja maior que o atual
		if(anoFabricacao > anoAtual){
			throw new ActionServletException("atencao.ano.fabricacao.nao.superior.data.corrente");

		}
		// caso o ano de fabricação seja menor que 1985
		if(anoFabricacao < 1985){
			throw new ActionServletException("atencao.ano.fabricacao.nao.inferior.1985");
		}

		hidrometroAtualizado.setAnoFabricacao(anoFabricacao.shortValue());

		Integer anoDataAquisicao = Util.getAno(dataAquisicao);
		// caso a data de aquisição seja menor que o ano fabricação
		if(anoDataAquisicao < anoFabricacao){
			throw new ActionServletException("atencao.ano.aquisicao.menor.ano.fabricacao");

		}

		hidrometroAtualizado.setDataAquisicao(dataAquisicao);
		hidrometroAtualizado.setNumeroNotaFiscal(numeroNotaFiscal);
		hidrometroAtualizado.setIndicadorMacromedidor(new Short(hidrometroActionForm.getIndicadorMacromedidor()));
		hidrometroAtualizado.setNumeroDigitosLeitura(new Short(hidrometroActionForm.getIdNumeroDigitosLeitura()));

		if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
			boolean indicadorHidrometroComposto = hidrometroActionForm.isIndicadorHidrometroComposto();

			if(indicadorHidrometroComposto){
				hidrometroAtualizado.setIndicadorHidrometroComposto(ConstantesSistema.SIM);
			}else{
				hidrometroAtualizado.setIndicadorHidrometroComposto(ConstantesSistema.NAO);
			}

			String fatorConversao = hidrometroActionForm.getFatorConversao();

			if(fatorConversao != null && !fatorConversao.equals("")){
				hidrometroAtualizado.setFatorConversao(new BigDecimal(fatorConversao.replace(",", ".")));
			}
		}else{
			hidrometroAtualizado.setIndicadorHidrometroComposto(null);
			hidrometroAtualizado.setFatorConversao(null);
		}

		// Atualizar hidrômetro
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		fachada.atualizarConjuntoHidrometro(hidrometros, hidrometroAtualizado, usuarioLogado);

		// Método utilizado para montar a página de sucesso
		if(codigoFormatoNumeracao.equals(Hidrometro.FORMATO_NUMERACAO_LIVRE)){
			montarPaginaSucesso(httpServletRequest, hidrometros.size() + " Hidrômetro(s) atualizado(s) com sucesso.",
							"Realizar outra Manutenção de Hidrômetro", "exibirManterHidrometroAction.do?menu=sim");
		}else{
			String fixo = (String) sessao.getAttribute("fixo");

			montarPaginaSucesso(httpServletRequest, hidrometros.size() + " Hidrômetro(s) com a numeração fixa igual a " + fixo
							+ " atualizado(s) com sucesso.", "Realizar outra Manutenção de Hidrômetro",
							"exibirManterHidrometroAction.do?menu=sim");
		}

		// Remove objetos da sessão
		sessao.removeAttribute("HidrometroActionForm");
		sessao.removeAttribute("colecaoIntervalo");
		sessao.removeAttribute("colecaoHidrometroClasseMetrologica");
		sessao.removeAttribute("colecaoHidrometroMarca");
		sessao.removeAttribute("colecaoHidrometroDiametro");
		sessao.removeAttribute("colecaoHidrometroCapacidade");
		sessao.removeAttribute("colecaoHidrometroTipo");
		sessao.removeAttribute("hidrometros");
		sessao.removeAttribute("fixo");
		sessao.removeAttribute("faixaInicial");
		sessao.removeAttribute("faixaFinal");

		return retorno;
	}
}
