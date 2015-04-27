package org.modelcatalogue.discourse.api

import org.modelcatalogue.discourse.Discourse

class Categories {

    private final Discourse discourse

    Categories(Discourse discourse) {
        this.discourse = discourse
    }

    def createCategory(String name, String color, String textColor, String description = null, Long parentCategoryId = null) {
        Map<String, Object> args = [name: name, color: color, text_color: textColor]

        if (description) {
            args.description = description
        }

        if (parentCategoryId) {
            args.parentCategoryId = parentCategoryId
        }

        discourse.getClient("/categories").post(body: args, requestContentType: 'application/x-www-form-urlencoded')
    }

    def getCategories(Map<String, Object> params = [:]) {
        discourse.getClient('/categories.json').get(query: params)
    }

    def getLatestTopics(String categorySlug) {
        discourse.getClient("/category/${categorySlug}/l/latest.json").get([:])
    }

    def getTopTopics(String categorySlug) {
        discourse.getClient("/category/${categorySlug}/l/top.json").get([:])
    }

    def getNewTopics(String categorySlug) {
        discourse.getClient("/category/${categorySlug}/l/new.json").get([:])
    }

    def getCategory(Long id) {
        discourse.getClient("/c/${id}/show").get([:])
    }

    def getCategory(String name) {
        def result = search name
        if (result.status != 200) {
            return null
        }
        def data = result.data.categories.find { it.name == name }
        if (data) {
            return getCategory(data.id as Long)
        }
        return null
    }

    def search(String term, Map<String, Object> options = [:]) {
        discourse.search.search(term, 'category', options)
    }

}
