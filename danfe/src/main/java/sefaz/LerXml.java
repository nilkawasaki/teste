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


public class LerXml { 
	public static ArrayList<XmlEstruturaSimples> xmlESL = new ArrayList<XmlEstruturaSimples>();
	public static ArrayList<XmlEstruturaSimples> xmlESL2 = new ArrayList<XmlEstruturaSimples>();
	public static String valor = new String();
	public static String grupo = new String();
	public static Boolean flag = false;
	public static Boolean flagG = false;
	

	//----------------------Trata XML	
	    public static ArrayList<XmlEstruturaSimples> lerarq(String stringComEstruturaDoXML, String campo, String campo1) throws UnsupportedEncodingException {  
	//Aqui você informa o nome do arquivo XML.  
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
	                /*System.out.println("\nattribute de ("+nfe.getName()+"):"+ atrib.getName()+" - valor: "+atrib.getValue());  */
	            }  
	//Recuperamos os elementos filhos (children)  
	            List elements = nfe.getChildren();  
	            Iterator i = elements.iterator();  
	  
	//Iteramos com os elementos filhos, e filhos do dos filhos  
	            while (i.hasNext()) {  
	                Element element = (Element) i.next();  
	                //System.out.println("element:"+ element.getName());  
	               // Estrutura =	trataElement(element); 
	                xmlESL2 = trataElement(element, campo, campo1);
	            }  
	  
	        } catch (JDOMException ex) {  
	            Logger.getLogger(SefazXml.class.getName()).log(Level.SEVERE, null, ex);  
	        } catch (IOException ex) {  
	            Logger.getLogger(SefazXml.class.getName()).log(Level.SEVERE, null, ex);  
	        }  
	        return xmlESL2;
	    }  
	  
	    public static ArrayList<XmlEstruturaSimples> trataElement(Element element, String campo, String campo1) {
	    	XmlEstruturaSimples xmlest = new XmlEstruturaSimples();
	    	XmlEstruturaSimples xmlest2 = new XmlEstruturaSimples();
	//Recuperamos os atributos filhos (Attributes)  
	            List atributes = element.getAttributes();  
	            Iterator i_atr = atributes.iterator();

	//Iteramos com os atributos filhos  
	            while (i_atr.hasNext()) {
	                Attribute atrib = (Attribute) i_atr.next();  
	                //System.out.println("atributo de ("+element.getName()+"):"+ atrib.getName()+" - valor: "+atrib.getValue());
	                //  aqui voce pode escolher qual(is) campo(s) quer manipular
	                if (atrib.getName().contentEquals("schema")) {
	                	//System.out.println("Schema: "+atrib.getValue())
	                }
	                
	                if (atrib.getName().contentEquals("NSU")) {
	                	//System.out.println("NSU: "+atrib.getValue());
	                }
	                //xmlest.setGrupo(grupo);
	                //xmlest.setAtributo(atrib.getName());
	                //xmlest.setValor_atributo(atrib.getValue());
	                //System.out.println("*"+atrib.getName()+"="+atrib.getValue());
	                //System.out.println(xmlES2.getGrupo()+":"+xmlES2.getAtributo()+";"+xmlES2.getValor_atributo());
		            //xmlESL.getXmlES().add(xmlest);
	            } 
	            
	            
	//Recuperamos os elementos filhos (children)  
	        List elements = element.getChildren();  
	        Iterator it = elements.iterator();
	        //Iteramos com os elementos filhos, e filhos do dos filhos  
	        while (it.hasNext()) {  
	            Element el = (Element) it.next();  
	            //System.out.println("elemento("+element.getName()+"):"+ el.getName()+" - Valor: "+el.getText());  
	  
	            //  aqui voce pode escolher qual(is) campo(s) quer manipular   

	            if (el.getText().equals("")) {
	            	//xmlES2.setGrupo(el.getName());
	            	grupo = el.getName();
	            }
	            if (!el.getText().equals("")) {
	            	xmlest.setGrupo(grupo);
	            	xmlest.setCampo(el.getName());
	            	xmlest.setValor(el.getText());
	            	System.out.println(xmlest.getGrupo()+"-"+xmlest.getCampo()+"-"+xmlest.getValor());
	            	xmlESL.add(xmlest);
	            }
	           
	            //System.out.println(xmlESL.getXmlES());
	            
	           
	            if (el.getName().equals("xMotivo") && el.getText().equals("Nenhum documento localizado")) {
	            	xmlest.setAtributo(null);
	            	xmlest.setCampo(null);
	            	xmlest.setGrupo(null);
	            	xmlest.setValor(null);
	            	xmlest.setValor_atributo(null);
	            	xmlESL.removeAll(xmlESL);
	            }
	            
	            
	            trataElement(el, campo, campo1);   
	        }
	    return xmlESL;
	    }
}
