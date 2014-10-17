/**
 * 
 */
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

package gcom.gui.arrecadacao;

import gcom.arrecadacao.ArrecadacaoForma;
import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.ArrecadadorContratoTarifa;
import gcom.arrecadacao.ArrecadadorContratoTarifaPK;
import gcom.arrecadacao.Concessionaria;
import gcom.arrecadacao.FiltroArrecadacaoForma;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.arrecadacao.FiltroConcessionaria;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descri��o da classe
 * 
 * @author Marcio Roberto
 * @date 13/03/2007
 */
public class ExibirInserirContratoArrecadadorAction
				extends GcomAction {

	/**
	 * [UC0509] Inserir Contrato Arrecadador
	 * 
	 * @author Marcio Roberto
	 * @date 13/03/2007
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("contratoArrecadadorInserir");

		InserirContratoArrecadadorActionForm inserirContratoArrecadadorActionForm = (InserirContratoArrecadadorActionForm) actionForm;

		// Instancia da Fachada
		Fachada fachada = Fachada.getInstancia();

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getParameter("menu") != null){
			sessao.removeAttribute("colecaoArrecadadorContratoTarifa");
		}

		// Campos do formulario
		String idCliente = inserirContratoArrecadadorActionForm.getIdCliente();

		// Arrecadador
		FiltroArrecadador filtroArrecadador = new FiltroArrecadador();

		// Ordena filtro de arrecadador por id do cliente
		filtroArrecadador.setCampoOrderBy(FiltroArrecadador.CLIENTE_ID);
		// Inclui a obejeto de cliente no filtro de arrecadador
		filtroArrecadador.adicionarCaminhoParaCarregamentoEntidade("cliente");
		// Preenche colecao de arrecadador
		Collection<Arrecadador> colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());
		if(colecaoArrecadador == null || colecaoArrecadador.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Arrecadador");
		}else{
			FiltroCliente filtroCliente = new FiltroCliente();
			Iterator iteratorColecaoArrecadador = colecaoArrecadador.iterator();
			Cliente cliente = new Cliente();
			while(iteratorColecaoArrecadador.hasNext()){
				Arrecadador arrecadador = (Arrecadador) iteratorColecaoArrecadador.next();
				cliente = arrecadador.getCliente();
				if(cliente != null){
					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId(), ParametroSimples.CONECTOR_OR));
				}
			}
			Collection colecaoClienteArrecadador = fachada.pesquisar(filtroCliente, Cliente.class.getName());
			if(colecaoClienteArrecadador.isEmpty()){
				sessao.setAttribute("colecaoClienteArrecadador", colecaoClienteArrecadador);
			}else{
				sessao.setAttribute("colecaoClienteArrecadador", colecaoClienteArrecadador);
			}
		}

		Collection colecaoPesquisa = null;
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
			}

		}catch(ControladorException e){
			throw new IllegalArgumentException("erro.sistema");
		}

		FiltroArrecadacaoForma filtroArrecadacaoForma = new FiltroArrecadacaoForma();

		filtroArrecadacaoForma.setCampoOrderBy(FiltroArrecadacaoForma.DESCRICAO);

		Collection<ArrecadacaoForma> colecaoFormaArrecadacao = fachada.pesquisar(filtroArrecadacaoForma, ArrecadacaoForma.class.getName());

		if(colecaoFormaArrecadacao != null && !colecaoFormaArrecadacao.isEmpty()){
			sessao.setAttribute("colecaoFormaArrecadacao", colecaoFormaArrecadacao);
		}else{
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "ARRECADACAO_FORMA");
		}

		// Verificar se o n�mero do cliente n�o est� cadastrado
		if(idCliente != null && !idCliente.trim().equals("")){
			// Filtro para descobrir id do Cliente
			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade("clienteTipo");
			Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());
			if(colecaoCliente == null || colecaoCliente.isEmpty()){
				inserirContratoArrecadadorActionForm.setIdCliente("");
				httpServletRequest.setAttribute("existeCliente", "exception");
				throw new ActionServletException("atencao.cliente.inexistente");
			}else{
				Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
				// [FS0004]-Verificar se pessoa f�sica
				if(cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica() != null
								&& cliente.getClienteTipo().getIndicadorPessoaFisicaJuridica().equals(new Short("2"))){
					throw new ActionServletException("atencao.cliente_arrecadador_pessoa_fisica");
				}

				inserirContratoArrecadadorActionForm.setIdCliente(cliente.getId().toString());
				httpServletRequest.setAttribute("nomeCampo", "idCliente");
				inserirContratoArrecadadorActionForm.setNomeCliente(cliente.getNome());
				httpServletRequest.setAttribute("nomeCampo", "nomeCliente");
			}
		}

		if(httpServletRequest.getParameter("adicionarFormaArrecadacao") != null){
			filtroArrecadacaoForma.limparListaParametros();
			filtroArrecadacaoForma.adicionarParametro(new ParametroSimples(FiltroArrecadador.ID, Util.obterInteger(httpServletRequest
							.getParameter("idFormaArrecadacao"))));

			ArrecadacaoForma arrecadacaoForma = (ArrecadacaoForma) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroArrecadacaoForma,
							ArrecadacaoForma.class.getName()));
			ArrecadadorContratoTarifa arrecadadorContratoTarifa = new ArrecadadorContratoTarifa();

			arrecadadorContratoTarifa.setComp_id(new ArrecadadorContratoTarifaPK(null, arrecadacaoForma.getId()));
			arrecadadorContratoTarifa.setArrecadacaoForma(arrecadacaoForma);
			arrecadadorContratoTarifa.setArrecadadorContrato(null);
			arrecadadorContratoTarifa.setNumeroDiaFloat(Util.obterShort(httpServletRequest.getParameter("qtdDiasFloat")));
			BigDecimal percentualTarifa = null;
			if(httpServletRequest.getParameter("percentualTarifa") != ""){
				percentualTarifa = new BigDecimal(httpServletRequest.getParameter("percentualTarifa").replace(",", "."));
				if(percentualTarifa.compareTo(new BigDecimal("100")) > 0 || percentualTarifa.equals(BigDecimal.ZERO)){
					throw new ActionServletException("atencao.percentual_informado_calculo_tarifa_invalido");
				}
			}
			BigDecimal valorTarifa = null;
			if(httpServletRequest.getParameter("valorTarifa") != ""){
				valorTarifa = new BigDecimal(httpServletRequest.getParameter("valorTarifa").replace(",", "."));
				if(valorTarifa.compareTo(BigDecimal.ZERO) <= 0){
					throw new ActionServletException("atencao.valor_monetario_tarifa_invalido");
				}
			}
			arrecadadorContratoTarifa.setPercentualTarifa(percentualTarifa);
			arrecadadorContratoTarifa.setValorTarifa(valorTarifa);
			arrecadadorContratoTarifa.setUltimaAlteracao(new Date());
			Collection<ArrecadadorContratoTarifa> colecaoArrecadadorContratoTarifa = (Collection<ArrecadadorContratoTarifa>) sessao
							.getAttribute("colecaoArrecadadorContratoTarifa");
			if(colecaoArrecadadorContratoTarifa == null){
				colecaoArrecadadorContratoTarifa = new Vector<ArrecadadorContratoTarifa>();
			}

			Iterator<ArrecadadorContratoTarifa> it = colecaoArrecadadorContratoTarifa.iterator();

			while(it.hasNext()){
				ArrecadadorContratoTarifa aux = it.next();
				if(aux.getComp_id().getArrecadacaoFormaId().equals(arrecadadorContratoTarifa.getComp_id().getArrecadacaoFormaId())){
					throw new ActionServletException("atencao.forma_ja_informada_contrato");
				}
			}

			colecaoArrecadadorContratoTarifa.add(arrecadadorContratoTarifa);
			sessao.setAttribute("colecaoArrecadadorContratoTarifa", colecaoArrecadadorContratoTarifa);
		}

		if(httpServletRequest.getParameter("removerArrecadacaoForma") != null){
			Collection<ArrecadadorContratoTarifa> colecaoAux = (Collection<ArrecadadorContratoTarifa>) sessao
							.getAttribute("colecaoArrecadadorContratoTarifa");

			Iterator<ArrecadadorContratoTarifa> it = colecaoAux.iterator();

			Collection<ArrecadadorContratoTarifa> colecaoArrecadadorContratoTarifa = new Vector<ArrecadadorContratoTarifa>();

			while(it.hasNext()){
				ArrecadadorContratoTarifa aux = it.next();
				if(!aux.getComp_id().getArrecadacaoFormaId().equals(
								Util.obterInteger(httpServletRequest.getParameter("idRegistrosRemocao")))){
					colecaoArrecadadorContratoTarifa.add(aux);
				}
			}

			sessao.setAttribute("colecaoArrecadadorContratoTarifa", colecaoArrecadadorContratoTarifa);
		}

		return retorno;
	}
}
