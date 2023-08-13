package ru.otus.spring.integration.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.integration.model.Butterfly;
import ru.otus.spring.integration.model.Caterpillar;
import ru.otus.spring.integration.model.Chrysalis;
import ru.otus.spring.integration.model.Egg;

@Service
@RequiredArgsConstructor
public class ButterflyLifecycleServiceImpl implements ButterflyLifecycleService {

    private final BaterflyGateway gateway;

    @Override
    public Egg birthBaterfly(String kind) {
        Egg egg = new Egg(kind);
        System.out.println("new Egg of " + kind);
        gateway.birthBaterfly(egg);
        return egg;
    }

    @Override
    public Caterpillar toCaterpillar(Egg egg) {
        System.out.println("Egg -> Caterpillar");
        return new Caterpillar(egg.kind());
    }

    @Override
    public Chrysalis toChrysalis(Caterpillar caterpillar) {
        System.out.println("Caterpillar -> Chrysalis");
        return new Chrysalis(caterpillar.kind());
    }

    @Override
    public Butterfly toButterfly(Chrysalis chrysalis) {
        System.out.println("Chrysalis -> Butterfly");
        return new Butterfly(chrysalis.kind());
    }
}
