package org.modelcatalogue.discourse.api

import groovyx.net.http.ContentType
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
        discourse.getClient("/admin/backups/${filename}").get(contentType: ContentType.BINARY)
    }

    static void main(String... args) {
        if (!args || args.size() < 2) {
            println "Usage: \\ndiscourse backup path/to/file.propeties path/to/backups/folder"
        }

        File propsFile = new File(args[0])

        if (!propsFile.exists()) {
            println "File ${propsFile.absolutePath} does not exist!"
            return
        }

        Properties props = new Properties()
        propsFile.withReader {
            props.load(it)
        }

        for (String key in ['discourse.url', 'discourse.key', 'discourse.user']) {
            if (!props.getProperty(key)) {
                println "Property '${key}' is missing in property file ${propsFile.absolutePath}."
                return
            }
        }

        File destFolder = new File(args[1])

        if (!destFolder.exists()) {
            destFolder.mkdirs()
        }

        if (!destFolder.directory) {
            println "Destination ${destFolder.absolutePath} is not a directory!"
            return
        }



        Discourse discourse = Discourse.create(props['discourse.url'].toString(), props['discourse.key'].toString(), props['discourse.user'].toString())

        def backups = discourse.backups.backups.data

        if (!backups) {
            println "There are no backups created yet. Have you set up the scheduler?"
            return
        }



        String filename = backups[0].filename
        File downloaded = new File(destFolder, filename)

        if (downloaded.exists()) {
            println "Backup ${downloaded.absolutePath} already exists. It will be replaced."
            downloaded.delete()
        }

        println "Downloading latest backup ${filename}"

        downloaded << discourse.backups.downloadBackup(filename).data

        println "Lates backup downloaded to ${downloaded.absolutePath}"

    }

}
