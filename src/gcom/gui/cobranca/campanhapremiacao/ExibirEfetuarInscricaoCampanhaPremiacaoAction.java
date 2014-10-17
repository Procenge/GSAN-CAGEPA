/**
 * 
 */
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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.cadastro.cliente.*;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.campanhapremiacao.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.IoUtil;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

/**
 * @author Hiroshi Goncalves
 * @date 09/09/2013
 */
public class ExibirEfetuarInscricaoCampanhaPremiacaoAction
				extends GcomAction {

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("efetuarInscricaoCampanhaPremiacao");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		EfetuarInscricaoCampanhaPremiacaoActionForm form = (EfetuarInscricaoCampanhaPremiacaoActionForm) actionForm;

		Campanha campanha = null;

		sessao.setAttribute("indicadorExibirBotaoEmitirComprovante", ConstantesSistema.NAO);

		if(form.getBotaoClicado() == null || form.getBotaoClicado().equals("")){

		// O sistema obtém os dados da campanha de premiação mais recente da empresa
		FiltroCampanha filtroCampanha = new FiltroCampanha(FiltroCampanha.ULTIMA_ALTERACAO + " desc");

		Collection colecaoCampanha = fachada.pesquisar(filtroCampanha, Campanha.class.getName(), Campanha.class.getSimpleName());

		if(!colecaoCampanha.isEmpty()){
				campanha = (Campanha) Util.retonarObjetoDeColecao(colecaoCampanha);

				if(form.getIdImovel() == null && httpServletRequest.getParameter("idImovel") != null){
					form.setIdImovel(httpServletRequest.getParameter("idImovel"));
				}

			// Verificar inscrição do imóvel na campanha
			if(form.getIdImovel() != null && !form.getIdImovel().equals("")){
				ArrayList alRetornoCampanhaCadastro = (ArrayList) fachada.verificarInscricaoImovelCampanha(form.getIdImovel(), campanha);

				// Se o imóvel já está inscrito na campanha
				// Consulta
				if(alRetornoCampanhaCadastro != null){

					CampanhaCadastro campanhaCadastro = (CampanhaCadastro) alRetornoCampanhaCadastro.get(0);
					Collection<CampanhaCadastroFone> colCampanhaCadastroFone = (Collection<CampanhaCadastroFone>) alRetornoCampanhaCadastro
									.get(1);

						Short indicadorExibirBotaoEmitirComprovante = (Short) alRetornoCampanhaCadastro.get(2);

						sessao.setAttribute("indicadorExibirBotaoEmitirComprovante", indicadorExibirBotaoEmitirComprovante);

						// Utilizado na EmitirComprovanteInscricaoCampanhaAction
						sessao.setAttribute("campanhaCadastro", campanhaCadastro);

					this.preencherForm(campanhaCadastro, form);

						if(campanhaCadastro.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.NAO)){
							sessao.setAttribute("indicadorExibirInscricao", ConstantesSistema.SIM);
						}else{
							sessao.removeAttribute("indicadorExibirInscricao");
						}

					Collection colecaoClienteFone = this.montarCollectionClienteFone(colCampanhaCadastroFone);

					sessao.setAttribute("indicadorConsulta", ConstantesSistema.SIM);
					sessao.setAttribute("colecaoClienteFone", colecaoClienteFone);

				}else{

					

					// Validar CPF - CNPJ
					if(httpServletRequest.getParameter("indicadorVerificarDocumentoImpedido") != null){

							Integer tipoDocumento = fachada.verificarDocumentoImpedido(Integer.valueOf(campanha.getId()), form.getNuCPF(),
											form.getNuCNPJ());

							if(tipoDocumento != null){
								if(tipoDocumento.equals(ConstantesSistema.DOCUMENTO_IMPEDIDO_CPF)){
									throw new ActionServletException("atencao.campanhapremiacao.documento_impedido_cpf");
								}else if(tipoDocumento.equals(ConstantesSistema.DOCUMENTO_IMPEDIDO_CNPJ)){
									throw new ActionServletException("atencao.campanhapremiacao.documento_impedido_cnpj");
								}
							}

					}else{
						sessao.setAttribute("indicadorConsulta", ConstantesSistema.NAO);
						sessao.removeAttribute("indicadorExibirInscricao");
						sessao.removeAttribute("colecaoClienteFone");
						
						form.reset(actionMapping, httpServletRequest);
					}
				}

				// Caso o imóvel seja da categoria residencial e tenha até 2 (duas) economias
				// (IMOV_QTECONOMIA menor ou igual a 2 (dois)):
				// Ativa-se o indicador de residencial
				Categoria categoriaPrincipalImovel = fachada.obterPrincipalCategoriaImovel(Integer.valueOf(form.getIdImovel()));
				int qtEconomiasImovel = fachada.obterQuantidadeEconomias(new Imovel(Integer.valueOf(form.getIdImovel())));

				if(categoriaPrincipalImovel.getId().equals(Categoria.RESIDENCIAL) && qtEconomiasImovel <= 2){
					sessao.setAttribute("indicadorResidencial", ConstantesSistema.SIM);
				}else{
					sessao.setAttribute("indicadorResidencial", ConstantesSistema.NAO);
				}

				String dsEndereco = "";

				try{
					dsEndereco = fachada.pesquisarEnderecoFormatado(Integer.valueOf(form.getIdImovel()));
				}catch(ControladorException e){
					// Nunca vai ocorrer porque a própria classe Fachada trata esta Exceção.
				}
				form.setEndereco(dsEndereco);

					// Utilizado nos campos auxiliares para verificar se o valor foi mudado.
					httpServletRequest.setAttribute("idImovel", form.getIdImovel());
					httpServletRequest.setAttribute("nuCPF", form.getNuCPF());
					httpServletRequest.setAttribute("nuCNPJ", form.getNuCNPJ());

			}

				sessao.setAttribute("campanha", campanha);
				form.setTituloCampanha(campanha.getDsTituloCampanha());

				// Total de Inscrições Registradas, Total de Inscrições Bloqueadas
				FiltroCampanhaCadastro filtroCampanhaCadastro = new FiltroCampanhaCadastro();
				filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.CAMPANHA_ID, campanha.getId()));

				// Total de Inscrições Registradas
				int qtInscricoesRegistradas = fachada.totalRegistrosPesquisa(filtroCampanhaCadastro, CampanhaCadastro.class.getName());
				form.setInscricoesRegistradas(qtInscricoesRegistradas + "");

				// Total de Inscrições Bloqueadas
				filtroCampanhaCadastro.adicionarParametro(new ParametroSimples(FiltroCampanhaCadastro.INDICADOR_COMPROVANTE_BLOQUEADO,
								ConstantesSistema.SIM));
				int qtInscricoesBloqueadas = fachada.totalRegistrosPesquisa(filtroCampanhaCadastro, CampanhaCadastro.class.getName());
				form.setInscricoesBloqueadas(qtInscricoesBloqueadas + "");

				form.setTxRegulamentoCampanha(IoUtil.lerConteudoCampoBlobTipoTxt(Hibernate.createBlob(campanha.getRegulamentoCampanha()))
								.toString());
		}


		// Inicializa a coleção de FoneTipo
		Collection foneTipos = null;
		ClienteFone clienteFone = null;
		Collection colDdds = null;
		Collection colOrgaoExpedidor = null;
		Collection colEstados = null;

		if(httpServletRequest.getParameter("menu") != null && httpServletRequest.getParameter("menu").equals((String) "sim")){
			sessao.removeAttribute("foneTipos");
			sessao.removeAttribute("colDdds");
			sessao.removeAttribute("colOrgaoExpedidorRg");
			sessao.removeAttribute("colecaoClienteFone");
			sessao.removeAttribute("telefoneJaExistente");
			sessao.removeAttribute("indicadorResidencial");
			sessao.removeAttribute("colEstados");
			sessao.removeAttribute("campanhaCadastro");
			sessao.setAttribute("indicadorConsulta", ConstantesSistema.SIM);
			sessao.removeAttribute("colecaoClienteFone");

		}

		// Filtro de tipos de telefone
		FiltroFoneTipo filtroFoneTipo = new FiltroFoneTipo();

		filtroFoneTipo.adicionarParametro(new ParametroSimples(FiltroFoneTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Realiza a pesquisa de tipos de telefone
		foneTipos = fachada.pesquisar(filtroFoneTipo, FoneTipo.class.getName());

		if(foneTipos == null || foneTipos.isEmpty()){
			// Nenhum tipo de telefone cadastrado
			new ActionServletException("erro.naocadastrado", null, "tipo de telefone");

		}else{
			// Envia os objetos no request
			sessao.setAttribute("foneTipos", foneTipos);
		}

		FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
		if(sessao.getAttribute("colDdds") == null){

			filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroMunicipio.setCampoOrderBy(FiltroMunicipio.DDD, FiltroMunicipio.NOME);

			colDdds = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

			sessao.setAttribute("colDdds", colDdds);
		}

		if(sessao.getAttribute("colEstados") == null){
			FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
			filtroUnidadeFederacao.setCampoOrderBy(FiltroUnidadeFederacao.SIGLA);

			colEstados = fachada.pesquisar(filtroUnidadeFederacao, UnidadeFederacao.class.getName());
			sessao.setAttribute("colEstados", colEstados);

		}

		FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
		if(sessao.getAttribute("colOrgaoExpedidorRg") == null){

			filtroOrgaoExpedidorRg.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroOrgaoExpedidorRg.setCampoOrderBy(FiltroOrgaoExpedidorRg.DESCRICAO);

			colOrgaoExpedidor = fachada.pesquisar(filtroOrgaoExpedidorRg, OrgaoExpedidorRg.class.getName());

			sessao.setAttribute("colOrgaoExpedidorRg", colOrgaoExpedidor);
		}
		
		} else {
		Collection colecaoClienteFone = (Collection) sessao.getAttribute("colecaoClienteFone");
		
		if(colecaoClienteFone == null){
			colecaoClienteFone = new ArrayList();
		}

		if(form.getBotaoClicado() != null && !form.getBotaoClicado().equals("")){
			if((form.getBotaoClicado().equals("ADICIONAR"))
							&& (form.getDdd() != null && !((String) form.getDdd()).trim().equalsIgnoreCase(""))
							&& (form.getIdTipoTelefone() != null && !((String) form.getIdTipoTelefone()).trim().equalsIgnoreCase(""))
							&& (form.getTelefone() != null && !((String) form.getTelefone()).trim().equalsIgnoreCase(""))){

				ClienteFone clienteFone = new ClienteFone();
				clienteFone.setDdd((String) form.getDdd());
				clienteFone.setTelefone((String) form.getTelefone());

				if(form.getRamal() != null && !((String) form.getRamal()).trim().equalsIgnoreCase("")){
					clienteFone.setRamal((String) form.getRamal());
				}

				FoneTipo foneTipo = new FoneTipo();

				foneTipo.setId(new Integer(form.getIdTipoTelefone().toString()));
				foneTipo.setDescricao(form.getTextoSelecionado().toString());

				clienteFone.setFoneTipo(foneTipo);
				clienteFone.setUltimaAlteracao(new Date());

				// Verifica se o telefone já existe na coleção
				if(!colecaoClienteFone.contains(clienteFone)){
					colecaoClienteFone.add(clienteFone);
				}else{
					httpServletRequest.setAttribute("telefoneJaExistente", true);
				}

				form.setBotaoAdicionar(null);
				form.setBotaoClicado(null);
				form.setDdd(null);
				form.setTelefone(null);
				form.setIdTipoTelefone(null);
				form.setIdMunicipioFone(null);
				form.setRamal(null);

			}else if((form.getBotaoClicado().equals("REMOVER"))
							&& (form.getIndexTelefone() != null && !((String) form.getIndexTelefone()).trim().equalsIgnoreCase(""))){

				colecaoClienteFone.remove(((ArrayList) colecaoClienteFone).get(Integer.parseInt(form.getIndexTelefone()) - 1));
			}
		}

		sessao.setAttribute("colecaoClienteFone", colecaoClienteFone);

		// Limpa a indicação que o botão adicionar foi clicado
		form.setBotaoClicado("");

		// Se a coleção de telefones tiver apenas um item, então este item tem
		// que estar selecionado
		// como telefone principal
		Iterator iterator = colecaoClienteFone.iterator();
		
		ClienteFone clienteFone = null;
		if(colecaoClienteFone != null && colecaoClienteFone.size() == 1){

			clienteFone = (ClienteFone) iterator.next();

			form.setIndicadorTelefonePadrao(new Long(obterTimestampIdObjeto(clienteFone)).toString());

		}

		}


		return retorno;

	}

	private void preencherForm(CampanhaCadastro campanhaCadastro, EfetuarInscricaoCampanhaPremiacaoActionForm form){

		form.setNomeCliente(campanhaCadastro.getNomeCliente());
		form.setTipoRelacao(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().toString());
		form.setNuCPF(campanhaCadastro.getNumeroCPF());
		form.setNuRG(campanhaCadastro.getNumeroRG());
		form.setDtEmissaoRG(Util.formatarData(campanhaCadastro.getDataRGEmissao()));
		if(campanhaCadastro.getOrgaoExpedidorRG() != null){
		form.setOrgaoExpedidorRG(campanhaCadastro.getOrgaoExpedidorRG().getId().toString());
		}
		if(campanhaCadastro.getUnidadeFederacao() != null){
		form.setEstado(campanhaCadastro.getUnidadeFederacao().getId().toString());
		}
		form.setDtNascimento(Util.formatarData(campanhaCadastro.getDataNascimento()));
		form.setNomeMae(campanhaCadastro.getNomeMae());
		form.setEmail(campanhaCadastro.getDsEmail());
		form.setInscricaoCampanha(campanhaCadastro.getNumeroInscricao());
		form.setNuCNPJ(campanhaCadastro.getNumeroCNPJ());
	}

	private Collection montarCollectionClienteFone(Collection<CampanhaCadastroFone> colCampanhaCadastroFone){

		Collection colClienteFone = new ArrayList();

		for(CampanhaCadastroFone campanhaCadastroFone : colCampanhaCadastroFone){
			ClienteFone clienteFone = new ClienteFone();

			clienteFone.setFoneTipo(campanhaCadastroFone.getFoneTipo());
			clienteFone.setDdd(campanhaCadastroFone.getCodigoDDD());
			clienteFone.setTelefone(campanhaCadastroFone.getNumeroFone());
			clienteFone.setUltimaAlteracao(campanhaCadastroFone.getUltimaAlteracao());

			if(campanhaCadastroFone.getNumeroFoneRamal() != null){
				clienteFone.setRamal(campanhaCadastroFone.getNumeroFoneRamal());
			}

			colClienteFone.add(clienteFone);

		}

		return colClienteFone;
	}

}
