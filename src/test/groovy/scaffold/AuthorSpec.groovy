package scaffold

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Author)
class AuthorSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test that first name isn't blank or null"() {
        when:"first name is null"
            def a = new Author(lastName:"Campbell")

        then:"validation fails"
            !a.validate()

        when:"first name is blank"
            def a2 = new Author(firstName:"", lastName:"Campbell")

        then:"validation fails"
            !a2.validate()
    }

    void "test that last name isn't blank or null"() {
        when:"last name is null"
        def a = new Author(firstName:"Jim")

        then:"validation fails"
        !a.validate()

        when:"last name is blank"
        def a2 = new Author(firstName:"Jim", lastName:"")

        then:"validation fails"
        !a2.validate()
    }
}

