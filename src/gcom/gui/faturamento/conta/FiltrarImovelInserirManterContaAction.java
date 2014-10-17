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

package gcom.gui.faturamento.conta;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
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
	 *       Adi��o do filtro de Bairro na sele��o dos Im�veis
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirManterContaConjuntoImovel");

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
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
		}else{

			Imovel imovelInscricaoOrigem = new Imovel();

			// Dados da inscri��o de origem
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

			// Valida��o dos campos da inscri��o de origem (FS0002, FS0003, FS0004)
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

			// Dados da inscri��o de destino
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

			// Remove as quadras selecionadas, caso n�o seja o mesmo setor comercial das inscri��es
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

			// Valida��o dos campos da inscri��o de destino (FS0002, FS0003,
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
			 * Prepara os objetos para a pesquisa dos imoveis que est�o
			 * localizados na inscri��o de origem
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

			/*
			 * Colocado por Raphael Rossiter em 02/08/2007
			 * OBJETIVO: Acrescentar o par�metro grupo de faturamento para o filtro de manuten��o
			 * de v�rias contas.
			 */
			Integer idGrupoFaturamento = null;

			if(filtrarImovelContaActionForm.getIdFaturamentoGrupo() != null
							&& !filtrarImovelContaActionForm.getIdFaturamentoGrupo().equals("")){

				idGrupoFaturamento = Integer.parseInt(filtrarImovelContaActionForm.getIdFaturamentoGrupo());
			}

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
			 * OBJETIVO: Acrescentar o par�metro grupo de faturamento para o filtro de manuten��o
			 * de v�rias contas.
			 */
			filtro.setIdGrupoFaturamento(idGrupoFaturamento);

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

			if(idGrupoFaturamento != null){
				sessao.setAttribute("grupoFaturamento", idGrupoFaturamento.toString());
			}else{
				sessao.removeAttribute("grupoFaturamento");
			}

			sessao.removeAttribute("codigoCliente");
			sessao.removeAttribute("nomeCliente");
		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * Verifica a situa��o em que se encontra a rota que pertence a quadra
	 * passada como par�metro - n�o faturada = false e faturada = true
	 * 
	 * @param quadra
	 * @return um boleano
	 */

	/*
	 * private boolean verificarSituacaoRota(Quadra quadra) {
	 * boolean retorno = true;
	 * Collection colecaoPesquisa = null;
	 * SistemaParametro sistemaParametro = null;
	 * //Obt�m a inst�ncia da fachada
	 * Fachada fachada = Fachada.getInstancia();
	 * //Retorna o �nico objeto da tabela sistemaParametro
	 * sistemaParametro = fachada.pesquisarParametrosDoSistema();
	 * if (sistemaParametro == null) {
	 * retorno = false;
	 * throw new ActionServletException(
	 * "atencao.pesquisa.sistemaparametro_inexistente");
	 * } else {
	 * FiltroFaturamentoAtividadeCronograma filtroFaturamentoAtividadeCronograma = new
	 * FiltroFaturamentoAtividadeCronograma();
	 * filtroFaturamentoAtividadeCronograma
	 * .adicionarParametro(new ParametroSimples(
	 * 
	 * FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_FATURAMENTO_GRUPO_ID,
	 * quadra.getRota().getFaturamentoGrupo().getId()));
	 * filtroFaturamentoAtividadeCronograma
	 * .adicionarParametro(new ParametroSimples(
	 * FiltroFaturamentoAtividadeCronograma.FATURAMENTO_GRUPO_CRONOGRAMA_MENSAL_ANO_MES_REFERENCIA,
	 * new Integer(sistemaParametro.getAnoMesFaturamento())));
	 * //O valor do ID ser� fixo
	 * // =============================================
	 * filtroFaturamentoAtividadeCronograma
	 * .adicionarParametro(new ParametroSimples(
	 * FiltroFaturamentoAtividadeCronograma.FATURAMENTO_ATIVIDADE_ID,
	 * FaturamentoAtividade.FATURAR_GRUPO));
	 * //=====================================================================
	 * colecaoPesquisa = fachada.pesquisar(
	 * filtroFaturamentoAtividadeCronograma,
	 * FaturamentoAtividadeCronograma.class.getName());
	 * if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
	 * retorno = false;
	 * throw new ActionServletException(
	 * "atencao.pesquisa.faturamento_atividade_cronograma_inexistente");
	 * } else {
	 * FaturamentoAtividadeCronograma faturamentoAtividadeCronograma =
	 * (FaturamentoAtividadeCronograma) Util
	 * .retonarObjetoDeColecao(colecaoPesquisa);
	 * //Local da verifica��o da situa��o da Rota (Faturada ou n�o
	 * // Faturada)
	 * if (faturamentoAtividadeCronograma.getDataRealizacao() == null) {
	 * retorno = false;
	 * }
	 * }
	 * }
	 * return retorno;
	 * }
	 */

	/**
	 * @param objetoPesquisa
	 * @param objetoPai
	 * @param tipoObjeto
	 * @return
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	/*
	 * private boolean pesquisarObjeto(Object objetoPesquisa, Object objetoPai,
	 * int tipoObjeto) {
	 * boolean retorno = true;
	 * Collection colecaoPesquisa = null;
	 * SetorComercial setorComercial = null;
	 * //Obt�m a inst�ncia da fachada
	 * Fachada fachada = Fachada.getInstancia();
	 * switch (tipoObjeto) {
	 * //Setor Comercial
	 * case 1:
	 * Localidade localidade = (Localidade) objetoPai;
	 * setorComercial = (SetorComercial) objetoPesquisa;
	 * FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
	 * filtroSetorComercial.adicionarParametro(new ParametroSimples(
	 * FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));
	 * filtroSetorComercial.adicionarParametro(new ParametroSimples(
	 * FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, String
	 * .valueOf(setorComercial.getCodigo())));
	 * filtroSetorComercial.adicionarParametro(new ParametroSimples(
	 * FiltroSetorComercial.INDICADORUSO,
	 * ConstantesSistema.INDICADOR_USO_ATIVO));
	 * colecaoPesquisa = fachada.pesquisar(filtroSetorComercial,
	 * SetorComercial.class.getName());
	 * if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
	 * retorno = false;
	 * }
	 * break;
	 * //Quadra
	 * case 2:
	 * setorComercial = (SetorComercial) objetoPai;
	 * Quadra quadra = (Quadra) objetoPesquisa;
	 * FiltroQuadra filtroQuadra = new FiltroQuadra();
	 * filtroQuadra.adicionarParametro(new ParametroSimples(
	 * FiltroQuadra.ID_SETORCOMERCIAL, String
	 * .valueOf(setorComercial.getId())));
	 * filtroQuadra.adicionarParametro(new ParametroSimples(
	 * FiltroQuadra.NUMERO_QUADRA, String.valueOf(quadra
	 * .getNumeroQuadra())));
	 * filtroQuadra.adicionarParametro(new ParametroSimples(
	 * FiltroQuadra.INDICADORUSO,
	 * ConstantesSistema.INDICADOR_USO_ATIVO));
	 * colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class
	 * .getName());
	 * if (colecaoPesquisa == null || colecaoPesquisa.isEmpty()) {
	 * retorno = false;
	 * }
	 * break;
	 * default:
	 * break;
	 * }
	 * return retorno;
	 * }
	 */
	/*    *//**
	 * @param objetoPesquisa
	 * @param objetoPai
	 * @param tipoObjeto
	 * @return
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */
	/*
	 * private Object pesquisarRetornarObjeto(Object objetoPesquisa,
	 * Object objetoPai, int tipoObjeto) {
	 * Object retorno = null;
	 * Collection colecaoPesquisa = null;
	 * SetorComercial setorComercial = null;
	 * Quadra quadra = null;
	 * Imovel imovel = null;
	 * //Obt�m a inst�ncia da fachada
	 * Fachada fachada = Fachada.getInstancia();
	 * switch (tipoObjeto) {
	 * //Setor Comercial
	 * case 1:
	 * Localidade localidade = (Localidade) objetoPai;
	 * setorComercial = (SetorComercial) objetoPesquisa;
	 * FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
	 * filtroSetorComercial.adicionarParametro(new ParametroSimples(
	 * FiltroSetorComercial.ID_LOCALIDADE, localidade.getId()));
	 * filtroSetorComercial.adicionarParametro(new ParametroSimples(
	 * FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, String
	 * .valueOf(setorComercial.getCodigo())));
	 * filtroSetorComercial.adicionarParametro(new ParametroSimples(
	 * FiltroSetorComercial.INDICADORUSO,
	 * ConstantesSistema.INDICADOR_USO_ATIVO));
	 * colecaoPesquisa = fachada.pesquisar(filtroSetorComercial,
	 * SetorComercial.class.getName());
	 * if (colecaoPesquisa != null || !colecaoPesquisa.isEmpty()) {
	 * retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
	 * }
	 * break;
	 * //Quadra
	 * case 2:
	 * setorComercial = (SetorComercial) objetoPai;
	 * quadra = (Quadra) objetoPesquisa;
	 * FiltroQuadra filtroQuadra = new FiltroQuadra();
	 * //Objetos que ser�o retornados pelo hibernate
	 * filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("rota.faturamentoGrupo");
	 * filtroQuadra.adicionarParametro(new ParametroSimples(
	 * FiltroQuadra.ID_SETORCOMERCIAL, String
	 * .valueOf(setorComercial.getId())));
	 * filtroQuadra.adicionarParametro(new ParametroSimples(
	 * FiltroQuadra.NUMERO_QUADRA, String.valueOf(quadra
	 * .getNumeroQuadra())));
	 * filtroQuadra.adicionarParametro(new ParametroSimples(
	 * FiltroQuadra.INDICADORUSO,
	 * ConstantesSistema.INDICADOR_USO_ATIVO));
	 * colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class
	 * .getName());
	 * if (colecaoPesquisa != null || !colecaoPesquisa.isEmpty()) {
	 * retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
	 * }
	 * break;
	 * //Imovel
	 * case 3:
	 * quadra = (Quadra) objetoPai;
	 * imovel = (Imovel) objetoPesquisa;
	 * FiltroImovel filtroImovel = new FiltroImovel();
	 * filtroImovel.adicionarParametro(new ParametroSimples(
	 * FiltroImovel.QUADRA_ID, quadra.getId()));
	 * filtroImovel.adicionarParametro(new ParametroSimples(
	 * FiltroImovel.LOTE, new Short(imovel.getLote())));
	 * filtroImovel.adicionarParametro(new ParametroSimples(
	 * FiltroImovel.SUBLOTE, new Short(imovel.getSubLote())));
	 * colecaoPesquisa = fachada.pesquisar(filtroImovel, Imovel.class
	 * .getName());
	 * if (colecaoPesquisa != null || !colecaoPesquisa.isEmpty()) {
	 * retorno = Util.retonarObjetoDeColecao(colecaoPesquisa);
	 * }
	 * break;
	 * default:
	 * break;
	 * }
	 * return retorno;
	 * }
	 */
	/**
	 * Compara os objetos para validar suas referencias
	 * 
	 * @param origem
	 * @param destino
	 * @param tipoObjeto
	 * @return
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	/*
	 * private boolean compararObjetos(Object origem, Object destino,
	 * int tipoObjeto) {
	 * boolean retorno = true;
	 * //Collection colecaoImoveis;
	 * switch (tipoObjeto) {
	 * //Localidade
	 * case 1:
	 * Collection colecaoSetorOrigem = pesquisarDependentes(origem, 1);
	 * //Compara��o de setores
	 * if (colecaoSetorOrigem != null && !colecaoSetorOrigem.isEmpty()) {
	 * Collection colecaoSetorDestino = pesquisarDependentes(destino,
	 * 1);
	 * if (colecaoSetorDestino != null
	 * && !colecaoSetorDestino.isEmpty()) {
	 * Iterator itSetorOrigem = (colecaoSetorOrigem).iterator();
	 * Iterator itSetorDestino = null;
	 * Iterator itQuadraOrigem = null;
	 * Iterator itQuadraDestino = null;
	 * SetorComercial setorComercialOrigem = null;
	 * SetorComercial setorComercialDestino = null;
	 * Quadra quadraOrigem = null;
	 * Quadra quadraDestino = null;
	 * while (itSetorOrigem.hasNext()) {
	 * if (!retorno) {
	 * // Verifica a exist�ncia de im�veis no setor
	 * // comercial
	 * if (existeImovel(setorComercialOrigem, 1)) {
	 * break;
	 * }
	 * }
	 * setorComercialOrigem = (SetorComercial) itSetorOrigem
	 * .next();
	 * //Colocar o �ndice para o primeiro registro
	 * itSetorDestino = (colecaoSetorDestino).iterator();
	 * //flag auxiliar para controlar a sa�da da rotina de
	 * // repeti��o
	 * boolean sairLoop = false;
	 * while (itSetorDestino.hasNext() && sairLoop == false) {
	 * setorComercialDestino = (SetorComercial) itSetorDestino
	 * .next();
	 * if (setorComercialDestino.getCodigo() == setorComercialOrigem
	 * .getCodigo()) {
	 * retorno = true;
	 * sairLoop = true;
	 * //Compara��o de quadras
	 * Collection colecaoQuadraOrigem = pesquisarDependentes(
	 * setorComercialOrigem, 2);
	 * if (colecaoQuadraOrigem == null
	 * || colecaoQuadraOrigem.isEmpty()) {
	 * retorno = true;
	 * break;
	 * } else {
	 * Collection colecaoQuadraDestino = pesquisarDependentes(
	 * setorComercialDestino, 2);
	 * if (colecaoQuadraDestino != null
	 * && !colecaoQuadraDestino.isEmpty()) {
	 * itQuadraOrigem = (colecaoQuadraOrigem)
	 * .iterator();
	 * while (itQuadraOrigem.hasNext()) {
	 * if (!retorno) {
	 * // Verifica a exist�ncia de
	 * // im�veis na quadra
	 * if (existeImovel(quadraOrigem,
	 * 2)) {
	 * break;
	 * }
	 * }
	 * quadraOrigem = (Quadra) itQuadraOrigem
	 * .next();
	 * //Colocar o �ndice para o primeiro
	 * // registro
	 * itQuadraDestino = (colecaoQuadraDestino)
	 * .iterator();
	 * while (itQuadraDestino.hasNext()) {
	 * quadraDestino = (Quadra) itQuadraDestino
	 * .next();
	 * if (quadraDestino
	 * .getNumeroQuadra() == quadraOrigem
	 * .getNumeroQuadra()) {
	 * retorno = true;
	 * break;
	 * } else {
	 * retorno = false;
	 * }
	 * }
	 * }
	 * } else {
	 * retorno = false;
	 * }
	 * }
	 * } else {
	 * retorno = false;
	 * }
	 * }
	 * }
	 * } else {
	 * retorno = false;
	 * }
	 * }
	 * break;
	 * //Setor Comercial
	 * case 2:
	 * Collection colecaoQuadraOrigem = pesquisarDependentes(origem, 2);
	 * //Compara��o de quadras
	 * if (colecaoQuadraOrigem != null && !colecaoQuadraOrigem.isEmpty()) {
	 * Collection colecaoQuadraDestino = pesquisarDependentes(destino,
	 * 2);
	 * if (colecaoQuadraDestino != null
	 * && !colecaoQuadraDestino.isEmpty()) {
	 * Iterator itQuadraOrigem = (colecaoQuadraOrigem).iterator();
	 * Iterator itQuadraDestino = null;
	 * while (itQuadraOrigem.hasNext()) {
	 * if (!retorno) {
	 * break;
	 * }
	 * Quadra quadraOrigem = (Quadra) itQuadraOrigem.next();
	 * //Colocar o �ndice para o primeiro registro
	 * itQuadraDestino = (colecaoQuadraDestino).iterator();
	 * while (itQuadraDestino.hasNext()) {
	 * Quadra quadraDestino = (Quadra) itQuadraDestino
	 * .next();
	 * if (quadraDestino.getNumeroQuadra() == quadraOrigem
	 * .getNumeroQuadra()) {
	 * retorno = true;
	 * break;
	 * } else {
	 * retorno = false;
	 * }
	 * }
	 * }
	 * } else {
	 * retorno = false;
	 * }
	 * }
	 * break;
	 * default:
	 * break;
	 * }
	 * return retorno;
	 * }
	 */

	/**
	 * Retorna os dependentes do objeto passado (Localidade ou Setor Comercial)
	 * 
	 * @param objetoPai
	 * @param tipoObjeto
	 * @return uma colecao com objetos dependentes do objeto passado
	 * @throws RemoteException
	 * @throws ErroRepositorioException
	 */

	/*
	 * private Collection pesquisarDependentes(Object objetoPai, int tipoObjeto) {
	 * Collection colecaoPesquisa = null;
	 * //Obt�m a inst�ncia da fachada
	 * Fachada fachada = Fachada.getInstancia();
	 * switch (tipoObjeto) {
	 * //Localidade - retorna uma cole��o de setor comercial
	 * case 1:
	 * Localidade localidade = (Localidade) objetoPai;
	 * //Indicador de uso considerado como ATIVO
	 * colecaoPesquisa = fachada.pesquisarSetorComercial(localidade
	 * .getId().intValue());
	 * break;
	 * //Setor Comercial - retorna uma cole��o de quadra
	 * case 2:
	 * SetorComercial setorComercial = (SetorComercial) objetoPai;
	 * //Indicador de uso considerado como ATIVO
	 * colecaoPesquisa = fachada.pesquisarQuadra(setorComercial.getId()
	 * .intValue());
	 * break;
	 * default:
	 * break;
	 * }
	 * return colecaoPesquisa;
	 * }
	 */
	/**
	 * Valida os valores digitados pelo usu�rio
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

		// Obt�m a inst�ncia da fachada
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

						// Objetos que ser�o retornados pelo hibernate
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
	 * Verifica a exist�ncia de im�veis no objeto passado como par�metro Os
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
	 * //Obt�m a inst�ncia da fachada
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
