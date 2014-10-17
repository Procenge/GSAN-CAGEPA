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

package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcaoSituacao;
import gcom.cobranca.CobrancaDocumento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.interceptor.RegistradorOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoEfetuada;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.comum.exception.PCGException;

/**
 * [UC3029] Informar Entrega do Documento Cobran�a
 * 
 * @author Cinthya Cavalcanti
 * @created 15 de Dezembro de 2011
 */
public class InformarEntregaDocumentoCobrancaAction
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
					HttpServletResponse httpServletResponse) throws PCGException{

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Mudar isso quando tiver esquema de seguran�a
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		InformarEntregaDocumentoCobrancaActionForm informarEntregaDocumentoCobrancaActionForm = (InformarEntregaDocumentoCobrancaActionForm) actionForm;

		String chavesDocumentoCobranca = (String) informarEntregaDocumentoCobrancaActionForm.getChavesDocumentoCobranca();
		String dataSituacaoAcaoGeral = (String) httpServletRequest.getParameter("dataSituacaoAcaoGeral");

		String[] chavesDocumentoCobrancaArray = chavesDocumentoCobranca.split("[$]");
		Collection<CobrancaDocumento> colecaoCobrancaDocumentos = (Collection) sessao.getAttribute("colecaoDocumentosCobranca");
		Collection<CobrancaDocumento> colecaoCobrancaDocumentosSelecionados = new ArrayList<CobrancaDocumento>();

		int count = 1;

		// [FS0001] � Verificar sele��o dos documentos para informa��o da data de entrega
		if(chavesDocumentoCobranca == null || chavesDocumentoCobranca.equals("")){

			// . Caso o usu�rio n�o selecione nenhum documento de cobran�a, exibir a mensagem ��
			// necess�rio selecionar pelo menos um documento de cobran�a para a informa��o da
			// entrega.�
			// e retornar para o passo correspondente no fluxo.

			throw new ActionServletException("atencao.nenhum.documento.cobranca.selecionado");
		}

		// [FS0003] � Validar data de entrega geral
		if(dataSituacaoAcaoGeral != null && !dataSituacaoAcaoGeral.equals("")){
			validarDataEntregaGeral(dataSituacaoAcaoGeral);
		}

		for(CobrancaDocumento cobrancaDocumento : colecaoCobrancaDocumentos){
			boolean isChavePresente = false;

			// verifica se o usu�rio informou a data da entrega e selecionou o documento. Caso o
			// usu�rio
			// tenha
			// informado a data da entrega mas, n�o tenha selecionado o documento � exibida a
			// mensagem
			// "A data de entrega do documento
			// da linha {0} foi preenchida mas, o documento n�o foi selecionado."

			for(int i = 0; i < chavesDocumentoCobrancaArray.length; i++){
				if(cobrancaDocumento.getId().equals(Integer.valueOf(chavesDocumentoCobrancaArray[i]))){
					isChavePresente = true;
					break;
				}
			}

			String dataEntrega = (String) httpServletRequest.getParameter("nDataEntrada" + cobrancaDocumento.getId().toString());

			if(dataEntrega != null && !dataEntrega.equals("")){
				if(isChavePresente){

					// Cria o formato da data
					SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
					Date dataSituacaoAcao = new Date();

					// [FS0004] � Validar datas de entrega dos documentos
					// Caso o usu�rio informe uma data de entrega inv�lida para qualquer
					// um dos documentos,
					// exibir a mensagem �H� data de entrega inv�lida para os documentos
					// das linhas
					// <<n�meros das linhas onde ocorre a data inv�lida, separados por
					// v�rgula>>� e retornar para o passo
					// correspondente no fluxo

					try{
						if(dataEntrega.length() == 10){
							String dia = dataEntrega.substring(0, 2);
							String mes = dataEntrega.substring(3, 5);
							String ano = dataEntrega.substring(6);

							String anoMesDiaSemBarra = ano + mes + dia;

							if(Util.validarAnoMesDiaSemBarra(anoMesDiaSemBarra)){
								throw new ActionServletException("atencao.data.entrega.invalida.linha", String.valueOf(count));
							}
						}else{
							throw new ActionServletException("atencao.data.entrega.invalida.linha", String.valueOf(count));
						}

						dataSituacaoAcao = formato.parse(dataEntrega);
					}catch(ParseException e){
						throw new ActionServletException("atencao.data.entrega.invalida.linha", String.valueOf(count));
					}

					// Caso o usu�rio informe uma data de entrega para qualquer um dos
					// documentos anterior
					// � data de emiss�o do documento, exibir a mensagem �A data de
					// entrega
					// dos documentos das
					// linhas <<n�meros das linhas onde ocorre a data inv�lida,
					// separados
					// por v�rgula>> n�o pode ser anterior
					// � data de emiss�o do documento. Informe nova data de entrega.� e
					// retornar para o passo correspondente no fluxo.

					if(dataSituacaoAcao.before(cobrancaDocumento.getEmissao())){
						throw new ActionServletException("atencao.data.entrega.anterior.data.emissao", String.valueOf(count));
					}

					// Caso o usu�rio informe uma data de entrega para qualquer um dos
					// documentos posterior � data corrente,
					// exibir a mensagem �A data de entrega dos documentos das linhas
					// <<n�meros das linhas onde ocorre a data inv�lida,
					// separados por v�rgula>> n�o pode ser posterior � <<data corrente
					// no formato DD/MM/AAAA>>.
					// Informe nova data de entrega.� e retornar para o passo
					// correspondente no fluxo

					Date dataAtual = new Date();
					if(dataSituacaoAcao.after(dataAtual)){
						throw new ActionServletException("atencao.data.entrega.posterior.data.atual", String.valueOf(count), Util
										.formatarData(dataAtual));
					}

					// seta a data da situa��o a��o no objeto cobran�a documento de acordo com a
					// data
					// que o usu�rio
					// informou
					cobrancaDocumento.setDataSituacaoAcao(dataSituacaoAcao);

					colecaoCobrancaDocumentosSelecionados.add(cobrancaDocumento);
				}else{
					// . Caso algum ou alguns dos documentos de cobran�a selecionados esteja sem
					// a data de
					// entrega informada, exibir a mensagem �� necess�rio informar a data de
					// entrega para
					// todos
					// os documentos selecionados.� e retornar para o passo correspondente no
					// fluxo.
					if(isChavePresente){
						throw new ActionServletException("atencao.informar.data.entrega.documentos.selecionados");
					}

				}
				count++;
			}
		}

		for(CobrancaDocumento cobrancaDocumentoAux : colecaoCobrancaDocumentosSelecionados){
			// [SB0001] - Informar Data de Entrega
			// 3.1. Atualiza a tabela COBRANCA_DOCUMENTO
			CobrancaAcaoSituacao cobrancaAcaoSituacao = new CobrancaAcaoSituacao();
			cobrancaAcaoSituacao.setId(CobrancaAcaoSituacao.ENTREGUE);

			cobrancaDocumentoAux.setCobrancaAcaoSituacao(cobrancaAcaoSituacao);
			cobrancaDocumentoAux.setUltimaAlteracao(new Date());

			/*
			 * 3.2. Registra a transa��o de informar entrega do documento <<Inclui>> [UC0107 �
			 * Registrar Transa��o].
			 */
			// ------------ REGISTRAR TRANSA��O ----------------
			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_INFORMAR_ENTREGA_DOCUMENTO_COBRANCA,
							cobrancaDocumentoAux.getId(), cobrancaDocumentoAux.getId(), new UsuarioAcaoUsuarioHelper(usuarioLogado,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));

			Operacao operacao = new Operacao();
			operacao.setId(Operacao.OPERACAO_INFORMAR_ENTREGA_DOCUMENTO_COBRANCA);

			OperacaoEfetuada operacaoEfetuada = new OperacaoEfetuada();
			operacaoEfetuada.setOperacao(operacao);

			cobrancaDocumentoAux.setOperacaoEfetuada(operacaoEfetuada);
			cobrancaDocumentoAux.adicionarUsuario(usuarioLogado, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);

			registradorOperacao.registrarOperacao(cobrancaDocumentoAux);
			// ------------ REGISTRAR TRANSA��O ----------------

			// atualiza Cobranca Documento
			fachada.atualizarCobrancaDocumento(cobrancaDocumentoAux);
		}

		// [FS0005] - Verificar sucesso da transa��o
		montarPaginaSucesso(httpServletRequest,
						"A(s) data(s) de entrega(s) do(s) documento(s) de cobran�a foi(foram) informada(s) com sucesso.",
						"Realizar outra Manuten��o de Documentos de Cobran�a",
						"exibirFiltrarEntregaDocumentoCobrancaAction.do?limparForm=OK");

		return retorno;
	}

	// [FS0003] � Validar data de entrega geral
	// . Caso o usu�rio informe uma data de entrega geral inv�lida,
	// exibir a mensagem �Data de entrega geral inv�lida.� e retornar para o passo
	// correspondente no fluxo.

	public void validarDataEntregaGeral(String dataSituacaoAcaoGeral){

		// Cria o formato da data
		Date dataSituacaoAcaoGeralCobranca = new Date();
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

		// Validar data de entrega geral
		try{
			if(dataSituacaoAcaoGeral.length() == 10){
				String dia = dataSituacaoAcaoGeral.substring(0, 2);
				String mes = dataSituacaoAcaoGeral.substring(3, 5);
				String ano = dataSituacaoAcaoGeral.substring(6);

				String anoMesDiaSemBarra = ano + mes + dia;

				if(Util.validarAnoMesDiaSemBarra(anoMesDiaSemBarra)){
					throw new ActionServletException("atencao.data.invalida", dataSituacaoAcaoGeral);
				}
			}

			dataSituacaoAcaoGeralCobranca = formato.parse(dataSituacaoAcaoGeral);

		}catch(ParseException e){
			throw new ActionServletException("atencao.data.invalida", dataSituacaoAcaoGeral);
		}
		Date dataAtual = new Date();

		// . Caso o usu�rio informe uma data de entrega geral posterior � data corrente,
		// exibir a mensagem �A data de entrega geral n�o pode ser posterior � <<data
		// corrente
		// no formato DD/MM/AAAA>>.
		// Informe nova data de entrega.� e retornar para o passo correspondente no
		// fluxo.
		if(dataSituacaoAcaoGeralCobranca.after(dataAtual)){
			throw new ActionServletException("atencao.data.entrega.geral.posterior.data.atual", dataSituacaoAcaoGeral);
		}

	}
}
