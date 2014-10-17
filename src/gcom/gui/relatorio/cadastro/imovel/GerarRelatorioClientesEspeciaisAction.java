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

package gcom.gui.relatorio.cadastro.imovel;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.gerencial.cadastro.GerarRelatorioClientesEspeciaisActionForm;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cadastro.imovel.RelatorioClientesEspeciais;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * action respons�vel pela exibi��o do relat�rio de bairro manter
 * 
 * @author S�vio Luiz
 * @created 11 de Julho de 2005
 */
public class GerarRelatorioClientesEspeciaisAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param actionMapping
	 *            Descri��o do par�metro
	 * @param actionForm
	 *            Descri��o do par�metro
	 * @param httpServletRequest
	 *            Descri��o do par�metro
	 * @param httpServletResponse
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a vari�vel de retorno
		ActionForward retorno = null;

		// Mudar isso quando tiver esquema de seguran�a
		// HttpSession sessao = httpServletRequest.getSession(false);

		GerarRelatorioClientesEspeciaisActionForm gerarRelatorioClientesEspeciaisActionForm = (GerarRelatorioClientesEspeciaisActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String idUnidadeNegocio = gerarRelatorioClientesEspeciaisActionForm.getIdUnidadeNegocio();
		String idGerenciaRegional = gerarRelatorioClientesEspeciaisActionForm.getIdGerenciaRegional();
		String idLocalidadeInicialForm = gerarRelatorioClientesEspeciaisActionForm.getIdLocalidadeInicial();
		String idLocalidadeFinalForm = gerarRelatorioClientesEspeciaisActionForm.getIdLocalidadeFinal();
		String[] idsPerfilImovel = gerarRelatorioClientesEspeciaisActionForm.getIdsImovelPerfil();
		String[] idsCategoria = gerarRelatorioClientesEspeciaisActionForm.getIdsCategoria();
		String[] idsSubcategoria = gerarRelatorioClientesEspeciaisActionForm.getIdsSubCategoria();
		String idSituacaoAgua = gerarRelatorioClientesEspeciaisActionForm.getIdSituacaoLigacaoAgua();
		String idSituacaoEsgoto = gerarRelatorioClientesEspeciaisActionForm.getIdSituacaoLigacaoEsgoto();
		String qtdeEconomiasInicial = gerarRelatorioClientesEspeciaisActionForm.getIntervaloQtdEcoInicial();
		String qtdeEconomiasFinal = gerarRelatorioClientesEspeciaisActionForm.getIntervaloQtdEcoFinal();
		String intervaloConsumoAguaInicial = gerarRelatorioClientesEspeciaisActionForm.getIntervaloConsumoAguaInicial();
		String intervaloConsumoAguaFinal = gerarRelatorioClientesEspeciaisActionForm.getIntervaloConsumoAguaFinal();
		String intervaloConsumoEsgotoInicial = gerarRelatorioClientesEspeciaisActionForm.getIntervaloConsumoEsgotoInicial();
		String intervaloConsumoEsgotoFinal = gerarRelatorioClientesEspeciaisActionForm.getIntervaloConsumoEsgotoFinal();
		String idClienteResponsavel = gerarRelatorioClientesEspeciaisActionForm.getIdClienteResponsavel();
		String intervaloConsumoResponsavelInicial = gerarRelatorioClientesEspeciaisActionForm.getIntervaloConsumoResponsavelInicial();
		String intervaloConsumoResponsavelFinal = gerarRelatorioClientesEspeciaisActionForm.getIntervaloConsumoResponsavelFinal();
		String[] idsCapacidadesHidrometro = gerarRelatorioClientesEspeciaisActionForm.getIdsCapacidadeHidrometro();
		String[] idsTarifasConsumo = gerarRelatorioClientesEspeciaisActionForm.getIdsTarifaConsumo();
		String idLeituraAnormalidade = gerarRelatorioClientesEspeciaisActionForm.getIdLeituraAnormalidade();
		String idConsumoAnormalidade = gerarRelatorioClientesEspeciaisActionForm.getIdConsumoAnormalidade();
		String leituraAnormalidade = gerarRelatorioClientesEspeciaisActionForm.getLeituraAnormalidade();
		String consumoAnormalidade = gerarRelatorioClientesEspeciaisActionForm.getConsumoAnormalidade();
		String[] idsClienteTipoEspecial = gerarRelatorioClientesEspeciaisActionForm.getIdsClienteTipoEspecial();

		// Inicio da parte que vai mandar os parametros para o relat�rio

		if(idLocalidadeInicialForm != null && !idLocalidadeInicialForm.equals("")){

			Integer idLocalidadeInicial = new Integer(idLocalidadeInicialForm);
			Integer idLocalidadeFinal = new Integer(idLocalidadeFinalForm);

			if(idLocalidadeInicial > idLocalidadeFinal){
				throw new ActionServletException("atencao.localidade.final.maior.localidade.inicial");
			}

			Localidade localidadeOrigem = fachada.pesquisarLocalidadeDigitada(new Integer(idLocalidadeInicial));

			if(localidadeOrigem == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Inicial");
			}

			Localidade localidadeDestino = fachada.pesquisarLocalidadeDigitada(new Integer(idLocalidadeFinal));

			if(localidadeDestino == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade Final");
			}

		}

		if(idClienteResponsavel != null && !idClienteResponsavel.equals("")){

			Cliente clienteResponsavel = fachada.pesquisarClienteDigitado(new Integer(idClienteResponsavel));

			if(clienteResponsavel == null){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cliente Respons�vel");
			}

		}

		if(idLeituraAnormalidade != null && !idLeituraAnormalidade.equals("")){

			FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
			filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, idLeituraAnormalidade));

			Collection colecaoLeiturasAnormalidades = fachada.pesquisar(filtroLeituraAnormalidade, LeituraAnormalidade.class.getName());

			if(colecaoLeiturasAnormalidades == null || colecaoLeiturasAnormalidades.isEmpty()){
				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Anormalidade de Leitura");
			}

		}

		Date dataInstalacaoHidrometroInicial = null;
		Date dataInstalacaoHidrometroFinal = null;

		if(gerarRelatorioClientesEspeciaisActionForm.getDataInstalacaoHidrometroInicial() != null
						&& !gerarRelatorioClientesEspeciaisActionForm.getDataInstalacaoHidrometroInicial().equals("")){
			dataInstalacaoHidrometroInicial = Util.converteStringParaDate(gerarRelatorioClientesEspeciaisActionForm
							.getDataInstalacaoHidrometroInicial());
			dataInstalacaoHidrometroFinal = Util.converteStringParaDate(gerarRelatorioClientesEspeciaisActionForm
							.getDataInstalacaoHidrometroFinal());

			if(dataInstalacaoHidrometroInicial.compareTo(dataInstalacaoHidrometroFinal) > 0){
				throw new ActionServletException("atencao.data_instalacao_hidrometro_inicial.maior.data_instalacao_hidrometro_final");
			}
		}

		// Verifica se o usu�rio n�o informou nenhum par�metro no filtro
		if((idUnidadeNegocio == null || idUnidadeNegocio.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (idGerenciaRegional == null || idGerenciaRegional.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (idLocalidadeInicialForm == null || idLocalidadeInicialForm.trim().equals(""))
						&& (idLocalidadeFinalForm == null || idLocalidadeFinalForm.trim().equals(""))
						&& ((idsPerfilImovel == null || idsPerfilImovel.equals("")) || (idsPerfilImovel != null
										&& idsPerfilImovel.length == 1 && idsPerfilImovel[0].equals(""
										+ ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& ((idsCategoria == null || idsCategoria.equals("")) || (idsCategoria != null && idsCategoria.length == 1 && idsCategoria[0]
										.equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& ((idsSubcategoria == null || idsSubcategoria.equals("")) || (idsSubcategoria != null
										&& idsSubcategoria.length == 1 && idsSubcategoria[0].equals(""
										+ ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& (idSituacaoAgua == null || idSituacaoAgua.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (idSituacaoEsgoto == null || idSituacaoEsgoto.trim().equals("" + ConstantesSistema.NUMERO_NAO_INFORMADO))
						&& (qtdeEconomiasInicial == null || qtdeEconomiasInicial.trim().equals(""))
						&& (qtdeEconomiasFinal == null || qtdeEconomiasFinal.trim().equals(""))
						&& (intervaloConsumoAguaInicial == null || intervaloConsumoAguaInicial.trim().equals(""))
						&& (intervaloConsumoAguaFinal == null || intervaloConsumoAguaFinal.trim().equals(""))
						&& (intervaloConsumoEsgotoInicial == null || intervaloConsumoEsgotoInicial.trim().equals(""))
						&& (intervaloConsumoEsgotoFinal == null || intervaloConsumoEsgotoFinal.trim().equals(""))
						&& (idClienteResponsavel == null || idClienteResponsavel.trim().equals(""))
						&& (intervaloConsumoResponsavelInicial == null || intervaloConsumoResponsavelInicial.trim().equals(""))
						&& (intervaloConsumoResponsavelFinal == null || intervaloConsumoResponsavelFinal.trim().equals(""))
						&& ((idsCapacidadesHidrometro == null || idsCapacidadesHidrometro.equals("")) || (idsCapacidadesHidrometro != null
										&& idsCapacidadesHidrometro.length == 1 && idsCapacidadesHidrometro[0].equals(""
										+ ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& ((idsTarifasConsumo == null || idsTarifasConsumo.equals("")) || (idsSubcategoria != null
										&& idsTarifasConsumo.length == 1 && idsTarifasConsumo[0].equals(""
										+ ConstantesSistema.NUMERO_NAO_INFORMADO)))
						&& (idLeituraAnormalidade == null || idLeituraAnormalidade.trim().equals(""))
						&& (idConsumoAnormalidade == null || idConsumoAnormalidade.trim().equals(""))
						&& (leituraAnormalidade == null || leituraAnormalidade.trim().equals(""))
						&& (consumoAnormalidade == null || consumoAnormalidade.trim().equals(""))
						&& dataInstalacaoHidrometroInicial == null
						&& dataInstalacaoHidrometroFinal == null
						&& ((idsClienteTipoEspecial == null || idsClienteTipoEspecial.equals("")) || (idsClienteTipoEspecial != null
										&& idsClienteTipoEspecial.length == 1 && idsClienteTipoEspecial[0].equals(""
										+ ConstantesSistema.NUMERO_NAO_INFORMADO)))){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");

		}

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		RelatorioClientesEspeciais relatorioClientesEspeciais = new RelatorioClientesEspeciais((Usuario) (httpServletRequest
						.getSession(false)).getAttribute("usuarioLogado"));
		relatorioClientesEspeciais.addParametro("idUnidadeNegocio", idUnidadeNegocio);
		relatorioClientesEspeciais.addParametro("idGerenciaRegional", idGerenciaRegional);
		relatorioClientesEspeciais.addParametro("idLocalidadeInicial", idLocalidadeInicialForm);
		relatorioClientesEspeciais.addParametro("idLocalidadeFinal", idLocalidadeFinalForm);
		relatorioClientesEspeciais.addParametro("idsPerfilImovel", idsPerfilImovel);
		relatorioClientesEspeciais.addParametro("idsCategoria", idsCategoria);
		relatorioClientesEspeciais.addParametro("idsSubcategoria", idsSubcategoria);
		relatorioClientesEspeciais.addParametro("idSituacaoAgua", idSituacaoAgua);
		relatorioClientesEspeciais.addParametro("idSituacaoEsgoto", idSituacaoEsgoto);
		relatorioClientesEspeciais.addParametro("qtdeEconomiasInicial", qtdeEconomiasInicial);
		relatorioClientesEspeciais.addParametro("qtdeEconomiasFinal", qtdeEconomiasFinal);
		relatorioClientesEspeciais.addParametro("intervaloConsumoAguaInicial", intervaloConsumoAguaInicial);
		relatorioClientesEspeciais.addParametro("intervaloConsumoAguaFinal", intervaloConsumoAguaFinal);
		relatorioClientesEspeciais.addParametro("intervaloConsumoEsgotoInicial", intervaloConsumoEsgotoInicial);
		relatorioClientesEspeciais.addParametro("intervaloConsumoEsgotoFinal", intervaloConsumoEsgotoFinal);
		relatorioClientesEspeciais.addParametro("idClienteResponsavel", idClienteResponsavel);
		relatorioClientesEspeciais.addParametro("intervaloConsumoResponsavelInicial", intervaloConsumoResponsavelInicial);
		relatorioClientesEspeciais.addParametro("intervaloConsumoResponsavelFinal", intervaloConsumoResponsavelFinal);
		relatorioClientesEspeciais.addParametro("dataInstalacaoHidrometroInicial", dataInstalacaoHidrometroInicial);
		relatorioClientesEspeciais.addParametro("dataInstalacaoHidrometroFinal", dataInstalacaoHidrometroFinal);
		relatorioClientesEspeciais.addParametro("idsCapacidadesHidrometro", idsCapacidadesHidrometro);
		relatorioClientesEspeciais.addParametro("idsTarifasConsumo", idsTarifasConsumo);
		relatorioClientesEspeciais.addParametro("idLeituraAnormalidade", idLeituraAnormalidade);
		relatorioClientesEspeciais.addParametro("idConsumoAnormalidade", idConsumoAnormalidade);
		relatorioClientesEspeciais.addParametro("leituraAnormalidade", leituraAnormalidade);
		relatorioClientesEspeciais.addParametro("consumoAnormalidade", consumoAnormalidade);
		relatorioClientesEspeciais.addParametro("idsClienteTipoEspecial", idsClienteTipoEspecial);

		if(tipoRelatorio == null){
			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioClientesEspeciais.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoRelatorio));
		retorno = processarExibicaoRelatorio(relatorioClientesEspeciais, tipoRelatorio, httpServletRequest, httpServletResponse,
						actionMapping);

		// devolve o mapeamento contido na vari�vel retorno
		return retorno;
	}

}
