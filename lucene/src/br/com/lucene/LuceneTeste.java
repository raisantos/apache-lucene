package br.com.lucene;

import java.io.IOException;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class LuceneTeste {
	
   String indexDir = "home/raisoledade/lucene-data-teste/index";
   String dataDir = "home/raisoledade/lucene-data-teste/data";
   Indexer indexer;
   Searcher searcher;

   public static void main(String[] args) {
      LuceneTeste tester;
      try {
    	  tester = new LuceneTeste();
    	  tester.createIndex();
    	  tester.search("context");
      } catch (IOException e) {
         e.printStackTrace();
      } catch (ParseException e) {
        e.printStackTrace();
      }
   }

   private void createIndex() throws IOException {
      indexer = new Indexer(indexDir);
      System.out.println("Retornou da criação do indexer... ");
     
      int numIndexed;
      long startTime = System.currentTimeMillis();
      System.out.println("Tempo inicial == " + startTime);
      
      numIndexed = indexer.createIndex(dataDir, new TextFileFilter());
      System.out.println("Número indexado == " + numIndexed);
      
      long endTime = System.currentTimeMillis();
      System.out.println("Tempo final == " + endTime);
      
      indexer.close();
      System.out.println(numIndexed+" File indexed, time taken: "
         +(endTime-startTime)+" ms");		
   }

   private void search(String searchQuery) throws IOException, ParseException {
      
	   System.out.println("\nInciando busca...");
	   
	  searcher = new Searcher(indexDir);
      long startTime = System.currentTimeMillis();
      TopDocs hits = searcher.search(searchQuery);
      long endTime = System.currentTimeMillis();
   
      System.out.println(hits.totalHits +
         " documents found. Time :" + (endTime - startTime) + "ms");
      for(ScoreDoc scoreDoc : hits.scoreDocs) {
         Document doc = searcher.getDocument(scoreDoc);
            System.out.println("File: "
            + doc.get(LuceneConstants.FILE_PATH));
      }
      searcher.close();
   }
}