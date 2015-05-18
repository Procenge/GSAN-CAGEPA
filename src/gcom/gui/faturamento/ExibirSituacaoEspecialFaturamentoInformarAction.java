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

package gcom.gui.faturamento;

import gcom.cadastro.cliente.ClienteImovel;
import gcom.cadastro.cliente.FiltroClienteImovel;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoSituacaoHistorico;
import gcom.faturamento.bean.SituacaoEspecialFaturamentoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.FiltroParametro;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;
import gcom.util.filtro.ParametroSimplesDiferenteDe;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ExibirSituacaoEspecialFaturamentoInformarAction
				extends GcomAction {

	/**
	 * @author eduardo henrique
	 * @date 09/01/2009 Alteração para inclusão de mais um filtro , nome de Bairro.
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirSituacaoEspecialFaturamentoInformar");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		String botaoOrigem = (String) sessao.getAttribute("botaoOrigem");

		SituacaoEspecialFaturamentoInformarActionForm situacaoEspecialFaturamentoInformarActionForm = (SituacaoEspecialFaturamentoInformarActionForm) actionForm;
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		String inscricaoTipo = httpServletRequest.getParameter("inscricaoTipo");

		if(httpServletRequest.getParameter("download") == null){
			sessao.removeAttribute("SEMSituacaoEspecialFaturamento");
			sessao.removeAttribute("COMSituacaoEspecialFaturamento");
		}

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("") && inscricaoTipo != null
						&& !inscricaoTipo.trim().equalsIgnoreCase("")){

			switch(Integer.parseInt(objetoConsulta)){
				// Localidade
				case 1:
					pesquisarLocalidade(inscricaoTipo, situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest);
					break;
				// Setor Comercial
				case 2:
					pesquisarLocalidade(inscricaoTipo, situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest);
					pesquisarSetorComercial(inscricaoTipo, situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest);
					break;
				// Quadra
				case 3:
					pesquisarLocalidade(inscricaoTipo, situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest);
					pesquisarSetorComercial(inscricaoTipo, situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest);
					pesquisarQuadra(inscricaoTipo, situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest);
					break;
				//Bairro
				case 4:
				
					pesquisarBairro(inscricaoTipo, situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest);
				
				
				default:
					break;
			}
		}else{
			if(httpServletRequest.getParameter("abrirPopup") != null){
				situacaoEspecialFaturamentoInformarActionForm.reset(actionMapping, httpServletRequest);
			}
			sessao.removeAttribute("situacaoEspecialFaturamentoInformarActionForm");
		}

		boolean liberarSelecao = false;

		if(situacaoEspecialFaturamentoInformarActionForm != null){

			String idImovel = situacaoEspecialFaturamentoInformarActionForm.getIdImovel();

			if(idImovel != null && !idImovel.equals("")){
				// Pesquisa o imovel para mandar para a pagina informacoes sobre o endereco
				FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

				// Objetos que serão retornados pelo Hibernate

				// filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra.bairro.municipio.unidadeFederacao");

				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.quadra");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.localidade");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.setorComercial");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroBairro.bairro.municipio.unidadeFederacao");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.cep");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.areaConstruidaFaixa");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTipo");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.logradouroCep.logradouro.logradouroTitulo");
				filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade("imovel.enderecoReferencia");
				filtroClienteImovel.adicionarParametro(new ParametroSimplesDiferenteDe(FiltroClienteImovel.INDICADOR_IMOVEL_EXCLUIDO,
								Imovel.IMOVEL_EXCLUIDO, FiltroParametro.CONECTOR_OR, 2));
				filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.INDICADOR_IMOVEL_EXCLUIDO));
				filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID, idImovel));
				Collection clientesImoveis = Fachada.getInstancia().pesquisar(filtroClienteImovel, ClienteImovel.class.getName());

				if(!clientesImoveis.isEmpty()){
					ClienteImovel clienteImovel = (ClienteImovel) ((List) clientesImoveis).get(0);

					// Obter a quantidade de economias do imóvel escolhido
					int quantEconomias = Fachada.getInstancia().obterQuantidadeEconomias(clienteImovel.getImovel());

					// Seta no request
					sessao.setAttribute("quantEconomias", String.valueOf(quantEconomias));
					situacaoEspecialFaturamentoInformarActionForm.setEndereco(clienteImovel.getImovel().getEnderecoFormatado());
					situacaoEspecialFaturamentoInformarActionForm.setInscricaoImovel(clienteImovel.getImovel().getInscricaoFormatada());
					httpServletRequest.setAttribute("nomeCampo", "selecionar");
					httpServletRequest.setAttribute("enderecoFormatado", "true");

				}else{
					situacaoEspecialFaturamentoInformarActionForm.setInscricaoImovel("MATRÍCULA INEXISTENTE");
					situacaoEspecialFaturamentoInformarActionForm.setEndereco("");
					httpServletRequest.setAttribute("corImovel", "exception");
					httpServletRequest.setAttribute("nomeCampo", "idImovel");
				}
			}
			if(httpServletRequest.getParameter("consultaQuantidadeImoveis") == null
							&& situacaoEspecialFaturamentoInformarActionForm.getArquivoMatricula() != null
							&& !situacaoEspecialFaturamentoInformarActionForm.getArquivoMatricula().equals("")){
				sessao.setAttribute("arquivo", situacaoEspecialFaturamentoInformarActionForm.getArquivoMatricula());
			}
			// Verifica se o tipoConsulta é diferente de nulo ou vazio.
			// É feita essa verificação pois pode ser que ainda não tenha feito a pesquisa de ....
			if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

				if(httpServletRequest.getParameter("tipoConsulta").equals("imovel")){
					situacaoEspecialFaturamentoInformarActionForm.setIdImovel(httpServletRequest.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setInscricaoImovel(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("nomeCampo", "idImovel");
				}

				if(httpServletRequest.getParameter("tipoConsulta").equals("bairro")){
					situacaoEspecialFaturamentoInformarActionForm.setIdBairro(httpServletRequest.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setNomeBairro(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("nomeCampo", "idBairro");
				}

				if(httpServletRequest.getParameter("tipoConsulta").equals("localidadeOrigem")){
					situacaoEspecialFaturamentoInformarActionForm.setLocalidadeOrigemID(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeOrigem(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemID");

					situacaoEspecialFaturamentoInformarActionForm.setLocalidadeDestinoID(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeDestino(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));

					liberarSelecao = true;
				}

				if(httpServletRequest.getParameter("tipoConsulta").equals("setorComercialOrigem")){
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialOrigemID(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialOrigemCD(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialOrigem(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
				}

				if(httpServletRequest.getParameter("tipoConsulta").equals("quadraOrigem")){
					situacaoEspecialFaturamentoInformarActionForm.setQuadraOrigemID(httpServletRequest.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setQuadraOrigemNM(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
				}

				if(httpServletRequest.getParameter("tipoConsulta").equals("localidadeDestino")){
					situacaoEspecialFaturamentoInformarActionForm.setLocalidadeDestinoID(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeDestino(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoID");

					liberarSelecao = true;
				}

				if(httpServletRequest.getParameter("tipoConsulta").equals("setorComercialDestino")){
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoID(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoCD(httpServletRequest
									.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialDestino(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
					httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoID");
				}



				if(httpServletRequest.getParameter("tipoConsulta").equals("quadraDestino")){
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoID(httpServletRequest.getParameter("idCampoEnviarDados"));
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoNM(httpServletRequest
									.getParameter("descricaoCampoEnviarDados"));
				}
			}
		}

		validacaoFinal(situacaoEspecialFaturamentoInformarActionForm, fachada, httpServletRequest.getRequestURI());

		// Exibir quantidade de Imoveis com situacao especial de faturamento
		if(httpServletRequest.getParameter("consultaQuantidadeImoveis") != null
						|| (situacaoEspecialFaturamentoInformarActionForm != null
										&& situacaoEspecialFaturamentoInformarActionForm.getIdImovel() != null && !situacaoEspecialFaturamentoInformarActionForm
										.getIdImovel().equals(""))){

			FormFile formFile = (FormFile) sessao.getAttribute("arquivo");
			if(formFile != null && !formFile.getFileName().equals("")){
				try{
					situacaoEspecialFaturamentoInformarActionForm.setArquivoDownload("");
					String fileType = formFile.getFileName()
									.substring(formFile.getFileName().length() - 4, formFile.getFileName().length());
					if(!fileType.equals(".txt") && !fileType.equals(".csv")){
						throw new ActionServletException("atencao.tipo_importacao.nao_txt", httpServletRequest.getRequestURI(), "");
					}
					Scanner scanner = new Scanner(formFile.getInputStream());
					Collection colecaoImoveis = new ArrayList();
					Collection<String> devolucao = new ArrayList<String>();
					String matricula;
					Imovel imovel;
					while(scanner.hasNext()){
						matricula = scanner.next();
						try{
							imovel = fachada.pesquisarImovel(Integer.valueOf(matricula));
							if(imovel == null){
								devolucao.add(matricula + " MATRÍCULA INEXISTENTE");
							}else if(colecaoImoveis.contains(imovel)){
								devolucao.add(matricula + " MATRICULA REPETIDA NO ARQUIVO");
							}else{
								colecaoImoveis.add(imovel);
							}
						}catch(NumberFormatException e){
							devolucao.add(matricula + " NÃO É UMA MATRÍCULA VALIDA");
						}
					}

					// Seta os imoveis para a pesquisa
					String idsImoveis = "";
					for(Imovel item : (Collection<Imovel>) colecaoImoveis){
						idsImoveis += item.getId() + ",";
					}
					if(idsImoveis.length() > 0){
						idsImoveis = idsImoveis.substring(0, idsImoveis.length() - 1);
						situacaoEspecialFaturamentoInformarActionForm.setIdsImoveis(idsImoveis);
					}else{
						throw new ActionServletException("atencao.nenhum_imovel_encontrado_arquivo", httpServletRequest.getRequestURI(), "");
					}

					// Prepara arquivo com erros
					File arquivoErro = new File("ArquivoErro.txt");
					BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoErro
									.getAbsolutePath())));
					for(String linha : devolucao){
						bufferedWriter.write(linha);
						bufferedWriter.newLine();
					}
					bufferedWriter.flush();
					bufferedWriter.close();
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					FileInputStream in = new FileInputStream(arquivoErro);
					int b;
					while((b = in.read()) > -1){
						byteArrayOutputStream.write(b);
					}
					situacaoEspecialFaturamentoInformarActionForm.setArquivoDownload("Clique para baixar o arquivo de críticas ");
					situacaoEspecialFaturamentoInformarActionForm.setEnderecoArquivoDownload(arquivoErro.getPath());
					if(httpServletRequest.getParameter("download") != null && httpServletRequest.getParameter("download").equals("true")){
						httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + arquivoErro.getName());
						String mimeType = "application/txt";
						httpServletResponse.setContentType(mimeType);
						OutputStream out = httpServletResponse.getOutputStream();
						out.write(byteArrayOutputStream.toByteArray());
						out.flush();
						out.close();
						retorno = null;
						sessao.removeAttribute("arquivo");
						situacaoEspecialFaturamentoInformarActionForm.setArquivoDownload(null);
						situacaoEspecialFaturamentoInformarActionForm.setEnderecoArquivoDownload(null);
					}
				}catch(FileNotFoundException e){
					throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
				}catch(IOException e){
					throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
				}
			}

			SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper = transferirActionFormParaHelper(
							situacaoEspecialFaturamentoInformarActionForm, usuarioLogado);

			if(botaoOrigem.equals("Inserir")){

				Collection COMSituacaoEspecialFaturamento = fachada.pesquisarImovelSituacaoEspecialFaturamento("COM",
								situacaoEspecialFaturamentoHelper);
				Collection SEMSituacaoEspecialFaturamento = fachada.pesquisarImovelSituacaoEspecialFaturamento("SEM",
								situacaoEspecialFaturamentoHelper);

				if((COMSituacaoEspecialFaturamento == null || COMSituacaoEspecialFaturamento.isEmpty())
								&& (SEMSituacaoEspecialFaturamento == null || SEMSituacaoEspecialFaturamento.isEmpty())){
					throw new ActionServletException("atencao.nao.parametro.informado", httpServletRequest.getRequestURI(), "");
				}

				if(SEMSituacaoEspecialFaturamento != null && !SEMSituacaoEspecialFaturamento.isEmpty()){
					httpServletRequest.setAttribute("liberarBotaoInserir", "true");
					sessao.setAttribute("SEMSituacaoEspecialFaturamento", SEMSituacaoEspecialFaturamento);
				}

				if(situacaoEspecialFaturamentoInformarActionForm != null && COMSituacaoEspecialFaturamento != null){
					situacaoEspecialFaturamentoInformarActionForm.setQuantidadeImoveisCOMSituacaoEspecialFaturamento(String
									.valueOf(COMSituacaoEspecialFaturamento.size()));
				}
				if(situacaoEspecialFaturamentoInformarActionForm != null && SEMSituacaoEspecialFaturamento != null){
					situacaoEspecialFaturamentoInformarActionForm.setQuantidadeImoveisSEMSituacaoEspecialFaturamento(String
									.valueOf(SEMSituacaoEspecialFaturamento.size()));
				}

			}else if(botaoOrigem.equals("Manter")){

				SituacaoEspecialFaturamentoActionForm situacaoEspecialFaturamentoActionForm = (SituacaoEspecialFaturamentoActionForm) sessao
								.getAttribute("situacaoEspecialFaturamentoActionForm");

				situacaoEspecialFaturamentoHelper = transferirActionFormParaHelper(situacaoEspecialFaturamentoActionForm,
								situacaoEspecialFaturamentoHelper);

				// Integer mesAnoReferenciaFaturamentoInicial =
				// Util.formatarMesAnoComBarraParaAnoMes(situacaoEspecialFaturamentoActionForm
				// .getMesAnoReferenciaFaturamentoInicial());
				//
				// Integer mesAnoReferenciaFaturamentoFinal =
				// Util.formatarMesAnoComBarraParaAnoMes(situacaoEspecialFaturamentoActionForm
				// .getMesAnoReferenciaFaturamentoFinal());

				Collection<FaturamentoSituacaoHistorico> colecaoFaturamentoSituacaoHistorico = fachada
								.pesquisarFaturamentosSituacaoHistoricoAtivos(situacaoEspecialFaturamentoHelper);

				if(colecaoFaturamentoSituacaoHistorico != null){
					if(!colecaoFaturamentoSituacaoHistorico.isEmpty()){
						httpServletRequest.setAttribute("liberarBotaoInserir", "true");
					}

					sessao.setAttribute("colecaoFaturamentoSituacaoHistorico", colecaoFaturamentoSituacaoHistorico);
					situacaoEspecialFaturamentoInformarActionForm.setQuantidadeImoveisCOMSituacaoEspecialFaturamento(String
									.valueOf(colecaoFaturamentoSituacaoHistorico.size()));
				}
			}

			httpServletRequest.setAttribute("nomeCampo", "inserir");

		}else{
			if(situacaoEspecialFaturamentoInformarActionForm != null){
				situacaoEspecialFaturamentoInformarActionForm.setQuantidadeImoveisCOMSituacaoEspecialFaturamento("");
				situacaoEspecialFaturamentoInformarActionForm.setQuantidadeImoveisSEMSituacaoEspecialFaturamento("");
			}
		}

		// Exibir quantidade de Imoveis com situacao especial de faturamento
		if(httpServletRequest.getParameter("bloquear") != null){
			if(httpServletRequest.getParameter("bloquear").equals("matricula")){
				httpServletRequest.setAttribute("bloquear", "matricula");
			}else{
				httpServletRequest.setAttribute("bloquear", "todos");
			}
		}else{
			if(liberarSelecao){
				httpServletRequest.setAttribute("bloquear", "matricula");
			}else{
				httpServletRequest.setAttribute("bloquear", "");
			}
		}

		// Manda o parametro que veio do validar enter para, se preciso, desabilitar os campos
		// posterior ao intervalo, que não são iguais.
		if(httpServletRequest.getParameter("campoDesabilita") != null && !httpServletRequest.getParameter("campoDesabilita").equals("")){
			httpServletRequest.setAttribute("campoDesabilita", httpServletRequest.getParameter("campoDesabilita"));
		}

		return retorno;
	}


	private void validacaoFinal(SituacaoEspecialFaturamentoInformarActionForm form, Fachada fachada, String url){

		// validar localidade inicial sendo maior que localidade final
		if(form.getLocalidadeOrigemID() != null && form.getLocalidadeDestinoID() != null){
			if(!form.getLocalidadeOrigemID().equals("") && !form.getLocalidadeDestinoID().equals("")){
				int origem = Integer.parseInt(form.getLocalidadeOrigemID());
				int destino = Integer.parseInt(form.getLocalidadeDestinoID());
				if(origem > destino){
					throw new ActionServletException("atencao.localidade.final.maior.localidade.inicial", url, "");
				}
				String localidadeID = form.getLocalidadeOrigemID();

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna localidade
				Collection colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Localidade nao encontrada
					throw new ActionServletException("atencao.pesquisa.localidade_inexistente", url, "");
				}
				Localidade objetoLocalidadeOrigem = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

				form.setLocalidadeOrigemID(String.valueOf(objetoLocalidadeOrigem.getId()));

				if(origem < destino){
					// se localidade inicial < localidade final pesquisa p descobrir localidade
					// final existe
					// se existir seta o id dela no actionForm

					filtroLocalidade.limparListaParametros();
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, destino));

					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna localidade
					Collection colecaoPesquisaDestino = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

					if(colecaoPesquisaDestino == null || colecaoPesquisaDestino.isEmpty()){
						// Localidade nao encontrada
						throw new ActionServletException("atencao.pesquisa.localidade_inexistente", url, "");
					}
					Localidade objetoLocalidadeDestino = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisaDestino);

					form.setLocalidadeDestinoID(String.valueOf(objetoLocalidadeDestino.getId()));

				}else{
					form.setLocalidadeDestinoID(String.valueOf(objetoLocalidadeOrigem.getId()));
				}
			}
		}

		// validar setor comercial sendo maior que setor comercial final
		if(form.getSetorComercialOrigemCD() != null && form.getSetorComercialDestinoCD() != null){
			if(!form.getSetorComercialOrigemCD().equals("") && !form.getSetorComercialDestinoCD().equals("")){
				int origem = Integer.parseInt(form.getSetorComercialOrigemCD());
				int destino = Integer.parseInt(form.getSetorComercialDestinoCD());
				if(origem > destino){
					form.setSetorComercialDestinoCD("");
					form.setSetorComercialOrigemCD("");
					throw new ActionServletException("atencao.setor.comercial.final.maior.setor.comercial.inicial", url, "");
				}
				String setorComercialCD = form.getSetorComercialOrigemCD();

				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				// Adiciona o id da localidade que está no formulário para
				// compor a pesquisa.
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, form
								.getLocalidadeOrigemID()));

				// Adiciona o código do setor comercial que esta no formulário
				// para compor a pesquisa.
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna setorComercial
				Collection colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor Comercial inexistente.
					throw new ActionServletException("atencao.setor_comercial.inexistente", url, "");
				}

				SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setSetorComercialOrigemID(String.valueOf(objetoSetorComercial.getId()));

				if(origem < destino){
					// se setor comercial inicial < setor comercial final pesquisa p descobrir setor
					// comercial final existe
					// se existir seta o id dele no actionForm
					filtroSetorComercial.limparListaParametros();
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, form
									.getLocalidadeOrigemID()));

					// Adiciona o código do setor comercial que esta no formulário para compor a
					// pesquisa.
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, destino));
					filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna setorComercial
					Collection colecaoPesquisaDestino = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

					if(colecaoPesquisaDestino == null || colecaoPesquisaDestino.isEmpty()){
						// Setor Comercial inexistente.
						throw new ActionServletException("atencao.setor_comercial.inexistente", url, "");
					}
					SetorComercial objetoSetorComercialDestino = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisaDestino);

					form.setLocalidadeDestinoID(String.valueOf(objetoSetorComercialDestino.getId()));

				}else{
					form.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
				}
			}
		}

		// validar quadra sendo maior que localidade final

		if(form.getQuadraOrigemNM() != null && form.getQuadraDestinoNM() != null){
			if(!form.getQuadraOrigemNM().equals("") && !form.getQuadraDestinoNM().equals("")){
				int origem = Integer.parseInt(form.getQuadraOrigemNM());
				int destino = Integer.parseInt(form.getQuadraDestinoNM());
				if(origem > destino){
					throw new ActionServletException("atencao.quadra.final.maior.quadra.inical", url, "");
				}
				String quadraNM = form.getQuadraOrigemNM();

				// Adiciona o id do setor comercial que está no formulário para
				// compor a pesquisa.
				FiltroQuadra filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, form.getSetorComercialOrigemID()));

				// Adiciona o número da quadra que esta no formulário para
				// compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNM));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna quadra
				Collection colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					throw new ActionServletException("atencao.quadra.inexistente", url, "");
				}
				Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
				form.setQuadraOrigemID(String.valueOf(objetoQuadra.getId()));

				if(origem < destino){
					// se setor comercial inicial < setor comercial final
					// pesquisa p descobrir setor comercial final existe
					// se existir seta o id dele no actionForm
					filtroQuadra.limparListaParametros();
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, form.getSetorComercialOrigemID()));

					// Adiciona o número da quadra que esta no formulário para
					// compor a pesquisa.
					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNM));

					filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

					// Retorna quadra
					Collection colecaoPesquisaDestino = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

					if(colecaoPesquisaDestino == null || colecaoPesquisaDestino.isEmpty()){
						throw new ActionServletException("atencao.quadra.inexistente", url, "");
					}
					Quadra objetoQuadraDestino = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisaDestino);
					form.setQuadraDestinoID(String.valueOf(objetoQuadraDestino.getId()));
				}else{
					form.setQuadraDestinoID(String.valueOf(objetoQuadra.getId()));
				}
			}
		}

		// validar lote sendo maior que localidade final
		if(form.getLoteOrigem() != null && form.getLoteDestino() != null){
			if(!form.getLoteOrigem().equals("") && !form.getLoteDestino().equals("")){
				try{
					int origem = Integer.parseInt(form.getLoteOrigem());
					int destino = Integer.parseInt(form.getLoteDestino());
					if(origem > destino){
						throw new ActionServletException("atencao.lote.final.maior.lote.inical", url, "");
					}
				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.nao.numerico", url, "Lote(s)");
				}
			}
		}

		// validar sublote sendo maior que localidade final
		if(form.getSubloteOrigem() != null && form.getSubloteDestino() != null){
			if(!form.getSubloteOrigem().equals("") && !form.getSubloteDestino().equals("")){
				try{
					int origem = Integer.parseInt(form.getSubloteOrigem());
					int destino = Integer.parseInt(form.getSubloteDestino());
					if(origem > destino){
						throw new ActionServletException("atencao.sublote.final.maior.sublote.inicial", url, "");
					}
				}catch(NumberFormatException e){
					throw new ActionServletException("atencao.nao.numerico", url, "SubLote(s)");
				}
			}
		}
		// validar Sublote sendo maior que localidade final

	}

	/**
	 * Pesquisa Bairro
	 * 
	 * @author Adriano Sousa
	 * @date 21/01/2015
	 */
	private void pesquisarBairro(String inscricaoTipo,
					SituacaoEspecialFaturamentoInformarActionForm situacaoEspecialFaturamentoInformarActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		Collection colecaoPesquisa = null;
		String idBairro = null;

		FiltroBairro filtroBairro = new FiltroBairro();

		if(inscricaoTipo.equalsIgnoreCase("bairro")){
			situacaoEspecialFaturamentoInformarActionForm.setInscricaoTipo("bairro");
			idBairro = situacaoEspecialFaturamentoInformarActionForm.getIdBairro();

			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, idBairro));
			filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			colecaoPesquisa = fachada.pesquisar(filtroBairro, Bairro.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				situacaoEspecialFaturamentoInformarActionForm.setIdBairro("");
				situacaoEspecialFaturamentoInformarActionForm.setNomeBairro("BAIRRO INEXISTENTE");
				httpServletRequest.setAttribute("corBairro", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idBairro");
			}else{
				Bairro objetoBairro = (Bairro) Util.retonarObjetoDeColecao(colecaoPesquisa);
				situacaoEspecialFaturamentoInformarActionForm.setIdBairro(String.valueOf(objetoBairro.getId()));
				situacaoEspecialFaturamentoInformarActionForm.setNomeBairro(objetoBairro.getNome());
				httpServletRequest.setAttribute("corBairro", "valor");
				httpServletRequest.setAttribute("nomeCampo", "idBairro");
			}
		}
	}

	private void pesquisarLocalidade(String inscricaoTipo,
					SituacaoEspecialFaturamentoInformarActionForm situacaoEspecialFaturamentoInformarActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		Collection colecaoPesquisa = null;
		String localidadeID = null;

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			situacaoEspecialFaturamentoInformarActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formulário.
			localidadeID = situacaoEspecialFaturamentoInformarActionForm.getLocalidadeOrigemID();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade nao encontrada
				// Limpa os campos localidadeOrigemID e nomeLocalidadeOrigem do formulário
				situacaoEspecialFaturamentoInformarActionForm.setLocalidadeOrigemID("");
				situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeOrigem("LOCALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("corLocalidadeOrigem", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeOrigemID");
			}else{
				Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				situacaoEspecialFaturamentoInformarActionForm.setLocalidadeOrigemID(String.valueOf(objetoLocalidade.getId()));
				situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeOrigem(objetoLocalidade.getDescricao());
				situacaoEspecialFaturamentoInformarActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
				situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
				httpServletRequest.setAttribute("corLocalidadeOrigem", "valor");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
			}
		}else{
			// Recebe o valor do campo localidadeDestinoID do formulário.
			localidadeID = situacaoEspecialFaturamentoInformarActionForm.getLocalidadeDestinoID();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, localidadeID));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			situacaoEspecialFaturamentoInformarActionForm.setInscricaoTipo("destino");

			if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
				// Localidade nao encontrada
				// Limpa os campos localidadeDestinoID e nomeLocalidadeDestino do formulário
				situacaoEspecialFaturamentoInformarActionForm.setLocalidadeDestinoID("");
				situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeDestino("Localidade inexistente.");
				httpServletRequest.setAttribute("corLocalidadeDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");
			}else{
				Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);
				situacaoEspecialFaturamentoInformarActionForm.setLocalidadeDestinoID(String.valueOf(objetoLocalidade.getId()));
				situacaoEspecialFaturamentoInformarActionForm.setNomeLocalidadeDestino(objetoLocalidade.getDescricao());
				httpServletRequest.setAttribute("corLocalidadeDestino", "valor");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
			}
		}
	}

	private void pesquisarSetorComercial(String inscricaoTipo,
					SituacaoEspecialFaturamentoInformarActionForm situacaoEspecialFaturamentoInformarActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		Collection colecaoPesquisa = null;
		String localidadeID = null;
		String setorComercialCD = null;

		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			situacaoEspecialFaturamentoInformarActionForm.setInscricaoTipo("origem");
			// Recebe o valor do campo localidadeOrigemID do formulário.
			localidadeID = situacaoEspecialFaturamentoInformarActionForm.getLocalidadeOrigemID();

			// O campo localidadeOrigemID será obrigatório
			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){

				setorComercialCD = situacaoEspecialFaturamentoInformarActionForm.getSetorComercialOrigemCD();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialOrigemCD, nomeSetorComercialOrigem e
					// setorComercialOrigemID do formulário
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialOrigemCD("");
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialOrigemID("");
					situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialOrigem("Setor comercial inexistente.");
					httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
					httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");

				}else{
					SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
					// setorComercialOrigem
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialOrigemCD(String
									.valueOf(objetoSetorComercial.getCodigo()));
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialOrigemID(String.valueOf(objetoSetorComercial.getId()));
					situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialOrigem(objetoSetorComercial.getDescricao());
					// setorComercialOrigem

					// setorComercialDestino
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial
									.getCodigo()));
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
					situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
					// setorComercialDestino
					httpServletRequest.setAttribute("corSetorComercialOrigem", "valor");
					httpServletRequest.setAttribute("nomeCampo", "quadraOrigemNM");
				}
			}else{
				// Limpa o campo setorComercialOrigemCD do formulário
				situacaoEspecialFaturamentoInformarActionForm.setSetorComercialOrigemCD("");
				situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialOrigem("Informe Localidade Inicial.");
				httpServletRequest.setAttribute("corSetorComercialOrigem", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeOrigemID");
			}
		}else{

			situacaoEspecialFaturamentoInformarActionForm.setInscricaoTipo("destino");

			// Recebe o valor do campo localidadeDestinoID do formulário.
			localidadeID = situacaoEspecialFaturamentoInformarActionForm.getLocalidadeDestinoID();

			// O campo localidadeOrigem será obrigatório
			if(localidadeID != null && !localidadeID.trim().equalsIgnoreCase("")){

				setorComercialCD = situacaoEspecialFaturamentoInformarActionForm.getSetorComercialDestinoCD();

				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, localidadeID));
				filtroSetorComercial
								.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setorComercialCD));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				colecaoPesquisa = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Setor Comercial nao encontrado
					// Limpa os campos setorComercialDestinoCD,
					// nomeSetorComercialDestino e setorComercialDestinoID do
					// formulário
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoCD("");
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoID("");
					situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialDestino("Setor Comercial inexistente.");
					httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
					httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
				}else{
					SetorComercial objetoSetorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoPesquisa);
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoCD(String.valueOf(objetoSetorComercial
									.getCodigo()));
					situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoID(String.valueOf(objetoSetorComercial.getId()));
					situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialDestino(objetoSetorComercial.getDescricao());
					httpServletRequest.setAttribute("corSetorComercialDestino", "valor");
					httpServletRequest.setAttribute("nomeCampo", "quadraDestinoNM");
				}
			}else{
				// Limpa o campo setorComercialDestinoCD do formulário
				situacaoEspecialFaturamentoInformarActionForm.setSetorComercialDestinoCD("");
				situacaoEspecialFaturamentoInformarActionForm.setNomeSetorComercialDestino("Informe Localidade Final.");
				httpServletRequest.setAttribute("corSetorComercialDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "localidadeDestinoID");
			}
		}

	}

	private SituacaoEspecialFaturamentoHelper transferirActionFormParaHelper(
					SituacaoEspecialFaturamentoInformarActionForm situacaoEspecialFaturamentoInformarActionForm, Usuario usuarioLogado){

		SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper = new SituacaoEspecialFaturamentoHelper();

		situacaoEspecialFaturamentoHelper.setIdImovel(situacaoEspecialFaturamentoInformarActionForm.getIdImovel() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getIdImovel());

		situacaoEspecialFaturamentoHelper.setInscricaoTipo(situacaoEspecialFaturamentoInformarActionForm.getInscricaoTipo() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getInscricaoTipo());

		situacaoEspecialFaturamentoHelper.setLoteDestino(situacaoEspecialFaturamentoInformarActionForm.getLoteDestino() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getLoteDestino());

		situacaoEspecialFaturamentoHelper
						.setQuadraDestinoNM(situacaoEspecialFaturamentoInformarActionForm.getQuadraDestinoNM() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getQuadraDestinoNM());

		situacaoEspecialFaturamentoHelper.setLoteOrigem(situacaoEspecialFaturamentoInformarActionForm.getLoteOrigem() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getLoteOrigem());

		situacaoEspecialFaturamentoHelper
						.setNomeLocalidadeOrigem(situacaoEspecialFaturamentoInformarActionForm.getNomeLocalidadeOrigem() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getNomeLocalidadeOrigem());

		situacaoEspecialFaturamentoHelper.setNomeSetorComercialOrigem(situacaoEspecialFaturamentoInformarActionForm
						.getNomeSetorComercialOrigem() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getNomeSetorComercialOrigem());

		situacaoEspecialFaturamentoHelper.setQuadraOrigemNM(situacaoEspecialFaturamentoInformarActionForm.getQuadraOrigemNM() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getQuadraOrigemNM());

		situacaoEspecialFaturamentoHelper
						.setQuadraMensagemOrigem(situacaoEspecialFaturamentoInformarActionForm.getQuadraMensagemOrigem() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getQuadraMensagemOrigem());

		situacaoEspecialFaturamentoHelper
						.setNomeLocalidadeDestino(situacaoEspecialFaturamentoInformarActionForm.getNomeLocalidadeDestino() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getNomeLocalidadeDestino());

		situacaoEspecialFaturamentoHelper.setSetorComercialDestinoCD(situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialDestinoCD() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialDestinoCD());

		situacaoEspecialFaturamentoHelper.setSetorComercialOrigemCD(situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialOrigemCD() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialOrigemCD());

		situacaoEspecialFaturamentoHelper.setSetorComercialOrigemID(situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialOrigemID() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialOrigemID());

		situacaoEspecialFaturamentoHelper.setQuadraOrigemID(situacaoEspecialFaturamentoInformarActionForm.getQuadraOrigemID() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getQuadraOrigemID());

		situacaoEspecialFaturamentoHelper
						.setLocalidadeDestinoID(situacaoEspecialFaturamentoInformarActionForm.getLocalidadeDestinoID() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getLocalidadeDestinoID());

		situacaoEspecialFaturamentoHelper
						.setLocalidadeOrigemID(situacaoEspecialFaturamentoInformarActionForm.getLocalidadeOrigemID() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getLocalidadeOrigemID());

		situacaoEspecialFaturamentoHelper.setNomeSetorComercialDestino(situacaoEspecialFaturamentoInformarActionForm
						.getNomeSetorComercialDestino() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getNomeSetorComercialDestino());

		situacaoEspecialFaturamentoHelper.setSetorComercialDestinoID(situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialDestinoID() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getSetorComercialDestinoID());

		situacaoEspecialFaturamentoHelper
						.setQuadraMensagemDestino(situacaoEspecialFaturamentoInformarActionForm.getQuadraMensagemDestino() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getQuadraMensagemDestino());

		situacaoEspecialFaturamentoHelper
						.setQuadraDestinoID(situacaoEspecialFaturamentoInformarActionForm.getQuadraDestinoID() == null ? ""
										: situacaoEspecialFaturamentoInformarActionForm.getQuadraDestinoID());

		situacaoEspecialFaturamentoHelper.setIdsImoveis(situacaoEspecialFaturamentoInformarActionForm.getIdsImoveis() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getIdsImoveis());

		// situacaoEspecialFaturamentoHelper
		// .setTipoSituacaoEspecialFaturamento(situacaoEspecialFaturamentoInformarActionForm
		// .getTipoSituacaoEspecialFaturamento() == null ? ""
		// : situacaoEspecialFaturamentoInformarActionForm
		// .getTipoSituacaoEspecialFaturamento());

		situacaoEspecialFaturamentoHelper.setLoteOrigem(situacaoEspecialFaturamentoInformarActionForm.getLoteOrigem() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getLoteOrigem());

		situacaoEspecialFaturamentoHelper.setLoteDestino(situacaoEspecialFaturamentoInformarActionForm.getLoteDestino() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getLoteDestino());

		situacaoEspecialFaturamentoHelper.setSubloteOrigem(situacaoEspecialFaturamentoInformarActionForm.getSubloteOrigem() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getSubloteOrigem());

		situacaoEspecialFaturamentoHelper.setSubloteDestino(situacaoEspecialFaturamentoInformarActionForm.getSubloteDestino() == null ? ""
						: situacaoEspecialFaturamentoInformarActionForm.getSubloteDestino());

		situacaoEspecialFaturamentoHelper.setQuantidadeImoveisCOMSituacaoEspecialFaturamento(situacaoEspecialFaturamentoInformarActionForm
						.getQuantidadeImoveisCOMSituacaoEspecialFaturamento() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getQuantidadeImoveisCOMSituacaoEspecialFaturamento());

		situacaoEspecialFaturamentoHelper.setQuantidadeImoveisSEMSituacaoEspecialFaturamento(situacaoEspecialFaturamentoInformarActionForm
						.getQuantidadeImoveisSEMSituacaoEspecialFaturamento() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getQuantidadeImoveisSEMSituacaoEspecialFaturamento());

		situacaoEspecialFaturamentoHelper.setQuantidadeImoveisAtualizados(situacaoEspecialFaturamentoInformarActionForm
						.getQuantidadeImoveisAtualizados() == null ? "" : situacaoEspecialFaturamentoInformarActionForm
						.getQuantidadeImoveisAtualizados());

		if(situacaoEspecialFaturamentoInformarActionForm.getCdRotaInicial() == null
						|| situacaoEspecialFaturamentoInformarActionForm.getCdRotaInicial().trim().equals("")){
			situacaoEspecialFaturamentoHelper.setCodigoRotaInicial("");
		}else{
			situacaoEspecialFaturamentoHelper.setCodigoRotaInicial(Integer.valueOf(
							situacaoEspecialFaturamentoInformarActionForm.getCdRotaInicial()).toString());
		}

		if(situacaoEspecialFaturamentoInformarActionForm.getCdRotaFinal() == null
						|| situacaoEspecialFaturamentoInformarActionForm.getCdRotaFinal().trim().equals("")){
			situacaoEspecialFaturamentoHelper.setCodigoRotaFinal("");
		}else{
			situacaoEspecialFaturamentoHelper.setCodigoRotaFinal(Integer.valueOf(
							situacaoEspecialFaturamentoInformarActionForm.getCdRotaFinal()).toString());
		}

		if(situacaoEspecialFaturamentoInformarActionForm.getSequencialRotaInicial() == null
						|| situacaoEspecialFaturamentoInformarActionForm.getSequencialRotaInicial().trim().equals("")){
			situacaoEspecialFaturamentoHelper.setSequencialRotaInicial("");
		}else{
			situacaoEspecialFaturamentoHelper.setSequencialRotaInicial(Integer.valueOf(
							situacaoEspecialFaturamentoInformarActionForm.getSequencialRotaInicial()).toString());
		}

		if(situacaoEspecialFaturamentoInformarActionForm.getSequencialRotaFinal() == null
						|| situacaoEspecialFaturamentoInformarActionForm.getSequencialRotaFinal().trim().equals("")){
			situacaoEspecialFaturamentoHelper.setSequencialRotaFinal("");
		}else{
			situacaoEspecialFaturamentoHelper.setSequencialRotaFinal(Integer.valueOf(
							situacaoEspecialFaturamentoInformarActionForm.getSequencialRotaFinal()).toString());
		}

		if(situacaoEspecialFaturamentoInformarActionForm.getNomeBairro() == null
						|| situacaoEspecialFaturamentoInformarActionForm.getNomeBairro().trim().equalsIgnoreCase("")){
			situacaoEspecialFaturamentoHelper.setNomeBairro("");
		}else{
			situacaoEspecialFaturamentoHelper.setNomeBairro(situacaoEspecialFaturamentoInformarActionForm.getNomeBairro());
		}

		situacaoEspecialFaturamentoHelper.setIdUsuario(usuarioLogado.getId().toString());

		return situacaoEspecialFaturamentoHelper;
	}

	private SituacaoEspecialFaturamentoHelper transferirActionFormParaHelper(
					SituacaoEspecialFaturamentoActionForm situacaoEspecialFaturamentoActionForm,
					SituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper){

		situacaoEspecialFaturamentoHelper
						.setIdFaturamentoSituacaoTipo(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo() == null ? ""
										: situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoTipo());

		situacaoEspecialFaturamentoHelper.setIdFaturamentoSituacaoMotivo(situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoMotivo() == null ? ""
						: situacaoEspecialFaturamentoActionForm.getIdFaturamentoSituacaoMotivo());
		
		situacaoEspecialFaturamentoHelper.setMesAnoReferenciaFaturamentoInicial(situacaoEspecialFaturamentoActionForm.getMesAnoReferenciaFaturamentoInicial() == null ? ""
						: situacaoEspecialFaturamentoActionForm.getMesAnoReferenciaFaturamentoInicial());
		
		situacaoEspecialFaturamentoHelper.setMesAnoReferenciaFaturamentoFinal(situacaoEspecialFaturamentoActionForm.getMesAnoReferenciaFaturamentoFinal() == null ? ""
						: situacaoEspecialFaturamentoActionForm.getMesAnoReferenciaFaturamentoFinal());
		
		situacaoEspecialFaturamentoHelper.setVolume(situacaoEspecialFaturamentoActionForm.getVolume() == null ? ""
						: situacaoEspecialFaturamentoActionForm.getVolume());
		
		situacaoEspecialFaturamentoHelper.setPercentualEsgoto(situacaoEspecialFaturamentoActionForm.getPercentualEsgoto() == null ? ""
						: situacaoEspecialFaturamentoActionForm.getPercentualEsgoto());

		return situacaoEspecialFaturamentoHelper;
	}

	public void pesquisarQuadra(String inscricaoTipo,
					SituacaoEspecialFaturamentoInformarActionForm situacaoEspecialFaturamentoInformarActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		Collection colecaoPesquisa = null;
		String setorComercialCD = null;
		String setorComercialID = null;
		String quadraNM = null;

		FiltroQuadra filtroQuadra = new FiltroQuadra();

		// Objetos que serão retornados pelo hibernate.
		// filtroQuadra.adicionarCaminhoParaCarregamentoEntidade("bairro");

		if(inscricaoTipo.equalsIgnoreCase("origem")){
			situacaoEspecialFaturamentoInformarActionForm.setInscricaoTipo("origem");
			// Recebe os valores dos campos setorComercialOrigemCD e setorComercialOrigemID do
			// formulário.
			setorComercialCD = situacaoEspecialFaturamentoInformarActionForm.getSetorComercialOrigemCD();

			setorComercialID = situacaoEspecialFaturamentoInformarActionForm.getSetorComercialOrigemID();

			// Os campos setorComercialOrigemCD e setorComercialID serão obrigatórios
			if(setorComercialCD != null && !setorComercialCD.trim().equalsIgnoreCase("") && setorComercialID != null
							&& !setorComercialID.trim().equalsIgnoreCase("")){

				quadraNM = situacaoEspecialFaturamentoInformarActionForm.getQuadraOrigemID();

				// Adiciona o id do setor comercial que está no formulário para compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercialID));

				// Adiciona o número da quadra que esta no formulário para
				// compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNM));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna quadra
				colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Quadra nao encontrada
					// Limpa os campos quadraOrigemNM e quadraOrigemID do formulário

					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!! NÃO ENCONTRADA !!!!!!!!!!!!!!!!!!!!!!!!
					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

					situacaoEspecialFaturamentoInformarActionForm.setNomeQuadraOrigem("");
					situacaoEspecialFaturamentoInformarActionForm.setQuadraOrigemID("");
					// Mensagem de tela
					situacaoEspecialFaturamentoInformarActionForm.setNomeQuadraOrigem("QUADRA INEXISTENTE.");
					httpServletRequest.setAttribute("corQuadraOrigem", "exception");
					httpServletRequest.setAttribute("nomeCampo", "quadraOrigemNM");
				}else{

					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ENCONTRADA !!!!!!!!!!!!!!!!!!!!!!!!!
					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

					Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
					situacaoEspecialFaturamentoInformarActionForm.setNomeQuadraOrigem("QUADRA " + objetoQuadra.getNumeroQuadra());
					situacaoEspecialFaturamentoInformarActionForm.setQuadraOrigemID(String.valueOf(objetoQuadra.getNumeroQuadra()));
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoNM("QUADRA " + objetoQuadra.getNumeroQuadra());
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoID(String.valueOf(objetoQuadra.getNumeroQuadra()));

					// httpServletRequest.setAttribute("corQuadraOrigem", "valor");
					httpServletRequest.setAttribute("nomeCampo", "loteOrigem");
				}
			}else{
				// Limpa o campo quadraOrigemNM do formulário
				situacaoEspecialFaturamentoInformarActionForm.setNomeQuadraOrigem("");
				situacaoEspecialFaturamentoInformarActionForm.setQuadraMensagemOrigem("Informe Setor Comercial Inicial.");
				httpServletRequest.setAttribute("corQuadraOrigem", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialOrigemCD");
			}
		}else{

			situacaoEspecialFaturamentoInformarActionForm.setInscricaoTipo("destino");

			// Recebe os valores dos campos setorComercialOrigemCD e setorComercialOrigemID do
			// formulário.
			setorComercialCD = situacaoEspecialFaturamentoInformarActionForm.getSetorComercialDestinoCD();
			setorComercialID = situacaoEspecialFaturamentoInformarActionForm.getSetorComercialDestinoID();

			// Os campos setorComercialOrigemCD e setorComercialID serão obrigatórios
			if(setorComercialCD != null && !setorComercialCD.trim().equalsIgnoreCase("") && setorComercialID != null
							&& !setorComercialID.trim().equalsIgnoreCase("")){

				quadraNM = situacaoEspecialFaturamentoInformarActionForm.getQuadraDestinoID();

				// Adiciona o id do setor comercial que está no formulário para compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercialID));

				// Adiciona o número da quadra que esta no formulário para compor a pesquisa.
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNM));

				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna quadra
				colecaoPesquisa = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
					// Quadra nao encontrada
					// Limpa os campos quadraOrigemNM e quadraOrigemID do formulário

					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!! NÃO ENCONTRADA !!!!!!!!!!!!!!!!!!!!!!!!
					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoNM("");
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoID("");
					// Mensagem de tela
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoNM("QUADRA INEXISTENTE.");
					httpServletRequest.setAttribute("corQuadraDestino", "exception");
					httpServletRequest.setAttribute("nomeCampo", "quadraDestinoNM");
				}else{
					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ENCONTRADA !!!!!!!!!!!!!!!!!!!!!!!!!
					// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

					Quadra objetoQuadra = (Quadra) Util.retonarObjetoDeColecao(colecaoPesquisa);
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoNM("QUADRA " + objetoQuadra.getNumeroQuadra());
					situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoID(String.valueOf(objetoQuadra.getNumeroQuadra()));
					// httpServletRequest.setAttribute("corQuadraDestino", "valor");
					httpServletRequest.setAttribute("nomeCampo", "loteDestino");
				}
			}else{
				// Limpa o campo setorComercialOrigemCD do formulário
				situacaoEspecialFaturamentoInformarActionForm.setQuadraDestinoNM("");
				// Mensagem de tela
				situacaoEspecialFaturamentoInformarActionForm.setQuadraMensagemDestino("Informe Setor Comercial.");
				// httpServletRequest.setAttribute("corQuadraDestino", "exception");
				httpServletRequest.setAttribute("nomeCampo", "setorComercialDestinoCD");
			}
		}
	}
}
