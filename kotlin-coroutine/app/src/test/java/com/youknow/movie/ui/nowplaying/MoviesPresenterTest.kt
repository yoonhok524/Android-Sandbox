package com.youknow.movie.ui.nowplaying

import android.view.View
import com.youknow.domain.model.SimpleMovie
import com.youknow.domain.usecase.GetNowPlayingMovies
import com.youknow.movie.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.InOrder
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class MoviesPresenterTest {

    @Mock
    private lateinit var mockView: NowPlayingContract.View

    @Mock
    private lateinit var getMovies: GetNowPlayingMovies

    private lateinit var presenter: NowPlayingPresenter

    private lateinit var inOrder: InOrder

    private val mockMovie1 = SimpleMovie("posterpath1", false, "2019-03-01", 10, "hello world1", 10f)
    private val mockMovie2 = SimpleMovie("posterpath2", true, "2019-03-02", 20, "hello world2", 9f)
    private val mockMovie3 = SimpleMovie("posterpath3", false, "2019-03-03", 30, "hello world3", 8f)
    private val mockMovie4 = SimpleMovie("posterpath4", false, "2019-03-04", 40, "hello world4", 7f)
    private val mockMovie5 = SimpleMovie("posterpath5", false, "2019-03-05", 50, "hello world5", 6f)
    private val mockMovies: List<SimpleMovie> = listOf(
        mockMovie1,
        mockMovie2,
        mockMovie3,
        mockMovie4,
        mockMovie5
    )

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        inOrder = Mockito.inOrder(mockView)

        presenter = NowPlayingPresenter(mockView, getMovies, Dispatchers.Unconfined, Dispatchers.Unconfined)
    }

    @Test
    fun `getMoviesNowPlayingTest() 정상 케이스 - ProgressBar를 보여줬다가 영화 목록을 정상적으로 받아오면 ProgressBar를 가리고 영화 목록을 보여줘야 한다`() = runBlocking {
        `when`(getMovies.get()).thenReturn(mockMovies)

        presenter.getMoviesNowPlaying()

        inOrder.verify(mockView).showProgressBar(View.VISIBLE)
        inOrder.verify(mockView).hideError()
        inOrder.verify(mockView).showProgressBar(View.GONE)
        inOrder.verify(mockView).onMoviesLoaded(mockMovies)
    }

    @Test
    fun `getMoviesNowPlayingTest() 정상 케이스 - 영화 목록이 없는 경우 영화 목록이 없다는 메세지와 함께 에러 화면을 보여줘야 한다`() = runBlocking {
        `when`(getMovies.get()).thenReturn(listOf())

        presenter.getMoviesNowPlaying()

        inOrder.verify(mockView).showProgressBar(View.VISIBLE)
        inOrder.verify(mockView).hideError()
        inOrder.verify(mockView).showProgressBar(View.GONE)
        inOrder.verify(mockView).onError(R.string.err_movies_not_exists)
    }

//    @Test
//    fun `getMoviesNowPlayingTest() 예외 케이스 - 예외가 발생하면 에러 화면을 보여줘야 한다`() = runBlocking {
//        `when`(getMovies.get()).thenThrow(Exception())
//
//        presenter.getMoviesNowPlaying()
//
//        inOrder.verify(mockView).showProgressBar(View.VISIBLE)
//        inOrder.verify(mockView).hideError()
//        inOrder.verify(mockView).showProgressBar(View.GONE)
//        inOrder.verify(mockView).onError(R.string.err_get_movies_failed)
//    }
}