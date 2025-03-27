package com.pernasa.varillasbirdsapp.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TriStateCheckbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.pernasa.varillasbirdsapp.R
import com.pernasa.varillasbirdsapp.ui.theme.GreenLime
import com.pernasa.varillasbirdsapp.ui.theme.SearchFieldBackground
import com.pernasa.varillasbirdsapp.ui.theme.SearchPlaceholderColor
import com.pernasa.varillasbirdsapp.ui.theme.SkyBluePrimary
import com.pernasa.varillasbirdsapp.ui.theme.SkyBluePrimaryLight
import com.pernasa.varillasbirdsapp.ui.theme.SkyBlueTertiary
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SearchRow(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    observationFilterState: ToggleableState,
    onObservationFilterStateChange: (ToggleableState) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Row(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp, top = 10.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        TextField(
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Icono de búsqueda",
                    tint = Color.Black
                )
            },
            value = searchQuery,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = SearchFieldBackground,
                focusedContainerColor = SearchFieldBackground,
                disabledTextColor = SearchPlaceholderColor,
                focusedTextColor = Color.Black,
                unfocusedLeadingIconColor = Color.Black,
                focusedLeadingIconColor = Color.Black,
                unfocusedTextColor = Color.Black
            ),
            onValueChange = onSearchQueryChange,
            label = { Text(stringResource(R.string.search_by_name)) },
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(8.dp))
                .border(BorderStroke(0.8.dp, SkyBlueTertiary), RoundedCornerShape(8.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column(
            Modifier.padding(4.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "¿Observadas?", color = SkyBluePrimary)
            TriStateCheckbox(
                state = observationFilterState,
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        onObservationFilterStateChange(
                            when (observationFilterState) {
                                ToggleableState.On -> ToggleableState.Indeterminate
                                ToggleableState.Off -> ToggleableState.On
                                ToggleableState.Indeterminate -> ToggleableState.Off
                            }
                        )
                    }
                },
                colors = CheckboxDefaults.colors(
                    checkedColor = GreenLime,
                    uncheckedColor = SkyBluePrimary,
                    checkmarkColor = Color.Black
                )
            )
        }
    }
}

@Composable
fun FiltersRow(
    onHeightSelected: (List<Int>) -> Unit,
    onFrequencySelected: (List<Int>) -> Unit
) {
    var selectedHeightRanges by rememberSaveable { mutableStateOf<List<Int>>(emptyList()) }
    var selectedFrequencies by rememberSaveable { mutableStateOf<List<Int>>(emptyList()) }

    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            DropdownMenuFilter(
                label = "Medida (cm)",
                options = listOf(
                    "0-15 cm" to 15,
                    "15-30 cm" to 30,
                    "30-50 cm" to 50,
                    "50 cm o más" to 1000
                ),
                selectedOptions = selectedHeightRanges,
                onSelectedChange = {
                    selectedHeightRanges = it
                    onHeightSelected(it)
                },
                Modifier.weight(1F).align(Alignment.CenterVertically)
            )

            DropdownMenuFilter(
                label = "Facilidad Observación",
                options = listOf(
                    "Abundante" to 4,
                    "Frecuente" to 3,
                    "Escaso" to 2,
                    "Dificil" to 1
                ),
                selectedOptions = selectedFrequencies,
                onSelectedChange = {
                    selectedFrequencies = it
                    onFrequencySelected(it)
                },
                Modifier.weight(1F).align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
fun <T> DropdownMenuFilter(
    label: String,
    options: List<Pair<String, T>>,
    selectedOptions: List<T>,
    onSelectedChange: (List<T>) -> Unit,
    modifier: Modifier
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    Box(modifier.padding(8.dp)) {
        Row(
            modifier = modifier.clickable { expanded = true }
                .background(
                    color = SkyBluePrimary ,
                    shape = RoundedCornerShape(8.dp)
                ).border(
                    BorderStroke(2.dp, if (selectedOptions.isEmpty()) Color.Black else GreenLime),
                    shape = RoundedCornerShape(8.dp)
                ).height(40.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                color = if (selectedOptions.isEmpty()) Color.White else Color.Black,
                modifier = Modifier.padding(start = 3.dp)
            )
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                tint = if (selectedOptions.isEmpty()) Color.White else Color.Black
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (label, value) ->
                val isChecked = value in selectedOptions
                DropdownMenuItem(
                    onClick = {
                        val newSelectedOptions = if (isChecked) {
                            selectedOptions - value
                        } else {
                            selectedOptions + value
                        }
                        onSelectedChange(newSelectedOptions)
                    },
                    text = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Checkbox(
                                checked = isChecked,
                                onCheckedChange = null,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = SkyBluePrimaryLight,
                                    uncheckedColor = Color.White
                                )
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = label, color = Color.White)
                        }
                    }
                )
            }
        }
    }
}