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

package gcom.gui.faturamento.conta;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.faturamento.bean.FiltrarImovelInserirManterContaHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ErroRepositorioException;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.rmi.RemoteException;
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

public class FiltrarImovelInserirManterContaAction
				extends GcomAction {

	/**
	 * @author eduardo henrique
	 * @date 08/01/2009
	 *       Adição do filtro de Bairro na seleção dos Imóveis
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirManterContaConjuntoImovel");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarImovelContaActionForm filtrarImovelContaActionForm = (FiltrarImovelContaActionForm) actionForm;

		Collection colecaoImovel = null;

		if(filtrarImovelContaActionForm.getCodigoCliente() != null && !filtrarImovelContaActionForm.getCodigoCliente().equals("")){
			Integer codigoCliente = Integer.parseInt(filtrarImovelContaActionForm.getCodigoCliente());
			// PESQUISAR CLIENTE
			String nomeCliente = "";
			if(codigoCliente != null && !codigoCliente.equals("")){
				nomeCliente = this.pesquisarCliente(codigoCliente.toString(), fachada);
			}

			Integer relacaoTipo = null;
			if(filtrarImovelContaActionForm.getTipoRelacao() != null
							&& !filtrarImovelContaActionForm.getTipoRelacao().trim().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				relacaoTipo = Integer.parseInt(filtrarImovelContaActionForm.getTipoRelacao());
			}

			colecaoImovel = fachada.pesquisarColecaoImovelCliente(codigoCliente, relacaoTipo);

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				throw new ActionServletException("atencao.imovel_inexistente_criterio_pesquisa");
			}

			sessao.setAttribute("colecaoImovel", colecaoImovel);

			sessao.setAttribute("codigoCliente", codigoCliente.toString());
			sessao.setAttribute("nomeCliente", nomeCliente);
			sessao.setAttribute("relacaoTipo", relacaoTipo);
			sessao.removeAttribute("inscricaoInicialImovel");
			sessao.removeAttribute("inscricaoDestinoImovel");

			sessao.removeAttribute("colecaoFaturamentoGrupo");

			if(sessao.getAttribute("quadraSelecionada") != null){
				sessao.removeAttribute("quadraSelecionada");
			}

		}else if(sessao.getAttribute("retornoArquivo") != null){
			Collection<Object[]> retornoArquivo = (Collection<Object[]>) sessao.getAttribute("retornoArquivo");
			colecaoImovel = new ArrayList();
			Collection<Conta> colecaoConta = new ArrayList();
			Collection<Conta> temp = null;
			Integer quantidadeConta = 0;
			for(Object[] item : retornoArquivo){
				Imovel imovel = (Imovel) item[0];
				if(!colecaoImovel.contains(imovel)){
					colecaoImovel.add(imovel);
				}
				Collection contaImovel = new ArrayList();
				contaImovel.add(imovel);
				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.IMOVEL, imovel));
				filtroConta.adicionarParametro(new Intervalo(FiltroConta.REFERENCIA, item[1], item[2]));
				temp = fachada.pesquisar(filtroConta, Conta.class.getName());
				for(Conta conta : temp){
					colecaoConta.add(conta);
				}
			}
			if(!Util.isVazioOrNulo(colecaoConta)){
				quantidadeConta = colecaoConta.size();
				sessao.setAttribute("colecaoContas", colecaoConta);
				sessao.setAttribute("colecaoImovel", colecaoImovel);
				sessao.setAttribute("arquivoQuantidadeContas", quantidadeConta);
			}

			sessao.removeAttribute("colecaoFaturamentoGrupo");

		}else{

			Imovel imovelInscricaoOrigem = new Imovel();

			// Dados da inscrição de origem
			Integer localidadeOrigemID = null;
			if(filtrarImovelContaActionForm.getLocalidadeOrigemID() != null
							&& !filtrarImovelContaActionForm.getLocalidadeOrigemID().equals("")){
				localidadeOrigemID = Integer.parseInt(filtrarImovelContaActionForm.getLocalidadeOrigemID());

				Localidade localidadeInscricaoOrigem = new Localidade();
				localidadeInscricaoOrigem.setId(localidadeOrigemID);

				imovelInscricaoOrigem.setLocalidade(localidadeInscricaoOrigem);
			}

			Integer setorComercialOrigemCD = null;
			SetorComercial setorComercialInscricaoOrigem = new SetorComercial();
			if(filtrarImovelContaActionForm.getSetorComercialOrigemCD() != null
							&& !filtrarImovelContaActionForm.getSetorComercialOrigemCD().equals("")){
				setorComercialOrigemCD = Integer.parseInt(filtrarImovelContaActionForm.getSetorComercialOrigemCD());

				setorComercialInscricaoOrigem.setCodigo(setorComercialOrigemCD);

				imovelInscricaoOrigem.setSetorComercial(setorComercialInscricaoOrigem);
			}else{

				setorComercialInscricaoOrigem.setCodigo(0);

				imovelInscricaoOrigem.setSetorComercial(setorComercialInscricaoOrigem);
			}

			Integer quadraOrigemNM = null;
			Quadra quadraInscricaoOrigem = new Quadra();
			if(filtrarImovelContaActionForm.getQuadraOrigemNM() != null && !filtrarImovelContaActionForm.getQuadraOrigemNM().equals("")){
				quadraOrigemNM = Integer.parseInt(filtrarImovelContaActionForm.getQuadraOrigemNM());

				quadraInscricaoOrigem.setNumeroQuadra(quadraOrigemNM);

				imovelInscricaoOrigem.setQuadra(quadraInscricaoOrigem);
			}else{

				quadraInscricaoOrigem.setNumeroQuadra(0);

				imovelInscricaoOrigem.setQuadra(quadraInscricaoOrigem);
			}

			short loteOrigem = 0;
			if(filtrarImovelContaActionForm.getLoteOrigem() != null && !filtrarImovelContaActionForm.getLoteOrigem().equalsIgnoreCase("")){
				loteOrigem = Short.parseShort(filtrarImovelContaActionForm.getLoteOrigem());

				imovelInscricaoOrigem.setLote(loteOrigem);

			}

			short subLoteOrigem = 0;
			if(filtrarImovelContaActionForm.getSubLoteOrigem() != null
							&& !filtrarImovelContaActionForm.getSubLoteOrigem().equalsIgnoreCase("")){
				subLoteOrigem = Short.parseShort(filtrarImovelContaActionForm.getSubLoteOrigem());

				imovelInscricaoOrigem.setSubLote(subLoteOrigem);
			}

			Integer codigoRotaOrigem = null;
			if(filtrarImovelContaActionForm.getCodigoRotaOrigem() != null
							&& !filtrarImovelContaActionForm.getCodigoRotaOrigem().equalsIgnoreCase("")){

				codigoRotaOrigem = new Integer(filtrarImovelContaActionForm.getCodigoRotaOrigem());
			}

			Integer sequencialRotaOrigem = null;
			if(filtrarImovelContaActionForm.getSequencialRotaOrigem() != null
							&& !filtrarImovelContaActionForm.getSequencialRotaOrigem().equalsIgnoreCase("")){

				sequencialRotaOrigem = new Integer(filtrarImovelContaActionForm.getSequencialRotaOrigem());
			}

			Localidade localidadeOrigem = (Localidade) validarCampo(localidadeOrigemID, null, 1);
			SetorComercial setorComercialOrigem = null;
			Quadra quadraOrigem = null;

			// Validação dos campos da inscrição de origem (FS0002, FS0003, FS0004)
			if(localidadeOrigem != null){

				if(setorComercialOrigemCD != null){

					setorComercialOrigem = (SetorComercial) validarCampo(localidadeOrigem.getId(), setorComercialOrigemCD.toString(), 2);

					if(setorComercialOrigem == null){
						throw new ActionServletException("atencao.pesquisa.setor_origem_inexistente");
					}

					if(quadraOrigemNM != null){

						quadraOrigem = (Quadra) validarCampo(setorComercialOrigem.getId(), quadraOrigemNM.toString(), 3);

						if(quadraOrigem == null){
							throw new ActionServletException("atencao.pesquisa.quadra_origem_inexistente");
						}
					}

				}
			}

			// Dados da inscrição de destino
			Imovel imovelInscricaoDestino = new Imovel();

			Integer localidadeDestinoID = null;
			if(filtrarImovelContaActionForm.getLocalidadeDestinoID() != null
							&& !filtrarImovelContaActionForm.getLocalidadeDestinoID().equals("")){
				localidadeDestinoID = Integer.parseInt(filtrarImovelContaActionForm.getLocalidadeDestinoID());

				Localidade localidadeInscricaoDestino = new Localidade();
				localidadeInscricaoDestino.setId(localidadeDestinoID);

				imovelInscricaoDestino.setLocalidade(localidadeInscricaoDestino);
			}

			Integer setorComercialDestinoCD = null;
			SetorComercial setorComercialInscricaoDestino = new SetorComercial();
			if(filtrarImovelContaActionForm.getSetorComercialDestinoCD() != null
							&& !filtrarImovelContaActionForm.getSetorComercialDestinoCD().equals("")){
				setorComercialDestinoCD = Integer.parseInt(filtrarImovelContaActionForm.getSetorComercialDestinoCD());

				setorComercialInscricaoDestino.setCodigo(setorComercialDestinoCD);

				imovelInscricaoDestino.setSetorComercial(setorComercialInscricaoDestino);
			}else{

				setorComercialInscricaoDestino.setCodigo(999);

				imovelInscricaoDestino.setSetorComercial(setorComercialInscricaoDestino);
			}

			// Remove as quadras selecionadas, caso não seja o mesmo setor comercial das inscrições
			if(setorComercialOrigemCD != null && setorComercialDestinoCD != null && !setorComercialOrigemCD.equals(setorComercialDestinoCD)){
				if(sessao.getAttribute("quadraSelecionada") != null){
					sessao.removeAttribute("quadraSelecionada");
				}
			}

			Integer quadraDestinoNM = null;
			Quadra quadraInscricaoDestino = new Quadra();
			if(filtrarImovelContaActionForm.getQuadraDestinoNM() != null && !filtrarImovelContaActionForm.getQuadraDestinoNM().equals("")){
				quadraDestinoNM = Integer.parseInt(filtrarImovelContaActionForm.getQuadraDestinoNM());

				quadraInscricaoDestino.setNumeroQuadra(quadraDestinoNM);

				imovelInscricaoDestino.setQuadra(quadraInscricaoDestino);
			}else{

				quadraInscricaoDestino.setNumeroQuadra(9999);

				imovelInscricaoDestino.setQuadra(quadraInscricaoDestino);
			}

			short loteDestino = 9999;
			if(filtrarImovelContaActionForm.getLoteDestino() != null && !filtrarImovelContaActionForm.getLoteDestino().equalsIgnoreCase("")){
				loteDestino = Short.parseShort(filtrarImovelContaActionForm.getLoteDestino());

				imovelInscricaoDestino.setLote(loteDestino);
			}else{
				imovelInscricaoDestino.setLote(loteDestino);
			}

			short subLoteDestino = 999;
			if(filtrarImovelContaActionForm.getSubLoteDestino() != null
							&& !filtrarImovelContaActionForm.getSubLoteDestino().equalsIgnoreCase("")){
				subLoteDestino = Short.parseShort(filtrarImovelContaActionForm.getSubLoteDestino());

				imovelInscricaoDestino.setSubLote(subLoteDestino);
			}else{
				imovelInscricaoDestino.setSubLote(subLoteDestino);
			}

			Integer codigoRotaDestino = null;
			if(filtrarImovelContaActionForm.getCodigoRotaDestino() != null
							&& !filtrarImovelContaActionForm.getCodigoRotaDestino().equalsIgnoreCase("")){

				codigoRotaDestino = new Integer(filtrarImovelContaActionForm.getCodigoRotaDestino());
			}

			Integer sequencialRotaDestino = null;
			if(filtrarImovelContaActionForm.getSequencialRotaDestino() != null
							&& !filtrarImovelContaActionForm.getSequencialRotaDestino().equalsIgnoreCase("")){

				sequencialRotaDestino = new Integer(filtrarImovelContaActionForm.getSequencialRotaDestino());
			}

			Localidade localidadeDestino = (Localidade) validarCampo(localidadeDestinoID, null, 1);
			SetorComercial setorComercialDestino = null;
			Quadra quadraDestino = null;

			// Validação dos campos da inscrição de destino (FS0002, FS0003,
			// FS0004)
			if(localidadeDestino != null){

				if(setorComercialDestinoCD != null){

					setorComercialDestino = (SetorComercial) validarCampo(localidadeDestino.getId(), setorComercialDestinoCD.toString(), 2);

					if(setorComercialDestino == null){
						// Nenhum Setor Comercial encontrado
						throw new ActionServletException("atencao.pesquisa.setor_destino_inexistente");
					}
					if(quadraDestinoNM != null){

						quadraDestino = (Quadra) validarCampo(setorComercialDestino.getId(), quadraDestinoNM.toString(), 3);

						if(quadraDestino == null){
							// Nenhuma Quadra encontrada
							throw new ActionServletException("atencao.pesquisa.quadra_destino_inexistente");
						}
					}
				}
			}

			/*
			 * Prepara os objetos para a pesquisa dos imoveis que estão
			 * localizados na inscrição de origem
			 */
			Integer quadraOrigemID = null;
			if(quadraOrigem != null){
				quadraOrigemID = quadraOrigem.getId();
			}

			Integer quadraDestinoID = null;
			if(quadraOrigem != null){
				quadraDestinoID = quadraDestino.getId();
			}

			Integer setorComercialOrigemID = null;
			if(setorComercialOrigem != null){
				setorComercialOrigemID = setorComercialOrigem.getId();
			}

			Integer setorComercialDestinoID = null;
			if(setorComercialDestino != null){
				setorComercialDestinoID = setorComercialDestino.getId();
			}

			String[] quadras = null;
			if(sessao.getAttribute("quadraSelecionada") != null){
				quadras = (String[]) sessao.getAttribute("quadraSelecionada");
			}

			// Faturamento Grupo
			Collection<Integer> colecaoFaturamentoGrupo = null;
			if(filtrarImovelContaActionForm.getFaturamentoGrupo() != null
							&& filtrarImovelContaActionForm.getFaturamentoGrupo().length > 0){

				colecaoFaturamentoGrupo = new ArrayList<Integer>();

				String[] faturamentoGrupo = filtrarImovelContaActionForm.getFaturamentoGrupo();
				for(int i = 0; i < faturamentoGrupo.length; i++){

					if(!Util.isVazioOuBranco(faturamentoGrupo[i])){

						colecaoFaturamentoGrupo.add(Integer.valueOf(faturamentoGrupo[i]));
					}
				}

			}else{
				sessao.removeAttribute("colecaoFaturamentoGrupo");
			}

			// Colecao Logradouro
			Collection<Logradouro> colecaoLogradouro = (Collection) sessao.getAttribute("colecaoLogradouro");
			Collection<Integer> colecaoIdLogradouro = null;
			if(!Util.isVazioOrNulo(colecaoLogradouro)){
				colecaoIdLogradouro = new ArrayList();
				Iterator it = colecaoLogradouro.iterator();
				while(it.hasNext()){
					Logradouro logradouro = (Logradouro) it.next();
					colecaoIdLogradouro.add(logradouro.getId());
				}
			}

			/*
			 * Colocado por Raphael Rossiter em 02/08/2007
			 * OBJETIVO: Acrescentar o parâmetro grupo de faturamento para o filtro de manutenção
			 * de várias contas.
			 */


			FiltrarImovelInserirManterContaHelper filtro = new FiltrarImovelInserirManterContaHelper();

			// Bairro
			if(filtrarImovelContaActionForm.getNomeBairro() != null
							&& !filtrarImovelContaActionForm.getNomeBairro().trim().equalsIgnoreCase("")){
				filtro.setNomeBairro(filtrarImovelContaActionForm.getNomeBairro());
			}

			if(localidadeOrigem != null){
				filtro.setLocalidadeOrigemID(localidadeOrigem.getId());
			}

			if(localidadeDestino != null){
				filtro.setLocalidadeDestinoID(localidadeDestino.getId());
			}

			filtro.setSetorComercialOrigemID(setorComercialOrigemID);
			filtro.setSetorComercialDestinoID(setorComercialDestinoID);

			filtro.setQuadraOrigemID(quadraOrigemID);
			filtro.setQuadraDestinoID(quadraDestinoID);

			filtro.setLoteOrigem(loteOrigem);
			filtro.setLoteDestino(loteDestino);

			filtro.setSubLoteOrigem(subLoteOrigem);
			filtro.setSubLoteDestino(subLoteDestino);

			filtro.setQuadras(quadras);

			filtro.setCodigoRotaOrigem(codigoRotaOrigem);
			filtro.setCodigoRotaDestino(codigoRotaDestino);

			filtro.setSequencialRotaOrigem(sequencialRotaOrigem);
			filtro.setSequencialRotaDestino(sequencialRotaDestino);

			/*
			 * Colocado por Raphael Rossiter em 02/08/2007
			 * OBJETIVO: Acrescentar o parâmetro grupo de faturamento para o filtro de manutenção
			 * de várias contas.
			 */

			filtro.setColecaoFaturamentoGrupo(colecaoFaturamentoGrupo);
			
			if(!Util.isVazioOrNulo(colecaoIdLogradouro)){
				filtro.setColecaoLogradouro(colecaoIdLogradouro);
			}

			colecaoImovel = fachada.pesquisarColecaoImovel(filtro);

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				throw new ActionServletException("atencao.imovel_inexistente_criterio_pesquisa");
			}

			sessao.setAttribute("colecaoImovel", colecaoImovel);

			if(imovelInscricaoOrigem.getLocalidade() != null){
				sessao.setAttribute("inscricaoInicialImovel", imovelInscricaoOrigem.getInscricaoFormatada());
			}else{
				sessao.removeAttribute("inscricaoInicialImovel");
			}

			if(imovelInscricaoDestino.getLocalidade() != null){
				sessao.setAttribute("inscricaoDestinoImovel", imovelInscricaoDestino.getInscricaoFormatada());
			}else{
				sessao.removeAttribute("inscricaoDestinoImovel");
			}

			if(colecaoFaturamentoGrupo != null){
				sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
			}else{
				sessao.removeAttribute("colecaoFaturamentoGrupo");
			}

			sessao.removeAttribute("codigoCliente");
			sessao.removeAttribute("nomeCliente");
		}

		// devolve o mapeamento de retorno
		return retorno;
	}


	/**
	 * Valida os valores digitados pelo usuário
	 * 
	 * @param campoDependencia
	 * @param dependente
	 * @param tipoObjeto
	 * @return Object
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	private Object validarCampo(Integer campoDependencia, String dependente, int tipoObjeto){

		Object objeto = null;
		Collection colecaoPesquisa;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		if(campoDependencia != null){

			if(dependente == null || tipoObjeto == 1){
				// Localidade
				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, campoDependencia));

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
					objeto = Util.retonarObjetoDeColecao(colecaoPesquisa);
				}
			}else if(dependente != null && !dependente.equalsIgnoreCase("") && tipoObjeto > 1){
				switch(tipoObjeto){
					// Setor Comercial
					case 2:
						FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, campoDependencia));

						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL,
										new Integer(dependente)));

						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

						if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
							objeto = Util.retonarObjetoDeColecao(colecaoPesquisa);
						}

						break;
					// Quadra
					case 3:
						FiltroQuadra filtroQuadra = new FiltroQuadra();

						// Objetos que serão retornados pelo hibernate
						filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");

						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, campoDependencia));

						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, new Integer(dependente)));

						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO,
										ConstantesSistema.INDICADOR_USO_ATIVO));

						colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

						if(colecaoPesquisa != null && !colecaoPesquisa.isEmpty()){
							objeto = Util.retonarObjetoDeColecao(colecaoPesquisa);
						}

						break;
					default:
						break;
				}
			}
		}

		return objeto;
	}

	/**
	 * Verifica a existência de imóveis no objeto passado como parâmetro Os
	 * objetos passados podem ser do tipo SetorComercial = 1 e Quadra = 2
	 * 
	 * @param objetoCondicao
	 * @param tipoObjeto
	 * @return um boleano
	 */
	/*
	 * private boolean existeImovel(Object objetoCondicao, int tipoObjeto) {
	 * boolean retorno = false;
	 * Collection colecaoPesquisa;
	 * //Obtém a instância da fachada
	 * Fachada fachada = Fachada.getInstancia();
	 * if (tipoObjeto == 1) {
	 * SetorComercial setorComercial = (SetorComercial) objetoCondicao;
	 * colecaoPesquisa = fachada.pesquisarImovel(null, setorComercial
	 * .getId(), null, null);
	 * if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
	 * retorno = true;
	 * }
	 * } else {
	 * Quadra quadra = (Quadra) objetoCondicao;
	 * colecaoPesquisa = fachada.pesquisarImovel(null, null, quadra
	 * .getId(), null);
	 * if (colecaoPesquisa != null && !colecaoPesquisa.isEmpty()) {
	 * retorno = true;
	 * }
	 * }
	 * return retorno;
	 * }
	 */
	/**
	 * Pesquisar Clientes
	 * 
	 * @param filtroCliente
	 * @param idCliente
	 * @param clientes
	 * @param form
	 * @param fachada
	 * @param httpServletRequest
	 */
	public String pesquisarCliente(String idCliente, Fachada fachada){

		FiltroCliente filtroCliente = new FiltroCliente();

		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection clienteEncontrado = fachada.pesquisar(filtroCliente, Cliente.class.getName());

		Cliente cliente = null;
		if(clienteEncontrado != null && !clienteEncontrado.isEmpty()){
			cliente = ((Cliente) ((List) clienteEncontrado).get(0));
			// O Cliente foi encontrado
			if(cliente.getIndicadorUso().equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){
				throw new ActionServletException("atencao.cliente.inativo", null, ""
								+ ((Cliente) ((List) clienteEncontrado).get(0)).getId());
			}

		}else{
			throw new ActionServletException("atencao.cliente.inexistente");
		}

		return cliente.getNome();
	}
}
