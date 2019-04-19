package under.the.bridge

import under.the.bridge.common.TestDataFactory
import under.the.bridge.data.DataManager
import under.the.bridge.data.remote.ApiHandler
import under.the.bridge.util.RxSchedulersOverrideRule
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DataManagerTest {

    @Rule @JvmField val mOverrideSchedulersRule = RxSchedulersOverrideRule()
    @Mock lateinit var mMockApiHandler: ApiHandler

    private var mDataManager: DataManager? = null

    @Before
    fun setUp() {
        mDataManager = DataManager(mMockApiHandler)
    }

    @Test
    fun getPokemonListCompletesAndEmitsPokemonList() {
        val namedResourceList = TestDataFactory.makeNamedResourceList(5)
        val pokemonListResponse = PokemonListResponse(namedResourceList)

//        `when`(mMockApiHandler.getTrackArtist(anyInt()))
//                .thenReturn(Single.just(pokemonListResponse))

//        mDataManager?.getTrackArtist(10)
//                ?.test()
//                ?.assertComplete()
//                ?.assertValue(TestDataFactory.makePokemonNameList(namedResourceList))
    }

    @Test
    fun getPokemonCompletesAndEmitsPokemon() {
        val name = "charmander"
        val pokemon = TestDataFactory.makePokemon(name)
        `when`(mMockApiHandler.getPokemon(anyString()))
                .thenReturn(Single.just(pokemon))

        mDataManager?.getPokemon(name)
                ?.test()
                ?.assertComplete()
                ?.assertValue(pokemon)
    }
}
