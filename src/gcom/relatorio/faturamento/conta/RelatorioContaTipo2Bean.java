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

package gcom.relatorio.faturamento.conta;

import gcom.faturamento.bean.EmitirContaTipo2Helper;
import gcom.relatorio.RelatorioBean;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC0352] Emitir Contas
 * 
 * @author Vivianne Sousa, Saulo Lima, Virgínia Melo
 * @date 28/06/2007, 09/10/2008, 12/03/2009
 * @author eduardo henrique
 * @date 06/05/2009
 *       Adição do indicador de impressão do Déb. Automático, Valores e Data de Vencimento
 */
public class RelatorioContaTipo2Bean
				implements RelatorioBean {

	private JRBeanCollectionDataSource arrayJRDetailConta1;

	private ArrayList arrayRelatorioContaTipo2DetailConta1Bean;

	private Collection colecaoContaLinhasDescricaoServicosTarifasTotalHelper1;

	private String matricula1;

	private String sequencialImpressao1;

	private String mesAnoConta1;

	private String nomeCliente1;

	private String endereco1;

	private String enderecoClienteEntrega1;

	private String inscLocalidade1;

	private String inscSetorComercial1;

	private String inscQuadra1;

	private String inscLote1;

	private String inscSubLote1;

	private String econResidencial1;

	private String econComercial1;

	private String econIndustrial1;

	private String econPublica1;

	private String tipoMedicao1;

	private String hidrometro1;

	private String dtLeituraAnterior1;

	private String dtLeituraAtual1;

	private String ligacaoAgua1;

	private String ligacaoEsgoto1;

	private String leituraAnterior1;

	private String leituraAtual1;

	private String mesAno1Conta1;

	private String consumo1Conta1;

	private String mesAno2Conta1;

	private String consumo2Conta1;

	private String mesAno3Conta1;

	private String consumo3Conta1;

	private String mesAno4Conta1;

	private String consumo4Conta1;

	private String mesAno5Conta1;

	private String consumo5Conta1;

	private String mesAno6Conta1;

	private String consumo6Conta1;

	private String consumoMedio1;

	private String msgConta1;

	private String qtdContasAviso1;

	private String dataVencimento1;

	private String valorTotalConta1;

	private String representacaoNumericaCodBarraFormatada1;

	private String representacaoNumericaCodBarraSemDigito1;

	private String inscricao1;

	private String rota1;

	private String consumoMedidoEstimado1;

	private String consumoFaturado1;

	private String anormalidade1;

	private String nomeBanco1;

	private String codigoAgencia1;

	private String idGrupoFaturamento1;

	private boolean indicadorCodigoBarras1;

	private boolean indicadorDebitoAutomatico1;

	private boolean indicadorValorConta1;

	private boolean indicadorVencimento1;

	private boolean indicadorFaturaInformativa1;

	private boolean indicadorFaturaInformativa2;

	private JRBeanCollectionDataSource arrayJRDetailConta2;

	private ArrayList arrayRelatorioContaTipo2DetailConta2Bean;

	private Collection colecaoContaLinhasDescricaoServicosTarifasTotalHelper2;

	private String matricula2;

	private String sequencialImpressao2;

	private String mesAnoConta2;

	private String nomeCliente2;

	private String endereco2;

	private String enderecoClienteEntrega2;

	private String inscLocalidade2;

	private String inscSetorComercial2;

	private String inscQuadra2;

	private String inscLote2;

	private String inscSubLote2;

	private String econResidencial2;

	private String econComercial2;

	private String econIndustrial2;

	private String econPublica2;

	private String tipoMedicao2;

	private String hidrometro2;

	private String dtLeituraAnterior2;

	private String dtLeituraAtual2;

	private String ligacaoAgua2;

	private String ligacaoEsgoto2;

	private String leituraAnterior2;

	private String leituraAtual2;

	private String mesAno1Conta2;

	private String consumo1Conta2;

	private String mesAno2Conta2;

	private String consumo2Conta2;

	private String mesAno3Conta2;

	private String consumo3Conta2;

	private String mesAno4Conta2;

	private String consumo4Conta2;

	private String mesAno5Conta2;

	private String consumo5Conta2;

	private String mesAno6Conta2;

	private String consumo6Conta2;

	private String consumoMedio2;

	private String msgConta2;

	private String qtdContasAviso2;

	private String dataVencimento2;

	private String valorTotalConta2;

	private String representacaoNumericaCodBarraFormatada2;

	private String representacaoNumericaCodBarraSemDigito2;

	private String inscricao2;

	private String rota2;

	private String consumoMedidoEstimado2;

	private String consumoFaturado2;

	private String anormalidade2;

	private String nomeBanco2;

	private String codigoAgencia2;

	private String idGrupoFaturamento2;

	private boolean indicadorCodigoBarras2;

	private boolean indicadorDebitoAutomatico2;

	private boolean indicadorValorConta2;

	private boolean indicadorVencimento2;

	private String totalContasImpressao;

	public RelatorioContaTipo2Bean(EmitirContaTipo2Helper emitirContaTipo2HelperConta1, EmitirContaTipo2Helper emitirContaTipo2HelperConta2) {

		this.arrayRelatorioContaTipo2DetailConta1Bean = new ArrayList();
		this.arrayRelatorioContaTipo2DetailConta1Bean.addAll(this.gerarDetail(emitirContaTipo2HelperConta1
						.getColecaoContaLinhasDescricaoServicosTarifasTotalHelper(), 1));
		this.arrayJRDetailConta1 = new JRBeanCollectionDataSource(this.arrayRelatorioContaTipo2DetailConta1Bean);
		if(emitirContaTipo2HelperConta1.getInscLocalidade() != null){
			this.inscLocalidade1 = "" + emitirContaTipo2HelperConta1.getInscLocalidade();
		}
		if(emitirContaTipo2HelperConta1.getInscSetorComercial() != null){
			this.inscSetorComercial1 = "" + emitirContaTipo2HelperConta1.getInscSetorComercial();
		}
		if(emitirContaTipo2HelperConta1.getInscQuadra() != null){
			this.inscQuadra1 = "" + emitirContaTipo2HelperConta1.getInscQuadra();
		}
		if(emitirContaTipo2HelperConta1.getInscLote() != null){
			this.inscLote1 = "" + emitirContaTipo2HelperConta1.getInscLote();
		}
		if(emitirContaTipo2HelperConta1.getInscSubLote() != null){
			this.inscSubLote1 = "" + emitirContaTipo2HelperConta1.getInscSubLote();
		}
		if(emitirContaTipo2HelperConta1.getEconResidencial() != null){
			this.econResidencial1 = "" + emitirContaTipo2HelperConta1.getEconResidencial();
		}
		if(emitirContaTipo2HelperConta1.getEconComercial() != null){
			this.econComercial1 = "" + emitirContaTipo2HelperConta1.getEconComercial();
		}
		if(emitirContaTipo2HelperConta1.getEconIndustrial() != null){
			this.econIndustrial1 = "" + emitirContaTipo2HelperConta1.getEconIndustrial();
		}
		if(emitirContaTipo2HelperConta1.getEconPublica() != null){
			this.econPublica1 = "" + emitirContaTipo2HelperConta1.getEconPublica();
		}
		if(emitirContaTipo2HelperConta1.getDescricaoLigacaoAguaSituacao() != null
						&& !emitirContaTipo2HelperConta1.getDescricaoLigacaoAguaSituacao().equals("")){
			this.ligacaoAgua1 = emitirContaTipo2HelperConta1.getDescricaoLigacaoAguaSituacao();
		}
		if(emitirContaTipo2HelperConta1.getDescricaoLigacaoEsgotoSituacao() != null
						&& !emitirContaTipo2HelperConta1.getDescricaoLigacaoEsgotoSituacao().equals("")){
			this.ligacaoEsgoto1 = emitirContaTipo2HelperConta1.getDescricaoLigacaoEsgotoSituacao();
		}
		if(emitirContaTipo2HelperConta1.getQtdContasAviso() != null){
			this.qtdContasAviso1 = "" + emitirContaTipo2HelperConta1.getQtdContasAviso();
		}
		if(emitirContaTipo2HelperConta1.getNomeCliente() != null && !emitirContaTipo2HelperConta1.getNomeCliente().equals("")){
			this.nomeCliente1 = emitirContaTipo2HelperConta1.getNomeCliente();
		}
		if(emitirContaTipo2HelperConta1.getEndereco() != null && !emitirContaTipo2HelperConta1.getEndereco().equals("")){
			this.endereco1 = emitirContaTipo2HelperConta1.getEndereco();
		}
		if(emitirContaTipo2HelperConta1.getEnderecoClienteEntrega() != null
						&& !emitirContaTipo2HelperConta1.getEnderecoClienteEntrega().equals("")){
			this.enderecoClienteEntrega1 = emitirContaTipo2HelperConta1.getEnderecoClienteEntrega();
		}
		if(emitirContaTipo2HelperConta1.getAnoMesConta() != null){
			this.mesAnoConta1 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta1.getAnoMesConta());
		}
		if(emitirContaTipo2HelperConta1.getDataVencimento() != null){
			this.dataVencimento1 = Util.formatarData(emitirContaTipo2HelperConta1.getDataVencimento());
		}
		if(emitirContaTipo2HelperConta1.getInscricao() != null && !emitirContaTipo2HelperConta1.getInscricao().equals("")){
			this.inscricao1 = emitirContaTipo2HelperConta1.getInscricao();
		}
		if(emitirContaTipo2HelperConta1.getIdImovel() != null){
			this.matricula1 = Util.inserirCaractereSeparador(emitirContaTipo2HelperConta1.getIdParametrizado());
		}
		if(emitirContaTipo2HelperConta1.getCodigoRota() != null && !emitirContaTipo2HelperConta1.getCodigoRota().equals("")){
			this.rota1 = "" + emitirContaTipo2HelperConta1.getCodigoRota();
		}
		if(emitirContaTipo2HelperConta1.getHidrometro() != null){
			this.hidrometro1 = "" + emitirContaTipo2HelperConta1.getHidrometro();
		}else{
			this.hidrometro1 = "NÃO MEDIDO";
		}
		if(emitirContaTipo2HelperConta1.getDescricaoTipoConsumo() != null
						&& !emitirContaTipo2HelperConta1.getDescricaoTipoConsumo().equals("")){
			this.tipoMedicao1 = emitirContaTipo2HelperConta1.getDescricaoTipoConsumo();
		}
		if(emitirContaTipo2HelperConta1.getDtLeituraAnterior() != null){
			this.dtLeituraAnterior1 = Util.formatarData(emitirContaTipo2HelperConta1.getDtLeituraAnterior());
		}
		if(emitirContaTipo2HelperConta1.getDtLeituraAtual() != null){
			this.dtLeituraAtual1 = Util.formatarData(emitirContaTipo2HelperConta1.getDtLeituraAtual());
		}
		if(emitirContaTipo2HelperConta1.getLeituraAnterior() != null){
			this.leituraAnterior1 = "" + emitirContaTipo2HelperConta1.getLeituraAnterior();
		}
		if(emitirContaTipo2HelperConta1.getLeituraAtual() != null){
			this.leituraAtual1 = "" + emitirContaTipo2HelperConta1.getLeituraAtual();
		}
		if(emitirContaTipo2HelperConta1.getConsumoMedio() != null){
			this.consumoMedio1 = "" + emitirContaTipo2HelperConta1.getConsumoMedio();
		}
		if(emitirContaTipo2HelperConta1.getAnoMes1Conta() != null && !"".equals(emitirContaTipo2HelperConta1.getAnoMes1Conta())){
			this.mesAno1Conta1 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta1.getAnoMes1Conta());
		}
		if(emitirContaTipo2HelperConta1.getConsumo1Conta() != null){
			this.consumo1Conta1 = "" + emitirContaTipo2HelperConta1.getConsumo1Conta();
		}
		if(emitirContaTipo2HelperConta1.getAnoMes2Conta() != null && !"".equals(emitirContaTipo2HelperConta1.getAnoMes2Conta())){
			this.mesAno2Conta1 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta1.getAnoMes2Conta());
		}
		if(emitirContaTipo2HelperConta1.getConsumo2Conta() != null){
			this.consumo2Conta1 = "" + emitirContaTipo2HelperConta1.getConsumo2Conta();
		}
		if(emitirContaTipo2HelperConta1.getAnoMes3Conta() != null && !"".equals(emitirContaTipo2HelperConta1.getAnoMes3Conta())){
			this.mesAno3Conta1 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta1.getAnoMes3Conta());
		}
		if(emitirContaTipo2HelperConta1.getConsumo3Conta() != null){
			this.consumo3Conta1 = "" + emitirContaTipo2HelperConta1.getConsumo3Conta();
		}
		if(emitirContaTipo2HelperConta1.getAnoMes4Conta() != null && !"".equals(emitirContaTipo2HelperConta1.getAnoMes4Conta())){
			this.mesAno4Conta1 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta1.getAnoMes4Conta());
		}
		if(emitirContaTipo2HelperConta1.getConsumo4Conta() != null){
			this.consumo4Conta1 = "" + emitirContaTipo2HelperConta1.getConsumo4Conta();
		}
		if(emitirContaTipo2HelperConta1.getAnoMes5Conta() != null && !"".equals(emitirContaTipo2HelperConta1.getAnoMes5Conta())){
			this.mesAno5Conta1 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta1.getAnoMes5Conta());
		}
		if(emitirContaTipo2HelperConta1.getConsumo5Conta() != null){
			this.consumo5Conta1 = "" + emitirContaTipo2HelperConta1.getConsumo5Conta();
		}
		if(emitirContaTipo2HelperConta1.getAnoMes6Conta() != null && !"".equals(emitirContaTipo2HelperConta1.getAnoMes6Conta())){
			this.mesAno6Conta1 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta1.getAnoMes6Conta());
		}
		if(emitirContaTipo2HelperConta1.getConsumo6Conta() != null){
			this.consumo6Conta1 = "" + emitirContaTipo2HelperConta1.getConsumo6Conta();
		}
		if(emitirContaTipo2HelperConta1.getMsgConta() != null && !emitirContaTipo2HelperConta1.getMsgConta().equals("")){
			this.msgConta1 = emitirContaTipo2HelperConta1.getMsgConta();
		}
		if(emitirContaTipo2HelperConta1.getRepresentacaoNumericaCodBarraFormatada() != null
						&& !emitirContaTipo2HelperConta1.getRepresentacaoNumericaCodBarraFormatada().equals("")){
			this.representacaoNumericaCodBarraFormatada1 = emitirContaTipo2HelperConta1.getRepresentacaoNumericaCodBarraFormatada();
		}
		if(emitirContaTipo2HelperConta1.getRepresentacaoNumericaCodBarraSemDigito() != null
						&& !emitirContaTipo2HelperConta1.getRepresentacaoNumericaCodBarraSemDigito().equals("")){
			this.representacaoNumericaCodBarraSemDigito1 = emitirContaTipo2HelperConta1.getRepresentacaoNumericaCodBarraSemDigito();
		}
		if(emitirContaTipo2HelperConta1.getValorTotalConta() != null){
			this.valorTotalConta1 = Util.formatarMoedaReal(emitirContaTipo2HelperConta1.getValorTotalConta());
		}
		if(emitirContaTipo2HelperConta1.getSequencialImpressao() != 0){
			this.sequencialImpressao1 = "" + emitirContaTipo2HelperConta1.getSequencialImpressao();
		}
		if(emitirContaTipo2HelperConta1.getConsumoMedidoEstimado() != null
						&& !emitirContaTipo2HelperConta1.getConsumoMedidoEstimado().equals("")){
			this.consumoMedidoEstimado1 = emitirContaTipo2HelperConta1.getConsumoMedidoEstimado();
		}
		if(emitirContaTipo2HelperConta1.getConsumoFaturado() != null){
			this.consumoFaturado1 = "" + emitirContaTipo2HelperConta1.getConsumoFaturado();
		}
		if(emitirContaTipo2HelperConta1.getDescricaoAnormalidadeConsumo() != null
						&& !emitirContaTipo2HelperConta1.getDescricaoAnormalidadeConsumo().equals("")){
			this.anormalidade1 = emitirContaTipo2HelperConta1.getDescricaoAnormalidadeConsumo();
		}
		if(emitirContaTipo2HelperConta1.getIndicadorCodigoBarras() != null
						&& emitirContaTipo2HelperConta1.getIndicadorCodigoBarras().equals(ConstantesSistema.SIM)){
			this.indicadorCodigoBarras1 = true;
		}else{
			this.indicadorCodigoBarras1 = false;
		}

		if(emitirContaTipo2HelperConta1.getIndicadorFaturaInformativa() != null
						&& emitirContaTipo2HelperConta1.getIndicadorFaturaInformativa().equals(ConstantesSistema.SIM)){
			this.indicadorFaturaInformativa1 = true;
		}else{
			this.indicadorFaturaInformativa1 = false;
		}

		if(emitirContaTipo2HelperConta1.getIndicadorDebitoAutomatico() != null
						&& emitirContaTipo2HelperConta1.getIndicadorDebitoAutomatico().equals(ConstantesSistema.SIM)){
			this.indicadorDebitoAutomatico1 = true;
			if(emitirContaTipo2HelperConta1.getCodigoAgencia() != null && !emitirContaTipo2HelperConta1.getCodigoAgencia().equals("")){
				this.codigoAgencia1 = emitirContaTipo2HelperConta1.getCodigoAgencia();
			}
			if(emitirContaTipo2HelperConta1.getNomeBanco() != null && !emitirContaTipo2HelperConta1.getNomeBanco().equals("")){
				this.nomeBanco1 = emitirContaTipo2HelperConta1.getNomeBanco();
			}

		}else{
			this.indicadorDebitoAutomatico1 = false;

		}
		if(emitirContaTipo2HelperConta1.getIndicadorValorConta() != null
						&& emitirContaTipo2HelperConta1.getIndicadorValorConta().equals(ConstantesSistema.SIM)){
			this.indicadorValorConta1 = true;

		}else{
			this.indicadorValorConta1 = false;
		}
		if(emitirContaTipo2HelperConta1.getIndicadorVencimentoConta() != null
						&& emitirContaTipo2HelperConta1.getIndicadorVencimentoConta().equals(ConstantesSistema.SIM)){
			this.indicadorVencimento1 = true;

		}else{
			this.indicadorVencimento1 = false;
		}
		if(emitirContaTipo2HelperConta1.getIdFaturamentoGrupo() != null && !emitirContaTipo2HelperConta1.getIdFaturamentoGrupo().equals("")){
			this.idGrupoFaturamento1 = "" + emitirContaTipo2HelperConta1.getIdFaturamentoGrupo();
		}
		if(emitirContaTipo2HelperConta1.getTotalContasImpressao() != 0){
			this.totalContasImpressao = "" + emitirContaTipo2HelperConta1.getTotalContasImpressao();
		}

		this.arrayRelatorioContaTipo2DetailConta2Bean = new ArrayList();

		this.arrayRelatorioContaTipo2DetailConta2Bean.addAll(this.gerarDetail(emitirContaTipo2HelperConta2
						.getColecaoContaLinhasDescricaoServicosTarifasTotalHelper(), 2));

		this.arrayJRDetailConta2 = new JRBeanCollectionDataSource(this.arrayRelatorioContaTipo2DetailConta2Bean);

		if(emitirContaTipo2HelperConta2.getInscLocalidade() != null){
			this.inscLocalidade2 = "" + emitirContaTipo2HelperConta2.getInscLocalidade();
		}
		if(emitirContaTipo2HelperConta2.getInscSetorComercial() != null){
			this.inscSetorComercial2 = "" + emitirContaTipo2HelperConta2.getInscSetorComercial();
		}
		if(emitirContaTipo2HelperConta2.getInscQuadra() != null){
			this.inscQuadra2 = "" + emitirContaTipo2HelperConta2.getInscQuadra();
		}
		if(emitirContaTipo2HelperConta2.getInscLote() != null){
			this.inscLote2 = "" + emitirContaTipo2HelperConta2.getInscLote();
		}
		if(emitirContaTipo2HelperConta2.getInscSubLote() != null){
			this.inscSubLote2 = "" + emitirContaTipo2HelperConta2.getInscSubLote();
		}
		if(emitirContaTipo2HelperConta2.getEconResidencial() != null){
			this.econResidencial2 = "" + emitirContaTipo2HelperConta2.getEconResidencial();
		}
		if(emitirContaTipo2HelperConta2.getEconComercial() != null){
			this.econComercial2 = "" + emitirContaTipo2HelperConta2.getEconComercial();
		}
		if(emitirContaTipo2HelperConta2.getEconIndustrial() != null){
			this.econIndustrial2 = "" + emitirContaTipo2HelperConta2.getEconIndustrial();
		}
		if(emitirContaTipo2HelperConta2.getEconPublica() != null){
			this.econPublica2 = "" + emitirContaTipo2HelperConta2.getEconPublica();
		}
		if(emitirContaTipo2HelperConta2.getDescricaoLigacaoAguaSituacao() != null
						&& !emitirContaTipo2HelperConta2.getDescricaoLigacaoAguaSituacao().equals("")){
			this.ligacaoAgua2 = emitirContaTipo2HelperConta2.getDescricaoLigacaoAguaSituacao();
		}
		if(emitirContaTipo2HelperConta2.getDescricaoLigacaoEsgotoSituacao() != null
						&& !emitirContaTipo2HelperConta2.getDescricaoLigacaoEsgotoSituacao().equals("")){
			this.ligacaoEsgoto2 = emitirContaTipo2HelperConta2.getDescricaoLigacaoEsgotoSituacao();
		}
		if(emitirContaTipo2HelperConta2.getQtdContasAviso() != null){
			this.qtdContasAviso2 = "" + emitirContaTipo2HelperConta2.getQtdContasAviso();
		}
		if(emitirContaTipo2HelperConta2.getNomeCliente() != null && !emitirContaTipo2HelperConta2.getNomeCliente().equals("")){
			this.nomeCliente2 = emitirContaTipo2HelperConta2.getNomeCliente();
		}
		if(emitirContaTipo2HelperConta2.getEndereco() != null && !emitirContaTipo2HelperConta2.getEndereco().equals("")){
			this.endereco2 = emitirContaTipo2HelperConta2.getEndereco();
		}
		if(emitirContaTipo2HelperConta2.getEnderecoClienteEntrega() != null
						&& !emitirContaTipo2HelperConta2.getEnderecoClienteEntrega().equals("")){
			this.enderecoClienteEntrega2 = emitirContaTipo2HelperConta2.getEnderecoClienteEntrega();
		}
		if(emitirContaTipo2HelperConta2.getAnoMesConta() != null){
			this.mesAnoConta2 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta2.getAnoMesConta());
		}
		if(emitirContaTipo2HelperConta2.getDataVencimento() != null){
			this.dataVencimento2 = Util.formatarData(emitirContaTipo2HelperConta2.getDataVencimento());
		}
		if(emitirContaTipo2HelperConta2.getInscricao() != null && !emitirContaTipo2HelperConta2.getInscricao().equals("")){
			this.inscricao2 = emitirContaTipo2HelperConta2.getInscricao();
		}
		if(emitirContaTipo2HelperConta2.getIdImovel() != null){
			this.matricula2 = Util.inserirCaractereSeparador(emitirContaTipo2HelperConta2.getIdParametrizado());
		}
		if(emitirContaTipo2HelperConta2.getCodigoRota() != null && !emitirContaTipo2HelperConta2.getCodigoRota().equals("")){
			this.rota2 = "" + emitirContaTipo2HelperConta2.getCodigoRota();
		}
		if(emitirContaTipo2HelperConta2.getHidrometro() != null){
			this.hidrometro2 = "" + emitirContaTipo2HelperConta2.getHidrometro();
		}else{
			this.hidrometro2 = "NÃO MEDIDO";
		}
		if(emitirContaTipo2HelperConta2.getDescricaoTipoConsumo() != null
						&& !emitirContaTipo2HelperConta2.getDescricaoTipoConsumo().equals("")){
			this.tipoMedicao2 = emitirContaTipo2HelperConta2.getDescricaoTipoConsumo();
		}
		if(emitirContaTipo2HelperConta2.getDtLeituraAnterior() != null){
			this.dtLeituraAnterior2 = Util.formatarData(emitirContaTipo2HelperConta2.getDtLeituraAnterior());
		}
		if(emitirContaTipo2HelperConta2.getDtLeituraAtual() != null){
			this.dtLeituraAtual2 = Util.formatarData(emitirContaTipo2HelperConta2.getDtLeituraAtual());
		}
		if(emitirContaTipo2HelperConta2.getLeituraAnterior() != null){
			this.leituraAnterior2 = "" + emitirContaTipo2HelperConta2.getLeituraAnterior();
		}
		if(emitirContaTipo2HelperConta2.getLeituraAtual() != null){
			this.leituraAtual2 = "" + emitirContaTipo2HelperConta2.getLeituraAtual();
		}
		if(emitirContaTipo2HelperConta2.getConsumoMedio() != null){
			this.consumoMedio2 = "" + emitirContaTipo2HelperConta2.getConsumoMedio();
		}
		if(emitirContaTipo2HelperConta2.getAnoMes1Conta() != null && !"".equals(emitirContaTipo2HelperConta2.getAnoMes1Conta())){
			this.mesAno1Conta2 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta2.getAnoMes1Conta());
		}
		if(emitirContaTipo2HelperConta2.getConsumo1Conta() != null){
			this.consumo1Conta2 = "" + emitirContaTipo2HelperConta2.getConsumo1Conta();
		}
		if(emitirContaTipo2HelperConta2.getAnoMes2Conta() != null && !"".equals(emitirContaTipo2HelperConta2.getAnoMes2Conta())){
			this.mesAno2Conta2 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta2.getAnoMes2Conta());
		}
		if(emitirContaTipo2HelperConta2.getConsumo2Conta() != null){
			this.consumo2Conta2 = "" + emitirContaTipo2HelperConta2.getConsumo2Conta();
		}
		if(emitirContaTipo2HelperConta2.getAnoMes3Conta() != null && !"".equals(emitirContaTipo2HelperConta2.getAnoMes3Conta())){
			this.mesAno3Conta2 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta2.getAnoMes3Conta());
		}
		if(emitirContaTipo2HelperConta2.getConsumo3Conta() != null){
			this.consumo3Conta2 = "" + emitirContaTipo2HelperConta2.getConsumo3Conta();
		}
		if(emitirContaTipo2HelperConta2.getAnoMes4Conta() != null && !"".equals(emitirContaTipo2HelperConta2.getAnoMes4Conta())){
			this.mesAno4Conta2 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta2.getAnoMes4Conta());
		}
		if(emitirContaTipo2HelperConta2.getConsumo4Conta() != null){
			this.consumo4Conta2 = "" + emitirContaTipo2HelperConta2.getConsumo4Conta();
		}
		if(emitirContaTipo2HelperConta2.getAnoMes5Conta() != null && !"".equals(emitirContaTipo2HelperConta2.getAnoMes5Conta())){
			this.mesAno5Conta2 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta2.getAnoMes5Conta());
		}
		if(emitirContaTipo2HelperConta2.getConsumo5Conta() != null){
			this.consumo5Conta2 = "" + emitirContaTipo2HelperConta2.getConsumo5Conta();
		}
		if(emitirContaTipo2HelperConta2.getAnoMes6Conta() != null && !"".equals(emitirContaTipo2HelperConta2.getAnoMes6Conta())){
			this.mesAno6Conta2 = Util.retornaDescricaoAnoMes4Digitos("" + emitirContaTipo2HelperConta2.getAnoMes6Conta());
		}
		if(emitirContaTipo2HelperConta2.getConsumo6Conta() != null){
			this.consumo6Conta2 = "" + emitirContaTipo2HelperConta2.getConsumo6Conta();
		}
		if(emitirContaTipo2HelperConta2.getMsgConta() != null && !emitirContaTipo2HelperConta2.getMsgConta().equals("")){
			this.msgConta2 = emitirContaTipo2HelperConta2.getMsgConta();
		}
		if(emitirContaTipo2HelperConta2.getRepresentacaoNumericaCodBarraFormatada() != null
						&& !emitirContaTipo2HelperConta2.getRepresentacaoNumericaCodBarraFormatada().equals("")){
			this.representacaoNumericaCodBarraFormatada2 = emitirContaTipo2HelperConta2.getRepresentacaoNumericaCodBarraFormatada();
		}
		if(emitirContaTipo2HelperConta2.getRepresentacaoNumericaCodBarraSemDigito() != null
						&& !emitirContaTipo2HelperConta2.getRepresentacaoNumericaCodBarraSemDigito().equals("")){
			this.representacaoNumericaCodBarraSemDigito2 = emitirContaTipo2HelperConta2.getRepresentacaoNumericaCodBarraSemDigito();
		}
		if(emitirContaTipo2HelperConta2.getValorTotalConta() != null){
			this.valorTotalConta2 = Util.formatarMoedaReal(emitirContaTipo2HelperConta2.getValorTotalConta());
		}
		if(emitirContaTipo2HelperConta2.getSequencialImpressao() != 0){
			this.sequencialImpressao2 = "" + emitirContaTipo2HelperConta2.getSequencialImpressao();
		}
		if(emitirContaTipo2HelperConta2.getConsumoMedidoEstimado() != null
						&& !emitirContaTipo2HelperConta2.getConsumoMedidoEstimado().equals("")){
			this.consumoMedidoEstimado2 = emitirContaTipo2HelperConta2.getConsumoMedidoEstimado();
		}
		if(emitirContaTipo2HelperConta2.getConsumoFaturado() != null){
			this.consumoFaturado2 = "" + emitirContaTipo2HelperConta2.getConsumoFaturado();
		}
		if(emitirContaTipo2HelperConta2.getDescricaoAnormalidadeConsumo() != null
						&& !emitirContaTipo2HelperConta2.getDescricaoAnormalidadeConsumo().equals("")){
			this.anormalidade2 = emitirContaTipo2HelperConta2.getDescricaoAnormalidadeConsumo();
		}
		if(emitirContaTipo2HelperConta2.getIndicadorCodigoBarras() != null
						&& emitirContaTipo2HelperConta2.getIndicadorCodigoBarras().equals(ConstantesSistema.SIM)){
			this.indicadorCodigoBarras2 = true;
		}else{
			this.indicadorCodigoBarras2 = false;
		}

		if(emitirContaTipo2HelperConta2.getIndicadorFaturaInformativa() != null
						&& emitirContaTipo2HelperConta2.getIndicadorFaturaInformativa().equals(ConstantesSistema.SIM)){
			this.indicadorFaturaInformativa2 = true;
		}else{
			this.indicadorFaturaInformativa2 = false;
		}

		if(emitirContaTipo2HelperConta2.getIndicadorDebitoAutomatico() != null
						&& emitirContaTipo2HelperConta2.getIndicadorDebitoAutomatico().equals(ConstantesSistema.SIM)){
			this.indicadorDebitoAutomatico2 = true;

			if(emitirContaTipo2HelperConta2.getCodigoAgencia() != null && !emitirContaTipo2HelperConta2.getCodigoAgencia().equals("")){
				this.codigoAgencia2 = emitirContaTipo2HelperConta2.getCodigoAgencia();
			}
			if(emitirContaTipo2HelperConta2.getNomeBanco() != null && !emitirContaTipo2HelperConta2.getNomeBanco().equals("")){
				this.nomeBanco2 = emitirContaTipo2HelperConta2.getNomeBanco();
			}

		}else{
			this.indicadorDebitoAutomatico2 = false;
		}

		if(emitirContaTipo2HelperConta2.getIndicadorValorConta() != null
						&& emitirContaTipo2HelperConta2.getIndicadorValorConta().equals(ConstantesSistema.SIM)){
			this.indicadorValorConta2 = true;

		}else{
			this.indicadorValorConta2 = false;
		}

		if(emitirContaTipo2HelperConta2.getIndicadorVencimentoConta() != null
						&& emitirContaTipo2HelperConta2.getIndicadorVencimentoConta().equals(ConstantesSistema.SIM)){
			this.indicadorVencimento2 = true;

		}else{
			this.indicadorVencimento2 = false;
		}

		if(emitirContaTipo2HelperConta2.getIdFaturamentoGrupo() != null && !emitirContaTipo2HelperConta2.getIdFaturamentoGrupo().equals("")){
			this.idGrupoFaturamento2 = "" + emitirContaTipo2HelperConta2.getIdFaturamentoGrupo();
		}
	}

	private Collection gerarDetail(Collection colecaoLinhasServicos, int tipoRelatorio){

		Collection colecaoDetail = new ArrayList();

		if(colecaoLinhasServicos != null && !colecaoLinhasServicos.isEmpty()){
			Iterator iterator = colecaoLinhasServicos.iterator();
			while(iterator.hasNext()){

				ContaLinhasDescricaoServicosTarifasTotalHelper linhasDescricaoServicosTarifasTotalHelper = (ContaLinhasDescricaoServicosTarifasTotalHelper) iterator
								.next();

				Object relatorio = null;

				if(tipoRelatorio == 1){
					relatorio = new RelatorioContaTipo2DetailConta1Bean(linhasDescricaoServicosTarifasTotalHelper
									.getDescricaoServicosTarifas(), linhasDescricaoServicosTarifasTotalHelper.getValor());

				}else{
					relatorio = new RelatorioContaTipo2DetailConta2Bean(linhasDescricaoServicosTarifasTotalHelper
									.getDescricaoServicosTarifas(), linhasDescricaoServicosTarifasTotalHelper.getValor());

				}

				colecaoDetail.add(relatorio);

			}
		}

		return colecaoDetail;
	}

	public String getMatricula1(){

		return matricula1;
	}

	public void setMatricula1(String matricula1){

		this.matricula1 = matricula1;
	}

	public String getMatricula2(){

		return matricula2;
	}

	public void setMatricula2(String matricula2){

		this.matricula2 = matricula2;
	}

	public JRBeanCollectionDataSource getArrayJRDetailConta1(){

		return arrayJRDetailConta1;
	}

	public void setArrayJRDetailConta1(JRBeanCollectionDataSource arrayJRDetailConta1){

		this.arrayJRDetailConta1 = arrayJRDetailConta1;
	}

	public JRBeanCollectionDataSource getArrayJRDetailConta2(){

		return arrayJRDetailConta2;
	}

	public void setArrayJRDetailConta2(JRBeanCollectionDataSource arrayJRDetailConta2){

		this.arrayJRDetailConta2 = arrayJRDetailConta2;
	}

	public ArrayList getArrayRelatorioContaTipo2DetailConta1Bean(){

		return arrayRelatorioContaTipo2DetailConta1Bean;
	}

	public void setArrayRelatorioContaTipo2DetailConta1Bean(ArrayList arrayRelatorioContaTipo2DetailConta1Bean){

		this.arrayRelatorioContaTipo2DetailConta1Bean = arrayRelatorioContaTipo2DetailConta1Bean;
	}

	public ArrayList getArrayRelatorioContaTipo2DetailConta2Bean(){

		return arrayRelatorioContaTipo2DetailConta2Bean;
	}

	public void setArrayRelatorioContaTipo2DetailConta2Bean(ArrayList arrayRelatorioContaTipo2DetailConta2Bean){

		this.arrayRelatorioContaTipo2DetailConta2Bean = arrayRelatorioContaTipo2DetailConta2Bean;
	}

	public Collection getColecaoContaLinhasDescricaoServicosTarifasTotalHelper1(){

		return colecaoContaLinhasDescricaoServicosTarifasTotalHelper1;
	}

	public void setColecaoContaLinhasDescricaoServicosTarifasTotalHelper1(Collection colecaoContaLinhasDescricaoServicosTarifasTotalHelper1){

		this.colecaoContaLinhasDescricaoServicosTarifasTotalHelper1 = colecaoContaLinhasDescricaoServicosTarifasTotalHelper1;
	}

	public Collection getColecaoContaLinhasDescricaoServicosTarifasTotalHelper2(){

		return colecaoContaLinhasDescricaoServicosTarifasTotalHelper2;
	}

	public void setColecaoContaLinhasDescricaoServicosTarifasTotalHelper2(Collection colecaoContaLinhasDescricaoServicosTarifasTotalHelper2){

		this.colecaoContaLinhasDescricaoServicosTarifasTotalHelper2 = colecaoContaLinhasDescricaoServicosTarifasTotalHelper2;
	}

	public String getConsumo1Conta1(){

		return consumo1Conta1;
	}

	public void setConsumo1Conta1(String consumo1Conta1){

		this.consumo1Conta1 = consumo1Conta1;
	}

	public String getConsumo1Conta2(){

		return consumo1Conta2;
	}

	public void setConsumo1Conta2(String consumo1Conta2){

		this.consumo1Conta2 = consumo1Conta2;
	}

	public String getConsumo2Conta1(){

		return consumo2Conta1;
	}

	public void setConsumo2Conta1(String consumo2Conta1){

		this.consumo2Conta1 = consumo2Conta1;
	}

	public String getConsumo2Conta2(){

		return consumo2Conta2;
	}

	public void setConsumo2Conta2(String consumo2Conta2){

		this.consumo2Conta2 = consumo2Conta2;
	}

	public String getConsumo3Conta1(){

		return consumo3Conta1;
	}

	public void setConsumo3Conta1(String consumo3Conta1){

		this.consumo3Conta1 = consumo3Conta1;
	}

	public String getConsumo3Conta2(){

		return consumo3Conta2;
	}

	public void setConsumo3Conta2(String consumo3Conta2){

		this.consumo3Conta2 = consumo3Conta2;
	}

	public String getConsumo4Conta1(){

		return consumo4Conta1;
	}

	public void setConsumo4Conta1(String consumo4Conta1){

		this.consumo4Conta1 = consumo4Conta1;
	}

	public String getConsumo4Conta2(){

		return consumo4Conta2;
	}

	public void setConsumo4Conta2(String consumo4Conta2){

		this.consumo4Conta2 = consumo4Conta2;
	}

	public String getConsumo5Conta1(){

		return consumo5Conta1;
	}

	public void setConsumo5Conta1(String consumo5Conta1){

		this.consumo5Conta1 = consumo5Conta1;
	}

	public String getConsumo5Conta2(){

		return consumo5Conta2;
	}

	public void setConsumo5Conta2(String consumo5Conta2){

		this.consumo5Conta2 = consumo5Conta2;
	}

	public String getConsumo6Conta1(){

		return consumo6Conta1;
	}

	public void setConsumo6Conta1(String consumo6Conta1){

		this.consumo6Conta1 = consumo6Conta1;
	}

	public String getConsumo6Conta2(){

		return consumo6Conta2;
	}

	public void setConsumo6Conta2(String consumo6Conta2){

		this.consumo6Conta2 = consumo6Conta2;
	}

	public String getConsumoMedio1(){

		return consumoMedio1;
	}

	public void setConsumoMedio1(String consumoMedio1){

		this.consumoMedio1 = consumoMedio1;
	}

	public String getConsumoMedio2(){

		return consumoMedio2;
	}

	public void setConsumoMedio2(String consumoMedio2){

		this.consumoMedio2 = consumoMedio2;
	}

	public String getDataVencimento1(){

		return dataVencimento1;
	}

	public void setDataVencimento1(String dataVencimento1){

		this.dataVencimento1 = dataVencimento1;
	}

	public String getDataVencimento2(){

		return dataVencimento2;
	}

	public void setDataVencimento2(String dataVencimento2){

		this.dataVencimento2 = dataVencimento2;
	}

	public String getDtLeituraAnterior1(){

		return dtLeituraAnterior1;
	}

	public void setDtLeituraAnterior1(String dtLeituraAnterior1){

		this.dtLeituraAnterior1 = dtLeituraAnterior1;
	}

	public String getDtLeituraAnterior2(){

		return dtLeituraAnterior2;
	}

	public void setDtLeituraAnterior2(String dtLeituraAnterior2){

		this.dtLeituraAnterior2 = dtLeituraAnterior2;
	}

	public String getDtLeituraAtual1(){

		return dtLeituraAtual1;
	}

	public void setDtLeituraAtual1(String dtLeituraAtual1){

		this.dtLeituraAtual1 = dtLeituraAtual1;
	}

	public String getDtLeituraAtual2(){

		return dtLeituraAtual2;
	}

	public void setDtLeituraAtual2(String dtLeituraAtual2){

		this.dtLeituraAtual2 = dtLeituraAtual2;
	}

	public String getEndereco2(){

		return endereco2;
	}

	public void setEndereco2(String endereco2){

		this.endereco2 = endereco2;
	}

	public String getHidrometro1(){

		return hidrometro1;
	}

	public void setHidrometro1(String hidrometro1){

		this.hidrometro1 = hidrometro1;
	}

	public String getHidrometro2(){

		return hidrometro2;
	}

	public void setHidrometro2(String hidrometro2){

		this.hidrometro2 = hidrometro2;
	}

	public String getInscricao1(){

		return inscricao1;
	}

	public void setInscricao1(String inscricao1){

		this.inscricao1 = inscricao1;
	}

	public String getInscricao2(){

		return inscricao2;
	}

	public void setInscricao2(String inscricao2){

		this.inscricao2 = inscricao2;
	}

	public String getLeituraAnterior1(){

		return leituraAnterior1;
	}

	public void setLeituraAnterior1(String leituraAnterior1){

		this.leituraAnterior1 = leituraAnterior1;
	}

	public String getLeituraAnterior2(){

		return leituraAnterior2;
	}

	public void setLeituraAnterior2(String leituraAnterior2){

		this.leituraAnterior2 = leituraAnterior2;
	}

	public String getLeituraAtual1(){

		return leituraAtual1;
	}

	public void setLeituraAtual1(String leituraAtual1){

		this.leituraAtual1 = leituraAtual1;
	}

	public String getLeituraAtual2(){

		return leituraAtual2;
	}

	public void setLeituraAtual2(String leituraAtual2){

		this.leituraAtual2 = leituraAtual2;
	}

	public String getMesAno1Conta1(){

		return mesAno1Conta1;
	}

	public void setMesAno1Conta1(String mesAno1Conta1){

		this.mesAno1Conta1 = mesAno1Conta1;
	}

	public String getMesAno1Conta2(){

		return mesAno1Conta2;
	}

	public void setMesAno1Conta2(String mesAno1Conta2){

		this.mesAno1Conta2 = mesAno1Conta2;
	}

	public String getMesAno2Conta1(){

		return mesAno2Conta1;
	}

	public void setMesAno2Conta1(String mesAno2Conta1){

		this.mesAno2Conta1 = mesAno2Conta1;
	}

	public String getMesAno2Conta2(){

		return mesAno2Conta2;
	}

	public void setMesAno2Conta2(String mesAno2Conta2){

		this.mesAno2Conta2 = mesAno2Conta2;
	}

	public String getMesAno3Conta1(){

		return mesAno3Conta1;
	}

	public void setMesAno3Conta1(String mesAno3Conta1){

		this.mesAno3Conta1 = mesAno3Conta1;
	}

	public String getMesAno3Conta2(){

		return mesAno3Conta2;
	}

	public void setMesAno3Conta2(String mesAno3Conta2){

		this.mesAno3Conta2 = mesAno3Conta2;
	}

	public String getMesAno4Conta1(){

		return mesAno4Conta1;
	}

	public void setMesAno4Conta1(String mesAno4Conta1){

		this.mesAno4Conta1 = mesAno4Conta1;
	}

	public String getMesAno4Conta2(){

		return mesAno4Conta2;
	}

	public void setMesAno4Conta2(String mesAno4Conta2){

		this.mesAno4Conta2 = mesAno4Conta2;
	}

	public String getMesAno5Conta1(){

		return mesAno5Conta1;
	}

	public void setMesAno5Conta1(String mesAno5Conta1){

		this.mesAno5Conta1 = mesAno5Conta1;
	}

	public String getMesAno5Conta2(){

		return mesAno5Conta2;
	}

	public void setMesAno5Conta2(String mesAno5Conta2){

		this.mesAno5Conta2 = mesAno5Conta2;
	}

	public String getMesAno6Conta1(){

		return mesAno6Conta1;
	}

	public void setMesAno6Conta1(String mesAno6Conta1){

		this.mesAno6Conta1 = mesAno6Conta1;
	}

	public String getMesAno6Conta2(){

		return mesAno6Conta2;
	}

	public void setMesAno6Conta2(String mesAno6Conta2){

		this.mesAno6Conta2 = mesAno6Conta2;
	}

	public String getMesAnoConta1(){

		return mesAnoConta1;
	}

	public void setMesAnoConta1(String mesAnoConta1){

		this.mesAnoConta1 = mesAnoConta1;
	}

	public String getMesAnoConta2(){

		return mesAnoConta2;
	}

	public void setMesAnoConta2(String mesAnoConta2){

		this.mesAnoConta2 = mesAnoConta2;
	}

	public String getMsgConta1(){

		return msgConta1;
	}

	public void setMsgConta1(String msgConta1){

		this.msgConta1 = msgConta1;
	}

	public String getMsgConta2(){

		return msgConta2;
	}

	public void setMsgConta2(String msgConta2){

		this.msgConta2 = msgConta2;
	}

	public String getNomeCliente1(){

		return nomeCliente1;
	}

	public void setNomeCliente1(String nomeCliente1){

		this.nomeCliente1 = nomeCliente1;
	}

	public String getNomeCliente2(){

		return nomeCliente2;
	}

	public void setNomeCliente2(String nomeCliente2){

		this.nomeCliente2 = nomeCliente2;
	}

	public String getRepresentacaoNumericaCodBarraFormatada1(){

		return representacaoNumericaCodBarraFormatada1;
	}

	public void setRepresentacaoNumericaCodBarraFormatada1(String representacaoNumericaCodBarraFormatada1){

		this.representacaoNumericaCodBarraFormatada1 = representacaoNumericaCodBarraFormatada1;
	}

	public String getRepresentacaoNumericaCodBarraFormatada2(){

		return representacaoNumericaCodBarraFormatada2;
	}

	public void setRepresentacaoNumericaCodBarraFormatada2(String representacaoNumericaCodBarraFormatada2){

		this.representacaoNumericaCodBarraFormatada2 = representacaoNumericaCodBarraFormatada2;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito1(){

		return representacaoNumericaCodBarraSemDigito1;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito1(String representacaoNumericaCodBarraSemDigito1){

		this.representacaoNumericaCodBarraSemDigito1 = representacaoNumericaCodBarraSemDigito1;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito2(){

		return representacaoNumericaCodBarraSemDigito2;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito2(String representacaoNumericaCodBarraSemDigito2){

		this.representacaoNumericaCodBarraSemDigito2 = representacaoNumericaCodBarraSemDigito2;
	}

	public String getRota1(){

		return rota1;
	}

	public void setRota1(String rota1){

		this.rota1 = rota1;
	}

	public String getRota2(){

		return rota2;
	}

	public void setRota2(String rota2){

		this.rota2 = rota2;
	}

	public String getValorTotalConta1(){

		return valorTotalConta1;
	}

	public void setValorTotalConta1(String valorTotalConta1){

		this.valorTotalConta1 = valorTotalConta1;
	}

	public String getValorTotalConta2(){

		return valorTotalConta2;
	}

	public void setValorTotalConta2(String valorTotalConta2){

		this.valorTotalConta2 = valorTotalConta2;
	}

	public String getSequencialImpressao1(){

		return sequencialImpressao1;
	}

	public void setSequencialImpressao1(String sequencialImpressao1){

		this.sequencialImpressao1 = sequencialImpressao1;
	}

	public String getSequencialImpressao2(){

		return sequencialImpressao2;
	}

	public void setSequencialImpressao2(String sequencialImpressao2){

		this.sequencialImpressao2 = sequencialImpressao2;
	}

	public String getInscLocalidade1(){

		return inscLocalidade1;
	}

	public void setInscLocalidade1(String inscLocalidade1){

		this.inscLocalidade1 = inscLocalidade1;
	}

	public String getInscSetorComercial1(){

		return inscSetorComercial1;
	}

	public void setInscSetorComercial1(String inscSetorComercial1){

		this.inscSetorComercial1 = inscSetorComercial1;
	}

	public String getInscQuadra1(){

		return inscQuadra1;
	}

	public void setInscQuadra1(String inscQuadra1){

		this.inscQuadra1 = inscQuadra1;
	}

	public String getInscLote1(){

		return inscLote1;
	}

	public void setInscLote1(String inscLote1){

		this.inscLote1 = inscLote1;
	}

	public String getInscSubLote1(){

		return inscSubLote1;
	}

	public void setInscSubLote1(String inscSubLote1){

		this.inscSubLote1 = inscSubLote1;
	}

	public String getEconResidencial1(){

		return econResidencial1;
	}

	public void setEconResidencial1(String econResidencial1){

		this.econResidencial1 = econResidencial1;
	}

	public String getEconComercial1(){

		return econComercial1;
	}

	public void setEconComercial1(String econComercial1){

		this.econComercial1 = econComercial1;
	}

	public String getEconIndustrial1(){

		return econIndustrial1;
	}

	public String getEndereco1(){

		return endereco1;
	}

	public void setEndereco1(String endereco1){

		this.endereco1 = endereco1;
	}

	public String getEnderecoClienteEntrega1(){

		return enderecoClienteEntrega1;
	}

	public void setEnderecoClienteEntrega1(String enderecoClienteEntrega1){

		this.enderecoClienteEntrega1 = enderecoClienteEntrega1;
	}

	public String getEconPublica2(){

		return econPublica2;
	}

	public void setEconPublica2(String econPublica2){

		this.econPublica2 = econPublica2;
	}

	public String getTipoMedicao2(){

		return tipoMedicao2;
	}

	public void setTipoMedicao2(String tipoMedicao2){

		this.tipoMedicao2 = tipoMedicao2;
	}

	public String getLigacaoAgua2(){

		return ligacaoAgua2;
	}

	public void setLigacaoAgua2(String ligacaoAgua2){

		this.ligacaoAgua2 = ligacaoAgua2;
	}

	public String getLigacaoEsgoto2(){

		return ligacaoEsgoto2;
	}

	public void setLigacaoEsgoto2(String ligacaoEsgoto2){

		this.ligacaoEsgoto2 = ligacaoEsgoto2;
	}

	public String getQtdContasAviso2(){

		return qtdContasAviso2;
	}

	public void setQtdContasAviso2(String qtdContasAviso2){

		this.qtdContasAviso2 = qtdContasAviso2;
	}

	public String getConsumoMedidoEstimado2(){

		return consumoMedidoEstimado2;
	}

	public void setConsumoMedidoEstimado2(String consumoMedidoEstimado2){

		this.consumoMedidoEstimado2 = consumoMedidoEstimado2;
	}

	public String getConsumoFaturado2(){

		return consumoFaturado2;
	}

	public void setConsumoFaturado2(String consumoFaturado2){

		this.consumoFaturado2 = consumoFaturado2;
	}

	public String getAnormalidade2(){

		return anormalidade2;
	}

	public void setAnormalidade2(String anormalidade2){

		this.anormalidade2 = anormalidade2;
	}

	public String getNomeBanco2(){

		return nomeBanco2;
	}

	public void setNomeBanco2(String nomeBanco2){

		this.nomeBanco2 = nomeBanco2;
	}

	public String getCodigoAgencia2(){

		return codigoAgencia2;
	}

	public void setCodigoAgencia2(String codigoAgencia2){

		this.codigoAgencia2 = codigoAgencia2;
	}

	public boolean getIndicadorCodigoBarras2(){

		return indicadorCodigoBarras2;
	}

	public void setIndicadorCodigoBarras2(boolean indicadorCodigoBarras2){

		this.indicadorCodigoBarras2 = indicadorCodigoBarras2;
	}

	public String getEnderecoClienteEntrega2(){

		return enderecoClienteEntrega2;
	}

	public void setEnderecoClienteEntrega2(String enderecoClienteEntrega2){

		this.enderecoClienteEntrega2 = enderecoClienteEntrega2;
	}

	public String getInscLocalidade2(){

		return inscLocalidade2;
	}

	public void setInscLocalidade2(String inscLocalidade2){

		this.inscLocalidade2 = inscLocalidade2;
	}

	public String getInscSetorComercial2(){

		return inscSetorComercial2;
	}

	public void setInscSetorComercial2(String inscSetorComercial2){

		this.inscSetorComercial2 = inscSetorComercial2;
	}

	public String getInscQuadra2(){

		return inscQuadra2;
	}

	public void setInscQuadra2(String inscQuadra2){

		this.inscQuadra2 = inscQuadra2;
	}

	public String getInscLote2(){

		return inscLote2;
	}

	public void setInscLote2(String inscLote2){

		this.inscLote2 = inscLote2;
	}

	public String getInscSubLote2(){

		return inscSubLote2;
	}

	public void setInscSubLote2(String inscSubLote2){

		this.inscSubLote2 = inscSubLote2;
	}

	public String getEconResidencial2(){

		return econResidencial2;
	}

	public void setEconResidencial2(String econResidencial2){

		this.econResidencial2 = econResidencial2;
	}

	public String getEconComercial2(){

		return econComercial2;
	}

	public void setEconComercial2(String econComercial2){

		this.econComercial2 = econComercial2;
	}

	public String getEconIndustrial2(){

		return econIndustrial2;
	}

	public void setEconIndustrial2(String econIndustrial2){

		this.econIndustrial2 = econIndustrial2;
	}

	public String getTipoMedicao1(){

		return tipoMedicao1;
	}

	public void setTipoMedicao1(String tipoMedicao1){

		this.tipoMedicao1 = tipoMedicao1;
	}

	public String getLigacaoAgua1(){

		return ligacaoAgua1;
	}

	public void setLigacaoAgua1(String ligacaoAgua1){

		this.ligacaoAgua1 = ligacaoAgua1;
	}

	public String getLigacaoEsgoto1(){

		return ligacaoEsgoto1;
	}

	public void setLigacaoEsgoto1(String ligacaoEsgoto1){

		this.ligacaoEsgoto1 = ligacaoEsgoto1;
	}

	public String getConsumoMedidoEstimado1(){

		return consumoMedidoEstimado1;
	}

	public void setConsumoMedidoEstimado1(String consumoMedidoEstimado1){

		this.consumoMedidoEstimado1 = consumoMedidoEstimado1;
	}

	public String getConsumoFaturado1(){

		return consumoFaturado1;
	}

	public void setConsumoFaturado1(String consumoFaturado1){

		this.consumoFaturado1 = consumoFaturado1;
	}

	public String getAnormalidade1(){

		return anormalidade1;
	}

	public void setAnormalidade1(String anormalidade1){

		this.anormalidade1 = anormalidade1;
	}

	public String getNomeBanco1(){

		return nomeBanco1;
	}

	public void setNomeBanco1(String nomeBanco1){

		this.nomeBanco1 = nomeBanco1;
	}

	public String getCodigoAgencia1(){

		return codigoAgencia1;
	}

	public void setCodigoAgencia1(String codigoAgencia1){

		this.codigoAgencia1 = codigoAgencia1;
	}

	public boolean getIndicadorCodigoBarras1(){

		return indicadorCodigoBarras1;
	}

	public void setIndicadorCodigoBarras1(boolean indicadorCodigoBarras1){

		this.indicadorCodigoBarras1 = indicadorCodigoBarras1;
	}

	public void setEconIndustrial1(String econIndustrial1){

		this.econIndustrial1 = econIndustrial1;
	}

	public String getEconPublica1(){

		return econPublica1;
	}

	public void setEconPublica1(String econPublica1){

		this.econPublica1 = econPublica1;
	}

	public String getQtdContasAviso1(){

		return qtdContasAviso1;
	}

	public void setQtdContasAviso1(String qtdContasAviso1){

		this.qtdContasAviso1 = qtdContasAviso1;
	}

	public String getIdGrupoFaturamento1(){

		return idGrupoFaturamento1;
	}

	public void setIdGrupoFaturamento1(String idGrupoFaturamento1){

		this.idGrupoFaturamento1 = idGrupoFaturamento1;
	}

	public String getIdGrupoFaturamento2(){

		return idGrupoFaturamento2;
	}

	public void setIdGrupoFaturamento2(String idGrupoFaturamento2){

		this.idGrupoFaturamento2 = idGrupoFaturamento2;
	}

	public String getTotalContasImpressao(){

		return totalContasImpressao;
	}

	public void setTotalContasImpressao(String totalContasImpressao){

		this.totalContasImpressao = totalContasImpressao;
	}

	public boolean getIndicadorDebitoAutomatico1(){

		return indicadorDebitoAutomatico1;
	}

	public void setIndicadorDebitoAutomatico1(boolean indicadorDebitoAutomatico1){

		this.indicadorDebitoAutomatico1 = indicadorDebitoAutomatico1;
	}

	public boolean getIndicadorValorConta1(){

		return indicadorValorConta1;
	}

	public void setIndicadorValorConta1(boolean indicadorValorConta1){

		this.indicadorValorConta1 = indicadorValorConta1;
	}

	public boolean getIndicadorVencimento1(){

		return indicadorVencimento1;
	}

	public void setIndicadorVencimento1(boolean indicadorVencimento1){

		this.indicadorVencimento1 = indicadorVencimento1;
	}

	public boolean getIndicadorDebitoAutomatico2(){

		return indicadorDebitoAutomatico2;
	}

	public void setIndicadorDebitoAutomatico2(boolean indicadorDebitoAutomatico2){

		this.indicadorDebitoAutomatico2 = indicadorDebitoAutomatico2;
	}

	public boolean getIndicadorValorConta2(){

		return indicadorValorConta2;
	}

	public void setIndicadorValorConta2(boolean indicadorValorConta2){

		this.indicadorValorConta2 = indicadorValorConta2;
	}

	public boolean getIndicadorVencimento2(){

		return indicadorVencimento2;
	}

	public void setIndicadorVencimento2(boolean indicadorVencimento2){

		this.indicadorVencimento2 = indicadorVencimento2;
	}

	public boolean getIndicadorFaturaInformativa1(){

		return indicadorFaturaInformativa1;
	}

	public void setIndicadorFaturaInformativa1(boolean indicadorFaturaInformativa1){

		this.indicadorFaturaInformativa1 = indicadorFaturaInformativa1;
	}

	public boolean getIndicadorFaturaInformativa2(){

		return indicadorFaturaInformativa2;
	}

	public void setIndicadorFaturaInformativa2(boolean indicadorFaturaInformativa2){

		this.indicadorFaturaInformativa2 = indicadorFaturaInformativa2;
	}

}
