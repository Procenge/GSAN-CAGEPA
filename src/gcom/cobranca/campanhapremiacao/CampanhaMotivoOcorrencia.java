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
 * GSANPCG
 * Virgínia Melo
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

package gcom.cobranca.campanhapremiacao;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;

public class CampanhaMotivoOcorrencia
				extends ObjetoTransacao {
	
	private static final String DESCRICAO_NOME_CLIENTE_USUARIO_DIVERGE = "Nome do cliente diverge do nome do usuario do imovel.";

	private static final String DESCRICAO_NOME_CLIENTE_RESPONSAVEl_DIVERGE = "Nome do cliente diverge do nome do responsavel do imovel.";

	private static final String DESCRICAO_NOME_CLIENTE_USUA_RESP_DIVERGE = "Nome do cliente diverge do nome do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_CPF_CNPJ_CLIENTE_USUARIO_DIVERGE = "CPF/CNPJ do cliente diverge do CPF/CNPJ do usuario do imovel.";

	private static final String DESCRICAO_CPF_CNPJ_CLIENTE_RESPONSAVEL_DIVERGE = "CPF/CNPJ do cliente diverge do CPF/CNPJ do responsavel do imovel.";

	private static final String DESCRICAO_CPF_CNPJ_CLIENTE_USUA_RESP_DIVERGE = "CPF/CNPJ do cliente diverge do CPF/CNPJ do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_RG_CLIENTE_USUARIO_DIVERGE = "RG do cliente diverge do RG do usuario do imovel.";

	private static final String DESCRICAO_RG_CLIENTE_RESPONSAVEL_DIVERGE = "RG do cliente diverge do RG do responsavel do imovel.";

	private static final String DESCRICAO_RG_CLIENTE_USUA_RESP_DIVERGE = "RG do cliente diverge do RG do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_DT_EMISSAO_RG_CLIENTE_USUARIO_DIVERGE = "Dt.Emissao do RG do cliente diverge da data de emissao do RG do usuario do imovel.";

	private static final String DESCRICAO_DT_EMISSAO_RG_CLIENTE_RESPONSAVEL_DIVERGE = "Dt.Emissao do RG do cliente diverge da data de emissao do RG do responsavel do imovel.";

	private static final String DESCRICAO_DT_EMISSAO_RG_CLIENTE_USUA_RESP_DIVERGE = "Dt.Emissao do RG do cliente diverge da data de emissao do RG do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_ORGAO_EXP_CLIENTE_USUARIO_DIVERGE = "Orgao Expedidor do cliente diverge do Orgao Expedidor do usuario do imovel.";

	private static final String DESCRICAO_ORGAO_EXP_CLIENTE_RESPONSAVEL_DIVERGE = "Orgao Expedidor do cliente diverge do Orgao Expedidor do responsavel do imovel.";

	private static final String DESCRICAO_ORGAO_EXP_CLIENTE_USUA_RESP_DIVERGE = "Orgao Expedidor do cliente diverge do Orgao Expedidor do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_ESTADO_RG_CLIENTE_USUARIO_DIVERGE = "Estado do RG do cliente diverge do Estado do RG do usuario do imovel.";

	private static final String DESCRICAO_ESTADO_RG_CLIENTE_RESPONSAVEL_DIVERGE = "Estado do RG do cliente diverge do Estado do RG do responsavel do imovel.";

	private static final String DESCRICAO_ESTADO_RG_CLIENTE_USUA_RESP_DIVERGE = "Estado do RG do cliente diverge do Estado do RG do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_DT_NASC_CLIENTE_USUARIO_DIVERGE = "Data Nascimento do cliente diverge da data de nascimento do usuario do imovel.";

	private static final String DESCRICAO_DT_NASC_CLIENTE_RESPONSAVEL_DIVERGE = "Data Nascimento do cliente diverge da data de nascimento do responsavel do imovel.";

	private static final String DESCRICAO_DT_NASC_CLIENTE_USUA_RESP_DIVERGE = "Data Nascimento do cliente diverge da data de nascimento do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_NOME_MAE_CLIENTE_USUARIO_DIVERGE = "Nome da Mae do cliente diverge do nome da mae do usuario do imovel.";

	private static final String DESCRICAO_NOME_MAE_CLIENTE_RESPONSAVEL_DIVERGE = "Nome da Mae do cliente diverge do nome da mae do responsavel do imovel.";

	private static final String DESCRICAO_NOME_MAE_CLIENTE_USUA_RESP_DIVERGE = "Nome da Mae do cliente diverge do nome da mae do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_EMAIL_CLIENTE_USUARIO_DIVERGE = "E-mail do cliente diverge do e-mail do usuario do imovel.";

	private static final String DESCRICAO_EMAIL_CLIENTE_RESPONSAVEL_DIVERGE = "E-mail do cliente diverge do e-mail do responsavel do imovel.";

	private static final String DESCRICAO_EMAIL_CLIENTE_USUA_RESP_DIVERGE = "E-mail do cliente diverge do e-mail do usuario e do responsavel do imovel.";

	private static final String DESCRICAO_TIPO_TELEFONE_CLIENTE_USUARIO_DIVERGE = "Tipo do Telefone novo para o usuario do imovel.";

	private static final String DESCRICAO_TIPO_TELEFONE_CLIENTE_RESPONSAVEL_DIVERGE = "Tipo do Telefone novo para o responsavel do imovel.";

	private static final String DESCRICAO_TIPO_TELEFONE_CLIENTE_USUA_RESP_DIVERGE = "Tipo do Telefone novo para o usuario e o responsavel do imovel.";

	private static final String DESCRICAO_DADOS_FONE_CLIENTE_USUARIO_DIVERGE = "Dados do Fone divergem dos dados do fone do usuario do imovel.";

	private static final String DESCRICAO_DADOS_FONE_CLIENTE_RESPONSAVEL_DIVERGE = "Dados do Fone divergem dos dados do fone do responsavel do imovel.";

	private static final String DESCRICAO_DADOS_FONE_CLIENTE_USUA_RESP_DIVERGE = "Dados do Fone divergem dos dados do fone do usuario e do responsavel do imovel.";

	public static final String DESCRICAO_NOME_CLIENTE = "DESCRICAO_NOME_CLIENTE";

	public static final String DESCRICAO_CPF_CNPJ = "DESCRICAO_CPF_CNPJ";

	public static final String DESCRICAO_RG = "DESCRICAO_RG";

	public static final String DESCRICAO_DT_EMISSAO_RG = "DESCRICAO_DT_EMISSAO_RG";

	public static final String DESCRICAO_ORGAO_EXP = "DESCRICAO_ORGAO_EXP";

	public static final String DESCRICAO_ESTADO_RG = "DESCRICAO_ESTADO_RG";

	public static final String DESCRICAO_DT_NASC = "DESCRICAO_DT_NASC";

	public static final String DESCRICAO_NOME_MAE = "DESCRICAO_NOME_MAE";

	public static final String DESCRICAO_EMAIL = "DESCRICAO_EMAIL";

	public static final String DESCRICAO_TIPO_TELEFONE = "DESCRICAO_TIPO_TELEFONE";

	public static final String DESCRICAO_DADOS_FONE = "DESCRICAO_DADOS_FONE";

	public static String retornaDescricaoMotivo(Integer tipoRelacaoCliente, String descricao){

		String retorno = "";

		if(descricao.equals(DESCRICAO_CPF_CNPJ)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_CPF_CNPJ_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_CPF_CNPJ_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_CPF_CNPJ_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_DADOS_FONE)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_DADOS_FONE_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_DADOS_FONE_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_DADOS_FONE_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_DT_EMISSAO_RG)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_DT_EMISSAO_RG_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_DT_EMISSAO_RG_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_DT_EMISSAO_RG_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_DT_NASC)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_DT_NASC_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_DT_NASC_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_DT_NASC_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_EMAIL)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_EMAIL_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_EMAIL_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_EMAIL_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_ESTADO_RG)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_ESTADO_RG_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_ESTADO_RG_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_ESTADO_RG_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_NOME_CLIENTE)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_NOME_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_NOME_CLIENTE_RESPONSAVEl_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_NOME_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_NOME_MAE)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_NOME_MAE_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_NOME_MAE_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_NOME_MAE_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_ORGAO_EXP)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_ORGAO_EXP_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_ORGAO_EXP_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_ORGAO_EXP_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_RG)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_RG_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_RG_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_RG_CLIENTE_USUA_RESP_DIVERGE;
			}
		}else if(descricao.equals(DESCRICAO_TIPO_TELEFONE)){
			if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO)){
				return DESCRICAO_TIPO_TELEFONE_CLIENTE_USUARIO_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_RESPONSAVEL)){
				return DESCRICAO_TIPO_TELEFONE_CLIENTE_RESPONSAVEL_DIVERGE;
			}else if(tipoRelacaoCliente.equals(CampanhaCadastro.TIPO_RELACAO_USUARIO_RESPONSAVEL)){
				return DESCRICAO_TIPO_TELEFONE_CLIENTE_USUA_RESP_DIVERGE;
			}
		}

		return retorno;
	}

	private static final long serialVersionUID = 1L;

	private Integer id;

	private String descricao;

	private Short indicadorUso;

	private Date ultimaAlteracao;

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public boolean equals(Object objeto){

		try{
			boolean a = ((CampanhaMotivoOcorrencia) objeto).getDescricao().equals(this.getDescricao());

			return (a);

		}catch(ClassCastException ex){
			return false;
		}
	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}


	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

}
