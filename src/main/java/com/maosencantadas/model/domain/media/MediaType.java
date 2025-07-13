package com.maosencantadas.model.domain.media;

public enum MediaType {
    IMAGE,
    VIDEO,
    AUDIO,
    DOCUMENT;

    public static MediaType get(String type) {
        for (MediaType mediaType : MediaType.values()) {
            if (mediaType.name().equalsIgnoreCase(type)) {
                return mediaType;
            }
        }
        throw new IllegalArgumentException("Unknown media type: " + type);
    }
}
