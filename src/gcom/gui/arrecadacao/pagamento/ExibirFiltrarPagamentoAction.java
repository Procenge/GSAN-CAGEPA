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

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.pagamento.FiltroPagamentoSituacao;
import gcom.arrecadacao.pagamento.PagamentoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.FiltroCategoria;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoTipo;
import gcom.faturamento.debito.FiltroDebitoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

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
 * Cria um filtro que ser� usado na pesquisa de pagamentos
 * [UC0255] Filtrar Pagamentos
 * 
 * @author Tiago Moreno, Roberta Costa ,Vivianne Sousa,Rafael Santos
 * @date 31/01/2006, 05/05/2003,07/10/2006
 */
public class ExibirFiltrarPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("filtrarPagamento");

		// Instacia a fachada
		Fachada fachada = Fachada.getInstancia();

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o formul�rio
		ConsultarPagamentoActionForm consultarPagamentoActionForm = (ConsultarPagamentoActionForm) actionForm;

		String tela = httpServletRequest.getParameter("tela");

		// Verifica se o im�vel � v�lido
		String idImovel = consultarPagamentoActionForm.getIdImovel();
		if(idImovel != null && !idImovel.equals("")){
			FiltroImovel filtroImovel = new FiltroImovel();
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("localidade");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("quadra");
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));

			Collection imoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(imoveis != null && !imoveis.isEmpty()){
				Imovel imovel = (Imovel) ((List) imoveis).get(0);
				httpServletRequest.setAttribute("imovel", imovel);
				consultarPagamentoActionForm.setInscricao(imovel.getInscricaoFormatada());
			}else{
				httpServletRequest.setAttribute("matriculaInexistente", "true");
				consultarPagamentoActionForm.setIdImovel("");
				consultarPagamentoActionForm.setInscricao("MATR�CULA INEXISTENTE");
			}
		}else if(idImovel != null && idImovel.equals("")
						&& consultarPagamentoActionForm.getInscricao().equalsIgnoreCase("MATR�CULA INEXISTENTE")){
			httpServletRequest.setAttribute("matriculaInexistente", "true");
		}

		// Verifica se o CLIENTE � v�lido
		String idCliente = consultarPagamentoActionForm.getIdCliente();

		if(idCliente != null && !idCliente.equals("")){
			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

			Collection clientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(clientes != null && !clientes.isEmpty()){
				Cliente cliente = (Cliente) ((List) clientes).get(0);
				consultarPagamentoActionForm.setNomeCliente(cliente.getNome());
				sessao.setAttribute("cliente", cliente);
			}else{
				httpServletRequest.setAttribute("clienteInexistente", "true");
				consultarPagamentoActionForm.setIdCliente("");
				consultarPagamentoActionForm.setNomeCliente("C�DIGO DE CLIENTE INEXISTENTE");
			}
		}else if(idCliente != null && idCliente.equals("")
						&& consultarPagamentoActionForm.getNomeCliente().equalsIgnoreCase("C�DIGO DE CLIENTE INEXISTENTE")){
			httpServletRequest.setAttribute("clienteInexistente", "true");
		}

		// Verifica se a LOCALIDADE � v�lida
		String idLocalidadeInicial = consultarPagamentoActionForm.getLocalidadeInicial();
		if(idLocalidadeInicial != null && !idLocalidadeInicial.trim().equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeInicial));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
				Localidade localidade = (Localidade) colecaoLocalidade.iterator().next();

				consultarPagamentoActionForm.setLocalidadeInicial(idLocalidadeInicial);
				consultarPagamentoActionForm.setDescricaoLocalidadeInicial(localidade.getDescricao());

			}else{
				consultarPagamentoActionForm.setLocalidadeInicial("");
				consultarPagamentoActionForm.setDescricaoLocalidadeInicial("LOCALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("localidadeInexistente", true);
			}
		}

		String idLocalidadeFinal = consultarPagamentoActionForm.getLocalidadeFinal();
		if(idLocalidadeFinal != null && !idLocalidadeFinal.trim().equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidadeFinal));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){
				Localidade localidade = (Localidade) colecaoLocalidade.iterator().next();

				consultarPagamentoActionForm.setLocalidadeFinal(idLocalidadeFinal);
				consultarPagamentoActionForm.setDescricaoLocalidadeFinal(localidade.getDescricao());

			}else{
				consultarPagamentoActionForm.setLocalidadeFinal("");
				consultarPagamentoActionForm.setDescricaoLocalidadeFinal("LOCALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("localidadeInexistente", true);
			}
		}

		if(!Util.isVazioOuBranco(idLocalidadeInicial) && !Util.isVazioOuBranco(idLocalidadeFinal)
						&& idLocalidadeInicial.equals(idLocalidadeFinal)){
			httpServletRequest.setAttribute("habilitaSetor", "true");

			Collection<SetorComercial> colecaoSetorComercial = null;
			FiltroSetorComercial filtroSetorComercial = null;

			String codigoSetorComercialInicial = consultarPagamentoActionForm.getCodigoSetorComercialInicial();

			if(!Util.isVazioOuBranco(codigoSetorComercialInicial)){
				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Util
								.obterInteger(idLocalidadeInicial)));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Util
								.obterShort(codigoSetorComercialInicial)));

				colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(Util.isVazioOrNulo(colecaoSetorComercial)){
					httpServletRequest.setAttribute("setorComercialInicialInexistente", "true");

					consultarPagamentoActionForm.setCodigoSetorComercialInicial("");
					consultarPagamentoActionForm.setDescricaoSetorComercialInicial("SETOR COMERCIAL INEXISTENTE");
				}else{
					SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
					String descricaoSetorComercial = setorComercial.getDescricao();

					consultarPagamentoActionForm.setCodigoSetorComercialInicial(codigoSetorComercialInicial);
					consultarPagamentoActionForm.setDescricaoSetorComercialInicial(descricaoSetorComercial);
				}
			}

			String codigoSetorComercialFinal = consultarPagamentoActionForm.getCodigoSetorComercialFinal();

			if(!Util.isVazioOuBranco(codigoSetorComercialFinal)){
				filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, Util
								.obterInteger(idLocalidadeFinal)));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Util
								.obterShort(codigoSetorComercialFinal)));

				colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(Util.isVazioOrNulo(colecaoSetorComercial)){
					httpServletRequest.setAttribute("setorComercialFinalInexistente", "true");

					consultarPagamentoActionForm.setCodigoSetorComercialFinal("");
					consultarPagamentoActionForm.setDescricaoSetorComercialFinal("SETOR COMERCIAL INEXISTENTE");
				}else{
					SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);
					String descricaoSetorComercial = setorComercial.getDescricao();

					consultarPagamentoActionForm.setCodigoSetorComercialFinal(codigoSetorComercialFinal);
					consultarPagamentoActionForm.setDescricaoSetorComercialFinal(descricaoSetorComercial);
				}
			}
		}else{
			httpServletRequest.setAttribute("habilitaSetor", "false");

			consultarPagamentoActionForm.setCodigoSetorComercialInicial("");
			consultarPagamentoActionForm.setDescricaoSetorComercialInicial("");

			consultarPagamentoActionForm.setCodigoSetorComercialFinal("");
			consultarPagamentoActionForm.setDescricaoSetorComercialFinal("");
		}

		String operacao = httpServletRequest.getParameter("operacao");

		Collection<Arrecadador> colecaoArrecadadores = (Collection<Arrecadador>) sessao.getAttribute("colecaoArrecadadores");

		String codigoArrecadadorAuxiliar = consultarPagamentoActionForm.getCodigoArrecadadorAuxiliar();

		if(!Util.isVazioOuBranco(codigoArrecadadorAuxiliar)){
			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, Util
							.obterInteger(codigoArrecadadorAuxiliar)));
			filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade(FiltroArrecadador.CLIENTE);

			Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());

			if(Util.isVazioOrNulo(colecaoArrecadador)){
				httpServletRequest.setAttribute("arrecadadorInexistente", "true");

				consultarPagamentoActionForm.setCodigoArrecadadorAuxiliar("");
				consultarPagamentoActionForm.setNomeArrecadadorAuxiliar("ARRECADADOR INEXISTENTE");
			}else{
				Arrecadador arrecadador = (Arrecadador) Util.retonarObjetoDeColecao(colecaoArrecadador);

				Integer idArrecadador = arrecadador.getId();

				String nomeArrecadadorAuxiliar = "";

				Cliente cliente = arrecadador.getCliente();

				if(cliente != null){
					nomeArrecadadorAuxiliar = cliente.getNome();
				}

				consultarPagamentoActionForm.setCodigoArrecadadorAuxiliar(codigoArrecadadorAuxiliar);
				consultarPagamentoActionForm.setNomeArrecadadorAuxiliar(nomeArrecadadorAuxiliar);

				if(!Util.isVazioOuBranco(operacao) && operacao.equals("adicionarArrecadador")){
					Short codigoArrecadadorAuxiliarShot = Short.valueOf(codigoArrecadadorAuxiliar);

					if(colecaoArrecadadores == null){
						colecaoArrecadadores = new ArrayList<Arrecadador>();
					}

					boolean incluir = true;

					if(!Util.isVazioOrNulo(colecaoArrecadadores)){
						Short codigoArrecadadorExistente = null;

						for(Arrecadador arrecadadorAuxiliar : colecaoArrecadadores){
							codigoArrecadadorExistente = arrecadadorAuxiliar.getCodigoAgente();

							if(codigoArrecadadorExistente.shortValue() == codigoArrecadadorAuxiliarShot.shortValue()){
								incluir = false;

								break;
							}
						}
					}

					if(incluir){
						Arrecadador arrecadadorAuxiliar = new Arrecadador();
						arrecadadorAuxiliar.setId(idArrecadador);
						arrecadadorAuxiliar.setCodigoAgente(codigoArrecadadorAuxiliarShot);

						Cliente clienteAuxiliar = new Cliente();
						clienteAuxiliar.setNome(nomeArrecadadorAuxiliar);

						arrecadadorAuxiliar.setCliente(cliente);

						colecaoArrecadadores.add(arrecadadorAuxiliar);
					}

					sessao.setAttribute("colecaoArrecadadores", colecaoArrecadadores);
				}
			}
		}

		if(!Util.isVazioOuBranco(operacao) && operacao.equals("removerArrecadador")){
			String codigoSelecionado = httpServletRequest.getParameter("codigo");

			if(!Util.isVazioOrNulo(colecaoArrecadadores) && !Util.isVazioOuBranco(codigoSelecionado)){
				Short codigoSelecionadoShort = Short.valueOf(codigoSelecionado);

				Arrecadador arrecadadorAuxiliar = null;
				Short codigoArrecadadorExistente = null;

				Iterator<Arrecadador> iterator = colecaoArrecadadores.iterator();

				while(iterator.hasNext()){
					arrecadadorAuxiliar = (Arrecadador) iterator.next();

					codigoArrecadadorExistente = arrecadadorAuxiliar.getCodigoAgente();

					if(codigoArrecadadorExistente.shortValue() == codigoSelecionadoShort.shortValue()){
						iterator.remove();

						break;
					}
				}

				sessao.setAttribute("colecaoArrecadadores", colecaoArrecadadores);
			}
		}

		String indicadorTotalizarPorDataPagamento = consultarPagamentoActionForm.getIndicadorTotalizarPorDataPagamento();

		if(Util.isVazioOuBranco(indicadorTotalizarPorDataPagamento)){
			consultarPagamentoActionForm.setIndicadorTotalizarPorDataPagamento(ConstantesSistema.NAO.toString());
		}

		// Pegando valores pra o combo Tipo de Relacao do Cliente
		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();
		filtroClienteRelacaoTipo.adicionarParametro(new ParametroSimples(FiltroClienteRelacaoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		Collection colecaoClienteRelacaoTipo = fachada.pesquisar(filtroClienteRelacaoTipo, ClienteRelacaoTipo.class.getName());
		sessao.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);

		// Pegando valores pra o combo Tipo de Situacao de Pagamentos
		FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();
		Collection colecaoPagamentoSituacao = fachada.pesquisar(filtroPagamentoSituacao, PagamentoSituacao.class.getName());
		sessao.setAttribute("colecaoPagamentoSituacao", colecaoPagamentoSituacao);

		// Pegando valores pra o combo FORMA DE ARRECADACAO
		FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();
		Collection colecaoArrecadacaoForma = fachada.pesquisar(filtroArrecadacaoForma, ArrecadacaoForma.class.getName());
		sessao.setAttribute("colecaoArrecadacaoForma", colecaoArrecadacaoForma);

		// Pegando valores pra o combo Tipo de D�bito
		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();
		filtroDebitoTipo.setConsultaSemLimites(true);
		Collection colecaoDebitoTipo = fachada.pesquisar(filtroDebitoTipo, DebitoTipo.class.getCanonicalName());
		sessao.setAttribute("colecaoDebitoTipo", colecaoDebitoTipo);

		// Pegando valores pra o combo Tipo de Documento
		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();
		Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());
		sessao.setAttribute("colecaoDocumentoTipo", colecaoDocumentoTipo);

		FiltroCategoria filtroCategoria = new FiltroCategoria();
		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

		Collection<Categoria> colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
		sessao.setAttribute("colecaoCategoria", colecaoDocumentoTipo);
		if(colecaoCategoria.isEmpty()){
			throw new ActionServletException("Atencao.Sem_dados_para_selecao");
		}
		sessao.setAttribute("colecaoCategoria", colecaoCategoria);

		if(httpServletRequest.getParameter("tela") != null){
			sessao.setAttribute("tela", tela);

			String primeiraVez = httpServletRequest.getParameter("menu");
			if(primeiraVez != null && !primeiraVez.equals("") && !"estorno".equals(tela)){
				sessao.setAttribute("indicadorAtualizar", "1");
			}
		}
		// se voltou da tela de Atualizar Pagamento
		if(sessao.getAttribute("voltar") != null && sessao.getAttribute("voltar").equals("filtrar")){
			sessao.setAttribute("indicadorAtualizar", "1");
		}

		// seta o valor para a op��o de pagamento
		if(consultarPagamentoActionForm.getOpcaoPagamento() == null){
			consultarPagamentoActionForm.setOpcaoPagamento("atual");
		}

		if("estorno".equals(tela)){
			consultarPagamentoActionForm.setOpcaoPagamento("historico");
		}

		return retorno;
	}
}