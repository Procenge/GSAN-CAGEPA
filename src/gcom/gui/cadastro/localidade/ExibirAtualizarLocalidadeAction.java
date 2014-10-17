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

package gcom.gui.cadastro.localidade;

import gcom.arrecadacao.Concessionaria;
import gcom.arrecadacao.ConcessionariaLocalidade;
import gcom.arrecadacao.FiltroConcessionaria;
import gcom.arrecadacao.FiltroConcessionariaLocalidade;
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
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ComparacaoCampos;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.Collection;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarLocalidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("atualizarLocalidade");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarLocalidadeActionForm atualizarLocalidadeActionForm = (AtualizarLocalidadeActionForm) actionForm;

		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");
		String removerEndereco = (String) httpServletRequest.getParameter("removerEndereco");

		// variavel criada para identificar voltou do Adicionar Endereço
		String tipoRetorno = (String) sessao.getAttribute("tipoPesquisaRetorno");

		String localidadeID = null;

		if((objetoConsulta == null || objetoConsulta.equalsIgnoreCase(""))
						&& (removerEndereco == null || removerEndereco.equalsIgnoreCase(""))
						&& (httpServletRequest.getParameter("desfazer") == null)
						&& (tipoRetorno == null || !tipoRetorno.equalsIgnoreCase("localidade"))){
			// Recupera o id da Localidade que vai ser atualizada

			if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
				localidadeID = httpServletRequest.getParameter("idRegistroInseridoAtualizar");
				// Definindo a volta do botão Voltar p Filtrar Localidade
				sessao.setAttribute("voltar", "filtrar");
				sessao.setAttribute("idRegistroAtualizar", localidadeID);
				sessao.setAttribute("tipoPesquisa", ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());
			}else if(httpServletRequest.getParameter("idRegistroAtualizar") == null){
				localidadeID = (String) sessao.getAttribute("idRegistroAtualizar");
				// Definindo a volta do botão Voltar p Filtrar Localidade
				sessao.setAttribute("voltar", "filtrar");
			}else if(httpServletRequest.getParameter("idRegistroAtualizar") != null){
				localidadeID = httpServletRequest.getParameter("idRegistroAtualizar");
				// Definindo a volta do botão Voltar p Manter Localidade
				sessao.setAttribute("voltar", "manter");
				sessao.setAttribute("idRegistroAtualizar", localidadeID);
			}

		}else{
			localidadeID = (String) sessao.getAttribute("idRegistroAtualizar");
		}

		httpServletRequest.setAttribute("voltar", sessao.getAttribute("voltar"));

		String atualizarEndereco = (String) httpServletRequest.getParameter("limparCampos");

		Collection colecaoPesquisa = null;

		// Validação do codigo da localidade que ficará como Elo.
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
		httpServletRequest.setAttribute("colecaoElo", colecaoPesquisa);

		if((objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase(""))
						|| (removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase(""))
						|| (atualizarEndereco != null && !atualizarEndereco.trim().equalsIgnoreCase(""))){

			if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){
				switch(Integer.parseInt(objetoConsulta)){
					// Elo - Localidade
					case 1:

						// Recebe o valor do campo id Unidade de Negocio do formulário.
						String idUnidadeNegocio = atualizarLocalidadeActionForm.getIdUnidadeNegocio();

						// O elo informado deve pertencer à mesma unidade
						// de negocio da localidade que está sendo inserida

						filtroLocalidade = new FiltroLocalidade();

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

						this.pesquisarFuncionario(atualizarLocalidadeActionForm);

					default:
						break;
				}
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

		}else if(httpServletRequest.getParameter("desfazer") == null){

			String localidadeIDForm = atualizarLocalidadeActionForm.getLocalidadeID();
			if((localidadeID == null || localidadeID.equalsIgnoreCase(""))
							&& (localidadeIDForm == null || localidadeIDForm.equalsIgnoreCase(""))){
				// ID da localidade não informado
				throw new ActionServletException("atencao.codigo_localidade_nao_informado");
			}else{

				// Carregamento inicial do formulário.
				// ===================================================================

				// Elo - Localidade
				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				// Retorna localidade - Elo
				colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());
				httpServletRequest.setAttribute("colecaoElo", colecaoPesquisa);

				if(localidadeID != null && !localidadeID.equalsIgnoreCase("")){

					if(sessao.getAttribute("colecaoUnidadeNegocio") == null //
									|| sessao.getAttribute("colecaoClasse") == null
									|| sessao.getAttribute("colecaoPorte") == null
									|| sessao.getAttribute("colecaoConcessionaria") == null
									|| sessao.getAttribute("colecaoGerenciaRegional") == null
									|| sessao.getAttribute("colecaoMunicipio") == null){

						// Unidade de Negocio
						// ==============================================
						FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();
						filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME_ABREVIADO, FiltroUnidadeNegocio.NOME);

						filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna Localidade_Classe
						Collection colecaoUnidadeNegocio = this.getFachada()
										.pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

						if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){
							// Nenhum registro na tabela gerencia_regional foi
							// encontrada
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", "Unidade de Negócio");
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
						// ================================================================

						// Localidade_Classe
						// ==============================================
						FiltroLocalidadeClasse filtroLocalidadeClasse = new FiltroLocalidadeClasse();

						filtroLocalidadeClasse.setCampoOrderBy(FiltroLocalidadeClasse.DESCRICAO);

						filtroLocalidadeClasse.adicionarParametro(new ParametroSimples(FiltroLocalidadeClasse.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna Localidade_Classe
						colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidadeClasse, LocalidadeClasse.class.getName());

						if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
							// Nenhum registro na tabela localidade_classe foi
							// encontrado
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", "Localidade_Classe");
						}else{
							sessao.setAttribute("colecaoClasse", colecaoPesquisa);
						}
						// ================================================================

						// Localidade_Porte
						// ===============================================
						FiltroLocalidadePorte filtroLocalidadePorte = new FiltroLocalidadePorte();

						filtroLocalidadePorte.setCampoOrderBy(FiltroLocalidadePorte.DESCRICAO);

						filtroLocalidadePorte.adicionarParametro(new ParametroSimples(FiltroLocalidadePorte.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						// Retorna Localidade_Porte
						colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidadePorte, LocalidadePorte.class.getName());

						if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
							// Nenhum registro na tabela localidade_porte foi
							// encontrado
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", "Localidade_Porte");
						}else{
							sessao.setAttribute("colecaoPorte", colecaoPesquisa);
						}

						try{
							String indicadorConcessionaria = ParametroCadastro.P_INDICADOR_CONCESSIONARIA.executar();

							if(!ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){
								// Populando Concessionária
								FiltroConcessionaria filtroConcessionaria = new FiltroConcessionaria();
								filtroConcessionaria.setCampoOrderBy(FiltroConcessionaria.NOME);
								filtroConcessionaria.adicionarParametro(new ParametroSimples(FiltroConcessionaria.INDICADOR_USO,
												ConstantesSistema.INDICADOR_USO_ATIVO));

								colecaoPesquisa = this.getFachada().pesquisar(filtroConcessionaria, Concessionaria.class.getName());

								if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
									throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Concessionária");
								}else{
									sessao.setAttribute("colecaoConcessionaria", colecaoPesquisa);
								}
							}

						}catch(ControladorException e){
							throw new IllegalArgumentException("erro.sistema");
						}

						// Populando Gerência Regional
						FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
						filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
						filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

						if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", "GERENCIA_REGIONAL");
						}else{
							sessao.setAttribute("colecaoGerenciaRegional", colecaoPesquisa);
						}

						// Populando Município
						FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
						filtroMunicipio.setCampoOrderBy(FiltroMunicipio.NOME);
						filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = this.getFachada().pesquisar(filtroMunicipio, Municipio.class.getName());

						if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", "MUNICIPIO");
						}else{
							sessao.setAttribute("colecaoMunicipio", colecaoPesquisa);
						}

					}
					// ==================================================================

					// Pesquisa os dados da localidade selecionada
					// ==================================================================

					exibirLocalidade(localidadeID, atualizarLocalidadeActionForm, sessao, httpServletRequest);

				}
			}
		}

		if(httpServletRequest.getParameter("desfazer") != null && httpServletRequest.getParameter("desfazer").equalsIgnoreCase("S")){

			sessao.removeAttribute("tipoPesquisaRetorno");
			sessao.removeAttribute("colecaoEnderecos");
			exibirLocalidade(localidadeID, atualizarLocalidadeActionForm, sessao, httpServletRequest);
		}

		// Codigo Cliente
		if(atualizarLocalidadeActionForm.getGerenteLocalidade() != null && !atualizarLocalidadeActionForm.getGerenteLocalidade().equals("")
						&& atualizarLocalidadeActionForm.getNomeGerente() != null
						&& !atualizarLocalidadeActionForm.getNomeGerente().equals("")){

			httpServletRequest.setAttribute("gerenteLocalidadeEncontrado", "true");
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	private void exibirLocalidade(String localidadeID, AtualizarLocalidadeActionForm atualizarLocalidadeActionForm, HttpSession sessao,
					HttpServletRequest httpServletRequest){

		Collection colecaoPesquisa = null;

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		// Objetos que serão retornados pelo hibernate
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("unidadeNegocio");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("funcionario");
		filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade("localidade");

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

		// Retorna Localidade
		colecaoPesquisa = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
			// Localidade não cadastrada
			throw new ActionServletException("atencao.processo.localidadeNaoCadastrada");
		}else{
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

			// Coloca o objeto selecionado na sessão
			sessao.setAttribute("localidadeManter", localidade);

			atualizarLocalidadeActionForm.setLocalidadeID(localidadeID);

			atualizarLocalidadeActionForm.setLocalidadeNome(localidade.getDescricao().trim());

			if(localidade.getFone() != null){
				atualizarLocalidadeActionForm.setTelefone(String.valueOf(localidade.getFone()).trim());
			}else{
				atualizarLocalidadeActionForm.setTelefone("");
			}

			if(localidade.getRamalfone() != null){
				atualizarLocalidadeActionForm.setRamal(String.valueOf(localidade.getRamalfone()).trim());
			}else{
				atualizarLocalidadeActionForm.setRamal("");
			}

			if(localidade.getFax() != null){
				atualizarLocalidadeActionForm.setFax(String.valueOf(localidade.getFax()).trim());
			}else{
				atualizarLocalidadeActionForm.setFax("");
			}

			if(localidade.getConsumoGrandeUsuario() != null){
				atualizarLocalidadeActionForm.setMenorConsumo(String.valueOf(localidade.getConsumoGrandeUsuario()).trim());
			}else{
				atualizarLocalidadeActionForm.setMenorConsumo("");
			}

			if(localidade.getEmail() != null){
				atualizarLocalidadeActionForm.setEmail(localidade.getEmail().trim());
			}else{
				atualizarLocalidadeActionForm.setEmail("");
			}

			// ICMS
			if(localidade.getCodigoICMS() != null){
				atualizarLocalidadeActionForm.setIcms(localidade.getCodigoICMS().toString());
			}else{
				atualizarLocalidadeActionForm.setIcms("");
			}

			// Centro de Custo
			if(localidade.getCodigoCentroCusto() != null){
				atualizarLocalidadeActionForm.setCentroCusto(localidade.getCodigoCentroCusto().toString());
			}else{
				atualizarLocalidadeActionForm.setCentroCusto("");
			}

			// Informatizada
			atualizarLocalidadeActionForm.setInformatizada(localidade.getIndicadorLocalidadeInformatizada().toString());

			// Cliente
			if(localidade.getFuncionario() != null){
				atualizarLocalidadeActionForm.setGerenteLocalidade(localidade.getFuncionario().getId().toString());
				atualizarLocalidadeActionForm.setNomeGerente(localidade.getFuncionario().getNome().toString());
			}else{
				atualizarLocalidadeActionForm.setGerenteLocalidade("");
				atualizarLocalidadeActionForm.setNomeGerente("");
			}

			// atualizarLocalidadeActionForm
			// .setGerenciaID(String.valueOf(localidade
			// .getGerenciaRegional().getId()));

			// Unidade de Negócio
			if(localidade.getUnidadeNegocio() != null){
				atualizarLocalidadeActionForm.setIdUnidadeNegocio(String.valueOf(localidade.getUnidadeNegocio().getId()));
			}else{
				atualizarLocalidadeActionForm.setIdUnidadeNegocio(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			}

			// Pesquisa os elos realacionados com a gerência
			// regional da localidade
			// ================================================================
			if(SistemaParametro.INDICADOR_EMPRESA_ADA.equals(getParametroCompanhia(httpServletRequest))){
				FiltroLocalidade filtroElo = new FiltroLocalidade();
				filtroElo.setConsultaSemLimites(true);
				filtroElo.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
				filtroElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_UNIDADE_NEGOCIO, localidade.getUnidadeNegocio()
								.getId()));
				filtroElo.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroElo.adicionarParametro(new ComparacaoCampos(FiltroLocalidade.ID, "localidade"));

				// Retorna uma coleção de Elos
				colecaoPesquisa = this.getFachada().pesquisar(filtroElo, Localidade.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					httpServletRequest.setAttribute("colecaoElo", colecaoPesquisa);
				}
			}
			// ================================================================

			if(localidade.getLocalidade() != null){

				atualizarLocalidadeActionForm.setEloID(String.valueOf(localidade.getLocalidade().getId()));
			}

			/*
			 * if(localidade.getLocalidade() != null && localidade.getLocalidade().getId() != null){
			 * 
			 * atualizarLocalidadeActionForm.setEloID(String.valueOf(localidade.getLocalidade().getId
			 * ()));
			 * }else{
			 * atualizarLocalidadeActionForm.setEloID(String.valueOf(ConstantesSistema.
			 * NUMERO_NAO_INFORMADO));
			 * }
			 */

			if(localidade.getLocalidadeClasse() != null && localidade.getLocalidadeClasse().getId() != null){
				atualizarLocalidadeActionForm.setClasseID(String.valueOf(localidade.getLocalidadeClasse().getId()));
			}else{
				atualizarLocalidadeActionForm.setClasseID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			}

			if(localidade.getLocalidadePorte() != null && localidade.getLocalidadePorte().getId() != null){
				atualizarLocalidadeActionForm.setPorteID(String.valueOf(localidade.getLocalidadePorte().getId()));
			}else{
				atualizarLocalidadeActionForm.setPorteID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			}

			if(localidade.getIndicadorUso() != null){
				atualizarLocalidadeActionForm.setIndicadorUso(String.valueOf(localidade.getIndicadorUso()));
			}else{
				atualizarLocalidadeActionForm.setIndicadorUso(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			}

			String indicadorConcessionaria = null;
			try{
				indicadorConcessionaria = ParametroCadastro.P_INDICADOR_CONCESSIONARIA.executar();
			}catch(ControladorException e){
				throw new IllegalArgumentException("erro.sistema");
			}

			ConcessionariaLocalidade concessionariaLocalidade = null;

			if(!ConstantesSistema.NUMERO_NAO_INFORMADO_STRING.equals(indicadorConcessionaria)){
				FiltroConcessionariaLocalidade filtroConcessionaria = new FiltroConcessionariaLocalidade();
				filtroConcessionaria.adicionarCaminhoParaCarregamentoEntidade(FiltroConcessionariaLocalidade.CONCESSIONARIA);
				filtroConcessionaria.adicionarCaminhoParaCarregamentoEntidade(FiltroConcessionariaLocalidade.LOCALIDADE);
				filtroConcessionaria.adicionarParametro(new ParametroSimples(FiltroConcessionariaLocalidade.ID_LOCALIDADE, localidadeID));

				filtroConcessionaria.adicionarParametro(new ParametroNulo(FiltroConcessionariaLocalidade.DATA_VIGENCIA_FIM));

				colecaoPesquisa = this.getFachada().pesquisar(filtroConcessionaria, ConcessionariaLocalidade.class.getName());

				concessionariaLocalidade = (ConcessionariaLocalidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

				if(concessionariaLocalidade != null){
					atualizarLocalidadeActionForm.setConcessionariaId(concessionariaLocalidade.getConcessionaria().getId());
					atualizarLocalidadeActionForm.setConcessionariaIdOriginal(concessionariaLocalidade.getConcessionaria().getId());
					atualizarLocalidadeActionForm.setDataVigenciaInicioConcessionaria(Util.formatarData(concessionariaLocalidade
									.getDataVigenciaInicio()));
					atualizarLocalidadeActionForm.setDataVigenciaInicioConcessionariaOriginal(Util.formatarData(concessionariaLocalidade
									.getDataVigenciaInicio()));
				}
			}

			if(localidade.getGerenciaRegional() != null){
				atualizarLocalidadeActionForm.setGerenciaRegionalID(String.valueOf(localidade.getGerenciaRegional().getId()));
			}else{
				atualizarLocalidadeActionForm.setGerenciaRegionalID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			}

			if(localidade.getMunicipio() != null){
				atualizarLocalidadeActionForm.setMunicipioID(String.valueOf(localidade.getMunicipio().getId()));
			}else{
				atualizarLocalidadeActionForm.setMunicipioID(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			}

			if(localidade.getCodigoLocalidadeCEF() != null){
				atualizarLocalidadeActionForm.setLocalidadeCEF(String.valueOf(localidade.getCodigoLocalidadeCEF()));
			}else{
				atualizarLocalidadeActionForm.setLocalidadeCEF("");
			}

			if(localidade.getNumeroUltimaQuadraCadastrada() != null){
				atualizarLocalidadeActionForm.setUltimaQuadra(String.valueOf(localidade.getNumeroUltimaQuadraCadastrada()));
			}else{
				atualizarLocalidadeActionForm.setUltimaQuadra("");
			}

			if(localidade.getCodigoLocalidadeContabil() != null){
				atualizarLocalidadeActionForm.setLocalidadeContabil(String.valueOf(localidade.getCodigoLocalidadeContabil()));
			}else{
				atualizarLocalidadeActionForm.setLocalidadeContabil("");
			}

			if(localidade.getIndicadorAbastecimentoMinimo() != null
							&& localidade.getIndicadorAbastecimentoMinimo().equals(ConstantesSistema.SIM.intValue())){
				atualizarLocalidadeActionForm.setIndicadorAbastecimentoMinimo(true);
			}else{
				atualizarLocalidadeActionForm.setIndicadorAbastecimentoMinimo(false);
			}

			if(localidade.getIndicardoAbastecimentoSuspenso() != null
							&& localidade.getIndicardoAbastecimentoSuspenso().equals(ConstantesSistema.SIM.intValue())){
				atualizarLocalidadeActionForm.setIndicadorAbastecimentoSuspenso(true);
			}else{
				atualizarLocalidadeActionForm.setIndicadorAbastecimentoSuspenso(false);
			}

			Collection endereco = new Vector();
			endereco.add(localidade);

			// Coloca o atual endereço na sessao
			if(localidade.getLogradouroCep() != null){
				sessao.setAttribute("colecaoEnderecos", endereco);
			}else{
				sessao.removeAttribute("colecaoEnderecos");
			}

		}

	}

	/**
	 * Pesquisa Funcionario
	 * 
	 * @author Rafael Pinto
	 * @date 15/08/2006
	 */
	private void pesquisarFuncionario(AtualizarLocalidadeActionForm form){

		String gerenteLocalidade = form.getGerenteLocalidade();

		if(gerenteLocalidade != null && !gerenteLocalidade.equals("")){
			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, Util.obterInteger(gerenteLocalidade)));

			// Pesquisa de acordo com os parâmetros informados no filtro
			Collection colecaoFuncionario = this.getFachada().pesquisar(filtroFuncionario, Funcionario.class.getName());

			// Verifica se a pesquisa retornou algum objeto para a coleção
			if(colecaoFuncionario != null && !colecaoFuncionario.isEmpty()){
				// Obtém o objeto da coleção pesquisada
				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				form.setGerenteLocalidade(funcionario.getId().toString());
				form.setNomeGerente(funcionario.getNome());

			}else{
				form.setGerenteLocalidade("");
				form.setNomeGerente("Cliente inexistente");
			}
		}else{
			form.setNomeGerente("");
		}
	}

}
