package com.example.twidy.chats

import com.example.twidy.R
import com.example.twidy.domain.Failure
import com.example.twidy.domain.NetworkFailure
import com.example.twidy.domain.Success
import com.example.twidy.ui.chats.ChatsViewModel
import com.example.twidy.ui.chats.entities.ChatItem
import com.example.twidy.ui.chats.entities.FavoriteItem
import com.example.twidy.ui.chats.FavoritesType
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.junit.Ignore
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class ChatsViewModelTest {

    private lateinit var viewModel: ChatsViewModel

    @Before
    fun init(){
        viewModel = ChatsViewModel()
        viewModel.apply {
            getChatsUseCase = mock()
            getFavoritesUseCase = mock()
            archiveChatsUseCase = mock()
            resultConfirmData = mock{on{access_token} doReturn("token")}
        }
    }

    private fun fetchCorrectChats() = runTest {
        val chats = arrayListOf(mock<ChatItem>{on{checked} doReturn(true)}, mock(), mock())
        whenever(viewModel.getChatsUseCase.invoke(any())).thenReturn(flow{emit(Success(chats, true))})
        viewModel.fetchChats()
    }

    private fun fetchUnselectedChats() = runTest {
        val chats = arrayListOf(mock<ChatItem>{on{checked} doReturn(false)})
        whenever(viewModel.getChatsUseCase.invoke(any())).thenReturn(flow{emit(Success(chats, true))})
        viewModel.fetchChats()
    }

    @Test
    fun `Get chats successfully`() = runTest {
        fetchCorrectChats()
        assertEquals(3,viewModel.chatsListLiveData.value!!.size)
    }

    @Test
    fun `Get chats api error`() = runTest {
        whenever(viewModel.getChatsUseCase.invoke(any())).thenReturn(flow{emit(Failure("api error"))})
        viewModel.fetchChats()
        assertEquals("api error",viewModel.apiError.value)
    }

    @Test
    fun `Get chats network error`() = runTest {
        whenever(viewModel.getChatsUseCase.invoke(any())).thenReturn(flow{emit(NetworkFailure)})
        viewModel.fetchChats()
        assertEquals(R.string.chats_error,viewModel.error.value)
    }

    @Ignore("Nonsensical test")
    @Test
    fun `Archive chats successfully`() = runTest {
        fetchCorrectChats()
        whenever(viewModel.archiveChatsUseCase.invoke(any(), any())).thenReturn(Success("ok",true))
        viewModel.archiveChats()
        assertEquals(3,viewModel.chatsListLiveData.value!!.size)
    }

    @Test
    fun `Archive chats api error`() = runTest {
        fetchCorrectChats()
        whenever(viewModel.archiveChatsUseCase.invoke(any(), any())).thenReturn(NetworkFailure)
        viewModel.archiveChats()
        assertEquals(R.string.archive_error,viewModel.error.value)
    }
    
    @Test
    fun `Archive chats not selected`() = runTest {
        fetchUnselectedChats()
        viewModel.archiveChats()
        assertEquals(R.string.choose_chats,viewModel.error.value)
    }

    @Test
    fun `Get favorites successfully`() = runTest {
        val favorites = arrayListOf(mock<FavoriteItem>(), mock(),mock())
        whenever(viewModel.getFavoritesUseCase.invoke(any())).thenReturn(Success(favorites,true))
        viewModel.fetchFavorites(FavoritesType.ALL)
        assertEquals(3,viewModel.favoriteListLiveData.value!!.size)
    }

    @Test
    fun `Get favorites api error`() = runTest {
        whenever(viewModel.getFavoritesUseCase.invoke(any())).thenReturn(Failure("api error"))
        viewModel.fetchFavorites(FavoritesType.ALL)
        assertEquals("api error",viewModel.apiError.value)
    }

    @Test
    fun `Get favorites network error`() = runTest {
        whenever(viewModel.getFavoritesUseCase.invoke(any())).thenReturn(NetworkFailure)
        viewModel.fetchFavorites(FavoritesType.ALL)
        assertEquals(R.string.favorite_error,viewModel.error.value)
    }
}