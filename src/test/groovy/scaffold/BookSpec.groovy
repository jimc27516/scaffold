package scaffold

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Book)
class BookSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "title can't be null"() {
        when: "book created with no title"
        def b = new Book()

        then:
        !b.validate()
    }

    void "title can't be empty string"() {
        when: "book created with empty string for title"
        def b = new Book(title:"")

        then:
        !b.validate()
    }

}
