package ru.otus.mar.booklibrary.cache;

import org.bson.types.ObjectId;

import java.util.UUID;

public interface BookCache {

    ObjectId get(UUID uuid);

    void put(UUID uuid, ObjectId objectId);
}
