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

package gcom.relatorio.atendimentopublico;

import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * [UC1015] Relat�rio Resumo de Ordens de Servi�o Pendentes
 * 
 * @author Anderson Italo
 * @date 06/06/2011
 */
public class RelatorioResumoOrdensServicoPendentes
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioResumoOrdensServicoPendentes(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES);
	}

	@Deprecated
	public RelatorioResumoOrdensServicoPendentes() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// obt�m os par�metros passados
		FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro = (FiltrarRelatorioResumoOrdensServicoPendentesHelper) getParametro("filtroRelatorioHelper");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		Collection<RelatorioResumoOrdensServicoPendentesHelper> colecaoDadosRelatorio = fachada.pesquisaRelatorioResumoOSPendentes(filtro);

		// vari�veis utilizadas no loop
		Categoria categoria = new Categoria();
		Imovel imovel = new Imovel();
		HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = new HidrometroRelatorioOSHelper();
		Integer totalRegistros = 0;

		for(Iterator iterator = colecaoDadosRelatorio.iterator(); iterator.hasNext();){

			RelatorioResumoOrdensServicoPendentesHelper dados = (RelatorioResumoOrdensServicoPendentesHelper) iterator.next();

			RelatorioResumoOrdensServicoPendentesBean bean = new RelatorioResumoOrdensServicoPendentesBean();

			// Cabe�alho

			// 4.1 Ger�ncia Regional
			bean.setCodigoGerencia(dados.getIdGerenciaRegional().toString());
			bean.setNomeGerencia(dados.getNomeGerenciaRegional());

			// 4.2 Localidade
			bean.setCodigoLocalidade(dados.getIdLocalidade().toString());
			bean.setNomeLocalidade(dados.getDescricaoLocalidade());

			// 4.3 C�digo do Setor Comercial
			bean.setCodigoSetorComercial(String.valueOf(dados.getCodigoSetorComercial()));
			bean.setNomeSetorComercial(dados.getDescricaoSetorComercial().toString());

			// 4.4 Unidade de Negocio
			bean.setCodigoUnidadeNegocio(String.valueOf(dados.getIdUnidadeNegocio()));
			bean.setNomeUnidadeNegocio(String.valueOf(dados.getNomeUnidadeNegocio()));

			// 4.5 Tipo de Servi�o
			bean.setCodigoTipoServico(dados.getIdServicoTipo().toString());
			bean.setDescricaoTipoServico(dados.getDescricaoServicoTipo());

			// Linha Detalhe

			// 4.7 Data Emiss�o
			bean.setDataEmissao(dados.getDataEmissaoOSFormatada());

			// 4.8 N�mero da Ordem de Servi�o
			bean.setOrdemServico(dados.getIdOrdemServico().toString());

			// 4.9 Dados do Hidr�metro instalado na liga��o de �gua
			hidrometroRelatorioOSHelper = fachada.obterDadosHidrometroPorImovel(dados.getIdImovel());

			if(hidrometroRelatorioOSHelper != null){

				bean.setCapacidadeHidrometro(hidrometroRelatorioOSHelper.getHidrometroCapacidade());
				bean.setLocalInstalacaoHidrometro(hidrometroRelatorioOSHelper.getHidrometroLocal());
				bean.setMarcaHidrometro(hidrometroRelatorioOSHelper.getHidrometroMarca());
				bean.setNumeroHidrometro(hidrometroRelatorioOSHelper.getHidrometroNumero());

				if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro())){

					bean.setTipoHidrometro(hidrometroRelatorioOSHelper.getIdTipoHidrometro() + " - "
									+ hidrometroRelatorioOSHelper.getDescricaoTipoHidrometro());
				}

				if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro())){

					bean.setTipoProtecaoHidrometro(hidrometroRelatorioOSHelper.getIdProtecaoHidrometro() + " - "
									+ hidrometroRelatorioOSHelper.getDescricaoProtecaoHidrometro());
				}

				if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getHidrometroDiametro())){

					bean.setDiametroHidrometro(hidrometroRelatorioOSHelper.getHidrometroDiametro());
				}

				if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getIndicadorCavalete())){

					bean.setCavaleteHidrometro(hidrometroRelatorioOSHelper.getIndicadorCavalete());
				}

				if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper.getAnoFabricacaoHidrometro())){

					bean.setAnoFabricacaoHidrometro(hidrometroRelatorioOSHelper.getAnoFabricacaoHidrometro());
				}
			}

			hidrometroRelatorioOSHelper = null;

			// 4.10 Inscri��o do im�vel
			bean.setInscricao(dados.getInscricaoImovel());

			// 4.13
			bean.setEndereco(fachada.obterEnderecoCompletoOS(dados.getIdOrdemServico()));

			// 4.14 Categoria do Im�vel
			imovel.setId(dados.getIdImovel());
			categoria = fachada.obterDescricoesCategoriaImovel(imovel);
			bean.setCategoria(categoria.getDescricao());
			categoria = null;

			totalRegistros++;

			if(!iterator.hasNext()){

				// �ltima p�gina finaliza totalizando tudo
				bean.setTotalRegistros(totalRegistros);
			}

			relatorioBeans.add(bean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES, parametros, ds, tipoFormatoRelatorio);

		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro = (FiltrarRelatorioResumoOrdensServicoPendentesHelper) getParametro("filtroRelatorioHelper");
		retorno = Fachada.getInstancia().pesquisarTotalRegistrosRelatorioResumoOSPendentes(filtro);

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioResumoOrdensServicoPendentes", this);
	}

}