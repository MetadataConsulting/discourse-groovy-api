package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Posts {

    private final Discourse discourse

    Posts(Discourse discourse) {
        this.discourse = discourse
    }

    def createPost(Long topicId, String raw) {
        discourse.getClient("/posts").post([body: [topic_id: topicId, raw: raw]])
    }

    def getPost(Long id, Long version = null) {
        Map<String, Object> query = [:]

        if (version) {
            query.version = version
        }

        discourse.getClient("/posts/${id}.json").get(query: query)
    }

    def wikifyPost(Long id) {
        discourse.getClient("/posts/${id}/wiki").put(body: [wiki: true])
    }

    def editPost(Long id, String raw) {
        discourse.getClient("/posts/${id}").put(body: [raw: raw])
    }

}
