package com.example.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsapp.model.local.ArticlesDAO
import com.example.newsapp.model.local.ArticlesDataBase
import com.example.newsapp.model.local.ArticlesEntity
import com.example.newsapp.model.remote.Source
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DBTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var db: ArticlesDataBase
    private lateinit var dao: ArticlesDAO

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ArticlesDataBase::class.java
        ).allowMainThreadQueries().build()

        dao = db.getDao()
    }

    @After
    fun after() {
        db.close()
    }


    @Test
    fun insetIntoDB() = runBlockingTest {
        val news =
            ArticlesEntity(
                id = 1,
                source = Source("name", "name"),
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            )
        dao.insert(news)
        val list = dao.getAllLocalNews()
        assertThat(list).contains(news)

    }

    @Test
    fun updateIntoDB() = runBlockingTest {
        insetIntoDB()
        val news =
            ArticlesEntity(
                id = 1,
                source = Source("lol", "lol"),
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            )
        dao.insert(news)
        val list = dao.getAllLocalNews()
        assertThat(list.size).isEqualTo(1)

    }

    @Test
    fun observeFromDB() = runBlockingTest {
        val news1 =
            ArticlesEntity(
                id = 1,
                source = Source("name", "name"),
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            )
        val news2 = ArticlesEntity(
            id = 2,
            source = Source("name", "name"),
            title = "",
            urlToImage = "",
            author = "",
            publishedAt = "",
            content = "",
            description = "",
            url = ""
        )
        val news3 =
            ArticlesEntity(
                id = 3,
                source = Source("name", "name"),
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            )
        val news4 =
            ArticlesEntity(
                id = 3,
                source = Source("name", "name"),
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "fdgdf",
                content = "",
                description = "sdfdfg",
                url = ""
            )
        dao.insert(news1)
        dao.insert(news2)
        dao.insert(news3)
        dao.insert(news4)
        val list = dao.getAllLocalNews()
        assertThat(list.size).isEqualTo(3)
    }


    @Test
    fun observeFromDBWithNullData() = runBlockingTest {
        val news1 =
            ArticlesEntity(
                id = 1,
                source = null,
                title = null,
                urlToImage = null,
                author = null,
                publishedAt = null,
                content = null,
                description = null,
                url = null
            )
        val news2 = ArticlesEntity(
            id = 2,
            source = null,
            title = null,
            urlToImage = null,
            author = null,
            publishedAt = null,
            content = null,
            description = null,
            url = null
        )
        val news3 = ArticlesEntity(
            id = 2,
            source = null,
            title = null,
            urlToImage = "null",
            author = null,
            publishedAt = null,
            content = null,
            description = "null",
            url = null
        )
        dao.insert(news1)
        dao.insert(news2)
        dao.insert(news3)
        val list = dao.getAllLocalNews()
        assertThat(list.size).isEqualTo(2)
    }
}