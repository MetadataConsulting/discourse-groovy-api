package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Badges {

    private final Discourse discourse

    Badges(Discourse discourse) {
        this.discourse = discourse
    }

    def getBadges() {
        discourse.getClient("/admin/badges.json").get([:])
    }


    def getUserBadges(String username = discourse.username) {
        discourse.getClient("/users/${username}/activity/badges.json").get([:])
    }

    def grantUserBadge(Map<String, Object> params) {
        discourse.getClient("/user_badges").post([body:params])
    }

}
