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
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela pre-exibição da atualização do conjunto de hidrometro
 * 
 * @author Sávio Luiz
 * @created 14 de Setembro de 2005
 */
public class ExibirAtualizarConjuntoHidrometroAction
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

		ActionForward retorno = actionMapping.findForward("atualizarConjuntoHidrometro");

		// Obtém o action form
		HidrometroActionForm hidrometroActionForm = (HidrometroActionForm) actionForm;
		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Collection colecaoHidrometroCapacidade = null;
		/*
		 * if(sessao.getAttribute("colecaoHidrometroCapacidade")!= null &&
		 * !sessao.getAttribute("colecaoHidrometroCapacidade").equals("")){
		 * colecaoHidrometroCapacidade =
		 * (Collection)sessao.getAttribute("colecaoHidrometroCapacidade"); }
		 */

		// Filtro de hidrômetro capacidade para obter capacidade de hidrômetro
		// de acordo com o id
		FiltroHidrometroCapacidade filtroHidrometroCapacidadeNumeroDigitos = new FiltroHidrometroCapacidade();

		String fixo = (String) sessao.getAttribute("fixo");
		String faixaInicial = (String) sessao.getAttribute("faixaInicial");
		String faixaFinal = (String) sessao.getAttribute("faixaFinal");

		Collection hidrometros = (Collection) sessao.getAttribute("hidrometros2");

		if(hidrometros == null || hidrometros.isEmpty()){
			hidrometros = (Collection) sessao.getAttribute("hidrometros");
		}
		// Obtém a facahda
		Fachada fachada = Fachada.getInstancia();

		// caso o pesquisa intervalo seja diferente de nulo então veio do
		// hidrometro_atualizar_conjunto
		// e não será necessario verificar novamente se o conjunto de
		// hidrometros
		// estão com o mesmo valor
		String pesquisaIntervaloPorCapacidade = httpServletRequest.getParameter("pesquisaIntervalo");

		if(pesquisaIntervaloPorCapacidade == null || pesquisaIntervaloPorCapacidade.equals("")){

			// Verifica se os objetos estão na sessão
			if(hidrometros != null && !hidrometros.isEmpty()){

				Iterator hidrometroIterator = hidrometros.iterator();

				boolean primeiraVez = true;
				Hidrometro primeiroHidrometro = null;

				Collection<String> colecaoHidrometroNumero = new ArrayList<String>();

				while(hidrometroIterator.hasNext()){
					if(primeiraVez){

						primeiraVez = false;

						primeiroHidrometro = (Hidrometro) hidrometroIterator.next();
						// seta os valores no form
						sessao.setAttribute("hidrometro", primeiroHidrometro);
						hidrometroActionForm.setFixo(fixo);
						hidrometroActionForm.setFaixaFinal(faixaFinal);
						hidrometroActionForm.setFaixaInicial(faixaInicial);
						hidrometroActionForm.setAnoFabricacao(formatarResultado("" + primeiroHidrometro.getAnoFabricacao()));
						SimpleDateFormat dataFormatoAtual = new SimpleDateFormat("dd/MM/yyyy");
						// joga em dataInicial a parte da data
						String dataAquisicao = dataFormatoAtual.format(primeiroHidrometro.getDataAquisicao());

						if(primeiroHidrometro.getNumeroNotaFiscal() != null){

							hidrometroActionForm.setNumeroNotaFiscal(primeiroHidrometro.getNumeroNotaFiscal().toString());
						}

						hidrometroActionForm.setDataAquisicao(formatarResultado(dataAquisicao));
						hidrometroActionForm.setIdHidrometroCapacidade(formatarResultado(""
										+ primeiroHidrometro.getHidrometroCapacidade().getId()));
						hidrometroActionForm.setIdHidrometroClasseMetrologica(formatarResultado(""
										+ primeiroHidrometro.getHidrometroClasseMetrologica().getId()));
						hidrometroActionForm.setIdHidrometroDiametro(formatarResultado(""
										+ primeiroHidrometro.getHidrometroDiametro().getId()));
						hidrometroActionForm.setIdHidrometroMarca(formatarResultado("" + primeiroHidrometro.getHidrometroMarca().getId()));
						hidrometroActionForm.setIdHidrometroTipo(formatarResultado("" + primeiroHidrometro.getHidrometroTipo().getId()));
						hidrometroActionForm
										.setIndicadorMacromedidor(formatarResultado("" + primeiroHidrometro.getIndicadorMacromedidor()));
						hidrometroActionForm
										.setIdNumeroDigitosLeitura(formatarResultado("" + primeiroHidrometro.getNumeroDigitosLeitura()));

						if(SistemaParametro.INDICADOR_EMPRESA_DESO.equals(getParametroCompanhia(httpServletRequest))){
							if(ConstantesSistema.SIM.equals(primeiroHidrometro.getIndicadorHidrometroComposto())){
								hidrometroActionForm.setIndicadorHidrometroComposto(true);
							}else{
								hidrometroActionForm.setIndicadorHidrometroComposto(false);
							}

							if(primeiroHidrometro.getFatorConversao() != null){
								String fatorConversao = primeiroHidrometro.getFatorConversao().toString();
								hidrometroActionForm.setFatorConversao(fatorConversao.replace(".", ","));
							}else{
								hidrometroActionForm.setFatorConversao("");
							}
						}

						// Formato da Numeração do Hidrômetro
						String codigoFormatoNumeracaoStr = "";

						Integer codigoFormatoNumeracao = primeiroHidrometro.getCodigoFormatoNumeracao();

						if(codigoFormatoNumeracao != null){
							codigoFormatoNumeracaoStr = codigoFormatoNumeracao.toString();
						}

						hidrometroActionForm.setCodigoFormatoNumeracao(codigoFormatoNumeracaoStr);

						// Tipo de Instalação da Turbina
						String idHidrometroTipoTurbinaStr = "";

						HidrometroTipoTurbina hidrometroTipoTurbina = primeiroHidrometro.getHidrometroTipoTurbina();

						if(hidrometroTipoTurbina != null){
							Integer idHidrometroTipoTurbina = hidrometroTipoTurbina.getId();
							idHidrometroTipoTurbinaStr = idHidrometroTipoTurbina.toString();
						}

						hidrometroActionForm.setIdHidrometroTipoTurbina(idHidrometroTipoTurbinaStr);

						// Número do Hidrômetro (Utilizado para o formato "Livre")
						String numero = primeiroHidrometro.getNumero();
						colecaoHidrometroNumero.add(numero);
					}else{
						Hidrometro hidrometro = (Hidrometro) hidrometroIterator.next();
						// Caso as informações de data de aquisição, ano de
						// fabricação, finalidade da medição do hidrômetro,
						// classe metrológica, marca, diâmetro, capacidade,
						// tipo,fator de conversão e hidrômetro composto.
						// não sejam todas iguais
						if(!primeiroHidrometro.equalsHidrometro(hidrometro)){
							throw new ActionServletException("atencao.faixa.nao.uniformidade");
						}

						// Número do Hidrômetro (Utilizado para o formato "Livre")
						String numero = hidrometro.getNumero();
						colecaoHidrometroNumero.add(numero);
					}
				}

				// Ordenando coleção de números do hidrômetro (Utilizado para o formato "Livre")
				Collections.sort((List<String>) colecaoHidrometroNumero);

				// Filtro de hidrômetro classe metrológica para obter todas as
				// classes metrológicas ativas
				FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();

				filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroHidrometroClasseMetrologica.setCampoOrderBy(FiltroHidrometroClasseMetrologica.DESCRICAO);

				// Pesquisa a coleção de classe metrológica
				Collection colecaoHidrometroClasseMetrologica = fachada.pesquisar(filtroHidrometroClasseMetrologica,
								HidrometroClasseMetrologica.class.getName());

				// Filtro de hidrômetro marca para obter todas as marcas de
				// hidrômetros ativas
				FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();

				filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroHidrometroMarca.setCampoOrderBy(FiltroHidrometroMarca.DESCRICAO);

				// Pesquisa a coleção de hidrômetro marca
				Collection colecaoHidrometroMarca = fachada.pesquisar(filtroHidrometroMarca, HidrometroMarca.class.getName());

				// Filtro de hidrômetro diâmetro para obter todos os diâmetros
				// de
				// hidrômetros ativos
				FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();

				filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroHidrometroDiametro.setCampoOrderBy(FiltroHidrometroDiametro.DESCRICAO);

				// Pesquisa a coleção de hidrômetro capacidade
				Collection colecaoHidrometroDiametro = fachada.pesquisar(filtroHidrometroDiametro, HidrometroDiametro.class.getName());

				// Filtro de hidrômetro capacidade para obter todos as
				// capacidade de
				// hidrômetros ativas
				FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();

				filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.DESCRICAO);

				// Pesquisa a coleção de hidrômetro capacidade
				colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade, HidrometroCapacidade.class.getName());

				// Filtro de hidrômetro tipo para obter todos os tipos de
				// hidrômetros ativos
				FiltroHidrometroTipo filtroHidrometroTipo = new FiltroHidrometroTipo();

				filtroHidrometroTipo.adicionarParametro(new ParametroSimples(FiltroHidrometroTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroHidrometroTipo.setCampoOrderBy(FiltroHidrometroTipo.DESCRICAO);

				// Pesquisa a coleção de hidrômetro tipo
				Collection colecaoHidrometroTipo = fachada.pesquisar(filtroHidrometroTipo, HidrometroTipo.class.getName());

				// Tipo de Turbina do Hidrômetro
				FiltroHidrometroTipoTurbina filtroHidrometroTipoTurbina = new FiltroHidrometroTipoTurbina();
				filtroHidrometroTipoTurbina.adicionarParametro(new ParametroSimples(FiltroHidrometroTipoTurbina.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroHidrometroTipoTurbina.setCampoOrderBy(FiltroHidrometroTipoTurbina.DESCRICAO);

				// Pesquisa a coleção de hidrômetro marca
				Collection colecaoHidrometroTipoTurbina = fachada.pesquisar(filtroHidrometroTipoTurbina, HidrometroTipoTurbina.class
								.getName());

				// Envia as coleções na sessão
				sessao.setAttribute("colecaoHidrometroClasseMetrologica", colecaoHidrometroClasseMetrologica);
				sessao.setAttribute("colecaoHidrometroMarca", colecaoHidrometroMarca);
				sessao.setAttribute("colecaoHidrometroDiametro", colecaoHidrometroDiametro);
				sessao.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);
				sessao.setAttribute("colecaoHidrometroTipo", colecaoHidrometroTipo);
				sessao.setAttribute("colecaoHidrometroTipoTurbina", colecaoHidrometroTipoTurbina);
				sessao.setAttribute("colecaoHidrometroNumero", colecaoHidrometroNumero);

				// Verifica se a coleção de hidrometro capacidade é diferente de
				// null
				// if (colecaoHidrometroCapacidade != null
				// && !colecaoHidrometroCapacidade.isEmpty()) {
				//
				// // Obtém o primeiro objeto da collection
				// Iterator colecaoHidrometroCapacidadeIterator = colecaoHidrometroCapacidade
				// .iterator();
				// HidrometroCapacidade hidrometroCapacidade = (HidrometroCapacidade)
				// colecaoHidrometroCapacidadeIterator
				// .next();

				// Filtra pelo primeiro objeto da collection
				filtroHidrometroCapacidadeNumeroDigitos.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID,
								primeiroHidrometro.getHidrometroCapacidade().getId()));
				// }
			}
		}else{

			// Filtra pelo id selecionado no combobox
			filtroHidrometroCapacidadeNumeroDigitos.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID, new Integer(
							hidrometroActionForm.getIdHidrometroCapacidade())));

		}

		// Pesquisa o número de dígitos de acordo com o filtro
		Collection colecaoHidrometroCapacidadeNumeroDigitos = fachada.pesquisar(filtroHidrometroCapacidadeNumeroDigitos,
						HidrometroCapacidade.class.getName());

		// Retorna o objeto pesquisado
		HidrometroCapacidade hidrometroCapacidadeNumeroDigitos = (HidrometroCapacidade) Util
						.retonarObjetoDeColecao(colecaoHidrometroCapacidadeNumeroDigitos);

		// Obtém as leituras
		Integer leituraMinimo = new Integer(hidrometroCapacidadeNumeroDigitos.getLeituraMinimo().toString());
		Integer leituraMaximo = new Integer(hidrometroCapacidadeNumeroDigitos.getLeituraMaximo().toString());

		// Obtém a quantidade da diferença
		int quantidade = (leituraMaximo.intValue() - leituraMinimo.intValue()) + 1;

		Collection colecaoIntervalo = new ArrayList();

		// Adiciona a quantidade da diferença na coleção
		for(int i = 0; i < quantidade; i++){

			colecaoIntervalo.add(new Integer(leituraMinimo.intValue() + i));
		}

		// Envia pela sessão
		sessao.setAttribute("colecaoIntervalo", colecaoIntervalo);

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param parametro
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
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
