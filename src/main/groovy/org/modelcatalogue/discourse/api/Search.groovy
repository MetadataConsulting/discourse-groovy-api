package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Search {

    private final Discourse discourse

    Search(Discourse discourse) {
        this.discourse = discourse
    }

    def search(String term, Map<String, String> options) {
        options.term = term
        discourse.getClient('/search/query').get(query:options)
    }

}
