package org.modelcatalogue.discourse

import groovy.json.JsonOutput
import org.junit.Rule
import org.junit.rules.TemporaryFolder
import org.modelcatalogue.discourse.api.Backups
import org.modelcatalogue.discourse.util.DelegatingMainClass
import spock.lang.Ignore
import spock.lang.Specification

@Ignore
class Sandbox extends Specification {

    @Rule TemporaryFolder temporaryFolder = new TemporaryFolder()

    Discourse discourse = Discourse.create 'http://discourse.metadataregistry.org.uk/', '7b6d51fd026e18e3d96a868501f4890d4cff87a6de5f4cfb26778685de534622', 'admin'


    def "download latest backup"() {
        File testFolder = temporaryFolder.newFolder('backup-tests')

        File propFile = new File(testFolder, 'discourse.properties')
        Properties props = new Properties()
        props.setProperty('discourse.url', 'http://discourse.metadataregistry.org.uk/')
        props.setProperty('discourse.key', 'xxx')
        props.setProperty('discourse.user', 'admin')
        propFile.withWriter {
            props.store(it,  'Test properties file')
        }

        File bkpDir = new File(testFolder, 'backups')
        bkpDir.mkdirs()

        Backups.main(propFile.absolutePath, bkpDir.absolutePath)

        expect:
        bkpDir.listFiles().size() == 1
        bkpDir.listFiles()[0].name.endsWith('tar.gz')
        bkpDir.listFiles()[0].size() > 100

    }

    def "download latest backup (using delegating class)"() {
        File testFolder = temporaryFolder.newFolder('backup-tests')

        File propFile = new File(testFolder, 'discourse.properties')
        Properties props = new Properties()
        props.setProperty('discourse.url', 'http://discourse.metadataregistry.org.uk/')
        props.setProperty('discourse.key', 'xxx')
        props.setProperty('discourse.user', 'admin')
        propFile.withWriter {
            props.store(it,  'Test properties file')
        }

        File bkpDir = new File(testFolder, 'backups')
        bkpDir.mkdirs()

        DelegatingMainClass.main('backup', propFile.absolutePath, bkpDir.absolutePath)

        expect:
        bkpDir.listFiles().size() == 1
        bkpDir.listFiles()[0].name.endsWith('tar.gz')

    }

    def "download backup"() {
        def result = discourse.backups.backups

        println JsonOutput.prettyPrint(JsonOutput.toJson(result.data))

        expect:
        result

        when:

        File backupsDir = temporaryFolder.newFolder('discourse-backups')
        backupsDir.mkdirs()

        for (backup in result.data) {
            def backupFile = discourse.backups.downloadBackup(backup.filename)
            new File(backupsDir, backup.filename) << backupFile.data
        }

        then:
        true
    }
}
