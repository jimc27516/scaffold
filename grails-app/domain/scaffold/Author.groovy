package scaffold

class Author {

    String firstName
    String lastName
    def books = []

    static constraints = {
        firstName(blank: false, nullable: false)
        lastName(blank: false, nullable: false)
    }
}
