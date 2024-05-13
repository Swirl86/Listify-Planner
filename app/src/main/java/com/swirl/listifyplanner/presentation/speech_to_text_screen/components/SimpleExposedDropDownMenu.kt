package com.swirl.listifyplanner.presentation.speech_to_text_screen.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.swirl.listifyplanner.ui.constants.LargeDp
import com.swirl.listifyplanner.utils.extenstions.capitalizeWord
import com.swirl.listifyplanner.utils.keyboardAsState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.Locale

// https://stackoverflow.com/a/77353891

/** A text field that allows the user to type in to filter down options. */
@OptIn(ExperimentalFoundationApi::class,ExperimentalMaterial3Api::class)
@Composable
fun <T> TextFieldMenu(
    modifier: Modifier = Modifier,
    /** The label for the text field */
    label: String,
    /** All the available options. */
    options: List<T>,
    /** The selected option. */
    selectedOption: T?,
    /** When the option is selected via tapping on the dropdown option or typing in the option. */
    onOptionSelected: (T?) -> Unit,
    /** Converts [T] to a string for populating the initial text field value. */
    optionToString: (T) -> String,
    /** Returns the filtered options based on the input. This where you need to implement your search. */
    filteredOption: (searchInput: String) -> List<T>,
    /** Creates the row for the filtered down option in the menu. */
    optionToDropdownRow: @Composable (T) -> Unit = { option ->
        Text(optionToString(option))
    },
    /** Creates the view when [filteredOption] returns a empty list. */
    noResultsRow: @Composable () -> Unit = {
        // By default, wrap in a menu item to get the same style
        DropdownMenuItem(
            onClick = {},
            text = {
                Text(
                    "No Matches Found",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.secondary,
                    fontStyle = FontStyle.Italic,
                )
            },
        )
    },
    focusRequester: FocusRequester = remember { FocusRequester() },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (expanded: Boolean) -> Unit = { expanded ->
        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
    },
    textFieldColors: TextFieldColors = ExposedDropdownMenuDefaults.textFieldColors(
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent
    ),
    bringIntoViewRequester: BringIntoViewRequester = remember { BringIntoViewRequester() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
) {
    // Get our text for the selected option
    val selectedOptionText = remember(selectedOption) {
        selectedOption?.let { optionToString(it) }.orEmpty()
    }

    // Default our text input to the selected option
    var textInput by remember(selectedOptionText) {
        mutableStateOf(selectedOptionText)
    }

    var dropDownExpanded by remember { mutableStateOf(false) }

    // Update our filtered options everytime our text input changes
    val filteredOptions = remember(textInput, dropDownExpanded) {
        when (dropDownExpanded) {
            true -> filteredOption(textInput)
            // Skip filtering when we don't need to
            false -> emptyList()
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val isKeyboardOpen by keyboardAsState()

    LaunchedEffect(isKeyboardOpen) {
        /*if (isKeyboardOpen == Keyboard.Closed) {
            dropDownExpanded = false
            focusManager.clearFocus(force = true)
        }*/
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LargeDp)
    ) {
        ExposedDropdownMenuBox(
            expanded = dropDownExpanded,
            onExpandedChange = { dropDownExpanded = !dropDownExpanded },
            modifier = modifier,
        ) {
            // Text Input
            OutlinedTextField(
                value = textInput.capitalizeWord(),
                onValueChange = {
                    dropDownExpanded = true
                    textInput = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .bringIntoViewRequester(bringIntoViewRequester)
                    .menuAnchor()
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (!focusState.isFocused) {
                            dropDownExpanded = false

                            when (filteredOptions.size) {
                                // Auto select the single option
                                1 -> if (filteredOptions.first() != selectedOption) {
                                    onOptionSelected(filteredOptions.first())
                                }
                                // Nothing to we can auto select - reset our text input to the selected value
                                else -> textInput = selectedOptionText
                            }
                        } else {
                            coroutineScope.launch {
                                bringIntoViewRequester.bringIntoView()
                            }
                            dropDownExpanded = true
                        }
                    }
                ,
                label = { Text(label) },
                trailingIcon = { trailingIcon(dropDownExpanded) },
                colors = textFieldColors,
                keyboardOptions = keyboardOptions.copy(
                    imeAction = when (filteredOptions.size) {
                        0, 1 -> ImeAction.Done
                        else -> ImeAction.Search
                    }
                ),
                keyboardActions = KeyboardActions(
                    onAny = {
                        when (filteredOptions.size) {
                            // Remove focus to execute our onFocusChanged effect
                            0, 1 -> focusManager.clearFocus(force = true)
                            // Can't auto select option since we have a list, so hide keyboard to give more room for dropdown
                            else -> {
                                keyboardController?.hide()
                            }
                        }
                    }
                )
            )

            // Dropdown
            if (dropDownExpanded) {
                val dropdownOptions = remember(textInput) {
                    if (textInput.isEmpty()) {
                        // Show all options if nothing to filter yet
                        options
                    } else {
                        filteredOption(textInput)
                    }
                }

                ExposedDropdownMenu(
                    expanded = dropDownExpanded,
                    onDismissRequest = { dropDownExpanded = false }
                ) {
                    if (dropdownOptions.isEmpty()) {
                        noResultsRow()
                    } else {
                        dropdownOptions.forEach { option ->
                            DropdownMenuItem(
                                onClick = {
                                    dropDownExpanded = false
                                    onOptionSelected(option)
                                    focusManager.clearFocus(force = true)
                                },
                                text = {
                                    optionToDropdownRow(option)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun PreviewTextFieldMenu() {
    var chosenLanguage by remember { mutableStateOf<Pair<String, String>?>(null) }

    val isoLanguages = Locale.getISOLanguages()
    val languages = isoLanguages.map { languageCode ->
        val locale = Locale(languageCode)
        val displayName = locale.getDisplayName(locale)
        displayName to languageCode
    }

    val sortedLanguages = languages
        .groupBy { it.first.firstOrNull()?.uppercaseChar() }
        .toSortedMap(compareBy { it ?: ' ' })
        .flatMap { (_, list) -> list.sortedBy { it.first } }



    val optionsFocusRequester = remember { FocusRequester() }

    TextFieldMenu(
        modifier = Modifier.fillMaxWidth(),
        label = "Choose Language",
        options = sortedLanguages,
        selectedOption = chosenLanguage,
        onOptionSelected = { chosenLanguage = it },
        optionToString = { it.toString().capitalizeWord() },
        filteredOption = { searchInput ->
            sortedLanguages.filter { it.toString().contains(searchInput, ignoreCase = true) }
        },
        focusRequester = optionsFocusRequester,
    )
}