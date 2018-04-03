package lee.jaebeom.rainist_android.data

/**
 * Created by leejaebeom on 2018. 3. 30..
 */
class Data {
    companion object {
        val repositories : ArrayList<Repository> = ArrayList()
        val starGazers : ArrayList<User> = ArrayList()

        fun binarySearchAdd(user: User, repository: Repository){
            var start = 0
            var end = starGazers.size-1
            var middle = 0

            if (end < 0){
                starGazers.add(middle, user)    //새로 유저 추가
                starGazers[middle].starred = ArrayList()
                starGazers[middle].starred.add(repository)  //그 유저에 repo추가
            }

            while (start <= end){
                middle = (start + end) / 2
                if (starGazers[middle].id == user.id){
                    starGazers[middle].starred.add(repository)  //기존에 있던 유저 새로 repo 추가
                    break
                }else {
                    if (start == end){  // 하나만 남은 상황에서 일치 하지 않은 경우
                        if (starGazers[middle].id > user.id){
                            starGazers.add(middle, user)    //새로 유저 추가
                            starGazers[middle].starred = ArrayList()
                            starGazers[middle].starred.add(repository)  //그 유저에 repo추가
                            break
                        }else{
                            starGazers.add(middle + 1, user)    //새로 유저 추가
                            starGazers[middle+1].starred = ArrayList()
                            starGazers[middle+1].starred.add(repository)  //그 유저에 repo추가
                            break
                        }
                    }else{
                        if (starGazers[middle].id > user.id){
                            end = middle - 1
                            if (end < 0){
                                end = 0
                            }
                        }else{
                            start = middle + 1
                        }
                    }
                }
            }
        }
    }
}