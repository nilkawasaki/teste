package sefaz;

import java.security.Security;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;



public class SefazXml {


	public static void main(String[] args) {
	
		String cnf = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n" + 
				"<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\r\n" + 
				"  <soap12:Body>\r\n" + 
				"    <nfeDistDFeInteresse xmlns=\"http://www.portalfiscal.inf.br/nfe/wsdl/NFeDistribuicaoDFe\">\r\n" + 
				"      <nfeDadosMsg><distDFeInt xmlns=\"http://www.portalfiscal.inf.br/nfe\" versao=\"1.01\"><tpAmb>1</tpAmb><cUFAutor>35</cUFAutor><CNPJ>08618022000202</CNPJ><distNSU><ultNSU>000000000000000</ultNSU></distNSU></distDFeInt></nfeDadosMsg>\r\n" + 
				"    </nfeDistDFeInteresse>\r\n" + 
				"  </soap12:Body>\r\n" + 
				"</soap12:Envelope>";

		/** 
         * 1) codigoDoEstado = Código do Estado conforme tabela IBGE. 
         * 
         * 2) url = Endereço do WebService para cada Estado. 
         *       Ver relação dos endereços em: 
         *       Para Homologação: http://hom.nfe.fazenda.gov.br/PORTAL/WebServices.aspx 
         *       Para Produção: http://www.nfe.fazenda.gov.br/portal/WebServices.aspx 
         * 
         * 3) caminhoDoCertificadoDoCliente = Caminho do Certificado do Cliente (A1). 
         * 
         * 4) senhaDoCertificadoDoCliente = Senha do Certificado A1 do Cliente. 
         * 
         * 5) arquivoCacertsGeradoParaCadaEstado = Arquivo com os Certificados necessarios para 
         * acessar o WebService. Pode ser gerado com a Classe NFeBuildCacerts. 
         */  
        //String codigoDoEstado = "35";  
        
        
        String caminhoDoCertificadoDoCliente = "C:/NFE/medicamental.pfx";  
        String senhaDoCertificadoDoCliente = "master2012";  
        String arquivoCacertsGeradoParaCadaEstado = "C:/NFE/NFeCacerts/";  

        /** 
         * Informações do Certificado Digital. 
         */  
        System.setProperty("java.protocol.handler.pkgs", "com.sun.net.ssl.internal.www.protocol");  
        Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());  

        System.setProperty("javax.net.ssl.keyStoreType", "PKCS12");  

        System.clearProperty("javax.net.ssl.keyStore");  
        System.clearProperty("javax.net.ssl.keyStorePassword");  
        System.clearProperty("javax.net.ssl.trustStore");  

        System.setProperty("javax.net.ssl.keyStore", caminhoDoCertificadoDoCliente);  
        System.setProperty("javax.net.ssl.keyStorePassword", senhaDoCertificadoDoCliente);  

        System.setProperty("javax.net.ssl.trustStoreType", "JKS");  
        System.setProperty("javax.net.ssl.trustStore", arquivoCacertsGeradoParaCadaEstado); 


			HttpClient httpClient = new HttpClient();
			//httpClient.getParams()
			//.setParameter("http.useragent", "Web Service Test Client");
			 
			BufferedReader br = null;
			String data = cnf;
					PostMethod methodPost = new PostMethod("https://www1.nfe.fazenda.gov.br/NFeDistribuicaoDFe/NFeDistribuicaoDFe.asmx");
							methodPost.setRequestBody(data);
							methodPost.setRequestHeader("Content-Type", "text/xml");
							try {
							int returnCode = httpClient.executeMethod(methodPost);
							 
							if (returnCode == HttpStatus.SC_NOT_IMPLEMENTED) {
							System.out
							.println("The Post method is not implemented by this URI");
							methodPost.getResponseBodyAsString();
							} else {
							br = new BufferedReader(new InputStreamReader(methodPost
							.getResponseBodyAsStream()));
							String readLine;
							while (((readLine = br.readLine()) != null)) {
								/** 
						         * Printa a linha do retorno xml que etá na readLine
						         */  
							TrataXML lexml = new TrataXML();  
						       lexml.lerarq(readLine); 
						       
							
							//System.out.println(readLine); 
								}
							  }
							} catch (Exception e) {
							e.printStackTrace();
							} finally {
							methodPost.releaseConnection();
							if (br != null)
							try {
							br.close();
							} catch (Exception fe) {
							fe.printStackTrace();
							    }
							}
		 }
}
