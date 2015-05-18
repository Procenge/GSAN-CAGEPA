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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.gui.relatorio.cobranca;

import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.DocumentoTipo;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.ConsultarDebitoImovelActionForm;
import gcom.interceptor.RegistradorOperacao;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioCertidaoPositivaModelo1;
import gcom.seguranca.acesso.Argumento;
import gcom.seguranca.acesso.DocumentoEmissaoEfetuada;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.cobranca.ModeloRelatorioCertidaoNegativaDebitos;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3154] Gerar Certidão Positiva
 * 
 * @author Anderson Italo
 * @date 07/08/2014
 */
public class GerarRelatorioCertidaoPositivaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		ConsultarDebitoImovelActionForm consultarDebitoImovelActionForm = (ConsultarDebitoImovelActionForm) actionForm;

		String matriculaImovel = "";
		Imovel imovel = new Imovel();

		if(sessao.getAttribute("codigoImovel") != null){

			matriculaImovel = (String) sessao.getAttribute("codigoImovel");
		}else{

			matriculaImovel = consultarDebitoImovelActionForm.getCodigoImovel();
		}

		if(!Util.isVazioOuBranco(matriculaImovel)){
			imovel.setId(new Integer(matriculaImovel));
		}

		// seta os dados das datas digitadas ou informa datas padrão
		// no caso de não ter sido digitado nenhum dado para esses campos
		String anoMesReferenciaInicial = "000101";
		String anoMesReferenciaFinal = "999912";
		Date dataVencimentoInicial = null;
		Date dataVencimentoFinal = null;


		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getReferenciaInicial())){

			anoMesReferenciaInicial = Util.formatarMesAnoParaAnoMes(consultarDebitoImovelActionForm.getReferenciaInicial());
		}

		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getReferenciaFinal())){

			anoMesReferenciaFinal = Util.formatarMesAnoParaAnoMes(consultarDebitoImovelActionForm.getReferenciaFinal());
		}

		SimpleDateFormat formatoDataInicial = new SimpleDateFormat("dd/MM/yyyy");

		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getDataVencimentoInicial())){

			try{

				dataVencimentoInicial = formatoDataInicial.parse(consultarDebitoImovelActionForm.getDataVencimentoInicial());
			}catch(ParseException e){

				e.printStackTrace();
			}
		}else{

			try{

				dataVencimentoInicial = formatoDataInicial.parse("01/01/0001");
			}catch(ParseException e){

				e.printStackTrace();
			}
		}

		SimpleDateFormat formatoDataFinal = new SimpleDateFormat("dd/MM/yyyy");

		if(!Util.isVazioOuBranco(consultarDebitoImovelActionForm.getDataVencimentoFinal())){

			try{

				dataVencimentoFinal = formatoDataFinal.parse(consultarDebitoImovelActionForm.getDataVencimentoFinal());
			}catch(ParseException e){

				e.printStackTrace();
			}
		}else{

			try{

				dataVencimentoFinal = formatoDataFinal.parse("31/12/9999");
			}catch(ParseException e){

				e.printStackTrace();
			}
		}

		String parametroModeloCertidaoPositivaDebitos = "";
		try{

			parametroModeloCertidaoPositivaDebitos = ParametroCobranca.P_MODELO_CERTIDAO_POSITIVA_DEBITOS.executar();
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e.getParametroMensagem().toArray(new String[e.getParametroMensagem().size()]));
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoRelatorio == null){

			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		TarefaRelatorio relatorio = null;

		if(parametroModeloCertidaoPositivaDebitos.equals(ModeloRelatorioCertidaoNegativaDebitos.UM.getValor())){

			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

			DocumentoEmissaoEfetuada documentoEmissaoEfetuada = new DocumentoEmissaoEfetuada();

			// ------------ REGISTRAR TRANSação----------------------------

			documentoEmissaoEfetuada.setImovel(imovel);
			documentoEmissaoEfetuada.setUsuario(usuario);
			DocumentoTipo documentoTipo = new DocumentoTipo();
			documentoTipo.setId(DocumentoTipo.CERTIDAO_POSITIVA);
			documentoEmissaoEfetuada.setDocumentoTipo(documentoTipo);
			documentoEmissaoEfetuada.setUltimaAlteracao(new Date());

			Argumento argumento = new Argumento();
			argumento.setId(Argumento.IMOVEL);

			RegistradorOperacao registradorOperacao = new RegistradorOperacao(Operacao.OPERACAO_EMITIR_DOCUMENTO_CERTIDAO_POSITIVA,
 imovel.getId(),
							argumento, imovel.getId(), new UsuarioAcaoUsuarioHelper(usuario,
											UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO));
			RegistradorOperacao.set(registradorOperacao);
			documentoEmissaoEfetuada.adicionarUsuario(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
			registradorOperacao.registrarOperacao(documentoEmissaoEfetuada);
			this.getFachada().inserir(documentoEmissaoEfetuada);

			// ------------ REGISTRAR TRANSAÇÃO ----------------

			relatorio = new RelatorioCertidaoPositivaModelo1(usuario);

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){

				relatorio.addParametro("usuarioEmissor", usuario.getFuncionario().getId().toString() + "-"
								+ usuario.getFuncionario().getNome());
			}else{

				relatorio.addParametro("usuarioEmissor", usuario.getLogin() + "-" + usuario.getNomeUsuario());
			}

			relatorio.addParametro("matriculaImovel", Util.obterInteger(matriculaImovel));
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			relatorio.addParametro("anoMesReferenciaInicial", anoMesReferenciaInicial);
			relatorio.addParametro("anoMesReferenciaFinal", anoMesReferenciaFinal);
			relatorio.addParametro("dataVencimentoInicial", dataVencimentoInicial);
			relatorio.addParametro("dataVencimentoFinal", dataVencimentoFinal);
		}else{
			throw new ActionServletException("erro.parametro.sistema.valor.invalido", "P_MODELO_CERTIDAO_POSITIVA_DEBITOS");
		}

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}
}
