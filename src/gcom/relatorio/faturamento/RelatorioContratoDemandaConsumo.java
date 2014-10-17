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

package gcom.relatorio.faturamento;

import gcom.arrecadacao.ContratoDemandaConsumo;
import gcom.batch.Relatorio;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

/**
 * [] Gerar Relatório Maiores Devedores
 * 
 * @author Victon Malcolm
 * @since 13/11/2013
 */
public class RelatorioContratoDemandaConsumo
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioContratoDemandaConsumo(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTRATO_DEMANDA_CONSUMO);
	}

	@Deprecated
	public RelatorioContratoDemandaConsumo() {

		super(null, "");
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @return Descrição do retorno
	 * @exception RelatorioVazioException
	 *                Descrição da exceção
	 */
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Integer faturamentoGrupo = null;
		String[] localidades = null;
		Integer[] localidadesInt = null;
		String tipoContrato = null;
		Integer tarifaConsumo = null;
		Integer mesAnoFaturamentoInicial = null;
		Integer mesAnoFaturamentoFinal = null;
		Integer encerrado = null;

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		byte[] retorno = null;

		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		FaturamentoGrupo faturamentoGrupoFiltro;
		String localidadesFiltro = null;
		ConsumoTarifa tarifaContrato;
		String periodoFaturamentoFiltro = "";
		
		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());


		if(getParametro("faturamentoGrupo") != null && !getParametro("faturamentoGrupo").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			faturamentoGrupo = Integer.parseInt((String) getParametro("faturamentoGrupo"));

			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, faturamentoGrupo));

			Collection colecaoFaturamentoGrupo = fachada.pesquisar(filtroFaturamentoGrupo, FaturamentoGrupo.class.getName());
			
			if(colecaoFaturamentoGrupo != null && !colecaoFaturamentoGrupo.isEmpty()){

				faturamentoGrupoFiltro = (FaturamentoGrupo) Util.retonarObjetoDeColecao(colecaoFaturamentoGrupo);
				parametros.put("faturamentoGrupo", faturamentoGrupoFiltro.getDescricao());	
				
			}
		}
		if(getParametro("localidades") != null){

			localidades = (String[]) getParametro("localidades");

			localidadesInt = new Integer[localidades.length];

			int contador = 0;

			for(String id : localidades){

				localidadesInt[contador] = Integer.parseInt(id);

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, id));

				Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

				if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){

					Iterator iterator = colecaoLocalidades.iterator();

					while(iterator.hasNext()){

						Localidade localidade = (Localidade) iterator.next();

						if(localidade != null){

							if(localidadesFiltro == null){

								localidadesFiltro = localidade.getDescricao();

							}else{

							localidadesFiltro = localidadesFiltro + "," + localidade.getDescricao();
							}
						}else{

							localidadesFiltro = localidadesFiltro + "...";

						}
					}
				}
			}

			parametros.put("localidades", localidadesFiltro);
		}
		if(getParametro("tipoContrato") != null && !getParametro("tipoContrato").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			tipoContrato = (String) getParametro("tipoContrato");

			if(tipoContrato.equals(ConstantesSistema.CONFIRMADA)){

				parametros.put("tipoContratoFiltro", "Tarifa de Consumo");

			}else if(tipoContrato.equals(ConstantesSistema.NAO_CONFIRMADA)){

				parametros.put("tipoContratoFiltro", "Consumo Fixo");
			}
		}
		if(getParametro("tarifaConsumo") != null && !getParametro("tarifaConsumo").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			tarifaConsumo = Integer.parseInt((String) getParametro("tarifaConsumo"));

			FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

			filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.ID, tarifaConsumo));

			Collection colecaoTarifaConsumo = fachada.pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

			if(colecaoTarifaConsumo != null && !colecaoTarifaConsumo.isEmpty()){

				tarifaContrato = (ConsumoTarifa) Util.retonarObjetoDeColecao(colecaoTarifaConsumo);
				
				if(tarifaContrato != null){

					parametros.put("tarifaConsumoFiltro", tarifaContrato.getDescricao());

				}
			}
		}
		if(getParametro("mesAnoFaturamentoInicial") != null && !getParametro("mesAnoFaturamentoInicial").equals("")){

			String data = (String) getParametro("mesAnoFaturamentoInicial");

			periodoFaturamentoFiltro = data;

			mesAnoFaturamentoInicial = Util.formatarMesAnoComBarraParaAnoMes(data);

		}

		if(getParametro("mesAnoFaturamentoFinal") != null && !getParametro("mesAnoFaturamentoFinal").equals("")){

			String data = (String) getParametro("mesAnoFaturamentoFinal");

			periodoFaturamentoFiltro = periodoFaturamentoFiltro + " á " + data;

			mesAnoFaturamentoFinal = Util.formatarMesAnoComBarraParaAnoMes(data);

		}

		parametros.put("periodoFaturamentoFiltro", periodoFaturamentoFiltro);

		if(getParametro("encerrado") != null && !getParametro("encerrado").equals("")){

			encerrado = Integer.parseInt((String) getParametro("encerrado"));

			if(encerrado.toString().equals(ConstantesSistema.CONFIRMADA)){

				parametros.put("encerradoFiltro", "Sim");
			}else if(encerrado.toString().equals(ConstantesSistema.NAO_CONFIRMADA)){

				parametros.put("encerradoFiltro", "Não");
			}else{

				parametros.put("encerradoFiltro", "Ambos");
			}
		}
		

		RelatorioContratoDemandaConsumoBean relatorioBean = null;

		Collection<ContratoDemandaConsumo> colecaoRelatorioContratoDemandaConsumo = fachada.pesquisarDadosRelatorioContratoDemandaConsumo(
						faturamentoGrupo, localidadesInt, tipoContrato, tarifaConsumo, mesAnoFaturamentoInicial, mesAnoFaturamentoFinal,
						encerrado);
		
		if(colecaoRelatorioContratoDemandaConsumo != null && !colecaoRelatorioContratoDemandaConsumo.isEmpty()){

			Iterator iterator = colecaoRelatorioContratoDemandaConsumo.iterator();

			while(iterator.hasNext()){

				ContratoDemandaConsumo contratoDemandaConsumo=(ContratoDemandaConsumo) iterator.next();
				
				relatorioBean=new RelatorioContratoDemandaConsumoBean();
				
				if(contratoDemandaConsumo.getNumeroContrato() != null){

					relatorioBean.setContrato(contratoDemandaConsumo.getNumeroContrato().toString());
				}
				if(contratoDemandaConsumo.getImovel() != null){

					relatorioBean.setImovel(contratoDemandaConsumo.getImovel().getId().toString());
				}
				if(contratoDemandaConsumo.getAnoMesFaturamentoInicio() != null){

					String dataFormatada = Util.formatarAnoMesSemBarraParaMesAnoComBarra(contratoDemandaConsumo
									.getAnoMesFaturamentoInicio());

					relatorioBean.setMesAnoFaturamentoInicial(dataFormatada);

				}
				if(contratoDemandaConsumo.getAnoMesFaturamentoFim() != null){

					String dataFormatada = Util.formatarAnoMesSemBarraParaMesAnoComBarra(contratoDemandaConsumo.getAnoMesFaturamentoFim());

					relatorioBean.setMesAnoFaturamentoFinal(dataFormatada);

				}
				if(contratoDemandaConsumo.getNumeroConsumoFixo() != null){

					relatorioBean.setConsumoFixo(contratoDemandaConsumo.getNumeroConsumoFixo().toString());
				}
				if(contratoDemandaConsumo.getConsumoTarifa() != null){

					relatorioBean.setTarifaConsumo(contratoDemandaConsumo.getConsumoTarifa().getDescricao());
				}
				if(contratoDemandaConsumo.getContratoEncerrado() != null){

					relatorioBean.setEncerrado(contratoDemandaConsumo.getContratoEncerrado());
				}
				
				relatorioBeans.add(relatorioBean);
			}
		}

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTRATO_DEMANDA_CONSUMO, parametros, ds, tipoFormatoRelatorio);
		
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTRATO_DEMANDA_CONSUMO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		
		// retorna o relatório gerado

		return retorno;
	}
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContratoDemandaConsumo", this);
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		Fachada fachada = Fachada.getInstancia();

		Integer faturamentoGrupo = null;
		String[] localidades = null;
		Integer[] localidadesInt = null;
		String tipoContrato = null;
		Integer tarifaConsumo = null;
		Integer mesAnoFaturamentoInicial = null;
		Integer mesAnoFaturamentoFinal = null;
		Integer encerrado = null;

		if(getParametro("faturamentoGrupo") != null && !getParametro("faturamentoGrupo").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			faturamentoGrupo = Integer.parseInt((String) getParametro("faturamentoGrupo"));

		}
		if(getParametro("localidades") != null){

			localidades = (String[]) getParametro("localidades");

			localidadesInt = new Integer[localidades.length];

			for(int i = 0; i < localidades.length; i++){
				localidadesInt[i] = Integer.parseInt(localidades[i]);
			}

		}
		if(getParametro("tipoContrato") != null && !getParametro("tipoContrato").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			tipoContrato = (String) getParametro("tipoContrato");

		}
		if(getParametro("tarifaConsumo") != null && !getParametro("tarifaConsumo").equals(ConstantesSistema.NUMERO_NAO_INFORMADO)){

			tarifaConsumo = Integer.parseInt((String) getParametro("tarifaConsumo"));

		}
		if(getParametro("mesAnoFaturamentoInicial") != null && !getParametro("mesAnoFaturamentoInicial").equals("")){

			String data = (String) getParametro("mesAnoFaturamentoInicial");

			mesAnoFaturamentoInicial = Util.formatarMesAnoComBarraParaAnoMes(data);

		}

		if(getParametro("mesAnoFaturamentoFinal") != null && !getParametro("mesAnoFaturamentoFinal").equals("")){

			String data = (String) getParametro("mesAnoFaturamentoFinal");

			mesAnoFaturamentoFinal = Util.formatarMesAnoComBarraParaAnoMes(data);

		}

		if(getParametro("encerrado") != null && !getParametro("encerrado").equals("")){

			encerrado = Integer.parseInt((String) getParametro("encerrado"));

		}

		retorno = fachada.pesquisarDadosRelatorioContratoDemandaConsumoCount(faturamentoGrupo, localidadesInt, tipoContrato, tarifaConsumo,
						mesAnoFaturamentoInicial, mesAnoFaturamentoFinal, encerrado);

		return retorno;
	}
}