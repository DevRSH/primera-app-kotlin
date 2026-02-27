package com.example.app_kotlin.trivia

data class Question(
    val id: Int,
    val title: String,
    val options: List<String>,
    val correctIndex: Int
)

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val lives: Int = 3,
    val feedbackText: String? = null,
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentIndex)

    val progressPercent: Int
        get() {
            if (questions.isEmpty()) return 0
            return ((currentIndex.toFloat() / questions.size.toFloat()) * 100).toInt()
        }
}
