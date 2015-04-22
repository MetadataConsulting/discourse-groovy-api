package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Users {

    private final Discourse discourse

    Users(Discourse discourse) {
        this.discourse = discourse
    }

    def createUser(String name, String email, String username, String password = null, Boolean active = null) {
        Map<String, Object> params = [name: name, email: email, username: username]
        if (password) {
            params.password = password
        }
        if (active != null) {
            params.active = active
        }
        discourse.getClient("/users").post([body: params])
    }

    def updateTrustLevel(Long id, String level){
        discourse.getClient("/admin/users/$id/trust_level").put([body: [user_id: id, level: level]])
    }

    def inviteAdmin(Map<String, Object> params = [:]) {
        discourse.getClient("/admin/users/invite_admin").post([body: params])
    }

    def getUsers(String type) {
        discourse.getClient("admin/users/list/${type}.json").get([:])
    }

    def getUser(String username = discourse.username, Map<String, Object> params) {
        discourse.getClient("/users/${username}.json").get(query: params)
    }


    def grantAdmin(Long id) {
        discourse.getClient("admin/users/${id}/grant_admin").put([:])
    }

    def revokeAdmin(Long id) {
        discourse.getClient("admin/users/${id}/revoke_admin").put([:])
    }

    def activate(Long id) {
        discourse.getClient("/admin/users/${id}/activate").put([:])
    }

    def logOut(Long id) {
        discourse.getClient("/admin/users/${id}/log_out").post([:])
    }

    def updateEmail(String username = discourse.username, String email) {
        discourse.getClient("/users/${username}/preferences/email").put(query: [email: email])
    }

    def updateUser(String username = discourse.username, Map<String, Object> params) {
        discourse.getClient("/users/${username}").put(query: params)
    }

    def updateUsername(String username = discourse.username, String newUsername) {
        discourse.getClient("/users/${username}/preferences/username").put(query: [new_username: newUsername])
    }

    // TODO: implement the rest of the Users API
//    def update_avatar(args)
//    args = API.params(args)
//    .required(:username, :file)
//    .default(image_type: 'avatar')
//    .to_h
//    upload_response = post("/users/#{args[:username]}/preferences/user_image", args)
//    put("/users/#{args[:username]}/preferences/avatar/pick", { upload_id: upload_response['upload_id'] })
//    end
//
//    def update_user(username, params={})
//    put("/users/#{username}", params)
//    end

}
