package frendit.xyz.com.service;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;

import edu.stanford.nlp.util.CoreMap;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ContentQualityService {
    private final StanfordCoreNLP pipeline;

    public ContentQualityService() {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
        this.pipeline = new StanfordCoreNLP(props);
    }

    public int rateContent(String text) {
        Annotation annotation = new Annotation(text);
        pipeline.annotate(annotation);

        int qualityScore = 0;
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            String sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            qualityScore += getSentimentScore(sentiment);
        }
        qualityScore /= sentences.size();

        // Map the quality score to a 1-100 scale
        int mappedScore = (qualityScore + 5) * 10;
        if (mappedScore > 100) {
            mappedScore = 100;
        } else if (mappedScore < 1) {
            mappedScore = 1;
        }
        return mappedScore;
    }

    public List<String> extractTopics(String text) {
        List<String> topics = new ArrayList<>();

        // annotate the text
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        // extract noun phrases from the text
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                String word = token.get(CoreAnnotations.TextAnnotation.class);

                // check if the token is a noun phrase
                if (pos.startsWith("NN")) {
                    topics.add(word);
                }
            }
        }

        // return the top 5 most frequent topics
        Map<String, Long> frequencyMap = topics.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        return frequencyMap.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(5)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }


    private int getSentimentScore(String sentiment) {
        return switch (sentiment) {
            case "Very negative" -> 0;
            case "Negative" -> 1;
            case "Neutral" -> 2;
            case "Positive" -> 3;
            case "Very positive" -> 4;
            default -> 2;
        };
    }
}
