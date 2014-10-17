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
 * Ivan Sérgio da Silva Júnior
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
 * [UC1015] Relatório Resumo de Ordens de Serviço Pendentes
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

		// obtêm os parâmetros passados
		FiltrarRelatorioResumoOrdensServicoPendentesHelper filtro = (FiltrarRelatorioResumoOrdensServicoPendentesHelper) getParametro("filtroRelatorioHelper");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// monta a coleção de beans do relatório
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		Collection<RelatorioResumoOrdensServicoPendentesHelper> colecaoDadosRelatorio = fachada.pesquisaRelatorioResumoOSPendentes(filtro);

		// variáveis utilizadas no loop
		Categoria categoria = new Categoria();
		Imovel imovel = new Imovel();
		HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = new HidrometroRelatorioOSHelper();
		Integer totalRegistros = 0;

		for(Iterator iterator = colecaoDadosRelatorio.iterator(); iterator.hasNext();){

			RelatorioResumoOrdensServicoPendentesHelper dados = (RelatorioResumoOrdensServicoPendentesHelper) iterator.next();

			RelatorioResumoOrdensServicoPendentesBean bean = new RelatorioResumoOrdensServicoPendentesBean();

			// Cabeçalho

			// 4.1 Gerência Regional
			bean.setCodigoGerencia(dados.getIdGerenciaRegional().toString());
			bean.setNomeGerencia(dados.getNomeGerenciaRegional());

			// 4.2 Localidade
			bean.setCodigoLocalidade(dados.getIdLocalidade().toString());
			bean.setNomeLocalidade(dados.getDescricaoLocalidade());

			// 4.3 Código do Setor Comercial
			bean.setCodigoSetorComercial(String.valueOf(dados.getCodigoSetorComercial()));
			bean.setNomeSetorComercial(dados.getDescricaoSetorComercial().toString());

			// 4.4 Unidade de Negocio
			bean.setCodigoUnidadeNegocio(String.valueOf(dados.getIdUnidadeNegocio()));
			bean.setNomeUnidadeNegocio(String.valueOf(dados.getNomeUnidadeNegocio()));

			// 4.5 Tipo de Serviço
			bean.setCodigoTipoServico(dados.getIdServicoTipo().toString());
			bean.setDescricaoTipoServico(dados.getDescricaoServicoTipo());

			// Linha Detalhe

			// 4.7 Data Emissão
			bean.setDataEmissao(dados.getDataEmissaoOSFormatada());

			// 4.8 Número da Ordem de Serviço
			bean.setOrdemServico(dados.getIdOrdemServico().toString());

			// 4.9 Dados do Hidrômetro instalado na ligação de água
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

			// 4.10 Inscrição do imóvel
			bean.setInscricao(dados.getInscricaoImovel());

			// 4.13
			bean.setEndereco(fachada.obterEnderecoCompletoOS(dados.getIdOrdemServico()));

			// 4.14 Categoria do Imóvel
			imovel.setId(dados.getIdImovel());
			categoria = fachada.obterDescricoesCategoriaImovel(imovel);
			bean.setCategoria(categoria.getDescricao());
			categoria = null;

			totalRegistros++;

			if(!iterator.hasNext()){

				// última página finaliza totalizando tudo
				bean.setTotalRegistros(totalRegistros);
			}

			relatorioBeans.add(bean);
		}

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(tipoFormatoRelatorio == TIPO_PDF){
			parametros.put("tipoFormatoRelatorio", "PDF");
		}

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// processa a geração do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES, parametros, ds, tipoFormatoRelatorio);

		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_RESUMO_ORDENS_SERVICO_PENDENTES, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// retorna o relatório gerado
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