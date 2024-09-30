package org.com.firstech;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        var  input = new Scanner(System.in);

        while(true){
            System.out.println("Digite a entrada relacionada as categorias: ");

            var categorias = input.nextLine();

            System.out.println("Digite a entrada relacionada ao nome: ");

            var nomeProduto = input.nextLine();

            String userPrompt = "Escova de dentes";
            String systemMessage = "" +
                    "Você é um gerador de produtos de um ecommerce de produtos digitais e deve responder apenas a categoria" +
                    "%s".formatted(categorias);

            dispararRequisicao(userPrompt, systemMessage);
        }
    }

    public static void dispararRequisicao(String user, String system) {
        String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null) {
            System.err.println("API key not found. Set the OPENAI_API_KEY environment variable.");
            return;
        }

        OpenAiService service = new OpenAiService(apiKey, Duration.ofSeconds(30));


        ChatCompletionRequest completionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-4")
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.USER.value(), user),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), system)
                ))
                .n(5)
                .build();

        try {

            List<ChatCompletionChoice> response = service.createChatCompletion(completionRequest).getChoices();
            response.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}