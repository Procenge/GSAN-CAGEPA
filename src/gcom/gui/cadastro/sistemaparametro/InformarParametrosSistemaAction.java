/**
 * 
 */
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
 * 
 * GSANPCG
 * Eduardo Henrique
 * Saulo Lima
 */

package gcom.gui.cadastro.sistemaparametro;

import gcom.arrecadacao.banco.ContaBancaria;
import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ControladorException;
import gcom.util.IoUtil;
import gcom.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 10/01/2007
 */
public class InformarParametrosSistemaAction
				extends GcomAction {

	private ServletContext context;

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 * @author eduardo henrique
	 * @date 06/06/2008
	 *       Adição do Parâmetro de Unidade Org. da Presidência
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		InformarSistemaParametrosActionForm form = (InformarSistemaParametrosActionForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		SistemaParametro sistemaParametro = new SistemaParametro();

		sistemaParametro = (SistemaParametro) sessao.getAttribute("sistemaParametro");
		String imagensPath = "./imagens/" + sistemaParametro.getNomeAbreviadoEmpresa() + "/";

		/* ele passa aqui quando tenta salvar o objeto SistemaParametro */
		if(!"".equals(form.getEspecificacao()) && form.getEspecificacao() != null){
			sistemaParametro.setSolicitacaoTipoEspecificacaoDefault(Integer.valueOf(form.getEspecificacao()));
		}

		if(!"".equals(form.getEspecificacaoReiteracao()) && form.getEspecificacaoReiteracao() != null){
			sistemaParametro.setSolicitacaoTipoEspecificacaoReiteracao(Integer.valueOf(form.getEspecificacaoReiteracao()));
		}

		if(form.getImagemLogomarca() != null && !form.getImagemLogomarca().trim().equalsIgnoreCase("")){

			sistemaParametro.setImagemLogomarca(form.getImagemLogomarca());
		}

		if(form.getImagemConta() != null && !form.getImagemConta().trim().equalsIgnoreCase("")){

			sistemaParametro.setImagemConta(imagensPath + form.getImagemConta());
		}

		if(form.getImagemRelatorio() != null && !form.getImagemRelatorio().trim().equalsIgnoreCase("")){

			sistemaParametro.setImagemRelatorio(imagensPath + form.getImagemRelatorio());
		}

		if(form.getImagemCabecalho() != null && !form.getImagemCabecalho().trim().equalsIgnoreCase("")){

			sistemaParametro.setImagemCabecalho(form.getImagemCabecalho());
		}

		if(form.getImagemPrincipal() != null && !form.getImagemPrincipal().trim().equalsIgnoreCase("")){

			sistemaParametro.setImagemPrincipal(form.getImagemPrincipal());
		}

		if(form.getImagemSecundaria() != null && !form.getImagemSecundaria().trim().equalsIgnoreCase("")){

			sistemaParametro.setImagemSecundaria(form.getImagemSecundaria());
		}

		if(sessao.getAttribute("paramId") != null){
			sistemaParametro.setParmId((Integer) sessao.getAttribute("paramId"));
		}

		sistemaParametro.setNomeEstado(form.getNomeEstado());

		sistemaParametro.setNomeEmpresa(form.getNomeEmpresa());
		sistemaParametro.setNomeAbreviadoEmpresa(form.getAbreviaturaEmpresa());

		sistemaParametro.setCnpjEmpresa(form.getCnpj());

		// Logradouro
		Logradouro logradouro = new Logradouro();

		logradouro.setId(Integer.valueOf(form.getLogradouro()));

		sistemaParametro.setLogradouro(logradouro);

		if(form.getNumero() != null && !form.getNumero().trim().equalsIgnoreCase("")){

			sistemaParametro.setNumeroImovel(form.getNumero());
		}

		if(form.getComplemento() != null && !form.getComplemento().trim().equalsIgnoreCase("")){
			sistemaParametro.setComplementoEndereco(form.getComplemento());
		}
		// Bairro

		if(form.getBairro() != null && !form.getBairro().trim().equalsIgnoreCase("-1")){
			Bairro bairro = new Bairro();

			bairro.setId(Integer.valueOf(form.getBairro()));

			sistemaParametro.setBairro(bairro);
		}

		// CEP

		if(form.getCep() != null && !form.getCep().trim().equalsIgnoreCase("-1")){

			Cep cep = new Cep();
			cep.setCepId(Integer.valueOf(form.getCep()));
			sistemaParametro.setCep(cep);
		}

		// Endereco Referencia

		if(form.getEnderecoReferencia() != null && !form.getEnderecoReferencia().trim().equalsIgnoreCase("-1")){

			EnderecoReferencia enderecoReferencia = new EnderecoReferencia();
			enderecoReferencia.setId(Integer.valueOf(form.getEnderecoReferencia()));
			sistemaParametro.setEnderecoReferencia(enderecoReferencia);
		}

		if(form.getNumeroTelefone() != null && !form.getNumeroTelefone().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroTelefone(form.getNumeroTelefone());
		}

		if(form.getRamal() != null && !form.getRamal().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroRamal(form.getRamal());

		}

		if(form.getFax() != null && !form.getFax().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroFax(form.getFax());

		}

		if(form.getEmail() != null && !form.getEmail().trim().equalsIgnoreCase("")){
			sistemaParametro.setDescricaoEmail(form.getEmail());

		}

		// 2ª aba

		if(form.getMesAnoReferencia() != null && !form.getMesAnoReferencia().trim().equalsIgnoreCase("")){

			boolean mesAnoValido = Util.validarMesAno(form.getMesAnoReferencia());
			if(mesAnoValido == false){
				throw new ActionServletException("atencao.ano_mes_referencia.invalida");
			}

			String mes = form.getMesAnoReferencia().substring(0, 2);
			String ano = form.getMesAnoReferencia().substring(3, 7);

			Integer anoMesReferenciaFaturamento = Integer.valueOf(ano + mes);

			sistemaParametro.setAnoMesFaturamento(anoMesReferenciaFaturamento);

		}

		if(form.getMenorConsumo() != null && !form.getMenorConsumo().trim().equalsIgnoreCase("")){
			sistemaParametro.setMenorConsumoGrandeUsuario(Integer.valueOf(form.getMenorConsumo()));
		}

		if(form.getMenorValor() != null && !form.getMenorValor().trim().equalsIgnoreCase("")){

			BigDecimal valorMinimoEmissaoConta = new BigDecimal(0);

			String valorAux = form.getMenorValor().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			valorMinimoEmissaoConta = new BigDecimal(valorAux);
			sistemaParametro.setValorMinimoEmissaoConta(valorMinimoEmissaoConta);

		}

		if(form.getQtdeEconomias() != null && !form.getQtdeEconomias().trim().equalsIgnoreCase("")){

			sistemaParametro.setMenorEconomiasGrandeUsuario(Short.valueOf(form.getQtdeEconomias()));
		}

		if(form.getMesesCalculoMedio() != null && !form.getMesesCalculoMedio().trim().equalsIgnoreCase("")){

			sistemaParametro.setMesesMediaConsumo(Short.valueOf(form.getMesesCalculoMedio()));
		}

		if(form.getDiasVencimentoCobranca() != null && !form.getDiasVencimentoCobranca().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroMinimoDiasEmissaoVencimento(Short.valueOf(form.getDiasVencimentoCobranca()));
		}

		if(form.getDiasMinimoVencimento() != null && !form.getDiasMinimoVencimento().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroMinimoDiasEmissaoVencimento(Short.valueOf(form.getDiasMinimoVencimento()));
		}

		if(form.getDiasMinimoVencimentoCorreio() != null && !form.getDiasMinimoVencimentoCorreio().equalsIgnoreCase("")){
			sistemaParametro.setNumeroDiasAdicionaisCorreios(Short.valueOf(form.getDiasMinimoVencimentoCorreio()));
		}

		if(form.getNumeroMesesValidadeConta() != null && !form.getNumeroMesesValidadeConta().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroMesesValidadeConta(Short.valueOf(form.getNumeroMesesValidadeConta()));
		}

		if(form.getNumeroMesesAlteracaoVencimento() != null && !form.getNumeroMesesAlteracaoVencimento().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroMesesMinimoAlteracaoVencimento(Short.valueOf(form.getNumeroMesesAlteracaoVencimento()));
		}

		if(form.getNumeroMaximoTiposDebitoEmissaoDocumento() != null
						&& !form.getNumeroMaximoTiposDebitoEmissaoDocumento().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroMaximoTiposDebitoEmissaoDocumento(Short.valueOf(form.getNumeroMaximoTiposDebitoEmissaoDocumento()));
		}

		if(form.getNumeroDiasEsperaExtratoDebito() != null && !form.getNumeroDiasEsperaExtratoDebito().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroDiasEsperaExtratoDebito(Short.valueOf(form.getNumeroDiasEsperaExtratoDebito()));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Número de Dias em Espera para Extrato de Débito");
		}

		if(form.getAnoReferenciaDebitoConta() != null && !form.getAnoReferenciaDebitoConta().trim().equalsIgnoreCase("")){
			sistemaParametro.setAnoReferenciaDebitoConta((Integer.valueOf(form.getAnoReferenciaDebitoConta())));
		}else{
			throw new ActionServletException("atencao.informe_campo", null, "Ano Referência Débito");
		}

		if(form.getTitulosRelatorio() != null && !form.getTitulosRelatorio().trim().equalsIgnoreCase("")){
			sistemaParametro.setTituloPagina(form.getTitulosRelatorio());

		}

		if(form.getSalarioMinimo() != null && !form.getSalarioMinimo().trim().equalsIgnoreCase("")){
			BigDecimal valorValorSalarioMinimo = new BigDecimal(0);

			String valorAux = form.getSalarioMinimo().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			valorValorSalarioMinimo = new BigDecimal(valorAux);

			sistemaParametro.setValorSalarioMinimo(valorValorSalarioMinimo);

		}

		if(form.getAreaMaxima() != null && !form.getAreaMaxima().trim().equalsIgnoreCase("")){

			sistemaParametro.setAreaMaximaTarifaSocial(Integer.valueOf(form.getAreaMaxima()));
		}

		if(form.getConsumoMaximo() != null && !form.getConsumoMaximo().trim().equalsIgnoreCase("")){
			sistemaParametro.setConsumoEnergiaMaximoTarifaSocial(Integer.valueOf(form.getConsumoMaximo()));
		}

		if(form.getIndicadorClienteAtualFatura() != null && !form.getIndicadorClienteAtualFatura().trim().equalsIgnoreCase("")){
			sistemaParametro.setIndicadorClienteAtualFatura(Short.valueOf(form.getIndicadorClienteAtualFatura()));
		}

		if(form.getAnoReferenciaDebitoConta() != null && !"".equals(form.getAnoReferenciaDebitoConta())){
			sistemaParametro.setAnoReferenciaDebitoConta(Integer.valueOf(form.getAnoReferenciaDebitoConta()));
		}

		if(form.getNumeroMinDebitosAguaParaTodos() != null){
			sistemaParametro.setNumeroMinDebitosAguaParaTodos(Util.converterStringParaInteger(form.getNumeroMinDebitosAguaParaTodos()));
		}

		if(form.getCodMotivoExclusaoAguaParaTodos() != null){
			sistemaParametro.setCodMotivoExclusaoAguaParaTodos(Util.converterStringParaInteger(form.getCodMotivoExclusaoAguaParaTodos()));
		}

		if(form.getNumeroConsumoMinAguaParaTodos() != null){
			sistemaParametro.setNumeroConsumoMinAguaParaTodos(Util.converterStringParaInteger(form.getNumeroConsumoMinAguaParaTodos()));
		}

		if(form.getNumeroConsumoExcedidoAguaParaTodos() != null){
			sistemaParametro.setNumeroConsumoExcedidoAguaParaTodos(Util.converterStringParaInteger(form
							.getNumeroConsumoExcedidoAguaParaTodos()));
		}

		if(form.getCodMotExclusaoConsumoAguaParaTodos() != null){
			sistemaParametro.setCodMotExclusaoConsumoAguaParaTodos(Util.converterStringParaInteger(form
							.getCodMotExclusaoConsumoAguaParaTodos()));
		}

		if(form.getCodTarifaAguaParaTodos() != null){
			sistemaParametro.setCodTarifaAguaParaTodos(Util.converterStringParaInteger(form.getCodTarifaAguaParaTodos()));
		}

		if(form.getNumeroMaxDiasVigenciaTarifaAguaParaTodos() != null){
			sistemaParametro.setNumeroMaxDiasVigenciaTarifaAguaParaTodos(Util.converterStringParaInteger(form
							.getNumeroMaxDiasVigenciaTarifaAguaParaTodos()));
		}

		// 3ª aba

		if(form.getMesAnoReferenciaArrecadacao() != null && !form.getMesAnoReferenciaArrecadacao().trim().equalsIgnoreCase("")){

			boolean mesAnoValido = Util.validarMesAno(form.getMesAnoReferenciaArrecadacao());

			if(mesAnoValido == false){
				throw new ActionServletException("atencao.ano_mes_referencia.invalida");
			}

			String mes = form.getMesAnoReferenciaArrecadacao().substring(0, 2);
			String ano = form.getMesAnoReferenciaArrecadacao().substring(3, 7);

			Integer anoMesReferenciaArrecadacao = Integer.valueOf(ano + mes);

			sistemaParametro.setAnoMesArrecadacao(anoMesReferenciaArrecadacao);
		}

		if(form.getCodigoEmpresaFebraban() != null && !form.getCodigoEmpresaFebraban().trim().equalsIgnoreCase("")){
			sistemaParametro.setCodigoEmpresaFebraban(Short.valueOf(form.getCodigoEmpresaFebraban()));
		}

		if(form.getNumeroLayOut() != null && !form.getNumeroLayOut().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroLayoutFebraban(Short.valueOf(form.getNumeroLayOut()));
		}
		// Conta Bancaria

		if(form.getIndentificadorContaDevolucao() != null && !form.getIndentificadorContaDevolucao().trim().equalsIgnoreCase("-1")){
			ContaBancaria contaBancaria = new ContaBancaria();

			contaBancaria.setId(Integer.valueOf(form.getIndentificadorContaDevolucao()));
			sistemaParametro.setContaBancaria(contaBancaria);

		}

		if(form.getPercentualEntradaMinima() != null && !form.getPercentualEntradaMinima().trim().equalsIgnoreCase("")){

			BigDecimal percentualEntradaMinima = new BigDecimal(0);

			String valorAux = form.getPercentualEntradaMinima().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			percentualEntradaMinima = new BigDecimal(valorAux);

			sistemaParametro.setPercentualFinanciamentoEntradaMinima(percentualEntradaMinima);
		}

		if(form.getMaximoParcelas() != null && !form.getMaximoParcelas().trim().equalsIgnoreCase("")){

			sistemaParametro.setNumeroMaximoParcelasFinanciamento(Short.valueOf(form.getMaximoParcelas()));
		}

		if(form.getPercentualMaximoAbatimento() != null && !form.getPercentualMaximoAbatimento().trim().equalsIgnoreCase("")){

			BigDecimal percentualMaximoAbatimento = new BigDecimal(0);

			String valorAux = form.getPercentualMaximoAbatimento().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			percentualMaximoAbatimento = new BigDecimal(valorAux);

			sistemaParametro.setPercentualMaximoAbatimento(percentualMaximoAbatimento);
		}

		if(form.getPercentualTaxaFinanciamento() != null && !form.getPercentualTaxaFinanciamento().trim().equalsIgnoreCase("")){
			BigDecimal percentualTaxaFinanciamento = new BigDecimal(0);

			String valorAux = form.getPercentualTaxaFinanciamento().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			percentualTaxaFinanciamento = new BigDecimal(valorAux);

			sistemaParametro.setPercentualTaxaJurosFinanciamento(percentualTaxaFinanciamento);

		}

		if(form.getNumeroMaximoParcelaCredito() != null && !form.getNumeroMaximoParcelaCredito().trim().equalsIgnoreCase("")){

			sistemaParametro.setNumeroMaximoParcelaCredito(Short.valueOf(form.getNumeroMaximoParcelaCredito()));
		}

		if(form.getPercentualCalculoIndice() != null && !form.getPercentualCalculoIndice().trim().equalsIgnoreCase("")){

			BigDecimal percentualCalculoIndice = new BigDecimal(0);

			String valorAux = form.getPercentualCalculoIndice().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			percentualCalculoIndice = new BigDecimal(valorAux);

			sistemaParametro.setPercentualMediaIndice(percentualCalculoIndice);
			// }
		}
		// 4ª Aba
		// Hidrometro Capacidade ou codigo da Menor capacidade de hidrometro
		// para ser grande usuario

		if(form.getCodigoMenorCapacidade() != null && !form.getCodigoMenorCapacidade().trim().equalsIgnoreCase("-1")){

			HidrometroCapacidade hidrometroCapacidade = new HidrometroCapacidade();

			hidrometroCapacidade.setId(Integer.valueOf(form.getCodigoMenorCapacidade()));

			sistemaParametro.setHidrometroCapacidade(hidrometroCapacidade);
		}

		if(form.getIndicadorGeracaoFaixaFalsa() != null && !form.getIndicadorGeracaoFaixaFalsa().trim().equalsIgnoreCase("")){

			sistemaParametro.setIndicadorFaixaFalsa(Short.valueOf(form.getIndicadorGeracaoFaixaFalsa()));
		}

		if(form.getIndicadorPercentualGeracaoFaixaFalsa() != null
						&& !form.getIndicadorPercentualGeracaoFaixaFalsa().trim().equalsIgnoreCase("")){
			sistemaParametro.setIndicadorUsoFaixaFalsa(Short.valueOf(form.getIndicadorPercentualGeracaoFaixaFalsa()));
		}

		if(form.getPercentualGeracaoFaixaFalsa() != null && !form.getPercentualGeracaoFaixaFalsa().trim().equalsIgnoreCase("")){
			BigDecimal percentualGeracaoFaixaFalsa = new BigDecimal(0);

			String valorAux = form.getPercentualGeracaoFaixaFalsa().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			percentualGeracaoFaixaFalsa = new BigDecimal(valorAux);

			sistemaParametro.setPercentualFaixaFalsa(percentualGeracaoFaixaFalsa);

		}

		if(form.getIndicadorGeracaoFiscalizacaoLeitura() != null
						&& !form.getIndicadorGeracaoFiscalizacaoLeitura().trim().equalsIgnoreCase("")){
			sistemaParametro.setIndicadorPercentualFiscalizacaoLeitura(Short.valueOf(form
							.getIndicadorPercentualGeracaoFiscalizacaoLeitura()));
		}

		if(form.getIndicadorPercentualGeracaoFiscalizacaoLeitura() != null
						&& !form.getIndicadorPercentualGeracaoFiscalizacaoLeitura().trim().equalsIgnoreCase("")){
			sistemaParametro.setIndicadorUsoFiscalizadorLeitura(Short.valueOf(form.getIndicadorGeracaoFiscalizacaoLeitura()));
		}

		if(form.getPercentualGeracaoFiscalizacaoLeitura() != null
						&& !form.getPercentualGeracaoFiscalizacaoLeitura().trim().equalsIgnoreCase("")){

			BigDecimal percentualGeracaoFiscalizacaoLeitura = new BigDecimal(0);

			String valorAux = form.getPercentualGeracaoFiscalizacaoLeitura().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			percentualGeracaoFiscalizacaoLeitura = new BigDecimal(valorAux);

			sistemaParametro.setPercentualFiscalizacaoLeitura(percentualGeracaoFiscalizacaoLeitura);

		}

		if(form.getIncrementoMaximoConsumo() != null && !form.getIncrementoMaximoConsumo().trim().equalsIgnoreCase("")){

			sistemaParametro.setIncrementoMaximoConsumoRateio(Integer.valueOf(form.getIncrementoMaximoConsumo()));
		}

		if(form.getDecrementoMaximoConsumo() != null && !form.getDecrementoMaximoConsumo().trim().equalsIgnoreCase("")){
			sistemaParametro.setDecrementoMaximoConsumoRateio(Integer.valueOf(form.getDecrementoMaximoConsumo()));
		}

		if(!Util.isVazioOuBranco(form.getIndicadorLayoutArquivoLeituraPadrao())){
			sistemaParametro.setIndicadorLayoutArquivoLeituraPadrao(Util.obterShort(form.getIndicadorLayoutArquivoLeituraPadrao()));
		}

		if(!Util.isVazioOuBranco(form.getCodigoLimiteAceitavelAnormalidades())){
			sistemaParametro.setCodigoLimiteAceitavelAnormalidades(Util.obterInteger(form.getCodigoLimiteAceitavelAnormalidades()));
		}else{
			sistemaParametro.setCodigoLimiteAceitavelAnormalidades(null);
		}

		if(!Util.isVazioOuBranco(form.getPercentualAnormalidadeAceitavel())){
			sistemaParametro.setPercentualAnormalidadeAceitavel(Util.formatarStringParaBigDecimal(
							form.getPercentualAnormalidadeAceitavel(), 2, false));
		}

		if(form.getPercentualToleranciaRateioConsumo() != null && !form.getPercentualToleranciaRateioConsumo().trim().equalsIgnoreCase("")){

			BigDecimal percentualToleranciaRateioConsumo = new BigDecimal(0);

			String valorAux = form.getPercentualToleranciaRateioConsumo().toString().replace(".", "");

			valorAux = valorAux.replace(",", ".");

			percentualToleranciaRateioConsumo = new BigDecimal(valorAux);

			sistemaParametro.setPercentualToleranciaRateio(percentualToleranciaRateioConsumo);
		}

		if(form.getDiasVencimentoCobranca() != null && !form.getDiasVencimentoCobranca().trim().equalsIgnoreCase("")){

			sistemaParametro.setNumeroDiasVencimentoCobranca(Short.valueOf(form.getDiasVencimentoCobranca()));

		}

		// 5ª aba

		if(form.getIndicadorSugestaoTramite() != null && !form.getIndicadorSugestaoTramite().trim().equalsIgnoreCase("")){

			sistemaParametro.setIndicadorSugestaoTramite(Short.valueOf(form.getIndicadorSugestaoTramite()));
		}

		if(form.getDiasMaximoReativarRA() != null && !form.getDiasMaximoReativarRA().trim().equalsIgnoreCase("")){
			sistemaParametro.setDiasReativacao(Short.valueOf(form.getDiasMaximoReativarRA()));
		}

		if(form.getDiasMaximoAlterarOS() != null && !form.getDiasMaximoAlterarOS().trim().equalsIgnoreCase("")){
			sistemaParametro.setDiasMaximoAlterarOS(Integer.valueOf(form.getDiasMaximoAlterarOS()));
		}

		if(form.getUltimoIDGeracaoRA() != null && !form.getUltimoIDGeracaoRA().trim().equalsIgnoreCase("")){
			sistemaParametro.setUltimoRAManual(Short.valueOf(form.getUltimoIDGeracaoRA()));
		}

		if(form.getIdUnidadeOrganizacionalPresidencia() != null && !form.getIdUnidadeOrganizacionalPresidencia().equalsIgnoreCase("")){
			UnidadeOrganizacional unidadePresidencia = new UnidadeOrganizacional();

			unidadePresidencia.setId(Integer.valueOf(form.getIdUnidadeOrganizacionalPresidencia()));

			sistemaParametro.setUnidadeOrganizacionalIdPresidencia(unidadePresidencia);
		}

		if(form.getDiasMaximoExpirarAcesso() != null && !form.getDiasMaximoExpirarAcesso().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroDiasExpiracaoAcesso(Short.valueOf(form.getDiasMaximoExpirarAcesso()));
		}

		if(form.getDiasMensagemExpiracaoSenha() != null && !form.getDiasMensagemExpiracaoSenha().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroDiasMensagemExpiracao(Short.valueOf(form.getDiasMensagemExpiracaoSenha()));
		}

		if(form.getNumeroMaximoTentativasAcesso() != null && !form.getNumeroMaximoTentativasAcesso().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroMaximoLoginFalho(Short.valueOf(form.getNumeroMaximoTentativasAcesso()));
		}

		if(form.getNumeroMaximoFavoritosMenu() != null && !form.getNumeroMaximoFavoritosMenu().trim().equalsIgnoreCase("")){
			sistemaParametro.setNumeroMaximoFavorito(Integer.valueOf(form.getNumeroMaximoFavoritosMenu()));
		}

		if(form.getIpServidorSmtp() != null && !form.getIpServidorSmtp().trim().equalsIgnoreCase("")){
			sistemaParametro.setIpServidorSmtp(form.getIpServidorSmtp());
		}

		if(form.getEmailResponsavel() != null && !form.getEmailResponsavel().trim().equalsIgnoreCase("")){
			sistemaParametro.setDsEmailResponsavel(form.getEmailResponsavel());
		}

		// Atributos obrigatórios no BD , que não estão sendo exigidos na GUI (Indicador Rota e
		// Faturamento Antecipado)
		sistemaParametro.setIndicadorFaturamentoAntecipado(Short.valueOf(form.getIndicadorFaturamentoAntecipado()));
		sistemaParametro.setIndicadorRoteiroEmpresa(Short.valueOf(form.getIndicadorRoteiroEmpresa()));

		// Captura a imagem de Logomarca da empresa para salvar no banco de dados. Esta informação é
		// necessária na geração dos relatórios em Crystal Report
		// @author: Luciano Galvão
		if(!Util.isVazioOuBranco(sistemaParametro.getNomeAbreviadoEmpresa())
						&& !Util.isVazioOuBranco(sistemaParametro.getImagemLogomarca())){

			String caminhoCompletoImagemRelatorio = httpServletRequest.getSession().getServletContext()
							.getRealPath(sistemaParametro.getImagemRelatorio());

			try{

				sistemaParametro.setArquivoImagemRelatorio(IoUtil.getImagem(caminhoCompletoImagemRelatorio));

			}catch(FileNotFoundException e){
				throw new ActionServletException("Erro ao ler a imagem de logomarca da empresa", e);
			}catch(IOException e){
				throw new ActionServletException("Erro ao ler a imagem de logomarca da empresa", e);
			}

		}

		fachada.informarParametrosSistema(sistemaParametro, usuario);

		montarPaginaSucesso(httpServletRequest, "Parâmetro do Sistema informado com sucesso.", "Informar outro Parametro do Sistema",
						"exibirInformarParametrosSistemaAction.do?menu=sim");

		return retorno;

	}

	/**
	 * Atualiza a imagem de logomarca da empresa no banco de dados.
	 * 
	 * @author lgalvao (Luciano Galvão)
	 * @date 22/05/2012
	 * @param sistemaParametro
	 * @throws ControladorException
	 */
	private byte[] getImagem(String caminhoCompleto) throws ActionServletException{

		try{
			InputStream in = new FileInputStream(caminhoCompleto);
			ByteArrayOutputStream out = new ByteArrayOutputStream();

			byte[] content = new byte[in.available()];

			int bytesRead = in.read(content);

			// Stream the byte array to the client.
			while(bytesRead != -1){
				out.write(content, 0, bytesRead);
				bytesRead = in.read(content);
			}

			return out.toByteArray();

		}catch(FileNotFoundException e){
			throw new ActionServletException("Erro ao ler a imagem de logomarca da empresa", e);
		}catch(IOException e){
			throw new ActionServletException("Erro ao ler a imagem de logomarca da empresa", e);
		}

	}

}
