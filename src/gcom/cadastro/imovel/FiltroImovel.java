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

package gcom.cadastro.imovel;

import gcom.util.filtro.Filtro;

import java.io.Serializable;

/**
 * < <Descrição da Classe>>
 * 
 * @author Administrador
 * @author eduardo henrique
 * @date 06/11/2008
 *       Alteração na Entidade para retirada da associação 'direta' com Hidrometro (já estava
 *       comentada no hbm).
 */
public class FiltroImovel
				extends Filtro
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Constructor for the FiltroCliente object
	 */
	public FiltroImovel() {

	}

	/**
	 * Constructor for the FiltroCliente object
	 * 
	 * @param campoOrderBy
	 *            Description of the Parameter
	 */
	public FiltroImovel(String campoOrderBy) {

		this.campoOrderBy = campoOrderBy;
	}

	public final static String ID = "id";

	public final static String LOCALIDADE = "localidade";

	public final static String LOCALIDADE_ID = "localidade.id";

	public final static String SETOR_COMERCIAL = "setorComercial";

	public final static String SETOR_COMERCIAL_ID = "setorComercial.id";

	public final static String SETOR_COMERCIAL_CODIGO = "setorComercial.codigo";

	public final static String QUADRA = "quadra";

	public final static String QUADRA_ID = "quadra.id";

	public final static String QUADRA_NUMERO = "quadra.numeroQuadra";

	public final static String QUADRA_ROTA_ID = "rota.id";

	public final static String QUADRA_ROTA = "quadra.rota";

	public final static String QUADRA_SETOR_COMERCIAL_LOCALIDADE = "quadra.setorComercial.localidade";

	public final static String CEP = "logradouroCep.cep";

	public final static String CEP_CODIGO = "logradouroCep.cep.codigo";

	public final static String LOGRADOURO = "logradouroCep.logradouro";

	public final static String LOGRADOURO_ID = "logradouroCep.logradouro.id";

	public final static String LOGRADOURO_TIPO = "logradouroCep.logradouro.logradouroTipo";

	public final static String LOGRADOURO_TIPO_ID = "logradouroCep.logradouro.logradouroTipo.id";

	public final static String LOGRADOURO_TITULO = "logradouroCep.logradouro.logradouroTitulo";

	public final static String LOGRADOURO_TITULO_ID = "logradouroCep.logradouro.logradouroTitulo.id";

	public final static String MUNICIPIO = "logradouroBairro.bairro.municipio";

	public final static String MUNICIPIO_ID = "logradouroBairro.bairro.municipio.id";

	// public final static String UNIDADE_FEDERACAO = "quadra.bairro.municipio.unidadeFederacao.id";
	public final static String UNIDADE_FEDERACAO = "logradouroBairro.bairro.municipio.unidadeFederacao";

	public final static String BAIRRO = "logradouroBairro.bairro";

	public final static String BAIRRO_ID = "logradouroBairro.bairro.id";

	public final static String IPTU = "numeroIptu";

	public final static String NUMERO_CELPE = "numeroCelpe";

	public final static String LIGACAO_AGUA_SITUACAO = "ligacaoAguaSituacao";

	public final static String LIGACAO_AGUA_SITUACAO_ID = "ligacaoAguaSituacao.id";

	public final static String LIGACAO_AGUA = "ligacaoAgua";

	public final static String LIGACAO_AGUA_ID = "ligacaoAgua.id";

	public final static String LIGACAO_AGUA_HIDROMETRO_INSTALACAO_HIST = "ligacaoAgua.hidrometroInstalacaoHistorico";

	public final static String LIGACAO_ESGOTO = "ligacaoEsgoto";

	public final static String LIGACAO_ESGOTO_ID = "ligacaoEsgoto.id";

	public final static String LIGACAO_ESGOTO_PERFIL = "ligacaoEsgoto.ligacaoEsgotoPerfil";

	public final static String LIGACAO_ESGOTO_SITUACAO = "ligacaoEsgotoSituacao";

	public final static String LIGACAO_ESGOTO_SITUACAO_ID = "ligacaoEsgotoSituacao.id";

	public final static String QUADRA_ROTA_FATURAMENTO_GRUPO_ID = "rota.faturamento_grupo.id";

	public final static String ROTA_FATURAMENTO_GRUPO = "rota.faturamentoGrupo";

	public final static String ROTA_FATURAMENTO_GRUPO_ID = "rota.faturamentoGrupo.id";

	/**
	 * Description of the Field
	 */
	public final static String ROTA = "rota";

	public final static String ROTA_ID = "rota.id";

	public final static String NUMERO_SEGMENTO = "numeroSegmento";

	public final static String LOTE = "lote";

	public final static String SUBLOTE = "subLote";

	public final static String CLIENTES_IMOVEIS = "clienteImoveis";

	public final static String CLIENTES_IMOVEIS_CLIENTE_ID = "clienteImoveis.cliente.id";

	public final static String INDICADOR_IMOVEL_CONDOMINIO = "indicadorImovelCondominio";

	public final static String HIDROMETRO_INSTALACAO_HISTORICO_ID = "hidrometroInstalacaoHistorico.id";

	public final static String IMOVEL_CONDOMINIO = "imovelCondominio";

	public final static String IMOVEL_CONDOMINIO_ID = "imovelCondominio.id";

	public final static String IMOVEL = "imovel";

	public final static String SETOR_COMERCIAL_MUNICIPIO = "setorComercial.municipio";

	public final static String SETOR_COMERCIAL_MUNICIPIO_ID = "setorComercial.municipio.id";

	public final static String SETOR_COMERCIAL_MUNICIPIO_UF = "setorComercial.municipio.unidadeFederacao";

	public final static String SETOR_COMERCIAL_MUNICIPIO_UF_ID = "setorComercial.municipio.unidadeFederacao.id";

	public final static String GERENCIA_REGIONAL_ID = "quadra.setorComercial.localidade.gerenciaRegional.id";

	public final static String LIGACAO_AGUA_HIDROMETRO_INSTALACAO_HISTORICO_ID = "ligacaoAgua.hidrometroInstalacaoHistorico.id";

	public final static String IMOVEL_HIDROMETRO_INSTALACAO_HISTORICO_ID = "imovel.hidrometroInstalacaoHistorico.id";

	public final static String ID_IMOVEL_PRINCIPAL = "imovelPrincipal.id";

	public final static String IMOVEL_PRINCIPAL = "imovelPrincipal";

	public final static String ID_NOME_CONTA = "nomeConta.id";

	public final static String CLIENTES_IMOVEL_RELACAO_TIPO = "clienteImoveis.clienteRelacaoTipo";

	public final static String CLIENTES_IMOVEIS_CLIENTE_RELACAO_TIPO_ID = "clienteImoveis.clienteRelacaoTipo.id";

	public final static String INDICADOR_IMOVEL_EXCLUIDO = "indicadorExclusao";

	public final static String ENDERECO_REFERENCIA = "enderecoReferencia";

	public final static String EMPRESA = "rota.empresa";

	public final static String CONSUMO_TARIFA = "consumoTarifa";

	public final static String CONSUMO_TARIFA_ID = "consumoTarifa.id";

	public final static String AREA_CONSTRUIDA_FAIXA = "areaConstruidaFaixa";

	public final static String AREA_CONSTRUIDA_FAIXA_ID = "areaConstruidaFaixa.id";

	public final static String FOTO_FACHADA = "fotoFachada";

	public final static String PAVIMENTO_CALCADA = "pavimentoCalcada";

	public final static String PAVIMENTO_RUA = "pavimentoRua";

	public final static String IMOVEL_PERFIL = "imovelPerfil";

	public final static String IMOVEL_PERFIL_ID = "imovelPerfil.id";

	public final static String RESERVATORIO_VOLUME_FAIXA_SUPERIOR = "reservatorioVolumeFaixaSuperior";

	public final static String RESERVATORIO_VOLUME_FAIXA_SUPERIOR_ID = "reservatorioVolumeFaixaSuperior.id";

	public final static String RESERVATORIO_VOLUME_FAIXA_INFERIOR = "reservatorioVolumeFaixaInferior";

	public final static String RESERVATORIO_VOLUME_FAIXA_INFERIOR_ID = "reservatorioVolumeFaixaInferior.id";

	public final static String POCO_TIPO = "pocoTipo";

	public final static String POCO_TIPO_ID = "pocoTipo.id";

	public final static String DESPEJO = "despejo";

	public final static String DESPEJO_ID = "despejo.id";

	public final static String PISCINA_VOLUME_FAIXA = "piscinaVolumeFaixa";

	public final static String PISCINA_VOLUME_FAIXA_ID = "piscinaVolumeFaixa.id";

	public final static String IMOVEL_SUBCATEGORIAS = "imovelSubcategorias";

	public final static String IMOVEL_CONTA_ENVIO = "imovelContaEnvio";

	public final static String FUNCIONARIO = "funcionario";

	public static final String PADRAO_CONSTRUCAO = "padraoConstrucao";

	public static final String PADRAO_CONSTRUCAO_ID = "padraoConstrucao.id";

	public static final String ESGOTAMENTO = "esgotamento";

	public static final String RENDA_FAMILIAR_FAIXA = "rendaFamiliarFaixa";

	public static final String RENDA_FAMILIAR_FAIXA_ID = "rendaFamiliarFaixa.id";

	public static final String HIDROMETRO_INSTALACAO_HISTORICO = "hidrometroInstalacaoHistorico";

	public static final String MEDICAO_HISTORICOS = "medicaoHistoricos";

	public static final String MEDICAO_HISTORICOS_ANO_REFERENCIA = "medicaoHistoricos.anoMesReferencia";

	public static final String MEDICAO_HISTORICOS_CONSUMO_MEDIO_HIDROMETRO = "medicaoHistoricos.consumoMedioHidrometro";

	public static final String IMOVEL_ENDERECO_ANTERIOR = "imovelEnderecoAnterior";

	public static final String IMOVEL_ENDERECO_ANTERIOR_ID = "imovelEnderecoAnterior.id";

	public static final String LEITURA_ANORMALIDADE = "leituraAnormalidade";

	public static final String LEITURA_ANORMALIDADE_ID = "leituraAnormalidade.id";

	public static final String ELO_ANORMALIDADE = "eloAnormalidade";

	public static final String ELO_ANORMALIDADE_ID = "eloAnormalidade.id";

	public static final String COBRANCA_SITUACAO_TIPO = "cobrancaSituacaoTipo";

	public static final String COBRANCA_SITUACAO_TIPO_ID = "cobrancaSituacaoTipo.id";

	public static final String CADASTRO_OCORRENCIA = "cadastroOcorrencia";

	public static final String CADASTRO_OCORRENCIA_ID = "cadastroOcorrencia.id";

	public static final String FATURAMENTO_SITUACAO_TIPO = "faturamentoSituacaoTipo";

	public static final String FATURAMENTO_SITUACAO_TIPO_ID = "faturamentoSituacaoTipo.id";

	public static final String FONTE_ABASTECIMENTO = "fonteAbastecimento";

	public static final String FONTE_ABASTECIMENTO_ID = "fonteAbastecimento.id";

	public static final String FATURAMENTO_TIPO = "faturamentoTipo";

	public static final String FATURAMENTO_TIPO_ID = "faturamentoTipo.id";

	public static final String FATURAMENTO_SITUACAO_MOTIVO = "faturamentoSituacaoMotivo";

	public static final String FATURAMENTO_SITUACAO_MOTIVO_ID = "faturamentoSituacaoMotivo.id";

	public static final String LOGRADOURO_BAIRRO = "logradouroBairro";

	public static final String LOGRADOURO_BAIRRO_ID = "logradouroBairro.id";

	public static final String LOGRADOURO_CEP = "logradouroCep";

	public static final String LOGRADOURO_CEP_ID = "logradouroCep.id";

	public static final String COBRANCA_SITUACAO = "cobrancaSituacao";

	public static final String COBRANCA_SITUACAO_ID = "cobrancaSituacao.id";

	public final static String DISTRITO_OPERACIONAL = "distritoOperacional";

	public final static String UNIDADE_NEGOCIO = "localidade.unidadeNegocio";

	public final static String INDICADOR_EMISSAO_EXTRATO_FATURAMENTO = "indicadorEmissaoExtratoFaturamento";

	public final static String TESTADA_LOTE = "testadaLote";
}
