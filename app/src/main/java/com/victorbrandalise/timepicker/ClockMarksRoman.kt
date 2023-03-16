package com.victorbrandalise.timepicker

import androidx.compose.runtime.Composable

@Composable
fun ClockMarksRoman(selectedPart: TimePart, selectedTime: Int, onTime: (Int) -> Unit) {
    if (selectedPart == TimePart.Hour) {
        Mark(text = "XII", index = 0, isSelected = selectedTime == 0, onIndex = onTime)
        Mark(text = "I", index = 1, isSelected = selectedTime == 1, onIndex = onTime)
        Mark(text = "II", index = 2, isSelected = selectedTime == 2, onIndex = onTime)
        Mark(text = "III", index = 3, isSelected = selectedTime == 3, onIndex = onTime)
        Mark(text = "IV", index = 4, isSelected = selectedTime == 4, onIndex = onTime)
        Mark(text = "V", index = 5, isSelected = selectedTime == 5, onIndex = onTime)
        Mark(text = "VI", index = 6, isSelected = selectedTime == 6, onIndex = onTime)
        Mark(text = "VII", index = 7, isSelected = selectedTime == 7, onIndex = onTime)
        Mark(text = "VIII", index = 8, isSelected = selectedTime == 8, onIndex = onTime)
        Mark(text = "IX", index = 9, isSelected = selectedTime == 9, onIndex = onTime)
        Mark(text = "X", index = 10, isSelected = selectedTime == 10, onIndex = onTime)
        Mark(text = "XI", index = 11, isSelected = selectedTime == 11, onIndex = onTime)
        Mark(text = "XII", index = 12, isSelected = selectedTime == 12, onIndex = onTime)
        Mark(text = "XIII", index = 13, isSelected = selectedTime == 13, onIndex = onTime)
        Mark(text = "XIV", index = 14, isSelected = selectedTime == 14, onIndex = onTime)
        Mark(text = "XV", index = 15, isSelected = selectedTime == 15, onIndex = onTime)
        Mark(text = "XVI", index = 16, isSelected = selectedTime == 16, onIndex = onTime)
        Mark(text = "XVII", index = 17, isSelected = selectedTime == 17, onIndex = onTime)
        Mark(text = "XVIII", index = 18, isSelected = selectedTime == 18, onIndex = onTime)
        Mark(text = "XIX", index = 19, isSelected = selectedTime == 19, onIndex = onTime)
        Mark(text = "XX", index = 20, isSelected = selectedTime == 20, onIndex = onTime)
        Mark(text = "XXI", index = 21, isSelected = selectedTime == 21, onIndex = onTime)
        Mark(text = "XXII", index = 22, isSelected = selectedTime == 22, onIndex = onTime)
        Mark(text = "XXIII", index = 23, isSelected = selectedTime == 23, onIndex = onTime)
    } else {
        Mark(text = "XII", index = 0, isSelected = selectedTime == 0, onIndex = onTime)
        Mark(text = "I", index = 1, isSelected = selectedTime == 1, onIndex = onTime)
        Mark(text = "II", index = 2, isSelected = selectedTime == 2, onIndex = onTime)
        Mark(text = "III", index = 3, isSelected = selectedTime == 3, onIndex = onTime)
        Mark(text = "IV", index = 4, isSelected = selectedTime == 4, onIndex = onTime)
        Mark(text = "V", index = 5, isSelected = selectedTime == 5, onIndex = onTime)
        Mark(text = "VI", index = 6, isSelected = selectedTime == 6, onIndex = onTime)
        Mark(text = "VII", index = 7, isSelected = selectedTime == 7, onIndex = onTime)
        Mark(text = "VIII", index = 8, isSelected = selectedTime == 8, onIndex = onTime)
        Mark(text = "IX", index = 9, isSelected = selectedTime == 9, onIndex = onTime)
        Mark(text = "X", index = 10, isSelected = selectedTime == 10, onIndex = onTime)
        Mark(text = "XI", index = 11, isSelected = selectedTime == 11, onIndex = onTime)
    }
}