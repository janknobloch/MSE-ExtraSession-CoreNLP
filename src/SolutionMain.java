import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.NERCombinerAnnotator;
import edu.stanford.nlp.pipeline.POSTaggerAnnotator;
import edu.stanford.nlp.pipeline.ParserAnnotator;
import edu.stanford.nlp.pipeline.SentimentAnnotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.pipeline.WordsToSentencesAnnotator;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class SolutionMain {

	

	// we will store all sentiments(key) and their respective count(value) in this
	// map
	private static HashMap<String, Integer> sentimentCountMap = new HashMap<String, Integer>();

	public static void main(String[] args) throws ClassNotFoundException, IOException {

		
		// Retrieve the number of Trump Posts required
		List<TrumpPost> list = TrumpPost.getNumberOfTrumpPostsasList(10);


		/*
		 * We iterate over all Trump posts
		 */
		for (TrumpPost p : list) {

			/*
			 * see https://stanfordnlp.github.io/CoreNLP/pipelines.html The core document
			 * contains all annotations (Annotation Object)
			 *
			 * We can use CORENLP in two flavors 1. using a pipeline 2. applying the
			 * individual processing steps manually (encouraged for learning the steps)
			 * during this class
			 */

			CoreDocument document = new CoreDocument(p.text);

			// https://nlp.stanford.edu/IR-book/html/htmledition/tokenization-1.html
			createTokens(document, false);

			createSentenceSplit(document, false);

			// https://en.wikipedia.org/wiki/Part-of-speech_tagging
			// createPOSTags(document, true);

			// https://en.wikipedia.org/wiki/Named-entity_recognition
			// createNERTags(document, true);

			// https://en.wikipedia.org/wiki/Sentiment_analysis
			createSentiment(document, true);

		}

		printTrumpTweetSentiments();

	}

	private static void printTrumpTweetSentiments() {
		/*
		 * Lets print the overview of all sentiments give for the number of posts
		 * retrieved.
		 */
		System.out.println("");
		System.out.println("------------------------------------------");
		System.out.println("Donald Trumps tweets listed by sentiment:");
		System.out.println("------------------------------------------");
		for (Entry<String, Integer> entry : sentimentCountMap.entrySet()) {
			System.out.println("\t"+entry.getKey() + " \t\t" + entry.getValue());
			System.out.println("------------------------------------------");
		}
	}

	/**
	 * Creates Sentiments for sentences based on the following scale: 0 = very
	 * negative, 1 = negative, 2 = neutral, 3 = positive,
	 * 
	 * @param document
	 * @param print    - if you want to print out the results found
	 */
	private static void createSentiment(CoreDocument document, boolean print) {
		Properties props = new Properties();
		props.setProperty("annotators", "sentiment");
		ParserAnnotator parserAnnotator = new ParserAnnotator("", props);
		parserAnnotator.annotate(document.annotation());

		SentimentAnnotator sa = new SentimentAnnotator("", props);
		sa.annotate(document.annotation());

		for (CoreMap sentence : document.annotation().get(CoreAnnotations.SentencesAnnotation.class)) {
			Tree sentimentTree = sentence.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
			int result = RNNCoreAnnotations.getPredictedClass(sentimentTree);

			String resultString = createSentimentStringFromSentimentResultInteger(result);
			countSentiment(resultString);

			if (print) {
				System.out.println(resultString + " : " + sentence);
			}
		}
	}

	/**
	 * Adds the given Sentiment String (very negative(0), negative neutral(1), positive(2) and no sentiment(3)) to a HashMap and increases their
	 * counter if already existent
	 * @param sentimentString
	 */
	private static void countSentiment(String sentimentString) {

		if (!sentimentCountMap.containsKey(sentimentString)) {
			sentimentCountMap.put(sentimentString, 1);
		} else {
			int count = sentimentCountMap.get(sentimentString);
			sentimentCountMap.put(sentimentString, ++count);
		}

	}

	/**
	 * converts sentimentindex as integer to sentiment String
	 * @param sentiment result as integer
	 * @return sentiment result as string
	 */
	private static String createSentimentStringFromSentimentResultInteger(int result) {

		switch (result) {
		case 0:
			return "very negative";

		case 1:
			return "negative";

		case 2:
			return "neutral";

		case 3:
			return "positive";

		default:
			break;
		}
		return "no sentiment";
	}

	/**
	 * Computes the Named-Entity Tags based on a given input document
	 * @param document as the input document to add the NER Tags to
	 * @param print - if the NER Tags should be printed out
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private static void createNERTags(CoreDocument document, boolean print) throws IOException, ClassNotFoundException {
		NERCombinerAnnotator test = new NERCombinerAnnotator();
		test.annotate(document.annotation());
		List<CoreMap> sentences = document.annotation().get(CoreAnnotations.SentencesAnnotation.class);

		for (CoreMap sentence : sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);
				// this is the NER label of the token
				String ne = token.get(NamedEntityTagAnnotation.class);
				// only print words which contain a NER tag

				if (print) {
					if (!ne.equals("O")) {
						System.out.println(word + " :  " + pos + " " + ne);
					}
				}
			}
		}
	}

	/**
	 * Creates the POS Tags based on a document input see
	 * https://www.ling.upenn.edu/courses/Fall_2003/ling001/penn_treebank_pos.html
	 * for abbreviations
	 * 
	 * @param document
	 * @param print    - if you want to print out the results found
	 */
	private static void createPOSTags(CoreDocument document, boolean print) {
		POSTaggerAnnotator posannotator = new POSTaggerAnnotator();
		posannotator.annotate(document.annotation());

		List<CoreMap> sentences = document.annotation().get(CoreAnnotations.SentencesAnnotation.class);
		for (CoreMap sentence : sentences) {
			// traversing the words in the current sentence
			// a CoreLabel is a CoreMap with additional token-specific methods
			for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
				// this is the text of the token
				String word = token.get(TextAnnotation.class);
				// this is the POS tag of the token
				String pos = token.get(PartOfSpeechAnnotation.class);
				// this is the POS label of the token
				if (print) {
					System.out.println(word + " :  " + pos);
				}

			}
		}
	}

	/**
	 * Annotates the provided paragraphs into separate sentences
	 * @param document
	 * @param print
	 */
	private static void createSentenceSplit(CoreDocument document, boolean print) {
		WordsToSentencesAnnotator sentenceannotator = new WordsToSentencesAnnotator();
		sentenceannotator.annotate(document.annotation());
		List<CoreMap> sentences = document.annotation().get(CoreAnnotations.SentencesAnnotation.class);
		if (print) {
			for (CoreMap sentence : sentences) {

				System.out.println(sentence);
			}
		}
	}

	/**
	 * 
	 * @param document
	 * @param print    - if you want to print out the results found
	 */
	private static void createTokens(CoreDocument document, boolean print) {

		TokenizerAnnotator tokenizer = new TokenizerAnnotator();
		tokenizer.annotate(document.annotation());
		List<CoreLabel> tokenLabels = document.annotation().get(CoreAnnotations.TokensAnnotation.class);
		if (print) {
			for (CoreLabel token : tokenLabels) {
				System.out.println(token);
			}
		}

	}

}
