package com.hopital;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Calcul simple de métriques de qualité à partir du code source.
 * La complexité cyclomatique d'une méthode vaut 1 + le nombre de points de décision
 * (if, for, while, case, catch, &&, ||).
 * L'indice de maintenabilité utilise la formule classique sans le volume de Halstead,
 * ramenée sur 100.
 */
final class QualityMetrics {

    private static final Pattern METHOD = Pattern.compile(
            "(public|protected|private)[\\w<>\\[\\],?\\s]*\\s+\\w+\\s*\\([^)]*\\)\\s*(throws[\\w.,\\s]+)?\\{");
    private static final Pattern DECISION = Pattern.compile("\\b(if|for|while|case|catch)\\b|&&|\\|\\|");

    private QualityMetrics() {
    }

    /** Supprime les commentaires et le contenu des chaînes pour ne pas fausser le comptage. */
    static String clean(String source) {
        String noStrings = source.replaceAll("\"(\\\\.|[^\"\\\\])*\"", "\"\"");
        String noBlock = Pattern.compile("/\\*.*?\\*/", Pattern.DOTALL).matcher(noStrings).replaceAll("");
        return noBlock.replaceAll("//[^\\n]*", "");
    }

    /** Renvoie le corps de chaque méthode et constructeur du fichier. */
    static List<String> methodBodies(Path file) throws IOException {
        String src = clean(Files.readString(file));
        List<String> bodies = new ArrayList<>();
        Matcher m = METHOD.matcher(src);
        while (m.find()) {
            int start = m.end() - 1;
            int depth = 0;
            int i = start;
            do {
                char c = src.charAt(i);
                if (c == '{') {
                    depth++;
                } else if (c == '}') {
                    depth--;
                }
                i++;
            } while (depth > 0 && i < src.length());
            bodies.add(src.substring(start, i));
        }
        return bodies;
    }

    static int cyclomaticComplexity(String body) {
        Matcher m = DECISION.matcher(body);
        int count = 1;
        while (m.find()) {
            count++;
        }
        return count;
    }

    static int maxCyclomaticComplexity(Path file) throws IOException {
        return methodBodies(file).stream().mapToInt(QualityMetrics::cyclomaticComplexity).max().orElse(1);
    }

    static double maintainabilityIndex(Path file) throws IOException {
        List<String> bodies = methodBodies(file);
        double avgCc = bodies.stream().mapToInt(QualityMetrics::cyclomaticComplexity).average().orElse(1);
        double avgLoc = bodies.stream()
                .mapToLong(b -> b.lines().filter(l -> !l.isBlank()).count())
                .average().orElse(1);
        double mi = (171 - 0.23 * avgCc - 16.2 * Math.log(avgLoc)) * 100 / 171;
        return Math.max(0, Math.min(100, mi));
    }
}
