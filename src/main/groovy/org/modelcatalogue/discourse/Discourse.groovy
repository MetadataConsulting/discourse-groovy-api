package org.modelcatalogue.discourse

import groovyx.net.http.RESTClient
import groovyx.net.http.URIBuilder
import org.modelcatalogue.discourse.api.ApiKey
import org.modelcatalogue.discourse.api.Backups
import org.modelcatalogue.discourse.api.Badges
import org.modelcatalogue.discourse.api.Categories
import org.modelcatalogue.discourse.api.Email
import org.modelcatalogue.discourse.api.Groups
import org.modelcatalogue.discourse.api.Posts
import org.modelcatalogue.discourse.api.PrivateMessages
import org.modelcatalogue.discourse.api.Search
import org.modelcatalogue.discourse.api.Tags
import org.modelcatalogue.discourse.api.Topics
import org.modelcatalogue.discourse.api.Users
import org.modelcatalogue.discourse.sso.SingleSignOn


class Discourse {

    private final String apiKey
    private final String singleSignOnSecret
    private final Closure builderConfiguration

    final String username
    final String discourseServerUrl

    private Discourse(String discourseServerUrl, String apiKey, String singleSignOnSecret, String username, Closure builderConfiguration) {
        this.discourseServerUrl = discourseServerUrl
        this.apiKey = apiKey
        this.username = username
        this.singleSignOnSecret = singleSignOnSecret
        this.builderConfiguration = builderConfiguration ?: {}
    }

    static Discourse create(String discourseServerUrl, String apiKey, String user, String singleSignOnSecret, @DelegatesTo(RESTClient) Closure builderConfiguration = {}) {
        new Discourse(discourseServerUrl, apiKey, singleSignOnSecret, user, builderConfiguration)
    }

    static Discourse create(String discourseServerUrl, String apiKey, String user, @DelegatesTo(RESTClient) Closure builderConfiguration = {}) {
        new Discourse(discourseServerUrl, apiKey, null, user, builderConfiguration)
    }


    Discourse user(String user) {
        new Discourse(this.discourseServerUrl, this.apiKey, this.singleSignOnSecret, user, this.builderConfiguration)
    }

    Discourse configure(@DelegatesTo(RESTClient) Closure builderConfiguration) {
        new Discourse(this.discourseServerUrl, this.apiKey, this.singleSignOnSecret, this.username, builderConfiguration)
    }

    RESTClient getClient(String path) {
        URIBuilder uriBuilder = new URIBuilder(discourseServerUrl)
        uriBuilder.addQueryParam('api_key', apiKey)
        if (username) {
            uriBuilder.addQueryParam('api_username', username)
        }
        uriBuilder.path = path
        RESTClient client = new RESTClient(uriBuilder.toString())
        client.handler.failure = { resp, data -> client.handler.success(resp, data) }
        client.with builderConfiguration
        client
    }

    ApiKey getApiKey() {
        new ApiKey(this)
    }

    Backups getBackups() {
        new Backups(this)
    }

    Badges getBadges() {
        new Badges(this)
    }

    Categories getCategories() {
        new Categories(this)
    }

    Email getEmail() {
        new Email(this)
    }

    Groups getGroups() {
        new Groups(this)
    }

    Posts getPosts() {
        new Posts(this)
    }

    PrivateMessages getPrivateMessages() {
        new PrivateMessages(this)
    }

    Search getSearch() {
        new Search(this)
    }

    Tags getTags() {
        new Tags(this)
    }

    Topics getTopics() {
        new Topics(this)
    }

    Users getUsers() {
        new Users(this)
    }

    SingleSignOn getSingleSignOn(){
        new SingleSignOn(singleSignOnSecret, discourseServerUrl)
    }

}
