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
 * GSANPCG
 * Eduardo Henrique
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
import gcom.micromedicao.hidrometro.FiltroHidrometro;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.FiltroHidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.FiltroHidrometroMarca;
import gcom.micromedicao.hidrometro.FiltroHidrometroSituacao;
import gcom.micromedicao.hidrometro.FiltroHidrometroTipo;
import gcom.micromedicao.hidrometro.FiltroHidrometroTipoTurbina;
import gcom.micromedicao.hidrometro.Hidrometro;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroClasseMetrologica;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroMarca;
import gcom.micromedicao.hidrometro.HidrometroSituacao;
import gcom.micromedicao.hidrometro.HidrometroTipo;
import gcom.micromedicao.hidrometro.HidrometroTipoTurbina;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * < <Descri��o da Classe>>
 * 
 * @author Administrador
 * @created 13 de Setembro de 2005
 */
public class ExibirAtualizarHidrometroAction
				extends GcomAction {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @author eduardo henrique
	 * @date 30/07/2008
	 *       Altera��o na forma de Carregamento das Marcas e Capacidades do Hidr�metro (baseadas no
	 *       nr. fixo)
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarHidrometro");

		// Obt�m o action form
		AtualizarHidrometroActionForm atualizarHidrometroActionForm = (AtualizarHidrometroActionForm) actionForm;

		Collection colecaoHidrometroCapacidade = new ArrayList();

		Collection colecaoHidrometroMarca = new ArrayList();

		Collection<HidrometroTipoTurbina> colecaoHidrometroTipoTurbina = new ArrayList<HidrometroTipoTurbina>();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		String idHidrometro = null;

		if("S".equals(httpServletRequest.getParameter("desfazer"))){
			idHidrometro = atualizarHidrometroActionForm.getIdHidrometro();
		}else{
			idHidrometro = httpServletRequest.getParameter("idRegistroAtualizacao");

			if(idHidrometro == null){
				if(httpServletRequest.getAttribute("idRegistroAtualizacao") == null){
					idHidrometro = (String) sessao.getAttribute("idRegistroAtualizacao");
				}else{
					idHidrometro = (String) httpServletRequest.getAttribute("idRegistroAtualizacao").toString();
					httpServletRequest.setAttribute("idRegistroAtualizacao", idHidrometro);
					sessao.setAttribute("idRegistroAtualizacao", idHidrometro);
					sessao.setAttribute("veioDaTelaFiltrarHidrometro", true);
				}

			}else{
				sessao.setAttribute("i", true);
			}
		}

		// Obt�m a facahda
		Fachada fachada = Fachada.getInstancia();
		Hidrometro hidrometro = null;

		// Verifica se os objetos est�o na sess�o
		if(idHidrometro != null && !idHidrometro.equals("")){

			FiltroHidrometro filtroHidrometro = new FiltroHidrometro();
			filtroHidrometro.adicionarCaminhoParaCarregamentoEntidade(FiltroHidrometro.HIDROMETRO_SITUACAO);
			filtroHidrometro.adicionarParametro(new ParametroSimples(FiltroHidrometro.ID, idHidrometro));

			Collection hidrometros = fachada.pesquisar(filtroHidrometro, Hidrometro.class.getName());

			if(hidrometros != null && !hidrometros.isEmpty()){
				hidrometro = (Hidrometro) ((List) hidrometros).get(0);
				sessao.setAttribute("hidrometro", hidrometro);
				atualizarHidrometroActionForm.setNumeroHidrometro(formatarResultado(hidrometro.getNumero()));
				atualizarHidrometroActionForm.setAnoFabricacao(formatarResultado("" + hidrometro.getAnoFabricacao()));
				SimpleDateFormat dataFormatoAtual = new SimpleDateFormat("dd/MM/yyyy");
				// joga em dataInicial a parte da data
				String dataAquisicao = dataFormatoAtual.format(hidrometro.getDataAquisicao());

				atualizarHidrometroActionForm.setDataAquisicao(formatarResultado(dataAquisicao));
				atualizarHidrometroActionForm
								.setIdHidrometroCapacidade(formatarResultado("" + hidrometro.getHidrometroCapacidade().getId()));
				atualizarHidrometroActionForm.setIdHidrometroClasseMetrologica(formatarResultado(""
								+ hidrometro.getHidrometroClasseMetrologica().getId()));
				atualizarHidrometroActionForm.setIdHidrometroDiametro(formatarResultado("" + hidrometro.getHidrometroDiametro().getId()));
				atualizarHidrometroActionForm.setIdHidrometroMarca(formatarResultado("" + hidrometro.getHidrometroMarca().getId()));
				atualizarHidrometroActionForm.setIdHidrometroTipo(formatarResultado("" + hidrometro.getHidrometroTipo().getId()));
				atualizarHidrometroActionForm.setIndicadorMacromedidor(formatarResultado("" + hidrometro.getIndicadorMacromedidor()));
				atualizarHidrometroActionForm.setIdNumeroDigitosLeitura(formatarResultado("" + hidrometro.getNumeroDigitosLeitura()));

				// Formato da Numera��o do Hidr�metro
				String codigoFormatoNumeracaoStr = "";

				Integer codigoFormatoNumeracao = hidrometro.getCodigoFormatoNumeracao();

				if(codigoFormatoNumeracao != null){
					codigoFormatoNumeracaoStr = codigoFormatoNumeracao.toString();
				}

				atualizarHidrometroActionForm.setCodigoFormatoNumeracao(codigoFormatoNumeracaoStr);

				// Tipo de Instala��o da Turbina
				String idHidrometroTipoTurbinaStr = "";

				HidrometroTipoTurbina hidrometroTipoTurbina = hidrometro.getHidrometroTipoTurbina();

				if(hidrometroTipoTurbina != null){
					Integer idHidrometroTipoTurbina = hidrometroTipoTurbina.getId();
					idHidrometroTipoTurbinaStr = idHidrometroTipoTurbina.toString();
				}

				atualizarHidrometroActionForm.setIdHidrometroTipoTurbina(idHidrometroTipoTurbinaStr);

				// Im�vel em que o hidr�metro est� instalado
				String idImovel = fachada.pesquisarImovelHidrometroInstalado(hidrometro.getId());

				atualizarHidrometroActionForm.setIdImovel(idImovel);

				HidrometroSituacao hidrometroSituacao = hidrometro.getHidrometroSituacao();
				atualizarHidrometroActionForm.setIdHidrometroSituacao(hidrometroSituacao.getId().toString());

				atualizarHidrometroActionForm.setIdHidrometro(hidrometro.getId().toString());

				if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
					if(ConstantesSistema.SIM.equals(hidrometro.getIndicadorHidrometroComposto())){
						atualizarHidrometroActionForm.setIndicadorHidrometroComposto(true);
					}else{
						atualizarHidrometroActionForm.setIndicadorHidrometroComposto(false);
					}

					if(hidrometro.getFatorConversao() != null){
						String fatorConversao = hidrometro.getFatorConversao().toString();
						atualizarHidrometroActionForm.setFatorConversao(fatorConversao.replace(".", ","));
					}else{
						atualizarHidrometroActionForm.setFatorConversao("");
					}

					if(hidrometro.getDataUltimaRevisao() != null){
						dataFormatoAtual = new SimpleDateFormat("dd/MM/yyyy");
						String dataUltimaRevisao = dataFormatoAtual.format(hidrometro.getDataUltimaRevisao());
						atualizarHidrometroActionForm.setDataUltimaRevisao(dataUltimaRevisao);
					}else{
						atualizarHidrometroActionForm.setDataUltimaRevisao("");
					}

					if(hidrometro.getNumeroLeituraAcumulada() != null){
						atualizarHidrometroActionForm.setNumeroLeituraAcumulada(hidrometro.getNumeroLeituraAcumulada().toString());
					}else{
						atualizarHidrometroActionForm.setNumeroLeituraAcumulada("");
					}
				}
			}else{
				throw new ActionServletException("atencao.atualizacao.timestamp");
			}

			// Filtro de hidr�metro classe metrol�gica para obter todas as
			// classes metrol�gicas ativas
			FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();

			filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroClasseMetrologica.setCampoOrderBy(FiltroHidrometroClasseMetrologica.DESCRICAO);

			// Pesquisa a cole��o de classe metrol�gica
			Collection colecaoHidrometroClasseMetrologica = fachada.pesquisar(filtroHidrometroClasseMetrologica,
							HidrometroClasseMetrologica.class.getName());

			// Filtro de hidr�metro di�metro para obter todos os di�metros de
			// hidr�metros ativos
			FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();

			filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroDiametro.setCampoOrderBy(FiltroHidrometroDiametro.NUMERO_ORDEM);

			// Pesquisa a cole��o de hidr�metro capacidade
			Collection colecaoHidrometroDiametro = fachada.pesquisar(filtroHidrometroDiametro, HidrometroDiametro.class.getName());

			// Filtro de hidr�metro tipo para obter todos os tipos de
			// hidr�metros ativos
			FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();

			filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroHidrometroTipo.setCampoOrderBy(FiltroHidrometroTipo.DESCRICAO);

			// Pesquisa a cole��o de hidr�metro tipo
			Collection colecaoHidrometroTipo = fachada.pesquisar(filtroHidrometroTipo, HidrometroTipo.class.getName());

			FiltroHidrometroSituacao filtroHidroMetroSituacao = new FiltroHidrometroSituacao();
			filtroHidroMetroSituacao
.adicionarParametro(new ParametroSimples(FiltroHidrometroSituacao.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			Collection colecaoHidrometroSituacao = this.getFachada()
							.pesquisar(filtroHidroMetroSituacao, HidrometroSituacao.class.getName());

			// Envia as cole��es na sess�o
			sessao.setAttribute("colecaoHidrometroClasseMetrologica", colecaoHidrometroClasseMetrologica);
			sessao.setAttribute("colecaoHidrometroDiametro", colecaoHidrometroDiametro);
			sessao.setAttribute("colecaoHidrometroTipo", colecaoHidrometroTipo);
			sessao.setAttribute("colecaoHidrometroSituacao", colecaoHidrometroSituacao);
		}

		// Filtro de hidr�metro capacidade para obter todos as capacidade de
		// hidr�metros ativas , considerando o n�mero Fixo
		if(atualizarHidrometroActionForm.getNumeroHidrometro() != null && !atualizarHidrometroActionForm.getNumeroHidrometro().equals("")){
			FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

			filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.NUMERO_ORDEM);

			// Pesquisa a cole��o de hidr�metro capacidade
			colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());

			// Filtro de hidr�metro marca para obter todas as marcas de
			// hidr�metros ativas, considerando o n�mero fixo
			if(atualizarHidrometroActionForm.getNumeroHidrometro().length() >= 4){

				FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();

				filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				filtroHidrometroMarca.setCampoOrderBy(FiltroHidrometroMarca.DESCRICAO);

				// Pesquisa a cole��o de hidr�metro marca
				colecaoHidrometroMarca = fachada.pesquisar(filtroHidrometroMarca, HidrometroMarca.class.getName());
			}

			// Tipo de Turbina do Hidr�metro
			if(atualizarHidrometroActionForm.getNumeroHidrometro().length() >= 5){
				FiltroHidrometroTipoTurbina filtroHidrometroTipoTurbina = new FiltroHidrometroTipoTurbina();
				filtroHidrometroTipoTurbina.adicionarParametro(new ParametroSimples(FiltroHidrometroTipoTurbina.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroHidrometroTipoTurbina.setCampoOrderBy(FiltroHidrometroTipoTurbina.DESCRICAO);

				// Pesquisa a cole��o de hidr�metro marca
				colecaoHidrometroTipoTurbina = fachada.pesquisar(filtroHidrometroTipoTurbina, HidrometroTipoTurbina.class.getName());
			}
		}

		sessao.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);
		sessao.setAttribute("colecaoHidrometroMarca", colecaoHidrometroMarca);
		sessao.setAttribute("colecaoHidrometroTipoTurbina", colecaoHidrometroTipoTurbina);

		// Filtro de hidr�metro capacidade para obter capacidade de hidr�metro
		// de acordo com o id
		FiltroHidrometroCapacidade filtroHidrometroCapacidadeNumeroDigitos = new FiltroHidrometroCapacidade();

		// Verifica se a cole��o de hidrometro capacidade � diferente de null
		if(colecaoHidrometroCapacidade != null && !colecaoHidrometroCapacidade.isEmpty()){

			// Obt�m o primeiro objeto da collection

			HidrometroCapacidade hidrometroCapacidade = null;
			if(hidrometro != null && hidrometro.getHidrometroCapacidade() != null){
				hidrometroCapacidade = (HidrometroCapacidade) hidrometro.getHidrometroCapacidade();
			}else{
				Iterator colecaoHidrometroCapacidadeIterator = colecaoHidrometroCapacidade.iterator();
				hidrometroCapacidade = (HidrometroCapacidade) colecaoHidrometroCapacidadeIterator.next();
			}

			// Filtra pelo primeiro objeto da collection
			filtroHidrometroCapacidadeNumeroDigitos.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID,
							hidrometroCapacidade.getId()));
		}else{
			// Filtra pelo id selecionado no combobox
			filtroHidrometroCapacidadeNumeroDigitos.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID, new Integer(
							atualizarHidrometroActionForm.getIdHidrometroCapacidade())));

		}

		// Pesquisa o n�mero de d�gitos de acordo com o filtro
		Collection colecaoHidrometroCapacidadeNumeroDigitos = fachada.pesquisar(filtroHidrometroCapacidadeNumeroDigitos,
						HidrometroCapacidade.class.getName());

		// Retorna o objeto pesquisado
		HidrometroCapacidade hidrometroCapacidadeNumeroDigitos = (HidrometroCapacidade) Util
						.retonarObjetoDeColecao(colecaoHidrometroCapacidadeNumeroDigitos);

		if(hidrometroCapacidadeNumeroDigitos != null && !hidrometroCapacidadeNumeroDigitos.equals("")){
			// Obt�m as leituras
			Integer leituraMinimo = new Integer(hidrometroCapacidadeNumeroDigitos.getLeituraMinimo().toString());
			Integer leituraMaximo = new Integer(hidrometroCapacidadeNumeroDigitos.getLeituraMaximo().toString());
			// Obt�m a quantidade da diferen�a
			int quantidade = (leituraMaximo.intValue() - leituraMinimo.intValue()) + 1;
			Collection colecaoIntervalo = new ArrayList();

			// Adiciona a quantidade da diferen�a na cole��o
			for(int i = 0; i < quantidade; i++){
				Hidrometro hidrometroDigitos = new Hidrometro();
				Integer numero = leituraMinimo.intValue() + i;
				hidrometroDigitos.setNumeroDigitosLeitura(new Short(numero + ""));
				colecaoIntervalo.add(hidrometroDigitos);
			}
			// Envia pela sess�o
			sessao.setAttribute("colecaoIntervalo", colecaoIntervalo);
		}

		// caso ainda n�o tenha sido setado o nome campo(na primeira vez)
		if(httpServletRequest.getParameter("manter") != null){
			sessao.setAttribute("manter", "manter");
		}

		return retorno;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param parametro
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	private String formatarResultado(String parametro){

		if(parametro != null && !parametro.trim().equals("")){
			if(parametro.equals("null")){
				return "";
			}else{
				return parametro.trim();
			}
		}else{
			return "";
		}
	}

}
