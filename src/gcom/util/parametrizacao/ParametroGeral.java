
package gcom.util.parametrizacao;

public class ParametroGeral
				extends Parametro {

	public static final Parametro PARAMETRO_CALCULO_DV = new ParametroGeral("PARAMETRO_CALCULO_DV");

	public static final Parametro P_USUARIO_BATCH = new ParametroGeral("P_USUARIO_BATCH");

	public static final Parametro P_FUNCIONARIO_ADMINISTRADOR_SISTEMA = new ParametroGeral("P_FUNCIONARIO_ADMINISTRADOR_SISTEMA");

	public static final Parametro P_PORTA_SERVIDOR_SMTP = new ParametroGeral("P_PORTA_SERVIDOR_SMTP");

	public static final Parametro P_USUARIO_CONEXAO_SMTP = new ParametroGeral("P_USUARIO_CONEXAO_SMTP");

	public static final Parametro P_SENHA_CONEXAO_SMTP = new ParametroGeral("P_SENHA_CONEXAO_SMTP");

	public static final Parametro P_PORTA_SERVIDOR_SMTP_SOCKET_FACTORY_INDICADOR = new ParametroGeral(
					"P_PORTA_SERVIDOR_SMTP_SOCKET_FACTORY_INDICADOR");

	public static final Parametro P_INSCRICAO_ESTADUAL_EMPRESA = new ParametroGeral("P_INSCRICAO_ESTADUAL_EMPRESA");

	public static final Parametro P_NOME_ESTADO_ABREVIADO_EMPRESA = new ParametroGeral("P_NOME_ESTADO_ABREVIADO_EMPRESA");

	public static final Parametro P_JUNTA_COMERCIAL_EMPRESA = new ParametroGeral("P_JUNTA_COMERCIAL_EMPRESA");

	public static final Parametro P_TIPO_BANCO_DE_DADOS = new ParametroGeral("P_TIPO_BANCO_DE_DADOS");

	public static final Parametro P_SENHA_QUANTIDADE_MINIMA_CARACTERES = new ParametroGeral("P_SENHA_QUANTIDADE_MINIMA_CARACTERES");

	public static final Parametro P_SENHA_QUANTIDADE_MINIMA_LETRAS_MINUSCULAS = new ParametroGeral(
					"P_SENHA_QUANTIDADE_MINIMA_LETRAS_MINUSCULAS");

	public static final Parametro P_SENHA_QUANTIDADE_MINIMA_LETRAS_MAIUSCULAS = new ParametroGeral(
					"P_SENHA_QUANTIDADE_MINIMA_LETRAS_MAIUSCULAS");

	public static final Parametro P_SENHA_QUANTIDADE_MINIMA_NUMEROS = new ParametroGeral("P_SENHA_QUANTIDADE_MINIMA_NUMEROS");

	public static final Parametro P_SENHA_QUANTIDADE_MINIMA_CARACTERES_ESPECIAIS = new ParametroGeral(
					"P_SENHA_QUANTIDADE_MINIMA_CARACTERES_ESPECIAIS");

	public static final Parametro P_SENHA_QUANTIDADE_HISTORICO = new ParametroGeral("P_SENHA_QUANTIDADE_HISTORICO");

	public static final Parametro P_NOME_EMPRESA_RELATORIO = new ParametroGeral("P_NOME_EMPRESA_RELATORIO");

	private ParametroGeral(String parametro) {

		super(parametro);
	}
}
