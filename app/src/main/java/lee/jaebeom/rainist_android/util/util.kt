package lee.jaebeom.rainist_android.util

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import lee.jaebeom.rainist_android.R

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
private val DELIM_LINKS = ","
private val DELIM_LINK_PARAM = ";"


//Header link 예시
//<https://api.github.com/user/7378196/repos?page=2>; rel="next", <https://api.github.com/user/7378196/repos?page=8>; rel="last"

fun getLast(linkHeader: String?) : Int{
    var last = 1
    val links = linkHeader?.split(DELIM_LINKS)
    if (links != null) {
        for (link in links){
            val segments = link.split(DELIM_LINK_PARAM)
            if (segments.size < 2){ //2개 미만 일경우 (나눠지지 않는 경우)
                continue
            }

            var linkPart = segments[0].trim()
            if (!linkPart.startsWith("<") or !linkPart.endsWith(">")){  //< 로 시작하지 않거나 >로 끝나지 않는 경우
                continue
            }

            linkPart = linkPart.substring(1, linkPart.length-1);
            for (i in 1 until segments.size){
                val rel = segments[i].trim().split("=")
                if (rel.size < 2 || "rel" != rel[0]){
                    continue
                }

                var relValue = rel[1]
                if (relValue.startsWith("\"") && relValue.endsWith("\"")){
                    relValue = relValue.substring(1, relValue.length-1)
                }

                val count = linkPart.split("=")[3]
                when(relValue){
                    "last" -> last = count.toInt()
                }
            }
        }
    }
    return last
}

fun isNetworking(context: Context) : Boolean{
    val manager: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = manager.activeNetworkInfo
    return activeNetwork != null && activeNetwork.isConnected
}

fun simpleAlertDialog(context: Context, textId: Int, positiveText: String){
    AlertDialog.Builder(context)
            .setMessage(textId)
            .setPositiveButton(positiveText, { _, _ ->
                (context as Activity).finish()
            })
            .show()

}
val option = RequestOptions()
        .placeholder(R.drawable.avatar_placeholder)
fun glideLoad(view: View, resource: String?, imageView: ImageView){
    Glide.with(view)
        .load(resource)
        .apply(option)
        .into(imageView)

}

fun glideLoad(context: Context, resource: String?, imageView: ImageView){
    Glide.with(context)
            .load(resource)
            .apply(option)
            .into(imageView)

}
fun glideLoad(activity: Activity, resource: String?, imageView: ImageView){
    Glide.with(activity)
            .load(resource)
            .apply(option)
            .into(imageView)

}

fun glideLoad(fragment: Fragment, resource: String?, imageView: ImageView){
    Glide.with(fragment)
            .load(resource)
            .apply(option)
            .into(imageView)

}