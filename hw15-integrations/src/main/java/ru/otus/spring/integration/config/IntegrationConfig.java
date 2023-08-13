package ru.otus.spring.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannelSpec;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.spring.integration.services.ButterflyLifecycleService;

@Configuration
public class IntegrationConfig {

    @Bean
    public MessageChannelSpec<?, ?> eggChannel() {
        return MessageChannels.queue();
    }

    @Bean
    public MessageChannelSpec<?, ?> caterpillarChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> chrysalisChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public MessageChannelSpec<?, ?> butterflyChannel() {
        return MessageChannels.publishSubscribe();
    }

    @Bean
    public IntegrationFlow eggFlow(ButterflyLifecycleService butterflyLifecycleService) {
        return IntegrationFlow.from(eggChannel())
                //.split()
                //.<Egg, Caterpillar>transform(b -> new Caterpillar(b.kind()))
                .handle(butterflyLifecycleService, "toCaterpillar")
                //.aggregate()
                .channel(caterpillarChannel())
                .get();
    }

    @Bean
    public IntegrationFlow caterpillarFlow(ButterflyLifecycleService butterflyLifecycleService) {
        return IntegrationFlow.from(caterpillarChannel())
                .handle(butterflyLifecycleService, "toChrysalis")
                .channel(chrysalisChannel())
                .get();
    }

    @Bean
    public IntegrationFlow chrysalisFlow(ButterflyLifecycleService butterflyLifecycleService) {
        return IntegrationFlow.from(chrysalisChannel())
                .handle(butterflyLifecycleService, "toButterfly")
                .channel(butterflyChannel())
                .get();
    }
}
