package org.modelcatalogue.discourse

import groovy.json.JsonOutput
import spock.lang.Ignore
import spock.lang.Specification

class Sandbox extends Specification {

    Discourse discourse = Discourse.create 'http://192.168.1.114', 'af9402ba45b8f4aff5a84bcdf6da85fc7548db746026c5095ed652d0f83fcd8b', 'discourse'

    @Ignore
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

    def "test searching"() {
        def result = discourse.categories.getCategory('NHIC')

        println JsonOutput.prettyPrint(JsonOutput.toJson(result.data))

        expect:
        result
    }

    @Ignore
    def "create topic with category"() {
        def result = discourse.topics.createTopic(
                "this is a cool title ${System.currentTimeMillis()}",
                "this is ultracool post about nothing because I don't know what to write for ${System.currentTimeMillis()}",
                "Catalogue Elements"
        )

        expect:
        result
    }


    @Ignore
    def "verify user does not exist"() {
        def result = discourse.users.getUser('otto_von_bahnhof')

        println JsonOutput.prettyPrint(JsonOutput.toJson(result.data))

        expect:
        result.status == 404
    }
}
