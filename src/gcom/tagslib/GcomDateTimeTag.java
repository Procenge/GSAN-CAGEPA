
package gcom.tagslib;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * Tag com comportamento padrão de data e hora.
 * 
 * @author João Santana
 */
public class GcomDateTimeTag
				extends GcomBaseTag {

	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_DATE_MAX_LENGTH = 10;

	private static final int DEFAULT_HOUR_MAX_LENGTH = 5;

	private static final int DEFAULT_MONTH_YEAR_MAX_LENGTH = 7;

	private static final int DATE_SIZE = 11;

	private static final int HOUR_SIZE = 7;

	private static final int MONTH_YEAR_SIZE = 8;

	private String form = null;

	private String label = null;

	private boolean datePopup = true;

	private boolean monthYear = false;

	private boolean hour = false;

	public int doStartTag() throws JspException{

		StringBuilder sbDatePopup = new StringBuilder();

		sbDatePopup.append(prepareDateField());

		if(isMonthYear() || isHour()){
			setDatePopup(false);
		}
		if(isDatePopup()){
			sbDatePopup.append(prepareDateButton());
		}
		if(isMonthYear()){
			sbDatePopup.append("(mm/aaaa)");
		}else if(isHour()){
			sbDatePopup.append("(hh:mm:ss)");
		}else{
			sbDatePopup.append("(dd/mm/aaaa)");
		}
		sbDatePopup.append(prepareScripts());

		ResponseUtils.write(pageContext, sbDatePopup.toString());

		return (EVAL_PAGE);

	}

	private String prepareDateField() throws JspException{

		StringBuilder dateField = new StringBuilder("<input type=\"text\"");
		dateField.append(" name=\"");
		dateField.append(property);
		dateField.append("\"");
		dateField.append(" id=\"");
		dateField.append(property);
		dateField.append("\"");
		if(accesskey != null){
			dateField.append(" accesskey=\"");
			dateField.append(accesskey);
			dateField.append("\"");
		}
		dateField.append(" maxlength=\"");
		if(isMonthYear()){
			dateField.append(DEFAULT_MONTH_YEAR_MAX_LENGTH);
		}else if(isHour()){
			dateField.append(DEFAULT_HOUR_MAX_LENGTH);
		}else{
			dateField.append(DEFAULT_DATE_MAX_LENGTH);
		}
		dateField.append("\"");
		dateField.append(" size=\"");
		if(isMonthYear()){
			dateField.append(MONTH_YEAR_SIZE);
		}else if(isHour()){
			dateField.append(HOUR_SIZE);
		}else{
			dateField.append(DATE_SIZE);
		}
		dateField.append("\"");
		if(tabindex != null){
			dateField.append(" tabindex=\"");
			dateField.append(tabindex);
			dateField.append("\"");
		}
		dateField.append(" value=\"");
		if(value != null){
			dateField.append(ResponseUtils.filter(value));
		}else if(redisplay){
			Object value = RequestUtils.lookup(pageContext, name, property, null);
			if(value == null) value = "";
			dateField.append(ResponseUtils.filter(value.toString()));
		}
		dateField.append("\"");
		dateField.append(prepareEventHandlers());
		dateField.append(prepareStyles());
		dateField.append(getElementClose());
		dateField.append("&nbsp;");

		return (dateField.toString());
	}

	private String prepareScripts() throws JspException{

		MessageResources bundle = MessageResources.getMessageResources("application");
		String caminhoJs = bundle.getMessage("caminho.js");
		String mensagemErro = bundle.getMessage("atencao.campo.invalido", (label == null ? property : label));

		StringBuilder scripts = new StringBuilder("<script language=\"JavaScript\" src=\"");
		scripts.append(caminhoJs).append("date-util.js\"></script>");

		scripts.append("<script language=\"JavaScript\">function validate");
		scripts.append(Character.toUpperCase(property.charAt(0)));
		if(property.length() > 1) scripts.append(property.substring(1));

		if(form != null && !form.trim().equals("")){
			scripts.append("() { return dateCheck(document.").append(form).append(".");
		}else{
			scripts.append("() { return dateCheck(document.forms[0].");
		}
		if(isMonthYear()){
			scripts.append(property).append(", '%mm/%yyyy', '");
		}else if(isHour()){
			scripts.append(property).append(", '%hh:%mins:%ss', '");
		}else{
			scripts.append(property).append(", '%dd/%mm/%yyyy', '");
		}
		scripts.append(mensagemErro).append("') }</script>");

		scripts.append("<script language=\"JavaScript\"> ");
		scripts.append(" j(document).ready(function() { ");

		if(isMonthYear()){
			scripts.append(" j(\"#" + property + "\").mask(\"99/9999\"); ");
		}else if(isHour()){
			scripts.append(" j(\"#" + property + "\").mask(\"99:99:99\"); ");
		}else{
			scripts.append(" j(\"#" + property + "\").mask(\"99/99/9999\"); ");
		}
		scripts.append(" }); ");
		scripts.append("</script> ");

		return (scripts.toString());
	}

	private String prepareDateButton() throws JspException{

		MessageResources bundle = MessageResources.getMessageResources("application");
		String caminhoJs = bundle.getMessage("caminho.js");

		StringBuilder findButton = new StringBuilder("<script language=\"JavaScript\" src=\"");
		findButton.append(caminhoJs).append("Calendario.js\"></script>");

		findButton.append("<img src=\"").append(getContextRoot()).append("/imagens/calendario.gif\"");
		findButton.append(" style=\"cursor: pointer\"");

		if(form != null && !form.trim().equals("")){
			findButton.append(" onclick=\"abrirCalendario('").append(form).append("', '");
			findButton.append(property).append("');\"");
		}else{
			findButton.append(" onclick=\"abrirCalendario(document.forms[0].name, '");
			findButton.append(property).append("');\"");
		}
		if(tabindex != null){
			findButton.append(" tabindex=\"");
			findButton.append((Integer.valueOf(tabindex) + 1));
			findButton.append("\"");
		}
		findButton.append(getElementClose());
		findButton.append("&nbsp;");

		return (findButton.toString());
	}

	public void release(){

		super.release();
		form = null;
		label = null;
		datePopup = true;
		monthYear = false;
		hour = false;
	}

	public String getForm(){

		return form;
	}

	public void setForm(String form){

		this.form = form;
	}

	public void setDatePopup(boolean datePopup){

		this.datePopup = datePopup;
	}

	public boolean isDatePopup(){

		return datePopup;
	}

	public void setMonthYear(boolean monthYear){

		this.monthYear = monthYear;
	}

	public boolean isMonthYear(){

		return monthYear;
	}

	public void setHour(boolean hour){

		this.hour = hour;
	}

	public boolean isHour(){

		return hour;
	}

	public void setLabel(String label){

		this.label = label;
	}

	public String getLabel(){

		return label;
	}

}
