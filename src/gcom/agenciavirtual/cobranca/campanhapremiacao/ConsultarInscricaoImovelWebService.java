
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.campanhapremiacao.Campanha;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.CampanhaCadastroFone;
import gcom.cobranca.campanhapremiacao.FiltroCampanha;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.FachadaException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

/**
 * Retorna: Se o im�vel j� estiver inscrito retornar� os dados da inscri��o
 * em 3 objetos (dsEndereco, CampanhaCadastroJSONHelper e
 * Collection<CampanhaCadastroFoneJSONHelper>),
 * caso contr�rio retornar� (dsEndereco e indicadorResidencial)
 * <b>Este servi�o � P�BLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/consultarInscricaoImovelWebService.do
 * 
 * @author Hiroshi Goncalves
 */
public class ConsultarInscricaoImovelWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		String idImovel = recuperarParametroStringObrigatorio("idImovel", "Im�vel", true, request);

		Fachada fachada = Fachada.getInstancia();
		
		// O sistema obt�m os dados da campanha de premia��o mais recente da empresa
		FiltroCampanha filtroCampanha = new FiltroCampanha(FiltroCampanha.ULTIMA_ALTERACAO + " desc");

		Collection colecaoCampanha = fachada.pesquisar(filtroCampanha, Campanha.class.getName(), Campanha.class.getSimpleName());

		Campanha campanha = null;

		if(!colecaoCampanha.isEmpty()){
			campanha = (Campanha) Util.retonarObjetoDeColecao(colecaoCampanha);
		}

		try{

			// Endere�o do Im�vel
			String dsEndereco = "";

			try{
				dsEndereco = fachada.pesquisarEnderecoFormatado(Integer.parseInt(idImovel));
			}catch(ControladorException e){
				// Nunca vai ocorrer porque a pr�pria classe Fachada trata esta Exce��o.
			}
			
			adicionarValorPrimitivoAoBody("dsEndereco", dsEndereco);

			ArrayList alRetornoCampanhaCadastro = (ArrayList) fachada.verificarInscricaoImovelCampanha(idImovel, campanha);

			if(alRetornoCampanhaCadastro != null){
				// Extrai os dados do retorno
				CampanhaCadastro campanhaCadastro = (CampanhaCadastro) alRetornoCampanhaCadastro.get(0);
				Collection<CampanhaCadastroFone> colCampanhaCadastroFone = (Collection) alRetornoCampanhaCadastro.get(1);
				Short indicadorExibirBotaoEmitirComprovante = (Short) alRetornoCampanhaCadastro.get(2);
				
				// Preenche o CampanhaCadastroJSONHelper
				CampanhaCadastroJSONHelper campanhaCadastroJSONHelper = this.preencherCampanhaCadastroJSONHelper(fachada, campanhaCadastro,
								indicadorExibirBotaoEmitirComprovante);
				
				// Preenche Collection<CampanhaCadastroFoneJSONHelper>
				Collection<CampanhaCadastroFoneJSONHelper> colFonesJSONHelper = new ArrayList<CampanhaCadastroFoneJSONHelper>();

				for(CampanhaCadastroFone objeto : colCampanhaCadastroFone){
					CampanhaCadastroFoneJSONHelper helper = new CampanhaCadastroFoneJSONHelper();

					helper.setIdTipoTelefone(objeto.getFoneTipo().getId().toString());
					helper.setDdd(objeto.getCodigoDDD());
					helper.setTelefone(objeto.getNumeroFone());

					if(objeto.getNumeroFoneRamal() != null){
						helper.setRamal(objeto.getNumeroFoneRamal());
					}

					colFonesJSONHelper.add(helper);
				}

				adicionarObjetoAoBody("dadosCampanhaCadastro", campanhaCadastroJSONHelper);
				adicionarListaAoBody("colTelefone", colFonesJSONHelper);
			}else{

				Short indicadorResidencial = null;
				
				// Caso o im�vel seja da categoria residencial e tenha at� 2 (duas) economias
				// (IMOV_QTECONOMIA menor ou igual a 2 (dois)):
				// Ativa-se o indicador de residencial
				Categoria categoriaPrincipalImovel = fachada.obterPrincipalCategoriaImovel(Integer.valueOf(idImovel));
				int qtEconomiasImovel = fachada.obterQuantidadeEconomias(new Imovel(Integer.valueOf(idImovel)));

				if(categoriaPrincipalImovel.getId().equals(Categoria.RESIDENCIAL) && qtEconomiasImovel <= 2){
					indicadorResidencial = ConstantesSistema.SIM;
				}else{
					indicadorResidencial = ConstantesSistema.NAO;
				}

				adicionarValorPrimitivoAoBody("indicadorResidencial", indicadorResidencial);

			}

		}catch(FachadaException e){
			if(e.getMensagem() != null){
				informarStatus(STATUS_TIPO_ALERTA, e.getMensagem());
			}
		}
	}

	private CampanhaCadastroJSONHelper preencherCampanhaCadastroJSONHelper(Fachada fachada, CampanhaCadastro campanhaCadastro,
					Short indicadorExibirBotaoEmitirComprovante){
		
		CampanhaCadastroJSONHelper retorno = new CampanhaCadastroJSONHelper();
	
		// Envia a inscri��o apenas se o comprovante n�o estiver bloqueado.
		if(campanhaCadastro.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.NAO)){
			retorno.setInscricaoCampanha(campanhaCadastro.getNumeroInscricao());
		}
		
		// idCampanhaCadastro
		retorno.setIdCampanhaCadasto(campanhaCadastro.getId().toString());

		// idImovel
		retorno.setIdImovel(campanhaCadastro.getImovel().getId().toString());
				
		// nomeCliente
		retorno.setNomeCliente(campanhaCadastro.getNomeCliente());

		// tipoRelacao
		retorno.setTipoRelacao(campanhaCadastro.getCodigoTipoRelacaoClienteImovel().toString());

		// Pessoa F�sica
		if(campanhaCadastro.getNumeroCPF() != null){
			// N� CPF
			retorno.setNuCPF(campanhaCadastro.getNumeroCPF());
			
			// Nome da M�e
			retorno.setNomeMae(campanhaCadastro.getNomeMae());

			// N� RG
			retorno.setNuRG(campanhaCadastro.getNumeroRG());

			// Data Emiss�o RG
			retorno.setDtEmissaoRG(Util.formatarData(campanhaCadastro.getDataRGEmissao()));

			// id �rg�o Expedidor
			retorno.setIdOrgaoExpedidorRG(campanhaCadastro.getOrgaoExpedidorRG().getId().toString());

			// Estado - UF
			retorno.setEstado(campanhaCadastro.getUnidadeFederacao().getId().toString());
			
			// Data Nascimento
			retorno.setDtNascimento(Util.formatarData(campanhaCadastro.getDataNascimento()));

		}else{
			// N� CNPJ
			retorno.setNuCNPJ(campanhaCadastro.getNumeroCNPJ());
		}
		
		// E-Mail
		if(campanhaCadastro.getDsEmail() != null){
			retorno.setDsEmail(campanhaCadastro.getDsEmail());
		}

		// Indicador Exibir Bot�o Emitir Comprovante
		retorno.setIndicadorExibirBotaoEmitirComprovante(indicadorExibirBotaoEmitirComprovante.toString());

		return retorno;
	
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
