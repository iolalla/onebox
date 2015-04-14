package com.google.work.search;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Field;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

@SuppressWarnings("serial")
public class DocumentController extends HttpServlet {
	
	public static final String indexName = "OneBoxIndex";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String query = req.getParameter("query");
		resp.setContentType("text/xml");
		resp.getWriter().println(search(query));
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//resp.setContentType("text/plain");
		//resp.getWriter().println("Hello, world");
		String url = req.getParameter("url");
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String tags = req.getParameter("tags");
		Document doc = this.insertDocument(url, title, content, tags);
		Logger.getGlobal().fine("Doc Id " + doc.getId());
		//Log.debug("Doc Id " + doc.getId());
		resp.setContentType("text/xml");
	    resp.getWriter().println(search(url));
	}
	
	public String search(String query) {
		IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
	    Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
	    String xmlResults = "";
		try {
		    //String queryString = "product: piano AND price &lt; 5000";
		    Results<ScoredDocument> results = index.search(query);
		    Logger.getGlobal().fine("Query " + query);

		    xmlResults = this.buildResponse(results);
		} catch (SearchException e) {
		    if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
		        // retry
		    }
		}
		return xmlResults;
	}
	
	public Document insertDocument(String url, String title,String content, String tags) {
		//
	    IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
	    com.google.appengine.api.search.Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
	    
	    Document document = Document.newBuilder()
	    			.addField(Field.newBuilder().setName("url").setText(url))
	    			.addField(Field.newBuilder().setName("title").setText(title))
	    			.addField(Field.newBuilder().setName("content").setText(content))
	    			.addField(Field.newBuilder().setName("tags").setText(tags)).build();
	    
	    try {
	        index.put(document);
	    } catch (PutException e) {
	        if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
	           // Logger.getGlobal().log(level, msg);
	        }
	    }
	    return document;
	}
	
	//TODO: Prepare this with JAXB
	/**
	 * <?xml version="1.0" encoding="UTF-8"?>
	<OneBoxResults xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:noNamespaceSchemaLocation="oneboxresults.xsd">
	  <resultCode>success</resultCode>
	  <provider>ACME Employee Directory</provider>
	  <title>
	    <urlText>13 results in the ACME directory</urlText>
	    <urlLink>http://directory.corp.acme.com/cgi-bin/search?bill%20smith</urlLink>
	  </title>
	  <IMAGE_SOURCE>http://directory.corp.acme.com//images/onebox.jpg</IMAGE_SOURCE>
	  <MODULE_RESULT>
	    <U>http://directory.corp.acme.com/cqi-bin/lookup?empid=448473</U>
	    <Title></Title>
	    <Field name="display">Smith, William</Field>
	    <Field name="firstname">William</Field>
	    <Field name="lastname">Smith</Field>
	    <Field name="phone">617-555-1234</Field>
	    <Field name="email">wsmith@acmecorp.com</Field>
	    <Field name="picture">http://directory.corp.acme.com/cqi-bin/lookup?photo=448473</Field>
	  </MODULE_RESULT>
	  <MODULE_RESULT>
	    <U>http://directory.corp.acme.com/cqi-bin/lookup?empid=22638</U>
	    <Title></Title>
	    <Field name="display">Smith, Bill R.</Field>
	    <Field name="firstname">Bill</Field>
	    <Field name="lastname">Smith</Field>
	    <Field name="phone">617-555-9345</Field>
	    <Field name="email">bsmith@acmecorp.com</Field>
	    <Field name="picture">http://directory.corp.acme.com/cqi-bin/lookup?photo=22638</Field>
	  </MODULE_RESULT>
	</OneBoxResults>
	 * @param results
	 * @return
	 */
	public String buildResponse( Results<ScoredDocument> results ) {
		StringBuffer buf = new StringBuffer();
		buf.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		buf.append("<OneBoxResults xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"")
			.append(" xsi:noNamespaceSchemaLocation=\"oneboxresults.xsd\">");
		buf.append("<resultCode>").append("success").append("</resultCode>");
			buf.append("<provider>")
				.append("One Box module indexing from the GSA")
				.append("</provider>");
			buf.append("<title>");
			buf.append("<urlText>")
				.append("The URL of the One Box module indexing from the GSA")
				.append("</urlText>");
			buf.append("<urlLink>").append("https://www.google.com/work/search").append("</urlLink>");
			buf.append("</title>");
			buf.append("<IMAGE_SOURCE>").append("http://vectorlogo.biz/wp-content/uploads/2013/01/GOOGLE-VECTORLOGO-BIZ-128x128.png").append("</IMAGE_SOURCE>");
			for (ScoredDocument document : results) {
			buf.append("<MODULE_RESULT>");
				buf.append("<U>").append(document.getOnlyField("url").getText()).append("</U>");
				buf.append("<Title>").append(document.getOnlyField("title").getText()).append("</Title>");
				buf.append("<Field");
					buf.append(" name=\"tags\"");
				buf.append(">");
				buf.append(document.getOnlyField("tags").getText());
				buf.append("</Field>");
			buf.append("</MODULE_RESULT>");
			}
		buf.append("</OneBoxResults>");
		return buf.toString();
	}
}
