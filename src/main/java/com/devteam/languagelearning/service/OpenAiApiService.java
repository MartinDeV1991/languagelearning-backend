package com.devteam.languagelearning.service;

import com.devteam.languagelearning.config.OpenAiConfig;
import com.devteam.languagelearning.model.RootWord;
import com.devteam.languagelearning.model.Word;
import com.theokanning.openai.completion.CompletionChoice;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpenAiApiService {
    static OpenAiService service = new OpenAiService(OpenAiConfig.getOpenAiKey());

    // No language mentioned
    public String getPartOfSpeech(Word word) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "Find the part of speech of the given word within the provided context sentence."));
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "You should answer with one of the following: verb, noun, adjective, adverb, preposition, pronoun, conjunction, interjection, other."));
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "If the word could be either a noun or a verb based on the context, provide the part of speech that best fits the given sentence. Provide only one word as your answer."));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), "Find the part of speech of the word " + word.getWord() + " in the context sentence '" + word.getContextSentence() + "'."));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .build();
        List<ChatCompletionChoice> result = service.createChatCompletion(chatCompletionRequest).getChoices();
        System.out.println(result);
        return result.get(0).getMessage().getContent();
    }

    public RootWord getRootVerb(Word word) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "You find the infinitive form of the given verb, when provided the verb and the context sentence it is found in. You answer in one word only."));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), "Give me the base form of the verb 'verdween' in the sentence 'Alles verdween: Sue, Johan, de keuken.'"));
        messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), "verdwijnen"));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), "Give me the base form of the verb " + word.getWord() + " in the sentence '" + word.getContextSentence() + "'."));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .build();
        List<ChatCompletionChoice> result = service.createChatCompletion(chatCompletionRequest).getChoices();
        System.out.println(result);
        return new RootWord(result.get(0).getMessage().getContent());
    }

    public RootWord getRootNoun(Word word) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "You find the base form of the given noun, when provided the noun and the context sentence it is found in. You answer in one word followed by a space and then the correct article (form of the) in brackets."));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), "Give me the base form of the noun 'steegje' in the sentence 'Ze wees met haar stok naar het steegje naast de bioscoop.'"));
        messages.add(new ChatMessage(ChatMessageRole.ASSISTANT.value(), "steeg (de)"));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), "Give me the base form of the noun " + word.getWord() + " in the sentence '" + word.getContextSentence() + "'."));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .build();
        List<ChatCompletionChoice> result = service.createChatCompletion(chatCompletionRequest).getChoices();
        System.out.println(result);
        return new RootWord(result.get(0).getMessage().getContent());
    }

    public RootWord getRoot(Word word, String partOfSpeech) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "You find the base form of the given " + partOfSpeech +", when provided the " + partOfSpeech + " and the context sentence it is found in. You answer in one word."));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), "Give me the base form of the " + partOfSpeech + " " + word.getWord() + " in the sentence '" + word.getContextSentence() + "'."));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .build();
        List<ChatCompletionChoice> result = service.createChatCompletion(chatCompletionRequest).getChoices();
        System.out.println(result);
        return new RootWord(result.get(0).getMessage().getContent());
    }

    public RootWord getRootWordDefinition(RootWord rootWord) {
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM.value(), "Provided a word in " + rootWord.getLanguage() + ", you give the English translation or translations. If there are multiple possible meanings, you give all of the most common ones, separated with commas. You answer in as few words as possible."));
        messages.add(new ChatMessage(ChatMessageRole.USER.value(), "Translate the word " + rootWord.getWord() + " (" + rootWord.getPartOfSpeech() + ")."));
        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest.builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(50)
                .build();
        List<ChatCompletionChoice> result = service.createChatCompletion(chatCompletionRequest).getChoices();
        System.out.println(result);
        rootWord.setDefinitionInEnglish(result.get(0).getMessage().getContent());
        return rootWord;
    }
}
