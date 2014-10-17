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

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.cobranca.ConsultarDebitoImovelActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioCertidaoNegativaModelo1;
import gcom.relatorio.cobranca.RelatorioCertidaoNegativaModelo2;
import gcom.relatorio.cobranca.RelatorioCertidaoNegativaModelo3;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.cobranca.ModeloRelatorioCertidaoNegativaDebitos;
import gcom.util.parametrizacao.cobranca.ParametroCobranca;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela exibição do relatório de certidão negativa
 * 
 * @author Virgínia Melo
 * @date 06/02/2009
 * @author eduardo henrique
 * @date 08/02/2009
 *       Alterações/Correções solicitadas por ADA
 */
public class GerarRelatorioCertidaoNegativaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = null;

		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		ConsultarDebitoImovelActionForm consultarDebitoImovelActionForm = (ConsultarDebitoImovelActionForm) actionForm;

		// Verificar se o valor dos débitos é zero
		String valorDebitos = httpServletRequest.getParameter("valorDebitos");

		if(valorDebitos != null && !valorDebitos.equals("")){
			BigDecimal valor = Util.formatarMoedaRealparaBigDecimal(valorDebitos);

			// Se o valor for maior do que zero
			if(valor.compareTo(BigDecimal.ZERO) == 1){
				throw new ActionServletException("atencao.valor_debitos_deve_ser_zero");
			}
		}

		String codigoImovel = "";
		String nomeCliente = "";
		String enderecoCliente = "";
		String periodoInicial = "";
		String periodoFinal = "";

		// Código do Imóvel
		if(sessao.getAttribute("codigoImovel") != null){
			codigoImovel = (String) sessao.getAttribute("codigoImovel");
		}else{
			codigoImovel = consultarDebitoImovelActionForm.getCodigoImovel();
		}

		Imovel imovelCertidao = fachada.pesquisarImovel(Integer.valueOf(codigoImovel));
		if(imovelCertidao == null){
			throw new ActionServletException("atencao.imovel_certidao_nao_encontrado");
		}

		// Endereço do Cliente
		enderecoCliente = httpServletRequest.getParameter("enderecoFormatado");
		periodoInicial = consultarDebitoImovelActionForm.getReferenciaInicial();
		periodoFinal = consultarDebitoImovelActionForm.getReferenciaFinal();

		Cliente cliente = fachada.pesquisarClienteUsuarioImovel(new Integer(codigoImovel));

		// pesquisar cliente usuário
		if(cliente != null){
			nomeCliente = cliente.getNome();
		}else{
			throw new ActionServletException("atencao.cliente_usuario_nao_encontrado");
		}

		Usuario usuario = null;

		if(sessao.getAttribute("usuarioLogado") != null){
			usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		}

		String parametroModeloRelatorioCertidao = "";
		try{

			parametroModeloRelatorioCertidao = ParametroCobranca.P_MODELO_RELATORIO_CERTIDAO_NEGATIVA_DEBITOS.executar();
		}catch(ControladorException e){

			e.printStackTrace();
		}

		if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.UM.getValor())){

			// Verifica se o período de ref. foi informado.
			if((periodoInicial == null || periodoInicial.equals("")) || (periodoFinal == null || periodoFinal.equals(""))){
				throw new ActionServletException("atencao.informar_periodo_referencia_debito");
			}

			// Verifica se o período final é maior do que a data atual
			int anoMesAtual = Util.getAnoMesComoInt(new Date());
			String anoMesAtualSemBarra = String.valueOf(anoMesAtual);
			String anoMesFinalSemBarra = Util.formatarMesAnoParaAnoMes(periodoFinal);

			if(Util.compararAnoMesReferencia(anoMesFinalSemBarra, anoMesAtualSemBarra, ">")){
				throw new ActionServletException("atencao.periodo_referencia_maior_hoje");
			}
		}

		// Verifica endereço
		if(enderecoCliente == null){
			throw new ActionServletException("atencao.endereco_cliente_nao_encontrado");
		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		TarefaRelatorio relatorio = null;

		SistemaParametro sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");

		if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.UM.getValor())){

			relatorio = new RelatorioCertidaoNegativaModelo1((Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			if(sistemaParametro != null){
				relatorio.addParametro("empresa", sistemaParametro.getNomeEmpresa());
			}

			// Dados do Imóvel (Inscrição, Matrícula, Dados Hidrômetro)
			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			// Hidrometro
			if(imovelCertidao.getLigacaoAgua() != null && imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico() != null
							&& imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico().getHidrometro() != null){
				relatorio.addParametro("numeroHidrometro", imovelCertidao.getLigacaoAgua().getHidrometroInstalacaoHistorico()
								.getHidrometro().getNumero());
			}else{
				relatorio.addParametro("numeroHidrometro", "NAO INSTALADO");
			}

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}

			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoCliente);
			relatorio.addParametro("periodoInicial", periodoInicial);
			relatorio.addParametro("periodoFinal", periodoFinal);
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

		}else if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.DOIS.getValor())){

			relatorio = new RelatorioCertidaoNegativaModelo2((Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			if(periodoInicial == null){
				periodoInicial = "";
			}

			if(periodoFinal == null){
				periodoFinal = "";
			}

			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoCliente);
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			relatorio.addParametro("periodoInicial", periodoInicial);
			relatorio.addParametro("periodoFinal", periodoFinal);

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}

		}else if(parametroModeloRelatorioCertidao.equals(ModeloRelatorioCertidaoNegativaDebitos.TRES.getValor())){

			relatorio = new RelatorioCertidaoNegativaModelo3((Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

			relatorio.addParametro("inscricaoImovel", imovelCertidao.getInscricaoFormatada());
			relatorio.addParametro("matriculaImovel", imovelCertidao.getId().toString());
			relatorio.addParametro("nomeCliente", nomeCliente);
			relatorio.addParametro("enderecoCliente", enderecoCliente);
			String cidade = "";
			if(imovelCertidao.getLogradouroCep() != null && imovelCertidao.getLogradouroCep().getCep() != null){
				cidade = imovelCertidao.getLogradouroCep().getCep().getMunicipio();
			
			}
			relatorio.addParametro("cidade", cidade);
			relatorio.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));

			if(usuario.getFuncionario() != null && usuario.getFuncionario().getId() > 0){
				relatorio.addParametro("nomeUsuario", usuario.getFuncionario().getNome());
				relatorio.addParametro("emissorUsuario", usuario.getFuncionario().getId());
			}else{
				relatorio.addParametro("nomeUsuario", usuario.getNomeUsuario());
				relatorio.addParametro("emissorUsuario", usuario.getId());
			}
		}

		if(tipoRelatorio == null){

			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		retorno = processarExibicaoRelatorio(relatorio, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		// devolve o mapeamento contido na variável retorno
		return retorno;
	}
}
