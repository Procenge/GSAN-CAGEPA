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

package gcom.gui.cadastro.sistemaparametro;

import gcom.cadastro.geografico.Municipio;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.FiltroTabelaAuxiliarAbreviada;

import java.io.*;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * [UC0534] INSERIR FERIADO
 * 
 * @author K�ssia Albuquerque
 * @date 12/01/2007
 */

public class ExibirInserirFeriadoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirFeriado");

		Fachada fachada = Fachada.getInstancia();

		InserirFeriadoActionForm form = (InserirFeriadoActionForm) actionForm;

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("importarArquivoComFeriados"))){

			this.validarArquivoUpload(form.getArquivoFeriadosImportar());
			File arquivoFeriados = this.formFileToFile(form.getArquivoFeriadosImportar(), httpServletRequest);

			if(arquivoFeriados != null){

				File arquivoLogProcessamento = fachada.importarFeriado(arquivoFeriados, this.getUsuarioLogado(httpServletRequest));
				this.getSessao(httpServletRequest).setAttribute("arquivoLogProcessamento", arquivoLogProcessamento);

				retorno = actionMapping.findForward("telaSucesso");

				// Montar P�giuna de Sucesso
				montarPaginaSucesso(httpServletRequest, "Arquivo de Feriados processado com sucesso.", "Inserir Outro(s) Feriado(s)",
								"exibirInserirFeriadoAction.do?menu=sim", "exibirInserirFeriadoAction.do?download=true",
								"Clique Aqui para baixar o Log do Processamento do Arquivo");
			}
		}

		if(form.getArquivoFeriadosImportar() != null && !form.getArquivoFeriadosImportar().getFileName().equals("")){

			try{
				File arquivoLogProcessamento = (File) this.getSessao(httpServletRequest).getAttribute("arquivoLogProcessamento");

				if(httpServletRequest.getParameter("download") != null && httpServletRequest.getParameter("download").equals("true")
								&& arquivoLogProcessamento != null){

					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					FileInputStream in = new FileInputStream(arquivoLogProcessamento);

					int b;

					while((b = in.read()) > -1){

						byteArrayOutputStream.write(b);
					}

					httpServletResponse.addHeader("Content-Disposition", "attachment; filename=" + arquivoLogProcessamento.getName());
					String mimeType = "application/txt";
					httpServletResponse.setContentType(mimeType);
					OutputStream out = httpServletResponse.getOutputStream();
					out.write(byteArrayOutputStream.toByteArray());
					out.flush();
					out.close();
					retorno = null;
					this.getSessao(httpServletRequest).removeAttribute("arquivoLogProcessamento");
				}

			}catch(FileNotFoundException e){

				levantarExcecaoUrlSetada(new ActionServletException("", httpServletRequest.getRequestURI(), ""));
			}catch(IOException e){

				levantarExcecaoUrlSetada(new ActionServletException("", httpServletRequest.getRequestURI(), ""));
			}
		}

		if(httpServletRequest.getParameter("menu") != null){
			form.setIndicadorTipoFeriado("2");
		}

		// -- [UC0534] INSERIR FERIADO
		// -- [FS0002] Verificar Exist�ncia do Municipio --

		if((form.getIdMunicipio() != null && !form.getIdMunicipio().equals(""))){

			FiltroTabelaAuxiliarAbreviada filtroTabelaAuxiliarAbreviada = new FiltroTabelaAuxiliarAbreviada();

			filtroTabelaAuxiliarAbreviada.adicionarParametro(new ParametroSimples(FiltroTabelaAuxiliarAbreviada.ID, form.getIdMunicipio()));

			Collection colecaoMunicipio = fachada.pesquisar(filtroTabelaAuxiliarAbreviada, Municipio.class.getName());

			if(colecaoMunicipio != null && !colecaoMunicipio.isEmpty()){
				Municipio municipio = (Municipio) colecaoMunicipio.iterator().next();
				form.setDescricaoMunicipio(municipio.getNome());
			}else{
				httpServletRequest.setAttribute("municipioEncontrado", "exception");
				form.setIdMunicipio("");
				form.setDescricaoMunicipio("MUNICIPIO INEXISTENTE");
			}

		}

		return retorno;
	}

	private void validarArquivoUpload(FormFile arquivo){

		if(arquivo.getFileSize() == 0){

			levantarExcecaoUrlSetada(new ActionServletException("atencao.arquivo_sem_dados", arquivo.getFileName()));
		}
	}

	private void levantarExcecaoUrlSetada(ActionServletException actionServletException){

		actionServletException.setUrlBotaoVoltar("/gsan/exibirInserirFeriadoAction.do");
		throw actionServletException;
	}

	private File formFileToFile(FormFile formFile, HttpServletRequest httpServletRequest){

		File file = null;
		try{

			file = new File(formFile.getFileName());
		}catch(Exception e){

			e.printStackTrace();
		}

		try{

			InputStreamReader reader = new InputStreamReader(formFile.getInputStream());
			BufferedReader buffer = new BufferedReader(reader);
			String linha = null;
			FileOutputStream fout = new FileOutputStream(file);
			PrintWriter pw = new PrintWriter(fout);

			while((linha = buffer.readLine()) != null){

				pw.println(linha);
			}

			buffer.close();
			reader.close();
			pw.flush();
			pw.close();

		}catch(FileNotFoundException e){

			levantarExcecaoUrlSetada(new ActionServletException("erro.leitura_arquivo"));

		}catch(IOException e){

			levantarExcecaoUrlSetada(new ActionServletException("erro.leitura_arquivo"));

		}
		return file;

	}
}
