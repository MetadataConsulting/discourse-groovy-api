package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class PrivateMessages {

    private final Discourse discourse

    PrivateMessages(Discourse discourse) {
        this.discourse = discourse
    }

    def getPrivateMessages(String username = discourse.username) {
        discourse.getClient("topics/private-messages/${username}.json").get([:])
    }
}
