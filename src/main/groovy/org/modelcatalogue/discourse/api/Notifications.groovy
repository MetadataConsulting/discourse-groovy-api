package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Notifications {

    private final Discourse discourse

    Notifications(Discourse discourse) {
        this.discourse = discourse
    }

    def getNotifications() {
        discourse.getClient('/notifications.json').get([:])
    }
}
