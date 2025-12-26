package com.example.questapi.view

import android.R
import android.app.AlertDialog
import android.telecom.Call
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.questapi.modeldata.DataSiswa
import com.example.questapi.viewmodel.StatusUiSiswa
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailSiswaScreen(
    navigateToEditItem: (Int) -> Unit,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    // Memastikan penulisan ViewModel dan Factory benar
    viewModel: DetailViewModel = viewModel(factory = PenyediaViewModel.Factory)
){
    val uiState = viewModel.statusUIDetail // Observasi state dari ViewModel

    Scaffold(
        topBar = {
            SiswaTopAppBar(
                title = stringResource(DestinasiDetail.titleRes),
                canNavigateBack = true,
                navigateUp = navigateBack
            )
        },
        floatingActionButton = {
            // FAB hanya aktif jika data berhasil dimuat (Success)
            if (uiState is StatusUIDetail.Success) {
                FloatingActionButton(
                    onClick = { navigateToEditItem(uiState.satusiswa.id) },
                    shape = MaterialTheme.shapes.medium,
                    modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_large))
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = stringResource(R.string.edit_siswa),
                    )
                }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val coroutineScope = rememberCoroutineScope()

        BodyDetailDataSiswa(
            statusUIDetail = uiState,
            onDelete = {
                coroutineScope.launch {
                    viewModel.hapusSatuSiswa()
                    navigateBack()
                }
            },
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
    ){
        var deleteConfirmationRequestby rememberSaveable { mutableStateOf(false) }
        when(statusUIDetail){
            is StatusUIDetail.Success -> DetailDataSiswa(
                siswa = statusUIDetail.satusiswa,
                modifier = Modifier.fillMaxWidth())
            else -> {}
        }
        OutlinedButton(
            onClick = {dleeteConfirmationRequired = true },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(Rr.string.delete))
        }
        if (deleteConfirmationRequired){
            DeleteConfirmationDialog(
                onDeleteConfirm = {
                    deleteConfirmationRequired = false
                    onDelete()
                },
                onDeleteCancel = {deleteConfrimationRequired = false},
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))
            )
        }
    }
}
@Composable
fun DetailDataSiswa(
    siswa: DataSiswa, modifier: Modifier = Modifier
){
    Card(
        modifier = modifier, color = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.padding__medium)),
            verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_medium))
        ){
            BarisDetailData(
                labelResID = R.string.nama1,
                itemDetail = siswa.nama,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            BarisDetailData(
                labelResID = R.string.alamat1,
                itemDetail = siswa.alamat,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )
            BarisDetailData(
                labelResID = R.string.telpon1,
                itemDetail = siswa.telpon,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(
                        id = R.dimen
                            .padding_medium
                    )
                )
            )

        }
    }
}
