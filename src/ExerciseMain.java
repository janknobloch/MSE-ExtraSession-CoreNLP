import java.util.List;
import java.util.Properties;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
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

public class ExerciseMain {

	public static final String TEXT = "When the first digital computers appeared in the early 1940s, the instructions to make"
			+ " them operate were wired into the machine. Practitioners quickly realized that this design"
			+ " was not flexible and came up with the \"stored program architecture\" or von Neumann architecture. "
			+ "Thus the division between \"hardware\" and \"software\" began with abstraction being used to deal with "
			+ "the complexity of computing Programming languages started to appear in the early 1950s and this was also "
			+ "another major step in abstraction. Major languages such as Fortran, ALGOL, and COBOL were released in the late 1950s "
			+ "to deal with scientific, algorithmic, and business problems respectively. David Parnas introduced the key concept of modularity"
			+ " and information hiding in 1972 to help programmers deal with the ever-increasing complexity of software systems.";

	public static void main(String[] args) {

		/*
		 * see https://stanfordnlp.github.io/CoreNLP/pipelines.html The core document
		 * contains all annotations (Annotation Object)
		 *
		 * We can use CORENLP in two flavors 1. using a pipeline 2. applying the
		 * individual processing steps manually (encouraged for learning the steps)
		 * during this class
		 */

		CoreDocument document = new CoreDocument(TEXT);
		
		/**
		 * Theory: https://nlp.stanford.edu/IR-book/html/htmledition/tokenization-1.html
		 */

		// TODO 1: Use the TokenizerAnnotator class

		// TODO 1.1: Call the Constructor to initialize the TokenizerAnnotator
		// TODO 1.2: Call the tokenizer.annotate(Annotation) function
		// TODO 1.3: Retrieve all Labels(CoreLabel) from the document annotation,
		// Hint: you can use the CoreAnnotations.TokensAnnotation.class to retrieve all
		// Tokens
		// TODO 2.4: Print all tokens

		// TODO 2: Use the WordsToSentencesAnnotator class
		// TODO 2.1: Call the Constructor to initialize the WordsToSenstencesAnnotator
		// TODO 2.2: Call the sentenceannotator.annotate(Annotation) function
		// TODO 2.3: Retrieve all Sentences(CoreMap) from the document annotation
		// Hint: you can use the CoreAnnotations.SentecesAnnotation.class
		// TODO 2.4: Print all sentences

		/**
		 * Theory: https://en.wikipedia.org/wiki/Part-of-speech_tagging
		 */

		// TODO 3: Use the POSTaggerAnnotator class
		// TODO 3.1: Call the Constructor to initialize the POSTaggerAnnotator
		// TODO 3.2: Call the posannotator.annotate(Annotation) function
		// TODO 3.3: Retrieve all POS Tags(CoreMap) from the document annotation
		// Hint: you can use the CoreAnnotations.SentencesAnnotation.class
		// TODO 2.4: Print all POS Tags
		// Hint: Iterate over the CoreMap by using .get(TokensAnnotation.class) for
		// Tokens
		// Using .get(TextAnnotation.class) to retrieve words and
		// .get(PartOfSpeechAnnotation.class) for POS Tags
		// TODO 2.5: Figure out what all abbreviations for POS Tags means for CoreNLP
		// Hint: Penn Treebank + pos google

		/**
		 * Theory: https://en.wikipedia.org/wiki/Named-entity_recognition
		 */

		// TODO 4: Use the NERCombinerAnnotator class
		// TODO 4.1: Call the Constructor to initialize the NERCombinerAnnotator
		// TODO 4.2: Call the nerannotator.annotate(Annotation) function
		// TODO 4.3: Retrieve all NER Tags(CoreMap) from the document annotation
		// Hint: you can use the CoreAnnotations.SentencesAnnotation.class
		// TODO 4.4: Print all NER Tags
		// Hint: Iterate over the CoreMap by using .get(TokensAnnotation.class) for
		// Tokens
		// Using .get(NamedEntityTagAnnotation.class) to retrieve NER Tags

		/**
		 * Theory: https://en.wikipedia.org/wiki/Sentiment_analysis
		 */

		// TODO 5: Use the ParserAnnotator and SentimentAnnotator class
		// TODO 5.1: Call the Constructor to initialize the ParserAnnotator providing an
		// empty string and the properties defined
		// TODO 5.2: Call the Constructor to initialize the SentimentAnnotator providing
		// an
		// empty string and the properties defined

		Properties props = new Properties();
		props.setProperty("annotators", "sentiment");

		// TODO 5.3: Call the parserannotator.annotate(Annotation) function
		// TODO 5.4: Call the sentimentannotator.annotate(Annotation) function
		// TODO 5.5: Retrieve all Sentiment Tags(CoreMap) from the document annotation
		// Hint: Iterate over the CoreMap by using .get(SentencesAnnotation.class) for
		// Sentences

		/*
		 * Inside your CoreMap for-each loop access the sentiment Tree using:
		 * sentimentTree =
		 * coreMapEntry.get(SentimentCoreAnnotations.SentimentAnnotatedTree.class);
		 * Compute the sentiment results for each sentence int result =
		 * RNNCoreAnnotations.getPredictedClass(sentimentTree); print out the result and
		 * the sentence System.out.println(result + " " + coreMapEntry);
		 * 
		 * scale of 0 = very negative, 1 = negative, 2 = neutral, 3 = positive, and 4 =
		 * very positive.
		 */

	}
}
