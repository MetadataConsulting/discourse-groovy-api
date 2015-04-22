package org.modelcatalogue.discourse.sso

class User {

    final String name
    final String username
    final String email
    final String externalId

    User(String name, String username, String email, String externalId) {
        if (!name) throw new IllegalArgumentException("Name cannot be null")
        if (!username) throw new IllegalArgumentException("Username cannot be null")
        if (!email) throw new IllegalArgumentException("Email cannot be null")
        if (!externalId) throw new IllegalArgumentException("External ID cannot be null")

        this.name = name
        this.username = username
        this.email = email
        this.externalId = externalId
    }
}
