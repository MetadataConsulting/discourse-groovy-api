package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Backups {

    private final Discourse discourse

    Backups(Discourse discourse) {
        this.discourse = discourse
    }

    def getBackups() {
        discourse.getClient("/admin/backups.json").get([:])
    }

    def createBackup() {
        discourse.getClient("/admin/backups").post([:])
    }

    def restoreBackup(String filename) {
        discourse.getClient("/admin/backups/${filename}/restore").post([:])
    }

    def downloadBackup(String filename) {
        discourse.getClient("/admin/backups/${filename}").get([:])
    }

}
