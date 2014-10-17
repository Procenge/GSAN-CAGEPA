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
 * [UC3029] Informar Entrega do Documento Cobrança
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

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		InformarEntregaDocumentoCobrancaActionForm informarEntregaDocumentoCobrancaActionForm = (InformarEntregaDocumentoCobrancaActionForm) actionForm;

		String chavesDocumentoCobranca = (String) informarEntregaDocumentoCobrancaActionForm.getChavesDocumentoCobranca();
		String dataSituacaoAcaoGeral = (String) httpServletRequest.getParameter("dataSituacaoAcaoGeral");

		String[] chavesDocumentoCobrancaArray = chavesDocumentoCobranca.split("[$]");
		Collection<CobrancaDocumento> colecaoCobrancaDocumentos = (Collection) sessao.getAttribute("colecaoDocumentosCobranca");
		Collection<CobrancaDocumento> colecaoCobrancaDocumentosSelecionados = new ArrayList<CobrancaDocumento>();

		int count = 1;

		// [FS0001] – Verificar seleção dos documentos para informação da data de entrega
		if(chavesDocumentoCobranca == null || chavesDocumentoCobranca.equals("")){

			// . Caso o usuário não selecione nenhum documento de cobrança, exibir a mensagem “É
			// necessário selecionar pelo menos um documento de cobrança para a informação da
			// entrega.”
			// e retornar para o passo correspondente no fluxo.

			throw new ActionServletException("atencao.nenhum.documento.cobranca.selecionado");
		}

		// [FS0003] – Validar data de entrega geral
		if(dataSituacaoAcaoGeral != null && !dataSituacaoAcaoGeral.equals("")){
			validarDataEntregaGeral(dataSituacaoAcaoGeral);
		}

		for(CobrancaDocumento cobrancaDocumento : colecaoCobrancaDocumentos){
			boolean isChavePresente = false;

			// verifica se o usuário informou a data da entrega e selecionou o documento. Caso o
			// usuário
			// tenha
			// informado a data da entrega mas, não tenha selecionado o documento é exibida a
			// mensagem
			// "A data de entrega do documento
			// da linha {0} foi preenchida mas, o documento não foi selecionado."

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

					// [FS0004] – Validar datas de entrega dos documentos
					// Caso o usuário informe uma data de entrega inválida para qualquer
					// um dos documentos,
					// exibir a mensagem “Há data de entrega inválida para os documentos
					// das linhas
					// <<números das linhas onde ocorre a data inválida, separados por
					// vírgula>>” e retornar para o passo
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

					// Caso o usuário informe uma data de entrega para qualquer um dos
					// documentos anterior
					// à data de emissão do documento, exibir a mensagem “A data de
					// entrega
					// dos documentos das
					// linhas <<números das linhas onde ocorre a data inválida,
					// separados
					// por vírgula>> não pode ser anterior
					// à data de emissão do documento. Informe nova data de entrega.” e
					// retornar para o passo correspondente no fluxo.

					if(dataSituacaoAcao.before(cobrancaDocumento.getEmissao())){
						throw new ActionServletException("atencao.data.entrega.anterior.data.emissao", String.valueOf(count));
					}

					// Caso o usuário informe uma data de entrega para qualquer um dos
					// documentos posterior à data corrente,
					// exibir a mensagem “A data de entrega dos documentos das linhas
					// <<números das linhas onde ocorre a data inválida,
					// separados por vírgula>> não pode ser posterior à <<data corrente
					// no formato DD/MM/AAAA>>.
					// Informe nova data de entrega.” e retornar para o passo
					// correspondente no fluxo

					Date dataAtual = new Date();
					if(dataSituacaoAcao.after(dataAtual)){
						throw new ActionServletException("atencao.data.entrega.posterior.data.atual", String.valueOf(count), Util
										.formatarData(dataAtual));
					}

					// seta a data da situação ação no objeto cobrança documento de acordo com a
					// data
					// que o usuário
					// informou
					cobrancaDocumento.setDataSituacaoAcao(dataSituacaoAcao);

					colecaoCobrancaDocumentosSelecionados.add(cobrancaDocumento);
				}else{
					// . Caso algum ou alguns dos documentos de cobrança selecionados esteja sem
					// a data de
					// entrega informada, exibir a mensagem “É necessário informar a data de
					// entrega para
					// todos
					// os documentos selecionados.” e retornar para o passo correspondente no
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
			 * 3.2. Registra a transação de informar entrega do documento <<Inclui>> [UC0107 –
			 * Registrar Transação].
			 */
			// ------------ REGISTRAR TRANSAÇÃO ----------------
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
			// ------------ REGISTRAR TRANSAÇÃO ----------------

			// atualiza Cobranca Documento
			fachada.atualizarCobrancaDocumento(cobrancaDocumentoAux);
		}

		// [FS0005] - Verificar sucesso da transação
		montarPaginaSucesso(httpServletRequest,
						"A(s) data(s) de entrega(s) do(s) documento(s) de cobrança foi(foram) informada(s) com sucesso.",
						"Realizar outra Manutenção de Documentos de Cobrança",
						"exibirFiltrarEntregaDocumentoCobrancaAction.do?limparForm=OK");

		return retorno;
	}

	// [FS0003] – Validar data de entrega geral
	// . Caso o usuário informe uma data de entrega geral inválida,
	// exibir a mensagem “Data de entrega geral inválida.” e retornar para o passo
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

		// . Caso o usuário informe uma data de entrega geral posterior à data corrente,
		// exibir a mensagem “A data de entrega geral não pode ser posterior à <<data
		// corrente
		// no formato DD/MM/AAAA>>.
		// Informe nova data de entrega.” e retornar para o passo correspondente no
		// fluxo.
		if(dataSituacaoAcaoGeralCobranca.after(dataAtual)){
			throw new ActionServletException("atencao.data.entrega.geral.posterior.data.atual", dataSituacaoAcaoGeral);
		}

	}
}
