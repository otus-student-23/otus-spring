package ru.otus.mar.booklibrary.cache;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class BookCacheImpl implements BookCache {

    private final Map<UUID, ObjectId> ids = new HashMap<>();

    @Override
    public ObjectId get(UUID uuid) {
        return ids.get(uuid);
    }

    @Override
    public void put(UUID uuid, ObjectId objectId) {
        ids.put(uuid, objectId);
    }
}
