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

import gcom.gerencial.bean.OrcamentoSINPHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

/**
 * classe responsável por criar o relatório de orçamento e SINP
 * 
 * @author Rafael Pinto
 * @created 22/11/2007
 */
public class RelatorioOrcamentoSINPBean
				implements RelatorioBean {

	private static final long serialVersionUID = 1L;

	private String opcaoTotalizacao;

	private String aguaTotalLigacoesCadastradas;

	private String aguaTotalLigacoesAtivas;

	private String aguaTotalLigacoesMedidas;

	private String aguaTotalLigacoesComHidrometro;

	private String aguaTotalLigacoesResidencialCadastradas;

	private String aguaTotalLigacoesDesligadas;

	private String aguaTotalEconomiasCadastradas;

	private String aguaTotalEconomiasAtivas;

	private String aguaTotalEconomiasAtivasMedidas;

	private String aguaTotalEconomiasResidencialCadastradas;

	private String aguaTotalEconomiasResidencialAtivasMicromedidas;

	private String aguaTotalEconomiasResidencialAtivas;

	private String aguaTotalEconomiasComercialAtivas;

	private String aguaTotalEconomiasIndustrialAtivas;

	private String aguaTotalEconomiasPublicoAtivas;

	private String aguaTotalEconomiasRuralAtivas;

	private String esgotoTotalLigacoesCadastradas;

	private String esgotoTotalLigacoesCadastradasConvencional;

	private String esgotoTotalLigacoesCadastradasCondominial;

	private String esgotoTotalLigacoesAtivasConvencional;

	private String esgotoTotalLigacoesAtivasCondominial;

	private String esgotoTotalLigacoesResidencialCadastradas;

	private String esgotoTotalEconomiasCadastradasConvencional;

	private String esgotoTotalEconomiasCadastradasCondominial;

	private String esgotoTotalEconomiasAtivasConvencional;

	private String esgotoTotalEconomiasAtivasCondominial;

	private String esgotoTotalEconomiasResidencialCadastradas;

	private String esgotoTotalEconomiasResidencialAtivas;

	private String esgotoTotalEconomiasComercialAtivas;

	private String esgotoTotalEconomiasIndustrialAtivas;

	private String esgotoTotalEconomiasPublicoAtivas;

	private String aguaTotalVolumeFaturadoResidencial;

	private String aguaTotalVolumeFaturadoComercial;

	private String aguaTotalVolumeFaturadoIndustrial;

	private String aguaTotalVolumeFaturadoPublico;

	private String aguaTotalVolumeFaturadoRural;

	private String aguaTotalVolumeFaturadoGeral;

	private String aguaTotalVolumeFaturadoMedido;

	private String aguaTotalVolumeFaturadoEstimado;

	private String esgotoTotalVolumeFaturadoIndustrial;

	private String esgotoTotalVolumeFaturadoPublico;

	private String esgotoTotalVolumeFaturadoResidencial;

	private String esgotoTotalVolumeFaturadoComercial;

	private String esgotoTotalVolumeFaturadoGeral;

	private String aguaTotalFaturadoResidencial;

	private String aguaTotalFaturadoComercial;

	private String aguaTotalFaturadoIndustrial;

	private String aguaTotalFaturadoPublico;

	private String aguaTotalFaturadoDireto;

	private String aguaTotalFaturadoIndireto;

	private String esgotoTotalFaturadoResidencial;

	private String esgotoTotalFaturadoComercial;

	private String esgotoTotalFaturadoIndustrial;

	private String esgotoTotalFaturadoPublico;

	private String esgotoTotalFaturadoDireto;

	private String esgotoTotalFaturadoIndireto;

	private String devolucao;

	private String aguaTotalLigacoesNovas;

	private String esgotoTotalLigacoesNovas;

	private String aguaTotalVolumeFaturadoMicroMedido;

	private String aguaTotalVolumeFaturadoEconomiasResidenciasAtivas;

	private String totalArrecadacaoMesAtual;

	private String totalArrecadacaoMesAnterior;

	private String totalFaturamentoLiquido;

	private String totalFaturamentoGeral;

	private String totalArrecadacaoGeral;

	private String aguaTotalFaturamentoGeralDI;

	private String esgotoTotalFaturamentoGeralDI;

	private String aguaTotalLigacoesSuprimidas;

	private String aguaTotalVolumeFaturadoConsumido;

	private String aguaTotalVolumeFaturado;

	private String esgotoTotalLigacoesAtivas;

	private String esgotoTotalEconomiasAtivas;

	private String esgotoTotalEconomiasCadastradas;

	private String receitaOperacionalTotal;

	private String receitaOperacionalDireta;

	private String receitaOperacionalIndireta;

	private String saldoContasReceber;

	public RelatorioOrcamentoSINPBean(OrcamentoSINPHelper orcamento) {

		this.opcaoTotalizacao = orcamento.getOpcaoTotalizacao();
		this.aguaTotalLigacoesCadastradas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesCadastradas());
		this.esgotoTotalLigacoesCadastradas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalLigacoesCadastradas());
		this.esgotoTotalLigacoesCadastradasConvencional = Util.agruparNumeroEmMilhares(orcamento
						.getEsgotoTotalLigacoesCadastradasConvencional());
		this.aguaTotalLigacoesAtivas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesAtivas());
		this.esgotoTotalLigacoesCadastradasCondominial = Util.agruparNumeroEmMilhares(orcamento
						.getEsgotoTotalLigacoesCadastradasCondominial());
		this.aguaTotalLigacoesMedidas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesMedidas());
		this.esgotoTotalLigacoesAtivasConvencional = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalLigacoesAtivasConvencional());
		this.aguaTotalLigacoesComHidrometro = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesComHidrometro());
		this.esgotoTotalLigacoesAtivasCondominial = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalLigacoesAtivasCondominial());
		this.aguaTotalLigacoesResidencialCadastradas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesResidencialCadastradas());
		this.esgotoTotalLigacoesResidencialCadastradas = Util.agruparNumeroEmMilhares(orcamento
						.getEsgotoTotalLigacoesResidencialCadastradas());
		this.aguaTotalLigacoesDesligadas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesDesligadas());
		this.aguaTotalEconomiasCadastradas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasCadastradas());
		this.esgotoTotalEconomiasCadastradasConvencional = Util.agruparNumeroEmMilhares(orcamento
						.getEsgotoTotalEconomiasCadastradasConvencional());
		this.esgotoTotalEconomiasCadastradasCondominial = Util.agruparNumeroEmMilhares(orcamento
						.getEsgotoTotalEconomiasCadastradasCondominial());
		this.aguaTotalEconomiasAtivas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasAtivas());
		this.aguaTotalEconomiasAtivasMedidas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasAtivasMedidas());
		this.esgotoTotalEconomiasAtivasConvencional = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasAtivasConvencional());
		this.aguaTotalEconomiasResidencialCadastradas = Util.agruparNumeroEmMilhares(orcamento
						.getAguaTotalEconomiasResidencialCadastradas());
		this.esgotoTotalEconomiasAtivasCondominial = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasAtivasCondominial());
		this.aguaTotalEconomiasResidencialAtivasMicromedidas = Util.agruparNumeroEmMilhares(orcamento
						.getAguaTotalEconomiasResidencialAtivasMicromedidas());
		this.esgotoTotalEconomiasResidencialCadastradas = Util.agruparNumeroEmMilhares(orcamento
						.getEsgotoTotalEconomiasResidencialCadastradas());
		this.aguaTotalEconomiasResidencialAtivas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasResidencialAtivas());
		this.esgotoTotalEconomiasResidencialAtivas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasResidencialAtivas());
		this.aguaTotalEconomiasComercialAtivas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasComercialAtivas());
		this.esgotoTotalEconomiasComercialAtivas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasComercialAtivas());
		this.aguaTotalEconomiasIndustrialAtivas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasIndustrialAtivas());
		this.esgotoTotalEconomiasIndustrialAtivas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasIndustrialAtivas());
		this.aguaTotalEconomiasPublicoAtivas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasPublicoAtivas());
		this.esgotoTotalEconomiasPublicoAtivas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasPublicoAtivas());
		this.aguaTotalEconomiasRuralAtivas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalEconomiasRuralAtivas());
		this.aguaTotalVolumeFaturadoMedido = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoMedido());
		this.esgotoTotalVolumeFaturadoResidencial = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalVolumeFaturadoResidencial());
		this.esgotoTotalVolumeFaturadoComercial = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalVolumeFaturadoComercial());
		this.aguaTotalVolumeFaturadoEstimado = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoEstimado());
		this.esgotoTotalVolumeFaturadoIndustrial = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalVolumeFaturadoIndustrial());
		this.esgotoTotalVolumeFaturadoPublico = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalVolumeFaturadoPublico());
		this.aguaTotalVolumeFaturadoResidencial = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoResidencial());
		this.esgotoTotalVolumeFaturadoGeral = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalVolumeFaturadoGeral());
		this.aguaTotalVolumeFaturadoComercial = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoComercial());
		this.aguaTotalVolumeFaturadoIndustrial = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoIndustrial());
		this.aguaTotalVolumeFaturadoPublico = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoPublico());
		this.aguaTotalVolumeFaturadoRural = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoRural());
		this.aguaTotalVolumeFaturadoGeral = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoGeral());

		this.aguaTotalFaturadoResidencial = Util.formatarMoedaReal(orcamento.getAguaTotalFaturadoResidencial());
		this.esgotoTotalFaturadoResidencial = Util.formatarMoedaReal(orcamento.getEsgotoTotalFaturadoResidencial());
		this.aguaTotalFaturadoComercial = Util.formatarMoedaReal(orcamento.getAguaTotalFaturadoComercial());
		this.esgotoTotalFaturadoComercial = Util.formatarMoedaReal(orcamento.getEsgotoTotalFaturadoComercial());
		this.aguaTotalFaturadoIndustrial = Util.formatarMoedaReal(orcamento.getAguaTotalFaturadoIndustrial());
		this.esgotoTotalFaturadoIndustrial = Util.formatarMoedaReal(orcamento.getEsgotoTotalFaturadoIndustrial());
		this.aguaTotalFaturadoPublico = Util.formatarMoedaReal(orcamento.getAguaTotalFaturadoPublico());
		this.esgotoTotalFaturadoPublico = Util.formatarMoedaReal(orcamento.getEsgotoTotalFaturadoPublico());
		this.aguaTotalFaturadoDireto = Util.formatarMoedaReal(orcamento.getAguaTotalFaturadoDireto());
		this.esgotoTotalFaturadoDireto = Util.formatarMoedaReal(orcamento.getEsgotoTotalFaturadoDireto());
		this.aguaTotalFaturadoIndireto = Util.formatarMoedaReal(orcamento.getAguaTotalFaturadoIndireto());
		this.esgotoTotalFaturadoIndireto = Util.formatarMoedaReal(orcamento.getEsgotoTotalFaturadoIndireto());
		this.devolucao = Util.formatarMoedaReal(orcamento.getDevolucao());

		this.aguaTotalLigacoesNovas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesNovas());
		this.esgotoTotalLigacoesNovas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalLigacoesNovas());
		this.aguaTotalVolumeFaturadoMicroMedido = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoMicroMedido());
		this.aguaTotalVolumeFaturadoEconomiasResidenciasAtivas = Util.agruparNumeroEmMilhares(orcamento
						.getAguaTotalVolumeFaturadoEconomiasResidenciasAtivas());

		this.totalArrecadacaoMesAtual = Util.formatarMoedaReal(orcamento.getTotalArrecadacaoMesAtual());
		this.totalArrecadacaoMesAnterior = Util.formatarMoedaReal(orcamento.getTotalArrecadacaoMesAnterior());

		this.totalFaturamentoLiquido = Util.formatarMoedaReal(orcamento.getTotalFaturamentoLiquido());
		this.totalArrecadacaoGeral = Util.formatarMoedaReal(orcamento.getTotalArrecadacaoGeral());

		this.aguaTotalFaturamentoGeralDI = Util.formatarMoedaReal(orcamento.getAguaTotalFaturamentoGeralDI());
		this.esgotoTotalFaturamentoGeralDI = Util.formatarMoedaReal(orcamento.getEsgotoTotalFaturamentoGeralDI());

		this.aguaTotalLigacoesSuprimidas = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalLigacoesSuprimidas());

		this.esgotoTotalLigacoesAtivas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalLigacoesAtivas());
		this.esgotoTotalEconomiasAtivas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasAtivas());
		this.esgotoTotalEconomiasCadastradas = Util.agruparNumeroEmMilhares(orcamento.getEsgotoTotalEconomiasCadastradas());

		this.aguaTotalVolumeFaturadoConsumido = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturadoConsumido());
		this.aguaTotalVolumeFaturado = Util.agruparNumeroEmMilhares(orcamento.getAguaTotalVolumeFaturado());

		this.receitaOperacionalTotal = Util.formatarMoedaReal(orcamento.getReceitaOperacionalTotal());
		this.receitaOperacionalDireta = Util.formatarMoedaReal(orcamento.getReceitaOperacionalDireta());
		this.receitaOperacionalIndireta = Util.formatarMoedaReal(orcamento.getReceitaOperacionalIndireta());

		this.saldoContasReceber = Util.formatarMoedaReal(orcamento.getSaldoContasReceber());
	}

	public String getAguaTotalEconomiasAtivas(){

		return aguaTotalEconomiasAtivas;
	}

	public String getAguaTotalEconomiasAtivasMedidas(){

		return aguaTotalEconomiasAtivasMedidas;
	}

	public String getAguaTotalEconomiasCadastradas(){

		return aguaTotalEconomiasCadastradas;
	}

	public String getAguaTotalEconomiasComercialAtivas(){

		return aguaTotalEconomiasComercialAtivas;
	}

	public String getAguaTotalEconomiasIndustrialAtivas(){

		return aguaTotalEconomiasIndustrialAtivas;
	}

	public String getAguaTotalEconomiasPublicoAtivas(){

		return aguaTotalEconomiasPublicoAtivas;
	}

	public String getAguaTotalEconomiasResidencialAtivas(){

		return aguaTotalEconomiasResidencialAtivas;
	}

	public String getAguaTotalEconomiasResidencialAtivasMicromedidas(){

		return aguaTotalEconomiasResidencialAtivasMicromedidas;
	}

	public String getAguaTotalEconomiasResidencialCadastradas(){

		return aguaTotalEconomiasResidencialCadastradas;
	}

	public String getAguaTotalEconomiasRuralAtivas(){

		return aguaTotalEconomiasRuralAtivas;
	}

	public String getAguaTotalFaturadoComercial(){

		return aguaTotalFaturadoComercial;
	}

	public String getAguaTotalFaturadoDireto(){

		return aguaTotalFaturadoDireto;
	}

	public String getAguaTotalFaturadoIndireto(){

		return aguaTotalFaturadoIndireto;
	}

	public String getAguaTotalFaturadoIndustrial(){

		return aguaTotalFaturadoIndustrial;
	}

	public String getAguaTotalFaturadoPublico(){

		return aguaTotalFaturadoPublico;
	}

	public String getAguaTotalFaturadoResidencial(){

		return aguaTotalFaturadoResidencial;
	}

	public String getAguaTotalLigacoesAtivas(){

		return aguaTotalLigacoesAtivas;
	}

	public String getAguaTotalLigacoesCadastradas(){

		return aguaTotalLigacoesCadastradas;
	}

	public String getAguaTotalLigacoesComHidrometro(){

		return aguaTotalLigacoesComHidrometro;
	}

	public String getAguaTotalLigacoesDesligadas(){

		return aguaTotalLigacoesDesligadas;
	}

	public String getAguaTotalLigacoesMedidas(){

		return aguaTotalLigacoesMedidas;
	}

	public String getAguaTotalLigacoesNovas(){

		return aguaTotalLigacoesNovas;
	}

	public String getAguaTotalLigacoesResidencialCadastradas(){

		return aguaTotalLigacoesResidencialCadastradas;
	}

	public String getAguaTotalVolumeFaturadoComercial(){

		return aguaTotalVolumeFaturadoComercial;
	}

	public String getAguaTotalVolumeFaturadoEconomiasResidenciasAtivas(){

		return aguaTotalVolumeFaturadoEconomiasResidenciasAtivas;
	}

	public String getAguaTotalVolumeFaturadoEstimado(){

		return aguaTotalVolumeFaturadoEstimado;
	}

	public String getAguaTotalVolumeFaturadoGeral(){

		return aguaTotalVolumeFaturadoGeral;
	}

	public String getAguaTotalVolumeFaturadoIndustrial(){

		return aguaTotalVolumeFaturadoIndustrial;
	}

	public String getAguaTotalVolumeFaturadoMedido(){

		return aguaTotalVolumeFaturadoMedido;
	}

	public String getAguaTotalVolumeFaturadoMicroMedido(){

		return aguaTotalVolumeFaturadoMicroMedido;
	}

	public String getAguaTotalVolumeFaturadoPublico(){

		return aguaTotalVolumeFaturadoPublico;
	}

	public String getAguaTotalVolumeFaturadoResidencial(){

		return aguaTotalVolumeFaturadoResidencial;
	}

	public String getAguaTotalVolumeFaturadoRural(){

		return aguaTotalVolumeFaturadoRural;
	}

	public String getDevolucao(){

		return devolucao;
	}

	public String getEsgotoTotalEconomiasAtivasCondominial(){

		return esgotoTotalEconomiasAtivasCondominial;
	}

	public String getEsgotoTotalEconomiasAtivasConvencional(){

		return esgotoTotalEconomiasAtivasConvencional;
	}

	public String getEsgotoTotalEconomiasCadastradasCondominial(){

		return esgotoTotalEconomiasCadastradasCondominial;
	}

	public String getEsgotoTotalEconomiasCadastradasConvencional(){

		return esgotoTotalEconomiasCadastradasConvencional;
	}

	public String getEsgotoTotalEconomiasComercialAtivas(){

		return esgotoTotalEconomiasComercialAtivas;
	}

	public String getEsgotoTotalEconomiasIndustrialAtivas(){

		return esgotoTotalEconomiasIndustrialAtivas;
	}

	public String getEsgotoTotalEconomiasPublicoAtivas(){

		return esgotoTotalEconomiasPublicoAtivas;
	}

	public String getEsgotoTotalEconomiasResidencialAtivas(){

		return esgotoTotalEconomiasResidencialAtivas;
	}

	public String getEsgotoTotalEconomiasResidencialCadastradas(){

		return esgotoTotalEconomiasResidencialCadastradas;
	}

	public String getEsgotoTotalFaturadoComercial(){

		return esgotoTotalFaturadoComercial;
	}

	public String getEsgotoTotalFaturadoDireto(){

		return esgotoTotalFaturadoDireto;
	}

	public String getEsgotoTotalFaturadoIndireto(){

		return esgotoTotalFaturadoIndireto;
	}

	public String getEsgotoTotalFaturadoIndustrial(){

		return esgotoTotalFaturadoIndustrial;
	}

	public String getEsgotoTotalFaturadoPublico(){

		return esgotoTotalFaturadoPublico;
	}

	public String getEsgotoTotalFaturadoResidencial(){

		return esgotoTotalFaturadoResidencial;
	}

	public String getEsgotoTotalLigacoesAtivasCondominial(){

		return esgotoTotalLigacoesAtivasCondominial;
	}

	public String getEsgotoTotalLigacoesAtivasConvencional(){

		return esgotoTotalLigacoesAtivasConvencional;
	}

	public String getEsgotoTotalLigacoesCadastradas(){

		return esgotoTotalLigacoesCadastradas;
	}

	public String getEsgotoTotalLigacoesCadastradasCondominial(){

		return esgotoTotalLigacoesCadastradasCondominial;
	}

	public String getEsgotoTotalLigacoesCadastradasConvencional(){

		return esgotoTotalLigacoesCadastradasConvencional;
	}

	public String getEsgotoTotalLigacoesNovas(){

		return esgotoTotalLigacoesNovas;
	}

	public String getEsgotoTotalLigacoesResidencialCadastradas(){

		return esgotoTotalLigacoesResidencialCadastradas;
	}

	public String getEsgotoTotalVolumeFaturadoComercial(){

		return esgotoTotalVolumeFaturadoComercial;
	}

	public String getEsgotoTotalVolumeFaturadoGeral(){

		return esgotoTotalVolumeFaturadoGeral;
	}

	public String getEsgotoTotalVolumeFaturadoIndustrial(){

		return esgotoTotalVolumeFaturadoIndustrial;
	}

	public String getEsgotoTotalVolumeFaturadoPublico(){

		return esgotoTotalVolumeFaturadoPublico;
	}

	public String getEsgotoTotalVolumeFaturadoResidencial(){

		return esgotoTotalVolumeFaturadoResidencial;
	}

	public String getOpcaoTotalizacao(){

		return opcaoTotalizacao;
	}

	public String getTotalArrecadacaoMesAnterior(){

		return totalArrecadacaoMesAnterior;
	}

	public String getTotalArrecadacaoMesAtual(){

		return totalArrecadacaoMesAtual;
	}

	public String getTotalArrecadacaoGeral(){

		return totalArrecadacaoGeral;
	}

	public String getTotalFaturamentoGeral(){

		return totalFaturamentoGeral;
	}

	public String getTotalFaturamentoLiquido(){

		return totalFaturamentoLiquido;
	}

	public String getAguaTotalFaturamentoGeralDI(){

		return aguaTotalFaturamentoGeralDI;
	}

	public String getEsgotoTotalFaturamentoGeralDI(){

		return esgotoTotalFaturamentoGeralDI;
	}

	public String getAguaTotalLigacoesSuprimidas(){

		return aguaTotalLigacoesSuprimidas;
	}

	public String getAguaTotalVolumeFaturado(){

		return aguaTotalVolumeFaturado;
	}

	public String getAguaTotalVolumeFaturadoConsumido(){

		return aguaTotalVolumeFaturadoConsumido;
	}

	public String getEsgotoTotalEconomiasAtivas(){

		return esgotoTotalEconomiasAtivas;
	}

	public String getEsgotoTotalEconomiasCadastradas(){

		return esgotoTotalEconomiasCadastradas;
	}

	public String getEsgotoTotalLigacoesAtivas(){

		return esgotoTotalLigacoesAtivas;
	}

	public String getReceitaOperacionalDireta(){

		return receitaOperacionalDireta;
	}

	public String getReceitaOperacionalIndireta(){

		return receitaOperacionalIndireta;
	}

	public String getReceitaOperacionalTotal(){

		return receitaOperacionalTotal;
	}

	public String getSaldoContasReceber(){

		return saldoContasReceber;
	}

}
