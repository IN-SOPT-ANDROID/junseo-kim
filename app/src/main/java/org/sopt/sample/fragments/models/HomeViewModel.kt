package org.sopt.sample.fragments.models

import androidx.lifecycle.ViewModel
import org.sopt.sample.R
import org.sopt.sample.fragments.adapters.GithubUsersAdapter.Companion


class HomeViewModel : ViewModel(){
    val exampleList = listOf<UserRepo>(
        UserRepo(
            image = null,
            name = "junseo-repo",
            repositoryName = null,
            dataType = Companion.header
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "Youth-Pick",
            dataType = Companion.content
        ),UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "junstagram",
            dataType = Companion.content
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "AndroidLab",
            dataType = Companion.content
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "print",
            dataType = Companion.content
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "IN-SOPT-ANDROID / junseo-kim",
            dataType = Companion.content
        ),UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "Youth-Pick",
            dataType = Companion.content
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "junstagram",
            dataType = Companion.content
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "AndroidLab",
            dataType = Companion.content
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "print",
            dataType = Companion.content
        ),
        UserRepo(
            image = R.drawable.github,
            name = "Kim Junseo",
            repositoryName = "IN-SOPT-ANDROID / junseo-kim",
            dataType = Companion.content
        )
    )
}