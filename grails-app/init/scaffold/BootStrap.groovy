package scaffold

class BootStrap {

    def init = { servletContext ->
        new Book(title:"The Stand").save()
        new Book(title:"The Shining").save()
    }
    def destroy = {
    }
}
