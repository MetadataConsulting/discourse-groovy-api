package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Tags {

    private final Discourse discourse

    Tags(Discourse discourse) {
        this.discourse = discourse
    }

    def showTag(String tag) {
        discourse.getClient("/tags/${tag}").get([:])
    }

}
