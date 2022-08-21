package ru.otus.httpclientsdemo.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import ru.otus.httpclientsdemo.ui.posts.PostsFragment
import javax.inject.Inject

class FragmentFactory @Inject constructor() : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            PostsFragment::class.java.name -> {
                PostsFragment()
            }

            else -> super.instantiate(classLoader, className)
        }
    }
}