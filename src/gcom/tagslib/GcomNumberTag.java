
package gcom.tagslib;

import javax.servlet.jsp.JspException;

import org.apache.struts.util.RequestUtils;
import org.apache.struts.util.ResponseUtils;

/**
 * Tag com comportamento padrão de número.
 * 
 * @author João Santana
 */
public class GcomNumberTag
				extends GcomBaseTag {

	private static final long serialVersionUID = 1L;

	private int digits = 3;

	private int size = 20;

	private boolean useMask = true;

	private int decimalDigits = 2;

	private String centsSeparator = ",";

	private String thousandsSeparator = ".";

	private String prefix = "";

	public int doStartTag() throws JspException{

		StringBuilder numberTag = new StringBuilder();

		numberTag.append(prepareField());

		numberTag.append(prepareScripts());

		ResponseUtils.write(pageContext, numberTag.toString());

		return (EVAL_PAGE);
	}

	private String prepareField() throws JspException{

		StringBuilder fieldText = new StringBuilder("<input type=\"text\"");
		fieldText.append(" name=\"");
		fieldText.append(property);
		fieldText.append("\"");
		fieldText.append(" id=\"");
		fieldText.append(property);
		fieldText.append("\"");
		if(accesskey != null){
			fieldText.append(" accesskey=\"");
			fieldText.append(accesskey);
			fieldText.append("\"");
		}
		int maxlength = 0;
		fieldText.append(" maxlength=\"");
		int intDigits = getDigits();
		if(isUseMask()){
			int decDigits = getDecimalDigits();
			if(decDigits > 0) maxlength += decDigits + centsSeparator.length();
			intDigits -= decDigits;
			if(intDigits % 3 == 0){
				intDigits += (((int) intDigits / 3) - 1) * thousandsSeparator.length();
			}else{
				intDigits += ((int) intDigits / 3) * thousandsSeparator.length();
			}
		}
		maxlength += intDigits;
		fieldText.append(maxlength);
		fieldText.append("\"");
		fieldText.append(" size=\"");
		fieldText.append(getSize());
		fieldText.append("\"");
		if(tabindex != null){
			fieldText.append(" tabindex=\"");
			fieldText.append(tabindex);
			fieldText.append("\"");
		}
		fieldText.append(" value=\"");
		if(value != null){
			fieldText.append(ResponseUtils.filter(value));
		}else if(redisplay){
			Object value = RequestUtils.lookup(pageContext, name, property, null);
			if(value == null) value = "";
			fieldText.append(ResponseUtils.filter(value.toString()));
		}
		fieldText.append("\"");
		fieldText.append(prepareEventHandlers());
		fieldText.append(prepareStyles());
		fieldText.append(getElementClose());
		fieldText.append("&nbsp;");

		return (fieldText.toString());
	}

	private String prepareScripts() throws JspException{

		StringBuilder scripts = new StringBuilder();

		scripts.append("<script language=\"JavaScript\"> ");
		scripts.append(" j(document).ready(function() { ");
		scripts.append(" j(\"#").append(property).append("\").priceFormat({");

		if(isUseMask()){
			scripts.append(" prefix: '").append(prefix).append("', ");
			scripts.append(" centsSeparator: '").append(centsSeparator).append("', ");
			scripts.append(" thousandsSeparator: '").append(thousandsSeparator).append("', ");
		}else{
			scripts.append(" prefix: '', ");
			scripts.append(" centsSeparator: '', ");
			scripts.append(" thousandsSeparator: '', ");
		}
		scripts.append(" centsLimit: ").append(getDecimalDigits()).append(", ");
		scripts.append(" limit: ").append(getDigits()).append(" ");
		scripts.append(" }); ");
		scripts.append(" }); ");
		scripts.append("</script> ");

		return (scripts.toString());
	}

	public void release(){

		super.release();
		prefix = "";
		centsSeparator = ",";
		thousandsSeparator = ".";
		useMask = true;
		size = 20;
		digits = 3;
		decimalDigits = 2;
	}

	public String getCentsSeparator(){

		return centsSeparator;
	}

	public void setCentsSeparator(String centsSeparator){

		this.centsSeparator = centsSeparator;
	}

	public String getThousandsSeparator(){

		return thousandsSeparator;
	}

	public void setThousandsSeparator(String thousandsSeparator){

		this.thousandsSeparator = thousandsSeparator;
	}

	public String getPrefix(){

		return prefix;
	}

	public void setPrefix(String prefix){

		this.prefix = prefix;
	}

	public void setDigits(int digits){

		this.digits = digits;
	}

	public int getDigits(){

		return digits;
	}

	public void setSize(int size){

		this.size = size;
	}

	public int getSize(){

		return size;
	}

	public void setUseMask(boolean useMask){

		this.useMask = useMask;
	}

	public boolean isUseMask(){

		return useMask;
	}

	public void setDecimalDigits(int decimalDigits){

		this.decimalDigits = decimalDigits;
	}

	public int getDecimalDigits(){

		return decimalDigits;
	}

}
