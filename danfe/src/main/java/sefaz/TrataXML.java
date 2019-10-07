package sefaz;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom.Attribute;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


//import Logger GZIPtest
import org.apache.commons.codec.binary.Base64;  
//----------------------------------------------


public class TrataXML { 
	public static XmlRetorno retornoXml = new XmlRetorno();
	public static XmlRetorno retornoXml2 = new XmlRetorno();

	//----------------------Trata XML	
	    public static XmlRetorno lerarq(String stringComEstruturaDoXML) throws UnsupportedEncodingException {  
	//Aqui voc� informa o nome do arquivo XML.  
	       // File f = new File("C:/NFE/nfd.xml");
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
	               // Estrutura =	trataElement(element); 
	                retornoXml2 = trataElement(element);
	            }  
	  
	        } catch (JDOMException ex) {  
	            Logger.getLogger(SefazXml.class.getName()).log(Level.SEVERE, null, ex);  
	        } catch (IOException ex) {  
	            Logger.getLogger(SefazXml.class.getName()).log(Level.SEVERE, null, ex);  
	        }  
	        return retornoXml2;
	    }  
	  
	    public static XmlRetorno trataElement(Element element) {
            	XmlEstrutura Estrutura = new XmlEstrutura();
            	XmlEstrutura EstruturaXml= new XmlEstrutura();
	//Recuperamos os atributos filhos (Attributes)  
	            List atributes = element.getAttributes();  
	            Iterator i_atr = atributes.iterator();

	//Iteramos com os atributos filhos  
	            while (i_atr.hasNext()) {
	                Attribute atrib = (Attribute) i_atr.next();  
	                //System.out.println("atributo de ("+element.getName()+"):"+ atrib.getName()+" - valor: "+atrib.getValue());
	                //  aqui voce pode escolher qual(is) campo(s) quer manipular
	                if (atrib.getName().contentEquals("schema")) {
	                	//System.out.println("Schema: "+atrib.getValue());
	                	Estrutura.setSchema(atrib.getValue());
	    	            if(retornoXml.getXmlEstrutura() == null) {
	    	    			retornoXml.setXmlEstrutura(new ArrayList<XmlEstrutura>());
	    	    		}
	    	            retornoXml.getXmlEstrutura().add(Estrutura);
	    	            System.out.println(Estrutura.getNsu());
	    	            System.out.println(Estrutura.getSchema());
	    	            System.out.println(Estrutura.getXml());

	                }
	                
	                if (atrib.getName().contentEquals("NSU")) {
	                	EstruturaXml = retornoXml2.getXmlEstruturaP();
	                	//System.out.println("NSU: "+atrib.getValue());
	                	//System.out.println(Estrutura.getXml());
	                	Estrutura.setXml(EstruturaXml.getXml());
	                	Estrutura.setNsu(atrib.getValue());
	                }
	            } 
	            
	            
	//Recuperamos os elementos filhos (children)  
	        List elements = element.getChildren();  
	        Iterator it = elements.iterator();
	  
	        //Iteramos com os elementos filhos, e filhos do dos filhos  
	        while (it.hasNext()) {  
	            Element el = (Element) it.next();  
	            //System.out.println("elemento("+element.getName()+"):"+ el.getName()+" - Valor: "+el.getText());  
	  
	            //  aqui voce pode escolher qual(is) campo(s) quer manipular   
	            if (el.getName().equals("ultNSU")) {  
	            	 //System.out.println("ultNSU_: "+ el.getText());
	            	 retornoXml.setUltNSU(el.getText());
	            }
	     
	            if (el.getName().equals("maxNSU")) {  
	            	//System.out.println("maxNSU_: "+ el.getText());
	            	retornoXml.setMaxNSU(el.getText());
	            }
	            
	            
	            
	            if (el.getName().equals("docZip")) {  
	            	try {  
	                    String gzipBase64 = el.getText();  
	          
	                    byte[] decoded = Base64.decodeBase64(gzipBase64.getBytes());  
	                    String texto = GzipUtils.decompress(decoded);  
	                    //System.out.println("XML: ".concat(texto));
	                    Estrutura.setXml(texto);
	    	            if(retornoXml2.getXmlEstrutura() == null) {
	    	    			retornoXml2.setXmlEstrutura(new ArrayList<XmlEstrutura>());
	    	    		}
	    	            retornoXml2.getXmlEstrutura().add(Estrutura);
	                    
	                } catch (Exception e) {  
	                	System.out.printf(TrataXML.class.getSimpleName().concat(" :"), e);  
	            	}
	            }          
	            trataElement(el);   
	        }
	    return retornoXml;
	    }
}
