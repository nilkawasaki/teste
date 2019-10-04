package sefaz;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class XmlBuilder {
	
	private XmlRetorno instancia;
	
	public XmlBuilder() {
		this.instancia = new XmlRetorno();
	}
	
	public XmlBuilder setXmlRetornoUltNSU(String ultNSU) {
		instancia.setUltNSU(ultNSU);
		return this;
	}
	
	public XmlBuilder setXmlRetornoMaxNSU(String maxNSU) {
		instancia.setMaxNSU(maxNSU);
		return this;
	}
	
	public XmlBuilder setXmlEstrutura(String schema, String nsu, String xml) {
		XmlEstrutura xmlEstrutura = new XmlEstrutura();
		xmlEstrutura.setSchema(schema);
		xmlEstrutura.setNsu(nsu);
		xmlEstrutura.setXml(xml);
		
		if(instancia.getXmlEstrutura() == null) {
			instancia.setXmlEstrutura(new ArrayList());
		}
		
		instancia.getXmlEstrutura().add(xmlEstrutura);
		return this;
		
	}
	
	public XmlBuilder builder() {
		return instancia;
	}

}
