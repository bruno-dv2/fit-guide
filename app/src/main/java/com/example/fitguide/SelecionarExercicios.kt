package com.example.fitguide

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SelecionarExercicios : AppCompatActivity() {

    private var fichaId: Int = -1
    private lateinit var ficha: Ficha
    private lateinit var diaSemana: String
    private lateinit var grupoMuscular: String
    private val exerciciosSelecionados = mutableListOf<ExercicioTreino>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selecionar_exercicios)

        // Configurar o botão de voltar
        val botaoVoltar = findViewById<ImageButton>(R.id.imageButtonBack)
        botaoVoltar.setOnClickListener {
            finish()
        }

        // Obter o ID da ficha, dia da semana e grupo muscular
        fichaId = intent.getIntExtra("fichaId", -1)
        diaSemana = intent.getStringExtra("diaSemana") ?: ""
        grupoMuscular = intent.getStringExtra("grupoMuscular") ?: ""

        if (fichaId == -1 || diaSemana.isEmpty() || grupoMuscular.isEmpty()) {
            finish()
            return
        }

        ficha = BancoTemporario.listaFichas.find { it.id == fichaId }
            ?: run {
                finish()
                return
            }

        // Atualizar título
        findViewById<TextView>(R.id.textViewTitulo).text = "Selecione os exercícios"
        findViewById<TextView>(R.id.textViewSubtitulo).text = "Adicione os exercícios"

        // Adicionar exercícios para seleção
        carregarExercicios()

        // Configurar botão próximo
        findViewById<Button>(R.id.buttonProximo).setOnClickListener {
            salvarExercicios()

            val intent = Intent(this, ConfigurarTreino::class.java)
            intent.putExtra("fichaId", fichaId)
            intent.putExtra("diaSemana", diaSemana)
            startActivity(intent)
        }
    }

    private fun carregarExercicios() {
        val exerciciosContainer = findViewById<LinearLayout>(R.id.exerciciosContainer)
        exerciciosContainer.removeAllViews()

        // Verificar se temos um DiaTreino para este dia da semana
        if (!ficha.diasTreino.containsKey(diaSemana)) {
            ficha.diasTreino[diaSemana] = DiaTreino(diaSemana)
        }

        val diaTreino = ficha.diasTreino[diaSemana]!!

        // Filtrar exercícios pelo grupo muscular selecionado
        val exerciciosFiltrados = BancoTemporario.listaExercicios.filter { it.categoria == grupoMuscular }

        if (exerciciosFiltrados.isEmpty()) {
            // Se não houver exercícios cadastrados, exibir alguns exemplos fixos
            val exemplos = getExerciciosExemplo(grupoMuscular)

            for (exercicio in exemplos) {
                BancoTemporario.listaExercicios.add(exercicio)
                adicionarExercicioView(exerciciosContainer, exercicio, diaTreino)
            }
        } else {
            // Exibir exercícios cadastrados
            for (exercicio in exerciciosFiltrados) {
                adicionarExercicioView(exerciciosContainer, exercicio, diaTreino)
            }
        }
    }

    private fun adicionarExercicioView(container: LinearLayout, exercicio: Exercicio, diaTreino: DiaTreino) {
        val exercicioView = LayoutInflater.from(this).inflate(R.layout.item_exercicio_selecao, container, false)

        val nomeExercicio = exercicioView.findViewById<TextView>(R.id.textViewNomeExercicio)
        val detalhesExercicio = exercicioView.findViewById<TextView>(R.id.textViewDetalhesExercicio)
        val checkBox = exercicioView.findViewById<CheckBox>(R.id.checkBoxSelecionar)

        nomeExercicio.text = exercicio.nome
        detalhesExercicio.text = "Equipamento: ${exercicio.equipamento}"

        // Verificar se o exercício já está selecionado neste dia
        val jaExiste = diaTreino.exercicios.any { it.exercicio.id == exercicio.id }

        checkBox.isChecked = jaExiste

        if (jaExiste) {
            // Adicionar à lista de selecionados se já existe
            val exercicioTreino = diaTreino.exercicios.find { it.exercicio.id == exercicio.id }
            if (exercicioTreino != null && !exerciciosSelecionados.contains(exercicioTreino)) {
                exerciciosSelecionados.add(exercicioTreino)
            }
        }

        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                // Adicionar à lista de selecionados se ainda não estiver
                if (!exerciciosSelecionados.any { it.exercicio.id == exercicio.id }) {
                    exerciciosSelecionados.add(ExercicioTreino(exercicio))
                }
            } else {
                // Remover da lista de selecionados
                exerciciosSelecionados.removeAll { it.exercicio.id == exercicio.id }
            }
        }

        container.addView(exercicioView)
    }

    private fun getExerciciosExemplo(grupo: String): List<Exercicio> {
        return when (grupo) {
            "Peito" -> listOf(
                Exercicio("Supino Reto", "Barra e Banco", "Deite-se no banco, segure a barra e faça o movimento", "Peitoral, Tríceps, Ombros", "", "Peito"),
                Exercicio("Supino Inclinado", "Barra e Banco Inclinado", "Deite-se no banco inclinado e faça o movimento", "Peitoral superior, Tríceps, Ombros", "", "Peito"),
                Exercicio("Crucifixo", "Halteres e Banco", "Deite-se no banco, abra os braços lateralmente", "Peitoral, Ombros", "", "Peito")
            )
            "Costas" -> listOf(
                Exercicio("Puxada Frontal", "Máquina", "Puxe a barra para baixo em direção ao peito", "Dorsais, Bíceps", "", "Costas"),
                Exercicio("Remada Curvada", "Barra", "Curve-se e puxe a barra em direção ao abdômen", "Dorsais, Lombar, Bíceps", "", "Costas")
            )
            "Pernas" -> listOf(
                Exercicio("Agachamento", "Barra", "Coloque a barra nos ombros e agache", "Quadríceps, Glúteos, Isquiotibiais", "", "Pernas"),
                Exercicio("Leg Press", "Máquina", "Empurre a plataforma com os pés", "Quadríceps, Glúteos", "", "Pernas")
            )
            "Braços" -> listOf(
                Exercicio("Rosca Direta", "Barra", "Flexione os cotovelos segurando a barra", "Bíceps", "", "Braços"),
                Exercicio("Tríceps Corda", "Cabo e Corda", "Estenda os cotovelos empurrando a corda para baixo", "Tríceps", "", "Braços")
            )
            "Abdômen" -> listOf(
                Exercicio("Abdominal Reto", "Colchonete", "Deite-se e levante o tronco", "Abdômen", "", "Abdômen"),
                Exercicio("Prancha", "Colchonete", "Mantenha-se na posição de prancha", "Abdômen, Core", "", "Abdômen")
            )
            "Ombro" -> listOf(
                Exercicio("Desenvolvimento", "Barra ou Halteres", "Empurre o peso acima da cabeça", "Deltoides", "", "Ombro"),
                Exercicio("Elevação Lateral", "Halteres", "Eleve os halteres lateralmente", "Deltoides laterais", "", "Ombro")
            )
            else -> listOf()
        }
    }

    private fun salvarExercicios() {
        // Garantir que o dia existe
        if (!ficha.diasTreino.containsKey(diaSemana)) {
            ficha.diasTreino[diaSemana] = DiaTreino(diaSemana)
        }

        val diaTreino = ficha.diasTreino[diaSemana]!!

        // Remover exercícios deste grupo muscular que não estão mais selecionados
        diaTreino.exercicios.removeAll { exercicio ->
            exercicio.exercicio.categoria == grupoMuscular &&
                    !exerciciosSelecionados.any { it.exercicio.id == exercicio.exercicio.id }
        }

        // Adicionar novos exercícios selecionados
        for (exercicioTreino in exerciciosSelecionados) {
            if (!diaTreino.exercicios.any { it.exercicio.id == exercicioTreino.exercicio.id }) {
                diaTreino.exercicios.add(exercicioTreino)
            }
        }
    }
}