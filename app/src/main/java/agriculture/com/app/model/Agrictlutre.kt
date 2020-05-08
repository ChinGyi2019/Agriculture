package agriculture.com.app.model
import android.graphics.drawable.Drawable
data class Agrictlutre (var id: String,
                        var name: String,
                        var favourite: String,
                        var v_f: String,
                        var desp_one: String,
                        var desp_two: String,
                        var desp_three: String,
                        var desp_four: String,
                        var agri_Picture: String) {
     constructor() : this(
        "",
        "",
        "",
        "",
        "",
        "",
        ",",
        "",
        "")
  init {

  }
}