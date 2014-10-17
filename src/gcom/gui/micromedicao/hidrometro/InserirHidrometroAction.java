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
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.FiltroHidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.FiltroHidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.FiltroHidrometroMarca;
import gcom.micromedicao.hidrometro.FiltroHidrometroSituacao;
import gcom.micromedicao.hidrometro.FiltroHidrometroTipo;
import gcom.micromedicao.hidrometro.FiltroHidrometroTipoTurbina;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroLocalArmazenagem;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.micromedicao.hidrometro.HidrometroTipo;
import gcom.micromedicao.hidrometro.HidrometroTipoTurbina;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
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
 * <<Descrição da Classe>>
 * 
 * @author Administrador
 */
public class InserirHidrometroAction
				extends GcomAction {

	/**
	 * <<Descrição do método>>
	 * 
	 * @author Saulo Lima
	 * @date 03/03/2009
	 *       Identação + mensagem de confirmação
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obtém o action form
		HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);

		String confirmado = httpServletRequest.getParameter("confirmado");

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		// Define a ação de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Filtro para obter o local de armazenagem ativo de id informado
		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem = new FiltroHidrometroLocalArmazenagem();

		filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.ID, new Integer(
						hidrometroActionForm.getIdLocalArmazenagem()), ParametroSimples.CONECTOR_AND));
		filtroHidrometroLocalArmazenagem.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoHidrometroLocalArmazenagem = fachada.pesquisar(filtroHidrometroLocalArmazenagem, HidrometroLocalArmazenagem.class
						.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoHidrometroLocalArmazenagem == null || colecaoHidrometroLocalArmazenagem.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.hidrometro_local_armazenagem.inexistente");
		}

		// Cria o objeto classe metrológica e seta o id
		FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();
		filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.ID, Integer
						.valueOf(hidrometroActionForm.getIdHidrometroClasseMetrologica())));

		HidrometroClasseMetrologica hidrometroClasseMetrologica = (HidrometroClasseMetrologica) Util.retonarObjetoDeColecao(fachada
						.pesquisar(filtroHidrometroClasseMetrologica, HidrometroClasseMetrologica.class.getName()));

		if(hidrometroClasseMetrologica == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Classe Metrológica");
		}

		// Cria o objeto hidrômetro marca e seta o id
		FiltroHidrometroMarca filtroHidrometroMarca1 = new FiltroHidrometroMarca();
		filtroHidrometroMarca1.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.ID, Integer.valueOf(hidrometroActionForm
						.getIdHidrometroMarca())));

		HidrometroMarca hidrometroMarca = (HidrometroMarca) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroHidrometroMarca1,
						HidrometroMarca.class.getName()));

		if(hidrometroMarca == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Marca");
		}

		/**
		 * [FS0004]- Verificar Preenchimento dos campos Caso 3
		 */
		// FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();
		//
		// filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.ID,
		// hidrometroMarca.getId().toString()));
		//
		// Collection colecaoHidrometroMarcaBase = fachada.pesquisar(filtroHidrometroMarca,
		// HidrometroMarca.class.getName());
		//
		// HidrometroMarca hidrometroMarcaBase = (HidrometroMarca)
		// colecaoHidrometroMarcaBase.iterator().next();

		if(!hidrometroMarca.getCodigoHidrometroMarca().equalsIgnoreCase(hidrometroActionForm.getFixo().substring(3, 4))){
			throw new ActionServletException("atencao.marca_incompativel_numero_fixo");
		}

		// Formato da Numeração do Hidrômetro
		String codigoFormatoNumeracaoStr = hidrometroActionForm.getCodigoFormatoNumeracao();
		Integer codigoFormatoNumeracao = Integer.valueOf(codigoFormatoNumeracaoStr);

		// Tipo de Instalação da Turbina
		HidrometroTipoTurbina hidrometroTipoTurbina = null;

		if(codigoFormatoNumeracaoStr.equals(Hidrometro.FORMATO_NUMERACAO_5_X_5.toString())){
			FiltroHidrometroTipoTurbina filtroHidrometroTipoTurbina = new FiltroHidrometroTipoTurbina();
			filtroHidrometroTipoTurbina.adicionarParametro(new ParametroSimples(FiltroHidrometroTipoTurbina.ID, Integer
							.valueOf(hidrometroActionForm.getIdHidrometroTipoTurbina())));

			hidrometroTipoTurbina = (HidrometroTipoTurbina) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroHidrometroTipoTurbina,
							HidrometroTipoTurbina.class.getName()));

			if(hidrometroTipoTurbina == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo de Instalação da Turbina");
			}

			char codigoHidrometroTipoTurbina = hidrometroTipoTurbina.getCodigo();
			String codigoHidrometroTipoTurbinaStr = Character.toString(codigoHidrometroTipoTurbina);

			if(!codigoHidrometroTipoTurbinaStr.equalsIgnoreCase(hidrometroActionForm.getFixo().substring(4, 5))){
				throw new ActionServletException("atencao.tipo_turbina_incompativel_numero_fixo");
			}
		}

		String fixo = hidrometroActionForm.getFixo();

		/**
		 * [FS0004]- Verificar Preenchimento dos campos Caso 2
		 */
		// Cria o objeto hidrômetro capacidade e seta o id
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID, Integer
						.valueOf(hidrometroActionForm.getIdHidrometroCapacidade())));

		HidrometroCapacidade hidrometroCapacidade = (HidrometroCapacidade) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroHidrometroCapacidade, HidrometroCapacidade.class.getName()));

		if(hidrometroCapacidade == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Capacidade");
		}

		if(!hidrometroCapacidade.getCodigoHidrometroCapacidade().equalsIgnoreCase(hidrometroActionForm.getFixo().substring(0, 1))){
			throw new ActionServletException("atencao.capacidade_incompativel_numero_fixo");
		}

		// Cria o objeto hidrômetro diâmetro e seta o id
		FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
		filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.ID, Integer.valueOf(hidrometroActionForm
						.getIdHidrometroDiametro())));

		HidrometroDiametro hidrometroDiametro = (HidrometroDiametro) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroHidrometroDiametro, HidrometroDiametro.class.getName()));

		if(hidrometroDiametro == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Diâmetro");
		}

		// Cria o objeto hidrômetro tipo e seta o id
		FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();
		filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.ID, Integer.valueOf(hidrometroActionForm
						.getIdHidrometroTipo())));

		HidrometroTipo hidrometroTipo = (HidrometroTipo) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroHidrometroTipo,
						HidrometroTipo.class.getName()));

		if(hidrometroTipo == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo");
		}

		// Cria o objeto hidrômetro local armazenagem e seta o id
		FiltroHidrometroLocalArmazenagem filtroHidrometroLocalArmazenagem1 = new FiltroHidrometroLocalArmazenagem();
		filtroHidrometroLocalArmazenagem1.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalArmazenagem.ID, Integer
						.valueOf(hidrometroActionForm.getIdLocalArmazenagem())));

		HidrometroLocalArmazenagem hidrometroLocalArmazenagem = (HidrometroLocalArmazenagem) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroHidrometroLocalArmazenagem1, HidrometroLocalArmazenagem.class.getName()));

		if(hidrometroLocalArmazenagem == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Local de Armazenagem");
		}

		// Cria o objeto hidrômetro situacao e seta o id
		FiltroHidrometroSituacao filtroHidrometroSituacao = new FiltroHidrometroSituacao();
		filtroHidrometroSituacao.adicionarParametro(new ParametroSimples(FiltroHidrometroSituacao.ID, HidrometroSituacao.DISPONIVEL));

		HidrometroSituacao hidrometroSituacao = (HidrometroSituacao) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroHidrometroSituacao, HidrometroSituacao.class.getName()));

		if(hidrometroSituacao == null){
			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Situação do Hidrometro");
		}

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

		Integer anoFabricacao = new Integer(hidrometroActionForm.getAnoFabricacao());

		// caso o ano de fabricação seja maior que o atual
		if(anoFabricacao > anoAtual){
			throw new ActionServletException("atencao.ano.fabricacao.nao.superior.data.corrente");
		}

		// caso o ano de fabricação seja menor que 1985
		if(anoFabricacao < 1985){
			throw new ActionServletException("atencao.ano.fabricacao.nao.inferior.1985");
		}

		Integer anoDataAquisicao = Util.getAno(dataAquisicao);

		// caso a data de aquisição seja menor que o ano fabricação
		if(anoDataAquisicao < anoFabricacao){
			throw new ActionServletException("atencao.ano.aquisicao.menor.ano.fabricacao");
		}

		// Cria o objeto hidromêtro
		Hidrometro hidrometro = null;

		try{
			hidrometro = new Hidrometro(null,
			// numero
							formatoData.parse(hidrometroActionForm.getDataAquisicao()),
							// dataAquisicao
							new Short(hidrometroActionForm.getAnoFabricacao()),
							// anoFabricacao
							new Short(hidrometroActionForm.getIndicadorMacromedidor()),
							// indicadorMacromedidor
							null,
							// dataUltimaRevisao
							null,
							// dataBaixa
							new Integer("0"),
							// numeroLeituraAcumulada
							new Short(hidrometroActionForm.getIdNumeroDigitosLeitura()),
							// numeroDigitosLeitura
							new Date(),
							// ultimaAlteracao
							hidrometroTipo,
							// hidrômetroTipo
							hidrometroSituacao,
							// hidrometroSituacao
							hidrometroMarca,
							// hidrometroMarca
							hidrometroCapacidade,
							// hidrometroCapacidade
							null,
							// hidrometroMotivoBaixa
							hidrometroLocalArmazenagem,
							// hidrometroLocalArmazenagem
							hidrometroClasseMetrologica,
							// hidrometroClasseMetrologica
							hidrometroDiametro);
			// hidrometroDiametro

			if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
				boolean indicadorHidrometroComposto = hidrometroActionForm.isIndicadorHidrometroComposto();

				if(indicadorHidrometroComposto){
					hidrometro.setIndicadorHidrometroComposto(ConstantesSistema.SIM);
				}else{
					hidrometro.setIndicadorHidrometroComposto(ConstantesSistema.NAO);
				}

				String fatorConversao = hidrometroActionForm.getFatorConversao();

				if(fatorConversao != null && !fatorConversao.equals("")){
					hidrometro.setFatorConversao(new BigDecimal(fatorConversao.replace(",", ".")));
				}
			}

			// Formato da Numeração do Hidrômetro
			hidrometro.setCodigoFormatoNumeracao(codigoFormatoNumeracao);

			// Tipo de Instalação da Turbina
			hidrometro.setHidrometroTipoTurbina(hidrometroTipoTurbina);
		}catch(ParseException ex){
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}

		// Obtém a faixa inicial
		Integer faixaInicial = new Integer(hidrometroActionForm.getFaixaInicial());

		// Obtém a faixa final
		Integer faixaFinal = new Integer(hidrometroActionForm.getFaixaFinal());

		Integer intervalo = new Integer((faixaFinal.intValue() - faixaInicial.intValue()) + 1);

		if(confirmado == null || !confirmado.trim().equalsIgnoreCase("ok")){
			return montarPaginaConfirmacao("atencao.inclusao.hidrometro", httpServletRequest, actionMapping, Integer.toString(intervalo));
		}

		// Inserir hidrômetro
		int quantidadeInseridos = fachada.inserirHidrometro(hidrometro, fixo, faixaInicial, faixaFinal, usuarioLogado);

		String mensagemConfirmacao = "";
		if(intervalo.intValue() == 1){
			if(quantidadeInseridos == 1){
				mensagemConfirmacao = "Hidrômetro inserido com sucesso.";
			}else{
				mensagemConfirmacao = "Hidrômetro já existente atualizado com sucesso.";
			}
		}else{
			if(intervalo.intValue() == quantidadeInseridos){
				mensagemConfirmacao = "" + quantidadeInseridos + " hidrômetros inseridos com sucesso.";
			}else{
				mensagemConfirmacao = "" + quantidadeInseridos + " hidrômetro(s) inserido(s) e "
								+ (intervalo.intValue() - quantidadeInseridos) + " atualizado(s) com sucesso";
			}
		}

		// Método utilizado para montar a página de sucesso
		montarPaginaSucesso(httpServletRequest, mensagemConfirmacao, "Inserir outro(s) Hidrômetro(s)",
						"exibirInserirHidrometroAction.do?menu=sim");

		// Remove objetos da sessão
		sessao.removeAttribute("HidrometroActionForm");
		sessao.removeAttribute("colecaoIntervalo");
		sessao.removeAttribute("colecaoHidrometroClasseMetrologica");
		sessao.removeAttribute("colecaoHidrometroMarca");
		sessao.removeAttribute("colecaoHidrometroDiametro");
		sessao.removeAttribute("colecaoHidrometroCapacidade");
		sessao.removeAttribute("colecaoHidrometroTipo");

		return retorno;
	}
}
