package sefaz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;
import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class RecuperaXml {

	    public static void lerarq(String stringComEstruturaDoXML) throws UnsupportedEncodingException {  
	        InputStream f = new ByteArrayInputStream(stringComEstruturaDoXML.getBytes("utf-8"));  

	//Criamos uma classe SAXBuilder que vai processar o XML  
	        SAXBuilder sb = new SAXBuilder();  
	  
	//Este documento agora possui toda a estrutura do arquivo.  
	        Document d;  
	        try {  
	            d = sb.build(f);  
	//Recuperamos o elemento root  
	            Element nfe = d.getRootElement();  
	  
	//Recuperamos os atributos filhos (Attributes)  
	            List atributes = nfe.getAttributes();  
	            Iterator i_atr = atributes.iterator();  
	  
	//Iteramos com os atributos filhos  
	            while (i_atr.hasNext()) {  
	                Attribute atrib = (Attribute) i_atr.next();  
	                System.out.println("\nattribute de ("+nfe.getName()+"):"+ atrib.getName()+" - valor: "+atrib.getValue());  
	            }  
	//Recuperamos os elementos filhos (children)  
	            List elements = nfe.getChildren();  
	            Iterator i = elements.iterator();  
	  
	//Iteramos com os elementos filhos, e filhos do dos filhos  
	            while (i.hasNext()) {  
	                Element element = (Element) i.next();  
	                //System.out.println("element:"+ element.getName());  
	                	trataElement(element);
	            }  
	  
	        } catch (JDOMException ex) {  
	            Logger.getLogger(SefazXml.class.getName()).log(Level.SEVERE, null, ex);  
	        } catch (IOException ex) {  
	            Logger.getLogger(SefazXml.class.getName()).log(Level.SEVERE, null, ex);  
	        }  
	    }  
	  
	    public static void trataElement(Element element) {  
	            List atributes = element.getAttributes();   	//Recuperamos os atributos filhos (Attributes)
	            Iterator i_atr = atributes.iterator();         	//Iteramos com os atributos filhos 
	            while (i_atr.hasNext()) {  
	                Attribute atrib = (Attribute) i_atr.next();  
	                //  aqui voce pode escolher qual(is) campo(s) quer manipular                 
	                if (atrib.getName().contentEquals("schema")) {
	                	System.out.println("Schema: "+atrib.getValue()); //aqui fica o Schema
	                }
	                
	                if (atrib.getName().contentEquals("NSU")) {
	                	System.out.println("NSU: "+atrib.getValue());  //aqui fica o NSU
	                }
	            }  
	//Recuperamos os elementos filhos (children)  
	        List elements = element.getChildren();  
	        Iterator it = elements.iterator();  
	  
	        //Iteramos com os elementos filhos, e filhos do dos filhos  
	        while (it.hasNext()) {  
	            Element el = (Element) it.next();    
	            if (el.getName().equals("ultNSU")) {  
	            	 System.out.println("ultNSU: "+ el.getText());  //aqui fica o ultNSU
	            }
	     
	            if (el.getName().equals("maxNSU")) {  
	            	 System.out.println("maxNSU: "+ el.getText());  //aqui fica o maxNSU
	            }

	            if (el.getName().equals("docZip")) {  
	            	try {  
	                    String gzipBase64 = el.getText();  
	                    byte[] decoded = Base64.decodeBase64(gzipBase64.getBytes());  
	                    String texto = GzipUtils.decompress(decoded);  
	                    System.out.println("XML: ".concat(texto)); //aqui fica o xml
	                } catch (Exception e) {  
	                	System.out.printf(TrataXML.class.getSimpleName().concat(" :"), e);  	
	            	}
	            }  
	            trataElement(el);

	        }
	    }

}
	