package com.example.newsapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.newsapp.database.DataAccessObject
import com.example.newsapp.database.DataBase
import com.example.newsapp.model.Articles
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


    private lateinit var db: DataBase
    private lateinit var dao: DataAccessObject

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            DataBase::class.java
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
            Articles(
                id = 1,
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            )

        dao.insertArticles(news)
        val list = dao.getAllLocalArticles()
        assertThat(list).contains(news)

    }

    @Test
    fun updateIntoDB() = runBlockingTest {
        insetIntoDB()
        val news =
            Articles(
                id = 1,
                title = "",
                urlToImage = "lol",
                author = "",
                publishedAt = "uuuu",
                content = "",
                description = "",
                url = ""
            )
        dao.insertArticles(news)
        val list = dao.getAllLocalArticles()
        assertThat(list).contains(news)

    }

    @Test
    fun observeFromDB() = runBlockingTest {
        val news1 = listOf(
            Articles(
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            ),
            Articles(
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            ),
            Articles(
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            ),
            Articles(
                title = "",
                urlToImage = "",
                author = "",
                publishedAt = "",
                content = "",
                description = "",
                url = ""
            ),
        )

        var id = 1
        news1.forEach {
            it.id = id
            dao.insertArticles(it)
            ++id
        }
        val list = dao.getAllLocalArticles()
        assertThat(news1.last().id).isEqualTo(list[0].id)
    }


    @Test
    fun observeFromDBWithNullData() = runBlockingTest {
        val news1 =
            Articles(
                id = 1,
                title = null,
                urlToImage = null,
                author = null,
                publishedAt = null,
                content = null,
                description = null,
                url = null
            )
        val news2 = Articles(
            id = 2,
            title = null,
            urlToImage = null,
            author = null,
            publishedAt = null,
            content = null,
            description = null,
            url = null
        )
        val news3 = Articles(
            id = 3,
            title = null,
            urlToImage = "null",
            author = null,
            publishedAt = null,
            content = null,
            description = "null",
            url = null
        )
        dao.insertArticles(news1)
        dao.insertArticles(news2)
        dao.insertArticles(news3)
        val list = dao.getAllLocalArticles()
        assertThat(list.size).isEqualTo(3)
    }
}