package br.com.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopDocs;

public class LuceneTesteOrder {
	String indexDir = "home/raisoledade/lucene-data-teste/index";
	   String dataDir = "home/raisoledade/lucene-data-teste/data";
	   Indexer indexer;
	   SearcherOrder searcher;

	   public static void main(String[] args) {
	      LuceneTesteOrder tester;
	      try {
	          tester = new LuceneTesteOrder();
	          tester.sortUsingRelevance("skit.pdf");
	          tester.sortUsingIndex("cord3.txt");
	      } catch (IOException e) {
	          e.printStackTrace();
	      } catch (ParseException e) {
	          e.printStackTrace();
	      }		
	   }

	   private void sortUsingRelevance(String searchQuery)
	      throws IOException, ParseException {
	      searcher = new SearcherOrder(indexDir);
	      long startTime = System.currentTimeMillis();
	      
	      //create a term to search file name
	      Term term = new Term(LuceneConstants.FILE_NAME, searchQuery);
	      //create the term query object
	      Query query = new FuzzyQuery(term);
	      searcher.setDefaultFieldSortScoring(true, false);
	      //do the search
	      TopDocs hits = searcher.search(query,Sort.RELEVANCE);
	      long endTime = System.currentTimeMillis();

	      System.out.println(hits.totalHits +
	         " documents found. Time :" + (endTime - startTime) + "ms");
	      for(ScoreDoc scoreDoc : hits.scoreDocs) {
	         Document doc = searcher.getDocument(scoreDoc);
	         System.out.print("Score: "+ scoreDoc.score + " ");
	         System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
	      }
	      searcher.close();
	   }

	   private void sortUsingIndex(String searchQuery)
	      throws IOException, ParseException {
	      searcher = new SearcherOrder(indexDir);
	      long startTime = System.currentTimeMillis();
	      //create a term to search file name
	      Term term = new Term(LuceneConstants.FILE_NAME, searchQuery);
	      //create the term query object
	      Query query = new FuzzyQuery(term);
	      searcher.setDefaultFieldSortScoring(true, false);
	      //do the search
	      TopDocs hits = searcher.search(query,Sort.INDEXORDER);
	      long endTime = System.currentTimeMillis();

	      System.out.println(hits.totalHits +
	      " documents found. Time :" + (endTime - startTime) + "ms");
	      for(ScoreDoc scoreDoc : hits.scoreDocs) {
	         Document doc = searcher.getDocument(scoreDoc);
	         System.out.print("Score: "+ scoreDoc.score + " ");
	         System.out.println("File: "+ doc.get(LuceneConstants.FILE_PATH));
	      }
	      searcher.close();
	   }
}
