package br.com.henrique.beerlist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import br.com.henrique.data.client.BeerListAPI
import br.com.henrique.data.models.BeerItem
import br.com.henrique.data.models.CustomResponse
import br.com.henrique.features.beerlist.repository.BeerListRepository
import br.com.henrique.features.beerlist.viewmodel.BeerListViewModel
import br.com.henrique.helper.MainCoroutinesScopeRule
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BeerListViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @InjectMockKs
    internal lateinit var viewModelTest: BeerListViewModel

    @MockK
    internal lateinit var repositoryTest: BeerListRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesScopeRule = MainCoroutinesScopeRule.create()

    @Before
    fun setup() {
        val client = mockk<BeerListAPI>{}
        repositoryTest = BeerListRepository(client)
        MockKAnnotations.init(this)
    }

    @Test
    fun `on call function nextPage should call repository once`() {
        coEvery { repositoryTest.getBeers(1) } returns CustomResponse.Success(listMock())

        viewModelTest.nextPage()

        coVerify(exactly = 1) {
            repositoryTest.getBeers(1)
        }
    }

    @Test
    fun `on call function getBeersFirstPage should call repository once`() {
        coEvery { repositoryTest.getBeers(1) } returns CustomResponse.Success(listMock())

        viewModelTest.getBeersFirstPage()

        coVerify(exactly = 1) {
            repositoryTest.getBeers(1)
        }
    }
}

fun listMock(): List<BeerItem> {
    return listOf(
        BeerItem(0.0, "", "", "", 0.0, ""),
        BeerItem(0.0, "", "", "", 0.0, ""),
        BeerItem(0.0, "", "", "", 0.0, "")
    )
}
