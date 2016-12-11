package scaffold

class Book {

    String title
    static constraints = {
        title(blank: false, nullable: false)
    }
}
