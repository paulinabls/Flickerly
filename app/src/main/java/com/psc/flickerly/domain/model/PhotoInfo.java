package com.psc.flickerly.domain.model;

import java.util.Objects;

public class PhotoInfo {
    private String id;
    private String secret;
    private String server;
    private int farm;

    public PhotoInfo(final String id, final String secret, final String server, final int farm) {
        this.id = id;
        this.secret = secret;
        this.server = server;
        this.farm = farm;
    }

    public String getId() {
        return id;
    }

    public String getSecret() {
        return secret;
    }

    public String getServer() {
        return server;
    }

    public int getFarm() {
        return farm;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PhotoInfo photoInfo = (PhotoInfo) o;
        return farm == photoInfo.farm &&
               Objects.equals(id, photoInfo.id) &&
               Objects.equals(secret, photoInfo.secret) &&
               Objects.equals(server, photoInfo.server);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, secret, server, farm);
    }
}
