
package gcom.agenciavirtual.cobranca.campanhapremiacao;

import gcom.agenciavirtual.AbstractAgenciaVirtualJSONWebservice;
import gcom.cadastro.cliente.FoneTipo;
import gcom.cadastro.cliente.OrgaoExpedidorRg;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cobranca.campanhapremiacao.Campanha;
import gcom.cobranca.campanhapremiacao.CampanhaCadastro;
import gcom.cobranca.campanhapremiacao.CampanhaCadastroFone;
import gcom.fachada.Fachada;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

/**
 * Retorna mensagemExibicao
 * <b>Este serviço é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idImovel [OBRIGATORIO]</li>
 * <li>cdTipoRelacao [OBRIGATORIO]</li>
 * <li>nomeCliente [OBRIGATORIO]</li>
 * <li>idCampanha [OBRIGATORIO]</li>
 * <li>nomeMae [OBRIGATORIO]</li>
 * <li>nuRG [OBRIGATORIO]</li>
 * <li>dtRGEmissao [OBRIGATORIO]</li>
 * <li>idOrgaoExpedidorRG [OBRIGATORIO]</li>
 * <li>idUnidadeFederacao [OBRIGATORIO]</li>
 * <li>dtNascimento [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/efetuarInscricaoCampanhaPremiacaoWebService.do
 * 
 * @author Hiroshi Goncalves
 */
public class EfetuarInscricaoCampanhaPremiacaoWebService
				extends AbstractAgenciaVirtualJSONWebservice {

	@Override
	protected void preencherJSONBody(ActionForm form, HttpServletRequest request) throws Exception{

		Fachada fachada = Fachada.getInstancia();

		// String Formato: "cdTipoFone-ddd-nuFone-nuRamal;..."
		String pStringColFone = recuperarParametroString("colTelefone", "Telefone", false, true, request);
		Integer idImovel = recuperarParametroInteiroObrigatorio("idImovel", "Imóvel", true, request);
		Integer cdTipoRelacao = recuperarParametroInteiroObrigatorio("cdTipoRelacao", "Tipo Relação", true, request);
		String nomeCliente = recuperarParametroStringObrigatorio("nomeCliente", "Nome Cliente", true, request);

		String cpf = recuperarParametroString("cpf", "CPF", false, true, request);
		String cnpj = recuperarParametroString("cnpj", "CNPJ", false, true, request);

		String cpfCnpj = cpf;
		if(cpfCnpj == null){
			cpfCnpj = cnpj;
		}
		try{

			if(cpfCnpj == null){
				throw new FachadaException("atencao.erro.campo_obrigatorio_masculino", null, "cpf/cnpj");
			}

			String dsEmail = recuperarParametroString("dsEmail", "E-mail", false, true, request);
			Integer idCampanha = recuperarParametroInteiroObrigatorio("idCampanha", "Campanha", false, request);
			
			Collection<CampanhaCadastroFone> colecaoCampanhaCadastroFone = this.montarCollectionCampanhaCadastroFone(pStringColFone);

			CampanhaCadastro campanhaCadastro = new CampanhaCadastro();

			Campanha campanha = (Campanha) fachada.pesquisar(idCampanha, Campanha.class);

			campanhaCadastro.setCampanha(campanha);

			campanhaCadastro.setImovel(new Imovel(idImovel));

			campanhaCadastro.setCodigoTipoRelacaoClienteImovel(cdTipoRelacao);

			campanhaCadastro.setNomeCliente(nomeCliente);

			// Se Residencial (CPF)
			if(cpfCnpj.length() == 11){

				String nomeMae = recuperarParametroStringObrigatorio("nomeMae", "Nome da Mãe", true, request);
				String nuRG = recuperarParametroStringObrigatorio("nuRG", "N° RG", true, request);
				String dtRGEmissao = recuperarParametroStringObrigatorio("dtRGEmissao", "Data de Emissão do RG", false, request);
				Integer idOrgaoExpedidorRG = recuperarParametroInteiroObrigatorio("idOrgaoExpedidorRG", "Órgão Expedidor", true, request);
				Integer idUnidadeFederacao = recuperarParametroInteiroObrigatorio("idUnidadeFederacao", "UF", true, request);
				String dtNascimento = recuperarParametroStringObrigatorio("dtNascimento", "Data de Nascimento", false, request);

				campanhaCadastro.setNumeroCPF(cpfCnpj);
				campanhaCadastro.setNumeroRG(nuRG);
				campanhaCadastro.setDataRGEmissao(Util.converteStringParaDate(dtRGEmissao, false));

				OrgaoExpedidorRg orgaoExpedidorRG = new OrgaoExpedidorRg();
				orgaoExpedidorRG.setId(Integer.valueOf(idOrgaoExpedidorRG));
				campanhaCadastro.setOrgaoExpedidorRG(orgaoExpedidorRG);

				campanhaCadastro.setUnidadeFederacao(new UnidadeFederacao(Integer.valueOf(idUnidadeFederacao)));

				campanhaCadastro.setDataNascimento(Util.converteStringParaDate(dtNascimento, false));
				campanhaCadastro.setNomeMae(nomeMae);

			}else{
				campanhaCadastro.setNumeroCNPJ(cpfCnpj);
			}

			if(dsEmail != null && !dsEmail.equals("")){
				campanhaCadastro.setDsEmail(dsEmail);
			}



			campanhaCadastro = fachada.efetuarInscricaoCampanhaPremiacaoAction(null, campanhaCadastro, colecaoCampanhaCadastroFone);


			// [FS0014] - Verificar sucesso da transação
			String mensagemExibicao = "";
			Short indicadorComprovanteBloqueado = ConstantesSistema.NAO;
			Short indicadorPossuiEmail = ConstantesSistema.NAO;

			if(campanhaCadastro.getIndicadorComprovanteBloqueado().equals(ConstantesSistema.NAO)){

				mensagemExibicao = "A inscrição do imóvel " + idImovel + " na campanha "
								+ campanhaCadastro.getCampanha().getDsTituloCampanha()
								+ " foi realizada com sucesso. O Número de Inscrição é " + campanhaCadastro.getNumeroInscricao()
								+ ". Solicite a emissão do comprovante de inscrição.";

				if(campanhaCadastro.getDsEmail() != null && !campanhaCadastro.getDsEmail().equals("")){
					indicadorPossuiEmail = ConstantesSistema.SIM;
				}

			}else{
				indicadorComprovanteBloqueado = ConstantesSistema.SIM;

				FiltroEmpresa filtroEmpresa = new FiltroEmpresa();

				filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADOR_EMPRESA_PRINCIPAL, ConstantesSistema.SIM));

				Collection colEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

				Empresa empresa = (Empresa) Util.retonarObjetoDeColecao(colEmpresa);

				mensagemExibicao = "Esta matrícula apresenta débito, solicitamos comparecer ao Escritório Local da "
								+ empresa.getDescricaoAbreviada();
			}

			adicionarValorPrimitivoAoBody("indicadorComprovanteBloqueado", indicadorComprovanteBloqueado);
			adicionarValorPrimitivoAoBody("indicadorPossuiEmail", indicadorPossuiEmail);
			adicionarValorPrimitivoAoBody("mensagemExibicao", mensagemExibicao);

		}catch(FachadaException e){
			if(e.getMensagem() != null){
				if(e.getParametroMensagem() != null){
					informarStatus(STATUS_TIPO_ALERTA, e.getMensagem().replace("{0}", e.getParametroMensagem().get(0)));
				}else{
					informarStatus(STATUS_TIPO_ALERTA, e.getMensagem());
				}
			}
		}

	}

	private Collection<CampanhaCadastroFone> montarCollectionCampanhaCadastroFone(String stringColFone){

		// stringColFone Formato: "cdTipoFone-ddd-nuFone-nuRamal;..."
		Collection colCampanhaCadastroFone = new ArrayList();

		if(stringColFone != null && !stringColFone.equals("")){
			String[] arFones = stringColFone.split(";");
			for(String sFone : arFones){
				// sFone Formato: cdTipoFone-ddd-nuFone-nuRamal

				String[] arFone = sFone.split("-");
				CampanhaCadastroFone campanhaCadastroFone = new CampanhaCadastroFone();
				FoneTipo foneTipo = new FoneTipo();

				foneTipo.setId(Integer.valueOf(arFone[0]));

				campanhaCadastroFone.setFoneTipo(foneTipo);
				campanhaCadastroFone.setCodigoDDD(arFone[1]);
				campanhaCadastroFone.setNumeroFone(arFone[2]);
				campanhaCadastroFone.setUltimaAlteracao(new Date());

				if(arFone[3] != null && !arFone[3].equals("")){
					campanhaCadastroFone.setNumeroFoneRamal(arFone[3]);
				}

				colCampanhaCadastroFone.add(campanhaCadastroFone);

			}
		}

		return colCampanhaCadastroFone;
	}

	@Override
	protected boolean isServicoRestrito(){

		return false;
	}
}
