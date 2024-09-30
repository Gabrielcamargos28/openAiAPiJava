package org.com.firstech;

import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String userPrompt = "Escova de dentes";
        String systemMessage = "Você é um gerador de produtos de um ecommerce de produtos digitais e deve responder apenas a categoria";



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
                        new ChatMessage(ChatMessageRole.USER.value(), userPrompt),
                        new ChatMessage(ChatMessageRole.SYSTEM.value(), systemMessage)
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
