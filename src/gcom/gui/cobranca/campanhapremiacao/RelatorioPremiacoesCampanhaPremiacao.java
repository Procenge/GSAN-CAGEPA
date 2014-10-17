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

package gcom.gui.cobranca.campanhapremiacao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.campanhapremiacao.CampanhaPremiacao;
import gcom.cobranca.campanhapremiacao.CampanhaPremio;
import gcom.cobranca.campanhapremiacao.FiltroCampanhaPremio;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

/**
 * classe respons�vel por criar o RelatorioPremiacoesCampanhaPremiacao
 * 
 * @author Hiroshi Gon�alves
 * @date 17/10/2013
 */
public class RelatorioPremiacoesCampanhaPremiacao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static final int UNID_PREM_GERENCIA_REGIONAL = 1;

	private static final int UNID_PREM_UNIDADE_NEGOCIO = 2;

	private static final int UNID_PREM_ELO = 3;

	private static final int UNID_PREM_LOCALIDADE = 4;

	private static final int UNID_PREM_GERAL = 5;

	public RelatorioPremiacoesCampanhaPremiacao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_PREMIACOES_CAMPANHA_PREMIACAO);
	}

	private Collection<RelatorioPremiacoesCampanhaPremiacaoBean> inicializarBeanRelatorio(Collection<CampanhaPremiacao> colCampanhaPremiacao){

		ArrayList retorno = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		RelatorioPremiacoesCampanhaPremiacaoBean bean = null;
		CampanhaPremio campanhaPremioAnterior = null;

		for(CampanhaPremiacao campanhaPremiacao : colCampanhaPremiacao){
			bean = new RelatorioPremiacoesCampanhaPremiacaoBean();
			
			bean.setDsTituloCampanha("");

			// Linha 1 (s� emitir esta linha uma vez por conjunto de premia��es da mesma unidade de premia��o):
			// if(CampanhaPremio.mudouGrupo(campanhaPremiacao.getCampanhaPremio(),
			// campanhaPremioAnterior)){
			String dsUnidadePremiacao = this.recuperarDsUnidadePremiacao(campanhaPremiacao.getCampanhaPremio(), false);

			bean.setDsUnidadePremiacao(dsUnidadePremiacao);
			
			// Quantidade de Pr�mios
			FiltroCampanhaPremio filtro = new FiltroCampanhaPremio();
			
			int tpUnidadePremiacao = identificarUnidadePremiacao(campanhaPremiacao.getCampanhaPremio());

			if(tpUnidadePremiacao == this.UNID_PREM_GERENCIA_REGIONAL){

				filtro.adicionarParametro(new ParametroSimples(FiltroCampanhaPremio.GERENCIA_REGIONAL_ID, campanhaPremiacao
								.getCampanhaPremio().getGerenciaRegional().getId()));
			}else if(tpUnidadePremiacao == this.UNID_PREM_UNIDADE_NEGOCIO){

				filtro.adicionarParametro(new ParametroSimples(FiltroCampanhaPremio.UNIDADE_NEGOCIO_ID, campanhaPremiacao
								.getCampanhaPremio().getUnidadeNegocio().getId()));
			}else if(tpUnidadePremiacao == this.UNID_PREM_ELO){

				filtro.adicionarParametro(new ParametroSimples(FiltroCampanhaPremio.ELO_ID, campanhaPremiacao.getCampanhaPremio()
								.getEloPremio().getId()));
			}else if(tpUnidadePremiacao == this.UNID_PREM_LOCALIDADE){

				filtro.adicionarParametro(new ParametroSimples(FiltroCampanhaPremio.LOCALIDADE_ID, campanhaPremiacao.getCampanhaPremio()
								.getLocalidade().getId()));
			}
			
			Collection<CampanhaPremio> colPremios = fachada.pesquisar(filtro, CampanhaPremio.class.getName());

			int qtdPremios = extrairQtdPremios(colPremios);

			bean.setQtdPremios(qtdPremios + "");
				
			// campanhaPremioAnterior = campanhaPremiacao.getCampanhaPremio();
			// }

			// Linha 2
			bean.setDsPremio(campanhaPremiacao.getCampanhaPremio().getDescricao());
			bean.setNuOrdemPremiacao(campanhaPremiacao.getCampanhaPremio().getNumeroOrdemPremiacao().toString());
			bean.setDtSorteio(campanhaPremiacao.getCampanhaSorteio().getDataSorteioFormatada());
			
			// Linha 3
			bean.setNuInscricao(campanhaPremiacao.getCampanhaCadastro().getNumeroInscricao());
			bean.setIdImovel(campanhaPremiacao.getCampanhaCadastro().getImovel().getId().toString());
			
			String dsUnidadePremiacaoLinha3 = this.recuperarDsUnidadePremiacao(campanhaPremiacao.getCampanhaPremio(), true);
			bean.setDsUnidadePremiacaoLinha3(dsUnidadePremiacaoLinha3);
			
			// Endere�o do Im�vel (<<Inclui>> [UC0085 - Obter Endere�o])
			bean.setDsEnderecoImovel(fachada.pesquisarEndereco(campanhaPremiacao.getCampanhaCadastro().getImovel().getId()));

			// Linha 4
			bean.setNmCliente(campanhaPremiacao.getCampanhaCadastro().getNomeCliente());
			bean.setDsEmailCliente(campanhaPremiacao.getCampanhaCadastro().getDsEmail());
			bean.setCdTipoRelacaoClienteImovel(campanhaPremiacao.getCampanhaCadastro().getCodigoTipoRelacaoClienteImovel().toString());

			if(campanhaPremiacao.getCampanhaCadastro().getNumeroCPF() != null
							&& !campanhaPremiacao.getCampanhaCadastro().getNumeroCPF().equals("")){

				bean.setNuCPF(campanhaPremiacao.getCampanhaCadastro().getNumeroCPF());
				bean.setNuRG(campanhaPremiacao.getCampanhaCadastro().getNumeroRG());
				bean.setDtEmissaoRG(Util.formatarData(campanhaPremiacao.getCampanhaCadastro().getDataRGEmissao()));
				bean.setOrgaoExpedidorRG(campanhaPremiacao.getCampanhaCadastro().getOrgaoExpedidorRG().getDescricao());
				bean.setEstadoRG(campanhaPremiacao.getCampanhaCadastro().getUnidadeFederacao().getSigla());
				bean.setDtNascimentoCliente(Util.formatarData(campanhaPremiacao.getCampanhaCadastro().getDataNascimento()));
				bean.setNmMaeCliente(campanhaPremiacao.getCampanhaCadastro().getNomeMae());

			}else{
				bean.setNuCNPJ(campanhaPremiacao.getCampanhaCadastro().getNumeroCNPJ());
			}

			// Linha 5 (s� emitir esta linha, caso a premia��o n�o esteja cancelada)
			if(campanhaPremiacao.getCampanhaPremiacaoMotCancel() == null 
							|| campanhaPremiacao.getCampanhaPremiacaoMotCancel().getId() == null){
				
				if(campanhaPremiacao.getDataRetiradaPremio() != null){
					bean.setDtRetiradaPremio(Util.formatarData(campanhaPremiacao.getDataRetiradaPremio()));
				}
				if(campanhaPremiacao.getUsuarioEntregaPremio() != null){
					bean.setNmUsuarioRespEntregaPremio(campanhaPremiacao.getUsuarioEntregaPremio().getNomeUsuario());
				}

			}else{
				// Linha 6 (s� emitir esta linha, caso a premia��o esteja cancelada)
				if(campanhaPremiacao.getDataCancelamentoPremiacao() != null){
					bean.setDtCancelamentoPremiacao(Util.formatarData(campanhaPremiacao.getDataCancelamentoPremiacao()));
				}
				if(campanhaPremiacao.getUsuarioCancelamentoPremiacao() != null){
					bean.setNmUsuarioRespCancelPremiacao(campanhaPremiacao.getUsuarioCancelamentoPremiacao().getNomeUsuario());
				}
				if(campanhaPremiacao.getCampanhaPremiacaoMotCancel() != null){
					bean.setDsMotivoCancelPremiacao(campanhaPremiacao.getCampanhaPremiacaoMotCancel().getDescricao());
				}
			}
			
			retorno.add(bean);
		}
		return retorno;
	}

	private String recuperarDsUnidadePremiacao(CampanhaPremio campanhaPremio, boolean isLinha3){

		String retorno = "";

		if(campanhaPremio.getGerenciaRegional() != null){
			if(isLinha3){
				retorno += "Ger. Regional " + campanhaPremio.getGerenciaRegional().getNomeAbreviado();
			}else{
				retorno += "Premia��o da Ger�ncia Regional: " + campanhaPremio.getGerenciaRegional().getNomeComId();
			}

		}else if(campanhaPremio.getUnidadeNegocio() != null){
			if(isLinha3){
				retorno += "Unid. Neg�cio " + campanhaPremio.getUnidadeNegocio().getNomeAbreviado();
			}else{
				retorno += "Premia��o da Unidade de Neg�cio: " + campanhaPremio.getUnidadeNegocio().getNomeComId();
			}

		}else if(campanhaPremio.getEloPremio() != null){
			if(isLinha3){
				retorno += "Elo " + campanhaPremio.getEloPremio().getDescricao();
			}else{
				retorno += "Premia��o do Elo: " + campanhaPremio.getEloPremio().getDescricaoComId();
			}

		}else if(campanhaPremio.getLocalidade() != null){
			if(isLinha3){
				retorno += "Localidade " + campanhaPremio.getLocalidade().getDescricao();
			}else{
				retorno += "Premia��o da Localidade: " + campanhaPremio.getLocalidade().getDescricaoComId();
			}

		}else{
			if(!isLinha3){
				retorno += "Premia��o Global";
			}

		}

		return retorno;
	}

	private int identificarUnidadePremiacao(CampanhaPremio campanhaPremio){

		int retorno = -1;

		if(campanhaPremio.getGerenciaRegional() != null){

			retorno = this.UNID_PREM_GERENCIA_REGIONAL;

		}else if(campanhaPremio.getUnidadeNegocio() != null){

			retorno = this.UNID_PREM_UNIDADE_NEGOCIO;

		}else if(campanhaPremio.getEloPremio() != null){

			retorno = this.UNID_PREM_ELO;

		}else if(campanhaPremio.getLocalidade() != null){

			retorno = this.UNID_PREM_LOCALIDADE;

		}else{

			retorno = this.UNID_PREM_GERAL;

		}

		return retorno;

	}

	private int extrairQtdPremios(Collection<CampanhaPremio> colPremios){

		int retorno = 0;

		for(CampanhaPremio premio : colPremios){
			retorno += premio.getQuantidadePremio();
		}

		return retorno;
	}

	/**
	 * M�todo que executa a tarefa
	 * 
	 * @return Object
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Collection<CampanhaPremiacao> colCampanhaPremiacao = (Collection<CampanhaPremiacao>) getParametro("colCampanhaPremiacao");

		if(colCampanhaPremiacao == null || colCampanhaPremiacao.isEmpty()){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}

		// valor de retorno
		byte[] retorno = null;

		Fachada fachada = Fachada.getInstancia();

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// Ordenar as premia��es por Unidade de Premia��o
		Collections.sort((List<CampanhaPremiacao>) colCampanhaPremiacao);

		Collection<RelatorioPremiacoesCampanhaPremiacaoBean> colCampanhaPremiacaoBean = this.inicializarBeanRelatorio(colCampanhaPremiacao);

		RelatorioDataSource ds = new RelatorioDataSource((List) colCampanhaPremiacaoBean);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_PREMIACOES_CAMPANHA_PREMIACAO, parametros, ds, tipoFormatoRelatorio);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_PREMIACOES_CAMPANHA_PREMIACAO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 1;

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioPremiacoesCampanhaPremiacao", this);
	}
}