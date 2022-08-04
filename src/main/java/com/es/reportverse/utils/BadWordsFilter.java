package com.es.reportverse.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.es.reportverse.exception.ApiRequestException;

public class BadWordsFilter {
    private final static String BAD_WORD_FOUNDED = "O texto inserido foi bloqueado porque uma palavra imprópria foi encontrada nele.";

    static Map<String, String[]> words = new HashMap<>();
    
    static int largestWordLength = 0;
    
    public static void loadConfigs() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new URL("https://docs.google.com/spreadsheets/d/1Jf60Mp_B4ZEWqYC8E0ML4RsvIoSOOqO4CWG7LU7C2O8/export?format=csv").openConnection().getInputStream()));
            String line = "";
            int counter = 0;
            while((line = reader.readLine()) != null) {
                counter++;
                String[] content = null;
                try {
                    content = line.split(",");
                    String word = content[0];
                    String[] ignoreInCombinationWithWords = new String[]{};
                    if(content.length > 1) {
                        ignoreInCombinationWithWords = content[1].split("_");
                    }

                    if(word.length() > largestWordLength) {
                        largestWordLength = word.length();
                    }
                    words.put(word.replaceAll(" ", ""), ignoreInCombinationWithWords);

                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
            System.out.println("Loaded " + counter + " words to filter out");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
     
    public static List<String> badWordsFound(String input) {
        if(input == null) {
            return new ArrayList<>();
        }

        // ele troca leetspeak por sua letra correspondente
        
        input = input.replaceAll("1","i");
        input = input.replaceAll("!","i");
        input = input.replaceAll("3","e");
        input = input.replaceAll("4","a");
        input = input.replaceAll("@","a");
        input = input.replaceAll("5","s");
        input = input.replaceAll("7","t");
        input = input.replaceAll("0","o");
        input = input.replaceAll("9","g");
        

        ArrayList<String> badWords = new ArrayList<>();
        input = input.toLowerCase().replaceAll("[^a-zA-Z]", "");

        // itera sobre cada letra da palavra
        for(int start = 0; start < input.length(); start++) {
            // a partir de cada letra, ele vai verificando se encontra palavras impróprias até que o final da frase seja alcançado, ou o comprimento máximo da palavra seja alcançado. 
            for(int offset = 1; offset < (input.length()+1 - start) && offset < largestWordLength; offset++)  {
                String wordToCheck = input.substring(start, start + offset);
                if(words.containsKey(wordToCheck)) {
                    // caso a parte que ele achou imprópria faça parte de uma palavra maior ele ignora
                    String[] ignoreCheck = words.get(wordToCheck);
                    boolean ignore = false;
                    for (String value : ignoreCheck) {
                        if (input.contains(value)) {
                            ignore = true;
                            break;
                        }
                    }
                    if(!ignore) {
                        badWords.add(wordToCheck);
                    }
                }
            }
        }


        for(String s: badWords) {
            System.out.println(s + " é qualificado como uma palavra imprópria");
        }
        return badWords;

    }

    public static void filterText(String input) {
        BadWordsFilter.loadConfigs();
        List<String> badWords = badWordsFound(input);
        if(!badWords.isEmpty()) {
            throw new ApiRequestException(String.format(BAD_WORD_FOUNDED));
        }
    }
}

// COMO USAR O FILTRO DE PALAVRÕES:
// 
// Primeiro você importa a classe: import com.es.reportverse.utils.BadWordsFilter;
// 1) Inicializa o filtro com esta linha de código: BadWordsFilter.loadConfigs();
// 2) É só chamar a função filterText e passar o texto que você quer que seja feito a verificação com a linha de código abaixo: 
// BadWordsFilter.filterText("texto aqui")
//
// BASE DE DADOS DE PALAVRÕES:
//
// - Se quiser adicionar mais palavras a serem bloqueadas é só clicar no link abaixo e adicionar a tabela:
// https://docs.google.com/spreadsheets/d/1Jf60Mp_B4ZEWqYC8E0ML4RsvIoSOOqO4CWG7LU7C2O8/edit?usp=sharing
//
// ENDPOINT PARA TESTAR O FILTRO SE QUISER:
//
// É só adicionar o método abaixo no controller de publicações, colocar o texto que quer passar e rodar pra testar.
//
// @GetMapping("/filterTest")
// public ResponseEntity<?> testFilterBadWords() {
//     return new ResponseEntity<>(BadWordsFilter.filterText("texto aqui"), HttpStatus.OK);
// }