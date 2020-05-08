package agriculture.com.app.model

data class Book(
                var id: String,
                var name: String,
                var description: String,
                var download_Link: String,
                var picture: String) {

        constructor() : this(
            "",
            "",
            "",
            "",
            "")
        init {

        }

}