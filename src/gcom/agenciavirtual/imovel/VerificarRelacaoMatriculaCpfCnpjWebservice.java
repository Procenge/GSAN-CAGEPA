
package gcom.agenciavirtual.imovel;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.atendimentopublico.ligacaoagua.LigacaoAgua;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import br.com.procenge.util.Constantes;
import br.com.procenge.util.MensagemUtil;

/**
 * Verifica se a Matricule de imovel tem relação com o Cliente representado pelo CNPJ/CPF.
 * <b>Este serviço não é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>matricula [OBRIGATORIO]</li>
 * <li>cpfcnpj [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/verificarRelacaoMatriculaCpfCnpjWebservice.do
 * 
 * @author Marlos Ribeiro
 */
public class VerificarRelacaoMatriculaCpfCnpjWebservice
				extends AbstractAgenciaVirtualJSONWebservice {

	/*
	 * (non-Javadoc)
	 * @see
	 * gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice#preencherJSONBody(org.apache.struts
	 * .action.ActionForm, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Integer imovelId = recuperarParametroInteiroObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);
		ImovelJSONHelper imovelJSONHelper = criarImovelJSONHelper(imovelId);
		if(imovelJSONHelper == null){

			Object[] argumentosMensagemArray = new Object[1];
			argumentosMensagemArray[0] = imovelId;

			informarStatus(STATUS_TIPO_ALERTA, MensagemUtil.obterMensagem(Constantes.RESOURCE_BUNDLE,
							"atencao.pesquisa.imovel.inexistente", argumentosMensagemArray));
		}else{
			adicionarObjetoAoBody("imovel", imovelJSONHelper);
		}
	}

	private ImovelJSONHelper criarImovelJSONHelper(Integer imovelId) throws ControladorException{

		ImovelJSONHelper imovelJSONHelper = null;
		Imovel imovel = ServiceLocator.getInstancia().getControladorImovel().consultarImovel(imovelId);
		if(imovel != null){
			imovelJSONHelper = new ImovelJSONHelper();
			LigacaoAgua ligacaoAgua = ServiceLocator.getInstancia().getControladorLigacaoAgua().pesquisarLigacaoAgua(imovelId);
			String cliente = ServiceLocator.getInstancia().getControladorImovel().consultarClienteUsuarioImovel(imovelId);
			SistemaParametro SistemaParametro = ServiceLocator.getInstancia().getControladorUtil().pesquisarParametrosDoSistema();
			int[] consumoMedio = ServiceLocator.getInstancia().getControladorMicromedicao()
							.obterConsumoMedioImovel(imovel, SistemaParametro);
			imovelJSONHelper.setCliente(cliente);
			imovelJSONHelper.setConsumoMedio(consumoMedio[0]);
			imovelJSONHelper.setDataUltimaAtualizacao(imovel.getUltimaAlteracao());
			imovelJSONHelper.setEndereco(imovel.getEnderecoFormatado());
			if(ligacaoAgua != null && ligacaoAgua.getHidrometroInstalacaoHistorico() != null
							&& ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro() != null){
				imovelJSONHelper.setHidrometro(ligacaoAgua.getHidrometroInstalacaoHistorico().getHidrometro().getNumero());
			}
			imovelJSONHelper.setInscricao(imovel.getInscricaoFormatada());
			imovelJSONHelper.setMatricula(imovel.getId());
			imovelJSONHelper.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getDescricao());
			imovelJSONHelper.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getDescricao());

			boolean isBairroSetado = imovel.getLogradouroBairro() != null && imovel.getLogradouroBairro().getBairro() != null;
			boolean isMunicipioSetado = isBairroSetado && imovel.getLogradouroBairro().getBairro().getMunicipio() != null;
			boolean isLogradouroSetado = imovel.getLogradouroCep() != null && imovel.getLogradouroCep().getLogradouro() != null;

			imovelJSONHelper.setIdBairro(isBairroSetado ? imovel.getLogradouroBairro().getBairro().getId() : null);
			imovelJSONHelper.setIdMunicipio(isMunicipioSetado ? imovel.getLogradouroBairro().getBairro().getMunicipio().getId() : null);
			imovelJSONHelper.setIdLogradouro(isLogradouroSetado ? imovel.getLogradouroCep().getLogradouro().getId() : null);
			imovelJSONHelper.setNumeroImovel(imovel.getNumeroImovel());
		}
		return imovelJSONHelper;
	}

}
