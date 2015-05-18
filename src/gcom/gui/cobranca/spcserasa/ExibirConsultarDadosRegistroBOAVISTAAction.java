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
 * Yara Taciane de Souza
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

package gcom.gui.cobranca.spcserasa;

import gcom.cobranca.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.spcserasa.FiltroNegativadorMovimentoRegRetMot;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Permite a consulta dos dados de um registro no formato padrão BOAVISTA.
 * 
 * @author Gicevalter Couto
 * @date 08/04/2015
 */
public class ExibirConsultarDadosRegistroBOAVISTAAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		ConsultarDadosRegistroActionForm form = (ConsultarDadosRegistroActionForm) actionForm;

		NegativadorMovimentoReg negativadorMovimentoReg = (NegativadorMovimentoReg) sessao.getAttribute("negativadorMovimentoReg");

		NegativadorRegistroTipo negativadorRegistroTipo = negativadorMovimentoReg.getNegativadorRegistroTipo();

		if(negativadorRegistroTipo != null){

			if(negativadorRegistroTipo.getId().equals(NegativadorRegistroTipo.ID_BOA_VISTA_HEADER)){
				retorno = exibirDadosRegistroTipoHeader(negativadorMovimentoReg, form, fachada, sessao, httpServletRequest, actionMapping);
			}else if(negativadorRegistroTipo.getId().equals(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_NOME_BOA_VISTA)){
				retorno = exibirDadosRegistroTipoDetalheNome(negativadorMovimentoReg, form, fachada, sessao, httpServletRequest,
								actionMapping);
			}else if(negativadorRegistroTipo.getId().equals(NegativadorRegistroTipo.ID_SPC_DETALHE_CONSUMIDOR_ENDERECO_BOA_VISTA)){
				retorno = exibirDadosRegistroTipoDetalheEndereco(negativadorMovimentoReg, form, fachada, sessao, httpServletRequest,
								actionMapping);
			}else if(negativadorRegistroTipo.getId().equals(NegativadorRegistroTipo.ID_SPC_DETALHE_OCORRENCIA_SPC_BOA_VISTA)){
				retorno = exibirDadosRegistroTipoDetalheOcorrencia(negativadorMovimentoReg, form, fachada, sessao, httpServletRequest,
								actionMapping);
			}else if(negativadorRegistroTipo.getId().equals(NegativadorRegistroTipo.ID_BOA_VISTA_TRAILLER)){
				retorno = exibirDadosRegistroTipoTrailler(negativadorMovimentoReg, form, fachada, sessao, httpServletRequest, actionMapping);
			}else{

				throw new ActionServletException("atencao.codigo_tipo_registro_inexistente");
			}
		}

		FiltroNegativadorMovimentoRegRetMot filtroNegativadorMovimentoRegRetMot = new FiltroNegativadorMovimentoRegRetMot();
		filtroNegativadorMovimentoRegRetMot.adicionarParametro(new ParametroSimples(
						FiltroNegativadorMovimentoRegRetMot.NEGATIVADOR_MOVIMENTO_REG_ID, negativadorMovimentoReg.getId()));
		filtroNegativadorMovimentoRegRetMot.adicionarCaminhoParaCarregamentoEntidade("negativadorRetornoMotivo");

		Collection<NegativadorMovimentoRegRetMot> collNegativadorMovimentoRegRetMot = fachada.pesquisar(
						filtroNegativadorMovimentoRegRetMot, NegativadorMovimentoRegRetMot.class.getName());

		form.setCollNegativadorMovimentoRegRetMot(collNegativadorMovimentoRegRetMot);
		sessao.setAttribute("collNegativadorMovimentoRegRetMot", collNegativadorMovimentoRegRetMot);

		return retorno;
	}

	private ActionForward exibirDadosRegistroTipoHeader(NegativadorMovimentoReg negativadorMovimentoReg,
					ConsultarDadosRegistroActionForm form, Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest,
					ActionMapping actionMapping){

		ActionForward retorno = actionMapping.findForward("consultarDadosRegistroBOAVISTAHeader");

		Negativador negativador = negativadorMovimentoReg.getNegativadorMovimento().getNegativador();
		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		if(negativador != null && !negativador.equals("")){
			form.setNegativador(negativador.getCliente().getNome());
		}else{
			form.setNegativador("");
		}

		Short codigoMovimento = negativadorMovimento.getCodigoMovimento();
		if(codigoMovimento != null && !codigoMovimento.equals("")){
			form.setTipoMovimento(codigoMovimento.toString());
		}else{
			form.setTipoMovimento("");
		}

		String tipoRegistroCodigo = negativadorMovimentoReg.getNegativadorRegistroTipo().getCodigoRegistro();
		if(tipoRegistroCodigo != null && !tipoRegistroCodigo.equals("")){
			form.setTipoRegistroCodigo(tipoRegistroCodigo);
		}else{
			form.setTipoRegistroCodigo("");
		}

		String tipoRegistroDescricao = negativadorMovimentoReg.getNegativadorRegistroTipo().getDescricaoRegistroTipo();
		if(tipoRegistroDescricao != null && !tipoRegistroDescricao.equals("")){
			form.setTipoRegistroDescricao(tipoRegistroDescricao);
		}else{
			form.setTipoRegistroDescricao("");
		}

		// Conteúdo Registro
		String conteudoRegistro = negativadorMovimentoReg.getConteudoRegistro();
		conteudoRegistro = Util.completaString(conteudoRegistro, 600);

		if(conteudoRegistro != null && !conteudoRegistro.equals("")){

			// Cod Pos Ini Pos Fin Descricao
			// H.01 1 8 Código do Associado
			// H.02 9 18 Constante (0000000000)
			// H.03 19 24 Data da Geração do Movimento no formato: 'DDMMAA'
			// H.04 25 31 Identificação do movimento (REMESSA ou RETORNO)
			// H.05 32 86 Nome da Empresa
			// H.06 87 87 Controle de Remessa
			// H.07 88 94 Número de Sequência do Arquivo
			// H.08 95 102 Constante (brancos)
			// H.09 103 104 Constante (04)
			// H.10 105 250 Constante (brancos)

			// H.04
			String codigoEnvio = "";
			String descricaoCodigoEnvio = conteudoRegistro.substring(24, 31);
			if(descricaoCodigoEnvio != null && !descricaoCodigoEnvio.equals("")){
				if(descricaoCodigoEnvio.equalsIgnoreCase("REMESSA")){
					codigoEnvio = "E";
				}else if(descricaoCodigoEnvio.equalsIgnoreCase("RETORNO")){
					codigoEnvio = "R";
				}
				form.setCodigoEnvio(codigoEnvio);
				form.setDescricaoCodigoEnvio(descricaoCodigoEnvio);

			}else{
				form.setCodigoEnvio("");
				form.setDescricaoCodigoEnvio("");
			}

			// H.01
			String codigoAssociado = conteudoRegistro.substring(0, 8);
			if(codigoAssociado != null && !codigoAssociado.equals("")){
				form.setCnpj(codigoAssociado);
			}else{
				form.setCnpj("");
			}

			// H.03
			String dataFormatada = conteudoRegistro.substring(18, 22).trim() + conteudoRegistro.substring(22, 24).trim();
			if(dataFormatada != null && !dataFormatada.equals("")){
				form.setDataMovimento(dataFormatada);
			}else{
				form.setDataMovimento("");
			}

			// H.05
			String nomeContato = conteudoRegistro.substring(31, 86);
			if(nomeContato != null && !nomeContato.equals("")){
				form.setNomeContato(nomeContato);
			}else{
				form.setNomeContato("");
			}

			// H.06
			String identificacaoArquivo = conteudoRegistro.substring(86, 87);
			if(identificacaoArquivo != null && !identificacaoArquivo.equals("")){
				form.setIdentificacaoArquivo(identificacaoArquivo);
			}else{
				form.setIdentificacaoArquivo("");
			}

			// H.07
			String sequencialRemessa = conteudoRegistro.substring(87, 94);
			if(sequencialRemessa != null && !sequencialRemessa.equals("")){
				form.setSequencialRemessa(sequencialRemessa);
			}else{
				form.setSequencialRemessa("");
			}

			Short indicadorAceito = negativadorMovimentoReg.getIndicadorAceito();
			if(indicadorAceito != null && !indicadorAceito.equals("")){

				if(indicadorAceito.equals(ConstantesSistema.ACEITO)){
					form.setIndicadorAceitacao("SIM");
				}else{
					form.setIndicadorAceitacao("NÃO");
				}

			}else{
				form.setIndicadorAceitacao("");
			}

		}

		return retorno;
	}

	/**
	 * --------------------------------------------------------------------------------------------
	 * ------------
	 */

	private ActionForward exibirDadosRegistroTipoDetalheNome(NegativadorMovimentoReg negativadorMovimentoReg,
					ConsultarDadosRegistroActionForm form, Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest,
					ActionMapping actionMapping){

		ActionForward retorno = actionMapping.findForward("consultarDadosRegistroBOAVISTADetalheNome");

		Negativador negativador = negativadorMovimentoReg.getNegativadorMovimento().getNegativador();
		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		if(negativador != null && !negativador.equals("")){
			form.setNegativador(negativador.getCliente().getNome());
		}else{
			form.setNegativador("");
		}

		Short codigoMovimento = negativadorMovimento.getCodigoMovimento();
		if(codigoMovimento != null && !codigoMovimento.equals("")){
			form.setTipoMovimento(codigoMovimento.toString());
		}else{
			form.setTipoMovimento("");
		}

		String tipoRegistroCodigo = negativadorMovimentoReg.getNegativadorRegistroTipo().getCodigoRegistro();
		if(tipoRegistroCodigo != null && !tipoRegistroCodigo.equals("")){
			form.setTipoRegistroCodigo(tipoRegistroCodigo);
		}else{
			form.setTipoRegistroCodigo("");
		}

		String tipoRegistroDescricao = negativadorMovimentoReg.getNegativadorRegistroTipo().getDescricaoRegistroTipo();
		if(tipoRegistroDescricao != null && !tipoRegistroDescricao.equals("")){
			form.setTipoRegistroDescricao(tipoRegistroDescricao);
		}else{
			form.setTipoRegistroDescricao("");
		}

		// Conteúdo Registro
		String conteudoRegistro = negativadorMovimentoReg.getConteudoRegistro();
		conteudoRegistro = Util.completaString(conteudoRegistro, 600);

		if(conteudoRegistro != null && !conteudoRegistro.equals("")){
			// D1.01 1 8 NUM(8) Código do Associado
			// D1.02 9 9 CHAR(1) '1'
			// D1.03 10 14 NUM(5) N de Sequencia do Registro
			// D1.04 15 15 CHAR(1) Codigo do Sistema: '1' SCPC, '2' UseCheque, '3' Central de
			// Credito / Passagens, '4' Recuperacao de Credito
			// D1.05 16 16 CHAR(1) 'A'
			// D1.06 17 18 CHAR(2) '10'
			// D1.07 19 38 CHAR (20) Documento Principal
			// D1.08 39 58 CHAR (20) Documento Secundario
			// D1.09 59 078 CHAR (20) Documento Terciario
			// D1.10 79 128 CHAR (50) Nome do Devedor, sendo Pessoa Fisica, ate 40 posicoes
			// D1.11 129 134 CHAR (6) Brancos
			// D1.12 135 142 NUM(8) Data de Nascimento do cliente, 'DDMMAAAA' ou '00000000'
			// D1.13 143 182 CHAR (40) Nome do Cônjuge
			// D1.14 183 198 CHAR (16) Brancos
			// D1.15 199 204 NUM(6) '000000'
			// D1.16 205 224 CHAR (20) Quando informado, deve-se informar o campo 17 (Unidade da
			// Federação)
			// D1.17 225 226 CHAR (2) Informar a Unidade da Federação quando for informado o campo
			// 16 (Naturalidade)
			// D1.18 227 230 CHAR (4) Brancos
			// D1.19 231 250 CHAR (20) Código de Retorno


			// D1.01
			String codigoAssociado = conteudoRegistro.substring(1, 8);
			if(codigoAssociado != null && !codigoAssociado.equals("")){
				form.setCnpj(codigoAssociado);
			}else{
				form.setCnpj("");
			}

			// D1.03
			String sequencialRegistro = conteudoRegistro.substring(9, 14);
			if(sequencialRegistro != null && !sequencialRegistro.equals("")){
				form.setSequencialRegistro(sequencialRegistro);
			}else{
				form.setSequencialRegistro("");
			}

			// D1.04
			String codigoSistema = conteudoRegistro.substring(14, 15);
			if(codigoSistema != null && !codigoSistema.equals("")){
				form.setCodigoSistema(codigoSistema);

				if(codigoSistema.trim().equals("1")){
					form.setDescricaoCodigoSistema("SCPC");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("UseCheque");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("Central de Crédito / Passagens");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("Recuperacao de Crédito");
				}else{
					form.setDescricaoCodigoSistema("Não identificado");
				}

			}else{
				form.setCodigoSistema("");
				form.setDescricaoCodigoSistema("");
			}
			
			// D1.07
			String documentoPrincipal = conteudoRegistro.substring(18, 38);
			if(documentoPrincipal != null && !documentoPrincipal.equals("")){
				form.setDocumentoPrincipal(documentoPrincipal); 
			}else{
				form.setDocumentoPrincipal("");
			}
			
			// D1.08
			String documentoSecundario = conteudoRegistro.substring(38, 58);
			if(documentoSecundario != null && !documentoSecundario.equals("")){
				form.setDocumentoSecundario(documentoSecundario);
			}else{
				form.setDocumentoSecundario("");
			}

			// D1.09
			String documentoTerciario = conteudoRegistro.substring(58, 78);
			if(documentoTerciario != null && !documentoTerciario.equals("")){
				form.setDocumentoTerciario(documentoTerciario);
			}else{
				form.setDocumentoTerciario("");
			}
			
			// D1.10
			String nomeDevedor = conteudoRegistro.substring(78,128);
			if(nomeDevedor != null && !nomeDevedor.equals("")){
				form.setNomeDevedor(nomeDevedor);
			}else{
				form.setNomeDevedor("");
			}
			
			// D1.12
			String dataNascimento = conteudoRegistro.substring(134, 142).trim();
			if(dataNascimento != null && !dataNascimento.equals("") && !dataNascimento.equals("00000000")){
				form.setDataNascimento(dataNascimento);
			}else{
				form.setDataNascimento("");
			}			

			// D1.13
			String nomeConjuge = conteudoRegistro.substring(142, 182);
			if(nomeConjuge != null && !nomeConjuge.equals("")){
				form.setNomeConjuge(nomeConjuge);
			}else{
				form.setNomeConjuge("");
			}

			// D1.16
			String naturalidade = conteudoRegistro.substring(224, 226);
			if(naturalidade != null && !naturalidade.equals("")){
				form.setNaturalidade(naturalidade);
			}else{
				form.setNaturalidade("");
			}

			// D1.17
			String unidadeFederacao = conteudoRegistro.substring(204, 224);
			if(unidadeFederacao != null && !unidadeFederacao.equals("")){
				form.setUnidadeFederacao(unidadeFederacao);
			}else{
				form.setUnidadeFederacao("");
			}

			// D1.19
			String codigoRetorno = conteudoRegistro.substring(230, 250);
			if(codigoRetorno != null && !codigoRetorno.equals("")){
				form.setCodigoRetorno(codigoRetorno);
			}else{
				form.setCodigoRetorno("");
			}


			Short indicadorAceito = negativadorMovimentoReg.getIndicadorAceito();
			if(indicadorAceito != null && !indicadorAceito.equals("")){

				if(indicadorAceito.equals(ConstantesSistema.ACEITO)){
					form.setIndicadorAceitacao("SIM");
				}else{
					form.setIndicadorAceitacao("NÃO");
				}

			}else{
				form.setIndicadorAceitacao("");
			}
		}

		return retorno;
	}

	/**
	 * --------------------------------------------------------------------------------------------
	 * ------------
	 */

	private ActionForward exibirDadosRegistroTipoDetalheEndereco(NegativadorMovimentoReg negativadorMovimentoReg,
					ConsultarDadosRegistroActionForm form, Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest,
					ActionMapping actionMapping){

		ActionForward retorno = actionMapping.findForward("consultarDadosRegistroBOAVISTADetalheEndereco");

		Negativador negativador = negativadorMovimentoReg.getNegativadorMovimento().getNegativador();
		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		if(negativador != null && !negativador.equals("")){
			form.setNegativador(negativador.getCliente().getNome());
		}else{
			form.setNegativador("");
		}

		Short codigoMovimento = negativadorMovimento.getCodigoMovimento();
		if(codigoMovimento != null && !codigoMovimento.equals("")){
			form.setTipoMovimento(codigoMovimento.toString());
		}else{
			form.setTipoMovimento("");
		}

		String tipoRegistroCodigo = negativadorMovimentoReg.getNegativadorRegistroTipo().getCodigoRegistro();
		if(tipoRegistroCodigo != null && !tipoRegistroCodigo.equals("")){
			form.setTipoRegistroCodigo(tipoRegistroCodigo);
		}else{
			form.setTipoRegistroCodigo("");
		}

		String tipoRegistroDescricao = negativadorMovimentoReg.getNegativadorRegistroTipo().getDescricaoRegistroTipo();
		if(tipoRegistroDescricao != null && !tipoRegistroDescricao.equals("")){
			form.setTipoRegistroDescricao(tipoRegistroDescricao);
		}else{
			form.setTipoRegistroDescricao("");
		}

		// Conteúdo Registro
		String conteudoRegistro = negativadorMovimentoReg.getConteudoRegistro();
		conteudoRegistro = Util.completaString(conteudoRegistro, 600);

		if(conteudoRegistro != null && !conteudoRegistro.equals("")){
			// D1.01 1 8 NUM(8) Código do Associado
			// D1.02 9 9 CHAR(1) '1'
			// D1.03 10 14 NUM(5) N de Sequencia do Registro
			// D1.04 15 15 CHAR(1) Codigo do Sistema: '1' SCPC, '2' UseCheque, '3' Central de
			// Credito / Passagens, '4' Recuperacao de Credito
			// D1.05 16 16 CHAR(1) 'A'
			// D1.06 17 18 CHAR(2) '10'
			// D1.07 19 38 CHAR (20) Documento Principal
			// D2.08	39	39	CHAR (1)	Informar:	'1' - Endereço do Devedor (Sistema '1' e '4'),	'2' - Endereço do Correntista (Sistema '2'),'3' - Endereço do Avalista (Sistema '1'), '4' - Endereço Comercial do Devedor (Sistema'4')
			// D2.09	40	89	CHAR (50)	Informar tipo e nome do logradouro, número e complemento (Apto, Bloco, Sala, etc. ..) 
			// D2.10	90	109	CHAR (20)	Nome do Bairro (Opcional)
			// D2.11	110	117	NUM (8)		Número do CEP
			// D2.12	118	137	CHAR (20)	Nome da Cidade
			// D2.13 138 139 CHAR (2) Sigla da Unidade da Federação
			// D2.14 140 159 CHAR (20) Número do Telefone, Sistema '4' (SRC)
			// D2.15 160 230 CHAR (71) Brancos
			// D2.16 231 250 CHAR (20) Código de Retorno
			
			// D1.01
			String codigoAssociado = conteudoRegistro.substring(1, 8);
			if(codigoAssociado != null && !codigoAssociado.equals("")){
				form.setCnpj(codigoAssociado);
			}else{
				form.setCnpj("");
			}

			// D1.03
			String sequencialRegistro = conteudoRegistro.substring(9, 14);
			if(sequencialRegistro != null && !sequencialRegistro.equals("")){
				form.setSequencialRegistro(sequencialRegistro);
			}else{
				form.setSequencialRegistro("");
			}

			// D1.04
			String codigoSistema = conteudoRegistro.substring(14, 15);
			if(codigoSistema != null && !codigoSistema.equals("")){
				form.setCodigoSistema(codigoSistema);

				if(codigoSistema.trim().equals("1")){
					form.setDescricaoCodigoSistema("SCPC");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("UseCheque");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("Central de Crédito / Passagens");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("Recuperacao de Crédito");
				}else{
					form.setDescricaoCodigoSistema("Não identificado");
				}

			}else{
				form.setCodigoSistema("");
				form.setDescricaoCodigoSistema("");
			}

			// D1.07
			String documentoPrincipal = conteudoRegistro.substring(18, 38);
			if(documentoPrincipal != null && !documentoPrincipal.equals("")){
				form.setDocumentoPrincipal(documentoPrincipal);
			}else{
				form.setDocumentoPrincipal("");
			}

			// D1.08
			String codigoTipoEndereco = conteudoRegistro.substring(38, 39);
			if(codigoTipoEndereco != null && !codigoTipoEndereco.equals("")){
				form.setCodigoTipoEndereco(codigoTipoEndereco);
				if(codigoSistema.trim().equals("1") || codigoSistema.trim().equals("4")){
					form.setDescricaoTipoEndereco("Endereço do Devedor");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoTipoEndereco("Endereço do Correntista");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoTipoEndereco("Endereço do Avalista");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoTipoEndereco("Endereço Comercial do Devedor");
				}else{
					form.setDescricaoTipoEndereco("Não identificado");
				}				
			}else{
				form.setCodigoTipoEndereco("");
				form.setDescricaoTipoEndereco("");
			}

			// D1.09
			String endereco = conteudoRegistro.substring(39, 89);
			if(endereco != null && !endereco.equals("")){
				form.setEndereco(endereco);
			}else{
				form.setEndereco("");
			}

			// D1.10
			String bairro = conteudoRegistro.substring(89,	109);
			if(bairro != null && !bairro.equals("")){
				form.setBairro(bairro);
			}else{
				form.setBairro("");
			}

			// D1.11
			String cep = conteudoRegistro.substring(109	,117).trim();
			if(cep != null && !cep.equals("") && !cep.equals("00000000")){
				form.setCep(Util.formatarCEP(cep) );
			}else{
				form.setCep("");
			}

			// D1.12
			String cidade = conteudoRegistro.substring(117,	137);
			if(cidade != null && !cidade.equals("")){
				form.setCidade(cidade);
			}else{
				form.setCidade("");
			}

			// D1.13
			String unidadeFederacao = conteudoRegistro.substring(137, 139);
			if(unidadeFederacao != null && !unidadeFederacao.equals("")){
				form.setUnidadeFederacao(unidadeFederacao);
			}else{
				form.setUnidadeFederacao("");
			}

			// D1.14
			String fone = conteudoRegistro.substring(139, 159);
			if(fone != null && !fone.equals("")){
				form.setFoneNumero(fone);
			}else{
				form.setFoneNumero("");
			}

			// D1.16
			String codigoRetorno = conteudoRegistro.substring(230, 250);
			if(codigoRetorno != null && !codigoRetorno.equals("")){
				form.setCodigoRetorno(codigoRetorno);
			}else{
				form.setCodigoRetorno("");
			}

			Short indicadorAceito = negativadorMovimentoReg.getIndicadorAceito();
			if(indicadorAceito != null && !indicadorAceito.equals("")){

				if(indicadorAceito.equals(ConstantesSistema.ACEITO)){
					form.setIndicadorAceitacao("SIM");
				}else{
					form.setIndicadorAceitacao("NÃO");
				}

			}else{
				form.setIndicadorAceitacao("");
			}
		}

		return retorno;
	}

	/**
	 * --------------------------------------------------------------------------------------------
	 * ------------
	 */

	private ActionForward exibirDadosRegistroTipoDetalheOcorrencia(NegativadorMovimentoReg negativadorMovimentoReg,
					ConsultarDadosRegistroActionForm form, Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest,
					ActionMapping actionMapping){

		ActionForward retorno = actionMapping.findForward("consultarDadosRegistroBOAVISTADetalheOcorrencia");

		Negativador negativador = negativadorMovimentoReg.getNegativadorMovimento().getNegativador();
		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		if(negativador != null && !negativador.equals("")){
			form.setNegativador(negativador.getCliente().getNome());
		}else{
			form.setNegativador("");
		}

		Short codigoMovimento = negativadorMovimento.getCodigoMovimento();
		if(codigoMovimento != null && !codigoMovimento.equals("")){
			form.setTipoMovimento(codigoMovimento.toString());
		}else{
			form.setTipoMovimento("");
		}

		String tipoRegistroCodigo = negativadorMovimentoReg.getNegativadorRegistroTipo().getCodigoRegistro();
		if(tipoRegistroCodigo != null && !tipoRegistroCodigo.equals("")){
			form.setTipoRegistroCodigo(tipoRegistroCodigo);
		}else{
			form.setTipoRegistroCodigo("");
		}

		String tipoRegistroDescricao = negativadorMovimentoReg.getNegativadorRegistroTipo().getDescricaoRegistroTipo();
		if(tipoRegistroDescricao != null && !tipoRegistroDescricao.equals("")){
			form.setTipoRegistroDescricao(tipoRegistroDescricao);
		}else{
			form.setTipoRegistroDescricao("");
		}

		// Conteúdo Registro
		String conteudoRegistro = negativadorMovimentoReg.getConteudoRegistro();
		conteudoRegistro = Util.completaString(conteudoRegistro, 600);

		if(conteudoRegistro != null && !conteudoRegistro.equals("")){
			// D3.01 1 8 NUM(8) Código do Associado
			// D3.02 9 9 CHAR(1) '1'
			// D3.03 10 14 NUM(5) N de Sequencia do Registro
			// D3.04 15 15 CHAR(1) Codigo do Sistema: '1' SCPC, '2' UseCheque, '3' Central de
			// Credito / Passagens, '4' Recuperacao de Credito
			// D3.05	16	16	CHAR (1)	'1'
			// D3.06	17	18	CHAR (2)	Código de Operação:	'10' Inclusão, '20' Exclusão
			// D3.07	19	38	CHAR (20)	Documento Principal
			// D3.08	39	46	NUM (8)		Data da Ocorrência 'DDMMAAAA'
			// D3.09	47	48	CHAR (2)	Tipo de Ocorrência no SCPC
			// D3.10	49	70	CHAR (22)	Contrato
			// D3.11	71	90	CHAR (20)	Documento do Avalista
			// D3.12	91	101	NUM (11,2)	Valor do Débito
			// D3.13	102	103	CHAR (2)	" " Brancos,"CH" Cheque,"CT" Contrato,"OT" OutrosTítulos
			// D3.14	104	120	CHAR (17)	Brancos
			// D3.15	121	121	CHAR (1)	'N'
			// D3.16	122	123	NUM (2)		0
			// D3.17	124	124	CHAR (1)	Opção de Carta com Boleto para o Devedor, sendo: 'S' Boleto Bancário,'A' Arrecadação / Consumo,  'N' Não (Envio da Carta sem Boleto), 'N' Quando ocorrência for SCPCE
			// D3.18	125	125	CHAR (1)	Opção de Carta com Boleto para o Avalista, sendo: 'S' Boleto Bancário, 'A' Arrecadação / Consumo, 'N' Não (Envio da Carta sem Boleto), 'N' Quando ocorrência for SCPCE
			// D3.19	126	133	NUM (8)		Data de Vencimento, no Formato: 'DDMMAAAA'	(deve estar dentro dos 30 dias, a partir da data de	exibição do débito)
			// D3.20 134 144 NUM (11,2) Valor de Cobrança
			// D3.21 145 215 CHAR (71) Brancos
			// D3.22 216 230 CHAR (15) Reservado ao Cliente
			// D3.23 231 250 CHAR (20) Código de Retorno
			

			// D3.01
			String codigoAssociado = conteudoRegistro.substring(1, 8);
			if(codigoAssociado != null && !codigoAssociado.equals("")){
				form.setCnpj(codigoAssociado);
			}else{
				form.setCnpj("");
			}

			// D3.03
			String sequencialRegistro = conteudoRegistro.substring(9, 14);
			if(sequencialRegistro != null && !sequencialRegistro.equals("")){
				form.setSequencialRegistro(sequencialRegistro);
			}else{
				form.setSequencialRegistro("");
			}

			// D3.04
			String codigoSistema = conteudoRegistro.substring(14, 15);
			if(codigoSistema != null && !codigoSistema.equals("")){
				form.setCodigoSistema(codigoSistema);

				if(codigoSistema.trim().equals("1")){
					form.setDescricaoCodigoSistema("SCPC");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("UseCheque");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("Central de Crédito / Passagens");
				}else if(codigoSistema.trim().equals("2")){
					form.setDescricaoCodigoSistema("Recuperacao de Crédito");
				}else{
					form.setDescricaoCodigoSistema("Não identificado");
				}

			}else{
				form.setCodigoSistema("");
				form.setDescricaoCodigoSistema("");
			}

			// D3.06
			String codigoOperacao = conteudoRegistro.substring(16,	18);
			if(codigoOperacao != null && !codigoOperacao.equals("")){
				form.setCodigoOperacao(codigoOperacao);
				
				if(codigoOperacao.trim().equals("10")){
					form.setOperacao("Inclusão");
				}else if(codigoOperacao.trim().equals("20")){
					form.setOperacao("Exclusão");
				}else{
					form.setOperacao("Não identificado");
				}				

			}else{
				form.setOperacao("");
			}
			
			
			// D3.07
			String documentoPrincipal = conteudoRegistro.substring(18,	38);
			if(documentoPrincipal != null && !documentoPrincipal.equals("")){
				form.setDocumentoPrincipal(documentoPrincipal);
			}else{
				form.setDocumentoPrincipal("");
			}	
			
			// D3.08
			String dataOcorrencia = conteudoRegistro.substring(38,	46).trim();
			if(dataOcorrencia != null && !dataOcorrencia.equals("") && !dataOcorrencia.equals("00000000")){
				form.setDataOcorrencia(dataOcorrencia);
			}else{
				form.setDataOcorrencia("");
			}				

			// D3.09
			String tipoOcorrencia = conteudoRegistro.substring(46,	48);
			if(tipoOcorrencia != null && !tipoOcorrencia.equals("")){
				form.setTipoOcorrencia(tipoOcorrencia); 
			}else{
				form.setTipoOcorrencia("");
			}

			// D3.10
			String contrato = conteudoRegistro.substring(48,	70);
			if(contrato != null && !contrato.equals("")){
				form.setContrato(contrato);
			}else{
				form.setContrato("");
			}

			// D3.11
			String documentoAvalista = conteudoRegistro.substring(70,	90);
			if(documentoAvalista != null && !documentoAvalista.equals("")){
				form.setDocumentoAvalista(documentoAvalista);
			}else{
				form.setDocumentoAvalista("");
			}

			// D3.12
			String valorDebito = conteudoRegistro.substring(90,	101).trim();
			if(valorDebito != null && !valorDebito.equals("") ){
				form.setValorDebito(valorDebito); 
			}else{
				form.setValorDebito("");
			}


			// D3.17
			String codigoOpcaoCartaComBoletoParaDevedor = conteudoRegistro.substring(123,	124);
			if(codigoOpcaoCartaComBoletoParaDevedor != null && !codigoOpcaoCartaComBoletoParaDevedor.equals("")){
				form.setCodigoOpcaoCartaComBoletoParaDevedor(codigoOpcaoCartaComBoletoParaDevedor);
				
				if(codigoOpcaoCartaComBoletoParaDevedor.trim().equals("S")){
					form.setDescricaoOpcaoCartaComBoletoParaDevedor("Boleto Bancário");
				}else if(codigoOpcaoCartaComBoletoParaDevedor.trim().equals("A")){
					form.setDescricaoOpcaoCartaComBoletoParaDevedor("Boleto Bancário");
				}else if(codigoOpcaoCartaComBoletoParaDevedor.trim().equals("N")){
					form.setDescricaoOpcaoCartaComBoletoParaDevedor("Envio da Carta sem Boleto / Quando ocorrência for SCPCE");
			
				}else{
					form.setDescricaoOpcaoCartaComBoletoParaDevedor("Não identificado");
				}	
				
			}else{
				form.setCodigoOpcaoCartaComBoletoParaDevedor("");
				form.setDescricaoOpcaoCartaComBoletoParaDevedor("");
			}
			
			// D3.18
			String codigoOpcaoCartaComBoletoParaAvalista = conteudoRegistro.substring(124,	125);
			if(codigoOpcaoCartaComBoletoParaAvalista != null && !codigoOpcaoCartaComBoletoParaAvalista.equals("")){
				form.setCodigoOpcaoCartaComBoletoParaAvalista(codigoOpcaoCartaComBoletoParaDevedor);
				
				if(codigoOpcaoCartaComBoletoParaAvalista.trim().equals("S")){
					form.setDescricaoOpcaoCartaComBoletoParaAvalista("Boleto Bancário");
				}else if(codigoOpcaoCartaComBoletoParaAvalista.trim().equals("A")){
					form.setDescricaoOpcaoCartaComBoletoParaAvalista("Boleto Bancário");
				}else if(codigoOpcaoCartaComBoletoParaAvalista.trim().equals("N")){
					form.setDescricaoOpcaoCartaComBoletoParaAvalista("Envio da Carta sem Boleto / Quando ocorrência for SCPCE");
			
				}else{
					form.setDescricaoOpcaoCartaComBoletoParaAvalista("Não identificado");
				}	
				
			}else{
				form.setCodigoOpcaoCartaComBoletoParaAvalista("");
				form.setDescricaoOpcaoCartaComBoletoParaAvalista("");
			}

			// D3.19
			String dataVencimento = conteudoRegistro.substring(125,	133);
			if(dataVencimento != null && !dataVencimento.equals("")&& !dataVencimento.equals("00000000")){
				form.setDataVencimentoDebito(dataVencimento);
			}else{
				form.setDataVencimentoDebito("");
			}
			
			// D3.20
			String valorCobranca = conteudoRegistro.substring(133, 144).trim();
			if(valorCobranca != null && !valorCobranca.equals("") && !valorCobranca.equals("00000000000")){
				form.setValorCobranca(valorCobranca);
			}else{
				form.setValorCobranca("");
			}

			// D3.23
			String codigoRetorno = conteudoRegistro.substring(230, 250);
			if(codigoRetorno != null && !codigoRetorno.equals("")){
				form.setCodigoRetorno(codigoRetorno);
			}else{
				form.setCodigoRetorno("");
			}

			Short indicadorAceito = negativadorMovimentoReg.getIndicadorAceito();
			if(indicadorAceito != null && !indicadorAceito.equals("")){

				if(indicadorAceito.equals(ConstantesSistema.ACEITO)){
					form.setIndicadorAceitacao("SIM");
				}else{
					form.setIndicadorAceitacao("NÃO");
				}

			}else{
				form.setIndicadorAceitacao("");
			}
		}

		return retorno;
	}

	private ActionForward exibirDadosRegistroTipoTrailler(NegativadorMovimentoReg negativadorMovimentoReg,
					ConsultarDadosRegistroActionForm form, Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest,
					ActionMapping actionMapping){

		ActionForward retorno = actionMapping.findForward("consultarDadosRegistroBOAVISTATrailler");

		Negativador negativador = negativadorMovimentoReg.getNegativadorMovimento().getNegativador();
		NegativadorMovimento negativadorMovimento = negativadorMovimentoReg.getNegativadorMovimento();

		if(negativador != null && !negativador.equals("")){
			form.setNegativador(negativador.getCliente().getNome());
		}else{
			form.setNegativador("");
		}

		Short codigoMovimento = negativadorMovimento.getCodigoMovimento();
		if(codigoMovimento != null && !codigoMovimento.equals("")){
			form.setTipoMovimento(codigoMovimento.toString());
		}else{
			form.setTipoMovimento("");
		}

		String tipoRegistroCodigo = negativadorMovimentoReg.getNegativadorRegistroTipo().getCodigoRegistro();
		if(tipoRegistroCodigo != null && !tipoRegistroCodigo.equals("")){
			form.setTipoRegistroCodigo(tipoRegistroCodigo);
		}else{
			form.setTipoRegistroCodigo("");
		}

		String tipoRegistroDescricao = negativadorMovimentoReg.getNegativadorRegistroTipo().getDescricaoRegistroTipo();
		if(tipoRegistroDescricao != null && !tipoRegistroDescricao.equals("")){
			form.setTipoRegistroDescricao(tipoRegistroDescricao);
		}else{
			form.setTipoRegistroDescricao("");
		}

		// Conteúdo Registro
		String conteudoRegistro = negativadorMovimentoReg.getConteudoRegistro();
		conteudoRegistro = Util.completaString(conteudoRegistro, 600);

		if(conteudoRegistro != null && !conteudoRegistro.equals("")){

			// T.01 1 8 NUM(8) Código do Associado
			// T.02 9 18 NUM(10) Constante (9999999999)
			// T.03 19 24 NUM (6) Data da Geração do Movimento no formato: 'DDMMAA'
			// T.04 25 250 CHAR(226) Constante (brancos)

			// T.01
			String codigoAssociado = conteudoRegistro.substring(0, 8);
			if(codigoAssociado != null && !codigoAssociado.equals("")){
				form.setCnpj(codigoAssociado);
			}else{
				form.setCnpj("");
			}

			// T.03
			String dataGeracaoMovimento = conteudoRegistro.substring(18, 24);
			if(dataGeracaoMovimento != null && !dataGeracaoMovimento.equals("")){
				form.setDataMovimento(dataGeracaoMovimento);
			}else{
				form.setDataMovimento("");
			}

			Short indicadorAceito = negativadorMovimentoReg.getIndicadorAceito();
			if(indicadorAceito != null && !indicadorAceito.equals("")){

				if(indicadorAceito.equals(ConstantesSistema.ACEITO)){
					form.setIndicadorAceitacao("SIM");
				}else{
					form.setIndicadorAceitacao("NÃO");
				}

			}else{
				form.setIndicadorAceitacao("");
			}

		}

		return retorno;
	}

}
