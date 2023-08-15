package ru.otus.spring.integration.services;

import ru.otus.spring.integration.model.Butterfly;
import ru.otus.spring.integration.model.Caterpillar;
import ru.otus.spring.integration.model.Chrysalis;
import ru.otus.spring.integration.model.Egg;

public interface ButterflyLifecycleService {

    Egg birthBaterfly(String kind);

    Caterpillar toCaterpillar(Egg egg);

    Chrysalis toChrysalis(Caterpillar caterpillar);

    Butterfly toButterfly(Chrysalis chrysalis);
}
