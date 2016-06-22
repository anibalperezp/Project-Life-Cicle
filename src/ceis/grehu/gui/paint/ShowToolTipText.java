package ceis.grehu.gui.paint;

public class ShowToolTipText{

	public static String getHTMLToolTip(String description) {
		if (description == null) {
			description = "No disponible";
		}
		String tooltip = "<html>" +
		"<body>" +
		"<span style='font-family:Verdana, Arial, Helvetica, sans-serif; font-weight: bold; color: #669999; font-size: 10px'>Informaci&oacute;n</span>" +
		"<hr size='1' align='left'>" +
		"<div align='justify'><span style='font-family: Verdana, Arial, Helvetica, sans-serif; font-size: 10px'>" + description + "</span>" + 
		"</div>" +
		"</body>" +
		"</html>";
		return tooltip;
	}
	
	public static String getFont(String description) {
		String tooltip = "<html>" +
		"<body>" +
		"<div align='left'><span style='font-family: Dialog; font-weight: bold; font-size: 10px'>" + description + "</span>" + 
		"</div>" +
		"</body>" +
		"</html>";
		return tooltip;
	}
}