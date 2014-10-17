/**
 * 
 */
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

package gcom.gui.cobranca.prescricao;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.CobrancaSituacao;
import gcom.cobranca.prescricao.*;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;

/**
 * [UC3142] Pesquisar Comando de Prescri��o
 * 
 * @author Anderson Italo
 * @since 13/04/2014
 */
public class ExibirResultadoPesquisaComandoPrescricaoParametrosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirResultadoPesquisaComandoPrescricaoParametros");

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		String idPrescricaoComando = httpServletRequest.getParameter("idPrescricaoComando");

		sessao.removeAttribute("prescricaoComando");
		sessao.removeAttribute("colecaoCategoriaComando");
		sessao.removeAttribute("colecaoSubcategoriaComando");
		sessao.removeAttribute("colecaoLigacaoAguaSituacaoComando");
		sessao.removeAttribute("colecaoLigacaoEsgotoSituacaoComando");
		sessao.removeAttribute("colecaoCobrancaSituacaoComando");
		sessao.removeAttribute("matriculasImoveisComando");

		Collection colecaoPrescricaoComando = null;

		FiltroPrescricaoComando filtroPrescricaoComando = new FiltroPrescricaoComando();
		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.GERENCIA_REGIONAL);
		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.UNIDADE_NEGOCIO);
		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.ELO);
		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.LOCALIDADE_INICIAL);
		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.LOCALIDADE_FINAL);
		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.CLIENTE);
		filtroPrescricaoComando.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComando.CLIENTE_RELACAO_TIPO);
		filtroPrescricaoComando.adicionarParametro(new ParametroSimples(FiltroPrescricaoComando.ID, idPrescricaoComando));

		colecaoPrescricaoComando = fachada.pesquisar(filtroPrescricaoComando, PrescricaoComando.class.getName());

		PrescricaoComando prescricaoComando = (PrescricaoComando) Util.retonarObjetoDeColecao(colecaoPrescricaoComando);

		if(prescricaoComando.getArquivoImoveis() != null){

			sessao.setAttribute("matriculasImoveisComando",
							this.obterConteudoArquivoMatriculasFormatando(Hibernate.createBlob(prescricaoComando.getArquivoImoveis())));
		}

		// Categoria e Subcategoria
		FiltroPrescricaoComandoCategoriaSubcategoria filtroPrescricaoComandoCategoriaSubcategoria = new FiltroPrescricaoComandoCategoriaSubcategoria();
		filtroPrescricaoComandoCategoriaSubcategoria.adicionarParametro(new ParametroSimples(
						FiltroPrescricaoComandoCategoriaSubcategoria.ID_PRESCRICAO_COMANDO, prescricaoComando.getId()));
		filtroPrescricaoComandoCategoriaSubcategoria
						.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComandoCategoriaSubcategoria.CATEGORIA);
		filtroPrescricaoComandoCategoriaSubcategoria
						.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComandoCategoriaSubcategoria.SUBCATEGORIA);
		filtroPrescricaoComandoCategoriaSubcategoria.setCampoOrderBy(FiltroPrescricaoComandoCategoriaSubcategoria.ID_CATEGORIA,
						FiltroPrescricaoComandoCategoriaSubcategoria.ID_SUBCATEGORIA);

		Collection<PrescricaoComandoCategoriaSubcategoria> colecaoPrescricaoComandoCategoriaSubcategoria = fachada.pesquisar(
						filtroPrescricaoComandoCategoriaSubcategoria, PrescricaoComandoCategoriaSubcategoria.class.getName());

		if(!Util.isVazioOrNulo(colecaoPrescricaoComandoCategoriaSubcategoria)){

			boolean primeiraVez = true;
			Collection<Categoria> colecaoCategoria = new ArrayList<Categoria>();
			Collection<Subcategoria> colecaoSubcategoria = new ArrayList<Subcategoria>();

			for(PrescricaoComandoCategoriaSubcategoria prescricaoComandoCategoriaSubcategoria : colecaoPrescricaoComandoCategoriaSubcategoria){

				if(prescricaoComandoCategoriaSubcategoria.getSubcategoria() != null){

					if(primeiraVez){

						colecaoCategoria.add(prescricaoComandoCategoriaSubcategoria.getCategoria());
						colecaoSubcategoria.add(prescricaoComandoCategoriaSubcategoria.getSubcategoria());
					}else{

						colecaoSubcategoria.add(prescricaoComandoCategoriaSubcategoria.getSubcategoria());
					}
				}else{

					colecaoCategoria.add(prescricaoComandoCategoriaSubcategoria.getCategoria());
				}

				primeiraVez = false;
			}

			if(!Util.isVazioOrNulo(colecaoSubcategoria)){

				sessao.setAttribute("colecaoCategoriaComando", colecaoCategoria);
				sessao.setAttribute("colecaoSubcategoriaComando", colecaoSubcategoria);
			}else if(!Util.isVazioOuBranco(colecaoCategoria)){

				sessao.setAttribute("colecaoCategoriaComando", colecaoCategoria);
			}
		}

		// Situa��o da Liga��o de �gua
		FiltroPrescricaoComandoLigacaoAgua filtroPrescricaoComandoLigacaoAgua = new FiltroPrescricaoComandoLigacaoAgua();
		filtroPrescricaoComandoLigacaoAgua.adicionarParametro(new ParametroSimples(
						FiltroPrescricaoComandoLigacaoAgua.ID_PRESCRICAO_COMANDO, prescricaoComando.getId()));
		filtroPrescricaoComandoLigacaoAgua
						.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComandoLigacaoAgua.LIGACAO_AGUA_SITUACAO);
		filtroPrescricaoComandoLigacaoAgua.setCampoOrderBy(FiltroPrescricaoComandoLigacaoAgua.ID_LIGACAO_AGUA_SITUACAO);

		Collection<PrescricaoComandoLigacaoAgua> colecaoPrescricaoComandoLigacaoAgua = fachada.pesquisar(
						filtroPrescricaoComandoLigacaoAgua, PrescricaoComandoLigacaoAgua.class.getName());

		if(!Util.isVazioOrNulo(colecaoPrescricaoComandoLigacaoAgua)){

			Collection<LigacaoAguaSituacao> colecaoLigacaoAguaSituacao = new ArrayList<LigacaoAguaSituacao>();

			for(PrescricaoComandoLigacaoAgua prescricaoComandoLigacaoAgua : colecaoPrescricaoComandoLigacaoAgua){

				colecaoLigacaoAguaSituacao.add(prescricaoComandoLigacaoAgua.getLigacaoAguaSituacao());
			}

			if(!Util.isVazioOrNulo(colecaoLigacaoAguaSituacao)){

				sessao.setAttribute("colecaoLigacaoAguaSituacaoComando", colecaoLigacaoAguaSituacao);
			}
		}

		// Situa��o da Liga��o de Esgoto
		FiltroPrescricaoComandoLigacaoEsgoto filtroPrescricaoComandoLigacaoEsgoto = new FiltroPrescricaoComandoLigacaoEsgoto();
		filtroPrescricaoComandoLigacaoEsgoto.adicionarParametro(new ParametroSimples(
						FiltroPrescricaoComandoLigacaoEsgoto.ID_PRESCRICAO_COMANDO, prescricaoComando.getId()));
		filtroPrescricaoComandoLigacaoEsgoto
						.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComandoLigacaoEsgoto.LIGACAO_ESGOTO_SITUACAO);
		filtroPrescricaoComandoLigacaoEsgoto.setCampoOrderBy(FiltroPrescricaoComandoLigacaoEsgoto.ID_LIGACAO_ESGOTO_SITUACAO);

		Collection<PrescricaoComandoLigacaoEsgoto> colecaoPrescricaoComandoLigacaoEsgoto = fachada.pesquisar(
						filtroPrescricaoComandoLigacaoEsgoto, PrescricaoComandoLigacaoEsgoto.class.getName());

		if(!Util.isVazioOrNulo(colecaoPrescricaoComandoLigacaoEsgoto)){

			Collection<LigacaoEsgotoSituacao> colecaoLigacaoEsgotoSituacao = new ArrayList<LigacaoEsgotoSituacao>();
			for(PrescricaoComandoLigacaoEsgoto prescricaoComandoLigacaoEsgoto : colecaoPrescricaoComandoLigacaoEsgoto){

				colecaoLigacaoEsgotoSituacao.add(prescricaoComandoLigacaoEsgoto.getLigacaoEsgotoSituacao());
			}

			if(!Util.isVazioOrNulo(colecaoLigacaoEsgotoSituacao)){

				sessao.setAttribute("colecaoLigacaoAguaSituacaoComando", colecaoLigacaoEsgotoSituacao);
			}
		}

		// Situa��es de Cobran�a que Impedem a Prescri��o
		FiltroPrescricaoComandoSituacaoCobranca filtroPrescricaoComandoSituacaoCobranca = new FiltroPrescricaoComandoSituacaoCobranca();
		filtroPrescricaoComandoSituacaoCobranca.adicionarParametro(new ParametroSimples(
						FiltroPrescricaoComandoSituacaoCobranca.ID_PRESCRICAO_COMANDO, prescricaoComando.getId()));
		filtroPrescricaoComandoSituacaoCobranca
						.adicionarCaminhoParaCarregamentoEntidade(FiltroPrescricaoComandoSituacaoCobranca.SITUACAO_COBRANCA);
		filtroPrescricaoComandoSituacaoCobranca.setCampoOrderBy(FiltroPrescricaoComandoSituacaoCobranca.ID_SITUACAO_COBRANCA);

		Collection<PrescricaoComandoSituacaoCobranca> colecaoPrescricaoComandoSituacaoCobranca = fachada.pesquisar(
						filtroPrescricaoComandoSituacaoCobranca, PrescricaoComandoSituacaoCobranca.class.getName());

		if(!Util.isVazioOrNulo(colecaoPrescricaoComandoSituacaoCobranca)){

			Collection<CobrancaSituacao> colecaoCobrancaSituacao = new ArrayList<CobrancaSituacao>();

			for(PrescricaoComandoSituacaoCobranca prescricaoComandoSituacaoCobranca : colecaoPrescricaoComandoSituacaoCobranca){

				colecaoCobrancaSituacao.add(prescricaoComandoSituacaoCobranca.getCobrancaSituacao());
			}

			if(!Util.isVazioOrNulo(colecaoCobrancaSituacao)){

				sessao.setAttribute("colecaoCobrancaSituacaoComando", colecaoCobrancaSituacao);
			}
		}

		sessao.setAttribute("prescricaoComando", prescricaoComando);

		return retorno;
	}

	private String obterConteudoArquivoMatriculasFormatando(Blob blob){

		InputStream blobStream = null;
		BufferedReader bufferedReader = null;
		String linha = null;
		String retorno = "";

		try{
			blobStream = blob.getBinaryStream();
			bufferedReader = new BufferedReader(new InputStreamReader(blobStream));

			// loop que percorrer� todas as linhas do arquivo.txt
			while((linha = bufferedReader.readLine()) != null){

				if(!linha.equals("")){

					retorno += linha.trim() + ",";
				}
			}

			retorno = retorno.substring(0, retorno.length() - 1);
		}catch(IOException e){

			e.printStackTrace();
		}catch(SQLException e){

			e.printStackTrace();
		}finally{

			if(blobStream != null){
				try{

					blobStream.close();
				}catch(IOException e){

					e.printStackTrace();
				}
			}

			if(bufferedReader != null){

				try{

					bufferedReader.close();
				}catch(IOException e){

					e.printStackTrace();
				}
			}
		}

		return retorno;
	}

}
