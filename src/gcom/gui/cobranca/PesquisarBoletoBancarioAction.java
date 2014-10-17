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

package gcom.gui.cobranca;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.FiltroArrecadador;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.BoletoBancario;
import gcom.cobranca.BoletoBancarioSituacao;
import gcom.cobranca.BoletoBancarioSituacaoHistorico;
import gcom.cobranca.FiltroBoletoBancarioSituacaoHistorico;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.comum.exception.PCGException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.Util;

/**
 * Realiza a pesquisa de boleto bancário de acordo com os parâmetros informados
 * 
 * @author Cinthya Cavalcanti
 * @created 23 de Setembro de 2011
 */
public class PesquisarBoletoBancarioAction
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
	@SuppressWarnings("null")
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws PCGException{

		ActionForward retorno = actionMapping.findForward("listaBoletoBancario");

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		PesquisarBoletoBancarioActionForm pesquisarBoletoBancarioActionForm = (PesquisarBoletoBancarioActionForm) actionForm;

		// Recupera os parâmetros do form
		String idBanco = (String) pesquisarBoletoBancarioActionForm.getBancoBoletoBancarioPesquisa();
		String idImovel = (String) pesquisarBoletoBancarioActionForm.getImovelBoletoBancarioPesquisa();
		String idCliente = (String) pesquisarBoletoBancarioActionForm.getClienteBoletoBancarioPesquisa();
		String dataInicialEntrada = (String) pesquisarBoletoBancarioActionForm.getDataInicialEntradaBoletoBancarioPesquisa();
		String dataFinalEntrada = (String) pesquisarBoletoBancarioActionForm.getDataFinalEntradaBoletoBancarioPesquisa();
		String dataInicialVencimento = (String) pesquisarBoletoBancarioActionForm.getDataInicialVencimentoBoletoBancarioPesquisa();
		String dataFinalVencimento = (String) pesquisarBoletoBancarioActionForm.getDataFinalVencimentoBoletoBancarioPesquisa();

		BoletoBancarioHelper boletoBancarioHelper = new BoletoBancarioHelper();
		boolean verificarDocumentoCobranca = false;

		// Insere os parâmetros informados no boletoBancarioHelper
		if(idBanco != null && !idBanco.trim().equalsIgnoreCase("")){

			FiltroArrecadador filtroArrecadador = new FiltroArrecadador();
			filtroArrecadador.adicionarParametro(new ParametroSimples(FiltroArrecadador.CODIGO_AGENTE, idBanco));

			Collection colecaoArrecadador = fachada.pesquisar(filtroArrecadador, Arrecadador.class.getName());
			if(colecaoArrecadador != null && !colecaoArrecadador.isEmpty()){
				boletoBancarioHelper.setArrecadador((Arrecadador) colecaoArrecadador.iterator().next());
			}else{
				throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Bancário");
			}

		}

		if(sessao.getAttribute("indicador") != null && sessao.getAttribute("indicador").equals("1")){
			verificarDocumentoCobranca = true;
		}

		if(idImovel != null && !idImovel.trim().equalsIgnoreCase("")){
			Imovel imovel = new Imovel();
			imovel.setId(Integer.parseInt(idImovel));
			boletoBancarioHelper.setImovel(imovel);

		}

		if(idCliente != null && !idCliente.trim().equalsIgnoreCase("")){
			Cliente cliente = new Cliente();
			cliente.setId(Integer.parseInt(idCliente));
			boletoBancarioHelper.setCliente(cliente);

		}

		if(dataInicialEntrada != null && !dataInicialEntrada.trim().equalsIgnoreCase("")){
			boletoBancarioHelper.setDataInicialEntrada(Util
							.converterCampoStringParaData("", dataInicialEntrada, Constantes.FORMATO_DATA_BR));

		}

		if(dataFinalEntrada != null && !dataFinalEntrada.trim().equalsIgnoreCase("")){
			boletoBancarioHelper.setDataFinalEntrada(Util.converterCampoStringParaData("", dataFinalEntrada, Constantes.FORMATO_DATA_BR));

		}

		if(dataInicialVencimento != null && !dataInicialVencimento.trim().equalsIgnoreCase("")){
			boletoBancarioHelper.setDataInicialVencimento(Util.converterCampoStringParaData("", dataInicialVencimento,
							Constantes.FORMATO_DATA_BR));

		}

		if(dataFinalVencimento != null && !dataFinalVencimento.trim().equalsIgnoreCase("")){
			boletoBancarioHelper.setDataFinalVencimento(Util.converterCampoStringParaData("", dataFinalVencimento,
							Constantes.FORMATO_DATA_BR));

		}

		Collection<BoletoBancario> colecaoBoleto = null;
		Collection<BoletoBancarioSituacaoHistorico> colecaoBoletoSituacaoHistorico = new ArrayList<BoletoBancarioSituacaoHistorico>();

		// Total de registros
		Integer totalRegistros = fachada.pesquisarQuantidadeBoletoBancario(boletoBancarioHelper, false, false, false);

		if(totalRegistros == 0){
			// Nenhuma registro encontrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Bancário");
		}

		// Paginação
		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		colecaoBoleto = fachada.pesquisarBoletoBancario(boletoBancarioHelper, false, false, verificarDocumentoCobranca,
						(Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		if(colecaoBoleto != null && !colecaoBoleto.isEmpty()){
			for(BoletoBancario boletoBancario : colecaoBoleto){
				FiltroBoletoBancarioSituacaoHistorico filtroSituacaoHistorico = new FiltroBoletoBancarioSituacaoHistorico();
				filtroSituacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("boletoBancario.arrecadador");
				filtroSituacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("boletoBancario.arrecadador.cliente");
				filtroSituacaoHistorico.adicionarCaminhoParaCarregamentoEntidade("boletoBancario.boletoBancarioSituacao");
				filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_ID,
								boletoBancario.getId()));
				filtroSituacaoHistorico.adicionarParametro(new ParametroSimples(
								FiltroBoletoBancarioSituacaoHistorico.BOLETO_BANCARIO_SITUACAO_ID,
								BoletoBancarioSituacao.GERADO_NAO_ENVIADO_AO_BANCO));

				Collection<BoletoBancarioSituacaoHistorico> colecaoBoletoSituacaoHistoricoAux = fachada.pesquisar(filtroSituacaoHistorico,
								BoletoBancarioSituacaoHistorico.class.getName());

				if(colecaoBoletoSituacaoHistoricoAux != null && !colecaoBoletoSituacaoHistoricoAux.isEmpty()){
					BoletoBancarioSituacaoHistorico boletoBancarioSituacaoHistorico = (BoletoBancarioSituacaoHistorico) colecaoBoletoSituacaoHistoricoAux
									.iterator().next();
					colecaoBoletoSituacaoHistorico.add(boletoBancarioSituacaoHistorico);
				}
			}
		}

		if(colecaoBoletoSituacaoHistorico == null || colecaoBoletoSituacaoHistorico.isEmpty()){
			// Nenhuma cliente cadastrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Bancário");
		}else if(colecaoBoleto.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_BOLETO_BANCARIO){
			// Muitos registros encontrados
			throw new ActionServletException("atencao.pesquisa.muitosregistros");
		}else{
			// Coloca a coleção na sessão
			sessao.setAttribute("colecaoBoletoSituacaoHistorico", colecaoBoletoSituacaoHistorico);

		}

		return retorno;

	}
}
