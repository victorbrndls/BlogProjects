package com.victorbrandalise.timepicker

import androidx.compose.runtime.Composable

@Composable
fun ClockMarks24h(selectedPart: TimePart, selectedTime: Int, onTime: (Int) -> Unit) {
    if (selectedPart == TimePart.Hour) {
        Mark(text = "00", index = 0, isSelected = selectedTime == 0, onIndex = onTime)
        Mark(text = "1", index = 1, isSelected = selectedTime == 1, onIndex = onTime)
        Mark(text = "2", index = 2, isSelected = selectedTime == 2, onIndex = onTime)
        Mark(text = "3", index = 3, isSelected = selectedTime == 3, onIndex = onTime)
        Mark(text = "4", index = 4, isSelected = selectedTime == 4, onIndex = onTime)
        Mark(text = "5", index = 5, isSelected = selectedTime == 5, onIndex = onTime)
        Mark(text = "6", index = 6, isSelected = selectedTime == 6, onIndex = onTime)
        Mark(text = "7", index = 7, isSelected = selectedTime == 7, onIndex = onTime)
        Mark(text = "8", index = 8, isSelected = selectedTime == 8, onIndex = onTime)
        Mark(text = "9", index = 9, isSelected = selectedTime == 9, onIndex = onTime)
        Mark(text = "10", index = 10, isSelected = selectedTime == 10, onIndex = onTime)
        Mark(text = "11", index = 11, isSelected = selectedTime == 11, onIndex = onTime)
        Mark(text = "12", index = 12, isSelected = selectedTime == 12, onIndex = onTime)
        Mark(text = "13", index = 13, isSelected = selectedTime == 13, onIndex = onTime)
        Mark(text = "14", index = 14, isSelected = selectedTime == 14, onIndex = onTime)
        Mark(text = "15", index = 15, isSelected = selectedTime == 15, onIndex = onTime)
        Mark(text = "16", index = 16, isSelected = selectedTime == 16, onIndex = onTime)
        Mark(text = "17", index = 17, isSelected = selectedTime == 17, onIndex = onTime)
        Mark(text = "18", index = 18, isSelected = selectedTime == 18, onIndex = onTime)
        Mark(text = "19", index = 19, isSelected = selectedTime == 19, onIndex = onTime)
        Mark(text = "20", index = 20, isSelected = selectedTime == 20, onIndex = onTime)
        Mark(text = "21", index = 21, isSelected = selectedTime == 21, onIndex = onTime)
        Mark(text = "22", index = 22, isSelected = selectedTime == 22, onIndex = onTime)
        Mark(text = "23", index = 23, isSelected = selectedTime == 23, onIndex = onTime)
    } else {
        Mark(text = "00", index = 0, isSelected = selectedTime == 0, onIndex = onTime)
        Mark(text = "05", index = 1, isSelected = selectedTime == 1, onIndex = onTime)
        Mark(text = "10", index = 2, isSelected = selectedTime == 2, onIndex = onTime)
        Mark(text = "15", index = 3, isSelected = selectedTime == 3, onIndex = onTime)
        Mark(text = "20", index = 4, isSelected = selectedTime == 4, onIndex = onTime)
        Mark(text = "25", index = 5, isSelected = selectedTime == 5, onIndex = onTime)
        Mark(text = "30", index = 6, isSelected = selectedTime == 6, onIndex = onTime)
        Mark(text = "35", index = 7, isSelected = selectedTime == 7, onIndex = onTime)
        Mark(text = "40", index = 8, isSelected = selectedTime == 8, onIndex = onTime)
        Mark(text = "45", index = 9, isSelected = selectedTime == 9, onIndex = onTime)
        Mark(text = "50", index = 10, isSelected = selectedTime == 10, onIndex = onTime)
        Mark(text = "55", index = 11, isSelected = selectedTime == 11, onIndex = onTime)
    }
}