package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Email {

    private final Discourse discourse

    Email(Discourse discourse) {
        this.discourse = discourse
    }

    def getEmailSettings() {
        discourse.getClient("/admin/email.json").get([:])
    }


    def listEmail(String filter) {
        discourse.getClient("/admin/email/${filter}.json").get([:])
    }

}
