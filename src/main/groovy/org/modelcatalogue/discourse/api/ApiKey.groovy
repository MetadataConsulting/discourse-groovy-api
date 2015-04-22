package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class ApiKey {

    private final Discourse discourse

    ApiKey(Discourse discourse) {
        this.discourse = discourse
    }

    def getApi() {
        discourse.getClient("/admin/api.json").get([:])
    }

    def generateMasterKey() {
        discourse.getClient("/admin/api/key").post([:])
    }

    def generateUserApiKey(Long userId) {
        discourse.getClient("/admin/users/${userId}/generate_api_key.json").post([:])
    }

    def revokeUserApiKey(Long userId) {
        discourse.getClient("/admin/users/${userId}/revoke_api_key.json").delete([:])
    }

    def revokeApiKey(Long id) {
        discourse.getClient("/admin/api/key").delete(body: [id: id])
    }

    def regenerateApiKey(Long id) {
        discourse.getClient("/admin/api/key").put(body: [id: id])
    }

}
