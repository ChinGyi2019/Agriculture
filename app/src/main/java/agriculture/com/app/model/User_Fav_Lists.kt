package agriculture.com.app.model

data class User_Fav_Lists (
    var name:String,
    var id:String,
    var desp_one:String,
    var desp_two:String,
    var desp_three:String,
    var desp_four:String,
    var agri_Picture:String
){
    constructor():this(
        ""
        , ""
        ,""
        ,""
        ,""
        ,""
        ,""

        )
      init {

      }

}