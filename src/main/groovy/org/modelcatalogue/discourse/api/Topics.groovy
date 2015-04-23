package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Topics {

    private final Discourse discourse

    Topics(Discourse discourse) {
        this.discourse = discourse
    }

    def createTopic(String title, String raw, String categorySlug = null, Boolean skipValidations = null, Boolean autoTrack = null) {
        Map<String, Object> params = [title: title, raw: raw]
        if (categorySlug) {
            params.category = categorySlug
        }
        if (skipValidations != null) {
            params.skip_validations = skipValidations
        }
        if (autoTrack != null) {
            params.auto_track = autoTrack
        }
        discourse.getClient("/posts").post([body: params])
    }

    def getLatestTopics(Map<String, String> params = [:]) {
        discourse.getClient("/latest.json").get(query: params)
    }

    def getNewTopics(Map<String, String> params = [:]) {
        discourse.getClient("/new.json").get(query: params)
    }

    def renameTopic(Long topicId, String title) {
        discourse.getClient("/t/${topicId}.json").put(body: [topic_id: topicId, title: title], requestContentType: 'application/json')
    }

    def recategorizeTopic(Long topicId, Long categoryId) {
        discourse.getClient("/t/${topicId}.json").put(body: [topic_id: topicId, category_id: categoryId], requestContentType: 'application/json')
    }

    def getTopic(Long id, Map<String, Object> params = [:]) {
        discourse.getClient("/t/${id}.json").get(query: params)
    }

    def getTopicsBy(String username = discourse.username, Map<String, Object> params = [:]) {
        discourse.getClient("/topics/created-by/${username}.json").get(query: params)
    }

    def deleteTopic(Long id) {
        discourse.getClient("/t/${id}.json").delete([:])
    }

}
