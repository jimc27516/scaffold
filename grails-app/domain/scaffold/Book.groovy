package scaffold

class Book {

    String title
    Author author
    static constraints = {
        title(blank: false, nullable: false)
    }
}
