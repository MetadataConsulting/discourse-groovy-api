package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Invite {

    private final Discourse discourse

    Invite(Discourse discourse) {
        this.discourse = discourse
    }

    def inviteUser(Map<String, Object> params) {
        discourse.getClient("/invites").post(body: params, requestContentType: 'application/x-www-form-urlencoded')
    }

    def disposableTokens(Map<String, Object> params) {
        discourse.getClient("/invites/disposable").post(body: params, requestContentType: 'application/x-www-form-urlencoded')
    }

    def inviteUserToTopic(Long topicId, Map<String, Object> params) {
        discourse.getClient("/t/${topicId}/invite").post(body: params, requestContentType: 'application/x-www-form-urlencoded')
    }

}
