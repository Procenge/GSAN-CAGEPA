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

package gcom.gui.cadastro.localidade;

import gcom.arrecadacao.Concessionaria;
import gcom.arrecadacao.FiltroConcessionaria;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroLocalidadeClasse;
import gcom.cadastro.localidade.FiltroLocalidadePorte;
import gcom.cadastro.localidade.FiltroUnidadeNegocio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.LocalidadeClasse;
import gcom.cadastro.localidade.LocalidadePorte;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirLocalidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirLocalidade");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirLocalidadeActionForm inserirLocalidadeActionForm = (InserirLocalidadeActionForm) actionForm;

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");
		String limparForm = (String) httpServletRequest.getParameter("limparCampos");
		String removerEndereco = (String) httpServletRequest.getParameter("removerEndereco");

		Collection colecaoPesquisa = null;

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){

			switch(Integer.parseInt(objetoConsulta)){
				// Elo - Localidade
				case 1:

					// Recebe o valor do campo id Unidade de Negocio do formul�rio.
					String idUnidadeNegocio = inserirLocalidadeActionForm.getIdUnidadeNegocio();

					// O elo informado deve pertencer � mesma unidade
					// de negocio da localidade que est� sendo inserida

					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

					filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_UNIDADE_NEGOCIO, idUnidadeNegocio));

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna localidade - Elo
					colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
					httpServletRequest.setAttribute("colecaoElo", colecaoPesquisa);

					break;

				// Gerente da Localidade
				case 2:

					this.pesquisarFuncionario(inserirLocalidadeActionForm);

				default:

					break;
			}
		}else{

			// Elo - Localidade
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			// Retorna localidade - Elo
			colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
			// httpServletRequest.setAttribute("colecaoElo", colecaoPesquisa);

			if(sessao.getAttribute("colecaoUnidadeNegocio") == null || sessao.getAttribute("colecaoClasse") == null
							|| sessao.getAttribute("colecaoPorte") == null || sessao.getAttribute("colecaoGerenciaRegional") == null
							|| sessao.getAttribute("colecaoMunicipio") == null){

				// Carregamento inicial do formul�rio.
				// FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

				FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
				filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME_ABREVIADO, FiltroUnidadeNegocio.NOME);

				filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna Localidade_Classe
				Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

				if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
					// Nenhum registro na tabela gerencia_regional foi
					// encontrada
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Unidade de Neg�cio");
				}else{

					UnidadeNegocio unidadeNegocio = null;
					Iterator iterator = colecaoUnidadeNegocio.iterator();

					while(iterator.hasNext()){
						unidadeNegocio = (UnidadeNegocio) iterator.next();

						String descUnidadeNegocio = unidadeNegocio.getNomeAbreviado() + "-" + unidadeNegocio.getNome();
						unidadeNegocio.setNome(descUnidadeNegocio);

					}

					sessao.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
				}

				FiltroLocalidadeClasse filtroLocalidadeClasse = new FiltroLocalidadeClasse();

				filtroLocalidadeClasse.setCampoOrderBy(FiltroLocalidadeClasse.DESCRICAO);
				filtroLocalidadeClasse.adicionarParametro(new ParametroSimples(FiltroLocalidadeClasse.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna Localidade_Classe
				colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidadeClasse, LocalidadeClasse.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Nenhum registro na tabela localidade_classe foi
					// encontrado
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Localidade_Classe");
				}else{
					sessao.setAttribute("colecaoClasse", colecaoPesquisa);
				}

				FiltroLocalidadePorte filtroLocalidadePorte = new FiltroLocalidadePorte();

				filtroLocalidadePorte.setCampoOrderBy(FiltroLocalidadePorte.DESCRICAO);

				filtroLocalidadePorte.adicionarParametro(new ParametroSimples(FiltroLocalidadePorte.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna Localidade_Porte
				colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidadePorte, LocalidadePorte.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Nenhum registro na tabela localidade_porte foi encontrado
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Localidade_Porte");
				}else{
					sessao.setAttribute("colecaoPorte", colecaoPesquisa);
				}

				// Populando Ger�ncia Regional
				FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
				filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
				filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "GERENCIA_REGIONAL");
				}else{
					sessao.setAttribute("colecaoGerenciaRegional", colecaoPesquisa);
				}

				try{
					String indicadorConcessionaria = ParametroCadastro.P_INDICADOR_CONCESSIONARIA.executar();

					if(!ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){
						// Populando Concession�ria
						FiltroConcessionaria filtroConcessionaria = new FiltroConcessionaria();
						filtroConcessionaria.setCampoOrderBy(FiltroConcessionaria.NOME);
						filtroConcessionaria.adicionarParametro(new ParametroSimples(FiltroConcessionaria.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = this.getFachada().pesquisar(filtroConcessionaria, Concessionaria.class.getName());

						if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Concession�ria");
						}else{
							sessao.setAttribute("colecaoConcessionaria", colecaoPesquisa);
						}
					}else{
						sessao.removeAttribute("colecaoConcessionaria");
					}

				}catch(ControladorException e){
					throw new IllegalArgumentException("erro.sistema");
				}


				// Populando Munic�pio
				FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
				filtroMunicipio.setCampoOrderBy(FiltroMunicipio.NOME);
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = this.getFachada().pesquisar(filtroMunicipio, Municipio.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "MUNICIPIO");
				}else{
					sessao.setAttribute("colecaoMunicipio", colecaoPesquisa);
				}

			}

			// Carrega os elos da primeira Ger�ncia do comboBox
			/*
			 * Collection colecaoGerencia = (Collection) sessao
			 * .getAttribute("colecaoGerenciaRegional");
			 * GerenciaRegional primeiraGerencia = (GerenciaRegional) Util
			 * .retonarObjetoDeColecao(colecaoGerencia);
			 * FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			 * filtroLocalidade.adicionarParametro(new ParametroSimples(
			 * FiltroLocalidade.ID_GERENCIA, primeiraGerencia));
			 * filtroLocalidade.adicionarParametro(new ParametroSimples(
			 * FiltroLocalidade.INDICADORUSO,
			 * ConstantesSistema.INDICADOR_USO_ATIVO));
			 * filtroLocalidade.adicionarParametro(new ComparacaoCampos(
			 * FiltroLocalidade.ID, "localidade"));
			 * //Retorna localidade - Elo
			 * colecaoPesquisa = fachada.pesquisar(filtroLocalidade,
			 * Localidade.class.getName());
			 * //if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
			 * httpServletRequest.setAttribute("colecaoElo", colecaoPesquisa);
			 * //}
			 */
		}

		// Codigo Cliente
		if(inserirLocalidadeActionForm.getGerenteLocalidade() != null && !inserirLocalidadeActionForm.getGerenteLocalidade().equals("")
						&& inserirLocalidadeActionForm.getNomeGerente() != null && !inserirLocalidadeActionForm.getNomeGerente().equals("")){

			httpServletRequest.setAttribute("gerenteLocalidadeEncontrado", "true");
			limparForm = "1";
		}

		if((limparForm == null || limparForm.trim().equalsIgnoreCase(""))
						|| (httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer")
										.equalsIgnoreCase("S"))){
			// -------------- bt DESFAZER ---------------

			// Estado Inicial do formulario
			// Retorna o maior id de Localidade existente
			int id = this.getFachada().pesquisarMaximoIdLocalidade();
			// Acrescenta 1 no valor do id para setar o primeiro id vazio para o usu�rio
			id = (id + 1);
			inserirLocalidadeActionForm.setLocalidadeID("" + id);
			httpServletRequest.setAttribute("nomeCampo", "localidadeID");

			// Limpando o formulario
			inserirLocalidadeActionForm.setEmail("");
			inserirLocalidadeActionForm.setFax("");
			// inserirLocalidadeActionForm.setLocalidadeID("");
			inserirLocalidadeActionForm.setLocalidadeNome("");
			inserirLocalidadeActionForm.setMenorConsumo("");
			inserirLocalidadeActionForm.setRamal("");
			inserirLocalidadeActionForm.setTelefone("");
			inserirLocalidadeActionForm.setLocalidadeCEF("");
			inserirLocalidadeActionForm.setIndicadorAbastecimentoSuspenso(false);
			inserirLocalidadeActionForm.setIndicadorAbastecimentoMinimo(false);
			inserirLocalidadeActionForm.setUltimaQuadra("");
			inserirLocalidadeActionForm.setLocalidadeContabil("");

			// Campos do tipo lista no formul�rio
			inserirLocalidadeActionForm.setEloID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirLocalidadeActionForm.setClasseID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirLocalidadeActionForm.setPorteID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirLocalidadeActionForm.setIdUnidadeNegocio("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirLocalidadeActionForm.setGerenciaRegionalID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirLocalidadeActionForm.setMunicipioID("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirLocalidadeActionForm.setGerenteLocalidade(null);
			inserirLocalidadeActionForm.setNomeGerente(null);

			// Limpa o endere�o da sess�o
			if(sessao.getAttribute("colecaoEnderecos") != null){
				sessao.removeAttribute("colecaoEnderecos");
			}
			sessao.removeAttribute("tipoPesquisaRetorno");
		}

		// Remove o endereco informado.
		if(removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase("")){

			if(sessao.getAttribute("colecaoEnderecos") != null){

				Collection enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");
				if(!enderecos.isEmpty()){
					enderecos.remove(enderecos.iterator().next());
				}

			}

		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * Pesquisa Funcionario
	 * 
	 * @author Rafael Pinto
	 * @date 15/08/2006
	 */
	private void pesquisarFuncionario(InserirLocalidadeActionForm form){

		FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Util.obterInteger(form.getGerenteLocalidade())));

		// Pesquisa de acordo com os par�metros informados no filtro
		Collection colecaoFuncionario = this.getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a cole��o
		if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){

			// Obt�m o objeto da cole��o pesquisada
			Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

			form.setGerenteLocalidade(funcionario.getId().toString());
			form.setNomeGerente(funcionario.getNome());

		}else{
			form.setGerenteLocalidade("");
			form.setNomeGerente("Funcion�rio inexistente");
		}
	}

}
