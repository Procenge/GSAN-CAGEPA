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
 * GSANPCG
 * Virgínia Melo
 * Saulo Lima
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

import gcom.atendimentopublico.ordemservico.*;
import gcom.cadastro.localidade.Localidade;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.struts.validator.ValidatorForm;

/**
 * [SB0001]Atualizar Tipo Perfil de Serviço
 * 
 * @author Kássia Albuquerque
 * @date 30/10/2006
 */

public class AtualizarTipoServicoActionForm
				extends ValidatorForm {

	private static final long serialVersionUID = 1L;

	private String idTipoServico;

	private String codigoServico;

	private String descricao;

	private String abreviada;

	private String subgrupo;

	private String valor;

	private String pavimento;

	private String atualizacaoComercial;

	private String servicoTerceirizado;

	private String indicadorFiscalizacaoInfracao;

	private String indicadorVistoria;

	private String tempoMedioIncial;

	private String tempoMedioFinal;

	private String tempoMedioExecucao;

	private String idTipoDebito;

	private String descricaoTipoDebito;

	private String idTipoCredito;

	private String idPrioridadeServico;

	private String perfilServico;

	private String descricaoPerfilServico;

	private String idTipoServicoReferencia;

	private String descricaoTipoServicoReferencia;

	private String indicadorAtividadeUnica;

	private String indicadorUso;

	private String idAtividadeTipoServico;

	private String idAtividade;

	private String descricaoAtividadeTipoServico;

	private String valorLocalidadeId;

	private String servicoTipoValorLocalidadeId;

	private String servicoTipoValorLocalidadeDescricao;

	private String servicoTipoValorLocalidadeValor;

	private String idMaterialTipoServico;

	private String idMaterial;

	private String descricaoMaterialTipoServico;

	private String servicoTipoMaterialQuantidadePadrao;

	private String indicadorAtualizar;

	private String servicoTipoAtividadeOrdemExecucao;

	private String valorServico;

	private String descricaoTipoCredito;

	private String method;

	private String valorRemuneracao;

	private String percentualRemuneracao;

	private String prazoExecucao;

	private String tipoRemuneracao;

	private String idOrdemServicoLayout;

	private String indicadorDeslocamento;

	private String indicadorHorariosExecucao;

	private String indicadorCausaOcorrencia;

	private String indicadorRedeRamalAgua;

	private String indicadorRedeRamalEsgoto;

	private String indicadorMaterial;

	private String indicadorVala;

	private String indicadorOrdemSeletiva;

	private String indicadorServicoCritico;

	Collection servicoTipoAtividades = new ArrayList();

	Collection servicoTipoMateriais = new ArrayList();

	Collection<ServicoTipoValorLocalidade> servicoTipoValorLocalidade = new ArrayList<ServicoTipoValorLocalidade>();
	
	
	
	


	private Short indicadorGerarHistoricoImovel;

	public void addServicoTipoAtividade(){

		Atividade atv;
		ServicoTipoAtividade stp;
		atv = new Atividade();
		atv.setId(Integer.parseInt(getIdAtividade()));
		atv.setDescricao(getDescricaoAtividadeTipoServico());
		stp = new ServicoTipoAtividade();
		stp.setNumeroExecucao(Short.parseShort(getServicoTipoAtividadeOrdemExecucao()));
		stp.setAtividade(atv);
		servicoTipoAtividades.add(stp);
	}

	public void removeServicoTipoAtividade(){

		for(Iterator iter = getServicoTipoAtividades().iterator(); iter.hasNext();){
			ServicoTipoAtividade stp = (ServicoTipoAtividade) iter.next();
			String string = "$" + stp.getAtividade().getId() + "$" + stp.getNumeroExecucao() + "$";
			if(string.equals(getIdAtividadeTipoServico())){
				iter.remove();
			}
		}
	}

	public void removeAllServicoTipoAtividade(){

		for(Iterator iter = getServicoTipoAtividades().iterator(); iter.hasNext();){
			iter.next();
			iter.remove();
		}
	}

	public void addServicoTipoValorLocalidade(){

		Localidade loca = new Localidade();
		ServicoTipoValorLocalidade stvl = new ServicoTipoValorLocalidade();

		loca.setId(Util.converterStringParaInteger(getValorLocalidadeId()));
		loca.setDescricao(getServicoTipoValorLocalidadeDescricao());
		stvl.setValorServico(Util.formatarStringParaBigDecimal(getServicoTipoValorLocalidadeValor(), 2, true));
		stvl.setLocalidade(loca);
		stvl.setIndicadorUso(ConstantesSistema.SIM);
		stvl.setUltimaAlteracao(new Date());

		servicoTipoValorLocalidade.add(stvl);
	}

	public void removeServicoTipoValorLocalidade(){

		for(Iterator iter = getServicoTipoValorLocalidade().iterator(); iter.hasNext();){
			ServicoTipoValorLocalidade stvl = (ServicoTipoValorLocalidade) iter.next();
			String string = stvl.getLocalidade().getId().toString();
			if(string.equals(getValorLocalidadeId())){
				iter.remove();
			}
		}
	}

	public void addServicoTipoMaterial(){

		Material mat;
		ServicoTipoMaterial stm;
		mat = new Material();
		mat.setId(Integer.parseInt(getIdMaterial()));
		mat.setDescricao(getDescricaoMaterialTipoServico());
		stm = new ServicoTipoMaterial();
		try{
			stm.setQuantidadePadrao(new BigDecimal(getServicoTipoMaterialQuantidadePadrao()));
		}catch(NumberFormatException e){
		}
		stm.setMaterial(mat);
		servicoTipoMateriais.add(stm);
	}

	public void removeServicoTipoMaterial(){

		for(Iterator iter = getServicoTipoMateriais().iterator(); iter.hasNext();){
			ServicoTipoMaterial stm = (ServicoTipoMaterial) iter.next();
			String string = "$" + stm.getMaterial().getId() + "$";
			if(string.equals(getIdMaterialTipoServico())){
				iter.remove();
			}
		}
	}

	public String getDescricaoTipoCredito(){

		return descricaoTipoCredito;
	}

	public void setDescricaoTipoCredito(String descricaoTipoCredito){

		this.descricaoTipoCredito = descricaoTipoCredito;
	}

	public ServicoPerfilTipo setFormValues(ServicoPerfilTipo servicoPerfilTipo){

		/*
		 * Campos obrigatórios
		 */

		// Descrição
		/*
		 * Campos opcionais
		 */

		// data da retirada
		return servicoPerfilTipo;
	}

	public String getAbreviada(){

		return abreviada;
	}

	public void setAbreviada(String abreviada){

		this.abreviada = abreviada;
	}

	public String getAtualizacaoComercial(){

		return atualizacaoComercial;
	}

	public void setAtualizacaoComercial(String atualizacaoComercial){

		this.atualizacaoComercial = atualizacaoComercial;
	}

	public String getCodigoServico(){

		return codigoServico;
	}

	public void setCodigoServico(String codigoServico){

		this.codigoServico = codigoServico;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAtividadeTipoServico(){

		return descricaoAtividadeTipoServico;
	}

	public void setDescricaoAtividadeTipoServico(String descricaoAtividadeTipoServico){

		this.descricaoAtividadeTipoServico = descricaoAtividadeTipoServico;
	}

	public String getValorLocalidadeId(){

		return valorLocalidadeId;
	}

	public void setValorLocalidadeId(String valorLocalidadeId){

		this.valorLocalidadeId = valorLocalidadeId;
	}

	public String getServicoTipoValorLocalidadeId(){

		return servicoTipoValorLocalidadeId;
	}

	public void setServicoTipoValorLocalidadeId(String servicoTipoValorLocalidadeId){

		this.servicoTipoValorLocalidadeId = servicoTipoValorLocalidadeId;
	}

	public String getServicoTipoValorLocalidadeDescricao(){

		return servicoTipoValorLocalidadeDescricao;
	}

	public void setServicoTipoValorLocalidadeDescricao(String servicoTipoValorLocalidadeDescricao){

		this.servicoTipoValorLocalidadeDescricao = servicoTipoValorLocalidadeDescricao;
	}

	public String getServicoTipoValorLocalidadeValor(){

		return servicoTipoValorLocalidadeValor;
	}

	public void setServicoTipoValorLocalidadeValor(String servicoTipoValorLocalidadeValor){

		this.servicoTipoValorLocalidadeValor = servicoTipoValorLocalidadeValor;
	}

	public Collection<ServicoTipoValorLocalidade> getServicoTipoValorLocalidade(){

		return servicoTipoValorLocalidade;
	}

	public void setServicoTipoValorLocalidade(Collection<ServicoTipoValorLocalidade> servicoTipoValorLocalidade){

		this.servicoTipoValorLocalidade = servicoTipoValorLocalidade;
	}


	public String getDescricaoMaterialTipoServico(){

		return descricaoMaterialTipoServico;
	}

	public void setDescricaoMaterialTipoServico(String descricaoMaterialTipoServico){

		this.descricaoMaterialTipoServico = descricaoMaterialTipoServico;
	}

	public String getDescricaoPerfilServico(){

		return descricaoPerfilServico;
	}

	public void setDescricaoPerfilServico(String descricaoPerfilServico){

		this.descricaoPerfilServico = descricaoPerfilServico;
	}

	public String getDescricaoTipoDebito(){

		return descricaoTipoDebito;
	}

	public void setDescricaoTipoDebito(String descricaoTipoDebito){

		this.descricaoTipoDebito = descricaoTipoDebito;
	}

	public String getIdAtividadeTipoServico(){

		return idAtividadeTipoServico;
	}

	public void setIdAtividadeTipoServico(String idAtividadeTipoServico){

		this.idAtividadeTipoServico = idAtividadeTipoServico;
	}

	public String getIdMaterialTipoServico(){

		return idMaterialTipoServico;
	}

	public void setIdMaterialTipoServico(String idMaterialTipoServico){

		this.idMaterialTipoServico = idMaterialTipoServico;
	}

	public String getIdPrioridadeServico(){

		return idPrioridadeServico;
	}

	public void setIdPrioridadeServico(String idPrioridadeServico){

		this.idPrioridadeServico = idPrioridadeServico;
	}

	public String getIdTipoCredito(){

		return idTipoCredito;
	}

	public void setIdTipoCredito(String idTipoCredito){

		this.idTipoCredito = idTipoCredito;
	}

	public String getIdTipoDebito(){

		return idTipoDebito;
	}

	public void setIdTipoDebito(String idTipoDebito){

		this.idTipoDebito = idTipoDebito;
	}

	public String getIdTipoServico(){

		return idTipoServico;
	}

	public void setIdTipoServico(String idTipoServico){

		this.idTipoServico = idTipoServico;
	}

	public String getIdTipoServicoReferencia(){

		return idTipoServicoReferencia;
	}

	public void setIdTipoServicoReferencia(String idTipoServicoReferencia){

		this.idTipoServicoReferencia = idTipoServicoReferencia;
	}

	public String getIndicadorAtividadeUnica(){

		return indicadorAtividadeUnica;
	}

	public void setIndicadorAtividadeUnica(String indicadorAtividadeUnica){

		this.indicadorAtividadeUnica = indicadorAtividadeUnica;
	}

	public String getIndicadorAtualizar(){

		return indicadorAtualizar;
	}

	public void setIndicadorAtualizar(String indicadorAtualizar){

		this.indicadorAtualizar = indicadorAtualizar;
	}

	public String getIndicadorFiscalizacaoInfracao(){

		return indicadorFiscalizacaoInfracao;
	}

	public void setIndicadorFiscalizacaoInfracao(String indicadorFiscalizacaoInfracao){

		this.indicadorFiscalizacaoInfracao = indicadorFiscalizacaoInfracao;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getIndicadorVistoria(){

		return indicadorVistoria;
	}

	public void setIndicadorVistoria(String indicadorVistoria){

		this.indicadorVistoria = indicadorVistoria;
	}

	public String getPavimento(){

		return pavimento;
	}

	public void setPavimento(String pavimento){

		this.pavimento = pavimento;
	}

	public String getPerfilServico(){

		return perfilServico;
	}

	public void setPerfilServico(String perfilServico){

		this.perfilServico = perfilServico;
	}

	public String getServicoTerceirizado(){

		return servicoTerceirizado;
	}

	public void setServicoTerceirizado(String servicoTerceirizado){

		this.servicoTerceirizado = servicoTerceirizado;
	}

	public String getServicoTipoAtividadeOrdemExecucao(){

		return servicoTipoAtividadeOrdemExecucao;
	}

	public void setServicoTipoAtividadeOrdemExecucao(String servicoTipoAtividadeOrdemExecucao){

		this.servicoTipoAtividadeOrdemExecucao = servicoTipoAtividadeOrdemExecucao;
	}

	public String getServicoTipoMaterialQuantidadePadrao(){

		return servicoTipoMaterialQuantidadePadrao;
	}

	public void setServicoTipoMaterialQuantidadePadrao(String servicoTipoMaterialQuantidadePadrao){

		this.servicoTipoMaterialQuantidadePadrao = servicoTipoMaterialQuantidadePadrao;
	}

	public String getSubgrupo(){

		return subgrupo;
	}

	public void setSubgrupo(String subgrupo){

		this.subgrupo = subgrupo;
	}

	public String getTempoMedioFinal(){

		return tempoMedioFinal;
	}

	public void setTempoMedioFinal(String tempoMedioFinal){

		this.tempoMedioFinal = tempoMedioFinal;
	}

	public String getTempoMedioIncial(){

		return tempoMedioIncial;
	}

	public void setTempoMedioIncial(String tempoMedioIncial){

		this.tempoMedioIncial = tempoMedioIncial;
	}

	public String getValorServico(){

		return valorServico;
	}

	public void setValorServico(String valorServico){

		this.valorServico = valorServico;
	}

	public String getMethod(){

		return method;
	}

	public void setMethod(String method){

		this.method = method;
	}

	public String getIdAtividade(){

		return idAtividade;
	}

	public void setIdAtividade(String idAtividade){

		this.idAtividade = idAtividade;
	}

	public String getIdMaterial(){

		return idMaterial;
	}

	public void setIdMaterial(String idMaterial){

		this.idMaterial = idMaterial;
	}

	public String getDescricaoTipoServicoReferencia(){

		return descricaoTipoServicoReferencia;
	}

	public void setDescricaoTipoServicoReferencia(String descricaoTipoServicoReferencia){

		this.descricaoTipoServicoReferencia = descricaoTipoServicoReferencia;
	}

	public Collection getServicoTipoAtividades(){

		return servicoTipoAtividades;
	}

	public void setServicoTipoAtividades(Collection servicoTipoAtividades){

		this.servicoTipoAtividades = servicoTipoAtividades;
	}

	public Collection getServicoTipoMateriais(){

		return servicoTipoMateriais;
	}

	public void setServicoTipoMateriais(Collection servicoTipoMateriais){

		this.servicoTipoMateriais = servicoTipoMateriais;
	}

	public String getValor(){

		return valor;
	}

	public void setValor(String valor){

		this.valor = valor;
	}

	public String getTempoMedioExecucao(){

		return tempoMedioExecucao;
	}

	public void setTempoMedioExecucao(String tempoMedioExecucao){

		this.tempoMedioExecucao = tempoMedioExecucao;
	}

	public String getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(String valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public String getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(String percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public String getPrazoExecucao(){

		return prazoExecucao;
	}

	public void setPrazoExecucao(String prazoExecucao){

		this.prazoExecucao = prazoExecucao;
	}

	public String getTipoRemuneracao(){

		return tipoRemuneracao;
	}

	public void setTipoRemuneracao(String tipoRemuneracao){

		this.tipoRemuneracao = tipoRemuneracao;
	}

	public String getIndicadorDeslocamento(){

		return indicadorDeslocamento;
	}

	public void setIndicadorDeslocamento(String indicadorDeslocamento){

		this.indicadorDeslocamento = indicadorDeslocamento;
	}

	public String getIndicadorHorariosExecucao(){

		return indicadorHorariosExecucao;
	}

	public void setIndicadorHorariosExecucao(String indicadorHorariosExecucao){

		this.indicadorHorariosExecucao = indicadorHorariosExecucao;
	}

	public String getIndicadorCausaOcorrencia(){

		return indicadorCausaOcorrencia;
	}

	public void setIndicadorCausaOcorrencia(String indicadorCausaOcorrencia){

		this.indicadorCausaOcorrencia = indicadorCausaOcorrencia;
	}

	public String getIndicadorRedeRamalAgua(){

		return indicadorRedeRamalAgua;
	}

	public void setIndicadorRedeRamalAgua(String indicadorRedeRamalAgua){

		this.indicadorRedeRamalAgua = indicadorRedeRamalAgua;
	}

	public String getIndicadorRedeRamalEsgoto(){

		return indicadorRedeRamalEsgoto;
	}

	public void setIndicadorRedeRamalEsgoto(String indicadorRedeRamalEsgoto){

		this.indicadorRedeRamalEsgoto = indicadorRedeRamalEsgoto;
	}

	public String getIndicadorMaterial(){

		return indicadorMaterial;
	}

	public void setIndicadorMaterial(String indicadorMaterial){

		this.indicadorMaterial = indicadorMaterial;
	}

	public String getIndicadorVala(){

		return indicadorVala;
	}

	public void setIndicadorVala(String indicadorVala){

		this.indicadorVala = indicadorVala;
	}

	public String getIdOrdemServicoLayout(){

		return idOrdemServicoLayout;
	}

	public void setIdOrdemServicoLayout(String idOrdemServicoLayout){

		this.idOrdemServicoLayout = idOrdemServicoLayout;
	}

	public String getIndicadorOrdemSeletiva(){

		return indicadorOrdemSeletiva;
	}

	public void setIndicadorOrdemSeletiva(String indicadorOrdemSeletiva){

		this.indicadorOrdemSeletiva = indicadorOrdemSeletiva;
	}

	public String getIndicadorServicoCritico(){

		return indicadorServicoCritico;
	}

	public void setIndicadorServicoCritico(String indicadorServicoCritico){

		this.indicadorServicoCritico = indicadorServicoCritico;
	}

	public Short getIndicadorGerarHistoricoImovel(){

		return indicadorGerarHistoricoImovel;
	}

	public void setIndicadorGerarHistoricoImovel(Short indicadorGerarHistoricoImovel){

		this.indicadorGerarHistoricoImovel = indicadorGerarHistoricoImovel;
	}


}
