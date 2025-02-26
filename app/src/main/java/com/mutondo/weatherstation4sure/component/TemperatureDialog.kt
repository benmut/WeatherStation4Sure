package com.mutondo.weatherstation4sure.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.mutondo.weatherstation4sure.R
import com.mutondo.weatherstation4sure.utils.Constants.CELSIUS
import com.mutondo.weatherstation4sure.utils.Constants.FAHRENHEIT

@Composable
fun TemperatureDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (String) -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(dimensionResource(R.dimen.margin_default)),
            shape = RoundedCornerShape(dimensionResource(R.dimen.margin_default)),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                val radioOptions = listOf(CELSIUS, FAHRENHEIT)
                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }

                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .height(dimensionResource(R.dimen.margin_huge))
                                .selectable(
                                    selected = (text == selectedOption),
                                    onClick = { onOptionSelected(text) },
                                    role = Role.RadioButton
                                )
                                .padding(horizontal = dimensionResource(R.dimen.margin_default)),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption),
                                onClick = null
                            )
                            Text(
                                text = text,
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = dimensionResource(R.dimen.margin_default))
                            )
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(dimensionResource(R.dimen.margin_tiny)),
                    ) {
                        Text(stringResource(R.string.dismiss))
                    }
                    TextButton(
                        onClick = {
                            onConfirmation(selectedOption)
                        },
                        modifier = Modifier.padding(dimensionResource(R.dimen.margin_tiny)),
                    ) {
                        Text(stringResource(R.string.confirm))
                    }
                }
            }
        }
    }
}
