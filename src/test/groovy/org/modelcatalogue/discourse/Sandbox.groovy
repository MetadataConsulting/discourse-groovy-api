package org.modelcatalogue.discourse

import groovy.json.JsonOutput
import spock.lang.Specification

class Sandbox extends Specification {

    Discourse discourse = Discourse.create 'http://192.168.1.109', 'af9402ba45b8f4aff5a84bcdf6da85fc7548db746026c5095ed652d0f83fcd8b', 'discourse'

    def "get some posts"() {
        def topic = discourse.posts.createPost(14, """
            bla bla bla

        """.stripIndent().trim())

        println JsonOutput.prettyPrint(JsonOutput.toJson(topic.data))

        expect:
        topic
        topic.status > 300

    }

}
