/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio Virginio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.FiltroLigacaoEsgotoSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.*;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
import gcom.cadastro.imovel.*;
import gcom.cadastro.localidade.*;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.AjaxService;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroRota;
import gcom.micromedicao.Rota;
import gcom.micromedicao.hidrometro.*;
import gcom.micromedicao.leitura.FiltroLeituraAnormalidade;
import gcom.micromedicao.leitura.LeituraAnormalidade;
import gcom.micromedicao.medicao.MedicaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Description of the Class
 * 
 * @author Ivan Sérgio
 */
public class ValidarImovelEmissaoOrdensSeletivasAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		AjaxService ajaxService = new AjaxService();
		Fachada fachada = Fachada.getInstancia();
		ServicoTipo servicoTipo = null;
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		ImovelEmissaoOrdensSeletivasActionForm imovelEmissaoOrdensSeletivas = (ImovelEmissaoOrdensSeletivasActionForm) actionForm;
		OsSeletivaComandoLocalGeo osSeletivaComandoLocalGeo = null;
		String inserirComando = httpServletRequest.getParameter("concluir");

		if(imovelEmissaoOrdensSeletivas.getLocalidade() != null && imovelEmissaoOrdensSeletivas.getLocalidade()[0].equalsIgnoreCase("-1")){
			imovelEmissaoOrdensSeletivas.limparCombos();
		}
		/**
		 * Tipo da Ordem: Caso o usuario selecione a opcao INSTALACAO inibir todos os
		 * os campos da Aba Hidrometro;
		 */
		if(imovelEmissaoOrdensSeletivas.getServicoTipo() != null && !imovelEmissaoOrdensSeletivas.getServicoTipo().equalsIgnoreCase("-1")){
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, imovelEmissaoOrdensSeletivas.getServicoTipo()));

			Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

			imovelEmissaoOrdensSeletivas.setServicoTipoDescricao(servicoTipo.getDescricao());

			if(!ajaxService.comparaServicoTipoSubGrupoSubstituicaoHidrometro(imovelEmissaoOrdensSeletivas.getServicoTipo().toString())){
				imovelEmissaoOrdensSeletivas.limparCamposHidrometro();
			}
		}else{
			throw new ActionServletException(
							"atencao.informe_campo",
							"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
							new Exception(), "Tipo da Ordem");
		}

		if(imovelEmissaoOrdensSeletivas.getFirma() != null && !imovelEmissaoOrdensSeletivas.getFirma().equalsIgnoreCase("-1")){
		}else{
			throw new ActionServletException(
							"atencao.informe_campo",
							"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
							new Exception(), "a Empresa");
		}

		// [FS0006] - Validar Ano/Mes de Instalacao
		if(imovelEmissaoOrdensSeletivas.getMesAnoInstalacao() != null
						&& !imovelEmissaoOrdensSeletivas.getMesAnoInstalacao().trim().equals("")){
			Integer anoMesAtual = Util.converterStringParaInteger(Util.getAnoMesComoString(new Date()).replace("/", ""));

			Integer anoMesInstalacao = Util.converterStringParaInteger(Util.formatarMesAnoParaAnoMes(imovelEmissaoOrdensSeletivas
							.getMesAnoInstalacao().replace("/", "")));

			if(anoMesInstalacao > anoMesAtual){
				throw new ActionServletException("atencao.mes_ano_instalacao_invalido", null, "");
			}
		}

		// [FS0011] - Verificar preenchimento dos parâmetros. Caso não informe o Arquivo, nem o Elo,
		// nem o Grupo de Faturamento, nem algum dos Dados de Localização Geográfica (regional,
		// localidade, setor, quadra, rota, lote, bairro ou logradouro), exibir a mensagem “É
		// necessário informar algum desses parâmetros: Arquivo ou Elo ou Grupo de Faturamento ou
		// algum dos dados de localização geográfica do imóvel – Gerência Regional, Localidade,
		// Setor, Quadra, Lote, Rota, Bairro, Logradouro.” e retornar para o passo correspondente no
		// fluxo principal.

		if(Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getArquivoDownload())
						&& Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getIdsImoveis())
						&& Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getElo())
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getFaturamentoGrupo()) || imovelEmissaoOrdensSeletivas
										.getFaturamentoGrupo().equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getGerenciaRegional()) || imovelEmissaoOrdensSeletivas
										.getGerenciaRegional().equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLocalidade()[0]) || imovelEmissaoOrdensSeletivas
										.getLocalidade()[0].equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getSetor()) || imovelEmissaoOrdensSeletivas.getSetor()[0]
										.equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getBairro()) || imovelEmissaoOrdensSeletivas.getBairro()[0]
										.equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getQuadra()) || imovelEmissaoOrdensSeletivas.getQuadra()[0]
										.equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getRota()) || imovelEmissaoOrdensSeletivas.getRota()[0]
										.equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLogradouro()) || imovelEmissaoOrdensSeletivas
										.getLogradouro()[0].equals(ConstantesSistema.INVALIDO_ID.toString()))
						&& (Util.isVazioOuBranco(imovelEmissaoOrdensSeletivas.getLote()) || imovelEmissaoOrdensSeletivas.getLote()[0]
										.equals(ConstantesSistema.INVALIDO_ID.toString()))){
			throw new ActionServletException(
							"atencao.nenhum.parametro.preenchido",
							"filtrarImovelEmissaoOrdensSeletivasWizardAction.do?destino=1&action=exibirFiltrarImovelEmissaoOrdensSeletivasParametros",
							new Exception(), "É necessário informar algum desses parâmetros: Arquivo ou Elo ou Grupo de Faturamento"
											+ " ou algum dos dados de localização geográfica do imóvel – Gerência Regional,"
											+ " Localidade, Setor, Quadra, Lote, Rota, Bairro, Logradouro.");

		}

		if(imovelEmissaoOrdensSeletivas.getTitulo() == null || imovelEmissaoOrdensSeletivas.getTitulo().equals("")){

			throw new ActionServletException("atencao.titulo_nao_informado", null, "");

		}

		if(inserirComando != null && imovelEmissaoOrdensSeletivas.getSimulacao().equals(ConstantesSistema.NAO.toString())){

			OsSeletivaComando osSeletivaComando = new OsSeletivaComando();

			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, imovelEmissaoOrdensSeletivas.getServicoTipo()));

			Empresa empresa = new Empresa();
			empresa.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getFirma()));

			osSeletivaComando.setServicoTipo(servicoTipo);
			osSeletivaComando.setEmpresa(empresa);
			osSeletivaComando.setDescricaoTitulo(imovelEmissaoOrdensSeletivas.getTitulo());

			if(imovelEmissaoOrdensSeletivas.getQuantidadeMaxima() != null && !imovelEmissaoOrdensSeletivas.getQuantidadeMaxima().equals("")){

				osSeletivaComando.setQuantidadeMaximaOrdens(Integer.parseInt(imovelEmissaoOrdensSeletivas.getQuantidadeMaxima()));
			}

			if(imovelEmissaoOrdensSeletivas.getIdsImoveis() != null && !imovelEmissaoOrdensSeletivas.getIdsImoveis().isEmpty()){

				Collection<Integer> idImoveis = imovelEmissaoOrdensSeletivas.getIdsImoveis();

				byte[] arquivo = null;

				StringBuilder arquivoSB = new StringBuilder();

				for(Integer idImovel : idImoveis){
					arquivoSB.append(idImovel);
					arquivoSB.append(System.getProperty("line.separator"));
				}
				arquivo = String.valueOf(arquivoSB).getBytes();

				imovelEmissaoOrdensSeletivas.setArquivo(arquivo);

				osSeletivaComando.setArquivoImovel(imovelEmissaoOrdensSeletivas.getArquivoBlob());
			}

			if(imovelEmissaoOrdensSeletivas.getElo() != null && !imovelEmissaoOrdensSeletivas.getElo().equals("")){

				osSeletivaComando.setCodigoElo(Integer.parseInt(imovelEmissaoOrdensSeletivas.getElo()));
			}
			if(imovelEmissaoOrdensSeletivas.getFaturamentoGrupo() != null
							&& !imovelEmissaoOrdensSeletivas.getFaturamentoGrupo().toString()
											.equals(ConstantesSistema.INVALIDO_ID.toString())){

				FaturamentoGrupo faturamentoGrupo = new FaturamentoGrupo();
				faturamentoGrupo.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getFaturamentoGrupo()));

				osSeletivaComando.setFaturamentoGrupo(faturamentoGrupo);
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasInicial().equals("")){

				osSeletivaComando.setQuantidadeEconomiasInicial(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloQuantidadeEconomiasInicial()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeEconomiasInicial().equals("")){

				osSeletivaComando.setQuantidadeEconomiasFinal(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloQuantidadeEconomiasFinal()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosInicial().equals("")){

				osSeletivaComando.setQuantidadeDocumentosInicial(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloQuantidadeDocumentosInicial()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosFinal() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeDocumentosFinal().equals("")){

				osSeletivaComando.setQuantidadeDocumentosFinal(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloQuantidadeDocumentosFinal()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresInicial().equals("")){

				osSeletivaComando.setNumeroMoradoresInicial(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloNumeroMoradoresInicial()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresFinal() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresFinal().equals("")){

				osSeletivaComando
								.setNumeroMoradoresFinal(Integer.parseInt(imovelEmissaoOrdensSeletivas.getIntervaloNumeroMoradoresFinal()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloNumeroPontosUtilizacaoInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloNumeroPontosUtilizacaoInicial().equals("")){

				osSeletivaComando.setNumeroPontosUtilizacaoInicial(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloNumeroPontosUtilizacaoInicial()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloNumeroPontosUtilizacaoFinal() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloNumeroPontosUtilizacaoFinal().equals("")){

				osSeletivaComando.setNumeroPontosUtilizacaoFinal(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloNumeroPontosUtilizacaoFinal()));
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial().equals("")){

				BigDecimal intervaloAreaConstruidaInicial = new BigDecimal(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaInicial());

				osSeletivaComando.setNumeroAreaConstruidaInicial(intervaloAreaConstruidaInicial);
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal().equals("")){

				BigDecimal intervaloAreaConstruidaFinal = new BigDecimal(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaFinal());

				osSeletivaComando.setNumeroAreaConstruidaFinal(intervaloAreaConstruidaFinal);
			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida().toString()
											.equals(ConstantesSistema.INVALIDO_ID.toString())){

				AreaConstruidaFaixa areaConstruidaFaixa = new AreaConstruidaFaixa();
				areaConstruidaFaixa.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getIntervaloAreaConstruidaPredefinida()));

				osSeletivaComando.setAreaConstruidaFaixa(areaConstruidaFaixa);

			}
			if(imovelEmissaoOrdensSeletivas.getImovelCondominio() != null && !imovelEmissaoOrdensSeletivas.getImovelCondominio().equals("")){

				osSeletivaComando.setIndicadorImovelCondominio(Integer.parseInt(imovelEmissaoOrdensSeletivas.getImovelCondominio()));

			}
			if(imovelEmissaoOrdensSeletivas.getConsumoPorEconomia() != null
							&& !imovelEmissaoOrdensSeletivas.getConsumoPorEconomia().equals("")){

				osSeletivaComando.setNumeroConsumoEconomia(Integer.parseInt(imovelEmissaoOrdensSeletivas.getConsumoPorEconomia()));

			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloNumeroConsumoMesInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloNumeroConsumoMesInicial().equals("")){

				osSeletivaComando.setNumeroFaixaConsumoInicial(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloNumeroConsumoMesInicial()));

			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloNumeroConsumoMesFinal() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloNumeroConsumoMesFinal().equals("")){

				osSeletivaComando.setNumeroFaixaConsumoFinal(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloNumeroConsumoMesFinal()));

			}
			if(imovelEmissaoOrdensSeletivas.getTipoMedicao() != null && !imovelEmissaoOrdensSeletivas.getTipoMedicao().equals("")){

				MedicaoTipo medicaoTipo = new MedicaoTipo();
				medicaoTipo.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getTipoMedicao()));

				osSeletivaComando.setMedicaoTipo(medicaoTipo);

			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeContasVencidasInicial() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeContasVencidasInicial().equals("")){

				osSeletivaComando.setQuantidadeDebitoInicial(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloQuantidadeContasVencidasInicial()));

			}
			if(imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeContasVencidasFinal() != null
							&& !imovelEmissaoOrdensSeletivas.getIntervaloQuantidadeContasVencidasFinal().equals("")){

				osSeletivaComando.setQuantidadeDebitoFinal(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getIntervaloQuantidadeContasVencidasFinal()));

			}
			if(imovelEmissaoOrdensSeletivas.getValorTotalDebitoVencido() != null
							&& !imovelEmissaoOrdensSeletivas.getValorTotalDebitoVencido().equals("")){

				BigDecimal valorDebito = new BigDecimal(imovelEmissaoOrdensSeletivas.getValorTotalDebitoVencido());

				osSeletivaComando.setValorDebito(valorDebito);

			}
			if(imovelEmissaoOrdensSeletivas.getNumeroOcorrenciasConsecutivas() != null
							&& !imovelEmissaoOrdensSeletivas.getNumeroOcorrenciasConsecutivas().equals("")){

				osSeletivaComando.setNumeroOcorrenciasConsecutivas(Integer.parseInt(imovelEmissaoOrdensSeletivas
								.getNumeroOcorrenciasConsecutivas()));

			}
			osSeletivaComando.setUsuario(usuarioLogado);
			osSeletivaComando.setTempoComando(new Date());
			osSeletivaComando.setUltimaAlteracao(new Date());

			Integer idosSeletivaComando = (Integer) fachada.inserir(osSeletivaComando);

			if(idosSeletivaComando != null){

				imovelEmissaoOrdensSeletivas.setIdComandoOsServicoSeletiva(idosSeletivaComando.toString());

			}

			if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
							&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")
							&& imovelEmissaoOrdensSeletivas.getLocalidade() == null){

				String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

				if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

					osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
					osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

					GerenciaRegional gerenciaRegional = new GerenciaRegional();
					gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

					osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);
					osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

					fachada.inserir(osSeletivaComandoLocalGeo);

				}

			}
			if(imovelEmissaoOrdensSeletivas.getLocalidade() != null && imovelEmissaoOrdensSeletivas.getSetor() == null
							&& imovelEmissaoOrdensSeletivas.getBairro() == null){

				String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

				if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

					osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
					osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

					if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
									&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")){

						GerenciaRegional gerenciaRegional = new GerenciaRegional();
						gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

						osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);

					}

					Localidade localidade = new Localidade();
					idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];
					localidade.setId(Integer.parseInt(idLocalidade));

					osSeletivaComandoLocalGeo.setLocalidade(localidade);
					osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

					fachada.inserir(osSeletivaComandoLocalGeo);

				}
			}
			if(imovelEmissaoOrdensSeletivas.getSetor() != null && imovelEmissaoOrdensSeletivas.getRota() == null
							&& imovelEmissaoOrdensSeletivas.getQuadra() == null){

				for(String idSetor : imovelEmissaoOrdensSeletivas.getSetor()){

					if(!idSetor.equals(ConstantesSistema.INVALIDO_ID)){

						osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
						osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

						if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
										&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")){

							GerenciaRegional gerenciaRegional = new GerenciaRegional();
							gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

							osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);

						}

						String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

						if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

							Localidade localidade = new Localidade();
							idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];
							localidade.setId(Integer.parseInt(idLocalidade));

						}

						FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID, idSetor));

						Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

						SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

						osSeletivaComandoLocalGeo.setSetorComercial(setorComercial);
						osSeletivaComandoLocalGeo.setCodigoSetorComercial(setorComercial.getCodigo());
						osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLocalGeo);
					}
				}
			}

			if(imovelEmissaoOrdensSeletivas.getLote() != null){

				for(String idLote : imovelEmissaoOrdensSeletivas.getLote()){

					if(!idLote.equals(ConstantesSistema.INVALIDO_ID.toString())){

						osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
						osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

						if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
										&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")){

							GerenciaRegional gerenciaRegional = new GerenciaRegional();
							gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

							osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);

						}

						String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

						if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

							Localidade localidade = new Localidade();
							idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];
							localidade.setId(Integer.parseInt(idLocalidade));

						}

						if(imovelEmissaoOrdensSeletivas.getSetor() != null){

							FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
							filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID,
											imovelEmissaoOrdensSeletivas.getSetor()[0]));

							Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

							SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

							osSeletivaComandoLocalGeo.setSetorComercial(setorComercial);
							osSeletivaComandoLocalGeo.setCodigoSetorComercial(setorComercial.getCodigo());

						}

						if(imovelEmissaoOrdensSeletivas.getQuadra() != null){

							String idQuadra = imovelEmissaoOrdensSeletivas.getQuadra()[0];

							FiltroQuadra filtroQuadra = new FiltroQuadra();
							filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, idQuadra));
							Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

							Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

							osSeletivaComandoLocalGeo.setQuadra(quadra);
							osSeletivaComandoLocalGeo.setNumeroQuadra(quadra.getNumeroQuadra());

						}

						osSeletivaComandoLocalGeo.setNumeroLote(Integer.parseInt(idLote));
						osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLocalGeo);
					}
				}
			}else if(imovelEmissaoOrdensSeletivas.getQuadra() != null){

				for(String idQuadra : imovelEmissaoOrdensSeletivas.getQuadra()){

					if(!idQuadra.equals(ConstantesSistema.INVALIDO_ID.toString())){

						osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
						osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

						if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
										&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")){

							GerenciaRegional gerenciaRegional = new GerenciaRegional();
							gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

							osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);

						}

						String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

						if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

							Localidade localidade = new Localidade();
							idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];
							localidade.setId(Integer.parseInt(idLocalidade));

						}

						if(imovelEmissaoOrdensSeletivas.getSetor() != null){

							FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
							filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID,
											imovelEmissaoOrdensSeletivas.getSetor()[0]));

							Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

							SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

							osSeletivaComandoLocalGeo.setSetorComercial(setorComercial);
							osSeletivaComandoLocalGeo.setCodigoSetorComercial(setorComercial.getCodigo());

						}

						FiltroQuadra filtroQuadra = new FiltroQuadra();
						filtroQuadra.adicionarParametro(new ParametroSimples(FiltroQuadra.ID, idQuadra));
						Collection colecaoQuadra = fachada.pesquisar(filtroQuadra, Quadra.class.getName());

						Quadra quadra = (Quadra) Util.retonarObjetoDeColecao(colecaoQuadra);

						osSeletivaComandoLocalGeo.setQuadra(quadra);
						osSeletivaComandoLocalGeo.setNumeroQuadra(quadra.getNumeroQuadra());
						osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLocalGeo);
					}

				}
			}

			if(imovelEmissaoOrdensSeletivas.getRota() != null){

				for(String idRota : imovelEmissaoOrdensSeletivas.getRota()){

					if(!idRota.equals(ConstantesSistema.INVALIDO_ID.toString())){

						osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
						osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

						if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
										&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")){

							GerenciaRegional gerenciaRegional = new GerenciaRegional();
							gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

							osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);

						}

						String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

						if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

							Localidade localidade = new Localidade();
							idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];
							localidade.setId(Integer.parseInt(idLocalidade));

						}

						if(imovelEmissaoOrdensSeletivas.getSetor() != null){

							FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
							filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.ID,
											imovelEmissaoOrdensSeletivas.getSetor()[0]));

							Collection colecaoSetorComercial = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

							SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetorComercial);

							osSeletivaComandoLocalGeo.setSetorComercial(setorComercial);
							osSeletivaComandoLocalGeo.setCodigoSetorComercial(setorComercial.getCodigo());

						}

						FiltroRota filtroRota = new FiltroRota();
						filtroRota.adicionarParametro(new ParametroSimples(FiltroRota.ID_ROTA, idRota));

						Collection colecaoFiltroRota = fachada.pesquisar(filtroRota, Rota.class.getName());

						Rota rota = (Rota) Util.retonarObjetoDeColecao(colecaoFiltroRota);

						osSeletivaComandoLocalGeo.setRota(rota);
						osSeletivaComandoLocalGeo.setCodigoRota(Integer.parseInt(rota.getCodigo().toString()));
						osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLocalGeo);

					}
				}

			}

			if(imovelEmissaoOrdensSeletivas.getLogradouro() != null){

				for(String idLogradouro : imovelEmissaoOrdensSeletivas.getLogradouro()){

					if(!idLogradouro.equals(ConstantesSistema.INVALIDO_ID.toString())){

						osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
						osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

						if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
										&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")){

							GerenciaRegional gerenciaRegional = new GerenciaRegional();
							gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

							osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);

						}

						String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

						if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

							Localidade localidade = new Localidade();
							idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];
							localidade.setId(Integer.parseInt(idLocalidade));

						}

						if(imovelEmissaoOrdensSeletivas.getBairro() != null
										&& !imovelEmissaoOrdensSeletivas.getBairro().toString()
														.equals(ConstantesSistema.INVALIDO_ID.toString())){

							FiltroBairro filtroBairro = new FiltroBairro();
							filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID,
											imovelEmissaoOrdensSeletivas.getBairro()[0]));

							Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

							Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

							osSeletivaComandoLocalGeo.setBairro(bairro);
							osSeletivaComandoLocalGeo.setCodigoBairro(bairro.getCodigoBairroPrefeitura());

						}

						FiltroLogradouro filtroLogradou = new FiltroLogradouro();
						filtroLogradou.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, idLogradouro));

						Collection colecaoLogradouro = fachada.pesquisar(filtroLogradou, Logradouro.class.getName());

						Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

						osSeletivaComandoLocalGeo.setLogradouro(logradouro);
						osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLocalGeo);
					}
				}
			}else if(imovelEmissaoOrdensSeletivas.getBairro() != null){

				for(String idBairro : imovelEmissaoOrdensSeletivas.getBairro()){

					if(!idBairro.equals(ConstantesSistema.INVALIDO_ID.toString())){

						osSeletivaComandoLocalGeo = new OsSeletivaComandoLocalGeo();
						osSeletivaComandoLocalGeo.setOsSeletivaComando(osSeletivaComando);

						if(imovelEmissaoOrdensSeletivas.getGerenciaRegional() != null
										&& !imovelEmissaoOrdensSeletivas.getGerenciaRegional().equals("-1")){

							GerenciaRegional gerenciaRegional = new GerenciaRegional();
							gerenciaRegional.setId(Integer.parseInt(imovelEmissaoOrdensSeletivas.getGerenciaRegional()));

							osSeletivaComandoLocalGeo.setGerenciaRegional(gerenciaRegional);

						}

						String idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];

						if(!idLocalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

							Localidade localidade = new Localidade();
							idLocalidade = imovelEmissaoOrdensSeletivas.getLocalidade()[0];
							localidade.setId(Integer.parseInt(idLocalidade));

						}

						if(imovelEmissaoOrdensSeletivas.getBairro() != null){

							FiltroBairro filtroBairro = new FiltroBairro();
							filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, idBairro));

							Collection colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

							Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

							osSeletivaComandoLocalGeo.setBairro(bairro);
							osSeletivaComandoLocalGeo.setCodigoBairro(bairro.getCodigoBairroPrefeitura());

						}

						osSeletivaComandoLocalGeo.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLocalGeo);
					}
				}
			}
			if(imovelEmissaoOrdensSeletivas.getPerfilImovel() != null){

				for(String idPerfilImovel : imovelEmissaoOrdensSeletivas.getPerfilImovel()){

					if(!idPerfilImovel.equals(ConstantesSistema.INVALIDO_ID.toString())){

						OsSeletivaComandoImovelPerfil osSeletivaComandoImovelPerfil = new OsSeletivaComandoImovelPerfil();
						osSeletivaComandoImovelPerfil.setOsSeletivaComando(osSeletivaComando);

						FiltroImovelPerfil filtroImovelPerfil = new FiltroImovelPerfil();
						filtroImovelPerfil.adicionarParametro(new ParametroSimples(FiltroImovelPerfil.ID, idPerfilImovel));
						Collection colecaoImovelPerfil = fachada.pesquisar(filtroImovelPerfil, ImovelPerfil.class.getName());

						ImovelPerfil imovelPerfil = (ImovelPerfil) Util.retonarObjetoDeColecao(colecaoImovelPerfil);

						osSeletivaComandoImovelPerfil.setImovelPerfil(imovelPerfil);
						osSeletivaComandoImovelPerfil.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoImovelPerfil);

					}

				}

			}
			if(imovelEmissaoOrdensSeletivas.getSubCategoria() != null){

				for(String idSubcategoria : imovelEmissaoOrdensSeletivas.getSubCategoria()){

					if(!idSubcategoria.equals(ConstantesSistema.INVALIDO_ID.toString())){

						OsSeletivaComandoCategoriaSubcategoria osSeletivaComandoCategoriaSubcategoria = new OsSeletivaComandoCategoriaSubcategoria();

						osSeletivaComandoCategoriaSubcategoria.setOsSeletivaComando(osSeletivaComando);

						FiltroSubCategoria filtroSubCategoria = new FiltroSubCategoria();
						filtroSubCategoria.adicionarParametro(new ParametroSimples(FiltroSubCategoria.ID, idSubcategoria));

						Collection colecaoSubCategoria = fachada.pesquisar(filtroSubCategoria, Subcategoria.class.getName());

						Subcategoria subcategoria = (Subcategoria) Util.retonarObjetoDeColecao(colecaoSubCategoria);

						osSeletivaComandoCategoriaSubcategoria.setSubCategoria(subcategoria);

						if(imovelEmissaoOrdensSeletivas.getCategoria() != null){

							String idCategoria = imovelEmissaoOrdensSeletivas.getCategoria()[0];

							if(!idCategoria.equals(ConstantesSistema.INVALIDO_ID.toString())){

								FiltroCategoria filtroCategoria = new FiltroCategoria();
								filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

								Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
								Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoCategoria);

								osSeletivaComandoCategoriaSubcategoria.setCategoria(categoria);
							}
						}

						osSeletivaComandoCategoriaSubcategoria.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoCategoriaSubcategoria);

					}
				}
			}else if(imovelEmissaoOrdensSeletivas.getCategoria() != null){

				for(String idCategoria : imovelEmissaoOrdensSeletivas.getCategoria()){

					if(!idCategoria.equals(ConstantesSistema.INVALIDO_ID.toString())){

						OsSeletivaComandoCategoriaSubcategoria osSeletivaComandoCategoriaSubcategoria = new OsSeletivaComandoCategoriaSubcategoria();

						osSeletivaComandoCategoriaSubcategoria.setOsSeletivaComando(osSeletivaComando);

						FiltroCategoria filtroCategoria = new FiltroCategoria();
						filtroCategoria.adicionarParametro(new ParametroSimples(FiltroCategoria.CODIGO, idCategoria));

						Collection colecaoCategoria = fachada.pesquisar(filtroCategoria, Categoria.class.getName());
						Categoria categoria = (Categoria) Util.retonarObjetoDeColecao(colecaoCategoria);

						osSeletivaComandoCategoriaSubcategoria.setUltimaAlteracao(new Date());
						osSeletivaComandoCategoriaSubcategoria.setCategoria(categoria);

						fachada.inserir(osSeletivaComandoCategoriaSubcategoria);

					}
				}
			}

			if(imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao() != null){

				for(String idSituacaoLigacaoAgua : imovelEmissaoOrdensSeletivas.getLigacaoAguaSituacao()){

					if(!idSituacaoLigacaoAgua.equals(ConstantesSistema.INVALIDO_ID.toString())){

						OsSeletivaComandoLigacaoAgua osSeletivaComandoLigacaoAgua = new OsSeletivaComandoLigacaoAgua();

						osSeletivaComandoLigacaoAgua.setOsSeletivaComando(osSeletivaComando);

						FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = new FiltroLigacaoAguaSituacao();
						filtroLigacaoAguaSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID,
										idSituacaoLigacaoAgua));

						Collection colecaoLigacaoAguaSituacao = fachada.pesquisar(filtroLigacaoAguaSituacao,
										LigacaoAguaSituacao.class.getName());

						LigacaoAguaSituacao ligacaoAguaSituacao = (LigacaoAguaSituacao) Util
										.retonarObjetoDeColecao(colecaoLigacaoAguaSituacao);

						osSeletivaComandoLigacaoAgua.setLigacaoAguaSituacao(ligacaoAguaSituacao);
						osSeletivaComandoLigacaoAgua.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLigacaoAgua);

					}
				}
			}

			if(imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao() != null){

				for(String idSituacaoLigacaoEsgoto : imovelEmissaoOrdensSeletivas.getLigacaoEsgotoSituacao()){

					if(!idSituacaoLigacaoEsgoto.equals(ConstantesSistema.INVALIDO_ID.toString())){

						OsSeletivaComandoLigacaoEsgoto osSeletivaComandoLigacaoEsgoto = new OsSeletivaComandoLigacaoEsgoto();

						osSeletivaComandoLigacaoEsgoto.setOsSeletivaComando(osSeletivaComando);

						FiltroLigacaoEsgotoSituacao filtroLigacaoEsgotoSituacao = new FiltroLigacaoEsgotoSituacao();
						filtroLigacaoEsgotoSituacao.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgotoSituacao.ID,
										idSituacaoLigacaoEsgoto));

						Collection colecaoLigacaoEsgotoSituacao = fachada.pesquisar(filtroLigacaoEsgotoSituacao,
										LigacaoEsgotoSituacao.class.getName());

						LigacaoEsgotoSituacao ligacaoEsgotoSituacao = (LigacaoEsgotoSituacao) Util
										.retonarObjetoDeColecao(colecaoLigacaoEsgotoSituacao);

						osSeletivaComandoLigacaoEsgoto.setLigacaoEsgotoSituacao(ligacaoEsgotoSituacao);
						osSeletivaComandoLigacaoEsgoto.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLigacaoEsgoto);

					}
				}
			}

			if(imovelEmissaoOrdensSeletivas.getColecaoConsumoMedioImovel() != null){

				for(ConsumoMedioImovelHelper consumoMedioImovelHelper : imovelEmissaoOrdensSeletivas.getColecaoConsumoMedioImovel()){

					OsSeletivaComandoConsumoMedio osSeletivaComandoConsumoMedio = new OsSeletivaComandoConsumoMedio();

					osSeletivaComandoConsumoMedio.setOsSeletivaComando(osSeletivaComando);
					osSeletivaComandoConsumoMedio.setNumeroConsumoMedioInicial(consumoMedioImovelHelper.getConsumoMedioInicial());
					osSeletivaComandoConsumoMedio.setNumeroConsumoMedioFinal(consumoMedioImovelHelper.getConsumoMedioFinal());

					osSeletivaComandoConsumoMedio.setUltimaAlteracao(new Date());

					fachada.inserir(osSeletivaComandoConsumoMedio);

				}

			}

			if(imovelEmissaoOrdensSeletivas.getMarca() != null){

				for(String idMarca : imovelEmissaoOrdensSeletivas.getMarca()){

					if(!idMarca.equals(ConstantesSistema.INVALIDO_ID.toString())){

						OsSeletivaComandoHidrometroMarca osSeletivaComandoHidrometroMarca = new OsSeletivaComandoHidrometroMarca();

						FiltroHidrometroMarca filtroHidrometroMarca = new FiltroHidrometroMarca();
						filtroHidrometroMarca.adicionarParametro(new ParametroSimples(FiltroHidrometroMarca.ID, idMarca));

						Collection colecaoHidrometroMarca = fachada.pesquisar(filtroHidrometroMarca, HidrometroMarca.class.getName());

						HidrometroMarca hidrometroMarca = (HidrometroMarca) Util.retonarObjetoDeColecao(colecaoHidrometroMarca);

						osSeletivaComandoHidrometroMarca.setHidrometroMarca(hidrometroMarca);
						osSeletivaComandoHidrometroMarca.setOsSeletivaComando(osSeletivaComando);
						osSeletivaComandoHidrometroMarca.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoHidrometroMarca);
					}
				}

			}

			if(imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica() != null){

				for(String idClasse : imovelEmissaoOrdensSeletivas.getHidrometroClasseMetrologica()){

					if(!idClasse.equals(ConstantesSistema.INVALIDO_ID.toString())){

						OsSeletivaComandoHidrometroClasse osSeletivaComandoHidrometroClasse = new OsSeletivaComandoHidrometroClasse();

						FiltroHidrometroClasseMetrologica filtroHidrometroClasseMetrologica = new FiltroHidrometroClasseMetrologica();
						filtroHidrometroClasseMetrologica.adicionarParametro(new ParametroSimples(FiltroHidrometroClasseMetrologica.ID,
										idClasse));

						Collection colecaoHidrometroClasse = fachada.pesquisar(filtroHidrometroClasseMetrologica,
										HidrometroClasseMetrologica.class.getName());

						HidrometroClasseMetrologica hidrometroClasse = (HidrometroClasseMetrologica) Util
										.retonarObjetoDeColecao(colecaoHidrometroClasse);

						osSeletivaComandoHidrometroClasse.setHidrometroClasseMetrologica(hidrometroClasse);
						osSeletivaComandoHidrometroClasse.setOsSeletivaComando(osSeletivaComando);
						osSeletivaComandoHidrometroClasse.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoHidrometroClasse);
					}
				}

			}
			if(imovelEmissaoOrdensSeletivas.getHidrometroProtecao() != null){

				for(String idHidrometroProtecao : imovelEmissaoOrdensSeletivas.getHidrometroProtecao()){

					if(!idHidrometroProtecao.equals(ConstantesSistema.INVALIDO_ID.toString())){

						FiltroHidrometroProtecao filtroHidrometroProtecao = new FiltroHidrometroProtecao();

						filtroHidrometroProtecao
										.adicionarParametro(new ParametroSimples(FiltroHidrometroProtecao.ID, idHidrometroProtecao));
						Collection colecaoHidrometroProtecao = fachada.pesquisar(filtroHidrometroProtecao,
										HidrometroProtecao.class.getName());

						HidrometroProtecao hidrometroProtecao = (HidrometroProtecao) Util.retonarObjetoDeColecao(colecaoHidrometroProtecao);

						OsSeletivaComandoHidrometroProtecao osSeletivaComandoHidrometroProtecao = new OsSeletivaComandoHidrometroProtecao();

						osSeletivaComandoHidrometroProtecao.setOsSeletivaComando(osSeletivaComando);
						osSeletivaComandoHidrometroProtecao.setHidrometroProtecao(hidrometroProtecao);
						osSeletivaComandoHidrometroProtecao.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoHidrometroProtecao);

					}
				}

			}

			if(imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao() != null){

				for(String idHidrometroLocalInstalacao : imovelEmissaoOrdensSeletivas.getHidrometroLocalInstalacao()){

					if(!idHidrometroLocalInstalacao.equals(ConstantesSistema.INVALIDO_ID.toString())){

						FiltroHidrometroLocalInstalacao filtroHidrometroLocalInstalacao = new FiltroHidrometroLocalInstalacao();

						filtroHidrometroLocalInstalacao.adicionarParametro(new ParametroSimples(FiltroHidrometroLocalInstalacao.ID,
										idHidrometroLocalInstalacao));

						Collection colecaoHidrometroLocalInstalacao = fachada.pesquisar(filtroHidrometroLocalInstalacao,
										HidrometroLocalInstalacao.class.getName());
						HidrometroLocalInstalacao hidrometroLocalInstalacao = (HidrometroLocalInstalacao) Util
										.retonarObjetoDeColecao(colecaoHidrometroLocalInstalacao);

						OsSeletivaComandoHidrometroLocalInstacao osSeletivaComandoHidrometroLocalInstacao = new OsSeletivaComandoHidrometroLocalInstacao();

						osSeletivaComandoHidrometroLocalInstacao.setOsSeletivaComando(osSeletivaComando);
						osSeletivaComandoHidrometroLocalInstacao.setHidrometroLocalInstalacao(hidrometroLocalInstalacao);
						osSeletivaComandoHidrometroLocalInstacao.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoHidrometroLocalInstacao);

					}

				}
			}

			if(imovelEmissaoOrdensSeletivas.getAnormalidadeHidrometro() != null){

				for(String idAnormalidadeHidrometroAnromalidade : imovelEmissaoOrdensSeletivas.getAnormalidadeHidrometro()){

					if(!idAnormalidadeHidrometroAnromalidade.equals(ConstantesSistema.INVALIDO_ID.toString())){

						FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();
						filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID,
										idAnormalidadeHidrometroAnromalidade));

						Collection colecaoLeituraAnormalidade = fachada.pesquisar(filtroLeituraAnormalidade,
										LeituraAnormalidade.class.getName());

						LeituraAnormalidade leituraAnormalidade = (LeituraAnormalidade) Util
										.retonarObjetoDeColecao(colecaoLeituraAnormalidade);

						OsSeletivaComandoLeituraAnormalidade osSeletivaComandoLeituraAnormalidade = new OsSeletivaComandoLeituraAnormalidade();

						osSeletivaComandoLeituraAnormalidade.setOsSeletivaComando(osSeletivaComando);
						osSeletivaComandoLeituraAnormalidade.setLeituraAnormalidade(leituraAnormalidade);
						osSeletivaComandoLeituraAnormalidade.setUltimaAlteracao(new Date());

						fachada.inserir(osSeletivaComandoLeituraAnormalidade);

					}
				}
			}

			if(imovelEmissaoOrdensSeletivas.getColecaoDadosDoHidrometro() != null
							&& !imovelEmissaoOrdensSeletivas.getColecaoDadosDoHidrometro().isEmpty()){

				for(DadosDoHidrometroHelper dadosHidrometro : imovelEmissaoOrdensSeletivas.getColecaoDadosDoHidrometro()){

					OsSeletivaComandoHidrometroDiametro osSeletivaComandoHidrometroDiametro = new OsSeletivaComandoHidrometroDiametro();

					osSeletivaComandoHidrometroDiametro.setOsSeletivaComando(osSeletivaComando);

					if(dadosHidrometro.getIdHidrometroDiametro() != null){

						HidrometroDiametro hidrometroDiametro = new HidrometroDiametro();

						FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
						filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.ID, dadosHidrometro
										.getIdHidrometroDiametro()));

						Collection colecaoHidrometroDiametro = fachada.pesquisar(filtroHidrometroDiametro,
										HidrometroDiametro.class.getName());

						hidrometroDiametro = (HidrometroDiametro) Util.retonarObjetoDeColecao(colecaoHidrometroDiametro);

						osSeletivaComandoHidrometroDiametro.setHidrometroDiametro(hidrometroDiametro);

					}

					if(dadosHidrometro.getIdHidrometroCapacidade() != null){

						HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();

						FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
						filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.ID, dadosHidrometro
										.getIdHidrometroCapacidade()));

						Collection colecaoHidrometroCapacidade = fachada.pesquisar(filtroHidrometroCapacidade,
										HidrometroCapacidade.class.getName());

						hidrometroCapacidade = (HidrometroCapacidade) Util.retonarObjetoDeColecao(colecaoHidrometroCapacidade);

						osSeletivaComandoHidrometroDiametro.setHidrometroCapacidade(hidrometroCapacidade);
					}

					if(dadosHidrometro.getIntervaloInstalacaoInicial() != null){

						String referencia = dadosHidrometro.getIntervaloInstalacaoInicial().replace("/", "");

						referencia = referencia.substring(2, 6) + referencia.substring(0, 2);

						osSeletivaComandoHidrometroDiametro.setReferenciaInicialInstalacaoHidrometro(Integer.parseInt(referencia));

					}

					if(dadosHidrometro.getIntervaloInstalacaoFinal() != null){

						String referencia = dadosHidrometro.getIntervaloInstalacaoFinal().replace("/", "");

						referencia = referencia.substring(2, 6) + referencia.substring(0, 2);

						osSeletivaComandoHidrometroDiametro.setReferenciaFinalInstalacaoHidrometro(Integer.parseInt(referencia));

					}

					osSeletivaComandoHidrometroDiametro.setUltimaAlteracao(new Date());

					fachada.inserir(osSeletivaComandoHidrometroDiametro);

				}
			}
		}

		return null;
	}
}