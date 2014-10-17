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
 * Ivan Sérgio Virginio da Silva Júnior
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.endereco.FiltroLogradouroBairro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.FiltroImovel;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroQuadra;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroNaoNulo;
import gcom.util.filtro.ParametroSimples;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

public class ExibirFiltrarImovelEmissaoOrdensSeletivasParametros
				extends GcomAction {

	private Collection<Localidade> colecaoPesquisa = null;

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarImovelEmissaoOrdensSeletivasParametros");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		ImovelEmissaoOrdensSeletivasActionForm form = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;
		if(form.getSimulacao() == null){
			form.setSimulacao("1"); // sim
		}
		// Verifica o Tipo do Usuario
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		boolean usuarioPermissaoGerar = fachada.verificarPermissaoGerarOSSeletivasHidrometro(usuario);
		httpServletRequest.setAttribute("usuarioPermissaoGerar", usuarioPermissaoGerar);

		// carrega dados da combo de ServicoTipo
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroServicoTipo.adicionarParametro(new ParametroNaoNulo(FiltroServicoTipo.INDICADOR_ORDEM_SELETIVA));
		filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_ORDEM_SELETIVA,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);

		Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
		httpServletRequest.setAttribute("colecaoServicoTipo", colecaoServicoTipo);

		// Carrega dados do combo Grupo de Faturamento
		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);

		Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
		httpServletRequest.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);

		// Carrega dados do combo Gerencia Regional
		FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
		filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroGerenciaRegional.setCampoOrderBy(FiltroGerenciaRegional.NOME);

		Collection<GerenciaRegional> colecaoGerenciaRegional = fachada.pesquisar(filtroGerenciaRegional, GerenciaRegional.class.getName());
		httpServletRequest.setAttribute("colecaoGerenciaRegional", colecaoGerenciaRegional);

		// carrega dados da combo Localidade
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);

		Collection<Localidade> colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
		httpServletRequest.setAttribute("colecaoLocalidade", colecaoLocalidade);

		if(form.getSimulacao() != null){
			httpServletRequest.setAttribute("simulacao", form.getSimulacao());
		}

		if(sessao.getAttribute("colecaoFirma") == null){
			final FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
			filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);

			Collection<Empresa> colecaoFirma = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

			// [FS0001 - Verificar Existencia de dados]
			if((colecaoFirma == null) || (colecaoFirma.isEmpty())){
				throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, Empresa.class.getName());
			}
			sessao.setAttribute("colecaoFirma", colecaoFirma);

		}

		final String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");
		final String inscricaoTipo = httpServletRequest.getParameter("inscricaoTipo");
		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("") && inscricaoTipo != null
						&& !inscricaoTipo.trim().equalsIgnoreCase("")){
			// ELO
			if(Integer.parseInt(objetoConsulta) == 4){
				pesquisarElo(inscricaoTipo, form, fachada, httpServletRequest);
			}
		}else{
			sessao.removeAttribute("imovelEmissaoOrdensSeletivas");
		}

		// Usado para fazer o controle de navegacao por conta da Aba Local
		sessao.setAttribute("abaAtual", "PARAMETROS");

		if(form.getServicoTipo() != null && !form.getServicoTipo().equalsIgnoreCase("-1")){
			Boolean mesmoSubgrupo = false;
			try{
				mesmoSubgrupo = fachada.comparaServicoTipoSubgrupoSubstituicaoHidrometro(form.getServicoTipo());
			}catch(ControladorException e){
			}
			if(mesmoSubgrupo){
				httpServletRequest.setAttribute("servicoTipo", "SUBSTITUIÇÃO");
			}
		}
		if(form.getArquivoMatricula() != null && GenericValidator.isBlankOrNull(form.getArquivoMatricula().getFileName())){
			// mantêm o estado das combos de multipla escolha selecionadas e carregadas via ajax
			// Setor
			if(form.getLocalidade() != null && form.getLocalidade().length == 1){
				FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID_LOCALIDADE, form.getLocalidade()[0]));
				filtroSetorComercial.setCampoOrderBy(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL);

				Collection<SetorComercial> colecaoSetores = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

				httpServletRequest.setAttribute("colecaoSetores", colecaoSetores);
			}
			// Bairro
			if(form.getLocalidade() != null && form.getLocalidade().length == 1){
				filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, form.getLocalidade()[0]));
				filtroLocalidade.adicionarCaminhoParaCarregamentoEntidade(FiltroLocalidade.MUNICIPIO);

				Collection<Localidade> colecaoLocalidadeTMP = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				Collection<Bairro> colecaoBairros = null;
				if(colecaoLocalidadeTMP != null && !colecaoLocalidadeTMP.isEmpty()
								&& colecaoLocalidadeTMP.iterator().next().getMunicipio() != null
								&& !colecaoLocalidadeTMP.iterator().next().getMunicipio().equals("")){
					FiltroBairro filtroBairro = new FiltroBairro();
					filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.MUNICIPIO_ID, colecaoLocalidadeTMP.iterator().next()
									.getMunicipio().getId().toString()));
					filtroBairro
									.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO,
													ConstantesSistema.INDICADOR_USO_ATIVO));
					filtroBairro.setCampoOrderBy(FiltroBairro.NOME);

					colecaoBairros = fachada.pesquisar(filtroBairro, Bairro.class.getName());
				}
				httpServletRequest.setAttribute("colecaoBairros", colecaoBairros);
			}
			// Rota
			if(form.getSetor() != null && form.getSetor().length == 1){
				FiltroRota filtroRota = new FiltroRota();
				filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.SETOR_COMERCIAL_ID, form.getSetor()[0]));
				filtroRota.setCampoOrderBy(FiltroRota.CODIGO_ROTA);

				Collection<Rota> colecaoRotas = fachada.pesquisar(filtroRota, Rota.class.getName());

				httpServletRequest.setAttribute("colecaoRotas", colecaoRotas);
			}
			// Quadra
			if(form.getSetor() != null && form.getSetor().length == 1){
				FiltroQuadra filtroQuadra = new FiltroQuadra();
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.CODIGO_SETORCOMERCIAL, form.getSetor()[0]));
				filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroQuadra.setCampoOrderBy(FiltroQuadra.NUMERO_QUADRA);

				Collection<Quadra> colecaoQuadras = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

				httpServletRequest.setAttribute("colecaoQuadras", colecaoQuadras);
			}
			// Logradouro
			if(form.getBairro() != null && form.getBairro().length == 1){
				FiltroLogradouroBairro filtroLogradouroBairro = new FiltroLogradouroBairro();
				filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.INDICADORUSO_BAIRRO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				filtroLogradouroBairro.adicionarParametro(new ParametroSimples(FiltroLogradouroBairro.ID_BAIRRO, form.getBairro()[0]));
				filtroLogradouroBairro.adicionarCaminhoParaCarregamentoEntidade(FiltroLogradouroBairro.LOGRADOURO);
				filtroLogradouroBairro.setCampoOrderBy(FiltroLogradouroBairro.NOME_LOGRADOURO);

				Collection<LogradouroBairro> colecaoLogradouroBairro = fachada.pesquisar(filtroLogradouroBairro, LogradouroBairro.class
								.getName());
				Collection<Logradouro> colecaoLogradouros = new ArrayList<Logradouro>();

				for(Iterator<LogradouroBairro> iterator = colecaoLogradouroBairro.iterator(); iterator.hasNext();){
					colecaoLogradouros.add(iterator.next().getLogradouro());
				}
				httpServletRequest.setAttribute("colecaoLogradouros", colecaoLogradouros);
			}
			// Lote
			String[] quadra = form.getQuadra();
			if(quadra != null && quadra.length == 1){
				String idQuadra = quadra[0];

				Collection colecaoLotes = new ArrayList();

				FiltroImovel filtroImovel = new FiltroImovel();
				filtroImovel.adicionarParametro(new ParametroSimples(FiltroImovel.QUADRA_ID, idQuadra));
				filtroImovel.setCampoOrderBy(FiltroImovel.LOTE);

				Collection<Imovel> colecaoImoveis = fachada.pesquisar(filtroImovel, Imovel.class.getName());

				if(!Util.isVazioOrNulo(colecaoImoveis)){
					for(Imovel imovel : colecaoImoveis){
						Short lote = imovel.getLote();

						if(!colecaoLotes.contains(lote)){
							colecaoLotes.add(imovel);
						}
					}
				}

				httpServletRequest.setAttribute("colecaoLotes", colecaoLotes);
			}
		}
		if(form.getArquivoMatricula() != null && !form.getArquivoMatricula().getFileName().equals("")){
			try{
				FormFile formFile = form.getArquivoMatricula();
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
				Collection<Integer> idsImoveis = new ArrayList();
				for(Imovel item : (Collection<Imovel>) colecaoImoveis){
					idsImoveis.add(item.getId());
				}

				// Prepara arquivo com erros
				File arquivoErro = new File("ArquivoErro.txt");
				BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(arquivoErro
								.getAbsolutePath())));
				if(devolucao.isEmpty()){
					form.setArquivoDownload("arquivo validado com sucesso. Foram encontrados " + colecaoImoveis.size()
									+ " imóveis válidos.");
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
									+ " falhas no arquivo. Clique aqui para baixar o arquivo com a descrição das falhas. ");
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
					form.setArquivoDownload("Nenhum imóvel foi encontrado no arquivo.");
					form.setArquivoCarregado("");
				}
			}catch(FileNotFoundException e){
				throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
			}catch(IOException e){
				throw new ActionServletException("", httpServletRequest.getRequestURI(), "");
			}
		}

		return retorno;
	}

	private void pesquisarElo(String inscricaoTipo, ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas, Fachada fachada,
					HttpServletRequest httpServletRequest){

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		// Recebe o valor do campo elo do formulário.
		String eloID = imovelEmissaoOrdensSeletivas.getElo();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID_ELO, eloID));

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna localidade
		colecaoPesquisa = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		if(colecaoPesquisa == null || colecaoPesquisa.isEmpty()){
			// Localidade nao encontrada
			imovelEmissaoOrdensSeletivas.setElo("");
			imovelEmissaoOrdensSeletivas.setNomeElo("Elo inexistente.");

			httpServletRequest.setAttribute("corElo", "exception");
			httpServletRequest.setAttribute("nomeCampo", "elo");
		}else{
			Localidade objetoLocalidade = (Localidade) Util.retonarObjetoDeColecao(colecaoPesquisa);

			objetoLocalidade = objetoLocalidade.getLocalidade();

			imovelEmissaoOrdensSeletivas.setElo(objetoLocalidade.getId().toString());
			imovelEmissaoOrdensSeletivas.setNomeElo(objetoLocalidade.getDescricao());

			httpServletRequest.setAttribute("corElo", "valor");
		}
	}
}
