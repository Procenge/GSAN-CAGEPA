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

package gcom.gui.arrecadacao.banco;

import gcom.arrecadacao.Arrecadador;
import gcom.arrecadacao.aviso.AvisoBancario;
import gcom.arrecadacao.aviso.AvisoDeducoes;
import gcom.arrecadacao.aviso.AvisoDeducoesPK;
import gcom.arrecadacao.banco.ContaBancaria;
import gcom.cadastro.sistemaparametro.FiltroSistemaParametro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.Util;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirAvisoBancarioAction
				extends GcomAction {

	/**
	 * Alteração em 08/01/10 para cadastrar aviso bancario com valor zero nas colunas de valor
	 * pagamento classificados e
	 * não classificados
	 * 
	 * @author Andre Nishimura
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		InserirAvisoBancarioActionForm form = (InserirAvisoBancarioActionForm) actionForm;
		Arrecadador arrecadadorSessao = (Arrecadador) sessao.getAttribute("arrecadador");
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date dataLancamento = null;

		try{
			dataLancamento = formato.parse(form.getDataLancamento());
		}catch(ParseException e){
		}

		if(arrecadadorSessao == null || arrecadadorSessao.equals("")){
			// O Arrecadador eh obrigatório
			throw new ActionServletException("atencao.informe_campo", null, "Arrecadador");
		}

		if(dataLancamento == null || dataLancamento.equals("")){
			// O Arrecadador eh obrigatório
			throw new ActionServletException("atencao.informe_campo", null, "Data do Lançamento");
		}

		if(form.getNumeroDocumento() == null || form.getNumeroDocumento().equals("")){
			// O Arrecadador eh obrigatório
			throw new ActionServletException("atencao.informe_campo", null, "Número do Documento");
		}

		if(form.getNumeroSequencial() == null || form.getNumeroSequencial().equals("")){
			// O Arrecadador eh obrigatório
			throw new ActionServletException("atencao.informe_campo", null, "Número do Sequencial");
		}

		Double numeroSequencial = new Double(form.getNumeroSequencial());
		BigDecimal valorAviso = BigDecimal.ZERO;
		String valorArrecadacaoFormatado = null;
		String valorDevolucaoFormatado = null;

		// Início do cálculo do valor do aviso
		if(form.getValorArrecadacao() != null && !form.getValorArrecadacao().equals("")){

			valorArrecadacaoFormatado = form.getValorArrecadacao().replace(".", "").replace(",", ".");

			if(form.getTipoAviso().equals(AvisoBancario.INDICADOR_CREDITO.toString()) || form.getTipoAviso().equalsIgnoreCase("credito")){

				valorAviso = new BigDecimal(valorArrecadacaoFormatado);
			}
		}

		if(form.getValorDevolucao() != null && !form.getValorDevolucao().equals("")){

			valorDevolucaoFormatado = form.getValorDevolucao().replace(".", "").replace(",", ".");

			if(form.getTipoAviso().equals(AvisoBancario.INDICADOR_DEBITO.toString()) || form.getTipoAviso().equalsIgnoreCase("debito")){

				valorAviso = new BigDecimal(valorDevolucaoFormatado);
			}
		}

		if(sessao.getAttribute("colecaoAvisoDeducao") != null && !((Collection) sessao.getAttribute("colecaoAvisoDeducao")).isEmpty()){
			Collection colecaoAvisoDeducao = (Collection) sessao.getAttribute("colecaoAvisoDeducao");
			Iterator colecaoAvisoDeducaoIterator = colecaoAvisoDeducao.iterator();

			while(colecaoAvisoDeducaoIterator.hasNext()){
				AvisoDeducoes avisoDeducoes = (AvisoDeducoes) colecaoAvisoDeducaoIterator.next();
				valorAviso = valorAviso.subtract(avisoDeducoes.getValorDeducao());
			}

			if(form.getAvisoBancario() != null){

				if(fachada.pesquisarDeducoesAvisoBancario(arrecadadorSessao.getCodigoAgente().toString(), dataLancamento, ""
								+ numeroSequencial.shortValue()) != null){
					Double valorDeducoes = fachada.pesquisarDeducoesAvisoBancario(arrecadadorSessao.getCodigoAgente().toString(),
									dataLancamento, "" + numeroSequencial.shortValue());
					valorAviso = valorAviso.subtract(new BigDecimal(valorDeducoes.toString()));
				}
			}

		}

		// Fim do cálculo do valor do aviso

		if(form.getTipoAviso().equals("" + AvisoBancario.INDICADOR_CREDITO) || form.getTipoAviso().equalsIgnoreCase("credito")){
			if(valorAviso.compareTo(BigDecimal.ZERO) < 0){
				throw new ActionServletException("atencao.valor_aviso.nao_credito", null, Util.formatarMoedaReal(valorAviso));
			}

		}else{
			if(valorAviso.compareTo(BigDecimal.ZERO) > 0){
				throw new ActionServletException("atencao.valor_aviso.nao_debito", null, Util.formatarMoedaReal(valorAviso));
			}
		}

		AvisoBancario avisoBancario = new AvisoBancario();

		Date dataRealizacao = null;

		if(form.getDataRealizacao() != null && !form.getDataRealizacao().equals("")){
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

			try{
				dataRealizacao = format.parse(form.getDataRealizacao());
			}catch(ParseException ex){
				throw new ActionServletException("erro.sistema");
			}

		}

		BigDecimal valorArrecadacao = BigDecimal.ZERO;

		if(form.getValorArrecadacao() != null && !form.getValorArrecadacao().equals("")){

			valorArrecadacao = new BigDecimal(valorArrecadacaoFormatado);
		}

		BigDecimal valorDevolucao = BigDecimal.ZERO;

		if(form.getValorDevolucao() != null && !form.getValorDevolucao().equals("")){

			valorDevolucao = new BigDecimal(valorDevolucaoFormatado);
		}

		ContaBancaria contaBancaria = new ContaBancaria();
		contaBancaria.setId(Integer.valueOf(form.getIdContaBancaria()));

		// Verifica se o usuário selecionou um aviso bancário se não tiver
		// selecionado insere
		if(form.getAvisoBancario() == null){

			Short tipoAviso = null;

			if(form.getTipoAviso().equalsIgnoreCase("credito") || form.getTipoAviso().equals("" + AvisoBancario.INDICADOR_CREDITO)){
				tipoAviso = Short.valueOf("1");
			}else{
				tipoAviso = Short.valueOf("2");
			}

			FiltroSistemaParametro filtroSistemaParametro = new FiltroSistemaParametro();

			Collection colecaoSistemaParametros = fachada.pesquisar(filtroSistemaParametro, SistemaParametro.class.getName());

			SistemaParametro sistemaParametro = (SistemaParametro) colecaoSistemaParametros.iterator().next();

			// Arrecadador arrecadador = new Arrecadador();
			//
			// arrecadador.setId(new Integer(form.getCodigoArrecadador()));

			avisoBancario.setDataLancamento(dataLancamento);
			avisoBancario.setNumeroSequencial(numeroSequencial.shortValue());
			avisoBancario.setDataPrevista(dataLancamento);
			avisoBancario.setDataRealizada(dataRealizacao);
			// avisoBancario.setValorPrevisto(null);
			avisoBancario.setValorRealizado(valorAviso);
			avisoBancario.setValorArrecadacaoCalculado(BigDecimal.ZERO);
			avisoBancario.setValorDevolucaoCalculado(BigDecimal.ZERO);
			avisoBancario.setValorArrecadacaoInformado(valorArrecadacao);
			avisoBancario.setValorDevolucaoInformado(valorDevolucao);
			avisoBancario.setValorContabilizado(BigDecimal.ZERO);
			// Alterado por Sávio Luiz data:22/03/2007
			// pegar o maior anoMes entre o anoMes da dataLancamento e o
			// anoMesArrecadacao
			Integer anoMesDataLancamento = Util.recuperaAnoMesDaData(dataLancamento);
			if(anoMesDataLancamento > sistemaParametro.getAnoMesArrecadacao()){
				avisoBancario.setAnoMesReferenciaArrecadacao(anoMesDataLancamento);
			}else{
				avisoBancario.setAnoMesReferenciaArrecadacao(sistemaParametro.getAnoMesArrecadacao());
			}

			avisoBancario.setIndicadorCreditoDebito(Short.valueOf(tipoAviso));
			avisoBancario.setNumeroDocumento(Integer.valueOf(form.getNumeroDocumento()).intValue());
			avisoBancario.setArrecadador(arrecadadorSessao);
			avisoBancario.setContaBancaria(contaBancaria);
			avisoBancario.setArrecadadorMovimento(null);
			avisoBancario.setValorPagamentoClassificado(BigDecimal.ZERO);
			avisoBancario.setValorPagamentoNaoClassificado(BigDecimal.ZERO);
			avisoBancario.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_AVISO_BANCARIO_INSERIR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacao.registrarOperacao(avisoBancario);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			Boolean existeAvisoArrecadador = Fachada.getInstancia().existeAvisoArrecadadorTipoCaixaEmpresa(arrecadadorSessao.getId(),
							dataLancamento);

			if(existeAvisoArrecadador){

				throw new ActionServletException("atencao.arrecadacao.existe_aviso_bancario_para_arrecadador_tipo_caixa",
								Util.formatarData(dataLancamento));

			}

			fachada.inserir(avisoBancario);

		}else{
			// Caso tenha selecionado atualiza
			avisoBancario = (AvisoBancario) sessao.getAttribute("avisoBancario");
			avisoBancario.setDataRealizada(dataRealizacao);
			avisoBancario.setValorRealizado(valorAviso);
			/*
			 * avisoBancario.setValorArrecadacaoCalculado(new BigDecimal("0.00"));
			 * avisoBancario.setValorDevolucaoCalculado(new BigDecimal("0.00"));
			 */
			avisoBancario.setValorArrecadacaoInformado(valorArrecadacao);
			avisoBancario.setValorDevolucaoInformado(valorDevolucao);
			avisoBancario.setNumeroDocumento(Integer.valueOf(form.getNumeroDocumento()).intValue());
			avisoBancario.setContaBancaria(contaBancaria);
			avisoBancario.setUltimaAlteracao(new Date());

			// ------------ REGISTRAR TRANSAÇÃO ----------------
			RegistradorOperacao registradorOperacaoAtualizar = new RegistradorOperacao(Operacao.OPERACAO_AVISO_BANCARIO_ATUALIZAR,
							new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			registradorOperacaoAtualizar.registrarOperacao(avisoBancario);
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			fachada.atualizar(avisoBancario);
		}

		if(sessao.getAttribute("colecaoAvisoDeducao") != null && !((Collection) sessao.getAttribute("colecaoAvisoDeducao")).isEmpty()){
			Collection colecaoAvisoDeducao = (Collection) sessao.getAttribute("colecaoAvisoDeducao");
			Iterator colecaoAvisoDeducaoIterator = colecaoAvisoDeducao.iterator();
			while(colecaoAvisoDeducaoIterator.hasNext()){
				AvisoDeducoes avisoDeducoes = (AvisoDeducoes) colecaoAvisoDeducaoIterator.next();
				AvisoDeducoesPK avisoDeducoesPK = new AvisoDeducoesPK();
				avisoDeducoes.setAvisoBancario(avisoBancario);
				avisoDeducoesPK.setAvisoBancarioId(avisoBancario.getId());
				avisoDeducoesPK.setDeducaoTipoId(avisoDeducoes.getDeducaoTipo().getId());

				RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_AVISO_BANCARIO_INSERIR,
								new UsuarioAcaoUsuarioHelper(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

				registradorOperacao.registrarOperacao(avisoBancario);
				registradorOperacao.registrarOperacao(avisoDeducoes);
				avisoDeducoes.setComp_id(avisoDeducoesPK);

				fachada.inserirAvisosDeducoes(avisoDeducoes, avisoBancario);

			}
		}

		this.montaTelaSucessoAvisoBancario(httpServletRequest, sessao, form, avisoBancario);

		sessao.removeAttribute("InserirAvisoBancarioActionForm");

		return retorno;
	}

	/**
	 * 2.1.1. Aviso Bancário (obrigatório) <<Inclui>> [UC0241 – Pesquisar Aviso Bancário]. Inserir
	 * Aviso <<Inclui>> [UC0235 - Inserir Aviso Bancário] (Retornar com o aviso preenchido ao
	 * incluir um novo aviso)
	 * 
	 * @param httpServletRequest
	 * @param sessao
	 * @param form
	 * @param avisoBancario
	 */
	private void montaTelaSucessoAvisoBancario(HttpServletRequest httpServletRequest, HttpSession sessao,
					InserirAvisoBancarioActionForm form,
					AvisoBancario avisoBancario){

		if(httpServletRequest.getParameter("montarPopUp") == null){
		
			montarPaginaSucesso(httpServletRequest, "Aviso Bancário de código " + avisoBancario.getId() + " com Data de Lançamento "
						+ form.getDataLancamento() + " e seqüencial " + form.getNumeroSequencial() + " do Arrecadador "
						+ form.getNomeArrecadador() + " inserido com sucesso.", "Inserir outro Aviso Bancário",
						"exibirInserirAvisoBancarioAction.do?menu=sim", "ExibirAtualizarAvisoBancarioAction.do?idAvisoBancario="
										+ avisoBancario.getId(), "Atualizar Aviso Bancário Inserido");
		}else{
				// Fechar a página de sucesso
				sessao.setAttribute("fecharPopup", true);
				sessao.setAttribute("idRegistroAtualizacao", avisoBancario.getId());
			montarPaginaSucesso(
							httpServletRequest,
							"Aviso Bancário de código " + avisoBancario.getId() + " com Data de Lançamento " + form.getDataLancamento()
											+ " e seqüencial " + form.getNumeroSequencial() + " do Arrecadador "
											+ form.getNomeArrecadador() + " inserido com sucesso.", "Voltar para Inserir Pagamento",
							"exibirInserirPagamentosAction.do?menu=sim");
		}
	}
}
