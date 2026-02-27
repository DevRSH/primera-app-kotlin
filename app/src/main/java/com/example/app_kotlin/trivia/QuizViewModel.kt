package com.example.app_kotlin.trivia

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        QuizUiState(
            questions = seedQuestions()
        )
    )

    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onSelectedOption(index: Int) {
        val current = _uiState.value
        if (current.isFinished) return
        // solo se puede seleccionar si no hay feedback todavia
        if (current.feedbackText != null) return
        _uiState.value = current.copy(selectedIndex = index)
    }

    fun onConfirmAnswer() {
        val current = _uiState.value
        val selected = current.selectedIndex ?: return
        val currentQuestion = current.currentQuestion ?: return

        val isCorrect = selected == currentQuestion.correctIndex

        val feedback = if (isCorrect) "✅ Correcto" else "❌ Incorrecto"

        val newScore = if (isCorrect) current.score + 100 else current.score
        val newLives = if (isCorrect) current.lives else current.lives - 1

        _uiState.value = current.copy(
            score = newScore,
            lives = newLives,
            feedbackText = feedback,
        )
    }

    fun onNextQuestion() {
        val current = _uiState.value

        // si no quedan vidas, finalizar
        if (current.lives <= 0) {
            _uiState.value = current.copy(
                isFinished = true,
                feedbackText = null
            )
            return
        }

        val nextIndex = current.currentIndex + 1
        val finished = nextIndex >= current.questions.size

        _uiState.value = current.copy(
            currentIndex = nextIndex,
            selectedIndex = null,
            feedbackText = null,
            isFinished = finished
        )
    }

    private fun seedQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                title = "¿Que palabra clave se usa para declarar una variable inmutable en Kotlin?",
                options = listOf("var", "val", "let", "const"),
                correctIndex = 1
            ),
            Question(
                id = 2,
                title = "En Jetpack Compose, ¿que anotacion marca una funcion como UI?",
                options = listOf("@UI", "@Widget", "@Composable", "@Compose"),
                correctIndex = 2
            ),
            Question(
                id = 3,
                title = "¿Que componente se usa para listas eficientes y scrolleables?",
                options = listOf("Column", "RecyclerView", "Stack", "LazyColumn"),
                correctIndex = 3
            ),
            Question(
                id = 4,
                title = "La instrucción que permite restaurar estado tras recreación de Activity es",
                options = listOf("intentData", "savedInstanceState", "activityState", "bundleConfig"),
                correctIndex = 1
            ),
            Question(
                id = 5,
                title = "¿Cual es el tipo de dato en Kotlin para texto?",
                options = listOf("Text", "Char", "String", "Word"),
                correctIndex = 2
            ),
            Question(
                id = 6,
                title = "¿Como se llama el patron de arquitectura recomendado para Jetpack Compose?",
                options = listOf("MVC", "MVP", "MVVM", "VIPER"),
                correctIndex = 2
            ),
            Question(
                id = 7,
                title = "¿Que funcion se usa para guardar estado en un Composable?",
                options = listOf("rememberState()", "useState()", "remember { mutableStateOf() }", "saveState()"),
                correctIndex = 2
            ),
            Question(
                id = 8,
                title = "¿Que modificador en Compose hace que un elemento ocupe todo el ancho disponible?",
                options = listOf("Modifier.maxWidth()", "Modifier.fillMaxWidth()", "Modifier.wrapContent()", "Modifier.expand()"),
                correctIndex = 1
            ),
            Question(
                id = 9,
                title = "¿Como se declara una funcion en Kotlin?",
                options = listOf("function miFuncion()", "def miFuncion()", "fun miFuncion()", "func miFuncion()"),
                correctIndex = 2
            ),
            Question(
                id = 10,
                title = "¿Que clase se usa como base para pantallas en una app Android moderna?",
                options = listOf("AppCompatActivity", "ComponentActivity", "FragmentActivity", "BaseActivity"),
                correctIndex = 1
            ),
            Question(
                id = 11,
                title = "¿Como se llama el archivo principal de configuracion de dependencias en Gradle con Kotlin DSL?",
                options = listOf("build.gradle", "build.gradle.kts", "settings.xml", "dependencies.kts"),
                correctIndex = 1
            ),
            Question(
                id = 12,
                title = "¿Que componente de Compose organiza elementos en forma vertical?",
                options = listOf("Row", "Box", "Column", "Stack"),
                correctIndex = 2
            ),
            Question(
                id = 13,
                title = "En Kotlin, ¿que simbolo indica que una variable puede ser nula?",
                options = listOf("!", "?", "#", "*"),
                correctIndex = 1
            ),
            Question(
                id = 14,
                title = "¿Que clase de ViewModel se usa para exponer estado reactivo?",
                options = listOf("LiveData", "StateFlow", "Observable", "Channel"),
                correctIndex = 1
            ),
            Question(
                id = 15,
                title = "¿Que palabra se usa en Kotlin para heredar de una clase?",
                options = listOf("extends", "implements", ":", "inherits"),
                correctIndex = 2
            )
        )
    }
}
