
package gcom.agenciavirtual.cobranca;

import gcom.agenciavirtual.AbstractAgenciaVirtualBinarioWebservice;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.relatorio.faturamento.conta.Relatorio2ViaConta;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.*;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;
import gcom.util.parametrizacao.webservice.ParametrosAgenciaVirtual;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.CreateException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.comum.exception.NegocioException;
import br.com.procenge.util.Constantes;

/**
 * Emite a segunda via de contas .pdf
 * <b>Este serviço não é PÚBLICO</b>
 * Parametros no resquest.
 * <ul>
 * <li>idsConta [OBRIGATORIO]</li>
 * <li>matricula [OBRIGATORIO]</li>
 * <li>cpfcnpj [OBRIGATORIO]</li>
 * </ul>
 * URL de acesso: /agenciavirtual/emissaoContaSegundaViaWebservice.do
 * 
 * @author Jose Claudio
 */
public class EmissaoContaSegundaViaWebservice
				extends AbstractAgenciaVirtualBinarioWebservice {

	@Override
	public void processarArquivo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		Integer idContaHistorico = recuperarIDContaHistorico(request);
		Collection<Integer> idsConta = recuperarIDsContas(request);

		Integer matriculaImovel = recuperarParametroInteiroObrigatorio("matricula", LABEL_CAMPO_MATRICULA_DO_IMOVEL, false, request);

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		String nomeEmpresa = sistemaParametro.getNomeAbreviadoEmpresa();

		// Parte que vai mandar o relatório para a tela
		// cria uma instância da classe do relatório
		Relatorio2ViaConta relatorio2ViaConta = new Relatorio2ViaConta(Usuario.USUARIO_BATCH);
		Short contaSemCodigoBarras = ConstantesSistema.NAO;
		for(Integer idConta : idsConta){
			Fachada.getInstancia().verificarPermissaoImovelConta(idConta, matriculaImovel);

			FiltroConta filtroConta = new FiltroConta();
			filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
			Collection<Conta> colecaoConta = getControladorUtil().pesquisar(filtroConta, Conta.class.getName());

			Conta conta = null;
			if(colecaoConta != null && !colecaoConta.isEmpty()){
				conta = (Conta) Util.retonarObjetoDeColecao(colecaoConta);

				// Obs.: Esse trecho de código está duplicado, aqui e na emissão de 2ª Via do GSAN.
				// Essa não é uma boa prática, porém devido ao tempo e a urgência essa ação foi
				// tomada.
				// YSouza 09/09/2014
		relatorio2ViaConta.addParametro("indicadorEmitido2ViaAgenciaVirtual", "S");

				// [FS0013] – Verificar se conta em débito automático
				// . Caso a conta cadastrada para débito automático (CNTA_ICCONTADEBITO = 1) e o
				// indicador
				// de Emissão de 2ª. Via de Conta Conta com código de barras para clientes com
				// débito
				// automático com valor igual a NÃO (PASI_VLPARAMETRO = 2 na da tabela
				// PARAMETRO_SISTEMA
				// com
				// PASI_CDPARAMETRO=“ P_EMITE_2VIA_COM_CDBARRAS_CTA_DEB_AUTOM”), gerar linha em
				// branco.
			
				if(conta != null && conta.getIndicadorDebitoConta().equals(ConstantesSistema.SIM)){

					String pEmite2ViaCodBarrasContaDebAut = null;
					try{
						pEmite2ViaCodBarrasContaDebAut = ParametroFaturamento.P_EMITE_2VIA_COM_CDBARRAS_CTA_DEB_AUTOM.executar();
						if(!Util.isVazioOuBrancoOuZero(pEmite2ViaCodBarrasContaDebAut)
										&& pEmite2ViaCodBarrasContaDebAut.equals(ConstantesSistema.NAO.toString())){
							contaSemCodigoBarras = ConstantesSistema.SIM;
						}

					}catch(ControladorException e){
						throw new ActionServletException("atencao.sistemaparametro_inexistente", "P_EMITE_2VIA_COM_CDBARRAS_CTA_DEB_AUTOM");
					}

				}

			}

			relatorio2ViaConta.addParametro("idsConta", idsConta);
			relatorio2ViaConta.addParametro("cobrarTaxaEmissaoConta", false);
			relatorio2ViaConta.addParametro("contaSemCodigoBarras", contaSemCodigoBarras);

			relatorio2ViaConta.addParametro("qtdeContas", 1);
			String tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";

			relatorio2ViaConta.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
			relatorio2ViaConta.addParametro("nomeEmpresa", nomeEmpresa);
			relatorio2ViaConta.addParametro("nomeRelatorio", "2ª VIA");
			relatorio2ViaConta.addParametro("idContaHistorico", idContaHistorico);
		}

		processarExibicaoRelatorio(relatorio2ViaConta, TarefaRelatorio.TIPO_PDF, request, response, mapping);
	}

	private Integer recuperarIDContaHistorico(HttpServletRequest request){

		String idContHistorico = request.getParameter("idContaHistorico");
		Integer idContaHistorico = Util.isVazioOuBranco(idContHistorico) ? null : Integer.valueOf(idContHistorico);
		return idContaHistorico;
	}

	private Collection<Integer> recuperarIDsContas(HttpServletRequest request) throws NegocioException, ControladorException{

		String idsContaResquest = recuperarParametroStringObrigatorio("idsConta", "Contas", false, request);
		Collection idsConta = new ArrayList();
		Collection motivosRevisaoParaExcluir = getMotivosRevisaoParaExcluir();

		String indicadorNaoPermiteContaEmRevisao = (String) ParametrosAgenciaVirtual.P_AV_EMITE_2VIA_CTA_REVISAO.executar();
		boolean verificarMotivoRevisaoEstaPreenchido = false;
		if(ConstantesSistema.NAO.equals(Short.valueOf(indicadorNaoPermiteContaEmRevisao))){
			verificarMotivoRevisaoEstaPreenchido = true;
		}

		try{
			StringBuilder contasRetidas = new StringBuilder();
			for(String idContaString : idsContaResquest.split(",")){

				Integer idConta = Integer.valueOf(idContaString);

				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
				filtroConta.adicionarCaminhoParaCarregamentoEntidade("contaMotivoRevisao");
				Collection collContas = null;
					collContas = getControladorUtil().pesquisar(filtroConta, Conta.class.getName());
					Conta conta = (Conta) Util.retonarObjetoDeColecao(collContas);

				if(verificarMotivoRevisaoEstaPreenchido){

					if(Util.isNaoNuloBrancoZero(conta.getContaMotivoRevisao())){
						if(contasRetidas.length() > 0){
							contasRetidas.append(", ");
						}
						contasRetidas.append(conta.getFormatarAnoMesParaMesAno());
						idsConta.add(conta.getId());
					}else{
						idsConta.add(conta.getId());
					}

				}else{

					if(Util.isNaoNuloBrancoZero(conta.getContaMotivoRevisao())){
						if(motivosRevisaoParaExcluir.contains(conta.getContaMotivoRevisao().getId())){
							if(contasRetidas.length() > 0){
								contasRetidas.append(", ");
							}
							contasRetidas.append(conta.getFormatarAnoMesParaMesAno());
						}else{
							idsConta.add(conta.getId());
						}
					}else{
						idsConta.add(conta.getId());
					}
				}

			}

			if(contasRetidas.length() > 0){
				throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.contas.segunda_via_nao_permitida", contasRetidas.toString());
			}
		}catch(NumberFormatException e){
			Object[] argumentosMensagemArray = new Object[1];
			argumentosMensagemArray[0] = "Conta";

			throw new NegocioException(Constantes.RESOURCE_BUNDLE, "atencao.campo.informado.invalido", argumentosMensagemArray);
		}

		return idsConta;
	}

	/**
	 * Recupera a lista de motivos de revisão que deve ser desconsiderada da lista de contas
	 * 
	 * @author Luciano Galvao
	 * @return
	 * @throws ControladorException
	 */
	private Collection<Integer> getMotivosRevisaoParaExcluir() throws ControladorException{

		String paramMotivos = (String) ParametrosAgenciaVirtual.P_AV_CONTA_MOTIVO_REVISAO_NAO_EMITIR_2_VIA.executar();
		String[] motivosArray = paramMotivos.split(",");

		Collection<Integer> motivosRevisaoParaExcluir = new ArrayList<Integer>();

		for(int i = 0; i < motivosArray.length; i++){
			motivosRevisaoParaExcluir.add(Integer.parseInt(motivosArray[i]));
		}

		return motivosRevisaoParaExcluir;

	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	private ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}
}
