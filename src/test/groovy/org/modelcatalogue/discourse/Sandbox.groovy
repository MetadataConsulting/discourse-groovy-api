package org.modelcatalogue.discourse

import groovy.json.JsonOutput
import spock.lang.Specification

class Sandbox extends Specification {

    Discourse discourse = Discourse.create 'http://192.168.1.114', 'af9402ba45b8f4aff5a84bcdf6da85fc7548db746026c5095ed652d0f83fcd8b', 'discourse'

    def "get some posts"() {
        def topic = discourse.posts.createPost(14, """
            one two three four five six seven eight nine ten ${System.currentTimeMillis()}
        """.stripIndent().trim())

        println JsonOutput.prettyPrint(JsonOutput.toJson(topic.data))

        expect:
        topic instanceof Reader
        topic
        topic.data instanceof Map
        topic.status == 200

    }

}
