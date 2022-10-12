package org.sopt.sample.fragments.models

import androidx.lifecycle.ViewModel
import org.sopt.sample.R

class HomeViewModel : ViewModel(){
    val exampleList = listOf<UserRepo>(
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "Youth-Pick "
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "junstagram"
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "AndroidLab"
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "print"
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "IN-SOPT-ANDROID / junseo-kim"
        ),UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "Youth-Pick "
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "junstagram"
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "AndroidLab"
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "print"
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "IN-SOPT-ANDROID / junseo-kim"
        )

    )
}