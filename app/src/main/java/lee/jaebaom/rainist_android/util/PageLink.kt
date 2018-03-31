package lee.jaebaom.rainist_android.util

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class PageLink{
    private val DELIM_LINKS = ","
    private val DELIM_LINK_PARAM = ";"
    private var last: Int? = null

    //Header link 예시
    //<https://api.github.com/user/7378196/repos?page=2>; rel="next", <https://api.github.com/user/7378196/repos?page=8>; rel="last"

    fun getLast(link: String) : Int?{
        val links = link.split(DELIM_LINKS)
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

                val count = linkPart.split("=")[1]
                when(relValue){
                    "last" -> last = count.toInt()
                }
            }
        }
        return last
    }
}