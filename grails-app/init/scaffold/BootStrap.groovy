package scaffold

class BootStrap {

    def init = { servletContext ->
        def a = new Author(firstName:"Herman", lastName:"Melville")
        def b = new Author(firstName:"Billy", lastName: "Shakespere")
        new Book(title:"The Stand", author:a).save()
        new Book(title:"The Shining", author:b).save()
    }
    def destroy = {
    }
}
