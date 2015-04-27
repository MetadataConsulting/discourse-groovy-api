package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Search {

    private final Discourse discourse

    Search(Discourse discourse) {
        this.discourse = discourse
    }

    def search(String term, Map<String, String> options = [:]) {
        search term, null, options
    }

    def search(String term, String type, Map<String, Object> options = [:]) {
        options.term = term
        if (type) {
            options.type_filter = type
        }
        discourse.getClient('/search/query').get(query:options)
    }

}
