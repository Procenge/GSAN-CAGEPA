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

package gcom.cobranca.bean;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.debito.DebitoCreditoSituacao;

/**
 * @author gcom
 */
public class ContaHelper
				extends Conta {

	private Integer idImovel;

	private Integer idContaMotivoRevisao;

	private Integer idDebitoCreditoSituacaoAtual;

	private String descricaoDebitoCreditoSituacaoAtual;

	private String descricaoAbreviadaDebitoCreditoSituacaoAtual;

	private Integer idLigacaoAguaSituacao;

	private Integer idLigacaoEsgotoSituacao;

	private Integer idLocalidade;

	@Override
	public Imovel getImovel(){

		if(idImovel != null && super.getImovel() == null){
			super.setImovel(new Imovel());
			super.getImovel().setId(idImovel);
		}
		return super.getImovel();
	}

	public void setIdImovel(Integer idImovel){

		if(super.getImovel() != null){
			super.getImovel().setId(idImovel);
		}
		this.idImovel = idImovel;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	@Override
	public ContaMotivoRevisao getContaMotivoRevisao(){

		if(idContaMotivoRevisao != null && super.getContaMotivoRevisao() == null){
			super.setContaMotivoRevisao(new ContaMotivoRevisao());
			super.getContaMotivoRevisao().setId(idContaMotivoRevisao);
		}
		return super.getContaMotivoRevisao();
	}

	public void setIdContaMotivoRevisao(Integer idContaMotivoRevisao){

		if(super.getContaMotivoRevisao() != null){
			super.getContaMotivoRevisao().setId(idContaMotivoRevisao);
		}
		this.idContaMotivoRevisao = idContaMotivoRevisao;
	}

	public Integer getIdContaMotivoRevisao(){

		return idContaMotivoRevisao;
	}

	@Override
	public DebitoCreditoSituacao getDebitoCreditoSituacaoAtual(){

		if(idDebitoCreditoSituacaoAtual != null && super.getDebitoCreditoSituacaoAtual() == null){
			super.setDebitoCreditoSituacaoAtual(new DebitoCreditoSituacao());
			super.getDebitoCreditoSituacaoAtual().setId(idDebitoCreditoSituacaoAtual);
			super.getDebitoCreditoSituacaoAtual().setDescricaoAbreviada(descricaoAbreviadaDebitoCreditoSituacaoAtual);
			super.getDebitoCreditoSituacaoAtual().setDescricaoDebitoCreditoSituacao(descricaoDebitoCreditoSituacaoAtual);
		}
		return super.getDebitoCreditoSituacaoAtual();
	}

	public void setIdDebitoCreditoSituacaoAtual(Integer idDebitoCreditoSituacaoAtual){

		if(super.getDebitoCreditoSituacaoAtual() != null){
			super.getDebitoCreditoSituacaoAtual().setId(idDebitoCreditoSituacaoAtual);
		}
		this.idDebitoCreditoSituacaoAtual = idDebitoCreditoSituacaoAtual;
	}

	public Integer getIdDebitoCreditoSituacaoAtual(){

		return idDebitoCreditoSituacaoAtual;
	}

	public void setDescricaoDebitoCreditoSituacaoAtual(String descricaoDebitoCreditoSituacaoAtual){

		if(super.getDebitoCreditoSituacaoAtual() != null){
			super.getDebitoCreditoSituacaoAtual().setDescricaoDebitoCreditoSituacao(descricaoDebitoCreditoSituacaoAtual);
		}
		this.descricaoDebitoCreditoSituacaoAtual = descricaoDebitoCreditoSituacaoAtual;
	}

	public String getDescricaoDebitoCreditoSituacaoAtual(){

		return descricaoDebitoCreditoSituacaoAtual;
	}

	public void setDescricaoAbreviadaDebitoCreditoSituacaoAtual(String descricaoAbreviadaDebitoCreditoSituacaoAtual){

		if(super.getDebitoCreditoSituacaoAtual() != null){
			super.getDebitoCreditoSituacaoAtual().setDescricaoAbreviada(descricaoAbreviadaDebitoCreditoSituacaoAtual);
		}
		this.descricaoAbreviadaDebitoCreditoSituacaoAtual = descricaoAbreviadaDebitoCreditoSituacaoAtual;
	}

	public String getDescricaoAbreviadaDebitoCreditoSituacaoAtual(){

		return descricaoAbreviadaDebitoCreditoSituacaoAtual;
	}

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		if(idLigacaoAguaSituacao != null && super.getLigacaoAguaSituacao() == null){
			super.setLigacaoAguaSituacao(new LigacaoAguaSituacao());
			super.getLigacaoAguaSituacao().setId(idLigacaoAguaSituacao);
		}
		return super.getLigacaoAguaSituacao();
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		if(super.getLigacaoAguaSituacao() != null){
			super.getLigacaoAguaSituacao().setId(idLigacaoAguaSituacao);
		}
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		if(idLigacaoEsgotoSituacao != null && super.getLigacaoEsgotoSituacao() == null){
			super.setLigacaoEsgotoSituacao(new LigacaoEsgotoSituacao());
			super.getLigacaoEsgotoSituacao().setId(idLigacaoEsgotoSituacao);
		}
		return super.getLigacaoEsgotoSituacao();
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		if(super.getLigacaoEsgotoSituacao() != null){
			super.getLigacaoEsgotoSituacao().setId(idLigacaoEsgotoSituacao);
		}
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public Localidade getLocalidade(){

		if(idLocalidade != null && super.getLocalidade() == null){
			super.setLocalidade(new Localidade());
			super.getLocalidade().setId(idLocalidade);
		}
		return super.getLocalidade();
	}

	public void setIdLocalidade(Integer idLocalidade){

		if(super.getLocalidade() != null){
			super.getLocalidade().setId(idLocalidade);
		}
		this.idLocalidade = idLocalidade;
	}

}
