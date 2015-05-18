
package gcom.gui.cobranca.prescricao;

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

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.cliente.FiltroClienteRelacaoTipo;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.FiltroCobrancaSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * [UC3138] Filtrar D�bito para Prescri��o
 * 
 * @author Anderson Italo
 * @date 19/02/2014
 */
public class ExibirComandarPrescricaoDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirComandarPrescricaoDebitos");

		ComandarPrescricaoDebitosActionForm form = (ComandarPrescricaoDebitosActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		boolean temPermissaoComandarPrescricaoDebitos = fachada.verificarPermissaoComandarPrescricaoDebito(usuarioLogado);

		if(temPermissaoComandarPrescricaoDebitos){

			sessao.setAttribute("temPermissaoComandarPrescricaoDebitos", temPermissaoComandarPrescricaoDebitos);
		}else{

			form.setIndicadorSimulacao(ConstantesSistema.SIM.toString());
		}

		// Faz a pesquisa das Gerencias Regionais
		this.pesquisarGerenciaRegional(httpServletRequest);

		// Faz a pesquisa das Unidades de Neg�cio
		this.pesquisarUnidadeNegocio(httpServletRequest);

		// Flag indicando que o usu�rio fez uma consulta a partir da tecla Enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade Elo
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("10"))){

			// Faz a consulta de Localidade Elo
			this.pesquisarLocalidadeElo(form, objetoConsulta);
		}

		// Pesquisar Localidade
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("1") || objetoConsulta.trim().equals("3"))){

			// Faz a consulta de Localidade
			this.pesquisarLocalidade(form, objetoConsulta);
		}

		// Pesquisar Setor Comercial
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("2") || objetoConsulta.trim().equals("4"))){

			// Faz a consulta de Setor Comercial
			this.pesquisarSetorComercial(form, objetoConsulta);
		}

		// Pesquisar Quadra
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("5") || objetoConsulta.trim().equals("6"))){

			// Faz a consulta de Quadra
			this.pesquisarQuadra(form, objetoConsulta, httpServletRequest);
		}

		// Pesquisar Cliente
		if(!Util.isVazioOuBranco(objetoConsulta) && (objetoConsulta.trim().equals("11"))){

			// Faz a consulta de Cliente
			this.pesquisarCliente(form, objetoConsulta);
		}

		// Faz a pesquisa dos Tipos de Rela��o de Cliente
		this.pesquisarClienteRelacaoTipo(httpServletRequest);

		// Faz a pesquisa das Categorias
		this.pesquisarCategoria(httpServletRequest);

		// Faz a pesquisa das SubCategorias
		this.pesquisarSubCategoria(httpServletRequest, form);

		// Faz a pesquisa das Situa��es de Liga��o de �gua
		this.pesquisarLigacaoAguaSituacao(httpServletRequest);

		// Faz a pesquisa das Situa��es de Liga��o de Esgoto
		this.pesquisarLigacaoEsgotoSituacao(httpServletRequest);

		// Faz a pesquisa das Situa��es de Cobran�a
		this.pesquisarCobrancaSituacao(httpServletRequest);

		// [FS0006 - Verificar exist�ncia do arquivo de im�veis]
		if(form.getArquivoMatricula() != null && !form.getArquivoMatricula().getFileName().equals("")){

			try{

				FormFile formFile = form.getArquivoMatricula();
				Scanner scanner = new Scanner(formFile.getInputStream());
				Collection colecaoImoveis = new ArrayList();
				Collection<String> devolucao = new ArrayList<String>();
				Collection<Integer> idsImoveis = new ArrayList();
				String matricula;
				Imovel imovel;

				while(scanner.hasNext()){

					matricula = scanner.next();

					try{

						imovel = fachada.pesquisarImovel(Integer.valueOf(matricula));

						if(imovel == null){

							devolucao.add(matricula + " MATR�CULA INEXISTENTE");
						}else if(colecaoImoveis.contains(imovel)){

							devolucao.add(matricula + " MATRICULA REPETIDA NO ARQUIVO");
						}else{

							colecaoImoveis.add(imovel);
							idsImoveis.add(imovel.getId());
						}

					}catch(NumberFormatException e){

						devolucao.add(matricula + " N�O � UMA MATR�CULA VALIDA");
					}
				}

				// // Seta os imoveis para a pesquisa
				// Collection<Integer> idsImoveis = new ArrayList();
				// for(Imovel item : (Collection<Imovel>) colecaoImoveis){
				//
				// idsImoveis.add(item.getId());
				// }

				// Prepara arquivo com erros
				File arquivoErro = new File("ArquivoErro.txt");

				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
								arquivoErro.getAbsolutePath())));

				if(devolucao.isEmpty()){

					form.setArquivoDownload("arquivo validado com sucesso. Foram encontrados " + colecaoImoveis.size()
									+ " im�veis v�lidos.");
					form.setArquivoCarregado(form.getArquivoMatricula().getFileName());
				}else{

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

					form.setArquivoDownload("Foram encontradas " + devolucao.size()
									+ " falhas no arquivo. Clique aqui para baixar o arquivo com a descri��o das falhas. ");
					form.setEnderecoArquivoDownload(arquivoErro.getPath());

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
						form.setArquivoDownload(null);
						form.setArquivoCarregado("");
						form.setEnderecoArquivoDownload(null);
					}
				}

				if(idsImoveis.size() > 0){

					form.setIdsImoveis(idsImoveis);
				}else{

					form.setArquivoDownload("Nenhum im�vel foi encontrado no arquivo.");
					form.setArquivoCarregado("");
				}
			}catch(FileNotFoundException e){

				throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
			}catch(IOException e){

				throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
			}
		}

		// Seta os request�s encontrados
		this.setaRequest(httpServletRequest, form);

		return retorno;
	}

	/**
	 * Pesquisar Gerencial Regional
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarGerenciaRegional(HttpServletRequest httpServletRequest){

		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();

		filtroGerenciaRegional.setConsultaSemLimites(true);
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoGerenciaRegional = this.getFachada().pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoGerenciaRegional == null || colecaoGerenciaRegional.isEmpty()){
			throw new ActionServletException("atencao.naocadastrado", null, "Ger�ncia Regional");
		}else{
			httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);
		}
	}

	/**
	 * Pesquisar Unidade de Neg�cio
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarUnidadeNegocio(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroUnidadeNegocio filtroUnidadeNegocio = new FiltroUnidadeNegocio();

		filtroUnidadeNegocio.setCampoOrderBy(FiltroUnidadeNegocio.NOME);

		filtroUnidadeNegocio.adicionarParametro(new ParametroSimples(FiltroUnidadeNegocio.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Gerencia_Regional
		Collection colecaoUnidadeNegocio = this.getFachada().pesquisar(filtroUnidadeNegocio, UnidadeNegocio.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoUnidadeNegocio == null || colecaoUnidadeNegocio.isEmpty()){

			// Nenhum registro na tabela gerencia_regional foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Unidade de Neg�cio");
		}else{

			UnidadeNegocio unidadeNegocio = null;
			Iterator iterator = colecaoUnidadeNegocio.iterator();

			while(iterator.hasNext()){
				unidadeNegocio = (UnidadeNegocio) iterator.next();

				String descUnidadeNegocio = unidadeNegocio.getNomeAbreviado() + "-" + unidadeNegocio.getNome();
				unidadeNegocio.setNome(descUnidadeNegocio);

			}

			httpServletRequest.setAttribute("colecaoUnidadeNegocio", colecaoUnidadeNegocio);
		}

	}

	/**
	 * Pesquisar Localidade
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarLocalidade(ComandarPrescricaoDebitosActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidadeInicial();

		if(!objetoConsulta.trim().equals("1")){
			local = form.getIdLocalidadeFinal();
		}

		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(form.getIdLocalidadeFinal())){

			if(Util.obterInteger(form.getIdLocalidadeFinal()).intValue() > Util.obterInteger(form.getIdLocalidadeInicial()).intValue()){

				form.setCodigoSetorComercialInicial(null);
				form.setDescricaoSetorComercialInicial(null);
				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal(null);
				form.setNumeroQuadraInicial(null);
				form.setNumeroQuadraFinal(null);
			}
		}

		// [FS0003] - Verificar exist�ncia da localidade
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, local));

		if(!Util.isVazioOuBranco(form.getIdGerenciaRegional())){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_GERENCIA, form.getIdGerenciaRegional()));
		}

		if(!Util.isVazioOuBranco(form.getIdUnidadeNegocio())){
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_UNIDADE_NEGOCIO, form.getIdUnidadeNegocio()));
		}

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			if(objetoConsulta.trim().equals("1")){
				form.setIdLocalidadeInicial(localidade.getId().toString());
				form.setDescricaoLocalidadeInicial(localidade.getDescricao());
			}

			form.setIdLocalidadeFinal(localidade.getId().toString());
			form.setDescricaoLocalidadeFinal(localidade.getDescricao());

		}else{

			if(objetoConsulta.trim().equals("1")){
				form.setIdLocalidadeInicial(null);
				form.setDescricaoLocalidadeInicial("Localidade Inicial inexistente");

				form.setIdLocalidadeFinal(null);
				form.setDescricaoLocalidadeFinal(null);
			}else{
				form.setIdLocalidadeFinal(null);
				form.setDescricaoLocalidadeFinal("Localidade Final inexistente");
			}
		}
	}

	/**
	 * Pesquisar Localidade Elo
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarLocalidadeElo(ComandarPrescricaoDebitosActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidadeElo();

		if(!Util.isVazioOuBranco(form.getIdLocalidadeElo())){

			form.setIdLocalidadeInicial(null);
			form.setDescricaoLocalidadeInicial(null);
			form.setIdLocalidadeFinal(null);
			form.setDescricaoLocalidadeFinal(null);
			form.setCodigoSetorComercialInicial(null);
			form.setDescricaoSetorComercialInicial(null);
			form.setCodigoSetorComercialFinal(null);
			form.setDescricaoSetorComercialFinal(null);
			form.setNumeroQuadraInicial(null);
			form.setNumeroQuadraFinal(null);

		}

		// [FS0002] - Verificar exist�ncia do elo
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO_ID, local));

		// Recupera Localidade
		Collection colecaoLocalidade = this.getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
			form.setIdLocalidadeElo(localidade.getId().toString());
			form.setDescricaoLocalidadeElo(localidade.getDescricao());

		}else{

			form.setIdLocalidadeElo(null);
			form.setDescricaoLocalidadeElo("Elo inexistente");
		}
	}

	/**
	 * Pesquisar Setor comercial
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarSetorComercial(ComandarPrescricaoDebitosActionForm form, String objetoConsulta){

		Object local = form.getIdLocalidadeInicial();
		Object setor = form.getCodigoSetorComercialInicial();

		if(!objetoConsulta.trim().equals("2")){

			local = form.getIdLocalidadeFinal();
			setor = form.getCodigoSetorComercialFinal();
		}

		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(form.getIdLocalidadeFinal())){

			if(Util.obterInteger(form.getIdLocalidadeFinal()).intValue() > Util.obterInteger(form.getIdLocalidadeInicial()).intValue()){

				form.setCodigoSetorComercialInicial(null);
				form.setDescricaoSetorComercialInicial(null);
				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal(null);
				form.setNumeroQuadraInicial(null);
				form.setNumeroQuadraFinal(null);
				return;
			}
		}

		// [FS0004] - Verificar exist�ncia do setor
		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, local));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		if(colecaoSetorComercial != null && !colecaoSetorComercial.isEmpty()){

			SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

			if(objetoConsulta.trim().equals("2")){

				form.setCodigoSetorComercialInicial(String.valueOf(setorComercial.getCodigo()));
				form.setDescricaoSetorComercialInicial(setorComercial.getDescricao());
			}

			form.setCodigoSetorComercialFinal(String.valueOf(setorComercial.getCodigo()));
			form.setDescricaoSetorComercialFinal(setorComercial.getDescricao());

		}else{

			if(objetoConsulta.trim().equals("2")){

				form.setCodigoSetorComercialInicial(null);
				form.setDescricaoSetorComercialInicial("Setor Comercial Inicial inexistente");

				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal(null);
			}else{

				form.setCodigoSetorComercialFinal(null);
				form.setDescricaoSetorComercialFinal("Setor Comercial Final inexistente");
			}

		}
	}

	/**
	 * Seta os request com os id encontrados
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void setaRequest(HttpServletRequest httpServletRequest, ComandarPrescricaoDebitosActionForm form){

		// Localidade Elo
		if(!Util.isVazioOuBranco(form.getIdLocalidadeElo()) && !Util.isVazioOuBranco(form.getDescricaoLocalidadeElo())){

			httpServletRequest.setAttribute("localidadeEloEncontrada", "true");
		}

		// Localidade Inicial
		if(!Util.isVazioOuBranco(form.getIdLocalidadeInicial()) && !Util.isVazioOuBranco(form.getDescricaoLocalidadeInicial())){

			httpServletRequest.setAttribute("localidadeInicialEncontrada", "true");
			httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
			this.getSessao(httpServletRequest).setAttribute("idLocalidade", form.getIdLocalidadeInicial());
		}else{

			if(!Util.isVazioOuBranco(form.getIdLocalidadeFinal()) && !Util.isVazioOuBranco(form.getDescricaoLocalidadeFinal())){

				httpServletRequest.setAttribute("localidadeFinalEncontrada", "true");
				this.getSessao(httpServletRequest).setAttribute("idLocalidade", form.getIdLocalidadeFinal());
			}
		}

		// Setor Comercial Inicial
		if(!Util.isVazioOuBranco(form.getCodigoSetorComercialInicial()) && !Util.isVazioOuBranco(form.getDescricaoSetorComercialInicial())){

			httpServletRequest.setAttribute("setorComercialInicialEncontrado", "true");
			httpServletRequest.setAttribute("setorComercialFinalEncontrado", "true");
		}else{

			if(!Util.isVazioOuBranco(form.getCodigoSetorComercialFinal()) && !Util.isVazioOuBranco(form.getDescricaoSetorComercialFinal())){

				httpServletRequest.setAttribute("setorComercialFinalEncontrado", "true");
			}
		}

		// Quadra Inicial
		if(Util.isVazioOuBranco(form.getNumeroQuadraInicial()) && httpServletRequest.getAttribute("msgQuadraInicial") != null){

			httpServletRequest.setAttribute("quadraInicialNaoEncontrada", "true");
		}else{

			if(Util.isVazioOuBranco(form.getNumeroQuadraFinal()) && httpServletRequest.getAttribute("msgQuadraFinal") != null){

				httpServletRequest.setAttribute("quadraFinalNaoEncontrada", "true");
			}
		}

		// Cliente
		if(!Util.isVazioOuBranco(form.getIdCliente()) && !Util.isVazioOuBranco(form.getNomeCliente())){

			httpServletRequest.setAttribute("clienteEncontrado", "true");
		}

	}

	/**
	 * Pesquisar Setor comercial
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarQuadra(ComandarPrescricaoDebitosActionForm form, String objetoConsulta,
					HttpServletRequest httpServletRequest){

		Object setor = form.getCodigoSetorComercialInicial();
		Object quadraNumero = form.getNumeroQuadraInicial();

		if(!objetoConsulta.trim().equals("5")){

			setor = form.getCodigoSetorComercialFinal();
			quadraNumero = form.getNumeroQuadraFinal();
		}

		if(!Util.isVazioOuBranco(form.getCodigoSetorComercialInicial()) && !Util.isVazioOuBranco(form.getCodigoSetorComercialFinal())){

			if(Util.obterInteger(form.getCodigoSetorComercialFinal()).intValue() > Util.obterInteger(form.getCodigoSetorComercialInicial())
							.intValue()){

				form.setNumeroQuadraInicial(null);
				form.setNumeroQuadraFinal(null);
				return;
			}
		}

		// [FS0005] - Verificar exist�ncia da quadra
		FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, setor));

		filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.LOCALIDADE, Util.obterInteger(form
						.getIdLocalidadeInicial())));

		// Recupera Setor Comercial
		Collection colecaoSetorComercial = this.getFachada().pesquisar(filtroSetorComercial, SetorComercial.class.getName());

		SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

		FiltroQuadra filtroQuadra = new FiltroQuadra();
		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID_SETORCOMERCIAL, setorComercial.getId()));

		filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.NUMERO_QUADRA, quadraNumero));

		// Recupera Quadra
		Collection colecaoQuadra = this.getFachada().pesquisar(filtroQuadra, Quadra.class.getName());

		if(colecaoQuadra != null && !colecaoQuadra.isEmpty()){

			Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

			if(objetoConsulta.trim().equals("5")){

				form.setNumeroQuadraInicial(String.valueOf(quadra.getNumeroQuadra()));
			}

			form.setNumeroQuadraFinal(String.valueOf(quadra.getNumeroQuadra()));

		}else{

			if(objetoConsulta.trim().equals("5")){

				form.setNumeroQuadraInicial(null);
				httpServletRequest.setAttribute("msgQuadraInicial", "Quadra Inicial inexistente");
				form.setNumeroQuadraFinal(null);
			}else{

				httpServletRequest.setAttribute("msgQuadraFinal", "Quadra Final inexistente");
				form.setNumeroQuadraFinal(null);
			}

		}
	}

	/**
	 * Pesquisar Cliente
	 * 
	 * @author Anderson Italo
	 * @date 14/02/2014
	 */
	private void pesquisarCliente(ComandarPrescricaoDebitosActionForm form, String objetoConsulta){

		Object idCliente = form.getIdCliente();

		// [FS0012 - Verificar exist�ncia do cliente]
		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));

		// Recupera Cliente
		Collection colecaoCliente = this.getFachada().pesquisar(filtroCliente, Cliente.class.getName());

		if(colecaoCliente != null && !colecaoCliente.isEmpty()){

			Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
			form.setIdCliente(cliente.getId().toString());
			form.setNomeCliente(cliente.getNome());

		}else{

			form.setIdCliente(null);
			form.setNomeCliente("Cliente inexistente");
		}
	}

	/**
	 * Pesquisar Tipo da Rela��o do Cliente
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarClienteRelacaoTipo(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroClienteRelacaoTipo filtroClienteRelacaoTipo = new FiltroClienteRelacaoTipo();

		filtroClienteRelacaoTipo.setCampoOrderBy(FiltroClienteRelacaoTipo.DESCRICAO);

		filtroClienteRelacaoTipo
						.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna cliente_relacao_tipo
		Collection colecaoClienteRelacaoTipo = this.getFachada().pesquisar(filtroClienteRelacaoTipo, ClienteRelacaoTipo.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoClienteRelacaoTipo == null || colecaoClienteRelacaoTipo.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Tipo da Rela��o do Cliente");
		}else{

			httpServletRequest.setAttribute("colecaoClienteRelacaoTipo", colecaoClienteRelacaoTipo);
		}
	}

	/**
	 * Pesquisar Categoria
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarCategoria(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroCategoria filtroCategoria = new FiltroCategoria();

		filtroCategoria.setCampoOrderBy(FiltroCategoria.DESCRICAO);

		filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna categoria
		Collection colecaoCategoria = this.getFachada().pesquisar(filtroCategoria, Categoria.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoCategoria == null || colecaoCategoria.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Categoria");
		}else{

			httpServletRequest.setAttribute("colecaoCategoria", colecaoCategoria);
		}
	}

	/**
	 * Pesquisar Subcategoria
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarSubCategoria(HttpServletRequest httpServletRequest, ComandarPrescricaoDebitosActionForm form){

		if(!Util.isVazioOrNulo(form.getIdCategoria())){

			if(form.getIdCategoria().length == 1){

				// Carregamento inicial do formul�rio.
				FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();

				filtroSubCategoria.setCampoOrderBy(FiltroSubCategoria.DESCRICAO);

				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.CATEGORIA_ID, Util.obterInteger(form
								.getIdCategoria()[0])));

				filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				// Retorna sub_categoria
				Collection colecaoSubCategoria = this.getFachada().pesquisar(filtroSubCategoria, Subcategoria.class.getName());
				httpServletRequest.setAttribute("colecaoSubCategoria", colecaoSubCategoria);

			}else{

				httpServletRequest.removeAttribute("colecaoSubCategoria");
				form.setIdSubCategoria(null);
			}
		}
	}

	/**
	 * Pesquisar Situa��o de Liga��o de �gua
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarLigacaoAguaSituacao(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();

		filtroLigacaoAguaSituacao.setCampoOrderBy(FiltroLigacaoAguaSituacao.DESCRICAO);

		filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Situa��od e Liga��o de �gua
		Collection colecaoLigacaoAguaSituacao = this.getFachada().pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoLigacaoAguaSituacao == null || colecaoLigacaoAguaSituacao.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Liga��o �gua Situa��o");
		}else{

			httpServletRequest.setAttribute("colecaoLigacaoAguaSituacao", colecaoLigacaoAguaSituacao);
		}
	}

	/**
	 * Pesquisar Situa��o de Liga��o de Esgoto
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarLigacaoEsgotoSituacao(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();

		filtroLigacaoEsgotoSituacao.setCampoOrderBy(FiltroLigacaoEsgotoSituacao.DESCRICAO);

		filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Situa��o de Liga��o de Esgoto
		Collection colecaoLigacaoEsgotoSituacao = this.getFachada().pesquisar(filtroLigacaoEsgotoSituacao,
						LigacaoEsgotoSituacao.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoLigacaoEsgotoSituacao == null || colecaoLigacaoEsgotoSituacao.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Liga��o Esgoto Situa��o");
		}else{

			httpServletRequest.setAttribute("colecaoLigacaoEsgotoSituacao", colecaoLigacaoEsgotoSituacao);
		}
	}

	/**
	 * Pesquisar Situa��o de Cobran�a
	 * 
	 * @author Anderson Italo
	 * @date 13/02/2014
	 */
	private void pesquisarCobrancaSituacao(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formul�rio.
		FiltroCobrancaSituacao filtroCobrancaSituacao = new FiltroCobrancaSituacao();

		filtroCobrancaSituacao.setCampoOrderBy(FiltroCobrancaSituacao.DESCRICAO);

		filtroCobrancaSituacao.adicionarParametro(new ParametroSimples(FiltroCobrancaSituacao.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Situa��o de Cobran�a
		Collection colecaoCobrancaSituacao = this.getFachada().pesquisar(filtroCobrancaSituacao, CobrancaSituacao.class.getName());

		// [FS0001] - Verificar exist�ncia de dados
		if(colecaoCobrancaSituacao == null || colecaoCobrancaSituacao.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Cobran�a Situa��o");
		}else{

			httpServletRequest.setAttribute("colecaoCobrancaSituacao", colecaoCobrancaSituacao);
		}
	}
}

