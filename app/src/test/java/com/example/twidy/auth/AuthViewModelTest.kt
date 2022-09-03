package com.example.twidy.auth

import com.example.twidy.R
import com.example.twidy.domain.Failure
import com.example.twidy.domain.NetworkFailure
import com.example.twidy.domain.Success
import com.example.twidy.data.response.Country
import com.example.twidy.data.response.Location
import com.example.twidy.ui.auth.AuthViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AuthViewModelTest {
    private lateinit var viewModel: AuthViewModel


    @Before
    fun init(){
        viewModel = AuthViewModel()
        viewModel.apply {
            getCountriesUseCase = mock()
            getLocationUseCase = mock()
            authUseCase = mock()
            authConfirmUseCase = mock()
            setPhoneCountry(Country("Russia",7,"RU"))
            phoneNumber.value = "9998887766"
            codeNumber.value = "777777"
        }
    }
    @Test
    fun `Get countries successfully`() = runTest {
        val countries = arrayListOf(mock<Country>(),mock())
        whenever(viewModel.getCountriesUseCase.invoke()).thenReturn(Success(countries,true))
        viewModel.getCountries()
        assertEquals(2,viewModel.countries.value!!.size)
    }

    @Test
    fun `Get countries api error`() = runTest {
        whenever(viewModel.getCountriesUseCase.invoke()).thenReturn(Failure("api error"))
        viewModel.getCountries()
        assertEquals("api error",viewModel.apiError.value)
    }

    @Test
    fun `Get countries network error`() = runTest {
        whenever(viewModel.getCountriesUseCase.invoke()).thenReturn(NetworkFailure)
        viewModel.getCountries()
        assertEquals(R.string.static_data_error,viewModel.error.value)
    }

    @Test
    fun `Get location successfully`() = runTest {
        whenever(viewModel.getLocationUseCase.invoke()).thenReturn(Success(Location("Russia",7),true))
        viewModel.getLocation()
        assertEquals("Russia",viewModel.location.value!!.name)
    }

    @Test
    fun `Get location api error`() = runTest {
        whenever(viewModel.getLocationUseCase.invoke()).thenReturn(Failure("api error"))
        viewModel.getLocation()
        assertEquals("api error",viewModel.apiError.value)
    }

    @Test
    fun `Get location network error`() = runTest {
        whenever(viewModel.getLocationUseCase.invoke()).thenReturn(NetworkFailure)
        viewModel.getLocation()
        assertEquals(R.string.location_error,viewModel.error.value)
    }

    @Test
    fun `Auth successfully`() = runTest {
        whenever(viewModel.authUseCase.invoke(any())).thenReturn(Success("id",true))
        viewModel.auth()
        assertEquals("id",viewModel.auth.value)
    }

    @Test
    fun `Auth api error`() = runTest {
        whenever(viewModel.authUseCase.invoke(any())).thenReturn(Failure("api error"))
        viewModel.auth()
        assertEquals("api error",viewModel.apiError.value)
    }

    @Test
    fun `Auth network error`() = runTest {
        whenever(viewModel.authUseCase.invoke(any())).thenReturn(NetworkFailure)
        viewModel.auth()
        assertEquals(R.string.auth_error,viewModel.error.value)
    }

    @Test
    fun `Confirm successfully`() = runTest {
        whenever(viewModel.authConfirmUseCase.invoke(any(),any(),any())).thenReturn(Success(mock(),true))
        viewModel.confirm()
        assertNotNull(viewModel.confirm.value)
    }

    @Test
    fun `Confirm api error`() = runTest {
        whenever(viewModel.authConfirmUseCase.invoke(any(),any(),any())).thenReturn(Failure("api error"))
        viewModel.confirm()
        assertEquals("api error",viewModel.apiError.value)
    }

    @Test
    fun `Confirm network error`() = runTest {
        whenever(viewModel.authConfirmUseCase.invoke(any(),any(),any())).thenReturn(NetworkFailure)
        viewModel.confirm()
        assertEquals(R.string.auth_error,viewModel.error.value)
    }
}