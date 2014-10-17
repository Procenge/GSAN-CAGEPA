
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.util.FiltroRecursosExternos;
import gcom.util.exception.GcomSystemException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;

public class BaixaOrdemServicoCobrancaAction
				extends GcomAction {

	private static final String EXTENSAO_ARQUIVO_PERMITIDA = "CSV";

	public ActionForward exibirTelaBaixaOrdemServicoCobranca(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		return actionMapping.findForward("forwardTelaBaixaOrdemServicoCobranca");
	}

	public ActionForward baixaOrdemServicoCobranca(ActionMapping actionMapping, ActionForm actionForm,
					HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse){

		DynaActionForm form = (DynaActionForm) actionForm;
		FormFile arquivo = (FormFile) form.get("arquivo");

		this.validarArquivoUpload(arquivo);

		String arquivoLocalPath = salvarArquivoEmDisco(arquivo);

		Fachada.getInstancia().efetuarBaixaOrdensServicoCobranca(arquivoLocalPath, this.getUsuarioLogado(httpServletRequest));

		this.montarPaginaSucesso(httpServletRequest, "Baixa de Ordens de Serviço enviada para Processamento", "Voltar",
						"exibirTelaBaixaOrdemServicoCobranca.do?action=exibirTelaBaixaOrdemServicoCobranca");

		return actionMapping.findForward("telaSucesso");
	}

	private String salvarArquivoEmDisco(FormFile arquivo){

		try{
			// CRIANDO ARQUIVO DESTINO NO DISCO
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String arquivoDestinoAbsolutPath = FiltroRecursosExternos.DIR_RECURSOS_EXTERNOS + "ORDEM_SERVICO_"
							+ dateFormat.format(Calendar.getInstance().getTime()) + ".csv";
			File arqDestino = new File(arquivoDestinoAbsolutPath);
			FileOutputStream arqDestinoOutPutStream = new FileOutputStream(arqDestino.getAbsolutePath());
			arqDestinoOutPutStream.write(arquivo.getFileData());

			// FECHANDO ARQUIVO DE DESTINO
			arqDestinoOutPutStream.close();
			return arquivoDestinoAbsolutPath;
		}catch(FileNotFoundException e){
			throw new GcomSystemException(e);
		}catch(IOException e){
			throw new GcomSystemException(e);
		}
	}

	private void validarArquivoUpload(FormFile arquivo){

		String nomeArquivo = arquivo.getFileName();
		String extensaoArquivo = nomeArquivo.substring(nomeArquivo.lastIndexOf(".") + 1).toUpperCase();

		if((!extensaoArquivo.equals(EXTENSAO_ARQUIVO_PERMITIDA))){
			throw new ActionServletException("atencao.arquivo_extensao_invalida");
		}

		if(arquivo.getFileSize() == 0){
			throw new ActionServletException("atencao.arquivo_vazio");
		}

	}

	private File formFileToFile(FormFile formFile, HttpServletRequest httpServletRequest){

		File file = null;
		try{
			file = new File(formFile.getFileName());
		}catch(Exception e){
			e.printStackTrace();
		}

		try{

			DiskFileUpload upload = new DiskFileUpload();

			List items = upload.parseRequest(httpServletRequest);
			FileItem item = null;

			Iterator iter = items.iterator();
			while(iter.hasNext()){
				item = (FileItem) iter.next();
				if(!item.isFormField()){
					InputStreamReader reader = new InputStreamReader(item.getInputStream());
					BufferedReader buffer = new BufferedReader(reader);

					FileOutputStream fout = new FileOutputStream(file);
					PrintWriter pw = new PrintWriter(fout);

					boolean eof = false;
					while(!eof){
						String linhaLida = buffer.readLine();

						if(linhaLida != null){
							pw.println(linhaLida);
						}else{
							break;
						}
					}
					buffer.close();
					reader.close();
					item.getInputStream().close();
					pw.flush();
					pw.close();
				}
			}

		}catch(FileNotFoundException e){

			throw new ActionServletException("erro.leitura_arquivo");

		}catch(IOException e){

			throw new ActionServletException("erro.leitura_arquivo");

		}catch(FileUploadException e){

			throw new ActionServletException("erro.leitura_arquivo");
		}

		return file;

	}

}
