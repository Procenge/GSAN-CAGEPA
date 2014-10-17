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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.faturamento;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.DocumentoTipo;
import gcom.cobranca.FiltroDocumentoTipo;
import gcom.fachada.Fachada;
import gcom.faturamento.DocumentoNaoEntregue;
import gcom.faturamento.GuiaPagamentoGeral;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaGeral;
import gcom.faturamento.conta.Fatura;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action utilizado para Informar a Não Entrega de Documentos no banco
 * [UC0559] Informar Não Entrega de Documentos Permite informar uma Não Entrega
 * de Documentos
 * 
 * @author Thiago Tenório
 * @since 03/04/2007
 * @author eduardo henrique
 * @date 10/07/2008
 *       Alteração no JSP para seleção de motivos a partir de comboboxes
 */
public class InformarNaoEntregaDocumentosAction
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

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario logado no sistema
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		InformarNaoEntregaDocumentosActionForm informarNaoEntregaDocumentosActionForm = (InformarNaoEntregaDocumentosActionForm) actionForm;

		String idResponsavelEntrega = informarNaoEntregaDocumentosActionForm.getIdResponsavelEntrega();

		String tipoDocumento = informarNaoEntregaDocumentosActionForm.getTipoDocumento();
		String mesAnoConta = informarNaoEntregaDocumentosActionForm.getMesAnoConta();
		String dataDevolucao = informarNaoEntregaDocumentosActionForm.getDataDevolucao();
		String idCodigo1 = informarNaoEntregaDocumentosActionForm.getIdCodigo1();
		String idCodigo2 = informarNaoEntregaDocumentosActionForm.getIdCodigo2();
		String idCodigo3 = informarNaoEntregaDocumentosActionForm.getIdCodigo3();
		String idCodigo4 = informarNaoEntregaDocumentosActionForm.getIdCodigo4();
		String idCodigo5 = informarNaoEntregaDocumentosActionForm.getIdCodigo5();
		String idCodigo6 = informarNaoEntregaDocumentosActionForm.getIdCodigo6();
		String idCodigo7 = informarNaoEntregaDocumentosActionForm.getIdCodigo7();
		String idCodigo8 = informarNaoEntregaDocumentosActionForm.getIdCodigo8();
		String idCodigo9 = informarNaoEntregaDocumentosActionForm.getIdCodigo9();
		String idCodigo10 = informarNaoEntregaDocumentosActionForm.getIdCodigo10();
		String idCodigo11 = informarNaoEntregaDocumentosActionForm.getIdCodigo11();
		String idCodigo12 = informarNaoEntregaDocumentosActionForm.getIdCodigo12();
		String idCodigo13 = informarNaoEntregaDocumentosActionForm.getIdCodigo13();
		String idCodigo14 = informarNaoEntregaDocumentosActionForm.getIdCodigo14();
		String idCodigo15 = informarNaoEntregaDocumentosActionForm.getIdCodigo15();
		String idCodigo16 = informarNaoEntregaDocumentosActionForm.getIdCodigo16();
		String idCodigo17 = informarNaoEntregaDocumentosActionForm.getIdCodigo17();
		String idCodigo18 = informarNaoEntregaDocumentosActionForm.getIdCodigo18();
		String idCodigo19 = informarNaoEntregaDocumentosActionForm.getIdCodigo19();
		String idCodigo20 = informarNaoEntregaDocumentosActionForm.getIdCodigo20();

		String numeroDocumento1 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento1();

		String numeroDocumento2 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento2();

		String numeroDocumento3 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento3();

		String numeroDocumento4 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento4();

		String numeroDocumento5 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento5();

		String numeroDocumento6 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento6();

		String numeroDocumento7 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento7();

		String numeroDocumento8 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento8();

		String numeroDocumento9 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento9();

		String numeroDocumento10 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento10();

		String numeroDocumento11 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento11();

		String numeroDocumento12 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento12();

		String numeroDocumento13 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento13();

		String numeroDocumento14 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento14();

		String numeroDocumento15 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento15();

		String numeroDocumento16 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento16();

		String numeroDocumento17 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento17();

		String numeroDocumento18 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento18();

		String numeroDocumento19 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento19();

		String numeroDocumento20 = informarNaoEntregaDocumentosActionForm.getNumeroDocumento20();

		String matricula1 = informarNaoEntregaDocumentosActionForm.getMatricula1();
		String matricula2 = informarNaoEntregaDocumentosActionForm.getMatricula2();
		String matricula3 = informarNaoEntregaDocumentosActionForm.getMatricula3();
		String matricula4 = informarNaoEntregaDocumentosActionForm.getMatricula4();
		String matricula5 = informarNaoEntregaDocumentosActionForm.getMatricula5();
		String matricula6 = informarNaoEntregaDocumentosActionForm.getMatricula6();
		String matricula7 = informarNaoEntregaDocumentosActionForm.getMatricula7();
		String matricula8 = informarNaoEntregaDocumentosActionForm.getMatricula8();
		String matricula9 = informarNaoEntregaDocumentosActionForm.getMatricula9();
		String matricula10 = informarNaoEntregaDocumentosActionForm.getMatricula10();
		String matricula11 = informarNaoEntregaDocumentosActionForm.getMatricula11();
		String matricula12 = informarNaoEntregaDocumentosActionForm.getMatricula12();
		String matricula13 = informarNaoEntregaDocumentosActionForm.getMatricula13();
		String matricula14 = informarNaoEntregaDocumentosActionForm.getMatricula14();
		String matricula15 = informarNaoEntregaDocumentosActionForm.getMatricula15();
		String matricula16 = informarNaoEntregaDocumentosActionForm.getMatricula16();
		String matricula17 = informarNaoEntregaDocumentosActionForm.getMatricula17();
		String matricula18 = informarNaoEntregaDocumentosActionForm.getMatricula18();
		String matricula19 = informarNaoEntregaDocumentosActionForm.getMatricula19();
		String matricula20 = informarNaoEntregaDocumentosActionForm.getMatricula20();
		String qtTentativas1 = informarNaoEntregaDocumentosActionForm.getQtTentativas1();
		String qtTentativas2 = informarNaoEntregaDocumentosActionForm.getQtTentativas2();
		String qtTentativas3 = informarNaoEntregaDocumentosActionForm.getQtTentativas3();
		String qtTentativas4 = informarNaoEntregaDocumentosActionForm.getQtTentativas4();
		String qtTentativas5 = informarNaoEntregaDocumentosActionForm.getQtTentativas5();
		String qtTentativas6 = informarNaoEntregaDocumentosActionForm.getQtTentativas6();
		String qtTentativas7 = informarNaoEntregaDocumentosActionForm.getQtTentativas7();
		String qtTentativas8 = informarNaoEntregaDocumentosActionForm.getQtTentativas8();
		String qtTentativas9 = informarNaoEntregaDocumentosActionForm.getQtTentativas9();
		String qtTentativas10 = informarNaoEntregaDocumentosActionForm.getQtTentativas10();
		String qtTentativas11 = informarNaoEntregaDocumentosActionForm.getQtTentativas11();
		String qtTentativas12 = informarNaoEntregaDocumentosActionForm.getQtTentativas12();
		String qtTentativas13 = informarNaoEntregaDocumentosActionForm.getQtTentativas13();
		String qtTentativas14 = informarNaoEntregaDocumentosActionForm.getQtTentativas14();
		String qtTentativas15 = informarNaoEntregaDocumentosActionForm.getQtTentativas15();
		String qtTentativas16 = informarNaoEntregaDocumentosActionForm.getQtTentativas16();
		String qtTentativas17 = informarNaoEntregaDocumentosActionForm.getQtTentativas17();
		String qtTentativas18 = informarNaoEntregaDocumentosActionForm.getQtTentativas18();
		String qtTentativas19 = informarNaoEntregaDocumentosActionForm.getQtTentativas19();
		String qtTentativas20 = informarNaoEntregaDocumentosActionForm.getQtTentativas20();

		// Tipo do Documento
		DocumentoTipo documentoTipo = new DocumentoTipo();
		if(tipoDocumento != null && !tipoDocumento.equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();

			filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, tipoDocumento));

			filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna Tipo do Documento
			Collection colecaoDocumentoTipo = fachada.pesquisar(filtroDocumentoTipo, DocumentoTipo.class.getName());

			if(colecaoDocumentoTipo == null || colecaoDocumentoTipo.isEmpty()){

				throw new ActionServletException("atencao.pesquisa_tipo_documento_nao_inexistente");
			}else{
				documentoTipo = (DocumentoTipo) Util.retonarObjetoDeColecao(colecaoDocumentoTipo);
			}
		}

		Date dataDevolucaoFormatada = Util.converteStringParaDate(dataDevolucao);

		Collection colecaoPesquisa = null;

		// Cria um Cliente que será setado no Responsável pela Entrega os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		Cliente cliente = null;

		if(idResponsavelEntrega != null && !idResponsavelEntrega.equals("")){

			cliente = new Cliente();
			cliente.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdResponsavelEntrega()));

		}

		Collection colecaoDocumentosNaoEntregues = new ArrayList();

		// Cria varios tipos de documento que serão setados no banco ao mesmo
		// tempo
		// valores informados pelo
		// usuário na pagina para ser inserido no banco
		DocumentoNaoEntregue documentoNaoEntregue1 = null;
		DocumentoNaoEntregue documentoNaoEntregue2 = null;
		DocumentoNaoEntregue documentoNaoEntregue3 = null;
		DocumentoNaoEntregue documentoNaoEntregue4 = null;
		DocumentoNaoEntregue documentoNaoEntregue5 = null;
		DocumentoNaoEntregue documentoNaoEntregue6 = null;
		DocumentoNaoEntregue documentoNaoEntregue7 = null;
		DocumentoNaoEntregue documentoNaoEntregue8 = null;
		DocumentoNaoEntregue documentoNaoEntregue9 = null;
		DocumentoNaoEntregue documentoNaoEntregue10 = null;
		DocumentoNaoEntregue documentoNaoEntregue11 = null;
		DocumentoNaoEntregue documentoNaoEntregue12 = null;
		DocumentoNaoEntregue documentoNaoEntregue13 = null;
		DocumentoNaoEntregue documentoNaoEntregue14 = null;
		DocumentoNaoEntregue documentoNaoEntregue15 = null;
		DocumentoNaoEntregue documentoNaoEntregue16 = null;
		DocumentoNaoEntregue documentoNaoEntregue17 = null;
		DocumentoNaoEntregue documentoNaoEntregue18 = null;
		DocumentoNaoEntregue documentoNaoEntregue19 = null;
		DocumentoNaoEntregue documentoNaoEntregue20 = null;

		// Motivo
		// Cria varios Motivos que serão setados no Motivo da Não entrega de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento1 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento2 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento3 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento4 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento5 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento6 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento7 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento8 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento9 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento10 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento11 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento12 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento13 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento14 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento15 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento16 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento17 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento18 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento19 = new MotivoNaoEntregaDocumento();
		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento20 = new MotivoNaoEntregaDocumento();

		// Tabela 1
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco
		if(idCodigo1 != null && !idCodigo1.equals("")
						&& !idCodigo1.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue1 = new DocumentoNaoEntregue();
			documentoNaoEntregue1.setCliente(cliente);
			documentoNaoEntregue1.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue1.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue1.setNumeroTentativa(new Short(qtTentativas1));
			documentoNaoEntregue1.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento1.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo1()));

			documentoNaoEntregue1.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento1);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){

				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula1));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue1.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);
				guiaPagamentoGeral.setId(new Integer(numeroDocumento1));
				documentoNaoEntregue1.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento1));
				documentoNaoEntregue1.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue1);

		}

		// Tabela 2
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo2 != null && !idCodigo2.equals("")
						&& !idCodigo2.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue2 = new DocumentoNaoEntregue();
			documentoNaoEntregue2.setCliente(cliente);
			documentoNaoEntregue2.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue2.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue2.setNumeroTentativa(new Short(qtTentativas2));
			documentoNaoEntregue2.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento2.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo2()));

			documentoNaoEntregue2.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento2);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula2));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue2.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento2));
				documentoNaoEntregue2.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento2));
				documentoNaoEntregue2.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue2);
		}
		// Tabela 3
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo3 != null && !idCodigo3.equals("")
						&& !idCodigo3.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue3 = new DocumentoNaoEntregue();
			documentoNaoEntregue3.setCliente(cliente);
			documentoNaoEntregue3.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue3.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue3.setNumeroTentativa(new Short(qtTentativas3));
			documentoNaoEntregue3.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento3.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo3()));

			documentoNaoEntregue3.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento3);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula3));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue3.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento3));
				documentoNaoEntregue3.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento3));
				documentoNaoEntregue3.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue3);

		}

		// Tabela 4
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo4 != null && !idCodigo4.equals("")
						&& !idCodigo4.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue4 = new DocumentoNaoEntregue();
			documentoNaoEntregue4.setCliente(cliente);
			documentoNaoEntregue4.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue4.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue4.setNumeroTentativa(new Short(qtTentativas4));
			documentoNaoEntregue4.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento4.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo4()));

			documentoNaoEntregue4.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento4);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula4));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);

				documentoNaoEntregue4.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);
				guiaPagamentoGeral.setId(new Integer(numeroDocumento4));
				documentoNaoEntregue4.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento4));
				documentoNaoEntregue4.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue4);

		}

		// Tabela 5
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo5 != null && !idCodigo5.equals("")
						&& !idCodigo5.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue5 = new DocumentoNaoEntregue();
			documentoNaoEntregue5.setCliente(cliente);
			documentoNaoEntregue5.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue5.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue5.setNumeroTentativa(new Short(qtTentativas5));
			documentoNaoEntregue5.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento5.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo5()));

			documentoNaoEntregue5.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento5);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula5));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue5.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento5));
				documentoNaoEntregue5.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento5));
				documentoNaoEntregue5.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue5);

		}
		// Tabela 6
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo6 != null && !idCodigo6.equals("")
						&& !idCodigo6.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue6 = new DocumentoNaoEntregue();
			documentoNaoEntregue6.setCliente(cliente);
			documentoNaoEntregue6.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue6.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue6.setNumeroTentativa(new Short(qtTentativas6));
			documentoNaoEntregue6.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento6.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo6()));

			documentoNaoEntregue6.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento6);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula6));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue6.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento6));
				documentoNaoEntregue6.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento6));
				documentoNaoEntregue6.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue6);

		}
		// Tabela 7
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo7 != null && !idCodigo7.equals("")
						&& !idCodigo7.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue7 = new DocumentoNaoEntregue();
			documentoNaoEntregue7.setCliente(cliente);
			documentoNaoEntregue7.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue7.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue7.setNumeroTentativa(new Short(qtTentativas7));
			documentoNaoEntregue7.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento7.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo7()));

			documentoNaoEntregue7.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento7);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula7));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue7.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento7));
				documentoNaoEntregue7.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento7));
				documentoNaoEntregue7.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue7);

		}
		// Tabela 8
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo8 != null && !idCodigo8.equals("")
						&& !idCodigo8.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue8 = new DocumentoNaoEntregue();
			documentoNaoEntregue8.setCliente(cliente);
			documentoNaoEntregue8.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue8.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue8.setNumeroTentativa(new Short(qtTentativas8));
			documentoNaoEntregue8.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento8.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo8()));

			documentoNaoEntregue8.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento8);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula8));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue8.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento8));
				documentoNaoEntregue8.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento8));
				documentoNaoEntregue8.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue8);

		}
		// Tabela 9
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo9 != null && !idCodigo9.equals("")
						&& !idCodigo9.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue9 = new DocumentoNaoEntregue();
			documentoNaoEntregue9.setCliente(cliente);
			documentoNaoEntregue9.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue9.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue9.setNumeroTentativa(new Short(qtTentativas9));
			documentoNaoEntregue9.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento9.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo9()));

			documentoNaoEntregue9.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento9);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula9));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue9.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento9));
				documentoNaoEntregue9.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento9));
				documentoNaoEntregue9.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue9);

		}
		// Tabela 10
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo10 != null && !idCodigo10.equals("")
						&& !idCodigo10.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue10 = new DocumentoNaoEntregue();
			documentoNaoEntregue10.setCliente(cliente);
			documentoNaoEntregue10.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue10.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue10.setNumeroTentativa(new Short(qtTentativas10));
			documentoNaoEntregue10.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento10.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo10()));

			documentoNaoEntregue10.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento10);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula10));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue10.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento10));
				documentoNaoEntregue10.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento10));
				documentoNaoEntregue10.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue10);

		}

		// Tabela 11
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo11 != null && !idCodigo11.equals("")
						&& !idCodigo11.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue11 = new DocumentoNaoEntregue();
			documentoNaoEntregue11.setCliente(cliente);
			documentoNaoEntregue11.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue11.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue11.setNumeroTentativa(new Short(qtTentativas11));
			documentoNaoEntregue11.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento11.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo11()));

			documentoNaoEntregue11.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento11);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula11));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue11.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento11));
				documentoNaoEntregue11.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento11));
				documentoNaoEntregue11.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue11);

		}
		// Tabela 12
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo12 != null && !idCodigo12.equals("")
						&& !idCodigo12.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue12 = new DocumentoNaoEntregue();
			documentoNaoEntregue12.setCliente(cliente);
			documentoNaoEntregue12.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue12.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue12.setNumeroTentativa(new Short(qtTentativas12));
			documentoNaoEntregue12.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento12.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo12()));

			documentoNaoEntregue12.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento12);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula12));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue12.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento12));
				documentoNaoEntregue12.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento12));
				documentoNaoEntregue12.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue12);

		}
		// Tabela 13
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo13 != null && !idCodigo13.equals("")
						&& !idCodigo13.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue13 = new DocumentoNaoEntregue();
			documentoNaoEntregue13.setCliente(cliente);
			documentoNaoEntregue13.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue13.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue13.setNumeroTentativa(new Short(qtTentativas13));
			documentoNaoEntregue13.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento13.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo13()));

			documentoNaoEntregue13.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento13);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula13));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue13.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento13));
				documentoNaoEntregue13.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento13));
				documentoNaoEntregue13.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue13);

		}
		// Tabela 14
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo14 != null && !idCodigo14.equals("")
						&& !idCodigo14.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue14 = new DocumentoNaoEntregue();
			documentoNaoEntregue14.setCliente(cliente);
			documentoNaoEntregue14.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue14.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue14.setNumeroTentativa(new Short(qtTentativas14));
			documentoNaoEntregue14.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento14.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo14()));

			documentoNaoEntregue14.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento14);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula14));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue14.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento14));
				documentoNaoEntregue14.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento14));
				documentoNaoEntregue14.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue14);

		}
		// Tabela 15
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo15 != null && !idCodigo15.equals("")
						&& !idCodigo15.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue15 = new DocumentoNaoEntregue();
			documentoNaoEntregue15.setCliente(cliente);
			documentoNaoEntregue15.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue15.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue15.setNumeroTentativa(new Short(qtTentativas15));
			documentoNaoEntregue15.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento15.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo15()));

			documentoNaoEntregue15.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento15);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula15));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue15.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento15));
				documentoNaoEntregue15.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento15));
				documentoNaoEntregue15.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue15);

		}
		// Tabela 16
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo16 != null && !idCodigo16.equals("")
						&& !idCodigo16.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue16 = new DocumentoNaoEntregue();
			documentoNaoEntregue16.setCliente(cliente);
			documentoNaoEntregue16.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue16.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue16.setNumeroTentativa(new Short(qtTentativas16));
			documentoNaoEntregue16.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento16.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo16()));

			documentoNaoEntregue16.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento16);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula16));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue16.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento16));
				documentoNaoEntregue16.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento16));
				documentoNaoEntregue16.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue16);

		}
		// Tabela 17
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo17 != null && !idCodigo17.equals("")
						&& !idCodigo17.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue17 = new DocumentoNaoEntregue();
			documentoNaoEntregue17.setCliente(cliente);
			documentoNaoEntregue17.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue17.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue17.setNumeroTentativa(new Short(qtTentativas17));
			documentoNaoEntregue17.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento17.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo17()));

			documentoNaoEntregue17.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento17);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula17));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue17.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento17));
				documentoNaoEntregue17.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento17));
				documentoNaoEntregue17.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue17);

		}
		// Tabela 18
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo18 != null && !idCodigo18.equals("")
						&& !idCodigo18.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue18 = new DocumentoNaoEntregue();
			documentoNaoEntregue18.setCliente(cliente);
			documentoNaoEntregue18.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue18.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue18.setNumeroTentativa(new Short(qtTentativas18));
			documentoNaoEntregue18.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento18.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo18()));

			documentoNaoEntregue18.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento18);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula18));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue18.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento18));
				documentoNaoEntregue18.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento18));
				documentoNaoEntregue18.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue18);

		}
		// Tabela 19
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo19 != null && !idCodigo19.equals("")
						&& !idCodigo19.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue19 = new DocumentoNaoEntregue();
			documentoNaoEntregue19.setCliente(cliente);
			documentoNaoEntregue19.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue19.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue19.setNumeroTentativa(new Short(qtTentativas19));
			documentoNaoEntregue19.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento19.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo19()));

			documentoNaoEntregue19.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento19);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula19));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue19.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento19));
				documentoNaoEntregue19.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento19));
				documentoNaoEntregue19.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue19);

		}
		// Tabela 20
		// Cria todos os campos a serem setados no banco e que serão setados na classe Não entrega
		// de Documentos
		// os
		// valores informados pelo
		// usuário na pagina para ser inserido no banco

		if(idCodigo20 != null && !idCodigo20.equals("")
						&& !idCodigo20.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

			documentoNaoEntregue20 = new DocumentoNaoEntregue();
			documentoNaoEntregue20.setCliente(cliente);
			documentoNaoEntregue20.setDataTentativaEntrega(dataDevolucaoFormatada);
			documentoNaoEntregue20.setDocumentoTipo(documentoTipo);
			documentoNaoEntregue20.setNumeroTentativa(new Short(qtTentativas20));
			documentoNaoEntregue20.setUltimaAlteracao(new Date());

			motivoNaoEntregaDocumento20.setId(new Integer(informarNaoEntregaDocumentosActionForm.getIdCodigo20()));

			documentoNaoEntregue20.setMotivoNaoEntregaDocumento(motivoNaoEntregaDocumento20);

			// verificar se a Entrega foi Conta ou outro Tipo de Documento
			if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.CONTA.toString())){
				ContaGeral contaGeral = new ContaGeral();
				Conta conta = new Conta();
				Imovel imovelConta = new Imovel();

				imovelConta.setId(new Integer(matricula20));

				conta.setImovel(imovelConta);
				contaGeral.setConta(conta);
				documentoNaoEntregue20.setContaGeral(contaGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.GUIA_PAGAMENTO.toString())){
				GuiaPagamentoGeral guiaPagamentoGeral = new GuiaPagamentoGeral();

				GuiaPagamento guiaPagamento = new GuiaPagamento();

				guiaPagamentoGeral.setGuiaPagamento(guiaPagamento);

				guiaPagamentoGeral.setId(new Integer(numeroDocumento20));
				documentoNaoEntregue20.setGuiaPagamentoGeral(guiaPagamentoGeral);

			}else if(informarNaoEntregaDocumentosActionForm.getTipoDocumento().equals(DocumentoTipo.FATURA_CLIENTE.toString())){
				Fatura fatura = new Fatura();

				fatura = new Fatura();

				fatura.setId(new Integer(numeroDocumento20));
				documentoNaoEntregue20.setFatura(fatura);
			}

			colecaoDocumentosNaoEntregues.add(documentoNaoEntregue20);

		}

		// Informar um Documento Não Entregue na base, mas fazendo, antes,
		// algumas verificações no ControladorFaturamento.

		fachada.informarNaoEntregaDocumentos(colecaoDocumentosNaoEntregues, usuarioLogado, Util.formatarMesAnoParaAnoMes(mesAnoConta));

		// Exibe a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Documentos Não Entregues " + colecaoDocumentosNaoEntregues.size()
						+ " informado com sucesso.", "Informar outro Documentos Não Entregues",
						"exibirInformarNaoEntregaDocumentosAction.do?menu=sim");

		return retorno;

	}

}