# MSE-ExtraSession-CoreNLP

This repository elaborates on how to use the CORENLP libraries for Natural Language Processing (NLP).

Typically CORENLP uses a pipeline approach to define all needed processing steps and then apply this pipeline towards a document(CoreDocument) generating Annotations for its stored text.

For the purpose of elaborating the different processing steps, this project aims to highlight the different processing steps one after another.

The used steps are: tokenization, sentence splitting, creation of Part-of-Speech (POS) tags, the creation of Named Enitity tags applying Named-Entity-Recognition (NER) and the computation of sentence sentiments using a Recurrant Neuronal Network (RNN).

Please see the Solution Main java file for getting started
The TrumpPost Class helps by providing static methods to input a given JSON file and convert it to TrumpPost Java instance objects.

------------------------------------------
###Donald Trumps tweets listed by sentiment:
------------------------------------------
	very negative 		55
------------------------------------------
	negative 		3032
------------------------------------------
	neutral 		1781
------------------------------------------
	positive 		1214
------------------------------------------
	no sentiment 		105
------------------------------------------
