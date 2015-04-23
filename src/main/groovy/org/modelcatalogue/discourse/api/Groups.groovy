package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Groups {

    private final Discourse discourse

    Groups(Discourse discourse) {
        this.discourse = discourse
    }

    def createGroup(String name, boolean visible = true) {
        Map<String, Object> args = [name: name, visible: visible]

        discourse.getClient("/admin/groups").post(body: args, requestContentType: 'application/x-www-form-urlencoded')
    }

    def getGroups() {
        discourse.getClient('/admin/groups.json').get([:])
    }

    // TODO: implement add to group and remove from group
    // the implementation is unclear in the ruby geb

//    def group_add(group_id, users)
//    users.keys.each do |key|
//    # Accept arrays and convert to comma-delimited string.
//    if users[key].respond_to? :join
//    users[key] = users[key].join(",")
//    end
//
//    # Accept non-plural user_id or username, but send pluralized version in the request.
//    if key.to_s[-1] != 's'
//    users["#{key}s"] = users[key]
//    users.delete(key)
//    end
//    end
//
//    put("/admin/groups/#{group_id}/members.json", users)
//    end
//
//    def group_remove(group_id, user)
//    delete("/admin/groups/#{group_id}/members.json", user)
//    end
}
