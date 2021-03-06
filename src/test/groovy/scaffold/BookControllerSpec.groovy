package scaffold

import grails.test.mixin.*
import spock.lang.*

@TestFor(BookController)
@Mock(Book)
class BookControllerSpec extends Specification {

    def populateValidParams(params) {
        assert params != null

        // make an author
        def a = new Author(firstName: "Jim", lastName: "Campbell")

        params.title = "Title of the Book"
        params.author = a
    }

    void "Test the index action returns the correct model"() {

        when:"The index action is executed"
            controller.index()

        then:"The model is correct"
            !model.bookList
            model.bookCount == 0
    }

    void "Test the create action returns the correct model"() {
        when:"The create action is executed"
            controller.create()

        then:"The model is correctly created"
            model.book!= null
    }

    void "Test the save action correctly persists an instance"() {

        when:"The save action is executed with an invalid instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'POST'
            def book = new Book()
            book.validate()
            controller.save(book)

        then:"The create view is rendered again with the correct model"
            model.book!= null
            view == 'create'

        when:"The save action is executed with a valid instance"
            response.reset()
            populateValidParams(params)
            book = new Book(params)

            controller.save(book)

        then:"A redirect is issued to the show action"
            response.redirectedUrl == '/books/1'
            controller.flash.message != null
            Book.count() == 1
    }

    void "Test that the show action returns the correct model"() {
        when:"The show action is executed with a null domain"
            controller.show(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the show action"
            populateValidParams(params)
            def book = new Book(params)
            controller.show(book)

        then:"A model is populated containing the domain instance"
            model.book == book
    }

    void "Test that the edit action returns the correct model"() {
        when:"The edit action is executed with a null domain"
            controller.edit(null)

        then:"A 404 error is returned"
            response.status == 404

        when:"A domain instance is passed to the edit action"
            populateValidParams(params)
            def book = new Book(params)
            controller.edit(book)

        then:"A model is populated containing the domain instance"
            model.book == book
    }

    void "Test the update action performs an update on a valid domain instance"() {
        when:"Update is called for a domain instance that doesn't exist"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'PUT'
            controller.update(null)

        then:"A 404 error is returned"
            response.redirectedUrl == '/books'
            flash.message != null

        when:"An invalid domain instance is passed to the update action"
            response.reset()
            def book = new Book()
            book.validate()
            controller.update(book)

        then:"The edit view is rendered again with the invalid instance"
            view == 'edit'
            model.book == book

        when:"A valid domain instance is passed to the update action"
            response.reset()
            populateValidParams(params)
            book = new Book(params).save(flush: true)
            controller.update(book)

        then:"A redirect is issued to the show action"
            book != null
            response.redirectedUrl == "/books/1"
            flash.message != null
    }

    void "Test that the delete action deletes an instance if it exists"() {
        when:"The delete action is called for a null instance"
            request.contentType = FORM_CONTENT_TYPE
            request.method = 'DELETE'
            controller.delete(null)

        then:"A 404 is returned"
            response.redirectedUrl == '/books'
            flash.message != null

        when:"A domain instance is created"
            response.reset()
            populateValidParams(params)
            def book = new Book(params).save(flush: true)

        then:"It exists"
            Book.count() == 1

        when:"The domain instance is passed to the delete action"
            controller.delete(book)

        then:"The instance is deleted"
            Book.count() == 0
            response.redirectedUrl == '/books'
            flash.message != null
    }

    void "test that the list method returns the correct format based on format setting"() {
        when: "format is json"
            response.format = "json"
            controller.list()

        then:
            response.text == "[]"

        when:"format is xml"
            response.reset()
            response.format="xml"
            controller.list()
        then:
            response.text.take(5) == "<?xml"

        when:"format is html"
            response.reset()
            response.format = "html"
            controller.list()
        then:
            view == "/book/index"
    }
}
