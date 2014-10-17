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
 * Thiago Silva Toscano de Brito
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

package gcom.gui.cobranca.spcserasa;

import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.Negativador;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.spcserasa.FiltroNegativador;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * @author Thiago Silva Toscano de Brito
 * @date 22/12/2007
 */
public class ExibirExcluirNegativacaoOnLineAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirExcluirNegativacaoOnLine");

		Fachada fachada = Fachada.getInstancia();

		httpServletRequest.getSession(false);
		ExcluirNegativacaoOnLineActionForm form = (ExcluirNegativacaoOnLineActionForm) actionForm;
		form.setHabilitaBotaoExcluir(Boolean.FALSE);
		form.setEhArquivo(Boolean.FALSE);

		String idImovel = form.getIdImovel();
		FormFile formFileArquivoMatricula = form.getArquivoMatricula();
		// caso tenha passado o negativador
		Integer idNegativador = null;
		if(form.getNegativador() != null && !form.getNegativador().equals("")){
			idNegativador = Integer.parseInt(form.getNegativador());
		}

		// form.setDataHoje(Util.formatarData(new Date()));
		// form.setDataEnvio(Util.formatarData(new Date()));

		FiltroNegativador fn = new FiltroNegativador();
		fn.adicionarCaminhoParaCarregamentoEntidade("cliente");
		String[] ordem = {"cliente.nome"};
		fn.setCampoOrderBy(ordem);

		Collection collection = Fachada.getInstancia().pesquisar(fn, Negativador.class.getName());
		form.setCollNegativador(collection);
		form.setQtdNaoProcessados("0");
		form.setQtdProcessados("0");
		// Verifica se o id do imovel n�o est� cadastrado
		if(idImovel != null && !idImovel.trim().equals("")){

			// Filtro para descobrir id do Imovel
			FiltroImovel filtroImovel = new FiltroImovel();

			filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.ID, idImovel));
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.CEP);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.UNIDADE_FEDERACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.ENDERECO_REFERENCIA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TIPO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_AGUA_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LIGACAO_ESGOTO_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.QUADRA);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOCALIDADE);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.SETOR_COMERCIAL);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.COBRANCA_SITUACAO);
			filtroImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroImovel.LOGRADOURO_TITULO);

			Collection colecaoImovel = fachada.pesquisar(filtroImovel, Imovel.class.getName());

			if(colecaoImovel == null || colecaoImovel.isEmpty()){
				form.setIdImovel("");
				form.setInscricaoImovel("IMOVEL INEXISTENTE");
				httpServletRequest.setAttribute("existeImovel", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idImovel");

			}else{
				Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoImovel);

				if(!form.getIdImovel().equals(form.getIdImovelAnterior()) || !form.getNegativador().equals(form.getNegativadorAnterior())){

					form.setMotivoExclusao("");

					form.setNegativadorAnterior(form.getNegativador());
					form.setIdImovelAnterior(imovel.getId().toString());
					form.setIdImovel(imovel.getId().toString());
					String inscricaoImovel = fachada.pesquisarInscricaoImovel(imovel.getId(), true);
					form.setInscricaoImovel(inscricaoImovel);
					httpServletRequest.setAttribute("existeImovel", "true");
					httpServletRequest.setAttribute("nomeCampo", "idImovel");

					// caso tenha passado o negativador
					if(form.getNegativador() != null && !form.getNegativador().equals("")){

						// caso nao tenha negativacao para o negativador selecionado
						fn = new FiltroNegativador();
						fn.adicionarParametro(new ParametroSimples(FiltroNegativador.ID, form.getNegativador()));
						fn.adicionarCaminhoParaCarregamentoEntidade("cliente");
						Negativador negativador = (Negativador) Util.retonarObjetoDeColecao(fachada.pesquisar(fn, Negativador.class
										.getName()));
						if(negativador == null){
							throw new ActionServletException("atencao.negativador.nao.selecionado");
						}

						Negativador negativ = new Negativador();
						negativ.setId(Integer.valueOf(form.getNegativador()));

						Collection colecaoNegativacao = fachada.pesquisarImovelNegativado(imovel, negativ);

						if(colecaoNegativacao == null || colecaoNegativacao.isEmpty()){
							String[] parametros = {negativador.getCliente().getNome()};
							throw new ActionServletException("atencao.nao.ha.negativacao.para.imovel.selecionado", null, parametros);
						}
					}
				}
				form.setHabilitaBotaoExcluir(Boolean.TRUE);
			}
		}else if(formFileArquivoMatricula != null){

			form.setEhArquivo(Boolean.TRUE);

			this.validarListaImoveisArquivo(form, fachada, httpServletRequest, httpServletResponse);

			// [SB0001] � Validar Im�veis para Exclus�o da Negativa��o
			Collection<Integer> colecaoImoveis = (Collection<Integer>) getSessao(httpServletRequest).getAttribute("colecaoImoveis");
			FiltroNegativador filtroNegativador = new FiltroNegativador();
			filtroNegativador.adicionarParametro(new ParametroSimples(FiltroNegativador.ID, idNegativador));
			filtroNegativador.adicionarCaminhoParaCarregamentoEntidade(FiltroNegativador.CLIENTE);
			Negativador negativador = (Negativador) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroNegativador, Negativador.class
							.getName()));
			Map<String, Collection<Integer>> idImoveis = fachada.validarImoveisExclusaoNegativacao(colecaoImoveis, idNegativador);
			try{
				Collection<String> resumoArquivo = carregarResumoArquivo(idImoveis, form, httpServletResponse, negativador);

				Collection<Integer> idsImoveisAceitos = idImoveis.get("idsImoveisAceitos");
				if(idsImoveisAceitos.size() > 0){
					form.setHabilitaBotaoExcluir(Boolean.TRUE);
				}else{
					form.setHabilitaBotaoExcluir(Boolean.FALSE);
				}

				httpServletRequest.setAttribute("resumoArquivo", resumoArquivo);

			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}else{
			// n�o foi selecionado o imovel
			form.setMotivoExclusao("");
		}

		return retorno;
	}

	private Collection<String> carregarResumoArquivo(Map<String, Collection<Integer>> idImoveis, ExcluirNegativacaoOnLineActionForm form,
					HttpServletResponse httpServletResponse, Negativador negativador) throws IOException{

		Collection<String> resumoArquivo = new ArrayList<String>();

		Collection<Integer> idsImoveisAceitos = idImoveis.get("idsImoveisAceitos");
		Collection<Integer> idsImoveisNaoAceitos = idImoveis.get("idsImoveisNaoAceitos");
		Collection<Integer> idsImoveisNaoCadastrados = idImoveis.get("idsImoveisNaoCadastrados");
		Collection<Integer> idsImoveisNaoNegativados = idImoveis.get("idsImoveisNaoNegativados");

		int quantidadeImoveisAceitos = idsImoveisAceitos.size();
		int quantidadeImoveisNaoAceitos = idsImoveisNaoAceitos.size();
		int quantidadeImoveisNaoCadastrados = idsImoveisNaoCadastrados.size();
		int quantidadeImoveisNaoNegativados = idsImoveisNaoNegativados.size();

		if(quantidadeImoveisAceitos == 0){
			// 1.6. Caso nenhum im�vel do arquivo esteja apto para ser exclu�do da negativa��o
			resumoArquivo.add("Nenhum dos im�veis do arquivo est�o aptos a serem exclu�dos da negativa��o");
			resumoArquivo.add("Total de im�veis: " + quantidadeImoveisNaoAceitos);
			resumoArquivo
							.add("Quantidade de imov�is que n�o constam no cadastro de im�veis da empresa: "
											+ quantidadeImoveisNaoCadastrados);
			resumoArquivo.add("Quantidade de im�veis que n�o est�o negativados no negativador " + negativador.getCliente().getNome() + ": "
							+ quantidadeImoveisNaoNegativados);

		}else{
			// 1.7. Caso algum dos im�veis do arquivo esteja apto para ser exclu�do da
			// negativa��o
			resumoArquivo.add("Quantidade de Im�veis do Arquivo: " + (quantidadeImoveisAceitos + quantidadeImoveisNaoAceitos));
			resumoArquivo.add("Quantidade de Im�veis do Arquivo Aceitos para Exclus�o da Negativa��o: " + quantidadeImoveisAceitos);
			resumoArquivo.add("Quantidade de Im�veis do Arquivo N�o Aceitos para Exclus�o da Negativa��o: " + quantidadeImoveisNaoAceitos);
			resumoArquivo.add("Quantidade de Im�veis N�o Aceitos - N�o constam no cadastro de im�veis da empresa: "
							+ quantidadeImoveisNaoCadastrados);
			resumoArquivo.add("Quantidade de Im�veis N�o Aceitos - N�o est�o negativados no negativador "
							+ negativador.getCliente().getNome() + ": " + quantidadeImoveisNaoNegativados);
		}

		return resumoArquivo;
	}

	private void validarListaImoveisArquivo(ExcluirNegativacaoOnLineActionForm form, Fachada fachada,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		Collection<Integer> colecaoImoveis = new ArrayList<Integer>();

		if(httpServletRequest.getParameter("download") != null && httpServletRequest.getParameter("download").equals("true")){

			FormFile formFile = form.getArquivoMatricula();
			if(formFile != null && !formFile.getFileName().equals("")){
				try{
					// form.setArquivoDownload("");
					String fileType = formFile.getFileName()
									.substring(formFile.getFileName().length() - 4, formFile.getFileName().length());
					if(!fileType.equals(".txt") && !fileType.equals(".csv")){
						throw new ActionServletException("atencao.tipo_importacao.nao_txt", httpServletRequest.getRequestURI(), "");
					}
					Scanner scanner = new Scanner(formFile.getInputStream());

					String imovel;

					while(scanner.hasNext()){
						imovel = scanner.next();
						colecaoImoveis.add(Util.converterStringParaInteger(imovel));
					}

					// [FS0008] - Verificar exist�ncia de dados no arquivo
					if(colecaoImoveis.size() <= 0){
						throw new ActionServletException("atencao.arquivo_sem_dados", httpServletRequest.getRequestURI(), "");
					}

					getSessao(httpServletRequest).removeAttribute("arquivo");
					// form.setArquivoDownload(null);
					// form.setEnderecoArquivoDownload(null);

					getSessao(httpServletRequest).setAttribute("colecaoImoveis", colecaoImoveis);

				}catch(FileNotFoundException e){
					throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
				}catch(IOException e){
					throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
				}
			}else{
				// [FS0010] - Verificar exist�ncia do arquivo de im�veis para exclus�o da
				// negativa��o
				throw new ActionServletException("atencao.arquivo_imoveis_exclusao_negativacao_inexistente", httpServletRequest
								.getRequestURI(), "");
			}

		}

	}
}