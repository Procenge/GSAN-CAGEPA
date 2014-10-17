
package br.com.procenge.geradorrelatorio.impl;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.util.ConstantesConfig;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

import br.com.procenge.geradorrelatorio.api.ControladorRelatorio;
import br.com.procenge.geradorrelatorio.api.Relatorio;
import br.com.procenge.geradorrelatorio.api.RelatorioDAO;
import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

import com.crystaldecisions.reports.sdk.ParameterFieldController;
import com.crystaldecisions.reports.sdk.ReportClientDocument;
import com.crystaldecisions.sdk.occa.report.data.Field;
import com.crystaldecisions.sdk.occa.report.data.Fields;
import com.crystaldecisions.sdk.occa.report.exportoptions.ReportExportFormat;
import com.crystaldecisions.sdk.occa.report.lib.ReportSDKException;

public class GeradorRelatorioCrystalImpl
				implements ControladorRelatorio {

	private static final String P_CAMINHO_IMAGEM = "P_CAMINHO_IMAGEM";
	private static final String P_NM_ESTADO = "P_NM_ESTADO";

	
	
	private RelatorioDAO relatorioDAO;

	public RelatorioDAO getRelatorioDAO(){

		return relatorioDAO;
	}

	public void setRelatorioDAO(RelatorioDAO relatorioDAO){

		this.relatorioDAO = relatorioDAO;
	}

	public ByteArrayInputStream gerarRelatorio(Relatorio relatorio, Map<String, Object> parametros, int formato){

		ByteArrayInputStream byteArrayInputStream = null;
		ReportClientDocument reportClientDoc = null;

		try{
			reportClientDoc = new ReportClientDocument();
			reportClientDoc.open(relatorio.getArquivo(), 0);

			if(parametros != null && !parametros.isEmpty()){
				ParameterFieldController paramController = reportClientDoc.getDataDefController().getParameterFieldController();
				Fields fields = reportClientDoc.getDataDefController().getDataDefinition().getParameterFields();
				for(int i = 0; i < fields.size(); i++){
					Field field = (Field) fields.getField(i);
					paramController.setCurrentValue("", field.getName(), parametros.get(field.getName()));
				}
			}

			ReportExportFormat reportExportFormat = null;
			switch(formato){
				case FORMATO_PDF:
					reportExportFormat = ReportExportFormat.PDF;
					break;
				case FORMATO_PLANILHA:
					reportExportFormat = ReportExportFormat.MSExcel;
					break;
				case FORMATO_DOCUMENTO:
					reportExportFormat = ReportExportFormat.MSWord;
					break;
				default:
					reportExportFormat = ReportExportFormat.PDF;
					break;
			}

			byteArrayInputStream = (ByteArrayInputStream) reportClientDoc.getPrintOutputController().export(reportExportFormat);

			reportClientDoc.close();

		}catch(ReportSDKException e){
			throw new RuntimeException(e.getMessage(), e);
		}

		return byteArrayInputStream;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.com.procenge.geradorrelatorio.api.ControladorRelatorio#obterRelatorioPorNome(java.lang
	 * .String)
	 */
	public Relatorio obterRelatorioPorNome(String nome){

		return this.relatorioDAO.obterRelatorioPorNome(nome);
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * br.com.procenge.geradorrelatorio.api.ControladorRelatorio#validarParametros(br.com.procenge
	 * .geradorrelatorio.api.Relatorio, java.util.Map)
	 */
	public Map<String, String> validarParametros(Relatorio relatorio, Map<String, Object> parametros){

		Map<String, String> erros = null;
		String classeValidacaoParametros = relatorio.getClasseValidacaoParametros();
		if(classeValidacaoParametros != null && classeValidacaoParametros.length() > 0){
			try{
				Class clazz = Class.forName(classeValidacaoParametros);
				ValidadorParametros validador = (ValidadorParametros) clazz.newInstance();
				erros = validador.validar(parametros);
			}catch(ClassNotFoundException e){
				throw new RuntimeException(e.getMessage(), e);
			}catch(InstantiationException e){
				throw new RuntimeException(e.getMessage(), e);
			}catch(IllegalAccessException e){
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		return erros;
	}

	public Map<String, Object> converterParametros(Relatorio relatorio, Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = null;
		String classeValidacaoParametros = relatorio.getClasseValidacaoParametros();
		if(classeValidacaoParametros != null && classeValidacaoParametros.length() > 0){
			try{
				Class clazz = Class.forName(classeValidacaoParametros);
				ValidadorParametros validador = (ValidadorParametros) clazz.newInstance();
				parametrosConvertidos = validador.converter(parametros);

				if(parametrosConvertidos == null){
					parametrosConvertidos = new HashMap<String, Object>();
				}

				adicionarCaminhoImagem(parametrosConvertidos);

			}catch(ClassNotFoundException e){
				throw new RuntimeException(e.getMessage(), e);
			}catch(InstantiationException e){
				throw new RuntimeException(e.getMessage(), e);
			}catch(IllegalAccessException e){
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		return parametrosConvertidos;
	}

	/**
	 * Adiciona o caminho da imagem como um parâmetro do Relatório
	 * Todos os relatórios utilizam este parâmetro para tornar a logomarca dinâmica, de acordo com a
	 * empresa em operação
	 * 
	 * @author Luciano Galvao
	 * @date 04/08/2012
	 * @param parametrosConvertidos
	 */
	private void adicionarCaminhoImagem(Map<String, Object> parametrosConvertidos){

		SistemaParametro sistemaParametro;
		try{
			sistemaParametro = ServiceLocator.getInstancia().getControladorUtil().pesquisarParametrosDoSistema();

		}catch(ControladorException e){
			throw new RuntimeException(e.getMessage(), e);
		}

		String caminhoImagem = ConstantesConfig.getDiretorioDeployAplicacao()// DEPLOY BASE
						.concat("gcom.ear/gcom.war"// WAR BASE
						.concat(sistemaParametro.getImagemRelatorio().substring(1).replaceAll(".gif", ".jpg"))); // IMAGE
		
		System.out.println("XXX  Caminho da Imagem " + caminhoImagem);



		parametrosConvertidos.put(P_CAMINHO_IMAGEM, caminhoImagem);

		parametrosConvertidos.put(P_NM_ESTADO, sistemaParametro.getNomeEstado());

	}
}
