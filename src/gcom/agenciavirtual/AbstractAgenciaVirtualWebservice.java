/**
 * 
 */

package gcom.agenciavirtual;

import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.util.Util;
import gcom.util.exception.GcomSystemException;
import gcom.util.parametrizacao.webservice.ParametrosAgenciaVirtual;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * @author Marlos Ribeiro
 */
public abstract class AbstractAgenciaVirtualWebservice
				extends ExibidorProcessamentoTarefaRelatorio {

	protected static final String CHAVE_JSON_STATUS = "status";

	protected static final String LABEL_CAMPO_CPF_CNPJ = "CPF / CNPJ";

	protected static final String LABEL_CAMPO_MATRICULA_DO_IMOVEL = "Matrícula do imóvel";

	protected static final String LABEL_CAMPO_CODIGO_DO_CLIENTE = "Código do Cliente";

	protected static final String LABEL_CAMPO_MES_ANO = "Mês Ano";

	protected static final String LABEL_CAMPO_GRUPO_FATURAMENTO = "Grupo Faturamento";

	protected static final String LABEL_CAMPO_AGUA_SITUACAO = "Situação Água";

	protected static final String LABEL_CAMPO_ESGOTO_SITUACAO = "Situação Esgoto";

	protected static final String LABEL_CAMPO_CONSUMO_AGUA = "Consumo Água";

	protected static final String LABEL_CAMPO_VOLUME_ESGOTO = "Volume Esgoto";

	protected static final String LABEL_CAMPO_TARIFA_CONSUMO = "Tarifa Consumo ";

	protected static final String LABEL_CAMPO_PERCENTUAL_ESGOTO = "Percentual Esgoto";

	private static final List collectionTypes = Arrays.asList(Collection.class, ArrayList.class, List.class, Set.class);

	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	protected static final String STATUS_TIPO_ERROR = "erro";

	protected static final String STATUS_TIPO_SUCESSO = "sucesso";

	protected static final String STATUS_TIPO_INFORMACAO = "informacao";

	protected static final String STATUS_TIPO_ALERTA = "alerta";

	private static final List wrapperTypes = Arrays.asList(String.class, Integer.class, Double.class, Short.class, Boolean.class);

	private JSONObject jsonBody;

	private JSONObject jsonHeader;

	private JSONObject jsonResponse;

	private Set noLoop = new HashSet();

	private static final String CHAVE_JSON_STATUS_MENSAGEM = "statusMensagem";

	private static final Logger LOGGER = Logger.getLogger(AbstractAgenciaVirtualWebservice.class);

	/**
	 * Adiciona uma {@link Collection} ao body do {@link JSONObject} de resposta.
	 * 
	 * @param key
	 *            - Chave de identificação no {@link JSONObject}.
	 * @param objList
	 *            - Instância de {@link Collection}.
	 * @throws JSONException
	 * @author Marlos Ribeiro
	 */
	protected void adicionarListaAoBody(String key, Collection objList) throws JSONException{

		if(objList != null){

			JSONArray jsonArray = new JSONArray();
			for(Object item : objList){
				if(wrapperTypes.contains(item.getClass()) || item.getClass().isPrimitive()){
					jsonArray.put(item);
				}else{
					jsonArray.put(converterBeanEmJSONObject(item));
				}
			}
			getJSONBody().put(key, jsonArray);
		}
	}

	/**
	 * Adiciona um {@link Object} ao body do {@link JSONObject} de resposta.
	 * 
	 * @param key
	 *            - Chave de identificação no {@link JSONObject}.
	 * @param object
	 *            - Instância de {@link Object}.
	 * @throws JSONException
	 * @author Marlos Ribeiro
	 */
	protected void adicionarObjetoAoBody(String key, Object object) throws JSONException{

		getJSONBody().put(key, converterBeanEmJSONObject(object));
	}

	/**
	 * Adiciona um valor primitivo ou seus respectivos wrapper class ao body do {@link JSONObject}
	 * de resposta.
	 * 
	 * @param key
	 *            - Chave de identificação no {@link JSONObject}.
	 * @param object
	 *            - valor.
	 * @throws JSONException
	 * @author Marlos Ribeiro
	 */
	protected void adicionarValorPrimitivoAoBody(String key, Object object) throws JSONException{

		if(wrapperTypes.contains(object.getClass()) || object.getClass().isPrimitive()){
			getJSONBody().put(key, object);
		}else{
			throw new UnsupportedOperationException(object.getClass() + " não é um tipo primitivo nem Wrapper");
		}
	}

	private JSONObject converterBeanEmJSONObject(Object obj){

		JSONObject novoObj = new JSONObject();
		try{
			if(obj != null){
				if(wrapperTypes.contains(obj.getClass()) || obj.getClass().isPrimitive()){
					throw new IllegalArgumentException("o parametro atribuido é um tipo primitivo ou wrapper.");
				}
				Method[] metodos = obj.getClass().getDeclaredMethods();
				noLoop.add(obj);
				for(Method metodo : metodos){
					// Filtra metodos que nao sejam GET
					String metodoNome = metodo.getName();
					if(!metodoNome.matches("^(get)([A-Z\\_])+(.)*") || metodo.getParameterTypes().length > 0){
						continue;
					}
					// System.out.println("Convertendo-->" + obj.getClass().getSimpleName() +
					// "-> Metodo -> " + metodoNome);
					char[] propNomeChar = metodoNome.substring(3).toCharArray();
					propNomeChar[0] = Character.toLowerCase(propNomeChar[0]);
					String fieldName = String.valueOf(propNomeChar);

					// Date Type of Field
					Class type = metodo.getReturnType();
					// String typeName = type.getName();
					Object fieldValue = null;
					try{
						fieldValue = metodo.invoke(obj);
					}catch(InvocationTargetException e){
						novoObj.put(fieldName, "--NÃO INICIALIZADO--");
						e.printStackTrace();
						continue;
					}

					if(fieldValue == null || !Hibernate.isInitialized(fieldValue)){
						novoObj.put(fieldName, "");
					}else if(wrapperTypes.contains(type) || type.isPrimitive()){
						novoObj.put(fieldName, fieldValue);
					}else if(collectionTypes.contains(type)){
						JSONArray jsonArray = new JSONArray();
						if(fieldValue != null){
							for(Object item : (Collection) fieldValue){
								if(wrapperTypes.contains(item.getClass()) || type.isPrimitive()){
									jsonArray.put(item);
								}else{
									jsonArray.put(converterBeanEmJSONObject(item));
								}
							}
						}
						novoObj.put(fieldName, jsonArray);
					}else if(Date.class.equals(type)){
						novoObj.put(fieldName, dateFormat.format(fieldValue));
					}else if(BigDecimal.class.equals(type)){
						DecimalFormat df = new DecimalFormat("#,##0.00");
						novoObj.put(fieldName, df.format(fieldValue));
					}else if(!noLoop.contains(fieldValue)){
						novoObj.put(fieldName, converterBeanEmJSONObject(fieldValue));
					}
				}
				noLoop.remove(obj);
			}
		}catch(IllegalAccessException e){
			throw new GcomSystemException("Acesso nao permitido ao metodo do bean.", e);
		}catch(JSONException e){
			throw new GcomSystemException("Erro construir o JSON.", e);
		}
		return novoObj;
	}

	/*
	 * (non-Javadoc)
	 * @see org.apache.struts.actions.DispatchAction#execute(org.apache.struts.action.ActionMapping,
	 * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		resetarServico();

		response.setContentType(getContentType());
		JSONObject jsonResponse = getJSONResponse();

		try{
			if(isServicoRestrito()){
				String cpfCnpjCliente = recuperarParametroString("cpfcnpj", LABEL_CAMPO_CPF_CNPJ,
								ParametrosAgenciaVirtual.isCpfCnpjObrigatorio(), true, request);
				String matriculaImovel = recuperarParametroStringObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);
				Fachada.getInstancia().validarPermissaoClienteImovel(cpfCnpjCliente, matriculaImovel);
			}
			processarRequisicao(mapping, form, request, response);
		}catch(Exception e){
			LOGGER.error("Ocorreu um erro ao processar requisição ao GSAN WS.", e);
			response.setContentType("application/json");
			StringWriter writer = null;
			String sts;
			String msg;
			try{
				writer = new StringWriter();
				msg = Util.obterStackTraceCompleta(e, writer);
				if(Util.isVazioOuBranco(msg)){
					sts = STATUS_TIPO_ERROR;
					msg = e.getMessage();
				}else{
					sts = STATUS_TIPO_ALERTA;
				}
			}finally{
				if(writer != null) try{
					writer.close();
				}catch(IOException e1){
					// ignore
				}
			}

			informarStatus(sts, msg);
			getJSONHeader().put("stackTrace", writer.toString());
			getJSONHeader().put("dataEnviada", dateFormat.format(GregorianCalendar.getInstance().getTime()));
			jsonResponse.put("header", getJSONHeader());

			PrintWriter outputStream = response.getWriter();
			outputStream.print(jsonResponse.toString());
		}
		if(getJSONHeader().has(CHAVE_JSON_STATUS)){
			LOGGER.info("GSAN WS:[RESPONSE->" + Util.recuperarIpCliente(request) + "]> Status(" + getJSONHeader().get(CHAVE_JSON_STATUS)
							+ "): " + getJSONHeader().get(CHAVE_JSON_STATUS_MENSAGEM));
		}else{
			LOGGER.info("GSAN WS:[RESPONSE->" + Util.recuperarIpCliente(request) + "]> Resposta em binário.");
		}
		return null;
	}

	private void resetarServico(){

		jsonBody = null;
		jsonHeader = null;
		jsonResponse = null;
	}

	/**
	 * Tipo de retorno do Serviço
	 * 
	 * @return application/json Por padrão.
	 */
	protected String getContentType(){

		return "application/json";
	}

	protected final JSONObject getJSONBody(){

		if(jsonBody == null){
			jsonBody = new JSONObject();
		}
		return jsonBody;
	}

	protected final JSONObject getJSONHeader(){

		if(jsonHeader == null){
			jsonHeader = new JSONObject();
		}
		return jsonHeader;
	}

	protected final JSONObject getJSONResponse(){

		if(jsonResponse == null){
			jsonResponse = new JSONObject();
		}
		return jsonResponse;
	}

	/**
	 * Adciona uma mensagem no HEAD do {@link JSONObject} de resposta.
	 * 
	 * @param tipo
	 *            - Tipo da mensagem
	 * @param msg
	 *            - mensagem.
	 * @throws JSONException
	 * @author Marlos Ribeiro FIXME Está sendo subrescrito o método pois o serviço de IncluirRA estava mascarando a mensagem de erro/alerta. 
	 */
	protected void informarStatus(String tipo, String msg){

		try{
			getJSONHeader().put(CHAVE_JSON_STATUS, tipo);
			getJSONHeader().put(CHAVE_JSON_STATUS_MENSAGEM, msg);
		}catch(JSONException e){
			throw new RuntimeException("Erro ao incluir mensagem de status.", e);
		}
	}

	// /**
	// * Adciona uma mensagem de detalhe no HEAD do {@link JSONObject} de resposta.
	// *
	// * @param tipo
	// * - Tipo da mensagem
	// * @param msg
	// * - mensagem.
	// * @throws JSONException
	// * @author Marlos Ribeiro
	// */
	// protected void informarDetalhes(String tipo, String msg){
	//
	// try{
	// getJSONHeader().put("tipoDetalhe", tipo);
	// getJSONHeader().put("detalhe", msg);
	// }catch(JSONException e){
	// throw new RuntimeException("Erro ao incluir mensagem de status.", e);
	// }
	// }

	/**
	 * Indicador de acesso do serviço.
	 * 
	 * @return <code>true</code> se o serviço é restrito ao usuario e imovel.
	 */
	protected boolean isServicoRestrito(){

		return true;
	}

	/**
	 * Processa a requisicao ao serviço.
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	protected abstract void processarRequisicao(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception;

	protected String recuperarParametroCpfCnpj(boolean isObrigatorio, HttpServletRequest request) throws NegocioException{

		String cpfcnpj = recuperarParametroString("cpfcnpj", "CPF / CNPJ", isObrigatorio, true, request);
		if(!Util.isVazioOuBranco(cpfcnpj)){
			if(!cpfcnpj.matches("[0-9]*")){
				throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
								"atencao.erro.campo_deve_conter_apenas_numeros", "CPF / CNPJ"));
			}else if(!(cpfcnpj.length() == 11 || cpfcnpj.length() == 14)){
				throw new NegocioException(MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE, "atencao.campo.invalido", "CPF / CNPJ"));
			}
		}
		return cpfcnpj;

	}

	protected String recuperarParametroString(String nomeParametro, String labelCampo, Boolean isObrigatorio, boolean isMasculino,
					HttpServletRequest request) throws NegocioException{

		String parameter = request.getParameter(nomeParametro);
		LOGGER.info("GSAN WS:[" + Util.recuperarIpCliente(request) + "]> getParametro: [" + nomeParametro + "]"
						+ (isObrigatorio ? "*" : " ") + "=>'" + parameter + "'");

		if(isObrigatorio && Util.isVazioOuBranco(parameter)){
			String msgKey = Boolean.TRUE.equals(isMasculino) ? "atencao.erro.campo_obrigatorio_masculino"
							: "atencao.erro.campo_obrigatorio_feminino";
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, msgKey, new String[] {labelCampo});
		}
		return parameter;
	}

	protected Collection<Integer> recuperarParametroColecaoInteiro(String nomeParametro, String labelCampo, Boolean isObrigatorio,
					boolean isMasculino,
					HttpServletRequest request) throws NegocioException{

		Collection<Integer> retorno = new ArrayList<Integer>();
		String parameter = request.getParameter(nomeParametro);
		LOGGER.info("GSAN WS:[" + Util.recuperarIpCliente(request) + "]> getParametro: [" + nomeParametro + "]"
						+ (isObrigatorio ? "*" : " ") + "=>'" + parameter + "'");

		if(isObrigatorio && Util.isVazioOuBranco(parameter)){
			String msgKey = Boolean.TRUE.equals(isMasculino) ? "atencao.erro.campo_obrigatorio_masculino"
							: "atencao.erro.campo_obrigatorio_feminino";
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, msgKey, new String[] {labelCampo});
		}

		if(!Util.isVazioOuBranco(parameter)){
			String[] itens = parameter.split(",");
			for(String string : itens){
				if(!Util.isVazioOuBranco(string)){
					validarInteiro(labelCampo, string);
					retorno.add(Integer.valueOf(string));
				}
			}
		}
		return retorno;
	}

	protected Collection<String> recuperarParametroColecaoString(String nomeParametro, String labelCampo, Boolean isObrigatorio,
					boolean isMasculino, HttpServletRequest request) throws NegocioException{

		Collection<String> retorno = new ArrayList<String>();
		String parameter = request.getParameter(nomeParametro);

		if(isObrigatorio && Util.isVazioOuBranco(parameter)){
			String msgKey = Boolean.TRUE.equals(isMasculino) ? "atencao.erro.campo_obrigatorio_masculino"
							: "atencao.erro.campo_obrigatorio_feminino";
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, msgKey, new String[] {labelCampo});
		}

		if(!Util.isVazioOuBranco(parameter)){
			String[] itens = parameter.split(",");
			for(String string : itens){
				if(!Util.isVazioOuBranco(string)){
					validarInteiro(labelCampo, string);
					retorno.add(string);
				}
			}
		}
		return retorno;
	}

	private void validarInteiro(String labelCampo, String string) throws NegocioException{

		if(!string.matches("[0-9]*")){
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.erro.campo_deve_conter_apenas_numeros",
							new String[] {labelCampo});
		}
	}

	protected Integer recuperarParametroInteiroObrigatorio(String nomeParametro, String labelCampo, boolean isMasculino,
					HttpServletRequest request) throws NegocioException{

		return recuperarParametroInteiro(nomeParametro, labelCampo, true, isMasculino, request);
	}

	protected Integer recuperarParametroInteiro(String nomeParametro, String labelCampo, boolean isObrigatorio, Boolean isMasculino,
					HttpServletRequest request) throws NegocioException{

		String valor = recuperarParametroString(nomeParametro, labelCampo, isObrigatorio, isMasculino, request);
		if(!isObrigatorio && Util.isVazioOuBranco(valor)){
			return null;
		}
		validarInteiro(labelCampo, valor);

		return Integer.valueOf(valor);
	}

	protected Short recuperarParametroShort(String nomeParametro, String labelCampo, boolean isObrigatorio, Boolean isMasculino,
					HttpServletRequest request) throws NegocioException{

		String valor = recuperarParametroString(nomeParametro, labelCampo, isObrigatorio, isMasculino, request);
		if(!isObrigatorio && Util.isVazioOuBranco(valor)){
			return null;
		}
		validarInteiro(labelCampo, valor);

		return Short.valueOf(valor);
	}

	protected String recuperarParametroStringObrigatorio(String nomeParametro, String labelCampo, boolean isMasculino,
					HttpServletRequest request) throws NegocioException{

		return recuperarParametroString(nomeParametro, labelCampo, true, isMasculino, request);
	}

	protected BigDecimal recuperarParametroBigDecimal(String nomeParametro, String labelCampo, boolean isObrigatorio, boolean isMasculino,
					HttpServletRequest request) throws NegocioException{

		String parameter = request.getParameter(nomeParametro);
		BigDecimal retorno = null;
		LOGGER.info("GSAN WS:[" + Util.recuperarIpCliente(request) + "]> getParametro: [" + nomeParametro + "]"
						+ (isObrigatorio ? "*" : " ") + "=>'" + parameter + "'");

		if(isObrigatorio && Util.isVazioOuBranco(parameter)){
			String msgKey = Boolean.TRUE.equals(isMasculino) ? "atencao.erro.campo_obrigatorio_masculino"
							: "atencao.erro.campo_obrigatorio_feminino";
			throw new NegocioException(Constantes.RESOURCE_BUNDLE, msgKey, new String[] {labelCampo});
		}

		if(parameter != null){
			retorno = new BigDecimal(parameter);
		}

		return retorno;
	}
}
