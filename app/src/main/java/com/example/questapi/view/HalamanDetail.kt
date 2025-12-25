package com.example.questapi.view

import android.telecom.Call
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.questapi.viewmodel.StatusUiSiswa
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel : DetailViewModel = viewModel(factory = PenvediaViewModel.Factory)
){
    Scaffold(
        topBar = {
            SiswaTopAppBar(title = stringResource(DestinasiDetail.titleRes),
            canNavigateBack = true,
            navigateUp = navigationBack
            )
        },
        floatingActionButton = {
            val uiState = viewModel.statusUIDetail
            FloatingActionButton(
                onClick = {
                    when(uiState){ is StatusUIDetail.Success->navigateToEditItem(uiState.satusiswa.id) else->{}}
                },
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = stringResource(R.string.update),
                )
            }
        }, modifier = modifier
    ) {innerPadding ->
        val coroutineScope = rememberCoroutineScope()
        BodyDetailDataSiswa(
            statusUIDetail = viewModel.statusUIDetail,
            onDelete = { coroutineScope.launch {
                viewModel.hapusSatuSiswa()
                navigateBack()
            }},
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
private fun BodyDetailDataSiswa(
    statusUiSiswa: StatusUiSiswa,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
){
    Column(
        modifier =  modifier.padding(dimensionResource(id = R.dimen.padding_medium)),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dime.padding_meidum))
    ){}
}