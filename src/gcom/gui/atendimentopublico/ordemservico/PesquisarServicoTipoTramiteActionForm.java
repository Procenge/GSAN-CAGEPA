
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoTipoTramite;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.unidade.UnidadeOrganizacional;

import org.apache.struts.action.ActionForm;

/**
 * Pesquisar Serviço Tipo Trâmite
 * [UC0410] Inserir Serviço Tipo
 * [UC0412] Manter Tipo de Serviço
 * 
 * @author Hebert Falcão
 * @date 11/02/2012
 */
public class PesquisarServicoTipoTramiteActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String codigoSetorComercial;

	private String descricaoSetorComercial;

	private String idUnidadeOrganizacionalOrigem;

	private String descricaoUnidadeOrganizacionalOrigem;

	private String idUnidadeOrganizacionalDestino;

	private String descricaoUnidadeOrganizacionalDestino;

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public String getCodigoSetorComercial(){

		return codigoSetorComercial;
	}

	public void setCodigoSetorComercial(String codigoSetorComercial){

		this.codigoSetorComercial = codigoSetorComercial;
	}

	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	public String getIdUnidadeOrganizacionalOrigem(){

		return idUnidadeOrganizacionalOrigem;
	}

	public void setIdUnidadeOrganizacionalOrigem(String idUnidadeOrganizacionalOrigem){

		this.idUnidadeOrganizacionalOrigem = idUnidadeOrganizacionalOrigem;
	}

	public String getDescricaoUnidadeOrganizacionalOrigem(){

		return descricaoUnidadeOrganizacionalOrigem;
	}

	public void setDescricaoUnidadeOrganizacionalOrigem(String descricaoUnidadeOrganizacionalOrigem){

		this.descricaoUnidadeOrganizacionalOrigem = descricaoUnidadeOrganizacionalOrigem;
	}

	public String getIdUnidadeOrganizacionalDestino(){

		return idUnidadeOrganizacionalDestino;
	}

	public void setIdUnidadeOrganizacionalDestino(String idUnidadeOrganizacionalDestino){

		this.idUnidadeOrganizacionalDestino = idUnidadeOrganizacionalDestino;
	}

	public String getDescricaoUnidadeOrganizacionalDestino(){

		return descricaoUnidadeOrganizacionalDestino;
	}

	public void setDescricaoUnidadeOrganizacionalDestino(String descricaoUnidadeOrganizacionalDestino){

		this.descricaoUnidadeOrganizacionalDestino = descricaoUnidadeOrganizacionalDestino;
	}

	public void limparForm(){

		this.setId("");
		this.setIdLocalidade("");
		this.setDescricaoLocalidade("");
		this.setCodigoSetorComercial("");
		this.setDescricaoSetorComercial("");
		this.setIdUnidadeOrganizacionalOrigem("");
		this.setDescricaoUnidadeOrganizacionalOrigem("");
		this.setIdUnidadeOrganizacionalDestino("");
		this.setDescricaoUnidadeOrganizacionalDestino("");
	}

	public void preencherForm(ServicoTipoTramite servicoTipoTramite){

		// Id
		Integer idInt = servicoTipoTramite.getId();

		String idStr = "";

		if(idInt != null){
			idStr = Integer.toString(idInt);
		}

		this.setId(idStr);

		// Localidade
		Localidade localidade = servicoTipoTramite.getLocalidade();

		String idLocalidadeStr = "";
		String descricaoLocalidade = "";

		if(localidade != null){
			Integer id = localidade.getId();
			idLocalidadeStr = Integer.toString(id);

			descricaoLocalidade = localidade.getDescricao();
		}

		this.setIdLocalidade(idLocalidadeStr);
		this.setDescricaoLocalidade(descricaoLocalidade);

		// Setor Comercial
		SetorComercial setorComercial = servicoTipoTramite.getSetorComercial();

		String codigoSetorComercialStr = "";
		String descricaoSetorComercial = "";

		if(setorComercial != null){
			Integer codigo = setorComercial.getCodigo();
			codigoSetorComercialStr = Integer.toString(codigo);

			descricaoSetorComercial = setorComercial.getDescricao();
		}

		this.setCodigoSetorComercial(codigoSetorComercialStr);
		this.setDescricaoSetorComercial(descricaoSetorComercial);

		// Unidade Organizacional - Origem
		UnidadeOrganizacional unidadeOrganizacionalOrigem = servicoTipoTramite.getUnidadeOrganizacionalOrigem();

		String idUnidadeOrganizacionalOrigemStr = "";
		String descricaoUnidadeOrganizacionalOrigem = "";

		if(unidadeOrganizacionalOrigem != null){
			Integer id = unidadeOrganizacionalOrigem.getId();
			idUnidadeOrganizacionalOrigemStr = Integer.toString(id);

			descricaoUnidadeOrganizacionalOrigem = unidadeOrganizacionalOrigem.getDescricao();
		}

		this.setIdUnidadeOrganizacionalOrigem(idUnidadeOrganizacionalOrigemStr);
		this.setDescricaoUnidadeOrganizacionalOrigem(descricaoUnidadeOrganizacionalOrigem);

		// Unidade Organizacional - Destino
		UnidadeOrganizacional unidadeOrganizacionalDestino = servicoTipoTramite.getUnidadeOrganizacionalDestino();

		String idUnidadeOrganizacionalDestinoStr = "";
		String descricaoUnidadeOrganizacionalDestino = "";

		if(unidadeOrganizacionalDestino != null){
			Integer id = unidadeOrganizacionalDestino.getId();
			idUnidadeOrganizacionalDestinoStr = Integer.toString(id);

			descricaoUnidadeOrganizacionalDestino = unidadeOrganizacionalDestino.getDescricao();
		}

		this.setIdUnidadeOrganizacionalDestino(idUnidadeOrganizacionalDestinoStr);
		this.setDescricaoUnidadeOrganizacionalDestino(descricaoUnidadeOrganizacionalDestino);
	}

}
