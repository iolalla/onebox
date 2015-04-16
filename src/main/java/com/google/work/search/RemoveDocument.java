
package com.google.work.search;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.StatusCode;

@SuppressWarnings("serial")
public class RemoveDocument extends HttpServlet {
	
	public static final String indexName = "OneBoxIndex";
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String docid = req.getParameter("docid");
		resp.setContentType("text/xml");
		resp.getWriter().println(this.remove(docid));
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/html");
	    resp.getWriter().println("Unsupported Operation");
	}
	
	private String remove(String docId) {
		//
	    IndexSpec indexSpec = IndexSpec.newBuilder().setName(indexName).build(); 
	    com.google.appengine.api.search.Index index = SearchServiceFactory.getSearchService().getIndex(indexSpec);
	    try {
	        index.delete(docId);
	    } catch (PutException e) {
	        if (StatusCode.TRANSIENT_ERROR.equals(e.getOperationResult().getCode())) {
	           return e.toString();
	        }
	    }
	    return "200 OK Document Removed";
	}
}
