/**
 * 
 */

package gcom.agenciavirtual.atendimento;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.fachada.Fachada;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.json.JSONException;

/**
 * @author mgrb
 */
public class InserirRegistroAtendimentoWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice#preencherJSONBody(org.apache.struts
	 * .action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer matricula = recuperarParametroInteiro("matricula", "Matrícula", false, false, request);
		String cpfcnpj = recuperarParametroCpfCnpj(false, request);
		String pontoReferencia = recuperarParametroString("pontoReferencia", "Ponto de Referência", false, false, request);
		String nomeSolicitante = recuperarParametroString("nomeSolicitante", "Nome do Solicitante", false, true, request);
		String foneSolicitante = recuperarParametroString("foneSolicitante", "Telofone do Solicitante", false, true, request);
		String emailSolicitante = recuperarParametroString("emailSolicitante", "E-mail do Solicitante", false, true, request);

		Integer idSolicitacaoTipoEspecificacao = recuperarParametroInteiroObrigatorio("idSolicitacaoTipoEspecificacao",
						"Tipo Especificação da Solicitação", true, request);
		Integer idMunicio = recuperarParametroInteiroObrigatorio("idMunicipio", "Município", true, request);
		Integer idBairro = recuperarParametroInteiroObrigatorio("idBairro", "Bairro", true, request);
		Integer idLogradouro = recuperarParametroInteiroObrigatorio("idLogradouro", "Logradouro", true, request);
		Integer numero = recuperarParametroInteiroObrigatorio("numero", "Número", true, request);
		Integer pavimentoTipoRua = recuperarParametroInteiro("tipo_pavimento", "Tipo Pavimento Rua", false, true, request);
		String descricao = recuperarParametroStringObrigatorio("descricao", "Descrição", false, request);

		Integer numeroRA = null;
		String mensagemErro = null;
		try{

			numeroRA = Fachada.getInstancia().inserirRegistroAtendimento(matricula, cpfcnpj, pontoReferencia, nomeSolicitante,
							foneSolicitante, emailSolicitante, idSolicitacaoTipoEspecificacao, idMunicio, idBairro, idLogradouro, numero,
							descricao, pavimentoTipoRua);
		}catch(Exception e){
			e.printStackTrace();
			mensagemErro = e.getCause().getMessage();
		}

		if(numeroRA != null){
			adicionarValorPrimitivoAoBody("IdRegistroAtendimento", numeroRA);
		}

		if(mensagemErro != null){
			informarStatus(STATUS_TIPO_ALERTA, mensagemErro);
		}

	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}

	@Override
	protected final void informarStatus(String tipo, String msg){

		try{
			if(getJSONHeader().length() == 0 || getJSONHeader().get("status") == null){
				getJSONHeader().put("status", tipo);
				getJSONHeader().put("statusMensagem", msg);
			}
		}catch(JSONException e){
			throw new RuntimeException("Erro ao incluir mensagem de status.", e);
		}
	}

}
